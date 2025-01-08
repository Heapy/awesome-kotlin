@file:JvmName("Application")

fun main() {
    createApplicationModule {}
        .use(ApplicationModule::run)
}
