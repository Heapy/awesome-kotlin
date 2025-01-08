package infra.db

import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import infra.config.ConfigModule
import infra.config.decode
import io.heapy.komok.tech.di.lib.Module
import kotlinx.serialization.Serializable
import infra.lifecycle.AutoClosableModule

@Module
open class JdbcModule(
    private val configModule: ConfigModule,
    private val autoClosableModule: AutoClosableModule,
) {
    open val dataSource by lazy {
        val dataSource = HikariDataSource(
            HikariConfig().also {
                it.poolName = "Awesome Kotlin Pool"
                it.dataSourceClassName = "org.postgresql.ds.PGSimpleDataSource"
                it.username = "awesome_kotlin"
                it.password = "awesome_kotlin"
                it.addDataSourceProperty("databaseName", "awesome_kotlin")
                it.addDataSourceProperty("serverName", jdbcConfig.host)
                it.addDataSourceProperty("portNumber", jdbcConfig.port)
            }
        )

        autoClosableModule.addClosable(
            t = dataSource,
            close = HikariDataSource::close,
        )

        dataSource
    }

    open val jdbcConfig: JdbcConfig by lazy {
        configModule.decode("jdbc", JdbcConfig.serializer())
    }

    @Serializable
    data class JdbcConfig(
        val host: String,
        val port: String,
    )
}
