package uk.co.brightec.xmlcheck.rules.attr.android.margin

import org.w3c.dom.Attr
import uk.co.brightec.xmlcheck.Constants.ATTR_NAMESPACE_ANDROID
import uk.co.brightec.xmlcheck.Failure

class LayoutMarginStart : Margin() {

    override val attrName: String
        get() = "$ATTR_NAMESPACE_ANDROID:layout_marginStart"

    override fun run(attr: Attr): Failure<Attr>? {
        return super.run(attr)
    }
}