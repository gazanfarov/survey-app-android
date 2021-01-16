package com.surveyapp.presentation.adapter

import android.content.res.ColorStateList
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.surveyapp.R
import com.surveyapp.databinding.RecyclerAnswerItemBinding
import com.surveyapp.entity.AnswerEntity

class AnswersAdapter(
    private val onAnswerChosen: (answerId: Long) -> Unit
) : RecyclerView.Adapter<AnswersAdapter.AnswerViewHolder>() {

    private val answers = mutableListOf<AnswerEntity>()
    private var chosenAnswerId: Long? = null

    fun setItems(
        items: List<AnswerEntity>,
        chosenItemId: Long?
    ) {
        chosenAnswerId = chosenItemId
        answers.clear()
        answers.addAll(items)
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int = answers.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AnswerViewHolder {
        return AnswerViewHolder(
            RecyclerAnswerItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: AnswerViewHolder, position: Int) {
        holder.bindAnswer(
            answer = answers[position],
            onAnswerChecked = { id ->
                onAnswerChecked(id)
            }
        )
    }

    private fun onAnswerChecked(answerId: Long) {
        val oldChosenAnswerPosition = chosenAnswerId?.let { id ->
            answers.find { answer -> answer.id == id }
        }
        val newChosenAnswerPosition = answers.first { answer ->
            answer.id == answerId
        }
        chosenAnswerId = answerId
        oldChosenAnswerPosition?.let { answer ->
            notifyItemChanged(answers.indexOf(answer))
        }
        notifyItemChanged(answers.indexOf(newChosenAnswerPosition))
        onAnswerChosen(answerId)
    }

    inner class AnswerViewHolder(
        private val binding: RecyclerAnswerItemBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bindAnswer(
            answer: AnswerEntity,
            onAnswerChecked: (answerId: Long) -> Unit
        ) {
            binding.rbAnswer.text = answer.value
            binding.rbAnswer.setOnClickListener {
                onAnswerChecked(answer.id)
            }
            binding.rbAnswer.isChecked = answer.id == chosenAnswerId
            setUpRadioBtnStateColors()
        }

        private fun setUpRadioBtnStateColors() {
            val colorStateList = ColorStateList(
                arrayOf(
                    intArrayOf(
                        -android.R.attr.state_checked
                    ),
                    intArrayOf(
                        android.R.attr.state_checked
                    )
                ),
                intArrayOf(
                    ContextCompat.getColor(itemView.context, R.color.black),
                    ContextCompat.getColor(itemView.context, R.color.secondary)
                )
            )
            binding.rbAnswer.buttonTintList = colorStateList
            binding.rbAnswer.invalidate()

        }
    }
}