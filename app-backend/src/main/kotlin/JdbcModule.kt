import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import di.bean
import kotlinx.serialization.Serializable
import kotlinx.serialization.hocon.Hocon
import kotlinx.serialization.hocon.decodeFromConfig
import utils.close

open class JdbcModule(
    private val configModule: ConfigModule,
) : AutoCloseable {
    open val dataSource by bean {
        HikariDataSource(
            HikariConfig().also {
                it.dataSourceClassName = "org.postgresql.ds.PGSimpleDataSource"
                it.username = "awesome_kotlin"
                it.password = "awesome_kotlin"
                it.addDataSourceProperty("databaseName", "awesome_kotlin")
                it.addDataSourceProperty("serverName", jdbcConfig.get.host)
                it.addDataSourceProperty("portNumber", jdbcConfig.get.port)
            }
        )
    }

    open val jdbcConfig by bean<JdbcConfig> {
        Hocon.decodeFromConfig(configModule.config.get.getConfig("jdbc"))
    }

    @Serializable
    data class JdbcConfig(
        val host: String,
        val port: String,
    )

    override fun close() {
        if (dataSource.isInitialized) dataSource.get.close {}
    }
}
