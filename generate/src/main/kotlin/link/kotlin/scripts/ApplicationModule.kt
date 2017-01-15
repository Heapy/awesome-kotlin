package link.kotlin.scripts

import com.google.inject.Binder
import com.google.inject.Module
import com.google.inject.Provides
import io.bootique.BQCoreModule
import io.bootique.config.ConfigurationFactory
import link.kotlin.scripts.commands.BuildCommand
import link.kotlin.scripts.commands.ReadmeCommand

class ApplicationModule : Module {
    override fun configure(binder: Binder) {
        arrayOf(
            BuildCommand::class,
            ReadmeCommand::class
        ).forEach {
            BQCoreModule
                .contributeCommands(binder)
                .addBinding()
                .to(it.java)
        }
    }

    @Provides
    fun createBotConfiguration(configurationFactory: ConfigurationFactory): LinkCheckerFactory {
        return configurationFactory.config(LinkCheckerFactory::class.java, "links")
    }
}
