package utils

fun AutoCloseable.close(block: () -> Unit) {
    try {
        this.use {}
    } catch (e: Exception) {
        logger(block)
            .error("Error while closing resource: ${e.message}", e)
    }
}
