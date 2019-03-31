//
//  Date+Epoch.swift
//  Starter
//
//  Created by Colton Nohelty on 1/30/19.
//  Copyright © 2019 Colton Nohelty. All rights reserved.
//

import Foundation

extension Date {
    var epochMilli: Int64 {
        return Int64((self.timeIntervalSince1970 * 1000.0).rounded())
    }
    
    init(withEpochMilli millis: Int64) {
        self = Date(timeIntervalSince1970: TimeInterval(millis) / 1000)
    }
}
