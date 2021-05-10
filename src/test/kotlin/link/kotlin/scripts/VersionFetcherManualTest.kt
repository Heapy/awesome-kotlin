package link.kotlin.scripts

import link.kotlin.scripts.utils.HttpClient
import link.kotlin.scripts.utils.default

suspend fun main() {
    val client = HttpClient.default()
    val fetcher = KotlinVersionFetcher.default(client)
    val versions = fetcher.getLatestVersions(listOf("1.4", "1.5"))
    println(versions)
}
