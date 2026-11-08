package com.example.data.curriculum

// مسیر خطی یک تا صد: از صفر مطلق تا آمادگی کنکور انسانی و فرهنگیان ۱۴۰۶.
// هر قدم چند درس موجود را با شناسه واقعی به هم وصل می کند و مینی چک آن
// همان مینی چک اولین درس قدم است. قدم هایی که هنوز درس واقعی ندارند
// وضعیت GAP دارند و lessonIds و checkLessonId آن ها خالی است.
// هیچ وقت بر اساس شمارش قدم ها کامل ادعا نمی شود: کامل بودن یعنی
// تطبیق متن هر درس با کتاب رسمی، نه تعداد درس.

enum class LearningPathStatus {
    READY,
    GAP
}

data class LearningPathStep(
    val stepNumber: Int,
    val title: String,
    val subtitle: String,
    val lessonIds: List<String>,
    val checkLessonId: String,
    val status: LearningPathStatus,
    val gapNote: String
)

object LearningPath1406 {
    val steps: List<LearningPathStep> = listOf(
        LearningPathStep(
            stepNumber = 1,
            title = "روش مطالعه از صفر",
            subtitle = "اول یاد بگیر چطور بخوانی، بعد برو سراغ درس ها",
            lessonIds = listOf(
                "methods1406-01-sq3r",
                "methods1406-05-active-study",
                "methods1406-10-sleep-energy"
            ),
            checkLessonId = "methods1406-01-sq3r",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 2,
            title = "حساب پایه: جمع و تفریق و ضرب",
            subtitle = "چهار عمل اصلی از اول، آرام و قدم به قدم",
            lessonIds = listOf(
                "found1406-math-01-add-subtract",
                "found1406-math-02-multiplication-fractions"
            ),
            checkLessonId = "found1406-math-01-add-subtract",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 3,
            title = "تقسیم و عددهای منفی",
            subtitle = "تقسیم با باقی مانده و منفی ها روی خط عددها",
            lessonIds = listOf(
                "found1406-math-03-division",
                "found1406-math-04-negative-numbers"
            ),
            checkLessonId = "found1406-math-03-division",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 4,
            title = "کسر و اعشار",
            subtitle = "کسر یعنی تکه ای از یک چیز کامل؛ اعشار همان کسر با شکل دیگر است",
            lessonIds = listOf(
                "found1406-math-05-fractions-1",
                "found1406-math-06-fractions-2",
                "found1406-math-07-fractions-3",
                "found1406-math-08-decimals-1",
                "found1406-math-09-decimals-2"
            ),
            checkLessonId = "found1406-math-05-fractions-1",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 5,
            title = "نسبت و درصد",
            subtitle = "نسبت یعنی مقایسه دو چیز؛ درصد یعنی از هر صد تا چند تا",
            lessonIds = listOf(
                "found1406-math-10-ratio-proportion",
                "found1406-math-11-percent-1",
                "found1406-math-12-percent-2"
            ),
            checkLessonId = "found1406-math-10-ratio-proportion",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 6,
            title = "توان و جبر",
            subtitle = "توان یعنی ضرب تکراری؛ جبر یعنی حرف به جای عدد",
            lessonIds = listOf(
                "found1406-math-13-powers-roots",
                "found1406-math-14-algebra-1",
                "found1406-math-15-equations-1"
            ),
            checkLessonId = "found1406-math-13-powers-roots",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 7,
            title = "هندسه پایه",
            subtitle = "زاویه و شکل و محیط و مساحت و حجم با چشم و دست",
            lessonIds = listOf(
                "found1406-math-16-geometry-1",
                "found1406-math-17-geometry-2",
                "found1406-math-18-geometry-3",
                "found1406-math-19-coordinates"
            ),
            checkLessonId = "found1406-math-16-geometry-1",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 8,
            title = "آمار و احتمال پایه",
            subtitle = "جدول و نمودار خواندن، میانگین گرفتن و شانس حساب کردن",
            lessonIds = listOf(
                "found1406-math-21-statistics-1",
                "found1406-math-22-statistics-2",
                "found1406-math-23-probability-1",
                "found1406-math-24-sets"
            ),
            checkLessonId = "found1406-math-21-statistics-1",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 9,
            title = "خواندن فارسی",
            subtitle = "روان خواندن، نشانه ها و پیام اصلی متن",
            lessonIds = listOf(
                "found1406-farsi-01-alphabet-reading",
                "found1406-farsi-03-reading-signs",
                "found1406-farsi-09-comprehension-1"
            ),
            checkLessonId = "found1406-farsi-01-alphabet-reading",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 10,
            title = "املا و دستور فارسی",
            subtitle = "حروف هم صدا، تشدید و تنوین، اسم و فعل و صفت",
            lessonIds = listOf(
                "found1406-farsi-04-spelling-1",
                "found1406-farsi-05-spelling-2",
                "found1406-farsi-02-sentence-grammar",
                "found1406-farsi-06-adjective-ezafe"
            ),
            checkLessonId = "found1406-farsi-04-spelling-1",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 11,
            title = "جمله و درک مطلب فارسی",
            subtitle = "جمله ساده و مرکب، خلاصه کردن متن و نوشتن توصیفی کوتاه",
            lessonIds = listOf(
                "found1406-farsi-07-pronoun-adverb",
                "found1406-farsi-08-sentence-types",
                "found1406-farsi-10-comprehension-2",
                "found1406-farsi-11-descriptive-essay"
            ),
            checkLessonId = "found1406-farsi-07-pronoun-adverb",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 12,
            title = "الفبای عربی و صیغه چیست",
            subtitle = "حروف و حرکات عربی؛ صیغه یعنی شکل فعل برای هر شخص",
            lessonIds = listOf(
                "found1406-ar-01-alphabet-reading",
                "found1406-ar-02-sigha-what-is",
                "found1406-ar-03-pronouns"
            ),
            checkLessonId = "found1406-ar-01-alphabet-reading",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 13,
            title = "فعل های عربی",
            subtitle = "ماضی و مضارع، اشاره و عدد و جنس اسم",
            lessonIds = listOf(
                "found1406-ar-04-demonstratives",
                "found1406-ar-05-gender-number",
                "found1406-ar-06-numbers",
                "found1406-ar-07-past-tense",
                "found1406-ar-08-present-tense"
            ),
            checkLessonId = "found1406-ar-04-demonstratives",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 14,
            title = "جمله و ترجمه عربی",
            subtitle = "جمله اسمیه و فعلیه و ترجمه متن کوتاه کلمه به کلمه",
            lessonIds = listOf(
                "found1406-ar-09-imperative",
                "found1406-ar-10-nominal-sentence",
                "found1406-ar-11-verbal-sentence",
                "found1406-ar-12-translation",
                "found1406-ar-13-reading-text"
            ),
            checkLessonId = "found1406-ar-09-imperative",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 15,
            title = "انگلیسی از صفر",
            subtitle = "الفبا و صداها، اولین واژه ها و فعل بودن",
            lessonIds = listOf(
                "found1406-en-01-alphabet-sounds",
                "found1406-en-02-first-words",
                "found1406-en-03-vocab-1",
                "found1406-en-04-vocab-2",
                "found1406-en-05-tobe",
                "found1406-en-06-have"
            ),
            checkLessonId = "found1406-en-01-alphabet-sounds",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 16,
            title = "فارسی دهم",
            subtitle = "دستور و واژه و قلمرو ادبی و فکری فارسی دهم به ترتیب کتاب",
            lessonIds = listOf(
                "farsi_10_ch1_grammar_vocab_realms",
                "farsi_10_ch2_literary_intellectual_realms",
                "farsi_01_syntax_roles_groups"
            ),
            checkLessonId = "farsi_10_ch1_grammar_vocab_realms",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 17,
            title = "نگارش دهم",
            subtitle = "نوشته عینی و ذهنی، خاطره و سفرنامه",
            lessonIds = listOf(
                "negaresh_01_grade10_objective_subjective",
                "neg1406-01-khatereh",
                "neg1406-02-safarnameh"
            ),
            checkLessonId = "negaresh_01_grade10_objective_subjective",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 18,
            title = "عربی دهم",
            subtitle = "انواع کلمه و صیغه، ماضی و مضارع و باب ها و جمله اسمیه و فعلیه",
            lessonIds = listOf(
                "ar1406-01-word-types-sigha",
                "ar1406-02-past-present-tenses",
                "ar1406-03-mujarrad-mazeed-1",
                "ar1406-04-mazeed-2",
                "ar1406-05-lesson5-nominal-sentence",
                "ar1406-06-lesson5-verbal-sentence",
                "arabic_01_word_types_pronouns"
            ),
            checkLessonId = "ar1406-01-word-types-sigha",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 19,
            title = "ریاضی و آمار دهم",
            subtitle = "علامت ها و کسر، معادله، تابع و دنباله",
            lessonIds = listOf(
                "math_01_signs_fractions",
                "math_02_equations",
                "math_03_functions",
                "math_04_functions_domain",
                "math_05_sequences_arithmetic"
            ),
            checkLessonId = "math_01_signs_fractions",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 20,
            title = "دینی دهم بخش یک",
            subtitle = "هدف زندگی و قدرت پرواز و آهنگ سفر، درس یک تا چهار",
            lessonIds = listOf(
                "dini1-01-hadaf-zendegi",
                "dini1-02-qodrat-parvaz",
                "dini1-03-be-sooye-javedanegi",
                "dini1-04-ayandeye-rowshan"
            ),
            checkLessonId = "dini1-01-hadaf-zendegi",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 21,
            title = "دینی دهم بخش دو",
            subtitle = "منزلگاه بعد، واقعه بزرگ، فرجام کار و اعتماد، درس پنج تا هشت",
            lessonIds = listOf(
                "dini1-05-manzelgahe-baad",
                "dini1-06-vagheye-bozorg",
                "dini1-07-farjam-kar",
                "dini1-08-ahang-safar"
            ),
            checkLessonId = "dini1-05-manzelgahe-baad",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 22,
            title = "دینی دهم بخش سه",
            subtitle = "یاری از دو یار عزیز و فضیلت آراستگی و پیام پوشش، درس نه تا دوازده",
            lessonIds = listOf(
                "dini1-09-etemad-bar-oo",
                "dini1-10-yari-az-do-yar-aziz",
                "dini1-11-fazilat-arastegi",
                "dini1-12-payam-pooshidegi"
            ),
            checkLessonId = "dini1-09-etemad-bar-oo",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 23,
            title = "تاریخ دهم بخش یک",
            subtitle = "تاریخ چیست و دنیای باستان، درس یک تا پنج",
            lessonIds = listOf(
                "hist1-01",
                "hist1-02",
                "hist1-03",
                "hist1-04",
                "hist1-05",
                "hist_10_ch1_ancient_world_mesopotamia_egypt"
            ),
            checkLessonId = "hist1-01",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 24,
            title = "تاریخ دهم بخش دو",
            subtitle = "ایران باستان از ماد تا ساسانی، درس شش تا ده",
            lessonIds = listOf(
                "hist1-06",
                "hist1-07",
                "hist1-08",
                "hist1-09",
                "hist1-10",
                "hist_10_ch2_ancient_iran_medes_achaemenids_parthians_sasanians"
            ),
            checkLessonId = "hist1-06",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 25,
            title = "تاریخ دهم بخش سه",
            subtitle = "پایان ایران باستان و جمع بندی، درس یازده تا شانزده",
            lessonIds = listOf(
                "hist1-11",
                "hist1-12",
                "hist1-13",
                "hist1-14",
                "hist1-15",
                "hist1-16"
            ),
            checkLessonId = "hist1-11",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 26,
            title = "فنون دهم",
            subtitle = "تاریخ ادبیات خراسانی و بدیع و تشبیه و مجاز",
            lessonIds = listOf(
                "fonoon_10_ch1_history_khorasani",
                "fonoon_10_ch2_badi_tarsee_movazeneh",
                "fonoon_10_ch3_tashbih_majaz_10",
                "fonoon_10_lesson10_sade5_6"
            ),
            checkLessonId = "fonoon_10_ch1_history_khorasani",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 27,
            title = "جغرافیا دهم بخش یک",
            subtitle = "جغرافیا برای زندگی بهتر و موقعیت ایران، درس یک تا چهار",
            lessonIds = listOf(
                "geo1-01-joghrafia-elmi-baraye-zendegi-behtar",
                "geo1-02-ravesh-motalee-va-pazhuhesh",
                "geo1-03-mogheiyat-joghrafiayi-iran",
                "geo1-04-nahamvarihaye-iran",
                "geo_10_ch1_location_topography_climate_rivers"
            ),
            checkLessonId = "geo1-01-joghrafia-elmi-baraye-zendegi-behtar",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 28,
            title = "جغرافیا دهم بخش دو",
            subtitle = "آب و هوای ایران و منابع آب و جمعیت، درس پنج تا هشت",
            lessonIds = listOf(
                "geo1-05-ab-va-havaye-iran",
                "geo1-06-manabe-ab-iran",
                "geo1-07-vizhegihaye-jamiyat-iran",
                "geo1-08-taqsimat-keshvari-iran",
                "geo_10_ch2_iran_population_settlements_economy"
            ),
            checkLessonId = "geo1-05-ab-va-havaye-iran",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 29,
            title = "جغرافیا دهم بخش سه",
            subtitle = "سکونتگاه ها و توان های اقتصادی و حمل و نقل، درس نه و ده",
            lessonIds = listOf(
                "geo1-09-sokonatgahhaye-iran",
                "geo1-10-tavanhaye-eqtesadi-iran",
                "geo_10_ch3_economy_transport_borders"
            ),
            checkLessonId = "geo1-09-sokonatgahhaye-iran",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 30,
            title = "جامعه شناسی دهم بخش یک",
            subtitle = "کنش انسانی و فرهنگ، درس یک تا پنج",
            lessonIds = listOf(
                "soc1-01",
                "soc1-02",
                "soc1-03",
                "soc1-04",
                "soc1-05",
                "soc_10_ch1_human_social_action"
            ),
            checkLessonId = "soc1-01",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 31,
            title = "جامعه شناسی دهم بخش دو",
            subtitle = "لایه های فرهنگ و ارزش ها، درس شش تا ده",
            lessonIds = listOf(
                "soc1-06",
                "soc1-07",
                "soc1-08",
                "soc1-09",
                "soc1-10",
                "soc_10_ch2_culture_layers_values"
            ),
            checkLessonId = "soc1-06",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 32,
            title = "جامعه شناسی دهم بخش سه",
            subtitle = "پایان کتاب دهم و جمع بندی، درس یازده تا شانزده",
            lessonIds = listOf(
                "soc1-11",
                "soc1-12",
                "soc1-13",
                "soc1-14",
                "soc1-15",
                "soc1-16"
            ),
            checkLessonId = "soc1-11",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 33,
            title = "انگلیسی دهم",
            subtitle = "طبیعت و آفرینش و ارزش دانش، سه درس ویژن یک",
            lessonIds = listOf(
                "english_10_vision1_lesson1_saving_nature",
                "english_10_vision1_lesson2_wonders_creation",
                "english_10_vision1_lesson3_value_knowledge"
            ),
            checkLessonId = "english_10_vision1_lesson1_saving_nature",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 34,
            title = "اقتصاد دهم",
            subtitle = "بازار و عرضه و تقاضا و تجارت، با روش چشمی",
            lessonIds = listOf(
                "econ_01_grade10_1403_complete",
                "econ_10_ch2_market_supply_demand",
                "econ_10_ch4_trade_exchange_resilience",
                "eco-quick-01",
                "eco-quick-02"
            ),
            checkLessonId = "econ_01_grade10_1403_complete",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 35,
            title = "منطق دهم",
            subtitle = "مفهوم و مصداق و تعریف، درس یک تا پنج منطق",
            lessonIds = listOf(
                "logic_10_lesson1_2_concepts_terms",
                "logic_10_lesson3_4_relations_five_universals",
                "logic_10_lesson5_definitions",
                "logic1406-01-definitions-deep"
            ),
            checkLessonId = "logic_10_lesson1_2_concepts_terms",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 36,
            title = "فارسی و فنون یازدهم",
            subtitle = "دستور و واژه یازدهم و تاریخ ادبیات عراقی و تشبیه و استعاره",
            lessonIds = listOf(
                "farsi_11_ch1_grammar_vocab_realms",
                "farsi_02_vocab_spelling_3_realms",
                "farsi_02_word_morphology",
                "fonoon_11_ch1_history_eraqi",
                "fonoon_11_ch2_tashbih_types",
                "fonoon_11_ch3_estearah_majaz_kenayeh"
            ),
            checkLessonId = "farsi_11_ch1_grammar_vocab_realms",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 37,
            title = "نگارش یازدهم",
            subtitle = "گزارش و حکایت و مثل و داستان و جانشین و خلاصه",
            lessonIds = listOf(
                "negaresh_03_grade11_report_dialogue_recreate",
                "neg2-01-hekayat",
                "neg2-02-masal",
                "neg2-03-gozaresh",
                "neg2-04-dastan",
                "neg2-05-janeshin",
                "neg2-06-sanjesh",
                "neg2-07-nasazi",
                "neg2-08-kholaseh"
            ),
            checkLessonId = "negaresh_03_grade11_report_dialogue_recreate",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 38,
            title = "عربی یازدهم",
            subtitle = "شرط و جمله وصفیه و افعال ناقصه و تفضیل و مکان و زمان",
            lessonIds = listOf(
                "arabic_11_ch1_tafzeel_makan_zaman",
                "arabic_11_ch2_shart_and_naqeseh",
                "arabic_11_ch3_moqarabeh_vasfiyeh_huruf",
                "arabic_03b_grade11_shart_naqeseh_vasf"
            ),
            checkLessonId = "arabic_11_ch1_tafzeel_makan_zaman",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 39,
            title = "دینی یازدهم بخش یک",
            subtitle = "هدایت الهی و پیامبر و معجزه جاویدان، درس یک تا پنج",
            lessonIds = listOf(
                "dini2-01-hedayat-elahi",
                "dini2-02-tadavom-hedayat",
                "dini2-03-akharin-payambar",
                "dini2-04-mojeze-javidan",
                "dini2-05-masouliyat-payambar"
            ),
            checkLessonId = "dini2-01-hedayat-elahi",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 40,
            title = "دینی یازدهم بخش دو",
            subtitle = "امامت و جانشینی پیامبر، درس شش تا ده",
            lessonIds = listOf(
                "dini2-06-pishvaye-osve",
                "dini2-07-emamat-tadavom-resalat",
                "dini2-08-jan-va-janeshin-payambar",
                "dini2-09-vaziyat-mosalamanan-pas-az-rehlat",
                "dini2-10-ehyaye-arzeshhaye-rastin"
            ),
            checkLessonId = "dini2-06-pishvaye-osve",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 41,
            title = "دینی یازدهم بخش سه",
            subtitle = "عصر غیبت و مرجعیت و رهبری، درس یازده تا پانزده",
            lessonIds = listOf(
                "dini2-11-jahad-dar-rastaye-velayat-zaheri",
                "dini2-12-asr-gheybat-emam-zaman",
                "dini2-13-dar-entezar-zohour",
                "dini2-14-marjaeyat-va-velayat-faqih",
                "dini2-15-rahbari-va-mardom"
            ),
            checkLessonId = "dini2-11-jahad-dar-rastaye-velayat-zaheri",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 42,
            title = "تاریخ یازدهم بخش یک",
            subtitle = "اسلام و پیامبر و خلافت ها، درس یک تا پنج",
            lessonIds = listOf(
                "hist2-01",
                "hist2-02",
                "hist2-03",
                "hist2-04",
                "hist2-05",
                "hist_11_ch1_islam_prophet_caliphates"
            ),
            checkLessonId = "hist2-01",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 43,
            title = "تاریخ یازدهم بخش دو",
            subtitle = "سلسله های ایرانی از طاهری تا تیموری، درس شش تا ده",
            lessonIds = listOf(
                "hist2-06",
                "hist2-07",
                "hist2-08",
                "hist2-09",
                "hist2-10",
                "hist_11_ch2_iran_islamic_dynasties_tahirid_to_timurid"
            ),
            checkLessonId = "hist2-06",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 44,
            title = "تاریخ یازدهم بخش سه",
            subtitle = "پایان کتاب یازدهم و جمع بندی، درس یازده تا شانزده",
            lessonIds = listOf(
                "hist2-11",
                "hist2-12",
                "hist2-13",
                "hist2-14",
                "hist2-15",
                "hist2-16"
            ),
            checkLessonId = "hist2-11",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 45,
            title = "جغرافیا یازدهم بخش یک",
            subtitle = "ناحیه یعنی چه و انسان و ناحیه، درس یک تا چهار",
            lessonIds = listOf(
                "geo2-01-mana-va-mafhum-nahiye",
                "geo2-02-ensan-va-nahiye",
                "geo2-03-navahi-ab-va-havayi",
                "geo2-04-nahamvariha-va-ashkal-zamin",
                "geo_11_ch1_regions_biomes_culture"
            ),
            checkLessonId = "geo2-01-mana-va-mafhum-nahiye",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 46,
            title = "جغرافیا یازدهم بخش دو",
            subtitle = "ناحیه های زیستی و فرهنگی و اقتصادی، درس پنج تا هشت",
            lessonIds = listOf(
                "geo2-05-navahi-zisti",
                "geo2-06-navahi-farhangi",
                "geo2-07-navahi-eqtesadi-keshavarzi-va-sanati",
                "geo2-08-navahi-eqtesadi-tejarat-va-eqtesad-jahani",
                "geo_11_ch2_economic_political_regions_seas"
            ),
            checkLessonId = "geo2-05-navahi-zisti",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 47,
            title = "جغرافیا یازدهم بخش سه",
            subtitle = "ناحیه سیاسی و ژئوپلیتیک و مطالعه موردی، درس نه تا یازده",
            lessonIds = listOf(
                "geo2-09-mana-va-mafhum-nahiye-siyasi",
                "geo2-10-keshvar-yek-nahiye-siyasi",
                "geo2-11-geopolitik",
                "geo_11_ch3_case_studies_sw_asia_europe"
            ),
            checkLessonId = "geo2-09-mana-va-mafhum-nahiye-siyasi",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 48,
            title = "جامعه شناسی یازدهم بخش یک",
            subtitle = "جهان فرهنگی و جهانی شدن، درس یک تا پنج",
            lessonIds = listOf(
                "soc2-01",
                "soc2-02",
                "soc2-03",
                "soc2-04",
                "soc2-05",
                "soc_11_ch1_cultural_world_globalization"
            ),
            checkLessonId = "soc2-01",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 49,
            title = "جامعه شناسی یازدهم بخش دو",
            subtitle = "ادامه کتاب یازدهم، درس شش تا ده",
            lessonIds = listOf(
                "soc2-06",
                "soc2-07",
                "soc2-08",
                "soc2-09",
                "soc2-10"
            ),
            checkLessonId = "soc2-06",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 50,
            title = "جامعه شناسی یازدهم بخش سه",
            subtitle = "پایان کتاب یازدهم و جمع بندی، درس یازده تا پانزده",
            lessonIds = listOf(
                "soc2-11",
                "soc2-12",
                "soc2-13",
                "soc2-14",
                "soc2-15"
            ),
            checkLessonId = "soc2-11",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 51,
            title = "فلسفه یازدهم",
            subtitle = "فلسفه چیست و انسان چیست، از اول با مثال روشن",
            lessonIds = listOf(
                "phil1-03-falsafe-va-zendegi",
                "phil1-05-zendegi-bar-asas-andishe",
                "phil1-09-chisti-ensan-1",
                "phil1-10-chisti-ensan-2",
                "phil1-11-ensan-mowjud-akhlaqgara"
            ),
            checkLessonId = "phil1-03-falsafe-va-zendegi",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 52,
            title = "منطق یازدهم",
            subtitle = "قضیه و قیاس و مغالطه و استقرا و تمثیل",
            lessonIds = listOf(
                "logic1406-02-propositions-square-conversion",
                "logic1406-03-syllogism-reasoning",
                "logic1406-04-fallacies-deep",
                "logic1406-05-esteghra-tamsil"
            ),
            checkLessonId = "logic1406-02-propositions-square-conversion",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 53,
            title = "روان شناسی یازدهم",
            subtitle = "مکتب ها و رشد و تفکر و استرس و سلامت روان",
            lessonIds = listOf(
                "psych_01_grade11_1404_complete",
                "psych_11_ch1_schools_and_development",
                "psych_11_ch3_thinking_problem_solving",
                "psych_11_ch4_stress_mental_health"
            ),
            checkLessonId = "psych_01_grade11_1404_complete",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 54,
            title = "انگلیسی یازدهم",
            subtitle = "سبک زندگی سالم و فرهنگ و هنر، سه درس ویژن دو",
            lessonIds = listOf(
                "english_11_vision2_lesson1_healthy_lifestyle",
                "english_11_vision2_lesson2_gerund_infinitive",
                "english_11_vision2_lesson3_culture_and_art"
            ),
            checkLessonId = "english_11_vision2_lesson1_healthy_lifestyle",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 55,
            title = "ریاضی و آمار یازدهم",
            subtitle = "تابع و توان و دنباله ها و پراکندگی داده ها",
            lessonIds = listOf(
                "math_05c_grade11_functions_indices",
                "math_11_grade11_functions_exponential",
                "math_05d_sequences_arithmetic_deep",
                "math_05e_sequences_geometric_deep",
                "math_06_statistics_dispersion"
            ),
            checkLessonId = "math_05c_grade11_functions_indices",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 56,
            title = "فارسی دوازدهم",
            subtitle = "نحو و گروه اسمی و واژه و املا و نمونه نهایی",
            lessonIds = listOf(
                "farsi_12_ch1_syntax_noun_phrase_realms",
                "farsi_12_ch2_final_exam_vocab_spelling_authors",
                "farsi_04_advanced_syntax_pronoun_shift",
                "farsi_03_negaresh_final_exam_20"
            ),
            checkLessonId = "farsi_12_ch1_syntax_noun_phrase_realms",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 57,
            title = "نگارش دوازدهم",
            subtitle = "شش درس نگارش سه و قالب های نمره بیست نهایی",
            lessonIds = listOf(
                "neg3-01",
                "neg3-02",
                "neg3-03",
                "neg3-04",
                "neg3-05",
                "neg3-06",
                "negaresh_02_grade12_final_20_templates",
                "neg1406-03-tosif",
                "neg1406-04-zendeginameh"
            ),
            checkLessonId = "neg3-01",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 58,
            title = "عربی دوازدهم",
            subtitle = "لای نفی جنس و حال و استثنا و تمییز و منادا و مفعول مطلق",
            lessonIds = listOf(
                "ar12-01-lesson1-la-nafy-jins",
                "ar12-02-lesson2-hal",
                "ar12-03-lesson3-istithna-tamyiz",
                "ar12-04-lesson4-5-monada-mafoul-ialal",
                "arabic_03_grade12_rules"
            ),
            checkLessonId = "ar12-01-lesson1-la-nafy-jins",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 59,
            title = "دینی دوازدهم بخش یک",
            subtitle = "هستی بخش و سنت الهی و توبه و دنیای امروز، درس یک تا پنج",
            lessonIds = listOf(
                "dini3-01-hasti-bakhsh",
                "dini3-02-sonnat-elahi",
                "dini3-03-towbe",
                "dini3-04-donyaye-emruz",
                "dini3-05-qodrat-parvaz"
            ),
            checkLessonId = "dini3-01-hasti-bakhsh",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 60,
            title = "دینی دوازدهم بخش دو",
            subtitle = "سنت های خدا در زندگی و بازگشت و پایه های استوار، درس شش تا ده",
            lessonIds = listOf(
                "dini3-06-sonnathaye-khodavand-dar-zendegi",
                "dini3-07-dar-gero-kar-khish",
                "dini3-08-bazgasht",
                "dini3-09-zendegi-dar-donyaye-emruz-va-amal-be-ahkam-elahi",
                "dini3-10-payehaye-ostovar"
            ),
            checkLessonId = "dini3-06-sonnathaye-khodavand-dar-zendegi",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 61,
            title = "دینی دوازدهم بخش سه",
            subtitle = "عصر شکوفایی و تمدن جدید و مسئولیت بزرگ ما، درس یازده تا سیزده",
            lessonIds = listOf(
                "dini3-11-asr-shokoufai",
                "dini3-12-negahi-be-tamadon-jadid",
                "dini3-13-masouliyat-bozorg-ma"
            ),
            checkLessonId = "dini3-11-asr-shokoufai",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 62,
            title = "تاریخ دوازدهم بخش یک",
            subtitle = "افشار و زند و قاجار و مشروطه، درس یک تا چهار",
            lessonIds = listOf(
                "hist3-01",
                "hist3-02",
                "hist3-03",
                "hist3-04",
                "hist_12_ch1_afshar_zand_qajar_constitution"
            ),
            checkLessonId = "hist3-01",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 63,
            title = "تاریخ دوازدهم بخش دو",
            subtitle = "پهلوی و نفت و ملی شدن و انقلاب، درس پنج تا هشت",
            lessonIds = listOf(
                "hist3-05",
                "hist3-06",
                "hist3-07",
                "hist3-08",
                "hist_12_ch2_pahlavi_oil_nationalization_revolution"
            ),
            checkLessonId = "hist3-05",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 64,
            title = "تاریخ دوازدهم بخش سه",
            subtitle = "پایان کتاب دوازدهم و جمع بندی، درس نه تا دوازده",
            lessonIds = listOf(
                "hist3-09",
                "hist3-10",
                "hist3-11",
                "hist3-12"
            ),
            checkLessonId = "hist3-09",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 65,
            title = "جغرافیا دوازدهم",
            subtitle = "جغرافیای کاربردی، مخاطرات و مدیریت بحران و حمل و نقل",
            lessonIds = listOf(
                "geo3-04-transport-management",
                "geo3-06-crisis-management",
                "geo_12_ch1_applied_geography_hazards",
                "geo_12_ch2_climate_spatial_planning_gis",
                "geo3-skills-geo-techniques"
            ),
            checkLessonId = "geo3-04-transport-management",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 66,
            title = "جامعه شناسی دوازدهم بخش یک",
            subtitle = "سه رویکرد به علوم اجتماعی، درس یک تا چهار",
            lessonIds = listOf(
                "soc3-01",
                "soc3-02",
                "soc3-03",
                "soc3-04",
                "soc_12_ch1_three_approaches_social_sciences"
            ),
            checkLessonId = "soc3-01",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 67,
            title = "جامعه شناسی دوازدهم بخش دو",
            subtitle = "نظم اجتماعی و خانواده و سیاست، درس پنج تا هفت",
            lessonIds = listOf(
                "soc3-05",
                "soc3-06",
                "soc3-07",
                "soc_12_ch2_social_order_family_policy"
            ),
            checkLessonId = "soc3-05",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 68,
            title = "جامعه شناسی دوازدهم بخش سه",
            subtitle = "پایان کتاب دوازدهم و جمع بندی، درس هشت تا ده",
            lessonIds = listOf(
                "soc3-08",
                "soc3-09",
                "soc3-10"
            ),
            checkLessonId = "soc3-08",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 69,
            title = "فلسفه دوازدهم بخش یک",
            subtitle = "هستی و چیستی و جهان ممکنات و علت و معلول و خدا",
            lessonIds = listOf(
                "phil2-01-hasti-va-chisti",
                "phil2-02-jahan-momkenat",
                "phil2-03-jahan-elli-malooli",
                "phil2-04-kodam-tasvir",
                "phil2-05-khoda-dar-falsafe-1",
                "phil2-06-khoda-dar-falsafe-2"
            ),
            checkLessonId = "phil2-01-hasti-va-chisti",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 70,
            title = "فلسفه دوازدهم بخش دو",
            subtitle = "عقل و آغاز فلسفه و دوره ها و حکمت معاصر",
            lessonIds = listOf(
                "phil2-07-aghl-1",
                "phil2-08-aghl-2",
                "phil2-09-aghaz-falsafeh",
                "phil2-10-dowreh-miyani",
                "phil2-11-dowran-motakhkher",
                "phil2-12-hekmat-moaser"
            ),
            checkLessonId = "phil2-07-aghl-1",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 71,
            title = "فنون و عروض دوازدهم",
            subtitle = "سبک هندی و بازگشت و لف و نشر و بحرها و قافیه پیشرفته",
            lessonIds = listOf(
                "fonoon_12_ch1_history_hindi_bazgasht",
                "fonoon_04_grade12_lafo_nashr_esloob_hasan",
                "arooz12-01-dowri",
                "arooz12-02-meters12",
                "arooz12-03-licenses",
                "arooz12-04-ghafieh-advanced"
            ),
            checkLessonId = "fonoon_12_ch1_history_hindi_bazgasht",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 72,
            title = "ریاضی و آمار دوازدهم",
            subtitle = "رگرسیون و سری زمانی و نمایش داده ها و احتمال شرطی",
            lessonIds = listOf(
                "math_07_grade12_regression_time_series",
                "math_10_ch4_data_display",
                "math_09_statistics_percentiles",
                "math_07_combinatorics_probability",
                "math_10_conditional_probability"
            ),
            checkLessonId = "math_07_grade12_regression_time_series",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 73,
            title = "انگلیسی دوازدهم و سلامت و هویت",
            subtitle = "انرژی تجدیدپذیر و سلامت و خانواده، کتاب های عمومی دوازدهم",
            lessonIds = listOf(
                "eng1406-01-vision3-appreciation",
                "eng1406-02-vision3-look-it-up",
                "eng1406-03-vision3-renewable-energy",
                "gen_01_salamat_grade12_1405",
                "gen_02_hoviyat_family_grade12_1405"
            ),
            checkLessonId = "eng1406-01-vision3-appreciation",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 74,
            title = "اقتصاد دوازدهم",
            subtitle = "تولید ناخالص و تورم و بازار و مالیات و توزیع، با روش چشمی",
            lessonIds = listOf(
                "econ_03_gdp_inflation_unemployment_budget",
                "econ_04_market_equilibrium_curves",
                "econ_05_tax_gini_distribution",
                "econ_full_math_mastery",
                "eco-quick-03",
                "eco-quick-04",
                "eco-quick-05",
                "eco-quick-06"
            ),
            checkLessonId = "econ_03_gdp_inflation_unemployment_budget",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 75,
            title = "جمع بندی دوازدهم",
            subtitle = "مرور کامل کتاب های دوازدهم در یک نگاه",
            lessonIds = listOf(
                "soc_03_grade12_1405_complete",
                "dini_03_grade12_1405_complete",
                "geo_03_grade12_1405",
                "hist_03_grade12_1405"
            ),
            checkLessonId = "soc_03_grade12_1405_complete",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 76,
            title = "نقشه راه فرهنگیان",
            subtitle = "آزمون فرهنگیان چیست و سند تحول چه می گوید",
            lessonIds = listOf(
                "farh-src-01-roadmap-exam-1405",
                "far_01_roadmap_1406",
                "farh1406-01-sanad-tahavol"
            ),
            checkLessonId = "farh-src-01-roadmap-exam-1405",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 77,
            title = "مهارت معلمی قرائتی بخش یک",
            subtitle = "ارزش و ویژگی ها و وظیفه های معلم، فصل یک تا سه",
            lessonIds = listOf(
                "farh-src-02-qaraati-ch1-value",
                "farh-src-03-qaraati-ch2-attributes",
                "farh-src-04-qaraati-ch3-duties"
            ),
            checkLessonId = "farh-src-02-qaraati-ch1-value",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 78,
            title = "مهارت معلمی قرائتی بخش دو",
            subtitle = "روش های معلمی قرائتی به زبان ساده و کامل",
            lessonIds = listOf(
                "far_02_gharaati_talim",
                "far_08_qaraati_teacher_methods_complete"
            ),
            checkLessonId = "far_02_gharaati_talim",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 79,
            title = "تعلیم و تربیت اسلامی",
            subtitle = "تربیت اسلامی و دین و زندگی ویژه فرهنگیان",
            lessonIds = listOf(
                "farh1406-02-talim-tarbiyat-eslami",
                "far_05_dini_farhangian_ensani"
            ),
            checkLessonId = "farh1406-02-talim-tarbiyat-eslami",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 80,
            title = "دین و زندگی ویژه فرهنگیان",
            subtitle = "محدوده درست دین دو و جمع بندی دین هر سه پایه",
            lessonIds = listOf(
                "farh-src-05-dini-correct-range",
                "far_08_dini_complete_10_11_12_1406"
            ),
            checkLessonId = "farh-src-05-dini-correct-range",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 81,
            title = "هوش و استعداد معلمی بخش یک",
            subtitle = "هوش معلمی یعنی چه و سوال های کمی و طبقه بندی",
            lessonIds = listOf(
                "far_03_teacher_intelligence",
                "farh-src-06-housh-quantitative",
                "farh-src-07-housh-classification"
            ),
            checkLessonId = "far_03_teacher_intelligence",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 82,
            title = "هوش و استعداد معلمی بخش دو",
            subtitle = "هوش کلامی و منطقی و ریاضی و استدلال معلمی",
            lessonIds = listOf(
                "far_03a_verbal_logical_math_intelligence_full",
                "far_09_teacher_aptitude_reasoning"
            ),
            checkLessonId = "far_03a_verbal_logical_math_intelligence_full",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 83,
            title = "مصاحبه فرهنگیان",
            subtitle = "سناریوهای مصاحبه انسانی و نوشتن کانون",
            lessonIds = listOf(
                "far_04_interview_humanities",
                "far_06_comprehensive_interview_scenarios",
                "farh-src-08-kanoon-writing"
            ),
            checkLessonId = "far_04_interview_humanities",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 84,
            title = "جدول ها و مرور فرهنگیان",
            subtitle = "جدول های جمع بندی انسانی و دستور فارسی هر سه پایه",
            lessonIds = listOf(
                "far_07_konkoor_full_package_humanities_tables",
                "far_09_farsi_grammar_1_to_12_1406",
                "far_10_remaining_humanities_books_10_11_12",
                "farhangian_04_mnemonics_final20"
            ),
            checkLessonId = "far_07_konkoor_full_package_humanities_tables",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 85,
            title = "آزمون فرهنگیان سال های پیش",
            subtitle = "سوال های واقعی فرهنگیان ۱۴۰۳ و ۱۴۰۴ و ۱۴۰۵",
            lessonIds = listOf(
                "exam_1403_farhangian",
                "exam_1404_farhangian",
                "q_1403_far_talim",
                "q_1405_farhangian_1",
                "q_1405_farhangian_2",
                "q_1405_farhangian_3"
            ),
            checkLessonId = "exam_1403_farhangian",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 86,
            title = "کنکور عربی",
            subtitle = "سوال های کنکور عربی و کلیدهای ترجمه",
            lessonIds = listOf(
                "q_1405_arabic_1",
                "q_1405_arabic_2",
                "q_1403_arabic_1",
                "q_1404_arabic_hal",
                "arabic_05_konkoor_translation_keys"
            ),
            checkLessonId = "q_1405_arabic_1",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 87,
            title = "کنکور فارسی و فنون",
            subtitle = "سوال های کنکور فنون و آرایه ها",
            lessonIds = listOf(
                "q_1405_fonoon_1",
                "q_1405_fonoon_2",
                "q_1405_fonoon_3",
                "q_1403_fonoon_1",
                "q_1404_fonoon_araye"
            ),
            checkLessonId = "q_1405_fonoon_1",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 88,
            title = "کنکور دینی",
            subtitle = "سوال های کنکور دینی ۱۴۰۵؛ فقط دو بسته دارد و هنوز کم است",
            lessonIds = listOf(
                "q_1405_dini_1",
                "q_1405_dini_2"
            ),
            checkLessonId = "q_1405_dini_1",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 89,
            title = "کنکور تاریخ و جغرافیا",
            subtitle = "سوال های کنکور تاریخ و جغرافیا ۱۴۰۵؛ هر کدام یک بسته دارد و هنوز کم است",
            lessonIds = listOf(
                "q_1405_hist_1",
                "q_1405_geo_1"
            ),
            checkLessonId = "q_1405_hist_1",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 90,
            title = "کنکور جامعه و اقتصاد",
            subtitle = "سوال های کنکور جامعه شناسی و اقتصاد ۱۴۰۵",
            lessonIds = listOf(
                "q_1405_soc_1",
                "q_1405_econ_1",
                "q_1405_econ_2"
            ),
            checkLessonId = "q_1405_soc_1",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 91,
            title = "کنکور فلسفه و منطق و روان شناسی",
            subtitle = "سوال های کنکور فلسفه و روان شناسی ۱۴۰۳ و ۱۴۰۵",
            lessonIds = listOf(
                "q_1405_phil_1",
                "q_1405_phil_2",
                "q_1405_psych_1",
                "q_1403_phil_1",
                "q_1403_psych_1"
            ),
            checkLessonId = "q_1405_phil_1",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 92,
            title = "کنکور ریاضی و آمار",
            subtitle = "سوال های کنکور ریاضی و آمار و عملیات و تابع",
            lessonIds = listOf(
                "q_1405_math_1",
                "q_1405_math_2",
                "q_1404_math_amaliyat",
                "q_1404_math_tabe"
            ),
            checkLessonId = "q_1405_math_1",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 93,
            title = "کنکور سراسری واقعی ۱۴۰۳",
            subtitle = "دفترچه های واقعی اردیبهشت و تیر ۱۴۰۳",
            lessonIds = listOf(
                "exam_1403_ordibehesht",
                "exam_1403_tirmaah",
                "exam_real_1403_tir_b2"
            ),
            checkLessonId = "exam_1403_ordibehesht",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 94,
            title = "کنکور سراسری واقعی ۱۴۰۴",
            subtitle = "دفترچه های واقعی اردیبهشت و تیر ۱۴۰۴",
            lessonIds = listOf(
                "exam_1404_ordibehesht",
                "exam_1404_tirmaah"
            ),
            checkLessonId = "exam_1404_ordibehesht",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 95,
            title = "کنکور سراسری واقعی ۱۴۰۵",
            subtitle = "دفترچه جامع و مرداد ۱۴۰۵؛ آزمون خرداد ۱۴۰۵ هنوز در بانک نیست",
            lessonIds = listOf(
                "exam_1405_comprehensive",
                "exam_real_1405_mordad_b2"
            ),
            checkLessonId = "exam_1405_comprehensive",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 96,
            title = "نهایی فارسی ۱۴۰۵ بخش یک",
            subtitle = "سوال های واقعی نهایی فارسی ۱۴۰۵، شماره یک تا شش",
            lessonIds = listOf(
                "nah_real_1405_farsi_01",
                "nah_real_1405_farsi_02",
                "nah_real_1405_farsi_03",
                "nah_real_1405_farsi_04",
                "nah_real_1405_farsi_05",
                "nah_real_1405_farsi_06"
            ),
            checkLessonId = "nah_real_1405_farsi_01",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 97,
            title = "نهایی فارسی ۱۴۰۵ بخش دو",
            subtitle = "سوال های واقعی نهایی فارسی ۱۴۰۵، شماره هفت تا دوازده",
            lessonIds = listOf(
                "nah_real_1405_farsi_07",
                "nah_real_1405_farsi_08",
                "nah_real_1405_farsi_09",
                "nah_real_1405_farsi_10",
                "nah_real_1405_farsi_11",
                "nah_real_1405_farsi_12"
            ),
            checkLessonId = "nah_real_1405_farsi_07",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 98,
            title = "نهایی سال های پیش",
            subtitle = "سوال های واقعی نهایی خرداد ۱۴۰۴ برای پنج درس؛ بقیه درس ها هنوز سوال واقعی ندارند",
            lessonIds = listOf(
                "nah_arabic_1404_khordad_1",
                "nah_dini_1404_khordad_1",
                "nah_farsi_1404_khordad_1",
                "nah_math_1404_khordad_1",
                "nah_econ_1404_khordad_1"
            ),
            checkLessonId = "nah_arabic_1404_khordad_1",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 99,
            title = "نهایی خرداد ۱۴۰۵ برای درس های غیرفارسی",
            subtitle = "هنوز ساخته نشده؛ فقط فارسی سوال واقعی ۱۴۰۵ دارد",
            lessonIds = emptyList(),
            checkLessonId = "",
            status = LearningPathStatus.GAP,
            gapNote = "نهایی خرداد ۱۴۰۵ فقط برای فارسی سوال واقعی دارد و برای عربی و دینی و ریاضی و اقتصاد و تاریخ و جغرافیا و جامعه و فلسفه و منطق و فنون و انگلیسی و روان شناسی هنوز صفر است؛ پوشش نهایی در کل حدود یک چهارم است و این قدم تا ساخته شدن درس ها خالی می ماند."
        ),
        LearningPathStep(
            stepNumber = 100,
            title = "شبیه ساز کامل کنکور انسانی ۱۴۰۶",
            subtitle = "هنوز ساخته نشده؛ آزمون جامع زمان دار با کارنامه",
            lessonIds = emptyList(),
            checkLessonId = "",
            status = LearningPathStatus.GAP,
            gapNote = "بانک سوال کنکور هنوز فقط حدود ده درصد سوال واقعی دارد و شبیه ساز کامل کنکور ۱۴۰۶ با زمان بندی و کارنامه هنوز ساخته نشده است؛ این قدم تا ساخته شدن شبیه ساز خالی می ماند."
        )
    )
}
