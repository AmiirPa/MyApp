package com.example.ui.screens

import androidx.compose.foundation.background
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
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Calculate
import androidx.compose.material.icons.filled.GraphicEq
import androidx.compose.material.icons.filled.MusicNote
import androidx.compose.material.icons.filled.School
import androidx.compose.material.icons.filled.Translate
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FilterChip
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Slider
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Brush
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.data.curriculum.BismillahAndMnemonicsCurriculum
import com.example.data.curriculum.SubjectCatalog
import com.example.ui.components.ArabicZeroToMasterVisualGuide
import com.example.ui.components.BidiFormatter
import com.example.ui.components.EnglishZeroToMasterVisualGuide
import com.example.ui.components.SmartDiagramAndFormulaBlock
import com.example.ui.components.TopRankersAndSocialTricksHub
import com.example.ui.components.VisualMemoryTreeAndTimelineCard
import com.example.ui.components.FifteenChartFamiliesGalleryCard
import com.example.ui.components.ComprehensiveLexiconAndPoetryHubCard
import com.example.ui.theme.SubjectArabicColor
import com.example.ui.theme.SubjectAroozColor
import com.example.ui.theme.SubjectEnglishColor
import com.example.ui.theme.SubjectFarhangianColor
import com.example.ui.theme.SubjectMathColor
import com.example.ui.theme.SubjectPhilosophyColor
import kotlin.math.sqrt

data class SampleProsodyVerse(
    val title: String,
    val writtenText: String,
    val phoneticSyllables: List<Pair<String, String>>, // Pair(syllableText, "U" or "–" or "–U")
    val meterName: String,
    val samaeiRhythm: String,
    val ghafiehRule: String
)

private val presetVerses = listOf(
    SampleProsodyVerse(
        title = "بیت ۱: بنی آدم اعضای یکدیگرند (سعدی)",
        writtenText = "بنی آدم اعضای یکدیگرند",
        phoneticSyllables = listOf(
            "بَ" to "U", "نی" to "–", "آ" to "–", "دَ" to "U",
            "مَع (وصل م به ا)" to "–", "ضا" to "–", "یِ" to "U",
            "یِک" to "–", "دی" to "–", "گَ" to "U", "رَند" to "–"
        ),
        meterName = "فَعولُن فَعولُن فَعولُن فَعَل (U – – | U – – | U – – | U –) [در اصل: بَنی آ / دَمَع ضا / یِ یِک دی / گَرَند]",
        samaeiRhythm = "تَ تَن تَن | تَ تَن تَن | تَ تَن تَن | تَ تَن (بحر متقارب مثمن محذوف)",
        ghafiehRule = "ردیف: «ـَند» | قافیه: «یکدیگر / گوهر» -> ختم به «ـَ ر» (مصوت کوتاه + صامت) = قاعده ۲ قافیه"
    ),
    SampleProsodyVerse(
        title = "بیت ۲: که عشق آسان نمود اول ولی افتاد مشکل‌ها (حافظ)",
        writtenText = "که عشق آسان نمود اول ولی افتاد مشکل‌ها",
        phoneticSyllables = listOf(
            "کِ" to "U", "عِش" to "–", "قا (وصل ق به آ)" to "–", "سان" to "–",
            "نُ" to "U", "مو" to "–", "دَو (وصل د به ا)" to "–", "وَل" to "–",
            "وَ" to "U", "لی" to "–", "اُف" to "–", "تا" to "–",
            "دِ" to "U", "مُش" to "–", "کِل" to "–", "ها" to "–"
        ),
        meterName = "مَفاعیلُن مَفاعیلُن مَفاعیلُن مَفاعیلُن (U – – – چهار بار)",
        samaeiRhythm = "تَ تَن تَن تَن | تَ تَن تَن تَن (بحر هزج مثمن سالم - وزن همسان)",
        ghafiehRule = "قافیه: «ناولها / مشکل‌ها» -> ختم به مصوت بلند «آ» = قاعده ۱ قافیه"
    ),
    SampleProsodyVerse(
        title = "بیت ۳: ای ساربان آهسته ران کارام جانم می‌رود (سعدی)",
        writtenText = "ای ساربان آهسته ران کارام جانم می‌رود",
        phoneticSyllables = listOf(
            "ای" to "–", "سا" to "–", "رِ" to "U", "بان" to "–",
            "آ" to "–", "هِس" to "–", "تِ" to "U", "ران" to "–",
            "کا" to "–", "را" to "–", "مِ" to "U", "جا" to "–",
            "نَم" to "–", "می" to "–", "رَ" to "U", "وَد" to "–"
        ),
        meterName = "مُستَفعِلُن مُستَفعِلُن // مُستَفعِلُن مُستَفعِلُن (– – U – چهار بار - دارای مکث دوری وسط مصراع)",
        samaeiRhythm = "تَن تَن تَ تَن | تَن تَن تَ تَن (بحر رجز مثمن سالم)",
        ghafiehRule = "ردیف: «می‌رود» | قافیه: «جانم / ساربانم» -> «آن» (مصوت بلند آ + صامت ن) = قاعده ۲ قافیه"
    ),
    SampleProsodyVerse(
        title = "بیت ۴: یاد باد آنکه نهانت نظری با ما بود (حافظ)",
        writtenText = "یاد باد آنکه نهانت نظری با ما بود",
        phoneticSyllables = listOf(
            "یا" to "–", "دِ" to "U", "با" to "–", "دان" to "–",
            "کِ" to "U", "نَ" to "U", "ها" to "–", "نَت" to "–",
            "نَ" to "U", "ظَ" to "U", "ری" to "–", "با" to "–",
            "ما" to "–", "بود" to "–"
        ),
        meterName = "فاعِلاتُن فَعِلاتُن فَعِلاتُن فَعَلُن (– U – – | U U – – | U U – – | – –)",
        samaeiRhythm = "تَن تَ تَن تَن | تَ تَ تَن تَن (بحر رمل مثمن مخبون محذوف)",
        ghafiehRule = "ردیف: «بود» | قافیه: «ما / سیما» -> ختم به مصوت بلند «آ» = قاعده ۱ قافیه"
    )
)

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun InteractiveLabsScreen() {
    var selectedLabIndex by remember { mutableIntStateOf(-1) }
    var topSectionMode by remember { mutableIntStateOf(0) } // 0 = 8 Labs, 1 = Trees & Animations, 2 = Top Rankers & Social Tricks
    val primaryColor = MaterialTheme.colorScheme.primary
    val labTabs = listOf(
        Triple("۱. جمع‌بندی نکات حفظی کنکور و نهایی", "رمزگردانی، کلمات کلیدی و نکات پرتکرار دروس انسانی", primaryColor),
        Triple("۲. تمرین تقطیع عروض و قافیه", "تقطیع هجایی، تشخیص وزن سماعی و قواعد قافیه", primaryColor),
        Triple("۳. تمرین حل معادله و علامت‌ها در ریاضی", "حل گام‌به‌گام معادلات، کسرها و عبارت‌های جبری", primaryColor),
        Triple("۴. جدول و تمرین صرف ۱۴ صیغه عربی", "صرف فعل ماضی، مضارع، امر و تشخیص اوزان ثلاثی مزید", primaryColor),
        Triple("۵. خلاصه قواعد و گرامر زبان انگلیسی", "زمان‌ها، معلوم و مجهول، جملات شرطی و ضمایر موصولی", primaryColor),
        Triple("۶. تمرین مربع تقابل و قضایای منطق", "تناقض، تضاد، دخول تحت تضاد، تداخل و قیاس‌ها", primaryColor),
        Triple("۷. فرمول‌های اقتصاد و نکات علوم انسانی", "مسائل سود، مالیات، استهلاک، عرضه و تقاضا و جامعه‌شناسی", primaryColor),
        Triple("۸. تخمین تراز فرهنگیان ۱۴۰۶", "محاسبه تراز کل، سوابق تحصیلی نهایی و دفترچه فرهنگیان", primaryColor)
    )
    val labIcons = listOf(
        Icons.Default.School,
        Icons.Default.MusicNote,
        Icons.Default.Calculate,
        Icons.Default.Translate,
        Icons.Default.Translate,
        Icons.Default.GraphicEq,
        Icons.Default.Calculate,
        Icons.Default.School
    )

    if (selectedLabIndex < 0) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
                .testTag("labs_vertical_list"),
            contentPadding = PaddingValues(start = 20.dp, top = 12.dp, end = 20.dp, bottom = 88.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            item(key = "labs_header") {
                Surface(
                    shape = RoundedCornerShape(20.dp),
                    color = MaterialTheme.colorScheme.surface,
                    border = androidx.compose.foundation.BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(
                        modifier = Modifier.padding(horizontal = 14.dp, vertical = 12.dp),
                        verticalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(6.dp)
                                .background(
                                    Brush.linearGradient(
                                        listOf(
                                            MaterialTheme.colorScheme.primary.copy(alpha = 0.35f),
                                            MaterialTheme.colorScheme.secondary.copy(alpha = 0.35f),
                                            MaterialTheme.colorScheme.tertiary.copy(alpha = 0.35f)
                                        )
                                    ),
                                    RoundedCornerShape(3.dp)
                                )
                        )
                        Text(
                            text = "کارگاه‌های عملی و نمودارهای آموزشی",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(6.dp)
                        ) {
                            val modes = listOf(
                                0 to "کارگاه‌ها",
                                1 to "نمودارها",
                                2 to "نکات رتبه‌ها",
                                3 to "لغت و اشعار"
                            )
                            modes.forEach { (idx, label) ->
                                val selected = topSectionMode == idx
                                Surface(
                                    modifier = Modifier
                                        .weight(1f)
                                        .clip(RoundedCornerShape(10.dp))
                                        .clickable { topSectionMode = idx }
                                        .testTag("labs_mode_$idx"),
                                    shape = RoundedCornerShape(10.dp),
                                    color = if (selected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.background,
                                    border = androidx.compose.foundation.BorderStroke(
                                        1.dp,
                                        if (selected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.outlineVariant
                                    )
                                ) {
                                    Text(
                                        text = label,
                                        style = MaterialTheme.typography.labelSmall,
                                        fontWeight = if (selected) FontWeight.Bold else FontWeight.Medium,
                                        color = if (selected) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.onSurfaceVariant,
                                        textAlign = TextAlign.Center,
                                        modifier = Modifier.padding(horizontal = 4.dp, vertical = 8.dp),
                                        maxLines = 1
                                    )
                                }
                            }
                        }
                    }
                }
            }

            if (topSectionMode == 1) {
                item(key = "labs_visual_zoom_pan_and_animator") {
                    Column(verticalArrangement = Arrangement.spacedBy(14.dp)) {
                        VisualMemoryTreeAndTimelineCard()
                        FifteenChartFamiliesGalleryCard()
                    }
                }
            } else if (topSectionMode == 2) {
                item(key = "labs_top_rankers_hub") {
                    TopRankersAndSocialTricksHub()
                }
            } else if (topSectionMode == 3) {
                item(key = "labs_comprehensive_lexicon_poetry_hub") {
                    ComprehensiveLexiconAndPoetryHubCard()
                }
            } else {
                items(labTabs.size) { index ->
                    val (title, subtitle, _) = labTabs[index]
                    Surface(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(18.dp))
                            .clickable { selectedLabIndex = index }
                            .testTag("lab_tab_$index"),
                        shape = RoundedCornerShape(18.dp),
                        color = MaterialTheme.colorScheme.surface,
                        border = androidx.compose.foundation.BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant)
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 16.dp, vertical = 14.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(46.dp)
                                    .background(
                                        Brush.linearGradient(
                                            listOf(
                                                MaterialTheme.colorScheme.primaryContainer,
                                                MaterialTheme.colorScheme.secondaryContainer
                                            )
                                        ),
                                        RoundedCornerShape(14.dp)
                                    ),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(
                                    imageVector = labIcons[index],
                                    contentDescription = null,
                                    tint = MaterialTheme.colorScheme.primary,
                                    modifier = Modifier.size(26.dp)
                                )
                            }
                            Spacer(modifier = Modifier.width(10.dp))
                            Column(
                                modifier = Modifier.weight(1f),
                                verticalArrangement = Arrangement.spacedBy(4.dp)
                            ) {
                                Text(
                                    text = BidiFormatter.formatMixedPersianText(title),
                                    style = MaterialTheme.typography.titleSmall,
                                    fontWeight = FontWeight.Bold,
                                    color = MaterialTheme.colorScheme.onSurface
                                )
                                Text(
                                    text = BidiFormatter.formatMixedPersianText(subtitle),
                                    style = MaterialTheme.typography.bodySmall,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                            }
                            Spacer(modifier = Modifier.width(8.dp))
                            Surface(
                                color = MaterialTheme.colorScheme.primaryContainer,
                                shape = RoundedCornerShape(8.dp)
                            ) {
                                Text(
                                    text = "ورود ←",
                                    style = MaterialTheme.typography.labelSmall,
                                    fontWeight = FontWeight.Bold,
                                    color = MaterialTheme.colorScheme.onPrimaryContainer,
                                    modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp)
                                )
                            }
                        }
                    }
                }
            }
        }
    } else {
        androidx.activity.compose.BackHandler {
            selectedLabIndex = -1
        }
        val currentLab = labTabs.getOrElse(selectedLabIndex) { labTabs.first() }
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
        ) {
            Surface(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp, vertical = 8.dp),
                shape = RoundedCornerShape(14.dp),
                color = MaterialTheme.colorScheme.surface,
                border = androidx.compose.foundation.BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { selectedLabIndex = -1 }
                        .padding(horizontal = 14.dp, vertical = 10.dp)
                        .testTag("back_to_labs_list_btn"),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = BidiFormatter.formatMixedPersianText(currentLab.first),
                            style = MaterialTheme.typography.titleSmall,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                        Text(
                            text = "بازگشت به فهرست کارگاه‌های عملی",
                            style = MaterialTheme.typography.labelSmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                    Surface(
                        color = MaterialTheme.colorScheme.primaryContainer,
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        Text(
                            text = "بازگشت ←",
                            style = MaterialTheme.typography.labelSmall,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onPrimaryContainer,
                            modifier = Modifier.padding(horizontal = 10.dp, vertical = 5.dp)
                        )
                    }
                }
            }

            when (selectedLabIndex) {
                0 -> MnemonicAcronymVaultLabView()
                1 -> AroozProsodyLabView()
                2 -> MathStepByStepSolverView()
                3 -> ArabicPatternAnalyzerView()
                4 -> EnglishGrammarAntiConfusionLabView()
                5 -> PhilosophyAndLogicSquareLabView()
                6 -> HumanitiesComprehensiveSuperLabView()
                7 -> FarhangianScoreSimulatorView()
            }
        }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
private fun AroozProsodyLabView() {
    var selectedVerseIndex by remember { mutableIntStateOf(0) }
    var customWordInput by remember { mutableStateOf("آسمانِ آبی") }
    val currentVerse = presetVerses[selectedVerseIndex]

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(start = 16.dp, top = 16.dp, end = 16.dp, bottom = 88.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            Card(
                shape = RoundedCornerShape(24.dp),
                colors = CardDefaults.cardColors(
                    containerColor = SubjectAroozColor.copy(alpha = 0.1f)
                ),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(6.dp)
                            .background(
                                Brush.linearGradient(
                                    listOf(
                                        SubjectAroozColor.copy(alpha = 0.25f),
                                        MaterialTheme.colorScheme.tertiary.copy(alpha = 0.25f)
                                    )
                                ),
                                RoundedCornerShape(3.dp)
                            )
                    )
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(Icons.Default.MusicNote, contentDescription = null, tint = SubjectAroozColor)
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "کارگاه دیداری و سماعی عروض و قافیه (از منفی صفر)",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold,
                            color = SubjectAroozColor
                        )
                    }
                    Spacer(modifier = Modifier.height(6.dp))
                    Text(
                        text = "روی هر یک از ابیات پرتکرار کنکور کلیک کن تا ببینی چطور کلمات به هم وصل می‌شوند (حذف همزه) و چطور از روی ۴ هجای اول، وزن شعر در ۵ ثانیه پیدا می‌شود!",
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            }
        }

        // Preset verses selector
        item {
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                Text(
                    text = "انتخاب بیت آزمایشگاهی:",
                    style = MaterialTheme.typography.titleSmall,
                    fontWeight = FontWeight.Bold
                )
                presetVerses.forEachIndexed { idx, verse ->
                    val selected = idx == selectedVerseIndex
                    Surface(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { selectedVerseIndex = idx }
                            .testTag("preset_verse_$idx"),
                        shape = RoundedCornerShape(14.dp),
                        color = if (selected) SubjectAroozColor.copy(alpha = 0.16f) else MaterialTheme.colorScheme.surface,
                        tonalElevation = if (selected) 0.dp else 1.dp
                    ) {
                        Text(
                            text = verse.title,
                            style = MaterialTheme.typography.bodyMedium,
                            fontWeight = if (selected) FontWeight.Bold else FontWeight.Normal,
                            color = if (selected) SubjectAroozColor else MaterialTheme.colorScheme.onSurface,
                            modifier = Modifier.padding(12.dp)
                        )
                    }
                }
            }
        }

        // Visual Syllable Blocks for Selected Verse
        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Text(
                        text = "۱. برش هجایی و علامت‌گذاری خط عروضی (شنیداری):",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold
                    )

                    FlowRow(
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        currentVerse.phoneticSyllables.forEach { (syl, sign) ->
                            val badgeColor = if (sign == "U") Color(0xFF0284C7) else SubjectAroozColor
                            Surface(
                                shape = RoundedCornerShape(12.dp),
                                color = badgeColor.copy(alpha = 0.14f)
                            ) {
                                Column(
                                    horizontalAlignment = Alignment.CenterHorizontally,
                                    modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp)
                                ) {
                                    Text(
                                        text = syl,
                                        style = MaterialTheme.typography.titleSmall,
                                        fontWeight = FontWeight.Bold
                                    )
                                    Text(
                                        text = sign,
                                        style = MaterialTheme.typography.titleMedium,
                                        fontWeight = FontWeight.Bold,
                                        color = badgeColor
                                    )
                                    Text(
                                        text = if (sign == "U") "کوتاه" else "بلند",
                                        style = MaterialTheme.typography.labelSmall,
                                        color = badgeColor
                                    )
                                }
                            }
                        }
                    }

                    HorizontalDivider()

                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(Icons.Default.GraphicEq, contentDescription = null, tint = SubjectAroozColor)
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "ضرب‌آهنگ سماعی (با دست روی میز بزن!):",
                            style = MaterialTheme.typography.titleSmall,
                            fontWeight = FontWeight.Bold
                        )
                    }
                    Text(
                        text = currentVerse.samaeiRhythm,
                        style = MaterialTheme.typography.bodyMedium,
                        color = SubjectAroozColor,
                        fontWeight = FontWeight.SemiBold
                    )

                    Surface(
                        color = MaterialTheme.colorScheme.surfaceVariant,
                        shape = RoundedCornerShape(12.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Column(modifier = Modifier.padding(12.dp)) {
                            Text(
                                text = "🎼 وزن عروضی: ${currentVerse.meterName}",
                                style = MaterialTheme.typography.bodyMedium,
                                fontWeight = FontWeight.Bold
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = "🔍 کالبدشکافی قافیه و ردیف: ${currentVerse.ghafiehRule}",
                                style = MaterialTheme.typography.bodySmall
                            )
                        }
                    }
                }
            }
        }

        // Custom Word / Phrase Syllable Helper
        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    Text(
                        text = "۲. تبدیل‌گر هوشمند کلمه به خط عروضی (قانون حذف و تشدید):",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold
                    )
                    OutlinedTextField(
                        value = customWordInput,
                        onValueChange = { customWordInput = it },
                        label = { Text("یک کلمه یا ترکیب بنویس (مثل: خواب، خویش، آسمان، خواهر)") },
                        modifier = Modifier
                            .fillMaxWidth()
                            .testTag("custom_arooz_input")
                    )

                    val analysis = remember(customWordInput) { analyzePersianProsodyWord(customWordInput) }
                    Surface(
                        color = MaterialTheme.colorScheme.secondaryContainer.copy(alpha = 0.5f),
                        shape = RoundedCornerShape(14.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Column(modifier = Modifier.padding(12.dp)) {
                            Text(
                                text = "خط عروضی (شنیداری): ${analysis.first}",
                                style = MaterialTheme.typography.titleSmall,
                                fontWeight = FontWeight.Bold
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = analysis.second,
                                style = MaterialTheme.typography.bodyMedium
                            )
                        }
                    }
                }
            }
        }
    }
}

private fun analyzePersianProsodyWord(input: String): Pair<String, String> {
    val clean = input.trim()
    if (clean.isEmpty()) return "—" to "کلمه‌ای وارد کنید تا قوانین خط عروضی آن نمایش داده شود."
    val notes = mutableListOf<String>()
    var phonetic = clean
    if (clean.contains("خواب") || clean.contains("خواه") || clean.contains("خوان") || clean.contains("خویش") || clean.contains("خورد")) {
        phonetic = phonetic
            .replace("خواب", "خاب")
            .replace("خواه", "خاه")
            .replace("خوان", "خان")
            .replace("خویش", "خیش")
            .replace("خورشید", "خُرشید")
        notes.add("• حرف «و» معدوله در (خواب/خواهر/خویش/خواندن) خوانده نمی‌شود و در خط عروضی حذف می‌گردد!")
    }
    if (clean.contains("ّ")) {
        notes.add("• این عبارت دارای تشدید (ّ) است؛ در عروض حرف مشدد ۲ بار نوشته می‌شود (یک بار ساکن در هجای قبلی، یک بار متحرک در هجای بعدی).")
    }
    if (clean.endsWith("ان") || clean.endsWith("ون") || clean.endsWith("ین")) {
        notes.add("• ختم به مصوت بلند + «ن» ساکن (مثل آسمان/جان/خون): در حالت عادی هجای کشیده (–U) است اما در وسط مصراع در صورت نیاز وزن، «ن» تلفظ نمی‌شود و بلند (–) حساب می‌شود.")
    }
    if (clean.endsWith("ه")) {
        notes.add("• اگر «ه» آخر کلمه صدای «ـِ» بدهد (مثل خانه، ستاره)، مصوت کوتاه است و همیشه هجای کوتاه (U) می‌سازد!")
    }
    if (notes.isEmpty()) {
        notes.add("• کلمه را دقیقاً بر اساس صدای تلفظ‌شده بخش کنید: هر بخش با صدای کوتاه (ـَـِـُ) = U ، با مصوت بلند (آ/او/ای) یا سه حرفی (صامت+مصوت+صامت) = – ، و چهارحرفی یا مصوت بلند+ساکن = کشیده (–U).")
    }
    return phonetic to notes.joinToString("\n")
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
private fun MathStepByStepSolverView() {
    // 1. Invented Number-Line Elevator & Sign Anti-Confusion Machine
    var num1Sign by remember { mutableIntStateOf(-1) }
    var num1Abs by remember { mutableIntStateOf(5) }
    var num2Sign by remember { mutableIntStateOf(-1) }
    var num2Abs by remember { mutableIntStateOf(3) }
    var isMultiplyMode by remember { mutableStateOf(false) }

    var coefA by remember { mutableStateOf("2") }
    var coefB by remember { mutableStateOf("-9") }
    var coefC by remember { mutableStateOf("7") }

    var seqTerm1Index by remember { mutableStateOf("3") }
    var seqTerm1Value by remember { mutableStateOf("10") }
    var seqTerm2Index by remember { mutableStateOf("7") }
    var seqTerm2Value by remember { mutableStateOf("26") }
    var seqTargetIndex by remember { mutableStateOf("10") }

    val n1 = num1Sign * num1Abs
    val n2 = num2Sign * num2Abs

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(start = 16.dp, top = 16.dp, end = 16.dp, bottom = 88.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Graphical Table & Tree of Number Sets (N, W, Z, Q, Q', R)
        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(22.dp),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surface
                ),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
            ) {
                Column(
                    modifier = Modifier.padding(14.dp),
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    Text(
                        text = "📊 جدول و درخت گرافیکی مجموعه‌های اعداد (پایه اول ابتدایی تا نهم و دهم انسانی)",
                        style = MaterialTheme.typography.titleSmall,
                        fontWeight = FontWeight.ExtraBold,
                        color = SubjectMathColor
                    )
                    com.example.ui.components.SubjectGraphicalMasterTable(
                        subjectId = "math",
                        accentColor = SubjectMathColor
                    )
                    com.example.ui.components.SubjectGraphicalMasterTree(
                        subjectId = "math",
                        accentColor = SubjectMathColor
                    )
                }
            }
        }

        // Invented Tool 1: Number-Line Elevator & Sign Anti-Confusion Machine
        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(22.dp),
                colors = CardDefaults.cardColors(
                    containerColor = SubjectMathColor.copy(alpha = 0.1f)
                )
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(Icons.Default.Calculate, contentDescription = null, tint = SubjectMathColor)
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "۱. قواعد جمع، تفریق، ضرب و تقسیم اعداد علامت‌دار",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold,
                            color = SubjectMathColor
                        )
                    }
                    Text(
                        text = "توجه مهم: علامت منفی همیشه در سمت چپ عدد نوشته می‌شود (مثل \u2066\u200E−۵\u200E\u2069). بسیاری از دانش‌آموزان قانون «جمع و تفریق» را با «ضرب و تقسیم» قاطی می‌کنند! با دکمه‌های زیر امتحان کن:",
                        style = MaterialTheme.typography.bodySmall
                    )

                    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        FilterChip(
                            selected = !isMultiplyMode,
                            onClick = { isMultiplyMode = false },
                            label = { Text("حالت ۱: جمع و تفریق (پول + و بدهی −)") }
                        )
                        FilterChip(
                            selected = isMultiplyMode,
                            onClick = { isMultiplyMode = true },
                            label = { Text("حالت ۲: ضرب و تقسیم (دوست و دشمن)") }
                        )
                    }

                    // Number 1 & Number 2 quick selectors
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Surface(
                            color = MaterialTheme.colorScheme.surface,
                            shape = RoundedCornerShape(14.dp),
                            modifier = Modifier.weight(1f)
                        ) {
                            Column(
                                modifier = Modifier.padding(10.dp),
                                verticalArrangement = Arrangement.spacedBy(6.dp)
                            ) {
                                Text(
                                    text = BidiFormatter.formatMixedPersianText("عدد اول: ${if (n1 >= 0) "+$n1" else "$n1"}"),
                                    style = MaterialTheme.typography.titleSmall,
                                    fontWeight = FontWeight.Bold
                                )
                                Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                                    FilterChip(
                                        selected = num1Sign < 0,
                                        onClick = { num1Sign = -1 },
                                        label = { Text(BidiFormatter.formatMixedPersianText("منفی (−)")) }
                                    )
                                    FilterChip(
                                        selected = num1Sign > 0,
                                        onClick = { num1Sign = 1 },
                                        label = { Text(BidiFormatter.formatMixedPersianText("مثبت (+)")) }
                                    )
                                }
                                Slider(
                                    value = num1Abs.toFloat(),
                                    onValueChange = { num1Abs = it.toInt().coerceIn(1, 15) },
                                    valueRange = 1f..15f
                                )
                            }
                        }

                        Surface(
                            color = MaterialTheme.colorScheme.surface,
                            shape = RoundedCornerShape(14.dp),
                            modifier = Modifier.weight(1f)
                        ) {
                            Column(
                                modifier = Modifier.padding(10.dp),
                                verticalArrangement = Arrangement.spacedBy(6.dp)
                            ) {
                                Text(
                                    text = BidiFormatter.formatMixedPersianText("عدد دوم: ${if (n2 >= 0) "+$n2" else "$n2"}"),
                                    style = MaterialTheme.typography.titleSmall,
                                    fontWeight = FontWeight.Bold
                                )
                                Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                                    FilterChip(
                                        selected = num2Sign < 0,
                                        onClick = { num2Sign = -1 },
                                        label = { Text(BidiFormatter.formatMixedPersianText("منفی (−)")) }
                                    )
                                    FilterChip(
                                        selected = num2Sign > 0,
                                        onClick = { num2Sign = 1 },
                                        label = { Text(BidiFormatter.formatMixedPersianText("مثبت (+)")) }
                                    )
                                }
                                Slider(
                                    value = num2Abs.toFloat(),
                                    onValueChange = { num2Abs = it.toInt().coerceIn(1, 15) },
                                    valueRange = 1f..15f
                                )
                            }
                        }
                    }

                    val elevatorStory = if (!isMultiplyMode) {
                        val sum = n1 + n2
                        val s1Text = if (n1 < 0) "${num1Abs} هزار تومان بدهی داری ($n1)" else "${num1Abs} هزار تومان پول داری (+$n1)"
                        val s2Text = if (n2 < 0) "${num2Abs} هزار تومان دیگر هم بدهی بالا می‌آوری ($n2)" else "${num2Abs} هزار تومان پول به دست می‌آوری (+$n2)"
                        val resultStory = when {
                            sum < 0 -> "در مجموع ${kotlin.math.abs(sum)} هزار تومان بدهکار می‌مانی! پس علامت جواب منفی در سمت چپ است: ($sum)"
                            sum > 0 -> "در مجموع $sum هزار تومان پول برایت می‌ماند! پس علامت جواب مثبت است: (+$sum)"
                            else -> "بدهی و پولت کامل صاف می‌شود و به طبقه صفر (0) می‌رسی!"
                        }
                        "(${if (n1 >= 0) "+$n1" else "$n1"}) + (${if (n2 >= 0) "+$n2" else "$n2"}) = ${if (sum > 0) "+$sum" else "$sum"}\n" +
                                "• داستان آسانسور و جیب: اول $s1Text، بعد $s2Text.\n" +
                                "• نتیجه قطعی: $resultStory\n" +
                                "⚠️ هشدار طلایی: در جمع دو عدد منفی، هرگز نگو «منفی در منفی مثبت می‌شود»! دو تا بدهی روی هم، بدهیِ بزرگ‌تر می‌سازد!"
                    } else {
                        val prod = n1 * n2
                        val signRule = when {
                            n1 < 0 && n2 < 0 -> "دشمنِ (−) دشمنِ من (−) = دوستِ من (+) است! (هم‌علامت در ضرب = مثبت)"
                            n1 > 0 && n2 > 0 -> "دوستِ (+) دوستِ من (+) = دوستِ من (+) است! (هم‌علامت در ضرب = مثبت)"
                            else -> "دوستِ (+) دشمنِ من (−) = دشمنِ من (−) است! (ناهم‌علامت در ضرب = منفی)"
                        }
                        "(${if (n1 >= 0) "+$n1" else "$n1"}) × (${if (n2 >= 0) "+$n2" else "$n2"}) = ${if (prod > 0) "+$prod" else "$prod"}\n" +
                                "• قانون ضرب و تقسیم: $signRule\n" +
                                "• گام ۱ (تعیین علامت): ${if (prod >= 0) "+" else "−"} | گام ۲ (ضرب دو عدد در هم): $num1Abs × $num2Abs = ${kotlin.math.abs(prod)}"
                    }

                    SmartDiagramAndFormulaBlock(
                        rawText = elevatorStory,
                        accentColor = SubjectMathColor
                    )
                }
            }
        }

        // Invented Tool 2: Step-by-Step Quadratic Equation Solver
        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(Icons.Default.Calculate, contentDescription = null, tint = SubjectMathColor)
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "۲. حل‌کننده گام‌به‌گام معادله درجه ۲ به روش شگردهای کنکوری",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold,
                            color = SubjectMathColor
                        )
                    }
                    Text(
                        text = BidiFormatter.formatMixedPersianText(
                            "ضرایب معادله ax² + bx + c = 0 را وارد کن تا ببینی چطور با شگرد جمع ضرایب یا دلتا حل می‌شود:"
                        ),
                        style = MaterialTheme.typography.bodySmall
                    )

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        OutlinedTextField(
                            value = coefA,
                            onValueChange = { coefA = it },
                            label = { Text("ضریب a (پشت x²)") },
                            modifier = Modifier.weight(1f)
                        )
                        OutlinedTextField(
                            value = coefB,
                            onValueChange = { coefB = it },
                            label = { Text("ضریب b (پشت x)") },
                            modifier = Modifier.weight(1f)
                        )
                        OutlinedTextField(
                            value = coefC,
                            onValueChange = { coefC = it },
                            label = { Text("عدد ثابت c") },
                            modifier = Modifier.weight(1f)
                        )
                    }

                    val a = coefA.toDoubleOrNull() ?: 1.0
                    val b = coefB.toDoubleOrNull() ?: 0.0
                    val c = coefC.toDoubleOrNull() ?: 0.0

                    val explanation = buildString {
                        if (a == 0.0) {
                            append("x = −c / b\nچون a = 0 است، معادله درجه اول است.")
                        } else {
                            append("(${a.toInt()})x² + (${b.toInt()})x + (${c.toInt()}) = 0\n")
                            if (a + b + c == 0.0) {
                                append("• شگرد طلایی ۱ برقرار است! جمع ضرایب صفر شد (a + b + c = 0):\n")
                                append("x₁ = 1 , x₂ = c / a = ${c.toInt()} / ${a.toInt()}\n")
                            } else if (a - b + c == 0.0) {
                                append("• شگرد طلایی ۲ برقرار است! (a − b + c = 0):\n")
                                append("x₁ = −1 , x₂ = −c / a = ${(-c).toInt()} / ${a.toInt()}\n")
                            }
                            val delta = b * b - 4 * a * c
                            append("Δ = b² − 4ac = (${b.toInt()})² − 4(${a.toInt()})(${c.toInt()}) = ${delta.toInt()}\n")
                            if (delta > 0) {
                                val r1 = (-b + sqrt(delta)) / (2 * a)
                                val r2 = (-b - sqrt(delta)) / (2 * a)
                                append("x₁ = ${"%.2f".format(r1)} , x₂ = ${"%.2f".format(r2)}\n")
                            } else if (delta == 0.0) {
                                val r = -b / (2 * a)
                                append("x = ${"%.2f".format(r)}\n• دلتا صفر است (یک ریشه مضاعف).\n")
                            } else {
                                append("• چون دلتا منفی است، معادله ریشه حقیقی ندارد!\n")
                            }
                            append("S = −b / a = ${(-b).toInt()} / ${a.toInt()} , P = c / a = ${c.toInt()} / ${a.toInt()}")
                        }
                    }

                    SmartDiagramAndFormulaBlock(
                        rawText = explanation,
                        accentColor = SubjectMathColor
                    )
                }
            }
        }

        // Invented Tool 3: Sequence Solver
        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Text(
                        text = "۳. حل‌کننده دنباله حسابی به روش «تعداد قدم‌ها» (بدون فرمول پیچیده):",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = SubjectMathColor
                    )

                    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        OutlinedTextField(
                            value = seqTerm1Index,
                            onValueChange = { seqTerm1Index = it },
                            label = { Text("شماره جمله اول (m)") },
                            modifier = Modifier.weight(1f)
                        )
                        OutlinedTextField(
                            value = seqTerm1Value,
                            onValueChange = { seqTerm1Value = it },
                            label = { Text("مقدار جمله (aₘ)") },
                            modifier = Modifier.weight(1f)
                        )
                    }
                    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        OutlinedTextField(
                            value = seqTerm2Index,
                            onValueChange = { seqTerm2Index = it },
                            label = { Text("شماره جمله دوم (n)") },
                            modifier = Modifier.weight(1f)
                        )
                        OutlinedTextField(
                            value = seqTerm2Value,
                            onValueChange = { seqTerm2Value = it },
                            label = { Text("مقدار جمله (aₙ)") },
                            modifier = Modifier.weight(1f)
                        )
                    }
                    OutlinedTextField(
                        value = seqTargetIndex,
                        onValueChange = { seqTargetIndex = it },
                        label = { Text("کدام جمله دنباله را می‌خواهی پیدا کنی؟ (مثلاً جمله ۱۰)") },
                        modifier = Modifier.fillMaxWidth()
                    )

                    val m = seqTerm1Index.toIntOrNull() ?: 1
                    val am = seqTerm1Value.toDoubleOrNull() ?: 0.0
                    val n = seqTerm2Index.toIntOrNull() ?: 2
                    val an = seqTerm2Value.toDoubleOrNull() ?: 0.0
                    val k = seqTargetIndex.toIntOrNull() ?: 10

                    val seqText = if (m == n) {
                        "شماره دو جمله باید متفاوت باشد."
                    } else {
                        val steps = n - m
                        val diff = an - am
                        val d = diff / steps
                        val ak = am + (k - m) * d
                        "d = (${an.toInt()} − ${am.toInt()}) / ($n − $m) = ${diff.toInt()} / $steps = ${"%.2f".format(d)}\n" +
                                "• گام ۱: فاصله از جمله $m تا جمله $n برابر با $steps قدم است.\n" +
                                "• گام ۲: اندازه هر قدم (قدر نسبت d) برابر با ${"%.2f".format(d)} است.\n" +
                                "• گام ۳: حالا از جمله $m تا جمله $k به تعداد (${k - m}) قدم حرکت می‌کنیم:\n" +
                                "a_$k = ${am.toInt()} + (${k - m}) × (${"%.2f".format(d)}) = ${"%.2f".format(ak)}"
                    }

                    SmartDiagramAndFormulaBlock(
                        rawText = seqText,
                        accentColor = SubjectMathColor
                    )
                }
            }
        }
    }
}

private data class SighaRoomEntry(
    val roomIndex: Int, // 0 = غایب, 1 = مخاطب, 2 = متکلم
    val sighaNum: String,
    val pronounLabel: String,
    val pastVerb: String,
    val pastCode: String,
    val pastMeaning: String,
    val presentVerb: String,
    val presentCode: String,
    val presentMeaning: String,
    val commandAndPassive: String
)

private data class ArabicAntiConfusionPair(
    val title: String,
    val leftRuleName: String,
    val leftVisualSign: String,
    val leftExample: String,
    val rightRuleName: String,
    val rightVisualSign: String,
    val rightExample: String,
    val goldenShortcut: String
)

@OptIn(ExperimentalLayoutApi::class)
@Composable
private fun ArabicPatternAnalyzerView() {
    var selectedRoomIndex by remember { mutableIntStateOf(0) }
    var selectedSighaInRoom by remember { mutableIntStateOf(0) }
    var selectedComparisonIndex by remember { mutableIntStateOf(0) }

    val rooms = listOf(
        "اتاق ۱: غایب (او / آن‌ها - ۶ صیغه)",
        "اتاق ۲: مخاطب (تو / شما - ۶ صیغه)",
        "اتاق ۳: متکلم (من / ما - ۲ صیغه)"
    )

    val all14Sighas = listOf(
        // Room 0: غایب (۶ صیغه)
        SighaRoomEntry(
            roomIndex = 0,
            sighaNum = "صیغه ۱ (مفرد مذکر غایب)",
            pronounLabel = "هُوَ (او - ۱ مرد)",
            pastVerb = "ذَهَبَ",
            pastCode = "آخرش هیچ پسوندی ندارد (فقط فتحه ـَ)",
            pastMeaning = "او رفت",
            presentVerb = "یَذهَبُ",
            presentCode = "سرش «یـ» دارد + آخرش تنهاست (ـُ)",
            presentMeaning = "او می‌رود",
            commandAndPassive = "امر غایب: لِیَذهَبْ (باید برود) | مجهول: یُکتَبُ (نوشته می‌شود)"
        ),
        SighaRoomEntry(
            roomIndex = 0,
            sighaNum = "صیغه ۲ (مثنی مذکر غایب)",
            pronounLabel = "هُما (آن دو مرد)",
            pastVerb = "ذَهَبا",
            pastCode = "دمش الفِ دو نفره «ـا» دارد",
            pastMeaning = "آن دو رفتند",
            presentVerb = "یَذهَبانِ",
            presentCode = "سرش «یـ» + دمش «ـانِ» (الف و نون دو نفره)",
            presentMeaning = "آن دو می‌روند",
            commandAndPassive = "نهی: لا یَذهَبا (نونِ آخر در جزم حذف می‌شود)"
        ),
        SighaRoomEntry(
            roomIndex = 0,
            sighaNum = "صیغه ۳ (جمع مذکر غایب)",
            pronounLabel = "هُمْ (آن‌ها - مردان)",
            pastVerb = "ذَهَبوا",
            pastCode = "دمش «ـوا» دارد (واو جمع =ند)",
            pastMeaning = "آن‌ها رفتند",
            presentVerb = "یَذهَبونَ",
            presentCode = "سرش «یـ» (آن‌ها) + دمش «ـونَ» (جمع)",
            presentMeaning = "آن‌ها می‌روند",
            commandAndPassive = "با لَمْ: لَمْ یَذهَبوا = نرفتند (گذشته منفی!)"
        ),
        SighaRoomEntry(
            roomIndex = 0,
            sighaNum = "صیغه ۴ (مفرد مؤنث غایب)",
            pronounLabel = "هِیَ (او - ۱ زن)",
            pastVerb = "ذَهَبَتْ",
            pastCode = "دمش «ـَتْ» ساکن دارد (تاء تأنیث = فقط برای ۱ زن غایب!)",
            pastMeaning = "او (زن) رفت",
            presentVerb = "تَذهَبُ",
            presentCode = "سرش «تـ» دارد (وقتی فاعلِ جمله مؤنث است)",
            presentMeaning = "او (زن) می‌رود",
            commandAndPassive = "مجهول ماضی: کُتِبَتْ (نوشته شد)"
        ),
        SighaRoomEntry(
            roomIndex = 0,
            sighaNum = "صیغه ۵ (مثنی مؤنث غایب)",
            pronounLabel = "هُما (آن دو زن)",
            pastVerb = "ذَهَبَتا",
            pastCode = "دمش «ـَتا» دارد (تاء مؤنث + الف دو نفره)",
            pastMeaning = "آن دو (زن) رفتند",
            presentVerb = "تَذهَبانِ",
            presentCode = "سرش «تـ» + دمش «ـانِ»",
            presentMeaning = "آن دو (زن) می‌روند",
            commandAndPassive = "نهی: لا تَذهَبا"
        ),
        SighaRoomEntry(
            roomIndex = 0,
            sighaNum = "صیغه ۶ (جمع مؤنث غایب)",
            pronounLabel = "هُنَّ (آن زنان)",
            pastVerb = "ذَهَبْنَ",
            pastCode = "دمش «ـْنَ» دارد (نون نسوه)",
            pastMeaning = "آن‌ها (زنان) رفتند",
            presentVerb = "یَذهَبْنَ",
            presentCode = "سرش «یـ» + دمش «ـْنَ» (نون زنان که هرگز حذف نمی‌شود!)",
            presentMeaning = "آن‌ها (زنان) می‌روند",
            commandAndPassive = "نکته: نونِ «یَذهَبْنَ» در امر و نهی هرگز حذف نمی‌شود!"
        ),

        // Room 1: مخاطب (۶ صیغه)
        SighaRoomEntry(
            roomIndex = 1,
            sighaNum = "صیغه ۷ (مفرد مذکر مخاطب)",
            pronounLabel = "أَنتَ (تو - ۱ مرد)",
            pastVerb = "ذَهَبْتَ",
            pastCode = "دمش «ـتَ» دارد (تَ در انتَ = تَ آخر فعل = تو!)",
            pastMeaning = "تو رفتی",
            presentVerb = "تَذهَبُ",
            presentCode = "سرش «تـ» دارد (تِ تو!) + آخرش تنهاست",
            presentMeaning = "تو می‌روی",
            commandAndPassive = "امر حاضر: إِذهَبْ (برو!) | نهی: لا تَذهَبْ (نرو!)"
        ),
        SighaRoomEntry(
            roomIndex = 1,
            sighaNum = "صیغه ۸ (مثنی مذکر مخاطب)",
            pronounLabel = "أَنتُما (شما دو نفر)",
            pastVerb = "ذَهَبْتُما",
            pastCode = "دمش «ـتُما» دارد (دقیقاً هم‌قافیه با أَنتُما!)",
            pastMeaning = "شما دو نفر رفتید",
            presentVerb = "تَذهَبانِ",
            presentCode = "سرش «تـ» (شما) + دمش «ـانِ» (دو نفر)",
            presentMeaning = "شما دو نفر می‌روید",
            commandAndPassive = "امر حاضر: إِذهَبا (بروید - دو نفر) | نهی: لا تَذهَبا"
        ),
        SighaRoomEntry(
            roomIndex = 1,
            sighaNum = "صیغه ۹ (جمع مذکر مخاطب)",
            pronounLabel = "أَنتُمْ (شما مردان)",
            pastVerb = "ذَهَبْتُمْ",
            pastCode = "دمش «ـتُمْ» دارد (دقیقاً هم‌قافیه با أَنتُمْ = شما!)",
            pastMeaning = "شما رفتید",
            presentVerb = "تَذهَبونَ",
            presentCode = "سرش «تـ» (شما) + دمش «ـونَ» (جمع)",
            presentMeaning = "شما می‌روید",
            commandAndPassive = "امر حاضر: إِذهَبوا (بروید!) | نهی: لا تَذهَبوا (نروید!)"
        ),
        SighaRoomEntry(
            roomIndex = 1,
            sighaNum = "صیغه ۱۰ (مفرد مؤنث مخاطب)",
            pronounLabel = "أَنتِ (تو - ۱ زن)",
            pastVerb = "ذَهَبْتِ",
            pastCode = "دمش «ـتِ» کسره‌دار دارد (هم‌قافیه با أَنتِ!)",
            pastMeaning = "تو (زن) رفتی",
            presentVerb = "تَذهَبینَ",
            presentCode = "سرش «تـ» + دمش «ـینَ» (یاء مخاطبه برای ۱ زن)",
            presentMeaning = "تو (زن) می‌روی",
            commandAndPassive = "امر حاضر: إِذهَبی (برو - ۱ خانم!) | نهی: لا تَذهَبی (نون حذف شده)"
        ),
        SighaRoomEntry(
            roomIndex = 1,
            sighaNum = "صیغه ۱۱ (مثنی مؤنث مخاطب)",
            pronounLabel = "أَنتُما (شما دو زن)",
            pastVerb = "ذَهَبْتُما",
            pastCode = "دمش «ـتُما» دارد",
            pastMeaning = "شما دو زن رفتید",
            presentVerb = "تَذهَبانِ",
            presentCode = "سرش «تـ» + دمش «ـانِ»",
            presentMeaning = "شما دو زن می‌روید",
            commandAndPassive = "امر حاضر: إِذهَبا | نهی: لا تَذهَبا"
        ),
        SighaRoomEntry(
            roomIndex = 1,
            sighaNum = "صیغه ۱۲ (جمع مؤنث مخاطب)",
            pronounLabel = "أَنتُنَّ (شما زنان)",
            pastVerb = "ذَهَبْتُنَّ",
            pastCode = "دمش «ـتُنَّ» دارد (هم‌قافیه با أَنتُنَّ!)",
            pastMeaning = "شما (زنان) رفتید",
            presentVerb = "تَذهَبْنَ",
            presentCode = "سرش «تـ» (شما) + دمش «ـْنَ» (نون زنان)",
            presentMeaning = "شما (زنان) می‌روید",
            commandAndPassive = "امر حاضر: إِذهَبْنَ | نهی: لا تَذهَبْنَ (نون نسوه حذف نمی‌شود!)"
        ),

        // Room 2: متکلم (۲ صیغه)
        SighaRoomEntry(
            roomIndex = 2,
            sighaNum = "صیغه ۱۳ (متکلم وحده - من)",
            pronounLabel = "أَنا (من - ۱ نفر)",
            pastVerb = "ذَهَبْتُ",
            pastCode = "دمش «ـتُ» ضمه‌دار دارد (فقط صیغه من در ماضی صدای ـُ دارد!)",
            pastMeaning = "من رفتم",
            presentVerb = "أَذهَبُ",
            presentCode = "سرش «أَ» دارد (همزه أَنا = من!)",
            presentMeaning = "من می‌روم",
            commandAndPassive = "مجهول مضارع: أُکتَبُ / أُحسَبُ (شناخته می‌شوم)"
        ),
        SighaRoomEntry(
            roomIndex = 2,
            sighaNum = "صیغه ۱۴ (متکلم مع‌الغیر - ما)",
            pronounLabel = "نَحْنُ (ما - جمع)",
            pastVerb = "ذَهَبْنا",
            pastCode = "دمش «ـنا» دارد (نا = ما!)",
            pastMeaning = "ما رفتیم",
            presentVerb = "نَذهَبُ",
            presentCode = "سرش «نـ» دارد (نونِ نَحنُ = ما!)",
            presentMeaning = "ما می‌رویم",
            commandAndPassive = "با لَمْ: لَمْ نَذهَبْ = نرفتیم | ما ذَهَبْنا = نرفتیم"
        )
    )

    val antiConfusionPairs = listOf(
        ArabicAntiConfusionPair(
            title = "۱. ضد قاطی کردن: «کانَ و لَیسَ» در برابر «إِنَّ و لَعَلَّ» (پایه یازدهم)",
            leftRuleName = "خانواده افعال ناقصه (کانَ، صَارَ، أَصبَحَ، لَیسَ)",
            leftVisualSign = "اسمِ اولش مرفوع (ـُ / ـٌ) است و خبرش منصوب (ـً)!",
            leftExample = "کانَ الجَوُّ بارِداً (هوا سرد بود) | لَیسَ الکَذِبُ نافِعاً (دروغ سودمند نیست)",
            rightRuleName = "خانواده حروف مشبهة بالفعل (إِنَّ، أَنَّ، لٰکِنَّ، لَعَلَّ، لَیتَ)",
            rightVisualSign = "دقیقاً برعکسِ کانَ! اسمِ بلافاصله بعدش منصوب (ـَ / ـً) و خبرش مرفوع (ـٌ) است!",
            rightExample = "إِنَّ اللّهَ عَلِیمٌ (قطعاً خدا داناست) | لَعَلَّ الفَرَجَ قَرِیبٌ (شاید گشایش نزدیک باشد)",
            goldenShortcut = "🔑 رمز قفل کنکور: «کانَ» اولش را بالا می‌برد (ـُ)، «إِنَّ» با چکشِ تشدید روی سرِ کلمه اول می‌کوبد و فتحه (ـَ) می‌دهد!"
        ),
        ArabicAntiConfusionPair(
            title = "۲. ضد قاطی کردن: «جمله وصفیه» در برابر «حال و جمله حالیه» (یازدهم و دوازدهم)",
            leftRuleName = "جمله وصفیه (صفت بعد از اسم نکره - یازدهم)",
            leftVisualSign = "کلمه قبلش «نکره» است (الـ ندارد و تنوین دارد: طالِباً، شَجَرَةٌ) + در ترجمه فارسی حتماً حرف «که» می‌گیرد!",
            leftExample = "رَأَیتُ طالِباً یَقرَأُ = دانش‌آموزی را دیدم «که» می‌خواند (ماضیِ مضارع بعد از کانَ)",
            rightRuleName = "حال و جمله حالیه (بعد از اسم معرفه - دوازدهم)",
            rightVisualSign = "صاحبِ حال «معرفه» است (الـ دارد یا اسم خاص است: الطِّفلُ، المُعَلِّمَ) + چگونگی هنگام انجام کار را می‌گوید («در حالی که»).",
            rightExample = "جاءَ الطِّفلُ باکِیاً (کودک گریان آمد) | رَأَیتُ المُعَلِّمَ وَ هُوَ یَبتَسِمُ (در حالی که لبخند می‌زد)",
            goldenShortcut = "🔑 رمز قفل کنکور: قبل از فعل را نگاه کن! اگر «نکره (بدون الـ)» بود = جمله وصفیه (ترجمه با «که»)؛ اگر «معرفه (با الـ)» بود = حال (ترجمه با «در حالی که»)!"
        ),
        ArabicAntiConfusionPair(
            title = "۳. ضد قاطی کردن ۳ منصوب دوازدهم: «مفعول مطلق» vs «تمییز» vs «حال»",
            leftRuleName = "مفعول مطلق (هم‌ریشه با فعل جمله)",
            leftVisualSign = "حروف اصلیِ اسمِ منصوب (ـً) دقیقاً تکرارِ حروفِ فعلِ همان جمله است! (تأکیدی = تنها، نوعی = با صفت/مضاف‌الیه).",
            leftExample = "إِصبِرْ صَبراً جَمیلاً (صبر کن صبرِ زیبایی = مفعول مطلق نوعی) | نَجَحَ نَجاحاً (قطعاً موفق شد = تأکیدی)",
            rightRuleName = "تمییز (بعد از عدد یا وزن أَفعَل و کَمْ)",
            rightVisualSign = "هم‌ریشه با فعل نیست! بلافاصله بعد از عدد (۱۱ تا ۹۹) یا بعد از أَفعَل (أَکثَر، أَحسَن، أَشَدّ) یا کَمْ می‌آید و ابهام نسبت را رفع می‌کند.",
            rightExample = "أَحسَنُ النّاسِ خُلُقاً (خوش‌اخلاق‌ترین مردم) | عِندی عِشرونَ کِتاباً (۲۰ کتاب دارم)",
            goldenShortcut = "🔑 رمز ۳ ثانیه: ۱) هم‌خانواده با فعل بود؟ -> مفعول مطلق! ۲) بعد از عدد یا أَفعَل بود؟ -> تمییز! ۳) مشتق (بر وزن فاعل/مفعول) و جوابِ «چگونه؟» بود؟ -> حال!"
        ),
        ArabicAntiConfusionPair(
            title = "۴. ضد قاطی کردن: «تَعَلَّمَ / تَکاتَبَ (فعل ماضی)» در برابر «تَعَلُّم / تَکاتُب (مصدر)» (پایه دهم)",
            leftRuleName = "فعل ماضی باب تَفَعُّل و تَفاعُل (فتحه ـَ)",
            leftVisualSign = "حرفِ یکی مانده به آخرش صدای «ـَ (فتحه)» دارد -> حتماً فعلِ زمان گذشته است!",
            leftExample = "تَعَلَّمَ = یاد گرفت (فعل ماضی) | تَعاوَنَ = همکاری کرد (فعل ماضی)",
            rightRuleName = "مصدر باب تَفَعُّل و تَفاعُل (ضمه ـُ)",
            rightVisualSign = "حرفِ یکی مانده به آخرش صدای «ـُ (ضمه)» دارد -> اسم (مصدر) است و در فارسی «... کردن / ... شدن» معنی می‌دهد!",
            rightExample = "تَعَلُّم = یاد گرفتن (مصدر/اسم) | تَعاوُن = همکاری کردن (مصدر/اسم)",
            goldenShortcut = "🔑 رمز قفل کنکور: در تست ترجمه دهم، فقط به حرکت حرفِ قبل از آخر نگاه کن: فتحه (ـَ) = فعل گذشته؛ ضمه (ـُ) = مصدر (اسم)!"
        )
    )

    val roomSighas = all14Sighas.filter { it.roomIndex == selectedRoomIndex }
    val safeIndex = selectedSighaInRoom.coerceIn(0, (roomSighas.size - 1).coerceAtLeast(0))
    val activeSigha = roomSighas[safeIndex]

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(start = 16.dp, top = 16.dp, end = 16.dp, bottom = 88.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        // Zero-to-Master Visual Guide for Thulathi Mujarrad, Mazid, 8 Baabs & 14 Sighas
        item {
            ArabicZeroToMasterVisualGuide(accentColor = SubjectArabicColor)
        }

        // Graphical 14-Sigha Master Table & Tree
        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(22.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
            ) {
                Column(
                    modifier = Modifier.padding(14.dp),
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    Text(
                        text = "📋 جدول گرافیکی کامل ۱۴ صیغه عربی + نمودار درختی (از پایه هفتم تا دوازدهم)",
                        style = MaterialTheme.typography.titleSmall,
                        fontWeight = FontWeight.ExtraBold,
                        color = SubjectArabicColor
                    )
                    com.example.ui.components.Arabic14SighaGraphicalTable(accentColor = SubjectArabicColor)
                    com.example.ui.components.SubjectGraphicalMasterTree(
                        subjectId = "arabic",
                        accentColor = SubjectArabicColor
                    )
                }
            }
        }

        // Invented Tool 1: 3 Rooms of 14 Sighas
        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(22.dp),
                colors = CardDefaults.cardColors(containerColor = SubjectArabicColor.copy(alpha = 0.12f))
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(Icons.Default.Translate, contentDescription = null, tint = SubjectArabicColor)
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "۱. دسته‌بندی ۱۴ صیغه عربی بر اساس ضمایر (غایب، مخاطب، متکلم)",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold,
                            color = SubjectArabicColor
                        )
                    }
                    Text(
                        text = "چرا ۱۴ صیغه را قاطی می‌کنی؟ چون همه را یکجا حفظ می‌کنی! ۱۴ صیغه فقط ۳ اتاق دارد: اول اتاق را انتخاب کن، بعد ضمیر را بزن تا قفلِ سر و دمِ فعل باز شود:",
                        style = MaterialTheme.typography.bodySmall
                    )

                    // Room Selector
                    LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        items(rooms.size) { rIdx ->
                            FilterChip(
                                selected = selectedRoomIndex == rIdx,
                                onClick = {
                                    selectedRoomIndex = rIdx
                                    selectedSighaInRoom = 0
                                },
                                label = { Text(rooms[rIdx], fontWeight = FontWeight.Bold) }
                            )
                        }
                    }

                    // Pronouns inside the selected room
                    FlowRow(
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        roomSighas.forEachIndexed { idx, item ->
                            FilterChip(
                                selected = safeIndex == idx,
                                onClick = { selectedSighaInRoom = idx },
                                label = { Text(item.pronounLabel) }
                            )
                        }
                    }

                    Surface(
                        color = MaterialTheme.colorScheme.surface,
                        shape = RoundedCornerShape(16.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Column(
                            modifier = Modifier.padding(14.dp),
                            verticalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Text(
                                text = "📌 ${activeSigha.sighaNum} — ضمیر: ${activeSigha.pronounLabel}",
                                style = MaterialTheme.typography.labelLarge,
                                color = SubjectArabicColor,
                                fontWeight = FontWeight.Bold
                            )
                            HorizontalDivider()
                            Text(
                                text = "🔹 ماضی (گذشته): ${activeSigha.pastVerb}  ⬅️  «${activeSigha.pastMeaning}»",
                                style = MaterialTheme.typography.titleSmall,
                                fontWeight = FontWeight.Bold,
                                color = SubjectArabicColor
                            )
                            Text(
                                text = "🔑 نشانه چشمی آخرِ ماضی: ${activeSigha.pastCode}",
                                style = MaterialTheme.typography.bodySmall
                            )
                            HorizontalDivider()
                            Text(
                                text = "🔸 مضارع (حال/آینده): ${activeSigha.presentVerb}  ⬅️  «${activeSigha.presentMeaning}»",
                                style = MaterialTheme.typography.titleSmall,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.primary
                            )
                            Text(
                                text = "🔑 نشانه چشمی سر و دمِ مضارع: ${activeSigha.presentCode}",
                                style = MaterialTheme.typography.bodySmall
                            )
                            HorizontalDivider()
                            Text(
                                text = "⚡ امر، نهی و مجهول در کنکور: ${activeSigha.commandAndPassive}",
                                style = MaterialTheme.typography.bodySmall,
                                fontWeight = FontWeight.SemiBold,
                                color = MaterialTheme.colorScheme.secondary
                            )
                        }
                    }
                }
            }
        }

        // Invented Tool 2: Side-by-Side Arabic Rules Anti-Confusion Comparator
        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(22.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Text(
                        text = "۲. دستگاه «ضد قاطی کردن قواعد مشابه عربی» (دهم، یازدهم و دوازدهم)",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = SubjectArabicColor
                    )
                    Text(
                        text = "روی هر جفتِ شبیه به هم بزن تا تفاوت چشمی آن‌ها را کنار هم ببینی و دیگر هرگز سر جلسه کنکور قاطی نکنی:",
                        style = MaterialTheme.typography.bodySmall
                    )

                    Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                        antiConfusionPairs.forEachIndexed { idx, pair ->
                            val selected = selectedComparisonIndex == idx
                            Surface(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clickable { selectedComparisonIndex = idx },
                                shape = RoundedCornerShape(12.dp),
                                color = if (selected) SubjectArabicColor.copy(alpha = 0.16f) else MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.45f)
                            ) {
                                Text(
                                    text = pair.title,
                                    style = MaterialTheme.typography.bodyMedium,
                                    fontWeight = if (selected) FontWeight.Bold else FontWeight.Normal,
                                    color = if (selected) SubjectArabicColor else MaterialTheme.colorScheme.onSurface,
                                    modifier = Modifier.padding(12.dp)
                                )
                            }
                        }
                    }

                    val activePair = antiConfusionPairs[selectedComparisonIndex]
                    Surface(
                        color = SubjectArabicColor.copy(alpha = 0.08f),
                        shape = RoundedCornerShape(16.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Column(
                            modifier = Modifier.padding(14.dp),
                            verticalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Text(
                                text = "🅰️ ${activePair.leftRuleName}",
                                style = MaterialTheme.typography.titleSmall,
                                fontWeight = FontWeight.Bold,
                                color = SubjectArabicColor
                            )
                            Text(
                                text = "• نشانه چشمی: ${activePair.leftVisualSign}\n• مثال: ${activePair.leftExample}",
                                style = MaterialTheme.typography.bodySmall
                            )
                            HorizontalDivider()
                            Text(
                                text = "🅱️ ${activePair.rightRuleName}",
                                style = MaterialTheme.typography.titleSmall,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.primary
                            )
                            Text(
                                text = "• نشانه چشمی: ${activePair.rightVisualSign}\n• مثال: ${activePair.rightExample}",
                                style = MaterialTheme.typography.bodySmall
                            )
                            HorizontalDivider()
                            Text(
                                text = activePair.goldenShortcut,
                                style = MaterialTheme.typography.bodyMedium,
                                fontWeight = FontWeight.Bold,
                                color = SubjectArabicColor
                            )
                        }
                    }
                }
            }
        }
    }
}

private data class EnglishBlankRadarRule(
    val gradeBookBadge: String,
    val clueTitle: String,
    val beforeBlankClue: String,
    val afterBlankClue: String,
    val exactFormulaLtr: String,
    val sampleTestLtr: String,
    val persianTrick: String
)

@OptIn(ExperimentalLayoutApi::class)
@Composable
private fun EnglishGrammarAntiConfusionLabView() {
    var selectedRadarIndex by remember { mutableIntStateOf(0) }

    val radarRules = listOf(
        EnglishBlankRadarRule(
            gradeBookBadge = "پایه هفتم تا نهم + دهم (Vision 1)",
            clueTitle = "۱. فرق ۴ زمان اصلی (حال ساده، حال استمراری، گذشته ساده، آینده)",
            beforeBlankClue = "فاعل مفرد (He/She/It) یا جمع (I/We/They)",
            afterBlankClue = "every day -> V(s) | now/at the moment -> am/is/are + V-ing | yesterday/ago -> V-ed | tomorrow/next -> will + V / be going to + V",
            exactFormulaLtr = "now -> am/is/are + V-ing | yesterday/ago -> V2 (past) | tomorrow -> will + V (simple)",
            sampleTestLtr = "Look! The students [are practicing] English right now, but yesterday they [studied] Arabic.",
            persianTrick = "فقط به کلمه زمان در آخر جمله نگاه کن! اگر yesterday یا ago دیدی، هر گزینه‌ای که am/is/are/have/will دارد را در ۱ ثانیه خط بزن!"
        ),
        EnglishBlankRadarRule(
            gradeBookBadge = "زبان دهم (Vision 1 - Lesson 2 & 3)",
            clueTitle = "۲. ضد قاطی کردن صفت برتر (er / more) و صفت عالی (est / the most)",
            beforeBlankClue = "اگر قبل از جای خالی کلمه «the» دیدی -> صفت عالی (est / most)",
            afterBlankClue = "اگر بعد از جای خالی کلمه «than» دیدی -> صفت تفضیلی (er / more) | اگر «as ... as» دیدی -> صفت ساده بدون هیچ پسوندی!",
            exactFormulaLtr = "... + (-er / more / better / worse) + than | the + (-est / most / best / worst) | as + Adjective + as",
            sampleTestLtr = "Gold is [more expensive] than silver, but health is the [best] gift in life.",
            persianTrick = "قفل تصویری: «than = ـتر (er / more)»، «the = ـترین (est / most)»، «as ... as = صفت لُخت و ساده»!"
        ),
        EnglishBlankRadarRule(
            gradeBookBadge = "زبان یازدهم (Vision 2 - Lesson 2)",
            clueTitle = "۳. ماضی نقلی (have/has + p.p.) در برابر گذشته ساده (since و for)",
            beforeBlankClue = "فاعل مفرد (He/She/Ali) -> has + p.p. | فاعل جمع (I/They) -> have + p.p.",
            afterBlankClue = "در ادامه جمله یکی از کلمات «since / for / already / just / yet / ever / lately / recently» وجود دارد!",
            exactFormulaLtr = "Subject + have / has + Past Participle (V3) + ... + (since + start point / for + duration)",
            sampleTestLtr = "Mr. Karimi [has taught] in this high school since 1395 (for 10 years).",
            persianTrick = "همین که در جمله «since» (از مبدا زمان) یا «for + طول زمان» دیدی، فقط دنبال گزینه «have/has + شکل سوم فعل» بگرد!"
        ),
        EnglishBlankRadarRule(
            gradeBookBadge = "زبان یازدهم (Vision 2 - Lesson 3)",
            clueTitle = "۴. ضد قاطی کردن «V-ing (اسم مصدر)» در برابر «to + V (مصدر با to)»",
            beforeBlankClue = "کلمه دقیقاً قبل از جای خالی را بخوان: حرف اضافه (in/of/at/for/by/without) است یا فعل؟",
            afterBlankClue = "بعد از حروف اضافه و افعال (enjoy, mind, finish, avoid, keep, suggest) -> V-ing | بعد از (want, decide, hope, plan, promise, agree) و بعد از صفت (easy/hard) -> to + V",
            exactFormulaLtr = "Preposition (of/in/by/for) or enjoy/finish/avoid + V-ing | want/decide/plan/hope or Adjective + to + V",
            sampleTestLtr = "Thank you for [helping] me! My brother decided [to study] for Farhangian University.",
            persianTrick = "قانون طلایی: بعد از تمام حروف اضافه (in, on, of, for, about, without, by) و اولِ جمله در نقش فاعل، ۱۰۰٪ فعل با «ing» می‌آید!"
        ),
        EnglishBlankRadarRule(
            gradeBookBadge = "زبان دوازدهم (Vision 3 - Lesson 1)",
            clueTitle = "۵. مجهول (Passive Voice) در برابر معلوم (Active) + دنباله سوالی (Tag Question)",
            beforeBlankClue = "اولِ جمله «شیء یا مفعول» است (مثلاً The book / The bridge / Patients) نه انجام‌دهنده کار!",
            afterBlankClue = "بعد از جای خالی «by + شخص» یا حرف اضافه (in/to/at) آمده است و مفعول ندارد -> حتماً مجهول (be + p.p.) است!",
            exactFormulaLtr = "Object + [am/is/are/was/were/have been/will be + Past Participle (p.p.)] + by ...",
            sampleTestLtr = "Avicenna's famous book [was written] many centuries ago, [wasn't it]?",
            persianTrick = "در مجهول همزمان ۲ قفل باید در گزینه باشد: ۱) یکی از خانواده be (am/is/are/was/were/been/be) + ۲) شکل سوم فعل (p.p.). در دنباله سوالی (Tag Question) هم: مثبت -> منفی، منفی -> مثبت!"
        ),
        EnglishBlankRadarRule(
            gradeBookBadge = "زبان دوازدهم (Vision 3 - Lesson 2)",
            clueTitle = "۶. ضد قاطی کردن ضمایر موصولی: «who / whom / which / that»",
            beforeBlankClue = "کلمه دقیقاً قبل از جای خالی چیست؟ انسان (the man / student) یا غیرانسان (the book / car)؟",
            afterBlankClue = "کلمه دقیقاً بعد از جای خالی چیست؟ فعل (is / works / bought) یا ضمیر فاعلی (I / you / we / he)؟",
            exactFormulaLtr = "Human + [who] + Verb | Human + [whom] + Subject (I/we/he...) | Thing/Animal + [which] + Verb/Subject",
            sampleTestLtr = "The teacher [who] teaches us Arabic is very kind. The doctor [whom] we met yesterday was my uncle.",
            persianTrick = "فرمول ۲ ثانیه بدون ترجمه: ۱) غیرانسان + هرچی = which | ۲) انسان + فعل = who | ۳) انسان + ضمیر/اسم (I, you, Ali) = whom!"
        ),
        EnglishBlankRadarRule(
            gradeBookBadge = "زبان یازدهم و دوازدهم (Vision 2 & Vision 3)",
            clueTitle = "۷. ضد قاطی کردن شرطی نوع ۱ (واقعی) و شرطی نوع ۲ (خیالی) + ماضی بعید (had p.p.)",
            beforeBlankClue = "در جمله If کدام زمان آمده؟ و در جملات زمان گذشته آیا before / after / because داریم؟",
            afterBlankClue = "شرطی ۱: If + حال ساده -> will + V | شرطی ۲: If + گذشته ساده (یا were) -> would/could + V | ماضی بعید: before + گذشته ساده -> had + p.p.",
            exactFormulaLtr = "Type 1: If + Present -> will + V | Type 2: If + Past (were) -> would + V | Past Perfect: had + p.p. + before + Past",
            sampleTestLtr = "If I [were] you, I [would start] from zero today! Ali [had practiced] a lot before he [took] the exam.",
            persianTrick = "ترازوی هماهنگی زمان: در شرطی ۱ «حال با will» ست می‌شود؛ در شرطی ۲ «گذشته (were) با would» ست می‌شود (در شرطی ۲ برای I/he/she هم همیشه were می‌آید!)."
        )
    )

    val activeRule = radarRules[selectedRadarIndex]

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(start = 16.dp, top = 16.dp, end = 16.dp, bottom = 88.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        // Zero-to-Master Visual Guide for English Relative Pronouns & All Core Grammar
        item {
            EnglishZeroToMasterVisualGuide(accentColor = SubjectEnglishColor)
        }

        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(22.dp),
                colors = CardDefaults.cardColors(containerColor = SubjectEnglishColor.copy(alpha = 0.12f))
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    Text(
                        text = "راهنمای تشخیص سریع قواعد گرامر زبان انگلیسی (هفتم تا دوازدهم)",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = SubjectEnglishColor
                    )
                    Text(
                        text = "اگر معنی جملات انگلیسی را اصلاً نمی‌فهمی، غصه نخور! در تست‌های گرامر کنکور فقط کافی است به یک کلمه «قبل از جای خالی» و یک کلمه «بعد از جای خالی» نگاه کنی. مبحث را انتخاب کن:",
                        style = MaterialTheme.typography.bodySmall
                    )

                    Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                        radarRules.forEachIndexed { idx, rule ->
                            val selected = selectedRadarIndex == idx
                            Surface(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clickable { selectedRadarIndex = idx },
                                shape = RoundedCornerShape(12.dp),
                                color = if (selected) SubjectEnglishColor.copy(alpha = 0.18f) else MaterialTheme.colorScheme.surface
                            ) {
                                Column(modifier = Modifier.padding(12.dp)) {
                                    Text(
                                        text = rule.gradeBookBadge,
                                        style = MaterialTheme.typography.labelSmall,
                                        color = SubjectEnglishColor
                                    )
                                    Text(
                                        text = rule.clueTitle,
                                        style = MaterialTheme.typography.bodyMedium,
                                        fontWeight = if (selected) FontWeight.Bold else FontWeight.Normal
                                    )
                                }
                            }
                        }
                    }

                    Surface(
                        color = MaterialTheme.colorScheme.surface,
                        shape = RoundedCornerShape(16.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Column(
                            modifier = Modifier.padding(14.dp),
                            verticalArrangement = Arrangement.spacedBy(10.dp)
                        ) {
                            Text(
                                text = "🔍 ۱. قبل از جای خالی چه می‌بینی؟\n${activeRule.beforeBlankClue}",
                                style = MaterialTheme.typography.bodyMedium
                            )
                            Text(
                                text = "🔍 ۲. بعد از جای خالی چه می‌بینی؟\n${activeRule.afterBlankClue}",
                                style = MaterialTheme.typography.bodyMedium
                            )
                            HorizontalDivider()
                            Text(
                                text = "📐 فرمول چپ‌به‌راست گرامر و نمونه تست:",
                                style = MaterialTheme.typography.labelLarge,
                                color = SubjectEnglishColor,
                                fontWeight = FontWeight.Bold
                            )
                            SmartDiagramAndFormulaBlock(
                                rawText = "${activeRule.exactFormulaLtr}\n${activeRule.sampleTestLtr}",
                                accentColor = SubjectEnglishColor
                            )
                            Surface(
                                color = SubjectEnglishColor.copy(alpha = 0.1f),
                                shape = RoundedCornerShape(12.dp),
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Text(
                                    text = "🔑 رمز ضد قاطی کردن: ${activeRule.persianTrick}",
                                    style = MaterialTheme.typography.bodyMedium,
                                    fontWeight = FontWeight.SemiBold,
                                    color = SubjectEnglishColor,
                                    modifier = Modifier.padding(12.dp)
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
private fun FarhangianScoreSimulatorView() {
    var finalExamGpa by remember { mutableFloatStateOf(18.0f) } // out of 20
    var mathPercent by remember { mutableFloatStateOf(35f) }
    var fonoonPercent by remember { mutableFloatStateOf(60f) }
    var arabicPercent by remember { mutableFloatStateOf(55f) }
    var humanitiesOtherPercent by remember { mutableFloatStateOf(70f) }
    var farhangianBookletPercent by remember { mutableFloatStateOf(80f) }

    // Weighted estimate for Konkoor Ensani & Farhangian 1406
    val gpaScore = ((finalExamGpa - 10f).coerceAtLeast(0f) / 10f) * 10000f
    val konkoorScore = (
        (mathPercent * 2f + fonoonPercent * 3f + arabicPercent * 2f + humanitiesOtherPercent * 5f) / 12f
        ) * 105f + 2500f
    val farhangianScore = farhangianBookletPercent * 100f + 2200f
    val totalWeightedTaraz = (gpaScore * 0.50f + konkoorScore * 0.25f + farhangianScore * 0.25f).toInt()

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(start = 16.dp, top = 16.dp, end = 16.dp, bottom = 88.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(22.dp),
                colors = CardDefaults.cardColors(
                    containerColor = SubjectFarhangianColor.copy(alpha = 0.14f)
                )
            ) {
                Column(
                    modifier = Modifier.padding(18.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(Icons.Default.School, contentDescription = null, tint = SubjectFarhangianColor)
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "شبیه‌ساز تراز و شانس قبولی فرهنگیان ۱۴۰۶",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold,
                            color = SubjectFarhangianColor
                        )
                    }
                    Spacer(modifier = Modifier.height(10.dp))
                    Text(
                        text = BidiFormatter.formatMixedPersianText("تراز کل تخمینی شما: $totalWeightedTaraz"),
                        style = MaterialTheme.typography.headlineMedium,
                        fontWeight = FontWeight.Bold
                    )
                    val statusText = when {
                        totalWeightedTaraz >= 8200 -> "🌟 عالی! شانس بسیار بالا برای قبولی در دبیری ادبیات، عربی، علوم اجتماعی و آموزش ابتدایی شهرهای برتر!"
                        totalWeightedTaraz >= 7200 -> "✅ خوب! دعوت قطعی به مصاحبه فرهنگیان؛ با تقویت دفترچه فرهنگیان و فنون قبولی نهایی را تضمین کن."
                        else -> "📈 در ابتدای مسیر هستی! با رساندن دفترچه فرهنگیان به بالای ۷۵٪ و فنون به ۵۰٪ ترازت بیش از ۱۵۰۰ واحد جهش می‌کند!"
                    }
                    Spacer(modifier = Modifier.height(6.dp))
                    Text(
                        text = BidiFormatter.formatMixedPersianText(statusText),
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            }
        }

        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    SliderRow(
                        label = "معدل کتبی امتحانات نهایی (تاثیر ۵۰٪): ${"%.1f".format(finalExamGpa)} از ۲۰",
                        value = finalExamGpa,
                        valueRange = 10f..20f,
                        onValueChange = { finalExamGpa = it }
                    )
                    SliderRow(
                        label = "درصد ریاضی و آمار (ضریب ۲): ${mathPercent.toInt()}٪",
                        value = mathPercent,
                        valueRange = 0f..100f,
                        onValueChange = { mathPercent = it }
                    )
                    SliderRow(
                        label = "درصد علوم و فنون و عروض (ضریب ۳): ${fonoonPercent.toInt()}٪",
                        value = fonoonPercent,
                        valueRange = 0f..100f,
                        onValueChange = { fonoonPercent = it }
                    )
                    SliderRow(
                        label = "درصد عربی اختصاصی انسانی (ضریب ۲): ${arabicPercent.toInt()}٪",
                        value = arabicPercent,
                        valueRange = 0f..100f,
                        onValueChange = { arabicPercent = it }
                    )
                    SliderRow(
                        label = "میانگین اقتصاد، روانشناسی، جامعه‌شناسی و فلسفه: ${humanitiesOtherPercent.toInt()}٪",
                        value = humanitiesOtherPercent,
                        valueRange = 0f..100f,
                        onValueChange = { humanitiesOtherPercent = it }
                    )
                    SliderRow(
                        label = "دفترچه اختصاصی فرهنگیان (قرائتی + هوش معلمی): ${farhangianBookletPercent.toInt()}٪",
                        value = farhangianBookletPercent,
                        valueRange = 0f..100f,
                        onValueChange = { farhangianBookletPercent = it }
                    )
                }
            }
        }
    }
}

@Composable
private fun SliderRow(
    label: String,
    value: Float,
    valueRange: ClosedFloatingPointRange<Float>,
    onValueChange: (Float) -> Unit
) {
    Column {
        Text(
            text = BidiFormatter.formatMixedPersianText(label),
            style = MaterialTheme.typography.bodyMedium,
            fontWeight = FontWeight.SemiBold
        )
        Slider(
            value = value,
            onValueChange = onValueChange,
            valueRange = valueRange
        )
    }
}

private data class SighaVisualLock(
    val pronounLabel: String,
    val pastVerb: String,
    val pastCode: String,
    val pastMeaning: String,
    val presentVerb: String,
    val presentCode: String,
    val presentMeaning: String
)

@OptIn(ExperimentalLayoutApi::class)
@Composable
private fun PhilosophyAndLogicSquareLabView() {
    var selectedPropIndex by remember { mutableIntStateOf(0) }
    var isAssumedTrue by remember { mutableStateOf(true) }

    val propositions = listOf(
        "موجبه کلی (م.ک): هر الف ب است",
        "سالبه کلی (س.ک): هیچ الف ب نیست",
        "موجبه جزئی (م.ج): بعضی الف ب است",
        "سالبه جزئی (س.ج): بعضی الف ب نیست"
    )

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(start = 16.dp, top = 16.dp, end = 16.dp, bottom = 88.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(22.dp),
                colors = CardDefaults.cardColors(
                    containerColor = SubjectPhilosophyColor.copy(alpha = 0.12f)
                )
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    Text(
                        text = "ماشین هوشمند «مربع تقابل و عکس مستوی» (منطق دهم)",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = SubjectPhilosophyColor
                    )
                    Text(
                        text = "هر قضیه و ارزش آن (صادق یا کاذب) را انتخاب کن تا ماشین منطق بلافاصله وضعیت ۳ قضیه دیگر و عکس مستوی را با متد «ضربدر و سقف» نشان دهد:",
                        style = MaterialTheme.typography.bodySmall
                    )

                    FlowRow(
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        propositions.forEachIndexed { idx, label ->
                            FilterChip(
                                selected = selectedPropIndex == idx,
                                onClick = { selectedPropIndex = idx },
                                label = { Text(label) }
                            )
                        }
                    }

                    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        FilterChip(
                            selected = isAssumedTrue,
                            onClick = { isAssumedTrue = true },
                            label = { Text("فرض: صادق (درست ✅)") }
                        )
                        FilterChip(
                            selected = !isAssumedTrue,
                            onClick = { isAssumedTrue = false },
                            label = { Text("فرض: کاذب (غلط ❌)") }
                        )
                    }

                    val analysisText = remember(selectedPropIndex, isAssumedTrue) {
                        when (selectedPropIndex to isAssumedTrue) {
                            0 to true -> """
                                • قضیه مفروض: «هر الف ب است» (م.ک) = صادق ✅
                                • تناقض (ضربدر -> س.ج: بعضی الف ب نیست): ۱۰۰٪ کاذب ❌
                                • تضاد (سقف -> س.ک: هیچ الف ب نیست): ۱۰۰٪ کاذب ❌
                                • تداخل (زیرش -> م.ج: بعضی الف ب است): ۱۰۰٪ صادق ✅
                                • عکس مستوی: «بعضی ب الف است» (م.ج) = صادق ✅
                            """.trimIndent()

                            0 to false -> """
                                • قضیه مفروض: «هر الف ب است» (م.ک) = کاذب ❌
                                • تناقض (ضربدر -> س.ج: بعضی الف ب نیست): ۱۰۰٪ صادق ✅
                                • تضاد (سقف -> س.ک: هیچ الف ب نیست): نامعلوم ❓ (شاید بعضی الف‌ها ب باشند!)
                                • تداخل (زیرش -> م.ج: بعضی الف ب است): نامعلوم ❓
                                • عکس مستوی: «بعضی ب الف است» (م.ج)
                            """.trimIndent()

                            1 to true -> """
                                • قضیه مفروض: «هیچ الف ب نیست» (س.ک) = صادق ✅
                                • تناقض (ضربدر -> م.ج: بعضی الف ب است): ۱۰۰٪ کاذب ❌
                                • تضاد (سقف -> م.ک: هر الف ب است): ۱۰۰٪ کاذب ❌
                                • تداخل (زیرش -> س.ج: بعضی الف ب نیست): ۱۰۰٪ صادق ✅
                                • عکس مستوی: «هیچ ب الف نیست» (س.ک) = صادق ✅
                            """.trimIndent()

                            1 to false -> """
                                • قضیه مفروض: «هیچ الف ب نیست» (س.ک) = کاذب ❌
                                • تناقض (ضربدر -> م.ج: بعضی الف ب است): ۱۰۰٪ صادق ✅
                                • تضاد (سقف -> م.ک: هر الف ب است): نامعلوم ❓
                                • تداخل (زیرش -> س.ج: بعضی الف ب نیست): نامعلوم ❓
                                • عکس مستوی: «هیچ ب الف نیست» (س.ک)
                            """.trimIndent()

                            2 to true -> """
                                • قضیه مفروض: «بعضی الف ب است» (م.ج) = صادق ✅
                                • تناقض (ضربدر -> س.ک: هیچ الف ب نیست): ۱۰۰٪ کاذب ❌
                                • تحت تضاد (کف -> س.ج: بعضی الف ب نیست): نامعلوم ❓
                                • تداخل (بالایش -> م.ک: هر الف ب است): نامعلوم ❓
                                • عکس مستوی: «بعضی ب الف است» (م.ج) = صادق ✅
                            """.trimIndent()

                            2 to false -> """
                                • قضیه مفروض: «بعضی الف ب است» (م.ج) = کاذب ❌
                                • تناقض (ضربدر -> س.ک: هیچ الف ب نیست): ۱۰۰٪ صادق ✅
                                • تحت تضاد (کف -> س.ج: بعضی الف ب نیست): ۱۰۰٪ صادق ✅
                                • تداخل (بالایش -> م.ک: هر الف ب است): ۱۰۰٪ کاذب ❌
                                • عکس مستوی: «بعضی ب الف است» (م.ج)
                            """.trimIndent()

                            3 to true -> """
                                • قضیه مفروض: «بعضی الف ب نیست» (س.ج) = صادق ✅
                                • تناقض (ضربدر -> م.ک: هر الف ب است): ۱۰۰٪ کاذب ❌
                                • تحت تضاد (کف -> م.ج: بعضی الف ب است): نامعلوم ❓
                                • تداخل (بالایش -> س.ک: هیچ الف ب نیست): نامعلوم ❓
                                • عکس مستوی: ⚠️ سالبه جزئی (س.ج) اصلاً عکس مستوی معتبر ندارد!
                            """.trimIndent()

                            else -> """
                                • قضیه مفروض: «بعضی الف ب نیست» (س.ج) = کاذب ❌
                                • تناقض (ضربدر -> م.ک: هر الف ب است): ۱۰۰٪ صادق ✅
                                • تحت تضاد (کف -> م.ج: بعضی الف ب است): ۱۰۰٪ صادق ✅
                                • تداخل (بالایش -> س.ک: هیچ الف ب نیست): ۱۰۰٪ کاذب ❌
                                • عکس مستوی: ⚠️ سالبه جزئی (س.ج) عکس مستوی ندارد!
                            """.trimIndent()
                        }
                    }

                    Surface(
                        color = MaterialTheme.colorScheme.surface,
                        shape = RoundedCornerShape(16.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(
                            text = BidiFormatter.formatMixedPersianText(analysisText),
                            style = MaterialTheme.typography.bodyMedium,
                            modifier = Modifier.padding(14.dp)
                        )
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
private fun MnemonicAcronymVaultLabView() {
    var selectedSubjectFilter by remember { mutableStateOf("all") }
    var searchQuery by remember { mutableStateOf("") }
    val persianDigitsMode = BidiFormatter.usePersianDigits

    val totalMnemonicsCount = BismillahAndMnemonicsCurriculum.allMnemonicCodes.size
    val filterOptions = listOf(
        "all" to "همه دروس ($totalMnemonicsCount رمز طلایی)",
        "math" to "ریاضی (پـ تـ ضـ ج)",
        "arabic" to "عربی (بِـلِـکَـمِـنْ / کَصامَل)",
        "english" to "زبان انگلیسی (قواعد to + V و V-ing)",
        "arooz" to "عروض (کد ۴ هجایی)",
        "fonoon" to "فنون (رُفَفَم / سَمَحَع / صَکَبَع)",
        "philosophy" to "فلسفه و منطق (طالاب / صَقَبَع)",
        "history_geo" to "تاریخ و جغرافیا (قرن / ساعت / اقلیم)",
        "social_psych_econ" to "اقتصاد، روان‌شناسی و جامعه‌شناسی",
        "farhangian" to "فرهنگیان (اعـاجـز / قـحـب)"
    )

    val filteredCodes = remember(selectedSubjectFilter, searchQuery, persianDigitsMode) {
        BismillahAndMnemonicsCurriculum.allMnemonicCodes.filter { item ->
            val matchesSubject = selectedSubjectFilter == "all" || item.subjectId == selectedSubjectFilter
            val q = searchQuery.trim()
            val matchesQuery = q.isEmpty() ||
                item.acronymCode.contains(q, ignoreCase = true) ||
                item.topicTitle.contains(q, ignoreCase = true) ||
                item.decodedMeaning.contains(q, ignoreCase = true)
            matchesSubject && matchesQuery
        }
    }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .testTag("mnemonic_vault_lab"),
        contentPadding = PaddingValues(start = 16.dp, top = 16.dp, end = 16.dp, bottom = 88.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        item {
            Card(
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.65f)
                ),
                shape = RoundedCornerShape(22.dp)
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text(
                        text = BidiFormatter.formatMixedPersianText(
                            "🔐 بانک جامع رمزها، مخفف‌های حفظی و تکنیک‌های تست‌زنی کنکور"
                        ),
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onPrimaryContainer
                    )
                    Text(
                        text = BidiFormatter.formatMixedPersianText(
                            "تمام قواعد فرّار عربی، گرامر انگلیسی، فرمول‌های ریاضی، بحرهای عروض، تاریخ ادبیات و فیلسوفان را با یک «کلمه رمزی مخفف» در ۱۰ ثانیه حفظ کن تا دیگر هرگز سر جلسه قاطی نکنی!"
                        ),
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onPrimaryContainer
                    )

                    OutlinedTextField(
                        value = searchQuery,
                        onValueChange = { searchQuery = it },
                        label = { Text("جستجوی رمز یا مبحث (مثلاً: کصامل، افعال، سبک، فیلسوف...)") },
                        singleLine = true,
                        modifier = Modifier.fillMaxWidth()
                    )

                    FlowRow(
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        verticalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        filterOptions.forEach { (id, label) ->
                            FilterChip(
                                selected = selectedSubjectFilter == id,
                                onClick = { selectedSubjectFilter = id },
                                label = {
                                    Text(
                                        BidiFormatter.formatMixedPersianText(label),
                                        style = MaterialTheme.typography.labelMedium
                                    )
                                }
                            )
                        }
                    }
                }
            }
        }

        items(filteredCodes, key = { it.id }) { codeItem ->
            val subjectMeta = SubjectCatalog.getSubjectById(codeItem.subjectId)
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surface
                ),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Surface(
                            color = subjectMeta.color.copy(alpha = 0.15f),
                            shape = RoundedCornerShape(10.dp)
                        ) {
                            Text(
                                text = BidiFormatter.formatMixedPersianText(codeItem.badge),
                                style = MaterialTheme.typography.labelMedium,
                                fontWeight = FontWeight.Bold,
                                color = subjectMeta.color,
                                modifier = Modifier.padding(horizontal = 10.dp, vertical = 5.dp)
                            )
                        }
                        Text(
                            text = BidiFormatter.formatMixedPersianText(subjectMeta.title.substringBefore(" (")),
                            style = MaterialTheme.typography.labelSmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }

                    Surface(
                        color = subjectMeta.color.copy(alpha = 0.10f),
                        shape = RoundedCornerShape(14.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Column(
                            modifier = Modifier.padding(12.dp),
                            verticalArrangement = Arrangement.spacedBy(4.dp)
                        ) {
                            Text(
                                text = BidiFormatter.formatMixedPersianText(codeItem.acronymCode),
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.ExtraBold,
                                color = subjectMeta.color
                            )
                            Text(
                                text = BidiFormatter.formatMixedPersianText(codeItem.topicTitle),
                                style = MaterialTheme.typography.bodyMedium,
                                fontWeight = FontWeight.SemiBold
                            )
                        }
                    }

                    SmartDiagramAndFormulaBlock(
                        rawText = BidiFormatter.formatMixedPersianText(codeItem.decodedMeaning),
                        accentColor = subjectMeta.color
                    )

                    Surface(
                        color = MaterialTheme.colorScheme.tertiaryContainer.copy(alpha = 0.55f),
                        shape = RoundedCornerShape(12.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(
                            text = BidiFormatter.formatMixedPersianText(
                                "⚡ شگرد ۱ ثانیه‌ای در تست و نهایی: ${codeItem.instantTestApplication}"
                            ),
                            style = MaterialTheme.typography.bodySmall,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onTertiaryContainer,
                            modifier = Modifier.padding(12.dp)
                        )
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
private fun HumanitiesComprehensiveSuperLabView() {
    var totalRevenueMillion by remember { mutableFloatStateOf(120f) }
    var explicitCostMillion by remember { mutableFloatStateOf(50f) }
    var implicitCostMillion by remember { mutableFloatStateOf(30f) }

    var historicalYear by remember { mutableIntStateOf(1285) }
    var lonDiffDegrees by remember { mutableIntStateOf(30) }
    var selectedPsychScenario by remember { mutableIntStateOf(0) }
    var selectedSocApproach by remember { mutableIntStateOf(0) }

    val accountingProfit = (totalRevenueMillion - explicitCostMillion).toInt()
    val economicProfit = (accountingProfit - implicitCostMillion).toInt()
    val calculatedCentury = ((historicalYear - 1) / 100) + 1
    val timeDiffMinutes = lonDiffDegrees * 4
    val timeDiffHours = timeDiffMinutes / 60
    val timeDiffRemMins = timeDiffMinutes % 60

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .testTag("humanities_super_lab"),
        contentPadding = PaddingValues(start = 16.dp, top = 16.dp, end = 16.dp, bottom = 88.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // 1. Economics Profit Simulator (Konkoor 1406 Guaranteed Question)
        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(22.dp),
                colors = CardDefaults.cardColors(
                    containerColor = Color(0xFFBE185D).copy(alpha = 0.10f)
                )
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    Text(
                        text = BidiFormatter.formatMixedPersianText("💰 ۱. ماشین حل مسائل اقتصاد دهم (سود حسابداری و سود ویژه اقتصادی)"),
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.ExtraBold,
                        color = Color(0xFFBE185D)
                    )
                    Text(
                        text = BidiFormatter.formatMixedPersianText(
                            "در کنکور انسانی حداقل ۳ تست مسأله از اقتصاد می‌آید! اسلایدرها را حرکت بده تا تفاوت هزینه آشکار (فاکتورهای پرداختی) و هزینه پنهان (فرصت از دست رفته مثل حقوق قبلی و اجاره مغازه شخصی) را ببینی:"
                        ),
                        style = MaterialTheme.typography.bodySmall
                    )

                    SliderRow(
                        label = "کل درآمد فروش سالانه: ${totalRevenueMillion.toInt()} میلیون تومان",
                        value = totalRevenueMillion,
                        valueRange = 20f..300f,
                        onValueChange = { totalRevenueMillion = it }
                    )
                    SliderRow(
                        label = "هزینه‌های آشکار (مواد اولیه، قبض، دستمزد کارگر): ${explicitCostMillion.toInt()} میلیون تومان",
                        value = explicitCostMillion,
                        valueRange = 10f..200f,
                        onValueChange = { explicitCostMillion = it }
                    )
                    SliderRow(
                        label = "هزینه‌های پنهان / فرصت (حقوق شغل قبلی + اجاره ملک شخصی): ${implicitCostMillion.toInt()} میلیون تومان",
                        value = implicitCostMillion,
                        valueRange = 0f..150f,
                        onValueChange = { implicitCostMillion = it }
                    )

                    Surface(
                        color = MaterialTheme.colorScheme.surface,
                        shape = RoundedCornerShape(16.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Column(
                            modifier = Modifier.padding(14.dp),
                            verticalArrangement = Arrangement.spacedBy(6.dp)
                        ) {
                            Text(
                                text = BidiFormatter.formatMixedPersianText(
                                    "📊 گام ۱ (سود حسابداری) = درآمد کل (${totalRevenueMillion.toInt()}) − هزینه آشکار (${explicitCostMillion.toInt()}) = $accountingProfit میلیون تومان"
                                ),
                                style = MaterialTheme.typography.bodyMedium,
                                fontWeight = FontWeight.Bold
                            )
                            Text(
                                text = BidiFormatter.formatMixedPersianText(
                                    "💎 گام ۲ (سود ویژه اقتصادی) = سود حسابداری ($accountingProfit) − هزینه پنهان (${implicitCostMillion.toInt()}) = $economicProfit میلیون تومان"
                                ),
                                style = MaterialTheme.typography.bodyMedium,
                                fontWeight = FontWeight.ExtraBold,
                                color = if (economicProfit >= 0) Color(0xFF059669) else Color(0xFFDC2626)
                            )
                            Text(
                                text = BidiFormatter.formatMixedPersianText(
                                    if (economicProfit > 0) "✅ حکم اقتصادی: چون سود اقتصادی مثبت است ($economicProfit میلیون)، ادامه این کسب‌وکار کاملاً به‌صرفه است!"
                                    else if (economicProfit == 0) "⚖️ حکم اقتصادی: سود اقتصادی صفر است (سود عادی؛ فرقی با شغل قبلی ندارد)."
                                    else "⚠️ حکم اقتصادی: هرچند حسابدار می‌گوید $accountingProfit میلیون سود کرده‌ای، اما در واقع ${-economicProfit} میلیون تومان ضرر اقتصادی کرده‌ای و شغل قبلی‌ات به‌صرفه‌تر بود!"
                                ),
                                style = MaterialTheme.typography.bodySmall,
                                fontWeight = FontWeight.SemiBold
                            )
                        }
                    }
                }
            }
        }

        // 2. Psychology Interference & Piaget Detector
        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(22.dp),
                colors = CardDefaults.cardColors(
                    containerColor = Color(0xFF7C3AED).copy(alpha = 0.10f)
                )
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    Text(
                        text = BidiFormatter.formatMixedPersianText("🧠 ۲. رادار ضد قاطی کردن روان‌شناسی یازدهم (تداخل حافظه و مراحل پیاژه)"),
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.ExtraBold,
                        color = Color(0xFF7C3AED)
                    )
                    val scenarios = listOf(
                        "تداخل پیش‌گستر (قدیم -> جدید)",
                        "تداخل پس‌گستر (جدید -> قدیم)",
                        "پیاژه: حسّی-حرکتی (۰ تا ۲ سال)",
                        "پیاژه: پیش‌عملیاتی (۲ تا ۷ سال)",
                        "پیاژه: عملیاتی عینی (۷ تا ۱۱ سال)"
                    )
                    FlowRow(
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        verticalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        scenarios.forEachIndexed { idx, title ->
                            FilterChip(
                                selected = selectedPsychScenario == idx,
                                onClick = { selectedPsychScenario = idx },
                                label = { Text(BidiFormatter.formatMixedPersianText(title)) }
                            )
                        }
                    }

                    val psychExplanation = when (selectedPsychScenario) {
                        0 -> "🎯 رمز ۱ ثانیه‌ای «تداخل پیش‌گستر»: به کلمه دوم بعد از «مطلبِ ... را یادم رفت» نگاه کن! اگر «مطلبِ جدید را یادم رفت» (مثلاً در سال ۱۴۰۵ اشتباهاً هنوز سال ۱۴۰۴ را می‌نویسی یا شماره خط جدیدت را به خاطر خط قبلی یادت نمی‌آید) = تداخل پیش‌گستر (قدیمی جلو آمده و جلوی جدید را گرفته)."
                        1 -> "🎯 رمز ۱ ثانیه‌ای «تداخل پس‌گستر»: اگر «مطلبِ قدیم را یادم رفت» (مثلاً بعد از یاد گرفتن زبان فرانسه، لغات انگلیسی قبلی‌ات یا رمز کارت قبلی‌ات یادت رفته است) = تداخل پس‌گستر (جدید برگشته به عقب و قدیمی را پاک کرده)."
                        2 -> "👶 مرحله ۱ پیاژه (حسّی-حرکتی: تولد تا ۲ سالگی): مهم‌ترین دستاورد: «پایداری شیء» (وقتی اسباب‌بازی را زیر پتو قایم می‌کنی، در ۴ ماهگی فکر می‌کند نابود شده، اما در ۱۲ ماهگی پتو را کنار می‌زند چون فهمیده شیء هنوز وجود دارد!)."
                        3 -> "🧸 مرحله ۲ پیاژه (پیش‌عملیاتی: ۲ تا ۷ سالگی): ۳ علامت قطعی در تست: ۱) جاندارپنداری (عروسکم سردشه، خورشید خوابیده) ۲) خودمحوری (فکر می‌کند همه از زاویه دید او می‌بینند) ۳) عدم نگهداری ذهنی (فکر می‌کند آب در لیوان باریک و بلند بیشتر از لیوان پهن است!)."
                        else -> "📐 مرحله ۳ پیاژه (عملیاتی عینی: ۷ تا ۱۱ سالگی / دبستان): کودک «نگهداری ذهنی» (مقدار، وزن، حجم) و «بازگشت‌پذیری ذهنی» و «طبقه‌بندی» را کامل می‌فهمد، اما هنوز برای مفاهیم انتزاعی محض (مثل عدالت و فلسفه) نیاز به شکل و مثال ملموس دارد."
                    }

                    Surface(
                        color = MaterialTheme.colorScheme.surface,
                        shape = RoundedCornerShape(16.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(
                            text = BidiFormatter.formatMixedPersianText(psychExplanation),
                            style = MaterialTheme.typography.bodyMedium,
                            modifier = Modifier.padding(14.dp)
                        )
                    }
                }
            }
        }

        // 3. Sociology 3-Approach Radar
        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(22.dp),
                colors = CardDefaults.cardColors(
                    containerColor = Color(0xFF059669).copy(alpha = 0.10f)
                )
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    Text(
                        text = BidiFormatter.formatMixedPersianText("🏛️ ۳. رادار ۳ رویکرد جامعه‌شناسی ۳ دوازدهم (تبیینی، تفسیری، انتقادی)"),
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.ExtraBold,
                        color = Color(0xFF059669)
                    )
                    val approaches = listOf(
                        "۱. تبیینی / پوزیتیویستی (کنت و دورکیم)",
                        "۲. تفسیری / معناگرا (ماکس وبر)",
                        "۳. انتقادی (کارل مارکس)"
                    )
                    FlowRow(
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        verticalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        approaches.forEachIndexed { idx, title ->
                            FilterChip(
                                selected = selectedSocApproach == idx,
                                onClick = { selectedSocApproach = idx },
                                label = { Text(BidiFormatter.formatMixedPersianText(title)) }
                            )
                        }
                    }

                    val socDetails = when (selectedSocApproach) {
                        0 -> """
                            • کلیدواژه ۱ ثانیه‌ای در تست: «قوانین علّی»، «مشاهده و آزمایش حسی»، «آمار و ارقام کمّی»، «شباهت جامعه به طبیعت»
                            • هدف علم: کشف قوانین جهان‌شمول برای «پیش‌بینی و کنترل» جامعه
                            • نگاه به ارزش‌ها: داوری ارزشی (خوب و بد اخلاقی) را غیرعلمی می‌داند (جدایی علم از ارزش).
                        """.trimIndent()
                        1 -> """
                            • کلیدواژه ۱ ثانیه‌ای در تست: «فهم عمیق»، «معنای ذهنی کنشگران»، «همدلی»، «تفاوت جهان اجتماعی با طبیعت»
                            • هدف علم: «فهم و تفسیر» معانی، نمادها و نیت‌های درونی کنش انسان‌ها
                            • نگاه به ارزش‌ها: ارزش‌های فرهنگی را مطالعه و توصیف می‌کند اما از صدور حکم ارزشی عام خودداری می‌کند (نسبی‌گرایی).
                        """.trimIndent()
                        else -> """
                            • کلیدواژه ۱ ثانیه‌ای در تست: «رهایی و آزادی»، «کشف سلطه و استثمار»، «آگاهی‌بخشی به فرودستان»، «تغییر وضع موجود»
                            • هدف علم: نقد ساختارهای ظالمانه و «تغییر جامعه» به سوی عدالت
                            • نگاه به ارزش‌ها: علم و ارزش را درهم‌تنیده می‌داند و صریحاً از ارزش عدالت و رهایی دفاع می‌کند.
                        """.trimIndent()
                    }

                    Surface(
                        color = MaterialTheme.colorScheme.surface,
                        shape = RoundedCornerShape(16.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(
                            text = BidiFormatter.formatMixedPersianText(socDetails),
                            style = MaterialTheme.typography.bodyMedium,
                            modifier = Modifier.padding(14.dp)
                        )
                    }
                }
            }
        }

        // 4. History Century & Geography Time-Zone Calculator
        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(22.dp),
                colors = CardDefaults.cardColors(
                    containerColor = Color(0xFFB45309).copy(alpha = 0.10f)
                )
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    Text(
                        text = BidiFormatter.formatMixedPersianText("🌍 ۴. ماشین تبدیل سال به قرن (تاریخ) و اختلاف ساعت دو نصف‌النهار (جغرافیا)"),
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.ExtraBold,
                        color = Color(0xFFB45309)
                    )
                    SliderRow(
                        label = "انتخاب سال تاریخی (مثلاً امضای فرمان مشروطه ۱۲۸۵ ش): سال $historicalYear",
                        value = historicalYear.toFloat(),
                        valueRange = 100f..1406f,
                        onValueChange = { historicalYear = it.toInt() }
                    )
                    SliderRow(
                        label = "اختلاف طول جغرافیایی دو شهر: $lonDiffDegrees درجه",
                        value = lonDiffDegrees.toFloat(),
                        valueRange = 1f..90f,
                        onValueChange = { lonDiffDegrees = it.toInt() }
                    )

                    Surface(
                        color = MaterialTheme.colorScheme.surface,
                        shape = RoundedCornerShape(16.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Column(
                            modifier = Modifier.padding(14.dp),
                            verticalArrangement = Arrangement.spacedBy(6.dp)
                        ) {
                            Text(
                                text = BidiFormatter.formatMixedPersianText(
                                    "⏳ محاسبه قرن برای سال $historicalYear: دو رقم سمت راست را خط می‌زنیم و ۱ واحد به بقیه اضافه می‌کنیم -> قرن $calculatedCentury (نیمه ${if ((historicalYear % 100) in 1..50) "اول" else "دوم"} قرن $calculatedCentury)"
                                ),
                                style = MaterialTheme.typography.bodyMedium,
                                fontWeight = FontWeight.Bold
                            )
                            Text(
                                text = BidiFormatter.formatMixedPersianText(
                                    "🧭 اختلاف ساعت برای $lonDiffDegrees درجه طول جغرافیایی: هر ۱ درجه = ۴ دقیقه -> $lonDiffDegrees × ۴ = $timeDiffMinutes دقیقه (معادل $timeDiffHours ساعت و $timeDiffRemMins دقیقه؛ شهر شرقی‌تر جلوتر است)"
                                ),
                                style = MaterialTheme.typography.bodyMedium,
                                fontWeight = FontWeight.Bold,
                                color = Color(0xFFB45309)
                            )
                        }
                    }
                }
            }
        }
    }
}


