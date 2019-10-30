package uk.co.brightec.xmlcheck.rules.attr.android

import org.w3c.dom.Attr
import uk.co.brightec.xmlcheck.Constants.ATTR_NAMESPACE_ANDROID
import uk.co.brightec.xmlcheck.Failure
import uk.co.brightec.xmlcheck.rules.attr.AttrRule

class Margin2s : AttrRule(
    name = "Margin2s",
    attrNames = listOf(
        "$ATTR_NAMESPACE_ANDROID:layout_marginBottom",
        "$ATTR_NAMESPACE_ANDROID:layout_marginEnd",
        "$ATTR_NAMESPACE_ANDROID:layout_marginStart",
        "$ATTR_NAMESPACE_ANDROID:layout_marginTop"
    )
) {

    override fun run(node: Attr): Failure<Attr>? {
        if (node.value.endsWith("dp")) {
            val value = node.value.substringBefore("dp").toInt()
            if (value.rem(2) != 0) {
                return failure(node, ERROR_MESSAGE)
            }
        }
        return null
    }

    companion object {

        const val ERROR_MESSAGE = "Margin not divisible by 2"
    }
}