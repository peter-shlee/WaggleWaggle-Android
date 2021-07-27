package com.somasoma.speakworld

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.somasoma.speakworld.databinding.ActivityHomeBinding
import com.somasoma.speakworld.databinding.AvatarPagerItemBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeActivity : AppCompatActivity() {
    private val viewModel: HomeViewModel by viewModels()
    lateinit var viewPagerAdapter: AvatarPagerAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel.avatars.observe(this) { onAvatarsLoaded() }
        viewModel.user.observe(this) { onUserChanged() }

        viewModel.getUserInfo()

        val binding: ActivityHomeBinding =
            DataBindingUtil.setContentView(this, R.layout.activity_home)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        viewPagerAdapter = AvatarPagerAdapter(viewModel.avatars.value ?: Avatars())
        binding.viewpagerSelectCharacter.adapter = viewPagerAdapter
        binding.viewpagerSelectCharacter.registerOnPageChangeCallback(object :
            ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)

                viewModel.avatars.value?.let {
                    viewModel.selectedAvatar = it.list[position]
                    viewModel.checkCanPressStartButton()
                }
            }
        })
    }

    private fun onAvatarsLoaded() {
        viewModel.avatars.value?.let {
            viewPagerAdapter.avatars = it
            viewPagerAdapter.notifyDataSetChanged()
        }
    }

    private fun onUserChanged() {
        viewModel.checkCanPressStartButton()
    }

    fun onClickSettingButton(view: View) {
        val intent = Intent(this, SettingActivity::class.java)
        startActivity(intent)
    }
}

class AvatarPagerAdapter(var avatars: Avatars) :
// TODO: ListAdapter로 리팩토링
    RecyclerView.Adapter<AvatarViewHolder>() {
    override fun getItemCount(): Int {
        return avatars.list.size
    }

    override fun onBindViewHolder(holder: AvatarViewHolder, position: Int) {
        holder.binding.avatar = avatars.list[position]
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AvatarViewHolder {
        val itemView =
            AvatarPagerItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return AvatarViewHolder(itemView)
    }
}

class AvatarViewHolder(val binding: AvatarPagerItemBinding) :
    RecyclerView.ViewHolder(binding.root)