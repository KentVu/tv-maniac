package com.thomaskioko.tvmaniac.search.implementation

import com.thomaskioko.tvmaniac.core.base.model.AppCoroutineDispatchers
import com.thomaskioko.tvmaniac.core.store.mapToEither
import com.thomaskioko.tvmaniac.core.networkutil.model.Either
import com.thomaskioko.tvmaniac.core.networkutil.model.Failure
import com.thomaskioko.tvmaniac.search.api.SearchRepository
import com.thomaskioko.tvmaniac.shows.api.model.ShowEntity
import com.thomaskioko.tvmaniac.shows.api.TvShowsDao
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOn
import me.tatarka.inject.annotations.Inject
import org.mobilenativefoundation.store.store5.StoreReadRequest
import software.amazon.lastmile.kotlin.inject.anvil.AppScope
import software.amazon.lastmile.kotlin.inject.anvil.ContributesBinding
import software.amazon.lastmile.kotlin.inject.anvil.SingleIn

private const val MIN_SHOW_COUNT = 10

@Inject
@SingleIn(AppScope::class)
@ContributesBinding(AppScope::class)
class DefaultSearchRepository(
  private val tvShowsDao: TvShowsDao,
  private val store: SearchShowStore,
  private val dispatchers: AppCoroutineDispatchers,
) : SearchRepository {
  override suspend fun search(query: String): Flow<Either<Failure, List<ShowEntity>>> =
    store.stream(
      StoreReadRequest.cached(
        key = query,
        refresh = hasNoLocalData(query),
      ),
    )
      .mapToEither()
      .flowOn(dispatchers.io)

  private suspend fun hasNoLocalData(query: String): Boolean {
    return tvShowsDao.observeQueryCount(query)
      .first()
      .let { cachedShows ->
        cachedShows < MIN_SHOW_COUNT
      }
  }

}
