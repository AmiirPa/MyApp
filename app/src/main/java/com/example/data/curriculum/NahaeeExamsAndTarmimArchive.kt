package com.example.data.curriculum

import androidx.compose.ui.graphics.Color

/**
 * مدل ساختار بارم‌بندی رسمی امتحانات نهایی آموزش و پرورش (مصوب کنکور ۱۴۰۵ و ۱۴۰۶)
 */
data class NahaeeBaremBandiItem(
    val subjectId: String,
    val subjectTitle: String,
    val gradeLevel: String, // "پایه دوازدهم", "پایه یازدهم"
    val coefficient: String, // ضریب قطعی سوابق تحصیلی (مثلاً ۱۸.۶۴)
    val khordadBreakdown: List<Pair<String, String>>, // بخش/فصل به نمره خرداد
    val shahrivarDeyBreakdown: List<Pair<String, String>>, // نمره شهریور و دی
    val expertAdviceNote: String // توصیه کلیدی مشاور برای کسب ۲۰
)

/**
 * مدل داده‌ای امتحان نهایی تشریحی با بارم‌بندی رسمی مصححین آموزش و پرورش
 */
data class NahaeeDescriptiveQuestion(
    val id: String,
    val subjectId: String,
    val subjectTitle: String,
    val gradeLevel: String, // "دوازدهم", "یازدهم"
    val sessionYear: String, // "خرداد ۱۴۰۴", "شهریور ۱۴۰۳", "دی ۱۴۰۲", "خردad ۱۳۹۸", ...
    val examType: String, // "خرداد", "شهریور", "دی"
    val scorePoints: String, // "۱.۵ نمره", "۲ نمره", "۱ نمره"
    val questionText: String,
    val officialCorrectionKey: String, // کلید رسمی تصحیح برگه نهایی (نحوه نمره‌دهی مصحح)
    val graderKeywords: List<String>, // کلمات کلیدی که نمره‌دهی روی آن‌ها قفل است
    val commonStudentTraps: String // خطاهایی که باعث کسر ۰.۲۵ یا ۰.۵ نمره می‌شود
)

/**
 * راهنمای جامع و ترفندهای ترمیم معدل داوطلبان نظام قدیم (سالی‌واحدی / ترمی‌واحدی)
 */
data class TarmimMoadelGuideRule(
    val stepIndex: Int,
    val title: String,
    val targetAudience: String,
    val summaryText: String,
    val detailedActionableSteps: String,
    val graderSecretTip: String
)

/**
 * آرشیو کامل امتحانات نهایی نظام جدید (۱۳۹۸ تا ۱۴۰۵) شامل دوره‌های خرداد، شهریور و دی
 * همراه با بارم‌بندی رسمی سازمان پژوهش و آموزش و پرورش مصوب کنکور ۱۴۰۶
 */
object NahaeeExamsAndTarmimArchive {

    val baremBandiList = listOf(
        NahaeeBaremBandiItem(
            subjectId = "farsi",
            subjectTitle = "فارسی ۳ دوازدهم",
            gradeLevel = "پایه دوازدهم",
            coefficient = "۱۸.۶۴ (بزرگ‌ترین ضریب در کل کنکور)",
            khordadBreakdown = listOf(
                "معنی واژگان و املا" to "۴.۰ نمره (واژه‌نامه پایان کتاب + ریزنکات املایی)",
                "دستور زبان فارسی" to "۵.۰ نمره (نقش‌های دستوری، گروه اسمی، وابسته، شیوه بلاغی)",
                "آرایه‌های ادبی" to "۴.۰ نمره (تشبیه، استعاره، مجاز، ایهام، کنایه و اسلوب معادله)",
                "معنی و مفهوم شعر و نثر" to "۵.۰ نمره (معنی روان ابیات و عبارات دشوار)",
                "درک مطلب و خودارزیابی" to "۲.۰ نمره (پاسخ به سوالات درک متن)"
            ),
            shahrivarDeyBreakdown = listOf(
                "کل کتاب فارسی ۳ (تمام فصل‌های ۱ تا ۸)" to "۲۰.۰ نمره کامل"
            ),
            expertAdviceNote = "💡 خط به خط واژه‌نامه را حفظ کنید! غلط املایی در کلمات املایی ۰.۲۵ نمره کسر قطعی دارد."
        ),
        NahaeeBaremBandiItem(
            subjectId = "dini",
            subjectTitle = "دین و زندگی ۳ دوازدهم",
            gradeLevel = "پایه دوازدهم",
            coefficient = "۱۴.۲۳ (دومین ضریب بزرگ کنکور ۱۴۰۶)",
            khordadBreakdown = listOf(
                "ترجمه و پیام آیات قرآن" to "۶.۰ نمره (تک‌تک کلمات کلیدی پیام آیات)",
                "متن درس‌ها و مفاهیم" to "۸.۰ نمره (تعاریف، استدلال‌ها و تقسیم‌بندی‌ها)",
                "اندیشه و تحقیق و تفکر در قرآن" to "۴.۰ نمره (پاسخ‌های انتهای هر درس)",
                "حفظ آیات برجسته" to "۲.۰ نمره (تکمیل جاهای خالی آیات)"
            ),
            shahrivarDeyBreakdown = listOf(
                "دروس ۱ تا ۱۰ دینی ۳" to "۲۰.۰ نمره کامل"
            ),
            expertAdviceNote = "💡 تیترها و شماره‌گذاری‌های متن درس بارم ثابت ۰.۵ نمره‌ای دارند. پاسخ‌ها را دقیقاً با تیتر کتاب بنویسید."
        ),
        NahaeeBaremBandiItem(
            subjectId = "fonoon",
            subjectTitle = "علوم و فنون ادبی ۳ دوازدهم",
            gradeLevel = "پایه دوازدهم",
            coefficient = "۱۰.۴۳ (مهم‌ترین درس اختصاصی انسانی)",
            khordadBreakdown = listOf(
                "تاریخ ادبیات و سبک‌شناسی" to "۴.۰ نمره (ویژگی‌های فکری، زبانی و ادبی سبک‌ها)",
                "موسیقی شعر و عروض" to "۵.۰ نمره (تقطیع هجایی، اختیارات و وزن شعر)",
                "زیبایی‌شناسی و آرایه‌ها" to "۵.۰ نمره (تشخیص آرایه‌ها با دلیل)",
                "نقد ادبی و تحلیل متن" to "۶.۰ نمره (تحلیل کارگاهی و خودارزیابی‌ها)"
            ),
            shahrivarDeyBreakdown = listOf(
                "کتاب علوم و فنون ۳ کامل" to "۲۰.۰ نمره کامل"
            ),
            expertAdviceNote = "💡 علامت‌های هجایی (کوتاه U، بلند -) را در تقطیع با دقت رسم کنید؛ هر علامت اشتباه ۰.۲۵ نمره می‌سوزاند."
        ),
        NahaeeBaremBandiItem(
            subjectId = "english",
            subjectTitle = "زبان انگلیسی ۳ (Vision 3)",
            gradeLevel = "پایه دوازدهم",
            coefficient = "۱۰.۱۷",
            khordadBreakdown = listOf(
                "درک مطلب (Reading)" to "۶.۰ نمره (متن خارج از کتاب + سوالات True/False و تشریحی)",
                "واژگان (Vocabulary)" to "۴.۰ نمره (تکمیل جمله، مترادف/متضاد و تعریف واژه)",
                "گرامر (Grammar)" to "۴.۰ نمره (جملات مجهول، شرطی‌ها و حروف ربط)",
                "نگارش (Writing)" to "۴.۰ نمره (پاراگراف‌نویسی، Topic Sentence و Supporting Sentences)",
                "تلفظ و آواشناسی (Pronunciation)" to "۲.۰ نمره (تکیه کلمات Stress)"
            ),
            shahrivarDeyBreakdown = listOf(
                "دروس ۱ تا ۳ زبان ۳" to "۲۰.۰ نمره کامل"
            ),
            expertAdviceNote = "💡 در بخش Writing رعایت بزرگ نوشتن حروف اول جملات (Capitalization) و نقطه انتهای جمله بارم دارد!"
        ),
        NahaeeBaremBandiItem(
            subjectId = "arabic",
            subjectTitle = "عربی ۳ زبان قرآن دوازدهم",
            gradeLevel = "پایه دوازدهم",
            coefficient = "۷.۸۰",
            khordadBreakdown = listOf(
                "ترجمه عبارات و افعال" to "۷.۰ نمره (ترجمه دقیق زمان افعال و ترکیب‌ها)",
                "واژه‌نامه و حوار (گفت‌وگو)" to "۳.۰ نمره (مترادف، متضاد و پاسخ به حوار)",
                "قواعد و اعراب" to "۶.۰ نمره (حال، استثناء، مفعول مطلق و اعراب)",
                "تحلیل صرفی و درک متن" to "۴.۰ نمره (جدول تحلیل صرفی و سوالات درک متن)"
            ),
            shahrivarDeyBreakdown = listOf(
                "دروس ۱ تا ۴ عربی ۳" to "۲۰.۰ نمره کامل"
            ),
            expertAdviceNote = "💡 نقش «حال» باید حتماً با عبارت «در حالی که...» ترجمه شود تا ۰.۷۵ نمره کامل ترجمه اعطا گردد."
        ),
        NahaeeBaremBandiItem(
            subjectId = "math",
            subjectTitle = "ریاضی و آمار ۳ دوازدهم",
            gradeLevel = "پایه دوازدهم",
            coefficient = "۵.۱۶",
            khordadBreakdown = listOf(
                "فصل ۱: آمار و احتمال" to "۸.۰ نمره (چرخه آمار، احتمال شرطی و پیشامد مستقل)",
                "فصل ۲: الگوهای خطی و غیرخطی" to "۶.۰ نمره (سری‌های زمانی و مدل‌سازی)",
                "فصل ۳: منطق و استدلال ریاضی" to "۶.۰ نمره (گزاره‌ها، سورها و اثبات)"
            ),
            shahrivarDeyBreakdown = listOf(
                "فصل‌های ۱ تا ۳ کامل" to "۲۰.۰ نمره کامل"
            ),
            expertAdviceNote = "💡 نوشتن فرمول‌های ریاضی و راه‌حل خط به خط حتی اگر پاسخ نهایی عددی اشتباه شود، بیشتر بارم را دارد."
        ),
        NahaeeBaremBandiItem(
            subjectId = "philosophy",
            subjectTitle = "فلسفه ۲ دوازدهم",
            gradeLevel = "پایه دوازدهم",
            coefficient = "۵.۶۲",
            khordadBreakdown = listOf(
                "بخش ۱: هستی‌شناسی" to "۷.۰ نمره (وجود و ماهیت، علت و معلول)",
                "بخش ۲: معرفت‌شناسی و انسان" to "۷.۰ نمره (شناخت، دکارت و سهروردی)",
                "بخش ۳: حکمت متعالیه ملاصدرا" to "۶.۰ نمره (حرکت جوهری و اصالت وجود)"
            ),
            shahrivarDeyBreakdown = listOf(
                "دروس ۱ تا ۱۲ فلسفه ۲" to "۲۰.۰ نمره کامل"
            ),
            expertAdviceNote = "💡 اصطلاحات فلسفی نظیر «ماهیت اعتباری» و «حرکت در جوهر» کلیدهای اصلی تصحیح برگه هستند."
        ),
        NahaeeBaremBandiItem(
            subjectId = "sociology",
            subjectTitle = "جامعه‌شناسی ۳ دوازدهم",
            gradeLevel = "پایه دوازدهم",
            coefficient = "۵.۵۲",
            khordadBreakdown = listOf(
                "فصل ۱: ذخیره دانشی و کنش" to "۶.۰ نمره",
                "فصل ۲: علوم اجتماعی و فرهنگ" to "۷.۰ نمره",
                "فصل ۳: هویت و نظام جهانی" to "۷.۰ نمره"
            ),
            shahrivarDeyBreakdown = listOf(
                "دروس ۱ تا ۱۰ جامعه ۳" to "۲۰.۰ نمره کامل"
            ),
            expertAdviceNote = "💡 پاسخ‌ها را تیتروار با شماره بنویسید؛ مصحح برگه جامعه‌شناسی فقط کلید تیترها را کنترل می‌کند."
        ),
        NahaeeBaremBandiItem(
            subjectId = "history",
            subjectTitle = "تاریخ ۳ دوازدهم انسانی",
            gradeLevel = "پایه دوازدهم",
            coefficient = "۵.۴۰",
            khordadBreakdown = listOf(
                "فصل ۱: تاریخنگاری و منابع دوره معاصر" to "۳.۵ نمره",
                "فصل ۲: ایران و جهان در آستانه دوره معاصر (قاجار و مشروطه)" to "۵.۵ نمره",
                "فصل ۳: ایران از کودتای ۱۲۹۹ تا انقلاب اسلامی" to "۶.۰ نمره",
                "فصل ۴: انقلاب اسلامی و دفاع مقدس" to "۵.۰ نمره"
            ),
            shahrivarDeyBreakdown = listOf(
                "دروس ۱ تا ۱۲ تاریخ ۳" to "۲۰.۰ نمره کامل"
            ),
            expertAdviceNote = "💡 در سوالات علل و پیامدهای وقایع تاریخی (مانند انقلاب مشروطه و ملی شدن نفت)، تفکیک عوامل سیاسی، اقتصادی و اجتماعی بارم کامل دارد."
        ),
        NahaeeBaremBandiItem(
            subjectId = "geography",
            subjectTitle = "جغرافیا ۳ (کاربردی) دوازدهم انسانی",
            gradeLevel = "پایه دوازدهم",
            coefficient = "۴.۸۵",
            khordadBreakdown = listOf(
                "فصل ۱: جغرافیا و آمایش سرزمین" to "۵.۰ نمره",
                "فصل ۲: مدیریت شهرها و روستاها و حمل‌ونقل" to "۵.۰ نمره",
                "فصل ۳: مدیریت مخاطرات طبیعی (زلزله، سیل، خشکسالی)" to "۵.۵ نمره",
                "فصل ۴: جغرافیای سیاسی و ژئوپلیتیک" to "۴.۵ نمره"
            ),
            shahrivarDeyBreakdown = listOf(
                "دروس ۱ تا ۶ جغرافیا ۳" to "۲۰.۰ نمره کامل"
            ),
            expertAdviceNote = "💡 چرخه مدیریت بحران (پیشگیری، آمادگی، مقابله، بازسازی) به ترتیب مراحل در هر نوبت امتحان نهایی ۱.۵ نمره قطعی دارد."
        ),
        NahaeeBaremBandiItem(
            subjectId = "social_identity",
            subjectTitle = "هویت اجتماعی دوازدهم",
            gradeLevel = "پایه دوازدهم",
            coefficient = "۳.۶۴",
            khordadBreakdown = listOf(
                "فصل ۱ و ۲: کنش اجتماعی و پدیده‌های اجتماعی" to "۶.۵ نمره",
                "فصل ۳ و ۴: هویت فردی، اجتماعی و فرهنگی" to "۷.۰ نمره",
                "فصل ۵ و ۶: هویت ایرانی-اسلامی و جهانی‌شدن" to "۶.۵ نمره"
            ),
            shahrivarDeyBreakdown = listOf(
                "کل کتاب هویت اجتماعی دوازدهم" to "۲۰.۰ نمره کامل"
            ),
            expertAdviceNote = "💡 تفاوت ویژگی‌های انتسابی و اکتسابی هویت همراه با مثال، سوال ثابت تمام ادوار خرداد و شهریور است."
        ),
        NahaeeBaremBandiItem(
            subjectId = "general_humanities",
            subjectTitle = "سلامت و بهداشت دوازدهم",
            gradeLevel = "پایه دوازدهم",
            coefficient = "۳.۶۴",
            khordadBreakdown = listOf(
                "فصل ۱ و ۲: ابعاد سلامت، تغذیه سالم و شاخص BMI" to "۷.۰ نمره",
                "فصل ۳ و ۴: پیشگیری از بیماری‌های غیرواگیر و واگیر" to "۷.۰ نمره",
                "فصل ۵ و ۶: بهداشت روان، پیشگیری از اعتیاد و کمک‌های اولیه" to "۶.۰ نمره"
            ),
            shahrivarDeyBreakdown = listOf(
                "کل کتاب سلامت و بهداشت دوازدهم" to "۲۰.۰ نمره کامل"
            ),
            expertAdviceNote = "💡 فرمول شاخص توده بدنی BMI = وزن (کیلوگرم) تقسیم بر مجذور قد (متر) همیشه ۱ تا ۱.۵ نمره مستقیم دارد."
        )
    )

    val tarmimMoadelGuideRules = listOf(
        TarmimMoadelGuideRule(
            stepIndex = 1,
            title = "ویژه داوطلبان کنکور ۱۴۰۶ و دیپلمه‌های نظام قدیم (سالی‌واحدی/ترمی‌واحدی)",
            targetAudience = "تمام فارغ‌التحصیلان نظام قدیم و جدید که خواهان ارتقای تراز قطعی ۶۰٪ هستند",
            summaryText = "سوابق تحصیلی ۶۰٪ قطعی برای کنکور ۱۴۰۶ اعمال می‌شود! نمرات نهایی دوازدهم حیاتی‌ترین بخش رتبه‌سازی شماست.",
            detailedActionableSteps = """
                ۱. ثبت‌نام در سامانه my.medu.ir برای امتحانات نهایی خرداد ماه.
                ۲. ایجاد یا ترمیم سوابق دروس پایه دوازدهم شامل تمام دروس نهایی.
                ۳. نمره‌دهی مصححین بر اساس کلید دقیق بارم‌بندی رسمی انجام می‌گیرد (نه انشای طویل).
                ۴. در صورت اخذ نمره کمتر در ترمیم معدل، نمره قبلی بالاتر شما حفظ خواهد شد و هیچ ریسکی ندارد.
            """.trimIndent(),
            graderSecretTip = "💡 راز سنجش: سوابق تحصیلی ۶۰٪ قطعی است یعنی معدل ۲۰ به تنهایی نصف راه رتبه زیر ۱۰۰ را هموار می‌کند!"
        ),
        TarmimMoadelGuideRule(
            stepIndex = 2,
            title = "تکنیک کلیدواژه‌نویسی (کسب ۲۰ در امتحانات نهایی تشریحی)",
            targetAudience = "تمام داوطلبان کنکور انسانی و ترمیم معدل",
            summaryText = "مصححین برگه نهایی بر اساس «کلید بارم‌بندی رسمی» تصحیح می‌کنند! کلمات کلیدی دقیقاً به اندازه ۰.۲۵ نمره ارزش دارند.",
            detailedActionableSteps = """
                ۱. خط زدن کلمات اضافه: جواب‌ها را کوتاه، تیتروار و با شماره (۱، ۲، ۳) بنویسید.
                ۲. درج دقیق اصطلاحات کتاب درسی: مثلاً در جامعه‌شناسی عبارت «اومانیسم» یا «روشنگری سوژه‌محور» را قید کنید.
                ۳. در دینی: آوردن کلمه کلیدی آیه (مثلاً «تأکید بر توحید افعالی») بارم کامل ۰.۵ نمره‌ای را آزاد می‌کند.
                ۴. دور کلمات کلیدی اصلی با مداد خط بکشید تا مصحح در نیم‌ثانیه نمره کامل را ثبت کند.
            """.trimIndent(),
            graderSecretTip = "💡 راز مصححین: پاسخ‌های شماره‌گذاری‌شده بالاترین سرعت و دقت نمره‌دهی را دارند."
        )
    )

    val descriptiveNahaeeQuestions = listOf(
        // ==================== ۱. فارسی ۳ دوازدهم ====================
        NahaeeDescriptiveQuestion(
            id = "nah_farsi_1404_khordad_1",
            subjectId = "farsi",
            subjectTitle = "فارسی ۳ دوازدهم (ضریب ۱۸.۶۴)",
            gradeLevel = "دوازدهم",
            sessionYear = "امتحان نهایی خرداد ۱۴۰۴",
            examType = "خرداد",
            scorePoints = "۲.۰ نمره",
            questionText = "در عبارت «دشمن شب‌پرست چون خفاش از روشنایی حق گریزان است»، الف) نقش دستوری «خفاش» چیست؟ ب) آرایه‌های ادبی موجود در بیت را مشخص کنید.",
            officialCorrectionKey = "الف) «خفاش» = متمم مشبه‌به (۰.۵ نمره).\nب) آرایه‌ها: ۱. تشبیه (دشمن به خفاش) (۰.۵ نمره) ۲. تضاد (شب و روشنایی) (۰.۵ نمره) ۳. مراعات نظیر (شب‌پرست و خفاش) (۰.۵ نمره).",
            graderKeywords = listOf("متمم", "تشبیه", "تضاد", "مراعات نظیر"),
            commonStudentTraps = "نوشتن «مفعول» به جای «متمم» ۰.۵ نمره کسر می‌کند."
        ),
        NahaeeDescriptiveQuestion(
            id = "nah_farsi_1403_shahrivar_2",
            subjectId = "farsi",
            subjectTitle = "فارسی ۳ دوازدهم (ضریب ۱۸.۶۴)",
            gradeLevel = "دوازدهم",
            sessionYear = "امتحان نهایی شهریور ۱۴۰۳",
            examType = "شهریور",
            scorePoints = "۱.۵ نمره",
            questionText = "در بیت «تو کز محنت دیگران بی‌غمی // نشاید که نامت نهند آدمی»:\nالف) نوع «که» و نقش دستوری «آدمی» را بنویسید.\nب) وابسته‌های گروه اسمی «محنت دیگران» را مشخص کنید.",
            officialCorrectionKey = "الف) «که»: حرف ربط وابسته‌ساز (۰.۲۵ نمره) | «آدمی»: مسند (به همراه فعل نهند در معنای بنامند) (۰.۵ نمره).\nب) «محنت»: هسته (۰.۲۵ نمره) | «دیگران»: مضاف‌الیه (وابسته پسین) (۰.۵ نمره).",
            graderKeywords = listOf("مسند", "هسته", "مضاف‌الیه", "وابسته پسین"),
            commonStudentTraps = "نوشتن «مفعول» برای «آدمی» به جای مسند ۰.۵ نمره کسر می‌کند."
        ),
        NahaeeDescriptiveQuestion(
            id = "nah_farsi_1402_dey_3",
            subjectId = "farsi",
            subjectTitle = "فارسی ۳ دوازدهم",
            gradeLevel = "دوازدهم",
            sessionYear = "امتحان نهایی دی ۱۴۰۲",
            examType = "دی",
            scorePoints = "۲.۰ نمره",
            questionText = "بیت «مرغ باغ ملکوتم نیم از عالم خاک // دو سه روزی قفسی ساخته‌اند از بدنم» را به نثر روان معنی کنید و یک استعاره و یک تشبیه در آن بیابید.",
            officialCorrectionKey = "۱. معنی: من پرنده‌ای متعلق به جهان فرشتگان و آسمانم و از دنیای خاکی نیستم؛ برای چند روز کوتاه از جسم و تنم قفسی برای روحم ساخته‌اند (۱.۰ نمره).\n۲. تشبیه: بدن به قفس (اضافه تشبیهی یا تشبیه بلیغ) (۰.۵ نمره) | استعاره: مرغ باغ ملکوت (استعاره از روح الهی انسان) (۰.۵ نمره).",
            graderKeywords = listOf("پرنده جهان آسمان", "روح انسان", "بدن به قفس"),
            commonStudentTraps = "ترجمه تحت‌اللفظی بدون اشاره به مفهوم روح و جسم ۰.۵ نمره کسر دارد."
        ),
        NahaeeDescriptiveQuestion(
            id = "nah_farsi_1398_khordad_4",
            subjectId = "farsi",
            subjectTitle = "فارسی ۳ دوازدهم",
            gradeLevel = "دوازدهم",
            sessionYear = "امتحان نهایی خرداد ۱۳۹۸",
            examType = "خرداد",
            scorePoints = "۱.۵ نمره",
            questionText = "معنی واژه‌های «استسقا»، «طمأنینه» و «شغاد» را بنویسید.",
            officialCorrectionKey = "۱. استسقا: نام بیماری که بیمار آب بسیار خواهد (۰.۵ نمره)\n۲. طمأنینه: آرامش و سکون (۰.۵ نمره)\n۳. شغاد: نام برادر ناتنی رستم (۰.۵ نمره).",
            graderKeywords = listOf("نام بیماری", "آرامش", "برادر رستم"),
            commonStudentTraps = "غلط املایی در کلمه «طمأنینه» ۰.۲۵ نمره کسر قطعی دارد."
        ),

        // ==================== ۲. دین و زندگی ۳ دوازدهم ====================
        NahaeeDescriptiveQuestion(
            id = "nah_dini_1404_khordad_1",
            subjectId = "dini",
            subjectTitle = "دین و زندگی ۳ دوازدهم (ضریب ۱۴.۲۳)",
            gradeLevel = "دوازدهم",
            sessionYear = "امتحان نهایی خرداد ۱۴۰۴",
            examType = "خرداد",
            scorePoints = "۱.۵ نمره",
            questionText = "الف) آیه شریفه «إِنَّمَا وَلِیُّکُمُ اللَّهُ وَ رَسُولُهُ وَ الَّذِینَ آمَنُوا الَّذِینَ یُقِیمُونَ الصَّلَاةَ وَ یُؤْتُونَ الزَّکَاةَ وَ هُمْ رَاکِعُونَ» به کدام مرتبه از توحید و کدام رخداد تاریخی اشاره دارد؟\nب) تفاوت توحید افعالی و توحید صفاتی را در دو سطر بنویسید.",
            officialCorrectionKey = "الف) اشاره به آیه ولایت و امامت حضرت علی (ع) در حال رکوع (۰.۵ نمره) + توحید در ولایت و سرپرستی (۰.۲۵ نمره).\nب) توحید صفاتی: صفات خدا عین ذات اوست (۰.۳۷۵ نمره). توحید افعالی: تمام حوادث مخلوق و تحت اراده خداست (۰.۳۷۵ نمره).",
            graderKeywords = listOf("آیه ولایت", "امامت علی (ع)", "عین ذات", "تحت اراده خدا"),
            commonStudentTraps = "اشتباه گرفتن توحید صفاتی با توحید ذاتی ۰.۵ نمره کسر می‌کند."
        ),
        NahaeeDescriptiveQuestion(
            id = "nah_dini_1403_shahrivar_2",
            subjectId = "dini",
            subjectTitle = "دین و زندگی ۳ دوازدهم",
            gradeLevel = "دوازدهم",
            sessionYear = "امتحان نهایی شهریور ۱۴۰۳",
            examType = "شهریور",
            scorePoints = "۱.۲۵ نمره",
            questionText = "مفهوم سنت «امداد عام الهی» در قرآن کریم چیست و شامل چه کسانی می‌شود؟ (مرتبط با آیه کُلًّا نُمِدُّ هَؤُلَاءِ وَ هَؤُلَاءِ)",
            officialCorrectionKey = "امداد عام الهی شامل تمام انسان‌ها (چه مؤمن و چه کافر) می‌شود (۰.۵ نمره) و خداوند امکانات مادی حیات و قوانین جهان را در اختیار همگان قرار می‌دهد تا اختیار انسان حفظ شود (۰.۷۵ نمره).",
            graderKeywords = listOf("مؤمن و کافر", "امکانات مادی حیات", "پشتیبانی عام"),
            commonStudentTraps = "منحصر دانستن امداد عام به مؤمنان موجب کسر ۰.۵ نمره می‌شود."
        ),
        NahaeeDescriptiveQuestion(
            id = "nah_dini_1402_dey_3",
            subjectId = "dini",
            subjectTitle = "دین و زندگی ۳ دوازدهم",
            gradeLevel = "دوازدهم",
            sessionYear = "امتحان نهایی دی ۱۴۰۲",
            examType = "دی",
            scorePoints = "۱.۵ نمره",
            questionText = "مهم‌ترین آثار و برکات «اخلاص در نیت و عمل» را در زندگی فردی و اجتماعی بنویسید (۳ مورد) و شرایط قبولی «توبه» را نام ببرید.",
            officialCorrectionKey = "۱. آرامش روحی و رهایی از اضطراب (۰.۵ نمره)\n۲. نفوذ کلام و محبوبیت نزد پروردگار و مردم (۰.۵ نمره)\n۳. شرایط توبه: پشیمانی واقعی، تصمیم بر ترک گناه، و جبران «حق‌الناس» و «حق‌الله» (۰.۵ نمره).",
            graderKeywords = listOf("آرامش روحی", "محبوبیت", "جبران حق‌الناس"),
            commonStudentTraps = "نوشتن پاسخ‌های کلی بدون ذکر «جبران حق‌الناس» ۰.۲۵ نمره کسر می‌کند."
        ),

        // ==================== ۳. علوم و فنون ادبی ۳ دوازدهم ====================
        NahaeeDescriptiveQuestion(
            id = "nah_fonoon_1404_khordad_1",
            subjectId = "fonoon",
            subjectTitle = "علوم و فنون ادبی ۳ دوازدهم (ضریب ۱۰.۴۳)",
            gradeLevel = "دوازدهم",
            sessionYear = "امتحان نهایی خرداد ۱۴۰۴",
            examType = "خرداد",
            scorePoints = "۱.۷۵ نمره",
            questionText = "الف) بیت «سعدیا مرد نکونام نمیرد هرگز // زنده است آنکه دلش زنده شد به عشق» را تقطیع هجایی کنید.\nب) وزن عروضی و بحر آن را بنویسید.",
            officialCorrectionKey = "الف) تقطیع: سعدیا (- U -) مرد نکو (- U U -) نام نمیر (- U U -) د هرگز (- -) (۰.۷۵ نمره).\nب) وزن: فاعلاتن فعلاتن فعلاتن فع لن (بحر رمل مثمن مخبون محذوف) (۱ نمره).",
            graderKeywords = listOf("فاعلاتن", "فعلاتن", "رمل مثمن مخبون"),
            commonStudentTraps = "اشتباه در علامت هجای کشیده «مرد» ۰.۲۵ نمره کسر می‌کند."
        ),
        NahaeeDescriptiveQuestion(
            id = "nah_fonoon_1403_shahrivar_2",
            subjectId = "fonoon",
            subjectTitle = "علوم و فنون ادبی ۳ دوازدهم",
            gradeLevel = "دوازدهم",
            sessionYear = "امتحان نهایی شهریور ۱۴۰۳",
            examType = "شهریور",
            scorePoints = "۱.۵ نمره",
            questionText = "در بیت «عاقلان نقطه پرگار وجودند ولی // عشق داند که در این دایره سرگردانند»:\nالف) آرایه «ایهام» در کدام واژه است و دو معنای آن را بنویسید.\nب) کدام اختیارات شاعری (وزنی یا زبانی) در مصراع اول به کار رفته است؟",
            officialCorrectionKey = "الف) واژه «سرگردان» دارای ایهام است: ۱. حیران و سرگشته ۲. چرخنده به دور مرکز دایره (هر کدام ۰.۵ نمره = ۱ نمره).\nب) اختیار زبانی: بلند تلفظ کردن مصوت کوتاه (ضمّه «پرگارِ») در پایان رکن (۰.۵ نمره).",
            graderKeywords = listOf("سرگردان", "حیران", "چرخنده", "اختیار زبانی"),
            commonStudentTraps = "نوشتن تنها یک معنی برای واژه ایهام‌دار هیچ نمره‌ای از بارم ایهام نمی‌گیرد!"
        ),
        NahaeeDescriptiveQuestion(
            id = "nah_fonoon_1401_dey_3",
            subjectId = "fonoon",
            subjectTitle = "علوم و فنون ادبی ۳ دوازدهم",
            gradeLevel = "دوازدهم",
            sessionYear = "امتحان نهایی دی ۱۴۰۱",
            examType = "دی",
            scorePoints = "۱.۵ نمره",
            questionText = "سه مورد از ویژگی‌های زبانی و فکری شعر دوره بازگشت ادبی و تفاوت آن با دوره مشروطه را بنویسید.",
            officialCorrectionKey = "۱. بازگشت ادبی: تقلید از سبک خراسانی و عراقی، فقر نوآوری و دوری از پیچیدگی سبک هندی (۱ نمره).\n۲. دوره مشروطه: ورود مفاهیم وطن، آزادی، قانون و ساده‌نویسی نزدیک به زبان مردم کوچه و بازار (۰.۵ نمره).",
            graderKeywords = listOf("تقلید از سبک کهن", "فقر نوآوری", "آزادی و قانون"),
            commonStudentTraps = "عدم ذکر کلمه «تقلید» در دوره بازگشت ۰.۲۵ نمره کسر می‌کند."
        ),

        // ==================== ۴. زبان انگلیسی ۳ (Vision 3) ====================
        NahaeeDescriptiveQuestion(
            id = "nah_english_1404_khordad_1",
            subjectId = "english",
            subjectTitle = "زبان انگلیسی ۳ (Vision 3 — ضریب ۱۰.۱۷)",
            gradeLevel = "دوازدهم",
            sessionYear = "امتحان نهایی خرداد ۱۴۰۴",
            examType = "خرداد",
            scorePoints = "۲.۰ نمره",
            questionText = "الف) جمله معلوم زیر را به جمله مجهول (Passive) تبدیل کنید:\n\"Dr. Gharib founded the first children's hospital in Iran.\"\nب) دنباله پرسشی (Tag Question) مناسب برای جمله زیر بنویسید:\n\"Reza never comes late to school, ...............?\"",
            officialCorrectionKey = "الف) The first children's hospital was founded (by Dr. Gharib) in Iran. (مفعول در اول جمله ۰.۲۵ + was ۰.۵ + founded ۰.۲۵ = ۱.۰ نمره).\nب) does he? (چون قید never جمله را منفی کرده، دنباله پرسشی باید مثبت باشد = ۱.۰ نمره).",
            graderKeywords = listOf("was founded", "does he", "جمله منفی با never"),
            commonStudentTraps = "نوشتن doesn't he به دلیل ندیدن کلمه منفی‌ساز never باعث از دست رفتن کامل ۱ نمره می‌شود!"
        ),
        NahaeeDescriptiveQuestion(
            id = "nah_english_1403_shahrivar_2",
            subjectId = "english",
            subjectTitle = "زبان انگلیسی ۳ (Vision 3)",
            gradeLevel = "دوازدهم",
            sessionYear = "امتحان نهایی شهریور ۱۴۰۳",
            examType = "شهریور",
            scorePoints = "۱.۵ نمره",
            questionText = "الف) شکل صحیح فعل داخل پرانتز را در جمله شرطی نوع دوم (Conditional Type 2) بنویسید:\n\"If I (have) enough money, I would buy a dictionary for my brother.\"\nب) در پاراگراف‌نویسی انگلیسی، وظیفه جمله موضوع (Topic Sentence) چیست؟",
            officialCorrectionKey = "الف) had (در جمله شرطی نوع دوم، بعد از If زمان گذشته ساده به کار می‌رود = ۰.۷۵ نمره).\nب) Topic Sentence ایده اصلی پاراگراف (Main Idea) را معرفی می‌کند و شامل Topic + Controlling Idea است (۰.۷۵ نمره).",
            graderKeywords = listOf("had", "Main Idea", "Controlling Idea"),
            commonStudentTraps = "نوشتن have یا would have در قسمت If شرطی نوع دوم ۰.۷۵ نمره را کامل می‌سوزاند."
        ),
        NahaeeDescriptiveQuestion(
            id = "nah_english_1402_dey_3",
            subjectId = "english",
            subjectTitle = "زبان انگلیسی ۳ (Vision 3)",
            gradeLevel = "دوازدهم",
            sessionYear = "امتحان نهایی دی ۱۴۰۲",
            examType = "دی",
            scorePoints = "۱.۵ نمره",
            questionText = "جملات زیر را با حروف ربط هم‌پایه‌ساز مناسب (and, but, or, so) کامل کنید:\n1. Ali studied very hard for the final exam, ......... he got a high score.\n2. You can look up the word in a print dictionary, ......... you can use an online app.\n3. I wanted to help my grandfather, ......... I was too busy.",
            officialCorrectionKey = "1. so (بیان نتیجه: سخت درس خواند، بنابراین نمره بالا گرفت = ۰.۵ نمره)\n2. or (بیان انتخاب بین دو گزینه = ۰.۵ نمره)\n3. but (بیان تضاد: می‌خواستم کمک کنم، اما خیلی سرم شلوغ بود = ۰.۵ نمره).",
            graderKeywords = listOf("so (نتیجه)", "or (انتخاب)", "but (تضاد)"),
            commonStudentTraps = "جابه‌جا نوشتن so و but هر کدام ۰.۵ نمره کسر قطعی دارد."
        ),

        // ==================== ۵. عربی ۳ زبان قرآن دوازدهم ====================
        NahaeeDescriptiveQuestion(
            id = "nah_arabic_1404_khordad_1",
            subjectId = "arabic",
            subjectTitle = "عربی ۳ زبان قرآن دوازدهم (ضریب ۷.۸۰)",
            gradeLevel = "دوازدهم",
            sessionYear = "امتحان نهایی خرداد ۱۴۰۴",
            examType = "خرداد",
            scorePoints = "۱.۵ نمره",
            questionText = "عبارت «شَرِبْتُ المَاءَ بارِداً» را ترجمه کرده و نقش و اعراب کلمه «بارِداً» و صاحب حال را مشخص کنید.",
            officialCorrectionKey = "۱. ترجمه: آب را در حالی که سرد بود نوشیدم (۰.۷۵ نمره).\n۲. نقش «بارداً»: حال مفرد و منصوب به فتحه (۰.۵ نمره) | صاحب حال: الماء (معرفه به ال) (۰.۲۵ نمره).",
            graderKeywords = listOf("در حالی که سرد بود", "حال مفرد", "منصوب", "صاحب حال الماء"),
            commonStudentTraps = "ترجمه «بارداً» به صورت صفت («آب سرد را نوشیدم») ۰.۵ نمره کسر می‌کند."
        ),
        NahaeeDescriptiveQuestion(
            id = "nah_arabic_1403_shahrivar_2",
            subjectId = "arabic",
            subjectTitle = "عربی ۳ زبان قرآن دوازدهم",
            gradeLevel = "دوازدهم",
            sessionYear = "امتحان نهایی شهریور ۱۴۰۳",
            examType = "شهریور",
            scorePoints = "۱.۵ نمره",
            questionText = "الف) در عبارت «ما جاءَ إِلّا عَلِیٌّ»، نوع اسلوب استثناء و اعراب کلمه «عَلِیٌّ» را بنویسید.\nب) در آیه «وَ کَلَّمَ اللَّهُ مُوسَی تَکْلِیماً»، نقش و نوع مفعول مطلق را بنویسید.",
            officialCorrectionKey = "الف) نوع استثناء: مفرّغ (مستثنی‌منه محذوف و جمله منفی) (۰.۵ نمره) | اعراب علِیّ: فاعل و مرفوع (۰.۲۵ نمره).\nب) «تَکْلِیماً»: مفعول مطلق تأکیدی (چون بعد از آن صفت یا مضاف‌الیه نیامده است) (۰.۷۵ نمره).",
            graderKeywords = listOf("استثناء مفرغ", "فاعل مرفوع", "مفعول مطلق تأکیدی"),
            commonStudentTraps = "نوشتن «مستثنی منصوب» به جای «فاعل مرفوع» ۰.۵ نمره کسر می‌کند."
        ),
        NahaeeDescriptiveQuestion(
            id = "nah_arabic_1402_dey_3",
            subjectId = "arabic",
            subjectTitle = "عربی ۳ زبان قرآن دوازدهم",
            gradeLevel = "دوازدهم",
            sessionYear = "امتحان نهایی دی ۱۴۰۲",
            examType = "دی",
            scorePoints = "۱.۵ نمره",
            questionText = "در جمله «دَخَلَ الْمُعَلِّمُ الصَّفَّ وَ هُوَ یَبْتَسِمُ»، جمله حالیه را مشخص کرده و جمله را به فارسی روان ترجمه کنید.",
            officialCorrectionKey = "۱. جمله حالیه: «وَ هُوَ یَبْتَسِمُ» (واو حالیه + ضمیر + فعل مضارع) (۰.۷۵ نمره).\n۲. ترجمه: معلم وارد کلاس شد در حالی که لبخند می‌زد (۰.۷۵ نمره).",
            graderKeywords = listOf("و هو یبتسم", "جمله حالیه", "در حالی که لبخند می‌زد"),
            commonStudentTraps = "ترجمه فعل مضارع بعد از فعل ماضی به صورت «لبخند می‌زند» به جای «لبخند می‌زد» (ماضی استمراری) ۰.۵ نمره کسر دارد."
        ),

        // ==================== ۶. ریاضی و آمار ۳ دوازدهم ====================
        NahaeeDescriptiveQuestion(
            id = "nah_math_1404_khordad_1",
            subjectId = "math",
            subjectTitle = "ریاضی و آمار ۳ دوازدهم (ضریب ۵.۱۶)",
            gradeLevel = "دوازدهم",
            sessionYear = "امتحان نهایی خرداد ۱۴۰۴",
            examType = "خرداد",
            scorePoints = "۲.۰ نمره",
            questionText = "در یک سری زمانی با دو نقطه مرجع (x₁ = 1 , y₁ = 12) و (x₂ = 5 , y₂ = 28)، معادله خط روند را نوشته و مقدار y را در زمان x = 3 (درون‌یابی) و x = 7 (برون‌یابی) محاسبه کنید.",
            officialCorrectionKey = "m = (28 - 12) / (5 - 1) = 16 / 4 = 4 | y = 4x + 8 | x = 3 ⟹ y = 4 × 3 + 8 = 20 | x = 7 ⟹ y = 4 × 7 + 8 = 36",
            graderKeywords = listOf("شیب خط m = 4", "معادله y = 4x + 8", "درون‌یابی 20", "برون‌یابی 36"),
            commonStudentTraps = "ننوشتن فرمول شیب خط m = (y₂ - y₁)/(x₂ - x₁) باعث کسر ۰.۵ نمره راه‌حل می‌شود."
        ),
        NahaeeDescriptiveQuestion(
            id = "nah_math_1403_shahrivar_2",
            subjectId = "math",
            subjectTitle = "ریاضی و آمار ۳ دوازدهم",
            gradeLevel = "دوازدهم",
            sessionYear = "امتحان نهایی شهریور ۱۴۰۳",
            examType = "شهریور",
            scorePoints = "۲.۰ نمره",
            questionText = "اگر A و B دو پیشامد مستقل باشند به طوری که P(A) = 0.4 و P(B) = 0.5 باشد، مقادیر P(A ∩ B)، P(A ∪ B) و احتمال شرطی P(A | B) را محاسبه کنید.",
            officialCorrectionKey = "P(A ∩ B) = 0.4 × 0.5 = 0.2 | P(A ∪ B) = 0.4 + 0.5 - 0.2 = 0.7 | P(A | B) = P(A) = 0.4",
            graderKeywords = listOf("پیشامد مستقل", "P(A ∩ B) = 0.2", "P(A ∪ B) = 0.7", "P(A | B) = 0.4"),
            commonStudentTraps = "فراموش کردن کم کردن P(A ∩ B) در فرمول اجتماع دو پیشامد ۰.۵ نمره کسر می‌کند."
        ),
        NahaeeDescriptiveQuestion(
            id = "nah_math_1402_dey_3",
            subjectId = "math",
            subjectTitle = "ریاضی و آمار ۳ دوازدهم",
            gradeLevel = "دوازدهم",
            sessionYear = "امتحان نهایی دی ۱۴۰۲",
            examType = "دی",
            scorePoints = "۱.۷۵ نمره",
            questionText = "از بین ۶ معلم ریاضی و ۴ معلم ادبیات، به چند طریق می‌توان یک گروه ۳ نفره تشکیل داد که حداقل ۲ نفر آن‌ها معلم ریاضی باشند؟",
            officialCorrectionKey = "C(6,2) × C(4,1) + C(6,3) × C(4,0) = 15 × 4 + 20 × 1 = 60 + 20 = 80",
            graderKeywords = listOf("دو حالت مجزا", "۲ ریاضی و ۱ ادبیات", "۳ ریاضی", "۸۰ حالت"),
            commonStudentTraps = "فراموش کردن حالت «هر ۳ نفر معلم ریاضی» در کلمه کلیدی «حداقل ۲ نفر» ۰.۷۵ نمره کسر دارد."
        ),

        // ==================== ۷. فلسفه ۲ دوازدهم ====================
        NahaeeDescriptiveQuestion(
            id = "nah_phil_1404_khordad_1",
            subjectId = "philosophy",
            subjectTitle = "فلسفه ۲ دوازدهم (ضریب ۵.۶۲)",
            gradeLevel = "دوازدهم",
            sessionYear = "امتحان نهایی خرداد ۱۴۰۴",
            examType = "خرداد",
            scorePoints = "۱.۵ نمره",
            questionText = "دیدگاه حکمت متعالیه (ملاصدرا) درباره «حقیقت حرکت جوهری» و تفاوت آن با نظر ابن‌سینا را توضیح دهید.",
            officialCorrectionKey = "۱. ابن‌سینا حرکت را فقط در ۴ عرض (کمی، کیفی، اینی، وضعی) ممکن می‌دانست و جوهر را ثابت می‌دید (۰.۷۵ نمره).\n۲. ملاصدرا اثبات کرد خود جوهر و ذات اشیاء مادی در سیلان و حرکت مداوم است (۰.۷۵ نمره).",
            graderKeywords = listOf("اعراض چهارگانه", "حرکت در ذات جوهر", "سیلان مداوم"),
            commonStudentTraps = "عدم ذکر کلمه «جوهر» باعث کسر ۰.۵ نمره می‌شود."
        ),
        NahaeeDescriptiveQuestion(
            id = "nah_phil_1403_shahrivar_2",
            subjectId = "philosophy",
            subjectTitle = "فلسفه ۲ دوازدهم",
            gradeLevel = "دوازدهم",
            sessionYear = "امتحان نهایی شهریور ۱۴۰۳",
            examType = "شهریور",
            scorePoints = "۱.۵ نمره",
            questionText = "منظور از «اصالت وجود و اعتباریت ماهیت» در فلسفه اسلامی چیست و با چه مثالی تبیین می‌شود؟",
            officialCorrectionKey = "۱. اصالت وجود: آنچه واقعیت عینی دارد و منشأ اثر در عالم خارج است «وجود» است (۰.۷۵ نمره).\n۲. اعتباریت ماهیت: «ماهیت» تنها قالب ذهنی و حدود وجود است و به تبع وجود تحقق می‌یابد (۰.۷۵ نمره).",
            graderKeywords = listOf("منشأ اثر خارجی", "اصالت وجود", "اعتباری بودن ماهیت"),
            commonStudentTraps = "یکی دانستن اعتباریت ماهیت با خیالی یا موهوم بودن ماهیت ۰.۵ نمره کسر می‌کند."
        ),
        NahaeeDescriptiveQuestion(
            id = "nah_phil_1402_dey_3",
            subjectId = "philosophy",
            subjectTitle = "فلسفه ۲ دوازدهم",
            gradeLevel = "دوازدهم",
            sessionYear = "امتحان نهایی دی ۱۴۰۲",
            examType = "دی",
            scorePoints = "۱.۵ نمره",
            questionText = "تفاوت «علم حضوری» و «علم حصولی» را در مکتب اشراق (سهروردی) بنویسید و چرا علم حضوری خطاناپذیر است؟",
            officialCorrectionKey = "۱. در علم حضوری، واقعیت خودِ معلوم بدون واسطه صورت ذهنی نزد عالم حاضر است (مانند علم انسان به خود و درد و شادی درونی)، لذا هیچ خطایی در مطابقت با خارج ندارد (۰.۷۵ نمره).\n۲. در علم حصولی، شناخت از طریق مفهوم و صورت ذهنی حاصل می‌شود و احتمال خطا در تطابق مفهوم با واقعیت خارجی وجود دارد (۰.۷۵ نمره).",
            graderKeywords = listOf("حضور خود معلوم بدون واسطه", "خطاناپذیر بودن علم حضوری", "واسطه صورت ذهنی در علم حصولی"),
            commonStudentTraps = "نوشتن مثال‌های حسی بیرونی (مثل دیدن درخت) برای علم حضوری ۰.۵ نمره کسر دارد."
        ),
        NahaeeDescriptiveQuestion(
            id = "nah_farsi_1404_shahrivar_5",
            subjectId = "farsi",
            subjectTitle = "فارسی ۳ دوازدهم (ضریب ۱۸.۶۴)",
            gradeLevel = "دوازدهم",
            sessionYear = "امتحان نهایی شهریور ۱۴۰۴",
            examType = "شهریور",
            scorePoints = "۲.۰ نمره",
            questionText = "در بیت «سعدیا گر بکند سیل فنا خانه عمر // دل قوی دار که بنیاد بقا محکم از اوست»:\nالف) دو اضافه تشبیهی (تشبیه بلیغ اضافی) بیابید.\nب) نقش دستوری «سعدیا» و «قوی» را بنویسید.",
            officialCorrectionKey = "الف) ۱. سیلِ فنا (فنا به سیل تشبیه شده) (۰.۵ نمره) ۲. خانهٔ عمر (عمر به خانه تشبیه شده) و بنیادِ بقا (۰.۵ نمره).\nب) «سعدیا»: منادا (۰.۵ نمره) | «قوی»: مسند (در عبارت دل قوی دار = دل را قوی نگه دار) (۰.۵ نمره).",
            graderKeywords = listOf("سیل فنا", "خانه عمر", "منادا", "مسند"),
            commonStudentTraps = "نوشتن صفت برای «قوی» به جای مسند ۰.۵ نمره کسر دارد."
        ),
        NahaeeDescriptiveQuestion(
            id = "nah_dini_1404_shahrivar_4",
            subjectId = "dini",
            subjectTitle = "دین و زندگی ۳ دوازدهم (ضریب ۱۴.۲۳)",
            gradeLevel = "دوازدهم",
            sessionYear = "امتحان نهایی شهریور ۱۴۰۴",
            examType = "شهریور",
            scorePoints = "۱.۵ نمره",
            questionText = "تفاوت سنت «استدراج» و سنت «املاء و مهلت دادن» در قرآن کریم چیست؟ (مرتبط با آیات سَنَسْتَدْرِجُهُمْ و إِنَّمَا نُمْلِی لَهُمْ)",
            officialCorrectionKey = "۱. سنت استدراج: هنگامی که گنهکاران با وجود غرق شدن در گناه، با فزونی نعمت‌های مادی روبه‌رو می‌شوند تا غفلتشان بیشتر شود و به تدریج به عذاب نزدیک گردند (۰.۷۵ نمره).\n۲. سنت املاء: مهلت دادن خداوند به ظالمان و کافران در دنیا تا با اختیار خود بار گناهانشان سنگین‌تر شود و حجت بر آنان تمام گردد (۰.۷۵ نمره).",
            graderKeywords = listOf("افزایش نعمت در عین گناه", "سنت استدراج", "مهلت دادن و اتمام حجت در املاء"),
            commonStudentTraps = "جابه‌جا نوشتن تعریف استدراج و املاء باعث کسر ۱ نمره می‌شود."
        ),
        NahaeeDescriptiveQuestion(
            id = "nah_fonoon_1404_shahrivar_4",
            subjectId = "fonoon",
            subjectTitle = "علوم و فنون ادبی ۳ دوازدهم",
            gradeLevel = "دوازدهم",
            sessionYear = "امتحان نهایی شهریور ۱۴۰۴",
            examType = "شهریور",
            scorePoints = "۱.۵ نمره",
            questionText = "در بیت «چشم آسایش که دارد از سپهر تیزرو // ساقیا جامی به من ده تا بیاسایم دمی»:\nالف) آرایه «مجاز» و «حسن تعلیل» یا «کنایه» را مشخص کنید.\nب) منظور از «استفهام انکاری» در مصراع اول چیست؟",
            officialCorrectionKey = "الف) «چشم»: مجاز از امید و انتظار (۰.۵ نمره) | «سپهر تیزرو»: کنایه از گذر سریع عمر و روزگار (۰.۵ نمره).\nب) استفهام انکاری: پرسشی که هدف آن نفی مضمون جمله است؛ یعنی هیچ‌کس از روزگار امید آسایش ندارد (۰.۵ نمره).",
            graderKeywords = listOf("چشم مجاز از امید", "استفهام انکاری", "هیچ‌کس امید آسایش ندارد"),
            commonStudentTraps = "معنی کردن «چشم» به معنای عضو بینایی بدون اشاره به مجاز از امید ۰.۵ نمره کسر دارد."
        ),
        NahaeeDescriptiveQuestion(
            id = "nah_math_1404_shahrivar_4",
            subjectId = "math",
            subjectTitle = "ریاضی و آمار ۳ دوازدهم",
            gradeLevel = "دوازدهم",
            sessionYear = "امتحان نهایی شهریور ۱۴۰۴",
            examType = "شهریور",
            scorePoints = "۱.۵ نمره",
            questionText = "نقیض گزاره سوردار «∀x ∈ ℝ : x² + 1 > 0» را بنویسید و ارزش گزاره اولیه و نقیض آن را تعیین کنید.",
            officialCorrectionKey = "~(∀x ∈ ℝ : x² + 1 > 0) ≡ ∃x ∈ ℝ : x² + 1 ≤ 0 | ارزش گزاره اولیه = درست (T) | ارزش نقیض = نادرست (F)",
            graderKeywords = listOf("تبدیل سور عمومی ∀ به وجودی ∃", "تبدیل > به ≤", "ارزش اولیه درست و نقیض نادرست"),
            commonStudentTraps = "تبدیل کردن علامت > به < (بدون مساوی ≤) در نقیض گزاره ۰.۵ نمره کسر قطعی دارد!"
        ),

        // ==================== ۸. جامعه‌شناسی ۳ دوازدهم ====================
        NahaeeDescriptiveQuestion(
            id = "nah_soc_1404_khordad_1",
            subjectId = "sociology",
            subjectTitle = "جامعه‌شناسی ۳ دوازدهم (ضریب ۵.۵۲)",
            gradeLevel = "دوازدهم",
            sessionYear = "امتحان نهایی خرداد ۱۴۰۴",
            examType = "خرداد",
            scorePoints = "۱.۵ نمره",
            questionText = "تفاوت رویکرد تبیینی (اثبات‌گرایی) و رویکرد تفسیری (تفهمی) را در نحوه مواجهه با پدیده‌های اجتماعی بنویسید.",
            officialCorrectionKey = "۱. رویکرد تبیینی: جهان اجتماعی را مانند طبیعت دانسته و از روش‌های کمی و علّی و تجربی استفاده می‌کند (۰.۷۵ نمره).\n۲. رویکرد تفسیری: بر معنای ذهنی کنشگران تأکید داشته و از روش‌های کیفی و تفهمی استفاده می‌کند (۰.۷۵ نمره).",
            graderKeywords = listOf("روش کمی و علّی", "معنای ذهنی", "روش کیفی و تفهمی"),
            commonStudentTraps = "قاطی کردن رویکرد انتقادی با تفسیری ۰.۷۵ نمره کسر می‌کند."
        ),
        NahaeeDescriptiveQuestion(
            id = "nah_soc_1403_shahrivar_2",
            subjectId = "sociology",
            subjectTitle = "جامعه‌شناسی ۳ دوازدهم",
            gradeLevel = "دوازدهم",
            sessionYear = "امتحان نهایی شهریور ۱۴۰۳",
            examType = "شهریور",
            scorePoints = "۱.۵ نمره",
            questionText = "ویژگی‌های اصلی «کنش اجتماعی» را نام برده (۴ مورد) و تفاوت پیامدهای ارادی و غیرارادی کنش را بنویسید.",
            officialCorrectionKey = "۱. ویژگی‌های کنش: آگاهانه، ارادی، هدفدار، معنادار و معطوف به دیگران (۱.۰ نمره).\n۲. پیامد ارادی وابسته به خواست کنشگر است (مثل روشن کردن آتش با کبریت) اما پیامد غیرارادی خارج از قصد کنشگر رخ می‌دهد (۰.۵ نمره).",
            graderKeywords = listOf("آگاهانه و ارادی", "هدفدار و معنادار", "معطوف به دیگران"),
            commonStudentTraps = "حذف ویژگی «معطوف به دیگران» در تعریف کنش اجتماعی ۰.۲۵ نمره کسر دارد."
        ),
        NahaeeDescriptiveQuestion(
            id = "nah_soc_1402_dey_3",
            subjectId = "sociology",
            subjectTitle = "جامعه‌شناسی ۳ دوازدهم",
            gradeLevel = "دوازدهم",
            sessionYear = "امتحان نهایی دی ۱۴۰۲",
            examType = "دی",
            scorePoints = "۱.۷۵ نمره",
            questionText = "تفاوت «فرهنگ آرمانی» و «فرهنگ واقعی» را توضیح دهید و بنویسید بحران هویت (ازخودبیگانگی فرهنگی) چه زمانی در جامعه رخ می‌دهد؟",
            officialCorrectionKey = "۱. فرهنگ آرمانی: عقاید و ارزش‌هایی که جامعه آن‌ها را حق و مطلوب می‌داند و از آن‌ها دفاع می‌کند (۰.۵ نمره).\n۲. فرهنگ واقعی: عقاید و ارزش‌هایی که مردم در عمل و زندگی روزمره به آن‌ها رفتار می‌کنند (۰.۵ نمره).\n۳. ازخودبیگانگی فرهنگی: زمانی رخ می‌دهد که جامعه ارزش‌ها و هویت اصیل خود را فراموش کرده و هویت بیگانه را بدون پالایش بپذیرد (۰.۷۵ نمره).",
            graderKeywords = listOf("فرهنگ آرمانی (مطلوب)", "فرهنگ واقعی (در عمل)", "فراموشی هویت اصیل"),
            commonStudentTraps = "برعکس نوشتن تعریف فرهنگ آرمانی و واقعی ۱ نمره کامل کسر می‌کند."
        ),
        NahaeeDescriptiveQuestion(
            id = "nah_soc_1401_khordad_4",
            subjectId = "sociology",
            subjectTitle = "جامعه‌شناسی ۳ دوازدهم",
            gradeLevel = "دوازدهم",
            sessionYear = "امتحان نهایی خرداد ۱۴۰۱",
            examType = "خرداد",
            scorePoints = "۱.۵ نمره",
            questionText = "رویکرد «انتقادی» در علوم اجتماعی چه هدفی را دنبال می‌کند و چه تفاوتی با رویکرد اثبات‌گرا در قبال وضع موجود جامعه دارد؟",
            officialCorrectionKey = "۱. هدف رویکرد انتقادی: کشف روابط سلطه، رهایی انسان‌ها از ستم و تغییر و اصلاح وضع موجود جامعه (۰.۷۵ نمره).\n۲. تفاوت با اثبات‌گرایی: اثبات‌گرایی فقط به توصیف و حفظ وضع موجود می‌پردازد و از داوری ارزشی پرهیز می‌کند، اما رویکرد انتقادی ارزش‌داورانه و تحول‌خواه است (۰.۷۵ نمره).",
            graderKeywords = listOf("کشف روابط سلطه", "رهایی و تغییر وضع موجود", "داوری ارزشی"),
            commonStudentTraps = "ننوشتن عبارت کلیدی «تغییر وضع موجود و رهایی از سلطه» ۰.۵ نمره کسر دارد."
        ),

        // ==================== ۹. تاریخ ۳ دوازدهم انسانی ====================
        NahaeeDescriptiveQuestion(
            id = "nah_hist_1404_khordad_1",
            subjectId = "history",
            subjectTitle = "تاریخ ۳ دوازدهم انسانی (ضریب ۵.۴۰)",
            gradeLevel = "دوازدهم",
            sessionYear = "امتحان نهایی خرداد ۱۴۰۴",
            examType = "خرداد",
            scorePoints = "۱.۵ نمره",
            questionText = "مهم‌ترین زمینه‌ها و علل شکل‌گیری «نهضت مشروطه در ایران» و نقش علما و مراجع تقلید در پیروزی آن را توضیح دهید.",
            officialCorrectionKey = "۱. علل: استبداد شاهان قاجار، نفوذ استعماری روسیه و انگلیس، بحران اقتصادی و واگذاری امتیازات (۰.۷۵ نمره).\n۲. نقش علما: رهبری مهاجرت صغری و کبری و صدور فتاوای مشروطه‌خواهی برای تأسیس عدالتخانه و مجلس شورای ملی (۰.۷۵ نمره).",
            graderKeywords = listOf("تأسیس عدالتخانه", "مهاجرت صغری و کبری", "مبارزه با استبداد و استعمار"),
            commonStudentTraps = "فراموش کردن اشاره به درخواست اولیه «تأسیس عدالتخانه» ۰.۵ نمره کسر می‌کند."
        ),
        NahaeeDescriptiveQuestion(
            id = "nah_hist_1403_shahrivar_2",
            subjectId = "history",
            subjectTitle = "تاریخ ۳ دوازدهم انسانی",
            gradeLevel = "دوازدهم",
            sessionYear = "امتحان نهایی شهریور ۱۴۰۳",
            examType = "شهریور",
            scorePoints = "۱.۵ نمره",
            questionText = "اهداف و نتایج «نهضت ملی شدن صنعت نفت ایران» در سال ۱۳۲۹ و نقش آیت‌الله کاشانی و دکتر مصدق را بنویسید.",
            officialCorrectionKey = "۱. هدف: قطع دست استعمار انگلیس (شرکت نفت ایران و انگلیس) از منابع نفتی و تثبیت حاکمیت ملی ایران (۰.۷۵ نمره).\n۲. نتیجه: تصویب قانون ملی شدن صنعت نفت در ۲۹ اسفند ۱۳۲۹ با اتحاد رهبری مذهبی (آیت‌الله کاشانی) و سیاسی (دکتر مصدق) (۰.۷۵ نمره).",
            graderKeywords = listOf("۲۹ اسفند ۱۳۲۹", "قطع نفوذ انگلیس", "اتحاد آیت‌الله کاشانی و دکتر مصدق"),
            commonStudentTraps = "عدم ذکر تاریخ دقیق ۲۹ اسفند ۱۳۲۹ باعث کسر ۰.۲۵ نمره می‌شود."
        ),
        NahaeeDescriptiveQuestion(
            id = "nah_hist_1402_dey_3",
            subjectId = "history",
            subjectTitle = "تاریخ ۳ دوازدهم انسانی",
            gradeLevel = "دوازدهم",
            sessionYear = "امتحان نهایی دی ۱۴۰۲",
            examType = "دی",
            scorePoints = "۲.۰ نمره",
            questionText = "الف) معاهدات «گلستان» و «ترکمنچای» در زمان کدام شاه قاجار و با کدام کشور منعقد شد؟\nب) دو مورد از پیامدهای سنگین معاهده ترکمنچای را بنویسید.",
            officialCorrectionKey = "الف) در زمان فتحعلی‌شاه قاجار (۰.۵ نمره) با کشور روسیه تزاری (۰.۵ نمره).\nب) ۱. جدایی بخش‌های وسیعی از قفقاز و تعیین رود ارس به عنوان مرز ۲. تحمیل حق قضاوت کنسولی (کاپیتولاسیون) و غرامت سنگین جنگی (۱.۰ نمره).",
            graderKeywords = listOf("فتحعلی‌شاه قاجار", "روسیه تزاری", "مرز رود ارس", "کاپیتولاسیون"),
            commonStudentTraps = "عدم ذکر «حق قضاوت کنسولی (کاپیتولاسیون)» ۰.۵ نمره کسر دارد."
        ),
        NahaeeDescriptiveQuestion(
            id = "nah_hist_1401_khordad_4",
            subjectId = "history",
            subjectTitle = "تاریخ ۳ دوازدهم انسانی",
            gradeLevel = "دوازدهم",
            sessionYear = "امتحان نهایی خرداد ۱۴۰۱",
            examType = "خرداد",
            scorePoints = "۱.۵ نمره",
            questionText = "علت وقوع قیام ۱۵ خرداد ۱۳۴۲ چه بود و چرا این قیام «نقطه عطف نهضت اسلامی ایران» به شمار می‌رود؟",
            officialCorrectionKey = "۱. علت: سخنرانی افشاگرانه امام خمینی (ره) در روز عاشورا در مدرسه فیضیه قم علیه رژیم پهلوی و اسرائیل و دستگیری ایشان در سحرگاه ۱۵ خرداد (۰.۷۵ نمره).\n۲. اهمیت: جدایی کامل صف مردم از رژیم پهلوی و آغاز رسمی نهضت اسلامی به رهبری امام خمینی (ره) (۰.۷۵ نمره).",
            graderKeywords = listOf("سخنرانی عاشورا در مدرسه فیضیه", "دستگیری امام خمینی", "آغاز نهضت اسلامی"),
            commonStudentTraps = "اشتباه گرفتن قیام ۱۵ خرداد با ماجرای لایحه انجمن‌های ایالتی و ولایتی (مهر ۱۳۴۱) ۰.۵ نمره کسر دارد."
        ),

        // ==================== ۱۰. جغرافیا ۳ (کاربردی) دوازدهم ====================
        NahaeeDescriptiveQuestion(
            id = "nah_geo_1404_khordad_1",
            subjectId = "geography",
            subjectTitle = "جغرافیا ۳ کاربردی دوازدهم (ضریب ۴.۸۵)",
            gradeLevel = "دوازدهم",
            sessionYear = "امتحان نهایی خرداد ۱۴۰۴",
            examType = "خرداد",
            scorePoints = "۲.۰ نمره",
            questionText = "چهار مرحله اصلی «چرخه مدیریت بحران» در برابر مخاطرات طبیعی (مانند زلزله و سیل) را به ترتیب نام برده و هر یک را تعریف کنید.",
            officialCorrectionKey = "۱. پیشگیری و کاهش اثرات: مقاوم‌سازی ساختمان‌ها و رعایت حریم گسل و رودخانه پیش از وقوع (۰.۵ نمره)\n۲. آمادگی: آموزش همگانی، مانور و ذخیره اقلام امدادی پیش از وقوع (۰.۵ نمره)\n۳. مقابله و امداد و نجات: عملیات جستجو و نجات در حین و بلافاصله پس از وقوع (۰.۵ نمره)\n۴. بازسازی و بازتوانی: جبران خسارات و بازگرداندن شهر به حالت عادی پس از بحران (۰.۵ نمره).",
            graderKeywords = listOf("۱. پیشگیری", "۲. آمادگی", "۳. مقابله", "۴. بازسازی"),
            commonStudentTraps = "به‌هم زدن ترتیب ۴ مرحله مدیریت بحران ۰.۵ نمره کسر می‌کند."
        ),
        NahaeeDescriptiveQuestion(
            id = "nah_geo_1403_shahrivar_2",
            subjectId = "geography",
            subjectTitle = "جغرافیا ۳ کاربردی دوازدهم",
            gradeLevel = "دوازدهم",
            sessionYear = "امتحان نهایی شهریور ۱۴۰۳",
            examType = "شهریور",
            scorePoints = "۱.۵ نمره",
            questionText = "مفهوم «آمایش سرزمین» چیست و چه نقشی در توسعه متوازن و عدالت فضایی کشور دارد؟",
            officialCorrectionKey = "آمایش سرزمین یعنی تنظیم رابطه میان انسان، فضا و فعالیت‌های اقتصادی بر اساس توان اکولوژیک هر منطقه (۰.۷۵ نمره) که موجب توزیع عادلانه جمعیت و امکانات و جلوگیری از تمرکزگرایی در کلان‌شهرها می‌شود (۰.۷۵ نمره).",
            graderKeywords = listOf("انسان، فضا و فعالیت", "توان اکولوژیک", "توزیع متوازن جمعیت و امکانات"),
            commonStudentTraps = "حذف سه رکن «انسان، فضا و فعالیت» ۰.۵ نمره از تعریف کسر می‌کند."
        ),
        NahaeeDescriptiveQuestion(
            id = "nah_geo_1402_dey_3",
            subjectId = "geography",
            subjectTitle = "جغرافیا ۳ کاربردی دوازدهم",
            gradeLevel = "دوازدهم",
            sessionYear = "امتحان نهایی دی ۱۴۰۲",
            examType = "دی",
            scorePoints = "۱.۵ نمره",
            questionText = "تفاوت «سامانه اطلاعات جغرافیایی (GIS)» و «سنجش از دور (RS)» را در مدیریت شهری و مخاطرات بنویسید.",
            officialCorrectionKey = "۱. سنجش از دور (RS): کسب اطلاعات از سطح زمین بدون تماس مستقیم از طریق ماهواره‌ها و عکس‌های هوایی (۰.۷۵ نمره).\n۲. سامانه اطلاعات جغرافیایی (GIS): سیستم رایانه‌ای برای ورود، ذخیره‌سازی، لایه‌بندی و تحلیل داده‌های مکانی جهت تصمیم‌گیری (۰.۷۵ نمره).",
            graderKeywords = listOf("سنجش از دور بدون تماس با ماهواره", "GIS تحلیل لایه‌های مکانی"),
            commonStudentTraps = "جابه‌جا نوشتن تعریف GIS و RS باعث کسر کامل ۱.۵ نمره می‌شود."
        ),

        // ==================== ۱۱. هویت اجتماعی دوازدهم ====================
        NahaeeDescriptiveQuestion(
            id = "nah_identity_1404_khordad_1",
            subjectId = "social_identity",
            subjectTitle = "هویت اجتماعی دوازدهم (ضریب ۳.۶۴)",
            gradeLevel = "دوازدهم",
            sessionYear = "امتحان نهایی خرداد ۱۴۰۴",
            examType = "خرداد",
            scorePoints = "۱.۵ نمره",
            questionText = "تفاوت «ویژگی‌های انتسابی» و «ویژگی‌های اکتسابی» هویت را توضیح داده و برای هر کدام دو مثال بزنید.",
            officialCorrectionKey = "۱. انتسابی: ویژگی‌هایی که فرد در انتخاب آن‌ها نقشی ندارد و از بدو تولد به او داده می‌شود؛ مانند جنسیت، محل تولد و قومیت (۰.۷۵ نمره).\n۲. اکتسابی: ویژگی‌هایی که فرد با اراده، تلاش و کنش خود کسب می‌کند؛ مانند شغل، تحصیلات و مهارت معلمی (۰.۷۵ نمره).",
            graderKeywords = listOf("انتسابی (بدون اراده)", "اکتسابی (با تلاش و انتخاب)", "مثال شغل و تحصیلات"),
            commonStudentTraps = "نوشتن تعریف بدون ذکر مثال ۰.۵ نمره کسر دارد."
        ),
        NahaeeDescriptiveQuestion(
            id = "nah_identity_1403_shahrivar_2",
            subjectId = "social_identity",
            subjectTitle = "هویت اجتماعی دوازدهم",
            gradeLevel = "دوازدهم",
            sessionYear = "امتحان نهایی شهریور ۱۴۰۳",
            examType = "شهریور",
            scorePoints = "۱.۵ نمره",
            questionText = "منظور از «جامعه‌پذیری» و «تحرک اجتماعی» چیست و چه رابطه‌ای میان قشربندی اجتماعی و تحرک اجتماعی وجود دارد؟",
            officialCorrectionKey = "۱. جامعه‌پذیری: فرایندی که طی آن فرد هنجارها و ارزش‌های جامعه را یاد می‌گیرد تا عضو جامعه شود (۰.۵ نمره).\n۲. تحرک اجتماعی: جابه‌جایی افراد و گروه‌ها از یک پایگاه اجتماعی به پایگاه دیگر (صعودی یا نزولی، نسلی یا درون‌نسلی) در نظام قشربندی (۱.۰ نمره).",
            graderKeywords = listOf("یادگیری هنجارها و ارزش‌ها", "جابه‌جایی بین پایگاه‌های اجتماعی", "تحرک صعودی و نزولی"),
            commonStudentTraps = "عدم اشاره به مفهوم «پایگاه اجتماعی» در تعریف تحرک اجتماعی ۰.۵ نمره کسر دارد."
        ),
        NahaeeDescriptiveQuestion(
            id = "nah_identity_1402_dey_3",
            subjectId = "social_identity",
            subjectTitle = "هویت اجتماعی دوازدهم",
            gradeLevel = "دوازدهم",
            sessionYear = "امتحان نهایی دی ۱۴۰۲",
            examType = "دی",
            scorePoints = "۱.۵ نمره",
            questionText = "سه عنصر اصلی «هویت ایرانی-اسلامی» را نام ببرید و نقش زبان فارسی و تشیع را در یکپارچگی تاریخی ایران توضیح دهید.",
            officialCorrectionKey = "۱. عناصر اصلی: اسلام (به‌ویژه مذهب تشیع)، سرزمین ایران و تاریخ مشترک، زبان و ادبیات فارسی (۰.۷۵ نمره).\n۲. زبان فارسی و تشیع به عنوان محور پیوند اقوام گوناگون ایرانی، مانع تجزیه فرهنگی در برابر هجوم بیگانگان شده‌اند (۰.۷۵ نمره).",
            graderKeywords = listOf("اسلام و تشیع", "سرزمین و تاریخ ایران", "زبان و ادبیات فارسی"),
            commonStudentTraps = "حذف یکی از سه رکن (ایران، اسلام، زبان فارسی) ۰.۲۵ نمره کسر می‌کند."
        ),

        // ==================== ۱۲. سلامت و بهداشت دوازدهم ====================
        NahaeeDescriptiveQuestion(
            id = "nah_health_1404_khordad_1",
            subjectId = "general_humanities",
            subjectTitle = "سلامت و بهداشت دوازدهم (ضریب ۳.۶۴)",
            gradeLevel = "دوازدهم",
            sessionYear = "امتحان نهایی خرداد ۱۴۰۴",
            examType = "خرداد",
            scorePoints = "۱.۵ نمره",
            questionText = "الف) چهار بُعد اصلی سلامت از دیدگاه سازمان جهانی بهداشت (WHO) را نام ببرید.\nب) فردی با وزن ۷۲ کیلوگرم و قد ۲ متر چه شاخص توده بدنی (BMI) دارد؟",
            officialCorrectionKey = "الف) ابعاد چهارگانه سلامت: ۱. سلامت جسمی ۲. سلامت روانی ۳. سلامت اجتماعی ۴. سلامت معنوی (۱.۰ نمره).\nب) BMI = 72 / (2 × 2) = 72 / 4 = 18 (۰.۵ نمره).",
            graderKeywords = listOf("جسمی، روانی، اجتماعی، معنوی", "فرمول BMI = وزن تقسیم بر مجذور قد", "BMI = 18"),
            commonStudentTraps = "تقسیم کردن وزن بر قد (به جای مجذور قد) خطای پرتکرار محاسباتی در امتحان نهایی است."
        ),
        NahaeeDescriptiveQuestion(
            id = "nah_health_1403_shahrivar_2",
            subjectId = "general_humanities",
            subjectTitle = "سلامت و بهداشت دوازدهم",
            gradeLevel = "دوازدهم",
            sessionYear = "امتحان نهایی شهریور ۱۴۰۳",
            examType = "شهریور",
            scorePoints = "۱.۵ نمره",
            questionText = "تفاوت «بیماری‌های واگیر» و «بیماری‌های غیرواگیر» را نوشته و چهار عامل خطر اصلی بیماری‌های غیرواگیر (مانند دیابت و قلبی-عروقی) را نام ببرید.",
            officialCorrectionKey = "۱. تفاوت: بیماری واگیر توسط عوامل بیماری‌زا (ویروس، باکتری) از فردی به فرد دیگر منتقل می‌شود اما بیماری غیرواگیر عامل عفونی ندارد و وابسته به سبک زندگی است (۰.۵ نمره).\n۲. چهار عامل خطر: تغذیه ناسالم، کم‌تحرکی، مصرف دخانیات، و استرس مداوم (هر مورد ۰.۲۵ = ۱.۰ نمره).",
            graderKeywords = listOf("وابسته به سبک زندگی", "تغذیه ناسالم", "کم‌تحرکی", "مصرف دخانیات"),
            commonStudentTraps = "نوشتن نام بیماری‌ها به جای «عوامل خطر» باعث از دست رفتن ۱ نمره می‌شود."
        ),
        NahaeeDescriptiveQuestion(
            id = "nah_health_1402_dey_3",
            subjectId = "general_humanities",
            subjectTitle = "سلامت و بهداشت دوازدهم",
            gradeLevel = "دوازدهم",
            sessionYear = "امتحان نهایی دی ۱۴۰۲",
            examType = "دی",
            scorePoints = "۱.۵ نمره",
            questionText = "در جدول اطلاعات تغذیه‌ای روی بسته‌بندی مواد غذایی (چراغ راهنمای تغذیه)، رنگ‌های قرمز، زرد و سبز نشانه چیست و کدام اسید چرب بیشترین خطر قلبی را دارد؟",
            officialCorrectionKey = "۱. سبز: مقدار کم و سالم | زرد: مقدار متوسط (مصرف متعادل) | قرمز: مقدار زیاد (هشدار محدودیت مصرف قند، نمک یا چربی) (۰.۷۵ نمره).\n۲. اسید چرب ترانس (Trans Fat) خطرناک‌ترین نوع چربی است که کلسترول بد (LDL) را بالا و کلسترول خوب (HDL) را پایین می‌آورد (۰.۷۵ نمره).",
            graderKeywords = listOf("چراغ راهنمای تغذیه", "اسید چرب ترانس", "افزایش LDL و کاهش HDL"),
            commonStudentTraps = "اشتباه گرفتن کلسترول LDL (بد) و HDL (خوب) ۰.۵ نمره کسر دارد."
        ),

        // ==================== ۱۳. روان‌شناسی، اقتصاد و منطق ====================
        NahaeeDescriptiveQuestion(
            id = "nah_psych_1404_khordad_1",
            subjectId = "psychology",
            subjectTitle = "روان‌شناسی یازدهم انسانی",
            gradeLevel = "یازدهم و نهایی",
            sessionYear = "امتحان نهایی خرداد ۱۴۰۴",
            examType = "خرداد",
            scorePoints = "۱.۵ نمره",
            questionText = "تفاوت «حافظه کوتاه‌مدت (کاری)» و «حافظه بلندمدت» را از نظر ظرفیت، مدت نگهداری و نوع رمزگردانی مقایسه کنید.",
            officialCorrectionKey = "۱. حافظه کوتاه‌مدت: ظرفیت محدود (۷ ± ۲ ماده)، مدت نگهداری حدود ۱۵ تا ۳۰ ثانیه، رمزگردانی عمدتاً دیداری و شنیداری (۰.۷۵ نمره).\n۲. حافظه بلندمدت: ظرفیت نامحدود، مدت نگهداری تا پایان عمر، رمزگردانی عمدتاً معنایی (۰.۷۵ نمره).",
            graderKeywords = listOf("ظرفیت ۷ ± ۲", "رمزگردانی معنایی", "حافظه کاری و بلندمدت"),
            commonStudentTraps = "نوشتن رمزگردانی معنایی برای حافظه کوتاه‌مدت ۰.۵ نمره کسر می‌کند."
        ),
        NahaeeDescriptiveQuestion(
            id = "nah_psych_1403_shahrivar_2",
            subjectId = "psychology",
            subjectTitle = "روان‌شناسی یازدهم انسانی",
            gradeLevel = "یازدهم و نهایی",
            sessionYear = "امتحان نهایی شهریور ۱۴۰۳",
            examType = "شهریور",
            scorePoints = "۱.۵ نمره",
            questionText = "تفاوت «توجه ارادی» و «توجه غیرارادی» چیست و چهار مرحله حل مسئله را به ترتیب بنویسید.",
            officialCorrectionKey = "۱. توجه ارادی با قصد و تمرکز آگاهانه فرد انجام می‌شود (مثل درس خواندن)، اما توجه غیرارادی بر اثر ویژگی‌های محرک بیرونی مثل صدای بلند ناگهانی رخ می‌دهد (۰.۵ نمره).\n۲. مراحل حل مسئله: ۱. تشخیص و تعریف مسئله ۲. تولید راه‌حل‌ها ۳. ارزیابی و انتخاب بهترین راه‌حل ۴. اجرا و بازنگری (۱.۰ نمره).",
            graderKeywords = listOf("توجه ارادی و غیرارادی", "تعریف مسئله", "تولید و انتخاب راه‌حل", "اجرا و بازنگری"),
            commonStudentTraps = "رعایت نکردن ترتیب مراحل حل مسئله ۰.۵ نمره کسر دارد."
        ),
        NahaeeDescriptiveQuestion(
            id = "nah_econ_1404_khordad_1",
            subjectId = "economics",
            subjectTitle = "اقتصاد دهم انسانی",
            gradeLevel = "دهم و نهایی",
            sessionYear = "امتحان نهایی خرداد ۱۴۰۴",
            examType = "خرداد",
            scorePoints = "۲.۰ نمره",
            questionText = "کارگاهی ماهانه ۵۰ میلیون تومان درآمد دارد، ۲۰ میلیون تومان هزینه آشکار (مواد اولیه و دستمزد) می‌پردازد و صاحب کارگاه شغل قبلی خود با حقوق ۱۲ میلیون تومان را رها کرده است. «سود حسابداری» و «سود اقتصادی» را محاسبه کنید.",
            officialCorrectionKey = "سود حسابداری = 50 - 20 = 30 | هزینه کل اقتصادی = 20 + 12 = 32 | سود اقتصادی = 50 - 32 = 18",
            graderKeywords = listOf("سود حسابداری = ۳۰ میلیون", "هزینه ضمنی (فرصت) = ۱۲ میلیون", "سود اقتصادی = ۱۸ میلیون"),
            commonStudentTraps = "کم نکردن هزینه فرصت (۱۲ میلیون) در محاسبه سود اقتصادی ۰.۷۵ نمره کسر دارد."
        ),
        NahaeeDescriptiveQuestion(
            id = "nah_econ_1403_shahrivar_2",
            subjectId = "economics",
            subjectTitle = "اقتصاد دهم انسانی",
            gradeLevel = "دهم و نهایی",
            sessionYear = "امتحان نهایی شهریور ۱۴۰۳",
            examType = "شهریور",
            scorePoints = "۱.۷۵ نمره",
            questionText = "در بازار یک کالا، اگر قیمت بالاتر از قیمت تعادلی تعیین شود، چه پدیده‌ای در بازار رخ می‌دهد و مکانیسم بازار چگونه تعادل را بازمی‌گرداند؟",
            officialCorrectionKey = "۱. در قیمت بالاتر از تعادل، مقدار عرضه بیشتر از مقدار تقاضا شده و «مازاد عرضه (فزونی عرضه)» رخ می‌دهد (۰.۷۵ نمره).\n۲. تولیدکنندگان برای فروش کالاهای انبارشده، با یکدیگر رقابت کرده و قیمت را کاهش می‌دهند تا بازار دوباره به نقطه تعادل (برابری عرضه و تقاضا) برسد (۱.۰ نمره).",
            graderKeywords = listOf("مازاد عرضه", "بیشتر بودن عرضه از تقاضا", "رقابت فروشندگان و کاهش قیمت"),
            commonStudentTraps = "اشتباه گرفتن مازاد عرضه با مازاد تقاضا (کمبود) ۱ نمره کامل کسر می‌کند."
        ),
        NahaeeDescriptiveQuestion(
            id = "nah_logic_1404_khordad_1",
            subjectId = "logic",
            subjectTitle = "منطق دهم انسانی",
            gradeLevel = "دهم و نهایی",
            sessionYear = "امتحان نهایی خرداد ۱۴۰۴",
            examType = "خرداد",
            scorePoints = "۱.۵ نمره",
            questionText = "در مربع تقابل قضایا، اگر قضیه «هر انسانی ناطق است» (موجبه کلی) صادق باشد، حکم صدق و کذب سه قضیه متقابل آن (سالبه کلی، موجبه جزئی، سالبه جزئی) را بنویسید.",
            officialCorrectionKey = "۱. سالبه کلی (هیچ انسانی ناطق نیست): متضاد ⟵ کاذب قطعی (۰.۵ نمره)\n۲. سالبه جزئی (بعضی انسان‌ها ناطق نیستند): متناقض ⟵ کاذب قطعی (۰.۵ نمره)\n۳. موجبه جزئی (بعضی انسان‌ها ناطق‌اند): تداخل ⟵ صادق قطعی (۰.۵ نمره).",
            graderKeywords = listOf("تضاد (کاذب)", "تناقض (کاذب)", "تداخل (صادق)"),
            commonStudentTraps = "نامعلوم نوشتن حکم سالبه کلی در هنگام صدق موجبه کلی ۰.۵ نمره کسر دارد."
        ),
        NahaeeDescriptiveQuestion(
            id = "nah_logic_1403_shahrivar_2",
            subjectId = "logic",
            subjectTitle = "منطق دهم انسانی",
            gradeLevel = "دهم و نهایی",
            sessionYear = "امتحان نهایی شهریور ۱۴۰۳",
            examType = "شهریور",
            scorePoints = "۱.۵ نمره",
            questionText = "رابطه میان دو مفهوم «انسان و حیوان» و دو مفهوم «فلز و طلا» از میان نسب چهارگانه (تساوی، تباین، عموم و خصوص مطلق، عموم و خصوص من‌وجه) چیست؟ تعریف حد تام «انسان» را بنویسید.",
            officialCorrectionKey = "۱. رابطه انسان و حیوان: عموم و خصوص مطلق (هر انسانی حیوان است ولی هر حیوانی انسان نیست) (۰.۵ نمره).\n۲. رابطه فلز و طلا: عموم و خصوص مطلق (هر طلایی فلز است) (۰.۵ نمره).\n۳. حد تام انسان: حیوان (جنس قریب) + ناطق (فصل قریب) = حیوان ناطق (۰.۵ نمره).",
            graderKeywords = listOf("عموم و خصوص مطلق", "جنس قریب + فصل قریب", "حیوان ناطق"),
            commonStudentTraps = "نوشتن عموم و خصوص من‌وجه به جای مطلق ۰.۵ نمره کسر دارد."
        )
    )

    /**
     * مشخصات ضریب و پیش‌فرض نمره هر درس برای ماشین‌حساب هوشمند تراز ۶۰٪ قطعی امتحانات نهایی و اولویت‌بندی ترمیم معدل
     */
    data class NahaeeSubjectTarazSpec(
        val subjectId: String,
        val title: String,
        val coefficient: Double,
        val defaultScore: Double,
        val tarazPerOneScoreGain: Int // هر ۱ نمره افزایش در این درس چند واحد به تراز کل ۶۰٪ اضافه می‌کند؟
    )

    val tarazCalculatorSpecs = listOf(
        NahaeeSubjectTarazSpec("farsi", "فارسی ۳ (عمومی)", 18.64, 16.5, 295),
        NahaeeSubjectTarazSpec("dini", "دین و زندگی ۳ (عمومی)", 14.23, 17.5, 225),
        NahaeeSubjectTarazSpec("fonoon", "علوم و فنون ادبی ۳ (تخصصی)", 10.43, 15.5, 185),
        NahaeeSubjectTarazSpec("english", "زبان انگلیسی ۳ (عمومی)", 10.17, 14.0, 175),
        NahaeeSubjectTarazSpec("arabic", "عربی ۳ زبان قرآن (تخصصی)", 7.80, 16.0, 140),
        NahaeeSubjectTarazSpec("philosophy", "فلسفه ۲ دوازدهم (تخصصی)", 5.62, 15.0, 110),
        NahaeeSubjectTarazSpec("sociology", "جامعه‌شناسی ۳ (تخصصی)", 5.52, 17.0, 100),
        NahaeeSubjectTarazSpec("history", "تاریخ ۳ دوازدهم (تخصصی)", 5.40, 16.5, 95),
        NahaeeSubjectTarazSpec("math", "ریاضی و آمار ۳ (تخصصی)", 5.16, 14.5, 98),
        NahaeeSubjectTarazSpec("geography", "جغرافیا ۳ کاربردی (تخصصی)", 4.85, 17.0, 85),
        NahaeeSubjectTarazSpec("social_identity", "هویت اجتماعی (عمومی)", 3.64, 18.0, 62),
        NahaeeSubjectTarazSpec("general_humanities", "سلامت و بهداشت (عمومی)", 3.64, 18.5, 60)
    )

    /**
     * بسته طلایی جمع‌بندی شب امتحان (چک‌لیست سوالات ۱۰۰٪ قطعی فردا صبح + فرمول‌ها و کلیدواژه‌های ۲۰ساز)
     */
    data class NahaeeNightBeforeSummary(
        val subjectId: String,
        val subjectTitle: String,
        val coefficientBadge: String,
        val guaranteedQuestionTypes: List<String>,
        val goldenFormulaOrKeywords: String,
        val nightBeforeChecklist: String
    )

    val nightBeforeSummaries = listOf(
        NahaeeNightBeforeSummary(
            subjectId = "farsi",
            subjectTitle = "فارسی ۳ دوازدهم — بسته ۲۰ قطعی شب امتحان",
            coefficientBadge = "ضریب ۱۸.۶۴ (رتبه‌ساز شماره ۱)",
            guaranteedQuestionTypes = listOf(
                "۱. معنی واژگان و املا (۴ نمره): واژه‌نامه آخر کتاب + کلمات هم‌آوا (خوار/خار، فترت/فطرت، صواب/ثواب)",
                "۲. دستور زبان (۵ نمره): تشخیص وابسته‌های پیشین و پسین، نقش تبعی (معطوف، بدل، تکرار)، و حذف فعل به قرینه لفظی/معنوی",
                "۳. آرایه‌های ادبی (۴ نمره): تشخیص اسلوب معادله، حس‌آمیزی، ایهام، مجاز، استعاره و کنایه",
                "۴. قلمرو فکری و معنی ابیات (۵ نمره) + حفظ شعر (۲ نمره)"
            ),
            goldenFormulaOrKeywords = "فرمول تشخیص اسلوب معادله: دو مصراع مستقل از نظر دستوری که می‌توان بین آن‌ها عبارت «همانند آن است که» گذاشت و مصراع دوم مصداق عینی مصراع اول است.",
            nightBeforeChecklist = "✅ واژه‌نامه پایان کتاب (صفحه ۱۷۲ تا آخر) را ۱۰ دقیقه ورق بزنید (۲ نمره مستقیم).\n✅ جدول وابسته‌های مضاف‌الیه و صفت بیانی را مرور کنید.\n✅ ابیات حفظی ستاره‌دار را از رو بخوانید."
        ),
        NahaeeNightBeforeSummary(
            subjectId = "dini",
            subjectTitle = "دین و زندگی ۳ دوازدهم — بسته ۲۰ قطعی شب امتحان",
            coefficientBadge = "ضریب ۱۴.۲۳ (دومین ضریب بزرگ)",
            guaranteedQuestionTypes = listOf(
                "۱. پیام آیات و ارتباط آیه با موضوع درس (۶ نمره): دروس ۱ تا ۱۰",
                "۲. مراتب توحید (ذاتی، صفاتی، افعالی، عبادی و ربوبی) (۳ نمره قطعی)",
                "۳. سنت‌های الهی (امداد عام، امداد خاص، استدراج، املاء، ابتلا و سبقت رحمت بر غضب) (۳.۵ نمره قطعی)",
                "۴. شرایط توبه، اخلاص، عدل الهی و ولایت فقیه (۵.۵ نمره) + تکمیل آیات حفظی (۲ نمره)"
            ),
            goldenFormulaOrKeywords = "کلید طلایی سنت‌ها: کُلًّا نُمِدُّ = امداد عام (مؤمن و کافر) | إِنْ تَنْصُرُوا اللَّهَ = امداد خاص (فقط مؤمنان) | سَنَسْتَدْرِجُهُمْ = سنت استدراج (غفلت در نعمت).",
            nightBeforeChecklist = "✅ تیترهای شماره‌دار هر درس را بدون تغییر کلمات حفظ کنید.\n✅ جدول سنت‌های الهی درس ۸ را کامل مرور کنید.\n✅ پاسخ فعالیت‌های «اندیشه و تحقیق» پایان هر درس را بخوانید (۴ نمره مستقیم)."
        ),
        NahaeeNightBeforeSummary(
            subjectId = "fonoon",
            subjectTitle = "علوم و فنون ادبی ۳ — بسته ۲۰ قطعی شب امتحان",
            coefficientBadge = "ضریب ۱۰.۴۳ (بلندترین ضریب تخصصی)",
            guaranteedQuestionTypes = listOf(
                "۱. تقطیع هجایی و تعیین وزن و بحر یک بیت (۳.۵ نمره قطعی)",
                "۲. اختیارات شاعری وزنی (ابدال، بلند بودن هجای پایانی) و زبانی (حذف همزه، تغییر کمیت مصوت‌ها، تسکین) (۱.۵ نمره)",
                "۳. تاریخ ادبیات و سبک‌شناسی (قرن ۱۲ و ۱۳ بازگشت، مشروطه، معاصر و نیمایی) (۴ نمره)",
                "۴. آرایه‌های ایهام، ایهام تناسب، اغراق، حسن تعلیل، لف و نشر و تضاد (۵ نمره) + نقد ادبی (۶ نمره)"
            ),
            goldenFormulaOrKeywords = "تفاوت ایهام و ایهام تناسب: در «ایهام» هر دو معنی واژه با کل بیت سازگار و مقصود شاعر است؛ در «ایهام تناسب» فقط یک معنی مقصود است و معنی دوم صرفاً با کلمه‌ای دیگر در بیت مراعات نظیر دارد.",
            nightBeforeChecklist = "✅ جدول اوزان همسان و دولختی را مرور کنید.\n✅ آثار نویسندگان دوره مشروطه و معاصر را دوره کنید.\n✅ در تقطیع هجایی بالای هر هجا علامت U و - را واضح بگذارید."
        ),
        NahaeeNightBeforeSummary(
            subjectId = "english",
            subjectTitle = "زبان انگلیسی ۳ (Vision 3) — بسته ۲۰ قطعی شب امتحان",
            coefficientBadge = "ضریب ۱۰.۱۷",
            guaranteedQuestionTypes = listOf(
                "۱. گرامر درس ۱: جملات مجهول (Passive Voice) و دنباله پرسشی (Tag Questions) (۲ نمره قطعی)",
                "۲. گرامر درس ۲: موصول‌ها (who, whom, which, that) و شرطی نوع دوم (If + Past Simple -> would + V) (۱.۵ نمره)",
                "۳. گرامر درس ۳: حروف ربط هم‌پایه‌ساز (and, but, or, so) (۱ نمره)",
                "۴. نگارش پاراگراف (Topic Sentence, Supporting Sentences, Concluding Sentence) (۴ نمره)"
            ),
            goldenFormulaOrKeywords = "فرمول طلایی مجهول: Object + am/is/are/was/were/have been/will be + P.P. (شکل سوم فعل). در Tag Question کلمات never, hardly, seldom, little, nobody جمله را منفی می‌کنند پس دنباله پرسشی باید مثبت باشد!",
            nightBeforeChecklist = "✅ جدول افعال بی‌قاعده آخر کتاب را مرور کنید.\n✅ فرمول نوشتن Topic Sentence را برای موضوعات پرتکرار (Parents, Dictionary, Clean Energy) تمرین کنید."
        ),
        NahaeeNightBeforeSummary(
            subjectId = "arabic",
            subjectTitle = "عربی ۳ زبان قرآن — بسته ۲۰ قطعی شب امتحان",
            coefficientBadge = "ضریب ۷.۸۰",
            guaranteedQuestionTypes = listOf(
                "۱. ترجمه دقیق جملات کتاب با رعایت حال، مفعول مطلق و اسلوب استثناء (۷ نمره)",
                "۲. تشخیص «حال مفرد و جمله حالیه» و «صاحب حال» (۲ نمره قطعی)",
                "۳. تشخیص «مفعول مطلق تأکیدی، نوعی و عددی» (۲ نمره قطعی)",
                "۴. تشخیص «اسلوب استثناء تام و مفرّغ» و اعراب کلمه بعد از إلّا (۲ نمره) + تحلیل صرفی و درک متن (۷ نمره)"
            ),
            goldenFormulaOrKeywords = "فرمول مفعول مطلق: اگر بعد از مصدر هم‌ریشه فعل هیچ صفت یا مضاف‌الیهی نیامد = تأکیدی (ترجمه با قید حتماً/بسیار)؛ اگر صفت یا مضاف‌الیه آمد = نوعی (بیانی).",
            nightBeforeChecklist = "✅ مترادف و متضادهای واژه‌نامه ۴ درس را مرور کنید (۳ نمره ساده).\n✅ تمرین‌های «اعلموا» و «تمارین» هر ۴ درس را خط به خط چک کنید (۹۰٪ سوالات عیناً از تمارین است)."
        ),
        NahaeeNightBeforeSummary(
            subjectId = "math",
            subjectTitle = "ریاضی و آمار ۳ — بسته ۲۰ قطعی شب امتحان",
            coefficientBadge = "ضریب ۵.۱۶",
            guaranteedQuestionTypes = listOf(
                "۱. فصل ۱ (۸ نمره): چرخه آمار، اصل ضرب و ترکیب C(n,r)، پیشامدهای مستقل و احتمال شرطی P(A|B)",
                "۲. فصل ۲ (۶ نمره): معادله خط روند سری زمانی، درون‌یابی و برون‌یابی، و رشد نمایی (سود مرکب)",
                "۳. فصل ۳ (۶ نمره): نقیض گزاره‌های سوردار (∀ و ∃)، عکس و contrapositive (عکس نقیض) ترکیب شرطی، و استدلال ریاضی"
            ),
            goldenFormulaOrKeywords = "خط روند: m = (y₂ - y₁) / (x₂ - x₁) و y - y₁ = m(x - x₁) | پیشامد مستقل: P(A ∩ B) = P(A) × P(B) | نقیض (∀x, P(x))  ≡ (∃x, ~P(x)).",
            nightBeforeChecklist = "✅ نوشتن فرمول خام در هر سوال ۰.۵ نمره مستقل دارد؛ حتی اگر محاسبات را کامل نکردید فرمول را بنویسید!\n✅ مسائل درون‌یابی و برون‌یابی سری زمانی را تمرین کنید."
        ),
        NahaeeNightBeforeSummary(
            subjectId = "philosophy",
            subjectTitle = "فلسفه ۲ دوازدهم — بسته ۲۰ قطعی شب امتحان",
            coefficientBadge = "ضریب ۵.۶۲",
            guaranteedQuestionTypes = listOf(
                "۱. تمایز وجود و ماهیت، اصالت وجود و اعتباریت ماهیت، و تشکیک در وجود (۴ نمره)",
                "۲. اقسام علت (فاعلی، مادی، صوری، غایی) و اصل سنخیت و ضرورت علّی (۴ نمره)",
                "۳. معرفت‌شناسی: دیدگاه شکاکان، دکارت، کانت، سهروردی (علم حضوری و حصولی) (۶ نمره)",
                "۴. حکمت متعالیه ملاصدرا و حرکت جوهری (۶ نمره)"
            ),
            goldenFormulaOrKeywords = "علم حضوری = حضور خود معلوم نزد عالم بدون واسطه صورت ذهنی (خطاناپذیر، مثل علم انسان به خود و حالات درونی) | علم حصولی = با واسطه مفهوم ذهنی (احتمال خطا دارد).",
            nightBeforeChecklist = "✅ جدول مقایسه مشاء (ابن‌سینا)، اشراق (سهروردی) و حکمت متعالیه (ملاصدرا) را مرور کنید."
        ),
        NahaeeNightBeforeSummary(
            subjectId = "sociology",
            subjectTitle = "جامعه‌شناسی ۳ دوازدهم — بسته ۲۰ قطعی شب امتحان",
            coefficientBadge = "ضریب ۵.۵۲",
            guaranteedQuestionTypes = listOf(
                "۱. مقایسه سه رویکرد تبیینی (اثباتی)، تفسیری و انتقادی از نظر هدف، روش و نگاه به ارزش‌ها (۵ نمره قطعی)",
                "۲. کنش اجتماعی، پیامدهای ارادی/غیرارادی و پدیده‌های اجتماعی (۴ نمره)",
                "۳. فرهنگ آرمانی و واقعی، حق و باطل، و ازخودبیگانگی فرهنگی (۵ نمره)",
                "۴. هویت فرهنگی، جهانی‌شدن و بیداری اسلامی (۶ نمره)"
            ),
            goldenFormulaOrKeywords = "جدول طلایی ۳ رویکرد: تبیینی (کشف قوانین علّی، کمی، حفظ وضع موجود) | تفسیری (فهم معنای ذهنی، کیفی، توصیف فرهنگ‌ها) | انتقادی (کشف سلطه، رهایی و تغییر وضع موجود).",
            nightBeforeChecklist = "✅ جدول مقایسه ۳ رویکرد جامعه‌شناسی را ۱۰۰٪ حفظ کنید (سوال ثابت هر نوبت امتحان نهایی)."
        ),
        NahaeeNightBeforeSummary(
            subjectId = "history",
            subjectTitle = "تاریخ ۳ دوازدهم — بسته ۲۰ قطعی شب امتحان",
            coefficientBadge = "ضریب ۵.۴۰",
            guaranteedQuestionTypes = listOf(
                "۱. منابع تاریخ معاصر و آرشیوها (۳.۵ نمره)",
                "۲. جنگ‌های ایران و روس، معاهدات گلستان و ترکمنچای، امتیازات استعماری (دارسی، رویتر، تالبوت) و نهضت مشروطه (۵.۵ نمره)",
                "۳. کودتای سوم اسفند ۱۲۹۹، اقدامات رضاشاه، نهضت ملی شدن نفت و کودتای ۲۸ مرداد ۱۳۳۲ (۶ نمره)",
                "۴. قیام ۱۵ خرداد ۱۳۴۲، پیروزی انقلاب اسلامی و دفاع مقدس (۵ نمره)"
            ),
            goldenFormulaOrKeywords = "امتیاز تالبوت (تنباکو) ⟵ فتوای تحریم میرزای شیرازی | ملی شدن نفت (۲۹ اسفند ۱۳۲۹) ⟵ آیت‌الله کاشانی و دکتر مصدق | کودتای ۱۲۹۹ ⟵ رضاخان و سیدضیاءالدین طباطبایی.",
            nightBeforeChecklist = "✅ خط زمانی وقایع مهم قاجار و پهلوی را به ترتیب سال مرور کنید."
        ),
        NahaeeNightBeforeSummary(
            subjectId = "geography",
            subjectTitle = "جغرافیا ۳ کاربردی — بسته ۲۰ قطعی شب امتحان",
            coefficientBadge = "ضریب ۴.۸۵",
            guaranteedQuestionTypes = listOf(
                "۱. تعریف آمایش سرزمین، سه رکن (انسان، فضا، فعالیت) و شاخص‌های توسعه (۵ نمره)",
                "۲. شهرهای هوشمند، حمل‌ونقل پایدار و مزایای حمل‌ونقل ریلی (۵ نمره)",
                "۳. چرخه مدیریت بحران (پیشگیری، آمادگی، مقابله، بازسازی) و مخاطرات زلزله و سیل (۵.۵ نمره)",
                "۴. ژئوپلیتیک، مرزهای سیاسی و موقعیت استراتژیک ایران و خلیج فارس (۴.۵ نمره)"
            ),
            goldenFormulaOrKeywords = "۴ مرحله مدیریت بحران به ترتیب: ۱. پیشگیری ۲. آمادگی (قبل از بحران) ⟵ ۳. مقابله و امداد ⟵ ۴. بازسازی و بازتوانی (بعد از بحران).",
            nightBeforeChecklist = "✅ تفاوت GIS و RS و ۴ مرحله چرخه مدیریت بحران را حتماً حفظ کنید."
        ),
        NahaeeNightBeforeSummary(
            subjectId = "social_identity",
            subjectTitle = "هویت اجتماعی دوازدهم — بسته ۲۰ قطعی شب امتحان",
            coefficientBadge = "ضریب ۳.۶۴",
            guaranteedQuestionTypes = listOf(
                "۱. ویژگی‌های کنش اجتماعی و تفاوت آن با رفتار غیرارادی (۴ نمره)",
                "۲. هویت فردی و اجتماعی، ویژگی‌های انتسابی و اکتسابی با مثال (۵ نمره)",
                "۳. جامعه‌پذیری، قشربندی و تحرک اجتماعی صعودی/نزولی (۵ نمره)",
                "۴. ارکان هویت ایرانی-اسلامی (اسلام، ایران، زبان فارسی) و جهانی‌شدن (۶ نمره)"
            ),
            goldenFormulaOrKeywords = "انتسابی = بدون اراده از بدو تولد (جنسیت، قومیت) | اکتسابی = با تلاش و انتخاب (شغل، تحصیلات) | تحرک اجتماعی = جابه‌جایی بین پایگاه‌های اجتماعی.",
            nightBeforeChecklist = "✅ جدول مقایسه ویژگی‌های انتسابی و اکتسابی و سه عنصر هویت ایرانی-اسلامی را مرور کنید."
        ),
        NahaeeNightBeforeSummary(
            subjectId = "general_humanities",
            subjectTitle = "سلامت و بهداشت دوازدهم — بسته ۲۰ قطعی شب امتحان",
            coefficientBadge = "ضریب ۳.۶۴",
            guaranteedQuestionTypes = listOf(
                "۱. ابعاد چهارگانه سلامت (جسمی، روانی، اجتماعی، معنوی) از نظر WHO (۳.۵ نمره)",
                "۲. تغذیه سالم، چراغ راهنمای تغذیه، اسید چرب ترانس و محاسبه فرمول BMI (۴.۵ نمره)",
                "۳. پیشگیری از بیماری‌های غیرواگیر (دیابت، قلبی، سرطان) و واگیر (هپاتیت، ایدز، آنفولانزا) (۶ نمره)",
                "۴. بهداشت روان، مهارت نه گفتن، پیشگیری از اعتیاد و کمک‌های اولیه (۶ نمره)"
            ),
            goldenFormulaOrKeywords = "فرمول قطعی امتحان: BMI = وزن (کیلوگرم) ÷ مجذور قد (متر مربع). بازه طبیعی BMI بین ۱۸.۵ تا ۲۴.۹ است.",
            nightBeforeChecklist = "✅ فرمول BMI و چهار عامل خطر بیماری‌های غیرواگیر (تغذیه ناسالم، کم‌تحرکی، دخانیات، استرس) را مرور کنید."
        )
    )

    /**
     * مدل دفترچه کامل ۲۰ نمره‌ای امتحان نهایی رسمی (شامل تمام ردیف‌های سوال از سوال ۱ تا آخر جمعاً ۲۰ نمره کامل)
     */
    data class NahaeeSheetRowItem(
        val rowNumber: Int,
        val sectionTitle: String,
        val scoreBaram: String,
        val questionPrompt: String,
        val officialAnswerKey: String
    )

    data class NahaeeFullExamBooklet(
        val id: String,
        val subjectId: String,
        val subjectTitle: String,
        val sessionTitle: String, // "نوبت خرداد ۱۴۰۴", "نوبت شهریور ۱۴۰۴", "نوبت دی ۱۴۰۳"
        val durationMinutes: Int,
        val totalScore: String = "۲۰.۰ نمره کامل",
        val coefficientBadge: String,
        val sheetRows: List<NahaeeSheetRowItem>
    )

    val fullExamBooklets = listOf(
        // ۱. دفترچه کامل ۲۰ نمره‌ای فارسی ۳ دوازدهم
        NahaeeFullExamBooklet(
            id = "full_nah_farsi_1404_khordad",
            subjectId = "farsi",
            subjectTitle = "دفترچه کامل امتحان نهایی فارسی ۳ دوازدهم",
            sessionTitle = "نوبت خرداد ۱۴۰۴ (سراسری کشوری)",
            durationMinutes = 120,
            coefficientBadge = "ضریب ۱۸.۶۴ (رتبه‌ساز شماره ۱)",
            sheetRows = listOf(
                NahaeeSheetRowItem(
                    rowNumber = 1,
                    sectionTitle = "قلمرو زبانی — معنی واژگان (۲ نمره)",
                    scoreBaram = "۲.۰ نمره",
                    questionPrompt = "معنی واژه‌های مشخص‌شده را در عبارات زیر بنویسید:\nالف) در «استسقا» بیمار آب بسیار خواهد. (۰.۵)\nب) دل در «طمأنینه» و آرامش الهی قرار گرفت. (۰.۵)\nپ) «تفتن» و فتنه‌انگیزی کار دون‌همتان است. (۰.۵)\nت) از «منافرت» و بیزاری میان یاران پرهیز کنید. (۰.۵)",
                    officialAnswerKey = "الف) استسقا: نام بیماری که بیمار عطش بسیار دارد (۰.۵) | ب) طمأنینه: آرامش و سکون و اطمینان (۰.۵) | پ) تفتن: فتنه‌انگیزی و دسیسه (۰.۵) | ت) منافرت: بیزاری، نفرت و دوری جستن از یکدیگر (۰.۵)."
                ),
                NahaeeSheetRowItem(
                    rowNumber = 2,
                    sectionTitle = "قلمرو زبانی — املا و واژگان هم‌آوا (۲ نمره)",
                    scoreBaram = "۲.۰ نمره",
                    questionPrompt = "الف) املای صحیح واژه مناسب را داخل پرانتز انتخاب کنید:\n۱. او از (حول / هول) و هراسِ روز رستاخیز گریست. (۰.۵)\n۲. در دوران (فترت / فطرت) وحی برای مدتی قطع شد. (۰.۵)\nب) دو غلط املایی در عبارت زیر بیابید و صحیح آن را بنویسید:\n«رستم بر رخش صوار شد و به سوی دشت خوارزم تاخط.» (۱.۰)",
                    officialAnswerKey = "الف) ۱. هول (ترس و هراس) (۰.۵) | ۲. فترت (فاصله زمانی میان دو پیامبر) (۰.۵).\nب) صوار ⟵ سوار (۰.۵) | تاخط ⟵ تاخت (۰.۵)."
                ),
                NahaeeSheetRowItem(
                    rowNumber = 3,
                    sectionTitle = "قلمرو زبانی — دستور زبان و گروه اسمی (۵ نمره)",
                    scoreBaram = "۵.۰ نمره",
                    questionPrompt = "الف) در بیت «تو کز محنت دیگران بی‌غمی // نشاید که نامت نهند آدمی»، نقش دستوری «آدمی» و نوع «که» را بنویسید. (۱.۵)\nب) در گروه اسمی «این سه کتابِ ارزشمندِ تاریخِ ایران»، هسته و نوع وابسته‌های پیشین و پسین را مشخص کنید. (۲.۰)\nپ) در عبارت «سعدیا مرد نکونام نمیرد هرگز»، نقش تبعی یا منادا را مشخص کنید و فعل را به زمان ماضی بعید تبدیل نمایید. (۱.۵)",
                    officialAnswerKey = "الف) «آدمی»: مسند (۰.۷۵) | «که»: حرف ربط وابسته‌ساز (۰.۷۵).\nب) «کتاب»: هسته (۰.۵) | «این»: صفت اشاره‌ای (وابسته پیشین) (۰.۲۵) | «سه»: صفت شمارشی (وابسته پیشین) (۰.۲۵) | «ارزشمند»: صفت بیانی (وابسته پسین) (۰.۵) | «تاریخ ایران»: مضاف‌الیه و مضاف‌الیه مضاف‌الیه (۰.۵).\nپ) «سعدیا»: منادا (۰.۷۵) | ماضی بعید نمیرد: نَمُرده بود (۰.۷۵)."
                ),
                NahaeeSheetRowItem(
                    rowNumber = 4,
                    sectionTitle = "قلمرو ادبی — آرایه‌های ادبی و حفظ شعر (۴ نمره)",
                    scoreBaram = "۴.۰ نمره",
                    questionPrompt = "الف) در بیت «دود اگر بالا نشیند کسر شأن شعله نیست // جای چشم ابرو نگیرد گرچه بالاتر نشست»، کدام آرایه برجسته فکری وجود دارد؟ دلیل آن را بنویسید. (۱.۵)\nب) در بیت «مرغ باغ ملکوتم نیم از عالم خاک // دو سه روزی قفسی ساخته‌اند از بدنم»، یک «استعاره» و یک «اضافه تشبیهی» یا تشبیه بیابید. (۱.۵)\nپ) جاهای خالی بیت حفظی زیر را کامل کنید:\n«هرکه را خوابگه آخر به دو مشتِ ............... است // گو چه حاجت که به افلاک کشی ............... را» (۱.۰)",
                    officialAnswerKey = "الف) آرایه «اسلوب معادله» (۰.۷۵)؛ زیرا دو مصراع از نظر دستوری مستقل‌اند و مصراع دوم مصداق و معادل مصراع اول است (می‌توان بین دو مصراع «همانند آن است که» قرار داد) (۰.۷۵).\nب) استعاره: مرغ باغ ملکوت (استعاره از روح انسان) (۰.۷۵) | تشبیه: عالمِ خاک (اضافه تشبیهی) و تشبیه بدن به قفس (۰.۷۵).\nپ) خاک (۰.۵) | ایوان (۰.۵)."
                ),
                NahaeeSheetRowItem(
                    rowNumber = 5,
                    sectionTitle = "قلمرو فکری — معنی شعر و نثر (۵ نمره)",
                    scoreBaram = "۵.۰ نمره",
                    questionPrompt = "ابیات و عبارات زیر را به نثر روان و دقیق فارسی معنی کنید:\nالف) «سعدیا گر بکند سیل فنا خانه عمر // دل قوی دار که بنیاد بقا محکم از اوست» (۱.۷۵)\nب) «ملکا، ذکر تو گویم که تو پاکی و خدایی // نروم جز به همان ره که توام راه نمایی» (۱.۷۵)\nپ) «آنچه بر صفحهٔ دل نقش بندد، به دشواری زدوده گردد.» (۱.۵)",
                    officialAnswerKey = "الف) ای سعدی، اگر سیلِ نابودی، خانهٔ عمر را ویران کند، دلت را محکم و استوار نگه دار؛ زیرا شالوده و اساس جاودانگی به وسیلهٔ خداوند پایدار و استوار است (۱.۷۵).\nب) پروردگارا، یاد و نام تو را بر زبان می‌آورم چرا که تو منزه و پروردگاری؛ جز در همان راهی که تو به من نشان می‌دهی گام برنمی‌دارم (۱.۷۵).\nپ) هر سخنی و نقشی که در لوح دل و جان انسان جای بگیرد، به سختی پاک و فراموش می‌شود (۱.۵)."
                ),
                NahaeeSheetRowItem(
                    rowNumber = 6,
                    sectionTitle = "قلمرو فکری — درک مطلب و مفهوم‌شناسی (۲ نمره)",
                    scoreBaram = "۲.۰ نمره",
                    questionPrompt = "الف) مفهوم مشترک دو عبارت «مار گزیده از ریسمان سیاه و سفید می‌ترسد» و «مسلمان نشنیدی که گزیده از مار، از ریسمان بترسد» چیست؟ (۱.۰)\nب) در درس «کاوه آهنگر»، خروش کاوه در برابر ضحاک نماد چه مفهومی در ادبیات حماسی ایران است؟ (۱.۰)",
                    officialAnswerKey = "الف) درس عبرت گرفتن از تجربه‌های تلخ گذشته و احتیاط شدید پس از آسیب دیدن (۱.۰).\nب) نماد بیداری ملی، دادخواهی، ایستادگی مردم در برابر ظلم و استبداد و پیروزی حق بر باطل (۱.۰)."
                )
            )
        ),

        // ۲. دفترچه کامل ۲۰ نمره‌ای دین و زندگی ۳ دوازدهم
        NahaeeFullExamBooklet(
            id = "full_nah_dini_1404_khordad",
            subjectId = "dini",
            subjectTitle = "دفترچه کامل امتحان نهایی دین و زندگی ۳ دوازدهم",
            sessionTitle = "نوبت خرداد ۱۴۰۴ (سراسری کشوری)",
            durationMinutes = 90,
            coefficientBadge = "ضریب ۱۴.۲۳ (دومین ضریب بزرگ کنکور)",
            sheetRows = listOf(
                NahaeeSheetRowItem(
                    rowNumber = 1,
                    sectionTitle = "بخش اول — ترجمه و پیام آیات شریفه قرآن (۶ نمره)",
                    scoreBaram = "۶.۰ نمره",
                    questionPrompt = "پیام و مفهوم هر یک از آیات شریفه زیر را بنویسید:\nالف) «لَوْ کَانَ فِیهِمَا آلِهَةٌ إِلَّا اللَّهُ لَفَسَدَتَا» (۱.۵)\nب) «یَا أَیُّهَا النَّاسُ أَنْتُمُ الْفُقَرَاءُ إِلَى اللَّهِ وَاللَّهُ هُوَ الْغَنِیُّ الْحَمِیدُ» (۱.۵)\nپ) «کُلًّا نُمِدُّ هَؤُلَاءِ وَهَؤُلَاءِ مِنْ عَطَاءِ رَبِّکَ وَمَا کَانَ عَطَاءُ رَبِّکَ مَحْظُورًا» (۱.۵)\nت) «إِنْ تَنْصُرُوا اللَّهَ یَنْصُرْکُمْ وَیُثَبِّتْ أَقْدَامَکُمْ» (۱.۵)",
                    officialAnswerKey = "الف) برهان تمانع (نظم و یگانگی تدبیر جهان): اگر در آسمان و زمین خدایانی جز الله بود، قطعاً نظم آن‌ها تباه می‌شد ⟵ اثبات توحید ربوبی و ذاتی (۱.۵).\nب) فقر ذاتی تمام مخلوقات به خداوند و غنای بالذات پروردگار در هر لحظه از هستی (۱.۵).\nپ) سنت امداد عام الهی: خداوند به همه انسان‌ها (چه مؤمن و چه کافر) امکانات مادی و بخشش خود را عطا می‌کند تا راه انتخابشان باز باشد (۱.۵).\nت) سنت امداد خاص الهی: اگر با ایمان و عمل صالح یاری‌گر دین خدا باشید، خداوند شما را یاری کرده و گام‌هایتان را استوار می‌سازد (۱.۵)."
                ),
                NahaeeSheetRowItem(
                    rowNumber = 2,
                    sectionTitle = "بخش دوم — مراتب توحید و شرک (۴ نمره)",
                    scoreBaram = "۴.۰ نمره",
                    questionPrompt = "الف) چهار مرتبه اصلی توحید (ذاتی، صفاتی، افعالی، عبادی) را تعریف کنید. (۲.۰)\nب) چرا «توحید افعالی» با اختیار انسان و قانون علت و معلول منافاتی ندارد؟ (۲.۰)",
                    officialAnswerKey = "الف) ۱. توحید ذاتی: یگانگی و بی‌همتایی ذات خدا (۰.۵) ۲. توحید صفاتی: عینیت صفات با ذات الهی (۰.۵) ۳. توحید افعالی: هر اثر و فعلی در جهان به اذن و اراده الهی است (۰.۵) ۴. توحید عبادی: تنها خداوند شایسته پرستش و بندگی است (۰.۵).\nب) زیرا خداوند خودش اراده کرده که کارها در جهان از طریق نظام اسباب و علل (و از جمله اراده و اختیار خود انسان) انجام پذیرد؛ پس اختیار انسان و علیت، طولی و در راستا و به اذن الهی است نه در عرض قدرت خدا (۲.۰)."
                ),
                NahaeeSheetRowItem(
                    rowNumber = 3,
                    sectionTitle = "بخش سوم — سنت‌های الهی، توبه و اخلاص (۵ نمره)",
                    scoreBaram = "۵.۰ نمره",
                    questionPrompt = "الف) تفاوت سنت «استدراج» و سنت «ابتلا و آزمایش» را بنویسید. (۲.۰)\nب) ارکان و مراحل چهارگانه «توبه واقعی» به ویژه در «حق‌الناس» چیست؟ (۱.۵)\nپ) سه اثر مهم «اخلاص در نیت و عمل» را نام ببرید. (۱.۵)",
                    officialAnswerKey = "الف) ابتلا و آزمایش برای همه انسان‌ها (با خوشی و ناخوشی) جهت شکوفایی استعدادها و بیداری است (۱.۰)؛ اما استدراج مخصوص گنهکارانی است که با فزونی نعمت در غفلت فرو می‌روند تا به تدریج به هلاکت برسند (۱.۰).\nب) ۱. ندامت و پشیمانی قلبی ۲. تصمیم جدی بر ترک گناه در آینده ۳. جبران گذشته: ادا کردن حقوق الهی (قضای نماز و روزه) و بازگرداندن حق‌الناس و جلب رضایت صاحب حق (۱.۵).\nپ) ۱. قبولی اعمال نزد خدا ۲. آرامش روحی و رهایی از وابستگی به غیر خدا ۳. حکمت‌آموزی و نفوذ کلام در دل‌ها (۱.۵)."
                ),
                NahaeeSheetRowItem(
                    rowNumber = 4,
                    sectionTitle = "بخش چهارم — امامت، ولایت فقیه و تمدن اسلامی (۵ نمره)",
                    scoreBaram = "۵.۰ نمره",
                    questionPrompt = "الف) در عصر غیبت امام زمان (عج)، سه وظیفه اصلی منتظران واقعی را بنویسید. (۱.۵)\nب) دو پایه مقبولیت و مشروعیت حکومت اسلامی در دوره غیبت (ولایت فقیه) کدام است؟ (۱.۵)\nپ) تفاوت «تجددگرایی غرب‌زده» و «تمدن‌سازی نوین اسلامی» در مواجهه با دستاوردهای بشری چیست؟ (۲.۰)",
                    officialAnswerKey = "الف) ۱. خودسازی اخلاقی و تقوا ۲. اصلاح جامعه و امر به معروف و نهی از منکر ۳. کسب آمادگی علمی، دفاعی و پیروی از فقیه جامع‌الشرایط (۱.۵).\nب) ۱. مشروعیت الهی (نصب عام از سوی امام معصوم ع) (۰.۷۵) ۲. مقبولیت و رأی و بیعت مردم جهت تحقق عینی حکومت (۰.۷۵).\nپ) تمدن اسلامی با تکیه بر وحی و عقل، دستاوردهای علمی مفید بشر را می‌گیرد و پالایش می‌کند اما هویت توحیدی خود را حفظ می‌نماید؛ در حالی که غرب‌زدگی به تقلید کورکورانه و ازخودبیگانگی فرهنگی می‌انجامد (۲.۰)."
                )
            )
        ),

        // ۳. دفترچه کامل ۲۰ نمره‌ای علوم و فنون ادبی ۳ دوازدهم
        NahaeeFullExamBooklet(
            id = "full_nah_fonoon_1404_khordad",
            subjectId = "fonoon",
            subjectTitle = "دفترچه کامل امتحان نهایی علوم و فنون ادبی ۳ دوازدهم",
            sessionTitle = "نوبت خرداد ۱۴۰۴ (سراسری کشوری)",
            durationMinutes = 120,
            coefficientBadge = "ضریب ۱۰.۴۳ (مهم‌ترین تخصصی انسانی)",
            sheetRows = listOf(
                NahaeeSheetRowItem(
                    rowNumber = 1,
                    sectionTitle = "بخش اول — تاریخ ادبیات و سبک‌شناسی (۴ نمره)",
                    scoreBaram = "۴.۰ نمره",
                    questionPrompt = "الف) چرا شعر دوره قاجار را «دوره بازگشت ادبی» می‌نامند و شاعران این دوره از کدام سبک‌ها پیروی کردند؟ (۱.۵)\nب) سه ویژگی فکری و زبانی «شعر دوره مشروطه (بیداری)» را بنویسید. (۱.۵)\nپ) بنیان‌گذار شعر نو (نیمایی) کیست و نخستین منظومه او چه نام دارد؟ (۱.۰)",
                    officialAnswerKey = "الف) زیرا شاعران این دوره از پیچیدگی‌های سبک هندی روی برگرداندند و به تقلید از سبک خراسانی (قصیده) و سبک عراقی (غزل) بازگشتند (۱.۵).\nب) ۱. ورود مفاهیم سیاسی-اجتماعی مانند وطن، آزادی، قانون و مبارزه با استبداد ۲. نزدیک شدن زبان شعر به زبان کوچه و بازار و روزنامه ۳. توجه به تعلیم همگانی و حقوق زنان (۱.۵).\nپ) علی اسفندیاری مشهور به نیما یوشیج (۰.۵) و منظومه «افسانه» در سال ۱۳۰۱ خورشیدی (۰.۵)."
                ),
                NahaeeSheetRowItem(
                    rowNumber = 2,
                    sectionTitle = "بخش دوم — موسیقی شعر، عروض و اختیارات شاعری (۵ نمره)",
                    scoreBaram = "۵.۰ نمره",
                    questionPrompt = "الف) بیت «ای ساربان آهسته ران کارام جانم می‌رود // وان دل که با خود داشتم با دلستانم می‌رود» را تقطیع هجایی و رکنی کرده و وزن و بحر آن را بنویسید. (۳.۰)\nب) در مصراع «بشنو از نی چون حکایت می‌کند»، کدام اختیار شاعری زبانی یا وزنی به کار رفته است؟ (۲.۰)",
                    officialAnswerKey = "الف) تقطیع: ای ساربان (- - U -) / آهسته ران (- - U -) / کارام جا (- - U -) / نم می‌رود (- - U -) (۱.۵) | وزن: مستفعلن مستفعلن مستفعلن مستفعلن (بحر رجز مثمن سالم) (۱.۵).\nب) ۱. در «بشنو از»: حذف همزه از ابتدای «از» بعد از صامت (بِشنُ وَز) = اختیار زبانی (۱.۰) | ۲. بلند حساب شدن هجای پایانی مصراع (می‌کند) = اختیار وزنی (۱.۰)."
                ),
                NahaeeSheetRowItem(
                    rowNumber = 3,
                    sectionTitle = "بخش سوم — زیبایی‌شناسی و آرایه‌های ادبی (۵ نمره)",
                    scoreBaram = "۵.۰ نمره",
                    questionPrompt = "الف) تفاوت آرایه «ایهام» و «ایهام تناسب» را توضیح داده و در بیت «چون شبنم اوفتاده بدم پیش آفتاب // مهرم به جان رسید و به عیوق برشدم» واژه ایهام‌دار و دو معنای آن را بنویسید. (۲.۵)\nب) در بیت «از صدای سخن عشق ندیدم خوش‌تر // یادگاری که در این گنبد دوار بماند»، آرایه‌های «حس‌آمیزی» و «استعاره» را مشخص کنید. (۲.۵)",
                    officialAnswerKey = "الف) در ایهام هر دو معنی قابل جایگذاری و مقصود شاعر است، اما در ایهام تناسب فقط یک معنی مقصود است و معنی دوم با واژه‌ای دیگر در بیت تناسب دارد (۱.۰) | واژه «مهر»: ۱. خورشید ۲. محبت و عشق (۱.۵).\nب) حس‌آمیزی: «دیدنِ صدای سخن عشق» (ترکیب حس بینایی و شنوایی) (۱.۲۵) | استعاره: «گنبد دوّار» استعاره از آسمان و روزگار (۱.۲۵)."
                ),
                NahaeeSheetRowItem(
                    rowNumber = 4,
                    sectionTitle = "بخش چهارم — نقد ادبی و تحلیل متن (۶ نمره)",
                    scoreBaram = "۶.۰ نمره",
                    questionPrompt = "الف) چهار عنصر اصلی «داستان و روایت» را نام برده و تفاوت زاویه دید «اول‌شخص» و «سوم‌شخص دانای کل» را بنویسید. (۳.۰)\nب) بیت «بنی‌آدم اعضای یک پیکرند // که در آفرینش ز یک گوهرند» را از سه منظر (زبانی، ادبی، فکری) تحلیل کنید. (۳.۰)",
                    officialAnswerKey = "الف) عناصر داستان: پیرنگ (طرح)، شخصیت، صحنه (زمان و مکان)، زاویه دید و درون‌مایه (۱.۵) | در زاویه دید اول‌شخص راوی یکی از شخصیت‌های داستان است (با ضمیر من)، اما در دانای کل راوی بیرون از داستان بوده و از افکار درونی همه شخصیت‌ها آگاه است (۱.۵).\nب) ۱. قلمرو زبانی: واژگان ساده و روان، فعل اسنادی «ند» (۱.۰) ۲. قلمرو ادبی: تشبیه بنی‌آدم به اعضای یک پیکر و استعاره گوهر از سرشت واحد انسانی (۱.۰) ۳. قلمرو فکری: نوع‌دوستی، همدلی جهانی و برابری ذاتی انسان‌ها (۱.۰)."
                )
            )
        ),

        // ۴. دفترچه کامل ۲۰ نمره‌ای زبان انگلیسی ۳ (Vision 3)
        NahaeeFullExamBooklet(
            id = "full_nah_english_1404_khordad",
            subjectId = "english",
            subjectTitle = "دفترچه کامل امتحان نهایی زبان انگلیسی ۳ (Vision 3)",
            sessionTitle = "نوبت خرداد ۱۴۰۴ (سراسری کشوری)",
            durationMinutes = 120,
            coefficientBadge = "ضریب ۱۰.۱۷",
            sheetRows = listOf(
                NahaeeSheetRowItem(
                    rowNumber = 1,
                    sectionTitle = "Part 1: Vocabulary (واژگان — ۴ نمره)",
                    scoreBaram = "۴.۰ نمره",
                    questionPrompt = "A) Match the words with their definitions: (2.0 pts)\n1. appreciate  2.entry  3. renewable  4.elderly\na) able to be replaced naturally (like solar energy)\nb) to value somebody or something highly\nc) old people\nd) a word or phrase explained in a dictionary\n\nB) Fill in the blanks with the given words: (2.0 pts) [combines, boost, forgive, pollution]\n5. Regular exercise can .............. your energy and health.\n6. Hydrogen .............. with oxygen to form water.",
                    officialAnswerKey = "1 -> b (appreciate = قدردانی کردن) (0.5)\n2 -> d (entry = مدخل لغت‌نامه) (0.5)\n3 -> a (renewable = تجدیدپذیر) (0.5)\n4 -> c (elderly = سالمندان) (0.5)\n5. boost (افزایش دادن) (1.0)\n6. combines (ترکیب می‌شود) (1.0)."
                ),
                NahaeeSheetRowItem(
                    rowNumber = 2,
                    sectionTitle = "Part 2: Grammar (گرامر دروس ۱ تا ۳ — ۶ نمره)",
                    scoreBaram = "۶.۰ نمره",
                    questionPrompt = "A) Change the active sentence into Passive Voice: (1.5 pts)\n1. \"Hafez wrote many beautiful poems in the 14th century.\"\n\nB) Write appropriate Tag Questions: (1.5 pts)\n2. \"Mina rarely watches television at night, ....................?\"\n3. \"The students have finished their project, ....................?\"\n\nC) Complete the Conditional Type 2 and Relative Clause sentences: (3.0 pts)\n4. If Ali (know) .................... the answer, he would tell us immediately.\n5. The scientist (who / which) .................... invented the telephone was Alexander Graham Bell.\n6. It was raining heavily, (so / but) .................... we stayed at home.",
                    officialAnswerKey = "1. Many beautiful poems were written (by Hafez) in the 14th century. (1.5)\n2. does she? (چون rarely منفی است، دنباله پرسشی مثبت می‌شود) (0.75)\n3. haven't they? (0.75)\n4. knew (شرطی نوع دوم: گذشته ساده در جمله If) (1.0)\n5. who (ضمیر موصولی فاعلی برای انسان) (1.0)\n6. so (بیان نتیجه: باران شدید می‌بارید، بنابراین در خانه ماندیم) (1.0)."
                ),
                NahaeeSheetRowItem(
                    rowNumber = 3,
                    sectionTitle = "Part 3: Reading Comprehension (درک مطلب — ۶ نمره)",
                    scoreBaram = "۶.۰ نمره",
                    questionPrompt = "Read the passage and answer the questions:\n\"Dictionaries are very useful tools for language learners. A monolingual dictionary uses only one language to explain words, while a bilingual dictionary translates words from one language into another. Learner's dictionaries provide simple definitions, phonetic symbols, and clear example sentences.\"\n1. What is the difference between a monolingual and a bilingual dictionary? (2.0)\n2. True or False: A learner's dictionary provides phonetic symbols and examples. (2.0)\n3. What does a monolingual dictionary use to explain words? (2.0)",
                    officialAnswerKey = "1. A monolingual dictionary uses only one language, while a bilingual dictionary translates words from one language into another. (2.0)\n2. True (2.0)\n3. It uses only one language to explain words. (2.0)."
                ),
                NahaeeSheetRowItem(
                    rowNumber = 4,
                    sectionTitle = "Part 4: Writing & Paragraph Organization (نگارش — ۴ نمره)",
                    scoreBaram = "۴.۰ نمره",
                    questionPrompt = "A) Write a complete Topic Sentence for the following topic: (2.0 pts)\nTopic: Respecting Parents\n\nB) Identify the three main parts of a standard English paragraph and write a short Concluding Sentence about \"Saving Energy\". (2.0 pts)",
                    officialAnswerKey = "A) Topic Sentence: Respecting parents is one of the most important moral duties in every society for several reasons. (Topic + Controlling Idea) (2.0)\nB) 1. Topic Sentence, 2. Supporting Sentences, 3. Concluding Sentence (1.0) | Concluding Sentence: In short, saving energy protects our environment and guarantees a bright future for the next generation. (1.0)."
                )
            )
        ),

        // ۵. دفترچه کامل ۲۰ نمره‌ای عربی ۳ زبان قرآن دوازدهم
        NahaeeFullExamBooklet(
            id = "full_nah_arabic_1404_khordad",
            subjectId = "arabic",
            subjectTitle = "دفترچه کامل امتحان نهایی عربی ۳ تخصصی دوازدهم",
            sessionTitle = "نوبت خرداد ۱۴۰۴ (سراسری کشوری)",
            durationMinutes = 90,
            coefficientBadge = "ضریب ۷.۸۰",
            sheetRows = listOf(
                NahaeeSheetRowItem(
                    rowNumber = 1,
                    sectionTitle = "بخش اول — مهارت واژه‌شناسی (مترادف و متضاد — ۳ نمره)",
                    scoreBaram = "۳.۰ نمره",
                    questionPrompt = "الف) مترادف و متضاد واژه‌های زیر را بنویسید: (۲.۰)\n۱. ضِیاء = ............... (مترادف) | ۲. کَسلان ≠ ............... (متضاد)\n۳. عَفو = ............... (مترادف) | ۴. حُزن ≠ ............... (متضاد)\nب) مفرد واژه‌های «مَواعِظ» و «أَفاضِل» را بنویسید. (۱.۰)",
                    officialAnswerKey = "الف) ۱. ضیاء = نور (۰.۵) | ۲. کسلان ≠ نَشیط (کوشا) (۰.۵) | ۳. عفو = صَفح / غُفران (۰.۵) | ۴. حزن ≠ فَرَح / سُرور (۰.۵).\nب) مواعظ ⟵ مَوعِظَة (۰.۵) | أفاضل ⟵ أَفضَل (۰.۵)."
                ),
                NahaeeSheetRowItem(
                    rowNumber = 2,
                    sectionTitle = "بخش دوم — مهارت ترجمه به فارسی (۷ نمره)",
                    scoreBaram = "۷.۰ نمره",
                    questionPrompt = "جملات زیر را با رعایت دقیق قواعد «حال»، «مفعول مطلق» و «اسلوب استثناء» به فارسی ترجمه کنید:\nالف) «رَجَعَ الْجُنُودُ مِنَ الْمَعْرَکَةِ مُنْتَصِرِینَ.» (۱.۷۵)\nب) «إِنَّا فَتَحْنَا لَکَ فَتْحًا مُبِینًا.» (۱.۷۵)\nپ) «مَا نَجَحَ فِی الِامْتِحَانِ إِلَّا الْمُجْتَهِدُونَ.» (۱.۷۵)\nت) «خَرَجَ الطُّلَّابُ مِنَ الْمَدْرَسَةِ وَ هُمْ فَرِحُونَ.» (۱.۷۵)",
                    officialAnswerKey = "الف) سربازان در حالی که پیروز بودند (پیروزمندانه) از نبرد بازگشتند (۱.۷۵).\nب) همانا ما برای تو پیروزی آشکاری را رقم زدیم (گشودنی آشکار گشودیم — مفعول مطلق نوعی) (۱.۷۵).\nپ) در امتحان جز کوشندگان (تلاشگران) کسی موفق نشد (استثناء مفرغ) (۱.۷۵).\nت) دانش‌آموزان از مدرسه خارج شدند در حالی که شادمان بودند (جمله حالیه اسمیه) (۱.۷۵)."
                ),
                NahaeeSheetRowItem(
                    rowNumber = 3,
                    sectionTitle = "بخش سوم — قواعد: حال، مفعول مطلق، استثناء و حروف مشبهة (۶ نمره)",
                    scoreBaram = "۶.۰ نمره",
                    questionPrompt = "الف) در عبارت «یَعِیشُ الْمُؤْمِنُ فِی الدُّنْیَا قَانِعاً»، «حال» و «صاحب حال» و اعراب هر یک را بنویسید. (۲.۰)\nب) نوع مفعول مطلق را در دو آیه زیر مشخص کنید: (۲.۰)\n۱. «وَ کَلَّمَ اللَّهُ مُوسَى تَکْلِیماً»\n۲. «فَاصْبِرْ صَبْراً جَمِیلاً»\nپ) در جمله «حَضَرَ الضُّیُوفُ إِلَّا خَالِداً» و جمله «مَا حَضَرَ إِلَّا خَالِدٌ»، نوع استثناء و نقش و اعراب کلمه بعد از «إلّا» را بنویسید. (۲.۰)",
                    officialAnswerKey = "الف) حال: «قانعاً» (منصوب به فتحه) (۱.۰) | صاحب حال: «المؤمنُ» (فاعل و مرفوع) (۱.۰).\nب) ۱. تکلیماً: مفعول مطلق تأکیدی (۱.۰) | ۲. صبراً: مفعول مطلق نوعی (بیانی، چون صفت جمیلاً بعد از آن آمده) (۱.۰).\nپ) جمله اول: استثناء تام، «خالداً» مستثنی منصوب (۱.۰) | جمله دوم: استثناء مفرّغ، «خالدٌ» فاعل و مرفوع (۱.۰)."
                ),
                NahaeeSheetRowItem(
                    rowNumber = 4,
                    sectionTitle = "بخش چهارم — تحلیل صرفی و درک متن (۴ نمره)",
                    scoreBaram = "۴.۰ نمره",
                    questionPrompt = "الف) تحلیل صرفی (ماده/ریشه، باب، صیغه، نوع فعل) کلمه «یَسْتَغْفِرُونَ» را بنویسید. (۲.۰)\nب) در عبارت «الْعِلْمُ نُورٌ یَقْذِفُهُ اللَّهُ فِی قَلْبِ مَنْ یَشَاءُ»، نقش دستوری «العلمُ» و «نورٌ» را بنویسید. (۲.۰)",
                    officialAnswerKey = "الف) یستغفرون: فعل مضارع، صیغه جمع مذکر غایب، باب استفعال، ریشه (غ ف ر)، معرب و مرفوع به ثبوت نون (۲.۰).\nب) «العلمُ»: مبتدا و مرفوع (۱.۰) | «نورٌ»: خبر مفرد و مرفوع (۱.۰)."
                )
            )
        ),

        // ۶. دفترچه کامل ۲۰ نمره‌ای ریاضی و آمار ۳ دوازدهم
        NahaeeFullExamBooklet(
            id = "full_nah_math_1404_khordad",
            subjectId = "math",
            subjectTitle = "دفترچه کامل امتحان نهایی ریاضی و آمار ۳ دوازدهم",
            sessionTitle = "نوبت خرداد ۱۴۰۴ (سراسری کشوری)",
            durationMinutes = 110,
            coefficientBadge = "ضریب ۵.۱۶",
            sheetRows = listOf(
                NahaeeSheetRowItem(
                    rowNumber = 1,
                    sectionTitle = "فصل اول — شمارش و احتمال (۸ نمره)",
                    scoreBaram = "۸.۰ نمره",
                    questionPrompt = "الف) با ارقام {1, 2, 3, 4, 5} چند عدد سه‌رقمی زوج بدون تکرار ارقام می‌توان نوشت؟ (۲.۰)\nب) از بین ۶ معلم ریاضی و ۴ معلم ادبیات، به چند طریق می‌توان یک کمیته ۳ نفره تشکیل داد که حداقل ۲ نفر معلم ریاضی باشند؟ (۳.۰)\nپ) اگر A و B دو پیشامد مستقل باشند و P(A) = 0.4 و P(B) = 0.5 باشد، مقادیر P(A ∩ B)، P(A ∪ B) و احتمال شرطی P(A|B) را بیابید. (۳.۰)",
                    officialAnswerKey = "الف) رقم یکان ۲ حالت (2 یا 4) × رقم صدگان ۴ حالت × رقم دهگان ۳ حالت = 4 × 3 × 2 = 24 عدد (۲.۰).\nب) حالت اول (۲ ریاضی و ۱ ادبیات): C(6,2) × C(4,1) = 15 × 4 = 60 (۱.۵) | حالت دوم (هر ۳ نفر ریاضی): C(6,3) × C(4,0) = 20 × 1 = 20 (۱.۰) | جمع کل: 60 + 20 = 80 (۰.۵).\nپ) پیشامد مستقل: P(A ∩ B) = 0.4 × 0.5 = 0.2 (۱.۰) | P(A ∪ B) = 0.4 + 0.5 - 0.2 = 0.7 (۱.۰) | P(A|B) = P(A) = 0.4 (۱.۰)."
                ),
                NahaeeSheetRowItem(
                    rowNumber = 2,
                    sectionTitle = "فصل دوم — الگوهای خطی، سری زمانی و رشد نمایی (۶ نمره)",
                    scoreBaram = "۶.۰ نمره",
                    questionPrompt = "الف) در یک سری زمانی دو نقطه مرجع (x₁ = 2, y₁ = 15) و (x₂ = 6, y₂ = 35) داده شده است. معادله خط روند را بنویسید و مقدار y را در x = 4 (درون‌یابی) و x = 8 (برون‌یابی) محاسبه کنید. (۴.۰)\nب) جمعیتی با نرخ رشد سالانه ۱۰٪ از ۱۰۰ هزار نفر آغاز می‌شود. پس از ۲ سال با مدل رشد نمایی به چند نفر می‌رسد؟ (۲.۰)",
                    officialAnswerKey = "الف) شیب خط: m = (35 - 15) / (6 - 2) = 20 / 4 = 5 (۱.۰) | معادله خط: y - 15 = 5(x - 2) ⟹ y = 5x + 5 (۱.۰) | درون‌یابی در x = 4: y = 5(4) + 5 = 25 (۱.۰) | برون‌یابی در x = 8: y = 5(8) + 5 = 45 (۱.۰).\nب) فرمول رشد نمایی: P(t) = P₀(1 + r)ᵗ = 100,000 × (1 + 0.10)² = 100,000 × 1.21 = 121,000 نفر (۲.۰)."
                ),
                NahaeeSheetRowItem(
                    rowNumber = 3,
                    sectionTitle = "فصل سوم — منطق و استدلال ریاضی (۶ نمره)",
                    scoreBaram = "۶.۰ نمره",
                    questionPrompt = "الف) نقیض گزاره سوردار «∀x ∈ ℝ : 2x - 4 > 0» را بنویسید و با آوردن یک مثال نقض نشان دهید گزاره اولیه نادرست است. (۳.۰)\nب) برای گزاره شرطی «اگر عددی بر ۶ بخش‌پذیر باشد، آنگاه بر ۳ نیز بخش‌پذیر است»، گزاره‌های «عکس»، «نقیض» و «عکس نقیض» را بنویسید. (۳.۰)",
                    officialAnswerKey = "الف) نقیض گزاره: ∃x ∈ ℝ : 2x - 4 ≤ 0 (۱.۵) | مثال نقض: به ازای x = 1 داریم 2(1) - 4 = -2 ≤ 0 پس گزاره اولیه نادرست است (۱.۵).\nب) ۱. عکس (q ⟹ p): اگر عددی بر ۳ بخش‌پذیر باشد، بر ۶ بخش‌پذیر است (۱.۰) | ۲. عکس نقیض (~q ⟹ ~p): اگر عددی بر ۳ بخش‌پذیر نباشد، آنگاه بر ۶ نیز بخش‌پذیر نیست (۱.۰) | ۳. نقیض ترکیب شرطی (p ∧ ~q): عددی بر ۶ بخش‌پذیر است ولی بر ۳ بخش‌پذیر نیست (۱.۰)."
                )
            )
        ),

        // ۷. دفترچه کامل ۲۰ نمره‌ای فلسفه ۲ دوازدهم
        NahaeeFullExamBooklet(
            id = "full_nah_philosophy_1404_khordad",
            subjectId = "philosophy",
            subjectTitle = "دفترچه کامل امتحان نهایی فلسفه ۲ دوازدهم",
            sessionTitle = "نوبت خرداد ۱۴۰۴ (سراسری کشوری)",
            durationMinutes = 90,
            coefficientBadge = "ضریب ۵.۶۲",
            sheetRows = listOf(
                NahaeeSheetRowItem(
                    rowNumber = 1,
                    sectionTitle = "بخش اول — هستی‌شناسی: وجود، ماهیت و تشکیک (۷ نمره)",
                    scoreBaram = "۷.۰ نمره",
                    questionPrompt = "الف) تمایز «وجود» و «ماهیت» را توضیح داده و معنای «اصالت وجود و اعتباریت ماهیت» را بنویسید. (۳.۵)\nب) منظور از «تشکیک در مراتب وجود» چیست و حکیمان اشراقی آن را با کدام مثال تبیین می‌کنند؟ (۳.۵)",
                    officialAnswerKey = "الف) هر پدیده‌ای در ذهن به دو جنبه تجزیه می‌شود: چیستی (ماهیت) و هستی (وجود) (۱.۰). اصالت وجود یعنی آنچه واقعیت عینی دارد و منشأ اثر در خارج است «وجود» است (۱.۵) و ماهیت تنها قالب و مرز ذهنی وجود و امری اعتباری است (۱.۰).\nب) تشکیک در وجود یعنی حقیقت وجود با اینکه یک حقیقت واحد است، دارای مراتب شدید و ضعیف (کامل و ناقص) است (۲.۰)؛ مانند «نور» که نور خورشید و نور شمع هر دو از جنس نورند اما در شدت و ضعف تفاوت دارند (۱.۵)."
                ),
                NahaeeSheetRowItem(
                    rowNumber = 2,
                    sectionTitle = "بخش دوم — اصل علیت، سنخیت و ضرورت علّی (۴ نمره)",
                    scoreBaram = "۴.۰ نمره",
                    questionPrompt = "الف) علل چهارگانه (فاعلی، مادی، صوری، غایی) را برای ساخت یک «میز چوبی» مشخص کنید. (۲.۰)\nب) اصل «سنخیت میان علت و معلول» چه نتیجه‌ای دارد؟ (۲.۰)",
                    officialAnswerKey = "الف) علت فاعلی: نجار (۰.۵) | علت مادی: چوب (۰.۵) | علت صوری: شکل و ساختار میز (۰.۵) | علت غایی: هدف از ساختن میز و استفاده از آن (۰.۵).\nب) اصل سنخیت یعنی از هر علتی هر معلولی پدید نمی‌آید و میان علت و معلول تناسب ذاتی برقرار است؛ همین اصل پایه تمام قوانین علمی و تجربی است (۲.۰)."
                ),
                NahaeeSheetRowItem(
                    rowNumber = 3,
                    sectionTitle = "بخش سوم — معرفت‌شناسی و حکمت متعالیه ملاصدرا (۹ نمره)",
                    scoreBaram = "۹.۰ نمره",
                    questionPrompt = "الف) تفاوت «علم حضوری» و «علم حصولی» را بنویسید و بگویید چرا علم حضوری خطاناپذیر است؟ (۳.۰)\nب) دیدگاه «کانت» درباره نقش ذهن در شناخت (مقولات پیشینی) چیست؟ (۳.۰)\nپ) نظریه «حرکت جوهری ملاصدرا» و تفاوت آن با دیدگاه ابن‌سینا را شرح دهید. (۳.۰)",
                    officialAnswerKey = "الف) در علم حضوری، خودِ واقعیت معلوم بدون واسطه صورت ذهنی نزد عالم حاضر است (مثل علم انسان به خود)، بنابراین خطایی در تطابق وجود ندارد (۱.۵)؛ اما در علم حصولی شناخت با واسطه مفهوم ذهنی است و احتمال خطا دارد (۱.۵).\nب) کانت معتقد است شناخت حاصل ترکیب داده‌های حسی (پسینی) با قالب‌های فطری و پیشینی ذهن است و ما فقط پدیدارها (فنومن) را می‌شناسیم نه ذات اشیاء (نومن) را (۳.۰).\nپ) ابن‌سینا حرکت را تنها در ۴ مقوله عرضی (کم، کیف، این، وضع) می‌پذیرفت و جوهر را ثابت می‌دانست؛ اما ملاصدرا اثبات کرد که خود جوهر و ذات عالم مادی نیز در حرکت و نو شدن دائمی است (۳.۰)."
                )
            )
        ),

        // ۸. دفترچه کامل ۲۰ نمره‌ای جامعه‌شناسی ۳ دوازدهم
        NahaeeFullExamBooklet(
            id = "full_nah_sociology_1404_khordad",
            subjectId = "sociology",
            subjectTitle = "دفترچه کامل امتحان نهایی جامعه‌شناسی ۳ دوازدهم",
            sessionTitle = "نوبت خرداد ۱۴۰۴ (سراسری کشوری)",
            durationMinutes = 90,
            coefficientBadge = "ضریب ۵.۵۲",
            sheetRows = listOf(
                NahaeeSheetRowItem(
                    rowNumber = 1,
                    sectionTitle = "بخش اول — کنش اجتماعی و ذخیره دانشی (۶ نمره)",
                    scoreBaram = "۶.۰ نمره",
                    questionPrompt = "الف) پنج ویژگی اصلی «کنش اجتماعی» را نام برده و تفاوت پیامد ارادی و غیرارادی کنش را با مثال بنویسید. (۳.۰)\nب) منظور از «ذخیره دانشی جامعه» چیست و شامل چه بخش‌هایی است؟ (۳.۰)",
                    officialAnswerKey = "الف) ویژگی‌ها: آگاهانه، ارادی، هدفدار، معنادار و معطوف به دیگران (۱.۵). پیامد ارادی وابسته به خواست کنشگر است اما پیامد غیرارادی خارج از قصد کنشگر رخ می‌دهد (مانند ترافیک ناشی از حرکت خودروها) (۱.۵).\nب) مجموعه آگاهی‌ها، باورها، ارزش‌ها و مهارت‌های انباشته‌شده در فرهنگ یک جامعه که کنشگران بر اساس آن عمل می‌کنند؛ شامل دانش عمومی (عرفی) و دانش تخصصی و علمی (۳.۰)."
                ),
                NahaeeSheetRowItem(
                    rowNumber = 2,
                    sectionTitle = "بخش دوم — مقایسه سه رویکرد تبیینی، تفسیری و انتقادی (۷ نمره)",
                    scoreBaram = "۷.۰ نمره",
                    questionPrompt = "الف) سه رویکرد «تبیینی (اثبات‌گرا)»، «تفسیری» و «انتقادی» را از نظر ۱. هدف شناخت ۲. روش تحقیق و ۳. نگاه به ارزش‌ها و وضع موجود مقایسه کنید. (۴.۵)\nب) چرا رویکرد اثبات‌گرا از تبیین معنایی و ارزشی کنش‌های انسانی ناتوان است؟ (۲.۵)",
                    officialAnswerKey = "الف) ۱. تبیینی: هدف کشف قوانین علّی و کنترل، روش کمی و تجربی، بی‌طرفی ارزشی و حفظ وضع موجود (۱.۵)\n۲. تفسیری: هدف فهم معنای ذهنی کنشگران، روش کیفی و تفهمی، نسبی‌گرایی ارزشی (۱.۵)\n۳. انتقادی: هدف کشف روابط سلطه و رهایی انسان، روش تاریخی-انتقادی، ارزش‌داورانه و تغییر وضع موجود (۱.۵).\nب) زیرا روش تجربی-حسی فقط ظاهر فیزیکی رفتار را می‌بیند و به نیت، معنا و ارزش‌های درونی کنشگر دسترسی ندارد (۲.۵)."
                ),
                NahaeeSheetRowItem(
                    rowNumber = 3,
                    sectionTitle = "بخش سوم — فرهنگ آرمانی و واقعی، هویت و نظام جهانی (۷ نمره)",
                    scoreBaram = "۷.۰ نمره",
                    questionPrompt = "الف) تفاوت «فرهنگ آرمانی» و «فرهنگ واقعی» و تفاوت «فرهنگ حق» و «فرهنگ باطل» را بنویسید. (۳.۵)\nب) «ازخودبیگانگی فرهنگی» چیست و انقلاب اسلامی ایران چه تحولی در نظم استعماری نظام جهانی ایجاد کرد؟ (۳.۵)",
                    officialAnswerKey = "الف) فرهنگ آرمانی ارزش‌های مطلوب مورد ادعای جامعه است و فرهنگ واقعی آنچه در عمل اجرا می‌شود (۱.۷۵). فرهنگ حق منطبق بر فطرت الهی و عقل سلیم است اما فرهنگ باطل برخلاف فطرت و مبتنی بر طاغوت و هوای نفس است (۱.۷۵).\nب) ازخودبیگانگی فرهنگی یعنی فراموشی هویت اصیل خودی و تقلید منفعلانه از فرهنگ بیگانه (۱.۷۵). انقلاب اسلامی با احیای هویت دینی و استکبارستیزی، دوقطبی شرق و غرب را شکست و الگوی بیداری اسلامی را ارائه کرد (۱.۷۵)."
                )
            )
        ),

        // ۹. دفترچه کامل ۲۰ نمره‌ای تاریخ ۳ دوازدهم (ایران و جهان معاصر)
        NahaeeFullExamBooklet(
            id = "full_nah_history_1404_khordad",
            subjectId = "history",
            subjectTitle = "دفترچه کامل امتحان نهایی تاریخ ۳ دوازدهم",
            sessionTitle = "نوبت خرداد ۱۴۰۴ (سراسری کشوری)",
            durationMinutes = 90,
            coefficientBadge = "ضریب ۵.۴۰",
            sheetRows = listOf(
                NahaeeSheetRowItem(
                    rowNumber = 1,
                    sectionTitle = "بخش اول — دوره قاجار، استعمار و انقلاب مشروطه (۷ نمره)",
                    scoreBaram = "۷.۰ نمره",
                    questionPrompt = "الف) عهدنامه‌های «گلستان» و «ترکمنچای» میان ایران و کدام کشور بسته شد و دو پیامد سنگین ترکمنچای (از جمله حق کاپیتولاسیون) را بنویسید. (۳.۵)\nب) سه اقدام اصلاحی «امیرکبیر» در دوره ناصرالدین‌شاه قاجار را نام ببرید. (۲.۰)\nپ) نقش علمای دینی در «نهضت تنباکو» و پیروزی «انقلاب مشروطه» چه بود؟ (۱.۵)",
                    officialAnswerKey = "الف) میان ایران و روسیه تزاری در دوره فتحعلی‌شاه قاجار (۱.۰). پیامدها: ۱. جدایی کامل قفقاز و تعیین رود ارس به عنوان مرز ۲. اعطای حق قضاوت کنسولی (کاپیتولاسیون) به اتباع روسیه و تحمیل غرامت سنگین (۲.۵).\nب) ۱. تأسیس مدرسه دارالفنون ۲. انتشار روزنامه وقایع اتفاقیه ۳. اصلاح مالیه، کاهش هزینه‌های دربار و مبارزه با رشوه و نفوذ بیگانگان (۲.۰).\nپ) تحریم تنباکو با فتوای میرزای شیرازی که به لغو امتیاز تالبوت انجامید (۰.۷۵) و رهبری جنبش عدالتخانه و مشروطه توسط آخوند خراسانی، طباطبایی و بهبهانی و نظارت بر قوانین با اصل طراز (شیخ فضل‌الله نوری) (۰.۷۵)."
                ),
                NahaeeSheetRowItem(
                    rowNumber = 2,
                    sectionTitle = "بخش دوم — جنگ‌های جهانی اول و دوم و عصر پهلوی (۶.۵ نمره)",
                    scoreBaram = "۶.۵ نمره",
                    questionPrompt = "الف) با وجود اعلام بی‌طرفی ایران در جنگ جهانی اول و دوم، چه سرنوشتی برای کشور رقم خورد و پیامد اشغال ایران در شهریور ۱۳۲۰ چه بود؟ (۳.۵)\nب) علل و دستاوردهای «نهضت ملی شدن صنعت نفت» و عوامل وقوع «کودتای ۲۸ مرداد ۱۳۳۲» را بنویسید. (۳.۰)",
                    officialAnswerKey = "الف) در هر دو جنگ بیگانگان بی‌طرفی ایران را نقض و کشور را اشغال کردند (۱.۵). در شهریور ۱۳۲۰ متفقین (شوروی از شمال و انگلیس از جنوب) ایران را اشغال کرده و رضاشاه ناچار به استعفا و تبعید شد (۲.۰).\nب) نهضت ملی شدن نفت به رهبری دکتر مصدق و آیت‌الله کاشانی برای قطع سلطه انگلیس بر نفت ایران شکل گرفت (۱.۵)؛ اما با توطئه مشترک آمریکا (سیا) و انگلیس و اختلاف در درون نهضت، کودتای ۲۸ مرداد ۱۳۳۲ رخ داد و استبداد محمدرضاشاه بازگشت (۱.۵)."
                ),
                NahaeeSheetRowItem(
                    rowNumber = 3,
                    sectionTitle = "بخش سوم — انقلاب اسلامی و دفاع مقدس (۶.۵ نمره)",
                    scoreBaram = "۶.۵ نمره",
                    questionPrompt = "الف) چرا قیام ۱۵ خرداد ۱۳۴۲ نقطه عطف نهضت اسلامی به شمار می‌رود؟ (۲.۵)\nب) چهار دستاورد راهبردی «هشت سال دفاع مقدس» و عملیات‌های «ثامن‌الائمه» و «الی‌بیت‌المقدس» را بنویسید. (۴.۰)",
                    officialAnswerKey = "الف) زیرا ماهیت استبدادی و وابسته رژیم پهلوی آشکار شد، رهبری امام خمینی (ره) تثبیت گردید و مبارزه وارد مرحله نوین و سراسری مذهبی-سیاسی شد (۲.۵).\nب) ۱. حفظ تمامیت ارضی کشور در برابر متجاوز و حامیان جهانی او ۲. خودباوری و پیشرفت صنایع دفاعی بومی (۲.۰) | عملیات ثامن‌الائمه: شکست حصر آبادان در مهر ۱۳۶۰ (۱.۰) | عملیات الی‌بیت‌المقدس: آزادسازی خرمشهر در ۳ خرداد ۱۳۶۱ (۱.۰)."
                )
            )
        ),

        // ۱۰. دفترچه کامل ۲۰ نمره‌ای جغرافیا ۳ دوازدهم (کاربردی)
        NahaeeFullExamBooklet(
            id = "full_nah_geography_1404_khordad",
            subjectId = "geography",
            subjectTitle = "دفترچه کامل امتحان نهایی جغرافیا ۳ دوازدهم",
            sessionTitle = "نوبت خرداد ۱۴۰۴ (سراسری کشوری)",
            durationMinutes = 90,
            coefficientBadge = "ضریب ۴.۸۵",
            sheetRows = listOf(
                NahaeeSheetRowItem(
                    rowNumber = 1,
                    sectionTitle = "بخش اول — آمایش سرزمین و برنامه‌ریزی منطقه‌ای (۵ نمره)",
                    scoreBaram = "۵.۰ نمره",
                    questionPrompt = "الف) «آمایش سرزمین» را تعریف کرده و سه رکن اصلی آن را نام ببرید. (۲.۵)\nب) دو هدف اصلی آمایش سرزمین در جلوگیری از عدم تعادل منطقه‌ای چیست؟ (۲.۵)",
                    officialAnswerKey = "الف) تنظیم رابطه منطقی و متعادل میان سه رکن «انسان (جمعیت)»، «فضا (سرزمین)» و «فعالیت (اقتصاد)» برای بهره‌برداری پایدار (۲.۵).\nب) ۱. توزیع متوازن جمعیت و امکانات در پهنه کشور و کاهش تمرکزگرایی در کلان‌شهرها (۱.۲۵) ۲. حفظ محیط‌زیست و استفاده بهینه از استعدادهای طبیعی هر ناحیه (۱.۲۵)."
                ),
                NahaeeSheetRowItem(
                    rowNumber = 2,
                    sectionTitle = "بخش دوم — مدیریت شهرها، روستاها و حمل‌ونقل (۵ نمره)",
                    scoreBaram = "۵.۰ نمره",
                    questionPrompt = "الف) چهار چالش اصلی کلان‌شهرهای امروزی (مانند حاشیه‌نشینی و جزیره حرارتی) را بنویسید. (۲.۵)\nب) مزایای حمل‌ونقل ریلی نسبت به حمل‌ونقل جاده‌ای را در سه مورد بیان کنید. (۲.۵)",
                    officialAnswerKey = "الف) ۱. گسترش سکونتگاه‌های غیررسمی (حاشیه‌نشینی) ۲. آلودگی هوا و شکل‌گیری جزیره حرارتی شهری ۳. ترافیک سنگین و فرسودگی بافت قدیم ۴. کمبود فضای سبز و تولید انبوه پسماند (۲.۵).\nب) ۱. ایمنی بسیار بالاتر و کاهش تصادفات ۲. مصرف سوخت کمتر و آلایندگی زیست‌محیطی ناچیز ۳. ظرفیت جابه‌جایی بار و مسافر انبوه با هزینه کمتر (۲.۵)."
                ),
                NahaeeSheetRowItem(
                    rowNumber = 3,
                    sectionTitle = "بخش سوم — مدیریت مخاطرات طبیعی (زلزله، سیل، خشکسالی) (۵ نمره)",
                    scoreBaram = "۵.۰ نمره",
                    questionPrompt = "الف) چهار مرحله اصلی «چرخه مدیریت بحران» را به ترتیب بنویسید و بگویید کدام مراحل قبل از وقوع بحران و کدام بعد از وقوع است؟ (۳.۰)\nب) تفاوت «مخاطره طبیعی» و «فاجعه (بحران)» در چیست؟ (۲.۰)",
                    officialAnswerKey = "الف) قبل از وقوع: ۱. پیشگیری و کاهش اثرات ۲. آمادگی (۱.۵) | حین و بعد از وقوع: ۳. مقابله (امداد و نجات) ۴. بازسازی و بازتوانی (۱.۵).\nب) مخاطره رویدادی طبیعی مانند زلزله یا سیل است؛ اما زمانی به فاجعه و بحران تبدیل می‌شود که در سکونتگاه‌های آسیب‌پذیر انسانی رخ دهد و تلفات و خسارات سنگین به بار آورد (۲.۰)."
                ),
                NahaeeSheetRowItem(
                    rowNumber = 4,
                    sectionTitle = "بخش چهارم — فناوری‌های نوین جغرافیایی (GIS، RS و GPS) (۵ نمره)",
                    scoreBaram = "۵.۰ نمره",
                    questionPrompt = "الف) تفاوت «سنجش از دور (RS)» و «سامانه اطلاعات جغرافیایی (GIS)» را بنویسید. (۲.۵)\nب) سه بخش اصلی سامانه موقعیت‌یاب جهانی (GPS) و دو کاربرد آن را نام ببرید. (۲.۵)",
                    officialAnswerKey = "الف) سنجش از دور (RS) دانش دریافت اطلاعات از پدیده‎ها بدون تماس مستقیم (با ماهواره و عکس هوایی) است (۱.۲۵)؛ اما GIS سامانه‌ای رایانه‌ای برای ذخیره، روی‌هم‌اندازی لایه‌ها، تحلیل و تولید نقشه از داده‌های مکانی است (۱.۲۵).\nب) سه بخش GPS: ۱. بخش فضایی (ماهواره‌ها) ۲. بخش کنترل زمینی ۳. بخش کاربری (گیرنده‌ها) (۱.۵) | کاربرد: ناوبری حمل‌ونقل، نقشه‌برداری و امدادرسانی (۱.۰)."
                )
            )
        ),

        // ۱۱. دفترچه کامل ۲۰ نمره‌ای هویت اجتماعی دوازدهم
        NahaeeFullExamBooklet(
            id = "full_nah_social_identity_1404_khordad",
            subjectId = "social_identity",
            subjectTitle = "دفترچه کامل امتحان نهایی هویت اجتماعی دوازدهم",
            sessionTitle = "نوبت خرداد ۱۴۰۴ (سراسری کشوری)",
            durationMinutes = 75,
            coefficientBadge = "ضریب ۳.۶۴ (درس ۲۰ نمره‌ساز و ترازآور)",
            sheetRows = listOf(
                NahaeeSheetRowItem(
                    rowNumber = 1,
                    sectionTitle = "بخش اول — کنش اجتماعی، پدیده‌های اجتماعی و هویت (۷ نمره)",
                    scoreBaram = "۷.۰ نمره",
                    questionPrompt = "الف) تفاوت «هویت فردی» و «هویت اجتماعی» را با ذکر دو مثال برای هر کدام بنویسید. (۳.۵)\nب) چرا «جامعه‌پذیری» شرط اصلی تداوم فرهنگ و شکل‌گیری هویت اجتماعی افراد است؟ (۳.۵)",
                    officialAnswerKey = "الف) هویت فردی ویژگی‌های شخصی و منحصربه‌فرد است (مانند اثر انگشت، گروه خونی یا بهره هوشی) (۱.۷۵)؛ اما هویت اجتماعی ناشی از عضویت در گروه‌ها و جامعه است (مانند ایرانی بودن، مسلمان بودن، معلم بودن) (۱.۷۵).\nب) زیرا نوزاد انسان در بدو تولد فاقد فرهنگ است و از طریق جامعه‌پذیری (خانواده، مدرسه، رسانه) هنجارها، ارزش‌ها و نقش‌های اجتماعی را درونی می‌کند و هویت جمعی می‌یابد (۳.۵)."
                ),
                NahaeeSheetRowItem(
                    rowNumber = 2,
                    sectionTitle = "بخش دوم — تحرک اجتماعی، قشربندی و بحران هویت (۶.۵ نمره)",
                    scoreBaram = "۶.۵ نمره",
                    questionPrompt = "الف) انواع «تحرک اجتماعی» (عمودی صعودی/نزولی، افقی، درون‌نسلی و میان‌نسلی) را با مثال توضیح دهید. (۴.۰)\nب) چه عواملی باعث ایجاد «بحران هویت» یا «تعارض نقش» در جامعه می‌شود؟ (۲.۵)",
                    officialAnswerKey = "الف) تحرک صعودی: ارتقای پایگاه (کارمند شدن یک کارگر) (۱.۰) | تحرک افقی: تغییر شغل بدون تغییر رتبه اجتماعی (۱.۰) | درون‌نسلی: تغییر پایگاه فرد در طول زندگی خودش (۱.۰) | میان‌نسلی: تفاوت پایگاه فرزند نسبت به پدر (مانند پزشک شدن فرزند یک کشاورز) (۱.۰).\nب) زمانی که میان ارزش‌های خانواده، مدرسه و رسانه‌ها تضاد به وجود آید یا جامعه دچار تغییرات ناگهانی فرهنگی بدون بازتعریف هویت شود (۲.۵)."
                ),
                NahaeeSheetRowItem(
                    rowNumber = 3,
                    sectionTitle = "بخش سوم — ابعاد هویت ایرانی-اسلامی و جهانی شدن (۶.۵ نمره)",
                    scoreBaram = "۶.۵ نمره",
                    questionPrompt = "الف) سه مؤلفه اصلی «هویت ملی و دینی ایرانیان» (زبان فارسی، اسلام و تشیع، تاریخ و جغرافیای مشترک) چگونه یکدیگر را تقویت کرده‌اند؟ (۳.۵)\nب) تفاوت «تعامل فرهنگی فعال» و «تهاجم فرهنگی / غرب‌زدگی» در عصر جهانی‌شدن چیست؟ (۳.۰)",
                    officialAnswerKey = "الف) ایرانیان با پذیرش آگاهانه اسلام و تشیع، فرهنگ خود را از محدوده قومی به تراز جهانی رساندند و زبان فارسی به عنوان زبان دوم جهان اسلام، حامل معارف الهی و عرفانی شد (۳.۵).\nب) در تعامل فعال، جامعه با حفظ اصول و هویت خود، دستاوردهای مفید دیگر فرهنگ‌ها را جذب و بومی‌سازی می‌کند (۱.۵)؛ اما در غرب‌زدگی، جامعه هویت خود را فراموش کرده و مصرف‌کننده منفعل فرهنگ مهاجم می‌شود (۱.۵)."
                )
            )
        ),

        // ۱۲. دفترچه کامل ۲۰ نمره‌ای سلامت و بهداشت دوازدهم
        NahaeeFullExamBooklet(
            id = "full_nah_health_1404_khordad",
            subjectId = "general_humanities",
            subjectTitle = "دفترچه کامل امتحان نهایی سلامت و بهداشت دوازدهم",
            sessionTitle = "نوبت خرداد ۱۴۰۴ (سراسری کشوری)",
            durationMinutes = 75,
            coefficientBadge = "ضریب ۳.۶۴ (ساده‌ترین ۲۰ قطعی نهایی)",
            sheetRows = listOf(
                NahaeeSheetRowItem(
                    rowNumber = 1,
                    sectionTitle = "بخش اول — ابعاد سلامت، سبک زندگی و تغذیه سالم (۷ نمره)",
                    scoreBaram = "۷.۰ نمره",
                    questionPrompt = "الف) چهار بُعد اصلی «سلامت» (جسمی، روانی، اجتماعی، معنوی) را نام برده و اهمیت سلامت معنوی را بنویسید. (۳.۰)\nب) شاخص توده بدنی (BMI) فردی با وزن ۷۲ کیلوگرم و قد ۱.۸۰ متر را محاسبه کرده و وضعیت وزنی او را مشخص کنید. (۲.۰)\nپ) تفاوت «چربی‌های اشباع و ترانس» با «چربی‌های غیراشباع» چیست؟ (۲.۰)",
                    officialAnswerKey = "الف) ابعاد جسمی، روانی، اجتماعی و معنوی (۱.۵). سلامت معنوی به زندگی معنا، امید و آرامش درونی می‌بخشد و سایر ابعاد سلامت را هدایت می‌کند (۱.۵).\nب) فرمول: BMI = وزن تقسیم بر مجذور قد = 72 / (1.8 × 1.8) = 72 / 3.24 = 22.22 (۱.۲۵) ⟵ چون بین ۱۸.۵ تا ۲۴.۹ است، در محدوده «وزن طبیعی (نرمال)» قرار دارد (۰.۷۵).\nپ) چربی‌های اشباع و اسید چرب ترانس (روغن جامد و فست‌فود) کلسترول بد خون (LDL) و خطر گرفتگی عروق را افزایش می‌دهند (۱.۰)؛ اما چربی‌های غیراشباع (روغن زیتون، ماهی و مغزها) حافظ سلامت قلب‌اند (۱.۰)."
                ),
                NahaeeSheetRowItem(
                    rowNumber = 2,
                    sectionTitle = "بخش دوم — پیشگیری از بیماری‌های غیرواگیر و واگیر (۷ نمره)",
                    scoreBaram = "۷.۰ نمره",
                    questionPrompt = "الف) سه سطح پیشگیری (سطح اول، سطح دوم، سطح سوم) را با ذکر مثال برای بیماری دیابت یا فشار خون توضیح دهید. (۳.۰)\nب) تفاوت «دیابت نوع ۱» و «دیابت نوع ۲» چیست؟ (۲.۰)\nپ) سه راه انتقال قطعی ویروس HIV (ایدز) و دو باور غلط درباره انتقال آن را بنویسید. (۲.۰)",
                    officialAnswerKey = "الف) پیشگیری سطح اول: جلوگیری از بروز بیماری با تغذیه سالم و ورزش (۱.۰) | سطح دوم: غربالگری، تشخیص زودهنگام و درمان سریع (۱.۰) | سطح سوم: بازتوانی و جلوگیری از عوارض و ناتوانی (۱.۰).\nب) دیابت نوع ۱ معمولاً در سنین کودکی/نوجوانی به علت توقف ترشح انسولین رخ می‌دهد و وابسته به تزریق انسولین است (۱.۰)؛ دیابت نوع ۲ بیشتر در بزرگسالی به علت مقاومت سلول‌ها به انسولین، چاقی و کم‌تحرکی ایجاد می‌شود (۱.۰).\nپ) راه‌های انتقال: خون و سرنگ آلوده، تماس جنسی محافظت‌نشده، از مادر مبتلا به نوزاد (۱.۲۵). از طریق دست دادن، روبوسی، ظروف غذا یا نیش حشرات منتقل نمی‌شود (۰.۷۵)."
                ),
                NahaeeSheetRowItem(
                    rowNumber = 3,
                    sectionTitle = "بخش سوم — سلامت روان، پیشگیری از اعتیاد و کمک‌های اولیه (۶ نمره)",
                    scoreBaram = "۶.۰ نمره",
                    questionPrompt = "الف) مهارت «نه گفتن قاطعانه» در برابر تعارف سیگار و مواد مخدر چه ویژگی‌هایی دارد؟ (۲.۰)\nب) اقدامات صحیح کمک‌های اولیه در مواجهه با «سوختگی سطحی» و «خونریزی بینی» چیست؟ (۲.۵)\nپ) سه مورد از حقوق بیمار و اصول مصرف منطقی دارو (پرهیز از مصرف خودسرانه آنتی‌بیوتیک) را بنویسید. (۱.۵)",
                    officialAnswerKey = "الف) بیان صریح، محترمانه و قاطعانه مخالفت با ارتباط چشمی مناسب، تغییر موضوع گفتگو یا ترک محیط پرخطر بدون احساس شرم (۲.۰).\nب) در سوختگی: سرد کردن محل با آب خنک جاری به مدت ۱۰ تا ۱۵ دقیقه و پرهیز از مالیدن خمیردندان یا ترکاندن تاول (۱.۲۵) | در خونریزی بینی: نشستن و خم کردن سر کمی به جلو و گرفتن بخش نرم بینی به مدت ۱۰ دقیقه (۱.۲۵).\nپ) تکمیل دوره کامل آنتی‌بیوتیک طبق دستور پزشک برای جلوگیری از مقاومت میکروبی، توجه به تاریخ انقضا و عدم تجویز داروی خود به دیگران (۱.۵)."
                )
            )
        )
    )

    fun getAllNahaeeQuestions(): List<NahaeeDescriptiveQuestion> = descriptiveNahaeeQuestions
}
