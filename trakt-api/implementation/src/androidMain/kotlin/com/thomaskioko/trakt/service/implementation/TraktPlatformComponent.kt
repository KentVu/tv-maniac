package com.thomaskioko.trakt.service.implementation

import io.ktor.client.engine.okhttp.OkHttp
import me.tatarka.inject.annotations.Provides
import software.amazon.lastmile.kotlin.inject.anvil.AppScope
import software.amazon.lastmile.kotlin.inject.anvil.ContributesTo

@ContributesTo(AppScope::class)
interface TraktPlatformComponent {

  @Provides
  fun provideTraktHttpClientEngine(interceptor: TraktAuthInterceptor): TraktHttpClientEngine =
    OkHttp.create { addInterceptor(interceptor) }
}
