package com.thomaskioko.tvmaniac.domain.showdetails

import com.thomaskioko.tvmaniac.db.RecommendedShows
import com.thomaskioko.tvmaniac.db.ShowCast
import com.thomaskioko.tvmaniac.db.ShowSeasons
import com.thomaskioko.tvmaniac.db.SimilarShows
import com.thomaskioko.tvmaniac.db.Trailers
import com.thomaskioko.tvmaniac.db.WatchProviders
import com.thomaskioko.tvmaniac.domain.showdetails.model.Casts
import com.thomaskioko.tvmaniac.domain.showdetails.model.Providers
import com.thomaskioko.tvmaniac.domain.showdetails.model.Season
import com.thomaskioko.tvmaniac.domain.showdetails.model.Show
import com.thomaskioko.tvmaniac.domain.showdetails.model.Trailer

internal fun List<ShowCast>.toCastList(): List<Casts> =
  map {
    Casts(
      id = it.id.id,
      name = it.name,
      profileUrl = it.profile_path,
      characterName = it.character_name,
    )
  }

internal  fun List<SimilarShows>.toSimilarShowList(): List<Show> =
 map {
   Show(
     tmdbId = it.id.id,
     title = it.name,
     posterImageUrl = it.poster_path,
     backdropImageUrl = it.backdrop_path,
     isInLibrary = it.in_library == 1L,
   )
  }

internal fun List<RecommendedShows>.toRecommendedShowList(): List<Show> =
  map {
    Show(
      tmdbId = it.id.id,
      title = it.name,
      posterImageUrl = it.poster_path,
      backdropImageUrl = it.backdrop_path,
      isInLibrary = it.in_library == 1L,
    )
  }

internal  fun List<WatchProviders>.toWatchProviderList(): List<Providers> =
  map {
    Providers(
      id = it.id.id,
      name = it.name ?: "",
      logoUrl = it.logo_path,
    )
  }

internal  fun List<ShowSeasons>.toSeasonsList(): List<Season> =
  map {
    Season(
      seasonId = it.season_id.id,
      tvShowId = it.show_id.id,
      name = it.season_title,
      seasonNumber = it.season_number,
    )
  }


internal  fun List<Trailers>.toTrailerList(): List<Trailer> =
  map {
    Trailer(
      showId = it.show_id.id,
      key = it.key,
      name = it.name,
      youtubeThumbnailUrl = "https://i.ytimg.com/vi/${it.key}/hqdefault.jpg",
    )
  }

internal fun String?.toGenreList(): List<String> = this?.split(", ") ?: emptyList()
