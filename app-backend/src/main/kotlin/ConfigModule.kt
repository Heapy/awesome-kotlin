import com.typesafe.config.Config
import com.typesafe.config.ConfigFactory
import config.dotenv
import di.bean

open class ConfigModule {
    open val overrides by bean {
        mapOf<String, String>()
    }

    open val dotenv by bean {
        dotenv()
    }

    open val config by bean<Config> {
        (overrides.get + dotenv.get).forEach {
            System.setProperty(it.key, it.value)
        }

        ConfigFactory.load()
    }
}
