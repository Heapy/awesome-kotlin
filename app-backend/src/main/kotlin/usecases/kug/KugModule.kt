package usecases.kug

import HttpClientModule
import JooqModule
import YamlModule
import io.heapy.komok.tech.di.lib.Module

@Module
class KugModule(
    private val httpClientModule: HttpClientModule,
    private val yamlModule: YamlModule,
    private val jooqModule: JooqModule,
) {
    val kugDownloadService by lazy {
        KugDownloadService(
            yaml = yamlModule.yaml,
            httpClient = httpClientModule.httpClient,
        )
    }

    val kugDao by lazy<KugDao> {
        DefaultKugDao(jooqModule.dslContext)
    }

    val updateKugsRoute by lazy {
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
