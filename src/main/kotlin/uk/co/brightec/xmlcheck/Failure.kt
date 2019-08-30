package uk.co.brightec.xmlcheck

import org.w3c.dom.Node
import uk.co.brightec.xmlcheck.rules.RuleName

data class Failure<T : Node>(
    val ruleName: RuleName,
    val node: T,
    val errorMessage: String
)