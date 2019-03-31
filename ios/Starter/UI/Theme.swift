//
//  Theme.swift
//  Starter
//
//  Created by Colton Nohelty on 1/31/19.
//  Copyright © 2019 Colton Nohelty. All rights reserved.
//

import MaterialComponents

class Theme {
    static var colorScheme: MDCSemanticColorScheme = {
        let colorScheme = MDCSemanticColorScheme()
        
        colorScheme.primaryColor = UIColor.gray
        colorScheme.primaryColorVariant = UIColor.lightGray
        colorScheme.onPrimaryColor = UIColor.white
        colorScheme.secondaryColor = UIColor.lightGray
        colorScheme.onSecondaryColor = UIColor.lightGray
        colorScheme.surfaceColor = UIColor.darkGray
        colorScheme.onSurfaceColor = UIColor.lightGray
        colorScheme.backgroundColor = UIColor.lightGray
        colorScheme.onBackgroundColor = UIColor.darkGray
        colorScheme.errorColor = UIColor.red
        
        return colorScheme
    }()
    
    static let buttonScheme: MDCButtonScheming = {
        let buttonScheme = MDCButtonScheme()
        
        buttonScheme.colorScheme = colorScheme
        buttonScheme.minimumHeight = 50
        
        return buttonScheme
    }()
    
    static func makeOutlinedButton(ofType type: MDCButton.ButtonType) -> MDCButton {
        let button = MDCButton(type: type)
        MDCOutlinedButtonThemer.applyScheme(buttonScheme, to: button)
        return button
    }
    
    static func makeContainedButton(ofType type: MDCButton.ButtonType) -> MDCButton {
        let button = MDCButton(type: type)
        MDCContainedButtonThemer.applyScheme(buttonScheme, to: button)
        return button
    }
    
    static let alertScheme: MDCAlertScheme = {
        let alertScheme = MDCAlertScheme()
        
        alertScheme.colorScheme = colorScheme

        return alertScheme
    }()
}
