package uk.co.brightec.xmlcheck.rules.element.android

import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.w3c.dom.Element
import uk.co.brightec.xmlcheck.rules.element.android.ClassName.Companion.RULE_CLASS_MATERIAL_BUTTON
import uk.co.brightec.xmlcheck.testutil.Small

@Small
private class ClassNameTest {

    private lateinit var check: ClassName

    @BeforeEach
    fun beforeEach() {
        check = ClassName()
    }

    @Test
    fun `Given ClassMaterialButton suppressed | When run check | Then no failure`() {
        // GIVEN
        val suppressions = listOf(
            "ClassMaterialButton"
        )
        val node = mockk<Element>()

        // WHEN
        val result = check.check(node, suppressions)

        // THEN
        assertNull(result)
    }

    @Test
    fun `Given tagName Button | When run check | Then failure`() {
        // GIVEN
        val node = mockk<Element> {
            every { tagName } returns "Button"
        }

        // WHEN
        val result = check.check(node, emptyList())

        // THEN
        val expected = RULE_CLASS_MATERIAL_BUTTON.failure(node)
        assertEquals(expected, result)
    }

    @Test
    fun `Given tagName MaterialButton | When run check | Then no failure`() {
        // GIVEN
        val node = mockk<Element> {
            every { tagName } returns "MaterialButton"
        }

        // WHEN
        val result = check.check(node, emptyList())

        // THEN
        assertNull(result)
    }
}