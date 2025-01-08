package infra.db

import io.heapy.komok.tech.di.lib.Module
import org.jooq.SQLDialect
import org.jooq.impl.DSL

@Module
open class JooqModule(
    private val jdbcModule: JdbcModule,
) {
    open val dslContext by lazy {
        System.setProperty("org.jooq.no-logo", "true")
        System.setProperty("org.jooq.no-tips", "true")
        DSL.using(jdbcModule.dataSource, SQLDialect.POSTGRES)
    }
}
