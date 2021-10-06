package com.somasoma.wagglewaggle.presentation.custom_views

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.somasoma.wagglewaggle.databinding.InterestItemViewBinding

class SelectedInterestListAdapter :
    ListAdapter<String, SelectedInterestListAdapter.ViewHolder>(StringDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
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

        fun bind(interestKeyword: String) {
            binding.text = interestKeyword
            binding.backgroundType = InterestItemView.BackgroundType.FILLED_YELLOW

            binding.executePendingBindings()
        }
    }
}
