package com.noheltcj.starter.android.ui.extension

import com.google.android.material.snackbar.BaseTransientBottomBar
import com.google.android.material.snackbar.Snackbar

fun Snackbar.addDismissListener(listener: () -> Unit) {
    addCallback(object: BaseTransientBottomBar.BaseCallback<Snackbar>() {
        override fun onDismissed(transientBottomBar: Snackbar?, event: Int) {
            listener()
            super.onDismissed(transientBottomBar, event)
        }
    })
}
