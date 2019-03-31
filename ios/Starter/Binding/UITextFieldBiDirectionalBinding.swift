//
//  UITextFieldBiDirectionalBinding.swift
//  Starter
//
//  Created by Colton Nohelty on 2/1/19.
//  Copyright © 2019 Colton Nohelty. All rights reserved.
//

import StarterCore
import UIKit

class UITextFieldBiDirectionalBinding : BiDirectionalBinding {
    override func transformForUpstream(localData: Any?) -> Any? {
        return localData
    }
    
    override func transformForSubscriber(emission: Any?) -> Any? {
        return emission
    }
    
    @objc func didEditLocalField(sender: UITextField) {
        onSubscriberUpdatedLocally(localData: sender.text)
    }
}
