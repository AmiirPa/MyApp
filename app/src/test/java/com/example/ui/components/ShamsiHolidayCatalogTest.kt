package com.example.ui.components

import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Test

/**
 * تست جدول تعطیلات قمری [ShamsiHolidayCatalog].
 *
 * مبنای تاریخ‌ها: تقویم رسمی ایران آن‌گونه که time.ir منتشر می‌کند (بازیابی
 * ۱۷ آبان ۱۴۰۵)؛ برای ۱۴۰۴ و ۱۴۰۵ آینهٔ همان تقویم در bahesab.ir و برای ۱۴۰۶
 * جدول iranian_holidays 0.3.0 («هر دو سال ۱۴۰۵ و ۱۴۰۶ با تقویم منتشرشده در
 * time.ir روزبه‌روز مطابق‌اند») با مقابلهٔ multicalendar و nayrika و roozaneh.
 * جدول کامل با منبع هر تاریخ در lunar-holidays-1404-1407.md آمده است.
 *
 * توجه: این تست فقط جدول را می‌خواند. راستی‌آزمایی نمایش تعطیلات در رابط
 * کاربری برنامه‌ریز روی دستگاه واقعی، و اجرای کامل مجموعهٔ تست Gradle، خارج
 * از توان محیط فعلی است و بر عهدهٔ هماهنگ‌کننده است.
 */
class ShamsiHolidayCatalogTest {

    private fun title(year: Int, month: Int, day: Int): String? =
        ShamsiHolidayCatalog.getHolidayOccasion(year, month, day)

    private fun monthDays(year: Int, month: Int): List<Int> =
        ShamsiHolidayCatalog.getMonthHolidays(year, month).map { it.first }

    // ─── ۱۴۰۴ ───

    @Test
    fun fitr1404_is11And12Farvardin_notOld2Ordibehesht() {
        // خطای ~۳ هفته‌ای جدول قدیمی: عید فطر روی ۲–۳ اردیبهشت گذاشته شده بود.
        assertTrue(title(1404, 1, 11)?.contains("عید سعید فطر") == true)
        assertTrue(title(1404, 1, 12)?.contains("عید سعید فطر") == true)
        assertNull(title(1404, 2, 2))
        assertNull(title(1404, 2, 3))
        assertTrue(ShamsiHolidayCatalog.isOfficialHoliday(1404, 1, 11))
        assertFalse(ShamsiHolidayCatalog.isOfficialHoliday(1404, 2, 2))
    }

    @Test
    fun sadiq1404_is4Ordibehesht_notOld25Ordibehesht() {
        // خطای ۲۱ روزهٔ جدول قدیمی (۲۵ اردیبهشت).
        assertTrue(title(1404, 2, 4)?.contains("شهادت امام جعفر صادق") == true)
        assertNull(title(1404, 2, 25))
    }

    @Test
    fun aliShahadat1404_occursTwiceInTheYear() {
        // ۲۱ رمضان ۱۴۴۶ = ۲ فروردین (هم‌زمان با نوروز) و ۲۱ رمضان ۱۴۴۷ = ۲۰ اسفند.
        val farvardin2 = title(1404, 1, 2)
        assertTrue(farvardin2?.contains("شهادت حضرت علی") == true)
        assertTrue(farvardin2?.contains("نوروز") == true)
        assertTrue(title(1404, 12, 20)?.contains("شهادت حضرت علی") == true)
    }

    @Test
    fun tasuaAshuraArbaeen1404() {
        assertTrue(title(1404, 4, 14)?.contains("تاسوعا") == true)
        assertTrue(title(1404, 4, 15)?.contains("عاشورا") == true)
        assertTrue(title(1404, 5, 23)?.contains("اربعین") == true)
        // جدول قدیمی تاسوعا را ۱۵ تیر می‌گفت؛ ۱۵ تیر عاشوراست، نه تاسوعا.
        assertFalse(title(1404, 4, 15)?.contains("تاسوعا") == true)
    }

    @Test
    fun safarAndRabi1404() {
        assertTrue(title(1404, 5, 31)?.contains("رحلت پیامبر") == true)
        assertTrue(title(1404, 6, 2)?.contains("شهادت امام رضا") == true)
        assertTrue(title(1404, 6, 10)?.contains("شهادت امام حسن عسکری") == true)
        assertTrue(title(1404, 6, 19)?.contains("میلاد پیامبر") == true)
    }

    @Test
    fun fatemeh1404_secondNarrationIsOccasionNotHoliday() {
        // روایت دوم (۱۳ جمادی‌الاول = ۱۳ آبان) در تقویم رسمی مناسبت است، نه تعطیل.
        assertTrue(title(1404, 8, 13)?.contains("روایت دوم") == true)
        assertFalse(ShamsiHolidayCatalog.isOfficialHoliday(1404, 8, 13))
        // روایت اصلی (۳ جمادی‌الثانی = ۳ آذر) تعطیل رسمی است.
        assertTrue(title(1404, 9, 3)?.contains("شهادت حضرت فاطمه") == true)
        assertTrue(ShamsiHolidayCatalog.isOfficialHoliday(1404, 9, 3))
        // روایت دوم در فهرست «تعطیلات رسمی» ماه نمی‌آید.
        assertFalse(monthDays(1404, 8).contains(13))
        assertTrue(monthDays(1404, 9).contains(3))
    }

    @Test
    fun rajabShaban1404() {
        assertTrue(title(1404, 10, 13)?.contains("ولادت امام علی") == true)
        assertTrue(title(1404, 10, 27)?.contains("مبعث") == true)
        assertTrue(title(1404, 11, 15)?.contains("نیمه شعبان") == true)
    }

    // ─── ۱۴۰۵ ───

    @Test
    fun fitr1405_occursTwiceInTheYear() {
        // عید فطر هم اول سال (۱ فروردین، هم‌زمان با نوروز) و هم آخر سال (۱۹ اسفند).
        val farvardin1 = title(1405, 1, 1)
        assertTrue(farvardin1?.contains("عید سعید فطر") == true)
        assertTrue(farvardin1?.contains("نوروز") == true)
        assertTrue(title(1405, 1, 2)?.contains("عید سعید فطر") == true)
        assertTrue(title(1405, 12, 19)?.contains("عید سعید فطر") == true)
        assertTrue(title(1405, 12, 20)?.contains("عید سعید فطر") == true)
        assertTrue(title(1405, 1, 25)?.contains("شهادت امام جعفر صادق") == true)
    }

    @Test
    fun ghadir1405_mergesWithSolarRahlat() {
        // ۱۴ خرداد هم رحلت امام خمینی (خورشیدی) است و هم غدیر (قمری).
        val khordad14 = title(1405, 3, 14)
        assertTrue(khordad14?.contains("غدیر") == true)
        assertTrue(khordad14?.contains("خمینی") == true)
        assertTrue(title(1405, 3, 6)?.contains("عید سعید قربان") == true)
    }

    @Test
    fun muharramSafarRabi1405() {
        assertTrue(title(1405, 4, 3)?.contains("تاسوعا") == true)
        assertTrue(title(1405, 4, 4)?.contains("عاشورا") == true)
        assertTrue(title(1405, 5, 13)?.contains("اربعین") == true)
        assertTrue(title(1405, 5, 21)?.contains("رحلت پیامبر") == true)
        assertTrue(title(1405, 5, 22)?.contains("شهادت امام رضا") == true)
        assertTrue(title(1405, 5, 30)?.contains("شهادت امام حسن عسکری") == true)
        assertTrue(title(1405, 6, 8)?.contains("میلاد پیامبر") == true)
    }

    @Test
    fun fatemehRajabShabanRamadan1405() {
        assertTrue(title(1405, 8, 2)?.contains("روایت دوم") == true)
        assertFalse(ShamsiHolidayCatalog.isOfficialHoliday(1405, 8, 2))
        assertTrue(title(1405, 8, 22)?.contains("شهادت حضرت فاطمه") == true)
        assertTrue(ShamsiHolidayCatalog.isOfficialHoliday(1405, 8, 22))
        assertTrue(title(1405, 10, 2)?.contains("ولادت امام علی") == true)
        assertTrue(title(1405, 10, 16)?.contains("مبعث") == true)
        assertTrue(title(1405, 11, 4)?.contains("نیمه شعبان") == true)
        assertTrue(title(1405, 12, 9)?.contains("شهادت حضرت علی") == true)
    }

    // ─── ۱۴۰۶ ───

    @Test
    fun holidays1406() {
        assertTrue(title(1406, 1, 14)?.contains("شهادت امام جعفر صادق") == true)
        assertTrue(title(1406, 2, 27)?.contains("عید سعید قربان") == true)
        assertTrue(title(1406, 3, 4)?.contains("عید سعید غدیر") == true)
        assertTrue(title(1406, 3, 25)?.contains("تاسوعا") == true)
        assertTrue(title(1406, 3, 26)?.contains("عاشورا") == true)
        assertTrue(title(1406, 5, 3)?.contains("اربعین") == true)
        assertTrue(title(1406, 5, 11)?.contains("رحلت پیامبر") == true)
        assertTrue(title(1406, 5, 12)?.contains("شهادت امام رضا") == true)
        assertTrue(title(1406, 5, 20)?.contains("شهادت امام حسن عسکری") == true)
        assertTrue(title(1406, 5, 29)?.contains("میلاد پیامبر") == true)
        assertTrue(title(1406, 8, 12)?.contains("شهادت حضرت فاطمه") == true)
        assertTrue(title(1406, 9, 21)?.contains("ولادت امام علی") == true)
        assertTrue(title(1406, 10, 5)?.contains("مبعث") == true)
        assertTrue(title(1406, 10, 23)?.contains("نیمه شعبان") == true)
        assertTrue(title(1406, 11, 29)?.contains("شهادت حضرت علی") == true)
        assertTrue(title(1406, 12, 8)?.contains("عید سعید فطر") == true)
        assertTrue(title(1406, 12, 9)?.contains("عید سعید فطر") == true)
    }

    @Test
    fun fatemehSecondNarration1406_isUnknown_notGuessed() {
        // تاریخ جلالی ۱۳ جمادی‌الاول ۱۴۴۹ در هیچ منبع معتبری یافت نشد؛ در جدول
        // ثبت نشده است (نه اینکه روز دیگری حدس زده شده باشد).
        val hasSecondNarration = (1..30).any { day ->
            title(1406, 7, day)?.contains("روایت دوم") == true
        }
        assertFalse(hasSecondNarration)
    }

    // ─── خورشیدی دست نخورده + فهرست ماه سال‌محور ───

    @Test
    fun solarHolidaysUntouched() {
        assertTrue(title(1404, 1, 12)?.contains("جمهوری اسلامی") == true)
        assertTrue(title(1406, 11, 22)?.contains("پیروزی انقلاب") == true)
        assertTrue(title(1405, 12, 29)?.contains("ملی شدن صنعت نفت") == true)
        assertTrue(title(1404, 3, 14)?.contains("رحلت حضرت امام خمینی") == true)
        // برچسب مطالعاتی ۱۳ مهر — رفتار قبلی حفظ شده، دست نخورده.
        assertTrue(title(1405, 7, 13) != null)
    }

    @Test
    fun monthHolidaysAreYearAware() {
        // فروردین ۱۴۰۴: خورشیدی ۱،۲،۳،۴،۱۲،۱۳ + قمری ۲،۱۱،۱۲.
        assertEquals(listOf(1, 2, 3, 4, 11, 12, 13), monthDays(1404, 1))
        // فروردین ۱۴۰۶: خورشیدی ۱،۲،۳،۴،۱۲،۱۳ + قمری ۱۴ (شهادت امام صادق).
        assertEquals(listOf(1, 2, 3, 4, 12, 13, 14), monthDays(1406, 1))
        // مرداد ۱۴۰۵: قمری ۱۳،۲۱،۲۲،۳۰ و هیچ تعطیل خورشیدی ندارد.
        assertEquals(listOf(13, 21, 22, 30), monthDays(1405, 5))
    }

    // ─── «نامشخص»: ۱۴۰۷ ───

    @Test
    fun lunarHolidays1407_areUnknown_notGuessed() {
        // تقویم رسمی ۱۴۰۷ هنوز منتشر نشده؛ جدول قمری برای ۱۴۰۷ خالی است و فقط
        // تعطیلات خورشیدی برگردانده می‌شوند.
        assertNull(title(1407, 5, 3))
        assertFalse(ShamsiHolidayCatalog.isOfficialHoliday(1407, 5, 3))
        assertEquals(emptyList<Int>(), monthDays(1407, 5))
        assertEquals(listOf(1, 2, 3, 4, 12, 13), monthDays(1407, 1))
    }
}
