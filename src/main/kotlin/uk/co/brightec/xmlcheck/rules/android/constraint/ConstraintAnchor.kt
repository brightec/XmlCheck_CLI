package uk.co.brightec.xmlcheck.rules.android.constraint

import org.w3c.dom.Node
import uk.co.brightec.xmlcheck.rules.NodeCheck

abstract class ConstraintAnchor : NodeCheck() {

    override fun run(node: Node, tag: String) {
        if (node.nodeValue != "parent" && !node.nodeValue.startsWith("@+id/")) {
            error("$tag: $nodeName - Doesn't start with @+id/")
        }
    }
}