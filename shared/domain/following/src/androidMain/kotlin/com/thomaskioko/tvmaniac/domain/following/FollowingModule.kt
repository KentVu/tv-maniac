package com.thomaskioko.tvmaniac.domain.following

import com.thomaskioko.tvmaniac.shows.api.ShowsRepository
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import org.koin.core.module.Module
import org.koin.dsl.module

actual fun followingDomainModule() : Module = module {  }

@InstallIn(SingletonComponent::class)
@dagger.Module
object FollowingModule {

    @Provides
    fun provideFollowingStateMachine(
        repository: ShowsRepository
    ): FollowingStateMachine = FollowingStateMachine(repository)
}