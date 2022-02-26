package com.dev_yogesh.montra.ui.comon

import android.app.Activity
import android.graphics.Color
import android.os.Build
import android.util.Log
import android.view.View
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat

/**
 * Created by Cypress on 30,August,2021
 */


fun Activity.makeStatusBarTransparent() {
    window.apply {
        clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
        statusBarColor = Color.TRANSPARENT
    }
}

fun Activity.hideKeyboardFromActivity() {
    Log.d("Extension", "hideKeyboardActivity")
    val inputMethodManager =
        getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
    inputMethodManager.hideSoftInputFromWindow(
        window.decorView.rootView.windowToken, 0
    )
}



fun Activity.changeStatusBarColor(colorId: Int) {
    window.statusBarColor =
        ContextCompat.getColor(this, colorId)
}

fun Activity.longToast(resId: Int) {
    Toast.makeText(
        this,
        resId,
        Toast.LENGTH_LONG
    ).show()
}

fun Activity.longToast(msg: String) {
    Toast.makeText(
        this,
        msg,
        Toast.LENGTH_LONG
    ).show()
}