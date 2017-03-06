package link.kotlin.scripts.commands

import com.google.inject.Inject
import io.bootique.cli.Cli
import io.bootique.command.CommandOutcome
import io.bootique.command.CommandWithMetadata
import io.bootique.meta.application.CommandMetadata

class ReadmeCommand @Inject constructor(
) : CommandWithMetadata(commandMetadata) {

    override fun run(cli: Cli): CommandOutcome {
        return CommandOutcome.succeeded()
    }
}

private val commandMetadata = CommandMetadata.builder(ReadmeCommand::class.java)
    .description("Generate README.md file.")
    .build()

