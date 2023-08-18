package com.alexereh.stats.ui

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StatsTopAppBar(
    modifier: Modifier = Modifier,
    onProfileAction: () -> Unit
) {
    CenterAlignedTopAppBar(
        modifier = modifier,
        title = {
            Text(
                text = "БРС",
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        },
        colors = TopAppBarDefaults.centerAlignedTopAppBarColors(),
        actions = {
            IconButton(onClick = { onProfileAction() }) {
                Icon(
                    imageVector = Icons.Filled.Person,
                    contentDescription = "Профиль"
                )
            }
        }
    )
}

@Preview
@Composable
fun StatsTopAppBarPreview() {
    MaterialTheme {
        StatsTopAppBar(
            onProfileAction = {}
        )
    }
}