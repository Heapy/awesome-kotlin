package link.kotlin.scripts.utils

import java.nio.file.Files
import java.nio.file.Paths
import java.nio.file.StandardCopyOption.REPLACE_EXISTING

object CopyTask {
    fun copy(mapping: Map<String, String>) {
        mapping.forEach { (from, to) ->
            Files.copy(Paths.get(from), Paths.get(to), REPLACE_EXISTING)
        }
    }
}
