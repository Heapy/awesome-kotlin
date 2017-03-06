package link.kotlin.scripts

import com.google.inject.Binder
import com.google.inject.Module
import com.google.inject.Provides
import com.google.inject.Singleton
import io.bootique.BQCoreModule
import io.bootique.config.ConfigurationFactory
import link.kotlin.scripts.commands.BuildCommand
import link.kotlin.scripts.commands.ReadmeCommand
import link.kotlin.scripts.commands.SitemapCommand
import link.kotlin.scripts.data.allLinks

class ApplicationModule : Module {
    override fun configure(binder: Binder) {
        arrayOf(
            BuildCommand::class,
            SitemapCommand::class,
            ReadmeCommand::class
        ).forEach {
            BQCoreModule
                .contributeCommands(binder)
                .addBinding()
                .to(it.java)
        }
    }

    @Singleton
    @Provides
    fun createBotConfiguration(configurationFactory: ConfigurationFactory): LinkCheckerFactory {
        return configurationFactory.config(LinkCheckerFactory::class.java, "links")
    }

    @Singleton
    @Provides
    fun createReadmeGenerator(): ReadmeGenerator = DefaultReadmeGenerator(allLinks)

    @Singleton
    @Provides
    fun createSitemapGenerator(): SitemapGenerator = DefaultSitemapGenerator(allLinks)
}
