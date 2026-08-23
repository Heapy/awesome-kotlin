import io.heapy.komok.tech.di.lib.Module
import tools.jackson.dataformat.xml.XmlMapper
import tools.jackson.module.kotlin.kotlinModule

@Module
class XmlModule {
    val xmlMapper: XmlMapper by lazy {
        XmlMapper.builder()
            .addModule(kotlinModule { })
            .build()
    }
}
