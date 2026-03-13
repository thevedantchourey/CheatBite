package com.app.cheatbite

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.lifecycle.lifecycleScope
import androidx.navigation.compose.rememberNavController
import com.app.cheatbite.app.presentation.navigation.AppNavHost
import com.app.cheatbite.ui.theme.CheatBiteTheme
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject
import kotlin.getValue


class MainActivity : ComponentActivity() {
    val sharedPreference: UserSharedPreference by inject()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        lifecycleScope.launch {
            enableEdgeToEdge(
                statusBarStyle = SystemBarStyle.light(Color.Transparent.toArgb(), Color.Transparent.toArgb()),
            )
        }
        setContent {
            val startDestination = if(sharedPreference.getUserStatus()){
                 "mainGraph"
            }else{
                 "authGraph"
            }
            val navController = rememberNavController()
            CheatBiteTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    AppNavHost(
                        navController = navController,
                        modifier = Modifier.padding(innerPadding),
                        startDestination = startDestination
                    )
                }
            }
        }
    }
}
