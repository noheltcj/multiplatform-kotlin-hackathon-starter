//
//  ConcurrentScheduler.swift
//  Starter
//
//  Created by Colton Nohelty on 3/31/19.
//  Copyright © 2019 Colton Nohelty. All rights reserved.
//

import StarterCore
import struct RxSwift.Completable
import struct RxSwift.RxTimeInterval
import class RxSwift.ConcurrentDispatchQueueScheduler
import class RxSwift.MainScheduler
import protocol RxSwift.Disposable

fileprivate typealias PlatformCompletable = RxSwift.Completable
fileprivate typealias PlatformDisposable = RxSwift.Disposable

class ConcurrentScheduler : NSObject, DelayingScheduler {
    private let concurrentScheduler = <~ConcurrentDispatchQueueScheduler.self
    private let mainScheduler = <~MainScheduler.self

    func scheduleDelayed(duration: Int64, operation: @escaping () -> KotlinUnit) -> DelayingSchedulerCancelable {
        let disposable = PlatformCompletable.empty()
            .delay(Double(duration) / 1000, scheduler: concurrentScheduler)
            .observeOn(mainScheduler)
            .subscribe(onCompleted: {
                let _ = operation()
            })
        return ScheduleDelayedCancelable(disposable: disposable)
    }
    
    fileprivate class ScheduleDelayedCancelable : NSObject, DelayingSchedulerCancelable {
        private let disposable: PlatformDisposable
        
        init(disposable: PlatformDisposable) {
            self.disposable = disposable
        }
        
        func cancel() {
            disposable.dispose()
        }
    }
}
