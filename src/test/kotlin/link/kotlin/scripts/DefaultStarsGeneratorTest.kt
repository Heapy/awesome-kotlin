package link.kotlin.scripts

import org.junit.Assert.assertEquals
import org.junit.Test

class DefaultStarsGeneratorTest {

    @Test fun parseDate() {
        assertEquals(21, parseInstant("2017-04-21T10:33:23Z").dayOfMonth)
        assertEquals(10, parseInstant("2017-04-21T10:33:23Z").hour)
    }
}
