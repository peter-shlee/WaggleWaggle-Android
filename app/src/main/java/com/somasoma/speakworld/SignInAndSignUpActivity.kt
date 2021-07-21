package com.somasoma.speakworld

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.firebase.ui.auth.AuthUI
import com.firebase.ui.auth.FirebaseAuthUIActivityResultContract
import com.firebase.ui.auth.data.model.FirebaseAuthUIAuthenticationResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.somasoma.speakworld.databinding.ActivitySignInAndSignUpBinding
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class SignInAndSignUpActivity : AppCompatActivity() {

    private val viewModel: SignInAndSignUpViewModel by viewModels()

    private val signInLauncher = registerForActivityResult(
        FirebaseAuthUIActivityResultContract()
    ) { res ->
        this.onSignInResult(res)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding: ActivitySignInAndSignUpBinding =
            DataBindingUtil.setContentView(this, R.layout.activity_sign_in_and_sign_up)
    }

    override fun onStart() {
        super.onStart()

        val user = FirebaseAuth.getInstance().currentUser
        if (user != null) {
            updateUI(user, false)
        }
    }

    private fun updateUI(user: FirebaseUser?, isNewUser: Boolean) {
        Timber.d("hello world")
        user?.let {
            if (isNewUser) {
                Timber.d("hello world2")
                val intent = Intent(this, SignUpSetNameActivity::class.java)
                startActivity(intent)
            } else {
                Timber.d("hello world3")
                val intent = Intent(this, HomeActivity::class.java)
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                startActivity(intent)
            }
        }
    }

    private fun createSignInIntent() {
        // Choose authentication providers
        val providers = arrayListOf(
            AuthUI.IdpConfig.GoogleBuilder().build()
        )

        // Create and launch sign-in intent
        val signInIntent = AuthUI.getInstance()
            .createSignInIntentBuilder()
            .setAvailableProviders(providers)
            .build()
        signInLauncher.launch(signInIntent)
    }

    private fun onSignInResult(result: FirebaseAuthUIAuthenticationResult) {
        val response = result.idpResponse
        if (result.resultCode == RESULT_OK) {
            // Successfully signed in
            val user = FirebaseAuth.getInstance().currentUser
            Timber.d(user.toString())
            val isNewUser: Boolean = response?.isNewUser ?: false
            updateUI(user, isNewUser)
        } else {
            Timber.w("signInWithCredential:failure")
            updateUI(null, false)
        }
    }

    fun onClickSignInButton(view: View) {
        createSignInIntent()
    }
}