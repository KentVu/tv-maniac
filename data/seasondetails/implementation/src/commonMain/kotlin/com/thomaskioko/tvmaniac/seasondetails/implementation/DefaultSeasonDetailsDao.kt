package com.thomaskioko.tvmaniac.seasondetails.implementation

import app.cash.sqldelight.coroutines.asFlow
import com.thomaskioko.tvmaniac.core.db.Season
import com.thomaskioko.tvmaniac.core.db.SeasonDetails
import com.thomaskioko.tvmaniac.core.db.TvManiacDatabase
import com.thomaskioko.tvmaniac.db.Id
import com.thomaskioko.tvmaniac.seasondetails.api.SeasonDetailsDao
import com.thomaskioko.tvmaniac.seasondetails.api.model.EpisodeDetails
import com.thomaskioko.tvmaniac.seasondetails.api.model.SeasonDetailsWithEpisodes
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import me.tatarka.inject.annotations.Inject

@Inject
class DefaultSeasonDetailsDao(
    private val database: TvManiacDatabase,
) : SeasonDetailsDao {

    private val seasonQueries get() = database.seasonQueries

    override fun upsert(season: Season) {
        database.transaction {
            seasonQueries.upsert(
                id = season.id,
                show_id = season.show_id,
                season_number = season.season_number,
                episode_count = season.episode_count,
                title = season.title,
                overview = season.overview,
                image_url = season.image_url,
            )
        }
    }

    override fun fetchSeasonDetails(
        showId: Long,
        seasonNumber: Long,
    ): SeasonDetailsWithEpisodes {
        val queryResult = database.seasonQueries.seasonDetails(
            showId = Id(showId),
            seasonNumber = seasonNumber,
        ).executeAsList()
        return mapSeasonDetails(queryResult)
    }

    override fun observeSeasonEpisodeDetails(
        showId: Long,
        seasonNumber: Long,
    ): Flow<SeasonDetailsWithEpisodes> =
        seasonQueries.seasonDetails(showId = Id(showId), seasonNumber = seasonNumber)
            .asFlow()
            .map { mapSeasonDetails(it.executeAsList()) }

    override fun delete(id: Long) {
        seasonQueries.delete(Id(id))
    }

    override fun deleteAll() {
        database.transaction {
            seasonQueries.deleteAll()
        }
    }

    private fun mapSeasonDetails(resultItem: List<SeasonDetails>): SeasonDetailsWithEpisodes {
        val seasonDetails = resultItem.first()
        val episodeList = mapEpisode(resultItem)

        return SeasonDetailsWithEpisodes(
            seasonId = seasonDetails.season_id.id,
            name = seasonDetails.season_title,
            seasonNumber = seasonDetails.season_number,
            tvShowId = seasonDetails.show_id.id,
            showTitle = seasonDetails.show_title,
            episodes = episodeList,
            episodeCount = episodeList.size.toLong(),
        )
    }

    private fun mapEpisode(resultItem: List<SeasonDetails>): List<EpisodeDetails> {
        return resultItem.mapNotNull { seasonDetails ->
            seasonDetails.episode_id?.let { episodeId ->
                EpisodeDetails(
                    id = episodeId.id,
                    seasonId = seasonDetails.season_id.id,
                    name = seasonDetails.episode_title ?: "",
                    seasonNumber = seasonDetails.season_number,
                    episodeNumber = seasonDetails.episode_number ?: 0,
                    overview = seasonDetails.overview ?: "",
                    voteAverage = seasonDetails.vote_average ?: 0.0,
                    voteCount = seasonDetails.vote_count ?: 0,
                    stillPath = seasonDetails.episode_image_url,
                    runtime = seasonDetails.runtime ?: 0,
                    isWatched = false,
                )
            }
        }
    }
}
