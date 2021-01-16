package com.surveyapp.presentation.intro

import android.os.Bundle
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.annotation.LayoutRes
import androidx.core.content.ContextCompat
import androidx.navigation.fragment.navArgs
import by.kirich1409.viewbindingdelegate.viewBinding
import com.google.android.material.snackbar.Snackbar
import com.surveyapp.R
import com.surveyapp.base.BaseMvpFragment
import com.surveyapp.base.BaseMvpView
import com.surveyapp.databinding.FragmentIntroBinding
import com.surveyapp.domain.interactor.SurveyInteractor
import moxy.MvpAppCompatFragment
import moxy.ktx.moxyPresenter
import org.koin.android.ext.android.inject

class IntroFragment : BaseMvpFragment(R.layout.fragment_intro), IntroView {

    override val fragmentId: Int
        get() = R.id.intro
    private val binding: FragmentIntroBinding by viewBinding(FragmentIntroBinding::bind)
    private val args: IntroFragmentArgs by navArgs()
    private val surveyInteractor: SurveyInteractor by inject()
    private val backPressedCallback = object : OnBackPressedCallback(true) {
        override fun handleOnBackPressed() {
            presenter.backButtonPressed()
        }
    }
    private val presenter: IntroPresenter by moxyPresenter {
        IntroPresenter(
            hoursSavedAnswerId = args.hoursAnswerId,
            paymentSavedAnswerId = args.paymentAnswerId,
            surveyInteractor = surveyInteractor
        )
    }

    override fun setUpViews() {
        binding.btnStartSurvey.setOnClickListener { presenter.startSurveyBtnClicked() }
        setUpBackButtonPressedListener()
    }

    private fun setUpBackButtonPressedListener() {
        requireActivity().onBackPressedDispatcher.addCallback(
            viewLifecycleOwner,
            backPressedCallback
        )
    }

    override fun openHoursQuestion(questionId: Long) {
        navigate(IntroFragmentDirections.introToHoursQuestion(questionId))
    }

    override fun returnBackToSurveyQuestions() {
        backPressedCallback.isEnabled = false
        activity?.onBackPressed()
    }

    override fun closeSurveyFlow() {
        activity?.finish()
    }

    override fun showSavedSurveySnackBar() {
        Snackbar.make(
            binding.root,
            resources.getString(R.string.surveySaved),
            Snackbar.LENGTH_LONG
        ).apply {
            setAction(R.string.surveyUndo) {
                presenter.removedSavedQuestionsBtnClicked()
            }
            setActionTextColor(
                ContextCompat.getColor(requireContext(), R.color.secondary)
            )
            show()
        }
    }
}