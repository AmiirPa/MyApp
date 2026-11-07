package com.example.ui.components

import com.example.data.curriculum.normalizedSubjectId
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.BorderStroke
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
import androidx.compose.material.icons.filled.Bolt
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.ExpandLess
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.material.icons.filled.FormatUnderlined
import androidx.compose.material.icons.filled.Highlight
import androidx.compose.material.icons.filled.Lightbulb
import androidx.compose.material.icons.filled.Psychology
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.WarningAmber
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.curriculum.ZeroLesson
import com.example.ui.theme.LocalAppThemeController
import com.example.ui.theme.LocalResponsiveDimens

/**
 * کارت جامع «نکته‌سنجی و موشکافی طراح کنکور»:
 * کالبدشکافی تله‌ها، واژگان کلیدی خط‌کشی‌شده، ترفندهای رد گزینه و شاه‌کلیدهای مفهومی
 * با خط‌کشی زیر نکات و هایلایتر ماژیکی اختصاصی
 */
@Composable
fun ExamNuanceAnalysisCard(
    lesson: ZeroLesson,
    accentColor: Color,
    modifier: Modifier = Modifier
) {
    val themeController = LocalAppThemeController.current
    val responsive = LocalResponsiveDimens.current
    var isExpanded by remember(lesson.id) { mutableStateOf(true) }

    // استخراج هوشمندانه نکات و تله‌های کنکوری از درس
    val primaryTrap = remember(lesson.id) {
        val trapFromExample = lesson.workedExamples.firstOrNull { it.commonTrapAlert.isNotBlank() }?.commonTrapAlert
        when {
            !trapFromExample.isNullOrBlank() -> trapFromExample
            lesson.whyStudentsStruggle.isNotBlank() -> lesson.whyStudentsStruggle
            else -> "طراح معمولاً با جابه‌جا کردن صورت و مخرج یا نادیده گرفتن علامت‌های منفی و قیدهای استثناء، تله‌گذاری می‌کند."
        }
    }

    val triggerKeywords = remember(lesson.id) {
        extractTriggerKeywordsForLesson(lesson)
    }

    val optionEliminationShortcut = remember(lesson.id) {
        extractEliminationShortcut(lesson)
    }

    val conceptualMasterKey = remember(lesson.id) {
        val firstStepTrick = lesson.steps.firstOrNull { it.memoryTrick.isNotBlank() }?.memoryTrick
        when {
            !firstStepTrick.isNullOrBlank() -> firstStepTrick
            lesson.goldenFormulas.isNotEmpty() -> lesson.goldenFormulas.first()
            lesson.steps.isNotEmpty() -> lesson.steps.first().title
            else -> lesson.subtitle
        }
    }

    val goldenExamAmber = Color(0xFFD97706)
    val trapRed = Color(0xFFDC2626)
    val shortcutCyan = Color(0xFF0284C7)

    Surface(
        modifier = modifier
            .fillMaxWidth()
            .testTag("exam_nuance_analysis_card"),
        shape = RoundedCornerShape(responsive.cardCornerRadius),
        color = MaterialTheme.colorScheme.surface,
        border = BorderStroke(1.2.dp, goldenExamAmber.copy(alpha = 0.45f)),
        tonalElevation = 2.dp
    ) {
        Column(modifier = Modifier.padding(responsive.cardInnerPadding)) {
            // Header Bar
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(8.dp))
                    .clickable { isExpanded = !isExpanded },
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.weight(1f)
                ) {
                    Surface(
                        color = goldenExamAmber,
                        shape = CircleShape,
                        modifier = Modifier.size(32.dp)
                    ) {
                        Box(contentAlignment = Alignment.Center) {
                            Icon(
                                imageVector = Icons.Default.Search,
                                contentDescription = null,
                                tint = Color.White,
                                modifier = Modifier.size(18.dp)
                            )
                        }
                    }
                    Spacer(modifier = Modifier.width(10.dp))
                    Column {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(
                                text = "🎯 جعبه نکته‌سنجی و تله‌های طراح کنکور",
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.Black,
                                color = goldenExamAmber
                            )
                        }
                        Text(
                            text = "موشکافی تله‌ها، خط‌کشی واژگان فریبنده و شگردهای ۱۰ ثانیه‌ای",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }

                IconButton(
                    onClick = { isExpanded = !isExpanded },
                    modifier = Modifier.size(36.dp)
                ) {
                    Icon(
                        imageVector = if (isExpanded) Icons.Default.ExpandLess else Icons.Default.ExpandMore,
                        contentDescription = "باز و بسته کردن نکته‌سنجی",
                        tint = goldenExamAmber
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
                        .padding(top = 10.dp),
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    HorizontalDivider(thickness = 0.8.dp, color = goldenExamAmber.copy(alpha = 0.25f))

                    // 1. تله اصلی طراح (Trap Nuance)
                    Surface(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(10.dp),
                        color = trapRed.copy(alpha = 0.08f),
                        border = BorderStroke(1.dp, trapRed.copy(alpha = 0.35f))
                    ) {
                        Column(
                            modifier = Modifier.padding(10.dp),
                            verticalArrangement = Arrangement.spacedBy(4.dp)
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(
                                    imageVector = Icons.Default.WarningAmber,
                                    contentDescription = null,
                                    tint = trapRed,
                                    modifier = Modifier.size(18.dp)
                                )
                                Spacer(modifier = Modifier.width(6.dp))
                                Text(
                                    text = "⚠️ نکته‌سنجی ۱: کانون تله‌گذاری طراح در این درس",
                                    style = MaterialTheme.typography.titleSmall,
                                    fontWeight = FontWeight.Black,
                                    color = trapRed
                                )
                            }
                            BiDiText(
                                text = primaryTrap,
                                style = MaterialTheme.typography.bodyMedium
                            )
                        }
                    }

                    // 2. واژگان کلیدی فریبنده که باید زیرش خط بکشی (Trigger Keywords)
                    Surface(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(10.dp),
                        color = goldenExamAmber.copy(alpha = 0.08f),
                        border = BorderStroke(1.dp, goldenExamAmber.copy(alpha = 0.35f))
                    ) {
                        Column(
                            modifier = Modifier.padding(10.dp),
                            verticalArrangement = Arrangement.spacedBy(6.dp)
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(
                                    imageVector = Icons.Default.FormatUnderlined,
                                    contentDescription = null,
                                    tint = goldenExamAmber,
                                    modifier = Modifier.size(18.dp)
                                )
                                Spacer(modifier = Modifier.width(6.dp))
                                Text(
                                    text = "✍️ نکته‌سنجی ۲: واژگان حساسی که باید در صورت تست زیرش خط بکشی!",
                                    style = MaterialTheme.typography.titleSmall,
                                    fontWeight = FontWeight.Black,
                                    color = goldenExamAmber
                                )
                            }
                            BiDiText(
                                text = triggerKeywords,
                                style = MaterialTheme.typography.bodyMedium
                            )
                        }
                    }

                    // 3. شگرد رد گزینه در ۱۰ ثانیه (Option Elimination)
                    Surface(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(10.dp),
                        color = shortcutCyan.copy(alpha = 0.08f),
                        border = BorderStroke(1.dp, shortcutCyan.copy(alpha = 0.35f))
                    ) {
                        Column(
                            modifier = Modifier.padding(10.dp),
                            verticalArrangement = Arrangement.spacedBy(4.dp)
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(
                                    imageVector = Icons.Default.Bolt,
                                    contentDescription = null,
                                    tint = shortcutCyan,
                                    modifier = Modifier.size(18.dp)
                                )
                                Spacer(modifier = Modifier.width(6.dp))
                                Text(
                                    text = "⚡ نکته‌سنجی ۳: شگرد رد گزینه در ۱۰ ثانیه",
                                    style = MaterialTheme.typography.titleSmall,
                                    fontWeight = FontWeight.Black,
                                    color = shortcutCyan
                                )
                            }
                            BiDiText(
                                text = optionEliminationShortcut,
                                style = MaterialTheme.typography.bodyMedium
                            )
                        }
                    }

                    // 4. شاه‌کلید مفهومی (Conceptual Master-Key)
                    Surface(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(10.dp),
                        color = accentColor.copy(alpha = 0.08f),
                        border = BorderStroke(1.dp, accentColor.copy(alpha = 0.35f))
                    ) {
                        Column(
                            modifier = Modifier.padding(10.dp),
                            verticalArrangement = Arrangement.spacedBy(4.dp)
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(
                                    imageVector = Icons.Default.Lightbulb,
                                    contentDescription = null,
                                    tint = accentColor,
                                    modifier = Modifier.size(18.dp)
                                )
                                Spacer(modifier = Modifier.width(6.dp))
                                Text(
                                    text = "💡 نکته‌سنجی ۴: شاه‌کلید مفهومی بدون استثناء",
                                    style = MaterialTheme.typography.titleSmall,
                                    fontWeight = FontWeight.Black,
                                    color = accentColor
                                )
                            }
                            BiDiText(
                                text = conceptualMasterKey,
                                style = MaterialTheme.typography.bodyMedium
                            )
                        }
                    }

                    // نواره تنظیمات سریع خواندن و مطالعه در انتهای کارت
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 2.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        // کلید خط‌کشی زیر نکات
                        Surface(
                            modifier = Modifier
                                .clip(RoundedCornerShape(50))
                                .clickable {
                                    themeController.updateUnderlineKeyPoints(!themeController.isUnderlineKeyPointsEnabled)
                                },
                            color = if (themeController.isUnderlineKeyPointsEnabled)
                                goldenExamAmber.copy(alpha = 0.15f)
                            else
                                MaterialTheme.colorScheme.surfaceVariant,
                            border = BorderStroke(
                                0.8.dp,
                                if (themeController.isUnderlineKeyPointsEnabled) goldenExamAmber else Color.Transparent
                            )
                        ) {
                            Row(
                                modifier = Modifier.padding(horizontal = 10.dp, vertical = 5.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Icon(
                                    imageVector = Icons.Default.FormatUnderlined,
                                    contentDescription = null,
                                    tint = if (themeController.isUnderlineKeyPointsEnabled) goldenExamAmber else MaterialTheme.colorScheme.onSurfaceVariant,
                                    modifier = Modifier.size(14.dp)
                                )
                                Spacer(modifier = Modifier.width(4.dp))
                                Text(
                                    text = if (themeController.isUnderlineKeyPointsEnabled) "خط‌کشی زیر نکات: فعال ✓" else "خط‌کشی زیر نکات: خاموش",
                                    style = MaterialTheme.typography.labelSmall,
                                    fontWeight = FontWeight.Bold,
                                    color = if (themeController.isUnderlineKeyPointsEnabled) goldenExamAmber else MaterialTheme.colorScheme.onSurfaceVariant
                                )
                            }
                        }

                        // کلید هایلایتر ماژیکی
                        Surface(
                            modifier = Modifier
                                .clip(RoundedCornerShape(50))
                                .clickable {
                                    themeController.updateHighlighter(!themeController.isHighlighterEnabled)
                                },
                            color = if (themeController.isHighlighterEnabled)
                                Color(0xFFFEF08A).copy(alpha = 0.35f)
                            else
                                MaterialTheme.colorScheme.surfaceVariant,
                            border = BorderStroke(
                                0.8.dp,
                                if (themeController.isHighlighterEnabled) Color(0xFFD97706) else Color.Transparent
                            )
                        ) {
                            Row(
                                modifier = Modifier.padding(horizontal = 10.dp, vertical = 5.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Highlight,
                                    contentDescription = null,
                                    tint = if (themeController.isHighlighterEnabled) Color(0xFFB45309) else MaterialTheme.colorScheme.onSurfaceVariant,
                                    modifier = Modifier.size(14.dp)
                                )
                                Spacer(modifier = Modifier.width(4.dp))
                                Text(
                                    text = if (themeController.isHighlighterEnabled) "هایلایت ماژیکی: فعال ✓" else "هایلایت ماژیکی: خاموش",
                                    style = MaterialTheme.typography.labelSmall,
                                    fontWeight = FontWeight.Bold,
                                    color = if (themeController.isHighlighterEnabled) Color(0xFFB45309) else MaterialTheme.colorScheme.onSurfaceVariant
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

private fun extractTriggerKeywordsForLesson(lesson: ZeroLesson): String {
    val sub = lesson.normalizedSubjectId().lowercase()
    return when {
        sub.contains("math") ->
            "• واژگان دام‌ساز ریاضی: «صرفاً مقادیر نامنفی»، «دامنه مجاز»، «علامت منفی پشت پرانتز»، «تعداد جملات زوج/فرد»."
        sub.contains("arabic") ->
            "• واژگان دام‌ساز عربی: «حرکت ضمه/فتحه در فاعل و مفعول»، «لا نفی جنس و اسم بدون تنوین»، «افعال ناقصه»، «ترجمه به شکل ماضی نقلی/استمراری»."
        sub.contains("economics") ->
            "• واژگان دام‌ساز اقتصاد: «درآمد حقیقی در برابر اسمی»، «هزینه فرصت»، «سود خالص در برابر ناخالص»، «نرخ بار تکفل»."
        sub.contains("psychology") ->
            "• واژگان دام‌ساز روان‌شناسی: «تقویت منفی در برابر تنبیه»، «تداخل پیش‌گستر در برابر پس‌گستر»، «مقابله مسئله‌مدار در برابر هیجان‌مدار»، «مراحل رشد پیاژه»."
        sub.contains("sociology") || sub.contains("social_identity") ->
            "• واژگان دام‌ساز جامعه‌شناسی و هویت: «رویکرد تبیینی در برابر تفسیری و انتقادی»، «فرهنگ آرمانی در برابر واقعی»، «هویت انتسابی در برابر اکتسابی»."
        sub.contains("logic") ->
            "• واژگان دام‌ساز منطق: «مربع تقابل (تناقض، تضاد، تداخل)»، «عکس مستوی سالبه جزئی»، «قیاس اقترانی در برابر استثنایی»."
        sub.contains("philosophy") ->
            "• واژگان دام‌ساز فلسفه: «اصالت وجود در برابر اصالت ماهیت»، «علت تامه در برابر معدّه»، «مشاء، اشراق و حکمت متعالیه»."
        sub.contains("arooz") || sub.contains("fonoon") ->
            "• واژگان دام‌ساز عروض و فنون: «هجای کشیده سه حرفی»، «اختیار شاعری تسکین»، «تشبیه بلیغ در برابر استعاره مکنیه»."
        sub.contains("farsi") || sub.contains("negaresh") ->
            "• واژگان دام‌ساز فارسی و نگارش: «مضاف‌الیه در برابر صفت بیانی»، «تکواژ تصریفی در برابر اشتقاقی»، «مسند و فعل اسنادی»."
        sub.contains("history") ->
            "• واژگان دام‌ساز تاریخ: «توالی زمانی سلسله‌ها»، «مفاد عهدنامه‌های دوره قاجار»، «اصلاحات امیرکبیر و مشروطه»."
        sub.contains("geography") ->
            "• واژگان دام‌ساز جغرافیا: «مقیاس بزرگ در برابر مقیاس کوچک»، «اختلاف ساعت نصف‌النهارها»، «سنجش از دور (RS) در برابر GIS»."
        sub.contains("english") ->
            "• واژگان دام‌ساز زبان انگلیسی: «معلوم در برابر مجهول (to be + p.p)»، «ضمایر موصولی who/whom/whose»، «شرطی نوع ۱ و ۲»."
        sub.contains("dini") || sub.contains("farhangian") ->
            "• واژگان دام‌ساز دینی و فرهنگیان: «توحید افعالی در برابر توحید عملی»، «الامر بین الامرین»، «ساحت‌های شش‌گانه سند تحول بنیادین»."
        else ->
            "• واژگان کلیدی تست‌ساز: «همواره»، «صرفاً»، «به‌جز»، «بیشترین»، «کمترین»، «رابطه مستقیم/معکوس»، «علت»، «نتیجه»."
    }
}

private fun extractEliminationShortcut(lesson: ZeroLesson): String {
    val sub = lesson.normalizedSubjectId().lowercase()
    return when {
        sub.contains("math") ->
            "۱. با جایگذاری گزینه عدد ۰ یا ۱ در معادله، فوراً ۲ گزینه غلط را رد کن.\n۲. به علامت منفی در ضریب توجه کن تا جهت نمودار فوراً مشخص شود."
        sub.contains("arabic") ->
            "۱. اول زمان فعل را نگاه کن (ماضی، مضارع، منفی). با دیدن زمان فعل ۲ گزینه بلافاصله رد می‌شوند.\n۲. مفرد و جمع بودن اسم‌ها و مضاف‌الیه را چک کن."
        sub.contains("economics") ->
            "۱. هر جا صحبت از سال پایه شد، تورم را صفر در نظر بگیر (قیمت سال پایه × مقدار جاری).\n۲. در سود اقتصادی، همیشه هزینه‌های آشکار و پنهان (فرصت) را از درآمد کل کم کن."
        sub.contains("psychology") ->
            "۱. اگر رفتار مطلوب افزایش یافته است، گزینه‌های «تنبیه» را خط بزن (تقویت مثبت یا منفی است).\n۲. در تداخل حافظه، به کلمه بعد از «فراموشیِ...» نگاه کن (جدید = پیش‌گستر، قدیم = پس‌گستر)."
        sub.contains("sociology") || sub.contains("social_identity") ->
            "۱. کلیدواژه «کشف قانون، کمّی و مشاهده حسی» دیدی = تبیینی؛ «فهم معنا و نیت» = تفسیری؛ «رهایی و عدالت» = انتقادی.\n۲. ویژگی‌های غیرارادی و طبیعی را از دایره کنش اجتماعی خارج کن."
        sub.contains("logic") ->
            "۱. اگر یکی از مقدمات منفی یا جزئی باشد، نتیجه قیاس حتماً منفی یا جزئی است.\n۲. علامت سور (هر/هیچ/بعضی) را در ۵ ثانیه تطبیق بده."
        sub.contains("philosophy") ->
            "۱. هر گزینه را با فیلسوف و مکتب اصلی‌اش (مشاء = برهان، اشراق = شهود، متعالیه = وحدت برهان و عرفان و قرآن) تطبیق بده."
        sub.contains("arooz") || sub.contains("fonoon") ->
            "۱. ۴ هجای اول بیت را با ضربه ریتمیک تقطیع کن.\n۲. در آرایه‌ها، اگر هر دو طرف تشبیه حاضرند استعاره را خط بزن."
        sub.contains("farsi") || sub.contains("negaresh") ->
            "۱. بین دو اسم کسره‌دار کلمه «است» بگذار: اگر معنی داد صفت بیانی است، اگر نداد مضاف‌الیه.\n۲. جمع‌ها (ها، ان) و ضمایر متصل، واژه را مشتق نمی‌کنند (ساده می‌ماند)."
        else ->
            "۱. گزینه‌هایی که ادعای افراطی دارند («همیشه»، «هیچ‌وقت»، «به‌هیچ‌وجه») در ۹۰٪ موارد گزینه‌های غلط طراح هستند.\n۲. گزینه هم‌معنی با صورت سوال را انتخاب نکن، گزینه علت یا پیامد را انتخاب کن."
    }
}
