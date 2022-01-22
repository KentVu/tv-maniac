package com.thomaskioko.tvmaniac.discover.implementation.cache

import com.squareup.sqldelight.runtime.coroutines.asFlow
import com.squareup.sqldelight.runtime.coroutines.mapToList
import com.squareup.sqldelight.runtime.coroutines.mapToOne
import com.thomaskioko.tvmaniac.datasource.cache.AirEpisodesByShowId
import com.thomaskioko.tvmaniac.datasource.cache.Show
import com.thomaskioko.tvmaniac.datasource.cache.TvManiacDatabase
import com.thomaskioko.tvmaniac.discover.api.cache.TvShowCache
import kotlinx.coroutines.flow.Flow

class TvShowCacheImpl(
    private val database: TvManiacDatabase
) : TvShowCache {

    override fun insert(show: Show) {
        database.showQueries.transaction {
            database.showQueries.insertOrReplace(
                id = show.id,
                title = show.title,
                description = show.description,
                language = show.language,
                poster_image_url = show.poster_image_url,
                backdrop_image_url = show.backdrop_image_url,
                votes = show.votes,
                vote_average = show.vote_average,
                genre_ids = show.genre_ids,
                year = show.year,
                status = show.status,
                popularity = show.popularity,
                is_watchlist = show.is_watchlist
            )
        }
    }

    override fun insert(list: List<Show>) {
        list.forEach { insert(it) }
    }

    override fun getTvShow(showId: Int): Flow<Show> {
        return database.showQueries.selectByShowId(
            id = showId.toLong()
        )
            .asFlow()
            .mapToOne()
    }

    override fun getTvShows(): Flow<List<Show>> {
        return database.showQueries.selectAll()
            .asFlow()
            .mapToList()
    }

    override fun getWatchlist(): Flow<List<Show>> {
        return database.showQueries.selectWatchlist()
            .asFlow()
            .mapToList()
    }

    override fun getShowAirEpisodes(showId: Long): Flow<List<AirEpisodesByShowId>> {
        return database.lastAirEpisodeQueries.airEpisodesByShowId(
            show_id = showId
        ).asFlow()
            .mapToList()
    }

    override fun updateWatchlist(showId: Int, isInWatchlist: Boolean) {
        database.showQueries.updateWatchlist(
            is_watchlist = isInWatchlist,
            id = showId.toLong()
        )
    }

    override fun deleteTvShows() {
        database.showQueries.deleteAll()
    }
}
