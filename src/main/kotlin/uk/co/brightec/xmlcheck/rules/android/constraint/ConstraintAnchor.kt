package uk.co.brightec.xmlcheck.rules.android.constraint

import org.w3c.dom.Attr
import uk.co.brightec.xmlcheck.Failure
import uk.co.brightec.xmlcheck.rules.AttrCheck

abstract class ConstraintAnchor : AttrCheck() {

    override fun run(attr: Attr): Failure? {
        if (attr.value != "parent" && !attr.value.startsWith("@+id/")) {
            return failure(attr, "Doesn't start with @+id/")
        }

        return null
    }
}