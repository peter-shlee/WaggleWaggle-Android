package com.somasoma.wagglewaggle.core

import android.content.Context
import android.util.TypedValue
import com.somasoma.wagglewaggle.R
import com.somasoma.wagglewaggle.data.Avatar

fun dp2Px(context: Context, dp: Float) =
    TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, context.resources.displayMetrics)
        .toInt()

fun getAvatarResourceId(avatar: Avatar) = when (avatar) {
    Avatar.FEMALE1 -> R.drawable.female1
    Avatar.FEMALE2 -> R.drawable.female2_wave
    Avatar.FEMALE3 -> R.drawable.female3
    Avatar.FEMALE4 -> R.drawable.female4_wave
    Avatar.MALE1 -> R.drawable.male1_wave
    Avatar.MALE2 -> R.drawable.male2
    Avatar.MALE3 -> R.drawable.male3_wave
    Avatar.MALE4 -> R.drawable.male4
}

fun getProfileAvatarResourceId(avatar: Avatar) = when (avatar) {
    Avatar.FEMALE1 -> R.drawable.female1_profile
    Avatar.FEMALE2 -> R.drawable.female2_wave_profile
    Avatar.FEMALE3 -> R.drawable.female3_profile
    Avatar.FEMALE4 -> R.drawable.female4_wave_profile
    Avatar.MALE1 -> R.drawable.male1_wave_profile
    Avatar.MALE2 -> R.drawable.male2_profile
    Avatar.MALE3 -> R.drawable.male3_profile
    Avatar.MALE4 -> R.drawable.male4_wave_profile
}