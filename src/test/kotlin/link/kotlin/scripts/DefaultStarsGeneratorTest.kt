package link.kotlin.scripts

import link.kotlin.scripts.utils.parseInstant
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class DefaultStarsGeneratorTest {

    @Test
    fun parseDate() {
        assertEquals(21, parseInstant("2017-04-21T10:33:23Z").dayOfMonth)
        assertEquals(10, parseInstant("2017-04-21T10:33:23Z").hour)
    }
}
