plugins {
  alias(libs.plugins.tvmaniac.kmp)
}

tvmaniac {
  multiplatform {
    useKotlinInject()
    useKspAnvilCompiler()
  }
}

kotlin {
  sourceSets {
    commonMain {
      dependencies {
        implementation(projects.core.base)
        implementation(projects.database)
        implementation(projects.datastore.api)
        implementation(projects.tmdbApi.api)
        implementation(projects.core.util)
        implementation(projects.data.cast.api)
        implementation(projects.data.episodes.api)
        implementation(projects.data.requestManager.api)
        implementation(projects.data.seasondetails.api)
        implementation(projects.data.seasons.api)

        implementation(libs.kotlinx.atomicfu)
        implementation(libs.sqldelight.extensions)
        implementation(libs.store5)
      }
    }
  }
}
