package com.thomaskioko.tvmaniac.datastore.implementation

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import com.thomaskioko.tvmaniac.core.base.model.AppCoroutineScope
import me.tatarka.inject.annotations.Provides
import platform.Foundation.NSDocumentDirectory
import platform.Foundation.NSFileManager
import platform.Foundation.NSURL
import platform.Foundation.NSUserDomainMask
import software.amazon.lastmile.kotlin.inject.anvil.AppScope
import software.amazon.lastmile.kotlin.inject.anvil.ContributesTo
import software.amazon.lastmile.kotlin.inject.anvil.SingleIn

@ContributesTo(AppScope::class)
interface DataStorePlatformComponent {

  @SingleIn(AppScope::class)
  @Provides
  fun provideDataStore(dispatchers: AppCoroutineScope): DataStore<Preferences> =
    createDataStore(
      coroutineScope = dispatchers.io,
      produceFile = {
        val documentDirectory: NSURL? =
          NSFileManager.defaultManager.URLForDirectory(
            directory = NSDocumentDirectory,
            inDomain = NSUserDomainMask,
            appropriateForURL = null,
            create = false,
            error = null,
          )
        requireNotNull(documentDirectory).path + "/$dataStoreFileName"
      },
    )
}
