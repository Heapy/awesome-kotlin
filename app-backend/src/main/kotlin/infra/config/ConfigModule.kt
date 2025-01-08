package infra.config

import com.typesafe.config.Config
import com.typesafe.config.ConfigFactory
import io.heapy.komok.tech.config.dotenv.dotenv
import io.heapy.komok.tech.di.lib.Module
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.KSerializer
import kotlinx.serialization.hocon.Hocon

@Module
open class ConfigModule {
    open val overrides by lazy {
        mapOf<String, String>()
    }

    open val dotenv by lazy {
        dotenv()
    }

    open val env by lazy {
        buildMap {
            putAll(System.getenv())
            putAll(dotenv.properties)
            putAll(overrides)
        }
    }

    open val config: Config by lazy {
        env.forEach {
            System.setProperty(it.key, it.value)
        }

        ConfigFactory.load()
    }
}

@OptIn(ExperimentalSerializationApi::class)
fun <T> ConfigModule.decode(
    path: String,
    deserializer: KSerializer<T>,
): T {
    return Hocon.decodeFromConfig(deserializer, config.getConfig(path))
}
