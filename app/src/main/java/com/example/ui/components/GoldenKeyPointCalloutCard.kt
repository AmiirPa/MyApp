package com.example.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
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
import androidx.compose.material.icons.filled.Bolt
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.ContentCopy
import androidx.compose.material.icons.filled.ExpandLess
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.material.icons.filled.FormatUnderlined
import androidx.compose.material.icons.filled.Highlight
import androidx.compose.material.icons.filled.Lightbulb
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.WarningAmber
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.luminance
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.theme.LocalAppThemeController
import com.example.ui.theme.LocalResponsiveDimens
import kotlinx.coroutines.delay

/**
 * رتبه‌بندی و دسته‌بندی نکات طلایی بر پایه چراغ راهنما، ستاره و چراغ اندیشه
 */
enum class GoldenPointLevel(
    val levelNameFa: String,
    val iconVector: ImageVector,
    val primaryColor: Color,
    val darkPrimaryColor: Color,
    val lightBgColor: Color,
    val darkBgColor: Color,
    val markerHighlightColor: Color,
    val trafficEmoji: String
) {
    /**
     * 🚦 چراغ قرمز: دام‌های فوق‌العاده مرگبار، خطاهای پرتکرار داوطلبان و فریب‌های طراح
     */
    TRAFFIC_RED(
        levelNameFa = "چراغ قرمز: دام و تله مرگبار طراح",
        iconVector = Icons.Default.WarningAmber,
        primaryColor = Color(0xFFDC2626),
        darkPrimaryColor = Color(0xFFF87171),
        lightBgColor = Color(0xFFFEF2F2),
        darkBgColor = Color(0xFF2A1215),
        markerHighlightColor = Color(0xFFFECACA),
        trafficEmoji = "🔴"
    ),

    /**
     * 🟡 چراغ زرد: نکات احتیاطی، موارد چندوجهی، قیود مشکوک و تکنیک‌های سرعتی رد گزینه
     */
    TRAFFIC_YELLOW(
        levelNameFa = "چراغ زرد: نکته احتیاطی و شگرد رد گزینه",
        iconVector = Icons.Default.Bolt,
        primaryColor = Color(0xFFD97706),
        darkPrimaryColor = Color(0xFFFBBF24),
        lightBgColor = Color(0xFFFFFBEB),
        darkBgColor = Color(0xFF2B1D0E),
        markerHighlightColor = Color(0xFFFEF08A),
        trafficEmoji = "🟡"
    ),

    /**
     * 🟢 چراغ سبز: قواعد قطعی و بدون استثناء، اصول بنیادین و مسیرهای تضمین‌شده تست
     */
    TRAFFIC_GREEN(
        levelNameFa = "چراغ سبز: قاعده قطعی و تست‌ساز",
        iconVector = Icons.Default.CheckCircle,
        primaryColor = Color(0xFF059669),
        darkPrimaryColor = Color(0xFF34D399),
        lightBgColor = Color(0xFFECFDF5),
        darkBgColor = Color(0xFF0D2820),
        markerHighlightColor = Color(0xFFA7F3D0),
        trafficEmoji = "🟢"
    ),

    /**
     * ⭐ ستاره طلایی: شاه‌کلیدهای رتبه‌ساز، رمزهای ابداعی و فرمول‌های طلایی کنکور
     */
    GOLDEN_STAR(
        levelNameFa = "ستاره طلایی: شاه‌کلید رتبه‌ساز کنکور",
        iconVector = Icons.Default.Star,
        primaryColor = Color(0xFFCA8A04),
        darkPrimaryColor = Color(0xFFFACC15),
        lightBgColor = Color(0xFFFFFBEB),
        darkBgColor = Color(0xFF2B210E),
        markerHighlightColor = Color(0xFFFDE68A),
        trafficEmoji = "⭐"
    ),

    /**
     * 💡 چراغ اندیشه: نکات تحلیلی، بینش عمیق مفهومی و ارتباط بین دروس
     */
    CONCEPT_LAMP(
        levelNameFa = "چراغ اندیشه: نکته مفهومی عمیق",
        iconVector = Icons.Default.Lightbulb,
        primaryColor = Color(0xFF2563EB),
        darkPrimaryColor = Color(0xFF60A5FA),
        lightBgColor = Color(0xFFEFF6FF),
        darkBgColor = Color(0xFF112240),
        markerHighlightColor = Color(0xFFBFDBFE),
        trafficEmoji = "💡"
    );

    companion object {
        /**
         * تشخیص خودکار سطح نکته از روی کلمات کلیدی متن
         */
        fun autoDetect(text: String): GoldenPointLevel {
            val lower = text.lowercase()
            return when {
                lower.contains("دام") || lower.contains("تله") || lower.contains("اشتباه") ||
                    lower.contains("خطر") || lower.contains("حواست باشه") || lower.contains("حواست باشد") ||
                    lower.contains("فریب") || lower.contains("غلط") -> TRAFFIC_RED

                lower.contains("رد گزینه") || lower.contains("احتیاط") || lower.contains("شک") ||
                    lower.contains("قید") || lower.contains("گاهی") || lower.contains("ممکن است") ||
                    lower.contains("میانبر") || lower.contains("ترفند") -> TRAFFIC_YELLOW

                lower.contains("قطعی") || lower.contains("همیشه") || lower.contains("تضمین") ||
                    lower.contains("بدون استثناء") || lower.contains("قاعده") || lower.contains("اصل") -> TRAFFIC_GREEN

                lower.contains("شاه‌کلید") || lower.contains("ستاره") || lower.contains("رمز") ||
                    lower.contains("فرمول طلایی") || lower.contains("طلایی") || lower.contains("رتبه") -> GOLDEN_STAR

                else -> CONCEPT_LAMP
            }
        }
    }
}

/**
 * کامپوننت اختصاصی «نمایش نکات طلایی و مهم درسنامه‌ها»:
 * - آیکون‌های رنگی (چراغ راهنما: قرمز، زرد، سبز، ستاره طلایی و لامپ اندیشه)
 * - حاشیه متمایز ضخیم (Accent Border Bar) برای جداسازی چشمگیر از متن عادی
 * - قابلیت هایلایت ماژیکی و خط‌کشی تعاملی مستقل با انیمیشن ماژیک فسفری
 * - قابلیت کپی مستقیم نکته در حافظه کلیپ‌بورد برای یادداشت‌برداری
 * - پشتیبانی از باز/بسته شدن توضیحات تکمیلی
 */
@OptIn(ExperimentalLayoutApi::class)
@Composable
fun GoldenKeyPointCalloutCard(
    keyPointText: String,
    modifier: Modifier = Modifier,
    level: GoldenPointLevel = remember(keyPointText) { GoldenPointLevel.autoDetect(keyPointText) },
    title: String? = null,
    explanation: String? = null,
    tags: List<String> = emptyList(),
    initiallyExpanded: Boolean = true,
    enableLocalHighlighterToggle: Boolean = true,
    overrideHighlight: Boolean? = null,
    overrideUnderline: Boolean? = null
) {
    val themeController = LocalAppThemeController.current
    val responsive = LocalResponsiveDimens.current
    val clipboardManager = LocalClipboardManager.current

    // وضعیت محلی هایلایت و زیرخط (اگر کاربر مستقیماً روی کارت کلیک کند تغییر می‌کند)
    var localHighlightActive by remember {
        mutableStateOf(overrideHighlight ?: themeController.isHighlighterEnabled)
    }
    var localUnderlineActive by remember {
        mutableStateOf(overrideUnderline ?: themeController.isUnderlineKeyPointsEnabled)
    }
    var isExpanded by remember { mutableStateOf(initiallyExpanded) }
    var isCopied by remember { mutableStateOf(false) }

    // هماهنگی با تنظیمات کلی برنامه در صورت تغییر در نوار ابزار بالا
    LaunchedEffect(themeController.isHighlighterEnabled) {
        if (overrideHighlight == null) {
            localHighlightActive = themeController.isHighlighterEnabled
        }
    }
    LaunchedEffect(themeController.isUnderlineKeyPointsEnabled) {
        if (overrideUnderline == null) {
            localUnderlineActive = themeController.isUnderlineKeyPointsEnabled
        }
    }

    // بازنشانی وضعیت کپی پس از ۲ ثانیه
    LaunchedEffect(isCopied) {
        if (isCopied) {
            delay(2000)
            isCopied = false
        }
    }

    val isDark = MaterialTheme.colorScheme.surface.luminance() < 0.5f
    val accentColor = if (isDark) level.darkPrimaryColor else level.primaryColor
    val backgroundColor = if (isDark) level.darkBgColor else level.lightBgColor
    val borderColor = accentColor.copy(alpha = if (isDark) 0.55f else 0.40f)

    // انیمیشن رنگ حاشیه هنگام فعال بودن هایلایتر
    val animatedBorderColor by animateColorAsState(
        targetValue = if (localHighlightActive) accentColor else borderColor,
        label = "goldenBorderAnim"
    )

    CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl) {
        Card(
            modifier = modifier
                .fillMaxWidth()
                .padding(vertical = 4.dp)
                .testTag("golden_keypoint_card_${level.name.lowercase()}"),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = backgroundColor),
            border = BorderStroke(1.5.dp, animatedBorderColor),
            elevation = CardDefaults.cardElevation(defaultElevation = if (localHighlightActive) 2.dp else 1.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth()
            ) {
                // ۱. حاشیه متمایز ضخیم عمودی (Accent Callout Bar) برای جداسازی قطعی از متن
                Box(
                    modifier = Modifier
                        .width(6.dp)
                        .background(
                            Brush.verticalGradient(
                                colors = listOf(
                                    accentColor,
                                    accentColor.copy(alpha = 0.5f)
                                )
                            )
                        )
                )

                // ۲. محتوای داخلی کارت
                Column(
                    modifier = Modifier
                        .weight(1f)
                        .padding(horizontal = 14.dp, vertical = 12.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    // سطر بالایی: آیکون تمیز، عنوان شیک و کلید کپی
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Surface(
                            modifier = Modifier.size(32.dp),
                            shape = CircleShape,
                            color = accentColor.copy(alpha = 0.16f),
                            border = BorderStroke(1.dp, accentColor.copy(alpha = 0.35f))
                        ) {
                            Box(contentAlignment = Alignment.Center) {
                                Icon(
                                    imageVector = level.iconVector,
                                    contentDescription = null,
                                    tint = accentColor,
                                    modifier = Modifier.size(17.dp)
                                )
                            }
                        }

                        Spacer(modifier = Modifier.width(8.dp))

                        val cleanTitle = (title ?: level.levelNameFa)
                            .replace("🎯", "")
                            .replace("🔴", "")
                            .replace("🟡", "")
                            .replace("🟢", "")
                            .replace("نکته‌سنجی و رمز", "نکته طلایی")
                            .trim()

                        Column(
                            modifier = Modifier.weight(1f)
                        ) {
                            Text(
                                text = BidiFormatter.applyPreferredDigits(cleanTitle),
                                style = MaterialTheme.typography.titleSmall,
                                fontWeight = FontWeight.ExtraBold,
                                color = accentColor
                            )
                            Text(
                                text = "کلیدواژه حیاتی امتحان نهایی و کنکور",
                                style = MaterialTheme.typography.labelSmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.75f),
                                fontSize = 10.sp
                            )
                        }

                        // ابزارهای تعاملی: کلید کپی
                        Row(
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            IconButton(
                                onClick = {
                                    val fullTextToCopy = buildString {
                                        appendLine("【${title ?: level.levelNameFa}】")
                                        appendLine(keyPointText)
                                        if (!explanation.isNullOrBlank()) {
                                            appendLine()
                                            appendLine("توضیح: $explanation")
                                        }
                                    }
                                    clipboardManager.setText(AnnotatedString(fullTextToCopy))
                                    isCopied = true
                                },
                                modifier = Modifier
                                    .size(28.dp)
                                    .testTag("golden_card_copy_btn")
                            ) {
                                Icon(
                                    imageVector = if (isCopied) Icons.Default.Check else Icons.Default.ContentCopy,
                                    contentDescription = if (isCopied) "کپی شد" else "کپی نکته طلایی",
                                    tint = if (isCopied) MaterialTheme.colorScheme.tertiary else MaterialTheme.colorScheme.onSurfaceVariant,
                                    modifier = Modifier.size(16.dp)
                                )
                            }
                        }
                    }

                    // ۳. متن اصلی نکته طلایی متمایز با هایلایت و خط‌کشی
                    Surface(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(10.dp))
                            .border(
                                width = 1.dp,
                                color = if (localHighlightActive) accentColor.copy(alpha = 0.35f) else Color.Transparent,
                                shape = RoundedCornerShape(10.dp)
                            ),
                        color = if (localHighlightActive) {
                            level.markerHighlightColor.copy(alpha = if (isDark) 0.18f else 0.40f)
                        } else {
                            MaterialTheme.colorScheme.surface.copy(alpha = 0.5f)
                        },
                        shape = RoundedCornerShape(10.dp)
                    ) {
                        Column(
                            modifier = Modifier.padding(10.dp),
                            verticalArrangement = Arrangement.spacedBy(4.dp)
                        ) {
                            BiDiText(
                                text = keyPointText,
                                style = MaterialTheme.typography.bodyMedium.copy(
                                    lineHeight = 24.sp
                                ),
                                fontWeight = FontWeight.SemiBold,
                                color = MaterialTheme.colorScheme.onSurface,
                                isUnderlineEnabled = localUnderlineActive,
                                isHighlighterEnabled = localHighlightActive
                            )
                        }
                    }

                    // ۴. برچسب‌ها و تگ‌های تستی (در صورت وجود)
                    if (tags.isNotEmpty()) {
                        FlowRow(
                            horizontalArrangement = Arrangement.spacedBy(6.dp),
                            verticalArrangement = Arrangement.spacedBy(4.dp)
                        ) {
                            tags.forEach { tag ->
                                Surface(
                                    shape = RoundedCornerShape(6.dp),
                                    color = accentColor.copy(alpha = 0.12f),
                                    border = BorderStroke(0.5.dp, accentColor.copy(alpha = 0.3f))
                                ) {
                                    Text(
                                        text = "#$tag",
                                        style = MaterialTheme.typography.labelSmall,
                                        fontSize = 10.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = accentColor,
                                        modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                                    )
                                }
                            }
                        }
                    }

                    // ۵. توضیحات تشریحی و نکته‌سنجی تکمیلی (قابل باز و بسته شدن)
                    if (!explanation.isNullOrBlank()) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clip(RoundedCornerShape(6.dp))
                                .clickable { isExpanded = !isExpanded }
                                .padding(vertical = 3.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = if (isExpanded) "بستن تحلیل و جزئیات تکمیلی" else "مشاهده تحلیل و روش حل سریع در کنکور",
                                style = MaterialTheme.typography.labelSmall,
                                fontWeight = FontWeight.Bold,
                                color = accentColor,
                                modifier = Modifier.weight(1f)
                            )
                            Icon(
                                imageVector = if (isExpanded) Icons.Default.ExpandLess else Icons.Default.ExpandMore,
                                contentDescription = null,
                                tint = accentColor,
                                modifier = Modifier.size(16.dp)
                            )
                        }

                        AnimatedVisibility(
                            visible = isExpanded,
                            enter = fadeIn() + expandVertically(),
                            exit = fadeOut() + shrinkVertically()
                        ) {
                            Surface(
                                shape = RoundedCornerShape(8.dp),
                                color = MaterialTheme.colorScheme.surface.copy(alpha = 0.65f),
                                border = BorderStroke(0.5.dp, accentColor.copy(alpha = 0.25f)),
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Column(
                                    modifier = Modifier.padding(9.dp),
                                    verticalArrangement = Arrangement.spacedBy(4.dp)
                                ) {
                                    BiDiText(
                                        text = explanation,
                                        style = MaterialTheme.typography.bodySmall.copy(
                                            lineHeight = 22.sp
                                        ),
                                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                                        isUnderlineEnabled = localUnderlineActive,
                                        isHighlighterEnabled = localHighlightActive
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

/**
 * کامپوننت گروهی نمایش چراغ‌های راهنمای درسنامه:
 * مجموعه‌ای فشرده از ۳ چراغ راهنما (قرمز، زرد، سبز) در یک قاب جذاب
 */
@Composable
fun TrafficLightsLessonSummaryCard(
    redTrapPoint: String,
    yellowCautionPoint: String,
    greenGuaranteedPoint: String,
    modifier: Modifier = Modifier,
    cardTitle: String = "🚦 چراغ راهنمای کنکور: تله‌ها، احتیاط‌ها و قواعد قطعی"
) {
    val responsive = LocalResponsiveDimens.current

    Surface(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(responsive.cardCornerRadius),
        color = MaterialTheme.colorScheme.surface,
        border = BorderStroke(1.2.dp, MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.7f)),
        tonalElevation = 2.dp
    ) {
        Column(
            modifier = Modifier.padding(responsive.cardInnerPadding),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            // سربرگ چراغ راهنما
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Surface(
                    color = Color(0xFF1E293B),
                    shape = RoundedCornerShape(8.dp),
                    modifier = Modifier.size(28.dp)
                ) {
                    Box(contentAlignment = Alignment.Center) {
                        Text(
                            text = "🚦",
                            fontSize = 15.sp
                        )
                    }
                }
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = cardTitle,
                    style = MaterialTheme.typography.titleSmall,
                    fontWeight = FontWeight.Black,
                    color = MaterialTheme.colorScheme.onSurface
                )
            }

            // ۱. چراغ قرمز: تله خطرناک
            GoldenKeyPointCalloutCard(
                keyPointText = redTrapPoint,
                level = GoldenPointLevel.TRAFFIC_RED,
                tags = listOf("تله_طراح", "چراغ_قرمز", "کنکور")
            )

            // ۲. چراغ زرد: احتیاط و رد گزینه
            GoldenKeyPointCalloutCard(
                keyPointText = yellowCautionPoint,
                level = GoldenPointLevel.TRAFFIC_YELLOW,
                tags = listOf("شگرد_رد_گزینه", "چراغ_زرد")
            )

            // ۳. چراغ سبز: قاعده قطعی
            GoldenKeyPointCalloutCard(
                keyPointText = greenGuaranteedPoint,
                level = GoldenPointLevel.TRAFFIC_GREEN,
                tags = listOf("قاعده_قطعی", "چراغ_سبز", "۱۰۰_درصد")
            )
        }
    }
}
