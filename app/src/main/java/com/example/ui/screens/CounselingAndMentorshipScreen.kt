package com.example.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.Bolt
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.EmojiEvents
import androidx.compose.material.icons.filled.ExpandLess
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.material.icons.filled.Lightbulb
import androidx.compose.material.icons.filled.Psychology
import androidx.compose.material.icons.filled.Quiz
import androidx.compose.material.icons.filled.School
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.Timer
import androidx.compose.material.icons.filled.TipsAndUpdates
import androidx.compose.material.icons.filled.WarningAmber
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FilterChip
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.SecondaryTabRow
import androidx.compose.material3.Surface
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.AppTab
import com.example.ui.KonkoorUiState
import com.example.ui.components.BiDiText
import com.example.ui.components.BidiFormatter
import com.example.ui.theme.resolveDynamicAccent

data class CounselingArticle(
    val id: String,
    val tabCategory: Int, // 0: یادگیری، 1: برنامه‌ریزی، 2: تست‌زنی، 3: استرس، 4: داستان رتبه‌برترها
    val title: String,
    val subtitle: String,
    val badgeTag: String,
    val badgeColor: Color,
    val coreConcept: String,
    val stepByStepInstructions: List<String>,
    val goldenSecret: String,
    val suitableForSoldierTeachers: String
)

// فهرست مقاله‌ها یک‌بار برای کل فرآیند ساخته می‌شود (lazy): پیش‌تر با
// remember ساخته می‌شد و با هر بار خروج از ترکیب — مثلاً رفتن به زیربخش
// دیگر تب «بیشتر» و برگشتن — از نو ساخته می‌شد.
private val comprehensiveCounselingArticles: List<CounselingArticle> by lazy {
    buildComprehensiveCounselingArticles()
}

private val counselingCategories = listOf(
    0 to "تکنیک‌های یادگیری",
    1 to "مدیریت زمان و تمرکز",
    2 to "استراتژی تست‌زنی",
    3 to "کنترل استرس",
    4 to "تجربیات رتبه‌ها",
    5 to "کتابخانه و منابع"
)

@OptIn(ExperimentalLayoutApi::class, androidx.compose.material3.ExperimentalMaterial3Api::class)
@Composable
fun CounselingAndMentorshipScreen(
    uiState: KonkoorUiState,
    onNavigateTab: (AppTab) -> Unit
) {
    var selectedCategoryTab by remember { mutableIntStateOf(0) }

    val filteredArticles = remember(selectedCategoryTab) {
        comprehensiveCounselingArticles.filter { it.tabCategory == selectedCategoryTab }
    }

    val categories = counselingCategories

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .testTag("screen_counseling_hub"),
        contentPadding = PaddingValues(start = 20.dp, top = 12.dp, end = 20.dp, bottom = 92.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        item {
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
                    Column(
                        modifier = Modifier.weight(1f),
                        verticalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        Text(
                            text = "مشاوره و روش مطالعه",
                            style = MaterialTheme.typography.titleLarge,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                        Text(
                            text = "راهنمای علمی مطالعه، مدیریت زمان، و استراتژی آزمون",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                    Box(
                        modifier = Modifier
                            .size(52.dp)
                            .background(
                                MaterialTheme.colorScheme.surface.copy(alpha = 0.65f),
                                CircleShape
                            ),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.Psychology,
                            contentDescription = null,
                            tint = resolveDynamicAccent(MaterialTheme.colorScheme.primary),
                            modifier = Modifier.size(28.dp)
                        )
                    }
                }
            }
        }

        item {
            ScrollableTabRow(
                selectedTabIndex = selectedCategoryTab,
                edgePadding = 0.dp,
                containerColor = Color.Transparent,
                contentColor = MaterialTheme.colorScheme.primary,
                indicator = {},
                divider = {}
            ) {
                categories.forEach { (idx, title) ->
                    val isSelected = selectedCategoryTab == idx
                    Surface(
                        modifier = Modifier
                            .padding(end = 8.dp)
                            .clip(RoundedCornerShape(10.dp))
                            .clickable { selectedCategoryTab = idx },
                        shape = RoundedCornerShape(10.dp),
                        color = if (isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.surface,
                        border = BorderStroke(
                            1.dp,
                            if (isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.outlineVariant
                        )
                    ) {
                        Text(
                            text = title,
                            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
                            fontSize = 12.sp,
                            color = if (isSelected) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.onSurfaceVariant,
                            maxLines = 1,
                            softWrap = false,
                            modifier = Modifier.padding(horizontal = 14.dp, vertical = 8.dp)
                        )
                    }
                }
            }
        }

        item {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(4.dp)
                    .background(
                        Brush.linearGradient(
                            listOf(
                                resolveDynamicAccent(MaterialTheme.colorScheme.primary).copy(alpha = 0.45f),
                                MaterialTheme.colorScheme.secondary.copy(alpha = 0.45f),
                                MaterialTheme.colorScheme.tertiary.copy(alpha = 0.45f)
                            )
                        ),
                        RoundedCornerShape(2.dp)
                    )
            )
        }

        if (selectedCategoryTab == 5) {
            item(key = "counseling_resources_hub") {
                com.example.ui.components.ComprehensiveStudyResourcesHubCard(
                    onNavigateTab = onNavigateTab
                )
            }
        } else {
            filteredArticles.forEachIndexed { index, article ->
                item(key = article.id) {
                    CounselingArticleCard(
                        article = article,
                        initiallyExpanded = index == 0
                    )
                }
            }
        }
    }
}

@Composable
private fun CounselingArticleCard(
    article: CounselingArticle,
    initiallyExpanded: Boolean = false
) {
    var isExpanded by remember(article.id) { mutableStateOf(initiallyExpanded) }

    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(20.dp))
            .clickable { isExpanded = !isExpanded }
            .testTag("counseling_card_${article.id}"),
        shape = RoundedCornerShape(20.dp),
        color = MaterialTheme.colorScheme.surface,
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(5.dp)
                    .background(
                        Brush.linearGradient(
                            listOf(
                                MaterialTheme.colorScheme.primaryContainer,
                                MaterialTheme.colorScheme.secondaryContainer
                            )
                        ),
                        RoundedCornerShape(3.dp)
                    )
            )
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Surface(
                    shape = RoundedCornerShape(8.dp),
                    color = MaterialTheme.colorScheme.primaryContainer
                ) {
                    Text(
                        text = article.badgeTag,
                        style = MaterialTheme.typography.labelSmall,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onPrimaryContainer,
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                    )
                }
                Icon(
                    imageVector = if (isExpanded) Icons.Default.ExpandLess else Icons.Default.ExpandMore,
                    contentDescription = if (isExpanded) "بستن" else "مشاهده جزئیات",
                    tint = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }

            Text(
                text = article.title,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface
            )

            Text(
                text = article.subtitle,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            // بدون AnimatedVisibility: انیمیشن expandVertically داخل LazyColumn
            // محتوای بازشده (چند BiDiText بلند با حروف‌چینی فارسی) را در هر فریم
            // انیمیشن از نو اندازه‌گیری می‌کرد و هم‌زمان با دو fade تودرتوی
            // تعویض تب، ترکیب اول تب «بیشتر» را قفل می‌کرد؛ همان دلیلی که در
            // هاب منابع هم AnimatedVisibility حذف شده است. محتوا عیناً همان است.
            if (isExpanded) {
                Column(
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    HorizontalDivider(color = MaterialTheme.colorScheme.outlineVariant)

                    Surface(
                        color = MaterialTheme.colorScheme.background,
                        shape = RoundedCornerShape(12.dp),
                        border = BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Column(
                            modifier = Modifier.padding(12.dp),
                            verticalArrangement = Arrangement.spacedBy(4.dp)
                        ) {
                            Text(
                                text = "اصل علمی این روش:",
                                style = MaterialTheme.typography.labelMedium,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.primary
                            )
                            BiDiText(
                                text = article.coreConcept,
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.onSurface
                            )
                        }
                    }

                    Text(
                        text = "مراحل اجرای گام‌به‌گام:",
                        style = MaterialTheme.typography.labelMedium,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary
                    )

                    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                        article.stepByStepInstructions.forEachIndexed { idx, step ->
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                verticalAlignment = Alignment.Top
                            ) {
                                Surface(
                                    color = MaterialTheme.colorScheme.primaryContainer,
                                    shape = CircleShape,
                                    modifier = Modifier.size(22.dp)
                                ) {
                                    Box(contentAlignment = Alignment.Center) {
                                        Text(
                                            text = BidiFormatter.toPersianDigits((idx + 1).toString()),
                                            style = MaterialTheme.typography.labelSmall,
                                            fontWeight = FontWeight.Bold,
                                            color = MaterialTheme.colorScheme.onPrimaryContainer
                                        )
                                    }
                                }
                                Spacer(modifier = Modifier.width(8.dp))
                                BiDiText(
                                    text = step,
                                    style = MaterialTheme.typography.bodySmall,
                                    color = MaterialTheme.colorScheme.onSurface,
                                    modifier = Modifier.weight(1f)
                                )
                            }
                        }
                    }

                    if (article.suitableForSoldierTeachers.isNotBlank()) {
                        Surface(
                            color = MaterialTheme.colorScheme.secondaryContainer,
                            shape = RoundedCornerShape(12.dp),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Column(
                                modifier = Modifier.padding(12.dp),
                                verticalArrangement = Arrangement.spacedBy(4.dp)
                            ) {
                                Text(
                                    text = "ویژه داوطلبان شاغل و سربازمعلم‌ها:",
                                    style = MaterialTheme.typography.labelSmall,
                                    fontWeight = FontWeight.Bold,
                                    color = MaterialTheme.colorScheme.onSecondaryContainer
                                )
                                BiDiText(
                                    text = article.suitableForSoldierTeachers,
                                    style = MaterialTheme.typography.bodySmall,
                                    color = MaterialTheme.colorScheme.onSecondaryContainer
                                )
                            }
                        }
                    }

                    Surface(
                        color = MaterialTheme.colorScheme.primaryContainer,
                        shape = RoundedCornerShape(12.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        BiDiText(
                            text = "نکته کلیدی: ${article.goldenSecret}",
                            style = MaterialTheme.typography.labelMedium,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onPrimaryContainer,
                            modifier = Modifier.padding(12.dp)
                        )
                    }
                }
            }
        }
    }
}

private fun buildComprehensiveCounselingArticles(): List<CounselingArticle> = listOf(
    CounselingArticle(
        id = "art_feynman_technique",
        tabCategory = 0,
        title = "۱. تکنیک یادگیری فاینمن (آموزش به کودک ۶ ساله)",
        subtitle = "چگونه سخت‌ترین مباحث فلسفه، عربی و منطق را طوری یاد بگیریم که هرگز فراموش نشود؟",
        badgeTag = "تکنیک یادگیری",
        badgeColor = Color(0xFF8B5CF6),
        coreConcept = "ریچارد فاینمن (برنده جایزه نوبل) معتقد بود اگر نتوانید مبحثی را به زبان ساده برای یک کودک یا شخص ناشناس توضیح دهید، خودتان هم آن را کاملاً نفهمیده‌اید!",
        stepByStepInstructions = listOf(
            "مبحث مورد نظر (مثلاً باب‌های مزید عربی یا مربع تقابل منطق) را ۵ دقیقه بخوانید.",
            "کتاب را ببندید و فرض کنید می‌خواهید آن را به دوستتان که هیچی از کنکور نمی‌داند یاد بدهید.",
            "جاهایی که هنگام توضیح دادن گیر می‌کنید یا کلمات گنگ استفاده می‌کنید را علامت بزنید.",
            "دوباره به کتاب برگردید و فقط همان نقطه گنگ را شفاف کنید تا کاملاً ساده شود."
        ),
        goldenSecret = "توضیح دادن به زبان ساده، اتصالات عصبی مغز را ۳ برابر قوی‌تر می‌کند.",
        suitableForSoldierTeachers = "در مدرسه می‌توانید مباحث ساده را برای دانش‌آموزانتان توضیح دهید؛ این کار تسلط خودتان را ۱۰۰٪ می‌کند!"
    ),
    CounselingArticle(
        id = "art_cornell_notes",
        tabCategory = 0,
        title = "۲. خلاصه‌نویسی به روش استاندارد کرنل (Cornell)",
        subtitle = "جایگزین کردن دفترچه‌های شلوغ و وقت‌گیر با برگه ۳ بخش کرنل",
        badgeTag = "خلاصه‌نویسی",
        badgeColor = Color(0xFF3B82F6),
        coreConcept = "خلاصه‌نویسی به سبک کپی کردن از روی کتاب بزرگ‌ترین اشتباه است. روش کرنل برگه کاغذ را به ۳ ستون اصلی تقسیم می‌کند.",
        stepByStepInstructions = listOf(
            "ستون سمت راست (بزرگ): فقط فرمول‌ها، نمودارها و نکات اصلی درس را فشرده بنویسید.",
            "ستون سمت چپ (کوچک): سوالات کلیدی و شاه‌کلیدها را بنویسید.",
            "بخش پایین برگه: خلاصه ۲ خطی از کل صفحه به زبان کاملاً خودمونی بنویسید."
        ),
        goldenSecret = "در هفته‌های منتهی به کنکور، فقط ستون سمت چپ (سوالات) را می‌خوانید و جواب را بازیابی می‌کنید.",
        suitableForSoldierTeachers = "برگه‌های کرنل به دلیل فشرده بودن، بهترین ابزار مرور سریع ۵ دقیقه‌ای در زنگ‌های تفریح مدرسه هستند."
    ),
    CounselingArticle(
        id = "art_soldier_teacher_routine",
        tabCategory = 1,
        title = "۳. مدیریت زمان اختصاصی سربازمعلم‌ها و داوطلبان شاغل",
        subtitle = "چگونه با روزی ۵ ساعت مطالعه در روزهای عادی و ۱۰ ساعت در آخر هفته، رتبه زیر ۱۰۰ بیاوریم؟",
        badgeTag = "مدیریت زمان",
        badgeColor = Color(0xFF10B981),
        coreConcept = "کیفیت مطالعه و استمرار روزانه بسیار مهم‌تر از ساعت‌های طولانی و بی‌کیفیت است. ۵ ساعت مطالعه متمرکز با مغز استراحت‌کرده، بازدهی ۱۰ ساعت مطالعه پراکنده را دارد.",
        stepByStepInstructions = listOf(
            "ساعت ۱۴:۰۰ تا ۱۶:۰۰: بازگشت از مدرسه، ناهار و ۱ ساعت استراحت/خواب قیلوله حتمی.",
            "پارت اول (۱۶:۰۰ تا ۱۷:۴۵): سنگین‌ترین درس اختصاصی (عربی، ریاضی یا فنون).",
            "پارت دوم (۱۸:۰۰ تا ۱۹:۴۵): درس مفهومی انسانی (جامعه‌شناسی، روانشناسی، فلسفه).",
            "پارت سوم (۲۰:۰۰ تا ۲۱:۱۵): دفترچه فرهنگیان (مهارت معلمی و هوش تحلیلی).",
            "پارت چهارم شبانه (۲۱:۴۵ تا ۲۳:۰۰): مرور درختواره تاریخ و لایتنر."
        ),
        goldenSecret = "خواب قیلوله ۳۰ دقیقه‌ای بعد از ظهر، خستگی تدریس مدرسه را کاملاً بشوید و مغز را شارژ می‌کند.",
        suitableForSoldierTeachers = "پنجشنبه و جمعه‌ها که تعطیل هستید، تا ساعت ۱۰:۳۰ صبح راحت بخوابید و سپس ۴ پارت ۲ ساعته مطالعه کنید."
    ),
    CounselingArticle(
        id = "art_cross_minus_strategy",
        tabCategory = 2,
        title = "۴. استراتژی زمان‌های نقصانی و تکنیک ضربدر-منها",
        subtitle = "چگونه در جلسه کنکور ۱۵ تا ۲۰ دقیقه زمان ذخیره بوجود آوریم؟",
        badgeTag = "تکنیک آزمون",
        badgeColor = Color(0xFFF59E0B),
        coreConcept = "همه سوالات کنکور هم‌نمره هستند! حل یک سوال سخت ریاضی که ۵ دقیقه وقت می‌برد با یک سوال ۲۰ ثانیه‌ای جامعه‌شناسی دقیقا یک درصد دارد.",
        stepByStepInstructions = listOf(
            "دور اول: فقط سوالاتی که در کمتر از ۳۰ ثانیه جوابشان را بلدید را حل کنید.",
            "سوالات وقت‌گیر ولی بلد: علامت «ضربدر ✖» بزنید و بگذرید.",
            "سوالات سخت، گنگ یا ناصحیح: علامت «منها ➖» بزنید و کلا فراموشش کنید.",
            "در ۱۰ دقیقه پایانی دفترچه: برگردید و فقط سوالات ضربدردار را حل کنید."
        ),
        goldenSecret = "با زدن علامت منها روی سوالات سخت، عذاب وجدان را از بین می‌برید و استرس صفر می‌شود.",
        suitableForSoldierTeachers = "این تکنیک را در تمام آزمون‌های تک‌درسی و جامع اپلیکیشن تمرین کنید."
    ),
    CounselingArticle(
        id = "art_exam_anxiety_cure",
        tabCategory = 3,
        title = "۵. غلبه بر استرس جلسه کنکور و شب قبل از آزمون",
        subtitle = "تنفس ۴-۷-۸ و پاکسازی ذهن از افکار منفی ناتوانی",
        badgeTag = "مدیریت استرس",
        badgeColor = Color(0xFFEF4444),
        coreConcept = "استرس شدید ترشح کورتیزول را بالا برده و دسترسی به حافظه بلندمدت را موقتاً مسدود می‌کند. با تکنیک‌های فیزیولوژیک می‌توان در ۲۰ ثانیه ضربان قلب را آرام کرد.",
        stepByStepInstructions = listOf(
            "تکنیک تنفس ۴-۷-۸: ۴ ثانیه دم عمیق از بینی ⬅️ ۷ ثانیه حبس نفس ⬅️ ۸ ثانیه بازدم آرام از دهان.",
            "شب قبل از آزمون: از ساعت ۲۰:۰۰ کتب را ببندید و فقط وسایل صندلی کنکور را آماده کنید.",
            "اگر سر جلسه به یک سوال سخت برخوردید: لبخند بزنید و بگوئید «این سوال برای همه سخت است، میرم سوال بعدی!»."
        ),
        goldenSecret = "سختی سوالات برای همه یکسان است؛ کسی رتبه برتر می‌شود که خونسردی‌اش را حفظ کند.",
        suitableForSoldierTeachers = "شما به عنوان معلم تجربه حضور در کلاس درس را دارید؛ این تسلط به شما اعتمادبه‌نفس بالایی می‌دهد."
    ),
    CounselingArticle(
        id = "art_top_ranker_story_1",
        tabCategory = 4,
        title = "۶. روایت رتبه ۳ کنکور انسانی: از درصد منفی عربی تا ۱۰۰٪ کنکور",
        subtitle = "داوطلبی که در آزمون اول عربی را منفی ۱۵٪ زده بود ولی به رتبه تک‌رقمی رسید!",
        badgeTag = "روایت رتبه برتر",
        badgeColor = Color(0xFFD97706),
        coreConcept = "«من تا پاییز دوازدهم فکر می‌کردم عربی سخت‌ترین درس جهان است؛ چون همه قواعد را با هم می‌خواندم. وقتی صیغه‌ها را با انگشتان دست یاد گرفتم و ۸ باب را به کلمات فارسی ربط دادم، عربی شیرین‌ترین درسم شد.»",
        stepByStepInstructions = listOf(
            "ابتدا ۱ هفته فقط روی ضمایر و صیغه‌ها وقت گذاشت.",
            "برای ترجمه هرگز متن را کامل معنی نمی‌کرد؛ فقط زمان فعل و معرفه/نکره را چک می‌کرد.",
            "هر روز شب‌ها ۱۰ تست عربی زمان‌دار می‌زد."
        ),
        goldenSecret = "عربی درس تکنیکی است؛ وقتی قفل چشمی افعال را یاد بگیرید، ۵۰٪ راه را رفته‌اید.",
        suitableForSoldierTeachers = "مهم نیست الان در چه درصدی هستید؛ با استمرار ۳ ماهه می‌توانید عربی را بالای ۸۰٪ بزنید."
    )
)
