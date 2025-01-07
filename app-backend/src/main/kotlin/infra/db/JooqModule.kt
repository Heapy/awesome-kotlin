package infra.db

import infra.coroutines.DispatchersModule
import infra.db.transaction.JooqTransactionProvider
import infra.db.transaction.TransactionProvider
import io.heapy.komok.tech.di.lib.Module
import org.jooq.SQLDialect
import org.jooq.impl.DSL

@Module
open class JooqModule(
    private val jdbcModule: JdbcModule,
    private val dispatchersModule: DispatchersModule,
) {
    open val dslContext by lazy {
        System.setProperty("org.jooq.no-logo", "true")
        System.setProperty("org.jooq.no-tips", "true")
        DSL.using(jdbcModule.dataSource, SQLDialect.POSTGRES)
    }

    open val transactionProvider: TransactionProvider by lazy {
        JooqTransactionProvider(
            dslContext = dslContext,
            ioDispatcher = dispatchersModule.ioDispatcher,
        )
    }
}
