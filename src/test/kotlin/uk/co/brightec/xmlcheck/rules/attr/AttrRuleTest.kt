package uk.co.brightec.xmlcheck.rules.attr

import io.mockk.every
import io.mockk.mockk
import io.mockk.spyk
import io.mockk.verify
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.w3c.dom.Attr
import uk.co.brightec.xmlcheck.Failure
import uk.co.brightec.xmlcheck.testutil.Small

@Small
private class AttrRuleTest {

    private lateinit var rule: AttrRule

    @BeforeEach
    fun beforeEach() {
        rule = spyk(SomeRule())
    }

    @Test
    fun `Given wrong attr | When run check | Then failure`() {
        // GIVEN
        val node = mockk<Attr> {
            every { name } returns "Some_other_attr"
        }

        // WHEN
        val result = rule.runChecked(node)

        // THEN
        val expected = rule.failure(
            node = node,
            errorMessage = "Run on incorrect attr: ${node.name}"
        )
        assertEquals(expected, result)
    }

    @Test
    fun `Given correct attr | When run check | Then calls run`() {
        // GIVEN
        val node = mockk<Attr> {
            every { name } returns "attr1"
        }

        // WHEN
        rule.runChecked(node)

        // THEN
        verify { rule.run(node) }
    }

    private class SomeRule : AttrRule("Some Rule", attrNames = listOf("attr1", "attr2")) {

        override fun run(node: Attr): Failure<Attr>? {
            return failure(node, "Some failure")
        }
    }
}