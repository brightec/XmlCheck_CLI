package uk.co.brightec.xmlcheck.rules.attr.android

import org.w3c.dom.Attr
import uk.co.brightec.xmlcheck.Constants.ATTR_NAMESPACE_ANDROID
import uk.co.brightec.xmlcheck.Failure
import uk.co.brightec.xmlcheck.rules.Rule
import uk.co.brightec.xmlcheck.rules.RuleName
import uk.co.brightec.xmlcheck.rules.attr.AttrCheck

class TextSize : AttrCheck() {

    override val rules: List<Rule>
        get() = listOf(
            RULE_TEXT_SIZE_UNIT
        )
    override val attrName: String
        get() = "$ATTR_NAMESPACE_ANDROID:textSize"

    override fun runCheck(node: Attr, suppressions: Collection<RuleName>): Failure<Attr>? {
        if (!suppressions.contains(RULE_TEXT_SIZE_UNIT.name) && ruleTextSizeUnit(node)) {
            return RULE_TEXT_SIZE_UNIT.failure(node)
        }

        return null
    }

    private fun ruleTextSizeUnit(attr: Attr) =
        !attr.value.endsWith("sp")

    companion object {

        val RULE_TEXT_SIZE_UNIT = Rule(
            name = "TextSizeUnit",
            errorMessage = "Should be specified in sp"
        )
    }
}