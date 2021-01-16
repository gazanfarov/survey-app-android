package com.surveyapp.base

import android.app.Activity
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.annotation.IdRes
import androidx.annotation.LayoutRes
import androidx.navigation.NavDirections
import androidx.navigation.fragment.findNavController
import moxy.MvpAppCompatFragment
import timber.log.Timber

abstract class BaseMvpFragment(
    @LayoutRes contentLayoutId: Int
) : MvpAppCompatFragment(contentLayoutId) {

    abstract val fragmentId: Int

    abstract fun setUpViews()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpViews()
    }

    protected fun navigate(@IdRes destinationId: Int) {
        try {
            findNavController().navigate(destinationId)
        } catch (e: IllegalArgumentException) {
            Timber.e(e, "Error while trying preform navigation action: $e")
        }
    }

    protected fun navigate(direction: NavDirections) {
        try {
            findNavController().navigate(direction)
        } catch (e: IllegalArgumentException) {
            Timber.e(e, "Error while trying preform navigation action: $e")
        }
    }

    fun showKeyboard() {
        if (isAdded) {
            (requireContext().getSystemService(Activity.INPUT_METHOD_SERVICE) as? InputMethodManager?)
                ?.toggleSoftInput(
                    InputMethodManager.SHOW_FORCED,
                    InputMethodManager.HIDE_IMPLICIT_ONLY
                )
        }
    }

    fun hideKeyboard() {
        if (isAdded) {
            (requireContext().getSystemService(Activity.INPUT_METHOD_SERVICE) as? InputMethodManager?)
                ?.hideSoftInputFromWindow(view?.windowToken, 0)
        }
    }
}