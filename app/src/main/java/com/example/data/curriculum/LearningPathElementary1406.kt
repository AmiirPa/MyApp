package com.example.data.curriculum

// مسیر دبستان: پایه ۱ تا ۶، از اول تا ششم، به ترتیب کتاب.
// هر قدم = یک درس کتاب، به ترتیب پایه و بعد ترتیب کتاب: اول این را بخوان،
// بعد آن را. قدم به قدم، پله به پله. درس‌های تمرین کتاب (practice) قدم
// جدا نیستند — تمرین هر درس، داخل همان درس و در فهرست موضوعی است.
// همه شناسه‌ها از درس‌های واقعاً تعریف‌شده گرفته شده‌اند؛ قدمی با درس
// ساخته‌نشده وجود ندارد — اگر درسی هنوز ساخته نشده باشد، به‌صورت GAP
// با یادداشت «هنوز ساخته نشده» می‌آید، نه خالی.
// مسیر ۱ تا ۱۰۰ (متوسطه دوم + فرهنگیان) در LearningPath1406.kt است و
// دست نخورده است؛ این مسیر کنار آن است، نه به‌جای آن.

object LearningPathElementary1406 {
    val steps: List<LearningPathStep> = listOf(
        LearningPathStep(
            stepNumber = 1,
            title = "فارسی اول ۱: نگاره‌ها ۱ — از خانه تا کلاس",
            subtitle = "پایه اول دبستان — فارسی",
            lessonIds = listOf(
                "elem1-farsi-01-negareh-1"
            ),
            checkLessonId = "elem1-farsi-01-negareh-1",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 2,
            title = "فارسی اول ۲: نگاره‌ها ۲ — بازی، روستا، طبیعت، مسجد و نوروز",
            subtitle = "پایه اول دبستان — فارسی",
            lessonIds = listOf(
                "elem1-farsi-02-negareh-2"
            ),
            checkLessonId = "elem1-farsi-02-negareh-2",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 3,
            title = "فارسی اول ۳: نشانه ۱ — آ ا و بـ ب",
            subtitle = "پایه اول دبستان — فارسی",
            lessonIds = listOf(
                "elem1-farsi-03-neshane-01"
            ),
            checkLessonId = "elem1-farsi-03-neshane-01",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 4,
            title = "فارسی اول ۴: نشانه ۲ — اَ و َ و د",
            subtitle = "پایه اول دبستان — فارسی",
            lessonIds = listOf(
                "elem1-farsi-04-neshane-02"
            ),
            checkLessonId = "elem1-farsi-04-neshane-02",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 5,
            title = "فارسی اول ۵: نشانه ۳ — مـ م و سـ س",
            subtitle = "پایه اول دبستان — فارسی",
            lessonIds = listOf(
                "elem1-farsi-05-neshane-03"
            ),
            checkLessonId = "elem1-farsi-05-neshane-03",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 6,
            title = "فارسی اول ۶: نشانه ۴ — او و و تـ ت",
            subtitle = "پایه اول دبستان — فارسی",
            lessonIds = listOf(
                "elem1-farsi-06-neshane-04"
            ),
            checkLessonId = "elem1-farsi-06-neshane-04",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 7,
            title = "فارسی اول ۷: نشانه ۵ — ر و نـ ن",
            subtitle = "پایه اول دبستان — فارسی",
            lessonIds = listOf(
                "elem1-farsi-07-neshane-05"
            ),
            checkLessonId = "elem1-farsi-07-neshane-05",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 8,
            title = "فارسی اول ۸: نشانه ۶ — ایـ یـ ی ای و ز",
            subtitle = "پایه اول دبستان — فارسی",
            lessonIds = listOf(
                "elem1-farsi-08-neshane-06"
            ),
            checkLessonId = "elem1-farsi-08-neshane-06",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 9,
            title = "فارسی اول ۹: نشانه ۷ — اِ و ِ و ـه و ه و شـ ش",
            subtitle = "پایه اول دبستان — فارسی",
            lessonIds = listOf(
                "elem1-farsi-09-neshane-07"
            ),
            checkLessonId = "elem1-farsi-09-neshane-07",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 10,
            title = "فارسی اول ۱۰: نشانه ۸ — یـ ی و اُ و ُ",
            subtitle = "پایه اول دبستان — فارسی",
            lessonIds = listOf(
                "elem1-farsi-10-neshane-08"
            ),
            checkLessonId = "elem1-farsi-10-neshane-08",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 11,
            title = "فارسی اول ۱۱: نشانه ۹ — «کـ ک» و «و»",
            subtitle = "پایه اول دبستان — فارسی",
            lessonIds = listOf(
                "elem1-farsi-11-neshane-09"
            ),
            checkLessonId = "elem1-farsi-11-neshane-09",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 12,
            title = "فارسی اول ۱۲: نشانه ۱۰ — «پـ پ» و «گـ گ»",
            subtitle = "پایه اول دبستان — فارسی",
            lessonIds = listOf(
                "elem1-farsi-12-neshane-10"
            ),
            checkLessonId = "elem1-farsi-12-neshane-10",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 13,
            title = "فارسی اول ۱۳: نشانه ۱۱ — «فـ ف» و «خـ خ»",
            subtitle = "پایه اول دبستان — فارسی",
            lessonIds = listOf(
                "elem1-farsi-13-neshane-11"
            ),
            checkLessonId = "elem1-farsi-13-neshane-11",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 14,
            title = "فارسی اول ۱۴: نشانه ۱۲ — «قـ ق» و «لـ ل»",
            subtitle = "پایه اول دبستان — فارسی",
            lessonIds = listOf(
                "elem1-farsi-14-neshane-12"
            ),
            checkLessonId = "elem1-farsi-14-neshane-12",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 15,
            title = "فارسی اول ۱۵: نشانه ۱۳ — «جـ ج» و «ـُ استثنا»",
            subtitle = "پایه اول دبستان — فارسی",
            lessonIds = listOf(
                "elem1-farsi-15-neshane-13"
            ),
            checkLessonId = "elem1-farsi-15-neshane-13",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 16,
            title = "فارسی اول ۱۶: نشانه ۱۴ — «هـ ـهـ ـه ه» و «چـ چ»",
            subtitle = "پایه اول دبستان — فارسی",
            lessonIds = listOf(
                "elem1-farsi-16-neshane-14"
            ),
            checkLessonId = "elem1-farsi-16-neshane-14",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 17,
            title = "فارسی اول ۱۷: نشانه ۱۵ — «ژ» و «خوا»",
            subtitle = "پایه اول دبستان — فارسی",
            lessonIds = listOf(
                "elem1-farsi-17-neshane-15"
            ),
            checkLessonId = "elem1-farsi-17-neshane-15",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 18,
            title = "فارسی اول ۱۸: نشانه ۱۶ — تشدید",
            subtitle = "پایه اول دبستان — فارسی",
            lessonIds = listOf(
                "elem1-farsi-18-neshane-16"
            ),
            checkLessonId = "elem1-farsi-18-neshane-16",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 19,
            title = "فارسی اول ۱۹: نشانه ۱۷ — «صـ ص» و «ذ»",
            subtitle = "پایه اول دبستان — فارسی",
            lessonIds = listOf(
                "elem1-farsi-19-neshane-17"
            ),
            checkLessonId = "elem1-farsi-19-neshane-17",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 20,
            title = "فارسی اول ۲۰: نشانه ۱۸ — «عـ ـعـ ـع ع» و «ثـ ث»",
            subtitle = "پایه اول دبستان — فارسی",
            lessonIds = listOf(
                "elem1-farsi-20-neshane-18"
            ),
            checkLessonId = "elem1-farsi-20-neshane-18",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 21,
            title = "فارسی اول ۱۹: حلزون — نشانه ح",
            subtitle = "پایه اول دبستان — فارسی",
            lessonIds = listOf(
                "elem1-farsi-21-neshane-19"
            ),
            checkLessonId = "elem1-farsi-21-neshane-19",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 22,
            title = "فارسی اول ۲۰: رضا و خاطرات انقلاب — نشانه ض و ط",
            subtitle = "پایه اول دبستان — فارسی",
            lessonIds = listOf(
                "elem1-farsi-22-neshane-20"
            ),
            checkLessonId = "elem1-farsi-22-neshane-20",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 23,
            title = "فارسی اول ۲۱: لاک‌پشت و مرغابی‌ها — نشانه غ",
            subtitle = "پایه اول دبستان — فارسی",
            lessonIds = listOf(
                "elem1-farsi-23-neshane-21"
            ),
            checkLessonId = "elem1-farsi-23-neshane-21",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 24,
            title = "فارسی اول ۲۲: پیامبر مهربان — نشانه ظ",
            subtitle = "پایه اول دبستان — فارسی",
            lessonIds = listOf(
                "elem1-farsi-24-neshane-22"
            ),
            checkLessonId = "elem1-farsi-24-neshane-22",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 25,
            title = "فارسی اول ۲۵: روان‌خوانی — متن را روان و با معنا بخوانیم",
            subtitle = "پایه اول دبستان — فارسی",
            lessonIds = listOf(
                "elem1-farsi-25-ravan-khani"
            ),
            checkLessonId = "elem1-farsi-25-ravan-khani",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 26,
            title = "ریاضی اول فصل ۱: آشنایی با اعداد",
            subtitle = "پایه اول دبستان — ریاضی",
            lessonIds = listOf(
                "elem1-math-01-ch1-numbers"
            ),
            checkLessonId = "elem1-math-01-ch1-numbers",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 27,
            title = "ریاضی اول فصل ۲: الگوها",
            subtitle = "پایه اول دبستان — ریاضی",
            lessonIds = listOf(
                "elem1-math-02-ch2-patterns"
            ),
            checkLessonId = "elem1-math-02-ch2-patterns",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 28,
            title = "ریاضی اول فصل ۳: جمع و تفریق",
            subtitle = "پایه اول دبستان — ریاضی",
            lessonIds = listOf(
                "elem1-math-03-ch3-add-subtract"
            ),
            checkLessonId = "elem1-math-03-ch3-add-subtract",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 29,
            title = "ریاضی اول فصل ۴: اندازه‌گیری",
            subtitle = "پایه اول دبستان — ریاضی",
            lessonIds = listOf(
                "elem1-math-04-ch4-measurement"
            ),
            checkLessonId = "elem1-math-04-ch4-measurement",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 30,
            title = "ریاضی اول ۵: فصل ۵ — هندسه؛ شکل‌ها دور و بر ما",
            subtitle = "پایه اول دبستان — ریاضی",
            lessonIds = listOf(
                "elem1-math-05-ch5-geometry"
            ),
            checkLessonId = "elem1-math-05-ch5-geometry",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 31,
            title = "ریاضی اول ۶: فصل ۶ — اعداد تا ۲۰؛ یک دسته ده‌تایی و چند تا یکی",
            subtitle = "پایه اول دبستان — ریاضی",
            lessonIds = listOf(
                "elem1-math-06-ch6-numbers-20"
            ),
            checkLessonId = "elem1-math-06-ch6-numbers-20",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 32,
            title = "ریاضی اول ۷: فصل ۷ — جمع و تفریق تا ۲۰",
            subtitle = "پایه اول دبستان — ریاضی",
            lessonIds = listOf(
                "elem1-math-07-ch7-add-subtract-20"
            ),
            checkLessonId = "elem1-math-07-ch7-add-subtract-20",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 33,
            title = "ریاضی اول ۸: فصل ۸ — حل مسئله؛ قصه را بفهم، شکل بکش، حل کن",
            subtitle = "پایه اول دبستان — ریاضی",
            lessonIds = listOf(
                "elem1-math-08-ch8-problem-solving"
            ),
            checkLessonId = "elem1-math-08-ch8-problem-solving",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 34,
            title = "ریاضی اول ۹: فصل ۹ — هندسه؛ تقارن و ساختن شکل",
            subtitle = "پایه اول دبستان — ریاضی",
            lessonIds = listOf(
                "elem1-math-09-ch9-geometry-2"
            ),
            checkLessonId = "elem1-math-09-ch9-geometry-2",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 35,
            title = "ریاضی اول ۱۰: فصل ۱۰ — اعداد تا ۱۰۰؛ ده‌تایی‌ها",
            subtitle = "پایه اول دبستان — ریاضی",
            lessonIds = listOf(
                "elem1-math-10-ch10-numbers-100"
            ),
            checkLessonId = "elem1-math-10-ch10-numbers-100",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 36,
            title = "ریاضی اول ۱۱: فصل ۱۱ — جمع و تفریق تا ۱۰۰",
            subtitle = "پایه اول دبستان — ریاضی",
            lessonIds = listOf(
                "elem1-math-11-ch11-add-subtract-100"
            ),
            checkLessonId = "elem1-math-11-ch11-add-subtract-100",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 37,
            title = "علوم اول ۱: درس ۱ — زنگ علوم؛ نگاه کن و بپرس",
            subtitle = "پایه اول دبستان — علوم تجربی",
            lessonIds = listOf(
                "elem1-science-01-zang-olum"
            ),
            checkLessonId = "elem1-science-01-zang-olum",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 38,
            title = "علوم اول ۲: درس ۲ — سلام به من نگاه کن؛ بدن من و پنج حس",
            subtitle = "پایه اول دبستان — علوم تجربی",
            lessonIds = listOf(
                "elem1-science-02-salam-be-man"
            ),
            checkLessonId = "elem1-science-02-salam-be-man",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 39,
            title = "علوم اول ۳: درس ۳ — سالم باش شاداب باش؛ غذای مفید و بدن سالم",
            subtitle = "پایه اول دبستان — علوم تجربی",
            lessonIds = listOf(
                "elem1-science-03-salem-bash"
            ),
            checkLessonId = "elem1-science-03-salem-bash",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 40,
            title = "علوم اول ۴: دنیای جانوران — از صفر",
            subtitle = "پایه اول دبستان — علوم تجربی",
            lessonIds = listOf(
                "elem1-science-04-donyay-janevaran"
            ),
            checkLessonId = "elem1-science-04-donyay-janevaran",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 41,
            title = "علوم اول ۵: دنیای گیاهان — از صفر",
            subtitle = "پایه اول دبستان — علوم تجربی",
            lessonIds = listOf(
                "elem1-science-05-donyay-giyahan"
            ),
            checkLessonId = "elem1-science-05-donyay-giyahan",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 42,
            title = "علوم اول ۶: زمین خانه پر آب ما — از صفر",
            subtitle = "پایه اول دبستان — علوم تجربی",
            lessonIds = listOf(
                "elem1-science-06-zamin-khane-por-ab"
            ),
            checkLessonId = "elem1-science-06-zamin-khane-por-ab",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 43,
            title = "علوم اول ۷: زمین خانه سنگی ما — از صفر",
            subtitle = "پایه اول دبستان — علوم تجربی",
            lessonIds = listOf(
                "elem1-science-07-zamin-khane-sangi"
            ),
            checkLessonId = "elem1-science-07-zamin-khane-sangi",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 44,
            title = "علوم اول ۸: چه می‌خواهم بسازم — از صفر",
            subtitle = "پایه اول دبستان — علوم تجربی",
            lessonIds = listOf(
                "elem1-science-08-che-mikhaham-besazam"
            ),
            checkLessonId = "elem1-science-08-che-mikhaham-besazam",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 45,
            title = "علوم اول ۹: زمین خانه خاکی ما — از صفر",
            subtitle = "پایه اول دبستان — علوم تجربی",
            lessonIds = listOf(
                "elem1-science-09-zamin-khane-khaki"
            ),
            checkLessonId = "elem1-science-09-zamin-khane-khaki",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 46,
            title = "علوم اول ۱۰: در اطراف ما هوا وجود دارد — از صفر",
            subtitle = "پایه اول دبستان — علوم تجربی",
            lessonIds = listOf(
                "elem1-science-10-hava"
            ),
            checkLessonId = "elem1-science-10-hava",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 47,
            title = "علوم اول ۱۱: دنیای سرد و گرم — از صفر",
            subtitle = "پایه اول دبستان — علوم تجربی",
            lessonIds = listOf(
                "elem1-science-11-donyay-sard-garm"
            ),
            checkLessonId = "elem1-science-11-donyay-sard-garm",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 48,
            title = "علوم اول ۱۲: از خانه تا مدرسه — از صفر",
            subtitle = "پایه اول دبستان — علوم تجربی",
            lessonIds = listOf(
                "elem1-science-12-az-khane-ta-madrese"
            ),
            checkLessonId = "elem1-science-12-az-khane-ta-madrese",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 49,
            title = "علوم اول ۱۳: آهن‌ربای من — از صفر",
            subtitle = "پایه اول دبستان — علوم تجربی",
            lessonIds = listOf(
                "elem1-science-13-ahanroba"
            ),
            checkLessonId = "elem1-science-13-ahanroba",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 50,
            title = "علوم اول ۱۴: از گذشته تا آینده — از صفر",
            subtitle = "پایه اول دبستان — علوم تجربی",
            lessonIds = listOf(
                "elem1-science-14-az-gozashte-ta-ayande"
            ),
            checkLessonId = "elem1-science-14-az-gozashte-ta-ayande",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 51,
            title = "قرآن اول ۱: به نام خدا، بسم الله (سوره توحید) — از صفر",
            subtitle = "پایه اول دبستان — قرآن",
            lessonIds = listOf(
                "elem1-quran-01-be-nam-khoda"
            ),
            checkLessonId = "elem1-quran-01-be-nam-khoda",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 52,
            title = "قرآن اول ۲: نعمت‌های خدا (سوره کوثر، داستان نان) — از صفر",
            subtitle = "پایه اول دبستان — قرآن",
            lessonIds = listOf(
                "elem1-quran-02-nemathaye-khoda"
            ),
            checkLessonId = "elem1-quran-02-nemathaye-khoda",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 53,
            title = "قرآن اول ۳: خانه ما (سوره عصر، هدیه پدر بزرگ) — از صفر",
            subtitle = "پایه اول دبستان — قرآن",
            lessonIds = listOf(
                "elem1-quran-03-khane-ma"
            ),
            checkLessonId = "elem1-quran-03-khane-ma",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 54,
            title = "قرآن اول ۴: قرآن بخوانیم (سوره ناس، مهربان‌ترین معلم، هدیه با ارزش) — از صفر",
            subtitle = "پایه اول دبستان — قرآن",
            lessonIds = listOf(
                "elem1-quran-04-quran-bekhanim"
            ),
            checkLessonId = "elem1-quran-04-quran-bekhanim",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 55,
            title = "قرآن اول ۵: کودک مسلمان (سوره حمد) — از صفر",
            subtitle = "پایه اول دبستان — قرآن",
            lessonIds = listOf(
                "elem1-quran-05-kudak-mosalman"
            ),
            checkLessonId = "elem1-quran-05-kudak-mosalman",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 56,
            title = "قرآن اول ۶: مدرسه ما (سوره فلق، کلاس قرآن) — از صفر",
            subtitle = "پایه اول دبستان — قرآن",
            lessonIds = listOf(
                "elem1-quran-06-madrese-ma"
            ),
            checkLessonId = "elem1-quran-06-madrese-ma",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 57,
            title = "قرآن اول ۷: پیامبران خدا (سوره نصر، کودکی حضرت موسی) — از صفر",
            subtitle = "پایه اول دبستان — قرآن",
            lessonIds = listOf(
                "elem1-quran-07-payambaran-khoda"
            ),
            checkLessonId = "elem1-quran-07-payambaran-khoda",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 58,
            title = "قرآن اول ۸: درس آخر — سوره‌های فیل، ماعون، کافرون، شرح، قدر و تین — از صفر",
            subtitle = "پایه اول دبستان — قرآن",
            lessonIds = listOf(
                "elem1-quran-08-dars-akhar"
            ),
            checkLessonId = "elem1-quran-08-dars-akhar",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 59,
            title = "فارسی دوم، درس ۱: کتابخانه کلاس ما",
            subtitle = "پایه دوم دبستان — فارسی",
            lessonIds = listOf(
                "elem2-farsi-01-ketabkhaneh-kelas"
            ),
            checkLessonId = "elem2-farsi-01-ketabkhaneh-kelas",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 60,
            title = "فارسی دوم، درس ۲: مسجد محله ما",
            subtitle = "پایه دوم دبستان — فارسی",
            lessonIds = listOf(
                "elem2-farsi-02-masjed-mahalleh"
            ),
            checkLessonId = "elem2-farsi-02-masjed-mahalleh",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 61,
            title = "فارسی دوم، درس ۳: خرس کوچولو",
            subtitle = "پایه دوم دبستان — فارسی",
            lessonIds = listOf(
                "elem2-farsi-03-khers-koochooloo"
            ),
            checkLessonId = "elem2-farsi-03-khers-koochooloo",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 62,
            title = "فارسی دوم، درس ۴: مدرسه خرگوش‌ها",
            subtitle = "پایه دوم دبستان — فارسی",
            lessonIds = listOf(
                "elem2-farsi-04-madrese-khargooshha"
            ),
            checkLessonId = "elem2-farsi-04-madrese-khargooshha",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 63,
            title = "فارسی دوم، درس ۵: چوپان درستکار",
            subtitle = "پایه دوم دبستان — فارسی",
            lessonIds = listOf(
                "elem2-farsi-05-choopan-dorostkar"
            ),
            checkLessonId = "elem2-farsi-05-choopan-dorostkar",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 64,
            title = "فارسی دوم، درس ۶: کوشا و نوشا",
            subtitle = "پایه دوم دبستان — فارسی",
            lessonIds = listOf(
                "elem2-farsi-06-koosha-va-noosha"
            ),
            checkLessonId = "elem2-farsi-06-koosha-va-noosha",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 65,
            title = "فارسی دوم، درس ۷: دوستان ما",
            subtitle = "پایه دوم دبستان — فارسی",
            lessonIds = listOf(
                "elem2-farsi-07-doostan-ma"
            ),
            checkLessonId = "elem2-farsi-07-doostan-ma",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 66,
            title = "فارسی دوم، درس ۸: از همه مهربان‌تر",
            subtitle = "پایه دوم دبستان — فارسی",
            lessonIds = listOf(
                "elem2-farsi-08-az-hame-mehraban-tar"
            ),
            checkLessonId = "elem2-farsi-08-az-hame-mehraban-tar",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 67,
            title = "فارسی دوم، درس ۹: زیارت",
            subtitle = "پایه دوم دبستان — فارسی",
            lessonIds = listOf(
                "elem2-farsi-09-ziyarat"
            ),
            checkLessonId = "elem2-farsi-09-ziyarat",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 68,
            title = "فارسی دوم، درس ۱۰: هنرمند",
            subtitle = "پایه دوم دبستان — فارسی",
            lessonIds = listOf(
                "elem2-farsi-10-honarmand"
            ),
            checkLessonId = "elem2-farsi-10-honarmand",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 69,
            title = "فارسی دوم ۱۱: درس آزاد — خودمان متن را انتخاب می‌کنیم",
            subtitle = "پایه دوم دبستان — فارسی",
            lessonIds = listOf(
                "elem2-farsi-11-dars-azad"
            ),
            checkLessonId = "elem2-farsi-11-dars-azad",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 70,
            title = "فارسی دوم ۱۲: فردوسی — شاعری که زبان فارسی را نجات داد",
            subtitle = "پایه دوم دبستان — فارسی",
            lessonIds = listOf(
                "elem2-farsi-12-ferdowsi"
            ),
            checkLessonId = "elem2-farsi-12-ferdowsi",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 71,
            title = "فارسی دوم ۱۳: ایران زیبا — کشور ما چه جاهای زیبایی دارد؟",
            subtitle = "پایه دوم دبستان — فارسی",
            lessonIds = listOf(
                "elem2-farsi-13-iran-ziba"
            ),
            checkLessonId = "elem2-farsi-13-iran-ziba",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 72,
            title = "فارسی دوم ۱۴: پرچم — سه رنگ زیبای ایران",
            subtitle = "پایه دوم دبستان — فارسی",
            lessonIds = listOf(
                "elem2-farsi-14-parcham"
            ),
            checkLessonId = "elem2-farsi-14-parcham",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 73,
            title = "فارسی دوم ۱۵: نوروز — جشن بهار و سال نو",
            subtitle = "پایه دوم دبستان — فارسی",
            lessonIds = listOf(
                "elem2-farsi-15-nowruz"
            ),
            checkLessonId = "elem2-farsi-15-nowruz",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 74,
            title = "فارسی دوم ۱۶: پرواز قطره — سفر یک قطره آب",
            subtitle = "پایه دوم دبستان — فارسی",
            lessonIds = listOf(
                "elem2-farsi-16-parvaz-ghatreh"
            ),
            checkLessonId = "elem2-farsi-16-parvaz-ghatreh",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 75,
            title = "فارسی دوم ۱۷: مثل دانشمندان — مثل یک دانشمند فکر کن",
            subtitle = "پایه دوم دبستان — فارسی",
            lessonIds = listOf(
                "elem2-farsi-17-mesl-daneshmandan"
            ),
            checkLessonId = "elem2-farsi-17-mesl-daneshmandan",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 76,
            title = "نگارش دوم ۱: کتابخانه کلاس ما — نوشتن درباره کتابخانه",
            subtitle = "پایه دوم دبستان — نگارش",
            lessonIds = listOf(
                "elem2-negaresh-01-ketabkhaneh-kelas-ma"
            ),
            checkLessonId = "elem2-negaresh-01-ketabkhaneh-kelas-ma",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 77,
            title = "نگارش دوم ۲: مسجد محله ما — نوشتن درباره مسجد",
            subtitle = "پایه دوم دبستان — نگارش",
            lessonIds = listOf(
                "elem2-negaresh-02-masjed-mahalleh-ma"
            ),
            checkLessonId = "elem2-negaresh-02-masjed-mahalleh-ma",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 78,
            title = "نگارش دوم ۳: خرس کوچولو — نوشتن درباره خرس کوچولو",
            subtitle = "پایه دوم دبستان — نگارش",
            lessonIds = listOf(
                "elem2-negaresh-03-khers-koochooloo"
            ),
            checkLessonId = "elem2-negaresh-03-khers-koochooloo",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 79,
            title = "نگارش دوم ۴: مدرسه خرگوش‌ها — جمله کامل بسازیم",
            subtitle = "پایه دوم دبستان — نگارش",
            lessonIds = listOf(
                "elem2-negaresh-04-madrese-khargooshha"
            ),
            checkLessonId = "elem2-negaresh-04-madrese-khargooshha",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 80,
            title = "نگارش دوم ۵: چوپان درستکار — جمله درست و راست بنویس",
            subtitle = "پایه دوم دبستان — نگارش",
            lessonIds = listOf(
                "elem2-negaresh-05-choopan-dorostkar"
            ),
            checkLessonId = "elem2-negaresh-05-choopan-dorostkar",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 81,
            title = "نگارش دوم ۶: کوشا و نوشا — جمله‌ها را مرتب کن",
            subtitle = "پایه دوم دبستان — نگارش",
            lessonIds = listOf(
                "elem2-negaresh-06-koosha-va-noosha"
            ),
            checkLessonId = "elem2-negaresh-06-koosha-va-noosha",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 82,
            title = "نگارش دوم ۷: دوستان ما — درباره دوستم بنویس",
            subtitle = "پایه دوم دبستان — نگارش",
            lessonIds = listOf(
                "elem2-negaresh-07-doustan-ma"
            ),
            checkLessonId = "elem2-negaresh-07-doustan-ma",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 83,
            title = "نگارش دوم ۸: از همه مهربان‌تر — مهربانی را بنویس",
            subtitle = "پایه دوم دبستان — نگارش",
            lessonIds = listOf(
                "elem2-negaresh-08-az-hame-mehraban-tar"
            ),
            checkLessonId = "elem2-negaresh-08-az-hame-mehraban-tar",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 84,
            title = "نگارش دوم ۹: زیارت — خاطره‌ات را بنویس",
            subtitle = "پایه دوم دبستان — نگارش",
            lessonIds = listOf(
                "elem2-negaresh-09-ziyarat"
            ),
            checkLessonId = "elem2-negaresh-09-ziyarat",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 85,
            title = "نگارش دوم ۱۰: هنرمند — با حواست توصیف کن",
            subtitle = "پایه دوم دبستان — نگارش",
            lessonIds = listOf(
                "elem2-negaresh-10-honarmand"
            ),
            checkLessonId = "elem2-negaresh-10-honarmand",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 86,
            title = "نگارش دوم ۱۱: درس آزاد — موضوع دلخواهت را بنویس",
            subtitle = "پایه دوم دبستان — نگارش",
            lessonIds = listOf(
                "elem2-negaresh-11-dars-azad"
            ),
            checkLessonId = "elem2-negaresh-11-dars-azad",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 87,
            title = "نگارش دوم ۱۲: فردوسی — درباره یک شخص بزرگ بنویس",
            subtitle = "پایه دوم دبستان — نگارش",
            lessonIds = listOf(
                "elem2-negaresh-12-ferdowsi"
            ),
            checkLessonId = "elem2-negaresh-12-ferdowsi",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 88,
            title = "نگارش دوم ۱۳: ایران زیبا — کشورم را توصیف کن",
            subtitle = "پایه دوم دبستان — نگارش",
            lessonIds = listOf(
                "elem2-negaresh-13-iran-ziba"
            ),
            checkLessonId = "elem2-negaresh-13-iran-ziba",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 89,
            title = "نگارش دوم ۱۴: پرچم — یک جمله کامل درباره پرچم ایران بنویس",
            subtitle = "پایه دوم دبستان — نگارش",
            lessonIds = listOf(
                "elem2-negaresh-14-parcham"
            ),
            checkLessonId = "elem2-negaresh-14-parcham",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 90,
            title = "نگارش دوم ۱۵: نوروز — خاطره عید را به ترتیب بنویس",
            subtitle = "پایه دوم دبستان — نگارش",
            lessonIds = listOf(
                "elem2-negaresh-15-norouz"
            ),
            checkLessonId = "elem2-negaresh-15-norouz",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 91,
            title = "نگارش دوم ۱۶: پرواز قطره — از زبان یک قطره آب بنویس",
            subtitle = "پایه دوم دبستان — نگارش",
            lessonIds = listOf(
                "elem2-negaresh-16-parvaz-ghatre"
            ),
            checkLessonId = "elem2-negaresh-16-parvaz-ghatre",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 92,
            title = "نگارش دوم ۱۷: مثل دانشمندان — مشاهده‌ات را بنویس",
            subtitle = "پایه دوم دبستان — نگارش",
            lessonIds = listOf(
                "elem2-negaresh-17-mesl-daneshmandan"
            ),
            checkLessonId = "elem2-negaresh-17-mesl-daneshmandan",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 93,
            title = "ریاضی دوم فصل ۱: عدد و رقم — رقم‌ها آجرهای عدد هستند",
            subtitle = "پایه دوم دبستان — ریاضی",
            lessonIds = listOf(
                "elem2-math-01-adad-ragham"
            ),
            checkLessonId = "elem2-math-01-adad-ragham",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 94,
            title = "ریاضی دوم فصل ۲: جمع و تفریق اعداد دو رقمی — با انتقال و قرض",
            subtitle = "پایه دوم دبستان — ریاضی",
            lessonIds = listOf(
                "elem2-math-02-jam-tafrigh-doraghami"
            ),
            checkLessonId = "elem2-math-02-jam-tafrigh-doraghami",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 95,
            title = "ریاضی دوم فصل ۳: اشکال هندسی — دایره، مربع، مستطیل و مثلث",
            subtitle = "پایه دوم دبستان — ریاضی",
            lessonIds = listOf(
                "elem2-math-03-ashkal-handasi"
            ),
            checkLessonId = "elem2-math-03-ashkal-handasi",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 96,
            title = "ریاضی دوم فصل ۴: عددهای سه رقمی — صدتایی هم آمد",
            subtitle = "پایه دوم دبستان — ریاضی",
            lessonIds = listOf(
                "elem2-math-04-adad-se-raghami"
            ),
            checkLessonId = "elem2-math-04-adad-se-raghami",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 97,
            title = "ریاضی دوم فصل ۵: اندازه‌گیری — طول، جرم، زمان و گنجایش",
            subtitle = "پایه دوم دبستان — ریاضی",
            lessonIds = listOf(
                "elem2-math-05-andazegiri"
            ),
            checkLessonId = "elem2-math-05-andazegiri",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 98,
            title = "ریاضی دوم فصل ۶: جمع و تفریق اعداد سه رقمی — با انتقال و قرض",
            subtitle = "پایه دوم دبستان — ریاضی",
            lessonIds = listOf(
                "elem2-math-06-jam-tafrigh-se-raghami"
            ),
            checkLessonId = "elem2-math-06-jam-tafrigh-se-raghami",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 99,
            title = "ریاضی دوم ۷: کسر و احتمال — از صفر",
            subtitle = "پایه دوم دبستان — ریاضی",
            lessonIds = listOf(
                "elem2-math-07-kasr-ehtemal"
            ),
            checkLessonId = "elem2-math-07-kasr-ehtemal",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 100,
            title = "ریاضی دوم ۸: آمار و نمودار — از صفر",
            subtitle = "پایه دوم دبستان — ریاضی",
            lessonIds = listOf(
                "elem2-math-08-amar-nemudar"
            ),
            checkLessonId = "elem2-math-08-amar-nemudar",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 101,
            title = "علوم دوم ۱: زنگ علوم، گردش در باغ — از صفر",
            subtitle = "پایه دوم دبستان — علوم تجربی",
            lessonIds = listOf(
                "elem2-science-01-zang-olum"
            ),
            checkLessonId = "elem2-science-01-zang-olum",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 102,
            title = "علوم دوم ۲: هوای سالم، آب سالم — از صفر",
            subtitle = "پایه دوم دبستان — علوم تجربی",
            lessonIds = listOf(
                "elem2-science-02-havay-salem"
            ),
            checkLessonId = "elem2-science-02-havay-salem",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 103,
            title = "علوم دوم ۳: زندگی ما و گردش زمین (۱) — از صفر",
            subtitle = "پایه دوم دبستان — علوم تجربی",
            lessonIds = listOf(
                "elem2-science-03-gardesh-zamin-1"
            ),
            checkLessonId = "elem2-science-03-gardesh-zamin-1",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 104,
            title = "علوم دوم ۴: زندگی ما و گردش زمین (۲) — از صفر",
            subtitle = "پایه دوم دبستان — علوم تجربی",
            lessonIds = listOf(
                "elem2-science-04-gardesh-zamin-2"
            ),
            checkLessonId = "elem2-science-04-gardesh-zamin-2",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 105,
            title = "علوم دوم ۵: پیام رمز را پیدا کن (۱) — از صفر",
            subtitle = "پایه دوم دبستان — علوم تجربی",
            lessonIds = listOf(
                "elem2-science-05-payam-ramz-seda"
            ),
            checkLessonId = "elem2-science-05-payam-ramz-seda",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 106,
            title = "علوم دوم ۶: پیام رمز را پیدا کن (۲) — از صفر",
            subtitle = "پایه دوم دبستان — علوم تجربی",
            lessonIds = listOf(
                "elem2-science-06-payam-ramz-nur"
            ),
            checkLessonId = "elem2-science-06-payam-ramz-nur",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 107,
            title = "علوم دوم ۷: اگر تمام شود… — از صفر",
            subtitle = "پایه دوم دبستان — علوم تجربی",
            lessonIds = listOf(
                "elem2-science-07-agar-tamam-shavad"
            ),
            checkLessonId = "elem2-science-07-agar-tamam-shavad",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 108,
            title = "علوم دوم ۸: بسازیم و لذت ببریم — از صفر",
            subtitle = "پایه دوم دبستان — علوم تجربی",
            lessonIds = listOf(
                "elem2-science-08-besazim-lezzat"
            ),
            checkLessonId = "elem2-science-08-besazim-lezzat",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 109,
            title = "علوم دوم ۹: سرگذشت دانه — دانه چطور گیاه می‌شود؟",
            subtitle = "پایه دوم دبستان — علوم تجربی",
            lessonIds = listOf(
                "elem2-science-09-سرگذشت-دانه"
            ),
            checkLessonId = "elem2-science-09-سرگذشت-دانه",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 110,
            title = "علوم دوم ۱۰: درون آشیانه‌ها — خانه پرنده‌ها",
            subtitle = "پایه دوم دبستان — علوم تجربی",
            lessonIds = listOf(
                "elem2-science-10-درون-آشیانه‌ها"
            ),
            checkLessonId = "elem2-science-10-درون-آشیانه‌ها",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 111,
            title = "علوم دوم ۱۱: من رشد می‌کنم — بزرگ شدن من",
            subtitle = "پایه دوم دبستان — علوم تجربی",
            lessonIds = listOf(
                "elem2-science-11-من-رشد-می‌کنم"
            ),
            checkLessonId = "elem2-science-11-من-رشد-می‌کنم",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 112,
            title = "علوم دوم ۱۲: برای جشن آماده شویم — جشن یعنی دور هم شادی",
            subtitle = "پایه دوم دبستان — علوم تجربی",
            lessonIds = listOf(
                "elem2-science-12-برای-جشن-آماده-شویم"
            ),
            checkLessonId = "elem2-science-12-برای-جشن-آماده-شویم",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 113,
            title = "علوم دوم ۱۳: بعد از جشن — تمیز کردن و نگه داشتن",
            subtitle = "پایه دوم دبستان — علوم تجربی",
            lessonIds = listOf(
                "elem2-science-13-بعد-از-جشن"
            ),
            checkLessonId = "elem2-science-13-بعد-از-جشن",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 114,
            title = "علوم دوم ۱۴: از گذشته تا آینده — سرگذشت نان",
            subtitle = "پایه دوم دبستان — علوم تجربی",
            lessonIds = listOf(
                "elem2-science-14-از-گذشته-تا-آینده"
            ),
            checkLessonId = "elem2-science-14-از-گذشته-تا-آینده",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 115,
            title = "هدیه دوم ۱: هدیه‌های خدا — همه‌چیز هدیه خداست",
            subtitle = "پایه دوم دبستان — هدیه‌های آسمانی",
            lessonIds = listOf(
                "elem2-hedyeh-01-هدیه‌های-خدا"
            ),
            checkLessonId = "elem2-hedyeh-01-هدیه‌های-خدا",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 116,
            title = "هدیه دوم ۲: پرندگان چه می‌گویند؟ — آواز پرنده‌ها",
            subtitle = "پایه دوم دبستان — هدیه‌های آسمانی",
            lessonIds = listOf(
                "elem2-hedyeh-02-پرندگان-چه-می‌گویند"
            ),
            checkLessonId = "elem2-hedyeh-02-پرندگان-چه-می‌گویند",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 117,
            title = "هدیه دوم ۳: خاطره‌ی ماه — ماه در آسمان شب",
            subtitle = "پایه دوم دبستان — هدیه‌های آسمانی",
            lessonIds = listOf(
                "elem2-hedyeh-03-خاطره‌ی-ماه"
            ),
            checkLessonId = "elem2-hedyeh-03-خاطره‌ی-ماه",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 118,
            title = "هدیه دوم ۴: مهربان‌تر از مادر — خدا از همه مهربان‌تر است",
            subtitle = "پایه دوم دبستان — هدیه‌های آسمانی",
            lessonIds = listOf(
                "elem2-hedyeh-04-مهربان‌تر-از-مادر"
            ),
            checkLessonId = "elem2-hedyeh-04-مهربان‌تر-از-مادر",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 119,
            title = "هدیه دوم ۵: می‌خواهم وضو بگیرم — آماده شدن برای حرف زدن با خدا",
            subtitle = "پایه دوم دبستان — هدیه‌های آسمانی",
            lessonIds = listOf(
                "elem2-hedyeh-05-vozou"
            ),
            checkLessonId = "elem2-hedyeh-05-vozou",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 120,
            title = "هدیه دوم ۶: پیامبران خدا — راهنماهای مهربان مردم",
            subtitle = "پایه دوم دبستان — هدیه‌های آسمانی",
            lessonIds = listOf(
                "elem2-hedyeh-06-payambaran"
            ),
            checkLessonId = "elem2-hedyeh-06-payambaran",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 121,
            title = "هدیه دوم ۷: مهمان کوچک — مهربانی پیامبر با بچه‌ها",
            subtitle = "پایه دوم دبستان — هدیه‌های آسمانی",
            lessonIds = listOf(
                "elem2-hedyeh-07-mehman-kochak"
            ),
            checkLessonId = "elem2-hedyeh-07-mehman-kochak",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 122,
            title = "هدیه دوم ۸: جشن میلاد — تولد پیامبر خدا",
            subtitle = "پایه دوم دبستان — هدیه‌های آسمانی",
            lessonIds = listOf(
                "elem2-hedyeh-08-jashn-milad"
            ),
            checkLessonId = "elem2-hedyeh-08-jashn-milad",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 123,
            title = "هدیه دوم ۹: اهل بیت پیامبر — خانواده پیامبر خدا",
            subtitle = "پایه دوم دبستان — هدیه‌های آسمانی",
            lessonIds = listOf(
                "elem2-hedyeh-09-ahle-beyt"
            ),
            checkLessonId = "elem2-hedyeh-09-ahle-beyt",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 124,
            title = "هدیه دوم ۱۰: خانواده‌ی بخشنده — بخشش یعنی دادن با خوشحالی",
            subtitle = "پایه دوم دبستان — هدیه‌های آسمانی",
            lessonIds = listOf(
                "elem2-hedyeh-10-khanevade-bakhshande"
            ),
            checkLessonId = "elem2-hedyeh-10-khanevade-bakhshande",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 125,
            title = "هدیه دوم ۱۱: نماز بخوانیم — بهترین راه حرف زدن با خدا",
            subtitle = "پایه دوم دبستان — هدیه‌های آسمانی",
            lessonIds = listOf(
                "elem2-hedyeh-11-namaz"
            ),
            checkLessonId = "elem2-hedyeh-11-namaz",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 126,
            title = "هدیه دوم ۱۲: پدر مهربان — امام علی و کودکان یتیم",
            subtitle = "پایه دوم دبستان — هدیه‌های آسمانی",
            lessonIds = listOf(
                "elem2-hedyeh-12-pedar-mehraban"
            ),
            checkLessonId = "elem2-hedyeh-12-pedar-mehraban",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 127,
            title = "هدیه دوم ۱۳: بهترین دوست — امام حسن و مرد مسافر",
            subtitle = "پایه دوم دبستان — هدیه‌های آسمانی",
            lessonIds = listOf(
                "elem2-hedyeh-13-behtarin-doust"
            ),
            checkLessonId = "elem2-hedyeh-13-behtarin-doust",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 128,
            title = "هدیه دوم ۱۴: دعای باران — دعای امام حسین برای مردم کوفه",
            subtitle = "پایه دوم دبستان — هدیه‌های آسمانی",
            lessonIds = listOf(
                "elem2-hedyeh-14-doaye-baran"
            ),
            checkLessonId = "elem2-hedyeh-14-doaye-baran",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 129,
            title = "هدیه دوم ۱۵: بچه‌ها سلام! — از صفر",
            subtitle = "پایه دوم دبستان — هدیه‌های آسمانی",
            lessonIds = listOf(
                "elem2-hedyeh-15-bacheha-salam"
            ),
            checkLessonId = "elem2-hedyeh-15-bacheha-salam",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 130,
            title = "هدیه دوم ۱۶: کیسه‌ی سنجاب کوچولو — از صفر",
            subtitle = "پایه دوم دبستان — هدیه‌های آسمانی",
            lessonIds = listOf(
                "elem2-hedyeh-16-kiseye-sanjab"
            ),
            checkLessonId = "elem2-hedyeh-16-kiseye-sanjab",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 131,
            title = "هدیه دوم ۱۷: وقت نماز — از صفر",
            subtitle = "پایه دوم دبستان — هدیه‌های آسمانی",
            lessonIds = listOf(
                "elem2-hedyeh-17-vaghte-namaz"
            ),
            checkLessonId = "elem2-hedyeh-17-vaghte-namaz",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 132,
            title = "هدیه دوم ۱۸: خاطره‌ی نانوشته — از صفر",
            subtitle = "پایه دوم دبستان — هدیه‌های آسمانی",
            lessonIds = listOf(
                "elem2-hedyeh-18-khatereye-naneveshte"
            ),
            checkLessonId = "elem2-hedyeh-18-khatereye-naneveshte",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 133,
            title = "هدیه دوم ۱۹: جشن بزرگ — از صفر",
            subtitle = "پایه دوم دبستان — هدیه‌های آسمانی",
            lessonIds = listOf(
                "elem2-hedyeh-19-jashne-bozorg"
            ),
            checkLessonId = "elem2-hedyeh-19-jashne-bozorg",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 134,
            title = "هدیه دوم ۲۰: در کنار سفره — از صفر",
            subtitle = "پایه دوم دبستان — هدیه‌های آسمانی",
            lessonIds = listOf(
                "elem2-hedyeh-20-dar-kenare-sofre"
            ),
            checkLessonId = "elem2-hedyeh-20-dar-kenare-sofre",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 135,
            title = "قرآن دوم ۱: الف کوچک و سکون — از صفر",
            subtitle = "پایه دوم دبستان — قرآن",
            lessonIds = listOf(
                "elem2-quran-01-alef-koochak-sokoon"
            ),
            checkLessonId = "elem2-quran-01-alef-koochak-sokoon",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 136,
            title = "قرآن دوم ۲: تشدید و اتصالات — از صفر",
            subtitle = "پایه دوم دبستان — قرآن",
            lessonIds = listOf(
                "elem2-quran-02-tashdid-etessalat"
            ),
            checkLessonId = "elem2-quran-02-tashdid-etessalat",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 137,
            title = "قرآن دوم ۳: سوره حمد و حرف ک — از صفر",
            subtitle = "پایه دوم دبستان — قرآن",
            lessonIds = listOf(
                "elem2-quran-03-sooreye-hamd"
            ),
            checkLessonId = "elem2-quran-03-sooreye-hamd",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 138,
            title = "قرآن دوم ۴: سوره ناس و حرف‌های ـه و ه — از صفر",
            subtitle = "پایه دوم دبستان — قرآن",
            lessonIds = listOf(
                "elem2-quran-04-sooreye-nas"
            ),
            checkLessonId = "elem2-quran-04-sooreye-nas",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 139,
            title = "قرآن دوم ۵: سوره فلق و تنوین‌ها — از صفر",
            subtitle = "پایه دوم دبستان — قرآن",
            lessonIds = listOf(
                "elem2-quran-05-falaq-tanvin"
            ),
            checkLessonId = "elem2-quran-05-falaq-tanvin",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 140,
            title = "قرآن دوم ۶: سوره توحید و «ـه و» و «ـه ی» — از صفر",
            subtitle = "پایه دوم دبستان — قرآن",
            lessonIds = listOf(
                "elem2-quran-06-towhid-he"
            ),
            checkLessonId = "elem2-quran-06-towhid-he",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 141,
            title = "قرآن دوم ۷: سوره نصر، فتح مکه و همزه — از صفر",
            subtitle = "پایه دوم دبستان — قرآن",
            lessonIds = listOf(
                "elem2-quran-07-nasr-hamze"
            ),
            checkLessonId = "elem2-quran-07-nasr-hamze",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 142,
            title = "قرآن دوم ۸: سوره کوثر، «ئـ» و کودک خداپرست — از صفر",
            subtitle = "پایه دوم دبستان — قرآن",
            lessonIds = listOf(
                "elem2-quran-08-kowsar-hamze-ye"
            ),
            checkLessonId = "elem2-quran-08-kowsar-hamze-ye",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 143,
            title = "قرآن دوم ۹: سوره عصر و کارهای خوب — از صفر",
            subtitle = "پایه دوم دبستان — قرآن",
            lessonIds = listOf(
                "elem2-quran-09-asr-kar-khub"
            ),
            checkLessonId = "elem2-quran-09-asr-kar-khub",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 144,
            title = "قرآن دوم ۱۰: سوره فیل، اصحاب فیل و سوره ماعون — از صفر",
            subtitle = "پایه دوم دبستان — قرآن",
            lessonIds = listOf(
                "elem2-quran-10-fil-maun"
            ),
            checkLessonId = "elem2-quran-10-fil-maun",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 145,
            title = "قرآن دوم ۱۱: سوره کافرون و چهار قل — از صفر",
            subtitle = "پایه دوم دبستان — قرآن",
            lessonIds = listOf(
                "elem2-quran-11-kafirun-char-qol"
            ),
            checkLessonId = "elem2-quran-11-kafirun-char-qol",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 146,
            title = "قرآن دوم ۱۲: سوره شرح (انشراح) — پس از هر سختی، آسانی است — از صفر",
            subtitle = "پایه دوم دبستان — قرآن",
            lessonIds = listOf(
                "elem2-quran-12-sharh-inshirah"
            ),
            checkLessonId = "elem2-quran-12-sharh-inshirah",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 147,
            title = "قرآن دوم ۱۳: سوره تین و قهرمان بت‌شکن — از صفر",
            subtitle = "پایه دوم دبستان — قرآن",
            lessonIds = listOf(
                "elem2-quran-13-tin-bot-shekan"
            ),
            checkLessonId = "elem2-quran-13-tin-bot-shekan",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 148,
            title = "قرآن دوم ۱۴: سوره قدر و باغی میان آتش — از صفر",
            subtitle = "پایه دوم دبستان — قرآن",
            lessonIds = listOf(
                "elem2-quran-14-qadr-bagh-atash"
            ),
            checkLessonId = "elem2-quran-14-qadr-bagh-atash",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 149,
            title = "قرآن دوم ۱۵: سخن آخر — قرآن در تابستان — از صفر",
            subtitle = "پایه دوم دبستان — قرآن",
            lessonIds = listOf(
                "elem2-quran-15-tabestan-sokhan-akhar"
            ),
            checkLessonId = "elem2-quran-15-tabestan-sokhan-akhar",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 150,
            title = "فارسی سوم ۱: محله ما — همسایه‌ها و زندگی در محله",
            subtitle = "پایه سوم دبستان — فارسی",
            lessonIds = listOf(
                "elem3-farsi-01-mahalle-ma"
            ),
            checkLessonId = "elem3-farsi-01-mahalle-ma",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 151,
            title = "فارسی سوم ۲: زنگ ورزش — بازی، سلامتی و همکاری",
            subtitle = "پایه سوم دبستان — فارسی",
            lessonIds = listOf(
                "elem3-farsi-02-zang-varzesh"
            ),
            checkLessonId = "elem3-farsi-02-zang-varzesh",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 152,
            title = "فارسی سوم ۳: آسمان آبی، طبیعت پاک — هوای پاک و زمین پاک",
            subtitle = "پایه سوم دبستان — فارسی",
            lessonIds = listOf(
                "elem3-farsi-03-aseman-abi"
            ),
            checkLessonId = "elem3-farsi-03-aseman-abi",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 153,
            title = "فارسی سوم ۴: آواز گنجشک — صدای صبح و زیبایی طبیعت",
            subtitle = "پایه سوم دبستان — فارسی",
            lessonIds = listOf(
                "elem3-farsi-04-avaz-gonjeshk"
            ),
            checkLessonId = "elem3-farsi-04-avaz-gonjeshk",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 154,
            title = "فارسی سوم ۵: بلدرچین و برزگر — سه پند دانا",
            subtitle = "پایه سوم دبستان — فارسی",
            lessonIds = listOf(
                "elem3-farsi-05-boladorchin"
            ),
            checkLessonId = "elem3-farsi-05-boladorchin",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 155,
            title = "فارسی سوم ۶: فداکاران — کسانی که برای دیگران از خود می‌گذرند",
            subtitle = "پایه سوم دبستان — فارسی",
            lessonIds = listOf(
                "elem3-farsi-06-fadakaran"
            ),
            checkLessonId = "elem3-farsi-06-fadakaran",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 156,
            title = "فارسی سوم ۷: کار نیک — نیکی‌های کوچک، دل‌های بزرگ",
            subtitle = "پایه سوم دبستان — فارسی",
            lessonIds = listOf(
                "elem3-farsi-07-kar-nik"
            ),
            checkLessonId = "elem3-farsi-07-kar-nik",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 157,
            title = "فارسی سوم ۸: پیراهن بهشتی — پاداش نیکی و مهربانی",
            subtitle = "پایه سوم دبستان — فارسی",
            lessonIds = listOf(
                "elem3-farsi-08-pirahan-beheshti"
            ),
            checkLessonId = "elem3-farsi-08-pirahan-beheshti",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 158,
            title = "فارسی سوم ۹: بوی نرگس — بوی خوش بهار",
            subtitle = "پایه سوم دبستان — فارسی",
            lessonIds = listOf(
                "elem3-farsi-09-bu-narges"
            ),
            checkLessonId = "elem3-farsi-09-bu-narges",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 159,
            title = "فارسی سوم ۱۰: یار مهربان — کتاب، بهترین دوست",
            subtitle = "پایه سوم دبستان — فارسی",
            lessonIds = listOf(
                "elem3-farsi-10-yar-mehraban"
            ),
            checkLessonId = "elem3-farsi-10-yar-mehraban",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 160,
            title = "فارسی سوم ۱۱: نویسنده بزرگ — سعدی و حکایت حاتم طایی",
            subtitle = "پایه سوم دبستان — فارسی",
            lessonIds = listOf(
                "elem3-farsi-11-nevisande-bozorg"
            ),
            checkLessonId = "elem3-farsi-11-nevisande-bozorg",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 161,
            title = "فارسی سوم ۱۲: ایران عزیز — شعر میهن",
            subtitle = "پایه سوم دبستان — فارسی",
            lessonIds = listOf(
                "elem3-farsi-12-iran-aziz"
            ),
            checkLessonId = "elem3-farsi-12-iran-aziz",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 162,
            title = "فارسی سوم ۱۳: درس آزاد — روش خواندن و فهم متن آزاد",
            subtitle = "پایه سوم دبستان — فارسی",
            lessonIds = listOf(
                "elem3-farsi-13-dars-azad"
            ),
            checkLessonId = "elem3-farsi-13-dars-azad",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 163,
            title = "فارسی سوم ۱۴: ایران آباد — تلاش برای آبادانی میهن",
            subtitle = "پایه سوم دبستان — فارسی",
            lessonIds = listOf(
                "elem3-farsi-14-iran-abad"
            ),
            checkLessonId = "elem3-farsi-14-iran-abad",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 164,
            title = "فارسی سوم ۱۵: دریا — شعر دریا و تشبیه",
            subtitle = "پایه سوم دبستان — فارسی",
            lessonIds = listOf(
                "elem3-farsi-15-darya"
            ),
            checkLessonId = "elem3-farsi-15-darya",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 165,
            title = "فارسی سوم ۱۶: اگر جنگل نباشد — اهمیت جنگل",
            subtitle = "پایه سوم دبستان — فارسی",
            lessonIds = listOf(
                "elem3-farsi-16-agar-jangal-nabashad"
            ),
            checkLessonId = "elem3-farsi-16-agar-jangal-nabashad",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 166,
            title = "فارسی سوم ۱۷: چشم‌های آسمان — ستاره‌ها و آسمان",
            subtitle = "پایه سوم دبستان — فارسی",
            lessonIds = listOf(
                "elem3-farsi-17-cheshmhaye-aseman"
            ),
            checkLessonId = "elem3-farsi-17-cheshmhaye-aseman",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 167,
            title = "نگارش سوم ۱: محله ما — توصیف محله با حواس پنج‌گانه",
            subtitle = "پایه سوم دبستان — نگارش",
            lessonIds = listOf(
                "elem3-negaresh-01-mahalle-ma"
            ),
            checkLessonId = "elem3-negaresh-01-mahalle-ma",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 168,
            title = "نگارش سوم ۲: زنگ ورزش — نوشتن با ترتیب زمانی",
            subtitle = "پایه سوم دبستان — نگارش",
            lessonIds = listOf(
                "elem3-negaresh-02-zang-varzesh"
            ),
            checkLessonId = "elem3-negaresh-02-zang-varzesh",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 169,
            title = "نگارش سوم ۳: آسمان آبی، طبیعت پاک — نوشتن درباره طبیعت",
            subtitle = "پایه سوم دبستان — نگارش",
            lessonIds = listOf(
                "elem3-negaresh-03-aseman-abi"
            ),
            checkLessonId = "elem3-negaresh-03-aseman-abi",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 170,
            title = "نگارش سوم ۴: آواز گنجشک — توصیف صداها",
            subtitle = "پایه سوم دبستان — نگارش",
            lessonIds = listOf(
                "elem3-negaresh-04-avaz-gonjeshk"
            ),
            checkLessonId = "elem3-negaresh-04-avaz-gonjeshk",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 171,
            title = "نگارش سوم ۵: بلدرچین و برزگر — بازنویسی حکایت",
            subtitle = "پایه سوم دبستان — نگارش",
            lessonIds = listOf(
                "elem3-negaresh-05-boladorchin"
            ),
            checkLessonId = "elem3-negaresh-05-boladorchin",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 172,
            title = "نگارش سوم ۶: فداکاران — نوشتن درباره فداکاری",
            subtitle = "پایه سوم دبستان — نگارش",
            lessonIds = listOf(
                "elem3-negaresh-06-fadakaran"
            ),
            checkLessonId = "elem3-negaresh-06-fadakaran",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 173,
            title = "نگارش سوم ۷: کار نیک — نوشتن درباره کار نیک",
            subtitle = "پایه سوم دبستان — نگارش",
            lessonIds = listOf(
                "elem3-negaresh-07-kar-nik"
            ),
            checkLessonId = "elem3-negaresh-07-kar-nik",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 174,
            title = "نگارش سوم ۸: پیراهن بهشتی — نوشتن درباره بخشش",
            subtitle = "پایه سوم دبستان — نگارش",
            lessonIds = listOf(
                "elem3-negaresh-08-pirahan-beheshti"
            ),
            checkLessonId = "elem3-negaresh-08-pirahan-beheshti",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 175,
            title = "نگارش سوم ۹: بوی نرگس — نوشتن با حس بویایی",
            subtitle = "پایه سوم دبستان — نگارش",
            lessonIds = listOf(
                "elem3-negaresh-09-bu-narges"
            ),
            checkLessonId = "elem3-negaresh-09-bu-narges",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 176,
            title = "نگارش سوم ۱۰: یار مهربان — نوشتن درباره کتاب",
            subtitle = "پایه سوم دبستان — نگارش",
            lessonIds = listOf(
                "elem3-negaresh-10-yar-mehraban"
            ),
            checkLessonId = "elem3-negaresh-10-yar-mehraban",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 177,
            title = "نگارش سوم ۱۱: نویسنده بزرگ — نوشتن درباره سعدی",
            subtitle = "پایه سوم دبستان — نگارش",
            lessonIds = listOf(
                "elem3-negaresh-11-nevisande-bozorg"
            ),
            checkLessonId = "elem3-negaresh-11-nevisande-bozorg",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 178,
            title = "نگارش سوم ۱۲: ایران عزیز — نوشتن درباره وطن",
            subtitle = "پایه سوم دبستان — نگارش",
            lessonIds = listOf(
                "elem3-negaresh-12-iran-aziz"
            ),
            checkLessonId = "elem3-negaresh-12-iran-aziz",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 179,
            title = "نگارش سوم ۱۳: درس آزاد — روش نوشتن درباره متن آزاد",
            subtitle = "پایه سوم دبستان — نگارش",
            lessonIds = listOf(
                "elem3-negaresh-13-dars-azad"
            ),
            checkLessonId = "elem3-negaresh-13-dars-azad",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 180,
            title = "نگارش سوم ۱۴: ایران آباد — نوشتن درباره آبادانی ایران",
            subtitle = "پایه سوم دبستان — نگارش",
            lessonIds = listOf(
                "elem3-negaresh-14-iran-abad"
            ),
            checkLessonId = "elem3-negaresh-14-iran-abad",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 181,
            title = "نگارش سوم ۱۵: دریا — توصیف دریا با حواس پنج‌گانه",
            subtitle = "پایه سوم دبستان — نگارش",
            lessonIds = listOf(
                "elem3-negaresh-15-darya"
            ),
            checkLessonId = "elem3-negaresh-15-darya",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 182,
            title = "نگارش سوم ۱۶: اگر جنگل نباشد — نوشتن علت و نتیجه",
            subtitle = "پایه سوم دبستان — نگارش",
            lessonIds = listOf(
                "elem3-negaresh-16-agar-jangal-nabashad"
            ),
            checkLessonId = "elem3-negaresh-16-agar-jangal-nabashad",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 183,
            title = "نگارش سوم ۱۷: چشم‌های آسمان — توصیف آسمان شب و ستاره‌ها",
            subtitle = "پایه سوم دبستان — نگارش",
            lessonIds = listOf(
                "elem3-negaresh-17-cheshmhaye-aseman"
            ),
            checkLessonId = "elem3-negaresh-17-cheshmhaye-aseman",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 184,
            title = "ریاضی سوم فصل ۱: الگوها — الگویابی و شمارش چندتا چندتا",
            subtitle = "پایه سوم دبستان — ریاضی",
            lessonIds = listOf(
                "elem3-math-01-olguha"
            ),
            checkLessonId = "elem3-math-01-olguha",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 185,
            title = "ریاضی سوم فصل ۲: عددهای چهار رقمی — هزار و ارزش مکانی",
            subtitle = "پایه سوم دبستان — ریاضی",
            lessonIds = listOf(
                "elem3-math-02-adadhaye-chahar-raghami"
            ),
            checkLessonId = "elem3-math-02-adadhaye-chahar-raghami",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 186,
            title = "ریاضی سوم فصل ۳: عددهای کسری — کسر چیست و کسرها با هم برابرند؟",
            subtitle = "پایه سوم دبستان — ریاضی",
            lessonIds = listOf(
                "elem3-math-03-adadhaye-kesri"
            ),
            checkLessonId = "elem3-math-03-adadhaye-kesri",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 187,
            title = "ریاضی سوم فصل ۴: ضرب و تقسیم — ضرب عددهای یک رقمی و تقسیم",
            subtitle = "پایه سوم دبستان — ریاضی",
            lessonIds = listOf(
                "elem3-math-04-zarb-taqsim"
            ),
            checkLessonId = "elem3-math-04-zarb-taqsim",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 188,
            title = "ریاضی سوم فصل ۵: محیط و مساحت — دور تا دور و سطح داخل شکل",
            subtitle = "پایه سوم دبستان — ریاضی",
            lessonIds = listOf(
                "elem3-math-05-mohit-masahat"
            ),
            checkLessonId = "elem3-math-05-mohit-masahat",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 189,
            title = "ریاضی سوم فصل ۶: جمع و تفریق — جمع و تفریق عددهای چهار رقمی",
            subtitle = "پایه سوم دبستان — ریاضی",
            lessonIds = listOf(
                "elem3-math-06-jam-tafriq"
            ),
            checkLessonId = "elem3-math-06-jam-tafriq",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 190,
            title = "ریاضی سوم فصل ۷: آمار و احتمال — از صفر",
            subtitle = "پایه سوم دبستان — ریاضی",
            lessonIds = listOf(
                "elem3-math-07-amar-ehtemal"
            ),
            checkLessonId = "elem3-math-07-amar-ehtemal",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 191,
            title = "ریاضی سوم فصل ۸: ضرب عددها — از صفر",
            subtitle = "پایه سوم دبستان — ریاضی",
            lessonIds = listOf(
                "elem3-math-08-zarb-adadha"
            ),
            checkLessonId = "elem3-math-08-zarb-adadha",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 192,
            title = "علوم سوم درس ۱: زنگ علوم — از صفر",
            subtitle = "پایه سوم دبستان — علوم تجربی",
            lessonIds = listOf(
                "elem3-science-01-zang-olum"
            ),
            checkLessonId = "elem3-science-01-zang-olum",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 193,
            title = "علوم سوم درس ۲: خوراکی‌ها — از صفر",
            subtitle = "پایه سوم دبستان — علوم تجربی",
            lessonIds = listOf(
                "elem3-science-02-khorakiha"
            ),
            checkLessonId = "elem3-science-02-khorakiha",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 194,
            title = "علوم سوم درس ۳: اندازه‌گیری مواد — از صفر",
            subtitle = "پایه سوم دبستان — علوم تجربی",
            lessonIds = listOf(
                "elem3-science-03-andazegiri-mavad"
            ),
            checkLessonId = "elem3-science-03-andazegiri-mavad",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 195,
            title = "علوم سوم درس ۴: مواد اطراف ما — از صفر",
            subtitle = "پایه سوم دبستان — علوم تجربی",
            lessonIds = listOf(
                "elem3-science-04-mavad-atraf-ma"
            ),
            checkLessonId = "elem3-science-04-mavad-atraf-ma",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 196,
            title = "علوم سوم درس ۵: آب ماده با ارزش — از صفر",
            subtitle = "پایه سوم دبستان — علوم تجربی",
            lessonIds = listOf(
                "elem3-science-05-ab-made-ba-arzesh"
            ),
            checkLessonId = "elem3-science-05-ab-made-ba-arzesh",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 197,
            title = "علوم سوم درس ۶: زندگی ما و آب — از صفر",
            subtitle = "پایه سوم دبستان — علوم تجربی",
            lessonIds = listOf(
                "elem3-science-06-zendegi-ma-va-ab"
            ),
            checkLessonId = "elem3-science-06-zendegi-ma-va-ab",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 198,
            title = "علوم سوم درس ۷: نور و مشاهده اجسام — از صفر",
            subtitle = "پایه سوم دبستان — علوم تجربی",
            lessonIds = listOf(
                "elem3-science-07-nur-va-moshahede"
            ),
            checkLessonId = "elem3-science-07-nur-va-moshahede",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 199,
            title = "علوم سوم درس ۸: جست‌وجو کنیم و بسازیم — از صفر",
            subtitle = "پایه سوم دبستان — علوم تجربی",
            lessonIds = listOf(
                "elem3-science-08-jostoju-besazim"
            ),
            checkLessonId = "elem3-science-08-jostoju-besazim",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 200,
            title = "علوم سوم درس ۹: نیرو همه جا (۱) — از صفر",
            subtitle = "پایه سوم دبستان — علوم تجربی",
            lessonIds = listOf(
                "elem3-science-09-niru-1"
            ),
            checkLessonId = "elem3-science-09-niru-1",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 201,
            title = "علوم سوم درس ۱۰: نیرو همه جا (۲) — از صفر",
            subtitle = "پایه سوم دبستان — علوم تجربی",
            lessonIds = listOf(
                "elem3-science-10-niru-2"
            ),
            checkLessonId = "elem3-science-10-niru-2",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 202,
            title = "علوم سوم درس ۱۱: بکارید و ببینید — از صفر",
            subtitle = "پایه سوم دبستان — علوم تجربی",
            lessonIds = listOf(
                "elem3-science-11-bekarid-bebinid"
            ),
            checkLessonId = "elem3-science-11-bekarid-bebinid",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 203,
            title = "علوم سوم درس ۱۲: هر کدام جای خود (۱) — از صفر",
            subtitle = "پایه سوم دبستان — علوم تجربی",
            lessonIds = listOf(
                "elem3-science-12-har-kodam-jay-khod-1"
            ),
            checkLessonId = "elem3-science-12-har-kodam-jay-khod-1",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 204,
            title = "علوم سوم درس ۱۳: هر کدام جای خود (۲) — از صفر",
            subtitle = "پایه سوم دبستان — علوم تجربی",
            lessonIds = listOf(
                "elem3-science-13-har-kodam-jay-khod-2"
            ),
            checkLessonId = "elem3-science-13-har-kodam-jay-khod-2",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 205,
            title = "علوم سوم درس ۱۴: از گذشته تا آینده — از صفر",
            subtitle = "پایه سوم دبستان — علوم تجربی",
            lessonIds = listOf(
                "elem3-science-14-az-gozashte-ta-ayande"
            ),
            checkLessonId = "elem3-science-14-az-gozashte-ta-ayande",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 206,
            title = "مطالعات سوم درس ۱: من به دنیا آمدم — از صفر",
            subtitle = "پایه سوم دبستان — مطالعات اجتماعی",
            lessonIds = listOf(
                "elem3-social-01-man-be-donya-amadam"
            ),
            checkLessonId = "elem3-social-01-man-be-donya-amadam",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 207,
            title = "مطالعات سوم درس ۲: من بزرگ‌تر شده‌ام — از صفر",
            subtitle = "پایه سوم دبستان — مطالعات اجتماعی",
            lessonIds = listOf(
                "elem3-social-02-man-bozorgtar-shodeam"
            ),
            checkLessonId = "elem3-social-02-man-bozorgtar-shodeam",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 208,
            title = "مطالعات سوم درس ۳: آیا ما مثل هم هستیم؟ — از صفر",
            subtitle = "پایه سوم دبستان — مطالعات اجتماعی",
            lessonIds = listOf(
                "elem3-social-03-aya-ma-mesl-ham-hastim"
            ),
            checkLessonId = "elem3-social-03-aya-ma-mesl-ham-hastim",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 209,
            title = "مطالعات سوم درس ۴: اعضای خانواده — از صفر",
            subtitle = "پایه سوم دبستان — مطالعات اجتماعی",
            lessonIds = listOf(
                "elem3-social-04-aaza-khanevade"
            ),
            checkLessonId = "elem3-social-04-aaza-khanevade",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 210,
            title = "مطالعات اجتماعی سوم درس ۵: خانواده‌ام را دوست دارم — از صفر",
            subtitle = "پایه سوم دبستان — مطالعات اجتماعی",
            lessonIds = listOf(
                "elem3-social-05-khanevade-dust-daram"
            ),
            checkLessonId = "elem3-social-05-khanevade-dust-daram",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 211,
            title = "مطالعات اجتماعی سوم درس ۶: تغییر در خانواده — از صفر",
            subtitle = "پایه سوم دبستان — مطالعات اجتماعی",
            lessonIds = listOf(
                "elem3-social-06-taghyir-dar-khanevade"
            ),
            checkLessonId = "elem3-social-06-taghyir-dar-khanevade",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 212,
            title = "مطالعات اجتماعی سوم درس ۷: از بزرگ‌ترها قدردانی کنیم — از صفر",
            subtitle = "پایه سوم دبستان — مطالعات اجتماعی",
            lessonIds = listOf(
                "elem3-social-07-az-bozorgtarha-ghadrdani"
            ),
            checkLessonId = "elem3-social-07-az-bozorgtarha-ghadrdani",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 213,
            title = "مطالعات اجتماعی سوم درس ۸: چرا با هم همکاری می‌کنیم؟ — از صفر",
            subtitle = "پایه سوم دبستان — مطالعات اجتماعی",
            lessonIds = listOf(
                "elem3-social-08-chera-hamkari"
            ),
            checkLessonId = "elem3-social-08-chera-hamkari",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 214,
            title = "مطالعات اجتماعی سوم درس ۹: مقررات خانه ما — از صفر",
            subtitle = "پایه سوم دبستان — مطالعات اجتماعی",
            lessonIds = listOf(
                "elem3-social-09-moghararat-khane-ma"
            ),
            checkLessonId = "elem3-social-09-moghararat-khane-ma",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 215,
            title = "مطالعات اجتماعی سوم درس ۱۰: نیازهای خانواده چگونه تأمین می‌شود؟ — از صفر",
            subtitle = "پایه سوم دبستان — مطالعات اجتماعی",
            lessonIds = listOf(
                "elem3-social-10-niyazhaye-khanevade"
            ),
            checkLessonId = "elem3-social-10-niyazhaye-khanevade",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 216,
            title = "مطالعات اجتماعی سوم درس ۱۱: منابع — از صفر",
            subtitle = "پایه سوم دبستان — مطالعات اجتماعی",
            lessonIds = listOf(
                "elem3-social-11-manabe"
            ),
            checkLessonId = "elem3-social-11-manabe",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 217,
            title = "مطالعات اجتماعی سوم درس ۱۲: درست مصرف کنیم — از صفر",
            subtitle = "پایه سوم دبستان — مطالعات اجتماعی",
            lessonIds = listOf(
                "elem3-social-12-dorost-masraf-konim"
            ),
            checkLessonId = "elem3-social-12-dorost-masraf-konim",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 218,
            title = "مطالعات اجتماعی سوم درس ۱۳: بازیافت — از صفر",
            subtitle = "پایه سوم دبستان — مطالعات اجتماعی",
            lessonIds = listOf(
                "elem3-social-13-bazyaft"
            ),
            checkLessonId = "elem3-social-13-bazyaft",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 219,
            title = "مطالعات اجتماعی سوم درس ۱۴: خانه‌ام را دوست دارم — از صفر",
            subtitle = "پایه سوم دبستان — مطالعات اجتماعی",
            lessonIds = listOf(
                "elem3-social-14-khaneam-dust-daram"
            ),
            checkLessonId = "elem3-social-14-khaneam-dust-daram",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 220,
            title = "مطالعات سوم ۱۵: خانه‌ها با هم تفاوت دارند",
            subtitle = "پایه سوم دبستان — مطالعات اجتماعی",
            lessonIds = listOf(
                "elem3-social-15-khaneha-tafavot"
            ),
            checkLessonId = "elem3-social-15-khaneha-tafavot",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 221,
            title = "مطالعات سوم ۱۶: خانه شما چه شکلی است؟",
            subtitle = "پایه سوم دبستان — مطالعات اجتماعی",
            lessonIds = listOf(
                "elem3-social-16-khane-shoma-che-shekli"
            ),
            checkLessonId = "elem3-social-16-khane-shoma-che-shekli",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 222,
            title = "مطالعات سوم ۱۷: از خانه محافظت کنیم",
            subtitle = "پایه سوم دبستان — مطالعات اجتماعی",
            lessonIds = listOf(
                "elem3-social-17-az-khane-mohafezat"
            ),
            checkLessonId = "elem3-social-17-az-khane-mohafezat",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 223,
            title = "مطالعات سوم ۱۸: مدرسه دوست‌داشتنی ما",
            subtitle = "پایه سوم دبستان — مطالعات اجتماعی",
            lessonIds = listOf(
                "elem3-social-18-madrese-dustdashtani"
            ),
            checkLessonId = "elem3-social-18-madrese-dustdashtani",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 224,
            title = "مطالعات سوم ۱۹: مکان‌های مدرسه را بشناسیم",
            subtitle = "پایه سوم دبستان — مطالعات اجتماعی",
            lessonIds = listOf(
                "elem3-social-19-makanhaye-madrese"
            ),
            checkLessonId = "elem3-social-19-makanhaye-madrese",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 225,
            title = "مطالعات سوم ۲۰: خانه شما کجاست؟",
            subtitle = "پایه سوم دبستان — مطالعات اجتماعی",
            lessonIds = listOf(
                "elem3-social-20-khane-shoma-kojast"
            ),
            checkLessonId = "elem3-social-20-khane-shoma-kojast",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 226,
            title = "مطالعات سوم ۲۱: جهت‌های اصلی",
            subtitle = "پایه سوم دبستان — مطالعات اجتماعی",
            lessonIds = listOf(
                "elem3-social-21-jahathaye-asli"
            ),
            checkLessonId = "elem3-social-21-jahathaye-asli",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 227,
            title = "مطالعات سوم ۲۲: پست",
            subtitle = "پایه سوم دبستان — مطالعات اجتماعی",
            lessonIds = listOf(
                "elem3-social-22-post"
            ),
            checkLessonId = "elem3-social-22-post",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 228,
            title = "مطالعات سوم ۲۳: ایمنی در کوچه و خیابان",
            subtitle = "پایه سوم دبستان — مطالعات اجتماعی",
            lessonIds = listOf(
                "elem3-social-23-eimeni-kuche-khiaban"
            ),
            checkLessonId = "elem3-social-23-eimeni-kuche-khiaban",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 229,
            title = "هدیه سوم ۱: آستین‌های خالی",
            subtitle = "پایه سوم دبستان — هدیه‌های آسمانی",
            lessonIds = listOf(
                "elem3-hedyeh-01-astinhaye-khali"
            ),
            checkLessonId = "elem3-hedyeh-01-astinhaye-khali",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 230,
            title = "هدیه سوم ۲: غروب یک روز بهاری — دعا و شکرگزاری",
            subtitle = "پایه سوم دبستان — هدیه‌های آسمانی",
            lessonIds = listOf(
                "elem3-hedyeh-02-ghorub-yek-ruz-bahari"
            ),
            checkLessonId = "elem3-hedyeh-02-ghorub-yek-ruz-bahari",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 231,
            title = "هدیه سوم ۳: همیشه با من — پیامبر امین",
            subtitle = "پایه سوم دبستان — هدیه‌های آسمانی",
            lessonIds = listOf(
                "elem3-hedyeh-03-hamishe-ba-man"
            ),
            checkLessonId = "elem3-hedyeh-03-hamishe-ba-man",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 232,
            title = "هدیه سوم ۴: در کاخ نمرود — حضرت ابراهیم",
            subtitle = "پایه سوم دبستان — هدیه‌های آسمانی",
            lessonIds = listOf(
                "elem3-hedyeh-04-dar-kakh-namrud"
            ),
            checkLessonId = "elem3-hedyeh-04-dar-kakh-namrud",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 233,
            title = "هدیه سوم ۵: روز دهم — عاشورا",
            subtitle = "پایه سوم دبستان — هدیه‌های آسمانی",
            lessonIds = listOf(
                "elem3-hedyeh-05-ruz-dahom"
            ),
            checkLessonId = "elem3-hedyeh-05-ruz-dahom",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 234,
            title = "هدیه سوم ۶: بانوی قهرمان — حضرت زینب",
            subtitle = "پایه سوم دبستان — هدیه‌های آسمانی",
            lessonIds = listOf(
                "elem3-hedyeh-06-banuy-qahreman"
            ),
            checkLessonId = "elem3-hedyeh-06-banuy-qahreman",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 235,
            title = "هدیه سوم ۷: بوی بهشت — اویس قرنی",
            subtitle = "پایه سوم دبستان — هدیه‌های آسمانی",
            lessonIds = listOf(
                "elem3-hedyeh-07-bu-behesht"
            ),
            checkLessonId = "elem3-hedyeh-07-bu-behesht",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 236,
            title = "هدیه سوم ۸: جشن تکلیف — بزرگ شدن",
            subtitle = "پایه سوم دبستان — هدیه‌های آسمانی",
            lessonIds = listOf(
                "elem3-hedyeh-08-jashn-taklif"
            ),
            checkLessonId = "elem3-hedyeh-08-jashn-taklif",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 237,
            title = "هدیه سوم ۹: گفت‌وگو با خدا — دعا و نماز",
            subtitle = "پایه سوم دبستان — هدیه‌های آسمانی",
            lessonIds = listOf(
                "elem3-hedyeh-09-goftogu-ba-khoda"
            ),
            checkLessonId = "elem3-hedyeh-09-goftogu-ba-khoda",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 238,
            title = "هدیه سوم ۱۰: ماه میهمانی خدا — ماه رمضان",
            subtitle = "پایه سوم دبستان — هدیه‌های آسمانی",
            lessonIds = listOf(
                "elem3-hedyeh-10-mah-mihmani-khoda"
            ),
            checkLessonId = "elem3-hedyeh-10-mah-mihmani-khoda",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 239,
            title = "هدیه سوم ۱۱: عید مسلمانان — عید فطر",
            subtitle = "پایه سوم دبستان — هدیه‌های آسمانی",
            lessonIds = listOf(
                "elem3-hedyeh-11-eid-mosalamanan"
            ),
            checkLessonId = "elem3-hedyeh-11-eid-mosalamanan",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 240,
            title = "هدیه سوم ۱۲: سخن آسمانی — قرآن سخن خداست",
            subtitle = "پایه سوم دبستان — هدیه‌های آسمانی",
            lessonIds = listOf(
                "elem3-hedyeh-12-sokhan-asemani"
            ),
            checkLessonId = "elem3-hedyeh-12-sokhan-asemani",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 241,
            title = "هدیه سوم ۱۳: انتخاب پروانه — دوست خوب را انتخاب کن",
            subtitle = "پایه سوم دبستان — هدیه‌های آسمانی",
            lessonIds = listOf(
                "elem3-hedyeh-13-entekhab-parvane"
            ),
            checkLessonId = "elem3-hedyeh-13-entekhab-parvane",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 242,
            title = "هدیه سوم ۱۴: ام ابیها — حضرت فاطمه، مادر پدرش",
            subtitle = "پایه سوم دبستان — هدیه‌های آسمانی",
            lessonIds = listOf(
                "elem3-hedyeh-14-om-abiha"
            ),
            checkLessonId = "elem3-hedyeh-14-om-abiha",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 243,
            title = "هدیه سوم ۱۵: همسفر ناشناس — به همسفر کمک کن",
            subtitle = "پایه سوم دبستان — هدیه‌های آسمانی",
            lessonIds = listOf(
                "elem3-hedyeh-15-hamsafar-nashenas"
            ),
            checkLessonId = "elem3-hedyeh-15-hamsafar-nashenas",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 244,
            title = "هدیه سوم ۱۶: داناترین مردم — امام باقر، دانای دین",
            subtitle = "پایه سوم دبستان — هدیه‌های آسمانی",
            lessonIds = listOf(
                "elem3-hedyeh-16-danatarin-mardom"
            ),
            checkLessonId = "elem3-hedyeh-16-danatarin-mardom",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 245,
            title = "هدیه سوم ۱۷: خواب شیرین — کمک امام صادق به نیازمند",
            subtitle = "پایه سوم دبستان — هدیه‌های آسمانی",
            lessonIds = listOf(
                "elem3-hedyeh-17-khab-shirin"
            ),
            checkLessonId = "elem3-hedyeh-17-khab-shirin",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 246,
            title = "هدیه سوم ۱۸: آینه سخنگو — آینه راست می‌گوید",
            subtitle = "پایه سوم دبستان — هدیه‌های آسمانی",
            lessonIds = listOf(
                "elem3-hedyeh-18-ayene-sokhangu"
            ),
            checkLessonId = "elem3-hedyeh-18-ayene-sokhangu",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 247,
            title = "هدیه سوم ۱۹: گندم از گندم بروید — هرچه بکاری، همان را درو می‌کنی",
            subtitle = "پایه سوم دبستان — هدیه‌های آسمانی",
            lessonIds = listOf(
                "elem3-hedyeh-19-gandom-az-gandom"
            ),
            checkLessonId = "elem3-hedyeh-19-gandom-az-gandom",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 248,
            title = "هدیه سوم ۲۰: باغ همیشه بهار — بهشت پاداش کار خوب است",
            subtitle = "پایه سوم دبستان — هدیه‌های آسمانی",
            lessonIds = listOf(
                "elem3-hedyeh-20-bagh-hamishe-bahar"
            ),
            checkLessonId = "elem3-hedyeh-20-bagh-hamishe-bahar",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 249,
            title = "قرآن سوم ۱: ترکیب حروف و حرکات — از صفر روخوانی",
            subtitle = "پایه سوم دبستان — قرآن",
            lessonIds = listOf(
                "elem3-quran-01-tarkib-horuf-harakat"
            ),
            checkLessonId = "elem3-quran-01-tarkib-horuf-harakat",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 250,
            title = "قرآن سوم ۲: حرف ساکن — حرفی که حرکت ندارد",
            subtitle = "پایه سوم دبستان — قرآن",
            lessonIds = listOf(
                "elem3-quran-02-harf-saken"
            ),
            checkLessonId = "elem3-quran-02-harf-saken",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 251,
            title = "قرآن سوم ۳: تنوین — دو حرکت روی هم",
            subtitle = "پایه سوم دبستان — قرآن",
            lessonIds = listOf(
                "elem3-quran-03-tanvin"
            ),
            checkLessonId = "elem3-quran-03-tanvin",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 252,
            title = "قرآن سوم ۴: تشدید — حرف را دو بار بخوان",
            subtitle = "پایه سوم دبستان — قرآن",
            lessonIds = listOf(
                "elem3-quran-04-tashdid"
            ),
            checkLessonId = "elem3-quran-04-tashdid",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 253,
            title = "قرآن سوم ۵: خواندن «هو» و «هی» — ه آخر کلمه",
            subtitle = "پایه سوم دبستان — قرآن",
            lessonIds = listOf(
                "elem3-quran-05-khandan-hu-hi"
            ),
            checkLessonId = "elem3-quran-05-khandan-hu-hi",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 254,
            title = "قرآن سوم ۶: پایه‌های همزه — همزه روی چه می‌نشیند؟",
            subtitle = "پایه سوم دبستان — قرآن",
            lessonIds = listOf(
                "elem3-quran-06-paye-hamze"
            ),
            checkLessonId = "elem3-quran-06-paye-hamze",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 255,
            title = "قرآن سوم ۷: حروف ناخوانا ۱ — الف و واو ناخوانا",
            subtitle = "پایه سوم دبستان — قرآن",
            lessonIds = listOf(
                "elem3-quran-07-horuf-nakhana-1"
            ),
            checkLessonId = "elem3-quran-07-horuf-nakhana-1",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 256,
            title = "قرآن سوم ۸: حروف ناخوانا ۲ — الف لام و یا ناخوانا",
            subtitle = "پایه سوم دبستان — قرآن",
            lessonIds = listOf(
                "elem3-quran-08-horuf-nakhana-2"
            ),
            checkLessonId = "elem3-quran-08-horuf-nakhana-2",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 257,
            title = "قرآن سوم ۹: حروف ناخوانا ۳ — الف کوچک و یا ناخوانا",
            subtitle = "پایه سوم دبستان — قرآن",
            lessonIds = listOf(
                "elem3-quran-09-horuf-nakhana-3"
            ),
            checkLessonId = "elem3-quran-09-horuf-nakhana-3",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 258,
            title = "قرآن سوم ۱۰: سوره فیل — روخوانی قرآن با قواعد",
            subtitle = "پایه سوم دبستان — قرآن",
            lessonIds = listOf(
                "elem3-quran-10-sure-fil"
            ),
            checkLessonId = "elem3-quran-10-sure-fil",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 259,
            title = "قرآن سوم ۱۱: اذان و اقامه — صدای دعوت به نماز",
            subtitle = "پایه سوم دبستان — قرآن",
            lessonIds = listOf(
                "elem3-quran-11-azan-eghame"
            ),
            checkLessonId = "elem3-quran-11-azan-eghame",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 260,
            title = "قرآن سوم ۱۲: سوره حمد — مادر قرآن",
            subtitle = "پایه سوم دبستان — قرآن",
            lessonIds = listOf(
                "elem3-quran-12-sure-hamd"
            ),
            checkLessonId = "elem3-quran-12-sure-hamd",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 261,
            title = "قرآن سوم ۱۳: سوره توحید — خدا یکی است",
            subtitle = "پایه سوم دبستان — قرآن",
            lessonIds = listOf(
                "elem3-quran-13-sure-tohid"
            ),
            checkLessonId = "elem3-quran-13-sure-tohid",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 262,
            title = "قرآن سوم ۱۴: اذکار نماز — ذکرهای رکوع و سجده",
            subtitle = "پایه سوم دبستان — قرآن",
            lessonIds = listOf(
                "elem3-quran-14-azkar-namaz"
            ),
            checkLessonId = "elem3-quran-14-azkar-namaz",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 263,
            title = "قرآن سوم ۱۵: آیه صلوات — درود بر پیامبر",
            subtitle = "پایه سوم دبستان — قرآن",
            lessonIds = listOf(
                "elem3-quran-15-aye-salavat"
            ),
            checkLessonId = "elem3-quran-15-aye-salavat",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 264,
            title = "قرآن سوم ۱۶: انس ۱ — سوره حمد و بقره آیه ۱ تا ۵",
            subtitle = "پایه سوم دبستان — قرآن",
            lessonIds = listOf(
                "elem3-quran-16-ons-01-hamd-baghare"
            ),
            checkLessonId = "elem3-quran-16-ons-01-hamd-baghare",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 265,
            title = "قرآن سوم ۱۷: انس ۲ — آیةالکرسی",
            subtitle = "پایه سوم دبستان — قرآن",
            lessonIds = listOf(
                "elem3-quran-17-ons-02-ayatalkorsi"
            ),
            checkLessonId = "elem3-quran-17-ons-02-ayatalkorsi",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 266,
            title = "قرآن سوم ۱۸: انس ۳ — ابراهیم آیه ۳۷ و ۳۸ + چشمه زمزم",
            subtitle = "پایه سوم دبستان — قرآن",
            lessonIds = listOf(
                "elem3-quran-18-ons-03-ebrahim-zamzam"
            ),
            checkLessonId = "elem3-quran-18-ons-03-ebrahim-zamzam",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 267,
            title = "قرآن سوم ۱۹: انس ۴ — اسراء آیه ۲۳ و ۲۴",
            subtitle = "پایه سوم دبستان — قرآن",
            lessonIds = listOf(
                "elem3-quran-19-ons-04-esra"
            ),
            checkLessonId = "elem3-quran-19-ons-04-esra",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 268,
            title = "قرآن سوم ۲۰: انس ۵ — ابراهیم آیه ۳۹ تا ۴۱",
            subtitle = "پایه سوم دبستان — قرآن",
            lessonIds = listOf(
                "elem3-quran-20-ons-05-ebrahim"
            ),
            checkLessonId = "elem3-quran-20-ons-05-ebrahim",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 269,
            title = "قرآن سوم ۲۱: انس ۶ — سوره بلد",
            subtitle = "پایه سوم دبستان — قرآن",
            lessonIds = listOf(
                "elem3-quran-21-ons-06-balad"
            ),
            checkLessonId = "elem3-quran-21-ons-06-balad",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 270,
            title = "قرآن سوم ۲۲: انس ۷ — طه آیه ۱۷ تا ۲۴ + پیامبری موسی",
            subtitle = "پایه سوم دبستان — قرآن",
            lessonIds = listOf(
                "elem3-quran-22-ons-07-taha"
            ),
            checkLessonId = "elem3-quran-22-ons-07-taha",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 271,
            title = "قرآن سوم ۲۳: انس ۸ — طه آیه ۴۳ تا ۴۷ + به سوی مبارزه بزرگ",
            subtitle = "پایه سوم دبستان — قرآن",
            lessonIds = listOf(
                "elem3-quran-23-ons-08-taha"
            ),
            checkLessonId = "elem3-quran-23-ons-08-taha",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 272,
            title = "قرآن سوم ۲۴: انس ۹ — سوره‌های قدر و زلزال",
            subtitle = "پایه سوم دبستان — قرآن",
            lessonIds = listOf(
                "elem3-quran-24-ons-09-ghadr-zelzal"
            ),
            checkLessonId = "elem3-quran-24-ons-09-ghadr-zelzal",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 273,
            title = "قرآن سوم ۲۵: انس ۱۰ — شعراء آیه ۴۱ تا ۴۸",
            subtitle = "پایه سوم دبستان — قرآن",
            lessonIds = listOf(
                "elem3-quran-25-ons-10-shoara"
            ),
            checkLessonId = "elem3-quran-25-ons-10-shoara",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 274,
            title = "قرآن سوم ۲۶: انس ۱۱ — سوره قارعه",
            subtitle = "پایه سوم دبستان — قرآن",
            lessonIds = listOf(
                "elem3-quran-26-ons-11-gharee"
            ),
            checkLessonId = "elem3-quran-26-ons-11-gharee",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 275,
            title = "قرآن سوم ۲۷: انس ۱۲ — سوره فجر",
            subtitle = "پایه سوم دبستان — قرآن",
            lessonIds = listOf(
                "elem3-quran-27-ons-12-fajr"
            ),
            checkLessonId = "elem3-quran-27-ons-12-fajr",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 276,
            title = "قرآن سوم ۲۸: انس ۱۳ — سوره عادیات + داستان صبح پیروزی",
            subtitle = "پایه سوم دبستان — قرآن",
            lessonIds = listOf(
                "elem3-quran-28-ons-13-adiyat"
            ),
            checkLessonId = "elem3-quran-28-ons-13-adiyat",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 277,
            title = "قرآن سوم ۲۹: انس ۱۴ — سوره علق",
            subtitle = "پایه سوم دبستان — قرآن",
            lessonIds = listOf(
                "elem3-quran-29-ons-14-alagh"
            ),
            checkLessonId = "elem3-quran-29-ons-14-alagh",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 278,
            title = "فارسی چهارم ۱: آفریدگار زیبایی — چهار فصل نقاش",
            subtitle = "پایه چهارم دبستان — فارسی",
            lessonIds = listOf(
                "elem4-farsi-01-afaridegar-zibayi"
            ),
            checkLessonId = "elem4-farsi-01-afaridegar-zibayi",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 279,
            title = "فارسی چهارم ۲: کوچ پرستوها — سفر دور و دراز",
            subtitle = "پایه چهارم دبستان — فارسی",
            lessonIds = listOf(
                "elem4-farsi-02-kouch-parastouha"
            ),
            checkLessonId = "elem4-farsi-02-kouch-parastouha",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 280,
            title = "فارسی چهارم ۳: راز نشانه‌ها — پسری که خوب نگاه کرد",
            subtitle = "پایه چهارم دبستان — فارسی",
            lessonIds = listOf(
                "elem4-farsi-03-raz-neshaneha"
            ),
            checkLessonId = "elem4-farsi-03-raz-neshaneha",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 281,
            title = "فارسی چهارم ۴: ارزش علم — علم بهتر است یا مال؟",
            subtitle = "پایه چهارم دبستان — فارسی",
            lessonIds = listOf(
                "elem4-farsi-04-arzesh-elm"
            ),
            checkLessonId = "elem4-farsi-04-arzesh-elm",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 282,
            title = "فارسی چهارم ۵: رهایی از قفس — طوطی و بازرگان",
            subtitle = "پایه چهارم دبستان — فارسی",
            lessonIds = listOf(
                "elem4-farsi-05-rahaee-az-ghafas"
            ),
            checkLessonId = "elem4-farsi-05-rahaee-az-ghafas",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 283,
            title = "فارسی چهارم ۶: آرش کمان‌گیر — تیری برای مرز ایران",
            subtitle = "پایه چهارم دبستان — فارسی",
            lessonIds = listOf(
                "elem4-farsi-06-arash-kamangir"
            ),
            checkLessonId = "elem4-farsi-06-arash-kamangir",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 284,
            title = "فارسی چهارم ۷: مهمان شهر ما — مهمان‌نوازی ایرانی",
            subtitle = "پایه چهارم دبستان — فارسی",
            lessonIds = listOf(
                "elem4-farsi-07-mehman-shahr-ma"
            ),
            checkLessonId = "elem4-farsi-07-mehman-shahr-ma",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 285,
            title = "فارسی چهارم ۸: درس آزاد ۱ — چطور یک متن را انتخاب کنیم و بخوانیم؟",
            subtitle = "پایه چهارم دبستان — فارسی",
            lessonIds = listOf(
                "elem4-farsi-08-dars-azad-1"
            ),
            checkLessonId = "elem4-farsi-08-dars-azad-1",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 286,
            title = "فارسی چهارم ۹: درس آزاد ۲ — فهم عمیق متن و گفت‌وگو درباره آن",
            subtitle = "پایه چهارم دبستان — فارسی",
            lessonIds = listOf(
                "elem4-farsi-09-dars-azad-2"
            ),
            checkLessonId = "elem4-farsi-09-dars-azad-2",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 287,
            title = "فارسی چهارم ۱۰: باغچه اطفال — جبار باغچه‌بان",
            subtitle = "پایه چهارم دبستان — فارسی",
            lessonIds = listOf(
                "elem4-farsi-10-baghche-atfal"
            ),
            checkLessonId = "elem4-farsi-10-baghche-atfal",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 288,
            title = "فارسی چهارم درس ۱۱: فرمانده دل‌ها — از صفر",
            subtitle = "پایه چهارم دبستان — فارسی",
            lessonIds = listOf(
                "elem4-farsi-11-farmande-delha"
            ),
            checkLessonId = "elem4-farsi-11-farmande-delha",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 289,
            title = "فارسی چهارم درس ۱۲: اتفاق ساده — از صفر",
            subtitle = "پایه چهارم دبستان — فارسی",
            lessonIds = listOf(
                "elem4-farsi-12-ettefagh-sadeh"
            ),
            checkLessonId = "elem4-farsi-12-ettefagh-sadeh",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 290,
            title = "فارسی چهارم درس ۱۳: لطف حق — از صفر",
            subtitle = "پایه چهارم دبستان — فارسی",
            lessonIds = listOf(
                "elem4-farsi-13-lotf-hagh"
            ),
            checkLessonId = "elem4-farsi-13-lotf-hagh",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 291,
            title = "فارسی چهارم درس ۱۴: ادب از که آموختی؟ — از صفر",
            subtitle = "پایه چهارم دبستان — فارسی",
            lessonIds = listOf(
                "elem4-farsi-14-adab-az-ke-amoukhti"
            ),
            checkLessonId = "elem4-farsi-14-adab-az-ke-amoukhti",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 292,
            title = "فارسی چهارم درس ۱۵: شیر و موش — از صفر",
            subtitle = "پایه چهارم دبستان — فارسی",
            lessonIds = listOf(
                "elem4-farsi-15-shir-va-moush"
            ),
            checkLessonId = "elem4-farsi-15-shir-va-moush",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 293,
            title = "فارسی چهارم درس ۱۶: پرسشگری — از صفر",
            subtitle = "پایه چهارم دبستان — فارسی",
            lessonIds = listOf(
                "elem4-farsi-16-porseshgari"
            ),
            checkLessonId = "elem4-farsi-16-porseshgari",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 294,
            title = "فارسی چهارم درس ۱۷: مدرسه هوشمند — از صفر",
            subtitle = "پایه چهارم دبستان — فارسی",
            lessonIds = listOf(
                "elem4-farsi-17-madrese-houshmand"
            ),
            checkLessonId = "elem4-farsi-17-madrese-houshmand",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 295,
            title = "نگارش چهارم درس ۱: آفریدگار زیبایی — از صفر",
            subtitle = "پایه چهارم دبستان — نگارش",
            lessonIds = listOf(
                "elem4-negaresh-01-afaridegar-zibayi"
            ),
            checkLessonId = "elem4-negaresh-01-afaridegar-zibayi",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 296,
            title = "نگارش چهارم درس ۲: کوچ پرستوها — از صفر",
            subtitle = "پایه چهارم دبستان — نگارش",
            lessonIds = listOf(
                "elem4-negaresh-02-kouch-parastouha"
            ),
            checkLessonId = "elem4-negaresh-02-kouch-parastouha",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 297,
            title = "نگارش چهارم درس ۳: راز نشانه‌ها — از صفر",
            subtitle = "پایه چهارم دبستان — نگارش",
            lessonIds = listOf(
                "elem4-negaresh-03-raz-neshaneha"
            ),
            checkLessonId = "elem4-negaresh-03-raz-neshaneha",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 298,
            title = "نگارش چهارم ۴: ارزش علم — چرا علم ارزش دارد؟",
            subtitle = "پایه چهارم دبستان — نگارش",
            lessonIds = listOf(
                "elem4-negaresh-04-arzesh-elm"
            ),
            checkLessonId = "elem4-negaresh-04-arzesh-elm",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 299,
            title = "نگارش چهارم ۵: رهایی از قفس — از زبان پرنده بنویس",
            subtitle = "پایه چهارم دبستان — نگارش",
            lessonIds = listOf(
                "elem4-negaresh-05-rahaee-az-ghafas"
            ),
            checkLessonId = "elem4-negaresh-05-rahaee-az-ghafas",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 300,
            title = "نگارش چهارم ۶: آرش کمان‌گیر — قصه را به ترتیب بگو",
            subtitle = "پایه چهارم دبستان — نگارش",
            lessonIds = listOf(
                "elem4-negaresh-06-arash-kamangir"
            ),
            checkLessonId = "elem4-negaresh-06-arash-kamangir",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 301,
            title = "نگارش چهارم ۷: مهمان شهر ما — دعوت‌نامه و توصیف شهر",
            subtitle = "پایه چهارم دبستان — نگارش",
            lessonIds = listOf(
                "elem4-negaresh-07-mehman-shahr-ma"
            ),
            checkLessonId = "elem4-negaresh-07-mehman-shahr-ma",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 302,
            title = "نگارش چهارم ۸: درس آزاد ۱ — موضوع خودت را انتخاب کن",
            subtitle = "پایه چهارم دبستان — نگارش",
            lessonIds = listOf(
                "elem4-negaresh-08-dars-azad-1"
            ),
            checkLessonId = "elem4-negaresh-08-dars-azad-1",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 303,
            title = "نگارش چهارم ۹: درس آزاد ۲ — از فرهنگ خودت بنویس",
            subtitle = "پایه چهارم دبستان — نگارش",
            lessonIds = listOf(
                "elem4-negaresh-09-dars-azad-2"
            ),
            checkLessonId = "elem4-negaresh-09-dars-azad-2",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 304,
            title = "نگارش چهارم ۱۰: باغچه اطفال — با آهنگ بنویس",
            subtitle = "پایه چهارم دبستان — نگارش",
            lessonIds = listOf(
                "elem4-negaresh-10-baghche-atfal"
            ),
            checkLessonId = "elem4-negaresh-10-baghche-atfal",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 305,
            title = "نگارش چهارم ۱۱: فرمانده دل‌ها — شرح حال یک الگو",
            subtitle = "پایه چهارم دبستان — نگارش",
            lessonIds = listOf(
                "elem4-negaresh-11-farmande-delha"
            ),
            checkLessonId = "elem4-negaresh-11-farmande-delha",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 306,
            title = "نگارش چهارم ۱۲: اتفاق ساده — خاطره یک روز معمولی",
            subtitle = "پایه چهارم دبستان — نگارش",
            lessonIds = listOf(
                "elem4-negaresh-12-ettefagh-sadeh"
            ),
            checkLessonId = "elem4-negaresh-12-ettefagh-sadeh",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 307,
            title = "نگارش چهارم ۱۳: لطف حق — شکرگزاری با جمله‌های زیبا",
            subtitle = "پایه چهارم دبستان — نگارش",
            lessonIds = listOf(
                "elem4-negaresh-13-lotf-hagh"
            ),
            checkLessonId = "elem4-negaresh-13-lotf-hagh",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 308,
            title = "نگارش چهارم ۱۴: ادب از که آموختی؟ — حکایت‌نویسی با پیام",
            subtitle = "پایه چهارم دبستان — نگارش",
            lessonIds = listOf(
                "elem4-negaresh-14-adab-az-ke-amoukhti"
            ),
            checkLessonId = "elem4-negaresh-14-adab-az-ke-amoukhti",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 309,
            title = "نگارش چهارم ۱۵: شیر و موش — بازنویسی شعر به نثر",
            subtitle = "پایه چهارم دبستان — نگارش",
            lessonIds = listOf(
                "elem4-negaresh-15-shir-va-moush"
            ),
            checkLessonId = "elem4-negaresh-15-shir-va-moush",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 310,
            title = "نگارش چهارم ۱۶: پرسشگری — سؤال خوب بپرسیم و مصاحبه کنیم",
            subtitle = "پایه چهارم دبستان — نگارش",
            lessonIds = listOf(
                "elem4-negaresh-16-porseshgari"
            ),
            checkLessonId = "elem4-negaresh-16-porseshgari",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 311,
            title = "نگارش چهارم ۱۷: مدرسه هوشمند — توصیف‌نویسی یک جا",
            subtitle = "پایه چهارم دبستان — نگارش",
            lessonIds = listOf(
                "elem4-negaresh-17-madrese-houshmand"
            ),
            checkLessonId = "elem4-negaresh-17-madrese-houshmand",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 312,
            title = "ریاضی چهارم، فصل ۱: اعداد و الگوها — عددنویسی تا میلیون",
            subtitle = "پایه چهارم دبستان — ریاضی",
            lessonIds = listOf(
                "elem4-math-01-adad-olguha"
            ),
            checkLessonId = "elem4-math-01-adad-olguha",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 313,
            title = "ریاضی چهارم، فصل ۲: کسر — شناخت کسر و جمع و تفریق آن",
            subtitle = "پایه چهارم دبستان — ریاضی",
            lessonIds = listOf(
                "elem4-math-02-kasr"
            ),
            checkLessonId = "elem4-math-02-kasr",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 314,
            title = "ریاضی چهارم، فصل ۳: ضرب و تقسیم — ضرب دو رقمی و تقسیم چند رقمی",
            subtitle = "پایه چهارم دبستان — ریاضی",
            lessonIds = listOf(
                "elem4-math-03-zarb-taghsim"
            ),
            checkLessonId = "elem4-math-03-zarb-taghsim",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 315,
            title = "ریاضی چهارم، فصل ۴: اندازه‌گیری — زاویه، زمان و طول",
            subtitle = "پایه چهارم دبستان — ریاضی",
            lessonIds = listOf(
                "elem4-math-04-andazegiri"
            ),
            checkLessonId = "elem4-math-04-andazegiri",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 316,
            title = "ریاضی چهارم، فصل ۵: عدد مخلوط و عدد اعشاری — از کسر تا اعشار",
            subtitle = "پایه چهارم دبستان — ریاضی",
            lessonIds = listOf(
                "elem4-math-05-adad-makhlout-ashari"
            ),
            checkLessonId = "elem4-math-05-adad-makhlout-ashari",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 317,
            title = "ریاضی چهارم، فصل ۶: شکل‌های هندسی — عمود و موازی، چهارضلعی و مساحت",
            subtitle = "پایه چهارم دبستان — ریاضی",
            lessonIds = listOf(
                "elem4-math-06-shekl-hendesi"
            ),
            checkLessonId = "elem4-math-06-shekl-hendesi",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 318,
            title = "ریاضی چهارم ۷: آمار و احتمال — نمودار و شانس",
            subtitle = "پایه چهارم دبستان — ریاضی",
            lessonIds = listOf(
                "elem4-math-07-amar-ehtemal"
            ),
            checkLessonId = "elem4-math-07-amar-ehtemal",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 319,
            title = "علوم چهارم ۱: زنگ علوم — مثل یک دانشمند نگاه کن",
            subtitle = "پایه چهارم دبستان — علوم تجربی",
            lessonIds = listOf(
                "elem4-science-01-zang-oloum"
            ),
            checkLessonId = "elem4-science-01-zang-oloum",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 320,
            title = "علوم چهارم ۲: مخلوط‌ها در زندگی — با هم ولی جدا",
            subtitle = "پایه چهارم دبستان — علوم تجربی",
            lessonIds = listOf(
                "elem4-science-02-makhloutha"
            ),
            checkLessonId = "elem4-science-02-makhloutha",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 321,
            title = "علوم چهارم ۳: انرژی، نیاز هر روز ما — نیروی زندگی",
            subtitle = "پایه چهارم دبستان — علوم تجربی",
            lessonIds = listOf(
                "elem4-science-03-energy-niaz"
            ),
            checkLessonId = "elem4-science-03-energy-niaz",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 322,
            title = "علوم چهارم ۴: انرژی الکتریکی — مدار بسته، لامپ روشن",
            subtitle = "پایه چهارم دبستان — علوم تجربی",
            lessonIds = listOf(
                "elem4-science-04-energy-elektriki"
            ),
            checkLessonId = "elem4-science-04-energy-elektriki",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 323,
            title = "علوم چهارم ۵: گرما و ماده — از گرم به سرد",
            subtitle = "پایه چهارم دبستان — علوم تجربی",
            lessonIds = listOf(
                "elem4-science-05-garma-madde"
            ),
            checkLessonId = "elem4-science-05-garma-madde",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 324,
            title = "علوم چهارم ۶: سنگ‌ها — قصه میلیون‌ها سال زمین",
            subtitle = "پایه چهارم دبستان — علوم تجربی",
            lessonIds = listOf(
                "elem4-science-06-sangha"
            ),
            checkLessonId = "elem4-science-06-sangha",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 325,
            title = "علوم چهارم ۷: آهن‌ربا در زندگی — جذب و دفع",
            subtitle = "پایه چهارم دبستان — علوم تجربی",
            lessonIds = listOf(
                "elem4-science-07-ahanroba"
            ),
            checkLessonId = "elem4-science-07-ahanroba",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 326,
            title = "علوم چهارم ۸: آسمان در شب — ماه و ستاره‌ها",
            subtitle = "پایه چهارم دبستان — علوم تجربی",
            lessonIds = listOf(
                "elem4-science-08-aseman-shab"
            ),
            checkLessonId = "elem4-science-08-aseman-shab",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 327,
            title = "علوم چهارم ۹: بدن ما، بخش یکم — استخوان‌ها و ماهیچه‌ها",
            subtitle = "پایه چهارم دبستان — علوم تجربی",
            lessonIds = listOf(
                "elem4-science-09-badan-1"
            ),
            checkLessonId = "elem4-science-09-badan-1",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 328,
            title = "علوم چهارم ۱۰: بدن ما ۲ — غذا چطور در بدن ما سفر می‌کند؟",
            subtitle = "پایه چهارم دبستان — علوم تجربی",
            lessonIds = listOf(
                "elem4-science-10-badan-2"
            ),
            checkLessonId = "elem4-science-10-badan-2",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 329,
            title = "علوم چهارم ۱۱: بی‌مهره‌ها — جانورانی بدون ستون مهره‌ها",
            subtitle = "پایه چهارم دبستان — علوم تجربی",
            lessonIds = listOf(
                "elem4-science-11-bimohreha"
            ),
            checkLessonId = "elem4-science-11-bimohreha",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 330,
            title = "علوم چهارم ۱۲: گوناگونی گیاهان — گیاهان چقدر با هم فرق می‌کنند؟",
            subtitle = "پایه چهارم دبستان — علوم تجربی",
            lessonIds = listOf(
                "elem4-science-12-gounagouni-giyahan"
            ),
            checkLessonId = "elem4-science-12-gounagouni-giyahan",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 331,
            title = "علوم چهارم ۱۳: زیستگاه — خانه جانداران کجاست؟",
            subtitle = "پایه چهارم دبستان — علوم تجربی",
            lessonIds = listOf(
                "elem4-science-13-zistgah"
            ),
            checkLessonId = "elem4-science-13-zistgah",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 332,
            title = "مطالعات چهارم ۱: همسایه ما — همسایه یعنی کی؟",
            subtitle = "پایه چهارم دبستان — مطالعات اجتماعی",
            lessonIds = listOf(
                "elem4-social_studies-01-hamsaye-ma"
            ),
            checkLessonId = "elem4-social_studies-01-hamsaye-ma",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 333,
            title = "مطالعات چهارم ۲: اینجا محله ماست — محله ما چه جاهایی دارد؟",
            subtitle = "پایه چهارم دبستان — مطالعات اجتماعی",
            lessonIds = listOf(
                "elem4-social_studies-02-inja-mahalle-mast"
            ),
            checkLessonId = "elem4-social_studies-02-inja-mahalle-mast",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 334,
            title = "مطالعات چهارم ۳: خرید و فروش در محله — چطور خوب خرید کنیم؟",
            subtitle = "پایه چهارم دبستان — مطالعات اجتماعی",
            lessonIds = listOf(
                "elem4-social_studies-03-kharid-foroush-mahalle"
            ),
            checkLessonId = "elem4-social_studies-03-kharid-foroush-mahalle",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 335,
            title = "مطالعات چهارم ۴: نقشه محله ما — محله را از بالا ببینیم",
            subtitle = "پایه چهارم دبستان — مطالعات اجتماعی",
            lessonIds = listOf(
                "elem4-social_studies-04-naghshe-mahalle"
            ),
            checkLessonId = "elem4-social_studies-04-naghshe-mahalle",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 336,
            title = "مطالعات چهارم ۵: زندگی در شهر و روستا — شهر با روستا چه فرقی دارد؟",
            subtitle = "پایه چهارم دبستان — مطالعات اجتماعی",
            lessonIds = listOf(
                "elem4-social_studies-05-zendegi-shahr-rousta"
            ),
            checkLessonId = "elem4-social_studies-05-zendegi-shahr-rousta",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 337,
            title = "مطالعات چهارم ۶: جهت‌های جغرافیایی — شمال و جنوب و شرق و غرب کجا هستند؟",
            subtitle = "پایه چهارم دبستان — مطالعات اجتماعی",
            lessonIds = listOf(
                "elem4-social_studies-06-jahat-joghrafiayi"
            ),
            checkLessonId = "elem4-social_studies-06-jahat-joghrafiayi",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 338,
            title = "مطالعات اجتماعی چهارم ۷: جغرافی‌دانان چگونه محیط‌های زندگی را مطالعه می‌کنند؟",
            subtitle = "پایه چهارم دبستان — مطالعات اجتماعی",
            lessonIds = listOf(
                "elem4-social_studies-07-joghrafidan-mohit"
            ),
            checkLessonId = "elem4-social_studies-07-joghrafidan-mohit",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 339,
            title = "مطالعات اجتماعی چهارم ۸: نخستین روستاها چگونه به وجود آمدند؟",
            subtitle = "پایه چهارم دبستان — مطالعات اجتماعی",
            lessonIds = listOf(
                "elem4-social_studies-08-nakhostin-roustaha"
            ),
            checkLessonId = "elem4-social_studies-08-nakhostin-roustaha",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 340,
            title = "مطالعات اجتماعی چهارم ۹: نخستین شهرها چگونه به وجود آمدند؟",
            subtitle = "پایه چهارم دبستان — مطالعات اجتماعی",
            lessonIds = listOf(
                "elem4-social_studies-09-nakhostin-shahrha"
            ),
            checkLessonId = "elem4-social_studies-09-nakhostin-shahrha",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 341,
            title = "مطالعات اجتماعی چهارم ۱۰: مورخان چگونه گذشته را مطالعه می‌کنند؟",
            subtitle = "پایه چهارم دبستان — مطالعات اجتماعی",
            lessonIds = listOf(
                "elem4-social_studies-10-movarekhan-gozashte"
            ),
            checkLessonId = "elem4-social_studies-10-movarekhan-gozashte",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 342,
            title = "مطالعات اجتماعی چهارم ۱۱: سفری به شهر باستانی همدان",
            subtitle = "پایه چهارم دبستان — مطالعات اجتماعی",
            lessonIds = listOf(
                "elem4-social_studies-11-hamedan-bastani"
            ),
            checkLessonId = "elem4-social_studies-11-hamedan-bastani",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 343,
            title = "مطالعات اجتماعی چهارم ۱۲: سفری به تخت جمشید (۱)",
            subtitle = "پایه چهارم دبستان — مطالعات اجتماعی",
            lessonIds = listOf(
                "elem4-social_studies-12-takht-jamshid-1"
            ),
            checkLessonId = "elem4-social_studies-12-takht-jamshid-1",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 344,
            title = "مطالعات اجتماعی چهارم ۱۳: سفری به تخت جمشید (۲)",
            subtitle = "پایه چهارم دبستان — مطالعات اجتماعی",
            lessonIds = listOf(
                "elem4-social_studies-13-takht-jamshid-2"
            ),
            checkLessonId = "elem4-social_studies-13-takht-jamshid-2",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 345,
            title = "مطالعات اجتماعی چهارم ۱۴: سفری به شهر باستانی کرمانشاه",
            subtitle = "پایه چهارم دبستان — مطالعات اجتماعی",
            lessonIds = listOf(
                "elem4-social_studies-14-kermanshah-bastani"
            ),
            checkLessonId = "elem4-social_studies-14-kermanshah-bastani",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 346,
            title = "مطالعات اجتماعی چهارم ۱۵: کوه‌ها و دشت‌های زیبا",
            subtitle = "پایه چهارم دبستان — مطالعات اجتماعی",
            lessonIds = listOf(
                "elem4-social_studies-15-kouh-dasht"
            ),
            checkLessonId = "elem4-social_studies-15-kouh-dasht",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 347,
            title = "مطالعات اجتماعی چهارم ۱۶: آب و هوا",
            subtitle = "پایه چهارم دبستان — مطالعات اجتماعی",
            lessonIds = listOf(
                "elem4-social_studies-16-ab-hava"
            ),
            checkLessonId = "elem4-social_studies-16-ab-hava",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 348,
            title = "مطالعات چهارم ۱۷: نواحی آب و هوایی ایران — چهار آب و هوای گوناگون",
            subtitle = "پایه چهارم دبستان — مطالعات اجتماعی",
            lessonIds = listOf(
                "elem4-social_studies-17-navahi-ab-havayi"
            ),
            checkLessonId = "elem4-social_studies-17-navahi-ab-havayi",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 349,
            title = "مطالعات چهارم ۱۸: پوشش گیاهی و زندگی جانوری در ایران",
            subtitle = "پایه چهارم دبستان — مطالعات اجتماعی",
            lessonIds = listOf(
                "elem4-social_studies-18-poushesh-giyahi-janvari"
            ),
            checkLessonId = "elem4-social_studies-18-poushesh-giyahi-janvari",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 350,
            title = "مطالعات چهارم ۱۹: از محیط زیست مراقبت کنیم",
            subtitle = "پایه چهارم دبستان — مطالعات اجتماعی",
            lessonIds = listOf(
                "elem4-social_studies-19-mohit-zist-moraghebat"
            ),
            checkLessonId = "elem4-social_studies-19-mohit-zist-moraghebat",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 351,
            title = "مطالعات چهارم ۲۰: نشانه‌های ملی ما",
            subtitle = "پایه چهارم دبستان — مطالعات اجتماعی",
            lessonIds = listOf(
                "elem4-social_studies-20-neshanehaye-melli"
            ),
            checkLessonId = "elem4-social_studies-20-neshanehaye-melli",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 352,
            title = "مطالعات چهارم ۲۱: تقویم",
            subtitle = "پایه چهارم دبستان — مطالعات اجتماعی",
            lessonIds = listOf(
                "elem4-social_studies-21-taghvim"
            ),
            checkLessonId = "elem4-social_studies-21-taghvim",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 353,
            title = "مطالعات چهارم ۲۲: روزهای مهم",
            subtitle = "پایه چهارم دبستان — مطالعات اجتماعی",
            lessonIds = listOf(
                "elem4-social_studies-22-rouzhaye-mohem"
            ),
            checkLessonId = "elem4-social_studies-22-rouzhaye-mohem",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 354,
            title = "هدیه چهارم ۱: دانه‌ای که نمی‌خواست بروید!",
            subtitle = "پایه چهارم دبستان — هدیه‌های آسمانی",
            lessonIds = listOf(
                "elem4-hedyeh-01-danei-nemikhast"
            ),
            checkLessonId = "elem4-hedyeh-01-danei-nemikhast",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 355,
            title = "هدیه چهارم ۲: کودکی بر آب",
            subtitle = "پایه چهارم دبستان — هدیه‌های آسمانی",
            lessonIds = listOf(
                "elem4-hedyeh-02-koudaki-bar-ab"
            ),
            checkLessonId = "elem4-hedyeh-02-koudaki-bar-ab",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 356,
            title = "هدیه چهارم ۳: ما به مسجد می‌رویم",
            subtitle = "پایه چهارم دبستان — هدیه‌های آسمانی",
            lessonIds = listOf(
                "elem4-hedyeh-03-ma-be-masjed-miravim"
            ),
            checkLessonId = "elem4-hedyeh-03-ma-be-masjed-miravim",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 357,
            title = "هدیه چهارم ۴: یک نماز و ده رکوع!",
            subtitle = "پایه چهارم دبستان — هدیه‌های آسمانی",
            lessonIds = listOf(
                "elem4-hedyeh-04-yek-namaz-dah-rokou"
            ),
            checkLessonId = "elem4-hedyeh-04-yek-namaz-dah-rokou",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 358,
            title = "هدیه چهارم ۵: سخنی که سه بار تکرار شد! — همسایه را آزار ندهیم",
            subtitle = "پایه چهارم دبستان — هدیه‌های آسمانی",
            lessonIds = listOf(
                "elem4-hedyeh-05-sokhani-se-bar"
            ),
            checkLessonId = "elem4-hedyeh-05-sokhani-se-bar",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 359,
            title = "هدیه چهارم ۶: حرمی با دو گنبد — امام موسی کاظم و امام جواد",
            subtitle = "پایه چهارم دبستان — هدیه‌های آسمانی",
            lessonIds = listOf(
                "elem4-hedyeh-06-haram-dou-gonbad"
            ),
            checkLessonId = "elem4-hedyeh-06-haram-dou-gonbad",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 360,
            title = "هدیه چهارم ۷: نماز در کوهستان — تیمم یعنی چی؟",
            subtitle = "پایه چهارم دبستان — هدیه‌های آسمانی",
            lessonIds = listOf(
                "elem4-hedyeh-07-namaz-kouhestan"
            ),
            checkLessonId = "elem4-hedyeh-07-namaz-kouhestan",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 361,
            title = "هدیه چهارم ۸: دیدار دوست — دعبل و امام رضا",
            subtitle = "پایه چهارم دبستان — هدیه‌های آسمانی",
            lessonIds = listOf(
                "elem4-hedyeh-08-didar-doust"
            ),
            checkLessonId = "elem4-hedyeh-08-didar-doust",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 362,
            title = "هدیه چهارم ۹: کودک شجاع — امام جواد در برابر مأمون",
            subtitle = "پایه چهارم دبستان — هدیه‌های آسمانی",
            lessonIds = listOf(
                "elem4-hedyeh-09-koudak-shoja"
            ),
            checkLessonId = "elem4-hedyeh-09-koudak-shoja",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 363,
            title = "هدیه چهارم ۱۰: روشن‌ترین شب — بعثت پیامبر",
            subtitle = "پایه چهارم دبستان — هدیه‌های آسمانی",
            lessonIds = listOf(
                "elem4-hedyeh-10-roshantarin-shab"
            ),
            checkLessonId = "elem4-hedyeh-10-roshantarin-shab",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 364,
            title = "هدیه چهارم ۱۱: دو دوست (ابوذر و پیامبر) — عنوان توصیفی، نیازمند منبع",
            subtitle = "پایه چهارم دبستان — هدیه‌های آسمانی",
            lessonIds = listOf(
                "elem4-hedyeh-11-lesson-11"
            ),
            checkLessonId = "elem4-hedyeh-11-lesson-11",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 365,
            title = "هدیه چهارم ۱۲: روزی برای تمام بچه‌ها — انتظار مهدی موعود",
            subtitle = "پایه چهارم دبستان — هدیه‌های آسمانی",
            lessonIds = listOf(
                "elem4-hedyeh-12-rouzi-baraye-bacheha"
            ),
            checkLessonId = "elem4-hedyeh-12-rouzi-baraye-bacheha",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 366,
            title = "هدیه چهارم ۱۳: خاله نرگس — صله رحم یعنی چی؟",
            subtitle = "پایه چهارم دبستان — هدیه‌های آسمانی",
            lessonIds = listOf(
                "elem4-hedyeh-13-khale-narges"
            ),
            checkLessonId = "elem4-hedyeh-13-khale-narges",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 367,
            title = "هدیه چهارم ۱۴: اولین بانوی مسلمان — حضرت خدیجه",
            subtitle = "پایه چهارم دبستان — هدیه‌های آسمانی",
            lessonIds = listOf(
                "elem4-hedyeh-14-avalin-banou-mosalman"
            ),
            checkLessonId = "elem4-hedyeh-14-avalin-banou-mosalman",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 368,
            title = "هدیه چهارم ۱۵: یک ماجرای زیبا — نیکی به دیگران",
            subtitle = "پایه چهارم دبستان — هدیه‌های آسمانی",
            lessonIds = listOf(
                "elem4-hedyeh-15-yek-majeraye-ziba"
            ),
            checkLessonId = "elem4-hedyeh-15-yek-majeraye-ziba",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 369,
            title = "هدیه چهارم ۱۶: اسب طلایی — امانت‌داری",
            subtitle = "پایه چهارم دبستان — هدیه‌های آسمانی",
            lessonIds = listOf(
                "elem4-hedyeh-16-asb-talaee"
            ),
            checkLessonId = "elem4-hedyeh-16-asb-talaee",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 370,
            title = "هدیه چهارم ۱۷: آقای بهاری، خانم بهاری — احترام به معلم",
            subtitle = "پایه چهارم دبستان — هدیه‌های آسمانی",
            lessonIds = listOf(
                "elem4-hedyeh-17-aghaye-bahari"
            ),
            checkLessonId = "elem4-hedyeh-17-aghaye-bahari",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 371,
            title = "هدیه چهارم ۱۸: چشمان همیشه باز — خدا همه‌جا ما را می‌بیند",
            subtitle = "پایه چهارم دبستان — هدیه‌های آسمانی",
            lessonIds = listOf(
                "elem4-hedyeh-18-cheshman-hamishe-baz"
            ),
            checkLessonId = "elem4-hedyeh-18-cheshman-hamishe-baz",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 372,
            title = "هدیه چهارم ۱۹: خداجون از تو ممنونم — شکر نعمت‌ها",
            subtitle = "پایه چهارم دبستان — هدیه‌های آسمانی",
            lessonIds = listOf(
                "elem4-hedyeh-19-khodajoun-mamnounam"
            ),
            checkLessonId = "elem4-hedyeh-19-khodajoun-mamnounam",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 373,
            title = "قرآن چهارم ۱: یادآوری قواعد و سوره نبأ",
            subtitle = "پایه چهارم دبستان — قرآن",
            lessonIds = listOf(
                "elem4-quran-01-yadavari-naba"
            ),
            checkLessonId = "elem4-quran-01-yadavari-naba",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 374,
            title = "قرآن چهارم ۲: سوره تغابن و سوره نبأ",
            subtitle = "پایه چهارم دبستان — قرآن",
            lessonIds = listOf(
                "elem4-quran-02-taghabon-naba"
            ),
            checkLessonId = "elem4-quran-02-taghabon-naba",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 375,
            title = "قرآن چهارم ۳: سوره آل عمران و سوره عبس",
            subtitle = "پایه چهارم دبستان — قرآن",
            lessonIds = listOf(
                "elem4-quran-03-ale-emran-abas"
            ),
            checkLessonId = "elem4-quran-03-ale-emran-abas",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 376,
            title = "قرآن چهارم ۴: سوره فصلت و سوره عبس",
            subtitle = "پایه چهارم دبستان — قرآن",
            lessonIds = listOf(
                "elem4-quran-04-fosselat-abas"
            ),
            checkLessonId = "elem4-quran-04-fosselat-abas",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 377,
            title = "قرآن چهارم ۵: سوره آل عمران و سوره تکویر",
            subtitle = "پایه چهارم دبستان — قرآن",
            lessonIds = listOf(
                "elem4-quran-05-ale-emran-takvir"
            ),
            checkLessonId = "elem4-quran-05-ale-emran-takvir",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 378,
            title = "قرآن چهارم ۶: سوره اسراء و تکویر — علامت مد",
            subtitle = "پایه چهارم دبستان — قرآن",
            lessonIds = listOf(
                "elem4-quran-06"
            ),
            checkLessonId = "elem4-quran-06",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 379,
            title = "قرآن چهارم ۷: سوره نحل و سوره محمد",
            subtitle = "پایه چهارم دبستان — قرآن",
            lessonIds = listOf(
                "elem4-quran-07"
            ),
            checkLessonId = "elem4-quran-07",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 380,
            title = "قرآن چهارم ۸: داستان حضرت زکریا و مریم",
            subtitle = "پایه چهارم دبستان — قرآن",
            lessonIds = listOf(
                "elem4-quran-08"
            ),
            checkLessonId = "elem4-quran-08",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 381,
            title = "قرآن چهارم ۹: انفاق در راه خدا و راست‌گویی",
            subtitle = "پایه چهارم دبستان — قرآن",
            lessonIds = listOf(
                "elem4-quran-09"
            ),
            checkLessonId = "elem4-quran-09",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 382,
            title = "قرآن چهارم ۱۰: پایداری در راه خدا",
            subtitle = "پایه چهارم دبستان — قرآن",
            lessonIds = listOf(
                "elem4-quran-10"
            ),
            checkLessonId = "elem4-quran-10",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 383,
            title = "قرآن چهارم ۱۱: سوره ملک — وقف بر ی",
            subtitle = "پایه چهارم دبستان — قرآن",
            lessonIds = listOf(
                "elem4-quran-11"
            ),
            checkLessonId = "elem4-quran-11",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 384,
            title = "قرآن چهارم ۱۲: داستان حضرت زکریا و یحیی",
            subtitle = "پایه چهارم دبستان — قرآن",
            lessonIds = listOf(
                "elem4-quran-12"
            ),
            checkLessonId = "elem4-quran-12",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 385,
            title = "قرآن چهارم ۱۳: سوره نمل — خدای یاری‌رسان",
            subtitle = "پایه چهارم دبستان — قرآن",
            lessonIds = listOf(
                "elem4-quran-13"
            ),
            checkLessonId = "elem4-quran-13",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 386,
            title = "قرآن چهارم ۱۴: احترام و درود بر پیامبر",
            subtitle = "پایه چهارم دبستان — قرآن",
            lessonIds = listOf(
                "elem4-quran-14"
            ),
            checkLessonId = "elem4-quran-14",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 387,
            title = "قرآن چهارم — درس آخر: قرآن در تابستان",
            subtitle = "پایه چهارم دبستان — قرآن",
            lessonIds = listOf(
                "elem4-quran-15-quran-dar-tabestan"
            ),
            checkLessonId = "elem4-quran-15-quran-dar-tabestan",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 388,
            title = "فارسی پنجم ۱: تماشاخانه — طبیعت، تماشاخانه خداست",
            subtitle = "پایه پنجم دبستان — فارسی",
            lessonIds = listOf(
                "elem5-farsi-01-tamashakhaneh"
            ),
            checkLessonId = "elem5-farsi-01-tamashakhaneh",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 389,
            title = "فارسی پنجم ۲: فضل خدا — نعمت‌های خدا را نمی‌توان شمرد",
            subtitle = "پایه پنجم دبستان — فارسی",
            lessonIds = listOf(
                "elem5-farsi-02-fazl-khoda"
            ),
            checkLessonId = "elem5-farsi-02-fazl-khoda",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 390,
            title = "فارسی پنجم ۳: رازی و ساخت بیمارستان — دانشمند چطور جای بیمارستان را پیدا کرد؟",
            subtitle = "پایه پنجم دبستان — فارسی",
            lessonIds = listOf(
                "elem5-farsi-03-razi-bimarestan"
            ),
            checkLessonId = "elem5-farsi-03-razi-bimarestan",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 391,
            title = "فارسی پنجم ۴: بازرگان و پسران — گنج واقعی چیست؟",
            subtitle = "پایه پنجم دبستان — فارسی",
            lessonIds = listOf(
                "elem5-farsi-04-bazargan-pesaran"
            ),
            checkLessonId = "elem5-farsi-04-bazargan-pesaran",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 392,
            title = "فارسی پنجم ۵: چنار و کدوبن — رشد سریع و بی‌ریشه ماندگار نیست",
            subtitle = "پایه پنجم دبستان — فارسی",
            lessonIds = listOf(
                "elem5-farsi-05-chenar-kadoban"
            ),
            checkLessonId = "elem5-farsi-05-chenar-kadoban",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 393,
            title = "فارسی پنجم ۶: سرود ملی — معنی سرود ملی ایران",
            subtitle = "پایه پنجم دبستان — فارسی",
            lessonIds = listOf(
                "elem5-farsi-06-sorud-melli"
            ),
            checkLessonId = "elem5-farsi-06-sorud-melli",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 394,
            title = "فارسی پنجم ۷: درس آزاد، فرهنگ بومی ۱ — چطور یک متن آزاد را بخوانیم و بفهمیم؟",
            subtitle = "پایه پنجم دبستان — فارسی",
            lessonIds = listOf(
                "elem5-farsi-07-dars-azad-farhang-bumi"
            ),
            checkLessonId = "elem5-farsi-07-dars-azad-farhang-bumi",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 395,
            title = "فارسی پنجم ۸: دفاع از میهن — وطن یعنی چی و چرا از آن دفاع می‌کنیم؟",
            subtitle = "پایه پنجم دبستان — فارسی",
            lessonIds = listOf(
                "elem5-farsi-08-defa-az-mihan"
            ),
            checkLessonId = "elem5-farsi-08-defa-az-mihan",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 396,
            title = "فارسی پنجم ۹: نام‌آوران دیروز، امروز، فردا — نام‌آور شدن یعنی چی؟",
            subtitle = "پایه پنجم دبستان — فارسی",
            lessonIds = listOf(
                "elem5-farsi-09-namavaran-dirouz-emrouz-farda"
            ),
            checkLessonId = "elem5-farsi-09-namavaran-dirouz-emrouz-farda",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 397,
            title = "فارسی پنجم ۱۰: نام نیکو — نام نیک با کار نیک ساخته می‌شود",
            subtitle = "پایه پنجم دبستان — فارسی",
            lessonIds = listOf(
                "elem5-farsi-10-nam-niku"
            ),
            checkLessonId = "elem5-farsi-10-nam-niku",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 398,
            title = "فارسی پنجم ۱۱: نقش خردمندان — قلم از شمشیر کارسازتر است",
            subtitle = "پایه پنجم دبستان — فارسی",
            lessonIds = listOf(
                "elem5-farsi-11-naghshe-kheradmandan"
            ),
            checkLessonId = "elem5-farsi-11-naghshe-kheradmandan",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 399,
            title = "فارسی پنجم ۱۲: درس آزاد، فرهنگ بومی ۲ — روش خواندن متن آزاد",
            subtitle = "پایه پنجم دبستان — فارسی",
            lessonIds = listOf(
                "elem5-farsi-12-darse-azad-farhange-bumi-2"
            ),
            checkLessonId = "elem5-farsi-12-darse-azad-farhange-bumi-2",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 400,
            title = "فارسی پنجم ۱۳: روزی که باران می‌بارید — زیبایی با یاد معنوی کامل می‌شود",
            subtitle = "پایه پنجم دبستان — فارسی",
            lessonIds = listOf(
                "elem5-farsi-13-roozi-ke-baran-mibarid"
            ),
            checkLessonId = "elem5-farsi-13-roozi-ke-baran-mibarid",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 401,
            title = "فارسی پنجم ۱۴: شجاعت — نترسیدن نیست؛ ایستادگی با تدبیر است",
            subtitle = "پایه پنجم دبستان — فارسی",
            lessonIds = listOf(
                "elem5-farsi-14-shojaat"
            ),
            checkLessonId = "elem5-farsi-14-shojaat",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 402,
            title = "فارسی پنجم ۱۵: کاجستان — همدلی، ده را به کاجستان تبدیل کرد",
            subtitle = "پایه پنجم دبستان — فارسی",
            lessonIds = listOf(
                "elem5-farsi-15-kajestan"
            ),
            checkLessonId = "elem5-farsi-15-kajestan",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 403,
            title = "فارسی پنجم ۱۶: وقتی بوعلی، کودک بود — کنجکاوی و پشتکار، راه دانایی است",
            subtitle = "پایه پنجم دبستان — فارسی",
            lessonIds = listOf(
                "elem5-farsi-16-vaghti-booali-koodak-bood"
            ),
            checkLessonId = "elem5-farsi-16-vaghti-booali-koodak-bood",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 404,
            title = "فارسی پنجم ۱۷: کار و تلاش — مورچه با پای ملخ، بار گران می‌کشد",
            subtitle = "پایه پنجم دبستان — فارسی",
            lessonIds = listOf(
                "elem5-farsi-17-kar-va-talash"
            ),
            checkLessonId = "elem5-farsi-17-kar-va-talash",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 405,
            title = "نگارش پنجم ۱: تماشاخانه — تصویرنویسی؛ از روی تصویر، دو بند بنویس",
            subtitle = "پایه پنجم دبستان — نگارش",
            lessonIds = listOf(
                "elem5-negaresh-01-tamashakhaneh"
            ),
            checkLessonId = "elem5-negaresh-01-tamashakhaneh",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 406,
            title = "نگارش پنجم ۲: فضل خدا — درباره یک نعمت بنویس و دلیل بیاور",
            subtitle = "پایه پنجم دبستان — نگارش",
            lessonIds = listOf(
                "elem5-negaresh-02-fazle-khoda"
            ),
            checkLessonId = "elem5-negaresh-02-fazle-khoda",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 407,
            title = "نگارش پنجم ۳: رازی و ساخت بیمارستان — شرح حال بنویس؛ با ترتیب زمانی",
            subtitle = "پایه پنجم دبستان — نگارش",
            lessonIds = listOf(
                "elem5-negaresh-03-razi-va-sakhte-bimarestan"
            ),
            checkLessonId = "elem5-negaresh-03-razi-va-sakhte-bimarestan",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 408,
            title = "نگارش پنجم ۴: بازرگان و پسران — نوشتن با نشانه‌های ربط",
            subtitle = "پایه پنجم دبستان — نگارش",
            lessonIds = listOf(
                "elem5-negaresh-04-bazargan"
            ),
            checkLessonId = "elem5-negaresh-04-bazargan",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 409,
            title = "نگارش پنجم ۵: چنار و کدوبن — نوشتن مقایسه و تفاوت‌ها",
            subtitle = "پایه پنجم دبستان — نگارش",
            lessonIds = listOf(
                "elem5-negaresh-05-chenar"
            ),
            checkLessonId = "elem5-negaresh-05-chenar",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 410,
            title = "نگارش پنجم ۶: سرود ملی — نوشتن درباره احساس و میهن",
            subtitle = "پایه پنجم دبستان — نگارش",
            lessonIds = listOf(
                "elem5-negaresh-06-sorud"
            ),
            checkLessonId = "elem5-negaresh-06-sorud",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 411,
            title = "نگارش پنجم ۷: درس آزاد، فرهنگ بومی ۱ — نوشتن درباره آداب و رسوم خودمان",
            subtitle = "پایه پنجم دبستان — نگارش",
            lessonIds = listOf(
                "elem5-negaresh-07-azad1"
            ),
            checkLessonId = "elem5-negaresh-07-azad1",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 412,
            title = "نگارش پنجم ۸: دفاع از میهن — نوشتن با شروع و پایان درست",
            subtitle = "پایه پنجم دبستان — نگارش",
            lessonIds = listOf(
                "elem5-negaresh-08-defa"
            ),
            checkLessonId = "elem5-negaresh-08-defa",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 413,
            title = "نگارش پنجم ۹: نام‌آوران دیروز، امروز، فردا — شرح حال‌نویسی",
            subtitle = "پایه پنجم دبستان — نگارش",
            lessonIds = listOf(
                "elem5-negaresh-09-namavaran"
            ),
            checkLessonId = "elem5-negaresh-09-namavaran",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 414,
            title = "نگارش پنجم ۱۰: نام نیکو — نوشتن درباره معنای نام‌ها",
            subtitle = "پایه پنجم دبستان — نگارش",
            lessonIds = listOf(
                "elem5-negaresh-10-namniko"
            ),
            checkLessonId = "elem5-negaresh-10-namniko",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 415,
            title = "نگارش پنجم ۱۱: نقش خردمندان — نوشتن با دلیل و مثال",
            subtitle = "پایه پنجم دبستان — نگارش",
            lessonIds = listOf(
                "elem5-negaresh-11-kheradmandan"
            ),
            checkLessonId = "elem5-negaresh-11-kheradmandan",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 416,
            title = "نگارش پنجم ۱۲: درس آزاد، فرهنگ بومی ۲ — نوشتن درباره یک آدم یا یک جای محلی",
            subtitle = "پایه پنجم دبستان — نگارش",
            lessonIds = listOf(
                "elem5-negaresh-12-azad2"
            ),
            checkLessonId = "elem5-negaresh-12-azad2",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 417,
            title = "نگارش پنجم ۱۳: روزی که باران می‌بارید — توصیف یک روز بارانی",
            subtitle = "پایه پنجم دبستان — نگارش",
            lessonIds = listOf(
                "elem5-negaresh-13-baran"
            ),
            checkLessonId = "elem5-negaresh-13-baran",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 418,
            title = "نگارش پنجم ۱۴: شجاعت — نوشتن درباره شجاعت",
            subtitle = "پایه پنجم دبستان — نگارش",
            lessonIds = listOf(
                "elem5-negaresh-14-shojaat"
            ),
            checkLessonId = "elem5-negaresh-14-shojaat",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 419,
            title = "نگارش پنجم ۱۵: کاجستان — نوشتن توصیفی از طبیعت",
            subtitle = "پایه پنجم دبستان — نگارش",
            lessonIds = listOf(
                "elem5-negaresh-15-kajestan"
            ),
            checkLessonId = "elem5-negaresh-15-kajestan",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 420,
            title = "نگارش پنجم ۱۶: وقتی بوعلی، کودک بود — نوشتن روایت و زندگی‌نامه کوتاه",
            subtitle = "پایه پنجم دبستان — نگارش",
            lessonIds = listOf(
                "elem5-negaresh-16-bouali-koudak"
            ),
            checkLessonId = "elem5-negaresh-16-bouali-koudak",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 421,
            title = "نگارش پنجم ۱۷: کار و تلاش — نوشتن درباره کار و تلاش",
            subtitle = "پایه پنجم دبستان — نگارش",
            lessonIds = listOf(
                "elem5-negaresh-17-kar-talaash"
            ),
            checkLessonId = "elem5-negaresh-17-kar-talaash",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 422,
            title = "ریاضی پنجم فصل ۱: عددنویسی و الگوها",
            subtitle = "پایه پنجم دبستان — ریاضی",
            lessonIds = listOf(
                "elem5-math-01-adadnavisi-olgouha"
            ),
            checkLessonId = "elem5-math-01-adadnavisi-olgouha",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 423,
            title = "ریاضی پنجم فصل ۲: کسر",
            subtitle = "پایه پنجم دبستان — ریاضی",
            lessonIds = listOf(
                "elem5-math-02-kasr"
            ),
            checkLessonId = "elem5-math-02-kasr",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 424,
            title = "ریاضی پنجم فصل ۳: نسبت، تناسب و درصد",
            subtitle = "پایه پنجم دبستان — ریاضی",
            lessonIds = listOf(
                "elem5-math-03-nesbat-tanasob-darsad"
            ),
            checkLessonId = "elem5-math-03-nesbat-tanasob-darsad",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 425,
            title = "ریاضی پنجم فصل ۴: تقارن و چندضلعی‌ها",
            subtitle = "پایه پنجم دبستان — ریاضی",
            lessonIds = listOf(
                "elem5-math-04-tagharon-chandzaleiha"
            ),
            checkLessonId = "elem5-math-04-tagharon-chandzaleiha",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 426,
            title = "ریاضی پنجم فصل ۵: اعداد اعشاری",
            subtitle = "پایه پنجم دبستان — ریاضی",
            lessonIds = listOf(
                "elem5-math-05-aadad-ashari"
            ),
            checkLessonId = "elem5-math-05-aadad-ashari",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 427,
            title = "ریاضی پنجم فصل ۶: اندازه‌گیری",
            subtitle = "پایه پنجم دبستان — ریاضی",
            lessonIds = listOf(
                "elem5-math-06-andazegiri"
            ),
            checkLessonId = "elem5-math-06-andazegiri",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 428,
            title = "ریاضی پنجم، فصل ۷: آمار و احتمال",
            subtitle = "پایه پنجم دبستان — ریاضی",
            lessonIds = listOf(
                "elem5-math-07-amar-va-ehtemal"
            ),
            checkLessonId = "elem5-math-07-amar-va-ehtemal",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 429,
            title = "علوم پنجم، درس ۱: زنگ علوم",
            subtitle = "پایه پنجم دبستان — علوم تجربی",
            lessonIds = listOf(
                "elem5-science-01-zang-olum"
            ),
            checkLessonId = "elem5-science-01-zang-olum",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 430,
            title = "علوم پنجم، درس ۲: ماده تغییر می‌کند",
            subtitle = "پایه پنجم دبستان — علوم تجربی",
            lessonIds = listOf(
                "elem5-science-02-madeh-taghieer-mikonad"
            ),
            checkLessonId = "elem5-science-02-madeh-taghieer-mikonad",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 431,
            title = "علوم پنجم، درس ۳: رنگین‌کمان",
            subtitle = "پایه پنجم دبستان — علوم تجربی",
            lessonIds = listOf(
                "elem5-science-03-rangin-kaman"
            ),
            checkLessonId = "elem5-science-03-rangin-kaman",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 432,
            title = "علوم پنجم، درس ۴: برگی از تاریخ زمین",
            subtitle = "پایه پنجم دبستان — علوم تجربی",
            lessonIds = listOf(
                "elem5-science-04-bargi-az-tarikh-zamin"
            ),
            checkLessonId = "elem5-science-04-bargi-az-tarikh-zamin",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 433,
            title = "علوم پنجم، درس ۵: حرکت بدن",
            subtitle = "پایه پنجم دبستان — علوم تجربی",
            lessonIds = listOf(
                "elem5-science-05-harekat-badan"
            ),
            checkLessonId = "elem5-science-05-harekat-badan",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 434,
            title = "علوم پنجم، درس ۶: چه خبر؟ (۱)",
            subtitle = "پایه پنجم دبستان — علوم تجربی",
            lessonIds = listOf(
                "elem5-science-06-che-khabar-1"
            ),
            checkLessonId = "elem5-science-06-che-khabar-1",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 435,
            title = "علوم پنجم، درس ۷: چه خبر؟ (۲)",
            subtitle = "پایه پنجم دبستان — علوم تجربی",
            lessonIds = listOf(
                "elem5-science-07-che-khabar-2"
            ),
            checkLessonId = "elem5-science-07-che-khabar-2",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 436,
            title = "علوم پنجم، درس ۸: کارها آسان می‌شود (۱)",
            subtitle = "پایه پنجم دبستان — علوم تجربی",
            lessonIds = listOf(
                "elem5-science-08-karha-asan-mishavad-1"
            ),
            checkLessonId = "elem5-science-08-karha-asan-mishavad-1",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 437,
            title = "علوم پنجم، درس ۹: کارها آسان می‌شود (۲)",
            subtitle = "پایه پنجم دبستان — علوم تجربی",
            lessonIds = listOf(
                "elem5-science-09-karha-asan-mishavad-2"
            ),
            checkLessonId = "elem5-science-09-karha-asan-mishavad-2",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 438,
            title = "علوم پنجم ۱۰: خاک با ارزش — خاک چیست و چرا باارزش است؟",
            subtitle = "پایه پنجم دبستان — علوم تجربی",
            lessonIds = listOf(
                "elem5-science-10-khak-ba-arzesh"
            ),
            checkLessonId = "elem5-science-10-khak-ba-arzesh",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 439,
            title = "علوم پنجم ۱۱: بکارید و بخورید — گیاهان چه چیزهایی لازم دارند؟",
            subtitle = "پایه پنجم دبستان — علوم تجربی",
            lessonIds = listOf(
                "elem5-science-11-bekarid-va-bokhorid"
            ),
            checkLessonId = "elem5-science-11-bekarid-va-bokhorid",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 440,
            title = "علوم پنجم ۱۲: از ریشه تا برگ — هر بخش گیاه چه کاری می‌کند؟",
            subtitle = "پایه پنجم دبستان — علوم تجربی",
            lessonIds = listOf(
                "elem5-science-12-az-rishe-ta-barg"
            ),
            checkLessonId = "elem5-science-12-az-rishe-ta-barg",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 441,
            title = "مطالعات اجتماعی پنجم ۱: من با دیگران ارتباط برقرار می‌کنم",
            subtitle = "پایه پنجم دبستان — مطالعات اجتماعی",
            lessonIds = listOf(
                "elem5-social-01-ertebat-ba-digaran"
            ),
            checkLessonId = "elem5-social-01-ertebat-ba-digaran",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 442,
            title = "مطالعات اجتماعی پنجم ۲: احساسات ما",
            subtitle = "پایه پنجم دبستان — مطالعات اجتماعی",
            lessonIds = listOf(
                "elem5-social-02-ehsasat-ma"
            ),
            checkLessonId = "elem5-social-02-ehsasat-ma",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 443,
            title = "مطالعات اجتماعی پنجم ۳: همدلی با دیگران",
            subtitle = "پایه پنجم دبستان — مطالعات اجتماعی",
            lessonIds = listOf(
                "elem5-social-03-hamdeli-ba-digaran"
            ),
            checkLessonId = "elem5-social-03-hamdeli-ba-digaran",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 444,
            title = "مطالعات اجتماعی پنجم ۴: من عضو گروه هستم",
            subtitle = "پایه پنجم دبستان — مطالعات اجتماعی",
            lessonIds = listOf(
                "elem5-social-04-ozv-goruh"
            ),
            checkLessonId = "elem5-social-04-ozv-goruh",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 445,
            title = "مطالعات اجتماعی پنجم ۵: جمعیت ایران",
            subtitle = "پایه پنجم دبستان — مطالعات اجتماعی",
            lessonIds = listOf(
                "elem5-social-05-jamiat-iran"
            ),
            checkLessonId = "elem5-social-05-jamiat-iran",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 446,
            title = "مطالعات اجتماعی پنجم ۶: منابع آب ایران",
            subtitle = "پایه پنجم دبستان — مطالعات اجتماعی",
            lessonIds = listOf(
                "elem5-social-06-manabe-ab-iran"
            ),
            checkLessonId = "elem5-social-06-manabe-ab-iran",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 447,
            title = "مطالعات اجتماعی پنجم ۷: نواحی صنعتی مهم ایران",
            subtitle = "پایه پنجم دبستان — مطالعات اجتماعی",
            lessonIds = listOf(
                "elem5-social-07-navahi-sanati-iran"
            ),
            checkLessonId = "elem5-social-07-navahi-sanati-iran",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 448,
            title = "مطالعات اجتماعی پنجم ۸: راه‌ها و حمل و نقل (۱) — از صفر",
            subtitle = "پایه پنجم دبستان — مطالعات اجتماعی",
            lessonIds = listOf(
                "elem5-social-08-rahha-va-hamlonaghl-1"
            ),
            checkLessonId = "elem5-social-08-rahha-va-hamlonaghl-1",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 449,
            title = "مطالعات اجتماعی پنجم ۹: راه‌ها و حمل و نقل (۲) — از صفر",
            subtitle = "پایه پنجم دبستان — مطالعات اجتماعی",
            lessonIds = listOf(
                "elem5-social-09-rahha-va-hamlonaghl-2"
            ),
            checkLessonId = "elem5-social-09-rahha-va-hamlonaghl-2",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 450,
            title = "مطالعات اجتماعی پنجم ۱۰: کشور ما چگونه اداره می‌شود؟ — از صفر",
            subtitle = "پایه پنجم دبستان — مطالعات اجتماعی",
            lessonIds = listOf(
                "elem5-social-10-keshvar-ma-chegone-edare-mishavad"
            ),
            checkLessonId = "elem5-social-10-keshvar-ma-chegone-edare-mishavad",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 451,
            title = "مطالعات اجتماعی پنجم ۱۱: کشورهای همسایه (۱) — از صفر",
            subtitle = "پایه پنجم دبستان — مطالعات اجتماعی",
            lessonIds = listOf(
                "elem5-social-11-keshvarhaye-hamsaye-1"
            ),
            checkLessonId = "elem5-social-11-keshvarhaye-hamsaye-1",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 452,
            title = "مطالعات اجتماعی پنجم ۱۲: کشورهای همسایه (۲) — از صفر",
            subtitle = "پایه پنجم دبستان — مطالعات اجتماعی",
            lessonIds = listOf(
                "elem5-social-12-keshvarhaye-hamsaye-2"
            ),
            checkLessonId = "elem5-social-12-keshvarhaye-hamsaye-2",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 453,
            title = "مطالعات اجتماعی پنجم ۱۳: حرکت‌های زمین — از صفر",
            subtitle = "پایه پنجم دبستان — مطالعات اجتماعی",
            lessonIds = listOf(
                "elem5-social-13-harekathaye-zamin"
            ),
            checkLessonId = "elem5-social-13-harekathaye-zamin",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 454,
            title = "مطالعات اجتماعی پنجم ۱۴: زندگی در نواحی مختلف جهان — از صفر",
            subtitle = "پایه پنجم دبستان — مطالعات اجتماعی",
            lessonIds = listOf(
                "elem5-social-14-zendegi-dar-navahi-mokhtalef-jahan"
            ),
            checkLessonId = "elem5-social-14-zendegi-dar-navahi-mokhtalef-jahan",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 455,
            title = "مطالعات اجتماعی پنجم ۱۵: بازگشت از سفر حج — از صفر",
            subtitle = "پایه پنجم دبستان — مطالعات اجتماعی",
            lessonIds = listOf(
                "elem5-social-15-bazgasht-az-safar-hajj"
            ),
            checkLessonId = "elem5-social-15-bazgasht-az-safar-hajj",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 456,
            title = "مطالعات اجتماعی پنجم ۱۶: مدینه، شهر پیامبر (ص) — از صفر",
            subtitle = "پایه پنجم دبستان — مطالعات اجتماعی",
            lessonIds = listOf(
                "elem5-social-16-madine-shahr-payambar"
            ),
            checkLessonId = "elem5-social-16-madine-shahr-payambar",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 457,
            title = "مطالعات اجتماعی پنجم ۱۷: سفر به کربلا (۱) — از صفر",
            subtitle = "پایه پنجم دبستان — مطالعات اجتماعی",
            lessonIds = listOf(
                "elem5-social-17-safar-be-karbala-1"
            ),
            checkLessonId = "elem5-social-17-safar-be-karbala-1",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 458,
            title = "مطالعات اجتماعی پنجم ۱۸: سفر به کربلا (۲) — زیارت حرم امام حسین",
            subtitle = "پایه پنجم دبستان — مطالعات اجتماعی",
            lessonIds = listOf(
                "elem5-social-18-safar-karbala-2"
            ),
            checkLessonId = "elem5-social-18-safar-karbala-2",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 459,
            title = "مطالعات اجتماعی پنجم ۱۹: ایرانیان مسلمان حکومت تشکیل می‌دهند",
            subtitle = "پایه پنجم دبستان — مطالعات اجتماعی",
            lessonIds = listOf(
                "elem5-social-19-hokoomat-iranian"
            ),
            checkLessonId = "elem5-social-19-hokoomat-iranian",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 460,
            title = "مطالعات اجتماعی پنجم ۲۰: وزیران کاردان، شهرهای آباد",
            subtitle = "پایه پنجم دبستان — مطالعات اجتماعی",
            lessonIds = listOf(
                "elem5-social-20-vaziran-kardan"
            ),
            checkLessonId = "elem5-social-20-vaziran-kardan",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 461,
            title = "مطالعات اجتماعی پنجم ۲۱: کشورگشایان بی‌رحم",
            subtitle = "پایه پنجم دبستان — مطالعات اجتماعی",
            lessonIds = listOf(
                "elem5-social-21-keshvargoshayan"
            ),
            checkLessonId = "elem5-social-21-keshvargoshayan",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 462,
            title = "مطالعات اجتماعی پنجم ۲۲: بازسازی ویرانه‌ها",
            subtitle = "پایه پنجم دبستان — مطالعات اجتماعی",
            lessonIds = listOf(
                "elem5-social-22-bazsazi"
            ),
            checkLessonId = "elem5-social-22-bazsazi",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 463,
            title = "هدیه‌های آسمانی پنجم ۱: دسته گلی از آسمان",
            subtitle = "پایه پنجم دبستان — هدیه‌های آسمانی",
            lessonIds = listOf(
                "elem5-hedyeh-01-daste-gol-aseman"
            ),
            checkLessonId = "elem5-hedyeh-01-daste-gol-aseman",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 464,
            title = "هدیه‌های آسمانی پنجم ۲: تنها او",
            subtitle = "پایه پنجم دبستان — هدیه‌های آسمانی",
            lessonIds = listOf(
                "elem5-hedyeh-02-tanha-ou"
            ),
            checkLessonId = "elem5-hedyeh-02-tanha-ou",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 465,
            title = "هدیه‌های آسمانی پنجم ۳: بانویی که یک سوره به نام اوست",
            subtitle = "پایه پنجم دبستان — هدیه‌های آسمانی",
            lessonIds = listOf(
                "elem5-hedyeh-03-banou-sore"
            ),
            checkLessonId = "elem5-hedyeh-03-banou-sore",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 466,
            title = "هدیه‌های آسمانی پنجم ۴: از نوزاد بپرسید!",
            subtitle = "پایه پنجم دبستان — هدیه‌های آسمانی",
            lessonIds = listOf(
                "elem5-hedyeh-04-az-nozad-beporsid"
            ),
            checkLessonId = "elem5-hedyeh-04-az-nozad-beporsid",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 467,
            title = "هدیه‌های آسمانی پنجم ۵: گل صد برگ",
            subtitle = "پایه پنجم دبستان — هدیه‌های آسمانی",
            lessonIds = listOf(
                "elem5-hedyeh-05-gol-sad-barg"
            ),
            checkLessonId = "elem5-hedyeh-05-gol-sad-barg",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 468,
            title = "هدیه پنجم ۶: مال مردم — مال دیگران را بدون اجازه برنداریم",
            subtitle = "پایه پنجم دبستان — هدیه‌های آسمانی",
            lessonIds = listOf(
                "elem5-hedyeh-06-mal-mardom"
            ),
            checkLessonId = "elem5-hedyeh-06-mal-mardom",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 469,
            title = "هدیه پنجم ۷: رنگین‌کمان جمعه — روز جمعه، روز شادی و نماز جمعه",
            subtitle = "پایه پنجم دبستان — هدیه‌های آسمانی",
            lessonIds = listOf(
                "elem5-hedyeh-07-ranginkaman-jome"
            ),
            checkLessonId = "elem5-hedyeh-07-ranginkaman-jome",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 470,
            title = "هدیه پنجم ۸: دو نامه — مراقب زبانت باش",
            subtitle = "پایه پنجم دبستان — هدیه‌های آسمانی",
            lessonIds = listOf(
                "elem5-hedyeh-08-do-name"
            ),
            checkLessonId = "elem5-hedyeh-08-do-name",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 471,
            title = "هدیه پنجم ۹: یک جهان جشن! — روز غدیر خم",
            subtitle = "پایه پنجم دبستان — هدیه‌های آسمانی",
            lessonIds = listOf(
                "elem5-hedyeh-09-yek-jahan-jashn"
            ),
            checkLessonId = "elem5-hedyeh-09-yek-jahan-jashn",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 472,
            title = "هدیه پنجم ۱۰: در ساحل دجله — امام هادی، امام دهم",
            subtitle = "پایه پنجم دبستان — هدیه‌های آسمانی",
            lessonIds = listOf(
                "elem5-hedyeh-10-dar-sahel-dajle"
            ),
            checkLessonId = "elem5-hedyeh-10-dar-sahel-dajle",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 473,
            title = "هدیه پنجم ۱۱: سروِ سربلند سامرا — امام حسن عسکری، امام یازدهم",
            subtitle = "پایه پنجم دبستان — هدیه‌های آسمانی",
            lessonIds = listOf(
                "elem5-hedyeh-11-sarv-sarboland-samara"
            ),
            checkLessonId = "elem5-hedyeh-11-sarv-sarboland-samara",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 474,
            title = "هدیه پنجم ۱۲: خورشید پشت ابر — امام مهدی و غیبت",
            subtitle = "پایه پنجم دبستان — هدیه‌های آسمانی",
            lessonIds = listOf(
                "elem5-hedyeh-12-khorshid-posht-abr"
            ),
            checkLessonId = "elem5-hedyeh-12-khorshid-posht-abr",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 475,
            title = "هدیه پنجم ۱۳: کوچک‌های بزرگ! — پرهیز از اسراف",
            subtitle = "پایه پنجم دبستان — هدیه‌های آسمانی",
            lessonIds = listOf(
                "elem5-hedyeh-13-koochakha-ye-bozorg"
            ),
            checkLessonId = "elem5-hedyeh-13-koochakha-ye-bozorg",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 476,
            title = "هدیه پنجم ۱۴: نمایش واقعی — قهرمان واقعی کیست؟",
            subtitle = "پایه پنجم دبستان — هدیه‌های آسمانی",
            lessonIds = listOf(
                "elem5-hedyeh-14-namayesh-vaghei"
            ),
            checkLessonId = "elem5-hedyeh-14-namayesh-vaghei",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 477,
            title = "هدیه پنجم ۱۵: این داستان را دو بار باید خواند — مردم آریاشهر",
            subtitle = "پایه پنجم دبستان — هدیه‌های آسمانی",
            lessonIds = listOf(
                "elem5-hedyeh-15-in-dastan-ra-do-bar-bayad-khand"
            ),
            checkLessonId = "elem5-hedyeh-15-in-dastan-ra-do-bar-bayad-khand",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 478,
            title = "هدیه‌های پنجم ۱۶: روزنامه‌های دیواری — کارنامه زندگی ما",
            subtitle = "پایه پنجم دبستان — هدیه‌های آسمانی",
            lessonIds = listOf(
                "elem5-hedyeh-16-rooznamehaye-divari"
            ),
            checkLessonId = "elem5-hedyeh-16-rooznamehaye-divari",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 479,
            title = "هدیه‌های پنجم ۱۷: اینها و آنها — دو گروه در روز قیامت",
            subtitle = "پایه پنجم دبستان — هدیه‌های آسمانی",
            lessonIds = listOf(
                "elem5-hedyeh-17-inha-va-anha"
            ),
            checkLessonId = "elem5-hedyeh-17-inha-va-anha",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 480,
            title = "قرآن پنجم ۱: یادآوری — نماز در قرآن",
            subtitle = "پایه پنجم دبستان — قرآن",
            lessonIds = listOf(
                "elem5-quran-01-yadavari"
            ),
            checkLessonId = "elem5-quran-01-yadavari",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 481,
            title = "قرآن پنجم ۲: لقمان ۱۶ تا ۱۹ — سفارش‌های لقمان به پسرش",
            subtitle = "پایه پنجم دبستان — قرآن",
            lessonIds = listOf(
                "elem5-quran-02-loghman-16-19"
            ),
            checkLessonId = "elem5-quran-02-loghman-16-19",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 482,
            title = "قرآن پنجم ۳: اعراف ۱۴۲ و ۱۴۳ — موسی و دیدار با خدا",
            subtitle = "پایه پنجم دبستان — قرآن",
            lessonIds = listOf(
                "elem5-quran-03-araf-142-143"
            ),
            checkLessonId = "elem5-quran-03-araf-142-143",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 483,
            title = "قرآن پنجم ۴: بقره ۲۸۵ و ۲۸۶ — خدا هیچ‌کس را بیش از توانش تکلیف نمی‌کند",
            subtitle = "پایه پنجم دبستان — قرآن",
            lessonIds = listOf(
                "elem5-quran-04-baqareh-285-286"
            ),
            checkLessonId = "elem5-quran-04-baqareh-285-286",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 484,
            title = "قرآن پنجم ۵: اسراء ۳۴ تا ۳۸ — به عهد خود وفا کن",
            subtitle = "پایه پنجم دبستان — قرآن",
            lessonIds = listOf(
                "elem5-quran-05-esra-34-38"
            ),
            checkLessonId = "elem5-quran-05-esra-34-38",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 485,
            title = "قرآن پنجم ۶: یوسف ۱ تا ۵ — خواب یوسف",
            subtitle = "پایه پنجم دبستان — قرآن",
            lessonIds = listOf(
                "elem5-quran-06-yusuf-1-5"
            ),
            checkLessonId = "elem5-quran-06-yusuf-1-5",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 486,
            title = "قرآن پنجم ۷: بقره ۲۵۹ — خواب صد ساله",
            subtitle = "پایه پنجم دبستان — قرآن",
            lessonIds = listOf(
                "elem5-quran-07-baqareh-259"
            ),
            checkLessonId = "elem5-quran-07-baqareh-259",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 487,
            title = "قرآن پنجم ۸: ابراهیم ۲۴ تا ۲۷ — سخن پاک مثل درخت پاک است",
            subtitle = "پایه پنجم دبستان — قرآن",
            lessonIds = listOf(
                "elem5-quran-08-ebrahim-24-27"
            ),
            checkLessonId = "elem5-quran-08-ebrahim-24-27",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 488,
            title = "قرآن پنجم ۹: سوره نحل، آیه‌های ۹۰ تا ۹۲ — عدالت، نیکی و وفای به عهد",
            subtitle = "پایه پنجم دبستان — قرآن",
            lessonIds = listOf(
                "elem5-quran-09-nahal-90-92"
            ),
            checkLessonId = "elem5-quran-09-nahal-90-92",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 489,
            title = "قرآن پنجم ۱۰: سوره حجر، آیه‌های ۱ تا ۹ — قرآن روشنگر و نگهبانی خدا از قرآن",
            subtitle = "پایه پنجم دبستان — قرآن",
            lessonIds = listOf(
                "elem5-quran-10-hajr-1-9"
            ),
            checkLessonId = "elem5-quran-10-hajr-1-9",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 490,
            title = "قرآن پنجم ۱۱: سوره زمر، آیه‌های ۷۳ تا ۷۵ — ورود پرهیزکاران به بهشت",
            subtitle = "پایه پنجم دبستان — قرآن",
            lessonIds = listOf(
                "elem5-quran-11-zomar-73-75"
            ),
            checkLessonId = "elem5-quran-11-zomar-73-75",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 491,
            title = "قرآن پنجم ۱۲: سوره فرقان، آیه‌های ۶۳ تا ۶۷ — بندگان خدای بخشنده",
            subtitle = "پایه پنجم دبستان — قرآن",
            lessonIds = listOf(
                "elem5-quran-12-forqan-63-67"
            ),
            checkLessonId = "elem5-quran-12-forqan-63-67",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 492,
            title = "فارسی ششم ۱: معرفت آفریدگار — خدا را از روی آفریده‌هایش بشناس",
            subtitle = "پایه ششم دبستان — فارسی",
            lessonIds = listOf(
                "elem6-farsi-01-marifat-afaridgar"
            ),
            checkLessonId = "elem6-farsi-01-marifat-afaridgar",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 493,
            title = "فارسی ششم ۲: پنجره‌های شناخت — پنج پنجره برای دیدن آفرینش",
            subtitle = "پایه ششم دبستان — فارسی",
            lessonIds = listOf(
                "elem6-farsi-02-panjerehaye-shenakht"
            ),
            checkLessonId = "elem6-farsi-02-panjerehaye-shenakht",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 494,
            title = "فارسی ششم ۳: هوشیاری — اول فکر کن، بعد عمل کن",
            subtitle = "پایه ششم دبستان — فارسی",
            lessonIds = listOf(
                "elem6-farsi-03-houshyari"
            ),
            checkLessonId = "elem6-farsi-03-houshyari",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 495,
            title = "فارسی ششم ۴: داستان من و شما — زبان فارسی، موجودی زنده",
            subtitle = "پایه ششم دبستان — فارسی",
            lessonIds = listOf(
                "elem6-farsi-04-dastan-man-va-shoma"
            ),
            checkLessonId = "elem6-farsi-04-dastan-man-va-shoma",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 496,
            title = "فارسی ششم ۵: هفت خان رستم — هفت مرحله سخت تا پیروزی",
            subtitle = "پایه ششم دبستان — فارسی",
            lessonIds = listOf(
                "elem6-farsi-05-haft-khan-rostam"
            ),
            checkLessonId = "elem6-farsi-05-haft-khan-rostam",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 497,
            title = "فارسی ششم ۶: ای وطن — سرودی برای ایران",
            subtitle = "پایه ششم دبستان — فارسی",
            lessonIds = listOf(
                "elem6-farsi-06-ey-vatan"
            ),
            checkLessonId = "elem6-farsi-06-ey-vatan",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 498,
            title = "فارسی ششم ۷: درس آزاد، فرهنگ بومی ۱ — خودت متن انتخاب کن و بخوان",
            subtitle = "پایه ششم دبستان — فارسی",
            lessonIds = listOf(
                "elem6-farsi-07-dars-azad-1"
            ),
            checkLessonId = "elem6-farsi-07-dars-azad-1",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 499,
            title = "فارسی ششم ۸: دریاقلی — یک دوچرخه، یک شهر",
            subtitle = "پایه ششم دبستان — فارسی",
            lessonIds = listOf(
                "elem6-farsi-08-daryagholi"
            ),
            checkLessonId = "elem6-farsi-08-daryagholi",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 500,
            title = "فارسی ششم ۹: رنج‌هایی کشیده‌ام که مپرس — زندگی دهخدا",
            subtitle = "پایه ششم دبستان — فارسی",
            lessonIds = listOf(
                "elem6-farsi-09-ranjhaei-keshideam"
            ),
            checkLessonId = "elem6-farsi-09-ranjhaei-keshideam",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 501,
            title = "فارسی ششم ۱۰: عطار و جلال‌الدین محمد — دیداری که آینده را خبر داد",
            subtitle = "پایه ششم دبستان — فارسی",
            lessonIds = listOf(
                "elem6-farsi-10-attar-va-jalaleddin"
            ),
            checkLessonId = "elem6-farsi-10-attar-va-jalaleddin",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 502,
            title = "فارسی ششم ۱۱: شهدا خورشیدند — شعر کلاس انشا",
            subtitle = "پایه ششم دبستان — فارسی",
            lessonIds = listOf(
                "elem6-farsi-11-shohada-khorshidand"
            ),
            checkLessonId = "elem6-farsi-11-shohada-khorshidand",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 503,
            title = "فارسی ششم ۱۲: دوستی و مشاوره — دو متن کهن درباره دوست و مشورت",
            subtitle = "پایه ششم دبستان — فارسی",
            lessonIds = listOf(
                "elem6-farsi-12-dousti-va-mashverat"
            ),
            checkLessonId = "elem6-farsi-12-dousti-va-mashverat",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 504,
            title = "فارسی ششم ۱۳: درس آزاد، فرهنگ بومی ۲ — روش خواندن و فهم متن آزاد",
            subtitle = "پایه ششم دبستان — فارسی",
            lessonIds = listOf(
                "elem6-farsi-13-dars-azad-2"
            ),
            checkLessonId = "elem6-farsi-13-dars-azad-2",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 505,
            title = "فارسی ششم ۱۴: راز زندگی — مناظره گل و غنچه",
            subtitle = "پایه ششم دبستان — فارسی",
            lessonIds = listOf(
                "elem6-farsi-14-raz-zendegi"
            ),
            checkLessonId = "elem6-farsi-14-raz-zendegi",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 506,
            title = "فارسی ششم ۱۵: میوه هنر — مناظره سپیدار و تبر",
            subtitle = "پایه ششم دبستان — فارسی",
            lessonIds = listOf(
                "elem6-farsi-15-miveh-honar"
            ),
            checkLessonId = "elem6-farsi-15-miveh-honar",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 507,
            title = "فارسی ششم ۱۶: آداب مطالعه — فایده قصه خواندن و آداب آن",
            subtitle = "پایه ششم دبستان — فارسی",
            lessonIds = listOf(
                "elem6-farsi-16-adab-motalee"
            ),
            checkLessonId = "elem6-farsi-16-adab-motalee",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 508,
            title = "فارسی ششم ۱۷: ستاره روشن — وصیت بزرگمهر حکیم",
            subtitle = "پایه ششم دبستان — فارسی",
            lessonIds = listOf(
                "elem6-farsi-17-setareh-roshan"
            ),
            checkLessonId = "elem6-farsi-17-setareh-roshan",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 509,
            title = "نگارش ششم ۱: معرفت آفریدگار — نوشتن درباره بهار و آفرینش",
            subtitle = "پایه ششم دبستان — نگارش",
            lessonIds = listOf(
                "elem6-negaresh-01-marifat-afaridgar"
            ),
            checkLessonId = "elem6-negaresh-01-marifat-afaridgar",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 510,
            title = "نگارش ششم ۲: پنجره‌های شناخت — توصیف با پنج حس",
            subtitle = "پایه ششم دبستان — نگارش",
            lessonIds = listOf(
                "elem6-negaresh-02-panjerehaye-shenakht"
            ),
            checkLessonId = "elem6-negaresh-02-panjerehaye-shenakht",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 511,
            title = "نگارش ششم ۳: هوشیاری — داستان‌گویی، گفت‌وگو و زندگی‌نامه",
            subtitle = "پایه ششم دبستان — نگارش",
            lessonIds = listOf(
                "elem6-negaresh-03-houshyari"
            ),
            checkLessonId = "elem6-negaresh-03-houshyari",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 512,
            title = "نگارش ششم درس ۴: داستان من و شما — از زبان فارسی بنویس",
            subtitle = "پایه ششم دبستان — نگارش",
            lessonIds = listOf(
                "elem6-negaresh-04-dastan-man-va-shoma"
            ),
            checkLessonId = "elem6-negaresh-04-dastan-man-va-shoma",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 513,
            title = "نگارش ششم درس ۵: هفت خان رستم — داستان حماسی با هفت مرحله بنویس",
            subtitle = "پایه ششم دبستان — نگارش",
            lessonIds = listOf(
                "elem6-negaresh-05-haft-khan-rostam"
            ),
            checkLessonId = "elem6-negaresh-05-haft-khan-rostam",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 514,
            title = "نگارش ششم درس ۶: ای وطن — درباره وطن با احساس بنویس",
            subtitle = "پایه ششم دبستان — نگارش",
            lessonIds = listOf(
                "elem6-negaresh-06-ey-vatan"
            ),
            checkLessonId = "elem6-negaresh-06-ey-vatan",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 515,
            title = "نگارش ششم درس ۷: درس آزاد، فرهنگ بومی ۱ — درباره فرهنگ محل خودمان بنویس",
            subtitle = "پایه ششم دبستان — نگارش",
            lessonIds = listOf(
                "elem6-negaresh-07-dars-azad-1"
            ),
            checkLessonId = "elem6-negaresh-07-dars-azad-1",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 516,
            title = "نگارش ششم درس ۸: دریاقلی — روایت یک فداکاری واقعی بنویس",
            subtitle = "پایه ششم دبستان — نگارش",
            lessonIds = listOf(
                "elem6-negaresh-08-daryagholi"
            ),
            checkLessonId = "elem6-negaresh-08-daryagholi",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 517,
            title = "نگارش ششم درس ۹: رنج‌هایی کشیده‌ام که مپرس — درباره دهخدا و تلاش برای زبان فارسی بنویس",
            subtitle = "پایه ششم دبستان — نگارش",
            lessonIds = listOf(
                "elem6-negaresh-09-ranjhaei-keshideam"
            ),
            checkLessonId = "elem6-negaresh-09-ranjhaei-keshideam",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 518,
            title = "نگارش ششم درس ۱۰: عطار و جلال‌الدین محمد — درباره دو بزرگ و حکایت درخت علم بنویس",
            subtitle = "پایه ششم دبستان — نگارش",
            lessonIds = listOf(
                "elem6-negaresh-10-attar-va-jalaleddin"
            ),
            checkLessonId = "elem6-negaresh-10-attar-va-jalaleddin",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 519,
            title = "نگارش ششم درس ۱۱: شهدا خورشیدند — با تشبیه بنویس",
            subtitle = "پایه ششم دبستان — نگارش",
            lessonIds = listOf(
                "elem6-negaresh-11-shohada-khorshidand"
            ),
            checkLessonId = "elem6-negaresh-11-shohada-khorshidand",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 520,
            title = "نگارش ششم درس ۱۲: دوستی و مشاوره — از دو متن کهن بنویس",
            subtitle = "پایه ششم دبستان — نگارش",
            lessonIds = listOf(
                "elem6-negaresh-12-dousti-va-mashverat"
            ),
            checkLessonId = "elem6-negaresh-12-dousti-va-mashverat",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 521,
            title = "نگارش ششم درس ۱۳: درس آزاد، فرهنگ بومی ۲ — گام دوم نوشتن درباره فرهنگ محل خودمان",
            subtitle = "پایه ششم دبستان — نگارش",
            lessonIds = listOf(
                "elem6-negaresh-13-dars-azad-2"
            ),
            checkLessonId = "elem6-negaresh-13-dars-azad-2",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 522,
            title = "نگارش ششم ۱۴: راز زندگی — گفت‌وگوی غنچه و گل",
            subtitle = "پایه ششم دبستان — نگارش",
            lessonIds = listOf(
                "elem6-negaresh-14-raz-zendegi"
            ),
            checkLessonId = "elem6-negaresh-14-raz-zendegi",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 523,
            title = "نگارش ششم ۱۵: میوه هنر — شعر پروین اعتصامی",
            subtitle = "پایه ششم دبستان — نگارش",
            lessonIds = listOf(
                "elem6-negaresh-15-miveh-honar"
            ),
            checkLessonId = "elem6-negaresh-15-miveh-honar",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 524,
            title = "نگارش ششم ۱۶: آداب مطالعه — چطور درست بخوانیم و بشنویم؟",
            subtitle = "پایه ششم دبستان — نگارش",
            lessonIds = listOf(
                "elem6-negaresh-16-adab-motalee"
            ),
            checkLessonId = "elem6-negaresh-16-adab-motalee",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 525,
            title = "نگارش ششم ۱۷: ستاره روشن — سفارش‌های نیک",
            subtitle = "پایه ششم دبستان — نگارش",
            lessonIds = listOf(
                "elem6-negaresh-17-setareh-roshan"
            ),
            checkLessonId = "elem6-negaresh-17-setareh-roshan",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 526,
            title = "ریاضی ششم ۱: عدد و الگوهای عددی",
            subtitle = "پایه ششم دبستان — ریاضی",
            lessonIds = listOf(
                "elem6-math-01-adad-va-olgou"
            ),
            checkLessonId = "elem6-math-01-adad-va-olgou",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 527,
            title = "ریاضی ششم ۲: کسر — جمع و تفریق و ضرب و تقسیم",
            subtitle = "پایه ششم دبستان — ریاضی",
            lessonIds = listOf(
                "elem6-math-02-kasr"
            ),
            checkLessonId = "elem6-math-02-kasr",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 528,
            title = "ریاضی ششم ۳: اعداد اعشاری — ضرب و تقسیم",
            subtitle = "پایه ششم دبستان — ریاضی",
            lessonIds = listOf(
                "elem6-math-03-adad-ashari"
            ),
            checkLessonId = "elem6-math-03-adad-ashari",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 529,
            title = "ریاضی ششم ۴: تقارن و مختصات",
            subtitle = "پایه ششم دبستان — ریاضی",
            lessonIds = listOf(
                "elem6-math-04-tagharin-va-mokhtasat"
            ),
            checkLessonId = "elem6-math-04-tagharin-va-mokhtasat",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 530,
            title = "ریاضی ششم ۵: اندازه‌گیری — طول و سطح و حجم و مساحت دایره",
            subtitle = "پایه ششم دبستان — ریاضی",
            lessonIds = listOf(
                "elem6-math-05-andazehgiri"
            ),
            checkLessonId = "elem6-math-05-andazehgiri",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 531,
            title = "ریاضی ششم ۶: تناسب و درصد",
            subtitle = "پایه ششم دبستان — ریاضی",
            lessonIds = listOf(
                "elem6-math-06-tanasob-va-darsad"
            ),
            checkLessonId = "elem6-math-06-tanasob-va-darsad",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 532,
            title = "ریاضی ششم، فصل ۷: آمار و احتمال — داده، میانگین و شانس",
            subtitle = "پایه ششم دبستان — ریاضی",
            lessonIds = listOf(
                "elem6-math-07-amar-va-ehtemal"
            ),
            checkLessonId = "elem6-math-07-amar-va-ehtemal",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 533,
            title = "علوم ششم، درس ۱: زنگ علوم — روش علمی از مشاهده تا نتیجه",
            subtitle = "پایه ششم دبستان — علوم تجربی",
            lessonIds = listOf(
                "elem6-science-01-zang-oloum"
            ),
            checkLessonId = "elem6-science-01-zang-oloum",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 534,
            title = "علوم ششم، درس ۲: سرگذشت دفتر من — از درخت تا کاغذ",
            subtitle = "پایه ششم دبستان — علوم تجربی",
            lessonIds = listOf(
                "elem6-science-02-sargozasht-daftar-man"
            ),
            checkLessonId = "elem6-science-02-sargozasht-daftar-man",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 535,
            title = "علوم ششم، درس ۳: کارخانه کاغذسازی — آهن، اسید و فلزها",
            subtitle = "پایه ششم دبستان — علوم تجربی",
            lessonIds = listOf(
                "elem6-science-03-karkhaneh-kaghazsazi"
            ),
            checkLessonId = "elem6-science-03-karkhaneh-kaghazsazi",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 536,
            title = "علوم ششم، درس ۴: سفر به اعماق زمین — لایه‌های زمین",
            subtitle = "پایه ششم دبستان — علوم تجربی",
            lessonIds = listOf(
                "elem6-science-04-safar-be-amagh-zamin"
            ),
            checkLessonId = "elem6-science-04-safar-be-amagh-zamin",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 537,
            title = "علوم ششم، درس ۵: زمین پویا — زمین‌لرزه و آتشفشان",
            subtitle = "پایه ششم دبستان — علوم تجربی",
            lessonIds = listOf(
                "elem6-science-05-zamin-pouya"
            ),
            checkLessonId = "elem6-science-05-zamin-pouya",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 538,
            title = "علوم ششم، درس ۶: ورزش و نیرو (۱) — نیرو چیست و چه می‌کند؟",
            subtitle = "پایه ششم دبستان — علوم تجربی",
            lessonIds = listOf(
                "elem6-science-06-varzesh-va-niro-1"
            ),
            checkLessonId = "elem6-science-06-varzesh-va-niro-1",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 539,
            title = "علوم ششم، درس ۷: ورزش و نیرو (۲) — گرانش، اصطکاک و انواع نیرو",
            subtitle = "پایه ششم دبستان — علوم تجربی",
            lessonIds = listOf(
                "elem6-science-07-varzesh-va-niro-2"
            ),
            checkLessonId = "elem6-science-07-varzesh-va-niro-2",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 540,
            title = "علوم ششم، درس ۸: طراحی کنیم و بسازیم — خودروی خودت را بساز",
            subtitle = "پایه ششم دبستان — علوم تجربی",
            lessonIds = listOf(
                "elem6-science-08-tarrahi-konim-va-besazim"
            ),
            checkLessonId = "elem6-science-08-tarrahi-konim-va-besazim",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 541,
            title = "علوم ششم، درس ۹: سفر انرژی — انرژی از کجا می‌آید و به کجا می‌رود؟",
            subtitle = "پایه ششم دبستان — علوم تجربی",
            lessonIds = listOf(
                "elem6-science-09-safar-energy"
            ),
            checkLessonId = "elem6-science-09-safar-energy",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 542,
            title = "علوم ششم درس ۱۰: خیلی کوچک، خیلی بزرگ — سلول و میکروسکوپ",
            subtitle = "پایه ششم دبستان — علوم تجربی",
            lessonIds = listOf(
                "elem6-science-10-kheili-kouchak-kheili-bozorg"
            ),
            checkLessonId = "elem6-science-10-kheili-kouchak-kheili-bozorg",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 543,
            title = "علوم ششم درس ۱۱: شگفتی‌های برگ — غذاسازی گیاه",
            subtitle = "پایه ششم دبستان — علوم تجربی",
            lessonIds = listOf(
                "elem6-science-11-shegeftihaye-barg"
            ),
            checkLessonId = "elem6-science-11-shegeftihaye-barg",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 544,
            title = "علوم ششم درس ۱۲: جنگل برای کیست؟ — زیستگاه و پاسداری از طبیعت",
            subtitle = "پایه ششم دبستان — علوم تجربی",
            lessonIds = listOf(
                "elem6-science-12-jangal-baraye-kist"
            ),
            checkLessonId = "elem6-science-12-jangal-baraye-kist",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 545,
            title = "علوم ششم درس ۱۳: سالم بمانیم — میکروب و دفاع بدن",
            subtitle = "پایه ششم دبستان — علوم تجربی",
            lessonIds = listOf(
                "elem6-science-13-salem-bemanim"
            ),
            checkLessonId = "elem6-science-13-salem-bemanim",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 546,
            title = "علوم ششم درس ۱۴: از گذشته تا آینده — وسایل ارتباطی",
            subtitle = "پایه ششم دبستان — علوم تجربی",
            lessonIds = listOf(
                "elem6-science-14-az-gozashteh-ta-ayandeh"
            ),
            checkLessonId = "elem6-science-14-az-gozashteh-ta-ayandeh",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 547,
            title = "مطالعات اجتماعی ششم درس ۱: دوستی — دوست خوب کیست؟",
            subtitle = "پایه ششم دبستان — مطالعات اجتماعی",
            lessonIds = listOf(
                "elem6-social_studies-01-dousti"
            ),
            checkLessonId = "elem6-social_studies-01-dousti",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 548,
            title = "مطالعات اجتماعی ششم درس ۲: آداب دوستی — با دوست چطور رفتار کنیم؟",
            subtitle = "پایه ششم دبستان — مطالعات اجتماعی",
            lessonIds = listOf(
                "elem6-social_studies-02-adab-dousti"
            ),
            checkLessonId = "elem6-social_studies-02-adab-dousti",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 549,
            title = "مطالعات اجتماعی ششم درس ۳: تصمیم‌گیری چیست؟ — انتخاب میان راه‌ها",
            subtitle = "پایه ششم دبستان — مطالعات اجتماعی",
            lessonIds = listOf(
                "elem6-social_studies-03-tasmimgiri-chist"
            ),
            checkLessonId = "elem6-social_studies-03-tasmimgiri-chist",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 550,
            title = "مطالعات اجتماعی ششم درس ۴: چگونه تصمیم بگیریم؟ — مراحل تصمیم خوب",
            subtitle = "پایه ششم دبستان — مطالعات اجتماعی",
            lessonIds = listOf(
                "elem6-social_studies-04-chegouneh-tasmim-begirim"
            ),
            checkLessonId = "elem6-social_studies-04-chegouneh-tasmim-begirim",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 551,
            title = "مطالعات اجتماعی ششم درس ۵: عوامل مؤثر در کشاورزی — چه چیزی محصول را زیاد می‌کند؟",
            subtitle = "پایه ششم دبستان — مطالعات اجتماعی",
            lessonIds = listOf(
                "elem6-social_studies-05-avamel-keshavarzi"
            ),
            checkLessonId = "elem6-social_studies-05-avamel-keshavarzi",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 552,
            title = "مطالعات ششم ۶: محصولات کشاورزی از تولید تا مصرف",
            subtitle = "پایه ششم دبستان — مطالعات اجتماعی",
            lessonIds = listOf(
                "elem6-social_studies-06-mahsoolat-keshavarzi"
            ),
            checkLessonId = "elem6-social_studies-06-mahsoolat-keshavarzi",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 553,
            title = "مطالعات ششم ۷: طلای سیاه",
            subtitle = "پایه ششم دبستان — مطالعات اجتماعی",
            lessonIds = listOf(
                "elem6-social_studies-07-talay-siah"
            ),
            checkLessonId = "elem6-social_studies-07-talay-siah",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 554,
            title = "مطالعات ششم ۸: انرژی را بهتر مصرف کنیم",
            subtitle = "پایه ششم دبستان — مطالعات اجتماعی",
            lessonIds = listOf(
                "elem6-social_studies-08-energy-behtar"
            ),
            checkLessonId = "elem6-social_studies-08-energy-behtar",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 555,
            title = "مطالعات ششم ۹: پیشرفت‌های علمی مسلمانان",
            subtitle = "پایه ششم دبستان — مطالعات اجتماعی",
            lessonIds = listOf(
                "elem6-social_studies-09-pishraft-elmi-mosalamanan"
            ),
            checkLessonId = "elem6-social_studies-09-pishraft-elmi-mosalamanan",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 556,
            title = "مطالعات ششم ۱۰: چه عواملی موجب گسترش علوم و فنون در دوره اسلامی شد؟",
            subtitle = "پایه ششم دبستان — مطالعات اجتماعی",
            lessonIds = listOf(
                "elem6-social_studies-10-avamel-gostaresh-oloum"
            ),
            checkLessonId = "elem6-social_studies-10-avamel-gostaresh-oloum",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 557,
            title = "مطالعات ششم ۱۱: اصفهان؛ نصف جهان",
            subtitle = "پایه ششم دبستان — مطالعات اجتماعی",
            lessonIds = listOf(
                "elem6-social_studies-11-esfahan-nesf-jahan"
            ),
            checkLessonId = "elem6-social_studies-11-esfahan-nesf-jahan",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 558,
            title = "مطالعات ششم ۱۲: چرا فرهنگ و هنر در دوره صفویه شکوفا شد؟",
            subtitle = "پایه ششم دبستان — مطالعات اجتماعی",
            lessonIds = listOf(
                "elem6-social_studies-12-farhang-honar-safavieh"
            ),
            checkLessonId = "elem6-social_studies-12-farhang-honar-safavieh",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 559,
            title = "مطالعات ششم ۱۳: برنامه روزانه متعادل",
            subtitle = "پایه ششم دبستان — مطالعات اجتماعی",
            lessonIds = listOf(
                "elem6-social_studies-13-barnameh-rouzaneh"
            ),
            checkLessonId = "elem6-social_studies-13-barnameh-rouzaneh",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 560,
            title = "مطالعات ششم ۱۴: برنامه‌ریزی برای اوقات فراغت",
            subtitle = "پایه ششم دبستان — مطالعات اجتماعی",
            lessonIds = listOf(
                "elem6-social_studies-14-barnamehrizi-oghat-faraghat"
            ),
            checkLessonId = "elem6-social_studies-14-barnamehrizi-oghat-faraghat",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 561,
            title = "مطالعات ششم ۱۵: انواع لباس",
            subtitle = "پایه ششم دبستان — مطالعات اجتماعی",
            lessonIds = listOf(
                "elem6-social_studies-15-anva-lebas"
            ),
            checkLessonId = "elem6-social_studies-15-anva-lebas",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 562,
            title = "مطالعات اجتماعی ششم ۱۶: لباس از تولید تا مصرف — از صفر",
            subtitle = "پایه ششم دبستان — مطالعات اجتماعی",
            lessonIds = listOf(
                "elem6-social_studies-16-lebas-az-tolid-ta-masraf"
            ),
            checkLessonId = "elem6-social_studies-16-lebas-az-tolid-ta-masraf",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 563,
            title = "مطالعات اجتماعی ششم ۱۷: ویژگی‌های دریاهای ایران — از صفر",
            subtitle = "پایه ششم دبستان — مطالعات اجتماعی",
            lessonIds = listOf(
                "elem6-social_studies-17-vizhegihaye-daryahaye-iran"
            ),
            checkLessonId = "elem6-social_studies-17-vizhegihaye-daryahaye-iran",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 564,
            title = "مطالعات اجتماعی ششم ۱۸: دریا، نعمت خداوندی — از صفر",
            subtitle = "پایه ششم دبستان — مطالعات اجتماعی",
            lessonIds = listOf(
                "elem6-social_studies-18-darya-nemat-khodavandi"
            ),
            checkLessonId = "elem6-social_studies-18-darya-nemat-khodavandi",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 565,
            title = "مطالعات اجتماعی ششم ۱۹: همسایگان ما — از صفر",
            subtitle = "پایه ششم دبستان — مطالعات اجتماعی",
            lessonIds = listOf(
                "elem6-social_studies-19-hamsayegan-ma"
            ),
            checkLessonId = "elem6-social_studies-19-hamsayegan-ma",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 566,
            title = "مطالعات اجتماعی ششم ۲۰: مطالعه موردی — از صفر",
            subtitle = "پایه ششم دبستان — مطالعات اجتماعی",
            lessonIds = listOf(
                "elem6-social_studies-20-motalee-moredi"
            ),
            checkLessonId = "elem6-social_studies-20-motalee-moredi",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 567,
            title = "مطالعات اجتماعی ششم ۲۱: استعمار چیست؟ — از صفر",
            subtitle = "پایه ششم دبستان — مطالعات اجتماعی",
            lessonIds = listOf(
                "elem6-social_studies-21-esteamar-chist"
            ),
            checkLessonId = "elem6-social_studies-21-esteamar-chist",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 568,
            title = "مطالعات اجتماعی ششم ۲۲: مبارزه مردم ایران با استعمار — از صفر",
            subtitle = "پایه ششم دبستان — مطالعات اجتماعی",
            lessonIds = listOf(
                "elem6-social_studies-22-mobarezeh-mardom-ba-esteamar"
            ),
            checkLessonId = "elem6-social_studies-22-mobarezeh-mardom-ba-esteamar",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 569,
            title = "مطالعات اجتماعی ششم ۲۳: خرمشهر در چنگال دشمن — از صفر",
            subtitle = "پایه ششم دبستان — مطالعات اجتماعی",
            lessonIds = listOf(
                "elem6-social_studies-23-khorramshahr-dar-chang-doshman"
            ),
            checkLessonId = "elem6-social_studies-23-khorramshahr-dar-chang-doshman",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 570,
            title = "مطالعات اجتماعی ششم ۲۴: خرمشهر در دامان میهن — از صفر",
            subtitle = "پایه ششم دبستان — مطالعات اجتماعی",
            lessonIds = listOf(
                "elem6-social_studies-24-khorramshahr-dar-daman-mihan"
            ),
            checkLessonId = "elem6-social_studies-24-khorramshahr-dar-daman-mihan",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 571,
            title = "هدیه‌های آسمانی ششم ۱: یکتا — از صفر",
            subtitle = "پایه ششم دبستان — هدیه‌های آسمانی",
            lessonIds = listOf(
                "elem6-hedyeh-01-yekta"
            ),
            checkLessonId = "elem6-hedyeh-01-yekta",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 572,
            title = "هدیه ششم درس ۲: بهترین راهنمایان — پیامبران و امامان",
            subtitle = "پایه ششم دبستان — هدیه‌های آسمانی",
            lessonIds = listOf(
                "elem6-hedyeh-02-behtarin-rahnamayan"
            ),
            checkLessonId = "elem6-hedyeh-02-behtarin-rahnamayan",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 573,
            title = "هدیه ششم درس ۳: سرور آزادگان — امام حسین و قیام عاشورا",
            subtitle = "پایه ششم دبستان — هدیه‌های آسمانی",
            lessonIds = listOf(
                "elem6-hedyeh-03-sarvar-azadegan"
            ),
            checkLessonId = "elem6-hedyeh-03-sarvar-azadegan",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 574,
            title = "هدیه ششم درس ۴: باغ سری — امر به معروف و نهی از منکر",
            subtitle = "پایه ششم دبستان — هدیه‌های آسمانی",
            lessonIds = listOf(
                "elem6-hedyeh-04-bagh-serri"
            ),
            checkLessonId = "elem6-hedyeh-04-bagh-serri",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 575,
            title = "هدیه ششم درس ۵: شتربان با ایمان — تولی و تبری",
            subtitle = "پایه ششم دبستان — هدیه‌های آسمانی",
            lessonIds = listOf(
                "elem6-hedyeh-05-shotarban-ba-iman"
            ),
            checkLessonId = "elem6-hedyeh-05-shotarban-ba-iman",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 576,
            title = "هدیه ششم درس ۶: مثل فرات و کارون — پیاده‌روی اربعین",
            subtitle = "پایه ششم دبستان — هدیه‌های آسمانی",
            lessonIds = listOf(
                "elem6-hedyeh-06-mesl-forat-va-karun"
            ),
            checkLessonId = "elem6-hedyeh-06-mesl-forat-va-karun",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 577,
            title = "هدیه ششم درس ۷: سیمای خوبان — اهل بیت، الگوی ما",
            subtitle = "پایه ششم دبستان — هدیه‌های آسمانی",
            lessonIds = listOf(
                "elem6-hedyeh-07-simaye-khouban"
            ),
            checkLessonId = "elem6-hedyeh-07-simaye-khouban",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 578,
            title = "هدیه ششم درس ۸: دست در دست دوست — دیدار علامه حلی با امام زمان",
            subtitle = "پایه ششم دبستان — هدیه‌های آسمانی",
            lessonIds = listOf(
                "elem6-hedyeh-08-dast-dar-dast-doust"
            ),
            checkLessonId = "elem6-hedyeh-08-dast-dar-dast-doust",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 579,
            title = "هدیه ششم درس ۹: دوران غیبت — امام مهدی و غیبت صغری و کبری",
            subtitle = "پایه ششم دبستان — هدیه‌های آسمانی",
            lessonIds = listOf(
                "elem6-hedyeh-09-douran-gheibat"
            ),
            checkLessonId = "elem6-hedyeh-09-douran-gheibat",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 580,
            title = "هدیه ششم درس ۱۰: جهان دیگر — معاد و زندگی پس از مرگ",
            subtitle = "پایه ششم دبستان — هدیه‌های آسمانی",
            lessonIds = listOf(
                "elem6-hedyeh-10-jahan-digar"
            ),
            checkLessonId = "elem6-hedyeh-10-jahan-digar",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 581,
            title = "هدیه ششم درس ۱۱: آداب زندگی — آداب معاشرت و گفت‌وگو",
            subtitle = "پایه ششم دبستان — هدیه‌های آسمانی",
            lessonIds = listOf(
                "elem6-hedyeh-11-adab-zendegi"
            ),
            checkLessonId = "elem6-hedyeh-11-adab-zendegi",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 582,
            title = "هدیه ششم درس ۱۲: زنگ میناب — از صفر",
            subtitle = "پایه ششم دبستان — هدیه‌های آسمانی",
            lessonIds = listOf(
                "elem6-hedyeh-12-danesh-amouz-nemouneh"
            ),
            checkLessonId = "elem6-hedyeh-12-danesh-amouz-nemouneh",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 583,
            title = "هدیه ششم درس ۱۳: سفرهای با برکت — از صفر",
            subtitle = "پایه ششم دبستان — هدیه‌های آسمانی",
            lessonIds = listOf(
                "elem6-hedyeh-13-safarhaye-ba-barakat"
            ),
            checkLessonId = "elem6-hedyeh-13-safarhaye-ba-barakat",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 584,
            title = "هدیه ششم درس ۱۴: عید مسلمانان — از صفر",
            subtitle = "پایه ششم دبستان — هدیه‌های آسمانی",
            lessonIds = listOf(
                "elem6-hedyeh-14-eid-mosalamanan"
            ),
            checkLessonId = "elem6-hedyeh-14-eid-mosalamanan",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 585,
            title = "هدیه ششم درس ۱۵: راز موفقیت — از صفر",
            subtitle = "پایه ششم دبستان — هدیه‌های آسمانی",
            lessonIds = listOf(
                "elem6-hedyeh-15-raz-movafaghiat"
            ),
            checkLessonId = "elem6-hedyeh-15-raz-movafaghiat",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 586,
            title = "هدیه ششم درس ۱۶: حماسه‌آفرینان جاودان — از صفر",
            subtitle = "پایه ششم دبستان — هدیه‌های آسمانی",
            lessonIds = listOf(
                "elem6-hedyeh-16-hamaseh-afarinan-javdan"
            ),
            checkLessonId = "elem6-hedyeh-16-hamaseh-afarinan-javdan",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 587,
            title = "هدیه ششم درس ۱۷: زیارت — از صفر",
            subtitle = "پایه ششم دبستان — هدیه‌های آسمانی",
            lessonIds = listOf(
                "elem6-hedyeh-17-ziyarat"
            ),
            checkLessonId = "elem6-hedyeh-17-ziyarat",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 588,
            title = "قرآن ششم درس ۱: نماز در قرآن — از صفر",
            subtitle = "پایه ششم دبستان — قرآن",
            lessonIds = listOf(
                "elem6-quran-01-namaz-dar-quran"
            ),
            checkLessonId = "elem6-quran-01-namaz-dar-quran",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 589,
            title = "قرآن ششم درس ۲: سوره فتح — از صفر",
            subtitle = "پایه ششم دبستان — قرآن",
            lessonIds = listOf(
                "elem6-quran-02-sureh-fath"
            ),
            checkLessonId = "elem6-quran-02-sureh-fath",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 590,
            title = "قرآن ششم درس ۳: سوره حجرات ۱ — از صفر",
            subtitle = "پایه ششم دبستان — قرآن",
            lessonIds = listOf(
                "elem6-quran-03-sureh-hojorat-1"
            ),
            checkLessonId = "elem6-quran-03-sureh-hojorat-1",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 591,
            title = "قرآن ششم درس ۴: سوره حجرات ۲ و سوره ق — از صفر",
            subtitle = "پایه ششم دبستان — قرآن",
            lessonIds = listOf(
                "elem6-quran-04-sureh-hojorat-2"
            ),
            checkLessonId = "elem6-quran-04-sureh-hojorat-2",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 592,
            title = "قرآن ششم ۵: سوره ق ۳۶ تا ۴۵ و سوره ذاریات ۱ تا ۲۳ — گسترش آسمان",
            subtitle = "پایه ششم دبستان — قرآن",
            lessonIds = listOf(
                "elem6-quran-05-sureh-qaf-zariyat-1"
            ),
            checkLessonId = "elem6-quran-05-sureh-qaf-zariyat-1",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 593,
            title = "قرآن ششم ۶: سوره ذاریات ۴۷ تا ۶۰ و سوره طور ۱ تا ۲۱",
            subtitle = "پایه ششم دبستان — قرآن",
            lessonIds = listOf(
                "elem6-quran-06-sureh-zariyat-tour"
            ),
            checkLessonId = "elem6-quran-06-sureh-zariyat-tour",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 594,
            title = "قرآن ششم ۷: سوره قمر ۱ تا ۳۲ — شق‌القمر و نظم در آفرینش",
            subtitle = "پایه ششم دبستان — قرآن",
            lessonIds = listOf(
                "elem6-quran-07-sureh-qamar-shaqq-al-qamar"
            ),
            checkLessonId = "elem6-quran-07-sureh-qamar-shaqq-al-qamar",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 595,
            title = "قرآن ششم ۸: سوره الرحمن ۱۷ تا ۶۵ — مردان پارسی",
            subtitle = "پایه ششم دبستان — قرآن",
            lessonIds = listOf(
                "elem6-quran-08-sureh-rahman-mardan-parsi"
            ),
            checkLessonId = "elem6-quran-08-sureh-rahman-mardan-parsi",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 596,
            title = "قرآن ششم ۹: سوره واقعه ۱ تا ۲۶ و ۵۷ تا ۷۴ — آب و آبادانی",
            subtitle = "پایه ششم دبستان — قرآن",
            lessonIds = listOf(
                "elem6-quran-09-sureh-vaqeah-ab-va-abadani"
            ),
            checkLessonId = "elem6-quran-09-sureh-vaqeah-ab-va-abadani",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 597,
            title = "قرآن ششم ۱۰: سوره حدید ۱ تا ۱۷ — تولدی دوباره",
            subtitle = "پایه ششم دبستان — قرآن",
            lessonIds = listOf(
                "elem6-quran-10-sureh-hadid-tavallodi-dobareh"
            ),
            checkLessonId = "elem6-quran-10-sureh-hadid-tavallodi-dobareh",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 598,
            title = "قرآن ششم ۱۱: سوره حدید ۲۵ تا ۲۹ و سوره مجادله ۱ تا ۵ — مادر فلزات",
            subtitle = "پایه ششم دبستان — قرآن",
            lessonIds = listOf(
                "elem6-quran-11-sureh-hadid-mojadeleh-madar-felezat"
            ),
            checkLessonId = "elem6-quran-11-sureh-hadid-mojadeleh-madar-felezat",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 599,
            title = "قرآن ششم ۱۲: سوره مجادله ۱۱ تا ۲۲ — حزب‌الله پیروز است",
            subtitle = "پایه ششم دبستان — قرآن",
            lessonIds = listOf(
                "elem6-quran-12-sureh-mojadeleh-hezbollah-pirouz-ast"
            ),
            checkLessonId = "elem6-quran-12-sureh-mojadeleh-hezbollah-pirouz-ast",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 600,
            title = "قرآن ششم ۱۳: سوره حشر ۱۸ تا ۲۴ و سوره ممتحنه ۱ تا ۴ — خدای تصویرگر",
            subtitle = "پایه ششم دبستان — قرآن",
            lessonIds = listOf(
                "elem6-quran-13-sureh-hashr-momtaheneh-khodaya-tasvirgar"
            ),
            checkLessonId = "elem6-quran-13-sureh-hashr-momtaheneh-khodaya-tasvirgar",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 601,
            title = "قرآن ششم ۱۴: سوره صف ۱ تا ۱۴ — همه تسبیح‌گوی او",
            subtitle = "پایه ششم دبستان — قرآن",
            lessonIds = listOf(
                "elem6-quran-14-sureh-saff-hame-tasbih-gouye-ou"
            ),
            checkLessonId = "elem6-quran-14-sureh-saff-hame-tasbih-gouye-ou",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 602,
            title = "قرآن ششم ۱۵: قرآن در تابستان — سوره‌های جمعه تا قلم و آیه‌الکرسی",
            subtitle = "پایه ششم دبستان — قرآن",
            lessonIds = listOf(
                "elem6-quran-15-quran-dar-tabestan"
            ),
            checkLessonId = "elem6-quran-15-quran-dar-tabestan",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 603,
            title = "تفکر و پژوهش ششم ۰: واژه‌های تصادفی — پیشگفتار کتاب",
            subtitle = "پایه ششم دبستان — تفکر و سبک زندگی",
            lessonIds = listOf(
                "elem6-tafakkor-00-vajehaye-tasadifi"
            ),
            checkLessonId = "elem6-tafakkor-00-vajehaye-tasadifi",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 604,
            title = "تفکر و پژوهش ششم ۱: دیدنی‌های سرزمین من — فصل اول: انتخاب و تصمیم‌گیری",
            subtitle = "پایه ششم دبستان — تفکر و سبک زندگی",
            lessonIds = listOf(
                "elem6-tafakkor-01-didani-haye-sarzamin-man"
            ),
            checkLessonId = "elem6-tafakkor-01-didani-haye-sarzamin-man",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 605,
            title = "تفکر و پژوهش ششم ۲: مارگیر و اژدها — فصل اول: انتخاب و تصمیم‌گیری",
            subtitle = "پایه ششم دبستان — تفکر و سبک زندگی",
            lessonIds = listOf(
                "elem6-tafakkor-02-margir-va-azhdaha"
            ),
            checkLessonId = "elem6-tafakkor-02-margir-va-azhdaha",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 606,
            title = "تفکر و پژوهش ششم ۳: بوی خوش مدینه — فصل اول: انتخاب و تصمیم‌گیری",
            subtitle = "پایه ششم دبستان — تفکر و سبک زندگی",
            lessonIds = listOf(
                "elem6-tafakkor-03-booye-khosh-madine"
            ),
            checkLessonId = "elem6-tafakkor-03-booye-khosh-madine",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 607,
            title = "تفکر و پژوهش ششم ۴: آقکند — فصل اول: انتخاب و تصمیم‌گیری",
            subtitle = "پایه ششم دبستان — تفکر و سبک زندگی",
            lessonIds = listOf(
                "elem6-tafakkor-04-aghakand"
            ),
            checkLessonId = "elem6-tafakkor-04-aghakand",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 608,
            title = "تفکر و پژوهش ششم ۵: ماه بود و روباه! — فصل اول: انتخاب و تصمیم‌گیری",
            subtitle = "پایه ششم دبستان — تفکر و سبک زندگی",
            lessonIds = listOf(
                "elem6-tafakkor-05-mah-boud-va-roubah"
            ),
            checkLessonId = "elem6-tafakkor-05-mah-boud-va-roubah",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 609,
            title = "تفکر و پژوهش ششم ۶: نارنجی‌پوش امانتدار — فصل اول: انتخاب و تصمیم‌گیری (انتخابی)",
            subtitle = "پایه ششم دبستان — تفکر و سبک زندگی",
            lessonIds = listOf(
                "elem6-tafakkor-06-narenji-poosh-amanatdar"
            ),
            checkLessonId = "elem6-tafakkor-06-narenji-poosh-amanatdar",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 610,
            title = "تفکر و پژوهش ششم ۷: روش گردآوری اطلاعات — فصل دوم: پروژه پژوهشی",
            subtitle = "پایه ششم دبستان — تفکر و سبک زندگی",
            lessonIds = listOf(
                "elem6-tafakkor-07-ravesh-gardavari-ettelaat"
            ),
            checkLessonId = "elem6-tafakkor-07-ravesh-gardavari-ettelaat",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 611,
            title = "تفکر و پژوهش ششم ۸: طرح پرسش پژوهشی — فصل دوم: پروژه پژوهشی",
            subtitle = "پایه ششم دبستان — تفکر و سبک زندگی",
            lessonIds = listOf(
                "elem6-tafakkor-08-tarh-porsesh-pazhuheshi"
            ),
            checkLessonId = "elem6-tafakkor-08-tarh-porsesh-pazhuheshi",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 612,
            title = "تفکر و پژوهش ششم ۹: مردم‌پسندها — فصل دوم: پروژه پژوهشی",
            subtitle = "پایه ششم دبستان — تفکر و سبک زندگی",
            lessonIds = listOf(
                "elem6-tafakkor-09-mardom-pasandha"
            ),
            checkLessonId = "elem6-tafakkor-09-mardom-pasandha",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 613,
            title = "تفکر و پژوهش ششم ۱۰: زیر ذره‌بین — فصل دوم: پروژه پژوهشی (انتخابی)",
            subtitle = "پایه ششم دبستان — تفکر و سبک زندگی",
            lessonIds = listOf(
                "elem6-tafakkor-10-zir-zarreh-bin"
            ),
            checkLessonId = "elem6-tafakkor-10-zir-zarreh-bin",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 614,
            title = "تفکر و پژوهش ششم ۱۱: از کجا بدانم؟ — فصل دوم: پروژه پژوهشی (انتخابی)",
            subtitle = "پایه ششم دبستان — تفکر و سبک زندگی",
            lessonIds = listOf(
                "elem6-tafakkor-11-az-koja-bedanam"
            ),
            checkLessonId = "elem6-tafakkor-11-az-koja-bedanam",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 615,
            title = "تفکر و پژوهش ششم ۱۲: ارائه یافته‌های پژوهشی — فصل دوم: پروژه پژوهشی",
            subtitle = "پایه ششم دبستان — تفکر و سبک زندگی",
            lessonIds = listOf(
                "elem6-tafakkor-12-erae-ye-yafte-haye-pazhuheshi"
            ),
            checkLessonId = "elem6-tafakkor-12-erae-ye-yafte-haye-pazhuheshi",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 616,
            title = "تفکر و پژوهش ششم ۱۳: لی‌لی حوضک — فصل سوم: نظام‌مندی",
            subtitle = "پایه ششم دبستان — تفکر و سبک زندگی",
            lessonIds = listOf(
                "elem6-tafakkor-13-lili-hozak"
            ),
            checkLessonId = "elem6-tafakkor-13-lili-hozak",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 617,
            title = "تفکر و پژوهش ششم ۱۴: آخر خط — فصل سوم: نظام‌مندی",
            subtitle = "پایه ششم دبستان — تفکر و سبک زندگی",
            lessonIds = listOf(
                "elem6-tafakkor-14-akhar-khat"
            ),
            checkLessonId = "elem6-tafakkor-14-akhar-khat",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 618,
            title = "تفکر و پژوهش ششم ۱۵: ما کجا هستیم؟ — فصل سوم: نظام‌مندی",
            subtitle = "پایه ششم دبستان — تفکر و سبک زندگی",
            lessonIds = listOf(
                "elem6-tafakkor-15-ma-koja-hastim"
            ),
            checkLessonId = "elem6-tafakkor-15-ma-koja-hastim",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 619,
            title = "تفکر و پژوهش ششم ۱۶: یک اتفاق عجیب — فصل سوم: نظام‌مندی (انتخابی)",
            subtitle = "پایه ششم دبستان — تفکر و سبک زندگی",
            lessonIds = listOf(
                "elem6-tafakkor-16-yek-ettefagh-ajib"
            ),
            checkLessonId = "elem6-tafakkor-16-yek-ettefagh-ajib",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 620,
            title = "تفکر و پژوهش ششم ۱۷: پیوستگی — فصل سوم: نظام‌مندی (انتخابی)",
            subtitle = "پایه ششم دبستان — تفکر و سبک زندگی",
            lessonIds = listOf(
                "elem6-tafakkor-17-peyvastegi"
            ),
            checkLessonId = "elem6-tafakkor-17-peyvastegi",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 621,
            title = "تفکر و پژوهش ششم ۱۸: پلنگ‌ها و گوزن‌ها — فصل سوم: نظام‌مندی (انتخابی)",
            subtitle = "پایه ششم دبستان — تفکر و سبک زندگی",
            lessonIds = listOf(
                "elem6-tafakkor-18-palangha-va-gavaznha"
            ),
            checkLessonId = "elem6-tafakkor-18-palangha-va-gavaznha",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 622,
            title = "تفکر و پژوهش ششم ۱۹: تپه کوچک — فصل چهارم: هویت و ارزش",
            subtitle = "پایه ششم دبستان — تفکر و سبک زندگی",
            lessonIds = listOf(
                "elem6-tafakkor-19-tappe-ye-kochak"
            ),
            checkLessonId = "elem6-tafakkor-19-tappe-ye-kochak",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 623,
            title = "تفکر و پژوهش ششم ۲۰: میمون‌های گران‌بها — فصل چهارم: هویت و ارزش",
            subtitle = "پایه ششم دبستان — تفکر و سبک زندگی",
            lessonIds = listOf(
                "elem6-tafakkor-20-meymun-haye-geran-baha"
            ),
            checkLessonId = "elem6-tafakkor-20-meymun-haye-geran-baha",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 624,
            title = "تفکر و پژوهش ششم ۲۱: شکلی دیگر — فصل چهارم: هویت و ارزش",
            subtitle = "پایه ششم دبستان — تفکر و سبک زندگی",
            lessonIds = listOf(
                "elem6-tafakkor-21-shekli-digar"
            ),
            checkLessonId = "elem6-tafakkor-21-shekli-digar",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 625,
            title = "تفکر و پژوهش ششم ۲۲: خرسی که می‌خواست خرس باقی بماند — فصل چهارم: هویت و ارزش (انتخابی)",
            subtitle = "پایه ششم دبستان — تفکر و سبک زندگی",
            lessonIds = listOf(
                "elem6-tafakkor-22-khersi-ke-mikhast-khers-baghi-bemanad"
            ),
            checkLessonId = "elem6-tafakkor-22-khersi-ke-mikhast-khers-baghi-bemanad",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 626,
            title = "تفکر و پژوهش ششم ۲۳: پرواز کن! پرواز! — فصل چهارم: هویت و ارزش (انتخابی)",
            subtitle = "پایه ششم دبستان — تفکر و سبک زندگی",
            lessonIds = listOf(
                "elem6-tafakkor-23-parvaz-kon-parvaz"
            ),
            checkLessonId = "elem6-tafakkor-23-parvaz-kon-parvaz",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 627,
            title = "تفکر و پژوهش ششم ۲۴: من عاقبت از این پل خواهم گذشت — فصل چهارم: هویت و ارزش (انتخابی)",
            subtitle = "پایه ششم دبستان — تفکر و سبک زندگی",
            lessonIds = listOf(
                "elem6-tafakkor-24-man-aghebat-az-in-pol-khaham-gozasht"
            ),
            checkLessonId = "elem6-tafakkor-24-man-aghebat-az-in-pol-khaham-gozasht",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 628,
            title = "تفکر و پژوهش ششم ۲۵: کیمیاگر — فصل چهارم: هویت و ارزش (انتخابی)",
            subtitle = "پایه ششم دبستان — تفکر و سبک زندگی",
            lessonIds = listOf(
                "elem6-tafakkor-25-kimiyagar"
            ),
            checkLessonId = "elem6-tafakkor-25-kimiyagar",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
    )
}
