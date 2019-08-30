package uk.co.brightec.xmlcheck.rules.attr.android.color

import org.w3c.dom.Attr
import uk.co.brightec.xmlcheck.Failure
import uk.co.brightec.xmlcheck.rules.Rule
import uk.co.brightec.xmlcheck.rules.RuleName
import uk.co.brightec.xmlcheck.rules.attr.AttrCheck

abstract class Color : AttrCheck() {

    override val rules: List<Rule>
        get() = listOf(
            RULE_COLOR_RES
        )

    override fun run(node: Attr, suppressions: Collection<RuleName>): Failure<Attr>? {
        if (!suppressions.contains(RULE_COLOR_RES.name) && ruleColorRes(node)) {
            return RULE_COLOR_RES.failure(node)
        }

        return null
    }

    private fun ruleColorRes(attr: Attr) =
        attr.value.startsWith("#")

    companion object {

        val RULE_COLOR_RES = Rule(
            name = "ColorRes",
            errorMessage = "Color not in resources"
        )
    }
}