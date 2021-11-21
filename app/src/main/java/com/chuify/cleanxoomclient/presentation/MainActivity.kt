package com.chuify.cleanxoomclient.presentation

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.collectAsState
import com.chuify.cleanxoomclient.data.prefrences.flow.FlowSharedPreferences
import com.chuify.cleanxoomclient.presentation.navigation.MainNavigation
import com.chuify.cleanxoomclient.presentation.service.FireBaseCloudMessagingService
import com.chuify.cleanxoomclient.presentation.theme.XoomGasClientTheme
import com.google.accompanist.pager.ExperimentalPagerApi
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi
import javax.inject.Inject

@ExperimentalCoroutinesApi
@ExperimentalPagerApi
@ExperimentalAnimationApi
@ExperimentalFoundationApi
@ExperimentalMaterialApi
@AndroidEntryPoint
class MainActivity : ComponentActivity() {


    @Inject
    lateinit var flowSharedPreferences: FlowSharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val myService = Intent(this, FireBaseCloudMessagingService::class.java)
        startService(myService)
        setContent {
            val isDark = flowSharedPreferences.getBoolean("IS_DARK", false).asFlow().collectAsState(
                initial = false
            ).value
            XoomGasClientTheme(darkTheme = isDark) {
                MainNavigation()
            }
        }

    }

}