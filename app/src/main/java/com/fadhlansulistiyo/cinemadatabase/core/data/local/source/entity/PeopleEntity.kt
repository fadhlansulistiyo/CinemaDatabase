package com.fadhlansulistiyo.cinemadatabase.core.data.local.source.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "people")
data class PeopleEntity(

    @PrimaryKey(autoGenerate = false)
    @ColumnInfo("id")
    var id: Int,

    @ColumnInfo("name")
    var name: String? = null,

    @ColumnInfo("profile_path")
    var profilePath: String? = null
)