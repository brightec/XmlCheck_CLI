package uk.co.brightec.xmlcheck

import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.parameters.arguments.argument
import com.github.ajalt.clikt.parameters.arguments.multiple
import com.github.ajalt.clikt.parameters.options.default
import com.github.ajalt.clikt.parameters.options.option
import com.github.ajalt.clikt.parameters.options.split
import com.github.ajalt.clikt.parameters.types.file
import org.w3c.dom.*
import uk.co.brightec.xmlcheck.Constants.ATTR_NAMESPACE_TOOLS
import uk.co.brightec.xmlcheck.extension.lineNumber
import uk.co.brightec.xmlcheck.extension.systemId
import uk.co.brightec.xmlcheck.rules.RuleName
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

// TODO : Add units test

class Checker : CliktCommand() {

    private val paths by argument(
        name = "paths",
        help = "The paths to your xml directories"
    ).file(
        exists = true,
        fileOkay = false,
        folderOkay = true,
        writable = false,
        readable = true
    ).multiple()
    private val excludes: List<RuleName> by option(
        "-x", "--exclude",
        help = "The rules you want to exclude (e.g. xmlcheck -x Rule1,Rule2)"
    ).split(",").default(emptyList())

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

        val files = arrayListOf<File>()
        paths.forEach { dir ->
            val dirFiles = dir.listFiles()
            if (dirFiles.isNullOrEmpty()) {
                echo(message = "Empty dir: $dir")
            } else {
                files.addAll(dirFiles)
            }
        }
        val failures = arrayListOf<Failure<*>>()
        for (file in files) {
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
                                message = "(${it.ruleName}) Line:${node.lineNumber}: ${node.tagName} - ${it.errorMessage}",
                                err = true
                            )
                        is Attr ->
                            echo(
                                message = "(${it.ruleName}) Line:${node.ownerElement.lineNumber}: $node - ${it.errorMessage}",
                                err = true
                            )
                        else ->
                            echo(
                                message = "(${it.ruleName}) $node - ${it.errorMessage}",
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

        val suppressions = determineSuppressions(element).union(excludes)

        allElementChecks.forEach { elementCheck ->
            elementCheck.check(element, suppressions)?.let {
                failures.add(it)
            }
        }

        allAttrChecks.forEach { attrCheck ->
            element.attributes?.getNamedItem(attrCheck.attrName)?.let { node ->
                check(node is Attr)
                attrCheck.check(node, suppressions)?.let {
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

    private fun determineSuppressions(element: Element): List<RuleName> {
        element.attributes?.getNamedItem("$ATTR_NAMESPACE_TOOLS:ignore")?.let { node ->
            check(node is Attr)
            return node.value.split(",")
        }
        return emptyList()
    }
}