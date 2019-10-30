package uk.co.brightec.xmlcheck.rules.attr.android

import org.w3c.dom.Attr
import uk.co.brightec.xmlcheck.Constants.ATTR_NAMESPACE_APP
import uk.co.brightec.xmlcheck.Failure
import uk.co.brightec.xmlcheck.rules.attr.AttrRule

class ConstraintIdPlus : AttrRule(
    name = "ConstraintIdPlus",
    attrNames = listOf(
        "$ATTR_NAMESPACE_APP:layout_constraintBottom_toBottomOf",
        "$ATTR_NAMESPACE_APP:layout_constraintBottom_toTopOf",
        "$ATTR_NAMESPACE_APP:layout_constraintEnd_toEndOf",
        "$ATTR_NAMESPACE_APP:layout_constraintEnd_toStartOf",
        "$ATTR_NAMESPACE_APP:layout_constraintStart_toEndOf",
        "$ATTR_NAMESPACE_APP:layout_constraintStart_toStartOf",
        "$ATTR_NAMESPACE_APP:layout_constraintTop_toBottomOf",
        "$ATTR_NAMESPACE_APP:layout_constraintTop_toTopOf"
    )
) {

    override fun run(node: Attr): Failure<Attr>? {
        return if (node.value == "parent" || node.value.startsWith("@+id/")) {
            null
        } else {
            failure(node, ERROR_MESSAGE)
        }
    }

    companion object {

        const val ERROR_MESSAGE = "Doesn't start with @+id/"
    }
}