package com.example.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.curriculum.ZeroLesson
import com.example.data.curriculum.normalizedSubjectId
import com.example.ui.KonkoorUiState
import com.example.ui.components.BidiFormatter

/**
 * فهرست همه درس‌های برنامه — برای اینکه کاربر خودش ببیند چه درس‌هایی
 * اضافه شده و با زدن روی هر درس، همان درس باز شود.
 *
 * فهرست از `uiState.allLessons` ساخته می‌شود، یعنی دقیقاً همان درس‌هایی که
 * در برنامه وصل‌اند (نه یک فهرست جدا که بتواند با واقعیت فرق کند). درس‌ها
 * بر اساس درس (موضوع) گروه‌بندی می‌شوند و تعداد هر گروه هم نشان داده می‌شود.
 */
@Composable
fun AddedLessonsScreen(
    uiState: KonkoorUiState,
    onOpenLesson: (String) -> Unit
) {
    var query by remember { mutableStateOf("") }

    val subjectTitleById = remember(uiState.subjects) {
        uiState.subjects.associate { it.id to it.title }
    }

    // گروه‌بندی درس‌ها بر اساس موضوع (با شناسه نرمال‌شده، مثل بقیه برنامه).
    val grouped: List<Pair<String, List<ZeroLesson>>> = remember(uiState.allLessons, query) {
        val q = query.trim()
        val filtered = if (q.isEmpty()) {
            uiState.allLessons
        } else {
            uiState.allLessons.filter { lesson ->
                lesson.title.contains(q) ||
                    (subjectTitleById[lesson.normalizedSubjectId()]?.contains(q) == true)
            }
        }
        filtered.groupBy { it.normalizedSubjectId() }
            .toList()
            .sortedBy { (subjectId, _) ->
                subjectTitleById[subjectId] ?: subjectId
            }
    }

    val totalCount = uiState.allLessons.size

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 20.dp, vertical = 12.dp)
            .testTag("added_lessons_root")
    ) {
        Text(
            text = "فهرست همه درس‌ها",
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onSurface
        )
        Text(
            text = "این‌ها دقیقاً همان درس‌هایی‌اند که در برنامه هست؛ روی هر درس بزن تا باز شود.",
            fontSize = 13.sp,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            modifier = Modifier.padding(top = 4.dp, bottom = 8.dp)
        )
        Text(
            text = "مجموع: " + BidiFormatter.toPersianDigits(totalCount.toString()) + " درس",
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.primary,
            modifier = Modifier.padding(bottom = 10.dp)
        )

        OutlinedTextField(
            value = query,
            onValueChange = { query = it },
            label = { Text("جستجو در عنوان درس یا موضوع…") },
            singleLine = true,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 10.dp)
                .testTag("added_lessons_search")
        )

        if (grouped.isEmpty()) {
            Text(
                text = if (totalCount == 0) "هنوز درسی بارگذاری نشده است." else "درسی با این جستجو پیدا نشد.",
                fontSize = 14.sp,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.padding(top = 16.dp)
            )
        } else {
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                grouped.forEach { (subjectId, lessons) ->
                    val subjectTitle = subjectTitleById[subjectId] ?: subjectId
                    item(key = "header-$subjectId") {
                        Text(
                            text = subjectTitle + " (" +
                                BidiFormatter.toPersianDigits(lessons.size.toString()) +
                                " درس)",
                            fontSize = 15.sp,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.primary,
                            modifier = Modifier.padding(top = 8.dp, bottom = 2.dp)
                        )
                    }
                    items(lessons, key = { it.id }) { lesson ->
                        Surface(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable { onOpenLesson(lesson.id) }
                                .testTag("added_lesson_${lesson.id}"),
                            shape = RoundedCornerShape(12.dp),
                            color = MaterialTheme.colorScheme.surface,
                            border = BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant)
                        ) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(horizontal = 14.dp, vertical = 10.dp),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text(
                                    text = lesson.title,
                                    fontSize = 14.sp,
                                    color = MaterialTheme.colorScheme.onSurface,
                                    modifier = Modifier.weight(1f)
                                )
                                Text(
                                    text = BidiFormatter.toPersianDigits(
                                        lesson.estimatedMinutes.toString()
                                    ) + " دقیقه",
                                    fontSize = 12.sp,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                                    modifier = Modifier.padding(start = 8.dp)
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}
