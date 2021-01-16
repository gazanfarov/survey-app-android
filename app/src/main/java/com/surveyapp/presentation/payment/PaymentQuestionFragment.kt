package com.surveyapp.presentation.payment

import android.view.KeyEvent
import androidx.navigation.fragment.navArgs
import by.kirich1409.viewbindingdelegate.viewBinding
import com.redmadrobot.inputmask.MaskedTextChangedListener
import com.surveyapp.R
import com.surveyapp.base.BaseMvpFragment
import com.surveyapp.databinding.FragmentPaymentQuestionBinding
import com.surveyapp.domain.interactor.SurveyInteractor
import moxy.ktx.moxyPresenter
import org.koin.android.ext.android.inject

class PaymentQuestionFragment : BaseMvpFragment(R.layout.fragment_payment_question),
    PaymentQuestionView {

    override val fragmentId: Int
        get() = R.id.paymentQuestion
    private val binding: FragmentPaymentQuestionBinding by viewBinding(
        FragmentPaymentQuestionBinding::bind
    )
    private val args: PaymentQuestionFragmentArgs by navArgs()
    private val surveyInteractor: SurveyInteractor by inject()
    private val presenter: PaymentQuestionPresenter by moxyPresenter {
        PaymentQuestionPresenter(
            questionId = args.questionId,
            hoursAnswerId = args.hoursAnswerId,
            surveyInteractor = surveyInteractor
        )
    }

    override fun setUpViews() {
        setUpEditText()
        binding.btnSubmitSurvey.setOnClickListener {
            presenter.submitBtnClicked()
        }
    }

    private fun setUpEditText() {
        val currencyListener = MaskedTextChangedListener.installOn(
            binding.etAmount,
            "[0]{,}[00]",
            object : MaskedTextChangedListener.ValueListener {
                override fun onTextChanged(
                    maskFilled: Boolean,
                    extractedValue: String,
                    formattedValue: String
                ) {
                    presenter.paymentEditTextFilled(
                        maskFilled = maskFilled,
                        value = formattedValue
                    )
                }
            }
        )
        binding.etAmount.hint = currencyListener.placeholder()
        binding.etAmount.setOnKeyListener { _, keyCode, event ->
            if (event.action == KeyEvent.ACTION_DOWN) {
                when (keyCode) {
                    KeyEvent.KEYCODE_DPAD_CENTER, KeyEvent.KEYCODE_ENTER -> {
                        presenter.submitBtnClicked()
                        return@setOnKeyListener true
                    }
                    else -> {
                    }
                }
            }
            false
        }
    }

    override fun showQuestion(question: String) {
        binding.tvTitle.text = question
    }

    override fun setSubmitBtnAlpha(alpha: Float) {
        binding.btnSubmitSurvey.alpha = alpha
    }

    override fun openIntro(hoursAnswerId: Long, paymentAnswerId: Long) {
        navigate(
            PaymentQuestionFragmentDirections.paymentQuestionToIntro(
                hoursAnswerId = hoursAnswerId,
                paymentAnswerId = paymentAnswerId
            )
        )
    }
}