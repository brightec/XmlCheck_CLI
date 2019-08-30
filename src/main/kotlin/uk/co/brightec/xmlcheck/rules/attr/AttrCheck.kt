package uk.co.brightec.xmlcheck.rules.attr

import org.w3c.dom.Attr
import uk.co.brightec.xmlcheck.Failure
import uk.co.brightec.xmlcheck.rules.Check
import uk.co.brightec.xmlcheck.rules.RuleName

abstract class AttrCheck : Check<Attr>() {

    abstract val attrName: String

    override fun runEnsured(node: Attr, suppressions: List<RuleName>): Failure<Attr>? {
        if (node.name != attrName) {
            return null
        }
        return run(node, suppressions)
    }
}