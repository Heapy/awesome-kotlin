package link.kotlin.server.model

enum class KotlinPlatform {
    /**
     * Kotlin/Native target.
     * For iOS specif libraries, please use [IOS].
     */
    Native,
    /**
     * Libraries and Projects that dependent on Android APIs.
     * For libraries that supposed to used in Android apps,
     * but doesn't depend on Android, please use [JVM].
     */
    Android,
    /**
     * Libraries and Projects that dependent on iOS APIs.
     */
    IOS,
    /**
     * Generic JVM libraries and projects.
     */
    JVM,
    /**
     * Libraries for Kotlin/JS, both frontend and backend.
     */
    JS,
    /**
     * Multi-Platform projects. Please, also define supported targets, like:
     * [JVM], [JS]
     */
    MPP
}
