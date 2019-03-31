//
//  UserResponse.swift
//  Starter
//
//  Created by Colton Nohelty on 2/3/19.
//  Copyright © 2019 Colton Nohelty. All rights reserved.
//

import Foundation

struct UserResponse {
    let id: String
    let email: String
    let createdAt: Date
    let updatedAt: Date
}

extension UserResponse : Decodable {
    enum CodingKeys : String, CodingKey {
        case id
        case email
        case createdAt = "created_at"
        case updatedAt = "updated_at"
    }
    
    public init(from decoder: Decoder) throws {
        let container = try! decoder.container(keyedBy: CodingKeys.self)
        let dateFormatter = <~ISO8601DateFormatter.self
        
        id = try! container.decode(String.self, forKey: .id)
        email = try! container.decode(String.self, forKey: .email)
        createdAt = dateFormatter.date(from: try! container.decode(String.self, forKey: .createdAt))!
        updatedAt = dateFormatter.date(from: try! container.decode(String.self, forKey: .updatedAt))!
    }
}
