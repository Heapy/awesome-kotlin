package usecases.kug

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.serialization.Serializable
import infra.serialization.OffsetDateTimeSerializer
import jooq.main.tables.references.KUG
import org.jooq.DSLContext
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

data class KugCreatePrj(
    val continent: String,
    val name: String,
    val country: String,
    val url: String,
    val latitude: Double?,
    val longitude: Double?,
)

interface KugDao {
    suspend fun getAll(): List<Kug>
    suspend fun create(kug: KugCreatePrj): Kug
}

class DefaultKugDao(
    private val dslContext: DSLContext,
) : KugDao {
    override suspend fun getAll(): List<Kug> = withContext(Dispatchers.IO) {
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

    override suspend fun create(
        kug: KugCreatePrj,
    ): Kug = withContext(Dispatchers.IO) {
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
