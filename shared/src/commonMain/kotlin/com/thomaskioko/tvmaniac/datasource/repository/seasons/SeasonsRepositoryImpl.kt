package com.thomaskioko.tvmaniac.datasource.repository.seasons

import com.thomaskioko.tvmaniac.datasource.cache.seasons.SeasonsCache
import com.thomaskioko.tvmaniac.datasource.cache.shows.TvShowCache
import com.thomaskioko.tvmaniac.datasource.mapper.toSeasonCacheList
import com.thomaskioko.tvmaniac.datasource.network.api.TvShowsService
import com.thomaskioko.tvmaniac.datasource.repository.util.networkBoundResource
import com.thomaskioko.tvmaniac.presentation.model.SeasonUiModel
import kotlinx.coroutines.flow.Flow

class SeasonsRepositoryImpl(
    private val apiService: TvShowsService,
    private val tvShowCache: TvShowCache,
    private val seasonCache: SeasonsCache
) : SeasonsRepository {

    override fun getSeasonListByTvShowId(tvShowId: Int): Flow<Result<List<SeasonUiModel>>> =
        networkBoundResource(
            query = {},
            shouldFetch = {},
            fetch = {},
            saveFetchResult = {},
            onFetchFailed = {}
        )

    /*{
        return if (seasonCache.getSeasonsByTvShowId(tvShowId).isEmpty()) {

            updateTvShowsDetails(tvShowId)

            seasonCache.getSeasonsByTvShowId(tvShowId)
                .toSeasonsEntityList()
                .filter { it.seasonNumber != 0 }
                .sortedBy { it.seasonNumber }
        } else {
            seasonCache.getSeasonsByTvShowId(tvShowId)
                .toSeasonsEntityList()
                .filter { it.seasonNumber != 0 }
                .sortedBy { it.seasonNumber }
        }
    }*/

    override suspend fun updateTvShowsDetails(tvShowId: Int) {

        val apiResponse = apiService.getTvShowDetails(tvShowId)
        val seasonsEntityList = apiResponse.toSeasonCacheList()

        val seasonIds = mutableListOf<Int>()
        for (season in seasonsEntityList) {
            seasonIds.add(season.id.toInt())
        }

        tvShowCache.updateShowDetails(
            showId = tvShowId,
            showStatus = apiResponse.status,
            seasonIds = seasonIds
        )

        // Insert Seasons
        seasonCache.insert(seasonsEntityList)
    }
}
