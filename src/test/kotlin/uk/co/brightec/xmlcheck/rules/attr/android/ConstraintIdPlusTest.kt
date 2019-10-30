package uk.co.brightec.xmlcheck.rules.attr.android

import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.w3c.dom.Attr
import uk.co.brightec.xmlcheck.rules.attr.android.ConstraintIdPlus.Companion.ERROR_MESSAGE
import uk.co.brightec.xmlcheck.testutil.Small

@Small
private class ConstraintIdPlusTest {

    private lateinit var rule: ConstraintIdPlus

    @BeforeEach
    fun beforeEach() {
        rule = ConstraintIdPlus()
    }

    @Test
    fun `Given value @id | When run check | Then failure`() {
        // GIVEN
        val node = mockk<Attr> {
            every { value } returns "@id/"
        }

        // WHEN
        val result = rule.run(node)

        // THEN
        val expected = rule.failure(node, ERROR_MESSAGE)
        assertEquals(expected, result)
    }

    @Test
    fun `Given value @+id | When run check | Then no failure`() {
        // GIVEN
        val node = mockk<Attr> {
            every { value } returns "@+id/"
        }

        // WHEN
        val result = rule.run(node)

        // THEN
        assertNull(result)
    }
}