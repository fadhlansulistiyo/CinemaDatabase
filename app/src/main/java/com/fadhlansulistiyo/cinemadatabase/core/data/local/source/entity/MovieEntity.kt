package com.fadhlansulistiyo.cinemadatabase.core.data.local.source.entity

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Entity(tableName = "movie")
@Parcelize
data class MovieEntity(

    @ColumnInfo("id")
    @PrimaryKey(autoGenerate = false)
    var id: Int,

    @ColumnInfo("title")
    var title: String,

    @ColumnInfo("poster_path")
    var posterPath: String,

    @ColumnInfo("release_date")
    var releaseDate: String,

    @ColumnInfo("vote_average")
    var voteAverage: Double,

    @ColumnInfo("backdrop_path")
    var backdropPath: String,

) : Parcelable