package usecases.kug

import HttpClientModule
import JooqModule
import YamlModule
import di.bean

open class KugModule(
    private val httpClientModule: HttpClientModule,
    private val yamlModule: YamlModule,
    private val jooqModule: JooqModule,
) {
    open val kugDownloadService by bean {
        KugDownloadService(
            yaml = yamlModule.yaml.get,
            httpClient = httpClientModule.httpClient.get,
        )
    }

    open val kugDao by bean<KugDao> {
        DefaultKugDao(jooqModule.dslContext.get)
    }

    open val updateKugsRoute by bean {
        UpdateKugsRoute(
            kugDownloadService = kugDownloadService.get,
        )
    }

    val getKugRoute by bean {
        GetKugsRoute(
            kugDao = kugDao.get,
        )
    }
}

