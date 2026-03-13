package com.app.cheatbite.ui.theme

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.remember

@Composable
fun AppUtils(
    appDimens: Dimensions,
    content: @Composable () -> Unit
) {
    val appDimension = remember {
        appDimens
    }

    CompositionLocalProvider(LocalAppDimens provides appDimension) {
        content()
    }
}

val LocalAppDimens = compositionLocalOf {
    compactDimens
}