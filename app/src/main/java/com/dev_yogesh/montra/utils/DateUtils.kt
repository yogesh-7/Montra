package com.dev_yogesh.montra.utils

import android.annotation.SuppressLint
import android.util.Log
import com.dev_yogesh.montra.utils.Constants.DATE_FORMAT
import com.dev_yogesh.montra.utils.Constants.monthName
import java.text.DateFormat
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.GregorianCalendar
import java.util.Locale


fun getCurrentMonth(): String {
    return monthName[(Calendar.getInstance()).get(Calendar.MONTH)]
}

fun getCurrentYear(): String {
    return (Calendar.getInstance()).get(Calendar.YEAR).toString()
}

fun getCurrentDate(): String {
    return (SimpleDateFormat(DATE_FORMAT, Locale.getDefault())).format(Calendar.getInstance().time)
}


fun getSelectedMonthName(int: Int): String {
    return monthName[int]
}

fun getCurrentWeekStartEndDate(startDateReturn :Boolean):String {
    val c = GregorianCalendar.getInstance()
    c[Calendar.DAY_OF_WEEK] = Calendar.SUNDAY

    val df: DateFormat = SimpleDateFormat(DATE_FORMAT, Locale.getDefault())

    c.add(Calendar.DATE, -3)
    val startDate = df.format(c.time)
    c.add(Calendar.DATE, +6)
    val endDate = df.format(c.time)
    Log.i("date", "startDate:::: $startDate")
    Log.i("date", "endDate:::: $endDate")

    return if(startDateReturn){
        startDate
    }else{
        endDate
    }
}

@SuppressLint("SimpleDateFormat")
fun isThisIsInCurrentWeek(transactionDate :String):Boolean {
    val c = GregorianCalendar.getInstance()
    c[Calendar.DAY_OF_WEEK] = Calendar.SUNDAY

    val df: DateFormat = SimpleDateFormat(DATE_FORMAT, Locale.getDefault())

    c.add(Calendar.DATE, -3)
    val startDate = df.format(c.time)
    c.add(Calendar.DATE, +6)
    val endDate = df.format(c.time)
    Log.i("date", "startDate:::: $startDate")
    Log.i("date", "endDate:::: $endDate")
    Log.i("date", "transactionDate:::: $transactionDate")
    val format = SimpleDateFormat(DATE_FORMAT)
    val transactionDateObj: Date = format.parse(transactionDate)!!
    val startDateObj: Date = format.parse(startDate)!!
    val endDateObj: Date = format.parse(endDate)!!
    Log.i("date", "value:::: ${startDateObj<transactionDateObj && transactionDateObj <endDateObj}")
    return startDateObj<transactionDateObj && transactionDateObj <endDateObj
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
