package com.example.ui.theme

import androidx.compose.material3.ColorScheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.ui.graphics.Color

// Minimalist Academic Study Design System Tokens
val StudyBackground = Color(0xFFF6F7F9)
val StudySurface = Color(0xFFFFFFFF)
val StudyPrimary = Color(0xFF243B63)
val StudySecondary = Color(0xFF58769C)
val StudyText = Color(0xFF202938)
val StudyMuted = Color(0xFF7B8493)
val StudyBorder = Color(0xFFE6EAF0)
val StudySuccess = Color(0xFF38836B)

// Default Palette 1: Turquoise & Persian Gold (فیروزه‌ای و طلایی شاد ایرانی)
val PrimaryLight = Color(0xFF243B63)
val OnPrimaryLight = Color(0xFFFFFFFF)
val PrimaryContainerLight = Color(0xFFDCE4F2)
val OnPrimaryContainerLight = Color(0xFF10203B)

val SecondaryLight = Color(0xFF58769C)
val OnSecondaryLight = Color(0xFFFFFFFF)
val SecondaryContainerLight = Color(0xFFE3ECF7)
val OnSecondaryContainerLight = Color(0xFF1B2E47)

val TertiaryLight = Color(0xFF38836B)
val OnTertiaryLight = Color(0xFFFFFFFF)
val TertiaryContainerLight = Color(0xFFD5F2E8)
val OnTertiaryContainerLight = Color(0xFF0B3B2C)

val BackgroundLight = Color(0xFFF6F7F9)
val OnBackgroundLight = Color(0xFF202938)
val SurfaceLight = Color(0xFFFFFFFF)
val OnSurfaceLight = Color(0xFF202938)
val SurfaceVariantLight = Color(0xFFEEF1F6)
val OnSurfaceVariantLight = Color(0xFF5A6578)

// Dark Theme Palette 1
val PrimaryDark = Color(0xFF9BB8E8)
val OnPrimaryDark = Color(0xFF0F2342)
val PrimaryContainerDark = Color(0xFF243B63)
val OnPrimaryContainerDark = Color(0xFFDCE4F2)

val SecondaryDark = Color(0xFFAEC6E8)
val OnSecondaryDark = Color(0xFF1B2E47)
val SecondaryContainerDark = Color(0xFF384E6B)
val OnSecondaryContainerDark = Color(0xFFE3ECF7)

val TertiaryDark = Color(0xFF76CBB0)
val OnTertiaryDark = Color(0xFF083828)
val TertiaryContainerDark = Color(0xFF1F5E4A)
val OnTertiaryContainerDark = Color(0xFFD5F2E8)

val BackgroundDark = Color(0xFF111621)
val OnBackgroundDark = Color(0xFFE4E8F0)
val SurfaceDark = Color(0xFF19202E)
val OnSurfaceDark = Color(0xFFE4E8F0)
val SurfaceVariantDark = Color(0xFF242D3F)
val OnSurfaceVariantDark = Color(0xFFA9B4C4)

// Subject Accent Colors for Visual Differentiation (Harmonized with Minimalist Academic Theme)
val SubjectMathColor = Color(0xFF243B63)       // Deep Academic Navy
val SubjectFonoonColor = Color(0xFF4A5568)     // Slate Grey-Blue
val SubjectAroozColor = Color(0xFF38836B)      // Academic Sage Green
val SubjectArabicColor = Color(0xFF2B6CB0)     // Classic Blue
val SubjectEnglishColor = Color(0xFF58769C)    // Muted Steel Blue
val SubjectPhilosophyColor = Color(0xFF2C5282) // Royal Navy
val SubjectFarhangianColor = Color(0xFF2F855A) // Deep Emerald

/**
 * Material 3 Color Palettes selectable by the user.
 */
enum class AppColorPalette(
    val persianTitle: String,
    val englishTitle: String,
    val persianSubtitle: String,
    val previewPrimary: Color,
    val previewSecondary: Color,
    val previewTertiary: Color,
    val isFeatured: Boolean = false
) {
    PIXEL_EXPRESSIVE_17(
        persianTitle = "دفتر مطالعه کلاسیک سرمه‌ای (پیش‌فرض)",
        englishTitle = "Academic Navy & Sage",
        persianSubtitle = "سرمه‌ای رسمی، سبز یشمی و پس‌زمینه روشن با کنتراست استاندارد",
        previewPrimary = Color(0xFF243B63),
        previewSecondary = Color(0xFF58769C),
        previewTertiary = Color(0xFF38836B),
        isFeatured = true
    ),
    TURQUOISE_GOLD(
        persianTitle = "فیروزه‌ای ایرانی و کهربایی",
        englishTitle = "Persian Turquoise & Amber",
        persianSubtitle = "ترکیب فیروزه‌ای اصیل، کهربایی گرم و سبز زمردی با تفکیک بصری بالا",
        previewPrimary = Color(0xFF00677F),
        previewSecondary = Color(0xFFD97706),
        previewTertiary = Color(0xFF1F7A4C),
        isFeatured = true
    ),
    PSYCHOLOGICAL_SAGE(
        persianTitle = "سبز مریم‌گلی و فیروزه‌ای",
        englishTitle = "Calm Sage & Teal",
        persianSubtitle = "طیف سبز ملایم و فیروزه‌ای تیره مناسب مطالعه طولانی‌مدت",
        previewPrimary = Color(0xFF1E5B42),
        previewSecondary = Color(0xFFB45309),
        previewTertiary = Color(0xFF0D9488),
        isFeatured = true
    ),
    FARHANGIAN_CRIMSON_GOLD(
        persianTitle = "زرشکی و طلایی فرهنگیان",
        englishTitle = "Farhangian Crimson & Gold",
        persianSubtitle = "ترکیب زرشکی رسمی، کهربایی و سبز تیره هماهنگ با هویت بصری برنامه",
        previewPrimary = Color(0xFF9F1239),
        previewSecondary = Color(0xFFD97706),
        previewTertiary = Color(0xFF047857),
        isFeatured = true
    ),
    KETABRAH_FIDIBO_PAPER(
        persianTitle = "کاغذی گرم کتابخانه (سپیا)",
        englishTitle = "Warm Sepia Paper",
        persianSubtitle = "پس‌زمینه کرم کاغذی با متن قهوه‌ای تیره برای محیط‌های کم‌نور",
        previewPrimary = Color(0xFF7C3E1D),
        previewSecondary = Color(0xFFC67C24),
        previewTertiary = Color(0xFF2D6A4F),
        isFeatured = true
    ),
    EMERALD_SPRING(
        persianTitle = "زمردی و نارنجی گرم",
        englishTitle = "Emerald & Warm Orange",
        persianSubtitle = "سبز زمردی به همراه نارنجی تیره و آبی آسمانی",
        previewPrimary = Color(0xFF059669),
        previewSecondary = Color(0xFFEA580C),
        previewTertiary = Color(0xFF0284C7),
        isFeatured = false
    ),
    ROYAL_VIOLET(
        persianTitle = "بنفش یاسی و مرجانی",
        englishTitle = "Royal Violet & Coral",
        persianSubtitle = "بنفش کلاسیک و سرخابی تیره با پس‌زمینه روشن",
        previewPrimary = Color(0xFF7C3AED),
        previewSecondary = Color(0xFFDB2777),
        previewTertiary = Color(0xFF0D9488),
        isFeatured = false
    ),
    CORAL_SUNRISE(
        persianTitle = "یاقوتی و کهربایی",
        englishTitle = "Ruby & Amber",
        persianSubtitle = "قرمز یاقوتی و کهربایی با کنتراست بالا برای تیترها",
        previewPrimary = Color(0xFFE11D48),
        previewSecondary = Color(0xFFD97706),
        previewTertiary = Color(0xFF2563EB),
        isFeatured = false
    ),
    OCEAN_SAPPHIRE(
        persianTitle = "لاجوردی و آبی اقیانوسی",
        englishTitle = "Ocean Sapphire",
        persianSubtitle = "آبی لاجوردی و فیروزه‌ای تیره با خوانایی بالا",
        previewPrimary = Color(0xFF1D4ED8),
        previewSecondary = Color(0xFF0891B2),
        previewTertiary = Color(0xFF9333EA),
        isFeatured = false
    ),
    NIGHT_CYBERPUNK_NEON(
        persianTitle = "فیروزه‌ای تیره و کنتراست بالا",
        englishTitle = "High-Contrast Cyan",
        persianSubtitle = "پس‌زمینه تیره با رنگ‌های فیروزه‌ای و بنفش پرکنتراست",
        previewPrimary = Color(0xFF06B6D4),
        previewSecondary = Color(0xFFA855F7),
        previewTertiary = Color(0xFF10B981),
        isFeatured = false
    ),
    COFFEE_CARAMEL_WARM(
        persianTitle = "قهوه‌ای کلاسیک و کارامل",
        englishTitle = "Espresso & Caramel",
        persianSubtitle = "طیف گرم قهوه‌ای و کارامل برای مطالعه متون طولانی",
        previewPrimary = Color(0xFF78350F),
        previewSecondary = Color(0xFFD97706),
        previewTertiary = Color(0xFF4D7C0F),
        isFeatured = false
    ),
    FOREST_PINE_MINT(
        persianTitle = "سبز کاج و نعنایی",
        englishTitle = "Forest Pine & Mint",
        persianSubtitle = "طیف سبز تیره و نعنایی با پس‌زمینه ملایم",
        previewPrimary = Color(0xFF15803D),
        previewSecondary = Color(0xFF0D9488),
        previewTertiary = Color(0xFF0284C7),
        isFeatured = false
    ),
    SUNSET_PEACH_LAVENDER(
        persianTitle = "سرخابی و بنفش ملایم",
        englishTitle = "Crimson & Lavender",
        persianSubtitle = "ترکیب سرخابی و بنفش ملایم با سطح روشن",
        previewPrimary = Color(0xFFE11D48),
        previewSecondary = Color(0xFF7C3AED),
        previewTertiary = Color(0xFFEA580C),
        isFeatured = false
    ),
    ROSE_GOLD_VELVET(
        persianTitle = "شرابی و کهربایی",
        englishTitle = "Burgundy & Amber",
        persianSubtitle = "ترکیب شرابی تیره و کهربایی برای محیط مطالعه",
        previewPrimary = Color(0xFF881337),
        previewSecondary = Color(0xFFDB2777),
        previewTertiary = Color(0xFFB45309),
        isFeatured = false
    );

    companion object {
        val featuredPalettes: List<AppColorPalette>
            get() = entries.filter { it.isFeatured }
    }

    fun toLightColorScheme(): ColorScheme = when (this) {
        PIXEL_EXPRESSIVE_17 -> lightColorScheme(
            primary = StudyPrimary,
            onPrimary = Color(0xFFFFFFFF),
            primaryContainer = Color(0xFFE3EAF4),
            onPrimaryContainer = Color(0xFF14243E),
            secondary = StudySecondary,
            onSecondary = Color(0xFFFFFFFF),
            secondaryContainer = Color(0xFFE8EFF7),
            onSecondaryContainer = Color(0xFF1E3148),
            tertiary = StudySuccess,
            onTertiary = Color(0xFFFFFFFF),
            tertiaryContainer = Color(0xFFDDF3EC),
            onTertiaryContainer = Color(0xFF0E382B),
            background = StudyBackground,
            onBackground = StudyText,
            surface = StudySurface,
            onSurface = StudyText,
            surfaceVariant = Color(0xFFEEF1F5),
            onSurfaceVariant = Color(0xFF4A5568),
            outline = Color(0xFFCBD5E1),
            outlineVariant = StudyBorder,
            surfaceContainerLowest = Color(0xFFFFFFFF),
            surfaceContainerLow = Color(0xFFFFFFFF),
            surfaceContainer = Color(0xFFF6F7F9),
            surfaceContainerHigh = Color(0xFFEEF1F5),
            surfaceContainerHighest = Color(0xFFE6EAF0)
        )
        PSYCHOLOGICAL_SAGE -> lightColorScheme(
            primary = Color(0xFF1E5B42),
            onPrimary = Color.White,
            primaryContainer = Color(0xFFD6EBE0),
            onPrimaryContainer = Color(0xFF052B1E),
            secondary = Color(0xFFB45309),
            onSecondary = Color.White,
            secondaryContainer = Color(0xFFFEF3C7),
            onSecondaryContainer = Color(0xFF451A03),
            tertiary = Color(0xFF0F766E),
            onTertiary = Color.White,
            tertiaryContainer = Color(0xFFCCFBF1),
            onTertiaryContainer = Color(0xFF042F2E),
            background = Color(0xFFF7FAF8),
            onBackground = Color(0xFF131D18),
            surface = Color(0xFFFFFFFF),
            onSurface = Color(0xFF131D18),
            surfaceVariant = Color(0xFFE2EBE5),
            onSurfaceVariant = Color(0xFF3F4D45)
        )
        TURQUOISE_GOLD -> lightColorScheme(
            primary = PrimaryLight,
            onPrimary = OnPrimaryLight,
            primaryContainer = PrimaryContainerLight,
            onPrimaryContainer = OnPrimaryContainerLight,
            secondary = SecondaryLight,
            onSecondary = OnSecondaryLight,
            secondaryContainer = SecondaryContainerLight,
            onSecondaryContainer = OnSecondaryContainerLight,
            tertiary = TertiaryLight,
            onTertiary = OnTertiaryLight,
            tertiaryContainer = TertiaryContainerLight,
            onTertiaryContainer = OnTertiaryContainerLight,
            background = BackgroundLight,
            onBackground = OnBackgroundLight,
            surface = SurfaceLight,
            onSurface = OnSurfaceLight,
            surfaceVariant = SurfaceVariantLight,
            onSurfaceVariant = OnSurfaceVariantLight
        )
        EMERALD_SPRING -> lightColorScheme(
            primary = Color(0xFF047857),
            onPrimary = Color.White,
            primaryContainer = Color(0xFFA7F3D0),
            onPrimaryContainer = Color(0xFF022C22),
            secondary = Color(0xFFEA580C),
            onSecondary = Color.White,
            secondaryContainer = Color(0xFFFFEDD5),
            onSecondaryContainer = Color(0xFF431407),
            tertiary = Color(0xFF0284C7),
            onTertiary = Color.White,
            tertiaryContainer = Color(0xFFE0F2FE),
            onTertiaryContainer = Color(0xFF082F49),
            background = Color(0xFFF4FBF7),
            onBackground = Color(0xFF14211B),
            surface = Color(0xFFFFFFFF),
            onSurface = Color(0xFF14211B),
            surfaceVariant = Color(0xFFDDF3E8),
            onSurfaceVariant = Color(0xFF3A4F45)
        )
        ROYAL_VIOLET -> lightColorScheme(
            primary = Color(0xFF6D28D9),
            onPrimary = Color.White,
            primaryContainer = Color(0xFFEDE9FE),
            onPrimaryContainer = Color(0xFF2E1065),
            secondary = Color(0xFFDB2777),
            onSecondary = Color.White,
            secondaryContainer = Color(0xFFFCE7F3),
            onSecondaryContainer = Color(0xFF500724),
            tertiary = Color(0xFF0D9488),
            onTertiary = Color.White,
            tertiaryContainer = Color(0xFFCCFBF1),
            onTertiaryContainer = Color(0xFF042F2E),
            background = Color(0xFFFAF7FF),
            onBackground = Color(0xFF1E162B),
            surface = Color(0xFFFFFFFF),
            onSurface = Color(0xFF1E162B),
            surfaceVariant = Color(0xFFEDE5F8),
            onSurfaceVariant = Color(0xFF493F59)
        )
        CORAL_SUNRISE -> lightColorScheme(
            primary = Color(0xFFBE123C),
            onPrimary = Color.White,
            primaryContainer = Color(0xFFFFE4E6),
            onPrimaryContainer = Color(0xFF4C0519),
            secondary = Color(0xFFD97706),
            onSecondary = Color.White,
            secondaryContainer = Color(0xFFFEF3C7),
            onSecondaryContainer = Color(0xFF451A03),
            tertiary = Color(0xFF2563EB),
            onTertiary = Color.White,
            tertiaryContainer = Color(0xFFDBEAFE),
            onTertiaryContainer = Color(0xFF1E3A8A),
            background = Color(0xFFFFF8F8),
            onBackground = Color(0xFF241619),
            surface = Color(0xFFFFFFFF),
            onSurface = Color(0xFF241619),
            surfaceVariant = Color(0xFFFCE4E8),
            onSurfaceVariant = Color(0xFF573E43)
        )
        OCEAN_SAPPHIRE -> lightColorScheme(
            primary = Color(0xFF1D4ED8),
            onPrimary = Color.White,
            primaryContainer = Color(0xFFDBEAFE),
            onPrimaryContainer = Color(0xFF172554),
            secondary = Color(0xFF0891B2),
            onSecondary = Color.White,
            secondaryContainer = Color(0xFFCFFAFE),
            onSecondaryContainer = Color(0xFF083344),
            tertiary = Color(0xFF7C3AED),
            onTertiary = Color.White,
            tertiaryContainer = Color(0xFFEDE9FE),
            onTertiaryContainer = Color(0xFF2E1065),
            background = Color(0xFFF5F8FF),
            onBackground = Color(0xFF141B29),
            surface = Color(0xFFFFFFFF),
            onSurface = Color(0xFF141B29),
            surfaceVariant = Color(0xFFE0EAFA),
            onSurfaceVariant = Color(0xFF3E4A5E)
        )
        FARHANGIAN_CRIMSON_GOLD -> lightColorScheme(
            primary = Color(0xFF9F1239),
            onPrimary = Color.White,
            primaryContainer = Color(0xFFFFE4E6),
            onPrimaryContainer = Color(0xFF4C0519),
            secondary = Color(0xFFD97706),
            onSecondary = Color.White,
            secondaryContainer = Color(0xFFFEF3C7),
            onSecondaryContainer = Color(0xFF451A03),
            tertiary = Color(0xFF047857),
            onTertiary = Color.White,
            tertiaryContainer = Color(0xFFD1FAE5),
            onTertiaryContainer = Color(0xFF022C22),
            background = Color(0xFFFFF9FA),
            onBackground = Color(0xFF261217),
            surface = Color.White,
            onSurface = Color(0xFF261217),
            surfaceVariant = Color(0xFFFDE8EC),
            onSurfaceVariant = Color(0xFF553B42)
        )
        NIGHT_CYBERPUNK_NEON -> lightColorScheme(
            primary = Color(0xFF0891B2),
            onPrimary = Color.White,
            primaryContainer = Color(0xFFCFFAFE),
            onPrimaryContainer = Color(0xFF083344),
            secondary = Color(0xFF9333EA),
            onSecondary = Color.White,
            secondaryContainer = Color(0xFFF3E8FF),
            onSecondaryContainer = Color(0xFF3B0764),
            tertiary = Color(0xFF059669),
            onTertiary = Color.White,
            tertiaryContainer = Color(0xFFD1FAE5),
            onTertiaryContainer = Color(0xFF022C22),
            background = Color(0xFFF8FAFC),
            onBackground = Color(0xFF0F172A),
            surface = Color.White,
            onSurface = Color(0xFF0F172A),
            surfaceVariant = Color(0xFFE2E8F0),
            onSurfaceVariant = Color(0xFF334155)
        )
        COFFEE_CARAMEL_WARM -> lightColorScheme(
            primary = Color(0xFF78350F),
            onPrimary = Color.White,
            primaryContainer = Color(0xFFFEF3C7),
            onPrimaryContainer = Color(0xFF451A03),
            secondary = Color(0xFFB45309),
            onSecondary = Color.White,
            secondaryContainer = Color(0xFFFFEDD5),
            onSecondaryContainer = Color(0xFF431407),
            tertiary = Color(0xFF4D7C0F),
            onTertiary = Color.White,
            tertiaryContainer = Color(0xFFECFCCB),
            onTertiaryContainer = Color(0xFF1A2E05),
            background = Color(0xFFFFFDF7),
            onBackground = Color(0xFF231B15),
            surface = Color.White,
            onSurface = Color(0xFF231B15),
            surfaceVariant = Color(0xFFF5EBE1),
            onSurfaceVariant = Color(0xFF51453D)
        )
        FOREST_PINE_MINT -> lightColorScheme(
            primary = Color(0xFF15803D),
            onPrimary = Color.White,
            primaryContainer = Color(0xFFDCFCE7),
            onPrimaryContainer = Color(0xFF052E16),
            secondary = Color(0xFF0D9488),
            onSecondary = Color.White,
            secondaryContainer = Color(0xFFCCFBF1),
            onSecondaryContainer = Color(0xFF042F2E),
            tertiary = Color(0xFF0284C7),
            onTertiary = Color.White,
            tertiaryContainer = Color(0xFFE0F2FE),
            onTertiaryContainer = Color(0xFF082F49),
            background = Color(0xFFF7FCF9),
            onBackground = Color(0xFF112217),
            surface = Color.White,
            onSurface = Color(0xFF112217),
            surfaceVariant = Color(0xFFE0EFE6),
            onSurfaceVariant = Color(0xFF394C40)
        )
        SUNSET_PEACH_LAVENDER -> lightColorScheme(
            primary = Color(0xFFE11D48),
            onPrimary = Color.White,
            primaryContainer = Color(0xFFFFE4E6),
            onPrimaryContainer = Color(0xFF4C0519),
            secondary = Color(0xFF7C3AED),
            onSecondary = Color.White,
            secondaryContainer = Color(0xFFEDE9FE),
            onSecondaryContainer = Color(0xFF2E1065),
            tertiary = Color(0xFFEA580C),
            onTertiary = Color.White,
            tertiaryContainer = Color(0xFFFFEDD5),
            onTertiaryContainer = Color(0xFF431407),
            background = Color(0xFFFFF9FA),
            onBackground = Color(0xFF251419),
            surface = Color.White,
            onSurface = Color(0xFF251419),
            surfaceVariant = Color(0xFFF9E4E8),
            onSurfaceVariant = Color(0xFF523D42)
        )
        ROSE_GOLD_VELVET -> lightColorScheme(
            primary = Color(0xFF881337),
            onPrimary = Color.White,
            primaryContainer = Color(0xFFFFE4E6),
            onPrimaryContainer = Color(0xFF4C0519),
            secondary = Color(0xFFDB2777),
            onSecondary = Color.White,
            secondaryContainer = Color(0xFFFCE7F3),
            onSecondaryContainer = Color(0xFF500724),
            tertiary = Color(0xFFB45309),
            onTertiary = Color.White,
            tertiaryContainer = Color(0xFFFEF3C7),
            onTertiaryContainer = Color(0xFF451A03),
            background = Color(0xFFFFF8F9),
            onBackground = Color(0xFF241318),
            surface = Color.White,
            onSurface = Color(0xFF241318),
            surfaceVariant = Color(0xFFFCE2E7),
            onSurfaceVariant = Color(0xFF553A40)
        )
        KETABRAH_FIDIBO_PAPER -> lightColorScheme(
            primary = Color(0xFF7C3E1D),
            onPrimary = Color(0xFFFFFFFF),
            primaryContainer = Color(0xFFF3DCAF),
            onPrimaryContainer = Color(0xFF3D1B08),
            secondary = Color(0xFFC67C24),
            onSecondary = Color(0xFFFFFFFF),
            secondaryContainer = Color(0xFFF7E6C4),
            onSecondaryContainer = Color(0xFF4A2A08),
            tertiary = Color(0xFF2D6A4F),
            onTertiary = Color(0xFFFFFFFF),
            tertiaryContainer = Color(0xFFD4EDDA),
            onTertiaryContainer = Color(0xFF0F3822),
            background = Color(0xFFF7E8CA),
            onBackground = Color(0xFF2C221E),
            surface = Color(0xFFFBF0D9),
            onSurface = Color(0xFF2C221E),
            surfaceVariant = Color(0xFFF2DFB8),
            onSurfaceVariant = Color(0xFF42332B),
            surfaceContainerLowest = Color(0xFFFFF8ED),
            surfaceContainerLow = Color(0xFFFBF0D9),
            surfaceContainer = Color(0xFFF6E5C3),
            surfaceContainerHigh = Color(0xFFEED9B1),
            surfaceContainerHighest = Color(0xFFE5CD9F)
        )
    }

    fun toDarkColorScheme(): ColorScheme = when (this) {
        PIXEL_EXPRESSIVE_17 -> darkColorScheme(
            primary = Color(0xFFA8C7FA),
            onPrimary = Color(0xFF062E6F),
            primaryContainer = Color(0xFF0842A0),
            onPrimaryContainer = Color(0xFFD3E3FD),
            secondary = Color(0xFF7FCFFF),
            onSecondary = Color(0xFF003355),
            secondaryContainer = Color(0xFF004A77),
            onSecondaryContainer = Color(0xFFC2E7FF),
            tertiary = Color(0xFF6DD58C),
            onTertiary = Color(0xFF0A3818),
            tertiaryContainer = Color(0xFF0F5223),
            onTertiaryContainer = Color(0xFFC4EED0),
            background = Color(0xFF111318),
            onBackground = Color(0xFFE2E2E9),
            surface = Color(0xFF191C20),
            onSurface = Color(0xFFE2E2E9),
            surfaceVariant = Color(0xFF282A2F),
            onSurfaceVariant = Color(0xFFC4C7C5),
            surfaceContainerLowest = Color(0xFF0C0E13),
            surfaceContainerLow = Color(0xFF191C20),
            surfaceContainer = Color(0xFF1E2025),
            surfaceContainerHigh = Color(0xFF282A2F),
            surfaceContainerHighest = Color(0xFF33353A)
        )
        PSYCHOLOGICAL_SAGE -> darkColorScheme(
            primary = Color(0xFF6EE7B7),
            onPrimary = Color(0xFF022C22),
            primaryContainer = Color(0xFF164E3A),
            onPrimaryContainer = Color(0xFFA7F3D0),
            secondary = Color(0xFF5EEAD4),
            onSecondary = Color(0xFF042F2E),
            secondaryContainer = Color(0xFF115E59),
            onSecondaryContainer = Color(0xFFCCFBF1),
            tertiary = Color(0xFF38BDF8),
            onTertiary = Color(0xFF082F49),
            tertiaryContainer = Color(0xFF0C4A6E),
            onTertiaryContainer = Color(0xFFE0F2FE),
            background = Color(0xFF0D1512),
            onBackground = Color(0xFFE2ECE7),
            surface = Color(0xFF15221C),
            onSurface = Color(0xFFF0F7F4),
            surfaceVariant = Color(0xFF22352C),
            onSurfaceVariant = Color(0xFFBCC8C1)
        )
        TURQUOISE_GOLD -> darkColorScheme(
            primary = PrimaryDark,
            onPrimary = OnPrimaryDark,
            primaryContainer = PrimaryContainerDark,
            onPrimaryContainer = OnPrimaryContainerDark,
            secondary = SecondaryDark,
            onSecondary = OnSecondaryDark,
            secondaryContainer = SecondaryContainerDark,
            onSecondaryContainer = OnSecondaryContainerDark,
            tertiary = TertiaryDark,
            onTertiary = OnTertiaryDark,
            tertiaryContainer = TertiaryContainerDark,
            onTertiaryContainer = OnTertiaryContainerDark,
            background = BackgroundDark,
            onBackground = OnBackgroundDark,
            surface = SurfaceDark,
            onSurface = OnSurfaceDark,
            surfaceVariant = SurfaceVariantDark,
            onSurfaceVariant = OnSurfaceVariantDark
        )
        EMERALD_SPRING -> darkColorScheme(
            primary = Color(0xFF6EE7B7),
            onPrimary = Color(0xFF022C22),
            primaryContainer = Color(0xFF065F46),
            onPrimaryContainer = Color(0xFFA7F3D0),
            secondary = Color(0xFFFDBA74),
            onSecondary = Color(0xFF431407),
            secondaryContainer = Color(0xFF7C2D12),
            onSecondaryContainer = Color(0xFFFFEDD5),
            tertiary = Color(0xFF7DD3FC),
            onTertiary = Color(0xFF082F49),
            tertiaryContainer = Color(0xFF0369A1),
            onTertiaryContainer = Color(0xFFE0F2FE),
            background = Color(0xFF0C1713),
            onBackground = Color(0xFFDCECE4),
            surface = Color(0xFF13221C),
            onSurface = Color(0xFFDCECE4),
            surfaceVariant = Color(0xFF21382F),
            onSurfaceVariant = Color(0xFFB8CFC5)
        )
        ROYAL_VIOLET -> darkColorScheme(
            primary = Color(0xFFC4B5FD),
            onPrimary = Color(0xFF2E1065),
            primaryContainer = Color(0xFF5B21B6),
            onPrimaryContainer = Color(0xFFEDE9FE),
            secondary = Color(0xFFF472B6),
            onSecondary = Color(0xFF500724),
            secondaryContainer = Color(0xFF831843),
            onSecondaryContainer = Color(0xFFFCE7F3),
            tertiary = Color(0xFF5EEAD4),
            onTertiary = Color(0xFF042F2E),
            tertiaryContainer = Color(0xFF115E59),
            onTertiaryContainer = Color(0xFFCCFBF1),
            background = Color(0xFF120E1A),
            onBackground = Color(0xFFE9E2F4),
            surface = Color(0xFF1B1526),
            onSurface = Color(0xFFE9E2F4),
            surfaceVariant = Color(0xFF2E2440),
            onSurfaceVariant = Color(0xFFCBC2DC)
        )
        CORAL_SUNRISE -> darkColorScheme(
            primary = Color(0xFFFDA4AF),
            onPrimary = Color(0xFF4C0519),
            primaryContainer = Color(0xFF9F1239),
            onPrimaryContainer = Color(0xFFFFE4E6),
            secondary = Color(0xFFFCD34D),
            onSecondary = Color(0xFF451A03),
            secondaryContainer = Color(0xFF78350F),
            onSecondaryContainer = Color(0xFFFEF3C7),
            tertiary = Color(0xFF93C5FD),
            onTertiary = Color(0xFF1E3A8A),
            tertiaryContainer = Color(0xFF1D4ED8),
            onTertiaryContainer = Color(0xFFDBEAFE),
            background = Color(0xFF191012),
            onBackground = Color(0xFFF5E1E4),
            surface = Color(0xFF24171A),
            onSurface = Color(0xFFF5E1E4),
            surfaceVariant = Color(0xFF3B262B),
            onSurfaceVariant = Color(0xFFD8C0C5)
        )
        OCEAN_SAPPHIRE -> darkColorScheme(
            primary = Color(0xFF93C5FD),
            onPrimary = Color(0xFF172554),
            primaryContainer = Color(0xFF1E40AF),
            onPrimaryContainer = Color(0xFFDBEAFE),
            secondary = Color(0xFF67E8F9),
            onSecondary = Color(0xFF083344),
            secondaryContainer = Color(0xFF155E75),
            onSecondaryContainer = Color(0xFFCFFAFE),
            tertiary = Color(0xFFC4B5FD),
            onTertiary = Color(0xFF2E1065),
            tertiaryContainer = Color(0xFF5B21B6),
            onTertiaryContainer = Color(0xFFEDE9FE),
            background = Color(0xFF0D131F),
            onBackground = Color(0xFFDDE5F4),
            surface = Color(0xFF151E2E),
            onSurface = Color(0xFFDDE5F4),
            surfaceVariant = Color(0xFF233047),
            onSurfaceVariant = Color(0xFFBEC8DC)
        )
        FARHANGIAN_CRIMSON_GOLD -> darkColorScheme(
            primary = Color(0xFFFB7185),
            onPrimary = Color(0xFF4C0519),
            primaryContainer = Color(0xFF9F1239),
            onPrimaryContainer = Color(0xFFFFE4E6),
            secondary = Color(0xFFFBBF24),
            onSecondary = Color(0xFF451A03),
            secondaryContainer = Color(0xFF78350F),
            onSecondaryContainer = Color(0xFFFEF3C7),
            tertiary = Color(0xFF34D399),
            onTertiary = Color(0xFF022C22),
            tertiaryContainer = Color(0xFF065F46),
            onTertiaryContainer = Color(0xFFD1FAE5),
            background = Color(0xFF14070B),
            onBackground = Color(0xFFFDE8EC),
            surface = Color(0xFF1F0D13),
            onSurface = Color(0xFFFDE8EC),
            surfaceVariant = Color(0xFF331620),
            onSurfaceVariant = Color(0xFFE2C2CA)
        )
        NIGHT_CYBERPUNK_NEON -> darkColorScheme(
            primary = Color(0xFF22D3EE),
            onPrimary = Color(0xFF083344),
            primaryContainer = Color(0xFF0E7490),
            onPrimaryContainer = Color(0xFFCFFAFE),
            secondary = Color(0xFFC084FC),
            onSecondary = Color(0xFF3B0764),
            secondaryContainer = Color(0xFF6B21A8),
            onSecondaryContainer = Color(0xFFF3E8FF),
            tertiary = Color(0xFF34D399),
            onTertiary = Color(0xFF022C22),
            tertiaryContainer = Color(0xFF065F46),
            onTertiaryContainer = Color(0xFFD1FAE5),
            background = Color(0xFF030712),
            onBackground = Color(0xFFF9FAFB),
            surface = Color(0xFF0B0F19),
            onSurface = Color(0xFFF9FAFB),
            surfaceVariant = Color(0xFF161E2E),
            onSurfaceVariant = Color(0xFFD1D5DB)
        )
        COFFEE_CARAMEL_WARM -> darkColorScheme(
            primary = Color(0xFFFBBF24),
            onPrimary = Color(0xFF451A03),
            primaryContainer = Color(0xFF78350F),
            onPrimaryContainer = Color(0xFFFEF3C7),
            secondary = Color(0xFFFB923C),
            onSecondary = Color(0xFF431407),
            secondaryContainer = Color(0xFF9A3412),
            onSecondaryContainer = Color(0xFFFFEDD5),
            tertiary = Color(0xFFA3E635),
            onTertiary = Color(0xFF1A2E05),
            tertiaryContainer = Color(0xFF365314),
            onTertiaryContainer = Color(0xFFECFCCB),
            background = Color(0xFF120C08),
            onBackground = Color(0xFFFDF6EE),
            surface = Color(0xFF1D140F),
            onSurface = Color(0xFFFDF6EE),
            surfaceVariant = Color(0xFF2E211A),
            onSurfaceVariant = Color(0xFFE7D8CD)
        )
        FOREST_PINE_MINT -> darkColorScheme(
            primary = Color(0xFF4ADE80),
            onPrimary = Color(0xFF052E16),
            primaryContainer = Color(0xFF15803D),
            onPrimaryContainer = Color(0xFFDCFCE7),
            secondary = Color(0xFF2DD4BF),
            onSecondary = Color(0xFF042F2E),
            secondaryContainer = Color(0xFF0F766E),
            onSecondaryContainer = Color(0xFFCCFBF1),
            tertiary = Color(0xFF38BDF8),
            onTertiary = Color(0xFF082F49),
            tertiaryContainer = Color(0xFF0369A1),
            onTertiaryContainer = Color(0xFFE0F2FE),
            background = Color(0xFF08140D),
            onBackground = Color(0xFFE5F5EC),
            surface = Color(0xFF102117),
            onSurface = Color(0xFFE5F5EC),
            surfaceVariant = Color(0xFF1D3527),
            onSurfaceVariant = Color(0xFFC3DDD0)
        )
        SUNSET_PEACH_LAVENDER -> darkColorScheme(
            primary = Color(0xFFFB7185),
            onPrimary = Color(0xFF4C0519),
            primaryContainer = Color(0xFFBE123C),
            onPrimaryContainer = Color(0xFFFFE4E6),
            secondary = Color(0xFFC084FC),
            onSecondary = Color(0xFF2E1065),
            secondaryContainer = Color(0xFF6B21A8),
            onSecondaryContainer = Color(0xFFEDE9FE),
            tertiary = Color(0xFFFB923C),
            onTertiary = Color(0xFF431407),
            tertiaryContainer = Color(0xFF9A3412),
            onTertiaryContainer = Color(0xFFFFEDD5),
            background = Color(0xFF13090F),
            onBackground = Color(0xFFFCE7F0),
            surface = Color(0xFF1F101A),
            onSurface = Color(0xFFFCE7F0),
            surfaceVariant = Color(0xFF331B2B),
            onSurfaceVariant = Color(0xFFE4C4D7)
        )
        ROSE_GOLD_VELVET -> darkColorScheme(
            primary = Color(0xFFFDA4AF),
            onPrimary = Color(0xFF4C0519),
            primaryContainer = Color(0xFF881337),
            onPrimaryContainer = Color(0xFFFFE4E6),
            secondary = Color(0xFFF472B6),
            onSecondary = Color(0xFF500724),
            secondaryContainer = Color(0xFF9D174D),
            onSecondaryContainer = Color(0xFFFCE7F3),
            tertiary = Color(0xFFFBBF24),
            onTertiary = Color(0xFF451A03),
            tertiaryContainer = Color(0xFF78350F),
            onTertiaryContainer = Color(0xFFFEF3C7),
            background = Color(0xFF14080D),
            onBackground = Color(0xFFFCE9F0),
            surface = Color(0xFF210E17),
            onSurface = Color(0xFFFCE9F0),
            surfaceVariant = Color(0xFF361927),
            onSurfaceVariant = Color(0xFFE5C5D4)
        )
        KETABRAH_FIDIBO_PAPER -> darkColorScheme(
            primary = Color(0xFFE5A65D),
            onPrimary = Color(0xFF231B12),
            primaryContainer = Color(0xFF42301D),
            onPrimaryContainer = Color(0xFFFADDB8),
            secondary = Color(0xFFE2B870),
            onSecondary = Color(0xFF2B2011),
            secondaryContainer = Color(0xFF4A3A22),
            onSecondaryContainer = Color(0xFFF8E7CD),
            tertiary = Color(0xFF81C784),
            onTertiary = Color(0xFF0D2818),
            tertiaryContainer = Color(0xFF1E4620),
            onTertiaryContainer = Color(0xFFC8E6C9),
            background = Color(0xFF1B1814),
            onBackground = Color(0xFFE8DAC2),
            surface = Color(0xFF23201B),
            onSurface = Color(0xFFE8DAC2),
            surfaceVariant = Color(0xFF2F2922),
            onSurfaceVariant = Color(0xFFD4C3AA)
        )
    }
}
