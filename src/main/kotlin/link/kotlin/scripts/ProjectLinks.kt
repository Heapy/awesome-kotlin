package link.kotlin.scripts

import link.kotlin.scripts.scripting.AwesomeScriptHost
import link.kotlin.scripts.utils.logger
import java.nio.file.Paths

private val files = listOf(
    "Links.kts",
    "Libraries.kts",
    "Projects.kts",
    "Android.kts",
    "JavaScript.kts",
    "Native.kts",
    "UserGroups.kts"
)

class ProjectLinks(private val scriptHost: AwesomeScriptHost) {
    private val _links by lazy {
        files.map(this::linksFromFile)
    }

    fun getLinks(): List<Category> {
        return _links
    }

    private fun linksFromFile(path: String): Category {
        try {
            return scriptHost.eval(Paths.get("src/main/resources/links/$path").toFile())
        } catch (e: Exception) {
            LOGGER.error("Error while processing file {}", path, e)
            throw e
        }
    }

    companion object {
        private val LOGGER = logger<ProjectLinks>()
    }
}
