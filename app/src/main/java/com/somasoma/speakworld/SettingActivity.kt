package com.somasoma.speakworld

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.firebase.ui.auth.AuthUI
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SettingActivity : AppCompatActivity() {

    private val viewModel: SettingViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_setting)
    }

    fun onClickChangeNameButton(view: View) {
        val intent = Intent(this, SettingChangeNameActivity::class.java)
        startActivity(intent)
    }

    fun onClickChangeLanguageButton(view: View) {
        val intent = Intent(this, SettingChangeLanguageActivity::class.java)
        startActivity(intent)
    }

    fun onClickSignOutButton(view: View) {
        AuthUI.getInstance()
            .signOut(this)
            .addOnCompleteListener {
                val intent = Intent(this, SignInAndSignUpActivity::class.java)
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                startActivity(intent)
            }
    }

    fun onClickDeleteAccountButton(view: View) {
        // 유저 정보 실시간 db에서 삭제해야 함
        AuthUI.getInstance()
            .delete(this)
            .addOnCompleteListener {
                val intent = Intent(this, SignInAndSignUpActivity::class.java)
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                startActivity(intent)
            }
    }

}