package usecases.robots_txt

import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.server.config.*
import io.ktor.server.testing.*
import infra.ktor.installRoute
import org.junit.jupiter.api.Assertions.assertEquals
import types.UnitTest
import usecases.web.WebApplicationContext

class RobotsTxtRouteTest {
    @UnitTest
    fun `install should respond with robots txt`() {
        val robotsTxtModule = createRobotsTxtModule {
            webModule {
                externalWebApplicationContext {
                    WebApplicationContext(
                        host = "domain.com",
                        port = 80,
                        protocol = "http",
                    )
                }
            }
        }

        val route = robotsTxtModule.route

        testApplication {
            environment {
                config = MapApplicationConfig()
            }

            routing {
                route.installRoute()
            }

            val response = client.get("/robots.txt")

            assertEquals(
                """
                User-agent: *
                Sitemap: http://domain.com/sitemap.xml
                """.trimIndent(),
                response.bodyAsText()
            )
        }
    }
}
