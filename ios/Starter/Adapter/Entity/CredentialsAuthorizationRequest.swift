//
//  CredentialsAuthorizationRequest.swift
//  Starter
//
//  Created by Colton Nohelty on 2/2/19.
//  Copyright © 2019 Colton Nohelty. All rights reserved.
//

import Foundation

struct CredentialsAuthorizationRequest {
    let email: String
    let password: String
    let clientId: String
    let grantType: String
}

extension CredentialsAuthorizationRequest : Encodable {
    enum CodingKeys: String, CodingKey {
        case email
        case password
        case clientId = "client_id"
        case grantType = "grant_type"
    }
    
    public func encode(to encoder: Encoder) throws {
        var container = encoder.container(keyedBy: CodingKeys.self)
        
        try container.encode(email, forKey: .email)
        try container.encode(password, forKey: .password)
        try container.encode(clientId, forKey: .clientId)
        try container.encode(grantType, forKey: .grantType)
    }
}
