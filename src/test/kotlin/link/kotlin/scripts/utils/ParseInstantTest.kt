package link.kotlin.scripts.utils

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class ParseInstantTest {
    @Test
    fun parseDate() {
        Assertions.assertEquals(21, parseInstant("2017-04-21T10:33:23Z").dayOfMonth)
        Assertions.assertEquals(10, parseInstant("2017-04-21T10:33:23Z").hour)
    }
}
