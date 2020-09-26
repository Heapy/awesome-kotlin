package link.kotlin.scripts.import

import by.heap.remark.Options
import by.heap.remark.Remark
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import kotlinx.coroutines.runBlocking
import link.kotlin.scripts.utils.HttpClient
import link.kotlin.scripts.utils.body
import link.kotlin.scripts.utils.default
import link.kotlin.scripts.utils.parseInstant
import org.apache.http.client.methods.HttpGet
import org.apache.http.client.utils.URIBuilder
import java.nio.file.Files
import java.nio.file.Paths
import java.nio.file.StandardOpenOption
import java.time.LocalDateTime.now

class Readability(
    private val httpClient: HttpClient
) {
    suspend fun getArticle(url: String): String {
        val request = HttpGet().also {
            it.uri = URIBuilder("https://mercury.postlight.com/parser").addParameter("url", url).build()
            it.addHeader("Content-Type", "application/json")
        }

        return httpClient.execute(request).body()
    }
}

fun main() = runBlocking {
    val mapper = jacksonObjectMapper()
    val readability = Readability(
        HttpClient.default()
    )

    val response = readability.getArticle("https://blog.jetbrains.com/kotlin/2017/03/kotlin-1-1-1-is-out/")
    val res = mapper.readValue<ReadabilityResponse>(response)
    val article = res.toArticle()
    Files.write(
        Paths.get("./articles/english/2017/${res.title}.kts"),
        article.toByteArray(),
        StandardOpenOption.CREATE,
        StandardOpenOption.TRUNCATE_EXISTING
    )
    Unit
}

data class ReadabilityResponse(
    val title: String,
    val content: String,
    val author: String,
    val date_published: String?, // "2016-09-30T07:00:12.000Z"
    val lead_image_url: String?,
    val dek: String?,
    val url: String,
    val domain: String,
    val excerpt: String,
    val word_count: Int,
    val direction: String,
    val total_pages: Int,
    val rendered_pages: Int,
    val next_page_url: String?
)

val opts = Options().apply {
    hardwraps = true
    fencedCodeBlocks = Options.FencedCodeBlocks.ENABLED_BACKTICK
    autoLinks = true
    tables = Options.Tables.CONVERT_TO_CODE_BLOCK
    inlineLinks = true
}
val remark = Remark(opts)

fun ReadabilityResponse.toArticle(): String {
    val date = if (date_published != null) parseInstant(date_published) else now()

    val body = remark.convert(content)

    return """

import link.kotlin.scripts.dsl.Article
import link.kotlin.scripts.dsl.Enclosure
import link.kotlin.scripts.dsl.LanguageCodes.EN
import link.kotlin.scripts.dsl.LinkType.article
import java.time.LocalDate

// language=Markdown
val body = ${"\"\"\""}
$body
${"\"\"\""}

Article(
  title = "$title",
  url = "$url",
  categories = listOf(
    "Kotlin"
  ),
  type = article,
  lang = EN,
  author = "$author",
  date = LocalDate.of(${date.year}, ${date.month.value}, ${date.dayOfMonth}),
  body = body
)
"""
}
