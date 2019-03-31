//
//  Either.swift
//  Starter
//
//  Created by Colton Nohelty on 2/3/19.
//  Copyright © 2019 Colton Nohelty. All rights reserved.
//

import Foundation

class PotentialData<T> {
    let data: T?
    let error: Error?
    
    init(withData data: T) {
        self.data = data
        self.error = nil
    }
    
    init(withError error: Error) {
        self.data = nil
        self.error = error
    }
}
