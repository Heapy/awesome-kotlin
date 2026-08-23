package usecases.kug

import HttpClientModule
import YamlModule
import io.heapy.komok.tech.di.delegate.buildModule
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.AssertionFailureBuilder.assertionFailure
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class KugDownloadServiceTest {
    private val httpClientModule = buildModule<HttpClientModule>()
    private val yamlModule = buildModule<YamlModule>()

    private val kugDownloadService = KugDownloadService(
        yaml = yamlModule.yaml,
        httpClient = httpClientModule.httpClient,
    )

    @AfterEach
    fun close() {
        // TODO: autoclosableModule.close()
    }

    @Test
    fun `test getting yaml file with user groups`() = runTest {
        val yaml = kugDownloadService.download()

        if (!yaml.contains("https://www.meetup.com/kotlin-amsterdam/")) {
            assertionFailure()
                .message("yaml")
                .expected("Yaml with Kotlin User Groups")
                .actual(yaml)
                .buildAndThrow()
        }
    }

    @Test
    fun `test parsing yaml file with user groups`() = runTest {
        val sections = kugDownloadService.pull()
        val amsterdamKug = sections.find { it.section == "Europe" }
            ?.groups
            ?.find { it.url == "https://www.meetup.com/kotlin-amsterdam/" }

        assertEquals(
            KugDownloadService.Kug(
                name = "Amsterdam KUG",
                country = "Netherlands",
                url = "https://www.meetup.com/kotlin-amsterdam/",
                position = KugDownloadService.Position(
                    lat = 52.3675734,
                    lng = 4.9041389,
                ),
            ),
            amsterdamKug
        )
    }
}
