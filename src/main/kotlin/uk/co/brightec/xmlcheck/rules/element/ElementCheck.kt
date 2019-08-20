package uk.co.brightec.xmlcheck.rules.element

import org.w3c.dom.Element
import uk.co.brightec.xmlcheck.Failure

abstract class ElementCheck {

    fun runEnsured(element: Element): Failure<Element>? {
        return run(element)
    }

    abstract fun run(element: Element): Failure<Element>?

    protected fun failure(
        element: Element,
        errorMessage: String
    ) = Failure(
        ruleClass = this::class,
        node = element,
        errorMessage = errorMessage
    )
}