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
        implementation(projects.core.paging)
        implementation(projects.core.logger.api)
        implementation(projects.core.util)
        implementation(projects.data.recommendedshows.api)
        implementation(projects.data.requestManager.api)
        implementation(projects.data.shows.api)
        implementation(projects.tmdbApi.api)

        implementation(libs.sqldelight.extensions)
        implementation(libs.kotlinx.atomicfu)
        implementation(libs.store5)
      }
    }

    commonTest { dependencies { implementation(libs.bundles.unittest) } }
  }
}
