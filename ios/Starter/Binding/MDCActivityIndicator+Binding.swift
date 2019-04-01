//
//  MDCActivityIndicator+Binding.swift
//  Starter
//
//  Created by Colton Nohelty on 2/3/19.
//  Copyright © 2019 Colton Nohelty. All rights reserved.
//

import MaterialComponents
import StarterCore

extension MDCActivityIndicator {
    func bind(toSource source: Source) -> Disposable {
        return source.subscribe(observer: NextObserver { [weak self] element in
            if (element as! Bool) {
                self?.isHidden = false
                self?.startAnimating()
            } else {
                self?.isHidden = true
                self?.stopAnimating()
            }
            return KotlinUnit()
        })
    }
}
