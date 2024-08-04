package com.fadhlansulistiyo.cinemadatabase.presentation.utils

import java.text.DecimalFormat
import java.text.DecimalFormatSymbols
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

fun Double.format(digits: Int): String {
    val df = DecimalFormat()
    df.decimalFormatSymbols = DecimalFormatSymbols(Locale.US)
    df.maximumFractionDigits = digits
    return df.format(this)
}

fun String.toFormattedDateString(): String {
    val originalFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
    val targetFormat = SimpleDateFormat("dd MMM yyyy", Locale.getDefault())
    val date: Date? = originalFormat.parse(this)
    return if (date != null) targetFormat.format(date) else this
}

fun Int.toFormattedRuntime(): String {
    val hours = this / 60
    val minutes = this % 60
    return if (hours > 0) {
        String.format(Locale.getDefault(), "%dh %02dm", hours, minutes)
    } else {
        String.format(Locale.getDefault(), "%dm", minutes)
    }
}