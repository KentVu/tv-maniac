//
//  RootNavigationView.swift
//  tv-maniac
//
//  Created by Thomas Kioko on 12/8/24.
//  Copyright © 2024 orgName. All rights reserved.
//

import SwiftUI
import TvManiacKit

struct RootNavigationView: View {
    private let rootPresenter: RootPresenter
    private let rootNavigator: RootNavigator
    @StateObject @KotlinStateFlow private var themeState: ThemeState
    @StateObject private var store = SettingsAppStorage.shared

    init(rootPresenter: RootPresenter, rootNavigator: RootNavigator) {
        self.rootPresenter = rootPresenter
        self.rootNavigator = rootNavigator
        _themeState = .init(rootPresenter.themeState)
    }

    var body: some View {
        SplashView {
            DecomposeNavigationView(
                stack: rootPresenter.childStack,
                onBack: rootNavigator.popTo,
                content: { child in
                    switch onEnum(of: child) {
                    case let .home(child):
                        TabBarView(presenter: child.presenter)
                    case let .showDetails(child):
                        ShowDetailsView(presenter: child.presenter)
                    case let .seasonDetails(child):
                        SeasonDetailsView(presenter: child.presenter)
                    case .moreShows:
                        EmptyView()
                    case .trailers:
                        EmptyView()
                    case .genreShows:
                        EmptyView()
                    }
                }
            )
        }
        .appTheme()
        .onChange(of: themeState.appTheme) { newTheme in
            store.appTheme = newTheme.toDeveiceAppTheme()
        }
    }
}
