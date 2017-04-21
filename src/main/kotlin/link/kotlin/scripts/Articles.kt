package link.kotlin.scripts

import link.kotlin.scripts.ArticleFeature.highlightjs
import link.kotlin.scripts.LanguageCodes.EN
import link.kotlin.scripts.model.Link
import org.commonmark.parser.Parser
import org.commonmark.renderer.html.HtmlRenderer
import java.io.IOException
import java.nio.file.FileVisitResult
import java.nio.file.FileVisitor
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths
import java.nio.file.attribute.BasicFileAttributes
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeFormatterBuilder
import java.time.format.SignStyle.EXCEEDS_PAD
import java.time.format.TextStyle.FULL
import java.time.temporal.ChronoField.MONTH_OF_YEAR
import java.time.temporal.ChronoField.YEAR

// TODO: FIXME: WARNING: Refactor SHIT code

data class Article(
    val title: String,
    val url: String,
    val body: String,
    val author: String,
    val date: LocalDate,
    val type: LinkType,
    val categories: List<String> = listOf(),
    val features: List<ArticleFeature> = listOf(highlightjs),
    val description: String = "",
    val filename: String = "",
    val prev: String = "",
    val next: String = "",
    val lang: LanguageCodes = EN,
    val enclosure: Enclosure? = null
)

data class Enclosure(
    val url: String,
    val size: Int
)

/**
 * Provides access to all article entries.
 *
 * @author Ibragimov Ruslan
 * @since 0.1
 */
class Articles(private val compiler: ScriptCompiler) {
    private val _articles by lazy {
        this
            .scan(Paths.get("articles"))
            .asSequence()
            .onEach { validateArticleName(it.fileName.toString()) }
            .map { toArticle(it, compiler) }
            .sortedWith(Comparator { a, b -> b.date.compareTo(a.date) })
            .toList()
    }

    fun links(): List<Category> {
        val articles = articles()

        val posts = articles.filter { it.type == LinkType.article }
        val video = articles.filter { it.type == LinkType.video }
        val slides = articles.filter { it.type == LinkType.slides }
        val webinars = articles.filter { it.type == LinkType.webinar }

        return listOf(posts, video, slides, webinars).map(::getCategory)
    }

    fun articles(): List<Article> {
        return _articles
    }

    fun scan(root: Path): List<Path> {
        val articles = mutableListOf<Path>()

        Files.walkFileTree(root, object : FileVisitor<Path> {
            override fun preVisitDirectory(dir: Path, attrs: BasicFileAttributes): FileVisitResult {
                // Skip excluded folders (folders which starts with dot)
                if (dir.isExcluded()) {
                    return FileVisitResult.SKIP_SUBTREE
                }

                return FileVisitResult.CONTINUE
            }

            override fun postVisitDirectory(dir: Path, exc: IOException?): FileVisitResult {
                return FileVisitResult.CONTINUE
            }

            override fun visitFile(file: Path, attrs: BasicFileAttributes): FileVisitResult {
                if (file.isExcluded()) {
                    return FileVisitResult.CONTINUE
                }

                if (file.fileName.toString().endsWith(".kts")) {
                    articles.add(file)
                }

                return FileVisitResult.CONTINUE
            }

            override fun visitFileFailed(file: Path, exc: IOException?): FileVisitResult {
                throw RuntimeException("visitFileFailed" + file.toAbsolutePath())
            }
        })

        return articles
    }


    fun generate() {
        // https://github.com/isagalaev/highlight.js
    }
}

private val dayFormatter: DateTimeFormatter = DateTimeFormatterBuilder()
    .appendText(MONTH_OF_YEAR, FULL)
    .appendLiteral(' ')
    .appendValue(YEAR, 4, 10, EXCEEDS_PAD)
    .toFormatter()

private fun getCategory(articles: List<Article>): Category {
    val groupByDate = articles.groupBy { it.date.format(dayFormatter) }

    val subcategories = groupByDate.map { (k, v) ->
        Subcategory(
            name = k,
            links = v
                .sortedBy { it.date }
                .map {
                    Link(
                        name = it.title,
                        desc = it.author,
                        href = "http://kotlin.link/articles/${it.filename}",
                        type = LinkType.blog,
                        tags = it.categories.toTypedArray()
                    )
                }.toMutableList()
        )
    }.toMutableList()

    return Category(name = articles[0].type.toView(), subcategories = subcategories)
}

private fun Path.isExcluded() = this.fileName.toString().startsWith(".")

private val parser = Parser.builder().build()
private val renderer = HtmlRenderer.builder().build()

private fun readFile(path: Path, compiler: ScriptCompiler): Article {
    return compiler.execute<Article>(Files.newInputStream(path))
}

private fun getFileName(path: Path): String {
    val name = path.fileName.toString().removeSuffix(".kts")

    val escaped = name
        .map { it.toInt() }
        .map { code ->
            // See html codes: 32 - space, 47 - slash, 58 - colon, 64 - at, 91 - opening bracket, 96 - grave accent
            // http://ascii-code.com/
            // whitelist approach instead?
            Char
            if ((code in 32..47) || (code in 58..64) || (code in 91..96) || (code in 123..255)) {
                '-'
            } else {
                code.toChar()
            }
        }
        .joinToString(separator = "")
        .replace(Regex("-$"), "") // Replace last dash in string with ''
        .replace(Regex("^-"), "") // Replace first dash in string with ''
        .replace(Regex("-+"), "-") // Replace multiple dashes with one dash

    return "${escaped}.html"
}

private fun toArticle(path: Path, compiler: ScriptCompiler): Article {
    println(path.toString())
    val article = readFile(path, compiler)
    val document = parser.parse(article.body)
    val html = renderer.render(document)

    return article.copy(
        description = html,
        body = html,
        filename = getFileName(path)
    )
}

private val invalid = listOf('\\', '/', ':', '*', '?', '"', '<', '>', '|')
private fun validateArticleName(name: String) {
    val symbol = invalid.find { symbol -> name.contains(symbol) }

    if (symbol != null) throw RuntimeException("File '$name' includes restricted symbol '$symbol'.")
}
