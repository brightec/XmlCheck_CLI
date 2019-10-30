package uk.co.brightec.xmlcheck.rules.attr.android

import org.w3c.dom.Attr
import uk.co.brightec.xmlcheck.Constants.ATTR_NAMESPACE_ANDROID
import uk.co.brightec.xmlcheck.Failure
import uk.co.brightec.xmlcheck.rules.attr.AttrRule

class ColorRes : AttrRule(
    name = "ColorRes",
    attrNames = listOf(
        "$ATTR_NAMESPACE_ANDROID:textColor",
        "$ATTR_NAMESPACE_ANDROID:tint"
    )
) {

    override fun run(node: Attr): Failure<Attr>? {
        return if (node.value.startsWith("#")) {
            failure(node, ERROR_MESSAGE)
        } else {
            null
        }
    }

    companion object {

        const val ERROR_MESSAGE = "Color not in resources"
    }
}