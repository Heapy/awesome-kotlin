package infra.uuid

import io.heapy.komok.tech.di.lib.Module

@Module
open class UuidModule {
    open val uuidSource: UuidSource by lazy {
        UuidV4Source()
    }
}
