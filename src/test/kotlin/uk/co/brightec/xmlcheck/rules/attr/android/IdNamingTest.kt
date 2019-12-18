package uk.co.brightec.xmlcheck.rules.attr.android

import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import org.w3c.dom.Attr
import org.w3c.dom.Element
import uk.co.brightec.xmlcheck.Constants.ATTR_NAMESPACE_TOOLS
import uk.co.brightec.xmlcheck.testutil.Small
import java.util.stream.Stream

@Small
private class IdNamingTest {

    private lateinit var rule: IdNaming

    @BeforeEach
    fun beforeEach() {
        rule = IdNaming()
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
                attributes.getNamedItem("$ATTR_NAMESPACE_TOOLS:parentTag")
            } returns parentTagAttr
        }
        val node = mockk<Attr> {
            every { value } returns "@+id/anything"
            every { ownerElement } returns owner
        }

        // WHEN
        val result = rule.run(node)

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
                attributes.getNamedItem("$ATTR_NAMESPACE_TOOLS:parentTag")
            } returns parentTagAttr
        }
        val node = mockk<Attr> {
            every { value } returns "@+id/anything"
            every { ownerElement } returns owner
        }

        // WHEN
        val result = rule.run(node)

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
                attributes.getNamedItem("$ATTR_NAMESPACE_TOOLS:parentTag")
            } returns parentTagAttr
        }
        val node = mockk<Attr> {
            every { value } returns "@+id/other"
            every { ownerElement } returns owner
        }

        // WHEN
        val result = rule.run(node)

        // THEN
        val expected = rule.failure(node, "Id for ${owner.tagName} doesn't conform to naming convention")
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
                attributes.getNamedItem("$ATTR_NAMESPACE_TOOLS:parentTag")
            } returns parentTagAttr
        }
        val node = mockk<Attr> {
            every { value } returns "@+id/horizontal_anything"
            every { ownerElement } returns owner
        }

        // WHEN
        val result = rule.run(node)

        // THEN
        val expected = rule.failure(node, "Id for ${owner.tagName} doesn't conform to naming convention")
        assertEquals(expected, result)
    }

    @ParameterizedTest
    @MethodSource("createArgumentsHorizontalScrollView")
    fun `Given tagName HorizontalScrollView and naming exact correct | When run check | Then no failure`(
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
                attributes.getNamedItem("$ATTR_NAMESPACE_TOOLS:parentTag")
            } returns parentTagAttr
        }
        val node = mockk<Attr> {
            every { value } returns "@+id/scroll"
            every { ownerElement } returns owner
        }

        // WHEN
        val result = rule.run(node)

        // THEN
        assertNull(result)
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
                attributes.getNamedItem("$ATTR_NAMESPACE_TOOLS:parentTag")
            } returns parentTagAttr
        }
        val node = mockk<Attr> {
            every { value } returns "@+id/scroll_anything"
            every { ownerElement } returns owner
        }

        // WHEN
        val result = rule.run(node)

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
                attributes.getNamedItem("$ATTR_NAMESPACE_TOOLS:parentTag")
            } returns parentTagAttr
        }
        val node = mockk<Attr> {
            every { value } returns "@+id/other"
            every { ownerElement } returns owner
        }

        // WHEN
        val result = rule.run(node)

        // THEN
        val expected = rule.failure(node, "Id for ${owner.tagName} doesn't conform to naming convention")
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
                attributes.getNamedItem("$ATTR_NAMESPACE_TOOLS:parentTag")
            } returns parentTagAttr
        }
        val node = mockk<Attr> {
            every { value } returns "@+id/gif_anything"
            every { ownerElement } returns owner
        }

        // WHEN
        val result = rule.run(node)

        // THEN
        val expected = rule.failure(node, "Id for ${owner.tagName} doesn't conform to naming convention")
        assertEquals(expected, result)
    }

    @ParameterizedTest
    @MethodSource("createArgumentsGifImageView")
    fun `Given tagName GifImageView and naming exact correct | When run check | Then no failure`(
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
                attributes.getNamedItem("$ATTR_NAMESPACE_TOOLS:parentTag")
            } returns parentTagAttr
        }
        val node = mockk<Attr> {
            every { value } returns "@+id/image"
            every { ownerElement } returns owner
        }

        // WHEN
        val result = rule.run(node)

        // THEN
        assertNull(result)
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
                attributes.getNamedItem("$ATTR_NAMESPACE_TOOLS:parentTag")
            } returns parentTagAttr
        }
        val node = mockk<Attr> {
            every { value } returns "@+id/image_anything"
            every { ownerElement } returns owner
        }

        // WHEN
        val result = rule.run(node)

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
                attributes.getNamedItem("$ATTR_NAMESPACE_TOOLS:parentTag")
            } returns parentTagAttr
        }
        val node = mockk<Attr> {
            every { value } returns "@+id/other"
            every { ownerElement } returns owner
        }

        // WHEN
        val result = rule.run(node)

        // THEN
        val expected = rule.failure(node, "Id for ${owner.tagName} doesn't conform to naming convention")
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
                attributes.getNamedItem("$ATTR_NAMESPACE_TOOLS:parentTag")
            } returns parentTagAttr
        }
        val node = mockk<Attr> {
            every { value } returns "@+id/guideline_anything"
            every { ownerElement } returns owner
        }

        // WHEN
        val result = rule.run(node)

        // THEN
        val expected = rule.failure(node, "Id for ${owner.tagName} doesn't conform to naming convention")
        assertEquals(expected, result)
    }

    @ParameterizedTest
    @MethodSource("createArgumentsGuideline")
    fun `Given tagName Guideline and naming exact correct | When run check | Then no failure`(
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
                attributes.getNamedItem("$ATTR_NAMESPACE_TOOLS:parentTag")
            } returns parentTagAttr
        }
        val node = mockk<Attr> {
            every { value } returns "@+id/guide"
            every { ownerElement } returns owner
        }

        // WHEN
        val result = rule.run(node)

        // THEN
        assertNull(result)
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
                attributes.getNamedItem("$ATTR_NAMESPACE_TOOLS:parentTag")
            } returns parentTagAttr
        }
        val node = mockk<Attr> {
            every { value } returns "@+id/guide_anything"
            every { ownerElement } returns owner
        }

        // WHEN
        val result = rule.run(node)

        // THEN
        assertNull(result)
    }

    @ParameterizedTest
    @MethodSource("createArgumentsCheckBox")
    fun `Given tagName CheckBox and naming failure | When run check | Then failure`(
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
                attributes.getNamedItem("$ATTR_NAMESPACE_TOOLS:parentTag")
            } returns parentTagAttr
        }
        val node = mockk<Attr> {
            every { value } returns "@+id/other"
            every { ownerElement } returns owner
        }

        // WHEN
        val result = rule.run(node)

        // THEN
        val expected = rule.failure(node, "Id for ${owner.tagName} doesn't conform to naming convention")
        assertEquals(expected, result)
    }

    @ParameterizedTest
    @MethodSource("createArgumentsCheckBox")
    fun `Given tagName CheckBox and naming exact correct | When run check | Then no failure`(
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
                attributes.getNamedItem("$ATTR_NAMESPACE_TOOLS:parentTag")
            } returns parentTagAttr
        }
        val node = mockk<Attr> {
            every { value } returns "@+id/check_box"
            every { ownerElement } returns owner
        }

        // WHEN
        val result = rule.run(node)

        // THEN
        assertNull(result)
    }

    @ParameterizedTest
    @MethodSource("createArgumentsCheckBox")
    fun `Given tagName CheckBox and naming correct | When run check | Then no failure`(
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
                attributes.getNamedItem("$ATTR_NAMESPACE_TOOLS:parentTag")
            } returns parentTagAttr
        }
        val node = mockk<Attr> {
            every { value } returns "@+id/check_box_anything"
            every { ownerElement } returns owner
        }

        // WHEN
        val result = rule.run(node)

        // THEN
        assertNull(result)
    }

    @ParameterizedTest
    @MethodSource("createArgumentsViewStub")
    fun `Given tagName ViewStub and naming failure | When run check | Then failure`(
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
                attributes.getNamedItem("$ATTR_NAMESPACE_TOOLS:parentTag")
            } returns parentTagAttr
        }
        val node = mockk<Attr> {
            every { value } returns "@+id/other"
            every { ownerElement } returns owner
        }

        // WHEN
        val result = rule.run(node)

        // THEN
        val expected = rule.failure(node, "Id for ${owner.tagName} doesn't conform to naming convention")
        assertEquals(expected, result)
    }

    @ParameterizedTest
    @MethodSource("createArgumentsViewStub")
    fun `Given tagName ViewStub and naming exact correct | When run check | Then no failure`(
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
                attributes.getNamedItem("$ATTR_NAMESPACE_TOOLS:parentTag")
            } returns parentTagAttr
        }
        val node = mockk<Attr> {
            every { value } returns "@+id/stub"
            every { ownerElement } returns owner
        }

        // WHEN
        val result = rule.run(node)

        // THEN
        assertNull(result)
    }

    @ParameterizedTest
    @MethodSource("createArgumentsViewStub")
    fun `Given tagName ViewStub and naming correct | When run check | Then no failure`(
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
                attributes.getNamedItem("$ATTR_NAMESPACE_TOOLS:parentTag")
            } returns parentTagAttr
        }
        val node = mockk<Attr> {
            every { value } returns "@+id/stub_anything"
            every { ownerElement } returns owner
        }

        // WHEN
        val result = rule.run(node)

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
                attributes.getNamedItem("$ATTR_NAMESPACE_TOOLS:parentTag")
            } returns parentTagAttr
        }
        val node = mockk<Attr> {
            every { value } returns "@+id/other"
            every { ownerElement } returns owner
        }

        // WHEN
        val result = rule.run(node)

        // THEN
        val expected = rule.failure(node, "Id for ${owner.tagName} doesn't conform to naming convention")
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
                attributes.getNamedItem("$ATTR_NAMESPACE_TOOLS:parentTag")
            } returns parentTagAttr
        }
        val node = mockk<Attr> {
            every { value } returns "@+id/some_anything"
            every { ownerElement } returns owner
        }

        // WHEN
        val result = rule.run(node)

        // THEN
        val expected = rule.failure(node, "Id for ${owner.tagName} doesn't conform to naming convention")
        assertEquals(expected, result)
    }

    @ParameterizedTest
    @MethodSource("createArgumentsSomeButton")
    fun `Given tagName SomeButton and naming exact correct | When run check | Then no failure`(
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
                attributes.getNamedItem("$ATTR_NAMESPACE_TOOLS:parentTag")
            } returns parentTagAttr
        }
        val node = mockk<Attr> {
            every { value } returns "@+id/button"
            every { ownerElement } returns owner
        }

        // WHEN
        val result = rule.run(node)

        // THEN
        assertNull(result)
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
                attributes.getNamedItem("$ATTR_NAMESPACE_TOOLS:parentTag")
            } returns parentTagAttr
        }
        val node = mockk<Attr> {
            every { value } returns "@+id/button_anything"
            every { ownerElement } returns owner
        }

        // WHEN
        val result = rule.run(node)

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
                attributes.getNamedItem("$ATTR_NAMESPACE_TOOLS:parentTag")
            } returns parentTagAttr
        }
        val node = mockk<Attr> {
            every { value } returns "@+id/other"
            every { ownerElement } returns owner
        }

        // WHEN
        val result = rule.run(node)

        // THEN
        val expected = rule.failure(node, "Id for ${owner.tagName} doesn't conform to naming convention")
        assertEquals(expected, result)
    }

    @ParameterizedTest
    @MethodSource("createArgumentsSomeCustom")
    fun `Given tagName SomeCustom and naming exact correct | When run check | Then no failure`(
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
                attributes.getNamedItem("$ATTR_NAMESPACE_TOOLS:parentTag")
            } returns parentTagAttr
        }
        val node = mockk<Attr> {
            every { value } returns "@+id/some"
            every { ownerElement } returns owner
        }

        // WHEN
        val result = rule.run(node)

        // THEN
        assertNull(result)
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
                attributes.getNamedItem("$ATTR_NAMESPACE_TOOLS:parentTag")
            } returns parentTagAttr
        }
        val node = mockk<Attr> {
            every { value } returns "@+id/some_anything"
            every { ownerElement } returns owner
        }

        // WHEN
        val result = rule.run(node)

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
        fun createArgumentsCheckBox() = createArguments("CheckBox")

        @JvmStatic
        fun createArgumentsViewStub() = createArguments("ViewStub")

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