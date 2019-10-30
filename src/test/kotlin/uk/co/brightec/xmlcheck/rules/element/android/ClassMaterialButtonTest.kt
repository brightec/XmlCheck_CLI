package uk.co.brightec.xmlcheck.rules.element.android

import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.w3c.dom.Element
import uk.co.brightec.xmlcheck.rules.element.android.ClassMaterialButton.Companion.ERROR_MESSAGE
import uk.co.brightec.xmlcheck.testutil.Small

@Small
private class ClassMaterialButtonTest {

    private lateinit var rule: ClassMaterialButton

    @BeforeEach
    fun beforeEach() {
        rule = ClassMaterialButton()
    }

    @Test
    fun `Given tagName Button | When run check | Then failure`() {
        // GIVEN
        val node = mockk<Element> {
            every { tagName } returns "Button"
        }

        // WHEN
        val result = rule.run(node)

        // THEN
        val expected = rule.failure(node, ERROR_MESSAGE)
        assertEquals(expected, result)
    }

    @Test
    fun `Given tagName MaterialButton | When run check | Then no failure`() {
        // GIVEN
        val node = mockk<Element> {
            every { tagName } returns "MaterialButton"
        }

        // WHEN
        val result = rule.run(node)

        // THEN
        assertNull(result)
    }
}