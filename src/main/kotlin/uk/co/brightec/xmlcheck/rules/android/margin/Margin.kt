package uk.co.brightec.xmlcheck.rules.android.margin

import org.w3c.dom.Node
import uk.co.brightec.xmlcheck.rules.NodeCheck

abstract class Margin : NodeCheck() {

    override fun run(node: Node, tag: String) {
        if (node.nodeValue.endsWith("dp")) {
            val value = node.nodeValue.substringBefore("dp").toInt()
            if (value.rem(2) != 0) {
                error("$tag: $nodeName - Margin not divisible by 2")
            }
        }
    }
}