package link.kotlin.scripts.commands

import com.google.inject.Inject
import com.google.inject.Provider
import io.bootique.cli.Cli
import io.bootique.command.CommandOutcome
import io.bootique.command.CommandWithMetadata
import io.bootique.meta.application.CommandMetadata
import link.kotlin.scripts.LinkChecker
import link.kotlin.scripts.data.allLinks

class BuildCommand @Inject constructor(
    private val linkCheckerProvider: Provider<LinkChecker>
) : CommandWithMetadata(commandMetadata) {

    override fun run(cli: Cli): CommandOutcome {
        // stars + webpack + rss + pages + sitemap
        linkCheckerProvider.get().check(allLinks)

        return CommandOutcome.succeeded()
    }
}

private val commandMetadata = CommandMetadata.builder(BuildCommand::class.java)
    .description("Runs link checker, stars fetcher, sitemap generator, rss generator and generates articles page.")
    .build()

