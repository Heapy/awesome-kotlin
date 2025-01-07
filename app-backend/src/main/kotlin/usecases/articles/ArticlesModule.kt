package usecases.articles

import io.heapy.komok.tech.di.lib.Module
import org.commonmark.ext.gfm.tables.TablesExtension
import org.commonmark.parser.Parser
import org.commonmark.renderer.html.HtmlRenderer

@Module
open class ArticlesModule {
    open val articleSource by lazy {
        ArticleSource(
            articlesProcessor = articlesProcessor,
        )
    }

    open val markdownRenderer by lazy {
        val extensions = listOf(TablesExtension.create())
        val parser = Parser.builder().extensions(extensions).build()
        val renderer = HtmlRenderer.builder().extensions(extensions).build()

        MarkdownRenderer(
            parser = parser,
            renderer = renderer
        )
    }

    open val articlesProcessor by lazy {
        ArticlesProcessor(
            markdownRenderer = markdownRenderer,
        )
    }

    open val pagesGenerator by lazy {
        PagesGenerator()
    }

    open val pagesIndexRoute by lazy {
        PagesIndexRoute(
            articlesSource = articleSource,
            pagesGenerator = pagesGenerator,
        )
    }

    open val pagesRoute by lazy {
        PagesRoute(
            articlesSource = articleSource,
            pagesGenerator = pagesGenerator,
        )
    }
}
