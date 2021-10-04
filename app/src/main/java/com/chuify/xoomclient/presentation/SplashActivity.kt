package com.chuify.xoomclient.presentation

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.chuify.xoomclient.data.prefrences.SharedPrefs
import com.chuify.xoomclient.presentation.ui.signup.TAG
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class SplashActivity : AppCompatActivity() {

    @Inject
    lateinit var sharedPreferences: SharedPrefs

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val token = sharedPreferences.getToken()
        Log.d(TAG, "token $token")
        if (token.isEmpty()) {
            startActivity(Intent(this@SplashActivity, AuthenticationActivity::class.java))
        } else {
            startActivity(Intent(this@SplashActivity, MainActivity::class.java))
        }
        finish()

    }
}