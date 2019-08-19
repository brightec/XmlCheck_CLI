package uk.co.brightec.xmlcheck.rules

import org.w3c.dom.Attr
import uk.co.brightec.xmlcheck.Failure

abstract class AttrCheck {

    abstract val attrName: String

    fun runEnsured(attr: Attr): Failure? {
        if (attr.name != attrName) {
            return null
        }
        return run(attr)
    }

    abstract fun run(attr: Attr): Failure?

    protected fun failure(
        attr: Attr,
        errorMessage: String
    ) = Failure(
        ruleClass = this::class,
        attr = attr,
        errorMessage = errorMessage
    )
}