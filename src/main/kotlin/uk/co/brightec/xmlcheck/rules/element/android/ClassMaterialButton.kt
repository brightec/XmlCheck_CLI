package uk.co.brightec.xmlcheck.rules.element.android

import org.w3c.dom.Element
import uk.co.brightec.xmlcheck.Failure
import uk.co.brightec.xmlcheck.rules.element.ElementRule

class ClassMaterialButton : ElementRule(
    name = "ClassMaterialButton"
) {

    override fun run(node: Element): Failure<Element>? {
        return if (node.tagName.split(".").last() == "Button") {
            failure(node, ERROR_MESSAGE)
        } else {
            null
        }
    }

    companion object {

        const val ERROR_MESSAGE = "Should use MaterialButton"
    }
}