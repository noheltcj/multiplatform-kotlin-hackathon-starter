//
//  ApiAdapterImpl.swift
//  Starter
//
//  Created by Colton Nohelty on 1/30/19.
//  Copyright © 2019 Colton Nohelty. All rights reserved.
//

import Foundation
import StarterCore
import RxSwift
import RxCocoa
import JWTDecode

class ApiAdapterImpl : NSObject, ApiAdapter {
    private let networkAdapter = <~NetworkAdapter.self
    private let mainScheduler = <~MainScheduler.self
    private let authorizationMapper = <~AuthorizationMapper.self
    private let userMapper = <~UserMapper.self
    
    func exchangeCredentialsForAuthorization(email: String, password: String) -> RxCommonSingle {
        let requestEntity = CredentialsAuthorizationRequest(
            email: email,
            password: password,
            clientId: Constants.clientId,
            grantType: "password"
        )
        let responseDriver: Driver<PotentialData<AuthorizationResponse>> = networkAdapter.send(
            requestEntity: requestEntity,
            path: "/auth/token",
            method: .post,
            contentType: .formUrlEncoded
        )
        return responseDriver
            .processResponseAndConvertToRxCommon(ofType: AuthorizationResponse.self)
            .map { response -> Authorization in
                let response = response as! AuthorizationResponse
                return self.authorizationMapper
                    .mapAuthorizationResponse(authorizationResponse: response,
                                              accessTokenClaims: try! decode(jwt: response.accessToken),
                                              refreshTokenClaims: try! decode(jwt: response.refreshToken))
            }
            .toSingle()
    }
    
    func refreshAuthorization(authorization: Authorization) -> RxCommonSingle {
        return Single(just: authorization)
    }
    
    func signUp(email: String, password: String) -> RxCommonSingle {
        let requestEntity = SignUpNewUserRequest(email: email, password: password, clientId: Constants.clientId)
        let responseDriver: Driver<PotentialData<UserResponse>> = networkAdapter.send(
            requestEntity: requestEntity,
            path: "/auth/signup",
            method: .post,
            contentType: .formUrlEncoded
        )
        return responseDriver
            .processResponseAndConvertToRxCommon(ofType: UserResponse.self)
            .map { response -> User in
                return self.userMapper.mapUserResponse(userResponse: response as! UserResponse)
            }
            .toSingle()
    }
    
    func initiatePasswordReset(email: String) -> RxCommonSingle {
        return Single(just: KotlinUnit())
    }
}

