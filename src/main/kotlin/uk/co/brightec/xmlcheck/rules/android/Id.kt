package uk.co.brightec.xmlcheck.rules.android

import org.w3c.dom.Attr
import uk.co.brightec.xmlcheck.Constants.ATTR_NAMESPACE_ANDROID
import uk.co.brightec.xmlcheck.Failure
import uk.co.brightec.xmlcheck.rules.AttrCheck

class Id : AttrCheck() {

    override val attrName: String
        get() = "$ATTR_NAMESPACE_ANDROID:id"

    override fun run(attr: Attr): Failure? {
        if (!attr.value.startsWith("@+id/")) {
            return failure(attr, "Doesn't start with @+id/")
        }

        val attrId = attr.value.substringAfter("@+id/")

        val parentTagName = attr.ownerElement.tagName
        if (!checkIdNamingConvention(attrId, parentTagName)) {
            return failure(attr, "Id for $parentTagName doesn't conform to naming convention")
        }

        return null
    }

    // TODO : Finish configuring checkIdNamingConvention()
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

    private fun splitClassIntoWords(cpt: String): List<String> {
        val words = arrayListOf<String>()
        var ongoingWord = ""
        for (i in 0 until cpt.length) {
            val c = cpt[i]
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