import com.thomaskioko.tvmaniac.plugins.addKspDependencyForAllTargets

plugins {
  alias(libs.plugins.tvmaniac.android.library)
  alias(libs.plugins.tvmaniac.multiplatform)
  alias(libs.plugins.ksp)
}

kotlin {
  sourceSets {
    androidMain {
      dependencies {
        implementation(projects.traktAuth.api)

        implementation(libs.androidx.activity)
        implementation(libs.androidx.browser)
        implementation(libs.androidx.core)
      }
    }

    commonMain {
      dependencies {
        implementation(projects.core.base)
        implementation(projects.core.logger)
        implementation(projects.traktAuth.api)
        implementation(libs.kotlinInject.runtime)
      }
    }
  }
}

android { namespace = "com.thomaskioko.tvmaniac.traktauth.implementation" }

addKspDependencyForAllTargets(libs.kotlinInject.compiler)
