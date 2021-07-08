package com.somasoma.speakworld

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.google.firebase.auth.FirebaseUser
import com.somasoma.speakworld.databinding.ActivitySetLanguageBinding
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class SetLanguageActivity : AppCompatActivity() {

    private val viewModel: SetLanguageViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val intent = intent
        viewModel.name = intent.getStringExtra("name")
        val firebaseUser: FirebaseUser? = intent.getParcelableExtra("firebaseUser")
        viewModel.setFirebaseUser(firebaseUser)

        val binding: ActivitySetLanguageBinding = DataBindingUtil.setContentView(this, R.layout.activity_set_language)
        binding.viewModel = viewModel

    }

    fun onClickLanguageButton(view: View){
        Timber.d(viewModel.language)
        if (viewModel.language == "") {
            return
        } else {
            Timber.d(viewModel.getFirebaseUser().toString())
            viewModel.signUp()
            val intent = Intent(this, HomeActivity::class.java)
            intent.putExtra("firebaseUser", viewModel.getFirebaseUser())
            startActivity(intent)
        }
    }
}