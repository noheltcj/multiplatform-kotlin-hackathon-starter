//
//  SignInViewController.swift
//  Starter
//
//  Created by Colton Nohelty on 1/29/19.
//  Copyright © 2019 Colton Nohelty. All rights reserved.
//

import UIKit
import StarterCore
import MaterialComponents

class SignInViewController: UIViewController {
    private lazy var viewModel = <~LoginViewModel.self
    
    private lazy var scrollView = UIScrollView(frame: .zero)
    private lazy var emailField = MDCTextField(frame: .zero)
    private lazy var emailFieldController = MDCTextInputControllerOutlined(textInput: emailField)
    private lazy var passwordField = MDCTextField(frame: .zero)
    private lazy var passwordFieldController = MDCTextInputControllerOutlined(textInput: passwordField)
    private lazy var signInButton = Theme.makeContainedButton(ofType: .roundedRect)
    private lazy var signUpButton = Theme.makeContainedButton(ofType: .roundedRect)
    private lazy var forgotPasswordButton = Theme.makeOutlinedButton(ofType: .system)

    private lazy var progressIndicator = MDCActivityIndicator(frame: .zero)

    private var disposeBag: CompositeDisposeBag?
    
    private let totalSpaceHeldByConstraints: CGFloat = 112 // Manually calculated
    private var keyboardOffset: CGFloat = 0.0
    private var subviewsHeight: CGFloat = 400

    override func loadView() {
        super.loadView()
        
        NotificationCenter.default.addObserver(self,
                                               selector: #selector(keyboardWillShow),
                                               name: NSNotification.Name.MDCKeyboardWatcherKeyboardWillShow,
                                               object: nil)
        NotificationCenter.default.addObserver(self,
                                               selector: #selector(keyboardWillHide),
                                               name: NSNotification.Name.MDCKeyboardWatcherKeyboardWillHide,
                                               object: nil)
        
        view.backgroundColor = UIColor.white
        view.addSubview(scrollView)

        disposeBag = CompositeDisposeBag()
        
        setupSubviews()
        bindViews()
    }
    
    override func viewDidLoad() {
        super.viewDidLoad()
        
        scrollView.delegate = self
        
        viewModel.onInit()
    }
    
    override func viewDidLayoutSubviews() {
        super.viewDidLayoutSubviews()

        updateScrollViewContentSize()
    }
    
    override func viewDidAppear(_ animated: Bool) {
        super.viewDidAppear(animated)

        observeNavigationEvents()
    }
    
    private func setupSubviews() {
        let margins = view.layoutMarginsGuide
        
        scrollView.translatesAutoresizingMaskIntoConstraints = false
        emailField.translatesAutoresizingMaskIntoConstraints = false
        passwordField.translatesAutoresizingMaskIntoConstraints = false
        forgotPasswordButton.translatesAutoresizingMaskIntoConstraints = false
        signInButton.translatesAutoresizingMaskIntoConstraints = false
        signUpButton.translatesAutoresizingMaskIntoConstraints = false
        progressIndicator.translatesAutoresizingMaskIntoConstraints = false
        
        view.addSubview(scrollView)
        NSLayoutConstraint.activate([
            scrollView.topAnchor.constraint(equalTo: view.topAnchor),
            scrollView.bottomAnchor.constraint(equalTo: view.bottomAnchor),
            scrollView.leftAnchor.constraint(equalTo: view.leftAnchor),
            scrollView.rightAnchor.constraint(equalTo: view.rightAnchor)
        ])
        
        scrollView.addSubview(emailField)
        MDCOutlinedTextFieldColorThemer.applySemanticColorScheme(Theme.colorScheme, to: emailFieldController)
        emailField.placeholder = "email"
        emailField.autocapitalizationType = UITextAutocapitalizationType.none
        emailField.autocorrectionType = UITextAutocorrectionType.no
        emailField.keyboardType = UIKeyboardType.emailAddress
        NSLayoutConstraint.activate([
            emailField.leadingAnchor.constraint(equalTo: margins.leadingAnchor),
            emailField.trailingAnchor.constraint(equalTo: margins.trailingAnchor),
            emailField.heightAnchor.constraint(equalToConstant: 82),
            emailField.topAnchor.constraint(equalTo: scrollView.topAnchor, constant: 50)
        ])
        
        scrollView.addSubview(passwordField)
        MDCOutlinedTextFieldColorThemer.applySemanticColorScheme(Theme.colorScheme, to: passwordFieldController)
        passwordField.placeholder = "password"
        passwordField.isSecureTextEntry = true
        passwordField.autocapitalizationType = UITextAutocapitalizationType.none
        passwordField.autocorrectionType = UITextAutocorrectionType.no
        passwordField.keyboardType = UIKeyboardType.default
        NSLayoutConstraint.activate([
            passwordField.leadingAnchor.constraint(equalTo: margins.leadingAnchor),
            passwordField.trailingAnchor.constraint(equalTo: margins.trailingAnchor),
            passwordField.heightAnchor.constraint(equalToConstant: 84),
            passwordField.topAnchor.constraint(equalTo: emailField.bottomAnchor, constant: 2)
        ])
        
        scrollView.addSubview(signInButton)
        signInButton.setTitle("Sign In", for: .normal)
        NSLayoutConstraint.activate([
            signInButton.heightAnchor.constraint(equalToConstant: 50),
            signInButton.leadingAnchor.constraint(equalTo: margins.leadingAnchor),
            signInButton.trailingAnchor.constraint(equalTo: margins.trailingAnchor),
            signInButton.topAnchor.constraint(equalTo: passwordField.bottomAnchor, constant: 10)
        ])
        
        scrollView.addSubview(signUpButton)
        signUpButton.setTitle("Sign Up", for: .normal)
        NSLayoutConstraint.activate([
            signUpButton.heightAnchor.constraint(equalToConstant: 50),
            signUpButton.leadingAnchor.constraint(equalTo: margins.leadingAnchor),
            signUpButton.trailingAnchor.constraint(equalTo: margins.trailingAnchor),
            signUpButton.topAnchor.constraint(equalTo: signInButton.bottomAnchor, constant: 20)
        ])
        
        scrollView.addSubview(forgotPasswordButton)
        forgotPasswordButton.setTitle("Forgot Password", for: .normal)
        NSLayoutConstraint.activate([
            forgotPasswordButton.heightAnchor.constraint(equalToConstant: 50),
            forgotPasswordButton.leadingAnchor.constraint(equalTo: margins.leadingAnchor),
            forgotPasswordButton.trailingAnchor.constraint(equalTo: margins.trailingAnchor),
            forgotPasswordButton.topAnchor.constraint(equalTo: signUpButton.bottomAnchor, constant: 30)
        ])
        
        view.addSubview(progressIndicator)
        progressIndicator.radius = 25
        progressIndicator.strokeWidth = 5
        NSLayoutConstraint.activate([
            progressIndicator.leadingAnchor.constraint(equalTo: view.leadingAnchor),
            progressIndicator.trailingAnchor.constraint(equalTo: view.trailingAnchor),
            progressIndicator.topAnchor.constraint(equalTo: view.topAnchor),
            progressIndicator.bottomAnchor.constraint(equalTo: view.bottomAnchor)
        ])
    }
    
    private func bindViews() {
        disposeBag!.add(disposable: emailField.bindBiDirectionally(toRelay: viewModel.emailField))
        disposeBag!.add(disposable: emailFieldController.bindErrorToSource(source: viewModel.emailFieldError))
        disposeBag!.add(disposable: passwordField.bindBiDirectionally(toRelay: viewModel.passwordField))
        disposeBag!.add(disposable: passwordFieldController.bindErrorToSource(source: viewModel.passwordFieldError))
        signInButton.addTarget(self, action: #selector(didTapSignIn), for: .touchUpInside)
        disposeBag!.add(disposable: signInButton.bindIsEnabled(toSource: viewModel.submitEnabled))
        signUpButton.addTarget(self, action: #selector(didTapSignUp), for: .touchUpInside)
        disposeBag!.add(disposable: signUpButton.bindIsEnabled(toSource: viewModel.submitEnabled))
        forgotPasswordButton.addTarget(self, action: #selector(didTapForgotPassword), for: .touchUpInside)
        disposeBag!.add(disposable: progressIndicator.bind(toSource: viewModel.showLoader))
    }
    
    fileprivate func updateScrollViewContentSize() {
        let marginOffset = view.layoutMargins.top + view.layoutMargins.bottom
        scrollView.contentSize = CGSize(
            width: view.bounds.width,
            height: subviewsHeight + marginOffset + keyboardOffset
        )
        print(scrollView.contentSize)
    }
    
    deinit {
        viewModel.onCleanup()
    }
}

extension SignInViewController {
    @objc func didTapSignUp() {
        viewModel.onSignUpTapped()
    }
    
    @objc func didTapSignIn() {
        viewModel.onLoginTapped()
    }
    
    @objc func didTapForgotPassword() {
        viewModel.onForgotPasswordTapped()
    }
}

extension SignInViewController {
    private func observeNavigationEvents() {
        disposeBag!.add(disposable: viewModel.navigationEvents.subscribe(observer: NextObserver { event in
            switch(event!) {
            case is EXLoginViewModelNavigationEventShowHome: self.showHome()
            case is EXLoginViewModelNavigationEventShowForgotPassword: self.showForgotPassword()
            case is EXLoginViewModelNavigationEventShowErrorAlert: self.showErrorAlert(event as! EXLoginViewModelNavigationEventShowErrorAlert)
            default: break
            }
            return KotlinUnit()
        }))
    }
    
    private func showErrorAlert(_ event: EXLoginViewModelNavigationEventShowErrorAlert) {
        let alertController = MDCAlertController(title: event.title, message: event.message)
        MDCAlertControllerThemer.applyScheme(Theme.alertScheme, to: alertController)
        alertController.addAction(MDCAlertAction(title:"OK"))
        
        present(alertController, animated: true, completion: nil)
    }
    
    private func showHome() {
        let alertController = MDCAlertController(title: "Success!", message: "Normally, you'd be seeing something grand by now, but this is just an example app.")
        MDCAlertControllerThemer.applyScheme(Theme.alertScheme, to: alertController)
        alertController.addAction(MDCAlertAction(title:"OK"))
        
        present(alertController, animated: true, completion: nil)
    }
    
    private func showForgotPassword() {
        let alertController = MDCAlertController(title: "Oh...", message: "Hope you wrote it down. Get to diggin!")
        MDCAlertControllerThemer.applyScheme(Theme.alertScheme, to: alertController)
        alertController.addAction(MDCAlertAction(title:"OK"))
        
        present(alertController, animated: true, completion: nil)
    }
}

extension SignInViewController : UIScrollViewDelegate {}

extension SignInViewController : UITextViewDelegate {
    @objc func keyboardWillShow(notification: NSNotification){
        let notificationValue = notification.userInfo![UIResponder.keyboardFrameBeginUserInfoKey] as! NSValue
        keyboardOffset = notificationValue.cgRectValue.height
        updateScrollViewContentSize()
    }
    
    @objc func keyboardWillHide(notification: NSNotification) {
        keyboardOffset = 0
    }
}
