package com.alexereh.stats.ui.stat

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.alexereh.model.ScoringType
import com.alexereh.model.StatisticRow
import com.alexereh.stats.Score
import com.alexereh.ui.theme.CSFBRSTheme
import com.alexereh.ui.theme.ExtendedTheme
import com.alexereh.ui.theme.StatsRowBackGroundLight


val TEXT_PADDING: PaddingValues = PaddingValues(
    start = 5.dp,
    top = 10.dp,
    end = 5.dp,
    bottom = 5.dp
)
val SCORE_COLUMN_PADDING: PaddingValues = PaddingValues(
    start = 20.dp,
    top = 10.dp,
    end = 20.dp,
    bottom = 10.dp
)

@Composable
fun DisciplineStat(modifier: Modifier = Modifier, subjectState: State<StatisticRow>) {
    val subject by subjectState
    DisciplineStat(
        modifier = modifier,
        disciplineName = subject.disciplineName,
        disciplineScoring = subject.scoringType,
        score = Score(
            subject.marks.firstAttestationScore,
            subject.marks.secondAttestationScore,
            subject.marks.thirdAttestationScore,
            subject.marks.examScore,
            subject.marks.resultScore
        )
    )
}

@Composable
fun DisciplineStat(
    modifier: Modifier = Modifier,
    disciplineName: String = "Введение в программирование",
    disciplineScoring: ScoringType = ScoringType.EXAM,
    score: Score
) {
    Card(
        modifier = modifier.width(IntrinsicSize.Max),
        colors = CardDefaults.cardColors(
            containerColor = ExtendedTheme.colors.rowCardBackground
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 4.dp,
            pressedElevation = 4.dp,
            focusedElevation = 4.dp,
            hoveredElevation = 4.dp,
            draggedElevation = 4.dp,
            disabledElevation = 4.dp,
        )
    ) {
        Text(
            text = disciplineName,
            modifier = Modifier
                .padding(TEXT_PADDING)
                .fillMaxWidth()
                .background(ExtendedTheme.colors.rowCardBackground),
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center
        )
        Divider(
            thickness = Dp.Hairline,
            color = MaterialTheme.colorScheme.outline,
            modifier = Modifier.fillMaxWidth()
        )
        ScoreRow(
            modifier = Modifier
                .fillMaxWidth()
                .background(StatsRowBackGroundLight),
            scores = score,
            disciplineScoring = disciplineScoring
        )
    }
}


val PREVIEW_DEFAULT_SCORE = Score(
    first = 27,
    second = 44,
    third = 44,
    exam = 50,
    overall = 100
)
@Preview
@Composable
fun ExamStatPreview() {
    CSFBRSTheme {
        DisciplineStat(
            score = PREVIEW_DEFAULT_SCORE,
            modifier = Modifier
                .sizeIn(
                    //minWidth = 400.dp,
                    //minHeight = 200.dp,
                    maxWidth = 900.dp,
                    maxHeight = 400.dp
                )
        )
    }
}