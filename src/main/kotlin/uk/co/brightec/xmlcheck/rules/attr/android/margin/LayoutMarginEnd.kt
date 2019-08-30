package uk.co.brightec.xmlcheck.rules.attr.android.margin

import uk.co.brightec.xmlcheck.Constants.ATTR_NAMESPACE_ANDROID

class LayoutMarginEnd : Margin() {

    override val attrName: String
        get() = "$ATTR_NAMESPACE_ANDROID:layout_marginEnd"
}