package com.alexereh.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

private val darkColorScheme = darkColorScheme(
    primary = Purple80,
    secondary = PurpleGrey80,
    tertiary = Pink80
)

private val lightColorScheme = lightColorScheme(
    primary = Purple40,
    secondary = PurpleGrey40,
    tertiary = Pink40
)

@Immutable
data class ExtendedColors(
    val rowCardBackground: Color,
    val noGradeBackground: Color,
    val badGradeBackground: Color,
    val fineGradeBackground: Color,
    val goodGradeBackground: Color,
    val excellentGradeBackground: Color,
)

val LocalExtendedColors = staticCompositionLocalOf {
    ExtendedColors(
        rowCardBackground = Color.Unspecified,
        noGradeBackground = Color.Unspecified,
        badGradeBackground = Color.Unspecified,
        fineGradeBackground = Color.Unspecified,
        goodGradeBackground = Color.Unspecified,
        excellentGradeBackground = Color.Unspecified,
    )
}

val extendedColorsLight = ExtendedColors(
    rowCardBackground = RowCardBackgroundLight,
    noGradeBackground = NoScoreBackgroundLight,
    badGradeBackground = BadScoreBackgroundLight,
    fineGradeBackground = FineScoreBackgroundLight,
    goodGradeBackground = GoodScoreBackgroundLight,
    excellentGradeBackground = ExcellentScoreBackgroundLight,
)

val extendedColorsDark = ExtendedColors(
    rowCardBackground = RowCardBackgroundDark,
    noGradeBackground = NoScoreBackgroundDark,
    badGradeBackground = BadScoreBackgroundDark,
    fineGradeBackground = FineScoreBackgroundDark,
    goodGradeBackground = GoodScoreBackgroundDark,
    excellentGradeBackground = ExcellentScoreBackgroundDark,
)



@Composable
fun CSFBRSTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = true,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }

        darkTheme -> darkColorScheme
        else -> lightColorScheme
    }
    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            //window.statusBarColor = colorScheme.primary.toArgb()
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = !darkTheme
        }
    }
    CompositionLocalProvider(
        LocalExtendedColors provides
                if(darkTheme) extendedColorsDark
                else extendedColorsLight
    ) {
        MaterialTheme(
            colorScheme = colorScheme,
            typography = Typography,
            content = content
        )
    }
}

// Use with eg. ExtendedTheme.colors.tertiary
object ExtendedTheme {
    val colors: ExtendedColors
        @Composable
        get() = LocalExtendedColors.current
}
