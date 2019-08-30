package uk.co.brightec.xmlcheck.rules.attr.android.constraint

import uk.co.brightec.xmlcheck.Constants.ATTR_NAMESPACE_APP

class LayoutConstraintEndToStartOf : ConstraintAnchor() {

    override val attrName: String
        get() = "$ATTR_NAMESPACE_APP:layout_constraintEnd_toStartOf"
}