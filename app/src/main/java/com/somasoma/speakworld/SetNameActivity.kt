package com.somasoma.speakworld

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.google.firebase.auth.FirebaseUser
import com.somasoma.speakworld.databinding.ActivitySetNameBinding
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class SetNameActivity : AppCompatActivity() {

    private val viewModel: SetNameViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val intent = intent

        val firebaseUser: FirebaseUser? = intent.getParcelableExtra("firebaseUser")
        viewModel.firebaseUser = firebaseUser

        val binding: ActivitySetNameBinding = DataBindingUtil.setContentView(this, R.layout.activity_set_name)
        binding.viewModel = viewModel

    }

    fun onClickNameButton(view: View){
        Timber.d(viewModel.name)
        if (viewModel.name == "") {
            return
        } else {
            val intent = Intent(this, SetLanguageActivity::class.java)
            intent.putExtra("name", viewModel.name)
            intent.putExtra("firebaseUser", viewModel.firebaseUser)
            startActivity(intent)
        }
    }
}