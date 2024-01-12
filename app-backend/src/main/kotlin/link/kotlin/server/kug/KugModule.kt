package link.kotlin.server.kug

import link.kotlin.server.SharedModule

open class KugModule(
    open val sharedModule: SharedModule,
) {
    open val kugDownloadService by lazy {
        KugDownloadService(
            yaml = sharedModule.yaml,
            httpClient = sharedModule.httpClient,
        )
    }
}
