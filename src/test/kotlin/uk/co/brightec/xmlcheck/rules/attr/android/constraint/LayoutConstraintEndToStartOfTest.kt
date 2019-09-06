package uk.co.brightec.xmlcheck.rules.attr.android.constraint

import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.w3c.dom.Attr
import uk.co.brightec.xmlcheck.rules.attr.android.constraint.ConstraintAnchor.Companion.RULE_CONSTRAINT_ID_PLUS
import uk.co.brightec.xmlcheck.testutil.Small

@Small
private class LayoutConstraintEndToStartOfTest {

    private lateinit var check: LayoutConstraintEndToStartOf

    @BeforeEach
    fun beforeEach() {
        check = LayoutConstraintEndToStartOf()
    }

    @Test
    fun `Given ConstraintIdPlus suppressed | When run check | Then no failure`() {
        // GIVEN
        val suppressions = listOf("ConstraintIdPlus")
        val node = mockk<Attr> {
            every { name } returns check.attrName
        }

        // WHEN
        val result = check.check(node, suppressions)

        // THEN
        assertNull(result)
    }

    @Test
    fun `Given value @id | When run check | Then failure`() {
        // GIVEN
        val node = mockk<Attr> {
            every { name } returns check.attrName
            every { value } returns "@id/"
        }

        // WHEN
        val result = check.check(node, emptyList())

        // THEN
        val expected = RULE_CONSTRAINT_ID_PLUS.failure(node)
        assertEquals(expected, result)
    }

    @Test
    fun `Given value @+id | When run check | Then no failure`() {
        // GIVEN
        val node = mockk<Attr> {
            every { name } returns check.attrName
            every { value } returns "@+id/"
        }

        // WHEN
        val result = check.check(node, emptyList())

        // THEN
        assertNull(result)
    }
}