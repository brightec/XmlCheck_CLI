package uk.co.brightec.xmlcheck.rules.attr.android.margin

import org.w3c.dom.Attr
import uk.co.brightec.xmlcheck.Failure
import uk.co.brightec.xmlcheck.rules.Rule
import uk.co.brightec.xmlcheck.rules.RuleName
import uk.co.brightec.xmlcheck.rules.attr.AttrCheck

abstract class Margin : AttrCheck() {

    override val rules: List<Rule>
        get() = listOf(
            RULE_MARGIN_2S
        )

    override fun run(node: Attr, suppressions: List<RuleName>): Failure<Attr>? {
        if (!suppressions.contains(RULE_MARGIN_2S.name) && ruleMargin2s(node)) {
            return RULE_MARGIN_2S.failure(node)
        }

        return null
    }

    private fun ruleMargin2s(attr: Attr): Boolean {
        if (attr.value.endsWith("dp")) {
            val value = attr.value.substringBefore("dp").toInt()
            if (value.rem(2) != 0) {
                return true
            }
        }
        return false
    }

    companion object {

        val RULE_MARGIN_2S = Rule(
            name = "Margin2s",
            errorMessage = "Margin not divisible by 2"
        )
    }
}