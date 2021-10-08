package com.somasoma.wagglewaggle.presentation.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.somasoma.wagglewaggle.R
import com.somasoma.wagglewaggle.data.Avatar
import com.somasoma.wagglewaggle.databinding.AvatarSelectPagerItemBinding

class AvatarSelectPagerAdapter :
    ListAdapter<Avatar, AvatarSelectPagerAdapter.ViewHolder>(AvatarsDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class ViewHolder private constructor(val binding: AvatarSelectPagerItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = AvatarSelectPagerItemBinding.inflate(layoutInflater, parent, false)
                return ViewHolder(binding)
            }
        }

        fun bind(avatar: Avatar) {
            when (avatar) {
                Avatar.FEMALE1 -> {
                    Glide.with(binding.root)
                        .load(R.drawable.female1_wave)
                        .centerInside()
                        .into(binding.imgAvatar)
                }
                Avatar.FEMALE2 -> {
                    Glide.with(binding.root)
                        .load(R.drawable.female2_wave)
                        .centerInside()
                        .into(binding.imgAvatar)
                }
                Avatar.FEMALE3 -> {
                    Glide.with(binding.root)
                        .load(R.drawable.female3_wave)
                        .centerInside()
                        .into(binding.imgAvatar)
                }
                Avatar.FEMALE4 -> {
                    Glide.with(binding.root)
                        .load(R.drawable.female4_wave)
                        .centerInside()
                        .into(binding.imgAvatar)
                }
                Avatar.MALE1 -> {
                    Glide.with(binding.root)
                        .load(R.drawable.male1_wave)
                        .centerInside()
                        .into(binding.imgAvatar)
                }
                Avatar.MALE2 -> {
                    Glide.with(binding.root)
                        .load(R.drawable.male2_wave)
                        .centerInside()
                        .into(binding.imgAvatar)
                }
                Avatar.MALE3 -> {
                    Glide.with(binding.root)
                        .load(R.drawable.male3_wave)
                        .centerInside()
                        .into(binding.imgAvatar)
                }
                Avatar.MALE4 -> {
                    Glide.with(binding.root)
                        .load(R.drawable.male4_wave)
                        .centerInside()
                        .into(binding.imgAvatar)
                }
            }
        }
    }
}

class AvatarsDiffCallback : DiffUtil.ItemCallback<Avatar>() {
    override fun areItemsTheSame(oldItem: Avatar, newItem: Avatar): Boolean {
        return oldItem === newItem
    }

    override fun areContentsTheSame(oldItem: Avatar, newItem: Avatar): Boolean {
        return oldItem == newItem
    }
}