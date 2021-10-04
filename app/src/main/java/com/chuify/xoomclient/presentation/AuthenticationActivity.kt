package com.chuify.xoomclient.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.chuify.xoomclient.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AuthenticationActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_authentication)

    }


}