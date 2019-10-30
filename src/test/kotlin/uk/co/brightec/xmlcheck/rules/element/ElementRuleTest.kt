package uk.co.brightec.xmlcheck.rules.element

import io.mockk.mockk
import io.mockk.spyk
import io.mockk.verify
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.w3c.dom.Element
import uk.co.brightec.xmlcheck.Failure
import uk.co.brightec.xmlcheck.testutil.Small

@Small
private class ElementRuleTest {

    private lateinit var rule: ElementRule

    @BeforeEach
    fun beforeEach() {
        rule = spyk(SomeRule())
    }

    @Test
    fun `Given node | When run check | Then calls run`() {
        // GIVEN
        val node = mockk<Element>()

        // WHEN
        rule.runChecked(node)

        // THEN
        verify { rule.run(node) }
    }

    private class SomeRule : ElementRule("Some Rule") {

        override fun run(node: Element): Failure<Element>? {
            return failure(node, "Some failure")
        }
    }
}