package usecases.version

import infra.db.transaction.TransactionContext
import infra.db.transaction.dslContext
import infra.serialization.serializers.OffsetDateTimeSerializer
import io.heapy.komok.tech.time.TimeSource
import jooq.main.tables.KotlinVersion.Companion.KOTLIN_VERSION
import kotlinx.serialization.Serializable
import java.time.OffsetDateTime

class KotlinVersionDao(
    private val timeSource: TimeSource,
) {
    context(_: TransactionContext)
    fun latestAndGreatest(): List<KotlinVersion> = dslContext { dslContext ->
        dslContext
            .select(
                KOTLIN_VERSION.ID,
                KOTLIN_VERSION.VALUE,
                KOTLIN_VERSION.CREATED,
                KOTLIN_VERSION.DISPLAY,
            )
            .from(KOTLIN_VERSION)
            .where(KOTLIN_VERSION.DISPLAY.eq(true))
            .fetchInto(KotlinVersion::class.java)
    }

    context(_: TransactionContext)
    fun getAll(): List<KotlinVersion> = dslContext { dslContext ->
        dslContext
            .select(
                KOTLIN_VERSION.ID,
                KOTLIN_VERSION.VALUE,
                KOTLIN_VERSION.CREATED,
                KOTLIN_VERSION.DISPLAY,
            )
            .from(KOTLIN_VERSION)
            .fetchInto(KotlinVersion::class.java)
    }

    context(_: TransactionContext)
    fun updateDisplayStatus(
        id: Long,
        display: Boolean,
    ): Int = dslContext { dslContext ->
        dslContext
            .update(KOTLIN_VERSION)
            .set(KOTLIN_VERSION.DISPLAY, display)
            .where(KOTLIN_VERSION.ID.eq(id))
            .and(KOTLIN_VERSION.DISPLAY.eq(!display))
            .execute()
    }

    context(_: TransactionContext)
    fun mergeVersions(
        versions: List<String>,
    ): List<KotlinVersion> = dslContext { dslContext ->
        val knownVersions = getAll()
            .map(KotlinVersion::value)
            .toSet()

        val newVersions = versions
            .filter { version -> version !in knownVersions }

        newVersions
            .map { newVersion ->
                dslContext
                    .insertInto(
                        KOTLIN_VERSION,
                        KOTLIN_VERSION.VALUE,
                        KOTLIN_VERSION.CREATED,
                    )
                    .values(
                        newVersion,
                        timeSource.offsetDateTime(),
                    )
                    .returning(
                        KOTLIN_VERSION.ID,
                        KOTLIN_VERSION.VALUE,
                        KOTLIN_VERSION.CREATED,
                        KOTLIN_VERSION.DISPLAY
                    )
                    .fetchSingleInto(KotlinVersion::class.java)
            }
    }

    @Serializable
    data class KotlinVersion(
        val id: Long,
        val value: String,
        @Serializable(with = OffsetDateTimeSerializer::class)
        val created: OffsetDateTime,
        val display: Boolean,
    )
}
