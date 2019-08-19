package uk.co.brightec.xmlcheck.rules.android.color

import org.w3c.dom.Attr
import uk.co.brightec.xmlcheck.Constants.ATTR_NAMESPACE_ANDROID
import uk.co.brightec.xmlcheck.Failure

class Tint : Color() {

    override val attrName: String
        get() = "$ATTR_NAMESPACE_ANDROID:tint"

    override fun run(attr: Attr): Failure? {
        return super.run(attr)
    }
}