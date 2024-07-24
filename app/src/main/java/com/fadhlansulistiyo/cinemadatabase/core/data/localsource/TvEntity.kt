package com.fadhlansulistiyo.cinemadatabase.core.data.localsource

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Entity(tableName = "tv")
@Parcelize
data class TvEntity(

    @ColumnInfo("id")
    @PrimaryKey(autoGenerate = false)
    var id: Int,

    @ColumnInfo("name")
    var name: String,

    @ColumnInfo("poster_path")
    var posterPath: String,

    @ColumnInfo("first_air_date")
    var firstAirDate: String,

    @ColumnInfo("vote_average")
    var voteAverage: Double,

    @ColumnInfo(name = "isBookmarked")
    var isBookmarked: Boolean = false

) : Parcelable