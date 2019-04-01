//
//  StorageAdapterImpl.swift
//  Starter
//
//  Created by Colton Nohelty on 1/30/19.
//  Copyright © 2019 Colton Nohelty. All rights reserved.
//

import Foundation
import StarterCore

class StorageAdapterImpl : NSObject, StorageAdapter {
    private var temporaryUserStorage: User? = nil // TODO: Implement persistence
    private var temporaryAuthorizationStorage: Authorization? = nil // TODO: Implement persistence
    
    private lazy var currentUserRelay = BehaviorRelay(seed: temporaryUserStorage)
    private lazy var currentAuthorizationRelay = BehaviorRelay(seed: temporaryAuthorizationStorage)

    var usingLoggedInExperience: Bool = false

    func observeUser() -> Source {
        return currentUserRelay
    }
    
    func saveUser(user: User?) {
        temporaryUserStorage = user
        currentUserRelay.onNext(value: user)
    }

    func observeAuthorization() -> Source {
        return currentAuthorizationRelay
    }
    
    func saveAuthorization(authorization: Authorization?) {
        temporaryAuthorizationStorage = authorization
        currentAuthorizationRelay.onNext(value: authorization)
    }
}
