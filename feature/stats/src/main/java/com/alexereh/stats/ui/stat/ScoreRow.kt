package com.alexereh.stats.ui.stat

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.alexereh.model.ScoringType
import com.alexereh.stats.Score

@Composable
fun ScoreRow(
    modifier: Modifier = Modifier,
    scores: Score,
    disciplineScoring: ScoringType
) {
    Row(
        modifier = modifier.height(IntrinsicSize.Max).width(IntrinsicSize.Max),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        ScoreColumn(
            title = "А1",
            score = scores.first,
            modifier = Modifier.fillMaxWidth().weight(1f)
        )
        ScoreColumn(
            title = "А2",
            score = scores.second,
            modifier = Modifier.fillMaxWidth().weight(1f)
        )
        ScoreColumn(
            title = "А3",
            score = scores.third,
            modifier = Modifier.fillMaxWidth().weight(1f)
        )
        if (disciplineScoring == ScoringType.EXAM) {
            ScoreColumn(
                title = "Э",
                score = scores.exam,
                modifier = Modifier.fillMaxWidth().weight(1f)
            )
        }
        ScoreColumn(
            title = "И",
            score = scores.overall,
            modifier = Modifier.fillMaxWidth().weight(1f)
        )
    }
}