package com.example.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ProvideTextStyle
import androidx.compose.material3.Shapes
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.remember
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

val ExpressiveShapes = Shapes(
    extraSmall = RoundedCornerShape(12.dp),
    small = RoundedCornerShape(16.dp),
    medium = RoundedCornerShape(20.dp),
    large = RoundedCornerShape(24.dp),
    extraLarge = RoundedCornerShape(28.dp)
)

@Immutable
data class ResponsiveDimens(
    val screenWidthDp: Int = 392,
    val screenHeightDp: Int = 800,
    val isCompactPhone: Boolean = false,
    val isExpandedScreen: Boolean = false,
    val horizontalScreenPadding: Dp = 14.dp,
    val cardInnerPadding: Dp = 14.dp,
    val cardCornerRadius: Dp = 22.dp,
    val itemVerticalSpacing: Dp = 10.dp,
    val subjectGridColumns: Int = 2
) {
    val horizontalPadding: Dp get() = horizontalScreenPadding
    val verticalSpacing: Dp get() = itemVerticalSpacing
    val gridColumns: Int get() = subjectGridColumns
}

val LocalResponsiveDimens = staticCompositionLocalOf { ResponsiveDimens() }

@Composable
fun MyApplicationTheme(
    themeController: AppThemeController? = null,
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit
) {
    val context = LocalContext.current
    val controller = themeController ?: remember { AppThemeController(context) }

    val resolvedDarkTheme = if (themeController != null) {
        controller.resolveIsDarkTheme(darkTheme)
    } else {
        darkTheme
    }

    val resolvedDynamicColor = dynamicColor || controller.useDynamicColor
    val selectedPalette = controller.selectedColorPalette

    val colorScheme = when {
        resolvedDynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            if (resolvedDarkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }
        resolvedDarkTheme -> selectedPalette.toDarkColorScheme()
        else -> selectedPalette.toLightColorScheme()
    }

    val revision = controller.fontRevision
    val isBold = controller.isBoldTextEnabled
    val weightLevel = controller.fontWeightLevel
    val activeFontFamily = remember(controller.customFontFamily, controller.selectedFontPreset, isBold, weightLevel, revision) {
        controller.customFontFamily ?: controller.selectedFontPreset.toFontFamily(forceBold = false)
    }

    val configuration = LocalConfiguration.current
    val screenWidth = configuration.screenWidthDp
    val screenHeight = configuration.screenHeightDp

    val responsiveDimens = remember(screenWidth, screenHeight) {
        when {
            screenWidth < 360 -> ResponsiveDimens(
                screenWidthDp = screenWidth,
                screenHeightDp = screenHeight,
                isCompactPhone = true,
                isExpandedScreen = false,
                horizontalScreenPadding = 12.dp,
                cardInnerPadding = 12.dp,
                cardCornerRadius = 18.dp,
                itemVerticalSpacing = 8.dp,
                subjectGridColumns = 2
            )
            screenWidth >= 600 -> ResponsiveDimens(
                screenWidthDp = screenWidth,
                screenHeightDp = screenHeight,
                isCompactPhone = false,
                isExpandedScreen = true,
                horizontalScreenPadding = 20.dp,
                cardInnerPadding = 16.dp,
                cardCornerRadius = 24.dp,
                itemVerticalSpacing = 12.dp,
                subjectGridColumns = 3
            )
            else -> ResponsiveDimens(
                screenWidthDp = screenWidth,
                screenHeightDp = screenHeight,
                isCompactPhone = false,
                isExpandedScreen = false,
                horizontalScreenPadding = 14.dp,
                cardInnerPadding = 14.dp,
                cardCornerRadius = 22.dp,
                itemVerticalSpacing = 10.dp,
                subjectGridColumns = 2
            )
        }
    }

    val activeTypography = remember(
        activeFontFamily,
        controller.selectedFontPreset,
        controller.customFontPath,
        controller.fontScale,
        controller.lineSpacingScale,
        isBold,
        weightLevel,
        revision
    ) {
        buildAppTypography(
            bodyFontFamily = activeFontFamily,
            headlineFontFamily = activeFontFamily,
            scale = controller.fontScale,
            isBoldText = isBold,
            fontWeightLevel = weightLevel,
            lineSpacingScale = controller.lineSpacingScale
        )
    }

    val layoutDir = if (controller.appLanguage == "en") {
        androidx.compose.ui.unit.LayoutDirection.Ltr
    } else {
        androidx.compose.ui.unit.LayoutDirection.Rtl
    }

    val defaultBodyWeight = when (weightLevel) {
        2 -> FontWeight.Bold
        1 -> FontWeight.Medium
        else -> FontWeight.Normal
    }

    CompositionLocalProvider(
        LocalAppThemeController provides controller,
        LocalResponsiveDimens provides responsiveDimens,
        androidx.compose.ui.platform.LocalLayoutDirection provides layoutDir
    ) {
        MaterialTheme(
            colorScheme = colorScheme,
            typography = activeTypography,
            shapes = ExpressiveShapes
        ) {
            ProvideTextStyle(
                value = activeTypography.bodyMedium.copy(
                    fontFamily = activeFontFamily,
                    fontWeight = defaultBodyWeight,
                    fontSynthesis = androidx.compose.ui.text.font.FontSynthesis.None
                )
            ) {
                content()
            }
        }
    }
}
