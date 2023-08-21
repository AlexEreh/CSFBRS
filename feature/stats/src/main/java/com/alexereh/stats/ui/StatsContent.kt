package com.alexereh.stats.ui

import androidx.compose.foundation.isSystemInDarkTheme
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
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.alexereh.model.StatisticRow
import com.alexereh.stats.component.StatsComponent
import com.alexereh.stats.ui.stat.DisciplineStat
import com.alexereh.util.Result
import com.google.accompanist.systemuicontroller.rememberSystemUiController

@Composable
fun StatsContent(component: StatsComponent) {
    val rows by component.statRows.collectAsStateWithLifecycle()
    StatsContent(
        rows = rows,
        doProfileAction = component::doProfileAction
    )
}

@Composable
fun StatsContent(
    rows: Result<List<StatisticRow>>,
    doProfileAction: () -> Unit,
){
    val systemUiController = rememberSystemUiController()
    val useDarkIcons = !isSystemInDarkTheme()
    val surfaceColor = MaterialTheme.colorScheme.surface
    DisposableEffect(systemUiController, useDarkIcons) {
        // Update all of the system bar colors to be transparent, and use
        // dark icons if we're in light theme
        systemUiController.setSystemBarsColor(
            color = surfaceColor,
            darkIcons = useDarkIcons
        )

        // setStatusBarColor() and setNavigationBarColor() also exist

        onDispose {}
    }
    Scaffold (
        topBar = {
            Surface(
                shadowElevation = 6.dp,
            ) {
                StatsTopAppBar(
                    modifier = Modifier,
                    onProfileAction = doProfileAction
                )
            }
        }
    ){ scaffoldPadding ->
        when (rows) {
            is Result.NotLoading -> {

            }
            is Result.Loading -> {
                Column (
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(scaffoldPadding)
                        .wrapContentSize(Alignment.Center)
                ){
                    CircularProgressIndicator()
                }
            }
            is Result.Success -> {
                LazyVerticalStaggeredGrid(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(scaffoldPadding),
                    columns = StaggeredGridCells.Adaptive(320.dp),
                    contentPadding = PaddingValues(10.dp),
                    verticalItemSpacing = 10.dp,
                    horizontalArrangement = Arrangement.spacedBy(10.dp, Alignment.Start)
                ) {
                    items(items = rows.data) { subject ->
                        DisciplineStat(subject = subject)
                    }
                }
            }

            is Result.Error -> {

            }
        }
    }
}