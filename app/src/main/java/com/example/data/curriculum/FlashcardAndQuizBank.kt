package com.example.data.curriculum

import com.example.ui.components.ArabicTextProcessor
import java.util.concurrent.ConcurrentHashMap

object FlashcardAndQuizBank {
    private val enrichedLessonCache = ConcurrentHashMap<String, ZeroLesson>()

    fun getEnrichedLesson(lesson: ZeroLesson): ZeroLesson {
        return enrichedLessonCache.getOrPut(lesson.id) {
            ArabicTextProcessor.enrichLessonContent(lesson)
        }
    }

    val allLessons: List<ZeroLesson> by lazy(LazyThreadSafetyMode.PUBLICATION) {
        buildList {
            addAll(ZeroToHundredMasterCurriculum1405.lessons)
            addAll(BismillahAndMnemonicsCurriculum.lessons)
            addAll(MathAndStatsCurriculum.lessons)
            addAll(MathStatsGapsCompletion1406.lessons)
            addAll(MathSequencesSplitCompletion1406.lessons)
            addAll(MathZeroPrereqCompletion1406.lessons)
            addAll(MathZeroOrderOpsCompletion1406.lessons)
            addAll(MathZeroProbFuncCompletion1406.lessons)
            // Arooz zero-to-mastery chain (rhythm-first) — must come before existing arooz lessons
            addAll(AroozZeroCompletion1406.lessons)
            addAll(AroozKhafifRadif1406.lessons)
            addAll(FonoonAndAroozCurriculum.aroozLessons)
            addAll(FonoonAndAroozCurriculum.fonoonLessons)
            addAll(ArabicAndEnglishCurriculum.arabicLessons)
            addAll(ArabicAndEnglishCurriculum.englishLessons)
            addAll(PhilosophyAndLogicCurriculum.lessons)
            addAll(FarhangianAndGeneralCurriculum.lessons)
            addAll(ExpandedHumanitiesCurriculum.lessons)
            addAll(CompleteTextbookChaptersCurriculum.lessons)
            addAll(MasteryEncyclopedia1405.lessons)
            addAll(GrandComprehensiveCurriculum1405.lessons)
            addAll(CentPercentMasteryCurriculum1406.lessons)
            addAll(CentPercentFullCurriculum1406.lessons)
            addAll(UltraFastTechniquesEncyclopedia1406.lessons)
            addAll(PsychologyCompletion1406.lessons)
            addAll(PsychologyDeepCompletion1406.lessons)
            addAll(PsychologyGapsCompletion1406.lessons)
            addAll(FarsiCompletion1406.lessons)
            addAll(Farsi3Part1Completion1406.lessons)
            addAll(Farsi3Part2Completion1406.lessons)
            addAll(Farsi3Part3Completion1406.lessons)
            addAll(Farsi1Part1Completion1406.lessons)
            addAll(Farsi1Part2Completion1406.lessons)
            addAll(Farsi1Part3Completion1406.lessons)
            addAll(Farsi1Part4Completion1406.lessons)
            addAll(Farsi1Part5Completion1406.lessons)
            addAll(Farsi2Part1Completion1406.lessons)
            addAll(Farsi2Part2Completion1406.lessons)
            addAll(Farsi2Part3Completion1406.lessons)
            addAll(Farsi2Part4Completion1406.lessons)
            addAll(Farsi2Part5Completion1406.lessons)
            addAll(FarhangianCompletion1406.lessons)
            addAll(FarhangianSourcesCompletion1406.lessons)
            addAll(EconomicsRebuildPart1Completion1406.lessons)
            addAll(EconomicsRebuildPart2Completion1406.lessons)
            addAll(EconomicsGaps1Completion1406.lessons)
            addAll(EconomicsGaps2Completion1406.lessons)
            addAll(EconomicsGaps3Completion1406.lessons)
            addAll(EconQuickMethodsCompletion1406.lessons)
            addAll(LogicCompletion1406.lessons)
            addAll(LogicGapsCompletion1406.lessons)
            addAll(Philosophy2Part1Completion1406.lessons)
            addAll(Philosophy2Part2Completion1406.lessons)
            addAll(PhilosophyGapsPart1Completion1406.lessons)
            addAll(PhilosophyGapsPart2Completion1406.lessons)
            addAll(Geography3Completion1406.lessons)
            addAll(HealthMediaEnvironmentCompletion1406.lessons)
            addAll(Arabic12Completion1406.lessons)
            addAll(Arabic5AndGdpCompletion1406.lessons)
            addAll(ArabicCompletion1406.lessons)
            addAll(ArabicGapsCompletion1406.lessons)
            addAll(EnglishCompletion1406.lessons)
            addAll(EnglishZeroFinalCompletion1406.lessons)
            addAll(EnglishZeroGrammarCompletion1406.lessons)
            addAll(StudyMethodsCompletion1406.lessons)
            addAll(StudyMethodsPlanningCompletion1406.lessons)
            addAll(SocialStudyTipsCompletion1406.lessons)
            addAll(AroozAndLogicMethods1406.lessons)
            addAll(NegareshCompletion1406.lessons)
            addAll(Negaresh12Part1Completion1406.lessons)
            addAll(Negaresh12Part2Completion1406.lessons)
            addAll(NegareshBaseSkillsPart1Completion1406.lessons)
            addAll(NegareshBaseSkillsPart2Completion1406.lessons)
            addAll(Negaresh3Completion1406.lessons)
            addAll(Sociology1Part1Completion1406.lessons)
            addAll(Sociology1Part2Completion1406.lessons)
            addAll(Sociology1Part3Completion1406.lessons)
            addAll(Sociology2Part1Completion1406.lessons)
            addAll(Sociology2Part2Completion1406.lessons)
            addAll(Sociology3Part1Completion1406.lessons)
            addAll(Sociology3Part2Completion1406.lessons)
            addAll(SociologyGapsCompletion1406.lessons)
            addAll(History1Part1Completion1406.lessons)
            addAll(History1Part2Completion1406.lessons)
            addAll(History1Part3Completion1406.lessons)
            addAll(History2Part1Completion1406.lessons)
            addAll(History2Part2Completion1406.lessons)
            addAll(History2Part3Completion1406.lessons)
            addAll(History3Part1Completion1406.lessons)
            addAll(History3Part2Completion1406.lessons)
            addAll(History3Part3Completion1406.lessons)
            addAll(HistGeoGaps2Completion1406.lessons)
            addAll(Dini1Part1Completion1406.lessons)
            addAll(Dini1Part2Completion1406.lessons)
            addAll(Dini1Part3Completion1406.lessons)
            addAll(Dini2Part1Completion1406.lessons)
            addAll(Dini2Part2Completion1406.lessons)
            addAll(Dini2Part3Completion1406.lessons)
            addAll(Dini2Part4Completion1406.lessons)
            addAll(Dini3Part1Completion1406.lessons)
            addAll(Dini3Part2Completion1406.lessons)
            addAll(Dini3Part3Completion1406.lessons)
            addAll(Geography1Part1Completion1406.lessons)
            addAll(Geography1Part2Completion1406.lessons)
            addAll(Geography2Part1Completion1406.lessons)
            addAll(Geography2Part2Completion1406.lessons)
            addAll(Geography2Part3Completion1406.lessons)
            addAll(Arooz12Completion1406.lessons)
            addAll(AroozFonoon2LessonsCompletion1406.lessons)
            addAll(AroozQuickMethodsCompletion1406.lessons)
            addAll(SocialArabicTipsCompletion1406.lessons)
            addAll(Fonoon12Completion1406.lessons)
            addAll(Fonoon3HistoryCompletion1406.lessons)
            addAll(Fonoon3MusicCompletion1406.lessons)
            addAll(Fonoon3FiguresCompletion1406.lessons)
            addAll(FonoonTamsilCompletion1406.lessons)
            addAll(FonoonTashkhisJinasCompletion1406.lessons)
            addAll(Fonoon1Lesson10Completion1406.lessons)
            addAll(FoundationsMathPersian1406.lessons)
            addAll(FoundationsArabicEnglish1406.lessons)
            addAll(ArabicZeroTrackCompletion1406.lessons)
            addAll(ArabicZeroRulesCompletion1406.lessons)
            addAll(FoundationsF1Completion1406.lessons)
            addAll(FarsiZeroGrammar1Completion1406.lessons)
            addAll(FarsiZeroGrammar2Completion1406.lessons)
            addAll(FarsiZeroGrammar3Completion1406.lessons)
            addAll(FarsiZeroGrammar4Completion1406.lessons)
            addAll(FarsiZeroGrammar5Completion1406.lessons)
            addAll(FarsiZeroGrammar6Completion1406.lessons)
            addAll(FarsiZeroGrammar7Completion1406.lessons)
            addAll(FarsiZeroGrammar8Completion1406.lessons)
            addAll(FarsiZeroGrammar9Completion1406.lessons)
            addAll(FoundationsF7Completion1406.lessons)
            addAll(FoundationsF8Completion1406.lessons)
            addAll(FoundationsF3Completion1406.lessons)
            addAll(FoundationsF4Completion1406.lessons)
            addAll(FoundationsF5Completion1406.lessons)
            addAll(FoundationsF6Completion1406.lessons)
            addAll(FoundationsF2Completion1406.lessons)
            addAll(FoundationsF9Completion1406.lessons)
            addAll(GeographyGapsCompletion1406.lessons)
            // Elementary Grade 2 (E7–E15)
            addAll(ElementaryE7Completion1406.lessons)
            addAll(ElementaryE8Completion1406.lessons)
            addAll(ElementaryE9Completion1406.lessons)
            addAll(ElementaryE10Completion1406.lessons)
            addAll(ElementaryE11Completion1406.lessons)
            addAll(ElementaryE12Completion1406.lessons)
            addAll(ElementaryE13Completion1406.lessons)
            addAll(ElementaryE14Completion1406.lessons)
            addAll(ElementaryE15Completion1406.lessons)
            // Elementary Grade 3 (E16–E25)
            addAll(ElementaryE16Completion1406.lessons)
            addAll(ElementaryE17Completion1406.lessons)
            addAll(ElementaryE18Completion1406.lessons)
            addAll(ElementaryE19Completion1406.lessons)
            addAll(ElementaryE20Completion1406.lessons)
            addAll(ElementaryE21Completion1406.lessons)
            addAll(ElementaryE22Completion1406.lessons)
            addAll(ElementaryE23Completion1406.lessons)
            addAll(ElementaryE24Completion1406.lessons)
            addAll(ElementaryE25Completion1406.lessons)
            // Elementary Grade 1 (E1–E6)
            addAll(ElementaryE1Completion1406.lessons)
            addAll(ElementaryE2Completion1406.lessons)
            addAll(ElementaryE3Completion1406.lessons)
            addAll(ElementaryE4Completion1406.lessons)
            addAll(ElementaryE5Completion1406.lessons)
            addAll(ElementaryE6Completion1406.lessons)
            // Elementary Grade 4 (E26–E36)
            addAll(ElementaryE26Completion1406.lessons)
            addAll(ElementaryE27Completion1406.lessons)
            addAll(ElementaryE28Completion1406.lessons)
            addAll(ElementaryE29Completion1406.lessons)
            addAll(ElementaryE30Completion1406.lessons)
            addAll(ElementaryE31Completion1406.lessons)
            addAll(ElementaryE32Completion1406.lessons)
            addAll(ElementaryE33Completion1406.lessons)
            addAll(ElementaryE34Completion1406.lessons)
            addAll(ElementaryE35Completion1406.lessons)
            addAll(ElementaryE36Completion1406.lessons)
            // Elementary Grade 5 (E37–E47)
            addAll(ElementaryE37Completion1406.lessons)
            addAll(ElementaryE38Completion1406.lessons)
            addAll(ElementaryE39Completion1406.lessons)
            addAll(ElementaryE40Completion1406.lessons)
            addAll(ElementaryE41Completion1406.lessons)
            addAll(ElementaryE42Completion1406.lessons)
            addAll(ElementaryE43Completion1406.lessons)
            addAll(ElementaryE44Completion1406.lessons)
            addAll(ElementaryE45Completion1406.lessons)
            addAll(ElementaryE46Completion1406.lessons)
            addAll(ElementaryE47Completion1406.lessons)
            // Middle Grade 9 (M34–M46)
            addAll(MiddleM34Completion1406.lessons)
            addAll(MiddleM35Completion1406.lessons)
            addAll(MiddleM36Completion1406.lessons)
            addAll(MiddleM37Completion1406.lessons)
            addAll(MiddleM38Completion1406.lessons)
            addAll(MiddleM39Completion1406.lessons)
            addAll(MiddleM40Completion1406.lessons)
            addAll(MiddleM41Completion1406.lessons)
            addAll(MiddleM42Completion1406.lessons)
            addAll(MiddleM43Completion1406.lessons)
            addAll(MiddleM44Completion1406.lessons)
            addAll(MiddleM45Completion1406.lessons)
            addAll(MiddleM46Completion1406.lessons)
            addAll(MiddleM46Part2Completion1406.lessons)
            addAll(MiddleM33Part2Completion1406.lessons)
            addAll(ArabicPracticeCompletion1406.lessons)
            addAll(DiniPracticeCompletion1406.lessons)
            addAll(EnglishGeneralPracticeCompletion1406.lessons)
            addAll(FarsiPracticeCompletion1406.lessons)
            addAll(FonoonPracticeCompletion1406.lessons)
            addAll(GeographyPracticeCompletion1406.lessons)
            addAll(HealthEnvironmentPracticeCompletion1406.lessons)
            addAll(HistoryPracticeCompletion1406.lessons)
            addAll(NegareshPracticeCompletion1406.lessons)
            addAll(PhilosophyLogicPracticeCompletion1406.lessons)
            addAll(PsychologyEconomicsPracticeCompletion1406.lessons)
            addAll(ElementaryGrade1PracticeCompletion1406.lessons)
            addAll(ElementaryGrade2PracticeCompletion1406.lessons)
            addAll(ElementaryGrade3PracticeCompletion1406.lessons)
            addAll(ElementaryGrade4PracticeCompletion1406.lessons)
            addAll(ElementaryGrade5PracticeCompletion1406.lessons)
            addAll(ElementaryGrade6PracticeCompletion1406.lessons)
            addAll(MiddleGrade7PracticeCompletion1406.lessons)
            addAll(MiddleGrade8PracticeCompletion1406.lessons)
            addAll(MiddleGrade9PracticeCompletion1406.lessons)
            // Middle Grade 8 (M18–M33)
            addAll(MiddleM18Completion1406.lessons)
            addAll(MiddleM19Completion1406.lessons)
            addAll(MiddleM20Completion1406.lessons)
            addAll(MiddleM21Completion1406.lessons)
            addAll(MiddleM22Completion1406.lessons)
            addAll(MiddleM23Completion1406.lessons)
            addAll(MiddleM24Completion1406.lessons)
            addAll(MiddleM25Completion1406.lessons)
            addAll(MiddleM26Completion1406.lessons)
            addAll(MiddleM27Completion1406.lessons)
            addAll(MiddleM28Completion1406.lessons)
            addAll(MiddleM29Completion1406.lessons)
            addAll(MiddleM30Completion1406.lessons)
            addAll(MiddleM31Completion1406.lessons)
            addAll(MiddleM32Completion1406.lessons)
            addAll(MiddleM33Completion1406.lessons)
            // Middle Grade 7 (M1–M17)
            addAll(MiddleM1Completion1406.lessons)
            addAll(MiddleM2Completion1406.lessons)
            addAll(MiddleM3Completion1406.lessons)
            addAll(MiddleM4Completion1406.lessons)
            addAll(MiddleM5Completion1406.lessons)
            addAll(MiddleM6Completion1406.lessons)
            addAll(MiddleM7Completion1406.lessons)
            addAll(MiddleM8Completion1406.lessons)
            addAll(MiddleM9Completion1406.lessons)
            addAll(MiddleM10Completion1406.lessons)
            addAll(MiddleM11Completion1406.lessons)
            addAll(MiddleM12Completion1406.lessons)
            addAll(MiddleM13Completion1406.lessons)
            addAll(MiddleM14Completion1406.lessons)
            addAll(MiddleM15Completion1406.lessons)
            addAll(MiddleM16Completion1406.lessons)
            addAll(MiddleM17Completion1406.lessons)
            // Elementary Grade 6 (E48–E58)
            addAll(ElementaryE48Completion1406.lessons)
            addAll(ElementaryE49Completion1406.lessons)
            addAll(ElementaryE50Completion1406.lessons)
            addAll(ElementaryE51Completion1406.lessons)
            addAll(ElementaryE52Completion1406.lessons)
            addAll(ElementaryE53Completion1406.lessons)
            addAll(ElementaryE54Completion1406.lessons)
            addAll(ElementaryE55Completion1406.lessons)
            addAll(ElementaryE56Completion1406.lessons)
            addAll(ElementaryE57Completion1406.lessons)
            addAll(ElementaryE58Completion1406.lessons)
            addAll(SocialHumanitiesTipsCompletion1406.lessons)
    addAll(SiteFreeResourcesCompletion1406.lessons)
    addAll(SiteGozinehMethodsCompletion1406.lessons)
    addAll(SiteKanoonMethodsCompletion1406.lessons)
    addAll(SiteKanoonPathCompletion1406.lessons)
    addAll(SiteSubjectMethods1Completion1406.lessons)
    addAll(SiteSubjectMethods2Completion1406.lessons)
    addAll(SiteTopperExperienceCompletion1406.lessons)
            addAll(GapKarafariniPart1Completion1406.lessons)
            addAll(GapKarafariniPart2Completion1406.lessons)
            addAll(GapKarafariniPart3Completion1406.lessons)
            addAll(GapKarafariniPart4Completion1406.lessons)
            addAll(GapFamilyBoysPart1Completion1406.lessons)
            addAll(GapFamilyBoysPart2Completion1406.lessons)
            addAll(GapFamilyBoysPart3Completion1406.lessons)
            addAll(GapResanehPart1Completion1406.lessons)
            addAll(GapResanehPart2Completion1406.lessons)
            addAll(GapResanehPart3Completion1406.lessons)
            addAll(GapArabicLesson8Completion1406.lessons)
            addAll(GapArabic12AndPsychologyCompletion1406.lessons.filter { it.id == "gap2-ar-tamyiz" || it.id == "gap2-ar-la-nafy-jins" })
            addAll(GapBatch2Completion1406.lessons.filter { it.id == "gap3-ar-esm-fael" || it.id == "gap3-ar-esm-makan" || it.id == "gap3-ar-esm-tafdil" || it.id == "gap3-ar-afal-naqesa" || it.id == "gap3-neg-sadeh" })
    addAll(GapFamilyGirlsCompletion1406.lessons)
        }
            .distinctBy { it.id }
            .map { it.copy(subjectId = it.normalizedSubjectId()) }
            // گام ۱ fix-plan: اشتقاق سلسله‌مراتب از folder، قبل از sort.
            .map { lesson ->
                val (folderNum, folderTitle) = lesson.subcategoryFolder()
                lesson.copy(
                    grade = lesson.hierarchyGrade(),
                    bookTitle = lesson.hierarchyBookTitle(),
                    chapterNumber = folderNum,
                    chapterTitle = folderTitle,
                    sectionType = lesson.hierarchySectionType(folderNum)
                    // sectionNumber در mapIndexed پایین = bookLessonNum پر می‌شود.
                )
            }
            .groupBy { it.subjectId }
            .values
            .flatMap { subjectLessons ->
                val sortedSubjectLessons = subjectLessons.sortedWith(
                    compareBy<ZeroLesson>(
                        { it.grade.number },
                        { it.chapterNumber },
                        { it.prerequisiteStepOrder() },
                        { it.orderIndex }
                    )
                )
                val perFolderCounter = mutableMapOf<Int, Int>()
                sortedSubjectLessons.mapIndexed { globalIdx, lesson ->
                    val folderGrade = lesson.subcategoryFolder().first
                    val bookLessonNum = (perFolderCounter[folderGrade] ?: 0) + 1
                    perFolderCounter[folderGrade] = bookLessonNum

                    val persianStep = com.example.ui.components.BidiFormatter.toPersianDigits(bookLessonNum.toString())
                    val cleanBadge = lesson.levelBadge
                        .replace(Regex("^(پله|درس)\\s*[\\d۰-۹]+\\s*(•|-)?\\s*"), "")
                        .trim()
                    val normalizedTitle = normalizeSequentialLessonTitle(lesson.title, bookLessonNum)
                        .replace('\u064A', '\u06CC')
                        .replace('\u0649', '\u06CC')
                        .replace('\u0643', '\u06A9')
                    val normalizedLesson = lesson.copy(
                        orderIndex = globalIdx + 1,
                        sectionNumber = bookLessonNum,
                        title = normalizedTitle,
                        levelBadge = "درس $persianStep • $cleanBadge",
                        steps = lesson.steps.map { step ->
                            step.copy(
                                title = step.title
                                    .replace('\u064A', '\u06CC')
                                    .replace('\u0649', '\u06CC')
                                    .replace('\u0643', '\u06A9'),
                                simpleExplanation = step.simpleExplanation
                                    .replace('\u064A', '\u06CC')
                                    .replace('\u0649', '\u06CC')
                                    .replace('\u0643', '\u06A9')
                            )
                        }
                    )
                    ArabicTextProcessor.enrichLessonContent(normalizedLesson)
                }
            }
    }

    val flashcards: List<FlashcardItem> by lazy(LazyThreadSafetyMode.PUBLICATION) { (listOf(
        // Math
        FlashcardItem(
            id = "fc_math_1",
            subjectId = "math",
            categoryTag = "معادله درجه ۲",
            frontQuestion = "اگر در معادله درجه دوم ax² + bx + c = 0 جمع ضرایب صفر شود (a + b + c = 0)، ریشه‌ها چه هستند؟",
            backAnswer = "به زبان ساده:\nیعنی اگر عدد پشت x² (آ) را با عدد پشت x (ب) و عدد تنهای آخر (ج) جمع کنی و جواب شود صفر (0):\n۱. ریشه اول همیشه عدد ۱ است (x = 1)\n۲. ریشه دوم همیشه عدد آخر تقسیم بر اول است (x = c / a)\nمثال: در 2x² - 9x + 7 = 0 چون (2 - 9 + 7 = 0)، پس ریشه‌ها ۱ و ۷/۲ هستند.",
            memoryHook = "جمع ضرایب شد صفر؟ فورا بگو: یکی ۱، یکی آخر به اول (c/a)!"
        ),
        FlashcardItem(
            id = "fc_math_2",
            subjectId = "math",
            categoryTag = "منطق و گزاره‌ها",
            frontQuestion = "گزاره شرطی (p ⟹ q) تنها در چه حالتی ارزش «نادرست (F)» دارد؟",
            backAnswer = "به زبان ساده:\nفقط در یک حالت نمره‌اش صفر (نادرست) می‌شود: وقتی مقدم (شرط) درست باشد اما تالی (نتیجه) دروغ از آب درآید!\nمثل اینکه به دوستت بگویی: «اگر باران ببارد، چتر می‌خارم» (باران بارید اما چتر نخریدی = زیر قول زدی و نادرست است).",
            memoryHook = "شرط برقرار شد اما به قولت عمل نکردی = زیر قول زدی (نادرست)!"
        ),
        FlashcardItem(
            id = "fc_math_3",
            subjectId = "math",
            categoryTag = "آمار و واریانس",
            frontQuestion = "اگر به همه داده‌های آماری ۵ واحد اضافه کنیم، واریانس و انحراف معیار چه تغییری می‌کنند؟",
            backAnswer = "به زبان ساده:\nهیچ تغییری نمی‌کنند!\nدلیل: واریانس یعنی «میزان فاصله و پراکندگی داده‌ها از هم». وقتی همه نمرات کلاس را با هم ۵ نمره بالا می‌بریم، فاصله بچه‌ها از همدیگر تغییر نمی‌کند.",
            memoryHook = "جابه‌جایی دسته‌جمعی داده‌ها به جلو یا عقب، پراکندگی را عوض نمی‌کند."
        ),
        FlashcardItem(
            id = "fc_math_4",
            subjectId = "math",
            categoryTag = "دنباله حسابی",
            frontQuestion = "چطور از روی دو جمله دلخواه در دنباله حسابی (مثلاً جمله ۳ و ۸) قدر نسبت d را پیدا کنیم؟",
            backAnswer = "به زبان ساده با فرمول:\nd = (اختلاف مقادیر دو جمله) تقسیم بر (اختلاف شماره‌های آن‌ها).\nیعنی مقدار جمله بزرگ‌تر منهای کوچک‌تر، تقسیم بر پله‌هایی که طی شده.",
            memoryHook = "اختلاف عددها تقسیم بر تعداد قدم‌ها!"
        ),
        // Arooz & Ghafieh
        FlashcardItem(
            id = "fc_arooz_1",
            subjectId = "arooz",
            categoryTag = "تقطیع هجایی",
            frontQuestion = "هجای کشیده (مثل: کار، دوست، رفت) در تقطیع عروضی معادل چه علامتی است؟",
            backAnswer = "به زبان ساده:\nمعادل یک هجای بلند و یک هجای کوتاه یعنی «- U» (خط و هلال).\nچون هجای کشیده طولانی است (یک مصوت بلند + دو صامت در پایان)، در عروض دو بخش به حساب می‌آید.",
            memoryHook = "کشیده = بلند + کوتاه (- U)"
        ),
        FlashcardItem(
            id = "fc_arooz_2",
            subjectId = "arooz",
            categoryTag = "قواعد قافیه",
            frontQuestion = "فرق قاعده ۱ و قاعده ۲ قافیه در شعر فارسی چیست؟",
            backAnswer = "به زبان ساده:\n- قاعده ۱: کلمه قافیه به «صدای کشیده (آ، او، ای)» ختم شود و بعدش هیچ حرف بی‌صدایی نباشد (مثل صبا / وفا).\n- قاعده ۲: بعد از صدای کشیده، یک یا دو حرف بی‌صدا بیاید (مثل بهار / نگار یا دست / مست).",
            memoryHook = "بعد از صدای کشیده، صامت (حرف بی‌صدا) آمد = قاعده ۲!"
        ),
        FlashcardItem(
            id = "fc_arooz_3",
            subjectId = "arooz",
            categoryTag = "اوزان همسان",
            frontQuestion = "الگوی هجایی رکن‌های «مفاعیلن» و «فاعلاتن» به ترتیب چیست؟",
            backAnswer = "به زبان ساده با تلفظ:\n- مفاعیلن: مَ (کوتاه U) + فاعیلن (سه بلند - - -) ==> U - - -\n- فاعلاتن: فا (بلند -) + عِ (کوتاه U) + لاتن (دو بلند - -) ==> - U - -",
            memoryHook = "مفاعیلن = یک کوتاه سه بلند (U---) | فاعلاتن = بلند کوتاه دو بلند (-U--)"
        ),
        // Fonoon
        FlashcardItem(
            id = "fc_fonoon_1",
            subjectId = "fonoon",
            categoryTag = "آرایه‌های ادبی",
            frontQuestion = "فرق «اضافه تشبیهی» (چراغِ عقل) با «اضافه استعاری» (دستِ روزگار) چیست؟",
            backAnswer = "به زبان ساده:\n۱. در اضافه تشبیهی (چراغِ عقل): عقل (مشبه) و چراغ (مشبه‌به) هر دو واقعی و حاضرند.\n۲. در اضافه استعاری (دستِ روزگار): روزگار انسان نیست که دست داشته باشد! انسان حذف شده و یکی از اجزایش (دست) آمده است.",
            memoryHook = "غیرانسان + عضو بدن انسان (دست، چشم، دامن) = استعاره مکنیه!"
        ),
        FlashcardItem(
            id = "fc_fonoon_2",
            subjectId = "fonoon",
            categoryTag = "ایهام",
            frontQuestion = "واژه‌های «مدام» و «پرده» در شعر فارسی با چه معناهایی ایهام می‌سازند؟",
            backAnswer = "به زبان ساده (۱ کلمه با ۲ معنی):\n- مدام: ۱. همیشه و پیوسته ۲. شراب و باده\n- پرده: ۱. پرده و حجاب خانه ۲. پرده و نغمه موسیقی.",
            memoryHook = "کلمه‌ای که هم‌زمان دو معنی کاملاً درست در شعر زنده کند."
        ),
        FlashcardItem(
            id = "fc_fonoon_3",
            subjectId = "fonoon",
            categoryTag = "سبک‌شناسی",
            frontQuestion = "آرایه «اسلوب معادله» نشانه کدام سبک است و چطور در کنکور پیدا می‌شود؟",
            backAnswer = "به زبان ساده:\nنشانه بارز «سبک هندی» (صائب تبریزی) است.\nروش تشخیص: مصراع اول و دوم از نظر دستوری به هم وابسته نیستند، اما مصراع دوم مثالی برای اثبات حرف مصراع اول است. بین دو مصراع می‌توان عبارت «همان‌طور که» گذاشت.",
            memoryHook = "مصراع اول = مصراع دوم (با واسطه همان‌طور که) ==> اسلوب معادله!"
        ),
        // Arabic
        FlashcardItem(
            id = "fc_arabic_1",
            subjectId = "arabic",
            categoryTag = "ترجمه تکنیکی",
            frontQuestion = "حروف «لَم»، «لَن» و «لا» بر سر فعل مضارع چه معنایی می‌سازند؟",
            backAnswer = "به زبان ساده و تستی:\n۱. لَم + مضارع = گذشته منفی (نرفت / ننویس)\n۲. لَن + مضارع = آینده منفی با تأکید (هرگز نخواهد رفت)\n۳. لا + مضارع = حال یا عمومیت منفی (نمی‌رود).",
            memoryHook = "لَم برمی‌گرداند به گذشته! لَن می‌برد به آینده!"
        ),
        FlashcardItem(
            id = "fc_arabic_2",
            subjectId = "arabic",
            categoryTag = "تحلیل صرفی",
            frontQuestion = "تفاوت «تَعَلَّمَ» (با فتحه روی لام) و «تَعَلُّم» (با ضمه) چیست؟",
            backAnswer = "به زبان ساده:\n۱. تَعَلَّمَ: فعل ماضی (گذشته) از باب تفعّل است (یعنی: یاد گرفت).\n۲. تَعَلُّم: مصدر از باب تفعّل است و اسم محسوب می‌شود (یعنی: یاد گرفتن).",
            memoryHook = "حرکت حرف ماقبل آخر: فتحه (ـَ) = فعل گذشته | ضمه (ـُ) = اسم و مصدر!"
        ),
        FlashcardItem(
            id = "fc_arabic_3",
            subjectId = "arabic",
            categoryTag = "قواعد دوازدهم",
            frontQuestion = "اسم نکره منصوبی که بعد از عدد (۱۱ تا ۹۹) یا بعد از اسم تفضیل (أفعَل) می‌آید چه نقشی دارد؟",
            backAnswer = "به زبان ساده:\nنقش «تمییز» دارد و ابهام جمله را برطرف می‌کند.\nمثال: عِشرونَ طالباً (بیست تا... چه چیزی؟ طالباً = دانش‌آموز / تمییز تعداد).",
            memoryHook = "عدد یا برترین (أفعَل) + اسم تنوین‌دار منصوب = تمییز برای رفع ابهام!"
        ),
        // English
        FlashcardItem(
            id = "fc_eng_1",
            subjectId = "english",
            categoryTag = "گرامر دوازدهم",
            frontQuestion = "چه زمانی در جای خالی از «who» و چه زمانی از «which» استفاده می‌کنیم؟",
            backAnswer = "به زبان بسیار ساده:\n- اگر قبل از جای خالی «انسان» بود و بعدش «فعل» آمد ==> who\n- اگر قبل از جای خالی «شیء یا حیوان» بود ==> which",
            memoryHook = "شخص = who | غیرانسان = which"
        ),
        FlashcardItem(
            id = "fc_eng_2",
            subjectId = "english",
            categoryTag = "شرطی نوع دوم",
            frontQuestion = "فرمول جمله شرطی نوع دوم (If Clause Type 2) در انگلیسی چیست؟",
            backAnswer = "به زبان ساده:\nدر بخش If از «گذشته ساده» استفاده می‌شود، و در بخش اصلی جمله از «would + فعل ساده» استفاده می‌کنیم.\nمثال: If I had money, I would buy a car.",
            memoryHook = "اگر در بخش If گذشته آمد، در بخش دیگر would قرار بده!"
        ),
        // Farhangian
        FlashcardItem(
            id = "fc_far_1",
            subjectId = "farhangian",
            categoryTag = "مهارت معلمی قرائتی",
            frontQuestion = "طبق کتاب مهارت معلمی استاد قرائتی، حدیث «کَلِّمِ النّاسَ عَلی قَدرِ عُقولِهِم» به چه اصلی اشاره دارد؟",
            backAnswer = "به زبان ساده:\nبه اصل «توجه به تفاوت‌های فردی و سطح درک و سن مخاطبان» اشاره دارد؛ یعنی معلم باید مطالب را متناسب با ظرفیت فکری دانش‌آموزان تدریس کند.",
            memoryHook = "تناسب سخن با ظرفیت و فهم شاگرد!"
        ),
        FlashcardItem(
            id = "fc_far_2",
            subjectId = "farhangian",
            categoryTag = "سند تحول بنیادین",
            frontQuestion = "شش ساحت تربیتی در سند تحول بنیادین کدام‌اند و کدام یک ساحت محوری است؟",
            backAnswer = "به زبان ساده:\n۱. تربیتی، اعتقادی و عبادی (سـاحت مـحـوری!)\n۲. علمی و فناورانه\n۳. اجتماعی و سیاسی\n۴. اقتصادی و حرفه‌ای\n۵. زیستی و بدنی\n۶. زیباشناختی و هنری.",
            memoryHook = "ساحت اول و پایه = اعتقادی، عبادی و اخلاقی."
        ),
        // Philosophy & Logic
        FlashcardItem(
            id = "fc_phil_1",
            subjectId = "logic",
            categoryTag = "منطق دهم - کلیات خمس",
            frontQuestion = "فرق «حد تام» و «رسم تام» در منطق دهم چیست؟",
            backAnswer = "به زبان ساده:\n- حد تام = جنس نزدیک (مثل حیوان) + فصل ذاتی (مثل نطق)\n- رسم تام = جنس نزدیک (مثل حیوان) + عرض خاص صفتی (مثل خندان).",
            memoryHook = "فصل ذاتی = حد | عرض خاص صفتی = رسم!"
        ),
        FlashcardItem(
            id = "fc_phil_2",
            subjectId = "logic",
            categoryTag = "منطق دهم - قیاس استثنایی",
            frontQuestion = "در قیاس استثنایی اتصالی (اگر الف، آنگاه ب)، کدام دو حالت منطقی معتبر است؟",
            backAnswer = "به زبان ساده:\nفقط دو حالت درست است:\n۱. وضع مقدم (الف هست، پس ب هست)\n۲. رفع تالی (ب نیست، پس الف نیست).",
            memoryHook = "مقدم را تایید کن، تالی را رد کن!"
        ),
        FlashcardItem(
            id = "fc_phil_3",
            subjectId = "philosophy",
            categoryTag = "فلسفه دوازدهم - اصالت وجود",
            frontQuestion = "منظور از «اصالت وجود» و «تشکیک در وجود» در حکمت متعالیه ملاصدرا چیست؟",
            backAnswer = "به زبان ساده:\n۱. اصالت وجود یعنی در عالم خارج، فقط «وجود» واقعی و اثرگذار است و ماهیت تنها قاب ذهنی ماست.\n۲. تشکیک یعنی وجود حقیقت واحدی است که شدت و ضعف دارد (مانند نور ضعیف چراغ و نور قوی خورشید).",
            memoryHook = "وجود = نور واقعی با شدت و ضعف | ماهیت = قاب ذهنی ما."
        ),
        // Additional High-Power Flashcards Across All 7 Subjects
        FlashcardItem(
            id = "fc_math_5",
            subjectId = "math",
            categoryTag = "شمارش و ترکیب دوازدهم",
            frontQuestion = "فرق «جایگشت (ترتیب مهم)» و «ترکیب C(n,r) (ترتیب بی‌اهمیت)» در مسائل شمارش چیست؟",
            backAnswer = "به زبان ساده:\n• اگر عوض کردن جای افراد حالت جدید بسازد (مثل انتخاب مدیر و معاون، رمز یا عدد چندرقمی)  ترتیب مهم است (جایگشت).\n• اگر یک تیم یا گروه بدون سمت انتخاب کنیم یا دو نفر دست بدهند  ترتیب مهم نیست و از فرمول ترکیب C(n,r) استفاده می‌کنیم.",
            memoryHook = "سمت و رقم = جایگشت | تیم و گروه و دست دادن = ترکیب C(n,r)!"
        ),
        FlashcardItem(
            id = "fc_math_6",
            subjectId = "math",
            categoryTag = "احتمال و امید ریاضی",
            frontQuestion = "فرمول «احتمال پیشامد A» و «احتمال متمم ('A)» چیست؟",
            backAnswer = "به زبان ساده:\n۱. P(A) = تعداد حالت‌های مطلوب تقسیم بر کل حالت‌های ممکن: n(A) / n(S)\n۲. احتمال رخ ندادن پیشامد (متمم): P(A') = 1 − P(A).\nهمیشه جمع احتمال وقوع و عدم وقوع برابر ۱ (۱۰۰٪) است.",
            memoryHook = "وقوع + عدم وقوع = ۱  P(A') = 1 − P(A)!"
        ),
        FlashcardItem(
            id = "fc_math_7",
            subjectId = "math",
            categoryTag = "تابع و دامنه یازدهم",
            frontQuestion = "چه زمانی یک رابطه زوج‌مرتبی «تابع» است و چطور از روی نمودار تابع بودن را تشخیص دهیم؟",
            backAnswer = "به زبان ساده:\n۱. در زوج‌های مرتب (x, y): مؤلفه اول (x) نباید با دو مؤلفه دوم متفاوت تکرار شود.\n۲. روی نمودار (آزمون خط موازی محور عرض‌ها): اگر خط عمودی نمودار را حداکثر در ۱ نقطه قطع کند تابع است؛ اگر در ۲ نقطه قطع کند تابع نیست!",
            memoryHook = "تکرار مؤلفه اول (x) ممنوع! خط عمودی بیش از ۱ برخورد = تابع نیست!"
        ),
        FlashcardItem(
            id = "fc_arooz_4",
            subjectId = "arooz",
            categoryTag = "اختیارات شاعری",
            frontQuestion = "«اشباع» و «ابدال» در اختیارات شاعری عروض یعنی چه؟",
            backAnswer = "به زبان ساده:\n۱. اشباع (بلند کردن مصوت کوتاه): شاعر کسره اضافه یا ضمه را در پایان واژه می‌کشد تا هجای کوتاه (U) تبدیل به بلند (-) شود.\n۲. ابدال (قلب): جابه‌جا کردن دو هجای «- U» با «U -» (به‌ویژه تبدیل مفاعلن U - U - در آغاز مصراع به مفتعلن - U U -).",
            memoryHook = "اشباع = کشیدنِ کسره/ضمه (U  -) | ابدال = جابه‌جایی دو هجا!"
        ),
        FlashcardItem(
            id = "fc_arooz_5",
            subjectId = "arooz",
            categoryTag = "قالب‌های شعری",
            frontQuestion = "تفاوت آرایش قافیه در «مثنوی»، «غزل/قصیده» و «رباعی/دوبیتی» چیست؟",
            backAnswer = "به زبان ساده:\n• مثنوی: هر بیت قافیه مستقل خودش را دارد (aa, bb, cc)  مناسب داستان‌های بلند.\n• غزل و قصیده: مصراع اول با تمام مصراع‌های زوج هم‌قافیه است (ab, cb, db).\n• رباعی و دوبیتی: ۴ مصراع دارد که مصراع ۱، ۲ و ۴ هم‌قافیه‌اند (aaba). وزن رباعی «لاحول ولا قوه الا بالله» و وزن دوبیتی «مفاعیلن مفاعیلن فعولن» است.",
            memoryHook = "مثنوی = بیت‌به‌بیت قافیه جدا | دوبیتی = مفاعیلن | رباعی = لاحول ولا قوه الا بالله!"
        ),
        FlashcardItem(
            id = "fc_fonoon_4",
            subjectId = "fonoon",
            categoryTag = "آرایه‌های ادبی - حس‌آمیزی و پارادوکس",
            frontQuestion = "فرق آرایه «حس‌آمیزی» با «متناقض‌نما (پارادوکس)» چیست؟",
            backAnswer = "به زبان ساده:\n• حس‌آمیزی: ترکیب دو حس از حواس پنج‌گانه با هم (مثل «سخنِ تلخ»: سخن شنیدنی است + تلخ چشیدنی است، یا «بویِ بهبود»).\n• متناقض‌نما (پارادوکس): نسبت دادن دو ویژگی کاملاً ضدِ هم به یک چیز واحد (مثل «سلطانِ گدا»، «جنگِ صلح‌آمیز»، «در عینِ بی‌نیازی محتاج»).",
            memoryHook = "دو حس پنج‌گانه با هم = حس‌آمیزی | دو صفت ضد هم در یک چیز = پارادوکس!"
        ),
        FlashcardItem(
            id = "fc_fonoon_5",
            subjectId = "fonoon",
            categoryTag = "تاریخ ادبیات - سبک‌ها",
            frontQuestion = "رمز تشخیص فوری شاعران ۳ سبک «خراسانی»، «عراقی» و «هندی» چیست؟",
            backAnswer = "به زبان ساده با ۳ کلمه رمزی:\n• خراسانی (قرن ۴ و ۵) = رمز «رُفَفَم»: رودکی، فردوسی، فرخی سیستانی، منوچهری دامغانی.\n• عراقی (قرن ۶ تا ۹) = رمز «سَمَحَع»: سعدی، مولوی، حافظ، عطار و نظامی.\n• هندی (قرن ۱۰ تا ۱۲) = رمز «صَکَبَع»: صائب تبریزی، کلیم کاشانی، بیدل دهلوی، عرفی شیرازی.",
            memoryHook = "رُفَفَم = خراسانی | سَمَحَع = عراقی | صَکَبَع = هندی!"
        ),
        FlashcardItem(
            id = "fc_fonoon_6",
            subjectId = "fonoon",
            categoryTag = "جناس تام و ناقص",
            frontQuestion = "تفاوت «جناس تام» و «جناس ناقص (حرکتی، اختلافی، افزایشی)» چیست؟",
            backAnswer = "به زبان ساده:\n• جناس تام: دو کلمه در نوشتار و تلفظ ۱۰۰٪ یکسان‌اند اما دو معنی متفاوت دارند (مثل: «شیر» خوراکی و «شیر» جنگل، یا «روان» به معنی روح و «روان» به معنی جاری).\n• جناس ناقص: دو کلمه در یک حرکت (مِهر/مُهر)، یک حرف (کشت/گشت) یا تعداد حروف (کام/کامل) تفاوت دارند.",
            memoryHook = "جناس تام = دوقلوی همسان در ظاهر با دو معنی متفاوت!"
        ),
        FlashcardItem(
            id = "fc_arabic_4",
            subjectId = "arabic",
            categoryTag = "قواعد دوازدهم - مفعول مطلق",
            frontQuestion = "چطور در ۲ ثانیه «مفعول مطلق تأکیدی» را از «مفعول مطلق نوعی» تشخیص دهیم؟",
            backAnswer = "به زبان ساده:\nوقتی در جمله فعل و مصدرِ هم‌ریشه منصوب (مثل: نَصَرَ ... نَصراً) دیدی:\n• اگر بعد از مصدر هیچ صفت یا مضاف‌الیهی نیامد  مفعول مطلق تأکیدی.\n• اگر بعد از مصدر، صفت یا مضاف‌الیه آمد (مثل: نَصراً عَزیزاً یا اجتِهادَ العُلَماءِ)  مفعول مطلق نوعی!",
            memoryHook = "مصدر هم‌ریشه تنها = تأکیدی | مصدر هم‌ریشه + صفت/مضاف‌الیه = نوعی!"
        ),
        FlashcardItem(
            id = "fc_arabic_5",
            subjectId = "arabic",
            categoryTag = "قواعد یازدهم و دوازدهم - لای نفی جنس",
            frontQuestion = "چطور «لای نفی جنس» (معادل: هیچ ... نیست) را از سایر انواع «لا» تشخیص دهیم؟",
            backAnswer = "به زبان ساده:\nبعد از لای نفی جنس بلافاصله یک «اسم نکره بدون الـ و بدون تنوین با فتحه (ـَ)» می‌آید!\nمثال‌های پرتکرار کنکور: لا رَیبَ (هیچ شکی نیست)، لا بَأسَ (هیچ اشکالی نیست)، لا شَکَّ، لا خَوفَ، لا إلهَ.",
            memoryHook = "لا + اسمِ بدون الـ با یک فتحه (ـَ) = ۱۰۰٪ لای نفی جنس!"
        ),
        FlashcardItem(
            id = "fc_arabic_6",
            subjectId = "arabic",
            categoryTag = "قواعد دوازدهم - حال و منادا",
            frontQuestion = "نشانه قطعی «حال مفرد» در عربی دوازدهم انسانی چیست؟",
            backAnswer = "به زبان ساده:\nحال مفرد ۳ نشانه هم‌زمان دارد:\n۱. اسم مشتق نکره و منصوب است (آخرش تنوین نصب «ـاً» یا «ـینَ» دارد)\n۲. حالتِ یک اسم معرفه (صاحب حال) را بیان می‌کند\n۳. جوابِ سوال «چگونه؟» است (مثل: رَجَعَ الجُنودُ مُنتَصِرینَ = سربازان پیروزمندانه بازگشتند).",
            memoryHook = "جوابِ «چگونه؟» با تنوین نصب (ـاً) یا (ـینَ) = حال!"
        ),
        FlashcardItem(
            id = "fc_eng_3",
            subjectId = "english",
            categoryTag = "گرامر یازدهم - Gerund & Infinitive",
            frontQuestion = "کدام افعال در انگلیسی بعد از خودشان «to + فعل ساده» و کدام افعال «فعل + ing» می‌گیرند؟",
            backAnswer = "به زبان ساده با دسته‌بندی معنایی فارسی:\n• افعال خواستن، تصمیم و آینده‌نگری (want, decide, plan, agree, hope, promise, wish) ⬅ همیشه to + Verb.\n• افعال لذت، ادامه و پایان کار (enjoy, mind, finish, avoid, keep, suggest, imagine) + تمام حروف اضافه (in, on, of, for, by, without) ⬅ همیشه Verb + ing.",
            memoryHook = "افعال تصمیم و امید (want/decide/hope/plan) = to + V | افعال لذت و پایان (enjoy/finish/avoid/keep) + حروف اضافه = V-ing!"
        ),
        FlashcardItem(
            id = "fc_eng_4",
            subjectId = "english",
            categoryTag = "گرامر دوازدهم - Tag Questions",
            frontQuestion = "اگر در جمله انگلیسی کلمات «never, hardly, seldom, little, few» بیاید، دنباله سوالی (Tag Question) مثبت می‌شود یا منفی؟",
            backAnswer = "به زبان ساده:\nحتماً «مثبت» می‌شود!\nچون کلمات never (هرگز)، hardly (به‌ندرت)، seldom (به‌ندرت)، little و few خودشان بار معنایی منفی دارند، پس جمله منفی محسوب می‌شود و تگِ آخر جمله باید مثبت باشد!\nمثال: He never comes late, does he?",
            memoryHook = "کلمات منفی پنهان (never / hardly / seldom / few / little)  تگ مثبت!"
        ),
        FlashcardItem(
            id = "fc_phil_4",
            subjectId = "logic",
            categoryTag = "منطق دهم - مربع تقابل و عکس مستوی",
            frontQuestion = "کدام یک از ۴ قضیه حملی در منطق دهم «اصلاً عکس مستوی معتبر ندارد» و عکس مستوی «موجبه کلی» چیست؟",
            backAnswer = "به زبان ساده:\n۱. قضیه «سالبه جزئی (س.ج: بعضی الف ب نیست)» به هیچ وجه عکس مستوی معتبر ندارد! (دام شماره ۱ کنکور انسانی).\n۲. عکس مستوی «موجبه کلی (هر الف ب است)» تبدیل به «موجبه جزئی (بعضی ب الف است)» می‌شود!",
            memoryHook = "سالبه جزئی = عکس مستوی ندارد! | عکسِ موجبه کلی = موجبه جزئی!"
        ),
        FlashcardItem(
            id = "fc_phil_5",
            subjectId = "philosophy",
            categoryTag = "فلسفه دوازدهم - مکاتب فلسفه اسلامی",
            frontQuestion = "ابزار شناخت در ۴ مکتب «مشاء»، «اشراق»، «عرفان نظری» و «حکمت متعالیه» چه تفاوتی دارد؟",
            backAnswer = "به زبان ساده با رمز «مَفا / سَشَن / صَقَبَع»:\n۱. مشاء (فارابی و ابن‌سینا): فقط برهان و استدلال عقلی\n۲. اشراق (سهروردی): استدلال عقلی + کشف و شهود قلبی\n۳. عرفان نظری (ابن‌عربی): فقط کشف و شهود قلبی\n۴. حکمت متعالیه (ملاصدرا): وحدت و هماهنگیِ قرآن (وحی) + برهان (عقل) + عرفان (شهود).",
            memoryHook = "ابن‌سینا = عقل | سهروردی = عقل و شهود | ملاصدرا = قرآن و برهان و عرفان!"
        ),
        FlashcardItem(
            id = "fc_far_3",
            subjectId = "farhangian",
            categoryTag = "مصاحبه و کانون ارزیابی فرهنگیان",
            frontQuestion = "در مصاحبه فرهنگیان اگر بپرسند «در صورت خطای دانش‌آموز در کلاس چه می‌کنید؟» بهترین پاسخ تربیتی چیست؟",
            backAnswer = "به زبان ساده:\n۱. در لحظه تدریس در جمع کلاس: حفظ آرامش، «تغافل (نادیده گرفتن هوشمندانه برای حفظ آبرو)» و مدیریت غیرمستقیم با تغییر لحن یا سوال.\n۲. پس از پایان کلاس: گفت‌وگوی خصوصی، محترمانه و همدلانه با دانش‌آموز برای ریشه‌یابی علت رفتار.",
            memoryHook = "در جمع = حفظ کرامت و تغافل | در خلوت = گفت‌وگوی محترمانه و ریشه‌یابی!"
        ),
        FlashcardItem(
            id = "fc_far_4",
            subjectId = "farhangian",
            categoryTag = "هوش و استعداد معلمی",
            frontQuestion = "قوی‌ترین روش «تضعیف یا تقویت یک استدلال علی» در تست‌های هوش و استعداد معلمی چیست؟",
            backAnswer = "به زبان ساده:\n• برای تضعیف: معرفی یک «عامل سوم (علت پنهان مشترک)» یا نشان دادن اینکه معلول بدون آن علت هم رخ داده است.\n• برای تقویت: رد کردن سایر عوامل مزاحم و نشان دادن اینکه هر جا علت حضور داشته، معلول نیز ایجاد شده است.",
            memoryHook = "تضعیف استدلال علی = پیدا کردن عامل سوم پنهان!"
        ),
        FlashcardItem(
            id = "fc_math_8",
            subjectId = "math",
            categoryTag = "میان‌بر ۸۰٪ کنکور — واریانس و CV",
            frontQuestion = "اگر همه داده‌های آماری در عدد ثابت k ضرب و با عدد c جمع شوند (y = kx + c)، میانگین، انحراف معیار و واریانس چه تغییری می‌کنند؟",
            backAnswer = "به زبان ساده (بدون نیاز به محاسبه تک‌تک داده‌ها):\n۱. میانگین جدید: هم ضرب می‌شود و هم جمع (ȳ = k·x̄ + c)\n۲. انحراف معیار جدید: جمع (c) بی‌اثر است! فقط در قدرمطلق k ضرب می‌شود (σ_y = |k|·σ_x)\n۳. واریانس جدید: جمع (c) بی‌اثر است! در مجذور k ضرب می‌شود (σ²_y = k²·σ²_x).",
            memoryHook = "جمع و تفریق روی واریانس و انحراف معیار هیچ اثری ندارد! ضرب در k واریانس را k² برابر می‌کند!"
        ),
        FlashcardItem(
            id = "fc_arooz_6",
            subjectId = "arooz",
            categoryTag = "تسلط ۱۰۰٪ عروض و قافیه",
            frontQuestion = "چطور در ۳ ثانیه وزن‌های شبیه «مفاعیلن (U - - -)» و «مفاعلن (U - U -)» یا «فعلاتن مفاعلن» و «فعلاتن فعلاتن» را تفکیک کنیم؟",
            backAnswer = "به زبان ساده:\n۱. بین مفاعیلن و مفاعلن فقط به «هجای سوم مصراع» نگاه کن: اگر هجای سوم بلند (-) بود مفاعیلن است؛ اگر کوتاه (U) بود مفاعلن است!\n۲. بین «فعلاتن مفاعلن فعلن (۱۱ هجایی)» و «فعلاتن فعلاتن فعلاتن فعلن (۱۵ هجایی)» به تعداد هجا یا هجای ششم نگاه کن (در مفاعلن هجای ششم بلند است، در فعلاتن کوتاه!).",
            memoryHook = "هجای سوم = مرز مفاعیلن و مفاعلن | هجای ششم و تعداد هجا = مرز فعلاتن مفاعلن و فعلاتن فعلاتن!"
        ),
        FlashcardItem(
            id = "fc_fonoon_7",
            subjectId = "fonoon",
            categoryTag = "آرایه‌های پیشرفته — ۵ نوع ترکیب اضافی",
            frontQuestion = "چگونه ۵ نوع ترکیب اضافی (تشبیهی، استعاری، مجازی، تخصیصی و اقترانی) را در ۵ ثانیه از هم تشخیص دهیم؟",
            backAnswer = "به زبان ساده:\n۱. هر دو کلمه را برعکس کن و بینشان «مثلِ» بگذار: اگر معنی داد = اضافه تشبیهی (درختِ دانش = دانش مثل درخت).\n۲. اگر کلمه اول عضو انسان/جاندار + کلمه دوم غیرانسان بود = اضافه استعاری مکنیه (دستِ روزگار، لبِ ساحل).\n۳. اگر کلمه اول در معنی غیراصلی خودش با قرینه بود = اضافه مجازی (سرِ کوه، خونِ رزان).",
            memoryHook = "برعکس کن + مثل = تشبیهی | عضو + غیرانسان = استعاری!"
        ),
        FlashcardItem(
            id = "fc_arabic_7",
            subjectId = "arabic",
            categoryTag = "تسلط ۱۰۰٪ عربی — اعلال و مفعول مطلق",
            frontQuestion = "چطور «مفعول مطلق تأکیدی» را از «مفعول مطلق نوعی» و «اعلال به حذف» را در افعال معتل تشخیص دهیم؟",
            backAnswer = "به زبان ساده:\n۱. مصدر منصوب هم‌ریشه فعل: اگر تنها آمد = مفعول مطلق تأکیدی (نَصَرَ نَصراً)؛ اگر بعدش صفت یا مضاف‌الیه آمد = مفعول مطلق نوعی (فَتَحنا فَتحاً مُبیناً).\n۲. اعلال به حذف: اگر اول فعل مضارع/امر از ریشه مثال واوی «و» افتاد (وَعَدَ ⟹ یَعِدُ / عِدْ) یا در اجوف مجزوم حرف عله وسط افتاد (قالَ ⟹ لَم یَقُلْ / قُلْ) = اعلال به حذف!",
            memoryHook = "مصدر تنها = تأکیدی | مصدر + صفت/مضاف‌الیه = نوعی | یَعِدُ / قُلْ / اِرمِ = اعلال به حذف!"
        ),
        FlashcardItem(
            id = "fc_english_4",
            subjectId = "english",
            categoryTag = "تسلط ۱۰۰٪ زبان — گرامر دوازدهم",
            frontQuestion = "۳ دام مرگبار کنکور در دنباله سوالی (Tag Questions) انگلیسی چیست؟",
            backAnswer = "به زبان ساده:\n۱. کلمات منفی پنهان (never, hardly, seldom, rarely, little, few) جمله را منفی می‌کنند ⟹ تگ آخر حتماً باید مثبت باشد!\n۲. جمله با «I am ...» ⟹ تگ منفی‌اش می‌شود «aren't I?»\n۳. جمله با «Let's ...» ⟹ تگش می‌شود «shall we?» و جمله امری ⟹ «will you?».",
            memoryHook = "دیدن hardly / never / seldom / little / few = تگِ آخر ۱۰۰٪ مثبت!"
        ),
        FlashcardItem(
            id = "fc_far_5",
            subjectId = "farhangian",
            categoryTag = "فول‌پکیج انسانی — اقتصاد و روان‌شناسی",
            frontQuestion = "تفاوت «سود حسابداری» با «سود اقتصادی (ویژه)» در اقتصاد و «تقویت منفی» با «تنبیه» در روان‌شناسی چیست؟",
            backAnswer = "به زبان ساده:\n• در اقتصاد: سود حسابداری = درآمد کل − هزینه‌های آشکار | سود اقتصادی = سود حسابداری − هزینه‌های پنهان (هزینه فرصت).\n• در روان‌شناسی: تقویت منفی یعنی «حذف یک عامل ناخوشایند برای افزایش رفتار مطلوب» (مثل بستن کمربند برای قطع صدای بوق خودرو)؛ اما تنبیه یعنی کاهش رفتار نامطلوب.",
            memoryHook = "سود اقتصادی هزینه فرصت را هم کم می‌کند | تقویت منفی = حذف درد و بوق مزاحم (افزایش رفتار)!"
        ),
        // History & Geography (تاریخ ۱، ۲، ۳ و جغرافیا ۱، ۲، ۳ — دهم ۱۴۰۳، یازدهم ۱۴۰۴، دوازدهم ۱۴۰۵)
        FlashcardItem(
            id = "fc_hist_1",
            subjectId = "history",
            categoryTag = "تاریخ ۱ دهم (چاپ ۱۴۰۳) — ایران باستان",
            frontQuestion = "تفاوت شیوه کشورداری «هخامنشیان»، «اشکانیان» و «ساسانیان» در یک جمله چیست؟",
            backAnswer = "به زبان خودمونی:\n۱. هخامنشیان: سیستم «ساتراپی (شهربانی)» با بازرسان مخفی (چشم و گوش شاه) و راه شاهی.\n۲. اشکانیان: سیستم «ملوک‌الطوایفی (پادشاهان محلی نیمه‌مستقل)» + «مجلس مهستان» که قدرت شاه را محدود می‌کرد.\n۳. ساسانیان: حکومت «شدیداً متمرکز» + «اتحاد دین زرتشتی و دولت».",
            memoryHook = "هخامنشی = ساتراپی | اشکانی = مهستان و ملوک‌الطوایفی | ساسانی = تمرکز و پیوند دین و دولت!"
        ),
        FlashcardItem(
            id = "fc_hist_2",
            subjectId = "history",
            categoryTag = "تاریخ ۲ یازدهم (چاپ ۱۴۰۴) — ایران اسلامی",
            frontQuestion = "تفاوت «طاهریان»، «صفاریان» و «آل‌بویه» در برابر خلفای عباسی چه بود؟",
            backAnswer = "به زبان خودمونی:\n• طاهریان (نیشابور): نخستین حکومت نیمه‌مستقل ایرانی که با حکم خلیفه مأمون سر کار آمدند.\n• صفاریان (یعقوب لیث): بدون اجازه خلیفه و با تکیه بر هویت ایرانی و زبان فارسی با خلیفه عباسی جنگیدند (جنگ دیرالعاقول).\n• آل‌بویه (شیعه): پایتخت خلافت عباسی (بغداد) را فتح کردند و خلیفه را مطیع خود ساختند.",
            memoryHook = "طاهریان = با اجازه خلیفه | صفاریان = جنگ با خلیفه | آل‌بویه = فتح بغداد!"
        ),
        FlashcardItem(
            id = "fc_hist_3",
            subjectId = "history",
            categoryTag = "تاریخ ۳ دوازدهم (چاپ ۱۴۰۵) — عهدنامه‌های قاجار",
            frontQuestion = "عهدنامه‌های «گلستان و ترکمانچای» و «پاریس» در زمان کدام شاهان قاجار بسته شدند و چه مناطقی از ایران جدا شد؟",
            backAnswer = "به زبان خودمونی:\n۱. گلستان و ترکمانچای: در زمان «فتحعلی‌شاه قاجار» با روسیه تزاری (جدایی قفقاز، تعیین رود ارس به عنوان مرز و آغاز حق کاپیتولاسیون در ترکمانچای).\n۲. عهدنامه پاریس: در زمان «ناصرالدین‌شاه قاجار» با انگلستان (جدایی کامل هرات و افغانستان از ایران).",
            memoryHook = "فتحعلی‌شاه + روسیه = جدایی قفقاز (ترکمانچای) | ناصرالدین‌شاه + انگلیس = جدایی هرات (پاریس)!"
        ),
        FlashcardItem(
            id = "fc_geo_1",
            subjectId = "geography",
            categoryTag = "جغرافیا ۱ دهم (چاپ ۱۴۰۳) — آب و مرزهای ایران",
            frontQuestion = "تنها حوضه آبریز «باز» ایران کدام است و ایران طولانی‌ترین و کوتاه‌ترین مرز خشکی را با کدام کشورها دارد؟",
            backAnswer = "به زبان خودمونی:\n• تنها حوضه آبریز باز ایران (متصل به اقیانوس): حوضه «خلیج فارس و دریای عمان» (بقیه ۵ حوضه مثل خزر، ارومیه، مرکزی، هامون و قرهقوم بسته هستند).\n• طولانی‌ترین مرز خشکی ایران با «عراق» و کوتاه‌ترین مرز خشکی با «ارمنستان» است.",
            memoryHook = "حوضه باز = فقط خلیج فارس و دریای عمان | مرز خشکی بلند = عراق، کوتاه = ارمنستان!"
        ),
        FlashcardItem(
            id = "fc_geo_2",
            subjectId = "geography",
            categoryTag = "جغرافیا ۲ و ۳ (چاپ ۱۴۰۴ و ۱۴۰۵) — مدیریت بحران و آمایش",
            frontQuestion = "چهار مرحله «چرخه مدیریت بحران» به ترتیب زمانی و سه رکن «آمایش سرزمین» در جغرافیای دوازدهم کدام‌اند؟",
            backAnswer = "به زبان خودمونی:\n• ۴ مرحله مدیریت بحران: ۱. پیشگیری (مقاوم‌سازی قبل از حادثه) ⟵ ۲. آمادگی (مانور و آموزش قبل از حادثه) ⟵ ۳. مقابله (امداد و نجات حین حادثه) ⟵ ۴. بازسازی و بازتوانی (پس از حادثه).\n• ۳ رکن آمایش سرزمین: ۱. انسان (جمعیت) + ۲. فضا (سرزمین) + ۳. فعالیت (اقتصاد).",
            memoryTrickOrHook = "مدیریت بحران: پیشگیری ⟵ آمادگی ⟵ مقابله ⟵ بازسازی | آمایش سرزمین: انسان + فضا + فعالیت!"
        ),
        // Sociology, Psychology & Economics (اقتصاد ۱۰، روان‌شناسی ۱۱، جامعه‌شناسی ۱-۳)
        FlashcardItem(
            id = "fc_econ_1",
            subjectId = "economics",
            categoryTag = "اقتصاد دهم (چاپ ۱۴۰۳) — هزینه فرصت و سود",
            frontQuestion = "فرمول دقیق «سود حسابداری» و «سود اقتصادی» در مسائل کنکور اقتصاد چیست؟",
            backAnswer = "به زبان کاملاً خودمونی:\n۱. سود حسابداری = درآمد کل منهای هزینه‌های آشکار (پول‌هایی که فاکتور دارند مثل مواد اولیه، دستمزد کارگر، اجاره پرداختی).\n۲. سود اقتصادی = سود حسابداری منهای هزینه‌های پنهان (هزینه فرصت؛ مثل حقوق شغل قبلی که رها کرده‌ای یا اجاره مغازه شخصی خودت).",
            memoryTrickOrHook = "سود حسابداری فقط هزینه نقدی را کم می‌کند؛ سود اقتصادی هزینه فرصت (شغل قبلی) را هم کم می‌کند!"
        ),
        FlashcardItem(
            id = "fc_econ_2",
            subjectId = "economics",
            categoryTag = "اقتصاد دهم (چاپ ۱۴۰۳) — عرضه و تقاضا",
            frontQuestion = "چه زمانی در بازار «مازاد عرضه» و چه زمانی «مازاد تقاضا» به وجود می‌آید؟",
            backAnswer = "به زبان خودمونی:\n• وقتی قیمت بازار «بالاتر از قیمت تعادلی» باشد: تولیدکننده‌ها زیاد می‌آورند اما مردم نمی‌خرند ⟹ «مازاد عرضه» (کالا روی دست فروشنده می‌ماند و قیمت پایین می‌آید).\n• وقتی قیمت بازار «پایین‌تر از قیمت تعادلی» باشد: مردم صف می‌کشند اما کالا کم است ⟹ «مازاد تقاضا» (کمبود کالا).",
            memoryTrickOrHook = "قیمت بالا = مازاد عرضه (اضافه کالا) | قیمت پایین = مازاد تقاضا (کمبود و صف)!"
        ),
        FlashcardItem(
            id = "fc_psych_1",
            subjectId = "psychology",
            categoryTag = "روان‌شناسی یازدهم (چاپ ۱۴۰۴) — حافظه و فراموشی",
            frontQuestion = "رمز تشخیص فوری «تداخل پیش‌گستر» از «تداخل پس‌گستر» در تست‌های حافظه روان‌شناسی چیست؟",
            backAnswer = "به زبان خودمونی:\nهمیشه ببین کدام مطلب یادت رفته است:\n• اگر مطلب «جدید» یادت رفته (یعنی آموختهٔ قدیمی جلو آمده و مزاحمِ یادگیری جدید شده) ⟹ تداخل پیش‌گستر!\n• اگر مطلب «قدیمی» یادت رفته (یعنی آموختهٔ جدید به عقب برگشته و قبلی را پاک کرده) ⟹ تداخل پس‌گستر!",
            memoryTrickOrHook = "جدید یادت رفت = پیش‌گستر! | قدیم یادت رفت = پس‌گستر!"
        ),
        FlashcardItem(
            id = "fc_psych_2",
            subjectId = "psychology",
            categoryTag = "روان‌شناسی یازدهم (چاپ ۱۴۰۴) — مراحل رشد پیاژه",
            frontQuestion = "چهار مرحله رشد شناختی «ژان پیاژه» به ترتیب سن و کلیدواژه هر کدام چیست؟",
            backAnswer = "به زبان خودمونی:\n۱. حسی - حرکتی (۰ تا ۲ سال): کسب «پایداری شیء».\n۲. پیش‌عملیاتی (۲ تا ۷ سال): دارای «خودمحوری، جاندارپنداری» و ناتوان در نگهداری ذهنی.\n۳. عملیات عینی (۷ تا ۱۱ سال): کسب «نگهداری ذهنی» (جرم، عدد، مایعات) و تفکر منطقی ملموس.\n۴. عملیات صوری (۱۱ سال به بالا): تفکر «انتزاعی و فرضی».",
            memoryTrickOrHook = "پایداری شیء (حسی-حرکتی) ⟵ جاندارپنداری (پیش‌عملیاتی) ⟵ نگهداری ذهنی (عینی) ⟵ انتزاعی (صوری)!"
        ),
        FlashcardItem(
            id = "fc_soc_1",
            subjectId = "sociology",
            categoryTag = "جامعه‌شناسی ۱، ۲ و ۳ (چاپ ۱۴۰۳ تا ۱۴۰۵)",
            frontQuestion = "تفاوت سه رویکرد «تبیینی (اثبات‌گرا)»، «تفسیری» و «انتقادی» در جامعه‌شناسی دوازدهم چیست؟",
            backAnswer = "به زبان خودمونی:\n۱. تبیینی (کنت و دورکیم): جامعه مثل طبیعت است؛ روش کمّی و آماری؛ هدف: کشف قانون علّی برای کنترل جامعه (بی‌طرفی ارزشی).\n۲. تفسیری (ماکس وبر): جامعه معنادار است؛ روش کیفی و تفهمی؛ هدف: فهم نیت و معنای ذهنی کنشگران.\n۳. انتقادی (مکتب فرانکفورت): رد بی‌طرفی ارزشی؛ هدف: نقد سلطه و آگاهی‌بخشی و رهایی مظلومان.",
            memoryTrickOrHook = "کنت/آمار/کنترل = تبیینی | وبر/معنا/تفهمی = تفسیری | فرانکفورت/نقد سلطه/رهایی = انتقادی!"
        ),
        FlashcardItem(
            id = "fc_soc_2",
            subjectId = "sociology",
            categoryTag = "جامعه‌شناسی ۱ دهم (چاپ ۱۴۰۳) — هویت و تحرک",
            frontQuestion = "فرق «هویت انتسابی» با «هویت اکتسابی» و «آنومی» با «برچسب‌زنی» در جامعه‌شناسی چیست؟",
            backAnswer = "به زبان خودمونی:\n• انتسابی: ویژگی‌هایی که از بدو تولد بدون انتخاب خودت داری (جنسیت، قومیت، خانواده).\n• اکتسابی: ویژگی‌هایی که با تلاش و انتخاب خودت به دست می‌آوری (شغل، مدرک، مهارت).\n• علت کج‌روی: در رویکرد تبیینی «آنومی (بی‌هنجاری)»، در تفسیری «برچسب‌زنی» و در انتقادی «بی‌عدالتی و سلطه طبقاتی» است.",
            memoryTrickOrHook = "تولدی = انتسابی | تلاشی = اکتسابی | آنومی = تبیینی | برچسب‌زنی = تفسیری!"
        ),
        FlashcardItem(
            id = "fc_dini_1",
            subjectId = "dini",
            categoryTag = "دین و زندگی ۱، ۲ و ۳ جامع (چاپ ۱۴۰۳ تا ۱۴۰۵)",
            frontQuestion = "تفاوت ۴ مرتبه «توحید ذاتی، صفاتی، افعالی و عملی» در دین و زندگی دوازدهم چیست؟",
            backAnswer = "به زبان خودمونی:\n۱. توحید ذاتی: خدا یکی است و شریک و جزء ندارد (قل هو الله احد).\n۲. توحید صفاتی: صفات خدا (علم، قدرت) عینِ ذات اوست.\n۳. توحید افعالی: تنها رب، مدبّر و کارگردان جهان خداست (ریشه توکل).\n۴. توحید عملی: در زندگی و عمل، فقط خدا را بندگی کنیم و تسلیم طاغوت و هوس نشویم.",
            memoryTrickOrHook = "خلقت و تدبیر و روزی = توحید افعالی (توکل) | بندگی در عمل = توحید عملی!"
        ),
        FlashcardItem(
            id = "fc_farsi_1",
            subjectId = "farsi",
            categoryTag = "فارسی و نگارش ۱، ۲ و ۳ (دستور زبان)",
            frontQuestion = "چطور در ۲ ثانیه در گروه اسمی «صفت بیانی» را از «مضاف‌الیه» تشخیص دهیم؟",
            backAnswer = "به زبان خودمونی:\nکسرهٔ «ـِ» را بردار و بین دو کلمه «است» بگذار:\n• اگر جمله معنی داد ⟹ صفت بیانی است (مثل: «دلِ شاد» ⬅ دل شاد است ✅).\n• اگر معنی نداد و نشان‌دهنده مالکیت بود ⟹ مضاف‌الیه است (مثل: «کتابِ علی» ⬅ کتاب علی است ❌).",
            memoryTrickOrHook = "گذاشتنِ «است» بین دو کلمه: معنی داد = صفت بیانی | معنی نداد = مضاف‌الیه!"
        ),
        FlashcardItem(
            id = "fc_gen_1",
            subjectId = "general_humanities",
            categoryTag = "سلامت ۱۲، محیط زیست ۱۱ و سواد رسانه‌ای ۱۰",
            frontQuestion = "منظور از «آب مجازی» در محیط زیست یازدهم، «BMI» در سلامت دوازدهم و «فن شهادت» در سواد رسانه‌ای دهم چیست؟",
            backAnswer = "به زبان خودمونی:\n• آب مجازی: کل آبی که در تمام مراحل تولید یک کالا (مثلاً از کاشت علوفه تا تولید ۱ کیلو گوشت) مصرف شده است.\n• نمایه توده بدنی (BMI): وزن (کیلوگرم) تقسیم بر مجذور قد (متر مربع)؛ عدد ۱۸/۵ تا ۲۵ طبیعی است.\n• فن شهادت: استفاده از افراد مشهور (ورزشکار/بازیگر) برای تبلیغ یک کالا.",
            memoryTrickOrHook = "آب پنهان تولید = آب مجازی | وزن بر قد² = BMI | سلبریتی در تبلیغ = فن شهادت!"
        )
    ) + MasteryEncyclopedia1405.flashcards + ZeroToHundredMasterCurriculum1405.flashcards)
        .distinctBy { it.id }
    }

    // Helper constructor overload for FlashcardItem
    private fun FlashcardItem(
        id: String,
        subjectId: String,
        categoryTag: String,
        frontQuestion: String,
        backAnswer: String,
        memoryTrickOrHook: String
    ) = com.example.data.curriculum.FlashcardItem(
        id = id,
        subjectId = subjectId,
        categoryTag = categoryTag,
        frontQuestion = frontQuestion,
        backAnswer = backAnswer,
        memoryHook = memoryTrickOrHook
    )

    val quizQuestions: List<QuizQuestionItem> by lazy(LazyThreadSafetyMode.PUBLICATION) { (listOf(
        // Math
        QuizQuestionItem(
            id = "q_math_1",
            subjectId = "math",
            difficultyTag = "سطح منفی صفر",
            questionText = "حاصل عبارت عددی  -8 - (-15) + (-4)  کدام است؟",
            options = listOf("+3", "-27", "-11", "+19"),
            correctOptionIndex = 0,
            zeroLevelSolution = "ابتدا دو منفی پشت پرانتز -(-15) در هم ضرب می‌شوند و تبدیل به +15 می‌شوند. مثبت پشت (-4) هم می‌شود -4. حالا عبارت می‌شود: -8 + 15 - 4. هشت تومن بدهی با ۴ تومن بدهی می‌شود ۱۲ تومن بدهی (-12). حالا -12 + 15 = +3.",
            shortcutTechnique = "منفی در منفی پشت پرانتز = مثبت! دو بدهی را با هم جمع کن و از پولت کم کن."
        ),
        QuizQuestionItem(
            id = "q_math_2",
            subjectId = "math",
            difficultyTag = "تست کنکور انسانی",
            questionText = "مجموع ریشه‌های معادله  2x² - 9x + 7 = 0  کدام است و ریشه‌های آن چه اعدادی هستند؟",
            options = listOf("ریشه‌ها 1 و 7/2 هستند (مجموع 9/2)", "ریشه‌ها -1 و -7/2 هستند", "ریشه‌ها 2 و 7 هستند", "معادله ریشه حقیقی ندارد"),
            correctOptionIndex = 0,
            zeroLevelSolution = "جمع ضرایب معادله را حساب کن: 2 + (-9) + 7 = 0! طبق شگرد طلایی، وقتی جمع ضرایب صفر شود یک ریشه x = 1 و ریشه دیگر x = c/a = 7/2 است. مجموع ریشه‌ها نیز -b/a = 9/2 است.",
            shortcutTechnique = "شگرد a + b + c = 0 در ۳ ثانیه بدون محاسبه دلتا!"
        ),
        QuizQuestionItem(
            id = "q_math_3",
            subjectId = "math",
            difficultyTag = "تست کنکور انسانی",
            questionText = "اگر در یک دنباله حسابی جمله چهارم برابر 13 و جمله نهم برابر 28 باشد، جمله اول (a₁) کدام است؟",
            options = listOf("4", "3", "5", "7"),
            correctOptionIndex = 0,
            zeroLevelSolution = "از جمله چهارم تا نهم 5 قدم فاصله است (9 - 4 = 5). مقدار جملات 28 - 13 = 15 واحد زیاد شده است. پس هر قدم (قدر نسبت d) برابر 15 ÷ 5 = 3 است. حالا از جمله چهارم (13) سه قدم به عقب برمی‌گردیم تا به جمله اول برسیم: 13 - 3(3) = 13 - 9 = 4.",
            shortcutTechnique = "d = (28 - 13) / (9 - 4) = 3  --->  a₁ = a₄ - 3d = 4"
        ),
        QuizQuestionItem(
            id = "q_math_4",
            subjectId = "math",
            difficultyTag = "امتحان نهایی و کنکور",
            questionText = "اگر واریانس ۱۰ داده آماری برابر 4 باشد و همه داده‌ها را در 3 ضرب کرده و سپس 7 واحد به هر کدام اضافه کنیم، واریانس داده‌های جدید کدام است؟",
            options = listOf("36", "12", "43", "19"),
            correctOptionIndex = 0,
            zeroLevelSolution = "طبق رمز «جمع بی‌اثر در پراکندگی»، اضافه کردن عدد 7 هیچ تاثیری بر واریانس ندارد! فقط چون داده‌ها در k = 3 ضرب شده‌اند، واریانس جدید برابر k² برابرِ واریانس قدیم می‌شود: 3² × 4 = 9 × 4 = 36.",
            shortcutTechnique = "واریانس جدید = k² × واریانس قدیم (جمع و تفریق را خط بزن!): 9 × 4 = 36."
        ),
        QuizQuestionItem(
            id = "q_math_5",
            subjectId = "math",
            difficultyTag = "تست کنکور انسانی - شمارش و احتمال",
            questionText = "از بین 5 معلم ریاضی و 4 معلم ادبیات، به چند طریق می‌توان یک گروه 3 نفره انتخاب کرد به طوری که دقیقاً 2 معلم ریاضی و 1 معلم ادبیات در گروه باشند؟",
            options = listOf("40 حالت", "14 حالت", "20 حالت", "84 حالت"),
            correctOptionIndex = 0,
            zeroLevelSolution = "۱. چون گروه بدون سمت انتخاب می‌کنیم، ترتیب مهم نیست و از ترکیب استفاده می‌کنیم.\n۲. انتخاب 2 معلم ریاضی از 5 نفر: C(5, 2) = (5 × 4) / 2 = 10.\n۳. انتخاب 1 معلم ادبیات از 4 نفر: C(4, 1) = 4.\n۴. چون می‌گوید 2 ریاضی «وَ» 1 ادبیات، طبق اصل ضرب در هم ضرب می‌شوند: 10 × 4 = 40.",
            shortcutTechnique = "C(5,2) × C(4,1) = 10 × 4 = 40 (کلمه «وَ» یعنی ضرب!)."
        ),
        QuizQuestionItem(
            id = "q_math_6",
            subjectId = "math",
            difficultyTag = "تست کنکور انسانی - منطق ریاضی دهم",
            questionText = "اگر گزاره «(p ⟹ q)» نادرست (F) باشد، ارزش گزاره مرکب «(~p ∨ r) ⟺ q» کدام است؟",
            options = listOf("بسته به ارزش r می‌تواند درست یا نادرست باشد (هم‌ارز با ~r)", "همواره درست (T) است", "همواره نادرست (F) است", "ارزش p نادرست و q درست است"),
            correctOptionIndex = 0,
            zeroLevelSolution = "تنها حالتی که گزاره شرطی (p ⟹ q) نادرست (F) می‌شود وقتی است که مقدم درست (p ≡ T) و تالی نادرست (q ≡ F) باشد! پس ~p ≡ F است. در این صورت (~p ∨ r) می‌شود (F ∨ r) ≡ r، و چون q ≡ F است، دوشرطی (r ⟺ F) معادل ~r خواهد بود.",
            shortcutTechnique = "نادرست بودن (p ⟹ q) فوراً لو می‌دهد که p = T و q = F است!"
        ),
        // Arooz
        QuizQuestionItem(
            id = "q_arooz_1",
            subjectId = "arooz",
            difficultyTag = "سطح صفر عروض",
            questionText = "تقطیع هجایی عبارت «دلِ عاشقان» در وسط مصراع کدام است؟",
            options = listOf("U U - U - U", "- U - U -", "U - - U -", "- - U U -"),
            correctOptionIndex = 0,
            zeroLevelSolution = "بخش می‌کنیم: دِ (U) + لِ (U) + عا (-) + شِ (U) + قان (-U، کشیده است چون مصوت بلند آ + ن ساکن دارد). پس به ترتیب می‌شود: U U - U - U.",
            shortcutTechnique = "دِ (کوتاه) + لِ (کوتاه) + عا (بلند) + شِ (کوتاه) + قان (کشیده = بلند و کوتاه)."
        ),
        QuizQuestionItem(
            id = "q_arooz_2",
            subjectId = "arooz",
            difficultyTag = "تست کنکور انسانی",
            questionText = "وزن بیت «الا یا ایها الساقی ادر کاساً و ناولها // که عشق آسان نمود اول ولی افتاد مشکل‌ها» کدام است؟",
            options = listOf(
                "مفاعیلن مفاعیلن مفاعیلن مفاعیلن (U - - - چهار بار)",
                "فاعلاتن فاعلاتن فاعلاتن فاعلاتن (- U - - چهار بار)",
                "فعولن فعولن فعولن فعولن (U - - چهار بار)",
                "مستفعلن مستفعلن مستفعلن مستفعلن (- - U - چهار بار)"
            ),
            correctOptionIndex = 0,
            zeroLevelSolution = "فقط ۴ هجای اول مصراع دوم را تقطیع کن: کِ (U) + عِش (-) + قا (-) [وصل قِ عشق به آ در آسان!] + سان (-). الگوی ۴ هجای اول شد «U - - -» که معادل «مفاعیلن» است!",
            shortcutTechnique = "تقطیع ۴ هجای اول مصراع = تشخیص فوری وزن در کمتر از ۱۰ ثانیه!"
        ),
        QuizQuestionItem(
            id = "q_arooz_3",
            subjectId = "arooz",
            difficultyTag = "تست کنکور انسانی - اوزان دوری",
            questionText = "وزن بیت «ای ساربان آهسته ران کارام جانم می‌رود // وان دل که با خود داشتم با دلستانم می‌رود» کدام است؟",
            options = listOf(
                "مستفعلن مستفعلن مستفعلن مستفعلن (- - U - چهار بار — بحر رجز)",
                "مفاعیلن مفاعیلن مفاعیلن مفاعیلن (بحر هزج)",
                "فاعلاتن فاعلاتن فاعلاتن فعلن (بحر رمل)",
                "فعولن فعولن فعولن فعل (بحر متقارب)"
            ),
            correctOptionIndex = 0,
            zeroLevelSolution = "۴ هجای اول را تقطیع می‌کنیم: ای (-) + سا (-) + رِ (U) + بان (-U، که نون آن به آ در آهسته حذف همزه و وصل می‌شود: با (-) + نآ (-)). ۴ هجای اول «ای سا رِ با» الگوی «- - U -» دارد که تنها هجای کوتاه در خانه ۳ است  مستفعلن (بحر رجز)!",
            shortcutTechnique = "کوتاه در خانه ۳ (- - U -) = مستفعلن (بحر رجز)!"
        ),
        // Fonoon
        QuizQuestionItem(
            id = "q_fonoon_1",
            subjectId = "fonoon",
            difficultyTag = "تست کنکور انسانی",
            questionText = "در بیت «پیش صاحب‌نظران ملک سلیمان باد است // بلکه آن است سلیمان که ز ملک آزاد است» کدام آرایه در واژه «باد» وجود دارد؟",
            options = listOf("ایهام (۱. هوا و باد، ۲. هیچ و نابود، ۳. فعل دعایی به معنی بادا)", "حس‌آمیزی", "متناقض‌نما (پارادوکس)", "اسلوب معادله"),
            correctOptionIndex = 0,
            zeroLevelSolution = "واژه «باد» در اینجا هم به معنی «هیچ و بی‌ارزش» است، هم به داستان «فرمانروایی حضرت سلیمان بر باد» اشاره دارد (تلمیح و تناسب)، و هم ایهام دارد!",
            shortcutTechnique = "کلمه «باد» در کنار «سلیمان» همیشه تلمیح و ایهام می‌سازد."
        ),
        QuizQuestionItem(
            id = "q_fonoon_2",
            subjectId = "fonoon",
            difficultyTag = "تست کنکور انسانی",
            questionText = "بیت «حسن تو همیشه در فزون باد // رویت همه ساله لاله‌گون باد» در بخش قافیه تابع کدام قاعده است؟",
            options = listOf("قاعده ۲ قافیه (مصوت بلند «او» + صامت «ن» در فزون و لاله‌گون)", "قاعده ۱ قافیه", "ردیف ندارد و قافیه «باد» است", "قافیه درونی دارد"),
            correctOptionIndex = 0,
            zeroLevelSolution = "اول ردیف «باد» را خط می‌زنیم. کلمات قافیه «فزون» و «لاله‌گون» هستند. بخش مشترک پایانی «اون» (مصوت بلند او + صامت ن) است؛ چون بعد از مصوت حرف صامت آمده، تابع قاعده ۲ قافیه است.",
            shortcutTechnique = "اول ردیف را خط بزن؛ بعد از مصوت حرف ساکن دیدی = قاعده ۲!"
        ),
        QuizQuestionItem(
            id = "q_fonoon_3",
            subjectId = "fonoon",
            difficultyTag = "تست کنکور انسانی - سبک‌شناسی و آرایه",
            questionText = "بیت «اظهار عجز پیش ستم‌پیشه ابلهی است // اشک کباب باعث طغیان آتش است» دارای کدام آرایه شاخص و سروده کدام سبک ادبی است؟",
            options = listOf(
                "اسلوب معادله و اضافه استعاری (اشک کباب) — سبک هندی",
                "لف و نشر مرتب — سبک خراسانی",
                "قلب و تضاد — سبک عراقی",
                "جناس تام — دوره بازگشت ادبی"
            ),
            correctOptionIndex = 0,
            zeroLevelSolution = "۱. دو مصراع کاملاً مستقل‌اند و می‌توان بین آن‌ها عبارت «همان‌طور که» قرار داد: اظهار عجز پیش ستمگر ابلهی است، همان‌طور که چکیدن چربی (اشک) کباب آتش را شعله‌ورتر می‌کند  اسلوب معادله.\n۲. ترکیب «اشکِ کباب» و «طغیانِ آتش» استعاره مکنیه است و اسلوب معادله امضای قطعی «سبک هندی» (صائب تبریزی) است.",
            shortcutTechnique = "قرار گرفتن «همان‌طور که» بین دو مصراع مستقل = اسلوب معادله و سبک هندی!"
        ),
        QuizQuestionItem(
            id = "q_fonoon_4",
            subjectId = "fonoon",
            difficultyTag = "تست کنکور انسانی - مجاز و استعاره",
            questionText = "در مصراع «سرِ آن ندارد امشب که برآید آفتابی»، واژه «سر» و کل مصراع به ترتیب چه آرایه‌ای دارند؟",
            options = listOf(
                "«سر» مجاز از قصد و اندیشه است، و نسبت دادن آن به آفتاب استعاره مکنیه (تشخیص) است.",
                "«سر» استعاره مصرحه از خورشید است و حس‌آمیزی دارد.",
                "«آفتاب» مجاز از آسمان است و پارادوکس دارد.",
                "«امشب» جناس تام دارد."
            ),
            correctOptionIndex = 0,
            zeroLevelSolution = "۱. طبق رمز مجازهای کنکور، «سر» در عبارت «سرِ کاری داشتن» مجاز از قصد، نیت و اندیشه است (علاقه محلیه).\n۲. چون داشتنِ قصد و سر به «آفتاب» (غیرانسان) نسبت داده شده، آرایه تشخیص (استعاره مکنیه) نیز شکل گرفته است.",
            shortcutTechnique = "سرِ کاری داشتن = مجاز از قصد و نیت!"
        ),
        // Arabic
        QuizQuestionItem(
            id = "q_arabic_1",
            subjectId = "arabic",
            difficultyTag = "تست کنکور انسانی",
            questionText = "ترجمه دقیق عبارت «لا تَیْأَسوا مِنْ رَحمَةِ اللهِ، فَإِنَّ اللهَ یَغفِرُ الذُّنوبَ جَمیعاً» کدام است؟",
            options = listOf(
                "از رحمت خدا ناامید نشوید، چرا که خدا همه گناهان را می‌آمرزد.",
                "از رحمت خدا ناامید نشدند، زیرا خدا گناهان را بخشید.",
                "از رحمت خدا ناامید نمی‌شوید، همانا خدا گناهی را خواهد بخشید.",
                "نباید از رحمت خدا ناامید بشوند، زیرا خدا همه گناهان را بخشیده است."
            ),
            correctOptionIndex = 0,
            zeroLevelSolution = "۱. «لا تیأسوا» فعل نهی مخاطب است (ناامید نشوید)  گزینه‌های ۲ و ۳ و ۴ رد می‌شوند!\n۲. «یغفر» فعل مضارع است (می‌آمرزد) و «جمیعاً» حال است (همه را).",
            shortcutTechnique = "رد گزینه در ۵ ثانیه فقط با دیدن فعل اول جمله (لا تیأسوا = ناامید نشوید)!"
        ),
        QuizQuestionItem(
            id = "q_arabic_2",
            subjectId = "arabic",
            difficultyTag = "نهایی و کنکور",
            questionText = "در آیه شریفه «وَ کَلَّمَ اللهُ موسی تَکلیماً»، نقش واژه «تَکلیماً» چیست؟",
            options = listOf("مفعول مطلق تأکیدی", "مفعول مطلق نوعی", "حال مفرد", "تمییز"),
            correctOptionIndex = 0,
            zeroLevelSolution = "فعل جمله «کَلَّمَ» (از باب تفعیل) است و در ادامه مصدر هم‌ریشه همان باب یعنی «تَکلیماً» به صورت منصوب و بدون صفت یا مضاف‌الیه آمده است؛ پس مفعول مطلق تأکیدی است.",
            shortcutTechnique = "فعل + مصدر هم‌ریشه تنها = مفعول مطلق تأکیدی!"
        ),
        QuizQuestionItem(
            id = "q_arabic_3",
            subjectId = "arabic",
            difficultyTag = "تست کنکور انسانی - عربی دوازدهم",
            questionText = "در عبارت «لا رَیبَ فی ذلِکَ الکِتابِ، وَ یَقرَأُهُ المُؤمِنونَ خاشِعینَ»، به ترتیب نوع «لا» و نقش «خاشِعینَ» کدام است؟",
            options = listOf(
                "لای نفی جنس — حال مفرد (منصوب به نشانه ینَ)",
                "لای نهی — مفعول مطلق نوعی",
                "لای نافیه فعل — صفت مرفوع",
                "لای عاطفه — تمییز"
            ),
            correctOptionIndex = 0,
            zeroLevelSolution = "۱. بعد از «لا» اسم نکره مفرد بدون تنوین با فتحه («رَیبَ») آمده است  ۱۰۰٪ لای نفی جنس (هیچ شکی نیست).\n۲. «خاشِعینَ» اسم فاعل، نکره و منصوب است که حالتِ فاعلِ معرفه («المؤمنونَ») را در پاسخ به سوال «چگونه می‌خوانند؟» بیان می‌کند  حال مفرد!",
            shortcutTechnique = "لا رَیبَ = لای نفی جنس | پاسخِ «چگونه؟» با (ینَ) = حال مفرد!"
        ),
        QuizQuestionItem(
            id = "q_arabic_4",
            subjectId = "arabic",
            difficultyTag = "تست کنکور انسانی - اسلوب استثنا",
            questionText = "حرکت و نقش صحیح واژه داخل پرانتز در جمله «حَضَرَ الطُّلّابُ فی الصَّفِّ إِلّا (طالِب)» کدام است؟",
            options = listOf(
                "طالِباً — مستثنی منصوب (چون جمله قبل از إلّا مثبت و دارای مستثنی‌منه «الطلاب» است)",
                "طالِبٌ — فاعل مرفوع",
                "طالِبٍ — مضاف‌الیه مجرور",
                "طالِبانِ — خبر مرفوع"
            ),
            correctOptionIndex = 0,
            zeroLevelSolution = "جمله قبل از «إلّا» (حَضَرَ الطلابُ فی الصفِّ) مثبت است و مستثنی‌منه (الطلاب) در آن ذکر شده است (استثنای تامّ مثبت). در این حالت اسم بعد از إلّا واجب‌النصب است و باید حتماً منصوب («طالِباً») باشد.",
            shortcutTechnique = "جمله مثبت قبل از إلّا  کلمه بعد از إلّا ۱۰۰٪ منصوب (طالِباً)!"
        ),
        // English
        QuizQuestionItem(
            id = "q_eng_1",
            subjectId = "english",
            difficultyTag = "امتحان نهایی دوازدهم",
            questionText = "گزینه صحیح را انتخاب کنید: «The historical mosque _____ many centuries ago by great Iranian artists.»",
            options = listOf("was built", "built", "is building", "has built"),
            correctOptionIndex = 0,
            zeroLevelSolution = "۱. اول جمله «The historical mosque» (مسجد تاریخی = شیء) است و در ادامه کلمه «by» (توسط) آمده  جمله ۱۰۰٪ مجهول است!\n۲. در آخر جمله «many centuries ago» (قرن‌ها پیش = گذشته ساده) داریم.\n۳. فرمول مجهول گذشته ساده برای مفرد: was + P.P (was built).",
            shortcutTechnique = "شیء در اول جمله + by + ago ===> was / were + P.P!"
        ),
        QuizQuestionItem(
            id = "q_eng_2",
            subjectId = "english",
            difficultyTag = "امتحان نهایی و کنکور - Tag Question",
            questionText = "گزینه صحیح را برای دنباله سوالی انتخاب کنید: «mina and her sister hardly ever waste electricity at home, _____?»",
            options = listOf("do they", "don't they", "does she", "did they"),
            correctOptionIndex = 0,
            zeroLevelSolution = "۱. عبارت «hardly ever» (به‌ندرت) جمله را منفی می‌کند، پس دنباله سوالی (Tag) حتماً باید مثبت باشد! (گزینه don't they حذف می‌شود).\n۲. زمان فعل «waste» حال ساده است و فاعل «Mina and her sister» جمع (they) است  do they.",
            shortcutTechnique = "hardly ever = منفی پنهان  تگ مثبت (do they)!"
        ),
        QuizQuestionItem(
            id = "q_eng_3",
            subjectId = "english",
            difficultyTag = "امتحان نهایی دوازدهم - شرطی و موصولی",
            questionText = "گزینه صحیح را انتخاب کنید: «The scientist _____ invented this clean energy system said that if people _____ solar panels, pollution would decrease.»",
            options = listOf("who / used", "which / used", "whom / use", "who / will use"),
            correctOptionIndex = 0,
            zeroLevelSolution = "۱. قبل از جای خالی اول «The scientist» (دانشمند = انسان) و بعدش فعل «invented» آمده است  ضمیر موصولی فاعلی «who».\n۲. در بخش دوم «would decrease» (شرطی نوع دوم) داریم، پس در قسمت if باید گذشته ساده یعنی «used» بیاید.",
            shortcutTechnique = "انسان + فعل = who | دیدن would در شرطی نوع ۲ = فعل گذشته در بخش if!"
        ),
        // Farhangian
        QuizQuestionItem(
            id = "q_far_1",
            subjectId = "farhangian",
            difficultyTag = "کنکور فرهنگیان",
            questionText = "بر اساس کتاب «مهارت معلمی» استاد قرائتی، وقتی معلم پیش از ورود به کلاس مطالب را دسته‌بندی کرده و فیش‌برداری می‌کند، کدام مهارت را به کار بسته است؟",
            options = listOf(
                "آمادگی علمی و تنظیم طرح درس (مربوط به مرحله قبل از تدریس)",
                "فنون جلب توجه در حین تدریس",
                "ارزشیابی پایانی پس از تدریس",
                "شیوه جدال احسن با منتقدان"
            ),
            correctOptionIndex = 0,
            zeroLevelSolution = "طبق فصل اول کتاب مهارت معلمی، مطالعه، فیش‌برداری و دسته‌بندی مطالب در قالب طرح درس از مهم‌ترین وظایف معلم در مرحله «قبل از تدریس» است.",
            shortcutTechnique = "فیش‌برداری و طرح درس = مرحله قبل از تدریس (فصل ۱ کتاب قرائتی)."
        ),
        QuizQuestionItem(
            id = "q_far_2",
            subjectId = "farhangian",
            difficultyTag = "هوش و استعداد معلمی",
            questionText = "پژوهشگری می‌گوید: «مدارسی که کتابخانه بزرگ‌تری دارند، میانگین معدل بالاتری در امتحانات نهایی کسب کرده‌اند؛ پس خرید کتاب‌های بیشتر برای کتابخانه مدرسه مستقیماً باعث افزایش معدل دانش‌آموزان می‌شود.» کدام گزاره این استدلال را بیش از همه تضعیف می‌کند؟",
            options = listOf(
                "مدارس دارای کتابخانه بزرگ‌تر، بودجه بالاتری دارند و معلمان باتجربه‌تر و کلاس‌های تقویتی بیشتری نیز برگزار می‌کنند.",
                "برخی دانش‌آموزان کتاب‌های درسی را در منزل مطالعه می‌کنند.",
                "کتابخانه‌های مدارس در روزهای تعطیل بسته هستند.",
                "معدل امتحانات نهایی ۵۰ درصد در کنکور تاثیر دارد."
            ),
            correctOptionIndex = 0,
            zeroLevelSolution = "پژوهشگر ادعا کرده «بزرگی کتابخانه» علت مستقیم «معدل بالا» است. گزینه ۱ یک «عامل سوم (بودجه بالاتر و معلمان باتجربه‌تر)» را معرفی می‌کند که علت واقعی هر دو پدیده است و رابطه علی ادعاشده را تضعیف می‌کند.",
            shortcutTechnique = "معرفی عامل سوم (علت پنهان مشترک) قوی‌ترین روش تضعیف استدلال در هوش فرهنگیان است."
        ),
        QuizQuestionItem(
            id = "q_far_3",
            subjectId = "farhangian",
            difficultyTag = "تعلیم و تربیت اسلامی - قرائتی",
            questionText = "بر اساس کتاب «مهارت معلمی» استاد قرائتی، کدام مورد از آفت‌های اخلاص معلم است که در آیات قرآن با تعبیر «لا تُبطِلوا صَدَقاتِکُم بِالمَنِّ وَ الأَذی» به آن هشدار داده شده است؟",
            options = listOf(
                "منت گذاشتن بر شاگردان و تحقیر یا آزار زبانی آنان پس از آموزش",
                "استفاده از مثال‌های ساده و حسی برای تفهیم مطالب عمیق",
                "تقسیم‌بندی موضوعات درسی بر روی تخته کلاس",
                "مشورت با همکاران آموزشی درباره روش تدریس"
            ),
            correctOptionIndex = 0,
            zeroLevelSolution = "طبق فصل اول و سوم کتاب مهارت معلمی استاد قرائتی، «مَنّ و أذی» یعنی منت گذاشتن بر متعلمان و تحقیر آنان که اجر معنوی تعلیم و تربیت را همچون صدقه باطل می‌سازد.",
            shortcutTechnique = "مَنّ و أذی = منت گذاشتن و آزار زبانی شاگرد (آفت اخلاص معلم)."
        ),
        QuizQuestionItem(
            id = "q_far_4",
            subjectId = "farhangian",
            difficultyTag = "دینی و معارف فرهنگیان",
            questionText = "آیه شریفه «وَ لَو کُنتَ فَظّاً غَلیظَ القَلبِ لَانفَضّوا مِن حَولِکَ» بر کدام ویژگی شخصیتی پیامبر اکرم (ص) به عنوان الگوی معلم تراز اسلامی تأکید دارد؟",
            options = listOf(
                "نرم‌خویی، مهربانی و پرهیز از تندخویی و سنگدلی در جذب مخاطبان",
                "برنامه‌ریزی زمانی پیش از هجرت به مدینه",
                "اعجاز علمی قرآن در بیان اسرار آفرینش",
                "ضرورت فیش‌برداری از متون تاریخی"
            ),
            correctOptionIndex = 0,
            zeroLevelSolution = "واژه «فَظّاً غَلیظَ القلب» به معنی تندخو و سخت‌دل است؛ خداوند به پیامبر (ص) می‌فرماید به رحمت الهی با مردم نرم‌خو شدی و اگر تندخو بودی از اطرافت پراکنده می‌شدند.",
            shortcutTechnique = "فظّاً غلیظ القلب = تندخویی (که باعث پراکندگی شاگردان می‌شود)."
        ),
        // Philosophy & Logic
        QuizQuestionItem(
            id = "q_phil_1",
            subjectId = "philosophy",
            difficultyTag = "کنکور انسانی - منطق دهم",
            questionText = "اگر قضیه «هیچ انسانی پرنده نیست» صادق باشد، وضعیت قضیه «بعضی پرنده‌ها انسان نیستند» از نظر عکس مستوی و ارزش چیست؟",
            options = listOf(
                "صادق است (زیرا عکس مستوی سالبه کلی، سالبه کلی «هیچ پرنده‌ای انسان نیست» است که تداخلِ جزئیِ آن نیز صادق است)",
                "کاذب است",
                "نامعلوم است",
                "سالبه کلی عکس مستوی ندارد"
            ),
            correctOptionIndex = 0,
            zeroLevelSolution = "۱. قضیه «هیچ انسانی پرنده نیست» سالبه کلی (س.ک) و صادق است.\n۲. عکس مستوی س.ک می‌شود «هیچ پرنده‌ای انسان نیست» (س.ک صادق).\n۳. وقتی کلی (هیچ پرنده‌ای انسان نیست) صادق باشد، جزئی زیرش در تداخل یعنی «بعضی پرنده‌ها انسان نیستند» نیز ۱۰۰٪ صادق است!",
            shortcutTechnique = "س.ک صادق ---> عکس مستوی س.ک صادق ---> سالبه جزئی زیرش هم صادق!"
        ),
        QuizQuestionItem(
            id = "q_phil_2",
            subjectId = "philosophy",
            difficultyTag = "کنکور انسانی - فلسفه دوازدهم",
            questionText = "بر اساس حکمت متعالیه ملاصدرا، چرا خداوند (واجب‌الوجود) «ماهیت» ندارد؟",
            options = listOf(
                "زیرا ماهیت به معنای حد، مرز و قالب است، در حالی که واجب‌الوجود هستی نامتناهی و بی‌حدومرز است.",
                "زیرا خداوند وجود اعتباری دارد.",
                "زیرا ماهیت خداوند همان جسم لطیف است.",
                "زیرا در مکتب ملاصدرا اصالت با ماهیت است."
            ),
            correctOptionIndex = 0,
            zeroLevelSolution = "طبق متد «نور و شیشه رنگی»، ماهیت همان قاب و مرزی است که وجودهای محدود (ممکنات) را محدود می‌کند؛ چون خداوند وجود صرف و نامتناهی است، هیچ حد و قالبی ندارد، بنابراین فاقد ماهیت است (ماهیّته إنّیّته).",
            shortcutTechnique = "ماهیت = حد و مرز | واجب‌الوجود = بی‌مرز و نامتناهی ---> واجب‌الوجود ماهیت ندارد!"
        ),
        QuizQuestionItem(
            id = "q_phil_3",
            subjectId = "logic",
            difficultyTag = "کنکور انسانی - منطق دهم (قیاس)",
            questionText = "در قیاس «هر فلزی رسانا است؛ هیچ چوبی رسانا نیست؛ پس هیچ چوبی فلز نیست»، حد وسط کدام است و این قیاس از کدام شکل است؟",
            options = listOf(
                "حد وسط «رسانا» است که در هر دو مقدمه محمول واقع شده — شکل دوم قیاس",
                "حد وسط «فلز» است — شکل اول قیاس",
                "حد وسط «چوب» است — شکل سوم قیاس",
                "این استدلال قیاس استثنایی اتصالی است"
            ),
            correctOptionIndex = 0,
            zeroLevelSolution = "کلمه‌ای که در هر دو مقدمه تکرار شده و در نتیجه حذف شده است «رسانا» (حد وسط) است. چون «رسانا» هم در مقدمه اول (هر فلزی رسانا است) و هم در مقدمه دوم (هیچ چوبی رسانا نیست) در جایگاه دوم (محمول) قرار گرفته، این قیاس از «شکل دوم» است (که همیشه نتیجه‌اش سالبه است).",
            shortcutTechnique = "حد وسط در آخرِ هر دو مقدمه (محمول در هر دو) = شکل دوم قیاس!"
        ),
        QuizQuestionItem(
            id = "q_phil_4",
            subjectId = "philosophy",
            difficultyTag = "کنکور انسانی - فلسفه دوازدهم (علیت)",
            questionText = "از دیدگاه فلسفه اسلامی و ملاصدرا، معنای دقیق اینکه «معلول عین ربط و وابستگی به علت است» چیست؟",
            options = listOf(
                "معلول نه تنها در پیدایش (حدوث)، بلکه در بقا و ادامه هستی نیز لحظه‌به‌لحظه نیازمند علت ایجادی است.",
                "معلول تنها در لحظه پیدایش به علت نیاز دارد و پس از آن مستقل است (مثال معمار و ساختمان).",
                "رابطه علت و معلول صرفاً عادت ذهنی انسان از تداعی دو پدیده است (دیدگاه هیوم).",
                "علت و معلول هیچ سنخیتی با یکدیگر ندارند."
            ),
            correctOptionIndex = 0,
            zeroLevelSolution = "برخلاف تصور عامیانه (که علت را مثل معمار و ساختمان فقط برای حدوث لازم می‌داند)، در فلسفه اسلامی و حکمت متعالیه، وجود معلول عینِ وابستگی به علت است؛ یعنی اگر لحظه‌ای فیض علت قطع شود، معلول معدوم می‌گردد.",
            shortcutTechnique = "معلول = عین ربط (نیازمند به علت هم در حدوث و هم در بقا)!"
        ),
        QuizQuestionItem(
            id = "q_math_konkoor_80",
            subjectId = "math",
            difficultyTag = "کنکور انسانی - میان‌بر ۸۰٪ (آمار و احتمال)",
            questionText = "اگر واریانس داده‌های آماری برابر ۶ باشد و همه داده‌ها را در ۳− ضرب کرده و با ۱۰ جمع کنیم (y = −3x + 10)، واریانس داده‌های جدید چقدر است؟",
            options = listOf(
                "۵۴",
                "۱۸−",
                "۶۴",
                "۲۸"
            ),
            correctOptionIndex = 0,
            zeroLevelSolution = "گام ۱ (قانون طلایی واریانس) | جمع شدن با عدد ثابت (+۱۰) هیچ تاثیری روی پراکندگی و واریانس ندارد (خط بزن!).\nگام ۲ (اثر ضرب در واریانس) | ضرب شدن داده‌ها در k = −3 باعث می‌شود واریانس در k² = (−3)² = 9 ضرب شود.\nگام ۳ (محاسبه نهایی) | واریانس جدید = 9 × 6 = 54.",
            shortcutTechnique = "σ²_جدید = k² × σ²_قدیم ⟹ (−۳)² × ۶ = ۹ × ۶ = ۵۴ (جمع با ۱۰ بی‌اثر است)!"
        ),
        QuizQuestionItem(
            id = "q_arooz_konkoor_100",
            subjectId = "arooz",
            difficultyTag = "کنکور انسانی - تسلط ۱۰۰٪ عروض و قافیه",
            questionText = "در بیت «بهانه مگیر ای دل از روزگار // که چرخِ فلک راست لیل و نهار» یا بیت هم‌قافیه با واژگان «بهانه» و «ترانه»، حروف قافیه و تبصره آن کدام است؟",
            options = listOf(
                "در واژگان مختوم به «ه» بیان حرکت (ـِ) مانند «بهانه و ترانه»، مصوت کوتاه پایانی به تنهایی قافیه نمی‌سازد و حروف قافیه «آنه (مصوت بلند آ + ن + ه)» است.",
                "فقط حرف «ه» پایانی قافیه است و تابع قاعده ۱ است.",
                "واژه‌های بهانه و ترانه ردیف هستند نه قافیه.",
                "فقط حرف «ن» صامت قافیه است."
            ),
            correctOptionIndex = 0,
            zeroLevelSolution = "گام ۱ (تشخیص صدای پایانی) | در «بهانه» و «ترانه»، حرف «ه» پایانی تلفظِ مصوت کوتاه «ـِ» دارد (بهانِ / ترانِ).\nگام ۲ (تبصره ه غیرملفوظ) | مصوت کوتاه «ـِ» در پایان واژه به تنهایی قافیه نمی‌سازد؛ پس یک پله به عقب برمی‌گردیم تا به مصوتِ قبل از صامت برسیم.\nگام ۳ (نتیجه) | بخش مشترک از مصوت قبلی عبارت است از «آنه» (آ + ن + ـِ)!",
            shortcutTechnique = "واژه قافیه مختوم به «ه (ـِ)» ⟹ برگرد به مصوتِ قبل از آن (در بهانه/ترانه = آنه)!"
        ),
        QuizQuestionItem(
            id = "q_fonoon_konkoor_100",
            subjectId = "fonoon",
            difficultyTag = "کنکور انسانی - آرایه‌های فوق‌پیشرفته",
            questionText = "در کدام گزینه به ترتیب «اضافه تشبیهی»، «اضافه استعاری» و «اضافه مجازی» به درستی تفکیک شده‌اند؟",
            options = listOf(
                "چراغِ دانش (تشبیهی) — دستِ روزگار (استعاری) — سرِ کوه (مجازی)",
                "دستِ روزگار (تشبیهی) — چراغِ دانش (استعاری) — کتابِ علی (مجازی)",
                "لبِ دریا (تشبیهی) — آتشِ عشق (استعاری) — چشمِ آسمان (مجازی)",
                "درختِ دوستی (استعاری) — دامنِ صحرا (تشبیهی) — خونِ رزان (حقیقی)"
            ),
            correctOptionIndex = 0,
            zeroLevelSolution = "گام ۱ | در «چراغِ دانش»، دانش (مشبه) مثل چراغ (مشبهبه) است (هر دو طرف تشبیه حاضرند = اضافه تشبیهی).\nگام ۲ | در «دستِ روزگار»، عضو انسان (دست) به غیرانسان (روزگار) نسبت داده شده و مشبهبه (انسان) حذف شده است (= اضافه استعاری مکنیه).\nگام ۳ | در «سرِ کوه»، کلمه «سر» در معنای مجازیِ «بالا و قله» به کار رفته است (= اضافه مجازی).",
            shortcutTechnique = "برعکس کن + مثل = اضافه تشبیهی | عضو + غیرانسان = اضافه استعاری!"
        ),
        QuizQuestionItem(
            id = "q_arabic_konkoor_100",
            subjectId = "arabic",
            difficultyTag = "کنکور انسانی - تسلط ۱۰۰٪ عربی (اعلال و منصوبات)",
            questionText = "در عبارت قرآنی «وَ اذْکُرْ رَبَّکَ کَثیراً» و «قُلِ الْحَقَّ وَ لَوْ کانَ مُرّاً»، نوع «کَثیراً»، نوع اعلال در «قُلْ» و نقش «مُرّاً» به ترتیب کدام است؟",
            options = listOf(
                "مفعول مطلق (یا قید) — اعلال به حذف (حرف عله از اجوف امر مجزوم افتاده) — خبرِ کانَ منصوب",
                "حال مفرد — اعلال به قلب — اسمِ کانَ مرفوع",
                "تمییز — بدون اعلال — مفعول‌به",
                "مستثنی — اعلال به اسکان — صفت"
            ),
            correctOptionIndex = 0,
            zeroLevelSolution = "گام ۱ | فعل «قُلْ» امر از ریشه «قَوَلَ (قالَ - یَقولُ)» است که در حالت جزم، حرف عله «و» از وسط آن حذف شده است (اعلال به حذف).\nگام ۲ | بعد از فعل ناقصه «کانَ»، اسم آن ضمیر مستتر «هُوَ (الحق)» است و «مُرّاً (تلخ)» خبرِ کانَ و منصوب است!",
            shortcutTechnique = "قالَ ⟹ قُلْ (اعلال به حذف حرف عله وسط) | بعد از کانَ اسمِ منصوب = خبرِ کانَ!"
        ),
        QuizQuestionItem(
            id = "q_english_konkoor_100",
            subjectId = "english",
            difficultyTag = "کنکور و نهایی دوازدهم - تسلط ۱۰۰٪ زبان",
            questionText = "گزینه صحیح را برای تکمیل جمله زیر انتخاب کنید:\n«Reza _____ any junk food before he went on a diet, _____?»",
            options = listOf(
                "had hardly eaten — had he",
                "has hardly eaten — hasn't he",
                "hardly ate — didn't he",
                "was hardly eaten — wasn't he"
            ),
            correctOptionIndex = 0,
            zeroLevelSolution = "گام ۱ (تشخیص زمان با کلمه ربط before) | بعد از before گذشته ساده (went) آمده است، پس قبل از آن باید «ماضی بعید (had + P.P)» بیاید ⟹ had hardly eaten.\nگام ۲ (تشخیص تگ سوالی با قید منفی پنهان) | چون کلمه «hardly (به ندرت)» جمله را منفی کرده است، دنباله سوالی (Tag) حتماً باید مثبت باشد ⟹ had he?",
            shortcutTechnique = "ماضی بعید + before + گذشته ساده | دیدن hardly = تگ مثبت (had he)!"
        ),
        QuizQuestionItem(
            id = "q_far_full_package",
            subjectId = "farhangian",
            difficultyTag = "فول‌پکیج کنکور انسانی — اقتصاد و روان‌شناسی",
            questionText = "۱) اگر کارخانه‌ای در داخل شیراز توسط یک شرکت خارجی محصول نهایی تولید کند، جزو کدام شاخص ایران است؟ و ۲) خوردن قرص مسکن برای قطع شدن سردرد در روان‌شناسی چه نوع یادگیری است؟",
            options = listOf(
                "۱) تولید ناخالص داخلی (GDP) ایران — ۲) تقویت منفی",
                "۱) تولید ناخالص ملی (GNP) ایران — ۲) تنبیه منفی",
                "۱) تولید ناخالص ملی (GNP) ایران — ۲) تقویت مثبت",
                "۱) کالای واسطه‌ای — ۲) شرطی‌شدن کلاسیک"
            ),
            correctOptionIndex = 0,
            zeroLevelSolution = "گام ۱ (اقتصاد دهم) | ملاک تولید ناخالص داخلی (GDP) «مرز جغرافیایی کشور» است؛ چون کارخانه داخل خاک ایران (شیراز) است، جزو GDP ایران محسوب می‌شود.\nگام ۲ (روان‌شناسی یازدهم) | چون خوردن مسکن باعث «حذف یک عامل ناخوشایند (سردرد)» و افزایش رفتار در آینده می‌شود، نمونه‌ای از «تقویت منفی» است!",
            shortcutTechnique = "داخل مرزهای ایران = GDP | حذف عامل آزاردهنده (سردرد/بوق) = تقویت منفی!"
        ),
        // History & Geography Quiz Questions (تاریخ و جغرافیا جامع کنکور ۱۴۰۶)
        QuizQuestionItem(
            id = "q_hist_geo_1",
            subjectId = "history",
            difficultyTag = "کنکور انسانی ۱۴۰۶ — تاریخ ۱ دهم (چاپ ۱۴۰۳)",
            questionText = "کدام مورد از ویژگی‌های حکومت «اشکانیان» در مقایسه با هخامنشیان و ساسانیان است؟",
            options = listOf(
                "اداره غیرمتمرکز ایالات به شیوه ملوک‌الطوایفی و وجود مجلس مهستان برای محدود کردن قدرت شاه",
                "تأسیس سازمان ساتراپی و استفاده از بازرسان موسوم به چشم و گوش شاه",
                "تمرکز شدید قدرت در پایتخت و پیوند رسمی دین زرتشتی با حکومت",
                "انتقال پایتخت از تبریز به قزوین و سپس اصفهان"
            ),
            correctOptionIndex = 0,
            zeroLevelSolution = "در دوره اشکانی (پارت‌ها)، حکومت به صورت ملوک‌الطوایفی (پادشاهان محلی نیمه‌مستقل) اداره می‌شد و مجلس «مهستان» (متشکل از بزرگان و مغان) در انتخاب و محدود کردن اختیارات شاه نقش داشت.",
            shortcutTechnique = "اشکانیان = مجلس مهستان + ملوک‌الطوایفی!"
        ),
        QuizQuestionItem(
            id = "q_hist_geo_2",
            subjectId = "history",
            difficultyTag = "کنکور انسانی ۱۴۰۶ — تاریخ ۲ و ۳ (چاپ ۱۴۰۴ و ۱۴۰۵)",
            questionText = "به ترتیب: ۱) تأسیس «مدارس نظامیه» در کدام دوره رخ داد؟ و ۲) جدایی کامل «هرات» از ایران پیامد کدام عهدنامه بود؟",
            options = listOf(
                "۱) دوره سلجوقیان (توسط خواجه نظام‌الملک توسی) — ۲) عهدنامه پاریس (در زمان ناصرالدین‌شاه قاجار)",
                "۱) دوره سامانیان (توسط بلعمی) — ۲) عهدنامه ترکمانچای (در زمان فتحعلی‌شاه)",
                "۱) دوره غزنویان — ۲) عهدنامه گلستان",
                "۱) دوره صفویان — ۲) قرارداد ۱۹۰۷"
            ),
            correctOptionIndex = 0,
            zeroLevelSolution = "۱. خواجه نظام‌الملک توسی وزیر نامدار سلجوقیان، مدارس نظامیه را در بغداد، نیشابور و اصفهان تأسیس کرد.\n۲. در دوره ناصرالدین‌شاه قاجار، انگلستان با اشغال خارک و بوشهر، عهدنامه پاریس را تحمیل کرد که منجر به جدایی هرات و افغانستان از ایران شد.",
            shortcutTechnique = "نظامیه = سلجوقیان | جدایی هرات = عهدنامه پاریس (ناصرالدین‌شاه)!"
        ),
        QuizQuestionItem(
            id = "q_hist_geo_3",
            subjectId = "geography",
            difficultyTag = "کنکور انسانی ۱۴۰۶ — جغرافیا ۱، ۲ و ۳ (چاپ ۱۴۰۳ تا ۱۴۰۵)",
            questionText = "۱) تنها حوضه آبریز باز ایران کدام است؟ ۲) مقاوم‌سازی ساختمان‌ها قبل از زلزله مربوط به کدام مرحله از مدیریت بحران است؟ و ۳) کشور «شیلی» چه شکل ژئوپلیتیکی دارد؟",
            options = listOf(
                "۱) خلیج فارس و دریای عمان — ۲) پیشگیری و کاهش اثرات — ۳) طویل و کشیده",
                "۱) دریای خزر — ۲) مقابله — ۳) فشرده و گرد",
                "۱) فلات مرکزی — ۲) بازسازی — ۳) تکه‌تکه و مجمع‌الجزایری",
                "۱) دریاچه ارومیه — ۲) آمادگی — ۳) محیطی"
            ),
            correctOptionIndex = 0,
            zeroLevelSolution = "۱. تنها حوضه آبریز باز ایران که به اقیانوس راه دارد «خلیج فارس و دریای عمان» است.\n۲. مقاوم‌سازی بناها پیش از وقوع حادثه برای کم کردن خسارت، مرحله «پیشگیری» است (مانور و آموزش مرحله آمادگی است).\n۳. کشور شیلی در غرب آمریکای جنوبی شکلی «طویل و کشیده» دارد.",
            shortcutTechnique = "حوضه باز = خلیج فارس | مقاوم‌سازی = پیشگیری | شیلی = طویل و کشیده!"
        ),
        // Sociology, Psychology & Economics Quiz Questions (اقتصاد، روان‌شناسی و جامعه‌شناسی کنکور ۱۴۰۶)
        QuizQuestionItem(
            id = "q_soc_psych_econ_1",
            subjectId = "economics",
            difficultyTag = "کنکور انسانی ۱۴۰۶ — مسئله اقتصاد دهم (چاپ ۱۴۰۳)",
            questionText = "علی کارمند بانکی با حقوق ماهانه ۱۵ میلیون تومان بود. او استعفا داد و در مغازه شخصی خودش (که قبلاً ماهی ۱۰ میلیون تومان اجاره داده بود) یک تولیدی پوشاک راه انداخت. اگر در ماه اول درآمد فروش او ۹۰ میلیون تومان و هزینه پارچه، برق و دستمزد کارگر ۵۰ میلیون تومان باشد، «سود حسابداری» و «سود اقتصادی» او به ترتیب چند میلیون تومان است؟",
            options = listOf(
                "سود حسابداری ۴۰ میلیون تومان — سود اقتصادی ۱۵ میلیون تومان",
                "سود حسابداری ۱۵ میلیون تومان — سود اقتصادی ۴۰ میلیون تومان",
                "سود حسابداری ۶۵ میلیون تومان — سود اقتصادی ۲۵ میلیون تومان",
                "سود حسابداری ۴۰ میلیون تومان — سود اقتصادی ۲۵ میلیون تومان"
            ),
            correctOptionIndex = 0,
            zeroLevelSolution = "گام ۱ (سود حسابداری) | درآمد کل (۹۰) منهای هزینه‌های آشکار (۵۰) = ۴۰ میلیون تومان سود حسابداری.\nگام ۲ (هزینه فرصت / پنهان) | حقوق بانک (۱۵) + اجاره مغازه شخصی (۱۰) = ۲۵ میلیون تومان.\nگام ۳ (سود اقتصادی) | سود حسابداری (۴۰) منهای هزینه فرصت (۲۵) = ۱۵ میلیون تومان سود اقتصادی!",
            shortcutTechnique = "سود حسابداری = ۹۰ − ۵۰ = ۴۰ | سود اقتصادی = ۴۰ − (۱۵ + ۱۰) = ۱۵!"
        ),
        QuizQuestionItem(
            id = "q_soc_psych_econ_2",
            subjectId = "psychology",
            difficultyTag = "کنکور انسانی ۱۴۰۶ — روان‌شناسی یازدهم (چاپ ۱۴۰۴)",
            questionText = "۱) اگر فردی پس از اسباب‌کشی به خانه جدید، هنگام پر کردن فرم بانکی به اشتباه کد پستی خانه قبلی خود را بنویسد و کد پستی «جدید» را فراموش کند، دچار چه نوع تداخلی شده است؟ و ۲) استفاده نکردن از سکه فلزی برای باز کردن پیچ، نشانه کدام مانع حل مسئله است؟",
            options = listOf(
                "۱) تداخل پیش‌گستر — ۲) تثبیت کارکردی",
                "۱) تداخل پس‌گستر — ۲) آمایه ذهنی",
                "۱) تداخل پس‌گستر — ۲) تثبیت کارکردی",
                "۱) فراموشی موقت — ۲) تفکر واگرا"
            ),
            correctOptionIndex = 0,
            zeroLevelSolution = "۱. چون کد پستیِ «جدید» فراموش شده و خاطرهٔ قدیمی جلو آمده و مانع یادآوری جدید شده است، «تداخل پیش‌گستر» رخ داده است.\n۲. محدود کردن کاربرد یک شیء (سکه) فقط به کارکرد قبلی آن (خرید کردن) و ندیدن کاربرد جدیدش (پیچ‌گوشتی)، «تثبیت کارکردی» است.",
            shortcutTechnique = "جدید یادت رفت = پیش‌گستر | قفل شدن روی کاربردِ یک وسیله = تثبیت کارکردی!"
        ),
        QuizQuestionItem(
            id = "q_soc_psych_econ_3",
            subjectId = "sociology",
            difficultyTag = "کنکور انسانی ۱۴۰۶ — جامعه‌شناسی ۱، ۲ و ۳ (چاپ ۱۴۰۳ تا ۱۴۰۵)",
            questionText = "۱) عمیق‌ترین لایه فرهنگ کدام است؟ و ۲) کدام رویکرد جامعه‌شناسی با تأکید بر «روش تفهمی (کیفی)» به دنبال کشف معنای ذهنی کنش از دیدگاه خود کنشگران است؟",
            options = listOf(
                "۱) عقاید و باورهای بنیادین (جهان‌بینی) — ۲) رویکرد تفسیری (ماکس وبر)",
                "۱) نمادها و نشانه‌ها — ۲) رویکرد تبیینی یا اثبات‌گرا (اگوست کنت)",
                "۱) هنجارهای رفتاری — ۲) رویکرد انتقادی (مکتب فرانکفورت)",
                "۱) ارزش‌های اجتماعی — ۲) رویکرد پوزیتیویستی (امیل دورکیم)"
            ),
            correctOptionIndex = 0,
            zeroLevelSolution = "۱. لایه‌های فرهنگ از سطح به عمق عبارت‌اند از: نمادها ⟵ هنجارها ⟵ ارزش‌ها ⟵ عقاید و باورهای بنیادین (عمیق‌ترین لایه و هسته فرهنگ).\n۲. رویکرد تفسیری به پیشگامی ماکس وبر با روش تفهمی به دنبال درک نیت و معنای ذهنی کنشگران است.",
            shortcutTechnique = "عمیق‌ترین لایه فرهنگ = عقاید | روش تفهمی و معنا = رویکرد تفسیری (ماکس وبر)!"
        ),
        QuizQuestionItem(
            id = "q_dini_konkoor_1",
            subjectId = "dini",
            difficultyTag = "نهایی دوازدهم و کنکور فرهنگیان ۱۴۰۶ — دین و زندگی",
            questionText = "آیه شریفه «وَ مَنْ یَتَوَکَّلْ عَلَی اللَّهِ فَهُوَ حَسْبُهُ» به کدام مرتبه از توحید اشاره دارد و منظور از «توفّی» در قرآن کریم چیست؟",
            options = listOf(
                "توحید افعالی (زیرا توکل نتیجهٔ باور به ربوبیت و کارگردانی خداوند در جهان است) — دریافت کامل روح انسان توسط فرشتگان در هنگام مرگ",
                "توحید ذاتی — نابودی کامل انسان پس از مرگ",
                "توحید صفاتی — بازگشت روح به بدن در عالم دنیا",
                "شرک افعالی — آمرزش گناهان در عالم برزخ"
            ),
            correctOptionIndex = 0,
            zeroLevelSolution = "۱. توکل یعنی تکیه کردن بر خدا در کارها؛ چون معتقدیم تنها مؤثر و مدبّر واقعی جهان خداست، پس ریشه در «توحید افعالی» دارد.\n۲. «توفّی» به معنای دریافت کامل و تحویل گرفتنِ روح انسان توسط فرشتگان در لحظه مرگ است و نشان می‌دهد مرگ نابودی نیست.",
            shortcutTechnique = "توکل و تدبیر جهان = توحید افعالی | توفّی = دریافت کامل روح در مرگ!"
        ),
        QuizQuestionItem(
            id = "q_farsi_final_1",
            subjectId = "farsi",
            difficultyTag = "امتحان نهایی دوازدهم (۲۰ قطعی) — دستور زبان فارسی",
            questionText = "در جمله «دانش‌آموزِ کوشایِ مدرسه به موفقیتِ بزرگ رسید»، نقش دستوری واژه‌های «کوشا»، «مدرسه» و «موفقیت» به ترتیب کدام است؟",
            options = listOf(
                "کوشا: صفت بیانی — مدرسه: مضاف‌الیه — موفقیت: متمم",
                "کوشا: مضاف‌الیه — مدرسه: صفت بیانی — موفقیت: مفعول",
                "کوشا: مسند — مدرسه: متمم — موفقیت: نهاد",
                "کوشا: صفت پیشین — مدرسه: بدل — موفقیت: مسند"
            ),
            correctOptionIndex = 0,
            zeroLevelSolution = "۱. بین «دانش‌آموز» و «کوشا» کلمهٔ «است» بگذار: دانش‌آموز کوشا است (معنی می‌دهد ⟹ صفت بیانی).\n۲. بین «دانش‌آموز» و «مدرسه» کلمهٔ «است» بگذار: دانش‌آموز مدرسه است (معنی نمی‌دهد ⟹ مضاف‌الیه).\n۳. «موفقیت» بلافاصله بعد از حرف اضافه «به» آمده است ⟹ متمم!",
            shortcutTechnique = "تست «است» جواب داد = صفت بیانی | جواب نداد = مضاف‌الیه | بعد از حرف اضافه (به) = متمم!"
        ),
        QuizQuestionItem(
            id = "q_gen_humanities_1",
            subjectId = "general_humanities",
            difficultyTag = "امتحان نهایی و دروس عمومی انسانی — سلامت، محیط زیست و سواد رسانه‌ای",
            questionText = "۱) اگر فردی دارای قد ۲ متر و وزن ۱۰۰ کیلوگرم باشد، نمایه توده بدنی (BMI) او چقدر است؟ و ۲) استفاده از عبارت «همه مردم ایران این محصول را می‌خرند، شما چطور؟» کدام فن اقناع در سواد رسانه‌ای است؟",
            options = listOf(
                "۱) BMI برابر ۲۵ — ۲) فن همنوایی (همرنگ جماعت شدن)",
                "۱) BMI برابر ۵۰ — ۲) فن شهادت (استفاده از افراد مشهور)",
                "۱) BMI برابر ۲۰ — ۲) فن کلی‌گویی درخشان",
                "۱) BMI برابر ۳۰ — ۲) فن تداعی و تکرار"
            ),
            correctOptionIndex = 0,
            zeroLevelSolution = "۱. فرمول BMI = وزن تقسیم بر (قد به توان ۲) ⟹ ۱۰۰ ÷ (۲ × ۲) = ۱۰۰ ÷ ۴ = ۲۵.\n۲. وقتی پیام رسانه‌ای می‌گوید «همه دارند این کار را می‌کنند پس تو هم عقب نمان»، از «فن همنوایی» استفاده کرده است.",
            shortcutTechnique = "BMI = وزن ÷ قد² (۱۰۰ ÷ ۴ = ۲۵) | «همه می‌خرند» = فن همنوایی!"
        )
    ) + MasteryEncyclopedia1405.quizQuestions + ZeroToHundredMasterCurriculum1405.quizQuestions)
        .distinctBy { it.id }
    }
}
