package uk.co.brightec.xmlcheck.rules.element.android

import org.w3c.dom.Element
import uk.co.brightec.xmlcheck.Failure
import uk.co.brightec.xmlcheck.rules.element.ElementCheck

class ClassName : ElementCheck() {

    override fun run(element: Element): Failure<Element>? {
        val className = element.tagName.split(".").last()

        if (className == "Button") {
            return failure(element, "Should use MaterialButton")
        }

        return null
    }
}