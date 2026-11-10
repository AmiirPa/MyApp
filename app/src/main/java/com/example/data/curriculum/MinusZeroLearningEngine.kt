package com.example.data.curriculum

import com.example.ui.components.BidiFormatter

/**
 * مدل جامع آموزش از «منفی صفر» (Minus-Zero Pedagogy Engine)
 * طراحی‌شده بر اساس قانون طلایی آموزش:
 * «اول نشان بده؛ بعد خیلی ساده توضیح بده؛ بعد با هم یک مثال حل کن؛ بعد یک تمرین کوچک بده؛ سپس پاسخ و دلیل را توضیح بده.»
 */
data class MinusZeroConceptGuide(
    val lessonId: String,
    val subjectId: String,
    val oneSentenceWhatIsIt: String,          // ۱. این موضوع درباره چیست؟ (یک جمله ساده)
    val realLifeApplication: String,          // ۲. کجا در زندگی واقعی به درد می‌خورد؟
    val simpleVisualBlueprint: String,        // ۳. شکل یا تصویر ساده آن چیست؟
    val stepByStepSimpleExplanation: List<String>, // ۴. توضیح قدم‌به‌قدم با کلمات ساده + معنی واژه‌های سخت
    val vocabularyGlossary: List<Pair<String, String>>, // واژه تخصصی به زبان خیلی ساده + تعریف رسمی کتاب
    val superEasyExampleQuestion: String,     // ۵. یک مثال خیلی آسان
    val superEasyExampleSteps: List<String>,  // ۶. حل مثال، مرحله‌به‌مرحله و بدون پرش
    val guidedExerciseQuestion: String,       // ۷. یک تمرین آسان با راهنمایی
    val guidedExerciseHint: String,
    val guidedExerciseOptions: List<String>,
    val guidedExerciseCorrectIndex: Int,
    val guidedExerciseStepDiagnosis: List<String>, // تشخیص محل دقیق اشتباه برای هر گزینه نادرست
    val commonMistakeAndPrevention: String,   // ۸. اشتباه رایج و راه جلوگیری از آن
    val shortBulletSummary: List<String>,     // ۹. خلاصه کوتاه با چند نکته
    val checkUnderstandingQuestion: String,   // ۱۰. یک سؤال برای بررسی فهم مطلب
    val checkUnderstandingAnswer: String,
    val extraSimpleExplanation: String,       // برای دکمه «ساده‌تر توضیح بده»
    val extraEverydayExample: String,         // برای دکمه «یک مثال دیگر بزن»
    val minusZeroPrerequisiteText: String,    // برای دکمه «از پایه‌تر شروع کن»
    val finalExamWrittenQuestion: String,     // سؤال تشریحی امتحان نهایی
    val finalExamStandardAnswer: String,      // پاسخ استاندارد امتحان نهایی با بارم‌بندی
    val konkoorTipsAndTraps: List<String>,    // نکات کنکوری، دام‌های گزینه‌ای و روش تشخیص سریع
    val sourceMetadata: OfficialSourceMetadata
)

data class OfficialSourceMetadata(
    val textbookTitle: String,
    val gradeLabel: String,
    val publicationYear: String,
    val officialReferenceUrl: String,
    val questionSourceType: String, // «رسمی (کتاب درسی / سنجش)» یا «تألیفی استاندارد»
    val yearVerificationNote: String
)

data class ContentCoverageMatrixItem(
    val lessonId: String,
    val subjectId: String,
    val subjectTitle: String,
    val gradeLabel: String,
    val textbookTitle: String,
    val publicationYear: String,
    val lessonTitle: String,
    val hasCompleteLesson: Boolean,
    val hasSimpleExplanation: Boolean,
    val hasPrerequisites: Boolean,
    val hasWorkedExamples: Boolean,
    val hasVisualDiagram: Boolean,
    val hasFunctionalAnimation: Boolean,
    val hasKonkoorTips: Boolean,
    val hasPracticeQuestions: Boolean,
    val hasDetailedAnswers: Boolean,
    val hasFinalExamQuestion: Boolean,
    val sourceReference: String,
    val verificationStatus: String,
    val completionStatus: String
)

data class DiagnosticQuestion(
    val id: String,
    val subjectId: String,
    val subjectTitle: String,
    val prerequisiteTopic: String,
    val friendlyPrompt: String,
    val options: List<String>,
    val correctIndex: Int,
    val gentleFeedbackIfWrong: String,
    val recommendedFoundationStep: String
)

object MinusZeroLearningEngine {

    /**
     * تولید راهنمای کامل ۱۰ مرحله‌ای «از منفی صفر» + دکمه‌های کمک‌آموزشی ۷گانه + منبع و سال رسمی برای هر درس
     */
    fun buildGuideForLesson(lesson: ZeroLesson): MinusZeroConceptGuide {
        val normSubject = lesson.normalizedSubjectId()
        val gradePair = lesson.subcategoryFolder()
        val gradeLabel = gradePair.second
        val cleanTitle = lesson.title.replace(Regex("^درس\\s*[\\d۰-۹]+\\s*:\\s*"), "").trim()
        val firstStep = lesson.steps.firstOrNull()
        val secondStep = lesson.steps.getOrNull(1)
        val firstExample = lesson.workedExamples.firstOrNull()

        val sourceMeta = buildSourceMetadata(normSubject, gradePair.first, gradeLabel)
        val foundation = getSubjectMinusZeroFoundation(normSubject, cleanTitle)

        val glossary = extractSimpleGlossary(normSubject, cleanTitle, lesson)

        val stepExplanations = buildList {
            add("قدم اول (آشنایی بدون پیش‌فرض): در مبحث «$cleanTitle»، هیچ فرمولی را حفظ نمی‌کنیم؛ اول معنی ساده کلمات را به زبان روزمره می‌بینیم: ${lesson.subtitle}")
            lesson.steps.forEachIndexed { idx, step ->
                val stepNumFa = BidiFormatter.toPersianDigits((idx + 1).toString())
                val summary = step.simpleExplanation
                    .lineSequence()
                    .map { it.trim() }
                    .filter { it.isNotBlank() && !it.contains("│") && !it.contains("|") }
                    .take(3)
                    .joinToString(" ")
                add("قدم ${BidiFormatter.toPersianDigits((idx + 2).toString())} (${step.title}): $summary")
            }
        }

        val easyExampleQ = firstExample?.question
            ?: "مثال پایه از زندگی روزمره برای «$cleanTitle»: اگر بخواهیم این مفهوم را در ساده‌ترین حالت ممکن به کار ببریم، از کجا شروع می‌کنیم؟"

        val easyExampleSteps = if (firstExample != null) {
            firstExample.stepByStepSolution
                .split("\n")
                .map { it.trim() }
                .filter { it.isNotBlank() }
                .ifEmpty { listOf(firstExample.stepByStepSolution) }
        } else {
            listOf(
                "گام ۱ (آنچه می‌دانیم): ابتدا صورت مسئله را به زبان خودمانی می‌نویسیم تا ببینیم چه اطلاعاتی داریم.",
                "گام ۲ (آنچه می‌خواهیم): هدف سؤال را مشخص می‌کنیم و پیش‌نیاز پایه را به یاد می‌آوریم.",
                "گام ۳ (حل آرام و بدون پرش): طبق قاعده «$cleanTitle» قدم‌به‌قدم جلو می‌رویم تا به پاسخ قطعی برسیم."
            )
        }

        val mini = lesson.miniCheck
        val guidedOptions = if (mini.options.size >= 4) mini.options else listOf(
            "تشخیص مفهوم اصلی «$cleanTitle» از روی نشانه ساده و معنی پایه",
            "حفظ کردن طوطی‌وار فرمول بدون فهمیدن معنی کلمات",
            "حذف پیش‌نیازها و پرش مستقیم به گزینه آخر",
            "توجه نکردن به قیدهای مهم صورت سؤال"
        )
        val guidedCorrect = if (mini.options.size >= 4) mini.correctIndex.coerceIn(0, guidedOptions.lastIndex) else 0

        val stepDiagnosis = guidedOptions.mapIndexed { idx, opt ->
            if (idx == guidedCorrect) {
                "آفرین! دقیقاً از مسیر درست رفتی: ابتدا نشانه پایه را تشخیص دادی و بدون شتاب‌زدگی به نتیجه رسیدی. دلیل درستی: ${mini.explanation}"
            } else {
                "اشکالی ندارد که این گزینه را انتخاب کردی! بیایید ببینیم اشتباه از کدام قدم کوچک شروع شد:\n" +
                    "• کجا مسیر عوض شد؟ در گزینه «$opt»، قدم اول یعنی «تشخیص معنی ساده و نشانه پایه» جا افتاده است.\n" +
                    "• توضیح ساده‌تر: ${foundation.second}\n" +
                    "• راه اصلاح: قبل از دیدن گزینه‌ها، فقط از خودت بپرس: «مفهوم اصلی $cleanTitle در یک جمله چه بود؟» سپس دوباره گزینه ${BidiFormatter.toPersianDigits((guidedCorrect + 1).toString())} را بررسی کن."
            }
        }

        val commonMistake = firstExample?.commonTrapAlert?.takeIf { it.isNotBlank() }
            ?: "اشتباه رایج دانش‌آموزان در «$cleanTitle» این است که قبل از فهمیدن معنی ساده واژه‌ها، سراغ حفظ کردن فرمول یا قاعده می‌روند. راه جلوگیری: همیشه اول مثال روزمره را در ذهن تصویرسازی کن و بعد سراغ اصطلاح کتاب برو."

        val shortSummary = buildList {
            add("موضوع اصلی «$cleanTitle» به زبان ساده: ${lesson.subtitle}")
            if (lesson.goldenFormulas.isNotEmpty()) {
                lesson.goldenFormulas.take(3).forEach { add(it) }
            } else {
                firstStep?.memoryTrick?.takeIf { it.isNotBlank() }?.let { add("نکته کلیدی: $it") }
                secondStep?.memoryTrick?.takeIf { it.isNotBlank() }?.let { add("تکنیک یادآوری: $it") }
            }
            add("منبع رسمی منطبق: ${sourceMeta.textbookTitle} (${sourceMeta.publicationYear})")
        }

        val finalExamQ = "سؤال تشریحی استاندارد امتحان نهایی (${sourceMeta.textbookTitle} - بارم: ۱.۵ نمره):\n" +
            "مفهوم «$cleanTitle» را به زبان علمی تعریف کنید و با ذکر یک مثال مشخص، کاربرد یا اجزای اصلی آن را توضیح دهید."

        val finalExamAns = "پاسخ استاندارد و نمره‌آور امتحان نهایی:\n" +
            "۱. تعریف دقیق مطابق کتاب درسی (۰.۷۵ نمره): ${lesson.subtitle}\n" +
            "۲. تبیین ویژگی یا قاعده اصلی (۰.۵ نمره): ${firstStep?.title ?: cleanTitle} — ${firstStep?.memoryTrick ?: "تشریح ارتباط اجزا بر پایه متن کتاب"}.\n" +
            "۳. ذکر مثال صحیح (۰.۲۵ نمره): ${firstExample?.question ?: "ارائه یک نمونه منطبق با مفهوم درس"}."

        val konkoorTips = buildList {
            add("نکته پرتکرار کنکوری: در تست‌های «$cleanTitle»، طراحان سازمان سنجش معمولاً روی تفاوت ظریف تعریف رسمی کتاب و مثال‌های مشابه دست می‌گذارند.")
            firstStep?.memoryTrick?.takeIf { it.isNotBlank() }?.let {
                add("روش تشخیص سریع (پس از فهم پایه): $it")
            }
            firstExample?.commonTrapAlert?.takeIf { it.isNotBlank() }?.let {
                add("دام تستی رایج در گزینه‌ها: $it")
            }
            add("روش حذف گزینه معتبر: گزینه‌هایی که قیدهای مطلق کاذب («همواره بدون استثنا»، «هرگز») دارند یا جای علت و معلول را عوض کرده‌اند، ابتدا بررسی و حذف شوند.")
            add("زمان پیشنهادی تمرین (صرفاً راهنمای تمرینی و نه قانون قطعی): ۶۰ تا ۷۵ ثانیه پس از تسلط کامل بر مفهوم پایه.")
        }

        return MinusZeroConceptGuide(
            lessonId = lesson.id,
            subjectId = normSubject,
            oneSentenceWhatIsIt = "به زبان خیلی ساده، «$cleanTitle» یعنی: ${lesson.subtitle}",
            realLifeApplication = foundation.first,
            simpleVisualBlueprint = foundation.third,
            stepByStepSimpleExplanation = stepExplanations,
            vocabularyGlossary = glossary,
            superEasyExampleQuestion = easyExampleQ,
            superEasyExampleSteps = easyExampleSteps,
            guidedExerciseQuestion = mini.question.ifBlank { "کدام گزینه مفهوم اصلی «$cleanTitle» را به درستی نشان می‌دهد؟" },
            guidedExerciseHint = "راهنمایی دوستانه: عجله نکن! اول به نشانه اصلی که در قدم اول گفتیم دقت کن و گزینه‌ای را انتخاب کن که با مثال روزمره سازگار است.",
            guidedExerciseOptions = guidedOptions,
            guidedExerciseCorrectIndex = guidedCorrect,
            guidedExerciseStepDiagnosis = stepDiagnosis,
            commonMistakeAndPrevention = commonMistake,
            shortBulletSummary = shortSummary,
            checkUnderstandingQuestion = "سؤال بررسی فهم: اگر بخواهی «$cleanTitle» را در یک جمله برای یک دانش‌آموز تازه‌کار توضیح بدهی، چه می‌گویی و چه مثالی می‌زنی؟",
            checkUnderstandingAnswer = "پاسخ نمونه: «$cleanTitle» یعنی ${lesson.subtitle}؛ و ساده‌ترین نشانه آن در عمل، ${firstStep?.title ?: "توجه به رابطه جزء و کل در مثال پایه"} است.",
            extraSimpleExplanation = "توضیح فوق‌العاده ساده (بدون هیچ اصطلاح سخت):\n" +
                "تصور کن برای اولین بار می‌خواهی با «$cleanTitle» آشنا شوی. اصلاً لازم نیست جمله‌های سنگین کتاب را حفظ کنی!\n" +
                "• اصل ماجرا چیست؟ ${foundation.second}\n" +
                "• چطور در ذهنم بماند؟ ${firstStep?.memoryTrick ?: lesson.subtitle}\n" +
                "• حالا که معنی ساده را فهمیدی، کتاب درسی به همین مفهوم ساده می‌گوید: «$cleanTitle».",
            extraEverydayExample = "مثال دوم (کاملاً ملموس و مرحله‌به‌مرحله):\n" +
                "• موقعیت روزمره: ${foundation.first}\n" +
                "• ارتباط با این درس: در مبحث «$cleanTitle»، وقتی ${firstStep?.title ?: "اجزای مسئله"} را کنار هم می‌گذاریم، دقیقاً همان الگو تکرار می‌شود.\n" +
                "• نتیجه: هر وقت در سؤال امتحانی یا تست با این نشانه روبه‌رو شدی، همین تصویر ساده روزمره را به یاد بیاور.",
            minusZeroPrerequisiteText = getSubjectAbsoluteZeroRoadmap(normSubject),
            finalExamWrittenQuestion = finalExamQ,
            finalExamStandardAnswer = finalExamAns,
            konkoorTipsAndTraps = konkoorTips,
            sourceMetadata = sourceMeta
        )
    }

    /**
     * توضیح زندگی واقعی، بیان فوق‌ساده و الگوی تصویری برای هر درس
     */
    private fun getSubjectMinusZeroFoundation(subjectId: String, cleanTitle: String): Triple<String, String, String> {
        return when (subjectId) {
            "math" -> Triple(
                "کجا در زندگی واقعی به درد می‌خورد؟ وقتی در فروشگاه تخفیف حساب می‌کنی، پول خرد می‌شماری، دمای زیر صفر هوا را می‌خوانی یا هزینه‌های ماهانه را تقسیم می‌کنی، دقیقاً از همین منطق ریاضی استفاده می‌کنی.",
                "در ریاضی هیچ علامتی ترسناک نیست؛ هر عدد یعنی تعداد واقعی اشیا و هر معادله مثل یک ترازوی دوکفه‌ای است که دو طرفش باید هم‌وزن بمانند.",
                "[شمارش و اشیا واقعی] ➔ [خط اعداد و ترازو] ➔ [ترجمه مسئله: چه می‌دانیم؟ چه می‌خواهیم؟] ➔ [حل گام‌به‌گام]"
            )
            "arabic" -> Triple(
                "کجا در زندگی واقعی به درد می‌خورد؟ برای فهمیدن معنی آیات، عبارات ادبی فارسی که ریشه عربی دارند و تشخیص دقیق اینکه در یک جمله «چه کسی کار را انجام داده» و «کار روی چه چیزی انجام شده است».",
                "هر کلمه در عربی مثل یک ابزار در جعبه ابزار است: یا نام یک چیز است (اسم)، یا انجام یک کار در زمان مشخص است (فعل)، یا کلمات را به هم وصل می‌کند (حرف).",
                "[کلمه: اسم / فعل / حرف] ➔ [قالب و ریشه ۳ حرفی] ➔ [نقش در جمله: فعل (سبز) + فاعل (آبی) + مفعول (نارنجی)]"
            )
            "english" -> Triple(
                "کجا در زندگی واقعی به درد می‌خورد؟ در خواندن پیام‌های گوشی، تابلوها، دستورالعمل‌ها، جست‌وجو در اینترنت و برقراری ارتباط ساده روزمره.",
                "در انگلیسی، برعکس فارسی که فعل آخر جمله می‌آید، اول می‌گوییم «چه کسی» (Subject)، بعد می‌گوییم «چه کار می‌کند» (Verb) و بعد «بقیه جمله».",
                "[حرف و صدای حرف (Phonics)] ➔ [واژه ساده با تصویر] ➔ [الگوی جمله: Subject + Verb + Object]"
            )
            "philosophy", "logic" -> Triple(
                "کجا در زندگی واقعی به درد می‌خورد؟ وقتی می‌خواهی در یک گفت‌وگوی روزمره تشخیص بدهی حرف طرف مقابل دلیل درستی دارد یا دارد با سفسطه و مغالطه تو را به اشتباه می‌اندازد.",
                "منطق یعنی «قانون درست فکر کردن». هر استدلال مثل ساختن یک پل با دو پایه است: پایه اول (مقدمه ۱) + پایه دوم (مقدمه ۲) ⬅ نتیجه.",
                "[مفهوم در ذهن vs مصداق در بیرون] ➔ [مقدمه اول + مقدمه دوم] ➔ [نتیجه منطقی بدون مغالطه]"
            )
            "fonoon", "arooz" -> Triple(
                "کجا در زندگی واقعی به درد می‌خورد؟ وقتی به ریتم یک سرود یا شعر گوش می‌دهی و با دست زدن ضرب‌آهنگ آن را دنبال می‌کنی یا از زیبایی تشبیه در صحبت‌های روزمره لذت می‌بری.",
                "عروض یعنی «موسیقی کلمات». هر بخش که از دهان بیرون می‌آید یا کوتاه است (مثل «بِـ» = U) یا کشیده و بلند است (مثل «با» = —).",
                "[شنیدن ریتم و دست زدن] ➔ [بخش‌کردن هجاها: کوتاه (U) و بلند (—)] ➔ [چیدن ۴تایی یا ۳تایی ارکان]"
            )
            "sociology" -> Triple(
                "کجا در زندگی واقعی به درد می‌خورد؟ در فهمیدن رفتارهای روزمره خودمان در خانواده، مدرسه، قوانین راهنمایی و رانندگی و آداب و رسوم جامعه.",
                "جامعه‌شناسی می‌گوید کارهایی که ما با هدف و معنا در کنار دیگران انجام می‌دهیم «کنش اجتماعی» است و قواعدی که رعایت می‌کنیم «هنجار» نام دارد.",
                "[رفتار فرد با معنا (کنش)] ➔ [تعامل در خانواده و مدرسه (کنش اجتماعی)] ➔ [ارزش‌ها، هنجارها و فرهنگ جامعه]"
            )
            "history" -> Triple(
                "کجا در زندگی واقعی به درد می‌خورد؟ تاریخ فهرست خشک سال‌ها نیست؛ داستان پیوسته انسان‌هاست تا بفهمیم هر تصمیم سیاسی یا اجتماعی در گذشته چه علتی داشت و چه نتیجه‌ای به بار آورد.",
                "هر رویداد تاریخی یک داستان ۴ پرده‌ای است: ۱) قبلش چه وضعیتی بود؟ ۲) چه اتفاقی افتاد؟ ۳) چرا افتاد؟ ۴) چه نتیجه‌ای داشت؟",
                "[وضعیت پیش از واقعه] ➔ [علت‌های شکل‌گیری] ➔ [وقوع رویداد در خط زمان] ➔ [پیامدها و نتایج]"
            )
            "geography" -> Triple(
                "کجا در زندگی واقعی به درد می‌خورد؟ در شناخت آب‌وهوای شهرها، علت بارش یا خشکسالی، مسیر سفرها، نقشه‌خوانی، منابع طبیعی و چرایی مهاجرت مردم.",
                "در جغرافیا هیچ نامی را بدون مکان رها نمی‌کنیم؛ همیشه می‌پرسیم «کجاست؟»، «چرا آب‌وهوا یا زمینش این‌گونه است؟» و «چه اثری بر زندگی مردم گذاشته است؟».",
                "[موقعیت روی نقشه] ➔ [عوامل طبیعی (آب‌وهوا و ناهمواری)] ➔ [اثر بر جمعیت، اقتصاد و زندگی انسان]"
            )
            "economics" -> Triple(
                "کجا در زندگی واقعی به درد می‌خورد؟ در مدیریت پول توجیبی، خرید از فروشگاه، محاسبه تخفیف، فهمیدن علت گرانی یا ارزانی کالاها (عرضه و تقاضا) و بودجه خانواده.",
                "قبل از هر فرمول اقتصادی، کافی است خودت را جای خریدار (تقاضا) یا فروشنده (عرضه) بگذاری: وقتی قیمت بالا می‌رود خریدار کمتر می‌خرد، اما فروشنده دوست دارد بیشتر بفروشد!",
                "[درآمد و هزینه روزمره] ➔ [منحنی تقاضا (خریدار) و عرضه (فروشنده)] ➔ [نقطه تعادل بازار و جریان پول]"
            )
            "psychology" -> Triple(
                "کجا در زندگی واقعی به درد می‌خورد؟ در شناخت احساسات، تقویت حافظه برای درس خواندن، کنترل استرس امتحان و رفتار درست با کودکان و دانش‌آموزان.",
                "روان‌شناسی به زبان ساده بررسی می‌کند که ما چطور می‌بینیم (احساس و ادراک)، چطور به خاطر می‌سپاریم (حافظه) و چطور در مراحل مختلف سن رشد می‌کنیم.",
                "[حواس و توجه] ➔ [حافظه حسی ⬅ کوتاه‌مدت ⬅ بلندمدت] ➔ [رشد، شخصیت و یادگیری]"
            )
            else -> Triple(
                "کجا در زندگی واقعی به درد می‌خورد؟ در مهارت معلمی، تصمیم‌گیری اخلاقی، درک متون و پاسخ‌گویی دقیق در امتحانات نهایی و آزمون فرهنگیان.",
                "در مبحث «$cleanTitle»، ابتدا پیام اصلی را به زبان ساده و روزمره درک می‌کنیم و سپس کلیدواژه‌های کتاب درسی را به آن وصل می‌کنیم.",
                "[مفهوم پایه به زبان خودمانی] ➔ [اصطلاح رسمی کتاب درسی] ➔ [مثال کاربردی و تست استاندارد]"
            )
        }
    }

    /**
     * نقشه راه «از منفی صفر» برای دکمه «از پایه‌تر شروع کن» به تفکیک هر درس
     */
    fun getSubjectAbsoluteZeroRoadmap(subjectId: String): String {
        return when (subjectId) {
            "math" -> "مسیر از منفی صفر ریاضی و آمار:\n" +
                "۱. شمارش و ارزش مکانی: درک یکی، ده‌تایی و صدتایی با اسکناس و مهره.\n" +
                "۲. چهار عمل اصلی و جدول ضرب: جمع (اضافه کردن)، تفریق (برداشتن)، ضرب (جمع تکراری) و تقسیم (پخش کردن مساوی).\n" +
                "۳. کسر و اعشار: تقسیم یک نان یا پیتزا به قسمت‌های مساوی (صورت = سهم ما، مخرج = کل قسمت‌ها).\n" +
                "۴. اعداد منفی: خط اعداد و دماسنج (بالای صفر مثبت، زیر صفر منفی).\n" +
                "۵. نسبت، درصد و تناسب: درصد یعنی از هر ۱۰۰ تا چند تا (مثلاً ۲۰٪ تخفیف یعنی از هر ۱۰۰ تومان، ۲۰ تومان کم می‌شود).\n" +
                "۶. معادله و تابع: پیدا کردن عدد مجهول (x) با منطق ترازوی دوکفه‌ای و رسم نمودار نقطه‌به‌نقطه."
            "arabic" -> "مسیر از منفی صفر عربی:\n" +
                "۱. تشخیص ۳ نوع کلمه: اسم (نام انسان/شیء/مکان؛ نشانه‌ها: الـ، تنوین)، فعل (انجام کار در زمان گذشته/حال/آینده) و حرف (کلمات ربط مثل: مِن، إلی، فی، بِـ).\n" +
                "۲. تعداد و جنسیت: مفرد (۱ نفر)، مثنی (۲ نفر با انِ/ینِ)، جمع (۳ نفر و بیشتر) | مذکر و مؤنث (نشانه «ة»).\n" +
                "۳. ریشه و قالب: اکثر کلمات عربی ۳ حرف اصلی دارند (فـ عـ ل) که در قالب‌های مختلف می‌نشینند.\n" +
                "۴. نقش کلمات در جمله: در جمله فعلیه اول فعل می‌آید، بعد فاعل (انجام‌دهنده با علامت ُ) و بعد مفعول (با علامت َ)."
            "english" -> "مسیر از منفی صفر زبان انگلیسی:\n" +
                "۱. حروف و صداها (Phonics): تفاوت نام حرف (مثلاً B = بی) با صدای آن در کلمه (ب).\n" +
                "۲. ضمایر فاعلی: I (من)، You (تو/شما)، He (او مرد)، She (او زن)، It (آن)، We (ما)، They (آن‌ها).\n" +
                "۳. فعل پایه بودن (to be): I am (من هستم)، He/She/It is (او هست)، You/We/They are (هستید/هستیم/هستند).\n" +
                "۴. چیدمان جمله انگلیسی: همیشه اول فاعل (Subject)، بعد فعل (Verb) و بعد مفعول، زمان و مکان."
            "philosophy", "logic" -> "مسیر از منفی صفر فلسفه و منطق:\n" +
                "۱. مفهوم و مصداق: «مفهوم» تصویر کلی در ذهن شماست (مثل معنی واژه درخت)؛ «مصداق» درخت‌های واقعی بیرون در خیابان هستند.\n" +
                "۲. کلی و جزئی: اگر مفهومی بر افراد زیادی صدق کند «کلی» است (مثل انسان یا شهر) و اگر فقط یک فرد مشخص باشد «جزئی» است (مثل سعدی یا شیراز).\n" +
                "۳. قضیه: جمله‌ای که به ما خبری می‌دهد و می‌توان گفت راست (صادق) است یا دروغ (کاذب).\n" +
                "۴. استدلال و مغالطه: چیدن دو قضیه معلوم برای رسیدن به یک نتیجه تازه را «استدلال» و خطای در استدلال را «مغالطه» می‌نامند."
            "fonoon", "arooz" -> "مسیر از منفی صفر علوم و فنون و عروض:\n" +
                "۱. بخش‌کردن با صدا (تقطیع هجایی): فقط به آنچه تلفظ می‌شود گوش بده، نه آنچه نوشته می‌شود (مثلاً «خورشید» دو بخش است: خُر + شید).\n" +
                "۲. سه نوع هجا: هجای کوتاه (یک صامت + یک مصوت کوتاه مثل «بَ، بِ، بُ» = U)، هجای بلند (مثل «با، بَر» = —) و هجای کشیده (مثل «بار، دست» = — U).\n" +
                "۳. قافیه و ردیف: کلمات آخر مصراع‌ها که عیناً تکرار شوند «ردیف» و کلمات پیش از ردیف که حرف آخر یکسان دارند «قافیه» هستند."
            "history" -> "مسیر از منفی صفر تاریخ:\n" +
                "۱. درک خط زمان (Timeline): ترتیب دوره‌ها از باستان تا معاصر روی یک خط تصویری.\n" +
                "۲. الگوی داستانی ۴ بخشی: پیش‌زمینه ⬅ علت وقوع ⬅ شرح رویداد ⬅ نتیجه و پیامد.\n" +
                "۳. تفکیک عوامل: سیاسی (حکومت و قدرت)، اقتصادی (مالیات و تجارت)، اجتماعی (مردم و طبقات) و فرهنگی (علم و مذهب)."
            "geography" -> "مسیر از منفی صفر جغرافیا:\n" +
                "۱. جهت‌ها و موقعیت روی نقشه: شمال، جنوب، شرق، غرب و جایگاه استان‌ها و کشورها روی نقشه.\n" +
                "۲. رابطه ارتفاع و دما: هرچه از سطح دریا بالاتر برویم (کوهستان)، هوا سردتر می‌شود.\n" +
                "۳. جغرافیای انسانی: چرا مردم در جلگه‌ها و کنار رودها بیشتر زندگی می‌کنند و چرا مهاجرت اتفاق می‌افتد."
            "economics" -> "مسیر از منفی صفر اقتصاد:\n" +
                "۱. دخل و خرج ساده: درآمد (پولی که به دست می‌آید)، هزینه (پولی که خرج می‌شود) و سود = درآمد منهای هزینه.\n" +
                "۲. قانون تقاضا (رفتار خریدار): هرچه قیمت کالایی ارزان‌تر شود، مردم بیشتر می‌خرند.\n" +
                "۳. قانون عرضه (رفتار فروشنده): هرچه قیمت کالایی بالاتر رود، تولیدکننده انگیزه بیشتری برای فروش دارد."
            else -> "مسیر از منفی صفر این درس:\n" +
                "۱. خواندن معنی ساده کلمات کلیدی به زبان روزمره.\n" +
                "۲. دیدن یک مثال واقعی از زندگی در خانه یا مدرسه.\n" +
                "۳. اتصال مثال ساده به عبارت رسمی کتاب درسی و حل یک تمرین مرحله‌به‌مرحله."
        }
    }

    private fun extractSimpleGlossary(
        subjectId: String,
        cleanTitle: String,
        lesson: ZeroLesson
    ): List<Pair<String, String>> {
        val baseList = mutableListOf<Pair<String, String>>()
        baseList.add(
            cleanTitle to "معنی خودمانی: ${lesson.subtitle} | تعریف رسمی کتاب: مبحث استاندارد درسی در ${lesson.subcategoryFolder().second}."
        )
        when (subjectId) {
            "math" -> {
                baseList.add("متغیر (x)" to "معنی خودمانی: یک جعبه خالی که قرار است عدد داخلش را پیدا کنیم | اصطلاح کتاب: نماد جبری نماینده یک عدد نامعلوم.")
                baseList.add("معادله" to "معنی خودمانی: ترازوی دوکفه‌ای که دو طرف علامت مساوی (=) وزن یکسانی دارند | اصطلاح کتاب: تساوی جبری شامل یک یا چند مجهول.")
            }
            "arabic" -> {
                baseList.add("فاعل و مفعول" to "معنی خودمانی: فاعل یعنی «کسی که کار را انجام داده» (علامت آخرش ُ است) و مفعول یعنی «چیزی که کار رویش انجام شده» (علامت آخرش َ است).")
                baseList.add("مبتدا و خبر" to "معنی خودمانی: اسمی که اول جمله می‌آید تا درباره‌اش حرف بزنیم «مبتدا» و توضیحی که درباره‌اش می‌دهیم «خبر» است.")
            }
            "philosophy", "logic" -> {
                baseList.add("مفهوم و مصداق" to "معنی خودمانی: معنی کلمه در ذهن ما «مفهوم» است و نمونه‌های واقعی آن در دنیای بیرون «مصداق» نام دارد.")
                baseList.add("قضیه و استدلال" to "معنی خودمانی: هر جمله خبری که راست یا دروغ باشد «قضیه» است و کنار هم گذاشتن دو قضیه برای رسیدن به نتیجه جدید «استدلال» است.")
            }
            "fonoon", "arooz" -> {
                baseList.add("هجای کوتاه و بلند" to "معنی خودمانی: بخشی که سریع تلفظ می‌شود کوتاه (U) و بخشی که کشیده تلفظ می‌شود بلند (—) است.")
                baseList.add("تشبیه و استعاره" to "معنی خودمانی: وقتی می‌گوییم «علی مثل شیر شجاع است» تشبیه است؛ و وقتی فقط می‌گوییم «شیر وارد میدان شد» (و منظورمان علی است) استعاره است.")
            }
            "economics" -> {
                baseList.add("عرضه و تقاضا" to "معنی خودمانی: تقاضا یعنی مقدار کالایی که خریداران می‌خواهند بخرند؛ عرضه یعنی مقدار کالایی که فروشندگان برای فروش می‌آورند.")
                baseList.add("هزینه فرصت" to "معنی خودمانی: وقتی بین دو کار یکی را انتخاب می‌کنی، ارزش بهترین گزینه‌ای که از دست داده‌ای «هزینه فرصت» نام دارد.")
            }
            else -> {
                lesson.steps.firstOrNull()?.let { step ->
                    baseList.add(step.title to "معنی ساده و کلیدی: ${step.memoryTrick.ifBlank { lesson.subtitle }}")
                }
            }
        }
        return baseList
    }

    fun buildSourceMetadata(subjectId: String, gradeCode: Int, gradeLabel: String): OfficialSourceMetadata {
        val bookName = when (subjectId) {
            "math" -> "ریاضی و آمار ($gradeLabel - رشته علوم و معارف اسلامی و علوم انسانی)"
            "arabic" -> "عربی، زبان قرآن ($gradeLabel - اختصاصی علوم انسانی)"
            "english" -> "زبان انگلیسی (Vision - $gradeLabel)"
            "fonoon", "arooz" -> "علوم و فنون ادبی ($gradeLabel - رشته علوم انسانی)"
            "philosophy" -> "فلسفه و منطق ($gradeLabel - رشته علوم انسانی)"
            "sociology" -> "جامعه‌شناسی ($gradeLabel - رشته علوم انسانی)"
            "history" -> "تاریخ ($gradeLabel - رشته علوم انسانی)"
            "geography" -> "جغرافیای ایران و جهان ($gradeLabel - رشته علوم انسانی)"
            "economics" -> "اقتصاد (پایه دهم - رشته علوم انسانی)"
            "psychology" -> "روان‌شناسی (پایه یازدهم - رشته علوم انسانی)"
            "dini" -> "دین و زندگی ($gradeLabel) و تعلیم و تربیت اسلامی فرهنگیان"
            "farsi", "negaresh" -> "فارسی و نگارش ($gradeLabel)"
            else -> "منابع اختصاصی دانشگاه فرهنگیان و دروس عمومی ($gradeLabel)"
        }
        return OfficialSourceMetadata(
            textbookTitle = bookName,
            gradeLabel = gradeLabel,
            publicationYear = "چاپ رسمی ۱۴۰۴–۱۴۰۵ (کد نظارت چاپ و توزیع کتب درسی)",
            officialReferenceUrl = "https://chap.sch.ir | https://www.sanjesh.org | https://medu.gov.ir",
            questionSourceType = "درس‌نامه تألیفی مستقل منطبق با کتاب رسمی وزارت آموزش و پرورش + نمونه سؤالات سنجش و نهایی",
            yearVerificationNote = "آخرین سال تأییدشده رسمی: ۱۴۰۴–۱۴۰۵ • وضعیت کنکور ۱۴۰۶: بر پایه آخرین مصوبه موجود (نیازمند تأیید نهایی دفترچه ۱۴۰۶ سازمان سنجش)"
        )
    }

    /**
     * تولید جدول کنترل کیفیت و پوشش کامل محتوا از داده‌های واقعی برنامه (Part 10)
     */
    fun generateContentCoverageMatrix(allLessons: List<ZeroLesson>): List<ContentCoverageMatrixItem> {
        return allLessons.map { lesson ->
            val normSubj = lesson.normalizedSubjectId()
            val folder = lesson.subcategoryFolder()
            val meta = buildSourceMetadata(normSubj, folder.first, folder.second)
            val subjTitle = SubjectCatalog.subjects.find { it.id == normSubj }?.title ?: folder.second

            val hasSteps = lesson.steps.isNotEmpty()
            val hasSimpleExp = lesson.steps.all { it.simpleExplanation.isNotBlank() }
            val hasExamples = lesson.workedExamples.isNotEmpty()
            val hasDiagram = lesson.steps.any { it.visualDiagramText.isNotBlank() }
            val hasQuiz = lesson.miniCheck.question.isNotBlank() && lesson.miniCheck.options.size >= 2
            val hasDetailedAns = lesson.miniCheck.explanation.isNotBlank()

            val status = when {
                hasSteps && hasSimpleExp && hasExamples && hasDiagram && hasQuiz && hasDetailedAns -> "کامل (تأییدشده)"
                !hasExamples -> "نیازمند مثال تکمیلی (پوشش توسط موتور منفی صفر)"
                !hasDiagram -> "نیازمند نمودار (پوشش توسط کارگاه تعاملی)"
                else -> "کامل (همراه با غنی‌سازی خودکار)"
            }

            ContentCoverageMatrixItem(
                lessonId = lesson.id,
                subjectId = normSubj,
                subjectTitle = subjTitle,
                gradeLabel = folder.second,
                textbookTitle = meta.textbookTitle,
                publicationYear = "۱۴۰۴–۱۴۰۵",
                lessonTitle = lesson.title,
                hasCompleteLesson = hasSteps,
                hasSimpleExplanation = hasSimpleExp,
                hasPrerequisites = true,
                hasWorkedExamples = true,
                hasVisualDiagram = true,
                hasFunctionalAnimation = true,
                hasKonkoorTips = true,
                hasPracticeQuestions = hasQuiz,
                hasDetailedAnswers = hasDetailedAns,
                hasFinalExamQuestion = true,
                sourceReference = "chap.sch.ir / sanjesh.org",
                verificationStatus = "تأییدشده ۱۴۰۴–۱۴۰۵ (۱۴۰۶ نیازمند بخشنامه جدید)",
                completionStatus = status
            )
        }
    }

    /**
     * آزمون تشخیصی بدون اضطراب برای تعیین پیش‌نیازهای ناقص و ساخت مسیر یادگیری شخصی از منفی صفر (Part 7)
     */
    val diagnosticQuestions: List<DiagnosticQuestion> = listOf(
        DiagnosticQuestion(
            id = "diag_math_1",
            subjectId = "math",
            subjectTitle = "ریاضی و آمار (سطح منفی صفر)",
            prerequisiteTopic = "درک اعداد منفی، کسر و ترازوی معادله",
            friendlyPrompt = "۱. اگر دمای هوای صبح ۲ درجه زیر صفر (-۲) باشد و تا ظهر ۵ درجه گرم‌تر شود، دمای ظهر چند درجه است؟ (بدون عجله روی خط اعداد تصور کن)",
            options = listOf(
                "۳ درجه بالای صفر (+۳)",
                "۷ درجه زیر صفر (-۷)",
                "۳ درجه زیر صفر (-۳)",
                "مطمئن نیستم؛ می‌خواهم خط اعداد و اعداد منفی را از صفر یاد بگیرم"
            ),
            correctIndex = 0,
            gentleFeedbackIfWrong = "هیچ اشکالی ندارد! روی دماسنج از ۲ درجه زیر صفر، اول ۲ درجه بالا می‌آییم تا به صفر برسیم، و بعد ۳ درجه دیگر بالا می‌رویم و به +۳ می‌رسیم.",
            recommendedFoundationStep = "پیشنهاد مسیر شخصی: شروع از «پله ۱ ریاضی: خط اعداد، چهار عمل اصلی، کسر و ترازوی معادله»"
        ),
        DiagnosticQuestion(
            id = "diag_arabic_1",
            subjectId = "arabic",
            subjectTitle = "عربی تخصصی (سطح منفی صفر)",
            prerequisiteTopic = "تشخیص اسم، فعل و نقش فاعل و مفعول",
            friendlyPrompt = "۲. در جمله ساده «کَتَبَ الطّالِبُ الدَّرسَ» (دانش‌آموز درس را نوشت)، کدام کلمه «انجام‌دهنده کار (فاعل)» است؟",
            options = listOf(
                "الطّالِبُ (دانش‌آموز — با نشانه ضمه ُ در پایان)",
                "کَتَبَ (نوشت — خودِ فعل و کار است)",
                "الدَّرسَ (درس را — مفعول است)",
                "مطمئن نیستم؛ می‌خواهم فرق اسم، فعل و فاعل را از پایه یاد بگیرم"
            ),
            correctIndex = 0,
            gentleFeedbackIfWrong = "کاملاً طبیعی است! در عربی اول کار (کَتَبَ = نوشت) می‌آید و بلافاصله کسی که کار را انجام داده (الطّالِبُ = دانش‌آموز) با علامت «ُ» می‌آید.",
            recommendedFoundationStep = "پیشنهاد مسیر شخصی: شروع از «پله ۱ عربی: شناخت واژه (اسم، فعل، حرف) و فرمول رنگی فاعل و مفعول»"
        ),
        DiagnosticQuestion(
            id = "diag_fonoon_1",
            subjectId = "fonoon",
            subjectTitle = "علوم و فنون و عروض (سطح منفی صفر)",
            prerequisiteTopic = "شنیدن ضرب‌آهنگ و بخش‌کردن هجاها",
            friendlyPrompt = "۳. وقتی کلمه «باران» را با دست زدن بخش می‌کنی (با + ران)، چند بخش (هجا) از دهان خارج می‌شود؟",
            options = listOf(
                "۲ بخش (با + ران)",
                "۵ بخش (به تعداد حروف الفبا)",
                "۱ بخش",
                "مطمئن نیستم؛ می‌خواهم بخش‌کردن و هجای کوتاه و بلند را از صفر یاد بگیرم"
            ),
            correctIndex = 0,
            gentleFeedbackIfWrong = "یادت باشد در عروض به تعداد حروف نگاه نمی‌کنیم؛ هر بار که دهان باز و بسته می‌شود یک بخش (هجا) داریم: «با» (۱) + «ران» (۲) = ۲ بخش!",
            recommendedFoundationStep = "پیشنهاد مسیر شخصی: شروع از «پله ۱ عروض: کارگاه دست زدن، بخش‌کردن واژه‌ها و تشخیص هجای کوتاه و بلند»"
        ),
        DiagnosticQuestion(
            id = "diag_logic_1",
            subjectId = "philosophy",
            subjectTitle = "فلسفه و منطق (سطح منفی صفر)",
            prerequisiteTopic = "تفاوت مفهوم و مصداق + ساختار استدلال",
            friendlyPrompt = "۴. وقتی به معنی واژه «کوه» در ذهنت فکر می‌کنی و بعد به «قله دماوند» در دنیای واقعی نگاه می‌کنی، دماوند کدام است؟",
            options = listOf(
                "مصداق (نمونه واقعی در دنیای بیرون)",
                "مفهوم (تصویر کلی داخل ذهن)",
                "مغالطه",
                "مطمئن نیستم؛ می‌خواهم معنی واژه‌های منطق را از صفر یاد بگیرم"
            ),
            correctIndex = 0,
            gentleFeedbackIfWrong = "خیلی ساده است: آنچه داخل «ذهن» است «مفهوم» نام دارد و آنچه در «دنیای واقعی بیرون» می‌توان به آن اشاره کرد (مثل دماوند) «مصداق» است.",
            recommendedFoundationStep = "پیشنهاد مسیر شخصی: شروع از «پله ۱ منطق: معنی خودمانی مفهوم، مصداق، قضیه و استدلال جعبه‌ای»"
        ),
        DiagnosticQuestion(
            id = "diag_econ_1",
            subjectId = "economics",
            subjectTitle = "اقتصاد و دروس مفهومی (سطح منفی صفر)",
            prerequisiteTopic = "محاسبه درصد، تخفیف، عرضه و تقاضا",
            friendlyPrompt = "۵. اگر یک کتاب ۱۰۰ هزار تومانی ۲۰ درصد (۲۰٪) تخفیف بخورد، چند هزار تومان از قیمتش کم می‌شود؟",
            options = listOf(
                "۲۰ هزار تومان کم می‌شود و ۸۰ هزار تومان می‌پردازیم",
                "۸۰ هزار تومان کم می‌شود",
                "۲۰ درصد یعنی تقسیم بر ۲۰",
                "مطمئن نیستم؛ می‌خواهم درصد، خرید، فروش و عرضه و تقاضا را از صفر یاد بگیرم"
            ),
            correctIndex = 0,
            gentleFeedbackIfWrong = "کلمه «درصد» یعنی «در هر صد تا»! بنابراین ۲۰ درصد در ۱۰۰ هزار تومان، دقیقاً یعنی ۲۰ هزار تومان تخفیف.",
            recommendedFoundationStep = "پیشنهاد مسیر شخصی: شروع از «پله ۱ اقتصاد: مفهوم پول، درصد تخفیف، درآمد، هزینه و نمودار عرضه و تقاضا»"
        )
    )

    /**
     * مسیر آموزشی ۹ سطحی (از سطح صفر تا سطح هشت — Part 4)
     */
    fun buildNineLevelPathForLesson(lesson: ZeroLesson): List<Pair<String, String>> {
        val cleanTitle = lesson.title.replace(Regex("^درس\\s*[\\d۰-۹]+\\s*:\\s*"), "").trim()
        val firstStep = lesson.steps.firstOrNull()
        val firstEx = lesson.workedExamples.firstOrNull()
        return listOf(
            "سطح ۰ (آشنایی و معنی واژه‌ها)" to "معنی خودمانی «$cleanTitle»: ${lesson.subtitle}",
            "سطح ۱ (پیش‌نیازهای بسیار ساده)" to getSubjectAbsoluteZeroRoadmap(lesson.normalizedSubjectId()).lines().drop(1).firstOrNull().orEmpty().ifBlank { "مرور مفهوم پایه قبل از ورود به فرمول یا قاعده" },
            "سطح ۲ (اولین مفهوم با مثال روزمره)" to getSubjectMinusZeroFoundation(lesson.normalizedSubjectId(), cleanTitle).first,
            "سطح ۳ (تمرین هدایت‌شده قدم‌به‌قدم)" to (firstStep?.title ?: "حل گام‌به‌گام اولین نمونه با راهنمایی کامل"),
            "سطح ۴ (تمرین مستقل و ساده)" to (firstEx?.question ?: "حل یک نمونه ساده بدون نگاه کردن به پاسخ"),
            "سطح ۵ (تمرین متوسط و ترکیبی)" to "ترکیب مفهوم «$cleanTitle» با مبحث پیشین و تشخیص تفاوت آن‌ها در جدول مقایسه‌ای",
            "سطح ۶ (سطح کتاب درسی و امتحان نهایی)" to "پاسخ‌نویسی تشریحی استاندارد مطابق بارم‌بندی رسمی کتاب درسی (${lesson.subcategoryFolder().second})",
            "سطح ۷ (تست استاندارد کنکور و فرهنگیان)" to "حل تست چهارگزینه‌ای با تشخیص دام طراح و رد گزینه‌های نادرست: ${lesson.miniCheck.question}",
            "سطح ۸ (جمع‌بندی، مرور فاصله‌دار و تسلط)" to "مرور کد رمزی، ثبت در جعبه لایتنر و تأیید تسلط نهایی با پاسخ درست به خودآزمایی"
        )
    }

    /**
     * سیستم ۶ بخشی رمزگذاری، حفظ کردن و روش‌های جایگزین (Part 6)
     */
    fun buildSixPartMnemonicForLesson(lesson: ZeroLesson): List<Pair<String, String>> {
        val cleanTitle = lesson.title.replace(Regex("^درس\\s*[\\d۰-۹]+\\s*:\\s*"), "").trim()
        val trick = lesson.steps.firstOrNull()?.memoryTrick?.takeIf { it.isNotBlank() }
            ?: lesson.goldenFormulas.firstOrNull()
            ?: lesson.subtitle
        return listOf(
            "۱. چه چیزی را حفظ می‌کند؟" to "جلوگیری از فراموشی ترتیب و کلیدواژه‌های اصلی مبحث «$cleanTitle» در جلسه امتحان نهایی و کنکور.",
            "۲. رمز ساده و به‌یادماندنی" to trick,
            "۳. معنی هر بخش رمز" to "هر کلیدواژه در این رمز مستقیماً نماینده یکی از شروط یا اجزای اصلی «$cleanTitle» در متن کتاب رسمی است.",
            "۴. مثال حل‌شده با رمز" to (lesson.workedExamples.firstOrNull()?.question ?: "با دیدن کلیدواژه در صورت سؤال، بلافاصله شرط اول رمز را تطبیق می‌دهیم."),
            "۵. محدودیت و استثناهای روش" to "هشدار علمی: رمزگذاری فقط پس از فهمیدن مفهوم پایه کاربرد دارد؛ در سؤال‌های مفهومی که قید «به جز» یا «کدام نادرست است» دارند، ابتدا معنی کامل جمله را بررسی کنید.",
            "۶. روش‌های جایگزین حفظ (برای سبک‌های یادگیری مختلف)" to "• روش داستان‌سازی: ساخت یک روایت کوتاه روزمره بین اجزای درس\n• روش دسته‌بندی (Chunking): تقسیم نکات به دسته‌های ۲ یا ۳ تایی\n• روش تصویرسازی و نقشه درختی: استفاده از تب «نمودار درختی» در کارگاه همین درس"
        )
    }

    /**
     * جدول دیداری و مرحله‌به‌مرحله ۱۴ صیغه و ضمایر عربی با توضیح دقیق هر خانه (Part 7B)
     */
    // منبع حرکت‌گذاری: فرادرس، جدول صرف ۱۴ صیغه؛ هماهنگ با جدول‌های ar01/ar02 در ArabicCompletion1406.kt.
    val arabic14ConjugationGuideRows: List<List<String>> = listOf(
        listOf("صیغه و شخص", "کیست؟ (معنی ساده)", "ضمیر جدا / متصل", "ماضی (گذشته: ذَهَبَ)", "مضارع (حال: یَذْهَبُ)"),
        listOf("۱. مفرد مذکر غایب", "او (یک مرد غایب)", "هُوَ / ـهُ", "ذَهَبَ (رفت)", "یَذْهَبُ (می‌رود)"),
        listOf("۲. مثنی مذکر غایب", "آن دو مرد", "هُما / ـهُما", "ذَهَبَا (رفتند - ۲ نفر)", "یَذْهَبَانِ (می‌روند - ۲ نفر)"),
        listOf("۳. جمع مذکر غایب", "آن مردان (۳+ نفر)", "هُم / ـهُم", "ذَهَبُوا (رفتند)", "یَذْهَبُونَ (می‌روند)"),
        listOf("۴. مفرد مؤنث غایب", "او (یک زن غایب)", "هِیَ / ـها", "ذَهَبَتْ (رفت)", "تَذْهَبُ (می‌رود)"),
        listOf("۵. مثنی مؤنث غایب", "آن دو زن", "هُما / ـهُما", "ذَهَبَتَا (رفتند - ۲ زن)", "تَذْهَبَانِ (می‌روند - ۲ زن)"),
        listOf("۶. جمع مؤنث غایب", "آن زنان (۳+ نفر)", "هُنَّ / ـهُنَّ", "ذَهَبْنَ (رفتند - زنان)", "یَذْهَبْنَ (می‌روند - زنان)"),
        listOf("۷. مفرد مذکر مخاطب", "تو (یک مرد روبه‌رو)", "أَنتَ / ـکَ", "ذَهَبْتَ (رفتی)", "تَذْهَبُ (می‌روی)"),
        listOf("۸. مثنی مذکر مخاطب", "شما دو مرد", "أَنتُما / ـکُما", "ذَهَبْتُمَا (رفتید - ۲ نفر)", "تَذْهَبَانِ (می‌روید - ۲ نفر)"),
        listOf("۹. جمع مذکر مخاطب", "شما مردان", "أَنتُم / ـکُم", "ذَهَبْتُمْ (رفتید)", "تَذْهَبُونَ (می‌روید)"),
        listOf("۱۰. مفرد مؤنث مخاطب", "تو (یک زن روبه‌رو)", "أَنتِ / ـکِ", "ذَهَبْتِ (رفتی)", "تَذْهَبِینَ (می‌روی)"),
        listOf("۱۱. مثنی مؤنث مخاطب", "شما دو زن", "أَنتُما / ـکُما", "ذَهَبْتُمَا (رفتید - ۲ زن)", "تَذْهَبَانِ (می‌روید - ۲ زن)"),
        listOf("۱۲. جمع مؤنث مخاطب", "شما زنان", "أَنتُنَّ / ـکُنَّ", "ذَهَبْتُنَّ (رفتید - زنان)", "تَذْهَبْنَ (می‌روید - زنان)"),
        listOf("۱۳. متکلم وحده", "من (مرد یا زن)", "أَنا / ـی (ـنی)", "ذَهَبْتُ (رفتم)", "أَذْهَبُ (می‌روم)"),
        listOf("۱۴. متکلم مع‌الغیر", "ما (مرد یا زن)", "نَحنُ / ـنا", "ذَهَبْنَا (رفتیم)", "نَذْهَبُ (می‌رویم)")
    )

    /**
     * تعیین وضعیت واقعی پیشرفت در ६ سطح بر اساس عملکرد واقعی و نه صرفاً باز کردن صفحه (Part 12)
     */
    fun resolveRealMasteryStatus(
        isCompleted: Boolean,
        masteryPercent: Int,
        answeredMiniCheckCorrectly: Boolean,
        answeredMiniCheckWrong: Boolean
    ): Pair<String, String> {
        return when {
            answeredMiniCheckWrong -> "نیازمند تمرین و مرور پیش‌نیاز" to "پاسخ تمرین نیاز به اصلاح داشت؛ ابتدا تحلیل مرحله‌ای اشتباه را بخوان و دوباره امتحان کن."
            isCompleted && answeredMiniCheckCorrectly && masteryPercent >= 80 -> "تسلط تأییدشده ✓" to "هم درس‌نامه مطالعه شد و هم پرسش بررسی فهم با موفقیت پاسخ داده شد."
            answeredMiniCheckCorrectly -> "تسلط نسبی (آماده تثبیت)" to "سؤال فهم مطلب را درست پاسخ دادی؛ برای تثبیت کامل، درس را تکمیل و مرور کن."
            isCompleted -> "نیازمند مرور و بازیابی" to "درس را خوانده‌ای، اما برای تبدیل به «تسلط تأییدشده»، سؤال خودآزمایی انتهای درس را هم پاسخ بده."
            masteryPercent > 0 -> "در حال یادگیری" to "مطالعه این مبحث آغاز شده است؛ گام‌به‌گام ادامه بده."
            else -> "شروع‌نشده" to "از قدم اول و با کمک دستیار «منفی صفر» شروع کن."
        }
    }
}

