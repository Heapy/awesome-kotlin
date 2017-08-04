package link.kotlin.scripts

import link.kotlin.scripts.utils.DefaultScriptCompiler
import link.kotlin.scripts.utils.ScriptCompiler
import java.nio.file.Files
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

class ProjectLinks(private val compiler: ScriptCompiler = DefaultScriptCompiler()) {
    private val _links by lazy {
        files.map(this::linksFromFile)
    }

    fun getLinks(): List<Category> {
        return _links
    }

    private fun linksFromFile(path: String): Category {
        return compiler.execute(Files.newInputStream(Paths.get("links/$path")))
    }
}
