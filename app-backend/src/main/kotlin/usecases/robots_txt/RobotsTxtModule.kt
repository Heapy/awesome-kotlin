package usecases.robots_txt

import io.heapy.komok.tech.di.lib.Module
import usecases.web.WebModule

@Module
open class RobotsTxtModule(
    private val webModule: WebModule,
) {
    val route by lazy {
        RobotsTxtRoute(
            externalWebApplicationContext = webModule.externalWebApplicationContext,
        )
    }
}
