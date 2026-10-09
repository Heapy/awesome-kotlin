package link.kotlin.scripts

import io.heapy.komok.tech.di.delegate.buildModule
import link.kotlin.scripts.module.UtilsModule

suspend fun main() {
    val utilsModule = buildModule<UtilsModule>()
    val client = utilsModule.httpClient.value

    val fetcher = MavenCentralKotlinVersionFetcher(client)
    val versions = fetcher.getLatestVersions(listOf("2.3", "2.4", "2.5"))
    println(versions)
}
