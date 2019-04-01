//
//  ApplicationStateAdapterImpl.swift
//  Starter
//
//  Created by Colton Nohelty on 1/29/19.
//  Copyright © 2019 Colton Nohelty. All rights reserved.
//

import Foundation
import StarterCore

class ApplicationStateAdapterImpl : NSObject, ApplicationStateAdapter {
    private var isInBackgroundBackingRelay = BehaviorRelay(seed: false)
    
    var isInBackground: Bool {
        get {
            return isInBackgroundBackingRelay.value as! Bool
        }
        set(newValue) {
            if (isInBackground != newValue) {
                isInBackgroundBackingRelay.onNext(value: newValue)
            }
        }
    }
    
    lazy var isInBackgroundSubject: Subject = isInBackgroundBackingRelay
    
    var applicationReady: Single = Single(just: KotlinUnit())
}
