package com.surveyapp.base

import kotlinx.coroutines.CoroutineScope
import moxy.MvpPresenter
import moxy.MvpView
import moxy.presenterScope
import kotlin.coroutines.CoroutineContext

abstract class BaseMvpPresenter<View : MvpView> : MvpPresenter<View>(), CoroutineScope {

    override val coroutineContext: CoroutineContext
        get() = presenterScope.coroutineContext
}