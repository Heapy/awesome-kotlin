package link.kotlin.scripts

import io.bootique.Bootique

fun main(args: Array<String>) {
    Bootique.app(*args)
        .module(ApplicationModule::class.java)
        .autoLoadModules()
        .run()
}
