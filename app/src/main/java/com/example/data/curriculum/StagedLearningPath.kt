package com.example.data.curriculum

import android.content.Context
import androidx.compose.ui.graphics.Color
import com.example.ui.theme.SubjectArabicColor
import com.example.ui.theme.SubjectFarhangianColor
import com.example.ui.theme.SubjectFonoonColor
import com.example.ui.theme.SubjectMathColor
import com.example.ui.theme.SubjectPhilosophyColor
import org.json.JSONObject

/**
 * ساختار داده‌ای ایستگاه یادگیری (پله مطالعاتی)
 */
data class LearningStation(
    val id: String,
    val stationNumber: Int,
    val title: String,
    val subtitle: String,
    val estimatedHours: Int,
    val targetTestCount: Int,
    val keyConcepts: List<String>,
    val goldenTechnique: String,
    val commonMistakeTrap: String,
    val targetExamYield: String,
    val targetLessonId: String? = null
)

/**
 * ساختار داده‌ای فاز/مرحله مطالعاتی
 */
data class CurriculumPhase(
    val phaseNumber: Int,
    val title: String,
    val milestoneGoal: String,
    val badge: String,
    val estimatedTotalHours: Int,
    val stations: List<LearningStation>
)

/**
 * مسیر مرحله‌بندی شده درس برای کنکور ۱۴۰۶
 */
data class StagedSubjectPath(
    val subjectId: String,
    val subjectName: String,
    val totalPhasesCount: Int,
    val totalStationsCount: Int,
    val konkoorWeightInfo: String,
    val themeColor: Color,
    val badgeTag: String,
    val iconDescription: String,
    val summaryDescription: String,
    val phases: List<CurriculumPhase>
)

/**
 * مخزن مسیرهای یادگیری مرحله‌بندی شده برای ۵ درس کلیدی کنکور ۱۴۰۶
 * (۱. ادبیات و علوم و فنون ادبی • ۲. عربی • ۳. معارف و دین و زندگی • ۴. روان‌شناسی • ۵. جامعه‌شناسی)
 * قابلیت بارگذاری مستقیم از فایل JSON جامع در assets/konkoor_1406_staged_curriculum.json
 * به همراه داده‌های کامل درون‌برنامه‌ای برای دسترسی آنی و بدون خطا.
 */
object StagedLearningPathRepository {

    const val JSON_ASSET_FILE_NAME = "konkoor_1406_staged_curriculum.json"

    @Volatile
    private var cachedJsonPaths: List<StagedSubjectPath>? = null

    /**
     * بارگذاری و پارس کردن ساختار داده‌ها از فایل JSON جامع در پوشه assets
     */
    fun loadFromJsonAssets(context: Context): List<StagedSubjectPath> {
        cachedJsonPaths?.let { return it }
        return try {
            val jsonText = context.assets.open(JSON_ASSET_FILE_NAME).bufferedReader(Charsets.UTF_8).use { it.readText() }
            val parsed = parseJsonCurriculum(jsonText)
            if (parsed.isNotEmpty()) {
                cachedJsonPaths = parsed
                parsed
            } else {
                getAllStagedPaths()
            }
        } catch (_: Exception) {
            getAllStagedPaths()
        }
    }

    /**
     * مفسر استاندارد JSON برای تبدیل فایل konkoor_1406_staged_curriculum.json به مدل‌های کاتلین
     */
    fun parseJsonCurriculum(jsonString: String): List<StagedSubjectPath> {
        return try {
            val root = JSONObject(jsonString)
            val subjectsArray = root.getJSONArray("subjects")
            val result = mutableListOf<StagedSubjectPath>()

            for (i in 0 until subjectsArray.length()) {
                val subObj = subjectsArray.getJSONObject(i)
                val subjectId = subObj.getString("subjectId")
                val hexColor = subObj.optString("themeColorHex", "#2563EB")
                val parsedColor = parseHexColor(hexColor, subjectId)

                val phasesArray = subObj.getJSONArray("phases")
                val phasesList = mutableListOf<CurriculumPhase>()

                for (p in 0 until phasesArray.length()) {
                    val phaseObj = phasesArray.getJSONObject(p)
                    val stationsArray = phaseObj.getJSONArray("stations")
                    val stationsList = mutableListOf<LearningStation>()

                    for (s in 0 until stationsArray.length()) {
                        val stObj = stationsArray.getJSONObject(s)
                        val conceptsJson = stObj.getJSONArray("keyConcepts")
                        val concepts = mutableListOf<String>()
                        for (c in 0 until conceptsJson.length()) {
                            concepts.add(conceptsJson.getString(c))
                        }

                        stationsList.add(
                            LearningStation(
                                id = stObj.getString("id"),
                                stationNumber = stObj.getInt("stationNumber"),
                                title = stObj.getString("title"),
                                subtitle = stObj.getString("subtitle"),
                                estimatedHours = stObj.getInt("estimatedHours"),
                                targetTestCount = stObj.getInt("targetTestCount"),
                                keyConcepts = concepts,
                                goldenTechnique = stObj.getString("goldenTechnique"),
                                commonMistakeTrap = stObj.getString("commonMistakeTrap"),
                                targetExamYield = stObj.getString("targetExamYield"),
                                targetLessonId = stObj.optString("targetLessonId").takeIf { it.isNotBlank() }
                            )
                        )
                    }

                    phasesList.add(
                        CurriculumPhase(
                            phaseNumber = phaseObj.getInt("phaseNumber"),
                            title = phaseObj.getString("title"),
                            milestoneGoal = phaseObj.getString("milestoneGoal"),
                            badge = phaseObj.getString("badge"),
                            estimatedTotalHours = phaseObj.getInt("estimatedTotalHours"),
                            stations = stationsList
                        )
                    )
                }

                result.add(
                    StagedSubjectPath(
                        subjectId = subjectId,
                        subjectName = subObj.getString("subjectName"),
                        totalPhasesCount = subObj.getInt("totalPhasesCount"),
                        totalStationsCount = subObj.getInt("totalStationsCount"),
                        konkoorWeightInfo = subObj.getString("konkoorWeightInfo"),
                        themeColor = parsedColor,
                        badgeTag = subObj.getString("badgeTag"),
                        iconDescription = subObj.optString("iconDescription", ""),
                        summaryDescription = subObj.getString("summaryDescription"),
                        phases = phasesList
                    )
                )
            }
            result
        } catch (_: Exception) {
            emptyList()
        }
    }

    private fun parseHexColor(hex: String, subjectId: String): Color {
        return try {
            val clean = hex.removePrefix("#")
            val colorLong = clean.toLong(16) or 0xFF000000L
            Color(colorLong)
        } catch (_: Exception) {
            when (subjectId) {
                "fonoon" -> SubjectFonoonColor
                "arabic" -> SubjectArabicColor
                "dini" -> SubjectFarhangianColor
                "psychology" -> SubjectPhilosophyColor
                "sociology" -> SubjectMathColor
                else -> SubjectMathColor
            }
        }
    }

    // ۱. ادبیات و علوم و فنون ادبی (فارسی و فنون کنکور ۱۴۰۶)
    val literatureAndFonoonPath = StagedSubjectPath(
        subjectId = "fonoon",
        subjectName = "ادبیات و علوم و فنون ادبی کنکور ۱۴۰۶",
        totalPhasesCount = 4,
        totalStationsCount = 16,
        konkoorWeightInfo = "ضریب ۸ کنکور سراسری انسانی ۱۴۰۶ • زبان و ادبیات فارسی تخصصی (فارسی + علوم و فنون ادبی)",
        themeColor = SubjectFonoonColor,
        badgeTag = "رتبه‌سازترین درس کنکور ۱۴۰۶",
        iconDescription = "قلم و دفتر شعر عروضی",
        summaryDescription = "مسیر ۴ مرحله‌ای صفر تا صد از تقطیع سماعی و قافیه تا آرایه‌ها، سبک‌شناسی و مهندسی تست قرابت کنکور ۱۴۰۶",
        phases = listOf(
            CurriculumPhase(
                phaseNumber = 1,
                title = "مرحله ۱: زیرصفر عروض، قافیه و تقطیع سماعی",
                milestoneGoal = "تشخیص فوری هجاها، قافیه و وزن‌یابی شعر بدون خط کشیدن روی کاغذ در کمتر از ۵ ثانیه",
                badge = "پایه‌گذاری عروض و قافیه",
                estimatedTotalHours = 12,
                stations = listOf(
                    LearningStation(
                        id = "lit_p1_s1",
                        stationNumber = 1,
                        title = "الفبای هجا و علامت‌های عروضی (کوتاه، بلند، کشیده)",
                        subtitle = "تبدیل صوت شعر به علامت‌های خط و هلال (- و U)",
                        estimatedHours = 2,
                        targetTestCount = 25,
                        keyConcepts = listOf(
                            "هجای کوتاه (U): صامت + مصوت کوتاه",
                            "هجای بلند (-): صامت + مصوت بلند یا دو صامت در پایان",
                            "هجای کشیده (- U): معادل یک هجای بلند و یک هجای کوتاه",
                            "قاعده خط عروضی: نوشتن دقیقاً به صورت ملفوظ (آن‌چه شنیده می‌شود)"
                        ),
                        goldenTechnique = "هر کلمه‌ای که حرف ساکن دارد یا مصوت بلند (آ، او، ای) دارد، هجای بلند است؛ هجای کشیده همیشه در تقطیع دو هجا (- U) حساب می‌شود.",
                        commonMistakeTrap = "نوشتن حروف ناخوانا مثل «خویش»، «خواهر» و «نون ساکن بعد از مصوت بلند (جان، نان)» که در عروض حذف یا کم می‌شوند.",
                        targetExamYield = "پیشنیاز قطعی ۶ تست عروض کنکور سراسری",
                        targetLessonId = "arooz_10_ch1_syllables_and_script"
                    ),
                    LearningStation(
                        id = "lit_p1_s2",
                        stationNumber = 2,
                        title = "تقطیع سماعی و ریتم‌شناسی بدون خط‌کشی",
                        subtitle = "شنیدن موسیقی شعر و زمزمه اوزان اصلی با انگشت‌شماری",
                        estimatedHours = 3,
                        targetTestCount = 35,
                        keyConcepts = listOf("ضرب‌آهنگ شعر و تکیه هجاها", "تفکیک ارکان ۳تایی و ۴تایی", "گوش عروضی و ریتم‌خوانی", "شمارش ضرب در کف دست"),
                        goldenTechnique = "خواندن شعر با ریتم «تَن تَن تَنَن تَن» — در ۲ ثانیه ریتم پایه‌ای و مرز ارکان مشخص می‌شود.",
                        commonMistakeTrap = "شتاب‌زدگی در خواندن شعر و جاانداختن تشدیدها و همزه‌های میانی.",
                        targetExamYield = "صرفه‌جویی ۱۰ دقیقه‌ای در زمان دفترچه ۱ کنکور",
                        targetLessonId = "aroozm1406-01-rhythm-clap"
                    ),
                    LearningStation(
                        id = "lit_p1_s3",
                        stationNumber = 3,
                        title = "اوزان همسان، متناوب و دولختی پربسامد",
                        subtitle = "فاعلاتن، مفاعیلن، مستفعلن، فعولن و اوزان خیزابی (دوری)",
                        estimatedHours = 4,
                        targetTestCount = 45,
                        keyConcepts = listOf("بحر رمل (فاعلاتن - U - -)", "بحر هزج (مفاعیلن U - - -)", "بحر رجز (مستفعلن - - U -)", "بحر متقارب (فعولن U - -)", "اوزان دوری (مشتق از مفتعلن فاعلن یا مفعول فاعلاتن)"),
                        goldenTechnique = "رمزگشایی با مصراع‌های الگو: «بشنو از نی» = فاعلاتن | «دل می‌رود» = مفعول فاعلاتن (دوری با مکث میانی).",
                        commonMistakeTrap = "اشتباه گرفتن فاعلاتن (- U - -) با فعلاتن (U U - -) در ابتدای مصراع.",
                        targetExamYield = "حل مستقیم ۴ تست قطعی عروض در کنکور ۱۴۰۶",
                        targetLessonId = "arooz_11_lesson6_hamsan_meters"
                    ),
                    LearningStation(
                        id = "lit_p1_s4",
                        stationNumber = 4,
                        title = "قواعد قافیه، ردیف و اختیارات شاعری (زبانی و وزنی)",
                        subtitle = "قاعده ۱ و ۲ قافیه، حروف الحاقی، ابدال، قلب و تغییر کمیت مصوت‌ها",
                        estimatedHours = 3,
                        targetTestCount = 40,
                        keyConcepts = listOf("قاعده ۱ قافیه: مصوت بلند (آ، او، ای) در پایان یا قبل از یک صامت", "قاعده ۲ قافیه: مصوت کوتاه + یک یا دو صامت", "اختیارات زبانی: حذف همزه، اشباع کسره و ضمه، تسکین", "اختیارات وزنی: ابدال (U U به -)، قلب، فاعلاتن به مفعولن"),
                        goldenTechnique = "اگر هجای اول مصراع ۱ بلند بود و مصراع ۲ کوتاه شد (یا برعکس)، ۱۰۰٪ اختیار شاعری رخ داده است.",
                        commonMistakeTrap = "لحاظ کردن ردیف یا پسوندهای جمع به عنوان حروف اصلی قافیه بدون حذف زواید.",
                        targetExamYield = "۲ سوال فوق‌چالشی و ترازساز کنکور سنجش",
                        targetLessonId = "fon3-05-licenses-verbal"
                    )
                )
            ),
            CurriculumPhase(
                phaseNumber = 2,
                title = "مرحله ۲: آرایه‌های ادبی پرتکرار و رتبه‌ساز",
                milestoneGoal = "تسلط بر آرایه‌های بیان (تشبیه، استعاره، مجاز، کنایه) و بدیع لفظی و معنوی در ابیات ناآشنا",
                badge = "فتح آرایه‌ها",
                estimatedTotalHours = 14,
                stations = listOf(
                    LearningStation(
                        id = "lit_p2_s1",
                        stationNumber = 5,
                        title = "تشبیه، ارکان آن و اضافه‌های تشبیهی",
                        subtitle = "تشبیه گسترده، بلیغ اسنادی، اضافه تشبیهی و تشخیص ارکان محذوف",
                        estimatedHours = 3,
                        targetTestCount = 30,
                        keyConcepts = listOf("مشبه و مشبه‌به (طرفین تشبیه)", "وجه شبه و ادات تشبیه", "تشبیه بلیغ اضافی و اسنادی", "تفکیک اضافه تشبیهی از اضافه استعاری"),
                        goldenTechnique = "فرمول طلایی: بین مضاف و مضاف‌الیه کلمه «مانند» بگذار؛ اگر معنی داد = اضافه تشبیهی (مثلاً لبِ لعل = لب مانند لعل است).",
                        commonMistakeTrap = "اشتباه گرفتن اضافه تشبیهی با اضافه استعاری (مثلاً «دستِ روزگار» اضافه استعاری است چون روزگار مانند دست نیست!).",
                        targetExamYield = "۱ الی ۲ سوال در ترکیب آرایه‌ها",
                        targetLessonId = "fonoon_11_ch2_tashbih_types"
                    ),
                    LearningStation(
                        id = "lit_p2_s2",
                        stationNumber = 6,
                        title = "استعاره مصرحه، مکنیه، تشخیص و مجاز",
                        subtitle = "کشف لوازم مشبه‌به، جان‌بخشی به اشیاء و علاقه‌های مجازی",
                        estimatedHours = 3,
                        targetTestCount = 35,
                        keyConcepts = listOf("استعاره مصرحه (حذف مشبه و ذکر مشبه‌به مثل: بت، سرو، ماه، نرگس)", "استعاره مکنیه و تشخیص (انسان‌انگاری)", "مجاز به علاقه کلیه، محلیه، آلیه و لازمیه", "قرینه صارفه در مجاز و استعاره"),
                        goldenTechnique = "هر جا شاعر غیرانسان را صدا زد (ای باد، ای دل، ای شب) = بلافاصله تشخیص + منادا + استعاره مکنیه!",
                        commonMistakeTrap = "تصور این‌که هر استعاره مکنیه‌ای تشخیص است (مثلاً «تخم وفا کاشتن» استعاره مکنیه است اما تشخیص نیست).",
                        targetExamYield = "حداقل ۲ سوال اختصاصی در تست‌های ترکیبی فنون",
                        targetLessonId = "fonoon_11_ch3_estearah_majaz_kenayeh"
                    ),
                    LearningStation(
                        id = "lit_p2_s3",
                        stationNumber = 7,
                        title = "ایهام، ایهام تناسب و کنایه",
                        subtitle = "شناخت واژه‌های دوپهلوی مشهور شعر حافظ و سعدی",
                        estimatedHours = 3,
                        targetTestCount = 40,
                        keyConcepts = listOf("ایهام: دو معنای نزدیک و دور که هر دو با سیاق بیت می‌خوانند", "ایهام تناسب: یک معنا مقصود است و معنای دوم با کلمه دیگری در بیت مراعات نظیر می‌سازد", "کنایه فعلی و اسمی", "بانک واژگان پرتکرار ایهام (مهر، مدام، چنگ، پرده، خط، خال، شام، گور)"),
                        goldenTechnique = "اگر کلمه‌ای دو معنی داشت ولی فقط یکی در ترجمه مصراع جا می‌گرفت و معنای دوم با کلمه‌ای دیگر تناسب داشت = ۱۰۰٪ ایهام تناسب!",
                        commonMistakeTrap = "علامت زدن ایهام به جای ایهام تناسب در تست‌های سخت سنجش.",
                        targetExamYield = "۱ الی ۲ سوال با ضریب خطای بالای داوطلبان",
                        targetLessonId = "fon12-03-iham-tanasob"
                    ),
                    LearningStation(
                        id = "lit_p2_s4",
                        stationNumber = 8,
                        title = "اسلوب معادله، حسن تعلیل، پارادوکس و حس‌آمیزی",
                        subtitle = "شگردهای تفکیک سبک هندی، علت‌های شاعرانه و آمیزش حواس پنج‌گانه",
                        estimatedHours = 3,
                        targetTestCount = 35,
                        keyConcepts = listOf("اسلوب معادله: دو مصراع مستقل از نظر دستوری با رابطه تمثیلی (=)", "حسن تعلیل: آوردن دلیل ادعایی و غیرواقعی شاعرانه برای یک پدیده", "پارادوکس (متناقض‌نما): جمع دو صفت متضاد در یک پدیده", "حس‌آمیزی: ترکیب دو حس مختلف (مثل: سخنِ شیرین، قیافه تلخ)"),
                        goldenTechnique = "اگر توانستی بین مصراع اول و دوم کلمه «همان‌طور که» بگذاری و جای دو مصراع را عوض کنی = ۱۰۰٪ اسلوب معادله!",
                        commonMistakeTrap = "اشتباه گرفتن تمثیل وابسته (که حرف ربط که یا چو دارد) با اسلوب معادله.",
                        targetExamYield = "۲ تست ثابت در کنکور سراسری انسانی",
                        targetLessonId = "fon12-02-oslub-moadele-hosn-talil"
                    ),
                    LearningStation(
                        id = "lit_p2_s5",
                        stationNumber = 9,
                        title = "آرایه‌های لفظی: جناس، سجع، ترصیع، موازنه و لف و نشر",
                        subtitle = "موسیقی درونی و کناری کلام در نثر و نظم",
                        estimatedHours = 2,
                        targetTestCount = 30,
                        keyConcepts = listOf("جناس تام و جناس ناقص (حرکتی، اختلافی، افزایشی)", "سجع متوازی، مطرف و متوازن", "ترصیع (هم‌وزن و هم‌قافیه بودن تمام واژه‌های قرینه) در برابر موازنه", "لف و نشر مرتب و مشوش"),
                        goldenTechnique = "جناس تام: املا و تلفظ کاملاً یکسان + معنی کاملاً متفاوت (مثلاً روان = روح / روان = جاری).",
                        commonMistakeTrap = "شمارش کلمات تکراری با معنی یکسان به عنوان جناس (این تکرار است نه جناس!).",
                        targetExamYield = "۱ سوال لفظی قطعی در دفترچه ۱",
                        targetLessonId = "fonoon_10_ch2_badi_tarsee_movazeneh"
                    )
                )
            ),
            CurriculumPhase(
                phaseNumber = 3,
                title = "مرحله ۳: سبک‌شناسی، تاریخ ادبیات و کدهای رمزی",
                milestoneGoal = "تسلط بر ویژگی‌های زبانی، فکری و ادبی ادوار شعر و نثر و جدول رمزگذاری آثار",
                badge = "معماری سبک‌شناسی",
                estimatedTotalHours = 10,
                stations = listOf(
                    LearningStation(
                        id = "lit_p3_s1",
                        stationNumber = 10,
                        title = "سبک خراسانی و بینابین در سه سطح زبانی، ادبی و فکری",
                        subtitle = "شعر و نثر قرون ۴ تا ۶ هجری، سادگی زبان، واقع‌گرایی و روحیه حماسی",
                        estimatedHours = 2,
                        targetTestCount = 25,
                        keyConcepts = listOf("سطح زبانی: کهنگی واژگان، دو حرف اضافه برای یک متمم (به... اندر)، کاربرد «مر»", "سطح فکری: واقع‌گرایی، ستایش خرد، روحیه حماسی و نشاط", "سطح ادبی: تشبیه‌های حسی به حسی و ملموس، قالب قصیده و مثنوی"),
                        goldenTechnique = "دیدن لغاتی مثل «ایدون، همی، مر، ابا، اندر» بلافاصله گواه سبک خراسانی است.",
                        commonMistakeTrap = "اشتباه گرفتن شعر ناصرخسرو و سنایی با سبک عراقی محض.",
                        targetExamYield = "۱ الی ۲ تست قطعی سبک‌شناسی سنجش",
                        targetLessonId = "fonoon_10_ch1_history_khorasani"
                    ),
                    LearningStation(
                        id = "lit_p3_s2",
                        stationNumber = 11,
                        title = "سبک عراقی و صوفیانه (قرن ۷ تا ۹ هجری)",
                        subtitle = "ورود عرفان، غزل‌سرایی، استعاره‌های انتزاعی و زبان عاشقانه سعدی، مولوی و حافظ",
                        estimatedHours = 2,
                        targetTestCount = 30,
                        keyConcepts = listOf("درون‌گرایی، عرفان و ترجیح عشق بر عقل استدلالی", "رواج غزل عاشقانه و عارفانه به جای قصیده", "ایهام، استعاره، کنایه و نمادهای صوفیانه (پیر مغان، می، رند)", "رواج نثر فنی و مصنوع (مرزبان‌نامه و تاریخ وصاف)"),
                        goldenTechnique = "محور عشق، معشوق آسمانی، فراق، می و مستی عرفانی و واژگان عربی زیاد = سبک عراقی.",
                        commonMistakeTrap = "غفلت از تفاوت غزل عاشقانه سعدی با غزل رندانه و دوپهلوی حافظ.",
                        targetExamYield = "۲ تست پرتکرار کنکور سراسری",
                        targetLessonId = "fonoon_11_ch1_history_eraqi"
                    ),
                    LearningStation(
                        id = "lit_p3_s3",
                        stationNumber = 12,
                        title = "سبک هندی (اصفهانی)، بازگشت ادبی و دوره مشروطه و معاصر",
                        subtitle = "نازک‌خیالی، تک‌بیت‌های صائب، بازگشت قاجار و شعر وطنی مشروطه",
                        estimatedHours = 3,
                        targetTestCount = 30,
                        keyConcepts = listOf("سبک هندی: اسلوب معادله، حس‌آمیزی، تشخیص و واژگان کوچه و بازار", "دوره بازگشت: تقلید از خراسانی و عراقی (هاتف اصفهانی، قاآنی، صبای کاشانی)", "دوره مشروطه: ورود مفاهیم وطن، آزادی، قانون و زبان مردم", "شعر نیمایی و سپید معاصر"),
                        goldenTechnique = "شعر دارای دو مصراع معادل با مضمون اخلاقی و تمثيل روزمره = سبک هندی (صائب، کلیم، بیدل).",
                        commonMistakeTrap = "اشتباه گرفتن شعر دوره بازگشت با شعر قرن ۵ و ۷ به دلیل شباهت ظاهری واژگان.",
                        targetExamYield = "۲ تست تثبیت‌شده هندی، بازگشت و معاصر",
                        targetLessonId = "fonoon_12_ch1_history_hindi_bazgasht"
                    ),
                    LearningStation(
                        id = "lit_p3_s4",
                        stationNumber = 13,
                        title = "جدول کدهای طلایی تاریخ ادبیات و کتاب‌شناسی کنکور ۱۴۰۶",
                        subtitle = "کدگذاری آثار منظوم و منثور قرون ۴ تا ۱۴ برای ماندگاری در حافظه",
                        estimatedHours = 3,
                        targetTestCount = 35,
                        keyConcepts = listOf("آثار قرن ۴ و ۵: تاریخ بلعمی، حدودالعالم، تاریخ بیهقی، قابوس‌نامه، سیاست‌نامه", "آثار قرن ۶: کلیله و دمنه، چهارمقاله، مقامات حمیدی، اسرارالتوحید، تذکرةالاولیا", "آثار قرن ۷ و ۸: گلستان، مرزبان‌نامه، فیه مافیه، رساله دلگشا", "آثار معاصر و ادبیات پایداری"),
                        goldenTechnique = "کد رمزی: «بیهقی ابوالفضل» ⟵ تاریخ بیهقی | «کلیله نصرالله منشی» ⟵ آغاز نثر فنی در قرن ۶.",
                        commonMistakeTrap = "جابه‌جا زدن قرن تذکرةالاولیای عطار با نفحات‌الانس جامی (قرن ۹).",
                        targetExamYield = "۳ تست حفظی سرعتی با پاسخ‌گویی در زیر ۱۰ ثانیه",
                        targetLessonId = "fonoon_04_mnemonics_final20"
                    )
                )
            ),
            CurriculumPhase(
                phaseNumber = 4,
                title = "مرحله ۴: مهندسی تست قرابت معنایی و جمع‌بندی کنکور ۱۴۰۶",
                milestoneGoal = "پاسخ به سوالات مفهومی قرابت و حل آزمون جامع فنون در زمان استاندارد",
                badge = "تسلط نهایی و صدزنی",
                estimatedTotalHours = 12,
                stations = listOf(
                    LearningStation(
                        id = "lit_p4_s1",
                        stationNumber = 14,
                        title = "شاه‌مفاهیم پرتکرار قرابت معنایی در ادبیات فارسی",
                        subtitle = "عشق حقیقی، قناعت، بی‌اعتباری دنیا، تواضع، ریاستیزی، توکل و اغتنام فرصت",
                        estimatedHours = 3,
                        targetTestCount = 45,
                        keyConcepts = listOf("کلیدواژه‌های بی‌وفایی دنیا (سراب، باد، خواب، کاروانسرا، عجوز)", "ریاستیزی رندانه (زاهد، خرقه، سجاده، شیخ)", "استغنای طبع و قناعت (گنج قناعت، هما و کرکس)", "تسلیم و رضا در برابر قضای الهی"),
                        goldenTechnique = "پیام بیت صورت سوال را در ۳ کلمه خلاصه کن؛ گزینه‌ای که دقیقاً همان پیام را دارد علامت بزن.",
                        commonMistakeTrap = "فریب خوردن از تشابه کلمات ظاهری (مثلاً هر دو بیت کلمه «آینه» دارند اما مفهومشان کاملاً متفاوت است).",
                        targetExamYield = "۴ الی ۶ تست سرنوشت‌ساز در دفترچه کنکور و نهایی",
                        targetLessonId = "fonoon_07_complete_literary_history_10_11_12"
                    ),
                    LearningStation(
                        id = "lit_p4_s2",
                        stationNumber = 15,
                        title = "تکنیک رد گزینه فوق‌سریع در تست‌های دام‌دار قرابت",
                        subtitle = "فیلتر کردن گزینه‌های نامربوط با پیدا کردن جهت بار عاطفی و لحن بیت",
                        estimatedHours = 3,
                        targetTestCount = 40,
                        keyConcepts = listOf("بار معنایی مثبت (امید، بخشش، عشق) در برابر منفی (طمع، حسد، غفلت)", "تقابل عقل مصلحت‌اندیش و عشق بی‌پروا", "تفکیک شکایت از فلک با توکل بر خدا"),
                        goldenTechnique = "با تشخیص مثبت یا منفی بودن پیام بیت، ۲ گزینه در ۵ ثانیه اول حذف می‌شوند.",
                        commonMistakeTrap = "برداشت تحت‌اللفظی از کلمات نمادین و مجازی در غزلیات.",
                        targetExamYield = "افزایش سرعت تست‌زنی به زیر ۴۰ ثانیه در هر سوال"
                    ),
                    LearningStation(
                        id = "lit_p4_s3",
                        stationNumber = 16,
                        title = "شبیه‌ساز آزمون جامع ۳۰ سوالی علوم و فنون ادبی کنکور ۱۴۰۶",
                        subtitle = "حل ۳۰ تست طبق بودجه‌بندی رسمی سازمان سنجش در ۳۰ دقیقه",
                        estimatedHours = 6,
                        targetTestCount = 60,
                        keyConcepts = listOf("مدیریت زمان ۶۰ ثانیه به ازای هر تست", "ترتیب طلایی پاسخ‌دهی: تاریخ ادبیات ➔ سبک‌شناسی ➔ آرایه ➔ قرابت ➔ عروض و قافیه", "تکنیک ضربدر و منفی برای مدیریت تست‌های زمان‌بر"),
                        goldenTechnique = "شروع با تاریخ ادبیات و سبک‌شناسی در ۵ دقیقه اول، ۱۰ دقیقه ذخیره زمانی برای عروض و آرایه ایجاد می‌کند.",
                        commonMistakeTrap = "گیر کردن روی یک بیت دشوار عروضی در ابتدای درس و از دست دادن تست‌های ساده انتهای دفترچه!",
                        targetExamYield = "تضمین درصد بالای ۸۰٪ در علوم و فنون کنکور انسانی"
                    )
                )
            )
        )
    )

    // ۲. عربی کنکور ۱۴۰۶ (تخصصی انسانی و نهایی)
    val arabicPath = StagedSubjectPath(
        subjectId = "arabic",
        subjectName = "عربی تخصصی و عمومی کنکور ۱۴۰۶",
        totalPhasesCount = 4,
        totalStationsCount = 18,
        konkoorWeightInfo = "ضریب ۵ کنکور سراسری انسانی ۱۴۰۶ • عربی تخصصی + امتحان نهایی",
        themeColor = SubjectArabicColor,
        badgeTag = "سریع‌ترین درصدآور کنکور",
        iconDescription = "کتاب قواعد و لغت عربی",
        summaryDescription = "مسیر گام‌به‌گام از جدول ۱۴ صیغه و ضمایر تا ثلاثی مزید، اعراب و تکنیک ۵ ثانیه‌ای رد گزینه ترجمه",
        phases = listOf(
            CurriculumPhase(
                phaseNumber = 1,
                title = "مرحله ۱: زیربنای صرف — ۱۴ صیغه، ضمایر و فعل‌ها",
                milestoneGoal = "شناخت آنی ریشه، زمان، صیغه و ضمایر تمام افعال عربی",
                badge = "ستون فقرات صرف",
                estimatedTotalHours = 12,
                stations = listOf(
                    LearningStation(
                        id = "arab_p1_s1",
                        stationNumber = 1,
                        title = "جدول جادویی ۱۴ صیغه ماضی و مضارع و ساختار شناسه",
                        subtitle = "تفکیک ۶ غایب، ۶ مخاطب و ۲ متکلم با الگوهای صوتی",
                        estimatedHours = 3,
                        targetTestCount = 30,
                        keyConcepts = listOf("ماضی (فَعَلَ تا فَعَلْنَ): شناسه‌ها در انتهای فعل", "مضارع (یَفْعَلُ تا نَفْعَلُ): حروف أَتَیْنَ در ابتدای فعل", "نون عوض از تنوین در افعال خمسه (یَفْعَلونَ، تَفْعَلینَ...)", "نون نسوه در صیغه‌های ۶ و ۱۲ (جمع مؤنث)"),
                        goldenTechnique = "حروف مضارعه «أتین» در اول فعل می‌آیند؛ اگر اول فعل یـ/تـ/أ/نـ نداشت ۱۰۰٪ ماضی یا امر است.",
                        commonMistakeTrap = "اشتباه گرفتن «تَفعَلُ» (مفرد مذکر مخاطب: تو می‌کنی) با «تَفعَلُ» (مفرد مؤنث غایب: او می‌کند).",
                        targetExamYield = "پایه‌گذار پاسخ به تمام تست‌های ترجمه و قواعد",
                        targetLessonId = "arabic_step03_complete_14_sigha_tables"
                    ),
                    LearningStation(
                        id = "arab_p1_s2",
                        stationNumber = 2,
                        title = "ضمایر متصل و منفصل و اتصالات خطرناک",
                        subtitle = "ضمایر رفعی، نصبی و جری و چسبیدن ضمیر به اسم، فعل یا حرف",
                        estimatedHours = 2,
                        targetTestCount = 25,
                        keyConcepts = listOf("ضمایر منفصل فاعلی (هُوَ، هِیَ...) و مفعولی (إیّاهُ، إیّاکَ...)", "ضمیر متصل به فعل = مفعول‌به (مگر نون وقایه)", "ضمیر متصل به اسم = مضاف‌الیه", "ضمیر متصل به حرف جر = مجرور به حرف جر"),
                        goldenTechnique = "فرمول طلایی: اسم + ضمیر = مضاف‌الیه | فعل + ضمیر (ـه، ها، کم، نا) = مفعول‌به!",
                        commonMistakeTrap = "فاعل گرفتن ضمیر «ـه» در کلماتی مانند «خَلَقَهُ اللّهُ» (خدا او را آفرید؛ الله فاعل است و ـه مفعول).",
                        targetExamYield = "حل کلیدی حداقل ۳ تست ترجمه و تحلیل صرفی",
                        targetLessonId = "found1406-ar-03-pronouns"
                    ),
                    LearningStation(
                        id = "arab_p1_s3",
                        stationNumber = 3,
                        title = "فعل امر، نهی، نفی و ادوات جازمه و ناصبه",
                        subtitle = "ساخت امر با همزه، نهی با «لا» و جزم مضارع در اسلوب شرط",
                        estimatedHours = 3,
                        targetTestCount = 35,
                        keyConcepts = listOf("امر مخاطب و امر غایب (با لِـ امر: لِیَکْتُبْ = باید بنویسد)", "تفاوت لا نهی (مجزوم‌کننده: لا تَکْذِبْ = دروغ نگو) و لا نفی (لا یَکْذِبُ = دروغ نمی‌گوید)", "ادوات ناصبه: أَنْ، لَنْ، کَیْ، حَتَّی، لِـ تعلیل", "ادوات جازمه: لَمْ، لَمّا، لام امر، لا نهی و ادوات شرط"),
                        goldenTechnique = "«لَمْ + مضارع مجزوم» همیشه به صورت ماضی منفی یا نقلی منفی ترجمه می‌شود (لَمْ یَذْهَبْ = نرفت).",
                        commonMistakeTrap = "ترجمه «لَنْ + مضارع منصوب» به صورت زمان حال به جای آینده منفی (هرگز نخواهد...).",
                        targetExamYield = "۲ تست پرتکرار ترجمه و اعراب افعال"
                    ),
                    LearningStation(
                        id = "arab_p1_s4",
                        stationNumber = 4,
                        title = "افعال معتل (مثال، اجوف، ناقص) و تغییرات اعلال",
                        subtitle = "قواعد اسکان، قلب و حذف حروف عله (و، ا، ی) در عربی تخصصی انسانی",
                        estimatedHours = 4,
                        targetTestCount = 35,
                        keyConcepts = listOf("معتل مثال (حرف عله در فاءالفعل: وَعَدَ ⟵ یَعِدُ)", "معتل اجوف (حرف عله در عین‌الفعل: قالَ ⟵ یَقولُ ⟵ قُلْ)", "معتل ناقص (حرف عله در لام‌الفعل: دَعا، رَمَی، خَشِیَ)", "انواع اعلال: به اسکان، به قلب و به حذف"),
                        goldenTechnique = "در جزم و امر فعل اجوف، حرف عله وسط به علت التقای ساکنین حذف می‌شود (قُلْ، بِعْ، خَفْ) ⟵ اعلال به حذف!",
                        commonMistakeTrap = "فراموش کردن حذف واو در مضارع فعل مثال واوی (وَجَدَ ⟵ یَجِدُ: اعلال به حذف).",
                        targetExamYield = "۲ تست اختصاصی تحلیل صرفی و اعلال در کنکور انسانی"
                    )
                )
            ),
            CurriculumPhase(
                phaseNumber = 2,
                title = "مرحله ۲: ثلاثی مزید، اوزان و مشتقات",
                milestoneGoal = "تسلط بر ۸ باب ثلاثی مزید و شناسایی بدون خطای اسم‌های مشتق و جامد",
                badge = "اقیانوس مشتقات",
                estimatedTotalHours = 10,
                stations = listOf(
                    LearningStation(
                        id = "arab_p2_s1",
                        stationNumber = 5,
                        title = "۸ باب طلایی ثلاثی مزید و معانی آن‌ها",
                        subtitle = "افعال، تفعیل، مفاعله، تفعل، تفاعل، افتعال، انفعال، استفعال",
                        estimatedHours = 3,
                        targetTestCount = 35,
                        keyConcepts = listOf("إفعال (أَفْعَلَ - یُفْعِلُ - إفعال)", "تفعیل (فَعَّلَ - یُفَعِّلُ - تَفعیل)", "مفاعله (فاعَلَ - یُفاعِلُ - مُفاعَلَة / فِعال)", "تفعل، تفاعل، افتعال، انفعال و استفعال"),
                        goldenTechnique = "مصدر باب تفعّل و تفاعل با «تَـ» و ضمه ماقبل آخر ساخته می‌شود (تَعَلُّم، تَعاوُن)؛ نه با کسره!",
                        commonMistakeTrap = "اشتباه گرفتن باب «افتعال» با «انفعال» در کلماتی که فاءالفعل آن‌ها نون است (مثل: اِنْتَظَرَ از ریشه ن ظ ر = باب افتعال).",
                        targetExamYield = "۱ الی ۲ تست اختصاصی صرف و مصدرها",
                        targetLessonId = "arabic_step06_all_8_abwab_master_table"
                    ),
                    LearningStation(
                        id = "arab_p2_s2",
                        stationNumber = 6,
                        title = "مشتقات: اسم فاعل و اسم مفعول (مجرد و مزید)",
                        subtitle = "وزن‌های فاعِل و مَفعول و قاعده «مُـ...ِـ» و «مُـ...َـ»",
                        estimatedHours = 2,
                        targetTestCount = 30,
                        keyConcepts = listOf("اسم فاعل ثلاثی مجرد بر وزن فاعِل (کاتِب، ناصِر)", "اسم مفعول ثلاثی مجرد بر وزن مَفْعول (مَکتوب، مَنصور)", "اسم فاعل مزید: میم مضموم + کسره ماقبل آخر (مُعَلِّم، مُسْتَغْفِر)", "اسم مفعول مزید: میم مضموم + فتحه ماقبل آخر (مُعَلَّم، مُقَدَّس)"),
                        goldenTechnique = "مُـ + کسره ماقبل آخر = فاعل (کننده کار) | مُـ + فتحه ماقبل آخر = مفعول!",
                        commonMistakeTrap = "نادیده گرفتن حرکت حرف ماقبل آخر پیش از پسوندهای مثنی و جمع (مثل مُؤمِنینَ که کسره میم نشان اسم فاعل است).",
                        targetExamYield = "۱ سوال مستقیم در بخش تحلیل صرفی",
                        targetLessonId = "arabic_02b_derived_nouns_qaleb"
                    ),
                    LearningStation(
                        id = "arab_p2_s3",
                        stationNumber = 7,
                        title = "مشتقات تکمیلی: اسم تفضیل، مکان، زمان، مبالغه و آلت",
                        subtitle = "اوزان أَفْعَل / فُعْلی، مَفْعَل، فَعّال و صفت مشبهه",
                        estimatedHours = 3,
                        targetTestCount = 30,
                        keyConcepts = listOf("اسم تفضیل بر وزن أَفْعَل (مذکر) و فُعْلی (مؤنث: کُبری، صُغری، عُلیا، دُنیا)", "کاربرد استثنایی «خَیْر» و «شَرّ» به عنوان اسم تفضیل (بدون همزه)", "اسم مبالغه بر وزن فَعّال و فَعّالة (غَفّار، عَلّامة)", "اسم مکان و زمان بر وزن مَفْعَل و مَفْعِل"),
                        goldenTechnique = "کلمات «خَیْر» و «شَرّ» به معنی «بهتر/بهترین» و «بدتر/بدترین» پرکاربردترین دام طراح در اسم تفضیل هستند!",
                        commonMistakeTrap = "اشتباه گرفتن «أَفْعَل» در رنگ‌ها و عیب‌ها (مثل أَحْمَر، أَبْکَم که صفت مشبهه هستند) با اسم تفضیل.",
                        targetExamYield = "۱ تست ترکیبی در قواعد و تحلیل صرفی",
                        targetLessonId = "arabic_11_ch1_tafzeel_makan_zaman"
                    )
                )
            ),
            CurriculumPhase(
                phaseNumber = 3,
                title = "مرحله ۳: شاهراه نحو و نقش‌های کلمات",
                milestoneGoal = "تسلط بر اعراب، تشخیص جایگاه دستوری و فنون رد گزینه در نقش‌های نحوی",
                badge = "مهندسی نحو و اعراب",
                estimatedTotalHours = 14,
                stations = listOf(
                    LearningStation(
                        id = "arab_p3_s1",
                        stationNumber = 8,
                        title = "مرفوعات: فاعل، نائب فاعل، مبتدا و خبر",
                        subtitle = "انواع خبر (مفرد، جمله، شبه جمله) و تقدم خبر بر مبتدا",
                        estimatedHours = 3,
                        targetTestCount = 35,
                        keyConcepts = listOf("فاعل ظاهر و ضمیر بارز و مستتر", "نائب فاعل پس از فعل مجهول (فُعِلَ / یُفْعَلُ)", "انواع خبر: مفرد، جمله فعلیه/اسمیه، شبه‌جمله (جارومجرور یا ظرف)", "خبر مقدم (شبه جمله در ابتدای جمله اسمیه: فی البیتِ رَجُلٌ)"),
                        goldenTechnique = "هرگاه جمله با «جار و مجرور» یا «عِنْدَ / لَدی» شروع شود و بعدش اسم مرفوع بیاید = ۱۰۰٪ خبر مقدم و مبتدای مؤخر!",
                        commonMistakeTrap = "فاعل گرفتن اسم بعد از کانَ یا اسم قبل از فعل.",
                        targetExamYield = "۲ تست قطعی در بخش قواعد و اعراب"
                    ),
                    LearningStation(
                        id = "arab_p3_s2",
                        stationNumber = 9,
                        title = "نواسخ: افعال ناقصه، حروف مشبهة بالفعل و لا نفی جنس",
                        subtitle = "کانَ و اخواتها، إِنَّ و اخواتها، لا نفی جنس و شروط عمل",
                        estimatedHours = 3,
                        targetTestCount = 40,
                        keyConcepts = listOf("افعال ناقصه (کانَ، صارَ، أَصبَحَ، لَیسَ، ما دامَ): اسم مرفوع و خبر منصوب", "حروف مشبهه (إِنَّ، أَنَّ، کَأَنَّ، لکِنَّ، لَیتَ، لَعَلَّ): اسم منصوب و خبر مرفوع", "لا نفی جنس: لا + اسم نکره مفرد مبنی بر فتح بدون تنوین + خبر مرفوع (لا شَکَّ فیهِ)"),
                        goldenTechnique = "لا + اسم نکره بدون ال و بدون تنوین با فتحه = لا نفی جنس (در محل نصب)!",
                        commonMistakeTrap = "فراموش کردن این‌که وقتی «ما» به «إنَّ» بچسبد (إِنَّما)، عمل آن باطل می‌شود و بعدش مبتدا و خبر معمولی می‌آید.",
                        targetExamYield = "۲ تست بسیار رایج در سوالات اعراب و تحلیل صرفی"
                    ),
                    LearningStation(
                        id = "arab_p3_s3",
                        stationNumber = 10,
                        title = "منصوبات: مفعول‌به، مفعول مطلق، مفعول‌فیه و منادا",
                        subtitle = "مفعول مطلق تأکیدی و نوعی، ظروف و انواع منادا (مفرد علم، نکره مقصوده، مضاف)",
                        estimatedHours = 3,
                        targetTestCount = 35,
                        keyConcepts = listOf("مفعول مطلق تأکیدی (مصدر تنها از جنس فعل) و نوعی (همراه صفت یا مضاف‌الیه)", "منادای مبنی بر ضم در محل نصب (مفرد علم: یا علیُّ / نکره مقصوده: یا تلمیذُ)", "منادای معرب منصوب (منادای مضاف: یا رَبَّ العالَمینَ)", "ندای اسم دارای ال با أَیُّها و أَیَّتُها (کلمه بعد از أیها عطف بیان یا صفت مرفوع است)"),
                        goldenTechnique = "اسم بعد از «یا أَیُّها / یا أَیَّتُها» همیشه مرفوع است و منادا خودِ «أیُّ» است نه کلمه بعدش!",
                        commonMistakeTrap = "منادا گرفتن کلمه «النّاسُ» در «یا أَیُّها النّاسُ».",
                        targetExamYield = "۲ تست ترکیبی در ترجمه و قواعد منصوبات",
                        targetLessonId = "arabic_step11_mansoobat_estisna_monada_table"
                    ),
                    LearningStation(
                        id = "arab_p3_s4",
                        stationNumber = 11,
                        title = "حال (مفرد و جمله حالیه) و شگردهای تشخیص آن از صفت",
                        subtitle = "صاحب‌حال معرفه، حال مشتق نکره و جمله حالیه همراه با واو حالیه",
                        estimatedHours = 3,
                        targetTestCount = 35,
                        keyConcepts = listOf("حال مفرد: مشتق، نکره، منصوب، بیانگر حالت صاحب‌حال معرفه", "جمله حالیه: پس از اسم معرفه یا با «وَ» حالیه (وَ هُوَ...، وَ قَدْ...)", "تفاوت جمله حالیه (پس از معرفه) و جمله وصفیه (پس از نکره)"),
                        goldenTechnique = "فرمول طلایی: اسم معرفه + جمله = جمله حالیه | اسم نکره + جمله = جمله وصفیه (صفت)!",
                        commonMistakeTrap = "اشتباه گرفتن حال مفرد با مفعول‌به یا خبر کانَ.",
                        targetExamYield = "۲ تست قطعی و رتبه‌ساز کنکور سراسری انسانی",
                        targetLessonId = "ar12-02-lesson2-hal"
                    ),
                    LearningStation(
                        id = "arab_p3_s5",
                        stationNumber = 12,
                        title = "تمییز، استثنا، مجرورات و توابع (صفت، معطوف، بدل، تأکید)",
                        subtitle = "ابهام‌زدایی با تمییز، مستثنی تام و مفرغ و تبعیت در اعراب",
                        estimatedHours = 2,
                        targetTestCount = 30,
                        keyConcepts = listOf("تمییز: اسم نکره جامد منصوب پس از اعداد، وزن، مساحت و اسم تفضیل (أَکْثَرُ عِلْماً)", "استثنای تام (مستثنی منصوب) و استثنای مفرغ (حذف مستثنی‌منه و اعراب طبق نقش در جمله)", "صفت و موصوف (تبعیت در ۴ ویژگی) در برابر مضاف و مضاف‌الیه"),
                        goldenTechnique = "اسم نکره منصوب بلافاصله بعد از اسم تفضیل (أَفْعَل) یا کلمات «کَمْ / کَفی / اِمْتَلَأَ» = ۱۰۰٪ تمییز!",
                        commonMistakeTrap = "منصوب گرفتن کلمه بعد از «إلّا» در استثنای مفرغ (در حالی که باید نقشش در جمله مثل فاعل بررسی شود).",
                        targetExamYield = "۲ تست اختصاصی اعراب در دفترچه ۲",
                        targetLessonId = "ar12-03-lesson3-istithna-tamyiz"
                    )
                )
            ),
            CurriculumPhase(
                phaseNumber = 4,
                title = "مرحله ۴: تکنیک رد گزینه ترجمه در ۵ ثانیه کنکور ۱۴۰۶",
                milestoneGoal = "حل سریع تمام تست‌های ترجمه، تعریب و درک مطلب بدون ترجمه کامل خط‌به‌خط",
                badge = "پادشاه رد گزینه ترجمه",
                estimatedTotalHours = 12,
                stations = listOf(
                    LearningStation(
                        id = "arab_p4_s1",
                        stationNumber = 13,
                        title = "رد گزینه فوری بر اساس زمان و ساختار افعال",
                        subtitle = "ماضی استمراری، ماضی بعید، مضارع التزامی و نقلی",
                        estimatedHours = 2,
                        targetTestCount = 40,
                        keyConcepts = listOf("کانَ + مضارع = ماضی استمراری (می‌نوشت / داشت می‌نوشت)", "کانَ + قَدْ + ماضی = ماضی بعید (نوشته بود)", "قَدْ + ماضی = ماضی نقلی یا ساده با قد تحقیق (نوشته است / به راستی نوشت)", "قَدْ + مضارع = تقلیل و احتمال (گاهی / شاید + مضارع)"),
                        goldenTechnique = "ابتدا افعال جمله را علامت بزن؛ فقط زمان فعل را در ۴ گزینه چک کن ⟵ ۳ گزینه در کمتر از ۵ ثانیه حذف می‌شوند!",
                        commonMistakeTrap = "ترجمه «قَدْ + مضارع» به صورت «قطعاً» به جای «گاهی / شاید».",
                        targetExamYield = "حداقل ۴ تست قطعی با پاسخ‌گویی در زیر ۱۰ ثانیه",
                        targetLessonId = "arabic_05_konkoor_translation_keys"
                    ),
                    LearningStation(
                        id = "arab_p4_s2",
                        stationNumber = 14,
                        title = "رد گزینه بر اساس ضمایر، مفرد و جمع و اشاره",
                        subtitle = "شناسایی ضمایر پیوسته، اسم اشاره (هذا/ذلک) و مطابقت عدد",
                        estimatedHours = 2,
                        targetTestCount = 30,
                        keyConcepts = listOf("اسم اشاره نزدیک (هذا، هذه، هؤُلاء) در برابر دور (ذلِکَ، تِلْکَ، أُولئِکَ)", "ترجمه اسم اشاره + ال به صورت مفرد تا قبل از خبر", "تفکیک مفرد و جمع در کلمات فریبنده (مثل: إِخْوان، قَوْم، شَعْب)"),
                        goldenTechnique = "اگر در صورت سوال ضمیر وجود نداشت ولی در گزینه‌ای ضمیر «ش/تان/شان» اضافه شده بود (یا برعکس)، فوراً خط بزن!",
                        commonMistakeTrap = "ترجمه «هذا» به «آن» یا «ذلک» به «این».",
                        targetExamYield = "۲ تست اختصاصی رد گزینه در ۵ ثانیه"
                    ),
                    LearningStation(
                        id = "arab_p4_s3",
                        stationNumber = 15,
                        title = "معرفه و نکره و ترجمه جمله وصفیه",
                        subtitle = "تفاوت ترجمه «الکتابُ» با «کتابٌ» و حرف ربط «که» بعد از اسم نکره",
                        estimatedHours = 2,
                        targetTestCount = 30,
                        keyConcepts = listOf("اسم نکره = ترجمه با «یک» یا «ـی» نکره", "اسم نکره + جمله فعل = افزودن «که» در ترجمه و تغییر زمان فعل طبق فعل اول", "اسم موصول (الّذی، الّتی، مَنْ، ما) و جمله صله"),
                        goldenTechnique = "اسم نکره + فعل مضارع پس از فعل ماضی = مضارع در فارسی به صورت ماضی استمراری ترجمه می‌شود!",
                        commonMistakeTrap = "عدم آوردن حرف ربط «که» بین موصوف نکره و جمله وصفیه.",
                        targetExamYield = "۲ تست ثابت در کنکورهای انسانی",
                        targetLessonId = "arabic_03b_grade11_shart_naqeseh_vasf"
                    ),
                    LearningStation(
                        id = "arab_p4_s4",
                        stationNumber = 16,
                        title = "اسلوب شرط، حصر، جحود و کلمات خاص",
                        subtitle = "ترجمه إِنْ، مَنْ، ما، إِذا، لَوْ، إنّما و لم یکن... لِـ",
                        estimatedHours = 2,
                        targetTestCount = 25,
                        keyConcepts = listOf("ادوات شرط (إِنْ، مَنْ، ما، إِذا): فعل شرط ماضی به صورت مضارع التزامی ترجمه می‌شود", "حرف «لَوْ»: شرط غیرواقعی در گذشته (اگر می‌رفت... هرآینه موفق می‌شد)", "اسلوب جحود (ما کانَ / لَمْ یَکُنْ ... لِـ + مضارع منصوب): کسی نبود که ... کند"),
                        goldenTechnique = "دیدن «لَوْ» = هر دو فعل باید در فارسی ماضی استمراری یا بعید باشند (نه مضارع!).",
                        commonMistakeTrap = "ترجمه افعال ماضی بعد از «إِذا» یا «إِنْ» به صورت گذشته در فارسی.",
                        targetExamYield = "۱ الی ۲ تست بسیار رایج در کنکورهای اخیر"
                    ),
                    LearningStation(
                        id = "arab_p4_s5",
                        stationNumber = 17,
                        title = "درک مطلب و فنون کشف سریع پاسخ در متن",
                        subtitle = "یافتن کلمات کلیدی سوال در متن بدون خواندن کلمه به کلمه",
                        estimatedHours = 2,
                        targetTestCount = 25,
                        keyConcepts = listOf("اسکن چشمی کلمات کلیدی صورت سوال در متن", "شناسایی ادوات علت (لِأَنَّ، بِسَبَبِ) و هدف (لِکَیْ، حَتّی)", "رد گزینه‌های مغایر با نصّ متن در سوالات «أَیُّ العباراتِ صَحیحة / غَیرُ صَحیحة»"),
                        goldenTechnique = "اول صورت سوالات درک مطلب را بخوان و زیر کلیدواژه‌ها خط بکش؛ سپس جواب را خط به خط در متن شکار کن.",
                        commonMistakeTrap = "صرف ۱۰ دقیقه برای ترجمه کامل متن پیش از دیدن سوالات!",
                        targetExamYield = "۴ سوال ثابت درک مطلب کنکور سراسری"
                    ),
                    LearningStation(
                        id = "arab_p4_s6",
                        stationNumber = 18,
                        title = "شبیه‌ساز جامع ۲۰ سوالی عربی کنکور ۱۴۰۶ در ۲۰ دقیقه",
                        subtitle = "۲۰ سوال استاندارد سنجش (ترجمه + تعریب + واژگان + درک مطلب + قواعد)",
                        estimatedHours = 4,
                        targetTestCount = 40,
                        keyConcepts = listOf("مدیریت زمان ۶۰ ثانیه برای هر سوال", "ترتیب پاسخ‌دهی: ترجمه و تعریب (۷ تست در ۴ دقیقه) ➔ واژگان ➔ قواعد و تحلیل صرفی ➔ درک مطلب"),
                        goldenTechnique = "با حل ۷ تست ترجمه در ۴ دقیقه اول، ۱۶ دقیقه زمان خالص برای قواعد و درک مطلب ذخیره می‌شود.",
                        commonMistakeTrap = "بی‌دقتی به حرکات آخر کلمات در سوالات تحلیل صرفی و اعراب.",
                        targetExamYield = "تضمین درصد بالای ۸۵٪ در عربی تخصصی کنکور ۱۴۰۶"
                    )
                )
            )
        )
    )

    // ۳. معارف اسلامی و دین و زندگی کنکور ۱۴۰۶ (و آزمون فرهنگیان)
    val maarefAndDiniPath = StagedSubjectPath(
        subjectId = "dini",
        subjectName = "معارف، دین و زندگی و فرهنگیان ۱۴۰۶",
        totalPhasesCount = 4,
        totalStationsCount = 16,
        konkoorWeightInfo = "سوابق تحصیلی امتحان نهایی (۶۰٪ تأثیر) • دفترچه اختصاصی فرهنگیان",
        themeColor = SubjectFarhangianColor,
        badgeTag = "سنگر اول تراز کل و پذیرش دانشگاه فرهنگیان",
        iconDescription = "رحل قرآن و چراغ حکمت الهی",
        summaryDescription = "مسیر ۴ مرحله‌ای تسلط بر مراتب توحید، پیام آیات، احکام دوازدهم و کتب اختصاصی فرهنگیان (مطهری و قرائتی)",
        phases = listOf(
            CurriculumPhase(
                phaseNumber = 1,
                title = "مرحله ۱: هندسه توحید، مراتب آن و ریشه‌های شرک",
                milestoneGoal = "تفکیک صددرصدی توحید ذاتی، صفاتی، افعالی و عبادی و پیوند با آیات",
                badge = "شالوده اعتقادی",
                estimatedTotalHours = 10,
                stations = listOf(
                    LearningStation(
                        id = "dini_p1_s1",
                        stationNumber = 1,
                        title = "توحید ذاتی و نفی شریک و شبیه",
                        subtitle = "مرتبه اول: یگانگی ذات، بساطت و بی‌نیازی مطلق خداوند",
                        estimatedHours = 2,
                        targetTestCount = 25,
                        keyConcepts = listOf("توحید ذاتی (خداوند مثل، مانند، شریک و اجزا ندارد)", "آیه «لَیْسَ کَمِثْلِهِ شَیْءٌ» و سوره توحید (أَحَد و صَمَد)", "برهان تمانع (لَوْ کانَ فیهِما آلِهَةٌ إِلَّا اللّهُ لَفَسَدَتا)"),
                        goldenTechnique = "کلمات «أحد»، «صمد» و «لم یلد و لم یولد» مستقیماً به توحید ذاتی اشاره دارند؛ آیه لو کان فیهما آلهة اثبات توحید افعالی و ذاتی از راه نظم است.",
                        commonMistakeTrap = "اشتباه گرفتن توحید ذاتی با توحید افعالی در آیات خالقیت و تدبیر.",
                        targetExamYield = "۱ سوال قطعی مفهومی در نهایی و فرهنگیان",
                        targetLessonId = "dini3-02-yegane-bi-hamta"
                    ),
                    LearningStation(
                        id = "dini_p1_s2",
                        stationNumber = 2,
                        title = "توحید صفاتی و عینیت ذات و صفات",
                        subtitle = "صفات خداوند زائد بر ذات او نیستند و عین ذاتند",
                        estimatedHours = 2,
                        targetTestCount = 20,
                        keyConcepts = listOf("عینیت ذات با صفات (خداوند عین علم و عین قدرت است)", "نامحدود و هماهنگ بودن صفات الهی", "تفاوت صفات انسان (عارضی و محدود) با صفات خداوند"),
                        goldenTechnique = "هر جا سخن از «عدم دوگانگی بین ذات و صفات» یا «کمال مطلق در علم و قدرت» بود = توحید صفاتی.",
                        commonMistakeTrap = "پندار این‌که تعدد نام‌های حسنای الهی باعث تعدد در ذات می‌شود.",
                        targetExamYield = "تست پرتکرار و چالش‌برانگیز معارف",
                        targetLessonId = "dini3-02-yegane-bi-hamta"
                    ),
                    LearningStation(
                        id = "dini_p1_s3",
                        stationNumber = 3,
                        title = "توحید افعالی: خالقیت، ربوبیت، مالکیت و ولایت",
                        subtitle = "همه افعال و تحولات جهان مستند به اراده و تدبیر الهی است و اسباب، مستقل نیستند",
                        estimatedHours = 3,
                        targetTestCount = 35,
                        keyConcepts = listOf("توحید در خالقیت (آفرینش از عدم)", "توحید در ربوبیت (پرورش، تدبیر، روزی دادن، هدایت تکوینی و تشریعی)", "توحید در مالکیت و ولایت", "نفی استقلال از علل و عوامل طبیعی (برگ درختان با اذن او می‌افتد)"),
                        goldenTechnique = "تدبیر، نزول باران، روزی رساندن و قانون‌گذاری = ربوبیت | آفرینش اولیه = خالقیت.",
                        commonMistakeTrap = "نفی قانون علت و معلول به بهانه توحید افعالی (توحید افعالی علل طبیعی را نفی نمی‌کند بلکه استقلال آن‌ها را نفی می‌کند).",
                        targetExamYield = "حداقل ۲ سوال ثابت در آزمون‌ها",
                        targetLessonId = "dini3-02-yegane-bi-hamta"
                    ),
                    LearningStation(
                        id = "dini_p1_s4",
                        stationNumber = 4,
                        title = "توحید عبادی و عملی (بعد فردی و اجتماعی)",
                        subtitle = "اخلاص در بندگی (إیّاکَ نَعبُدُ)، توحید در اطاعت و مبارزه با طاغوت",
                        estimatedHours = 3,
                        targetTestCount = 30,
                        keyConcepts = listOf("توحید عبادی: پرستش، دعا و استعانت فقط از خدا", "بعد فردی توحید عملی: اخلاص در نیت و پیروی از هوای نفس نکردن (أَفَرَأَیْتَ مَنِ اتَّخَذَ إِلهَهُ هَواهُ)", "بعد اجتماعی توحید عملی: پذیرش حاکمیت الهی و نفی طاغوت", "شرک جلی و شرک خفی (ریاکاری)"),
                        goldenTechnique = "آیه «اتخذ إلهه هواه» (پرستش هوای نفس) مهم‌ترین آیه در بعد فردی توحید عملی است.",
                        commonMistakeTrap = "یکی دانستن توحید نظری (ذاتی، صفاتی، افعالی) با توحید عملی (عبادی و اطاعت).",
                        targetExamYield = "۲ سوال مفهومی در حوزه اخلاص و بندگی",
                        targetLessonId = "dini3-03-towhid-va-sabk-zendegi"
                    )
                )
            ),
            CurriculumPhase(
                phaseNumber = 2,
                title = "مرحله ۲: شاه‌کلید پیام آیات، روایات و سنت‌های الهی",
                milestoneGoal = "کشف روابط علت و معلولی آیات دهم، یازدهم و دوازدهم و تشخیص دقیق سنت‌های الهی",
                badge = "کاشف اسرار آیات",
                estimatedTotalHours = 12,
                stations = listOf(
                    LearningStation(
                        id = "dini_p2_s1",
                        stationNumber = 5,
                        title = "سنت‌های الهی در زندگی فرد و جامعه (ابتلا، استدراج، املاء، امداد، توفیق)",
                        subtitle = "قوانین ثابت و تغییرناپذیر حاکم بر تاریخ و سرنوشت انسان‌ها در قرآن",
                        estimatedHours = 3,
                        targetTestCount = 35,
                        keyConcepts = listOf("سنت ابتلا (آزمایش همگانی با خوشی‌ها و ناخوشی‌ها جهت شکوفایی استعدادها)", "سنت امداد عام (برای همه: کُلّاً نُمِدُّ هؤُلاءِ) و امداد خاص (برای اهل تقوا و جهاد)", "سنت استدراج و املاء (باز کردن درهای نعمت بر گناهکاران غافل پیش از هلاکت)", "سنت توفیق (هموارسازی راه خیر) و خذلان (واگذاری گناهکار به حال خود)"),
                        goldenTechnique = "نعمت بعد از فراموشی یاد خدا (فَلَمّا نَسُوا... فَتَحْنا عَلَیْهِمْ أَبْوابَ کُلِّ شَیْءٍ) = ۱۰۰٪ سنت استدراج!",
                        commonMistakeTrap = "اشتباه گرفتن سنت املاء (مهلت دادن) با سنت ابتلا (آزمایش).",
                        targetExamYield = "۲ تست حتمی و قطعی در هر آزمون نهایی و سنجش",
                        targetLessonId = "dini3-06-sonnathaye-khodavand-dar-zendegi"
                    ),
                    LearningStation(
                        id = "dini_p2_s2",
                        stationNumber = 6,
                        title = "قضا و قدر الهی، اختیار انسان و اجل (مسمی و معلق)",
                        subtitle = "تبیین علمی تقدیر، حتمیت قضا و نقش اراده انسان در رقم زدن سرنوشت",
                        estimatedHours = 3,
                        targetTestCount = 30,
                        keyConcepts = listOf("قدر الهی (اندازه‌گیری و قالب‌بندی پدیده‌ها) مقدم بر قضا الهی (حتمیت و تحقق)", "اختیار انسان در طول اراده الهی (نه جبر و نه تفویض؛ امرٌ بین الامرین)", "تغییر قضا و قدر با دعا، صدقه و صله رحم"),
                        goldenTechnique = "«مهندسی و اندازه‌گیری» = قَدَر | «امضای قطعی و وقوع» = قضا.",
                        commonMistakeTrap = "تصور این‌که علم پیشین خداوند به اعمال ما باعث مجبور بودن ما می‌شود.",
                        targetExamYield = "۱ سوال چالش‌ساز اعتقادی",
                        targetLessonId = "dini3-05-qodrat-parvaz"
                    ),
                    LearningStation(
                        id = "dini_p2_s3",
                        stationNumber = 7,
                        title = "هدف خلقت، مرگ، عالم برزخ و براهین اثبات معاد",
                        subtitle = "برهان حکمت، عدالت و رحمت، ویژگی‌های برزخ و تجسم اعمال در قیامت",
                        estimatedHours = 3,
                        targetTestCount = 35,
                        keyConcepts = listOf("برهان حکمت (نفی عبث بودن آفرینش: أَفَحَسِبْتُمْ أَنَّما خَلَقْناکُمْ عَبَثاً)", "برهان عدالت (لزوم تحقق پاداش و کیفر کامل: أَمْ نَجْعَلُ الَّذینَ آمَنُوا... کَالْمُفْسِدینَ)", "پرونده باز پس از مرگ (باقیات صالحات، فرزند صالح، علم نافع)", "ویژگی‌های قیامت: تجسم اعمال و گواهان درونی (اعضا و جوارح) و بیرونی"),
                        goldenTechnique = "دیدن کلمه «عبثاً / باطلاً / لاعبین» در آیه = برهان حکمت | دیدن مقایسه مؤمن و مفسد = برهان عدالت!",
                        commonMistakeTrap = "اشتباه گرفتن آیات پاسخ به استبعاد منکران معاد (خلقت نخستین و زنده شدن زمین) با براهین ضرورت معاد.",
                        targetExamYield = "۲ تست مفهومی برهان‌ها و برزخ",
                        targetLessonId = "dini_10_ch2_death_barzakh_hereafter"
                    ),
                    LearningStation(
                        id = "dini_p2_s4",
                        stationNumber = 8,
                        title = "روابط علت و معلولی و تقدم و تأخر در آیات منتخب",
                        subtitle = "شناسایی شرط، جزا، مقدمه و نتیجه در متن آیات دهم تا دوازدهم",
                        estimatedHours = 3,
                        targetTestCount = 30,
                        keyConcepts = listOf("تقوا ⟵ فرقان (نیروی تشخیص حق از باطل: إِنْ تَتَّقُوا اللّهَ یَجْعَلْ لَکُمْ فُرْقاناً)", "مجاهدت در راه خدا ⟵ هدایت به راه‌های الهی", "اعراض از یاد خدا ⟵ معیشت ضنک (زندگی تنگ و سخت)"),
                        goldenTechnique = "در جملات شرطی قرآنی (إِنْ / مَنْ)، عبارت اول همیشه «علت و مقدمه» و عبارت دوم «معلول و ثمره» است.",
                        commonMistakeTrap = "جابه‌جا کردن جای علت و معلول در گزینه‌های تستی.",
                        targetExamYield = "۲ سوال تفکیک علت و معلول و پیام آیات"
                    )
                )
            ),
            CurriculumPhase(
                phaseNumber = 3,
                title = "مرحله ۳: اخلاص، توبه، احکام و دروس پایه دوازدهم",
                milestoneGoal = "تسلط بر مباحث نهایی دوازدهم جهت تضمین نمره ۲۰ سوابق تحصیلی",
                badge = "ضامن نمره ۲۰ نهایی",
                estimatedTotalHours = 12,
                stations = listOf(
                    LearningStation(
                        id = "dini_p3_s1",
                        stationNumber = 9,
                        title = "اخلاص در عمل، حسن فاعلی و حسن فعلی",
                        subtitle = "شروط قبولی عمل، نیت الهی و آثار دنیوی و اخروی اخلاص",
                        estimatedHours = 2,
                        targetTestCount = 25,
                        keyConcepts = listOf("حسن فعلی (خوب بودن خود کار) + حسن فاعلی (خالص بودن نیت انجام‌دهنده)", "آثار اخلاص: جاری شدن چشمه‌های حکمت از قلب بر زبان در ۴۰ روز", "نفوذناپذیری مخلصان در برابر وسوسه شیطان (إِلّا عِبادَکَ مِنْهُمُ الْمُخْلَصینَ)"),
                        goldenTechnique = "پذیرش عمل در درگاه الهی مشروط به جمعِ هم‌زمان «حسن فعلی» و «حسن فاعلی» است.",
                        commonMistakeTrap = "تصور این‌که کار نیک بدون نیت الهی پاداش اخروی دارد.",
                        targetExamYield = "۱ سوال قطعی در امتحان نهایی و کنکور",
                        targetLessonId = "dini3-04-faghat-baraye-to"
                    ),
                    LearningStation(
                        id = "dini_p3_s2",
                        stationNumber = 10,
                        title = "توبه، ارکان، مراحل و جبران حق‌الناس",
                        subtitle = "پشیمانی قلبی، عزم بر ترک، جبران گذشته و تبدیل سیئات به حسنات",
                        estimatedHours = 3,
                        targetTestCount = 30,
                        keyConcepts = listOf("حقیقت توبه: ندامت و پشیمانی قلبی", "جبران حق‌الله (قضای واجبات) و حق‌الناس (رد مال و جلب رضایت)", "آثار توبه نصوح: تبدیل سیئات به حسنات (یُبَدِّلُ اللّهُ سَیِّئاتِهِمْ حَسَناتٍ)"),
                        goldenTechnique = "رکن اصلی توبه «پشیمانی درونی» است و شرط تکمیل آن «جبران حقوق ضایع‌شده» است.",
                        commonMistakeTrap = "بسنده کردن به استغفار لفظی بدون ردّ مظالم و حق‌الناس.",
                        targetExamYield = "۱ الی ۲ تست اخلاقی-اعتقادی با ضریب تکرار بالا",
                        targetLessonId = "dini3-08-bazgasht"
                    ),
                    LearningStation(
                        id = "dini_p3_s3",
                        stationNumber = 11,
                        title = "احکام خانواده، عفاف، معیشت اسلامی و مسئولیت اجتماعی",
                        subtitle = "اهداف ازدواج در قرآن، عوامل استحکام خانواده، حرمت ربا و امر به معروف",
                        estimatedHours = 3,
                        targetTestCount = 30,
                        keyConcepts = listOf("آیه ۲۱ سوره روم (لِتَسْکُنُوا إِلَیْها وَ جَعَلَ بَیْنَکُمْ مَوَدَّةً وَ رَحْمَةً)", "اقتصاد اسلامی: کسب حلال، حرمت ربا (جنگ با خدا)، اسراف و تبذیر", "مراتب و شرایط امر به معروف و نهی از منکر"),
                        goldenTechnique = "اسراف = زیاده‌روی بیش از حد نیاز | تبذیر = ریخت‌وپاش و اتلاف مال حتی در مقدار کم!",
                        commonMistakeTrap = "اشتباه گرفتن مفهوم اسراف با تبذیر در آیه «إِنَّ الْمُبَذِّرینَ کانُوا إِخْوانَ الشَّیاطینِ».",
                        targetExamYield = "بخش مهمی از بارم امتحان نهایی یازدهم و دوازدهم"
                    ),
                    LearningStation(
                        id = "dini_p3_s4",
                        stationNumber = 12,
                        title = "نبوت، امامت، مرجعیت و تمدن نوین اسلامی",
                        subtitle = "ختم نبوت، تداوم هدایت در امامت، ولایت فقیه و ویژگی‌های تمدن مهدوی",
                        estimatedHours = 4,
                        targetTestCount = 35,
                        keyConcepts = listOf("علل ختم نبوت (بلوغ عقلی بشر، کامل بودن قرآن و وجود امامان معصوم)", "ابعاد سه گانه مقام امامت: مرجعیت دینی، ولایت ظاهری (حکومت) و ولایت معنوی", "مسئولیت منتظران در عصر غیبت و زمینه‌سازی تمدن اسلامی"),
                        goldenTechnique = "تبیین و تفسیر وحی = مرجعیت دینی | رهبری سیاسی و اجتماعی امت = ولایت ظاهری.",
                        commonMistakeTrap = "خلط کردن وظایف شأن نبوت (دریافت وحی) با شأن امامت (تبیین و اجرای وحی).",
                        targetExamYield = "۲ سوال تضمینی نهایی و فرهنگیان"
                    )
                )
            ),
            CurriculumPhase(
                phaseNumber = 4,
                title = "مرحله ۴: معارف اختصاصی و هوش معلمی آزمون فرهنگیان ۱۴۰۶",
                milestoneGoal = "تسلط بر کتب اختصاصی شهید مطهری و حجت‌الاسلام قرائتی ویژه پذیرش دانشگاه فرهنگیان",
                badge = "تضمین قبولی فرهنگیان",
                estimatedTotalHours = 12,
                stations = listOf(
                    LearningStation(
                        id = "dini_p4_s1",
                        stationNumber = 13,
                        title = "کتاب تعلیم و تربیت در اسلام شهید مرتضی مطهری",
                        subtitle = "پرورش تعقل در برابر انباشت محفوظات، تربیت اراده، عبادت و کرامت نفس",
                        estimatedHours = 3,
                        targetTestCount = 35,
                        keyConcepts = listOf("تفاوت تعلیم (انتقال اطلاعات) و تربیت (شکوفاسازی استعدادهای درونی)", "عقل مطبوع (فطری) و عقل مسموع (تجرّبی و اکتسابی)", "مبارزه با تقلید کورکورانه، تعصب جاهلانه و تحمیل عقیده", "نقش احساس کرامت و شرافت نفس (الحرّية و الکرامة) در تربیت اخلاقی"),
                        goldenTechnique = "از نگاه شهید مطهری، هدف معلم نباید پر کردن انبار حافظه باشد، بلکه باید قوه اجتهاد، تجزیه و تحلیل و تفکر مستقل را در شاگرد بپروراند.",
                        commonMistakeTrap = "اشتباه گرفتن عقل مسموع با عقل مطبوع در حدیث «العقل عقلان: عقل مطبوع و عقل مسموع».",
                        targetExamYield = "۱۰ تست مستقیم در دفترچه اختصاصی فرهنگیان"
                    ),
                    LearningStation(
                        id = "dini_p4_s2",
                        stationNumber = 14,
                        title = "مهارت‌های معلمی حجت‌الاسلام محسن قرائتی",
                        subtitle = "آداب اخلاقی معلم، شیوه‌های تدریس جذاب، تمثیل‌گویی و مدیریت کلاس",
                        estimatedHours = 3,
                        targetTestCount = 35,
                        keyConcepts = listOf("ویژگی‌های شخصیتی معلم: اخلاص، عمل به علم، سعه صدر و محبت به شاگرد", "آمادگی پیش از تدریس: مطالعه، طرح درس و شناخت سطح مخاطب", "فنون تدریس: استفاده از تمثیل، قصه، پرسش و پاسخ، تشویق به‌موقع و پرهیز از تحقیر"),
                        goldenTechnique = "طبق کتاب مهارت‌های معلمی، بهترین ابزار برای انتقال مفاهیم عقلی به ذهن نوجوان «تمثیل و مثال‌های ملموس حسی» است.",
                        commonMistakeTrap = "تذکر علنی خطای دانش‌آموز در جمع کلاس که خلاف اصل حفظ آبروست.",
                        targetExamYield = "۱۰ تست مستقیم در دفترچه تعلیم و تربیت فرهنگیان",
                        targetLessonId = "far_08_qaraati_teacher_methods_complete"
                    ),
                    LearningStation(
                        id = "dini_p4_s3",
                        stationNumber = 15,
                        title = "هوش و استعداد معلمی: استدلال منطقی، درک متن و حل مسئله تربیتی",
                        subtitle = "حل سناریوهای واقعی موقعیت کلاس درس، تعارض دانش‌آموزان و استنتاج تحلیلی",
                        estimatedHours = 3,
                        targetTestCount = 30,
                        keyConcepts = listOf("سناریوهای موقعیتی کانون ارزیابی معلمان", "استنتاج منطقی، یافتن پیش‌فرض‌های پنهان و تقویت/تضعیف استدلال", "مدیریت افت تحصیلی، ناهنجاری رفتاری و ارتباط با اولیا"),
                        goldenTechnique = "در تست‌های موقعیتی فرهنگیان، گزینه‌ای که بر «گفت‌وگوی فردی، محرمانه، همدلانه و ریشه‌یابی علمی علت رفتار» تأکید دارد همیشه پاسخ صحیح است.",
                        commonMistakeTrap = "انتخاب گزینه‌های تنبیهی فوری، اخراج از کلاس یا ارجاع شتاب‌زده به مدیر.",
                        targetExamYield = "۲۰ سوال دفترچه هوش و استعداد معلمی",
                        targetLessonId = "far_09_teacher_aptitude_reasoning"
                    ),
                    LearningStation(
                        id = "dini_p4_s4",
                        stationNumber = 16,
                        title = "شبیه‌ساز جامع دفترچه اختصاصی فرهنگیان ۱۴۰۶",
                        subtitle = "۴۰ سوال اختصاصی (۲۰ هوش معلمی + ۲۰ تعلیم و تربیت اسلامی) در ۶۰ دقیقه",
                        estimatedHours = 3,
                        targetTestCount = 40,
                        keyConcepts = listOf("زمان‌بندی: ۱۵ دقیقه برای ۲۰ تست تعلیم و تربیت + ۴۵ دقیقه برای ۲۰ تست هوش معلمی", "آمادگی مصاحبه تخصصی و کانون ارزیابی دانشگاه فرهنگیان"),
                        goldenTechnique = "شروع دفترچه فرهنگیان با ۲۰ سوال تعلیم و تربیت اسلامی، روحیه و ذخیره زمانی عالی برای سوالات تحلیلی هوش ایجاد می‌کند.",
                        commonMistakeTrap = "گیر کردن روی سوالات وقت‌گیر هوش در ابتدای جلسه و نرسیدن به سوالات ساده معارف در انتهای دفترچه.",
                        targetExamYield = "تضمین دعوت به مصاحبه و قبولی نهایی دانشگاه فرهنگیان"
                    )
                )
            )
        )
    )

    // ۴. روان‌شناسی جامع کنکور ۱۴۰۶
    val psychologyPath = StagedSubjectPath(
        subjectId = "psychology",
        subjectName = "روان‌شناسی جامع کنکور ۱۴۰۶",
        totalPhasesCount = 4,
        totalStationsCount = 14,
        konkoorWeightInfo = "ضریب ۲ کنکور سراسری انسانی ۱۴۰۶ • روان‌شناسی",
        themeColor = SubjectPhilosophyColor,
        badgeTag = "ترازآورترین درس تک‌کتابی کنکور",
        iconDescription = "ذهن، شناخت و رشد انسان",
        summaryDescription = "مسیر ۴ مرحله‌ای تسلط ۱۰۰٪ بر روان‌شناسی یازدهم: تعریف و روش‌ها، رشد، احساس و ادراک، حافظه، تفکر و سلامت روان",
        phases = listOf(
            CurriculumPhase(
                phaseNumber = 1,
                title = "مرحله ۱: چیستی روان‌شناسی، رویکردها و روش‌های پژوهش",
                milestoneGoal = "تفکیک دقیق ۵ رویکرد اصلی روان‌شناسی و تشخیص متغیرهای مستقل، وابسته و کنترل در آزمایش‌ها",
                badge = "مبانی و روش تحقیق",
                estimatedTotalHours = 6,
                stations = listOf(
                    LearningStation(
                        id = "psy_p1_s1",
                        stationNumber = 1,
                        title = "تعریف علمی روان‌شناسی و ۴ عنصر کلیدی آن",
                        subtitle = "علم مطالعه رفتار و فرایندهای روانی بر اساس یافته‌های علمی",
                        estimatedHours = 2,
                        targetTestCount = 25,
                        keyConcepts = listOf(
                            "علم (مشاهده نظام‌مند و روش تجربی)",
                            "رفتار (هر عمل قابل مشاهده مستقیم مثل دویدن، صحبت کردن)",
                            "فرایندهای روانی (اعمال درونی غیرقابل مشاهده مستقیم مثل تفکر، حافظه، هیجان)",
                            "اهداف چهارگانه روان‌شناسی: توصیف، تبیین (علت‌یابی)، پیش‌بینی و کنترل (تغییر)"
                        ),
                        goldenTechnique = "هر پدیده‌ای که با دوربین فیلم‌برداری قابل ثبت باشد = «رفتار» | هر چه در درون ذهن رخ دهد = «فرایند روانی».",
                        commonMistakeTrap = "اشتباه گرفتن هدف «توصیف» (گزارش ویژگی‌ها) با «تبیین» (بیان چرایی و رابطه علت و معلولی).",
                        targetExamYield = "۱ تست قطعی در آغاز سوالات روان‌شناسی کنکور",
                        targetLessonId = "psych_11_ch1_schools_and_development"
                    ),
                    LearningStation(
                        id = "psy_p1_s2",
                        stationNumber = 2,
                        title = "تاریخچه و ۵ رویکرد عمده روان‌شناسی معاصر",
                        subtitle = "ساخت‌گرایی، کنش‌گرایی و رویکردهای زیستی، رفتاری، روان‌تحلیلی، شناختی و انسان‌گرایی",
                        estimatedHours = 2,
                        targetTestCount = 30,
                        keyConcepts = listOf(
                            "رویکرد زیستی: نقش مغز، ژنتیک، هورمون‌ها و سیستم عصبی",
                            "رویکرد رفتاری (واتسون، اسکینر): نقش محیط، شرطی‌سازی، پاداش و تنبیه",
                            "رویکرد روان‌تحلیلی (فروید): نقش ناهشیار، غرایز و تجارب کودکی",
                            "رویکرد شناختی: پردازش اطلاعات، باورها، تفکر و ادراک",
                            "رویکرد انسان‌گرایی (راجرز، مزلو): اراده آزاد، خودشکوفایی و عزت نفس"
                        ),
                        goldenTechnique = "پاداش و محیط = رفتاری | ناهشیار و کودکی = روان‌تحلیلی | پردازش ذهن و باور = شناختی | خودشکوفایی = انسان‌گرایی.",
                        commonMistakeTrap = "خلط کردن مکتب کنش‌گرایی (ویلیام جیمز: سازگاری ذهن با محیط) با ساخت‌گرایی (وونت: تجزیه عناصر هشیاری با درون‌نگری).",
                        targetExamYield = "۱ الی ۲ تست ثابت در کنکور سراسری"
                    ),
                    LearningStation(
                        id = "psy_p1_s3",
                        stationNumber = 3,
                        title = "روش‌های پژوهش: آزمایشی، همبستگی، توصیفی و متغیرها",
                        subtitle = "تشخیص متغیر مستقل، وابسته، کنترل و تعدیل‌کننده در فرضیه پژوهش",
                        estimatedHours = 2,
                        targetTestCount = 35,
                        keyConcepts = listOf(
                            "روش آزمایشی: تنها روش کشف رابطه علت و معلولی (دستکاری متغیر مستقل)",
                            "متغیر مستقل (علت / اثرگذار) در برابر متغیر وابسته (معلول / اثرپذیر)",
                            "روش همبستگی: بررسی رابطه بین دو متغیر بدون دستکاری (همبستگی مثبت و منفی)",
                            "جامعه آماری، نمونه و نمونه‌گیری تصادفی"
                        ),
                        goldenTechnique = "در جمله «بررسی تأثیر A بر B در بین دانش‌آموزان دختر»: A = متغیر مستقل | B = متغیر وابسته | دختر بودن = متغیر کنترل!",
                        commonMistakeTrap = "نتیجه‌گیری علت و معلولی از پژوهش‌های همبستگی (همبستگی هرگز به معنای علیت نیست!).",
                        targetExamYield = "۲ تست حتمی در کنکور انسانی",
                        targetLessonId = "psych_11_ch1_schools_and_development"
                    )
                )
            ),
            CurriculumPhase(
                phaseNumber = 2,
                title = "مرحله ۲: روان‌شناسی رشد — از کودکی تا سالمندی",
                milestoneGoal = "تسلط کامل بر ۴ مرحله تحول شناختی پیاژه، سبک‌های فرزندپروری و بحران‌های هویت نوجوانی",
                badge = "معماری مراحل رشد",
                estimatedTotalHours = 8,
                stations = listOf(
                    LearningStation(
                        id = "psy_p2_s1",
                        stationNumber = 4,
                        title = "مفاهیم پایه رشد: رسش، یادگیری، دوره حساس و وراثت و محیط",
                        subtitle = "تعامل ژنتیک و محیط و تفاوت تغییر کمی و کیفی در طول زندگی",
                        estimatedHours = 2,
                        targetTestCount = 25,
                        keyConcepts = listOf(
                            "رسش (پختگی): شکوفایی برنامه‌ریزی‌شده زیستی مستقل از تمرین (مثل راه رفتن در ۱ سالگی)",
                            "دوره حساس رشد: بازه زمانی طلایی برای کسب یک مهارت (مثل زبان‌آموزی در کودکی)",
                            "دیدگاه تعاملی در مناقشه وراثت (طبیعت) و محیط (تربیت)"
                        ),
                        goldenTechnique = "اگر مهارتی بدون آموزش خاص و با رسیدن به سن معین ظاهر شود و تمرین زودرس اثری بر آن نداشته باشد = ۱۰۰٪ رسش!",
                        commonMistakeTrap = "نادیده گرفتن این اصل که بدون رسیدن به سطح لازم «رسش»، هیچ «یادگیری» و تمرینی مؤثر نخواهد بود.",
                        targetExamYield = "۱ تست مفهومی از فصل رشد",
                        targetLessonId = "psych_11_ch1_schools_and_development"
                    ),
                    LearningStation(
                        id = "psy_p2_s2",
                        stationNumber = 5,
                        title = "نظریه ۴ مرحله‌ای تحول شناختی ژان پیاژه",
                        subtitle = "حسی-حرکتی، پیش‌عملیاتی، عملیات عینی و عملیات انتزاعی (صوری)",
                        estimatedHours = 3,
                        targetTestCount = 40,
                        keyConcepts = listOf(
                            "حسی-حرکتی (۰ تا ۲ سال): کسب «پایداری شیء» در پایان مرحله",
                            "پیش‌عملیاتی (۲ تا ۷ سال): خودمداری، جاندارپنداری (انیمیسم)، عدم نگهداری ذهنی (عدم درک بقا)",
                            "عملیات عینی (۷ تا ۱۱ سال): کسب «نگهداری ذهنی (مفهوم بقا)»، بازگشت‌پذیری و طبقه‌بندی ملموس",
                            "عملیات صوری/انتزاعی (۱۱ سال به بالا): تفکر فرضی-قیاسی و درک مفاهیم انتزاعی (عدالت، آزادی)"
                        ),
                        goldenTechnique = "کودکی که فکر می‌کند خورشید دنبال او راه می‌رود = جاندارپنداری (پیش‌عملیاتی) | کودکی که می‌فهمد آب در لیوان باریک و پهن برابر است = نگهداری ذهنی (عملیات عینی).",
                        commonMistakeTrap = "اشتباه گرفتن «خودمداری» کودک پیش‌عملیاتی (ناتوانی در دیدن از زاویه دید دیگران) با «خودخواهی اخلاقی».",
                        targetExamYield = "۲ تست قطعی و ۱۰۰٪ ثابت در هر کنکور سراسری",
                        targetLessonId = "psych_11_ch1_schools_and_development"
                    ),
                    LearningStation(
                        id = "psy_p2_s3",
                        stationNumber = 6,
                        title = "سبک‌های فرزندپروری بامریند و رشد اخلاقی و نوجوانی",
                        subtitle = "مقتدرانه، مستبدانه، سهل‌گیرانه و مسامحه‌کارانه (طردکننده) + افسانه شخصی",
                        estimatedHours = 3,
                        targetTestCount = 35,
                        keyConcepts = listOf(
                            "فرزندپروری مقتدرانه (کنترل و نظارت بالا + گرم و پذیرش بالا): بهترین سبک؛ فرزندان مستقل و باعزت‌نفس",
                            "فرزندپروری مستبدانه (کنترل بالا + پذیرش و محبت پایین)",
                            "فرزندپروری سهل‌گیرانه (محبت بالا + کنترل و محدودیت پایین)",
                            "خودمداری نوجوانی: «تماشاگر خیالی» (همه به من نگاه می‌کنند) و «افسانه شخصی» (من استثنا هستم و آسیب نمی‌بینم)"
                        ),
                        goldenTechnique = "جدول ۲×۲ بامریند: کنترل بالا + محبت بالا = مقتدرانه | کنترل بالا + محبت کم = مستبدانه!",
                        commonMistakeTrap = "اشتباه گرفتن واژه «مقتدرانه (مطلوب)» با «مستبدانه (نامطلوب)» در صورت سوالات.",
                        targetExamYield = "۲ تست کاربردی در کنکور و آزمون فرهنگیان"
                    )
                )
            ),
            CurriculumPhase(
                phaseNumber = 3,
                title = "مرحله ۳: احساس، توجه، ادراک و معماری حافظه",
                milestoneGoal = "تسلط بر قوانین گشتالت، خطاهای ادراکی، سه مخزن حافظه و علل فراموشی",
                badge = "مهندسی شناخت و حافظه",
                estimatedTotalHours = 10,
                stations = listOf(
                    LearningStation(
                        id = "psy_p3_s1",
                        stationNumber = 7,
                        title = "احساس، آستانه‌ها، توجه و قوانین سازمان‌دهی ادراک (گشتالت)",
                        subtitle = "آستانه مطلق و افتراقی، عادت‌واره (انطباق حسی) و قوانین مجاورت، مشابهت و بستن",
                        estimatedHours = 3,
                        targetTestCount = 35,
                        keyConcepts = listOf(
                            "احساس (دریافت خام محرک در گیرنده‌ها) در برابر ادراک (معنابخشی و تفسیر در مغز)",
                            "آستانه مطلق (حداقل انرژی لازم برای تشخیص محرک در ۵۰٪ مواقع) و آستانه افتراقی (حداقل تفاوت قابل تشخیص)",
                            "توجه انتخابی (پدیده مهمانی شبانه / کوکتل پارتی) و توجه تقسیم‌شده",
                            "قوانین گشتالت در ادراک: شکل و زمینه، مجاورت (نزدیکی)، مشابهت، تداوم و بستن (تکمیل)"
                        ),
                        goldenTechnique = "شنیدن نام خود در میان همهمه شلوغ کلاس = توجه انتخابی | خواندن کلماتی که نقطه‌هایشان افتاده = قانون بستن (تکمیل) گشتالت!",
                        commonMistakeTrap = "اشتباه گرفتن «انطباق حسی» (کاهش حساسیت گیرنده‌ها به محرک یکنواخت مثل بوی عطر) با «آستانه افتراقی».",
                        targetExamYield = "۲ تست قطعی در دفترچه ۱",
                        targetLessonId = "psych_03_perception_attention_gestalt"
                    ),
                    LearningStation(
                        id = "psy_p3_s2",
                        stationNumber = 8,
                        title = "مراحل سه گانه حافظه (رمزگردانی، اندوزش، بازیابی) و انواع مخازن",
                        subtitle = "حافظه حسی، کوتاه‌مدت (کاری) و بلندمدت + قطعه‌بندی (Chunking)",
                        estimatedHours = 4,
                        targetTestCount = 40,
                        keyConcepts = listOf(
                            "حافظه حسی: ظرفیت بسیار زیاد، مدت بسیار کوتاه (دیداری/آیکونیک زیر ۱ ثانیه، شنیداری/پژواکی چند ثانیه)",
                            "حافظه کوتاه‌مدت (کاری): ظرفیت محدود (۷±۲ ماده)، مدت ۱۵ تا ۳۰ ثانیه، رمزگردانی غالباً شنیداری",
                            "افزایش ظرفیت حافظه کوتاه‌مدت با «قطعه‌بندی (تقطیع)» و افزایش مدت آن با «مرور ذهنی»",
                            "حافظه بلندمدت: ظرفیت و مدت نامحدود، رمزگردانی غالباً معنایی (مبتنی بر بسط)"
                        ),
                        goldenTechnique = "انتقال از حسی به کوتاه‌مدت = با «توجه» | انتقال از کوتاه‌مدت به بلندمدت = با «مرور و بسط معنایی»!",
                        commonMistakeTrap = "تصور این‌که قطعه‌بندی مدت زمان حافظه کوتاه‌مدت را زیاد می‌کند (قطعه‌بندی ظرفیت را زیاد می‌کند، نه مدت را!).",
                        targetExamYield = "۲ الی ۳ تست بسیار پرتکرار کنکور",
                        targetLessonId = "psych_04_memory_forgetting_strategies"
                    ),
                    LearningStation(
                        id = "psy_p3_s3",
                        stationNumber = 9,
                        title = "انواع حافظه بلندمدت، علل فراموشی و روش پس‌ختام (PQ4R)",
                        subtitle = "حافظه رویدادی، معنایی و روندی + تداخل پیش‌گستر و پس‌گستر",
                        estimatedHours = 3,
                        targetTestCount = 35,
                        keyConcepts = listOf(
                            "حافظه آشکار (اخباری): رویدادی (خاطرات شخصی با زمان و مکان) و معنایی (دانش عمومی و فرمول‌ها)",
                            "حافظه ناآشکار (غیراخباری / روندی): مهارت‌های حرکتی مثل دوچرخه‌سواری و شنا",
                            "تداخل پیش‌گستر (مطلب قدیم مانع یادآوری مطلب جدید می‌شود) و پس‌گستر (مطلب جدید مانع یادآوری قدیم می‌شود)",
                            "۶ گام روش مطالعه پس‌ختام: پیش‌خوانی، سؤال، خواندن، تفکر، از حفظ گفتن، مرور"
                        ),
                        goldenTechnique = "اگر شماره تلفن «جدیدت» را به خاطر شماره «قدیمی» یادت نیاید = تداخل پیش‌گستر | اگر با یادگیری زبان فرانسه، انگلیسیِ قبلی یادت برود = تداخل پس‌گستر!",
                        commonMistakeTrap = "جابه‌جا زدن تداخل پیش‌گستر و پس‌گستر؛ همیشه به کلمه‌ای که فراموش شده نگاه کن: اگر جدید فراموش شده = پیش‌گستر!",
                        targetExamYield = "۲ تست تضمینی در کنکور سراسری",
                        targetLessonId = "psych_04_memory_forgetting_strategies"
                    )
                )
            ),
            CurriculumPhase(
                phaseNumber = 4,
                title = "مرحله ۴: تفکر، حل مسئله، تصمیم‌گیری، هیجان و سلامت روان",
                milestoneGoal = "تسلط بر مراحل حل مسئله، سوگیری‌های شناختی، سبک‌های اسناد، استرس و مکانیسم‌های دفاعی",
                badge = "فتح ۱۰۰٪ روان‌شناسی",
                estimatedTotalHours = 10,
                stations = listOf(
                    LearningStation(
                        id = "psy_p4_s1",
                        stationNumber = 10,
                        title = "تفکر، بازنمایی ذهنی، مفاهیم و مراحل حل مسئله",
                        subtitle = "الگوریتم در برابر میان‌برهای ذهنی (ابتکاری) و موانع حل مسئله",
                        estimatedHours = 3,
                        targetTestCount = 35,
                        keyConcepts = listOf(
                            "عناصر تفکر: تصویر ذهنی، مفهوم (طبیعی و مصنوعی) و گزاره",
                            "راهبرد حل مسئله: آزمایش و خطا، الگوریتم (تضمین جواب ولی زمان‌بر)، ابتکاری/میان‌بر (سریع ولی بدون تضمین) و بینش (ناگهانی)",
                            "موانع حل مسئله: «آمایه ذهنی» (اصرار بر روش‌های قبلی) و «تثبیت کارکردی» (دیدن فقط یک کاربرد متداول برای اشیاء)"
                        ),
                        goldenTechnique = "گیر کردن در روش حل قدیمی = آمایه ذهنی | ندیدن کاربرد جدید یک شیء (مثلاً استفاده از سکه به جای پیچ‌گوشتی) = تثبیت کارکردی!",
                        commonMistakeTrap = "اشتباه گرفتن آمایه ذهنی (مربوط به روش و راهبرد) با تثبیت کارکردی (مربوط به ابزار و شیء).",
                        targetExamYield = "۲ تست قطعی در دفترچه ۱ کنکور",
                        targetLessonId = "psych_11_ch3_thinking_problem_solving"
                    ),
                    LearningStation(
                        id = "psy_p4_s2",
                        stationNumber = 11,
                        title = "تصمیم‌گیری، سوگیری‌های شناختی و تفکر خلاق",
                        subtitle = "خطای دسترس‌پذیری، نماگری، چارچوب‌بندی، سوگیری تأیید و تفکر واگرا",
                        estimatedHours = 2,
                        targetTestCount = 30,
                        keyConcepts = listOf(
                            "سوگیری تأیید: جستجوی شواهد موافق با باور قبلی و نادیده گرفتن شواهد مخالف",
                            "میان‌بر دسترس‌پذیری: قضاوت بر اساس اولین خاطره‌ای که به ذهن می‌آید (مثل ترس از هواپیما پس از دیدن خبر سقوط)",
                            "خطای چارچوب‌بندی: تغییر تصمیم بر اثر نحوه بیان (۹۰٪ بقا در برابر ۱۰٪ مرگ)",
                            "عناصر تفکر خلاق (واگرا): سیالی (تعداد ایده‌ها)، انعطاف‌پذیری (تنوع طبقات)، ابتکار (نو بودن) و بسط (جزئیات)"
                        ),
                        goldenTechnique = "تعداد زیاد پاسخ = سیالی | تنوع دسته‌ها = انعطاف‌پذیری | غیرمعمول و نادر بودن = ابتکار (اصالت)!",
                        commonMistakeTrap = "اشتباه گرفتن «سیالی» (کمیت ایده‌ها) با «انعطاف‌پذیری» (تغییر زاویه دید و تنوع مقوله‌ها).",
                        targetExamYield = "۱ الی ۲ تست بسیار پرتکرار",
                        targetLessonId = "psych_11_ch3_thinking_problem_solving"
                    ),
                    LearningStation(
                        id = "psy_p4_s3",
                        stationNumber = 12,
                        title = "انگیزش، هیجان و سبک‌های اسناد (واینر و سلیگمن)",
                        subtitle = "انگیزش درونی و بیرونی، ابعاد اسناد (درونی/بیرونی، پایدار/ناپایدار، کلی/اختصاصی)",
                        estimatedHours = 2,
                        targetTestCount = 30,
                        keyConcepts = listOf(
                            "انگیزش درونی (انجام کار به خاطر لذت خود فعالیت) در برابر بیرونی (پاداش و نمره)",
                            "اثر توجیه بیش از حد: کاهش انگیزش درونی بر اثر دادن پاداش بیرونی بی‌مورد",
                            "سبک اسناد خوش‌بینانه در شکست: بیرونی، ناپایدار و اختصاصی",
                            "سبک اسناد بدبینانه در شکست (درماندگی آموخته‌شده): درونی، پایدار و کلی («من کلاً در همه چیز بی‌استعدادم»)"
                        ),
                        goldenTechnique = "جمله «سوالات این آزمونِ ریاضی سخت بود»: اسناد بیرونی (سوالات)، ناپایدار (این آزمون) و اختصاصی (ریاضی)!",
                        commonMistakeTrap = "تشخیص نادرست بعد «پایدار/ناپایدار» از «کلی/اختصاصی» در جملات تستی.",
                        targetExamYield = "۱ الی ۲ تست ثابت کنکور انسانی",
                        targetLessonId = "psych_11_ch4_stress_mental_health"
                    ),
                    LearningStation(
                        id = "psy_p4_s4",
                        stationNumber = 13,
                        title = "استرس (فشار روانی)، مقابله مسئله‌مدار و هیجان‌مدار و سلامت روان",
                        subtitle = "ناکامی، تعارض‌ها (گرایش-گرایش، اجتناب-اجتناب، گرایش-اجتناب) و مکانیسم‌های دفاعی",
                        estimatedHours = 2,
                        targetTestCount = 30,
                        keyConcepts = listOf(
                            "انواع تعارض: گرایش-گرایش (دو گزینه جذاب)، اجتناب-اجتناب (دو گزینه ناخوشایند)، گرایش-اجتناب (یک گزینه با جنبه مثبت و منفی هم‌زمان)",
                            "مقابله مسئله‌مدار (تغییر موقعیت یا حل علت استرس) در برابر مقابله هیجان‌مدار (آرام‌سازی احساسات در موقعیت‌های غیرقابل کنترل)",
                            "مکانیسم‌های دفاعی: سرکوب، انکار، فرافکنی، دلیل‌تراشی، جابه‌جایی، واکنش‌سازی، بازگشت و والایش"
                        ),
                        goldenTechnique = "نسبت دادن عیب خود به دیگران = فرافکنی | خالی کردن خشم رئیس سر خانواده = جابه‌جایی | بهانه‌آوری منطقی برای شکست = دلیل‌تراشی!",
                        commonMistakeTrap = "تصور این‌که مقابله هیجان‌مدار همیشه بد است (در موقعیت‌های غیرقابل تغییر مثل سوگ، هیجان‌مدار ضروری است).",
                        targetExamYield = "۲ تست قطعی و بسیار ساده در کنکور",
                        targetLessonId = "psych_11_ch4_stress_mental_health"
                    ),
                    LearningStation(
                        id = "psy_p4_s5",
                        stationNumber = 14,
                        title = "شبیه‌ساز جامع ۱۵ سوالی روان‌شناسی کنکور ۱۴۰۶ در ۱۵ دقیقه",
                        subtitle = "مرور سریع جدول دانشمندان و حل ۱۵ تست استاندارد سنجش",
                        estimatedHours = 1,
                        targetTestCount = 30,
                        keyConcepts = listOf(
                            "مدیریت زمان ۶۰ ثانیه برای هر سوال",
                            "پاسخ‌گویی به ۱۵ سوال در کمتر از ۱۰ دقیقه برای ذخیره زمان دفترچه ۱"
                        ),
                        goldenTechnique = "روان‌شناسی سریع‌ترین درس دفترچه ۱ پس از جامعه‌شناسی است؛ با تسلط بر کلیدواژه‌ها در ۸ دقیقه ۱۰۰٪ زده می‌شود.",
                        commonMistakeTrap = "بی‌توجهی به فعل آخر صورت سوال (می‌تواند / نمی‌تواند).",
                        targetExamYield = "تضمین درصد ۹۰ تا ۱۰۰٪ در روان‌شناسی کنکور ۱۴۰۶"
                    )
                )
            )
        )
    )

    // ۵. جامعه‌شناسی جامع کنکور ۱۴۰۶ (دهم، یازدهم و دوازدهم)
    val sociologyPath = StagedSubjectPath(
        subjectId = "sociology",
        subjectName = "جامعه‌شناسی جامع کنکور ۱۴۰۶ (دهم، یازدهم و دوازدهم)",
        totalPhasesCount = 4,
        totalStationsCount = 16,
        konkoorWeightInfo = "ضریب ۵ کنکور سراسری انسانی ۱۴۰۶ • علوم اجتماعی + امتحان نهایی دوازدهم",
        themeColor = SubjectMathColor,
        badgeTag = "شاهراه درصد ۱۰۰ دفترچه اول و نمره ۲۰ نهایی",
        iconDescription = "کنش اجتماعی، فرهنگ و نظام جهانی",
        summaryDescription = "مسیر ۴ مرحله‌ای تسلط بر جامعه‌شناسی ۱، ۲ و ۳: کنش اجتماعی، جهان اجتماعی، فرهنگ، هویت و مکاتب جامعه‌شناسی (تبیینی، تفسیری، انتقادی)",
        phases = listOf(
            CurriculumPhase(
                phaseNumber = 1,
                title = "مرحله ۱: جامعه‌شناسی دهم — کنش اجتماعی، پدیده‌ها و جهان اجتماعی",
                milestoneGoal = "تفکیک دقیق کنش انسانی از رفتار زیستی، ویژگی‌های کنش اجتماعی و لایه‌های جهان اجتماعی",
                badge = "بنیان‌های جامعه‌شناسی ۱",
                estimatedTotalHours = 10,
                stations = listOf(
                    LearningStation(
                        id = "soc_p1_s1",
                        stationNumber = 1,
                        title = "کنش انسانی و ۴ ویژگی بنیادین آن در برابر رفتار زیستی",
                        subtitle = "آگاهانه، ارادی، هدفدار و معنادار بودن کنش و پیامدهای ارادی و غیرارادی",
                        estimatedHours = 2,
                        targetTestCount = 30,
                        keyConcepts = listOf(
                            "ویژگی‌های کنش: ۱. آگاهی ۲. اراده (وابسته به قدرت و خواست) ۳. هدفداری ۴. معناداری",
                            "تفاوت کنش (مثل مطالعه کردن، روزه گرفتن) با فعالیت‌های غیرارادی و غریزی (مثل پلک زدن، گرسنگی، تپش قلب)",
                            "کنش اجتماعی: کنشی که با توجه به دیگران و معطوف به دیگران انجام می‌شود"
                        ),
                        goldenTechnique = "اگر کاری بدون اراده و آگاهی رخ دهد (مثل تنفس در خواب یا لرزش دست از سرما) = کنش نیست؛ اگر با توجه به دیگران باشد = کنش اجتماعی!",
                        commonMistakeTrap = "پندار این‌که کنش اجتماعی حتماً نیاز به حضور فیزیکی دیگران دارد (امام جماعت که در تنهایی تمرین قرائت برای نماز جماعت می‌کند، کنش اجتماعی انجام می‌دهد).",
                        targetExamYield = "۲ تست قطعی در کنکور سراسری",
                        targetLessonId = "soc_10_ch1_human_social_action"
                    ),
                    LearningStation(
                        id = "soc_p1_s2",
                        stationNumber = 2,
                        title = "پدیده‌های اجتماعی: هنجار، ارزش، نماد، نقش و نهاد اجتماعی",
                        subtitle = "سلسله‌مراتب مفاهیم از کنش تا نهاد و کلانشهر جهان اجتماعی",
                        estimatedHours = 3,
                        targetTestCount = 35,
                        keyConcepts = listOf(
                            "ارزش اجتماعی: خواسته‌ها و آرمان‌های مطلوب و معتبر در جامعه (مثل عدالت، احترام به والدین)",
                            "هنجار اجتماعی: شیوه و قاعده مشخص عمل برای تحقق ارزش‌ها (مثل سلام کردن، ایستادن پشت چراغ قرمز)",
                            "نماد (سمبل): پدیده‌ای که معنای خاصی را به صورت قراردادی نشان می‌دهد (مثل پرچم، لباس احرام، زبان)",
                            "نقش اجتماعی (حقوق و تکالیف متناسب با موقعیت) و نهاد اجتماعی (مجموعه هنجارها و نقش‌ها برای رفع نیاز پایدار مثل خانواده، آموزش)"
                        ),
                        goldenTechnique = "«مطلوب بودنِ علم» = ارزش | «تقلب نکردن در امتحان» = هنجار | «مدرک تحصیلی» = نماد | «دانش‌آموز بودن» = نقش | «مدرسه و دانشگاه» = نهاد!",
                        commonMistakeTrap = "اشتباه گرفتن «ارزش» (کلی و آرمانی) با «هنجار» (قاعده ملموس رفتاری).",
                        targetExamYield = "۲ الی ۳ تست حتمی در کنکور انسانی",
                        targetLessonId = "soc1-02"
                    ),
                    LearningStation(
                        id = "soc_p1_s3",
                        stationNumber = 3,
                        title = "جهان اجتماعی، لایه‌های آن (سطحی و عمیق) و بسط و انقباض",
                        subtitle = "تکوینی یا اعتباری بودن پدیده‌ها و مرزهای جهان اجتماعی و طبیعت",
                        estimatedHours = 3,
                        targetTestCount = 30,
                        keyConcepts = listOf(
                            "پدیده‌های تکوینی (مستقل از اراده و قرارداد انسان مثل کوه، باران، مرگ) در برابر اعتباری (وابسته به خواست و قرارداد انسان‌ها مثل پول، قانون، مالکیت)",
                            "لایه عمیق جهان اجتماعی: عقاید و ارزش‌های بنیادین (تغییر کند و دشوار)",
                            "لایه سطحی جهان اجتماعی: هنجارها، نمادها و فناوری‌ها (تغییر سریع‌تر)",
                            "بسط جهان اجتماعی (تبدیل پدیده‌های طبیعی به اجتماعی مثل کشاورزی) و انقباض آن"
                        ),
                        goldenTechnique = "عقاید و ارزش‌ها = لایه عمیق و کانون فرهنگ | هنجارها و نمادها = لایه سطحی!",
                        commonMistakeTrap = "تکوینی پنداشتنِ «مرز کشورها» یا «ارزش اسکناس» (هر دو اعتباری هستند).",
                        targetExamYield = "۲ تست پرتکرار از جامعه‌شناسی دهم",
                        targetLessonId = "soc1-04"
                    ),
                    LearningStation(
                        id = "soc_p1_s4",
                        stationNumber = 4,
                        title = "هویت فردی و اجتماعی، جامعه‌پذیری، کج‌روی و کنترل اجتماعی",
                        subtitle = "ابعاد انتسابی و اکتسابی هویت، تحرک اجتماعی و بحران هویت (ازخودبیگانگی)",
                        estimatedHours = 2,
                        targetTestCount = 30,
                        keyConcepts = listOf(
                            "ویژگی‌های انتسابی هویت (بدون اراده فرد در بدو تولد مثل جنسیت، قومیت) و اکتسابی (با تلاش و انتخاب فرد مثل شغل، تحصیلات)",
                            "جامعه‌پذیری (درونی کردن فرهنگ) و کنترل اجتماعی (رسمی با قانون و غیررسمی با عرف و خانواده)",
                            "تحرک اجتماعی: صعودی/نزولی (عمودی) و افقی (تغییر موقعیت هم‌سطح)",
                            "ازخودبیگانگی (الیناسیون): فراموشی هویت اصیل و تقلید بی‌چون‌وچرا از فرهنگ بیگانه"
                        ),
                        goldenTechnique = "مؤثرترین و کم‌هزینه‌ترین راه حفظ نظم جامعه «جامعه‌پذیری و کنترل درونی» است، نه صرفاً مجازات بیرونی!",
                        commonMistakeTrap = "اشتباه گرفتن تحرک اجتماعی افقی (تغییر شغل بدون تغییر طبقه و منزلت) با تحرک عمودی.",
                        targetExamYield = "۲ تست قطعی در دفترچه ۱",
                        targetLessonId = "soc_01b_grade10_identity_mobility_control"
                    )
                )
            ),
            CurriculumPhase(
                phaseNumber = 2,
                title = "مرحله ۲: جامعه‌شناسی یازدهم — فرهنگ، نظام اجتماعی و تحولات تاریخی غرب",
                milestoneGoal = "تسلط بر حق و باطل در فرهنگ، فرهنگ آرمانی و واقعی و گذار غرب از قرون وسطی به مدرنیته",
                badge = "کالبدشکافی فرهنگ و غرب",
                estimatedTotalHours = 10,
                stations = listOf(
                    LearningStation(
                        id = "soc_p2_s1",
                        stationNumber = 5,
                        title = "فرهنگ، خرده‌فرهنگ، ضدفرهنگ و فرهنگ آرمانی و واقعی",
                        subtitle = "ارزیابی علمی فرهنگ‌ها بر اساس معیار جهان‌شمول فطرت و عقل (حق و باطل)",
                        estimatedHours = 3,
                        targetTestCount = 35,
                        keyConcepts = listOf(
                            "فرهنگ عمومی در برابر خرده‌فرهنگ (موافق با کلیت فرهنگ مثل خرده‌فرهنگ پزشکان یا عشایر) و ضدفرهنگ (مخالف با ارزش‌های اصلی مثل باندهای بزهکار)",
                            "فرهنگ آرمانی (آنچه جامعه به آن معتقد است) و فرهنگ واقعی (آنچه عملاً در رفتار مردم دیده می‌شود)",
                            "فرهنگ حق (مطابق با فطرت، عقل و وحی) در برابر فرهنگ باطل (مبتنی بر خرافات، ظلم و جهل)",
                            "نقد نسبیت‌گرایی فرهنگی و قوم‌مداری"
                        ),
                        goldenTechnique = "خرده‌فرهنگِ مخالف با اصول جامعه = ضدفرهنگ | فاصله بین ادعا و عمل مردم = شکاف فرهنگ آرمانی و واقعی!",
                        commonMistakeTrap = "یکسان دانستن «هرگونه تفاوت فرهنگی» با «ضدفرهنگ» (تفاوت شغلی و محلی خرده‌فرهنگ مثبت است).",
                        targetExamYield = "۲ تست ثابت در کنکور سراسری"
                    ),
                    LearningStation(
                        id = "soc_p2_s2",
                        stationNumber = 6,
                        title = "نظام جهانی، استعمار (کهن، نو، فرانو) و جهانی‌شدن در برابر جهانی‌سازی",
                        subtitle = "تعامل فرهنگی (تبادل، تقلید، گزینش) و بحران‌های فرهنگ مدرن غرب",
                        estimatedHours = 3,
                        targetTestCount = 30,
                        keyConcepts = listOf(
                            "استعمار کهن (اشغال نظامی مستقیم)، استعمار نو (وابستگی اقتصادی و سیاسی با دولت‌های دست‌نشانده) و استعمار فرانو (تسلط فرهنگی، رسانه‌ای و علمی)",
                            "تفاوت جهانی‌سازی (پروژه استعماری تحمیل فرهنگ غرب) با جهانی‌شدن و آرمان جامعه جهانی موعود (مبتنی بر قسط و عدل)",
                            "انواع مواجهه فرهنگی: خودباختگی (تقلید)، جمود (انزوا) و گزینش خلاقانه و فعال"
                        ),
                        goldenTechnique = "تسلط از راه سینما، اینترنت و تغییر سبک زندگی بدون لشکرکشی نظامی = استعمار فرانو (امپریالیسم فرهنگی)!",
                        commonMistakeTrap = "اشتباه گرفتن مفهوم استعمار نو (سیاسی-اقتصادی) با استعمار فرانو (فرهنگی-رسانه‌ای).",
                        targetExamYield = "۱ الی ۲ تست قطعی",
                        targetLessonId = "soc2-08"
                    ),
                    LearningStation(
                        id = "soc_p2_s3",
                        stationNumber = 7,
                        title = "تبارشناسی فرهنگ غرب: از یونان و قرون وسطی تا رنسانس و روشنگری",
                        subtitle = "اصول چهارگانه فرهنگ غرب مدرن: سکولاریسم، اومانیسم، علم‌گرایی تجربی و راسیونالیسم",
                        estimatedHours = 4,
                        targetTestCount = 40,
                        keyConcepts = listOf(
                            "قرون وسطی: ترکیب مسیحیت تحریف‌شده کلیسایی با نظام فئودالیسم و مخالفت کلیسا با علم",
                            "رنسانس و پروتستانتیسم: بازگشت به انسان‌محوری یونان و دنیاگرایی",
                            "عصر روشنگری: اومانیسم (اصالت انسان دنیوی)، سکولاریسم (حذف دین از سیاست و جامعه)، پوزیتیویسم (انحصار علم در حس و تجربه)",
                            "بحران‌های قرن ۲۰ و ۲۱ غرب: بحران معنا، بحران محیط‌زیست، بحران خانواده و ظهور پست‌مدرنیسم (نسبیت مطلق)"
                        ),
                        goldenTechnique = "دنیاگرایی + انسان‌محوری غیردینی (اومانیسم) + حس‌گرایی (پوزیتیویسم) = ستون‌های فرهنگ مدرن غرب!",
                        commonMistakeTrap = "نسبت دادن «انکار کامل حس و تجربه» به عصر روشنگری (عصر روشنگری برعکس، وحی را کنار گذاشت و به حس و عقل ابزاری بسنده کرد).",
                        targetExamYield = "۲ الی ۳ تست بسیار مهم از یازدهم",
                        targetLessonId = "soc2-06"
                    )
                )
            ),
            CurriculumPhase(
                phaseNumber = 3,
                title = "مرحله ۳: جامعه‌شناسی دوازدهم (بخش اول) — سه رویکرد بزرگ تبیینی، تفسیری و انتقادی",
                milestoneGoal = "تفکیک ۱۰۰٪ دیدگاه‌های آگوست کنت و دورکیم (تبیینی)، ماکس وبر (تفسیری) و مارکس و فرانکفورت (انتقادی)",
                badge = "شاه‌کلید جامعه‌شناسی ۳ و نهایی",
                estimatedTotalHours = 12,
                stations = listOf(
                    LearningStation(
                        id = "soc_p3_s1",
                        stationNumber = 8,
                        title = "رویکرد اول: جامعه‌شناسی تبیینی (پوزیتیویستی / اثبات‌گرایی)",
                        subtitle = "آگوست کنت و امیل دورکیم — مطالعه پدیده‌های اجتماعی به مثابه اشیاء طبیعی با روش کمی",
                        estimatedHours = 3,
                        targetTestCount = 45,
                        keyConcepts = listOf(
                            "هستی‌شناسی: جهان اجتماعی بخشی از طبیعت است و قوانین ثابتی مثل فیزیک دارد (نادیده گرفتن معنا و اراده)",
                            "معرفت‌شناسی: علم محدود به حس و تجربه و آمار (روش کمّی و تبیین علی بیرونی)",
                            "دیدگاه به ارزش‌ها: «داوری ارزشی ممنوع!»؛ علم قادر به تشخیص حق و باطلِ ارزش‌ها نیست و صرفاً کارکرد آن‌ها را بررسی می‌کند",
                            "هدف: کشف قوانین برای پیش‌بینی، کنترل و حفظ نظم موجود سرمایه‌داری (محافظه‌کار)"
                        ),
                        goldenTechnique = "دیدن کلمات «روش کمّی، مشابهت با علوم طبیعی، واقعیت اجتماعی بیرون از فرد، حفظ نظم موجود، کارکردگرایی» = ۱۰۰٪ جامعه‌شناسی تبیینی!",
                        commonMistakeTrap = "نسبت دادن روش «تفهم و کشف معنای ذهنی کنشگر» به آگوست کنت یا دورکیم.",
                        targetExamYield = "۲ الی ۳ تست قطعی در کنکور و بارم ۴ نمره‌ای در امتحان نهایی دوازدهم",
                        targetLessonId = "soc_12_ch1_three_approaches_social_sciences"
                    ),
                    LearningStation(
                        id = "soc_p3_s2",
                        stationNumber = 9,
                        title = "رویکرد دوم: جامعه‌شناسی تفسیری (تفهمی)",
                        subtitle = "ماکس وبر و ویلهم دیلتای — تمرکز بر معنای ذهنی کنشگران و روش کیفی",
                        estimatedHours = 3,
                        targetTestCount = 45,
                        keyConcepts = listOf(
                            "هستی‌شناسی: تمایز بنیادین جهان اجتماعی (معنادار و فرهنگی) از جهان طبیعت (فاقد معنا)",
                            "معرفت‌شناسی: روش «کیفی، تفهمی و توصیف عمیق» برای کشف معانی ذهنی کنشگران از درون فرهنگ خودشان",
                            "دیدگاه به ارزش‌ها: نسبیت‌گرایی فرهنگی؛ همه فرهنگ‌ها محترم‌اند اما هیچ معیاری برای برتری یا حق و باطل بودن یک فرهنگ وجود ندارد",
                            "قفس آهنین ماکس وبر: اسارت انسان مدرن در دیوان‌سالاری (بوروکراسی) و عقلانیت ابزاری"
                        ),
                        goldenTechnique = "دیدن کلمات «معنای ذهنی، روش کیفی، تفهم، همدلی، قفس آهنین، نسبیت فرهنگی» = ۱۰۰٪ جامعه‌شناسی تفسیری (وبر)!",
                        commonMistakeTrap = "تصور این‌که جامعه‌شناسی تفسیری می‌تواند حق و باطل فرهنگ‌ها را داوری کند (تفسیری ارزش‌ها را می‌فهمد ولی داوری نمی‌کند!).",
                        targetExamYield = "۲ الی ۳ تست قطعی در کنکور و امتحان نهایی",
                        targetLessonId = "soc_12_ch1_three_approaches_social_sciences"
                    ),
                    LearningStation(
                        id = "soc_p3_s3",
                        stationNumber = 10,
                        title = "رویکرد سوم: جامعه‌شناسی انتقادی (مارکس و مکتب فرانکفورت)",
                        subtitle = "نقد سلطه، استثمار، صنعت فرهنگ و پیوند علم با ارزش عدالت و رهایی",
                        estimatedHours = 3,
                        targetTestCount = 45,
                        keyConcepts = listOf(
                            "هستی‌شناسی: جهان اجتماعی میدان تضاد منافع، سلطه طبقه حاکم و استثمار است",
                            "معرفت‌شناسی: تلفیق روش کمی و کیفی برای «افشای ایدئولوژی‌های سلطه و آگاهی کاذب»",
                            "دیدگاه به ارزش‌ها: علم نمی‌تواند بی‌طرف باشد! جامعه‌شناس باید به نفع مظلومان و ارزش «رهایی و عدالت» موضع بگیرد",
                            "صنعت فرهنگ (مکتب فرانکفورت): استفاده سرمایه‌داری از رسانه، سینما و مصرف‌گرایی برای تخدیر و انفعال توده‌ها"
                        ),
                        goldenTechnique = "دیدن کلمات «سلطه، رهایی، استثمار، آگاهی کاذب، صنعت فرهنگ، ردّ بی‌طرفی ارزشی» = ۱۰۰٪ جامعه‌شناسی انتقادی!",
                        commonMistakeTrap = "یکی دانستن دیدگاه کارل مارکس (تأکید صرف بر اقتصاد به عنوان زیربنا) با مکتب فرانکفورت (تأکید بر نقش مستقل فرهنگ و رسانه در تداوم سلطه).",
                        targetExamYield = "۲ الی ۳ تست قطعی کنکور و امتحان نهایی",
                        targetLessonId = "soc_12_ch1_three_approaches_social_sciences"
                    ),
                    LearningStation(
                        id = "soc_p3_s4",
                        stationNumber = 11,
                        title = "جدول مقایسه‌ای چهاروجهی ۳ رویکرد + جامعه‌شناسی اسلامی",
                        subtitle = "مقایسه در چیستی جهان اجتماعی، روش شناخت، نسبت با ارزش‌ها و هدف نهایی",
                        estimatedHours = 3,
                        targetTestCount = 40,
                        keyConcepts = listOf(
                            "تبیینی: کمّی + تبیین علی + بی‌طرفی ارزشی + کنترل و نظم",
                            "تفسیری: کیفی + تفهم معنایی + نسبیت ارزشی + فهم متقابل و تعامل",
                            "انتقادی: کمی و کیفی + نقد سلطه + دفاع از ارزش عدالت بدون پشتوانه وحی + رهایی و تغییر",
                            "علوم اجتماعی اسلامی: بهره‌گیری از حس، عقل (برهان) و وحی (نقل)؛ شناخت علمی ارزش‌های حق و باطل بر پایه فطرت و توحید"
                        ),
                        goldenTechnique = "تنها رویکردی که برای ارزش‌های اخلاقی «پشتوانه عقلی و وحیانی» دارد و گرفتار نسبیت نمی‌شود = علوم اجتماعی اسلامی!",
                        commonMistakeTrap = "تصور این‌که جامعه‌شناسی انتقادی توانسته با ابزار حس و تجربه، عدالت را اثبات کند (این تناقض درونی رویکرد انتقادی است).",
                        targetExamYield = "۳ تست ترکیبی فوق‌العاده مهم در کنکور ۱۴۰۶",
                        targetLessonId = "soc_12_ch1_three_approaches_social_sciences"
                    )
                )
            ),
            CurriculumPhase(
                phaseNumber = 4,
                title = "مرحله ۴: جامعه‌شناسی دوازدهم (بخش دوم) — تضاد، نظم، خانواده و انقلاب اسلامی",
                milestoneGoal = "تسلط بر ۴ سطح نظم اجتماعی، نظریه‌های آسیب‌های خانواده و بازتاب انقلاب اسلامی",
                badge = "تثبیت نمره ۲۰ نهایی و ۱۰۰ کنکور",
                estimatedTotalHours = 10,
                stations = listOf(
                    LearningStation(
                        id = "soc_p4_s1",
                        stationNumber = 12,
                        title = "نظم اجتماعی، تضاد اجتماعی و ۴ گونه نظم (تکوینی، طبیعی، قاهره، حق)",
                        subtitle = "تحلیل نظم از دیدگاه تبیینی، تفسیری، انتقادی و اسلامی",
                        estimatedHours = 3,
                        targetTestCount = 35,
                        keyConcepts = listOf(
                            "دیدگاه کارکردگرایی (تبیینی) به تضاد: تضاد یک بیماری و اختلال موقت است که باید حذف شود",
                            "دیدگاه انتقادی به تضاد: تضاد ذات جامعه طبقاتی و موتور محرک تغییر تاریخی است",
                            "تضاد در نگاه اسلامی: تقابل دائمی حق و باطل در تاریخ با پیروزی نهایی جبهه حق (سنت الهی)",
                            "نظم قاهره (مبتنی بر زور و استبداد)، نظم وفاقی مدرن و نظم عادلانه توحیدی"
                        ),
                        goldenTechnique = "کارکردگرایی = اولویت نظم و نفی تضاد | انتقادی = اولویت تضاد و تغییر | اسلامی = نظم بر پایه حق و عدالت!",
                        commonMistakeTrap = "یکسان گرفتن «هرگونه نظم اجتماعی» با «عدالت اجتماعی» (بسیاری از جوامع استبدادی نظم آهنین دارند اما ظالمانه‌اند).",
                        targetExamYield = "۲ تست در کنکور و نهایی دوازدهم",
                        targetLessonId = "soc3-03"
                    ),
                    LearningStation(
                        id = "soc_p4_s2",
                        stationNumber = 13,
                        title = "نهاد خانواده از منظر سه رویکرد و چالش‌های خانواده معاصر",
                        subtitle = "کارکردهای خانواده، فردگرایی مدرن و جایگاه قدسی خانواده در اسلام",
                        estimatedHours = 2,
                        targetTestCount = 30,
                        keyConcepts = listOf(
                            "رویکرد تبیینی به خانواده: تمرکز بر کارکردهای نظم‌بخش (تولید مثل، جامعه‌پذیری، حمایت عاطفی) و تقسیم نقش ابزاری (پدر) و عاطفی (مادر)",
                            "رویکرد تفسیری: خانواده عرصه ساخت معنای مشترک و گفت‌وگوی صمیمانه",
                            "رویکرد انتقادی و فمینیسم: نگاهی بدبینانه به خانواده سنتی به مثابه نهاد سلطه مردسالارانه و بهره‌کشی اقتصادی",
                            "نگاه اسلام: خانواده بنیادی‌ترین نهاد فطری با پیوند مودت و رحمت و مکمل بودن زن و مرد"
                        ),
                        goldenTechnique = "تقسیم نقش «ابزاری پدر و عاطفی مادر» (پارسونز) = رویکرد تبیینی (کارکردگرایی)!",
                        commonMistakeTrap = "نسبت دادن دیدگاه «خانواده به عنوان نهاد سلطه و نابرابری» به رویکرد تبیینی (این دیدگاه متعلق به رویکرد انتقادی است).",
                        targetExamYield = "۱ الی ۲ تست ثابت در کنکور سراسری و نهایی",
                        targetLessonId = "soc_12_ch2_social_order_family_policy"
                    ),
                    LearningStation(
                        id = "soc_p4_s3",
                        stationNumber = 14,
                        title = "هویت، اوقات فراغت، مصرف‌گرایی و فضای مجازی در جهان معاصر",
                        subtitle = "صنعت فرهنگ، توده‌ای شدن و بازتولید هویت در سبک زندگی",
                        estimatedHours = 2,
                        targetTestCount = 25,
                        keyConcepts = listOf(
                            "تبدیل اوقات فراغت از فرصتی برای تعالی روحی به بازاری برای مصرف کالاهای فرهنگی سرمایه‌داری",
                            "هویت کاذب مصرفی: تعریف ارزش انسان با برند لباس و کالاهای لوکس",
                            "فرصت‌ها و تهدیدهای فضای مجازی برای هویت دینی و ملی"
                        ),
                        goldenTechnique = "کالایی شدن اوقات فراغت و استانداردسازی سلیقه‌ها توسط رسانه‌ها = مفهوم «صنعت فرهنگ» در مکتب فرانکفورت!",
                        commonMistakeTrap = "نادیده گرفتن نقش فعال کنشگران در مقاومت فرهنگی در برابر بمباران رسانه‌ای.",
                        targetExamYield = "۱ تست مفهومی از فصل‌های پایانی دوازدهم"
                    ),
                    LearningStation(
                        id = "soc_p4_s4",
                        stationNumber = 15,
                        title = "انقلاب اسلامی ایران و بیداری اسلامی از منظر جامعه‌شناسی",
                        subtitle = "ناتوانی نظریه‌های کلاسیک غربی در پیش‌بینی انقلاب اسلامی و بازگشت دین به عرصه عمومی",
                        estimatedHours = 2,
                        targetTestCount = 25,
                        keyConcepts = listOf(
                            "چرا نظریه‌های مدرن غربی از تبیین انقلاب ۵۷ عاجز ماندند؟ (به دلیل پیش‌فرض سکولاریسم و نادیده گرفتن نقش ایمان، مرجعیت دینی و فرهنگ شهادت)",
                            "ویژگی‌های متمایز انقلاب اسلامی: ماهیت فرهنگی-دینی، رهبری ولایی، مردمی بودن و شعار استقلال، آزادی، جمهوری اسلامی",
                            "فروپاشی دوقطبی شرق و غرب و الگوی مردم‌سالاری دینی"
                        ),
                        goldenTechnique = "مهم‌ترین علت غافلگیری جامعه‌شناسان غربی در قبال انقلاب ایران = پیش‌فرض ماتریالیستی و سکولار آن‌ها مبنی بر افول دین در جهان مدرن بود!",
                        commonMistakeTrap = "تحلیل انقلاب اسلامی صرفاً بر پایه عوامل اقتصادی و طبقاتی مارکسیستی.",
                        targetExamYield = "۱ الی ۲ سوال قطعی در امتحان نهایی خرداد و کنکور",
                        targetLessonId = "soc2-14"
                    ),
                    LearningStation(
                        id = "soc_p4_s5",
                        stationNumber = 16,
                        title = "شبیه‌ساز جامع ۲۰ سوالی جامعه‌شناسی کنکور ۱۴۰۶ در ۱۵ دقیقه",
                        subtitle = "حل ۲۰ تست ترکیبی دهم، یازدهم و دوازدهم در ۴۵ ثانیه به ازای هر سوال",
                        estimatedHours = 1,
                        targetTestCount = 40,
                        keyConcepts = listOf(
                            "مدیریت زمان ۴۵ ثانیه برای هر تست",
                            "شگرد تطبیق کلیدواژه‌های تبیینی، تفسیری و انتقادی در ۵ ثانیه"
                        ),
                        goldenTechnique = "بیش از ۴۰٪ سوالات جامعه‌شناسی کنکور مستقیماً با تشخیص ۳ رویکرد (تبیینی/تفسیری/انتقادی) و لایه‌های فرهنگ حل می‌شوند.",
                        commonMistakeTrap = "خلط کردن دیدگاه خودِ کتاب درسی (رویکرد اسلامی) با دیدگاه جامعه‌شناسی انتقادی در تست‌های چندگزاره‌ای.",
                        targetExamYield = "تضمین درصد بالای ۹۰٪ در جامعه‌شناسی کنکور ۱۴۰۶ و نمره ۲۰ امتحان نهایی"
                    )
                )
            )
        )
    )

    fun getAllStagedPaths(): List<StagedSubjectPath> = cachedJsonPaths ?: listOf(
        literatureAndFonoonPath,
        arabicPath,
        maarefAndDiniPath,
        psychologyPath,
        sociologyPath
    )

    fun getStagedPathBySubject(subjectId: String): StagedSubjectPath? {
        val paths = getAllStagedPaths()
        return when (subjectId) {
            "fonoon", "arooz" -> paths.find { it.subjectId == "fonoon" } ?: literatureAndFonoonPath
            "arabic" -> paths.find { it.subjectId == "arabic" } ?: arabicPath
            "dini", "farhangian" -> paths.find { it.subjectId == "dini" } ?: maarefAndDiniPath
            "psychology" -> paths.find { it.subjectId == "psychology" } ?: psychologyPath
            "sociology" -> paths.find { it.subjectId == "sociology" } ?: sociologyPath
            else -> paths.find { it.subjectId == subjectId }
        }
    }
}
