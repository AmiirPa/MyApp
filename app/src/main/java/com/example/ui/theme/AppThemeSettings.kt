package com.example.ui.theme

import android.content.Context
import android.graphics.Typeface
import android.net.Uri
import android.provider.OpenableColumns
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.font.Typeface as ComposeTypeface
import com.example.ui.components.BidiFormatter
import android.os.Build
import java.io.File
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

import java.util.Calendar

enum class AppThemeMode(val persianLabel: String, val shortTitle: String) {
    SYSTEM("خودکار (تنظیمات گوشی / صبح سفید، شب سیاه)", "خودکار"),
    LIGHT("حالت صبح و روز (سفید روشن)", "روز (سفید)"),
    DARK("حالت شب (سیاه یکدست)", "شب (سیاه)")
}

/**
 * Color palettes for Arabic & Persian diacritics (اعراب: فتحه، کسره، ضمه، تنوین، سکون و تشدید).
 */
enum class DiacriticsColorPalette(
    val persianTitle: String,
    val shortTitle: String,
    val description: String,
    val previewColor: Color
) {
    EDUCATIONAL_MULTICOLOR(
        persianTitle = "تفکیک آموزشی ۴ رنگ (پیشنهادی عربی)",
        shortTitle = "۴ رنگ آموزشی",
        description = "ضمه (مرفوع): آبی • فتحه (منصوب): قرمز • کسره (مجرور): سبز • سکون/تشدید: نارنجی",
        previewColor = Color(0xFFE11D48)
    ),
    CRIMSON_RUBY(
        persianTitle = "قرمز یاقوتی (بسیار واضح)",
        shortTitle = "قرمز یاقوتی",
        description = "نمایش تمام حرکات و اعراب با رنگ قرمز یاقوتی پررنگ",
        previewColor = Color(0xFFDC2626)
    ),
    EMERALD_QURANIC(
        persianTitle = "سبز زمردی (سبک مصحف قرآنی)",
        shortTitle = "سبز زمردی",
        description = "نمایش تمام حرکات و اعراب با رنگ سبز زمردی چشم‌نواز",
        previewColor = Color(0xFF059669)
    ),
    ROYAL_CYAN(
        persianTitle = "آبی فیروزه‌ای درخشان",
        shortTitle = "آبی فیروزه‌ای",
        description = "نمایش تمام حرکات و اعراب با رنگ آبی فیروزه‌ای آرامش‌بخش",
        previewColor = Color(0xFF0284C7)
    ),
    GOLDEN_AMBER(
        persianTitle = "طلایی کهربایی",
        shortTitle = "طلایی",
        description = "نمایش تمام حرکات و اعراب با رنگ طلایی و نارنجی گرم",
        previewColor = Color(0xFFD97706)
    ),
    AMETHYST_PURPLE(
        persianTitle = "بنفش یاسی",
        shortTitle = "بنفش",
        description = "نمایش تمام حرکات و اعراب با رنگ بنفش پرکنتراست",
        previewColor = Color(0xFF7C3AED)
    );

    val id: String get() = name.lowercase()
    val title: String get() = persianTitle
    val subtitle: String get() = description

    fun colorForDiacritic(ch: Char, isDarkTheme: Boolean): Color = when (this) {
        EDUCATIONAL_MULTICOLOR -> when (ch) {
            '\u064F', '\u064C' -> if (isDarkTheme) Color(0xFF38BDF8) else Color(0xFF0284C7) // ضمه و تنوین رفع (آبی)
            '\u064E', '\u064B', '\u0670' -> if (isDarkTheme) Color(0xFFFB7185) else Color(0xFFE11D48) // فتحه و تنوین نصب (قرمز)
            '\u0650', '\u064D' -> if (isDarkTheme) Color(0xFF34D399) else Color(0xFF059669) // کسره و تنوین جر (سبز)
            else -> if (isDarkTheme) Color(0xFFFBBF24) else Color(0xFFD97706) // سکون و تشدید (نارنجی/طلایی)
        }
        CRIMSON_RUBY -> if (isDarkTheme) Color(0xFFF87171) else Color(0xFFDC2626)
        EMERALD_QURANIC -> if (isDarkTheme) Color(0xFF34D399) else Color(0xFF059669)
        ROYAL_CYAN -> if (isDarkTheme) Color(0xFF38BDF8) else Color(0xFF0284C7)
        GOLDEN_AMBER -> if (isDarkTheme) Color(0xFFFBBF24) else Color(0xFFD97706)
        AMETHYST_PURPLE -> if (isDarkTheme) Color(0xFFC084FC) else Color(0xFF7C3AED)
    }
}

data class BugLogEntry(
    val id: String,
    val timestamp: String,
    val category: String,
    val description: String
)

enum class HighlighterColorPalette(
    val persianTitle: String,
    val shortTitle: String,
    val previewColor: Color,
    val lightBgColor: Color,
    val darkBgColor: Color,
    val lightFgColor: Color,
    val darkFgColor: Color
) {
    SUNNY_YELLOW(
        persianTitle = "زرد کهربایی ملایم (استاندارد)",
        shortTitle = "زرد طلایی",
        previewColor = Color(0xFFD97706),
        lightBgColor = Color(0xFFFEF3C7),
        darkBgColor = Color(0xFF451A03),
        lightFgColor = Color(0xFF451A03),
        darkFgColor = Color(0xFFFEF3C7)
    ),
    MINT_EMERALD(
        persianTitle = "سبز نعنایی ملایم",
        shortTitle = "سبز نعنایی",
        previewColor = Color(0xFF059669),
        lightBgColor = Color(0xFFD1FAE5),
        darkBgColor = Color(0xFF064E3B),
        lightFgColor = Color(0xFF064E3B),
        darkFgColor = Color(0xFFD1FAE5)
    ),
    SKY_CYAN(
        persianTitle = "آبی آسمانی ملایم",
        shortTitle = "آبی آسمانی",
        previewColor = Color(0xFF0284C7),
        lightBgColor = Color(0xFFE0F2FE),
        darkBgColor = Color(0xFF0C4A6E),
        lightFgColor = Color(0xFF0C4A6E),
        darkFgColor = Color(0xFFE0F2FE)
    ),
    CORAL_PEACH(
        persianTitle = "نارنجی هلویی ملایم",
        shortTitle = "نارنجی هلویی",
        previewColor = Color(0xFFEA580C),
        lightBgColor = Color(0xFFFFEDD5),
        darkBgColor = Color(0xFF431407),
        lightFgColor = Color(0xFF431407),
        darkFgColor = Color(0xFFFFEDD5)
    ),
    ROSE_PINK(
        persianTitle = "صورتی یاقوتی ملایم",
        shortTitle = "صورتی",
        previewColor = Color(0xFFE11D48),
        lightBgColor = Color(0xFFFFE4E6),
        darkBgColor = Color(0xFF4C0519),
        lightFgColor = Color(0xFF4C0519),
        darkFgColor = Color(0xFFFFE4E6)
    ),
    LAVENDER_PURPLE(
        persianTitle = "بنفش یاسی ملایم",
        shortTitle = "بنفش یاسی",
        previewColor = Color(0xFF7C3AED),
        lightBgColor = Color(0xFFEDE9FE),
        darkBgColor = Color(0xFF2E1065),
        lightFgColor = Color(0xFF2E1065),
        darkFgColor = Color(0xFFEDE9FE)
    );

    val id: String get() = name.lowercase()
    val title: String get() = shortTitle
    val previewSwatch: Color get() = previewColor
    val lightTextColor: Color get() = lightFgColor

    fun resolveBackground(isDarkTheme: Boolean, intensity: Int = 1): Color {
        if (intensity <= 0) return Color.Transparent
        val base = if (isDarkTheme) darkBgColor else lightBgColor
        val alpha = when (intensity) {
            1 -> if (isDarkTheme) 0.55f else 0.65f
            else -> if (isDarkTheme) 0.85f else 0.92f
        }
        return base.copy(alpha = alpha)
    }

    fun resolveTextColor(isDarkTheme: Boolean): Color = if (isDarkTheme) darkFgColor else lightFgColor
}

enum class UnderlineColorPalette(
    val persianTitle: String,
    val shortTitle: String,
    val previewColor: Color,
    val lightColor: Color,
    val darkColor: Color
) {
    ROYAL_BLUE(
        persianTitle = "آبی سلطنتی (استاندارد)",
        shortTitle = "آبی سلطنتی",
        previewColor = Color(0xFF2563EB),
        lightColor = Color(0xFF1D4ED8),
        darkColor = Color(0xFF60A5FA)
    ),
    CRIMSON_RED(
        persianTitle = "قرمز یاقوتی (تأکید بالا)",
        shortTitle = "قرمز یاقوتی",
        previewColor = Color(0xFFEF4444),
        lightColor = Color(0xFFDC2626),
        darkColor = Color(0xFFF87171)
    ),
    EMERALD_GREEN(
        persianTitle = "سبز زمردی",
        shortTitle = "سبز زمردی",
        previewColor = Color(0xFF059669),
        lightColor = Color(0xFF047857),
        darkColor = Color(0xFF34D399)
    ),
    GOLDEN_AMBER(
        persianTitle = "طلایی کهربایی",
        shortTitle = "طلایی",
        previewColor = Color(0xFFD97706),
        lightColor = Color(0xFFB45309),
        darkColor = Color(0xFFFBBF24)
    ),
    ROYAL_PURPLE(
        persianTitle = "بنفش شاهانه",
        shortTitle = "بنفش",
        previewColor = Color(0xFF7C3AED),
        lightColor = Color(0xFF6D28D9),
        darkColor = Color(0xFFC084FC)
    ),
    TEAL_CYAN(
        persianTitle = "فیروزه‌ای مدرن",
        shortTitle = "فیروزه‌ای",
        previewColor = Color(0xFF0891B2),
        lightColor = Color(0xFF0E7490),
        darkColor = Color(0xFF22D3EE)
    );

    val id: String get() = name.lowercase()
    val title: String get() = shortTitle

    fun resolveColor(isDarkTheme: Boolean): Color = if (isDarkTheme) darkColor else lightColor
}

enum class TextAlignmentPreset(val persianLabel: String, val shortTitle: String) {
    SMART_AUTO("هوشمند استاندارد (فارسی راست‌چین • فرمول و انگلیسی چپ‌چین • تیتر وسط‌چین)", "هوشمند"),
    RIGHT("راست‌چین کامل (Right Align)", "راست‌چین"),
    CENTER("وسط‌چین (Center Align)", "وسط‌چین"),
    LEFT("چپ‌چین (Left Align)", "چپ‌چین");

    val id: String get() = name.lowercase()
    val title: String get() = shortTitle
}

@Stable
class AppThemeController(context: Context) {
    private val appContext = context.applicationContext
    private val prefs = appContext.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

    var themeMode by mutableStateOf(loadThemeMode())
        private set

    var selectedColorPalette by mutableStateOf(loadColorPalette())
        private set

    // Default to false so the vibrant custom palette is immediately visible; user can toggle Dynamic Color anytime
    var useDynamicColor by mutableStateOf(prefs.getBoolean(KEY_DYNAMIC_COLOR, false))
        private set

    var usePersianDigits by mutableStateOf(prefs.getBoolean(KEY_USE_PERSIAN_DIGITS, true))
        private set

    var appLanguage by mutableStateOf(prefs.getString(KEY_APP_LANGUAGE, "fa") ?: "fa")
        private set

    var selectedFontPreset by mutableStateOf(loadFontPreset())
        private set

    var customFontPath by mutableStateOf(prefs.getString(KEY_CUSTOM_FONT_PATH, null))
        private set

    var customFontName by mutableStateOf(prefs.getString(KEY_CUSTOM_FONT_NAME, null))
        private set

    var fontScale by mutableFloatStateOf(prefs.getFloat(KEY_FONT_SCALE, 0.94f))
        private set

    var textVerticalSpacingDp by mutableFloatStateOf(prefs.getFloat(KEY_TEXT_VERTICAL_SPACING, 1.5f))
        private set

    var lineSpacingScale by mutableFloatStateOf(prefs.getFloat(KEY_LINE_SPACING_SCALE, 1.0f))
        private set

    var isBoldTextEnabled by mutableStateOf(prefs.getBoolean(KEY_IS_BOLD_TEXT, false))
        private set

    // 0 = معمولی (Normal), 1 = نیمه‌پررنگ (Medium/SemiBold), 2 = پررنگ (Bold)
    var fontWeightLevel by mutableIntStateOf(
        prefs.getInt(KEY_FONT_WEIGHT_LEVEL, if (prefs.getBoolean(KEY_IS_BOLD_TEXT, false)) 2 else 0).coerceIn(0, 2)
    )
        private set

    var isUnderlineKeyPointsEnabled by mutableStateOf(prefs.getBoolean(KEY_UNDERLINE_KEY_POINTS, true))
        private set

    var isHighlighterEnabled by mutableStateOf(prefs.getBoolean(KEY_HIGHLIGHTER_ENABLED, true))
        private set

    // 0 = خاموش (Off), 1 = ملایم (Subtle - default), 2 = متوسط (Medium)
    var highlightIntensity by mutableIntStateOf(
        prefs.getInt(KEY_HIGHLIGHT_INTENSITY, if (prefs.getBoolean(KEY_HIGHLIGHTER_ENABLED, true)) 1 else 0).coerceIn(0, 2)
    )
        private set

    // 0 = شنبه (Saturday), 1 = یکشنبه (Sunday), 2 = دوشنبه (Monday)
    var weekStartDay by mutableIntStateOf(prefs.getInt(KEY_WEEK_START_DAY, 0).coerceIn(0, 2))
        private set

    var isColoredDiacriticsEnabled by mutableStateOf(prefs.getBoolean(KEY_COLORED_DIACRITICS, true))
        private set

    var arabicDiacriticsScale by mutableFloatStateOf(prefs.getFloat(KEY_ARABIC_DIACRITICS_SCALE, 1.0f))
        private set

    var arabicDedicatedFont by mutableStateOf(loadArabicDedicatedFont())
        private set

    var selectedDiacriticsPalette by mutableStateOf(loadDiacriticsPalette())
        private set

    var selectedHighlighterPalette by mutableStateOf(loadHighlighterPalette())
        private set

    var selectedUnderlinePalette by mutableStateOf(loadUnderlinePalette())
        private set

    var textAlignmentPreset by mutableStateOf(loadTextAlignmentPreset())
        private set

    var customFontFamily by mutableStateOf<FontFamily?>(loadFontFamilyFromPath(customFontPath, isBoldTextEnabled))
        private set

    var bugLogs by mutableStateOf(loadBugLogs())
        private set

    var fontRevision by mutableIntStateOf(0)
        private set

    // الف) تنظیمات یادگیری (Part 13A)
    var learningStartLevel by mutableStateOf(prefs.getString(KEY_LEARNING_START_LEVEL, "MINUS_ZERO") ?: "MINUS_ZERO")
        private set

    var learningPaceMode by mutableStateOf(prefs.getString(KEY_LEARNING_PACE_MODE, "SLOW") ?: "SLOW")
        private set

    var explanationDetailLevel by mutableStateOf(prefs.getString(KEY_EXPLANATION_DETAIL_LEVEL, "COMPLETE") ?: "COMPLETE")
        private set

    var showPrerequisitesBeforeLesson by mutableStateOf(prefs.getBoolean(KEY_SHOW_PREREQUISITES, true))
        private set

    var showUnderstandingCheck by mutableStateOf(prefs.getBoolean(KEY_SHOW_UNDERSTANDING_CHECK, true))
        private set

    var showMnemonicsAndMemoryTricks by mutableStateOf(prefs.getBoolean(KEY_SHOW_MNEMONICS, true))
        private set

    // ب) تنظیمات نمایش جدول و کاهش حرکت (Part 13B & 13G)
    var tableDisplayPreference by mutableStateOf(prefs.getString(KEY_TABLE_DISPLAY_PREF, "AUTO") ?: "AUTO")
        private set

    var reduceMotionEnabled by mutableStateOf(prefs.getBoolean(KEY_REDUCE_MOTION, false))
        private set

    // ج و د) تنظیمات سرعت خواندن صوتی و هدف مطالعه روزانه (Part 13C & 13D)
    var ttsSpeechRate by mutableFloatStateOf(prefs.getFloat(KEY_TTS_SPEECH_RATE, 0.9f))
        private set

    var dailyStudyGoalMinutes by mutableIntStateOf(prefs.getInt(KEY_DAILY_STUDY_GOAL_MIN, 120))
        private set

    init {
        BidiFormatter.setDigitMode(usePersianDigits)
    }

    private fun loadHighlighterPalette(): HighlighterColorPalette {
        val saved = prefs.getString(KEY_HIGHLIGHTER_PALETTE, HighlighterColorPalette.SUNNY_YELLOW.name)
        return HighlighterColorPalette.entries.find { it.name == saved } ?: HighlighterColorPalette.SUNNY_YELLOW
    }

    private fun loadUnderlinePalette(): UnderlineColorPalette {
        val saved = prefs.getString(KEY_UNDERLINE_PALETTE, UnderlineColorPalette.ROYAL_BLUE.name)
        return UnderlineColorPalette.entries.find { it.name == saved } ?: UnderlineColorPalette.ROYAL_BLUE
    }

    fun selectHighlighterPalette(palette: HighlighterColorPalette) {
        selectedHighlighterPalette = palette
        isHighlighterEnabled = true
        fontRevision++
        prefs.edit()
            .putString(KEY_HIGHLIGHTER_PALETTE, palette.name)
            .putBoolean(KEY_HIGHLIGHTER_ENABLED, true)
            .apply()
    }

    fun selectUnderlinePalette(palette: UnderlineColorPalette) {
        selectedUnderlinePalette = palette
        isUnderlineKeyPointsEnabled = true
        fontRevision++
        prefs.edit()
            .putString(KEY_UNDERLINE_PALETTE, palette.name)
            .putBoolean(KEY_UNDERLINE_KEY_POINTS, true)
            .apply()
    }

    private fun loadTextAlignmentPreset(): TextAlignmentPreset {
        val saved = prefs.getString(KEY_TEXT_ALIGNMENT_PRESET, TextAlignmentPreset.SMART_AUTO.name)
        return TextAlignmentPreset.entries.find { it.name == saved } ?: TextAlignmentPreset.SMART_AUTO
    }

    fun updateTextAlignmentPreset(preset: TextAlignmentPreset) {
        textAlignmentPreset = preset
        fontRevision++
        prefs.edit().putString(KEY_TEXT_ALIGNMENT_PRESET, preset.name).apply()
    }

    fun selectTextAlignmentPreset(preset: TextAlignmentPreset) {
        updateTextAlignmentPreset(preset)
    }

    private fun loadThemeMode(): AppThemeMode {
        val saved = prefs.getString(KEY_THEME_MODE, AppThemeMode.SYSTEM.name)
        return AppThemeMode.entries.find { it.name == saved } ?: AppThemeMode.SYSTEM
    }

    private fun loadColorPalette(): AppColorPalette {
        val saved = prefs.getString(KEY_COLOR_PALETTE, AppColorPalette.PIXEL_EXPRESSIVE_17.name)
        return AppColorPalette.entries.find { it.name == saved } ?: AppColorPalette.PIXEL_EXPRESSIVE_17
    }

    private fun loadFontPreset(): BuiltInFontPreset {
        val saved = prefs.getString(KEY_FONT_PRESET, BuiltInFontPreset.VAZIRMATN.name)
        return BuiltInFontPreset.entries.find { it.name == saved } ?: BuiltInFontPreset.VAZIRMATN
    }

    private fun loadDiacriticsPalette(): DiacriticsColorPalette {
        val saved = prefs.getString(KEY_DIACRITICS_PALETTE, DiacriticsColorPalette.EDUCATIONAL_MULTICOLOR.name)
        return DiacriticsColorPalette.entries.find { it.name == saved }
            ?: DiacriticsColorPalette.EDUCATIONAL_MULTICOLOR
    }

    private fun loadFontFamilyFromPath(path: String?, forceBold: Boolean = false): FontFamily? {
        if (path.isNullOrBlank()) return null
        val file = File(path)
        if (!file.exists() || file.length() == 0L) return null
        return runCatching {
            val baseTypeface = Typeface.createFromFile(file)
            require(baseTypeface != null)
            FontFamily(
                Font(file = file, weight = if (forceBold) FontWeight.Bold else FontWeight.Normal)
            )
        }.recoverCatching {
            val nativeTypeface = Typeface.createFromFile(file)
            val tf = if (forceBold) Typeface.create(nativeTypeface, Typeface.BOLD) else nativeTypeface
            FontFamily(ComposeTypeface(tf))
        }.getOrNull()
    }

    fun resolveIsDarkTheme(systemInDarkTheme: Boolean): Boolean {
        return when (themeMode) {
            AppThemeMode.LIGHT -> false
            AppThemeMode.DARK -> true
            AppThemeMode.SYSTEM -> systemInDarkTheme
        }
    }

    fun cycleThemeMode() {
        val next = when (themeMode) {
            AppThemeMode.SYSTEM -> AppThemeMode.LIGHT
            AppThemeMode.LIGHT -> AppThemeMode.DARK
            AppThemeMode.DARK -> AppThemeMode.SYSTEM
        }
        updateThemeMode(next)
    }

    fun updateThemeMode(mode: AppThemeMode) {
        themeMode = mode
        prefs.edit().putString(KEY_THEME_MODE, mode.name).apply()
    }

    fun selectColorPalette(palette: AppColorPalette) {
        selectedColorPalette = palette
        prefs.edit()
            .putString(KEY_COLOR_PALETTE, palette.name)
            .apply()
    }

    fun updateDynamicColor(enabled: Boolean) {
        useDynamicColor = enabled
        prefs.edit().putBoolean(KEY_DYNAMIC_COLOR, enabled).apply()
    }

    fun updatePersianDigits(enabled: Boolean) {
        usePersianDigits = enabled
        BidiFormatter.setDigitMode(enabled)
        fontRevision++
        prefs.edit().putBoolean(KEY_USE_PERSIAN_DIGITS, enabled).apply()
    }

    fun updateAppLanguage(lang: String) {
        val normalized = if (lang.lowercase() == "en") "en" else "fa"
        appLanguage = normalized
        fontRevision++
        prefs.edit().putString(KEY_APP_LANGUAGE, normalized).apply()
    }

    fun tr(fa: String, en: String): String = if (appLanguage == "en") en else fa

    fun selectBuiltInFontPreset(preset: BuiltInFontPreset) {
        selectedFontPreset = preset
        customFontFamily = null
        customFontPath = null
        customFontName = null
        fontRevision++
        prefs.edit()
            .putString(KEY_FONT_PRESET, preset.name)
            .remove(KEY_CUSTOM_FONT_PATH)
            .remove(KEY_CUSTOM_FONT_NAME)
            .apply()
    }

    fun updateFontScale(scale: Float) {
        val clamped = scale.coerceIn(0.80f, 1.35f)
        fontScale = clamped
        fontRevision++
        prefs.edit().putFloat(KEY_FONT_SCALE, clamped).apply()
    }

    fun updateTextVerticalSpacing(spacingDp: Float) {
        val clamped = spacingDp.coerceIn(0f, 14f)
        textVerticalSpacingDp = clamped
        fontRevision++
        prefs.edit().putFloat(KEY_TEXT_VERTICAL_SPACING, clamped).apply()
    }

    fun updateLineSpacingScale(scale: Float) {
        val clamped = scale.coerceIn(0.85f, 1.50f)
        lineSpacingScale = clamped
        fontRevision++
        prefs.edit().putFloat(KEY_LINE_SPACING_SCALE, clamped).apply()
    }

    fun updateBoldText(enabled: Boolean) {
        val level = if (enabled) 2 else 0
        updateFontWeightLevel(level)
    }

    fun updateFontWeightLevel(level: Int) {
        val clamped = level.coerceIn(0, 2)
        fontWeightLevel = clamped
        isBoldTextEnabled = clamped == 2
        if (!customFontPath.isNullOrBlank()) {
            customFontFamily = loadFontFamilyFromPath(customFontPath, forceBold = isBoldTextEnabled)
        }
        fontRevision++
        prefs.edit()
            .putInt(KEY_FONT_WEIGHT_LEVEL, clamped)
            .putBoolean(KEY_IS_BOLD_TEXT, isBoldTextEnabled)
            .apply()
    }

    fun updateUnderlineKeyPoints(enabled: Boolean) {
        isUnderlineKeyPointsEnabled = enabled
        fontRevision++
        prefs.edit().putBoolean(KEY_UNDERLINE_KEY_POINTS, enabled).apply()
    }

    fun updateHighlighter(enabled: Boolean) {
        val targetIntensity = if (enabled) {
            if (highlightIntensity == 0) 1 else highlightIntensity
        } else 0
        updateHighlightIntensity(targetIntensity)
    }

    fun updateHighlightIntensity(intensity: Int) {
        val clamped = intensity.coerceIn(0, 2)
        highlightIntensity = clamped
        isHighlighterEnabled = clamped > 0
        fontRevision++
        prefs.edit()
            .putInt(KEY_HIGHLIGHT_INTENSITY, clamped)
            .putBoolean(KEY_HIGHLIGHTER_ENABLED, isHighlighterEnabled)
            .apply()
    }

    fun updateWeekStartDay(day: Int) {
        val clamped = day.coerceIn(0, 2)
        weekStartDay = clamped
        prefs.edit().putInt(KEY_WEEK_START_DAY, clamped).apply()
    }

    fun updateColoredDiacritics(enabled: Boolean) {
        isColoredDiacriticsEnabled = enabled
        fontRevision++
        prefs.edit().putBoolean(KEY_COLORED_DIACRITICS, enabled).apply()
    }

    fun selectDiacriticsPalette(palette: DiacriticsColorPalette) {
        selectedDiacriticsPalette = palette
        isColoredDiacriticsEnabled = true
        fontRevision++
        prefs.edit()
            .putString(KEY_DIACRITICS_PALETTE, palette.name)
            .putBoolean(KEY_COLORED_DIACRITICS, true)
            .apply()
    }

    private fun loadBugLogs(): List<BugLogEntry> {
        val raw = prefs.getString(KEY_BUG_LOGS, null) ?: return emptyList()
        return raw.split("\n---BUG_ENTRY---\n").mapNotNull { chunk ->
            val parts = chunk.split("|||", limit = 4)
            if (parts.size == 4) {
                BugLogEntry(
                    id = parts[0],
                    timestamp = parts[1],
                    category = parts[2],
                    description = parts[3]
                )
            } else null
        }
    }

    private fun saveBugLogs(entries: List<BugLogEntry>) {
        val serialized = entries.take(30).joinToString("\n---BUG_ENTRY---\n") {
            "${it.id}|||${it.timestamp}|||${it.category}|||${it.description}"
        }
        prefs.edit().putString(KEY_BUG_LOGS, serialized).apply()
    }

    fun addBugLog(category: String, description: String) {
        if (description.isBlank()) return
        val timeStr = SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.US).format(Date())
        val newEntry = BugLogEntry(
            id = "bug_${System.currentTimeMillis()}",
            timestamp = timeStr,
            category = category,
            description = description.trim()
        )
        val updated = listOf(newEntry) + bugLogs
        bugLogs = updated.take(30)
        saveBugLogs(bugLogs)
    }

    fun clearBugLogs() {
        bugLogs = emptyList()
        prefs.edit().remove(KEY_BUG_LOGS).apply()
    }

    fun updateLearningStartLevel(level: String) {
        learningStartLevel = level
        prefs.edit().putString(KEY_LEARNING_START_LEVEL, level).apply()
    }

    fun updateLearningPaceMode(pace: String) {
        learningPaceMode = pace
        prefs.edit().putString(KEY_LEARNING_PACE_MODE, pace).apply()
    }

    fun updateExplanationDetailLevel(detail: String) {
        explanationDetailLevel = detail
        prefs.edit().putString(KEY_EXPLANATION_DETAIL_LEVEL, detail).apply()
    }

    fun updateShowPrerequisitesBeforeLesson(enabled: Boolean) {
        showPrerequisitesBeforeLesson = enabled
        prefs.edit().putBoolean(KEY_SHOW_PREREQUISITES, enabled).apply()
    }

    fun updateShowUnderstandingCheck(enabled: Boolean) {
        showUnderstandingCheck = enabled
        prefs.edit().putBoolean(KEY_SHOW_UNDERSTANDING_CHECK, enabled).apply()
    }

    fun updateShowMnemonicsAndMemoryTricks(enabled: Boolean) {
        showMnemonicsAndMemoryTricks = enabled
        prefs.edit().putBoolean(KEY_SHOW_MNEMONICS, enabled).apply()
    }

    fun updateTableDisplayPreference(pref: String) {
        tableDisplayPreference = pref
        fontRevision++
        prefs.edit().putString(KEY_TABLE_DISPLAY_PREF, pref).apply()
    }

    fun updateReduceMotion(enabled: Boolean) {
        reduceMotionEnabled = enabled
        prefs.edit().putBoolean(KEY_REDUCE_MOTION, enabled).apply()
    }

    fun updateTtsSpeechRate(rate: Float) {
        val clamped = rate.coerceIn(0.6f, 1.5f)
        ttsSpeechRate = clamped
        prefs.edit().putFloat(KEY_TTS_SPEECH_RATE, clamped).apply()
    }

    fun updateDailyStudyGoalMinutes(minutes: Int) {
        val clamped = minutes.coerceIn(30, 480)
        dailyStudyGoalMinutes = clamped
        prefs.edit().putInt(KEY_DAILY_STUDY_GOAL_MIN, clamped).apply()
    }

    fun clearTemporaryCache(): String {
        return runCatching {
            val cacheFiles = appContext.cacheDir?.listFiles().orEmpty()
            var count = 0
            cacheFiles.forEach { file ->
                if (file.isFile && file.delete()) count++
            }
            com.example.ui.components.ArabicTextProcessor.clearCacheIfNeeded()
            "حافظه موقت با موفقیت پاک‌سازی شد (${BidiFormatter.toPersianDigits(count.toString())} فایل موقت)."
        }.getOrDefault("حافظه موقت پاک‌سازی شد.")
    }

    fun resetAllAppearanceToStandard() {
        themeMode = AppThemeMode.SYSTEM
        selectedColorPalette = AppColorPalette.PIXEL_EXPRESSIVE_17
        useDynamicColor = false
        selectedFontPreset = BuiltInFontPreset.VAZIRMATN
        customFontFamily = null
        customFontPath = null
        customFontName = null
        fontScale = 0.94f
        textVerticalSpacingDp = 1.5f
        lineSpacingScale = 1.0f
        isBoldTextEnabled = false
        isUnderlineKeyPointsEnabled = true
        isHighlighterEnabled = true
        isColoredDiacriticsEnabled = true
        arabicDiacriticsScale = 1.0f
        arabicDedicatedFont = ArabicFontPreset.MATCH_APP
        selectedDiacriticsPalette = DiacriticsColorPalette.EDUCATIONAL_MULTICOLOR
        textAlignmentPreset = TextAlignmentPreset.SMART_AUTO
        usePersianDigits = true
        BidiFormatter.setDigitMode(true)
        bugLogs = emptyList()
        fontRevision++
        prefs.edit()
            .putString(KEY_THEME_MODE, AppThemeMode.SYSTEM.name)
            .putString(KEY_COLOR_PALETTE, AppColorPalette.PIXEL_EXPRESSIVE_17.name)
            .putBoolean(KEY_DYNAMIC_COLOR, false)
            .putString(KEY_FONT_PRESET, BuiltInFontPreset.VAZIRMATN.name)
            .remove(KEY_CUSTOM_FONT_PATH)
            .remove(KEY_CUSTOM_FONT_NAME)
            .putFloat(KEY_FONT_SCALE, 0.94f)
            .putFloat(KEY_TEXT_VERTICAL_SPACING, 1.5f)
            .putFloat(KEY_LINE_SPACING_SCALE, 1.0f)
            .putBoolean(KEY_IS_BOLD_TEXT, false)
            .putBoolean(KEY_UNDERLINE_KEY_POINTS, true)
            .putBoolean(KEY_HIGHLIGHTER_ENABLED, true)
            .putBoolean(KEY_COLORED_DIACRITICS, true)
            .putFloat(KEY_ARABIC_DIACRITICS_SCALE, 1.0f)
            .putString(KEY_ARABIC_DEDICATED_FONT, ArabicFontPreset.MATCH_APP.name)
            .putString(KEY_DIACRITICS_PALETTE, DiacriticsColorPalette.EDUCATIONAL_MULTICOLOR.name)
            .putString(KEY_TEXT_ALIGNMENT_PRESET, TextAlignmentPreset.SMART_AUTO.name)
            .putBoolean(KEY_USE_PERSIAN_DIGITS, true)
            .remove(KEY_BUG_LOGS)
            .apply()
    }

    fun buildDeviceDiagnosticsSummary(): String {
        val dm = appContext.resources.displayMetrics
        val fontName = customFontName ?: selectedFontPreset.persianTitle
        val logsText = if (bugLogs.isEmpty()) {
            "هیچ باگی ثبت نشده است."
        } else {
            bugLogs.mapIndexed { idx, b ->
                "${idx + 1}. [${b.timestamp}] (${b.category}): ${b.description}"
            }.joinToString("\n")
        }
        return buildString {
            appendLine("=== گزارش عیب‌یاب دستگاه و لاگ باگ (نسخه نصبی APK) ===")
            appendLine("مدل گوشی: ${Build.MANUFACTURER} ${Build.MODEL} (Android ${Build.VERSION.RELEASE} / SDK ${Build.VERSION.SDK_INT})")
            appendLine("تراکم صفحه: ${dm.widthPixels}x${dm.heightPixels}px (density=${dm.density})")
            appendLine("فونت فعال: $fontName | FontSynthesis: None (اتصال کامل حروف فارسی/عربی)")
            appendLine("سایز متن: ${(fontScale * 100).toInt()}% | تراز متن: ${textAlignmentPreset.shortTitle} | فاصله خطوط: ${(lineSpacingScale * 100).toInt()}%")
            appendLine("بولد: $isBoldTextEnabled | هایلایت: $isHighlighterEnabled | زیرخط: $isUnderlineKeyPointsEnabled | اعراب رنگی: $isColoredDiacriticsEnabled")
            appendLine("--- لیست باگ‌های ثبت‌شده توسط کاربر (${bugLogs.size} مورد) ---")
            appendLine(logsText)
        }
    }

    fun importCustomFontFromUri(uri: Uri): Result<String> {
        return runCatching {
            val displayName = queryDisplayName(uri) ?: "custom_font.ttf"
            val lower = displayName.lowercase()
            require(lower.endsWith(".ttf") || lower.endsWith(".otf")) {
                "لطفاً یک فایل فونت معتبر با پسوند .ttf یا .otf انتخاب کنید."
            }

            val fontsDir = File(appContext.filesDir, "custom_fonts").apply { mkdirs() }
            fontsDir.listFiles()?.forEach { it.delete() }
            val ext = if (lower.endsWith(".otf")) ".otf" else ".ttf"
            val targetFile = File(fontsDir, "user_font_${System.currentTimeMillis()}$ext")

            appContext.contentResolver.openInputStream(uri)?.use { input ->
                targetFile.outputStream().use { output ->
                    input.copyTo(output)
                }
            } ?: error("امکان خواندن فایل فونت انتخاب‌شده وجود ندارد.")

            val loadedFamily = loadFontFamilyFromPath(targetFile.absolutePath, forceBold = isBoldTextEnabled)
                ?: error("فایل انتخاب‌شده یک فونت استاندارد TTF یا OTF نیست.")

            customFontFamily = loadedFamily
            customFontPath = targetFile.absolutePath
            customFontName = displayName
            fontRevision++

            prefs.edit()
                .putString(KEY_CUSTOM_FONT_PATH, targetFile.absolutePath)
                .putString(KEY_CUSTOM_FONT_NAME, displayName)
                .apply()

            displayName
        }
    }

    fun resetToDefaultFont() {
        selectBuiltInFontPreset(BuiltInFontPreset.VAZIRMATN)
    }

    private fun queryDisplayName(uri: Uri): String? {
        return runCatching {
            appContext.contentResolver.query(uri, arrayOf(OpenableColumns.DISPLAY_NAME), null, null, null)
                ?.use { cursor ->
                    if (cursor.moveToFirst()) {
                        val idx = cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME)
                        if (idx >= 0) cursor.getString(idx) else null
                    } else null
                }
        }.getOrNull() ?: uri.lastPathSegment
    }

    fun updateArabicDiacriticsScale(scale: Float) {
        val clamped = scale.coerceIn(1.0f, 1.3f)
        arabicDiacriticsScale = clamped
        fontRevision++
        prefs.edit().putFloat(KEY_ARABIC_DIACRITICS_SCALE, clamped).apply()
    }

    fun selectArabicDedicatedFont(font: ArabicFontPreset) {
        arabicDedicatedFont = font
        val matchingBuiltIn = font.toMatchingBuiltInPreset()
        if (matchingBuiltIn != null) {
            selectedFontPreset = matchingBuiltIn
            customFontFamily = null
            customFontPath = null
            customFontName = null
            prefs.edit()
                .putString(KEY_ARABIC_DEDICATED_FONT, font.name)
                .putString(KEY_FONT_PRESET, matchingBuiltIn.name)
                .remove(KEY_CUSTOM_FONT_PATH)
                .remove(KEY_CUSTOM_FONT_NAME)
                .apply()
        } else {
            prefs.edit().putString(KEY_ARABIC_DEDICATED_FONT, font.name).apply()
        }
        fontRevision++
    }

    private fun loadArabicDedicatedFont(): ArabicFontPreset {
        val saved = prefs.getString(KEY_ARABIC_DEDICATED_FONT, ArabicFontPreset.MATCH_APP.name)
        return ArabicFontPreset.entries.find { it.name == saved } ?: ArabicFontPreset.MATCH_APP
    }

    companion object {
        private const val PREFS_NAME = "konkoor_expressive_theme_prefs"
        private const val KEY_THEME_MODE = "theme_mode_auto_system_v3"
        private const val KEY_COLOR_PALETTE = "color_palette"
        private const val KEY_DYNAMIC_COLOR = "dynamic_color"
        private const val KEY_USE_PERSIAN_DIGITS = "use_persian_digits"
        private const val KEY_FONT_PRESET = "font_preset_v35"
        private const val KEY_CUSTOM_FONT_PATH = "custom_font_path"
        private const val KEY_CUSTOM_FONT_NAME = "custom_font_name"
        private const val KEY_FONT_SCALE = "font_scale_v35"
        private const val KEY_TEXT_VERTICAL_SPACING = "text_vertical_spacing_dp_v35"
        private const val KEY_LINE_SPACING_SCALE = "line_spacing_scale_v35"
        private const val KEY_IS_BOLD_TEXT = "is_bold_text"
        private const val KEY_UNDERLINE_KEY_POINTS = "is_underline_key_points_v35"
        private const val KEY_HIGHLIGHTER_ENABLED = "is_highlighter_enabled_v35"
        private const val KEY_COLORED_DIACRITICS = "colored_diacritics_enabled_v35"
        private const val KEY_ARABIC_DIACRITICS_SCALE = "arabic_diacritics_scale_v35"
        private const val KEY_ARABIC_DEDICATED_FONT = "arabic_dedicated_font_v35"
        private const val KEY_DIACRITICS_PALETTE = "diacritics_color_palette"
        private const val KEY_HIGHLIGHTER_PALETTE = "highlighter_color_palette_v35"
        private const val KEY_UNDERLINE_PALETTE = "underline_color_palette_v35"
        private const val KEY_TEXT_ALIGNMENT_PRESET = "text_alignment_preset_v35"
        private const val KEY_BUG_LOGS = "user_bug_logs_v1"
        private const val KEY_LEARNING_START_LEVEL = "learning_start_level_v1"
        private const val KEY_LEARNING_PACE_MODE = "learning_pace_mode_v1"
        private const val KEY_EXPLANATION_DETAIL_LEVEL = "explanation_detail_level_v1"
        private const val KEY_SHOW_PREREQUISITES = "show_prerequisites_before_lesson_v1"
        private const val KEY_SHOW_UNDERSTANDING_CHECK = "show_understanding_check_v1"
        private const val KEY_SHOW_MNEMONICS = "show_mnemonics_v1"
        private const val KEY_TABLE_DISPLAY_PREF = "table_display_pref_v1"
        private const val KEY_REDUCE_MOTION = "reduce_motion_v1"
        private const val KEY_TTS_SPEECH_RATE = "tts_speech_rate_v1"
        private const val KEY_DAILY_STUDY_GOAL_MIN = "daily_study_goal_min_v1"
        private const val KEY_APP_LANGUAGE = "app_language_v1"
        private const val KEY_FONT_WEIGHT_LEVEL = "font_weight_level_v1"
        private const val KEY_HIGHLIGHT_INTENSITY = "highlight_intensity_v1"
        private const val KEY_WEEK_START_DAY = "week_start_day_v1"
    }
}

enum class ArabicFontPreset(
    val persianTitle: String,
    val shortTitle: String,
    val regularRes: Int?,
    val boldRes: Int? = regularRes
) {
    AMIRI(
        persianTitle = "امیری قرآنی (Amiri — خط نسخ عثمانی با حرکات درشت)",
        shortTitle = "امیری قرآنی",
        regularRes = com.example.R.font.amiri,
        boldRes = com.example.R.font.amiri_bold
    ),
    SCHEHERAZADE(
        persianTitle = "شهرزاد کتب درسی (Scheherazade — خط رسمی کتاب عربی)",
        shortTitle = "شهرزاد",
        regularRes = com.example.R.font.scheherazade_new,
        boldRes = com.example.R.font.scheherazade_bold
    ),
    ADOBE_ARABIC(
        persianTitle = "ادوبی عربیک اصلی (Adobe Arabic — رسمی و فوق‌العاده زیبا)",
        shortTitle = "ادوبی عربیک",
        regularRes = com.example.R.font.adobe_arabic,
        boldRes = com.example.R.font.adobe_arabic_bold
    ),
    HARMATTAN(
        persianTitle = "هارماتان نسخ (Harmattan — اعراب بسیار خوانا و تفکیک‌شده)",
        shortTitle = "هارماتان",
        regularRes = com.example.R.font.harmattan,
        boldRes = com.example.R.font.harmattan_bold
    ),
    LATEEF(
        persianTitle = "لطیف قرآنی (Lateef — خط نسخ لطیف و روان)",
        shortTitle = "لطیف",
        regularRes = com.example.R.font.lateef,
        boldRes = com.example.R.font.lateef
    ),
    NOON_QURANIC(
        persianTitle = "نون مصحف قرآنی (Noon — ویژه آیات و متون فصیح)",
        shortTitle = "نون قرآنی",
        regularRes = com.example.R.font.noon,
        boldRes = com.example.R.font.noon
    ),
    DROID_NASKH(
        persianTitle = "دروید نسخ عربی (Droid Arabic Naskh)",
        shortTitle = "دروید نسخ",
        regularRes = com.example.R.font.droid_naskh,
        boldRes = com.example.R.font.droid_naskh
    ),
    NOTO_KUFI(
        persianTitle = "کوفی هندسی عربی (Noto Kufi Arabic)",
        shortTitle = "کوفی عربی",
        regularRes = com.example.R.font.noto_kufi_arabic,
        boldRes = com.example.R.font.noto_kufi_arabic
    ),
    REEM_KUFI(
        persianTitle = "ریم کوفی مدرن (Reem Kufi)",
        shortTitle = "ریم کوفی",
        regularRes = com.example.R.font.reem_kufi,
        boldRes = com.example.R.font.reem_kufi
    ),
    AREF_RUQAA(
        persianTitle = "عارف رقعه خوشنویسی (Aref Ruqaa)",
        shortTitle = "عارف رقعه",
        regularRes = com.example.R.font.aref_ruqaa,
        boldRes = com.example.R.font.aref_ruqaa
    ),
    CAIRO(
        persianTitle = "قاهره مدرن عربی (Cairo)",
        shortTitle = "قاهره",
        regularRes = com.example.R.font.cairo,
        boldRes = com.example.R.font.cairo
    ),
    ALMARAI(
        persianTitle = "المراعی عربی (Almarai)",
        shortTitle = "المراعی",
        regularRes = com.example.R.font.almarai,
        boldRes = com.example.R.font.almarai
    ),
    TAJAWAL(
        persianTitle = "تجاول عربی (Tajawal)",
        shortTitle = "تجاول",
        regularRes = com.example.R.font.tajawal,
        boldRes = com.example.R.font.tajawal
    ),
    EL_MESSIRI(
        persianTitle = "المسیری هنری عربی (El Messiri)",
        shortTitle = "المسیری",
        regularRes = com.example.R.font.el_messiri,
        boldRes = com.example.R.font.el_messiri
    ),
    IBM_PLEX_ARABIC(
        persianTitle = "آی‌بی‌ام پلکس عربی (IBM Plex Sans Arabic)",
        shortTitle = "آی‌بی‌ام عربی",
        regularRes = com.example.R.font.ibm_plex_sans_arabic,
        boldRes = com.example.R.font.ibm_plex_sans_arabic
    ),
    READEX_PRO(
        persianTitle = "ریدکس پرو عربی (Readex Pro)",
        shortTitle = "ریدکس پرو",
        regularRes = com.example.R.font.readex_pro,
        boldRes = com.example.R.font.readex_pro
    ),
    MATCH_APP(
        persianTitle = "هماهنگ با فونت فارسی برنامه (وزیرمتن)",
        shortTitle = "فونت برنامه",
        regularRes = null,
        boldRes = null
    );

    fun toFontFamily(forceBold: Boolean = false): FontFamily? {
        val reg = regularRes ?: return null
        val bld = boldRes ?: reg
        return createCompleteFontFamily(
            regularRes = reg,
            boldRes = bld,
            forceBold = forceBold
        )
    }

    fun toMatchingBuiltInPreset(): BuiltInFontPreset? = when (this) {
        AMIRI -> BuiltInFontPreset.AMIRI
        SCHEHERAZADE -> BuiltInFontPreset.SCHEHERAZADE_NEW
        ADOBE_ARABIC -> BuiltInFontPreset.ADOBE_ARABIC
        HARMATTAN -> BuiltInFontPreset.HARMATTAN
        LATEEF -> BuiltInFontPreset.LATEEF_ARABIC
        NOON_QURANIC -> BuiltInFontPreset.NOON_QURANIC
        DROID_NASKH -> BuiltInFontPreset.DROID_NASKH
        NOTO_KUFI -> BuiltInFontPreset.NOTO_KUFI_ARABIC
        REEM_KUFI -> BuiltInFontPreset.REEM_KUFI_ARABIC
        AREF_RUQAA -> BuiltInFontPreset.AREF_RUQAA_ARABIC
        CAIRO -> BuiltInFontPreset.CAIRO_ARABIC
        ALMARAI -> BuiltInFontPreset.ALMARAI_ARABIC
        TAJAWAL -> BuiltInFontPreset.TAJAWAL_ARABIC
        EL_MESSIRI -> BuiltInFontPreset.EL_MESSIRI_ARABIC
        IBM_PLEX_ARABIC -> BuiltInFontPreset.IBM_PLEX_ARABIC
        READEX_PRO -> BuiltInFontPreset.READEX_PRO_ARABIC
        MATCH_APP -> BuiltInFontPreset.VAZIRMATN
    }
}

val LocalAppThemeController = staticCompositionLocalOf<AppThemeController> {
    error("AppThemeController not provided")
}
