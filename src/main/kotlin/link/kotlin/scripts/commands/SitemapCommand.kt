package link.kotlin.scripts.commands

import com.google.inject.Inject
import com.google.inject.Provider
import io.bootique.cli.Cli
import io.bootique.command.CommandOutcome
import io.bootique.command.CommandWithMetadata
import io.bootique.meta.application.CommandMetadata
import link.kotlin.scripts.SitemapGenerator
import java.nio.file.Files.createDirectory
import java.nio.file.Files.write
import java.nio.file.Paths
import java.nio.file.StandardOpenOption

class SitemapCommand @Inject constructor(
    private val sitemapGeneratorProvider: Provider<SitemapGenerator>
) : CommandWithMetadata(commandMetadata) {

    override fun run(cli: Cli): CommandOutcome {
        val sitemap = sitemapGeneratorProvider.get().generate()
        createDirectory(Paths.get("./dist"))
        write(Paths.get("./dist/sitemap.xml"), sitemap.toByteArray(), StandardOpenOption.CREATE)
        return CommandOutcome.succeeded()
    }
}

private val commandMetadata = CommandMetadata.builder(SitemapCommand::class.java)
    .description("Generate sitemap.xml file.")
    .build()

