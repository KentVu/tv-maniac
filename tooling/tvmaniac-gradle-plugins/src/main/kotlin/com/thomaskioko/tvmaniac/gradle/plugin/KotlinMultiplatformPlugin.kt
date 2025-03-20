package com.thomaskioko.tvmaniac.gradle.plugin

import com.thomaskioko.tvmaniac.gradle.plugin.utils.compilerOptions
import com.thomaskioko.tvmaniac.gradle.plugin.utils.baseExtension
import com.thomaskioko.tvmaniac.gradle.plugin.utils.kotlin
import com.thomaskioko.tvmaniac.gradle.plugin.utils.kotlinMultiplatform
import com.thomaskioko.tvmaniac.gradle.plugin.extensions.MultiplatformExtension
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.tasks.testing.Test
import com.thomaskioko.tvmaniac.gradle.plugin.utils.defaultTestSetup
import org.jetbrains.kotlin.gradle.plugin.mpp.KotlinNativeTarget

/**
 * A base plugin that configures a Kotlin Multiplatform project with common defaults.
 */
public abstract class KotlinMultiplatformPlugin : Plugin<Project> {
  override fun apply(target: Project) {
    target.plugins.apply("org.jetbrains.kotlin.multiplatform")
    target.plugins.apply(BasePlugin::class.java)

    target.baseExtension.extensions.create("multiplatform", MultiplatformExtension::class.java)

    target.kotlinMultiplatform {
      applyDefaultHierarchyTemplate()

      if (target.pluginManager.hasPlugin("com.android.library")) {
        androidTarget()
      }

      jvm()

      iosArm64()
      iosSimulatorArm64()

      targets.withType(KotlinNativeTarget::class.java).configureEach {

        it.binaries.all {
          it.linkerOpts("-lsqlite3")
        }

        it.compilations.configureEach {
          it.compileTaskProvider.configure {
            compilerOptions {
              freeCompilerArgs.add("-Xallocator=custom")
              freeCompilerArgs.add("-XXLanguage:+ImplicitSignedToUnsignedIntegerConversion")
              freeCompilerArgs.add("-Xadd-light-debug=enable")
              freeCompilerArgs.add("-Xexpect-actual-classes")

              freeCompilerArgs.addAll(
                "-opt-in=kotlinx.cinterop.ExperimentalForeignApi",
                "-opt-in=kotlinx.cinterop.BetaInteropApi",
              )
            }
          }
        }
      }
    }

    target.kotlin {
      compilerOptions {
        freeCompilerArgs.add("-Xexpect-actual-classes")
      }
    }

    target.tasks.withType(Test::class.java).configureEach(Test::defaultTestSetup)
  }
}
