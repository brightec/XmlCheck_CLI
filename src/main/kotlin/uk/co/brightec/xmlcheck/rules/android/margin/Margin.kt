package uk.co.brightec.xmlcheck.rules.android.margin

import org.w3c.dom.Attr
import uk.co.brightec.xmlcheck.Failure
import uk.co.brightec.xmlcheck.rules.AttrCheck

abstract class Margin : AttrCheck() {

    override fun run(attr: Attr): Failure? {
        if (attr.value.endsWith("dp")) {
            val value = attr.value.substringBefore("dp").toInt()
            if (value.rem(2) != 0) {
                return failure(attr, "Margin not divisible by 2")
            }
        }

        return null
    }
}