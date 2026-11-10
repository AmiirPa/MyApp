package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.data.adaptive.PlanItemKind
import com.example.data.curriculum.SubjectCatalog
import com.example.ui.KonkoorUiState
import com.example.ui.KonkoorViewModel
import com.example.ui.components.BidiFormatter
import com.example.ui.theme.resolveDynamicAccent
import com.example.util.StreakCalculator

/**
 * صفحه برنامه امروز (§۷ الحاقیه — برنامه‌ریزی واقع‌گرایانه).
 *
 * اگر برای امروز برنامه‌ای ساخته نشده باشد، کاربر دقیقه‌های در دسترس را
 * می‌گوید و برنامه ساخته می‌شود (حداکثر ۴۰٪ روز مرور، حداکثر ۲ درس تازه).
 * هر آیتم دلیلش را نشان می‌دهد. دکمه بازسازی، آیتم‌های انجام‌نشده روزهای
 * قبل را — بدون سرزنش — به ابتدای امروز می‌آورد.
 */
@Composable
fun DailyPlanScreen(
    viewModel: KonkoorViewModel,
    uiState: KonkoorUiState,
    onOpenLesson: (String) -> Unit
) {
    val plan by viewModel.adaptiveDailyPlan.collectAsStateWithLifecycle()
    val reviewCards by viewModel.adaptiveReviewCards.collectAsStateWithLifecycle()
    var selectedMinutes by remember { mutableIntStateOf(60) }
    var replanMessage by remember { mutableStateOf<String?>(null) }

    val today = StreakCalculator.todayEpochDay()

    fun isItemDone(lessonId: String, kind: PlanItemKind): Boolean {
        if (uiState.lessonProgressMap[lessonId]?.isCompleted == true) return true
        if (kind == PlanItemKind.REVIEW) {
            val card = reviewCards.firstOrNull { it.topicId == lessonId }
            if (card != null && card.lastReviewEpochDay == today) return true
        }
        return false
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp, vertical = 12.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    Brush.linearGradient(
                        listOf(
                            MaterialTheme.colorScheme.primaryContainer,
                            MaterialTheme.colorScheme.secondaryContainer
                        )
                    ),
                    RoundedCornerShape(20.dp)
                )
                .padding(16.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = "برنامه امروز",
                        style = MaterialTheme.typography.headlineSmall,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = "برنامه از روی وضعیت واقعی درس‌ها و مرورهای سررسیدشده ساخته می‌شود؛ اگر روزی از دست رفت، برنامه بازسازی می‌شود نه اینکه عقب‌ماندگی انباشته شود.",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
                Box(
                    modifier = Modifier
                        .size(52.dp)
                        .background(
                            MaterialTheme.colorScheme.surface.copy(alpha = 0.65f),
                            RoundedCornerShape(16.dp)
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.CalendarMonth,
                        contentDescription = null,
                        tint = resolveDynamicAccent(MaterialTheme.colorScheme.primary),
                        modifier = Modifier.size(28.dp)
                    )
                }
            }
        }
        Spacer(modifier = Modifier.height(12.dp))

        if (plan == null) {
            Box(
                modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                Box(
                    modifier = Modifier
                        .size(96.dp)
                        .background(
                            Brush.linearGradient(
                                listOf(
                                    MaterialTheme.colorScheme.primaryContainer,
                                    MaterialTheme.colorScheme.secondaryContainer
                                )
                            ),
                            RoundedCornerShape(28.dp)
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.CalendarMonth,
                        contentDescription = null,
                        tint = resolveDynamicAccent(MaterialTheme.colorScheme.primary),
                        modifier = Modifier.size(48.dp)
                    )
                }
            }
            Spacer(modifier = Modifier.height(12.dp))
            Text(
                text = "داده کافی نیست",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = "برای امروز هنوز برنامه‌ای ساخته نشده است. چند دقیقه وقت داری؟",
                style = MaterialTheme.typography.bodyMedium
            )
            Spacer(modifier = Modifier.height(12.dp))
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                listOf(30, 60, 90, 120).forEach { minutes ->
                    FilterChip(
                        selected = selectedMinutes == minutes,
                        onClick = { selectedMinutes = minutes },
                        label = { Text(text = BidiFormatter.applyPreferredDigits("$minutes دقیقه")) }
                    )
                }
            }
            Spacer(modifier = Modifier.height(12.dp))
            Button(
                onClick = { viewModel.buildAdaptiveDailyPlan(selectedMinutes) },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(text = "ساختن برنامه امروز")
            }
        } else {
            val items = plan?.items ?: emptyList()
            val doneCount = items.count { isItemDone(it.lessonId, it.kind) }
            val totalMinutes = items.sumOf { it.minutes }
            Text(
                text = BidiFormatter.applyPreferredDigits(
                    "$doneCount از ${items.size} آیتم انجام شده — مجموع $totalMinutes دقیقه"
                ),
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(8.dp))
            LinearProgressIndicator(
                progress = { if (items.isEmpty()) 0f else doneCount.toFloat() / items.size.toFloat() },
                modifier = Modifier.fillMaxWidth(),
                color = resolveDynamicAccent(MaterialTheme.colorScheme.primary),
                trackColor = MaterialTheme.colorScheme.primaryContainer
            )
            Spacer(modifier = Modifier.height(12.dp))
            if (items.isEmpty()) {
                Box(
                    modifier = Modifier.fillMaxWidth(),
                    contentAlignment = Alignment.Center
                ) {
                    Box(
                        modifier = Modifier
                            .size(72.dp)
                            .background(
                                Brush.linearGradient(
                                    listOf(
                                        MaterialTheme.colorScheme.primaryContainer,
                                        MaterialTheme.colorScheme.secondaryContainer
                                    )
                                ),
                                RoundedCornerShape(20.dp)
                            ),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.CheckCircle,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.primary,
                            modifier = Modifier.size(36.dp)
                        )
                    }
                }
                Spacer(modifier = Modifier.height(12.dp))
                Text(
                    text = "برای امروز آیتمی ساخته نشد؛ شاید همه مرورها انجام شده‌اند یا وقتی برای درس تازه نمانده است.",
                    style = MaterialTheme.typography.bodyMedium
                )
            } else {
                LazyColumn(
                    modifier = Modifier.weight(1f),
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    items(items, key = { it.lessonId + it.kind.name }) { item ->
                        val lesson = uiState.allLessons.firstOrNull { it.id == item.lessonId }
                        val title = lesson?.title
                            ?: SubjectCatalog.getSubjectById(item.subjectId).title
                        val done = isItemDone(item.lessonId, item.kind)
                        Card(
                            shape = RoundedCornerShape(16.dp),
                            colors = CardDefaults.cardColors(
                                containerColor = if (done) MaterialTheme.colorScheme.primaryContainer
                                else MaterialTheme.colorScheme.surfaceVariant
                            ),
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable(enabled = lesson != null) {
                                    lesson?.let { onOpenLesson(it.id) }
                                }
                        ) {
                            Column(modifier = Modifier.padding(12.dp)) {
                                Box(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(4.dp)
                                        .background(
                                            Brush.linearGradient(
                                                listOf(
                                                    MaterialTheme.colorScheme.primary.copy(alpha = 0.35f),
                                                    MaterialTheme.colorScheme.tertiary.copy(alpha = 0.35f)
                                                )
                                            ),
                                            RoundedCornerShape(2.dp)
                                        )
                                )
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween
                                ) {
                                    Text(
                                        text = title,
                                        style = MaterialTheme.typography.titleMedium,
                                        fontWeight = FontWeight.Bold,
                                        modifier = Modifier.weight(1f)
                                    )
                                    Text(
                                        text = BidiFormatter.applyPreferredDigits("${item.minutes} دقیقه"),
                                        style = MaterialTheme.typography.bodySmall,
                                        color = MaterialTheme.colorScheme.onSurfaceVariant
                                    )
                                }
                                Text(
                                    text = when (item.kind) {
                                        PlanItemKind.REVIEW -> "مرور"
                                        PlanItemKind.LEARN -> "یادگیری تازه"
                                        PlanItemKind.TEST -> "آزمون"
                                    } + if (done) " — انجام شد" else "",
                                    style = MaterialTheme.typography.bodySmall,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                                Text(
                                    text = item.reason,
                                    style = MaterialTheme.typography.bodyMedium
                                )
                            }
                        }
                    }
                }
            }
            Spacer(modifier = Modifier.height(12.dp))
            replanMessage?.let { message ->
                Text(
                    text = message,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.primary
                )
                Spacer(modifier = Modifier.height(8.dp))
            }
            OutlinedButton(
                onClick = {
                    val replanned = viewModel.replanAdaptiveAfterMissedDays(totalMinutes)
                    replanMessage = if (replanned == null) {
                        "موتور تطبیقی وصل نیست."
                    } else if (replanned.carriedOver.isEmpty()) {
                        "همه آیتم‌های جاافتاده در برنامه امروز جا شدند."
                    } else {
                        BidiFormatter.applyPreferredDigits(
                            "${replanned.carriedOver.size} آیتم در امروز جا نشد و به فردا می‌رود."
                        )
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(text = "بازسازی برنامه (آیتم‌های جاافتاده روزهای قبل)")
            }
        }
    }
}
