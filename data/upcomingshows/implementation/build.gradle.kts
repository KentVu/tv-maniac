plugins {
  alias(libs.plugins.tvmaniac.kmp)
}

tvmaniac {
  multiplatform {
    useKotlinInject()
    useKspAnvilCompiler()
  }

  optIn("androidx.paging.ExperimentalPagingApi")
}

kotlin {
  sourceSets {
    commonMain {
      dependencies {
        implementation(projects.core.base)
        implementation(projects.core.logger.api)
        implementation(projects.core.paging)
        implementation(projects.database)
        implementation(projects.tmdbApi.api)
        implementation(projects.core.util)
        implementation(projects.data.upcomingshows.api)
        implementation(projects.data.requestManager.api)

        api(libs.coroutines.core)

        implementation(libs.kotlinx.atomicfu)
        implementation(libs.sqldelight.extensions)
        implementation(libs.store5)
      }
    }
  }
}
