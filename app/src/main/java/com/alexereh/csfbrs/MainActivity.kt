package com.alexereh.csfbrs

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Modifier
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.view.WindowCompat
import com.alexereh.csfbrs.root.DefaultRootComponent
import com.alexereh.csfbrs.root.RootContent
import com.alexereh.ui.theme.CSFBRSTheme
import com.alexereh.ui.theme.ExtendedTheme
import com.arkivanov.decompose.defaultComponentContext

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        val splashScreen = installSplashScreen()
        super.onCreate(savedInstanceState)

        val root = DefaultRootComponent(
            componentContext = defaultComponentContext()
        )

        WindowCompat.setDecorFitsSystemWindows(window, false)

        splashScreen.setKeepOnScreenCondition {
            root.showSplashScreen.value
        }

        setContent {
            CSFBRSTheme {
                RootContent(
                    component = root,
                    modifier = Modifier
                        .background(ExtendedTheme.colors.rowCardBackground)
                        .fillMaxSize()
                )
            }
        }
    }
}