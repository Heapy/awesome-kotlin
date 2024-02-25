import com.fasterxml.jackson.dataformat.xml.XmlMapper
import com.fasterxml.jackson.module.kotlin.kotlinModule
import di.bean

open class XmlModule {
    open val xmlMapper by bean {
        XmlMapper().apply {
            registerModule(kotlinModule { })
        }
    }
}
