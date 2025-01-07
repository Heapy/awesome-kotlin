package infra.serialization

import io.heapy.komok.tech.di.lib.Module
import nl.adaptivity.xmlutil.serialization.XML

@Module
open class XmlModule {
    open val xml: XML by lazy {
        XML {}
    }
}
