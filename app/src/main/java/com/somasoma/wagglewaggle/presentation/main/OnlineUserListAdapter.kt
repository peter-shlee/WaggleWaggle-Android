package com.somasoma.wagglewaggle.presentation.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.somasoma.wagglewaggle.presentation.getProfileBackgroundColor
import com.somasoma.wagglewaggle.presentation.string2Avatar
import com.somasoma.wagglewaggle.data.model.dto.member.Member
import com.somasoma.wagglewaggle.databinding.OnlineUserListItemBinding

class OnlineUserListAdapter(private val memberClickListener: MemberClickListener) :
    ListAdapter<Member, OnlineUserListAdapter.ViewHolder>(MemberDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position), memberClickListener)
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

        fun bind(member: Member, memberClickListener: MemberClickListener) {
            binding.member = member
            binding.avatar = string2Avatar(member.avatar)
            binding.profileImageBackgroundColor = getProfileBackgroundColor(member.id ?: 0)
            binding.onClickListener = memberClickListener
        }
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

interface MemberClickListener {
    fun onClick(member: Member)
}
