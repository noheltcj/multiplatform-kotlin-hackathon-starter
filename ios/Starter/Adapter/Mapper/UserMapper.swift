//
//  UserMapper.swift
//  Starter
//
//  Created by Colton Nohelty on 2/3/19.
//  Copyright © 2019 Colton Nohelty. All rights reserved.
//

import StarterCore

class UserMapper {
    func mapUserResponse(userResponse: UserResponse) -> User {
        return User(id: userResponse.id, email: userResponse.email)
    }
}
