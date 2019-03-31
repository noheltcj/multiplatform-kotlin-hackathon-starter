//
//  AuthorizationMapper.swift
//  Starter
//
//  Created by Colton Nohelty on 2/2/19.
//  Copyright © 2019 Colton Nohelty. All rights reserved.
//

import StarterCore
import JWTDecode

class AuthorizationMapper {
    func mapAuthorizationResponse(
        authorizationResponse: AuthorizationResponse,
        accessTokenClaims: JWT,
        refreshTokenClaims: JWT
    ) -> Authorization {
        return Authorization(
            accessToken: authorizationResponse.accessToken,
            accessTokenExpiresAtEpochMilli: accessTokenClaims.expiresAt!.epochMilli,
            accessTokenIssuedAtEpochMilli: accessTokenClaims.issuedAt!.epochMilli,
            tokenType: authorizationResponse.tokenType,
            refreshToken: authorizationResponse.refreshToken,
            refreshTokenExpiresAtEpochMilli: refreshTokenClaims.expiresAt!.epochMilli
        )
    }
}
