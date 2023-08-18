package com.alexereh.stats.ui.stat

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.alexereh.ui.theme.ExtendedTheme

@Composable
fun ScoreColumn(
    modifier: Modifier = Modifier,
    title: String,
    score: Int?
) {
    val colors = listOf(
        ExtendedTheme.colors.noGradeBackground,
        ExtendedTheme.colors.badGradeBackground,
        ExtendedTheme.colors.fineGradeBackground,
        ExtendedTheme.colors.goodGradeBackground,
        ExtendedTheme.colors.excellentGradeBackground
    )
    val backgroundColor:MutableState<Color> = remember {
        var backgroundColorLocal = colors[0]
        var coefficient: Byte = 1
        if (title == "И") {
            coefficient = 2
        }
        if (score == null) {
            return@remember mutableStateOf(backgroundColorLocal)
        }
        when (score) {
            in 0..24 * coefficient -> {
                backgroundColorLocal = colors[1]
            }

            in 25 * coefficient..34 * coefficient -> {
                backgroundColorLocal = colors[2]
            }

            in 35 * coefficient..44 * coefficient -> {
                backgroundColorLocal = colors[3]
            }

            in 45 * coefficient..50 * coefficient -> {
                backgroundColorLocal = colors[4]
            }
        }
        mutableStateOf(backgroundColorLocal)
    }
    Column(
        modifier = modifier
            .width(IntrinsicSize.Max)
            .background(backgroundColor.value)
            .padding(SCORE_COLUMN_PADDING),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(0.dp)
    ) {
        Text(
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center,
            text = title,
            fontWeight = FontWeight.Black,
            fontSize = 16.sp
        )
        Box(
            modifier = Modifier
        ) {
            Text(
                text = "$score",
                fontWeight = FontWeight.Black,
                fontSize = 18.sp
            )
        }
    }
}

