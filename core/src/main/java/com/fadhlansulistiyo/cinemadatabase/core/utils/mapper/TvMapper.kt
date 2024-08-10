package com.fadhlansulistiyo.cinemadatabase.core.utils.mapper

import com.fadhlansulistiyo.cinemadatabase.core.data.local.source.entity.TvEntity
import com.fadhlansulistiyo.cinemadatabase.core.data.remote.response.CastResponse
import com.fadhlansulistiyo.cinemadatabase.core.data.remote.response.DetailTvResponse
import com.fadhlansulistiyo.cinemadatabase.core.data.remote.response.TvResponse
import com.fadhlansulistiyo.cinemadatabase.core.domain.model.DetailTv
import com.fadhlansulistiyo.cinemadatabase.core.domain.model.Tv
import com.fadhlansulistiyo.cinemadatabase.core.domain.model.TvCast
import com.fadhlansulistiyo.cinemadatabase.core.utils.CONSTANTS.NA
import com.fadhlansulistiyo.cinemadatabase.core.utils.mapper.BaseMapper.mapGenresResponseToDomain
import com.fadhlansulistiyo.cinemadatabase.core.utils.mapper.BaseMapper.mapProductionCompaniesResponseToDomain
import com.fadhlansulistiyo.cinemadatabase.core.utils.mapper.BaseMapper.mapSeasonsResponseToDomain

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
                name = it.name ?: NA,
                posterPath = it.posterPath ?: "",
                firstAirDate = it.firstAirDate ?: "",
                voteAverage = it.voteAverage ?: 0.0,
            )
        }

    // Map DetailTvResponse to DetailTv
    fun mapDetailTvResponseToDomain(detailTvResponse: DetailTvResponse): DetailTv {
        val sortedSeasons = detailTvResponse.seasons
            ?.map { mapSeasonsResponseToDomain(it) }
            ?.sortedByDescending { it.seasonNumber }
            ?: emptyList()

        return DetailTv(
            id = detailTvResponse.id,
            numberOfEpisodes = detailTvResponse.numberOfEpisodes ?: 0,
            backdropPath = detailTvResponse.backdropPath ?: "",
            genres = detailTvResponse.genres?.map { mapGenresResponseToDomain(it) } ?: emptyList(),
            numberOfSeasons = detailTvResponse.numberOfSeasons ?: 0,
            firstAirDate = detailTvResponse.firstAirDate ?: "",
            overview = detailTvResponse.overview ?: NA,
            posterPath = detailTvResponse.posterPath ?: "",
            productionCompanies = detailTvResponse.productionCompanies?.map {
                mapProductionCompaniesResponseToDomain(it) } ?: emptyList(),
            voteAverage = detailTvResponse.voteAverage ?: 0.0,
            name = detailTvResponse.name ?: NA,
            seasons = sortedSeasons
        )
    }

    fun mapCastResponseToDomain(input: CastResponse): TvCast {
        return TvCast(
            id = input.id,
            castId = input.castId ?: 0,
            name = input.name ?: NA,
            character = input.character ?: NA,
            profilePath = input.profilePath ?: "",
        )
    }
}