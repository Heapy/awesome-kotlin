package link.kotlin.scripts

import link.kotlin.scripts.utils.createHttpClient

suspend fun main() {
    val client = createHttpClient()
    val fetcher = MavenCentralVersionFetcher(client)
    val versions = fetcher.getLatestVersions(listOf("1.3", "1.4"))
    println(versions)
}
