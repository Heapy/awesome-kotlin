package usecases.kug

import infra.db.transaction.TransactionContext
import infra.db.transaction.dslContext
import infra.serialization.serializers.OffsetDateTimeSerializer
import jooq.main.tables.references.KUG
import kotlinx.serialization.Serializable
import java.time.OffsetDateTime

@Serializable
data class Kug(
    val id: Long,
    val continent: String,
    val name: String,
    val country: String,
    val url: String,
    val latitude: Double?,
    val longitude: Double?,
    @Serializable(OffsetDateTimeSerializer::class)
    val created: OffsetDateTime,
)

data class CreateKugRequest(
    val continent: String,
    val name: String,
    val country: String,
    val url: String,
    val latitude: Double?,
    val longitude: Double?,
)

class KugDao() {
    context(_: TransactionContext)
    fun getAll(): List<Kug> = dslContext { dslContext ->
        dslContext
            .select(
                KUG.ID,
                KUG.CONTINENT,
                KUG.NAME,
                KUG.COUNTRY,
                KUG.URL,
                KUG.LATITUDE,
                KUG.LONGITUDE,
                KUG.CREATED,
            )
            .from(KUG)
            .fetchInto(Kug::class.java)
    }

    context(_: TransactionContext)
    fun create(
        kug: CreateKugRequest,
    ): Kug = dslContext { dslContext ->
        dslContext
            .insertInto(
                KUG,
                KUG.CONTINENT,
                KUG.NAME,
                KUG.COUNTRY,
                KUG.URL,
                KUG.LATITUDE,
                KUG.LONGITUDE,
                KUG.CREATED,
            )
            .values(
                kug.continent,
                kug.name,
                kug.country,
                kug.url,
                kug.latitude,
                kug.longitude,
                OffsetDateTime.now(),
            )
            .returning()
            .fetchSingleInto(Kug::class.java)
    }
}
