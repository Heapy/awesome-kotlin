package usecases.rss

import io.heapy.komok.tech.di.lib.Module
import usecases.articles.ArticlesModule

@Module
open class RssModule(
    private val articlesModule: ArticlesModule,
) {
    open val rssRoute by lazy {
        RssRoute(
            articleSource = articlesModule.articleSource,
            rssGenerator = rssGenerator,
        )
    }

    open val fullRssRoute by lazy {
        FullRssRoute(
            articleSource = articlesModule.articleSource,
            rssGenerator = rssGenerator,
        )
    }

    open val rssGenerator by lazy {
        RssGenerator()
    }
}
