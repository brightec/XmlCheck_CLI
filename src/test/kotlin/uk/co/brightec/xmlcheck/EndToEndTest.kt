package uk.co.brightec.xmlcheck

import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import uk.co.brightec.xmlcheck.testutil.Large
import java.io.ByteArrayOutputStream
import java.io.PrintStream

@Large
private class EndToEndTest {

    private lateinit var outContent: ByteArrayOutputStream
    private lateinit var errContent: ByteArrayOutputStream
    private lateinit var originalOut: PrintStream
    private lateinit var originalErr: PrintStream

    @BeforeEach
    fun beforeEach() {
        originalOut = System.out
        originalErr = System.err

        outContent = ByteArrayOutputStream()
        errContent = ByteArrayOutputStream()
        System.setOut(PrintStream(outContent))
        System.setErr(PrintStream(errContent))
    }

    @AfterEach
    fun afterEach() {
        System.setOut(originalOut)
        System.setErr(originalErr)
    }

    @Test
    fun `Given TextSizeUnit Error | When running checker | Then fails with log`() {
        // GIVEN
        val path = "./src/test/resources/files/failures/TextSizeUnit"

        // WHEN
        val result = assertThrows<IllegalStateException> {
            main(
                arrayOf(
                    path
                )
            )
        }

        // THEN
        assertNotNull(result)
        val output = errContent.toString()
        val expectedLog1 = """
            ./src/test/resources/files/failures/TextSizeUnit/TextSizeUnit-dp.xml
            (TextSizeUnit) Line:3: android:textSize="12dp" - Should be specified in sp
            """.trimIndent()
        assertContains(expectedLog1, output)
        val expectedLog2 = """
            ./src/test/resources/files/failures/TextSizeUnit/TextSizeUnit-px.xml
            (TextSizeUnit) Line:3: android:textSize="12px" - Should be specified in sp
            """.trimIndent()
        assertContains(expectedLog2, output)
    }

    @Test
    fun `Given ClassMaterialButton Error | When running checker | Then fails with log`() {
        // GIVEN
        val path = "./src/test/resources/files/failures/ClassMaterialButton"

        // WHEN
        val result = assertThrows<IllegalStateException> {
            main(
                arrayOf(
                    path
                )
            )
        }

        // THEN
        assertNotNull(result)
        val output = errContent.toString()
        val expectedLog = """
            ./src/test/resources/files/failures/ClassMaterialButton/ClassMaterialButton.xml
            (ClassMaterialButton) Line:2: Button - Should use MaterialButton
            """.trimIndent()
        assertContains(expectedLog, output)
    }

    @Test
    fun `Given IdPlus Error | When running checker | Then fails with log`() {
        // GIVEN
        val path = "./src/test/resources/files/failures/IdPlus"

        // WHEN
        val result = assertThrows<Exception> {
            main(
                arrayOf(
                    path
                )
            )
        }

        // THEN
        assertNotNull(result)
        val output = errContent.toString()
        val expectedLog = """
            ./src/test/resources/files/failures/IdPlus/IdPlus.xml
            (IdPlus) Line:3: android:id="@id/something" - Doesn't start with @+id/
            """.trimIndent()
        assertContains(expectedLog, output)
    }

    @Test
    fun `Given IdNaming Error | When running checker | Then fails with log`() {
        // GIVEN
        val path = "./src/test/resources/files/failures/IdNaming"

        // WHEN
        val result = assertThrows<IllegalStateException> {
            main(
                arrayOf(
                    path
                )
            )
        }

        // THEN
        assertNotNull(result)
        val output = errContent.toString()
        val expectedLog1 = """
            ./src/test/resources/files/failures/IdNaming/IdNaming-Buttons.xml
            (IdNaming) Line:3: android:id="@+id/something" - Id for ImageButton doesn't conform to naming convention
            """.trimIndent()
        assertContains(expectedLog1, output)
        val expectedLog2 = """
            ./src/test/resources/files/failures/IdNaming/IdNaming-Buttons.xml
            (IdNaming) Line:3: android:id="@+id/something" - Id for ImageButton doesn't conform to naming convention
            """.trimIndent()
        assertContains(expectedLog2, output)
        val expectedLog3 = """
            ./src/test/resources/files/failures/IdNaming/IdNaming-GifImageView.xml
            (IdNaming) Line:3: android:id="@+id/something" - Id for GifImageView doesn't conform to naming convention
            """.trimIndent()
        assertContains(expectedLog3, output)
        val expectedLog4 = """
            ./src/test/resources/files/failures/IdNaming/IdNaming-Guideline.xml
            (IdNaming) Line:3: android:id="@+id/something" - Id for Guideline doesn't conform to naming convention
            """.trimIndent()
        assertContains(expectedLog4, output)
        val expectedLog5 = """
            ./src/test/resources/files/failures/IdNaming/IdNaming-HorizontalScrollView.xml
            (IdNaming) Line:3: android:id="@+id/something" - Id for HorizontalScrollView doesn't conform to naming convention
            """.trimIndent()
        assertContains(expectedLog5, output)
        val expectedLog6 = """
            ./src/test/resources/files/failures/IdNaming/IdNaming-Words.xml
            (IdNaming) Line:3: android:id="@+id/something" - Id for SomeOtherClassName doesn't conform to naming convention
            """.trimIndent()
        assertContains(expectedLog6, output)
    }

    @Test
    fun `Given Margin2s Error | When running checker | Then fails with log`() {
        // GIVEN
        val path = "./src/test/resources/files/failures/Margin2s"

        // WHEN
        val result = assertThrows<IllegalStateException> {
            main(
                arrayOf(
                    path
                )
            )
        }

        // THEN
        assertNotNull(result)
        val output = errContent.toString()
        val expectedLog1 = """
            ./src/test/resources/files/failures/Margin2s/Margin2s-LayoutMarginBottom.xml
            (Margin2s) Line:3: android:layout_marginBottom="3dp" - Margin not divisible by 2
            """.trimIndent()
        assertContains(expectedLog1, output)
        val expectedLog2 = """
            ./src/test/resources/files/failures/Margin2s/Margin2s-LayoutMarginEnd.xml
            (Margin2s) Line:3: android:layout_marginEnd="3dp" - Margin not divisible by 2
            """.trimIndent()
        assertContains(expectedLog2, output)
        val expectedLog3 = """
            ./src/test/resources/files/failures/Margin2s/Margin2s-LayoutMarginStart.xml
            (Margin2s) Line:3: android:layout_marginStart="3dp" - Margin not divisible by 2
            """.trimIndent()
        assertContains(expectedLog3, output)
        val expectedLog4 = """
            ./src/test/resources/files/failures/Margin2s/Margin2s-LayoutMarginTop.xml
            (Margin2s) Line:3: android:layout_marginTop="3dp" - Margin not divisible by 2
            """.trimIndent()
        assertContains(expectedLog4, output)
    }

    @Test
    fun `Given ConstraintIdPlus Error | When running checker | Then fails with log`() {
        // GIVEN
        val path = "./src/test/resources/files/failures/ConstraintIdPlus"

        // WHEN
        val result = assertThrows<Exception> {
            main(
                arrayOf(
                    path
                )
            )
        }

        // THEN
        assertNotNull(result)
        val output = errContent.toString()
        val expectedLog1 = """
            ./src/test/resources/files/failures/ConstraintIdPlus/ConstraintIdPlus-LayoutConstraintBottomToBottomOf.xml
            (ConstraintIdPlus) Line:3: app:layout_constraintBottom_toBottomOf="@id/something" - Doesn't start with @+id/
            """.trimIndent()
        assertContains(expectedLog1, output)
        val expectedLog2 = """
            ./src/test/resources/files/failures/ConstraintIdPlus/ConstraintIdPlus-LayoutConstraintBottomToTopOf.xml
            (ConstraintIdPlus) Line:3: app:layout_constraintBottom_toTopOf="@id/something" - Doesn't start with @+id/
            """.trimIndent()
        assertContains(expectedLog2, output)
        val expectedLog3 = """
            ./src/test/resources/files/failures/ConstraintIdPlus/ConstraintIdPlus-LayoutConstraintEndToEndOf.xml
            (ConstraintIdPlus) Line:3: app:layout_constraintEnd_toEndOf="@id/something" - Doesn't start with @+id/
            """.trimIndent()
        assertContains(expectedLog3, output)
        val expectedLog4 = """
            ./src/test/resources/files/failures/ConstraintIdPlus/ConstraintIdPlus-LayoutConstraintEndToStartOf.xml
            (ConstraintIdPlus) Line:3: app:layout_constraintEnd_toStartOf="@id/something" - Doesn't start with @+id/
            """.trimIndent()
        assertContains(expectedLog4, output)
        val expectedLog5 = """
            ./src/test/resources/files/failures/ConstraintIdPlus/ConstraintIdPlus-LayoutConstraintStartToEndOf.xml
            (ConstraintIdPlus) Line:3: app:layout_constraintStart_toEndOf="@id/something" - Doesn't start with @+id/
            """.trimIndent()
        assertContains(expectedLog5, output)
        val expectedLog6 = """
            ./src/test/resources/files/failures/ConstraintIdPlus/ConstraintIdPlus-LayoutConstraintStartToStartOf.xml
            (ConstraintIdPlus) Line:3: app:layout_constraintStart_toStartOf="@id/something" - Doesn't start with @+id/
            """.trimIndent()
        assertContains(expectedLog6, output)
        val expectedLog7 = """
            ./src/test/resources/files/failures/ConstraintIdPlus/ConstraintIdPlus-LayoutConstraintTopToBottomOf.xml
            (ConstraintIdPlus) Line:3: app:layout_constraintTop_toBottomOf="@id/something" - Doesn't start with @+id/
            """.trimIndent()
        assertContains(expectedLog7, output)
        val expectedLog8 = """
            ./src/test/resources/files/failures/ConstraintIdPlus/ConstraintIdPlus-LayoutConstraintTopToTopOf.xml
            (ConstraintIdPlus) Line:3: app:layout_constraintTop_toTopOf="@id/something" - Doesn't start with @+id/
            """.trimIndent()
        assertContains(expectedLog8, output)
    }

    @Test
    fun `Given ColorRes Error | When running checker | Then fails with log`() {
        // GIVEN
        val path = "./src/test/resources/files/failures/ColorRes"

        // WHEN
        val result = assertThrows<IllegalStateException> {
            main(
                arrayOf(
                    path
                )
            )
        }

        // THEN
        assertNotNull(result)
        val output = errContent.toString()
        val expectedLog1 = """
            ./src/test/resources/files/failures/ColorRes/ColorRes-TextColor.xml
            (ColorRes) Line:3: android:textColor="#123456" - Color not in resources
            """.trimIndent()
        assertContains(expectedLog1, output)
        val expectedLog2 = """
            ./src/test/resources/files/failures/ColorRes/ColorRes-Tint.xml
            (ColorRes) Line:3: android:tint="#123456" - Color not in resources
            """.trimIndent()
        assertContains(expectedLog2, output)
    }

    private fun assertContains(expected: String, str: String) {
        assertTrue(str.contains(expected)) {
            "String does not contain expected:\n\nString:\n$str\n\nEXPECTED:\n$expected"
        }
    }
}