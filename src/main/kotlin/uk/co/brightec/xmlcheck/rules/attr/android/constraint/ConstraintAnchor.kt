package uk.co.brightec.xmlcheck.rules.attr.android.constraint

import org.w3c.dom.Attr
import uk.co.brightec.xmlcheck.Failure
import uk.co.brightec.xmlcheck.rules.Rule
import uk.co.brightec.xmlcheck.rules.RuleName
import uk.co.brightec.xmlcheck.rules.attr.AttrCheck

abstract class ConstraintAnchor : AttrCheck() {

    override val rules: List<Rule>
        get() = listOf(
            RULE_CONSTRAINT_ID_PLUS
        )

    override fun runCheck(node: Attr, suppressions: Collection<RuleName>): Failure<Attr>? {
        if (!suppressions.contains(RULE_CONSTRAINT_ID_PLUS.name) && ruleConstraintIdPlus(node)) {
            return RULE_CONSTRAINT_ID_PLUS.failure(node)
        }

        return null
    }

    private fun ruleConstraintIdPlus(attr: Attr) =
        attr.value != "parent" && !attr.value.startsWith("@+id/")

    companion object {

        val RULE_CONSTRAINT_ID_PLUS = Rule(
            name = "ConstraintIdPlus",
            errorMessage = "Doesn't start with @+id/"
        )
    }
}