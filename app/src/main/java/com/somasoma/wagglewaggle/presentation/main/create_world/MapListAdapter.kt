package com.somasoma.wagglewaggle.presentation.main.create_world

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.somasoma.wagglewaggle.R
import com.somasoma.wagglewaggle.data.WorldMap
import com.somasoma.wagglewaggle.databinding.MapListItemBinding

class MapListAdapter : ListAdapter<WorldMap, MapListAdapter.ViewHolder>(WorldMapDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class ViewHolder private constructor(val binding: MapListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = MapListItemBinding.inflate(layoutInflater, parent, false)
                return ViewHolder(binding)
            }
        }

        fun bind(map: WorldMap) {
            binding.frame.clipToOutline = true
            binding.isSelected = true
            Glide.with(binding.root)
                .load(getMapImageResourceId(map))
                .centerCrop()
                .into(binding.imgMap)
        }

        private fun getMapImageResourceId(map: WorldMap) = when (map) {
            WorldMap.JONGMYO -> {
                R.drawable.map_jongmyo
            }
        }
    }
}

class WorldMapDiffCallback : DiffUtil.ItemCallback<WorldMap>() {
    override fun areItemsTheSame(oldItem: WorldMap, newItem: WorldMap): Boolean {
        return oldItem === newItem
    }

    override fun areContentsTheSame(oldItem: WorldMap, newItem: WorldMap): Boolean {
        return oldItem == newItem
    }
}