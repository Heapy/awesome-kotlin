package usecases.kug

import HttpClientModule
import JooqModule
import YamlModule
import io.heapy.komok.tech.di.delegate.bean

open class KugModule(
    private val httpClientModule: HttpClientModule,
    private val yamlModule: YamlModule,
    private val jooqModule: JooqModule,
) {
    open val kugDownloadService by bean {
        KugDownloadService(
            yaml = yamlModule.yaml.value,
            httpClient = httpClientModule.httpClient.value,
        )
    }

    open val kugDao by bean<KugDao> {
        DefaultKugDao(jooqModule.dslContext.value)
    }

    open val updateKugsRoute by bean {
        UpdateKugsRoute(
            kugDownloadService = kugDownloadService.value,
        )
    }

    val getKugRoute by bean {
        GetKugsRoute(
            kugDao = kugDao.value,
        )
    }
}

