package server

import infra.metrics.MetricsModule
import io.heapy.komok.tech.di.lib.Module
import usecases.articles.ArticlesModule
import usecases.github.GithubModule
import usecases.healthcheck.HealthcheckModule
import usecases.kug.KugModule
import usecases.links.LinksModule
import usecases.readme.ReadmeModule
import usecases.robots_txt.RobotsTxtModule
import usecases.rss.RssModule
import usecases.signup.LoginModule
import usecases.signup.RegisterModule
import usecases.sitemap.SitemapModule
import usecases.version.KotlinVersionModule

@Module
open class KtorRoutesModule(
    private val githubModule: GithubModule,
    private val healthcheckModule: HealthcheckModule,
    private val loginModule: LoginModule,
    private val registerModule: RegisterModule,
    private val linksModule: LinksModule,
    private val kugModule: KugModule,
    private val metricsModule: MetricsModule,
    private val rssModule: RssModule,
    private val kotlinVersionModule: KotlinVersionModule,
    private val robotsTxtModule: RobotsTxtModule,
    private val sitemapModule: SitemapModule,
    private val readmeModule: ReadmeModule,
    private val articlesModule: ArticlesModule,
) {
    open val routes by lazy {
        listOf(
            githubModule.githubRedirectRoute,
            githubModule.githubCallbackRoute,

            healthcheckModule.route,

            metricsModule.route,

            loginModule.route,
            registerModule.route,

            linksModule.route,
            kugModule.updateKugsRoute,

            rssModule.rssRoute,
            rssModule.fullRssRoute,

            kotlinVersionModule.route,

            robotsTxtModule.route,

            sitemapModule.route,

            readmeModule.route,

            articlesModule.pagesRoute,
            articlesModule.pagesIndexRoute,
        )
    }
}
