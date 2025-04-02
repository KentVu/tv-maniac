plugins {
  alias(libs.plugins.tvmaniac.kmp)
}

tvmaniac {
  useSerialization()

  multiplatform {
    addAndroidTarget()
    useKotlinInject()
    useKspAnvilCompiler()
  }
}

kotlin {
  sourceSets {
    androidMain { dependencies { implementation(libs.ktor.okhttp) } }

    commonMain {
      dependencies {
        implementation(projects.core.base)
        implementation(projects.core.logger.api)
        implementation(projects.tmdbApi.api)

        implementation(libs.ktor.core)
        implementation(libs.ktor.logging)
        implementation(libs.ktor.negotiation)
        implementation(libs.ktor.serialization.json)
        implementation(libs.sqldelight.extensions)
        implementation(libs.sqldelight.extensions)
      }
    }

    commonTest { dependencies { implementation(libs.ktor.serialization) } }

    iosMain {
      dependencies {
        implementation(libs.ktor.darwin)
        implementation(libs.ktor.negotiation)
      }
    }
  }
}

