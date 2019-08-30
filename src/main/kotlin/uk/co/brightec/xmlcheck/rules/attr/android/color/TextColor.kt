package uk.co.brightec.xmlcheck.rules.attr.android.color

import uk.co.brightec.xmlcheck.Constants.ATTR_NAMESPACE_ANDROID

class TextColor : Color() {

    override val attrName: String
        get() = "$ATTR_NAMESPACE_ANDROID:textColor"
}