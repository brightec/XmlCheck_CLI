package uk.co.brightec.xmlcheck.rules.attr.android

import org.w3c.dom.Attr
import uk.co.brightec.xmlcheck.Constants.ATTR_NAMESPACE_ANDROID
import uk.co.brightec.xmlcheck.Failure
import uk.co.brightec.xmlcheck.rules.attr.AttrRule

class Width2s : AttrRule(
    name = "Width2s",
    attrName = "$ATTR_NAMESPACE_ANDROID:layout_width"
) {

    override fun run(node: Attr): Failure<Attr>? {
        if (node.value.endsWith("dp")) {
            val value = node.value.substringBefore("dp").toInt()
            if (value == 1) return null
            if (value.rem(2) != 0) {
                return failure(node, ERROR_MESSAGE)
            }
        }
        return null
    }

    companion object {

        const val ERROR_MESSAGE = "Width not divisible by 2"
    }
}