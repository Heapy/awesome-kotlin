import di.bean
import org.flywaydb.core.Flyway

open class FlywayModule(
    private val jdbcModule: JdbcModule,
) {
    open val flyway by bean<Flyway> {
        Flyway.configure()
            .locations("classpath:db/migration/main")
            .dataSource(jdbcModule.dataSource.get)
            .load()
    }
}
