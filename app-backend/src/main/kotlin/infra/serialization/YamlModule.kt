package infra.serialization

import com.charleskorn.kaml.Yaml
import io.heapy.komok.tech.di.lib.Module

@Module
open class YamlModule {
    open val yaml by lazy {
        Yaml.default
    }
}
