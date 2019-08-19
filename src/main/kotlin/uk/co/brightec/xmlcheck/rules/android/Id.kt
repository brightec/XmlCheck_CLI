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

        return null
    }
}