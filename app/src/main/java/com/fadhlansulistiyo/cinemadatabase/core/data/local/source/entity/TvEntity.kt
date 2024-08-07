package com.fadhlansulistiyo.cinemadatabase.core.data.local.source.entity

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
    var name: String? = null,

    @ColumnInfo("poster_path")
    var posterPath: String? = null,

    @ColumnInfo("first_air_date")
    var firstAirDate: String? = null,

    @ColumnInfo("vote_average")
    var voteAverage: Double? = null,

) : Parcelable