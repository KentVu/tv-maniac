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
        implementation(projects.data.requestManager.api)

        implementation(libs.kotlinx.datetime)
        implementation(libs.sqldelight.extensions)
      }
    }

    commonTest {
      dependencies {
        implementation(projects.database.test)

        implementation(libs.kotlin.test)
        implementation(libs.kotest.assertions)
      }
    }
  }
}
