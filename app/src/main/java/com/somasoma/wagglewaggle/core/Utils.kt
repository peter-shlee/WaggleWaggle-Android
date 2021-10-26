package com.somasoma.wagglewaggle.core

import android.content.Context
import android.util.TypedValue
import com.somasoma.wagglewaggle.R
import com.somasoma.wagglewaggle.data.Avatar
import com.somasoma.wagglewaggle.data.Friendship
import com.somasoma.wagglewaggle.data.WorldMap
import com.somasoma.wagglewaggle.presentation.custom_views.ProfileImageBackgroundColor

val DEFAULT_AVATAR = Avatar.MALE1

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

fun string2Avatar(avatarString: String?) = when (avatarString) {
    "FEMALE1" -> Avatar.FEMALE1
    "FEMALE2" -> Avatar.FEMALE2
    "FEMALE3" -> Avatar.FEMALE3
    "FEMALE4" -> Avatar.FEMALE4
    "MALE1" -> Avatar.MALE1
    "MALE2" -> Avatar.MALE2
    "MALE3" -> Avatar.MALE3
    "MALE4" -> Avatar.MALE4
    "female1" -> Avatar.FEMALE1
    "female2" -> Avatar.FEMALE2
    "female3" -> Avatar.FEMALE3
    "female4" -> Avatar.FEMALE4
    "male1" -> Avatar.MALE1
    "male2" -> Avatar.MALE2
    "male3" -> Avatar.MALE3
    "male4" -> Avatar.MALE4
    else -> DEFAULT_AVATAR
}

fun avatar2String(avatar: Avatar) = when(avatar) {
    Avatar.FEMALE1 -> "FEMALE1"
    Avatar.FEMALE2 -> "FEMALE2"
    Avatar.FEMALE3 -> "FEMALE3"
    Avatar.FEMALE4 -> "FEMALE4"
    Avatar.MALE1 -> "MALE1"
    Avatar.MALE2 -> "MALE2"
    Avatar.MALE3 -> "MALE3"
    Avatar.MALE4 -> "MALE4"
}

fun getProfileBackgroundColor(memberId: Int): ProfileImageBackgroundColor =
    ProfileImageBackgroundColor.values()[memberId.mod(ProfileImageBackgroundColor.values().size)]

fun string2Friendship(friendshipString: String?) = when (friendshipString) {
    "NONE" -> Friendship.NONE
    "FOLLOW" -> Friendship.FOLLOW
    "BLOCK" -> Friendship.BLOCK
    "none" -> Friendship.NONE
    "follow" -> Friendship.FOLLOW
    "block" -> Friendship.BLOCK
    else -> Friendship.NONE
}

fun friendship2String(friendship: Friendship) = when (friendship) {
    Friendship.NONE -> "NONE"
    Friendship.BLOCK -> "BLOCK"
    Friendship.FOLLOW -> "FOLLOW"
}

fun worldMap2String(worldMap: WorldMap) = when (worldMap) {
    WorldMap.JONGMYO -> WorldMap.JONGMYO.text
    WorldMap.GWANGHWAMUN -> WorldMap.GWANGHWAMUN.text
}

fun string2WorldMap(string: String?) = when (string?.lowercase()) {
    WorldMap.JONGMYO.text.lowercase() -> WorldMap.JONGMYO
    WorldMap.GWANGHWAMUN.text.lowercase() -> WorldMap.GWANGHWAMUN
    else -> WorldMap.JONGMYO
}