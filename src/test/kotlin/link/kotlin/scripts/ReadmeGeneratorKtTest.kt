package link.kotlin.scripts

import org.junit.Assert.assertEquals
import org.junit.Test

internal class ReadmeGeneratorKtTest {
    @Test fun testNameNormalizer() {
        assertEquals("-", normalizeName("--- ---"))
        assertEquals("-", normalizeName("-"))
        assertEquals("-", normalizeName("\\"))
        assertEquals("-", normalizeName("-,-"))
    }
}