package uk.co.brightec.xmlcheck.rules.attr

import org.w3c.dom.Attr
import uk.co.brightec.xmlcheck.Failure
import uk.co.brightec.xmlcheck.rules.Rule
import uk.co.brightec.xmlcheck.rules.RuleName

abstract class AttrRule(name: RuleName, val attrNames: List<String>) : Rule<Attr>(name) {

    constructor(name: RuleName, attrName: String) : this(name, listOf(attrName))

    fun runChecked(node: Attr): Failure<Attr>? {
        if (!attrNames.contains(node.name)) return failure(node, "Run on incorrect attr: ${node.name}")

        return run(node)
    }
}