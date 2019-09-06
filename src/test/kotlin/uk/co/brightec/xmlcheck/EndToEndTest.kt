package uk.co.brightec.xmlcheck

import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import uk.co.brightec.xmlcheck.testutil.Assertions.assertContains
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
    fun `Given case1 | When run checker | Then logs running and passed`() {
        // GIVEN
        val path = "./src/test/resources/files/cases/case1"

        // WHEN
        main(
            arrayOf(
                path
            )
        )

        // THEN
        val outputOut = outContent.toString()
        val expectedLog1 = """
            XmlChecks RUNNING
            """.trimIndent()
        assertContains(expectedLog1, outputOut)
        val expectedLog2 = """
            case1.xml PASSED
            """.trimIndent()
        assertContains(expectedLog2, outputOut)
        val expectedLog3 = """
            XmlChecks ALL PASSED
            """.trimIndent()
        assertContains(expectedLog3, outputOut)
    }

    @Test
    fun `Given case2 | When run checker | Then logs running and failures`() {
        // GIVEN
        val path = "./src/test/resources/files/cases/case2"

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
        val outputOut = outContent.toString()
        val outputErr = errContent.toString()
        val expectedLog1 = """
            XmlChecks RUNNING
            """.trimIndent()
        assertContains(expectedLog1, outputOut)
        val expectedLog2 = """
            ./src/test/resources/files/cases/case2/case2.xml
            (IdNaming) Line:62: android:id="@+id/textinput_from" - Id for com.trainsplit.trainsplit.widgets.TextInputWidget doesn't conform to naming convention
            (IdNaming) Line:91: android:id="@+id/textinput_to" - Id for com.trainsplit.trainsplit.widgets.TextInputWidget doesn't conform to naming convention
            (ClassMaterialButton) Line:119: Button - Should use MaterialButton
            """.trimIndent()
        assertContains(expectedLog2, outputErr)
    }

    @Test
    fun `Given TextSizeUnit failure | When run checker | Then fails with log`() {
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
        val outputErr = errContent.toString()
        val expectedLog1 = """
            ./src/test/resources/files/failures/TextSizeUnit/TextSizeUnit-dp.xml
            (TextSizeUnit) Line:3: android:textSize="12dp" - Should be specified in sp
            """.trimIndent()
        assertContains(expectedLog1, outputErr)
        val expectedLog2 = """
            ./src/test/resources/files/failures/TextSizeUnit/TextSizeUnit-px.xml
            (TextSizeUnit) Line:3: android:textSize="12px" - Should be specified in sp
            """.trimIndent()
        assertContains(expectedLog2, outputErr)
    }

    @Test
    fun `Given TextSizeUnit success | When run checker | Then passes, no errors`() {
        // GIVEN
        val path = "./src/test/resources/files/success/TextSizeUnit"

        // WHEN
        main(
            arrayOf(
                path
            )
        )

        // THEN
        val outputOut = outContent.toString()
        val expectedLog = """
            TextSizeUnit.xml PASSED
            """.trimIndent()
        assertContains(expectedLog, outputOut)
        val outputErr = errContent.toString()
        assertEquals("", outputErr)
    }

    @Test
    fun `Given ClassMaterialButton failure | When run checker | Then fails with log`() {
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
        val outputErr = errContent.toString()
        val expectedLog = """
            ./src/test/resources/files/failures/ClassMaterialButton/ClassMaterialButton.xml
            (ClassMaterialButton) Line:2: Button - Should use MaterialButton
            """.trimIndent()
        assertContains(expectedLog, outputErr)
    }

    @Test
    fun `Given ClassMaterialButton success | When run checker | Then passes, no errors`() {
        // GIVEN
        val path = "./src/test/resources/files/success/ClassMaterialButton"

        // WHEN
        main(
            arrayOf(
                path
            )
        )

        // THEN
        val outputOut = outContent.toString()
        val expectedLog = """
            ClassMaterialButton.xml PASSED
            """.trimIndent()
        assertContains(expectedLog, outputOut)
        val outputErr = errContent.toString()
        assertEquals("", outputErr)
    }

    @Test
    fun `Given IdPlus failure | When run checker | Then fails with log`() {
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
        val outputErr = errContent.toString()
        val expectedLog = """
            ./src/test/resources/files/failures/IdPlus/IdPlus.xml
            (IdPlus) Line:3: android:id="@id/something" - Doesn't start with @+id/
            """.trimIndent()
        assertContains(expectedLog, outputErr)
    }

    @Test
    fun `Given IdPlus success | When run checker | Then passes, no errors`() {
        // GIVEN
        val path = "./src/test/resources/files/success/IdPlus"

        // WHEN
        main(
            arrayOf(
                path
            )
        )

        // THEN
        val outputOut = outContent.toString()
        val expectedLog = """
            IdPlus.xml PASSED
            """.trimIndent()
        assertContains(expectedLog, outputOut)
        val outputErr = errContent.toString()
        assertEquals("", outputErr)
    }

    @Test
    fun `Given IdNaming failure | When run checker | Then fails with log`() {
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
        val outputErr = errContent.toString()
        val expectedLog1 = """
            ./src/test/resources/files/failures/IdNaming/IdNaming-Buttons.xml
            (IdNaming) Line:3: android:id="@+id/something" - Id for ImageButton doesn't conform to naming convention
            """.trimIndent()
        assertContains(expectedLog1, outputErr)
        val expectedLog2 = """
            ./src/test/resources/files/failures/IdNaming/IdNaming-GifImageView.xml
            (IdNaming) Line:3: android:id="@+id/something" - Id for GifImageView doesn't conform to naming convention
            """.trimIndent()
        assertContains(expectedLog2, outputErr)
        val expectedLog3 = """
            ./src/test/resources/files/failures/IdNaming/IdNaming-Guideline.xml
            (IdNaming) Line:3: android:id="@+id/something" - Id for Guideline doesn't conform to naming convention
            """.trimIndent()
        assertContains(expectedLog3, outputErr)
        val expectedLog4 = """
            ./src/test/resources/files/failures/IdNaming/IdNaming-HorizontalScrollView.xml
            (IdNaming) Line:3: android:id="@+id/something" - Id for HorizontalScrollView doesn't conform to naming convention
            """.trimIndent()
        assertContains(expectedLog4, outputErr)
        val expectedLog5 = """
            ./src/test/resources/files/failures/IdNaming/IdNaming-Words.xml
            (IdNaming) Line:3: android:id="@+id/something" - Id for SomeOtherClassName doesn't conform to naming convention
            """.trimIndent()
        assertContains(expectedLog5, outputErr)
    }

    @Test
    fun `Given IdNaming success | When run checker | Then passes, no errors`() {
        // GIVEN
        val path = "./src/test/resources/files/success/IdNaming"

        // WHEN
        main(
            arrayOf(
                path
            )
        )

        // THEN
        val outputOut = outContent.toString()
        val expectedLog1 = """
            IdNaming-Buttons.xml PASSED
            """.trimIndent()
        assertContains(expectedLog1, outputOut)
        val expectedLog2 = """
            IdNaming-GifImageView.xml PASSED
            """.trimIndent()
        assertContains(expectedLog2, outputOut)
        val expectedLog3 = """
            IdNaming-Guideline.xml PASSED
            """.trimIndent()
        assertContains(expectedLog3, outputOut)
        val expectedLog4 = """
            IdNaming-HorizontalScrollView.xml PASSED
            """.trimIndent()
        assertContains(expectedLog4, outputOut)
        val expectedLog5 = """
            IdNaming-Words.xml PASSED
            """.trimIndent()
        assertContains(expectedLog5, outputOut)
        val outputErr = errContent.toString()
        assertEquals("", outputErr)
    }

    @Test
    fun `Given Margin2s failure | When run checker | Then fails with log`() {
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
        val outputErr = errContent.toString()
        val expectedLog1 = """
            ./src/test/resources/files/failures/Margin2s/Margin2s-LayoutMarginBottom.xml
            (Margin2s) Line:3: android:layout_marginBottom="3dp" - Margin not divisible by 2
            """.trimIndent()
        assertContains(expectedLog1, outputErr)
        val expectedLog2 = """
            ./src/test/resources/files/failures/Margin2s/Margin2s-LayoutMarginEnd.xml
            (Margin2s) Line:3: android:layout_marginEnd="3dp" - Margin not divisible by 2
            """.trimIndent()
        assertContains(expectedLog2, outputErr)
        val expectedLog3 = """
            ./src/test/resources/files/failures/Margin2s/Margin2s-LayoutMarginStart.xml
            (Margin2s) Line:3: android:layout_marginStart="3dp" - Margin not divisible by 2
            """.trimIndent()
        assertContains(expectedLog3, outputErr)
        val expectedLog4 = """
            ./src/test/resources/files/failures/Margin2s/Margin2s-LayoutMarginTop.xml
            (Margin2s) Line:3: android:layout_marginTop="3dp" - Margin not divisible by 2
            """.trimIndent()
        assertContains(expectedLog4, outputErr)
    }

    @Test
    fun `Given Margin2s success | When run checker | Then passes, no errors`() {
        // GIVEN
        val path = "./src/test/resources/files/success/Margin2s"

        // WHEN
        main(
            arrayOf(
                path
            )
        )

        // THEN
        val outputOut = outContent.toString()
        val expectedLog1 = """
            Margin2s-LayoutMarginBottom.xml PASSED
            """.trimIndent()
        assertContains(expectedLog1, outputOut)
        val expectedLog2 = """
            Margin2s-LayoutMarginEnd.xml PASSED
            """.trimIndent()
        assertContains(expectedLog2, outputOut)
        val expectedLog3 = """
            Margin2s-LayoutMarginStart.xml PASSED
            """.trimIndent()
        assertContains(expectedLog3, outputOut)
        val expectedLog4 = """
            Margin2s-LayoutMarginTop.xml PASSED
            """.trimIndent()
        assertContains(expectedLog4, outputOut)
        val outputErr = errContent.toString()
        assertEquals("", outputErr)
    }

    @Test
    fun `Given ConstraintIdPlus failure | When run checker | Then fails with log`() {
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
        val outputErr = errContent.toString()
        val expectedLog1 = """
            ./src/test/resources/files/failures/ConstraintIdPlus/ConstraintIdPlus-LayoutConstraintBottomToBottomOf.xml
            (ConstraintIdPlus) Line:3: app:layout_constraintBottom_toBottomOf="@id/something" - Doesn't start with @+id/
            """.trimIndent()
        assertContains(expectedLog1, outputErr)
        val expectedLog2 = """
            ./src/test/resources/files/failures/ConstraintIdPlus/ConstraintIdPlus-LayoutConstraintBottomToTopOf.xml
            (ConstraintIdPlus) Line:3: app:layout_constraintBottom_toTopOf="@id/something" - Doesn't start with @+id/
            """.trimIndent()
        assertContains(expectedLog2, outputErr)
        val expectedLog3 = """
            ./src/test/resources/files/failures/ConstraintIdPlus/ConstraintIdPlus-LayoutConstraintEndToEndOf.xml
            (ConstraintIdPlus) Line:3: app:layout_constraintEnd_toEndOf="@id/something" - Doesn't start with @+id/
            """.trimIndent()
        assertContains(expectedLog3, outputErr)
        val expectedLog4 = """
            ./src/test/resources/files/failures/ConstraintIdPlus/ConstraintIdPlus-LayoutConstraintEndToStartOf.xml
            (ConstraintIdPlus) Line:3: app:layout_constraintEnd_toStartOf="@id/something" - Doesn't start with @+id/
            """.trimIndent()
        assertContains(expectedLog4, outputErr)
        val expectedLog5 = """
            ./src/test/resources/files/failures/ConstraintIdPlus/ConstraintIdPlus-LayoutConstraintStartToEndOf.xml
            (ConstraintIdPlus) Line:3: app:layout_constraintStart_toEndOf="@id/something" - Doesn't start with @+id/
            """.trimIndent()
        assertContains(expectedLog5, outputErr)
        val expectedLog6 = """
            ./src/test/resources/files/failures/ConstraintIdPlus/ConstraintIdPlus-LayoutConstraintStartToStartOf.xml
            (ConstraintIdPlus) Line:3: app:layout_constraintStart_toStartOf="@id/something" - Doesn't start with @+id/
            """.trimIndent()
        assertContains(expectedLog6, outputErr)
        val expectedLog7 = """
            ./src/test/resources/files/failures/ConstraintIdPlus/ConstraintIdPlus-LayoutConstraintTopToBottomOf.xml
            (ConstraintIdPlus) Line:3: app:layout_constraintTop_toBottomOf="@id/something" - Doesn't start with @+id/
            """.trimIndent()
        assertContains(expectedLog7, outputErr)
        val expectedLog8 = """
            ./src/test/resources/files/failures/ConstraintIdPlus/ConstraintIdPlus-LayoutConstraintTopToTopOf.xml
            (ConstraintIdPlus) Line:3: app:layout_constraintTop_toTopOf="@id/something" - Doesn't start with @+id/
            """.trimIndent()
        assertContains(expectedLog8, outputErr)
    }

    @Test
    fun `Given ConstraintIdPlus success | When run checker | Then passes, no errors`() {
        // GIVEN
        val path = "./src/test/resources/files/success/ConstraintIdPlus"

        // WHEN
        main(
            arrayOf(
                path
            )
        )

        // THEN
        val outputOut = outContent.toString()
        val expectedLog1 = """
            ConstraintIdPlus-LayoutConstraintBottomToBottomOf.xml PASSED
            """.trimIndent()
        assertContains(expectedLog1, outputOut)
        val expectedLog2 = """
            ConstraintIdPlus-LayoutConstraintBottomToTopOf.xml PASSED
            """.trimIndent()
        assertContains(expectedLog2, outputOut)
        val expectedLog3 = """
            ConstraintIdPlus-LayoutConstraintEndToEndOf.xml PASSED
            """.trimIndent()
        assertContains(expectedLog3, outputOut)
        val expectedLog4 = """
            ConstraintIdPlus-LayoutConstraintEndToStartOf.xml PASSED
            """.trimIndent()
        assertContains(expectedLog4, outputOut)
        val expectedLog5 = """
            ConstraintIdPlus-LayoutConstraintStartToEndOf.xml PASSED
            """.trimIndent()
        assertContains(expectedLog5, outputOut)
        val expectedLog6 = """
            ConstraintIdPlus-LayoutConstraintStartToStartOf.xml PASSED
            """.trimIndent()
        assertContains(expectedLog6, outputOut)
        val expectedLog7 = """
            ConstraintIdPlus-LayoutConstraintTopToBottomOf.xml PASSED
            """.trimIndent()
        assertContains(expectedLog7, outputOut)
        val expectedLog8 = """
            ConstraintIdPlus-LayoutConstraintTopToTopOf.xml PASSED
            """.trimIndent()
        assertContains(expectedLog8, outputOut)
        val outputErr = errContent.toString()
        assertEquals("", outputErr)
    }

    @Test
    fun `Given ColorRes failure | When run checker | Then fails with log`() {
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
        val outputErr = errContent.toString()
        val expectedLog1 = """
            ./src/test/resources/files/failures/ColorRes/ColorRes-TextColor.xml
            (ColorRes) Line:3: android:textColor="#123456" - Color not in resources
            """.trimIndent()
        assertContains(expectedLog1, outputErr)
        val expectedLog2 = """
            ./src/test/resources/files/failures/ColorRes/ColorRes-Tint.xml
            (ColorRes) Line:3: android:tint="#123456" - Color not in resources
            """.trimIndent()
        assertContains(expectedLog2, outputErr)
    }

    @Test
    fun `Given ColorRes success | When run checker | Then passes, no errors`() {
        // GIVEN
        val path = "./src/test/resources/files/success/ColorRes"

        // WHEN
        main(
            arrayOf(
                path
            )
        )

        // THEN
        val outputOut = outContent.toString()
        val expectedLog1 = """
            ColorRes-TextColor.xml PASSED
            """.trimIndent()
        assertContains(expectedLog1, outputOut)
        val expectedLog2 = """
            ColorRes-Tint.xml PASSED
            """.trimIndent()
        assertContains(expectedLog2, outputOut)
        val outputErr = errContent.toString()
        assertEquals("", outputErr)
    }
}