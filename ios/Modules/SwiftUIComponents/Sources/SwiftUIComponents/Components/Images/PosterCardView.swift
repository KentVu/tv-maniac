import SDWebImageSwiftUI
import SwiftUI

public struct PosterCardView: View {
  private let title: String
  private let posterUrl: String?
  private let isInLibrary: Bool
  private let posterWidth: CGFloat
  private let posterHeight: CGFloat
  private let posterRadius: CGFloat
  
  public init(
    title: String,
    posterUrl: String?,
    isInLibrary: Bool = false,
    posterWidth: CGFloat = 120,
    posterHeight: CGFloat = 180,
    posterRadius: CGFloat = 4
  ) {
    self.title = title
    self.posterUrl = posterUrl
    self.isInLibrary = isInLibrary
    self.posterWidth = posterWidth
    self.posterHeight = posterHeight
    self.posterRadius = posterRadius
  }
  
  public var body: some View {
    PosterItemView(
      title: nil,
      posterUrl: posterUrl,
      isInLibrary: isInLibrary,
      posterWidth: posterWidth,
      posterHeight: posterHeight,
      posterRadius: posterRadius
    )
    .overlay {
      ZStack {
        Rectangle().fill(.black.opacity(0.5))
        VStack {
          Spacer()
          HStack {
            Text(title)
              .foregroundColor(.white)
              .font(.avenirNext(size: 18))
              .fontWeight(.semibold)
              .multilineTextAlignment(.center)
              .lineLimit(3)
              .frame(maxWidth: .infinity, alignment: .center)
            
            Spacer()
          }
          .padding(.horizontal)
          .padding(.bottom, 8)
        }
      }
      .frame(width: posterWidth, height: posterHeight, alignment: .center)
    }
  }
}

#Preview {
  VStack {
    PosterCardView(
      title: "Arcane",
      posterUrl: "https://image.tmdb.org/t/p/w780/fqldf2t8ztc9aiwn3k6mlX3tvRT.jpg",
      isInLibrary: true,
      posterWidth: 160,
      posterHeight: 240
    )
    
    PosterCardView(
      title: "Arcane",
      posterUrl: nil,
      isInLibrary: true,
      posterWidth: 160,
      posterHeight: 240
    )
  }
}
