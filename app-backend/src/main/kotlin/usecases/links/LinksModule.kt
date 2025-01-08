package usecases.links

import com.fasterxml.jackson.databind.json.JsonMapper
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.fasterxml.jackson.module.kotlin.kotlinModule
import infra.HttpClientModule
import io.heapy.komok.tech.di.lib.Module
import org.commonmark.ext.gfm.tables.TablesExtension
import org.commonmark.parser.Parser
import org.commonmark.renderer.html.HtmlRenderer
import usecases.articles.MarkdownRenderer
import usecases.github_trending.GithubTrendingLinkSourceModule

@Module
open class LinksModule(
    private val githubTrendingLinkSourceModule: GithubTrendingLinkSourceModule,
    private val httpClientModule: HttpClientModule,
) {
    open val linksDtoSource by lazy {
        LinksDtoSource(
            linksSource = linksSource,
        )
    }

    open val linksSource by lazy {
        LinksSource(
            githubTrendingLinkSource = githubTrendingLinkSourceModule.githubTrendingLinkSource,
            categoryProcessor = categoryProcessor,
        )
    }

    open val categoryProcessor by lazy {
        CategoryProcessor(
            linksProcessor = linksProcessor,
        )
    }

    open val objectMapper by lazy {
        JsonMapper
            .builder()
            .addModule(kotlinModule())
            .addModule(JavaTimeModule())
            .build()
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

    open val linksChecker by lazy {
        LinksChecker(
            httpClient = httpClientModule.httpClient,
        )
    }

    open val linksProcessor by lazy {
        val defaultLinksProcessor = DefaultLinksProcessor(
            githubConfig = githubTrendingLinkSourceModule.githubConfig,
            mapper = objectMapper,
            httpClient = httpClientModule.httpClient,
            linksChecker = linksChecker,
        )

        val markdownLinkProcessor = DescriptionMarkdownLinkProcessor(
            markdownRenderer = markdownRenderer
        )

        CombinedLinksProcessors(listOf(defaultLinksProcessor, markdownLinkProcessor))
    }

    open val route by lazy {
        LinksRoute(
            linksDtoSource = linksDtoSource,
        )
    }
}
