package uk.co.brightec.xmlcheck.rules.attr.android.margin

import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource
import org.w3c.dom.Attr
import uk.co.brightec.xmlcheck.rules.attr.android.margin.Margin.Companion.RULE_MARGIN_2S
import uk.co.brightec.xmlcheck.testutil.Small

@Small
private class LayoutMarginEndTest {

    private lateinit var check: LayoutMarginEnd

    @BeforeEach
    fun beforeEach() {
        check = LayoutMarginEnd()
    }

    @Test
    fun `Given Margin2s suppressed | When run check | Then no failure`() {
        // GIVEN
        val suppressions = listOf(
            "Margin2s"
        )
        val node = mockk<Attr> {
            every { name } returns check.attrName
        }

        // WHEN
        val result = check.check(node, suppressions)

        // THEN
        assertNull(result)
    }

    @ParameterizedTest
    @ValueSource(
        strings = ["1dp", "3dp", "5dp", "7dp", "9dp", "11dp", "13dp", "15dp", "17dp", "23dp", "25dp", "31dp", "33dp"]
    )
    fun `Given value not in 2s | When run check | Then failure`(value: String) {
        // GIVEN
        val node = mockk<Attr> {
            every { name } returns check.attrName
            every { this@mockk.value } returns value
        }

        // WHEN
        val result = check.check(node, emptyList())

        // THEN
        val expected = RULE_MARGIN_2S.failure(node)
        assertEquals(expected, result)
    }

    @ParameterizedTest
    @ValueSource(strings = ["0dp", "2dp", "4dp", "8dp", "12dp", "16dp", "24dp", "32dp"])
    fun `Given value in 2s | When run check | Then no failure`(value: String) {
        // GIVEN
        val node = mockk<Attr> {
            every { name } returns check.attrName
            every { this@mockk.value } returns value
        }

        // WHEN
        val result = check.check(node, emptyList())

        // THEN
        assertNull(result)
    }
}