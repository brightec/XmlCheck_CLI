package uk.co.brightec.xmlcheck

import org.w3c.dom.Node
import kotlin.reflect.KClass

data class Failure<T : Node>(
    val ruleClass: KClass<*>,
    val node: T,
    val errorMessage: String
)