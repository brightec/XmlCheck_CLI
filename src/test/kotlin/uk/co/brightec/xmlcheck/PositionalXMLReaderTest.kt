package uk.co.brightec.xmlcheck

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import uk.co.brightec.xmlcheck.extension.lineNumber
import java.io.ByteArrayInputStream

class PositionalXMLReaderTest {

    private lateinit var reader: PositionalXMLReader

    @BeforeEach
    fun beforeEach() {
        reader = PositionalXMLReader()
    }

    @Test
    fun `Given a simple XML | When readXML with InputStream | Then moo is at line 3`() {
        // GIVEN
        val xmlString = (
                """<foo>
                    <bar>
                        <moo>Hello World!</moo>
                    </bar>
                </foo>"""
                )

        // WHEN
        val doc = reader.readXML(ByteArrayInputStream(xmlString.toByteArray()))

        // THEN
        val nodes = doc.childNodes
        for (index in 0 until nodes.length) {
            val node = nodes.item(index)
            assertEquals(index + 1, node.lineNumber)
        }
    }
}