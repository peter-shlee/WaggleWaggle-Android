package com.somasoma.speakworld.domain.custom_views

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import androidx.databinding.BindingAdapter
import androidx.databinding.DataBindingUtil
import com.somasoma.speakworld.R
import com.somasoma.speakworld.databinding.InterestItemViewBinding

class InterestItemView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {
    companion object {
        @JvmStatic
        @BindingAdapter("app:backgroundType")
        fun setBackgroundType(view: InterestItemView, backgroundType: BackgroundType) {
            view.backgroundType = backgroundType
        }

        @JvmStatic
        @BindingAdapter("android:text")
        fun setText(view: InterestItemView, text: String) {
            view.text = text
        }
    }

    private var binding: InterestItemViewBinding = DataBindingUtil.inflate(
        LayoutInflater.from(context),
        R.layout.interest_item_view,
        this,
        true
    )

    var backgroundType: BackgroundType = BackgroundType.GRAY
        set(value) {
            field = value
            binding.backgroundType = value
        }

    var text: String = ""
        set(value) {
            field = value
            binding.txtItem.text = value
        }

    enum class BackgroundType {
        FILLED_YELLOW,
        YELLOW,
        FILLED_GRAY,
        GRAY
    }
}