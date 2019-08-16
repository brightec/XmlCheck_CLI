package uk.co.brightec.xmlcheck.rules.android.color

import org.w3c.dom.Node
import uk.co.brightec.xmlcheck.Constants.ATTR_NAMESPACE_ANDROID
import uk.co.brightec.xmlcheck.Constants.ATTR_NAMESPACE_APP

class Tint : Color() {

    override val nodeName: String
        get() = "$ATTR_NAMESPACE_ANDROID:tint"

    override fun run(node: Node, tag: String) {
        super.run(node, tag)
    }
}