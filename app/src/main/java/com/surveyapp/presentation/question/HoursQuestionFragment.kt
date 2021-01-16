package com.surveyapp.presentation.question

import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import com.surveyapp.R
import com.surveyapp.base.BaseMvpFragment
import com.surveyapp.databinding.FragmentHoursQuestionBinding
import com.surveyapp.domain.interactor.SurveyInteractor
import com.surveyapp.entity.AnswerEntity
import com.surveyapp.presentation.adapter.AnswersAdapter
import moxy.ktx.moxyPresenter
import org.koin.android.ext.android.inject

class HoursQuestionFragment : BaseMvpFragment(R.layout.fragment_hours_question), HoursQuestionView {

    private lateinit var adapter: AnswersAdapter
    override val fragmentId: Int
        get() = R.id.hoursQuestion
    private val args: HoursQuestionFragmentArgs by navArgs()
    private val surveyInteractor: SurveyInteractor by inject()
    private val binding: FragmentHoursQuestionBinding by viewBinding(FragmentHoursQuestionBinding::bind)
    private val presenter: HoursQuestionPresenter by moxyPresenter {
        HoursQuestionPresenter(
            questionId = args.questionId,
            surveyInteractor = surveyInteractor
        )
    }

    override fun setUpViews() {
        setUpRecyclerView()
    }

    private fun setUpRecyclerView() {
        adapter = AnswersAdapter { answerId ->
            presenter.onAnswerChosen(answerId)
        }
        binding.rvAnswers.layoutManager = LinearLayoutManager(requireContext())
        binding.rvAnswers.setHasFixedSize(true)
        binding.rvAnswers.addItemDecoration(
            AnswerItemDecoration(
                marginTop = resources.getDimensionPixelSize(R.dimen.baseMarginBig)
            )
        )
        binding.rvAnswers.adapter = adapter
    }

    override fun showQuestion(question: String) {
        binding.tvTitle.text = question
    }

    override fun setAnswerItems(
        answers: List<AnswerEntity>,
        chosenAnswerId: Long?
    ) {
        adapter.setItems(
            items = answers,
            chosenItemId = chosenAnswerId
        )
        binding.rvAnswers.invalidateItemDecorations()
    }

    override fun openPaymentQuestion(questionId: Long, chosenAnswerId: Long) {
        navigate(
            HoursQuestionFragmentDirections.hoursQuestionToPaymentQuestion(
                questionId = questionId,
                hoursAnswerId = chosenAnswerId
            )
        )
    }
}