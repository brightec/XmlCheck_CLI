package uk.co.brightec.xmlcheck.rules.element

import org.w3c.dom.Element
import uk.co.brightec.xmlcheck.Failure
import uk.co.brightec.xmlcheck.rules.Rule
import uk.co.brightec.xmlcheck.rules.RuleName

abstract class ElementRule(name: RuleName) : Rule<Element>(name) {

    fun runChecked(node: Element): Failure<Element>? {
        return run(node)
    }
}