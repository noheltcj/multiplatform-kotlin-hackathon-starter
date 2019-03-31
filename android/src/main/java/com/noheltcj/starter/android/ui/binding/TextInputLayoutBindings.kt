package com.noheltcj.starter.android.ui.binding

import androidx.databinding.BindingAdapter
import com.google.android.material.textfield.TextInputLayout

@BindingAdapter("error")
fun bindErrorText(textInputLayout: TextInputLayout, errorText: String?) {
    textInputLayout.error = errorText
}