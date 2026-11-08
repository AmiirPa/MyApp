package com.example.data.adaptive

import com.example.data.curriculum.ZeroLesson
import com.example.data.curriculum.prerequisiteStepOrder

/**
 * برنامه‌ریز مطالعاتی واقع‌بینانه و تطبیقی (§۷ الحاقیه — هسته برنامه‌ریز).
 *
 * قواعد:
 * - مرورهای سررسید اول می‌آیند (حداکثر ۴۰٪ زمان روز).
 * - بعد مباحث با ضعف پیش‌نیاز (درس بنیادین پیش‌نیازشان).
 * - بعد درس بعدی مسیر — اول پیش‌نیاز، بعد وابسته (ترتیب `prerequisiteStepOrder`).
 * - مجموع دقیقه‌ها هرگز از زمان در دسترس بیشتر نمی‌شود؛ آیتم اضافه به صف فردا می‌رود.
 * - عقب‌افتادگی بدون سرزنش بازسازی می‌شود: آیتم‌های انجام‌نشده به ابتدای صف روز بعد
 *   می‌آیند، با همان سقف زمانی؛ هیچ‌چیز حذف یا دوبرابر نمی‌شود.
 */
object AdaptivePlanner {

    /** دقیقه هر مرور. */
    const val REVIEW_MINUTES = 10

    /** دقیقه آزمون کوتاه پایان روز. */
    const val TEST_MINUTES = 15

    /** سقف سهم مرور از زمان روز (۴۰٪). */
    const val MAX_REVIEW_SHARE = 0.40

    /** ورودی ساخت برنامه روز. */
    data class PlanInput(
        /** همه درس‌های در دسترس (فهرست تجمیعی اپ). */
        val lessons: List<ZeroLesson>,
        /** شواهد درس‌ها از ذخیره‌ساز موجود؛ کلید = شناسه درس. */
        val lessonEvidence: Map<String, LessonEvidence>,
        /** وضعیت مباحث؛ کلید = شناسه مبحث (شناسه درس). */
        val topicStatuses: Map<String, TopicStatusResult>,
        /** مرورهای سررسید امروز. */
        val dueReviews: List<ReviewCardState>,
        /** دقیقه‌های در دسترس امروز. */
        val availableMinutes: Int,
        val todayEpochDay: Long,
        /** درس بنیادین پیش‌نیاز هر مبحث؛ کلید = شناسه مبحث، مقدار = شناسه درس بنیادین. */
        val prerequisiteLessonIdByTopic: Map<String, String> = emptyMap()
    )

    /** نتیجه بازسازی بعد از روزهای ازدست‌رفته. */
    data class ReplannedDay(
        val plan: DailyPlan,
        /** آیتم‌هایی که در سقف امروز جا نشدند و به روز بعد می‌روند. */
        val carriedOver: List<DailyPlanItem>
    )

    private fun lessonById(lessons: List<ZeroLesson>, id: String): ZeroLesson? =
        lessons.firstOrNull { it.id == id }

    private fun isCompleted(evidence: Map<String, LessonEvidence>, lessonId: String): Boolean =
        evidence[lessonId]?.isCompleted == true

    /**
     * برنامه روز را می‌سازد.
     * @return برنامه با آیتم‌های مرتب و مجموع دقیقه ≤ زمان در دسترس.
     */
    fun buildDailyPlan(input: PlanInput): DailyPlan {
        val items = ArrayList<DailyPlanItem>()
        var usedMinutes = 0
        val available = input.availableMinutes.coerceAtLeast(0)

        // ۱. مرورهای سررسید — حداکثر ۴۰٪ زمان روز، به ترتیب اولویت موتور مرور.
        val reviewBudget = (available * MAX_REVIEW_SHARE).toInt()
        val reviewQueue = ReviewScheduler.buildReviewQueue(
            input.dueReviews,
            input.todayEpochDay,
            maxItems = Int.MAX_VALUE
        )
        for (card in reviewQueue) {
            if (usedMinutes + REVIEW_MINUTES > reviewBudget) break
            if (usedMinutes + REVIEW_MINUTES > available) break
            items.add(
                DailyPlanItem(
                    lessonId = card.itemId,
                    subjectId = card.subjectId,
                    kind = PlanItemKind.REVIEW,
                    minutes = REVIEW_MINUTES,
                    reason = "مرور سررسید شده است" +
                        if (card.errorCount > 1) " — این مبحث ${card.errorCount} بار اشتباه شده و اولویت دارد." else "."
                )
            )
            usedMinutes += REVIEW_MINUTES
        }

        // ۲. مباحث با ضعف پیش‌نیاز — درس بنیادین پیش‌نیازشان.
        val weakPrerequisiteTopics = input.topicStatuses
            .filter { it.value.status == TopicStatus.PREREQUISITE_WEAK }
            .keys
        for (topicId in weakPrerequisiteTopics) {
            val foundationId = input.prerequisiteLessonIdByTopic[topicId] ?: continue
            if (isCompleted(input.lessonEvidence, foundationId)) continue
            val lesson = lessonById(input.lessons, foundationId) ?: continue
            val minutes = lesson.estimatedMinutes
            if (usedMinutes + minutes > available) break
            items.add(
                DailyPlanItem(
                    lessonId = foundationId,
                    subjectId = lesson.subjectId,
                    kind = PlanItemKind.LEARN,
                    minutes = minutes,
                    reason = "پیش‌نیاز مبحث «${lesson.title}» هنوز کامل نشده؛ اول این درس پایه خوانده می‌شود."
                )
            )
            usedMinutes += minutes
        }

        // ۳. درس بعدی مسیر — اول پیش‌نیاز، بعد وابسته.
        // هر روز حداکثر ۲ درس جدید (بقیه زمان برای مرور و تثبیت می‌ماند).
        val orderedLessons = input.lessons.sortedWith(
            compareBy<ZeroLesson> { it.prerequisiteStepOrder() }.thenBy { it.orderIndex }
        )
        var newLessonsAdded = 0
        for (lesson in orderedLessons) {
            if (usedMinutes >= available) break
            if (newLessonsAdded >= 2) break
            if (isCompleted(input.lessonEvidence, lesson.id)) continue
            if (items.any { it.lessonId == lesson.id }) continue
            val minutes = lesson.estimatedMinutes
            // درس بزرگ‌تر از زمان باقی‌مانده، به فردا می‌رود (برنامه فشرده نمی‌شود).
            if (usedMinutes + minutes > available) continue
            items.add(
                DailyPlanItem(
                    lessonId = lesson.id,
                    subjectId = lesson.subjectId,
                    kind = PlanItemKind.LEARN,
                    minutes = minutes,
                    reason = "درس بعدی مسیر یادگیری."
                )
            )
            usedMinutes += minutes
            newLessonsAdded++
        }

        // ۴. آزمون کوتاه — فقط اگر زمان ماند و حداقل یک درس خوانده شده باشد.
        val remaining = available - usedMinutes
        val hasLearnedToday = items.any { it.kind == PlanItemKind.LEARN }
        if (hasLearnedToday && remaining >= TEST_MINUTES) {
            val testSubject = items.first { it.kind == PlanItemKind.LEARN }.subjectId
            items.add(
                DailyPlanItem(
                    lessonId = "test:$testSubject",
                    subjectId = testSubject,
                    kind = PlanItemKind.TEST,
                    minutes = TEST_MINUTES,
                    reason = "آزمون کوتاه برای تثبیت درس‌های امروز."
                )
            )
        }

        return DailyPlan(
            dateEpochDay = input.todayEpochDay,
            items = items,
            generatedAtEpochDay = input.todayEpochDay
        )
    }

    /**
     * بازسازی برنامه بعد از روزهای ازدست‌رفته — بدون سرزنش.
     * آیتم‌های انجام‌نشده به ابتدای صف روز بعد می‌آیند؛ آیتم‌هایی که در سقف
     * امروز جا نمی‌شوند، به‌عنوان `carriedOver` برمی‌گردند (حذف نمی‌شوند).
     */
    fun replanAfterMissedDays(
        missedItems: List<DailyPlanItem>,
        availableMinutes: Int,
        todayEpochDay: Long
    ): ReplannedDay {
        val kept = ArrayList<DailyPlanItem>()
        val carried = ArrayList<DailyPlanItem>()
        var used = 0
        for (item in missedItems) {
            if (used + item.minutes <= availableMinutes) {
                kept.add(item)
                used += item.minutes
            } else {
                carried.add(item)
            }
        }
        return ReplannedDay(
            plan = DailyPlan(
                dateEpochDay = todayEpochDay,
                items = kept,
                generatedAtEpochDay = todayEpochDay
            ),
            carriedOver = carried
        )
    }

    /**
     * پایبندی به برنامه: انجام‌شده / برنامه‌ریزی‌شده.
     * بدون برنامه برای آن روز → `null` (داده کافی نیست).
     */
    fun planAdherence(planned: DailyPlan?, completedLessonIds: Set<String>): Double? {
        if (planned == null || planned.items.isEmpty()) return null
        val done = planned.items.count { it.lessonId in completedLessonIds }
        return done.toDouble() / planned.items.size.toDouble()
    }
}
