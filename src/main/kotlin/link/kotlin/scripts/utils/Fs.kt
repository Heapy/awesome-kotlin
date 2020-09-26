package link.kotlin.scripts.utils

import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths
import java.nio.file.StandardOpenOption

fun writeFile(path: String, data: String) {
    Files.write(
        Paths.get(path),
        data.toByteArray(),
        StandardOpenOption.CREATE,
        StandardOpenOption.TRUNCATE_EXISTING
    )
}

fun writeFile(path: Path, data: String) {
    Files.write(
        path,
        data.toByteArray(),
        StandardOpenOption.CREATE,
        StandardOpenOption.TRUNCATE_EXISTING
    )
}
