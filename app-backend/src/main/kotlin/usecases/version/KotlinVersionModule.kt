package usecases.version

import infra.HttpClientModule
import io.heapy.komok.tech.di.lib.Module
import infra.serialization.XmlModule

@Module
open class KotlinVersionModule(
    private val xmlModule: XmlModule,
    private val httpClientModule: HttpClientModule,
) {
    open val kotlinVersionFetcher: KotlinVersionFetcher by lazy {
        CachedVersionFetcher(
            kotlinVersionFetcher = MavenCentralKotlinVersionFetcher(
                xml = xmlModule.xml,
                httpClient = httpClientModule.httpClient,
            )
        )
    }

    open val route by lazy {
        KotlinVersionRoute(
            kotlinVersionFetcher = kotlinVersionFetcher,
        )
    }
}
