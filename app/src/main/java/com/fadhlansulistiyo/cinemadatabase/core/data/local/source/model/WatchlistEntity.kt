package com.fadhlansulistiyo.cinemadatabase.core.data.local.source.model

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Entity(tableName = "watchlist")
@Parcelize
data class WatchlistEntity(

    @ColumnInfo(name = "id")
    @PrimaryKey(autoGenerate = false)
    val id: Int,

    val title: String,
    val posterPath: String,
    val releaseDate: String,
    val voteAverage: Double
) : Parcelable