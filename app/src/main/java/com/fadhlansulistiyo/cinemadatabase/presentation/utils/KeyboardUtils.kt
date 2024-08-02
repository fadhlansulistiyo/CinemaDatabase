package com.fadhlansulistiyo.cinemadatabase.presentation.utils

import android.app.Activity
import android.graphics.Rect
import android.view.View
import android.view.ViewTreeObserver

fun Activity.isKeyboardVisible(): Boolean {
    val rootView: View = findViewById(android.R.id.content)
    val rect = Rect()
    rootView.getWindowVisibleDisplayFrame(rect)
    val screenHeight = rootView.height
    val keypadHeight = screenHeight - rect.bottom
    return keypadHeight > screenHeight * 0.15 // 15% of the screen height
}

fun Activity.setKeyboardVisibilityListener(onKeyboardVisibilityChanged: (Boolean) -> Unit) {
    val rootView: View = findViewById(android.R.id.content)
    rootView.viewTreeObserver.addOnGlobalLayoutListener(object : ViewTreeObserver.OnGlobalLayoutListener {
        private var wasKeyboardVisible = isKeyboardVisible()

        override fun onGlobalLayout() {
            val isKeyboardVisibleNow = isKeyboardVisible()
            if (isKeyboardVisibleNow != wasKeyboardVisible) {
                wasKeyboardVisible = isKeyboardVisibleNow
                onKeyboardVisibilityChanged(isKeyboardVisibleNow)
            }
        }
    })
}
