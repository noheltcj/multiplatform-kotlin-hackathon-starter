//
//  LocalizationAdapterImpl.swift
//  Starter
//
//  Created by Colton Nohelty on 1/30/19.
//  Copyright © 2019 Colton Nohelty. All rights reserved.
//

import Foundation
import StarterCore

class LocalizationAdapterImpl : NSObject, LocalizationAdapter {
    func get(stringResource: StringResource) -> String {
        switch(stringResource) {
        case .genericerrortitle: return "Woah"
        case .genericerrormessage: return "An error has occurred"
        case .logininvalidemail: return "Please enter a valid email address"
        case .logininvalidpassword: return "Password must be at least 8 characters long"
        case .logininvalidcredentials: return "Invalid username or password"
        default:
            exit(1)
        }
    }
}
