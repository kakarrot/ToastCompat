package com.et.base.toast

import android.widget.Toast

/**
 * @author drakeet
 */
interface BadTokenListener {
    fun onBadTokenCaught(toast: Toast)
}