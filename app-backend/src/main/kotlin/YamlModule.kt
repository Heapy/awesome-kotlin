import com.charleskorn.kaml.Yaml
import di.bean

open class YamlModule {
    open val yaml by bean {
        Yaml.default
    }
}
