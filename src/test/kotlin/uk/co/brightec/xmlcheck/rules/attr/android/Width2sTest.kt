package uk.co.brightec.xmlcheck.rules.attr.android

import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource
import org.w3c.dom.Attr
import uk.co.brightec.xmlcheck.rules.attr.android.Width2s.Companion.ERROR_MESSAGE
import uk.co.brightec.xmlcheck.testutil.Small

@Small
private class Width2sTest {

    private lateinit var rule: Width2s

    @BeforeEach
    fun beforeEach() {
        rule = Width2s()
    }

    @Test
    fun `Given value is 1 | When run check | Then no failure`() {
        // GIVEN
        val node = mockk<Attr> {
            every { this@mockk.value } returns "1dp"
        }

        // WHEN
        val result = rule.run(node)

        // THEN
        assertNull(result)
    }

    @ParameterizedTest
    @ValueSource(
        strings = ["3dp", "5dp", "7dp", "9dp", "11dp", "13dp", "15dp", "17dp", "23dp", "25dp", "31dp", "33dp"]
    )
    fun `Given value not in 2s | When run check | Then failure`(value: String) {
        // GIVEN
        val node = mockk<Attr> {
            every { this@mockk.value } returns value
        }

        // WHEN
        val result = rule.run(node)

        // THEN
        val expected = rule.failure(node, ERROR_MESSAGE)
        assertEquals(expected, result)
    }

    @ParameterizedTest
    @ValueSource(strings = ["0dp", "2dp", "4dp", "8dp", "12dp", "16dp", "24dp", "32dp"])
    fun `Given value in 2s | When run check | Then no failure`(value: String) {
        // GIVEN
        val node = mockk<Attr> {
            every { this@mockk.value } returns value
        }

        // WHEN
        val result = rule.run(node)

        // THEN
        assertNull(result)
    }
}