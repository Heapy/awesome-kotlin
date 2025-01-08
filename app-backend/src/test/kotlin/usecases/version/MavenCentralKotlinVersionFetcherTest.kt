package usecases.version

import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.*
import types.IntegrationTest

class MavenCentralKotlinVersionFetcherTest {
    @IntegrationTest
    fun `pull versions from central`() = runTest {
        val fetcher = createKotlinVersionModule {}
            .kotlinVersionFetcher

        val fetched = fetcher.getLatestVersions(listOf("1.9"))

        assertEquals(
            listOf("1.9.25"),
            fetched,
        )
    }
}
