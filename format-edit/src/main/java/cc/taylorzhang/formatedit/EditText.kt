package cc.taylorzhang.formatedit

import android.text.InputFilter
import android.widget.EditText

/**
 * <pre>
 *     author : Taylor Zhang
 *     time   : 2021/01/04
 *     desc   : EditText Extension
 *     version: 1.0.0
 * </pre>
 */

/**
 * Set format rules
 *
 * @param array format rules array, if you want to format as 'xxx xxxx xxxx', pass 3, 4, 4.
 * @param formatChar format char, default is space.
 */
fun EditText.setFormatRules(vararg array: Int, formatChar: Char = ' ') {
    require(array.size > 1) { "Format array size must greater than 1." }

    var count = 0
    val list = ArrayList<Int>(array.size - 1)
    array.forEachIndexed { index, item ->
        count += item
        if (index != array.lastIndex) {
            list.add(count + index)
        }
    }
    val maxLength = count + array.size - 1

    val oldText = textWithFormatRemoved
    setFormatTextWatcher(this, formatChar, list, maxLength)
    setMaxLength(this, maxLength)
    setText(oldText)
    setSelection(text.length)
}

/***
 * Get the text with format removed.
 */
val EditText.textWithFormatRemoved: String
    get() {
        val watcher = getTag(R.id.format_edit_tag_format_text_watcher) as? FormatTextWatcher
        return if (watcher == null) {
            text.toString()
        } else {
            text.toString().replace(watcher.formatChar.toString(), "")
        }
    }

/**
 * Set format edit listener.
 *
 * @param listener isComplete: is edit complete or not, text: text with format removed.
 */
fun EditText.setOnFormatEditListener(listener: (isComplete: Boolean, text: String) -> Unit) {
    val watcher = getTag(R.id.format_edit_tag_format_text_watcher) as? FormatTextWatcher
    require(watcher != null) { "You should call setFormatRules first." }
    watcher.onFormatEditListener = listener
}

private fun setFormatTextWatcher(
    et: EditText,
    formatChar: Char,
    list: ArrayList<Int>,
    maxLength: Int
) {
    var watcher = et.getTag(R.id.format_edit_tag_format_text_watcher) as? FormatTextWatcher
    if (watcher == null) {
        watcher = FormatTextWatcher(et)
        et.setTag(R.id.format_edit_tag_format_text_watcher, watcher)
        et.addTextChangedListener(watcher)
    }

    watcher.formatChar = formatChar
    watcher.formatCharIndexList.clear()
    watcher.formatCharIndexList.addAll(list)
    watcher.maxLength = maxLength
}

private fun setMaxLength(et: EditText, maxLength: Int) {
    val filterList = et.filters.filter { it !is InputFilter.LengthFilter }
    et.filters = Array(filterList.size + 1) {
        if (it < filterList.size) filterList[it] else InputFilter.LengthFilter(maxLength)
    }
}