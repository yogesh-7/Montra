package com.dev_yogesh.montra.utils

import com.dev_yogesh.montra.utils.Constants.monthName
import java.text.SimpleDateFormat
import java.util.*


fun getCurrentMonth(): String {
    return monthName[(Calendar.getInstance()).get(Calendar.MONTH)]
}

fun getCurrentYear(): String {
    return (Calendar.getInstance()).get(Calendar.YEAR).toString()
}

fun getCurrentDate(): String {
    return (SimpleDateFormat("dd-MM-yyyy", Locale.getDefault())).format(Calendar.getInstance().time)
}



fun getSelectedMonthName(int: Int): String {
    return monthName[int]
}




fun getCurrentMonthInInt(currentSelectedMonth: String): String {

    val pos = when {
        currentSelectedMonth.contentEquals("January") -> "01"
        currentSelectedMonth.contentEquals("February") -> "02"
        currentSelectedMonth.contentEquals("March") -> "03"
        currentSelectedMonth.contentEquals("April") -> "04"
        currentSelectedMonth.contentEquals("May") -> "05"
        currentSelectedMonth.contentEquals("June") -> "06"
        currentSelectedMonth.contentEquals("July") -> "07"
        currentSelectedMonth.contentEquals("August") -> "08"
        currentSelectedMonth.contentEquals("September") -> "09"
        currentSelectedMonth.contentEquals("October") -> "10"
        currentSelectedMonth.contentEquals("November") -> "11"
        currentSelectedMonth.contentEquals("December") -> "12"


        else -> {
            "00"
        }
    }
    return pos
}
