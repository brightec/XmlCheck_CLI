package uk.co.brightec.xmlcheck.testutil

import org.junit.jupiter.api.Assertions.assertTrue

object Assertions {

    fun assertContains(expected: String, parent: String) {
        assertTrue(parent.contains(expected)) {
            "String does not contain expected:\n\nString:\n$parent\n\nEXPECTED:\n$expected"
        }
    }
}