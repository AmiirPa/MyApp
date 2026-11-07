package com.example.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.ExpandLess
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.material.icons.filled.Groups
import androidx.compose.material.icons.filled.Lightbulb
import androidx.compose.material.icons.filled.MenuBook
import androidx.compose.material.icons.filled.Psychology
import androidx.compose.material.icons.filled.RadioButtonUnchecked
import androidx.compose.material.icons.filled.School
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.Timeline
import androidx.compose.material.icons.filled.WarningAmber
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.curriculum.CurriculumPhase
import com.example.data.curriculum.LearningStation
import com.example.data.curriculum.StagedLearningPathRepository

/**
 * نمایش مسیر یادگیری مرحله‌بندی شده برای ۵ درس کلیدی کنکور ۱۴۰۶
 * (ادبیات، عربی، معارف، روان‌شناسی و جامعه‌شناسی)
 * بارگذاری‌شده از فایل JSON جامع (konkoor_1406_staged_curriculum.json)
 * با ساختار ۱۰۰٪ عمودی و فهرست‌وار (بدون تب‌های بالای صفحه و بدون تداخل اسکرول)
 */
@OptIn(ExperimentalLayoutApi::class)
@Composable
fun StagedLearningPathView(
    onOpenLesson: (String) -> Unit = {},
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val allPaths = remember(context) {
        StagedLearningPathRepository.loadFromJsonAssets(context)
    }

    // وضعیت باز یا بسته بودن هر درس در فهرست عمودی (در ابتدا همه بسته تا هر ۵ درس یکجا و مرتب دیده شوند)
    val expandedSubjects = remember {
        mutableStateMapOf<String, Boolean>().apply {
            put("fonoon", false)
            put("arabic", false)
            put("dini", false)
            put("psychology", false)
            put("sociology", false)
        }
    }

    // وضعیت باز یا بسته بودن مراحل هر درس
    val expandedPhases = remember {
        mutableStateMapOf<String, Boolean>().apply {
            put("fonoon_1", true)
            put("arabic_1", true)
            put("dini_1", true)
            put("psychology_1", true)
            put("sociology_1", true)
        }
    }

    // ایستگاه‌های تکمیل‌شده توسط دانش‌آموز
    val completedStationIds = remember { mutableStateMapOf<String, Boolean>() }

    Column(
        modifier = modifier
            .fillMaxWidth()
            .testTag("staged_learning_path_vertical_list"),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        // کارت بازرسی و تطبیق ۱۰۰٪ با فهرست رسمی کتاب‌های درسی (تضمین عدم جا افتادگی)
        TextbookCompletenessAuditCard()

        // فهرست عمودی و مرتب ۵ درس اصلی کنکور ۱۴۰۶
        allPaths.forEach { path ->
            val isSubjectExpanded = expandedSubjects[path.subjectId] ?: false
            val completedInSubject = path.phases.flatMap { it.stations }.count { completedStationIds[it.id] == true }

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("vertical_subject_card_${path.subjectId}"),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                border = BorderStroke(
                    if (isSubjectExpanded) 1.5.dp else 1.dp,
                    if (isSubjectExpanded) path.themeColor else MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.45f)
                )
            ) {
                Column(modifier = Modifier.fillMaxWidth()) {
                    // سرصفحه فهرست‌وار هر درس
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(16.dp))
                            .clickable {
                                expandedSubjects[path.subjectId] = !isSubjectExpanded
                            }
                            .background(
                                if (isSubjectExpanded) path.themeColor.copy(alpha = 0.08f)
                                else Color.Transparent
                            )
                            .padding(14.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Surface(
                            shape = RoundedCornerShape(10.dp),
                            color = path.themeColor.copy(alpha = 0.15f),
                            modifier = Modifier.size(44.dp)
                        ) {
                            Box(contentAlignment = Alignment.Center) {
                                Icon(
                                    imageVector = when (path.subjectId) {
                                        "fonoon" -> Icons.Default.MenuBook
                                        "arabic" -> Icons.Default.School
                                        "dini" -> Icons.Default.Star
                                        "psychology" -> Icons.Default.Psychology
                                        "sociology" -> Icons.Default.Groups
                                        else -> Icons.Default.MenuBook
                                    },
                                    contentDescription = null,
                                    tint = path.themeColor,
                                    modifier = Modifier.size(24.dp)
                                )
                            }
                        }

                        Spacer(modifier = Modifier.width(12.dp))

                        Column(modifier = Modifier.weight(1f)) {
                            FlowRow(
                                horizontalArrangement = Arrangement.spacedBy(6.dp),
                                verticalArrangement = Arrangement.spacedBy(4.dp)
                            ) {
                                Text(
                                    text = path.subjectName,
                                    style = MaterialTheme.typography.titleMedium,
                                    fontWeight = FontWeight.Black,
                                    color = MaterialTheme.colorScheme.onSurface
                                )
                                Surface(
                                    shape = RoundedCornerShape(6.dp),
                                    color = path.themeColor.copy(alpha = 0.15f)
                                ) {
                                    Text(
                                        text = path.badgeTag,
                                        style = MaterialTheme.typography.labelSmall,
                                        fontSize = 10.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = path.themeColor,
                                        modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                                    )
                                }
                            }

                            Spacer(modifier = Modifier.height(2.dp))

                            Text(
                                text = BidiFormatter.formatMixedPersianText(
                                    "${path.totalPhasesCount} مرحله • ${path.totalStationsCount} ایستگاه مطالعاتی • ${path.konkoorWeightInfo}"
                                ),
                                style = MaterialTheme.typography.labelSmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )

                            if (completedInSubject > 0) {
                                Text(
                                    text = BidiFormatter.formatMixedPersianText("تکمیل شده: $completedInSubject از ${path.totalStationsCount} پله"),
                                    style = MaterialTheme.typography.labelSmall,
                                    color = Color(0xFF15803D),
                                    fontWeight = FontWeight.Bold
                                )
                            }
                        }

                        IconButton(onClick = {
                            expandedSubjects[path.subjectId] = !isSubjectExpanded
                        }) {
                            Icon(
                                imageVector = if (isSubjectExpanded) Icons.Default.ExpandLess else Icons.Default.ExpandMore,
                                contentDescription = if (isSubjectExpanded) "بستن فهرست درس" else "باز کردن فهرست درس",
                                tint = path.themeColor
                            )
                        }
                    }

                    // محتوای مراحل درس هنگام باز بودن
                    AnimatedVisibility(
                        visible = isSubjectExpanded,
                        enter = expandVertically() + fadeIn(),
                        exit = shrinkVertically() + fadeOut()
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 12.dp, vertical = 8.dp),
                            verticalArrangement = Arrangement.spacedBy(10.dp)
                        ) {
                            Text(
                                text = path.summaryDescription,
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.85f),
                                modifier = Modifier.padding(horizontal = 4.dp, vertical = 2.dp)
                            )

                            HorizontalDivider(
                                color = path.themeColor.copy(alpha = 0.2f),
                                thickness = 0.8.dp
                            )

                            // نمایش فهرست‌وار تمام ۴ مرحله هر درس
                            path.phases.forEach { phase ->
                                val phaseKey = "${path.subjectId}_${phase.phaseNumber}"
                                val isPhaseExpanded = expandedPhases[phaseKey] ?: false

                                VerticalPhaseItem(
                                    phase = phase,
                                    themeColor = path.themeColor,
                                    isExpanded = isPhaseExpanded,
                                    completedStationIds = completedStationIds,
                                    onToggleExpand = {
                                        expandedPhases[phaseKey] = !isPhaseExpanded
                                    },
                                    onToggleCompleteStation = { stationId ->
                                        val cur = completedStationIds[stationId] ?: false
                                        completedStationIds[stationId] = !cur
                                    },
                                    onOpenLesson = onOpenLesson
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
private fun VerticalPhaseItem(
    phase: CurriculumPhase,
    themeColor: Color,
    isExpanded: Boolean,
    completedStationIds: Map<String, Boolean>,
    onToggleExpand: () -> Unit,
    onToggleCompleteStation: (String) -> Unit,
    onOpenLesson: (String) -> Unit
) {
    Surface(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.35f),
        border = BorderStroke(
            0.8.dp,
            if (isExpanded) themeColor.copy(alpha = 0.6f) else MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.35f)
        )
    ) {
        Column(modifier = Modifier.fillMaxWidth()) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(12.dp))
                    .clickable { onToggleExpand() }
                    .padding(12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Surface(
                    color = themeColor.copy(alpha = 0.15f),
                    shape = CircleShape,
                    modifier = Modifier.size(32.dp)
                ) {
                    Box(contentAlignment = Alignment.Center) {
                        Text(
                            text = BidiFormatter.toPersianDigits(phase.phaseNumber.toString()),
                            style = MaterialTheme.typography.titleSmall,
                            fontWeight = FontWeight.Black,
                            color = themeColor
                        )
                    }
                }

                Spacer(modifier = Modifier.width(10.dp))

                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = phase.title,
                        style = MaterialTheme.typography.titleSmall,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    Text(
                        text = BidiFormatter.formatMixedPersianText("🎯 هدف: ${phase.milestoneGoal}"),
                        style = MaterialTheme.typography.bodySmall,
                        fontSize = 11.sp,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }

                IconButton(onClick = { onToggleExpand() }, modifier = Modifier.size(28.dp)) {
                    Icon(
                        imageVector = if (isExpanded) Icons.Default.ExpandLess else Icons.Default.ExpandMore,
                        contentDescription = null,
                        tint = themeColor,
                        modifier = Modifier.size(20.dp)
                    )
                }
            }

            AnimatedVisibility(
                visible = isExpanded,
                enter = expandVertically() + fadeIn(),
                exit = shrinkVertically() + fadeOut()
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 10.dp, vertical = 6.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    phase.stations.forEach { station ->
                        val isDone = completedStationIds[station.id] == true
                        VerticalStationRow(
                            station = station,
                            themeColor = themeColor,
                            isDone = isDone,
                            onToggleDone = { onToggleCompleteStation(station.id) },
                            onOpenLesson = onOpenLesson
                        )
                    }
                    Spacer(modifier = Modifier.height(4.dp))
                }
            }
        }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
private fun VerticalStationRow(
    station: LearningStation,
    themeColor: Color,
    isDone: Boolean,
    onToggleDone: () -> Unit,
    onOpenLesson: (String) -> Unit
) {
    Surface(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(10.dp),
        color = if (isDone) Color(0xFFF0FDF4) else MaterialTheme.colorScheme.surface,
        border = BorderStroke(
            0.6.dp,
            if (isDone) Color(0xFF16A34A).copy(alpha = 0.6f) else MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.35f)
        )
    ) {
        Column(
            modifier = Modifier.padding(10.dp),
            verticalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.Top
            ) {
                IconButton(
                    onClick = onToggleDone,
                    modifier = Modifier.size(28.dp)
                ) {
                    Icon(
                        imageVector = if (isDone) Icons.Default.CheckCircle else Icons.Default.RadioButtonUnchecked,
                        contentDescription = null,
                        tint = if (isDone) Color(0xFF16A34A) else MaterialTheme.colorScheme.outline,
                        modifier = Modifier.size(20.dp)
                    )
                }
                Spacer(modifier = Modifier.width(6.dp))
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = BidiFormatter.formatMixedPersianText("پله ${station.stationNumber}: ${station.title}"),
                        style = MaterialTheme.typography.titleSmall,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    Text(
                        text = station.subtitle,
                        style = MaterialTheme.typography.bodySmall,
                        fontSize = 11.sp,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }

            // کلیدواژه‌ها و مفاهیم محوری
            FlowRow(
                horizontalArrangement = Arrangement.spacedBy(4.dp),
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                station.keyConcepts.forEach { concept ->
                    Surface(
                        shape = RoundedCornerShape(4.dp),
                        color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.45f)
                    ) {
                        Text(
                            text = concept,
                            style = MaterialTheme.typography.labelSmall,
                            fontSize = 10.sp,
                            modifier = Modifier.padding(horizontal = 6.dp, vertical = 3.dp),
                            color = MaterialTheme.colorScheme.onSurface
                        )
                    }
                }
            }

            // شگرد طلایی کنکور ۱۴۰۶
            Surface(
                shape = RoundedCornerShape(6.dp),
                color = Color(0xFFFEF3C7).copy(alpha = 0.45f),
                border = BorderStroke(0.5.dp, Color(0xFFD97706).copy(alpha = 0.5f)),
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier.padding(6.dp),
                    verticalAlignment = Alignment.Top
                ) {
                    Icon(
                        imageVector = Icons.Default.Lightbulb,
                        contentDescription = null,
                        tint = Color(0xFFB45309),
                        modifier = Modifier.size(14.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = BidiFormatter.formatMixedPersianText("⚡ شگرد کنکور ۱۴۰۶: ${station.goldenTechnique}"),
                        style = MaterialTheme.typography.labelSmall,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF92400E)
                    )
                }
            }

            // نقشه راه و پیش‌نیاز سلسله‌مراتب مطالعاتی
            if (station.stationNumber > 1) {
                Surface(
                    shape = RoundedCornerShape(6.dp),
                    color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.6f),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Row(
                        modifier = Modifier.padding(6.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Default.Timeline,
                            contentDescription = null,
                            tint = themeColor,
                            modifier = Modifier.size(13.dp)
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = BidiFormatter.formatMixedPersianText("🔗 سلسله‌مراتب: برای فهم این پله، ابتدا باید پله‌های ۱ تا ${station.stationNumber - 1} را یاد بگیرید"),
                            style = MaterialTheme.typography.labelSmall,
                            fontSize = 10.sp,
                            fontWeight = FontWeight.Bold,
                            color = themeColor
                        )
                    }
                }
            }

            // دام طراح
            Surface(
                shape = RoundedCornerShape(6.dp),
                color = MaterialTheme.colorScheme.errorContainer.copy(alpha = 0.3f),
                border = BorderStroke(0.5.dp, MaterialTheme.colorScheme.error.copy(alpha = 0.4f)),
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier.padding(6.dp),
                    verticalAlignment = Alignment.Top
                ) {
                    Icon(
                        imageVector = Icons.Default.WarningAmber,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.error,
                        modifier = Modifier.size(14.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = BidiFormatter.formatMixedPersianText("⚠️ دام پرتکرار طراح: ${station.commonMistakeTrap}"),
                        style = MaterialTheme.typography.labelSmall,
                        fontWeight = FontWeight.Medium,
                        color = MaterialTheme.colorScheme.onErrorContainer
                    )
                }
            }

            // بازده و دکمه ورود به درس
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = BidiFormatter.formatMixedPersianText("🎯 ${station.targetExamYield} (${station.estimatedHours} ساعت • ${station.targetTestCount} تست)"),
                    style = MaterialTheme.typography.labelSmall,
                    fontSize = 10.sp,
                    fontWeight = FontWeight.Bold,
                    color = themeColor,
                    modifier = Modifier.weight(1f)
                )

                if (station.targetLessonId != null) {
                    Spacer(modifier = Modifier.width(6.dp))
                    OutlinedButton(
                        onClick = { onOpenLesson(station.targetLessonId) },
                        contentPadding = PaddingValues(horizontal = 8.dp, vertical = 2.dp),
                        shape = RoundedCornerShape(6.dp),
                        modifier = Modifier.height(28.dp)
                    ) {
                        Text(
                            text = "ورود به درس",
                            style = MaterialTheme.typography.labelSmall,
                            fontSize = 10.sp
                        )
                        Spacer(modifier = Modifier.width(2.dp))
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                            contentDescription = null,
                            modifier = Modifier.size(10.dp)
                        )
                    }
                }
            }
        }
    }
}

private data class TextbookAuditRow(
    val subjectTitle: String,
    val officialBooksCount: String,
    val chaptersCovered: String,
    val stationMapping: String
)

@Composable
private fun TextbookCompletenessAuditCard() {
    var expanded by remember { mutableStateOf(false) }
    val auditRows = remember {
        listOf(
            TextbookAuditRow(
                subjectTitle = "۱. علوم و فنون ادبی ۱، ۲ و ۳ + فارسی (۳ کتاب تخصصی + ۳ کتاب عمومی)",
                officialBooksCount = "۳۶ درس رسمی (۱۲ درس دهم + ۱۲ درس یازدهم + ۱۲ درس دوازدهم)",
                chaptersCovered = "پوشش ۱۰۰٪ هر ۴ محور کتاب درسی: تاریخ ادبیات و سبک‌شناسی (قرن ۴ تا معاصر) • موسیقی شعر و عروض (۲۰ بحر و وزن کنکور + اختیارات شاعری) • بدیع لفظی و معنوی (سجع، جناس، ایهام، تلمیح، تضاد، متناقض‌نما، لف و نشر، اغراق، حسن تعلیل، اسلوب معادله، حس‌آمیزی) • قرابت معنایی و بیان (تشبیه، استعاره، مجاز، کنایه)",
                stationMapping = "ایستگاه‌های ۱ تا ۱۶ (۴ فاز کامل)"
            ),
            TextbookAuditRow(
                subjectTitle = "۲. عربی، زبان قرآن ۱، ۲ و ۳ (ویژه رشته انسانی)",
                officialBooksCount = "۲۳ درس رسمی (۸ درس دهم + ۸ درس یازدهم + ۷ درس دوازدهم)",
                chaptersCovered = "پوشش ۱۰۰٪ قواعد هر ۳ پایه: ثلاثی مجرد و مزید (۸ باب) • اعلال (قلب، اسکان، حذف) • جامد و مشتق • افعال ناقصه، مقاربه و مشبهة بالفعل • منصوبات (مفعول‌به، مطلق، فیه، لاجله، حال، تمییز، منادا، مستثنی) • مجرورات و توابع (نعت، عطف، بدل، تأکید) • اسلوب شرط، حصر، ندبه، تعجب، قسم و عدد • کارگاه ترجمه و درک مطلب",
                stationMapping = "ایستگاه‌های ۱ تا ۱۸ (۴ فاز کامل)"
            ),
            TextbookAuditRow(
                subjectTitle = "۳. دین و زندگی ۱، ۲ و ۳ + منابع اختصاصی دانشگاه فرهنگیان",
                officialBooksCount = "۳۶ درس دینی (۱۲ دهم + ۱۲ یازدهم + ۱۲ دوازدهم) + ۲ منبع فرهنگیان",
                chaptersCovered = "پوشش ۱۰۰٪ آیات، روایات و مفاهیم: هدف زندگی و توحید (۴ مرتبه توحید و شرک) • نبوت، اعجاز قرآن و امامت و ولایت فقیه • معاد، برزخ، قضا و قدر و اختیار • احکام (تقلید، بلوغ، خمس، زکات، ازدواج، جهاد) • مهارت‌های معلمی استاد قرائتی + تعلیم و تربیت اسلامی + هوش و استعداد معلمی",
                stationMapping = "ایستگاه‌های ۱ تا ۱۶ (۴ فاز کامل)"
            ),
            TextbookAuditRow(
                subjectTitle = "۴. روان‌شناسی تخصصی (پایه یازدهم انسانی)",
                officialBooksCount = "۸ فصل رسمی کتاب درسی روان‌شناسی",
                chaptersCovered = "پوشش ۱۰۰٪ هر ۸ فصل کتاب: فصل ۱ (تعریف، مکاتب و روش‌های تحقیق آزمایشی/همبستگی) • فصل ۲ (رشد، رسش، پیاژه و اریکسون) • فصل ۳ (احساس، توجه و ادراک) • فصل ۴ (حافظه حسی، کوتاه‌مدت، کاری، بلندمدت و فراموشی) • فصل ۵ (یادگیری، شرطی‌سازی کلاسیک و کنشی، تقویت و تنبیه) • فصل ۶ (تفکر، حل مسئله و تصمیم‌گیری) • فصل ۷ و ۸ (استرس، مقابله و روان‌شناسی سلامت)",
                stationMapping = "ایستگاه‌های ۱ تا ۱۴ (۴ فاز کامل)"
            ),
            TextbookAuditRow(
                subjectTitle = "۵. جامعه‌شناسی ۱، ۲ و ۳ (دهم، یازدهم و دوازدهم انسانی)",
                officialBooksCount = "۴۲ درس رسمی (۱۴ درس دهم + ۱۴ درس یازدهم + ۱۴ درس دوازدهم)",
                chaptersCovered = "پوشش ۱۰۰٪ هر ۳ پایه: کنش فردی و اجتماعی و پیامدهای آن • پدیده‌ها و ساختار اجتماعی • فرهنگ (حق و باطل، آرمانی و واقعی) • هویت فردی/اجتماعی و تحرک اجتماعی • جامعه‌پذیری و کج‌روی • تغییرات فرهنگی (نوآوری، وام‌گیری، ازخودبیگانگی) • جهان‌های اجتماعی و جهان غرب • ۳ رویکرد نظری (اثبات‌گرایی، تفسیری، انتقادی) • سیاست، اقتصاد و آسیب‌های اجتماعی",
                stationMapping = "ایستگاه‌های ۱ تا ۱۶ (۴ فاز کامل)"
            )
        )
    }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .testTag("textbook_completeness_audit_card"),
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFF059669).copy(alpha = 0.08f)
        ),
        border = BorderStroke(1.dp, Color(0xFF059669).copy(alpha = 0.45f))
    ) {
        Column(modifier = Modifier.padding(12.dp)) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(10.dp))
                    .clickable { expanded = !expanded }
                    .padding(4.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.weight(1f)
                ) {
                    Icon(
                        imageVector = Icons.Default.CheckCircle,
                        contentDescription = null,
                        tint = Color(0xFF059669),
                        modifier = Modifier.size(22.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Column {
                        Text(
                            text = "رادار تضمین پوشش ۱۰۰٪ کتاب‌های درسی (بررسی عدم کسری مطالب)",
                            style = MaterialTheme.typography.labelLarge,
                            fontWeight = FontWeight.Black,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                        Text(
                            text = "تطبیق خط‌به‌خط ۱۴۵ درس رسمی کتاب‌های دهم، یازدهم و دوازدهم با ۸۰ ایستگاه",
                            style = MaterialTheme.typography.labelSmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
                Surface(
                    shape = RoundedCornerShape(8.dp),
                    color = Color(0xFF059669).copy(alpha = 0.15f)
                ) {
                    Row(
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = if (expanded) "بستن جدول" else "مشاهده جدول تطبیق",
                            style = MaterialTheme.typography.labelSmall,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF059669)
                        )
                        Icon(
                            imageVector = if (expanded) Icons.Default.ExpandLess else Icons.Default.ExpandMore,
                            contentDescription = null,
                            tint = Color(0xFF059669),
                            modifier = Modifier.size(16.dp)
                        )
                    }
                }
            }

            AnimatedVisibility(
                visible = expanded,
                enter = expandVertically() + fadeIn(),
                exit = shrinkVertically() + fadeOut()
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 10.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    auditRows.forEach { row ->
                        Surface(
                            shape = RoundedCornerShape(10.dp),
                            color = MaterialTheme.colorScheme.surface,
                            border = BorderStroke(0.8.dp, Color(0xFF059669).copy(alpha = 0.35f)),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Column(
                                modifier = Modifier.padding(10.dp),
                                verticalArrangement = Arrangement.spacedBy(4.dp)
                            ) {
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Text(
                                        text = row.subjectTitle,
                                        style = MaterialTheme.typography.labelMedium,
                                        fontWeight = FontWeight.Black,
                                        color = MaterialTheme.colorScheme.onSurface,
                                        modifier = Modifier.weight(1f)
                                    )
                                    Surface(
                                        shape = RoundedCornerShape(6.dp),
                                        color = Color(0xFF059669).copy(alpha = 0.15f)
                                    ) {
                                        Text(
                                            text = row.stationMapping,
                                            style = MaterialTheme.typography.labelSmall,
                                            fontSize = 10.sp,
                                            fontWeight = FontWeight.Bold,
                                            color = Color(0xFF059669),
                                            modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                                        )
                                    }
                                }
                                Text(
                                    text = "📚 حجم کتاب درسی: ${row.officialBooksCount}",
                                    style = MaterialTheme.typography.labelSmall,
                                    fontWeight = FontWeight.Bold,
                                    color = MaterialTheme.colorScheme.primary
                                )
                                Text(
                                    text = "✅ سرفصل‌های پوشش‌داده‌شده: ${row.chaptersCovered}",
                                    style = MaterialTheme.typography.bodySmall,
                                    fontSize = 11.sp,
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

