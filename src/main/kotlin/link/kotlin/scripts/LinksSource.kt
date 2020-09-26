package link.kotlin.scripts

import kotlinx.coroutines.runBlocking
import link.kotlin.scripts.scripting.ScriptEvaluator
import link.kotlin.scripts.scripting.default
import java.nio.file.Paths
import java.nio.file.Files
import kotlin.streams.toList

interface LinksSource {
    fun getLinks(): List<Category>

    companion object
}

private class FileSystemLinksSource(
    private val scriptEvaluator: ScriptEvaluator,
    private val categoryProcessor: CategoryProcessor
) : LinksSource {
    override fun getLinks(): List<Category> = runBlocking {
        Files.list(Paths.get("src/main/resources/links"))
            .toList()
            .filter { it.fileName.toString().endsWith(".awesome.kts") }
            .map { path ->
                val source = Files.readString(path)
                scriptEvaluator.eval(source, path.toString(), Category::class)
            }
            .map { category -> categoryProcessor.process(category) }
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
