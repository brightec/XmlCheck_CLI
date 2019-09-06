package uk.co.brightec.xmlcheck.rules.attr

import org.w3c.dom.Attr
import uk.co.brightec.xmlcheck.Failure
import uk.co.brightec.xmlcheck.rules.Check
import uk.co.brightec.xmlcheck.rules.RuleName

abstract class AttrCheck : Check<Attr>() {

    abstract val attrName: String

    override fun check(node: Attr, suppressions: Collection<RuleName>): Failure<Attr>? {
        if (node.name != attrName) {
            return null
        }
        return runCheck(node, suppressions)
    }

    abstract fun runCheck(node: Attr, suppressions: Collection<RuleName>): Failure<Attr>?
}