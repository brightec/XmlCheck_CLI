package uk.co.brightec.xmlcheck.rules

import org.w3c.dom.Node

abstract class NodeCheck {

    abstract val nodeName: String

    fun runEnsured(node: Node, tag: String) {
        if (node.nodeName != nodeName) {
            return
        }
        run(node, tag)
    }

    abstract fun run(node: Node, tag: String)
}