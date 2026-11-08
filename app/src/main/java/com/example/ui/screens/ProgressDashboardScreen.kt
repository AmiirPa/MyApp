package com.example.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.ui.KonkoorViewModel
import com.example.ui.components.BidiFormatter

/**
 * صفحه گزارش پیشرفت (§۹ الحاقیه).
 *
 * هر سنجه یا مقدار واقعی‌اش را نشان می‌دهد یا «داده کافی نیست» با دلیلش
 * را. هیچ نمودار خالی و هیچ عدد ساختگی اینجا نیست؛ مثلاً روند آزمون‌ها
 * فقط وقتی نشان داده می‌شود که حداقل دو آزمون ثبت شده باشد.
 */
@Composable
fun ProgressDashboardScreen(
    viewModel: KonkoorViewModel
) {
    val report by viewModel.adaptiveReport.collectAsStateWithLifecycle()
    val focusMinutes by viewModel.focusMinutesToday.collectAsStateWithLifecycle()
    val focusSessions by viewModel.focusSessionsToday.collectAsStateWithLifecycle()
    val focusInterruptions by viewModel.focusInterruptionsToday.collectAsStateWithLifecycle()
    val achievements by viewModel.achievements.collectAsStateWithLifecycle()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp, vertical = 12.dp)
    ) {
        Text(
            text = "گزارش پیشرفت",
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Bold
        )
        Text(
            text = "این گزارش فقط از کار واقعی تو ساخته می‌شود؛ هر جا داده کم باشد، به‌جای عدد حدسی «داده کافی نیست» می‌بینی.",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        Spacer(modifier = Modifier.height(12.dp))

        // تمرکز امروز (§۸): دقیقه‌ها و جلسه‌های واقعی ثبت‌شده با تایمر؛
        // اگر امروز جلسه‌ای ثبت نشده، «داده کافی نیست» نشان داده می‌شود.
        Card(
            shape = RoundedCornerShape(12.dp),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surfaceVariant
            ),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(modifier = Modifier.padding(12.dp)) {
                Text(
                    text = "تمرکز امروز",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "فقط ثانیه‌های واقعی تمرکز با تایمر شمرده می‌شوند؛ استراحت و مکث حساب نمی‌شوند.",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Spacer(modifier = Modifier.height(4.dp))
                if (focusSessions == 0) {
                    Text(
                        text = "داده کافی نیست",
                        style = MaterialTheme.typography.bodyLarge,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = "هنوز امروز جلسه تمرکزی ثبت نشده است؛ وقتی تایمر یک بازه را کامل کند یا نیمه‌کاره رها شود، اینجا ثبت می‌شود.",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                } else {
                    Text(
                        text = BidiFormatter.applyPreferredDigits(
                            "$focusMinutes دقیقه تمرکز • $focusSessions جلسه • $focusInterruptions وقفه"
                        ),
                        style = MaterialTheme.typography.bodyLarge,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary
                    )
                }
            }
        }
        Spacer(modifier = Modifier.height(12.dp))

        // دستاوردها (§۱۰): فقط از کار واقعی به دست می‌آیند و هر بار از شواهد
        // محاسبه می‌شوند؛ امتیاز و مقایسه با دیگران اینجا نیست و هیچ متنی
        // بابت عقب‌افتادگی سرزنش نمی‌کند.
        Card(
            shape = RoundedCornerShape(12.dp),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surfaceVariant
            ),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(modifier = Modifier.padding(12.dp)) {
                Text(
                    text = "دستاوردها",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "این‌ها فقط از کار واقعی تو به دست می‌آیند؛ با باز کردن برنامه یا عدد ساختگی به دست نمی‌آیند.",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Spacer(modifier = Modifier.height(4.dp))
                if (achievements.earned.isEmpty()) {
                    Text(
                        text = "هنوز دستاوردی نگرفتی",
                        style = MaterialTheme.typography.bodyLarge,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = "اولین قدم ساده است: یک درس را کامل کن.",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                } else {
                    achievements.earned.forEach { result ->
                        Text(
                            text = "✓ ${result.definition.title}",
                            style = MaterialTheme.typography.bodyLarge,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.primary
                        )
                        Text(
                            text = result.definition.description,
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                    }
                }
                val next = achievements.nearestUnearned
                if (next != null) {
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "دستاورد بعدی: ${next.definition.title}",
                        style = MaterialTheme.typography.titleSmall,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = next.definition.description,
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Text(
                        text = BidiFormatter.applyPreferredDigits(
                            "پیشرفت: ${next.progressText()}"
                        ),
                        style = MaterialTheme.typography.bodyMedium,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary
                    )
                } else if (achievements.earned.isNotEmpty()) {
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "همه دستاوردهای این فهرست را گرفتی؛ همین‌طور ادامه بده.",
                        style = MaterialTheme.typography.bodyMedium,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary
                    )
                }
            }
        }
        Spacer(modifier = Modifier.height(12.dp))

        if (report == null) {
            Text(
                text = "داده کافی نیست",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = "هنوز داده‌ای برای گزارش نیست؛ بعد از چند روز مطالعه و یکی دو آزمون، گزارش اینجا ساخته می‌شود.",
                style = MaterialTheme.typography.bodyMedium
            )
        } else {
            val rows = AdaptiveUiLabels.AdaptiveReportRow.values()
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = androidx.compose.foundation.layout.Arrangement.spacedBy(10.dp)
            ) {
                items(rows) { row ->
                    val value = AdaptiveUiLabels.metricValueOrInsufficient(report!!, row)
                    Card(
                        shape = RoundedCornerShape(12.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = MaterialTheme.colorScheme.surfaceVariant
                        ),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Column(modifier = Modifier.padding(12.dp)) {
                            Text(
                                text = AdaptiveUiLabels.metricLabel(row),
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.Bold
                            )
                            Text(
                                text = AdaptiveUiLabels.metricDescription(row),
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            if (value != null) {
                                Text(
                                    text = BidiFormatter.applyPreferredDigits(value),
                                    style = MaterialTheme.typography.bodyLarge,
                                    fontWeight = FontWeight.Bold,
                                    color = MaterialTheme.colorScheme.primary
                                )
                            } else {
                                Text(
                                    text = "داده کافی نیست",
                                    style = MaterialTheme.typography.bodyLarge,
                                    fontWeight = FontWeight.Bold
                                )
                                val reason = AdaptiveUiLabels.rowInsufficientReason(report!!, row)
                                if (reason != null) {
                                    Text(
                                        text = reason,
                                        style = MaterialTheme.typography.bodySmall,
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
