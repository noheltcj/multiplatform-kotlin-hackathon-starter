package com.noheltcj.starter.presentation

import com.noheltcj.rxcommon.observables.Single
import com.noheltcj.rxcommon.observers.NextObserver
import com.noheltcj.rxcommon.subjects.BehaviorRelay
import com.noheltcj.starter.adapter.ApplicationStateAdapter
import com.noheltcj.starter.di.Inject
import com.noheltcj.starter.domain.AuthInteractor

class ApplicationViewModel @Inject constructor(
    private val authInteractor: AuthInteractor,
    private val applicationStateAdapter: ApplicationStateAdapter
) {
    val navigationOverrideEvents = BehaviorRelay<NavigationOverrideEvent>(NavigationOverrideEvent.None)

    fun onInit() {
        applicationStateAdapter.applicationReady
            .switchMap { authInteractor.observeRefreshedAuthorization() }
            .subscribe(NextObserver { authorization ->
                if (authorization == null) {
                    navigationOverrideEvents.onNext(NavigationOverrideEvent.ShowLogin)
                }
            })

        applicationStateAdapter.isInBackgroundSubject
            .filter { !it }
            .flatMap {
                authInteractor.refreshAuthorizationIfNecessary()
                    .onErrorReturn { Single(Unit) }
            }
            .subscribe(NextObserver {})
    }

    sealed class NavigationOverrideEvent {
        object None : NavigationOverrideEvent()
        object ShowLogin : NavigationOverrideEvent()
    }
}