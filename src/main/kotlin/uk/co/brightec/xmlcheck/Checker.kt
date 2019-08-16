package uk.co.brightec.xmlcheck

import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.parameters.arguments.argument
import com.github.ajalt.clikt.parameters.arguments.default
import org.w3c.dom.Document
import org.w3c.dom.Node
import org.w3c.dom.NodeList
import org.xml.sax.InputSource
import uk.co.brightec.xmlcheck.rules.NodeCheck
import uk.co.brightec.xmlcheck.rules.android.*
import uk.co.brightec.xmlcheck.rules.android.color.TextColor
import uk.co.brightec.xmlcheck.rules.android.color.Tint
import uk.co.brightec.xmlcheck.rules.android.constraint.*
import uk.co.brightec.xmlcheck.rules.android.margin.LayoutMarginBottom
import uk.co.brightec.xmlcheck.rules.android.margin.LayoutMarginEnd
import uk.co.brightec.xmlcheck.rules.android.margin.LayoutMarginStart
import uk.co.brightec.xmlcheck.rules.android.margin.LayoutMarginTop
import java.io.File
import java.io.StringReader
import javax.xml.parsers.DocumentBuilderFactory

class Checker : CliktCommand() {

    // TODO : Remove the default for production
    private val path by argument(
        name = "path",
        help = "The path to your xml dir"
    ).default("./src/test/resources/xmlfiles")

    private val colorNodeChecks: List<NodeCheck> = listOf(
        TextColor(), Tint()
    )
    private val constraintNodeChecks: List<NodeCheck> = listOf(
        LayoutConstraintStartToStartOf(), LayoutConstraintStartToEndOf(),
        LayoutConstraintEndToStartOf(), LayoutConstraintEndToEndOf(),
        LayoutConstraintTopToTopOf(), LayoutConstraintTopToBottomOf(),
        LayoutConstraintBottomToTopOf(), LayoutConstraintBottomToBottomOf()
    )
    private val marginsChecks: List<NodeCheck> = listOf(
        LayoutMarginStart(), LayoutMarginEnd(), LayoutMarginTop(), LayoutMarginBottom()
    )
    private val allNodeChecks: List<NodeCheck> = arrayListOf(
        Id(),
        TextSize()
    ).apply {
        addAll(colorNodeChecks)
        addAll(constraintNodeChecks)
        addAll(marginsChecks)
    }

    private val dbFactory = DocumentBuilderFactory.newInstance()
    private val dBuilder = dbFactory.newDocumentBuilder()

    override fun run() {
        echo("XmlChecks RUNNING")

        val files = File(path)
        if (!files.isDirectory) {
            error("Must supply a directory")
        }
        if (files.listFiles().isNullOrEmpty()) {
            error("No files in directory")
        }
        for (file in files.listFiles()!!) {
            val doc = getXmlDoc(file)
            if (doc == null) {
                echo("Not an XML file - ${file.path}")
                continue
            }

            examineNodes(doc.childNodes, file.name)
            echo("XmlChecks - ${file.name} PASSED")
        }

        echo("XmlChecks ALL PASSED")
    }

    private fun getXmlDoc(xmlFile: File): Document? {
        if (xmlFile.extension != "xml") {
            return null
        }

        val xmlInput = InputSource(StringReader(xmlFile.readText()))
        return dBuilder.parse(xmlInput)
    }

    private fun examineNodes(nodes: NodeList, tag: String) {
        for (index in 0 until nodes.length) {
            val node = nodes.item(index)
            examineNode(node, tag)
            if (node.hasChildNodes()) {
                examineNodes(node.childNodes, tag)
            }
        }
    }

    private fun examineNode(node: Node, tag: String) {
        allNodeChecks.forEach { nodeCheck ->
            node.attributes?.getNamedItem(nodeCheck.nodeName)?.let { node ->
                nodeCheck.runEnsured(node, tag)
            }
        }
    }
}