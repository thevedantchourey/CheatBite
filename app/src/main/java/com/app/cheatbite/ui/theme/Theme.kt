package com.app.cheatbite.ui.theme

import android.util.Log
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Shapes
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp


private val DarkColorScheme = darkColorScheme(
    background = Color.White,
    surface = OffBlack,
    primary = OffWhite,
    secondary = Color.White,
    tertiary = TextPink,
    onTertiaryContainer = SkyPink
)

private val LightColorScheme = lightColorScheme(
    background = Color.Black,
    surface = OffWhite,
    primary = OffBlack,
    secondary = Color.Black,
    tertiary = SkyPink,
    onTertiaryContainer = TextPink
)


val AppShapes = Shapes(
    extraSmall = RoundedCornerShape(4.dp),
    small = RoundedCornerShape(8.dp),
    medium = RoundedCornerShape(12.dp), // Often used for cards, buttons
    large = RoundedCornerShape(16.dp),  // Larger elements
    extraLarge = RoundedCornerShape(24.dp)
)

@Composable
fun CheatBiteTheme(
    content: @Composable () -> Unit
) {

    val colorScheme = LightColorScheme
    val screenSize = getScreenSize()

    val appDimens = when (screenSize) {
        ScreenSize.SMALL -> compactSmallDimens
        ScreenSize.MEDIUM -> compactMediumDimens
        ScreenSize.LARGE -> compactDimens
        ScreenSize.XLARGE -> largeScreenDimens
    }

    val typography = when (screenSize) {
        ScreenSize.SMALL -> compactSmallTypography
        ScreenSize.MEDIUM -> compactMediumTypography
        ScreenSize.LARGE -> compactTypography
        ScreenSize.XLARGE -> largeScreenTypography
    }


    Log.d("TAG", "ApplyWiseTheme: $screenSize")

    AppUtils(appDimens = appDimens) {
        MaterialTheme(
            colorScheme = colorScheme,
            typography = typography,
            content = content,
            shapes = AppShapes
        )
    }
}

val MaterialTheme.dimens
    @Composable
    get() = LocalAppDimens.current