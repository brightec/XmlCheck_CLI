package uk.co.brightec.xmlcheck.rules.android.constraint

import org.w3c.dom.Attr
import uk.co.brightec.xmlcheck.Constants.ATTR_NAMESPACE_APP
import uk.co.brightec.xmlcheck.Failure

class LayoutConstraintTopToBottomOf : ConstraintAnchor() {

    override val attrName: String
        get() = "$ATTR_NAMESPACE_APP:layout_constraintTop_toBottomOf"

    override fun run(attr: Attr): Failure? {
        return super.run(attr)
    }
}