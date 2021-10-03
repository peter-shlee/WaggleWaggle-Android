package com.somasoma.wagglewaggle.core

import android.content.Context
import android.util.TypedValue

fun dp2Px(context: Context, dp: Float) =
    TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, context.resources.displayMetrics)
        .toInt()
