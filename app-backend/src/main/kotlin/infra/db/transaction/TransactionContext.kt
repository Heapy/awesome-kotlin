package infra.db.transaction

import infra.coroutines.runBlockingVirtual
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import org.jooq.DSLContext

@TransactionDsl
sealed interface TransactionContext

private class JooqTransactionContext(
    val type: TransactionType,
    val dslContext: DSLContext,
) : TransactionContext

internal object MockTransactionContext : TransactionContext

enum class TransactionType {
    READ_ONLY,
    READ_WRITE,
    NONE,
}

@DslMarker
annotation class TransactionDsl

@TransactionDsl
context(_: TransactionContext)
fun <T> dslContext(
    body: (DSLContext) -> T,
): T {
    return body(unwrap())
}

context(transactionContext: TransactionContext)
private fun unwrap(): DSLContext {
    return when(transactionContext) {
        is JooqTransactionContext -> transactionContext.dslContext
        MockTransactionContext -> throw IllegalStateException("MockTransactionContext does not have a DSLContext")
    }
}

interface TransactionProvider {
    @TransactionDsl
    suspend fun <T> transaction(
        type: TransactionType = TransactionType.NONE,
        block: suspend TransactionContext.() -> T,
    ): T
}

class JooqTransactionProvider(
    private val dslContext: DSLContext,
    private val ioDispatcher: CoroutineDispatcher,
) : TransactionProvider {
    override suspend fun <T> transaction(
        type: TransactionType,
        block: suspend (TransactionContext) -> T,
    ): T = withContext(ioDispatcher) {
        when (type) {
            TransactionType.READ_ONLY -> dslContext.transactionResult { configuration ->
                val transactionDslContext = configuration.dsl()
                transactionDslContext.connection { connection ->
                    connection.isReadOnly = true
                }

                val transactionContext = JooqTransactionContext(
                    type = type,
                    dslContext = transactionDslContext,
                )

                runBlockingVirtual(ioDispatcher) {
                    block(transactionContext)
                }
            }

            TransactionType.READ_WRITE -> dslContext.transactionResult { configuration ->
                val transactionContext = JooqTransactionContext(
                    type = type,
                    dslContext = configuration.dsl(),
                )

                runBlockingVirtual(ioDispatcher) {
                    block(transactionContext)
                }
            }

            TransactionType.NONE -> {
                val transactionContext = JooqTransactionContext(
                    type = type,
                    dslContext = dslContext,
                )

                runBlockingVirtual(ioDispatcher) {
                    block(transactionContext)
                }
            }
        }
    }
}
