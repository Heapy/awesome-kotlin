import io.heapy.komok.tech.di.delegate.bean
import org.flywaydb.core.Flyway

open class FlywayModule(
    private val jdbcModule: JdbcModule,
) {
    open val flyway by bean<Flyway> {
        Flyway.configure()
            .locations("classpath:db/migration/main")
            .dataSource(jdbcModule.dataSource.value)
            .load()
    }
}
