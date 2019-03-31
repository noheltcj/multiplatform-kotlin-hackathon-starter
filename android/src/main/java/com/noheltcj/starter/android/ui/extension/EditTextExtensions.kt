package com.noheltcj.starter.android.ui.extension

import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText

fun EditText.onTextChanged(listener: (String) -> Unit) {
    addTextChangedListener(OnTextChangedWatcher(listener))
}

class OnTextChangedWatcher(private val listener: (String) -> Unit) : TextWatcher {
    override fun afterTextChanged(s: Editable?) {}
    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
        listener(s.toString())
    }
}
