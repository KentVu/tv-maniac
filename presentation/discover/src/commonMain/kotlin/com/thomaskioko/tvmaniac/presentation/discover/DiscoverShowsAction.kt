package com.thomaskioko.tvmaniac.presentation.discover

sealed interface DiscoverShowAction

data object RetryLoading : DiscoverShowAction
data object SnackBarDismissed : DiscoverShowAction
data object UpComingClicked : DiscoverShowAction
data object TrendingClicked : DiscoverShowAction
data object PopularClicked : DiscoverShowAction
data object TopRatedClicked : DiscoverShowAction

data class ShowClicked(val id: Long) : DiscoverShowAction
