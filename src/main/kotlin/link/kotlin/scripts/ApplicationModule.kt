package link.kotlin.scripts

import com.fasterxml.jackson.databind.json.JsonMapper
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.fasterxml.jackson.module.kotlin.kotlinModule
import io.heapy.komok.tech.di.lib.Module
import link.kotlin.scripts.model.ApplicationConfiguration
import link.kotlin.scripts.model.default
import link.kotlin.scripts.scripting.ScriptEvaluator
import link.kotlin.scripts.scripting.default
import link.kotlin.scripts.utils.Cache
import link.kotlin.scripts.utils.HttpClient
import link.kotlin.scripts.utils.callLogger
import link.kotlin.scripts.utils.default

@Module
open class ApplicationModule {
    open val objectMapper: JsonMapper by lazy {
        JsonMapper.builder()
            .addModule(kotlinModule { })
            .addModule(JavaTimeModule())
            .build()
    }

    open val httpClient by lazy {
        HttpClient.Companion.default()
    }

    open val applicationConfiguration by lazy {
        ApplicationConfiguration.Companion.default()
    }

    open val markdownRenderer by lazy {
        MarkdownRenderer.default()
    }

    open val cache by lazy {
        Cache.Companion.default(
            mapper = objectMapper,
            configuration = applicationConfiguration,
        )
    }

    open val scriptEvaluator by lazy {
        ScriptEvaluator.Companion.default(
            cache = cache,
        )
    }

    open val kotlinVersionFetcher by lazy {
        KotlinVersionFetcher.default(
            httpClient = httpClient,
        )
    }

    open val sitemapGenerator by lazy {
        SitemapGenerator(
            configuration = applicationConfiguration,
        )
    }

    open val pagesGenerator by lazy {
        PagesGenerator.default()
    }

    open val rssGenerator by lazy {
        RssGenerator.default()
    }

    open val siteGenerator by lazy {
        SiteGenerator.default(
            mapper = objectMapper,
            kotlinVersionFetcher = kotlinVersionFetcher,
            sitemapGenerator = sitemapGenerator,
            pagesGenerator = pagesGenerator,
            rssGenerator = rssGenerator,
        )
    }

    open val linksChecker by lazy {
        LinksChecker.default(
            httpClient = httpClient,
        )
    }

    open val linksProcessor by lazy {
        LinksProcessor.default(
            mapper = objectMapper,
            httpClient = httpClient,
            linksChecker = linksChecker,
            configuration = applicationConfiguration,
            markdownRenderer = markdownRenderer,
        )
    }

    open val categoryProcessor by lazy {
        CategoryProcessor.default(
            linksProcessor = linksProcessor,
        )
    }

    open val githubTrending by lazy {
        GithubTrending.default(
            cache = cache,
        )
    }
    
    open val readmeGenerator by lazy {
        MarkdownReadmeGenerator()
    }

    open val linksSource by lazy {
        LinksSource.default(
            scriptEvaluator = scriptEvaluator,
            githubTrending = githubTrending,
            categoryProcessor = categoryProcessor,
        )
    }

    open val articlesProcessor by lazy {
        ArticlesProcessor.default(
            markdownRenderer = markdownRenderer,
        )
    }

    open val articlesSource by lazy {
        ArticlesSource.default(
            scriptEvaluator = scriptEvaluator,
            articlesProcessor = articlesProcessor,
        )
    }

    open val awesomeKotlinGenerator by lazy {
        val implementation = DefaultAwesomeKotlinGenerator(
            linksSource = linksSource,
            articlesSource = articlesSource,
            readmeGenerator = readmeGenerator,
            siteGenerator = siteGenerator,
        )

        callLogger<AwesomeKotlinGenerator>(implementation)
    }
}
