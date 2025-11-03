package com.rony.assignment

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.compose.rememberNavController
import com.rony.assignment.core.presentation.design_system.NotesApplicationTheme
import com.rony.assignment.navigation.NavigationRoot

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        var shouldShowSplash = true
        installSplashScreen().setKeepOnScreenCondition {
            shouldShowSplash
        }
        enableEdgeToEdge()
        setContent {
            App(
                onAuthenticationChecked = {
                    shouldShowSplash = false
                }
            )
        }
    }
}

@Preview
@Composable
private fun MainPreview() {
    App(
      onAuthenticationChecked = {}
    )
}