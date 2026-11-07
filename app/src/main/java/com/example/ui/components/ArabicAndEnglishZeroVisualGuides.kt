package com.example.ui.components

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Lightbulb
import androidx.compose.material.icons.filled.Language
import androidx.compose.material.icons.filled.Translate
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

private data class ArabicBaabVisualModel(
    val number: Int,
    val baabName: String,
    val persianAnchorWord: String,
    val meaningEffect: String,
    val oneSecondVisualSign: String,
    val maaziBlocks: List<Pair<String, Boolean>>, // Pair(text, isExtraLetter)
    val mudareBlocks: List<Pair<String, Boolean>>,
    val amrBlocks: List<Pair<String, Boolean>>,
    val masdarBlocks: List<Pair<String, Boolean>>,
    val maaziExample: String,
    val mudareExample: String,
    val amrExample: String,
    val masdarExample: String,
    val konkoorTrapAlert: String
)

private val eightArabicBaabs = listOf(
    ArabicBaabVisualModel(
        number = 1,
        baabName = "۱. باب إِفْعال",
        persianAnchorWord = "رمز فارسی: «اِخراج / اِرسال / اِکرام»",
        meaningEffect = "معنی فعل را «متعدی» می‌کند؛ یعنی کار را روی کسی دیگر انجام می‌دهی (خَرَجَ = بیرون رفت ⬅️ أَخْرَجَ = بیرون انداخت / اخراج کرد!).",
        oneSecondVisualSign = "اولِ ماضی «أَ» دارد | اولِ مضارع «یُـ» (با ضمه ـُ) دارد | اولِ مصدر «إِ» و قبل از آخرش «ا» دارد.",
        maaziBlocks = listOf("أَ (اضافه)" to true, "خْ (ف)" to false, "رَ (ع)" to false, "جَ (ل)" to false),
        mudareBlocks = listOf("یُـ (مضارع)" to true, "خْ (ف)" to false, "رِ (ع)" to false, "جُ (ل)" to false),
        amrBlocks = listOf("أَ (اضافه)" to true, "خْ (ف)" to false, "رِ (ع)" to false, "جْ (ل)" to false),
        masdarBlocks = listOf("إِ (اضافه)" to true, "خْ (ف)" to false, "ر (ع)" to false, "ا (اضافه)" to true, "ج (ل)" to false),
        maaziExample = "أَخْرَجَ (اخراج کرد)",
        mudareExample = "یُخْرِجُ (اخراج می‌کند)",
        amrExample = "أَخْرِجْ (اخراج کن!)",
        masdarExample = "إِخْراج (اخراج کردن)",
        konkoorTrapAlert = "🔴 تله کنکور: تنها باب در کل عربی که فعل امر آن با «أَ» (همزه مفتوح) شروع می‌شود همین باب إفعال است (أَخْرِجْ / أَکْرِمْ)! همچنین اگر اولِ فعل مضارع «یُـ» دیدی که تشدید و الف نداشت، شک نکن باب إفعال است!"
    ),
    ArabicBaabVisualModel(
        number = 2,
        baabName = "۲. باب تَفْعیل",
        persianAnchorWord = "رمز فارسی: «تَعلیم / تَشکیل / تَخریب»",
        meaningEffect = "کار را روی کسی دیگر انجام می‌دهد یا شدت می‌دهد (عَلِمَ = دانست ⬅️ عَلَّمَ = یاد داد / تعلیم داد!).",
        oneSecondVisualSign = "در ماضی و مضارع و امر، حرفِ وسطِ ریشه «تشدید (ّ)» دارد و اولِ ماضی «ت» ندارد! مصدرش با «تَـ» شروع شده و قبل از آخرش «ی» دارد.",
        maaziBlocks = listOf("عَ (ف)" to false, "لَّ (ع + تشدید اضافه)" to true, "مَ (ل)" to false),
        mudareBlocks = listOf("یُـ (مضارع)" to true, "عَ (ف)" to false, "لِّ (ع + تشدید)" to true, "مُ (ل)" to false),
        amrBlocks = listOf("عَ (ف)" to false, "لِّ (ع + تشدید)" to true, "مْ (ل)" to false),
        masdarBlocks = listOf("تَـ (اضافه)" to true, "عْ (ف)" to false, "ل (ع)" to false, "ی (اضافه)" to true, "م (ل)" to false),
        maaziExample = "عَلَّمَ (یاد داد)",
        mudareExample = "یُعَلِّمُ (یاد می‌دهد)",
        amrExample = "عَلِّمْ (یاد بده!)",
        masdarExample = "تَعْلیم (یاد دادن)",
        konkoorTrapAlert = "🔴 تله کنکور: خیلی‌ها فکر می‌کنند چون اسم این باب «تفعیل» است، ماضی‌اش هم اولش «ت» دارد! خیر! ماضیِ تفعیل فقط ۳ حرف است که وسطش تشدید دارد (عَلَّمَ، قَدَّمَ، کَبَّرَ). فقط مصدرش (تَعْلیم) اولش «تَـ» دارد!"
    ),
    ArabicBaabVisualModel(
        number = 3,
        baabName = "۳. باب مُفاعَلَة",
        persianAnchorWord = "رمز فارسی: «مُسابِقه / مُبارِزه / مُشاهِده»",
        meaningEffect = "نشان‌دهنده کارِ دوطرفه بین دو نفر یا بیشتر است (قاتَلَ = با او جنگید، کاتَبَ = با او نامه‌نگاری کرد).",
        oneSecondVisualSign = "بعد از حرف اولِ ریشه یک «الف (ا)» اضافه دارد (شاهَدَ / یُشاهِدُ) و مصدرش با «مُـ» شروع و به «ة» ختم می‌شود.",
        maaziBlocks = listOf("ش (ف)" to false, "ا (اضافه)" to true, "هَـ (ع)" to false, "دَ (ل)" to false),
        mudareBlocks = listOf("یُـ (مضارع)" to true, "ش (ف)" to false, "ا (اضافه)" to true, "هِـ (ع)" to false, "دُ (ل)" to false),
        amrBlocks = listOf("ش (ف)" to false, "ا (اضافه)" to true, "هِـ (ع)" to false, "دْ (ل)" to false),
        masdarBlocks = listOf("مُـ (اضافه)" to true, "ش (ف)" to false, "ا (اضافه)" to true, "هَـ (ع)" to false, "دَ (ل)" to false, "ة (اضافه)" to true),
        maaziExample = "شاهَدَ (مشاهده کرد)",
        mudareExample = "یُشاهِدُ (مشاهده می‌کند)",
        amrExample = "شاهِدْ (مشاهده کن!)",
        masdarExample = "مُشاهَدَة / شِهاد (مشاهده کردن)",
        konkoorTrapAlert = "🔴 تله کنکور: باب مفاعلة دو تا قالب مصدر دارد: یکی «مُفاعَلَة» (مثل مُجاهَدَة، مُقاتَلَة) و یکی «فِعال» (مثل جِهاد، قِتال، دِفاع). هر دو مصدر باب مفاعلة هستند!"
    ),
    ArabicBaabVisualModel(
        number = 4,
        baabName = "۴. باب تَفَعُّل",
        persianAnchorWord = "رمز فارسی: «تَشَکُّر / تَفَکُّر / تَعَلُّم»",
        meaningEffect = "یعنی پذیرفتنِ اثر کار (عَلَّمَ = یاد داد ⬅️ تَعَلَّمَ = خودش یاد گرفت!).",
        oneSecondVisualSign = "هم اولش «تَـ» اضافه دارد و هم حرف وسطِ ریشه «تشدید (ّ)» دارد! در ماضی و مضارع و امر و مصدر هر دو نشانه (ت + تشدید) حضور دارند!",
        maaziBlocks = listOf("تَـ (اضافه)" to true, "عَ (ف)" to false, "لَّ (ع + تشدید)" to true, "مَ (ل)" to false),
        mudareBlocks = listOf("یَـ (مضارع)" to true, "تَـ (اضافه)" to true, "عَ (ف)" to false, "لَّ (ع + تشدید)" to true, "مُ (ل)" to false),
        amrBlocks = listOf("تَـ (اضافه)" to true, "عَ (ف)" to false, "لَّ (ع + تشدید)" to true, "مْ (ل)" to false),
        masdarBlocks = listOf("تَـ (اضافه)" to true, "عَ (ف)" to false, "لُّ (ع + تشدید و ضمه!)" to true, "م (ل)" to false),
        maaziExample = "تَعَلَّمَ (یاد گرفت)",
        mudareExample = "یَتَعَلَّمُ (یاد می‌گیرد)",
        amrExample = "تَعَلَّمْ (یاد بگیر!)",
        masdarExample = "تَعَلُّم (یاد گرفتن)",
        konkoorTrapAlert = "🔴 تله بسیار پرتکرار کنکور: فرق ماضی (تَعَلَّمَ) و مصدر (تَعَلُّم) فقط در حرکتِ روی تشدید است! اگر روی تشدید فتحه (ـَ) بود فعل ماضی است؛ اگر روی تشدید ضمه (ـُ) بود یا تنوین/الـ داشت، مصدر (اسم) است!"
    ),
    ArabicBaabVisualModel(
        number = 5,
        baabName = "۵. باب تَفاعُل",
        persianAnchorWord = "رمز فارسی: «تَصادُف / تَظاهُر / تَعاوُن»",
        meaningEffect = "کارِ دوطرفه و دسته‌جمعی یا وانمود کردن (تَعاوَنَ = با هم همکاری کردند، تَمارَضَ = خودش را به مریضی زد).",
        oneSecondVisualSign = "هم اولش «تَـ» اضافه دارد و هم بعد از حرف اولِ ریشه یک «الف (ا)» دارد!",
        maaziBlocks = listOf("تَـ (اضافه)" to true, "ک (ف)" to false, "ا (اضافه)" to true, "تَ (ع)" to false, "بَ (ل)" to false),
        mudareBlocks = listOf("یَـ (مضارع)" to true, "تَـ (اضافه)" to true, "ک (ف)" to false, "ا (اضافه)" to true, "تَ (ع)" to false, "بُ (ل)" to false),
        amrBlocks = listOf("تَـ (اضافه)" to true, "ک (ف)" to false, "ا (اضافه)" to true, "تَ (ع)" to false, "بْ (ل)" to false),
        masdarBlocks = listOf("تَـ (اضافه)" to true, "ک (ف)" to false, "ا (اضافه)" to true, "تُ (ع + ضمه!)" to false, "ب (ل)" to false),
        maaziExample = "تَکاتَبَ (با هم مکاتبه کرد)",
        mudareExample = "یَتَکاتَبُ (با هم مکاتبه می‌کند)",
        amrExample = "تَکاتَبْ (مکاتبه کن!)",
        masdarExample = "تَکاتُب (مکاتبه کردن)",
        konkoorTrapAlert = "🔴 تله کنکور: در اینجا هم فرق ماضی (تَظاهَرَ - با فتحه) و مصدر (تَظاهُر - با ضمه) فقط در حرکتِ حرفِ یکی مانده به آخر است!"
    ),
    ArabicBaabVisualModel(
        number = 6,
        baabName = "۶. باب اِفْتِعال",
        persianAnchorWord = "رمز فارسی: «اِحتِرام / اِمتِحان / اِعتِقاد»",
        meaningEffect = "تلاش و کوشش برای انجام کار یا پذیرفتن اثر کار (اِکْتَسَبَ = کسب کرد، اِجْتَمَعَ = گرد هم آمدند).",
        oneSecondVisualSign = "اولش «اِ» دارد و بلافاصله بعد از حرفِ اولِ ریشه (ف)، یک حرف «ت» اضافه قایم شده است!",
        maaziBlocks = listOf("اِ (اضافه)" to true, "حْ (ف)" to false, "تَ (اضافه)" to true, "رَ (ع)" to false, "مَ (ل)" to false),
        mudareBlocks = listOf("یَـ (مضارع)" to true, "حْ (ف)" to false, "تَ (اضافه)" to true, "رِ (ع)" to false, "مُ (ل)" to false),
        amrBlocks = listOf("اِ (اضافه)" to true, "حْ (ف)" to false, "تَ (اضافه)" to true, "رِ (ع)" to false, "مْ (ل)" to false),
        masdarBlocks = listOf("اِ (اضافه)" to true, "حْ (ف)" to false, "تِ (اضافه)" to true, "ر (ع)" to false, "ا (اضافه)" to true, "م (ل)" to false),
        maaziExample = "اِحْتَرَمَ (احترام گذاشت)",
        mudareExample = "یَحْتَرِمُ (احترام می‌گذارد)",
        amrExample = "اِحْتَرِمْ (احترام بگذار!)",
        masdarExample = "اِحْتِرام (احترام گذاشتن)",
        konkoorTrapAlert = "🔴 تله کنکور: مراقب باش ریشه‌هایی که حرف اولشان «ت» است (مثل تَرَکَ) تو را گول نزنند! در باب افتعال، «ت» حرفِ سومِ کلمه است (اِ + حرف اول ریشه + ت + حرف دوم + حرف سوم = اِحْتَرَمَ از ریشه ح-ر-م)."
    ),
    ArabicBaabVisualModel(
        number = 7,
        baabName = "۷. باب اِنْفِعال",
        persianAnchorWord = "رمز فارسی: «اِنفِجار / اِنقِلاب / اِنصِراف»",
        meaningEffect = "همیشه فعل را «لازم» می‌کند و در فارسی با «شد / می‌شود» ترجمه می‌شود (کَسَرَ = شکست ⬅️ اِنْکَسَرَ = شکسته شد!).",
        oneSecondVisualSign = "اولش دو حرف چسبیدهٔ «اِنْـ» (در ماضی، امر و مصدر) یا «یَنْـ» (در مضارع) دارد!",
        maaziBlocks = listOf("اِنْـ (اضافه)" to true, "کَ (ف)" to false, "سَ (ع)" to false, "رَ (ل)" to false),
        mudareBlocks = listOf("یَـ (مضارع)" to true, "نْـ (اضافه)" to true, "کَ (ف)" to false, "سِ (ع)" to false, "رُ (ل)" to false),
        amrBlocks = listOf("اِنْـ (اضافه)" to true, "کَ (ف)" to false, "سِ (ع)" to false, "رْ (ل)" to false),
        masdarBlocks = listOf("اِنْـ (اضافه)" to true, "کِ (ف)" to false, "س (ع)" to false, "ا (اضافه)" to true, "ر (ل)" to false),
        maaziExample = "اِنْکَسَرَ (شکسته شد)",
        mudareExample = "یَنْکَسِرُ (شکسته می‌شود)",
        amrExample = "اِنْکَسِرْ (شکسته شو!)",
        masdarExample = "اِنْکِسار (شکسته شدن)",
        konkoorTrapAlert = "🔴 نکته طلایی ترجمه کنکور: فعل‌های باب انفعال (اِنْکَسَرَ، اِنْقَلَبَ، اِنْصَرَفَ، اِنْفَجَرَ) اگرچه ظاهرشان معلوم است، اما در فارسی مثل مجهول («شکسته شد»، «منقلب شد»، «منصرف شد») ترجمه می‌شوند!"
    ),
    ArabicBaabVisualModel(
        number = 8,
        baabName = "۸. باب اِسْتِفْعال",
        persianAnchorWord = "رمز فارسی: «اِستِخدام / اِستِغفار / اِستِخراج»",
        meaningEffect = "به معنی «طلب کردن و خواستن» است (غَفَرَ = بخشید ⬅️ اِسْتَغْفَرَ = طلب بخشش کرد!).",
        oneSecondVisualSign = "اولش ۳ حرفِ بسیار واضحِ «اِسْتَـ / یَسْتَـ / اِسْتِـ» دارد! ساده‌ترین باب برای تشخیص در ۱ ثانیه!",
        maaziBlocks = listOf("اِسْتَـ (اضافه)" to true, "خْ (ف)" to false, "رَ (ع)" to false, "جَ (ل)" to false),
        mudareBlocks = listOf("یَـ (مضارع)" to true, "سْتَـ (اضافه)" to true, "خْ (ف)" to false, "رِ (ع)" to false, "جُ (ل)" to false),
        amrBlocks = listOf("اِسْتَـ (اضافه)" to true, "خْ (ف)" to false, "رِ (ع)" to false, "جْ (ل)" to false),
        masdarBlocks = listOf("اِسْتِـ (اضافه)" to true, "خْ (ف)" to false, "ر (ع)" to false, "ا (اضافه)" to true, "ج (ل)" to false),
        maaziExample = "اِسْتَخْرَجَ (استخراج کرد)",
        mudareExample = "یَسْتَخْرِجُ (استخراج می‌کند)",
        amrExample = "اِسْتَخْرِجْ (استخراج کن!)",
        masdarExample = "اِسْتِخْراج (استخراج کردن)",
        konkoorTrapAlert = "🔴 تله کنکور: در کلماتی مثل «اِسْتَمَعَ / یَسْتَمِعُ / اِسْتِماع» (از ریشه س-م-ع) یا «اِسْتَلَمَ» (از ریشه س-ل-م)، چون خودِ حرف اولِ ریشه «س» است، این کلمات باب «افتعال» هستند نه استفعال! بشمار بعد از «استـ» چند حرف مانده: اگر فقط ۲ حرف مانده بود (م-ع)، باب افتعال است؛ اگر ۳ حرف مانده بود (خ-ر-ج)، باب استفعال است!"
    )
)

/**
 * راهنمای تصویری و تعاملی عربی از زیرِ صفر مطلق:
 * ویژه دانش‌آموزانی که هیچ پیش‌زمینه‌ای از «ثلاثی مجرد»، «ثلاثی مزید»، «۸ باب» و «۱۴ صیغه» ندارند.
 * این کامپوننت منحصراً در زیرمجموعه درس عربی نمایش داده می‌شود تا هیچ پراکندگی در برنامه ایجاد نشود.
 */
@OptIn(ExperimentalLayoutApi::class)
@Composable
fun ArabicZeroToMasterVisualGuide(
    accentColor: Color = Color(0xFF047857),
    modifier: Modifier = Modifier
) {
    var selectedTab by remember { mutableIntStateOf(0) }
    var selectedBaabIdx by remember { mutableIntStateOf(0) }

    Surface(
        modifier = modifier
            .fillMaxWidth()
            .testTag("arabic_zero_to_master_visual_guide"),
        shape = RoundedCornerShape(16.dp),
        color = MaterialTheme.colorScheme.surface,
        border = BorderStroke(1.2.dp, accentColor.copy(alpha = 0.45f)),
        tonalElevation = 1.dp
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(14.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            // Header
            Row(verticalAlignment = Alignment.CenterVertically) {
                Surface(
                    color = accentColor.copy(alpha = 0.14f),
                    shape = CircleShape,
                    modifier = Modifier.size(38.dp)
                ) {
                    Box(contentAlignment = Alignment.Center) {
                        Icon(
                            imageVector = Icons.Default.Translate,
                            contentDescription = null,
                            tint = accentColor,
                            modifier = Modifier.size(20.dp)
                        )
                    }
                }
                Spacer(modifier = Modifier.width(10.dp))
                Column(modifier = Modifier.weight(1f)) {
                    BiDiText(
                        text = "🕌 آموزش تصویری عربی از زیرِ صفر مطلق (باب‌های ثلاثی مزید و ۱۴ صیغه با شکل)",
                        style = MaterialTheme.typography.titleSmall,
                        fontWeight = FontWeight.Black,
                        color = accentColor
                    )
                    BiDiText(
                        text = "بدون اصطلاحات سخت! یادگیری مفهومی و شکلی با کلمات روزمره فارسی در ۴ پله",
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }

            // 4 Clean Step Tabs inside Arabic Guide
            val tabs = listOf(
                "۱. اصلاً ثلاثی و باب یعنی چی؟",
                "۲. شکل رنگی ۸ باب مزید",
                "۳. تشخیص ۳ ثانیه‌ای ماضی/مضارع/مصدر",
                "۴. ۱۴ صیغه با ۵ انگشت"
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .horizontalScroll(rememberScrollState()),
                horizontalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                tabs.forEachIndexed { idx, label ->
                    val isSelected = selectedTab == idx
                    Surface(
                        modifier = Modifier
                            .clip(RoundedCornerShape(10.dp))
                            .clickable { selectedTab = idx }
                            .testTag("arabic_zero_guide_tab_$idx"),
                        shape = RoundedCornerShape(10.dp),
                        color = if (isSelected) accentColor else accentColor.copy(alpha = 0.08f),
                        border = BorderStroke(0.8.dp, if (isSelected) accentColor else accentColor.copy(alpha = 0.3f))
                    ) {
                        Text(
                            text = label,
                            style = MaterialTheme.typography.labelMedium,
                            fontWeight = FontWeight.Bold,
                            color = if (isSelected) Color.White else accentColor,
                            modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp)
                        )
                    }
                }
            }

            when (selectedTab) {
                0 -> {
                    // TAB 0: What do "Thulathi", "Mujarrad", "Mazid", and "Baab" even mean?!
                    Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                        Surface(
                            shape = RoundedCornerShape(12.dp),
                            color = accentColor.copy(alpha = 0.07f),
                            border = BorderStroke(0.8.dp, accentColor.copy(alpha = 0.3f)),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Column(
                                modifier = Modifier.padding(12.dp),
                                verticalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                BiDiText(
                                    text = "💡 چرا اسم «ثلاثی مجرد و مزید» ترسناک به نظر می‌رسد؟ بیا به زبان سادهٔ خودمان ترجمه‌اش کنیم:",
                                    style = MaterialTheme.typography.titleSmall,
                                    fontWeight = FontWeight.Black,
                                    color = accentColor
                                )
                                BiDiText(
                                    text = "۱) «ثلاثی» یعنی «۳ حرفی»! در عربی ۹۹٪ کلمات از ۳ تا آجرِ اصلی ساخته شده‌اند. علمای عربی برای اینکه به این ۳ آجر یک اسم بدهند، به آجر اول می‌گویند «ف»، به آجر دوم «ع» و به آجر سوم «ل» (ف - ع - ل).\n" +
                                        "۲) «مُجَرَّد» یعنی «لُخت و تنها»! وقتی فعل فقط و فقط همان ۳ آجر اصلی را دارد و هیچ حرف اضافه‌ای به آن نچسبیده است (مثل: خَرَجَ = بیرون رفت، عَلِمَ = دانست).\n" +
                                        "۳) «مَزید» یعنی «اضافه‌شده»! وقتی این ۳ آجر اصلی را می‌ریزیم داخلِ یکی از ۸ قالب آماده و ۱ یا ۲ یا ۳ حرفِ اضافه (قرمز رنگ) به آن می‌چسبانیم تا معنی‌اش عوض شود!\n" +
                                        "۴) «باب» یعنی همان «قالب کیک‌پزی»! ما در کل عربی فقط ۸ تا قالب (۸ باب) داریم که اتفاقاً هر ۸ تای آن‌ها را در فارسی روزمره خودمان به کار می‌بریم!",
                                    style = MaterialTheme.typography.bodySmall,
                                    color = MaterialTheme.colorScheme.onSurface
                                )
                            }
                        }

                        // Visual Comparison Box: Bare 3 bricks vs Molded bricks
                        Surface(
                            shape = RoundedCornerShape(12.dp),
                            color = MaterialTheme.colorScheme.surfaceContainerLow,
                            border = BorderStroke(1.dp, accentColor.copy(alpha = 0.35f)),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Column(
                                modifier = Modifier.padding(12.dp),
                                verticalArrangement = Arrangement.spacedBy(10.dp)
                            ) {
                                BiDiText(
                                    text = "🧱 شکل مقایسه‌ای: ۳ آجر اصلی (خ - ر - ج) چطور وارد قالب‌ها می‌شوند؟",
                                    style = MaterialTheme.typography.labelLarge,
                                    fontWeight = FontWeight.Black,
                                    color = accentColor
                                )

                                // Row 1: Mujarrad
                                Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                                    BiDiText(
                                        text = "حالت اول: ثلاثی مجرد (فقط ۳ حرف اصلی سبز — بدون هیچ حرف اضافه):",
                                        style = MaterialTheme.typography.labelSmall,
                                        fontWeight = FontWeight.Bold
                                    )
                                    Row(
                                        horizontalArrangement = Arrangement.spacedBy(6.dp),
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        listOf("خَ (حرف ۱)", "رَ (حرف ۲)", "جَ (حرف ۳)").forEach { brick ->
                                            Surface(
                                                color = Color(0xFF059669).copy(alpha = 0.15f),
                                                border = BorderStroke(1.dp, Color(0xFF059669)),
                                                shape = RoundedCornerShape(8.dp)
                                            ) {
                                                Text(
                                                    text = brick,
                                                    style = MaterialTheme.typography.labelMedium,
                                                    fontWeight = FontWeight.Bold,
                                                    color = Color(0xFF059669),
                                                    modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp)
                                                )
                                            }
                                        }
                                        Text("⬅️ خَرَجَ (بیرون رفت)", style = MaterialTheme.typography.labelMedium, fontWeight = FontWeight.Black)
                                    }
                                }

                                HorizontalDivider(thickness = 0.6.dp)

                                // Row 2: Mazid Baab Efaal
                                Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                                    BiDiText(
                                        text = "حالت دوم: ثلاثی مزید — قالب «إفعال» (۱ حرفِ اضافهٔ قرمز «أَ» به اولش چسبید):",
                                        style = MaterialTheme.typography.labelSmall,
                                        fontWeight = FontWeight.Bold
                                    )
                                    FlowRow(
                                        horizontalArrangement = Arrangement.spacedBy(6.dp),
                                        verticalArrangement = Arrangement.spacedBy(6.dp)
                                    ) {
                                        Surface(
                                            color = Color(0xFFDC2626),
                                            shape = RoundedCornerShape(8.dp)
                                        ) {
                                            Text(
                                                text = "+ أَ (اضافه)",
                                                style = MaterialTheme.typography.labelMedium,
                                                fontWeight = FontWeight.Bold,
                                                color = Color.White,
                                                modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp)
                                            )
                                        }
                                        listOf("خْ (۱)", "رَ (۲)", "جَ (۳)").forEach { brick ->
                                            Surface(
                                                color = Color(0xFF059669).copy(alpha = 0.15f),
                                                border = BorderStroke(1.dp, Color(0xFF059669)),
                                                shape = RoundedCornerShape(8.dp)
                                            ) {
                                                Text(
                                                    text = brick,
                                                    style = MaterialTheme.typography.labelMedium,
                                                    fontWeight = FontWeight.Bold,
                                                    color = Color(0xFF059669),
                                                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 6.dp)
                                                )
                                            }
                                        }
                                        Text(
                                            text = "⬅️ أَخْرَجَ (اخراج کرد / بیرون راند!)",
                                            style = MaterialTheme.typography.labelMedium,
                                            fontWeight = FontWeight.Black,
                                            color = Color(0xFFDC2626),
                                            modifier = Modifier.align(Alignment.CenterVertically)
                                        )
                                    }
                                }

                                HorizontalDivider(thickness = 0.6.dp)

                                // Row 3: Mazid Baab Estefaal
                                Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                                    BiDiText(
                                        text = "حالت سوم: ثلاثی مزید — قالب «استفعال» (۳ حرفِ اضافهٔ قرمز «اِسْتَـ» به اولش چسبید):",
                                        style = MaterialTheme.typography.labelSmall,
                                        fontWeight = FontWeight.Bold
                                    )
                                    FlowRow(
                                        horizontalArrangement = Arrangement.spacedBy(6.dp),
                                        verticalArrangement = Arrangement.spacedBy(6.dp)
                                    ) {
                                        Surface(
                                            color = Color(0xFFDC2626),
                                            shape = RoundedCornerShape(8.dp)
                                        ) {
                                            Text(
                                                text = "+ اِسْتَـ (اضافه)",
                                                style = MaterialTheme.typography.labelMedium,
                                                fontWeight = FontWeight.Bold,
                                                color = Color.White,
                                                modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp)
                                            )
                                        }
                                        listOf("خْ (۱)", "رَ (۲)", "جَ (۳)").forEach { brick ->
                                            Surface(
                                                color = Color(0xFF059669).copy(alpha = 0.15f),
                                                border = BorderStroke(1.dp, Color(0xFF059669)),
                                                shape = RoundedCornerShape(8.dp)
                                            ) {
                                                Text(
                                                    text = brick,
                                                    style = MaterialTheme.typography.labelMedium,
                                                    fontWeight = FontWeight.Bold,
                                                    color = Color(0xFF059669),
                                                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 6.dp)
                                                )
                                            }
                                        }
                                        Text(
                                            text = "⬅️ اِسْتَخْرَجَ (استخراج کرد!)",
                                            style = MaterialTheme.typography.labelMedium,
                                            fontWeight = FontWeight.Black,
                                            color = Color(0xFFDC2626),
                                            modifier = Modifier.align(Alignment.CenterVertically)
                                        )
                                    }
                                }
                            }
                        }

                        SmartDiagramAndFormulaBlock(
                            rawText = "نام قالب (باب) | کلمه رمزی در فارسی روزمره | نشانهٔ چشمی در ۱ ثانیه\n" +
                                "۱. باب إِفْعال | اِخراج / اِرسال | ماضی با «أَ» (أَخْرَجَ) و مضارع با «یُـ» (یُخْرِجُ)\n" +
                                "۲. باب تَفْعیل | تَعلیم / تَشکیل | حرف وسط تشدید دارد بدون ت در اول (عَلَّمَ / یُعَلِّمُ)\n" +
                                "۳. باب مُفاعَلَة | مُسابِقه / مُبارِزه | بعد از حرف اول «ا» دارد (شاهَدَ / یُشاهِدُ / مُشاهَدَة)\n" +
                                "۴. باب تَفَعُّل | تَشَکُّر / تَفَکُّر | اولش «تَـ» + وسطش تشدید دارد (تَعَلَّمَ / یَتَعَلَّمُ / تَعَلُّم)\n" +
                                "۵. باب تَفاعُل | تَصادُف / تَظاهُر | اولش «تَـ» + بعد از حرف اول «ا» دارد (تَکاتَبَ / تَکاتُب)\n" +
                                "۶. باب اِفْتِعال | اِحتِرام / اِمتِحان | بعد از حرف اولِ ریشه یک «ت» اضافه قایم شده (اِحْتَرَمَ / یَحْتَرِمُ)\n" +
                                "۷. باب اِنْفِعال | اِنفِجار / اِنقِلاب | اولش «اِنْـ / یَنْـ» دارد و معنی «شدن» می‌دهد (اِنْکَسَرَ = شکسته شد)\n" +
                                "۸. باب اِسْتِفْعال | اِستِخدام / اِستِخراج | اولش «اِسْتَـ / یَسْتَـ / اِسْتِـ» دارد (اِسْتَغْفَرَ)",
                            accentColor = accentColor
                        )
                    }
                }

                1 -> {
                    // TAB 1: Interactive 8 Baabs Color-Coded Brick Explorer
                    val baab = eightArabicBaabs[selectedBaabIdx.coerceIn(eightArabicBaabs.indices)]
                    Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                        Text(
                            text = "👇 روی هر کدام از ۸ باب زیر بزن تا آجرهای رنگی (سبز = ۳ حرف اصلی، قرمز = حروف اضافه) را ببینی:",
                            style = MaterialTheme.typography.labelSmall,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                        FlowRow(
                            horizontalArrangement = Arrangement.spacedBy(6.dp),
                            verticalArrangement = Arrangement.spacedBy(6.dp)
                        ) {
                            eightArabicBaabs.forEachIndexed { idx, item ->
                                val selected = idx == selectedBaabIdx
                                Surface(
                                    modifier = Modifier
                                        .clip(RoundedCornerShape(10.dp))
                                        .clickable { selectedBaabIdx = idx }
                                        .testTag("arabic_baab_chip_$idx"),
                                    shape = RoundedCornerShape(10.dp),
                                    color = if (selected) accentColor else MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.6f),
                                    border = BorderStroke(0.8.dp, if (selected) accentColor else MaterialTheme.colorScheme.outlineVariant)
                                ) {
                                    Text(
                                        text = item.baabName,
                                        style = MaterialTheme.typography.labelMedium,
                                        fontWeight = FontWeight.Bold,
                                        color = if (selected) Color.White else MaterialTheme.colorScheme.onSurface,
                                        modifier = Modifier.padding(horizontal = 10.dp, vertical = 7.dp)
                                    )
                                }
                            }
                        }

                        AnimatedContent(
                            targetState = baab,
                            transitionSpec = {
                                (slideInHorizontally(tween(240)) { it / 5 } + fadeIn(tween(240))) togetherWith
                                    (slideOutHorizontally(tween(200)) { -it / 5 } + fadeOut(tween(200)))
                            },
                            label = "baab_visual_anim"
                        ) { b ->
                            Surface(
                                shape = RoundedCornerShape(14.dp),
                                color = accentColor.copy(alpha = 0.06f),
                                border = BorderStroke(1.dp, accentColor.copy(alpha = 0.4f)),
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Column(
                                    modifier = Modifier.padding(12.dp),
                                    verticalArrangement = Arrangement.spacedBy(9.dp)
                                ) {
                                    Row(
                                        modifier = Modifier.fillMaxWidth(),
                                        horizontalArrangement = Arrangement.SpaceBetween,
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        BiDiText(
                                            text = "${b.baabName} (${b.persianAnchorWord})",
                                            style = MaterialTheme.typography.titleSmall,
                                            fontWeight = FontWeight.Black,
                                            color = accentColor,
                                            modifier = Modifier.weight(1f)
                                        )
                                    }

                                    BiDiText(
                                        text = "🎯 نشانه چشمی ۱ ثانیه‌ای: ${b.oneSecondVisualSign}",
                                        style = MaterialTheme.typography.bodySmall,
                                        fontWeight = FontWeight.Bold,
                                        color = MaterialTheme.colorScheme.onSurface
                                    )
                                    BiDiText(
                                        text = "💡 تغییر معنی در این قالب: ${b.meaningEffect}",
                                        style = MaterialTheme.typography.bodySmall,
                                        color = MaterialTheme.colorScheme.onSurfaceVariant
                                    )

                                    // 4 Rows of Colored Bricks: Maazi, Mudare, Amr, Masdar
                                    val fourForms = listOf(
                                        Triple("۱. ماضی (گذشته)", b.maaziBlocks, b.maaziExample),
                                        Triple("۲. مضارع (حال)", b.mudareBlocks, b.mudareExample),
                                        Triple("۳. امر (فرمان)", b.amrBlocks, b.amrExample),
                                        Triple("۴. مصدر (اسم)", b.masdarBlocks, b.masdarExample)
                                    )
                                    fourForms.forEach { (formLabel, bricks, exampleText) ->
                                        Surface(
                                            shape = RoundedCornerShape(10.dp),
                                            color = MaterialTheme.colorScheme.surface,
                                            border = BorderStroke(0.6.dp, accentColor.copy(alpha = 0.25f)),
                                            modifier = Modifier.fillMaxWidth()
                                        ) {
                                            Column(
                                                modifier = Modifier.padding(9.dp),
                                                verticalArrangement = Arrangement.spacedBy(6.dp)
                                            ) {
                                                Row(
                                                    modifier = Modifier.fillMaxWidth(),
                                                    horizontalArrangement = Arrangement.SpaceBetween,
                                                    verticalAlignment = Alignment.CenterVertically
                                                ) {
                                                    Text(
                                                        text = formLabel,
                                                        style = MaterialTheme.typography.labelMedium,
                                                        fontWeight = FontWeight.Black,
                                                        color = accentColor
                                                    )
                                                    Text(
                                                        text = exampleText,
                                                        style = MaterialTheme.typography.labelMedium,
                                                        fontWeight = FontWeight.Black,
                                                        color = Color(0xFFB45309)
                                                    )
                                                }
                                                FlowRow(
                                                    horizontalArrangement = Arrangement.spacedBy(5.dp),
                                                    verticalArrangement = Arrangement.spacedBy(5.dp)
                                                ) {
                                                    bricks.forEach { (brickText, isExtra) ->
                                                        val brickColor = if (isExtra) Color(0xFFDC2626) else Color(0xFF059669)
                                                        Surface(
                                                            shape = RoundedCornerShape(7.dp),
                                                            color = brickColor.copy(alpha = 0.14f),
                                                            border = BorderStroke(1.dp, brickColor)
                                                        ) {
                                                            Text(
                                                                text = brickText,
                                                                style = MaterialTheme.typography.labelSmall,
                                                                fontWeight = FontWeight.Bold,
                                                                color = brickColor,
                                                                modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                                                            )
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    }

                                    Surface(
                                        shape = RoundedCornerShape(10.dp),
                                        color = Color(0xFFFEF2F2),
                                        border = BorderStroke(0.8.dp, Color(0xFFDC2626).copy(alpha = 0.5f)),
                                        modifier = Modifier.fillMaxWidth()
                                    ) {
                                        BiDiText(
                                            text = b.konkoorTrapAlert,
                                            style = MaterialTheme.typography.bodySmall,
                                            fontWeight = FontWeight.Bold,
                                            color = Color(0xFF991B1B),
                                            modifier = Modifier.padding(10.dp)
                                        )
                                    }
                                }
                            }
                        }
                    }
                }

                2 -> {
                    // TAB 2: 3-Second Visual Scanner for Maazi vs Mudare vs Amr vs Masdar
                    Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                        Surface(
                            shape = RoundedCornerShape(12.dp),
                            color = accentColor.copy(alpha = 0.08f),
                            border = BorderStroke(0.8.dp, accentColor.copy(alpha = 0.35f)),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Column(
                                modifier = Modifier.padding(12.dp),
                                verticalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                BiDiText(
                                    text = "⚡ در تست کنکور می‌گوید «کدام گزینه مصدر است؟» یا «کدام گزینه فعل ماضی/امر است؟»؛ چطور بدون معنی کردن در ۳ ثانیه جواب بدهیم؟",
                                    style = MaterialTheme.typography.titleSmall,
                                    fontWeight = FontWeight.Black,
                                    color = accentColor
                                )
                                BiDiText(
                                    text = "۱) شکار «مصدر» در ۲ ثانیه (مصدر اسم است، نه فعل!):\n" +
                                        "• در ۶ باب (إفعال، تفعیل، مفاعلة، افتعال، انفعال، استفعال): یک حرف مانده به آخرش «الف (ا)» یا «یاء (ی)» یا در آخرش «ة» دارد! (إِکْرام، تَعْلیم، مُشاهَدَة، اِحْتِرام، اِنْقِلاب، اِسْتِخْراج).\n" +
                                        "• در ۲ باب با «تَـ» (تفعّل و تفاعل): حرفِ یکی مانده به آخرش «ضمه (ـُ)» دارد! (تَعَلُّم، تَشَکُّر، تَظاهُر، تَصادُف).\n\n" +
                                        "۲) شکار «فعل مضارع» در ۱ ثانیه:\n" +
                                        "• اولش حتماً یکی از ۴ حرف «یـ / تـ / أ / نـ» دارد و در ۳ باب اول (إفعال، تفعیل، مفاعلة) با «یُـ / تُـ / أُ / نُـ» (ضمه) شروع می‌شود! (یُکْرِمُ، یُعَلِّمُ، یُشاهِدُ).\n\n" +
                                        "۳) شکار «فعل امر» در ۲ ثانیه:\n" +
                                        "• آخرش ساکن (ـْ) است (در مفرد مذکر) و یکی مانده به آخرش کسره (ـِ) دارد (أَکْرِمْ، عَلِّمْ، شاهِدْ، اِحْتَرِمْ، اِنْکَسِرْ، اِسْتَخْرِجْ)؛ به جز دو باب تفعّل و تفاعل که یکی مانده به آخرشان فتحه (ـَ) دارد (تَعَلَّمْ، تَظاهَرْ)!\n\n" +
                                        "۴) شکار «فعل ماضی» در ۲ ثانیه:\n" +
                                        "• تمام حروفِ متحرک آن فتحه (ـَ) دارند! (أَکْرَمَ، عَلَّمَ، شاهَدَ، تَعَلَّمَ، تَظاهَرَ، اِحْتَرَمَ، اِنْکَسَرَ، اِسْتَخْرَجَ).",
                                    style = MaterialTheme.typography.bodySmall,
                                    color = MaterialTheme.colorScheme.onSurface
                                )
                            }
                        }

                        SmartDiagramAndFormulaBlock(
                            rawText = "نوع کلمه در باب‌های مزید | رمز چشمی حروف و حرکات | مثال مقایسه در یک نگاه\n" +
                                "۱. فعل ماضی (گذشته) | همه حرکاتش فتحه (ـَ) است! | أَکْرَمَ / عَلَّمَ / تَعَلَّمَ / اِحْتَرَمَ\n" +
                                "۲. فعل مضارع (حال) | اولش یُـ / یَـ دارد و آخرش ـُ است | یُکْرِمُ / یُعَلِّمُ / یَتَعَلَّمُ / یَحْتَرِمُ\n" +
                                "۳. فعل امر (فرمان) | آخرش ساکن (ـْ) است و اولش یـ ندارد | أَکْرِمْ / عَلِّمْ / تَعَلَّمْ / اِحْتَرِمْ\n" +
                                "۴. مصدر (اسم کار) | قبل از آخرش «ا / ی / ـُ» دارد و الـ/تنوین می‌گیرد | إِکْرام / تَعْلیم / تَعَلُّم / اِحْتِرام",
                            accentColor = accentColor
                        )
                    }
                }

                3 -> {
                    // TAB 3: 14 Sighas with 5 Fingers of the Hand
                    Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                        Surface(
                            shape = RoundedCornerShape(12.dp),
                            color = accentColor.copy(alpha = 0.08f),
                            border = BorderStroke(0.8.dp, accentColor.copy(alpha = 0.35f)),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Column(
                                modifier = Modifier.padding(12.dp),
                                verticalArrangement = Arrangement.spacedBy(6.dp)
                            ) {
                                BiDiText(
                                    text = "✋ نقشه ۵ انگشت دست برای ۱۴ صیغه عربی (هر انگشت = ۳ بند):",
                                    style = MaterialTheme.typography.titleSmall,
                                    fontWeight = FontWeight.Black,
                                    color = accentColor
                                )
                                BiDiText(
                                    text = "• انگشت ۱ (کوچک - غایب مذکر: او / آن دو / آن‌ها مرد): ذَهَبَ، ذَهَبا، ذَهَبوا | یَذْهَبُ، یَذْهَبانِ، یَذْهَبونَ\n" +
                                        "• انگشت ۲ (حلقه - غایب مؤنث: او / آن دو / آن‌ها زن): ذَهَبَتْ، ذَهَبَتا، ذَهَبْنَ | تَذْهَبُ، تَذْهَبانِ، یَذْهَبْنَ\n" +
                                        "• انگشت ۳ (وسط - مخاطب مذکر: تو / شما دو / شما مردان): ذَهَبْتَ، ذَهَبْتُما، ذَهَبْتُمْ | تَذْهَبُ، تَذْهَبانِ، تَذْهَبونَ\n" +
                                        "• انگشت ۴ (اشاره - مخاطب مؤنث: تو / شما دو / شما زنان): ذَهَبْتِ، ذَهَبْتُما، ذَهَبْتُنَّ | تَذْهَبینَ، تَذْهَبانِ، تَذْهَبْنَ\n" +
                                        "• انگشت ۵ (شست - متکلم: من / ما): ذَهَبْتُ، ذَهَبْنا | أَذْهَبُ، نَذْهَبُ",
                                    style = MaterialTheme.typography.bodySmall,
                                    color = MaterialTheme.colorScheme.onSurface
                                )
                            }
                        }

                        SmartDiagramAndFormulaBlock(
                            rawText = "نشانهٔ اول یا آخر فعل | معنی قطعی در فارسی (صیغه) | مثال در تست کنکور\n" +
                                "آخرِ ماضی «ـتُ» | من (انجام دادم) | ذَهَبْتُ = رفتم | عَلِمْتُ = دانستم\n" +
                                "آخرِ ماضی «ـتَ» | تو مرد (انجام دادی) | ذَهَبْتَ = رفتی\n" +
                                "آخرِ ماضی «ـتِ» | تو زن (انجام دادی) | ذَهَبْتِ = رفتی\n" +
                                "آخرِ ماضی «ـتُمْ» | شما (انجام دادید) | ذَهَبْتُمْ = رفتید\n" +
                                "آخرِ ماضی «ـوا» | آن‌ها (انجام دادند) | ذَهَبوا = رفتند\n" +
                                "آخرِ ماضی «ـنا» | ما (انجام دادیم) | ذَهَبْنا = رفتیم\n" +
                                "اولِ مضارع «أَ» | من (انجام می‌دهم) | أَذْهَبُ = می‌روم\n" +
                                "اولِ مضارع «نَـ» | ما (انجام می‌دهیم) | نَذْهَبُ = می‌رویم\n" +
                                "«یَـ ... ونَ» | آن‌ها (انجام می‌دهند) | یَذْهَبونَ = می‌روند\n" +
                                "«تَـ ... ونَ» | شما (انجام می‌دهید) | تَذْهَبونَ = می‌روید\n" +
                                "«تَـ ... ینَ» | تو زن (انجام می‌دهی) | تَذْهَبینَ = می‌روی",
                            accentColor = accentColor
                        )
                    }
                }
            }
        }
    }
}

private data class RelativePronounSlotTest(
    val leftBox: String,
    val leftMeaning: String,
    val answerPronoun: String,
    val rightBox: String,
    val rightMeaning: String,
    val twoSecondReason: String
)

private val relativePronounSlotTests = listOf(
    RelativePronounSlotTest(
        leftBox = "The dictionary (فرهنگ لغت = شیء)",
        leftMeaning = "غیرانسان (شیء)",
        answerPronoun = "which",
        rightBox = "is on the table (روی میز است)",
        rightMeaning = "فعل",
        twoSecondReason = "چون کلمهٔ سمت چپ (The dictionary) شیء است و انسان نیست، در ۱ ثانیه بدون خواندن بقیه جمله گزینه «which» (یا that) را می‌زنیم!"
    ),
    RelativePronounSlotTest(
        leftBox = "The woman (زن = انسان)",
        leftMeaning = "انسان",
        answerPronoun = "who",
        rightBox = "helped us yesterday (دیروز کمک کرد = فعل)",
        rightMeaning = "فعل (helped)",
        twoSecondReason = "سمت چپ انسان (The woman) و سمت راست فعل (helped) آمده است: فرمول «انسان + who + فعل»!"
    ),
    RelativePronounSlotTest(
        leftBox = "The doctor (پزشک = انسان)",
        leftMeaning = "انسان",
        answerPronoun = "whom",
        rightBox = "we met at the hospital (ما ملاقات کردیم = ضمیر فاعلی we)",
        rightMeaning = "فاعل (we)",
        twoSecondReason = "سمت چپ انسان (The doctor) و سمت راست ضمیر فاعلی (we) آمده است: فرمول «انسان + whom + فاعل (I/you/we/they/he/she)»!"
    ),
    RelativePronounSlotTest(
        leftBox = "The man to (مردی که به... = حرف اضافه to)",
        leftMeaning = "انسان + حرف اضافه (to/with/for)",
        answerPronoun = "whom",
        rightBox = "you were talking (داشتی صحبت می‌کردی)",
        rightMeaning = "فاعل (you)",
        twoSecondReason = "وقتی بلافاصله قبل از جای خالی حرف اضافه (to, with, for, by, from) بیاید و مرجع انسان باشد، ۱۰۰٪ فقط «whom» درست است (حتی who هم غلط است)!"
    ),
    RelativePronounSlotTest(
        leftBox = "The boy (پسر = انسان)",
        leftMeaning = "انسان",
        answerPronoun = "whose",
        rightBox = "bicycle was stolen (دوچرخه‌اش دزدیده شد = اسمِ بدون a/the)",
        rightMeaning = "اسم ملکی (bicycle)",
        twoSecondReason = "بعد از جای خالی یک اسمِ لُخت بدون a/an/the (bicycle = دوچرخه) آمده که متعلق به پسر است (پسر که دوچرخه‌اش...): فرمول «انسان + whose + اسم»!"
    ),
    RelativePronounSlotTest(
        leftBox = "The cats (گربه‌ها = حیوان)",
        leftMeaning = "غیرانسان (حیوان)",
        answerPronoun = "which",
        rightBox = "live in the garden (در باغ زندگی می‌کنند)",
        rightMeaning = "فعل (live)",
        twoSecondReason = "چون گربه‌ها (The cats) غیرانسان هستند، حتی اگر بعدش فعل بیاید هم به جای who باید «which» (یا that) بزنیم!"
    )
)

/**
 * راهنمای تصویری و تعاملی گرامر زبان انگلیسی از زیرِ صفر مطلق:
 * ویژه دانش‌آموزانی که هیچ پیش‌زمینه‌ای از «ضمایر موصولی (who/whom/which/whose)»، «Tag Questions»،
 * «مجهول (Passive)»، «شرطی نوع ۱ و ۲» و «to / ing» ندارند.
 * این کامپوننت منحصراً در زیرمجموعه درس زبان انگلیسی نمایش داده می‌شود.
 */
@OptIn(ExperimentalLayoutApi::class)
@Composable
fun EnglishZeroToMasterVisualGuide(
    accentColor: Color = Color(0xFF0284C7),
    modifier: Modifier = Modifier
) {
    var selectedTab by remember { mutableIntStateOf(0) }
    var selectedSlotTestIdx by remember { mutableIntStateOf(0) }

    Surface(
        modifier = modifier
            .fillMaxWidth()
            .testTag("english_zero_to_master_visual_guide"),
        shape = RoundedCornerShape(16.dp),
        color = MaterialTheme.colorScheme.surface,
        border = BorderStroke(1.2.dp, accentColor.copy(alpha = 0.45f)),
        tonalElevation = 1.dp
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(14.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            // Header
            Row(verticalAlignment = Alignment.CenterVertically) {
                Surface(
                    color = accentColor.copy(alpha = 0.14f),
                    shape = CircleShape,
                    modifier = Modifier.size(38.dp)
                ) {
                    Box(contentAlignment = Alignment.Center) {
                        Icon(
                            imageVector = Icons.Default.Language,
                            contentDescription = null,
                            tint = accentColor,
                            modifier = Modifier.size(20.dp)
                        )
                    }
                }
                Spacer(modifier = Modifier.width(10.dp))
                Column(modifier = Modifier.weight(1f)) {
                    BiDiText(
                        text = "🇬🇧 آموزش تصویری گرامر زبان انگلیسی از زیرِ صفر (بدون نیاز به معنی کردن!)",
                        style = MaterialTheme.typography.titleSmall,
                        fontWeight = FontWeight.Black,
                        color = accentColor
                    )
                    BiDiText(
                        text = "یادگیری ضمایر موصولی (who/whom/which)، مجهول، شرطی و دم‌سؤالی با جعبه‌های چپ و راست",
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }

            // 4 Clean Step Tabs inside English Guide
            val tabs = listOf(
                "۱. ضمایر موصولی (who/whom/which)",
                "۲. دم‌سؤالی (Tag) و ضمایر انعکاسی",
                "۳. مجهول (Passive) و شرطی (If)",
                "۴. افعال to/ing و کلمات ربط"
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .horizontalScroll(rememberScrollState()),
                horizontalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                tabs.forEachIndexed { idx, label ->
                    val isSelected = selectedTab == idx
                    Surface(
                        modifier = Modifier
                            .clip(RoundedCornerShape(10.dp))
                            .clickable { selectedTab = idx }
                            .testTag("english_zero_guide_tab_$idx"),
                        shape = RoundedCornerShape(10.dp),
                        color = if (isSelected) accentColor else accentColor.copy(alpha = 0.08f),
                        border = BorderStroke(0.8.dp, if (isSelected) accentColor else accentColor.copy(alpha = 0.3f))
                    ) {
                        Text(
                            text = label,
                            style = MaterialTheme.typography.labelMedium,
                            fontWeight = FontWeight.Bold,
                            color = if (isSelected) Color.White else accentColor,
                            modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp)
                        )
                    }
                }
            }

            when (selectedTab) {
                0 -> {
                    // TAB 0: Relative Pronouns (who / whom / which / whose) Visual Left-Right Box Method
                    val slotTest = relativePronounSlotTests[selectedSlotTestIdx.coerceIn(relativePronounSlotTests.indices)]
                    Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                        Surface(
                            shape = RoundedCornerShape(12.dp),
                            color = accentColor.copy(alpha = 0.07f),
                            border = BorderStroke(0.8.dp, accentColor.copy(alpha = 0.3f)),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Column(
                                modifier = Modifier.padding(12.dp),
                                verticalArrangement = Arrangement.spacedBy(6.dp)
                            ) {
                                BiDiText(
                                    text = "💡 اصلاً «ضمیر موصولی» یعنی چی؟ یعنی همان کلمهٔ «که» در فارسی!",
                                    style = MaterialTheme.typography.titleSmall,
                                    fontWeight = FontWeight.Black,
                                    color = accentColor
                                )
                                BiDiText(
                                    text = "در فارسی به همه چیز می‌گوییم «که» (مردی که آمد، کتابی که خریدم). اما در انگلیسی به جای یک کلمه «که»، ۴ تا کلمه دارند: who، whom، which و whose.\n" +
                                        "🔥 راز طلایی: برای زدن تست‌های این مبحث، اصلاً لازم نیست جمله را معنی کنی! فقط به ۱ کلمهٔ سمت چپِ جای خالی (قبل از خط تیره) و ۱ کلمهٔ سمت راستِ جای خالی (بعد از خط تیره) نگاه کن:",
                                    style = MaterialTheme.typography.bodySmall,
                                    color = MaterialTheme.colorScheme.onSurface
                                )
                            }
                        }

                        // 4 Visual Formula Cards
                        SmartDiagramAndFormulaBlock(
                            rawText = "کلمه سمت چپِ جای خالی (قبل) | ضمیر موصولی (وسط) | کلمه سمت راستِ جای خالی (بعد)\n" +
                                "۱. شیء یا حیوان (book, car, cat, city) | 👉 which (یا that) | هر چیزی (فعل یا فاعل فرقی ندارد!)\n" +
                                "۲. انسان (man, woman, boy, doctor, people) | 👉 who (یا that) | فعل (is, was, are, works, helped, can)\n" +
                                "۳. انسان (یا حرف اضافه to/with/for) | 👉 whom | فاعل (I, you, he, she, we, they, Ali)\n" +
                                "۴. انسان یا شیء | 👉 whose (که ...اش) | اسمِ بدون a/an/the (car, father, name, roof)",
                            accentColor = accentColor
                        )

                        // Interactive Slot-Machine Tester
                        Surface(
                            shape = RoundedCornerShape(14.dp),
                            color = MaterialTheme.colorScheme.surfaceContainerLow,
                            border = BorderStroke(1.dp, accentColor.copy(alpha = 0.4f)),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Column(
                                modifier = Modifier.padding(12.dp),
                                verticalArrangement = Arrangement.spacedBy(9.dp)
                            ) {
                                BiDiText(
                                    text = "🎰 دستگاه تست‌زنی ۳ ثانیه‌ای ضمایر موصولی (روی شماره تست‌ها بزن):",
                                    style = MaterialTheme.typography.labelLarge,
                                    fontWeight = FontWeight.Black,
                                    color = accentColor
                                )

                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .horizontalScroll(rememberScrollState()),
                                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                                ) {
                                    relativePronounSlotTests.forEachIndexed { idx, _ ->
                                        val isSelected = idx == selectedSlotTestIdx
                                        Surface(
                                            modifier = Modifier
                                                .clip(RoundedCornerShape(8.dp))
                                                .clickable { selectedSlotTestIdx = idx }
                                                .testTag("english_slot_test_$idx"),
                                            shape = RoundedCornerShape(8.dp),
                                            color = if (isSelected) accentColor else MaterialTheme.colorScheme.surface,
                                            border = BorderStroke(0.8.dp, accentColor)
                                        ) {
                                            Text(
                                                text = "تست ${BidiFormatter.toPersianDigits((idx + 1).toString())}",
                                                style = MaterialTheme.typography.labelMedium,
                                                fontWeight = FontWeight.Bold,
                                                color = if (isSelected) Color.White else accentColor,
                                                modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp)
                                            )
                                        }
                                    }
                                }

                                // 3 Visual Boxes (Left -> Middle -> Right)
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.spacedBy(6.dp),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    // Left Box
                                    Surface(
                                        shape = RoundedCornerShape(10.dp),
                                        color = Color(0xFF059669).copy(alpha = 0.12f),
                                        border = BorderStroke(1.dp, Color(0xFF059669)),
                                        modifier = Modifier.weight(1f)
                                    ) {
                                        Column(
                                            modifier = Modifier.padding(8.dp),
                                            horizontalAlignment = Alignment.CenterHorizontally
                                        ) {
                                            Text(
                                                text = "۱. کلمه قبل از جای خالی",
                                                style = MaterialTheme.typography.labelSmall,
                                                color = Color(0xFF059669),
                                                fontWeight = FontWeight.Bold
                                            )
                                            BiDiText(
                                                text = slotTest.leftBox,
                                                style = MaterialTheme.typography.labelMedium,
                                                fontWeight = FontWeight.Black,
                                                textAlign = TextAlign.Center
                                            )
                                        }
                                    }

                                    // Middle Answer Box
                                    Surface(
                                        shape = RoundedCornerShape(10.dp),
                                        color = Color(0xFFDC2626),
                                        modifier = Modifier.width(86.dp)
                                    ) {
                                        Column(
                                            modifier = Modifier.padding(8.dp),
                                            horizontalAlignment = Alignment.CenterHorizontally
                                        ) {
                                            Text(
                                                text = "پاسخ قطعی",
                                                style = MaterialTheme.typography.labelSmall,
                                                color = Color.White.copy(alpha = 0.9f)
                                            )
                                            Text(
                                                text = slotTest.answerPronoun,
                                                style = MaterialTheme.typography.titleMedium,
                                                fontWeight = FontWeight.Black,
                                                color = Color.White
                                            )
                                        }
                                    }

                                    // Right Box
                                    Surface(
                                        shape = RoundedCornerShape(10.dp),
                                        color = Color(0xFF7C3AED).copy(alpha = 0.12f),
                                        border = BorderStroke(1.dp, Color(0xFF7C3AED)),
                                        modifier = Modifier.weight(1f)
                                    ) {
                                        Column(
                                            modifier = Modifier.padding(8.dp),
                                            horizontalAlignment = Alignment.CenterHorizontally
                                        ) {
                                            Text(
                                                text = "۲. کلمه بعد از جای خالی",
                                                style = MaterialTheme.typography.labelSmall,
                                                color = Color(0xFF7C3AED),
                                                fontWeight = FontWeight.Bold
                                            )
                                            BiDiText(
                                                text = slotTest.rightBox,
                                                style = MaterialTheme.typography.labelMedium,
                                                fontWeight = FontWeight.Black,
                                                textAlign = TextAlign.Center
                                            )
                                        }
                                    }
                                }

                                Surface(
                                    shape = RoundedCornerShape(9.dp),
                                    color = MaterialTheme.colorScheme.surface,
                                    border = BorderStroke(0.6.dp, accentColor.copy(alpha = 0.3f)),
                                    modifier = Modifier.fillMaxWidth()
                                ) {
                                    BiDiText(
                                        text = "✅ تحلیل ۲ ثانیه‌ای: ${slotTest.twoSecondReason}",
                                        style = MaterialTheme.typography.bodySmall,
                                        fontWeight = FontWeight.Bold,
                                        color = MaterialTheme.colorScheme.onSurface,
                                        modifier = Modifier.padding(10.dp)
                                    )
                                }
                            }
                        }
                    }
                }

                1 -> {
                    // TAB 1: Tag Questions & Reflexive Pronouns (Mirror Rule)
                    Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                        Surface(
                            shape = RoundedCornerShape(12.dp),
                            color = accentColor.copy(alpha = 0.07f),
                            border = BorderStroke(0.8.dp, accentColor.copy(alpha = 0.3f)),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Column(
                                modifier = Modifier.padding(12.dp),
                                verticalArrangement = Arrangement.spacedBy(6.dp)
                            ) {
                                BiDiText(
                                    text = "🪞 قانون ۱: آینهٔ برعکس در دم‌سؤالی (Tag Questions - درس ۱ دوازدهم):",
                                    style = MaterialTheme.typography.titleSmall,
                                    fontWeight = FontWeight.Black,
                                    color = accentColor
                                )
                                BiDiText(
                                    text = "• اگر جمله اول مثبت (+) بود ⬅️ دم‌سؤالی باید منفی (n't) باشد: «Ali is clever, ➡️ isn't he?»\n" +
                                        "• اگر جمله اول منفی (-) بود ⬅️ دم‌سؤالی باید مثبت باشد: «Ali isn't lazy, ➡️ is he?»\n" +
                                        "🔥 تله بزرگ کنکور: کلماتِ «never, hardly, rarely, seldom, no, nothing, nobody, little, few» جمله را منفی می‌کنند؛ پس دم‌سؤالی آن‌ها باید ۱۰۰٪ مثبت باشد! («She never lies, ➡️ does she?»)\n" +
                                        "🔥 دو استثنای حفظی: «I am late, ➡️ aren't I?» و «Let's go, ➡️ shall we?»",
                                    style = MaterialTheme.typography.bodySmall,
                                    color = MaterialTheme.colorScheme.onSurface
                                )
                            }
                        }

                        SmartDiagramAndFormulaBlock(
                            rawText = "فاعل در ابتدای جمله (آینه مستقیم) | ضمیر انعکاسی در انتهای جمله (پایه دهم) | ترجمه فارسی\n" +
                                "I (من) | 👉 myself | خودم\n" +
                                "He / Ali / My father (مرد مفرد) | 👉 himself | خودش (مرد)\n" +
                                "She / Sara / My mother (زن مفرد) | 👉 herself | خودش (زن)\n" +
                                "It / The cat (شیء یا حیوان مفرد) | 👉 itself | خودش (شیء/حیوان)\n" +
                                "We / Ali and I (ما) | 👉 ourselves | خودمان\n" +
                                "You (تو / شما) | 👉 yourself / yourselves | خودت / خودتان\n" +
                                "They / The boys / Ali and Reza (جمع) | 👉 themselves | خودشان",
                            accentColor = accentColor
                        )
                    }
                }

                2 -> {
                    // TAB 2: Passive Voice & Conditionals (Scale Rule)
                    Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                        Surface(
                            shape = RoundedCornerShape(12.dp),
                            color = accentColor.copy(alpha = 0.07f),
                            border = BorderStroke(0.8.dp, accentColor.copy(alpha = 0.3f)),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Column(
                                modifier = Modifier.padding(12.dp),
                                verticalArrangement = Arrangement.spacedBy(6.dp)
                            ) {
                                BiDiText(
                                    text = "⚖️ قانون ۱: فرمول ۲ تکهٔ مجهول (Passive - درس ۱ دوازدهم):",
                                    style = MaterialTheme.typography.titleSmall,
                                    fontWeight = FontWeight.Black,
                                    color = accentColor
                                )
                                BiDiText(
                                    text = "• از کجا بفهمیم جمله مجهول است؟ ۱) اولِ جمله یک شیء است (مثل The bridge / The room / English) چون شیء خودش کاری انجام نمی‌دهد! ۲) یا آخرِ جمله کلمه طلایی «by + شخص» آمده است!\n" +
                                        "• هر گزینه‌ای که یکی از شکل‌های «Be» (یعنی am / is / are / was / were / be / been) + «فعل با ed یا ستون سوم (P.P.)» را نداشت، در ۱ ثانیه خط بزن!",
                                    style = MaterialTheme.typography.bodySmall,
                                    color = MaterialTheme.colorScheme.onSurface
                                )
                            }
                        }

                        SmartDiagramAndFormulaBlock(
                            rawText = "نوع ساختار در کنکور و نهایی | کفه اول ترازو | کفه دوم ترازو\n" +
                                "مجهول زمان حال (now / every day) | شیء (مفعول) | am / is / are + P.P. (فعل سوم)\n" +
                                "مجهول زمان گذشته (yesterday / last / in 2010) | شیء (مفعول) | was / were + P.P. (فعل سوم)\n" +
                                "مجهول با مدال (will / can / must / should) | شیء (مفعول) | will / can / must + be + P.P.\n" +
                                "شرطی نوع ۱ (آیندهٔ ممکن - پایه یازدهم) | If + حال ساده (فعل ساده یا با s) | will / can / may + فعل ساده\n" +
                                "شرطی نوع ۲ (خیالی در حال - پایه دوازدهم) | If + گذشته ساده (ed / ستون دوم / were) | would / could / might + فعل ساده",
                            accentColor = accentColor
                        )
                    }
                }

                3 -> {
                    // TAB 3: Gerund (ing) vs Infinitive (to) & Conjunctions (and/but/or/so)
                    Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                        SmartDiagramAndFormulaBlock(
                            rawText = "کلمهٔ قبل از جای خالی (پایه یازدهم و دوازدهم) | فرمول قطعی بعد از جای خالی | مثال امتحانی و کنکوری\n" +
                                "حروف اضافه (in, on, of, for, at, about, without, by, before, after) | 👉 ۱۰۰٪ فعل با ing | interested in learning / tired of waiting\n" +
                                "افعال (enjoy, finish, mind, avoid, keep, suggest, imagine) | 👉 ۱۰۰٪ فعل با ing | I enjoy reading books.\n" +
                                "افعال (want, decide, plan, hope, promise, agree, learn, would like) | 👉 ۱۰۰٪ to + فعل ساده | She decided to study hard.\n" +
                                "صفت‌ها (easy, hard, difficult, happy, important, Impossible) | 👉 ۱۰۰٪ to + فعل ساده | It is easy to learn English.\n" +
                                "جمله اول + [ ویرگول , ] + نتیجه و معلول | 👉 so (بنابراین) | He was sick, so he stayed home.\n" +
                                "جمله اول + تضاد و برعکسِ انتظار | 👉 but (اما) | He tried hard, but he failed.\n" +
                                "جمله امری مثبت در اول (Study hard, ...) | 👉 or (وگرنه / یا) | Hurry up, or you will be late.",
                            accentColor = accentColor
                        )
                    }
                }
            }
        }
    }
}
