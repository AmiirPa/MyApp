package com.example.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.BookmarkBorder
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.LocalFireDepartment
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Pause
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Timer
import androidx.compose.material.icons.filled.EmojiEvents
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material.icons.filled.RadioButtonUnchecked
import androidx.compose.material.icons.filled.ErrorOutline
import androidx.compose.material.icons.filled.ThumbUp
import androidx.compose.material.icons.filled.FastForward
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FilterChip
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import com.example.data.curriculum.SubjectCatalog
import com.example.data.curriculum.ZeroLesson
import com.example.data.curriculum.normalizedSubjectId
import com.example.data.curriculum.officialTextbookReference
import com.example.data.curriculum.prerequisiteStepOrder
import com.example.data.curriculum.subcategoryFolder
import com.example.ui.KonkoorUiState
import com.example.ui.sharedPomodoroTimer
import com.example.util.StreakCalculator
import java.util.Calendar

data class SoldierRoutineSlot(
    val timeRange: String,
    val title: String,
    val subtitle: String,
    val badge: String,
    val requiredTestCount: Int,
    val isStudySlot: Boolean,
    val accentColor: Color,
    val linkedLesson: ZeroLesson? = null
)

data class CalendarDayPlan(
    val dayNumber: Int,
    val dayOfWeekName: String,
    val isWeekendOff: Boolean,
    val isOfficialHoliday: Boolean = false,
    val holidayOccasion: String? = null,
    val stepIndexFromZero: Int,
    val prescribedLessons: List<ZeroLesson>
)

// internal (نه private) تا تست واحد ShamsiHolidayCatalogTest بتواند جدول را مستقیم بخواند.
internal object ShamsiHolidayCatalog {
    // فقط تعطیلات خورشیدی با تاریخ ثابت جلالی — دست نخورده‌اند.
    // ورودی‌های قمری جدول قدیمی (فطر، صادق، قربان، غدیر، تاسوعا، عاشورا، اربعین،
    // رحلت پیامبر، رضا، عسکری، میلاد، فاطمه، ولادت علی، مبعث، نیمه شعبان، شهادت
    // علی) از اینجا حذف و به جدول سالانهٔ lunarHolidaysByYear / lunarOccasionsByYear
    // در پایین منتقل شدند؛ نگه‌داشتنشان یعنی همان خطای ~۳ هفته‌ای.
    private val holidaysByMonth: Map<Int, Map<Int, String>> = mapOf(
        1 to mapOf(
            1 to "عید نوروز (تعطیل رسمی)",
            2 to "عید نوروز (تعطیل رسمی)",
            3 to "عید نوروز (تعطیل رسمی)",
            4 to "عید نوروز (تعطیل رسمی)",
            12 to "روز جمهوری اسلامی (تعطیل رسمی)",
            13 to "روز طبیعت / سیزده‌به‌در (تعطیل رسمی)"
        ),
        3 to mapOf(
            14 to "رحلت حضرت امام خمینی (ره) (تعطیل رسمی)",
            15 to "قیام ۱۵ خرداد (تعطیل رسمی)"
        ),
        7 to mapOf(
            13 to "جمعه طلایی جمع‌بندی مهرماه"
        ),
        11 to mapOf(
            22 to "پیروزی انقلاب اسلامی ایران (تعطیل رسمی)"
        ),
        12 to mapOf(
            29 to "روز ملی شدن صنعت نفت ایران (تعطیل رسمی)"
        )
    )

    // ─── تعطیلات قمری با تاریخ دقیق جلالی هر سال (۱۴۰۴–۱۴۰۶) ───
    // مشکل قبلی: تعطیلات قمری روی تاریخ‌های ثابت جلالی گذاشته شده بودند؛ چون سال
    // قمری ~۱۱ روز از سال خورشیدی کوتاه‌تر است، در ۱۴۰۴ خطا تا ~۳ هفته بود (مثلاً
    // عید فطر روی ۲–۳ اردیبهشت گذاشته شده بود، در حالی که ۱۱–۱۲ فروردین است؛
    // شهادت امام صادق روی ۲۵ اردیبهشت بود، در حالی که ۴ اردیبهشت است).
    // منبع تاریخ‌ها: تقویم رسمی ایران آن‌گونه که time.ir منتشر می‌کند
    // (بازیابی ۱۷ آبان ۱۴۰۵ / 2026-11-08)؛ برای ۱۴۰۴ و ۱۴۰۵ با
    // bahesab.ir/time/1404/ و bahesab.ir/time/1405/ (آینهٔ همان تقویم رسمی) و
    // صفحهٔ اصلی time.ir (۱۷ آبان ۱۴۰۵ = ۲۸ جمادی‌الاول ۱۴۴۸؛ مناسبت‌های آبان
    // شامل «۲۲ آبان شهادت حضرت فاطمه زهرا (س)»)، و برای ۱۴۰۶ با جدول
    // iranian_holidays 0.3.0 که می‌گوید «هر دو سال ۱۴۰۵ و ۱۴۰۶ با تقویم
    // منتشرشده در time.ir روزبه‌روز مطابق‌اند» و با multicalendar و nayrika و
    // roozaneh مقابله شد. جدول کامل با تاریخ قمری هر مناسبت در سند
    // lunar-holidays-1404-1407.md کنار همین کار آمده است.
    // «نامشخص»: (۱) سال ۱۴۰۷ — تقویم رسمی ۱۴۰۷ هنوز منتشر نشده، پس هیچ تاریخ
    // قمری برای ۱۴۰۷ در جدول نیست و lookup برای ۱۴۰۷ فقط خورشیدی برمی‌گرداند؛
    // پس از انتشار تقویم رسمی ۱۴۰۷ باید اضافه شود. (۲) شهادت حضرت فاطمه (س) —
    // روایت دوم (۱۳ جمادی‌الاول) در ۱۴۰۶ — در هیچ منبع معتبری تاریخ جلالی آن
    // یافت نشد، پس حدس زده نشد و ثبت نشد.
    // توجه: ماه‌های قمری با رؤیت هلال قطعی می‌شوند؛ اگر اعلام رسمی تاریخ را یک
    // روز جابه‌جا کرد، جدول باید اصلاح شود.
    private val lunarHolidaysByYear: Map<Int, Map<Int, Map<Int, String>>> = mapOf(
        1404 to mapOf(
            1 to mapOf(
                2 to "شهادت حضرت علی (ع) (تعطیل رسمی)",
                11 to "عید سعید فطر (تعطیل رسمی)",
                12 to "تعطیل به مناسبت عید سعید فطر (تعطیل رسمی)"
            ),
            2 to mapOf(
                4 to "شهادت امام جعفر صادق (ع) (تعطیل رسمی)"
            ),
            3 to mapOf(
                16 to "عید سعید قربان (تعطیل رسمی)",
                24 to "عید سعید غدیر خم (تعطیل رسمی)"
            ),
            4 to mapOf(
                14 to "تاسوعای حسینی (تعطیل رسمی)",
                15 to "عاشورای حسینی (تعطیل رسمی)"
            ),
            5 to mapOf(
                23 to "اربعین حسینی (تعطیل رسمی)",
                31 to "رحلت پیامبر اکرم (ص) و شهادت امام حسن مجتبی (ع) (تعطیل رسمی)"
            ),
            6 to mapOf(
                2 to "شهادت امام رضا (ع) (تعطیل رسمی)",
                10 to "شهادت امام حسن عسکری (ع) (تعطیل رسمی)",
                19 to "میلاد پیامبر اکرم (ص) و امام جعفر صادق (ع) (تعطیل رسمی)"
            ),
            9 to mapOf(
                3 to "شهادت حضرت فاطمه زهرا (س) (تعطیل رسمی)"
            ),
            10 to mapOf(
                13 to "ولادت امام علی (ع) و روز پدر (تعطیل رسمی)",
                27 to "مبعث حضرت رسول اکرم (ص) (تعطیل رسمی)"
            ),
            11 to mapOf(
                15 to "ولادت حضرت قائم (عج) — نیمه شعبان (تعطیل رسمی)"
            ),
            12 to mapOf(
                20 to "شهادت حضرت علی (ع) (تعطیل رسمی)"
            )
        ),
        1405 to mapOf(
            1 to mapOf(
                1 to "عید سعید فطر (تعطیل رسمی)",
                2 to "تعطیل به مناسبت عید سعید فطر (تعطیل رسمی)",
                25 to "شهادت امام جعفر صادق (ع) (تعطیل رسمی)"
            ),
            3 to mapOf(
                6 to "عید سعید قربان (تعطیل رسمی)",
                14 to "عید سعید غدیر خم (تعطیل رسمی)"
            ),
            4 to mapOf(
                3 to "تاسوعای حسینی (تعطیل رسمی)",
                4 to "عاشورای حسینی (تعطیل رسمی)"
            ),
            5 to mapOf(
                13 to "اربعین حسینی (تعطیل رسمی)",
                21 to "رحلت پیامبر اکرم (ص) و شهادت امام حسن مجتبی (ع) (تعطیل رسمی)",
                22 to "شهادت امام رضا (ع) (تعطیل رسمی)",
                30 to "شهادت امام حسن عسکری (ع) (تعطیل رسمی)"
            ),
            6 to mapOf(
                8 to "میلاد پیامبر اکرم (ص) و امام جعفر صادق (ع) (تعطیل رسمی)"
            ),
            8 to mapOf(
                22 to "شهادت حضرت فاطمه زهرا (س) (تعطیل رسمی)"
            ),
            10 to mapOf(
                2 to "ولادت امام علی (ع) و روز پدر (تعطیل رسمی)",
                16 to "مبعث حضرت رسول اکرم (ص) (تعطیل رسمی)"
            ),
            11 to mapOf(
                4 to "ولادت حضرت قائم (عج) — نیمه شعبان (تعطیل رسمی)"
            ),
            12 to mapOf(
                9 to "شهادت حضرت علی (ع) (تعطیل رسمی)",
                19 to "عید سعید فطر (تعطیل رسمی)",
                20 to "تعطیل به مناسبت عید سعید فطر (تعطیل رسمی)"
            )
        ),
        1406 to mapOf(
            1 to mapOf(
                14 to "شهادت امام جعفر صادق (ع) (تعطیل رسمی)"
            ),
            2 to mapOf(
                27 to "عید سعید قربان (تعطیل رسمی)"
            ),
            3 to mapOf(
                4 to "عید سعید غدیر خم (تعطیل رسمی)",
                25 to "تاسوعای حسینی (تعطیل رسمی)",
                26 to "عاشورای حسینی (تعطیل رسمی)"
            ),
            5 to mapOf(
                3 to "اربعین حسینی (تعطیل رسمی)",
                11 to "رحلت پیامبر اکرم (ص) و شهادت امام حسن مجتبی (ع) (تعطیل رسمی)",
                12 to "شهادت امام رضا (ع) (تعطیل رسمی)",
                20 to "شهادت امام حسن عسکری (ع) (تعطیل رسمی)",
                29 to "میلاد پیامبر اکرم (ص) و امام جعفر صادق (ع) (تعطیل رسمی)"
            ),
            8 to mapOf(
                12 to "شهادت حضرت فاطمه زهرا (س) (تعطیل رسمی)"
            ),
            9 to mapOf(
                21 to "ولادت امام علی (ع) و روز پدر (تعطیل رسمی)"
            ),
            10 to mapOf(
                5 to "مبعث حضرت رسول اکرم (ص) (تعطیل رسمی)",
                23 to "ولادت حضرت قائم (عج) — نیمه شعبان (تعطیل رسمی)"
            ),
            11 to mapOf(
                29 to "شهادت حضرت علی (ع) (تعطیل رسمی)"
            ),
            12 to mapOf(
                8 to "عید سعید فطر (تعطیل رسمی)",
                9 to "تعطیل به مناسبت عید سعید فطر (تعطیل رسمی)"
            )
        )
        // ۱۴۰۷: «نامشخص» — تقویم رسمی ۱۴۰۷ هنوز منتشر نشده است.
    )

    // مناسبت‌های قمری که در تقویم رسمی «مناسبت»‌اند نه تعطیل: شهادت حضرت فاطمه (س)
    // به روایت ۱۳ جمادی‌الاول. نامشان در برنامه نمایش داده می‌شود اما روز مطالعه
    // را تعطیل نمی‌کنند (isOfficialHoliday=false). در جدول قدیمی هر دو روایت
    // فاطمه «تعطیل رسمی» خورده بودند که خلاف تقویم رسمی است.
    private val lunarOccasionsByYear: Map<Int, Map<Int, Map<Int, String>>> = mapOf(
        1404 to mapOf(
            8 to mapOf(
                13 to "شهادت حضرت فاطمه زهرا (س) — روایت دوم (مناسبت مذهبی)"
            )
        ),
        1405 to mapOf(
            8 to mapOf(
                2 to "شهادت حضرت فاطمه زهرا (س) — روایت دوم (مناسبت مذهبی)"
            )
        )
        // ۱۴۰۶: «نامشخص» — تاریخ جلالی ۱۳ جمادی‌الاول ۱۴۴۹ در منبع معتبری یافت نشد.
        // ۱۴۰۷: «نامشخص» — تقویم رسمی ۱۴۰۷ هنوز منتشر نشده است.
    )

    private fun mergeHolidayTitles(first: String, second: String): String {
        val a = first.removeSuffix(" (تعطیل رسمی)")
        val b = second.removeSuffix(" (تعطیل رسمی)")
        return "$a + $b (تعطیل رسمی)"
    }

    fun getHolidayOccasion(year: Int, month: Int, day: Int): String? {
        val solar = holidaysByMonth[month]?.get(day)
        val lunar = lunarHolidaysByYear[year]?.get(month)?.get(day)
            ?: lunarOccasionsByYear[year]?.get(month)?.get(day)
        if (solar == null) return lunar
        if (lunar == null) return solar
        return mergeHolidayTitles(solar, lunar)
    }

    fun isOfficialHoliday(year: Int, month: Int, day: Int): Boolean {
        // خورشیدی: رفتار قبلی حفظ شده (هر ورودی جدول خورشیدی تعطیل محسوب می‌شود).
        if (holidaysByMonth[month]?.containsKey(day) == true) return true
        return lunarHolidaysByYear[year]?.get(month)?.containsKey(day) == true
    }

    fun getMonthHolidays(year: Int, month: Int): List<Pair<Int, String>> {
        val merged = mutableMapOf<Int, String>()
        holidaysByMonth[month]?.forEach { (day, title) -> merged[day] = title }
        lunarHolidaysByYear[year]?.get(month)?.forEach { (day, title) ->
            merged[day] = merged[day]?.let { mergeHolidayTitles(it, title) } ?: title
        }
        return merged.entries.map { it.key to it.value }.sortedBy { it.first }
    }
}

object JalaliCalendarUtils {
    fun isLeapJalaliYear(jy: Int): Boolean {
        // 33-year cycle leap years + 1403/1408 official Iranian calendar leap years
        if (jy == 1403 || jy == 1408 || jy == 1399) return true
        if (jy in listOf(1400, 1401, 1402, 1404, 1405, 1406, 1407)) return false
        val rem = ((jy + 2346) * 683) % 2820
        return rem < 683
    }

    fun daysInJalaliMonth(jy: Int, jm: Int): Int {
        return when (jm) {
            in 1..6 -> 31
            in 7..11 -> 30
            12 -> if (isLeapJalaliYear(jy)) 30 else 29
            else -> 30
        }
    }
}

private fun buildZeroBasedInterleavedPool(
    allLessons: List<ZeroLesson>,
    subjectOrder: List<String>,
    minGradeFolder: Int = 1
): List<ZeroLesson> {
    val perSubjectSorted = subjectOrder.map { subId ->
        allLessons
            .filter { it.normalizedSubjectId() == subId && it.subcategoryFolder().first >= minGradeFolder }
            .sortedWith(
                compareBy<ZeroLesson>(
                    { it.subcategoryFolder().first },
                    { it.prerequisiteStepOrder() },
                    { it.orderIndex }
                )
            )
    }.filter { it.isNotEmpty() }

    if (perSubjectSorted.isEmpty()) return emptyList()

    val result = mutableListOf<ZeroLesson>()
    for (grade in minGradeFolder..4) {
        val gradeLists = perSubjectSorted.map { list ->
            list.filter { it.subcategoryFolder().first == grade }
        }.filter { it.isNotEmpty() }
        val maxLen = gradeLists.maxOfOrNull { it.size } ?: 0
        for (stepIdx in 0 until maxLen) {
            for (subList in gradeLists) {
                if (stepIdx < subList.size) {
                    result.add(subList[stepIdx])
                }
            }
        }
    }
    return result.distinctBy { it.id }
}

/**
 * برنامه‌ریز هوشمند و هدفمند کنکور انسانی و فرهنگیان
 * - تعداد تست الزامی برای هر درس (۳۵ الی ۴۵ تست کنکوری)
 * - سیستم «بلد شدم» (تسلط کامل) / «بلد نشدم» (انتقال خودکار به برنامه فردا)
 * - دکمه اضطراری «امروز نتوانستم بخوانم» (شیفت خودکار کل برنامه به روز بعد)
 */
@Composable
fun SmartSoldierTeacherPlannerCard(
    uiState: KonkoorUiState,
    onOpenLesson: (String) -> Unit,
    onToggleLessonCompleted: (ZeroLesson, Boolean) -> Unit,
    onToggleLessonBookmarked: (ZeroLesson, Boolean) -> Unit,
    modifier: Modifier = Modifier
) {
    val calendarNow = remember { Calendar.getInstance() }

    val persianWeekDays = remember {
        listOf("شنبه", "یکشنبه", "دوشنبه", "سه‌شنبه", "چهارشنبه", "پنجشنبه", "جمعه")
    }
    val shamsiMonthNames = remember {
        listOf(
            "فروردین", "اردیبهشت", "خرداد", "تیر", "مرداد", "شهریور",
            "مهر", "آبان", "آذر", "دی", "بهمن", "اسفند"
        )
    }
    val gregorianMonthNames = remember {
        listOf(
            "ژانویه", "فوریه", "مارس", "آوریل", "مه", "ژوئن",
            "ژوئیه", "اوت", "سپتامبر", "اکتبر", "نوامبر", "دسامبر"
        )
    }

    val todayInfo = remember {
        val gy = calendarNow.get(Calendar.YEAR)
        val gm = calendarNow.get(Calendar.MONTH) + 1
        val gd = calendarNow.get(Calendar.DAY_OF_MONTH)
        val g_d_m = intArrayOf(0, 31, 59, 90, 120, 151, 181, 212, 243, 273, 304, 334)
        val gy2 = if (gm > 2) gy + 1 else gy
        var days = 355666 + (365 * gy) + ((gy2 + 3) / 4) - ((gy2 + 99) / 100) + ((gy2 + 399) / 400) + gd + g_d_m[gm - 1]
        var jy = -1595 + (33 * (days / 12053))
        days %= 12053
        jy += 4 * (days / 1461)
        days %= 1461
        if (days > 365) {
            jy += (days - 1) / 365
            days = (days - 1) % 365
        }
        val jm = if (days < 186) 1 + (days / 31) else 7 + ((days - 186) / 30)
        val jd = 1 + if (days < 186) (days % 31) else ((days - 186) % 30)

        val dowJava = calendarNow.get(Calendar.DAY_OF_WEEK)
        val todayDowIdx = when (dowJava) {
            Calendar.SATURDAY -> 0
            Calendar.SUNDAY -> 1
            Calendar.MONDAY -> 2
            Calendar.TUESDAY -> 3
            Calendar.WEDNESDAY -> 4
            Calendar.THURSDAY -> 5
            Calendar.FRIDAY -> 6
            else -> 5
        }
        Triple(Triple(jy, jm, jd), Triple(gy, gm, gd), todayDowIdx)
    }

    val currentShamsiYear = todayInfo.first.first.coerceIn(1404, 1407)
    val currentShamsiMonth = todayInfo.first.second
    val defaultTodayNumber = todayInfo.first.third.coerceIn(1, 31)
    val currentGregorianMonth = todayInfo.second.second
    val currentGregorianDay = todayInfo.second.third
    val todayDowIndex = todayInfo.third

    var selectedShamsiYear by remember { mutableIntStateOf(currentShamsiYear) }
    var selectedShamsiMonth by remember { mutableIntStateOf(currentShamsiMonth) }
    var selectedDayNumber by remember { mutableIntStateOf(defaultTodayNumber) }
    var customStartDay by remember { mutableIntStateOf(defaultTodayNumber) }
    var manualStepOffset by remember { mutableIntStateOf(0) }
    var missedDaysShift by remember { mutableIntStateOf(0) }
    var startingLevelGrade by remember { mutableIntStateOf(1) }

    // Map to track mastery status: lessonId -> true (بلد شدم) / false (بلد نشدم - انتقال به فردا)
    val lessonMasteryStatus = remember { mutableStateMapOf<String, Boolean>() }

    // Pomodoro Timer State — hoisted OUT of this composable into
    // sharedPomodoroTimer (see PomodoroTimer.kt). The old remember { ... } state
    // + LaunchedEffect lived inside this card, so switching tabs disposed the
    // composition and the countdown silently reset to 25:00. The shared holder
    // survives tab switches; this card only collects and displays it.
    val pomodoro by sharedPomodoroTimer.state.collectAsState()

    val daysInSelectedMonth = remember(selectedShamsiYear, selectedShamsiMonth) {
        JalaliCalendarUtils.daysInJalaliMonth(selectedShamsiYear, selectedShamsiMonth)
    }

    val firstDayOfMonthDowIndex = remember(selectedShamsiYear, selectedShamsiMonth, currentShamsiYear, currentShamsiMonth, defaultTodayNumber, todayDowIndex) {
        // Days from today to the 1st of the selected month, counted with real Jalali
        // month lengths (31/30/29-30) instead of the old 365/30 approximation, which
        // drifted by one day per leap year and per 31-day month crossed.
        var offsetFromToday = 1 - defaultTodayNumber
        var y = currentShamsiYear
        var m = currentShamsiMonth
        while (y != selectedShamsiYear || m != selectedShamsiMonth) {
            if (y < selectedShamsiYear || (y == selectedShamsiYear && m < selectedShamsiMonth)) {
                offsetFromToday += JalaliCalendarUtils.daysInJalaliMonth(y, m)
                m++
                if (m > 12) { m = 1; y++ }
            } else {
                m--
                if (m < 1) { m = 12; y-- }
                offsetFromToday -= JalaliCalendarUtils.daysInJalaliMonth(y, m)
            }
        }
        ((todayDowIndex + offsetFromToday) % 7 + 7) % 7
    }

    val thirtyDayPlans = remember(
        uiState.allLessons,
        selectedShamsiYear,
        selectedShamsiMonth,
        daysInSelectedMonth,
        firstDayOfMonthDowIndex,
        customStartDay,
        manualStepOffset,
        missedDaysShift,
        startingLevelGrade
    ) {
        val poolBox1Analytical = buildZeroBasedInterleavedPool(
            allLessons = uiState.allLessons,
            subjectOrder = listOf("arabic", "fonoon", "english", "arooz", "math"),
            minGradeFolder = startingLevelGrade
        )
        val poolBox2Conceptual = buildZeroBasedInterleavedPool(
            allLessons = uiState.allLessons,
            subjectOrder = listOf("sociology", "logic", "economics", "psychology", "philosophy"),
            minGradeFolder = startingLevelGrade
        )
        val poolBox3Farhangian = buildZeroBasedInterleavedPool(
            allLessons = uiState.allLessons,
            subjectOrder = listOf("study_methods", "dini", "farhangian"),
            minGradeFolder = startingLevelGrade
        )
        val poolBox4HistoryFarsi = buildZeroBasedInterleavedPool(
            allLessons = uiState.allLessons,
            subjectOrder = listOf("history", "geography", "farsi", "negaresh", "social_identity", "general_humanities", "media_environment"),
            minGradeFolder = startingLevelGrade
        )

        (1..daysInSelectedMonth).map { dayIdx ->
            val dowIdx = (firstDayOfMonthDowIndex + (dayIdx - 1)) % 7
            val dowName = persianWeekDays[dowIdx]
            val holidayTitle = ShamsiHolidayCatalog.getHolidayOccasion(selectedShamsiYear, selectedShamsiMonth, dayIdx)
            val isOfficialHol = ShamsiHolidayCatalog.isOfficialHoliday(selectedShamsiYear, selectedShamsiMonth, dayIdx) || dowIdx == 6
            val isWeekend = dowIdx == 5 || dowIdx == 6 || isOfficialHol

            val monthOffset = (selectedShamsiMonth - currentShamsiMonth).coerceAtLeast(0) * 30
            // Include missedDaysShift so that if user skipped yesterday, tomorrow's step shifts forward automatically
            val stepFromZero = ((dayIdx - customStartDay) + monthOffset + manualStepOffset + missedDaysShift).coerceAtLeast(0)

            val dayLessons = buildList {
                if (poolBox1Analytical.isNotEmpty()) add(poolBox1Analytical[stepFromZero % poolBox1Analytical.size])
                if (poolBox2Conceptual.isNotEmpty()) add(poolBox2Conceptual[stepFromZero % poolBox2Conceptual.size])
                if (poolBox3Farhangian.isNotEmpty()) add(poolBox3Farhangian[stepFromZero % poolBox3Farhangian.size])
                if (poolBox4HistoryFarsi.isNotEmpty()) add(poolBox4HistoryFarsi[stepFromZero % poolBox4HistoryFarsi.size])
            }.distinctBy { it.id }

            CalendarDayPlan(
                dayNumber = dayIdx,
                dayOfWeekName = dowName,
                isWeekendOff = isWeekend,
                isOfficialHoliday = isOfficialHol,
                holidayOccasion = holidayTitle ?: if (dowIdx == 6) "جمعه (تعطیل رسمی هفتگی — طلایی برای سبقت کنکوری)" else if (dowIdx == 5) "پنجشنبه (نیمه‌تعطیل — شروع زودهنگام مطالعه)" else null,
                stepIndexFromZero = stepFromZero + 1,
                prescribedLessons = dayLessons
            )
        }
    }

    val activeDayPlan = thirtyDayPlans[(selectedDayNumber - 1).coerceIn(0, thirtyDayPlans.lastIndex)]
    var scheduleModeOverride by remember(selectedDayNumber, selectedShamsiMonth) {
        mutableIntStateOf(if (activeDayPlan.isWeekendOff || activeDayPlan.isOfficialHoliday) 1 else 0)
    }
    val isWeekendSchedule = scheduleModeOverride == 1
    // امکان تنظیم کاملاً دلخواه ساعت و دقیقه شروع مطالعه + شدت برنامه روزانه
    var customStudyStartHour by remember(selectedDayNumber, selectedShamsiMonth, isWeekendSchedule) {
        mutableIntStateOf(if (isWeekendSchedule) 8 else 16)
    }
    var customStudyStartMinute by remember(selectedDayNumber, selectedShamsiMonth) {
        mutableIntStateOf(0)
    }
    var studyIntensityLevel by remember { mutableIntStateOf(1) } // 0=سبک/فشرده، 1=استاندارد کنکوری، 2=فوق‌سنگین رتبه‌برتر
    var customBreakDuration by remember { mutableIntStateOf(15) } // ۱۰، ۱۵، ۲۰ دقیقه استراحت

    // استراتژی هوشمند جبران عقب‌افتادگی:
    // 0: توزیع متعادل در پارت‌های روزهای هفته (+۱۵ دقیقه به هر پارت)
    // 1: انتقال به جمعه نجات (آخر هفته طلایی)
    // 2: پارت شبانه جبرانی اختصاصی (ساعت ۲۲:۰۰)
    // 3: ریست هوشمند و شروع نو بدون استرس
    var catchUpStrategy by remember { mutableIntStateOf(0) }
    var isCatchUpPanelExpanded by remember { mutableStateOf(true) }

    // تشخیص دروس عقب‌افتاده از روزهای قبل تا روز انتخابی فعلی
    val overdueLessons = remember(thirtyDayPlans, selectedDayNumber, uiState.lessonProgressMap) {
        val currentPlanIdx = (selectedDayNumber - 1).coerceIn(0, thirtyDayPlans.lastIndex)
        thirtyDayPlans.take(currentPlanIdx)
            .flatMap { it.prescribedLessons }
            .filter { lesson -> uiState.lessonProgressMap[lesson.id]?.isCompleted != true }
            .distinctBy { it.id }
    }

    fun makeTimeRange(startTotalMinutes: Int, durM: Int): String {
        val sTotalMin = ((startTotalMinutes % (24 * 60)) + (24 * 60)) % (24 * 60)
        val eTotalMin = ((sTotalMin + durM) % (24 * 60))
        val sH = sTotalMin / 60
        val sM = sTotalMin % 60
        val eH = eTotalMin / 60
        val eM = eTotalMin % 60
        val sHStr = String.format(java.util.Locale.US, "%02d", sH)
        val sMStr = String.format(java.util.Locale.US, "%02d", sM)
        val eHStr = String.format(java.util.Locale.US, "%02d", eH)
        val eMStr = String.format(java.util.Locale.US, "%02d", eM)
        return "${BidiFormatter.toPersianDigits(sHStr)}:${BidiFormatter.toPersianDigits(sMStr)} تا ${BidiFormatter.toPersianDigits(eHStr)}:${BidiFormatter.toPersianDigits(eMStr)}"
    }

    val l0 = activeDayPlan.prescribedLessons.getOrNull(0)
    val l1 = activeDayPlan.prescribedLessons.getOrNull(1)
    val l2 = activeDayPlan.prescribedLessons.getOrNull(2)
    val l3 = activeDayPlan.prescribedLessons.getOrNull(3)

    val hourlyRoutineSlots = remember(
        isWeekendSchedule,
        activeDayPlan,
        customStudyStartHour,
        customStudyStartMinute,
        studyIntensityLevel,
        customBreakDuration,
        catchUpStrategy,
        overdueLessons
    ) {
        val sh = customStudyStartHour
        val sm = customStudyStartMinute
        val baseStartMin = sh * 60 + sm
        val extraAbsorbMin = if (catchUpStrategy == 0 && overdueLessons.isNotEmpty()) 15 else 0
        val slotDur1 = (when (studyIntensityLevel) { 0 -> 75; 2 -> 120; else -> 105 }) + extraAbsorbMin
        val slotDur2 = (when (studyIntensityLevel) { 0 -> 75; 2 -> 120; else -> 105 }) + extraAbsorbMin
        val slotDur3 = when (studyIntensityLevel) { 0 -> 60; 2 -> 90; else -> 75 }
        val slotDur4 = when (studyIntensityLevel) { 0 -> 60; 2 -> 90; else -> 75 }
        val breakMin = customBreakDuration
        val testMultiplier = when (studyIntensityLevel) { 0 -> 0.75f; 2 -> 1.35f; else -> 1.0f }

        val slots = mutableListOf<SoldierRoutineSlot>()
        val startClockFa = "${BidiFormatter.toPersianDigits(String.format(java.util.Locale.US, "%02d", sh))}:${BidiFormatter.toPersianDigits(String.format(java.util.Locale.US, "%02d", sm))}"

        if (isWeekendSchedule) {
            slots.add(
                SoldierRoutineSlot(
                    timeRange = "۰۶:۳۰ تا $startClockFa",
                    title = "🌅 بیداری صبحگاهی روز تعطیل + صبحانه مقوی و آمادگی ذهنی",
                    subtitle = activeDayPlan.holidayOccasion ?: "روز تعطیل طلایی برای شروع زودهنگام مطالعه پیش از رقبا!",
                    badge = "شروع صبحگاهی",
                    requiredTestCount = 0,
                    isStudySlot = false,
                    accentColor = Color(0xFFE11D48)
                )
            )
        } else if (sh >= 13) {
            slots.add(
                SoldierRoutineSlot(
                    timeRange = "۰۷:۳۰ تا $startClockFa",
                    title = "🏫 حضور در مدرسه / دانشگاه + استراحت پیش از مطالعه",
                    subtitle = "تا ساعت شروع دلخواهت ($startClockFa) استراحت کن تا با ذهن شفاف شروع کنی.",
                    badge = "مدرسه / کار",
                    requiredTestCount = 0,
                    isStudySlot = false,
                    accentColor = Color(0xFF0D9488)
                )
            )
        }

        var cursorMin = baseStartMin
        slots.add(
            SoldierRoutineSlot(
                timeRange = makeTimeRange(cursorMin, slotDur1),
                title = "⚡ باکس اول (تحلیلی: عربی، فنون و ریاضی)",
                subtitle = l0?.title ?: "درس ۱ پیش‌نیاز",
                badge = "${BidiFormatter.toPersianDigits(slotDur1.toString())} دقیقه",
                requiredTestCount = (45 * testMultiplier).toInt(),
                isStudySlot = true,
                accentColor = Color(0xFF2563EB),
                linkedLesson = l0
            )
        )
        cursorMin += slotDur1 + breakMin

        slots.add(
            SoldierRoutineSlot(
                timeRange = makeTimeRange(cursorMin, slotDur2),
                title = "🧠 باکس دوم (مفهومی: منطق، جامعه و فلسفه)",
                subtitle = l1?.title ?: "درس ۱ انسانی",
                badge = "${BidiFormatter.toPersianDigits(slotDur2.toString())} دقیقه",
                requiredTestCount = (40 * testMultiplier).toInt(),
                isStudySlot = true,
                accentColor = Color(0xFF7C3AED),
                linkedLesson = l1
            )
        )
        cursorMin += slotDur2 + breakMin

        slots.add(
            SoldierRoutineSlot(
                timeRange = makeTimeRange(cursorMin, slotDur3),
                title = "🎓 باکس سوم (ویژه فرهنگیان و دین و زندگی)",
                subtitle = l2?.title ?: "درس ۱ فرهنگیان",
                badge = "${BidiFormatter.toPersianDigits(slotDur3.toString())} دقیقه",
                requiredTestCount = (30 * testMultiplier).toInt(),
                isStudySlot = true,
                accentColor = Color(0xFF059669),
                linkedLesson = l2
            )
        )
        cursorMin += slotDur3 + breakMin

        slots.add(
            SoldierRoutineSlot(
                timeRange = makeTimeRange(cursorMin, slotDur4),
                title = "🌳 باکس چهارم (تاریخ، جغرافیا و فارسی)",
                subtitle = l3?.title ?: "درس ۱ تاریخ",
                badge = "${BidiFormatter.toPersianDigits(slotDur4.toString())} دقیقه",
                requiredTestCount = (30 * testMultiplier).toInt(),
                isStudySlot = true,
                accentColor = Color(0xFFD97706),
                linkedLesson = l3
            )
        )
        cursorMin += slotDur4 + breakMin

        // اگر استراتژی ۲ فعال باشد و درس عقب‌افتاده داشته باشیم، پارت اختصاصی جبرانی شبانه درج می‌شود
        if (catchUpStrategy == 2 && overdueLessons.isNotEmpty()) {
            val firstOverdue = overdueLessons.firstOrNull()
            if (firstOverdue != null) {
                slots.add(
                    SoldierRoutineSlot(
                        timeRange = makeTimeRange(cursorMin, 60),
                        title = "🚨 پارت پنجم: جبران هوشمند عقب‌افتادگی (بدون استرس)",
                        subtitle = firstOverdue.title,
                        badge = "۶۰ دقیقه جبرانی",
                        requiredTestCount = (35 * testMultiplier).toInt(),
                        isStudySlot = true,
                        accentColor = Color(0xFFDC2626),
                        linkedLesson = firstOverdue
                    )
                )
                cursorMin += 60 + breakMin
            }
        }

        slots.add(
            SoldierRoutineSlot(
                timeRange = makeTimeRange(cursorMin, 50),
                title = "🚨 باکس مرور، لایتنر شبانه و رفع اشکال تست‌های غلط",
                subtitle = "مرور تست‌های «بلد نشدم» و قفل کردن نکات در حافظه بلندمدت.",
                badge = "۵۰ دقیقه",
                requiredTestCount = (20 * testMultiplier).toInt(),
                isStudySlot = false,
                accentColor = Color(0xFFE11D48)
            )
        )

        slots
    }

    val todayShamsiMonthName = shamsiMonthNames[(currentShamsiMonth - 1).coerceIn(0, 11)]
    val selectedShamsiMonthName = shamsiMonthNames[(selectedShamsiMonth - 1).coerceIn(0, 11)]
    val todayGregorianMonthName = gregorianMonthNames[(currentGregorianMonth - 1).coerceIn(0, 11)]
    val todayDowName = persianWeekDays[todayDowIndex.coerceIn(0, 6)]

    // Real streak: consecutive days with at least one completed lesson.
    // (The old formula `(totalCompletedLessons / 3 + 1)` invented a streak
    // from a bare count and never looked at a date — it is gone.)
    val streakDays = StreakCalculator.currentStreak(uiState.completedStudyDays)

    // Total required tests for today
    val totalRequiredTestsToday = hourlyRoutineSlots.sumOf { it.requiredTestCount }

    CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl) {
        Card(
            modifier = modifier
                .fillMaxWidth()
                .testTag("smart_soldier_teacher_planner_card"),
            shape = RoundedCornerShape(18.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
            border = BorderStroke(1.dp, MaterialTheme.colorScheme.primary.copy(alpha = 0.4f)),
            elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(12.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                // ۱. هدر تقویم + استمرار و تست‌های امروز
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
                            color = MaterialTheme.colorScheme.primary,
                            shape = RoundedCornerShape(10.dp),
                            modifier = Modifier.size(38.dp)
                        ) {
                            Box(contentAlignment = Alignment.Center) {
                                Icon(
                                    imageVector = Icons.Default.CalendarMonth,
                                    contentDescription = null,
                                    tint = MaterialTheme.colorScheme.onPrimary,
                                    modifier = Modifier.size(22.dp)
                                )
                            }
                        }
                        Spacer(modifier = Modifier.width(8.dp))
                        Column {
                            Text(
                                text = BidiFormatter.formatMixedPersianText(
                                    "📅 امروز: $todayDowName $defaultTodayNumber $todayShamsiMonthName ۱۴۰۵"
                                ),
                                style = MaterialTheme.typography.titleSmall,
                                fontWeight = FontWeight.Black,
                                color = MaterialTheme.colorScheme.primary,
                                maxLines = 1
                            )
                            Text(
                                text = BidiFormatter.formatMixedPersianText(
                                    "روز انتخابی: ${activeDayPlan.dayOfWeekName} $selectedDayNumber $selectedShamsiMonthName • هدف تست: ${BidiFormatter.toPersianDigits(totalRequiredTestsToday.toString())} تست"
                                ),
                                style = MaterialTheme.typography.labelSmall,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.onSurfaceVariant,
                                maxLines = 1
                            )
                        }
                    }

                    if (selectedDayNumber != defaultTodayNumber || selectedShamsiMonth != currentShamsiMonth || missedDaysShift > 0) {
                        Surface(
                            onClick = {
                                selectedShamsiMonth = currentShamsiMonth
                                selectedDayNumber = defaultTodayNumber
                                missedDaysShift = 0
                            },
                            shape = RoundedCornerShape(8.dp),
                            color = MaterialTheme.colorScheme.primary
                        ) {
                            Text(
                                text = "امروز",
                                style = MaterialTheme.typography.labelSmall,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.onPrimary,
                                modifier = Modifier.padding(horizontal = 10.dp, vertical = 5.dp)
                            )
                        }
                    }
                }

                // ۱.۲ سامانه هوشمند و پیشرفته جبران عقب‌افتادگی (تسکین استرس و تراز خودکار برنامه)
                val overdueCount = overdueLessons.size
                Surface(
                    shape = RoundedCornerShape(14.dp),
                    color = if (overdueCount > 0) Color(0xFFEF4444).copy(alpha = 0.08f) else MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.25f),
                    border = BorderStroke(
                        1.dp,
                        if (overdueCount > 0) Color(0xFFEF4444).copy(alpha = 0.45f) else MaterialTheme.colorScheme.primary.copy(alpha = 0.35f)
                    ),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(
                        modifier = Modifier.padding(10.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(8.dp),
                                modifier = Modifier.weight(1f)
                            ) {
                                Icon(
                                    imageVector = if (overdueCount > 0) Icons.Default.Warning else Icons.Default.CheckCircle,
                                    contentDescription = null,
                                    tint = if (overdueCount > 0) Color(0xFFDC2626) else Color(0xFF16A34A),
                                    modifier = Modifier.size(22.dp)
                                )
                                Column {
                                    Text(
                                        text = if (overdueCount > 0)
                                            "سامانه جبران عقب‌افتادگی (${BidiFormatter.toPersianDigits(overdueCount.toString())} مبحث عقب‌مانده)"
                                        else
                                            "وضعیت مطالعه: کاملاً منطبق بر برنامه و بدون عقب‌افتادگی ✨",
                                        style = MaterialTheme.typography.titleSmall,
                                        fontWeight = FontWeight.Black,
                                        color = if (overdueCount > 0) Color(0xFFDC2626) else MaterialTheme.colorScheme.onSurface
                                    )
                                    Text(
                                        text = if (overdueCount > 0)
                                            "نگران نباشید! با ۴ راهکار هوشمند زیر بدون استرس و عذاب وجدان جبران کنید:"
                                        else
                                            "شما تمام دروس تا این مرحله را تکمیل کرده‌اید. برای شیفت برنامه می‌توانید از دکمه‌های زیر استفاده کنید.",
                                        style = MaterialTheme.typography.labelSmall,
                                        color = MaterialTheme.colorScheme.onSurfaceVariant
                                    )
                                }
                            }

                            IconButton(
                                onClick = { isCatchUpPanelExpanded = !isCatchUpPanelExpanded },
                                modifier = Modifier.size(32.dp)
                            ) {
                                Text(
                                    text = if (isCatchUpPanelExpanded) "▲" else "▼",
                                    style = MaterialTheme.typography.labelSmall,
                                    fontWeight = FontWeight.Bold,
                                    color = MaterialTheme.colorScheme.primary
                                )
                            }
                        }

                        if (isCatchUpPanelExpanded) {
                            // ۴ استراتژی هوشمند برای تراز مجدد برنامه
                            Text(
                                text = "انتخاب استراتژی جبران هوشمند:",
                                style = MaterialTheme.typography.labelSmall,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.onSurface
                            )

                            val strategies = listOf(
                                0 to "توزیع در طول هفته (+۱۵ دقیقه)",
                                1 to "جمعه نجات (تعطیلات طلایی)",
                                2 to "پارت شبانه جبرانی (۲۲:۰۰)",
                                3 to "ریست هوشمند و شروع نو"
                            )

                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.spacedBy(4.dp)
                            ) {
                                strategies.forEach { (stratId, stratLabel) ->
                                    val isSelected = catchUpStrategy == stratId
                                    Surface(
                                        onClick = {
                                            catchUpStrategy = stratId
                                            if (stratId == 3) {
                                                // ریست هوشمند از امروز
                                                selectedShamsiMonth = currentShamsiMonth
                                                selectedDayNumber = defaultTodayNumber
                                                customStartDay = defaultTodayNumber
                                                manualStepOffset = 0
                                                missedDaysShift = 0
                                            }
                                        },
                                        shape = RoundedCornerShape(8.dp),
                                        color = if (isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.surface,
                                        border = BorderStroke(
                                            0.8.dp,
                                            if (isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.45f)
                                        ),
                                        modifier = Modifier.weight(1f)
                                    ) {
                                        Text(
                                            text = stratLabel,
                                            style = MaterialTheme.typography.labelSmall,
                                            fontWeight = if (isSelected) FontWeight.Black else FontWeight.Medium,
                                            color = if (isSelected) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.onSurface,
                                            textAlign = TextAlign.Center,
                                            maxLines = 2,
                                            modifier = Modifier.padding(horizontal = 4.dp, vertical = 6.dp)
                                        )
                                    }
                                }
                            }

                            // دکمه‌های انتقال سریع بدون عذاب وجدان
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.spacedBy(6.dp)
                            ) {
                                Surface(
                                    onClick = { missedDaysShift += 1 },
                                    shape = RoundedCornerShape(8.dp),
                                    color = MaterialTheme.colorScheme.surfaceContainerHigh,
                                    border = BorderStroke(0.6.dp, MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.5f)),
                                    modifier = Modifier.weight(1f)
                                ) {
                                    Row(
                                        modifier = Modifier.padding(vertical = 6.dp, horizontal = 6.dp),
                                        verticalAlignment = Alignment.CenterVertically,
                                        horizontalArrangement = Arrangement.Center
                                    ) {
                                        Icon(
                                            imageVector = Icons.Default.FastForward,
                                            contentDescription = null,
                                            tint = Color(0xFFDC2626),
                                            modifier = Modifier.size(16.dp)
                                        )
                                        Spacer(modifier = Modifier.width(4.dp))
                                        Text(
                                            text = "انتقال به فردا (+۱ روز)",
                                            style = MaterialTheme.typography.labelSmall,
                                            fontWeight = FontWeight.Bold,
                                            color = MaterialTheme.colorScheme.onSurface,
                                            maxLines = 1
                                        )
                                    }
                                }

                                Surface(
                                    onClick = { missedDaysShift += 2 },
                                    shape = RoundedCornerShape(8.dp),
                                    color = MaterialTheme.colorScheme.surfaceContainerHigh,
                                    border = BorderStroke(0.6.dp, MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.5f)),
                                    modifier = Modifier.weight(1f)
                                ) {
                                    Row(
                                        modifier = Modifier.padding(vertical = 6.dp, horizontal = 6.dp),
                                        verticalAlignment = Alignment.CenterVertically,
                                        horizontalArrangement = Arrangement.Center
                                    ) {
                                        Icon(
                                            imageVector = Icons.Default.FastForward,
                                            contentDescription = null,
                                            tint = Color(0xFFD97706),
                                            modifier = Modifier.size(16.dp)
                                        )
                                        Spacer(modifier = Modifier.width(4.dp))
                                        Text(
                                            text = "شیفت ۲ روزه (+۲ روز)",
                                            style = MaterialTheme.typography.labelSmall,
                                            fontWeight = FontWeight.Bold,
                                            color = MaterialTheme.colorScheme.onSurface,
                                            maxLines = 1
                                        )
                                    }
                                }

                                Surface(
                                    onClick = {
                                        missedDaysShift = 0
                                        manualStepOffset = 0
                                    },
                                    shape = RoundedCornerShape(8.dp),
                                    color = MaterialTheme.colorScheme.surfaceContainerHigh,
                                    border = BorderStroke(0.6.dp, MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.5f)),
                                    modifier = Modifier.weight(1f)
                                ) {
                                    Row(
                                        modifier = Modifier.padding(vertical = 6.dp, horizontal = 6.dp),
                                        verticalAlignment = Alignment.CenterVertically,
                                        horizontalArrangement = Arrangement.Center
                                    ) {
                                        Icon(
                                            imageVector = Icons.Default.Refresh,
                                            contentDescription = null,
                                            tint = MaterialTheme.colorScheme.primary,
                                            modifier = Modifier.size(16.dp)
                                        )
                                        Spacer(modifier = Modifier.width(4.dp))
                                        Text(
                                            text = "تراز صفر تقویم",
                                            style = MaterialTheme.typography.labelSmall,
                                            fontWeight = FontWeight.Bold,
                                            color = MaterialTheme.colorScheme.onSurface,
                                            maxLines = 1
                                        )
                                    }
                                }
                            }

                            // لیست دروس عقب‌افتاده با امکان مطالعه فوری
                            if (overdueLessons.isNotEmpty()) {
                                HorizontalDivider(color = MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.35f))
                                Text(
                                    text = "مباحث اولویت‌دار جهت جبران سریع:",
                                    style = MaterialTheme.typography.labelSmall,
                                    fontWeight = FontWeight.Bold,
                                    color = Color(0xFFDC2626)
                                )
                                Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                                    overdueLessons.take(3).forEach { odLesson ->
                                        Surface(
                                            shape = RoundedCornerShape(8.dp),
                                            color = MaterialTheme.colorScheme.surface,
                                            border = BorderStroke(0.5.dp, MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.5f)),
                                            modifier = Modifier.fillMaxWidth()
                                        ) {
                                            Row(
                                                modifier = Modifier
                                                    .fillMaxWidth()
                                                    .padding(horizontal = 8.dp, vertical = 6.dp),
                                                horizontalArrangement = Arrangement.SpaceBetween,
                                                verticalAlignment = Alignment.CenterVertically
                                            ) {
                                                Column(modifier = Modifier.weight(1f)) {
                                                    Text(
                                                        text = odLesson.title,
                                                        style = MaterialTheme.typography.labelSmall,
                                                        fontWeight = FontWeight.Bold,
                                                        color = MaterialTheme.colorScheme.onSurface,
                                                        maxLines = 1
                                                    )
                                                    Text(
                                                        text = odLesson.officialTextbookReference(),
                                                        style = MaterialTheme.typography.labelSmall,
                                                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                                                        maxLines = 1
                                                    )
                                                }
                                                Surface(
                                                    onClick = { onOpenLesson(odLesson.id) },
                                                    shape = RoundedCornerShape(6.dp),
                                                    color = Color(0xFFDC2626)
                                                ) {
                                                    Text(
                                                        text = "مطالعه فوری",
                                                        style = MaterialTheme.typography.labelSmall,
                                                        fontWeight = FontWeight.Black,
                                                        color = Color.White,
                                                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                                                    )
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }

                // ۱.۵ نوار استمرار طلایی (Streak) و پومودورو
                Surface(
                    shape = RoundedCornerShape(10.dp),
                    color = MaterialTheme.colorScheme.tertiaryContainer.copy(alpha = 0.35f),
                    border = BorderStroke(0.6.dp, MaterialTheme.colorScheme.tertiary.copy(alpha = 0.4f)),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 10.dp, vertical = 7.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(6.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.LocalFireDepartment,
                                contentDescription = null,
                                tint = Color(0xFFEA580C),
                                modifier = Modifier.size(20.dp)
                            )
                            Text(
                                text = BidiFormatter.formatMixedPersianText("استمرار طلایی: $streakDays روز متوالی"),
                                style = MaterialTheme.typography.labelMedium,
                                fontWeight = FontWeight.Black,
                                color = MaterialTheme.colorScheme.tertiary,
                                maxLines = 1
                            )
                        }
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(4.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.EmojiEvents,
                                contentDescription = null,
                                tint = Color(0xFFD97706),
                                modifier = Modifier.size(18.dp)
                            )
                            Text(
                                text = BidiFormatter.formatMixedPersianText("هدف: رتبه برتر فرهنگیان"),
                                style = MaterialTheme.typography.labelSmall,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.onSurface,
                                maxLines = 1
                            )
                        }
                    }
                }

                // ۱.۸ تایمر پومودورو متمرکز داخلی
                Surface(
                    shape = RoundedCornerShape(12.dp),
                    color = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.25f),
                    border = BorderStroke(0.8.dp, MaterialTheme.colorScheme.primary.copy(alpha = 0.35f)),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 12.dp, vertical = 8.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.Timer,
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.primary,
                                modifier = Modifier.size(22.dp)
                            )
                            Column {
                                Text(
                                    text = if (pomodoro.isBreak) "☕ استراحت پومودورو" else "⚡ پومودورو تمرکز (۲۵ دقیقه)",
                                    style = MaterialTheme.typography.labelMedium,
                                    fontWeight = FontWeight.Black,
                                    color = MaterialTheme.colorScheme.primary,
                                    maxLines = 1
                                )
                                val mins = pomodoro.secondsLeft / 60
                                val secs = pomodoro.secondsLeft % 60
                                Text(
                                    text = BidiFormatter.toPersianDigits(String.format(java.util.Locale.US, "%02d:%02d", mins, secs)),
                                    style = MaterialTheme.typography.titleMedium,
                                    fontWeight = FontWeight.Black,
                                    color = MaterialTheme.colorScheme.onSurface
                                )
                            }
                        }

                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(6.dp)
                        ) {
                            Surface(
                                onClick = { sharedPomodoroTimer.toggle() },
                                shape = RoundedCornerShape(8.dp),
                                color = MaterialTheme.colorScheme.primary
                            ) {
                                Icon(
                                    imageVector = if (pomodoro.isRunning) Icons.Default.Pause else Icons.Default.PlayArrow,
                                    contentDescription = "شروع / توقف",
                                    tint = MaterialTheme.colorScheme.onPrimary,
                                    modifier = Modifier.padding(6.dp).size(20.dp)
                                )
                            }
                            Surface(
                                onClick = { sharedPomodoroTimer.reset() },
                                shape = RoundedCornerShape(8.dp),
                                color = MaterialTheme.colorScheme.surface,
                                border = BorderStroke(0.5.dp, MaterialTheme.colorScheme.primary)
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Refresh,
                                    contentDescription = "بازنشانی",
                                    tint = MaterialTheme.colorScheme.primary,
                                    modifier = Modifier.padding(6.dp).size(20.dp)
                                )
                            }
                        }
                    }
                }

                // ۳. تقویم شمسی ۷ ستونی دقیق
                Surface(
                    shape = RoundedCornerShape(12.dp),
                    color = MaterialTheme.colorScheme.surfaceContainerLow,
                    border = BorderStroke(0.7.dp, MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.5f)),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(
                        modifier = Modifier.padding(8.dp),
                        verticalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        // انتخاب سال تحصیلی و کنکور (۱۴۰۴ تا ۱۴۰۷ — ۱۴۰۷ دومین شانس کنکور است)
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(6.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "سال تقویم:",
                                style = MaterialTheme.typography.labelSmall,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                            listOf(1404, 1405, 1406, 1407).forEach { yr ->
                                val isYrSelected = selectedShamsiYear == yr
                                Surface(
                                    onClick = {
                                        selectedShamsiYear = yr
                                        val maxD = JalaliCalendarUtils.daysInJalaliMonth(yr, selectedShamsiMonth)
                                        if (selectedDayNumber > maxD) selectedDayNumber = maxD
                                    },
                                    shape = RoundedCornerShape(7.dp),
                                    color = if (isYrSelected) MaterialTheme.colorScheme.primaryContainer else MaterialTheme.colorScheme.surface,
                                    border = BorderStroke(
                                        0.7.dp,
                                        if (isYrSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.45f)
                                    ),
                                    modifier = Modifier.testTag("calendar_year_$yr")
                                ) {
                                    Text(
                                        text = BidiFormatter.applyPreferredDigits("سال $yr"),
                                        style = MaterialTheme.typography.labelSmall,
                                        fontWeight = if (isYrSelected) FontWeight.Bold else FontWeight.Medium,
                                        color = if (isYrSelected) MaterialTheme.colorScheme.onPrimaryContainer else MaterialTheme.colorScheme.onSurface,
                                        modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp)
                                    )
                                }
                            }
                        }

                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .horizontalScroll(rememberScrollState()),
                            horizontalArrangement = Arrangement.spacedBy(5.dp)
                        ) {
                            shamsiMonthNames.forEachIndexed { mIdx, mName ->
                                val monthNum = mIdx + 1
                                val isSelectedMonth = selectedShamsiMonth == monthNum
                                Surface(
                                    onClick = {
                                        selectedShamsiMonth = monthNum
                                        val maxDays = JalaliCalendarUtils.daysInJalaliMonth(selectedShamsiYear, monthNum)
                                        if (selectedDayNumber > maxDays) {
                                            selectedDayNumber = 1
                                        }
                                    },
                                    shape = RoundedCornerShape(7.dp),
                                    color = if (isSelectedMonth) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.surface,
                                    border = BorderStroke(
                                        0.6.dp,
                                        if (isSelectedMonth) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.45f)
                                    )
                                ) {
                                    Text(
                                        text = mName,
                                        style = MaterialTheme.typography.labelSmall,
                                        fontWeight = if (isSelectedMonth) FontWeight.Black else FontWeight.Medium,
                                        color = if (isSelectedMonth) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.onSurface,
                                        maxLines = 1,
                                        softWrap = false,
                                        modifier = Modifier.padding(horizontal = 9.dp, vertical = 4.dp)
                                    )
                                }
                            }
                        }

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(3.dp)
                        ) {
                            persianWeekDays.forEachIndexed { idx, dName ->
                                Text(
                                    text = dName,
                                    style = MaterialTheme.typography.labelSmall,
                                    fontWeight = FontWeight.Bold,
                                    color = if (idx >= 5) Color(0xFFD97706) else MaterialTheme.colorScheme.onSurfaceVariant,
                                    textAlign = TextAlign.Center,
                                    maxLines = 1,
                                    modifier = Modifier.weight(1f)
                                )
                            }
                        }

                        val paddedCells = remember(thirtyDayPlans, firstDayOfMonthDowIndex) {
                            List<CalendarDayPlan?>(firstDayOfMonthDowIndex) { null } + thirtyDayPlans
                        }
                        val rows = paddedCells.chunked(7)
                        Column(verticalArrangement = Arrangement.spacedBy(3.dp)) {
                            rows.forEach { weekRow ->
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.spacedBy(3.dp)
                                ) {
                                    for (colIdx in 0 until 7) {
                                        val plan = weekRow.getOrNull(colIdx)
                                        if (plan == null) {
                                            Spacer(modifier = Modifier.weight(1f).height(38.dp))
                                        } else {
                                            val isSelected = plan.dayNumber == selectedDayNumber
                                            val isToday = selectedShamsiMonth == currentShamsiMonth && plan.dayNumber == defaultTodayNumber
                                            val doneInDay = plan.prescribedLessons.count {
                                                uiState.lessonProgressMap[it.id]?.isCompleted == true
                                            }
                                            val allDoneInDay = doneInDay == plan.prescribedLessons.size && plan.prescribedLessons.isNotEmpty()

                                            val bg = when {
                                                isSelected -> MaterialTheme.colorScheme.primary
                                                allDoneInDay -> Color(0xFF16A34A).copy(alpha = 0.16f)
                                                plan.isOfficialHoliday -> Color(0xFFE11D48).copy(alpha = 0.14f)
                                                plan.isWeekendOff -> Color(0xFFD97706).copy(alpha = 0.12f)
                                                else -> MaterialTheme.colorScheme.surface
                                            }
                                            val textCol = when {
                                                isSelected -> MaterialTheme.colorScheme.onPrimary
                                                allDoneInDay -> MaterialTheme.colorScheme.tertiary
                                                plan.isOfficialHoliday -> Color(0xFFE11D48)
                                                plan.isWeekendOff -> Color(0xFFD97706)
                                                else -> MaterialTheme.colorScheme.onSurface
                                            }

                                            Surface(
                                                onClick = { selectedDayNumber = plan.dayNumber },
                                                shape = RoundedCornerShape(9.dp),
                                                color = bg,
                                                border = BorderStroke(
                                                    width = if (isToday || isSelected || plan.isOfficialHoliday) 1.3.dp else 0.5.dp,
                                                    color = when {
                                                        isSelected -> MaterialTheme.colorScheme.primary
                                                        isToday -> Color(0xFFE11D48)
                                                        plan.isOfficialHoliday -> Color(0xFFE11D48).copy(alpha = 0.55f)
                                                        else -> MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.45f)
                                                    }
                                                ),
                                                modifier = Modifier
                                                    .weight(1f)
                                                    .height(40.dp)
                                                    .testTag("calendar_day_${plan.dayNumber}")
                                            ) {
                                                Column(
                                                    horizontalAlignment = Alignment.CenterHorizontally,
                                                    verticalArrangement = Arrangement.Center
                                                ) {
                                                    Text(
                                                        text = BidiFormatter.toPersianDigits(plan.dayNumber.toString()),
                                                        style = MaterialTheme.typography.labelMedium,
                                                        fontWeight = FontWeight.Black,
                                                        color = textCol
                                                    )
                                                    Text(
                                                        text = when {
                                                            allDoneInDay -> "✓"
                                                            isToday -> "امروز"
                                                            plan.isOfficialHoliday -> "تعطیل"
                                                            else -> "گام ${BidiFormatter.toPersianDigits(plan.stepIndexFromZero.toString())}"
                                                        },
                                                        style = MaterialTheme.typography.labelSmall,
                                                        color = textCol.copy(alpha = 0.9f),
                                                        maxLines = 1,
                                                        softWrap = false
                                                    )
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }

                        // فهرست تعطیلات رسمی این ماه برای برنامه‌ریزی جهشی و شروع صبحگاهی
                        val monthHolidays = remember(selectedShamsiYear, selectedShamsiMonth) {
                            ShamsiHolidayCatalog.getMonthHolidays(selectedShamsiYear, selectedShamsiMonth)
                        }
                        if (monthHolidays.isNotEmpty()) {
                            HorizontalDivider(thickness = 0.5.dp, color = MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.4f))
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .horizontalScroll(rememberScrollState()),
                                horizontalArrangement = Arrangement.spacedBy(6.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    text = "🔴 تعطیلات رسمی $selectedShamsiMonthName:",
                                    style = MaterialTheme.typography.labelSmall,
                                    fontWeight = FontWeight.Black,
                                    color = Color(0xFFE11D48),
                                    maxLines = 1,
                                    softWrap = false
                                )
                                monthHolidays.forEach { (hDay, hTitle) ->
                                    val isChosenHol = selectedDayNumber == hDay
                                    Surface(
                                        onClick = { selectedDayNumber = hDay },
                                        shape = RoundedCornerShape(50),
                                        color = if (isChosenHol) Color(0xFFE11D48) else Color(0xFFE11D48).copy(alpha = 0.1f),
                                        border = BorderStroke(0.6.dp, Color(0xFFE11D48).copy(alpha = 0.45f))
                                    ) {
                                        Text(
                                            text = "${BidiFormatter.toPersianDigits(hDay.toString())} $selectedShamsiMonthName: ${hTitle.substringBefore(" (")}",
                                            style = MaterialTheme.typography.labelSmall,
                                            fontWeight = FontWeight.Bold,
                                            color = if (isChosenHol) Color.White else Color(0xFFE11D48),
                                            maxLines = 1,
                                            softWrap = false,
                                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp)
                                        )
                                    }
                                }
                            }
                        }
                    }
                }

                // ۴. تنظیم کاملاً دلخواه ساعت و دقیقه شروع مطالعه + شدت برنامه روزانه
                val formattedStartClock = "${BidiFormatter.toPersianDigits(String.format(java.util.Locale.US, "%02d", customStudyStartHour))}:${BidiFormatter.toPersianDigits(String.format(java.util.Locale.US, "%02d", customStudyStartMinute))}"
                Surface(
                    shape = RoundedCornerShape(14.dp),
                    color = if (isWeekendSchedule) Color(0xFFE11D48).copy(alpha = 0.08f) else MaterialTheme.colorScheme.surfaceContainerLow,
                    border = BorderStroke(
                        0.8.dp,
                        if (isWeekendSchedule) Color(0xFFE11D48).copy(alpha = 0.45f) else MaterialTheme.colorScheme.primary.copy(alpha = 0.35f)
                    ),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(
                        modifier = Modifier.padding(10.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column(modifier = Modifier.weight(1f)) {
                                Text(
                                    text = "⏰ ساعت شروع مطالعه دلخواه من: $formattedStartClock",
                                    style = MaterialTheme.typography.labelMedium,
                                    fontWeight = FontWeight.Black,
                                    color = if (isWeekendSchedule) Color(0xFFE11D48) else MaterialTheme.colorScheme.primary,
                                    maxLines = 1
                                )
                                Text(
                                    text = "ساعت و دقیقه شروع را خودت تنظیم کن تا تمام باکس‌ها خودکار از همان لحظه چیده شوند:",
                                    style = MaterialTheme.typography.labelSmall,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                                    maxLines = 1
                                )
                            }
                            Spacer(modifier = Modifier.width(6.dp))
                            Surface(
                                onClick = {
                                    scheduleModeOverride = if (isWeekendSchedule) 0 else 1
                                },
                                shape = RoundedCornerShape(50),
                                color = if (isWeekendSchedule) Color(0xFFE11D48) else MaterialTheme.colorScheme.primaryContainer
                            ) {
                                Text(
                                    text = if (isWeekendSchedule) "حالت تعطیل" else "حالت عادی",
                                    style = MaterialTheme.typography.labelSmall,
                                    fontWeight = FontWeight.Bold,
                                    color = if (isWeekendSchedule) Color.White else MaterialTheme.colorScheme.onPrimaryContainer,
                                    maxLines = 1,
                                    softWrap = false,
                                    modifier = Modifier.padding(horizontal = 10.dp, vertical = 5.dp)
                                )
                            }
                        }

                        // کنترل دستی ساعت و دقیقه (با دکمه‌های - و + و انتخابگر اسکرولی کامل از ۴ صبح تا ۲۲ شب)
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(6.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            // کنترل ساعت
                            Surface(
                                shape = RoundedCornerShape(10.dp),
                                color = MaterialTheme.colorScheme.surface,
                                border = BorderStroke(0.7.dp, MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.5f)),
                                modifier = Modifier.weight(1f)
                            ) {
                                Row(
                                    modifier = Modifier.padding(horizontal = 6.dp, vertical = 4.dp),
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.SpaceBetween
                                ) {
                                    Surface(
                                        onClick = { customStudyStartHour = if (customStudyStartHour > 4) customStudyStartHour - 1 else 22 },
                                        shape = RoundedCornerShape(6.dp),
                                        color = MaterialTheme.colorScheme.surfaceContainerHigh
                                    ) {
                                        Text(
                                            text = "−",
                                            style = MaterialTheme.typography.titleSmall,
                                            fontWeight = FontWeight.Black,
                                            modifier = Modifier.padding(horizontal = 10.dp, vertical = 2.dp)
                                        )
                                    }
                                    Text(
                                        text = "ساعت ${BidiFormatter.toPersianDigits(String.format(java.util.Locale.US, "%02d", customStudyStartHour))}",
                                        style = MaterialTheme.typography.labelMedium,
                                        fontWeight = FontWeight.Black,
                                        color = MaterialTheme.colorScheme.primary,
                                        maxLines = 1
                                    )
                                    Surface(
                                        onClick = { customStudyStartHour = if (customStudyStartHour < 22) customStudyStartHour + 1 else 4 },
                                        shape = RoundedCornerShape(6.dp),
                                        color = MaterialTheme.colorScheme.surfaceContainerHigh
                                    ) {
                                        Text(
                                            text = "+",
                                            style = MaterialTheme.typography.titleSmall,
                                            fontWeight = FontWeight.Black,
                                            modifier = Modifier.padding(horizontal = 10.dp, vertical = 2.dp)
                                        )
                                    }
                                }
                            }

                            // کنترل دقیقه (۰۰، ۱۵، ۳۰، ۴۵)
                            Row(
                                horizontalArrangement = Arrangement.spacedBy(4.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                listOf(0, 15, 30, 45).forEach { minVal ->
                                    val isMinSelected = customStudyStartMinute == minVal
                                    Surface(
                                        onClick = { customStudyStartMinute = minVal },
                                        shape = RoundedCornerShape(8.dp),
                                        color = if (isMinSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.surface,
                                        border = BorderStroke(0.6.dp, if (isMinSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.5f))
                                    ) {
                                        Text(
                                            text = ":${BidiFormatter.toPersianDigits(String.format(java.util.Locale.US, "%02d", minVal))}",
                                            style = MaterialTheme.typography.labelSmall,
                                            fontWeight = if (isMinSelected) FontWeight.Black else FontWeight.SemiBold,
                                            color = if (isMinSelected) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.onSurface,
                                            maxLines = 1,
                                            modifier = Modifier.padding(horizontal = 7.dp, vertical = 6.dp)
                                        )
                                    }
                                }
                            }
                        }

                        // پریست‌های سریع ساعت شروع مطالعه (مطابق نیاز دانش‌آموزان و فارغ‌التحصیلان)
                        Text(
                            text = "انتخاب سریع زمان‌بندی روزانه:",
                            style = MaterialTheme.typography.labelSmall,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onSurface
                        )

                        val quickTimePresets = listOf(
                            Triple("🌅 سحرخیز (۰۶:۰۰)", 6, 0),
                            Triple("☀️ صبحگاهی (۰۸:۰۰)", 8, 0),
                            Triple("🏫 بعد از مدرسه (۱۵:۳۰)", 15, 30),
                            Triple("🌆 عصرگاهی (۱۷:۰۰)", 17, 0),
                            Triple("🌙 شبانه (۱۹:۳۰)", 19, 30)
                        )

                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .horizontalScroll(rememberScrollState()),
                            horizontalArrangement = Arrangement.spacedBy(5.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            quickTimePresets.forEach { (label, h, m) ->
                                val isSelectedPreset = customStudyStartHour == h && customStudyStartMinute == m
                                Surface(
                                    onClick = {
                                        customStudyStartHour = h
                                        customStudyStartMinute = m
                                    },
                                    shape = RoundedCornerShape(50),
                                    color = if (isSelectedPreset) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.surface,
                                    border = BorderStroke(
                                        0.7.dp,
                                        if (isSelectedPreset) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.5f)
                                    )
                                ) {
                                    Text(
                                        text = label,
                                        style = MaterialTheme.typography.labelSmall,
                                        fontWeight = if (isSelectedPreset) FontWeight.Black else FontWeight.SemiBold,
                                        color = if (isSelectedPreset) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.onSurface,
                                        maxLines = 1,
                                        softWrap = false,
                                        modifier = Modifier.padding(horizontal = 9.dp, vertical = 5.dp)
                                    )
                                }
                            }
                        }

                        // نوار اسکرول افقی انتخاب سریع تک‌تک ساعات روز (از ۵ صبح تا ۲۲ شب)
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .horizontalScroll(rememberScrollState()),
                            horizontalArrangement = Arrangement.spacedBy(4.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            (5..22).forEach { hourVal ->
                                val isHourSelected = customStudyStartHour == hourVal
                                val periodLabel = when {
                                    hourVal < 12 -> "صبح"
                                    hourVal < 16 -> "ظهر"
                                    hourVal < 19 -> "عصر"
                                    else -> "شب"
                                }
                                Surface(
                                    onClick = { customStudyStartHour = hourVal },
                                    shape = RoundedCornerShape(8.dp),
                                    color = if (isHourSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.surface,
                                    border = BorderStroke(
                                        0.6.dp,
                                        if (isHourSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.45f)
                                    )
                                ) {
                                    Text(
                                        text = "${BidiFormatter.toPersianDigits(String.format(java.util.Locale.US, "%02d", hourVal))}:۰۰ $periodLabel",
                                        style = MaterialTheme.typography.labelSmall,
                                        fontWeight = if (isHourSelected) FontWeight.Black else FontWeight.Normal,
                                        color = if (isHourSelected) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.onSurface,
                                        maxLines = 1,
                                        softWrap = false,
                                        modifier = Modifier.padding(horizontal = 7.dp, vertical = 4.dp)
                                    )
                                }
                            }
                        }

                        // تنظیم مدت استراحت بین هر پارت (۱۰، ۱۵، ۲۰ دقیقه)
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "مدت استراحت بین پارت‌ها:",
                                style = MaterialTheme.typography.labelSmall,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.onSurface
                            )
                            Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                                listOf(10, 15, 20).forEach { breakM ->
                                    val isBreakSelected = customBreakDuration == breakM
                                    Surface(
                                        onClick = { customBreakDuration = breakM },
                                        shape = RoundedCornerShape(8.dp),
                                        color = if (isBreakSelected) MaterialTheme.colorScheme.tertiary else MaterialTheme.colorScheme.surface,
                                        border = BorderStroke(0.6.dp, if (isBreakSelected) MaterialTheme.colorScheme.tertiary else MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.5f))
                                    ) {
                                        Text(
                                            text = "${BidiFormatter.toPersianDigits(breakM.toString())} دقیقه",
                                            style = MaterialTheme.typography.labelSmall,
                                            fontWeight = if (isBreakSelected) FontWeight.Black else FontWeight.Normal,
                                            color = if (isBreakSelected) MaterialTheme.colorScheme.onTertiary else MaterialTheme.colorScheme.onSurface,
                                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                                        )
                                    }
                                }
                            }
                        }

                        // انتخاب شدت و حجم برنامه روزانه (هوشمند)
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(6.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            listOf(
                                0 to "🌱 سبک و فشرده (۵ ساعت)",
                                1 to "⚡ استاندارد کنکوری (۷ ساعت)",
                                2 to "🔥 فوق‌سنگین رتبه‌برتر (۹ ساعت)"
                            ).forEach { (lvl, lbl) ->
                                val isLvlSelected = studyIntensityLevel == lvl
                                Surface(
                                    onClick = { studyIntensityLevel = lvl },
                                    shape = RoundedCornerShape(8.dp),
                                    color = if (isLvlSelected) MaterialTheme.colorScheme.secondaryContainer else MaterialTheme.colorScheme.surface,
                                    border = BorderStroke(0.7.dp, if (isLvlSelected) MaterialTheme.colorScheme.secondary else MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.45f)),
                                    modifier = Modifier.weight(1f)
                                ) {
                                    Text(
                                        text = lbl,
                                        style = MaterialTheme.typography.labelSmall,
                                        fontWeight = if (isLvlSelected) FontWeight.Black else FontWeight.SemiBold,
                                        color = if (isLvlSelected) MaterialTheme.colorScheme.onSecondaryContainer else MaterialTheme.colorScheme.onSurfaceVariant,
                                        textAlign = TextAlign.Center,
                                        maxLines = 1,
                                        softWrap = false,
                                        modifier = Modifier.padding(vertical = 5.dp, horizontal = 3.dp)
                                    )
                                }
                            }
                        }

                        // خلاصه مجموع ساعات مطالعه روزانه
                        val totalStudyMins = hourlyRoutineSlots.filter { it.isStudySlot }.sumOf { slot ->
                            when {
                                slot.badge.contains("۱۲۰") -> 120
                                slot.badge.contains("۱۰۵") -> 105
                                slot.badge.contains("۹۰") -> 90
                                slot.badge.contains("۷۵") -> 75
                                slot.badge.contains("۶۰") -> 60
                                slot.badge.contains("۴۵") -> 45
                                else -> 75
                            }
                        }
                        val totalStudyH = totalStudyMins / 60
                        val totalStudyRemM = totalStudyMins % 60
                        val totalStudyText = if (totalStudyRemM > 0)
                            "${BidiFormatter.toPersianDigits(totalStudyH.toString())} ساعت و ${BidiFormatter.toPersianDigits(totalStudyRemM.toString())} دقیقه"
                        else
                            "${BidiFormatter.toPersianDigits(totalStudyH.toString())} ساعت"

                        Surface(
                            shape = RoundedCornerShape(8.dp),
                            color = MaterialTheme.colorScheme.surfaceContainerHigh.copy(alpha = 0.5f),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text(
                                text = "⏱️ مجموع ساعات مطالعه مفید امروز: $totalStudyText (${BidiFormatter.toPersianDigits(totalRequiredTestsToday.toString())} تست هدف)",
                                style = MaterialTheme.typography.labelSmall,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.primary,
                                textAlign = TextAlign.Center,
                                modifier = Modifier.padding(vertical = 5.dp, horizontal = 8.dp)
                            )
                        }
                    }
                }

                // ۵. باکس‌های زمانی و تعداد تست الزامی + دکمه «بلد شدم» و «بلد نشدم»
                val doneTodayCount = activeDayPlan.prescribedLessons.count {
                    uiState.lessonProgressMap[it.id]?.isCompleted == true
                }
                val totalTodayCount = activeDayPlan.prescribedLessons.size
                val todayProgress = if (totalTodayCount > 0) doneTodayCount.toFloat() / totalTodayCount else 0f

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = BidiFormatter.formatMixedPersianText(
                            "📌 برنامه گام ${activeDayPlan.stepIndexFromZero} (${activeDayPlan.dayOfWeekName} $selectedDayNumber $selectedShamsiMonthName):"
                        ),
                        style = MaterialTheme.typography.labelLarge,
                        fontWeight = FontWeight.Black,
                        color = MaterialTheme.colorScheme.onSurface,
                        modifier = Modifier.weight(1f)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = BidiFormatter.formatMixedPersianText("$doneTodayCount از $totalTodayCount درس تکمیل"),
                        style = MaterialTheme.typography.labelSmall,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary,
                        maxLines = 1,
                        softWrap = false
                    )
                }

                LinearProgressIndicator(
                    progress = { todayProgress.coerceIn(0f, 1f) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(6.dp)
                        .clip(RoundedCornerShape(3.dp)),
                    color = MaterialTheme.colorScheme.primary,
                    trackColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.14f)
                )

                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    hourlyRoutineSlots.forEach { slot ->
                        val lesson = slot.linkedLesson
                        val isDone = lesson != null && uiState.lessonProgressMap[lesson.id]?.isCompleted == true
                        val isBookmarked = lesson != null && uiState.lessonProgressMap[lesson.id]?.isBookmarked == true
                        val subMeta = lesson?.let { SubjectCatalog.getSubjectById(it.normalizedSubjectId()) }
                        val isMastered = lesson != null && lessonMasteryStatus[lesson.id] == true

                        Surface(
                            shape = RoundedCornerShape(12.dp),
                            color = slot.accentColor.copy(alpha = if (slot.isStudySlot) 0.07f else 0.04f),
                            border = BorderStroke(
                                width = if (slot.isStudySlot) 0.9.dp else 0.5.dp,
                                color = slot.accentColor.copy(alpha = 0.35f)
                            ),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Column(
                                modifier = Modifier.padding(10.dp),
                                verticalArrangement = Arrangement.spacedBy(6.dp)
                            ) {
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Surface(
                                        color = slot.accentColor,
                                        shape = RoundedCornerShape(6.dp)
                                    ) {
                                        Text(
                                            text = slot.timeRange,
                                            style = MaterialTheme.typography.labelSmall,
                                            fontWeight = FontWeight.Black,
                                            color = Color.White,
                                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp)
                                        )
                                    }
                                    Text(
                                        text = slot.title,
                                        style = MaterialTheme.typography.labelMedium,
                                        fontWeight = FontWeight.Bold,
                                        color = MaterialTheme.colorScheme.onSurface,
                                        modifier = Modifier
                                            .weight(1f)
                                            .padding(horizontal = 8.dp),
                                        maxLines = 1
                                    )
                                    if (slot.requiredTestCount > 0) {
                                        Surface(
                                            color = MaterialTheme.colorScheme.secondaryContainer,
                                            shape = RoundedCornerShape(6.dp)
                                        ) {
                                            Text(
                                                text = "🎯 ${BidiFormatter.toPersianDigits(slot.requiredTestCount.toString())} تست",
                                                style = MaterialTheme.typography.labelSmall,
                                                fontWeight = FontWeight.Bold,
                                                color = MaterialTheme.colorScheme.onSecondaryContainer,
                                                modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                                            )
                                        }
                                    }
                                }

                                if (lesson != null && subMeta != null) {
                                    HorizontalDivider(
                                        thickness = 0.5.dp,
                                        color = slot.accentColor.copy(alpha = 0.2f)
                                    )
                                    Row(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .clip(RoundedCornerShape(8.dp))
                                            .clickable { onOpenLesson(lesson.id) }
                                            .padding(vertical = 2.dp),
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        IconButton(
                                            onClick = { onToggleLessonCompleted(lesson, !isDone) },
                                            modifier = Modifier.size(30.dp)
                                        ) {
                                            Icon(
                                                imageVector = if (isDone) Icons.Default.CheckCircle else Icons.Default.RadioButtonUnchecked,
                                                contentDescription = "تکمیل درس",
                                                tint = if (isDone) Color(0xFF16A34A) else slot.accentColor,
                                                modifier = Modifier.size(20.dp)
                                            )
                                        }
                                        Column(
                                            modifier = Modifier
                                                .weight(1f)
                                                .padding(horizontal = 4.dp)
                                        ) {
                                            BiDiText(
                                                text = lesson.title,
                                                style = MaterialTheme.typography.bodySmall,
                                                fontWeight = FontWeight.Bold,
                                                color = MaterialTheme.colorScheme.onSurface,
                                                maxLines = 1
                                            )
                                            BiDiText(
                                                text = "${subMeta.shortTitle} • هدف: ${BidiFormatter.toPersianDigits(slot.requiredTestCount.toString())} تست کنکوری",
                                                style = MaterialTheme.typography.labelSmall,
                                                color = slot.accentColor,
                                                maxLines = 1
                                            )
                                        }
                                        IconButton(
                                            onClick = { onToggleLessonBookmarked(lesson, !isBookmarked) },
                                            modifier = Modifier.size(30.dp)
                                        ) {
                                            Icon(
                                                imageVector = if (isBookmarked) Icons.Default.Bookmark else Icons.Default.BookmarkBorder,
                                                contentDescription = "نشان کردن",
                                                tint = if (isBookmarked) Color(0xFFD97706) else MaterialTheme.colorScheme.onSurfaceVariant,
                                                modifier = Modifier.size(18.dp)
                                            )
                                        }
                                        Surface(
                                            onClick = { onOpenLesson(lesson.id) },
                                            shape = RoundedCornerShape(7.dp),
                                            color = slot.accentColor
                                        ) {
                                            Text(
                                                text = "مطالعه ◀",
                                                style = MaterialTheme.typography.labelSmall,
                                                fontWeight = FontWeight.Bold,
                                                color = Color.White,
                                                modifier = Modifier.padding(horizontal = 9.dp, vertical = 5.dp)
                                            )
                                        }
                                    }

                                    // دکمه‌های «بلد شدم» و «بلد نشدم (انتقال به فردا)»
                                    Row(
                                        modifier = Modifier.fillMaxWidth(),
                                        horizontalArrangement = Arrangement.spacedBy(6.dp),
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Surface(
                                            onClick = { lessonMasteryStatus[lesson.id] = true },
                                            shape = RoundedCornerShape(6.dp),
                                            color = if (isMastered) Color(0xFF16A34A) else Color(0xFF16A34A).copy(alpha = 0.15f),
                                            modifier = Modifier.weight(1f)
                                        ) {
                                            Text(
                                                text = if (isMastered) "🟢 بلد شدم (تسلط کامل)" else "✓ بلد شدم",
                                                style = MaterialTheme.typography.labelSmall,
                                                fontWeight = FontWeight.Bold,
                                                color = if (isMastered) Color.White else Color(0xFF16A34A),
                                                textAlign = TextAlign.Center,
                                                modifier = Modifier.padding(vertical = 5.dp)
                                            )
                                        }
                                        Surface(
                                            onClick = {
                                                lessonMasteryStatus[lesson.id] = false
                                                missedDaysShift += 1 // انتقال خودکار به برنامه فردا
                                            },
                                            shape = RoundedCornerShape(6.dp),
                                            color = Color(0xFFDC2626).copy(alpha = 0.15f),
                                            modifier = Modifier.weight(1f)
                                        ) {
                                            Text(
                                                text = "✗ بلد نشدم (انتقال به فردا)",
                                                style = MaterialTheme.typography.labelSmall,
                                                fontWeight = FontWeight.Bold,
                                                color = Color(0xFFDC2626),
                                                textAlign = TextAlign.Center,
                                                modifier = Modifier.padding(vertical = 5.dp)
                                            )
                                        }
                                    }
                                } else {
                                    Text(
                                        text = slot.subtitle,
                                        style = MaterialTheme.typography.labelSmall,
                                        color = MaterialTheme.colorScheme.onSurfaceVariant
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
