import SwiftUI

public struct CircularButton: View {
  private let iconName: String
  private let width: CGFloat
  private let height: CGFloat
  private let action: () -> Void

  @Environment(\.colorScheme) private var colorScheme
  @State private var isPressed = false

  public init(
    iconName: String,
    width: CGFloat = 40,
    height: CGFloat = 40,
    action: @escaping () -> Void,
    isPressed: Bool = false
  ) {
    self.iconName = iconName
    self.action = action
    self.width = width
    self.height = height
    self.isPressed = isPressed
  }

  public var body: some View {
    Button(action: {
      isPressed = true
      action()
      DispatchQueue.main.asyncAfter(deadline: .now() + 0.2) {
        isPressed = false
      }
    }) {
      ZStack {
        Circle()
          .fill(colorScheme == .dark ? Color.white : .gray.opacity(0.8))
          .overlay(
            Image(systemName: iconName)
              .resizable()
              .scaledToFit()
              .foregroundColor(colorScheme == .dark ? Color.black : Color.white)
              .font(.system(size: 20, weight: .bold))
              .padding(12)
          )
          .frame(width: width, height: height)
          .buttonElevationEffect(isPressed: $isPressed)
      }
    }
    .buttonStyle(PlainButtonStyle())
  }
}

#Preview {
  CircularButton(iconName: "arrow.backward", action: {})
}
