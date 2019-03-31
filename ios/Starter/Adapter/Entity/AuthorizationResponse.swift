//
//  AuthorizationResponse.swift
//  Starter
//
//  Created by Colton Nohelty on 2/2/19.
//  Copyright © 2019 Colton Nohelty. All rights reserved.
//

import Foundation

struct AuthorizationResponse: Any {
    let accessToken: String
    let expiresIn: UInt64
    let tokenType: String
    let refreshToken: String
}

extension AuthorizationResponse : Decodable {
    enum CodingKeys: String, CodingKey {
        case accessToken = "access_token"
        case expiresIn = "expires_in"
        case tokenType = "token_type"
        case refreshToken = "refresh_token"
    }
    
    public init(from decoder: Decoder) throws {
        let container = try decoder.container(keyedBy: CodingKeys.self)
        
        let accessToken = try container.decode(String.self, forKey: .accessToken)
        let expiresIn = try container.decode(UInt64.self, forKey: .expiresIn)
        let tokenType = try container.decode(String.self, forKey: .tokenType)
        let refreshToken = try container.decode(String.self, forKey: .refreshToken)
        
        self.init(accessToken: accessToken, expiresIn: expiresIn, tokenType: tokenType, refreshToken: refreshToken)
    }
}
