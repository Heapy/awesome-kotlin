package usecases.version

import HttpClientModule
import XmlModule
import di.bean

open class KotlinVersionModule(
    private val xmlModule: XmlModule,
    private val httpClientModule: HttpClientModule,
) {
    open val versionFetcher by bean<KotlinVersionFetcher> {
        MavenCentralKotlinVersionFetcher(
            xmlMapper = xmlModule.xmlMapper.get,
            httpClient = httpClientModule.httpClient.get,
        )
    }
}
