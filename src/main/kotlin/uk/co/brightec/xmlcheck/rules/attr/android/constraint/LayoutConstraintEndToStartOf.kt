package uk.co.brightec.xmlcheck.rules.attr.android.constraint

import org.w3c.dom.Attr
import uk.co.brightec.xmlcheck.Constants.ATTR_NAMESPACE_APP
import uk.co.brightec.xmlcheck.Failure

class LayoutConstraintEndToStartOf : ConstraintAnchor() {

    override val attrName: String
        get() = "$ATTR_NAMESPACE_APP:layout_constraintEnd_toStartOf"


    override fun run(attr: Attr): Failure<Attr>? {
        return super.run(attr)
    }
}