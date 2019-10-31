package com.noheltcj.starter.adapter

import com.noheltcj.rxcommon.observables.Single
import com.noheltcj.rxcommon.subjects.Subject

/** Not only should this be implemented, but also its data should be supplied on a per platform basis. */
interface ApplicationStateAdapter {
    var isInBackground: Boolean
    val isInBackgroundSubject: Subject<Boolean>

    val applicationReady: Single<Unit>
}