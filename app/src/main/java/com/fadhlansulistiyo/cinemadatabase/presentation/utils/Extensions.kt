package com.fadhlansulistiyo.cinemadatabase.presentation.utils

import android.content.Context
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.fadhlansulistiyo.cinemadatabase.R
import com.fadhlansulistiyo.cinemadatabase.core.utils.CONSTANTS.IMAGE_URL
import com.fadhlansulistiyo.cinemadatabase.core.utils.CONSTANTS.IMAGE_URL_ORIGINAL
import com.fadhlansulistiyo.cinemadatabase.core.utils.CONSTANTS.NA
import java.text.DecimalFormat
import java.text.DecimalFormatSymbols
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

fun Double?.toVoteAverageFormat(digits: Int): String {
    return if (this == null || this == 0.0) {
        NA
    } else {
        val df = DecimalFormat().apply {
            decimalFormatSymbols = DecimalFormatSymbols(Locale.US)
            maximumFractionDigits = digits
        }
        "${df.format(this)}/10"
    }
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
    return when {
        this == null || this == 0 -> NA
        else -> {
            val hours = this / 60
            val minutes = this % 60
            if (hours > 0) {
                String.format(Locale.getDefault(), "%dh %02dm", hours, minutes)
            } else {
                String.format(Locale.getDefault(), "%dm", minutes)
            }
        }
    }
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

fun ImageView.loadImageOriginal(context: Context, url: String) {
    Glide.with(context)
        .load(IMAGE_URL_ORIGINAL + url)
        .apply(
            RequestOptions.placeholderOf(R.drawable.ic_movie_grey_24dp)
                .error(R.drawable.ic_error)
        )
        .into(this)
}

fun ImageView.loadImage(context: Context, url: String) {
    Glide.with(context)
        .load(IMAGE_URL + url)
        .apply(
            RequestOptions.placeholderOf(R.drawable.ic_movie_grey_24dp)
                .error(R.drawable.ic_error)
        )
        .into(this)
}