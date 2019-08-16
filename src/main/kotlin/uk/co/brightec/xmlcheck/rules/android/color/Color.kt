package uk.co.brightec.xmlcheck.rules.android.color

import org.w3c.dom.Node
import uk.co.brightec.xmlcheck.rules.NodeCheck

abstract class Color : NodeCheck() {

    override fun run(node: Node, tag: String) {
        if (node.nodeValue.startsWith("#")) {
            error("$tag: $nodeName - Color not in resources")
        }
    }
}