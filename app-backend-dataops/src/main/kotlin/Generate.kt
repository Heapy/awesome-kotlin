import org.flywaydb.core.Flyway
import org.jooq.codegen.GenerationTool
import org.jooq.meta.jaxb.*
import org.jooq.meta.jaxb.Target
import org.postgresql.ds.PGSimpleDataSource
import java.util.*

fun main() {
    drop()
    flyway()
    jooq()
}

fun drop() {
    PGSimpleDataSource()
        .apply {
            setURL("jdbc:postgresql://localhost:9567/awesome_kotlin")
            user = "awesome_kotlin"
            password = "awesome_kotlin"
        }
        .connection
        .use { connection ->
            connection.createStatement().use { statement ->
                statement.execute("DROP SCHEMA public CASCADE;")
                statement.execute("CREATE SCHEMA public;")
            }
        }
}

fun flyway() {
    Flyway
        .configure()
        .locations("filesystem:./app-backend/src/main/resources/infra/db/migration/main")
        .dataSource(
            "jdbc:postgresql://localhost:9567/awesome_kotlin",
            "awesome_kotlin",
            "awesome_kotlin"
        )
        .load()
        .migrate()
}

fun jooq() {
    GenerationTool.generate(Configuration().apply {
        jdbc = Jdbc().apply {
            driver = "org.postgresql.Driver"
            url = "jdbc:postgresql://localhost:9567/awesome_kotlin"
            user = "awesome_kotlin"
            password = "awesome_kotlin"
        }

        generator = Generator().apply {
            name = "org.jooq.codegen.KotlinGenerator"
            database = Database().apply {
                name = "org.jooq.meta.postgres.PostgresDatabase"
                includes = ".*"
                inputSchema = "public"
            }

            generate = Generate().apply {
                isDaos = true
                isPojosAsKotlinDataClasses = true
            }

            target = Target().apply {
                packageName = "jooq.main"
                directory = "./app-backend/src/main/kotlin"
                locale = Locale.ROOT.toLanguageTag()
            }
        }
    })
}
