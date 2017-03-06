package link.kotlin.scripts.commands

import com.google.inject.Inject
import com.google.inject.Provider
import io.bootique.cli.Cli
import io.bootique.command.CommandOutcome
import io.bootique.command.CommandWithMetadata
import io.bootique.meta.application.CommandMetadata
import link.kotlin.scripts.ReadmeGenerator
import java.nio.file.Files.write
import java.nio.file.Paths

class ReadmeCommand @Inject constructor(
    private val readmeGeneratorProvider: Provider<ReadmeGenerator>
) : CommandWithMetadata(commandMetadata) {

    override fun run(cli: Cli): CommandOutcome {
        val readme = readmeGeneratorProvider.get().generate()
        write(Paths.get("README.md"), readme.toByteArray())
        return CommandOutcome.succeeded()
    }
}

private val commandMetadata = CommandMetadata.builder(ReadmeCommand::class.java)
    .description("Generate README.md file.")
    .build()

