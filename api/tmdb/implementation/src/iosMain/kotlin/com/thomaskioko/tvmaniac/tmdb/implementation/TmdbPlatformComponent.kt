package com.thomaskioko.tvmaniac.tmdb.implementation

import io.ktor.client.engine.darwin.Darwin
import me.tatarka.inject.annotations.Provides
import software.amazon.lastmile.kotlin.inject.anvil.AppScope
import software.amazon.lastmile.kotlin.inject.anvil.ContributesTo
import software.amazon.lastmile.kotlin.inject.anvil.SingleIn

@ContributesTo(AppScope::class)
interface TmdbPlatformComponent {

    @Provides
    @SingleIn(AppScope::class)
    fun provideTmdbHttpClientEngine(): TmdbHttpClientEngine = Darwin.create()
}
