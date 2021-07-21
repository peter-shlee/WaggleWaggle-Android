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
import com.somasoma.speakworld.databinding.CharacterPagerItemBinding
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class HomeActivity : AppCompatActivity() {
    private val viewModel: HomeViewModel by viewModels()
    lateinit var viewPagerAdapter: CharacterPagerAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel.getUserInfo()

        val binding: ActivityHomeBinding =
            DataBindingUtil.setContentView(this, R.layout.activity_home)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        viewPagerAdapter = CharacterPagerAdapter(viewModel.characters.value ?: Characters())
        binding.viewpagerSelectCharacter.adapter = viewPagerAdapter

        binding.viewpagerSelectCharacter.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)

                val index = binding.viewpagerSelectCharacter.currentItem
                viewModel.characters.value?.let {
                    viewModel.selectedCharacter = it.list[index]
                }

                Timber.d(viewModel.selectedCharacter)
            }
        })

        viewModel.characters.observe(this) { onCharactersLoaded() }
    }

    private fun onCharactersLoaded() {
        viewModel.characters.value?.let {
            viewPagerAdapter.characters = it
            viewPagerAdapter.notifyDataSetChanged()
        }
    }

    fun onClickSettingButton(view: View) {
        val intent = Intent(this, SettingActivity::class.java)
        startActivity(intent)
    }
}

class CharacterPagerAdapter(var characters: Characters) :
    RecyclerView.Adapter<CharacterViewHolder>() {
    override fun getItemCount(): Int {
        return characters.list.size
    }

    override fun onBindViewHolder(holder: CharacterViewHolder, position: Int) {
        holder.binding.character = characters.list[position]
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CharacterViewHolder {
        val itemView =
            CharacterPagerItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CharacterViewHolder(itemView)
    }
}

class CharacterViewHolder(val binding: CharacterPagerItemBinding) :
    RecyclerView.ViewHolder(binding.root)