//
//  ClockAdapterImpl.swift
//  Starter
//
//  Created by Colton Nohelty on 1/30/19.
//  Copyright © 2019 Colton Nohelty. All rights reserved.
//

import Foundation
import StarterCore

class ClockAdapterImpl : NSObject, ClockAdapter {
    var currentTimeEpochMilli: Int64 = Date().epochMilli
}
