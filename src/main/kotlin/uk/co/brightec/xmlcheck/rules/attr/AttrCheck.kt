package uk.co.brightec.xmlcheck.rules.attr

import org.w3c.dom.Attr
import uk.co.brightec.xmlcheck.Failure

abstract class AttrCheck {

    abstract val attrName: String

    fun runEnsured(attr: Attr): Failure<Attr>? {
        if (attr.name != attrName) {
            return null
        }
        return run(attr)
    }

    abstract fun run(attr: Attr): Failure<Attr>?

    protected fun failure(
        attr: Attr,
        errorMessage: String
    ) = Failure(
        ruleClass = this::class,
        node = attr,
        errorMessage = errorMessage
    )
}