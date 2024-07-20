//
//  BorderedButton.swift
//  tv-maniac
//
//  Created by Thomas Kioko on 15.01.22.
//  Copyright © 2022 orgName. All rights reserved.
//

import SwiftUI

public struct BorderedButton : View {
    public let text: String
    public let systemImageName: String
    public let isOn: Bool
    public let action: () -> Void
    public var color: Color = .accent
    public var borderColor: Color = .accent

    public init(text: String, systemImageName: String, isOn: Bool, action: @escaping () -> Void) {
        self.text = text
        self.systemImageName = systemImageName
        self.isOn = isOn
        self.action = action
    }

    public var body: some View {
        Button(action: {
            self.action()
        }, label: {
            HStack(alignment: .center, spacing: 4) {
                Image(systemName: systemImageName)
                    .resizable()
                    .aspectRatio(contentMode: .fit)
                    .foregroundColor(isOn ? Color.accent : color)
                    .frame(width: 24, height: 24)
                    .padding(.trailing, 16)

                Text(text)
                    .bodyMediumFont(size: 16)
                    .foregroundColor(Color.accent)
            }
        })
        .buttonStyle(BorderlessButtonStyle())
        .padding(12)
        .background(
            RoundedRectangle(cornerRadius: 5)
                .stroke(borderColor, lineWidth: 2)
                .background(isOn ? color : .clear)
                .cornerRadius(2)
        )
    }
}

struct BorderedButton_Previews : PreviewProvider {
    static var previews: some View {
        VStack {
            BorderedButton(
                text: "Watch Trailer",
                systemImageName: "film",
                isOn: false,
                action: { }
            )
            BorderedButton(
                text: "Follow",
                systemImageName: "film",
                isOn: true,
                action: { }
            )
        }
    }
}
