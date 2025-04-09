plugins {
  alias(libs.plugins.tvmaniac.kmp)
}

kotlin {
  sourceSets {
    commonMain {
      dependencies {
        api(projects.core.networkUtil)
        api(projects.database)
        api(projects.data.shows.api)

        implementation(projects.core.base)

        api(libs.androidx.paging.common)
        api(libs.coroutines.core)

        implementation(libs.kotlinInject.runtime)
      }
    }
  }
}
