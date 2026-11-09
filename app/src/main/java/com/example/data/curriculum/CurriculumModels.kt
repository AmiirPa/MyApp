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
                    id = "study_methods",
                    shortTitle = "روش‌های نوین یادگیری",
                    title = "روش‌های نوین یادگیری، تندخوانی و تکنیک‌های تست‌زنی کنکور",
                    subtitle = "تکنیک فاینمن، بازیابی فعال (Active Recall)، مرور فاصله‌دار لایتنر، یادداشت‌برداری کرنل، نقشه ذهنی، تندخوانی و مدیریت زمان کنکور",
                    konkoorCoefficient = "شتاب‌دهنده ۳ برابری سرعت مطالعه و حافظه",
                    targetForZeroStudent = "هدف قطعی: یادگیری ۳ برابر سریع‌تر بدون فراموشی + مدیریت زمان حرفه‌ای سر جلسه کنکور",
                    color = Color(0xFF7C3AED),
                    zeroReassurance = "یاد می‌گیری چطور هر درس را با تکنیک فاینمن و بازیابی فعال طوری بخوانی که تا روز کنکور و امتحان نهایی ۱٪ هم یادت نرود!"
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
                    id = "arooz",
                    shortTitle = "عروض و قافیه",
                    title = "عروض و قافیه (علوم و فنون ادبی ۱، ۲ و ۳)",
                    subtitle = "موسیقی شعر: هجا، وزن، تقطیع، بحرها و قافیه — اول با گوش و ریتم، بعد با نام‌ها",
                    konkoorCoefficient = "جزء درس علوم و فنون ادبی (کنکور انسانی + امتحان نهایی)",
                    targetForZeroStudent = "هدف قطعی: شنیدن وزن هر بیت و تقطیع درست آن",
                    color = SubjectAroozColor,
                    zeroReassurance = "وزن شعر را اول با کف‌زدن و ریتم یاد می‌گیری، نه با حفظ نام بحرها!"
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
                    id = "science",
                    shortTitle = "علوم تجربی",
                    title = "علوم تجربی دبستان و متوسطه اول",
                    subtitle = "آموزش گام‌به‌گام علوم از اول دبستان: زنگ علوم، هوا، زمین، نور و صدا، بدن انسان و محیط زیست",
                    konkoorCoefficient = "پایه و پیش‌نیاز دروس انسانی",
                    targetForZeroStudent = "هدف قطعی: فهم کامل مفاهیم علوم از صفر",
                    color = Color(0xFF0EA5E9),
                    zeroReassurance = "علوم را با مثال‌های روزمره و شکل، از صفر یاد می‌گیری!"
                ),
        SubjectMeta(
                    id = "hedyeh",
                    shortTitle = "هدیه‌های آسمانی",
                    title = "هدیه‌های آسمانی دبستان و متوسطه اول",
                    subtitle = "آموزش گام‌به‌گام هدیه‌های آسمانی از اول دبستان: خدا، پیامبران، اهل بیت، نماز و اخلاق",
                    konkoorCoefficient = "پایه و پیش‌نیاز دین و زندگی",
                    targetForZeroStudent = "هدف قطعی: فهم کامل مفاهیم دینی از صفر",
                    color = Color(0xFFF59E0B),
                    zeroReassurance = "مفاهیم دینی را با داستان و مثال‌های روزمره، از صفر یاد می‌گیری!"
                ),
        SubjectMeta(
                    id = "quran",
                    shortTitle = "قرآن",
                    title = "قرآن دبستان و متوسطه اول",
                    subtitle = "آموزش گام‌به‌گام قرآن از اول دبستان: روخوانی، تجوید، سوره‌ها و پیام‌های قرآنی",
                    konkoorCoefficient = "پایه و پیش‌نیاز عربی و دین و زندگی",
                    targetForZeroStudent = "هدف قطعی: روخوانی روان قرآن از صفر",
                    color = Color(0xFF059669),
                    zeroReassurance = "قرآن را قدم‌به‌قدم، از حروف تا سوره‌ها، از صفر یاد می‌گیری!"
                ),
        SubjectMeta(
                    id = "social_studies",
                    shortTitle = "مطالعات اجتماعی",
                    title = "مطالعات اجتماعی دبستان و متوسطه اول",
                    subtitle = "آموزش گام‌به‌گام مطالعات اجتماعی از اول دبستان: همسایه، شهر، استان، ایران و جهان",
                    konkoorCoefficient = "پایه و پیش‌نیاز جغرافیا، تاریخ و جامعه‌شناسی",
                    targetForZeroStudent = "هدف قطعی: فهم کامل مفاهیم اجتماعی و جغرافیایی از صفر",
                    color = Color(0xFF8B5CF6),
                    zeroReassurance = "مطالعات اجتماعی را با نقشه و داستان، از صفر یاد می‌گیری!"
                ),
        SubjectMeta(
                    id = "payam",
                    shortTitle = "پیام‌های آسمان",
                    title = "پیام‌های آسمان متوسطه اول",
                    subtitle = "آموزش گام‌به‌گام پیام‌های آسمان: خداشناسی، پیامبران، امامت، احکام و اخلاق",
                    konkoorCoefficient = "پایه و پیش‌نیاز دین و زندگی",
                    targetForZeroStudent = "هدف قطعی: فهم کامل مفاهیم دینی از صفر",
                    color = Color(0xFF14B8A6),
                    zeroReassurance = "پیام‌های آسمان را با داستان و مثال‌های روزمره، از صفر یاد می‌گیری!"
                ),
        SubjectMeta(
                    id = "karfan",
                    shortTitle = "کار و فناوری",
                    title = "کار و فناوری متوسطه اول",
                    subtitle = "آموزش گام‌به‌گام کار و فناوری: الگوریتم، برق، تأسیسات، عمران، خودرو و صنایع دستی",
                    konkoorCoefficient = "درس مهارتی متوسطه اول",
                    targetForZeroStudent = "هدف قطعی: تسلط بر پودمان‌های کار و فناوری",
                    color = Color(0xFFF97316),
                    zeroReassurance = "کار و فناوری را با فعالیت عملی، قدم‌به‌قدم یاد می‌گیری!"
                ),
        SubjectMeta(
                    id = "defa",
                    shortTitle = "آمادگی دفاعی",
                    title = "آمادگی دفاعی متوسطه اول",
                    subtitle = "آموزش آمادگی دفاعی: امنیت، پدافند غیرعامل، جنگ پنهان و امداد و نجات",
                    konkoorCoefficient = "درس مهارتی متوسطه اول",
                    targetForZeroStudent = "هدف قطعی: تسلط بر مفاهیم آمادگی دفاعی",
                    color = Color(0xFFDC2626),
                    zeroReassurance = "آمادگی دفاعی را با مثال‌های واقعی، از صفر یاد می‌گیری!"
                ),
        SubjectMeta(
                    id = "honar",
                    shortTitle = "فرهنگ و هنر",
                    title = "فرهنگ و هنر متوسطه اول",
                    subtitle = "آموزش فرهنگ و هنر: طراحی، گرافیک، عکاسی، خوشنویسی، هنرهای سنتی، آوایی و نمایشی",
                    konkoorCoefficient = "درس مهارتی متوسطه اول",
                    targetForZeroStudent = "هدف قطعی: تسلط بر مفاهیم فرهنگ و هنر",
                    color = Color(0xFFDB2777),
                    zeroReassurance = "فرهنگ و هنر را با دیدن و ساختن، از صفر یاد می‌گیری!"
                ),
        SubjectMeta(
                    id = "tafakkor",
                    shortTitle = "تفکر و سبک زندگی",
                    title = "تفکر و سبک زندگی متوسطه اول",
                    subtitle = "آموزش گام‌به‌گام تفکر و سبک زندگی: تفکر، احساس، انتخاب و سبک زندگی سالم",
                    konkoorCoefficient = "درس مهارتی متوسطه اول",
                    targetForZeroStudent = "هدف قطعی: تسلط بر مهارت‌های تفکر و سبک زندگی",
                    color = Color(0xFF0D9488),
                    zeroReassurance = "تفکر و سبک زندگی را با تمرین و مثال‌های روزمره، از صفر یاد می‌گیری!"
                )
    )

    /**
     * Explicit fallback for ids with no catalog entry (e.g. "all", typos
     * in stored data). It is deliberately NOT a real subject: it must never be
     * rendered as a subject's lessons (previously every unknown id silently
     * rendered as Math, the first entry). Callers display its title/color as-is;
     * none of them can produce a valid lesson list for it, which is the honest
     * outcome for an unknown id.
     */
    val unknownSubject = SubjectMeta(
        id = "unknown",
        shortTitle = "رشته ناشناخته",
        title = "رشته ناشناخته (شناسه نامعتبر)",
        subtitle = "شناسه این رشته در فهرست دروس برنامه وجود ندارد.",
        konkoorCoefficient = "—",
        targetForZeroStudent = "—",
        color = Color(0xFF64748B),
        zeroReassurance = "این رشته در برنامه تعریف نشده است؛ لطفاً از فهرست دروس یک رشته معتبر انتخاب کنید."
    )

    fun getSubjectById(id: String): SubjectMeta {
        val mappedId = when (id) {
            "history_geo" -> "history"
            "social_psych_econ" -> "sociology"
            else -> id
        }
        return subjects.find { it.id == mappedId } ?: unknownSubject
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
    "found1406-math-02-multiplication-fractions",
    "found1406-math-03-division",
    "found1406-math-04-negative-numbers",
    "found1406-math-05-fractions-1",
    "found1406-math-06-fractions-2",
    "found1406-math-07-fractions-3",
    "found1406-math-08-decimals-1",
    "found1406-math-09-decimals-2",
    "found1406-math-10-ratio-proportion",
    "found1406-math-11-percent-1",
    "found1406-math-12-percent-2",
    "found1406-math-13-powers-roots",
    "found1406-math-14-algebra-1",
    "found1406-math-15-equations-1",
    "found1406-math-16-geometry-1",
    "found1406-math-17-geometry-2",
    "found1406-math-18-geometry-3",
    "found1406-math-19-coordinates",
    "found1406-math-20-rational-irrational",
    "found1406-math-21-statistics-1",
    "found1406-math-22-statistics-2",
    "found1406-math-23-probability-1",
    "found1406-math-24-sets" -> 1 to "📘 پایه اول ابتدایی تا نهم — مبانی محاسبات، مجموعه اعداد و کسرها"
    "math0-01-counting-place-value",
    "math0-02-multiplication-table",
    "math0-03-order-of-operations" -> 1 to "📘 پایه اول ابتدایی تا نهم — مبانی محاسبات، مجموعه اعداد و کسرها"

    "math_02_equations",
    "math_03_radicals_powers",
    "math_04_functions_domain",
    "math_06_statistics_dispersion",
    "math_09_statistics_percentiles",
    "math_10_ch4_data_display",
    "math_03_functions" -> 2 to "📗 پایه دهم انسانی (چاپ ۱۴۰۳ ویژه کنکور ۱۴۰۶) — کتاب ریاضی و آمار ۱"
    "math0-04-inverse-function" -> 2 to "📗 پایه دهم انسانی (چاپ ۱۴۰۳ ویژه کنکور ۱۴۰۶) — کتاب ریاضی و آمار ۱"

    "math_08_logic_propositions",
    "math_11_grade11_functions_exponential",
    "math_07_grade12_regression_time_series",
    "math_05_logic_stats_prob",
    "math_05c_grade11_functions_indices" -> 3 to "📙 پایه یازدهم انسانی (چاپ ۱۴۰۴ ویژه کنکور ۱۴۰۶) — کتاب ریاضی و آمار ۲"

    "math_07_combinatorics_probability",
    "math_10_conditional_probability",
    "math_05_sequences_arithmetic",
    "math_05d_sequences_arithmetic_deep",
    "math_05e_sequences_geometric_deep",
    "math_12_konkoor_80_percent_shortcuts",
    "math_04_sequences",
    "math_05b_counting_permutations_combinations",
    "math_06_grade12_normal_conditional_probability",
    "math_ultra_fast_techniques",
    "math-gap-12-exponential-radical-equations",
    "math-gap-12-variance-applied",
    "site-sub-01" -> 4 to "📕 پایه دوازدهم انسانی (چاپ ۱۴۰۵ ویژه کنکور ۱۴۰۶) — کتاب ریاضی و آمار ۳"
    "math0-05-probability-rules" -> 4 to "📕 پایه دوازدهم انسانی (چاپ ۱۴۰۵ ویژه کنکور ۱۴۰۶) — کتاب ریاضی و آمار ۳"
    "elem1-math-01-ch1-numbers",
    "elem1-math-02-ch2-patterns",
    "elem1-math-03-ch3-add-subtract",
    "elem1-math-04-ch4-measurement",
    "elem1-math-05-ch5-geometry",
    "elem1-math-06-ch6-numbers-20",
    "elem1-math-07-ch7-add-subtract-20",
    "elem1-math-08-ch8-problem-solving",
    "elem1-math-09-ch9-geometry-2",
    "elem1-math-10-ch10-numbers-100",
    "elem1-math-11-ch11-add-subtract-100" -> 5 to "📘 پایه اول دبستان — ریاضی (۱۱ فصل)"
    "elem2-math-01-adad-ragham",
    "elem2-math-02-jam-tafrigh-doraghami",
    "elem2-math-03-ashkal-handasi",
    "elem2-math-04-adad-se-raghami",
    "elem2-math-05-andazegiri",
    "elem2-math-06-jam-tafrigh-se-raghami",
    "elem2-math-07-kasr-ehtemal",
    "elem2-math-08-amar-nemudar" -> 6 to "📗 پایه دوم دبستان — ریاضی (۸ فصل)"
    "elem4-math-01-adad-olguha",
    "elem4-math-02-kasr",
    "elem4-math-03-zarb-taghsim",
    "elem4-math-04-andazegiri",
    "elem4-math-05-adad-makhlout-ashari",
    "elem4-math-06-shekl-hendesi",
    "elem4-math-07-amar-ehtemal" -> 8 to "📗 پایه چهارم دبستان — ریاضی (۷ فصل)"
    "elem3-math-01-olguha",
    "elem3-math-02-adadhaye-chahar-raghami",
    "elem3-math-03-adadhaye-kesri",
    "elem3-math-04-zarb-taqsim",
    "elem3-math-05-mohit-masahat",
    "elem3-math-06-jam-tafriq",
    "elem3-math-07-amar-ehtemal",
    "elem3-math-08-zarb-adadha" -> 7 to "📗 پایه سوم دبستان — ریاضی (۸ فصل)"
    "mid9-math-01-sets",
    "mid9-math-02-real-numbers",
    "mid9-math-03-reasoning-proof-geometry",
    "mid9-math-04-powers-roots",
    "mid9-math-05-algebraic-expressions",
    "mid9-math-06-linear-equations",
    "mid9-math-07-rational-expressions",
    "mid9-math-08-volume-area" -> 13 to "📗 پایه نهم متوسطه اول — ریاضی (۸ فصل)"
    "mid8-math-01-integers-rationals",
    "mid8-math-02-prime-numbers",
    "mid8-math-03-polygons",
    "mid8-math-04-algebra-equation",
    "mid8-math-05-vector-coordinate",
    "mid8-math-06-triangle",
    "mid8-math-07-power-root",
    "mid8-math-08-stats-probability",
    "mid8-math-09-circle" -> 12 to "📗 پایه هشتم متوسطه اول — ریاضی (۹ فصل)"
    "mid7-math-01-problem-strategies",
    "mid7-math-02-integers",
    "mid7-math-03-algebra-equation",
    "mid7-math-04-geometry-reasoning",
    "mid7-math-05-divisors-primes",
    "mid7-math-06-surface-volume",
    "mid7-math-07-power-root",
    "mid7-math-08-vector-coordinate",
    "mid7-math-09-stats-probability" -> 11 to "📗 پایه هفتم متوسطه اول — ریاضی (۹ فصل)"
    "elem6-math-01-adad-va-olgou",
    "elem6-math-02-kasr",
    "elem6-math-03-adad-ashari",
    "elem6-math-04-tagharin-va-mokhtasat",
    "elem6-math-05-andazehgiri",
    "elem6-math-06-tanasob-va-darsad",
    "elem6-math-07-amar-va-ehtemal" -> 10 to "📗 پایه ششم دبستان — ریاضی (۷ فصل)"
    "elem5-math-01-adadnavisi-olgouha",
    "elem5-math-02-kasr",
    "elem5-math-03-nesbat-tanasob-darsad",
    "elem5-math-04-tagharon-chandzaleiha",
    "elem5-math-05-aadad-ashari",
    "elem5-math-06-andazegiri",
    "elem5-math-07-amar-va-ehtemal" -> 9 to "📗 پایه پنجم دبستان — ریاضی (۷ فصل)"

    // 2. Arabic (عربی، زبان قرآن ۱، ۲ و ۳ اختصاصی انسانی + پایه هفتم تا نهم)
    "arabic_step01_alphabet_diacritics_nouns",
    "arabic_0000_absolute_zero_7th_to_12th_khodemoni",
    "arabic_01_word_types_pronouns",
    "arabic_step03_complete_14_sigha_tables",
    "arabic_00_sigha_hand_code",
    "arabic_00_seventh_grade_sigha",
    "arabic_000_bismillah_mastery",
    "found1406-ar-01-alphabet-reading",
    "found1406-ar-02-sigha-what-is",
    "found1406-ar-03-pronouns",
    "found1406-ar-04-demonstratives",
    "found1406-ar-05-gender-number",
    "found1406-ar-06-numbers",
    "found1406-ar-07-past-tense",
    "found1406-ar-08-present-tense",
    "found1406-ar-09-imperative",
    "found1406-ar-10-nominal-sentence",
    "found1406-ar-11-verbal-sentence",
    "found1406-ar-12-translation",
    "found1406-ar-13-reading-text",
    "ar-zero-01",
    "ar-zero-02",
    "ar-zero-03",
    "ar-zero-04",
    "ar-zero-05",
    "ar-zero-06",
    "ar-zero-07",
    "ar-zero-08",
    "ar-zero-09",
    "arabic_01b_numbers_time_nouns" -> 1 to "📘 پایه هفتم تا نهم (از زیرِ صفر به زبان خودمونی) — حرکات، اسم و فعل، ضمایر و ۱۴ صیغه"
    "ar0-01",
    "ar0-02",
    "ar0-03",
    "ar0-04",
    "ar0-05" -> 1 to "📘 پایه هفتم تا نهم (از زیرِ صفر به زبان خودمونی) — حرکات، اسم و فعل، ضمایر و ۱۴ صیغه"

    "arabic_01_sigha_fingerprint",
    "arabic_step06_all_8_abwab_master_table",
    "arabic_02_verbs_abwab",
    "arabic_02_feal_bab_majhool",
    "ar1406-01-word-types-sigha",
    "ar1406-02-past-present-tenses",
    "ar1406-03-mujarrad-mazeed-1",
    "ar1406-04-mazeed-2",
    "ar1406-05-lesson5-nominal-sentence",
    "ar1406-06-lesson5-verbal-sentence",
    "ar-gap-majhul-madi",
    "ar-gap-majhul-mudare",
    "ar-gap-naeb-fael",
    "gap-ar-01" -> 2 to "📗 پایه دهم انسانی (چاپ ۱۴۰۳ ویژه کنکور ۱۴۰۶) — کتاب عربی، زبان قرآن ۱"

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
    "ar-tip-01",
    "ar-tip-02",
    "ar-tip-03",
    "ar-tip-04",
    "ar-tip-05",
    "ar-tip-06",
    "ar12-04-lesson4-5-monada-mafoul-ialal",
    "ar12-05-lesson5-monada",
    "site-sub-02" -> 4 to "📕 پایه دوازدهم انسانی (چاپ ۱۴۰۵ ویژه کنکور ۱۴۰۶) — کتاب عربی، زبان قرآن ۳ و جمع‌بندی ۱۰۰٪"
    "mid9-arabic-01-review",
    "mid9-arabic-02-safe-crossing",
    "mid9-arabic-03-friendship-bridge",
    "mid9-arabic-04-patience-key",
    "mid9-arabic-05-hope",
    "mid9-arabic-06-life-change",
    "mid9-arabic-07-fruit-of-effort",
    "mid9-arabic-08-taxi-dialogue",
    "mid9-arabic-09-health-texts",
    "mid9-arabic-10-soleimani-letter" -> 13 to "📗 پایه نهم متوسطه اول — عربی (۱۰ درس)"
    "mid8-arabic-01-review-7",
    "mid8-arabic-02-importance-arabic",
    "mid8-arabic-03-future-job",
    "mid8-arabic-04-new-experience",
    "mid8-arabic-05-friendship",
    "mid8-arabic-06-travel",
    "mid8-arabic-07-earth-wide",
    "mid8-arabic-08-self-reliance",
    "mid8-arabic-09-science-trip",
    "mid8-arabic-10-wisdoms" -> 12 to "📗 پایه هشتم متوسطه اول — عربی (۱۰ درس)"
    "mid7-arabic-01-alphabet",
    "mid7-arabic-02-greetings",
    "mid7-arabic-03-language",
    "mid7-arabic-04-gender",
    "mid7-arabic-05-dual",
    "mid7-arabic-06-plural-demonstrative",
    "mid7-arabic-07-hal-hamza",
    "mid7-arabic-08-man",
    "mid7-arabic-09-ma",
    "mid7-arabic-10-ayna",
    "mid7-arabic-11-kam-numbers",
    "mid7-arabic-12-daily-conversation",
    "mid7-arabic-13-past-first-second-singular",
    "mid7-arabic-14-mother-son-third-singular",
    "mid7-arabic-15-first-person-plural-phone",
    "mid7-arabic-16-second-person-plural-village",
    "mid7-arabic-17-arbaeen-story-third-plural" -> 11 to "📗 پایه هفتم متوسطه اول — عربی (۱۷ درس)"

    // 3. English (Prospect 1-3 & Vision 1, 2, 3)
    "english_000_zero_dictionary_grammar_words",
    "english_step01_pronouns_tobe_sentence_table",
    "english_01_sentence_zero",
    "english_step03_all_tenses_step_by_step_table",
    "english_00_seventh_grade_train",
    "english_00_seventh_grade_abc",
    "english_000_bismillah_mastery",
    "found1406-en-01-alphabet-sounds",
    "found1406-en-02-first-words",
    "found1406-en-03-vocab-1",
    "found1406-en-04-vocab-2",
    "found1406-en-05-tobe",
    "found1406-en-06-have",
    "found1406-en-07-vocab-3",
    "found1406-en-08-vocab-4",
    "found1406-en-09-vocab-5",
    "found1406-en-10-present-simple",
    "found1406-en-11-vocab-6",
    "found1406-en-12-vocab-7",
    "found1406-en-13-past-simple",
    "found1406-en-14-reading",
    "found1406-en-15-intonation",
    "found1406-en-16-vocab-8",
    "found1406-en-17-future-compare",
    "eng-zero-01",
    "eng-zero-02",
    "eng-zero-03",
    "eng-zero-04",
    "eng-zero-05",
    "eng-zero-06" -> 1 to "📘 پایه هفتم تا نهم (از زیرِ صفر) — معنی کلمات گرامری (Verb، Subject...) و جمله‌سازی پایه"
    "en0-present-continuous",
    "en0-present-perfect",
    "en0-articles",
    "en0-modals",
    "en0-irregular-verbs" -> 1 to "📘 پایه هفتم تا نهم (از زیرِ صفر) — معنی کلمات گرامری (Verb، Subject...) و جمله‌سازی پایه"

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

        "mid9-english-01-personality",
    "mid9-english-02-travel",
    "mid9-english-03-festivals",
    "mid9-english-04-services",
    "mid9-english-05-media",
    "mid9-english-06-health" -> 13 to "📗 پایه نهم متوسطه اول — زبان انگلیسی (Prospect 3، ۶ درس)"
    "mid8-english-01-nationality",
    "mid8-english-02-my-week",
    "mid8-english-03-my-abilities",
    "mid8-english-04-my-health",
    "mid8-english-05-my-city",
    "mid8-english-06-my-village",
    "mid8-english-07-my-hobbies" -> 12 to "📗 پایه هشتم متوسطه اول — زبان انگلیسی (Prospect 2، ۷ درس)"
    "mid7-english-01-my-name",
    "mid7-english-02-my-classmates",
    "mid7-english-03-my-age",
    "mid7-english-04-my-family",
    "mid7-english-05-appearance",
    "mid7-english-06-house",
    "mid7-english-07-address",
    "mid7-english-08-favorite-food" -> 11 to "📗 پایه هفتم متوسطه اول — زبان انگلیسی (Prospect 1، ۸ درس)"
// 4. Arooz & Prosody (موسیقی شعر و قافیه — علوم و فنون ۱، ۲ و ۳)
    "arozero-01-heja-clap",
    "arozero-02-taghtee",
    "arozero-03-rokn-rhythm",
    "arozero-04-hazaj",
    "arozero-05-rajaz",
    "arozero-06-ramal",
    "arozero-07-mozare",
    "arozero-08-mojtass",
    "arozero-09-motaqareb",
    "arozero-10-kamel",
    "arozero-11-monsareh",
    "arozero-12-dowri",
    "arozero-13-licenses",
    "arozero-14-ghafieh",
    "arozero-15-ghafieh-defects",
    "arooz_00_clapping_zero",
    "arooz_01_syllables_zero",
    "arooz_02_cutting_u_dash",
    "aroozm1406-01-rhythm-clap",
    "aroozm1406-02-taghtee",
    "aroozm1406-03-poetry-hook" -> 1 to "📘 مبانی پایه و علوم و فنون ۱ (چاپ ۱۴۰۳) — بخش کردن کلمات، هجای کوتاه و بلند و خط عروضی"

    "arooz_04_ghafieh_rules",
    "arooz_02_ghafieh_radif",
    "arooz_10_ch1_syllables_and_script",
        "arooz0-khafif",
    "arooz0-radif",
    "arooz0-ghafieh-letters" -> 1 to "📘 مبانی پایه و علوم و فنون ۱ (چاپ ۱۴۰۳) — بخش کردن کلمات، هجای کوتاه و بلند و خط عروضی"

"arooz_10_ch2_ghafieh_tabsareha" -> 2 to "📗 پایه دهم انسانی (چاپ ۱۴۰۳ ویژه کنکور ۱۴۰۶) — علوم و فنون ادبی ۱ (خط عروضی، هجا، قافیه و ردیف)"

    "arooz_00_bahr_barcode",
    "arooz_03_samaei_rhythms",
    "arooz_03_meters_samaei",
    "arooz_11_lesson3_vocal_bases",
    "arooz_11_lesson6_hamsan_meters",
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
    "arooz12-04-ghafieh-advanced",
    "arooz-quick-01",
    "arooz-quick-02",
    "arooz-quick-03",
    "arooz-quick-04",
    "arooz-quick-05",
    "arooz-quick-06" -> 4 to "📕 پایه دوازدهم انسانی (چاپ ۱۴۰۵ ویژه کنکور ۱۴۰۶) — علوم و فنون ادبی ۳ (اوزان دوری، ۱۶ وزن و اختیارات شاعری)"

    // 5. Fonoon (زیبایی‌شناسی، تاریخ ادبیات و سبک‌شناسی — علوم و فنون ۱، ۲ و ۳)
    "fonoon_00_zero_to_100_all_24_arrayeh_step_by_step" -> 1 to "📘 پیش‌نیاز از منفی صفر — آموزش پله‌به‌پله تمام ۲۴ آرایه ادبی از ساده‌ترین تا پیشرفته‌ترین"

    "fonoon_10_ch1_history_khorasani",
    "fonoon_10_ch2_badi_tarsee_movazeneh",
    "fonoon_10_ch3_tashbih_majaz_10",
    "fonoon_10_lesson10_sade5_6",
    "fonoon_01b_verbal_figures_saj" -> 2 to "📗 پایه دهم انسانی (چاپ ۱۴۰۳ ویژه کنکور ۱۴۰۶) — علوم و فنون ادبی ۱ کامل (درس ۱ تا ۱۲)"
    "fon0-jinas" -> 2 to "📗 پایه دهم انسانی (چاپ ۱۴۰۳ ویژه کنکور ۱۴۰۶) — علوم و فنون ادبی ۱ کامل (درس ۱ تا ۱۲)"

    "fonoon_11_ch1_history_eraqi",
    "fonoon_11_ch2_tashbih_types",
    "fonoon_11_ch3_estearah_majaz_kenayeh",
    "fonoon_01_four_pillars",
    "fonoon_01_bayani_figures" -> 3 to "📙 پایه یازدهم انسانی (چاپ ۱۴۰۴ ویژه کنکور ۱۴۰۶) — علوم و فنون ادبی ۲ کامل (درس ۱ تا ۱۲)"
    "fon0-tashkhis" -> 3 to "📙 پایه یازدهم انسانی (چاپ ۱۴۰۴ ویژه کنکور ۱۴۰۶) — علوم و فنون ادبی ۲ کامل (درس ۱ تا ۱۲)"

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
    "fon0-tamsil" -> 4 to "📕 پایه دوازدهم انسانی (چاپ ۱۴۰۵ ویژه کنکور ۱۴۰۶) — علوم و فنون ۳ + کل تاریخ ادبیات و سبک‌شناسی دهم تا دوازدهم"

    // 6. Logic (منطق دهم چاپ ۱۴۰۳)
    "found1406-logic-01-think",
    "found1406-logic-02-word-meaning",
    "found1406-logic-03-concept",
    "found1406-logic-04-definition",
    "found1406-logic-05-proposition",
    "found1406-logic-06-reasoning",
    "found1406-logic-07-syllogism",
    "found1406-logic-08-induction-analogy",
    "found1406-logic-09-fallacies",
    "found1406-logic-10-workshop" -> 1 to "📘 پایه دهم (از زیرِ صفر) — تفکر، مفهوم و مصداق، تعریف، قضیه، استدلال و مغالطه"
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
    "logic_phil_complete_mastery",
    "logic-tip-01",
    "logic1406-05-esteghra-tamsil" -> 2 to "📗 پایه دهم انسانی (چاپ ۱۴۰۳ ویژه کنکور ۱۴۰۶) — کتاب منطق کامل به زبان خودمونی (درس ۱ تا ۱۰)"

    // 7. Philosophy 1 & 2 (فلسفه ۱ یازدهم ۱۴۰۴ و فلسفه ۲ دوازدهم ۱۴۰۵)
    "phil_11_ch1_what_is_philosophy_greeks",
    "phil_11_ch2_epistemology_knowledge",
    "phil_11_ch3_islamic_philosophy_ethics",
    "phil_04_grade11_history_epistemology",
    "philo_04_grade11_history_epistemology",
    "phil1-03-falsafe-va-zendegi",
    "phil1-05-zendegi-bar-asas-andishe",
    "phil1-09-chisti-ensan-1",
    "phil1-10-chisti-ensan-2",
    "phil1-11-ensan-mowjud-akhlaqgara" -> 3 to "📙 پایه یازدهم انسانی (چاپ ۱۴۰۴ ویژه کنکور ۱۴۰۶) — کتاب فلسفه ۱ کامل (درس ۱ تا ۱۱)"

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
    "phil2-12-hekmat-moaser",
    "phil2-01-hasti-va-chisti",
    "phil2-03-jahan-elli-malooli",
    "phil2-05-khoda-dar-falsafe-1",
    "phil2-06-khoda-dar-falsafe-2",
    "phil2-07-details-aghl-dore-jadid",
    "phil-tip-01",
    "phil2-08-stages-roshd-aghl",
    "site-sub-03" -> 4 to "📕 پایه دوازدهم انسانی (چاپ ۱۴۰۵ ویژه کنکور ۱۴۰۶) — کتاب فلسفه ۲ کامل به زبان خودمونی (درس ۱ تا ۱۲)"

    // 8. History 1, 2, 3 (تاریخ ۱، ۲ و ۳)
    "hist_00_elementary_to_9th_zero",
    "found1406-soc-05-timeline",
    "found1406-soc-06-ancient-iran",
    "found1406-soc-07-islam-iran",
    "found1406-soc-08-modern-iran" -> 1 to "📘 پایه اول ابتدایی تا نهم (سطح زیرِ صفر) — محاسبه قرن‌ها، گاه‌شماری و الفبای تاریخ"
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
    "hist3-12",
    "site-sub-06" -> 4 to "📕 پایه دوازدهم انسانی (چاپ ۱۴۰۵ ویژه کنکور ۱۴۰۶) — کتاب تاریخ ۳ معاصر + جداول عهدنامه‌ها و سلسله‌ها"
    "histgeo0-06",
    "histgeo0-07" -> 4 to "📕 پایه دوازدهم انسانی (چاپ ۱۴۰۵ ویژه کنکور ۱۴۰۶) — کتاب تاریخ ۳ معاصر + جداول عهدنامه‌ها و سلسله‌ها"

    // 9. Geography 1, 2, 3 (جغرافیا ۱، ۲ و ۳)
    "geo_00_zero_maps_coordinates",
    "found1406-soc-01-maps",
    "found1406-soc-02-earth-motions",
    "found1406-soc-03-climate",
    "found1406-soc-04-population",
    "geo3-04-transport-management",
    "geo3-06-crisis-management",
    "geo3-skills-geo-techniques",
    "geo2-case2-islamic-world" -> 1 to "📘 از زیرِ صفر به زبان کوچه و بازار — حل چشمی مسائل ساعت، نصف‌النهار و مقیاس نقشه"
    "geo_01_grade10_1403",
    "geo_10_ch1_location_topography_climate_rivers",
    "geo_10_ch2_iran_population_settlements_economy",
    "geo_10_ch3_economy_transport_borders",
    "geo1-01-joghrafia-elmi-baraye-zendegi-behtar",
    "geo1-02-ravesh-motalee-va-pazhuhesh",
    "geo1-03-mogheiyat-joghrafiayi-iran",
    "geo1-04-nahamvarihaye-iran",
    "geo1-05-ab-va-havaye-iran",
    "geo1-06-manabe-ab-iran",
    "geo1-07-vizhegihaye-jamiyat-iran",
    "geo1-08-taqsimat-keshvari-iran",
    "geo1-09-sokonatgahhaye-iran",
    "geo1-10-tavanhaye-eqtesadi-iran" -> 2 to "📗 پایه دهم انسانی (چاپ ۱۴۰۳ ویژه کنکور ۱۴۰۶) — صفحه ۰ تا آخر کتاب جغرافیای ایران (دهم)"
    "geo_02_grade11_1404",
    "geo_11_ch1_regions_biomes_culture",
    "geo_11_ch2_economic_political_regions_seas",
    "geo_11_ch3_case_studies_sw_asia_europe",
    "geo2-01-mana-va-mafhum-nahiye",
    "geo2-02-ensan-va-nahiye",
    "geo2-03-navahi-ab-va-havayi",
    "geo2-04-nahamvariha-va-ashkal-zamin",
    "geo2-05-navahi-zisti",
    "geo2-06-navahi-farhangi",
    "geo2-07-navahi-eqtesadi-keshavarzi-va-sanati",
    "geo2-08-navahi-eqtesadi-tejarat-va-eqtesad-jahani",
    "geo2-09-mana-va-mafhum-nahiye-siyasi",
    "geo2-10-keshvar-yek-nahiye-siyasi",
    "geo2-11-geopolitik" -> 3 to "📙 پایه یازدهم انسانی (چاپ ۱۴۰۴ ویژه کنکور ۱۴۰۶) — صفحه ۰ تا آخر کتاب جغرافیا ۲ (ناحیه‌ای جهان)"
    "geo_03_grade12_1405",
    "geo_04_iran_climate_rivers_gis",
    "geo_12_ch1_applied_geography_hazards",
    "geo_12_ch2_climate_spatial_planning_gis",
    "geo1406-01-settlements",
    "geo1406-02-city-rural-management",
    "geo1406-03-transport",
    "geo1406-04-natural-hazards",
    "site-sub-10" -> 4 to "📕 پایه دوازدهم انسانی (چاپ ۱۴۰۵ ویژه کنکور ۱۴۰۶) — صفحه ۰ تا آخر کتاب جغرافیا ۳ (کاربردی)"

    // 10. Sociology 1, 2, 3 (جامعه‌شناسی ۱، ۲ و ۳)
    "soc_000_khodemoni_zero_to_100",
    "soc_00_elementary_to_9th_zero",
    "found1406-soc-09-rights-law",
    "soc-tip-03",
    "found1406-soc-10-culture-identity" -> 1 to "📘 از زیرِ صفر به زبان کوچه و بازار — ترجمه تمام کلمات قلمبه‌سلمبه جامعه‌شناسی ۱، ۲ و ۳"
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
    "soc1-16",
    "soc-gap-soc1-06-weber-iron-cage",
    "soc-gap-soc1-11-cultural-transformation-chain",
    "soc-gap-soc1-15-aristotle-six-systems",
    "soc-gap-soc1-thinkers-chapter-openings" -> 2 to "📗 پایه دهم انسانی (چاپ ۱۴۰۳ ویژه کنکور ۱۴۰۶) — صفحه ۰ تا آخر کتاب جامعه‌شناسی ۱ (درس ۱ تا ۱۶)"
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
    "soc2-15",
    "soc-gap-soc2-06-jean-calvin",
    "soc-gap-soc2-10-cuban-crisis-iran-famine",
    "soc-gap-soc2-14-social-revolution-jihad-treatises",
    "soc-gap-soc2-15-cultural-geography-palestine" -> 3 to "📙 پایه یازدهم انسانی (چاپ ۱۴۰۴ ویژه کنکور ۱۴۰۶) — صفحه ۰ تا آخر کتاب جامعه‌شناسی ۲ (درس ۱ تا ۱۵)"
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
    "soc3-10",
    "soc-tip-01",
    "soc-tip-02",
    "soc-gap-soc3-06-personality-persuasive-power",
    "site-sub-05" -> 4 to "📕 پایه دوازدهم انسانی (چاپ ۱۴۰۵ ویژه کنکور ۱۴۰۶) — صفحه ۰ تا آخر کتاب جامعه‌شناسی ۳ و ۳ رویکرد کنکور"

    // 11. Psychology 11 (روان‌شناسی یازدهم چاپ ۱۴۰۴)
    "psych_00_zero_khodemoni" -> 1 to "📘 از زیرِ صفر به زبان کوچه و بازار — ترجمه تمام کلمات سخت روان‌شناسی به زبان خودمونی"
    "psych_11_ch1_schools_and_development",
    "psych_01_grade11_1404_complete",
    "psych_02_sensation_memory_forgetting",
    "psych_03_perception_attention_gestalt",
    "psych_04_memory_forgetting_strategies",
    "psych_11_ch3_thinking_problem_solving",
    "psych_11_ch4_stress_mental_health",
    "psych1406-erikson-stages",
    "psych1406-07-negaresh",
    "psych-tip-01",
    "psych1406-08-sabk-zendegi-salem" -> 3 to "📙 پایه یازدهم انسانی (چاپ ۱۴۰۴ ویژه کنکور ۱۴۰۶) — صفحه ۰ تا آخر کتاب روان‌شناسی یازدهم (درس ۱ تا ۸ کامل)"

    // 12. Economics 10 (اقتصاد دهم چاپ ۱۴۰۳)
    "econ_02_math_problems_shortcuts_100",
    "econ_full_math_mastery",
    "economics_speed_shortcuts_master",
    "econ_05_tax_gini_distribution",
    "eco-quick-01",
    "eco-quick-02",
    "eco-quick-03",
    "eco-quick-04",
    "eco-quick-05",
    "eco-quick-06" -> 1 to "📘 شگردهای ۵ ثانیه‌ای و بدون فرمول برای حل تمام ۱۰ تیپ مسائل ریاضی اقتصاد کنکور"
    "econ_01_grade10_1403_complete",
    "econ_04_market_equilibrium_curves",
    "econ_10_ch2_market_supply_demand",
    "econ_03_gdp_inflation_unemployment_budget",
    "econ_10_ch4_trade_exchange_resilience",
    "gdp1406-01-what-is-gdp",
    "gdp1406-02-gdp-calculation-nominal-real",
    "eco1406-01-what-is-economics",
    "eco1406-02-opportunity-cost-ppf-deciles",
    "eco1406-03-money-functions-types",
    "eco1406-04-banking-inflation-unemployment",
    "found1406-soc-11-economy-basics",
    "eco-new-01-kasbokar-karafarini",
    "eco-new-02-entekhab-noe-kasbokar",
    "eco-new-03-osul-entekhab-dorost",
    "eco-new-04-marz-emkanat-tolid",
    "eco-new-05-bazar",
    "eco-new-06-naghsh-dolat",
    "eco-new-07-tejarat-beynolmelal",
    "eco-new-08-rokud-bikari-faqr",
    "eco-new-09-tavarom",
    "eco-new-10-moqavemsazi",
    "eco-new-11-roshd-pishraft",
    "eco-new-12-budjebandi",
    "eco-new-13-tasmimgiri-makharej",
    "eco-new-14-pasandaz-sarmayegozari" -> 2 to "📗 پایه دهم انسانی (چاپ ۱۴۰۳ ویژه کنکور ۱۴۰۶) — صفحه ۰ تا آخر کتاب اقتصاد دهم (۴ فصل و ۱۴ درس — کد 110221)"
    "eco0-01-keshesh-taghaza-arze",
    "eco0-02-pul-bank-markazi",
    "eco0-03-shakhes-tosee-ensani-hdi",
    "eco0-04-narkh-arz-taraz-pardakht",
    "eco0-05-budje-dolat-kasri-budje" -> 2 to "📗 پایه دهم انسانی (چاپ ۱۴۰۳ ویژه کنکور ۱۴۰۶) — صفحه ۰ تا آخر کتاب اقتصاد دهم (۴ فصل و ۱۴ درس — کد 110221)"

    // 13. Din o Zendegi 1, 2, 3 (دین و زندگی ۱، ۲ و ۳)
    "dini_10_ch1_purpose_creation_theology",
    "dini_10_ch2_death_barzakh_hereafter",
    "dini_10_ch3_friendship_ethics_society",
    "dini_01_grade10_1403_complete",
    "dini1-01-hadaf-zendegi",
    "dini1-02-qodrat-parvaz",
    "dini1-03-be-sooye-javedanegi",
    "dini1-04-ayandeye-rowshan",
    "dini1-05-manzelgahe-baad",
    "dini1-06-vagheye-bozorg",
    "dini1-07-farjam-kar",
    "dini1-08-ahang-safar",
    "dini1-09-etemad-bar-oo",
    "dini1-10-yari-az-do-yar-aziz",
    "dini1-11-fazilat-arastegi",
    "dini1-12-payam-pooshidegi" -> 2 to "📗 پایه دهم انسانی (چاپ دهم ۱۴۰۴ ویژه کنکور ۱۴۰۶) — صفحه ۰ تا آخر دین و زندگی ۱ (درس ۱ تا ۱۲)"
    "dini2-01-hedayat-elahi",
    "dini2-02-tadavom-hedayat",
    "dini2-03-akharin-payambar",
    "dini2-04-mojeze-javidan",
    "dini2-05-masouliyat-payambar",
    "dini2-06-pishvaye-osve",
    "dini2-07-emamat-tadavom-resalat",
    "dini2-08-jan-va-janeshin-payambar",
    "dini2-09-vaziyat-mosalamanan-pas-az-rehlat",
    "dini2-10-ehyaye-arzeshhaye-rastin",
    "dini2-11-jahad-dar-rastaye-velayat-zaheri",
    "dini2-12-asr-gheybat-emam-zaman",
    "dini2-13-dar-entezar-zohour",
    "dini2-14-marjaeyat-va-velayat-faqih",
    "dini2-15-rahbari-va-mardom",
    "dini2-16-ezzat-nafs",
    "dini2-17-zaminehaye-peyvand",
    "dini2-18-peyvand-moqaddas",
    "dini3-01-hasti-bakhsh",
    "dini3-02-yegane-bi-hamta",
    "dini3-03-towhid-va-sabk-zendegi",
    "dini3-04-faghat-baraye-to",
    "dini3-05-qodrat-parvaz",
    "dini3-06-sonnathaye-khodavand-dar-zendegi",
    "dini3-07-dar-gero-kar-khish",
    "dini3-08-bazgasht",
    "dini3-09-zendegi-dar-donyaye-emruz-va-amal-be-ahkam-elahi",
    "dini3-10-payehaye-ostovar",
    "dini3-11-asr-shokoufai",
    "dini3-12-negahi-be-tamadon-jadid",
    "dini3-13-masouliyat-bozorg-ma",
    "dini_11_ch1_divine_guidance_quran_prophet",
    "dini_11_ch2_imamate_ghadir_velayat",
    "dini_11_ch3_imams_life_ghaybat_velayat",
    "dini_02_grade11_1404_complete" -> 3 to "📙 پایه یازدهم انسانی (چاپ نهم ۱۴۰۴ ویژه کنکور ۱۴۰۶) — صفحه ۰ تا آخر دین و زندگی ۲ انسانی (درس ۱ تا ۱۸)"
    "dini_01_tawheed_degrees",
    "dini_03_grade12_1405_complete",
    "dini_04_divine_laws_sunan_barzakh",
    "far_05_dini_farhangian_ensani",
    "far_08_dini_complete_10_11_12_1406",

    // 14. Farsi 1, 2, 3 (فارسی ۱، ۲ و ۳)
    "far_09_farsi_grammar_1_to_12_1406",
    "found1406-farsi-01-alphabet-reading",
    "found1406-farsi-02-sentence-grammar",
    "found1406-farsi-03-reading-signs",
    "found1406-farsi-04-spelling-1",
    "found1406-farsi-05-spelling-2",
    "found1406-farsi-06-adjective-ezafe",
    "found1406-farsi-07-pronoun-adverb",
    "found1406-farsi-08-sentence-types",
    "found1406-farsi-09-comprehension-1",
    "found1406-farsi-10-comprehension-2",
    "found1406-farsi-11-descriptive-essay" -> 1 to "📘 از کلاس اول ابتدایی تا دوازدهم — دستور زبان فارسی، نقش‌ها، گروه اسمی و ساختمان واژه"
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
        "far0-01",
    "far0-02",
    "far0-03",
    "far0-04",
    "far0-05",
    "far0-06",
    "far0-07",
    "far0-08",
    "far0-09",
    "far0-10",
    "far0-11",
    "far0-12",
    "far0-13",
    "far0-14",
    "far0-15",
    "far0-16",
    "far0-17",
    "far0-18" -> 1 to "📘 از کلاس اول ابتدایی تا دوازدهم — دستور زبان فارسی، نقش‌ها، گروه اسمی و ساختمان واژه"

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
    "far1406-07-haghighate-eshgh",
    "far3-08-az-pariz-ta-paris",
    "far3-09-kavir",
    "far3-10-fasl-shokufayi",
    "far3-11-an-shab-aziz",
    "far3-12-gozar-siyavash-az-atash",
    "far3-13-khan-hashtom",
    "far3-14-si-morgh-va-simorgh",
    "far3-16-kabab-ghaz",
    "far3-17-khande-to",
    "far3-18-eshgh-javdani",
    "site-sub-04" -> 4 to "📕 پایه دوازدهم انسانی (چاپ ۱۴۰۵ ویژه کنکور و نهایی) — فارسی ۳ و فنون زبانی پیشرفته"
    "elem1-farsi-01-negareh-1",
    "elem1-farsi-02-negareh-2",
    "elem1-farsi-03-neshane-01",
    "elem1-farsi-04-neshane-02",
    "elem1-farsi-05-neshane-03",
    "elem1-farsi-06-neshane-04",
    "elem1-farsi-07-neshane-05",
    "elem1-farsi-08-neshane-06",
    "elem1-farsi-09-neshane-07",
    "elem1-farsi-10-neshane-08",
    "elem1-farsi-11-neshane-09",
    "elem1-farsi-12-neshane-10",
    "elem1-farsi-13-neshane-11",
    "elem1-farsi-14-neshane-12",
    "elem1-farsi-15-neshane-13",
    "elem1-farsi-16-neshane-14",
    "elem1-farsi-17-neshane-15",
    "elem1-farsi-18-neshane-16",
    "elem1-farsi-19-neshane-17",
    "elem1-farsi-20-neshane-18",
    "elem1-farsi-21-neshane-19",
    "elem1-farsi-22-neshane-20",
    "elem1-farsi-23-neshane-21",
    "elem1-farsi-24-neshane-22",
    "elem1-farsi-25-ravan-khani" -> 5 to "📘 پایه اول دبستان — فارسی (نگاره‌ها، نشانه‌ها و روان‌خوانی، ۲۵ درس)"
    "elem2-farsi-01-ketabkhaneh-kelas",
    "elem2-farsi-02-masjed-mahalleh",
    "elem2-farsi-03-khers-koochooloo",
    "elem2-farsi-04-madrese-khargooshha",
    "elem2-farsi-05-choopan-dorostkar",
    "elem2-farsi-06-koosha-va-noosha",
    "elem2-farsi-07-doostan-ma",
    "elem2-farsi-08-az-hame-mehraban-tar",
    "elem2-farsi-09-ziyarat",
    "elem2-farsi-10-honarmand",
    "elem2-farsi-11-dars-azad",
    "elem2-farsi-12-ferdowsi",
    "elem2-farsi-13-iran-ziba",
    "elem2-farsi-14-parcham",
    "elem2-farsi-15-nowruz",
    "elem2-farsi-16-parvaz-ghatreh",
    "elem2-farsi-17-mesl-daneshmandan" -> 6 to "📗 پایه دوم دبستان — فارسی (خوانداری، ۱۷ درس)"
    "elem4-farsi-01-afaridegar-zibayi",
    "elem4-farsi-02-kouch-parastouha",
    "elem4-farsi-03-raz-neshaneha",
    "elem4-farsi-04-arzesh-elm",
    "elem4-farsi-05-rahaee-az-ghafas",
    "elem4-farsi-06-arash-kamangir",
    "elem4-farsi-07-mehman-shahr-ma",
    "elem4-farsi-08-dars-azad-1",
    "elem4-farsi-09-dars-azad-2",
    "elem4-farsi-10-baghche-atfal",
    "elem4-farsi-11-farmande-delha",
    "elem4-farsi-12-ettefagh-sadeh",
    "elem4-farsi-13-lotf-hagh",
    "elem4-farsi-14-adab-az-ke-amoukhti",
    "elem4-farsi-15-shir-va-moush",
    "elem4-farsi-16-porseshgari",
    "elem4-farsi-17-madrese-houshmand" -> 8 to "📗 پایه چهارم دبستان — فارسی (خوانداری، ۱۷ درس)"
    "elem3-farsi-01-mahalle-ma",
    "elem3-farsi-02-zang-varzesh",
    "elem3-farsi-03-aseman-abi",
    "elem3-farsi-04-avaz-gonjeshk",
    "elem3-farsi-05-boladorchin",
    "elem3-farsi-06-fadakaran",
    "elem3-farsi-07-kar-nik",
    "elem3-farsi-08-pirahan-beheshti",
    "elem3-farsi-09-bu-narges",
    "elem3-farsi-10-yar-mehraban",
    "elem3-farsi-11-nevisande-bozorg",
    "elem3-farsi-12-iran-aziz",
    "elem3-farsi-13-dars-azad",
    "elem3-farsi-14-iran-abad",
    "elem3-farsi-15-darya",
    "elem3-farsi-16-agar-jangal-nabashad",
    "elem3-farsi-17-cheshmhaye-aseman" -> 7 to "📗 پایه سوم دبستان — فارسی (خوانداری، ۱۷ درس)"
    "mid9-farsi-01-afarinesh",
    "mid9-farsi-02-ajayeb",
    "mid9-farsi-03-ayeneh",
    "mid9-farsi-04-hamneshin",
    "mid9-farsi-05-dars-azad",
    "mid9-farsi-06-adab",
    "mid9-farsi-07-partow-omid",
    "mid9-farsi-08-mam-mihan",
    "mid9-farsi-09-raz-movaffaghiat",
    "mid9-farsi-10-arash",
    "mid9-farsi-11-zan-parsa",
    "mid9-farsi-12-payamavar-rahmat",
    "mid9-farsi-13-ashna-ghariban-milad-gol",
    "mid9-farsi-14-peyda-penhan",
    "mid9-farsi-15-dars-azad-adabiyat-bumi-2",
    "mid9-farsi-16-arezoo",
    "mid9-farsi-17-shazde-koochooloo" -> 13 to "📗 پایه نهم متوسطه اول — فارسی (خوانداری، ۱۷ درس)"
    "mid8-farsi-01-pish-az-inha",
    "mid8-farsi-02-khub-jahan-ra-bebin",
    "mid8-farsi-03-armaghan-iran",
    "mid8-farsi-04-safar-shekoftan",
    "mid8-farsi-05-dars-azad",
    "mid8-farsi-06-rah-nikbakhti",
    "mid8-farsi-07-adab-nikan",
    "mid8-farsi-08-azadegi",
    "mid8-farsi-09-nojavan-bahush",
    "mid8-farsi-10-qalam-sehramiz",
    "mid8-farsi-11-parchamdaran",
    "mid8-farsi-12-shir-hagh",
    "mid8-farsi-13-adabiat-enghelab",
    "mid8-farsi-14-yad-hossein",
    "mid8-farsi-15-dars-azad",
    "mid8-farsi-16-parande-azadi",
    "mid8-farsi-17-rah-khoshbakhti" -> 12 to "📗 پایه هشتم متوسطه اول — فارسی (خوانداری، ۱۷ درس)"
    "mid7-farsi-01-zang-afarinesh",
    "mid7-farsi-02-cheshme-marefat",
    "mid7-farsi-03-nasl-ayande-saz",
    "mid7-farsi-04-bahar-mi-resad",
    "mid7-farsi-05-dars-azad",
    "mid7-farsi-06-ghalb-kochak",
    "mid7-farsi-07-elm-zendegani",
    "mid7-farsi-08-zendegi-lahzeha",
    "mid7-farsi-09-nasihat-emam",
    "mid7-farsi-10-kelas-adabiyat",
    "mid7-farsi-11-ahd-peyman",
    "mid7-farsi-12-khadamat-moteghabel",
    "mid7-farsi-13-osveh-nikou",
    "mid7-farsi-14-imam-khomeini",
    "mid7-farsi-15-dars-azad",
    "mid7-farsi-16-adam-ahani-shaparak",
    "mid7-farsi-17-ma-mitavanim" -> 11 to "📗 پایه هفتم متوسطه اول — فارسی (خوانداری، ۱۷ درس)"
    "elem6-farsi-01-marifat-afaridgar",
    "elem6-farsi-02-panjerehaye-shenakht",
    "elem6-farsi-03-houshyari",
    "elem6-farsi-04-dastan-man-va-shoma",
    "elem6-farsi-05-haft-khan-rostam",
    "elem6-farsi-06-ey-vatan",
    "elem6-farsi-07-dars-azad-1",
    "elem6-farsi-08-daryagholi",
    "elem6-farsi-09-ranjhaei-keshideam",
    "elem6-farsi-10-attar-va-jalaleddin",
    "elem6-farsi-11-shohada-khorshidand",
    "elem6-farsi-12-dousti-va-mashverat",
    "elem6-farsi-13-dars-azad-2",
    "elem6-farsi-14-raz-zendegi",
    "elem6-farsi-15-miveh-honar",
    "elem6-farsi-16-adab-motalee",
    "elem6-farsi-17-setareh-roshan" -> 10 to "📗 پایه ششم دبستان — فارسی (خوانداری، ۱۷ درس)"
    "elem5-farsi-01-tamashakhaneh",
    "elem5-farsi-02-fazl-khoda",
    "elem5-farsi-03-razi-bimarestan",
    "elem5-farsi-04-bazargan-pesaran",
    "elem5-farsi-05-chenar-kadoban",
    "elem5-farsi-06-sorud-melli",
    "elem5-farsi-07-dars-azad-farhang-bumi",
    "elem5-farsi-08-defa-az-mihan",
    "elem5-farsi-09-namavaran-dirouz-emrouz-farda",
    "elem5-farsi-10-nam-niku",
    "elem5-farsi-11-naghshe-kheradmandan",
    "elem5-farsi-12-darse-azad-farhange-bumi-2",
    "elem5-farsi-13-roozi-ke-baran-mibarid",
    "elem5-farsi-14-shojaat",
    "elem5-farsi-15-kajestan",
    "elem5-farsi-16-vaghti-booali-koodak-bood",
    "elem5-farsi-17-kar-va-talash" -> 9 to "📗 پایه پنجم دبستان — فارسی (خوانداری، ۱۷ درس)"

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
    "elem2-negaresh-01-ketabkhaneh-kelas-ma",
    "elem2-negaresh-02-masjed-mahalleh-ma",
    "elem2-negaresh-03-khers-koochooloo",
    "elem2-negaresh-04-madrese-khargooshha",
    "elem2-negaresh-05-choopan-dorostkar",
    "elem2-negaresh-06-koosha-va-noosha",
    "elem2-negaresh-07-doustan-ma",
    "elem2-negaresh-08-az-hame-mehraban-tar",
    "elem2-negaresh-09-ziyarat",
    "elem2-negaresh-10-honarmand",
    "elem2-negaresh-11-dars-azad",
    "elem2-negaresh-12-ferdowsi",
    "elem2-negaresh-13-iran-ziba",
    "elem2-negaresh-14-parcham",
    "elem2-negaresh-15-norouz",
    "elem2-negaresh-16-parvaz-ghatre",
    "elem2-negaresh-17-mesl-daneshmandan" -> 6 to "📗 پایه دوم دبستان — نگارش (۱۷ درس)"
    "elem4-negaresh-01-afaridegar-zibayi",
    "elem4-negaresh-02-kouch-parastouha",
    "elem4-negaresh-03-raz-neshaneha",
    "elem4-negaresh-04-arzesh-elm",
    "elem4-negaresh-05-rahaee-az-ghafas",
    "elem4-negaresh-06-arash-kamangir",
    "elem4-negaresh-07-mehman-shahr-ma",
    "elem4-negaresh-08-dars-azad-1",
    "elem4-negaresh-09-dars-azad-2",
    "elem4-negaresh-10-baghche-atfal",
    "elem4-negaresh-11-farmande-delha",
    "elem4-negaresh-12-ettefagh-sadeh",
    "elem4-negaresh-13-lotf-hagh",
    "elem4-negaresh-14-adab-az-ke-amoukhti",
    "elem4-negaresh-15-shir-va-moush",
    "elem4-negaresh-16-porseshgari",
    "elem4-negaresh-17-madrese-houshmand" -> 8 to "📗 پایه چهارم دبستان — نگارش (۱۷ درس)"
    "elem3-negaresh-01-mahalle-ma",
    "elem3-negaresh-02-zang-varzesh",
    "elem3-negaresh-03-aseman-abi",
    "elem3-negaresh-04-avaz-gonjeshk",
    "elem3-negaresh-05-boladorchin",
    "elem3-negaresh-06-fadakaran",
    "elem3-negaresh-07-kar-nik",
    "elem3-negaresh-08-pirahan-beheshti",
    "elem3-negaresh-09-bu-narges",
    "elem3-negaresh-10-yar-mehraban",
    "elem3-negaresh-11-nevisande-bozorg",
    "elem3-negaresh-12-iran-aziz",
    "elem3-negaresh-13-dars-azad",
    "elem3-negaresh-14-iran-abad",
    "elem3-negaresh-15-darya",
    "elem3-negaresh-16-agar-jangal-nabashad",
    "elem3-negaresh-17-cheshmhaye-aseman" -> 7 to "📗 پایه سوم دبستان — نگارش (۱۷ درس)"
    "mid9-negaresh-01-zehn-nezammand",
    "mid9-negaresh-02-vazheha",
    "mid9-negaresh-03-noe-zaban",
    "mid9-negaresh-04-space-color",
    "mid9-negaresh-05-beginning-ending-title",
    "mid9-negaresh-06-choose-template",
    "mid9-negaresh-07-expand-depth",
    "mid9-negaresh-08-editing" -> 13 to "📗 پایه نهم متوسطه اول — نگارش (۸ درس)"
    "mid8-negaresh-01-naghshe-zehni",
    "mid8-negaresh-02-afkar-ghoftar",
    "mid8-negaresh-03-bebinim-benevisim",
    "mid8-negaresh-04-listen-write",
    "mid8-negaresh-05-senses-write",
    "mid8-negaresh-06-compare-write",
    "mid8-negaresh-07-substitute-write",
    "mid8-negaresh-08-different-write" -> 12 to "📗 پایه هشتم متوسطه اول — نگارش (۸ درس)"
    "mid7-negaresh-01-naghshe-neveshtan",
    "mid7-negaresh-02-bakhsh-haye-neveshte",
    "mid7-negaresh-03-tabaghebandi-mozoo",
    "mid7-negaresh-04-narrow-topic",
    "mid7-negaresh-05-body-paragraphs",
    "mid7-negaresh-06-topic-sentence",
    "mid7-negaresh-07-organize-mind",
    "mid7-negaresh-08-mental-topics" -> 11 to "📗 پایه هفتم متوسطه اول — نگارش (۸ درس)"
    "elem6-negaresh-01-marifat-afaridgar",
    "elem6-negaresh-02-panjerehaye-shenakht",
    "elem6-negaresh-03-houshyari",
    "elem6-negaresh-04-dastan-man-va-shoma",
    "elem6-negaresh-05-haft-khan-rostam",
    "elem6-negaresh-06-ey-vatan",
    "elem6-negaresh-07-dars-azad-1",
    "elem6-negaresh-08-daryagholi",
    "elem6-negaresh-09-ranjhaei-keshideam",
    "elem6-negaresh-10-attar-va-jalaleddin",
    "elem6-negaresh-11-shohada-khorshidand",
    "elem6-negaresh-12-dousti-va-mashverat",
    "elem6-negaresh-13-dars-azad-2",
    "elem6-negaresh-14-raz-zendegi",
    "elem6-negaresh-15-miveh-honar",
    "elem6-negaresh-16-adab-motalee",
    "elem6-negaresh-17-setareh-roshan" -> 10 to "📗 پایه ششم دبستان — نگارش (۱۷ درس)"
    "elem5-negaresh-01-tamashakhaneh",
    "elem5-negaresh-02-fazle-khoda",
    "elem5-negaresh-03-razi-va-sakhte-bimarestan",
    "elem5-negaresh-04-bazargan",
    "elem5-negaresh-05-chenar",
    "elem5-negaresh-06-sorud",
    "elem5-negaresh-07-azad1",
    "elem5-negaresh-08-defa",
    "elem5-negaresh-09-namavaran",
    "elem5-negaresh-10-namniko",
    "elem5-negaresh-11-kheradmandan",
    "elem5-negaresh-12-azad2",
    "elem5-negaresh-13-baran",
    "elem5-negaresh-14-shojaat",
    "elem5-negaresh-15-kajestan",
    "elem5-negaresh-16-bouali-koudak",
    "elem5-negaresh-17-kar-talaash" -> 9 to "📗 پایه پنجم دبستان — نگارش (۱۷ درس)"

    // 16. Farhangian Exclusive Resources (منابع اختصاصی فرهنگیان ۱۴۰۶)
    "far_01_roadmap_1406",
    "far_08_qaraati_teacher_methods_complete",
    "far_02_gharaati_talim",
    "farh1406-01-sanad-tahavol",
    "farh1406-02-talim-tarbiyat-eslami",
    "farh-src-01-roadmap-exam-1405",
    "farh-src-02-qaraati-ch1-value",
    "farh-src-03-qaraati-ch2-attributes",
    "farh-src-04-qaraati-ch3-duties",
    "farh-src-05-dini-correct-range" -> 3 to "📙 دفترچه اختصاصی فرهنگیان ۱۴۰۶ — مهارت‌های معلمی استاد قرائتی (فصل ۱ تا ۳) و سند تحول بنیادین"
    "far_03a_verbal_logical_math_intelligence_full",
    "far_03_teacher_intelligence",
    "far_09_teacher_aptitude_reasoning",
    "far_04_interview_humanities",
    "far_06_comprehensive_interview_scenarios",
    "far_07_konkoor_full_package_humanities_tables",
    "farhangian_04_mnemonics_final20",
    "persian_internet_konkoor_gems",
    "farh-src-06-housh-quantitative",
    "farh-src-07-housh-classification",
    "farh-src-08-kanoon-writing" -> 4 to "📕 هوش و استعداد معلمی + سوالات کانون ارزیابی و مصاحبه حضوری فرهنگیان ۱۴۰۶"

    // 17. Social Identity 12 (هویت اجتماعی دوازدهم چاپ ۱۴۰۵)
    "hoviyat_master_grade12_final20" -> 3 to "📙 بانک سوالات پرتکرار و جداول مقایسه‌ای هویت اجتماعی دوازدهم (نمره ۲۰ قطعی نهایی)"
    "hoviyat_01_action_phenomena",
    "hoviyat_02_society_culture",
    "hoviyat_03_reproduction_changes",
    "hoviyat_04_iranian_islamic_identity_globalization",
    "hum-tip-01",
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
    "res1406-01-media-message",
    "gap-res-01",
    "gap-res-02",
    "gap-res-03",
    "gap-res-04",
    "gap-res-05",
    "gap-res-06",
    "gap-res-07",
    "gap-res-08",
    "gap-res-09",
    "gap-res-10",
    "gap-res-11",
    "gap-res-12",
    "gap-res-13",
    "gap-res-14",
    "gap-res-15",
    "gap-res-16",
    "gap-res-17",
    "gap-res-18",
    "gap-res-19",
    "gap-res-20",
    "gap-res-21",
    "gap-kar-01",
    "gap-kar-02",
    "gap-kar-03",
    "gap-kar-04",
    "gap-kar-05",
    "gap-kar-06",
    "gap-kar-07",
    "gap-kar-08",
    "gap-kar-09",
    "gap-kar-10",
    "gap-kar-11",
    "gap-kar-12",
    "gap-kar-13",
    "gap-kar-14",
    "gap-kar-15",
    "gap-kar-16",
    "gap-kar-17",
    "gap-kar-18",
    "gap-kar-19",
    "gap-kar-20",
    "gap-kar-21",
    "gap-kar-22",
    "gap-kar-23",
    "gap-kar-24",
    "gap-kar-25",
    "gap-kar-26",
    "gap-kar-27",
    "gap-kar-28",
    "gap-kar-29",
    "gap-kar-30",
    "gap-kar-31",
    "gap-kar-32" -> 2 to "📗 پایه دهم انسانی (چاپ ۱۴۰۳) — تفکر و سواد رسانه‌ای دهم + کارگاه کارآفرینی و تولید دهم"
    "gen_03_mohit_zist_grade11_1404",
    "mohit_11_ch2_biodiversity_energy_pollution",
    "moh1406-01-water-air",
    "moh1406-02-soil" -> 3 to "📙 پایه یازدهم انسانی (چاپ ۱۴۰۴) — انسان و محیط زیست یازدهم (فصل ۱ تا ۷ کامل)"
    "family_12_management_lifestyle_complete",
    "far_10_remaining_humanities_books_10_11_12",
    "gap-famg-01",
    "gap-famg-02",
    "gap-famg-03",
    "gap-famg-04",
    "gap-famg-05",
    "gap-famg-06",
    "gap-famg-07",
    "gap-famg-08",
    "gap-famg-09",
    "gap-famg-10",
    "gap-fam-01",
    "gap-fam-02",
    "gap-fam-03",
    "gap-fam-04",
    "gap-fam-05",
    "gap-fam-06",
    "gap-fam-07",
    "gap-fam-08",
    "gap-fam-09",
    "gap-fam-10",
    "gap-fam-11",
    "gap-fam-12",
    "gap-fam-13",
    "gap-fam-14",
    "gap-fam-15",
    "gap-fam-16",
    "gap-fam-17",
    "gap-fam-18",
    "gap-fam-19",
    "gap-fam-20",
    "gap-fam-21",
    "gap-fam-22" -> 4 to "📕 پایه دوازدهم انسانی (چاپ ۱۴۰۵) — مدیریت خانواده و سبک زندگی ۱۲ و جمع‌بندی دروس مهارتی"

    // 20. Scientific Learning Methods & Speed Reading (روش‌های نوین یادگیری و تست‌زنی)
    "study_01_feynman_active_recall_leitner",
    "study_04_feynman_technique_deep_mastery",
    "study_05_daily_weekly_planning",
    "study_06_sleep_focus_phone",
    "methods1406-01-sq3r",
    "methods1406-02-memory-palace",
    "methods1406-03-interleaving",
    "methods1406-04-arabic-study",
    "methods1406-05-active-study",
    "methods1406-08-pomodoro",
    "methods1406-09-summary-mindmap",
    "sm-tip-02",
    "sm-tip-03",
    "sm-tip-04",
    "sm-tip-05",
    "methods1406-10-sleep-energy" -> 1 to "📘 روش‌های علمی یادگیری جهانی — تکنیک فاینمن، بازیابی فعال (Active Recall) و مرور فاصله‌دار لایتنر"
    "study_02_cornell_mindmap_speed_reading",
    "sm-tip-01" -> 2 to "📗 خلاصه‌نویسی و تندخوانی — روش کرنل (Cornell)، نقشه ذهنی (Mind Map)، رمزگردانی دوگانه و تندخوانی"
    "study_03_konkoor_test_strategy_final20",
    "site-res-01",
    "site-res-02",
    "site-res-03",
    "site-goz-01",
    "site-goz-02",
    "site-goz-03",
    "site-goz-04",
    "site-kan-01",
    "site-kan-02",
    "site-kan-03",
    "site-kan-04",
    "site-kan-05",
    "site-kan-06",
    "site-tst-01",
    "site-tst-02",
    "site-top-01",
    "site-top-02" -> 4 to "📕 مهندسی جلسه کنکور و امتحان نهایی — تکنیک ضربدر و منها، زمان‌های نقصانی، رد گزینه و فرمول نمره ۲۰ نهایی"

    // 21. Elementary Grade 1 & 2 new subjects (علوم، هدیه‌های آسمانی، قرآن)
    "elem1-science-01-zang-olum",
    "elem1-science-02-salam-be-man",
    "elem1-science-03-salem-bash",
    "elem1-science-04-donyay-janevaran",
    "elem1-science-05-donyay-giyahan",
    "elem1-science-06-zamin-khane-por-ab",
    "elem1-science-07-zamin-khane-sangi",
    "elem1-science-08-che-mikhaham-besazam",
    "elem1-science-09-zamin-khane-khaki",
    "elem1-science-10-hava",
    "elem1-science-11-donyay-sard-garm",
    "elem1-science-12-az-khane-ta-madrese",
    "elem1-science-13-ahanroba",
    "elem1-science-14-az-gozashte-ta-ayande" -> 1 to "📘 پایه اول دبستان — علوم (۱۴ درس)"
    "elem1-quran-01-be-nam-khoda",
    "elem1-quran-02-nemathaye-khoda",
    "elem1-quran-03-khane-ma",
    "elem1-quran-04-quran-bekhanim",
    "elem1-quran-05-kudak-mosalman",
    "elem1-quran-06-madrese-ma",
    "elem1-quran-07-payambaran-khoda",
    "elem1-quran-08-dars-akhar" -> 1 to "📘 پایه اول دبستان — قرآن (۸ درس)"
    "elem2-science-01-zang-olum",
    "elem2-science-02-havay-salem",
    "elem2-science-03-gardesh-zamin-1",
    "elem2-science-04-gardesh-zamin-2",
    "elem2-science-05-payam-ramz-seda",
    "elem2-science-06-payam-ramz-nur",
    "elem2-science-07-agar-tamam-shavad",
    "elem2-science-08-besazim-lezzat",
    "elem2-science-09-سرگذشت-دانه",
    "elem2-science-10-درون-آشیانه‌ها",
    "elem2-science-11-من-رشد-می‌کنم",
    "elem2-science-12-برای-جشن-آماده-شویم",
    "elem2-science-13-بعد-از-جشن",
    "elem2-science-14-از-گذشته-تا-آینده" -> 2 to "📗 پایه دوم دبستان — علوم (۱۴ درس)"
    "elem4-science-01-zang-oloum",
    "elem4-science-02-makhloutha",
    "elem4-science-03-energy-niaz",
    "elem4-science-04-energy-elektriki",
    "elem4-science-05-garma-madde",
    "elem4-science-06-sangha",
    "elem4-science-07-ahanroba",
    "elem4-science-08-aseman-shab",
    "elem4-science-09-badan-1",
    "elem4-science-10-badan-2",
    "elem4-science-11-bimohreha",
    "elem4-science-12-gounagouni-giyahan",
    "elem4-science-13-zistgah" -> 4 to "📗 پایه چهارم دبستان — علوم (۱۳ درس)"
    "elem3-science-01-zang-olum",
    "elem3-science-02-khorakiha",
    "elem3-science-03-andazegiri-mavad",
    "elem3-science-04-mavad-atraf-ma",
    "elem3-science-05-ab-made-ba-arzesh",
    "elem3-science-06-zendegi-ma-va-ab",
    "elem3-science-07-nur-va-moshahede",
    "elem3-science-08-jostoju-besazim",
    "elem3-science-09-niru-1",
    "elem3-science-10-niru-2",
    "elem3-science-11-bekarid-bebinid",
    "elem3-science-12-har-kodam-jay-khod-1",
    "elem3-science-13-har-kodam-jay-khod-2",
    "elem3-science-14-az-gozashte-ta-ayande" -> 3 to "📗 پایه سوم دبستان — علوم (۱۴ درس)"
    "mid9-science-01-materials-life",
    "mid9-science-02-atoms-behavior",
    "mid9-science-03-better-environment",
    "mid9-science-04-motion",
    "mid9-science-05-force",
    "mid9-science-06-plate-tectonics",
    "mid9-science-07-earth-past",
    "mid9-science-08-pressure",
    "mid9-science-09-machines",
    "mid9-science-10-space",
    "mid9-science-11-biodiversity",
    "mid9-science-12-plants",
    "mid9-science-13-invertebrates",
    "mid9-science-14-vertebrates",
    "mid9-science-15-living-together" -> 9 to "📗 پایه نهم متوسطه اول — علوم (۱۵ فصل)"
    "mid8-science-01-mixture-separation",
    "mid8-science-02-chemical-changes",
    "mid8-science-03-inside-atom",
    "mid8-science-04-nervous-system",
    "mid8-science-05-sense-movement",
    "mid8-science-06-hormonal-regulation",
    "mid8-science-07-biotech",
    "mid8-science-08-reproduction",
    "mid8-science-09-electricity",
    "mid8-science-10-magnetism",
    "mid8-science-11-minerals",
    "mid8-science-12-rocks",
    "mid8-science-13-weathering",
    "mid8-science-14-light",
    "mid8-science-15-refraction" -> 8 to "📗 پایه هشتم متوسطه اول — علوم (۱۵ فصل)"
    "mid7-science-01-experience-thinking",
    "mid7-science-02-measurement-tools",
    "mid7-science-03-atoms-alphabet",
    "mid7-science-04-materials-around",
    "mid7-science-05-mine-to-home",
    "mid7-science-06-water-cycle-land",
    "mid7-science-07-groundwater",
    "mid7-science-08-energy-conversion",
    "mid7-science-09-energy-sources",
    "mid7-science-10-heat-saving",
    "mid7-science-11-cell",
    "mid7-science-12-health-table",
    "mid7-science-13-food-journey",
    "mid7-science-14-material-circulation",
    "mid7-science-15-exchange-environment" -> 7 to "📗 پایه هفتم متوسطه اول — علوم (۱۵ فصل)"
    "elem6-science-01-zang-oloum",
    "elem6-science-02-sargozasht-daftar-man",
    "elem6-science-03-karkhaneh-kaghazsazi",
    "elem6-science-04-safar-be-amagh-zamin",
    "elem6-science-05-zamin-pouya",
    "elem6-science-06-varzesh-va-niro-1",
    "elem6-science-07-varzesh-va-niro-2",
    "elem6-science-08-tarrahi-konim-va-besazim",
    "elem6-science-09-safar-energy",
    "elem6-science-10-kheili-kouchak-kheili-bozorg",
    "elem6-science-11-shegeftihaye-barg",
    "elem6-science-12-jangal-baraye-kist",
    "elem6-science-13-salem-bemanim",
    "elem6-science-14-az-gozashteh-ta-ayandeh" -> 6 to "📗 پایه ششم دبستان — علوم (۱۴ درس)"
    "elem5-science-01-zang-olum",
    "elem5-science-02-madeh-taghieer-mikonad",
    "elem5-science-03-rangin-kaman",
    "elem5-science-04-bargi-az-tarikh-zamin",
    "elem5-science-05-harekat-badan",
    "elem5-science-06-che-khabar-1",
    "elem5-science-07-che-khabar-2",
    "elem5-science-08-karha-asan-mishavad-1",
    "elem5-science-09-karha-asan-mishavad-2",
    "elem5-science-10-khak-ba-arzesh",
    "elem5-science-11-bekarid-va-bokhorid",
    "elem5-science-12-az-rishe-ta-barg" -> 5 to "📗 پایه پنجم دبستان — علوم (۱۲ درس)"
    "elem4-social_studies-01-hamsaye-ma",
    "elem4-social_studies-02-inja-mahalle-mast",
    "elem4-social_studies-03-kharid-foroush-mahalle",
    "elem4-social_studies-04-naghshe-mahalle",
    "elem4-social_studies-05-zendegi-shahr-rousta",
    "elem4-social_studies-06-jahat-joghrafiayi",
    "elem4-social_studies-07-joghrafidan-mohit",
    "elem4-social_studies-08-nakhostin-roustaha",
    "elem4-social_studies-09-nakhostin-shahrha",
    "elem4-social_studies-10-movarekhan-gozashte",
    "elem4-social_studies-11-hamedan-bastani",
    "elem4-social_studies-12-takht-jamshid-1",
    "elem4-social_studies-13-takht-jamshid-2",
    "elem4-social_studies-14-kermanshah-bastani",
    "elem4-social_studies-15-kouh-dasht",
    "elem4-social_studies-16-ab-hava",
    "elem4-social_studies-17-navahi-ab-havayi",
    "elem4-social_studies-18-poushesh-giyahi-janvari",
    "elem4-social_studies-19-mohit-zist-moraghebat",
    "elem4-social_studies-20-neshanehaye-melli",
    "elem4-social_studies-21-taghvim",
    "elem4-social_studies-22-rouzhaye-mohem" -> 4 to "📗 پایه چهارم دبستان — مطالعات اجتماعی (۲۲ درس)"
    "elem3-social-01-man-be-donya-amadam",
    "elem3-social-02-man-bozorgtar-shodeam",
    "elem3-social-03-aya-ma-mesl-ham-hastim",
    "elem3-social-04-aaza-khanevade",
    "elem3-social-05-khanevade-dust-daram",
    "elem3-social-06-taghyir-dar-khanevade",
    "elem3-social-07-az-bozorgtarha-ghadrdani",
    "elem3-social-08-chera-hamkari",
    "elem3-social-09-moghararat-khane-ma",
    "elem3-social-10-niyazhaye-khanevade",
    "elem3-social-11-manabe",
    "elem3-social-12-dorost-masraf-konim",
    "elem3-social-13-bazyaft",
    "elem3-social-14-khaneam-dust-daram",
    "elem3-social-15-khaneha-tafavot",
    "elem3-social-16-khane-shoma-che-shekli",
    "elem3-social-17-az-khane-mohafezat",
    "elem3-social-18-madrese-dustdashtani",
    "elem3-social-19-makanhaye-madrese",
    "elem3-social-20-khane-shoma-kojast",
    "elem3-social-21-jahathaye-asli",
    "elem3-social-22-post",
    "elem3-social-23-eimeni-kuche-khiaban" -> 3 to "📗 پایه سوم دبستان — مطالعات اجتماعی (۲۳ درس)"
    "mid9-social-01-earth-home",
    "mid9-social-02-earth-movements",
    "mid9-social-03-earth-face",
    "mid9-social-04-water-air",
    "mid9-social-05-biomes",
    "mid9-social-06-biomes-danger",
    "mid9-social-07-population",
    "mid9-social-08-inequality",
    "mid9-social-09-united-iran",
    "mid9-social-10-safavid-society",
    "mid9-social-11-independence",
    "mid9-social-12-progress",
    "mid9-social-13-constitutional",
    "mid9-social-14-pahlavi",
    "mid9-social-15-revolution",
    "mid9-social-16-after-revolution",
    "mid9-social-17-culture",
    "mid9-social-18-identity",
    "mid9-social-19-family-values",
    "mid9-social-20-family-calm",
    "mid9-social-21-government",
    "mid9-social-22-citizen-rights",
    "mid9-social-23-productivity",
    "mid9-social-24-economy-productivity" -> 9 to "📗 پایه نهم متوسطه اول — مطالعات اجتماعی (۲۴ درس)"
    "mid8-social-01-taavon-1",
    "mid8-social-02-taavon-2",
    "mid8-social-03-government-structure",
    "mid8-social-04-government-duties",
    "mid8-social-05-asib-haye-ejtemaei",
    "mid8-social-06-ghovveh-ghazaeih",
    "mid8-social-07-ertebat-va-resaneh",
    "mid8-social-08-resaneh-ha-dar-zendegi",
    "mid8-social-09-zohour-eslam",
    "mid8-social-10-az-rehlat-ta-karbala",
    "mid8-social-11-voroud-eslam-be-iran",
    "mid8-social-12-asr-talaei",
    "mid8-social-13-ghaznavian-seljoughian-kharazmshahian",
    "mid8-social-14-miras-farhangi-seljoughi",
    "mid8-social-15-changiz-timur-attack",
    "mid8-social-16-culture-over-sword",
    "mid8-social-17-asia-natural",
    "mid8-social-18-asia-human-economic",
    "mid8-social-19-southwest-asia",
    "mid8-social-20-iran-southwest-asia",
    "mid8-social-21-europe-natural-human",
    "mid8-social-22-africa-natural-human",
    "mid8-social-23-america",
    "mid8-social-24-australia-oceania" -> 8 to "📗 پایه هشتم متوسطه اول — مطالعات اجتماعی (۲۴ درس)"
    "mid7-social_studies-01-rights",
    "mid7-social_studies-02-responsibility",
    "mid7-social_studies-03-why-rules",
    "mid7-social_studies-04-legislation",
    "mid7-social_studies-05-empathy-disasters",
    "mid7-social_studies-06-insurance-disasters",
    "mid7-social_studies-07-production-distribution",
    "mid7-social_studies-08-consumption",
    "mid7-social_studies-09-where-live",
    "mid7-social_studies-10-iran-home",
    "mid7-social_studies-11-climate-diversity",
    "mid7-social_studies-12-habitat-protection",
    "mid7-social_studies-13-population",
    "mid7-social_studies-14-water-soil",
    "mid7-social_studies-15-what-is-tourism",
    "mid7-social_studies-16-tourism-attractions",
    "mid7-social_studies-17-miras-farhangi",
    "mid7-social_studies-18-qadimi-tarin-sokonatgah",
    "mid7-social_studies-19-aryaiha",
    "mid7-social_studies-20-empire-admin",
    "mid7-social_studies-21-owzae-ejtemaei",
    "mid7-social_studies-22-owzae-eqtesadi",
    "mid7-social_studies-23-aqaed-sabk-zendegi",
    "mid7-social_studies-24-danesh-honar" -> 7 to "📗 پایه هفتم متوسطه اول — مطالعات اجتماعی (۲۴ درس)"
    "elem6-social_studies-01-dousti",
    "elem6-social_studies-02-adab-dousti",
    "elem6-social_studies-03-tasmimgiri-chist",
    "elem6-social_studies-04-chegouneh-tasmim-begirim",
    "elem6-social_studies-05-avamel-keshavarzi",
    "elem6-social_studies-06-mahsoolat-keshavarzi",
    "elem6-social_studies-07-talay-siah",
    "elem6-social_studies-08-energy-behtar",
    "elem6-social_studies-09-pishraft-elmi-mosalamanan",
    "elem6-social_studies-10-avamel-gostaresh-oloum",
    "elem6-social_studies-11-esfahan-nesf-jahan",
    "elem6-social_studies-12-farhang-honar-safavieh",
    "elem6-social_studies-13-barnameh-rouzaneh",
    "elem6-social_studies-14-barnamehrizi-oghat-faraghat",
    "elem6-social_studies-15-anva-lebas",
    "elem6-social_studies-16-lebas-az-tolid-ta-masraf",
    "elem6-social_studies-17-vizhegihaye-daryahaye-iran",
    "elem6-social_studies-18-darya-nemat-khodavandi",
    "elem6-social_studies-19-hamsayegan-ma",
    "elem6-social_studies-20-motalee-moredi",
    "elem6-social_studies-21-esteamar-chist",
    "elem6-social_studies-22-mobarezeh-mardom-ba-esteamar",
    "elem6-social_studies-23-khorramshahr-dar-chang-doshman",
    "elem6-social_studies-24-khorramshahr-dar-daman-mihan" -> 6 to "📗 پایه ششم دبستان — مطالعات اجتماعی (۲۴ درس)"
    "elem5-social-01-ertebat-ba-digaran",
    "elem5-social-02-ehsasat-ma",
    "elem5-social-03-hamdeli-ba-digaran",
    "elem5-social-04-ozv-goruh",
    "elem5-social-05-jamiat-iran",
    "elem5-social-06-manabe-ab-iran",
    "elem5-social-07-navahi-sanati-iran",
    "elem5-social-08-rahha-va-hamlonaghl-1",
    "elem5-social-09-rahha-va-hamlonaghl-2",
    "elem5-social-10-keshvar-ma-chegone-edare-mishavad",
    "elem5-social-11-keshvarhaye-hamsaye-1",
    "elem5-social-12-keshvarhaye-hamsaye-2",
    "elem5-social-13-harekathaye-zamin",
    "elem5-social-14-zendegi-dar-navahi-mokhtalef-jahan",
    "elem5-social-15-bazgasht-az-safar-hajj",
    "elem5-social-16-madine-shahr-payambar",
    "elem5-social-17-safar-be-karbala-1",
    "elem5-social-18-safar-karbala-2",
    "elem5-social-19-hokoomat-iranian",
    "elem5-social-20-vaziran-kardan",
    "elem5-social-21-keshvargoshayan",
    "elem5-social-22-bazsazi" -> 5 to "📗 پایه پنجم دبستان — مطالعات اجتماعی (۲۲ درس)"
    "elem2-hedyeh-01-هدیه‌های-خدا",
    "elem2-hedyeh-02-پرندگان-چه-می‌گویند",
    "elem2-hedyeh-03-خاطره‌ی-ماه",
    "elem2-hedyeh-04-مهربان‌تر-از-مادر",
    "elem2-hedyeh-05-vozou",
    "elem2-hedyeh-06-payambaran",
    "elem2-hedyeh-07-mehman-kochak",
    "elem2-hedyeh-08-jashn-milad",
    "elem2-hedyeh-09-ahle-beyt",
    "elem2-hedyeh-10-khanevade-bakhshande",
    "elem2-hedyeh-11-namaz",
    "elem2-hedyeh-12-pedar-mehraban",
    "elem2-hedyeh-13-behtarin-doust",
    "elem2-hedyeh-14-doaye-baran",
    "elem2-hedyeh-15-bacheha-salam",
    "elem2-hedyeh-16-kiseye-sanjab",
    "elem2-hedyeh-17-vaghte-namaz",
    "elem2-hedyeh-18-khatereye-naneveshte",
    "elem2-hedyeh-19-jashne-bozorg",
    "elem2-hedyeh-20-dar-kenare-sofre" -> 2 to "📗 پایه دوم دبستان — هدیه‌های آسمانی (۲۰ درس)"
    "elem4-hedyeh-01-danei-nemikhast",
    "elem4-hedyeh-02-koudaki-bar-ab",
    "elem4-hedyeh-03-ma-be-masjed-miravim",
    "elem4-hedyeh-04-yek-namaz-dah-rokou",
    "elem4-hedyeh-05-sokhani-se-bar",
    "elem4-hedyeh-06-haram-dou-gonbad",
    "elem4-hedyeh-07-namaz-kouhestan",
    "elem4-hedyeh-08-didar-doust",
    "elem4-hedyeh-09-koudak-shoja",
    "elem4-hedyeh-10-roshantarin-shab",
    "elem4-hedyeh-11-lesson-11",
    "elem4-hedyeh-12-rouzi-baraye-bacheha",
    "elem4-hedyeh-13-khale-narges",
    "elem4-hedyeh-14-avalin-banou-mosalman",
    "elem4-hedyeh-15-yek-majeraye-ziba",
    "elem4-hedyeh-16-asb-talaee",
    "elem4-hedyeh-17-aghaye-bahari",
    "elem4-hedyeh-18-cheshman-hamishe-baz",
    "elem4-hedyeh-19-khodajoun-mamnounam" -> 4 to "📗 پایه چهارم دبستان — هدیه‌های آسمانی (۱۹ درس)"
    "elem3-hedyeh-01-astinhaye-khali",
    "elem3-hedyeh-02-ghorub-yek-ruz-bahari",
    "elem3-hedyeh-03-hamishe-ba-man",
    "elem3-hedyeh-04-dar-kakh-namrud",
    "elem3-hedyeh-05-ruz-dahom",
    "elem3-hedyeh-06-banuy-qahreman",
    "elem3-hedyeh-07-bu-behesht",
    "elem3-hedyeh-08-jashn-taklif",
    "elem3-hedyeh-09-goftogu-ba-khoda",
    "elem3-hedyeh-10-mah-mihmani-khoda",
    "elem3-hedyeh-11-eid-mosalamanan",
    "elem3-hedyeh-12-sokhan-asemani",
    "elem3-hedyeh-13-entekhab-parvane",
    "elem3-hedyeh-14-om-abiha",
    "elem3-hedyeh-15-hamsafar-nashenas",
    "elem3-hedyeh-16-danatarin-mardom",
    "elem3-hedyeh-17-khab-shirin",
    "elem3-hedyeh-18-ayene-sokhangu",
    "elem3-hedyeh-19-gandom-az-gandom",
    "elem3-hedyeh-20-bagh-hamishe-bahar" -> 3 to "📗 پایه سوم دبستان — هدیه‌های آسمانی (۲۰ درس)"
    "elem6-hedyeh-01-yekta",
    "elem6-hedyeh-02-behtarin-rahnamayan",
    "elem6-hedyeh-03-sarvar-azadegan",
    "elem6-hedyeh-04-bagh-serri",
    "elem6-hedyeh-05-shotarban-ba-iman",
    "elem6-hedyeh-06-mesl-forat-va-karun",
    "elem6-hedyeh-07-simaye-khouban",
    "elem6-hedyeh-08-dast-dar-dast-doust",
    "elem6-hedyeh-09-douran-gheibat",
    "elem6-hedyeh-10-jahan-digar",
    "elem6-hedyeh-11-adab-zendegi",
    "elem6-hedyeh-12-danesh-amouz-nemouneh",
    "elem6-hedyeh-13-safarhaye-ba-barakat",
    "elem6-hedyeh-14-eid-mosalamanan",
    "elem6-hedyeh-15-raz-movafaghiat",
    "elem6-hedyeh-16-hamaseh-afarinan-javdan",
    "elem6-hedyeh-17-ziyarat" -> 6 to "📗 پایه ششم دبستان — هدیه‌های آسمانی (۱۷ درس)"
    "elem5-hedyeh-01-daste-gol-aseman",
    "elem5-hedyeh-02-tanha-ou",
    "elem5-hedyeh-03-banou-sore",
    "elem5-hedyeh-04-az-nozad-beporsid",
    "elem5-hedyeh-05-gol-sad-barg",
    "elem5-hedyeh-06-mal-mardom",
    "elem5-hedyeh-07-ranginkaman-jome",
    "elem5-hedyeh-08-do-name",
    "elem5-hedyeh-09-yek-jahan-jashn",
    "elem5-hedyeh-10-dar-sahel-dajle",
    "elem5-hedyeh-11-sarv-sarboland-samara",
    "elem5-hedyeh-12-khorshid-posht-abr",
    "elem5-hedyeh-13-koochakha-ye-bozorg",
    "elem5-hedyeh-14-namayesh-vaghei",
    "elem5-hedyeh-15-in-dastan-ra-do-bar-bayad-khand",
    "elem5-hedyeh-16-rooznamehaye-divari",
    "elem5-hedyeh-17-inha-va-anha" -> 5 to "📗 پایه پنجم دبستان — هدیه‌های آسمانی (۱۷ درس)"
    "elem2-quran-01-alef-koochak-sokoon",
    "elem2-quran-02-tashdid-etessalat",
    "elem2-quran-03-sooreye-hamd",
    "elem2-quran-04-sooreye-nas",
    "elem2-quran-05-falaq-tanvin",
    "elem2-quran-06-towhid-he",
    "elem2-quran-07-nasr-hamze",
    "elem2-quran-08-kowsar-hamze-ye",
    "elem2-quran-09-asr-kar-khub",
    "elem2-quran-10-fil-maun",
    "elem2-quran-11-kafirun-char-qol",
    "elem2-quran-12-sharh-inshirah",
    "elem2-quran-13-tin-bot-shekan",
    "elem2-quran-14-qadr-bagh-atash",
    "elem2-quran-15-tabestan-sokhan-akhar" -> 2 to "📗 پایه دوم دبستان — قرآن (۱۵ درس)"
    "elem4-quran-01-yadavari-naba",
    "elem4-quran-02-taghabon-naba",
    "elem4-quran-03-ale-emran-abas",
    "elem4-quran-04-fosselat-abas",
    "elem4-quran-05-ale-emran-takvir",
    "elem4-quran-06",
    "elem4-quran-07",
    "elem4-quran-08",
    "elem4-quran-09",
    "elem4-quran-10",
    "elem4-quran-11",
    "elem4-quran-12",
    "elem4-quran-13",
    "elem4-quran-14",
    "elem4-quran-15-quran-dar-tabestan" -> 4 to "📗 پایه چهارم دبستان — قرآن (۱۵ درس)"
    "elem3-quran-01-tarkib-horuf-harakat",
    "elem3-quran-02-harf-saken",
    "elem3-quran-03-tanvin",
    "elem3-quran-04-tashdid",
    "elem3-quran-05-khandan-hu-hi",
    "elem3-quran-06-paye-hamze",
    "elem3-quran-07-horuf-nakhana-1",
    "elem3-quran-08-horuf-nakhana-2",
    "elem3-quran-09-horuf-nakhana-3",
    "elem3-quran-10-sure-fil",
    "elem3-quran-11-azan-eghame",
    "elem3-quran-12-sure-hamd",
    "elem3-quran-13-sure-tohid",
    "elem3-quran-14-azkar-namaz",
    "elem3-quran-15-aye-salavat",
    "elem3-quran-16-ons-01-hamd-baghare",
    "elem3-quran-17-ons-02-ayatalkorsi",
    "elem3-quran-18-ons-03-ebrahim-zamzam",
    "elem3-quran-19-ons-04-esra",
    "elem3-quran-20-ons-05-ebrahim",
    "elem3-quran-21-ons-06-balad",
    "elem3-quran-22-ons-07-taha",
    "elem3-quran-23-ons-08-taha",
    "elem3-quran-24-ons-09-ghadr-zelzal",
    "elem3-quran-25-ons-10-shoara",
    "elem3-quran-26-ons-11-gharee",
    "elem3-quran-27-ons-12-fajr",
    "elem3-quran-28-ons-13-adiyat",
    "elem3-quran-29-ons-14-alagh" -> 3 to "📗 پایه سوم دبستان — قرآن (۲۹ درس)"
    "elem6-quran-01-namaz-dar-quran",
    "elem6-quran-02-sureh-fath",
    "elem6-quran-03-sureh-hojorat-1",
    "elem6-quran-04-sureh-hojorat-2",
    "elem6-quran-05-sureh-qaf-zariyat-1",
    "elem6-quran-06-sureh-zariyat-tour",
    "elem6-quran-07-sureh-qamar-shaqq-al-qamar",
    "elem6-quran-08-sureh-rahman-mardan-parsi",
    "elem6-quran-09-sureh-vaqeah-ab-va-abadani",
    "elem6-quran-10-sureh-hadid-tavallodi-dobareh",
    "elem6-quran-11-sureh-hadid-mojadeleh-madar-felezat",
    "elem6-quran-12-sureh-mojadeleh-hezbollah-pirouz-ast",
    "elem6-quran-13-sureh-hashr-momtaheneh-khodaya-tasvirgar",
    "elem6-quran-14-sureh-saff-hame-tasbih-gouye-ou",
    "elem6-quran-15-quran-dar-tabestan" -> 6 to "📗 پایه ششم دبستان — قرآن (۱۵ درس)"
    "elem5-quran-01-yadavari",
    "elem5-quran-02-loghman-16-19",
    "elem5-quran-03-araf-142-143",
    "elem5-quran-04-baqareh-285-286",
    "elem5-quran-05-esra-34-38",
    "elem5-quran-06-yusuf-1-5",
    "elem5-quran-07-baqareh-259",
    "elem5-quran-08-ebrahim-24-27",
    "elem5-quran-09-nahal-90-92",
    "elem5-quran-10-hajr-1-9",
    "elem5-quran-11-zomar-73-75",
    "elem5-quran-12-forqan-63-67" -> 5 to "📗 پایه پنجم دبستان — قرآن (۱۲ درس)"
    "mid9-payam-01-know-god",
    "mid9-payam-02-faith-shelter",
    "mid9-payam-03-rahnamayan-elahi",
    "mid9-payam-04-khorshid-penhan",
    "mid9-payam-05-rahbari-gheybat",
    "mid9-payam-06-vozu-ghosl-tayammom",
    "mid9-payam-07-ahkam-namaz",
    "mid9-payam-08-hamdeli-hamrahi",
    "mid9-payam-09-enghelab-islami",
    "mid9-payam-10-masouliat-hamegani",
    "mid9-payam-11-enfagh",
    "mid9-payam-12-jahad" -> 9 to "📗 پایه نهم متوسطه اول — پیام‌های آسمان (۱۲ درس)"
    "mid8-payam-01-afarinesh",
    "mid8-payam-02-afv",
    "mid8-payam-03-hame-chiz",
    "mid8-payam-04-peyvand",
    "mid8-payam-05-rooz-eslam",
    "mid8-payam-06-nardeban",
    "mid8-payam-07-forsat",
    "mid8-payam-08-neshan",
    "mid8-payam-09-tadbir",
    "mid8-payam-10-do-sarmaye",
    "mid8-payam-11-afat-zaban",
    "mid8-payam-12-arzesh-kar",
    "mid8-payam-13-kelid-ganjha",
    "mid8-payam-14-ma-mosalamanan",
    "mid8-payam-15-haq-alnas" -> 8 to "📗 پایه هشتم متوسطه اول — پیام‌های آسمان (۱۵ درس)"
    "mid7-payam-01-binay-mehraban",
    "mid7-payam-02-esteanat",
    "mid7-payam-03-talkh-ya-shirin",
    "mid7-payam-04-obour-asan",
    "mid7-payam-05-payambar-rahmat",
    "mid7-payam-06-osveh-fedakari-adalat",
    "mid7-payam-07-bartarin-banu",
    "mid7-payam-08-eftekhar-bandegi",
    "mid7-payam-09-be-suye-paki",
    "mid7-payam-10-sotoun-din",
    "mid7-payam-11-namaz-jamaat",
    "mid7-payam-12-neshan-ezzat",
    "mid7-payam-13-bar-bal-fereshtegan",
    "mid7-payam-14-kamal-hamneshin",
    "mid7-payam-15-mazdoran-sheytan" -> 7 to "📗 پایه هفتم متوسطه اول — پیام‌های آسمان (۱۵ درس)"
    "mid9-karfan-01-algoritm",
    "mid9-karfan-02-tarsim-rayaneh",
    "mid9-karfan-03-sazokar-harekati",
    "mid9-karfan-04-python-3",
    "mid9-karfan-05-hedayat-tahsili",
    "mid9-karfan-06-bargh",
    "mid9-karfan-07-tasisat",
    "mid9-karfan-08-omran",
    "mid9-karfan-09-khodro",
    "mid9-karfan-10-koodak",
    "mid9-karfan-11-mes" -> 9 to "📗 پایه نهم متوسطه اول — کار و فناوری (۱۱ پودمان)"
    "mid8-karfan-01-shahrvand-1",
    "mid8-karfan-02-shahrvand-2",
    "mid8-karfan-03-python-2",
    "mid8-karfan-04-electronic",
    "mid8-karfan-05-metal",
    "mid8-karfan-06-handicraft",
    "mid8-karfan-07-animals",
    "mid8-karfan-08-admin-finance",
    "mid8-karfan-09-architecture" -> 8 to "📗 پایه هشتم متوسطه اول — کار و فناوری (۹ پودمان)"
    "mid7-karfan-01-noavari-fanavari",
    "mid7-karfan-02-karbord-fanavari-ettelaat",
    "mid7-karfan-03-jostoju-jamavari",
    "mid7-karfan-04-mostanadsazi",
    "mid7-karfan-05-eshterakgozari-ettelaat",
    "mid7-karfan-06-barnamenevisi",
    "mid7-karfan-07-kasb-kar",
    "mid7-karfan-08-naghshekeshi",
    "mid7-karfan-09-kar-ba-choob",
    "mid7-karfan-10-parvaresh-giyahan",
    "mid7-karfan-11-pooshak",
    "mid7-karfan-12-khorak" -> 7 to "📗 پایه هفتم متوسطه اول — کار و فناوری (۱۲ پودمان)"
    "mid9-defa-01-tahdid-doshman",
    "mid9-defa-02-mogheiyat-iran",
    "mid9-defa-03-revayat-fath",
    "mid9-defa-04-bayad-barkhast",
    "mid9-defa-05-royaruyi-ghodratha",
    "mid9-defa-06-nezam-jam",
    "mid9-defa-07-padafand",
    "mid9-defa-08-jang-penhan",
    "mid9-defa-09-emdad" -> 9 to "📗 پایه نهم متوسطه اول — آمادگی دفاعی (۹ درس)"
    "mid9-honar-01-tarahi-karbord",
    "mid9-honar-02-tarahi-ashya",
    "mid9-honar-03-tarkibbandi",
    "mid9-honar-04-negashtar-chist",
    "mid9-honar-05-payam-mokhatab",
    "mid9-honar-06-tarahi-negashtar",
    "mid9-honar-07-aks-tarkibi",
    "mid9-honar-08-aks-ravayi",
    "mid9-honar-09-gerayesh-aks",
    "mid9-honar-11-qalam-tarashidan",
    "mid9-honar-12-nastaliq-satr",
    "mid9-honar-13-shekaste-dostri",
    "mid9-honar-14-gerayesh-daftari",
    "mid9-honar-15-gereh-hasht",
    "mid9-honar-16-boteh",
    "mid9-honar-17-jeldsazi",
    "mid9-honar-18-jabeh-roghani",
    "mid9-honar-19-ashya-roghani",
    "mid9-honar-20-darvishduzi",
    "mid9-honar-21-zartoshtiduzi",
    "mid9-honar-22-patehduzi",
    "mid9-honar-23-avaha-qowmi",
    "mid9-honar-24-radif-dastgahi",
    "mid9-honar-25-avaha-dini",
    "mid9-honar-26-vazn-ritm",
    "mid9-honar-27-shekl-guneh",
    "mid9-honar-28-kargardani",
    "mid9-honar-29-bazigar-khanesh",
    "mid9-honar-30-harekat-chideman",
    "mid9-honar-10-chahar-lengeh" -> 9 to "📗 پایه نهم متوسطه اول — فرهنگ و هنر (۱۰ درس)"
    "mid8-honar-01-design-2d-3d",
    "mid8-honar-02-birds",
    "mid8-honar-03-architecture",
    "mid8-honar-04-ornaments",
    "mid8-honar-05-tasvirsazi-khat",
    "mid8-honar-06-tasvirsazi-sath",
    "mid8-honar-07-tasvirsazi-vizhegi",
    "mid8-honar-08-tasvirsazi-ravand",
    "mid8-honar-09-akkasi-baft",
    "mid8-honar-10-akkasi-qabbandi",
    "mid8-honar-11-akkasi-zaviyeh",
    "mid8-honar-13-abzar-khoshnevisi",
    "mid8-honar-14-khat-reyhan",
    "mid8-honar-15-khat-towqi",
    "mid8-honar-16-khat-reqa",
    "mid8-honar-17-khat-taliq",
    "mid8-honar-18-noghoosh-hendesi",
    "mid8-honar-19-tabl-dar-tabl",
    "mid8-honar-20-naghshe-mowj",
    "mid8-honar-21-bafte-parche",
    "mid8-honar-22-taropoodi",
    "mid8-honar-23-bafte-morakkab",
    "mid8-honar-24-chap-tabi",
    "mid8-honar-25-pichman",
    "mid8-honar-26-chap-stensil",
    "mid8-honar-27-vazn-ritm",
    "mid8-honar-28-melodi",
    "mid8-honar-29-rang-avayi",
    "mid8-honar-30-baft-avayi",
    "mid8-honar-31-form-avayi",
    "mid8-honar-32-bazi-bazigari",
    "mid8-honar-33-anasor-bazigari",
    "mid8-honar-34-badan-harekat",
    "mid8-honar-35-ehsas-takhayyol",
    "mid8-honar-12-akkasi-rang" -> 8 to "📗 پایه هشتم متوسطه اول — فرهنگ و هنر (۳۵ درس)"
    "mid7-honar-01-moshahede",
    "mid7-honar-02-tanavoe-tarhha",
    "mid7-honar-03-alefbaye-tasvir",
    "mid7-honar-04-nazm",
    "mid7-honar-05-tabiat",
    "mid7-honar-06-naghshe-golha",
    "mid7-honar-07-baft",
    "mid7-honar-08-naghashi-irani",
    "mid7-honar-09-tarahi-sonnati",
    "mid7-honar-10-vazn",
    "mid7-honar-11-melodi",
    "mid7-honar-12-rang-souti",
    "mid7-honar-13-shekl-form",
    "mid7-honar-14-baft",
    "mid7-honar-15-naghsh-hendesi",
    "mid7-honar-16-tarahi-gereh",
    "mid7-honar-17-zirandazha",
    "mid7-honar-18-abzar-gelim",
    "mid7-honar-19-baft-gelim",
    "mid7-honar-20-sofal",
    "mid7-honar-21-hajm-feshari",
    "mid7-honar-22-hajm-fatilei",
    "mid7-honar-23-manzarehyab",
    "mid7-honar-24-durbin-khodkar",
    "mid7-honar-25-noghte-takid",
    "mid7-honar-26-nur",
    "mid7-honar-27-khoshnevisi-abzar",
    "mid7-honar-28-khat-kufi",
    "mid7-honar-29-khat-naskh",
    "mid7-honar-30-khat-sols",
    "mid7-honar-31-khat-mohaghagh",
    "mid7-honar-32-goftegoo-konid",
    "mid7-honar-33-goftegoo-namayeshi",
    "mid7-honar-34-shakhsiyat-mogheiyat",
    "mid7-honar-35-majraha-namayeshi" -> 7 to "📗 پایه هفتم متوسطه اول — فرهنگ و هنر (۳۵ درس)"
    "mid8-tafakkor-01-beauty",
    "mid8-tafakkor-02-princess-painting",
    "mid8-tafakkor-03-project",
    "mid8-tafakkor-04-rah-rahaee",
    "mid8-tafakkor-05-tarhe-soal-pajouheshi",
    "mid8-tafakkor-06-tahiye-tarhe-pajouhesh",
    "mid8-tafakkor-07-barname-rizi-baraye-ejra",
    "mid8-tafakkor-08-adab-moasherat-goftegoo",
    "mid8-tafakkor-09-moasherat-majazi",
    "mid8-tafakkor-10-maharat-tasmim-giri",
    "mid8-tafakkor-11-khishtandari",
    "mid8-tafakkor-12-raftar-porkhatar",
    "mid8-tafakkor-13-div-o-keyk",
    "mid8-tafakkor-14-khat-keshi",
    "mid8-tafakkor-15-niki-dajle",
    "mid8-tafakkor-16-gozaresh-pazhuhesh",
    "mid8-tafakkor-17-erae-gozaresh",
    "mid8-tafakkor-18-vagheiyat",
    "mid8-tafakkor-19-adatha",
    "mid8-tafakkor-20-pedar-pesar",
    "mid8-tafakkor-21-takhayyol",
    "mid8-tafakkor-22-asar",
    "mid8-tafakkor-23-pesarak-anar",
    "mid8-tafakkor-24-depression",
    "mid8-tafakkor-25-care-elderly",
    "mid8-tafakkor-26-respect",
    "mid8-tafakkor-27-values",
    "mid8-tafakkor-28-travel",
    "mid8-tafakkor-30-maharat-zendegi-movaffaq",
    "mid8-tafakkor-31-ezzat-nafs",
    "mid8-tafakkor-32-khodpendare",
    "mid8-tafakkor-33-mahar-feshar-ravani",
    "mid8-tafakkor-34-maharat-eqtesadi",
    "mid8-tafakkor-29-self-assessment" -> 8 to "📗 پایه هشتم متوسطه اول — تفکر و سبک زندگی (۳۴ فعالیت)"
    "mid7-tafakkor-01-tafakkor-darbare-tafakkor",
    "mid7-tafakkor-02-sekke-tala",
    "mid7-tafakkor-03-boz-ya-sag",
    "mid7-tafakkor-04-prozhe-pazhooheshi",
    "mid7-tafakkor-05-tarh-soal-pazhoohesh",
    "mid7-tafakkor-06-tahiye-tarh-pazhoohesh",
    "mid7-tafakkor-07-barnamerizi-baraye-ejra",
    "mid7-tafakkor-08-man-kistam",
    "mid7-tafakkor-09-khodagahi",
    "mid7-tafakkor-10-motalee-va-yadgiri",
    "mid7-tafakkor-11-modiriyat-zaman",
    "mid7-tafakkor-12-tabavari",
    "mid7-tafakkor-13-resanei",
    "mid7-tafakkor-14-hekayat-tarbiyat",
    "mid7-tafakkor-15-raz-tadol",
    "mid7-tafakkor-16-sarab",
    "mid7-tafakkor-17-gozaresh-pazhuhesh",
    "mid7-tafakkor-18-communication-styles",
    "mid7-tafakkor-19-communication-skills",
    "mid7-tafakkor-20-family-neighbors-teachers",
    "mid7-tafakkor-21-choosing-friend",
    "mid7-tafakkor-22-managing-disagreement",
    "mid7-tafakkor-23-anger-management",
    "mid7-tafakkor-24-truth-story",
    "mid7-tafakkor-25-little-great-men",
    "mid7-tafakkor-26-why-phone-not-ringing",
    "mid7-tafakkor-27-glass-wall",
    "mid7-tafakkor-28-poisoning-source",
    "mid7-tafakkor-29-nazem-manzum",
    "mid7-tafakkor-30-qateiat",
    "mid7-tafakkor-31-sabk-zendegi",
    "mid7-tafakkor-32-shad-zistan",
    "mid7-tafakkor-33-ekhtiari-khodarzyabi" -> 7 to "📗 پایه هفتم متوسطه اول — تفکر و سبک زندگی (۳۳ فعالیت)"
    "elem6-tafakkor-00-vajehaye-tasadifi",
    "elem6-tafakkor-01-didani-haye-sarzamin-man",
    "elem6-tafakkor-02-margir-va-azhdaha",
    "elem6-tafakkor-03-booye-khosh-madine",
    "elem6-tafakkor-04-aghakand",
    "elem6-tafakkor-05-mah-boud-va-roubah",
    "elem6-tafakkor-06-narenji-poosh-amanatdar",
    "elem6-tafakkor-07-ravesh-gardavari-ettelaat",
    "elem6-tafakkor-08-tarh-porsesh-pazhuheshi",
    "elem6-tafakkor-09-mardom-pasandha",
    "elem6-tafakkor-10-zir-zarreh-bin",
    "elem6-tafakkor-11-az-koja-bedanam",
    "elem6-tafakkor-12-erae-ye-yafte-haye-pazhuheshi",
    "elem6-tafakkor-13-lili-hozak",
    "elem6-tafakkor-14-akhar-khat",
    "elem6-tafakkor-15-ma-koja-hastim",
    "elem6-tafakkor-16-yek-ettefagh-ajib",
    "elem6-tafakkor-17-peyvastegi",
    "elem6-tafakkor-18-palangha-va-gavaznha",
    "elem6-tafakkor-19-tappe-ye-kochak",
    "elem6-tafakkor-20-meymun-haye-geran-baha",
    "elem6-tafakkor-21-shekli-digar",
    "elem6-tafakkor-22-khersi-ke-mikhast-khers-baghi-bemanad",
    "elem6-tafakkor-23-parvaz-kon-parvaz",
    "elem6-tafakkor-24-man-aghebat-az-in-pol-khaham-gozasht",
    "elem6-tafakkor-25-kimiyagar" -> 6 to "📗 پایه ششم دبستان — تفکر و پژوهش (۲۶ فعالیت)"



    // تمرین‌های کتاب درسی — arabic (خودکفایی، پوش دوم)
    "ar-practice-01-arabi1-lesson8-jar-majroor",
    "ar-practice-02-arabi2-lesson2-ism-zaman-talmih",
    "ar-practice-03-arabi2-lesson4-marefeh-nakereh",
    "ar-practice-04-arabi2-lesson5-6-madi-huruf" -> 20 to "📝 تمرین‌های کتاب درسی (خودکفایی)"

    // تمرین‌های کتاب درسی — dini (خودکفایی، پوش دوم)
    "dini-practice-01-hadaf-zendegi",
    "dini-practice-02-be-sooye-javedanegi",
    "dini-practice-03-ahang-safar",
    "dini-practice-04-payehaye-ostovar",
    "dini-practice-05-masouliyat-bozorg-ma" -> 20 to "📝 تمرین‌های کتاب درسی (خودکفایی)"

    // تمرین‌های کتاب درسی — economics (خودکفایی، پوش دوم)
    "psych-econ-practice-01-eco03-tahlil" -> 20 to "📝 تمرین‌های کتاب درسی (خودکفایی)"

    // تمرین‌های کتاب درسی — english (خودکفایی، پوش دوم)
    "gen-practice-01-vision1-workbook",
    "gen-practice-02-vision2-understanding-people",
    "gen-practice-03-vision2-healthy-art",
    "gen-practice-04-prospect3-festivals-services",
    "gen-practice-05-prospect3-health-injuries" -> 20 to "📝 تمرین‌های کتاب درسی (خودکفایی)"

    // تمرین‌های کتاب درسی — farsi (خودکفایی، پوش دوم)
    "far-practice-01-fasl1-taleemi",
    "far-practice-02-fasl2-paydari",
    "far-practice-03-fasl3-ghanaei",
    "far-practice-04-fasl4-safar-zendegi",
    "far-practice-05-fasl5-enghelab",
    "far-practice-06-fasl6-hamasi",
    "far-practice-07-fasl7-dastani",
    "far-practice-08-fasl8-jahan",
    "far-practice-09-fasl1-cheshmeh",
    "far-practice-10-fasl2-haghighat",
    "far-practice-11-fasl3-mehr",
    "far-practice-12-fasl4-basreh",
    "far-practice-13-fasl5-daryadelan",
    "far-practice-14-fasl6-rostam",
    "far-practice-15-fasl7-tooti",
    "far-practice-16-fasl8-sepideh",
    "far-practice-17-fasl1-niki",
    "far-practice-18-fasl2-sand",
    "far-practice-19-fasl3-eshgh",
    "far-practice-20-fasl4-kooy",
    "far-practice-21-fasl5-jaras",
    "far-practice-22-fasl6-kaveh",
    "far-practice-23-fasl7-kabootar",
    "far-practice-24-fasl8-darya" -> 20 to "📝 تمرین‌های کتاب درسی (خودکفایی)"

    // تمرین‌های کتاب درسی — fonoon (خودکفایی، پوش دوم)
    "fon-practice-01-fon1-f1",
    "fon-practice-02-fon1-f2",
    "fon-practice-03-fon1-f3",
    "fon-practice-04-fon1-f4",
    "fon-practice-05-fon2-f1",
    "fon-practice-06-fon2-f2",
    "fon-practice-07-fon2-f3",
    "fon-practice-08-fon2-f4",
    "fon-practice-09-fon3-f1",
    "fon-practice-10-fon3-f2",
    "fon-practice-11-fon3-f3",
    "fon-practice-12-fon3-f4" -> 20 to "📝 تمرین‌های کتاب درسی (خودکفایی)"

    // تمرین‌های کتاب درسی — general_humanities (خودکفایی، پوش دوم)
    "sal-practice-01-nutrition",
    "sal-practice-02-adolescent-mental-health",
    "sal-practice-03-drugs-prevention" -> 20 to "📝 تمرین‌های کتاب درسی (خودکفایی)"

    // تمرین‌های کتاب درسی — geography (خودکفایی، پوش دوم)
    "geo-practice-01-geo1-lesson3",
    "geo-practice-02-geo1-lesson6",
    "geo-practice-03-geo1-lesson7",
    "geo-practice-04-geo1-lesson9",
    "geo-practice-05-geo1-lesson10",
    "geo-practice-06-geo3-lesson1",
    "geo-practice-07-geo3-lesson2",
    "geo-practice-08-geo3-lesson3",
    "geo-practice-09-geo3-lesson4" -> 20 to "📝 تمرین‌های کتاب درسی (خودکفایی)"

    // تمرین‌های کتاب درسی — history (خودکفایی، پوش دوم)
    "hist-practice-01-history3-lesson8-world-war2",
    "hist-practice-02-history1-lesson1-history-science",
    "hist-practice-03-history1-lesson2-time-place",
    "hist-practice-04-history1-lesson3-archaeology",
    "hist-practice-05-history1-lesson6-greece-rome",
    "hist-practice-06-history1-lesson8-dawn-iranian-civilization",
    "hist-practice-07-history1-lesson11-statecraft",
    "hist-practice-08-history2-lesson1-sources",
    "hist-practice-09-history2-lesson4-medina",
    "hist-practice-10-history2-lesson5-first-caliphs",
    "hist-practice-11-history2-lesson7-abbasid",
    "hist-practice-12-history2-lesson8-islam-in-iran",
    "hist-practice-13-history2-lesson10-ghaznavid-seljuk-khwarazmshahi",
    "hist-practice-14-history2-lesson11-mongol-timurid",
    "hist-practice-15-history2-lesson12-culture-mongol-timurid",
    "hist-practice-16-history2-lesson13-safavid" -> 20 to "📝 تمرین‌های کتاب درسی (خودکفایی)"

    // تمرین‌های کتاب درسی — logic (خودکفایی، پوش دوم)
    "phil-logic-practice-07-logic-dars1-tarazoo-andishe",
    "phil-logic-practice-08-logic-dars2-lafz-mana",
    "phil-logic-practice-09-logic-dars3-mafhum-mesdagh" -> 20 to "📝 تمرین‌های کتاب درسی (خودکفایی)"

    // تمرین‌های کتاب درسی — media_environment (خودکفایی، پوش دوم)
    "moh-practice-01-water",
    "moh-practice-02-soil-air",
    "moh-practice-03-energy-waste-biodiversity-tourism" -> 20 to "📝 تمرین‌های کتاب درسی (خودکفایی)"

    // تمرین‌های کتاب درسی — negaresh (خودکفایی، پوش دوم)
    "neg-practice-01-neg1-l1-l2",
    "neg-practice-02-neg1-l3-l4",
    "neg-practice-03-neg1-l5-l6",
    "neg-practice-04-neg1-l7-l8",
    "neg-practice-05-neg2-l1-l2",
    "neg-practice-06-neg2-l3-l4",
    "neg-practice-07-neg2-l5-l6",
    "neg-practice-08-neg3-l1-l2",
    "neg-practice-09-neg3-l3-l4",
    "neg-practice-10-neg3-l5-l6" -> 20 to "📝 تمرین‌های کتاب درسی (خودکفایی)"

    // تمرین‌های کتاب درسی — philosophy (خودکفایی، پوش دوم)
    "phil-logic-practice-01-phil1-dars1-chisti-falsafe",
    "phil-logic-practice-02-phil1-dars2-rishe-shakhe",
    "phil-logic-practice-03-phil1-dars4-aghaz-tarikhi",
    "phil-logic-practice-04-phil1-dars6-emkan-shenakht",
    "phil-logic-practice-05-phil1-dars7-abzar-shenakht",
    "phil-logic-practice-06-phil1-dars8-tarikhche-marefat" -> 20 to "📝 تمرین‌های کتاب درسی (خودکفایی)"
    // تمرین‌های کتاب درسی — پایه اول دبستان (خودکفایی فاز ۲)
    "elem1-practice-01-farsi-negareh1",
    "elem1-practice-02-farsi-negareh2",
    "elem1-practice-03-farsi-neshane01",
    "elem1-practice-04-farsi-neshane02",
    "elem1-practice-05-farsi-neshane03",
    "elem1-practice-06-farsi-neshane04",
    "elem1-practice-07-farsi-neshane05",
    "elem1-practice-08-farsi-ravankhani",
    "elem1-practice-09-math-ch1",
    "elem1-practice-10-math-ch2",
    "elem1-practice-11-math-ch3",
    "elem1-practice-12-math-ch4",
    "elem1-practice-13-math-ch5",
    "elem1-practice-14-math-ch6",
    "elem1-practice-15-math-ch7",
    "elem1-practice-16-math-ch8",
    "elem1-practice-17-math-ch9",
    "elem1-practice-18-math-ch10",
    "elem1-practice-19-math-ch11",
    "elem1-practice-20-science-l1",
    "elem1-practice-21-science-l2",
    "elem1-practice-22-science-l3",
    "elem1-practice-23-science-l4",
    "elem1-practice-24-science-l5",
    "elem1-practice-25-science-l6",
    "elem1-practice-26-science-l7",
    "elem1-practice-27-science-l8",
    "elem1-practice-28-science-l9",
    "elem1-practice-29-science-l10",
    "elem1-practice-30-science-l11",
    "elem1-practice-31-science-l12",
    "elem1-practice-32-science-l13",
    "elem1-practice-33-science-l14",
    "elem1-practice-34-quran-l1",
    "elem1-practice-35-quran-l2",
    "elem1-practice-36-quran-l3",
    "elem1-practice-37-quran-l4",
    "elem1-practice-38-quran-l5",
    "elem1-practice-39-quran-l6",
    "elem1-practice-40-quran-l7",
    "elem1-practice-41-quran-l8" -> 20 to "📝 تمرین‌های کتاب درسی (خودکفایی)"

    // تمرین‌های کتاب درسی — پایه دوم دبستان (خودکفایی فاز ۲)
    "elem2-practice-01-farsi-01-ketabkhaneh",
    "elem2-practice-02-farsi-03-khers-koochooloo",
    "elem2-practice-03-farsi-05-choopan",
    "elem2-practice-04-farsi-06-koosha-noosha",
    "elem2-practice-05-farsi-12-ferdowsi",
    "elem2-practice-06-negaresh-01-ketabkhaneh",
    "elem2-practice-07-negaresh-03-khers",
    "elem2-practice-08-negaresh-06-koosha-noosha",
    "elem2-practice-09-negaresh-12-ferdowsi",
    "elem2-practice-10-math-02-morour-fasl",
    "elem2-practice-11-math-04-morour-fasl",
    "elem2-practice-12-math-06-morour-fasl",
    "elem2-practice-13-math-07-morour-fasl",
    "elem2-practice-14-science-02-havay-salem",
    "elem2-practice-15-science-05-payam-ramz-seda",
    "elem2-practice-16-science-09-sargozasht-daneh",
    "elem2-practice-17-science-11-man-roshd-mikonam",
    "elem2-practice-18-hedyeh-01-hedyehaye-khoda",
    "elem2-practice-19-hedyeh-05-vozou",
    "elem2-practice-20-hedyeh-11-namaz",
    "elem2-practice-21-hedyeh-20-dar-kenare-sofre",
    "elem2-practice-22-quran-03-hamd",
    "elem2-practice-23-quran-11-kafirun",
    "elem2-practice-24-quran-12-sharh" -> 20 to "📝 تمرین‌های کتاب درسی (خودکفایی)"

    // تمرین‌های کتاب درسی — پایه سوم دبستان (خودکفایی فاز ۲)
    "elem3-practice-01-farsi-01-mahalle-ma",
    "elem3-practice-02-farsi-02-zang-varzesh",
    "elem3-practice-03-farsi-03-aseman-abi",
    "elem3-practice-04-farsi-04-avaz-gonjeshk",
    "elem3-practice-05-farsi-05-boladorchin",
    "elem3-practice-06-farsi-06-fadakaran",
    "elem3-practice-07-farsi-07-kar-nik",
    "elem3-practice-08-farsi-08-pirahan-beheshti",
    "elem3-practice-09-farsi-09-bu-narges",
    "elem3-practice-10-farsi-10-yar-mehraban",
    "elem3-practice-11-farsi-11-nevisande-bozorg",
    "elem3-practice-12-farsi-12-iran-aziz",
    "elem3-practice-13-farsi-13-dars-azad",
    "elem3-practice-14-farsi-14-iran-abad",
    "elem3-practice-15-farsi-15-darya",
    "elem3-practice-16-farsi-16-agar-jangal-nabashad",
    "elem3-practice-17-farsi-17-cheshmhaye-aseman",
    "elem3-practice-18-math-01-olguha",
    "elem3-practice-19-math-02-adadhaye-chahar-raghami",
    "elem3-practice-20-math-03-adadhaye-kesri",
    "elem3-practice-21-math-04-zarb-taqsim",
    "elem3-practice-22-math-05-mohit-masahat",
    "elem3-practice-23-math-06-jam-tafriq",
    "elem3-practice-24-math-07-amar-ehtemal",
    "elem3-practice-25-math-08-zarb-adadha",
    "elem3-practice-26-science-01-zang-olum",
    "elem3-practice-27-science-02-khorakiha",
    "elem3-practice-28-science-03-andazegiri-mavad",
    "elem3-practice-29-science-04-mavad-atraf-ma",
    "elem3-practice-30-science-05-ab-made-ba-arzesh",
    "elem3-practice-31-science-06-zendegi-ma-va-ab",
    "elem3-practice-32-science-07-nur-va-moshahede",
    "elem3-practice-33-science-08-jostoju-besazim",
    "elem3-practice-34-science-09-niru-1",
    "elem3-practice-35-science-10-niru-2",
    "elem3-practice-36-science-11-bekarid-bebinid",
    "elem3-practice-37-science-12-har-kodam-jay-khod-1",
    "elem3-practice-38-science-13-har-kodam-jay-khod-2",
    "elem3-practice-39-science-14-az-gozashte-ta-ayande" -> 20 to "📝 تمرین‌های کتاب درسی (خودکفایی)"

    // تمرین‌های کتاب درسی — پایه چهارم دبستان (خودکفایی فاز ۲)
    "elem4-practice-01-farsi01",
    "elem4-practice-02-farsi02",
    "elem4-practice-03-farsi03",
    "elem4-practice-04-farsi04",
    "elem4-practice-05-farsi05",
    "elem4-practice-06-farsi06",
    "elem4-practice-07-farsi07",
    "elem4-practice-08-farsi10",
    "elem4-practice-09-farsi11",
    "elem4-practice-10-farsi12",
    "elem4-practice-11-farsi13",
    "elem4-practice-12-farsi14",
    "elem4-practice-13-farsi15",
    "elem4-practice-14-farsi16",
    "elem4-practice-15-farsi17",
    "elem4-practice-16-neg01",
    "elem4-practice-17-neg02",
    "elem4-practice-18-neg03",
    "elem4-practice-19-neg04",
    "elem4-practice-20-neg05",
    "elem4-practice-21-neg06",
    "elem4-practice-22-neg07",
    "elem4-practice-23-neg08",
    "elem4-practice-24-neg09",
    "elem4-practice-25-neg10",
    "elem4-practice-26-neg11",
    "elem4-practice-27-neg12",
    "elem4-practice-28-neg13",
    "elem4-practice-29-neg14",
    "elem4-practice-30-neg15",
    "elem4-practice-31-neg16",
    "elem4-practice-32-neg17",
    "elem4-practice-33-math01",
    "elem4-practice-34-math02",
    "elem4-practice-35-math03",
    "elem4-practice-36-math04",
    "elem4-practice-37-math05",
    "elem4-practice-38-math06",
    "elem4-practice-39-math07",
    "elem4-practice-40-sci01",
    "elem4-practice-41-sci02",
    "elem4-practice-42-sci03",
    "elem4-practice-43-sci04",
    "elem4-practice-44-sci05",
    "elem4-practice-45-sci06",
    "elem4-practice-46-sci07",
    "elem4-practice-47-sci08",
    "elem4-practice-48-sci09",
    "elem4-practice-49-sci10",
    "elem4-practice-50-sci11",
    "elem4-practice-51-sci12",
    "elem4-practice-52-sci13",
    "elem4-practice-53-soc01",
    "elem4-practice-54-soc02",
    "elem4-practice-55-soc03",
    "elem4-practice-56-soc04",
    "elem4-practice-57-soc05",
    "elem4-practice-58-soc06",
    "elem4-practice-59-soc07",
    "elem4-practice-60-soc08",
    "elem4-practice-61-soc09",
    "elem4-practice-62-soc10",
    "elem4-practice-63-soc11",
    "elem4-practice-64-soc12",
    "elem4-practice-65-soc13",
    "elem4-practice-66-soc14",
    "elem4-practice-67-soc15",
    "elem4-practice-68-soc16",
    "elem4-practice-69-soc17",
    "elem4-practice-70-soc18",
    "elem4-practice-71-soc19",
    "elem4-practice-72-soc20",
    "elem4-practice-73-soc21",
    "elem4-practice-74-soc22",
    "elem4-practice-75-hedyeh01",
    "elem4-practice-76-hedyeh02",
    "elem4-practice-77-hedyeh03",
    "elem4-practice-78-hedyeh04",
    "elem4-practice-79-hedyeh05",
    "elem4-practice-80-hedyeh06",
    "elem4-practice-81-hedyeh07",
    "elem4-practice-82-hedyeh08",
    "elem4-practice-83-hedyeh09",
    "elem4-practice-84-hedyeh10",
    "elem4-practice-85-hedyeh11",
    "elem4-practice-86-hedyeh12",
    "elem4-practice-87-hedyeh13",
    "elem4-practice-88-hedyeh14",
    "elem4-practice-89-hedyeh15",
    "elem4-practice-90-hedyeh16",
    "elem4-practice-91-hedyeh17",
    "elem4-practice-92-hedyeh18",
    "elem4-practice-93-hedyeh19",
    "elem4-practice-94-quran01",
    "elem4-practice-95-quran02",
    "elem4-practice-96-quran03",
    "elem4-practice-97-quran04",
    "elem4-practice-98-quran05",
    "elem4-practice-99-quran06",
    "elem4-practice-100-quran07",
    "elem4-practice-101-quran08",
    "elem4-practice-102-quran09",
    "elem4-practice-103-quran10",
    "elem4-practice-104-quran11",
    "elem4-practice-105-quran12",
    "elem4-practice-106-quran13",
    "elem4-practice-107-quran14",
    "elem4-practice-108-quran15" -> 20 to "📝 تمرین‌های کتاب درسی (خودکفایی)"

    // تمرین‌های کتاب درسی — پایه پنجم دبستان (خودکفایی فاز ۲)
    "elem5-practice-01-math-ch1",
    "elem5-practice-02-math-ch2",
    "elem5-practice-03-math-ch3",
    "elem5-practice-04-math-ch4",
    "elem5-practice-05-math-ch5",
    "elem5-practice-06-math-ch6",
    "elem5-practice-07-math-ch7",
    "elem5-practice-08-science-01",
    "elem5-practice-09-science-02",
    "elem5-practice-10-science-03",
    "elem5-practice-11-science-04",
    "elem5-practice-12-science-05",
    "elem5-practice-13-science-06",
    "elem5-practice-14-science-07",
    "elem5-practice-15-science-08",
    "elem5-practice-16-science-09",
    "elem5-practice-17-science-10",
    "elem5-practice-18-science-11",
    "elem5-practice-19-science-12",
    "elem5-practice-20-farsi-01",
    "elem5-practice-21-farsi-02",
    "elem5-practice-22-farsi-03",
    "elem5-practice-23-farsi-04",
    "elem5-practice-24-farsi-05",
    "elem5-practice-25-farsi-06",
    "elem5-practice-26-farsi-07",
    "elem5-practice-27-farsi-08",
    "elem5-practice-28-farsi-09",
    "elem5-practice-29-farsi-10",
    "elem5-practice-30-farsi-11",
    "elem5-practice-31-farsi-12",
    "elem5-practice-32-farsi-13",
    "elem5-practice-33-farsi-14",
    "elem5-practice-34-farsi-15",
    "elem5-practice-35-farsi-16",
    "elem5-practice-36-farsi-17",
    "elem5-practice-37-social-01",
    "elem5-practice-38-social-02",
    "elem5-practice-39-social-03",
    "elem5-practice-40-social-04",
    "elem5-practice-41-social-05",
    "elem5-practice-42-social-06",
    "elem5-practice-43-social-07",
    "elem5-practice-44-social-08",
    "elem5-practice-45-social-09",
    "elem5-practice-46-social-10",
    "elem5-practice-47-social-11",
    "elem5-practice-48-social-12",
    "elem5-practice-49-social-13",
    "elem5-practice-50-social-14",
    "elem5-practice-51-social-15",
    "elem5-practice-52-social-16",
    "elem5-practice-53-social-17",
    "elem5-practice-54-social-18",
    "elem5-practice-55-social-19",
    "elem5-practice-56-social-20",
    "elem5-practice-57-social-21",
    "elem5-practice-58-social-22",
    "elem5-practice-59-hedyeh-01",
    "elem5-practice-60-hedyeh-02",
    "elem5-practice-61-hedyeh-03",
    "elem5-practice-62-hedyeh-04",
    "elem5-practice-63-hedyeh-05",
    "elem5-practice-64-hedyeh-06",
    "elem5-practice-65-hedyeh-07",
    "elem5-practice-66-hedyeh-08",
    "elem5-practice-67-hedyeh-09",
    "elem5-practice-68-hedyeh-10",
    "elem5-practice-69-hedyeh-11",
    "elem5-practice-70-hedyeh-12",
    "elem5-practice-71-hedyeh-13",
    "elem5-practice-72-hedyeh-14",
    "elem5-practice-73-hedyeh-15",
    "elem5-practice-74-hedyeh-16",
    "elem5-practice-75-hedyeh-17",
    "elem5-practice-76-quran-01",
    "elem5-practice-77-quran-02",
    "elem5-practice-78-quran-03",
    "elem5-practice-79-quran-04",
    "elem5-practice-80-quran-05",
    "elem5-practice-81-quran-06",
    "elem5-practice-82-quran-07",
    "elem5-practice-83-quran-08",
    "elem5-practice-84-quran-09",
    "elem5-practice-85-quran-10",
    "elem5-practice-86-quran-11",
    "elem5-practice-87-quran-12",
    "elem5-practice-88-negaresh-01",
    "elem5-practice-89-negaresh-02",
    "elem5-practice-90-negaresh-03",
    "elem5-practice-91-negaresh-04",
    "elem5-practice-92-negaresh-05",
    "elem5-practice-93-negaresh-06",
    "elem5-practice-94-negaresh-07",
    "elem5-practice-95-negaresh-08",
    "elem5-practice-96-negaresh-09",
    "elem5-practice-97-negaresh-10",
    "elem5-practice-98-negaresh-11",
    "elem5-practice-99-negaresh-12",
    "elem5-practice-100-negaresh-13",
    "elem5-practice-101-negaresh-14",
    "elem5-practice-102-negaresh-15",
    "elem5-practice-103-negaresh-16",
    "elem5-practice-104-negaresh-17" -> 20 to "📝 تمرین‌های کتاب درسی (خودکفایی)"

    // تمرین‌های کتاب درسی — پایه ششم دبستان (خودکفایی فاز ۲)
    "elem6-practice-01-farsi-01-marifat",
    "elem6-practice-02-farsi-03-houshyari",
    "elem6-practice-03-farsi-05-haft-khan",
    "elem6-practice-04-farsi-06-ey-vatan",
    "elem6-practice-05-farsi-10-attar",
    "elem6-practice-06-farsi-12-dousti",
    "elem6-practice-07-farsi-16-adab-motalee",
    "elem6-practice-08-negaresh-01-band-bahar",
    "elem6-practice-09-negaresh-14-janbakhshi",
    "elem6-practice-10-negaresh-15-nasr-sher",
    "elem6-practice-11-negaresh-16-edameh-band",
    "elem6-practice-12-negaresh-17-motazad",
    "elem6-practice-13-math-01-adad-olgou",
    "elem6-practice-14-math-02-kasr",
    "elem6-practice-15-math-03-ashari",
    "elem6-practice-16-math-04-tagharin",
    "elem6-practice-17-math-05-andazehgiri",
    "elem6-practice-18-math-06-tanasob-darsad",
    "elem6-practice-19-math-07-amar-ehtemal",
    "elem6-practice-20-science-01-ravesh-elmi",
    "elem6-practice-21-science-02-kaghaz",
    "elem6-practice-22-science-04-zamin",
    "elem6-practice-23-science-05-zamin-pouya",
    "elem6-practice-24-science-09-energy",
    "elem6-practice-25-science-11-barg",
    "elem6-practice-26-social-01-dousti",
    "elem6-practice-27-social-03-tasmimgiri",
    "elem6-practice-28-social-05-keshavarzi",
    "elem6-practice-29-social-07-talay-siah",
    "elem6-practice-30-social-11-esfahan",
    "elem6-practice-31-hedyeh-01-yekta",
    "elem6-practice-32-hedyeh-02-rahnamayan",
    "elem6-practice-33-hedyeh-04-bagh-serri",
    "elem6-practice-34-hedyeh-06-forat-karun",
    "elem6-practice-35-hedyeh-11-adab-zendegi",
    "elem6-practice-36-quran-01-namaz",
    "elem6-practice-37-quran-02-fath",
    "elem6-practice-38-quran-08-rahman",
    "elem6-practice-39-quran-15-tabestan",
    "elem6-practice-40-tafakkor-01-entekhab",
    "elem6-practice-41-tafakkor-08-porsesh" -> 20 to "📝 تمرین‌های کتاب درسی (خودکفایی)"

    // تمرین‌های کتاب درسی — پایه هفتم متوسطه اول (خودکفایی فاز ۲)
    "mid7-practice-01-farsi01-zang-afarinesh",
    "mid7-practice-02-farsi07-elm-zendegani",
    "mid7-practice-03-farsi-ravankhani",
    "mid7-practice-04-negaresh03-tabaghebandi",
    "mid7-practice-05-negaresh05-body-paragraphs",
    "mid7-practice-06-math02-integers",
    "mid7-practice-07-math03-algebra-equation",
    "mid7-practice-08-math07-power-root",
    "mid7-practice-09-science02-measurement",
    "mid7-practice-10-science03-atoms",
    "mid7-practice-11-science08-energy",
    "mid7-practice-12-arabic01-alphabet",
    "mid7-practice-13-arabic11-kam-numbers",
    "mid7-practice-14-english01-my-name",
    "mid7-practice-15-english04-my-family",
    "mid7-practice-16-social03-why-rules",
    "mid7-practice-17-social11-climate",
    "mid7-practice-18-social19-aryaiha",
    "mid7-practice-19-payam02-esteanat",
    "mid7-practice-20-payam10-sotoun-din",
    "mid7-practice-21-karfan06-programming",
    "mid7-practice-22-tafakkor02-sekke-tala",
    "mid7-practice-23-honar15-naghsh-hendesi" -> 20 to "📝 تمرین‌های کتاب درسی (خودکفایی)"

    // تمرین‌های کتاب درسی — پایه هشتم متوسطه اول (خودکفایی فاز ۲)
    "mid8-practice-01-farsi-01-03",
    "mid8-practice-02-farsi-07-08",
    "mid8-practice-03-farsi-09-10",
    "mid8-practice-04-negaresh-01-03",
    "mid8-practice-05-math-01",
    "mid8-practice-06-math-02",
    "mid8-practice-07-math-04",
    "mid8-practice-08-math-07",
    "mid8-practice-09-science-01",
    "mid8-practice-10-science-03",
    "mid8-practice-11-science-09-10",
    "mid8-practice-12-arabic-02-03",
    "mid8-practice-13-arabic-04-05",
    "mid8-practice-14-english-01-02",
    "mid8-practice-15-english-03-04",
    "mid8-practice-16-social-01-04",
    "mid8-practice-17-social-09-12",
    "mid8-practice-18-social-17-20",
    "mid8-practice-19-payam-01-03",
    "mid8-practice-20-payam-08-10",
    "mid8-practice-21-karfan-01-02",
    "mid8-practice-22-tafakkor-01-05",
    "mid8-practice-23-honar-01-04" -> 20 to "📝 تمرین‌های کتاب درسی (خودکفایی)"

    // تمرین‌های کتاب درسی — پایه نهم متوسطه اول (خودکفایی فاز ۲)
    "mid9-practice-01-farsi-01-afarinesh",
    "mid9-practice-02-farsi-07-partow-omid",
    "mid9-practice-03-farsi-12-payamavar-rahmat",
    "mid9-practice-04-negaresh-02-vazheha",
    "mid9-practice-05-negaresh-08-editing",
    "mid9-practice-06-math-01-sets",
    "mid9-practice-07-math-02-real-numbers",
    "mid9-practice-08-math-04-powers-roots",
    "mid9-practice-09-math-06-linear-equations",
    "mid9-practice-10-science-02-atoms-behavior",
    "mid9-practice-11-science-04-motion",
    "mid9-practice-12-science-08-pressure",
    "mid9-practice-13-science-15-living-together",
    "mid9-practice-14-arabic-03-friendship-bridge",
    "mid9-practice-15-arabic-05-hope",
    "mid9-practice-16-arabic-08-taxi-dialogue",
    "mid9-practice-17-english-01-personality",
    "mid9-practice-18-english-06-health",
    "mid9-practice-19-social-02-earth-movements",
    "mid9-practice-20-social-10-safavid-society",
    "mid9-practice-21-social-23-productivity",
    "mid9-practice-22-payam-06-vozu-ghosl-tayammom",
    "mid9-practice-23-payam-10-masouliat-hamegani",
    "mid9-practice-24-karfan-01-algoritm",
    "mid9-practice-25-karfan-04-python-3",
    "mid9-practice-26-defa-07-padafand",
    "mid9-practice-27-defa-09-emdad",
    "mid9-practice-28-honar-02-tarahi-ashya",
    "mid9-practice-29-honar-04-negashtar",
    "mid9-practice-30-honar-10-chahar-lengeh" -> 20 to "📝 تمرین‌های کتاب درسی (خودکفایی)"


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
    "fonoon_04_grade12_lafo_nashr_esloob_hasan" -> "📚 مطابق درس ۶، ۹ و ۱۲ علوم و فنون ادبی ۳ — پایه دوازدهم انسانی (چاپ ۱۴۰۵ ویژه کنکور ۱۴۰۶)"
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
    "geo_01_grade10_1403" -> "📚 صفحه ۰ تا آخر کتاب جغرافیای ایران (۳ فصل و ۱۰ درس کامل) — پایه دهم انسانی (چاپ ۱۴۰۳ ویژه کنکور ۱۴۰۶)"
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
    "dini_02_grade11_1404_complete" -> "📚 صفحه ۰ تا آخر کتاب دین و زندگی ۲ اختصاصی انسانی (درس ۱ تا ۱۸ کامل) — پایه یازدهم انسانی (چاپ ۱۴۰۴)"
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
    "arooz_11_ch2_nahamsan_meters" -> "📚 مطابق درس ۶، ۹ و ۱۲ علوم و فنون ادبی ۲ — پایه یازدهم انسانی (چاپ ۱۴۰۴، کد ۱۱۱۲۰۳: اوزان ناهمسان و دولختی)"
    "fonoon_01_bayani_figures" -> "📚 مطابق درس ۲، ۵، ۸ و ۱۱ علوم و فنون ادبی ۲ — پایه یازدهم انسانی (چاپ ۱۴۰۴، کد ۱۱۱۲۰۳: تشبیه، استعاره، مجاز و کنایه)"
    "philo_03_logic_syllogism_conditional" -> "📚 مطابق درس ۸، ۹ و ۱۰ منطق — پایه دهم انسانی (چاپ ۱۴۰۳، کد ۱۱۰۲۲۰: قیاس اقترانی، قیاس استثنایی و مغالطات)"
    "logic_10_lesson1_2_concepts_terms" -> "📚 مطابق درس ۱ و ۲ منطق — پایه دهم انسانی (چاپ ۱۴۰۳، کد ۱۱۰۲۲۰: تفکر، تصور و تصدیق، دلالت و اقسام الفاظ)"
    "logic_10_lesson3_4_relations_five_universals" -> "📚 مطابق درس ۳ و ۴ منطق — پایه دهم انسانی (چاپ ۱۴۰۳، کد ۱۱۰۲۲۰: مفاهیم جزئی و کلی، نسب اربع و کلیات خمس)"
    "logic_10_lesson5_definitions" -> "📚 مطابق درس ۵ منطق — پایه دهم انسانی (چاپ ۱۴۰۳، کد ۱۱۰۲۲۰: تعریف، حد و رسم، شروط تعریف و تقسیم منطقی)"
    "philo_04_grade11_history_epistemology" -> "📚 مطابق درس ۱ تا ۱۲ فلسفه ۱ — پایه یازدهم انسانی (چاپ ۱۴۰۴، کد ۱۱۱۲۲۶: تاریخ فلسفه یونان و اسلامی و معرفت‌شناسی)"
    "soc_10_ch1_human_social_action" -> "📚 مطابق درس ۱ تا ۴ جامعه‌شناسی ۱ — پایه دهم انسانی (چاپ ۱۴۰۳: کنش‌های ما، پدیده‌های اجتماعی، جهان اجتماعی، اجزا و لایه‌های جهان اجتماعی)"
    "soc_10_ch2_culture_layers_values" -> "📚 مطابق درس ۵ تا ۹ جامعه‌شناسی ۱ — پایه دهم انسانی (چاپ ۱۴۰۳: جهان‌های اجتماعی، پیامدها و ارزیابی، هویت، بازتولید هویت اجتماعی)"
    "soc_11_ch1_cultural_world_globalization" -> "📚 مطابق درس ۱ تا ۷ جامعه‌شناسی ۲ — پایه یازدهم انسانی (چاپ ۱۴۰۴: جهان فرهنگی، فرهنگ جهانی، نمونه‌های فرهنگ جهانی، باورها و ارزش‌های بنیادین فرهنگ غرب، تکوین فرهنگ معاصر غرب، جامعه جهانی)"
    "soc_12_ch1_three_approaches_social_sciences" -> "📚 مطابق درس ۱ تا ۵ جامعه‌شناسی ۳ — پایه دوازدهم انسانی (چاپ ۱۴۰۵، کد ۱۱۲۲۲۲: ذخیره دانشی، علوم اجتماعی، نظم اجتماعی، کنش اجتماعی، معنای زندگی)"
    "geo_10_ch1_location_topography_climate_rivers" -> "📚 مطابق درس ۳ تا ۶ جغرافیای ایران — پایه دهم (چاپ ۱۴۰۳، کد ۱۱۰۲۱۸: موقعیت، ناهمواری‌ها، آب‌وهوا و منابع آب ایران)"
    "geo_10_ch3_economy_transport_borders" -> "📚 مطابق درس ۸ و ۱۰ جغرافیای ایران — پایه دهم (چاپ ۱۴۰۳، کد ۱۱۰۲۱۸: تقسیمات کشوری و توان‌های اقتصادی ایران)"
    "geo_11_ch1_regions_biomes_culture" -> "📚 مطابق درس ۱ تا ۸ جغرافیا ۲ (ناحیه‌ای) — پایه یازدهم انسانی (چاپ ۱۴۰۴، کد ۱۱۱۲۱۸: مفهوم ناحیه، زیست‌بوم‌ها و نواحی فرهنگی)"
    "geo_11_ch3_case_studies_sw_asia_europe" -> "📚 مطابق مطالعه‌های موردی جغرافیا ۲ (ناحیه‌ای) — پایه یازدهم انسانی (چاپ ۱۴۰۴، کد ۱۱۱۲۱۸: نپال، ناحیه فرهنگی جهان اسلام، خلیج فارس و اتحادیه اروپا)"
    "geo_12_ch1_applied_geography_hazards" -> "📚 مطابق درس ۱ تا ۴ جغرافیا ۳ (کاربردی) — پایه دوازدهم انسانی (چاپ ۱۴۰۵، کد ۱۱۲۲۱۸: شهر و روستا، مخاطرات طبیعی و مدیریت بحران)"
    "dini_10_ch1_purpose_creation_theology" -> "📚 مطابق درس ۱ تا ۱۲ دین و زندگی ۱ — پایه دهم انسانی (چاپ دهم ۱۴۰۴، کد ۱۱۰۲۰۵)"
    "dini_10_ch3_friendship_ethics_society" -> "📚 مطابق درس ۷ تا ۱۲ دین و زندگی ۱ — پایه دهم انسانی (چاپ ۱۴۰۳، کد ۱۱۰۲۰۱: واقعه بزرگ قیامت، فرشتگان، دوستی و عفاف)"
    "dini_11_ch1_divine_guidance_quran_prophet" -> "📚 مطابق درس ۱ تا ۵ دین و زندگی ۲ — پایه یازدهم انسانی (چاپ ۱۴۰۴، کد ۱۱۱۲۱۴: هدایت الهی، اعجاز قرآن و ختم نبوت)"
    "dini_11_ch3_imams_life_ghaybat_velayat" -> "📚 مطابق درس ۹ تا ۱۴ دین و زندگی ۲ — پایه یازدهم انسانی (چاپ ۱۴۰۴، کد ۱۱۱۲۱۴: سیره ائمه ع، عصر غیبت، ولایت فقیه و انتظار)"
    "farsi_10_ch2_literary_intellectual_realms" -> "📚 مطابق درس ۱ تا ۱۸ فارسی ۱ — پایه دهم (چاپ ۱۴۰۳، کد ۱۱۰۲۰۱: قلمرو ادبی، فکری، املا و تاریخ ادبیات)"
    "farsi_12_ch1_syntax_noun_phrase_realms" -> "📚 مطابق درس ۱ تا ۱۸ فارسی ۳ — پایه دوازدهم (چاپ ۱۴۰۵، کد ۱۱۲۲۰۱: قلمرو زبانی، گروه اسمی، وابسته‌ها و شمارش واژه)"
    "farsi_12_ch2_final_exam_vocab_spelling_authors" -> "📚 مطابق درس ۱ تا ۱۸ فارسی ۳ — پایه دوازدهم (چاپ ۱۴۰۵، کد ۱۱۲۲۰۱: قلمرو ادبی و فکری، هم‌آواها و تاریخ ادبیات نهایی)"
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




