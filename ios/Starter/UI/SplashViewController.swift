//
//  SplashViewController.swift
//  Starter
//
//  Created by Colton Nohelty on 1/31/19.
//  Copyright © 2019 Colton Nohelty. All rights reserved.
//

import UIKit
import StarterCore
import MaterialComponents

class SplashViewController : UIViewController {
    private lazy var progressIndicator = MDCActivityIndicator(frame: UIScreen.main.bounds)
    
    override func viewDidLoad() {
        view.backgroundColor = UIColor.white

        view.addSubview(progressIndicator)
        
        progressIndicator.startAnimating()
        
        super.viewDidLoad()
    }
    
    override func viewDidAppear(_ animated: Bool) {
        super.viewDidAppear(animated)
        
        AppDelegate.shared.routingController.showLogin()
    }
}
