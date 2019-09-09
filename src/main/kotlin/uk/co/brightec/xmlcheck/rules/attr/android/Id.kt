package uk.co.brightec.xmlcheck.rules.attr.android

import org.w3c.dom.Attr
import uk.co.brightec.xmlcheck.Constants.ATTR_NAMESPACE_ANDROID
import uk.co.brightec.xmlcheck.Constants.ATTR_NAMESPACE_TOOLS
import uk.co.brightec.xmlcheck.Failure
import uk.co.brightec.xmlcheck.rules.Rule
import uk.co.brightec.xmlcheck.rules.RuleName
import uk.co.brightec.xmlcheck.rules.attr.AttrCheck

class Id : AttrCheck() {

    override val rules: List<Rule>
        get() = listOf(
            RULE_ID_PLUS,
            RULE_ID_NAMING
        )
    override val attrName: String
        get() = "$ATTR_NAMESPACE_ANDROID:id"

    override fun runCheck(node: Attr, suppressions: Collection<RuleName>): Failure<Attr>? {
        if (!suppressions.contains(RULE_ID_PLUS.name) && ruleIdPlus(node)) {
            return RULE_ID_PLUS.failure(node)
        }

        if (!suppressions.contains(RULE_ID_NAMING.name) && !ruleIdNaming(node)) {
            val parentTagName = node.ownerElement.tagName
            return RULE_ID_NAMING.failure(node, "Id for $parentTagName doesn't conform to naming convention")
        }

        return null
    }

    private fun ruleIdPlus(attr: Attr) =
        !attr.value.startsWith("@+id/")

    private fun ruleIdNaming(attr: Attr): Boolean {
        val attrId = attr.value.substringAfter("@+id/")
        val ownerElement = attr.ownerElement
        val parentTagName = ownerElement.tagName
        val className = parentTagName.split(".").last()
        if (className == "merge") {
            if (attrId.startsWith("merge")) return true

            val parentTagAttr = ownerElement.attributes.getNamedItem("$ATTR_NAMESPACE_TOOLS:parentTag") as Attr
            val parentTagClassName = parentTagAttr.value.split(".").last()
            return checkIdNamingConvention(attrId, parentTagClassName)
        }

        return checkIdNamingConvention(attrId, parentTagName)
    }

    private fun checkIdNamingConvention(attrId: String, parentTagName: String): Boolean {
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

        // General rules
        if (words.contains("Button")) {
            return attrId == "button" || attrId.startsWith("button_")
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

    companion object {

        val RULE_ID_PLUS = Rule(
            name = "IdPlus",
            errorMessage = "Doesn't start with @+id/"
        )
        val RULE_ID_NAMING = Rule(
            name = "IdNaming",
            errorMessage = "Id doesn't conform to naming convention"
        )
    }
}