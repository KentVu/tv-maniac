import SwiftUI

public struct TrailerListView: View {
    private let trailers: [SwiftTrailer]
    private let openInYouTube: Bool
    private let onError: ((Error) -> Void)?

    public init(
        trailers: [SwiftTrailer],
        openInYouTube: Bool,
        onError: ((Error) -> Void)? = nil
    ) {
        self.trailers = trailers
        self.openInYouTube = openInYouTube
        self.onError = onError
    }

    public var body: some View {
        if !trailers.isEmpty {
            VStack {
                ChevronTitle(title: "Trailers")

                ScrollView(.horizontal, showsIndicators: false) {
                    HStack {
                        ForEach(trailers, id: \.key) { trailer in
                            YoutubeItemView(
                                openInYouTube: openInYouTube,
                                key: trailer.key,
                                name: trailer.name,
                                thumbnailUrl: trailer.youtubeThumbnailUrl,
                                onError: onError
                            )
                            .padding(.horizontal, 4)
                            .padding(.leading, trailer.key == trailers.first?.key ? 16 : 0)
                            .padding(.trailing, trailer.key == trailers.last?.key ? 16 : 0)
                        }
                    }
                }
            }
        }
    }
}

#Preview {
    VStack {
        TrailerListView(
            trailers: [
                .init(
                    showId: 123,
                    key: "XZ8daibM3AE",
                    name: "Series Trailer",
                    youtubeThumbnailUrl: "https://i.ytimg.com/vi/XZ8daibM3AE/hqdefault.jpg"
                ),
                .init(
                    showId: 1234,
                    key: "XZ8daibM3AE",
                    name: "Series Trailer",
                    youtubeThumbnailUrl: "https://i.ytimg.com/vi/XZ8daibM3AE/hqdefault.jpg"
                ),
            ],
            openInYouTube: false
        )
    }
}
