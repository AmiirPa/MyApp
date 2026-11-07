package com.example.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.BookmarkBorder
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Folder
import androidx.compose.material.icons.filled.MenuBook
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
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
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.curriculum.SubjectCatalog
import com.example.data.curriculum.ZeroLesson
import com.example.data.curriculum.normalizedSubjectId
import com.example.data.curriculum.officialTextbookReference
import com.example.ui.KonkoorUiState

/**
 * بخش اختصاصی دسترسی سریع به مباحث و درس‌های نشان‌شده (علاقه‌مندی‌ها)
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SavedItemsModalSheet(
    uiState: KonkoorUiState,
    onOpenLesson: (String) -> Unit,
    onToggleBookmark: (ZeroLesson) -> Unit,
    onDismiss: () -> Unit
) {
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)

    ModalBottomSheet(
        onDismissRequest = onDismiss,
        sheetState = sheetState,
        containerColor = MaterialTheme.colorScheme.surface,
        modifier = Modifier.testTag("saved_items_modal_sheet")
    ) {
        CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl) {
            SavedItemsContent(
                uiState = uiState,
                onOpenLesson = { lessonId ->
                    onDismiss()
                    onOpenLesson(lessonId)
                },
                onToggleBookmark = onToggleBookmark,
                onClose = onDismiss,
                isSheet = true
            )
        }
    }
}

/**
 * کارت اختصاصی و پیشرفته مباحث نشان‌شده برای تعبیه در صفحه اصلی
 */
@Composable
fun SavedItemsSectionCard(
    uiState: KonkoorUiState,
    onOpenLesson: (String) -> Unit,
    onToggleBookmark: (ZeroLesson) -> Unit,
    modifier: Modifier = Modifier
) {
    val savedLessons = uiState.bookmarkedLessons
    val savedCountFa = BidiFormatter.toPersianDigits(savedLessons.size.toString())

    CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl) {
        Card(
            modifier = modifier
                .fillMaxWidth()
                .testTag("saved_items_section_card"),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surface
            ),
            border = BorderStroke(1.dp, Color(0xFFD97706).copy(alpha = 0.5f)),
            elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(12.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                // Header
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Surface(
                            color = Color(0xFFD97706).copy(alpha = 0.15f),
                            shape = RoundedCornerShape(10.dp),
                            modifier = Modifier.size(36.dp)
                        ) {
                            Box(contentAlignment = Alignment.Center) {
                                Icon(
                                    imageVector = Icons.Default.Bookmark,
                                    contentDescription = null,
                                    tint = Color(0xFFD97706),
                                    modifier = Modifier.size(20.dp)
                                )
                            }
                        }
                        Spacer(modifier = Modifier.width(10.dp))
                        Column {
                            Text(
                                text = "⭐ مباحث و درس‌های نشان‌شده من",
                                style = MaterialTheme.typography.titleSmall,
                                fontWeight = FontWeight.Black,
                                color = MaterialTheme.colorScheme.onSurface
                            )
                            Text(
                                text = "$savedCountFa مبحث برگزیده برای مرور سریع",
                                style = MaterialTheme.typography.labelSmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    }
                }

                if (savedLessons.isEmpty()) {
                    EmptySavedItemsNotice()
                } else {
                    // آخرین ۳ درس نشان‌شده را به صورت فشرده نشان می‌دهد
                    Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                        savedLessons.take(3).forEach { lesson ->
                            val sub = SubjectCatalog.getSubjectById(lesson.normalizedSubjectId())
                            SavedLessonItemRow(
                                lesson = lesson,
                                subjectColor = sub.color,
                                subjectTitle = sub.shortTitle,
                                isCompleted = uiState.lessonProgressMap[lesson.id]?.isCompleted == true,
                                userNote = uiState.lessonProgressMap[lesson.id]?.userNote ?: "",
                                onClick = { onOpenLesson(lesson.id) },
                                onRemove = { onToggleBookmark(lesson) }
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun SavedItemsContent(
    uiState: KonkoorUiState,
    onOpenLesson: (String) -> Unit,
    onToggleBookmark: (ZeroLesson) -> Unit,
    onClose: (() -> Unit)? = null,
    isSheet: Boolean = false,
    modifier: Modifier = Modifier
) {
    val allSavedLessons = uiState.bookmarkedLessons
    var searchQuery by remember { mutableStateOf("") }
    var selectedSubjectId by remember { mutableStateOf("all") }

    // Subjects that have at least one bookmarked lesson
    val subjectsWithBookmarks = remember(allSavedLessons) {
        val subIds = allSavedLessons.map { it.normalizedSubjectId() }.distinct()
        SubjectCatalog.subjects.filter { it.id in subIds }
    }

    val filteredSavedLessons = remember(allSavedLessons, searchQuery, selectedSubjectId) {
        val q = searchQuery.trim()
        allSavedLessons.filter { lesson ->
            val matchSubject = selectedSubjectId == "all" || lesson.normalizedSubjectId() == selectedSubjectId
            val matchQuery = q.isEmpty() ||
                lesson.title.contains(q, ignoreCase = true) ||
                lesson.subtitle.contains(q, ignoreCase = true) ||
                lesson.officialTextbookReference().contains(q, ignoreCase = true)
            matchSubject && matchQuery
        }
    }

    val savedCountFa = BidiFormatter.toPersianDigits(allSavedLessons.size.toString())

    Column(
        modifier = modifier
            .fillMaxWidth()
            .then(if (isSheet) Modifier.height(600.dp) else Modifier.fillMaxSize())
            .padding(horizontal = 14.dp, vertical = 6.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        // Top Header
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Surface(
                    color = Color(0xFFD97706),
                    shape = RoundedCornerShape(10.dp),
                    modifier = Modifier.size(38.dp)
                ) {
                    Box(contentAlignment = Alignment.Center) {
                        Icon(
                            imageVector = Icons.Default.Bookmark,
                            contentDescription = null,
                            tint = Color.White,
                            modifier = Modifier.size(22.dp)
                        )
                    }
                }
                Spacer(modifier = Modifier.width(10.dp))
                Column {
                    Text(
                        text = "⭐ مباحث و درس‌های نشان‌شده (علاقه‌مندی‌ها)",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Black,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    Text(
                        text = "$savedCountFa مبحث برای مرور سریع قبل از آزمون و تست‌زنی",
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
            if (onClose != null) {
                IconButton(onClick = onClose) {
                    Icon(imageVector = Icons.Default.Close, contentDescription = "بستن")
                }
            }
        }

        if (allSavedLessons.isNotEmpty()) {
            // Search Box
            OutlinedTextField(
                value = searchQuery,
                onValueChange = { searchQuery = it },
                placeholder = {
                    Text(
                        text = "🔍 جستجو در مباحث نشان‌شده...",
                        style = MaterialTheme.typography.bodySmall
                    )
                },
                singleLine = true,
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("saved_items_search_input")
            )

            // Subject Filter Chips (Only shown if user has saved items in multiple subjects)
            if (subjectsWithBookmarks.size > 1) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .horizontalScroll(rememberScrollState()),
                    horizontalArrangement = Arrangement.spacedBy(6.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    FilterChip(
                        selected = selectedSubjectId == "all",
                        onClick = { selectedSubjectId = "all" },
                        label = {
                            Text(
                                text = "همه (${BidiFormatter.toPersianDigits(allSavedLessons.size.toString())})",
                                style = MaterialTheme.typography.labelSmall,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    )
                    subjectsWithBookmarks.forEach { subject ->
                        val countInSubject = allSavedLessons.count { it.normalizedSubjectId() == subject.id }
                        val isSelected = selectedSubjectId == subject.id
                        FilterChip(
                            selected = isSelected,
                            onClick = { selectedSubjectId = subject.id },
                            colors = FilterChipDefaults.filterChipColors(
                                selectedContainerColor = subject.color.copy(alpha = 0.18f),
                                selectedLabelColor = subject.color
                            ),
                            border = if (isSelected) BorderStroke(1.dp, subject.color) else null,
                            label = {
                                Text(
                                    text = "${subject.shortTitle} (${BidiFormatter.toPersianDigits(countInSubject.toString())})",
                                    style = MaterialTheme.typography.labelSmall,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                        )
                    }
                }
            }
        }

        // List or Empty View
        if (allSavedLessons.isEmpty()) {
            EmptySavedItemsNotice(modifier = Modifier.padding(top = 16.dp))
        } else if (filteredSavedLessons.isEmpty()) {
            Surface(
                shape = RoundedCornerShape(12.dp),
                color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 12.dp)
            ) {
                Text(
                    text = "هیچ مبحثی مطابق با جستجوی شما در نشان‌شده‌ها پیدا نشد.",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    modifier = Modifier.padding(16.dp),
                    textAlign = TextAlign.Center
                )
            }
        } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .testTag("saved_items_list"),
                contentPadding = PaddingValues(top = 4.dp, bottom = 48.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(filteredSavedLessons, key = { it.id }) { lesson ->
                    val sub = SubjectCatalog.getSubjectById(lesson.normalizedSubjectId())
                    val prog = uiState.lessonProgressMap[lesson.id]
                    SavedLessonItemRow(
                        lesson = lesson,
                        subjectColor = sub.color,
                        subjectTitle = sub.shortTitle,
                        isCompleted = prog?.isCompleted == true,
                        userNote = prog?.userNote ?: "",
                        onClick = { onOpenLesson(lesson.id) },
                        onRemove = { onToggleBookmark(lesson) }
                    )
                }
            }
        }
    }
}

@Composable
private fun SavedLessonItemRow(
    lesson: ZeroLesson,
    subjectColor: Color,
    subjectTitle: String,
    isCompleted: Boolean,
    userNote: String,
    onClick: () -> Unit,
    onRemove: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(14.dp))
            .clickable(onClick = onClick)
            .testTag("saved_lesson_card_${lesson.id}"),
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceContainerHigh),
        border = BorderStroke(0.7.dp, subjectColor.copy(alpha = 0.35f))
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            verticalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            // Header: Subject Badge + Official Book Reference + Bookmark Toggle
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
                        color = subjectColor.copy(alpha = 0.15f),
                        shape = RoundedCornerShape(6.dp)
                    ) {
                        Text(
                            text = subjectTitle,
                            style = MaterialTheme.typography.labelSmall,
                            fontWeight = FontWeight.Bold,
                            color = subjectColor,
                            modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                        )
                    }
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = BidiFormatter.formatMixedPersianText(lesson.officialTextbookReference()),
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }

                Row(verticalAlignment = Alignment.CenterVertically) {
                    if (isCompleted) {
                        Surface(
                            color = MaterialTheme.colorScheme.primaryContainer,
                            shape = RoundedCornerShape(6.dp)
                        ) {
                            Text(
                                text = "✓ مطالعه‌شده",
                                style = MaterialTheme.typography.labelSmall,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.primary,
                                modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                            )
                        }
                        Spacer(modifier = Modifier.width(4.dp))
                    }
                    IconButton(
                        onClick = onRemove,
                        modifier = Modifier.size(32.dp).testTag("remove_saved_lesson_${lesson.id}")
                    ) {
                        Icon(
                            imageVector = Icons.Default.Bookmark,
                            contentDescription = "حذف از نشان‌شده‌ها",
                            tint = Color(0xFFD97706),
                            modifier = Modifier.size(18.dp)
                        )
                    }
                }
            }

            // Title & Subtitle
            Text(
                text = BidiFormatter.formatMixedPersianText(lesson.title),
                style = MaterialTheme.typography.titleSmall,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface
            )
            if (lesson.subtitle.isNotBlank()) {
                Text(
                    text = BidiFormatter.formatMixedPersianText(lesson.subtitle),
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
            }

            // User Note preview if student saved personal notes for this lesson
            if (userNote.isNotBlank()) {
                Surface(
                    shape = RoundedCornerShape(8.dp),
                    color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = "📝 یادداشت من: $userNote",
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                    )
                }
            }

            // Action row
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "${lesson.steps.size} گام تدریس خط‌به‌خط",
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Surface(
                    shape = RoundedCornerShape(8.dp),
                    color = subjectColor.copy(alpha = 0.12f)
                ) {
                    Row(
                        modifier = Modifier.padding(horizontal = 10.dp, vertical = 5.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "ورود به مبحث ◀",
                            style = MaterialTheme.typography.labelSmall,
                            fontWeight = FontWeight.Bold,
                            color = subjectColor
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun EmptySavedItemsNotice(modifier: Modifier = Modifier) {
    Surface(
        shape = RoundedCornerShape(16.dp),
        color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.45f),
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.4f)),
        modifier = modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Surface(
                color = Color(0xFFD97706).copy(alpha = 0.15f),
                shape = CircleShape,
                modifier = Modifier.size(54.dp)
            ) {
                Box(contentAlignment = Alignment.Center) {
                    Icon(
                        imageVector = Icons.Default.BookmarkBorder,
                        contentDescription = null,
                        tint = Color(0xFFD97706),
                        modifier = Modifier.size(28.dp)
                    )
                }
            }

            Text(
                text = "هنوز مبحثی را به علاقه‌مندی‌ها اضافه نکرده‌اید",
                style = MaterialTheme.typography.titleSmall,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface,
                textAlign = TextAlign.Center
            )

            Text(
                text = "برای دسترسی سریع به مباحث دشوار، فرمول‌های مهم یا درس‌هایی که می‌خواهید قبل از آزمون دوباره مرور کنید، روی آیکون نشان (🔖 یا ⭐) در کنار هر درس بزنید.",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                textAlign = TextAlign.Center,
                lineHeight = 20.sp
            )
        }
    }
}
