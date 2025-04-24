package com.thomaskioko.tvmaniac.genre

import com.thomaskioko.tvmaniac.db.Tvshow
import kotlinx.coroutines.flow.Flow
import me.tatarka.inject.annotations.Inject
import org.mobilenativefoundation.store.store5.impl.extensions.fresh
import org.mobilenativefoundation.store.store5.impl.extensions.get
import software.amazon.lastmile.kotlin.inject.anvil.AppScope
import software.amazon.lastmile.kotlin.inject.anvil.ContributesBinding
import software.amazon.lastmile.kotlin.inject.anvil.SingleIn

@Inject
@SingleIn(AppScope::class)
@ContributesBinding(AppScope::class)
class DefaultGenreRepository(
  private val store: GenreStore,
  private val showsByGenreIdStore: ShowsByGenreIdStore,
  private val genreDao: GenreDao,
) : GenreRepository {

  override suspend fun fetchGenresWithShows(forceRefresh: Boolean) {
    when {
      forceRefresh -> store.fresh(Unit)
      else -> store.get(Unit)
    }
  }

  override suspend fun fetchShowByGenreId(id: String, forceRefresh: Boolean) {
    when {
      forceRefresh -> showsByGenreIdStore.fresh(id)
      else -> showsByGenreIdStore.get(id)
    }
  }

  override fun observeGenresWithShows(): Flow<List<ShowGenresEntity>> = genreDao.observeGenres()

  override suspend fun observeShowByGenreId(id: String): Flow<List<Tvshow>> = genreDao.observeShowsByGenreId(id)

}
