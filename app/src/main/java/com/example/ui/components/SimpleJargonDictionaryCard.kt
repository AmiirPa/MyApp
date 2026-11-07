package com.example.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.ExpandLess
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.material.icons.filled.Lightbulb
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Translate
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

data class PlainJargonEntry(
    val subjectId: String,
    val subCategory: String,
    val scaryTerm: String,
    val streetMeaning: String,
    val realLifeExample: String,
    val konkoorShortcut: String,
    val relatedKeywords: List<String> = emptyList()
)

object UniversalPlainJargonCatalog {

    val allEntries: List<PlainJargonEntry> = listOf(
        // =========================================================================
        // ۱. ریاضی و آمار (math)
        // =========================================================================
        PlainJargonEntry(
            subjectId = "math",
            subCategory = "معادله و تابع",
            scaryTerm = "متغیر (x) و ضریب",
            streetMeaning = "جعبهٔ دربسته (x) و عددِ چسبیده به بغلش!",
            realLifeExample = "وقتی می‌گیم 3x یعنی ۳ تا جعبهٔ دربسته داریم؛ ۳ ضریب است و x همان جعبهٔ مجهول.",
            konkoorShortcut = "برای پیدا کردن x، همیشه عددِ بغلش (ضریب) را به مخرجِ طرفِ دیگر تساوی بفرست!",
            relatedKeywords = listOf("معادله", "مجهول", "درجه اول")
        ),
        PlainJargonEntry(
            subjectId = "math",
            subCategory = "معادله و تابع",
            scaryTerm = "دلتا (Δ) و ریشهٔ معادله",
            streetMeaning = "چراغ راهنمای تعداد جواب‌ها (Δ) و عددِ صفرکننده (ریشه)!",
            realLifeExample = "ریشه یعنی عددی که اگر بذاری جای x، کل معادله صفر بشه. دلتا هم می‌گه ۲ تا جواب داریم (Δ>0)، ۱ جواب (Δ=0) یا هیچ جواب (Δ<0).",
            konkoorShortcut = "قبل از دلتا، جمع اعداد چسبیده به x را چک کن: اگر جمع ضرایب صفر شد، یکی از جواب‌ها شک نکن 1 است و آن یکی c/a!",
            relatedKeywords = listOf("درجه دوم", "سهمی", "ریشه")
        ),
        PlainJargonEntry(
            subjectId = "math",
            subCategory = "معادله و تابع",
            scaryTerm = "تابع، دامنه و برد",
            streetMeaning = "چرخ‌گوشت وفادار (تابع)، گوشتِ ورودی (دامنه) و سوسیسِ خروجی (برد)!",
            realLifeExample = "دامنه یعنی همون عددهای اول (x) که می‌ندازیم تو دستگاه؛ برد یعنی عددهای دوم (y) که از دستگاه بیرون میاد. تابع یعنی هر ورودی فقط ۱ خروجی داشته باشه (خیانت نکنه!).",
            konkoorShortcut = "در زوج‌مرتب‌ها اگر عدد اول (x) تکراری دیدی که خروجی‌های متفاوت داشت، بگو «تابع نیست»!",
            relatedKeywords = listOf("زوج مرتب", "نمودار", "f(x)")
        ),
        PlainJargonEntry(
            subjectId = "math",
            subCategory = "معادله و تابع",
            scaryTerm = "قدر مطلق |x|",
            streetMeaning = "ماشین لباسشوییِ پاک‌کنندهٔ علامت منفی!",
            realLifeExample = "هر عددی بره داخل دو تا خطِ صاف |−5|، منفی‌اش شسته می‌شه و مثبت (+5) میاد بیرون.",
            konkoorShortcut = "خروجی قدرمطلق هیچ‌وقت منفی نمی‌شه! اگر دیدی |x| = −3، بدون حل کردن بزن «فاقد جواب (تهی)».",
            relatedKeywords = listOf("فاصله", "منفی")
        ),
        PlainJargonEntry(
            subjectId = "math",
            subCategory = "دنباله‌ها",
            scaryTerm = "دنباله حسابی و قدر نسبت (d)",
            streetMeaning = "پله‌های مساوی (با جمع و تفریق ثابت)!",
            realLifeExample = "مثل آسانسوری که ۳ تا ۳ تا میره بالا: 2, 5, 8, 11. به این عدد ثابتی که اضافه می‌شه (+3) می‌گن قدر نسبت (d).",
            konkoorShortcut = "برای جملهٔ دهم نیازی به فرمول نیست: جملهٔ اول رو بنویس + ۹ بار پله (d) رو بهش اضافه کن!",
            relatedKeywords = listOf("جمله عمومی", "واسطه حسابی")
        ),
        PlainJargonEntry(
            subjectId = "math",
            subCategory = "دنباله‌ها",
            scaryTerm = "دنباله هندسی و قدر نسبت (r)",
            streetMeaning = "پرش‌های چندبرابری (با ضرب و تقسیم ثابت)!",
            realLifeExample = "مثل شایعه‌ای که هر ساعت ۲ برابر می‌شه: 3, 6, 12, 24. به عددی که هر بار ضرب می‌شه (×2) می‌گن قدر نسبت (r).",
            konkoorShortcut = "در دنبالهٔ هندسی، اگر جملهٔ وسط رو به توان ۲ برسونی، دقیقاً برابر با ضربِ جملهٔ قبلی و بعدی‌اش می‌شه!",
            relatedKeywords = listOf("رشد نمایی", "توان")
        ),
        PlainJargonEntry(
            subjectId = "math",
            subCategory = "آمار و احتمال",
            scaryTerm = "واریانس و انحراف معیار",
            streetMeaning = "پراکندگی و شلوغ‌پلوغی عددها دورِ میانگین!",
            realLifeExample = "اگر همهٔ دانش‌آموزان نمرهٔ 15 بگیرن، هیچ اختلافی نیست پس واریانس و انحراف معیار صفره! هرچی نمره‌ها از هم دورتر باشن، واریانس بیشتر می‌شه.",
            konkoorShortcut = "جمع و تفریقِ یک عدد ثابت با همهٔ داده‌ها، واریانس و انحراف معیار رو اصلاً تکون نمی‌ده! فقط ضرب (k)، انحراف معیار رو |k| برابر و واریانس رو k² برابر می‌کنه.",
            relatedKeywords = listOf("میانگین", "ضریب تغییرات")
        ),
        PlainJargonEntry(
            subjectId = "math",
            subCategory = "آمار و احتمال",
            scaryTerm = "جایگشت (ترتیب مهم) و ترکیب (ترتیب بی‌اهمیت)",
            streetMeaning = "صفِ مدال (جایگشت) در برابرِ ریختن میوه تو سبد (ترکیب)!",
            realLifeExample = "در رمز گوشی یا نفرات اول تا سوم مسابقه، جابه‌جایی افراد مهمه (جایگشت). ولی وقتی ۳ نفر رو برای اردوی مشهد انتخاب می‌کنی، فرقی نداره اسم کی اول خونده بشه (ترکیب).",
            konkoorShortcut = "کلمات «کد، رمز، عدد چندرقمی، صف، سمت‌های متفاوت» = ضرب سرسره‌ای؛ کلمات «گروه، تیم، کمیته، دست دادن» = ترکیب C(n,r)!",
            relatedKeywords = listOf("فاکتوریل", "شمارش", "احتمال")
        ),
        PlainJargonEntry(
            subjectId = "math",
            subCategory = "منطق ریاضی و آمار",
            scaryTerm = "درون‌یابی و برون‌یابی",
            streetMeaning = "حدس زدنِ وسطِ جدول (درون‌یابی) یا آیندهٔ بعد از جدول (برون‌یابی)!",
            realLifeExample = "اگر قیمت سال ۱۴۰۰ و ۱۴۰۲ رو داری و قیمت ۱۴۰۱ (وسطشون) رو حدس بزنی می‌شه درون‌یابی؛ اگر قیمت ۱۴۰۵ (بیرون بازه) رو حدس بزنی می‌شه برون‌یابی.",
            konkoorShortcut = "برای وسطِ دو سال، فقط دو تا عدد رو جمع کن و نصف کن! برای سالِ بعدی، ببین از قبلی چقدر رشد کرده همون‌قدر اضافه کن.",
            relatedKeywords = listOf("سری زمانی", "شاخص بها")
        ),

        // =========================================================================
        // ۲. عربی هفتم تا دوازدهم (arabic)
        // =========================================================================
        PlainJargonEntry(
            subjectId = "arabic",
            subCategory = "پایه و اعراب",
            scaryTerm = "مرفوع، منصوب، مجرور، مجزوم",
            streetMeaning = "کلمه‌ای که آخرش ـُ / ـٌ (مرفوع)، ـَ / ـً (منصوب)، ـِ / ـٍ (مجرور) یاْ ساکن (مجزوم) داره!",
            realLifeExample = "فقط به حرکتِ حرفِ آخر کلمه نگاه کن: ضمه (ـُ) یعنی مرفوع (مثل رئیسِ جمله)، فتحه (ـَ) یعنی منصوب (مثل مفعول و حال)، کسره (ـِ) یعنی مجرور (بعد از حرف جر یا مضاف‌الیه).",
            konkoorShortcut = "در جمع مذکر و مثنی: «ون / ان» یعنی مرفوع؛ «ین» یعنی منصوب یا مجرور!",
            relatedKeywords = listOf("معرب", "مبنی", "اعراب")
        ),
        PlainJargonEntry(
            subjectId = "arabic",
            subCategory = "پایه و اعراب",
            scaryTerm = "ثلاثی مجرد و ثلاثی مزید (باب‌ها)",
            streetMeaning = "کلمهٔ لُختِ ۳ حرفی (مجرد) در برابرِ کلمهٔ لباس‌پوشیده با حروف اضافه (مزید)!",
            realLifeExample = "«عَلِمَ» فقط ۳ حرفِ اصلی داره (مجرد)؛ اما «عَلَّمَ، تَعَلَّمَ، إستَعلَمَ» لباس اضافه پوشیده و رفته تو قالبِ باب‌ها (مزید).",
            konkoorShortcut = "هر وقت اولِ مصدر «إ / تـ / مـ» اضافه دیدی یا وسطش تشدید بود، شک نکن ثلاثی مزید است!",
            relatedKeywords = listOf("افعال", "تفعیل", "مفاعله", "مصدر")
        ),
        PlainJargonEntry(
            subjectId = "arabic",
            subCategory = "مشتقات و اسم‌ها",
            scaryTerm = "جامد و مشتق (اسم فاعل، مفعول، تفضیل، مکان)",
            streetMeaning = "کلمهٔ سنگِ بدونِ فامیل (جامد) در برابرِ کلمه‌ای که از ریشه ساخته شده (مشتق)!",
            realLifeExample = "از ریشهٔ «ک-ت-ب»: کاتِب (نویسنده = اسم فاعل)، مَکتوب (نوشته‌شده = اسم مفعول)، مَکتَب (محل نوشتن = اسم مکان)، أکتَب (نویسنده‌تر = اسم تفضیل).",
            konkoorShortcut = "کد شناسایی ۱ ثانیه‌ای: حرف دوم «ا» باشد (فاعِل) یا اولش «مُـ» و یکی مانده به آخر «ـِ» باشد = اسم فاعل! اولش «مَـ» و آخرش «و» باشد (مَفعول) یا اولش «مُـ» و یکی مانده به آخر «ـَ» باشد = اسم مفعول!",
            relatedKeywords = listOf("مبالغه", "صفت مشبهه")
        ),
        PlainJargonEntry(
            subjectId = "arabic",
            subCategory = "ترجمه و افعال",
            scaryTerm = "معرفه و نکره",
            streetMeaning = "آدم یا چیزِ سرشناس (معرفه) در برابرِ ناشناسِ «یک / ـی» دار (نکره)!",
            realLifeExample = "«الکِتاب» (ال دار) معروف و مشخصه پس در فارسی «کتاب» ترجمه می‌شه؛ اما «کِتابٌ» (تنوین‌دارِ بدون ال) ناشناسه پس در فارسی «کتابی / یک کتاب» ترجمه می‌شه.",
            konkoorShortcut = "اسم تنوین‌دار (ـٌ ـً ـٍ) در عربی = علامت «ـی / یک» در گزینهٔ فارسی! با همین یک نکته ۲ تا گزینهٔ کنکور خط می‌خورد.",
            relatedKeywords = listOf("وصفی", "اضافی")
        ),
        PlainJargonEntry(
            subjectId = "arabic",
            subCategory = "قواعد دهم تا دوازدهم",
            scaryTerm = "افعال ناقصه (کانَ، أصبَحَ، لَیسَ) و حروف مشبهه (إنَّ، أنَّ، لَیتَ)",
            streetMeaning = "مهمان‌های اولِ جملهٔ اسمیه که معنیِ «بود / شد / نیست» یا «قطعاً / کاش» می‌دن!",
            realLifeExample = "«کانَ» می‌ره سرِ جملهٔ اسمیه و بهش معنیِ گذشته (بود) می‌ده؛ «إنَّ» می‌ره سرِ جمله و تأکید (قطعاً / همانا) می‌کنه.",
            konkoorShortcut = "رمز اعراب: «کانَ» اسمِ اول رو مرفوع (ـُ) نگه می‌داره و خبر رو منصوب (ـَ) می‌کنه؛ «إنَّ» دقیقاً برعکسه (اول منصوب ـَ، دوم مرفوع ـُ)!",
            relatedKeywords = listOf("مبتدا", "خبر", "لای نفی جنس")
        ),
        PlainJargonEntry(
            subjectId = "arabic",
            subCategory = "قواعد دوازدهم",
            scaryTerm = "مفعول مطلق (تأکیدی، نوعی، عددی)",
            streetMeaning = "کپیِ مصدرِ خودِ فعل در همان جمله!",
            realLifeExample = "مثل وقتی تو فارسی می‌گیم «یه کتکی زدمش!»؛ در عربی می‌گه «ضَرَبتُ ضَرباً» (از خودِ فعلِ ضَرَبَ، مصدرِ ضَرباً رو آورده).",
            konkoorShortcut = "اگر کپیِ فعل در جمله تنها بود = تأکیدی (ترجمه: قطعاً / واقعاً)؛ اگر بعدش صفت یا مضاف‌الیه آمد = نوعی (ترجمه: به شکلِ / مانندِ)!",
            relatedKeywords = listOf("منصوبات", "مصدر")
        ),
        PlainJargonEntry(
            subjectId = "arabic",
            subCategory = "قواعد دوازدهم",
            scaryTerm = "حال (قید حالت) و تمییز",
            streetMeaning = "چطوری آمدنِ فاعل (حال) و رفعِ ابهام از عدد و «أفعَل» (تمییز)!",
            realLifeExample = "«جاءَ الطّفلُ باکِیاً» (کودک چطوری آمد؟ گریان = حال). «أکثَرُ الناسِ عِلماً» (بیشتر از چه نظر؟ از نظرِ علم = تمییز).",
            konkoorShortcut = "از فعل بپرس «چطوری؟» جوابش می‌شه «حال» (که همیشه مشتق و نکره است). بعد از عددها، کمّ، کَفی و وزنِ «أفعَل» اسمِ منصوب دیدی شک نکن «تمییز» است!",
            relatedKeywords = listOf("جمله حالیه", "ذوالحال")
        ),
        PlainJargonEntry(
            subjectId = "arabic",
            subCategory = "قواعد دوازدهم",
            scaryTerm = "مستثنی، مستثنی‌منه و اسلوب حصر",
            streetMeaning = "جدا کردنِ یک نفر از بقیه با «إلّا» (استثنا) یا معنیِ «فقط» دادن (حصر)!",
            realLifeExample = "«جاءَ الطُّلّابُ إلّا عَلیّاً» (همهٔ دانش‌آموزان آمدند به جز علی: الطلّاب = گروه بزرگ/مستثنی‌منه، علی = جداشده/مستثنی). اما اگر قبل از إلّا گروهِ بزرگ نباشه (ما جاءَ إلّا علیٌّ)، إلّا معنیِ «فقط» می‌ده (حصر)!",
            konkoorShortcut = "قبل از «إلّا» منفی دیدی و گروه کلی نبود = در ترجمه فقط بگرد دنبال کلمهٔ «فقط / تنها»!",
            relatedKeywords = listOf("إنّما", "استثنا")
        ),

        // =========================================================================
        // ۳. زبان انگلیسی هفتم تا دوازدهم (english)
        // =========================================================================
        PlainJargonEntry(
            subjectId = "english",
            subCategory = "اجزای جمله",
            scaryTerm = "Subject (فاعل)، Verb (فعل)، Object (مفعول)",
            streetMeaning = "قطار ۳ واگنه انگلیسی: ۱. کی کار رو کرد (Subject) + ۲. چیکار کرد (Verb) + ۳. روی چی (Object)!",
            realLifeExample = "در جملهٔ «Ali plays football»، علی کار رو انجام داده (Subject)، بازی می‌کنه (Verb = موتور جمله) و فوتبال مفعوله (Object).",
            konkoorShortcut = "برخلاف فارسی که فعل تهِ جمله میاد، تو انگلیسی همیشه واگن دوم (بلافاصله بعد از اسمِ اول) فعل (Verb) است!",
            relatedKeywords = listOf("جمله", "ترتیب کلمات")
        ),
        PlainJargonEntry(
            subjectId = "english",
            subCategory = "اجزای جمله",
            scaryTerm = "Adjective (صفت) و Adverb (قید)",
            streetMeaning = "توصیف‌کنندهٔ اسم (Adjective) در برابرِ توصیف‌کنندهٔ کار با ـly (Adverb)!",
            realLifeExample = "«careful driver» یعنی رانندهٔ بااحتیاط (صفت قبل از اسم میاد)؛ اما «drives carefully» یعنی بااحتیاط رانندگی می‌کنه (قید تهش ly داره و حالتِ کار رو می‌گه).",
            konkoorShortcut = "قبل از اسم جای خالی دیدی = صفت (بدون ly)؛ بعد از فعلِ اصلی جای خالی دیدی = قید (دارای ly)!",
            relatedKeywords = listOf("قید", "صفت", "ly")
        ),
        PlainJargonEntry(
            subjectId = "english",
            subCategory = "گرامر دوازدهم",
            scaryTerm = "Active (معلوم) و Passive (مجهول)",
            streetMeaning = "معلوم یعنی فاعل سرِ جاشه؛ مجهول یعنی مفعول اومده اولِ جمله و کار روش انجام شده!",
            realLifeExample = "«Ali broke the window» (علی شیشه رو شکست = معلوم). «The window was broken» (شیشه شکسته شد = مجهول).",
            konkoorShortcut = "فرمول طلایی مجهول همیشه ۲ تکه داره: «عمو Be (am/is/are/was/were/been) + شکل سوم فعل (ed / P.P)»! اگر اولِ جمله شیءِ بی‌جان (مثل پل، کتاب، ماشین) دیدی یا کلمهٔ by بود، فقط گزینهٔ مجهول رو بزن!",
            relatedKeywords = listOf("مجهول", "Past Participle", "P.P")
        ),
        PlainJargonEntry(
            subjectId = "english",
            subCategory = "گرامر دوازدهم",
            scaryTerm = "Tag Question (سؤال دم‌چسبیده)",
            streetMeaning = "تأییدیهٔ آخرِ جمله به معنیِ «مگه نه؟» با قانونِ آینهٔ برعکس!",
            realLifeExample = "«Ali is smart, isn't he?» — چون جملهٔ اول مثبت است (is)، دُمِ آخرش منفی می‌شود (isn't) و اسم هم تبدیل به ضمیر (he) می‌شود.",
            konkoorShortcut = "قانون آینه: مثبت ⬅ منفی، منفی ⬅ مثبت! حواست باشه کلمات «never, hardly, rarely, little, few» جمله رو منفی می‌کنن، پس دُمِ آخرشون حتماً باید مثبت باشه!",
            relatedKeywords = listOf("دم سوالی", "مگه نه")
        ),
        PlainJargonEntry(
            subjectId = "english",
            subCategory = "گرامر یازدهم و دوازدهم",
            scaryTerm = "Gerund (فعل ingدار) و Infinitive (مصدر با to)",
            streetMeaning = "فعلِ دوم که یا چسبِ ing می‌خوره یا چسبِ to!",
            realLifeExample = "بعد از حروف اضافه (in, on, of, for, without) و فعل‌های enjoy/avoid/mind همیشه فعل با ing میاد؛ اما بعد از want/decide/hope/plan و صفت‌ها همیشه to + فعل ساده میاد.",
            konkoorShortcut = "به‌محض دیدنِ حرف اضافه (of, in, for, about, without, by) قبل از جای خالی، در ۱ ثانیه گزینه‌ای که ing داره رو بزن!",
            relatedKeywords = listOf("حرف اضافه", "مصدر")
        ),
        PlainJargonEntry(
            subjectId = "english",
            subCategory = "گرامر دوازدهم",
            scaryTerm = "Relative Clause (جملات موصولی: who, whom, which, that)",
            streetMeaning = "چسبِ «که» برای وصل کردنِ دو تا جمله به هم!",
            realLifeExample = "اگر قبل از جای خالی «انسان» بود از who/whom استفاده می‌کنیم؛ اگر «شیء یا حیوان» بود از which استفاده می‌کنیم.",
            konkoorShortcut = "فرمول ۲ ثانیه‌ای: انسان + جای خالی + فعل = who! انسان + جای خالی + ضمیر/اسم (I, you, he, Ali) = whom! شیء/حیوان + جای خالی = which!",
            relatedKeywords = listOf("موصول", "ضمایر موصولی")
        ),
        PlainJargonEntry(
            subjectId = "english",
            subCategory = "گرامر یازدهم و دوازدهم",
            scaryTerm = "Conditional Type 1 & Type 2 (شرطی نوع اول و دوم)",
            streetMeaning = "شرطِ واقعیِ آینده (نوع ۱ با will) در برابرِ خیال‌پردازیِ الکیِ الان (نوع ۲ با گذشته و would)!",
            realLifeExample = "نوع ۱: «اگر درس بخوانی، قبول می‌شوی» (If + حال ساده ⬅ will). نوع ۲ (خیالی): «اگر الان پرنده بودم، پرواز می‌کردم» (If I were a bird, I would fly).",
            konkoorShortcut = "ترازوی شرطی: اگر طرفِ If مضارع ساده بود، آن طرف «will» بزن؛ اگر طرفِ If گذشته (ed / were) بود، آن طرف «would / could» بزن! در شرطی نوع ۲ برای همه (حتی I و he) به‌جای was از were استفاده می‌شه!",
            relatedKeywords = listOf("جملات شرطی", "If")
        ),

        // =========================================================================
        // ۴. علوم و فنون ادبی و آرایه‌ها (fonoon)
        // =========================================================================
        PlainJargonEntry(
            subjectId = "fonoon",
            subCategory = "آرایه‌های لفظی (بدیع)",
            scaryTerm = "سجع (متوازی، مطرّف، متوازن)",
            streetMeaning = "قافیه‌بازیِ آهنگین در آخرِ جمله‌های نثر (متن غیرشعر)!",
            realLifeExample = "«محبت را غایت نیست و دوستی را نهایت نیست» — کلماتِ «غایت» و «نهایت» در آخرِ دو جملهٔ نثر هم‌وزن و هم‌حرف آخرند (سجع متوازی).",
            konkoorShortcut = "فقط به ۲ تا فاکتور نگاه کن: ۱. حرفِ آخر، ۲. وزن! هر دو یکی بود = سجع متوازی (بهترین نوع)؛ فقط حرف آخر یکی بود = سجع مطرّف؛ فقط وزن یکی بود = سجع متوازن!",
            relatedKeywords = listOf("نثر مسجع", "گلستان سعدی")
        ),
        PlainJargonEntry(
            subjectId = "fonoon",
            subCategory = "آرایه‌های لفظی (بدیع)",
            scaryTerm = "جناس (تام و ناقص: حرکتی، اختلافی، افزایشی)",
            streetMeaning = "دو کلمه با قیافهٔ شبیهِ هم ولی معنیِ کاملاً متفاوت!",
            realLifeExample = "«شیر» (حیوان) و «شیر» (خوراکی) = جناس تام (۱۰۰٪ کپی هم). «مِهر» و «مُهر» = جناس ناقص حرکتی. «کفش» و «کشف» = اختلافی. «کام» و «کامل» = افزایشی.",
            konkoorShortcut = "اگر دو کلمهٔ هم‌شکل معنیِ یکسانی داشتن، جناس نیست (تکرار است)! حتماً باید معنی‌شون فرق کنه.",
            relatedKeywords = listOf("هم‌آوا", "بدیع لفظی")
        ),
        PlainJargonEntry(
            subjectId = "fonoon",
            subCategory = "آرایه‌های لفظی (بدیع)",
            scaryTerm = "موازنه و ترصیع",
            streetMeaning = "کلماتِ روبه‌روی هم در دو مصراعِ شعر که هم‌وزن (موازنه) یا هم‌وزن و هم‌قافیه‌اند (ترصیع)!",
            realLifeExample = "«ما زیاران چشم یاری داشتیم / خود غلط بود آنچه ما پنداشتیم» نیس؛ بلکه مثل «ای منوّر به تو نجوم جمال / وی مقرّر به تو رسوم کمال» که کلماتِ دو مصراع تک‌به‌تک هم‌وزن و هم‌قافیه‌اند (ترصیع).",
            konkoorShortcut = "کلماتِ مصراع اول و دوم رو بذار زیرِ هم: اگر تک‌به‌تک سجعِ متوازی (هم‌وزن + هم‌حرف آخر) بودن = ترصیع! اگر سجع متوازن (فقط هم‌وزن) بودن = موازنه!",
            relatedKeywords = listOf("توازن", "موسیقی درونی")
        ),
        PlainJargonEntry(
            subjectId = "fonoon",
            subCategory = "بیان (تشبیه و استعاره)",
            scaryTerm = "تشبیه بلیغ (اضافی و اسنادی)",
            streetMeaning = "تشبیهِ بدونِ کلمهٔ «مثل / چون» که دو طرف رو به هم چسبونده!",
            realLifeExample = "به‌جای اینکه بگه «علم مثلِ نور است»، می‌گه «نورِ علم» (تشبیه بلیغ اضافی با کسرهٔ ـِ) یا «علم نور است» (تشبیه بلیغ اسنادی).",
            konkoorShortcut = "هرجا ترکیبِ «الفِ ب» دیدی، برعکسش کن و وسطش «مثلِ» بذار: «درختِ دانش ⬅ دانش مثلِ درخت است»! اگر معنی داد، ۱۰۰٪ اضافهٔ تشبیهی (تشبیه بلیغ) است!",
            relatedKeywords = listOf("مشبه", "مشبه‌به", "اضافه تشبیهی")
        ),
        PlainJargonEntry(
            subjectId = "fonoon",
            subCategory = "بیان (تشبیه و استعاره)",
            scaryTerm = "استعاره (مصرحه و مکنیه / تشخیص)",
            streetMeaning = "تشبیهِ مخفی که فقط یک طرفش تو جمله مونده!",
            realLifeExample = "وقتی به معشوق می‌گی «ماه آمد» (منظورت یار است ولی فقط مشبه‌به یعنی ماه رو گفتی = استعاره مصرحه). وقتی می‌گی «شاخهٔ گل خندید» یا «دستِ روزگار» (به غیرانسان ویژگیِ انسان دادی یا اندام نسبت دادی = استعاره مکنیه / تشخیص).",
            konkoorShortcut = "تک‌کلمه‌ای که در معنیِ اصلی خودش نیست و بر پایهٔ شباهت است (بت، سرو، نرگس، لعل، شیر) = استعاره مصرحه! ای + غیرانسان (ای کوه، ای دل، ای باد) = ۱۰۰٪ تشخیص!",
            relatedKeywords = listOf("جان‌بخشی", "اضافه استعاری")
        ),
        PlainJargonEntry(
            subjectId = "fonoon",
            subCategory = "بیان (مجاز و کنایه)",
            scaryTerm = "مجاز و کنایه",
            streetMeaning = "کلمهٔ غیرواقعیِ بدونِ شباهت (مجاز) در برابرِ جملهٔ دوپهلویِ ضرب‌المثلی (کنایه)!",
            realLifeExample = "«ایران در جام جهانی گل زد» (کل کشور که گل نزده، تیم ملی گل زده = مجاز به علاقهٔ کل و جزء). «فلانی دستش کجه» (واقعاً استخوان دستش کج نیست، یعنی دزده = کنایه).",
            konkoorShortcut = "یک کلمهٔ تکی (سر، دست، خون، شهر، جهان، دهان) که معنیِ دیگری بدهد = مجاز! یک عبارت یا فعلِ چندکلمه‌ای (پند گرفتن، آب در هاون کوفتن، دندان طمع کندن) = کنایه!",
            relatedKeywords = listOf("علاقه", "معنی دور")
        ),
        PlainJargonEntry(
            subjectId = "fonoon",
            subCategory = "آرایه‌های معنوی",
            scaryTerm = "ایهام و ایهام تناسب",
            streetMeaning = "یک کلمهٔ دو معنیه که یا هر دو معنی‌اش به جمله می‌خوره (ایهام) یا یک معنی‌اش می‌خوره و معنیِ دومش با کلمهٔ دیگری جور است (ایهام تناسب)!",
            realLifeExample = "کلمهٔ «مُدام» هم یعنی «همیشه» و هم یعنی «شراب». اگر در بیت هر دو معنی درست باشد = ایهام؛ اگر در بیت فقط معنیِ «همیشه» درست باشد ولی در کنارش «ساقی و جام» آمده باشد (که با شراب تناسب دارد) = ایهام تناسب!",
            konkoorShortcut = "کلماتِ قفلِ ایهام در کنکور: «مُدام، چنگ، پرده، ماه، مهر، سیر، شام، نوا، گور، داد، شیرین، روی»! به محض دیدن این‌ها در بیت، زنگِ ایهام یا ایهام تناسب را بزن.",
            relatedKeywords = listOf("دو معنایی", "مراعات نظیر")
        ),
        PlainJargonEntry(
            subjectId = "fonoon",
            subCategory = "آرایه‌های معنوی",
            scaryTerm = "اسلوب معادله، حسن تعلیل، حس‌آمیزی، متناقض‌نما",
            streetMeaning = "۴ غولِ تست‌خیزِ فنون به زبانِ ۱ خطی!",
            realLifeExample = "• اسلوب معادله: مصراع اول و دوم مثل دو کفهٔ ترازو و مستقل‌اند که می‌شه وسطشون «همان‌طور که» گذاشت.\n• حسن تعلیل: دلیلِ شاعرانه و تخیلی آوردن برای یک پدیدهٔ طبیعی.\n• حس‌آمیزی: قاطی کردنِ دو حس از ۵ حس (مثل «خبرِ تلخ» = شنوایی + چشایی).\n• متناقض‌نما (پارادوکس): دو ویژگیِ ضدِ هم در یک چیز (مثل «جنگِ صلح‌آمیز» یا «دولتِ فقر»).",
            konkoorShortcut = "وسطِ دو مصراع کلمهٔ «همان‌طور که» بذار و جای دو مصراع رو عوض کن؛ اگر معنی به هم نریخت و مصراع دوم ضرب‌المثل بود = ۱۰۰٪ اسلوب معادله!",
            relatedKeywords = listOf("پارادوکس", "لف و نشر", "تلمیح")
        ),

        // =========================================================================
        // ۵. عروض و قافیه (arooz)
        // =========================================================================
        PlainJargonEntry(
            subjectId = "arooz",
            subCategory = "تقطیع و هجا",
            scaryTerm = "هجای کوتاه (U)، بلند (–) و کشیده (– U)",
            streetMeaning = "تکه‌های صدای کلمه هنگامِ دست زدن (۱ حرفی بعد از صدا = کوتاه، ۲ حرفی = بلند، ۳ حرفی = کشیده)!",
            realLifeExample = "کلمهٔ «تُ» = هجای کوتاه (U)؛ کلمهٔ «ما / دَر» = هجای بلند (–)؛ کلمهٔ «کار / دوست» = هجای کشیده که در عروض ۱ بلند + ۱ کوتاه (– U) حساب می‌شه.",
            konkoorShortcut = "در عروض به «طرزِ نوشتن» اصلاً کار نداشته باش؛ فقط کلمه رو بلند بخون و با انگشت بخش کن! هجای کشیدهٔ آخرِ مصراع فقط ۱ هجای بلند (–) حساب می‌شه.",
            relatedKeywords = listOf("مصوت", "صامت", "تقطیع هجایی")
        ),
        PlainJargonEntry(
            subjectId = "arooz",
            subCategory = "وزن و ارکان",
            scaryTerm = "وزن همسان، متناوب و دوری",
            streetMeaning = "مدلِ چیدنِ آجرهای وزن در یک مصراع (۴ تا کپیِ هم = همسان، یکی در میان = متناوب، آینه‌ای در وسط = دوری)!",
            realLifeExample = "• همسان: «فاعلاتن فاعلاتن فاعلاتن فاعلاتن» (یک کلمه ۴ بار تکرار شده).\n• متناوب: «مفاعلن فعلاتن مفاعلن فعلاتن» (یکی در میان).\n• دوری: وسطِ مصراع مکث دارد و نیمهٔ اول و دوم مصراع کپیِ هم است (مثل مفتعلن فاعلاتُ | مفتعلن فاعلاتُ).",
            konkoorShortcut = "اگر مصراع رو از وسطِ دقیق نصف کردی و دیدی هجاهای نیمهٔ چپ و راست کپیِ هم هستن، بدونِ تقطیع کامل گزینهٔ «وزن دوری» رو بزن!",
            relatedKeywords = listOf("ارکان", "بحر", "افاعیل")
        ),
        PlainJargonEntry(
            subjectId = "arooz",
            subCategory = "اختیارات شاعری",
            scaryTerm = "اشباع، حذف همزه، اسکان، قلب و ابدال",
            streetMeaning = "تقلب‌های مجازِ شاعر برای جور شدنِ وزنِ شعر!",
            realLifeExample = "• حذف همزه: وصل کردنِ صامتِ آخرِ کلمه به «ا» اولِ کلمهٔ بعد (مَن اَز ⬅ مَ نَز).\n• اشباع: کشیدنِ کسره یا ضمهٔ آخر کلمه به اندازهٔ «ی / و» بلند.\n• قلب: جابه‌جا کردنِ یک هجای کوتاه و بلند (U – ⬅ – U) یعنی تبدیلِ «مفاعلن» به «مفتعلن».\n• ابدال: تبدیلِ دو هجای کوتاه (U U) به یک هجای بلند (–) یعنی تبدیلِ «فَعِلاتن» به «مَفعولن».",
            konkoorShortcut = "در کنکور هرجا در یک مصراع رکنِ اول «فَعِلاتن (U U – –)» و در مصراع دیگر رکنِ اول «مَفعولن (– – –)» یا «فاعلاتن (– U – –)» بود، اختیارِ وزنی «ابدال» رخ داده است!",
            relatedKeywords = listOf("اختیارات وزنی", "اختیارات زبانی")
        ),
        PlainJargonEntry(
            subjectId = "arooz",
            subCategory = "قافیه",
            scaryTerm = "ردیف، قافیه، حرفِ رَویّ و قاعده ۱ و ۲ قافیه",
            streetMeaning = "کلمهٔ تکراریِ آخر (ردیف)، کلمهٔ هم‌آهنگِ قبلش (قافیه) و آخرین حرفِ اصلیِ مشترک (رویّ)!",
            realLifeExample = "در «شب است» و «تب است»: «است» عیناً تکرار شده = ردیف؛ «شب» و «تب» = قافیه؛ حرفِ «ب» = حرفِ رویّ. اگر قافیه بعد از مصوتِ بلند (ا، و، ی) تمام شود قاعدهٔ ۱ است (مثل پا / جا)؛ اگر بعد از مصوت کوتاه یا بلند حداقل یک صامت بیاید قاعدهٔ ۲ است (مثل شب / تب یا باد / یاد).",
            konkoorShortcut = "حروفِ جمع و ضمیر (ها، ان، م، ت، ش، یم، ید، ند) که به تهِ قافیه می‌چسبند «حروف الحاقی» هستند؛ اول آن‌ها را جدا کن تا به حرفِ اصلیِ قافیه (رویّ) برسی!",
            relatedKeywords = listOf("حروف قافیه", "عیوب قافیه")
        ),

        // =========================================================================
        // ۶. منطق دهم و فلسفه ۱ و ۲ (logic & philosophy)
        // =========================================================================
        PlainJargonEntry(
            subjectId = "logic",
            subCategory = "منطق دهم",
            scaryTerm = "تصور و تصدیق",
            streetMeaning = "عکسِ بدونِ حکم تو ذهن (تصور) در برابرِ جملهٔ دارای «هست / نیست» و راست و دروغ (تصدیق)!",
            realLifeExample = "«کوهِ طلا» یا «کتابِ علی» فقط یک تصویره و حکمی نداده (تصور)؛ اما «هوا سرد است» حکم داده و می‌شه گفت راست یا دروغه (تصدیق). توجه کن: سؤالی، تعجبی و امری هم در منطق «تصور» حساب می‌شن!",
            konkoorShortcut = "اگر جمله خبری نبود (سؤالی، امری، تعجبی، آرزویی بود) یا فعل نداشت، شک نکن «تصور» است!",
            relatedKeywords = listOf("مفهوم", "مصداق")
        ),
        PlainJargonEntry(
            subjectId = "logic",
            subCategory = "منطق دهم",
            scaryTerm = "دلالت مطابقی، تضمنی و التزامی",
            streetMeaning = "معنیِ کلِ کلمه (مطابقی)، یک تکه از داخلش (تضمنی) یا چیزی خارج از آن که یادش می‌افتیم (التزامی)!",
            realLifeExample = "وقتی می‌گیم «خانه خراب شد»: اگر کلِ خانه خراب شده = مطابقی؛ اگر فقط سقفِ اتاق خراب شده (جزئی از خانه) = تضمنی؛ اگر موقعِ فروش خانه بگن یعنی «پولِ آب و برقش هم تسویه شده» (لازمهٔ بیرونی) = التزامی!",
            konkoorShortcut = "کل = مطابقی، جزءِ درونی = تضمنی، لازمهٔ بیرونی و کنایه‌ای = التزامی!",
            relatedKeywords = listOf("لفظ", "معنا")
        ),
        PlainJargonEntry(
            subjectId = "logic",
            subCategory = "منطق دهم",
            scaryTerm = "کلیات خمس (نوع، جنس، فصل، عرض خاص، عرض عام)",
            streetMeaning = "۵ برچسبِ شناسنامهٔ هر موجود در منطق!",
            realLifeExample = "برای «انسان»: کلِ چیستی‌اش = نوع (انسان)؛ بخشِ مشترکِ درونی با حیوانات = جنس (حیوان)؛ بخشِ اختصاصیِ درونی = فصل (ناطق / متفکر)؛ ویژگیِ بیرونیِ مخصوصِ خودش = عرض خاص (خندنده / شاعر)؛ ویژگیِ بیرونیِ مشترک با بقیه = عرض عام (راه‌رونده / سفید).",
            konkoorShortcut = "درونیِ مشترک = جنس، درونیِ اختصاصی = فصل (جنس + فصل = حدّ تام)؛ بیرونیِ اختصاصی = عرض خاص (جنس + عرض خاص = رسم تام)!",
            relatedKeywords = listOf("ذاتی", "عرضی", "حد و رسم")
        ),
        PlainJargonEntry(
            subjectId = "logic",
            subCategory = "منطق دهم",
            scaryTerm = "قضیه حملی و شرطی (متصل و منفصل) + مربع تقابل",
            streetMeaning = "جملهٔ سادهٔ «الف ب است» (حملی) در برابرِ «اگر... آنگاه» (شرطی متصل) و «یا... یا» (شرطی منفصل)!",
            realLifeExample = "در مربع تقابل، قوی‌ترین دشمنی «تناقض» است: یکی موجبهٔ کلی (هر الف ب است) و یکی سالبهٔ جزئی (بعضی الف ب نیست) — یا برعکس (هیچ الف ب نیست ⬅ بعضی الف ب است).",
            konkoorShortcut = "برای پیدا کردنِ «نقیض» در ۱ ثانیه: «هر» تبدیل می‌شه به «بعضی... نیست» و «هیچ... نیست» تبدیل می‌شه به «بعضی... است»!",
            relatedKeywords = listOf("تناقض", "تضاد", "عکس مستوی")
        ),
        PlainJargonEntry(
            subjectId = "logic",
            subCategory = "منطق دهم",
            scaryTerm = "قیاس اقترانی و استثنایی (وضع مقدم و رفع تالی)",
            streetMeaning = "نتیجه‌گیری با پلِ مشترک (اقترانی) یا با «اگر و یا» (استثنایی)!",
            realLifeExample = "در قیاس استثناییِ «اگر باران ببارد (مقدم)، زمین خیس می‌شود (تالی)»: فقط ۲ حالت معتبر و درست است: ۱. باران بارید پس زمین خیس شد (وضع مقدم = تأییدِ تکهٔ اول)؛ ۲. زمین خیس نیست پس باران نباریده (رفع تالی = ردِ تکهٔ دوم).",
            konkoorShortcut = "تلهٔ کنکور: «رد کردنِ تکهٔ اول (رفع مقدم)» و «تأییدِ تکهٔ دوم (وضع تالی)» همیشه مغالطه و باطل است!",
            relatedKeywords = listOf("حد وسط", "شکل اول", "مغالطه")
        ),
        PlainJargonEntry(
            subjectId = "philosophy",
            subCategory = "فلسفه یازدهم و دوازدهم",
            scaryTerm = "وجود و ماهیت (اصالت وجود و اصالت ماهیت)",
            streetMeaning = "«هست بودنِ واقعی» (وجود) در برابرِ «قالب و چیستیِ ذهنی» (ماهیت)!",
            realLifeExample = "اینکه «درخت در باغ واقعاً هست» می‌شه وجودش؛ اینکه «چیست؟ گیاهی تنه‌دار و برگ‌دار» می‌شه ماهیتش. ملاصدرا و فارابی می‌گن اصل و واقعیتِ بیرون «وجود» است (اصالت وجود)؛ سهروردی و میرداماد می‌گن اصل «ماهیت» است.",
            konkoorShortcut = "کلمهٔ «نور» را دیدی = سهروردی (شیخ اشراق) و اصالت ماهیت! کلمهٔ «تشکیک وجود و حکمت متعالیه» را دیدی = ملاصدرا و اصالت وجود!",
            relatedKeywords = listOf("ملاصدرا", "سهروردی", "تشکیک")
        ),
        PlainJargonEntry(
            subjectId = "philosophy",
            subCategory = "فلسفه دوازدهم",
            scaryTerm = "علت تامه و ناقصه، حرکت جوهری، مشاء و اشراق",
            streetMeaning = "پکیجِ کاملِ علت‌ها (تامه)، تغییرِ درونیِ ذاتِ اشیاء (حرکت جوهری) و عقل‌گرایی (مشاء) در برابرِ دل و شهود (اشراق)!",
            realLifeExample = "• علت تامه: وقتی همهٔ شرایط (بذر، آب، خاک، نور) کامل جمع بشن، معلول ۱۰۰٪ و بدونِ تأخیر به وجود میاد.\n• حرکت جوهری (ابتکار ملاصدرا): نه فقط رنگ و جای سیب (اعراض)، بلکه خودِ ذات و هستیِ سیب هم لحظه‌به‌لحظه در حالِ نو شدن و حرکت است.",
            konkoorShortcut = "مشاء (ابن‌سینا و فارابی) = فقط استدلالِ عقلی؛ اشراق (سهروردی) = استدلال عقلی + کشف و شهود قلبی؛ عرفان = فقط شهود قلبی؛ حکمت متعالیه (ملاصدرا) = ترکیبِ هر سه (قرآن + برهان عقلی + عرفان)!",
            relatedKeywords = listOf("قوه و فعل", "ابن سینا", "وجوب و امکان")
        ),

        // =========================================================================
        // ۷. تاریخ و جغرافیا (history & geography)
        // =========================================================================
        PlainJargonEntry(
            subjectId = "history",
            subCategory = "تاریخ ۱، ۲، ۳",
            scaryTerm = "ساتراپ، مهستان، دیوان‌سالاری، اقطاع و تیول",
            streetMeaning = "استاندارِ هخامنشی (ساتراپ)، مجلسِ اشکانی (مهستان)، اداره‌جاتِ دولتی (دیوان‌سالاری) و بخشیدنِ زمین به‌جای حقوق (اقطاع/تیول)!",
            realLifeExample = "داریوش کشور رو به استان‌هایی به نام «ساتراپی» تقسیم کرد. اشکانیان مجلسِ مشورتیِ بزرگان به نام «مهستان» داشتند. در دورهٔ اسلامی و سلجوقی و صفوی، شاه به‌جای پولِ نقد، درآمدِ یک زمین را به فرمانده می‌داد (اقطاع / سیورغال / تیول).",
            konkoorShortcut = "مهستان = اشکانیان؛ چاپارخانه و راه شاهی = هخامنشیان؛ اقطاع = سلجوقیان (خواجه نظام‌الملک)؛ سیورغال = تیموریان؛ تیول = صفویه و قاجار!",
            relatedKeywords = listOf("هخامنشیان", "اشکانیان", "سلجوقیان")
        ),
        PlainJargonEntry(
            subjectId = "history",
            subCategory = "تاریخ معاصر",
            scaryTerm = "کاپیتولاسیون (حق قضاوت کنسولی) و مشروطه",
            streetMeaning = "تخفیفِ قضایی به بیگانگان که در دادگاهِ ایران محاکمه نشوند (کاپیتولاسیون) و قانون‌مند کردنِ شاه با مجلس (مشروطه)!",
            realLifeExample = "طبقِ کاپیتولاسیون (عهدنامهٔ ترکمانچای با روسیه)، اگر یک خارجی در ایران جرم می‌کرد، دادگاهِ ایران حقِ محاکمه‌اش را نداشت. مشروطه هم یعنی شاه دیگر هر کاری دلش خواست نکند، بلکه مشروط به «قانون و مجلس» باشد.",
            konkoorShortcut = "شروع کاپیتولاسیون در ایران = عهدنامهٔ ترکمانچای (دورهٔ فتحعلی‌شاه قاجار)!",
            relatedKeywords = listOf("قاجار", "ترکمانچای", "امتیاز")
        ),
        PlainJargonEntry(
            subjectId = "geography",
            subCategory = "جغرافیا ۱، ۲، ۳",
            scaryTerm = "دریای سرزمینی (۱۲ مایل) و منطقه انحصاری اقتصادی (۲۰۰ مایل)",
            streetMeaning = "حیاطِ خلوتِ کاملاً شخصیِ کشور تو دریا (۱۲ مایل) در برابرِ حقِ ماهی‌گیری و نفت تا ۲۰۰ مایل!",
            realLifeExample = "تا ۱۲ مایل از ساحل، انگار خاکِ خودِ کشور است (حتی آسمانش و کفِ دریا). اما از ۱۲ تا ۲۰۰ مایل، کشتی‌های بقیه می‌تونن رد بشن، ولی نفت و گاز و ماهی‌گیری‌اش فقط مالِ کشورِ ساحلی است.",
            konkoorShortcut = "حاکمیتِ مطلق و کامل = ۱۲ مایل (دریای سرزمینی)؛ حقِ بهره‌برداریِ اقتصادی و منابع = ۲۰۰ مایل (منطقهٔ انحصاری اقتصادی و فلات قاره)!",
            relatedKeywords = listOf("مرز دریایی", "آب‌های آزاد")
        ),
        PlainJargonEntry(
            subjectId = "geography",
            subCategory = "جغرافیا ۱، ۲، ۳",
            scaryTerm = "توندرا، تایگا، وارونگی دما (اینورژن) و آمایش سرزمین",
            streetMeaning = "بیابانِ یخیِ قطب (توندرا)، جنگلِ کاجِ سردسیر (تایگا)، قفل شدنِ دود تو هوای سردِ شهر (وارونگی) و چیدمانِ درستِ شهر و کارخانه (آمایش)!",
            realLifeExample = "در زمستان وقتی هوای سردِ سنگین پایین می‌آید و هوای گرم بالا می‌ماند، مثلِ درِ قابلمه روی شهر را می‌بندد و دودِ ماشین‌ها بالا نمی‌رود (وارونگی دما). آمایش سرزمین هم یعنی هر کارخانه و کشاورزی را جایِ مناسبِ خودش بسازیم.",
            konkoorShortcut = "خاکِ «پودزول» = جنگل‌های تایگا؛ خاکِ «لاتریت» (قرمز و شسته‌شده) = جنگل‌های بارانیِ استوایی!",
            relatedKeywords = listOf("بیوم", "زیست‌بوم", "جزیره حرارتی")
        ),

        // =========================================================================
        // ۸. اقتصاد، روان‌شناسی و جامعه‌شناسی (economics, psychology, sociology)
        // =========================================================================
        PlainJargonEntry(
            subjectId = "economics",
            subCategory = "اقتصاد دهم",
            scaryTerm = "هزینه فرصت، هزینه آشکار و پنهان، سود حسابداری و اقتصادی",
            streetMeaning = "بهترین چیزی که به‌خاطرِ انتخابت قیدش رو زدی (هزینه فرصت)!",
            realLifeExample = "اگر مغازهٔ خودت رو پیتزافروشی کنی، پولی که بابتِ پنیر و کارگر می‌دی «هزینه آشکار» است؛ اما اجاره‌ای که می‌تونستی از مغازه‌ات بگیری یا حقوقِ کارمندی که قیدش رو زدی «هزینه پنهان (فرصت)» است!",
            konkoorShortcut = "سود حسابداری = درآمد منهای هزینهٔ آشکار (فاکتورها)؛ سود اقتصادی = سود حسابداری منهای هزینهٔ پنهان (فرصت از دست رفته)!",
            relatedKeywords = listOf("کمیابی", "انتخاب")
        ),
        PlainJargonEntry(
            subjectId = "economics",
            subCategory = "اقتصاد دهم",
            scaryTerm = "تولید ناخالص داخلی (GDP) و ملی (GNP) + ارزش افزوده",
            streetMeaning = "تولیدِ داخلِ مرزهای ایران (GDP) در برابرِ تولیدِ ایرانی‌های کلِ دنیا (GNP)!",
            realLifeExample = "اگر یک مهندسِ آلمانی در تهران کار کنه، تولیدش تو «داخلی (GDP)» ایران هست ولی تو «ملی (GNP)» ایران نیست. ارزش افزوده هم یعنی «قیمتِ فروش منهای پولِ مواد اولیه‌ای که خریدی» (مثلاً نانوا آرد ۱۰ تومانی رو می‌کنه نانِ ۱۵ تومانی ⬅ ۵ تومان ارزش افزوده).",
            konkoorShortcut = "در محاسبهٔ GDP فقط «کالاهای نهایی» را جمع بزن (یا جمعِ ارزش افزودهٔ مراحل)؛ کالاهای واسطه‌ای (مثل آرد و گندمِ وسطِ کار) و دست‌دوم را اصلاً جمع نزن!",
            relatedKeywords = listOf("کالای نهایی", "کالای واسطه‌ای", "تولید واقعی")
        ),
        PlainJargonEntry(
            subjectId = "psychology",
            subCategory = "روان‌شناسی یازدهم",
            scaryTerm = "پایداری شیء، خودمحوری و نگهداری ذهنی (مراحل پیاژه)",
            streetMeaning = "۳ نشانهٔ بزرگ شدنِ مغزِ بچه در نظریهٔ پیاژه!",
            realLifeExample = "• پایداری شیء (حسی-حرکتی، ۰ تا ۲ سال): نوزاد اولش فکر می‌کنه اسباب‌بازی بره زیرِ پتو غیب شده، بعد یاد می‌گیره هنوز زیرِ پتو هست!\n• خودمحوری و جان‌دارپنداری (پیش‌عملیاتی، ۲ تا ۷ سال): بچه فکر می‌کنه خورشید داره دنبالش میاد و عروسکش گرسنه می‌شه.\n• نگهداری ذهنی (عملیات عینی، ۷ تا ۱۱ سال): می‌فهمه اگر آبِ لیوانِ پهن رو بریزیم تو لیوانِ باریک و بلند، مقدارِ آب بیشتر نشده!",
            konkoorShortcut = "نوزاد تا ۲ سال = پایداری شیء؛ ۲ تا ۷ سال = خودمحوری و نداشتنِ نگهداری ذهنی؛ ۷ تا ۱۱ سال = کسبِ نگهداری ذهنی؛ بالای ۱۱ سال (عملیات صوری) = تفکرِ انتزاعی و فلسفی!",
            relatedKeywords = listOf("رشد شناختی", "پیاژه")
        ),
        PlainJargonEntry(
            subjectId = "psychology",
            subCategory = "روان‌شناسی یازدهم",
            scaryTerm = "تداخل پیش‌گستر و پس‌گستر + آمایه ذهنی و تثبیت کارکردی",
            streetMeaning = "قاطی شدنِ اطلاعاتِ قدیم و جدید در حافظه + گیر کردنِ ذهن روی یک روشِ قدیمی!",
            realLifeExample = "• تداخل پیش‌گستر: رمزِ عابربانکِ «قدیمی» نمی‌ذاره رمزِ «جدید» یادت بمونه (قدیم رو به جلو مزاحمِ جدیده).\n• تداخل پس‌گستر: درسِ «جدید» باعث می‌شه درسِ «قدیمی» یادت بره (جدید رو به عقب قدیم رو پاک کرده).\n• تثبیت کارکردی: فکر کنی سکه فقط برای خرید است و به ذهنت نرسد با لبه‌اش پیچ را باز کنی!",
            konkoorShortcut = "به کلمهٔ بعد از «فراموشیِ...» نگاه کن: فراموشیِ مطلبِ «جدید» = پیش‌گستر! فراموشیِ مطلبِ «قدیم» = پس‌گستر!",
            relatedKeywords = listOf("حافظه", "فراموشی", "تقطیع")
        ),
        PlainJargonEntry(
            subjectId = "sociology",
            subCategory = "جامعه‌شناسی ۱، ۲، ۳",
            scaryTerm = "کنش اجتماعی، هنجار، ارزش و نماد",
            streetMeaning = "۴ ستونِ جامعه‌شناسی به زبانِ کوچه و بازار!",
            realLifeExample = "• کنش: کاری که از رویِ قصد و آگاهی انجام می‌دی (نه مثلِ عطسه یا پلک زدنِ غیرارادی). اگر دیگران را در نظر بگیری می‌شه «کنش اجتماعی» (مثل سلام کردن یا هدیه دادن).\n• ارزش: چیزهایی که برامون خوب و عزیزه (مثل احترام به والدین، عدالت).\n• هنجار: قانونِ رفتاری برای رسیدن به ارزش (مثل بلند شدن جلوی پای بزرگ‌تر).\n• نماد: علامتی که یک معنیِ پنهان پشتش داره (مثل چراغ قرمز، پرچم، حلقهٔ ازدواج).",
            konkoorShortcut = "ریزشِ برگ، گوارشِ معده و خواب دیدن = «کنش نیست»! خوب و بدِ کلی = ارزش؛ باید و نبایدِ رفتاری = هنجار!",
            relatedKeywords = listOf("پیامد ارادی", "فرهنگ")
        ),
        PlainJargonEntry(
            subjectId = "sociology",
            subCategory = "جامعه‌شناسی ۱، ۲، ۳",
            scaryTerm = "اومانیسم، سکولاریسم و سیانتیسم (فرهنگ غرب)",
            streetMeaning = "۳ کلمهٔ فرانسویِ پرسوالِ جامعه‌شناسی: انسان‌محوری، دنیامحوری و علم‌زدگی!",
            realLifeExample = "• اومانیسم: یعنی انسان به جایِ خدا مرکزِ عالم است و خودش خوب و بد را تعیین می‌کند.\n• سکولاریسم (دنیوی‌گرایی): یعنی دین و آخرت را از سیاست و زندگیِ اجتماعی بیرون بگذاریم و فقط به همین دنیا بچسبیم.\n• سیانتیسم (علم‌زدگی): یعنی فقط چیزی را قبول کنیم که در آزمایشگاه و با چشم و حس دیده شود (انکارِ وحی و عقلِ متافیزیکی).",
            konkoorShortcut = "جایگزینیِ انسان به‌جای خدا = اومانیسم؛ حذفِ دین از جامعه و توجه فقط به دنیا = سکولاریسم؛ انکارِ غیب و محدود کردنِ علم به حس و تجربه = سیانتیسم!",
            relatedKeywords = listOf("روشنگری", "مدرنیته", "استعمار فرانو")
        ),
        PlainJargonEntry(
            subjectId = "sociology",
            subCategory = "جامعه‌شناسی ۳ (دوازدهم)",
            scaryTerm = "رویکرد تبیینی (پوزیتیویستی)، تفسیری (تفهمی) و انتقادی",
            streetMeaning = "۳ عینکِ مختلفِ جامعه‌شناس‌ها برای دیدنِ جامعه (ماشینِ قانون‌دار، کتابِ پر از معنا، یا میدانِ ظلم و سلطه)!",
            realLifeExample = "• تبیینی (کنت و دورکیم): جامعه مثلِ طبیعت و فیزیک است؛ فقط ظاهرِ رفتار را با آمار و آزمایش اندازه می‌گیرد، دنبالِ کشفِ قانونِ علت و معلولی و «کنترل» است و می‌گوید محقق نباید دربارهٔ خوب و بدِ ارزش‌ها نظر بدهد (بی‌طرفی ارزشی).\n• تفسیری (ماکس وبر): انسان مثلِ سنگ نیست؛ باید «معنا و نیتِ درونی» کارِ مردم را بفهمیم و به فرهنگ‌های مختلف کاری نداشته باشیم (نسبیت‌گرایی).\n• انتقادی (مارکس و مکتب فرانکفورت): هدفِ علم باید افشایِ ظلمِ سرمایه‌داران و «رهاییِ محرومان» باشد.",
            konkoorShortcut = "جدولِ ۱ ثانیه‌ای کنکور:\n• «آمار، کمّی، علوم طبیعی، کشف قانون، کنترل، بی‌طرفی ارزشی» = تبیینی!\n• «معنا، نیت، درک و فهم، کیفی، نسبیت فرهنگی» = تفسیری!\n• «سلطه، استثمار، رهایی، آگاهی‌بخشی، دفاع از مظلوم» = انتقادی!",
            relatedKeywords = listOf("بی‌طرفی ارزشی", "نسبیت‌گرایی", "ماکس وبر", "دورکیم")
        ),
        PlainJargonEntry(
            subjectId = "sociology",
            subCategory = "جامعه‌شناسی ۲ و ۳",
            scaryTerm = "آنومی (بی‌هنجاری)، برچسب‌زنی، هویت انتسابی و اکتسابی",
            streetMeaning = "بلاتکلیفیِ قوانین (آنومی)، انگ زدنِ جامعه به فرد (برچسب‌زنی) و ویژگیِ مادرزادی در برابرِ تلاشی!",
            realLifeExample = "• آنومی (دورکیم): وقتی جامعه ناگهان عوض می‌شه و قوانینِ قبلی از کار می‌افتن و مردم نمی‌دونن چی درسته و چی غلط.\n• برچسب‌زنی (رویکرد تفسیری): وقتی به یک نوجوان برچسبِ «بزهکار» می‌زنن، خودش هم باورش می‌شه و واقعاً خلافکار می‌شه.\n• انتسابی و اکتسابی: جنسیت و محلِ تولد که دستِ خودت نبوده = انتسابی؛ شغل و مدرکِ تحصیلی که با تلاش به دست آوردی = اکتسابی!",
            konkoorShortcut = "تبیینی = علتِ کج‌روی را «آنومی (بی‌هنجاری)» می‌داند؛ تفسیری = علت را «برچسب‌زنی» می‌داند؛ انتقادی = علت را «بی‌عدالتی و فقرِ طبقاتی» می‌داند!",
            relatedKeywords = listOf("کج‌روی", "کنترل اجتماعی", "تحرک اجتماعی")
        ),

        // =========================================================================
        // ۹. فرهنگیان، دینی، فارسی و سایر کتب انسانی (farhangian, dini, farsi, general_humanities)
        // =========================================================================
        PlainJargonEntry(
            subjectId = "farhangian",
            subCategory = "منابع اختصاصی فرهنگیان",
            scaryTerm = "حیات طیبه و ۶ ساحتِ تربیتی (سند تحول بنیادین)",
            streetMeaning = "زندگیِ پاکِ خدایی (هدفِ نهاییِ مدرسه) و ۶ بُعدِ رشدِ دانش‌آموز!",
            realLifeExample = "هدفِ مدرسه فقط حفظ کردنِ کتاب نیست، بلکه رسیدن به «حیات طیبه» در ۶ زمینه (ساحت) است: ۱. اعتقادی-عبادی-اخلاقی، ۲. اجتماعی-سیاسی، ۳. زیستی-بدنی، ۴. زیباشناختی-هنری، ۵. اقتصادی-حرفه‌ای، ۶. علمی-فناورانه.",
            konkoorShortcut = "مهم‌ترین و محوری‌ترین ساحت در سند تحول بنیادین = ساحتِ «اعتقادی، عبادی و اخلاقی»!",
            relatedKeywords = listOf("معلم تراز", "کانون ارزیابی")
        ),
        PlainJargonEntry(
            subjectId = "dini",
            subCategory = "دین و زندگی ۱۰ تا ۱۲",
            scaryTerm = "توحید ذاتی، صفاتی، افعالی و عملی + شرکِ خفی",
            streetMeaning = "۴ پلهٔ یکتاپرستی: خدا یکی است (ذاتی)، صفاتش عینِ ذاتش است (صفاتی)، کارگردانِ عالم اوست (افعالی) و فقط بندگیِ او را کنیم (عملی)!",
            realLifeExample = "• توحید ذاتی: خدا شریک و جزء ندارد (قل هو الله احد).\n• توحید صفاتی: علم و قدرتِ خدا جدا از خودش نیست.\n• توحید افعالی: هیچ برگی بدونِ اذنِ او نمی‌افتد و همهٔ کارها به دستِ اوست.\n• توحید عملی: در زندگی و عمل، فقط تسلیمِ فرمانِ خدا باشیم (نه هوایِ نفس و طاغوت).",
            konkoorShortcut = "آیاتی که دربارهٔ «خلقت، تدبیر، روزی دادن، مالکیت و به دستِ خدا بودنِ کارهای جهان» است = ۱۰۰٪ توحید افعالی (که ریشهٔ توکل است)!",
            relatedKeywords = listOf("برهان تمانع", "توکل", "قضا و قدر")
        ),
        PlainJargonEntry(
            subjectId = "dini",
            subCategory = "دین و زندگی ۱۰ تا ۱۲",
            scaryTerm = "توفّی، تجسّم اعمال و الامر بین الامرین",
            streetMeaning = "دریافتِ کاملِ روح در مرگ (توفّی)، تبدیلِ کارها به صورتِ واقعی در قیامت (تجسم اعمال) و اختیارِ انسان در عینِ ارادهٔ خدا!",
            realLifeExample = "• توفّی: مرگ یعنی نابودی نیست، بلکه فرشتگان روحِ انسان را کامل تحویل می‌گیرند.\n• تجسم اعمال: در قیامت، غیبت کردن به شکلِ خوردنِ گوشتِ مرده و مالِ یتیم به شکلِ آتش در شکم دیده می‌شود.\n• الامر بین الامرین: انسان نه مجبورِ بی‌اختیار است (جبر) و نه رهاشدهٔ مستقل از خدا (تفویض)، بلکه با قدرتی که خدا لحظه‌به‌لحظه به او می‌دهد، خودش راهش را انتخاب می‌کند.",
            konkoorShortcut = "دیدنِ خودِ عمل در قیامت (فَمَن یَعمَل مِثقالَ ذَرَّةٍ خَیراً یَرَهُ) = تجسم اعمال!",
            relatedKeywords = listOf("برزخ", "معاد", "جبر و اختیار")
        ),
        PlainJargonEntry(
            subjectId = "farsi",
            subCategory = "فارسی و دستور زبان",
            scaryTerm = "نهاد، مفعول، متمم و مسند (نقش‌های دستوری)",
            streetMeaning = "۴ نقشِ اصلیِ کلمات در جملهٔ فارسی با رمزِ ۱ کلمه‌ای!",
            realLifeExample = "• نهاد: کسی یا چیزی که اولِ جمله کار رو کرده یا درباره‌اش حرف می‌زنیم (پاسخِ «چه کسی / چه چیزی؟»).\n• مفعول: کلمه‌ای که همراهِ «را» می‌آید یا می‌شود بعدش «را» گذاشت (پاسخِ «چه کسی را / چه چیزی را؟»).\n• متمم: کلمه‌ای که بلافاصله بعد از حرفِ اضافه (از، به، با، در، برای، بر، تا) می‌آید.\n• مسند: صفت یا حالتی که قبل از فعل‌های «است، بود، شد، گشت، گردید» می‌آید (مثل: هوا «سرد» شد ⬅ سرد = مسند).",
            konkoorShortcut = "بعد از «از، به، با، در» = متمم! قبل از «است، بود، شد، گشت، گردید» = مسند! همراهِ «را» = مفعول!",
            relatedKeywords = listOf("فعل اسنادی", "گروه اسمی", "بدل")
        ),
        PlainJargonEntry(
            subjectId = "farsi",
            subCategory = "فارسی و دستور زبان",
            scaryTerm = "هسته، وابستهٔ پیشین و وابستهٔ پسین (مضاف‌الیه و صفت بیانی)",
            streetMeaning = "رئیسِ گروهِ اسمی (هسته) و نوکرهایِ قبل و بعدش (وابسته‌ها)!",
            realLifeExample = "در عبارتِ «این کتابِ خوبِ علی»: کلمهٔ اصلی که درباره‌اش حرف می‌زنیم «کتاب» است (هسته). «این» قبلش آمده (وابستهٔ پیشین = صفت اشاره‌ای). «خوب» و «علی» بعدش با کسره (ـِ) آمده‌اند (وابسته‌های پسین).",
            konkoorShortcut = "فرمولِ طلاییِ تشخیصِ صفتِ بیانی از مضاف‌الیه: بینِ دو کلمه که کسره (ـِ) دارن، کلمهٔ «است» بذار:\n• «کتابِ خوب» ⬅ کتاب خوب است (معنی می‌ده = صفت بیانی)!\n• «کتابِ علی» ⬅ کتاب علی است (معنی نمی‌ده، مالکیت داره = مضاف‌الیه)!",
            relatedKeywords = listOf("گروه اسمی", "کسره اضافه")
        ),
        PlainJargonEntry(
            subjectId = "general_humanities",
            subCategory = "سواد رسانه‌ای، سلامت و محیط زیست",
            scaryTerm = "فنِ شهادت، همنوایی، کلی‌گویی درخشان، آبِ مجازی و BMI",
            streetMeaning = "۵ اصطلاحِ پرتکرارِ کتاب‌هایِ سلامت، محیط زیست و سواد رسانه‌ای!",
            realLifeExample = "• فن شهادت: استفاده از یک بازیگر یا فوتبالیستِ معروف در تبلیغِ پفک یا شامپو.\n• فن همنوایی: «همه دارن این محصول رو می‌خرن، تو هم از قافله عقب نمون!»\n• کلی‌گویی درخشان: استفاده از کلماتِ قشنگ ولی بدونِ مدرک (مثل «طعمِ جادوییِ خوشبختی»).\n• آبِ مجازی: کلِ آبی که از اولِ کاشتِ گندم و خوراکِ دام تا تولیدِ ۱ کیلو گوشت یا نان مصرف شده (نه فقط آبی که داخلش دیده می‌شه).\n• نمایه توده بدنی (BMI): وزن (کیلوگرم) تقسیم بر قد به توانِ ۲ (به متر).",
            konkoorShortcut = "سلبریتی در تبلیغ = فنِ شهادت؛ «همه می‌خرند» = فنِ همنوایی؛ شعارِ مبهم و پرزرق‌وبرق = کلی‌گویی درخشان!",
            relatedKeywords = listOf("توسعه پایدار", "غربالگری", "اقناع")
        )
    )

    fun getEntriesForSubject(subjectId: String): List<PlainJargonEntry> {
        if (subjectId == "all") return allEntries
        val mappedId = when (subjectId) {
            "negaresh" -> "farsi"
            "social_identity" -> "sociology"
            "media_environment" -> "general_humanities"
            "farhangian_tarbiat" -> "farhangian"
            else -> subjectId
        }
        return allEntries.filter { it.subjectId == mappedId }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun SimpleJargonDictionaryCard(
    subjectId: String,
    accentColor: Color,
    modifier: Modifier = Modifier,
    initiallyExpanded: Boolean = true
) {
    var isExpanded by remember(subjectId) { mutableStateOf(initiallyExpanded) }
    var searchQuery by remember { mutableStateOf("") }
    var showAllSubjects by remember(subjectId) { mutableStateOf(false) }

    val subjectEntries = remember(subjectId, showAllSubjects) {
        if (showAllSubjects) {
            UniversalPlainJargonCatalog.allEntries
        } else {
            UniversalPlainJargonCatalog.getEntriesForSubject(subjectId)
        }
    }

    val subCategories = remember(subjectEntries) {
        listOf("همه اصطلاحات") + subjectEntries.map { it.subCategory }.distinct()
    }
    var selectedSubCategoryIndex by remember(subjectId, showAllSubjects) { mutableIntStateOf(0) }

    val filteredEntries = remember(subjectEntries, selectedSubCategoryIndex, searchQuery) {
        val byCat = if (selectedSubCategoryIndex == 0 || selectedSubCategoryIndex >= subCategories.size) {
            subjectEntries
        } else {
            val targetCat = subCategories[selectedSubCategoryIndex]
            subjectEntries.filter { it.subCategory == targetCat }
        }
        val q = searchQuery.trim()
        if (q.isEmpty()) {
            byCat
        } else {
            UniversalPlainJargonCatalog.allEntries.filter { entry ->
                entry.scaryTerm.contains(q, ignoreCase = true) ||
                    entry.streetMeaning.contains(q, ignoreCase = true) ||
                    entry.realLifeExample.contains(q, ignoreCase = true) ||
                    entry.konkoorShortcut.contains(q, ignoreCase = true) ||
                    entry.subCategory.contains(q, ignoreCase = true) ||
                    entry.relatedKeywords.any { it.contains(q, ignoreCase = true) }
            }
        }
    }

    Card(
        modifier = modifier
            .fillMaxWidth()
            .testTag("simple_jargon_dictionary_card"),
        shape = RoundedCornerShape(22.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        border = BorderStroke(1.5.dp, accentColor.copy(alpha = 0.38f)),
        elevation = CardDefaults.cardElevation(defaultElevation = 3.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    Brush.verticalGradient(
                        listOf(
                            accentColor.copy(alpha = 0.12f),
                            MaterialTheme.colorScheme.surface
                        )
                    )
                )
                .padding(14.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            // Header Row
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { isExpanded = !isExpanded },
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.weight(1f)
                ) {
                    Surface(
                        color = accentColor,
                        shape = CircleShape,
                        modifier = Modifier.size(40.dp)
                    ) {
                        Box(contentAlignment = Alignment.Center) {
                            Icon(
                                imageVector = Icons.Default.Translate,
                                contentDescription = null,
                                tint = Color.White,
                                modifier = Modifier.size(22.dp)
                            )
                        }
                    }
                    Spacer(modifier = Modifier.width(10.dp))
                    Column {
                        Text(
                            text = BidiFormatter.formatMixedPersianText("🗣️ مترجم کلمات قلمبه‌سلمبه به زبان کوچه و بازار"),
                            style = MaterialTheme.typography.titleSmall,
                            fontWeight = FontWeight.ExtraBold,
                            color = accentColor
                        )
                        Text(
                            text = BidiFormatter.formatMixedPersianText(
                                "ترجمهٔ فوری تمام اصطلاحات سخت کتاب به معنی ۳ کلمه‌ای خودمونی + رمز کنکور (${filteredEntries.size} اصطلاح)"
                            ),
                            style = MaterialTheme.typography.labelSmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }

                Surface(
                    color = accentColor.copy(alpha = 0.16f),
                    shape = RoundedCornerShape(10.dp)
                ) {
                    Row(
                        modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = if (isExpanded) "بستن" else "مشاهده",
                            style = MaterialTheme.typography.labelSmall,
                            fontWeight = FontWeight.Bold,
                            color = accentColor
                        )
                        Icon(
                            imageVector = if (isExpanded) Icons.Default.ExpandLess else Icons.Default.ExpandMore,
                            contentDescription = null,
                            tint = accentColor,
                            modifier = Modifier.size(18.dp)
                        )
                    }
                }
            }

            AnimatedVisibility(visible = isExpanded) {
                Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                    // Scope toggle: Current Subject vs All 9 Subjects
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        FilterChip(
                            selected = !showAllSubjects,
                            onClick = {
                                showAllSubjects = false
                                selectedSubCategoryIndex = 0
                            },
                            label = {
                                Text(
                                    text = BidiFormatter.formatMixedPersianText("📚 اصطلاحات همین درس"),
                                    style = MaterialTheme.typography.labelSmall,
                                    fontWeight = if (!showAllSubjects) FontWeight.Bold else FontWeight.Normal
                                )
                            },
                            colors = FilterChipDefaults.filterChipColors(
                                selectedContainerColor = accentColor.copy(alpha = 0.2f),
                                selectedLabelColor = accentColor
                            )
                        )
                        FilterChip(
                            selected = showAllSubjects,
                            onClick = {
                                showAllSubjects = true
                                selectedSubCategoryIndex = 0
                            },
                            label = {
                                Text(
                                    text = BidiFormatter.formatMixedPersianText("🌟 کل ۱۶ درس کنکور (${UniversalPlainJargonCatalog.allEntries.size} واژه)"),
                                    style = MaterialTheme.typography.labelSmall,
                                    fontWeight = if (showAllSubjects) FontWeight.Bold else FontWeight.Normal
                                )
                            },
                            colors = FilterChipDefaults.filterChipColors(
                                selectedContainerColor = accentColor.copy(alpha = 0.2f),
                                selectedLabelColor = accentColor
                            )
                        )
                    }

                    // Search Field for Scary Textbook Words
                    OutlinedTextField(
                        value = searchQuery,
                        onValueChange = { searchQuery = it },
                        placeholder = {
                            Text(
                                text = BidiFormatter.formatMixedPersianText(
                                    "🔍 هر کلمهٔ سختی رو بنویس (مثلاً: تبیینی، اومانیسم، استعاره، مرفوع، Passive، واریانس...)"
                                ),
                                style = MaterialTheme.typography.bodySmall
                            )
                        },
                        leadingIcon = {
                            Icon(
                                imageVector = Icons.Default.Search,
                                contentDescription = null,
                                tint = accentColor
                            )
                        },
                        singleLine = true,
                        shape = RoundedCornerShape(14.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .testTag("jargon_search_input")
                    )

                    // Subcategory Filter Chips
                    if (searchQuery.isBlank() && subCategories.size > 1) {
                        LazyRow(
                            horizontalArrangement = Arrangement.spacedBy(6.dp),
                            contentPadding = PaddingValues(vertical = 2.dp)
                        ) {
                            items(subCategories.size) { index ->
                                val selected = selectedSubCategoryIndex == index
                                FilterChip(
                                    selected = selected,
                                    onClick = { selectedSubCategoryIndex = index },
                                    label = {
                                        Text(
                                            text = BidiFormatter.formatMixedPersianText(subCategories[index]),
                                            style = MaterialTheme.typography.labelSmall,
                                            fontWeight = if (selected) FontWeight.Bold else FontWeight.Normal
                                        )
                                    },
                                    colors = FilterChipDefaults.filterChipColors(
                                        selectedContainerColor = accentColor,
                                        selectedLabelColor = Color.White
                                    ),
                                    shape = RoundedCornerShape(10.dp)
                                )
                            }
                        }
                    }

                    // Jargon Translation Cards
                    Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                        filteredEntries.forEach { entry ->
                            PlainJargonItemCard(
                                entry = entry,
                                accentColor = accentColor
                            )
                        }
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
private fun PlainJargonItemCard(
    entry: PlainJargonEntry,
    accentColor: Color
) {
    Surface(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.42f),
        border = BorderStroke(1.dp, accentColor.copy(alpha = 0.25f))
    ) {
        Column(
            modifier = Modifier.padding(12.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            // Top Row: Scary Word vs SubCategory Badge
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.weight(1f)
                ) {
                    Surface(
                        color = Color(0xFFDC2626).copy(alpha = 0.14f),
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        Text(
                            text = "کلمه کتاب:",
                            style = MaterialTheme.typography.labelSmall,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFFDC2626),
                            modifier = Modifier.padding(horizontal = 6.dp, vertical = 3.dp)
                        )
                    }
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = BidiFormatter.formatMixedPersianText(entry.scaryTerm),
                        style = MaterialTheme.typography.titleSmall,
                        fontWeight = FontWeight.ExtraBold,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                }

                Surface(
                    color = accentColor.copy(alpha = 0.15f),
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Text(
                        text = BidiFormatter.formatMixedPersianText(entry.subCategory),
                        style = MaterialTheme.typography.labelSmall,
                        fontWeight = FontWeight.Bold,
                        color = accentColor,
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp)
                    )
                }
            }

            // Street-Persian Meaning Box (Green Highlight)
            Surface(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                color = Color(0xFF059669).copy(alpha = 0.13f),
                border = BorderStroke(1.dp, Color(0xFF059669).copy(alpha = 0.35f))
            ) {
                Row(
                    modifier = Modifier.padding(10.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Default.AutoAwesome,
                        contentDescription = null,
                        tint = Color(0xFF059669),
                        modifier = Modifier.size(18.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = BidiFormatter.formatMixedPersianText("معنی به زبان خودمونی: ${entry.streetMeaning}"),
                        style = MaterialTheme.typography.bodyMedium,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurface,
                        lineHeight = 22.sp
                    )
                }
            }

            // Real-Life Example
            Text(
                text = BidiFormatter.formatMixedPersianText("💡 مثال واقعی: ${entry.realLifeExample}"),
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurface,
                lineHeight = 21.sp
            )

            HorizontalDivider(color = MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.45f))

            // 2-Second Konkoor Radar
            Surface(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(10.dp),
                color = accentColor.copy(alpha = 0.11f)
            ) {
                Row(
                    modifier = Modifier.padding(9.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Default.Lightbulb,
                        contentDescription = null,
                        tint = accentColor,
                        modifier = Modifier.size(18.dp)
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = BidiFormatter.formatMixedPersianText("⚡ رادار ۲ ثانیه‌ای کنکور: ${entry.konkoorShortcut}"),
                        style = MaterialTheme.typography.labelMedium,
                        fontWeight = FontWeight.SemiBold,
                        color = accentColor,
                        lineHeight = 20.sp
                    )
                }
            }

            // Related Keywords Pills
            if (entry.relatedKeywords.isNotEmpty()) {
                FlowRow(
                    horizontalArrangement = Arrangement.spacedBy(6.dp),
                    verticalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    entry.relatedKeywords.forEach { kw ->
                        Surface(
                            color = MaterialTheme.colorScheme.surface,
                            shape = RoundedCornerShape(6.dp),
                            border = BorderStroke(0.8.dp, MaterialTheme.colorScheme.outlineVariant)
                        ) {
                            Text(
                                text = BidiFormatter.formatMixedPersianText("# $kw"),
                                style = MaterialTheme.typography.labelSmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant,
                                modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                            )
                        }
                    }
                }
            }
        }
    }
}
