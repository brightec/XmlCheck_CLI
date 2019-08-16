package uk.co.brightec.xmlcheck.rules.android

import org.w3c.dom.Node
import uk.co.brightec.xmlcheck.rules.NodeCheck
import uk.co.brightec.xmlcheck.Constants.ATTR_NAMESPACE_ANDROID

class Id : NodeCheck() {

    override val nodeName: String
        get() = "$ATTR_NAMESPACE_ANDROID:id"

    override fun run(node: Node, tag: String) {
        if (!node.nodeValue.startsWith("@+id/")) {
            error("$tag: $nodeName - Doesn't start with @+id/")
        }
    }
}