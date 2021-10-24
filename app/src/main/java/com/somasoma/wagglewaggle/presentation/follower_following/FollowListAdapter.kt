package com.somasoma.wagglewaggle.presentation.follower_following

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.somasoma.wagglewaggle.core.getProfileBackgroundColor
import com.somasoma.wagglewaggle.core.string2Avatar
import com.somasoma.wagglewaggle.data.model.dto.member.Member
import com.somasoma.wagglewaggle.databinding.FollowUserListItemBinding
import com.somasoma.wagglewaggle.presentation.main.MemberClickListener
import com.somasoma.wagglewaggle.presentation.main.MemberDiffCallback

class FollowListAdapter(private val memberClickListener: MemberClickListener): ListAdapter<Member, FollowListAdapter.ViewHolder>(MemberDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position), memberClickListener)
    }

    class ViewHolder private constructor(val binding: FollowUserListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = FollowUserListItemBinding.inflate(layoutInflater, parent, false)
                return ViewHolder(binding)
            }
        }

        fun bind(member: Member, memberClickListener: MemberClickListener) {
            binding.member = member
            binding.avatar = string2Avatar(member.avatar)
            binding.profileImageBackgroundColor = getProfileBackgroundColor(member.id ?: 0)
            binding.onClickListener = memberClickListener
            member.entranceRoom?.let {

            }
        }
    }
}