package com.thomaskioko.tvmaniac.ui.discover

import androidx.activity.ComponentActivity
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.remember
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import com.thomaskioko.tvmaniac.compose.components.TvManiacBackground
import com.thomaskioko.tvmaniac.core.view.UiMessage
import com.thomaskioko.tvmaniac.presentation.discover.DiscoverViewState
import com.thomaskioko.tvmaniac.screenshottests.captureMultiDevice
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import org.robolectric.annotation.GraphicsMode
import org.robolectric.annotation.LooperMode

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [33])
@GraphicsMode(GraphicsMode.Mode.NATIVE)
@LooperMode(LooperMode.Mode.PAUSED)
class DiscoverScreenshotTest {

  @get:Rule
  val composeTestRule = createAndroidComposeRule<ComponentActivity>()

  @Test
  fun discoverScreenEmptyState() {
    composeTestRule.captureMultiDevice("DiscoverScreenEmptyState") {
      TvManiacBackground {
        DiscoverScreen(
          state = DiscoverViewState.Empty,
          pagerState = rememberPagerState(pageCount = { 5 }),
          snackBarHostState = remember { SnackbarHostState() },
          onAction = {},
        )
      }
    }
  }

  @Test
  fun discoverScreenLoading() {
    composeTestRule.captureMultiDevice("DiscoverScreenLoading") {
      TvManiacBackground {
        DiscoverScreen(
          state = DiscoverViewState.Empty.copy(
            featuredRefreshing = true,
            topRatedRefreshing = true,
            trendingRefreshing = true,
            upcomingRefreshing = true,
            popularRefreshing = true,
          ),
          pagerState = rememberPagerState(pageCount = { 5 }),
          snackBarHostState = remember { SnackbarHostState() },
          onAction = {},
        )
      }
    }
  }

  @Test
  fun discoverScreenErrorState() {
    composeTestRule.captureMultiDevice("DiscoverScreenErrorState") {
      TvManiacBackground {
        DiscoverScreen(
          state = DiscoverViewState.Empty.copy(
            message = UiMessage(message = "Opps! Something went wrong"),
          ),
          pagerState = rememberPagerState(pageCount = { 5 }),
          snackBarHostState = remember { SnackbarHostState() },
          onAction = {},
        )
      }
    }
  }

  @Test
  fun discoverScreenDataLoaded() {
    composeTestRule.captureMultiDevice("DiscoverScreenDataLoaded") {
      TvManiacBackground {
        DiscoverScreen(
          state = discoverContentSuccess,
          pagerState = rememberPagerState(pageCount = { 5 }),
          snackBarHostState = remember { SnackbarHostState() },
          onAction = {},
        )
      }
    }
  }
}
