//
//  UIView+Binding.swift
//  Starter
//
//  Created by Colton Nohelty on 2/3/19.
//  Copyright © 2019 Colton Nohelty. All rights reserved.
//

import UIKit
import StarterCore

extension UIControl {
    func bindIsEnabled(toSource source: Source) -> Disposable {
        return source.subscribe(observer: NextObserver { [weak self] element in
            self?.isEnabled = element as! Bool
            return KotlinUnit()
        })
    }
}
