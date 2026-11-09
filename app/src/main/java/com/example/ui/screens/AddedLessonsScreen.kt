package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.curriculum.SubjectCatalog
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
 *
 * مینیمال: در نگاه اول هر ردیف فقط تیتر درس است — بدون چیپ، بدون بج،
 * بدون نوار پیشرفت و بدون توضیح اضافه.
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
        // سربرگ جمع‌وجور: تیتر + شمارش کل در یک ردیف.
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 12.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "فهرست همه درس‌ها",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface
            )
            // کارت شمارش: گرادیان ملایم primary + عدد بزرگ و تمیز — همان
            // «N درس» قبلی، فقط به‌شکل کارت؛ متن تازه‌ای اضافه نشده است.
            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(12.dp))
                    .background(
                        brush = Brush.linearGradient(
                            listOf(
                                MaterialTheme.colorScheme.primary.copy(alpha = 0.16f),
                                MaterialTheme.colorScheme.primary.copy(alpha = 0.05f)
                            )
                        ),
                        shape = RoundedCornerShape(12.dp)
                    )
                    .padding(horizontal = 12.dp, vertical = 4.dp),
                contentAlignment = Alignment.Center
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    Text(
                        text = BidiFormatter.toPersianDigits(totalCount.toString()),
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary
                    )
                    Text(
                        text = "درس",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary
                    )
                }
            }
        }

        OutlinedTextField(
            value = query,
            onValueChange = { query = it },
            label = { Text("جستجو در عنوان درس یا موضوع…") },
            singleLine = true,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 12.dp)
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
                modifier = Modifier.fillMaxSize()
            ) {
                grouped.forEach { (subjectId, lessons) ->
                    val subjectTitle = subjectTitleById[subjectId] ?: subjectId
                    // رنگ موضوع از SubjectCatalog — همان رنگی که برنامه
                    // برای این موضوع استفاده می‌کند.
                    val subjectDotColor = SubjectCatalog.getSubjectById(subjectId).color
                    item(key = "header-$subjectId") {
                        // سربرگ گروه: گرادیان ملایم رنگ همان موضوع (نه رنگ تخت) —
                        // متن و شمارش گروه دقیقاً همان قبلی، تک‌خطی.
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 16.dp, bottom = 4.dp)
                                .clip(RoundedCornerShape(10.dp))
                                .background(
                                    brush = Brush.horizontalGradient(
                                        listOf(
                                            subjectDotColor.copy(alpha = 0.20f),
                                            subjectDotColor.copy(alpha = 0.05f),
                                            subjectDotColor.copy(alpha = 0.12f)
                                        )
                                    ),
                                    shape = RoundedCornerShape(10.dp)
                                )
                                .padding(horizontal = 12.dp, vertical = 8.dp)
                        ) {
                            Text(
                                text = subjectTitle + " (" +
                                    BidiFormatter.toPersianDigits(lessons.size.toString()) +
                                    " درس)",
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.primary,
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis
                            )
                        }
                    }
                    items(lessons, key = { it.id }) { lesson ->
                        // هاله لمس: فقط وقتی ردیف لمس/فشرده می‌شود، یک گرادیان
                        // خیلی ملایم از رنگ موضوع پشت ردیف ظاهر می‌شود.
                        val rowInteractionSource = remember { MutableInteractionSource() }
                        val isRowPressed by rowInteractionSource.collectIsPressedAsState()
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .testTag("added_lesson_${lesson.id}")
                        ) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clip(RoundedCornerShape(8.dp))
                                    .background(
                                        brush = if (isRowPressed) {
                                            Brush.horizontalGradient(
                                                listOf(
                                                    subjectDotColor.copy(alpha = 0.14f),
                                                    subjectDotColor.copy(alpha = 0.03f)
                                                )
                                            )
                                        } else {
                                            Brush.horizontalGradient(
                                                listOf(Color.Transparent, Color.Transparent)
                                            )
                                        },
                                        shape = RoundedCornerShape(8.dp)
                                    )
                                    .clickable(
                                        interactionSource = rowInteractionSource,
                                        indication = null,
                                        onClick = { onOpenLesson(lesson.id) }
                                    )
                                    .padding(vertical = 12.dp, horizontal = 8.dp),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(10.dp)
                            ) {
                                // نقطه رنگی کوچکِ موضوعِ درس — تیتر تک‌خطی می‌ماند.
                                Box(
                                    modifier = Modifier
                                        .size(8.dp)
                                        .clip(CircleShape)
                                        .background(subjectDotColor)
                                )
                                Text(
                                    text = lesson.title,
                                    fontSize = 15.sp,
                                    color = MaterialTheme.colorScheme.onSurface,
                                    maxLines = 1,
                                    overflow = TextOverflow.Ellipsis,
                                    modifier = Modifier.weight(1f)
                                )
                            }
                            HorizontalDivider(
                                color = MaterialTheme.colorScheme.outlineVariant,
                                thickness = 0.5.dp
                            )
                        }
                    }
                }
            }
        }
    }
}
