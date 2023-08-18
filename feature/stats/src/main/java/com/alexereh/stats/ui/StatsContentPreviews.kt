package com.alexereh.stats.ui

import android.content.res.Configuration
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.alexereh.stats.FakeStatsComponent
import com.alexereh.ui.theme.CSFBRSTheme

@Preview(showSystemUi = false, device = "id:pixel_tablet", name = "Tablet Light", group = "Tablet")
@Composable
fun StatsContentPreviewTabletLight() {
    CSFBRSTheme {
        StatsContent(component = FakeStatsComponent())
    }
}

@Preview(showSystemUi = false, device = "id:pixel_tablet", name = "Tablet Dark", group = "Tablet",
    uiMode = Configuration.UI_MODE_NIGHT_YES or Configuration.UI_MODE_TYPE_NORMAL
)
@Composable
fun StatsContentPreviewTabletDark() {
    CSFBRSTheme {
        StatsContent(component = FakeStatsComponent())
    }
}

@Preview(showSystemUi = false, device = "id:pixel_7", name = "Phone Dark", group = "Phone",
    uiMode = Configuration.UI_MODE_NIGHT_YES or Configuration.UI_MODE_TYPE_NORMAL
)
@Composable
fun StatsContentPreviewPhoneDark() {
    CSFBRSTheme {
        StatsContent(component = FakeStatsComponent())
    }
}

@Preview(showSystemUi = false, device = "id:pixel_7", name = "Phone Light", group = "Phone",
    uiMode = Configuration.UI_MODE_NIGHT_NO or Configuration.UI_MODE_TYPE_NORMAL
)
@Composable
fun StatsContentPreviewPhoneLight() {
    CSFBRSTheme {
        StatsContent(component = FakeStatsComponent())
    }
}