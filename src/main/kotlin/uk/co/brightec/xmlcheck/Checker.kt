package uk.co.brightec.xmlcheck

import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.parameters.arguments.argument
import com.github.ajalt.clikt.parameters.arguments.default
import org.w3c.dom.Attr
import org.w3c.dom.Document
import org.w3c.dom.Node
import org.w3c.dom.NodeList
import uk.co.brightec.xmlcheck.extension.lineNumber
import uk.co.brightec.xmlcheck.extension.systemId
import uk.co.brightec.xmlcheck.rules.AttrCheck
import uk.co.brightec.xmlcheck.rules.android.Id
import uk.co.brightec.xmlcheck.rules.android.TextSize
import uk.co.brightec.xmlcheck.rules.android.color.TextColor
import uk.co.brightec.xmlcheck.rules.android.color.Tint
import uk.co.brightec.xmlcheck.rules.android.constraint.*
import uk.co.brightec.xmlcheck.rules.android.margin.LayoutMarginBottom
import uk.co.brightec.xmlcheck.rules.android.margin.LayoutMarginEnd
import uk.co.brightec.xmlcheck.rules.android.margin.LayoutMarginStart
import uk.co.brightec.xmlcheck.rules.android.margin.LayoutMarginTop
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

    override fun run() {
        echo(message = "XmlChecks RUNNING")

        val files = File(path)
        check(files.isDirectory) {
            "Must supply a directory"
        }
        check(!files.listFiles().isNullOrEmpty()) {
            "No files in directory"
        }
        val failures = arrayListOf<Failure>()
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
                echo(message = "${fileFailures.first().attr.ownerElement.systemId}", err = true)
                fileFailures.forEach {
                    val ownerElement = it.attr.ownerElement
                    echo(
                        message = "Line:${ownerElement.lineNumber}: ${it.attr} - ${it.errorMessage}",
                        err = true
                    )
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

    private fun examineNodes(nodes: NodeList): List<Failure> {
        val failures = arrayListOf<Failure>()
        for (index in 0 until nodes.length) {
            val node = nodes.item(index)
            examineNode(node).let {
                failures.addAll(it)
            }
            if (node.hasChildNodes()) {
                examineNodes(node.childNodes).let {
                    failures.addAll(it)
                }
            }
        }
        return failures
    }

    private fun examineNode(node: Node): List<Failure> {
        val failures = arrayListOf<Failure>()
        allAttrChecks.forEach { nodeCheck ->
            node.attributes?.getNamedItem(nodeCheck.attrName)?.let { node ->
                check(node is Attr)
                nodeCheck.runEnsured(node)?.let {
                    failures.add(it)
                }
            }
        }
        return failures
    }
}