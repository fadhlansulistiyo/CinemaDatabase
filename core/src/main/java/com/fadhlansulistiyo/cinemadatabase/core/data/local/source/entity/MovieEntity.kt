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
    var title: String? = null,

    @ColumnInfo("poster_path")
    var posterPath: String? = null,

    @ColumnInfo("release_date")
    var releaseDate: String? = null,

    @ColumnInfo("vote_average")
    var voteAverage: Double? = null,

    @ColumnInfo("backdrop_path")
    var backdropPath: String? = null,

) : Parcelable