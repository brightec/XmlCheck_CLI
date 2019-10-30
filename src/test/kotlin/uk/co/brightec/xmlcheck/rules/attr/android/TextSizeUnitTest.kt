package uk.co.brightec.xmlcheck.rules.attr.android

import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.w3c.dom.Attr
import uk.co.brightec.xmlcheck.rules.attr.android.TextSizeUnit.Companion.ERROR_MESSAGE
import uk.co.brightec.xmlcheck.testutil.Small

@Small
private class TextSizeUnitTest {

    private lateinit var rule: TextSizeUnit

    @BeforeEach
    fun beforeEach() {
        rule = TextSizeUnit()
    }

    @Test
    fun `Given value dp | When run check | Then failure`() {
        // GIVEN
        val node = mockk<Attr> {
            every { value } returns "12dp"
        }

        // WHEN
        val result = rule.run(node)

        // THEN
        val expected = rule.failure(node, ERROR_MESSAGE)
        assertEquals(expected, result)
    }

    @Test
    fun `Given value sp | When run check | Then no failure`() {
        // GIVEN
        val node = mockk<Attr> {
            every { value } returns "12sp"
        }

        // WHEN
        val result = rule.run(node)

        // THEN
        assertNull(result)
    }
}