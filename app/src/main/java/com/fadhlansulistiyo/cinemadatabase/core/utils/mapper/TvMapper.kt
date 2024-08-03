package com.fadhlansulistiyo.cinemadatabase.core.utils.mapper

import com.fadhlansulistiyo.cinemadatabase.core.data.local.source.entity.TvEntity
import com.fadhlansulistiyo.cinemadatabase.core.data.remote.response.DetailTvResponse
import com.fadhlansulistiyo.cinemadatabase.core.data.remote.response.TvResponse
import com.fadhlansulistiyo.cinemadatabase.core.domain.model.DetailTv
import com.fadhlansulistiyo.cinemadatabase.core.domain.model.Tv
import com.fadhlansulistiyo.cinemadatabase.core.utils.CONSTANTS.Companion.DATA_NOT_YET_AVAILABLE
import com.fadhlansulistiyo.cinemadatabase.core.utils.mapper.BaseMapper.mapGenreItemToDomain
import com.fadhlansulistiyo.cinemadatabase.core.utils.mapper.BaseMapper.mapProductionCompanyResponseToDomain

object TvMapper {

    // Map TvResponse to TvEntity
    fun mapTvResponsesToEntities(input: List<TvResponse>): List<TvEntity> {
        val tvList = ArrayList<TvEntity>()
        input.map {
            val tv = TvEntity(
                id = it.id,
                name = it.name,
                posterPath = it.posterPath,
                firstAirDate = it.firstAirDate,
                voteAverage = it.voteAverage,
            )
            tvList.add(tv)
        }
        return tvList
    }

    // Map TvEntity to Tv
    fun mapTvEntitiesToDomain(input: List<TvEntity>): List<Tv> =
        input.map {
            Tv(
                id = it.id,
                name = it.name,
                posterPath = it.posterPath,
                firstAirDate = it.firstAirDate,
                voteAverage = it.voteAverage,
            )
        }

    // Map DetailTvResponse to DetailTv
    fun mapDetailTvResponseToDomain(input: DetailTvResponse): DetailTv {
        return DetailTv(
            id = input.id,
            originalLanguage = input.originalLanguage ?: DATA_NOT_YET_AVAILABLE,
            numberOfEpisodes = input.numberOfEpisodes ?: 0,
            backdropPath = input.backdropPath ?: "",
            genres = input.genres?.map { mapGenreItemToDomain(it) } ?: emptyList(),
            popularity = input.popularity ?: 0.0,
            numberOfSeasons = input.numberOfSeasons ?: 0,
            voteCount = input.voteCount ?: 0,
            firstAirDate = input.firstAirDate ?: DATA_NOT_YET_AVAILABLE,
            overview = input.overview ?: DATA_NOT_YET_AVAILABLE,
            posterPath = input.posterPath ?: "",
            productionCompanies = input.productionCompanies?.map { mapProductionCompanyResponseToDomain(it) } ?: emptyList(),
            originalName = input.originalName ?: DATA_NOT_YET_AVAILABLE,
            voteAverage = input.voteAverage ?: 0.0,
            name = input.name ?: DATA_NOT_YET_AVAILABLE,
            episodeRunTime = input.episodeRunTime ?: emptyList(),
            lastAirDate = input.lastAirDate ?: DATA_NOT_YET_AVAILABLE,
            status = input.status ?: DATA_NOT_YET_AVAILABLE
        )
    }
}