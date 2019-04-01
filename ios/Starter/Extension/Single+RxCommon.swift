//
//  Single+RxCommon.swift
//  Starter
//
//  Created by Colton Nohelty on 2/2/19.
//  Copyright © 2019 Colton Nohelty. All rights reserved.
//

import StarterCore
import struct RxCocoa.Driver
import class RxSwift.DisposeBag
import class RxSwift.MainScheduler

extension Driver {
    func processResponseAndConvertToRxCommon<T>(ofType: T.Type) -> Source {
        return Observable(createWithEmitter: { commonEmitter in
            MainScheduler.ensureExecutingOnScheduler(errorMessage: "Must be executed on main scheduler")
            let disposable = self.asSharedSequence().asObservable().subscribe(onNext: { (element: Element) in
                // Forcing due to swift generic constraint limitations
                let element = element as! PotentialData<T>
                guard let data = element.data else {
                    guard let responseError = element.error! as? NetworkAdapter.ResponseError, let responseCode = responseError.responseCode else {
                        commonEmitter.terminate(throwable: ApiAdapterUnknownException())
                        return
                    }
                    commonEmitter.terminate(throwable: ApiAdapterUnexpectedResponseException(statusCode: Int32(responseCode)))
                    return
                }
                commonEmitter.next(value: data)
            }, onCompleted: {
                commonEmitter.complete()
            })
            return Disposables().create(onDispose: {
                disposable.dispose()
                return KotlinUnit()
            })
        })
    }
}
