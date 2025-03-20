package com.thomaskioko.tvmaniac.similar.implementation

import app.cash.sqldelight.coroutines.asFlow
import app.cash.sqldelight.coroutines.mapToList
import com.thomaskioko.tvmaniac.core.base.model.AppCoroutineDispatchers
import com.thomaskioko.tvmaniac.db.SimilarShows
import com.thomaskioko.tvmaniac.db.TvManiacDatabase
import com.thomaskioko.tvmaniac.db.Id
import com.thomaskioko.tvmaniac.similar.api.SimilarShowsDao
import kotlinx.coroutines.flow.Flow
import me.tatarka.inject.annotations.Inject
import software.amazon.lastmile.kotlin.inject.anvil.AppScope
import software.amazon.lastmile.kotlin.inject.anvil.ContributesBinding
import software.amazon.lastmile.kotlin.inject.anvil.SingleIn

@Inject
@SingleIn(AppScope::class)
@ContributesBinding(AppScope::class)
class DefaultSimilarShowsDao(
  private val database: TvManiacDatabase,
  private val dispatchers: AppCoroutineDispatchers,
) : SimilarShowsDao {

  override fun upsert(showId: Long, similarShowId: Long) {
    database.similarShowsQueries.transaction {
      database.similarShowsQueries.insertOrReplace(
        id = Id(similarShowId),
        similar_show_id = Id(showId),
      )
    }
  }

  override fun observeSimilarShows(traktId: Long): Flow<List<SimilarShows>> {
    return database.similarShowsQueries
      .similarShows(Id(traktId))
      .asFlow()
      .mapToList(dispatchers.io)
  }

  override fun delete(id: Long) {
    database.similarShowsQueries.delete(Id(id))
  }

  override fun deleteAll() {
    database.transaction { database.similarShowsQueries.deleteAll() }
  }
}
