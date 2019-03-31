//
//  RoutingController.swift
//  Starter
//
//  Created by Colton Nohelty on 1/31/19.
//  Copyright © 2019 Colton Nohelty. All rights reserved.
//

import UIKit

class RoutingController : UIViewController {
    var root: UIViewController = SplashViewController()
    
    override func viewDidLoad() {
        super.viewDidLoad()
        
        addChild(root)
        root.view.frame = view.bounds
        view.addSubview(root.view)
        root.didMove(toParent: self)
    }
    
    func showLogin() {
        replaceRoot(withViewController: SignInViewController(), animationOptions: [.transitionCrossDissolve, .curveEaseInOut], duration: 0.8)
    }
    
    private func replaceRoot(
        withViewController viewController: UIViewController,
        animationOptions: UIView.AnimationOptions = [.curveEaseInOut],
        duration: Double = 0.5
    ) {
        let nextRoot = UINavigationController(rootViewController: viewController)
        root.willMove(toParent: nil)
        addChild(nextRoot)
        view.addSubview(nextRoot.view)
        transition(from: root, to: nextRoot, duration: duration, options: animationOptions, animations: {}) { completed in
            nextRoot.didMove(toParent: self)
            self.root.removeFromParent()
            self.root = nextRoot
        }
    }
}
