package com.fadhlansulistiyo.cinemadatabase.presentation.utils

import com.fadhlansulistiyo.cinemadatabase.core.utils.CONSTANTS.Companion.NA
import java.text.DecimalFormat
import java.text.DecimalFormatSymbols
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

fun Double?.toVoteAverageFormat(digits: Int): String {
    return this?.let {
        val df = DecimalFormat().apply {
            decimalFormatSymbols = DecimalFormatSymbols(Locale.US)
            maximumFractionDigits = digits
        }
        "${df.format(it)}/10"
    } ?: NA
}

fun String?.toFormattedDateString(): String {
    return try {
        if (this.isNullOrEmpty()) return NA
        val originalFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        val targetFormat = SimpleDateFormat("dd MMM yyyy", Locale.getDefault())
        val date: Date? = originalFormat.parse(this)
        if (date != null) targetFormat.format(date) else this
    } catch (e: Exception) {
        NA
    }
}

fun Int?.toFormattedRuntime(): String {
    return this?.let {
        val hours = it / 60
        val minutes = it % 60
        if (hours > 0) {
            String.format(Locale.getDefault(), "%dh %02dm", hours, minutes)
        } else {
            String.format(Locale.getDefault(), "%dm", minutes)
        }
    } ?: NA
}

fun Int?.toSeasonString(): String {
    return this?.let {
        if (it == 1) "$it Season" else "$it Seasons"
    } ?: NA
}

fun Int?.toEpisodeString(): String {
    return this?.let {
        if (it == 1) "$it Episode" else "$it Episodes"
    } ?: NA
}

fun List<String?>?.toFormattedProductionCompanies(): String {
    return this?.filterNotNull()?.joinToString(" • ", prefix = "• ") ?: ""
}