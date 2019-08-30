package uk.co.brightec.xmlcheck.rules.attr.android.color

import uk.co.brightec.xmlcheck.Constants.ATTR_NAMESPACE_ANDROID

class Tint : Color() {

    override val attrName: String
        get() = "$ATTR_NAMESPACE_ANDROID:tint"
}