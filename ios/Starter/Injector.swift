//
//  Injector.swift
//  Starter
//
//  Created by Colton Nohelty on 8/10/18.
//  Copyright © 2018 Colton Nohelty. All rights reserved.
//

import StarterCore
import RxSwift
import Swinject
import SwinjectAutoregistration

prefix operator <~
prefix func <~ <P>(type: P.Type) -> P {
    return Injector.instance.dependencyGraph.resolve(type)!
}

class Injector {
    static let instance = Injector()
    
    let dependencyGraph = Container()
    
    init() {
        print("Initializing Injector")
        registerCommonDependencies()
        registerProductionDependencies()
    }
    
    fileprivate func registerCommonDependencies() {
        // MARK: Schedulers
        dependencyGraph.register(MainScheduler.self) { _ in MainScheduler.instance }
            .inObjectScope(.container)
        dependencyGraph.register(ConcurrentDispatchQueueScheduler.self) { _ in
            ConcurrentDispatchQueueScheduler(queue: DispatchQueue.global(qos: .background))
        }.inObjectScope(.container)
        
        // MARK: ViewModels
        dependencyGraph.autoregister(ApplicationViewModel.self, initializer: ApplicationViewModel.init)
        dependencyGraph.autoregister(LoginViewModel.self, initializer: LoginViewModel.init)
        
        // MARK: Interactors
        dependencyGraph.autoregister(AuthInteractor.self, initializer: AuthInteractorImpl.init)
            .inObjectScope(.container)
        dependencyGraph.autoregister(ValidationInteractor.self, initializer: ValidationInteractorImpl.init)
            .inObjectScope(.container)
    }
    
    fileprivate func registerProductionDependencies() {
        print("Registering production target dependencies")
        // MARK: Adapters
        dependencyGraph.autoregister(ApplicationStateAdapter.self, initializer: ApplicationStateAdapterImpl.init)
            .inObjectScope(.container)
        dependencyGraph.autoregister(StorageAdapter.self, initializer: StorageAdapterImpl.init)
            .inObjectScope(.container)
        dependencyGraph.autoregister(LocalizationAdapter.self, initializer: LocalizationAdapterImpl.init)
        dependencyGraph.autoregister(ClockAdapter.self, initializer: ClockAdapterImpl.init)
        dependencyGraph.autoregister(ApiAdapter.self, initializer: ApiAdapterImpl.init)
        
        // MARK: Mappers
        dependencyGraph.autoregister(AuthorizationMapper.self, initializer: AuthorizationMapper.init)
        dependencyGraph.autoregister(UserMapper.self, initializer: UserMapper.init)
        
        // MARK: Utility
        dependencyGraph.register(NetworkAdapter.self) { _ in
            NetworkAdapter(baseUrl: Constants.apiBaseUrl)
        }
        dependencyGraph.register(ISO8601DateFormatter.self) { _ in
            let formatter = ISO8601DateFormatter()
            formatter.formatOptions = [.withInternetDateTime, .withFractionalSeconds]
            formatter.timeZone = TimeZone(secondsFromGMT: 0)
            return formatter
        }
    }
}
