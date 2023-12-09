//
//  SearchView.swift
//  tv-maniac
//
//  Created by Thomas Kioko on 19.08.21.
//  Copyright © 2021 orgName. All rights reserved.
//

import SwiftUI
import TvManiac

struct SearchView: View {
    
    private let presenter: SearchPresenter
    
    @StateValue
    private var uiState: SearchState
    @State private var query = String()
    
    init(presenter: SearchPresenter){
        self.presenter = presenter
        _uiState = StateValue(presenter.state)
    }
    
    var body: some View {
        NavigationStack {
            VStack {
               
            }
            .background(Color.background)
            .navigationTitle("Search")
            .navigationBarTitleDisplayMode(.large)
            .searchable(text: $query)
            .task(id: query) {
                if query.isEmpty { return }
                if Task.isCancelled { return }
            }
        }
    }
}
