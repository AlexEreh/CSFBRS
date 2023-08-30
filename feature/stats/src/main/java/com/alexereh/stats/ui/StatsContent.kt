package com.alexereh.stats.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.alexereh.model.StatisticRow
import com.alexereh.stats.component.FakeStatsComponent
import com.alexereh.stats.component.StatsComponent
import com.alexereh.stats.ui.stat.DisciplineStat
import com.alexereh.ui.theme.CSFBRSTheme
import com.alexereh.ui.util.DefaultPreviews
import com.alexereh.util.Resource

@Composable
fun StatsContent(component: StatsComponent) {
    val rows by component.statRows.collectAsStateWithLifecycle()
    Scaffold (
        topBar = {
            Surface(
                shadowElevation = 6.dp,
            ) {
                StatsTopAppBar(
                    modifier = Modifier,
                    onProfileAction = component::doProfileAction
                )
            }
        }
    ){ scaffoldPadding ->
        when (rows) {
            is Resource.NotLoading -> {

            }
            is Resource.Loading -> {
                Column (
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(scaffoldPadding)
                        .wrapContentSize(Alignment.Center)
                ){
                    CircularProgressIndicator()
                }
            }
            is Resource.Success -> {
                LazyVerticalStaggeredGrid(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(scaffoldPadding),
                    columns = StaggeredGridCells.Adaptive(320.dp),
                    contentPadding = PaddingValues(10.dp),
                    verticalItemSpacing = 10.dp,
                    horizontalArrangement = Arrangement.spacedBy(10.dp, Alignment.Start)
                ) {
                    items(
                        items = (rows as Resource.Success<List<StatisticRow>>).data,
                    ) { subject ->
                        val subjectState = remember {
                            mutableStateOf(subject)
                        }
                        DisciplineStat(subjectState = subjectState)
                    }
                }
            }

            is Resource.Error -> {

            }
        }
    }
}

@DefaultPreviews
@Composable
fun StatsContentPreview() {
    CSFBRSTheme {
        StatsContent(component = FakeStatsComponent())
    }
}