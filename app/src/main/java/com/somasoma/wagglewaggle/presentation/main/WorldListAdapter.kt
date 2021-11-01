package com.somasoma.wagglewaggle.presentation.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.somasoma.wagglewaggle.R
import com.somasoma.wagglewaggle.core.string2WorldMap
import com.somasoma.wagglewaggle.data.model.dto.world.WorldRoom
import com.somasoma.wagglewaggle.databinding.WorldListItemBinding

class WorldListAdapter(private val worldListItemEnterButtonClickListener: WorldListItemEnterButtonClickListener) :
    ListAdapter<WorldRoom, WorldListAdapter.ViewHolder>(WorldRoomDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position), worldListItemEnterButtonClickListener)
    }

    class ViewHolder private constructor(
        val binding: WorldListItemBinding,
        private val adapter: KeywordListInWorldListItemAdapter
    ) :
        RecyclerView.ViewHolder(binding.root) {
        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = WorldListItemBinding.inflate(layoutInflater, parent, false)
                val adapter = KeywordListInWorldListItemAdapter()
                binding.listKeyword.adapter = adapter
                return ViewHolder(binding, adapter)
            }

            private const val MAX_USER_COUNT = 20
        }

        fun bind(worldRoom: WorldRoom, worldListItemEnterButtonClickListener: WorldListItemEnterButtonClickListener) {
            binding.worldListItemEnterButtonClickListener = worldListItemEnterButtonClickListener
            binding.worldRoom = worldRoom
            binding.worldMap = string2WorldMap(worldRoom.map).text
            binding.maxUserCount = MAX_USER_COUNT
            adapter.submitList(worldRoom.keywords)
            Glide.with(binding.root)
                .load(R.drawable.map_jongmyo)
                .centerCrop()
                .into(binding.imgMap)
            binding.root.clipToOutline = true
        }
    }
}

class WorldRoomDiffCallback : DiffUtil.ItemCallback<WorldRoom>() {
    override fun areItemsTheSame(oldItem: WorldRoom, newItem: WorldRoom): Boolean {
        return oldItem === newItem
    }

    override fun areContentsTheSame(oldItem: WorldRoom, newItem: WorldRoom): Boolean {
        return oldItem == newItem
    }
}

interface WorldListItemEnterButtonClickListener {
    fun onClick(worldRoom: WorldRoom)
}