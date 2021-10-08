package com.somasoma.wagglewaggle.presentation.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.somasoma.wagglewaggle.databinding.KeywordListItemInWorldListItemBinding
import com.somasoma.wagglewaggle.presentation.custom_views.StringDiffCallback

class KeywordListInWorldListItemAdapter :
    ListAdapter<String, KeywordListInWorldListItemAdapter.ViewHolder>(StringDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class ViewHolder private constructor(val binding: KeywordListItemInWorldListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = KeywordListItemInWorldListItemBinding.inflate(layoutInflater, parent, false)
                return ViewHolder(binding)
            }
        }

        fun bind(keyword: String) {
            binding.txtItem.text = keyword
        }
    }
}