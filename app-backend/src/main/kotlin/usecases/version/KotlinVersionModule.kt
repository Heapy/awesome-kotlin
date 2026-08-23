package usecases.version

import HttpClientModule
import XmlModule
import io.heapy.komok.tech.di.lib.Module

@Module
class KotlinVersionModule(
    private val xmlModule: XmlModule,
    private val httpClientModule: HttpClientModule,
) {
    val versionFetcher: KotlinVersionFetcher by lazy {
        MavenCentralKotlinVersionFetcher(
            xmlMapper = xmlModule.xmlMapper,
            httpClient = httpClientModule.httpClient,
        )
    }
}
