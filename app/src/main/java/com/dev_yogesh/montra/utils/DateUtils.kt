package com.dev_yogesh.montra.utils

import java.util.*

fun getCurrentMonth(): String {
    val c: Calendar = Calendar.getInstance()
    val monthName = arrayOf(
        "January", "February", "March", "April", "May", "June", "July",
        "August", "September", "October", "November",
        "December"
    )
    return monthName[c.get(Calendar.MONTH)]
}