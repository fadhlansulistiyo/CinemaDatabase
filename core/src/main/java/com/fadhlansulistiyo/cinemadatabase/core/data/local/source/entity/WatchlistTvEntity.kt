package com.fadhlansulistiyo.cinemadatabase.core.data.local.source.entity

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Entity(tableName = "watchlist_tv")
@Parcelize
data class WatchlistTvEntity(

    @ColumnInfo(name = "id")
    @PrimaryKey(autoGenerate = false)
    val id: Int,

    @ColumnInfo("name")
    val name: String,

    @ColumnInfo("poster_path")
    val posterPath: String,

    @ColumnInfo("first_air_date")
    val firstAirDate: String,

    @ColumnInfo("vote_average")
    val voteAverage: Double

) : Parcelable