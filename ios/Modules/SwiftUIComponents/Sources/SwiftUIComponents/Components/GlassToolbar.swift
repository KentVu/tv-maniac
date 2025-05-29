import SwiftUI

public struct GlassToolbar: View {
    private let title: String
    private let opacity: Double
    private let isLoading: Bool
    @Environment(\.colorScheme) private var colorScheme

    public init(
        title: String,
        opacity: Double,
        isLoading: Bool = false
    ) {
        self.title = title
        self.opacity = opacity
        self.isLoading = isLoading
    }

    public var body: some View {
        let toolbarHeight: CGFloat = 44
        let topPadding = (UIApplication.shared.connectedScenes.first as? UIWindowScene)?.windows.first?.safeAreaInsets
            .top ?? 0

        ZStack(alignment: .top) {
            VisualEffectView(effect: UIBlurEffect(style: colorScheme == .dark ? .dark : .light))
                .frame(height: toolbarHeight + topPadding)
                .opacity(opacity)
                .ignoresSafeArea()

            HStack(spacing: 16) {
                // Space for back button
                Rectangle()
                    .fill(Color.clear)
                    .frame(width: 30)

                Text(title)
                    .font(.system(size: 18, weight: .bold))
                    .foregroundColor(colorScheme == .dark ? .white : .black)
                    .opacity(opacity)
                    .lineLimit(1)
                    .padding(.bottom, 8)
                    .frame(maxWidth: .infinity, alignment: .center)

                if isLoading {
                    ProgressView()
                        .progressViewStyle(CircularProgressViewStyle(tint: colorScheme == .dark ? .white : .black))
                        .scaleEffect(0.8)
                        .frame(width: 30)
                } else {
                    Rectangle()
                        .fill(Color.clear)
                        .frame(width: 30)
                }
            }
            .padding(.horizontal, 16)
            .frame(height: toolbarHeight)
            .padding(.top, topPadding)
        }
        .frame(maxWidth: .infinity)
    }
}

struct VisualEffectView: UIViewRepresentable {
    let effect: UIVisualEffect

    func makeUIView(context _: UIViewRepresentableContext<Self>) -> UIVisualEffectView {
        UIVisualEffectView(effect: effect)
    }

    func updateUIView(_ uiView: UIVisualEffectView, context _: UIViewRepresentableContext<Self>) {
        uiView.effect = effect
    }
}

public struct NavigationBarModifier: ViewModifier {
    private var backgroundColor: UIColor
    @Environment(\.colorScheme) private var colorScheme

    public init(backgroundColor: UIColor) {
        let appearance = UINavigationBarAppearance()
        appearance.configureWithTransparentBackground()
        appearance.backgroundColor = backgroundColor

        // Remove back button text
        appearance.backButtonAppearance.normal.titleTextAttributes = [.foregroundColor: UIColor.clear]

        // Change back button color
        let backImage = UIImage(systemName: "chevron.left")?
            .withTintColor(.accentBlue, renderingMode: .alwaysOriginal)
        appearance.setBackIndicatorImage(backImage, transitionMaskImage: backImage)

        UINavigationBar.appearance().standardAppearance = appearance
        UINavigationBar.appearance().compactAppearance = appearance
        UINavigationBar.appearance().scrollEdgeAppearance = appearance
        UINavigationBar.appearance().tintColor = .accentBlue

        self.backgroundColor = backgroundColor
    }

    public func body(content: Content) -> some View {
        content
    }
}

// Add this extension to make navigation bar transparent
public extension View {
    func navigationBarColor(backgroundColor: UIColor) -> some View {
        modifier(NavigationBarModifier(backgroundColor: backgroundColor))
    }
}
