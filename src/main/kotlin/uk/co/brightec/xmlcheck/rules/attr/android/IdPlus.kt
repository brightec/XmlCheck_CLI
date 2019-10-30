package uk.co.brightec.xmlcheck.rules.attr.android

import org.w3c.dom.Attr
import uk.co.brightec.xmlcheck.Constants.ATTR_NAMESPACE_ANDROID
import uk.co.brightec.xmlcheck.Failure
import uk.co.brightec.xmlcheck.rules.attr.AttrRule

class IdPlus : AttrRule(
    name = "IdPlus",
    attrName = "$ATTR_NAMESPACE_ANDROID:id"
) {

    override fun run(node: Attr): Failure<Attr>? {
        return if (node.value.startsWith("@+id/")) {
            null
        } else {
            failure(node, ERROR_MESSAGE)
        }
    }

    companion object {

        const val ERROR_MESSAGE = "Doesn't start with @+id/"
    }
}