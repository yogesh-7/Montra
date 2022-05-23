package com.dev_yogesh.montra.utils

import com.dev_yogesh.montra.utils.Constants.monthName
import java.util.*

fun getCurrentMonth(): String {
    val c: Calendar = Calendar.getInstance()
    /*  val monthName = arrayOf(
          "January", "February", "March", "April", "May", "June", "July",
          "August", "September", "October", "November",
          "December"
      )*/
    return monthName[c.get(Calendar.MONTH)]
}

fun getCurrentYear(): String {
    val c: Calendar = Calendar.getInstance()

    return c.get(Calendar.YEAR).toString()
}

fun getCurrentDate(): String {
    val c: Calendar = Calendar.getInstance()

    return c.get(Calendar.DATE).toString()
}

fun getSelectedMonthName(int: Int): String {
    /* val c: Calendar = Calendar.getInstance()
       val monthName = arrayOf(
           "January", "February", "March", "April", "May", "June", "July",
           "August", "September", "October", "November",
           "December"
       )*/
    return monthName[int]
}


fun getCurrentMonthInInt(currentSelectedMonth: String): String {

    var pos = when {
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
