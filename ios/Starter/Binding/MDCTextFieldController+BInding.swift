//
//  MDCTextFieldController+BInding.swift
//  Starter
//
//  Created by Colton Nohelty on 2/1/19.
//  Copyright © 2019 Colton Nohelty. All rights reserved.
//

import StarterCore
import MaterialComponents

extension MDCTextInputController {
    func bindErrorToSource(source: Source) -> Disposable {
        return source.subscribe(observer: NextObserver { [weak self] element in
            self?.setErrorText(element as! String?, errorAccessibilityValue: nil)
            return KotlinUnit()
        })
    }
}
