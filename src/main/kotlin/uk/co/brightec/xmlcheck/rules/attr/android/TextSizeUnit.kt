package uk.co.brightec.xmlcheck.rules.attr.android

import org.w3c.dom.Attr
import uk.co.brightec.xmlcheck.Constants.ATTR_NAMESPACE_ANDROID
import uk.co.brightec.xmlcheck.Failure
import uk.co.brightec.xmlcheck.rules.attr.AttrRule

class TextSizeUnit : AttrRule(
    name = "TextSizeUnit",
    attrName = "$ATTR_NAMESPACE_ANDROID:textSize"
) {

    override fun run(node: Attr): Failure<Attr>? {
        return if (node.value.endsWith("sp")) {
            null
        } else {
            failure(node, ERROR_MESSAGE)
        }
    }

    companion object {

        const val ERROR_MESSAGE = "Should be specified in sp"
    }
}