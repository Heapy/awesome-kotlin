package infra.ktor.features

import io.ktor.http.*
import io.ktor.http.content.*
import io.ktor.server.application.*
import io.ktor.server.plugins.cachingheaders.*
import infra.ktor.KtorFeature
import kotlin.time.Duration.Companion.days

class CachingFeature : KtorFeature {
    override fun Application.install() {
        install(CachingHeaders) {
            options { _, outgoingContent ->
                when (outgoingContent.contentType?.withoutParameters()) {
                    ContentType.Text.CSS -> CachingOptions(
                        CacheControl.MaxAge(
                            maxAgeSeconds = 365.days
                                .inWholeSeconds
                                .toInt()
                        )
                    )
                    ContentType.Image.Any -> CachingOptions(
                        CacheControl.MaxAge(
                            maxAgeSeconds = 365.days
                                .inWholeSeconds
                                .toInt()
                        )
                    )
                    ContentType.Application.JavaScript -> CachingOptions(
                        CacheControl.MaxAge(
                            maxAgeSeconds = 365.days
                                .inWholeSeconds
                                .toInt()
                        )
                    )

                    else -> null
                }
            }
        }
    }
}
