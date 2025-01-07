package usecases.stars_job

import io.heapy.komok.tech.logging.Logger

class StarsJob {
    fun run() {
        log.info("Hello, StarsJob!")
    }

    private companion object : Logger()
}
