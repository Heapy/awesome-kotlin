package link.kotlin.server.model

data class Company(
    val name: String,
    val url: String,
    val description: String,
    val platforms: List<KotlinPlatform>
)

data class Kotliner(
    val id: String,
    val name: String,
    val url: String,
    val description: String
)

data class KotlinPosition(
    val id: String,
    val company: Long,
    val title: String,
    val description: String
)
