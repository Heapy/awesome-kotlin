@file:JvmName("Backend")

package link.kotlin.backend

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.dataformat.xml.XmlMapper
import com.fasterxml.jackson.module.kotlin.KotlinModule
import com.fasterxml.jackson.module.kotlin.registerKotlinModule
import com.typesafe.config.Config
import com.typesafe.config.ConfigFactory
import io.github.config4k.extract
import io.undertow.Undertow
import link.kotlin.backend.services.createHttpClient
import org.koin.Logger.slf4jLogger
import org.koin.core.KoinApplication
import org.koin.dsl.module
import kotlin.concurrent.thread

fun main() {
    val mainModule = module {
        single { ObjectMapper().registerKotlinModule() }
        single { XmlMapper().also { it.registerModule(KotlinModule()) } }
        single { createHttpClient() }
        single { ConfigFactory.load().resolve() }
        single { get<Config>().extract<ApplicationConfiguration>() }
        single { createRootHandler() }
    }

    val koin = KoinApplication.create().run {
        modules(mainModule)
        slf4jLogger()
        koin
    }

    val undertow = Undertow.builder()
        .addHttpListener(8080, "0.0.0.0", koin.get())
        .build()

    Runtime.getRuntime().addShutdownHook(thread(start = false) {
        logger<Undertow>().info("Undertow Shutdown called...")
        undertow.stop()
        logger<Undertow>().info("Undertow Shutdown done...")
    })

    undertow.start()
}

