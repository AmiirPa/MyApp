package com.example.ui.calm

import android.content.Context
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ProvideTextStyle
import androidx.compose.material3.Shapes
import androidx.compose.material3.Typography
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.theme.LocalAppThemeController

/*
 * طراحی آرام (Calm UI)
 * ---------------------
 * یک تم واحد برای کل برنامه: پس‌زمینهٔ خنثی و نرم، فقط یک رنگ لهجه (سبزآبیِ آرام)،
 * کارت‌های گرد یکدست، بدون گرادیان. حالت روشن و تیره هر دو تعریف شده‌اند.
 */

private val Accent = Color(0xFF2F7A72)
private val AccentDark = Color(0xFF7CC8BE)

private val CalmLight: ColorScheme = lightColorScheme(
    primary = Accent,
    onPrimary = Color(0xFFFFFFFF),
    primaryContainer = Color(0xFFDCEEEA),
    onPrimaryContainer = Color(0xFF0E3B36),
    secondary = Color(0xFF5B6B69),
    onSecondary = Color(0xFFFFFFFF),
    secondaryContainer = Color(0xFFE6ECEA),
    onSecondaryContainer = Color(0xFF1B2826),
    tertiary = Color(0xFF6B6F76),
    onTertiary = Color(0xFFFFFFFF),
    tertiaryContainer = Color(0xFFECEAE6),
    onTertiaryContainer = Color(0xFF26282C),
    background = Color(0xFFF6F5F2),
    onBackground = Color(0xFF1E2124),
    surface = Color(0xFFFFFFFF),
    onSurface = Color(0xFF1E2124),
    surfaceVariant = Color(0xFFEFEDE9),
    onSurfaceVariant = Color(0xFF686C72),
    surfaceTint = Color.Transparent,
    outline = Color(0xFFD9D6CF),
    outlineVariant = Color(0xFFE8E5DF),
    error = Color(0xFFB3261E),
    onError = Color(0xFFFFFFFF),
    errorContainer = Color(0xFFF9DEDC),
    onErrorContainer = Color(0xFF410E0B),
    inverseSurface = Color(0xFF2E3134),
    inverseOnSurface = Color(0xFFF1F0EC),
    inversePrimary = AccentDark,
    scrim = Color(0xFF000000)
)

private val CalmDark: ColorScheme = darkColorScheme(
    primary = AccentDark,
    onPrimary = Color(0xFF00332E),
    primaryContainer = Color(0xFF1E3D39),
    onPrimaryContainer = Color(0xFFCBEAE4),
    secondary = Color(0xFFB4C2BF),
    onSecondary = Color(0xFF1F2A28),
    secondaryContainer = Color(0xFF2A3230),
    onSecondaryContainer = Color(0xFFD8E2DF),
    tertiary = Color(0xFFA6A9AE),
    onTertiary = Color(0xFF26282C),
    tertiaryContainer = Color(0xFF2C2E31),
    onTertiaryContainer = Color(0xFFE2E1DE),
    background = Color(0xFF131416),
    onBackground = Color(0xFFE7E6E3),
    surface = Color(0xFF1B1C1F),
    onSurface = Color(0xFFE7E6E3),
    surfaceVariant = Color(0xFF25272A),
    onSurfaceVariant = Color(0xFFA4A7AC),
    surfaceTint = Color.Transparent,
    outline = Color(0xFF3A3D41),
    outlineVariant = Color(0xFF2D3033),
    error = Color(0xFFF2B8B5),
    onError = Color(0xFF601410),
    errorContainer = Color(0xFF8C1D18),
    onErrorContainer = Color(0xFFF9DEDC),
    inverseSurface = Color(0xFFE7E6E3),
    inverseOnSurface = Color(0xFF2E3134),
    inversePrimary = Accent,
    scrim = Color(0xFF000000)
)

/** رنگ «درست» آرام برای بازخورد سؤال‌ها (فقط در لحظهٔ پاسخ). */
val CalmSuccess: Color @Composable get() =
    if (MaterialTheme.colorScheme.background.red < 0.5f) Color(0xFF8FD3A6) else Color(0xFF2E7D4F)

val CalmShapes = Shapes(
    extraSmall = RoundedCornerShape(10.dp),
    small = RoundedCornerShape(14.dp),
    medium = RoundedCornerShape(18.dp),
    large = RoundedCornerShape(22.dp),
    extraLarge = RoundedCornerShape(28.dp)
)

private fun calmTypography(
    fontFamily: androidx.compose.ui.text.font.FontFamily,
    fontScale: Float,
    lineSpacingScale: Float,
    fontWeightLevel: Int
): Typography {
    fun s(size: Int, line: Int, w: FontWeight = FontWeight.Normal): TextStyle {
        val weight = when (fontWeightLevel) {
            1 -> when (w) {
                FontWeight.Normal -> FontWeight.Medium
                else -> w
            }
            2 -> when (w) {
                FontWeight.Normal -> FontWeight.Medium
                FontWeight.Medium -> FontWeight.Bold
                else -> FontWeight.Bold
            }
            else -> w
        }
        return TextStyle(
            fontFamily = fontFamily,
            fontSize = (size * fontScale).sp,
            lineHeight = (line * fontScale * lineSpacingScale).sp,
            fontWeight = weight
        )
    }
    return Typography(
        displayLarge = s(44, 56, FontWeight.Bold),
        displayMedium = s(36, 48, FontWeight.Bold),
        displaySmall = s(30, 42, FontWeight.Bold),
        headlineLarge = s(28, 40, FontWeight.Bold),
        headlineMedium = s(24, 36, FontWeight.Bold),
        headlineSmall = s(21, 32, FontWeight.Bold),
        titleLarge = s(19, 30, FontWeight.Bold),
        titleMedium = s(16, 26, FontWeight.Medium),
        titleSmall = s(14, 22, FontWeight.Medium),
        bodyLarge = s(16, 28),
        bodyMedium = s(14, 24),
        bodySmall = s(12, 20),
        labelLarge = s(14, 20, FontWeight.Medium),
        labelMedium = s(12, 18, FontWeight.Medium),
        labelSmall = s(11, 16, FontWeight.Medium)
    )
}

/** تنظیمات خود طراحی تازه (جدا از تنظیمات قدیمی؛ هیچ دادهٔ دیگری را تغییر نمی‌دهد). */
@Stable
class CalmPrefs(context: Context) {
    private val prefs = context.applicationContext.getSharedPreferences("calm_ui_prefs", Context.MODE_PRIVATE)

    /** ضریب اندازهٔ متن درس: ۰٫۹ کوچک، ۱ معمولی، ۱٫۱۵ بزرگ. */
    var readingScale by mutableFloatStateOf(prefs.getFloat(KEY_READING_SCALE, 1f))
        private set

    /** نقطهٔ شروع مسیر: از دبستان (پیش‌فرض، برای شروع واقعاً از صفر)، متوسطه اول یا متوسطه دوم. */
    var startStage by mutableStateOf(
        PathStage.entries.firstOrNull { it.name == prefs.getString(KEY_START_STAGE, null) } ?: PathStage.ELEMENTARY
    )
        private set

    fun updateReadingScale(value: Float) {
        readingScale = value.coerceIn(0.85f, 1.3f)
        prefs.edit().putFloat(KEY_READING_SCALE, readingScale).apply()
    }

    fun updateStartStage(stage: PathStage) {
        startStage = stage
        prefs.edit().putString(KEY_START_STAGE, stage.name).apply()
    }

    private companion object {
        const val KEY_READING_SCALE = "reading_scale"
        const val KEY_START_STAGE = "start_stage"
    }
}

enum class PathStage(val label: String) {
    ELEMENTARY("از دبستان (از صفرِ صفر)"),
    MIDDLE("از متوسطه اول"),
    HIGH("از متوسطه دوم و کنکور")
}

val LocalCalmPrefs = staticCompositionLocalOf<CalmPrefs> { error("CalmPrefs not provided") }

/**
 * تم آرام. باید داخل MyApplicationTheme قرار بگیرد تا LocalAppThemeController
 * (حالت روشن/تیره و تنظیمات قدیمی) در دسترس بماند.
 *
 * همهٔ تنظیمات ظاهری از AppThemeController خوانده می‌شوند تا تغییرشان در صفحهٔ
 * تنظیمات واقعاً اعمال شود (پیش‌تر رنگ و فونت اینجا ثابت بود و تنظیمات بی‌اثر
 * می‌ماند): پالت رنگی و رنگ پویا لهجهٔ برنامه را می‌سازند، فونت و اندازه و
 * فاصلهٔ خطوط و پررنگی تایپوگرافی را می‌سازند، و زبان جهت چیدمان را تعیین می‌کند.
 * پس‌زمینه و سطح‌های خنثی آرام (بدون گرادیان) حفظ شده‌اند.
 */
@Composable
fun CalmTheme(content: @Composable () -> Unit) {
    val controller = LocalAppThemeController.current
    val dark = controller.resolveIsDarkTheme(isSystemInDarkTheme())
    val context = LocalContext.current
    val prefs = remember { CalmPrefs(context) }

    // رنگ: پایه از پالت انتخابی یا رنگ پویای سیستم می‌آید؛ سطح‌های خنثی آرام ثابت‌اند.
    val baseScheme = if (controller.useDynamicColor && android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.S) {
        if (dark) androidx.compose.material3.dynamicDarkColorScheme(context) else androidx.compose.material3.dynamicLightColorScheme(context)
    } else {
        val palette = controller.selectedColorPalette
        if (dark) palette.toDarkColorScheme() else palette.toLightColorScheme()
    }
    val calmNeutrals = if (dark) CalmDark else CalmLight
    val colorScheme = baseScheme.copy(
        background = calmNeutrals.background,
        onBackground = calmNeutrals.onBackground,
        surface = calmNeutrals.surface,
        onSurface = calmNeutrals.onSurface,
        surfaceVariant = calmNeutrals.surfaceVariant,
        onSurfaceVariant = calmNeutrals.onSurfaceVariant,
        surfaceTint = Color.Transparent,
        outline = calmNeutrals.outline,
        outlineVariant = calmNeutrals.outlineVariant,
        inverseSurface = calmNeutrals.inverseSurface,
        inverseOnSurface = calmNeutrals.inverseOnSurface,
        scrim = calmNeutrals.scrim
    )

    // تایپوگرافی: فونت، اندازه، فاصلهٔ خطوط و پررنگی از تنظیمات می‌آیند.
    val fontFamily = controller.customFontFamily
        ?: controller.selectedFontPreset.toFontFamily()
    val typography = remember(
        fontFamily, controller.fontScale, controller.lineSpacingScale,
        controller.fontWeightLevel, controller.fontRevision
    ) {
        calmTypography(
            fontFamily = fontFamily,
            fontScale = controller.fontScale,
            lineSpacingScale = controller.lineSpacingScale,
            fontWeightLevel = controller.fontWeightLevel
        )
    }

    val layoutDirection = if (controller.appLanguage == "en") LayoutDirection.Ltr else LayoutDirection.Rtl
    CompositionLocalProvider(
        LocalLayoutDirection provides layoutDirection,
        LocalCalmPrefs provides prefs
    ) {
        MaterialTheme(
            colorScheme = colorScheme,
            typography = typography,
            shapes = CalmShapes
        ) {
            ProvideTextStyle(typography.bodyMedium) { content() }
        }
    }
}
