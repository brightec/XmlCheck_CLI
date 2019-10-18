package uk.co.brightec.xmlcheck.rules.attr.android

import org.w3c.dom.Attr
import uk.co.brightec.xmlcheck.Constants.ATTR_NAMESPACE_ANDROID
import uk.co.brightec.xmlcheck.Failure
import uk.co.brightec.xmlcheck.rules.Rule
import uk.co.brightec.xmlcheck.rules.RuleName
import uk.co.brightec.xmlcheck.rules.attr.AttrCheck

class Width : AttrCheck() {

    override val rules: List<Rule>
        get() = listOf(
            RULE_WIDTH_2S
        )
    override val attrName: String
        get() = "${ATTR_NAMESPACE_ANDROID}:layout_width"

    override fun runCheck(node: Attr, suppressions: Collection<RuleName>): Failure<Attr>? {
        if (!suppressions.contains(RULE_WIDTH_2S.name) && ruleWidth2s(node)) {
            return RULE_WIDTH_2S.failure(node)
        }

        return null
    }

    private fun ruleWidth2s(attr: Attr): Boolean {
        if (attr.value.endsWith("dp")) {
            val value = attr.value.substringBefore("dp").toInt()
            if (value == 1) return false
            if (value.rem(2) != 0) {
                return true
            }
        }
        return false
    }

    companion object {

        val RULE_WIDTH_2S = Rule(
            name = "Width2s",
            errorMessage = "Width not divisible by 2"
        )
    }
}