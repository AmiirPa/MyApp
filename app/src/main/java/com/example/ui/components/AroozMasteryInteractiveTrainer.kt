package com.example.ui.components

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
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.MusicNote
import androidx.compose.material.icons.filled.Psychology
import androidx.compose.material.icons.filled.CheckCircle
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

data class AroozConceptItem(
    val title: String,
    val conceptSimple: String,
    val syllableSymbolRule: String,
    val fiveSecondTrick: String,
    val sampleCouplet: String,
    val analysisSteps: List<String>
)

val AroozRepository = listOf(
    AroozConceptItem(
        title = "۱. قانون هجاهای کوتاه (U) و بلند (—) و کشیده (— U)",
        conceptSimple = "عروض یعنی وزن شعر با گوش دادن به ضرباهنگ کلمات؛ نه حفظ کردن طوطی‌وار فرمول‌ها!",
        syllableSymbolRule = "• حرف صامت + مصشک کوتاه (ـَـِـُ) = هجای کوتاه [ U ] (مثل: بَ، کِ)\n" +
                "• حرف صامت + مصوت بلند (آ، او، ای) یا دو حرف = هجای بلند [ — ] (مثل: با، دور، مرد)\n" +
                "• حرف صامت + مصوت بلند + صامت یا دو صامت = هجای کشیده [ — U ] (مثل: دست، مردْ)",
        fiveSecondTrick = "در کنکور هرگز کل مصراع را تقطیع نکن! فقط **۴ هجای اول** را علامت‌گذاری کن تا بحر شعر پیدا شود.",
        sampleCouplet = "ای ساربان آهسته ران کارام جانم می‌رود",
        analysisSteps = listOf(
            "گام ۱: واژه‌ها را تلفظ طبیعی کن (نه حالت کتابی).",
            "گام ۲: اِی (—) + سا (—) + رِ (U) + بان (—) ⟹ الگوی ۴ هجای اول [ — — U — ] است.",
            "گام ۳: مطابقت با بحرها: [ — — U — ] یعنی رکن «مستفعلن» در بحر رجز!"
        )
    ),
    AroozConceptItem(
        title = "۲. تقطیع سماعی (شنیدن ریتم ضرب‌آهنگ)",
        conceptSimple = "اگر دستت در تقطیع می‌لرزد، شعر را با ریتم موسیقی و ضرب پا بخوان!",
        syllableSymbolRule = "دستور کار عروض سماعی:\n" +
                "• وزن‌های رمل: ریتم رقصان و نرم (فاعلاتن فاعلاتن فاعلن)\n" +
                "• وزن‌های هزج: ریتم آرام و حزن‌انگیز (مفاعیلن مفاعیلن فعولن)\n" +
                "• وزن‌های رجز: ریتم رزم و حماسه (مستفعلن مستفعلن مستفعلن)",
        fiveSecondTrick = "با دست روی میز ضرب بگیر؛ شعر را با صدای بلند بخوان. آهنگ شعر هر چه بود نام بحر مشخص است.",
        sampleCouplet = "بشنو از نی چون حکایت می‌کند / از جدایی‌ها شکایت می‌کند",
        analysisSteps = listOf(
            "گام ۱: بشنو (— U —) از نی (— —) چون (— U) حکا (— —) یت (U —) می‌ (—) کند (U —).",
            "گام ۲: ریتم آهنگ بحر هزج مسدس محذوف است.",
            "گام ۳: در تست عروض، گزینه‌ها را با ضرباهنگ ۴ هجای اول تست کن."
        )
    ),
    AroozConceptItem(
        title = "۳. قافیه و ردک (شناخت حروف الحاق و روی)",
        conceptSimple = "قافیه یعنی کلمات هم‌پایان در آخر مصراع‌ها که حرف آخر آن‌ها (حرف روی) مشترک است.",
        syllableSymbolRule = "• حروف قافیه باید در صوت و املای آخر کاملاً هماهنگ باشند.\n" +
                "• ردیف کلمه یا عبارتی است که عیناً بعد از قافیه در همه بیت‌ها تکرار می‌شود (مثل: «می‌رود»، «است»).",
        fiveSecondTrick = "اول ردیف را پیدا کن و از آخر جمله حذف کن؛ کلمه قبل از ردیف حتماً «قافیه» است!",
        sampleCouplet = "در وفای عشق تو تار مویم / هر سحر از غصه می‌گویم",
        analysisSteps = listOf(
            "گام ۱: کلمات «مویم» و «می‌گویم» را بررسی کن.",
            "گام ۲: پسوند «یم» ردیف مشترک است.",
            "گام ۳: کلمه «مو» و «می‌گو» حروف قافیه با «و» و «ر» هستند."
        )
    )
);

/**
 * کامپوننت آموزشی ویژه ادبیات تخصصی برای رفع مشکل فهم عروض و قافیه
 */
@Composable
fun AroozMasteryInteractiveTrainer(
    accentColor: Color,
    modifier: Modifier = Modifier
) {
    var selectedTab by remember { mutableIntStateOf(0) }
    val item = AroozRepository[selectedTab]

    Card(
        modifier = modifier
            .fillMaxWidth()
            .testTag("arooz_mastery_interactive_trainer"),
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
                            imageVector = Icons.Default.MusicNote,
                            contentDescription = null,
                            tint = accentColor,
                            modifier = Modifier.size(20.dp)
                        )
                    }
                }
                Spacer(modifier = Modifier.width(8.dp))
                Column {
                    Text(
                        text = "🎵 کارگاه صفر تا صد عروض و قافیه ادبیات تخصصی (رفع گیجی عروض)",
                        style = MaterialTheme.typography.titleSmall,
                        fontWeight = FontWeight.Black,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    Text(
                        text = "آموزش بصری تقطیع هجایی و کشف بحر شعر بدون فرمول‌های گیج‌کننده",
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }

            // تب‌های انتخاب مبحث عروض
            androidx.compose.foundation.lazy.LazyRow(
                horizontalArrangement = Arrangement.spacedBy(6.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                items(AroozRepository.size) { idx ->
                    val aItem = AroozRepository[idx]
                    val isSelected = selectedTab == idx
                    Surface(
                        modifier = Modifier
                            .clip(RoundedCornerShape(8.dp))
                            .clickable { selectedTab = idx },
                        color = if (isSelected) accentColor else MaterialTheme.colorScheme.surfaceVariant,
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        Text(
                            text = aItem.title,
                            modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp),
                            style = MaterialTheme.typography.labelSmall,
                            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                            color = if (isSelected) Color.White else MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
            }

            HorizontalDivider(color = accentColor.copy(alpha = 0.25f))

            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                Text(
                    text = item.conceptSimple,
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.Bold,
                    color = accentColor
                )

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
                            text = "📏 قاعده علائم هجا:",
                            style = MaterialTheme.typography.labelSmall,
                            fontWeight = FontWeight.Bold,
                            color = accentColor
                        )
                        Text(
                            text = item.syllableSymbolRule,
                            style = MaterialTheme.typography.bodySmall
                        )
                    }
                }

                Surface(
                    shape = RoundedCornerShape(10.dp),
                    color = Color(0xFFF59E0B).copy(alpha = 0.1f),
                    border = BorderStroke(0.8.dp, Color(0xFFF59E0B).copy(alpha = 0.35f)),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(
                        modifier = Modifier.padding(10.dp),
                        verticalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        Text(
                            text = "⚡ شگرد ۵ ثانیه‌ای حل تست عروض کنکور:",
                            style = MaterialTheme.typography.labelSmall,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFFB45309)
                        )
                        Text(
                            text = item.fiveSecondTrick,
                            style = MaterialTheme.typography.bodySmall,
                            color = Color(0xFF78350F)
                        )
                    }
                }

                Surface(
                    shape = RoundedCornerShape(10.dp),
                    color = accentColor.copy(alpha = 0.08f),
                    border = BorderStroke(0.8.dp, accentColor.copy(alpha = 0.3f)),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(
                        modifier = Modifier.padding(10.dp),
                        verticalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        Text(
                            text = "📝 مثال عینی و تحلیل گام‌به‌گام:",
                            style = MaterialTheme.typography.labelSmall,
                            fontWeight = FontWeight.Bold,
                            color = accentColor
                        )
                        Text(
                            text = "« ${item.sampleCouplet} »",
                            style = MaterialTheme.typography.bodySmall,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                        item.analysisSteps.forEach { step ->
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(
                                    imageVector = Icons.Default.CheckCircle,
                                    contentDescription = null,
                                    tint = accentColor,
                                    modifier = Modifier.size(14.dp)
                                )
                                Spacer(modifier = Modifier.width(6.dp))
                                Text(
                                    text = step,
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
