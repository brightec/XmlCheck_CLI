package uk.co.brightec.xmlcheck.rules.element

import org.w3c.dom.Element
import uk.co.brightec.xmlcheck.Failure
import uk.co.brightec.xmlcheck.rules.Check
import uk.co.brightec.xmlcheck.rules.RuleName

abstract class ElementCheck : Check<Element>() {

    override fun runEnsured(node: Element, suppressions: List<RuleName>): Failure<Element>? {
        return run(node, suppressions)
    }
}