package usecases.kug

import infra.HttpClientModule
import infra.db.JooqModule
import infra.serialization.YamlModule
import io.heapy.komok.tech.di.lib.Module

@Module
open class KugModule(
    private val httpClientModule: HttpClientModule,
    private val yamlModule: YamlModule,
    private val jooqModule: JooqModule,
) {
    open val kugDownloadService by lazy {
        KugDownloadService(
            yaml = yamlModule.yaml,
            httpClient = httpClientModule.httpClient,
        )
    }

    open val kugDao by lazy<KugDao> {
        DefaultKugDao(jooqModule.dslContext)
    }

    open val updateKugsRoute by lazy {
        UpdateKugsRoute(
            kugDownloadService = kugDownloadService,
        )
    }

    val getKugRoute by lazy {
        GetKugsRoute(
            kugDao = kugDao,
        )
    }
}
