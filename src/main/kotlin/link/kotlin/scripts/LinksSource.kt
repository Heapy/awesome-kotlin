package link.kotlin.scripts

import kotlinx.coroutines.runBlocking
import link.kotlin.scripts.scripting.ScriptEvaluator
import java.nio.file.Paths
import java.nio.file.Files

interface LinksSource {
    fun getLinks(): List<Category>

    companion object
}

private val files = listOf(
    "Links.awesome.kts",
    "Libraries.awesome.kts",
    "Projects.awesome.kts",
    "Android.awesome.kts",
    "JavaScript.awesome.kts",
    "Native.awesome.kts",
    "UserGroups.awesome.kts"
)

private class FileSystemLinksSource(
    private val scriptEvaluator: ScriptEvaluator,
    private val categoryProcessor: CategoryProcessor
) : LinksSource {
    override fun getLinks(): List<Category> = runBlocking {
        files.map { file ->
            val source = Files.readString(Paths.get("src/main/resources/links/", file))
            val category = scriptEvaluator.eval(source, file, Category::class)
            categoryProcessor.process(category)
        }
    }
}

fun LinksSource.Companion.default(
    scriptEvaluator: ScriptEvaluator,
    categoryProcessor: CategoryProcessor
): LinksSource {
    return FileSystemLinksSource(
        scriptEvaluator = scriptEvaluator,
        categoryProcessor = categoryProcessor
    )
}
