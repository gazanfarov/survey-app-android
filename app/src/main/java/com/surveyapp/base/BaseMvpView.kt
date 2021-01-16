package com.surveyapp.base

import moxy.MvpView
import moxy.viewstate.strategy.alias.Skip

interface BaseMvpView : MvpView {

    @Skip
    fun showKeyboard()

    @Skip
    fun hideKeyboard()
}