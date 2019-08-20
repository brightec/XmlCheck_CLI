package uk.co.brightec.xmlcheck.rules.attr.android.color

import org.w3c.dom.Attr
import uk.co.brightec.xmlcheck.Constants.ATTR_NAMESPACE_ANDROID
import uk.co.brightec.xmlcheck.Failure

class TextColor : Color() {

    override val attrName: String
        get() = "$ATTR_NAMESPACE_ANDROID:textColor"

    override fun run(attr: Attr): Failure<Attr>? {
        return super.run(attr)
    }
}