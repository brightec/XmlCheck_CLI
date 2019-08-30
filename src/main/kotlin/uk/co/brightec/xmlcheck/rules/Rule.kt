package uk.co.brightec.xmlcheck.rules

import org.w3c.dom.Node
import uk.co.brightec.xmlcheck.Failure

data class Rule(
    val name: RuleName,
    val errorMessage: String
) {

    fun <T : Node> failure(
        node: T,
        errorMessage: String = this.errorMessage
    ) = Failure(
        ruleName = name,
        node = node,
        errorMessage = errorMessage
    )
}

typealias RuleName = String