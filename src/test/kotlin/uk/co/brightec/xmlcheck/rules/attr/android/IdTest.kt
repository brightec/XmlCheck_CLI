package uk.co.brightec.xmlcheck.rules.attr.android

import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import org.w3c.dom.Attr
import org.w3c.dom.Element
import uk.co.brightec.xmlcheck.Constants
import uk.co.brightec.xmlcheck.rules.attr.android.Id.Companion.RULE_ID_NAMING
import uk.co.brightec.xmlcheck.rules.attr.android.Id.Companion.RULE_ID_PLUS
import uk.co.brightec.xmlcheck.testutil.Small
import java.util.stream.Stream

@Small
private class IdTest {

    private lateinit var check: Id

    @BeforeEach
    fun beforeEach() {
        check = Id()
    }

    @Test
    fun `Given IdPlus,IdNaming suppressed | When run check | Then no failure`() {
        // GIVEN
        val supressions = listOf("IdPlus", "IdNaming")
        val node = mockk<Attr> {
            every { name } returns check.attrName
        }

        // WHEN
        val result = check.check(node, supressions)

        // THEN
        assertNull(result)
    }

    @Test
    fun `Given value @id | When run check | Then failure`() {
        // GIVEN
        val supressions = listOf("IdNaming")
        val node = mockk<Attr> {
            every { name } returns check.attrName
            every { value } returns "@id/"
        }

        // WHEN
        val result = check.check(node, supressions)

        // THEN
        val expected = RULE_ID_PLUS.failure(node)
        assertEquals(expected, result)
    }

    @Test
    fun `Given value @+id | When run check | Then no failure`() {
        // GIVEN
        val supressions = listOf("IdNaming")
        val node = mockk<Attr> {
            every { name } returns check.attrName
            every { value } returns "@+id/"
        }

        // WHEN
        val result = check.check(node, supressions)

        // THEN
        assertNull(result)
    }

    @ParameterizedTest
    @MethodSource("createArgumentsView")
    fun `Given tagName View | When run check | Then no failure`(
        tagName: String,
        parentTagName: String?
    ) {
        // GIVEN
        val parentTagAttr = mockk<Attr> {
            every { value } returns parentTagName
        }
        val owner = mockk<Element> {
            every { this@mockk.tagName } returns tagName
            every {
                attributes.getNamedItem("${Constants.ATTR_NAMESPACE_TOOLS}:parentTag")
            } returns parentTagAttr
        }
        val node = mockk<Attr> {
            every { name } returns check.attrName
            every { value } returns "@+id/anything"
            every { ownerElement } returns owner
        }

        // WHEN
        val result = check.check(node, emptyList())

        // THEN
        assertNull(result)
    }

    @ParameterizedTest
    @MethodSource("createArgumentsInclude")
    fun `Given tagName include | When run check | Then no failure`(
        tagName: String,
        parentTagName: String?
    ) {
        // GIVEN
        val parentTagAttr = mockk<Attr> {
            every { value } returns parentTagName
        }
        val owner = mockk<Element> {
            every { this@mockk.tagName } returns tagName
            every {
                attributes.getNamedItem("${Constants.ATTR_NAMESPACE_TOOLS}:parentTag")
            } returns parentTagAttr
        }
        val node = mockk<Attr> {
            every { name } returns check.attrName
            every { value } returns "@+id/anything"
            every { ownerElement } returns owner
        }

        // WHEN
        val result = check.check(node, emptyList())

        // THEN
        assertNull(result)
    }

    @ParameterizedTest
    @MethodSource("createArgumentsHorizontalScrollView")
    fun `Given tagName HorizontalScrollView and naming failure | When run check | Then failure`(
        tagName: String,
        parentTagName: String?
    ) {
        // GIVEN
        val parentTagAttr = mockk<Attr> {
            every { value } returns parentTagName
        }
        val owner = mockk<Element> {
            every { this@mockk.tagName } returns tagName
            every {
                attributes.getNamedItem("${Constants.ATTR_NAMESPACE_TOOLS}:parentTag")
            } returns parentTagAttr
        }
        val node = mockk<Attr> {
            every { name } returns check.attrName
            every { value } returns "@+id/other"
            every { ownerElement } returns owner
        }

        // WHEN
        val result = check.check(node, emptyList())

        // THEN
        val expected = RULE_ID_NAMING.failure(node, "Id for ${owner.tagName} doesn't conform to naming convention")
        assertEquals(expected, result)
    }

    @ParameterizedTest
    @MethodSource("createArgumentsHorizontalScrollView")
    fun `Given tagName HorizontalScrollView and naming expected failure | When run check | Then failure`(
        tagName: String,
        parentTagName: String?
    ) {
        // GIVEN
        val parentTagAttr = mockk<Attr> {
            every { value } returns parentTagName
        }
        val owner = mockk<Element> {
            every { this@mockk.tagName } returns tagName
            every {
                attributes.getNamedItem("${Constants.ATTR_NAMESPACE_TOOLS}:parentTag")
            } returns parentTagAttr
        }
        val node = mockk<Attr> {
            every { name } returns check.attrName
            every { value } returns "@+id/horizontal_anything"
            every { ownerElement } returns owner
        }

        // WHEN
        val result = check.check(node, emptyList())

        // THEN
        val expected = RULE_ID_NAMING.failure(node, "Id for ${owner.tagName} doesn't conform to naming convention")
        assertEquals(expected, result)
    }

    @ParameterizedTest
    @MethodSource("createArgumentsHorizontalScrollView")
    fun `Given tagName HorizontalScrollView and naming correct | When run check | Then no failure`(
        tagName: String,
        parentTagName: String?
    ) {
        // GIVEN
        val parentTagAttr = mockk<Attr> {
            every { value } returns parentTagName
        }
        val owner = mockk<Element> {
            every { this@mockk.tagName } returns tagName
            every {
                attributes.getNamedItem("${Constants.ATTR_NAMESPACE_TOOLS}:parentTag")
            } returns parentTagAttr
        }
        val node = mockk<Attr> {
            every { name } returns check.attrName
            every { value } returns "@+id/scroll_anything"
            every { ownerElement } returns owner
        }

        // WHEN
        val result = check.check(node, emptyList())

        // THEN
        assertNull(result)
    }

    @ParameterizedTest
    @MethodSource("createArgumentsGifImageView")
    fun `Given tagName GifImageView and naming failure | When run check | Then failure`(
        tagName: String,
        parentTagName: String?
    ) {
        // GIVEN
        val parentTagAttr = mockk<Attr> {
            every { value } returns parentTagName
        }
        val owner = mockk<Element> {
            every { this@mockk.tagName } returns tagName
            every {
                attributes.getNamedItem("${Constants.ATTR_NAMESPACE_TOOLS}:parentTag")
            } returns parentTagAttr
        }
        val node = mockk<Attr> {
            every { name } returns check.attrName
            every { value } returns "@+id/other"
            every { ownerElement } returns owner
        }

        // WHEN
        val result = check.check(node, emptyList())

        // THEN
        val expected = RULE_ID_NAMING.failure(node, "Id for ${owner.tagName} doesn't conform to naming convention")
        assertEquals(expected, result)
    }

    @ParameterizedTest
    @MethodSource("createArgumentsGifImageView")
    fun `Given tagName GifImageView and naming expected failure | When run check | Then failure`(
        tagName: String,
        parentTagName: String?
    ) {
        // GIVEN
        val parentTagAttr = mockk<Attr> {
            every { value } returns parentTagName
        }
        val owner = mockk<Element> {
            every { this@mockk.tagName } returns tagName
            every {
                attributes.getNamedItem("${Constants.ATTR_NAMESPACE_TOOLS}:parentTag")
            } returns parentTagAttr
        }
        val node = mockk<Attr> {
            every { name } returns check.attrName
            every { value } returns "@+id/gif_anything"
            every { ownerElement } returns owner
        }

        // WHEN
        val result = check.check(node, emptyList())

        // THEN
        val expected = RULE_ID_NAMING.failure(node, "Id for ${owner.tagName} doesn't conform to naming convention")
        assertEquals(expected, result)
    }

    @ParameterizedTest
    @MethodSource("createArgumentsGifImageView")
    fun `Given tagName GifImageView and naming correct | When run check | Then no failure`(
        tagName: String,
        parentTagName: String?
    ) {
        // GIVEN
        val parentTagAttr = mockk<Attr> {
            every { value } returns parentTagName
        }
        val owner = mockk<Element> {
            every { this@mockk.tagName } returns tagName
            every {
                attributes.getNamedItem("${Constants.ATTR_NAMESPACE_TOOLS}:parentTag")
            } returns parentTagAttr
        }
        val node = mockk<Attr> {
            every { name } returns check.attrName
            every { value } returns "@+id/image_anything"
            every { ownerElement } returns owner
        }

        // WHEN
        val result = check.check(node, emptyList())

        // THEN
        assertNull(result)
    }

    @ParameterizedTest
    @MethodSource("createArgumentsGuideline")
    fun `Given tagName Guideline and naming failure | When run check | Then failure`(
        tagName: String,
        parentTagName: String?
    ) {
        // GIVEN
        val parentTagAttr = mockk<Attr> {
            every { value } returns parentTagName
        }
        val owner = mockk<Element> {
            every { this@mockk.tagName } returns tagName
            every {
                attributes.getNamedItem("${Constants.ATTR_NAMESPACE_TOOLS}:parentTag")
            } returns parentTagAttr
        }
        val node = mockk<Attr> {
            every { name } returns check.attrName
            every { value } returns "@+id/other"
            every { ownerElement } returns owner
        }

        // WHEN
        val result = check.check(node, emptyList())

        // THEN
        val expected = RULE_ID_NAMING.failure(node, "Id for ${owner.tagName} doesn't conform to naming convention")
        assertEquals(expected, result)
    }

    @ParameterizedTest
    @MethodSource("createArgumentsGuideline")
    fun `Given tagName Guideline and naming expected failure | When run check | Then failure`(
        tagName: String,
        parentTagName: String?
    ) {
        // GIVEN
        val parentTagAttr = mockk<Attr> {
            every { value } returns parentTagName
        }
        val owner = mockk<Element> {
            every { this@mockk.tagName } returns tagName
            every {
                attributes.getNamedItem("${Constants.ATTR_NAMESPACE_TOOLS}:parentTag")
            } returns parentTagAttr
        }
        val node = mockk<Attr> {
            every { name } returns check.attrName
            every { value } returns "@+id/guideline_anything"
            every { ownerElement } returns owner
        }

        // WHEN
        val result = check.check(node, emptyList())

        // THEN
        val expected = RULE_ID_NAMING.failure(node, "Id for ${owner.tagName} doesn't conform to naming convention")
        assertEquals(expected, result)
    }

    @ParameterizedTest
    @MethodSource("createArgumentsGuideline")
    fun `Given tagName Guideline and naming correct | When run check | Then no failure`(
        tagName: String,
        parentTagName: String?
    ) {
        // GIVEN
        val parentTagAttr = mockk<Attr> {
            every { value } returns parentTagName
        }
        val owner = mockk<Element> {
            every { this@mockk.tagName } returns tagName
            every {
                attributes.getNamedItem("${Constants.ATTR_NAMESPACE_TOOLS}:parentTag")
            } returns parentTagAttr
        }
        val node = mockk<Attr> {
            every { name } returns check.attrName
            every { value } returns "@+id/guide_anything"
            every { ownerElement } returns owner
        }

        // WHEN
        val result = check.check(node, emptyList())

        // THEN
        assertNull(result)
    }

    @ParameterizedTest
    @MethodSource("createArgumentsSomeButton")
    fun `Given tagName SomeButton and naming failure | When run check | Then failure`(
        tagName: String,
        parentTagName: String?
    ) {
        // GIVEN
        val parentTagAttr = mockk<Attr> {
            every { value } returns parentTagName
        }
        val owner = mockk<Element> {
            every { this@mockk.tagName } returns tagName
            every {
                attributes.getNamedItem("${Constants.ATTR_NAMESPACE_TOOLS}:parentTag")
            } returns parentTagAttr
        }
        val node = mockk<Attr> {
            every { name } returns check.attrName
            every { value } returns "@+id/other"
            every { ownerElement } returns owner
        }

        // WHEN
        val result = check.check(node, emptyList())

        // THEN
        val expected = RULE_ID_NAMING.failure(node, "Id for ${owner.tagName} doesn't conform to naming convention")
        assertEquals(expected, result)
    }

    @ParameterizedTest
    @MethodSource("createArgumentsSomeButton")
    fun `Given tagName SomeButton and naming expected failure | When run check | Then failure`(
        tagName: String,
        parentTagName: String?
    ) {
        // GIVEN
        val parentTagAttr = mockk<Attr> {
            every { value } returns parentTagName
        }
        val owner = mockk<Element> {
            every { this@mockk.tagName } returns tagName
            every {
                attributes.getNamedItem("${Constants.ATTR_NAMESPACE_TOOLS}:parentTag")
            } returns parentTagAttr
        }
        val node = mockk<Attr> {
            every { name } returns check.attrName
            every { value } returns "@+id/some_anything"
            every { ownerElement } returns owner
        }

        // WHEN
        val result = check.check(node, emptyList())

        // THEN
        val expected = RULE_ID_NAMING.failure(node, "Id for ${owner.tagName} doesn't conform to naming convention")
        assertEquals(expected, result)
    }

    @ParameterizedTest
    @MethodSource("createArgumentsSomeButton")
    fun `Given tagName SomeButton and naming correct | When run check | Then no failure`(
        tagName: String,
        parentTagName: String?
    ) {
        // GIVEN
        val parentTagAttr = mockk<Attr> {
            every { value } returns parentTagName
        }
        val owner = mockk<Element> {
            every { this@mockk.tagName } returns tagName
            every {
                attributes.getNamedItem("${Constants.ATTR_NAMESPACE_TOOLS}:parentTag")
            } returns parentTagAttr
        }
        val node = mockk<Attr> {
            every { name } returns check.attrName
            every { value } returns "@+id/button_anything"
            every { ownerElement } returns owner
        }

        // WHEN
        val result = check.check(node, emptyList())

        // THEN
        assertNull(result)
    }

    @ParameterizedTest
    @MethodSource("createArgumentsSomeCustom")
    fun `Given tagName SomeCustom and naming failure | When run check | Then failure`(
        tagName: String,
        parentTagName: String?
    ) {
        // GIVEN
        val parentTagAttr = mockk<Attr> {
            every { value } returns parentTagName
        }
        val owner = mockk<Element> {
            every { this@mockk.tagName } returns tagName
            every {
                attributes.getNamedItem("${Constants.ATTR_NAMESPACE_TOOLS}:parentTag")
            } returns parentTagAttr
        }
        val node = mockk<Attr> {
            every { name } returns check.attrName
            every { value } returns "@+id/other"
            every { ownerElement } returns owner
        }

        // WHEN
        val result = check.check(node, emptyList())

        // THEN
        val expected = RULE_ID_NAMING.failure(node, "Id for ${owner.tagName} doesn't conform to naming convention")
        assertEquals(expected, result)
    }

    @ParameterizedTest
    @MethodSource("createArgumentsSomeCustom")
    fun `Given tagName SomeCustom and naming correct | When run check | Then no failure`(
        tagName: String,
        parentTagName: String?
    ) {
        // GIVEN
        val parentTagAttr = mockk<Attr> {
            every { value } returns parentTagName
        }
        val owner = mockk<Element> {
            every { this@mockk.tagName } returns tagName
            every {
                attributes.getNamedItem("${Constants.ATTR_NAMESPACE_TOOLS}:parentTag")
            } returns parentTagAttr
        }
        val node = mockk<Attr> {
            every { name } returns check.attrName
            every { value } returns "@+id/some_anything"
            every { ownerElement } returns owner
        }

        // WHEN
        val result = check.check(node, emptyList())

        // THEN
        assertNull(result)
    }

    @Suppress("unused")
    companion object {

        @JvmStatic
        fun createArgumentsView() = createArguments("View")

        @JvmStatic
        fun createArgumentsInclude() = createArguments("include")

        @JvmStatic
        fun createArgumentsHorizontalScrollView() = createArguments("HorizontalScrollView")

        @JvmStatic
        fun createArgumentsGifImageView() = createArguments("GifImageView")

        @JvmStatic
        fun createArgumentsGuideline() = createArguments("Guideline")

        @JvmStatic
        fun createArgumentsSomeButton() = createArguments("SomeButton")

        @JvmStatic
        fun createArgumentsSomeCustom() = createArguments("SomeCustom")

        fun createArguments(tagName: String): Stream<Arguments> = Stream.of(
            Arguments.of(tagName, null),
            Arguments.of("merge", tagName)
        )
    }
}