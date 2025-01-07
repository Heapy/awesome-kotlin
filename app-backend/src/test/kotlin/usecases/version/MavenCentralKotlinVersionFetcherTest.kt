package usecases.version

import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.assertTrue
import types.IntegrationTest

class MavenCentralKotlinVersionFetcherTest {
    @IntegrationTest
    fun `pull versions from central`() = runTest {
        val fetcher = createKotlinVersionModule {}
            .kotlinVersionFetcher

        val fetched = fetcher.getLatestVersions()

        assertTrue(fetched.contains("2.2.0"))
    }
}
