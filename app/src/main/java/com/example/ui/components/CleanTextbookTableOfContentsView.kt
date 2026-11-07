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
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.BookmarkBorder
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.ExpandLess
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.material.icons.filled.MenuBook
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
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
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import com.example.data.curriculum.ZeroLesson
import com.example.data.curriculum.officialTextbookReference

/**
 * فهرست یکپارچه، مرتب و خط‌به‌خط دروس هر پایه (۱، ۲، ۳ تا آخر)
 * بدون هیچ زیرمجموعه پنهان یا تو در توی اضافی تا تمام درس‌ها یکجا و واضح دیده شوند.
 */
@Composable
fun CleanTextbookTableOfContentsCard(
    chapterTitle: String,
    lessons: List<ZeroLesson>,
    modifier: Modifier = Modifier,
    accentColor: Color = Color(0xFF0284C7),
    bookmarkedLessonIds: Set<String> = emptySet(),
    completedLessonIds: Set<String> = emptySet(),
    onToggleBookmark: ((ZeroLesson) -> Unit)? = null,
    onSelectLesson: (ZeroLesson) -> Unit
) {
    var isExpanded by remember { mutableStateOf(true) }
    val totalCountFa = BidiFormatter.toPersianDigits(lessons.size.toString())

    CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl) {
        Card(
            modifier = modifier
                .fillMaxWidth()
                .padding(vertical = 4.dp)
                .testTag("textbook_toc_card"),
            shape = RoundedCornerShape(14.dp),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surface
            ),
            border = BorderStroke(1.dp, accentColor.copy(alpha = 0.45f)),
            elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp),
                verticalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                // ۱. سربرگ پایه یا فصل
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(10.dp))
                        .background(accentColor.copy(alpha = 0.1f))
                        .clickable { isExpanded = !isExpanded }
                        .padding(horizontal = 12.dp, vertical = 10.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.weight(1f)
                    ) {
                        Surface(
                            color = MaterialTheme.colorScheme.primaryContainer,
                            shape = RoundedCornerShape(8.dp),
                            modifier = Modifier.size(28.dp)
                        ) {
                            Box(contentAlignment = Alignment.Center) {
                                Icon(
                                    imageVector = Icons.Default.MenuBook,
                                    contentDescription = null,
                                    tint = MaterialTheme.colorScheme.onPrimaryContainer,
                                    modifier = Modifier.size(16.dp)
                                )
                            }
                        }

                        Spacer(modifier = Modifier.width(10.dp))

                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                text = BidiFormatter.formatMixedPersianText(chapterTitle),
                                style = MaterialTheme.typography.titleSmall,
                                fontWeight = FontWeight.Black,
                                color = accentColor,
                                maxLines = 2,
                                overflow = TextOverflow.Ellipsis
                            )
                            Text(
                                text = "$totalCountFa درسنامه کامل به ترتیب فصل‌های کتاب درسی",
                                style = MaterialTheme.typography.labelSmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    }

                    Icon(
                        imageVector = if (isExpanded) Icons.Default.ExpandLess else Icons.Default.ExpandMore,
                        contentDescription = null,
                        tint = accentColor,
                        modifier = Modifier.size(22.dp)
                    )
                }

                // ۲. فهرست سرراست و شماره‌گذاری‌شده تمام دروس (۱ تا آخر بدون هیچ بخش پنهان)
                AnimatedVisibility(
                    visible = isExpanded,
                    enter = fadeIn() + expandVertically(),
                    exit = fadeOut() + shrinkVertically()
                ) {
                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        verticalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        lessons.forEachIndexed { idx, lesson ->
                            val itemNumFa = BidiFormatter.toPersianDigits((idx + 1).toString())
                            val isBookmarked = lesson.id in bookmarkedLessonIds
                            val isCompleted = lesson.id in completedLessonIds

                            TextbookTocRowItem(
                                lessonId = lesson.id,
                                itemNumberStr = itemNumFa,
                                titleText = BidiFormatter.formatMixedPersianText(lesson.title),
                                subtitleText = BidiFormatter.formatMixedPersianText(
                                    "${lesson.officialTextbookReference()} • ${lesson.subtitle}"
                                ),
                                accentColor = accentColor,
                                isBookmarked = isBookmarked,
                                isCompleted = isCompleted,
                                onToggleBookmark = if (onToggleBookmark != null) {
                                    { onToggleBookmark(lesson) }
                                } else null,
                                onClick = { onSelectLesson(lesson) }
                            )
                            if (idx < lessons.lastIndex) {
                                HorizontalDivider(
                                    thickness = 0.5.dp,
                                    color = MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.35f)
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun TextbookTocRowItem(
    lessonId: String = "",
    itemNumberStr: String,
    titleText: String,
    subtitleText: String,
    accentColor: Color,
    isBookmarked: Boolean = false,
    isCompleted: Boolean = false,
    onToggleBookmark: (() -> Unit)? = null,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(8.dp))
            .clickable { onClick() }
            .then(
                if (lessonId.isNotBlank()) Modifier.testTag("lesson_card_$lessonId") else Modifier
            )
            .padding(vertical = 7.dp, horizontal = 6.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // شماره ردیف درس یا تیک تکمیل
        Surface(
            shape = CircleShape,
            color = if (isCompleted) Color(0xFF16A34A).copy(alpha = 0.15f) else accentColor.copy(alpha = 0.12f),
            modifier = Modifier.size(30.dp)
        ) {
            Box(contentAlignment = Alignment.Center) {
                if (isCompleted) {
                    Icon(
                        imageVector = Icons.Default.CheckCircle,
                        contentDescription = "تکمیل‌شده",
                        tint = Color(0xFF16A34A),
                        modifier = Modifier.size(18.dp)
                    )
                } else {
                    Text(
                        text = itemNumberStr,
                        style = MaterialTheme.typography.labelMedium,
                        fontWeight = FontWeight.Black,
                        color = accentColor
                    )
                }
            }
        }

        Spacer(modifier = Modifier.width(10.dp))

        // عنوان و زیرعنوان درس
        Column(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.spacedBy(2.dp)
        ) {
            BiDiText(
                text = titleText,
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface,
                maxLines = 2,
                isUnderlineEnabled = false,
                isHighlighterEnabled = false
            )
            if (subtitleText.isNotBlank()) {
                BiDiText(
                    text = subtitleText,
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    maxLines = 2,
                    isUnderlineEnabled = false,
                    isHighlighterEnabled = false
                )
            }
        }

        if (onToggleBookmark != null) {
            IconButton(
                onClick = onToggleBookmark,
                modifier = Modifier.size(32.dp)
            ) {
                Icon(
                    imageVector = if (isBookmarked) Icons.Default.Bookmark else Icons.Default.BookmarkBorder,
                    contentDescription = "نشان کردن درس",
                    tint = if (isBookmarked) Color(0xFFD97706) else MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.6f),
                    modifier = Modifier.size(18.dp)
                )
            }
        }

        Surface(
            shape = RoundedCornerShape(6.dp),
            color = accentColor.copy(alpha = 0.1f)
        ) {
            Text(
                text = "مطالعه ◀",
                style = MaterialTheme.typography.labelSmall,
                fontWeight = FontWeight.Bold,
                color = accentColor,
                modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
            )
        }
    }
}
