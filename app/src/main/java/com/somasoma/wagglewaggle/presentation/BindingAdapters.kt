package com.somasoma.wagglewaggle.presentation

import android.view.View
import android.view.ViewGroup
import androidx.databinding.BindingAdapter

@BindingAdapter("android:layout_height")
fun setLayoutHeight(view: View, height: Int) {
    val layoutParams = view.layoutParams
    layoutParams.height = height
    view.layoutParams = layoutParams
}

@BindingAdapter("android:layout_width")
fun setLayoutWidth(view: View, height: Int) {
    val layoutParams = view.layoutParams
    layoutParams.width = height
    view.layoutParams = layoutParams
}

@BindingAdapter("android:layout_marginTop")
fun setMarginTop(view: View, margin: Int) {
    val layoutParams = view.layoutParams as ViewGroup.MarginLayoutParams
    layoutParams.topMargin = margin
    view.layoutParams = layoutParams
}