package com.example.ui.components

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.Calculate
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Functions
import androidx.compose.material.icons.filled.Lightbulb
import androidx.compose.material.icons.filled.MenuBook
import androidx.compose.material.icons.filled.Psychology
import androidx.compose.material.icons.filled.QueryStats
import androidx.compose.material.icons.filled.ShowChart
import androidx.compose.material.icons.filled.Speed
import androidx.compose.material.icons.filled.Timeline
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

/**
 * کارگاه یادگیری عمیق و مفهومی ریاضی و آمار انسانی
 * حل مفهومی و الگوریتم‌های برق‌آسا بدون حفظ کورکورانه فرمول‌ها
 */
@Composable
fun MathDeepMasteryVisualGuide(
    accentColor: Color,
    modifier: Modifier = Modifier
) {
    var activeTab by remember { mutableIntStateOf(0) }
    val tabs = listOf(
        "⚡ ترازوی معادله در ۳ ثانیه",
        "📈 سهمی و نقاط عطف",
        "🚀 آسانسور پرش دنباله",
        "📊 واریانس بدون محاسبات",
        "🎲 کسر پله‌ای ترکیب"
    )

    CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl) {
        Surface(
            modifier = modifier
                .fillMaxWidth()
                .padding(vertical = 4.dp)
                .testTag("math_deep_mastery_card"),
            shape = RoundedCornerShape(16.dp),
            color = MaterialTheme.colorScheme.surface,
            border = BorderStroke(1.2.dp, accentColor.copy(alpha = 0.45f)),
            tonalElevation = 2.dp
        ) {
            Column(
                modifier = Modifier.padding(14.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                // Header
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Surface(
                        color = accentColor.copy(alpha = 0.15f),
                        shape = CircleShape,
                        modifier = Modifier.size(34.dp)
                    ) {
                        Box(contentAlignment = Alignment.Center) {
                            Icon(
                                imageVector = Icons.Default.Calculate,
                                contentDescription = null,
                                tint = accentColor,
                                modifier = Modifier.size(20.dp)
                            )
                        }
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    Column {
                        Text(
                            text = "🧠 کارگاه حل مفهومی و برق‌آسای ریاضی و آمار کنکور",
                            style = MaterialTheme.typography.titleSmall,
                            fontWeight = FontWeight.Black,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                        Text(
                            text = "تکنیک‌های دیداری حل تست بدون نیاز به دلتا و محاسبات طولانی",
                            style = MaterialTheme.typography.labelSmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }

                // Horizontal scrollable tabs
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .horizontalScroll(rememberScrollState()),
                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    tabs.forEachIndexed { index, label ->
                        val isSelected = activeTab == index
                        FilterChip(
                            selected = isSelected,
                            onClick = { activeTab = index },
                            label = {
                                Text(
                                    text = label,
                                    fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                                    fontSize = 11.sp
                                )
                            },
                            colors = FilterChipDefaults.filterChipColors(
                                selectedContainerColor = accentColor,
                                selectedLabelColor = Color.White
                            ),
                            shape = RoundedCornerShape(8.dp)
                        )
                    }
                }

                HorizontalDivider(color = accentColor.copy(alpha = 0.2f))

                // Content based on tab
                when (activeTab) {
                    0 -> {
                        // ترازوی معادله درجه ۲
                        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                            Surface(
                                shape = RoundedCornerShape(10.dp),
                                color = accentColor.copy(alpha = 0.08f),
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Column(modifier = Modifier.padding(10.dp), verticalArrangement = Arrangement.spacedBy(4.dp)) {
                                    Text(
                                        text = "🎯 فرمول کلی: ax² + bx + c = 0 (هرگز فوراً سراغ دلتا نرو!)",
                                        fontWeight = FontWeight.Bold,
                                        style = MaterialTheme.typography.labelMedium,
                                        color = accentColor
                                    )
                                    Text(
                                        text = "قانون ۱: اگر جمع ضرایب صفر شد (a + b + c = 0) ⟵ ریشه اول ۱۰۰٪ عدد ۱ است و ریشه دوم c/a است!\n" +
                                                "مثال: 4x² - 9x + 5 = 0 چون ۴ + (-۹) + ۵ = ۰ است ⟹ ریشه‌ها: x₁ = 1 و x₂ = 5/4 در ۲ ثانیه!",
                                        style = MaterialTheme.typography.bodySmall
                                    )
                                    Text(
                                        text = "قانون ۲: اگر ضریب وسط برابر جمع دو طرف شد (b = a + c) ⟵ ریشه اول ۱- و ریشه دوم -c/a است!\n" +
                                                "مثال: 3x² + 7x + 4 = 0 چون ۷ = ۳ + ۴ است ⟹ ریشه‌ها: x₁ = -1 و x₂ = -4/3 بدون هیچ فرمولی!",
                                        style = MaterialTheme.typography.bodySmall
                                    )
                                }
                            }
                        }
                    }
                    1 -> {
                        // سهمی و بیشینه کمینه
                        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                            Surface(
                                shape = RoundedCornerShape(10.dp),
                                color = Color(0xFF10B981).copy(alpha = 0.08f),
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Column(modifier = Modifier.padding(10.dp), verticalArrangement = Arrangement.spacedBy(4.dp)) {
                                    Text(
                                        text = "📊 سهمی y = ax² + bx + c و مفهوم هندسی رأس",
                                        fontWeight = FontWeight.Bold,
                                        style = MaterialTheme.typography.labelMedium,
                                        color = Color(0xFF047857)
                                    )
                                    Text(
                                        text = "• ضریب a مثبت (a > 0): دهانه رو به بالا ⟵ دارای کمترین مقدار (مینیمم).\n" +
                                                "• ضریب a منفی (a < 0): دهانه رو به پایین ⟵ دارای بیشترین مقدار (ماکزیمم).\n" +
                                                "• مکان بیشترین/کمترین سود یا هزینه همیشه روی خط تقارن است: x = -b / (2a).\n" +
                                                "• شگرد تستی: عدد x را داخل تابع قرار بده تا مقدار ماکزیمم/مینیمم به دست آید.",
                                        style = MaterialTheme.typography.bodySmall
                                    )
                                }
                            }
                        }
                    }
                    2 -> {
                        // دنباله‌ها
                        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                            Surface(
                                shape = RoundedCornerShape(10.dp),
                                color = Color(0xFFF59E0B).copy(alpha = 0.08f),
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Column(modifier = Modifier.padding(10.dp), verticalArrangement = Arrangement.spacedBy(4.dp)) {
                                    Text(
                                        text = "🚀 تکنیک آسانسور پرش در دنباله حسابی (بدون دستگاه دو مجهولی)",
                                        fontWeight = FontWeight.Bold,
                                        style = MaterialTheme.typography.labelMedium,
                                        color = Color(0xFFB45309)
                                    )
                                    Text(
                                        text = "فرمول جادویی قدرنسبت: d = (جمله mام - جمله nام) ÷ (m - n)\n" +
                                                "مثال: اگر جمله ۵ام برابر ۱۸ و جمله ۱۱ام برابر ۴۲ باشد:\n" +
                                                "d = (42 - 18) / (11 - 5) = 24 / 6 = 4!\n" +
                                                "حالا برای جمله ۲۰ام: کافی است ۹ تا d به جمله ۱۱ام اضافه کنی: a₂₀ = 42 + (9 × 4) = 78!",
                                        style = MaterialTheme.typography.bodySmall
                                    )
                                }
                            }
                        }
                    }
                    3 -> {
                        // واریانس و انحراف معیار
                        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                            Surface(
                                shape = RoundedCornerShape(10.dp),
                                color = Color(0xFF8B5CF6).copy(alpha = 0.08f),
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Column(modifier = Modifier.padding(10.dp), verticalArrangement = Arrangement.spacedBy(4.dp)) {
                                    Text(
                                        text = "⚡ قانون طلایی تغییر متغیر در پراکندگی (خط زدن جمع و تفریق)",
                                        fontWeight = FontWeight.Bold,
                                        style = MaterialTheme.typography.labelMedium,
                                        color = Color(0xFF6D28D9)
                                    )
                                    Text(
                                        text = "اگر داده‌ها در k ضرب و با b جمع شوند (y = kx + b):\n" +
                                                "۱. عدد b را کاملاً خط بزن! جمع و تفریق هیچ تاثیری در پراکندگی (واریانس، انحراف معیار، دامنه تغییرات) ندارد.\n" +
                                                "۲. انحراف معیار جدید = |k| × انحراف معیار قدیم.\n" +
                                                "۳. واریانس جدید = k² × واریانس قدیم.\n" +
                                                "مثال: انحراف معیار ۴ است و داده‌ها در ۳- ضرب و با ۵۰ جمع شدند: ۵۰ بی‌اثر است! انحراف معیار جدید = ۴ × |-۳| = ۱۲!",
                                        style = MaterialTheme.typography.bodySmall
                                    )
                                }
                            }
                        }
                    }
                    else -> {
                        // ترکیب و احتمال
                        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                            Surface(
                                shape = RoundedCornerShape(10.dp),
                                color = Color(0xFFEC4899).copy(alpha = 0.08f),
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Column(modifier = Modifier.padding(10.dp), verticalArrangement = Arrangement.spacedBy(4.dp)) {
                                    Text(
                                        text = "🎲 کسر پله‌ای ترکیب C(n, r) بدون فاکتوریل‌های سنگین",
                                        fontWeight = FontWeight.Bold,
                                        style = MaterialTheme.typography.labelMedium,
                                        color = Color(0xFFBE185D)
                                    )
                                    Text(
                                        text = "• برای انتخاب r شیء از n شیء:\n" +
                                                "صورت: از n به اندازه r عدد به عقب بشمار و ضرب کن.\n" +
                                                "مخرج: فاکتوریل r (از r تا ۱ ضرب کن).\n" +
                                                "مثال برای C(7, 3): صورت = ۷ × ۶ × ۵ | مخرج = ۳ × ۲ × ۱ = ۶ ⟹ ۶ با ۶ می‌رود = ۳۵!\n" +
                                                "• در احتمال شرطی P(A|B): کل جهان را فراموش کن! فقط تعداد حالت‌های شرط (B) را بشمار و در مخرج بگذار.",
                                        style = MaterialTheme.typography.bodySmall
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
 * کارگاه یادگیری عمیق علوم و فنون ادبی و عروض
 * عروض سماعی با ضرباهنگ + رمزگشایی ۲۴ آرایه ادبی و سبک‌شناسی
 */
@Composable
fun FonoonAndAroozVisualGuide(
    accentColor: Color,
    modifier: Modifier = Modifier
) {
    var activeTab by remember { mutableIntStateOf(0) }
    val tabs = listOf(
        "🥁 عروض سماعی (۴ هجای اول)",
        "🎭 استعاره مصرحه vs مکنیه",
        "💡 ایهام و اسلوب معادله",
        "📜 سبک‌شناسی ادوار (خراسانی تا هندی)"
    )

    CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl) {
        Surface(
            modifier = modifier
                .fillMaxWidth()
                .padding(vertical = 4.dp)
                .testTag("fonoon_arooz_deep_mastery_card"),
            shape = RoundedCornerShape(16.dp),
            color = MaterialTheme.colorScheme.surface,
            border = BorderStroke(1.2.dp, accentColor.copy(alpha = 0.45f)),
            tonalElevation = 2.dp
        ) {
            Column(
                modifier = Modifier.padding(14.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                // Header
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Surface(
                        color = accentColor.copy(alpha = 0.15f),
                        shape = CircleShape,
                        modifier = Modifier.size(34.dp)
                    ) {
                        Box(contentAlignment = Alignment.Center) {
                            Icon(
                                imageVector = Icons.Default.MenuBook,
                                contentDescription = null,
                                tint = accentColor,
                                modifier = Modifier.size(20.dp)
                            )
                        }
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    Column {
                        Text(
                            text = "🎭 کارگاه عروض سماعی و تحلیل آرایه‌های کنکور",
                            style = MaterialTheme.typography.titleSmall,
                            fontWeight = FontWeight.Black,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                        Text(
                            text = "تشخیص فوری بحر شعر با گوش و ریتم ضرب + معکوس‌سازی آرایه‌ها",
                            style = MaterialTheme.typography.labelSmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }

                // Horizontal scrollable tabs
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .horizontalScroll(rememberScrollState()),
                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    tabs.forEachIndexed { index, label ->
                        val isSelected = activeTab == index
                        FilterChip(
                            selected = isSelected,
                            onClick = { activeTab = index },
                            label = {
                                Text(
                                    text = label,
                                    fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                                    fontSize = 11.sp
                                )
                            },
                            colors = FilterChipDefaults.filterChipColors(
                                selectedContainerColor = accentColor,
                                selectedLabelColor = Color.White
                            ),
                            shape = RoundedCornerShape(8.dp)
                        )
                    }
                }

                HorizontalDivider(color = accentColor.copy(alpha = 0.2f))

                when (activeTab) {
                    0 -> {
                        // عروض سماعی ۴ هجای اول
                        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                            Surface(
                                shape = RoundedCornerShape(10.dp),
                                color = accentColor.copy(alpha = 0.08f),
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Column(modifier = Modifier.padding(10.dp), verticalArrangement = Arrangement.spacedBy(4.dp)) {
                                    Text(
                                        text = "🥁 جدول طلایی رمز ۴ هجای اول بیت (بدون تقطیع کامل):",
                                        fontWeight = FontWeight.Bold,
                                        style = MaterialTheme.typography.labelMedium,
                                        color = accentColor
                                    )
                                    Text(
                                        text = "• [ U — — — ] ⟵ مفاعیلن (بحر هزج): ریتم «دِ تا تا تا» (مفاعیلن مفاعیلن فعولن)\n" +
                                                "• [ — U — — ] ⟵ فاعلاتن (بحر رمل): ریتم «تا دِ تا تا» (فاعلاتن فاعلاتن فاعلن)\n" +
                                                "• [ — — U — ] ⟵ مستفعلن (بحر رجز): ریتم «تا تا دِ تا» (مستفعلن مستفعلن مستفعلن)\n" +
                                                "• [ U — — U ] ⟵ فعولن فعولن (بحر متقارب / شاهنامه): ریتم ضربدری حماسی\n" +
                                                "• [ — U U — ] ⟵ مفتعلن (وزن دوری / متناوب): مکث وسط و تقارن دو لنگه بیت",
                                        style = MaterialTheme.typography.bodySmall
                                    )
                                }
                            }
                        }
                    }
                    1 -> {
                        // استعاره
                        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                            Surface(
                                shape = RoundedCornerShape(10.dp),
                                color = Color(0xFF8B5CF6).copy(alpha = 0.08f),
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Column(modifier = Modifier.padding(10.dp), verticalArrangement = Arrangement.spacedBy(4.dp)) {
                                    Text(
                                        text = "🎭 تکنیک معکوس‌سازی برای تشخیص استعاره و تشبیه:",
                                        fontWeight = FontWeight.Bold,
                                        style = MaterialTheme.typography.labelMedium,
                                        color = Color(0xFF6D28D9)
                                    )
                                    Text(
                                        text = "۱. اضافه تشبیهی: «الفِ ب» را برعکس کن و «مانند» بگذار.\n" +
                                                "مثال: «دریای غم» ⟵ غم مانند دریاست (معنی داد ⟹ ۱۰۰٪ تشبیه بلیغ!)\n" +
                                                "۲. استعاره مکنیه (تشخیص): «عضو انسان + غیرانسان».\n" +
                                                "مثال: «دست روزگار»، «گوش هوش»، «پاییز نفس کشید» ⟵ چون روزگار دست ندارد، تشخیص و استعاره مکنیه است!\n" +
                                                "۳. استعاره مصرحه: خودِ کلمه استعاری را به جای معشوق/پدیده نشانده‌اند (نرگس = چشم | لعل = لب | کمان = ابرو).",
                                        style = MaterialTheme.typography.bodySmall
                                    )
                                }
                            }
                        }
                    }
                    2 -> {
                        // ایهام و اسلوب معادله
                        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                            Surface(
                                shape = RoundedCornerShape(10.dp),
                                color = Color(0xFF059669).copy(alpha = 0.08f),
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Column(modifier = Modifier.padding(10.dp), verticalArrangement = Arrangement.spacedBy(4.dp)) {
                                    Text(
                                        text = "💡 اسلوب معادله و ایهام در تست‌های کنکور:",
                                        fontWeight = FontWeight.Bold,
                                        style = MaterialTheme.typography.labelMedium,
                                        color = Color(0xFF047857)
                                    )
                                    Text(
                                        text = "• اسلوب معادله: مصراع اول یک ادعای عقلی و مصراع دوم یک مثال طبیعی و ملموس است.\n" +
                                                "تکنیک تست: بین دو مصراع علامت «همان‌طور که» یا «مساوی (=)» بگذار؛ اگر استقلال معنایی کامل داشتند ⟹ اسلوب معادله است!\n" +
                                                "• ایهام: کلمه ۲ معنی دارد (نزدیک و دور) و منظور شاعر معنی دور است (مانند: مدام، بو، مهر، لاله، راست).",
                                        style = MaterialTheme.typography.bodySmall
                                    )
                                }
                            }
                        }
                    }
                    else -> {
                        // سبک‌شناسی
                        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                            Surface(
                                shape = RoundedCornerShape(10.dp),
                                color = Color(0xFFD97706).copy(alpha = 0.08f),
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Column(modifier = Modifier.padding(10.dp), verticalArrangement = Arrangement.spacedBy(4.dp)) {
                                    Text(
                                        text = "📜 خط زمانی سبک‌شناسی شعر فارسی بدون حفظ طوطی‌وار:",
                                        fontWeight = FontWeight.Bold,
                                        style = MaterialTheme.typography.labelMedium,
                                        color = Color(0xFFB45309)
                                    )
                                    Text(
                                        text = "۱. سبک خراسانی (قرن ۴ و ۵): قصیده، حماسی، واقع‌گرا، کلمات کهن (ایدون، اندر، همی)، عشق زمینی ملموس.\n" +
                                                "۲. سبک عراقی (قرن ۷ تا ۹): غزل عاشقانه-عارفانه (سعدی، حافظ، مولوی)، درون‌گرا، معشوق آسمانی، ایهام.\n" +
                                                "۳. سبک هندی (قرن ۱۰ و ۱۱): تک‌بیت، تمثیل و اسلوب معادله، اغراق شدید، حس‌آمیزی و پارادوکس (صائب، بیدل).\n" +
                                                "۴. دوره بازگشت (قرن ۱۲ و ۱۳): تقلید کورکورانه از اساتید قدیم خراسانی و عراقی.",
                                        style = MaterialTheme.typography.bodySmall
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
 * کارگاه یادگیری عمیق اقتصاد کنکور
 * تحلیل مسائل ریاضی، سود حسابداری/اقتصادی، مالیات تصاعدی، کشش و دهک‌ها
 */
@Composable
fun EconomicsMathAndConceptsVisualGuide(
    accentColor: Color,
    modifier: Modifier = Modifier
) {
    var activeTab by remember { mutableIntStateOf(0) }
    val tabs = listOf(
        "💰 سود حسابداری vs اقتصادی",
        "📊 پلکان مالیات تصاعدی",
        "📈 کشش قیمتی تقاضا و درآمد",
        "⚖️ دهک‌ها و توزیع درآمد"
    )

    CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl) {
        Surface(
            modifier = modifier
                .fillMaxWidth()
                .padding(vertical = 4.dp)
                .testTag("economics_deep_mastery_card"),
            shape = RoundedCornerShape(16.dp),
            color = MaterialTheme.colorScheme.surface,
            border = BorderStroke(1.2.dp, accentColor.copy(alpha = 0.45f)),
            tonalElevation = 2.dp
        ) {
            Column(
                modifier = Modifier.padding(14.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                // Header
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Surface(
                        color = accentColor.copy(alpha = 0.15f),
                        shape = CircleShape,
                        modifier = Modifier.size(34.dp)
                    ) {
                        Box(contentAlignment = Alignment.Center) {
                            Icon(
                                imageVector = Icons.Default.QueryStats,
                                contentDescription = null,
                                tint = accentColor,
                                modifier = Modifier.size(20.dp)
                            )
                        }
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    Column {
                        Text(
                            text = "💰 کارگاه حل مسائل ریاضی و مفاهیم اقتصاد کنکور",
                            style = MaterialTheme.typography.titleSmall,
                            fontWeight = FontWeight.Black,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                        Text(
                            text = "تحلیل گام‌به‌گام سود، مالیات تصاعدی و کشش تقاضا در ۲ خط",
                            style = MaterialTheme.typography.labelSmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .horizontalScroll(rememberScrollState()),
                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    tabs.forEachIndexed { index, label ->
                        val isSelected = activeTab == index
                        FilterChip(
                            selected = isSelected,
                            onClick = { activeTab = index },
                            label = {
                                Text(
                                    text = label,
                                    fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                                    fontSize = 11.sp
                                )
                            },
                            colors = FilterChipDefaults.filterChipColors(
                                selectedContainerColor = accentColor,
                                selectedLabelColor = Color.White
                            ),
                            shape = RoundedCornerShape(8.dp)
                        )
                    }
                }

                HorizontalDivider(color = accentColor.copy(alpha = 0.2f))

                when (activeTab) {
                    0 -> {
                        // سود حسابداری vs اقتصادی
                        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                            Surface(
                                shape = RoundedCornerShape(10.dp),
                                color = accentColor.copy(alpha = 0.08f),
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Column(modifier = Modifier.padding(10.dp), verticalArrangement = Arrangement.spacedBy(4.dp)) {
                                    Text(
                                        text = "💰 فرمول طلایی سود در تست‌های کنکور:",
                                        fontWeight = FontWeight.Bold,
                                        style = MaterialTheme.typography.labelMedium,
                                        color = accentColor
                                    )
                                    Text(
                                        text = "۱. درآمد کل = تعداد فروش (Q) × قیمت واحد (P).\n" +
                                                "۲. سود حسابداری = درآمد کل - هزینه‌های آشکار (اجاره پرداختی + مواد اولیه + دستمزد کارگران + استهلاک).\n" +
                                                "۳. سود اقتصادی = سود حسابداری - هزینه فرصت (حقوق شغل قبلی که فرد رها کرده یا سود بانکی پول شخصی).\n" +
                                                "⚡ نکته کلیدی: سود اقتصادی همیشه کمتر از سود حسابداری است (یا حداکثر با آن برابر است).",
                                        style = MaterialTheme.typography.bodySmall
                                    )
                                }
                            }
                        }
                    }
                    1 -> {
                        // مالیات تصاعدی
                        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                            Surface(
                                shape = RoundedCornerShape(10.dp),
                                color = Color(0xFF10B981).copy(alpha = 0.08f),
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Column(modifier = Modifier.padding(10.dp), verticalArrangement = Arrangement.spacedBy(4.dp)) {
                                    Text(
                                        text = "📊 روش پله‌به‌پله مالیات تصاعدی بدون اشتباه محاسباتی:",
                                        fontWeight = FontWeight.Bold,
                                        style = MaterialTheme.typography.labelMedium,
                                        color = Color(0xFF047857)
                                    )
                                    Text(
                                        text = "در مالیات تصاعدی هرگز کل درآمد را در بالاترین درصد ضرب نکنید!\n" +
                                                "روش حل: درآمد را به طبقات جدول تقسیم کن:\n" +
                                                "• پله اول (مثلاً تا ۵۰ میلیون): نرخ ۱۰٪ ⟹ ۵ میلیون مالیات.\n" +
                                                "• پله دوم (از ۵۰ تا ۱۰۰ میلیون): نرخ ۱۵٪ ⟹ ۷.۵ میلیون مالیات.\n" +
                                                "• پله سوم (مازاد بر ۱۰۰ میلیون): نرخ ۲۰٪ روی باقی‌مانده.\n" +
                                                "جمع کل مالیات‌ها = مالیات نهایی مودی.",
                                        style = MaterialTheme.typography.bodySmall
                                    )
                                }
                            }
                        }
                    }
                    2 -> {
                        // کشش تقاضا
                        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                            Surface(
                                shape = RoundedCornerShape(10.dp),
                                color = Color(0xFF8B5CF6).copy(alpha = 0.08f),
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Column(modifier = Modifier.padding(10.dp), verticalArrangement = Arrangement.spacedBy(4.dp)) {
                                    Text(
                                        text = "📈 کشش قیمتی تقاضا (Ed) و اثر تغییر قیمت بر درآمد کل:",
                                        fontWeight = FontWeight.Bold,
                                        style = MaterialTheme.typography.labelMedium,
                                        color = Color(0xFF6D28D9)
                                    )
                                    Text(
                                        text = "فرمول کشش: Ed = | درصد تغییر مقدار تقاضا ÷ درصد تغییر قیمت |\n" +
                                                "• کالای باکشش (Ed > 1): حساس به قیمت (کالای لوکس) ⟵ کاهش قیمت باعث افزایش شدید فروش و افزایش درآمد کل می‌شود.\n" +
                                                "• کالای بی‌کشش (Ed < 1): غیرحساس به قیمت (دارو و نان) ⟵ افزایش قیمت باعث افزایش درآمد کل تولیدکننده می‌شود.\n" +
                                                "• کشش واحد (Ed = 1): تغییر قیمت تاثیری در درآمد کل ندارد.",
                                        style = MaterialTheme.typography.bodySmall
                                    )
                                }
                            }
                        }
                    }
                    else -> {
                        // دهک‌ها
                        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                            Surface(
                                shape = RoundedCornerShape(10.dp),
                                color = Color(0xFFF59E0B).copy(alpha = 0.08f),
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Column(modifier = Modifier.padding(10.dp), verticalArrangement = Arrangement.spacedBy(4.dp)) {
                                    Text(
                                        text = "⚖️ نسبت دهک دهم به اول و شاخص‌های عدالت اقتصادی:",
                                        fontWeight = FontWeight.Bold,
                                        style = MaterialTheme.typography.labelMedium,
                                        color = Color(0xFFB45309)
                                    )
                                    Text(
                                        text = "• دهک اول: ۱۰٪ فقیرترین جمعیت جامعه | دهک دهم: ۱۰٪ ثروتمندترین جمعیت.\n" +
                                                "• نسبت ۱۰ به ۱ = سهم دهک دهم ÷ سهم دهک اول.\n" +
                                                "قانون کنکوری: هر چه این نسبت کوچک‌تر باشد و به ۱ نزدیک‌تر شود، توزیع درآمد عادلانه‌تر است!\n" +
                                                "• ضریب جینی: عددی بین ۰ و ۱؛ عدد ۰ یعنی برابری کامل و عدد ۱ یعنی نابرابری مطلق.",
                                        style = MaterialTheme.typography.bodySmall
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
 * کارگاه یادگیری عمیق فلسفه و منطق کنکور
 * مربع تقابل قضایا، قیاس اقترانی، مغالطات ۱۳ گانه و سیر فلسفه اسلامی و غرب
 */
@Composable
fun PhilosophyAndLogicVisualGuide(
    accentColor: Color,
    modifier: Modifier = Modifier
) {
    var activeTab by remember { mutableIntStateOf(0) }
    val tabs = listOf(
        "🔲 مربع تقابل احکام قضایا",
        "⚖️ شرایط انتاج قیاس اقترانی",
        "🎭 مغالطات پرتکرار کنکور",
        "🏛️ سیر فلسفه اسلامی و دکارت"
    )

    CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl) {
        Surface(
            modifier = modifier
                .fillMaxWidth()
                .padding(vertical = 4.dp)
                .testTag("philosophy_logic_deep_mastery_card"),
            shape = RoundedCornerShape(16.dp),
            color = MaterialTheme.colorScheme.surface,
            border = BorderStroke(1.2.dp, accentColor.copy(alpha = 0.45f)),
            tonalElevation = 2.dp
        ) {
            Column(
                modifier = Modifier.padding(14.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                // Header
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Surface(
                        color = accentColor.copy(alpha = 0.15f),
                        shape = CircleShape,
                        modifier = Modifier.size(34.dp)
                    ) {
                        Box(contentAlignment = Alignment.Center) {
                            Icon(
                                imageVector = Icons.Default.Psychology,
                                contentDescription = null,
                                tint = accentColor,
                                modifier = Modifier.size(20.dp)
                            )
                        }
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    Column {
                        Text(
                            text = "🏛️ کارگاه استدلال مفهومی فلسفه و منطق کنکور",
                            style = MaterialTheme.typography.titleSmall,
                            fontWeight = FontWeight.Black,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                        Text(
                            text = "تحلیل دیداری مربع تقابل، اعتبارسنجی قیاس و دام مغالطات",
                            style = MaterialTheme.typography.labelSmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .horizontalScroll(rememberScrollState()),
                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    tabs.forEachIndexed { index, label ->
                        val isSelected = activeTab == index
                        FilterChip(
                            selected = isSelected,
                            onClick = { activeTab = index },
                            label = {
                                Text(
                                    text = label,
                                    fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                                    fontSize = 11.sp
                                )
                            },
                            colors = FilterChipDefaults.filterChipColors(
                                selectedContainerColor = accentColor,
                                selectedLabelColor = Color.White
                            ),
                            shape = RoundedCornerShape(8.dp)
                        )
                    }
                }

                HorizontalDivider(color = accentColor.copy(alpha = 0.2f))

                when (activeTab) {
                    0 -> {
                        // مربع تقابل
                        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                            Surface(
                                shape = RoundedCornerShape(10.dp),
                                color = accentColor.copy(alpha = 0.08f),
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Column(modifier = Modifier.padding(10.dp), verticalArrangement = Arrangement.spacedBy(4.dp)) {
                                    Text(
                                        text = "🔲 قوانین قطعی مربع تقابل احکام قضایا:",
                                        fontWeight = FontWeight.Bold,
                                        style = MaterialTheme.typography.labelMedium,
                                        color = accentColor
                                    )
                                    Text(
                                        text = "۱. تناقض (قطرها: موجبه کلیه با سالبه جزئیه / سالبه کلیه با موجبه جزئیه):\n" +
                                                "همیشه یکی درست و دیگری حتماً نادرست است (نه با هم جمع می‌شوند نه با هم رفع).\n" +
                                                "۲. تضاد (ضلع بالا: موجبه کلیه با سالبه کلیه):\n" +
                                                "هر دو با هم نمی‌توانند درست باشند (اما ممکن است هر دو نادرست باشند).\n" +
                                                "۳. دخول تحت تضاد (ضلع پایین: موجبه جزئیه با سالبه جزئیه):\n" +
                                                "هر دو با هم می‌توانند درست باشند اما هر دو با هم نادرست نمی‌شوند.\n" +
                                                "۴. تداخل (اضلاع عمودی چپ و راست):\n" +
                                                "صدق کلی ⟹ صدق جزئی | کذب جزئی ⟹ کذب کلی.",
                                        style = MaterialTheme.typography.bodySmall
                                    )
                                }
                            }
                        }
                    }
                    1 -> {
                        // قیاس اقترانی
                        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                            Surface(
                                shape = RoundedCornerShape(10.dp),
                                color = Color(0xFF059669).copy(alpha = 0.08f),
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Column(modifier = Modifier.padding(10.dp), verticalArrangement = Arrangement.spacedBy(4.dp)) {
                                    Text(
                                        text = "⚖️ شگرد اعتبارسنجی قیاس اقترانی حملی در ۳ ثانیه:",
                                        fontWeight = FontWeight.Bold,
                                        style = MaterialTheme.typography.labelMedium,
                                        color = Color(0xFF047857)
                                    )
                                    Text(
                                        text = "۱. تکرار حد وسط: کلمه مشترک دقیقاً با یک معنی تکرار شده باشد (بدون مغالطه اشتراک لفظ).\n" +
                                                "۲. علامت حد وسط: حداقل در یک مقدمه باید دارای علامت مثبت (+) یعنی کلی یا سالبه باشد.\n" +
                                                "۳. هر موضوع یا محمولی که در نتیجه علامت مثبت گرفت (+) حتماً باید در مقدمات هم مثبت باشد!\n" +
                                                "۴. از دو مقدمه سالبه (منفی) هرگز نتیجه به دست نمی‌آید.\n" +
                                                "۵. اگر یک مقدمه سالبه بود، نتیجه حتماً باید سالبه باشد.",
                                        style = MaterialTheme.typography.bodySmall
                                    )
                                }
                            }
                        }
                    }
                    2 -> {
                        // مغالطات
                        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                            Surface(
                                shape = RoundedCornerShape(10.dp),
                                color = Color(0xFFDC2626).copy(alpha = 0.08f),
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Column(modifier = Modifier.padding(10.dp), verticalArrangement = Arrangement.spacedBy(4.dp)) {
                                    Text(
                                        text = "🎭 مغالطات مهم و پرتکرار در سوالات کنکور:",
                                        fontWeight = FontWeight.Bold,
                                        style = MaterialTheme.typography.labelMedium,
                                        color = Color(0xFFB91C1C)
                                    )
                                    Text(
                                        text = "• مغالطه اشتراک لفظ: استفاده از کلمه‌ای با دو معنی متفاوت در دو مقدمه (مانند: شیر، باز، بار).\n" +
                                                "• مغالطه پهلوان‌پنبه: تغییر دادن و تحریف حرف طرف مقابل به شکلی ضعیف و مضحک و سپس حمله به آن.\n" +
                                                "• مغالطه مسموم کردن چاه: برچسب زدن به مخالفان قبل از شروع بحث (مثلاً: فقط افراد نادان با این حرف مخالفت می‌کنند!).\n" +
                                                "• مغالطه تعمیم شتاب‌زده: نتیجه‌گیری کلی برای کل جامعه بر اساس نمونه اندک و نامناسب.",
                                        style = MaterialTheme.typography.bodySmall
                                    )
                                }
                            }
                        }
                    }
                    else -> {
                        // فلسفه
                        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                            Surface(
                                shape = RoundedCornerShape(10.dp),
                                color = Color(0xFF8B5CF6).copy(alpha = 0.08f),
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Column(modifier = Modifier.padding(10.dp), verticalArrangement = Arrangement.spacedBy(4.dp)) {
                                    Text(
                                        text = "🏛️ شاه‌کلیدهای فلسفه اسلامی و غرب در کنکور:",
                                        fontWeight = FontWeight.Bold,
                                        style = MaterialTheme.typography.labelMedium,
                                        color = Color(0xFF6D28D9)
                                    )
                                    Text(
                                        text = "• ابن‌سینا: تمایز وجود و ماهیت، برهان صدیقین (استدلال از خود وجود بر واجب‌الوجود).\n" +
                                                "• سهروردی (حکمت اشراق): اولویت نور و شهود قلبی در کنار استدلال عقلی.\n" +
                                                "• ملاصدرا (حکمت متعالیه): اصالت وجود، وحدت تشکیکی وجود، حرکت جوهری (تحول در ذات ماده).\n" +
                                                "• دکارت (عقل‌گرایی غرب): شک دستوری، «می‌اندیشم پس هستم»، اثبات وجود خدا از ایده کمال مطلق.\n" +
                                                "• کانت (معرفت‌شناسی نقد عقل): فنومن (پدیدار) در برابر نومن (شیء فی‌نفسه)، زمان و مکان قالب‌های ذهن ما هستند.",
                                        style = MaterialTheme.typography.bodySmall
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
