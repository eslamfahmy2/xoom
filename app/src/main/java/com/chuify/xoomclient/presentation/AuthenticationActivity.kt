package com.chuify.xoomclient.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.collectAsState
import com.chuify.xoomclient.data.prefrences.flow.FlowSharedPreferences
import com.chuify.xoomclient.presentation.navigation.authentication.AuthenticationNavigation
import com.chuify.xoomclient.presentation.theme.XoomGasClientTheme
import com.google.accompanist.pager.ExperimentalPagerApi
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi
import javax.inject.Inject

@ExperimentalMaterialApi
@ExperimentalPagerApi
@ExperimentalAnimationApi
@ExperimentalFoundationApi
@ExperimentalCoroutinesApi
@AndroidEntryPoint
class AuthenticationActivity : ComponentActivity() {

    @Inject
    lateinit var flowSharedPreferences: FlowSharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val isDark = flowSharedPreferences.getBoolean("IS_DARK", false).asFlow().collectAsState(
                initial = false
            ).value
            XoomGasClientTheme(darkTheme = isDark) {
                AuthenticationNavigation()
            }
        }

    }


}