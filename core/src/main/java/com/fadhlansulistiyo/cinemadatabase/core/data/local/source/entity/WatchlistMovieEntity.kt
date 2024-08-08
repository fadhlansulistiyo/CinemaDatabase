package com.fadhlansulistiyo.cinemadatabase.core.data.local.source.entity

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Entity(tableName = "watchlist_movie")
@Parcelize
data class WatchlistMovieEntity(

    @ColumnInfo(name = "id")
    @PrimaryKey(autoGenerate = false)
    val id: Int,

    @ColumnInfo("title")
    val title: String,

    @ColumnInfo("poster_path")
    val posterPath: String,

    @ColumnInfo("release_date")
    val releaseDate: String,

    @ColumnInfo("vote_average")
    val voteAverage: Double

) : Parcelable