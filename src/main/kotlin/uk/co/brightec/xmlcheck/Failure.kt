package uk.co.brightec.xmlcheck

import org.w3c.dom.Attr
import kotlin.reflect.KClass

data class Failure(
    val ruleClass: KClass<*>,
    val attr: Attr,
    val errorMessage: String
)