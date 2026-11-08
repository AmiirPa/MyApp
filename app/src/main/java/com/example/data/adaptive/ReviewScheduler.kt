package com.example.data.adaptive

import com.example.util.StreakCalculator

/**
 * زمان‌بند مرور فاصله‌دار (§۵ الحاقیه) — شبیه لایتنر، بر اساس عملکرد واقعی.
 *
 * - جعبه‌ها ۱ تا ۵ با فاصله‌های ۱، ۲، ۴، ۸، ۱۶ روز.
 * - درست → جعبه +۱ (سقف ۵)؛ غلط → جعبه ۱.
 * - اولویت: اشتباه‌های پرتکرار اول (`3 × errorCount + daysOverdue`).
 * - صف مرور درهم‌آمیخته (Interleaving): چرخشی از درس‌های مختلف.
 * - بدون سابقه، چیزی سررسید نمی‌شود؛ ماندگاری فقط با حداقل ۳ مرور محاسبه می‌شود.
 * - همه توابع خالص‌اند؛ روز امروز به‌عنوان پارامتر می‌آید تا تست‌پذیر بماند.
 */
object ReviewScheduler {

    /** فاصله مرور (روز) برای هر جعبه لایتنر. */
    val BOX_INTERVAL_DAYS = mapOf(1 to 1L, 2 to 2L, 3 to 4L, 4 to 8L, 5 to 16L)

    /** حداقل مرور برای محاسبه نرخ ماندگاری. */
    const val MIN_REVIEWS_FOR_RETENTION = 3

    /** فاصله پیش‌فرض وقتی جعبه نامعتبر است. */
    const val DEFAULT_INTERVAL_DAYS = 1L

    /** فاصله روز بعد از مرور موفق در جعبه داده‌شده. */
    fun intervalDaysForBox(box: Int): Long =
        BOX_INTERVAL_DAYS[box.coerceIn(1, 5)] ?: DEFAULT_INTERVAL_DAYS

    /**
     * وضعیت جدید کارت بعد از یک مرور.
     * @param correct یادآوری درست بود.
     * @param reviewEpochDay روز مرور (epoch day).
     */
    fun applyReview(
        card: ReviewCardState,
        correct: Boolean,
        reviewEpochDay: Long
    ): ReviewCardState {
        val newBox = if (correct) (card.leitnerBox + 1).coerceAtMost(5) else 1
        val nextDay = reviewEpochDay + intervalDaysForBox(newBox)
        return card.copy(
            leitnerBox = newBox,
            errorCount = if (correct) card.errorCount else card.errorCount + 1,
            totalReviews = card.totalReviews + 1,
            consecutiveCorrect = if (correct) card.consecutiveCorrect + 1 else 0,
            lastReviewEpochDay = reviewEpochDay,
            nextReviewEpochDay = nextDay
        )
    }

    /** کارت جدید بعد از اولین جلسه یادگیری وارد جعبه ۱ می‌شود. */
    fun newCard(itemId: String, topicId: String, subjectId: String, learnedEpochDay: Long): ReviewCardState =
        ReviewCardState(
            itemId = itemId,
            topicId = topicId,
            subjectId = subjectId,
            leitnerBox = 1,
            errorCount = 0,
            totalReviews = 0,
            consecutiveCorrect = 0,
            lastReviewEpochDay = learnedEpochDay,
            nextReviewEpochDay = learnedEpochDay + intervalDaysForBox(1)
        )

    /** آیا کارت سررسید شده است؟ */
    fun isDue(card: ReviewCardState, todayEpochDay: Long): Boolean =
        card.nextReviewEpochDay <= todayEpochDay

    /** کارت‌های سررسیدشده. */
    fun dueCards(cards: List<ReviewCardState>, todayEpochDay: Long): List<ReviewCardState> =
        cards.filter { isDue(it, todayEpochDay) }

    /** چند روز از سررسید گذشته است (۰ یعنی امروز سررسید است). */
    fun daysOverdue(card: ReviewCardState, todayEpochDay: Long): Long =
        (todayEpochDay - card.nextReviewEpochDay).coerceAtLeast(0L)

    /**
     * امتیاز اولویت مرور: اشتباه‌های پرتکرار اول.
     * `priority = 3 × errorCount + daysOverdue`
     */
    fun priorityScore(card: ReviewCardState, todayEpochDay: Long): Long =
        3L * card.errorCount + daysOverdue(card, todayEpochDay)

    /**
     * صف مرور روز: سررسیدشده‌ها به ترتیب اولویت، ولی **درهم‌آمیخته** —
     * به‌صورت چرخشی از درس‌های مختلف می‌آیند، نه بلوکی از یک درس.
     */
    fun buildReviewQueue(
        cards: List<ReviewCardState>,
        todayEpochDay: Long,
        maxItems: Int
    ): List<ReviewCardState> {
        if (maxItems <= 0) return emptyList()
        val due = dueCards(cards, todayEpochDay)
            .sortedWith(
                compareByDescending<ReviewCardState> { priorityScore(it, todayEpochDay) }
                    .thenBy { it.nextReviewEpochDay }
            )
        if (due.isEmpty()) return emptyList()
        // درهم‌آمیزی: گروه‌بندی بر اساس درس، بعد چرخش نوبتی.
        val bySubject = due.groupBy { it.subjectId }.values.map { it.toMutableList() }
        val queue = ArrayList<ReviewCardState>(due.size)
        var added = true
        while (added && queue.size < maxItems) {
            added = false
            for (group in bySubject) {
                if (queue.size >= maxItems) break
                if (group.isNotEmpty()) {
                    queue.add(group.removeAt(0))
                    added = true
                }
            }
        }
        return queue
    }

    /**
     * نرخ ماندگاری: مرورهای درست / کل مرورها.
     * کمتر از [MIN_REVIEWS_FOR_RETENTION] مرور → `null` (داده کافی نیست).
     */
    fun retentionRate(cards: List<ReviewCardState>): Double? {
        val total = cards.sumOf { it.totalReviews }
        if (total < MIN_REVIEWS_FOR_RETENTION) return null
        val correct = cards.sumOf { it.totalReviews - it.errorCount }
        if (total == 0) return null
        return correct.toDouble() / total.toDouble()
    }

    /** روز امروز (epoch day) — برای فراخوانی از UI. */
    fun todayEpochDay(): Long = StreakCalculator.todayEpochDay()
}
