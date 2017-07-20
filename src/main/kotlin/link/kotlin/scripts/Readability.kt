package link.kotlin.scripts

import by.heap.remark.Options
import by.heap.remark.Remark
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import okhttp3.HttpUrl
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import java.nio.file.Files
import java.nio.file.Paths
import java.nio.file.StandardOpenOption
import java.time.LocalDateTime.now
import java.util.concurrent.CompletableFuture

class Readability(
    val client: OkHttpClient,
    val configuration: ApplicationConfiguration
) {
    fun getArticle(url: String): CompletableFuture<Response> {
        val request = Request.Builder()
            .url(HttpUrl.Builder()
                .scheme("https")
                .host("mercury.postlight.com")
                .encodedPath("/parser")
                .addQueryParameter("url", url)
                .build()
            )
            .header("Content-Type", "application/json")
            .header("x-api-key", configuration.mercuryToken)
            .build()

        return client.newCall(request).async()
    }
}

fun main(args: Array<String>) {
    okhttp { okHttpClient ->
        val mapper = jacksonObjectMapper()
        val readability = Readability(
            okHttpClient,
            ApplicationConfiguration(
                ghUser = "",
                ghToken = "",
                mercuryToken = System.getenv("MERCURY_TOKEN") ?: ""
            )
        )

        val response = readability.getArticle("https://blog.jetbrains.com/kotlin/2017/03/kotlin-1-1-1-is-out/")
        val res = mapper.readValue<ReadabilityResponse>(response.get().body().string())
        val article = res.toArticle()
        Files.write(
            Paths.get("./articles/english/2017/${res.title}.kts"),
            article.toByteArray(),
            StandardOpenOption.CREATE,
            StandardOpenOption.TRUNCATE_EXISTING
        )
    }
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

import link.kotlin.scripts.Article
import link.kotlin.scripts.Enclosure
import link.kotlin.scripts.LanguageCodes.EN
import link.kotlin.scripts.LinkType.article
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
