package usecases.readme

import io.heapy.komok.tech.di.lib.Module
import usecases.links.LinksModule

@Module
open class ReadmeModule(
    private val linksModule: LinksModule,
) {
    open val readmeGenerator by lazy {
        ReadmeGenerator()
    }

    open val route by lazy {
        ReadmeRoute(
            readmeGenerator = readmeGenerator,
            linksSource = linksModule.linksSource,
        )
    }
}
