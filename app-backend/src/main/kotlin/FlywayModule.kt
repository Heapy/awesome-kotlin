import io.heapy.komok.tech.di.lib.Module
import org.flywaydb.core.Flyway

@Module
class FlywayModule(
    private val jdbcModule: JdbcModule,
) {
    val flyway: Flyway by lazy {
        Flyway.configure()
            .locations("classpath:db/migration/main")
            .dataSource(jdbcModule.dataSource)
            .load()
    }
}
