package com.noheltcj.starter.android.ui.binding

import android.view.View
import androidx.databinding.BindingAdapter

@BindingAdapter("is_visible")
fun bindVisibility(view: View, isVisible: Boolean) {
    if (isVisible) {
        view.visibility = View.VISIBLE
    } else {
        view.visibility = View.GONE
    }
}
