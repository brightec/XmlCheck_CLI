package uk.co.brightec.xmlcheck.rules.android.margin

import org.w3c.dom.Node
import uk.co.brightec.xmlcheck.Constants.ATTR_NAMESPACE_ANDROID
import uk.co.brightec.xmlcheck.Constants.ATTR_NAMESPACE_APP

class LayoutMarginBottom : Margin() {

    override val nodeName: String
        get() = "$ATTR_NAMESPACE_ANDROID:layout_marginBottom"

    override fun run(node: Node, tag: String) {
        super.run(node, tag)
    }
}