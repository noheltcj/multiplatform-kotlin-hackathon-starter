//
//  Request.swift
//  Starter
//
//  Created by Colton Nohelty on 2/2/19.
//  Copyright © 2019 Colton Nohelty. All rights reserved.
//

import Foundation
import struct RxCocoa.Driver
import class RxSwift.Observable
import class RxSwift.ConcurrentDispatchQueueScheduler
import class MoreCodable.URLQueryItemsEncoder

class NetworkAdapter {
    private let baseUrl: String
    
    init(baseUrl: String) {
        self.baseUrl = baseUrl
    }
    
    func send<RequestType, ResponseType>(
        requestEntity: RequestType?,
        path: String,
        method: HTTPMethod,
        contentType: ContentType,
        headers: [String: String] = [String: String](),
        cachePolicy: NSURLRequest.CachePolicy = .reloadIgnoringLocalAndRemoteCacheData,
        timeoutIntervalInSeconds: TimeInterval = 10.0
    ) -> Driver<PotentialData<ResponseType>>
        where RequestType: Encodable, ResponseType: Decodable {
        return Observable
            .create { observer in
                var mutableHeaders = headers
                var requestData: Data? = nil
                switch contentType {
                    case .formUrlEncoded: do {
                        mutableHeaders["Content-Type"] = "application/x-www-form-urlencoded"
                        if let requestEntity = requestEntity {
                            var urlParser = URLComponents()
                            urlParser.percentEncodedQueryItems = try! URLQueryItemsEncoder().encode(requestEntity)
                            requestData = urlParser.percentEncodedQuery!.data(using: .ascii, allowLossyConversion: false)
                        }
                    }
                    case .json: do {
                        mutableHeaders["Content-Type"] = "application/json"
                        if let requestEntity = requestEntity {
                            requestData = try! JSONEncoder().encode(requestEntity)
                        }
                    }
                }
                
                var request = URLRequest(
                    url: URL(string: "\(self.baseUrl)\(path)")!,
                    cachePolicy: cachePolicy,
                    timeoutInterval: timeoutIntervalInSeconds
                )
                
                request.httpMethod = method.rawValue
                request.allHTTPHeaderFields = mutableHeaders
                request.httpBody = requestData
    
                let task = URLSession.shared.dataTask(with: request) { data, response, error in
                    if let error = error {
                        observer.on(.error(error))
                        return
                    }
                    guard let response = response as? HTTPURLResponse else {
                        observer.on(.error(UnknownError()))
                        return
                    }
                    guard 200...299 ~= response.statusCode else {
                        observer.on(.error(ResponseError(responseCode: response.statusCode, responseData: data)))
                        return
                    }
                    guard let actualData = data else {
                        observer.on(.completed)
                        return
                    }
                    observer.on(.next(PotentialData(withData: try! JSONDecoder().decode(ResponseType.self, from: actualData))))
                    observer.on(.completed)
                }
                
                task.resume()
                
                return RxSwiftDisposables.create {
                    if (task.state == URLSessionTask.State.running || task.state == URLSessionTask.State.suspended) {
                        task.cancel()
                    }
                }
            }
            .subscribeOn(<~ConcurrentDispatchQueueScheduler.self)
            .asDriver(onErrorRecover: { error in
                return Driver.just(PotentialData(withError: error))
            })
    }

    class ResponseError : Error {
        let responseCode: Int?
        let responseData: Data?
        
        init(responseCode: Int?, responseData: Data?) {
            self.responseCode = responseCode
            self.responseData = responseData
        }
    }
    
    enum HTTPMethod : String {
        case get
        case post
    }
    
    enum ContentType {
        case formUrlEncoded
        case json
    }
}
