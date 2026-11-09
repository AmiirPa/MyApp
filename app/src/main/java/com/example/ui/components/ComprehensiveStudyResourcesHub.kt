package com.example.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
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
import androidx.compose.material.icons.automirrored.filled.MenuBook
import androidx.compose.material.icons.filled.Assignment
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.ExpandLess
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Lightbulb
import androidx.compose.material.icons.filled.School
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.SecondaryTabRow
import androidx.compose.material3.Surface
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.AppTab

/**
 * مدل داده هر منبع آموزشی، تستی یا رسمی
 */
data class ComprehensiveResourceItem(
    val id: String,
    val categoryTab: Int, // 0: فرهنگیان رسمی, 1: کنکور انسانی رسمی, 2: کتب تستی رتبه‌ساز, 3: امتحانات نهایی, 4: مصاحبه و گزینش
    val title: String,
    val authorOrPublisher: String,
    val coefficientOrWeight: String, // ضریب یا بارم
    val gradeOrTarget: String,
    val badgeColor: Color,
    val summary: String,
    val exactChaptersOrTopics: List<String>,
    val studyStrategy: String,
    val goldenTip: String,
    val authorizedEditionYear: String = "چاپ رسمی مصوب سنجش"
)

// دیتاست منابع یک‌بار برای کل فرآیند ساخته می‌شود (lazy): پیش‌تر با remember
// ساخته می‌شد و با هر بار خروج از ترکیب — مثلاً تعویض تب مشاوره و برگشتن به
// «کتابخانه و منابع» — از نو ساخته می‌شد.
private val comprehensiveResourceDataset: List<ComprehensiveResourceItem> by lazy {
    buildComprehensiveResourceDataset()
}

private val resourceCategoryTabs = listOf(
    "🎓 منابع فرهنگیان",
    "📖 دروس کنکور",
    "🏆 کتب تستی رتبه‌ها",
    "📝 امتحانات نهایی",
    "👥 مصاحبه و گزینش"
)

/**
 * مرجع و بانک جامع منابع کنکور انسانی و دانشگاه فرهنگیان ۱۴۰۶
 */
@OptIn(ExperimentalLayoutApi::class, ExperimentalMaterial3Api::class)
@Composable
fun ComprehensiveStudyResourcesHubCard(
    modifier: Modifier = Modifier,
    onOpenLesson: (String) -> Unit = {},
    onNavigateTab: (AppTab) -> Unit = {}
) {
    var selectedCategoryTab by remember { mutableIntStateOf(0) }
    var searchQuery by remember { mutableStateOf("") }
    var tabNoticeExpanded by remember { mutableStateOf(false) }
    val expandedItemMap = remember { mutableStateMapOf<String, Boolean>() }
    val userOwnedResourceMap = remember { mutableStateMapOf<String, Boolean>() }

    val filteredResources = remember(selectedCategoryTab, searchQuery) {
        comprehensiveResourceDataset.filter { item ->
            val matchTab = item.categoryTab == selectedCategoryTab
            val matchQuery = searchQuery.isBlank() ||
                    item.title.contains(searchQuery, ignoreCase = true) ||
                    item.authorOrPublisher.contains(searchQuery, ignoreCase = true) ||
                    item.summary.contains(searchQuery, ignoreCase = true)
            matchTab && matchQuery
        }
    }

    Card(
        modifier = modifier
            .fillMaxWidth()
            .testTag("comprehensive_study_resources_hub_card"),
        shape = RoundedCornerShape(22.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.5f)),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier.padding(14.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            // هدر طلایی بخش منابع
            Surface(
                shape = RoundedCornerShape(16.dp),
                color = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.7f),
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier.padding(14.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Surface(
                        shape = CircleShape,
                        color = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.size(44.dp)
                    ) {
                        Box(contentAlignment = Alignment.Center) {
                            Icon(
                                imageVector = Icons.AutoMirrored.Filled.MenuBook,
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.onPrimary,
                                modifier = Modifier.size(24.dp)
                            )
                        }
                    }
                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = "📚 مرجع جامع و رسمی منابع کنکور انسانی و فرهنگیان ۱۴۰۶",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onPrimaryContainer
                        )
                        Spacer(modifier = Modifier.height(2.dp))
                        Text(
                            text = "منابع مصوب سازمان سنجش، ضرایب، کتب تستی رتبه‌برترها، منابع نهایی و مصاحبه",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.85f)
                        )
                    }
                }
            }

            // تب‌های دسته‌بندی منابع (قابل اسکرول و خوانا بدون شکستن کلمات و چندخطی شدن ناموزون)
            ScrollableTabRow(
                selectedTabIndex = selectedCategoryTab,
                containerColor = MaterialTheme.colorScheme.surfaceContainerHigh,
                contentColor = MaterialTheme.colorScheme.primary,
                edgePadding = 12.dp,
                divider = {},
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(14.dp))
            ) {
                resourceCategoryTabs.forEachIndexed { index, title ->
                    val isSelected = selectedCategoryTab == index
                    Tab(
                        selected = isSelected,
                        onClick = { selectedCategoryTab = index },
                        text = {
                            Text(
                                text = title,
                                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
                                fontSize = 12.sp,
                                maxLines = 1,
                                softWrap = false
                            )
                        }
                    )
                }
            }

            // فیلد جستجوی سریع
            OutlinedTextField(
                value = searchQuery,
                onValueChange = { searchQuery = it },
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("resource_search_input"),
                placeholder = { Text("جستجو در نام کتاب، درس، انتشارات یا سرفصل...", style = MaterialTheme.typography.bodySmall) },
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = "جستجو",
                        tint = MaterialTheme.colorScheme.primary
                    )
                },
                singleLine = true,
                shape = RoundedCornerShape(12.dp)
            )

            // توضیح سربرگ فعال
            val tabNotice = when (selectedCategoryTab) {
                0 -> "📌 منابع زیر طبق آخرین اصلاحیه رسمی سازمان سنجش آموزش کشور برای آزمون اختصاصی تربیت معلم و دانشگاه فرهنگیان ۱۴۰۶ الزامی هستند (ضرایب ۳ و ۲)."
                1 -> "📌 کتب درسی و بودجه‌بندی رسمی دفترچه‌های ۱ و ۲ کنکور سراسری انسانی به همراه سال چاپ مجاز کتب و ضرایب سنجش."
                2 -> "📌 پیشنهاد طلایی رتبه‌های زیر ۱۰۰ کنکور انسانی: تفکیک شده در ۳ سطح آموزشی (از صفر تا تسلط تستی و کتاب‌های جامع)."
                3 -> "📌 منابع رسمی و کتاب‌های تمرینی برای کسب نمره ۲۰ در امتحانات نهایی خرداد دوازدهم (با تاثیر ۶۰ درصدی قطعی در قبولی)."
                else -> "📌 منابع کتبی و شفاهی پرسش‌های مصاحبه عمومی، گزینش عقیدتی-سیاسی، احکام شرعی و کانون ارزیابی شایستگی معلمی."
            }
            Surface(
                color = MaterialTheme.colorScheme.surfaceContainer,
                shape = RoundedCornerShape(10.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { tabNoticeExpanded = !tabNoticeExpanded }
            ) {
                Text(
                    text = tabNotice,
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    modifier = Modifier.padding(10.dp),
                    lineHeight = 18.sp,
                    maxLines = if (tabNoticeExpanded) Int.MAX_VALUE else 1,
                    overflow = TextOverflow.Ellipsis
                )
            }

            // لیست کارت‌های منابع (فهرست کتاب: فاصله یکدست و بزرگ‌تر)
            Column(
                verticalArrangement = Arrangement.spacedBy(14.dp)
            ) {
                if (filteredResources.isEmpty()) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(24.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "منبعی با عبارت جستجو شده یافت نشد.",
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                } else {
                    filteredResources.forEachIndexed { index, item ->
                        // key پایدار: با تغییر فیلتر/جستجو، کارت‌ها به‌جای
                        // ترکیب دوبارهٔ همه، فقط جابه‌جا/حذف/اضافه می‌شوند.
                        key(item.id) {
                            val isExpanded = expandedItemMap[item.id] == true
                            val isOwned = userOwnedResourceMap[item.id] == true

                            ResourceItemCard(
                                item = item,
                                listIndex = index,
                                isExpanded = isExpanded,
                                isOwned = isOwned,
                                onToggleExpand = { expandedItemMap[item.id] = !isExpanded },
                                onToggleOwned = { userOwnedResourceMap[item.id] = !isOwned },
                                onOpenLesson = onOpenLesson
                            )
                        }
                    }
                }
            }
        }
    }
}

/**
 * گرادیان خیلی ملایم هم‌خانوادهٔ رنگ موضوع برای کارت منبع:
 * کارت بازشده گرادیان پررنگ‌تر، ردیف بسته فقط یک هالهٔ ملایم می‌گیرد.
 * فقط گرافیک بومی Compose — هیچ فایل تصویری.
 */
private fun resourceCardGradient(badgeColor: Color, base: Color, isExpanded: Boolean): Brush =
    if (isExpanded) {
        Brush.verticalGradient(
            listOf(
                badgeColor.copy(alpha = 0.16f),
                badgeColor.copy(alpha = 0.07f),
                base
            )
        )
    } else {
        Brush.verticalGradient(
            listOf(
                badgeColor.copy(alpha = 0.08f),
                badgeColor.copy(alpha = 0.03f),
                base
            )
        )
    }

/** گرادیان ملایم چیپ (ضریب / نویسنده‌وناشر / دکمهٔ تهیهٔ کتاب) — هم‌خانوادهٔ رنگ accent. */
private fun softChipGradient(accent: Color, fromAlpha: Float = 0.24f, toAlpha: Float = 0.08f): Brush =
    Brush.horizontalGradient(
        listOf(accent.copy(alpha = fromAlpha), accent.copy(alpha = toAlpha))
    )

@Composable
private fun ResourceItemCard(
    item: ComprehensiveResourceItem,
    listIndex: Int,
    isExpanded: Boolean,
    isOwned: Boolean,
    onToggleExpand: () -> Unit,
    onToggleOwned: () -> Unit,
    onOpenLesson: (String) -> Unit
) {
    Card(
        shape = RoundedCornerShape(16.dp),
        // پس‌زمینهٔ گرادیانی روی خود کارت می‌نشیند، پس رنگ یکدست کارت شفاف است
        // تا گرادیان دیده شود؛ رنگ پایهٔ گرادیان همان surfaceContainerLow قبلی است.
        colors = CardDefaults.cardColors(containerColor = Color.Transparent),
        border = BorderStroke(
            1.dp,
            if (isOwned) item.badgeColor.copy(alpha = 0.6f)
            else if (isExpanded) item.badgeColor.copy(alpha = 0.45f)
            else MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.4f)
        ),
        modifier = Modifier
            .fillMaxWidth()
            .background(
                brush = resourceCardGradient(
                    badgeColor = item.badgeColor,
                    base = MaterialTheme.colorScheme.surfaceContainerLow,
                    isExpanded = isExpanded
                ),
                shape = RoundedCornerShape(16.dp)
            )
            .clickable { onToggleExpand() }
    ) {
        Column(
            modifier = Modifier.padding(12.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            // ردیف عنوان و بج‌ها
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    modifier = Modifier.weight(1f)
                ) {
                    // دایرهٔ شمارهٔ فارسی — گرادیان ملایم هم‌خانوادهٔ رنگ موضوع
                    // (اندازه ۳۴dp، شکل دایره و متن داخلش دست نخورده‌اند.)
                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier
                            .size(34.dp)
                            .background(
                                brush = Brush.linearGradient(
                                    listOf(
                                        item.badgeColor.copy(alpha = 0.30f),
                                        item.badgeColor.copy(alpha = 0.10f)
                                    )
                                ),
                                shape = CircleShape
                            )
                    ) {
                        Text(
                            text = BidiFormatter.toPersianDigits((listIndex + 1).toString()),
                            style = MaterialTheme.typography.labelLarge,
                            fontWeight = FontWeight.Black,
                            color = item.badgeColor
                        )
                    }

                    // فهرست کتاب: ردیف بسته فقط تیتر تک‌خطی؛ نویسنده/ناشر فقط در نمای بازشده
                    Text(
                        text = item.title,
                        style = MaterialTheme.typography.titleSmall,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurface,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        modifier = Modifier.weight(1f)
                    )
                }

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    // دکمه باز و بسته کردن (بج ضریب فقط در نمای بازشده دیده می‌شود)
                    IconButton(
                        onClick = onToggleExpand,
                        modifier = Modifier
                            .size(28.dp)
                            .background(
                                color = item.badgeColor.copy(alpha = 0.08f),
                                shape = CircleShape
                            )
                    ) {
                        Icon(
                            imageVector = if (isExpanded) Icons.Default.ExpandLess else Icons.Default.ExpandMore,
                            contentDescription = if (isExpanded) "بستن" else "مشاهده جزئیات",
                            tint = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
            }

            // جزئیات آکاردئونی باز شده (خلاصه، نویسنده/ناشر و ضریب فقط اینجاست)
            // (بدون AnimatedVisibility: انیمیشن expandVertically داخل LazyColumn
            //  گاهی ارتفاع کارت را اشتباه اندازه می‌گرفت و فضای خالی بزرگ می‌ساخت.)
            if (isExpanded) {
                Column(
                    verticalArrangement = Arrangement.spacedBy(10.dp),
                    modifier = Modifier.padding(top = 4.dp)
                ) {
                    HorizontalDivider(color = MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.35f))

                    // نویسنده/ناشر، ضریب و خلاصه — فقط در نمای بازشده
                    // چیپ نویسنده/ناشر: گرادیان خیلی ملایم هم‌خانوادهٔ رنگ موضوع
                    Box(
                        modifier = Modifier
                            .background(
                                brush = softChipGradient(item.badgeColor, fromAlpha = 0.14f, toAlpha = 0.05f),
                                shape = RoundedCornerShape(8.dp)
                            )
                            .padding(horizontal = 8.dp, vertical = 4.dp)
                    ) {
                        Text(
                            text = "${item.authorOrPublisher} • ${item.gradeOrTarget}",
                            style = MaterialTheme.typography.labelSmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                    // چیپ ضریب: گرادیان ملایم رنگ موضوع + حاشیهٔ نازک هم‌رنگ
                    Box(
                        modifier = Modifier
                            .background(
                                brush = softChipGradient(item.badgeColor),
                                shape = RoundedCornerShape(8.dp)
                            )
                            .border(
                                width = 1.dp,
                                color = item.badgeColor.copy(alpha = 0.35f),
                                shape = RoundedCornerShape(8.dp)
                            )
                    ) {
                        Text(
                            text = item.coefficientOrWeight,
                            style = MaterialTheme.typography.labelSmall,
                            fontWeight = FontWeight.Bold,
                            color = item.badgeColor,
                            modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp)
                        )
                    }
                    Text(
                        text = item.summary,
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.85f),
                        lineHeight = 19.sp
                    )

                    // سال چاپ و مرجع
                    // (متن weight دارد تا دکمه «علامت‌گذاری تهیه کتاب» جای طبیعی خودش را
                    //  بگیرد؛ قبلاً متن بدون weight تمام عرض ردیف را می‌گرفت و دکمه
                    //  با عرض صفر اندازه‌گیری می‌شد: هم نامرئی بود، هم متنش در عرض صفر
                    //  خط‌به‌خط می‌شکست و ردیف را چندصد پیکسل بلند می‌کرد — همان فضای
                    //  خالی بزرگی که در اسکرین‌شات دیده می‌شد.)
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "🏷️ سال چاپ مجاز سنجش: ${item.authorizedEditionYear}",
                            style = MaterialTheme.typography.labelSmall,
                            fontWeight = FontWeight.Medium,
                            color = MaterialTheme.colorScheme.primary,
                            modifier = Modifier.weight(1f)
                        )

                        // تیک منبع تهیه شده — دکمهٔ گرادیانی ملایم با حاشیهٔ هم‌رنگ
                        // (متن، آیکون و منطق onToggleOwned دست نخورده‌اند.)
                        Box(
                            modifier = Modifier
                                .background(
                                    brush = if (isOwned) {
                                        softChipGradient(Color(0xFF10B981), fromAlpha = 0.32f, toAlpha = 0.12f)
                                    } else {
                                        softChipGradient(item.badgeColor, fromAlpha = 0.16f, toAlpha = 0.05f)
                                    },
                                    shape = RoundedCornerShape(10.dp)
                                )
                                .border(
                                    width = 1.dp,
                                    color = if (isOwned) Color(0xFF059669).copy(alpha = 0.45f)
                                    else item.badgeColor.copy(alpha = 0.30f),
                                    shape = RoundedCornerShape(10.dp)
                                )
                                .clip(RoundedCornerShape(10.dp))
                                .clickable { onToggleOwned() }
                        ) {
                            Row(
                                modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(4.dp)
                            ) {
                                Icon(
                                    imageVector = if (isOwned) Icons.Default.CheckCircle else Icons.Default.Bookmark,
                                    contentDescription = null,
                                    tint = if (isOwned) Color(0xFF059669) else MaterialTheme.colorScheme.onSurfaceVariant,
                                    modifier = Modifier.size(14.dp)
                                )
                                Text(
                                    text = if (isOwned) "تهیه کرده‌ام ✓" else "علامت‌گذاری تهیه کتاب",
                                    style = MaterialTheme.typography.labelSmall,
                                    color = if (isOwned) Color(0xFF059669) else MaterialTheme.colorScheme.onSurfaceVariant,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                        }
                    }

                    // سرفصل‌ها و فصول دقیق — گرادیان خیلی ملایم هم‌خانوادهٔ رنگ موضوع
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(
                                brush = Brush.verticalGradient(
                                    listOf(
                                        item.badgeColor.copy(alpha = 0.07f),
                                        MaterialTheme.colorScheme.surface
                                    )
                                ),
                                shape = RoundedCornerShape(10.dp)
                            )
                    ) {
                        Column(
                            modifier = Modifier.padding(10.dp),
                            verticalArrangement = Arrangement.spacedBy(4.dp)
                        ) {
                            Text(
                                text = "📑 سرفصل‌ها، مباحث و فصول امتحانی این منبع:",
                                style = MaterialTheme.typography.labelSmall,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.primary
                            )
                            item.exactChaptersOrTopics.forEachIndexed { idx, chapter ->
                                Text(
                                    text = "• ${BidiFormatter.formatMixedPersianText(chapter)}",
                                    style = MaterialTheme.typography.bodySmall,
                                    color = MaterialTheme.colorScheme.onSurface,
                                    lineHeight = 18.sp
                                )
                            }
                        }
                    }

                    // استراتژی مطالعه رتبه‌ها — گرادیان سبز خیلی ملایم
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(
                                brush = Brush.verticalGradient(
                                    listOf(Color(0xFFF0FDF4), Color(0xFFDCFCE7).copy(alpha = 0.55f))
                                ),
                                shape = RoundedCornerShape(10.dp)
                            )
                    ) {
                        Column(
                            modifier = Modifier.padding(10.dp),
                            verticalArrangement = Arrangement.spacedBy(3.dp)
                        ) {
                            Text(
                                text = "💡 شیوه و استراتژی مطالعه پیشنهادی رتبه‌های تک‌رقمی:",
                                style = MaterialTheme.typography.labelSmall,
                                fontWeight = FontWeight.Bold,
                                color = Color(0xFF15803D)
                            )
                            Text(
                                text = item.studyStrategy,
                                style = MaterialTheme.typography.bodySmall,
                                color = Color(0xFF14532D),
                                lineHeight = 18.sp
                            )
                        }
                    }

                    // نکته طلایی و دامی کنکور — گرادیان کهربایی خیلی ملایم
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(
                                brush = Brush.verticalGradient(
                                    listOf(Color(0xFFFFFBEB), Color(0xFFFEF3C7).copy(alpha = 0.55f))
                                ),
                                shape = RoundedCornerShape(10.dp)
                            )
                    ) {
                        Row(
                            modifier = Modifier.padding(10.dp),
                            horizontalArrangement = Arrangement.spacedBy(8.dp),
                            verticalAlignment = Alignment.Top
                        ) {
                            Icon(
                                imageVector = Icons.Default.Lightbulb,
                                contentDescription = null,
                                tint = Color(0xFFD97706),
                                modifier = Modifier.size(18.dp)
                            )
                            Column(verticalArrangement = Arrangement.spacedBy(2.dp)) {
                                Text(
                                    text = "⚡ نکته کنکوری و تله طراحان آزمون:",
                                    style = MaterialTheme.typography.labelSmall,
                                    fontWeight = FontWeight.Bold,
                                    color = Color(0xFFB45309)
                                )
                                Text(
                                    text = item.goldenTip,
                                    style = MaterialTheme.typography.bodySmall,
                                    color = Color(0xFF78350F),
                                    lineHeight = 18.sp
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

/**
 * دیتابیس جامع تمام منابع رسمی، تستی، نهایی و مصاحبه
 */
private fun buildComprehensiveResourceDataset(): List<ComprehensiveResourceItem> {
    return listOf(
        // ==================== بخش ۱: منابع رسمی دانشگاه فرهنگیان ۱۴۰۶ ====================
        ComprehensiveResourceItem(
            id = "res_far_gharaati",
            categoryTab = 0,
            title = "کتاب مهارت‌های معلمی",
            authorOrPublisher = "استاد محسن قرائتی — انتشارات ستاد اقامه نماز",
            coefficientOrWeight = "ضریب ۲ دفترچه فرهنگیان",
            gradeOrTarget = "آزمون کتبی تربیت معلم",
            badgeColor = Color(0xFFD97706),
            summary = "منبع اصلی و رسمی سازمان سنجش در بخش «تعلیم و تربیت اسلامی». شامل فصول منتخب پیرامون اخلاق، روش‌های تدریس و ارتباط موثر معلم با دانش‌آموزان.",
            exactChaptersOrTopics = listOf(
                "فصل ۱: جایگاه و عظمت تعلیم و تربیت، نیت الهی و اخلاص معلمی",
                "فصل ۲: بایسته‌ها، شرایط، آداب اخلاقی و رفتاری معلم و پیشگیری از آفات تدریس",
                "فصل ۳: روش‌ها و شیوه‌های انتقال مفاهیم، هنر قصه‌گویی، تمثیل، تشویق و تنبیه کلاسی",
                "پیوست: شاخص‌های معلم شایسته در ساحت‌های ۶‌گانه سند تحول بنیادین آموزش و پرورش"
            ),
            studyStrategy = "ابتدا یک دور سریع و بدون توقف بخوانید تا مفاهیم در ذهن بنشیند؛ دور دوم تمام مثال‌ها و احادیث متن را دسته‌بندی کنید؛ در دور سوم تست‌های شبیه‌ساز را بزنید.",
            goldenTip = "طراحان سنجش روی تمایز بین «شیوه‌های تشویق» و «تنبیه تربیتی» و همچنین اولویت «گفت‌وگوی خصوصی معلم با دانش‌آموز خاطی» بسیار تاکید دارند.",
            authorizedEditionYear = "چاپ‌های جدید ستاد اقامه نماز (فصول ۱، ۲ و ۳)"
        ),

        ComprehensiveResourceItem(
            id = "res_far_motahari",
            categoryTab = 0,
            title = "کتاب تعلیم و تربیت در اسلام",
            authorOrPublisher = "استاد شهید مرتضی مطهری — انتشارات صدرا",
            coefficientOrWeight = "ضریب ۲ دفترچه فرهنگیان",
            gradeOrTarget = "آزمون کتبی تربیت معلم",
            badgeColor = Color(0xFF059669),
            summary = "منبع رسمی مصوب سازمان سنجش برای بخش تربیت اسلامی. این کتاب به بررسی بنیادین عقل، اراده، عادت، تقوا و کرامت انسانی از منظر اسلام می‌پردازد.",
            exactChaptersOrTopics = listOf(
                "بخش اول: پرورش عقل و تفکر مستقل (تفاوت حفظ کردن طوطی‌وار با استدلال و تعقل)",
                "بخش دوم: عوامل مؤثر در تربیت (نقش عادت، تقوا و پرهیزکاری، عبادت و کار در تکامل نفس)",
                "بخش سوم: کرامت نفس و عزت‌نفس اسلامی (تفاوت اساسی عزت‌نفس مومن با غرور و تکبر)",
                "بخش چهارم: پیوند علم و ایمان و تعادل میان انگیزه معنوی و دانش فنی در معلم"
            ),
            studyStrategy = "بر روی اصطلاحات خاص شهید مطهری مانند «عقل فطری و مکتسب»، «تربیت عقلانی در برابر تلقین» و «تقوای مثبت در برابر انزواطلبی» تمرکز کنید و یادداشت بردارید.",
            goldenTip = "سوالات شهید مطهری مفهومی هستند. هرگز متن را صرفاً طوطی‌وار حفظ نکنید؛ تفاوت نگاه اسلام و غرب در مسئله اراده و آزادی پرتکرارترین دام تستی است.",
            authorizedEditionYear = "چاپ مصوب انتشارات صدرا (بخش‌های تربیت عقل، عادت و تقوا)"
        ),

        ComprehensiveResourceItem(
            id = "res_far_intelligence",
            categoryTab = 0,
            title = "هوش و استعداد معلمی و توانایی‌های ذهنی",
            authorOrPublisher = "منابع استاندارد هوش کلامی، استدلال و دفترچه‌های سنجش",
            coefficientOrWeight = "ضریب ۳ دفترچه فرهنگیان",
            gradeOrTarget = "آزمون کتبی فرهنگیان",
            badgeColor = Color(0xFF2563EB),
            summary = "بالاترین ضریب در دفترچه فرهنگیان (ضریب ۳). شامل سنجش استدلال منطقی، درک متن‌های طولانی کلاسی، تحلیل مسائل ریاضی کاربردی و حل مسئله.",
            exactChaptersOrTopics = listOf(
                "بخش ۱: هوش کلامی (تناسب واژگان، مترادف و متضاد، درک مطلب تحلیلی و استخراج نتیجه منطقی)",
                "بخش ۲: استدلال منطقی و استقرایی (تحلیل گزاره‌ها، قیاس و یافتن مغالطه‌های سناریوی کلاسی)",
                "بخش ۳: هوش ریاضی و محاسبات ذهنی (مسائل نسبت، درصد، تناسب و سرعت ویژه مدیریت کلاس)",
                "بخش ۴: تجسم فضایی و الگوهای تصویری (دوران شکل‌ها، برش و توالی اشکال هندسی)"
            ),
            studyStrategy = "روزی حداقل ۱۰ تا ۱۵ تست هوش کلامی و استدلالی در روتین شبانه خود قرار دهید. تکنیک خواندن گزینه‌ها قبل از متن در درک مطلب زمان شما را نصف می‌کند.",
            goldenTip = "در سناریوهای مدیریتی کلاسی، گزینه‌ای صحیح است که کرامت دانش‌آموز را حفظ کرده، تنش را در کلاس پخش نکند و راه‌حل ایجابی و مشورتی پیشنهاد دهد.",
            authorizedEditionYear = "آزمون‌های سراسری و استعداد تحصیلی سازمان سنجش"
        ),

        ComprehensiveResourceItem(
            id = "res_far_dini_ayat",
            categoryTab = 0,
            title = "آیات و روایات منتخب دین و زندگی (فرهنگیان)",
            authorOrPublisher = "کتب دین و زندگی دهم و یازدهم متوسطه — وزارت آموزش و پرورش",
            coefficientOrWeight = "ضریب ۲ دفترچه فرهنگیان",
            gradeOrTarget = "آزمون اختصاصی فرهنگیان",
            badgeColor = Color(0xFF7C3AED),
            summary = "پرسش‌های معارف اسلامی در کنکور فرهنگیان تمرکز ویژه بر پیام آیات دهم و یازدهم پیرامون هدف خلقت، اخلاص، توکل، مراتب توحید و امامت دارد.",
            exactChaptersOrTopics = listOf(
                "پایه دهم: دروس ۱ تا ۵ (هدف زندگی، کاروان هستی، فرجام برزخ و قیامت)",
                "پایه دهم: دروس ۱۱ و ۱۲ (فضیلت آراستگی، پوشش، عفاف و زیبایی اخلاقی)",
                "پایه یازدهم: دروس ۱ تا ۴ (هدایت الهی، اعجاز قرآن، معجزه جاویدان و مسئولیت‌های پیامبر)",
                "پایه یازدهم: دروس ۵ تا ۸ (امامت و ولایت، مرجعیت دینی، پیشوایان اسوه و تمدن اسلامی)"
            ),
            studyStrategy = "برای هر آیه، ۳ عنصر را استخراج کنید: ۱) مخاطب آیه، ۲) پیام و نتیجه اصلی، ۳) ارتباط معنایی با احادیث و اشعار فارسی.",
            goldenTip = "طراحان به شدت به دنبال ربط دادن آیات «اخلاص» (مثل آیه ما اسئلکم علیه اجرا) با انگیزه خدمت و معلمی هستند.",
            authorizedEditionYear = "چاپ‌های ۱۴۰۳، ۱۴۰۴ و ۱۴۰۵ آموزش و پرورش"
        ),

        // ==================== بخش ۲: منابع رسمی کنکور سراسری انسانی ====================
        ComprehensiveResourceItem(
            id = "res_konkoor_fonoon",
            categoryTab = 1,
            title = "علوم و فنون ادبی ۱، ۲ و ۳",
            authorOrPublisher = "کتب رسمی درسی پایه‌های دهم، یازدهم و دوازدهم علوم انسانی",
            coefficientOrWeight = "۳۰ تست • ضریب ۸ کنکور",
            gradeOrTarget = "دفترچه ۱ کنکور سراسری",
            badgeColor = Color(0xFFDC2626),
            summary = "مهم‌ترین و رتبه‌سازترین درس تخصصی انسانی با ضریب ۸. شامل تاریخ ادبیات، سبک‌شناسی، عروض و قافیه، آرایه‌های ادبی و قرابت معنایی.",
            exactChaptersOrTopics = listOf(
                "پایه دهم: تقطیع هجایی، علائم عروضی، فصاحت و بلاغت، تشبیه، مجاز و سبک خراسانی",
                "پایه یازدهم: اوزان همسان، استعاره، کنایه، ارکان تشبیه و سبک عراقی",
                "پایه دوازدهم: اختیارات شاعری (زبانی و وزنی)، اوزان دوری و ناهمسان، ایهام، تناسب، متناقض‌نما و سبک هندی و معاصر"
            ),
            studyStrategy = "عروض را به صورت سماعی (آهنگی) و روزانه ۳ تست تمرین کنید. برای آرایه‌ها هر شب ۵ بیت شعر را از نظر تمام آرایه‌ها کالبدشکافی کنید.",
            goldenTip = "در اختیارات وزنی، «تسکین» فقط در دو هجای کوتاه کنار هم (`U U ➔ —`) رخ می‌دهد و در هجای اول هرگز امکان‌پذیر نیست!",
            authorizedEditionYear = "دهم چاپ ۱۴۰۳ • یازدهم چاپ ۱۴۰۴ • دوازدهم چاپ ۱۴۰۵"
        ),

        ComprehensiveResourceItem(
            id = "res_konkoor_math",
            categoryTab = 1,
            title = "ریاضی و آمار ۱، ۲ و ۳",
            authorOrPublisher = "کتب درسی پایه‌های دهم، یازدهم و دوازدهم علوم انسانی",
            coefficientOrWeight = "۲۰ تست • ضریب ۶ کنکور",
            gradeOrTarget = "دفترچه ۱ کنکور سراسری",
            badgeColor = Color(0xFF0284C7),
            summary = "درس ترازآور کنکور که اکثر داوطلبان انسانی درصد پایینی دارند. کسب درصد بالای ۴۰ در این درس رتبه شما را چند هزار نفر ارتقا می‌دهد.",
            exactChaptersOrTopics = listOf(
                "دهم: معادله درجه دو، دلتا، نامعادلات، حل مسئله و آمار توصیفی (نمودارها و شاخص‌های پراکندگی)",
                "یازدهم: آشنایی با منطق ریاضی، گزاره‌ها، ترکیب عطفی/شرطی، توابع و آمار تحلیلی",
                "دوازدهم: شمارش بدون فرمول، جایگشت، ترکیبیات، احتمال شرطی، الگوهای خطی و سری‌های زمانی"
            ),
            studyStrategy = "ابتدا جزوه صفر تا صد ریاضی برنامه را بخوانید، سپس سراغ تیپ‌بندی تست‌ها بروید؛ تست‌های احتمالات و آمار توصیفی قطعی‌ترین تست‌های کنکور هستند.",
            goldenTip = "در فرمول‌های ترکیبیات، هرجا «ترتیب و چیدمان» مهم باشد از جایگشت (تبدیل)، و هرجا «انتخاب گروهی بدون ترتیب» باشد از ترکیب (انتخاب) استفاده کنید.",
            authorizedEditionYear = "دهم چاپ ۱۴۰۳ • یازدهم چاپ ۱۴۰۴ • دوازدهم چاپ ۱۴۰۵"
        ),

        ComprehensiveResourceItem(
            id = "res_konkoor_sociology",
            categoryTab = 1,
            title = "جامعه‌شناسی ۱، ۲ و ۳",
            authorOrPublisher = "کتب درسی پایه‌های دهم، یازدهم و دوازدهم علوم انسانی",
            coefficientOrWeight = "۲۰ تست • ضریب ۵ کنکور",
            gradeOrTarget = "دفترچه ۱ کنکور سراسری",
            badgeColor = Color(0xFF0D9488),
            summary = "درسی کاملاً مفهومی و زنجیره‌ای. درصد بالای ۸۰ برای داوطلبان هدف فرهنگیان و دانشگاه تهران الزامی است.",
            exactChaptersOrTopics = listOf(
                "دهم: کنش اجتماعی، پدیده‌ها، نظام‌های اجتماعی، هویت، بازتولید هویتی و کج‌روی",
                "یازدهم: فرهنگ جهانی، جهان متجدد، امپریالیسم اقتصادی و سیاسی و بیداری اسلامی",
                "دوازدهم: رویکردهای ۳‌گانه (تبیینی، تفسیری، انتقادی)، بلوک‌بندی قدرت و بحران‌های زیست‌محیطی و اخلاقی"
            ),
            studyStrategy = "برای هر مفهوم کتاب، یک مثال امروزی در دفترچه بنویسید. نمودارهای مفهومی ارتباط جهان غرب و جهان اسلام را ترسیم کنید.",
            goldenTip = "رویکرد تبیینی جامعه را «مستقل از اراده افراد» می‌داند، در حالی که رویکرد تفسیری انسان را «کنشگر معنابخش» معرفی می‌کند.",
            authorizedEditionYear = "دهم چاپ ۱۴۰۳ • یازدهم چاپ ۱۴۰۴ • دوازدهم چاپ ۱۴۰۵"
        ),

        ComprehensiveResourceItem(
            id = "res_konkoor_psychology",
            categoryTab = 1,
            title = "روان‌شناسی پایه یازدهم",
            authorOrPublisher = "کتاب تک‌جلدی پایه یازدهم علوم انسانی",
            coefficientOrWeight = "۱۵ تست • ضریب ۲ کنکور",
            gradeOrTarget = "دفترچه ۱ کنکور سراسری",
            badgeColor = Color(0xFFE11D48),
            summary = "درسی تک‌کتاب و بسیار زودبازده. با مطالعه استاندارد می‌توانید در این درس به درصد ۱۰۰ در کنکور دست پیدا کنید.",
            exactChaptersOrTopics = listOf(
                "درس ۱ و ۲: چیستی روان‌شناسی، روش‌های علمی تحقیق و مراحل رشد جسمی و شناختی پیاژه",
                "درس ۳ و ۴: احساس، توجه، ادراک، خطاهای شناختی و حافظه ۳ مرحله‌ای (حسی، کوتاه‌مدت، بلندمدت)",
                "درس ۵ و ۶: تفکر، حل مسئله، تصمیم‌گیری و خطاهای قضاوت (شهودی، دسترس‌پذیری، لنگر انداختن)",
                "درس ۷ و ۸: انگیزش، هیجان، عوامل تنیدگی (استرس) و راهبردهای روان‌شناختی سلامت روان"
            ),
            studyStrategy = "جدول مراحل رشد اریکسون و پیاژه و آزمایش‌های مشهور (مثل آزمایش میلگرام و خطوط اش) را با کاریکاتور و رمز تصویری حفظ کنید.",
            goldenTip = "خطای «دسترس‌پذیری» زمانی است که بر اساس اولین اطلاعات حاضر در ذهن تصمیم می‌گیریم؛ با خطای «تایید خود» اشتباه نشود!",
            authorizedEditionYear = "چاپ‌های ۱۴۰۳ و ۱۴۰۴ آموزش و پرورش"
        ),

        ComprehensiveResourceItem(
            id = "res_konkoor_arabic",
            categoryTab = 1,
            title = "عربی زبان قرآن ۱، ۲ و ۳ (تخصصی)",
            authorOrPublisher = "کتب درسی پایه‌های دهم، یازدهم و دوازدهم انسانی",
            coefficientOrWeight = "۲۰ تست • ضریب ۵ کنکور",
            gradeOrTarget = "دفترچه ۲ کنکور سراسری",
            badgeColor = Color(0xFF16A34A),
            summary = "ترکیب قواعد صرف و نحو، تحلیل صرفی، ضبط حرکات و درک مطلب ترجمه. ترازسازترین درس دفترچه دوم.",
            exactChaptersOrTopics = listOf(
                "دهم: اوزان ثلاثی مزید، عدد و معدود، ترکیب وصفی و اضافی و جمله اسمیه و فعلیه",
                "یازدهم: اسم تفضیل و مکان، شبیه، حروف مشبهة بالفعل، لای نفی جنس و افعال ناقصه",
                "دوازدهم: حروف ناصبه و جازمه، اعراب مضارع، حال (قید حالت)، اسلوب استثنا و تحلیل اعراب"
            ),
            studyStrategy = "در تست‌های ترجمه اول فعل‌ها (زمان و صیغه)، دوم معرفه و نکره و سوم جمع و مفرد را چک کنید تا بدون ترجمه کل متن به پاسخ برسید.",
            goldenTip = "«لای نفی جنس» همیشه بر سر اسم نکره می‌آید و بدون تنوین و با فتحه مبنی است: `لا رَیْبَ فیهِ`.",
            authorizedEditionYear = "دهم چاپ ۱۴۰۳ • یازدهم چاپ ۱۴۰۴ • دوازدهم چاپ ۱۴۰۵"
        ),

        ComprehensiveResourceItem(
            id = "res_konkoor_philosophy_logic",
            categoryTab = 1,
            title = "منطق دهم + فلسفه یازدهم و دوازدهم",
            authorOrPublisher = "کتب ۳ جلدی منطق ۱۰، فلسفه ۱۱ و فلسفه ۱۲",
            coefficientOrWeight = "۲۰ تست • ضریب ۵ کنکور",
            gradeOrTarget = "دفترچه ۲ کنکور سراسری",
            badgeColor = Color(0xFF4F46E5),
            summary = "مفهومی‌ترین مجموعه دروس علوم انسانی. نیازمند فهم زنجیره‌ای از تصورات و تصدیقات تا حکمت صدرایی و سهروردی.",
            exactChaptersOrTopics = listOf(
                "منطق دهم: تعریف و مغالطات تعریف، قضایا، مربع تقابل، عکس مستوی و قیاس اقترانی شکل اول تا چهارم",
                "فلسفه یازدهم: چیستی فلسفه، معرفت‌شناسی، امکان شناخت، ابزارهای شناخت و آغاز تاریخی فلسفه در یونان و اسلام",
                "فلسفه دوازدهم: وجود و ماهیت، علت و معلول، اصالت وجود ملاصدرا، حرکت جوهری، عقل و دین و فلسفه مدرن غرب"
            ),
            studyStrategy = "برای قیاس‌های منطق قوانین ۲ ضرب‌المثل طلایی برنامه را حفظ کنید: «از دو مقدمه سالبه هیچ نتیجه‌ای حاصل نمی‌شود» و «علامت نتیجه تابع اَخَسّ مقدمتین است».",
            goldenTip = "در مربع تقابل: اگر اصل قضیه موجبه کلیه صادق باشد، متناقض آن (سالبه جزئیه) حتماً کاذب است و متضاد آن (سالبه کلیه) نیز کاذب خواهد بود.",
            authorizedEditionYear = "منطق چاپ ۱۴۰۳ • فلسفه ۱۱ چاپ ۱۴۰۴ • فلسفه ۱۲ چاپ ۱۴۰۵"
        ),

        ComprehensiveResourceItem(
            id = "res_konkoor_economics",
            categoryTab = 1,
            title = "اقتصاد پایه دهم",
            authorOrPublisher = "کتاب تک‌جلدی اقتصاد دهم انسانی",
            coefficientOrWeight = "۱۵ تست • ضریب ۲ کنکور",
            gradeOrTarget = "دفترچه ۲ کنکور سراسری",
            badgeColor = Color(0xFFEA580C),
            summary = "شامل دو بخش مجزا: متن کتاب (تعاریف بازاریابی، تورم، پول) و مسائل ریاضی (مالیات تصاعدی، استهلاک، درآمد ملی و دهک‌های درآمدی).",
            exactChaptersOrTopics = listOf(
                "فصل ۱ و ۲: اصول انتخاب در کسب‌وکار، بازار، عرضه و تقاضا، تعادل و سود خالص و ناخالص",
                "فصل ۳: دولت و اقتصاد، بودجه، مالیات تصاعدی و ارزش افزوده، پول، تورم و نقدینگی",
                "فصل ۴ و ۵: تجارت بین‌الملل، مزیت مطلق و نسبی، شاخص‌های توسعه، فقر، دهک‌ها و ضریب جینی"
            ),
            studyStrategy = "مسائل اقتصاد را با روش‌های سریع ۵ ثانیه‌ای برنامه حل کنید. هر شب ۳ مسئله از دهک‌ها، استهلاک و مالیات حل کنید تا محاسباتتان روان شود.",
            goldenTip = "ضریب جینی بین صفر و یک است؛ هرچه به صفر نزدیک‌تر باشد نشان‌دهنده «توزیع عادلانه‌تر درآمد» و هرچه به یک نزدیک‌تر باشد نشان‌دهنده «شکاف عمیق‌تر طبقاتی» است.",
            authorizedEditionYear = "چاپ‌های ۱۴۰۳ و ۱۴۰۴ آموزش و پرورش"
        ),

        ComprehensiveResourceItem(
            id = "res_konkoor_history_geo",
            categoryTab = 1,
            title = "تاریخ ۱، ۲، ۳ + جغرافیای ۱، ۲، ۳",
            authorOrPublisher = "کتب ۶ جلدی تاریخ و جغرافیا پایه‌های دهم، یازدهم و دوازدهم",
            coefficientOrWeight = "۲۵ تست (۱۳ تاریخ + ۱۲ جغرافیا) • ضریب ۵",
            gradeOrTarget = "دفترچه ۲ کنکور سراسری",
            badgeColor = Color(0xFFCA8A04),
            summary = "مجموعه دروس حفظی-تحلیلی. دارای بودجه‌بندی ثابت در کنکور که با استفاده از نمودارهای زمانی و اطلس نقشه‌ها به راحتی تثبیت می‌شود.",
            exactChaptersOrTopics = listOf(
                "تاریخ ۱۰، ۱۱ و ۱۲: ایران باستان، دوران اسلامی و صفویه، قاجار، مشروطه، پهلوی و انقلاب اسلامی",
                "جغرافیای ۱۰: جغرافیای ایران (ناهمواری‌ها، حوضه‌های آبریز، آب‌وهوا و استان‌شناسی)",
                "جغرافیای ۱۱ و ۱۲: جغرافیای ناحیه‌ای جهان، سامانه‌های سنجش از دور GIS، فرونشست زمین و مخاطرات محیطی"
            ),
            studyStrategy = "از خط زمانی و درخت حافظه تصویری برنامه برای رویدادهای تاریخ استفاده کنید؛ برای جغرافیا روی حوضه‌های بسته و باز آبریز ایران مسلط شوید.",
            goldenTip = "طراحان به شدت به تقاطع رویدادهای مشروطه (مثل اولویت‌های روشنفکران و نقش علما در مهاجرت صغری و کبری) علاقه دارند.",
            authorizedEditionYear = "دهم چاپ ۱۴۰۳ • یازدهم چاپ ۱۴۰۴ • دوازدهم چاپ ۱۴۰۵"
        ),

        // ==================== بخش ۳: منابع تستی پیشنهادی رتبه‌های برتر ====================
        ComprehensiveResourceItem(
            id = "res_test_fonoon",
            categoryTab = 2,
            title = "بهترین کتب تستی علوم و فنون ادبی",
            authorOrPublisher = "خیلی سبز جامع • مشاوران آموزش جامع • نشر الگو",
            coefficientOrWeight = "سطح‌بندی پیشنهادی",
            gradeOrTarget = "آموزش + تست سراسری و تالیفی",
            badgeColor = Color(0xFFDC2626),
            summary = "برای درس فنون دو منبع لازم است: یک کتاب جامع برای درس‌نامه و تست‌های مبحثی، و یک کتاب جیبی برای عروض سماعی و آرایه‌ها.",
            exactChaptersOrTopics = listOf(
                "سطح ۱ (شروع از صفر): علوم و فنون ادبی خیلی سبز جامع (درس‌نامه‌های روان و جدول‌های مقایسه‌ای)",
                "سطح ۲ (تسلط و حل تست سنگین): علوم و فنون ادبی جامع مشاوران آموزش (تست‌های سخت و تیپ‌بندی شده)",
                "منبع مکمل مهارتی: مبحثی عروض و قافیه الگو یا مینی میکرو طلایی گاج برای تست روزانه"
            ),
            studyStrategy = "ابتدا تست‌های کنکور ۵ سال اخیر را بزنید؛ سپس به سراغ تست‌های تالیفی استاندارد بروید و نکات اشتباه را در کتاب درسی هایلایت کنید.",
            goldenTip = "هیچ کتاب کمکی جای متن خود کتاب درسی در تاریخ ادبیات را نمی‌گیرد؛ تاریخ ادبیات را مستقیماً از روی کتاب درسی یا خلاصه برنامه بخوانید.",
            authorizedEditionYear = "ویراست جدید منطبق بر کنکور ۱۴۰۵ و ۱۴۰۶"
        ),

        ComprehensiveResourceItem(
            id = "res_test_math",
            categoryTab = 2,
            title = "بهترین کتب تستی ریاضی و آمار انسانی",
            authorOrPublisher = "خیلی سبز جامع • مهروماه لقمه • نشر الگو",
            coefficientOrWeight = "سطح‌بندی پیشنهادی",
            gradeOrTarget = "آموزش گام‌به‌گام از پایه",
            badgeColor = Color(0xFF0284C7),
            summary = "کتابی برای ریاضی مناسب است که راه‌حل‌های کوتاه داشته باشد و فرمول‌های سنگین و غیرکاربردی ارائه ندهد.",
            exactChaptersOrTopics = listOf(
                "سطح ۱ (از منفی صفر): ریاضی و آمار جامع خیلی سبز (پاسخ‌های تشریحی بسیار روان و قدم‌به‌قدم)",
                "سطح ۲ (درصد بالای ۵۰): ریاضی و آمار جامع مهروماه یا نشر الگو (تست‌های خلاقانه و زمان‌دار)",
                "منبع جمع‌بندی سریع: فصل آزمون خیلی سبز یا موج آزمون نشر الگو برای دو ماه پایانی کنکور"
            ),
            studyStrategy = "هرگز پاسخنامه تشریحی را بلافاصله نگاه نکنید؛ حداقل ۳ دقیقه با هر مسئله دست‌وپنجه نرم کنید تا مسیر تفکر ریاضی در مغز فعال شود.",
            goldenTip = "تست‌های آمار و احتمال در کتاب خیلی سبز جامع بهترین پوشش را نسبت به تست‌های کنکور سراسری سال‌های اخیر دارند.",
            authorizedEditionYear = "چاپ‌های جدید منطبق بر کتاب‌های درسی سه پایه"
        ),

        ComprehensiveResourceItem(
            id = "res_test_philosophy",
            categoryTab = 2,
            title = "بهترین کتب تستی فلسفه و منطق",
            authorOrPublisher = "مشاوران آموزش جامع • خیلی سبز جامع • مهروماه",
            coefficientOrWeight = "سطح‌بندی پیشنهادی",
            gradeOrTarget = "تست‌های مفهومی و دام‌دار",
            badgeColor = Color(0xFF4F46E5),
            summary = "کتاب فلسفه و منطق باید دام‌های گزینه‌ای و کلمات چندپهلو را افشا کند تا داوطلب در گزینه‌های شبیه به هم فریب نخورد.",
            exactChaptersOrTopics = listOf(
                "سطح اول و برتر: فلسفه و منطق جامع مشاوران آموزش (احمد خداداد حسینی) — بهترین درس‌نامه تحلیلی بازار",
                "سطح مکمل: فلسفه و منطق خیلی سبز جامع (تست‌های کنکور تفکیک شده و پاسخ‌های مفصل)",
                "منبع سریع: کتاب لقمه طلایی فلسفه و منطق مهروماه برای مرور شب امتحان و تورق سریع"
            ),
            studyStrategy = "بعد از هر تست منطق، علت غلط بودن ۳ گزینه دیگر را بررسی کنید؛ طراحان کنکور معمولاً گزینه غلط سال قبل را صورت سوال امسال می‌کنند!",
            goldenTip = "بخش مغالطات منطق دهم نیازمند حل بیش از ۱۰۰ تست نمونه است؛ کتاب مشاوران آموزش بهترین نمونه‌های مغالطات واقعی را گردآوری کرده است.",
            authorizedEditionYear = "چاپ جدید سه سالانه جامع"
        ),

        ComprehensiveResourceItem(
            id = "res_test_farhangian",
            categoryTab = 2,
            title = "بهترین کتب ویژه آزمون فرهنگیان",
            authorOrPublisher = "کتاب ویژه فرهنگیان قلم‌چی • خیلی سبز فرهنگیان • انتشارات پویش",
            coefficientOrWeight = "تخصصی تربیت معلم",
            gradeOrTarget = "شبیه‌ساز کنکور فرهنگیان",
            badgeColor = Color(0xFFD97706),
            summary = "مجموعه‌هایی که دقیقاً بر اساس دو دفترچه اختصاصی فرهنگیان (هوش + تعلیم و تربیت اسلامی) و مصاحبه تالیف شده‌اند.",
            exactChaptersOrTopics = listOf(
                "کتاب ۱۰ کنکور فرهنگیان کانون قلم‌چی: آزمون‌های شبیه‌ساز استاندارد با سوالات عینا تطبیقی",
                "کتاب هوش و استعداد معلمی خیلی سبز یا پویش: آموزش تکنیک‌های پاسخ به سوالات استدلالی و متنی",
                "کتابچه مهارت‌های معلمی و تربیت اسلامی نشر قلم‌چی: خلاصه نموداری کتاب‌های قرائتی و شهید مطهری"
            ),
            studyStrategy = "حتماً حداقل ۳ آزمون جامع شبیه‌ساز فرهنگیان را با زمان‌بندی رسمی در منزل از خودتان بگیرید تا استراتژی زمان دفترچه دستتان بیاید.",
            goldenTip = "دفترچه فرهنگیان نمره منفی دارد! هرگز تست‌های مبهم هوش را شانسی نزنید؛ دقت در این دفترچه مهم‌تر از سرعت خام است.",
            authorizedEditionYear = "ویژه کنکور اردیبهشت و تیر ۱۴۰۶"
        ),

        // ==================== بخش ۴: منابع امتحانات نهایی خرداد ====================
        ComprehensiveResourceItem(
            id = "res_nahaee_farsi",
            categoryTab = 3,
            title = "فارسی ۳ و نگارش ۳ (امتحان نهایی دوازدهم)",
            authorOrPublisher = "فرمول بیست گاج • شب امتحان خیلی سبز • ماجراهای من و درسام",
            coefficientOrWeight = "ضریب نهایی: ۱۸.۶٪",
            gradeOrTarget = "امتحان نهایی خرداد",
            badgeColor = Color(0xFF059669),
            summary = "بالاترین ضریب در میان تمام دروس عمومی و اختصاصی در سابقه تحصیلی نهایی (حدود ۱۸.۶ درصد تراز سابقه قطعی).",
            exactChaptersOrTopics = listOf(
                "قلمرو زبانی: معنی واژگان ستاره‌دار انتهای کتاب، املای کلمات، تکواژ و واژه، وابسته‌های وابسته و حذف",
                "قلمرو ادبی: آرایه‌های کل دروس، قالب‌های شعری، بحور اوزان دروس و تاریخ ادبیات متن درس",
                "قلمرو فکری: مفاهیم و درک مطلب اشعار و نثرهای تمام ۱۸ درس، حفظ شعر و خودآزمایی‌ها",
                "نگارش ۳:اره‌نویسی، انشای تحلیلی، خاطره‌نویسی و رعایت اصول نگارشی و علائم سجاوندی"
            ),
            studyStrategy = "تمام لغات ستاره‌دار و بی‌ستاره انتهای کتاب فارسی ۳ را ۳ بار بنویسید؛ در کارگاه‌های متن‌پژوهی هیچ نکته‌ای را جا نیندازید.",
            goldenTip = "مصححان امتحانات نهایی کلید دقیق دارند! کلماتی که در پاسخنامه راهنمای تصحیح است دقیقاً باید در برگه امتحانی شما نوشته شود.",
            authorizedEditionYear = "چاپ سال دوازدهم ۱۴۰۵ آموزش و پرورش"
        ),

        ComprehensiveResourceItem(
            id = "res_nahaee_dini",
            categoryTab = 3,
            title = "دین و زندگی ۳ (امتحان نهایی دوازدهم)",
            authorOrPublisher = "فرمول بیست گاج • شب امتحان خیلی سبز",
            coefficientOrWeight = "ضریب نهایی: ۱۴.۲٪",
            gradeOrTarget = "امتحان نهایی خرداد",
            badgeColor = Color(0xFF7C3AED),
            summary = "دومین درس پرضریب امتحان نهایی. تصحیح برگه نهایی بسیار دقیق است و باید کلمات کلیدی آیات در پاسخ باشد.",
            exactChaptersOrTopics = listOf(
                "درس ۱ تا ۴: هستی‌بخش، یگانگی خداوند (توحید در خالقیت، مالکیت، ربوبیت، ولایت و توحید افعالی)",
                "درس ۵ و ۶: اخلاص، توبه، بندگی و راه رسیدن به حیات طیبه",
                "درس ۷ و ۸: سنت‌های الهی در زندگی (ابتلا، امداد عام و خاص، املا و استدراج، توفیق و سبقت رحمت)",
                "درس ۹ و ۱۰: تمدن جدید و مسئولیت‌های اخلاقی و اجتماعی مسلمانان"
            ),
            studyStrategy = "برای بخش آیات، ارتباط آیه با متن درس و اصطلاح فقهی یا کلامی مرتبط را در جدول خلاصه دو ستونه وارد کنید.",
            goldenTip = "تفاوت سنت «املا» با سنت «استدراج» پای ثابت سوالات نهایی خرداد است؛ افزایش نعمت بدون لیاقت برای غفلت بیشتر = استدراج.",
            authorizedEditionYear = "چاپ سال دوازدهم ۱۴۰۵ آموزش و پرورش"
        ),

        ComprehensiveResourceItem(
            id = "res_nahaee_general_pack",
            categoryTab = 3,
            title = "پکیج دروس عمومی نهایی (عربی ۳، زبان ۳، سلامت)",
            authorOrPublisher = "کتب درسی رسمی + نمونه سوالات نهایی ۵ سال اخیر سنجش",
            coefficientOrWeight = "مجموعاً بیش از ۲۵٪ تراز سوابق",
            gradeOrTarget = "امتحان نهایی خرداد",
            badgeColor = Color(0xFF0284C7),
            summary = "عربی ۳ (ضریب ۷.۸)، زبان انگلیسی ۳ (ضریب ۱۰.۱)، سلامت و بهداشت ۱۲ (ضریب ۳.۵) و علوم اجتماعی ۱۲ (ضریب ۸.۵).",
            exactChaptersOrTopics = listOf(
                "عربی ۳: قواعد حروف مشبهة بالفعل، لای نفی جنس، حال، استثنا و ترجمه لغات کارگاه‌ها",
                "زبان انگلیسی ۳: افعال مجهول، جملات شرطی، ضمایر موصولی و ریدینگ‌های استاندارد نهایی",
                "سلامت و بهداشت ۱۲: فرمول شاخص توده بدنی BMI، هرم غذایی، بیماری‌های واگیر و فشار خون",
                "علوم اجتماعی ۱۲: ۳ رویکرد تبیینی، تفسیری و انتقادی و نظام سلطه بین‌الملل"
            ),
            studyStrategy = "از اواسط فروردین روزی ۱ ساعت به حل نمونه سوالات نهایی خرداد سال‌های ۱۴۰۲، ۱۴۰۳ و ۱۴۰۴ با بارم‌بندی اختصاص دهید.",
            goldenTip = "در سلامت و بهداشت، تعاریف باید کلمه به کلمه شبیه کتاب باشد؛ مصححان نهایی تعاریف دستکاری‌شده را نمی‌پذیرند.",
            authorizedEditionYear = "چاپ مصوب سال جاری دوازدهم"
        ),

        // ==================== بخش ۵: منابع مصاحبه، کانون ارزیابی و گزینش ====================
        ComprehensiveResourceItem(
            id = "res_interview_ahkam",
            categoryTab = 4,
            title = "کتاب رساله احکام و مسائل شرعی گزینش",
            authorOrPublisher = "توضیح‌المسائل مراجع عظام تقلید + کتابچه احکام ستاد گزینش",
            coefficientOrWeight = "فیلتر صلاحیت عمومی و گزینش",
            gradeOrTarget = "گزینش عقیدتی-سیاسی",
            badgeColor = Color(0xFF16A34A),
            summary = "پرتکرارترین سوالات در مصاحبه گزینش آموزش و پرورش مربوط به احکام شرعی مبتلابه است که داوطلب باید با تسلط کامل پاسخ دهد.",
            exactChaptersOrTopics = listOf(
                "احکام طهارت و نجاسات: مطهرات، تفاوت وضو ارتماسی و ترتیبی، جبیره و انواع غسل‌ها",
                "احکام نماز: شکیات نماز، ارکان و غیرارکان، سجده سهو، نماز آیات، نماز جمعه، مسافر و جماعت",
                "احکام روزه: مبطلات روزه، کفاره عمد و غیرعمد، شرایط روزه مسافر و حد ترخص",
                "ولایت فقیه و احکام تقلید: دلایل عقلی و نقلی ولایت فقیه، تفاوت حکم و فتوا، خمس و زکات"
            ),
            studyStrategy = "سجده سهو، ذکر نماز آیات، ارکان نماز (۵ مورد) و تفاوت حکم حکومتی با فتوای مرجع تقلید را تمرین و با صدای بلند بیان کنید.",
            goldenTip = "ارکان نماز ۵ تاست (نیت، تکبیرة الاحرام، قیام متصل به رکوع، رکوع، دو سجده)؛ اگر رکن کم یا زیاد شود چه عمداً چه سهواً نماز باطل است!",
            authorizedEditionYear = "رساله آموزشی مقام معظم رهبری و مراجع تقلید"
        ),

        ComprehensiveResourceItem(
            id = "res_interview_kanoon",
            categoryTab = 4,
            title = "راهنمای کانون ارزیابی شایستگی‌های معلمی",
            authorOrPublisher = "سند تحول بنیادین + مدل سنجش شایستگی معلمان سنجش",
            coefficientOrWeight = "سهم ۲۵ تا ۳۰ درصدی تراز نهایی",
            gradeOrTarget = "مصاحبه تخصصی و کانون ارزیابی",
            badgeColor = Color(0xFF9333EA),
            summary = "مرحله کانون‌های ارزیابی شامل تدریس زنده (طرح درس)، ایفای نقش سناریوی کلاسی، مصاحبه روان‌شناختی و کارگروهی است.",
            exactChaptersOrTopics = listOf(
                "ایستگاه ۱: ارائه تدریس (طراحی پلن درسی ۱۰ دقیقه‌ای، شروع جذاب، درگیر کردن دانش‌آموز، ارزشیابی)",
                "ایستگاه ۲: سناریوی بحران کلاسی (مدیریت تقلب، پرخاشگری دانش‌آموز، بی‌انگیزگی، افت تحصیلی)",
                "ایستگاه ۳: مصاحبه روان‌شناختی (ثبات هیجانی، فن بیان، زبان بدن، تعهد حرفه‌ای و صبوری)",
                "سند تحول بنیادین: ۶ ساحت تربیتی (اعتقادی، علمی، اخلاقی، زیستی، هنری، اقتصادی)"
            ),
            studyStrategy = "یک مبحث ساده از کتاب درسی پایه دهم را انتخاب کنید و جلوی آینه یا گوشی با رعایت گام‌های مقدمه، انگیزش، تدریس و نتیجه‌گیری فیلم بگیرید.",
            goldenTip = "در ایستگاه سناریو هرگز سراغ تنبیه یا بیرون کردن دانش‌آموز از کلاس نروید؛ ارزیابان به شدت به دنبال مهارت شنوندگی فعال و مدیریت همدلانه هستند.",
            authorizedEditionYear = "شیوه‌نامه مصوب کانون‌های ارزیابی دانشگاه فرهنگیان"
        ),

        ComprehensiveResourceItem(
            id = "res_interview_politics",
            categoryTab = 4,
            title = "مسائل روز سیاسی، اجتماعی و تاریخ معاصر",
            authorOrPublisher = "قانون اساسی جمهوری اسلامی ایران + رویدادهای سال",
            coefficientOrWeight = "مصاحبه عمومی گزینش",
            gradeOrTarget = "گزینش عقیدتی-سیاسی",
            badgeColor = Color(0xFFEA580C),
            summary = "آشنایی با ساختار ارکان نظام، شورای نگهبان، مجمع تشخیص مصلحت، قوه قضاییه و رویدادهای مهم سال جاری و تاریخ انقلاب اسلامی.",
            exactChaptersOrTopics = listOf(
                "قانون اساسی: وظایف و اختیارات رهبری (اصل ۱۱۰)، شورای نگهبان، مجلس خبرگان رهبری",
                "تاریخ معاصر و مناسبت‌های تقویم: حوادث مهم (۱۳ آبان، ۹ دی، ۲۲ بهمن، روز معلم ۱۲ اردیبهشت)",
                "مسائل روز ایران و منطقه: تحولات جبهه مقاومت، فلسطین، انرژی هسته‌ای و پیشرفت‌های علمی",
                "بیانیه گام دوم انقلاب اسلامی: ۴۰ سال گذشته، ۷ توصیه اصلی به جوانان برای آینده کشور"
            ),
            studyStrategy = "یک دفترچه ۵ برگ برای اخبار مهم سال، مناسبت‌های تقویم رسمی و نام وزرا و روسای قوای کشور تهیه و مطالعه کنید.",
            goldenTip = "اعضای شورای نگهبان ۱۲ نفرند (۶ فقیه منصوب رهبر + ۶ حقوقدان معرفی‌شده توسط رئیس قوه قضاییه و منتخب مجلس شورای اسلامی).",
            authorizedEditionYear = "متن رسمی قانون اساسی و بیانیه گام دوم انقلاب"
        )
    )
}
