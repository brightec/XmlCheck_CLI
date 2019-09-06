package uk.co.brightec.xmlcheck.rules.attr.android.color

import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.w3c.dom.Attr
import uk.co.brightec.xmlcheck.rules.attr.android.color.Color.Companion.RULE_COLOR_RES
import uk.co.brightec.xmlcheck.testutil.Small

@Small
private class TextColorTest {

    private lateinit var check: TextColor

    @BeforeEach
    fun beforeEach() {
        check = TextColor()
    }

    @Test
    fun `Given ColorRes suppressed | When run check | Then no failure`() {
        // GIVEN
        val suppressions = listOf(
            "ColorRes"
        )
        val node = mockk<Attr> {
            every { name } returns check.attrName
        }

        // WHEN
        val result = check.check(node, suppressions)

        // THEN
        assertNull(result)
    }

    @Test
    fun `Given value hex | When run check | Then failure`() {
        // GIVEN
        val node = mockk<Attr> {
            every { name } returns check.attrName
            every { value } returns "#123ABC"
        }

        // WHEN
        val result = check.check(node, emptyList())

        // THEN
        val expected = RULE_COLOR_RES.failure(node)
        assertEquals(expected, result)
    }

    @Test
    fun `Given value res | When run check | Then no failure`() {
        // GIVEN
        val node = mockk<Attr> {
            every { name } returns check.attrName
            every { value } returns "@color/some_color"
        }

        // WHEN
        val result = check.check(node, emptyList())

        // THEN
        assertNull(result)
    }
}