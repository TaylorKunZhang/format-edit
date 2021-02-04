package cc.taylorzhang.formatedit

import android.widget.EditText
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

/**
 * <pre>
 *     author : Taylor Zhang
 *     time   : 2021/02/03
 *     desc   : format text unit test
 *     version: 1.0.0
 * </pre>
 */
@RunWith(AndroidJUnit4::class)
class FormatEditTest {

    private lateinit var et: EditText

    @Before
    fun setUp() {
        et = EditText(InstrumentationRegistry.getInstrumentation().targetContext)
        et.setFormatRules(3, 4)
    }

    @Test
    fun testInputEmpty() {
        et.setText("")
        assertEquals("", et.text.toString())
    }

    @Test
    fun testNotNeedFormat() {
        et.setText("1")
        assertEquals("1", et.text.toString())

        et.setText("13")
        assertEquals("13", et.text.toString())

        et.setText("133")
        assertEquals("133", et.text.toString())
    }

    @Test
    fun testNeedFormat() {
        et.setText("1331")
        assertEquals("133 1", et.text.toString())

        et.setText("13312")
        assertEquals("133 12", et.text.toString())

        et.setText("133123")
        assertEquals("133 123", et.text.toString())

        et.setText("1331234")
        assertEquals("133 1234", et.text.toString())
    }

    @Test
    fun testDeleteFirstChar() {
        et.setText("1231234")
        et.text?.delete(0, 1)
        assertEquals("231 234", et.text.toString())
    }

    @Test
    fun testDeleteMiddleChar() {
        et.setText("1231234")
        et.text?.delete(2, 3)
        assertEquals("121 234", et.text.toString())
    }

    @Test
    fun testDeleteLastChar() {
        et.setText("1231")
        et.text?.delete(4, 5)
        assertEquals("123", et.text.toString())
    }

    @Test
    fun testGetTextWithFormatRemoved() {
        et.setText("1231234")
        assertEquals("1231234", et.textWithFormatRemoved)
    }

    @Test
    fun testFormatEditListener() {
        var isCompleteValue = false
        var textValue = ""
        et.setOnFormatEditListener { isComplete, text ->
            isCompleteValue = isComplete
            textValue = text
        }

        et.text?.append("133")
        assertEquals(false, isCompleteValue)
        assertEquals("133", textValue)

        et.text?.append("1234")
        assertEquals(true, isCompleteValue)
        assertEquals("1331234", textValue)
    }
}