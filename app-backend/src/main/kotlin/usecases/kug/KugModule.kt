package usecases.kug

import infra.HttpClientModule
import infra.serialization.YamlModule
import io.heapy.komok.tech.di.lib.Module

@Module
open class KugModule(
    private val httpClientModule: HttpClientModule,
    private val yamlModule: YamlModule,
) {
    open val kugDownloadService by lazy {
        KugDownloadService(
            yaml = yamlModule.yaml,
            httpClient = httpClientModule.httpClient,
        )
    }

    open val kugDao by lazy<KugDao> {
        KugDao()
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
