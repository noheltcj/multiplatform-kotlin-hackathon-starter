//
//  AppDelegate.swift
//  Starter
//
//  Created by Colton Nohelty on 1/29/19.
//  Copyright © 2019 Colton Nohelty. All rights reserved.
//

import UIKit
import StarterCore

@UIApplicationMain
class AppDelegate: UIResponder, UIApplicationDelegate {
    private lazy var applicationViewModel = <~ApplicationViewModel.self
    private lazy var applicationStateAdapter = <~ApplicationStateAdapter.self
    
    var window: UIWindow?

    func application(_ application: UIApplication, didFinishLaunchingWithOptions launchOptions: [UIApplication.LaunchOptionsKey: Any]?) -> Bool {        
        window = UIWindow(frame: UIScreen.main.bounds)
        window!.rootViewController = RoutingController()
        window!.makeKeyAndVisible()

        applicationViewModel.onInit()
        observeNavigationOverrideEvents()

        return true
    }

    func applicationWillResignActive(_ application: UIApplication) {
        // Sent when the application is about to move from active to inactive state. This can occur for certain types of temporary interruptions (such as an incoming phone call or SMS message) or when the user quits the application and it begins the transition to the background state.
        // Use this method to pause ongoing tasks, disable timers, and invalidate graphics rendering callbacks. Games should use this method to pause the game.
    }

    func applicationDidEnterBackground(_ application: UIApplication) {
        applicationStateAdapter.isInBackground = true
    }

    func applicationWillEnterForeground(_ application: UIApplication) {
        applicationStateAdapter.isInBackground = false
    }

    func applicationDidBecomeActive(_ application: UIApplication) {
        // Restart any tasks that were paused (or not yet started) while the application was inactive. If the application was previously in the background, optionally refresh the user interface.
    }

    func applicationWillTerminate(_ application: UIApplication) {
        // Called when the application is about to terminate. Save data if appropriate. See also applicationDidEnterBackground:.
    }
    
    private func observeNavigationOverrideEvents() {
        applicationViewModel.navigationOverrideEvents.subscribe(observer: NextObserver { event in
            switch (event) {
                case is EXApplicationViewModelNavigationOverrideEventNone: break
                case is EXApplicationViewModelNavigationOverrideEventShowLogin: self.showLogin()
                default: break
            }
            return KotlinUnit()
        })
    }

    private func showLogin() {

    }
    
    static var shared: AppDelegate {
        return UIApplication.shared.delegate as! AppDelegate
    }
}

