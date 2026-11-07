package com.example.ui.theme

import androidx.annotation.FontRes
import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontSynthesis
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.R

internal fun createCompleteFontFamily(
    @FontRes regularRes: Int,
    @FontRes boldRes: Int = regularRes,
    @FontRes mediumRes: Int = regularRes,
    @FontRes lightRes: Int = regularRes,
    @FontRes blackRes: Int = boldRes,
    forceBold: Boolean = false
): FontFamily {
    val lightFont = if (forceBold) boldRes else lightRes
    val normalFont = if (forceBold) boldRes else regularRes
    val mediumFont = if (forceBold) boldRes else mediumRes
    val semiBoldFont = if (forceBold) boldRes else if (mediumRes != regularRes) mediumRes else boldRes
    val heavyOrBold = boldRes
    return FontFamily(
        Font(lightFont, FontWeight.Thin),
        Font(lightFont, FontWeight.ExtraLight),
        Font(lightFont, FontWeight.Light),
        Font(normalFont, FontWeight.Normal),
        Font(mediumFont, FontWeight.Medium),
        Font(semiBoldFont, FontWeight.SemiBold),
        Font(heavyOrBold, FontWeight.Bold),
        Font(heavyOrBold, FontWeight.ExtraBold),
        Font(blackRes, FontWeight.Black)
    )
}

private fun createDualFontFamily(
    @FontRes regularRes: Int,
    @FontRes boldRes: Int = regularRes,
    @FontRes mediumRes: Int = regularRes,
    @FontRes lightRes: Int = regularRes,
    @FontRes blackRes: Int = boldRes,
    forceBold: Boolean = false
): FontFamily = createCompleteFontFamily(
    regularRes = regularRes,
    boldRes = boldRes,
    mediumRes = mediumRes,
    lightRes = lightRes,
    blackRes = blackRes,
    forceBold = forceBold
)

val VazirmatnFontFamily: FontFamily = createDualFontFamily(
    regularRes = R.font.vazirmatn,
    boldRes = R.font.vazirmatn_bold,
    mediumRes = R.font.vazirmatn_medium,
    lightRes = R.font.vazirmatn_thin,
    blackRes = R.font.vazirmatn_black,
    forceBold = false
)
val VazirmatnBoldFontFamily: FontFamily = createDualFontFamily(
    regularRes = R.font.vazirmatn_bold,
    boldRes = R.font.vazirmatn_bold,
    mediumRes = R.font.vazirmatn_bold,
    lightRes = R.font.vazirmatn_medium,
    blackRes = R.font.vazirmatn_black,
    forceBold = true
)

val HarmattanFontFamily: FontFamily = createDualFontFamily(
    regularRes = R.font.harmattan,
    boldRes = R.font.harmattan_bold,
    forceBold = false
)
val HarmattanBoldFontFamily: FontFamily = createDualFontFamily(
    regularRes = R.font.harmattan_bold,
    boldRes = R.font.harmattan_bold,
    forceBold = true
)

val ArefRuqaaFontFamily: FontFamily = createDualFontFamily(R.font.aref_ruqaa)
val ArefRuqaaBoldFontFamily: FontFamily = createDualFontFamily(R.font.aref_ruqaa, forceBold = true)

val ScheherazadeFontFamily: FontFamily = createDualFontFamily(
    regularRes = R.font.scheherazade,
    boldRes = R.font.scheherazade_bold,
    forceBold = false
)
val ScheherazadeBoldFontFamily: FontFamily = createDualFontFamily(
    regularRes = R.font.scheherazade_bold,
    boldRes = R.font.scheherazade_bold,
    forceBold = true
)

val LateefFontFamily: FontFamily = createDualFontFamily(R.font.lateef)
val LateefBoldFontFamily: FontFamily = createDualFontFamily(R.font.lateef, forceBold = true)

val AmiriFontFamily: FontFamily = createDualFontFamily(
    regularRes = R.font.amiri,
    boldRes = R.font.amiri_bold,
    forceBold = false
)
val AmiriBoldFontFamily: FontFamily = createDualFontFamily(
    regularRes = R.font.amiri_bold,
    boldRes = R.font.amiri_bold,
    forceBold = true
)

val IbmPlexSansArabicFontFamily: FontFamily = createDualFontFamily(R.font.ibm_plex_sans_arabic)
val IbmPlexSansArabicBoldFontFamily: FontFamily = createDualFontFamily(R.font.ibm_plex_sans_arabic, forceBold = true)

val AlmaraiFontFamily: FontFamily = createDualFontFamily(R.font.almarai)
val AlmaraiBoldFontFamily: FontFamily = createDualFontFamily(R.font.almarai, forceBold = true)

val CairoModernFontFamily: FontFamily = createDualFontFamily(R.font.cairo)
val CairoModernBoldFontFamily: FontFamily = createDualFontFamily(R.font.cairo, forceBold = true)

val LalezarFontFamily: FontFamily = createDualFontFamily(R.font.lalezar)
val LalezarBoldFontFamily: FontFamily = createDualFontFamily(R.font.lalezar, forceBold = true)

enum class BuiltInFontPreset(
    val persianTitle: String,
    val persianSubtitle: String,
    @FontRes val regularRes: Int,
    @FontRes val boldRes: Int = regularRes
) {
    // === ۱. خانواده فونت‌های مدرن و استاندارد فارسی ===
    VAZIRMATN(
        persianTitle = "وزیرمتن استاندارد (Vazirmatn)",
        persianSubtitle = "فونت استاندارد و طلایی فارسی با اعداد و اعراب کامل",
        regularRes = R.font.vazirmatn,
        boldRes = R.font.vazirmatn_bold
    ),
    VAZIRMATN_RD(
        persianTitle = "وزیرمتن نقطه‌گرد (Vazirmatn RD)",
        persianSubtitle = "نسخه نقطه‌گرد و چشم‌نواز خانواده وزیرمتن",
        regularRes = R.font.vazirmatn_rd,
        boldRes = R.font.vazirmatn_rd_bold
    ),
    VAZIRMATN_MEDIUM(
        persianTitle = "وزیرمتن نیمه‌ضخیم (Vazirmatn Medium)",
        persianSubtitle = "وزن متعادل و پررنگ‌تر برای مطالعه در صفحه‌نمایش موبایل",
        regularRes = R.font.vazirmatn_medium,
        boldRes = R.font.vazirmatn_bold
    ),
    VAZIRMATN_THIN(
        persianTitle = "وزیرمتن ظریف (Vazirmatn Light)",
        persianSubtitle = "وزن ظریف و سبک برای متون مدرن و مینیمال",
        regularRes = R.font.vazirmatn_thin,
        boldRes = R.font.vazirmatn
    ),
    VAZIRMATN_BLACK(
        persianTitle = "وزیرمتن فوق‌ضخیم (Vazirmatn Black)",
        persianSubtitle = "سنگین‌ترین وزن وزیرمتن برای حداکثر خوانایی",
        regularRes = R.font.vazirmatn_black,
        boldRes = R.font.vazirmatn_black
    ),
    VAZIR_CLASSIC(
        persianTitle = "وزیر کلاسیک (Vazir v30)",
        persianSubtitle = "نسخه اصیل و خوانای فونت وزیر",
        regularRes = R.font.vazir_classic,
        boldRes = R.font.vazir_classic_bold
    ),
    SAHEL(
        persianTitle = "ساحل (Sahel)",
        persianSubtitle = "فونت مدرن، خوش‌تراش و کمی فشرده برای داشبورد و تیتر",
        regularRes = R.font.sahel,
        boldRes = R.font.sahel_bold
    ),
    SHABNAM(
        persianTitle = "شبنم (Shabnam)",
        persianSubtitle = "فونت نرم، گرد و صمیمی با ساختار خوانا",
        regularRes = R.font.shabnam,
        boldRes = R.font.shabnam_bold
    ),
    SAMIM(
        persianTitle = "صمیم (Samim)",
        persianSubtitle = "فونت رسمی و جدی مناسب جزوات و متون طولانی",
        regularRes = R.font.samim,
        boldRes = R.font.samim_bold
    ),
    PARASTOO(
        persianTitle = "پرستو (Parastoo)",
        persianSubtitle = "فونت کتابی، ادبی و کلاسیک برای مطالعه عمیق درس‌نامه‌ها",
        regularRes = R.font.parastoo,
        boldRes = R.font.parastoo_bold
    ),
    TANHA(
        persianTitle = "تنها (Tanha)",
        persianSubtitle = "فونت ظریف و خاص برای ادبیات و علوم و فنون",
        regularRes = R.font.tanha,
        boldRes = R.font.tanha
    ),
    GANDOM(
        persianTitle = "گندم (Gandom)",
        persianSubtitle = "فونت گرم و روان با حس انسانی و خوانایی بالا",
        regularRes = R.font.gandom,
        boldRes = R.font.gandom
    ),
    NAHID(
        persianTitle = "ناهید (Nahid)",
        persianSubtitle = "فونت هندسی و خوش‌فرم برای مطالعه روزمره",
        regularRes = R.font.nahid,
        boldRes = R.font.nahid
    ),

    // === ۲. فونت‌های مطبوعاتی، عربی و کتب درسی ===
    ADOBE_ARABIC(
        persianTitle = "ادوبی عربیک اصلی (Adobe Arabic)",
        persianSubtitle = "فونت اصیل و رسمی شرکت ادوبی برای متون عربی و فارسی با اعراب کامل",
        regularRes = R.font.adobe_arabic,
        boldRes = R.font.adobe_arabic_bold
    ),
    YEKAN(
        persianTitle = "یکان کلاسیک (Yekan)",
        persianSubtitle = "محبوب‌ترین فونت وب و نرم‌افزارهای فارسی",
        regularRes = R.font.yekan,
        boldRes = R.font.yekan
    ),
    NAZANIN(
        persianTitle = "نازنین کتابی (B Nazanin)",
        persianSubtitle = "فونت سنتی کتب درسی، امتحانات و جزوات آموزشی",
        regularRes = R.font.nazanin,
        boldRes = R.font.nazanin
    ),
    MITRA(
        persianTitle = "میترا مطبوعاتی (B Mitra)",
        persianSubtitle = "فونت رسمی روزنامه‌ها و سؤالات امتحانی",
        regularRes = R.font.mitra,
        boldRes = R.font.mitra
    ),
    ROYA(
        persianTitle = "رویا آموزشی (B Roya)",
        persianSubtitle = "فونت نرم و خوانای کتاب‌های درسی و آموزشی",
        regularRes = R.font.roya,
        boldRes = R.font.roya
    ),
    DROID_NASKH(
        persianTitle = "دروید نسخ (Droid Naskh)",
        persianSubtitle = "فونت نسخ استاندارد با وضوح عالی در موبایل",
        regularRes = R.font.droid_naskh,
        boldRes = R.font.droid_naskh
    ),
    NOTO_KUFI_ARABIC(
        persianTitle = "کوفی عربی (Noto Kufi)",
        persianSubtitle = "فونت هندسی و مدرن عربی با طراحی شکیل کوفی",
        regularRes = R.font.noto_kufi_arabic,
        boldRes = R.font.noto_kufi_arabic
    ),
    CAIRO_ARABIC(
        persianTitle = "قاهره عربی (Cairo)",
        persianSubtitle = "فونت مدرن و برجسته عربی برای تیترها و مباحث",
        regularRes = R.font.cairo,
        boldRes = R.font.cairo
    ),
    ALMARAI_ARABIC(
        persianTitle = "المراعی (Almarai)",
        persianSubtitle = "فونت استاندارد خبری و رسانه‌ای عربی",
        regularRes = R.font.almarai,
        boldRes = R.font.almarai
    ),
    TAJAWAL_ARABIC(
        persianTitle = "تجاول (Tajawal)",
        persianSubtitle = "فونت فشرده و روان عربی با خوانایی عالی",
        regularRes = R.font.tajawal,
        boldRes = R.font.tajawal
    ),
    EL_MESSIRI_ARABIC(
        persianTitle = "المسیری (El Messiri)",
        persianSubtitle = "فونت هنری و خاص عربی با انحناهای چشم‌نواز",
        regularRes = R.font.el_messiri,
        boldRes = R.font.el_messiri
    ),

    // === ۳. فونت‌های آزاد ایرانی و ویژه ===
    ESTEDAD(
        persianTitle = "استعداد (Estedad)",
        persianSubtitle = "فونت آزاد ایرانی مدرن، استاندارد و خوانا",
        regularRes = R.font.estedad,
        boldRes = R.font.estedad_bold
    ),
    MIKHAK(
        persianTitle = "میخک (Mikhak)",
        persianSubtitle = "فونت گرد، دلنشین و فانتزی برای فلش‌کارت و تست",
        regularRes = R.font.mikhak,
        boldRes = R.font.mikhak_bold
    ),
    BEHDAD(
        persianTitle = "بهداد (Behdad)",
        persianSubtitle = "طراحی اصیل و چشم‌نواز برای مطالعه طولانی",
        regularRes = R.font.behdad,
        boldRes = R.font.behdad
    ),
    NIKA(
        persianTitle = "نیکا (Nika)",
        persianSubtitle = "خوانایی بالا در سایزهای کوچک موبایل",
        regularRes = R.font.nika,
        boldRes = R.font.nika
    ),
    SHAHAB(
        persianTitle = "شهاب (Shahab)",
        persianSubtitle = "مناسب سرتیترها و متن‌های آموزشی",
        regularRes = R.font.shahab,
        boldRes = R.font.shahab
    ),
    GANJNAMEH(
        persianTitle = "گنجنامه (Ganjnameh)",
        persianSubtitle = "فونت تمیز و هندسی برای رابط کاربری مدرن",
        regularRes = R.font.ganjnameh,
        boldRes = R.font.ganjnameh
    ),
    FARBOD(
        persianTitle = "فربد (Farbod)",
        persianSubtitle = "ویژه متون چاپی و درس‌نامه‌های طولانی",
        regularRes = R.font.farbod,
        boldRes = R.font.farbod
    ),
    VIZHEH_AZAD(
        persianTitle = "ویژه آزاد (Vizheh Azad)",
        persianSubtitle = "تیتر و متن با لبه‌های متمایز و شکیل",
        regularRes = R.font.vizheh_azad,
        boldRes = R.font.vizheh_azad
    ),
    IRAN_NASTALIQ(
        persianTitle = "ایران نستعلیق (Iran Nastaliq)",
        persianSubtitle = "خط نستعلیق اصیل ایرانی ویژه ادبیات و اشعار (اتصال ۱۰۰٪ حروف بدون فاصله)",
        regularRes = R.font.iran_nastaliq,
        boldRes = R.font.iran_nastaliq
    ),
    NOON_QURANIC(
        persianTitle = "نون قرآنی (Noon)",
        persianSubtitle = "فونت مخصوص آیات قرآن، دین و زندگی و عربی تخصصی",
        regularRes = R.font.noon,
        boldRes = R.font.noon
    ),

    // === ۴. فونت‌های کلاسیک و کتب درسی با اعراب کامل ===
    SCHEHERAZADE(
        persianTitle = "شهرزاد کتب درسی (Scheherazade)",
        persianSubtitle = "فونت رسمی کتب درسی با اعراب‌گذاری کامل",
        regularRes = R.font.scheherazade,
        boldRes = R.font.scheherazade_bold
    ),
    SCHEHERAZADE_NEW(
        persianTitle = "شهرزاد جدید (Scheherazade New)",
        persianSubtitle = "نسخه به‌روز شده شهرزاد برای عربی و علوم اسلامی",
        regularRes = R.font.scheherazade_new,
        boldRes = R.font.scheherazade_bold
    ),
    AMIRI(
        persianTitle = "امیری اصیل (Amiri)",
        persianSubtitle = "فونت کلاسیک قرآنی و ادبی ویژه عربی و دین و زندگی",
        regularRes = R.font.amiri,
        boldRes = R.font.amiri_bold
    ),
    HARMATTAN(
        persianTitle = "هارماتان (Harmattan)",
        persianSubtitle = "فونت نسخ روان با وضوح عالی برای مطالعه طولانی",
        regularRes = R.font.harmattan,
        boldRes = R.font.harmattan_bold
    ),
    LATEEF_ARABIC(
        persianTitle = "لطیف قرآنی و عربی (Lateef)",
        persianSubtitle = "خط نسخ لطیف و روان با فاصله‌گذاری استاندارد حرکات و اعراب",
        regularRes = R.font.lateef,
        boldRes = R.font.lateef
    ),
    AREF_RUQAA_ARABIC(
        persianTitle = "عارف رقعه عربی (Aref Ruqaa)",
        persianSubtitle = "خط رقعه خوشنویسی عربی با جلوه اصیل و هنری",
        regularRes = R.font.aref_ruqaa,
        boldRes = R.font.aref_ruqaa
    ),
    REEM_KUFI_ARABIC(
        persianTitle = "ریم کوفی مدرن (Reem Kufi)",
        persianSubtitle = "خط کوفی مدرن و شکیل عربی با خوانایی بالا",
        regularRes = R.font.reem_kufi,
        boldRes = R.font.reem_kufi
    ),
    MARKAZI_TEXT_ARABIC(
        persianTitle = "مرکزی نسخ مجله‌ای (Markazi Text)",
        persianSubtitle = "فونت نسخ مدرن ویژه مطالعه متون طولانی عربی و فارسی",
        regularRes = R.font.markazi_text,
        boldRes = R.font.markazi_text
    ),
    IBM_PLEX_ARABIC(
        persianTitle = "آی‌بی‌ام پلکس عربی (IBM Plex Arabic)",
        persianSubtitle = "فونت مهندسی‌شده و فوق‌شفاف برای متون آموزشی عربی",
        regularRes = R.font.ibm_plex_sans_arabic,
        boldRes = R.font.ibm_plex_sans_arabic
    ),
    READEX_PRO_ARABIC(
        persianTitle = "ریدکس پرو عربی (Readex Pro)",
        persianSubtitle = "طراحی مدرن ویژه افزایش سرعت قرائت و درک مطلب عربی",
        regularRes = R.font.readex_pro,
        boldRes = R.font.readex_pro
    ),
    LALEZAR_HEAVY(
        persianTitle = "لاله‌زار ضخیم (Lalezar)",
        persianSubtitle = "فونت فوق‌ضخیم و توپر برای دید حداکثری تیترها و نکات طلایی",
        regularRes = R.font.lalezar,
        boldRes = R.font.lalezar
    );

    fun toFontFamily(forceBold: Boolean = false): FontFamily {
        val key = "${this.name}_$forceBold"
        return fontFamilyCache.getOrPut(key) {
            if (this == VAZIRMATN) {
                createDualFontFamily(
                    regularRes = R.font.vazirmatn,
                    boldRes = R.font.vazirmatn_bold,
                    mediumRes = R.font.vazirmatn_medium,
                    lightRes = R.font.vazirmatn_thin,
                    blackRes = R.font.vazirmatn_black,
                    forceBold = forceBold
                )
            } else {
                createDualFontFamily(
                    regularRes = regularRes,
                    boldRes = boldRes,
                    forceBold = forceBold
                )
            }
        }
    }
}

private val fontFamilyCache = java.util.concurrent.ConcurrentHashMap<String, FontFamily>()

/**
 * Smart, uniform, standard typography scale where titles, subtitles, body texts, and labels
 * have balanced, harmonious proportions (no oversized or tiny texts).
 * Font weight (Normal / Medium / Bold) is 100% independent of font size and line height.
 */
fun buildAppTypography(
    bodyFontFamily: FontFamily = FontFamily.Default,
    headlineFontFamily: FontFamily = FontFamily.Default,
    scale: Float = 1.0f,
    isBoldText: Boolean = false,
    fontWeightLevel: Int = if (isBoldText) 2 else 0,
    lineSpacingScale: Float = 1.0f
): Typography {
    // CRITICAL: Font weight must NEVER alter fontSize or lineHeight!
    val effectiveScale = scale
    val lhScale = effectiveScale * lineSpacingScale.coerceIn(0.85f, 1.50f)
    val resolvedLevel = if (isBoldText && fontWeightLevel == 0) 2 else fontWeightLevel.coerceIn(0, 2)

    val displayWeight = when (resolvedLevel) {
        2 -> FontWeight.Bold
        1 -> FontWeight.SemiBold
        else -> FontWeight.Medium
    }
    val headlineWeight = when (resolvedLevel) {
        2 -> FontWeight.Bold
        1 -> FontWeight.SemiBold
        else -> FontWeight.Medium
    }
    val titleWeight = when (resolvedLevel) {
        2 -> FontWeight.Bold
        1 -> FontWeight.Medium
        else -> FontWeight.Normal
    }
    val bodyWeight = when (resolvedLevel) {
        2 -> FontWeight.Bold
        1 -> FontWeight.Medium
        else -> FontWeight.Normal
    }
    val labelWeight = when (resolvedLevel) {
        2 -> FontWeight.Bold
        1 -> FontWeight.Medium
        else -> FontWeight.Normal
    }

    val defaultFeatureSettings = "liga 1, rlig 1"
    val defaultLetterSpacing = androidx.compose.ui.unit.TextUnit.Unspecified
    val activeSynthesis = if (resolvedLevel == 2) FontSynthesis.Weight else FontSynthesis.None

    return Typography(
        displayLarge = TextStyle(
            fontFamily = headlineFontFamily,
            fontWeight = displayWeight,
            fontSynthesis = activeSynthesis,
            fontSize = (17.5f * effectiveScale).sp,
            lineHeight = (26f * lhScale).sp,
            letterSpacing = defaultLetterSpacing,
            fontFeatureSettings = defaultFeatureSettings
        ),
        displayMedium = TextStyle(
            fontFamily = headlineFontFamily,
            fontWeight = displayWeight,
            fontSynthesis = activeSynthesis,
            fontSize = (16.5f * effectiveScale).sp,
            lineHeight = (24.5f * lhScale).sp,
            letterSpacing = defaultLetterSpacing,
            fontFeatureSettings = defaultFeatureSettings
        ),
        displaySmall = TextStyle(
            fontFamily = headlineFontFamily,
            fontWeight = displayWeight,
            fontSynthesis = activeSynthesis,
            fontSize = (15.5f * effectiveScale).sp,
            lineHeight = (23.5f * lhScale).sp,
            letterSpacing = defaultLetterSpacing,
            fontFeatureSettings = defaultFeatureSettings
        ),
        headlineLarge = TextStyle(
            fontFamily = headlineFontFamily,
            fontWeight = headlineWeight,
            fontSynthesis = activeSynthesis,
            fontSize = (15.0f * effectiveScale).sp,
            lineHeight = (23f * lhScale).sp,
            letterSpacing = defaultLetterSpacing,
            fontFeatureSettings = defaultFeatureSettings
        ),
        headlineMedium = TextStyle(
            fontFamily = headlineFontFamily,
            fontWeight = headlineWeight,
            fontSynthesis = activeSynthesis,
            fontSize = (14.4f * effectiveScale).sp,
            lineHeight = (22f * lhScale).sp,
            letterSpacing = defaultLetterSpacing,
            fontFeatureSettings = defaultFeatureSettings
        ),
        headlineSmall = TextStyle(
            fontFamily = headlineFontFamily,
            fontWeight = headlineWeight,
            fontSynthesis = activeSynthesis,
            fontSize = (13.8f * effectiveScale).sp,
            lineHeight = (21.5f * lhScale).sp,
            letterSpacing = defaultLetterSpacing,
            fontFeatureSettings = defaultFeatureSettings
        ),
        titleLarge = TextStyle(
            fontFamily = headlineFontFamily,
            fontWeight = headlineWeight,
            fontSynthesis = activeSynthesis,
            fontSize = (15.2f * effectiveScale).sp,
            lineHeight = (25f * lhScale).sp,
            letterSpacing = defaultLetterSpacing,
            fontFeatureSettings = defaultFeatureSettings
        ),
        titleMedium = TextStyle(
            fontFamily = bodyFontFamily,
            fontWeight = titleWeight,
            fontSynthesis = activeSynthesis,
            fontSize = (14.2f * effectiveScale).sp,
            lineHeight = (23.5f * lhScale).sp,
            letterSpacing = defaultLetterSpacing,
            fontFeatureSettings = defaultFeatureSettings
        ),
        titleSmall = TextStyle(
            fontFamily = bodyFontFamily,
            fontWeight = titleWeight,
            fontSynthesis = activeSynthesis,
            fontSize = (13.4f * effectiveScale).sp,
            lineHeight = (22f * lhScale).sp,
            letterSpacing = defaultLetterSpacing,
            fontFeatureSettings = defaultFeatureSettings
        ),
        bodyLarge = TextStyle(
            fontFamily = bodyFontFamily,
            fontWeight = bodyWeight,
            fontSynthesis = activeSynthesis,
            fontSize = (14.2f * effectiveScale).sp,
            lineHeight = (26f * lhScale).sp,
            letterSpacing = defaultLetterSpacing,
            fontFeatureSettings = defaultFeatureSettings
        ),
        bodyMedium = TextStyle(
            fontFamily = bodyFontFamily,
            fontWeight = bodyWeight,
            fontSynthesis = activeSynthesis,
            fontSize = (13.2f * effectiveScale).sp,
            lineHeight = (24f * lhScale).sp,
            letterSpacing = defaultLetterSpacing,
            fontFeatureSettings = defaultFeatureSettings
        ),
        bodySmall = TextStyle(
            fontFamily = bodyFontFamily,
            fontWeight = bodyWeight,
            fontSynthesis = activeSynthesis,
            fontSize = (12.2f * effectiveScale).sp,
            lineHeight = (21.5f * lhScale).sp,
            letterSpacing = defaultLetterSpacing,
            fontFeatureSettings = defaultFeatureSettings
        ),
        labelLarge = TextStyle(
            fontFamily = bodyFontFamily,
            fontWeight = labelWeight,
            fontSynthesis = activeSynthesis,
            fontSize = (12.0f * effectiveScale).sp,
            lineHeight = (18.5f * lhScale).sp,
            letterSpacing = defaultLetterSpacing,
            fontFeatureSettings = defaultFeatureSettings
        ),
        labelMedium = TextStyle(
            fontFamily = bodyFontFamily,
            fontWeight = labelWeight,
            fontSynthesis = activeSynthesis,
            fontSize = (11.4f * effectiveScale).sp,
            lineHeight = (17.5f * lhScale).sp,
            letterSpacing = defaultLetterSpacing,
            fontFeatureSettings = defaultFeatureSettings
        ),
        labelSmall = TextStyle(
            fontFamily = bodyFontFamily,
            fontWeight = labelWeight,
            fontSynthesis = activeSynthesis,
            fontSize = (10.8f * effectiveScale).sp,
            lineHeight = (16.5f * lhScale).sp,
            letterSpacing = defaultLetterSpacing,
            fontFeatureSettings = defaultFeatureSettings
        )
    )
}

val Typography = buildAppTypography()
