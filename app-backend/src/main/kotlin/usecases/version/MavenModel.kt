package usecases.version

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import nl.adaptivity.xmlutil.serialization.XmlElement
import nl.adaptivity.xmlutil.serialization.XmlSerialName

object MavenModel {
    @Serializable
    @SerialName("metadata")
    data class MavenMetadata(
        @XmlSerialName("versioning")
        @XmlElement
        val versioning: Versioning,
        @XmlElement
        val groupId: String,
        @XmlElement
        val artifactId: String,
    )

    @Serializable
    data class Versioning(
        @XmlElement
        val latest: String,
        @XmlElement
        val release: String,
        @XmlElement
        @XmlSerialName("versions")
        val versions: Versions,
        @XmlElement
        val lastUpdated: String
    )

    @Serializable
    data class Versions(
        val version: List<String>
    )
}
