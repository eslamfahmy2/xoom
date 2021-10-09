package com.chuify.xoomclient.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.ExperimentalMaterialApi
import androidx.navigation.compose.rememberNavController
import com.chuify.xoomclient.presentation.navigation.MainNavigation
import com.chuify.xoomclient.presentation.theme.XoomGasClientTheme
import com.google.accompanist.pager.ExperimentalPagerApi
import dagger.hilt.android.AndroidEntryPoint

@ExperimentalMaterialApi
@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @ExperimentalPagerApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            XoomGasClientTheme(darkTheme = true) {
                val navHostController = rememberNavController()
                MainNavigation(navController = navHostController)
            }
        }

    }

}