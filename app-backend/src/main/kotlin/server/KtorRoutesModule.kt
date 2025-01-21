package server

import infra.db.JooqModule
import infra.ktor.easy.EasyKtorRoute
import infra.ktor.easy.EasyKtorRouteAdapter
import infra.ktor.KtorRoute
import infra.ktor.auth.KtorAuthModule
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
    private val jooqModule: JooqModule,
    private val ktorAuthModule: KtorAuthModule,
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
    open val routes: List<KtorRoute> by lazy {
        easyRoutes
            .map { route ->
                EasyKtorRouteAdapter(
                    transactionProvider = jooqModule.transactionProvider,
                    ktorAuth = ktorAuthModule.ktorAuth,
                    route = route,
                )
            }
    }

    open val basicRoutes: List<KtorRoute> by lazy {
        listOf(
            kotlinVersionModule.route,
            linksModule.route,

            rssModule.rssRoute,
            rssModule.fullRssRoute,

            robotsTxtModule.route,

            sitemapModule.route,

            readmeModule.route,

            articlesModule.pagesRoute,
            articlesModule.pagesIndexRoute,

            kugModule.updateKugsRoute,
        )
    }

    open val easyRoutes: List<EasyKtorRoute> by lazy {
        listOf(
            githubModule.githubRedirectRoute,
            githubModule.githubCallbackRoute,

            healthcheckModule.route,

            metricsModule.route,

            loginModule.route,
            registerModule.route,

            kugModule.getKugRoute,
        )
    }
}
