package uk.co.brightec.xmlcheck.rules

import org.w3c.dom.Node
import uk.co.brightec.xmlcheck.Failure

abstract class Check<T : Node> {

    abstract val rules: List<Rule>

    abstract fun runEnsured(node: T, suppressions: Collection<RuleName>): Failure<T>?

    abstract fun run(node: T, suppressions: Collection<RuleName>): Failure<T>?
}