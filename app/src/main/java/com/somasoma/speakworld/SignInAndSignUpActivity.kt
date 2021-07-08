package com.somasoma.speakworld

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.somasoma.speakworld.databinding.ActivitySignInAndSignUpBinding
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class SignInAndSignUpActivity : AppCompatActivity() {

    private val viewModel: SignInAndSignUpViewModel by viewModels()

    private lateinit var signInLauncher: ActivityResultLauncher<Intent>

    private lateinit var auth: FirebaseAuth
    private lateinit var googleSignInClient: GoogleSignInClient


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (BuildConfig.DEBUG)
            Timber.plant(Timber.DebugTree())

        val binding: ActivitySignInAndSignUpBinding =
            DataBindingUtil.setContentView(this, R.layout.activity_sign_in_and_sign_up)

        // Configure Google Sign In
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()

        googleSignInClient = GoogleSignIn.getClient(this, gso)

        // Initialize Firebase Auth
        auth = Firebase.auth

        signInLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { activityResult ->
                val task = GoogleSignIn.getSignedInAccountFromIntent(activityResult.data)
                try {
                    // Google Sign In was successful, authenticate with Firebase
                    val account = task.getResult(ApiException::class.java)!!
                    Timber.d("firebaseAuthWithGoogle:%s", account.id)
                    firebaseAuthWithGoogle(account.idToken!!)
                } catch (e: ApiException) {
                    // Google Sign In failed, update UI appropriately
                    Timber.w("Google sign in failed")
                }
            }

    }

    override fun onStart() {
        super.onStart()
        // Check if user is signed in (non-null) and update UI accordingly.
        val currentUser = auth.currentUser
        updateUI(currentUser)
    }

    private fun firebaseAuthWithGoogle(idToken: String) {
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        auth.signInWithCredential(credential)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Timber.d("signInWithCredential:success")
                    val user = auth.currentUser

                    val isNewUser: Boolean = task.result?.additionalUserInfo?.isNewUser ?: false
                    if (isNewUser) {
                        // Let the new users do the initial setup
                        user?.let {
                            val intent = Intent(this, SetNameActivity::class.java)
                            intent.putExtra("firebaseUser", it)
                            startActivity(intent)
                        }
                    } else {
                        updateUI(user)
                    }
                } else {
                    // If sign in fails, display a message to the user.
                    Timber.w("signInWithCredential:failure")
                    updateUI(null)
                }
            }
    }

    fun onClick(view: View) {
        Timber.d("hello")
        signIn()
    }

    private fun signIn() {
        val signInIntent = googleSignInClient.signInIntent
        Timber.d(signInIntent.toString())
        signInLauncher.launch(signInIntent)
    }

    private fun updateUI(user: FirebaseUser?) {
        user?.let {
            val intent = Intent(this, HomeActivity::class.java)
            intent.putExtra("firebaseUser", it)
            startActivity(intent)
        }
    }
}