package link.kotlin.scripts

import link.kotlin.scripts.dsl.Article

interface ArticlesProcessor {
    fun process(article: Article): Article

    companion object
}

private class DefaultArticlesProcessor(
    private val markdownRenderer: MarkdownRenderer
) : ArticlesProcessor {
    override fun process(article: Article): Article {
        val html = markdownRenderer.render(article.body)

        return article.copy(
            description = html,
            body = html,
            filename = getFileName(article.title)
        )
    }
}

internal fun getFileName(title: String): String {
    val escaped = title
        .map { code ->
            if (
                (code in 'a'..'z') ||
                (code in 'A'..'Z') ||
                (code in '0'..'9') ||
                (code in 'a'..'я') ||
                (code in 'А'..'Я')
            ) {
                code
            } else {
                '-'
            }
        }
        .joinToString(separator = "")
        .replace(Regex("-$"), "") // Replace last dash in string with ''
        .replace(Regex("^-"), "") // Replace first dash in string with ''
        .replace(Regex("-+"), "-") // Replace multiple dashes with one dash

    return "$escaped.html"
}

fun ArticlesProcessor.Companion.default(
    markdownRenderer: MarkdownRenderer
): ArticlesProcessor {
    return DefaultArticlesProcessor(
        markdownRenderer = markdownRenderer
    )
}
