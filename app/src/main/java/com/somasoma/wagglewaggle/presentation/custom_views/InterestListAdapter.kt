package com.somasoma.wagglewaggle.presentation.custom_views

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.somasoma.wagglewaggle.databinding.InterestItemViewBinding

class InterestListAdapter(
    private val onItemClick: (text: String, selected: Boolean) -> Unit,
    private val viewModel: SelectInterestsDialogViewModel
) :
    ListAdapter<String, InterestListAdapter.ViewHolder>(StringDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item, viewModel, onItemClick)
    }

    class ViewHolder private constructor(val binding: InterestItemViewBinding) :
        RecyclerView.ViewHolder(binding.root) {
        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = InterestItemViewBinding.inflate(layoutInflater, parent, false)
                return ViewHolder(binding)
            }
        }

        fun bind(
            interestKeyword: String,
            viewModel: SelectInterestsDialogViewModel,
            onItemClick: (text: String, selected: Boolean) -> Unit
        ) {
            val isSelected = interestKeyword in viewModel.getSelectedInterests()

            fun changeItemViewBackground(isSelected: Boolean) {
                binding.backgroundType = if (isSelected) {
                    InterestItemView.BackgroundType.YELLOW
                } else {
                    InterestItemView.BackgroundType.FILLED_YELLOW
                }
            }

            binding.text = interestKeyword

            binding.backgroundType = if (isSelected) {
                InterestItemView.BackgroundType.FILLED_YELLOW
            } else {
                InterestItemView.BackgroundType.YELLOW
            }

            binding.clickListener = InterestItemClickListener {
                val currentSelectedState =
                    binding.backgroundType == InterestItemView.BackgroundType.FILLED_YELLOW
                if (currentSelectedState) {
                    viewModel.removeSelectedInterests(interestKeyword)
                } else {
                    viewModel.addSelectedInterests(interestKeyword)
                }
                changeItemViewBackground(currentSelectedState)
                onItemClick(it, currentSelectedState)
            }

            binding.executePendingBindings()
        }
    }
}

class StringDiffCallback : DiffUtil.ItemCallback<String>() {
    override fun areItemsTheSame(oldItem: String, newItem: String): Boolean {
        return oldItem === newItem
    }

    override fun areContentsTheSame(oldItem: String, newItem: String): Boolean {
        return oldItem == newItem
    }
}

class InterestItemClickListener(val clickListener: (text: String) -> Unit) {
    fun onClick(text: String) = clickListener(text)
}