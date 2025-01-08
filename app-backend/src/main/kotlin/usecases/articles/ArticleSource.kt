package usecases.articles

import infra.serialization.LocalDateTimeSerializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.builtins.ListSerializer
import kotlinx.serialization.json.Json
import java.time.LocalDate

class ArticleSource(
    private val articlesProcessor: ArticlesProcessor,
) {
    fun getArticles(): List<Article> {
        val json = ArticleSource::class.java
            .classLoader
            .getResource("data/articles.json")
            .readText()

        return Json
            .decodeFromString(ListSerializer(Article.serializer()), json)
            .map(articlesProcessor::process)
    }
}

enum class ArticleFeature {
    mathjax,
    highlightjs
}

enum class LinkType {
    article,
    video,
    slides,
    webinar
}

@Serializable
data class Article(
    val title: String,
    val url: String,
    val body: String,
    val author: String,
    @Serializable(with = LocalDateTimeSerializer::class)
    val date: LocalDate,
    val type: LinkType,
    val categories: List<String> = listOf(),
    val features: List<ArticleFeature> = listOf(ArticleFeature.highlightjs),
    val description: String = "",
    val filename: String = "",
    val lang: LanguageCodes = LanguageCodes.EN,
    val enclosure: Enclosure? = null
)

@Serializable
data class Enclosure(
    val url: String,
    val size: Int
)

/**
 * List of languages names - codes according ISO 639-1
 * https://en.wikipedia.org/wiki/List_of_ISO_639-1_codes
 *
 * @author Ibragimov Ruslan
 * @since 0.1
 */
enum class LanguageCodes(val id: String) {
    EN("english"),
    RU("russian"),
    IT("italian"),
    ZH("chinese"),
    HE("hebrew");

    companion object {
        fun contains(language: String) =
            entries
                .map(LanguageCodes::id)
                .contains(language)
    }
}
