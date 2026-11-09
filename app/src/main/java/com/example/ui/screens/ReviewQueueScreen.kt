package com.example.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.data.curriculum.SubjectCatalog
import com.example.ui.KonkoorUiState
import com.example.ui.KonkoorViewModel
import com.example.ui.components.BidiFormatter

/**
 * صفحه مرور امروز (§۵ الحاقیه — مرور فاصله‌دار).
 *
 * کارت‌ها از موتور می‌آیند: اول مبحث‌هایی که بیشتر غلط داشته‌اند، بعد
 * بقیه. «بلد بودم» کارت را به جعبه بعد می‌برد و «بلد نبودم» آن را به
 * جعبه اول برمی‌گرداند. ضربه روی کارت، درسش را باز می‌کند (اگر مبحث،
 * درس مشخصی باشد).
 */
@Composable
fun ReviewQueueScreen(
    viewModel: KonkoorViewModel,
    uiState: KonkoorUiState,
    onOpenLesson: (String) -> Unit
) {
    val queue by viewModel.adaptiveReviewQueue.collectAsStateWithLifecycle()
    val allCards by viewModel.adaptiveReviewCards.collectAsStateWithLifecycle()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp, vertical = 12.dp)
    ) {
        Text(
            text = "مرور امروز",
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Bold
        )
        Text(
            text = "مرور کوتاه، در فاصله زمانی درست، چیزی است که یادگیری را ماندگار می‌کند. اول مبحث‌هایی می‌آیند که بیشتر اشتباه داشته‌ای.",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        Spacer(modifier = Modifier.height(12.dp))

        when {
            allCards.isEmpty() -> {
                // حالت خالی: کارت نرم با رنگ scheme (متن‌ها همان قبلی).
                Card(
                    shape = RoundedCornerShape(20.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.6f)
                    ),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(
                            text = "داده کافی نیست",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = "هنوز کارتی برای مرور ساخته نشده است. وقتی درسی را کامل کنی یا آزمونی بدهی، کارت مرورش اینجا می‌آید.",
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
            }
            queue.isEmpty() -> {
                // حالت خالی: کارت نرم با رنگ scheme (متن‌ها همان قبلی).
                Card(
                    shape = RoundedCornerShape(20.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.6f)
                    ),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(
                            text = "مرورهای امروز تمام شد",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = "برای امروز کارت سررسیدشده دیگری نیست؛ فردا کارت‌های تازه می‌آیند.",
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
            }
            else -> {
                Text(
                    text = BidiFormatter.applyPreferredDigits("${queue.size} کارت برای امروز"),
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(8.dp))
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    items(queue, key = { it.itemId }) { card ->
                        val lesson = uiState.allLessons.firstOrNull { it.id == card.topicId }
                        val title = lesson?.title
                            ?: SubjectCatalog.getSubjectById(card.subjectId).title
                        Card(
                            shape = RoundedCornerShape(16.dp),
                            colors = CardDefaults.cardColors(
                                containerColor = MaterialTheme.colorScheme.surfaceVariant
                            ),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Column(modifier = Modifier.padding(12.dp)) {
                                Text(
                                    text = title,
                                    style = MaterialTheme.typography.titleMedium,
                                    fontWeight = FontWeight.Bold
                                )
                                Text(
                                    text = BidiFormatter.applyPreferredDigits(
                                        "جعبه ${card.leitnerBox} از ۵ — ${card.errorCount} بار اشتباه"
                                    ),
                                    style = MaterialTheme.typography.bodySmall,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                                Spacer(modifier = Modifier.height(8.dp))
                                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                                    Button(
                                        onClick = { viewModel.recordReviewAttempt(card, true) },
                                        colors = ButtonDefaults.buttonColors(
                                            containerColor = MaterialTheme.colorScheme.primary
                                        )
                                    ) {
                                        Text(text = "بلد بودم")
                                    }
                                    OutlinedButton(
                                        onClick = { viewModel.recordReviewAttempt(card, false) }
                                    ) {
                                        Text(text = "بلد نبودم")
                                    }
                                }
                                if (lesson != null) {
                                    Spacer(modifier = Modifier.height(8.dp))
                                    OutlinedButton(
                                        onClick = { onOpenLesson(lesson.id) }
                                    ) {
                                        Text(text = "باز کردن درس")
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
