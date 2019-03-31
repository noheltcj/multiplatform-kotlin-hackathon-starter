//
//  BindableRelay+ObjC.swift
//  Starter
//
//  Created by Colton Nohelty on 2/1/19.
//  Copyright © 2019 Colton Nohelty. All rights reserved.
//

import StarterCore
import UIKit

extension UITextField {
    func bindBiDirectionally(toRelay relay: BindingRelay) -> Disposable {
        let binding = UITextFieldBiDirectionalBinding(relay: relay, onNext: { [weak self] emission in
            self?.text = emission as! String?
            return KotlinUnit()
        })
        
        let didReceiveLocalUpdateAction = #selector(UITextFieldBiDirectionalBinding.didEditLocalField(sender:))
        
        addTarget(binding, action: didReceiveLocalUpdateAction, for: .editingChanged)
        binding.start()
        
        return Disposables().create { [weak self] in
            binding.dispose()
            self?.removeTarget(self, action: didReceiveLocalUpdateAction, for: .editingChanged)
            
            return KotlinUnit()
        }
    }
}
