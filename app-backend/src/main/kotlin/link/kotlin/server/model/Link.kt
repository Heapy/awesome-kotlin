package link.kotlin.server.model

import com.fasterxml.jackson.annotation.JsonSubTypes
import com.fasterxml.jackson.annotation.JsonTypeInfo
import com.fasterxml.jackson.annotation.JsonTypeInfo.As
import com.fasterxml.jackson.annotation.JsonTypeInfo.Id

@JsonTypeInfo(use = Id.NAME, include = As.PROPERTY)
@JsonSubTypes(
    JsonSubTypes.Type(value = Github::class, name = "Github"),
    JsonSubTypes.Type(value = BitBucket::class, name = "BitBucket"),
    JsonSubTypes.Type(value = Gitlab::class, name = "Gitlab"),
    JsonSubTypes.Type(value = Web::class, name = "Web"),
    JsonSubTypes.Type(value = Book::class, name = "Book"),
    JsonSubTypes.Type(value = Course::class, name = "Course"),
    JsonSubTypes.Type(value = UserGroup::class, name = "UserGroup"),
    JsonSubTypes.Type(value = Video::class, name = "Video")
)
sealed class Link {
//    abstract val rating: Int
//    abstract val lockVersion: Int
//    abstract val updated: String
//    abstract val archived: Boolean
}

data class Web(
    val title: String,
    val url: String,
    val description: String,
    val tags: List<String>,
    val languages: List<Language>
) : Link()

data class Book(
    val name: String,
    val url: String,
    val free: Boolean,
    val description: String,
    val tags: List<String>,
    val platforms: List<KotlinPlatform>,
    val versions: List<KotlinVersion>,
    val languages: List<Language>
) : Link()

data class Course(
    val name: String,
    val url: String,
    val free: Boolean,
    val description: String,
    val tags: List<String>,
    val platforms: List<KotlinPlatform>,
    val versions: List<KotlinVersion>,
    val languages: List<Language>
) : Link()

data class UserGroup(
    val name: String,
    val url: String,
    /**
     * Usually country, or country and city:
     * `Belarus` or `Belarus, Minsk`
     */
    val location: String,
    val tags: List<String>
) : Link()

/**
 * Inspiration: https://awesometalks.party/
 */
data class Video(
    val title: String,
    val url: String,
    val description: String,
    val tags: List<String>,
    val languages: List<Language>,
    val speakers: List<Kotliner>
) : Link()

data class Github(
    /**
     * Part of url, for:
     * `https://github.com/JetBrains/Kotlin`
     * it will be:
     * `JetBrains/Kotlin`
     */
    val name: String,
    val description: String,
    val tags: List<String>,
    val platforms: List<KotlinPlatform>,
    val versions: List<KotlinVersion>
) : Link()

data class BitBucket(
    /**
     * Same as for [Github.name]
     */
    val name: String,
    val description: String,
    val tags: List<String>,
    val platforms: List<KotlinPlatform>,
    val versions: List<KotlinVersion>
) : Link()

data class Gitlab(
    /**
     * Since gitlab repos can be self hosted, we need full url to repo.
     */
    val url: String,
    val description: String,
    val tags: List<String>,
    val platforms: List<KotlinPlatform>,
    val versions: List<KotlinVersion>
) : Link()

val Link.url: String
    get() {
        return when (this) {
            is Gitlab -> url
            is Web -> url
            is Book -> url
            is Course -> url
            is UserGroup -> url
            is Video -> url
            is Github -> "https://github.com/$name"
            is BitBucket -> "https://bitbucket.org/$name"
        }
    }
