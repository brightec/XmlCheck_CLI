package uk.co.brightec.xmlcheck

import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.parameters.arguments.argument
import com.github.ajalt.clikt.parameters.arguments.default
import org.w3c.dom.*
import uk.co.brightec.xmlcheck.extension.lineNumber
import uk.co.brightec.xmlcheck.extension.systemId
import uk.co.brightec.xmlcheck.rules.attr.AttrCheck
import uk.co.brightec.xmlcheck.rules.attr.android.Id
import uk.co.brightec.xmlcheck.rules.attr.android.TextSize
import uk.co.brightec.xmlcheck.rules.attr.android.color.TextColor
import uk.co.brightec.xmlcheck.rules.attr.android.color.Tint
import uk.co.brightec.xmlcheck.rules.attr.android.constraint.*
import uk.co.brightec.xmlcheck.rules.attr.android.margin.LayoutMarginBottom
import uk.co.brightec.xmlcheck.rules.attr.android.margin.LayoutMarginEnd
import uk.co.brightec.xmlcheck.rules.attr.android.margin.LayoutMarginStart
import uk.co.brightec.xmlcheck.rules.attr.android.margin.LayoutMarginTop
import uk.co.brightec.xmlcheck.rules.element.ElementCheck
import uk.co.brightec.xmlcheck.rules.element.android.ClassName
import java.io.File

class Checker : CliktCommand() {

    // TODO : Remove the default for production
    private val path by argument(
        name = "path",
        help = "The path to your xml dir"
    ).default("./src/test/resources/xmlfiles")

    private val colorAttrChecks: List<AttrCheck> = listOf(
        TextColor(), Tint()
    )
    private val constraintAttrChecks: List<AttrCheck> = listOf(
        LayoutConstraintStartToStartOf(), LayoutConstraintStartToEndOf(),
        LayoutConstraintEndToStartOf(), LayoutConstraintEndToEndOf(),
        LayoutConstraintTopToTopOf(), LayoutConstraintTopToBottomOf(),
        LayoutConstraintBottomToTopOf(), LayoutConstraintBottomToBottomOf()
    )
    private val marginAttrChecks: List<AttrCheck> = listOf(
        LayoutMarginStart(), LayoutMarginEnd(), LayoutMarginTop(), LayoutMarginBottom()
    )
    private val allAttrChecks: List<AttrCheck> = arrayListOf(
        Id(),
        TextSize()
    ).apply {
        addAll(colorAttrChecks)
        addAll(constraintAttrChecks)
        addAll(marginAttrChecks)
    }

    private val allElementChecks: List<ElementCheck> = listOf(
        ClassName()
    )

    override fun run() {
        echo(message = "XmlChecks RUNNING")

        val files = File(path)
        check(files.isDirectory) {
            "Must supply a directory"
        }
        check(!files.listFiles().isNullOrEmpty()) {
            "No files in directory"
        }
        val failures = arrayListOf<Failure<*>>()
        for (file in files.listFiles()!!) {
            val doc = getXmlDoc(file)
            if (doc == null) {
                echo(message = "Not an XML file - ${file.path}")
                continue
            }

            val fileFailures = examineNodes(doc.childNodes)
            if (fileFailures.isEmpty()) {
                echo(message = "${file.name} PASSED")
            } else {
                val systemId = determineSystemId(fileFailures.first().node)
                if (systemId.isNullOrBlank()) {
                    echo(message = file.path, err = true)
                } else {
                    echo(message = "$systemId", err = true)
                }
                fileFailures.forEach {
                    when (val node = it.node) {
                        is Element ->
                            echo(
                                message = "Line:${node.lineNumber}: ${node.tagName} - ${it.errorMessage}",
                                err = true
                            )
                        is Attr ->
                            echo(
                                message = "Line:${node.ownerElement.lineNumber}: $node - ${it.errorMessage}",
                                err = true
                            )
                        else ->
                            echo(
                                message = "$node - ${it.errorMessage}",
                                err = true
                            )
                    }
                }
            }
            failures.addAll(fileFailures)
        }

        check(failures.isEmpty()) {
            "XmlChecks FAILED"
        }
        echo(message = "XmlChecks ALL PASSED")
    }

    private fun getXmlDoc(xmlFile: File): Document? {
        if (xmlFile.extension != "xml") {
            return null
        }

        val reader = PositionalXMLReader()
        return reader.readXML(xmlFile)
    }

    private fun examineNodes(nodes: NodeList): List<Failure<*>> {
        val failures = arrayListOf<Failure<*>>()
        (0 until nodes.length).map { index ->
            nodes.item(index)
        }.filterIsInstance(
            Element::class.java
        ).forEach { element ->
            examineElement(element).let {
                failures.addAll(it)
            }
            if (element.hasChildNodes()) {
                examineNodes(element.childNodes).let {
                    failures.addAll(it)
                }
            }
        }
        return failures
    }

    private fun examineElement(element: Element): List<Failure<*>> {
        val failures = arrayListOf<Failure<*>>()

        allElementChecks.forEach { elementCheck ->
            elementCheck.runEnsured(element)?.let {
                failures.add(it)
            }
        }

        allAttrChecks.forEach { attrCheck ->
            element.attributes?.getNamedItem(attrCheck.attrName)?.let { node ->
                check(node is Attr)
                attrCheck.runEnsured(node)?.let {
                    failures.add(it)
                }
            }
        }

        return failures
    }

    private fun determineSystemId(node: Node): String? {
        var count = 0
        var elementNode = node

        // Find the nearest parent of type Element
        // We have a counter so that we can limit how many recursions we do, as a performance limit
        do {
            if (elementNode.parentNode == null) {
                return null
            }
            elementNode = elementNode.parentNode
            count++
        } while (elementNode !is Element && count < 10)

        return elementNode.systemId
    }
}