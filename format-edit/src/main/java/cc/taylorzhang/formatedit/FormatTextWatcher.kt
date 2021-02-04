package cc.taylorzhang.formatedit

import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText

/**
 * <pre>
 *     author : Taylor Zhang
 *     time   : 2021/01/04
 *     desc   : format text watcher
 *     version: 1.0.0
 * </pre>
 */
internal class FormatTextWatcher(
    private val et: EditText
) : TextWatcher {

    var formatChar: Char = ' '

    val formatCharIndexList = ArrayList<Int>()

    var maxLength = 0

    var onFormatEditListener: ((isComplete: Boolean, text: String) -> Unit)? = null

    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

    override fun afterTextChanged(s: Editable?) {
        val value = s?.toString() ?: return
        if (value.isEmpty()) return

        if (value.last() == formatChar) {
            deleteChar(s, value.lastIndex)
            return
        }

        value.forEachIndexed { index, c ->
            if (formatCharIndexList.contains(index)) {
                if (c != formatChar) {
                    insertFormatChar(s, index)
                    return
                }
            } else {
                if (c == formatChar) {
                    deleteChar(s, index)
                    return
                }
            }
        }

        updateSelectionPosition(s)
        onFormatEditListener?.invoke(
            value.length == maxLength, value.replace(formatChar.toString(), "")
        )
    }

    private fun insertFormatChar(s: Editable, index: Int) {
        val filters = s.filters
        s.filters = emptyArray()
        s.insert(index, formatChar.toString())
        s.filters = filters
        updateSelectionPosition(s)
    }

    private fun deleteChar(s: Editable, index: Int) {
        s.delete(index, index + 1)
        updateSelectionPosition(s)
    }

    private fun updateSelectionPosition(s: Editable) {
        val index = et.selectionStart - 1
        s.toString().getOrNull(index)?.let {
            if (it == formatChar) {
                et.setSelection(index)
            }
        }
    }
}