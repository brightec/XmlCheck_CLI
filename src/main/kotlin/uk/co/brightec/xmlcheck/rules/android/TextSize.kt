package uk.co.brightec.xmlcheck.rules.android

import org.w3c.dom.Attr
import uk.co.brightec.xmlcheck.Constants.ATTR_NAMESPACE_ANDROID
import uk.co.brightec.xmlcheck.Failure
import uk.co.brightec.xmlcheck.rules.AttrCheck

class TextSize : AttrCheck() {

    override val attrName: String
        get() = "$ATTR_NAMESPACE_ANDROID:textSize"

    override fun run(attr: Attr): Failure? {
        if (!attr.value.endsWith("sp")) {
            return failure(attr, "Should be specified in sp")
        }

        return null
    }
}