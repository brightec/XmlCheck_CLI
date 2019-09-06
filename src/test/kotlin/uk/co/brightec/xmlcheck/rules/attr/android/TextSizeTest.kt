package uk.co.brightec.xmlcheck.rules.attr.android

import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.w3c.dom.Attr
import uk.co.brightec.xmlcheck.rules.attr.android.TextSize.Companion.RULE_TEXT_SIZE_UNIT
import uk.co.brightec.xmlcheck.testutil.Small

@Small
private class TextSizeTest {

    private lateinit var check: TextSize

    @BeforeEach
    fun beforeEach() {
        check = TextSize()
    }

    @Test
    fun `Given TextSizeUnit suppressed | When run check | Then no failure`() {
        // GIVEN
        val supressions = listOf(
            "TextSizeUnit"
        )
        val node = mockk<Attr> {
            every { name } returns check.attrName
        }

        // WHEN
        val result = check.check(node, supressions)

        // THEN
        assertNull(result)
    }

    @Test
    fun `Given value dp | When run check | Then failure`() {
        // GIVEN
        val node = mockk<Attr> {
            every { name } returns check.attrName
            every { value } returns "12dp"
        }

        // WHEN
        val result = check.check(node, emptyList())

        // THEN
        val expected = RULE_TEXT_SIZE_UNIT.failure(node)
        assertEquals(expected, result)
    }

    @Test
    fun `Given value sp | When run check | Then no failure`() {
        // GIVEN
        val node = mockk<Attr> {
            every { name } returns check.attrName
            every { value } returns "12sp"
        }

        // WHEN
        val result = check.check(node, emptyList())

        // THEN
        assertNull(result)
    }
}