import com.charleskorn.kaml.Yaml
import io.heapy.komok.tech.di.lib.Module

@Module
class YamlModule {
    val yaml by lazy {
        Yaml.default
    }
}
