package link.kotlin.scripts

import link.kotlin.scripts.ArticleFeature.highlightjs
import link.kotlin.scripts.model.LanguageCodes
import link.kotlin.scripts.model.LanguageCodes.EN
import link.kotlin.scripts.model.Link
import link.kotlin.scripts.utils.ScriptCompiler
import link.kotlin.scripts.utils.logger
import org.commonmark.ext.gfm.tables.TablesExtension
import org.commonmark.parser.Parser
import org.commonmark.renderer.html.HtmlRenderer
import org.slf4j.Logger
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
import java.util.Locale

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
class Articles(
    private val compiler: ScriptCompiler
) {
    private val _articles by lazy {
        this
            .scan(Paths.get("articles"))
            .asSequence()
            .onEach { validateArticleName(it.fileName.toString()) }
            .map { toArticle(it, compiler, LOGGER) }
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

    companion object {
        private val LOGGER = logger<Articles>()
    }
}

private val dayFormatter: DateTimeFormatter = DateTimeFormatterBuilder()
    .appendText(MONTH_OF_YEAR, FULL)
    .appendLiteral(' ')
    .appendValue(YEAR, 4, 10, EXCEEDS_PAD)
    .toFormatter()
    .withLocale(Locale.US)

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
                        platforms = arrayOf(),
                        tags = it.categories.toTypedArray()
                    )
                }.toMutableList()
        )
    }.toMutableList()

    return Category(name = articles[0].type.toView(), subcategories = subcategories)
}

private fun Path.isExcluded() = this.fileName.toString().startsWith(".")

var extensions = listOf(TablesExtension.create())
private val parser = Parser.builder().extensions(extensions).build()
private val renderer = HtmlRenderer.builder().extensions(extensions).build()

private fun readFile(path: Path, compiler: ScriptCompiler): Article {
    return compiler.execute<Article>(Files.newInputStream(path))
}

private fun getFileName(path: Path): String {
    val name = path.fileName.toString().removeSuffix(".kts")

    val escaped = name
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

private fun toArticle(path: Path, compiler: ScriptCompiler, logger: Logger): Article {
    logger.info("Processing script: $path.")
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
