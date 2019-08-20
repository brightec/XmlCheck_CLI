package uk.co.brightec.xmlcheck.rules.attr.android.color

import org.w3c.dom.Attr
import uk.co.brightec.xmlcheck.Failure
import uk.co.brightec.xmlcheck.rules.attr.AttrCheck

abstract class Color : AttrCheck() {

    override fun run(attr: Attr): Failure<Attr>? {
        if (attr.value.startsWith("#")) {
            return failure(attr, "Color not in resources")
        }

        return null
    }
}