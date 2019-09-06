package uk.co.brightec.xmlcheck.extension

import org.w3c.dom.Node
import uk.co.brightec.xmlcheck.PositionalXMLReader

internal val Node.lineNumber: Int?
    get() = getUserData(PositionalXMLReader.KEY_LINE_NUMBER) as? Int

internal val Node.columnNumber: Int?
    get() = getUserData(PositionalXMLReader.KEY_COLUMN_NUMBER) as? Int

internal val Node.systemId: String?
    get() = getUserData(PositionalXMLReader.KEY_SYSTEM_ID) as? String
