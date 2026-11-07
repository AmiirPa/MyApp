package com.example.ui.components

import java.util.Collections
import com.example.data.curriculum.FlashcardItem
import com.example.data.curriculum.MiniCheckQuestion
import com.example.data.curriculum.QuizQuestionItem
import com.example.data.curriculum.WorkedExample
import com.example.data.curriculum.ZeroLesson
import com.example.data.curriculum.ZeroStep

/**
 * پردازشگر تخصصی متن عربی و فارسی (ArabicTextProcessor)
 *
 * وظایف اصلی:
 * ۱. جایگزینی تمامی کاراکترهای عربی «ي» (U+064A)، «ى» الف مقصوره (U+0649) و اشکال نمایشی آن‌ها با «ی» فارسی (U+06CC)
 *    و جایگزینی تمامی کاراکترهای عربی «ك» (U+0643) و اشکال نمایشی آن با «ک» فارسی (U+06A9) جهت جلوگیری از
 *    پرش فونت (Font Fallback) و گسستگی حروف در موتور HarfBuzz اندروید.
 * ۲. مدیریت هوشمند فاصله مجازی (ZWNJ - U+200C) در ساختار کلمات عربی و ترکیبات عربی-فارسی:
 *    - حذف فاصله مجازی مخرب بین حرف پایه عربی و اعراب (فتحه، کسره، ضمه، تنوین، تشدید، سکون) که باعث جدا شدن حروف می‌شود.
 *    - حذف فاصله مجازی مخرب بعد از «ال» تعریف (مانند «ال‌کتاب» ⬅ «الکتاب») و درون ریشه‌های متصل عربی.
 *    - درج و استانداردسازی فاصله مجازی (ZWNJ) در ترکیبات عربی و اصطلاحات قواعدی (مانند «مضاف‌الیه»، «مع‌الغیر»،
 *      «مشبه‌به»، «مسندالیه»، «بین‌الملل»، «فوق‌العاده»، «اولی‌الامر»، «شبه‌جمله») و افعال مضارع/استمراری فارسی («می‌رود»، «نمی‌شود»).
 * ۳. استانداردسازی ترتیب «تشدید + حرکت» (Shadda + Haraka) و اصلاح جایگاه کشیده (Tatweel «ـ») در پیشوندهای آموزشی
 *    (مانند تبدیل «بـِ / لـِ / کـَ» به «بِـ / لِـ / کَـ») برای اتصال بی‌نقص حروف.
 */
object ArabicTextProcessor {

    const val ZWNJ: Char = '\u200C'
    private const val ZWJ: Char = '\u200D'
    private const val TATWEEL: Char = '\u0640'
    private const val PERSIAN_YEH: Char = '\u06CC'
    private const val PERSIAN_KEHEH: Char = '\u06A9'
    private const val SHADDA: Char = '\u0651'
    private const val SUPERSCRIPT_ALEF: Char = '\u0670'

    private val cache: MutableMap<String, String> = Collections.synchronizedMap(
        object : LinkedHashMap<String, String>(4096, 0.75f, true) {
            override fun removeEldestEntry(eldest: MutableMap.MutableEntry<String, String>?): Boolean = size > 4096
        }
    )

    fun clearCacheIfNeeded() {
        cache.clear()
    }

    // حروفی که در خط عربی/فارسی تنها از سمت راست می‌چسبند و از سمت چپ به حرف بعدی متصل نمی‌شوند
    // (بنابراین قرار گرفتن ZWNJ بلافاصله بعد از این حروف قبل از پسوندهایی مثل «ها» غیرضروری است)
    private val rightOnlyJoiningLetters = charArrayOf(
        'ا', 'آ', 'أ', 'إ', 'د', 'ذ', 'ر', 'ز', 'ژ', 'و', 'ؤ', 'ة', 'ء'
    )

    // حروفی که از هر دو طرف (راست و چپ) قابلیت اتصال دارند
    private val dualJoiningLetters = charArrayOf(
        'ب', 'پ', 'ت', 'ث', 'ج', 'چ', 'ح', 'خ',
        'س', 'ش', 'ص', 'ض', 'ط', 'ظ', 'ع', 'غ',
        'ف', 'ق', 'ک', 'گ', 'ل', 'م', 'ن', 'ه', 'ی', 'ئ'
    )

    // پسوندهای مشروع فارسی که بعد از حرف دوطرفه نیازمند حفظ فاصله مجازی (ZWNJ) هستند
    private val validPersianZwnjSuffixes = listOf(
        "ها", "های", "هایی", "هایم", "هایت", "هایش", "هایمان", "هایتان", "هایشان",
        "تر", "ترین", "ای", "ام", "ات", "اش", "ایم", "اید", "اند",
        "انه", "گان", "گاه", "گر", "گری", "دار", "دان", "ساز", "سازی", "شناس", "شناسی",
        "بندی", "گذاری", "گیر", "گیری", "گیرانه", "پذیر", "پذیری", "گرا", "گرایی", "محور", "آمیز", "بخش",
        "نامه", "پرداز", "پردازی"
    )

    // ترکیبات عربی و اصطلاحات قواعدی که باید با فاصله مجازی (ZWNJ) استاندارد شوند تا نه به هم بچسبند و نه بشکنند
    private val compoundZwnjReplacements: List<Pair<Regex, String>> = listOf(
        Regex("""مضاف(?:\s+|)الیه""") to "مضاف\u200Cالیه",
        Regex("""مسند(?:\s+|)الیه""") to "مسند\u200Cالیه",
        Regex("""منتهی(?:\s+|)الیه""") to "منتهی\u200Cالیه",
        Regex("""مشبه(?:\s+|)به""") to "مشبه\u200Cبه",
        Regex("""مع(?:\s+|)الغیر""") to "مع\u200Cالغیر",
        Regex("""بین(?:\s+|)الملل""") to "بین\u200Cالملل",
        Regex("""فوق(?:\s+|)العاده""") to "فوق\u200Cالعاده",
        Regex("""بیت(?:\s+|)المال""") to "بیت\u200Cالمال",
        Regex("""نصف(?:\s+|)النهار""") to "نصف\u200Cالنهار",
        Regex("""مابه(?:\s+|)التفاوت""") to "مابه\u200Cالتفاوت",
        Regex("""ما\s*به\s*الاشتراک""") to "مابه\u200Cالاشتراک",
        Regex("""ما\s*به\s*الامتیاز""") to "مابه\u200Cالامتیاز",
        Regex("""تحت(?:\s+|)الشعاع""") to "تحت\u200Cالشعاع",
        Regex("""فارغ(?:\s+|)التحصیل""") to "فارغ\u200Cالتحصیل",
        Regex("""اولی(?:\s+|)الامر""") to "اولی\u200Cالامر",
        Regex("""شبه(?:\s+)جمله""") to "شبه\u200Cجمله",
        Regex("""پیش(?:\s+)نیاز""") to "پیش\u200Cنیاز",
        Regex("""دانش(?:\s+)آموز""") to "دانش\u200Cآموز",
        Regex("""جمع(?:\s+)بندی""") to "جمع\u200Cبندی",
        Regex("""علامت(?:\s+)گذاری""") to "علامت\u200Cگذاری",
        // پیشوندهای فعلی «می» و «نمی» در توضیحات قواعد عربی که با فاصله عادی نوشته شده‌اند
        Regex("""\b(ن?می)\s+(رود|روند|روم|روی|رویم|روید|شود|شوند|شوم|شوی|شویم|شوید|کند|کنند|کنم|کنی|کنیم|کنید|آید|آیند|آیم|آیی|آییم|آیید|گوید|گویند|گویم|گویی|گوییم|گویید|دهد|دهند|دهم|دهی|دهیم|دهید|گیرد|گیرند|گیرم|گیری|گیریم|گیرید|باشد|باشند|باشم|باشی|باشیم|باشید|توان|تواند|توانند|توانم|توانی|توانیم|توانید|خواند|خوانند|خوانم|خوانی|خوانیم|خوانید|نویسد|نویسند|نویسم|نویسی|نویسیم|نویسید|پرسد|پرسند|پرسم|پرسی|پرسیم|پرسید|داند|دانند|دانم|دانی|دانیم|دانید|برند|برد|برم|بری|بریم|برید)\b""") to "$1\u200C$2"
    )

    /**
     * بررسی می‌کند آیا کاراکتر ورودی جزو علائم اعراب و حرکات عربی است یا خیر.
     */
    fun isArabicDiacritic(ch: Char): Boolean {
        return ch in '\u0610'..'\u061A' ||
            ch in '\u064B'..'\u065F' ||
            ch == SUPERSCRIPT_ALEF ||
            ch in '\u06D6'..'\u06ED'
    }

    /**
     * بررسی می‌کند آیا کاراکتر ورودی یک حرکت کوتاه یا تنوین است (که باید بعد از تشدید قرار بگیرد).
     */
    private fun isVowelOrTanween(ch: Char): Boolean {
        return ch in '\u064B'..'\u0650' || ch == SUPERSCRIPT_ALEF
    }

    /**
     * تبدیل هرگونه کاراکتر «ي / ى» و «ك» عربی (و اشکال نمایشی Unicode Presentation Forms) به معادل استاندارد فارسی «ی» و «ک».
     */
    fun normalizeArabicYehAndKafChar(ch: Char): Char = when (ch) {
        // تمامی اشکال یاء عربی و الف مقصوره به «ی» فارسی (U+06CC)
        '\u064A', // ي Arabic Letter Yeh
        '\u0649', // ى Arabic Letter Alef Maksura
        '\u0620', // ؠ Kashmiri Yeh
        '\u06D0', // ې E
        '\u06D2', // ے Yeh Barree
        '\uFEEF', '\uFEF0', // ى، ﻰ Alef Maksura presentation forms
        '\uFEF1', '\uFEF2', '\uFEF3', '\uFEF4', // ي، ﻲ، ﻳ، ﻴ Arabic Yeh presentation forms
        '\uFBFD', '\uFBFE', '\uFBFF', // Farsi Yeh presentation forms
        '\uFBAE', '\uFBAF' -> PERSIAN_YEH

        // تمامی اشکال کاف عربی به «ک» فارسی (U+06A9)
        '\u0643', // ك Arabic Letter Kaf
        '\u06AA', // ڪ Swash Kaf
        '\u06AB', // ګ Kaf with ring
        '\u06AC', '\u06AD', '\u06AE',
        '\uFED9', '\uFEDA', '\uFEDB', '\uFEDC', // ك، ﻚ، ﻛ، ﻜ Arabic Kaf presentation forms
        '\uFB8E', '\uFB8F', '\uFB90', '\uFB91' -> PERSIAN_KEHEH

        else -> ch
    }

    /**
     * پردازش کامل متن عربی و فارسی:
     * ۱. جایگزینی «ي / ى» و «ك» عربی با «ی» و «ک» فارسی
     * ۲. اصلاح ترتیب تشدید و حرکت (Shadda + Haraka) و حذف الف خنجری (U+0670) روی «ی» مقصوره
     * ۳. اصلاح جایگاه کشیده (Tatweel «ـ») در پیشوندهای آموزشی (بـِ ⬅ بِـ)
     * ۴. مدیریت دقیق فاصله مجازی (ZWNJ) برای جلوگیری از شکستگی یا جدا شدن حروف عربی و فارسی
     */
    fun processArabicText(raw: String): String {
        if (raw.isEmpty()) return raw
        cache[raw]?.let { return it }

        val cleanedRaw = raw
            .replace("<u>", "")
            .replace("</u>", "")
            .replace("<U>", "")
            .replace("</U>", "")
        val step1 = normalizeCharactersAndDiacritics(cleanedRaw)
        var result = sanitizeZwnjInArabicAndPersianStructure(step1)
        for ((regex, replacement) in compoundZwnjReplacements) {
            result = regex.replace(result, replacement)
        }

        cache[raw] = result
        return result
    }

    /**
     * گام ۱: جایگزینی کاراکترهای عربی «ی» و «ک»، استانداردسازی ترتیب تشدید و حرکت،
     * و اصلاح پیشوندهای دارای کشیده (بـِ ⬅ بِـ، لـِ ⬅ لِـ، کـَ ⬅ کَـ).
     */
    private fun normalizeCharactersAndDiacritics(input: String): String {
        val n = input.length
        val sb = StringBuilder(n)
        var i = 0
        while (i < n) {
            val ch = normalizeArabicYehAndKafChar(input[i])

            // حذفVariation Selector اضافی که در برخی فونت‌ها باعث شکستگی اتصال می‌شود
            if (ch == '\uFE0F' || ch == '\uFE0E') {
                i++
                continue
            }

            // اگر الف خنجری (U+0670) بلافاصله بعد از «ی» (که در اصل الف مقصوره «ىٰ» بوده، مانند قَضَىٰ، إِلَىٰ، عَلَىٰ، الْيَتَامَىٰ)
            // قرار گرفته باشد، در فونت‌های فارسی «ی» دوطرفه جایگزین شده و وجود U+0670 روی «ی» باعث تداخل یا جدایی می‌شود
            if (ch == SUPERSCRIPT_ALEF && sb.isNotEmpty()) {
                val lastBase = findLastNonDiacriticChar(sb)
                if (lastBase == PERSIAN_YEH) {
                    i++
                    continue
                }
            }

            // اگر فاصله مجازی (ZWNJ) یا ZWJ بلافاصله قبل از علامت اعراب عربی آمده باشد، آن را حذف کن
            // تا اعراب از حرف پایه جدا نشود (جلوگیری از نمایش دایره نقطه‌چین ◌ و شکستگی حرف)
            if ((ch == ZWNJ || ch == ZWJ) && i + 1 < n && isArabicDiacritic(input[i + 1])) {
                i++
                continue
            }

            // اصلاح الگوی «حرف پایه + کشیده (ـ) + اعراب» (مانند «بـِ»، «لـِ»، «کـَ»، «تـُ») به «حرف پایه + اعراب + کشیده (ـ)» («بِـ»، «لِـ»، «کَـ»، «تُـ»)
            // تا حرکت دقیقاً روی خود حرف قرار گیرد و خط کشیده دنباله حرف را بدون شکستگی امتداد دهد
            if (ch == TATWEEL &&
                sb.isNotEmpty() &&
                isDualJoiningLetter(sb[sb.length - 1]) &&
                i + 1 < n &&
                isArabicDiacritic(input[i + 1]) &&
                (i + 2 >= n || !isArabicOrPersianBaseLetter(normalizeArabicYehAndKafChar(input[i + 2])))
            ) {
                // ابتدا تمام اعراب‌های بلافاصله بعدی را روی حرف پایه سوار کن، سپس کشیده (TATWEEL) را بگذار
                var j = i + 1
                while (j < n && isArabicDiacritic(input[j])) {
                    sb.append(input[j])
                    j++
                }
                sb.append(TATWEEL)
                i = j
                continue
            }

            sb.append(ch)
            i++
        }

        // استانداردسازی ترتیب «حرکت + تشدید» ⬅ «تشدید + حرکت» برای رندر بی‌نقص HarfBuzz
        val chars = sb.toString().toCharArray()
        for (idx in 0 until chars.size - 1) {
            if (isVowelOrTanween(chars[idx]) && chars[idx + 1] == SHADDA) {
                val tmp = chars[idx]
                chars[idx] = SHADDA
                chars[idx + 1] = tmp
            }
        }
        return String(chars)
    }

    /**
     * گام ۳: مدیریت هوشمند فاصله مجازی (ZWNJ) در ساختار کلمات عربی و فارسی:
     * - جلوگیری از تکرار ZWNJ (تبدیل \u200C\u200C+ به یک \u200C)
     * - حذف ZWNJ در ابتدا/انتهای خط یا در مجاورت فاصله و علائم نگارشی
     * - حذف ZWNJ بعد از حروفی که از چپ نمی‌چسبند (ا، د، ذ، ر، ز، ژ، و، ة) مگر قبل از «ای/ام/ات/اش/اند/ایم/اید»
     * - حذف ZWNJ مخرب بعد از «ال» تعریف عربی (مانند «ال‌کتاب» ⬅ «الکتاب»)
     * - حذف ZWNJ مخرب در میان کلمات معرب عربی (کلمات دارای اعراب که پسوند فارسی ندارند)
     */
    private fun sanitizeZwnjInArabicAndPersianStructure(input: String): String {
        if (!input.contains(ZWNJ)) return input

        val n = input.length
        val sb = StringBuilder(n)
        var i = 0

        while (i < n) {
            val ch = input[i]
            if (ch != ZWNJ) {
                sb.append(ch)
                i++
                continue
            }

            // ۱. رد کردن فاصله‌های مجازی تکراری متوالی
            while (i + 1 < n && input[i + 1] == ZWNJ) {
                i++
            }

            // ۲. اگر در ابتدای متن یا انتهای متن است، یا قبل/بعد از آن فاصله یا علامت نگارشی است، ZWNJ را حذف کن
            val prevBase = findLastNonDiacriticChar(sb)
            val nextIdx = i + 1
            if (prevBase == null || nextIdx >= n) {
                i++
                continue
            }
            val nextCh = input[nextIdx]
            if (prevBase.isWhitespace() || nextCh.isWhitespace() || isPunctuationOrBracket(prevBase) || isPunctuationOrBracket(nextCh)) {
                i++
                continue
            }

            // ۳. اگر بعد از ZWNJ علامت اعراب عربی آمده باشد، ZWNJ را حذف کن تا اعراب از حرف جدا نشود
            if (isArabicDiacritic(nextCh)) {
                i++
                continue
            }

            // ۴. اگر حرف قبلی فقط از راست می‌چسبد (ا، د، ذ، ر، ز، ژ، و، ة)، خودش به صورت طبیعی به حرف بعدی نمی‌چسبد؛
            // تنها استثناء زمانی است که بعد از «ا / و» پسوند «ای / ام / ات / اش / اند / ایم / اید» بیاید.
            if (isRightOnlyJoiningLetter(prevBase)) {
                val rem = input.substring(nextIdx)
                val needsZwnjAfterVowel = (prevBase == 'ا' || prevBase == 'آ' || prevBase == 'و') &&
                    (rem.startsWith("ای") || rem.startsWith("ام") || rem.startsWith("ات") ||
                        rem.startsWith("اش") || rem.startsWith("اند") || rem.startsWith("ایم") || rem.startsWith("اید"))
                if (!needsZwnjAfterVowel) {
                    i++
                    continue
                }
            }

            // ۵. جلوگیری از شکستگی «ال» تعریف عربی:
            // اگر قبل از ZWNJ حرف «ل» باشد و قبل از آن «ا / أ / إ / آ» (یعنی «الـ» تعریف عربی مانند «ال‌کتاب» یا «الْمُعَلِّم»)،
            // هرگز نباید بعد از «ل» فاصله مجازی قرار بگیرد، چون «ل» باید به حرف آغازین کلمه عربی بچسبد!
            if (prevBase == 'ل' && isPrecededByAlefOfAl(sb)) {
                i++
                continue
            }

            // ۶. اگر درون یک کلمه (چه معرب عربی چه فارسی) فاصله مجازی افتاده باشد و بخش دوم
            // نه «الـ» باشد، نه «الیه/به» باشد، و نه پسوند فارسی (مثل «‌ها / ‌های / ‌ای»)،
            // آن فاصله مجازی مخرب را حذف کن تا کلمه دچار شکستگی نشود.
            val currentWordBefore = extractWordSuffixNonSpace(sb)
            val currentWordAfter = extractWordPrefixNonSpace(input, nextIdx)
            val cleanBefore = stripDiacritics(currentWordBefore)
            val cleanAfter = stripDiacritics(currentWordAfter)

            val isLegitimateCompoundOrSuffix =
                cleanAfter.startsWith("ال") ||
                    cleanAfter.startsWith("الیه") ||
                    cleanAfter.startsWith("به") ||
                    cleanBefore == "می" ||
                    cleanBefore == "نمی" ||
                    cleanBefore == "بی" ||
                    cleanBefore == "هم" ||
                    validPersianZwnjSuffixes.any { cleanAfter == it || cleanAfter.startsWith(it) }

            // NEW RULE: If both letters are dual-joining and it's not a legitimate compound, remove ZWNJ
            val bothDualJoining = isDualJoiningLetter(prevBase) && isDualJoiningLetter(nextCh)
            
            if (!isLegitimateCompoundOrSuffix || (bothDualJoining && !isLegitimateCompoundOrSuffix)) {
                // حذف ZWNJ مخرب
                i++
                continue
            }

            sb.append(ZWNJ)
            i++
        }

        return sb.toString()
    }

    private fun findLastNonDiacriticChar(sb: CharSequence): Char? {
        for (k in sb.length - 1 downTo 0) {
            val c = sb[k]
            if (!isArabicDiacritic(c)) return c
        }
        return null
    }

    private fun isPrecededByAlefOfAl(sb: CharSequence): Boolean {
        var idx = sb.length - 1
        while (idx >= 0 && isArabicDiacritic(sb[idx])) idx--
        if (idx <= 0 || sb[idx] != 'ل') return false
        idx--
        while (idx >= 0 && isArabicDiacritic(sb[idx])) idx--
        if (idx < 0) return false
        val beforeLam = sb[idx]
        if (beforeLam != 'ا' && beforeLam != 'أ' && beforeLam != 'إ' && beforeLam != 'آ') return false
        // اطمینان از اینکه «ال» در ابتدای کلمه یا بعد از حروف تک‌حرفی عربی (و، ف، ب، ل، ک) است
        val prevIdx = idx - 1
        if (prevIdx < 0) return true
        var p = prevIdx
        while (p >= 0 && isArabicDiacritic(sb[p])) p--
        if (p < 0) return true
        val chBeforeAlef = sb[p]
        return chBeforeAlef.isWhitespace() || isPunctuationOrBracket(chBeforeAlef) || chBeforeAlef == ZWNJ ||
            chBeforeAlef in charArrayOf('و', 'ف', 'ب', 'ل', 'ک')
    }

    private fun extractWordSuffixNonSpace(sb: CharSequence): String {
        var start = sb.length
        while (start > 0 && !sb[start - 1].isWhitespace() && !isPunctuationOrBracket(sb[start - 1]) && sb[start - 1] != ZWNJ) {
            start--
        }
        return sb.substring(start, sb.length)
    }

    private fun extractWordPrefixNonSpace(s: CharSequence, from: Int): String {
        var end = from
        while (end < s.length && !s[end].isWhitespace() && !isPunctuationOrBracket(s[end]) && s[end] != ZWNJ) {
            end++
        }
        return s.substring(from, end)
    }

    private fun stripDiacritics(s: String): String {
        val sb = StringBuilder(s.length)
        for (c in s) {
            if (!isArabicDiacritic(c)) sb.append(c)
        }
        return sb.toString()
    }

    private fun isRightOnlyJoiningLetter(ch: Char): Boolean {
        return ch in rightOnlyJoiningLetters
    }

    private fun isDualJoiningLetter(ch: Char): Boolean {
        return ch in dualJoiningLetters
    }

    private fun isArabicOrPersianBaseLetter(ch: Char): Boolean {
        return isDualJoiningLetter(ch) || isRightOnlyJoiningLetter(ch)
    }

    private fun isPunctuationOrBracket(ch: Char): Boolean {
        return ch in ".,:;!?،؛؟«»()[]{}\"'|-—–/\\*+=<>~"
    }

    /**
     * پردازش یک درس کامل (ZeroLesson) با اعمال استانداردهای متون عربی بر روی تمامی فیلدها
     */
    fun processLesson(lesson: ZeroLesson): ZeroLesson = lesson.copy(
        levelBadge = processArabicText(lesson.levelBadge),
        title = processArabicText(lesson.title),
        subtitle = processArabicText(lesson.subtitle),
        whyStudentsStruggle = processArabicText(lesson.whyStudentsStruggle),
        steps = lesson.steps.map { step ->
            ZeroStep(
                stepNumber = step.stepNumber,
                title = processArabicText(step.title),
                simpleExplanation = processArabicText(step.simpleExplanation),
                visualDiagramText = processArabicText(step.visualDiagramText),
                memoryTrick = processArabicText(step.memoryTrick)
            )
        },
        goldenFormulas = lesson.goldenFormulas.map { processArabicText(it) },
        workedExamples = lesson.workedExamples.map { ex ->
            WorkedExample(
                question = processArabicText(ex.question),
                stepByStepSolution = processArabicText(ex.stepByStepSolution),
                commonTrapAlert = processArabicText(ex.commonTrapAlert)
            )
        },
        miniCheck = MiniCheckQuestion(
            question = processArabicText(lesson.miniCheck.question),
            options = lesson.miniCheck.options.map { processArabicText(it) },
            correctIndex = lesson.miniCheck.correctIndex,
            explanation = processArabicText(lesson.miniCheck.explanation)
        )
    )

    private fun cleanStepExplanationText(raw: String): String {
        var text = raw
            .replace("<u>", "")
            .replace("</u>", "")
            .replace("<U>", "")
            .replace("</U>", "")
        val marker = "📖 آموزش کامل و پله‌به‌پله مفاهیم این گام:"
        if (text.contains(marker)) {
            text = text.substringAfter(marker).trimStart('\n', '\r', ' ')
        }
        val tailMarker = "• تشریح خط‌به‌خط مطابق کتاب درسی وزارت آموزش و پرورش"
        if (text.contains(tailMarker)) {
            text = text.substringBefore(tailMarker).trimEnd()
        }
        return text.trim()
    }

    private fun extractCleanSentences(text: String, maxCount: Int = 3): List<String> {
        val rawParts = text
            .split(Regex("[\\n.!؟؛]+"))
            .map { it.trim().trimStart('•', '-', '*', ' ') }
            .filter { it.length >= 12 }
        return if (rawParts.isNotEmpty()) {
            rawParts.take(maxCount)
        } else {
            listOf(text.trim()).filter { it.isNotEmpty() }
        }
    }

    private fun cleanVisualDiagram(rawDiagram: String, stepTitle: String, cleanExplanation: String): String {
        val cleaned = rawDiagram
            .replace("<u>", "")
            .replace("</u>", "")
            .trim()
        if (cleaned.contains("سبک‌های چهارگانه فرزندپروری")) {
            return cleaned
        }
        val isTruncatedSynthetic = cleaned.contains("┌── نقشه مفهومی و درخت یادگیری:") ||
            cleaned.contains("┌── نقشه مفهومی و ساختاری:") ||
            cleaned.contains("┌── جدول مقایسه‌ای و تفکیک") ||
            cleaned.contains("قطب اول (") ||
            cleaned.contains("├── شاخه ۱:")
        if (!isTruncatedSynthetic) {
            return cleaned
        }
        val sentences = extractCleanSentences(cleanExplanation, 3)
        if (sentences.isEmpty()) return ""
        return buildString {
            appendLine("بخش مفهومی | نکته کلیدی و کاربرد در درس")
            sentences.forEachIndexed { index, sentence ->
                val numFa = BidiFormatter.toPersianDigits((index + 1).toString())
                appendLine("محور $numFa ($stepTitle) | $sentence")
            }
        }.trim()
    }

    private fun cleanMemoryTrickText(rawTrick: String, stepTitle: String, cleanExplanation: String): String {
        val cleaned = rawTrick
            .replace("<u>", "")
            .replace("</u>", "")
            .trim()
        val isTruncatedSynthetic = cleaned.startsWith("رمز طلایی «") ||
            cleaned.startsWith("رمز تفکیک سریع:") ||
            cleaned.contains("قطب اول")
        if (!isTruncatedSynthetic) {
            return cleaned
        }
        val firstSentence = extractCleanSentences(cleanExplanation, 1).firstOrNull() ?: cleanExplanation
        return "نکته کلیدی ($stepTitle): $firstSentence"
    }

    fun enrichLessonContent(lesson: ZeroLesson): ZeroLesson {
        val processed = processLesson(lesson)
        val nonSyntheticSteps = processed.steps.filterNot {
            it.title.startsWith("کارگاه دست‌به‌قلم") ||
                it.title.startsWith("کالبدشکافی مفهومی")
        }
        val filteredSteps = nonSyntheticSteps.ifEmpty { processed.steps }

        val enrichedSteps = filteredSteps.mapIndexed { idx, step ->
            val cleanedTitle = step.title
                .replace("<u>", "")
                .replace("</u>", "")
                .let { t ->
                    if (t.startsWith("کالبدشکافی مفهومی")) {
                        "جمع‌بندی مفهومی و نکات کلیدی فصل"
                    } else if (t.startsWith("کارگاه دست‌به‌قلم")) {
                        "روش حل نمونه سوال تشریحی و تستی"
                    } else {
                        t
                    }
                }
            val cleanedExp = cleanStepExplanationText(step.simpleExplanation)
            val cleanedDiagram = cleanVisualDiagram(step.visualDiagramText, cleanedTitle, cleanedExp)
            val cleanedTrick = cleanMemoryTrickText(step.memoryTrick, cleanedTitle, cleanedExp)
            step.copy(
                stepNumber = idx + 1,
                title = cleanedTitle,
                simpleExplanation = cleanedExp,
                visualDiagramText = cleanedDiagram,
                memoryTrick = cleanedTrick
            )
        }

        val nonSyntheticExamples = processed.workedExamples.filterNot {
            it.question.startsWith("سوال تشریحی امتحان نهایی و تستی: تفاوت و رابطهٔ اصلی میان")
        }
        val filteredExamples = nonSyntheticExamples.ifEmpty {
            processed.workedExamples.map { ex ->
                if (ex.question.startsWith("سوال تشریحی امتحان نهایی و تستی: تفاوت و رابطهٔ اصلی میان")) {
                    val mainConcept = enrichedSteps.firstOrNull()?.title ?: processed.title
                    val mainExp = enrichedSteps.firstOrNull()?.simpleExplanation ?: processed.subtitle
                    WorkedExample(
                        question = "سوال تشریحی و تستی: مهم‌ترین نکته و کاربرد آموزشی در مبحث «${processed.title}» چیست؟",
                        stepByStepSolution = "گام ۱ (تعریف و مفهوم پایه): $mainConcept\nگام ۲ (تحلیل و کاربرد در حل سوال): $mainExp",
                        commonTrapAlert = "توجه به قیدهای صورت سوال و تشخیص دقیق تفاوت مفاهیم اصلی این درس."
                    )
                } else ex
            }
        }

        val enrichedFormulas = processed.goldenFormulas
            .map { it.replace("<u>", "").replace("</u>", "").trim() }
            .filterNot { it.startsWith("قاعده تفکیک دو بخش اصلی درس:") }
            .ifEmpty {
                val s1 = enrichedSteps.firstOrNull()?.title ?: processed.title
                listOf("نکته کلیدی ${processed.title}: تمرکز بر مفهوم «$s1» و تشخیص کلیدواژه‌های اصلی درس در صورت سوال.")
            }

        return processed.copy(
            steps = enrichedSteps,
            goldenFormulas = enrichedFormulas,
            workedExamples = filteredExamples
        )
    }

    fun processFlashcard(card: FlashcardItem): FlashcardItem = card.copy(
        categoryTag = processArabicText(card.categoryTag),
        frontQuestion = processArabicText(card.frontQuestion),
        backAnswer = processArabicText(card.backAnswer),
        memoryHook = processArabicText(card.memoryHook)
    )

    fun processQuizQuestion(q: QuizQuestionItem): QuizQuestionItem = q.copy(
        difficultyTag = processArabicText(q.difficultyTag),
        questionText = processArabicText(q.questionText),
        options = q.options.map { processArabicText(it) },
        zeroLevelSolution = processArabicText(q.zeroLevelSolution),
        shortcutTechnique = processArabicText(q.shortcutTechnique)
    )
}
