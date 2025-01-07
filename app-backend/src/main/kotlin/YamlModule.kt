import com.charleskorn.kaml.Yaml
import io.heapy.komok.tech.di.delegate.bean

open class YamlModule {
    open val yaml by bean {
        Yaml.default
    }
}
