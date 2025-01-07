package usecases.version

import HttpClientModule
import XmlModule
import io.heapy.komok.tech.di.delegate.bean

open class KotlinVersionModule(
    private val xmlModule: XmlModule,
    private val httpClientModule: HttpClientModule,
) {
    open val versionFetcher by bean<KotlinVersionFetcher> {
        MavenCentralKotlinVersionFetcher(
            xmlMapper = xmlModule.xmlMapper.value,
            httpClient = httpClientModule.httpClient.value,
        )
    }
}
