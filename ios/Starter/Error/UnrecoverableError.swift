//
//  UnrecoverableError.swift
//  Starter
//
//  Created by Colton Nohelty on 2/3/19.
//  Copyright © 2019 Colton Nohelty. All rights reserved.
//

import Foundation

class UnrecoverableError : Error {
    let localizedDescription: String
    
    init(localizedDescription: String) {
        self.localizedDescription = localizedDescription
    }
}
