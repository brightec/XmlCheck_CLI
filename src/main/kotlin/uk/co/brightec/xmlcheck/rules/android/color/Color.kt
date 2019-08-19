package uk.co.brightec.xmlcheck.rules.android.color

import org.w3c.dom.Attr
import uk.co.brightec.xmlcheck.Failure
import uk.co.brightec.xmlcheck.rules.AttrCheck

abstract class Color : AttrCheck() {

    override fun run(attr: Attr): Failure? {
        if (attr.value.startsWith("#")) {
            return failure(attr, "Color not in resources")
        }

        return null
    }
}