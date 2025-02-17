plugins {
  alias(libs.plugins.tvmaniac.baseline.profile)
}

tvmaniac {
  benchmark {
    minSdkVersion(28)
    useManagedDevices()
 }
}

dependencies {
  implementation(libs.androidx.junit)
  implementation(libs.androidx.espresso.core)
  implementation(libs.androidx.uiautomator)
  implementation(libs.androidx.benchmark.macro.junit4)
}

