package com.dev_yogesh.montra.utils.comon

import android.graphics.drawable.Drawable

interface DialogMonthCallback {
    fun selectedMonth(month: String,monthInt: Int)
}

interface DialogTransactionTypeCallback {
    fun selectedType(type: String,typeInt: Int,drawable: Drawable)
}