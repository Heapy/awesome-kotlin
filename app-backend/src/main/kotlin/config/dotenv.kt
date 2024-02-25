package config

import java.nio.file.Path
import java.nio.file.Paths
import kotlin.io.path.exists
import kotlin.io.path.readLines

fun dotenv(): Map<String, String> {
    return resolveEnv(Paths.get(".").toAbsolutePath().normalize())
        ?.let { envPath ->
            envPath.readLines()
                .filter(String::isNotBlank)
                .filter { !it.startsWith("#") }
                .associate {
                    val split = it.split("=", limit = 2)
                    if (split.size != 2) error("Line syntax: key=value, got: $it")
                    split[0].trim() to split[1].trim()
                }
        }
        ?: emptyMap()
}

private fun resolveEnv(root: Path): Path? {
    return root.resolve(".env").let { envPath ->
        if (envPath.exists()) {
            envPath
        } else {
            if (envPath.parent == null) {
                null
            } else if (envPath.parent.resolve(".git").exists()) {
                null
            } else {
                resolveEnv(root = root.parent)
            }
        }
    }
}
