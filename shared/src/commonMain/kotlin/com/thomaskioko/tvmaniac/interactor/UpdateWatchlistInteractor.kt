package com.thomaskioko.tvmaniac.interactor

import com.thomaskioko.tvmaniac.datasource.repository.tvshow.TvShowsRepository
import com.thomaskioko.tvmaniac.shared.core.FlowInteractor
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class UpdateWatchlistInteractor constructor(
    private val repository: TvShowsRepository,
) : FlowInteractor<UpdateShowParams, Unit>() {

    override fun run(params: UpdateShowParams): Flow<Unit> = flow {
        repository.updateWatchlist(
            showId = params.showId,
            addToWatchList = params.addToWatchList
        )
        emit(Unit)
    }
}

data class UpdateShowParams(
    val showId: Int,
    val addToWatchList: Boolean
)
