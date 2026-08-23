@file:JvmName("Application")

suspend fun main() {
    createApplicationModule {}
        .use { module -> module.run() }
}
