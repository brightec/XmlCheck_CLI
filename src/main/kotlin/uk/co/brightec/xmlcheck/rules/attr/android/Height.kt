package uk.co.brightec.xmlcheck.rules.attr.android

import org.w3c.dom.Attr
import uk.co.brightec.xmlcheck.Constants.ATTR_NAMESPACE_ANDROID
import uk.co.brightec.xmlcheck.Failure
import uk.co.brightec.xmlcheck.rules.Rule
import uk.co.brightec.xmlcheck.rules.RuleName
import uk.co.brightec.xmlcheck.rules.attr.AttrCheck

class Height : AttrCheck() {

    override val rules: List<Rule>
        get() = listOf(
            RULE_HEIGHT_2S
        )
    override val attrName: String
        get() = "${ATTR_NAMESPACE_ANDROID}:layout_height"

    override fun runCheck(node: Attr, suppressions: Collection<RuleName>): Failure<Attr>? {
        if (!suppressions.contains(RULE_HEIGHT_2S.name) && ruleHeight2s(node)) {
            return RULE_HEIGHT_2S.failure(node)
        }

        return null
    }

    private fun ruleHeight2s(attr: Attr): Boolean {
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

        val RULE_HEIGHT_2S = Rule(
            name = "Height2s",
            errorMessage = "Height not divisible by 2"
        )
    }
}