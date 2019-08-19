package uk.co.brightec.xmlcheck.rules.android.margin

import org.w3c.dom.Attr
import uk.co.brightec.xmlcheck.Constants.ATTR_NAMESPACE_ANDROID
import uk.co.brightec.xmlcheck.Failure

class LayoutMarginTop : Margin() {

    override val attrName: String
        get() = "$ATTR_NAMESPACE_ANDROID:layout_marginTop"

    override fun run(attr: Attr): Failure? {
        return super.run(attr)
    }
}