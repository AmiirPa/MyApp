package com.example.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.rememberScrollState
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
import androidx.compose.material.icons.filled.AutoStories
import androidx.compose.material.icons.filled.Calculate
import androidx.compose.material.icons.filled.ExpandLess
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.material.icons.filled.Language
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.Translate
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
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
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.theme.LocalResponsiveDimens

/**
 * مدل متدهای فوق‌قوی ۵ درس اصلی انسانی که بیشترین درصد رد در کنکور را دارند
 */
data class FiveSubjectMethodInfo(
    val id: String,
    val subjectNameFa: String,
    val shortTabTitle: String,
    val methodTitle: String,
    val iconVector: ImageVector,
    val primaryColor: Color,
    val quickSummary: String,
    val stepByStepGuide: List<Pair<String, String>>,
    val goldenRuleText: String
)

object FiveSubjectsMethodRepository {

    val methods: List<FiveSubjectMethodInfo> = listOf(
        // ۱. ریاضی و آمار انسانی
        FiveSubjectMethodInfo(
            id = "method_math",
            subjectNameFa = "ریاضی و آمار انسانی",
            shortTabTitle = "ریاضی",
            methodTitle = "⚡ متد عددگذاری و حل ۲ خطی (بدون محاسبات فرمولی)",
            iconVector = Icons.Default.Calculate,
            primaryColor = Color(0xFF2563EB),
            quickSummary = "حل ۹۰٪ تست‌های ریاضی و آمار فقط با جای‌گذاری اعداد ساده (۰، ۱، ۲) و نگاه چشمی به ضریب x² و اعداد تنهای انتهایی.",
            stepByStepGuide = listOf(
                "گام ۱: جای‌گذاری عدد" to "اگر سوال درباره تابع، معادله یا طول/عرض نقاط بود، به جای x عدد ۱ یا ۰ بگذار و عدد خروجی را یادداشت کن.",
                "گام ۲: چک کردن ۴ گزینه" to "همان عدد ۱ یا ۰ را در ۴ گزینه قرار بده؛ گزینه‌ای که همان عدد خروجی را بدهد، پاسخ قطعی تست است!",
                "گام ۳: جمع ضرایب در ۳ ثانیه" to "در ax² + bx + c = 0 اگر a + b + c = 0 بود، ریشه‌ها بدون حل ۱ و c/a هستند! اگر a - b + c = 0 بود، ریشه‌ها ۱- و c/a- هستند."
            ),
            goldenRuleText = "در ریاضی انسانی هرگز وارد حل طولانی معادلات نشوید! یا عددگذاری کنید یا از فرمول جمع ضرایب استفاده کنید."
        ),

        // ۲. عربی تخصصی و عمومی نهایی
        FiveSubjectMethodInfo(
            id = "method_arabic",
            subjectNameFa = "عربی تخصصی و عمومی",
            shortTabTitle = "عربی",
            methodTitle = "⚡ متد رد گزینه ۳ ثانیه‌ای ترجمه و قواعد بدون نیاز به حفظ معنی کلمات",
            iconVector = Icons.Default.Translate,
            primaryColor = Color(0xFF059669),
            quickSummary = "حذف فوری ۳ گزینه غلط در تست‌های ترجمه فقط با چک کردن زمان فعل، ضمایر و معرفه/نکره بدون خواندن کل بیت یا عبارت.",
            stepByStepGuide = listOf(
                "گام ۱: نگاه به اولین و آخرین فعل" to "زمان فعل (ماضی، مضارع، نهی، امر، لَم + مضارع، لَن + مضارع) را در ۴ گزینه چک کن. ۲ گزینه اشتباه فوراً حذف می‌شوند!",
                "گام ۲: چک کردن ضمایر (ـه / ـها / ـهم)" to "ضمایر به متصل بودند یا منفصل؟ (او / آنان / شما). گزینه بدون ضمیر یا با ضمیر اشتباه حذف می‌شود.",
                "گام ۳: لای نفی جنس و مفعول مطلق" to "لا + اسم نکره با یک فتحه (ـَ) = لای نفی جنس | مصدر هم‌ریشه تنها = مفعول مطلق تأکیدی | مصدر + صفت/مضاف‌الیه = مفعول مطلق نوعی."
            ),
            goldenRuleText = "در عربی کل عبارت را معنی نکنید! فقط فعل ⟵ ضمیر ⟵ معرفه/نکره را با گزینه‌ها تطبیق دهید."
        ),

        // ۳. علوم و فنون ادبی، عروض و قافیه
        FiveSubjectMethodInfo(
            id = "method_fonoon_arooz",
            subjectNameFa = "علوم و فنون ادبی و عروض",
            shortTabTitle = "فنون ادبی",
            methodTitle = "⚡ متد تقطیع چشمی هجاها و فرمول معکوس‌سازی ۲۴ آرایه ادبی",
            iconVector = Icons.Default.AutoStories,
            primaryColor = Color(0xFFD97706),
            quickSummary = "کشف فوری وزن عروضی با چک کردن علامت هجای سوم و ششم و تشخیص ۲۴ آرایه ادبی با معکوس‌سازی ۲ کلمه‌ای.",
            stepByStepGuide = listOf(
                "گام ۱: تقطیع چشمی بدون آواز" to "مصراع را تقطیع کن. اگر هجای ۳ بلند (–) بود ⟹ مفاعیلن (U---) | اگر هجای ۳ کوتاه (U) بود ⟹ مفاعلن (U-U-).",
                "گام ۲: معکوس‌سازی اضافه تشبیهی" to "در ترکیب «الفِ ب»، جای دو کلمه را عوض کن و «مانندِ» بگذار. معنی داد؟ (چراغ عقل = عقل مانند چراغ است) ⟹ ۱۰۰٪ اضافه تشبیهی!",
                "گام ۳: فرمول اضافه استعاری" to "کلمه اول عضو انسان/جاندار + کلمه دوم غیرانسان (دستِ روزگار) ⟹ ۱۰۰٪ استعاره مکنیه تشخیصی!"
            ),
            goldenRuleText = "هجای سوم بلند = مفاعیلن | هجای سوم کوتاه = مفاعلن | برعکس کن + مانند = اضافه تشبیهی!"
        ),

        // ۴. زبان انگلیسی نهایی و کنکور
        FiveSubjectMethodInfo(
            id = "method_english",
            subjectNameFa = "زبان انگلیسی نهایی و کنکور",
            shortTabTitle = "زبان",
            methodTitle = "⚡ متد مجهول‌یابی، ضمایر موصولی و تگ‌های سوالی در ۱۰ ثانیه",
            iconVector = Icons.Default.Language,
            primaryColor = Color(0xFF7C3AED),
            quickSummary = "حل تست‌های گرامر، کلوزتست و ریدینگ بدون نیاز به دانستن لغات سخت از روی نشانه‌های ساختاری جمله.",
            stepByStepGuide = listOf(
                "گام ۱: فرمول جملات مجهول" to "دیدن by + اسم یا فاعل غیرانسان در اول جمله ⟹ ساختار مجهول [ to be (am/is/are/was/were/been) + Verb3 (p.p) ].",
                "گام ۲: ضمایر موصولی" to "انسان + who + فعل | انسان + whom + ضمیر/فاعل | غیرانسان + which | اسم + whose + اسم (مالکیت).",
                "گام ۳: تگ‌های سوالی مثبت" to "دیدن کلمات منفی پنهان (never, hardly, seldom, little, few) در جمله ⟹ تگِ آخر جمله ۱۰۰٪ باید مثبت باشد!"
            ),
            goldenRuleText = "no to be + p.p = no passive! دیدن never / hardly / seldom = تگ آخر حتماً مثبت است."
        ),

        // ۵. مسائل ریاضی اقتصاد
        FiveSubjectMethodInfo(
            id = "method_economics_math",
            subjectNameFa = "مسائل ریاضی اقتصاد",
            shortTabTitle = "مسائل",
            methodTitle = "⚡ متد ۲ خطی سود حسابداری، سود اقتصادی، تورم و دهک‌ها",
            iconVector = Icons.Default.Calculate,
            primaryColor = Color(0xFFDC2626),
            quickSummary = "محاسبه سریع مسائل درآمد، هزینه‌های نقدی، هزینه فرصت، جدول مالیات تصاعدی و دهک‌های درآمدی در ۲ سطر.",
            stepByStepGuide = listOf(
                "گام ۱: سود حسابداری" to "سود حسابداری = درآمد کل منهای هزینه‌های مستقیم نقدی (استهلاک، مواد اولیه، اجاره پرداختی، دستمزد).",
                "گام ۲: سود اقتصادی (ویژه)" to "سود اقتصادی = سود حسابداری منهای هزینه فرصت (حقوق شغل قبلی انصراف‌داده‌شده).",
                "گام ۳: دهک‌های درآمدی" to "نسبت دهک دهم به دهک اول (دهک ۱۰ ÷ دهک ۱) ⟹ هرچه کمتر و کوچک‌تر باشد = توزیع درآمد عادلانه‌تر است!"
            ),
            goldenRuleText = "سود حسابداری هزینه فرصت را کم نمی‌کند؛ سود اقتصادی حقوق شغل قبلی را هم از سود حسابداری کم می‌کند!"
        )
    )
}

/**
 * کامپوننت جذاب، تمیز و M3 برای نمایش متدهای فوق‌قوی ۵ درس اصلی کنکور
 */
@Composable
fun MasterFiveSubjectsMethodsCard(
    modifier: Modifier = Modifier
) {
    val responsive = LocalResponsiveDimens.current
    var selectedMethodIdx by remember { mutableIntStateOf(0) }
    var isExpanded by remember { mutableStateOf(true) }

    val activeMethod = FiveSubjectsMethodRepository.methods[selectedMethodIdx]

    CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl) {
        Card(
            modifier = modifier
                .fillMaxWidth()
                .padding(vertical = 4.dp)
                .testTag("master_five_subjects_methods_card"),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
            border = BorderStroke(1.2.dp, activeMethod.primaryColor.copy(alpha = 0.5f)),
            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(14.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                // Header
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(8.dp))
                        .clickable { isExpanded = !isExpanded },
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Surface(
                            color = activeMethod.primaryColor.copy(alpha = 0.15f),
                            shape = CircleShape,
                            modifier = Modifier.size(32.dp)
                        ) {
                            Box(contentAlignment = Alignment.Center) {
                                Icon(
                                    imageVector = Icons.Default.Star,
                                    contentDescription = null,
                                    tint = activeMethod.primaryColor,
                                    modifier = Modifier.size(18.dp)
                                )
                            }
                        }
                        Spacer(modifier = Modifier.width(8.dp))
                        Column {
                            Text(
                                text = "🏆 متدهای معجزه‌آسای ۵ درس سخت کنکور انسانی",
                                style = MaterialTheme.typography.titleSmall,
                                fontWeight = FontWeight.Black,
                                color = MaterialTheme.colorScheme.onSurface
                            )
                            Text(
                                text = "روش‌های تست‌زنی فوق‌سریع بدون نیاز به حفظیات سنگین",
                                style = MaterialTheme.typography.labelSmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant,
                                fontSize = 10.sp
                            )
                        }
                    }
                    Icon(
                        imageVector = if (isExpanded) Icons.Default.ExpandLess else Icons.Default.ExpandMore,
                        contentDescription = null,
                        tint = activeMethod.primaryColor,
                        modifier = Modifier.size(20.dp)
                    )
                }

                AnimatedVisibility(
                    visible = isExpanded,
                    enter = fadeIn() + expandVertically(),
                    exit = fadeOut() + shrinkVertically()
                ) {
                    Column(
                        verticalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        HorizontalDivider(thickness = 0.6.dp, color = MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.4f))

                        // Subject Tabs Selector
                        Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                            Text(
                                text = "درس مورد نظر را برای مشاهده متد اختصاصی انتخاب کنید:",
                                style = MaterialTheme.typography.labelSmall,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .horizontalScroll(rememberScrollState()),
                                horizontalArrangement = Arrangement.spacedBy(6.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                FiveSubjectsMethodRepository.methods.forEachIndexed { idx, m ->
                                    val isSel = selectedMethodIdx == idx
                                    Surface(
                                        modifier = Modifier
                                            .clip(RoundedCornerShape(10.dp))
                                            .clickable { selectedMethodIdx = idx }
                                            .testTag("method_tab_$idx"),
                                        color = if (isSel) m.primaryColor else MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.6f),
                                        shape = RoundedCornerShape(10.dp)
                                    ) {
                                        Box(
                                            contentAlignment = Alignment.Center,
                                            modifier = Modifier.padding(vertical = 8.dp, horizontal = 12.dp)
                                        ) {
                                            Text(
                                                text = m.subjectNameFa,
                                                style = MaterialTheme.typography.labelSmall,
                                                fontSize = 11.sp,
                                                fontWeight = if (isSel) FontWeight.Black else FontWeight.Bold,
                                                color = if (isSel) Color.White else MaterialTheme.colorScheme.onSurfaceVariant,
                                                maxLines = 1
                                            )
                                        }
                                    }
                                }
                            }
                        }

                        // Method Content Card
                        Surface(
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(12.dp),
                            color = activeMethod.primaryColor.copy(alpha = 0.07f),
                            border = BorderStroke(1.dp, activeMethod.primaryColor.copy(alpha = 0.35f))
                        ) {
                            Column(
                                modifier = Modifier.padding(12.dp),
                                verticalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Icon(
                                        imageVector = activeMethod.iconVector,
                                        contentDescription = null,
                                        tint = activeMethod.primaryColor,
                                        modifier = Modifier.size(20.dp)
                                    )
                                    Spacer(modifier = Modifier.width(6.dp))
                                    Text(
                                        text = activeMethod.methodTitle,
                                        style = MaterialTheme.typography.titleSmall,
                                        fontWeight = FontWeight.Black,
                                        color = activeMethod.primaryColor
                                    )
                                }

                                Text(
                                    text = activeMethod.quickSummary,
                                    style = MaterialTheme.typography.bodySmall,
                                    fontWeight = FontWeight.SemiBold,
                                    color = MaterialTheme.colorScheme.onSurface
                                )

                                HorizontalDivider(thickness = 0.5.dp, color = activeMethod.primaryColor.copy(alpha = 0.25f))

                                // Steps
                                activeMethod.stepByStepGuide.forEach { (stepTitle, stepDesc) ->
                                    Column(verticalArrangement = Arrangement.spacedBy(2.dp)) {
                                        Text(
                                            text = "📌 $stepTitle",
                                            style = MaterialTheme.typography.labelSmall,
                                            fontWeight = FontWeight.Black,
                                            color = activeMethod.primaryColor
                                        )
                                        Text(
                                            text = stepDesc,
                                            style = MaterialTheme.typography.bodySmall,
                                            fontSize = 11.sp,
                                            color = MaterialTheme.colorScheme.onSurfaceVariant
                                        )
                                    }
                                }

                                // Golden Rule Callout
                                Surface(
                                    color = activeMethod.primaryColor.copy(alpha = 0.15f),
                                    shape = RoundedCornerShape(8.dp),
                                    modifier = Modifier.fillMaxWidth()
                                ) {
                                    Text(
                                        text = "🎯 شاه‌کلید طلا: ${activeMethod.goldenRuleText}",
                                        style = MaterialTheme.typography.labelSmall,
                                        fontWeight = FontWeight.Black,
                                        color = activeMethod.primaryColor,
                                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 6.dp)
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
