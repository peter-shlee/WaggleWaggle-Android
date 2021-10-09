package com.somasoma.wagglewaggle.presentation.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.somasoma.wagglewaggle.data.Avatar
import com.somasoma.wagglewaggle.data.model.dto.member.Member
import com.somasoma.wagglewaggle.databinding.OnlineUserListItemBinding
import com.somasoma.wagglewaggle.presentation.custom_views.ProfileImageBackgroundColor

class OnlineUserListAdapter :
    ListAdapter<Member, OnlineUserListAdapter.ViewHolder>(MemberDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class ViewHolder private constructor(val binding: OnlineUserListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = OnlineUserListItemBinding.inflate(layoutInflater, parent, false)
                return ViewHolder(binding)
            }
        }

        fun bind(member: Member) {
            binding.member = member
            binding.avatar = stringToAvatar(member.avatar)
            binding.profileImageBackgroundColor = getBackgroundColor(member.id ?: 0)
        }

        private fun stringToAvatar(avatarName: String?) = when (avatarName) {
            "female1" -> Avatar.FEMALE1
            "female2" -> Avatar.FEMALE2
            "female3" -> Avatar.FEMALE3
            "female4" -> Avatar.FEMALE4
            "male1" -> Avatar.MALE1
            "male2" -> Avatar.MALE2
            "male3" -> Avatar.MALE3
            "male4" -> Avatar.MALE4
            else -> Avatar.MALE1
        }

        private fun getBackgroundColor(memberId: Int): ProfileImageBackgroundColor =
            ProfileImageBackgroundColor.values()[memberId.mod(ProfileImageBackgroundColor.values().size)]
    }
}

class MemberDiffCallback : DiffUtil.ItemCallback<Member>() {
    override fun areItemsTheSame(oldItem: Member, newItem: Member): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: Member, newItem: Member): Boolean {
        return oldItem == newItem
    }
}
