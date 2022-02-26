package com.dev_yogesh.montra.utils

import android.app.Activity
import android.os.Build
import android.view.View
import android.view.WindowManager

fun changeStatusBarColor(color: Int, activity: Activity) {
    val window = activity.window
    window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
    window.statusBarColor = activity.resources.getColor(color)
}

fun changeStatusBarTextColor(activity: Activity) {
    activity.window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
}