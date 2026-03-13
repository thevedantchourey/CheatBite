package com.app.cheatbite.ui.theme

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalWindowInfo
import androidx.compose.ui.unit.dp

enum class ScreenSize {
    SMALL, MEDIUM, LARGE, XLARGE
}

@Composable
fun getScreenSize(): ScreenSize {
    val density = LocalDensity.current
    val window = LocalWindowInfo.current
    val screenHeightDp = with(density) { window.containerSize.height.toDp() }
    val screenWidthDp = with(density) { window.containerSize.width.toDp() }

    return when {
        screenWidthDp < 360.dp || screenHeightDp < 640.dp -> ScreenSize.SMALL
        screenWidthDp < 400.dp || screenHeightDp < 800.dp -> ScreenSize.MEDIUM
        screenWidthDp < 600.dp || screenHeightDp < 1000.dp -> ScreenSize.LARGE
        else -> ScreenSize.XLARGE
    }
}