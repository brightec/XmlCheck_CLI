package uk.co.brightec.xmlcheck.rules.android.constraint

import org.w3c.dom.Node
import uk.co.brightec.xmlcheck.Constants.ATTR_NAMESPACE_APP

class LayoutConstraintEndToStartOf : ConstraintAnchor() {

    override val nodeName: String
        get() = "$ATTR_NAMESPACE_APP:layout_constraintEnd_toStartOf"


    override fun run(node: Node, tag: String) {
        super.run(node, tag)
    }
}