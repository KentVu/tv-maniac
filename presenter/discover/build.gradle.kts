plugins {
  alias(libs.plugins.tvmaniac.kmp)
}

tvmaniac {
  multiplatform {
    useKotlinInject()
  }
}

kotlin {
  sourceSets {
    commonMain {
      dependencies {
        api(projects.core.view)
        implementation(projects.core.base)
        implementation(projects.core.logger.api)
        implementation(projects.domain.discover)
        implementation(projects.data.watchlist.api)

        api(libs.decompose.decompose)
        api(libs.essenty.lifecycle)
        api(libs.kotlinx.collections)

        implementation(libs.coroutines.core)

      }
    }

    commonTest {
      dependencies {
        implementation(projects.core.logger.fixture)
        implementation(projects.data.featuredshows.testing)
        implementation(projects.data.genre.testing)
        implementation(projects.data.popularshows.testing)
        implementation(projects.data.topratedshows.testing)
        implementation(projects.data.trendingshows.testing)
        implementation(projects.data.upcomingshows.testing)
        implementation(projects.data.watchlist.testing)

        implementation(libs.bundles.unittest)
      }
    }
  }
}
