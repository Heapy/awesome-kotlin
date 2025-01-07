package infra.uuid

import java.util.UUID

class UuidV4Source : UuidSource {
    override fun generateUuid(): String = UUID.randomUUID().toString()
}
