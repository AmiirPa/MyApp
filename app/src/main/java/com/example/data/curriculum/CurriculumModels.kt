package com.example.data.curriculum

import androidx.compose.ui.graphics.Color
import com.example.ui.theme.SubjectArabicColor
import com.example.ui.theme.SubjectAroozColor
import com.example.ui.theme.SubjectEnglishColor
import com.example.ui.theme.SubjectFarhangianColor
import com.example.ui.theme.SubjectFonoonColor
import com.example.ui.theme.SubjectMathColor
import com.example.ui.theme.SubjectPhilosophyColor

data class SubjectMeta(
    val id: String,
    val shortTitle: String,
    val title: String,
    val subtitle: String,
    val konkoorCoefficient: String,
    val targetForZeroStudent: String,
    val color: Color,
    val zeroReassurance: String
)

data class ZeroStep(
    val stepNumber: Int,
    val title: String,
    val simpleExplanation: String,
    val visualDiagramText: String = "",
    val memoryTrick: String = ""
) {
    constructor(
        stepNumber: Int,
        title: String,
        explanation: String
    ) : this(
        stepNumber = stepNumber,
        title = title,
        simpleExplanation = explanation
    )
}

data class WorkedExample(
    val question: String,
    val stepByStepSolution: String,
    val commonTrapAlert: String
) {
    constructor(
        question: String,
        stepByStepSolution: String,
        finalAnswer: String,
        isChapterExample: Boolean = true
    ) : this(
        question = question,
        stepByStepSolution = stepByStepSolution,
        commonTrapAlert = "پاسخ نهایی و نکته کلیدی: $finalAnswer"
    )
}

data class MiniCheckQuestion(
    val question: String,
    val options: List<String>,
    val correctIndex: Int,
    val explanation: String
)

data class ZeroLesson(
    val id: String,
    val subjectId: String,
    val orderIndex: Int,
    val levelBadge: String, // "سطح منفی صفر (-۰)", "سطح صفر (۰)", "سطح پایه (۱)", "سطح کنکور (۲)"
    val title: String,
    val subtitle: String,
    val estimatedMinutes: Int,
    val whyStudentsStruggle: String,
    val steps: List<ZeroStep>,
    val goldenFormulas: List<String>,
    val workedExamples: List<WorkedExample>,
    val miniCheck: MiniCheckQuestion
) {
    constructor(
        id: String,
        subjectId: String,
        orderIndex: Int,
        levelTitle: String,
        title: String,
        zeroHook: String,
        steps: List<ZeroStep>,
        goldenFormulas: List<String>,
        workedExamples: List<WorkedExample>,
        miniCheck: MiniCheckQuestion
    ) : this(
        id = id,
        subjectId = subjectId,
        orderIndex = orderIndex,
        levelBadge = levelTitle,
        title = title,
        subtitle = zeroHook,
        estimatedMinutes = 20,
        whyStudentsStruggle = zeroHook,
        steps = steps,
        goldenFormulas = goldenFormulas,
        workedExamples = workedExamples,
        miniCheck = miniCheck
    )
}

data class FlashcardItem(
    val id: String,
    val subjectId: String,
    val categoryTag: String,
    val frontQuestion: String,
    val backAnswer: String,
    val memoryHook: String
)

data class QuizQuestionItem(
    val id: String,
    val subjectId: String,
    val difficultyTag: String,
    val questionText: String,
    val options: List<String>,
    val correctOptionIndex: Int,
    val zeroLevelSolution: String,
    val shortcutTechnique: String
)

object SubjectCatalog {
    val subjects = listOf(
        SubjectMeta(
            id = "math",
            shortTitle = "ریاضی و آمار",
            title = "ریاضی و آمار ۱، ۲ و ۳",
            subtitle = "آموزش گام‌به‌گام از پایه: معادله، تابع، آمار، احتمال و دنباله‌ها",
            konkoorCoefficient = "ضریب ۲ کنکور انسانی + امتحان نهایی",
            targetForZeroStudent = "هدف قطعی: تسلط کامل بر سوالات تستی و تشریحی",
            color = SubjectMathColor,
            zeroReassurance = "از مفاهیم اولیه و بدون فرمول‌های سخت، تمام تیپ‌های سوال را یاد می‌گیری!"
        ),
        SubjectMeta(
            id = "fonoon",
            shortTitle = "علوم و فنون ادبی",
            title = "علوم و فنون ادبی ۱، ۲ و ۳",
            subtitle = "آرایه‌ها، تاریخ ادبیات، سبک‌شناسی و عروض و قافیه",
            konkoorCoefficient = "ضریب ۳ کنکور انسانی (رتبه‌سازترین درس)",
            targetForZeroStudent = "هدف قطعی: نمره کامل نهایی و درصد بالای ۸۰ کنکور",
            color = SubjectFonoonColor,
            zeroReassurance = "تاریخ ادبیات، سبک‌شناسی و آرایه‌ها را با الگوهای چشمی و مفهومی یاد می‌گیری."
        ),

        SubjectMeta(
            id = "sociology",
            shortTitle = "جامعه‌شناسی",
            title = "جامعه‌شناسی ۱، ۲ و ۳",
            subtitle = "کنش اجتماعی، جهان‌های فرهنگی، هویت و نظام نوین جهانی",
            konkoorCoefficient = "ضریب ۲ کنکور انسانی + امتحان نهایی",
            targetForZeroStudent = "هدف قطعی: درک مفهومی متن کتاب و نمره ۲۰ نهایی",
            color = Color(0xFF0D9488),
            zeroReassurance = "تمام اصطلاحات جامعه‌شناسی را با مثال‌های ملموس جامعه و زندگی یاد می‌گیری."
        ),
        SubjectMeta(
            id = "psychology",
            shortTitle = "روان‌شناسی",
            title = "روان‌شناسی پایه یازدهم",
            subtitle = "رشد، احساس و ادراک، حافظه، تفکر، تصمیم‌گیری، انگیزش و سلامت روان",
            konkoorCoefficient = "ضریب ۲ کنکور انسانی (۲۰ سوال)",
            targetForZeroStudent = "هدف قطعی: تسلط ۱۰۰٪ بر ۸ درس کتاب درسی",
            color = Color(0xFF0891B2),
            zeroReassurance = "شیرین‌ترین درس کنکور را با کاربردهای روزمره و سناریوهای واقعی بیاموز."
        ),
        SubjectMeta(
            id = "economics",
            shortTitle = "اقتصاد",
            title = "اقتصاد پایه دهم",
            subtitle = "مفاهیم بازار، عرضه و تقاضا، پول و بانک، تورم و مالیات و حل مسائل",
            konkoorCoefficient = "ضریب ۱ کنکور انسانی (۱۵ سوال)",
            targetForZeroStudent = "هدف قطعی: حل سریع مسائل محاسباتی و تست‌های مفهومی",
            color = Color(0xFF059669),
            zeroReassurance = "روش‌های گام‌به‌گام برای مسائل مالیات، دهک‌ها و ارزش افزوده بدون سردرگمی."
        ),
        SubjectMeta(
            id = "philosophy",
            shortTitle = "فلسفه",
            title = "فلسفه ۱ و ۲ (یازدهم و دوازدهم)",
            subtitle = "چیستی فلسفه، معرفت‌شناسی، اصالت وجود، علیت و مکاتب فلسفی",
            konkoorCoefficient = "ضریب ۳ کنکور انسانی + امتحان نهایی فلسفه ۲",
            targetForZeroStudent = "هدف قطعی: درک عمیق مسائل هستی‌شناسی و نمره ۲۰ نهایی",
            color = SubjectPhilosophyColor,
            zeroReassurance = "سیر اندیشه فیلسوفان را با مقایسه‌های روشن و دسته‌بندی‌شده فرا بگیر."
        ),
        SubjectMeta(
            id = "logic",
            shortTitle = "منطق",
            title = "منطق پایه دهم",
            subtitle = "تعریف، نسبت‌های چهارگانه، قضیه حملی و شرطی، قیاس و مغالطات",
            konkoorCoefficient = "ضریب ۳ کنکور انسانی (بخش دهم فلسفه و منطق)",
            targetForZeroStudent = "هدف قطعی: پاسخگویی به تمام تست‌های منطق و آزمون نهایی",
            color = Color(0xFF6366F1),
            zeroReassurance = "با یادگیری شرایط انتاج و قواعد عکس مستوی، تست‌های منطق قطعی حل می‌شوند."
        ),
        SubjectMeta(
            id = "history",
            shortTitle = "تاریخ",
            title = "تاریخ ۱، ۲ و ۳",
            subtitle = "ایران و جهان باستان، تاریخ اسلام و ایران، و تاریخ معاصر",
            konkoorCoefficient = "ضریب ۱ کنکور انسانی + امتحان نهایی تاریخ ۳",
            targetForZeroStudent = "هدف قطعی: تسلط بر خط زمانی رویدادها، سلسله‌ها و پیمان‌ها",
            color = Color(0xFFD97706),
            zeroReassurance = "رویدادها را بر محور زمان پیوسته و با جداول منظم به خاطر بسپار."
        ),
        SubjectMeta(
            id = "geography",
            shortTitle = "جغرافیا",
            title = "جغرافیای ۱، ۲ و ۳",
            subtitle = "جغرافیای ایران، جغرافیای ناحیه‌ای و جغرافیای کاربردی",
            konkoorCoefficient = "ضریب ۱ کنکور انسانی + امتحان نهایی جغرافیا ۳",
            targetForZeroStudent = "هدف قطعی: یادگیری کامل نقشه‌ها، نمودارها و مسائل مقیاس",
            color = Color(0xFFEA580C),
            zeroReassurance = "مفاهیم محیطی، زیست‌بوم‌ها و عوارض زمین را با نقشه‌های واضح مرور کن."
        ),
        SubjectMeta(
            id = "arabic",
            shortTitle = "عربی",
            title = "عربی، زبان قرآن ۱، ۲ و ۳",
            subtitle = "ترجمه و تعریب، قواعد ثلاثی مزید، مشتقات، اعراب، حال، تمییز و استثناء",
            konkoorCoefficient = "ضریب ۲ کنکور انسانی + امتحان نهایی عربی ۳",
            targetForZeroStudent = "هدف قطعی: تسلط کامل بر ترجمه و قواعد در خدمت فهم متن",
            color = SubjectArabicColor,
            zeroReassurance = "با تمرکز بر نشانه‌های کلیدی، در کوتاه‌ترین زمان به سوالات عربی پاسخ بده."
        ),
        SubjectMeta(
            id = "english",
            shortTitle = "زبان انگلیسی",
            title = "زبان انگلیسی (پایه‌های دهم تا دوازدهم)",
            subtitle = "گرامر، واژگان کلیدی، کلوزتست و درک مطلب",
            konkoorCoefficient = "تأثیر مستقیم در سوابق تحصیلی امتحانات نهایی",
            targetForZeroStudent = "هدف قطعی: نمره کامل ۲۰ در امتحان نهایی زبان انگلیسی",
            color = SubjectEnglishColor,
            zeroReassurance = "با ساختار گرامری شفاف و الگوهای تکرارشونده، زبان را از پایه بیاموز."
        ),
        SubjectMeta(
            id = "dini",
            shortTitle = "دین و زندگی",
            title = "دین و زندگی ۱، ۲ و ۳ جامع (دهم ۱۴۰۳، یازدهم ۱۴۰۴، دوازدهم ۱۴۰۵)",
            subtitle = "آموزش خط‌به‌خط تمام دروس دینی ۱۰، ۱۱ و ۱۲ انسانی + بانک پیام آیات، توحید ذاتی/صفاتی/افعالی/عملی، قضا و قدر و معاد",
            konkoorCoefficient = "ضریب بالا در سوابق تحصیلی نهایی (۶۰٪) + دفترچه اختصاصی فرهنگیان",
            targetForZeroStudent = "هدف قطعی: نمره ۲۰ نهایی دینی و ۱۰۰٪ تست‌های دینی فرهنگیان ۱۴۰۶",
            color = Color(0xFF15803D),
            zeroReassurance = "تک‌تک آیات عربی کتاب دینی دهم، یازدهم و دوازدهم را با ۱ کلیدواژهٔ رنگی به مفهوم فارسی‌اش قفل کرده‌ایم!"
        ),
        SubjectMeta(
            id = "farsi",
            shortTitle = "فارسی ۱، ۲ و ۳",
            title = "فارسی ۱، ۲ و ۳ جامع (دستور، لغت، املا و سه قلمرو)",
            subtitle = "از کلاس اول ابتدایی تا فارسی دوازدهم (چاپ ۱۴۰۵): نقش‌های دستوری، ساختمان واژه، گروه اسمی، املا و قلمروهای زبانی، ادبی و فکری",
            konkoorCoefficient = "بالاترین ضریب در ۶۰٪ سوابق تحصیلی امتحان نهایی دوازدهم",
            targetForZeroStudent = "هدف قطعی: نمره ۲۰ قطعی در امتحان نهایی فارسی ۳",
            color = Color(0xFFBE185D),
            zeroReassurance = "تمام نقش‌های دستوری (نهاد، مفعول، متمم، مسند)، ساختمان واژه (ساده، مشتق، مرکب) و املای هم‌آواها را یک‌جا داری!"
        ),
        SubjectMeta(
            id = "negaresh",
            shortTitle = "نگارش و انشا",
            title = "نگارش ۱، ۲ و ۳ و قالب آماده نمره ۲۰ امتحان نهایی",
            subtitle = "آموزش عملی مثل‌نویسی، حکایت‌نگاری، بازآفرینی، زندگینامه‌نویسی، گزارش‌نویسی و قالب‌های آماده انشای استدلالی و تحلیلی",
            konkoorCoefficient = "امتحان نهایی پایه دوازدهم (تأثیر مستقیم در تراز سوابق تحصیلی)",
            targetForZeroStudent = "هدف قطعی: نمره ۲۰ قطعی در امتحان نهایی نگارش ۳ بدون نیاز به ذوق نویسندگی قبلی",
            color = Color(0xFFDB2777),
            zeroReassurance = "حتی اگر نمی‌دانی انشا را از کجا شروع کنی، با فرمول ۵ پاراگرافی و جملات طلایی آماده، برگه‌ات ۲۰ کامل می‌شود!"
        ),
        SubjectMeta(
            id = "farhangian",
            shortTitle = "منابع فرهنگیان",
            title = "منابع اختصاصی دانشگاه فرهنگیان ۱۴۰۶ (قرائتی، هوش و مصاحبه)",
            subtitle = "صفحه ۰ تا آخر مهارت‌های معلمی استاد قرائتی + سند تحول بنیادین + هوش و استعداد معلمی + سناریوهای کانون ارزیابی و مصاحبه",
            konkoorCoefficient = "دفترچه اختصاصی کنکور فرهنگیان ۱۴۰۶ + مصاحبه گزینش",
            targetForZeroStudent = "هدف قطعی: قبولی قطعی در دانشگاه فرهنگیان ۱۴۰۶ از رشته انسانی",
            color = SubjectFarhangianColor,
            zeroReassurance = "همه منابع دفترچه اختصاصی فرهنگیان (قرائتی، هوش معلمی، دینی) و سوالات واقعی کانون ارزیابی و مصاحبه حضوری آماده است."
        ),
        SubjectMeta(
            id = "social_identity",
            shortTitle = "هویت اجتماعی ۱۲",
            title = "هویت اجتماعی دوازدهم (چاپ ۱۴۰۵ ویژه امتحان نهایی)",
            subtitle = "صفحه ۰ تا آخر فصل‌های ۱ تا ۶ هویت اجتماعی ۱۲: کنش اجتماعی، هویت فردی و جمعی، تحولات هویتی، هویت ایرانی-اسلامی و جهانی‌شدن",
            konkoorCoefficient = "امتحان نهایی دوازدهم (تأثیر مستقیم در ۶۰٪ سوابق تحصیلی)",
            targetForZeroStudent = "هدف قطعی: نمره ۲۰ کامل در امتحان نهایی هویت اجتماعی دوازدهم",
            color = Color(0xFF4F46E5),
            zeroReassurance = "تمام تعاریف، نمودارها و سوالات پرتکرار امتحان نهایی هویت اجتماعی دوازدهم را خط‌به‌خط و خلاصه آماده کرده‌ایم."
        ),
        SubjectMeta(
            id = "general_humanities",
            shortTitle = "سلامت و بهداشت ۱۲",
            title = "سلامت و بهداشت دوازدهم (چاپ ۱۴۰۵ ویژه امتحان نهایی)",
            subtitle = "صفحه ۰ تا آخر سلامت و بهداشت ۱۲: ابعاد سلامت، تغذیه و فرمول BMI، بیماری‌های واگیر و غیرواگیر، بهداشت روان، اعتیاد و کمک‌های اولیه",
            konkoorCoefficient = "امتحان نهایی دوازدهم (تأثیر مستقیم در ۶۰٪ سوابق تحصیلی)",
            targetForZeroStudent = "هدف قطعی: نمره ۲۰ کامل در امتحان نهایی سلامت و بهداشت دوازدهم",
            color = Color(0xFF0284C7),
            zeroReassurance = "از فرمول نمایه توده بدنی (BMI) تا جدول بیماری‌ها و سوالات قطعی امتحان نهایی سلامت و بهداشت را در کمترین زمان جمع‌بندی کن."
        ),
        SubjectMeta(
            id = "media_environment",
            shortTitle = "رسانه، محیط زیست و خانواده",
            title = "تفکر و سواد رسانه‌ای ۱۰، انسان و محیط زیست ۱۱ و مدیریت خانواده ۱۲",
            subtitle = "پوشش کامل دروس مهارتی و عمومی: فنون اقناع و رژیم مصرف رسانه، آب مجازی و ردپای محیط‌زیستی، مدیریت خانواده و کارآفرینی",
            konkoorCoefficient = "امتحانات نهایی و داخلی پایه دهم، یازدهم و دوازدهم انسانی",
            targetForZeroStudent = "هدف قطعی: نمره ۲۰ در امتحانات سواد رسانه‌ای، محیط زیست، مدیریت خانواده و کارآفرینی",
            color = Color(0xFF16A34A),
            zeroReassurance = "تمام مفاهیم کلیدی سواد رسانه‌ای، محیط زیست، مدیریت خانواده و کارآفرینی را با جدول و مثال‌های واقعی یک‌جا داری."
        ),
        SubjectMeta(
            id = "study_methods",
            shortTitle = "روش‌های نوین یادگیری",
            title = "روش‌های نوین یادگیری، تندخوانی و تکنیک‌های تست‌زنی کنکور",
            subtitle = "تکنیک فاینمن، بازیابی فعال (Active Recall)، مرور فاصله‌دار لایتنر، یادداشت‌برداری کرنل، نقشه ذهنی، تندخوانی و مدیریت زمان کنکور",
            konkoorCoefficient = "شتاب‌دهنده ۳ برابری سرعت مطالعه و حافظه",
            targetForZeroStudent = "هدف قطعی: یادگیری ۳ برابر سریع‌تر بدون فراموشی + مدیریت زمان حرفه‌ای سر جلسه کنکور",
            color = Color(0xFF7C3AED),
            zeroReassurance = "یاد می‌گیری چطور هر درس را با تکنیک فاینمن و بازیابی فعال طوری بخوانی که تا روز کنکور و امتحان نهایی ۱٪ هم یادت نرود!"
        )
    )

    fun getSubjectById(id: String): SubjectMeta {
        val mappedId = when (id) {
            "history_geo" -> "history"
            "social_psych_econ" -> "sociology"
            else -> id
        }
        return subjects.find { it.id == mappedId } ?: subjects.first()
    }
}

fun ZeroLesson.normalizedSubjectId(): String = when (id) {
    // Logic lessons separated into "logic"
    "phil_00_khodemoni_logic_10_complete",
    "phil_01_logic_concepts_five_universals",
    "phil_02_logic_propositions_square",
    "phil_03_logic_syllogisms_fallacies",
    "philo_01_logic_concepts_definitions",
    "philo_02_logic_propositions_square",
    "philo_03_logic_syllogism_conditional",
    "logic_10_lesson1_2_concepts_terms",
    "logic_10_lesson3_4_relations_five_universals",
    "logic_10_lesson5_definitions",
    "logic_phil_complete_mastery" -> "logic"

    // History lessons separated into "history"
    "hist_00_elementary_to_9th_zero",
    "hist_01_grade10_1403",
    "hist_02_grade11_1404",
    "hist_03_grade12_1405",
    "hist_04_treaties_kings_konkoor_tables" -> "history"

    // Geography lessons separated into "geography"
    "geo_00_zero_maps_coordinates",
    "geo_01_grade10_1403",
    "geo_10_ch1_location_topography_climate_rivers",
    "geo_10_ch3_economy_transport_borders",
    "geo_02_grade11_1404",
    "geo_11_ch1_regions_biomes_culture",
    "geo_11_ch3_case_studies_sw_asia_europe",
    "geo_03_grade12_1405",
    "geo_12_ch1_applied_geography_hazards" -> "geography"

    // Sociology lessons separated into "sociology"
    "soc_000_khodemoni_zero_to_100",
    "soc_00_elementary_to_9th_zero",
    "soc_01_grade10_1403_complete",
    "soc_01b_grade10_identity_mobility_control",
    "soc_10_ch1_human_social_action",
    "soc_10_ch2_culture_layers_values",
    "soc_02_grade11_1404_complete",
    "soc_02b_grade11_west_islam_modernity",
    "soc_11_ch1_cultural_world_globalization",
    "soc_03_grade12_1405_complete",
    "soc_12_ch1_three_approaches_social_sciences" -> "sociology"

    // Psychology lessons separated into "psychology"
    "psych_00_zero_khodemoni",
    "psych_01_grade11_1404_complete",
    "psych_02_sensation_memory_forgetting" -> "psychology"

    // Economics lessons separated into "economics"
    "econ_01_grade10_1403_complete",
    "econ_02_math_problems_shortcuts_100",
    "econ_03_gdp_inflation_unemployment_budget" -> "economics"

    // Din o Zendegi 1, 2, 3 separated into "dini"
    "dini_01_grade10_1403_complete",
    "dini_10_ch1_purpose_creation_theology",
    "dini_10_ch3_friendship_ethics_society",
    "dini_02_grade11_1404_complete",
    "dini_11_ch1_divine_guidance_quran_prophet",
    "dini_11_ch3_imams_life_ghaybat_velayat",
    "dini_03_grade12_1405_complete",
    "far_05_dini_farhangian_ensani",
    "far_08_dini_complete_10_11_12_1406" -> "dini"

    // Farsi 1, 2, 3 separated into "farsi"
    "far_09_farsi_grammar_1_to_12_1406",
    "farsi_02_vocab_spelling_3_realms",
    "farsi_10_ch2_literary_intellectual_realms",
    "farsi_12_ch1_syntax_noun_phrase_realms",
    "farsi_12_ch2_final_exam_vocab_spelling_authors" -> "farsi"

    // Negaresh 1, 2, 3 separated into "negaresh"
    "farsi_03_negaresh_final_exam_20",
    "negaresh_10_ch2_proverb_parable_writing",
    "negaresh_master_10_11_12_complete" -> "negaresh"

    // Social Identity 12 separated into "social_identity"
    "gen_02_hoviyat_family_grade12_1405",
    "hoviyat_04_iranian_islamic_identity_globalization",
    "hoviyat_master_grade12_final20" -> "social_identity"

    // Health & Hygiene 12 in "general_humanities"
    "gen_01_salamat_grade12_1405",
    "salamat_01_health_dimensions_nutrition_bmi",
    "salamat_master_grade12_final20" -> "general_humanities"

    // Media Literacy 10, Environment 11, Family Management 12 in "media_environment"
    "gen_03_mohit_zist_grade11_1404",
    "gen_04_savad_resaneh_karafarini_grade10_1403",
    "resaneh_10_ch2_media_diet_ethics_entrepreneurship",
    "mohit_11_ch2_biodiversity_energy_pollution",
    "family_12_management_lifestyle_complete",
    "far_10_remaining_humanities_books_10_11_12" -> "media_environment"

    // Modern Learning Methods & Konkoor Strategies in "study_methods"
    "study_01_feynman_active_recall_leitner",
    "study_02_cornell_mindmap_speed_reading",
    "study_03_konkoor_test_strategy_final20",
    "study_04_feynman_technique_deep_mastery" -> "study_methods"

    else -> when {
        id.startsWith("math_") -> "math"
        id.startsWith("fonoon_") -> "fonoon"
        id.startsWith("arooz_") -> "arooz"
        id.startsWith("soc_") -> "sociology"
        id.startsWith("psych_") -> "psychology"
        id.startsWith("econ_") -> "economics"
        id.startsWith("phil_") || id.startsWith("philo_") -> "philosophy"
        id.startsWith("logic_") -> "logic"
        id.startsWith("hist_") -> "history"
        id.startsWith("geo_") -> "geography"
        id.startsWith("arabic_") -> "arabic"
        id.startsWith("eng_") -> "english"
        id.startsWith("dini_") -> "dini"
        id.startsWith("farsi_") -> "farsi"
        id.startsWith("negaresh_") -> "negaresh"
        id.startsWith("salamat_") -> "general_humanities"
        id.startsWith("resaneh_") || id.startsWith("mohit_") || id.startsWith("family_") -> "media_environment"
        id.startsWith("study_") -> "study_methods"
        id.startsWith("far_") -> "farhangian"
        else -> when (subjectId) {
            "history_geo" -> if (id.startsWith("geo_")) "geography" else "history"
            "social_psych_econ" -> when {
                id.startsWith("econ_") -> "economics"
                id.startsWith("psych_") -> "psychology"
                else -> "sociology"
            }
            else -> subjectId
        }
    }
}

/**
 * Organizes every lesson into its official Textbook / Level Subcategory Folder
 * with verified textbook edition years for the Konkoor 1406 cohort:
 * - Grade 10: چاپ ۱۴۰۳ (سال تحصیلی ۱۴۰۳-۱۴۰۴)
 * - Grade 11: چاپ ۱۴۰۴ (سال تحصیلی ۱۴۰۴-۱۴۰۵)
 * - Grade 12: چاپ ۱۴۰۵ (سال تحصیلی ۱۴۰۵-۱۴۰۶)
 */
fun ZeroLesson.subcategoryFolder(): Pair<Int, String> = when (id) {
    // 1. Math & Statistics (ریاضی و آمار ۱، ۲ و ۳ انسانی + پایه اول تا نهم)
    "math_000_bismillah_mastery",
    "math_00_elementary_school",
    "math_01_signs_fractions",
    "math_01b_powers_radicals_identities",
    "math_02b_sets_venn_intervals",
    "found1406-math-01-add-subtract",
    "found1406-math-02-multiplication-fractions" -> 1 to "📘 پایه اول ابتدایی تا نهم — مبانی محاسبات، مجموعه اعداد و کسرها"

    "math_02_equations",
    "math_03_radicals_powers",
    "math_04_functions_domain",
    "math_06_statistics_dispersion",
    "math_09_statistics_percentiles",
    "math_10_ch4_data_display",
    "math_03_functions" -> 2 to "📗 پایه دهم انسانی (چاپ ۱۴۰۳ ویژه کنکور ۱۴۰۶) — کتاب ریاضی و آمار ۱"

    "math_08_logic_propositions",
    "math_11_grade11_functions_exponential",
    "math_07_grade12_regression_time_series",
    "math_05_logic_stats_prob",
    "math_05c_grade11_functions_indices" -> 3 to "📙 پایه یازدهم انسانی (چاپ ۱۴۰۴ ویژه کنکور ۱۴۰۶) — کتاب ریاضی و آمار ۲"

    "math_07_combinatorics_probability",
    "math_10_conditional_probability",
    "math_05_sequences_arithmetic",
    "math_12_konkoor_80_percent_shortcuts",
    "math_04_sequences",
    "math_05b_counting_permutations_combinations",
    "math_06_grade12_normal_conditional_probability",
    "math_ultra_fast_techniques" -> 4 to "📕 پایه دوازدهم انسانی (چاپ ۱۴۰۵ ویژه کنکور ۱۴۰۶) — کتاب ریاضی و آمار ۳"

    // 2. Arabic (عربی، زبان قرآن ۱، ۲ و ۳ اختصاصی انسانی + پایه هفتم تا نهم)
    "arabic_step01_alphabet_diacritics_nouns",
    "arabic_0000_absolute_zero_7th_to_12th_khodemoni",
    "arabic_01_word_types_pronouns",
    "arabic_step03_complete_14_sigha_tables",
    "arabic_00_sigha_hand_code",
    "arabic_00_seventh_grade_sigha",
    "arabic_000_bismillah_mastery",
    "found1406-ar-01-alphabet-reading",
    "found1406-ar-02-sigha-what-is" -> 1 to "📘 پایه هفتم تا نهم (از زیرِ صفر به زبان خودمونی) — حرکات، اسم و فعل، ضمایر و ۱۴ صیغه"

    "arabic_01b_numbers_time_nouns",
    "arabic_01_sigha_fingerprint",
    "arabic_step06_all_8_abwab_master_table",
    "arabic_02_verbs_abwab",
    "arabic_02_feal_bab_majhool",
    "ar1406-01-word-types-sigha",
    "ar1406-02-past-present-tenses",
    "ar1406-03-mujarrad-mazeed-1",
    "ar1406-04-mazeed-2",
    "ar1406-05-lesson5-nominal-sentence",
    "ar1406-06-lesson5-verbal-sentence" -> 2 to "📗 پایه دهم انسانی (چاپ ۱۴۰۳ ویژه کنکور ۱۴۰۶) — کتاب عربی، زبان قرآن ۱"

    "arabic_02b_derived_nouns_qaleb",
    "arabic_11_ch1_tafzeel_makan_zaman",
    "arabic_11_ch2_shart_and_naqeseh",
    "arabic_11_ch3_moqarabeh_vasfiyeh_huruf",
    "arabic_03b_grade11_shart_naqeseh_vasf" -> 3 to "📙 پایه یازدهم انسانی (چاپ ۱۴۰۴ ویژه کنکور ۱۴۰۶) — کتاب عربی، زبان قرآن ۲"

    "arabic_03_grade12_rules",
    "arabic_03_mansoobat_grade12",
    "arabic_04_grade12_istisna_hasr_hal",
    "arabic_step11_mansoobat_estisna_monada_table",
    "arabic_05_konkoor_translation_keys",
    "arabic_06_konkoor_100_tables_and_traps",
    "arabic_04_mnemonics_final20",
    "arabic_ultra_fast_techniques",
    "arabic_visual_persian_mastery",
    "ar12-01-lesson1-la-nafy-jins",
    "ar12-02-lesson2-hal",
    "ar12-03-lesson3-istithna-tamyiz",
    "ar12-04-lesson4-5-monada-mafoul-ialal" -> 4 to "📕 پایه دوازدهم انسانی (چاپ ۱۴۰۵ ویژه کنکور ۱۴۰۶) — کتاب عربی، زبان قرآن ۳ و جمع‌بندی ۱۰۰٪"

    // 3. English (Prospect 1-3 & Vision 1, 2, 3)
    "english_000_zero_dictionary_grammar_words",
    "english_step01_pronouns_tobe_sentence_table",
    "english_01_sentence_zero",
    "english_step03_all_tenses_step_by_step_table",
    "english_00_seventh_grade_train",
    "english_00_seventh_grade_abc",
    "english_000_bismillah_mastery",
    "found1406-en-01-alphabet-sounds",
    "found1406-en-02-first-words" -> 1 to "📘 پایه هفتم تا نهم (از زیرِ صفر) — معنی کلمات گرامری (Verb، Subject...) و جمله‌سازی پایه"

    "english_01_tenses_modals",
    "english_01b_nouns_comparatives_adjectives",
    "english_10_vision1_lesson1_saving_nature",
    "english_10_vision1_lesson2_wonders_creation",
    "english_10_vision1_lesson3_value_knowledge",
    "eng1406-04-vision1-traveling" -> 2 to "📗 پایه دهم (چاپ ۱۴۰۳ ویژه کنکور ۱۴۰۶) — کتاب Vision 1 کامل"

    "english_01c_gerund_infinitive_conjunctions",
    "english_11_vision2_lesson1_healthy_lifestyle",
    "english_11_vision2_lesson2_gerund_infinitive",
    "english_11_vision2_lesson3_culture_and_art" -> 3 to "📙 پایه یازدهم (چاپ ۱۴۰۴ ویژه کنکور ۱۴۰۶) — کتاب Vision 2 کامل"

    "english_02_grade12_grammar",
    "english_04_tag_questions_condition_passive",
    "english_step11_passive_conditionals_relative_tags",
    "english_03_grade12_reading_cloze_vocab",
    "english_05_konkoor_100_master_tables",
    "english_06_full_vocab_7_to_12_konkoor",
    "english_full_nahaee_konkoor",
    "english_full_persian_mastery",
    "eng1406-01-vision3-appreciation",
    "eng1406-02-vision3-look-it-up",
    "eng1406-03-vision3-renewable-energy" -> 4 to "📕 پایه دوازدهم (چاپ ۱۴۰۵ ویژه کنکور ۱۴۰۶) — کتاب Vision 3 + بانک کامل لغات هفتم تا دوازدهم"

    // 4. Arooz & Prosody (موسیقی شعر و قافیه — علوم و فنون ۱، ۲ و ۳)
    "arooz_00_clapping_zero",
    "arooz_01_syllables_zero",
    "arooz_02_cutting_u_dash",
    "aroozm1406-01-rhythm-clap",
    "aroozm1406-02-taghtee",
    "aroozm1406-03-poetry-hook" -> 1 to "📘 مبانی پایه و علوم و فنون ۱ (چاپ ۱۴۰۳) — بخش کردن کلمات، هجای کوتاه و بلند و خط عروضی"

    "arooz_04_ghafieh_rules",
    "arooz_02_ghafieh_radif",
    "arooz_10_ch1_syllables_and_script",
    "arooz_10_ch2_ghafieh_tabsareha" -> 2 to "📗 پایه دهم انسانی (چاپ ۱۴۰۳ ویژه کنکور ۱۴۰۶) — علوم و فنون ادبی ۱ (خط عروضی، هجا، قافیه و ردیف)"

    "arooz_00_bahr_barcode",
    "arooz_03_samaei_rhythms",
    "arooz_03_meters_samaei",
    "arooz_11_ch2_nahamsan_meters",
    "arooz_04_mnemonics_mastery" -> 3 to "📙 پایه یازدهم انسانی (چاپ ۱۴۰۴ ویژه کنکور ۱۴۰۶) — علوم و فنون ادبی ۲ (اوزان همسان و دولختی)"

    "arooz_03b_poetic_licenses",
    "arooz_05_all_16_konkoor_meters",
    "arooz_06_konkoor_100_speed_scansion",
    "arooz_fonoon_ultra_fast_techniques",
    "arooz_master_complete_100",
    "arooz12-01-dowri",
    "arooz12-02-meters12",
    "arooz12-03-licenses",
    "arooz12-04-ghafieh-advanced" -> 4 to "📕 پایه دوازدهم انسانی (چاپ ۱۴۰۵ ویژه کنکور ۱۴۰۶) — علوم و فنون ادبی ۳ (اوزان دوری، ۱۶ وزن و اختیارات شاعری)"

    // 5. Fonoon (زیبایی‌شناسی، تاریخ ادبیات و سبک‌شناسی — علوم و فنون ۱، ۲ و ۳)
    "fonoon_00_zero_to_100_all_24_arrayeh_step_by_step" -> 1 to "📘 پیش‌نیاز از منفی صفر — آموزش پله‌به‌پله تمام ۲۴ آرایه ادبی از ساده‌ترین تا پیشرفته‌ترین"

    "fonoon_10_ch1_history_khorasani",
    "fonoon_10_ch2_badi_tarsee_movazeneh",
    "fonoon_10_ch3_tashbih_majaz_10",
    "fonoon_01b_verbal_figures_saj" -> 2 to "📗 پایه دهم انسانی (چاپ ۱۴۰۳ ویژه کنکور ۱۴۰۶) — علوم و فنون ادبی ۱ کامل (درس ۱ تا ۱۲)"

    "fonoon_11_ch1_history_eraqi",
    "fonoon_11_ch2_tashbih_types",
    "fonoon_11_ch3_estearah_majaz_kenayeh",
    "fonoon_01_four_pillars",
    "fonoon_01_bayani_figures" -> 3 to "📙 پایه یازدهم انسانی (چاپ ۱۴۰۴ ویژه کنکور ۱۴۰۶) — علوم و فنون ادبی ۲ کامل (درس ۱ تا ۱۲)"

    "fonoon_12_ch1_history_hindi_bazgasht",
    "fonoon_02_iham_paradox_badie",
    "fonoon_02_badi_figures",
    "fonoon_02b_spiritual_figures_talmih",
    "fonoon_04_grade12_lafo_nashr_esloob_hasan",
    "fonoon_06_advanced_konkoor_arrayeh_traps",
    "fonoon_03_styles_history",
    "fonoon_05_styles_history_comprehensive",
    "fonoon_07_complete_literary_history_10_11_12",
    "fonoon_04_mnemonics_final20",
    "fonoon_master_arrayeh_micro_notes",
    "fon12-01-laf-nashr",
    "fon12-02-oslub-moadele-hosn-talil",
    "fon12-03-iham-tanasob",
    "fon12-04-eghragh-talmih",
    "fon3-01-tarikh-12-13-bazgasht-bidari",
    "fon3-04-sabkshenasi-12-13",
    "fon3-07-tarikh-14-moaser-enghelab",
    "fon3-10-sabkshenasi-moaser-enghelab",
    "fon3-02-music-bases",
    "fon3-05-licenses-verbal",
    "fon3-08-licenses-metric",
    "fon3-11-nimai-meter",
    "fon3-03-moraat-tazmin",
    "fon3-06-tazad-motanaghez",
    "fon3-12-hesamizi" -> 4 to "📕 پایه دوازدهم انسانی (چاپ ۱۴۰۵ ویژه کنکور ۱۴۰۶) — علوم و فنون ۳ + کل تاریخ ادبیات و سبک‌شناسی دهم تا دوازدهم"

    // 6. Logic (منطق دهم چاپ ۱۴۰۳)
    "phil_00_khodemoni_logic_10_complete",
    "logic_10_lesson1_2_concepts_terms",
    "logic_10_lesson3_4_relations_five_universals",
    "logic_10_lesson5_definitions",
    "phil_01_logic_concepts_five_universals",
    "phil_02_logic_propositions_square",
    "phil_03_logic_syllogisms_fallacies",
    "philo_01_logic_concepts_definitions",
    "philo_02_logic_propositions_square",
    "philo_03_logic_syllogism_conditional",
    "logic_phil_complete_mastery" -> 2 to "📗 پایه دهم انسانی (چاپ ۱۴۰۳ ویژه کنکور ۱۴۰۶) — کتاب منطق کامل به زبان خودمونی (درس ۱ تا ۱۰)"

    // 7. Philosophy 1 & 2 (فلسفه ۱ یازدهم ۱۴۰۴ و فلسفه ۲ دوازدهم ۱۴۰۵)
    "phil_11_ch1_what_is_philosophy_greeks",
    "phil_11_ch2_epistemology_knowledge",
    "phil_11_ch3_islamic_philosophy_ethics",
    "phil_04_grade11_history_epistemology",
    "philo_04_grade11_history_epistemology" -> 3 to "📙 پایه یازدهم انسانی (چاپ ۱۴۰۴ ویژه کنکور ۱۴۰۶) — کتاب فلسفه ۱ کامل (درس ۱ تا ۱۲ تفکیک‌شده)"

    "phil_05_grade12_existence_quiddity",
    "phil_06_grade12_causality_motion_schools",
    "phil_07_grade12_mulla_sadra_and_theology",
    "phil_08_konkoor_100_master_tables",
    "phil_09_khodemoni_philosophy_11_12_complete",
    "philo_05_grade12_existence_causality",
    "philo_06_grade12_theology_islamic_schools",
    "philo_07_mnemonics_final20",
    "phil2-02-jahan-momkenat",
    "phil2-04-kodam-tasvir",
    "phil2-07-aghl-1",
    "phil2-08-aghl-2",
    "phil2-09-aghaz-falsafeh",
    "phil2-10-dowreh-miyani",
    "phil2-11-dowran-motakhkher",
    "phil2-12-hekmat-moaser" -> 4 to "📕 پایه دوازدهم انسانی (چاپ ۱۴۰۵ ویژه کنکور ۱۴۰۶) — کتاب فلسفه ۲ کامل به زبان خودمونی (درس ۱ تا ۱۲)"

    // 8. History 1, 2, 3 (تاریخ ۱، ۲ و ۳)
    "hist_00_elementary_to_9th_zero" -> 1 to "📘 پایه اول ابتدایی تا نهم (سطح زیرِ صفر) — محاسبه قرن‌ها، گاه‌شماری و الفبای تاریخ"
    "hist_10_ch1_ancient_world_mesopotamia_egypt",
    "hist_10_ch2_ancient_iran_medes_achaemenids_parthians_sasanians",
    "hist_01_grade10_1403",
    "hist1-01",
    "hist1-02",
    "hist1-03",
    "hist1-04",
    "hist1-05",
    "hist1-06",
    "hist1-07",
    "hist1-08",
    "hist1-09",
    "hist1-10",
    "hist1-11",
    "hist1-12",
    "hist1-13",
    "hist1-14",
    "hist1-15",
    "hist1-16" -> 2 to "📗 پایه دهم انسانی (چاپ دهم ۱۴۰۴ ویژه کنکور ۱۴۰۶) — صفحه ۰ تا آخر کتاب تاریخ ۱ (ایران و جهان باستان)"
    "hist_11_ch1_islam_prophet_caliphates",
    "hist_11_ch2_iran_islamic_dynasties_tahirid_to_timurid",
    "hist_02_grade11_1404",
    "hist2-01",
    "hist2-02",
    "hist2-03",
    "hist2-04",
    "hist2-05",
    "hist2-06",
    "hist2-07",
    "hist2-08",
    "hist2-09",
    "hist2-10",
    "hist2-11",
    "hist2-12",
    "hist2-13",
    "hist2-14",
    "hist2-15",
    "hist2-16" -> 3 to "📙 پایه یازدهم انسانی (چاپ نهم ۱۴۰۴ ویژه کنکور ۱۴۰۶) — صفحه ۰ تا آخر کتاب تاریخ ۲ (اسلام و ایران تا صفویه)"
    "hist_12_ch1_afshar_zand_qajar_constitution",
    "hist_12_ch2_pahlavi_oil_nationalization_revolution",
    "hist_03_grade12_1405",
    "hist_05_safavid_constitutional_oil",
    "hist_04_treaties_kings_konkoor_tables",
    "hist3-01",
    "hist3-02",
    "hist3-03",
    "hist3-04",
    "hist3-05",
    "hist3-06",
    "hist3-07",
    "hist3-08",
    "hist3-09",
    "hist3-10",
    "hist3-11",
    "hist3-12" -> 4 to "📕 پایه دوازدهم انسانی (چاپ ۱۴۰۵ ویژه کنکور ۱۴۰۶) — کتاب تاریخ ۳ معاصر + جداول عهدنامه‌ها و سلسله‌ها"

    // 9. Geography 1, 2, 3 (جغرافیا ۱، ۲ و ۳)
    "geo_00_zero_maps_coordinates" -> 1 to "📘 از زیرِ صفر به زبان کوچه و بازار — حل چشمی مسائل ساعت، نصف‌النهار و مقیاس نقشه"
    "geo_01_grade10_1403",
    "geo_10_ch1_location_topography_climate_rivers",
    "geo_10_ch2_iran_population_settlements_economy",
    "geo_10_ch3_economy_transport_borders" -> 2 to "📗 پایه دهم انسانی (چاپ ۱۴۰۳ ویژه کنکور ۱۴۰۶) — صفحه ۰ تا آخر کتاب جغرافیای ایران (دهم)"
    "geo_02_grade11_1404",
    "geo_11_ch1_regions_biomes_culture",
    "geo_11_ch2_economic_political_regions_seas",
    "geo_11_ch3_case_studies_sw_asia_europe" -> 3 to "📙 پایه یازدهم انسانی (چاپ ۱۴۰۴ ویژه کنکور ۱۴۰۶) — صفحه ۰ تا آخر کتاب جغرافیا ۲ (ناحیه‌ای جهان)"
    "geo_03_grade12_1405",
    "geo_04_iran_climate_rivers_gis",
    "geo_12_ch1_applied_geography_hazards",
    "geo_12_ch2_climate_spatial_planning_gis",
    "geo1406-01-settlements",
    "geo1406-02-city-rural-management",
    "geo1406-03-transport",
    "geo1406-04-natural-hazards" -> 4 to "📕 پایه دوازدهم انسانی (چاپ ۱۴۰۵ ویژه کنکور ۱۴۰۶) — صفحه ۰ تا آخر کتاب جغرافیا ۳ (کاربردی)"

    // 10. Sociology 1, 2, 3 (جامعه‌شناسی ۱، ۲ و ۳)
    "soc_000_khodemoni_zero_to_100",
    "soc_00_elementary_to_9th_zero" -> 1 to "📘 از زیرِ صفر به زبان کوچه و بازار — ترجمه تمام کلمات قلمبه‌سلمبه جامعه‌شناسی ۱، ۲ و ۳"
    "soc_10_ch1_human_social_action",
    "soc_10_ch2_culture_layers_values",
    "soc_01_grade10_1403_complete",
    "soc_01b_grade10_identity_mobility_control",
    "soc1-01",
    "soc1-02",
    "soc1-03",
    "soc1-04",
    "soc1-05",
    "soc1-06",
    "soc1-07",
    "soc1-08",
    "soc1-09",
    "soc1-10",
    "soc1-11",
    "soc1-12",
    "soc1-13",
    "soc1-14",
    "soc1-15",
    "soc1-16" -> 2 to "📗 پایه دهم انسانی (چاپ ۱۴۰۳ ویژه کنکور ۱۴۰۶) — صفحه ۰ تا آخر کتاب جامعه‌شناسی ۱ (درس ۱ تا ۱۶)"
    "soc_11_ch1_cultural_world_globalization",
    "soc_02_grade11_1404_complete",
    "soc_02b_grade11_west_islam_modernity",
    "soc2-01",
    "soc2-02",
    "soc2-03",
    "soc2-04",
    "soc2-05",
    "soc2-06",
    "soc2-07",
    "soc2-08",
    "soc2-09",
    "soc2-10",
    "soc2-11",
    "soc2-12",
    "soc2-13",
    "soc2-14",
    "soc2-15" -> 3 to "📙 پایه یازدهم انسانی (چاپ ۱۴۰۴ ویژه کنکور ۱۴۰۶) — صفحه ۰ تا آخر کتاب جامعه‌شناسی ۲ (درس ۱ تا ۱۵)"
    "soc_12_ch1_three_approaches_social_sciences",
    "soc_03_grade12_1405_complete",
    "soc_12_ch2_social_order_family_policy",
    "soc_master_10_11_12_full_encyclopedia",
    "soc3-01",
    "soc3-02",
    "soc3-03",
    "soc3-04",
    "soc3-05",
    "soc3-06",
    "soc3-07",
    "soc3-08",
    "soc3-09",
    "soc3-10" -> 4 to "📕 پایه دوازدهم انسانی (چاپ ۱۴۰۵ ویژه کنکور ۱۴۰۶) — صفحه ۰ تا آخر کتاب جامعه‌شناسی ۳ و ۳ رویکرد کنکور"

    // 11. Psychology 11 (روان‌شناسی یازدهم چاپ ۱۴۰۴)
    "psych_00_zero_khodemoni" -> 1 to "📘 از زیرِ صفر به زبان کوچه و بازار — ترجمه تمام کلمات سخت روان‌شناسی به زبان خودمونی"
    "psych_11_ch1_schools_and_development",
    "psych_01_grade11_1404_complete",
    "psych_02_sensation_memory_forgetting",
    "psych_03_perception_attention_gestalt",
    "psych_04_memory_forgetting_strategies",
    "psych_11_ch3_thinking_problem_solving",
    "psych_11_ch4_stress_mental_health" -> 3 to "📙 پایه یازدهم انسانی (چاپ ۱۴۰۴ ویژه کنکور ۱۴۰۶) — صفحه ۰ تا آخر کتاب روان‌شناسی یازدهم (فصل ۱ تا ۸ کامل)"

    // 12. Economics 10 (اقتصاد دهم چاپ ۱۴۰۳)
    "econ_02_math_problems_shortcuts_100",
    "econ_full_math_mastery",
    "economics_speed_shortcuts_master" -> 1 to "📘 شگردهای ۵ ثانیه‌ای و بدون فرمول برای حل تمام ۱۰ تیپ مسائل ریاضی اقتصاد کنکور"
    "econ_01_grade10_1403_complete",
    "econ_04_market_equilibrium_curves",
    "econ_10_ch2_market_supply_demand",
    "econ_05_tax_gini_distribution",
    "econ_03_gdp_inflation_unemployment_budget",
    "econ_10_ch4_trade_exchange_resilience",
    "gdp1406-01-what-is-gdp",
    "gdp1406-02-gdp-calculation-nominal-real" -> 2 to "📗 پایه دهم انسانی (چاپ ۱۴۰۳ ویژه کنکور ۱۴۰۶) — صفحه ۰ تا آخر کتاب اقتصاد دهم (فصل ۱ تا ۵ کامل)"

    // 13. Din o Zendegi 1, 2, 3 (دین و زندگی ۱، ۲ و ۳)
    "dini_10_ch1_purpose_creation_theology",
    "dini_10_ch2_death_barzakh_hereafter",
    "dini_10_ch3_friendship_ethics_society",
    "dini_01_grade10_1403_complete",
    "dini1-01-hadaf-zendegi",
    "dini1-02-qodrat-parvaz",
    "dini1-03-be-sooye-javedanegi",
    "dini1-04-ayandeye-rowshan",
    "dini1-05-manzelgahe-baad" -> 2 to "📗 پایه دهم انسانی (چاپ دهم ۱۴۰۴ ویژه کنکور ۱۴۰۶) — صفحه ۰ تا آخر دین و زندگی ۱ (درس ۱ تا ۱۲)"
    "dini_11_ch1_divine_guidance_quran_prophet",
    "dini_11_ch2_imamate_ghadir_velayat",
    "dini_11_ch3_imams_life_ghaybat_velayat",
    "dini_02_grade11_1404_complete" -> 3 to "📙 پایه یازدهم انسانی (چاپ ۱۴۰۴ ویژه کنکور ۱۴۰۶) — صفحه ۰ تا آخر دین و زندگی ۲ انسانی (درس ۱ تا ۱۴)"
    "dini_01_tawheed_degrees",
    "dini_03_grade12_1405_complete",
    "dini_04_divine_laws_sunan_barzakh",
    "far_05_dini_farhangian_ensani",
    "far_08_dini_complete_10_11_12_1406",
    "dini3-01-qaza-qadar",
    "dini3-02-sonnat-elahi",
    "dini3-03-towbe",
    "dini3-04-donyaye-emruz" -> 4 to "📕 پایه دوازدهم انسانی (چاپ ۱۴۰۵) و فرهنگیان — دین و زندگی ۳ + بانک جامع پیام آیات"

    // 14. Farsi 1, 2, 3 (فارسی ۱، ۲ و ۳)
    "far_09_farsi_grammar_1_to_12_1406",
    "found1406-farsi-01-alphabet-reading",
    "found1406-farsi-02-sentence-grammar" -> 1 to "📘 از کلاس اول ابتدایی تا دوازدهم — دستور زبان فارسی، نقش‌ها، گروه اسمی و ساختمان واژه"
    "farsi_01_syntax_roles_groups",
    "farsi_10_ch1_grammar_vocab_realms",
    "farsi_10_ch2_literary_intellectual_realms",
    "far1-01-cheshmeh",
    "far1-02-az-amookhtan-nang-madar",
    "far1-03-pasdari-haghighat",
    "far1-05-bidad-zaleman",
    "far1-06-mehr-vafa",
    "far1-07-jamal-kamal",
    "far1-08-safar-basreh",
    "far1-09-kelas-naghashi",
    "far1-10-daryadelan-safshekan",
    "far1-11-khak-azadegan",
    "far1-12-rostam-ashkbous",
    "far1-13-gordafarid",
    "far1-14-tooti-baghal",
    "far1-16-khosrow",
    "far1-17-sepideh-dam",
    "far1-18-azamat-negah" -> 2 to "📗 پایه دهم انسانی (چاپ ۱۴۰۳ ویژه کنکور ۱۴۰۶) — فارسی ۱ (قلمرو زبانی، ادبی و فکری)"
    "farsi_02_word_morphology",
    "farsi_11_ch1_grammar_vocab_realms",
    "farsi_02_vocab_spelling_3_realms",
    "far2-01-niki",
    "far2-02-ghazi-bast",
    "far2-03-dar-amvaj-sand",
    "far2-05-aghazgari-tanha",
    "far2-06-parvardeh-eshgh",
    "far2-07-baran-mohabbat",
    "far2-08-dar-kooy-asheghan",
    "far2-09-zowq-latif",
    "far2-10-bang-jaras",
    "far2-11-yaran-ashegh",
    "far2-12-kaveh-dadkhah",
    "far2-14-hamleh-heydari",
    "far2-15-kabootar-towqdar",
    "far2-16-ghesseh-eynakam",
    "far2-17-khamooshi-darya",
    "far2-18-khan-adl" -> 3 to "📙 پایه یازدهم انسانی (چاپ ۱۴۰۴ ویژه کنکور ۱۴۰۶) — فارسی ۲ (ساختمان واژه، نقش‌های تبعی، املا و قلمروها)"
    "farsi_12_ch1_syntax_noun_phrase_realms",
    "farsi_12_ch2_final_exam_vocab_spelling_authors",
    "farsi_04_advanced_syntax_pronoun_shift",
    "far1406-01-shokr-nemat",
    "far1406-02-mast-hoshiyar",
    "far1406-03-azadi",
    "far1406-05-damavandiyeh",
    "far1406-06-naynameh",
    "far1406-07-haghighate-eshgh" -> 4 to "📕 پایه دوازدهم انسانی (چاپ ۱۴۰۵ ویژه کنکور و نهایی) — فارسی ۳ و فنون زبانی پیشرفته"

    // 15. Negaresh 1, 2, 3 (نگارش ۱، ۲ و ۳)
    "negaresh_01_grade10_objective_subjective",
    "negaresh_10_ch2_proverb_parable_writing",
    "negaresh_master_10_11_12_complete",
    "neg2-01-hekayat",
    "neg2-02-masal",
    "neg2-03-gozaresh",
    "neg2-04-dastan",
    "neg2-05-janeshin",
    "neg2-06-sanjesh",
    "neg2-07-nasazi",
    "neg2-08-kholaseh",
    "neg2b-01",
    "neg2b-02",
    "neg2b-03",
    "neg2b-04",
    "neg2b-05",
    "neg2b-06",
    "neg2b-07",
    "neg2b-08",
    "neg3-01",
    "neg3-02",
    "neg3-03",
    "neg3-04",
    "neg3-05",
    "neg3-06" -> 2 to "📗 پایه دهم و یازدهم (چاپ ۱۴۰۳ و ۱۴۰۴) — نگارش ۱ و ۲ (مثل‌نویسی، حکایت‌نگاری، بازآفرینی و گزارش‌نویسی)"
    "negaresh_03_grade11_report_dialogue_recreate" -> 3 to "📙 پایه یازدهم انسانی (چاپ ۱۴۰۴) — نگارش ۲ (گزارش‌نویسی، گفتگو و بازآفرینی مثل)"
    "negaresh_02_grade12_final_20_templates",
    "farsi_03_negaresh_final_exam_20",
    "neg1406-01-khatereh",
    "neg1406-02-safarnameh",
    "neg1406-03-tosif",
    "neg1406-04-zendeginameh" -> 4 to "📕 پایه دوازدهم (چاپ ۱۴۰۵ ویژه امتحان نهایی) — نگارش ۳ و قالب آماده نمره ۲۰ نهایی"

    // 16. Farhangian Exclusive Resources (منابع اختصاصی فرهنگیان ۱۴۰۶)
    "far_01_roadmap_1406",
    "far_08_qaraati_teacher_methods_complete",
    "far_02_gharaati_talim",
    "farh1406-01-sanad-tahavol",
    "farh1406-02-talim-tarbiyat-eslami" -> 3 to "📙 دفترچه اختصاصی فرهنگیان ۱۴۰۶ — مهارت‌های معلمی استاد قرائتی (فصل ۱ تا ۳) و سند تحول بنیادین"
    "far_03a_verbal_logical_math_intelligence_full",
    "far_03_teacher_intelligence",
    "far_09_teacher_aptitude_reasoning",
    "far_04_interview_humanities",
    "far_06_comprehensive_interview_scenarios",
    "far_07_konkoor_full_package_humanities_tables",
    "farhangian_04_mnemonics_final20",
    "persian_internet_konkoor_gems" -> 4 to "📕 هوش و استعداد معلمی + سوالات کانون ارزیابی و مصاحبه حضوری فرهنگیان ۱۴۰۶"

    // 17. Social Identity 12 (هویت اجتماعی دوازدهم چاپ ۱۴۰۵)
    "hoviyat_master_grade12_final20" -> 3 to "📙 بانک سوالات پرتکرار و جداول مقایسه‌ای هویت اجتماعی دوازدهم (نمره ۲۰ قطعی نهایی)"
    "hoviyat_01_action_phenomena",
    "hoviyat_02_society_culture",
    "hoviyat_03_reproduction_changes",
    "hoviyat_04_iranian_islamic_identity_globalization",
    "gen_02_hoviyat_family_grade12_1405" -> 4 to "📕 پایه دوازدهم انسانی (چاپ ۱۴۰۵ ویژه نهایی) — صفحه ۰ تا آخر کتاب هویت اجتماعی دوازدهم"

    // 18. Health & Hygiene 12 (سلامت و بهداشت دوازدهم چاپ ۱۴۰۵)
    "salamat_master_grade12_final20" -> 3 to "📙 جداول طلایی، تغذیه، بیماری‌ها و سوالات قطعی امتحان نهایی سلامت و بهداشت دوازدهم"
    "salamat_01_health_dimensions_nutrition_bmi",
    "gen_01_salamat_grade12_1405",
    "salamat_02_diseases_communicable_non",
    "salamat_03_mental_health_first_aid",
    "sal1406-01-addiction" -> 4 to "📕 پایه دوازدهم انسانی (چاپ ۱۴۰۵ ویژه نهایی) — صفحه ۰ تا آخر کتاب سلامت و بهداشت دوازدهم"

    // 19. Media Literacy 10, Environment 11, Family Management 12 (رسانه، محیط زیست و خانواده)
    "gen_04_savad_resaneh_karafarini_grade10_1403",
    "resaneh_01_persuasion_techniques",
    "resaneh_10_ch2_media_diet_ethics_entrepreneurship",
    "res1406-01-media-message" -> 2 to "📗 پایه دهم انسانی (چاپ ۱۴۰۳) — تفکر و سواد رسانه‌ای دهم + کارگاه کارآفرینی و تولید دهم"
    "gen_03_mohit_zist_grade11_1404",
    "mohit_11_ch2_biodiversity_energy_pollution",
    "moh1406-01-water-air",
    "moh1406-02-soil" -> 3 to "📙 پایه یازدهم انسانی (چاپ ۱۴۰۴) — انسان و محیط زیست یازدهم (فصل ۱ تا ۷ کامل)"
    "family_12_management_lifestyle_complete",
    "far_10_remaining_humanities_books_10_11_12" -> 4 to "📕 پایه دوازدهم انسانی (چاپ ۱۴۰۵) — مدیریت خانواده و سبک زندگی ۱۲ و جمع‌بندی دروس مهارتی"

    // 20. Scientific Learning Methods & Speed Reading (روش‌های نوین یادگیری و تست‌زنی)
    "study_01_feynman_active_recall_leitner",
    "study_04_feynman_technique_deep_mastery",
    "methods1406-01-sq3r",
    "methods1406-02-memory-palace",
    "methods1406-03-interleaving",
    "methods1406-04-arabic-study" -> 1 to "📘 روش‌های علمی یادگیری جهانی — تکنیک فاینمن، بازیابی فعال (Active Recall) و مرور فاصله‌دار لایتنر"
    "study_02_cornell_mindmap_speed_reading" -> 2 to "📗 خلاصه‌نویسی و تندخوانی — روش کرنل (Cornell)، نقشه ذهنی (Mind Map)، رمزگردانی دوگانه و تندخوانی"
    "study_03_konkoor_test_strategy_final20" -> 4 to "📕 مهندسی جلسه کنکور و امتحان نهایی — تکنیک ضربدر و منها، زمان‌های نقصانی، رد گزینه و فرمول نمره ۲۰ نهایی"

    else -> 4 to "📕 مباحث تکمیلی کتاب درسی و جمع‌بندی کنکور ۱۴۰۶"
}

/**
 * Returns the exact official Ministry of Education textbook reference (کتاب، پایه، سال چاپ و فصل/درس)
 * verified for the Konkoor 1406 cohort (دهم چاپ ۱۴۰۳، یازدهم چاپ ۱۴۰۴، دوازدهم چاپ ۱۴۰۵).
 */
fun ZeroLesson.officialTextbookReference(): String = when (id) {
    "fonoon_master_arrayeh_micro_notes" -> "📚 دایرةالمعارف جامع زیبایی‌شناسی و آرایه‌های ادبی علوم و فنون ۱، ۲ و ۳ (با ریزترین نکات کنکوری)"
    "arooz_master_complete_100" -> "📚 دایرةالمعارف جامع عروض و قافیه علوم و فنون ۱، ۲ و ۳ (خط عروضی، ۱۶ وزن، اختیارات شاعری و قافیه)"
    "economics_speed_shortcuts_master" -> "📚 کارگاه راه‌حل‌های ساده‌کننده و ۵ ثانیه‌ای تمام مسائل محاسباتی اقتصاد دهم (کد ۱۱۰۲۲۱)"
    "english_full_persian_mastery" -> "📚 دایرةالمعارف کامل زبان انگلیسی هفتم تا دوازدهم (Vision 1-3) با توضیح ۱۰۰٪ فارسی"
    "arabic_visual_persian_mastery" -> "📚 دایرةالمعارف تصویری عربی هفتم تا دوازدهم انسانی با شکل و فلوچارت به زبان فارسی"
    "persian_internet_konkoor_gems" -> "📚 بانک طلایی نکات، جداول مقایسه‌ای و کلیدواژه‌های کل دروس کنکور انسانی و فرهنگیان"
    // Math & Statistics
    "math_000_bismillah_mastery" -> "📚 مطابق ریاضی اول دبستان تا نهم (مجموعه اعداد N, W, Z, Q, R و قدرمطلق)"
    "math_00_elementary_school" -> "📚 مطابق ریاضی سوم تا ششم دبستان (جدول ضرب، کسرها و ترتیب عملیات)"
    "math_01_signs_fractions" -> "📚 مطابق ریاضی هفتم و هشتم (اعداد صحیح علامت‌دار و عملیات کسرها)"
    "math_02_equations" -> "📚 مطابق فصل ۱ ریاضی و آمار ۱ — پایه دهم انسانی (چاپ ۱۴۰۳ ویژه کنکور ۱۴۰۶)"
    "math_03_radicals_powers" -> "📚 مطابق فصل ۱ ریاضی دهم (چاپ ۱۴۰۳) و فصل ۳ ریاضی دوازدهم انسانی (چاپ ۱۴۰۵)"
    "math_04_functions_domain" -> "📚 مطابق فصل ۲ ریاضی و آمار ۱ — پایه دهم انسانی (چاپ ۱۴۰۳ ویژه کنکور ۱۴۰۶)"
    "math_06_statistics_dispersion" -> "📚 مطابق فصل ۳ ریاضی و آمار ۱ — پایه دهم انسانی (چاپ ۱۴۰۳ ویژه کنکور ۱۴۰۶)"
    "math_09_statistics_percentiles" -> "📚 مطابق فصل ۳ ریاضی و آمار ۱ — پایه دهم انسانی (چاپ ۱۴۰۳ ویژه کنکور ۱۴۰۶)"
    "math_08_logic_propositions" -> "📚 مطابق فصل ۱ ریاضی و آمار ۲ — پایه یازدهم انسانی (چاپ ۱۴۰۴ ویژه کنکور ۱۴۰۶)"
    "math_11_grade11_functions_exponential" -> "📚 مطابق فصل ۲ ریاضی و آمار ۲ — پایه یازدهم انسانی (چاپ ۱۴۰۴ ویژه کنکور ۱۴۰۶)"
    "math_07_grade12_regression_time_series" -> "📚 مطابق فصل ۳ ریاضی و آمار ۲ — پایه یازدهم انسانی (چاپ ۱۴۰۴ ویژه کنکور ۱۴۰۶)"
    "math_07_combinatorics_probability" -> "📚 مطابق فصل ۱ ریاضی و آمار ۳ — پایه دوازدهم انسانی (چاپ ۱۴۰۵ ویژه کنکور ۱۴۰۶)"
    "math_10_conditional_probability" -> "📚 مطابق فصل ۱ ریاضی و آمار ۳ — پایه دوازدهم انسانی (چاپ ۱۴۰۵ ویژه کنکور ۱۴۰۶)"
    "math_05_sequences_arithmetic" -> "📚 مطابق فصل ۲ و ۳ ریاضی و آمار ۳ — پایه دوازدهم انسانی (چاپ ۱۴۰۵ ویژه کنکور ۱۴۰۶)"
    "math_12_konkoor_80_percent_shortcuts" -> "📚 جمع‌بندی ۱۰۰٪ ریاضی و آمار ۱ (چاپ ۱۴۰۳)، ۲ (چاپ ۱۴۰۴) و ۳ (چاپ ۱۴۰۵) ویژه کنکور ۱۴۰۶"

    // Arabic
    "arabic_0000_absolute_zero_7th_to_12th_khodemoni" -> "📚 آموزش از زیرِ صفرِ کلاس هفتم تا دوازدهم به زبان کاملاً خودمونی (حرکات، اسم، فعل، فاعل، مفعول و صیغه‌ها)"
    "arabic_000_bismillah_mastery" -> "📚 مطابق عربی پایه هفتم تا نهم به زبان ساده (ماضی، مضارع، امر و نهی)"
    "arabic_00_sigha_hand_code" -> "📚 مطابق عربی پایه هفتم و هشتم (صرف ۱۴ صیغه با کد ۵ انگشت دست)"
    "arabic_01_word_types_pronouns" -> "📚 مطابق عربی پایه هشتم و نهم (تشخیص اسم، فعل، حرف، ضمایر و اعراب)"
    "arabic_01b_numbers_time_nouns" -> "📚 مطابق درس ۱ و ۲ عربی، زبان قرآن ۱ — پایه دهم انسانی (چاپ ۱۴۰۳ ویژه کنکور ۱۴۰۶)"
    "arabic_02_verbs_abwab" -> "📚 مطابق درس ۳ تا ۸ عربی، زبان قرآن ۱ — پایه دهم انسانی (چاپ ۱۴۰۳ ویژه کنکور ۱۴۰۶)"
    "arabic_02b_derived_nouns_qaleb" -> "📚 مطابق درس ۱ و ۲ عربی، زبان قرآن ۲ — پایه یازدهم انسانی (چاپ ۱۴۰۴ ویژه کنکور ۱۴۰۶)"
    "arabic_03b_grade11_shart_naqeseh_vasf" -> "📚 مطابق درس ۳ تا ۷ عربی، زبان قرآن ۲ — پایه یازدهم انسانی (چاپ ۱۴۰۴ ویژه کنکور ۱۴۰۶)"
    "arabic_03_grade12_rules" -> "📚 مطابق درس ۱ تا ۳ عربی، زبان قرآن ۳ — پایه دوازدهم انسانی (چاپ ۱۴۰۵ ویژه کنکور ۱۴۰۶)"
    "arabic_04_grade12_istisna_hasr_hal" -> "📚 مطابق درس ۴ و ۵ عربی، زبان قرآن ۳ — پایه دوازدهم انسانی (چاپ ۱۴۰۵ ویژه کنکور ۱۴۰۶)"
    "arabic_05_konkoor_translation_keys" -> "📚 مطابق کل مباحث ترجمه و تعریب عربی ۱ (چاپ ۱۴۰۳)، ۲ (چاپ ۱۴۰۴) و ۳ (چاپ ۱۴۰۵)"
    "arabic_06_konkoor_100_tables_and_traps" -> "📚 جداول جامع ۱۰۰٪ عربی ۱، ۲ و ۳ اختصاصی انسانی ویژه کنکور ۱۴۰۶"
    "arabic_04_mnemonics_final20" -> "📚 مطابق بارم‌بندی نهایی عربی ۳ دوازدهم انسانی (چاپ ۱۴۰۵) و رمزهای کنکور ۱۴۰۶"

    // English
    "english_000_zero_dictionary_grammar_words" -> "📚 لغت‌نامه گرامری از زیرِ صفر: معنی تک‌تک کلمات Verb (فعل)، Subject (فاعل)، Object (مفعول) و... به زبان کاملاً خودمونی"
    "english_000_bismillah_mastery" -> "📚 مطابق زبان انگلیسی پایه هفتم تا نهم — کتب Prospect 1, 2, 3 به زبان ساده"
    "english_00_seventh_grade_train" -> "📚 مطابق زبان پایه هفتم تا یازدهم (جدول کامل زمان‌های انگلیسی با ساختار و نشانه‌ها)"
    "english_01_sentence_zero" -> "📚 مطابق مبانی جمله‌سازی Prospect و Vision 1 (فاعل Subject، فعل Verb، مفعول Object و ضمایر)"
    "english_01b_nouns_comparatives_adjectives" -> "📚 مطابق درس ۱، ۲ و ۳ کتاب Vision 1 — پایه دهم (چاپ ۱۴۰۳ ویژه کنکور ۱۴۰۶)"
    "english_01c_gerund_infinitive_conjunctions" -> "📚 مطابق درس ۱، ۲ و ۳ کتاب Vision 2 — پایه یازدهم (چاپ ۱۴۰۴ ویژه کنکور ۱۴۰۶)"
    "english_02_grade12_grammar" -> "📚 مطابق درس ۱، ۲ و ۳ کتاب Vision 3 — پایه دوازدهم (چاپ ۱۴۰۵ ویژه کنکور ۱۴۰۶)"
    "english_04_tag_questions_condition_passive" -> "📚 مطابق سوالات قطعی گرامر امتحان نهایی Vision 3 — پایه دوازدهم (چاپ ۱۴۰۵)"
    "english_03_grade12_reading_cloze_vocab" -> "📚 مطابق واژگان، Reading و Cloze Test کتاب Vision 3 — پایه دوازدهم (چاپ ۱۴۰۵)"
    "english_05_konkoor_100_master_tables" -> "📚 جداول جامع ۱۰۰٪ زبان انگلیسی هفتم تا دوازدهم (با معنی فارسی تمام اصطلاحات گرامری)"
    "english_06_full_vocab_7_to_12_konkoor" -> "📚 بانک جامع لغات خط‌به‌خط هفتم تا دوازدهم (Prospect 1-3 + Vision 1 چاپ ۱۴۰۳، Vision 2 چاپ ۱۴۰۴، Vision 3 چاپ ۱۴۰۵)"

    // Arooz & Ghafieh
    "arooz_00_clapping_zero" -> "📚 مطابق درس ۱ و ۳ علوم و فنون ادبی ۱ — پایه دهم انسانی (چاپ ۱۴۰۳ ویژه کنکور ۱۴۰۶)"
    "arooz_01_syllables_zero" -> "📚 مطابق درس ۳ و ۶ علوم و فنون ادبی ۱ — پایه دهم انسانی (چاپ ۱۴۰۳ ویژه کنکور ۱۴۰۶)"
    "arooz_02_cutting_u_dash" -> "📚 مطابق درس ۶ علوم و فنون ادبی ۱ — پایه دهم انسانی (چاپ ۱۴۰۳ ویژه کنکور ۱۴۰۶)"
    "arooz_04_ghafieh_rules" -> "📚 مطابق درس ۹ و ۱۲ علوم و فنون ادبی ۱ — پایه دهم انسانی (چاپ ۱۴۰۳ ویژه کنکور ۱۴۰۶)"
    "arooz_03_samaei_rhythms" -> "📚 مطابق درس ۳، ۶، ۹ و ۱۲ علوم و فنون ادبی ۲ — پایه یازدهم انسانی (چاپ ۱۴۰۴ ویژه کنکور ۱۴۰۶)"
    "arooz_04_mnemonics_mastery" -> "📚 مطابق علوم و فنون ۲ (چاپ ۱۴۰۴) و ۳ (چاپ ۱۴۰۵) — کدهای ۴ رقمی بحرها"
    "arooz_05_all_16_konkoor_meters" -> "📚 مطابق علوم و فنون ۲ (چاپ ۱۴۰۴) و ۳ (چاپ ۱۴۰۵) — جدول کامل ۱۶ وزن کتاب درسی"
    "arooz_03b_poetic_licenses" -> "📚 مطابق درس ۳ و ۶ علوم و فنون ادبی ۳ — پایه دوازدهم انسانی (چاپ ۱۴۰۵ ویژه کنکور ۱۴۰۶)"
    "arooz_06_konkoor_100_speed_scansion" -> "📚 جمع‌بندی پیشرفته ۱۰۰٪ عروض و قافیه علوم و فنون ۱ (۱۴۰۳)، ۲ (۱۴۰۴) و ۳ (۱۴۰۵)"

    // Fonoon
    "fonoon_01b_verbal_figures_saj" -> "📚 مطابق درس ۲، ۵، ۸ و ۱۱ علوم و فنون ادبی ۱ — پایه دهم انسانی (چاپ ۱۴۰۳ ویژه کنکور ۱۴۰۶)"
    "fonoon_01_four_pillars" -> "📚 مطابق درس ۲، ۵، ۸ و ۱۱ علوم و فنون ادبی ۲ — پایه یازدهم انسانی (چاپ ۱۴۰۴ ویژه کنکور ۱۴۰۶)"
    "fonoon_02_iham_paradox_badie" -> "📚 مطابق درس ۲، ۵ و ۸ علوم و فنون ادبی ۳ — پایه دوازدهم انسانی (چاپ ۱۴۰۵ ویژه کنکور ۱۴۰۶)"
    "fonoon_02b_spiritual_figures_talmih" -> "📚 مطابق علوم و فنون ۱ (چاپ ۱۴۰۳) و ۳ (چاپ ۱۴۰۵) — مراعات نظیر، تلمیح، تضمین، اغراق و تضاد"
    "fonoon_04_grade12_lafo_nashr_esloob_hasan" -> "📚 مطابق درس ۵، ۸ و ۱۱ علوم و فنون ادبی ۳ — پایه دوازدهم انسانی (چاپ ۱۴۰۵ ویژه کنکور ۱۴۰۶)"
    "fonoon_06_advanced_konkoor_arrayeh_traps" -> "📚 جمع‌بندی فوق‌پیشرفته ۱۰۰٪ آرایه‌های ادبی علوم و فنون ۱، ۲ و ۳ ویژه کنکور ۱۴۰۶"
    "fonoon_03_styles_history" -> "📚 مطابق بخش تاریخ ادبیات و سبک‌شناسی علوم و فنون ۱ (۱۴۰۳)، ۲ (۱۴۰۴) و ۳ (۱۴۰۵)"
    "fonoon_05_styles_history_comprehensive" -> "📚 مطابق درس ۱، ۴، ۷ و ۱۰ علوم و فنون ۱ (۱۴۰۳)، ۲ (۱۴۰۴) و ۳ (۱۴۰۵)"
    "fonoon_07_complete_literary_history_10_11_12" -> "📚 دایرةالمعارف کامل تاریخ ادبیات، قرن‌ها، شاعران، نویسندگان و آثار فنون دهم (۱۴۰۳)، یازدهم (۱۴۰۴) و دوازدهم (۱۴۰۵)"
    "fonoon_04_mnemonics_final20" -> "📚 مطابق بارم‌بندی نهایی علوم و فنون ۳ دوازدهم (چاپ ۱۴۰۵) و رمزهای تاریخ ادبیات"

    // Logic & Philosophy
    "phil_00_khodemoni_logic_10_complete" -> "📚 آموزش کاملاً خودمونی و داستانی کل کتاب منطق — پایه دهم انسانی (چاپ ۱۴۰۳ ویژه کنکور ۱۴۰۶)"
    "phil_01_logic_concepts_five_universals" -> "📚 مطابق درس ۱ تا ۵ کتاب منطق — پایه دهم انسانی (چاپ ۱۴۰۳ ویژه کنکور ۱۴۰۶)"
    "phil_02_logic_propositions_square" -> "📚 مطابق درس ۶ و ۷ کتاب منطق — پایه دهم انسانی (چاپ ۱۴۰۳ ویژه کنکور ۱۴۰۶)"
    "phil_03_logic_syllogisms_fallacies" -> "📚 مطابق درس ۸، ۹ و ۱۰ کتاب منطق — پایه دهم انسانی (چاپ ۱۴۰۳ ویژه کنکور ۱۴۰۶)"
    "phil_04_grade11_history_epistemology" -> "📚 مطابق درس ۱ تا ۱۲ کتاب فلسفه ۱ — پایه یازدهم انسانی (چاپ ۱۴۰۴ ویژه کنکور ۱۴۰۶)"
    "phil_05_grade12_existence_quiddity" -> "📚 مطابق درس ۱ کتاب فلسفه ۲ — پایه دوازدهم انسانی (چاپ ۱۴۰۵ ویژه کنکور ۱۴۰۶)"
    "phil_06_grade12_causality_motion_schools" -> "📚 مطابق درس ۳، ۱۰ و ۱۱ کتاب فلسفه ۲ — پایه دوازدهم انسانی (چاپ ۱۴۰۵ ویژه کنکور ۱۴۰۶)"
    "phil_07_grade12_mulla_sadra_and_theology" -> "📚 مطابق درس ۵ و ۶ کتاب فلسفه ۲ — پایه دوازدهم انسانی (چاپ ۱۴۰۵ ویژه کنکور ۱۴۰۶)"
    "phil2-02-jahan-momkenat" -> "📚 مطابق درس ۲ کتاب فلسفه ۲ — پایه دوازدهم انسانی (چاپ ۱۴۰۵ ویژه کنکور ۱۴۰۶)"
    "phil2-04-kodam-tasvir" -> "📚 مطابق درس ۴ کتاب فلسفه ۲ — پایه دوازدهم انسانی (چاپ ۱۴۰۵ ویژه کنکور ۱۴۰۶)"
    "phil2-07-aghl-1" -> "📚 مطابق درس ۷ کتاب فلسفه ۲ — پایه دوازدهم انسانی (چاپ ۱۴۰۵ ویژه کنکور ۱۴۰۶)"
    "phil2-08-aghl-2" -> "📚 مطابق درس ۸ کتاب فلسفه ۲ — پایه دوازدهم انسانی (چاپ ۱۴۰۵ ویژه کنکور ۱۴۰۶)"
    "phil2-09-aghaz-falsafeh" -> "📚 مطابق درس ۹ کتاب فلسفه ۲ — پایه دوازدهم انسانی (چاپ ۱۴۰۵ ویژه کنکور ۱۴۰۶)"
    "phil2-10-dowreh-miyani" -> "📚 مطابق درس ۱۰ کتاب فلسفه ۲ — پایه دوازدهم انسانی (چاپ ۱۴۰۵ ویژه کنکور ۱۴۰۶)"
    "phil2-11-dowran-motakhkher" -> "📚 مطابق درس ۱۱ کتاب فلسفه ۲ — پایه دوازدهم انسانی (چاپ ۱۴۰۵ ویژه کنکور ۱۴۰۶)"
    "phil2-12-hekmat-moaser" -> "📚 مطابق درس ۱۲ کتاب فلسفه ۲ — پایه دوازدهم انسانی (چاپ ۱۴۰۵ ویژه کنکور ۱۴۰۶)"
    "phil_08_konkoor_100_master_tables" -> "📚 جداول جامع ۱۰۰٪ منطق دهم (۱۴۰۳)، فلسفه ۱ یازدهم (۱۴۰۴) و فلسفه ۲ دوازدهم (۱۴۰۵)"
    "phil_09_khodemoni_philosophy_11_12_complete" -> "📚 آموزش کاملاً خودمونی و بدون کلمات پیچیده کل فلسفه یازدهم (چاپ ۱۴۰۴) و دوازدهم (چاپ ۱۴۰۵)"
    "philo_07_mnemonics_final20" -> "📚 مطابق کلیدواژه‌های تستی منطق دهم (۱۴۰۳)، فلسفه ۱ یازدهم (۱۴۰۴) و فلسفه ۲ دوازدهم (۱۴۰۵)"

    // History & Geography
    "hist_00_elementary_to_9th_zero" -> "📚 مطابق مطالعات اجتماعی اول ابتدایی تا نهم (تبدیل سال به قرن، گاه‌شماری و سلسله‌ها)"
    "hist_01_grade10_1403" -> "📚 صفحه ۰ تا آخر کتاب تاریخ ۱ (ایران و جهان باستان) — پایه دهم انسانی (چاپ دهم ۱۴۰۴، کد ۱۱۰۲۱۹)"
    "hist_02_grade11_1404" -> "📚 صفحه ۰ تا آخر کتاب تاریخ ۲ (تاریخ اسلام و ایران تا پایان صفویه) — پایه یازدهم انسانی (چاپ نهم ۱۴۰۴، کد ۱۱۱۲۱۹)"
    "hist_03_grade12_1405" -> "📚 صفحه ۰ تا آخر کتاب تاریخ ۳ (ایران و جهان معاصر) — پایه دوازدهم انسانی (چاپ ۱۴۰۵ ویژه کنکور ۱۴۰۶)"
    "hist_04_treaties_kings_konkoor_tables" -> "📚 جداول جامع عهدنامه‌ها، شاهان و مناصب دیوانی تاریخ ۱ (۱۴۰۳)، ۲ (۱۴۰۴) و ۳ (۱۴۰۵) ویژه کنکور ۱۴۰۶"
    "geo_00_zero_maps_coordinates" -> "📚 آموزش تصویری و بدون فرمول مسائل ساعت، نصف‌النهار و مقیاس نقشه در جغرافیای کنکور"
    "geo_01_grade10_1403" -> "📚 صفحه ۰ تا آخر کتاب جغرافیای ایران (فصل ۱ تا ۶ کامل) — پایه دهم انسانی (چاپ ۱۴۰۳ ویژه کنکور ۱۴۰۶)"
    "geo_02_grade11_1404" -> "📚 صفحه ۰ تا آخر کتاب جغرافیا ۲ (ناحیه‌ای جهان) — پایه یازدهم انسانی (چاپ ۱۴۰۴ ویژه کنکور ۱۴۰۶)"
    "geo_03_grade12_1405" -> "📚 صفحه ۰ تا آخر کتاب جغرافیا ۳ (کاربردی) — پایه دوازدهم انسانی (چاپ ۱۴۰۵ ویژه کنکور ۱۴۰۶)"

    // Sociology, Psychology & Economics
    "soc_000_khodemoni_zero_to_100" -> "📚 فرهنگ‌نامه کاملاً خودمونی جامعه‌شناسی ۱ (چاپ ۱۴۰۳)، ۲ (چاپ ۱۴۰۴) و ۳ (چاپ ۱۴۰۵) — ویژه پایه ضعیف و ۱۰۰ زدن بدون حفظ کردن"
    "soc_00_elementary_to_9th_zero" -> "📚 مطابق مطالعات اجتماعی پایه اول تا نهم (نیاز و خواسته، گروه، نقش و کنش اجتماعی)"
    "soc_01_grade10_1403_complete" -> "📚 صفحه ۰ تا آخر کتاب جامعه‌شناسی ۱ (بخش اول: کنش، فرهنگ، ارزش و هنجار) — پایه دهم انسانی (چاپ ۱۴۰۳)"
    "soc_01b_grade10_identity_mobility_control" -> "📚 صفحه ۰ تا آخر کتاب جامعه‌شناسی ۱ (بخش دوم: هویت، جامعه‌پذیری، تحرک و کج‌روی) — پایه دهم انسانی (چاپ ۱۴۰۳)"
    "soc_02_grade11_1404_complete" -> "📚 صفحه ۰ تا آخر کتاب جامعه‌شناسی ۲ (بخش اول: فرهنگ جهانی و استعمار) — پایه یازدهم انسانی (چاپ ۱۴۰۴)"
    "soc_02b_grade11_west_islam_modernity" -> "📚 صفحه ۰ تا آخر کتاب جامعه‌شناسی ۲ (بخش دوم: رنسانس، روشنگری، اومانیسم، سکولاریسم و بیداری اسلامی) — پایه یازدهم (چاپ ۱۴۰۴)"
    "soc_03_grade12_1405_complete" -> "📚 صفحه ۰ تا آخر کتاب جامعه‌شناسی ۳ (درس ۱ تا ۱۰: ذخیره دانشی تا افق علوم اجتماعی در جهان اسلام) — پایه دوازدهم انسانی (چاپ ۱۴۰۵، کد ۱۱۲۲۲۲)"
    "psych_00_zero_khodemoni" -> "📚 ترجمه کاملاً خودمونی تمام اصطلاحات روان‌شناسی یازدهم (مکاتب، شرطی‌سازی کلاسیک و کنشی، استرس) به زبان کوچه و بازار"
    "psych_01_grade11_1404_complete" -> "📚 صفحه ۰ تا آخر کتاب روان‌شناسی یازدهم (فصل ۱ تا ۸ کامل) — پایه یازدهم انسانی (چاپ ۱۴۰۴ ویژه کنکور ۱۴۰۶)"
    "psych_02_sensation_memory_forgetting" -> "📚 مطابق فصل ۳ و ۴ روان‌شناسی یازدهم (چاپ ۱۴۰۴) — احساس، توجه، ادراک، حافظه و تداخل پیش‌گستر و پس‌گستر"
    "econ_01_grade10_1403_complete" -> "📚 صفحه ۰ تا آخر کتاب اقتصاد (بخش اول: کسب‌وکار، هزینه فرصت، عرضه و تقاضا) — پایه دهم انسانی (چاپ ۱۴۰۳)"
    "econ_02_math_problems_shortcuts_100" -> "📚 شگردهای ۵ ثانیه‌ای و بدون فرمول برای حل تمام ۱۰ تیپ مسائل ریاضی اقتصاد دهم (چاپ ۱۴۰۳ ویژه ۱۰۰ زدن کنکور ۱۴۰۶)"
    "econ_03_gdp_inflation_unemployment_budget" -> "📚 مطابق فصل ۳ تا ۵ کتاب اقتصاد دهم (چاپ ۱۴۰۳) — پول، بانکداری اسلامی (مضاربه، جعاله...)، تورم، بیکاری و مالیات"

    // Din o Zendegi, Farsi, Farhangian & Remaining Humanities Textbooks
    "dini_01_grade10_1403_complete" -> "📚 صفحه ۰ تا آخر کتاب دین و زندگی ۱ (درس ۱ تا ۱۲ کامل + پیام آیات) — پایه دهم انسانی (چاپ ۱۴۰۳)"
    "dini_02_grade11_1404_complete" -> "📚 صفحه ۰ تا آخر کتاب دین و زندگی ۲ اختصاصی انسانی (درس ۱ تا ۱۴ کامل) — پایه یازدهم انسانی (چاپ ۱۴۰۴)"
    "dini_03_grade12_1405_complete" -> "📚 صفحه ۰ تا آخر کتاب دین و زندگی ۳ اختصاصی انسانی (درس ۱ تا ۱۰: توحید ذاتی/صفاتی/افعالی/عملی و قضا و قدر) — دوازدهم (چاپ ۱۴۰۵)"
    "far_05_dini_farhangian_ensani" -> "📚 مطابق منتخبی از دین و زندگی دهم (چاپ ۱۴۰۳)، یازدهم (چاپ ۱۴۰۴) و دوازدهم انسانی (چاپ ۱۴۰۵)"
    "far_08_dini_complete_10_11_12_1406" -> "📚 بانک جامع پیام آیات و روایات دین و زندگی ۱ (۱۴۰۳)، ۲ (۱۴۰۴) و ۳ (۱۴۰۵) ویژه کنکور و نهایی"
    "far_09_farsi_grammar_1_to_12_1406" -> "📚 صفحه ۰ تا آخر دستور زبان فارسی از اول ابتدایی تا فارسی ۳ دوازدهم (چاپ ۱۴۰۵)"
    "farsi_02_vocab_spelling_3_realms" -> "📚 مطابق فارسی ۱ (۱۴۰۳)، ۲ (۱۴۰۴) و ۳ (۱۴۰۵) — کلیدهای ۱۰۰٪ املا (حروف هم‌آوا)، ساختمان واژه و قلمروها"
    "farsi_03_negaresh_final_exam_20" -> "📚 مطابق کتاب نگارش ۱، ۲ و ۳ (ویژه امتحان نهایی دوازدهم چاپ ۱۴۰۵) — فرمول ۲۰ گرفتن در نگارش"
    "gen_01_salamat_grade12_1405" -> "📚 صفحه ۰ تا آخر کتاب سلامت و بهداشت — پایه دوازدهم (چاپ ۱۴۰۵ ویژه امتحان نهایی)"
    "gen_02_hoviyat_family_grade12_1405" -> "📚 صفحه ۰ تا آخر کتب هویت اجتماعی ۱۲ و مدیریت خانواده و سبک زندگی ۱۲ — پایه دوازدهم (چاپ ۱۴۰۵ ویژه امتحان نهایی)"
    "gen_03_mohit_zist_grade11_1404" -> "📚 صفحه ۰ تا آخر کتاب انسان و محیط زیست — پایه یازدهم (چاپ ۱۴۰۴)"
    "gen_04_savad_resaneh_karafarini_grade10_1403" -> "📚 صفحه ۰ تا آخر کتب تفکر و سواد رسانه‌ای ۱۰ و کارگاه کارآفرینی و تولید ۱۰ — پایه دهم (چاپ ۱۴۰۳)"
    "far_10_remaining_humanities_books_10_11_12" -> "📚 جمع‌بندی سلامت و بهداشت دوازدهم (۱۴۰۵)، مدیریت خانواده و هویت اجتماعی ۱۲، محیط زیست ۱۱، سواد رسانه‌ای و کارآفرینی ۱۰"
    "far_01_roadmap_1406" -> "📚 مطابق دفترچه اختصاصی فرهنگیان ۱۴۰۶ و سند تحول بنیادین آموزش و پرورش (۶ ساحت تربیتی)"
    "far_02_gharaati_talim" -> "📚 صفحه ۰ تا آخر کتاب «مهارت‌های معلمی» استاد محسن قرائتی (فصل ۱، ۲ و ۳) ویژه کنکور ۱۴۰۶"
    "far_03_teacher_intelligence" -> "📚 مطابق سرفصل هوش و استعداد معلمی دفترچه اختصاصی کنکور فرهنگیان ۱۴۰۶"
    "far_06_comprehensive_interview_scenarios" -> "📚 مطابق کانون ارزیابی فرهنگیان ۱۴۰۶ + سناریوهای مصاحبه تخصصی"
    "far_04_interview_humanities" -> "📚 مطابق گزینش عقیدتی-سیاسی و احکام و تاریخ اسلام کنکور فرهنگیان ۱۴۰۶"
    "far_07_konkoor_full_package_humanities_tables" -> "📚 جداول جامع فول‌پکیج کنکور انسانی و فرهنگیان ۱۴۰۶"
    "farhangian_04_mnemonics_final20" -> "📚 مطابق رمزهای طلایی دفترچه فرهنگیان ۱۴۰۶ و ۲۰ امتحان نهایی دروس انسانی"
    "arabic_step01_alphabet_diacritics_nouns" -> "📚 پله ۱ عربی از منفی صفر: جدول کامل حرکات، تنوین، تشخیص اسم و فعل و حرف، مثنی و جمع با اعراب رنگی"
    "arabic_step03_complete_14_sigha_tables" -> "📚 پله ۳ عربی: جدول کامل ۱۴ صیغه ماضی، مضارع، امر و نهی و ضمایر متصل و منفصل با اعراب دقیق"
    "arabic_step06_all_8_abwab_master_table" -> "📚 پله ۶ عربی (پایه دهم): جدول فوق‌منظم ۸ باب ثلاثی مزید و فعل مجهول با اعراب کامل"
    "arabic_step11_mansoobat_estisna_monada_table" -> "📚 پله ۱۱ عربی (پایه دوازدهم): جدول جامع مفعول مطلق، حال، تمییز، استثناء، منادا و لای نفی جنس"
    "english_step01_pronouns_tobe_sentence_table" -> "📚 پله ۱ زبان انگلیسی از منفی صفر: جدول کامل ضمایر، am/is/are/was/were و فرمول جمله‌سازی به فارسی"
    "english_step03_all_tenses_step_by_step_table" -> "📚 پله ۳ زبان انگلیسی: جدول کامل و مقایسه‌ای تمام ۸ زمان انگلیسی از حال ساده تا ماضی بعید"
    "english_step11_passive_conditionals_relative_tags" -> "📚 پله ۱۱ زبان انگلیسی (Vision 3): جدول جامع جملات مجهول، Tag Question، ضمایر موصولی و شرطی نوع ۱ و ۲"
    "soc_master_10_11_12_full_encyclopedia" -> "📚 دایرةالمعارف جامع جامعه‌شناسی ۱، ۲ و ۳ با جدول مقایسه‌ای ۳ رویکرد تبیینی، تفسیری و انتقادی"
    "fonoon_00_zero_to_100_all_24_arrayeh_step_by_step" -> "📚 پله ۱ علوم و فنون از منفی صفر: آموزش پله‌به‌پله تمام ۲۴ آرایه ادبی از ساده‌ترین تا پیشرفته‌ترین"
    "far_03a_verbal_logical_math_intelligence_full" -> "📚 دایرةالمعارف جامع هوش و استعداد معلمی فرهنگیان (کلامی، منطقی، تحلیلی، ریاضی و فضایی)"

    // 46 Newly Added Official Ministry of Education (chap.sch.ir) Complete Chapter Lessons
    "math_01b_powers_radicals_identities" -> "📚 مطابق ریاضی پایه هشتم و نهم و فصل ۱ ریاضی دهم انسانی (توان، رادیکال، اتحادها و تجزیه)"
    "math_02b_sets_venn_intervals" -> "📚 مطابق فصل ۱ ریاضی نهم و پیش‌نیاز ریاضی دهم انسانی (مجموعه‌ها، نمودار ون و بازه‌ها)"
    "math_03_functions" -> "📚 مطابق فصل ۲ ریاضی و آمار ۱ — پایه دهم انسانی (چاپ ۱۴۰۳، کد ۱۱۰۲۱۵: مفهوم تابع، دامنه و برد)"
    "math_05c_grade11_functions_indices" -> "📚 مطابق فصل ۲ و ۳ ریاضی و آمار ۲ — پایه یازدهم انسانی (چاپ ۱۴۰۴، کد ۱۱۱۲۱۵: توابع پلکانی، نمایی و شاخص‌های آماری)"
    "arabic_02_feal_bab_majhool" -> "📚 مطابق درس ۳ تا ۸ عربی، زبان قرآن ۱ — پایه دهم انسانی (چاپ ۱۴۰۳، کد ۱۱۰۲۱۱: ۸ باب مزید و فعل مجهول)"
    "arabic_11_ch1_tafzeel_makan_zaman" -> "📚 مطابق درس ۱ و ۲ عربی، زبان قرآن ۲ — پایه یازدهم انسانی (چاپ ۱۴۰۴، کد ۱۱۱۲۱۱: اسم تفضیل، مکان، زمان و خطاهای ترجمه)"
    "arabic_11_ch2_shart_and_naqeseh" -> "📚 مطابق درس ۳ و ۴ عربی، زبان قرآن ۲ — پایه یازدهم انسانی (چاپ ۱۴۰۴، کد ۱۱۱۲۱۱: اسلوب شرط و افعال ناقصه)"
    "arabic_11_ch3_moqarabeh_vasfiyeh_huruf" -> "📚 مطابق درس ۵، ۶ و ۷ عربی، زبان قرآن ۲ — پایه یازدهم انسانی (چاپ ۱۴۰۴، کد ۱۱۱۲۱۱: افعال مقاربه، جمله وصفیه و معانی حروف)"
    "english_10_vision1_lesson1_saving_nature" -> "📚 مطابق درس ۱ و ۲ کتاب Vision 1 — پایه دهم (چاپ ۱۴۰۳، کد ۱۱۰۲۱۲: Saving Nature, Future Will / Be going to)"
    "english_10_vision1_lesson2_wonders_creation" -> "📚 مطابق درس ۲ و ۳ کتاب Vision 1 — پایه دهم (چاپ ۱۴۰۳، کد ۱۱۰۲۱۲: Wonders of Creation, Comparatives & Superlatives)"
    "english_10_vision1_lesson3_value_knowledge" -> "📚 مطابق درس ۳ و ۴ کتاب Vision 1 — پایه دهم (چاپ ۱۴۰۳، کد ۱۱۰۲۱۲: Value of Knowledge, Past Progressive, Reflexive & Modals)"
    "english_11_vision2_lesson1_healthy_lifestyle" -> "📚 مطابق درس ۱ کتاب Vision 2 — پایه یازدهم (چاپ ۱۴۰۴، کد ۱۱۱۲۱۲: Gifts of Youth & Healthy Lifestyle, Countable/Uncountable & Present Perfect)"
    "english_11_vision2_lesson2_gerund_infinitive" -> "📚 مطابق درس ۲ کتاب Vision 2 — پایه یازدهم (چاپ ۱۴۰۴، کد ۱۱۱۲۱۲: Gerunds, Infinitives & Present Perfect For/Since)"
    "english_11_vision2_lesson3_culture_and_art" -> "📚 مطابق درس ۳ کتاب Vision 2 — پایه یازدهم (چاپ ۱۴۰۴، کد ۱۱۱۲۱۲: Art & Culture, Conditional Type 1 & Phrasal Verbs)"
    "arooz_00_bahr_barcode" -> "📚 مطابق علوم و فنون ادبی ۲ و ۳ (چاپ ۱۴۰۴ و ۱۴۰۵) — جدول بارکد ۴ رقمی بحرها و تشخیص فوری اوزان"
    "arooz_10_ch2_ghafieh_tabsareha" -> "📚 مطابق درس ۹ و ۱۲ علوم و فنون ادبی ۱ — پایه دهم انسانی (چاپ ۱۴۰۳، کد ۱۱۰۲۱۸: قواعد، حروف و تبصره‌های قافیه)"
    "arooz_11_ch2_nahamsan_meters" -> "📚 مطابق درس ۶، ۹ و ۱۲ علوم و فنون ادبی ۲ — پایه یازدهم انسانی (چاپ ۱۴۰۴، کد ۱۱۱۲۱۸: اوزان ناهمسان و دولختی)"
    "fonoon_01_bayani_figures" -> "📚 مطابق درس ۲، ۵، ۸ و ۱۱ علوم و فنون ادبی ۲ — پایه یازدهم انسانی (چاپ ۱۴۰۴، کد ۱۱۱۲۱۸: تشبیه، استعاره، مجاز و کنایه)"
    "philo_03_logic_syllogism_conditional" -> "📚 مطابق درس ۸، ۹ و ۱۰ منطق — پایه دهم انسانی (چاپ ۱۴۰۳، کد ۱۱۰۲۲۰: قیاس اقترانی، قیاس استثنایی و مغالطات)"
    "logic_10_lesson1_2_concepts_terms" -> "📚 مطابق درس ۱ و ۲ منطق — پایه دهم انسانی (چاپ ۱۴۰۳، کد ۱۱۰۲۲۰: تفکر، تصور و تصدیق، دلالت و اقسام الفاظ)"
    "logic_10_lesson3_4_relations_five_universals" -> "📚 مطابق درس ۳ و ۴ منطق — پایه دهم انسانی (چاپ ۱۴۰۳، کد ۱۱۰۲۲۰: مفاهیم جزئی و کلی، نسب اربع و کلیات خمس)"
    "logic_10_lesson5_definitions" -> "📚 مطابق درس ۵ منطق — پایه دهم انسانی (چاپ ۱۴۰۳، کد ۱۱۰۲۲۰: تعریف، حد و رسم، شروط تعریف و تقسیم منطقی)"
    "philo_04_grade11_history_epistemology" -> "📚 مطابق درس ۱ تا ۱۲ فلسفه ۱ — پایه یازدهم انسانی (چاپ ۱۴۰۴، کد ۱۱۱۲۱۹: تاریخ فلسفه یونان و اسلامی و معرفت‌شناسی)"
    "soc_10_ch1_human_social_action" -> "📚 مطابق درس ۱ تا ۴ جامعه‌شناسی ۱ — پایه دهم انسانی (چاپ ۱۴۰۳: کنش‌های ما، پدیده‌های اجتماعی، جهان اجتماعی، اجزا و لایه‌های جهان اجتماعی)"
    "soc_10_ch2_culture_layers_values" -> "📚 مطابق درس ۵ تا ۹ جامعه‌شناسی ۱ — پایه دهم انسانی (چاپ ۱۴۰۳: جهان‌های اجتماعی، پیامدها و ارزیابی، هویت، بازتولید هویت اجتماعی)"
    "soc_11_ch1_cultural_world_globalization" -> "📚 مطابق درس ۱ تا ۷ جامعه‌شناسی ۲ — پایه یازدهم انسانی (چاپ ۱۴۰۴: جهان فرهنگی، فرهنگ جهانی، نمونه‌های فرهنگ جهانی، باورها و ارزش‌های بنیادین فرهنگ غرب، تکوین فرهنگ معاصر غرب، جامعه جهانی)"
    "soc_12_ch1_three_approaches_social_sciences" -> "📚 مطابق درس ۱ تا ۵ جامعه‌شناسی ۳ — پایه دوازدهم انسانی (چاپ ۱۴۰۵، کد ۱۱۲۲۲۲: ذخیره دانشی، علوم اجتماعی، نظم اجتماعی، کنش اجتماعی، معنای زندگی)"
    "geo_10_ch1_location_topography_climate_rivers" -> "📚 مطابق درس ۱ تا ۵ جغرافیای ایران — پایه دهم (چاپ ۱۴۰۳، کد ۱۱۰۲۰۹: موقعیت، ناهمواری‌ها، آب‌وهوا و منابع آب ایران)"
    "geo_10_ch3_economy_transport_borders" -> "📚 مطابق درس ۸ تا ۱۰ جغرافیای ایران — پایه دهم (چاپ ۱۴۰۳، کد ۱۱۰۲۰۹: اقتصاد، حمل‌ونقل، استان‌ها و مرزهای سیاسی ایران)"
    "geo_11_ch1_regions_biomes_culture" -> "📚 مطابق درس ۱ تا ۵ جغرافیا ۲ (ناحیه‌ای) — پایه یازدهم انسانی (چاپ ۱۴۰۴، کد ۱۱۱۲۱۷: مفهوم ناحیه، زیست‌بوم‌ها و نواحی فرهنگی)"
    "geo_11_ch3_case_studies_sw_asia_europe" -> "📚 مطابق درس ۸ تا ۱۰ جغرافیا ۲ (ناحیه‌ای) — پایه یازدهم انسانی (چاپ ۱۴۰۴، کد ۱۱۱۲۱۷: جنوب غربی آسیا، اروپا و ژئوپلیتیک)"
    "geo_12_ch1_applied_geography_hazards" -> "📚 مطابق درس ۱ تا ۴ جغرافیا ۳ (کاربردی) — پایه دوازدهم انسانی (چاپ ۱۴۰۵، کد ۱۱۲۲۱۷: شهر و روستا، مخاطرات طبیعی و مدیریت بحران)"
    "dini_10_ch1_purpose_creation_theology" -> "📚 مطابق درس ۱ تا ۱۲ دین و زندگی ۱ — پایه دهم انسانی (چاپ دهم ۱۴۰۴، کد ۱۱۰۲۰۵)"
    "dini_10_ch3_friendship_ethics_society" -> "📚 مطابق درس ۷ تا ۱۲ دین و زندگی ۱ — پایه دهم انسانی (چاپ ۱۴۰۳، کد ۱۱۰۲۰۱: واقعه بزرگ قیامت، فرشتگان، دوستی و عفاف)"
    "dini_11_ch1_divine_guidance_quran_prophet" -> "📚 مطابق درس ۱ تا ۵ دین و زندگی ۲ — پایه یازدهم انسانی (چاپ ۱۴۰۴، کد ۱۱۱۲۱۴: هدایت الهی، اعجاز قرآن و ختم نبوت)"
    "dini_11_ch3_imams_life_ghaybat_velayat" -> "📚 مطابق درس ۹ تا ۱۴ دین و زندگی ۲ — پایه یازدهم انسانی (چاپ ۱۴۰۴، کد ۱۱۱۲۱۴: سیره ائمه ع، عصر غیبت، ولایت فقیه و انتظار)"
    "farsi_10_ch2_literary_intellectual_realms" -> "📚 مطابق درس ۱ تا ۱۸ فارسی ۱ — پایه دهم (چاپ ۱۴۰۳، کد ۱۱۰۲۰۱: قلمرو ادبی، فکری، املا و تاریخ ادبیات)"
    "farsi_12_ch1_syntax_noun_phrase_realms" -> "📚 مطابق درس ۱ تا ۱۸ فارسی ۳ — پایه دوازدهم (چاپ ۱۴۰۵، کد ۱۱۲۲۰۳: قلمرو زبانی، گروه اسمی، وابسته‌ها و شمارش واژه)"
    "farsi_12_ch2_final_exam_vocab_spelling_authors" -> "📚 مطابق درس ۱ تا ۱۸ فارسی ۳ — پایه دوازدهم (چاپ ۱۴۰۵، کد ۱۱۲۲۰۳: قلمرو ادبی و فکری، هم‌آواها و تاریخ ادبیات نهایی)"
    "negaresh_10_ch2_proverb_parable_writing" -> "📚 مطابق درس ۱ تا ۶ نگارش ۱ — پایه دهم (چاپ ۱۴۰۳، کد ۱۱۰۲۰۲: مثل‌نویسی، نوشتن با فهرست، سنجش و مقایسه و حکایت‌نگاری)"
    "hoviyat_04_iranian_islamic_identity_globalization" -> "📚 مطابق فصل ۵ و ۶ هویت اجتماعی — پایه دوازدهم (چاپ ۱۴۰۵، کد ۱۱۲۲۲۵: هویت ایرانی-اسلامی، انقلاب اسلامی و جهانی شدن)"
    "salamat_01_health_dimensions_nutrition_bmi" -> "📚 مطابق فصل ۱ تا ۳ سلامت و بهداشت — پایه دوازدهم (چاپ ۱۴۰۵، کد ۱۱۲۲۰۸: ابعاد سلامت، تغذیه سالم، BMI و سطوح پیشگیری)"
    "resaneh_10_ch2_media_diet_ethics_entrepreneurship" -> "📚 مطابق تفکر و سواد رسانه‌ای دهم (کد ۱۱۰۲۱۶) و کارگاه کارآفرینی و تولید دهم (چاپ ۱۴۰۳: رژیم مصرف رسانه‌ای و بوم کسب‌وکار)"
    "mohit_11_ch2_biodiversity_energy_pollution" -> "📚 مطابق فصل ۴ تا ۷ انسان و محیط زیست — پایه یازدهم (چاپ ۱۴۰۴، کد ۱۱۱۲۱۶: پسماند، تنوع زیستی، انرژی پاک و گردشگری پایدار)"
    "family_12_management_lifestyle_complete" -> "📚 مطابق فصل ۱ تا ۶ مدیریت خانواده و سبک زندگی — پایه دوازدهم (چاپ ۱۴۰۵، کد ۱۱۲۲۰۹: انتخاب همسر، ارتباط همسران و اقتصاد خانواده)"

    else -> "📚 مطابق سرفصل رسمی کتب درسی رشته علوم انسانی (دهم ۱۴۰۳، یازدهم ۱۴۰۴، دوازدهم ۱۴۰۵) ویژه کنکور ۱۴۰۶"
}

/**
 * Enforces strict prerequisite step-by-step ordering within every subject so that
 * a student starting at "below zero" (-0) learns Lesson 1 before Lesson 2, Lesson 2 before Lesson 3, etc.
 */
fun ZeroLesson.prerequisiteStepOrder(): Int = when (id) {
    // 1. ARABIC: Strict step-by-step order from alphabet/diacritics -> pronouns -> 14 sighas -> Grade 10 -> Grade 11 -> Grade 12 -> Konkoor Mastery
    "arabic_step01_alphabet_diacritics_nouns" -> 1
    "arabic_0000_absolute_zero_7th_to_12th_khodemoni" -> 2
    "arabic_01_word_types_pronouns" -> 3
    "arabic_step03_complete_14_sigha_tables" -> 4
    "arabic_00_sigha_hand_code" -> 5
    "arabic_000_bismillah_mastery" -> 6
    "arabic_01b_numbers_time_nouns" -> 7
    "arabic_step06_all_8_abwab_master_table" -> 8
    "arabic_02_verbs_abwab" -> 9
    "arabic_02_feal_bab_majhool" -> 10
    "arabic_02b_derived_nouns_qaleb" -> 11
    "arabic_11_ch1_tafzeel_makan_zaman" -> 12
    "arabic_11_ch2_shart_and_naqeseh" -> 13
    "arabic_11_ch3_moqarabeh_vasfiyeh_huruf" -> 14
    "arabic_03b_grade11_shart_naqeseh_vasf" -> 15
    "arabic_03_grade12_rules" -> 16
    "arabic_04_grade12_istisna_hasr_hal" -> 17
    "arabic_step11_mansoobat_estisna_monada_table" -> 18
    "arabic_visual_persian_mastery" -> 19
    "arabic_05_konkoor_translation_keys" -> 20
    "arabic_06_konkoor_100_tables_and_traps" -> 21
    "arabic_04_mnemonics_final20" -> 22

    // 2. ENGLISH: Strict step-by-step order from dictionary of terms -> pronouns & To Be -> sentence structure -> all tenses -> Grade 10 -> Grade 11 -> Grade 12 -> Vocabulary
    "english_000_zero_dictionary_grammar_words" -> 1
    "english_step01_pronouns_tobe_sentence_table" -> 2
    "english_01_sentence_zero" -> 3
    "english_step03_all_tenses_step_by_step_table" -> 4
    "english_00_seventh_grade_train" -> 5
    "english_000_bismillah_mastery" -> 6
    "english_01b_nouns_comparatives_adjectives" -> 7
    "english_10_vision1_lesson1_saving_nature" -> 8
    "english_10_vision1_lesson2_wonders_creation" -> 9
    "english_10_vision1_lesson3_value_knowledge" -> 10
    "english_01c_gerund_infinitive_conjunctions" -> 11
    "english_11_vision2_lesson1_healthy_lifestyle" -> 12
    "english_11_vision2_lesson2_gerund_infinitive" -> 13
    "english_11_vision2_lesson3_culture_and_art" -> 14
    "english_02_grade12_grammar" -> 15
    "english_04_tag_questions_condition_passive" -> 16
    "english_step11_passive_conditionals_relative_tags" -> 17
    "english_full_persian_mastery" -> 18
    "english_03_grade12_reading_cloze_vocab" -> 19
    "english_05_konkoor_100_master_tables" -> 20
    "english_06_full_vocab_7_to_12_konkoor" -> 21
    "english_full_nahaee_konkoor" -> 22

    // 3. MATH: Strict sequential pedagogical order from 1 to 17 matching official textbooks
    "math_00_elementary_school" -> 1
    "math_01_signs_fractions" -> 2
    "math_000_bismillah_mastery" -> 3
    "math_01b_powers_radicals_identities" -> 4
    "math_02b_sets_venn_intervals" -> 5
    "math_02_equations" -> 6
    "math_03_radicals_powers" -> 7
    "math_03_functions" -> 8
    "math_04_functions_domain" -> 9
    "math_06_statistics_dispersion" -> 10
    "math_09_statistics_percentiles" -> 11
    "math_10_ch4_data_display" -> 12
    "math_08_logic_propositions" -> 13
    "math_11_grade11_functions_exponential" -> 14
    "math_05c_grade11_functions_indices" -> 15
    "math_07_grade12_regression_time_series" -> 16
    "math_07_combinatorics_probability" -> 17
    "math_10_conditional_probability" -> 18
    "math_05_sequences_arithmetic" -> 19
    "math_12_konkoor_80_percent_shortcuts" -> 20

    // 4. FONOON: Step-by-step from 24 literary devices zero-to-100 -> Grade 10 -> Grade 11 -> Grade 12 -> Micro-notes encyclopedia
    "fonoon_00_zero_to_100_all_24_arrayeh_step_by_step" -> 1
    "fonoon_10_ch1_history_khorasani" -> 2
    "fonoon_10_ch2_badi_tarsee_movazeneh" -> 3
    "fonoon_10_ch3_tashbih_majaz_10" -> 4
    "fonoon_01b_verbal_figures_saj" -> 5
    "fonoon_11_ch1_history_eraqi" -> 6
    "fonoon_11_ch2_tashbih_types" -> 7
    "fonoon_11_ch3_estearah_majaz_kenayeh" -> 8
    "fonoon_01_four_pillars" -> 9
    "fonoon_01_bayani_figures" -> 10
    "fonoon_12_ch1_history_hindi_bazgasht" -> 11
    "fonoon_02_iham_paradox_badie" -> 12
    "fonoon_02b_spiritual_figures_talmih" -> 13
    "fonoon_04_grade12_lafo_nashr_esloob_hasan" -> 14
    "fonoon_03_styles_history" -> 15
    "fonoon_05_styles_history_comprehensive" -> 16
    "fonoon_07_complete_literary_history_10_11_12" -> 17
    "fonoon_06_advanced_konkoor_arrayeh_traps" -> 18
    "fonoon_04_mnemonics_final20" -> 19
    "fonoon_master_arrayeh_micro_notes" -> 20

    // 5. AROOZ: Step-by-step from clapping syllables -> U and - -> Ghafieh -> Grade 11 -> Grade 12 -> Complete Encyclopedia
    "arooz_00_clapping_zero" -> 1
    "arooz_01_syllables_zero" -> 2
    "arooz_02_cutting_u_dash" -> 3
    "arooz_10_ch1_syllables_and_script" -> 4
    "arooz_04_ghafieh_rules" -> 5
    "arooz_10_ch2_ghafieh_tabsareha" -> 6
    "arooz_00_bahr_barcode" -> 7
    "arooz_03_samaei_rhythms" -> 8
    "arooz_11_ch2_nahamsan_meters" -> 9
    "arooz_04_mnemonics_mastery" -> 10
    "arooz_03b_poetic_licenses" -> 11
    "arooz_05_all_16_konkoor_meters" -> 12
    "arooz_06_konkoor_100_speed_scansion" -> 13
    "arooz_master_complete_100" -> 14

    // 6. LOGIC (منطق دهم): Lesson 1..10 in textbook order
    "phil_00_khodemoni_logic_10_complete" -> 1
    "logic_10_lesson1_2_concepts_terms" -> 2
    "logic_10_lesson3_4_relations_five_universals" -> 3
    "logic_10_lesson5_definitions" -> 4
    "phil_01_logic_concepts_five_universals" -> 5
    "phil_02_logic_propositions_square" -> 6
    "phil_03_logic_syllogisms_fallacies" -> 7
    "philo_03_logic_syllogism_conditional" -> 8
    "logic_phil_complete_mastery" -> 9

    // 7. PHILOSOPHY (فلسفه ۱ یازدهم و فلسفه ۲ دوازدهم): Lesson 1..12 Grade 11 -> Lesson 1..12 Grade 12 -> Master Tables
    "phil_11_ch1_what_is_philosophy_greeks" -> 1
    "phil_11_ch2_epistemology_knowledge" -> 2
    "phil_11_ch3_islamic_philosophy_ethics" -> 3
    "phil_04_grade11_history_epistemology" -> 4
    "philo_04_grade11_history_epistemology" -> 5
    "phil_09_khodemoni_philosophy_11_12_complete" -> 6
    "phil_05_grade12_existence_quiddity" -> 7
    "phil2-02-jahan-momkenat" -> 8
    "phil_06_grade12_causality_motion_schools" -> 9
    "phil2-04-kodam-tasvir" -> 10
    "phil_07_grade12_mulla_sadra_and_theology" -> 11
    "phil2-07-aghl-1" -> 12
    "phil2-08-aghl-2" -> 13
    "phil2-09-aghaz-falsafeh" -> 14
    "phil2-10-dowreh-miyani" -> 15
    "phil2-11-dowran-motakhkher" -> 16
    "phil2-12-hekmat-moaser" -> 17
    "phil_08_konkoor_100_master_tables" -> 18
    "philo_07_mnemonics_final20" -> 19

    // 8. SOCIOLOGY: Step-by-step from zero dictionary -> elementary -> Grade 10 -> Grade 11 -> Grade 12 -> Full Encyclopedia
    "soc_000_khodemoni_zero_to_100" -> 1
    "soc_00_elementary_to_9th_zero" -> 2
    "soc_10_ch1_human_social_action" -> 3
    "soc_10_ch2_culture_layers_values" -> 4
    "soc_01_grade10_1403_complete" -> 5
    "soc_01b_grade10_identity_mobility_control" -> 6
    "soc_11_ch1_cultural_world_globalization" -> 7
    "soc_02_grade11_1404_complete" -> 8
    "soc_02b_grade11_west_islam_modernity" -> 9
    "soc_12_ch1_three_approaches_social_sciences" -> 10
    "soc_03_grade12_1405_complete" -> 11
    "soc_12_ch2_social_order_family_policy" -> 12
    "soc_master_10_11_12_full_encyclopedia" -> 13

    // 9. PSYCHOLOGY (روان‌شناسی یازدهم): Zero -> Ch 1&2 -> Ch 3&4 -> Ch 5&6 -> Ch 7&8 -> Complete
    "psych_00_zero_khodemoni" -> 1
    "psych_11_ch1_schools_and_development" -> 2
    "psych_02_sensation_memory_forgetting" -> 3
    "psych_03_perception_attention_gestalt" -> 4
    "psych_04_memory_forgetting_strategies" -> 5
    "psych_11_ch3_thinking_problem_solving" -> 6
    "psych_11_ch4_stress_mental_health" -> 7
    "psych_01_grade11_1404_complete" -> 8

    // 10. ECONOMICS: Step-by-step
    "econ_02_math_problems_shortcuts_100" -> 1
    "econ_full_math_mastery" -> 2
    "economics_speed_shortcuts_master" -> 3
    "econ_01_grade10_1403_complete" -> 4
    "econ_04_market_equilibrium_curves" -> 5
    "econ_10_ch2_market_supply_demand" -> 6
    "econ_05_tax_gini_distribution" -> 7
    "econ_03_gdp_inflation_unemployment_budget" -> 8
    "econ_10_ch4_trade_exchange_resilience" -> 9

    // 11. HISTORY: Elementary -> Grade 10 -> Grade 11 -> Grade 12 -> Treaties & Tables
    "hist_00_elementary_to_9th_zero" -> 1
    "hist_10_ch1_ancient_world_mesopotamia_egypt" -> 2
    "hist_10_ch2_ancient_iran_medes_achaemenids_parthians_sasanians" -> 3
    "hist_01_grade10_1403" -> 4
    "hist_11_ch1_islam_prophet_caliphates" -> 5
    "hist_11_ch2_iran_islamic_dynasties_tahirid_to_timurid" -> 6
    "hist_02_grade11_1404" -> 7
    "hist_12_ch1_afshar_zand_qajar_constitution" -> 8
    "hist_12_ch2_pahlavi_oil_nationalization_revolution" -> 9
    "hist_03_grade12_1405" -> 10
    "hist_05_safavid_constitutional_oil" -> 11
    "hist_04_treaties_kings_konkoor_tables" -> 12

    // 12. GEOGRAPHY: Zero Maps/Time -> Grade 10 -> Grade 11 -> Grade 12
    "geo_00_zero_maps_coordinates" -> 1
    "geo_10_ch1_location_topography_climate_rivers" -> 2
    "geo_10_ch2_iran_population_settlements_economy" -> 3
    "geo_10_ch3_economy_transport_borders" -> 4
    "geo_01_grade10_1403" -> 5
    "geo_11_ch1_regions_biomes_culture" -> 6
    "geo_11_ch2_economic_political_regions_seas" -> 7
    "geo_11_ch3_case_studies_sw_asia_europe" -> 8
    "geo_02_grade11_1404" -> 9
    "geo_12_ch1_applied_geography_hazards" -> 10
    "geo_12_ch2_climate_spatial_planning_gis" -> 11
    "geo_03_grade12_1405" -> 12
    "geo_04_iran_climate_rivers_gis" -> 13

    // 13. DIN O ZENDEGI: Grade 10 -> Grade 11 -> Grade 12 -> Ayat Bank & Farhangian
    "dini_10_ch1_purpose_creation_theology" -> 1
    "dini_10_ch2_death_barzakh_hereafter" -> 2
    "dini_10_ch3_friendship_ethics_society" -> 3
    "dini_01_grade10_1403_complete" -> 4
    "dini_11_ch1_divine_guidance_quran_prophet" -> 5
    "dini_11_ch2_imamate_ghadir_velayat" -> 6
    "dini_11_ch3_imams_life_ghaybat_velayat" -> 7
    "dini_02_grade11_1404_complete" -> 8
    "dini_01_tawheed_degrees" -> 9
    "dini_03_grade12_1405_complete" -> 10
    "dini_04_divine_laws_sunan_barzakh" -> 11
    "far_05_dini_farhangian_ensani" -> 12
    "far_08_dini_complete_10_11_12_1406" -> 13

    // 14. FARSI: Grammar 1..12 -> Grade 10 -> Grade 11 -> Grade 12 -> Comprehensive Vocab/Spelling/Realms
    "far_09_farsi_grammar_1_to_12_1406" -> 1
    "farsi_01_syntax_roles_groups" -> 2
    "farsi_10_ch1_grammar_vocab_realms" -> 3
    "farsi_10_ch2_literary_intellectual_realms" -> 4
    "farsi_02_word_morphology" -> 5
    "farsi_11_ch1_grammar_vocab_realms" -> 6
    "farsi_02_vocab_spelling_3_realms" -> 7
    "farsi_12_ch1_syntax_noun_phrase_realms" -> 8
    "farsi_12_ch2_final_exam_vocab_spelling_authors" -> 9
    "farsi_04_advanced_syntax_pronoun_shift" -> 10

    // 15. NEGARESH: Grade 10 & 11 -> Grade 12 Final Exam 20
    "negaresh_01_grade10_objective_subjective" -> 1
    "negaresh_10_ch2_proverb_parable_writing" -> 2
    "negaresh_master_10_11_12_complete" -> 3
    "negaresh_03_grade11_report_dialogue_recreate" -> 4
    "negaresh_02_grade12_final_20_templates" -> 5
    "farsi_03_negaresh_final_exam_20" -> 6

    // 16. FARHANGIAN: Step-by-step
    "far_01_roadmap_1406" -> 1
    "far_02_gharaati_talim" -> 2
    "far_08_qaraati_teacher_methods_complete" -> 3
    "far_03a_verbal_logical_math_intelligence_full" -> 4
    "far_03_teacher_intelligence" -> 5
    "far_09_teacher_aptitude_reasoning" -> 6
    "far_04_interview_humanities" -> 7
    "far_06_comprehensive_interview_scenarios" -> 8
    "far_07_konkoor_full_package_humanities_tables" -> 9
    "farhangian_04_mnemonics_final20" -> 10
    "persian_internet_konkoor_gems" -> 11

    // 17. SOCIAL IDENTITY 12:
    "hoviyat_01_action_phenomena" -> 1
    "hoviyat_02_society_culture" -> 2
    "hoviyat_03_reproduction_changes" -> 3
    "hoviyat_04_iranian_islamic_identity_globalization" -> 4
    "gen_02_hoviyat_family_grade12_1405" -> 5
    "hoviyat_master_grade12_final20" -> 6

    // 18. HEALTH & HYGIENE 12:
    "salamat_01_health_dimensions_nutrition_bmi" -> 1
    "gen_01_salamat_grade12_1405" -> 2
    "salamat_02_diseases_communicable_non" -> 3
    "salamat_03_mental_health_first_aid" -> 4
    "salamat_master_grade12_final20" -> 5

    // 19. MEDIA LITERACY 10, ENVIRONMENT 11, FAMILY 12:
    "resaneh_01_persuasion_techniques" -> 1
    "resaneh_10_ch2_media_diet_ethics_entrepreneurship" -> 2
    "gen_04_savad_resaneh_karafarini_grade10_1403" -> 3
    "gen_03_mohit_zist_grade11_1404" -> 4
    "mohit_11_ch2_biodiversity_energy_pollution" -> 5
    "family_12_management_lifestyle_complete" -> 6
    "far_10_remaining_humanities_books_10_11_12" -> 7

    // 20. STUDY METHODS:
    "study_01_feynman_active_recall_leitner" -> 1
    "study_04_feynman_technique_deep_mastery" -> 2
    "study_02_cornell_mindmap_speed_reading" -> 3
    "study_03_konkoor_test_strategy_final20" -> 4

    else -> 50 + orderIndex
}

data class LessonPrerequisiteLink(
    val prerequisiteLessonId: String,
    val prerequisiteTitle: String,
    val prerequisiteBadge: String,
    val note: String
)

fun ZeroLesson.getPrerequisiteLinks(allLessons: List<ZeroLesson>): List<LessonPrerequisiteLink> {
    val results = mutableListOf<LessonPrerequisiteLink>()
    val currentSubjId = this.normalizedSubjectId()
    val sameSubjectLessons = allLessons
        .filter { it.normalizedSubjectId() == currentSubjId }
        .sortedBy { it.prerequisiteStepOrder() }

    val currentStepOrder = this.prerequisiteStepOrder()
    val directPrior = sameSubjectLessons.lastOrNull { it.prerequisiteStepOrder() < currentStepOrder && it.id != this.id }
    if (directPrior != null) {
        results.add(
            LessonPrerequisiteLink(
                prerequisiteLessonId = directPrior.id,
                prerequisiteTitle = directPrior.title,
                prerequisiteBadge = directPrior.levelBadge,
                note = "پایه و پیش‌نیاز مستقیم این درس در سلسله‌مراتب آموزشی"
            )
        )
    }

    if (currentSubjId == "philosophy") {
        allLessons.find { it.normalizedSubjectId() == "logic" && it.prerequisiteStepOrder() <= 2 }?.let { logicLesson ->
            results.add(
                LessonPrerequisiteLink(
                    prerequisiteLessonId = logicLesson.id,
                    prerequisiteTitle = logicLesson.title,
                    prerequisiteBadge = "منطق دهم (پایه استدلال)",
                    note = "فلسفه بر پایه منطق بنا شده؛ در صورت ابهام در استدلال، این درس را بخوان."
                )
            )
        }
    }
    if (currentSubjId in listOf("fonoon", "arooz") && this.id != "arooz_0" && this.id != "fonoon_0") {
        allLessons.find { it.id == "arooz_0" || it.id.contains("step01") }?.let { baseArooz ->
            if (baseArooz.id != this.id && !results.any { it.prerequisiteLessonId == baseArooz.id }) {
                results.add(
                    LessonPrerequisiteLink(
                        prerequisiteLessonId = baseArooz.id,
                        prerequisiteTitle = baseArooz.title,
                        prerequisiteBadge = "پایه تقطیع هجایی",
                        note = "برای یادگیری اوزان، اختیارات و بحور ابتدا تقطیع هجایی را مسلط شو."
                    )
                )
            }
        }
    }
    if (currentSubjId == "math") {
        val mathZero = allLessons.find { it.normalizedSubjectId() == "math" && it.orderIndex == 0 }
        if (mathZero != null && mathZero.id != this.id && !results.any { it.prerequisiteLessonId == mathZero.id } && currentStepOrder > 2) {
            results.add(
                LessonPrerequisiteLink(
                    prerequisiteLessonId = mathZero.id,
                    prerequisiteTitle = mathZero.title,
                    prerequisiteBadge = "منفی صفر ریاضی",
                    note = "اگر در کسرها، اعداد علامت‌دار یا حل معادلات ساده گیر می‌کنی، این درس را ببین."
                )
            )
        }
    }
    return results
}

/**
 * Normalizes the leading lesson number in `rawTitle` so that every lesson in a subject
 * is sequentially titled "درس ۱", "درس ۲", ..., "درس N" matching its exact row badge `stepNum`
 * without deleting a single word of the topic or textbook chapter description.
 */
fun normalizeSequentialLessonTitle(rawTitle: String, stepNum: Int): String {
    val persianDigits = charArrayOf('۰', '۱', '۲', '۳', '۴', '۵', '۶', '۷', '۸', '۹')
    val persianStep = stepNum.toString().map { ch ->
        if (ch in '0'..'9') persianDigits[ch - '0'] else ch
    }.joinToString("")

    var t = rawTitle.trim()

    // 1. Strip stray old catalog index numbers like "۴. ", "۹. ", "۱۰. "
    t = t.replace(Regex("^[۰-۹\\d]+\\.\\s*"), "")

    // 2. If it starts with a textbook chapter/lesson range like "درس ۱ تا ۶ فلسفه ۱..." or "درس ۱، ۴ و ۷ فنون ۱..."
    // preserve the textbook lesson range and prepend "درس X — "
    if (Regex("^درس\\s+[۰-۹\\d]+\\s*(تا|و|،)").containsMatchIn(t)) {
        return "درس $persianStep — $t"
    }

    // 3. If it starts with "درس زیر صفر" or "درس زیرِ صفر"
    val zeroMatch = Regex("^درس\\s+زیرِ?\\s*صفر\\s*(.*)$").find(t)
    if (zeroMatch != null) {
        val rest = zeroMatch.groupValues[1].trim()
        return if (rest.startsWith(":")) {
            "درس $persianStep (زیر صفر)$rest"
        } else {
            "درس $persianStep (زیر صفر): $rest"
        }
    }

    // 4. If it starts with "درس X", "پله X", or "گام X" (single number)
    val singleStepMatch = Regex("^(?:درس|پله|گام)\\s+[۰-۹\\d]+\\s*(.*)$").find(t)
    if (singleStepMatch != null) {
        val rest = singleStepMatch.groupValues[1].trim()
        return when {
            rest.startsWith(":") || rest.startsWith("—") || rest.startsWith("-") ->
                "درس $persianStep$rest"
            rest.startsWith("(") ->
                "درس $persianStep $rest"
            else ->
                "درس $persianStep: $rest"
        }
    }

    // 5. Otherwise prepend "درس X: " while keeping 100% of the title text
    return "درس $persianStep: $t"
}

/**
 * مدل خوشه‌بندی درسنامه‌ها (ادغام دروس موازی و تکمیلی به عنوان «زیرمجموعه» درس اصلی بدون حذف هیچ کلمه‌ای)
 */
data class LessonCluster(
    val primaryLesson: ZeroLesson,
    val subLessons: List<ZeroLesson> = emptyList()
) {
    val totalStepsCount: Int
        get() = primaryLesson.steps.size + subLessons.sumOf { it.steps.size }

    val totalExamplesCount: Int
        get() = primaryLesson.workedExamples.size + subLessons.sumOf { it.workedExamples.size }
}

/**
 * مشخص می‌کند هر درسنامه تکمیلی، جدول رمزی یا موازی، زیرمجموعهٔ کدام «درس اصلی فصل کتاب درسی» است
 * تا هیچ نکته‌ای حذف نشود ولی تکراری‌ها در فهرست و صفحه درس با هم ادغام و زیرمجموعه شوند.
 */
fun ZeroLesson.parentLessonIdOrSelf(): String = when (id) {
    // 1. Math & Statistics (ادغام دروس موازی در فصل اصلی کتاب درسی)
    "math_00_elementary_school", "math_01_signs_fractions" -> "math_000_bismillah_mastery"
    "math_03_radicals_powers" -> "math_01b_powers_radicals_identities"
    "math_03_functions" -> "math_04_functions_domain"
    "math_09_statistics_percentiles" -> "math_06_statistics_dispersion"
    "math_05_logic_stats_prob" -> "math_08_logic_propositions"
    "math_05c_grade11_functions_indices" -> "math_11_grade11_functions_exponential"
    "math_05b_counting_permutations_combinations" -> "math_07_combinatorics_probability"
    "math_06_grade12_normal_conditional_probability" -> "math_10_conditional_probability"
    "math_04_sequences" -> "math_05_sequences_arithmetic"
    "math_ultra_fast_techniques" -> "math_12_konkoor_80_percent_shortcuts"

    // 2. Arabic (ادغام جداول و دروس موازی عربی در درس اصلی هر پایه)
    "arabic_0000_absolute_zero_7th_to_12th_khodemoni", "arabic_000_bismillah_mastery", "arabic_01_word_types_pronouns" -> "arabic_step01_alphabet_diacritics_nouns"
    "arabic_00_sigha_hand_code", "arabic_00_seventh_grade_sigha" -> "arabic_step03_complete_14_sigha_tables"
    "arabic_01_sigha_fingerprint" -> "arabic_01b_numbers_time_nouns"
    "arabic_02_verbs_abwab", "arabic_02_feal_bab_majhool" -> "arabic_step06_all_8_abwab_master_table"
    "arabic_02b_derived_nouns_qaleb" -> "arabic_11_ch1_tafzeel_makan_zaman"
    "arabic_03b_grade11_shart_naqeseh_vasf" -> "arabic_11_ch2_shart_and_naqeseh"
    "arabic_03_grade12_rules", "arabic_03_mansoobat_grade12" -> "arabic_step11_mansoobat_estisna_monada_table"
    "arabic_06_konkoor_100_tables_and_traps", "arabic_04_mnemonics_final20", "arabic_ultra_fast_techniques", "arabic_visual_persian_mastery" -> "arabic_05_konkoor_translation_keys"

    // 3. English (ادغام گرامرها و جداول موازی زبان در درس اصلی)
    "english_00_seventh_grade_abc", "english_000_bismillah_mastery" -> "english_000_zero_dictionary_grammar_words"
    "english_01_sentence_zero", "english_00_seventh_grade_train" -> "english_step01_pronouns_tobe_sentence_table"
    "english_01_tenses_modals" -> "english_10_vision1_lesson1_saving_nature"
    "english_01b_nouns_comparatives_adjectives" -> "english_10_vision1_lesson2_wonders_creation"
    "english_01c_gerund_infinitive_conjunctions" -> "english_11_vision2_lesson2_gerund_infinitive"
    "english_02_grade12_grammar", "english_04_tag_questions_condition_passive", "english_full_nahaee_konkoor" -> "english_step11_passive_conditionals_relative_tags"
    "english_05_konkoor_100_master_tables", "english_full_persian_mastery" -> "english_06_full_vocab_7_to_12_konkoor"

    // 4. Arooz & Prosody (ادغام دروس موازی عروض و قافیه)
    "arooz_01_syllables_zero", "arooz_02_cutting_u_dash" -> "arooz_00_clapping_zero"
    "arooz_04_ghafieh_rules", "arooz_02_ghafieh_radif" -> "arooz_10_ch2_ghafieh_tabsareha"
    "arooz_03_samaei_rhythms", "arooz_03_meters_samaei", "arooz_04_mnemonics_mastery" -> "arooz_00_bahr_barcode"
    "arooz_06_konkoor_100_speed_scansion", "arooz_fonoon_ultra_fast_techniques", "arooz_master_complete_100" -> "arooz_05_all_16_konkoor_meters"

    // 5. Fonoon (ادغام دروس موازی تاریخ ادبیات و آرایه‌های فنون)
    "fonoon_01b_verbal_figures_saj" -> "fonoon_10_ch2_badi_tarsee_movazeneh"
    "fonoon_01_four_pillars", "fonoon_01_bayani_figures" -> "fonoon_11_ch3_estearah_majaz_kenayeh"
    "fonoon_02_iham_paradox_badie", "fonoon_02_badi_figures", "fonoon_02b_spiritual_figures_talmih" -> "fonoon_04_grade12_lafo_nashr_esloob_hasan"
    "fonoon_03_styles_history", "fonoon_05_styles_history_comprehensive" -> "fonoon_12_ch1_history_hindi_bazgasht"
    "fonoon_04_mnemonics_final20", "fonoon_master_arrayeh_micro_notes" -> "fonoon_07_complete_literary_history_10_11_12"

    // 6. Logic (ادغام دروس موازی منطق دهم در فصول اصلی کتاب)
    "phil_00_khodemoni_logic_10_complete" -> "logic_10_lesson1_2_concepts_terms"
    "phil_01_logic_concepts_five_universals" -> "logic_10_lesson3_4_relations_five_universals"
    "philo_01_logic_concepts_definitions" -> "logic_10_lesson5_definitions"
    "philo_02_logic_propositions_square" -> "phil_02_logic_propositions_square"
    "philo_03_logic_syllogism_conditional", "logic_phil_complete_mastery" -> "phil_03_logic_syllogisms_fallacies"

    // 7. Philosophy 1 & 2 (ادغام دروس موازی فلسفه یازدهم و دوازدهم)
    "phil_04_grade11_history_epistemology", "philo_04_grade11_history_epistemology" -> "phil_11_ch2_epistemology_knowledge"
    "philo_05_grade12_existence_causality" -> "phil_05_grade12_existence_quiddity"
    "philo_06_grade12_theology_islamic_schools" -> "phil_07_grade12_mulla_sadra_and_theology"
    "phil_09_khodemoni_philosophy_11_12_complete", "philo_07_mnemonics_final20" -> "phil_08_konkoor_100_master_tables"

    // 8. History 1, 2, 3 (ادغام دروس خلاصه در فصول اصلی تاریخ)
    "hist_01_grade10_1403" -> "hist_10_ch2_ancient_iran_medes_achaemenids_parthians_sasanians"
    "hist_02_grade11_1404" -> "hist_11_ch2_iran_islamic_dynasties_tahirid_to_timurid"
    "hist_03_grade12_1405", "hist_05_safavid_constitutional_oil" -> "hist_12_ch1_afshar_zand_qajar_constitution"

    // 9. Geography 1, 2, 3 (ادغام دروس خلاصه در فصول اصلی جغرافیا)
    "geo_01_grade10_1403" -> "geo_10_ch1_location_topography_climate_rivers"
    "geo_02_grade11_1404" -> "geo_11_ch1_regions_biomes_culture"
    "geo_03_grade12_1405", "geo_04_iran_climate_rivers_gis" -> "geo_12_ch2_climate_spatial_planning_gis"

    // 10. Sociology 1, 2, 3 (ادغام دروس خلاصه در فصول اصلی جامعه‌شناسی)
    "soc_00_elementary_to_9th_zero" -> "soc_000_khodemoni_zero_to_100"
    "soc_01_grade10_1403_complete" -> "soc_10_ch1_human_social_action"
    "soc_01b_grade10_identity_mobility_control" -> "soc_10_ch2_culture_layers_values"
    "soc_02_grade11_1404_complete", "soc_02b_grade11_west_islam_modernity" -> "soc_11_ch1_cultural_world_globalization"
    "soc_03_grade12_1405_complete" -> "soc_12_ch1_three_approaches_social_sciences"
    "soc_master_10_11_12_full_encyclopedia" -> "soc_12_ch2_social_order_family_policy"

    // 11. Psychology 11 (ادغام دروس موازی روان‌شناسی یازدهم)
    "psych_01_grade11_1404_complete" -> "psych_11_ch1_schools_and_development"
    "psych_04_memory_forgetting_strategies" -> "psych_02_sensation_memory_forgetting"

    // 12. Economics 10 (ادغام دروس موازی اقتصاد دهم)
    "econ_full_math_mastery", "economics_speed_shortcuts_master" -> "econ_02_math_problems_shortcuts_100"
    "econ_04_market_equilibrium_curves" -> "econ_10_ch2_market_supply_demand"

    // 13. Din o Zendegi 1, 2, 3 (ادغام دروس جامع دینی در فصول اصلی)
    "dini_01_grade10_1403_complete" -> "dini_10_ch1_purpose_creation_theology"
    "dini_02_grade11_1404_complete" -> "dini_11_ch2_imamate_ghadir_velayat"
    "dini_03_grade12_1405_complete" -> "dini_01_tawheed_degrees"
    "far_05_dini_farhangian_ensani" -> "far_08_dini_complete_10_11_12_1406"

    // 14. Farsi 1, 2, 3 (ادغام دروس موازی فارسی)
    "farsi_01_syntax_roles_groups" -> "farsi_10_ch1_grammar_vocab_realms"
    "farsi_02_word_morphology", "farsi_02_vocab_spelling_3_realms" -> "farsi_11_ch1_grammar_vocab_realms"
    "farsi_04_advanced_syntax_pronoun_shift" -> "farsi_12_ch1_syntax_noun_phrase_realms"

    // 15. Negaresh 1, 2, 3 (ادغام دروس موازی نگارش)
    "negaresh_master_10_11_12_complete" -> "negaresh_10_ch2_proverb_parable_writing"
    "farsi_03_negaresh_final_exam_20" -> "negaresh_02_grade12_final_20_templates"

    // 16. Farhangian & Intelligence & Interview (ادغام دروس موازی فرهنگیان، هوش و گزینش)
    "far_02_gharaati_talim" -> "far_08_qaraati_teacher_methods_complete"
    "far_03_teacher_intelligence", "far_09_teacher_aptitude_reasoning" -> "far_03a_verbal_logical_math_intelligence_full"
    "far_04_interview_humanities" -> "far_06_comprehensive_interview_scenarios"
    "farhangian_04_mnemonics_final20", "persian_internet_konkoor_gems" -> "far_07_konkoor_full_package_humanities_tables"

    // 17. Hoviyat 12 (ادغام درس جامع در فصل اصلی هویت اجتماعی)
    "gen_02_hoviyat_family_grade12_1405" -> "hoviyat_04_iranian_islamic_identity_globalization"

    // 18. Salamat 12 (ادغام درس جامع در فصل اصلی سلامت و بهداشت)
    "gen_01_salamat_grade12_1405" -> "salamat_01_health_dimensions_nutrition_bmi"

    // 19. Media Literacy, Environment & Family (ادغام دروس موازی)
    "gen_04_savad_resaneh_karafarini_grade10_1403" -> "resaneh_10_ch2_media_diet_ethics_entrepreneurship"
    "far_10_remaining_humanities_books_10_11_12" -> "family_12_management_lifestyle_complete"

    // 20. Study Methods (ادغام دو درس تکنیک فاینمن)
    "study_04_feynman_technique_deep_mastery" -> "study_01_feynman_active_recall_leitner"

    else -> id
}

/**
 * لیست دروس یک سرفصل یا پایه را بدون حذف هیچ درسی، به خوشه‌های (درس اصلی + زیرمجموعه‌های ادغام‌شده) تبدیل می‌کند.
 */
fun groupLessonsIntoParentAndSubLessons(lessons: List<ZeroLesson>): List<LessonCluster> {
    if (lessons.isEmpty()) return emptyList()
    val lessonById = lessons.associateBy { it.id }
    val primaryLessons = mutableListOf<ZeroLesson>()
    val subLessonsMap = mutableMapOf<String, MutableList<ZeroLesson>>()

    for (lesson in lessons) {
        val parentId = lesson.parentLessonIdOrSelf()
        if (parentId != lesson.id && lessonById.containsKey(parentId)) {
            subLessonsMap.getOrPut(parentId) { mutableListOf() }.add(lesson)
        } else {
            primaryLessons.add(lesson)
        }
    }

    return primaryLessons.map { primary ->
        LessonCluster(
            primaryLesson = primary,
            subLessons = subLessonsMap[primary.id].orEmpty()
        )
    }
}

/**
 * برای هر درس (چه درس اصلی باشد چه زیرمجموعه)، خوشهٔ کامل (درس اصلی + تمام زیرمجموعه‌هایش) را برمی‌گرداند.
 */
fun getClusterForLesson(targetLesson: ZeroLesson, allLessons: List<ZeroLesson>): LessonCluster {
    val subjectLessons = allLessons.filter {
        it.normalizedSubjectId() == targetLesson.normalizedSubjectId()
    }.sortedBy { it.orderIndex }
    val clusters = groupLessonsIntoParentAndSubLessons(subjectLessons)
    return clusters.find { cluster ->
        cluster.primaryLesson.id == targetLesson.id ||
            cluster.subLessons.any { it.id == targetLesson.id }
    } ?: LessonCluster(primaryLesson = targetLesson, subLessons = emptyList())
}




