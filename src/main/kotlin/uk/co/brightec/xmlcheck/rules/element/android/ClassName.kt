package uk.co.brightec.xmlcheck.rules.element.android

import org.w3c.dom.Element
import uk.co.brightec.xmlcheck.Failure
import uk.co.brightec.xmlcheck.rules.Rule
import uk.co.brightec.xmlcheck.rules.RuleName
import uk.co.brightec.xmlcheck.rules.element.ElementCheck

class ClassName : ElementCheck() {

    override val rules: List<Rule>
        get() = listOf(
            RULE_CLASS_MATERIAL_BUTTON
        )

    override fun run(node: Element, suppressions: List<RuleName>): Failure<Element>? {
        if (!suppressions.contains(RULE_CLASS_MATERIAL_BUTTON.name) && ruleClassMaterialButton(node)) {
            return RULE_CLASS_MATERIAL_BUTTON.failure(node)
        }

        return null
    }

    private fun ruleClassMaterialButton(element: Element) =
        element.tagName.split(".").last() == "Button"

    companion object {

        val RULE_CLASS_MATERIAL_BUTTON = Rule(
            name = "ClassMaterialButton",
            errorMessage = "Should use MaterialButton"
        )
    }
}