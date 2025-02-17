import co.touchlab.skie.configuration.DefaultArgumentInterop
import co.touchlab.skie.configuration.EnumInterop
import co.touchlab.skie.configuration.FlowInterop
import co.touchlab.skie.configuration.SealedInterop
import co.touchlab.skie.configuration.SuppressSkieWarning
import co.touchlab.skie.configuration.SuspendInterop

plugins {
  alias(libs.plugins.tvmaniac.kmp)
  alias(libs.plugins.skie)
  alias(libs.plugins.tvmaniac.xcframework)
}

tvmaniac {

  multiplatform {
    useKspAnvil()
    useKotlinInject()

    addIosTargetsWithXcFramework(
      frameworkName = "TvManiac",
    ) { framework ->
      with(framework) {
        isStatic = !debuggable
        linkerOpts.add("-lsqlite3")
        freeCompilerArgs += if (debuggable) "-Xadd-light-debug=enable" else ""
        freeCompilerArgs += listOf("-Xbinary=bundleId=Kotlin")

        export(projects.navigation.api)
        export(projects.datastore.api)
        export(projects.presenter.discover)
        export(projects.presenter.home)
        export(projects.presenter.watchlist)
        export(projects.presenter.moreShows)
        export(projects.presenter.search)
        export(projects.presenter.seasondetails)
        export(projects.presenter.settings)
        export(projects.presenter.showDetails)
        export(projects.presenter.trailers)

        export(libs.decompose.decompose)
        export(libs.essenty.lifecycle)
      }
    }
  }

  optIn("kotlin.experimental.ExperimentalObjCName")
}

kotlin {
  sourceSets {
    commonMain {
      dependencies {
        api(projects.navigation.api)
        api(projects.presenter.discover)
        api(projects.presenter.watchlist)
        api(projects.presenter.home)
        api(projects.presenter.moreShows)
        api(projects.presenter.search)
        api(projects.presenter.seasondetails)
        api(projects.presenter.settings)
        api(projects.presenter.showDetails)
        api(projects.presenter.trailers)

        implementation(projects.core.base)
        implementation(projects.data.cast.api)
        implementation(projects.data.cast.implementation)
        implementation(projects.data.genre.api)
        implementation(projects.data.genre.implementation)
        implementation(projects.data.episodes.api)
        implementation(projects.data.episodes.implementation)
        implementation(projects.data.featuredshows.api)
        implementation(projects.data.featuredshows.implementation)
        implementation(projects.data.genre.api)
        implementation(projects.data.genre.implementation)
        implementation(projects.data.watchlist.api)
        implementation(projects.data.watchlist.implementation)
        implementation(projects.data.popularshows.api)
        implementation(projects.data.popularshows.implementation)
        implementation(projects.data.recommendedshows.api)
        implementation(projects.data.recommendedshows.implementation)
        implementation(projects.data.requestManager.api)
        implementation(projects.data.requestManager.implementation)
        implementation(projects.data.seasondetails.api)
        implementation(projects.data.seasondetails.implementation)
        implementation(projects.data.search.api)
        implementation(projects.data.search.implementation)
        implementation(projects.data.seasons.api)
        implementation(projects.data.seasons.implementation)
        implementation(projects.data.showdetails.api)
        implementation(projects.data.showdetails.implementation)
        implementation(projects.data.shows.api)
        implementation(projects.data.shows.implementation)
        implementation(projects.data.similar.api)
        implementation(projects.data.similar.implementation)
        implementation(projects.data.topratedshows.api)
        implementation(projects.data.topratedshows.implementation)
        implementation(projects.data.trailers.api)
        implementation(projects.data.trailers.implementation)
        implementation(projects.data.trendingshows.api)
        implementation(projects.data.trendingshows.implementation)
        implementation(projects.data.upcomingshows.api)
        implementation(projects.data.upcomingshows.implementation)
        implementation(projects.data.watchproviders.api)
        implementation(projects.data.watchproviders.implementation)

        implementation(projects.datastore.api)
        implementation(projects.datastore.implementation)
        implementation(projects.tmdbApi.api)
        implementation(projects.tmdbApi.implementation)
        implementation(projects.traktApi.api)
        implementation(projects.traktApi.implementation)
        implementation(projects.traktAuth.api)
        implementation(projects.traktAuth.implementation)

        implementation(projects.navigation.implementation)

      }
    }
  }
}

skie {
  analytics {
    disableUpload.set(true)
  }

  features {
    group {
      DefaultArgumentInterop.Enabled(false)
      SuspendInterop.Enabled(true)
      FlowInterop.Enabled(true)
      EnumInterop.Enabled(true)
      SealedInterop.Enabled(true)
      SuppressSkieWarning.NameCollision(true)
    }
  }
}
