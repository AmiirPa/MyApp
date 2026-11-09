package com.example.data.curriculum

// مسیر متوسطه اول: پایه ۷ تا ۹، به ترتیب کتاب.
// هر قدم = یک درس کتاب، به ترتیب پایه و بعد ترتیب کتاب: اول این را بخوان،
// بعد آن را. قدم به قدم، پله به پله. درس‌های تمرین کتاب (practice) قدم
// جدا نیستند — تمرین هر درس، داخل همان درس و در فهرست موضوعی است.
// همه شناسه‌ها از درس‌های واقعاً تعریف‌شده گرفته شده‌اند؛ قدمی با درس
// ساخته‌نشده وجود ندارد — اگر درسی هنوز ساخته نشده باشد، به‌صورت GAP
// با یادداشت «هنوز ساخته نشده» می‌آید، نه خالی.
// مسیر ۱ تا ۱۰۰ (متوسطه دوم + فرهنگیان) در LearningPath1406.kt است و
// دست نخورده است؛ این مسیر کنار آن است، نه به‌جای آن.

object LearningPathMiddle1406 {
    val steps: List<LearningPathStep> = listOf(
        LearningPathStep(
            stepNumber = 1,
            title = "فارسی هفتم ۱: زنگ آفرینش — شعر قیصر امین‌پور",
            subtitle = "پایه هفتم متوسطه اول — فارسی",
            lessonIds = listOf(
                "mid7-farsi-01-zang-afarinesh"
            ),
            checkLessonId = "mid7-farsi-01-zang-afarinesh",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 2,
            title = "فارسی هفتم ۲: چشمه معرفت — شناخت و آگاهی",
            subtitle = "پایه هفتم متوسطه اول — فارسی",
            lessonIds = listOf(
                "mid7-farsi-02-cheshme-marefat"
            ),
            checkLessonId = "mid7-farsi-02-cheshme-marefat",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 3,
            title = "فارسی هفتم ۳: نسل آینده‌ساز — نوجوانان، آینده ایران",
            subtitle = "پایه هفتم متوسطه اول — فارسی",
            lessonIds = listOf(
                "mid7-farsi-03-nasl-ayande-saz"
            ),
            checkLessonId = "mid7-farsi-03-nasl-ayande-saz",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 4,
            title = "فارسی هفتم ۴: با بهاری که می‌رسد از راه — زیبایی شکفتن",
            subtitle = "پایه هفتم متوسطه اول — فارسی",
            lessonIds = listOf(
                "mid7-farsi-04-bahar-mi-resad"
            ),
            checkLessonId = "mid7-farsi-04-bahar-mi-resad",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 5,
            title = "فارسی هفتم ۵: درس آزاد — روش خواندن و فهم متن آزاد",
            subtitle = "پایه هفتم متوسطه اول — فارسی",
            lessonIds = listOf(
                "mid7-farsi-05-dars-azad"
            ),
            checkLessonId = "mid7-farsi-05-dars-azad",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 6,
            title = "فارسی هفتم ۶: قلب کوچکم را به چه کسی بدهم؟ — نادر ابراهیمی",
            subtitle = "پایه هفتم متوسطه اول — فارسی",
            lessonIds = listOf(
                "mid7-farsi-06-ghalb-kochak"
            ),
            checkLessonId = "mid7-farsi-06-ghalb-kochak",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 7,
            title = "فارسی هفتم ۷: علم زندگانی — دانش، راه زندگی است",
            subtitle = "پایه هفتم متوسطه اول — فارسی",
            lessonIds = listOf(
                "mid7-farsi-07-elm-zendegani"
            ),
            checkLessonId = "mid7-farsi-07-elm-zendegani",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 8,
            title = "فارسی هفتم ۸: زندگی همین لحظه‌هاست — قدر اکنون را بدان",
            subtitle = "پایه هفتم متوسطه اول — فارسی",
            lessonIds = listOf(
                "mid7-farsi-08-zendegi-lahzeha"
            ),
            checkLessonId = "mid7-farsi-08-zendegi-lahzeha",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 9,
            title = "فارسی هفتم ۹: نصیحت امام — شوق خواندن",
            subtitle = "پایه هفتم متوسطه اول — فارسی",
            lessonIds = listOf(
                "mid7-farsi-09-nasihat-emam"
            ),
            checkLessonId = "mid7-farsi-09-nasihat-emam",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 10,
            title = "فارسی هفتم ۱۰: کلاس ادبیات — مرواریدی در صدف، زندگی حسابی، فرزند صالح انقلاب",
            subtitle = "پایه هفتم متوسطه اول — فارسی",
            lessonIds = listOf(
                "mid7-farsi-10-kelas-adabiyat"
            ),
            checkLessonId = "mid7-farsi-10-kelas-adabiyat",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 11,
            title = "فارسی هفتم ۱۱: عهد و پیمان — قول یعنی قول",
            subtitle = "پایه هفتم متوسطه اول — فارسی",
            lessonIds = listOf(
                "mid7-farsi-11-ahd-peyman"
            ),
            checkLessonId = "mid7-farsi-11-ahd-peyman",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 12,
            title = "فارسی هفتم ۱۲: خدمات متقابل اسلام و ایران — دوستی دوطرفه",
            subtitle = "پایه هفتم متوسطه اول — فارسی",
            lessonIds = listOf(
                "mid7-farsi-12-khadamat-moteghabel"
            ),
            checkLessonId = "mid7-farsi-12-khadamat-moteghabel",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 13,
            title = "فارسی هفتم ۱۳: اسوه نیکو — پیامبر، بهترین الگو",
            subtitle = "پایه هفتم متوسطه اول — فارسی",
            lessonIds = listOf(
                "mid7-farsi-13-osveh-nikou"
            ),
            checkLessonId = "mid7-farsi-13-osveh-nikou",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 14,
            title = "فارسی هفتم ۱۴: امام خمینی — زندگی رهبر انقلاب",
            subtitle = "پایه هفتم متوسطه اول — فارسی",
            lessonIds = listOf(
                "mid7-farsi-14-imam-khomeini"
            ),
            checkLessonId = "mid7-farsi-14-imam-khomeini",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 15,
            title = "فارسی هفتم ۱۵: درس آزاد — چطور هر متن آزادی را بخوانیم و بفهمیم؟",
            subtitle = "پایه هفتم متوسطه اول — فارسی",
            lessonIds = listOf(
                "mid7-farsi-15-dars-azad"
            ),
            checkLessonId = "mid7-farsi-15-dars-azad",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 16,
            title = "فارسی هفتم ۱۶: آدم آهنی و شاپرک — وقتی یک ربات احساس پیدا می‌کند",
            subtitle = "پایه هفتم متوسطه اول — فارسی",
            lessonIds = listOf(
                "mid7-farsi-16-adam-ahani-shaparak"
            ),
            checkLessonId = "mid7-farsi-16-adam-ahani-shaparak",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 17,
            title = "فارسی هفتم ۱۷: ما می‌توانیم — خاکسپاری نمی‌توانم",
            subtitle = "پایه هفتم متوسطه اول — فارسی",
            lessonIds = listOf(
                "mid7-farsi-17-ma-mitavanim"
            ),
            checkLessonId = "mid7-farsi-17-ma-mitavanim",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 18,
            title = "نگارش هفتم ۱: نقشه نوشتن — پیش از نوشتن، نقشه بکش",
            subtitle = "پایه هفتم متوسطه اول — نگارش",
            lessonIds = listOf(
                "mid7-negaresh-01-naghshe-neveshtan"
            ),
            checkLessonId = "mid7-negaresh-01-naghshe-neveshtan",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 19,
            title = "نگارش هفتم ۲: بخش‌های نوشته — مقدمه، بدنه، پایان",
            subtitle = "پایه هفتم متوسطه اول — نگارش",
            lessonIds = listOf(
                "mid7-negaresh-02-bakhsh-haye-neveshte"
            ),
            checkLessonId = "mid7-negaresh-02-bakhsh-haye-neveshte",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 20,
            title = "نگارش هفتم ۳: طبقه‌بندی موضوع — ایده‌ها را دسته‌بندی کن",
            subtitle = "پایه هفتم متوسطه اول — نگارش",
            lessonIds = listOf(
                "mid7-negaresh-03-tabaghebandi-mozoo"
            ),
            checkLessonId = "mid7-negaresh-03-tabaghebandi-mozoo",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 21,
            title = "نگارش هفتم درس ۴: کوچک‌تر کردن موضوع",
            subtitle = "پایه هفتم متوسطه اول — نگارش",
            lessonIds = listOf(
                "mid7-negaresh-04-narrow-topic"
            ),
            checkLessonId = "mid7-negaresh-04-narrow-topic",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 22,
            title = "نگارش هفتم درس ۵: بندهای بخش میانی (بدنه)",
            subtitle = "پایه هفتم متوسطه اول — نگارش",
            lessonIds = listOf(
                "mid7-negaresh-05-body-paragraphs"
            ),
            checkLessonId = "mid7-negaresh-05-body-paragraphs",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 23,
            title = "نگارش هفتم درس ۶: جمله موضوع",
            subtitle = "پایه هفتم متوسطه اول — نگارش",
            lessonIds = listOf(
                "mid7-negaresh-06-topic-sentence"
            ),
            checkLessonId = "mid7-negaresh-06-topic-sentence",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 24,
            title = "نگارش هفتم درس ۷: راهی برای نظم دادن به ذهن و نوشته",
            subtitle = "پایه هفتم متوسطه اول — نگارش",
            lessonIds = listOf(
                "mid7-negaresh-07-organize-mind"
            ),
            checkLessonId = "mid7-negaresh-07-organize-mind",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 25,
            title = "نگارش هفتم درس ۸: روشی برای نوشتن درباره موضوع‌های ذهنی",
            subtitle = "پایه هفتم متوسطه اول — نگارش",
            lessonIds = listOf(
                "mid7-negaresh-08-mental-topics"
            ),
            checkLessonId = "mid7-negaresh-08-mental-topics",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 26,
            title = "ریاضی هفتم فصل ۱: راهبردهای حل مسئله",
            subtitle = "پایه هفتم متوسطه اول — ریاضی",
            lessonIds = listOf(
                "mid7-math-01-problem-strategies"
            ),
            checkLessonId = "mid7-math-01-problem-strategies",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 27,
            title = "ریاضی هفتم فصل ۲: عددهای صحیح",
            subtitle = "پایه هفتم متوسطه اول — ریاضی",
            lessonIds = listOf(
                "mid7-math-02-integers"
            ),
            checkLessonId = "mid7-math-02-integers",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 28,
            title = "ریاضی هفتم فصل ۳: جبر و معادله",
            subtitle = "پایه هفتم متوسطه اول — ریاضی",
            lessonIds = listOf(
                "mid7-math-03-algebra-equation"
            ),
            checkLessonId = "mid7-math-03-algebra-equation",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 29,
            title = "ریاضی هفتم فصل ۴: هندسه و استدلال",
            subtitle = "پایه هفتم متوسطه اول — ریاضی",
            lessonIds = listOf(
                "mid7-math-04-geometry-reasoning"
            ),
            checkLessonId = "mid7-math-04-geometry-reasoning",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 30,
            title = "ریاضی هفتم فصل ۵: شمارنده‌ها و اعداد اول",
            subtitle = "پایه هفتم متوسطه اول — ریاضی",
            lessonIds = listOf(
                "mid7-math-05-divisors-primes"
            ),
            checkLessonId = "mid7-math-05-divisors-primes",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 31,
            title = "ریاضی هفتم فصل ۶: سطح و حجم — از صفر",
            subtitle = "پایه هفتم متوسطه اول — ریاضی",
            lessonIds = listOf(
                "mid7-math-06-surface-volume"
            ),
            checkLessonId = "mid7-math-06-surface-volume",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 32,
            title = "ریاضی هفتم فصل ۷: توان و جذر — از صفر",
            subtitle = "پایه هفتم متوسطه اول — ریاضی",
            lessonIds = listOf(
                "mid7-math-07-power-root"
            ),
            checkLessonId = "mid7-math-07-power-root",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 33,
            title = "ریاضی هفتم فصل ۸: بردار و مختصات — از صفر",
            subtitle = "پایه هفتم متوسطه اول — ریاضی",
            lessonIds = listOf(
                "mid7-math-08-vector-coordinate"
            ),
            checkLessonId = "mid7-math-08-vector-coordinate",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 34,
            title = "ریاضی هفتم فصل ۹: آمار و احتمال — از صفر",
            subtitle = "پایه هفتم متوسطه اول — ریاضی",
            lessonIds = listOf(
                "mid7-math-09-stats-probability"
            ),
            checkLessonId = "mid7-math-09-stats-probability",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 35,
            title = "علوم هفتم فصل ۱: تجربه و تفکر — از صفر",
            subtitle = "پایه هفتم متوسطه اول — علوم تجربی",
            lessonIds = listOf(
                "mid7-science-01-experience-thinking"
            ),
            checkLessonId = "mid7-science-01-experience-thinking",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 36,
            title = "علوم هفتم فصل ۲: اندازه‌گیری در علوم و ابزارهای آن — از صفر",
            subtitle = "پایه هفتم متوسطه اول — علوم تجربی",
            lessonIds = listOf(
                "mid7-science-02-measurement-tools"
            ),
            checkLessonId = "mid7-science-02-measurement-tools",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 37,
            title = "علوم هفتم فصل ۳: اتم‌ها الفبای مواد — از صفر",
            subtitle = "پایه هفتم متوسطه اول — علوم تجربی",
            lessonIds = listOf(
                "mid7-science-03-atoms-alphabet"
            ),
            checkLessonId = "mid7-science-03-atoms-alphabet",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 38,
            title = "علوم هفتم فصل ۴: مواد پیرامون ما — از صفر",
            subtitle = "پایه هفتم متوسطه اول — علوم تجربی",
            lessonIds = listOf(
                "mid7-science-04-materials-around"
            ),
            checkLessonId = "mid7-science-04-materials-around",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 39,
            title = "علوم هفتم فصل ۵: از معدن تا خانه — از صفر",
            subtitle = "پایه هفتم متوسطه اول — علوم تجربی",
            lessonIds = listOf(
                "mid7-science-05-mine-to-home"
            ),
            checkLessonId = "mid7-science-05-mine-to-home",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 40,
            title = "علوم هفتم فصل ۶: سفر آب روی زمین — از صفر",
            subtitle = "پایه هفتم متوسطه اول — علوم تجربی",
            lessonIds = listOf(
                "mid7-science-06-water-cycle-land"
            ),
            checkLessonId = "mid7-science-06-water-cycle-land",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 41,
            title = "علوم هفتم فصل ۷: سفر آب درون زمین — از صفر",
            subtitle = "پایه هفتم متوسطه اول — علوم تجربی",
            lessonIds = listOf(
                "mid7-science-07-groundwater"
            ),
            checkLessonId = "mid7-science-07-groundwater",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 42,
            title = "علوم هفتم فصل ۸: انرژی و تبدیل‌های آن — از صفر",
            subtitle = "پایه هفتم متوسطه اول — علوم تجربی",
            lessonIds = listOf(
                "mid7-science-08-energy-conversion"
            ),
            checkLessonId = "mid7-science-08-energy-conversion",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 43,
            title = "علوم هفتم فصل ۹: منابع انرژی — از صفر",
            subtitle = "پایه هفتم متوسطه اول — علوم تجربی",
            lessonIds = listOf(
                "mid7-science-09-energy-sources"
            ),
            checkLessonId = "mid7-science-09-energy-sources",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 44,
            title = "علوم هفتم فصل ۱۰: گرما و بهینه‌سازی مصرف انرژی — از صفر",
            subtitle = "پایه هفتم متوسطه اول — علوم تجربی",
            lessonIds = listOf(
                "mid7-science-10-heat-saving"
            ),
            checkLessonId = "mid7-science-10-heat-saving",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 45,
            title = "علوم هفتم فصل ۱۱: یاخته و سازمان‌بندی آن — از صفر",
            subtitle = "پایه هفتم متوسطه اول — علوم تجربی",
            lessonIds = listOf(
                "mid7-science-11-cell"
            ),
            checkLessonId = "mid7-science-11-cell",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 46,
            title = "علوم هفتم فصل ۱۲: سفره سلامت — از صفر",
            subtitle = "پایه هفتم متوسطه اول — علوم تجربی",
            lessonIds = listOf(
                "mid7-science-12-health-table"
            ),
            checkLessonId = "mid7-science-12-health-table",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 47,
            title = "علوم هفتم فصل ۱۳: سفر غذا — از صفر",
            subtitle = "پایه هفتم متوسطه اول — علوم تجربی",
            lessonIds = listOf(
                "mid7-science-13-food-journey"
            ),
            checkLessonId = "mid7-science-13-food-journey",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 48,
            title = "علوم هفتم فصل ۱۴: گردش مواد — از صفر",
            subtitle = "پایه هفتم متوسطه اول — علوم تجربی",
            lessonIds = listOf(
                "mid7-science-14-material-circulation"
            ),
            checkLessonId = "mid7-science-14-material-circulation",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 49,
            title = "علوم هفتم فصل ۱۵: تبادل با محیط — از صفر",
            subtitle = "پایه هفتم متوسطه اول — علوم تجربی",
            lessonIds = listOf(
                "mid7-science-15-exchange-environment"
            ),
            checkLessonId = "mid7-science-15-exchange-environment",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 50,
            title = "عربی هفتم درس ۱: الحروف العربیة — حروف الفبا و تلفظ — از صفر",
            subtitle = "پایه هفتم متوسطه اول — عربی",
            lessonIds = listOf(
                "mid7-arabic-01-alphabet"
            ),
            checkLessonId = "mid7-arabic-01-alphabet",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 51,
            title = "عربی هفتم ۲: در کلاس (فِی الصَّفِّ) — سلام و احوال‌پرسی، این و آن",
            subtitle = "پایه هفتم متوسطه اول — عربی",
            lessonIds = listOf(
                "mid7-arabic-02-greetings"
            ),
            checkLessonId = "mid7-arabic-02-greetings",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 52,
            title = "عربی هفتم ۳: زبان زیبا (اللُّغَةُ الجَمیلَةُ) — چرا عربی یاد می‌گیریم؟",
            subtitle = "پایه هفتم متوسطه اول — عربی",
            lessonIds = listOf(
                "mid7-arabic-03-language"
            ),
            checkLessonId = "mid7-arabic-03-language",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 53,
            title = "عربی هفتم ۴: ارزش دانش (قیمَةُ العِلْمِ) — اسم مذکر و مؤنث",
            subtitle = "پایه هفتم متوسطه اول — عربی",
            lessonIds = listOf(
                "mid7-arabic-04-gender"
            ),
            checkLessonId = "mid7-arabic-04-gender",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 54,
            title = "عربی هفتم ۵: نور سخن (نورُ الکَلامِ) — اسم مثنی و اسم اشاره مثنی",
            subtitle = "پایه هفتم متوسطه اول — عربی",
            lessonIds = listOf(
                "mid7-arabic-05-dual"
            ),
            checkLessonId = "mid7-arabic-05-dual",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 55,
            title = "عربی هفتم ۶: تفکر (التَّفَکُّرُ) — اسم اشاره جمع",
            subtitle = "پایه هفتم متوسطه اول — عربی",
            lessonIds = listOf(
                "mid7-arabic-06-plural-demonstrative"
            ),
            checkLessonId = "mid7-arabic-06-plural-demonstrative",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 56,
            title = "عربی هفتم ۷: بهترین سخن (خَیْرُ الکَلامِ) — «هَلْ» و «أَ»",
            subtitle = "پایه هفتم متوسطه اول — عربی",
            lessonIds = listOf(
                "mid7-arabic-07-hal-hamza"
            ),
            checkLessonId = "mid7-arabic-07-hal-hamza",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 57,
            title = "عربی هفتم ۸: گنج‌های حکمت (کُنوزُ الحِکْمَةِ) — «مَنْ»",
            subtitle = "پایه هفتم متوسطه اول — عربی",
            lessonIds = listOf(
                "mid7-arabic-08-man"
            ),
            checkLessonId = "mid7-arabic-08-man",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 58,
            title = "عربی هفتم ۹: گنج نصیحت (کَنْزُ النَّصیحَةِ) — «ما»",
            subtitle = "پایه هفتم متوسطه اول — عربی",
            lessonIds = listOf(
                "mid7-arabic-09-ma"
            ),
            checkLessonId = "mid7-arabic-09-ma",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 59,
            title = "عربی هفتم ۱۰: حکمت‌های سودمند (الحِکَمُ النّافِعَةُ) — «أَیْنَ»",
            subtitle = "پایه هفتم متوسطه اول — عربی",
            lessonIds = listOf(
                "mid7-arabic-10-ayna"
            ),
            checkLessonId = "mid7-arabic-10-ayna",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 60,
            title = "عربی هفتم ۱۱: موعظه‌های عددی (المَواعِظُ العَدَدِیَّةُ) — «کَمْ» و عددهای اصلی یک تا دوازده",
            subtitle = "پایه هفتم متوسطه اول — عربی",
            lessonIds = listOf(
                "mid7-arabic-11-kam-numbers"
            ),
            checkLessonId = "mid7-arabic-11-kam-numbers",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 61,
            title = "عربی هفتم ۱۲: اصطلاحات گفت‌وگوی روزانه — سلام و احوال‌پرسی",
            subtitle = "پایه هفتم متوسطه اول — عربی",
            lessonIds = listOf(
                "mid7-arabic-12-daily-conversation"
            ),
            checkLessonId = "mid7-arabic-12-daily-conversation",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 62,
            title = "عربی هفتم ۱۳: فعل ماضی اول و دوم شخص مفرد — در بازار",
            subtitle = "پایه هفتم متوسطه اول — عربی",
            lessonIds = listOf(
                "mid7-arabic-13-past-first-second-singular"
            ),
            checkLessonId = "mid7-arabic-13-past-first-second-singular",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 63,
            title = "عربی هفتم ۱۴: فعل ماضی سوم شخص مفرد — گفت‌وگوی مادر و پسر",
            subtitle = "پایه هفتم متوسطه اول — عربی",
            lessonIds = listOf(
                "mid7-arabic-14-mother-son-third-singular"
            ),
            checkLessonId = "mid7-arabic-14-mother-son-third-singular",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 64,
            title = "عربی هفتم ۱۵: فعل ماضی اول شخص جمع و ماضی منفی — گفت‌وگوی تلفنی مادربزرگ و نوه",
            subtitle = "پایه هفتم متوسطه اول — عربی",
            lessonIds = listOf(
                "mid7-arabic-15-first-person-plural-phone"
            ),
            checkLessonId = "mid7-arabic-15-first-person-plural-phone",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 65,
            title = "عربی هفتم ۱۶: فعل ماضی دوم شخص جمع — خانواده روستایی موفق",
            subtitle = "پایه هفتم متوسطه اول — عربی",
            lessonIds = listOf(
                "mid7-arabic-16-second-person-plural-village"
            ),
            checkLessonId = "mid7-arabic-16-second-person-plural-village",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 66,
            title = "عربی هفتم ۱۷: فعل ماضی سوم شخص جمع و مرور ضمیر و فعل ماضی — داستان و راهپیمایی اربعین",
            subtitle = "پایه هفتم متوسطه اول — عربی",
            lessonIds = listOf(
                "mid7-arabic-17-arbaeen-story-third-plural"
            ),
            checkLessonId = "mid7-arabic-17-arbaeen-story-third-plural",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 67,
            title = "انگلیسی هفتم ۱: My Name (نام من) — معرفی خود",
            subtitle = "پایه هفتم متوسطه اول — زبان انگلیسی",
            lessonIds = listOf(
                "mid7-english-01-my-name"
            ),
            checkLessonId = "mid7-english-01-my-name",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 68,
            title = "انگلیسی هفتم ۲: My Classmates (هم‌کلاسی‌های من) — معرفی دیگران",
            subtitle = "پایه هفتم متوسطه اول — زبان انگلیسی",
            lessonIds = listOf(
                "mid7-english-02-my-classmates"
            ),
            checkLessonId = "mid7-english-02-my-classmates",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 69,
            title = "انگلیسی هفتم ۳: My Age (سن من) — چند ساله هستی؟",
            subtitle = "پایه هفتم متوسطه اول — زبان انگلیسی",
            lessonIds = listOf(
                "mid7-english-03-my-age"
            ),
            checkLessonId = "mid7-english-03-my-age",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 70,
            title = "انگلیسی هفتم ۴: My Family (خانواده من) — پدر و مادر و برادر و خواهر",
            subtitle = "پایه هفتم متوسطه اول — زبان انگلیسی",
            lessonIds = listOf(
                "mid7-english-04-my-family"
            ),
            checkLessonId = "mid7-english-04-my-family",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 71,
            title = "انگلیسی هفتم ۵: ظاهر من — My Appearance",
            subtitle = "پایه هفتم متوسطه اول — زبان انگلیسی",
            lessonIds = listOf(
                "mid7-english-05-appearance"
            ),
            checkLessonId = "mid7-english-05-appearance",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 72,
            title = "انگلیسی هفتم ۶: خانه من — My House",
            subtitle = "پایه هفتم متوسطه اول — زبان انگلیسی",
            lessonIds = listOf(
                "mid7-english-06-house"
            ),
            checkLessonId = "mid7-english-06-house",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 73,
            title = "انگلیسی هفتم ۷: آدرس من — My Address",
            subtitle = "پایه هفتم متوسطه اول — زبان انگلیسی",
            lessonIds = listOf(
                "mid7-english-07-address"
            ),
            checkLessonId = "mid7-english-07-address",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 74,
            title = "انگلیسی هفتم ۸: غذای موردعلاقه من — My Favorite Food",
            subtitle = "پایه هفتم متوسطه اول — زبان انگلیسی",
            lessonIds = listOf(
                "mid7-english-08-favorite-food"
            ),
            checkLessonId = "mid7-english-08-favorite-food",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 75,
            title = "مطالعات هفتم ۱: من حق دارم",
            subtitle = "پایه هفتم متوسطه اول — مطالعات اجتماعی",
            lessonIds = listOf(
                "mid7-social_studies-01-rights"
            ),
            checkLessonId = "mid7-social_studies-01-rights",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 76,
            title = "مطالعات هفتم ۲: من مسئول هستم",
            subtitle = "پایه هفتم متوسطه اول — مطالعات اجتماعی",
            lessonIds = listOf(
                "mid7-social_studies-02-responsibility"
            ),
            checkLessonId = "mid7-social_studies-02-responsibility",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 77,
            title = "مطالعات هفتم ۳: چرا به مقررات و قوانین نیاز داریم؟",
            subtitle = "پایه هفتم متوسطه اول — مطالعات اجتماعی",
            lessonIds = listOf(
                "mid7-social_studies-03-why-rules"
            ),
            checkLessonId = "mid7-social_studies-03-why-rules",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 78,
            title = "مطالعات هفتم ۴: قانون‌گذاری",
            subtitle = "پایه هفتم متوسطه اول — مطالعات اجتماعی",
            lessonIds = listOf(
                "mid7-social_studies-04-legislation"
            ),
            checkLessonId = "mid7-social_studies-04-legislation",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 79,
            title = "مطالعات هفتم ۵: همدلی و همیاری در حوادث",
            subtitle = "پایه هفتم متوسطه اول — مطالعات اجتماعی",
            lessonIds = listOf(
                "mid7-social_studies-05-empathy-disasters"
            ),
            checkLessonId = "mid7-social_studies-05-empathy-disasters",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 80,
            title = "مطالعات هفتم ۶: بیمه و مقابله با حوادث",
            subtitle = "پایه هفتم متوسطه اول — مطالعات اجتماعی",
            lessonIds = listOf(
                "mid7-social_studies-06-insurance-disasters"
            ),
            checkLessonId = "mid7-social_studies-06-insurance-disasters",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 81,
            title = "مطالعات هفتم ۷: تولید و توزیع — از صفر",
            subtitle = "پایه هفتم متوسطه اول — مطالعات اجتماعی",
            lessonIds = listOf(
                "mid7-social_studies-07-production-distribution"
            ),
            checkLessonId = "mid7-social_studies-07-production-distribution",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 82,
            title = "مطالعات هفتم ۸: مصرف — از صفر",
            subtitle = "پایه هفتم متوسطه اول — مطالعات اجتماعی",
            lessonIds = listOf(
                "mid7-social_studies-08-consumption"
            ),
            checkLessonId = "mid7-social_studies-08-consumption",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 83,
            title = "مطالعات هفتم ۹: من کجا زندگی می‌کنم؟ — از صفر",
            subtitle = "پایه هفتم متوسطه اول — مطالعات اجتماعی",
            lessonIds = listOf(
                "mid7-social_studies-09-where-live"
            ),
            checkLessonId = "mid7-social_studies-09-where-live",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 84,
            title = "مطالعات هفتم ۱۰: ایران، خانه ما — از صفر",
            subtitle = "پایه هفتم متوسطه اول — مطالعات اجتماعی",
            lessonIds = listOf(
                "mid7-social_studies-10-iran-home"
            ),
            checkLessonId = "mid7-social_studies-10-iran-home",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 85,
            title = "مطالعات هفتم ۱۱: تنوع آب و هوای ایران — از صفر",
            subtitle = "پایه هفتم متوسطه اول — مطالعات اجتماعی",
            lessonIds = listOf(
                "mid7-social_studies-11-climate-diversity"
            ),
            checkLessonId = "mid7-social_studies-11-climate-diversity",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 86,
            title = "مطالعات هفتم ۱۲: حفاظت از زیستگاه‌های ایران — از صفر",
            subtitle = "پایه هفتم متوسطه اول — مطالعات اجتماعی",
            lessonIds = listOf(
                "mid7-social_studies-12-habitat-protection"
            ),
            checkLessonId = "mid7-social_studies-12-habitat-protection",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 87,
            title = "مطالعات هفتم ۱۳: جمعیت ایران — از صفر",
            subtitle = "پایه هفتم متوسطه اول — مطالعات اجتماعی",
            lessonIds = listOf(
                "mid7-social_studies-13-population"
            ),
            checkLessonId = "mid7-social_studies-13-population",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 88,
            title = "مطالعات هفتم ۱۴: منابع آب و خاک — از صفر",
            subtitle = "پایه هفتم متوسطه اول — مطالعات اجتماعی",
            lessonIds = listOf(
                "mid7-social_studies-14-water-soil"
            ),
            checkLessonId = "mid7-social_studies-14-water-soil",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 89,
            title = "مطالعات هفتم ۱۵: گردشگری چیست؟ — از صفر",
            subtitle = "پایه هفتم متوسطه اول — مطالعات اجتماعی",
            lessonIds = listOf(
                "mid7-social_studies-15-what-is-tourism"
            ),
            checkLessonId = "mid7-social_studies-15-what-is-tourism",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 90,
            title = "مطالعات هفتم ۱۶: جاذبه‌های گردشگری ایران — از صفر",
            subtitle = "پایه هفتم متوسطه اول — مطالعات اجتماعی",
            lessonIds = listOf(
                "mid7-social_studies-16-tourism-attractions"
            ),
            checkLessonId = "mid7-social_studies-16-tourism-attractions",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 91,
            title = "مطالعات هفتم ۱۷: میراث فرهنگی و تاریخ — از صفر",
            subtitle = "پایه هفتم متوسطه اول — مطالعات اجتماعی",
            lessonIds = listOf(
                "mid7-social_studies-17-miras-farhangi"
            ),
            checkLessonId = "mid7-social_studies-17-miras-farhangi",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 92,
            title = "مطالعات هفتم ۱۸: قدیمی‌ترین سکونتگاه‌های ایران — از صفر",
            subtitle = "پایه هفتم متوسطه اول — مطالعات اجتماعی",
            lessonIds = listOf(
                "mid7-social_studies-18-qadimi-tarin-sokonatgah"
            ),
            checkLessonId = "mid7-social_studies-18-qadimi-tarin-sokonatgah",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 93,
            title = "مطالعات هفتم ۱۹: آریایی‌ها و تشکیل حکومت‌های قدرتمند در ایران — از صفر",
            subtitle = "پایه هفتم متوسطه اول — مطالعات اجتماعی",
            lessonIds = listOf(
                "mid7-social_studies-19-aryaiha"
            ),
            checkLessonId = "mid7-social_studies-19-aryaiha",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 94,
            title = "مطالعات هفتم ۲۰: امپراتوری‌های ایران باستان چگونه کشور را اداره می‌کردند؟ — از صفر",
            subtitle = "پایه هفتم متوسطه اول — مطالعات اجتماعی",
            lessonIds = listOf(
                "mid7-social_studies-20-empire-admin"
            ),
            checkLessonId = "mid7-social_studies-20-empire-admin",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 95,
            title = "مطالعات هفتم ۲۱: اوضاع اجتماعی ایران باستان — از صفر",
            subtitle = "پایه هفتم متوسطه اول — مطالعات اجتماعی",
            lessonIds = listOf(
                "mid7-social_studies-21-owzae-ejtemaei"
            ),
            checkLessonId = "mid7-social_studies-21-owzae-ejtemaei",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 96,
            title = "مطالعات هفتم ۲۲: اوضاع اقتصادی در ایران باستان — از صفر",
            subtitle = "پایه هفتم متوسطه اول — مطالعات اجتماعی",
            lessonIds = listOf(
                "mid7-social_studies-22-owzae-eqtesadi"
            ),
            checkLessonId = "mid7-social_studies-22-owzae-eqtesadi",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 97,
            title = "مطالعات هفتم ۲۳: عقاید و سبک زندگی مردم در ایران باستان — از صفر",
            subtitle = "پایه هفتم متوسطه اول — مطالعات اجتماعی",
            lessonIds = listOf(
                "mid7-social_studies-23-aqaed-sabk-zendegi"
            ),
            checkLessonId = "mid7-social_studies-23-aqaed-sabk-zendegi",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 98,
            title = "مطالعات هفتم ۲۴: دانش و هنر در ایران باستان — از صفر",
            subtitle = "پایه هفتم متوسطه اول — مطالعات اجتماعی",
            lessonIds = listOf(
                "mid7-social_studies-24-danesh-honar"
            ),
            checkLessonId = "mid7-social_studies-24-danesh-honar",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 99,
            title = "پیام هفتم ۱: بینای مهربان — از صفر",
            subtitle = "پایه هفتم متوسطه اول — پیام‌های آسمان",
            lessonIds = listOf(
                "mid7-payam-01-binay-mehraban"
            ),
            checkLessonId = "mid7-payam-01-binay-mehraban",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 100,
            title = "پیام هفتم ۲: استعانت از خداوند — از صفر",
            subtitle = "پایه هفتم متوسطه اول — پیام‌های آسمان",
            lessonIds = listOf(
                "mid7-payam-02-esteanat"
            ),
            checkLessonId = "mid7-payam-02-esteanat",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 101,
            title = "پیام هفتم ۳: تلخ یا شیرین — از صفر",
            subtitle = "پایه هفتم متوسطه اول — پیام‌های آسمان",
            lessonIds = listOf(
                "mid7-payam-03-talkh-ya-shirin"
            ),
            checkLessonId = "mid7-payam-03-talkh-ya-shirin",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 102,
            title = "پیام هفتم ۴: عبور آسان — از صفر",
            subtitle = "پایه هفتم متوسطه اول — پیام‌های آسمان",
            lessonIds = listOf(
                "mid7-payam-04-obour-asan"
            ),
            checkLessonId = "mid7-payam-04-obour-asan",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 103,
            title = "پیام هفتم ۵: پیامبر رحمت — از صفر",
            subtitle = "پایه هفتم متوسطه اول — پیام‌های آسمان",
            lessonIds = listOf(
                "mid7-payam-05-payambar-rahmat"
            ),
            checkLessonId = "mid7-payam-05-payambar-rahmat",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 104,
            title = "پیام هفتم ۶: اسوه فداکاری و عدالت — از صفر",
            subtitle = "پایه هفتم متوسطه اول — پیام‌های آسمان",
            lessonIds = listOf(
                "mid7-payam-06-osveh-fedakari-adalat"
            ),
            checkLessonId = "mid7-payam-06-osveh-fedakari-adalat",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 105,
            title = "پیام هفتم ۷: برترین بانو — از صفر",
            subtitle = "پایه هفتم متوسطه اول — پیام‌های آسمان",
            lessonIds = listOf(
                "mid7-payam-07-bartarin-banu"
            ),
            checkLessonId = "mid7-payam-07-bartarin-banu",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 106,
            title = "پیام هفتم ۸: افتخار بندگی — از صفر",
            subtitle = "پایه هفتم متوسطه اول — پیام‌های آسمان",
            lessonIds = listOf(
                "mid7-payam-08-eftekhar-bandegi"
            ),
            checkLessonId = "mid7-payam-08-eftekhar-bandegi",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 107,
            title = "پیام هفتم ۹: به سوی پاکی — از صفر",
            subtitle = "پایه هفتم متوسطه اول — پیام‌های آسمان",
            lessonIds = listOf(
                "mid7-payam-09-be-suye-paki"
            ),
            checkLessonId = "mid7-payam-09-be-suye-paki",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 108,
            title = "پیام هفتم ۱۰: ستون دین — از صفر",
            subtitle = "پایه هفتم متوسطه اول — پیام‌های آسمان",
            lessonIds = listOf(
                "mid7-payam-10-sotoun-din"
            ),
            checkLessonId = "mid7-payam-10-sotoun-din",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 109,
            title = "پیام هفتم ۱۱: نماز جماعت — از صفر",
            subtitle = "پایه هفتم متوسطه اول — پیام‌های آسمان",
            lessonIds = listOf(
                "mid7-payam-11-namaz-jamaat"
            ),
            checkLessonId = "mid7-payam-11-namaz-jamaat",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 110,
            title = "پیام هفتم ۱۲: نشان عزت — از صفر",
            subtitle = "پایه هفتم متوسطه اول — پیام‌های آسمان",
            lessonIds = listOf(
                "mid7-payam-12-neshan-ezzat"
            ),
            checkLessonId = "mid7-payam-12-neshan-ezzat",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 111,
            title = "پیام‌های آسمان هفتم ۱۳: بر بال فرشتگان — ارزش علم‌آموزی",
            subtitle = "پایه هفتم متوسطه اول — پیام‌های آسمان",
            lessonIds = listOf(
                "mid7-payam-13-bar-bal-fereshtegan"
            ),
            checkLessonId = "mid7-payam-13-bar-bal-fereshtegan",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 112,
            title = "پیام‌های آسمان هفتم ۱۴: کمال همنشین — دوست خوب کیست؟",
            subtitle = "پایه هفتم متوسطه اول — پیام‌های آسمان",
            lessonIds = listOf(
                "mid7-payam-14-kamal-hamneshin"
            ),
            checkLessonId = "mid7-payam-14-kamal-hamneshin",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 113,
            title = "پیام‌های آسمان هفتم ۱۵: مزدوران شیطان — عجله و تنبلی",
            subtitle = "پایه هفتم متوسطه اول — پیام‌های آسمان",
            lessonIds = listOf(
                "mid7-payam-15-mazdoran-sheytan"
            ),
            checkLessonId = "mid7-payam-15-mazdoran-sheytan",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 114,
            title = "کار و فناوری هفتم ۱: نوآوری و فناوری — از صفر",
            subtitle = "پایه هفتم متوسطه اول — کار و فناوری",
            lessonIds = listOf(
                "mid7-karfan-01-noavari-fanavari"
            ),
            checkLessonId = "mid7-karfan-01-noavari-fanavari",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 115,
            title = "کار و فناوری هفتم ۲: کاربرد فناوری اطلاعات و ارتباطات — از صفر",
            subtitle = "پایه هفتم متوسطه اول — کار و فناوری",
            lessonIds = listOf(
                "mid7-karfan-02-karbord-fanavari-ettelaat"
            ),
            checkLessonId = "mid7-karfan-02-karbord-fanavari-ettelaat",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 116,
            title = "کار و فناوری هفتم ۳: جستجو و جمع‌آوری اطلاعات — از صفر",
            subtitle = "پایه هفتم متوسطه اول — کار و فناوری",
            lessonIds = listOf(
                "mid7-karfan-03-jostoju-jamavari"
            ),
            checkLessonId = "mid7-karfan-03-jostoju-jamavari",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 117,
            title = "کار و فناوری هفتم ۴: مستندسازی — از صفر",
            subtitle = "پایه هفتم متوسطه اول — کار و فناوری",
            lessonIds = listOf(
                "mid7-karfan-04-mostanadsazi"
            ),
            checkLessonId = "mid7-karfan-04-mostanadsazi",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 118,
            title = "کار و فناوری هفتم ۵: اشتراک‌گذاری اطلاعات — از صفر",
            subtitle = "پایه هفتم متوسطه اول — کار و فناوری",
            lessonIds = listOf(
                "mid7-karfan-05-eshterakgozari-ettelaat"
            ),
            checkLessonId = "mid7-karfan-05-eshterakgozari-ettelaat",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 119,
            title = "کار و فناوری هفتم ۶: برنامه‌نویسی — از صفر",
            subtitle = "پایه هفتم متوسطه اول — کار و فناوری",
            lessonIds = listOf(
                "mid7-karfan-06-barnamenevisi"
            ),
            checkLessonId = "mid7-karfan-06-barnamenevisi",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 120,
            title = "کار و فناوری هفتم ۷: کسب و کار — از صفر",
            subtitle = "پایه هفتم متوسطه اول — کار و فناوری",
            lessonIds = listOf(
                "mid7-karfan-07-kasb-kar"
            ),
            checkLessonId = "mid7-karfan-07-kasb-kar",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 121,
            title = "کار و فناوری هفتم ۸: نقشه‌کشی — از صفر",
            subtitle = "پایه هفتم متوسطه اول — کار و فناوری",
            lessonIds = listOf(
                "mid7-karfan-08-naghshekeshi"
            ),
            checkLessonId = "mid7-karfan-08-naghshekeshi",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 122,
            title = "کار و فناوری هفتم ۹: کار با چوب — از صفر",
            subtitle = "پایه هفتم متوسطه اول — کار و فناوری",
            lessonIds = listOf(
                "mid7-karfan-09-kar-ba-choob"
            ),
            checkLessonId = "mid7-karfan-09-kar-ba-choob",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 123,
            title = "کار و فناوری هفتم ۱۰: پرورش و نگهداری گیاهان — از صفر",
            subtitle = "پایه هفتم متوسطه اول — کار و فناوری",
            lessonIds = listOf(
                "mid7-karfan-10-parvaresh-giyahan"
            ),
            checkLessonId = "mid7-karfan-10-parvaresh-giyahan",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 124,
            title = "کار و فناوری هفتم ۱۱: پوشاک — از صفر",
            subtitle = "پایه هفتم متوسطه اول — کار و فناوری",
            lessonIds = listOf(
                "mid7-karfan-11-pooshak"
            ),
            checkLessonId = "mid7-karfan-11-pooshak",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 125,
            title = "کار و فناوری هفتم ۱۲: خوراک — از صفر",
            subtitle = "پایه هفتم متوسطه اول — کار و فناوری",
            lessonIds = listOf(
                "mid7-karfan-12-khorak"
            ),
            checkLessonId = "mid7-karfan-12-khorak",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 126,
            title = "تفکر هفتم ۱: تفکر درباره تفکر — از صفر",
            subtitle = "پایه هفتم متوسطه اول — تفکر و سبک زندگی",
            lessonIds = listOf(
                "mid7-tafakkor-01-tafakkor-darbare-tafakkor"
            ),
            checkLessonId = "mid7-tafakkor-01-tafakkor-darbare-tafakkor",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 127,
            title = "تفکر هفتم ۲: سکه طلا — از صفر",
            subtitle = "پایه هفتم متوسطه اول — تفکر و سبک زندگی",
            lessonIds = listOf(
                "mid7-tafakkor-02-sekke-tala"
            ),
            checkLessonId = "mid7-tafakkor-02-sekke-tala",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 128,
            title = "تفکر هفتم ۳: بز یا سگ؟ — از صفر",
            subtitle = "پایه هفتم متوسطه اول — تفکر و سبک زندگی",
            lessonIds = listOf(
                "mid7-tafakkor-03-boz-ya-sag"
            ),
            checkLessonId = "mid7-tafakkor-03-boz-ya-sag",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 129,
            title = "تفکر هفتم ۴: پروژه پژوهشی — از صفر",
            subtitle = "پایه هفتم متوسطه اول — تفکر و سبک زندگی",
            lessonIds = listOf(
                "mid7-tafakkor-04-prozhe-pazhooheshi"
            ),
            checkLessonId = "mid7-tafakkor-04-prozhe-pazhooheshi",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 130,
            title = "تفکر هفتم ۵: طرح سؤال پژوهش — از صفر",
            subtitle = "پایه هفتم متوسطه اول — تفکر و سبک زندگی",
            lessonIds = listOf(
                "mid7-tafakkor-05-tarh-soal-pazhoohesh"
            ),
            checkLessonId = "mid7-tafakkor-05-tarh-soal-pazhoohesh",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 131,
            title = "تفکر هفتم ۶: تهیه طرح پژوهش — از صفر",
            subtitle = "پایه هفتم متوسطه اول — تفکر و سبک زندگی",
            lessonIds = listOf(
                "mid7-tafakkor-06-tahiye-tarh-pazhoohesh"
            ),
            checkLessonId = "mid7-tafakkor-06-tahiye-tarh-pazhoohesh",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 132,
            title = "تفکر هفتم ۷: برنامه‌ریزی برای اجرا — از صفر",
            subtitle = "پایه هفتم متوسطه اول — تفکر و سبک زندگی",
            lessonIds = listOf(
                "mid7-tafakkor-07-barnamerizi-baraye-ejra"
            ),
            checkLessonId = "mid7-tafakkor-07-barnamerizi-baraye-ejra",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 133,
            title = "تفکر هفتم ۸: من کیستم؟ — شناخت خود از صفر",
            subtitle = "پایه هفتم متوسطه اول — تفکر و سبک زندگی",
            lessonIds = listOf(
                "mid7-tafakkor-08-man-kistam"
            ),
            checkLessonId = "mid7-tafakkor-08-man-kistam",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 134,
            title = "تفکر هفتم ۹: خودآگاهی چیست؟ — احساس و فکرم را بشناسم",
            subtitle = "پایه هفتم متوسطه اول — تفکر و سبک زندگی",
            lessonIds = listOf(
                "mid7-tafakkor-09-khodagahi"
            ),
            checkLessonId = "mid7-tafakkor-09-khodagahi",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 135,
            title = "تفکر هفتم ۱۰: مطالعه و یادگیری — چطور بخوانم که یاد بگیرم؟",
            subtitle = "پایه هفتم متوسطه اول — تفکر و سبک زندگی",
            lessonIds = listOf(
                "mid7-tafakkor-10-motalee-va-yadgiri"
            ),
            checkLessonId = "mid7-tafakkor-10-motalee-va-yadgiri",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 136,
            title = "تفکر هفتم ۱۱: زمان را چگونه مدیریت کنیم؟ — وقت من، سرمایه من",
            subtitle = "پایه هفتم متوسطه اول — تفکر و سبک زندگی",
            lessonIds = listOf(
                "mid7-tafakkor-11-modiriyat-zaman"
            ),
            checkLessonId = "mid7-tafakkor-11-modiriyat-zaman",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 137,
            title = "تفکر هفتم ۱۲: مهارت تاب‌آوری و تحمل سختی — وقتی سخت می‌شود چه کنم؟",
            subtitle = "پایه هفتم متوسطه اول — تفکر و سبک زندگی",
            lessonIds = listOf(
                "mid7-tafakkor-12-tabavari"
            ),
            checkLessonId = "mid7-tafakkor-12-tabavari",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 138,
            title = "تفکر هفتم ۱۳: آداب و مهارت‌های زندگی رسانه‌ای — در دنیای رسانه‌ها چطور زندگی کنم؟",
            subtitle = "پایه هفتم متوسطه اول — تفکر و سبک زندگی",
            lessonIds = listOf(
                "mid7-tafakkor-13-resanei"
            ),
            checkLessonId = "mid7-tafakkor-13-resanei",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 139,
            title = "تفکر هفتم ۱۴: حکایت تربیت — تربیت یعنی چی و چطور اتفاق می‌افتد؟",
            subtitle = "پایه هفتم متوسطه اول — تفکر و سبک زندگی",
            lessonIds = listOf(
                "mid7-tafakkor-14-hekayat-tarbiyat"
            ),
            checkLessonId = "mid7-tafakkor-14-hekayat-tarbiyat",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 140,
            title = "تفکر هفتم ۱۵: راز تعادل — نه افراط، نه تفریط؛ راه میانه",
            subtitle = "پایه هفتم متوسطه اول — تفکر و سبک زندگی",
            lessonIds = listOf(
                "mid7-tafakkor-15-raz-tadol"
            ),
            checkLessonId = "mid7-tafakkor-15-raz-tadol",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 141,
            title = "تفکر هفتم ۱۶: سراب — چیزی که می‌بینم، همیشه درست نیست",
            subtitle = "پایه هفتم متوسطه اول — تفکر و سبک زندگی",
            lessonIds = listOf(
                "mid7-tafakkor-16-sarab"
            ),
            checkLessonId = "mid7-tafakkor-16-sarab",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 142,
            title = "تفکر هفتم ۱۷: ارائه گزارش پژوهش — پژوهشم را چطور بنویسم و بگویم؟",
            subtitle = "پایه هفتم متوسطه اول — تفکر و سبک زندگی",
            lessonIds = listOf(
                "mid7-tafakkor-17-gozaresh-pazhuhesh"
            ),
            checkLessonId = "mid7-tafakkor-17-gozaresh-pazhuhesh",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 143,
            title = "تفکر هفتم ۱۸: سبک‌های ارتباطی ما با دیگران چگونه است؟",
            subtitle = "پایه هفتم متوسطه اول — تفکر و سبک زندگی",
            lessonIds = listOf(
                "mid7-tafakkor-18-communication-styles"
            ),
            checkLessonId = "mid7-tafakkor-18-communication-styles",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 144,
            title = "تفکر هفتم ۱۹: مهارت‌های ارتباطی و آداب معاشرت",
            subtitle = "پایه هفتم متوسطه اول — تفکر و سبک زندگی",
            lessonIds = listOf(
                "mid7-tafakkor-19-communication-skills"
            ),
            checkLessonId = "mid7-tafakkor-19-communication-skills",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 145,
            title = "تفکر هفتم ۲۰: ارتباط با خانواده، همسایگان و معلمان چگونه است؟",
            subtitle = "پایه هفتم متوسطه اول — تفکر و سبک زندگی",
            lessonIds = listOf(
                "mid7-tafakkor-20-family-neighbors-teachers"
            ),
            checkLessonId = "mid7-tafakkor-20-family-neighbors-teachers",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 146,
            title = "تفکر هفتم ۲۱: معیار من در انتخاب دوست چیست؟",
            subtitle = "پایه هفتم متوسطه اول — تفکر و سبک زندگی",
            lessonIds = listOf(
                "mid7-tafakkor-21-choosing-friend"
            ),
            checkLessonId = "mid7-tafakkor-21-choosing-friend",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 147,
            title = "تفکر هفتم ۲۲: تضاد و اختلاف نظرهای خود را چگونه مدیریت کنیم؟",
            subtitle = "پایه هفتم متوسطه اول — تفکر و سبک زندگی",
            lessonIds = listOf(
                "mid7-tafakkor-22-managing-disagreement"
            ),
            checkLessonId = "mid7-tafakkor-22-managing-disagreement",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 148,
            title = "تفکر هفتم ۲۳: مهار عصبانیت",
            subtitle = "پایه هفتم متوسطه اول — تفکر و سبک زندگی",
            lessonIds = listOf(
                "mid7-tafakkor-23-anger-management"
            ),
            checkLessonId = "mid7-tafakkor-23-anger-management",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 149,
            title = "تفکر هفتم ۲۴: حکایت راستی",
            subtitle = "پایه هفتم متوسطه اول — تفکر و سبک زندگی",
            lessonIds = listOf(
                "mid7-tafakkor-24-truth-story"
            ),
            checkLessonId = "mid7-tafakkor-24-truth-story",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 150,
            title = "تفکر هفتم ۲۵: بزرگ‌مردان کوچک",
            subtitle = "پایه هفتم متوسطه اول — تفکر و سبک زندگی",
            lessonIds = listOf(
                "mid7-tafakkor-25-little-great-men"
            ),
            checkLessonId = "mid7-tafakkor-25-little-great-men",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 151,
            title = "تفکر هفتم ۲۶: چرا این تلفن زنگ نمی‌زند؟",
            subtitle = "پایه هفتم متوسطه اول — تفکر و سبک زندگی",
            lessonIds = listOf(
                "mid7-tafakkor-26-why-phone-not-ringing"
            ),
            checkLessonId = "mid7-tafakkor-26-why-phone-not-ringing",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 152,
            title = "تفکر هفتم ۲۷: دیوار شیشه‌ای",
            subtitle = "پایه هفتم متوسطه اول — تفکر و سبک زندگی",
            lessonIds = listOf(
                "mid7-tafakkor-27-glass-wall"
            ),
            checkLessonId = "mid7-tafakkor-27-glass-wall",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 153,
            title = "تفکر هفتم ۲۸: مسموم کردن سرچشمه",
            subtitle = "پایه هفتم متوسطه اول — تفکر و سبک زندگی",
            lessonIds = listOf(
                "mid7-tafakkor-28-poisoning-source"
            ),
            checkLessonId = "mid7-tafakkor-28-poisoning-source",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 154,
            title = "تفکر هفتم ۲۹: ناظم و منظوم",
            subtitle = "پایه هفتم متوسطه اول — تفکر و سبک زندگی",
            lessonIds = listOf(
                "mid7-tafakkor-29-nazem-manzum"
            ),
            checkLessonId = "mid7-tafakkor-29-nazem-manzum",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 155,
            title = "تفکر هفتم ۳۰: مهارت قاطعیت و جرئت‌ورزی — از صفر",
            subtitle = "پایه هفتم متوسطه اول — تفکر و سبک زندگی",
            lessonIds = listOf(
                "mid7-tafakkor-30-qateiat"
            ),
            checkLessonId = "mid7-tafakkor-30-qateiat",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 156,
            title = "تفکر هفتم ۳۱: سبک زندگی و عوامل مؤثر بر آن — از صفر",
            subtitle = "پایه هفتم متوسطه اول — تفکر و سبک زندگی",
            lessonIds = listOf(
                "mid7-tafakkor-31-sabk-zendegi"
            ),
            checkLessonId = "mid7-tafakkor-31-sabk-zendegi",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 157,
            title = "تفکر هفتم ۳۲: شاد زیستن — از صفر",
            subtitle = "پایه هفتم متوسطه اول — تفکر و سبک زندگی",
            lessonIds = listOf(
                "mid7-tafakkor-32-shad-zistan"
            ),
            checkLessonId = "mid7-tafakkor-32-shad-zistan",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 158,
            title = "تفکر هفتم ۳۳: فعالیت‌های اختیاری و خودارزیابی نوبت اول و دوم — از صفر",
            subtitle = "پایه هفتم متوسطه اول — تفکر و سبک زندگی",
            lessonIds = listOf(
                "mid7-tafakkor-33-ekhtiari-khodarzyabi"
            ),
            checkLessonId = "mid7-tafakkor-33-ekhtiari-khodarzyabi",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 159,
            title = "فرهنگ و هنر هفتم ۱: طراحی — مشاهده — از صفر",
            subtitle = "پایه هفتم متوسطه اول — فرهنگ و هنر",
            lessonIds = listOf(
                "mid7-honar-01-moshahede"
            ),
            checkLessonId = "mid7-honar-01-moshahede",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 160,
            title = "فرهنگ و هنر هفتم ۲: طراحی — تنوع طرح‌ها — از صفر",
            subtitle = "پایه هفتم متوسطه اول — فرهنگ و هنر",
            lessonIds = listOf(
                "mid7-honar-02-tanavoe-tarhha"
            ),
            checkLessonId = "mid7-honar-02-tanavoe-tarhha",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 161,
            title = "فرهنگ و هنر هفتم ۳: طراحی — الفبای تصویر — از صفر",
            subtitle = "پایه هفتم متوسطه اول — فرهنگ و هنر",
            lessonIds = listOf(
                "mid7-honar-03-alefbaye-tasvir"
            ),
            checkLessonId = "mid7-honar-03-alefbaye-tasvir",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 162,
            title = "فرهنگ و هنر هفتم ۴: طراحی — نظم — از صفر",
            subtitle = "پایه هفتم متوسطه اول — فرهنگ و هنر",
            lessonIds = listOf(
                "mid7-honar-04-nazm"
            ),
            checkLessonId = "mid7-honar-04-nazm",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 163,
            title = "فرهنگ و هنر هفتم ۵: نقاشی — نقاشی از پدیده‌های طبیعت — از صفر",
            subtitle = "پایه هفتم متوسطه اول — فرهنگ و هنر",
            lessonIds = listOf(
                "mid7-honar-05-tabiat"
            ),
            checkLessonId = "mid7-honar-05-tabiat",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 164,
            title = "فرهنگ و هنر هفتم ۶: نقاشی — نقش گل‌ها — از صفر",
            subtitle = "پایه هفتم متوسطه اول — فرهنگ و هنر",
            lessonIds = listOf(
                "mid7-honar-06-naghshe-golha"
            ),
            checkLessonId = "mid7-honar-06-naghshe-golha",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 165,
            title = "فرهنگ و هنر هفتم ۷: نقاشی — بافت در نقاشی — از صفر",
            subtitle = "پایه هفتم متوسطه اول — فرهنگ و هنر",
            lessonIds = listOf(
                "mid7-honar-07-baft"
            ),
            checkLessonId = "mid7-honar-07-baft",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 166,
            title = "فرهنگ و هنر هفتم ۸: نقاشی — نقاشی ایرانی — از صفر",
            subtitle = "پایه هفتم متوسطه اول — فرهنگ و هنر",
            lessonIds = listOf(
                "mid7-honar-08-naghashi-irani"
            ),
            checkLessonId = "mid7-honar-08-naghashi-irani",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 167,
            title = "فرهنگ و هنر هفتم ۹: طراحی سنتی چیست؟ — از صفر",
            subtitle = "پایه هفتم متوسطه اول — فرهنگ و هنر",
            lessonIds = listOf(
                "mid7-honar-09-tarahi-sonnati"
            ),
            checkLessonId = "mid7-honar-09-tarahi-sonnati",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 168,
            title = "فرهنگ و هنر هفتم ۱۰: وزن در موسیقی — از صفر",
            subtitle = "پایه هفتم متوسطه اول — فرهنگ و هنر",
            lessonIds = listOf(
                "mid7-honar-10-vazn"
            ),
            checkLessonId = "mid7-honar-10-vazn",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 169,
            title = "فرهنگ و هنر هفتم ۱۱: ملودی یعنی نوا — از صفر",
            subtitle = "پایه هفتم متوسطه اول — فرهنگ و هنر",
            lessonIds = listOf(
                "mid7-honar-11-melodi"
            ),
            checkLessonId = "mid7-honar-11-melodi",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 170,
            title = "فرهنگ و هنر هفتم ۱۲: رنگ صوتی — از صفر",
            subtitle = "پایه هفتم متوسطه اول — فرهنگ و هنر",
            lessonIds = listOf(
                "mid7-honar-12-rang-souti"
            ),
            checkLessonId = "mid7-honar-12-rang-souti",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 171,
            title = "فرهنگ و هنر هفتم ۱۳: شکل یعنی فرم در موسیقی — از صفر",
            subtitle = "پایه هفتم متوسطه اول — فرهنگ و هنر",
            lessonIds = listOf(
                "mid7-honar-13-shekl-form"
            ),
            checkLessonId = "mid7-honar-13-shekl-form",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 172,
            title = "فرهنگ و هنر هفتم ۱۴: بافت در موسیقی — از صفر",
            subtitle = "پایه هفتم متوسطه اول — فرهنگ و هنر",
            lessonIds = listOf(
                "mid7-honar-14-baft"
            ),
            checkLessonId = "mid7-honar-14-baft",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 173,
            title = "فرهنگ و هنر هفتم ۱۵: آشنایی با نقش‌های هندسی — از صفر",
            subtitle = "پایه هفتم متوسطه اول — فرهنگ و هنر",
            lessonIds = listOf(
                "mid7-honar-15-naghsh-hendesi"
            ),
            checkLessonId = "mid7-honar-15-naghsh-hendesi",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 174,
            title = "فرهنگ و هنر هفتم ۱۶: طراحی گره — از صفر",
            subtitle = "پایه هفتم متوسطه اول — فرهنگ و هنر",
            lessonIds = listOf(
                "mid7-honar-16-tarahi-gereh"
            ),
            checkLessonId = "mid7-honar-16-tarahi-gereh",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 175,
            title = "فرهنگ و هنر هفتم ۱۷: زیراندازها — از صفر",
            subtitle = "پایه هفتم متوسطه اول — فرهنگ و هنر",
            lessonIds = listOf(
                "mid7-honar-17-zirandazha"
            ),
            checkLessonId = "mid7-honar-17-zirandazha",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 176,
            title = "فرهنگ و هنر هفتم ۱۸: ابزار، وسایل و مواد گلیم‌بافی — از صفر",
            subtitle = "پایه هفتم متوسطه اول — فرهنگ و هنر",
            lessonIds = listOf(
                "mid7-honar-18-abzar-gelim"
            ),
            checkLessonId = "mid7-honar-18-abzar-gelim",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 177,
            title = "فرهنگ و هنر هفتم ۱۹: بافت گلیم نقش‌دار — از صفر",
            subtitle = "پایه هفتم متوسطه اول — فرهنگ و هنر",
            lessonIds = listOf(
                "mid7-honar-19-baft-gelim"
            ),
            checkLessonId = "mid7-honar-19-baft-gelim",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 178,
            title = "فرهنگ و هنر هفتم ۲۰: آشنایی با سفالگری — از صفر",
            subtitle = "پایه هفتم متوسطه اول — فرهنگ و هنر",
            lessonIds = listOf(
                "mid7-honar-20-sofal"
            ),
            checkLessonId = "mid7-honar-20-sofal",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 179,
            title = "فرهنگ و هنر هفتم ۲۱: ساختن حجم‌های گلی با روش فشاری — از صفر",
            subtitle = "پایه هفتم متوسطه اول — فرهنگ و هنر",
            lessonIds = listOf(
                "mid7-honar-21-hajm-feshari"
            ),
            checkLessonId = "mid7-honar-21-hajm-feshari",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 180,
            title = "فرهنگ و هنر هفتم ۲۲: ساختن حجم‌های سفالی با روش فتیله‌ای — از صفر",
            subtitle = "پایه هفتم متوسطه اول — فرهنگ و هنر",
            lessonIds = listOf(
                "mid7-honar-22-hajm-fatilei"
            ),
            checkLessonId = "mid7-honar-22-hajm-fatilei",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 181,
            title = "فرهنگ و هنر هفتم ۲۳: منظره‌یاب — از صفر",
            subtitle = "پایه هفتم متوسطه اول — فرهنگ و هنر",
            lessonIds = listOf(
                "mid7-honar-23-manzarehyab"
            ),
            checkLessonId = "mid7-honar-23-manzarehyab",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 182,
            title = "فرهنگ و هنر هفتم ۲۴: دوربین‌های تمام خودکار — از صفر",
            subtitle = "پایه هفتم متوسطه اول — فرهنگ و هنر",
            lessonIds = listOf(
                "mid7-honar-24-durbin-khodkar"
            ),
            checkLessonId = "mid7-honar-24-durbin-khodkar",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 183,
            title = "فرهنگ و هنر هفتم ۲۵: نقطه تأکید — از صفر",
            subtitle = "پایه هفتم متوسطه اول — فرهنگ و هنر",
            lessonIds = listOf(
                "mid7-honar-25-noghte-takid"
            ),
            checkLessonId = "mid7-honar-25-noghte-takid",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 184,
            title = "فرهنگ و هنر هفتم ۲۶: نور در عکاسی — از صفر",
            subtitle = "پایه هفتم متوسطه اول — فرهنگ و هنر",
            lessonIds = listOf(
                "mid7-honar-26-nur"
            ),
            checkLessonId = "mid7-honar-26-nur",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 185,
            title = "فرهنگ و هنر هفتم ۲۷: آشنایی با اصطلاحات و ابزارهای خوشنویسی — از صفر",
            subtitle = "پایه هفتم متوسطه اول — فرهنگ و هنر",
            lessonIds = listOf(
                "mid7-honar-27-khoshnevisi-abzar"
            ),
            checkLessonId = "mid7-honar-27-khoshnevisi-abzar",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 186,
            title = "فرهنگ و هنر هفتم ۲۸: معرفی خط کوفی — از صفر",
            subtitle = "پایه هفتم متوسطه اول — فرهنگ و هنر",
            lessonIds = listOf(
                "mid7-honar-28-khat-kufi"
            ),
            checkLessonId = "mid7-honar-28-khat-kufi",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 187,
            title = "فرهنگ و هنر هفتم ۲۹: معرفی خط نسخ — از صفر",
            subtitle = "پایه هفتم متوسطه اول — فرهنگ و هنر",
            lessonIds = listOf(
                "mid7-honar-29-khat-naskh"
            ),
            checkLessonId = "mid7-honar-29-khat-naskh",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 188,
            title = "فرهنگ و هنر هفتم ۳۰: معرفی خط ثلث — از صفر",
            subtitle = "پایه هفتم متوسطه اول — فرهنگ و هنر",
            lessonIds = listOf(
                "mid7-honar-30-khat-sols"
            ),
            checkLessonId = "mid7-honar-30-khat-sols",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 189,
            title = "فرهنگ و هنر هفتم ۳۱: معرفی خط محقق — از صفر",
            subtitle = "پایه هفتم متوسطه اول — فرهنگ و هنر",
            lessonIds = listOf(
                "mid7-honar-31-khat-mohaghagh"
            ),
            checkLessonId = "mid7-honar-31-khat-mohaghagh",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 190,
            title = "فرهنگ و هنر هفتم ۳۲: گفت‌وگو کنید — از صفر",
            subtitle = "پایه هفتم متوسطه اول — فرهنگ و هنر",
            lessonIds = listOf(
                "mid7-honar-32-goftegoo-konid"
            ),
            checkLessonId = "mid7-honar-32-goftegoo-konid",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 191,
            title = "فرهنگ و هنر هفتم ۳۳: گفت‌وگوی نمایشی — از صفر",
            subtitle = "پایه هفتم متوسطه اول — فرهنگ و هنر",
            lessonIds = listOf(
                "mid7-honar-33-goftegoo-namayeshi"
            ),
            checkLessonId = "mid7-honar-33-goftegoo-namayeshi",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 192,
            title = "فرهنگ و هنر هفتم ۳۴: شخصیت و موقعیت در نمایش — از صفر",
            subtitle = "پایه هفتم متوسطه اول — فرهنگ و هنر",
            lessonIds = listOf(
                "mid7-honar-34-shakhsiyat-mogheiyat"
            ),
            checkLessonId = "mid7-honar-34-shakhsiyat-mogheiyat",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 193,
            title = "فرهنگ و هنر هفتم ۳۵: ماجراهای نمایشی — از صفر",
            subtitle = "پایه هفتم متوسطه اول — فرهنگ و هنر",
            lessonIds = listOf(
                "mid7-honar-35-majraha-namayeshi"
            ),
            checkLessonId = "mid7-honar-35-majraha-namayeshi",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 194,
            title = "فارسی هشتم ۱: پیش از این‌ها — خدا نزدیک است، نه دور",
            subtitle = "پایه هشتم متوسطه اول — فارسی",
            lessonIds = listOf(
                "mid8-farsi-01-pish-az-inha"
            ),
            checkLessonId = "mid8-farsi-01-pish-az-inha",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 195,
            title = "فارسی هشتم ۲: خوب جهان را ببین! صورتگر ماهر",
            subtitle = "پایه هشتم متوسطه اول — فارسی",
            lessonIds = listOf(
                "mid8-farsi-02-khub-jahan-ra-bebin"
            ),
            checkLessonId = "mid8-farsi-02-khub-jahan-ra-bebin",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 196,
            title = "فارسی هشتم ۳: ارمغان ایران",
            subtitle = "پایه هشتم متوسطه اول — فارسی",
            lessonIds = listOf(
                "mid8-farsi-03-armaghan-iran"
            ),
            checkLessonId = "mid8-farsi-03-armaghan-iran",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 197,
            title = "فارسی هشتم ۴: سفر شکفتن",
            subtitle = "پایه هشتم متوسطه اول — فارسی",
            lessonIds = listOf(
                "mid8-farsi-04-safar-shekoftan"
            ),
            checkLessonId = "mid8-farsi-04-safar-shekoftan",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 198,
            title = "فارسی هشتم ۵: درس آزاد — ادبیات بومی ۱",
            subtitle = "پایه هشتم متوسطه اول — فارسی",
            lessonIds = listOf(
                "mid8-farsi-05-dars-azad"
            ),
            checkLessonId = "mid8-farsi-05-dars-azad",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 199,
            title = "فارسی هشتم ۶: راه نیک‌بختی",
            subtitle = "پایه هشتم متوسطه اول — فارسی",
            lessonIds = listOf(
                "mid8-farsi-06-rah-nikbakhti"
            ),
            checkLessonId = "mid8-farsi-06-rah-nikbakhti",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 200,
            title = "فارسی هشتم ۷: آداب نیکان",
            subtitle = "پایه هشتم متوسطه اول — فارسی",
            lessonIds = listOf(
                "mid8-farsi-07-adab-nikan"
            ),
            checkLessonId = "mid8-farsi-07-adab-nikan",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 201,
            title = "فارسی هشتم ۸: آزادگی",
            subtitle = "پایه هشتم متوسطه اول — فارسی",
            lessonIds = listOf(
                "mid8-farsi-08-azadegi"
            ),
            checkLessonId = "mid8-farsi-08-azadegi",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 202,
            title = "فارسی هشتم ۹: نوجوان باهوش، آشپززاده وزیر، گریه امیر",
            subtitle = "پایه هشتم متوسطه اول — فارسی",
            lessonIds = listOf(
                "mid8-farsi-09-nojavan-bahush"
            ),
            checkLessonId = "mid8-farsi-09-nojavan-bahush",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 203,
            title = "فارسی هشتم ۱۰: قلم سحرآمیز، دو نامه",
            subtitle = "پایه هشتم متوسطه اول — فارسی",
            lessonIds = listOf(
                "mid8-farsi-10-qalam-sehramiz"
            ),
            checkLessonId = "mid8-farsi-10-qalam-sehramiz",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 204,
            title = "فارسی هشتم ۱۱: پرچم‌داران — از صفر",
            subtitle = "پایه هشتم متوسطه اول — فارسی",
            lessonIds = listOf(
                "mid8-farsi-11-parchamdaran"
            ),
            checkLessonId = "mid8-farsi-11-parchamdaran",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 205,
            title = "فارسی هشتم ۱۲: شیر حق — از صفر",
            subtitle = "پایه هشتم متوسطه اول — فارسی",
            lessonIds = listOf(
                "mid8-farsi-12-shir-hagh"
            ),
            checkLessonId = "mid8-farsi-12-shir-hagh",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 206,
            title = "فارسی هشتم ۱۳: ادبیات انقلاب — از صفر",
            subtitle = "پایه هشتم متوسطه اول — فارسی",
            lessonIds = listOf(
                "mid8-farsi-13-adabiat-enghelab"
            ),
            checkLessonId = "mid8-farsi-13-adabiat-enghelab",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 207,
            title = "فارسی هشتم ۱۴: یاد حسین (ع) — از صفر",
            subtitle = "پایه هشتم متوسطه اول — فارسی",
            lessonIds = listOf(
                "mid8-farsi-14-yad-hossein"
            ),
            checkLessonId = "mid8-farsi-14-yad-hossein",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 208,
            title = "فارسی هشتم ۱۵: درس آزاد، ادبیات بومی ۲ — از صفر",
            subtitle = "پایه هشتم متوسطه اول — فارسی",
            lessonIds = listOf(
                "mid8-farsi-15-dars-azad"
            ),
            checkLessonId = "mid8-farsi-15-dars-azad",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 209,
            title = "فارسی هشتم ۱۶: پرنده آزادی، کودکان سنگ — از صفر",
            subtitle = "پایه هشتم متوسطه اول — فارسی",
            lessonIds = listOf(
                "mid8-farsi-16-parande-azadi"
            ),
            checkLessonId = "mid8-farsi-16-parande-azadi",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 210,
            title = "فارسی هشتم ۱۷: راه خوشبختی — از صفر",
            subtitle = "پایه هشتم متوسطه اول — فارسی",
            lessonIds = listOf(
                "mid8-farsi-17-rah-khoshbakhti"
            ),
            checkLessonId = "mid8-farsi-17-rah-khoshbakhti",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 211,
            title = "نگارش هشتم ۱: پس از تفکر و طراحی نقشه ذهنی، بنویسیم — از صفر",
            subtitle = "پایه هشتم متوسطه اول — نگارش",
            lessonIds = listOf(
                "mid8-negaresh-01-naghshe-zehni"
            ),
            checkLessonId = "mid8-negaresh-01-naghshe-zehni",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 212,
            title = "نگارش هشتم ۲: افکار و گفتارمان را بنویسیم — از صفر",
            subtitle = "پایه هشتم متوسطه اول — نگارش",
            lessonIds = listOf(
                "mid8-negaresh-02-afkar-ghoftar"
            ),
            checkLessonId = "mid8-negaresh-02-afkar-ghoftar",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 213,
            title = "نگارش هشتم ۳: ببینیم و بنویسیم — از صفر",
            subtitle = "پایه هشتم متوسطه اول — نگارش",
            lessonIds = listOf(
                "mid8-negaresh-03-bebinim-benevisim"
            ),
            checkLessonId = "mid8-negaresh-03-bebinim-benevisim",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 214,
            title = "نگارش هشتم ۴: گوش بدهیم و بنویسیم — از صفر",
            subtitle = "پایه هشتم متوسطه اول — نگارش",
            lessonIds = listOf(
                "mid8-negaresh-04-listen-write"
            ),
            checkLessonId = "mid8-negaresh-04-listen-write",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 215,
            title = "نگارش هشتم ۵: لمس کنیم، ببوییم، بچشیم و بنویسیم — از صفر",
            subtitle = "پایه هشتم متوسطه اول — نگارش",
            lessonIds = listOf(
                "mid8-negaresh-05-senses-write"
            ),
            checkLessonId = "mid8-negaresh-05-senses-write",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 216,
            title = "نگارش هشتم ۶: با سنجش و مقایسه، آسان‌تر بنویسیم — از صفر",
            subtitle = "پایه هشتم متوسطه اول — نگارش",
            lessonIds = listOf(
                "mid8-negaresh-06-compare-write"
            ),
            checkLessonId = "mid8-negaresh-06-compare-write",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 217,
            title = "نگارش هشتم ۷: با جانشین‌سازی، راحت‌تر بنویسیم — از صفر",
            subtitle = "پایه هشتم متوسطه اول — نگارش",
            lessonIds = listOf(
                "mid8-negaresh-07-substitute-write"
            ),
            checkLessonId = "mid8-negaresh-07-substitute-write",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 218,
            title = "نگارش هشتم ۸: دگرگونه ببینیم و گونه‌گون بنویسیم — از صفر",
            subtitle = "پایه هشتم متوسطه اول — نگارش",
            lessonIds = listOf(
                "mid8-negaresh-08-different-write"
            ),
            checkLessonId = "mid8-negaresh-08-different-write",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 219,
            title = "ریاضی هشتم ۱: عددهای صحیح و گویا — از صفر",
            subtitle = "پایه هشتم متوسطه اول — ریاضی",
            lessonIds = listOf(
                "mid8-math-01-integers-rationals"
            ),
            checkLessonId = "mid8-math-01-integers-rationals",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 220,
            title = "ریاضی هشتم ۲: عددهای اول — از صفر",
            subtitle = "پایه هشتم متوسطه اول — ریاضی",
            lessonIds = listOf(
                "mid8-math-02-prime-numbers"
            ),
            checkLessonId = "mid8-math-02-prime-numbers",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 221,
            title = "ریاضی هشتم ۳: چندضلعی‌ها — از صفر",
            subtitle = "پایه هشتم متوسطه اول — ریاضی",
            lessonIds = listOf(
                "mid8-math-03-polygons"
            ),
            checkLessonId = "mid8-math-03-polygons",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 222,
            title = "ریاضی هشتم ۴: جبر و معادله — از صفر",
            subtitle = "پایه هشتم متوسطه اول — ریاضی",
            lessonIds = listOf(
                "mid8-math-04-algebra-equation"
            ),
            checkLessonId = "mid8-math-04-algebra-equation",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 223,
            title = "ریاضی هشتم ۵: بردار و مختصات — از صفر",
            subtitle = "پایه هشتم متوسطه اول — ریاضی",
            lessonIds = listOf(
                "mid8-math-05-vector-coordinate"
            ),
            checkLessonId = "mid8-math-05-vector-coordinate",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 224,
            title = "ریاضی هشتم فصل ۶: مثلث — از صفر",
            subtitle = "پایه هشتم متوسطه اول — ریاضی",
            lessonIds = listOf(
                "mid8-math-06-triangle"
            ),
            checkLessonId = "mid8-math-06-triangle",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 225,
            title = "ریاضی هشتم فصل ۷: توان و جذر — از صفر",
            subtitle = "پایه هشتم متوسطه اول — ریاضی",
            lessonIds = listOf(
                "mid8-math-07-power-root"
            ),
            checkLessonId = "mid8-math-07-power-root",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 226,
            title = "ریاضی هشتم فصل ۸: آمار و احتمال — از صفر",
            subtitle = "پایه هشتم متوسطه اول — ریاضی",
            lessonIds = listOf(
                "mid8-math-08-stats-probability"
            ),
            checkLessonId = "mid8-math-08-stats-probability",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 227,
            title = "ریاضی هشتم فصل ۹: دایره — از صفر",
            subtitle = "پایه هشتم متوسطه اول — ریاضی",
            lessonIds = listOf(
                "mid8-math-09-circle"
            ),
            checkLessonId = "mid8-math-09-circle",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 228,
            title = "علوم هشتم فصل ۱: مخلوط و جداسازی مواد — از صفر",
            subtitle = "پایه هشتم متوسطه اول — علوم تجربی",
            lessonIds = listOf(
                "mid8-science-01-mixture-separation"
            ),
            checkLessonId = "mid8-science-01-mixture-separation",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 229,
            title = "علوم هشتم فصل ۲: تغییرهای شیمیایی در خدمت زندگی — از صفر",
            subtitle = "پایه هشتم متوسطه اول — علوم تجربی",
            lessonIds = listOf(
                "mid8-science-02-chemical-changes"
            ),
            checkLessonId = "mid8-science-02-chemical-changes",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 230,
            title = "علوم هشتم فصل ۳: از درون اتم چه خبر — از صفر",
            subtitle = "پایه هشتم متوسطه اول — علوم تجربی",
            lessonIds = listOf(
                "mid8-science-03-inside-atom"
            ),
            checkLessonId = "mid8-science-03-inside-atom",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 231,
            title = "علوم هشتم فصل ۴: تنظیم عصبی — از صفر",
            subtitle = "پایه هشتم متوسطه اول — علوم تجربی",
            lessonIds = listOf(
                "mid8-science-04-nervous-system"
            ),
            checkLessonId = "mid8-science-04-nervous-system",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 232,
            title = "علوم هشتم فصل ۵: حس و حرکت — از صفر",
            subtitle = "پایه هشتم متوسطه اول — علوم تجربی",
            lessonIds = listOf(
                "mid8-science-05-sense-movement"
            ),
            checkLessonId = "mid8-science-05-sense-movement",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 233,
            title = "علوم هشتم فصل ۶: تنظیم هورمونی — از صفر",
            subtitle = "پایه هشتم متوسطه اول — علوم تجربی",
            lessonIds = listOf(
                "mid8-science-06-hormonal-regulation"
            ),
            checkLessonId = "mid8-science-06-hormonal-regulation",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 234,
            title = "علوم هشتم فصل ۷: الفبای زیست‌فناوری — از صفر",
            subtitle = "پایه هشتم متوسطه اول — علوم تجربی",
            lessonIds = listOf(
                "mid8-science-07-biotech"
            ),
            checkLessonId = "mid8-science-07-biotech",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 235,
            title = "علوم هشتم فصل ۸: تولید مثل در جانداران — از صفر",
            subtitle = "پایه هشتم متوسطه اول — علوم تجربی",
            lessonIds = listOf(
                "mid8-science-08-reproduction"
            ),
            checkLessonId = "mid8-science-08-reproduction",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 236,
            title = "علوم هشتم فصل ۹: الکتریسیته — از صفر",
            subtitle = "پایه هشتم متوسطه اول — علوم تجربی",
            lessonIds = listOf(
                "mid8-science-09-electricity"
            ),
            checkLessonId = "mid8-science-09-electricity",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 237,
            title = "علوم هشتم فصل ۱۰: مغناطیس — از صفر",
            subtitle = "پایه هشتم متوسطه اول — علوم تجربی",
            lessonIds = listOf(
                "mid8-science-10-magnetism"
            ),
            checkLessonId = "mid8-science-10-magnetism",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 238,
            title = "علوم هشتم فصل ۱۱: کانی‌ها — از صفر",
            subtitle = "پایه هشتم متوسطه اول — علوم تجربی",
            lessonIds = listOf(
                "mid8-science-11-minerals"
            ),
            checkLessonId = "mid8-science-11-minerals",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 239,
            title = "علوم هشتم فصل ۱۲: سنگ‌ها — از صفر",
            subtitle = "پایه هشتم متوسطه اول — علوم تجربی",
            lessonIds = listOf(
                "mid8-science-12-rocks"
            ),
            checkLessonId = "mid8-science-12-rocks",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 240,
            title = "علوم هشتم فصل ۱۳: هوازدگی — از صفر",
            subtitle = "پایه هشتم متوسطه اول — علوم تجربی",
            lessonIds = listOf(
                "mid8-science-13-weathering"
            ),
            checkLessonId = "mid8-science-13-weathering",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 241,
            title = "علوم هشتم فصل ۱۴: نور و ویژگی‌های آن — از صفر",
            subtitle = "پایه هشتم متوسطه اول — علوم تجربی",
            lessonIds = listOf(
                "mid8-science-14-light"
            ),
            checkLessonId = "mid8-science-14-light",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 242,
            title = "علوم هشتم فصل ۱۵: شکست نور — از صفر",
            subtitle = "پایه هشتم متوسطه اول — علوم تجربی",
            lessonIds = listOf(
                "mid8-science-15-refraction"
            ),
            checkLessonId = "mid8-science-15-refraction",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 243,
            title = "عربی هشتم درس ۱: مُراجَعَةُ دُروسِ الصَّفِّ السّابِعِ — مرور درس‌های کلاس هفتم",
            subtitle = "پایه هشتم متوسطه اول — عربی",
            lessonIds = listOf(
                "mid8-arabic-01-review-7"
            ),
            checkLessonId = "mid8-arabic-01-review-7",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 244,
            title = "عربی هشتم درس ۲: اهمیت زبان عربی — اسم اشاره",
            subtitle = "پایه هشتم متوسطه اول — عربی",
            lessonIds = listOf(
                "mid8-arabic-02-importance-arabic"
            ),
            checkLessonId = "mid8-arabic-02-importance-arabic",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 245,
            title = "عربی هشتم درس ۳: شغل تو در آینده — عددها و ساعت",
            subtitle = "پایه هشتم متوسطه اول — عربی",
            lessonIds = listOf(
                "mid8-arabic-03-future-job"
            ),
            checkLessonId = "mid8-arabic-03-future-job",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 246,
            title = "عربی هشتم درس ۴: تجربه تازه — فعل مضارع",
            subtitle = "پایه هشتم متوسطه اول — عربی",
            lessonIds = listOf(
                "mid8-arabic-04-new-experience"
            ),
            checkLessonId = "mid8-arabic-04-new-experience",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 247,
            title = "عربی هشتم درس ۵: دوستی — ضمیرها و حرف «ما»",
            subtitle = "پایه هشتم متوسطه اول — عربی",
            lessonIds = listOf(
                "mid8-arabic-05-friendship"
            ),
            checkLessonId = "mid8-arabic-05-friendship",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 248,
            title = "عربی هشتم درس ۶: در سفر — مضارع دوم شخص جمع",
            subtitle = "پایه هشتم متوسطه اول — عربی",
            lessonIds = listOf(
                "mid8-arabic-06-travel"
            ),
            checkLessonId = "mid8-arabic-06-travel",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 249,
            title = "عربی هشتم درس ۷: زمین خدا پهناور است — «متی»",
            subtitle = "پایه هشتم متوسطه اول — عربی",
            lessonIds = listOf(
                "mid8-arabic-07-earth-wide"
            ),
            checkLessonId = "mid8-arabic-07-earth-wide",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 250,
            title = "عربی هشتم درس ۸: تکیه بر خود — مضارع سوم شخص جمع",
            subtitle = "پایه هشتم متوسطه اول — عربی",
            lessonIds = listOf(
                "mid8-arabic-08-self-reliance"
            ),
            checkLessonId = "mid8-arabic-08-self-reliance",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 251,
            title = "عربی هشتم درس ۹: سفر علمی — «کیف»",
            subtitle = "پایه هشتم متوسطه اول — عربی",
            lessonIds = listOf(
                "mid8-arabic-09-science-trip"
            ),
            checkLessonId = "mid8-arabic-09-science-trip",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 252,
            title = "عربی هشتم درس ۱۰: حکمت‌ها — عددهای ترتیبی",
            subtitle = "پایه هشتم متوسطه اول — عربی",
            lessonIds = listOf(
                "mid8-arabic-10-wisdoms"
            ),
            checkLessonId = "mid8-arabic-10-wisdoms",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 253,
            title = "انگلیسی هشتم درس ۱: ملیت من — My Nationality (ملیت من)",
            subtitle = "پایه هشتم متوسطه اول — زبان انگلیسی",
            lessonIds = listOf(
                "mid8-english-01-nationality"
            ),
            checkLessonId = "mid8-english-01-nationality",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 254,
            title = "انگلیسی هشتم ۲: My Week یعنی هفته من — روزهای هفته و کارهای هر روز",
            subtitle = "پایه هشتم متوسطه اول — زبان انگلیسی",
            lessonIds = listOf(
                "mid8-english-02-my-week"
            ),
            checkLessonId = "mid8-english-02-my-week",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 255,
            title = "انگلیسی هشتم ۳: My Abilities یعنی توانایی‌های من — can و cannot",
            subtitle = "پایه هشتم متوسطه اول — زبان انگلیسی",
            lessonIds = listOf(
                "mid8-english-03-my-abilities"
            ),
            checkLessonId = "mid8-english-03-my-abilities",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 256,
            title = "انگلیسی هشتم ۴: My Health یعنی سلامت من — وقتی مریض می‌شوی چه بگویی؟",
            subtitle = "پایه هشتم متوسطه اول — زبان انگلیسی",
            lessonIds = listOf(
                "mid8-english-04-my-health"
            ),
            checkLessonId = "mid8-english-04-my-health",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 257,
            title = "انگلیسی هشتم ۵: My City یعنی شهر من — جاهای شهر و آدرس دادن",
            subtitle = "پایه هشتم متوسطه اول — زبان انگلیسی",
            lessonIds = listOf(
                "mid8-english-05-my-city"
            ),
            checkLessonId = "mid8-english-05-my-city",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 258,
            title = "انگلیسی هشتم ۶: My Village یعنی روستای من — زندگی روستا و There is و There are",
            subtitle = "پایه هشتم متوسطه اول — زبان انگلیسی",
            lessonIds = listOf(
                "mid8-english-06-my-village"
            ),
            checkLessonId = "mid8-english-06-my-village",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 259,
            title = "انگلیسی هشتم ۷: My Hobbies یعنی سرگرمی‌های من — دوست داشتن با like و ing",
            subtitle = "پایه هشتم متوسطه اول — زبان انگلیسی",
            lessonIds = listOf(
                "mid8-english-07-my-hobbies"
            ),
            checkLessonId = "mid8-english-07-my-hobbies",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 260,
            title = "مطالعات هشتم ۱: تعاون یک — همکاری و یاری رساندن به یکدیگر",
            subtitle = "پایه هشتم متوسطه اول — مطالعات اجتماعی",
            lessonIds = listOf(
                "mid8-social-01-taavon-1"
            ),
            checkLessonId = "mid8-social-01-taavon-1",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 261,
            title = "مطالعات هشتم ۲: تعاون دو — انفاق، وقف و شرکت‌های تعاونی",
            subtitle = "پایه هشتم متوسطه اول — مطالعات اجتماعی",
            lessonIds = listOf(
                "mid8-social-02-taavon-2"
            ),
            checkLessonId = "mid8-social-02-taavon-2",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 262,
            title = "مطالعات هشتم ۳: ساختار و تشکیلات دولت — دولت چگونه اداره می‌شود؟",
            subtitle = "پایه هشتم متوسطه اول — مطالعات اجتماعی",
            lessonIds = listOf(
                "mid8-social-03-government-structure"
            ),
            checkLessonId = "mid8-social-03-government-structure",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 263,
            title = "مطالعات هشتم ۴: وظایف دولت — دولت برای مردم چه کارهایی می‌کند؟",
            subtitle = "پایه هشتم متوسطه اول — مطالعات اجتماعی",
            lessonIds = listOf(
                "mid8-social-04-government-duties"
            ),
            checkLessonId = "mid8-social-04-government-duties",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 264,
            title = "مطالعات هشتم ۵: آسیب‌های اجتماعی و پیشگیری از آن‌ها — از صفر",
            subtitle = "پایه هشتم متوسطه اول — مطالعات اجتماعی",
            lessonIds = listOf(
                "mid8-social-05-asib-haye-ejtemaei"
            ),
            checkLessonId = "mid8-social-05-asib-haye-ejtemaei",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 265,
            title = "مطالعات هشتم ۶: قوه قضائیه — از صفر",
            subtitle = "پایه هشتم متوسطه اول — مطالعات اجتماعی",
            lessonIds = listOf(
                "mid8-social-06-ghovveh-ghazaeih"
            ),
            checkLessonId = "mid8-social-06-ghovveh-ghazaeih",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 266,
            title = "مطالعات هشتم ۷: ارتباط و رسانه — از صفر",
            subtitle = "پایه هشتم متوسطه اول — مطالعات اجتماعی",
            lessonIds = listOf(
                "mid8-social-07-ertebat-va-resaneh"
            ),
            checkLessonId = "mid8-social-07-ertebat-va-resaneh",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 267,
            title = "مطالعات هشتم ۸: رسانه‌ها در زندگی ما — از صفر",
            subtitle = "پایه هشتم متوسطه اول — مطالعات اجتماعی",
            lessonIds = listOf(
                "mid8-social-08-resaneh-ha-dar-zendegi"
            ),
            checkLessonId = "mid8-social-08-resaneh-ha-dar-zendegi",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 268,
            title = "مطالعات هشتم ۹: ظهور اسلام در شبه جزیره عربستان — از صفر",
            subtitle = "پایه هشتم متوسطه اول — مطالعات اجتماعی",
            lessonIds = listOf(
                "mid8-social-09-zohour-eslam"
            ),
            checkLessonId = "mid8-social-09-zohour-eslam",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 269,
            title = "مطالعات هشتم ۱۰: از رحلت پیامبر تا قیام کربلا — از صفر",
            subtitle = "پایه هشتم متوسطه اول — مطالعات اجتماعی",
            lessonIds = listOf(
                "mid8-social-10-az-rehlat-ta-karbala"
            ),
            checkLessonId = "mid8-social-10-az-rehlat-ta-karbala",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 270,
            title = "مطالعات هشتم ۱۱: ورود اسلام به ایران — از صفر",
            subtitle = "پایه هشتم متوسطه اول — مطالعات اجتماعی",
            lessonIds = listOf(
                "mid8-social-11-voroud-eslam-be-iran"
            ),
            checkLessonId = "mid8-social-11-voroud-eslam-be-iran",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 271,
            title = "مطالعات هشتم ۱۲: عصر طلایی فرهنگ و تمدن ایرانی‌اسلامی — از صفر",
            subtitle = "پایه هشتم متوسطه اول — مطالعات اجتماعی",
            lessonIds = listOf(
                "mid8-social-12-asr-talaei"
            ),
            checkLessonId = "mid8-social-12-asr-talaei",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 272,
            title = "مطالعات هشتم ۱۳: غزنویان، سلجوقیان و خوارزمشاهیان — از صفر",
            subtitle = "پایه هشتم متوسطه اول — مطالعات اجتماعی",
            lessonIds = listOf(
                "mid8-social-13-ghaznavian-seljoughian-kharazmshahian"
            ),
            checkLessonId = "mid8-social-13-ghaznavian-seljoughian-kharazmshahian",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 273,
            title = "مطالعات هشتم ۱۴: میراث فرهنگی ایران در عصر سلجوقی — از صفر",
            subtitle = "پایه هشتم متوسطه اول — مطالعات اجتماعی",
            lessonIds = listOf(
                "mid8-social-14-miras-farhangi-seljoughi"
            ),
            checkLessonId = "mid8-social-14-miras-farhangi-seljoughi",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 274,
            title = "مطالعات هشتم ۱۵: حمله چنگیز و تیمور به ایران — از صفر",
            subtitle = "پایه هشتم متوسطه اول — مطالعات اجتماعی",
            lessonIds = listOf(
                "mid8-social-15-changiz-timur-attack"
            ),
            checkLessonId = "mid8-social-15-changiz-timur-attack",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 275,
            title = "مطالعات هشتم ۱۶: پیروزی فرهنگ بر شمشیر — از صفر",
            subtitle = "پایه هشتم متوسطه اول — مطالعات اجتماعی",
            lessonIds = listOf(
                "mid8-social-16-culture-over-sword"
            ),
            checkLessonId = "mid8-social-16-culture-over-sword",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 276,
            title = "مطالعات هشتم ۱۷: ویژگی‌های طبیعی آسیا — از صفر",
            subtitle = "پایه هشتم متوسطه اول — مطالعات اجتماعی",
            lessonIds = listOf(
                "mid8-social-17-asia-natural"
            ),
            checkLessonId = "mid8-social-17-asia-natural",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 277,
            title = "مطالعات هشتم ۱۸: ویژگی‌های انسانی و اقتصادی آسیا — از صفر",
            subtitle = "پایه هشتم متوسطه اول — مطالعات اجتماعی",
            lessonIds = listOf(
                "mid8-social-18-asia-human-economic"
            ),
            checkLessonId = "mid8-social-18-asia-human-economic",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 278,
            title = "مطالعات هشتم ۱۹: ویژگی‌های منطقه جنوب‌غربی آسیا — از صفر",
            subtitle = "پایه هشتم متوسطه اول — مطالعات اجتماعی",
            lessonIds = listOf(
                "mid8-social-19-southwest-asia"
            ),
            checkLessonId = "mid8-social-19-southwest-asia",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 279,
            title = "مطالعات هشتم ۲۰: ایران و منطقه جنوب‌غربی آسیا — از صفر",
            subtitle = "پایه هشتم متوسطه اول — مطالعات اجتماعی",
            lessonIds = listOf(
                "mid8-social-20-iran-southwest-asia"
            ),
            checkLessonId = "mid8-social-20-iran-southwest-asia",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 280,
            title = "مطالعات هشتم ۲۱: ویژگی‌های طبیعی و انسانی اروپا — از صفر",
            subtitle = "پایه هشتم متوسطه اول — مطالعات اجتماعی",
            lessonIds = listOf(
                "mid8-social-21-europe-natural-human"
            ),
            checkLessonId = "mid8-social-21-europe-natural-human",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 281,
            title = "مطالعات هشتم ۲۲: ویژگی‌های طبیعی و انسانی افریقا — از صفر",
            subtitle = "پایه هشتم متوسطه اول — مطالعات اجتماعی",
            lessonIds = listOf(
                "mid8-social-22-africa-natural-human"
            ),
            checkLessonId = "mid8-social-22-africa-natural-human",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 282,
            title = "مطالعات هشتم ۲۳: قاره آمریکا — از صفر",
            subtitle = "پایه هشتم متوسطه اول — مطالعات اجتماعی",
            lessonIds = listOf(
                "mid8-social-23-america"
            ),
            checkLessonId = "mid8-social-23-america",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 283,
            title = "مطالعات هشتم ۲۴: قاره استرالیا و اقیانوسیه — از صفر",
            subtitle = "پایه هشتم متوسطه اول — مطالعات اجتماعی",
            lessonIds = listOf(
                "mid8-social-24-australia-oceania"
            ),
            checkLessonId = "mid8-social-24-australia-oceania",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 284,
            title = "پیام هشتم ۱: آفرینش شگفت‌انگیز — از صفر",
            subtitle = "پایه هشتم متوسطه اول — پیام‌های آسمان",
            lessonIds = listOf(
                "mid8-payam-01-afarinesh"
            ),
            checkLessonId = "mid8-payam-01-afarinesh",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 285,
            title = "پیام هشتم ۲: عفو و گذشت — از صفر",
            subtitle = "پایه هشتم متوسطه اول — پیام‌های آسمان",
            lessonIds = listOf(
                "mid8-payam-02-afv"
            ),
            checkLessonId = "mid8-payam-02-afv",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 286,
            title = "پیام هشتم ۳: همه چیز در دست تو — از صفر",
            subtitle = "پایه هشتم متوسطه اول — پیام‌های آسمان",
            lessonIds = listOf(
                "mid8-payam-03-hame-chiz"
            ),
            checkLessonId = "mid8-payam-03-hame-chiz",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 287,
            title = "پیام هشتم ۴: پیوند جاودان — از صفر",
            subtitle = "پایه هشتم متوسطه اول — پیام‌های آسمان",
            lessonIds = listOf(
                "mid8-payam-04-peyvand"
            ),
            checkLessonId = "mid8-payam-04-peyvand",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 288,
            title = "پیام هشتم ۵: روزی که اسلام کامل شد — از صفر",
            subtitle = "پایه هشتم متوسطه اول — پیام‌های آسمان",
            lessonIds = listOf(
                "mid8-payam-05-rooz-eslam"
            ),
            checkLessonId = "mid8-payam-05-rooz-eslam",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 289,
            title = "پیام هشتم ۶: نردبان آسمان — از صفر",
            subtitle = "پایه هشتم متوسطه اول — پیام‌های آسمان",
            lessonIds = listOf(
                "mid8-payam-06-nardeban"
            ),
            checkLessonId = "mid8-payam-06-nardeban",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 290,
            title = "پیام هشتم ۷: یک فرصت طلایی — از صفر",
            subtitle = "پایه هشتم متوسطه اول — پیام‌های آسمان",
            lessonIds = listOf(
                "mid8-payam-07-forsat"
            ),
            checkLessonId = "mid8-payam-07-forsat",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 291,
            title = "پیام هشتم ۸: نشان ارزشمندی — از صفر",
            subtitle = "پایه هشتم متوسطه اول — پیام‌های آسمان",
            lessonIds = listOf(
                "mid8-payam-08-neshan"
            ),
            checkLessonId = "mid8-payam-08-neshan",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 292,
            title = "پیام هشتم ۹: تدبیر زندگانی — از صفر",
            subtitle = "پایه هشتم متوسطه اول — پیام‌های آسمان",
            lessonIds = listOf(
                "mid8-payam-09-tadbir"
            ),
            checkLessonId = "mid8-payam-09-tadbir",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 293,
            title = "پیام هشتم ۱۰: دو سرمایه گرانبها — از صفر",
            subtitle = "پایه هشتم متوسطه اول — پیام‌های آسمان",
            lessonIds = listOf(
                "mid8-payam-10-do-sarmaye"
            ),
            checkLessonId = "mid8-payam-10-do-sarmaye",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 294,
            title = "پیام هشتم درس ۱۱: آفت‌های زبان — از صفر",
            subtitle = "پایه هشتم متوسطه اول — پیام‌های آسمان",
            lessonIds = listOf(
                "mid8-payam-11-afat-zaban"
            ),
            checkLessonId = "mid8-payam-11-afat-zaban",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 295,
            title = "پیام هشتم درس ۱۲: ارزش کار — از صفر",
            subtitle = "پایه هشتم متوسطه اول — پیام‌های آسمان",
            lessonIds = listOf(
                "mid8-payam-12-arzesh-kar"
            ),
            checkLessonId = "mid8-payam-12-arzesh-kar",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 296,
            title = "پیام هشتم درس ۱۳: کلید گنج‌ها — از صفر",
            subtitle = "پایه هشتم متوسطه اول — پیام‌های آسمان",
            lessonIds = listOf(
                "mid8-payam-13-kelid-ganjha"
            ),
            checkLessonId = "mid8-payam-13-kelid-ganjha",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 297,
            title = "پیام هشتم درس ۱۴: ما مسلمانان — از صفر",
            subtitle = "پایه هشتم متوسطه اول — پیام‌های آسمان",
            lessonIds = listOf(
                "mid8-payam-14-ma-mosalamanan"
            ),
            checkLessonId = "mid8-payam-14-ma-mosalamanan",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 298,
            title = "پیام هشتم درس ۱۵: حق‌الناس — از صفر",
            subtitle = "پایه هشتم متوسطه اول — پیام‌های آسمان",
            lessonIds = listOf(
                "mid8-payam-15-haq-alnas"
            ),
            checkLessonId = "mid8-payam-15-haq-alnas",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 299,
            title = "کار و فناوری هشتم پودمان ۱: شهروند الکترونیکی ۱ — از صفر",
            subtitle = "پایه هشتم متوسطه اول — کار و فناوری",
            lessonIds = listOf(
                "mid8-karfan-01-shahrvand-1"
            ),
            checkLessonId = "mid8-karfan-01-shahrvand-1",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 300,
            title = "کار و فناوری هشتم پودمان ۲: شهروند الکترونیکی ۲ — از صفر",
            subtitle = "پایه هشتم متوسطه اول — کار و فناوری",
            lessonIds = listOf(
                "mid8-karfan-02-shahrvand-2"
            ),
            checkLessonId = "mid8-karfan-02-shahrvand-2",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 301,
            title = "کار و فناوری هشتم پودمان ۳: برنامه‌نویسی پایتون ۲ — از صفر",
            subtitle = "پایه هشتم متوسطه اول — کار و فناوری",
            lessonIds = listOf(
                "mid8-karfan-03-python-2"
            ),
            checkLessonId = "mid8-karfan-03-python-2",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 302,
            title = "کار و فناوری هشتم پودمان ۴: الکترونیک — از صفر",
            subtitle = "پایه هشتم متوسطه اول — کار و فناوری",
            lessonIds = listOf(
                "mid8-karfan-04-electronic"
            ),
            checkLessonId = "mid8-karfan-04-electronic",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 303,
            title = "کار و فناوری هشتم ۵: کار با فلز — از صفر",
            subtitle = "پایه هشتم متوسطه اول — کار و فناوری",
            lessonIds = listOf(
                "mid8-karfan-05-metal"
            ),
            checkLessonId = "mid8-karfan-05-metal",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 304,
            title = "کار و فناوری هشتم ۶: صنایع دستی — بافت — از صفر",
            subtitle = "پایه هشتم متوسطه اول — کار و فناوری",
            lessonIds = listOf(
                "mid8-karfan-06-handicraft"
            ),
            checkLessonId = "mid8-karfan-06-handicraft",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 305,
            title = "کار و فناوری هشتم ۷: پرورش و نگهداری از حیوانات — از صفر",
            subtitle = "پایه هشتم متوسطه اول — کار و فناوری",
            lessonIds = listOf(
                "mid8-karfan-07-animals"
            ),
            checkLessonId = "mid8-karfan-07-animals",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 306,
            title = "کار و فناوری هشتم ۸: امور اداری و مالی — از صفر",
            subtitle = "پایه هشتم متوسطه اول — کار و فناوری",
            lessonIds = listOf(
                "mid8-karfan-08-admin-finance"
            ),
            checkLessonId = "mid8-karfan-08-admin-finance",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 307,
            title = "کار و فناوری هشتم ۹: معماری و سازه — ماکت‌سازی — از صفر",
            subtitle = "پایه هشتم متوسطه اول — کار و فناوری",
            lessonIds = listOf(
                "mid8-karfan-09-architecture"
            ),
            checkLessonId = "mid8-karfan-09-architecture",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 308,
            title = "تفکر هشتم ۱: زیبایی — از صفر",
            subtitle = "پایه هشتم متوسطه اول — تفکر و سبک زندگی",
            lessonIds = listOf(
                "mid8-tafakkor-01-beauty"
            ),
            checkLessonId = "mid8-tafakkor-01-beauty",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 309,
            title = "تفکر هشتم ۲: نقاشی شاهزاده خانم — از صفر",
            subtitle = "پایه هشتم متوسطه اول — تفکر و سبک زندگی",
            lessonIds = listOf(
                "mid8-tafakkor-02-princess-painting"
            ),
            checkLessonId = "mid8-tafakkor-02-princess-painting",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 310,
            title = "تفکر هشتم ۳: پروژه — از صفر",
            subtitle = "پایه هشتم متوسطه اول — تفکر و سبک زندگی",
            lessonIds = listOf(
                "mid8-tafakkor-03-project"
            ),
            checkLessonId = "mid8-tafakkor-03-project",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 311,
            title = "تفکر هشتم ۴: راه رهایی — وقتی گیر کرده‌ای، راه بیرون را پیدا کن",
            subtitle = "پایه هشتم متوسطه اول — تفکر و سبک زندگی",
            lessonIds = listOf(
                "mid8-tafakkor-04-rah-rahaee"
            ),
            checkLessonId = "mid8-tafakkor-04-rah-rahaee",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 312,
            title = "تفکر هشتم ۵: طرح سؤال پژوهشی — سؤال خوب، نصف پژوهش است",
            subtitle = "پایه هشتم متوسطه اول — تفکر و سبک زندگی",
            lessonIds = listOf(
                "mid8-tafakkor-05-tarhe-soal-pajouheshi"
            ),
            checkLessonId = "mid8-tafakkor-05-tarhe-soal-pajouheshi",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 313,
            title = "تفکر هشتم ۶: تهیه طرح پژوهش — نقشه کارت را قبل از راه افتادن بکش",
            subtitle = "پایه هشتم متوسطه اول — تفکر و سبک زندگی",
            lessonIds = listOf(
                "mid8-tafakkor-06-tahiye-tarhe-pajouhesh"
            ),
            checkLessonId = "mid8-tafakkor-06-tahiye-tarhe-pajouhesh",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 314,
            title = "تفکر هشتم ۷: برنامه‌ریزی برای اجرا — از نقشه تا عمل",
            subtitle = "پایه هشتم متوسطه اول — تفکر و سبک زندگی",
            lessonIds = listOf(
                "mid8-tafakkor-07-barname-rizi-baraye-ejra"
            ),
            checkLessonId = "mid8-tafakkor-07-barname-rizi-baraye-ejra",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 315,
            title = "تفکر هشتم ۸: آداب معاشرت و گفت‌وگو — چطور با دیگران خوب حرف بزنیم",
            subtitle = "پایه هشتم متوسطه اول — تفکر و سبک زندگی",
            lessonIds = listOf(
                "mid8-tafakkor-08-adab-moasherat-goftegoo"
            ),
            checkLessonId = "mid8-tafakkor-08-adab-moasherat-goftegoo",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 316,
            title = "تفکر هشتم ۹: معاشرت مجازی — در فضای مجازی هم آداب داریم",
            subtitle = "پایه هشتم متوسطه اول — تفکر و سبک زندگی",
            lessonIds = listOf(
                "mid8-tafakkor-09-moasherat-majazi"
            ),
            checkLessonId = "mid8-tafakkor-09-moasherat-majazi",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 317,
            title = "تفکر هشتم ۱۰: مهارت‌های تصمیم‌گیری — تصمیم خوب را قدم‌به‌قدم بگیر",
            subtitle = "پایه هشتم متوسطه اول — تفکر و سبک زندگی",
            lessonIds = listOf(
                "mid8-tafakkor-10-maharat-tasmim-giri"
            ),
            checkLessonId = "mid8-tafakkor-10-maharat-tasmim-giri",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 318,
            title = "تفکر هشتم ۱۱: خویشتنداری — اول مکث کن، بعد واکنش نشان بده",
            subtitle = "پایه هشتم متوسطه اول — تفکر و سبک زندگی",
            lessonIds = listOf(
                "mid8-tafakkor-11-khishtandari"
            ),
            checkLessonId = "mid8-tafakkor-11-khishtandari",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 319,
            title = "تفکر هشتم ۱۲: رفتار پرخطر — خطر را بشناس، «نه» بگو، راه امن را بردار",
            subtitle = "پایه هشتم متوسطه اول — تفکر و سبک زندگی",
            lessonIds = listOf(
                "mid8-tafakkor-12-raftar-porkhatar"
            ),
            checkLessonId = "mid8-tafakkor-12-raftar-porkhatar",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 320,
            title = "تفکر هشتم ۱۳: دیو و کیک — از روی ظاهر قضاوت نکن",
            subtitle = "پایه هشتم متوسطه اول — تفکر و سبک زندگی",
            lessonIds = listOf(
                "mid8-tafakkor-13-div-o-keyk"
            ),
            checkLessonId = "mid8-tafakkor-13-div-o-keyk",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 321,
            title = "تفکر هشتم ۱۴: خط‌کشی — کار کوچک، ارزش بزرگ",
            subtitle = "پایه هشتم متوسطه اول — تفکر و سبک زندگی",
            lessonIds = listOf(
                "mid8-tafakkor-14-khat-keshi"
            ),
            checkLessonId = "mid8-tafakkor-14-khat-keshi",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 322,
            title = "تفکر هشتم ۱۵: تو نیکی می‌کن و در دجله انداز — قانون عمل و عکس‌العمل",
            subtitle = "پایه هشتم متوسطه اول — تفکر و سبک زندگی",
            lessonIds = listOf(
                "mid8-tafakkor-15-niki-dajle"
            ),
            checkLessonId = "mid8-tafakkor-15-niki-dajle",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 323,
            title = "تفکر هشتم ۱۶: گزارش پژوهش — یافته‌هایت را مرتب بنویس",
            subtitle = "پایه هشتم متوسطه اول — تفکر و سبک زندگی",
            lessonIds = listOf(
                "mid8-tafakkor-16-gozaresh-pazhuhesh"
            ),
            checkLessonId = "mid8-tafakkor-16-gozaresh-pazhuhesh",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 324,
            title = "تفکر هشتم ۱۷: ارائه گزارش پژوهش — حرفت را خوب بگو",
            subtitle = "پایه هشتم متوسطه اول — تفکر و سبک زندگی",
            lessonIds = listOf(
                "mid8-tafakkor-17-erae-gozaresh"
            ),
            checkLessonId = "mid8-tafakkor-17-erae-gozaresh",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 325,
            title = "تفکر هشتم ۱۸: واقعیت — آنچه هست، با آنچه فکر می‌کنیم فرق دارد",
            subtitle = "پایه هشتم متوسطه اول — تفکر و سبک زندگی",
            lessonIds = listOf(
                "mid8-tafakkor-18-vagheiyat"
            ),
            checkLessonId = "mid8-tafakkor-18-vagheiyat",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 326,
            title = "تفکر هشتم ۱۹: عادت‌ها — زنجیرهایی که خودمان می‌سازیم",
            subtitle = "پایه هشتم متوسطه اول — تفکر و سبک زندگی",
            lessonIds = listOf(
                "mid8-tafakkor-19-adatha"
            ),
            checkLessonId = "mid8-tafakkor-19-adatha",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 327,
            title = "تفکر هشتم ۲۰: پدر و پسر — عادت به روش قدیمی یا راه حل تازه؟",
            subtitle = "پایه هشتم متوسطه اول — تفکر و سبک زندگی",
            lessonIds = listOf(
                "mid8-tafakkor-20-pedar-pesar"
            ),
            checkLessonId = "mid8-tafakkor-20-pedar-pesar",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 328,
            title = "تفکر هشتم ۲۱: تخیل یا...؟ — خیال بافی با فکر کردن فرق دارد",
            subtitle = "پایه هشتم متوسطه اول — تفکر و سبک زندگی",
            lessonIds = listOf(
                "mid8-tafakkor-21-takhayyol"
            ),
            checkLessonId = "mid8-tafakkor-21-takhayyol",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 329,
            title = "تفکر هشتم ۲۲: اثر... — هر کاری ردی از خودش می‌گذارد",
            subtitle = "پایه هشتم متوسطه اول — تفکر و سبک زندگی",
            lessonIds = listOf(
                "mid8-tafakkor-22-asar"
            ),
            checkLessonId = "mid8-tafakkor-22-asar",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 330,
            title = "تفکر هشتم ۲۳: آن پسرک انار ندارد — اول فکر کن، بعد قضاوت کن",
            subtitle = "پایه هشتم متوسطه اول — تفکر و سبک زندگی",
            lessonIds = listOf(
                "mid8-tafakkor-23-pesarak-anar"
            ),
            checkLessonId = "mid8-tafakkor-23-pesarak-anar",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 331,
            title = "تفکر هشتم ۲۴: مهار افسردگی — از صفر",
            subtitle = "پایه هشتم متوسطه اول — تفکر و سبک زندگی",
            lessonIds = listOf(
                "mid8-tafakkor-24-depression"
            ),
            checkLessonId = "mid8-tafakkor-24-depression",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 332,
            title = "تفکر هشتم ۲۵: چگونه از افراد بزرگسال، پیر و ناتوان مراقبت کنیم؟ — از صفر",
            subtitle = "پایه هشتم متوسطه اول — تفکر و سبک زندگی",
            lessonIds = listOf(
                "mid8-tafakkor-25-care-elderly"
            ),
            checkLessonId = "mid8-tafakkor-25-care-elderly",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 333,
            title = "تفکر هشتم ۲۶: چرا باید به همدیگر احترام بگذاریم؟ — از صفر",
            subtitle = "پایه هشتم متوسطه اول — تفکر و سبک زندگی",
            lessonIds = listOf(
                "mid8-tafakkor-26-respect"
            ),
            checkLessonId = "mid8-tafakkor-26-respect",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 334,
            title = "تفکر هشتم ۲۷: ارزش‌ها و معیارها — از صفر",
            subtitle = "پایه هشتم متوسطه اول — تفکر و سبک زندگی",
            lessonIds = listOf(
                "mid8-tafakkor-27-values"
            ),
            checkLessonId = "mid8-tafakkor-27-values",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 335,
            title = "تفکر هشتم ۲۸: آداب و مهارت‌های سفر کردن — از صفر",
            subtitle = "پایه هشتم متوسطه اول — تفکر و سبک زندگی",
            lessonIds = listOf(
                "mid8-tafakkor-28-travel"
            ),
            checkLessonId = "mid8-tafakkor-28-travel",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 336,
            title = "تفکر هشتم ۲۹: فعالیت‌های اختیاری و خودارزیابی — از صفر",
            subtitle = "پایه هشتم متوسطه اول — تفکر و سبک زندگی",
            lessonIds = listOf(
                "mid8-tafakkor-29-self-assessment"
            ),
            checkLessonId = "mid8-tafakkor-29-self-assessment",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 337,
            title = "تفکر هشتم ۳۰: مهارت‌های زندگی موفق — موفقیت یعنی چه و چطور به آن برسیم",
            subtitle = "پایه هشتم متوسطه اول — تفکر و سبک زندگی",
            lessonIds = listOf(
                "mid8-tafakkor-30-maharat-zendegi-movaffaq"
            ),
            checkLessonId = "mid8-tafakkor-30-maharat-zendegi-movaffaq",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 338,
            title = "تفکر هشتم ۳۱: مهارت افزایش عزت نفس — خودت را گرامی بدار",
            subtitle = "پایه هشتم متوسطه اول — تفکر و سبک زندگی",
            lessonIds = listOf(
                "mid8-tafakkor-31-ezzat-nafs"
            ),
            checkLessonId = "mid8-tafakkor-31-ezzat-nafs",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 339,
            title = "تفکر هشتم ۳۲: مهارت بهبود خودپنداره و افزایش اعتماد به نفس — خودت را بشناس، به خودت اعتماد کن",
            subtitle = "پایه هشتم متوسطه اول — تفکر و سبک زندگی",
            lessonIds = listOf(
                "mid8-tafakkor-32-khodpendare"
            ),
            checkLessonId = "mid8-tafakkor-32-khodpendare",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 340,
            title = "تفکر هشتم ۳۳: مهار فشار روانی و اضطراب — وقتی دلهره داری، چه کار کنی؟",
            subtitle = "پایه هشتم متوسطه اول — تفکر و سبک زندگی",
            lessonIds = listOf(
                "mid8-tafakkor-33-mahar-feshar-ravani"
            ),
            checkLessonId = "mid8-tafakkor-33-mahar-feshar-ravani",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 341,
            title = "تفکر هشتم ۳۴: مهارت‌های اقتصادی — مصرف‌کننده عاقل چه کسی است؟",
            subtitle = "پایه هشتم متوسطه اول — تفکر و سبک زندگی",
            lessonIds = listOf(
                "mid8-tafakkor-34-maharat-eqtesadi"
            ),
            checkLessonId = "mid8-tafakkor-34-maharat-eqtesadi",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 342,
            title = "فرهنگ و هنر هشتم ۱: طراحی دوبعدی (سطح)، نقش برجسته و طراحی سه‌بعدنما — از صفر",
            subtitle = "پایه هشتم متوسطه اول — فرهنگ و هنر",
            lessonIds = listOf(
                "mid8-honar-01-design-2d-3d"
            ),
            checkLessonId = "mid8-honar-01-design-2d-3d",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 343,
            title = "فرهنگ و هنر هشتم ۲: طراحی از پرندگان — از صفر",
            subtitle = "پایه هشتم متوسطه اول — فرهنگ و هنر",
            lessonIds = listOf(
                "mid8-honar-02-birds"
            ),
            checkLessonId = "mid8-honar-02-birds",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 344,
            title = "فرهنگ و هنر هشتم ۳: طراحی از بناها و فضاهای معماری — از صفر",
            subtitle = "پایه هشتم متوسطه اول — فرهنگ و هنر",
            lessonIds = listOf(
                "mid8-honar-03-architecture"
            ),
            checkLessonId = "mid8-honar-03-architecture",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 345,
            title = "فرهنگ و هنر هشتم ۴: تزیینات در معماری — از صفر",
            subtitle = "پایه هشتم متوسطه اول — فرهنگ و هنر",
            lessonIds = listOf(
                "mid8-honar-04-ornaments"
            ),
            checkLessonId = "mid8-honar-04-ornaments",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 346,
            title = "فرهنگ و هنر هشتم ۵: انواع خط در تصویرسازی — از صفر",
            subtitle = "پایه هشتم متوسطه اول — فرهنگ و هنر",
            lessonIds = listOf(
                "mid8-honar-05-tasvirsazi-khat"
            ),
            checkLessonId = "mid8-honar-05-tasvirsazi-khat",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 347,
            title = "فرهنگ و هنر هشتم ۶: کیفیت سطوح در تصویرسازی — از صفر",
            subtitle = "پایه هشتم متوسطه اول — فرهنگ و هنر",
            lessonIds = listOf(
                "mid8-honar-06-tasvirsazi-sath"
            ),
            checkLessonId = "mid8-honar-06-tasvirsazi-sath",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 348,
            title = "فرهنگ و هنر هشتم ۷: ویژگی‌های تصویرسازی — از صفر",
            subtitle = "پایه هشتم متوسطه اول — فرهنگ و هنر",
            lessonIds = listOf(
                "mid8-honar-07-tasvirsazi-vizhegi"
            ),
            checkLessonId = "mid8-honar-07-tasvirsazi-vizhegi",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 349,
            title = "فرهنگ و هنر هشتم ۸: روند تصویرسازی — از صفر",
            subtitle = "پایه هشتم متوسطه اول — فرهنگ و هنر",
            lessonIds = listOf(
                "mid8-honar-08-tasvirsazi-ravand"
            ),
            checkLessonId = "mid8-honar-08-tasvirsazi-ravand",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 350,
            title = "فرهنگ و هنر هشتم ۹: عناصر بصری در عکاسی — بافت — از صفر",
            subtitle = "پایه هشتم متوسطه اول — فرهنگ و هنر",
            lessonIds = listOf(
                "mid8-honar-09-akkasi-baft"
            ),
            checkLessonId = "mid8-honar-09-akkasi-baft",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 351,
            title = "فرهنگ و هنر هشتم ۱۰: ترکیب‌بندی در عکاسی — قاب‌بندی — از صفر",
            subtitle = "پایه هشتم متوسطه اول — فرهنگ و هنر",
            lessonIds = listOf(
                "mid8-honar-10-akkasi-qabbandi"
            ),
            checkLessonId = "mid8-honar-10-akkasi-qabbandi",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 352,
            title = "فرهنگ و هنر هشتم ۱۱: ترکیب‌بندی در عکاسی — زاویه دید — از صفر",
            subtitle = "پایه هشتم متوسطه اول — فرهنگ و هنر",
            lessonIds = listOf(
                "mid8-honar-11-akkasi-zaviyeh"
            ),
            checkLessonId = "mid8-honar-11-akkasi-zaviyeh",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 353,
            title = "فرهنگ و هنر هشتم ۱۲: عناصر بصری در عکاسی — رنگ — از صفر",
            subtitle = "پایه هشتم متوسطه اول — فرهنگ و هنر",
            lessonIds = listOf(
                "mid8-honar-12-akkasi-rang"
            ),
            checkLessonId = "mid8-honar-12-akkasi-rang",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 354,
            title = "هنر هشتم ۱۳: آشنایی با ابزار خوشنویسی — از صفر",
            subtitle = "پایه هشتم متوسطه اول — فرهنگ و هنر",
            lessonIds = listOf(
                "mid8-honar-13-abzar-khoshnevisi"
            ),
            checkLessonId = "mid8-honar-13-abzar-khoshnevisi",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 355,
            title = "هنر هشتم ۱۴: معرفی خط ریحان و مشق سطرنویسی (۱) — از صفر",
            subtitle = "پایه هشتم متوسطه اول — فرهنگ و هنر",
            lessonIds = listOf(
                "mid8-honar-14-khat-reyhan"
            ),
            checkLessonId = "mid8-honar-14-khat-reyhan",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 356,
            title = "هنر هشتم ۱۵: معرفی خط توقیع و مشق سطرنویسی (۲) — از صفر",
            subtitle = "پایه هشتم متوسطه اول — فرهنگ و هنر",
            lessonIds = listOf(
                "mid8-honar-15-khat-towqi"
            ),
            checkLessonId = "mid8-honar-15-khat-towqi",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 357,
            title = "هنر هشتم ۱۶: معرفی خط رقاع و مشق سطرنویسی (۳) — از صفر",
            subtitle = "پایه هشتم متوسطه اول — فرهنگ و هنر",
            lessonIds = listOf(
                "mid8-honar-16-khat-reqa"
            ),
            checkLessonId = "mid8-honar-16-khat-reqa",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 358,
            title = "هنر هشتم ۱۷: معرفی خط تعلیق و مشق دوسطرنویسی — از صفر",
            subtitle = "پایه هشتم متوسطه اول — فرهنگ و هنر",
            lessonIds = listOf(
                "mid8-honar-17-khat-taliq"
            ),
            checkLessonId = "mid8-honar-17-khat-taliq",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 359,
            title = "هنر هشتم ۱۸: رسم نقوش هندسی در شبکه‌ها — از صفر",
            subtitle = "پایه هشتم متوسطه اول — فرهنگ و هنر",
            lessonIds = listOf(
                "mid8-honar-18-noghoosh-hendesi"
            ),
            checkLessonId = "mid8-honar-18-noghoosh-hendesi",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 360,
            title = "هنر هشتم ۱۹: طبل در طبل — از صفر",
            subtitle = "پایه هشتم متوسطه اول — فرهنگ و هنر",
            lessonIds = listOf(
                "mid8-honar-19-tabl-dar-tabl"
            ),
            checkLessonId = "mid8-honar-19-tabl-dar-tabl",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 361,
            title = "هنر هشتم ۲۰: نقش موج — از صفر",
            subtitle = "پایه هشتم متوسطه اول — فرهنگ و هنر",
            lessonIds = listOf(
                "mid8-honar-20-naghshe-mowj"
            ),
            checkLessonId = "mid8-honar-20-naghshe-mowj",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 362,
            title = "هنر هشتم ۲۱: آشنایی با پارچه و انواع روش‌های بافت؛ بافت ساده یا تافته — از صفر",
            subtitle = "پایه هشتم متوسطه اول — فرهنگ و هنر",
            lessonIds = listOf(
                "mid8-honar-21-bafte-parche"
            ),
            checkLessonId = "mid8-honar-21-bafte-parche",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 363,
            title = "هنر هشتم ۲۲: طراحی و بافت پارچه‌های تاری‌ـ‌پودی — از صفر",
            subtitle = "پایه هشتم متوسطه اول — فرهنگ و هنر",
            lessonIds = listOf(
                "mid8-honar-22-taropoodi"
            ),
            checkLessonId = "mid8-honar-22-taropoodi",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 364,
            title = "هنر هشتم ۲۳: بافت مرکب — از صفر",
            subtitle = "پایه هشتم متوسطه اول — فرهنگ و هنر",
            lessonIds = listOf(
                "mid8-honar-23-bafte-morakkab"
            ),
            checkLessonId = "mid8-honar-23-bafte-morakkab",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 365,
            title = "هنر هشتم ۲۴: چاپ با مواد طبیعی و در دسترس — از صفر",
            subtitle = "پایه هشتم متوسطه اول — فرهنگ و هنر",
            lessonIds = listOf(
                "mid8-honar-24-chap-tabi"
            ),
            checkLessonId = "mid8-honar-24-chap-tabi",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 366,
            title = "هنر هشتم ۲۵: پیچمان (گره و رنگ) — از صفر",
            subtitle = "پایه هشتم متوسطه اول — فرهنگ و هنر",
            lessonIds = listOf(
                "mid8-honar-25-pichman"
            ),
            checkLessonId = "mid8-honar-25-pichman",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 367,
            title = "هنر هشتم ۲۶: چاپ استنسیل — از صفر",
            subtitle = "پایه هشتم متوسطه اول — فرهنگ و هنر",
            lessonIds = listOf(
                "mid8-honar-26-chap-stensil"
            ),
            checkLessonId = "mid8-honar-26-chap-stensil",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 368,
            title = "هنر هشتم ۲۷: وزن (ریتم) — از صفر",
            subtitle = "پایه هشتم متوسطه اول — فرهنگ و هنر",
            lessonIds = listOf(
                "mid8-honar-27-vazn-ritm"
            ),
            checkLessonId = "mid8-honar-27-vazn-ritm",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 369,
            title = "هنر هشتم ۲۸: ملودی یا لحن — از صفر",
            subtitle = "پایه هشتم متوسطه اول — فرهنگ و هنر",
            lessonIds = listOf(
                "mid8-honar-28-melodi"
            ),
            checkLessonId = "mid8-honar-28-melodi",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 370,
            title = "هنر هشتم ۲۹: رنگ (در هنرهای آوایی) — از صفر",
            subtitle = "پایه هشتم متوسطه اول — فرهنگ و هنر",
            lessonIds = listOf(
                "mid8-honar-29-rang-avayi"
            ),
            checkLessonId = "mid8-honar-29-rang-avayi",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 371,
            title = "هنر هشتم ۳۰: بافت (در هنرهای آوایی) — از صفر",
            subtitle = "پایه هشتم متوسطه اول — فرهنگ و هنر",
            lessonIds = listOf(
                "mid8-honar-30-baft-avayi"
            ),
            checkLessonId = "mid8-honar-30-baft-avayi",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 372,
            title = "هنر هشتم ۳۱: فرم — از صفر",
            subtitle = "پایه هشتم متوسطه اول — فرهنگ و هنر",
            lessonIds = listOf(
                "mid8-honar-31-form-avayi"
            ),
            checkLessonId = "mid8-honar-31-form-avayi",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 373,
            title = "هنر هشتم ۳۲: بازی و بازیگری — از صفر",
            subtitle = "پایه هشتم متوسطه اول — فرهنگ و هنر",
            lessonIds = listOf(
                "mid8-honar-32-bazi-bazigari"
            ),
            checkLessonId = "mid8-honar-32-bazi-bazigari",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 374,
            title = "هنر هشتم ۳۳: عناصر بازیگری — از صفر",
            subtitle = "پایه هشتم متوسطه اول — فرهنگ و هنر",
            lessonIds = listOf(
                "mid8-honar-33-anasor-bazigari"
            ),
            checkLessonId = "mid8-honar-33-anasor-bazigari",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 375,
            title = "هنر هشتم ۳۴: بدن و حرکت — از صفر",
            subtitle = "پایه هشتم متوسطه اول — فرهنگ و هنر",
            lessonIds = listOf(
                "mid8-honar-34-badan-harekat"
            ),
            checkLessonId = "mid8-honar-34-badan-harekat",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 376,
            title = "هنر هشتم ۳۵: احساس و تخیل — از صفر",
            subtitle = "پایه هشتم متوسطه اول — فرهنگ و هنر",
            lessonIds = listOf(
                "mid8-honar-35-ehsas-takhayyol"
            ),
            checkLessonId = "mid8-honar-35-ehsas-takhayyol",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 377,
            title = "فارسی نهم ۱: آفرینش همه تنبیه خداوند دل است — قصیده ستایشی سعدی",
            subtitle = "پایه نهم متوسطه اول — فارسی",
            lessonIds = listOf(
                "mid9-farsi-01-afarinesh"
            ),
            checkLessonId = "mid9-farsi-01-afarinesh",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 378,
            title = "فارسی نهم ۲: عجایب صنع حق تعالی — نثر غزالی از کیمیای سعادت",
            subtitle = "پایه نهم متوسطه اول — فارسی",
            lessonIds = listOf(
                "mid9-farsi-02-ajayeb"
            ),
            checkLessonId = "mid9-farsi-02-ajayeb",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 379,
            title = "فارسی نهم ۳: مثل آیینه، کار و شایستگی — پروین اعتصامی",
            subtitle = "پایه نهم متوسطه اول — فارسی",
            lessonIds = listOf(
                "mid9-farsi-03-ayeneh"
            ),
            checkLessonId = "mid9-farsi-03-ayeneh",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 380,
            title = "فارسی نهم ۴: هم‌نشین — اثر دوست خوب و بد",
            subtitle = "پایه نهم متوسطه اول — فارسی",
            lessonIds = listOf(
                "mid9-farsi-04-hamneshin"
            ),
            checkLessonId = "mid9-farsi-04-hamneshin",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 381,
            title = "فارسی نهم ۵: درس آزاد — ادبیات بومی ۱",
            subtitle = "پایه نهم متوسطه اول — فارسی",
            lessonIds = listOf(
                "mid9-farsi-05-dars-azad"
            ),
            checkLessonId = "mid9-farsi-05-dars-azad",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 382,
            title = "فارسی نهم ۶: آداب زندگانی — قابوس‌نامه و اخلاق ناصری",
            subtitle = "پایه نهم متوسطه اول — فارسی",
            lessonIds = listOf(
                "mid9-farsi-06-adab"
            ),
            checkLessonId = "mid9-farsi-06-adab",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 383,
            title = "فارسی نهم ۷: پرتو امید — غزل حافظ، یوسف گم‌گشته باز آید به کنعان",
            subtitle = "پایه نهم متوسطه اول — فارسی",
            lessonIds = listOf(
                "mid9-farsi-07-partow-omid"
            ),
            checkLessonId = "mid9-farsi-07-partow-omid",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 384,
            title = "فارسی نهم ۸: هم‌زیستی با مام میهن — وحدت ملی و همدلی اقوام",
            subtitle = "پایه نهم متوسطه اول — فارسی",
            lessonIds = listOf(
                "mid9-farsi-08-mam-mihan"
            ),
            checkLessonId = "mid9-farsi-08-mam-mihan",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 385,
            title = "فارسی نهم ۹: راز موفقیت — خواجه نصیرالدین طوسی و ابوریحان بیرونی",
            subtitle = "پایه نهم متوسطه اول — فارسی",
            lessonIds = listOf(
                "mid9-farsi-09-raz-movaffaghiat"
            ),
            checkLessonId = "mid9-farsi-09-raz-movaffaghiat",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 386,
            title = "فارسی نهم ۱۰: آرشی دیگر — شعر نیمایی محمد گودرزی دهریزی",
            subtitle = "پایه نهم متوسطه اول — فارسی",
            lessonIds = listOf(
                "mid9-farsi-10-arash"
            ),
            checkLessonId = "mid9-farsi-10-arash",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 387,
            title = "فارسی نهم ۱۱: زن پارسا — رابعه عدویه، زنی که با فقر و ایمان بزرگ شد",
            subtitle = "پایه نهم متوسطه اول — فارسی",
            lessonIds = listOf(
                "mid9-farsi-11-zan-parsa"
            ),
            checkLessonId = "mid9-farsi-11-zan-parsa",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 388,
            title = "فارسی نهم ۱۲: پیام‌آور رحمت — پیام پیامبر، گنجینه‌ای برای همه انسان‌ها",
            subtitle = "پایه نهم متوسطه اول — فارسی",
            lessonIds = listOf(
                "mid9-farsi-12-payamavar-rahmat"
            ),
            checkLessonId = "mid9-farsi-12-payamavar-rahmat",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 389,
            title = "فارسی نهم ۱۳: آشنای غریبان، میلاد گل — غزل قیصر امین‌پور و میلاد امام زمان",
            subtitle = "پایه نهم متوسطه اول — فارسی",
            lessonIds = listOf(
                "mid9-farsi-13-ashna-ghariban-milad-gol"
            ),
            checkLessonId = "mid9-farsi-13-ashna-ghariban-milad-gol",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 390,
            title = "فارسی نهم ۱۴: پیدای پنهان — خدا آن‌قدر پیداست که پنهان به نظر می‌رسد",
            subtitle = "پایه نهم متوسطه اول — فارسی",
            lessonIds = listOf(
                "mid9-farsi-14-peyda-penhan"
            ),
            checkLessonId = "mid9-farsi-14-peyda-penhan",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 391,
            title = "فارسی نهم ۱۵: درس آزاد — ادبیات بومی ۲؛ قصه‌های سرزمین خودمان را بخوانیم",
            subtitle = "پایه نهم متوسطه اول — فارسی",
            lessonIds = listOf(
                "mid9-farsi-15-dars-azad-adabiyat-bumi-2"
            ),
            checkLessonId = "mid9-farsi-15-dars-azad-adabiyat-bumi-2",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 392,
            title = "فارسی نهم ۱۶: آرزو — آرزوهای عجیب ویکتور هوگو برای فرزندش",
            subtitle = "پایه نهم متوسطه اول — فارسی",
            lessonIds = listOf(
                "mid9-farsi-16-arezoo"
            ),
            checkLessonId = "mid9-farsi-16-arezoo",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 393,
            title = "فارسی نهم ۱۷: شازده کوچولو — شازده‌ای از سیاره‌ای کوچک و گلی که دوستش دارد",
            subtitle = "پایه نهم متوسطه اول — فارسی",
            lessonIds = listOf(
                "mid9-farsi-17-shazde-koochooloo"
            ),
            checkLessonId = "mid9-farsi-17-shazde-koochooloo",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 394,
            title = "نگارش نهم ۱: با ذهنی نظام‌مند و پرورده بنویسیم — اول نقشه، بعد نوشتن",
            subtitle = "پایه نهم متوسطه اول — نگارش",
            lessonIds = listOf(
                "mid9-negaresh-01-zehn-nezammand"
            ),
            checkLessonId = "mid9-negaresh-01-zehn-nezammand",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 395,
            title = "نگارش نهم ۲: واژه‌ها را بشناسیم، گزینش کنیم و به کار بگیریم — مواد و مصالح نوشته",
            subtitle = "پایه نهم متوسطه اول — نگارش",
            lessonIds = listOf(
                "mid9-negaresh-02-vazheha"
            ),
            checkLessonId = "mid9-negaresh-02-vazheha",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 396,
            title = "نگارش نهم ۳: نوع زبان نوشته را انتخاب کنیم — زبان درست برای هر نوشته",
            subtitle = "پایه نهم متوسطه اول — نگارش",
            lessonIds = listOf(
                "mid9-negaresh-03-noe-zaban"
            ),
            checkLessonId = "mid9-negaresh-03-noe-zaban",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 397,
            title = "نگارش نهم درس ۴: فضا و رنگ نوشته را تغییر دهیم",
            subtitle = "پایه نهم متوسطه اول — نگارش",
            lessonIds = listOf(
                "mid9-negaresh-04-space-color"
            ),
            checkLessonId = "mid9-negaresh-04-space-color",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 398,
            title = "نگارش نهم درس ۵: نوشته را خوش آغاز کنیم؛ زیبا به پایان ببریم و نیکو نام‌گذاری کنیم",
            subtitle = "پایه نهم متوسطه اول — نگارش",
            lessonIds = listOf(
                "mid9-negaresh-05-beginning-ending-title"
            ),
            checkLessonId = "mid9-negaresh-05-beginning-ending-title",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 399,
            title = "نگارش نهم درس ۶: قالبی برای نوشتن، برگزینیم",
            subtitle = "پایه نهم متوسطه اول — نگارش",
            lessonIds = listOf(
                "mid9-negaresh-06-choose-template"
            ),
            checkLessonId = "mid9-negaresh-06-choose-template",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 400,
            title = "نگارش نهم درس ۷: وسعت و عمق نوشته را بیشتر کنیم",
            subtitle = "پایه نهم متوسطه اول — نگارش",
            lessonIds = listOf(
                "mid9-negaresh-07-expand-depth"
            ),
            checkLessonId = "mid9-negaresh-07-expand-depth",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 401,
            title = "نگارش نهم درس ۸: نوشته را ویرایش کنیم",
            subtitle = "پایه نهم متوسطه اول — نگارش",
            lessonIds = listOf(
                "mid9-negaresh-08-editing"
            ),
            checkLessonId = "mid9-negaresh-08-editing",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 402,
            title = "ریاضی نهم فصل ۱: مجموعه‌ها",
            subtitle = "پایه نهم متوسطه اول — ریاضی",
            lessonIds = listOf(
                "mid9-math-01-sets"
            ),
            checkLessonId = "mid9-math-01-sets",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 403,
            title = "ریاضی نهم فصل ۲: عددهای حقیقی",
            subtitle = "پایه نهم متوسطه اول — ریاضی",
            lessonIds = listOf(
                "mid9-math-02-real-numbers"
            ),
            checkLessonId = "mid9-math-02-real-numbers",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 404,
            title = "ریاضی نهم فصل ۳: استدلال و اثبات در هندسه",
            subtitle = "پایه نهم متوسطه اول — ریاضی",
            lessonIds = listOf(
                "mid9-math-03-reasoning-proof-geometry"
            ),
            checkLessonId = "mid9-math-03-reasoning-proof-geometry",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 405,
            title = "ریاضی نهم فصل ۴: توان و ریشه",
            subtitle = "پایه نهم متوسطه اول — ریاضی",
            lessonIds = listOf(
                "mid9-math-04-powers-roots"
            ),
            checkLessonId = "mid9-math-04-powers-roots",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 406,
            title = "ریاضی نهم فصل ۵: عبارت‌های جبری",
            subtitle = "پایه نهم متوسطه اول — ریاضی",
            lessonIds = listOf(
                "mid9-math-05-algebraic-expressions"
            ),
            checkLessonId = "mid9-math-05-algebraic-expressions",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 407,
            title = "ریاضی نهم ۶: خط و معادله‌های خطی — از صفر",
            subtitle = "پایه نهم متوسطه اول — ریاضی",
            lessonIds = listOf(
                "mid9-math-06-linear-equations"
            ),
            checkLessonId = "mid9-math-06-linear-equations",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 408,
            title = "ریاضی نهم ۷: عبارت‌های گویا — از صفر",
            subtitle = "پایه نهم متوسطه اول — ریاضی",
            lessonIds = listOf(
                "mid9-math-07-rational-expressions"
            ),
            checkLessonId = "mid9-math-07-rational-expressions",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 409,
            title = "ریاضی نهم ۸: حجم و مساحت — از صفر",
            subtitle = "پایه نهم متوسطه اول — ریاضی",
            lessonIds = listOf(
                "mid9-math-08-volume-area"
            ),
            checkLessonId = "mid9-math-08-volume-area",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 410,
            title = "علوم نهم ۱: مواد و نقش آن‌ها در زندگی — از صفر",
            subtitle = "پایه نهم متوسطه اول — علوم تجربی",
            lessonIds = listOf(
                "mid9-science-01-materials-life"
            ),
            checkLessonId = "mid9-science-01-materials-life",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 411,
            title = "علوم نهم ۲: رفتار اتم‌ها با یکدیگر — از صفر",
            subtitle = "پایه نهم متوسطه اول — علوم تجربی",
            lessonIds = listOf(
                "mid9-science-02-atoms-behavior"
            ),
            checkLessonId = "mid9-science-02-atoms-behavior",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 412,
            title = "علوم نهم ۳: به دنبال محیطی بهتر برای زندگی — از صفر",
            subtitle = "پایه نهم متوسطه اول — علوم تجربی",
            lessonIds = listOf(
                "mid9-science-03-better-environment"
            ),
            checkLessonId = "mid9-science-03-better-environment",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 413,
            title = "علوم نهم ۴: حرکت چیست؟ — از صفر",
            subtitle = "پایه نهم متوسطه اول — علوم تجربی",
            lessonIds = listOf(
                "mid9-science-04-motion"
            ),
            checkLessonId = "mid9-science-04-motion",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 414,
            title = "علوم نهم ۵: نیرو — از صفر",
            subtitle = "پایه نهم متوسطه اول — علوم تجربی",
            lessonIds = listOf(
                "mid9-science-05-force"
            ),
            checkLessonId = "mid9-science-05-force",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 415,
            title = "علوم نهم ۶: زمین‌ساخت ورقه‌ای — از صفر",
            subtitle = "پایه نهم متوسطه اول — علوم تجربی",
            lessonIds = listOf(
                "mid9-science-06-plate-tectonics"
            ),
            checkLessonId = "mid9-science-06-plate-tectonics",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 416,
            title = "علوم نهم ۷: آثاری از گذشته زمین — از صفر",
            subtitle = "پایه نهم متوسطه اول — علوم تجربی",
            lessonIds = listOf(
                "mid9-science-07-earth-past"
            ),
            checkLessonId = "mid9-science-07-earth-past",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 417,
            title = "علوم نهم، فصل ۸: فشار و آثار آن — از صفر",
            subtitle = "پایه نهم متوسطه اول — علوم تجربی",
            lessonIds = listOf(
                "mid9-science-08-pressure"
            ),
            checkLessonId = "mid9-science-08-pressure",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 418,
            title = "علوم نهم، فصل ۹: ماشین‌ها — از صفر",
            subtitle = "پایه نهم متوسطه اول — علوم تجربی",
            lessonIds = listOf(
                "mid9-science-09-machines"
            ),
            checkLessonId = "mid9-science-09-machines",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 419,
            title = "علوم نهم، فصل ۱۰: نگاهی به فضا — از صفر",
            subtitle = "پایه نهم متوسطه اول — علوم تجربی",
            lessonIds = listOf(
                "mid9-science-10-space"
            ),
            checkLessonId = "mid9-science-10-space",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 420,
            title = "علوم نهم، فصل ۱۱: گوناگونی جانداران — از صفر",
            subtitle = "پایه نهم متوسطه اول — علوم تجربی",
            lessonIds = listOf(
                "mid9-science-11-biodiversity"
            ),
            checkLessonId = "mid9-science-11-biodiversity",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 421,
            title = "علوم نهم، فصل ۱۲: دنیای گیاهان — از صفر",
            subtitle = "پایه نهم متوسطه اول — علوم تجربی",
            lessonIds = listOf(
                "mid9-science-12-plants"
            ),
            checkLessonId = "mid9-science-12-plants",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 422,
            title = "علوم نهم، فصل ۱۳: جانوران بی‌مهره — از صفر",
            subtitle = "پایه نهم متوسطه اول — علوم تجربی",
            lessonIds = listOf(
                "mid9-science-13-invertebrates"
            ),
            checkLessonId = "mid9-science-13-invertebrates",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 423,
            title = "علوم نهم، فصل ۱۴: جانوران مهره‌دار — از صفر",
            subtitle = "پایه نهم متوسطه اول — علوم تجربی",
            lessonIds = listOf(
                "mid9-science-14-vertebrates"
            ),
            checkLessonId = "mid9-science-14-vertebrates",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 424,
            title = "علوم نهم، فصل ۱۵: با هم زیستن — از صفر",
            subtitle = "پایه نهم متوسطه اول — علوم تجربی",
            lessonIds = listOf(
                "mid9-science-15-living-together"
            ),
            checkLessonId = "mid9-science-15-living-together",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 425,
            title = "عربی نهم، درس ۱: مراجعة دروس الصف السابع و الثامن — مرور درس‌های هفتم و هشتم",
            subtitle = "پایه نهم متوسطه اول — عربی",
            lessonIds = listOf(
                "mid9-arabic-01-review"
            ),
            checkLessonId = "mid9-arabic-01-review",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 426,
            title = "عربی نهم، درس ۲: العبور الآمن — عبور امن",
            subtitle = "پایه نهم متوسطه اول — عربی",
            lessonIds = listOf(
                "mid9-arabic-02-safe-crossing"
            ),
            checkLessonId = "mid9-arabic-02-safe-crossing",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 427,
            title = "عربی نهم درس ۳: جسر الصداقة (پل دوستی) + فعل امر — از صفر",
            subtitle = "پایه نهم متوسطه اول — عربی",
            lessonIds = listOf(
                "mid9-arabic-03-friendship-bridge"
            ),
            checkLessonId = "mid9-arabic-03-friendship-bridge",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 428,
            title = "عربی نهم درس ۴: الصبر مفتاح الفرج (صبر کلید گشایش است) + فعل امر — از صفر",
            subtitle = "پایه نهم متوسطه اول — عربی",
            lessonIds = listOf(
                "mid9-arabic-04-patience-key"
            ),
            checkLessonId = "mid9-arabic-04-patience-key",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 429,
            title = "عربی نهم درس ۵: الرجاء (امید) + نون تاکید — از صفر",
            subtitle = "پایه نهم متوسطه اول — عربی",
            lessonIds = listOf(
                "mid9-arabic-05-hope"
            ),
            checkLessonId = "mid9-arabic-05-hope",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 430,
            title = "عربی نهم درس ۶: تغییر الحیاة (تغییر زندگی) — از صفر",
            subtitle = "پایه نهم متوسطه اول — عربی",
            lessonIds = listOf(
                "mid9-arabic-06-life-change"
            ),
            checkLessonId = "mid9-arabic-06-life-change",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 431,
            title = "عربی نهم درس ۷: ثمرة الجد (میوه تلاش) + نون تاکید — از صفر",
            subtitle = "پایه نهم متوسطه اول — عربی",
            lessonIds = listOf(
                "mid9-arabic-07-fruit-of-effort"
            ),
            checkLessonId = "mid9-arabic-07-fruit-of-effort",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 432,
            title = "عربی نهم درس ۸: حوار بین الزائر و سائق سیارة الأجرة (گفت‌وگو میان زائر و راننده تاکسی) — از صفر",
            subtitle = "پایه نهم متوسطه اول — عربی",
            lessonIds = listOf(
                "mid9-arabic-08-taxi-dialogue"
            ),
            checkLessonId = "mid9-arabic-08-taxi-dialogue",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 433,
            title = "عربی نهم درس ۹: نصوص حول الصحة (متن‌هایی درباره سلامتی) — از صفر",
            subtitle = "پایه نهم متوسطه اول — عربی",
            lessonIds = listOf(
                "mid9-arabic-09-health-texts"
            ),
            checkLessonId = "mid9-arabic-09-health-texts",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 434,
            title = "عربی نهم درس ۱۰: رسالة الشهید سلیمانی (نامه شهید سلیمانی) — از صفر",
            subtitle = "پایه نهم متوسطه اول — عربی",
            lessonIds = listOf(
                "mid9-arabic-10-soleimani-letter"
            ),
            checkLessonId = "mid9-arabic-10-soleimani-letter",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 435,
            title = "انگلیسی نهم درس ۱: Personality (شخصیت) — از صفر",
            subtitle = "پایه نهم متوسطه اول — زبان انگلیسی",
            lessonIds = listOf(
                "mid9-english-01-personality"
            ),
            checkLessonId = "mid9-english-01-personality",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 436,
            title = "انگلیسی نهم درس ۲: Travel (سفر) — از صفر",
            subtitle = "پایه نهم متوسطه اول — زبان انگلیسی",
            lessonIds = listOf(
                "mid9-english-02-travel"
            ),
            checkLessonId = "mid9-english-02-travel",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 437,
            title = "انگلیسی نهم ۳: جشن‌ها و مراسم‌ها — Festivals and Ceremonies",
            subtitle = "پایه نهم متوسطه اول — زبان انگلیسی",
            lessonIds = listOf(
                "mid9-english-03-festivals"
            ),
            checkLessonId = "mid9-english-03-festivals",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 438,
            title = "انگلیسی نهم ۴: خدمت‌ها — Services",
            subtitle = "پایه نهم متوسطه اول — زبان انگلیسی",
            lessonIds = listOf(
                "mid9-english-04-services"
            ),
            checkLessonId = "mid9-english-04-services",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 439,
            title = "انگلیسی نهم ۵: رسانه‌ها — Media",
            subtitle = "پایه نهم متوسطه اول — زبان انگلیسی",
            lessonIds = listOf(
                "mid9-english-05-media"
            ),
            checkLessonId = "mid9-english-05-media",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 440,
            title = "انگلیسی نهم ۶: سلامتی و آسیب‌ها — Health and Injuries",
            subtitle = "پایه نهم متوسطه اول — زبان انگلیسی",
            lessonIds = listOf(
                "mid9-english-06-health"
            ),
            checkLessonId = "mid9-english-06-health",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 441,
            title = "مطالعات نهم ۱: زمین، مهد زیبای انسان‌ها",
            subtitle = "پایه نهم متوسطه اول — مطالعات اجتماعی",
            lessonIds = listOf(
                "mid9-social-01-earth-home"
            ),
            checkLessonId = "mid9-social-01-earth-home",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 442,
            title = "مطالعات نهم ۲: حرکات زمین",
            subtitle = "پایه نهم متوسطه اول — مطالعات اجتماعی",
            lessonIds = listOf(
                "mid9-social-02-earth-movements"
            ),
            checkLessonId = "mid9-social-02-earth-movements",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 443,
            title = "مطالعات نهم ۳: چهره زمین",
            subtitle = "پایه نهم متوسطه اول — مطالعات اجتماعی",
            lessonIds = listOf(
                "mid9-social-03-earth-face"
            ),
            checkLessonId = "mid9-social-03-earth-face",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 444,
            title = "مطالعات نهم ۴: آب فراوان، هوای پاک",
            subtitle = "پایه نهم متوسطه اول — مطالعات اجتماعی",
            lessonIds = listOf(
                "mid9-social-04-water-air"
            ),
            checkLessonId = "mid9-social-04-water-air",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 445,
            title = "مطالعات نهم ۵: پراکندگی زیست‌بوم‌های جهان",
            subtitle = "پایه نهم متوسطه اول — مطالعات اجتماعی",
            lessonIds = listOf(
                "mid9-social-05-biomes"
            ),
            checkLessonId = "mid9-social-05-biomes",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 446,
            title = "مطالعات نهم ۶: زیست‌بوم‌ها در خطرند",
            subtitle = "پایه نهم متوسطه اول — مطالعات اجتماعی",
            lessonIds = listOf(
                "mid9-social-06-biomes-danger"
            ),
            checkLessonId = "mid9-social-06-biomes-danger",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 447,
            title = "مطالعات اجتماعی نهم ۷: جمعیت جهان — چرا جمعیت زیاد و کم می‌شود؟",
            subtitle = "پایه نهم متوسطه اول — مطالعات اجتماعی",
            lessonIds = listOf(
                "mid9-social-07-population"
            ),
            checkLessonId = "mid9-social-07-population",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 448,
            title = "مطالعات اجتماعی نهم ۸: بی‌عدالتی و نابرابری در جهان — چرا بعضی کشورها ثروتمندند و بعضی فقیر؟",
            subtitle = "پایه نهم متوسطه اول — مطالعات اجتماعی",
            lessonIds = listOf(
                "mid9-social-08-inequality"
            ),
            checkLessonId = "mid9-social-08-inequality",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 449,
            title = "مطالعات اجتماعی نهم ۹: ایرانی متحد و یکپارچه — صفویان چطور ایران را یکی کردند؟",
            subtitle = "پایه نهم متوسطه اول — مطالعات اجتماعی",
            lessonIds = listOf(
                "mid9-social-09-united-iran"
            ),
            checkLessonId = "mid9-social-09-united-iran",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 450,
            title = "مطالعات اجتماعی نهم ۱۰: اوضاع اجتماعی، اقتصادی، علمی و فرهنگی ایران در عصر صفوی",
            subtitle = "پایه نهم متوسطه اول — مطالعات اجتماعی",
            lessonIds = listOf(
                "mid9-social-10-safavid-society"
            ),
            checkLessonId = "mid9-social-10-safavid-society",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 451,
            title = "مطالعات اجتماعی نهم ۱۱: تلاش برای حفظ استقلال و اتحاد سیاسی ایران",
            subtitle = "پایه نهم متوسطه اول — مطالعات اجتماعی",
            lessonIds = listOf(
                "mid9-social-11-independence"
            ),
            checkLessonId = "mid9-social-11-independence",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 452,
            title = "مطالعات اجتماعی نهم ۱۲: در جستجوی پیشرفت و رهایی از سلطه خارجی",
            subtitle = "پایه نهم متوسطه اول — مطالعات اجتماعی",
            lessonIds = listOf(
                "mid9-social-12-progress"
            ),
            checkLessonId = "mid9-social-12-progress",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 453,
            title = "مطالعات اجتماعی نهم ۱۳: نهضت مشروطه — مردم چطور شاه را محدود کردند؟",
            subtitle = "پایه نهم متوسطه اول — مطالعات اجتماعی",
            lessonIds = listOf(
                "mid9-social-13-constitutional"
            ),
            checkLessonId = "mid9-social-13-constitutional",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 454,
            title = "مطالعات اجتماعی نهم ۱۴: ایران در دوران حکومت پهلوی",
            subtitle = "پایه نهم متوسطه اول — مطالعات اجتماعی",
            lessonIds = listOf(
                "mid9-social-14-pahlavi"
            ),
            checkLessonId = "mid9-social-14-pahlavi",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 455,
            title = "مطالعات اجتماعی نهم ۱۵: انقلاب اسلامی ایران — از ۱۵ خرداد تا ۲۲ بهمن",
            subtitle = "پایه نهم متوسطه اول — مطالعات اجتماعی",
            lessonIds = listOf(
                "mid9-social-15-revolution"
            ),
            checkLessonId = "mid9-social-15-revolution",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 456,
            title = "مطالعات اجتماعی نهم ۱۶: ایران در دوران پس از انقلاب اسلامی",
            subtitle = "پایه نهم متوسطه اول — مطالعات اجتماعی",
            lessonIds = listOf(
                "mid9-social-16-after-revolution"
            ),
            checkLessonId = "mid9-social-16-after-revolution",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 457,
            title = "مطالعات نهم ۱۷: فرهنگ یعنی چی؟ — از صفر",
            subtitle = "پایه نهم متوسطه اول — مطالعات اجتماعی",
            lessonIds = listOf(
                "mid9-social-17-culture"
            ),
            checkLessonId = "mid9-social-17-culture",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 458,
            title = "مطالعات نهم ۱۸: هویت یعنی چی؟ — از صفر",
            subtitle = "پایه نهم متوسطه اول — مطالعات اجتماعی",
            lessonIds = listOf(
                "mid9-social-18-identity"
            ),
            checkLessonId = "mid9-social-18-identity",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 459,
            title = "مطالعات نهم ۱۹: ارزش‌ها و کارکردهای خانواده — از صفر",
            subtitle = "پایه نهم متوسطه اول — مطالعات اجتماعی",
            lessonIds = listOf(
                "mid9-social-19-family-values"
            ),
            checkLessonId = "mid9-social-19-family-values",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 460,
            title = "مطالعات نهم ۲۰: آرامش در خانواده — از صفر",
            subtitle = "پایه نهم متوسطه اول — مطالعات اجتماعی",
            lessonIds = listOf(
                "mid9-social-20-family-calm"
            ),
            checkLessonId = "mid9-social-20-family-calm",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 461,
            title = "مطالعات نهم ۲۱: نهاد حکومت یعنی چی؟ — از صفر",
            subtitle = "پایه نهم متوسطه اول — مطالعات اجتماعی",
            lessonIds = listOf(
                "mid9-social-21-government"
            ),
            checkLessonId = "mid9-social-21-government",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 462,
            title = "مطالعات نهم ۲۲: حقوق و تکالیف شهروندی — از صفر",
            subtitle = "پایه نهم متوسطه اول — مطالعات اجتماعی",
            lessonIds = listOf(
                "mid9-social-22-citizen-rights"
            ),
            checkLessonId = "mid9-social-22-citizen-rights",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 463,
            title = "مطالعات نهم ۲۳: بهره‌وری چیست؟ — از صفر",
            subtitle = "پایه نهم متوسطه اول — مطالعات اجتماعی",
            lessonIds = listOf(
                "mid9-social-23-productivity"
            ),
            checkLessonId = "mid9-social-23-productivity",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 464,
            title = "مطالعات نهم ۲۴: اقتصاد و بهره‌وری — از صفر",
            subtitle = "پایه نهم متوسطه اول — مطالعات اجتماعی",
            lessonIds = listOf(
                "mid9-social-24-economy-productivity"
            ),
            checkLessonId = "mid9-social-24-economy-productivity",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 465,
            title = "پیام نهم ۱: تو را چگونه بشناسم؟ — از صفر",
            subtitle = "پایه نهم متوسطه اول — پیام‌های آسمان",
            lessonIds = listOf(
                "mid9-payam-01-know-god"
            ),
            checkLessonId = "mid9-payam-01-know-god",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 466,
            title = "پیام نهم ۲: در پناه ایمان — از صفر",
            subtitle = "پایه نهم متوسطه اول — پیام‌های آسمان",
            lessonIds = listOf(
                "mid9-payam-02-faith-shelter"
            ),
            checkLessonId = "mid9-payam-02-faith-shelter",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 467,
            title = "پیام‌های آسمان نهم درس ۳: راهنمایان الهی — پیامبران، سرمشق زندگی",
            subtitle = "پایه نهم متوسطه اول — پیام‌های آسمان",
            lessonIds = listOf(
                "mid9-payam-03-rahnamayan-elahi"
            ),
            checkLessonId = "mid9-payam-03-rahnamayan-elahi",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 468,
            title = "پیام‌های آسمان نهم درس ۴: خورشید پنهان — امام زمان و دوران ظهور",
            subtitle = "پایه نهم متوسطه اول — پیام‌های آسمان",
            lessonIds = listOf(
                "mid9-payam-04-khorshid-penhan"
            ),
            checkLessonId = "mid9-payam-04-khorshid-penhan",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 469,
            title = "پیام‌های آسمان نهم درس ۵: رهبری در دوران غیبت — به چه کسی مراجعه کنیم؟",
            subtitle = "پایه نهم متوسطه اول — پیام‌های آسمان",
            lessonIds = listOf(
                "mid9-payam-05-rahbari-gheybat"
            ),
            checkLessonId = "mid9-payam-05-rahbari-gheybat",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 470,
            title = "پیام‌های آسمان نهم درس ۶: وضو، غسل و تیمم — طهارت برای نماز",
            subtitle = "پایه نهم متوسطه اول — پیام‌های آسمان",
            lessonIds = listOf(
                "mid9-payam-06-vozu-ghosl-tayammom"
            ),
            checkLessonId = "mid9-payam-06-vozu-ghosl-tayammom",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 471,
            title = "پیام‌های آسمان نهم درس ۷: احکام نماز — نمازهای واجب، وقت و مبطلات",
            subtitle = "پایه نهم متوسطه اول — پیام‌های آسمان",
            lessonIds = listOf(
                "mid9-payam-07-ahkam-namaz"
            ),
            checkLessonId = "mid9-payam-07-ahkam-namaz",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 472,
            title = "پیام‌های آسمان نهم درس ۸: همدلی و همراهی — برادری دینی در عمل",
            subtitle = "پایه نهم متوسطه اول — پیام‌های آسمان",
            lessonIds = listOf(
                "mid9-payam-08-hamdeli-hamrahi"
            ),
            checkLessonId = "mid9-payam-08-hamdeli-hamrahi",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 473,
            title = "پیام‌های آسمان نهم درس ۹: انقلاب اسلامی ایران — چرا و چگونه پیروز شد؟",
            subtitle = "پایه نهم متوسطه اول — پیام‌های آسمان",
            lessonIds = listOf(
                "mid9-payam-09-enghelab-islami"
            ),
            checkLessonId = "mid9-payam-09-enghelab-islami",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 474,
            title = "پیام‌های آسمان نهم درس ۱۰: مسئولیت همگانی — امر به معروف و نهی از منکر",
            subtitle = "پایه نهم متوسطه اول — پیام‌های آسمان",
            lessonIds = listOf(
                "mid9-payam-10-masouliat-hamegani"
            ),
            checkLessonId = "mid9-payam-10-masouliat-hamegani",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 475,
            title = "پیام‌های آسمان نهم درس ۱۱: انفاق — بخشیدن در راه خدا",
            subtitle = "پایه نهم متوسطه اول — پیام‌های آسمان",
            lessonIds = listOf(
                "mid9-payam-11-enfagh"
            ),
            checkLessonId = "mid9-payam-11-enfagh",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 476,
            title = "پیام‌های آسمان نهم درس ۱۲: جهاد — جهاد اکبر و جهاد اصغر",
            subtitle = "پایه نهم متوسطه اول — پیام‌های آسمان",
            lessonIds = listOf(
                "mid9-payam-12-jahad"
            ),
            checkLessonId = "mid9-payam-12-jahad",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 477,
            title = "کار و فناوری نهم ۱: الگوریتم — از صفر",
            subtitle = "پایه نهم متوسطه اول — کار و فناوری",
            lessonIds = listOf(
                "mid9-karfan-01-algoritm"
            ),
            checkLessonId = "mid9-karfan-01-algoritm",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 478,
            title = "کار و فناوری نهم ۲: ترسیم با رایانه — از صفر",
            subtitle = "پایه نهم متوسطه اول — کار و فناوری",
            lessonIds = listOf(
                "mid9-karfan-02-tarsim-rayaneh"
            ),
            checkLessonId = "mid9-karfan-02-tarsim-rayaneh",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 479,
            title = "کار و فناوری نهم ۳: ساز و کارهای حرکتی — از صفر",
            subtitle = "پایه نهم متوسطه اول — کار و فناوری",
            lessonIds = listOf(
                "mid9-karfan-03-sazokar-harekati"
            ),
            checkLessonId = "mid9-karfan-03-sazokar-harekati",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 480,
            title = "کار و فناوری نهم ۴: برنامه‌نویسی پایتون (۳) — از صفر",
            subtitle = "پایه نهم متوسطه اول — کار و فناوری",
            lessonIds = listOf(
                "mid9-karfan-04-python-3"
            ),
            checkLessonId = "mid9-karfan-04-python-3",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 481,
            title = "کار و فناوری نهم ۵: هدایت تحصیلی–حرفه‌ای — از صفر",
            subtitle = "پایه نهم متوسطه اول — کار و فناوری",
            lessonIds = listOf(
                "mid9-karfan-05-hedayat-tahsili"
            ),
            checkLessonId = "mid9-karfan-05-hedayat-tahsili",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 482,
            title = "کار و فناوری نهم ۶: برق — از صفر",
            subtitle = "پایه نهم متوسطه اول — کار و فناوری",
            lessonIds = listOf(
                "mid9-karfan-06-bargh"
            ),
            checkLessonId = "mid9-karfan-06-bargh",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 483,
            title = "کار و فناوری نهم ۷: تأسیسات مکانیکی — از صفر",
            subtitle = "پایه نهم متوسطه اول — کار و فناوری",
            lessonIds = listOf(
                "mid9-karfan-07-tasisat"
            ),
            checkLessonId = "mid9-karfan-07-tasisat",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 484,
            title = "کار و فناوری نهم ۸: عمران — از صفر",
            subtitle = "پایه نهم متوسطه اول — کار و فناوری",
            lessonIds = listOf(
                "mid9-karfan-08-omran"
            ),
            checkLessonId = "mid9-karfan-08-omran",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 485,
            title = "کار و فناوری نهم ۹: خودرو — از صفر",
            subtitle = "پایه نهم متوسطه اول — کار و فناوری",
            lessonIds = listOf(
                "mid9-karfan-09-khodro"
            ),
            checkLessonId = "mid9-karfan-09-khodro",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 486,
            title = "کار و فناوری نهم ۱۰: پایش رشد و تکامل کودک — از صفر",
            subtitle = "پایه نهم متوسطه اول — کار و فناوری",
            lessonIds = listOf(
                "mid9-karfan-10-koodak"
            ),
            checkLessonId = "mid9-karfan-10-koodak",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 487,
            title = "کار و فناوری نهم ۱۱: صنایع دستی — برجسته‌کاری روی فلز مس — از صفر",
            subtitle = "پایه نهم متوسطه اول — کار و فناوری",
            lessonIds = listOf(
                "mid9-karfan-11-mes"
            ),
            checkLessonId = "mid9-karfan-11-mes",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 488,
            title = "آمادگی دفاعی نهم ۱: تهدید چیست؟ دشمن کیست؟ — از صفر",
            subtitle = "پایه نهم متوسطه اول — آمادگی دفاعی",
            lessonIds = listOf(
                "mid9-defa-01-tahdid-doshman"
            ),
            checkLessonId = "mid9-defa-01-tahdid-doshman",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 489,
            title = "آمادگی دفاعی نهم ۲: موقعیت ایران و تهدیدهای آن — از صفر",
            subtitle = "پایه نهم متوسطه اول — آمادگی دفاعی",
            lessonIds = listOf(
                "mid9-defa-02-mogheiyat-iran"
            ),
            checkLessonId = "mid9-defa-02-mogheiyat-iran",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 490,
            title = "آمادگی دفاعی نهم ۳: روایت فتح — از صفر",
            subtitle = "پایه نهم متوسطه اول — آمادگی دفاعی",
            lessonIds = listOf(
                "mid9-defa-03-revayat-fath"
            ),
            checkLessonId = "mid9-defa-03-revayat-fath",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 491,
            title = "آمادگی دفاعی نهم ۴: باید برخاست — از صفر",
            subtitle = "پایه نهم متوسطه اول — آمادگی دفاعی",
            lessonIds = listOf(
                "mid9-defa-04-bayad-barkhast"
            ),
            checkLessonId = "mid9-defa-04-bayad-barkhast",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 492,
            title = "آمادگی دفاعی نهم ۵: رویارویی قدرت‌ها — از صفر",
            subtitle = "پایه نهم متوسطه اول — آمادگی دفاعی",
            lessonIds = listOf(
                "mid9-defa-05-royaruyi-ghodratha"
            ),
            checkLessonId = "mid9-defa-05-royaruyi-ghodratha",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 493,
            title = "آمادگی دفاعی نهم ۶: نظام جمع و مهارت‌های رزم — از صفر",
            subtitle = "پایه نهم متوسطه اول — آمادگی دفاعی",
            lessonIds = listOf(
                "mid9-defa-06-nezam-jam"
            ),
            checkLessonId = "mid9-defa-06-nezam-jam",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 494,
            title = "آمادگی دفاعی نهم ۷: پدافند غیرعامل — از صفر",
            subtitle = "پایه نهم متوسطه اول — آمادگی دفاعی",
            lessonIds = listOf(
                "mid9-defa-07-padafand"
            ),
            checkLessonId = "mid9-defa-07-padafand",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 495,
            title = "آمادگی دفاعی نهم ۸: جنگ پنهان — از صفر",
            subtitle = "پایه نهم متوسطه اول — آمادگی دفاعی",
            lessonIds = listOf(
                "mid9-defa-08-jang-penhan"
            ),
            checkLessonId = "mid9-defa-08-jang-penhan",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 496,
            title = "آمادگی دفاعی نهم ۹: امداد و نجات — از صفر",
            subtitle = "پایه نهم متوسطه اول — آمادگی دفاعی",
            lessonIds = listOf(
                "mid9-defa-09-emdad"
            ),
            checkLessonId = "mid9-defa-09-emdad",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 497,
            title = "فرهنگ و هنر نهم ۱: اهمیت و کاربرد طراحی در زندگی",
            subtitle = "پایه نهم متوسطه اول — فرهنگ و هنر",
            lessonIds = listOf(
                "mid9-honar-01-tarahi-karbord"
            ),
            checkLessonId = "mid9-honar-01-tarahi-karbord",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 498,
            title = "فرهنگ و هنر نهم ۲: طراحی از اشیا",
            subtitle = "پایه نهم متوسطه اول — فرهنگ و هنر",
            lessonIds = listOf(
                "mid9-honar-02-tarahi-ashya"
            ),
            checkLessonId = "mid9-honar-02-tarahi-ashya",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 499,
            title = "فرهنگ و هنر نهم ۳: ترکیب‌بندی",
            subtitle = "پایه نهم متوسطه اول — فرهنگ و هنر",
            lessonIds = listOf(
                "mid9-honar-03-tarkibbandi"
            ),
            checkLessonId = "mid9-honar-03-tarkibbandi",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 500,
            title = "فرهنگ و هنر نهم ۴: نگاشتار و تبلیغات ۱ — نگاشتار چیست",
            subtitle = "پایه نهم متوسطه اول — فرهنگ و هنر",
            lessonIds = listOf(
                "mid9-honar-04-negashtar-chist"
            ),
            checkLessonId = "mid9-honar-04-negashtar-chist",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 501,
            title = "فرهنگ و هنر نهم ۵: نگاشتار و تبلیغات ۲ — پیام و مخاطب در تبلیغات",
            subtitle = "پایه نهم متوسطه اول — فرهنگ و هنر",
            lessonIds = listOf(
                "mid9-honar-05-payam-mokhatab"
            ),
            checkLessonId = "mid9-honar-05-payam-mokhatab",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 502,
            title = "فرهنگ و هنر نهم ۶: نگاشتار و تبلیغات ۳ — طراحی یک نگاشتار تبلیغاتی",
            subtitle = "پایه نهم متوسطه اول — فرهنگ و هنر",
            lessonIds = listOf(
                "mid9-honar-06-tarahi-negashtar"
            ),
            checkLessonId = "mid9-honar-06-tarahi-negashtar",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 503,
            title = "فرهنگ و هنر نهم ۷: عکس‌های ترکیبی",
            subtitle = "پایه نهم متوسطه اول — فرهنگ و هنر",
            lessonIds = listOf(
                "mid9-honar-07-aks-tarkibi"
            ),
            checkLessonId = "mid9-honar-07-aks-tarkibi",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 504,
            title = "فرهنگ و هنر نهم ۸: عکس‌های روایی",
            subtitle = "پایه نهم متوسطه اول — فرهنگ و هنر",
            lessonIds = listOf(
                "mid9-honar-08-aks-ravayi"
            ),
            checkLessonId = "mid9-honar-08-aks-ravayi",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 505,
            title = "فرهنگ و هنر نهم ۹: گرایش‌های عکاسی",
            subtitle = "پایه نهم متوسطه اول — فرهنگ و هنر",
            lessonIds = listOf(
                "mid9-honar-09-gerayesh-aks"
            ),
            checkLessonId = "mid9-honar-09-gerayesh-aks",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 506,
            title = "فرهنگ و هنر نهم ۱۰: چهار لنگه",
            subtitle = "پایه نهم متوسطه اول — فرهنگ و هنر",
            lessonIds = listOf(
                "mid9-honar-10-chahar-lengeh"
            ),
            checkLessonId = "mid9-honar-10-chahar-lengeh",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 507,
            title = "فرهنگ و هنر نهم ۱۱: نحوه تراشیدن قلم و قالب‌های اجرایی خوشنویسی",
            subtitle = "پایه نهم متوسطه اول — فرهنگ و هنر",
            lessonIds = listOf(
                "mid9-honar-11-qalam-tarashidan"
            ),
            checkLessonId = "mid9-honar-11-qalam-tarashidan",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 508,
            title = "فرهنگ و هنر نهم ۱۲: معرفی خط نستعلیق و مشق سطرنویسی",
            subtitle = "پایه نهم متوسطه اول — فرهنگ و هنر",
            lessonIds = listOf(
                "mid9-honar-12-nastaliq-satr"
            ),
            checkLessonId = "mid9-honar-12-nastaliq-satr",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 509,
            title = "فرهنگ و هنر نهم ۱۳: معرفی خط شکسته نستعلیق و مشق دوسطری‌نویسی",
            subtitle = "پایه نهم متوسطه اول — فرهنگ و هنر",
            lessonIds = listOf(
                "mid9-honar-13-shekaste-dostri"
            ),
            checkLessonId = "mid9-honar-13-shekaste-dostri",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 510,
            title = "فرهنگ و هنر نهم ۱۴: گرایش‌های هنری با بنیان خوشنویسی و مشق دفتری‌نویسی",
            subtitle = "پایه نهم متوسطه اول — فرهنگ و هنر",
            lessonIds = listOf(
                "mid9-honar-14-gerayesh-daftari"
            ),
            checkLessonId = "mid9-honar-14-gerayesh-daftari",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 511,
            title = "فرهنگ و هنر نهم ۱۵: گره هشت و چهارلنگه",
            subtitle = "پایه نهم متوسطه اول — فرهنگ و هنر",
            lessonIds = listOf(
                "mid9-honar-15-gereh-hasht"
            ),
            checkLessonId = "mid9-honar-15-gereh-hasht",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 512,
            title = "فرهنگ و هنر نهم ۱۶: بته / بته‌جقه",
            subtitle = "پایه نهم متوسطه اول — فرهنگ و هنر",
            lessonIds = listOf(
                "mid9-honar-16-boteh"
            ),
            checkLessonId = "mid9-honar-16-boteh",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 513,
            title = "فرهنگ و هنر نهم ۱۷: جلدسازی سنتی (زیرلاکی با روش لایه‌های کاغذ)",
            subtitle = "پایه نهم متوسطه اول — فرهنگ و هنر",
            lessonIds = listOf(
                "mid9-honar-17-jeldsazi"
            ),
            checkLessonId = "mid9-honar-17-jeldsazi",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 514,
            title = "فرهنگ و هنر نهم ۱۸: جعبه‌های روغنی (زیرلاکی با روش نوار کاغذ)",
            subtitle = "پایه نهم متوسطه اول — فرهنگ و هنر",
            lessonIds = listOf(
                "mid9-honar-18-jabeh-roghani"
            ),
            checkLessonId = "mid9-honar-18-jabeh-roghani",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 515,
            title = "فرهنگ و هنر نهم ۱۹: اشیای روغنی (زیرلاکی روی چوب)",
            subtitle = "پایه نهم متوسطه اول — فرهنگ و هنر",
            lessonIds = listOf(
                "mid9-honar-19-ashya-roghani"
            ),
            checkLessonId = "mid9-honar-19-ashya-roghani",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 516,
            title = "فرهنگ و هنر نهم ۲۰: درویش‌دوزی",
            subtitle = "پایه نهم متوسطه اول — فرهنگ و هنر",
            lessonIds = listOf(
                "mid9-honar-20-darvishduzi"
            ),
            checkLessonId = "mid9-honar-20-darvishduzi",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 517,
            title = "فرهنگ و هنر نهم ۲۱: زرتشتی‌دوزی",
            subtitle = "پایه نهم متوسطه اول — فرهنگ و هنر",
            lessonIds = listOf(
                "mid9-honar-21-zartoshtiduzi"
            ),
            checkLessonId = "mid9-honar-21-zartoshtiduzi",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 518,
            title = "فرهنگ و هنر نهم ۲۲: پته‌دوزی",
            subtitle = "پایه نهم متوسطه اول — فرهنگ و هنر",
            lessonIds = listOf(
                "mid9-honar-22-patehduzi"
            ),
            checkLessonId = "mid9-honar-22-patehduzi",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 519,
            title = "فرهنگ و هنر نهم ۲۳: آواهای قومی (مردمی)",
            subtitle = "پایه نهم متوسطه اول — فرهنگ و هنر",
            lessonIds = listOf(
                "mid9-honar-23-avaha-qowmi"
            ),
            checkLessonId = "mid9-honar-23-avaha-qowmi",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 520,
            title = "فرهنگ و هنر نهم ۲۴: ردیف دستگاهی ایران",
            subtitle = "پایه نهم متوسطه اول — فرهنگ و هنر",
            lessonIds = listOf(
                "mid9-honar-24-radif-dastgahi"
            ),
            checkLessonId = "mid9-honar-24-radif-dastgahi",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 521,
            title = "فرهنگ و هنر نهم ۲۵: آواهای دینی ایران",
            subtitle = "پایه نهم متوسطه اول — فرهنگ و هنر",
            lessonIds = listOf(
                "mid9-honar-25-avaha-dini"
            ),
            checkLessonId = "mid9-honar-25-avaha-dini",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 522,
            title = "فرهنگ و هنر نهم ۲۶: وزن و ریتم در آواهای ایرانی",
            subtitle = "پایه نهم متوسطه اول — فرهنگ و هنر",
            lessonIds = listOf(
                "mid9-honar-26-vazn-ritm"
            ),
            checkLessonId = "mid9-honar-26-vazn-ritm",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 523,
            title = "فرهنگ و هنر نهم ۲۷: شکل (فرم) و گونه (ژانر)‌های آوایی",
            subtitle = "پایه نهم متوسطه اول — فرهنگ و هنر",
            lessonIds = listOf(
                "mid9-honar-27-shekl-guneh"
            ),
            checkLessonId = "mid9-honar-27-shekl-guneh",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 524,
            title = "فرهنگ و هنر نهم ۲۸: کارگردانی و انتخاب نمایشنامه",
            subtitle = "پایه نهم متوسطه اول — فرهنگ و هنر",
            lessonIds = listOf(
                "mid9-honar-28-kargardani"
            ),
            checkLessonId = "mid9-honar-28-kargardani",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 525,
            title = "فرهنگ و هنر نهم ۲۹: انتخاب بازیگر و خواندن متن",
            subtitle = "پایه نهم متوسطه اول — فرهنگ و هنر",
            lessonIds = listOf(
                "mid9-honar-29-bazigar-khanesh"
            ),
            checkLessonId = "mid9-honar-29-bazigar-khanesh",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
        LearningPathStep(
            stepNumber = 526,
            title = "فرهنگ و هنر نهم ۳۰: حرکت و چیدمان روی صحنه",
            subtitle = "پایه نهم متوسطه اول — فرهنگ و هنر",
            lessonIds = listOf(
                "mid9-honar-30-harekat-chideman"
            ),
            checkLessonId = "mid9-honar-30-harekat-chideman",
            status = LearningPathStatus.READY,
            gapNote = ""
        ),
    )
}
