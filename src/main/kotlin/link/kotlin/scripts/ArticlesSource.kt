package link.kotlin.scripts

import link.kotlin.scripts.scripting.ScriptEvaluator
import java.nio.file.Files
import java.nio.file.Paths
import kotlin.streams.toList

interface ArticlesSource {
    fun getArticles(): List<Article>

    companion object
}

private class FileSystemArticlesSource(
    private val scriptEvaluator: ScriptEvaluator,
    private val articlesProcessor: ArticlesProcessor
) : ArticlesSource {
    override fun getArticles(): List<Article> {
        return Files.list(Paths.get("articles"))
            .toList()
            .filter { it.fileName.toString().endsWith(".awesome.kts") }
            .map { path ->
                val source = Files.readString(path)
                scriptEvaluator.eval(source, path.toString(), Article::class)
            }
            .sortedWith { a, b -> b.date.compareTo(a.date) }
            .map(articlesProcessor::process)
    }
}

fun ArticlesSource.Companion.default(
    scriptEvaluator: ScriptEvaluator,
    articlesProcessor: ArticlesProcessor = ArticlesProcessor.default()
): ArticlesSource {
    return FileSystemArticlesSource(
        scriptEvaluator = scriptEvaluator,
        articlesProcessor = articlesProcessor
    )
}
