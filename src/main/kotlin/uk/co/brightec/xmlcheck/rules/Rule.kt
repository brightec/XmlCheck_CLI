package uk.co.brightec.xmlcheck.rules

import org.w3c.dom.Node
import uk.co.brightec.xmlcheck.Failure

abstract class Rule<T : Node>(
    val name: RuleName
) {

    abstract fun run(node: T): Failure<T>?

    fun failure(
        node: T,
        errorMessage: String
    ) = Failure(
        ruleName = name,
        node = node,
        errorMessage = errorMessage
    )
}

typealias RuleName = String