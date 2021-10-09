package com.somasoma.wagglewaggle.presentation.custom_views

import android.annotation.SuppressLint
import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import androidx.databinding.DataBindingUtil
import com.bumptech.glide.Glide
import com.somasoma.wagglewaggle.R
import com.somasoma.wagglewaggle.core.getProfileAvatarResourceId
import com.somasoma.wagglewaggle.data.Avatar
import com.somasoma.wagglewaggle.databinding.ProfileImageViewBinding

class ProfileImageView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {
    companion object {
        @JvmStatic
        @BindingAdapter("app:avatar")
        fun setAvatar(view: ProfileImageView, avatar: Avatar) {
            view.avatar = avatar
        }


        @JvmStatic
        @BindingAdapter("app:backgroundColor")
        fun setBackgroundTint(view: ProfileImageView, color: ProfileImageBackgroundColor) {
            view.backgroundTint = color
        }
    }

    private val binding: ProfileImageViewBinding = DataBindingUtil.inflate(
        LayoutInflater.from(context),
        R.layout.profile_image_view,
        this,
        true
    )

    init {
        binding.root.clipToOutline = true
    }

    var avatar: Avatar = Avatar.FEMALE1
        set(value) {
            field = value
            Glide.with(binding.root)
                .load(getProfileAvatarResourceId(value))
                .into(binding.imgCharacter)
        }

    var backgroundTint: ProfileImageBackgroundColor = ProfileImageBackgroundColor.GREEN
        @SuppressLint("ResourceType")
        set(color) {
            field = color
            binding.backgroundTintColor = ContextCompat.getColor(binding.root.context, color.value)
        }
}