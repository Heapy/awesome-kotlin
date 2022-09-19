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
        .translit()
        .map { code ->
            if (
                (code in 'a'..'z') ||
                (code in 'A'..'Z') ||
                (code in '0'..'9')
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

private val mapping = mapOf(
    "А" to "A", "а" to "a",
    "Б" to "B", "б" to "b",
    "В" to "V", "в" to "v",
    "Г" to "G", "г" to "g",
    "Д" to "D", "д" to "d",
    "Е" to "E", "е" to "e",
    "Ё" to "YO", "ё" to "yo",
    "Ж" to "J", "ж" to "j",
    "З" to "Z", "з" to "z",
    "И" to "I", "и" to "i",
    "Й" to "Y", "й" to "y",
    "К" to "K", "к" to "k",
    "Л" to "L", "л" to "l",
    "М" to "M", "м" to "m",
    "Н" to "N", "н" to "n",
    "О" to "O", "о" to "o",
    "П" to "P", "п" to "p",
    "Р" to "R", "р" to "r",
    "С" to "S", "с" to "s",
    "Т" to "T", "т" to "t",
    "У" to "U", "у" to "u",
    "Ф" to "F", "ф" to "f",
    "Х" to "H", "х" to "h",
    "Ц" to "TS", "ц" to "ts",
    "Ч" to "CH", "ч" to "ch",
    "Ш" to "SH", "ш" to "sh",
    "Щ" to "SCH", "щ" to "sch",
    "Ъ" to "", "ъ" to "y",
    "Ы" to "YI", "ы" to "yi",
    "Ь" to "", "ь" to "",
    "Э" to "E", "э" to "e",
    "Ю" to "YU", "ю" to "yu",
    "Я" to "YA", "я" to "ya",
)

internal fun String.translit(): String {
    return map { code ->
        mapping[code.toString()] ?: code.toString()
    }.joinToString(separator = "")
}

fun ArticlesProcessor.Companion.default(
    markdownRenderer: MarkdownRenderer
): ArticlesProcessor {
    return DefaultArticlesProcessor(
        markdownRenderer = markdownRenderer
    )
}
