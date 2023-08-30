package com.alexereh.ui.util

import android.content.res.Configuration
import androidx.compose.ui.tooling.preview.Preview

@Preview(name = "Phone Light", device = "spec:shape=Normal,width=360,height=640,unit=dp,dpi=480",
    uiMode = Configuration.UI_MODE_NIGHT_NO or Configuration.UI_MODE_TYPE_NORMAL
)
@Preview(name = "Phone Dark", device = "spec:shape=Normal,width=360,height=640,unit=dp,dpi=480",
    uiMode = Configuration.UI_MODE_NIGHT_YES or Configuration.UI_MODE_TYPE_NORMAL
)
@Preview(name = "Landscape Light", device = "spec:shape=Normal,width=640,height=360,unit=dp,dpi=480",
    uiMode = Configuration.UI_MODE_NIGHT_NO or Configuration.UI_MODE_TYPE_NORMAL
)
@Preview(name = "Landscape Dark", device = "spec:shape=Normal,width=640,height=360,unit=dp,dpi=480",
    uiMode = Configuration.UI_MODE_NIGHT_YES or Configuration.UI_MODE_TYPE_NORMAL
)
@Preview(name = "Foldable Light", device = "spec:shape=Normal,width=673,height=841,unit=dp,dpi=480",
    uiMode = Configuration.UI_MODE_NIGHT_NO or Configuration.UI_MODE_TYPE_NORMAL
)
@Preview(name = "Foldable Dark", device = "spec:shape=Normal,width=673,height=841,unit=dp,dpi=480",
    uiMode = Configuration.UI_MODE_NIGHT_YES or Configuration.UI_MODE_TYPE_NORMAL
)
@Preview(name = "Tablet Light", device = "spec:shape=Normal,width=1280,height=800,unit=dp,dpi=480",
    uiMode = Configuration.UI_MODE_NIGHT_NO or Configuration.UI_MODE_TYPE_NORMAL
)
@Preview(name = "Tablet Dark", device = "spec:shape=Normal,width=1280,height=800,unit=dp,dpi=480",
    uiMode = Configuration.UI_MODE_NIGHT_YES or Configuration.UI_MODE_TYPE_NORMAL
)
annotation class DefaultPreviews