import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import infra.config.ConfigModule
import infra.config.decode
import infra.lifecycle.AutoClosableModule
import io.heapy.komok.tech.di.lib.Module
import kotlinx.serialization.Serializable

@Module
class JdbcModule(
    private val configModule: ConfigModule,
    private val autoClosableModule: AutoClosableModule,
) {
    val dataSource by lazy {
        val dataSource = HikariDataSource(
            HikariConfig().also {
                it.dataSourceClassName = "org.postgresql.ds.PGSimpleDataSource"
                it.username = jdbcConfig.username
                it.password = jdbcConfig.password
                it.addDataSourceProperty("databaseName", jdbcConfig.databaseName)
                it.addDataSourceProperty("serverName", jdbcConfig.host)
                it.addDataSourceProperty("portNumber", jdbcConfig.port)
            }
        )

        autoClosableModule.addClosable(
            t = dataSource,
            close = HikariDataSource::close,
        )
    }

    val jdbcConfig by lazy<JdbcConfig> {
        configModule.decode("jdbc")
    }

    @Serializable
    data class JdbcConfig(
        val host: String,
        val port: String,
        val username: String,
        val password: String,
        val databaseName: String,
    )
}
