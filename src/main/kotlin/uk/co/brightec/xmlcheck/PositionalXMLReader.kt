package uk.co.brightec.xmlcheck

import org.w3c.dom.Document
import org.w3c.dom.Element
import org.xml.sax.Attributes
import org.xml.sax.InputSource
import org.xml.sax.Locator
import org.xml.sax.SAXException
import org.xml.sax.helpers.DefaultHandler
import java.io.File
import java.io.IOException
import java.io.InputStream
import java.util.*
import javax.xml.parsers.DocumentBuilderFactory
import javax.xml.parsers.ParserConfigurationException
import javax.xml.parsers.SAXParser
import javax.xml.parsers.SAXParserFactory

class PositionalXMLReader(
    private val saxParserFactory: SAXParserFactory = SAXParserFactory.newInstance(),
    private val docBuilderFactory: DocumentBuilderFactory = DocumentBuilderFactory.newInstance()
) {

    @Throws(ParserConfigurationException::class, SAXException::class, IOException::class)
    fun readXML(inputStream: InputStream) = read { parser, defaultHandler ->
        parser.parse(inputStream, defaultHandler)
    }

    @Throws(ParserConfigurationException::class, SAXException::class, IOException::class)
    fun readXML(uri: String) = read { parser, defaultHandler ->
        parser.parse(uri, defaultHandler)
    }

    @Throws(ParserConfigurationException::class, SAXException::class, IOException::class)
    fun readXML(file: File) = read { parser, defaultHandler ->
        parser.parse(file, defaultHandler)
    }

    @Throws(ParserConfigurationException::class, SAXException::class, IOException::class)
    fun readXML(inputSource: InputSource) = read { parser, defaultHandler ->
        parser.parse(inputSource, defaultHandler)
    }

    @Throws(ParserConfigurationException::class, SAXException::class, IOException::class)
    private fun read(parse: (parser: SAXParser, defaultHandler: DefaultHandler) -> Unit): Document {
        val docBuilder = docBuilderFactory.newDocumentBuilder()
        val doc = docBuilder.newDocument()
        val parser: SAXParser = saxParserFactory.newSAXParser()
        parse(parser, LineNumberHandler(doc))
        return doc
    }

    private class LineNumberHandler(
        private val doc: Document
    ) : DefaultHandler() {

        private lateinit var locator: Locator

        val elementStack = Stack<Element>()
        val textBuffer = StringBuilder()

        override fun setDocumentLocator(locator: Locator) {
            this.locator = locator
        }

        @Throws(SAXException::class)
        override fun startElement(uri: String?, localName: String?, qName: String?, attributes: Attributes) {
            addTextIfNeeded()
            val element = doc.createElement(qName)
            for (i in 0 until attributes.length) {
                element.setAttribute(attributes.getQName(i), attributes.getValue(i))
            }
            element.setLineNumber(locator.lineNumber)
            element.setColumnNumber(locator.columnNumber)
            element.setSystemId(locator.systemId)
            elementStack.push(element)
        }

        @Throws(SAXException::class)
        override fun endElement(uri: String?, localName: String?, qName: String?) {
            addTextIfNeeded()
            val closedEl = elementStack.pop()
            if (elementStack.isEmpty()) { // Is this the root element?
                doc.appendChild(closedEl)
            } else {
                val parentEl = elementStack.peek()
                parentEl.appendChild(closedEl)
            }
        }

        @Throws(SAXException::class)
        override fun characters(ch: CharArray?, start: Int, length: Int) {
            textBuffer.append(ch, start, length)
        }

        // Outputs text accumulated under the current node
        private fun addTextIfNeeded() {
            if (textBuffer.isNotEmpty()) {
                val el = elementStack.peek()
                val textNode = doc.createTextNode(textBuffer.toString())
                el.appendChild(textNode)
                textBuffer.delete(0, textBuffer.length)
            }
        }

        private fun Element.setLineNumber(value: Int?) {
            setUserData(KEY_LINE_NUMBER, value, null)
        }

        private fun Element.setColumnNumber(value: Int?) {
            setUserData(KEY_COLUMN_NUMBER, value, null)
        }

        private fun Element.setSystemId(value: String?) {
            setUserData(KEY_SYSTEM_ID, value, null)
        }
    }

    companion object {

        const val KEY_LINE_NUMBER = "PositionalXMLReader.KEY_LINE_NUMBER"
        const val KEY_COLUMN_NUMBER = "PositionalXMLReader.KEY_COLUMN_NUMBER"
        const val KEY_SYSTEM_ID = "PositionalXMLReader.KEY_SYSTEM_ID"
    }
}