package uk.co.brightec.xmlcheck.rules.android

import org.w3c.dom.Node
import uk.co.brightec.xmlcheck.Constants
import uk.co.brightec.xmlcheck.Constants.ATTR_NAMESPACE_ANDROID
import uk.co.brightec.xmlcheck.rules.NodeCheck

class TextSize : NodeCheck() {

    override val nodeName: String
        get() = "$ATTR_NAMESPACE_ANDROID:textSize"

    override fun run(node: Node, tag: String) {
        if (!node.nodeValue.endsWith("sp")) {
            error("$tag: $nodeName - Should be specified in sp")
        }
    }
}