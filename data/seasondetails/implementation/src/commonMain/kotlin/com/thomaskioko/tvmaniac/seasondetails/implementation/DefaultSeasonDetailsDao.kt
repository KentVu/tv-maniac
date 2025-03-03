package com.thomaskioko.tvmaniac.seasondetails.implementation

import app.cash.sqldelight.coroutines.asFlow
import app.cash.sqldelight.coroutines.mapToList
import com.thomaskioko.tvmaniac.core.base.model.AppCoroutineDispatchers
import com.thomaskioko.tvmaniac.db.SeasonDetails
import com.thomaskioko.tvmaniac.db.Season_images
import com.thomaskioko.tvmaniac.db.TvManiacDatabase
import com.thomaskioko.tvmaniac.db.Id
import com.thomaskioko.tvmaniac.seasondetails.api.SeasonDetailsDao
import com.thomaskioko.tvmaniac.seasondetails.api.model.EpisodeDetails
import com.thomaskioko.tvmaniac.seasondetails.api.model.SeasonDetailsWithEpisodes
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import me.tatarka.inject.annotations.Inject
import software.amazon.lastmile.kotlin.inject.anvil.AppScope
import software.amazon.lastmile.kotlin.inject.anvil.ContributesBinding
import software.amazon.lastmile.kotlin.inject.anvil.SingleIn

@Inject
@SingleIn(AppScope::class)
@ContributesBinding(AppScope::class)
class DefaultSeasonDetailsDao(
  private val database: TvManiacDatabase,
  private val dispatcher: AppCoroutineDispatchers,
) : SeasonDetailsDao {

  private val seasonQueries
    get() = database.seasonQueries

  override fun fetchSeasonDetails(showId: Long, seasonNumber: Long): SeasonDetailsWithEpisodes {
    val queryResult =
      database.seasonQueries
        .seasonDetails(
          showId = Id(showId),
          seasonNumber = seasonNumber,
        )
        .executeAsList()
    return mapSeasonDetails(queryResult)
  }

  override fun observeSeasonEpisodeDetails(
    showId: Long,
    seasonNumber: Long,
  ): Flow<SeasonDetailsWithEpisodes> =
    seasonQueries.seasonDetails(showId = Id(showId), seasonNumber = seasonNumber).asFlow().map {
      mapSeasonDetails(it.executeAsList())
    }

  override fun delete(id: Long) {
    seasonQueries.delete(Id(id))
  }

  override fun deleteAll() {
    database.transaction { seasonQueries.deleteAll() }
  }

  override fun upsertSeasonImage(seasonId: Long, imageUrl: String) {
    database.transaction {
      database.season_imagesQueries.upsert(
        season_id = Id(seasonId),
        image_url = imageUrl,
      )
    }
  }

  override fun fetchSeasonImages(id: Long): List<Season_images> =
    database.season_imagesQueries.seasonImages(Id(id)).executeAsList()

  override fun observeSeasonImages(id: Long): Flow<List<Season_images>> =
    database.season_imagesQueries.seasonImages(Id(id)).asFlow().mapToList(dispatcher.io)

  private fun mapSeasonDetails(resultItem: List<SeasonDetails>): SeasonDetailsWithEpisodes {
    val seasonDetails = resultItem.first()
    val episodeList = mapEpisode(resultItem)

    return SeasonDetailsWithEpisodes(
      seasonId = seasonDetails.season_id.id,
      name = seasonDetails.season_title,
      seasonNumber = seasonDetails.season_number,
      seasonOverview = seasonDetails.overview ?: "",
      tvShowId = seasonDetails.show_id.id,
      showTitle = seasonDetails.show_title,
      imageUrl = seasonDetails.season_image_url,
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
