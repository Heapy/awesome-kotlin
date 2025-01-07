package infra.uuid

interface UuidSource {
    fun generateUuid(): String
}
