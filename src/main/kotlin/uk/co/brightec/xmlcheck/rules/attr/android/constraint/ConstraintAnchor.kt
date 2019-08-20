package uk.co.brightec.xmlcheck.rules.attr.android.constraint

import org.w3c.dom.Attr
import uk.co.brightec.xmlcheck.Failure
import uk.co.brightec.xmlcheck.rules.attr.AttrCheck

abstract class ConstraintAnchor : AttrCheck() {

    override fun run(attr: Attr): Failure<Attr>? {
        if (attr.value != "parent" && !attr.value.startsWith("@+id/")) {
            return failure(attr, "Doesn't start with @+id/")
        }

        return null
    }
}