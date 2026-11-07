package com.example.ui.components

import androidx.compose.animation.AnimatedVisibility
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
import androidx.compose.material.icons.filled.HistoryEdu
import androidx.compose.material.icons.filled.Lightbulb
import androidx.compose.material.icons.filled.Timeline
import androidx.compose.material.icons.filled.WarningAmber
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
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

data class DynastyChronologyItem(
    val dynastyName: String,
    val timeSpan: String,
    val capitalCity: String,
    val famousKings: String,
    val chronologicalPrerequisite: String,
    val memoryHook: String,
    val konkoorTrap: String
)

val HistoryDynastyRepository = listOf(
    DynastyChronologyItem(
        dynastyName = "۱. طاهریان و صفاریان (قرن ۳)",
        timeSpan = "۸۲۱ تا ۹۰۳ میلادی (نیمه اول قرن ۳ تا ۴)",
        capitalCity = "نیشابور (طاهریان) • زرنج سیستان (صفاریان)",
        famousKings = "طاهر بن حسین، یعقوب لیث صفاری",
        chronologicalPrerequisite = "اول باید خلافت عباسی و ضعف حکومت مرکزی بغداد را درک کرده باشید.",
        memoryHook = "«طاهـ» یعنی طاهریان در نیشابور (نزدیک خراسان) و «یعقوب مسگر» صفاری از سیستان مسگری را رها کرد و شمشیر کشید!",
        konkoorTrap = "طاهریان دست‌نشانده عباسیان بودند اما صفاریان استقلال‌طلب‌تر بودند و زبان فارسی را رسمی کردند."
    ),
    DynastyChronologyItem(
        dynastyName = "۲. سامانیان (قرن ۴)",
        timeSpan = "۸۷۴ تا ۹۹۹ میلادی",
        capitalCity = "بخارا",
        famousKings = "نصر بن احمد، اسماعیل سامانی",
        chronologicalPrerequisite = "پیش‌نیاز: تسلط بر زوال صفاریان و تسلط سامانیان بر ماوراءالنهر و خراسان.",
        memoryHook = "رمز عصر زرین ادب فارسی: رودکی، فردوسی (آغاز شاهنامه)، ابن‌سینا و بیرونی همگی در بستر امنیت سامانیان رشد کردند.",
        konkoorTrap = "سامانیان ترک‌نژاد نبودند بلکه اصالت ایرانی (دهقانان بلخ) داشتند ولی ارتش غلامان ترک داشتند."
    ),
    DynastyChronologyItem(
        dynastyName = "۳. غزنویان و سلجوقیان (قرن ۵ و ۶)",
        timeSpan = "۹۶۲ تا ۱۱۸۶ (غزنویان) • ۱۰۳۷ تا ۱۱۹۴ (سلجوقیان)",
        capitalCity = "غزنین و لاهور (غزنویان) • نیشابور، اصفهان، مرو (سلجوقیان)",
        famousKings = "محمود و مسعود غزنوی • آلپ‌ارسلان و ملکشاه سلجوقی با وزیرشان خواجه نظام‌الملک",
        chronologicalPrerequisite = "پیش‌نیاز: شناخت ورود ترکان به قدرت سیاسی ایران و تاسیس مدارس نظامیه.",
        memoryHook = "غزنویان = لشکرکشی به هند و غزوه | سلجوقیان = ترکان غز با وزیر دانشمند خواجه نظام‌الملک (سیاست‌نامه و نظامیه‌ها).",
        konkoorTrap = "فردوسی شاهنامه را به سلطان محمود تقدیم کرد ولی صله نداد؛ خواجه نظام‌الملک بنیان‌گذار نظامیه‌ها بود نه غزنویان."
    ),
    DynastyChronologyItem(
        dynastyName = "۴. خوارزمشاهیان و حمله مغول (قرن ۷)",
        timeSpan = "۱۰۷۷ تا ۱۲۳۱ میلادی",
        capitalCity = "گرگانج (اولتنگ) • سمرقند",
        famousKings = "علاءالدین محمد خوارزمشاه و جلال‌الدین خوارزمشاه",
        chronologicalPrerequisite = "پیش‌نیاز: تسلط بر ضعف سیاسی خوارزمشاهیان در برابر چنگیز خان.",
        memoryHook = "ماجرای قتل بازرگانان مغول در اترار توسط خوارزمشاه = جرقه حمله خانمان‌سوز چنگیز خان مغول به ایران.",
        konkoorTrap = "مقاومت دلیرانه جلال‌الدین خوارزمشاه بی‌نظیر بود اما اختلافات داخلی مانع پیروزی شد."
    ),
    DynastyChronologyItem(
        dynastyName = "۵. ایلخانیان و تیموریان (قرن ۸ و ۹)",
        timeSpan = "۱۲۵۶ تا ۱۳۳۵ (ایلخانیان) • ۱۳۷۰ تا ۱۵۰۷ (تیموریان)",
        capitalCity = "تبریز و مراغه (ایلخانیان) • هرات و سمرقند (تیموریان)",
        famousKings = "هولاکو، غازان خان و اولجایتو • تیمور گورکانی و شاهرخ تیموری",
        chronologicalPrerequisite = "پیش‌نیاز: شناخت ایلخانیان مغول و سپس رنسانس فرهنگی دوره تیموری در هرات.",
        memoryHook = "غازان خان مسلمان شد (با وزیری خواجه رشیدالدین فضل‌الله) | تیموریان = اوج هنر مینیاتور (بهزاد) و بناهای هرات و سمرقند.",
        konkoorTrap = "خواجه رشیدالدین وزیر غازان خان بود نه چنگیز خان؛ مینیاتور مکتب هرات در عصر تیموری شکوفا شد."
    ),
    DynastyChronologyItem(
        dynastyName = "۶. صفویان و افشاریان و زندیه (قرن ۱۰ تا ۱۲)",
        timeSpan = "۱۵۰۱ تا ۱۷۳۶ (صفویه) • ۱۷۳۶ تا ۱۷۹۶ (افشار و زند)",
        capitalCity = "تبریز، قزوین، اصفهان (صفویه) • مشهد (نادرشاه) • شیراز (کریم‌خان زند)",
        famousKings = "شاه اسماعیل اول، شاه عباس کبیر • نادرشاه افشار • کریم‌خان زند (وکیل‌الرعایا)",
        chronologicalPrerequisite = "پیش‌نیاز: تسلط بر مذهب رسمی تشیع در صفویه و فتوحات نادرشاه.",
        memoryHook = "صفویه = تشیع رسمی و معماری اصفهان | نادرشاه = شمشیر تیز و فتح هند | کریم‌خان زند = کلاه نمدی و عدم قبول لقب پادشاهی.",
        konkoorTrap = "کریم‌خان هرگز خود را شاه ننامید بلکه لقب «وکیل‌الرعایا» را برگزید."
    )
)

/**
 * ابزار حافظه تصویری، تقویم و سلسله‌های تاریخی برای رفع مشکل قاطی کردن اعداد و نام‌ها
 */
@Composable
fun HistoryDynastyMemoryVault(
    accentColor: Color,
    modifier: Modifier = Modifier
) {
    var selectedIndex by remember { mutableIntStateOf(0) }
    val item = HistoryDynastyRepository[selectedIndex]

    Card(
        modifier = modifier
            .fillMaxWidth()
            .testTag("history_dynasty_memory_vault"),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        border = BorderStroke(1.2.dp, accentColor.copy(alpha = 0.5f)),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier.padding(14.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Surface(
                    color = accentColor.copy(alpha = 0.15f),
                    shape = CircleShape,
                    modifier = Modifier.size(34.dp)
                ) {
                    Box(contentAlignment = Alignment.Center) {
                        Icon(
                            imageVector = Icons.Default.HistoryEdu,
                            contentDescription = null,
                            tint = accentColor,
                            modifier = Modifier.size(20.dp)
                        )
                    }
                }
                Spacer(modifier = Modifier.width(8.dp))
                Column {
                    Text(
                        text = "🏰 جعبه ابزار طلایی تاریخ و سلسله‌های کنکور (ضدقاطی کردن)",
                        style = MaterialTheme.typography.titleSmall,
                        fontWeight = FontWeight.Black,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    Text(
                        text = "رمزنگاری تقویم، پایتخت‌ها و ترتیب دقیق سلسله‌های ایران",
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }

            // افقی: انتخاب سلسله
            androidx.compose.foundation.lazy.LazyRow(
                horizontalArrangement = Arrangement.spacedBy(6.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                items(HistoryDynastyRepository.size) { idx ->
                    val dyn = HistoryDynastyRepository[idx]
                    val isSelected = selectedIndex == idx
                    Surface(
                        modifier = Modifier
                            .clip(RoundedCornerShape(8.dp))
                            .clickable { selectedIndex = idx },
                        color = if (isSelected) accentColor else MaterialTheme.colorScheme.surfaceVariant,
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        Text(
                            text = dyn.dynastyName,
                            modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp),
                            style = MaterialTheme.typography.labelSmall,
                            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                            color = if (isSelected) Color.White else MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
            }

            HorizontalDivider(color = accentColor.copy(alpha = 0.25f))

            // نمایش اطلاعات جامع سلسله انتخاب‌شده
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = item.dynastyName,
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Black,
                        color = accentColor
                    )
                    Surface(
                        color = accentColor.copy(alpha = 0.12f),
                        shape = RoundedCornerShape(6.dp)
                    ) {
                        Text(
                            text = item.timeSpan,
                            style = MaterialTheme.typography.labelSmall,
                            fontWeight = FontWeight.Bold,
                            color = accentColor,
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp)
                        )
                    }
                }

                Surface(
                    shape = RoundedCornerShape(10.dp),
                    color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(
                        modifier = Modifier.padding(10.dp),
                        verticalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        Text(
                            text = "🏛️ پایتخت و مراکز حکومت: ${item.capitalCity}",
                            style = MaterialTheme.typography.bodySmall,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = "👑 شاهان و چهره‌های برجسته: ${item.famousKings}",
                            style = MaterialTheme.typography.bodySmall
                        )
                    }
                }

                // پیش‌نیاز ترتیب تاریخی
                Surface(
                    shape = RoundedCornerShape(10.dp),
                    color = Color(0xFFF59E0B).copy(alpha = 0.1f),
                    border = BorderStroke(0.8.dp, Color(0xFFF59E0B).copy(alpha = 0.35f)),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Row(
                        modifier = Modifier.padding(10.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Default.Timeline,
                            contentDescription = null,
                            tint = Color(0xFFD97706),
                            modifier = Modifier.size(18.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Column {
                            Text(
                                text = "🔗 زنجیره و پیش‌نیاز ترتیب تاریخی:",
                                style = MaterialTheme.typography.labelSmall,
                                fontWeight = FontWeight.Bold,
                                color = Color(0xFFB45309)
                            )
                            Text(
                                text = item.chronologicalPrerequisite,
                                style = MaterialTheme.typography.bodySmall,
                                color = Color(0xFF78350F)
                            )
                        }
                    }
                }

                // قلاب حافظه تصویری
                Surface(
                    shape = RoundedCornerShape(10.dp),
                    color = accentColor.copy(alpha = 0.08f),
                    border = BorderStroke(0.8.dp, accentColor.copy(alpha = 0.3f)),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Row(
                        modifier = Modifier.padding(10.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Default.Lightbulb,
                            contentDescription = null,
                            tint = accentColor,
                            modifier = Modifier.size(18.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Column {
                            Text(
                                text = "💡 قلاب حافظه تصویری (برای جلوگیری از قاطی کردن اعداد و نام‌ها):",
                                style = MaterialTheme.typography.labelSmall,
                                fontWeight = FontWeight.Bold,
                                color = accentColor
                            )
                            Text(
                                text = item.memoryHook,
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.onSurface
                            )
                        }
                    }
                }

                // تله کنکوری
                Surface(
                    shape = RoundedCornerShape(10.dp),
                    color = Color(0xFFEF4444).copy(alpha = 0.08f),
                    border = BorderStroke(0.8.dp, Color(0xFFEF4444).copy(alpha = 0.3f)),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Row(
                        modifier = Modifier.padding(10.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Default.WarningAmber,
                            contentDescription = null,
                            tint = Color(0xFFDC2626),
                            modifier = Modifier.size(18.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Column {
                            Text(
                                text = "🔴 دام مرگبار طراح سوال کنکور در این سلسله:",
                                style = MaterialTheme.typography.labelSmall,
                                fontWeight = FontWeight.Bold,
                                color = Color(0xFFB91C1C)
                            )
                            Text(
                                text = item.konkoorTrap,
                                style = MaterialTheme.typography.bodySmall,
                                color = Color(0xFF7F1D1D)
                            )
                        }
                    }
                }
            }
        }
    }
}
