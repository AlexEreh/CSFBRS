package com.alexereh.ui.theme

import android.app.Activity
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

private val darkColorScheme = darkColorScheme(
    primary = PrimaryDark,
    secondary = SecondaryDark,
    tertiary = TertiaryDark
)

private val lightColorScheme = lightColorScheme(
    primary = PrimaryLight,
    secondary = SecondaryLight,
    tertiary = TertiaryLight
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
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) darkColorScheme else lightColorScheme
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

object ExtendedTheme {
    val colors: ExtendedColors
        @Composable
        get() = LocalExtendedColors.current
}
