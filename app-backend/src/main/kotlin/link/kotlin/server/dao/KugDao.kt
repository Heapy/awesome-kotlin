package link.kotlin.server.dao

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.serialization.KSerializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import link.kotlin.server.jooq.main.tables.references.KUG
import org.jooq.DSLContext
import java.time.LocalDate

interface KugDao {
    suspend fun getAll(): List<GetView>
    suspend fun create(kug: CreateView): GetView

    @Serializable
    data class GetView(
        val id: Long,
        val continent: String,
        val name: String,
        val country: String,
        val url: String,
        val latitude: Double?,
        val longitude: Double?,
        @Serializable(LocalDateSerializer::class)
        val created: LocalDate,
    )

    data class CreateView(
        val continent: String,
        val name: String,
        val country: String,
        val url: String,
        val latitude: Double?,
        val longitude: Double?,
    )
}

object LocalDateSerializer : KSerializer<LocalDate> {
    override val descriptor: SerialDescriptor = PrimitiveSerialDescriptor("LocalDate", PrimitiveKind.STRING)

    override fun serialize(encoder: Encoder, value: LocalDate) {
        encoder.encodeString(value.toString())
    }

    override fun deserialize(decoder: Decoder): LocalDate {
        val string = decoder.decodeString()
        return LocalDate.parse(string)
    }
}

class DefaultKugDao(
    private val dslContext: DSLContext,
) : KugDao {
    override suspend fun getAll(): List<KugDao.GetView> = withContext(Dispatchers.IO) {
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
            .fetchInto(KugDao.GetView::class.java)
    }

    override suspend fun create(kug: KugDao.CreateView): KugDao.GetView = withContext(Dispatchers.IO) {
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
                LocalDate.now(),
            )
            .returning()
            .fetchSingleInto(KugDao.GetView::class.java)
    }
}
