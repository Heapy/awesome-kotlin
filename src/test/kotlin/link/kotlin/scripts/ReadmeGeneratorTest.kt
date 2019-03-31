package link.kotlin.scripts

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class ReadmeGeneratorTest {
    @Test
    fun testNameNormalizer() {
        assertEquals("-", normalizeName("--- ---"))
        assertEquals("-", normalizeName("-"))
        assertEquals("-", normalizeName("\\"))
        assertEquals("-", normalizeName("-,-"))
    }
}
