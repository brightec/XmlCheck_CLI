package uk.co.brightec.xmlcheck.rules.attr.android

import org.w3c.dom.Attr
import org.w3c.dom.Element
import uk.co.brightec.xmlcheck.Constants.ATTR_NAMESPACE_ANDROID
import uk.co.brightec.xmlcheck.Failure
import uk.co.brightec.xmlcheck.rules.attr.AttrCheck

class Id : AttrCheck() {

    override val attrName: String
        get() = "$ATTR_NAMESPACE_ANDROID:id"

    override fun run(attr: Attr): Failure<Attr>? {
        if (!attr.value.startsWith("@+id/")) {
            return failure(attr, "Doesn't start with @+id/")
        }

        val attrId = attr.value.substringAfter("@+id/")

        if (!checkIdNamingConvention(attrId, attr.ownerElement)) {
            val parentTagName = attr.ownerElement.tagName
            return failure(attr, "Id for $parentTagName doesn't conform to naming convention")
        }

        return null
    }

    private fun checkIdNamingConvention(attrId: String, ownerElement: Element): Boolean {
        val parentTagName = ownerElement.tagName
        val className = parentTagName.split(".").last()
        if (className == "merge") {
            if (attrId.startsWith("merge")) return true

            val parentTagAttr = ownerElement.attributes.getNamedItem("tools:parentTag") as Attr
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

        // Specific exception
        if (className == "HorizontalScrollView" && attrId.startsWith("scroll")) {
            return true
        }
        if (className == "GifImageView" && attrId.startsWith("image")) {
            return true
        }
        if (className == "Guideline" && attrId.startsWith("guide")) {
            return true
        }

        // General rules
        if (words.contains("Button") && attrId.startsWith("button")) {
            return true
        }
        if (attrId.startsWith(words.first().toLowerCase())) {
            return true
        }

        return false
    }

    private fun splitClassIntoWords(className: String): List<String> {
        val words = arrayListOf<String>()
        var ongoingWord = ""
        for (i in 0 until className.length) {
            val c = className[i]
            if (Character.isUpperCase(c)) {
                if (ongoingWord.isNotEmpty()) {
                    words.add(ongoingWord)
                }
                ongoingWord = "" + c
            } else {
                ongoingWord += c
            }
        }
        words.add(ongoingWord)
        return words
    }
}