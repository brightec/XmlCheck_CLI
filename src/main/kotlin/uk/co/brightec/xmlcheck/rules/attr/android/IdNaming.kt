package uk.co.brightec.xmlcheck.rules.attr.android

import org.w3c.dom.Attr
import uk.co.brightec.xmlcheck.Constants.ATTR_NAMESPACE_ANDROID
import uk.co.brightec.xmlcheck.Constants.ATTR_NAMESPACE_TOOLS
import uk.co.brightec.xmlcheck.Failure
import uk.co.brightec.xmlcheck.rules.attr.AttrRule

class IdNaming : AttrRule(
    name = "IdNaming",
    attrName = "$ATTR_NAMESPACE_ANDROID:id"
) {

    override fun run(node: Attr): Failure<Attr>? {
        val ownerElement = node.ownerElement
        val className = ownerElement.tagName.split(".").last()
        val parentTagName = if (className == "merge") {
            val parentTagAttr = ownerElement.attributes.getNamedItem("$ATTR_NAMESPACE_TOOLS:parentTag") as Attr
            parentTagAttr.value.split(".").last()
        } else {
            ownerElement.tagName
        }
        val attrId = node.value.substringAfter("@+id/")
        return if (conforms(attrId, parentTagName)) {
            null
        } else {
            failure(node, "Id for $className doesn't conform to naming convention")
        }
    }

    private fun conforms(attrId: String, parentTagName: String): Boolean {
        val className = parentTagName.split(".").last()
        val words = splitClassIntoWords(className)

        // General Exceptions
        if (className == "View" || className == "include") {
            return true
        }

        // Specific exceptions
        if (className == "HorizontalScrollView") {
            return attrId == "scroll" || attrId.startsWith("scroll_")
        }
        if (className == "GifImageView") {
            return attrId == "image" || attrId.startsWith("image_")
        }
        if (className == "Guideline") {
            return attrId == "guide" || attrId.startsWith("guide_")
        }
        if (className == "RadioButton") {
            return attrId == "radio" || attrId.startsWith("radio_")
        }
        if (className.contains("CheckBox")) {
            return attrId == "check_box" || attrId.startsWith("check_box_")
        }
        if (className.contains("ViewStub")) {
            return attrId == "stub" || attrId.startsWith("stub_")
        }

        // General rules
        if (words.contains("Button")) {
            return attrId == "button" || attrId.startsWith("button_")
        }
        if (words.contains("Card")) {
            return attrId == "card" || attrId.startsWith("card_")
        }
        val firstWordLower = words.first().toLowerCase()
        if (attrId == firstWordLower || attrId.startsWith("${firstWordLower}_")) {
            return true
        }

        return false
    }

    private fun splitClassIntoWords(className: String): List<String> {
        val words = arrayListOf<String>()
        var ongoingWord = ""
        for (element in className) {
            if (Character.isUpperCase(element)) {
                if (ongoingWord.isNotEmpty()) {
                    words.add(ongoingWord)
                }
                ongoingWord = "" + element
            } else {
                ongoingWord += element
            }
        }
        words.add(ongoingWord)
        return words
    }
}