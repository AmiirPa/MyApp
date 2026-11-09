package com.example.ui.screens

import androidx.activity.compose.BackHandler
import kotlinx.coroutines.launch
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandVertically
import androidx.compose.animation.shrinkVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.rememberScrollState
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
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.FormatSize
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.sp
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.BookmarkBorder
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.EmojiObjects
import androidx.compose.material.icons.filled.Lightbulb
import androidx.compose.material.icons.filled.MenuBook
import androidx.compose.material.icons.filled.Highlight
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Psychology
import androidx.compose.material.icons.filled.RadioButtonUnchecked
import androidx.compose.material.icons.filled.AltRoute
import androidx.compose.material.icons.filled.AutoStories
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.Calculate
import androidx.compose.material.icons.filled.Language
import androidx.compose.material.icons.filled.MusicNote
import androidx.compose.material.icons.filled.School
import androidx.compose.material.icons.filled.Translate
import androidx.compose.material.icons.filled.Quiz
import androidx.compose.material.icons.filled.Science
import androidx.compose.material.icons.filled.TrendingUp
import com.example.ui.theme.SubjectDesignSystem
import com.example.ui.theme.SubjectDesignToken
import androidx.compose.material.icons.filled.ExpandLess
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.ui.res.painterResource
import com.example.R
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.WarningAmber
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.lerp
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import com.example.data.curriculum.SubjectCatalog
import com.example.data.curriculum.SubjectMeta
import com.example.data.curriculum.ZeroLesson
import com.example.data.curriculum.officialTextbookReference
import com.example.data.curriculum.prerequisiteStepOrder
import com.example.data.curriculum.subcategoryFolder
import com.example.data.curriculum.normalizedSubjectId
import com.example.data.curriculum.getPrerequisiteLinks
import com.example.data.curriculum.LearningPath1406
import com.example.data.curriculum.LearningPathElementary1406
import com.example.data.curriculum.LearningPathMiddle1406
import com.example.data.curriculum.LessonLogos1406
import com.example.data.curriculum.MinusZeroLearningEngine
import com.example.data.local.LearningPathPreference
import com.example.ui.AppTab
import com.example.ui.KonkoorUiState
import com.example.ui.components.BiDiText
import com.example.ui.components.BidiFormatter
import com.example.ui.components.SmartDiagramAndFormulaBlock
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.Bolt
import androidx.compose.material.icons.filled.Download
import androidx.compose.material.icons.filled.Link
import androidx.compose.material.icons.filled.ExpandLess
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.material.icons.filled.Folder
import androidx.compose.material.icons.filled.Autorenew
import androidx.compose.material.icons.filled.FormatBold
import androidx.compose.material.icons.filled.PictureAsPdf
import androidx.compose.material.icons.filled.Spa
import androidx.compose.material3.TextButton
import com.example.data.curriculum.AdvancedMnemonicEngine
import com.example.ui.components.InAppTextbookPdfViewerDialog
import com.example.ui.components.LessonInteractiveVisualCard
import com.example.ui.components.LessonVectorIllustration
import com.example.ui.components.OfficialTextbookPdf
import com.example.ui.components.SimpleJargonDictionaryCard
import com.example.ui.components.SlowLearnerSuperMethodCard
import com.example.ui.components.TextbookPdfCatalog
import com.example.ui.components.WorkedExampleVisualDiagram
import com.example.ui.components.ExamNuanceAnalysisCard
import com.example.ui.components.GoldenKeyPointCalloutCard
import com.example.ui.theme.AppColorPalette
import com.example.ui.components.GoldenPointLevel
import com.example.ui.components.TrafficLightsLessonSummaryCard
import com.example.ui.components.MasterFiveSubjectsMethodsCard
import com.example.ui.components.CleanTextbookTableOfContentsCard
import com.example.data.curriculum.MiniCheckQuestion
import com.example.data.curriculum.QuizQuestionItem
import com.example.data.curriculum.FlashcardAndQuizBank
import com.example.data.curriculum.NahaeeExamsAndTarmimArchive
import com.example.ui.components.FiveSubjectsMethodRepository
import androidx.compose.material.icons.filled.FormatUnderlined
import androidx.compose.material.icons.filled.Highlight
import com.example.ui.theme.AppThemeController
import com.example.ui.theme.LocalAppThemeController
import com.example.ui.theme.resolveDynamicAccent
import com.example.ui.theme.LocalResponsiveDimens

fun getSubjectIcon(subjectId: String): androidx.compose.ui.graphics.vector.ImageVector = when (subjectId) {
    "math" -> Icons.Default.Calculate
    "arabic" -> Icons.Default.Translate
    "english" -> Icons.Default.Language
    "fonoon" -> Icons.Default.AutoStories
    "arooz" -> Icons.Default.MusicNote
    "sociology" -> Icons.Default.EmojiObjects
    "psychology" -> Icons.Default.Psychology
    "economics" -> Icons.Default.Calculate
    "philosophy" -> Icons.Default.Psychology
    "logic" -> Icons.Default.Lightbulb
    "history" -> Icons.Default.MenuBook
    "geography" -> Icons.Default.Language
    "dini" -> Icons.Default.Star
    "farsi" -> Icons.Default.AutoStories
    "negaresh" -> Icons.Default.AutoStories
    "farhangian" -> Icons.Default.School
    "social_identity" -> Icons.Default.EmojiObjects
    "general_humanities" -> Icons.Default.CheckCircle
    "media_environment" -> Icons.Default.Language
    "study_methods" -> Icons.Default.Psychology
    "history_geo" -> Icons.Default.MenuBook
    "social_psych_econ" -> Icons.Default.EmojiObjects
    else -> Icons.Default.MenuBook
}

data class SubjectGradeDropdownItem(
    val gradeLevel: Int, // 1: پایه ۱ تا ۹، 2: دهم، 3: یازدهم، 4: دوازدهم/نهایی
    val title: String,
    val subtitle: String,
    val badge: String
)

fun getSubjectGradeDropdownItems(subjectId: String): List<SubjectGradeDropdownItem> = when (subjectId) {
    "fonoon" -> listOf(
        SubjectGradeDropdownItem(
            gradeLevel = 2,
            title = "علوم و فنون ادبی ۱ (پایه دهم)",
            subtitle = "چاپ ۱۴۰۳ — سجع، ترصیع، موازنه، جناس و تاریخ ادبیات دهم",
            badge = "پایه دهم"
        ),
        SubjectGradeDropdownItem(
            gradeLevel = 3,
            title = "علوم و فنون ادبی ۲ (پایه یازدهم)",
            subtitle = "چاپ ۱۴۰۴ — تشبیه، استعاره، مجاز، کنایه و تاریخ ادبیات یازدهم",
            badge = "پایه یازدهم"
        ),
        SubjectGradeDropdownItem(
            gradeLevel = 4,
            title = "علوم و فنون ادبی ۳ (پایه دوازدهم)",
            subtitle = "چاپ ۱۴۰۵ — ایهام، متناقض‌نما، اسلوب معادله، لف‌ونشر و نهایی",
            badge = "پایه دوازدهم"
        )
    )
    "arooz" -> listOf(
        SubjectGradeDropdownItem(
            gradeLevel = 2,
            title = "عروض و قافیه ۱ (پایه دهم)",
            subtitle = "چاپ ۱۴۰۳ — تقطیع هجایی، علائم عروضی، ردیف و قوانین قافیه",
            badge = "پایه دهم"
        ),
        SubjectGradeDropdownItem(
            gradeLevel = 3,
            title = "عروض و قافیه ۲ (پایه یازدهم)",
            subtitle = "چاپ ۱۴۰۴ — اوزان همسان، تشخیص سماعی و بحور عروضی",
            badge = "پایه یازدهم"
        ),
        SubjectGradeDropdownItem(
            gradeLevel = 4,
            title = "عروض و قافیه ۳ (پایه دوازدهم)",
            subtitle = "چاپ ۱۴۰۵ — اوزان دوری، ناهمسان، ۱۶ وزن کنکور و اختیارات شاعری",
            badge = "پایه دوازدهم"
        )
    )
    "math" -> listOf(
        SubjectGradeDropdownItem(
            gradeLevel = 1,
            title = "پیش‌نیاز ریاضی (پایه ۱ تا ۹)",
            subtitle = "جدول ضرب، جمع و تفریق کسرها، اعداد علامت‌دار و اول ابتدایی تا نهم",
            badge = "پیش‌نیاز"
        ),
        SubjectGradeDropdownItem(
            gradeLevel = 2,
            title = "ریاضی و آمار ۱ (پایه دهم)",
            subtitle = "چاپ ۱۴۰۳ — معادله درجه دوم، حل مسئله، تابع و آمار توصیفی",
            badge = "پایه دهم"
        ),
        SubjectGradeDropdownItem(
            gradeLevel = 3,
            title = "ریاضی و آمار ۲ (پایه یازدهم)",
            subtitle = "چاپ ۱۴۰۴ — آشنایی با منطق، استدلال ریاضی و آمار تحلیلی",
            badge = "پایه یازدهم"
        ),
        SubjectGradeDropdownItem(
            gradeLevel = 4,
            title = "ریاضی و آمار ۳ (پایه دوازدهم)",
            subtitle = "چاپ ۱۴۰۵ — شمارش، احتمال، الگو و سری‌های زمانی ویژه نهایی",
            badge = "پایه دوازدهم"
        )
    )
    "philosophy" -> listOf(
        SubjectGradeDropdownItem(
            gradeLevel = 3,
            title = "فلسفه ۱ (پایه یازدهم)",
            subtitle = "چاپ ۱۴۰۴ — چیستی فلسفه، معرفت‌شناسی، ابزار شناخت و سیر تفکر",
            badge = "پایه یازدهم"
        ),
        SubjectGradeDropdownItem(
            gradeLevel = 4,
            title = "فلسفه ۲ (پایه دوازدهم)",
            subtitle = "چاپ ۱۴۰۵ — هستی و چیستی، علت و معلول، حکمت متعالیه و نهایی",
            badge = "پایه دوازدهم"
        )
    )
    "logic" -> listOf(
        SubjectGradeDropdownItem(
            gradeLevel = 2,
            title = "منطق دهم (بخش اول: تصورات)",
            subtitle = "چاپ ۱۴۰۳ — تعریف، الفاظ، مفاهیم پنج‌گانه و مغالطات تعریف",
            badge = "پایه دهم"
        ),
        SubjectGradeDropdownItem(
            gradeLevel = 2,
            title = "منطق دهم (بخش دوم: تصدیقات و استدلال)",
            subtitle = "چاپ ۱۴۰۳ — قضایا، مربع تقابل، عکس مستوی و قیاس اقترانی",
            badge = "پایه دهم"
        )
    )
    "economics" -> listOf(
        SubjectGradeDropdownItem(
            gradeLevel = 2,
            title = "اقتصاد دهم (مفاهیم و متن کتاب)",
            subtitle = "چاپ ۱۴۰۳ — کسب‌وکار، اصول بازار، عرضه و تقاضا، پول و بانکداری",
            badge = "پایه دهم"
        ),
        SubjectGradeDropdownItem(
            gradeLevel = 1,
            title = "مسائل ریاضی و محاسباتی اقتصاد",
            subtitle = "تکنیک‌های ۵ ثانیه‌ای و بدون فرمول حل مسائل مالیات، استهلاک و درآمد",
            badge = "مسائل کنکور"
        )
    )
    "psychology" -> listOf(
        SubjectGradeDropdownItem(
            gradeLevel = 3,
            title = "روان‌شناسی یازدهم (بخش ۱: شناخت و ادراک)",
            subtitle = "چاپ ۱۴۰۴ — تعریف، روش‌ها، مراحل رشد، احساس، توجه و ادراک",
            badge = "پایه یازدهم"
        ),
        SubjectGradeDropdownItem(
            gradeLevel = 3,
            title = "روان‌شناسی یازدهم (بخش ۲: حافظه، انگیزش و سلامت)",
            subtitle = "چاپ ۱۴۰۴ — انواع حافظه، خطاها، حل مسئله، تصمیم‌گیری و سلامت روان",
            badge = "پایه یازدهم"
        )
    )
    "sociology" -> listOf(
        SubjectGradeDropdownItem(
            gradeLevel = 2,
            title = "جامعه‌شناسی ۱ (پایه دهم)",
            subtitle = "چاپ ۱۴۰۳ — کنش اجتماعی، پدیده‌ها، هویت، جامعه‌پذیری و کج‌روی",
            badge = "پایه دهم"
        ),
        SubjectGradeDropdownItem(
            gradeLevel = 3,
            title = "جامعه‌شناسی ۲ (پایه یازدهم)",
            subtitle = "چاپ ۱۴۰۴ — فرهنگ جهانی، غرب، امپریالیسم و بیداری اسلامی",
            badge = "پایه یازدهم"
        ),
        SubjectGradeDropdownItem(
            gradeLevel = 4,
            title = "جامعه‌شناسی ۳ (پایه دوازدهم)",
            subtitle = "چاپ ۱۴۰۵ — ۳ رویکرد تبیینی، تفسیری و انتقادی ویژه امتحان نهایی",
            badge = "پایه دوازدهم"
        )
    )
    "history" -> listOf(
        SubjectGradeDropdownItem(
            gradeLevel = 2,
            title = "تاریخ ۱ (ایران و جهان باستان — دهم)",
            subtitle = "چاپ ۱۴۰۳ — پیدایش تمدن‌ها، سلسله‌های هخامنشی، اشکانی و ساسانی",
            badge = "پایه دهم"
        ),
        SubjectGradeDropdownItem(
            gradeLevel = 3,
            title = "تاریخ ۲ (اسلام و ایران تا صفویه — یازدهم)",
            subtitle = "چاپ ۱۴۰۴ — ظهور اسلام، خلافت‌ها، حکومت‌های ایرانی و دوره صفوی",
            badge = "پایه یازدهم"
        ),
        SubjectGradeDropdownItem(
            gradeLevel = 4,
            title = "تاریخ ۳ (ایران و جهان معاصر — دوازدهم)",
            subtitle = "چاپ ۱۴۰۵ — قاجار، مشروطه، پهلوی، انقلاب اسلامی و نهایی",
            badge = "پایه دوازدهم"
        )
    )
    "geography" -> listOf(
        SubjectGradeDropdownItem(
            gradeLevel = 2,
            title = "جغرافیای ۱ (جغرافیای ایران — دهم)",
            subtitle = "چاپ ۱۴۰۳ — نقشه‌خوانی، ناهمواری‌ها، آب‌وهوا و منابع آب ایران",
            badge = "پایه دهم"
        ),
        SubjectGradeDropdownItem(
            gradeLevel = 3,
            title = "جغرافیای ۲ (جغرافیای ناحیه‌ای — یازدهم)",
            subtitle = "چاپ ۱۴۰۴ — نواحی طبیعی، سیاسی و اقتصادی جهان",
            badge = "پایه یازدهم"
        ),
        SubjectGradeDropdownItem(
            gradeLevel = 4,
            title = "جغرافیای ۳ (جغرافیای کاربردی — دوازدهم)",
            subtitle = "چاپ ۱۴۰۵ — سامانه‌های اطلاعات جغرافیایی، مخاطرات و نهایی",
            badge = "پایه دوازدهم"
        )
    )
    "arabic" -> listOf(
        SubjectGradeDropdownItem(
            gradeLevel = 1,
            title = "پیش‌نیاز عربی (پایه ۷ تا ۹)",
            subtitle = "صرف ۱۴ صیغه با کد انگشتان دست، ضمایر و تشخیص اسم و فعل",
            badge = "پیش‌نیاز"
        ),
        SubjectGradeDropdownItem(
            gradeLevel = 2,
            title = "عربی زبان قرآن ۱ (پایه دهم)",
            subtitle = "چاپ ۱۴۰۳ — اوزان ثلاثی مزید، عدد و معدود و جمله اسمیه و فعلیه",
            badge = "پایه دهم"
        ),
        SubjectGradeDropdownItem(
            gradeLevel = 3,
            title = "عربی زبان قرآن ۲ (پایه یازدهم)",
            subtitle = "چاپ ۱۴۰۴ — اسم تفضیل و مکان، شبیه، شرط و افعال ناقصه",
            badge = "پایه یازدهم"
        ),
        SubjectGradeDropdownItem(
            gradeLevel = 4,
            title = "عربی زبان قرآن ۳ (پایه دوازدهم)",
            subtitle = "چاپ ۱۴۰۵ — حروف مشبهة بالفعل، لای نفی جنس، حال و استثنا ویژه نهایی",
            badge = "پایه دوازدهم"
        )
    )
    "english" -> listOf(
        SubjectGradeDropdownItem(
            gradeLevel = 1,
            title = "پیش‌نیاز زبان انگلیسی (پایه ۷ تا ۹)",
            subtitle = "Prospect 1, 2, 3 — زمان‌های اصلی و گرامر پایه‌ای جمله‌سازی",
            badge = "پیش‌نیاز"
        ),
        SubjectGradeDropdownItem(
            gradeLevel = 2,
            title = "زبان انگلیسی ۱ Vision 1 (پایه دهم)",
            subtitle = "چاپ ۱۴۰۳ — صفات تفضیلی و عالی، زمان آینده و گرامر دهم",
            badge = "پایه دهم"
        ),
        SubjectGradeDropdownItem(
            gradeLevel = 3,
            title = "زبان انگلیسی ۲ Vision 2 (پایه یازدهم)",
            subtitle = "چاپ ۱۴۰۴ — مصدر Gerund، ضمایر موصولی و جملات مرکب",
            badge = "پایه یازدهم"
        ),
        SubjectGradeDropdownItem(
            gradeLevel = 4,
            title = "زبان انگلیسی ۳ Vision 3 (پایه دوازدهم)",
            subtitle = "چاپ ۱۴۰۵ — مجهول، جملات شرطی، تگ کوئسشن و امتحان نهایی",
            badge = "پایه دوازدهم"
        )
    )
    "dini" -> listOf(
        SubjectGradeDropdownItem(
            gradeLevel = 2,
            title = "دین و زندگی ۱ (پایه دهم)",
            subtitle = "چاپ ۱۴۰۳ — درس ۱ تا ۱۲: هدف آفرینش، فرجام انسان و برزخ",
            badge = "پایه دهم"
        ),
        SubjectGradeDropdownItem(
            gradeLevel = 3,
            title = "دین و زندگی ۲ (پایه یازدهم)",
            subtitle = "چاپ ۱۴۰۴ — درس ۱ تا ۱۴: هدایت مستمر، امامت و مرجعیت دینی",
            badge = "پایه یازدهم"
        ),
        SubjectGradeDropdownItem(
            gradeLevel = 4,
            title = "دین و زندگی ۳ (پایه دوازدهم)",
            subtitle = "چاپ ۱۴۰۵ — درس ۱ تا ۱۰: مراتب توحید، اخلاص و قضا و قدر ویژه نهایی",
            badge = "پایه دوازدهم"
        )
    )
    "farsi" -> listOf(
        SubjectGradeDropdownItem(
            gradeLevel = 1,
            title = "پیش‌نیاز فارسی و دستور زبان جامع (۱ تا ۹)",
            subtitle = "نقش کلمات، ساختمان واژه، ترکیب‌های وصفی و اضافی از پایه",
            badge = "پیش‌نیاز"
        ),
        SubjectGradeDropdownItem(
            gradeLevel = 2,
            title = "فارسی و نگارش ۱ (پایه دهم)",
            subtitle = "چاپ ۱۴۰۳ — قلمرو زبانی، ادبی و فکری درس‌های دهم",
            badge = "پایه دهم"
        ),
        SubjectGradeDropdownItem(
            gradeLevel = 3,
            title = "فارسی و نگارش ۲ (پایه یازدهم)",
            subtitle = "چاپ ۱۴۰۴ — واژگان، املا، تاریخ ادبیات و قرابت معنایی یازدهم",
            badge = "پایه یازدهم"
        ),
        SubjectGradeDropdownItem(
            gradeLevel = 4,
            title = "فارسی و نگارش ۳ (پایه دوازدهم)",
            subtitle = "چاپ ۱۴۰۵ — آمادگی ۲۰ امتحان نهایی و قالب‌های نگارش نهایی",
            badge = "پایه دوازدهم"
        )
    )
    "farhangian" -> listOf(
        SubjectGradeDropdownItem(
            gradeLevel = 3,
            title = "مهارت‌های معلمی استاد قرائتی",
            subtitle = "دفترچه اختصاصی فرهنگیان ۱۴۰۶ — فصول ۱، ۲ و ۳ کتاب",
            badge = "دفترچه فرهنگیان"
        ),
        SubjectGradeDropdownItem(
            gradeLevel = 3,
            title = "تعلیم و تربیت در اسلام (شهید مطهری)",
            subtitle = "منبع رسمی مصوب سازمان سنجش — عقل، عادت، تقوا و کرامت نفس",
            badge = "منبع رسمی سنجش"
        ),
        SubjectGradeDropdownItem(
            gradeLevel = 4,
            title = "هوش و استعداد معلمی و تحلیلی",
            subtitle = "تحلیل متن، استدلال منطقی، درک ریاضی و تجسم فضایی",
            badge = "دفترچه فرهنگیان"
        ),
        SubjectGradeDropdownItem(
            gradeLevel = 2,
            title = "فرهنگ و تمدن اسلام و ایران",
            subtitle = "گزیده منابع رسمی اعلامی سازمان سنجش — دارالفنون و تاریخ آموزش",
            badge = "فرهنگ و تمدن"
        ),
        SubjectGradeDropdownItem(
            gradeLevel = 2,
            title = "آیات، روایات و پیام‌های دین و زندگی",
            subtitle = "مفاهیم توحید، امامت، اخلاص، تعلیم و احکام گزینش",
            badge = "آیات و احکام"
        ),
        SubjectGradeDropdownItem(
            gradeLevel = 4,
            title = "سوالات کانون ارزیابی و مصاحبه حضوری",
            subtitle = "سناریوهای کلاسداری، گزینش عقیدتی-سیاسی و احکام",
            badge = "مصاحبه و گزینش"
        )
    )
    "negaresh" -> listOf(
        SubjectGradeDropdownItem(
            gradeLevel = 2,
            title = "نگارش ۱ (پایه دهم)",
            subtitle = "چاپ ۱۴۰۳ — گزارش‌نویسی، توصیف و عینک نوشتن",
            badge = "پایه دهم"
        ),
        SubjectGradeDropdownItem(
            gradeLevel = 3,
            title = "نگارش ۲ (پایه یازدهم)",
            subtitle = "چاپ ۱۴۰۴ — زندگینامه‌نویسی، سفرنامه و نوشتن خلاق",
            badge = "پایه یازدهم"
        ),
        SubjectGradeDropdownItem(
            gradeLevel = 4,
            title = "نگارش ۳ (پایه دوازدهم — نهایی ۲۰)",
            subtitle = "چاپ ۱۴۰۵ — مثل‌نویسی، نقد و تحلیل و قالب آماده انشا",
            badge = "پایه دوازدهم"
        )
    )
    "social_identity" -> listOf(
        SubjectGradeDropdownItem(
            gradeLevel = 4,
            title = "هویت اجتماعی دوازدهم (فصل ۱ تا ۳)",
            subtitle = "چاپ ۱۴۰۵ — کنش اجتماعی، هویت فردی و اجتماعی و بازتولید هویت",
            badge = "پایه دوازدهم"
        ),
        SubjectGradeDropdownItem(
            gradeLevel = 4,
            title = "هویت اجتماعی دوازدهم (فصل ۴ تا ۶)",
            subtitle = "چاپ ۱۴۰۵ — تحرک اجتماعی، هویت ملی ایرانی-اسلامی و جهانی‌شدن",
            badge = "پایه دوازدهم"
        )
    )
    "general_humanities" -> listOf(
        SubjectGradeDropdownItem(
            gradeLevel = 4,
            title = "سلامت و بهداشت دوازدهم (بخش ۱: تغذیه و سلامت)",
            subtitle = "چاپ ۱۴۰۵ — ابعاد سلامت، نمایه توده بدنی BMI و تغذیه سالم",
            badge = "پایه دوازدهم"
        ),
        SubjectGradeDropdownItem(
            gradeLevel = 4,
            title = "سلامت و بهداشت دوازدهم (بخش ۲: پیشگیری از بیماری‌ها)",
            subtitle = "چاپ ۱۴۰۵ — بیماری‌های غیرواگیر، واگیر، بهداشت روان و نهایی",
            badge = "پایه دوازدهم"
        )
    )
    "media_environment" -> listOf(
        SubjectGradeDropdownItem(
            gradeLevel = 2,
            title = "تفکر و سواد رسانه‌ای دهم",
            subtitle = "چاپ ۱۴۰۳ — رژیم مصرف، فنون اقناع و تفکر انتقادی",
            badge = "پایه دهم"
        ),
        SubjectGradeDropdownItem(
            gradeLevel = 3,
            title = "انسان و محیط زیست یازدهم",
            subtitle = "چاپ ۱۴۰۴ — آب مجازی، ردپای بوم‌شناختی، خاک، هوا و پسماند",
            badge = "پایه یازدهم"
        ),
        SubjectGradeDropdownItem(
            gradeLevel = 4,
            title = "مدیریت خانواده و سبک زندگی دوازدهم",
            subtitle = "چاپ ۱۴۰۵ — ازدواج آگاهانه، اقتصاد خانواده و مدیریت زمان",
            badge = "پایه دوازدهم"
        )
    )
    "study_methods" -> listOf(
        SubjectGradeDropdownItem(
            gradeLevel = 1,
            title = "تکنیک فاینمن، بازیابی فعال و جعبه لایتنر",
            subtitle = "علم عصب‌شناسی یادگیری، منحنی فراموشی ابینگهاوس و مرور ۱-۳-۷-۳۰",
            badge = "یادگیری علمی"
        ),
        SubjectGradeDropdownItem(
            gradeLevel = 2,
            title = "تندخوانی، خلاصه‌نویسی کرنل و نقشه ذهنی",
            subtitle = "افزایش ۳ برابری سرعت مطالعه دروس حفظی و مفهومی انسانی",
            badge = "تندخوانی"
        ),
        SubjectGradeDropdownItem(
            gradeLevel = 4,
            title = "مهندسی تست‌زنی کنکور (ضربدر-منها) و بیست نهایی",
            subtitle = "مدیریت زمان دفترچه انسانی، مهندسی معکوس گزینه‌ها و مصاحبه فرهنگیان",
            badge = "تست‌زنی کنکور"
        )
    )
    else -> listOf(
        SubjectGradeDropdownItem(
            gradeLevel = 2,
            title = "پایه دهم (چاپ ۱۴۰۳)",
            subtitle = "سرفصل کامل پایه دهم ویژه کنکور و نهایی",
            badge = "پایه دهم"
        ),
        SubjectGradeDropdownItem(
            gradeLevel = 3,
            title = "پایه یازدهم (چاپ ۱۴۰۴)",
            subtitle = "سرفصل کامل پایه یازدهم ویژه کنکور و نهایی",
            badge = "پایه یازدهم"
        ),
        SubjectGradeDropdownItem(
            gradeLevel = 4,
            title = "پایه دوازدهم (چاپ ۱۴۰۵)",
            subtitle = "سرفصل کامل پایه دوازدهم ویژه کنکور و نهایی",
            badge = "پایه دوازدهم"
        )
    )
}

data class StudySubjectUi(
    val id: String,
    val title: String,
    val subtitle: String,
    val progress: Int
)

private val HomeBackground: Color
    @Composable get() = MaterialTheme.colorScheme.background

private val HomePrimary: Color
    @Composable get() = MaterialTheme.colorScheme.primary

private val HomeOnPrimary: Color
    @Composable get() = MaterialTheme.colorScheme.onPrimary

private val HomeText: Color
    @Composable get() = MaterialTheme.colorScheme.onSurface

private val HomeMuted: Color
    @Composable get() = MaterialTheme.colorScheme.onSurfaceVariant

private val HomeBorder: Color
    @Composable get() = MaterialTheme.colorScheme.outlineVariant

private val HomeGreen: Color
    @Composable get() = MaterialTheme.colorScheme.tertiary

@OptIn(ExperimentalLayoutApi::class, ExperimentalMaterial3Api::class)
@Composable
fun HomeDashboardScreen(
    uiState: KonkoorUiState,
    studentDisplayName: String? = null,
    onSelectSubjectForLessons: (String) -> Unit,
    onOpenLesson: (String?) -> Unit,
    onNavigateTab: (AppTab) -> Unit,
    onSaveLessonState: (ZeroLesson, Boolean, Boolean, String, Int) -> Unit = { _, _, _, _, _ -> },
    /** نتیجه مینی‌چک انتهای درس (فقط اولین پاسخ)؛ null یعنی ثبت نمی‌شود. */
    onMiniCheckAnswered: ((ZeroLesson, Boolean) -> Unit)? = null
) {
    // صفحه مسیر ۱ تا ۱۰۰ با وضعیت محلی باز می‌شود: همان صفحه‌ای که در
    // «بیشتر» با چیپ مسیر باز می‌شود، ولی از خانه با یک ضربه.
    // وضعیت محلی است چون AppTab در KonkoorViewModel تعریف شده و آن فایل
    // نباید دست بخورد، پس تب تازه برای لینک مستقیم ساخته نشد.
    var showLearningPath by remember { mutableStateOf(false) }
    BackHandler(enabled = showLearningPath) { showLearningPath = false }

    // 1. If a lesson is open, render LessonDetailView directly
    val activeLesson = remember(uiState.allLessons, uiState.activeLessonId) {
        uiState.allLessons.find { it.id == uiState.activeLessonId }
    }
    if (activeLesson != null) {
        LessonDetailView(
            lesson = activeLesson,
            allLessons = uiState.allLessons,
            progress = uiState.lessonProgressMap[activeLesson.id],
            onBack = { onOpenLesson(null) },
            onNavigateToLesson = { targetId -> onOpenLesson(targetId) },
            onSaveState = { isCompleted, isBookmarked, note, mastery ->
                onSaveLessonState(activeLesson, isCompleted, isBookmarked, note, mastery)
            },
            onMiniCheckAnswered = onMiniCheckAnswered
        )
        return
    }

    // 1b. If the learning path is open, render it directly (same screen as
    // the More tab chip; opening a lesson from here shows the lesson above).
    if (showLearningPath) {
        LearningPathScreen(
            uiState = uiState,
            onOpenLesson = { lessonId -> onOpenLesson(lessonId) }
        )
        return
    }

    // 2. If a specific subject is selected while on Home, render ZeroLessonsScreen
    if (uiState.selectedSubjectId != "all") {
        ZeroLessonsScreen(
            uiState = uiState,
            onSelectSubject = { onSelectSubjectForLessons(it) },
            onOpenLesson = { onOpenLesson(it) },
            onSaveLessonState = onSaveLessonState,
            onMiniCheckAnswered = onMiniCheckAnswered
        )
        return
    }

    val nextLesson = remember(uiState.allLessons, uiState.lessonProgressMap) {
        uiState.allLessons.firstOrNull { lesson ->
            uiState.lessonProgressMap[lesson.id]?.isCompleted != true
        } ?: uiState.allLessons.firstOrNull()
    }

    val lastStudiedOrBookmarkedLesson = remember(uiState.allLessons, uiState.lessonProgressMap, nextLesson) {
        uiState.allLessons.firstOrNull { uiState.lessonProgressMap[it.id]?.isBookmarked == true }
            ?: uiState.allLessons.lastOrNull { uiState.lessonProgressMap[it.id]?.isCompleted == true }
            ?: nextLesson
    }

    val currentSubjectTitle = remember(nextLesson) {
        if (nextLesson != null) {
            SubjectCatalog.getSubjectById(nextLesson.normalizedSubjectId()).shortTitle.ifBlank {
                SubjectCatalog.getSubjectById(nextLesson.normalizedSubjectId()).title.substringAfter(". ")
            }
        } else {
            "علوم و فنون ادبی"
        }
    }

    val currentLessonTitle = remember(nextLesson) {
        nextLesson?.title ?: "شروع گام اول مطالعه"
    }

    // Daily progress = the day's task checklist only.
    // (The old code took max(task %, ALL-TIME lesson %) — overall curriculum
    // progress is not daily progress: it kept "today" at >=40% even on a day
    // with zero study. The overall figure is already shown separately on this
    // card as completed/total lessons.)
    // Limitation, kept visible: tasks are not auto-reset each day, so this is
    // the checklist's completion share, not a clock-based daily metric.
    val dailyProgressPct = remember(uiState.studyTasks) {
        if (uiState.studyTasks.isNotEmpty()) {
            (uiState.studyTasks.count { it.isCompleted } * 100) / uiState.studyTasks.size
        } else 0
    }.coerceIn(0, 100)

    val studySubjects = remember(uiState.subjects, uiState.allLessons, uiState.lessonProgressMap) {
        uiState.subjects.map { sub ->
            val pct = (uiState.subjectProgressFraction(sub.id) * 100).toInt().coerceIn(0, 100)
            val cleanTitle = sub.shortTitle.ifBlank { sub.title.substringAfter(". ") }
            val lessonsCount = uiState.allLessons.count { it.normalizedSubjectId() == sub.id }
            val countFa = BidiFormatter.toPersianDigits(lessonsCount.toString())
            StudySubjectUi(
                id = sub.id,
                title = BidiFormatter.applyPreferredDigits(cleanTitle),
                subtitle = BidiFormatter.applyPreferredDigits("$countFa مبحث • ${sub.konkoorCoefficient}"),
                progress = pct
            )
        }
    }

    val cleanStudentName = remember(studentDisplayName) {
        studentDisplayName?.substringBefore("@")?.takeIf { it.isNotBlank() } ?: "دانش‌آموز عزیز"
    }

    val currentSubjectId = remember(nextLesson) {
        nextLesson?.normalizedSubjectId() ?: "fonoon"
    }

    // جادهٔ انتخاب‌شدهٔ خانه (دبستان / متوسطه اول / مسیر ۱ تا ۱۰۰):
    // از SharedPreferences خوانده می‌شود، پیش‌فرض HIGH. بدون remember
    // خوانده می‌شود تا اگر بخش در صفحهٔ مسیر عوض شد، برگشت به خانه
    // همان جادهٔ تازه را نشان بدهد. قدم فعلی، درس بعدی و شمار
    // کامل‌شده با نسخه‌های دوآرگومانی همان helperها از فهرست قدم‌های
    // همین بخش حساب می‌شوند — دیگر hardcode مسیر HIGH نیست.
    val pathSectionName = LearningPathPreference.loadSectionName(LocalContext.current)
    val pathSteps = when (pathSectionName) {
        LearningPathPreference.SECTION_ELEMENTARY -> LearningPathElementary1406.steps
        LearningPathPreference.SECTION_MIDDLE -> LearningPathMiddle1406.steps
        else -> LearningPath1406.steps
    }
    val pathSectionLabel = when (pathSectionName) {
        LearningPathPreference.SECTION_ELEMENTARY -> "دبستان"
        LearningPathPreference.SECTION_MIDDLE -> "متوسطه اول"
        else -> "مسیر یادگیری ۱ تا ۱۰۰"
    }
    val pathTotalSteps = pathSteps.size
    // قدم فعلی جادهٔ انتخاب‌شده برای کارت hero خانه.
    val pathCurrentStep = remember(uiState.lessonProgressMap, pathSteps) {
        currentLearningPathStep(uiState, pathSteps)
    }
    // نقشه درس‌ها یک‌بار کش می‌شود (remember روی allLessons) و به
    // nextUnfinishedPathLessonId داده می‌شود تا associateBy روی ۲٬۴۲۷ درس
    // در هر فراخوانی ساخته نشود.
    val pathLessonById = remember(uiState.allLessons) {
        uiState.allLessons.associateBy { it.id }
    }
    val pathNextLessonId = remember(
        uiState.lessonProgressMap,
        pathLessonById,
        pathCurrentStep
    ) {
        nextUnfinishedPathLessonId(pathCurrentStep, uiState.lessonProgressMap, pathLessonById)
    }
    val pathNextLessonTitle = remember(uiState.allLessons, pathNextLessonId) {
        uiState.allLessons.find { it.id == pathNextLessonId }?.title
    }
    val pathCompletedSteps = remember(uiState.lessonProgressMap, pathSteps) {
        countCompletedPathSteps(uiState, pathSteps)
    }
    // درس‌های امروز: از قدم فعلی به بعد، به ترتیب قدم‌ها، درس‌های
    // خوانده‌نشده (progress کامل نیست) جمع می‌شوند تا ۳ درس؛ هر درس
    // با شمارهٔ قدمش نگه داشته می‌شود تا کارت «امروز» قدم را هم نشان بدهد.
    val todayLessonsWithSteps = remember(
        uiState.lessonProgressMap,
        pathSteps,
        pathCurrentStep,
        pathLessonById
    ) {
        val startIndex = pathSteps.indexOf(pathCurrentStep).coerceAtLeast(0)
        val collected = mutableListOf<Pair<Int, ZeroLesson>>()
        for (step in pathSteps.drop(startIndex)) {
            for (lessonId in step.lessonIds) {
                if (collected.size >= 3) break
                val lesson = pathLessonById[lessonId] ?: continue
                if (uiState.lessonProgressMap[lessonId]?.isCompleted != true) {
                    collected.add(step.stepNumber to lesson)
                }
            }
            if (collected.size >= 3) break
        }
        collected.toList()
    }
    val todayLessons = todayLessonsWithSteps.map { it.second }
    val todayLessonStepNumbers = todayLessonsWithSteps.map { it.first }

    StudyFirstHomeScreen(
        dateText = BidiFormatter.applyPreferredDigits("کنکور انسانی و فرهنگیان ۱۴۰۶"),
        studentName = cleanStudentName,
        currentSubject = BidiFormatter.applyPreferredDigits(currentSubjectTitle),
        currentLesson = BidiFormatter.applyPreferredDigits(currentLessonTitle),
        currentSubjectId = currentSubjectId,
        dailyProgress = dailyProgressPct,
        completedLessonsCount = uiState.completedLessonsCount,
        totalLessonsCount = uiState.totalLessonsCount,
        masteredCardsCount = uiState.masteredFlashcardsCount,
        subjects = studySubjects,
        onStartStudy = {
            if (nextLesson != null) {
                onOpenLesson(nextLesson.id)
            } else {
                onNavigateTab(AppTab.LESSONS)
            }
        },
        onContinueStudy = {
            val target = lastStudiedOrBookmarkedLesson ?: nextLesson
            if (target != null) {
                onOpenLesson(target.id)
            } else {
                onNavigateTab(AppTab.LESSONS)
            }
        },
        onOpenSubject = { subjectUi ->
            onSelectSubjectForLessons(subjectUi.id)
        },
        onOpenAllSubjects = {
            onNavigateTab(AppTab.LESSONS)
        },
        onOpenPlanner = {
            onNavigateTab(AppTab.PLANNER)
        },
        onOpenTests = {
            onNavigateTab(AppTab.PRACTICE)
        },
        onOpenMore = {
            onNavigateTab(AppTab.COUNSELING)
        },
        onOpenLab = {
            onNavigateTab(AppTab.LAB)
        },
        onOpenRoadmap = {
            onNavigateTab(AppTab.ROADMAP)
        },
        pathStepNumber = pathCurrentStep.stepNumber,
        pathStepTitle = pathCurrentStep.title,
        pathNextLessonTitle = pathNextLessonTitle,
        pathCompletedSteps = pathCompletedSteps,
        pathSectionLabel = pathSectionLabel,
        pathTotalSteps = pathTotalSteps,
        todayLessons = todayLessons,
        todayLessonStepNumbers = todayLessonStepNumbers,
        onOpenLearningPath = {
            showLearningPath = true
        },
        onOpenNextPathLesson = {
            if (pathNextLessonId != null) {
                onOpenLesson(pathNextLessonId)
            }
        },
        onOpenTodayLesson = { lessonId ->
            onOpenLesson(lessonId)
        }
    )
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun StudyFirstHomeScreen(
    dateText: String,
    studentName: String,
    currentSubject: String,
    currentLesson: String,
    currentSubjectId: String = "fonoon",
    dailyProgress: Int,
    completedLessonsCount: Int = 0,
    totalLessonsCount: Int = 0,
    masteredCardsCount: Int = 0,
    subjects: List<StudySubjectUi>,
    onStartStudy: () -> Unit,
    onContinueStudy: () -> Unit,
    onOpenSubject: (StudySubjectUi) -> Unit,
    onOpenAllSubjects: () -> Unit = {},
    onOpenPlanner: () -> Unit,
    onOpenTests: () -> Unit,
    onOpenMore: () -> Unit,
    onOpenLab: () -> Unit = onOpenMore,
    onOpenRoadmap: () -> Unit = onOpenMore,
    pathStepNumber: Int = 1,
    pathStepTitle: String = "",
    pathNextLessonTitle: String? = null,
    pathCompletedSteps: Int = 0,
    pathSectionLabel: String = "مسیر یادگیری ۱ تا ۱۰۰",
    pathTotalSteps: Int = 100,
    todayLessons: List<ZeroLesson> = emptyList(),
    todayLessonStepNumbers: List<Int> = emptyList(),
    onOpenLearningPath: () -> Unit = {},
    onOpenNextPathLesson: () -> Unit = {},
    onOpenTodayLesson: (String) -> Unit = {}
) {
    // کارت راهنمای «از کجا شروع کنم؟»: تا وقتی بسته نشده نشان داده
    // می‌شود؛ بستن در Preference ذخیره می‌شود و دیگر برنمی‌گردد.
    val guideContext = LocalContext.current
    var startGuideDismissed by remember {
        mutableStateOf(LearningPathPreference.isStartGuideDismissed(guideContext))
    }
    val isDark = androidx.compose.foundation.isSystemInDarkTheme()
    val themeController = LocalAppThemeController.current
    val effectiveDark = themeController.resolveIsDarkTheme(isDark)
    val subjectToken = remember(currentSubjectId, effectiveDark) {
        SubjectDesignSystem.getToken(currentSubjectId, effectiveDark)
    }

    androidx.compose.runtime.CompositionLocalProvider(
        androidx.compose.ui.platform.LocalLayoutDirection provides androidx.compose.ui.unit.LayoutDirection.Rtl
    ) {
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .testTag("home_dashboard_list"),
            color = MaterialTheme.colorScheme.background
        ) {
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(
                    horizontal = 18.dp,
                    vertical = 20.dp
                ),
                verticalArrangement = Arrangement.spacedBy(20.dp)
            ) {
                // 0. کارت راهنمای «از کجا شروع کنم؟» — اولین آیتم خانه،
                // کوتاه و قابل‌بستن؛ بستن ذخیره می‌شود و کارت برنمی‌گردد.
                if (!startGuideDismissed) {
                    item(key = "home_start_guide") {
                        StartGuideCard(
                            onDismiss = {
                                LearningPathPreference.setStartGuideDismissed(guideContext, true)
                                startGuideDismissed = true
                            }
                        )
                    }
                }

                // 1. Smart Expressive Header with Monogram and Goal Badge
                item(key = "home_expressive_header") {
                    ExpressiveSmartHeader(
                        studentName = studentName,
                        dateText = dateText
                    )
                }

                // 2. Hero جادهٔ انتخاب‌شده — بزرگ‌ترین و تنها شروع خانه
                item(key = "home_learning_path_hero") {
                    LearningPathHeroCard(
                        stepNumber = pathStepNumber,
                        stepTitle = pathStepTitle,
                        nextLessonTitle = pathNextLessonTitle,
                        completedSteps = pathCompletedSteps,
                        sectionLabel = pathSectionLabel,
                        totalSteps = pathTotalSteps,
                        onOpenPath = onOpenLearningPath,
                        onOpenNextLesson = onOpenNextPathLesson
                    )
                }

                // 2a. «امروز این‌ها را بخوان» — فقط وقتی درس امروزی هست؛
                // زدن هر ردیف درس را باز می‌کند.
                if (todayLessons.isNotEmpty()) {
                    item(key = "home_today_lessons") {
                        TodayLessonsCard(
                            lessons = todayLessons,
                            stepNumbers = todayLessonStepNumbers,
                            onOpenLesson = onOpenTodayLesson
                        )
                    }
                }

                // 2b. نوار «امروز»: یک خط گرم و جمع‌وجور — قدم فعلی جاده
                // (همان قدم کارت hero) + تیتر قدم؛ بدون دکمه و بدون بخش تازه.
                item(key = "home_today_bar") {
                    TodayStepBar(
                        stepNumber = pathStepNumber,
                        stepTitle = pathStepTitle,
                        totalSteps = pathTotalSteps
                    )
                }

                // 3. Continue Study (ادامه مطالعه آخرین مبحث)
                item(key = "home_continue_study") {
                    ExpressiveContinueStudyCard(
                        subject = currentSubject,
                        lesson = currentLesson,
                        token = subjectToken,
                        onContinue = onContinueStudy
                    )
                }

            }
        }
    }
}

@Composable
private fun ExpressiveSmartHeader(
    studentName: String,
    dateText: String
) {
    val initialChar = remember(studentName) {
        studentName.trim().firstOrNull()?.toString() ?: "ف"
    }

    Surface(
        modifier = Modifier.fillMaxWidth(),
        color = MaterialTheme.colorScheme.surface,
        shape = RoundedCornerShape(22.dp),
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.6f))
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 14.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            Box(
                modifier = Modifier
                    .size(48.dp)
                    .clip(CircleShape)
                    .background(MaterialTheme.colorScheme.primary),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = initialChar,
                    color = Color.White,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )
            }

            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(3.dp)
            ) {
                Text(
                    text = "سلام، $studentName",
                    fontSize = 19.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    text = dateText,
                    fontSize = 12.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }
    }
}

@Composable
private fun ExpressiveHeroStudyCard(
    token: SubjectDesignToken,
    subjectTitle: String,
    lessonTitle: String,
    onStart: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .testTag("home_hero_study_card"),
        shape = RoundedCornerShape(26.dp),
        colors = CardDefaults.cardColors(
            containerColor = token.containerColor
        ),
        border = BorderStroke(1.5.dp, token.accentColor.copy(alpha = 0.35f)),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(18.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Surface(
                shape = RoundedCornerShape(12.dp),
                color = token.accentColor.copy(alpha = 0.15f)
            ) {
                Row(
                    modifier = Modifier.padding(horizontal = 10.dp, vertical = 5.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    Icon(
                        imageVector = token.icon,
                        contentDescription = null,
                        tint = token.onContainerColor,
                        modifier = Modifier.size(16.dp)
                    )
                    Text(
                        text = subjectTitle,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                        color = token.onContainerColor
                    )
                }
            }

            Text(
                text = lessonTitle,
                fontSize = 19.sp,
                fontWeight = FontWeight.Bold,
                color = token.onContainerColor,
                lineHeight = 27.sp,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )

            Button(
                onClick = onStart,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp)
                    .testTag("start_study_btn"),
                shape = RoundedCornerShape(16.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = token.accentColor,
                    contentColor = Color.White
                )
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.PlayArrow,
                        contentDescription = null,
                        modifier = Modifier.size(22.dp)
                    )
                    Text(
                        text = "شروع مطالعه گام‌به‌گام",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    }
}

@Composable

/**
 * دکمه‌های قدم‌به‌قدم زیر هر بخش درس: «یاد گرفتم» بخش بعد را باز می‌کند،
 * «یاد نگرفتم» همان بخش را برای دوباره‌خواندن نشان می‌دهد.
 */
@Composable
private fun StepLearnGateCard(
    stepNumber: Int,
    totalSteps: Int,
    accentColor: Color,
    onLearned: () -> Unit,
    onNotLearned: () -> Unit
) {
    Surface(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(18.dp),
        color = HomeSurface(),
        border = BorderStroke(1.dp, accentColor.copy(alpha = 0.45f))
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Text(
                text = "بخش " + BidiFormatter.toPersianDigits(stepNumber.toString()) +
                    " از " + BidiFormatter.toPersianDigits(totalSteps.toString()) + " تمام شد — یاد گرفتی؟",
                style = MaterialTheme.typography.titleSmall,
                fontWeight = FontWeight.Bold,
                color = HomeText
            )
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                Button(
                    onClick = onLearned,
                    modifier = Modifier.weight(1f),
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = accentColor,
                        contentColor = Color.White
                    )
                ) {
                    Text(
                        text = "یاد گرفتم",
                        fontWeight = FontWeight.Bold,
                        maxLines = 1,
                        softWrap = false
                    )
                }
                OutlinedButton(
                    onClick = onNotLearned,
                    modifier = Modifier.weight(1f),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Text(
                        text = "یاد نگرفتم",
                        fontWeight = FontWeight.Bold,
                        maxLines = 1,
                        softWrap = false
                    )
                }
            }
        }
    }
}

/** همهٔ بخش‌های درس یاد گرفته شد — رفتن به مثال و تست. */
@Composable
private fun StepsCompletedCard(
    accentColor: Color
) {
    Surface(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(18.dp),
        color = HomeSurface(),
        border = BorderStroke(1.dp, accentColor.copy(alpha = 0.45f))
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "آفرین! همهٔ بخش‌های این درس را یاد گرفتی.",
                style = MaterialTheme.typography.titleSmall,
                fontWeight = FontWeight.Bold,
                color = HomeText
            )
            Text(
                text = "مثال و تست در ادامهٔ همین صفحه است.",
                style = MaterialTheme.typography.bodyMedium,
                color = HomeMuted
            )
        }
    }
}

/** نکات کلیدی همین درس: فرمول‌ها و قواعد اصلی + رمز هر گام. */
@Composable
private fun LessonOwnKeyPointsCard(
    lesson: ZeroLesson,
    accentColor: Color
) {
    Surface(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(18.dp),
        color = HomeSurface(),
        border = BorderStroke(1.dp, HomeBorder)
    ) {
        Column(
            modifier = Modifier.padding(18.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = Icons.Default.Star,
                    contentDescription = null,
                    tint = accentColor,
                    modifier = Modifier.size(18.dp)
                )
                Spacer(modifier = Modifier.width(6.dp))
                Text(
                    text = "نکات کلیدی این درس",
                    maxLines = 1,
                    softWrap = false,
                    style = MaterialTheme.typography.titleSmall,
                    fontWeight = FontWeight.Bold,
                    color = accentColor
                )
            }
            if (lesson.goldenFormulas.isNotEmpty()) {
                SmartDiagramAndFormulaBlock(
                    rawText = lesson.goldenFormulas.joinToString("\n"),
                    accentColor = accentColor
                )
            }
            lesson.steps.forEach { step ->
                if (step.memoryTrick.isNotBlank()) {
                    GoldenKeyPointCalloutCard(
                        keyPointText = step.memoryTrick,
                        title = "رمز گام ${step.stepNumber}",
                        level = GoldenPointLevel.autoDetect(step.memoryTrick)
                    )
                }
            }
        }
    }
}

/**
 * نکات کلیدی کتاب — سر جای خودش (صفحهٔ کتاب). راهنماهای تصویری کلی هر موضوع
 * که قبلاً داخل هر درس تکرار می‌شدند، حالا فقط اینجا هستند.
 */
@Composable
private fun SubjectBookKeyPointsCard(
    subjectId: String,
    accentColor: Color
) {
    Surface(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(18.dp),
        color = HomeSurface(),
        border = BorderStroke(1.dp, HomeBorder)
    ) {
        Column(
            modifier = Modifier.padding(18.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = Icons.Default.MenuBook,
                    contentDescription = null,
                    tint = accentColor,
                    modifier = Modifier.size(18.dp)
                )
                Spacer(modifier = Modifier.width(6.dp))
                Text(
                    text = "نکات کلیدی کتاب",
                    maxLines = 1,
                    softWrap = false,
                    style = MaterialTheme.typography.titleSmall,
                    fontWeight = FontWeight.Bold,
                    color = accentColor
                )
            }
            when (subjectId) {
                "arabic" -> {
                    com.example.ui.components.ArabicZeroToMasterVisualGuide(
                        accentColor = accentColor
                    )
                    Text(
                        text = "جدول کامل و استاندارد ۱۴ صیغه عربی (تطبیق ضمیر، ماضی، مضارع و معنی دقیق):",
                        style = MaterialTheme.typography.titleSmall,
                        fontWeight = FontWeight.Bold,
                        color = accentColor
                    )
                    com.example.ui.components.EducationalStructuredTable(
                        rows = MinusZeroLearningEngine.arabic14ConjugationGuideRows,
                        accentColor = accentColor
                    )
                }
                "english" -> {
                    com.example.ui.components.EnglishZeroToMasterVisualGuide(
                        accentColor = accentColor
                    )
                }
                "math" -> {
                    com.example.ui.components.MathDeepMasteryVisualGuide(
                        accentColor = accentColor
                    )
                }
                "fonoon" -> {
                    com.example.ui.components.FonoonAndAroozVisualGuide(
                        accentColor = accentColor
                    )
                }
                "arooz" -> {
                    com.example.ui.components.FonoonAndAroozVisualGuide(
                        accentColor = accentColor
                    )
                    com.example.ui.components.AroozMasteryInteractiveTrainer(
                        accentColor = accentColor
                    )
                }
                "history" -> {
                    com.example.ui.components.HistoryDynastyMemoryVault(
                        accentColor = accentColor
                    )
                }
                "economics" -> {
                    com.example.ui.components.EconomicsMathAndConceptsVisualGuide(
                        accentColor = accentColor
                    )
                }
                "philosophy", "logic" -> {
                    com.example.ui.components.PhilosophyAndLogicVisualGuide(
                        accentColor = accentColor
                    )
                }
            }
        }
    }
}

private fun LearningPathHeroCard(
    stepNumber: Int,
    stepTitle: String,
    nextLessonTitle: String?,
    completedSteps: Int,
    sectionLabel: String = "مسیر یادگیری ۱ تا ۱۰۰",
    totalSteps: Int = 100,
    onOpenPath: () -> Unit,
    onOpenNextLesson: () -> Unit
) {
    // گرادیان گرم و اشباع از پالت موجود برنامه (نارنجی/کهرباییِ تم و
    // SubjectCatalog) — متن‌ها و چیدمان کارت دست نخورده‌اند.
    // وقتی داینامیک کالر روشن است، گرادیان و سایهٔ کارت از رنگ‌های سیستم
    // (تصویر زمینه) می‌آید؛ وقتی خاموش است، همان نارنجی/کهرباییِ گرم می‌ماند.
    val heroScheme = MaterialTheme.colorScheme
    val heroUseDynamic = LocalAppThemeController.current.useDynamicColor
    // گرادیان داینامیک سیستم (سامسونگ) گاهی خیلی روشن است و متن سفید رویش خوانا نیست؛
    // رنگ‌ها را به سمت تیره می‌بریم تا کارت همیشه تیره و اشباع بماند.
    val darkenHero: (Color) -> Color = { c -> Color(c.red * 0.55f, c.green * 0.55f, c.blue * 0.55f, c.alpha) }
    val heroGradientColors = if (heroUseDynamic) {
        listOf(darkenHero(heroScheme.primary), darkenHero(heroScheme.secondary), darkenHero(heroScheme.tertiary))
    } else {
        listOf(Color(0xFFEA580C), Color(0xFFF97316), Color(0xFFF59E0B), Color(0xFFFBBF24))
    }
    val heroSpotColor = if (heroUseDynamic) heroScheme.primary else Color(0xFFEA580C)
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .shadow(
                elevation = 10.dp,
                shape = RoundedCornerShape(20.dp),
                spotColor = heroSpotColor.copy(alpha = 0.35f)
            )
            .clip(RoundedCornerShape(20.dp))
            .background(
                Brush.linearGradient(heroGradientColors)
            )
            .clickable(onClick = onOpenPath)
            .testTag("home_learning_path_hero_card"),
        shape = RoundedCornerShape(20.dp),
        color = Color.Transparent,
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.primary.copy(alpha = 0.35f))
    ) {
        // گرادیان چندلایه ملایم (روشنای سفید بالا + سایه ملایم پایین) و
        // دایره‌های نیمه‌شفاف — همان الگوی سربرگ درس؛ متن‌ها و چیدمان
        // کارت دست نخورده‌اند.
        Box(modifier = Modifier.fillMaxWidth()) {
            Canvas(modifier = Modifier.matchParentSize()) {
                drawCircle(
                    color = Color.White.copy(alpha = 0.14f),
                    radius = size.minDimension * 0.55f,
                    center = Offset(size.width * 0.92f, -size.height * 0.25f)
                )
                drawCircle(
                    color = Color.White.copy(alpha = 0.10f),
                    radius = size.minDimension * 0.38f,
                    center = Offset(size.width * 0.78f, size.height * 1.05f)
                )
                drawCircle(
                    color = Color.White.copy(alpha = 0.08f),
                    radius = size.minDimension * 0.22f,
                    center = Offset(size.width * 0.08f, size.height * 0.15f)
                )
            }
            Box(
                modifier = Modifier
                    .matchParentSize()
                    .background(
                        Brush.linearGradient(
                            colors = listOf(
                                Color.White.copy(alpha = 0.10f),
                                Color.Transparent,
                                Color.Black.copy(alpha = 0.06f)
                            )
                        )
                    )
            )
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(14.dp)
            ) {
                Box(
                    modifier = Modifier
                        .size(44.dp)
                        .clip(RoundedCornerShape(14.dp))
                        .background(MaterialTheme.colorScheme.primary),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.AltRoute,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.onPrimary,
                        modifier = Modifier.size(22.dp)
                    )
                }

                Column(
                    modifier = Modifier.weight(1f),
                    verticalArrangement = Arrangement.spacedBy(2.dp)
                ) {
                    Text(
                        text = sectionLabel,
                        fontSize = 11.sp,
                        color = Color.White,
                        fontWeight = FontWeight.Medium
                    )
                    Text(
                        text = "قدم " + BidiFormatter.toPersianDigits(stepNumber.toString()) +
                            " از " + BidiFormatter.toPersianDigits(totalSteps.toString()) +
                            ": " + stepTitle,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                    Text(
                        text = BidiFormatter.toPersianDigits(completedSteps.toString()) +
                            " قدم کامل شد",
                        fontSize = 12.sp,
                        color = Color.White,
                        fontWeight = FontWeight.Medium
                    )
                }

                Surface(
                    shape = RoundedCornerShape(10.dp),
                    color = MaterialTheme.colorScheme.primary
                ) {
                    Text(
                        text = "دیدن مسیر",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onPrimary,
                        modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp)
                    )
                }
            }

            if (nextLessonTitle != null) {
                Button(
                    onClick = onOpenNextLesson,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp)
                        .testTag("home_learning_path_next_lesson_btn"),
                    shape = RoundedCornerShape(14.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.primary,
                        contentColor = MaterialTheme.colorScheme.onPrimary
                    )
                ) {
                    // نشان پخش داخل دایره سفید نیمه‌شفاف — فقط زیباسازی
                    // دکمه؛ متن و عملکرد «درس بعدی» دست نخورده‌اند.
                    Box(
                        modifier = Modifier
                            .size(26.dp)
                            .clip(CircleShape)
                            .background(Color.White.copy(alpha = 0.20f)),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.PlayArrow,
                            contentDescription = null,
                            tint = Color.White,
                            modifier = Modifier.size(16.dp)
                        )
                    }
                    Text(
                        text = "درس بعدی: " + nextLessonTitle,
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Bold,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        modifier = Modifier.padding(start = 6.dp)
                    )
                }
            } else {
                Text(
                    text = "این قدم هنوز درس آماده ندارد؛ مسیر را باز کن تا ببینی چه مانده است.",
                    fontSize = 12.sp,
                    color = MaterialTheme.colorScheme.onPrimaryContainer
                )
            }
        }
        }
    }
}

/**
 * نوار باریک و گرم «امروز»: فقط یک خط — قدم فعلی مسیر ۱ تا ۱۰۰
 * (همان قدمی که کارت hero نشان می‌دهد) + تیتر قدم. بدون دکمه، بدون
 * بخش تازه؛ گرما از همان نارنجی/کهرباییِ پالت برنامه با آلفای کم می‌آید.
 */
/**
 * کارت راهنمای «از کجا شروع کنم؟» — فقط همین ۳ جمله، بیشتر نه؛
 * دکمهٔ × بستن را در Preference ذخیره می‌کند و کارت برنمی‌گردد.
 */
@Composable
private fun StartGuideCard(
    onDismiss: () -> Unit
) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .testTag("home_start_guide_card"),
        shape = RoundedCornerShape(20.dp),
        color = MaterialTheme.colorScheme.primaryContainer,
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.primary.copy(alpha = 0.35f))
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 12.dp),
            verticalAlignment = Alignment.Top,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                Text(
                    text = "۱) این جادهٔ توست — از قدم ۱ شروع کن.",
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onPrimaryContainer,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    text = "۲) هر روز فقط «درس بعدی» را بخوان.",
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onPrimaryContainer,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    text = "۳) تست‌ها بعد از هر درس می‌آیند.",
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onPrimaryContainer,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
            IconButton(
                onClick = onDismiss,
                modifier = Modifier
                    .size(32.dp)
                    .testTag("home_start_guide_dismiss_btn")
            ) {
                Icon(
                    imageVector = Icons.Default.Close,
                    contentDescription = "بستن راهنما",
                    tint = MaterialTheme.colorScheme.onPrimaryContainer,
                    modifier = Modifier.size(18.dp)
                )
            }
        }
    }
}

/**
 * «امروز این‌ها را بخوان» — ۲ تا ۳ ردیف تک‌خطی (شمارهٔ قدم + تیتر
 * درس)؛ زدن هر ردیف درس را باز می‌کند. وقتی فهرست خالی است این کارت
 * اصلا ساخته نمی‌شود (شرط در StudyFirstHomeScreen است).
 */
@Composable
private fun TodayLessonsCard(
    lessons: List<ZeroLesson>,
    stepNumbers: List<Int>,
    onOpenLesson: (String) -> Unit
) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .testTag("home_today_lessons_card"),
        shape = RoundedCornerShape(20.dp),
        color = MaterialTheme.colorScheme.surface,
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(
                text = "امروز این‌ها را بخوان",
                fontSize = 15.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            lessons.forEachIndexed { index, lesson ->
                val stepNumber = stepNumbers.getOrNull(index)
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(12.dp))
                        .clickable { onOpenLesson(lesson.id) }
                        .padding(horizontal = 10.dp, vertical = 8.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.PlayArrow,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.size(18.dp)
                    )
                    Text(
                        text = (if (stepNumber != null) "قدم " +
                            BidiFormatter.toPersianDigits(stepNumber.toString()) + " — " else "") +
                            lesson.title,
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Medium,
                        color = MaterialTheme.colorScheme.onSurface,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        modifier = Modifier.weight(1f)
                    )
                }
            }
        }
    }
}

@Composable
private fun TodayStepBar(
    stepNumber: Int,
    stepTitle: String,
    totalSteps: Int = 100
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(14.dp))
            .background(
                Brush.horizontalGradient(
                    listOf(
                        Color(0xFFEA580C).copy(alpha = 0.14f),
                        Color(0xFFF59E0B).copy(alpha = 0.14f)
                    )
                )
            )
            .testTag("home_today_bar")
            .padding(horizontal = 14.dp, vertical = 10.dp)
    ) {
        Text(
            text = "قدم امروز: قدم " +
                BidiFormatter.toPersianDigits(stepNumber.toString()) +
                " از " + BidiFormatter.toPersianDigits(totalSteps.toString()) +
                if (stepTitle.isBlank()) "" else " — " + stepTitle,
            fontSize = 13.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onSurface,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
    }
}

@Composable
private fun ExpressiveContinueStudyCard(
    subject: String,
    lesson: String,
    token: SubjectDesignToken,
    onContinue: () -> Unit
) {
    // زیباسازی فقط: گرادیان آماده توکن موضوع (به‌جای سطح تک‌رنگ) +
    // سایه ملایم به رنگ لهجه موضوع؛ متن‌ها، تیترها و چیدمان دست نخورده‌اند.
    // وقتی داینامیک کالر روشن است، گرادیان و لهجهٔ این کارت از رنگ‌های سیستم
    // (تصویر زمینه) می‌آید؛ وقتی خاموش است، همان توکن رنگ موضوع می‌ماند.
    val continueScheme = MaterialTheme.colorScheme
    val continueUseDynamic = LocalAppThemeController.current.useDynamicColor
    val cardAccent = if (continueUseDynamic) continueScheme.primary else token.accentColor
    val cardBrush = if (continueUseDynamic) {
        Brush.linearGradient(listOf(continueScheme.primaryContainer, continueScheme.secondaryContainer))
    } else {
        token.gradientBrush
    }
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .shadow(
                elevation = 6.dp,
                shape = RoundedCornerShape(20.dp),
                spotColor = cardAccent.copy(alpha = 0.25f)
            )
            .clip(RoundedCornerShape(20.dp))
            .background(cardBrush)
            .clickable(onClick = onContinue)
            .testTag("continue_study_card"),
        shape = RoundedCornerShape(20.dp),
        color = Color.Transparent,
        border = BorderStroke(1.dp, cardAccent.copy(alpha = 0.25f))
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            Box(
                modifier = Modifier
                    .size(44.dp)
                    .clip(RoundedCornerShape(14.dp))
                    .background(cardAccent.copy(alpha = 0.16f)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = token.icon,
                    contentDescription = null,
                    tint = cardAccent,
                    modifier = Modifier.size(22.dp)
                )
            }

            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(2.dp)
            ) {
                Text(
                    text = "آخرین درس مطالعه‌شده",
                    fontSize = 11.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    fontWeight = FontWeight.Medium
                )
                Text(
                    text = lesson,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    text = subject,
                    fontSize = 12.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    fontWeight = FontWeight.SemiBold
                )
            }

            Surface(
                shape = RoundedCornerShape(10.dp),
                color = MaterialTheme.colorScheme.primaryContainer
            ) {
                Row(
                    modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    Text(
                        text = "ادامه",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onPrimaryContainer
                    )
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.onPrimaryContainer,
                        modifier = Modifier.size(14.dp)
                    )
                }
            }
        }
    }
}

@Composable
private fun ExpressiveAcademicProgressDashboard(
    dailyProgress: Int,
    completedLessons: Int,
    totalLessons: Int,
    masteredCards: Int
) {
    // پیش‌فرض جمع‌شده: خانه خلوت می‌ماند؛ جزئیات پشت یک ضربه است.
    var expanded by remember { mutableStateOf(false) }
    val pctFa = BidiFormatter.applyPreferredDigits(dailyProgress.coerceIn(0, 100).toString())
    val completedFa = BidiFormatter.applyPreferredDigits(completedLessons.toString())
    val totalFa = BidiFormatter.applyPreferredDigits(totalLessons.toString())
    val masteredFa = BidiFormatter.applyPreferredDigits(masteredCards.toString())

    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .testTag("home_academic_progress_card"),
        shape = RoundedCornerShape(22.dp),
        color = MaterialTheme.colorScheme.surface,
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .animateContentSize(animationSpec = JellyContentSizeSpring)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.TrendingUp,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.size(20.dp)
                    )
                    Text(
                        text = "رادار ارتقای تراز و تسلط",
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                }

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Surface(
                        shape = RoundedCornerShape(8.dp),
                        color = MaterialTheme.colorScheme.primaryContainer
                    ) {
                        Text(
                            text = "$pctFa٪ پیشرفت روزانه",
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onPrimaryContainer,
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp)
                        )
                    }

                    Surface(
                        shape = RoundedCornerShape(8.dp),
                        color = MaterialTheme.colorScheme.secondaryContainer,
                        modifier = Modifier.clickable { expanded = !expanded }
                    ) {
                        Row(
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(2.dp)
                        ) {
                            Text(
                                text = if (expanded) "نمایش کمتر" else "نمایش بیشتر",
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.onSecondaryContainer
                            )
                            Icon(
                                imageVector = if (expanded) Icons.Default.ExpandLess else Icons.Default.ExpandMore,
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.onSecondaryContainer,
                                modifier = Modifier.size(16.dp)
                            )
                        }
                    }
                }
            }

            if (expanded) {
                val safeProgress = dailyProgress.coerceIn(0, 100) / 100f
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(8.dp)
                        .clip(CircleShape)
                        .background(MaterialTheme.colorScheme.surfaceVariant)
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth(safeProgress)
                            .fillMaxSize()
                            .clip(CircleShape)
                            .background(
                                Brush.horizontalGradient(
                                    listOf(
                                        MaterialTheme.colorScheme.primary,
                                        MaterialTheme.colorScheme.tertiary
                                    )
                                )
                            )
                    )
                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    StatPillar(
                        title = "مباحث تکمیل",
                        value = "$completedFa از $totalFa"
                    )
                    Box(
                        modifier = Modifier
                            .width(1.dp)
                            .height(34.dp)
                            .background(MaterialTheme.colorScheme.outlineVariant)
                    )
                    StatPillar(
                        title = "فلش‌کارت مسلط",
                        value = "$masteredFa کارت"
                    )
                    Box(
                        modifier = Modifier
                            .width(1.dp)
                            .height(34.dp)
                            .background(MaterialTheme.colorScheme.outlineVariant)
                    )
                    StatPillar(
                        title = "تراز هدف",
                        value = "+۸۰۰۰"
                    )
                }
            }
        }
    }
}

@Composable
private fun StatPillar(title: String, value: String) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(2.dp)
    ) {
        Text(
            text = value,
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onSurface
        )
        Text(
            text = title,
            fontSize = 11.sp,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}

@Composable
private fun ExpressiveQuickToolsGrid(
    onOpenPlanner: () -> Unit,
    onOpenTests: () -> Unit,
    onOpenLab: () -> Unit,
    onOpenRoadmap: () -> Unit
) {
    // پیش‌فرض جمع‌شده: هر ۴ ابزار پشت یک ضربه می‌مانند، هیچ‌کدام حذف نمی‌شود.
    var expanded by remember { mutableStateOf(false) }
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .animateContentSize(animationSpec = JellyContentSizeSpring),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "جعبه‌ابزار هوشمند داوطلب",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onBackground
            )

            Surface(
                shape = RoundedCornerShape(8.dp),
                color = MaterialTheme.colorScheme.secondaryContainer,
                modifier = Modifier.clickable { expanded = !expanded }
            ) {
                Row(
                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(2.dp)
                ) {
                    Text(
                        text = if (expanded) "نمایش کمتر" else "نمایش بیشتر",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSecondaryContainer
                    )
                    Icon(
                        imageVector = if (expanded) Icons.Default.ExpandLess else Icons.Default.ExpandMore,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.onSecondaryContainer,
                        modifier = Modifier.size(16.dp)
                    )
                }
            }
        }

        if (expanded) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                ExpressiveToolCard(
                    modifier = Modifier.weight(1f),
                    title = "برنامه‌ریزی",
                    subtitle = "تقویم و وظایف",
                    icon = Icons.Default.CalendarMonth,
                    tint = Color(0xFF006A60),
                    onClick = onOpenPlanner
                )
                ExpressiveToolCard(
                    modifier = Modifier.weight(1f),
                    title = "آزمون‌ساز",
                    subtitle = "تست و لایتنر",
                    icon = Icons.Default.Quiz,
                    tint = Color(0xFF886820),
                    onClick = onOpenTests
                )
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                ExpressiveToolCard(
                    modifier = Modifier.weight(1f),
                    title = "آزمایشگاه",
                    subtitle = "تقطیع عروضی",
                    icon = Icons.Default.Science,
                    tint = Color(0xFF7250A1),
                    onClick = onOpenLab
                )
                ExpressiveToolCard(
                    modifier = Modifier.weight(1f),
                    title = "نقشه راه",
                    subtitle = "مسیر تا کنکور",
                    icon = Icons.Default.AltRoute,
                    tint = Color(0xFF243B63),
                    onClick = onOpenRoadmap
                )
            }
        }
    }
}

@Composable
private fun ExpressiveToolCard(
    modifier: Modifier = Modifier,
    title: String,
    subtitle: String,
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    tint: Color,
    onClick: () -> Unit
) {
    Surface(
        modifier = modifier
            .clip(RoundedCornerShape(18.dp))
            .clickable(onClick = onClick)
            .height(82.dp),
        shape = RoundedCornerShape(18.dp),
        color = MaterialTheme.colorScheme.surface,
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant)
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 12.dp, vertical = 10.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Box(
                modifier = Modifier
                    .size(42.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .background(tint.copy(alpha = 0.14f)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    tint = tint,
                    modifier = Modifier.size(22.dp)
                )
            }

            Column(
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = title,
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface,
                    maxLines = 1
                )
                Text(
                    text = subtitle,
                    fontSize = 10.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    maxLines = 1
                )
            }
        }
    }
}

@Composable
private fun ExpressiveSubjectCard(
    subject: StudySubjectUi,
    isDark: Boolean,
    onClick: () -> Unit
) {
    // فهرست تک‌خطی (قانون کاربر): فقط تیتر، یک خط؛ بدون آیکون رنگی،
    // بدون زیرتیتر، بدون چیپ درصد و بدون نوار پیشرفت. شمارش و درصد
    // داخل صفحه درس و «گزارش پیشرفت» دیده می‌شوند، نه در ردیف فهرست.
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .clickable(onClick = onClick)
            .testTag("home_subject_card_${subject.id}"),
        shape = RoundedCornerShape(16.dp),
        color = MaterialTheme.colorScheme.surface,
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 14.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Text(
                text = subject.title,
                fontSize = 15.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier.weight(1f)
            )
            Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.size(18.dp)
            )
        }
    }
}

@Composable
fun SubjectListItem(
    subject: StudySubjectUi,
    onClick: () -> Unit
) {
    val isDark = androidx.compose.foundation.isSystemInDarkTheme()
    val themeController = LocalAppThemeController.current
    val effectiveDark = themeController.resolveIsDarkTheme(isDark)
    ExpressiveSubjectCard(
        subject = subject,
        isDark = effectiveDark,
        onClick = onClick
    )
}

@Composable
fun SimpleProgressBar(
    value: Int
) {
    val safeProgress = value.coerceIn(0, 100) / 100f
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(7.dp)
            .clip(CircleShape)
            .background(MaterialTheme.colorScheme.surfaceVariant)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth(safeProgress)
                .fillMaxSize()
                .clip(CircleShape)
                .background(
                    Brush.horizontalGradient(
                        listOf(
                            MaterialTheme.colorScheme.primary,
                            MaterialTheme.colorScheme.tertiary
                        )
                    )
                )
        )
    }
}

/**
 * فهرست کتابی تک‌خطی (قانون کاربر): هر فصل یک کارت جمع‌وجور، هر درس
 * فقط «شماره + تیتر» در یک خط (maxLines=1)؛ بدون زیرتیتر، بدون چیپ،
 * بدون بج و بدون نوار پیشرفت. نشان‌کردن و وضعیت تکمیل داخل صفحه درس
 * باقی است (دکمه نشان در سربرگ درس و بنر نشان در معرفی درس).
 * جایگزین سبک CleanTextbookTableOfContentsCard در همین فایل؛ آن
 * کامپوننت در فایل خودش دست نخورد و فقط فراخوانی‌اش عوض شد.
 */
@Composable
private fun MinimalBookTocCard(
    chapterTitle: String,
    lessons: List<ZeroLesson>,
    onSelectLesson: (ZeroLesson) -> Unit,
    accentColor: Color = MaterialTheme.colorScheme.primary
) {
    var isExpanded by remember { mutableStateOf(true) }
    val countFa = BidiFormatter.toPersianDigits(lessons.size.toString())
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .shadow(
                elevation = 4.dp,
                shape = RoundedCornerShape(16.dp),
                spotColor = accentColor.copy(alpha = 0.18f)
            )
            .testTag("textbook_toc_card"),
        shape = RoundedCornerShape(16.dp),
        color = HomeSurface(),
        border = BorderStroke(1.dp, HomeBorder)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 8.dp),
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            // سربرگ موضوع با گرادیان رنگ همان موضوع (accentColor از
            // SubjectCatalog): نوار ملایم + تیتر و چیپ شمارش به رنگ موضوع.
            // تیتر تک‌خطی و رفتار باز/بسته‌شدن دست نخورده‌اند.
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(10.dp))
                    .background(
                        Brush.horizontalGradient(
                            listOf(
                                accentColor.copy(alpha = 0.16f),
                                accentColor.copy(alpha = 0.05f),
                                Color.Transparent
                            )
                        )
                    )
                    .clickable { isExpanded = !isExpanded }
                    .padding(horizontal = 10.dp, vertical = 8.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text(
                    text = BidiFormatter.formatMixedPersianText(chapterTitle),
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Bold,
                    color = accentColor,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.weight(1f)
                )
                Surface(
                    shape = RoundedCornerShape(8.dp),
                    color = accentColor.copy(alpha = 0.12f)
                ) {
                    Text(
                        text = "$countFa درس",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                        color = accentColor,
                        maxLines = 1,
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp)
                    )
                }
                Icon(
                    imageVector = if (isExpanded) Icons.Default.ExpandLess else Icons.Default.ExpandMore,
                    contentDescription = null,
                    tint = accentColor,
                    modifier = Modifier.size(20.dp)
                )
            }
            if (isExpanded) {
                lessons.forEachIndexed { idx, lesson ->
                    val itemNumFa = BidiFormatter.toPersianDigits((idx + 1).toString())
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(8.dp))
                            .clickable { onSelectLesson(lesson) }
                            .testTag("lesson_card_${lesson.id}")
                            .padding(vertical = 9.dp, horizontal = 4.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        // نقطه رنگی کوچکِ موضوعِ درس — تیتر تک‌خطی می‌ماند.
                        Box(
                            modifier = Modifier
                                .size(8.dp)
                                .clip(CircleShape)
                                .background(accentColor)
                        )
                        Text(
                            text = itemNumFa,
                            fontSize = 13.sp,
                            fontWeight = FontWeight.Bold,
                            color = HomePrimary,
                            maxLines = 1
                        )
                        Text(
                            text = BidiFormatter.formatMixedPersianText(lesson.title),
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Medium,
                            color = HomeText,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                            modifier = Modifier.weight(1f)
                        )
                    }
                    if (idx < lessons.lastIndex) {
                        // جداکننده ظریف به رنگ موضوع، با کمی تورفتگی از دو طرف.
                        HorizontalDivider(
                            modifier = Modifier.padding(horizontal = 4.dp),
                            thickness = 0.5.dp,
                            color = accentColor.copy(alpha = 0.14f)
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun HomeSurface(): Color = MaterialTheme.colorScheme.surface

/** فنر ژله‌ای مشترک برای باز/بسته شدن نرم کارت‌ها: نرم و روغنی، نه خشک. */
private val JellyContentSizeSpring = spring<IntSize>(
    stiffness = Spring.StiffnessLow,
    dampingRatio = 0.7f
)

object LessonSpacing {
    val paragraph = 12.dp
    val heading = 20.dp
    val section = 28.dp
    val example = 16.dp
    val table = 20.dp
    val media = 20.dp
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun ZeroLessonsScreen(
    uiState: KonkoorUiState,
    onSelectSubject: (String) -> Unit,
    onOpenLesson: (String?) -> Unit,
    onSaveLessonState: (ZeroLesson, Boolean, Boolean, String, Int) -> Unit,
    /** نتیجه مینی‌چک انتهای درس (فقط اولین پاسخ)؛ null یعنی ثبت نمی‌شود. */
    onMiniCheckAnswered: ((ZeroLesson, Boolean) -> Unit)? = null
) {
    val activeLesson = uiState.allLessons.find { it.id == uiState.activeLessonId }
    var activePdfBook by remember { mutableStateOf<Pair<OfficialTextbookPdf, List<ZeroLesson>>?>(null) }

    activePdfBook?.let { (bookMeta, bookLessons) ->
        val bookSubject = SubjectCatalog.getSubjectById(bookMeta.subjectId)
        InAppTextbookPdfViewerDialog(
            book = bookMeta,
            lessons = bookLessons,
            accentColor = bookSubject.color,
            onDismiss = { activePdfBook = null },
            onSelectLessonFromToc = { lessonId ->
                activePdfBook = null
                onOpenLesson(lessonId)
            }
        )
    }

    if (activeLesson != null) {
        LessonDetailView(
            lesson = activeLesson,
            allLessons = uiState.allLessons,
            progress = uiState.lessonProgressMap[activeLesson.id],
            onBack = { onOpenLesson(null) },
            onNavigateToLesson = { targetId -> onOpenLesson(targetId) },
            onSaveState = { isCompleted, isBookmarked, note, mastery ->
                onSaveLessonState(activeLesson, isCompleted, isBookmarked, note, mastery)
            },
            onMiniCheckAnswered = onMiniCheckAnswered
        )
        return
    }

    val filteredLessons = remember(uiState.allLessons, uiState.selectedSubjectId) {
        if (uiState.selectedSubjectId == "all") {
            emptyList()
        } else {
            uiState.allLessons
                .filter { it.normalizedSubjectId() == uiState.selectedSubjectId }
                .sortedWith(
                    compareBy<ZeroLesson>(
                        { it.subcategoryFolder().first },
                        { it.orderIndex }
                    )
                )
        }
    }
    val currentSubject = remember(uiState.selectedSubjectId) {
        SubjectCatalog.getSubjectById(uiState.selectedSubjectId)
    }

    var lessonSearchQuery by remember(uiState.selectedSubjectId) {
        mutableStateOf("")
    }

    val displayedLessons = remember(filteredLessons, lessonSearchQuery) {
        val q = lessonSearchQuery.trim()
        if (q.isEmpty()) {
            filteredLessons
        } else {
            filteredLessons.filter { lesson ->
                lesson.title.contains(q, ignoreCase = true) ||
                    lesson.subtitle.contains(q, ignoreCase = true) ||
                    lesson.officialTextbookReference().contains(q, ignoreCase = true)
            }
        }
    }

    Surface(
        modifier = Modifier
            .fillMaxSize()
            .testTag("zero_lessons_root"),
        color = HomeBackground
    ) {
        if (uiState.selectedSubjectId == "all") {
            var subjectSearchQuery by remember { mutableStateOf("") }
            val filteredSubjects = remember(uiState.subjects, subjectSearchQuery) {
                val q = subjectSearchQuery.trim()
                if (q.isEmpty()) {
                    uiState.subjects
                } else {
                    uiState.subjects.filter { sub ->
                        sub.title.contains(q, ignoreCase = true) ||
                            sub.shortTitle.contains(q, ignoreCase = true) ||
                            sub.subtitle.contains(q, ignoreCase = true)
                    }
                }
            }

            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .testTag("lessons_lazy_column"),
                contentPadding = PaddingValues(
                    horizontal = 20.dp,
                    vertical = 20.dp
                ),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                item(key = "lessons_catalog_header") {
                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        verticalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        Text(
                            text = "فهرست درس‌ها",
                            fontSize = 22.sp,
                            fontWeight = FontWeight.Bold,
                            color = HomeText
                        )
                        Text(
                            text = BidiFormatter.applyPreferredDigits("${uiState.subjects.size} درس • ${uiState.totalLessonsCount} مبحث — روی یک درس بزنید."),
                            fontSize = 12.sp,
                            color = HomeMuted,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                    }
                }

                item(key = "lessons_catalog_search") {
                    OutlinedTextField(
                        value = subjectSearchQuery,
                        onValueChange = { subjectSearchQuery = it },
                        placeholder = {
                            Text(
                                text = "جستجوی درس…",
                                fontSize = 13.sp,
                                color = HomeMuted
                            )
                        },
                        singleLine = true,
                        shape = RoundedCornerShape(14.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(HomeSurface(), RoundedCornerShape(14.dp))
                            .testTag("lessons_search_input")
                    )
                }

                items(filteredSubjects, key = { it.id }) { subject ->
                    val subjectLessonsCount = remember(uiState.allLessons, subject.id) {
                        uiState.allLessons.count { it.normalizedSubjectId() == subject.id }
                    }
                    val countFa = BidiFormatter.toPersianDigits(subjectLessonsCount.toString())
                    val cleanTitle = subject.shortTitle.ifBlank { subject.title.substringAfter(". ") }
                    val pct = (uiState.subjectProgressFraction(subject.id) * 100).toInt().coerceIn(0, 100)

                    SubjectListItem(
                        subject = StudySubjectUi(
                            id = subject.id,
                            title = BidiFormatter.applyPreferredDigits(cleanTitle),
                            subtitle = BidiFormatter.applyPreferredDigits("$countFa مبحث آموزشی • ${subject.subtitle}"),
                            progress = pct
                        ),
                        onClick = { onSelectSubject(subject.id) }
                    )
                }
            }
        } else {
            // SUBJECT DETAIL PAGE: Chapters -> Topics -> Lesson content, Key notes, Examples, Practice questions
            val cleanSubjectTitle = currentSubject.shortTitle.ifBlank {
                currentSubject.title.substringBefore(" (").substringAfter(". ").trim()
            }
            val countFa = BidiFormatter.toPersianDigits(displayedLessons.size.toString())
            val subjectBooks = remember(currentSubject.id) {
                TextbookPdfCatalog.getBooksForSubject(currentSubject.id)
            }
            val primaryBookMeta = remember(currentSubject.id, subjectBooks) {
                subjectBooks.firstOrNull() ?: TextbookPdfCatalog.getBookForSubjectAndGrade(currentSubject.id, 4)
            }


            val lessonsByChapter = remember(displayedLessons) {
                displayedLessons
                    .sortedWith(
                        compareBy<ZeroLesson>(
                            { it.subcategoryFolder().first },
                            { it.orderIndex }
                        )
                    )
                    .groupBy { lesson ->
                        val (gradeNum, folderName) = lesson.subcategoryFolder()
                        folderName.ifBlank {
                            when (gradeNum) {
                                1 -> "مبانی پایه و پیش‌نیاز"
                                2 -> "کتاب پایه دهم"
                                3 -> "کتاب پایه یازدهم"
                                4 -> "کتاب پایه دوازدهم"
                                else -> "سرفصل‌های آموزشی"
                            }
                        }
                    }
            }

            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .testTag("lessons_lazy_column"),
                contentPadding = PaddingValues(
                    start = 20.dp,
                    top = 10.dp,
                    end = 20.dp,
                    bottom = 88.dp
                ),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                item(key = "subject_header_card") {
                // Clean Minimalist Subject Header Card
                Surface(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 12.dp),
                    color = HomeSurface(),
                    shape = RoundedCornerShape(18.dp),
                    border = BorderStroke(1.dp, HomeBorder)
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier
                                    .weight(1f)
                                    .clip(RoundedCornerShape(8.dp))
                                    .clickable { onSelectSubject("all") }
                                    .testTag("back_to_all_subjects_bar")
                            ) {
                                Icon(
                                    Icons.AutoMirrored.Filled.ArrowBack,
                                    contentDescription = "بازگشت به فهرست دروس",
                                    tint = HomePrimary,
                                    modifier = Modifier.size(20.dp)
                                )
                                Spacer(modifier = Modifier.width(10.dp))
                                Column {
                                    Text(
                                        text = BidiFormatter.formatMixedPersianText(cleanSubjectTitle),
                                        fontSize = 18.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = HomeText
                                    )
                                    Text(
                                        text = BidiFormatter.applyPreferredDigits("$countFa مبحث آموزشی"),
                                        fontSize = 12.sp,
                                        color = HomeMuted
                                    )
                                }
                            }

                            Surface(
                                modifier = Modifier
                                    .clip(RoundedCornerShape(10.dp))
                                    .clickable {
                                        activePdfBook = primaryBookMeta to displayedLessons
                                    },
                                color = HomeBackground,
                                shape = RoundedCornerShape(10.dp),
                                border = BorderStroke(1.dp, HomeBorder)
                            ) {
                                Row(
                                    modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.PictureAsPdf,
                                        contentDescription = "PDF کتاب",
                                        tint = HomePrimary,
                                        modifier = Modifier.size(15.dp)
                                    )
                                    Spacer(modifier = Modifier.width(4.dp))
                                    Text(
                                        text = "کتاب درسی",
                                        fontSize = 11.sp,
                                        fontWeight = FontWeight.SemiBold,
                                        color = HomePrimary
                                    )
                                }
                            }
                        }
                    }
                }
                }

                // (0) فصل‌ها و مباحث — زیرمجموعهٔ همین کتاب
                item(key = "book_section_chapters_header") {
                    BookPageSectionHeader(
                        title = "فصل‌ها و مباحث",
                        accentColor = currentSubject.color
                    )
                }

                // کارت شمارش بالای فهرست: «N درس در M فصل» با
                // گرادیان رنگ موضوع؛ فقط شمارش است و ردیفی
                // اضافه یا کم نمی‌کند.
                item(key = "subject_lesson_count_card") {
                    val chaptersFa = BidiFormatter.toPersianDigits(lessonsByChapter.size.toString())
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(14.dp))
                            .background(
                                Brush.horizontalGradient(
                                    listOf(
                                        currentSubject.color.copy(alpha = 0.16f),
                                        currentSubject.color.copy(alpha = 0.05f)
                                    )
                                )
                            )
                            .testTag("subject_lesson_count_card")
                            .padding(horizontal = 14.dp, vertical = 12.dp)
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(10.dp)
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(36.dp)
                                    .clip(CircleShape)
                                    .background(currentSubject.color.copy(alpha = 0.18f)),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(
                                    imageVector = Icons.Default.MenuBook,
                                    contentDescription = null,
                                    tint = currentSubject.color,
                                    modifier = Modifier.size(20.dp)
                                )
                            }
                            Text(
                                text = "$countFa درس در $chaptersFa فصل",
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Bold,
                                color = HomeText,
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis
                            )
                        }
                    }
                }

                // نکات کلیدی کتاب (سر جای خودش: صفحهٔ کتاب، نه داخل هر درس)
                if (currentSubject.id in listOf("arabic", "english", "math", "fonoon", "arooz", "history", "economics", "philosophy", "logic")) {
                    item(key = "subject_book_key_points") {
                        SubjectBookKeyPointsCard(
                            subjectId = currentSubject.id,
                            accentColor = currentSubject.color
                        )
                    }
                }

                item(key = "subject_topic_search") {
                    OutlinedTextField(
                        value = lessonSearchQuery,
                        onValueChange = { lessonSearchQuery = it },
                        placeholder = {
                            Text(
                                BidiFormatter.formatMixedPersianText("جستجوی مبحث در $cleanSubjectTitle..."),
                                fontSize = 13.sp,
                                color = HomeMuted
                            )
                        },
                        singleLine = true,
                        shape = RoundedCornerShape(14.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(HomeSurface(), RoundedCornerShape(14.dp))
                            .testTag("lessons_search_input")
                    )
                }

                items(lessonsByChapter.keys.toList(), key = { it }) { chapterTitle ->
                    val chapterLessons = lessonsByChapter[chapterTitle] ?: emptyList()
                    MinimalBookTocCard(
                        chapterTitle = chapterTitle,
                        lessons = chapterLessons,
                        onSelectLesson = { lesson -> onOpenLesson(lesson.id) },
                        accentColor = currentSubject.color
                    )
                }

                // (2) سوالات تمرینی
                item(key = "book_section_testbank_header") {
                    BookPageSectionHeader(
                        title = "سوالات تمرینی",
                        accentColor = currentSubject.color
                    )
                }
                item(key = "book_section_testbank") {
                    SubjectTestBankView(
                        subjectId = currentSubject.id,
                        subjectTitle = cleanSubjectTitle,
                        accentColor = HomePrimary,
                        lessons = displayedLessons
                    )
                }

                // (3) فلش‌کارت‌ها
                item(key = "book_section_flashcards_header") {
                    BookPageSectionHeader(
                        title = "فلش‌کارت‌ها",
                        accentColor = currentSubject.color
                    )
                }
                item(key = "book_section_flashcards") {
                    SubjectFlashcardsView(
                        subjectId = currentSubject.id,
                        subjectTitle = cleanSubjectTitle,
                        accentColor = HomePrimary
                    )
                }

                // (1) امتحان نهایی
                item(key = "book_section_nahaee_header") {
                    BookPageSectionHeader(
                        title = "امتحان نهایی",
                        accentColor = currentSubject.color
                    )
                }
                item(key = "book_section_nahaee") {
                    SubjectNahaeeExamsView(
                        subjectId = currentSubject.id,
                        subjectTitle = cleanSubjectTitle,
                        accentColor = HomePrimary
                    )
                }

                // (4) نکات و روش مطالعه
                item(key = "book_section_method_header") {
                    BookPageSectionHeader(
                        title = "نکات و روش مطالعه",
                        accentColor = currentSubject.color
                    )
                }
                item(key = "book_section_method") {
                    SubjectStudyMethodView(
                        subjectId = currentSubject.id,
                        subjectTitle = cleanSubjectTitle,
                        accentColor = HomePrimary,
                        lessons = displayedLessons
                    )
                }
            }
        }
    }
}

@Composable
private fun LessonDetailView(
    lesson: ZeroLesson,
    allLessons: List<ZeroLesson>,
    progress: com.example.data.firebase.CloudLessonProgress?,
    onBack: () -> Unit,
    onNavigateToLesson: (String) -> Unit = {},
    onSaveState: (isCompleted: Boolean, isBookmarked: Boolean, userNote: String, masteryPercent: Int) -> Unit,
    /** نتیجه مینی‌چک انتهای درس (فقط اولین پاسخ هر بار باز شدن درس)؛ null یعنی ثبت نمی‌شود. */
    onMiniCheckAnswered: ((ZeroLesson, Boolean) -> Unit)? = null
) {
    // ورق زدن چپ/راست بین درس‌های همان subject با HorizontalPager؛ دکمه‌های
    // «مبحث قبلی / مبحث بعدی» داخل صفحه هم هست و همان onNavigateToLesson را صدا می‌زند.
    // در چیدمان راست‌به‌چپ، ورق به سمت چپ = درس بعدی (شماره صفحه بالاتر) است.
    val subjectOrderedLessons = remember(lesson.id, allLessons) {
        allLessons.filter { it.normalizedSubjectId() == lesson.normalizedSubjectId() }
            .sortedBy { it.orderIndex }
    }
    val currentLessonIdx = remember(lesson.id, subjectOrderedLessons) {
        subjectOrderedLessons.indexOfFirst { it.id == lesson.id }.coerceAtLeast(0)
    }
    val pagerState = rememberPagerState(
        initialPage = currentLessonIdx,
        pageCount = { subjectOrderedLessons.size.coerceAtLeast(1) }
    )
    // همگام‌سازی پیجر وقتی درس از بیرون عوض شد (دکمه‌های قبلی/بعدی یا لینک پیش‌نیاز):
    // با انیمیشن به صفحه درس تازه می‌لغزد.
    LaunchedEffect(currentLessonIdx) {
        if (pagerState.currentPage != currentLessonIdx) {
            pagerState.animateScrollToPage(currentLessonIdx)
        }
    }
    // وقتی کاربر با سوایپ ورق زد، درس فعال را عوض کن تا ذخیره وضعیت/نشان درست بماند.
    LaunchedEffect(pagerState) {
        snapshotFlow { pagerState.currentPage }.collect { page ->
            if (page != currentLessonIdx) {
                subjectOrderedLessons.getOrNull(page)?.let { onNavigateToLesson(it.id) }
            }
        }
    }
    HorizontalPager(
        state = pagerState,
        modifier = Modifier.fillMaxSize()
    ) { page ->
        val pageLesson = subjectOrderedLessons.getOrNull(page) ?: lesson
        LessonDetailPageContent(
            lesson = pageLesson,
            allLessons = allLessons,
            // صفحه همسایه هنگام ورق خوردن هنوز progress درس تازه را ندارد؛
            // بعد از ناوبری، والد progress درست را می‌دهد و state داخلی با کلید id تازه می‌شود.
            progress = if (pageLesson.id == lesson.id) progress else null,
            onBack = onBack,
            onNavigateToLesson = onNavigateToLesson,
            onSaveState = onSaveState,
            onMiniCheckAnswered = onMiniCheckAnswered
        )
    }
}

@Composable
private fun LessonDetailPageContent(
    lesson: ZeroLesson,
    allLessons: List<ZeroLesson>,
    progress: com.example.data.firebase.CloudLessonProgress?,
    onBack: () -> Unit,
    onNavigateToLesson: (String) -> Unit = {},
    onSaveState: (isCompleted: Boolean, isBookmarked: Boolean, userNote: String, masteryPercent: Int) -> Unit,
    /** نتیجه مینی‌چک انتهای درس (فقط اولین پاسخ هر بار باز شدن درس)؛ null یعنی ثبت نمی‌شود. */
    onMiniCheckAnswered: ((ZeroLesson, Boolean) -> Unit)? = null
) {
    BackHandler { onBack() }

    val enrichedLesson = remember(lesson.id) {
        FlashcardAndQuizBank.getEnrichedLesson(lesson)
    }

    val themeController = LocalAppThemeController.current
    val subject = SubjectCatalog.getSubjectById(enrichedLesson.normalizedSubjectId())
    // رنگ لهجه درس: وقتی داینامیک کالر روشن است، رنگ اصلی سیستم (تصویر زمینه)
    // می‌آید و سربرگ، نوار پیشرفت، قاب نمودارها و همهٔ لهجه‌های صفحهٔ درس را
    // می‌گیرد؛ وقتی خاموش است، رنگ ثابت موضوع (از SubjectCatalog) برمی‌گردد.
    val lessonAccent = resolveDynamicAccent(subject.color)
    var isCompleted by remember(enrichedLesson.id, progress?.isCompleted) {
        mutableStateOf(progress?.isCompleted ?: false)
    }
    var isBookmarked by remember(enrichedLesson.id, progress?.isBookmarked) {
        mutableStateOf(progress?.isBookmarked ?: false)
    }
    var userNote by remember(enrichedLesson.id, progress?.userNote) {
        mutableStateOf(progress?.userNote ?: "")
    }
    var selectedMiniOption by remember(enrichedLesson.id) { mutableIntStateOf(-1) }
    var showMiniAnswer by remember(enrichedLesson.id) { mutableStateOf(false) }
    var showPdfDialog by remember(enrichedLesson.id) { mutableStateOf(false) }
    var studySectionFilter by remember(enrichedLesson.id) { mutableIntStateOf(4) }
    // آموزش قدم‌به‌قدم داخل درس: اول فقط بخش ۱ نشان داده می‌شود؛ بعد از
    // «یاد گرفتم» بخش بعد باز می‌شود. «نمایش یکجا» (تب ۴) همه را نشان می‌دهد.
    var revealedStepCount by remember(enrichedLesson.id) { mutableIntStateOf(1) }
    var allStepsLearned by remember(enrichedLesson.id) { mutableStateOf(false) }
    var stepRetryHintFor by remember(enrichedLesson.id) { mutableStateOf<Int?>(null) }

    var isClozePracticeActive by remember(enrichedLesson.id) { mutableStateOf(false) }
    val revealedClozeKeys = remember(enrichedLesson.id) { androidx.compose.runtime.mutableStateMapOf<String, Boolean>() }
    var showQuickStyleBar by remember { mutableStateOf(false) }
    // کارت‌های تکمیلی پایین درس پیش‌فرض جمع‌شده‌اند تا متن درس بدون مزاحم خوانده شود
    var showMoreLessonCards by remember(enrichedLesson.id) { mutableStateOf(false) }
    // ورود ملایم کارت‌های گام با فنر ژله‌ای؛ فقط هنگام باز شدن درس، نه هنگام اسکرول برگشتی
    var stepsVisible by remember(enrichedLesson.id) { mutableStateOf(false) }
    LaunchedEffect(enrichedLesson.id) { stepsVisible = true }

    val gradeNum = remember(enrichedLesson.id) { enrichedLesson.subcategoryFolder().first }
    val officialBook = remember(enrichedLesson.id) {
        TextbookPdfCatalog.getBookForSubjectAndGrade(enrichedLesson.normalizedSubjectId(), gradeNum)
    }
    val subjectOrderedLessons = remember(enrichedLesson.id, allLessons) {
        allLessons.filter {
            it.normalizedSubjectId() == enrichedLesson.normalizedSubjectId()
        }.sortedBy { it.orderIndex }
    }
    val currentLessonIdx = remember(enrichedLesson.id, subjectOrderedLessons) {
        subjectOrderedLessons.indexOfFirst { it.id == enrichedLesson.id }
    }
    val prevLesson = remember(currentLessonIdx, subjectOrderedLessons) {
        if (currentLessonIdx > 0) subjectOrderedLessons.getOrNull(currentLessonIdx - 1) else null
    }
    val nextLesson = remember(currentLessonIdx, subjectOrderedLessons) {
        if (currentLessonIdx >= 0 && currentLessonIdx < subjectOrderedLessons.lastIndex) {
            subjectOrderedLessons.getOrNull(currentLessonIdx + 1)
        } else null
    }
    val gradeLessons = remember(enrichedLesson.id, allLessons) {
        allLessons.filter {
            it.normalizedSubjectId() == enrichedLesson.normalizedSubjectId() &&
                it.subcategoryFolder().first == gradeNum
        }
    }
    val advancedMethod = remember(enrichedLesson.id) {
        AdvancedMnemonicEngine.getMethodForLesson(enrichedLesson)
    }

    val extractedClozeItems = remember(enrichedLesson.id) {
        val guillemetRegex = Regex("«([^»]{2,45})»")
        val items = mutableListOf<Triple<String, String, String>>()
        enrichedLesson.steps.forEach { step ->
            val matches = guillemetRegex.findAll(step.simpleExplanation).map { it.groupValues[1].trim() }.distinct().toList()
            matches.take(2).forEachIndexed { idx, term ->
                val sentence = step.simpleExplanation.lines().firstOrNull { it.contains("«$term»") } ?: step.simpleExplanation.take(140)
                val masked = sentence.replace("«$term»", "« ░░░░░░ »")
                items.add(Triple("step_${step.stepNumber}_$idx", "گام ${BidiFormatter.toPersianDigits(step.stepNumber.toString())}: $masked", term))
            }
            if (step.memoryTrick.isNotBlank()) {
                val trickTerm = guillemetRegex.find(step.memoryTrick)?.groupValues?.getOrNull(1)
                    ?: step.title.substringAfter(":").trim().takeIf { it.length in 3..35 }
                if (!trickTerm.isNullOrBlank()) {
                    val maskedTrick = if (step.memoryTrick.contains(trickTerm)) {
                        step.memoryTrick.replaceFirst(trickTerm, "« ░░░░░░ »")
                    } else {
                        "نکته طلایی « ░░░░░░ »: ${step.memoryTrick}"
                    }
                    items.add(Triple("trick_${step.stepNumber}", maskedTrick, trickTerm))
                }
            }
        }
        enrichedLesson.goldenFormulas.take(4).forEachIndexed { fIdx, formula ->
            val parts = formula.split("=", "←", "➔", ":")
            if (parts.size >= 2) {
                val answerPart = parts.last().trim()
                val promptPart = parts.dropLast(1).joinToString(" = ").trim()
                items.add(Triple("formula_$fIdx", "فرمول ${BidiFormatter.toPersianDigits((fIdx + 1).toString())}: $promptPart = « ░░░░░░ »", answerPart))
            }
        }
        items.distinctBy { it.third }.take(10)
    }

    if (showPdfDialog) {
        InAppTextbookPdfViewerDialog(
            book = officialBook,
            lessons = gradeLessons.ifEmpty { listOf(enrichedLesson) },
            accentColor = HomePrimary,
            onDismiss = { showPdfDialog = false }
        )
    }

    // وضعیت اسکرول محتوای درس + شمار گام خوانده‌شده برای نوار پیشرفت (از کلید آیتم‌های گام در LazyColumn)
    val lessonListState = rememberLazyListState()
    val totalLessonSteps = enrichedLesson.steps.size
    val currentStepNumber by remember(enrichedLesson.id) {
        derivedStateOf {
            lessonListState.layoutInfo.visibleItemsInfo.mapNotNull { item ->
                (item.key as? String)?.let { key ->
                    Regex("_step_(\\d+)_").find(key)?.groupValues?.getOrNull(1)?.toIntOrNull()
                }
            }.maxOrNull() ?: 0
        }
    }
    val animatedLessonProgress by animateFloatAsState(
        targetValue = if (totalLessonSteps > 0) {
            (currentStepNumber / totalLessonSteps.toFloat()).coerceIn(0f, 1f)
        } else 0f,
        animationSpec = tween(300),
        label = "lesson_progress"
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(HomeBackground)
    ) {
        // Minimalist Calm Reading Header Bar
        Surface(
            color = HomeSurface(),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(modifier = Modifier.fillMaxWidth()) {
                val isBoldActive = themeController.isBoldTextEnabled
                val isUnderlineActive = themeController.isUnderlineKeyPointsEnabled
                val isHighlighterActive = themeController.isHighlighterEnabled

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 12.dp, vertical = 10.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.weight(1f)
                    ) {
                        IconButton(
                            onClick = onBack,
                            modifier = Modifier.testTag("lesson_back_button")
                        ) {
                            Icon(
                                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                                contentDescription = "بازگشت",
                                tint = HomeText
                            )
                        }
                        Column(modifier = Modifier.weight(1f).padding(end = 4.dp)) {
                            val refText = enrichedLesson.officialTextbookReference().let {
                                if (it.length > 38) it.take(38) + "…" else it
                            }
                            Text(
                                text = BidiFormatter.formatMixedPersianText(refText),
                                fontSize = 11.sp,
                                color = HomeMuted,
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis
                            )
                            Text(
                                text = BidiFormatter.formatMixedPersianText(enrichedLesson.title),
                                fontSize = 14.sp,
                                lineHeight = 19.sp,
                                fontWeight = FontWeight.Bold,
                                color = HomeText,
                                maxLines = 1,
                                softWrap = false,
                                overflow = TextOverflow.Ellipsis
                            )
                        }
                    }

                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(2.dp)
                    ) {
                        IconButton(
                            onClick = { showQuickStyleBar = !showQuickStyleBar },
                            modifier = Modifier.size(36.dp).testTag("lesson_quick_style_bar_btn")
                        ) {
                            Icon(
                                imageVector = Icons.Default.FormatSize,
                                contentDescription = "تنظیمات مطالعه",
                                tint = if (showQuickStyleBar) HomePrimary else HomeMuted,
                                modifier = Modifier.size(19.dp)
                            )
                        }

                        IconButton(
                            onClick = { showPdfDialog = true },
                            modifier = Modifier.size(36.dp).testTag("lesson_open_pdf_btn")
                        ) {
                            Icon(
                                imageVector = Icons.Default.PictureAsPdf,
                                contentDescription = "مشاهده منبع کتاب درسی",
                                tint = HomeMuted,
                                modifier = Modifier.size(18.dp)
                            )
                        }

                        IconButton(
                            onClick = {
                                isBookmarked = !isBookmarked
                                onSaveState(
                                    isCompleted,
                                    isBookmarked,
                                    userNote,
                                    progress?.masteryPercent ?: 0
                                )
                            },
                            modifier = Modifier.size(36.dp).testTag("bookmark_lesson_button")
                        ) {
                            Icon(
                                imageVector = if (isBookmarked) Icons.Default.Bookmark else Icons.Default.BookmarkBorder,
                                contentDescription = "نشان کردن درس",
                                tint = if (isBookmarked) HomePrimary else HomeMuted,
                                modifier = Modifier.size(20.dp)
                            )
                        }
                    }
                }

                // Collapsible Reading Tools Drawer (hidden by default for distraction-free reading)
                AnimatedVisibility(
                    visible = showQuickStyleBar,
                    enter = expandVertically(animationSpec = JellyContentSizeSpring) + fadeIn(),
                    exit = shrinkVertically(animationSpec = JellyContentSizeSpring) + fadeOut()
                ) {
                    Surface(
                        color = HomeBackground,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp, vertical = 6.dp),
                        shape = RoundedCornerShape(12.dp),
                        border = BorderStroke(1.dp, HomeBorder)
                    ) {
                        Column(
                            modifier = Modifier.padding(10.dp),
                            verticalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .horizontalScroll(rememberScrollState()),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                Surface(
                                    modifier = Modifier
                                        .clip(RoundedCornerShape(8.dp))
                                        .clickable { themeController.updateUnderlineKeyPoints(!isUnderlineActive) }
                                        .testTag("lesson_underline_toggle_btn"),
                                    color = if (isUnderlineActive) HomePrimary else HomeSurface(),
                                    border = BorderStroke(1.dp, HomeBorder),
                                    shape = RoundedCornerShape(8.dp)
                                ) {
                                    Text(
                                        text = "زیرخط نکات",
                                        maxLines = 1,
                                        softWrap = false,
                                        fontSize = 11.sp,
                                        color = if (isUnderlineActive) HomeOnPrimary else HomeText,
                                        modifier = Modifier.padding(horizontal = 10.dp, vertical = 5.dp)
                                    )
                                }

                                Surface(
                                    modifier = Modifier
                                        .clip(RoundedCornerShape(8.dp))
                                        .clickable { themeController.updateHighlighter(!isHighlighterActive) }
                                        .testTag("lesson_highlighter_toggle_btn"),
                                    color = if (isHighlighterActive) HomePrimary else HomeSurface(),
                                    border = BorderStroke(1.dp, HomeBorder),
                                    shape = RoundedCornerShape(8.dp)
                                ) {
                                    Text(
                                        text = "هایلایت",
                                        maxLines = 1,
                                        softWrap = false,
                                        fontSize = 11.sp,
                                        color = if (isHighlighterActive) HomeOnPrimary else HomeText,
                                        modifier = Modifier.padding(horizontal = 10.dp, vertical = 5.dp)
                                    )
                                }

                                Surface(
                                    modifier = Modifier
                                        .clip(RoundedCornerShape(8.dp))
                                        .clickable { isClozePracticeActive = !isClozePracticeActive }
                                        .testTag("lesson_cloze_practice_toggle_btn"),
                                    color = if (isClozePracticeActive) HomePrimary else HomeSurface(),
                                    border = BorderStroke(1.dp, HomeBorder),
                                    shape = RoundedCornerShape(8.dp)
                                ) {
                                    Text(
                                        text = if (isClozePracticeActive) "نمایش کلمات" else "تمرین حافظه (محو کلمات)",
                                        maxLines = 1,
                                        softWrap = false,
                                        fontSize = 11.sp,
                                        color = if (isClozePracticeActive) HomeOnPrimary else HomeText,
                                        modifier = Modifier.padding(horizontal = 10.dp, vertical = 5.dp)
                                    )
                                }

                                Surface(
                                    modifier = Modifier
                                        .clip(RoundedCornerShape(8.dp))
                                        .clickable { themeController.updateBoldText(!isBoldActive) }
                                        .testTag("lesson_bold_toggle_btn"),
                                    color = if (isBoldActive) HomePrimary else HomeSurface(),
                                    border = BorderStroke(1.dp, HomeBorder),
                                    shape = RoundedCornerShape(8.dp)
                                ) {
                                    Text(
                                        text = "متن ضخیم",
                                        maxLines = 1,
                                        softWrap = false,
                                        fontSize = 11.sp,
                                        color = if (isBoldActive) HomeOnPrimary else HomeText,
                                        modifier = Modifier.padding(horizontal = 10.dp, vertical = 5.dp)
                                    )
                                }
                            }
                        }
                    }
                }

                // درس یک صفحهٔ عمودی یکپارچه است (بدون تب افقی): درس‌نامه قدم‌به‌قدم،
                // بعد مثال و تست، بعد نکات کلیدی — همه زیرمجموعهٔ همین درس، پشت سر هم.

                if (prevLesson != null || nextLesson != null) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp, vertical = 4.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        if (prevLesson != null) {
                            Text(
                                text = "→ مبحث قبلی",
                                maxLines = 1,
                                softWrap = false,
                                fontSize = 12.sp,
                                fontWeight = FontWeight.SemiBold,
                                color = HomePrimary,
                                modifier = Modifier
                                    .clip(RoundedCornerShape(6.dp))
                                    .clickable { onNavigateToLesson(prevLesson.id) }
                                    .padding(horizontal = 6.dp, vertical = 2.dp)
                                    .testTag("prev_lesson_btn")
                            )
                        } else {
                            Spacer(modifier = Modifier.width(60.dp))
                        }

                        Text(
                            text = BidiFormatter.formatMixedPersianText(
                                "مبحث ${currentLessonIdx + 1} از ${subjectOrderedLessons.size}"
                            ),
                            fontSize = 11.sp,
                            color = HomeMuted,
                            maxLines = 1,
                            softWrap = false,
                            overflow = TextOverflow.Ellipsis
                        )

                        if (nextLesson != null) {
                            Text(
                                text = "مبحث بعدی ←",
                                maxLines = 1,
                                softWrap = false,
                                fontSize = 12.sp,
                                fontWeight = FontWeight.SemiBold,
                                color = HomePrimary,
                                modifier = Modifier
                                    .clip(RoundedCornerShape(6.dp))
                                    .clickable { onNavigateToLesson(nextLesson.id) }
                                    .padding(horizontal = 6.dp, vertical = 2.dp)
                                    .testTag("next_lesson_btn")
                            )
                        } else {
                            Spacer(modifier = Modifier.width(60.dp))
                        }
                    }
                }

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(1.dp)
                        .background(
                            Brush.horizontalGradient(
                                listOf(
                                    lessonAccent.copy(alpha = 0.0f),
                                    lessonAccent.copy(alpha = 0.55f),
                                    lessonAccent.copy(alpha = 0.0f)
                                )
                            )
                        )
                )
            }
        }

        // نوار پیشرفت خواندن گام‌ها: «قدم X از Y» + نوار باریک گرادیانی به رنگ موضوع (زیر سربرگ، بالای محتوا)
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(HomeSurface())
                .padding(horizontal = 20.dp, vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Text(
                text = if (totalLessonSteps > 0) {
                    "قدم ${BidiFormatter.toPersianDigits(currentStepNumber.coerceAtLeast(1).toString())} از ${BidiFormatter.toPersianDigits(totalLessonSteps.toString())}"
                } else {
                    "قدم ۱ از ۱"
                },
                fontSize = 12.sp,
                fontWeight = FontWeight.SemiBold,
                color = lessonAccent,
                maxLines = 1,
                softWrap = false,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier.testTag("lesson_progress_label")
            )
            Box(
                modifier = Modifier
                    .weight(1f)
                    .height(6.dp)
                    .clip(RoundedCornerShape(3.dp))
                    .background(HomeBorder.copy(alpha = 0.6f))
                    .testTag("lesson_progress_bar")
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth(animatedLessonProgress.coerceIn(0.04f, 1f))
                        .height(6.dp)
                        .clip(RoundedCornerShape(3.dp))
                        .background(
                            Brush.horizontalGradient(
                                listOf(
                                    lessonAccent,
                                    lerp(lessonAccent, Color.White, 0.35f)
                                )
                            )
                        )
                )
            }
        }

        val responsive = LocalResponsiveDimens.current
        // (lessonAccent بالاتر، کنار subject تعریف شده تا سربرگ و نوار پیشرفت هم به آن برسند.)
        val prerequisiteLinks = remember(enrichedLesson.id, allLessons) {
            enrichedLesson.getPrerequisiteLinks(allLessons)
        }
        val minusZeroGuide = remember(enrichedLesson.id) {
            MinusZeroLearningEngine.buildGuideForLesson(enrichedLesson)
        }
        val nineLevelPath = remember(enrichedLesson.id) {
            MinusZeroLearningEngine.buildNineLevelPathForLesson(enrichedLesson)
        }
        val sixPartMnemonic = remember(enrichedLesson.id) {
            MinusZeroLearningEngine.buildSixPartMnemonicForLesson(enrichedLesson)
        }
        // -1 = بسته، 0..6 = دکمه کمکی انتخاب‌شده، 7 = نمایش کامل الگوی ۱۰ مرحله‌ای و مسیر ۹ سطحی از منفی صفر
        var activeAssistAction by remember(enrichedLesson.id) { mutableIntStateOf(-1) }
        var guidedSelectedOption by remember(enrichedLesson.id) { mutableIntStateOf(-1) }

        LazyColumn(
            state = lessonListState,
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(
                start = 24.dp,
                top = 20.dp,
                end = 24.dp,
                bottom = 96.dp
            ),
            verticalArrangement = Arrangement.spacedBy(LessonSpacing.table)
        ) {
            // سربرگ مصور درس: گرادیان اشباع رنگ موضوع + دایره‌های تزئینی بومی Compose (بدون فایل تصویری)؛ تیتر و زیرتیتر عیناً حفظ شده‌اند.
            item(key = "${enrichedLesson.id}_hero_header") {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(20.dp))
                        .background(
                            Brush.linearGradient(
                                colors = listOf(
                                    lessonAccent,
                                    lerp(lessonAccent, MaterialTheme.colorScheme.tertiary, 0.45f),
                                    lerp(lessonAccent, Color.Black, 0.22f)
                                )
                            )
                        )
                        .testTag("lesson_hero_header")
                ) {
                    Canvas(modifier = Modifier.matchParentSize()) {
                        drawCircle(
                            color = Color.White.copy(alpha = 0.12f),
                            radius = size.minDimension * 0.55f,
                            center = Offset(size.width * 0.92f, -size.height * 0.25f)
                        )
                        drawCircle(
                            color = Color.White.copy(alpha = 0.10f),
                            radius = size.minDimension * 0.38f,
                            center = Offset(size.width * 0.78f, size.height * 1.05f)
                        )
                        drawCircle(
                            color = Color.White.copy(alpha = 0.08f),
                            radius = size.minDimension * 0.22f,
                            center = Offset(size.width * 0.08f, size.height * 0.15f)
                        )
                    }
                    Column(
                        modifier = Modifier.padding(horizontal = 20.dp, vertical = 20.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Row(
                            horizontalArrangement = Arrangement.spacedBy(8.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Surface(
                                shape = CircleShape,
                                color = Color.White.copy(alpha = 0.22f),
                                modifier = Modifier.size(48.dp).testTag("lesson_logo_icon")
                            ) {
                                Box(contentAlignment = Alignment.Center) {
                                    Icon(
                                        imageVector = LessonLogos1406.iconForLesson(
                                            enrichedLesson.id,
                                            enrichedLesson.normalizedSubjectId()
                                        ),
                                        contentDescription = null,
                                        tint = Color.White,
                                        modifier = Modifier.size(30.dp)
                                    )
                                }
                            }
                            Surface(
                                shape = RoundedCornerShape(8.dp),
                                color = Color.White.copy(alpha = 0.18f)
                            ) {
                                Text(
                                    text = subject.shortTitle.ifBlank { subject.title },
                                    softWrap = false,
                                    overflow = TextOverflow.Ellipsis,
                                    fontSize = 11.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = Color.White,
                                    maxLines = 1,
                                    modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp)
                                )
                            }
                            if (totalLessonSteps > 0) {
                                Text(
                                    text = "${BidiFormatter.toPersianDigits(totalLessonSteps.toString())} گام",
                                    softWrap = false,
                                    fontSize = 11.sp,
                                    fontWeight = FontWeight.Medium,
                                    color = Color.White.copy(alpha = 0.85f),
                                    maxLines = 1
                                )
                            }
                        }
                        Text(
                            text = BidiFormatter.formatMixedPersianText(enrichedLesson.title),
                            style = MaterialTheme.typography.titleLarge,
                            fontWeight = FontWeight.Bold,
                            lineHeight = 32.sp,
                            color = Color.White
                        )
                        if (enrichedLesson.subtitle.isNotBlank()) {
                            Text(
                                text = BidiFormatter.formatMixedPersianText(enrichedLesson.subtitle),
                                style = MaterialTheme.typography.bodyMedium,
                                lineHeight = 24.sp,
                                color = Color.White.copy(alpha = 0.92f)
                            )
                        }
                        // تصویر برداری اختصاصی همین درس — قاب قصه‌ای نرم: گوشه‌های گرد، سایهٔ ملایم و لهجهٔ ملایم‌شده تا با گرادیان هدر یکی شود، نه مستطیل تند و پررنگ.
                        LessonVectorIllustration(
                            lessonId = enrichedLesson.id,
                            subjectId = enrichedLesson.normalizedSubjectId(),
                            accent = lessonAccent.copy(alpha = 0.88f),
                            firstDiagramTitle = enrichedLesson.steps
                                .firstOrNull { it.visualDiagramText.isNotBlank() }
                                ?.visualDiagramText
                                ?.lineSequence()
                                ?.firstOrNull { it.isNotBlank() }
                                ?.trim()
                                ?.takeIf { it.isNotEmpty() }
                                ?: enrichedLesson.title,
                            lessonTitle = enrichedLesson.title,
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(200.dp)
                                .shadow(6.dp, RoundedCornerShape(24.dp))
                        )
                    }
                }
            }

            // ۰. کارت تمرین تعاملی محو و نمایش کلمات کلیدی (Cloze Active Recall Card)
            if (isClozePracticeActive && extractedClozeItems.isNotEmpty()) {
                item(key = "${enrichedLesson.id}_cloze_memory_drill") {
                    Surface(
                        shape = RoundedCornerShape(18.dp),
                        color = HomeSurface(),
                        border = BorderStroke(1.dp, HomePrimary.copy(alpha = 0.45f)),
                        modifier = Modifier
                            .fillMaxWidth()
                            .testTag("cloze_memory_drill_card")
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
                                Column(modifier = Modifier.weight(1f)) {
                                    Text(
                                        text = "تمرین حافظه فعال: بازیابی کلمات کلیدی",
                                        softWrap = false,
                                        overflow = TextOverflow.Ellipsis,
                                        style = MaterialTheme.typography.titleSmall,
                                        fontWeight = FontWeight.Bold,
                                        color = HomePrimary,
                                        maxLines = 1
                                    )
                                    Text(
                                        text = "کلمات کلیدی محو شده‌اند؛ ابتدا در ذهن پاسخ دهید، سپس روی هر کادر بزنید:",
                                        maxLines = 1,
                                        overflow = TextOverflow.Ellipsis,
                                        style = MaterialTheme.typography.labelSmall,
                                        color = HomeMuted
                                    )
                                }
                                Surface(
                                    onClick = {
                                        val allRevealed = extractedClozeItems.all { revealedClozeKeys[it.first] == true }
                                        extractedClozeItems.forEach { (k, _, _) ->
                                            revealedClozeKeys[k] = !allRevealed
                                        }
                                    },
                                    shape = RoundedCornerShape(8.dp),
                                    color = HomePrimary
                                ) {
                                    Text(
                                        text = if (extractedClozeItems.all { revealedClozeKeys[it.first] == true }) "محو مجدد همه" else "نمایش همه",
                                        softWrap = false,
                                        style = MaterialTheme.typography.labelSmall,
                                        fontWeight = FontWeight.Bold,
                                        color = HomeOnPrimary,
                                        maxLines = 1,
                                        modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp)
                                    )
                                }
                            }

                            extractedClozeItems.forEach { (itemId, maskedContext, hiddenWord) ->
                                val isRevealed = revealedClozeKeys[itemId] == true
                                Surface(
                                    onClick = { revealedClozeKeys[itemId] = !isRevealed },
                                    shape = RoundedCornerShape(12.dp),
                                    color = HomeBackground,
                                    border = BorderStroke(
                                        1.dp,
                                        if (isRevealed) HomeGreen else HomeBorder
                                    ),
                                    modifier = Modifier.fillMaxWidth()
                                ) {
                                    Column(
                                        modifier = Modifier.padding(12.dp),
                                        verticalArrangement = Arrangement.spacedBy(6.dp)
                                    ) {
                                        BiDiText(
                                            text = if (isRevealed) maskedContext.replace("« ░░░░░░ »", "«$hiddenWord»") else maskedContext,
                                            style = MaterialTheme.typography.bodySmall,
                                            isUnderlineEnabled = isRevealed,
                                            isHighlighterEnabled = isRevealed
                                        )
                                        Row(
                                            modifier = Modifier.fillMaxWidth(),
                                            horizontalArrangement = Arrangement.SpaceBetween,
                                            verticalAlignment = Alignment.CenterVertically
                                        ) {
                                            Surface(
                                                shape = RoundedCornerShape(6.dp),
                                                color = if (isRevealed) MaterialTheme.colorScheme.tertiaryContainer else MaterialTheme.colorScheme.primaryContainer
                                            ) {
                                                Text(
                                                    text = if (isRevealed) "پاسخ کلیدی: $hiddenWord" else "ضربه بزنید تا کلمه کلیدی نمایش داده شود",
                                                    softWrap = false,
                                                    overflow = TextOverflow.Ellipsis,
                                                    style = MaterialTheme.typography.labelSmall,
                                                    fontWeight = FontWeight.Bold,
                                                    color = if (isRevealed) MaterialTheme.colorScheme.onTertiaryContainer else MaterialTheme.colorScheme.onPrimaryContainer,
                                                    maxLines = 1,
                                                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
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

            // ۰. بخش پیش‌نیازهای مبحث (طراحی فشرده، جمع‌وجور و غیرمزاحم)
            if (themeController.showPrerequisitesBeforeLesson && prerequisiteLinks.isNotEmpty() && studySectionFilter in listOf(0, 4)) {
                item(key = "${enrichedLesson.id}_prerequisite_links") {
                    var prereqExpanded by remember(enrichedLesson.id) { mutableStateOf(false) }
                    Surface(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(12.dp),
                        color = MaterialTheme.colorScheme.secondaryContainer.copy(alpha = 0.65f),
                        border = BorderStroke(1.dp, HomeBorder)
                    ) {
                        Column(
                            modifier = Modifier
                                .padding(horizontal = 12.dp, vertical = 8.dp)
                                .animateContentSize(animationSpec = JellyContentSizeSpring),
                            verticalArrangement = Arrangement.spacedBy(6.dp)
                        ) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clickable { prereqExpanded = !prereqExpanded },
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.spacedBy(6.dp),
                                    modifier = Modifier.weight(1f)
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.Link,
                                        contentDescription = null,
                                        tint = HomePrimary,
                                        modifier = Modifier.size(16.dp)
                                    )
                                    Text(
                                        text = "پیش‌نیاز پیشنهادی: ${prerequisiteLinks.first().prerequisiteTitle}",
                                        softWrap = false,
                                        style = MaterialTheme.typography.labelMedium,
                                        fontWeight = FontWeight.Bold,
                                        color = MaterialTheme.colorScheme.onSecondaryContainer,
                                        maxLines = 1,
                                        overflow = TextOverflow.Ellipsis
                                    )
                                }
                                Text(
                                    text = if (prereqExpanded) "بستن ▲" else "مشاهده ◀",
                                    maxLines = 1,
                                    softWrap = false,
                                    style = MaterialTheme.typography.labelSmall,
                                    fontWeight = FontWeight.Bold,
                                    color = HomePrimary
                                )
                            }

                            // داخل LazyColumn: به‌جای AnimatedVisibility با expandVertically که فضای خالی می‌ساخت، if ساده + animateContentSize فنری
                            if (prereqExpanded) {
                                Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                                    prerequisiteLinks.forEach { link ->
                                        Surface(
                                            modifier = Modifier
                                                .fillMaxWidth()
                                                .clip(RoundedCornerShape(10.dp))
                                                .clickable { onNavigateToLesson(link.prerequisiteLessonId) },
                                            shape = RoundedCornerShape(10.dp),
                                            color = HomeSurface(),
                                            border = BorderStroke(1.dp, HomeBorder)
                                        ) {
                                            Row(
                                                modifier = Modifier.padding(horizontal = 10.dp, vertical = 8.dp),
                                                verticalAlignment = Alignment.CenterVertically,
                                                horizontalArrangement = Arrangement.SpaceBetween
                                            ) {
                                                Column(modifier = Modifier.weight(1f)) {
                                                    Text(
                                                        text = "${link.prerequisiteTitle} (${link.prerequisiteBadge})",
                                                        style = MaterialTheme.typography.labelMedium,
                                                        fontWeight = FontWeight.Bold,
                                                        color = HomeText,
                                                        maxLines = 1,
                                                        overflow = TextOverflow.Ellipsis
                                                    )
                                                    Text(
                                                        text = link.note,
                                                        style = MaterialTheme.typography.labelSmall,
                                                        color = HomeMuted,
                                                        maxLines = 1,
                                                        overflow = TextOverflow.Ellipsis
                                                    )
                                                }
                                                Text(
                                                    text = "مطالعه ←",
                                                    maxLines = 1,
                                                    softWrap = false,
                                                    style = MaterialTheme.typography.labelSmall,
                                                    fontWeight = FontWeight.Bold,
                                                    color = HomePrimary,
                                                    modifier = Modifier.padding(start = 8.dp)
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

            // ۱. باکس معرفی و پیام درس (در تب درس‌نامه و نمایش یکجا)
            if (studySectionFilter in listOf(0, 4)) {
                item(key = "${enrichedLesson.id}_overview") {
                    Surface(
                        shape = RoundedCornerShape(18.dp),
                        color = HomeSurface(),
                        border = BorderStroke(1.dp, HomeBorder),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Column(
                            modifier = Modifier.padding(18.dp),
                            verticalArrangement = Arrangement.spacedBy(10.dp)
                        ) {
                            Text(
                                text = BidiFormatter.formatMixedPersianText("${enrichedLesson.levelBadge} • ${enrichedLesson.subcategoryFolder().second}"),
                                softWrap = false,
                                overflow = TextOverflow.Ellipsis,
                                style = MaterialTheme.typography.labelSmall,
                                fontWeight = FontWeight.Bold,
                                color = lessonAccent,
                                maxLines = 1
                            )
                            BiDiText(
                                text = enrichedLesson.subtitle,
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.Bold,
                                color = HomeText
                            )
                            if (enrichedLesson.whyStudentsStruggle.isNotBlank() && enrichedLesson.whyStudentsStruggle != enrichedLesson.subtitle) {
                                Text(
                                    text = "هدف و کلید یادگیری این درس:",
                                    maxLines = 1,
                                    softWrap = false,
                                    style = MaterialTheme.typography.labelMedium,
                                    fontWeight = FontWeight.Bold,
                                    color = lessonAccent
                                )
                                BiDiText(
                                    text = enrichedLesson.whyStudentsStruggle,
                                    style = MaterialTheme.typography.bodyMedium,
                                    isUnderlineEnabled = themeController.isUnderlineKeyPointsEnabled,
                                    isHighlighterEnabled = themeController.isHighlighterEnabled
                                )
                            }

                            Surface(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clip(RoundedCornerShape(10.dp))
                                    .clickable {
                                        isBookmarked = !isBookmarked
                                        onSaveState(
                                            isCompleted,
                                            isBookmarked,
                                            userNote,
                                            progress?.masteryPercent ?: 0
                                        )
                                    }
                                    .testTag("toggle_favorite_banner"),
                                shape = RoundedCornerShape(10.dp),
                                color = if (isBookmarked) MaterialTheme.colorScheme.primaryContainer else HomeBackground,
                                border = BorderStroke(1.dp, if (isBookmarked) lessonAccent else HomeBorder)
                            ) {
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(horizontal = 12.dp, vertical = 9.dp),
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.SpaceBetween
                                ) {
                                    Row(
                                        modifier = Modifier.weight(1f),
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Icon(
                                            imageVector = if (isBookmarked) Icons.Default.Bookmark else Icons.Default.BookmarkBorder,
                                            contentDescription = null,
                                            tint = if (isBookmarked) lessonAccent else HomeMuted,
                                            modifier = Modifier.size(18.dp)
                                        )
                                        Spacer(modifier = Modifier.width(8.dp))
                                        Text(
                                            text = if (isBookmarked) "این مبحث در نشان‌شده‌ها ذخیره شده است" else "ذخیره این مبحث در نشان‌شده‌ها برای مرور سریع",
                                            softWrap = false,
                                            overflow = TextOverflow.Ellipsis,
                                            style = MaterialTheme.typography.labelSmall,
                                            fontWeight = if (isBookmarked) FontWeight.Bold else FontWeight.Medium,
                                            color = if (isBookmarked) MaterialTheme.colorScheme.onPrimaryContainer else HomeText,
                                            maxLines = 1,
                                            modifier = Modifier.weight(1f)
                                        )
                                    }
                                    Spacer(modifier = Modifier.width(8.dp))
                                    Text(
                                        text = if (isBookmarked) "حذف ✕" else "+ نشان کردن",
                                        style = MaterialTheme.typography.labelSmall,
                                        fontWeight = FontWeight.Bold,
                                        color = lessonAccent,
                                        maxLines = 1,
                                        softWrap = false
                                    )
                                }
                            }

                            // شناسنامه منبع رسمی و سال انتشار مبحث
                            Surface(
                                shape = RoundedCornerShape(10.dp),
                                color = HomeBackground,
                                border = BorderStroke(1.dp, HomeBorder),
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Column(
                                    modifier = Modifier.padding(10.dp),
                                    verticalArrangement = Arrangement.spacedBy(3.dp)
                                ) {
                                    Text(
                                        text = "منبع رسمی: ${minusZeroGuide.sourceMetadata.textbookTitle}",
                                        maxLines = 1,
                                        overflow = TextOverflow.Ellipsis,
                                        style = MaterialTheme.typography.labelSmall,
                                        fontWeight = FontWeight.Bold,
                                        color = HomeText
                                    )
                                    Text(
                                        text = "${minusZeroGuide.sourceMetadata.publicationYear} • ${minusZeroGuide.sourceMetadata.yearVerificationNote}",
                                        maxLines = 1,
                                        overflow = TextOverflow.Ellipsis,
                                        style = MaterialTheme.typography.labelSmall,
                                        color = HomeMuted
                                    )
                                    val masteryStatus = MinusZeroLearningEngine.resolveRealMasteryStatus(
                                        isCompleted = isCompleted,
                                        masteryPercent = progress?.masteryPercent ?: 0,
                                        answeredMiniCheckCorrectly = (selectedMiniOption == enrichedLesson.miniCheck.correctIndex) ||
                                            (guidedSelectedOption == minusZeroGuide.guidedExerciseCorrectIndex),
                                        answeredMiniCheckWrong = (selectedMiniOption != -1 && selectedMiniOption != enrichedLesson.miniCheck.correctIndex) ||
                                            (guidedSelectedOption != -1 && guidedSelectedOption != minusZeroGuide.guidedExerciseCorrectIndex)
                                    )
                                    Text(
                                        text = "وضعیت واقعی یادگیری شما: ${masteryStatus.first} — ${masteryStatus.second}",
                                        maxLines = 1,
                                        overflow = TextOverflow.Ellipsis,
                                        style = MaterialTheme.typography.labelSmall,
                                        fontWeight = FontWeight.Bold,
                                        color = lessonAccent
                                    )
                                }
                            }
                        }
                    }
                }

                // ۱.۵ نوار ۷ دکمه کمک‌آموزشی واقعی و الگوی ۱۰ مرحله‌ای «از منفی صفر»
                item(key = "${enrichedLesson.id}_minus_zero_assistant_bar") {
                    Surface(
                        shape = RoundedCornerShape(18.dp),
                        color = HomeSurface(),
                        border = BorderStroke(1.dp, HomeBorder),
                        modifier = Modifier
                            .fillMaxWidth()
                            .testTag("minus_zero_assistant_card")
                    ) {
                        Column(
                            modifier = Modifier
                                .padding(14.dp)
                                .animateContentSize(animationSpec = JellyContentSizeSpring),
                            verticalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Column(modifier = Modifier.weight(1f)) {
                                    Text(
                                        text = "دستیار آموزش از منفی صفر (کمک‌معلم گام‌به‌گام)",
                                        maxLines = 1,
                                        overflow = TextOverflow.Ellipsis,
                                        style = MaterialTheme.typography.titleSmall,
                                        fontWeight = FontWeight.Bold,
                                        color = HomeText
                                    )
                                    Text(
                                        text = "یکی را بزن تا همان‌جا ساده‌تر توضیح دهم:",
                                        maxLines = 1,
                                        overflow = TextOverflow.Ellipsis,
                                        style = MaterialTheme.typography.labelSmall,
                                        color = HomeMuted
                                    )
                                }
                                Surface(
                                    onClick = {
                                        activeAssistAction = if (activeAssistAction == 7) -1 else 7
                                    },
                                    shape = RoundedCornerShape(8.dp),
                                    color = if (activeAssistAction == 7) lessonAccent else MaterialTheme.colorScheme.primaryContainer,
                                    modifier = Modifier.testTag("toggle_10_step_guide_btn")
                                ) {
                                    Text(
                                        text = if (activeAssistAction == 7) "بستن الگوی ۱۰گانه" else "درس‌نامه ۱۰ مرحله‌ای",
                                        maxLines = 1,
                                        softWrap = false,
                                        style = MaterialTheme.typography.labelSmall,
                                        fontWeight = FontWeight.Bold,
                                        color = if (activeAssistAction == 7) HomeOnPrimary else MaterialTheme.colorScheme.onPrimaryContainer,
                                        modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp)
                                    )
                                }
                            }

                            val assistButtons = listOf(
                                Triple(0, "ساده‌تر توضیح بده", "assist_btn_simpler"),
                                Triple(1, "با شکل نشان بده", "assist_btn_diagram"),
                                Triple(2, "یک مثال دیگر بزن", "assist_btn_example"),
                                Triple(3, "از پایه‌تر شروع کن", "assist_btn_prerequisite"),
                                Triple(4, "مرحله‌به‌مرحله حل کن", "assist_btn_step_by_step"),
                                Triple(5, "از من امتحان بگیر", "assist_btn_test_me"),
                                Triple(6, "این قسمت را دوباره مرور کن", "assist_btn_review")
                            )

                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .horizontalScroll(rememberScrollState()),
                                horizontalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                assistButtons.forEach { (idx, label, tag) ->
                                    val selected = activeAssistAction == idx
                                    Surface(
                                        onClick = {
                                            activeAssistAction = if (selected) -1 else idx
                                        },
                                        shape = RoundedCornerShape(10.dp),
                                        color = if (selected) lessonAccent else HomeBackground,
                                        border = BorderStroke(1.dp, if (selected) lessonAccent else HomeBorder),
                                        modifier = Modifier.testTag(tag)
                                    ) {
                                        Text(
                                            text = label,
                                            style = MaterialTheme.typography.labelSmall,
                                            fontWeight = if (selected) FontWeight.Bold else FontWeight.Medium,
                                            color = if (selected) HomeOnPrimary else HomeText,
                                            maxLines = 1,
                                            softWrap = false,
                                            modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp)
                                        )
                                    }
                                }
                            }

                            if (activeAssistAction != -1) {
                                Surface(
                                    shape = RoundedCornerShape(14.dp),
                                    color = HomeBackground,
                                    border = BorderStroke(1.dp, lessonAccent.copy(alpha = 0.45f)),
                                    modifier = Modifier.fillMaxWidth()
                                ) {
                                    Column(
                                        modifier = Modifier.padding(14.dp),
                                        verticalArrangement = Arrangement.spacedBy(10.dp)
                                    ) {
                                        when (activeAssistAction) {
                                            0 -> {
                                                Text(
                                                    text = "۱. توضیح به زبان فوق‌العاده ساده و خودمانی:",
                                                    maxLines = 1,
                                                    overflow = TextOverflow.Ellipsis,
                                                    style = MaterialTheme.typography.labelLarge,
                                                    fontWeight = FontWeight.Bold,
                                                    color = lessonAccent
                                                )
                                                BiDiText(
                                                    text = minusZeroGuide.extraSimpleExplanation,
                                                    style = MaterialTheme.typography.bodyMedium
                                                )
                                                Box(
                                                    modifier = Modifier
                                                        .fillMaxWidth()
                                                        .height(1.dp)
                                                        .background(
                                                            Brush.horizontalGradient(
                                                                listOf(
                                                                    lessonAccent.copy(alpha = 0.0f),
                                                                    lessonAccent.copy(alpha = 0.55f),
                                                                    lessonAccent.copy(alpha = 0.0f)
                                                                )
                                                            )
                                                        )
                                                )
                                                Text(
                                                    text = "معنی واژه‌های تخصصی این درس به زبان ساده:",
                                                    maxLines = 1,
                                                    overflow = TextOverflow.Ellipsis,
                                                    style = MaterialTheme.typography.labelMedium,
                                                    fontWeight = FontWeight.Bold,
                                                    color = HomeText
                                                )
                                                minusZeroGuide.vocabularyGlossary.forEach { (term, meaning) ->
                                                    BiDiText(
                                                        text = "• $term: $meaning",
                                                        style = MaterialTheme.typography.bodySmall
                                                    )
                                                }
                                            }
                                            1 -> {
                                                Text(
                                                    text = "۲. نمایش تصویری، نمودار و انیمیشن گام‌به‌گام مبحث:",
                                                    maxLines = 1,
                                                    overflow = TextOverflow.Ellipsis,
                                                    style = MaterialTheme.typography.labelLarge,
                                                    fontWeight = FontWeight.Bold,
                                                    color = lessonAccent
                                                )
                                                BiDiText(
                                                    text = "الگوی دیداری: ${minusZeroGuide.simpleVisualBlueprint}",
                                                    style = MaterialTheme.typography.bodySmall,
                                                    fontWeight = FontWeight.SemiBold
                                                )
                                                LessonInteractiveVisualCard(
                                                    lesson = enrichedLesson,
                                                    subjectColor = lessonAccent
                                                )
                                            }
                                            2 -> {
                                                Text(
                                                    text = "۳. مثال ملموس دیگر از زندگی روزمره:",
                                                    maxLines = 1,
                                                    overflow = TextOverflow.Ellipsis,
                                                    style = MaterialTheme.typography.labelLarge,
                                                    fontWeight = FontWeight.Bold,
                                                    color = lessonAccent
                                                )
                                                BiDiText(
                                                    text = minusZeroGuide.extraEverydayExample,
                                                    style = MaterialTheme.typography.bodyMedium
                                                )
                                            }
                                            3 -> {
                                                Text(
                                                    text = "۴. شروع از پایه‌ترین نقطه (پیش‌نیازهای منفی صفر):",
                                                    maxLines = 1,
                                                    overflow = TextOverflow.Ellipsis,
                                                    style = MaterialTheme.typography.labelLarge,
                                                    fontWeight = FontWeight.Bold,
                                                    color = lessonAccent
                                                )
                                                BiDiText(
                                                    text = minusZeroGuide.minusZeroPrerequisiteText,
                                                    style = MaterialTheme.typography.bodyMedium
                                                )
                                                if (prerequisiteLinks.isNotEmpty()) {
                                                    Text(
                                                        text = "برای مطالعه درس پیش‌نیاز قبلی روی دکمه زیر بزنید:",
                                                        maxLines = 1,
                                                        overflow = TextOverflow.Ellipsis,
                                                        style = MaterialTheme.typography.labelSmall,
                                                        color = HomeMuted
                                                    )
                                                    val firstPre = prerequisiteLinks.first()
                                                    OutlinedButton(
                                                        onClick = { onNavigateToLesson(firstPre.prerequisiteLessonId) },
                                                        shape = RoundedCornerShape(10.dp)
                                                    ) {
                                                        Text(
                                                            text = "رفتن به پیش‌نیاز: ${firstPre.prerequisiteTitle}",
                                                            maxLines = 1,
                                                            softWrap = false,
                                                            overflow = TextOverflow.Ellipsis
                                                        )
                                                    }
                                                }
                                            }
                                            4 -> {
                                                Text(
                                                    text = "۵. حل مرحله‌به‌مرحله و بدون پرش مثال پایه:",
                                                    maxLines = 1,
                                                    overflow = TextOverflow.Ellipsis,
                                                    style = MaterialTheme.typography.labelLarge,
                                                    fontWeight = FontWeight.Bold,
                                                    color = lessonAccent
                                                )
                                                BiDiText(
                                                    text = "صورت مثال: ${minusZeroGuide.superEasyExampleQuestion}",
                                                    style = MaterialTheme.typography.bodyMedium,
                                                    fontWeight = FontWeight.Bold
                                                )
                                                minusZeroGuide.superEasyExampleSteps.forEachIndexed { idx, s ->
                                                    BiDiText(
                                                        text = "قدم ${BidiFormatter.toPersianDigits((idx + 1).toString())}: $s",
                                                        style = MaterialTheme.typography.bodySmall
                                                    )
                                                }
                                            }
                                            5 -> {
                                                Text(
                                                    text = "۶. تمرین کوچک هدایت‌شده (با تشخیص گام اشتباه):",
                                                    maxLines = 1,
                                                    overflow = TextOverflow.Ellipsis,
                                                    style = MaterialTheme.typography.labelLarge,
                                                    fontWeight = FontWeight.Bold,
                                                    color = lessonAccent
                                                )
                                                BiDiText(
                                                    text = minusZeroGuide.guidedExerciseQuestion,
                                                    style = MaterialTheme.typography.bodyMedium,
                                                    fontWeight = FontWeight.Bold
                                                )
                                                BiDiText(
                                                    text = minusZeroGuide.guidedExerciseHint,
                                                    style = MaterialTheme.typography.labelSmall,
                                                    color = HomeMuted
                                                )
                                                minusZeroGuide.guidedExerciseOptions.forEachIndexed { optIdx, opt ->
                                                    val isSel = guidedSelectedOption == optIdx
                                                    val isCorr = optIdx == minusZeroGuide.guidedExerciseCorrectIndex
                                                    Surface(
                                                        onClick = { guidedSelectedOption = optIdx },
                                                        shape = RoundedCornerShape(10.dp),
                                                        color = when {
                                                            isSel && isCorr -> MaterialTheme.colorScheme.tertiaryContainer
                                                            isSel && !isCorr -> MaterialTheme.colorScheme.errorContainer
                                                            else -> HomeSurface()
                                                        },
                                                        border = BorderStroke(
                                                            1.dp,
                                                            when {
                                                                isSel && isCorr -> HomeGreen
                                                                isSel && !isCorr -> MaterialTheme.colorScheme.error
                                                                else -> HomeBorder
                                                            }
                                                        ),
                                                        modifier = Modifier.fillMaxWidth()
                                                    ) {
                                                        BiDiText(
                                                            text = "${BidiFormatter.toPersianDigits((optIdx + 1).toString())}) $opt",
                                                            style = MaterialTheme.typography.bodySmall,
                                                            modifier = Modifier.padding(10.dp)
                                                        )
                                                    }
                                                }
                                                if (guidedSelectedOption in minusZeroGuide.guidedExerciseStepDiagnosis.indices) {
                                                    Surface(
                                                        shape = RoundedCornerShape(10.dp),
                                                        color = HomeSurface(),
                                                        border = BorderStroke(1.dp, HomeBorder),
                                                        modifier = Modifier.fillMaxWidth()
                                                    ) {
                                                        BiDiText(
                                                            text = minusZeroGuide.guidedExerciseStepDiagnosis[guidedSelectedOption],
                                                            style = MaterialTheme.typography.bodySmall,
                                                            modifier = Modifier.padding(10.dp)
                                                        )
                                                    }
                                                }
                                            }
                                            6 -> {
                                                Text(
                                                    text = "۷. مرور سریع، خلاصه نکات و جلوگیری از اشتباه رایج:",
                                                    maxLines = 1,
                                                    overflow = TextOverflow.Ellipsis,
                                                    style = MaterialTheme.typography.labelLarge,
                                                    fontWeight = FontWeight.Bold,
                                                    color = lessonAccent
                                                )
                                                minusZeroGuide.shortBulletSummary.forEach { bullet ->
                                                    BiDiText(
                                                        text = "• $bullet",
                                                        style = MaterialTheme.typography.bodySmall
                                                    )
                                                }
                                                Box(
                                                    modifier = Modifier
                                                        .fillMaxWidth()
                                                        .height(1.dp)
                                                        .background(
                                                            Brush.horizontalGradient(
                                                                listOf(
                                                                    lessonAccent.copy(alpha = 0.0f),
                                                                    lessonAccent.copy(alpha = 0.55f),
                                                                    lessonAccent.copy(alpha = 0.0f)
                                                                )
                                                            )
                                                        )
                                                )
                                                BiDiText(
                                                    text = "هشدار اشتباه رایج: ${minusZeroGuide.commonMistakeAndPrevention}",
                                                    style = MaterialTheme.typography.bodySmall
                                                )
                                            }
                                            7 -> {
                                                Text(
                                                    text = "الگوی کامل ۱۰ مرحله‌ای آموزش از منفی صفر:",
                                                    maxLines = 1,
                                                    overflow = TextOverflow.Ellipsis,
                                                    style = MaterialTheme.typography.titleSmall,
                                                    fontWeight = FontWeight.Bold,
                                                    color = lessonAccent
                                                )
                                                BiDiText(text = "۱. این موضوع درباره چیست؟ ${minusZeroGuide.oneSentenceWhatIsIt}", style = MaterialTheme.typography.bodySmall)
                                                BiDiText(text = "۲. کاربرد در زندگی واقعی: ${minusZeroGuide.realLifeApplication}", style = MaterialTheme.typography.bodySmall)
                                                BiDiText(text = "۳. تصویر ذهنی ساده: ${minusZeroGuide.simpleVisualBlueprint}", style = MaterialTheme.typography.bodySmall)
                                                Text(
                                                    text = "۴. توضیح قدم‌به‌قدم به زبان ساده:",
                                                    style = MaterialTheme.typography.labelMedium,
                                                    fontWeight = FontWeight.Bold,
                                                    color = HomeText,
                                                    maxLines = 1,
                                                    softWrap = false,
                                                    overflow = TextOverflow.Ellipsis
                                                )
                                                minusZeroGuide.stepByStepSimpleExplanation.forEach { s ->
                                                    BiDiText(text = "• $s", style = MaterialTheme.typography.bodySmall)
                                                }
                                                BiDiText(text = "۵. مثال آسان: ${minusZeroGuide.superEasyExampleQuestion}", style = MaterialTheme.typography.bodySmall)
                                                Text(
                                                    text = "۶. حل مرحله‌به‌مرحله مثال:",
                                                    style = MaterialTheme.typography.labelMedium,
                                                    fontWeight = FontWeight.Bold,
                                                    color = HomeText,
                                                    maxLines = 1,
                                                    softWrap = false,
                                                    overflow = TextOverflow.Ellipsis
                                                )
                                                minusZeroGuide.superEasyExampleSteps.forEach { s ->
                                                    BiDiText(text = "• $s", style = MaterialTheme.typography.bodySmall)
                                                }
                                                BiDiText(text = "۸. اشتباه رایج و راه جلوگیری: ${minusZeroGuide.commonMistakeAndPrevention}", style = MaterialTheme.typography.bodySmall)
                                                BiDiText(text = "۱۰. بررسی فهم مطلب: ${minusZeroGuide.checkUnderstandingQuestion}\n${minusZeroGuide.checkUnderstandingAnswer}", style = MaterialTheme.typography.bodySmall)
                                                Box(
                                                    modifier = Modifier
                                                        .fillMaxWidth()
                                                        .height(1.dp)
                                                        .background(
                                                            Brush.horizontalGradient(
                                                                listOf(
                                                                    lessonAccent.copy(alpha = 0.0f),
                                                                    lessonAccent.copy(alpha = 0.55f),
                                                                    lessonAccent.copy(alpha = 0.0f)
                                                                )
                                                            )
                                                        )
                                                )
                                                Text(
                                                    text = "سؤال تشریحی امتحان نهایی و پاسخ استاندارد:",
                                                    style = MaterialTheme.typography.labelMedium,
                                                    fontWeight = FontWeight.Bold,
                                                    color = lessonAccent,
                                                    maxLines = 1,
                                                    softWrap = false,
                                                    overflow = TextOverflow.Ellipsis
                                                )
                                                BiDiText(text = minusZeroGuide.finalExamWrittenQuestion, style = MaterialTheme.typography.bodySmall, fontWeight = FontWeight.Bold)
                                                BiDiText(text = minusZeroGuide.finalExamStandardAnswer, style = MaterialTheme.typography.bodySmall)
                                                Box(
                                                    modifier = Modifier
                                                        .fillMaxWidth()
                                                        .height(1.dp)
                                                        .background(
                                                            Brush.horizontalGradient(
                                                                listOf(
                                                                    lessonAccent.copy(alpha = 0.0f),
                                                                    lessonAccent.copy(alpha = 0.55f),
                                                                    lessonAccent.copy(alpha = 0.0f)
                                                                )
                                                            )
                                                        )
                                                )
                                                Text(
                                                    text = "نکات کنکوری و دام‌های تستی:",
                                                    style = MaterialTheme.typography.labelMedium,
                                                    fontWeight = FontWeight.Bold,
                                                    color = lessonAccent,
                                                    maxLines = 1,
                                                    softWrap = false,
                                                    overflow = TextOverflow.Ellipsis
                                                )
                                                minusZeroGuide.konkoorTipsAndTraps.forEach { tip ->
                                                    BiDiText(text = "• $tip", style = MaterialTheme.typography.bodySmall)
                                                }
                                                Box(
                                                    modifier = Modifier
                                                        .fillMaxWidth()
                                                        .height(1.dp)
                                                        .background(
                                                            Brush.horizontalGradient(
                                                                listOf(
                                                                    lessonAccent.copy(alpha = 0.0f),
                                                                    lessonAccent.copy(alpha = 0.55f),
                                                                    lessonAccent.copy(alpha = 0.0f)
                                                                )
                                                            )
                                                        )
                                                )
                                                Text(
                                                    text = "مسیر ۹ سطحی پیشرفت در این مبحث (از سطح ۰ تا سطح ۸):",
                                                    style = MaterialTheme.typography.labelMedium,
                                                    fontWeight = FontWeight.Bold,
                                                    color = lessonAccent,
                                                    maxLines = 1,
                                                    softWrap = false,
                                                    overflow = TextOverflow.Ellipsis
                                                )
                                                nineLevelPath.forEach { (levelTitle, levelDetail) ->
                                                    BiDiText(
                                                        text = "• $levelTitle: $levelDetail",
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
            }

            // ۲. گام‌های تدریس مفهومی کتاب درسی (همراه با جدول، مثال کاربردی مرتبط، و انیمیشن/نمودار در همان بخش)
            if (studySectionFilter in listOf(0, 4)) {
                // قدم‌به‌قدم: در صفحهٔ عمودی درس فقط بخش‌های بازشده نشان داده می‌شوند.
                val visibleSteps = enrichedLesson.steps.take(revealedStepCount.coerceIn(1, enrichedLesson.steps.size.coerceAtLeast(1)))
                itemsIndexed(
                    visibleSteps,
                    key = { _, step -> "${enrichedLesson.id}_step_${step.stepNumber}_${step.title.hashCode()}" }
                ) { stepIdx, step ->
                    val stepMaskKey = "step_body_mask_${step.stepNumber}"
                    val isStepMasked = isClozePracticeActive && revealedClozeKeys[stepMaskKey] != true
                    val displayedStepExplanation = remember(step.simpleExplanation, isStepMasked) {
                        if (!isStepMasked) step.simpleExplanation
                        else step.simpleExplanation.replace(Regex("«([^»]{2,45})»"), "« ░░░░ (ضربه بزن) ░░░░ »")
                    }
                    val pairedExample = remember(stepIdx, enrichedLesson.workedExamples) {
                        enrichedLesson.workedExamples.getOrNull(stepIdx)
                    }

                    // ورود ملایم هر کارت گام با فنر ژله‌ای + محو شدن کوتاه (فقط هنگام باز شدن درس)
                    AnimatedVisibility(
                        visible = stepsVisible,
                        enter = fadeIn(animationSpec = tween(300)) + expandVertically(animationSpec = JellyContentSizeSpring),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Column(
                            verticalArrangement = Arrangement.spacedBy(LessonSpacing.example)
                        ) {
                        Surface(
                            modifier = Modifier
                                .fillMaxWidth()
                                .then(
                                    if (isClozePracticeActive) {
                                        Modifier.clickable {
                                            revealedClozeKeys[stepMaskKey] = !(revealedClozeKeys[stepMaskKey] ?: false)
                                        }
                                    } else Modifier
                                ),
                            shape = RoundedCornerShape(18.dp),
                            color = HomeSurface(),
                            border = BorderStroke(1.dp, HomeBorder)
                        ) {
                            Column(
                                modifier = Modifier.padding(18.dp),
                                verticalArrangement = Arrangement.spacedBy(LessonSpacing.paragraph)
                            ) {
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.SpaceBetween
                                ) {
                                    Row(
                                        verticalAlignment = Alignment.CenterVertically,
                                        modifier = Modifier.weight(1f)
                                    ) {
                                        Surface(
                                            color = lessonAccent,
                                            shape = RoundedCornerShape(8.dp),
                                            modifier = Modifier.size(28.dp)
                                        ) {
                                            Box(contentAlignment = Alignment.Center) {
                                                Text(
                                                    text = BidiFormatter.formatMixedPersianText("${step.stepNumber}"),
                                                    color = HomeOnPrimary,
                                                    style = MaterialTheme.typography.labelMedium,
                                                    fontWeight = FontWeight.Bold
                                                )
                                            }
                                        }
                                        Spacer(modifier = Modifier.width(10.dp))
                                        BiDiText(
                                            text = "گام ${step.stepNumber}: ${step.title}",
                                            maxLines = 1,
                                            style = MaterialTheme.typography.titleMedium,
                                            fontWeight = FontWeight.Bold,
                                            color = HomeText,
                                            modifier = Modifier.weight(1f)
                                        )
                                    }

                                    if (isClozePracticeActive) {
                                        Surface(
                                            onClick = {
                                                revealedClozeKeys[stepMaskKey] = !(revealedClozeKeys[stepMaskKey] ?: false)
                                            },
                                            shape = RoundedCornerShape(6.dp),
                                            color = MaterialTheme.colorScheme.primaryContainer
                                        ) {
                                            Text(
                                                text = if (isStepMasked) "نمایش کلمات" else "محو کلمات",
                                                softWrap = false,
                                                style = MaterialTheme.typography.labelSmall,
                                                fontWeight = FontWeight.Bold,
                                                color = MaterialTheme.colorScheme.onPrimaryContainer,
                                                maxLines = 1,
                                                modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                                            )
                                        }
                                    }
                                }

                                BiDiText(
                                    text = displayedStepExplanation,
                                    style = MaterialTheme.typography.bodyLarge.copy(fontSize = 17.sp, lineHeight = 32.sp),
                                    isUnderlineEnabled = themeController.isUnderlineKeyPointsEnabled,
                                    isHighlighterEnabled = themeController.isHighlighterEnabled
                                )

                                if (step.visualDiagramText.isNotBlank()) {
                                    Spacer(modifier = Modifier.height(10.dp))
                                    SmartDiagramAndFormulaBlock(
                                        rawText = step.visualDiagramText,
                                        accentColor = lessonAccent
                                    )
                                    Spacer(modifier = Modifier.height(10.dp))
                                }

                                if (step.memoryTrick.isNotBlank()) {
                                    GoldenKeyPointCalloutCard(
                                        keyPointText = step.memoryTrick,
                                        title = "نکته‌سنجی و رمز گام ${step.stepNumber}",
                                        level = GoldenPointLevel.autoDetect(step.memoryTrick)
                                    )
                                }

                                if (stepRetryHintFor == step.stepNumber) {
                                    Surface(
                                        modifier = Modifier.fillMaxWidth(),
                                        shape = RoundedCornerShape(12.dp),
                                        color = HomeBackground,
                                        border = BorderStroke(1.dp, lessonAccent.copy(alpha = 0.45f))
                                    ) {
                                        BiDiText(
                                            text = "اشکالی ندارد! همین بخش را یک بار دیگر آرام بخوان؛ رمز گام را هم دوباره ببین، بعد «یاد گرفتم» را بزن.",
                                            style = MaterialTheme.typography.bodyMedium,
                                            fontWeight = FontWeight.Bold,
                                            color = lessonAccent,
                                            modifier = Modifier.padding(12.dp)
                                        )
                                    }
                                }

                                // مثال کاربردی مرتبط دقیقاً زیر مفهوم همین گام (در تب درس‌نامه)
                                if (pairedExample != null) {
                                    var showInlineSolution by remember(stepIdx) { mutableStateOf(false) }
                                    Spacer(modifier = Modifier.height(4.dp))
                                    Surface(
                                        modifier = Modifier.fillMaxWidth(),
                                        shape = RoundedCornerShape(14.dp),
                                        color = HomeBackground,
                                        border = BorderStroke(1.dp, HomeBorder)
                                    ) {
                                        Column(
                                            modifier = Modifier.padding(14.dp),
                                            verticalArrangement = Arrangement.spacedBy(8.dp)
                                        ) {
                                            Row(
                                                modifier = Modifier.fillMaxWidth(),
                                                horizontalArrangement = Arrangement.SpaceBetween,
                                                verticalAlignment = Alignment.CenterVertically
                                            ) {
                                                Text(
                                                    text = "مثال کاربردی مرتبط با گام ${BidiFormatter.toPersianDigits(step.stepNumber.toString())}",
                                                    maxLines = 1,
                                                    overflow = TextOverflow.Ellipsis,
                                                    style = MaterialTheme.typography.labelMedium,
                                                    fontWeight = FontWeight.Bold,
                                                    color = lessonAccent
                                                )
                                                Surface(
                                                    onClick = { showInlineSolution = !showInlineSolution },
                                                    shape = RoundedCornerShape(8.dp),
                                                    color = if (showInlineSolution) lessonAccent else HomeSurface(),
                                                    border = BorderStroke(1.dp, if (showInlineSolution) lessonAccent else HomeBorder)
                                                ) {
                                                    Text(
                                                        text = if (showInlineSolution) "بستن پاسخ" else "مشاهده پاسخ تشریحی",
                                                        maxLines = 1,
                                                        softWrap = false,
                                                        style = MaterialTheme.typography.labelSmall,
                                                        fontWeight = FontWeight.Bold,
                                                        color = if (showInlineSolution) HomeOnPrimary else lessonAccent,
                                                        modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp)
                                                    )
                                                }
                                            }
                                            BiDiText(
                                                text = pairedExample.question,
                                                style = MaterialTheme.typography.bodyMedium,
                                                fontWeight = FontWeight.SemiBold,
                                                color = HomeText
                                            )
                                            if (showInlineSolution) {
                                                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                                                    Box(
                                                        modifier = Modifier
                                                            .fillMaxWidth()
                                                            .height(1.dp)
                                                            .background(
                                                                Brush.horizontalGradient(
                                                                    listOf(
                                                                        lessonAccent.copy(alpha = 0.0f),
                                                                        lessonAccent.copy(alpha = 0.55f),
                                                                        lessonAccent.copy(alpha = 0.0f)
                                                                    )
                                                                )
                                                            )
                                                    )
                                                    SmartDiagramAndFormulaBlock(
                                                        rawText = pairedExample.stepByStepSolution,
                                                        accentColor = lessonAccent
                                                    )
                                                    if (pairedExample.commonTrapAlert.isNotBlank()) {
                                                        GoldenKeyPointCalloutCard(
                                                            keyPointText = pairedExample.commonTrapAlert,
                                                            title = "نکته مهم و دام طراح",
                                                            level = GoldenPointLevel.TRAFFIC_RED
                                                        )
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }

                        // نمودار تعاملی و انیمیشن آموزشی مرتبط بلافاصله پس از مفهوم اصلی (گام اول)
                        if (stepIdx == 0) {
                            var showInlineInteractive by remember(enrichedLesson.id) { mutableStateOf(false) }
                            Surface(
                                modifier = Modifier.fillMaxWidth(),
                                shape = RoundedCornerShape(16.dp),
                                color = HomeSurface(),
                                border = BorderStroke(1.dp, HomeBorder)
                            ) {
                                Column(
                                    modifier = Modifier.padding(14.dp),
                                    verticalArrangement = Arrangement.spacedBy(10.dp)
                                ) {
                                    Row(
                                        modifier = Modifier.fillMaxWidth(),
                                        horizontalArrangement = Arrangement.SpaceBetween,
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Column(modifier = Modifier.weight(1f)) {
                                            Text(
                                                text = "شبیه‌ساز و نمودار تعاملی این مبحث",
                                                maxLines = 1,
                                                overflow = TextOverflow.Ellipsis,
                                                style = MaterialTheme.typography.labelLarge,
                                                fontWeight = FontWeight.Bold,
                                                color = HomeText
                                            )
                                            Text(
                                                text = "مشاهده گام‌به‌گام و تعاملی مفهوم تدریس‌شده در همین بخش",
                                                maxLines = 1,
                                                overflow = TextOverflow.Ellipsis,
                                                style = MaterialTheme.typography.labelSmall,
                                                color = HomeMuted
                                            )
                                        }
                                        Surface(
                                            onClick = { showInlineInteractive = !showInlineInteractive },
                                            shape = RoundedCornerShape(10.dp),
                                            color = if (showInlineInteractive) lessonAccent else MaterialTheme.colorScheme.primaryContainer
                                        ) {
                                            Text(
                                                text = if (showInlineInteractive) "بستن شبیه‌ساز" else "اجرای شبیه‌ساز",
                                                maxLines = 1,
                                                softWrap = false,
                                                style = MaterialTheme.typography.labelSmall,
                                                fontWeight = FontWeight.Bold,
                                                color = if (showInlineInteractive) HomeOnPrimary else MaterialTheme.colorScheme.onPrimaryContainer,
                                                modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp)
                                            )
                                        }
                                    }
                                    if (showInlineInteractive) {
                                        LessonInteractiveVisualCard(
                                            lesson = enrichedLesson,
                                            subjectColor = lessonAccent
                                        )
                                    }
                                }
                            }
                        }
                    }
                    }
                }

                // فرمول‌ها و قواعد اصلی درس
                if (enrichedLesson.goldenFormulas.isNotEmpty()) {
                    item(key = "${enrichedLesson.id}_golden_formulas") {
                        Surface(
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(18.dp),
                            color = HomeSurface(),
                            border = BorderStroke(1.dp, HomeBorder)
                        ) {
                            Column(
                                modifier = Modifier.padding(18.dp),
                                verticalArrangement = Arrangement.spacedBy(10.dp)
                            ) {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Icon(
                                        imageVector = Icons.Default.Star,
                                        contentDescription = null,
                                        tint = lessonAccent,
                                        modifier = Modifier.size(18.dp)
                                    )
                                    Spacer(modifier = Modifier.width(6.dp))
                                    Text(
                                        text = "فرمول‌ها و قواعد اصلی درس",
                                        maxLines = 1,
                                        softWrap = false,
                                        style = MaterialTheme.typography.titleSmall,
                                        fontWeight = FontWeight.Bold,
                                        color = lessonAccent
                                    )
                                }
                                SmartDiagramAndFormulaBlock(
                                    rawText = enrichedLesson.goldenFormulas.joinToString("\n"),
                                    accentColor = lessonAccent
                                )
                            }
                        }
                    }
                }
            }

            // آموزش قدم‌به‌قدم: دکمه‌های «یاد گرفتم / یاد نگرفتم» زیر آخرین بخش بازشده (فقط تب درس‌نامه)
            if (enrichedLesson.steps.isNotEmpty()) {
                if (revealedStepCount < enrichedLesson.steps.size) {
                    item(key = "${enrichedLesson.id}_step_gate") {
                        StepLearnGateCard(
                            stepNumber = revealedStepCount,
                            totalSteps = enrichedLesson.steps.size,
                            accentColor = lessonAccent,
                            onLearned = {
                                stepRetryHintFor = null
                                revealedStepCount = (revealedStepCount + 1).coerceAtMost(enrichedLesson.steps.size)
                            },
                            onNotLearned = { stepRetryHintFor = revealedStepCount }
                        )
                    }
                } else if (!allStepsLearned) {
                    item(key = "${enrichedLesson.id}_step_gate_last") {
                        StepLearnGateCard(
                            stepNumber = enrichedLesson.steps.size,
                            totalSteps = enrichedLesson.steps.size,
                            accentColor = lessonAccent,
                            onLearned = { stepRetryHintFor = null; allStepsLearned = true },
                            onNotLearned = { stepRetryHintFor = enrichedLesson.steps.size }
                        )
                    }
                } else {
                    item(key = "${enrichedLesson.id}_steps_done") {
                        StepsCompletedCard(accentColor = lessonAccent)
                    }
                }
            }

            // ۳. مثال‌های حل‌شده و تست‌های خودآزمایی (تب ۱ و نمایش یکجا ۴ — استفاده یکپارچه از enrichedLesson)
            if (studySectionFilter in listOf(1, 4)) {
                itemsIndexed(
                    enrichedLesson.workedExamples,
                    key = { idx, _ -> "${enrichedLesson.id}_worked_ex_$idx" }
                ) { exIndex, ex ->
                    Surface(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(18.dp),
                        color = HomeSurface(),
                        border = BorderStroke(1.dp, HomeBorder)
                    ) {
                        var isDiagramExpanded by remember(exIndex) { mutableStateOf(false) }

                        Column(
                            modifier = Modifier.padding(18.dp),
                            verticalArrangement = Arrangement.spacedBy(12.dp)
                        ) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text(
                                    text = BidiFormatter.formatMixedPersianText("مثال حل‌شده ${exIndex + 1}:"),
                                    maxLines = 1,
                                    softWrap = false,
                                    style = MaterialTheme.typography.labelLarge,
                                    fontWeight = FontWeight.Bold,
                                    color = lessonAccent
                                )
                                Surface(
                                    modifier = Modifier
                                        .clip(RoundedCornerShape(8.dp))
                                        .clickable { isDiagramExpanded = !isDiagramExpanded },
                                    color = if (isDiagramExpanded) MaterialTheme.colorScheme.primaryContainer else HomeBackground,
                                    shape = RoundedCornerShape(8.dp),
                                    border = BorderStroke(1.dp, HomeBorder)
                                ) {
                                    Text(
                                        text = if (isDiagramExpanded) "▲ بستن نمودار" else "▼ مشاهده نمودار",
                                        maxLines = 1,
                                        softWrap = false,
                                        style = MaterialTheme.typography.labelSmall,
                                        fontWeight = FontWeight.SemiBold,
                                        color = lessonAccent,
                                        modifier = Modifier.padding(horizontal = 10.dp, vertical = 5.dp)
                                    )
                                }
                            }

                            BiDiText(
                                text = ex.question,
                                style = MaterialTheme.typography.titleSmall,
                                fontWeight = FontWeight.Bold
                            )

                            if (isDiagramExpanded) {
                                Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                                    WorkedExampleVisualDiagram(
                                        lesson = enrichedLesson,
                                        exampleIndex = exIndex,
                                        example = ex,
                                        accentColor = lessonAccent
                                    )

                                    // نمودار درختی و نقشه مفهومی
                                    Surface(
                                        shape = RoundedCornerShape(12.dp),
                                        color = HomeBackground,
                                        border = BorderStroke(1.dp, HomeBorder),
                                        modifier = Modifier.fillMaxWidth()
                                    ) {
                                        Column(
                                            modifier = Modifier.padding(12.dp),
                                            verticalArrangement = Arrangement.spacedBy(6.dp)
                                        ) {
                                            Text(
                                                text = "نمودار درختی و کالبدشکافی ساختار این مثال:",
                                                maxLines = 1,
                                                overflow = TextOverflow.Ellipsis,
                                                style = MaterialTheme.typography.labelSmall,
                                                fontWeight = FontWeight.Bold,
                                                color = lessonAccent
                                            )
                                            val treeLines = remember(ex.question, ex.stepByStepSolution) {
                                                buildList {
                                                    add("سرنخ اصلی ──> ${ex.question}")
                                                    ex.stepByStepSolution.lines().filter { it.isNotBlank() }.take(3).forEachIndexed { sIdx, sLine ->
                                                        val prefix = if (sIdx == 0) "├── گام ۱" else if (sIdx == 1) "├── گام ۲" else "└── نتیجه"
                                                        add("$prefix ──> ${sLine.trim()}")
                                                    }
                                                }
                                            }
                                            SmartDiagramAndFormulaBlock(
                                                rawText = treeLines.joinToString("\n"),
                                                accentColor = lessonAccent
                                            )
                                        }
                                    }
                                }
                            }

                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(1.dp)
                                    .background(
                                        Brush.horizontalGradient(
                                            listOf(
                                                lessonAccent.copy(alpha = 0.0f),
                                                lessonAccent.copy(alpha = 0.55f),
                                                lessonAccent.copy(alpha = 0.0f)
                                            )
                                        )
                                    )
                            )

                            Text(
                                text = "گام‌های حل تشریحی:",
                                maxLines = 1,
                                softWrap = false,
                                style = MaterialTheme.typography.labelSmall,
                                fontWeight = FontWeight.Bold,
                                color = lessonAccent
                            )

                            SmartDiagramAndFormulaBlock(
                                rawText = ex.stepByStepSolution,
                                accentColor = lessonAccent
                            )
                            if (ex.commonTrapAlert.isNotBlank()) {
                                GoldenKeyPointCalloutCard(
                                    keyPointText = ex.commonTrapAlert,
                                    title = "تله کنکوری و دام طراح",
                                    level = GoldenPointLevel.TRAFFIC_RED,
                                    tags = listOf("تله_طراح", "دام_تستی")
                                )
                            }
                        }
                    }
                }

                // Textbook Summary & Interactive Mini-Check
                item(key = "${enrichedLesson.id}_summary_check") {
                    val mini = enrichedLesson.miniCheck
                    Surface(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(18.dp),
                        color = HomeSurface(),
                        border = BorderStroke(1.dp, HomeBorder)
                    ) {
                        Column(
                            modifier = Modifier.padding(18.dp),
                            verticalArrangement = Arrangement.spacedBy(10.dp)
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(
                                    imageVector = Icons.Default.CheckCircle,
                                    contentDescription = null,
                                    tint = HomeGreen,
                                    modifier = Modifier.size(18.dp)
                                )
                                Spacer(modifier = Modifier.width(6.dp))
                                Text(
                                    text = "سوال خودآزمایی انتهای درس:",
                                    maxLines = 1,
                                    softWrap = false,
                                    style = MaterialTheme.typography.titleSmall,
                                    fontWeight = FontWeight.Bold,
                                    color = HomeText
                                )
                            }
                            BiDiText(
                                text = "پرسش: ${mini.question}",
                                style = MaterialTheme.typography.bodyMedium,
                                fontWeight = FontWeight.Bold
                            )

                            if (mini.options.isNotEmpty()) {
                                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                                    mini.options.forEachIndexed { optIdx, optText ->
                                        val optNumFa = BidiFormatter.toPersianDigits((optIdx + 1).toString())
                                        val isSelected = selectedMiniOption == optIdx
                                        val isCorrect = optIdx == mini.correctIndex
                                        val bgColor = when {
                                            (showMiniAnswer || selectedMiniOption != -1) && isCorrect ->
                                                MaterialTheme.colorScheme.tertiaryContainer
                                            isSelected && !isCorrect ->
                                                MaterialTheme.colorScheme.errorContainer
                                            else -> HomeBackground
                                        }
                                        val borderColor = when {
                                            (showMiniAnswer || selectedMiniOption != -1) && isCorrect ->
                                                HomeGreen
                                            isSelected && !isCorrect ->
                                                MaterialTheme.colorScheme.error
                                            else -> HomeBorder
                                        }
                                        Surface(
                                            modifier = Modifier
                                                .fillMaxWidth()
                                                .clip(RoundedCornerShape(10.dp))
                                                .clickable {
                                                    // فقط اولین پاسخ ثبت می‌شود؛ تغییر جواب بعد از
                                                    // دیدن پاسخ درست، «تلاش» تازه نیست.
                                                    val firstTap = selectedMiniOption == -1
                                                    selectedMiniOption = optIdx
                                                    showMiniAnswer = true
                                                    if (firstTap) {
                                                        onMiniCheckAnswered?.invoke(
                                                            enrichedLesson,
                                                            optIdx == mini.correctIndex
                                                        )
                                                    }
                                                },
                                            color = bgColor,
                                            shape = RoundedCornerShape(10.dp),
                                            border = BorderStroke(1.dp, borderColor)
                                        ) {
                                            Row(
                                                modifier = Modifier.padding(horizontal = 12.dp, vertical = 10.dp),
                                                verticalAlignment = Alignment.CenterVertically
                                            ) {
                                                BiDiText(
                                                    text = "گزینه $optNumFa) $optText",
                                                    style = MaterialTheme.typography.bodySmall,
                                                    fontWeight = if (isCorrect && (showMiniAnswer || selectedMiniOption != -1)) FontWeight.Bold else FontWeight.Normal,
                                                    modifier = Modifier.weight(1f)
                                                )
                                                if ((showMiniAnswer || selectedMiniOption != -1) && isCorrect) {
                                                    Text(
                                                        text = "✓ پاسخ صحیح",
                                                        maxLines = 1,
                                                        softWrap = false,
                                                        style = MaterialTheme.typography.labelSmall,
                                                        fontWeight = FontWeight.Bold,
                                                        color = HomeGreen
                                                    )
                                                }
                                            }
                                        }
                                    }
                                }
                            }

                            Surface(
                                color = HomeBackground,
                                shape = RoundedCornerShape(10.dp),
                                border = BorderStroke(1.dp, HomeBorder),
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Column(
                                    modifier = Modifier.padding(12.dp),
                                    verticalArrangement = Arrangement.spacedBy(6.dp)
                                ) {
                                    if (selectedMiniOption != -1 && selectedMiniOption != mini.correctIndex) {
                                        Text(
                                            text = "تحلیل مرحله‌ای علت اشتباه (آموزش مجدد بدون سرزنش):",
                                            maxLines = 1,
                                            overflow = TextOverflow.Ellipsis,
                                            style = MaterialTheme.typography.labelMedium,
                                            fontWeight = FontWeight.Bold,
                                            color = MaterialTheme.colorScheme.error
                                        )
                                        val diagText = minusZeroGuide.guidedExerciseStepDiagnosis
                                            .getOrNull(selectedMiniOption)
                                            ?: minusZeroGuide.commonMistakeAndPrevention
                                        BiDiText(
                                            text = diagText,
                                            style = MaterialTheme.typography.bodySmall
                                        )
                                        Box(
                                            modifier = Modifier
                                                .fillMaxWidth()
                                                .height(1.dp)
                                                .background(
                                                    Brush.horizontalGradient(
                                                        listOf(
                                                            lessonAccent.copy(alpha = 0.0f),
                                                            lessonAccent.copy(alpha = 0.55f),
                                                            lessonAccent.copy(alpha = 0.0f)
                                                        )
                                                    )
                                                )
                                        )
                                    }
                                    Text(
                                        text = "پاسخ تشریحی و دلیل علمی:",
                                        maxLines = 1,
                                        softWrap = false,
                                        style = MaterialTheme.typography.labelMedium,
                                        fontWeight = FontWeight.Bold,
                                        color = lessonAccent
                                    )
                                    BiDiText(
                                        text = mini.explanation,
                                        style = MaterialTheme.typography.bodySmall
                                    )
                                }
                            }
                        }
                    }
                }
            }

            // منوی واحد «ابزارها» — پیش‌فرض جمع‌شده تا متن درس بدون مزاحم خوانده شود؛ هیچ کارتی حذف نشده است
            item(key = "${lesson.id}_more_lesson_cards_toggle") {
                Surface(
                    onClick = { showMoreLessonCards = !showMoreLessonCards },
                    shape = RoundedCornerShape(14.dp),
                    color = HomeSurface(),
                    border = BorderStroke(1.dp, HomeBorder),
                    modifier = Modifier.fillMaxWidth().testTag("more_lesson_cards_toggle")
                ) {
                    Row(
                        modifier = Modifier.padding(horizontal = 16.dp, vertical = 10.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                text = if (showMoreLessonCards) "بستن ابزارها" else "ابزارها",
                                maxLines = 1,
                                softWrap = false,
                                style = MaterialTheme.typography.titleSmall,
                                fontWeight = FontWeight.Bold,
                                color = lessonAccent
                            )
                            Text(
                                text = "یادداشت، کارگاه، نکات تستی، روش مطالعه",
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis,
                                style = MaterialTheme.typography.labelSmall,
                                color = HomeMuted
                            )
                        }
                        Text(
                            text = if (showMoreLessonCards) "▲" else "▼",
                            style = MaterialTheme.typography.labelLarge,
                            fontWeight = FontWeight.Bold,
                            color = lessonAccent
                        )
                    }
                }
            }

            // نکات کلیدی همین درس — در صفحهٔ عمودی، بعد از مثال و تست، بدون تب و بدون ابزارها.
            item(key = "${enrichedLesson.id}_own_key_points") {
                LessonOwnKeyPointsCard(
                    lesson = enrichedLesson,
                    accentColor = lessonAccent
                )
            }

            if (showMoreLessonCards) {
            // ۴. کارگاه تصویری تعاملی و نمودارها (در منوی «ابزارها» — استفاده از enrichedLesson)
                item(key = "${enrichedLesson.id}_interactive_visual_card") {
                    LessonInteractiveVisualCard(
                        lesson = enrichedLesson,
                        subjectColor = lessonAccent
                    )
                }

                item(key = "${lesson.id}_visual_memory_tree_card") {
                    com.example.ui.components.VisualMemoryTreeAndTimelineCard(
                        lesson = lesson,
                        subjectIdFilter = lesson.normalizedSubjectId(),
                        accentColor = lessonAccent
                    )
                }

            // ۵. نکات تستی و رمزهای ۵ ثانیه‌ای — در منوی «ابزارها»
                item(key = "${lesson.id}_slow_learner_super_method") {
                    SlowLearnerSuperMethodCard(
                        lesson = lesson,
                        accentColor = lessonAccent
                    )
                }

                item(key = "${lesson.id}_advanced_mnemonic_method") {
                    Surface(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(18.dp),
                        color = HomeSurface(),
                        border = BorderStroke(1.dp, HomeBorder)
                    ) {
                        Column(
                            modifier = Modifier.padding(18.dp),
                            verticalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(
                                    imageVector = Icons.Default.Bolt,
                                    contentDescription = null,
                                    tint = lessonAccent,
                                    modifier = Modifier.size(18.dp)
                                )
                                Spacer(modifier = Modifier.width(6.dp))
                                BiDiText(
                                    text = advancedMethod.methodTitle,
                                    maxLines = 1,
                                    style = MaterialTheme.typography.titleSmall,
                                    fontWeight = FontWeight.Bold,
                                    color = lessonAccent,
                                    modifier = Modifier.weight(1f)
                                )
                            }
                            Surface(
                                color = HomeBackground,
                                shape = RoundedCornerShape(10.dp),
                                border = BorderStroke(1.dp, HomeBorder),
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Column(
                                    modifier = Modifier.padding(12.dp),
                                    verticalArrangement = Arrangement.spacedBy(6.dp)
                                ) {
                                    BiDiText(
                                        text = "کد رمزی حافظه: ${advancedMethod.mnemonicCode}",
                                        style = MaterialTheme.typography.bodySmall,
                                        fontWeight = FontWeight.Bold,
                                        color = lessonAccent
                                    )
                                    BiDiText(
                                        text = advancedMethod.fiveSecondAlgorithm,
                                        style = MaterialTheme.typography.bodySmall,
                                        color = HomeText
                                    )
                                    BiDiText(
                                        text = "مثال ۵ ثانیه‌ای: ${advancedMethod.exampleShortcut}",
                                        style = MaterialTheme.typography.bodySmall,
                                        fontWeight = FontWeight.Bold,
                                        color = HomeGreen
                                    )
                                }
                            }
                            if (themeController.showMnemonicsAndMemoryTricks) {
                                Surface(
                                    color = HomeBackground,
                                    shape = RoundedCornerShape(10.dp),
                                    border = BorderStroke(1.dp, HomeBorder),
                                    modifier = Modifier.fillMaxWidth()
                                ) {
                                    Column(
                                        modifier = Modifier.padding(12.dp),
                                        verticalArrangement = Arrangement.spacedBy(6.dp)
                                    ) {
                                        Text(
                                            text = "رمزیادگیری علمی ۶بخشی و روش جایگزین بدون حفظ کردن:",
                                            maxLines = 1,
                                            overflow = TextOverflow.Ellipsis,
                                            style = MaterialTheme.typography.labelMedium,
                                            fontWeight = FontWeight.Bold,
                                            color = lessonAccent
                                        )
                                        sixPartMnemonic.forEach { (partTitle, partBody) ->
                                            BiDiText(
                                                text = "$partTitle: $partBody",
                                                style = MaterialTheme.typography.bodySmall
                                            )
                                        }
                                    }
                                }
                            }
                        }
                    }
                }

                item(key = "${lesson.id}_exam_nuance") {
                    ExamNuanceAnalysisCard(
                        lesson = lesson,
                        accentColor = lessonAccent
                    )
                }

                item(key = "${lesson.id}_scientific_method") {
                    ScientificLessonStudyMethodCard(
                        lesson = lesson,
                        accentColor = lessonAccent
                    )
                }

            // ۶. یادداشت شخصی و دکمه تکمیل درس — در منوی «ابزارها»
            item(key = "${lesson.id}_personal_notes") {
                Surface(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(18.dp),
                    color = HomeSurface(),
                    border = BorderStroke(1.dp, HomeBorder)
                ) {
                    Column(
                        modifier = Modifier.padding(18.dp),
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        Text(
                            text = "یادداشت شخصی من برای این درس:",
                            maxLines = 1,
                            softWrap = false,
                            style = MaterialTheme.typography.titleSmall,
                            fontWeight = FontWeight.Bold,
                            color = HomeText
                        )
                        OutlinedTextField(
                            value = userNote,
                            onValueChange = { userNote = it },
                            placeholder = { Text("هر نکته یا خلاصه‌ای که مدنظر دارید اینجا بنویسید...", style = MaterialTheme.typography.bodySmall) },
                            modifier = Modifier
                                .fillMaxWidth()
                                .testTag("lesson_note_input"),
                            shape = RoundedCornerShape(12.dp),
                            minLines = 2
                        )

                        Button(
                            onClick = {
                                isCompleted = !isCompleted
                                onSaveState(
                                    isCompleted,
                                    isBookmarked,
                                    userNote,
                                    progress?.masteryPercent ?: 0
                                )
                            },
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(48.dp)
                                .testTag("complete_lesson_button"),
                            shape = RoundedCornerShape(12.dp),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = if (isCompleted) HomeGreen else HomePrimary,
                                contentColor = if (isCompleted) MaterialTheme.colorScheme.onTertiary else HomeOnPrimary
                            )
                        ) {
                            Icon(
                                imageVector = Icons.Default.CheckCircle,
                                contentDescription = null,
                                modifier = Modifier.size(18.dp)
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(
                                text = if (isCompleted) "این درس کامل مطالعه شد (ذخیره یادداشت)" else "ثبت مطالعه کامل این درس و ذخیره یادداشت",
                                style = MaterialTheme.typography.labelLarge,
                                fontWeight = FontWeight.Bold,
                                maxLines = 1,
                                softWrap = false,
                                overflow = TextOverflow.Ellipsis
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
private fun ScientificLessonStudyMethodCard(
    lesson: ZeroLesson,
    accentColor: Color
) {
    val responsive = LocalResponsiveDimens.current
    var expanded by remember(lesson.id) { mutableStateOf(true) }
    val keyConceptSummary = remember(lesson.id) {
        lesson.goldenFormulas.firstOrNull()
            ?: lesson.steps.firstOrNull()?.memoryTrick?.takeIf { it.isNotBlank() }
            ?: lesson.subtitle
    }

    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(responsive.cardCornerRadius))
            .clickable { expanded = !expanded }
            .testTag("scientific_study_method_card"),
        shape = RoundedCornerShape(responsive.cardCornerRadius),
        color = MaterialTheme.colorScheme.surface,
        border = BorderStroke(0.7.dp, accentColor.copy(alpha = 0.35f))
    ) {
        Column(
            modifier = Modifier.padding(responsive.cardInnerPadding),
            verticalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.weight(1f)
                ) {
                    Surface(
                        color = accentColor.copy(alpha = 0.14f),
                        shape = RoundedCornerShape(7.dp),
                        modifier = Modifier.size(26.dp)
                    ) {
                        Box(contentAlignment = Alignment.Center) {
                            Icon(
                                imageVector = Icons.Default.Psychology,
                                contentDescription = null,
                                tint = accentColor,
                                modifier = Modifier.size(15.dp)
                            )
                        }
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    Column {
                        Text(
                            text = "🧠 روش علمی مطالعه این درس (فاینمن + بازیابی فعال)",
                            style = MaterialTheme.typography.titleSmall,
                            fontWeight = FontWeight.Bold,
                            color = accentColor
                        )
                        Text(
                            text = "چطور این مبحث را در ۱۵ دقیقه برای همیشه در حافظه بلندمدت قفل کنی؟",
                            style = MaterialTheme.typography.labelSmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
                Icon(
                    imageVector = if (expanded) Icons.Default.ExpandLess else Icons.Default.ExpandMore,
                    contentDescription = null,
                    tint = accentColor,
                    modifier = Modifier.size(18.dp)
                )
            }

            if (expanded) {
                HorizontalDivider(thickness = 0.5.dp, color = accentColor.copy(alpha = 0.2f))
                BiDiText(
                    text = "• گام ۱ (تکنیک فاینمن): بعد از خواندن گام‌های زیر، چشمانت را ببند و بدون نگاه کردن، هستهٔ اصلی «${lesson.title}» را با زبان ساده برای خودت توضیح بده.\n" +
                        "• گام ۲ (هستهٔ بولد و امتحانی): «$keyConceptSummary» را روی یک برگه کرنل در ۱ خط یادداشت کن.\n" +
                        "• گام ۳ (بازیابی فعال و مرور ۱-۳-۷-۳۰): بلافاصله ۱ مثال حل‌شده پایین صفحه را بدون دیدن جواب حل کن و فردا، ۳ روز بعد و ۷ روز بعد مرور ۲ دقیقه‌ای انجام بده.",
                    style = MaterialTheme.typography.bodySmall
                )
            }
        }
    }
}

@Composable
fun PsychologyMoraleCard(
    themeController: AppThemeController,
    modifier: Modifier = Modifier
) {
    val affirmations = remember {
        listOf(
            "🌱 «آرامش، کلید تمرکز است» — با گام‌های آهسته اما پیوسته، تمام مباحث کنکور انسانی و فرهنگیان کاملاً در ذهنت تثبیت می‌شوند.",
            "✨ «به توانایی و استعدادت اعتماد کن» — هر مبحثی که امروز حتی نصفه یاد می‌گیری، یک قدم بزرگ به سمت صندلی دانشگاه فرهنگیان است.",
            "💡 «ذهن آرام، یادگیری را دوچندان می‌کند» — یک نفس عمیق بکش؛ استرس کنکور طبیعی است اما تو با مطالعه منظم قوی‌تر از هر اضطرابی هستی.",
            "🏆 «موفقیت‌های بزرگ حاصل تکرار قدم‌های کوچک روزانه است» — فقط به برنامه امروزت متعهد باش، فردا خودش ساخته می‌شود.",
            "🌿 «هیچ زحمتی در این مسیر بی‌پاسخ نمی‌ماند» — تمام این نکته‌برداری‌ها و تست‌ها، پایه‌های معلمی دلسوز و موفق در آینده کشور را می‌سازند.",
            "☕ «خستگی، نشانه پیشرفت است نه شکست» — هر زمان حس کردی کشش نداری، ۱۰ دقیقه چشمانت را ببند، آب بنوش و دوباره با طراوت شروع کن."
        )
    }
    var quoteIndex by remember { mutableIntStateOf(0) }

    Card(
        modifier = modifier
            .fillMaxWidth()
            .testTag("psychology_morale_card"),
        shape = RoundedCornerShape(18.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.5f)
        ),
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.primary.copy(alpha = 0.25f))
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(14.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.weight(1f)
                ) {
                    Surface(
                        color = MaterialTheme.colorScheme.primary.copy(alpha = 0.18f),
                        shape = CircleShape,
                        modifier = Modifier.size(30.dp)
                    ) {
                        Box(contentAlignment = Alignment.Center) {
                            Icon(
                                imageVector = Icons.Default.Spa,
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.primary,
                                modifier = Modifier.size(16.dp)
                            )
                        }
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "پیام آرامش و انرژی امروز",
                        style = MaterialTheme.typography.titleSmall,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onPrimaryContainer,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }

                Surface(
                    onClick = {
                        quoteIndex = (quoteIndex + 1) % affirmations.size
                    },
                    color = MaterialTheme.colorScheme.surface.copy(alpha = 0.85f),
                    shape = RoundedCornerShape(50),
                    border = BorderStroke(1.dp, MaterialTheme.colorScheme.primary.copy(alpha = 0.35f)),
                    modifier = Modifier.testTag("next_morale_quote_button")
                ) {
                    Row(
                        modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Autorenew,
                            contentDescription = "پیام بعدی",
                            tint = MaterialTheme.colorScheme.primary,
                            modifier = Modifier.size(14.dp)
                        )
                        Text(
                            text = "پیام بعدی",
                            style = MaterialTheme.typography.labelSmall,
                            color = MaterialTheme.colorScheme.primary,
                            fontWeight = FontWeight.Bold,
                            maxLines = 1,
                            softWrap = false
                        )
                    }
                }
            }

            Text(
                text = BidiFormatter.formatMixedPersianText(affirmations[quoteIndex]),
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onPrimaryContainer,
                lineHeight = 23.sp
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DinOZendegiHeroCalloutCard(
    onOpenDiniLessons: () -> Unit
) {
    var showDetailsDialog by remember { mutableStateOf(false) }

    if (showDetailsDialog) {
        ModalBottomSheet(
            onDismissRequest = { showDetailsDialog = false },
            sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .verticalScroll(rememberScrollState())
                    .padding(horizontal = 20.dp, vertical = 14.dp),
                verticalArrangement = Arrangement.spacedBy(14.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Surface(
                            shape = CircleShape,
                            color = Color(0xFF15803D),
                            modifier = Modifier.size(38.dp)
                        ) {
                            Box(contentAlignment = Alignment.Center) {
                                Icon(
                                    painter = painterResource(R.drawable.ic_leaf_dini),
                                    contentDescription = null,
                                    tint = Color.White,
                                    modifier = Modifier.size(20.dp)
                                )
                            }
                        }
                        Spacer(modifier = Modifier.width(10.dp))
                        Column {
                            Text(
                                text = "تحلیل جزئیات سنجش: جایگاه دینی در کنکور و نهایی",
                                style = MaterialTheme.typography.titleSmall,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.tertiary
                            )
                            Text(
                                text = "مصوبه شورای عالی انقلاب فرهنگی (کنکور ۱۴۰۲ تا ۱۴۰۶)",
                                style = MaterialTheme.typography.labelSmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    }
                    IconButton(onClick = { showDetailsDialog = false }) {
                        Icon(Icons.Default.Close, contentDescription = "بستن")
                    }
                }

                Surface(
                    shape = RoundedCornerShape(14.dp),
                    color = MaterialTheme.colorScheme.tertiaryContainer.copy(alpha = 0.35f),
                    border = BorderStroke(1.dp, MaterialTheme.colorScheme.tertiary.copy(alpha = 0.45f)),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(
                        modifier = Modifier.padding(14.dp),
                        verticalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                imageVector = Icons.Default.Info,
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.tertiary,
                                modifier = Modifier.size(20.dp)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = "۱. حذف دروس عمومی از دفترچه کنکور سراسری:",
                                style = MaterialTheme.typography.titleSmall,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.tertiary
                            )
                        }
                        Text(
                            text = "طبق مصوبه شورای عالی انقلاب فرهنگی از کنکور سال ۱۴۰۲ به بعد، تمام دروس عمومی (دین و زندگی، ادبیات عمومی، عربی عمومی و زبان انگلیسی) از دفترچه‌های کنکور سراسری (دفترچه ۱ و ۲ نوبت اردیبهشت و تیر) کاملاً حذف شده‌اند و سوال مستقیمی در کنکور سراسری ندارند.",
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurface,
                            lineHeight = 22.sp
                        )
                    }
                }

                Surface(
                    shape = RoundedCornerShape(14.dp),
                    color = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.35f),
                    border = BorderStroke(1.dp, MaterialTheme.colorScheme.primary.copy(alpha = 0.35f)),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(
                        modifier = Modifier.padding(14.dp),
                        verticalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        Text(
                            text = "۲. نقش حیاتی دین و زندگی در ۶۰٪ سوابق تحصیلی نهایی:",
                            style = MaterialTheme.typography.titleSmall,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.primary
                        )
                        Text(
                            text = "تأثیر دروس عمومی به سوابق تحصیلی امتحانات نهایی منتقل شده است. نمره امتحان نهایی «دین و زندگی ۳» پایه دوازدهم با ضریب ۱۴.۲۳، بالاترین ضریب را در بین تمام دروس عمومی و حتی بیشتر از برخی اختصاصی‌ها در سوابق تحصیلی دارد!\n\n• دینی ۳ دوازدهم: ضریب ۱۴.۲۳\n• فارسی ۳: ضریب ۱۸.۶۴\n• زبان انگلیسی ۳: ضریب ۱۰.۱۷\n• عربی ۳: ضریب ۷.۸۰",
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurface,
                            lineHeight = 22.sp
                        )
                    }
                }

                Surface(
                    shape = RoundedCornerShape(14.dp),
                    color = MaterialTheme.colorScheme.secondaryContainer.copy(alpha = 0.35f),
                    border = BorderStroke(1.dp, MaterialTheme.colorScheme.secondary.copy(alpha = 0.35f)),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(
                        modifier = Modifier.padding(14.dp),
                        verticalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        Text(
                            text = "۳. نقش مستقیم دین و زندگی در دفترچه اختصاصی فرهنگیان (دفترچه ۳):",
                            style = MaterialTheme.typography.titleSmall,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.secondary
                        )
                        Text(
                            text = "در کنکور اختصاصی دانشگاه فرهنگیان، دفترچه شماره ۳ حاوی ماده امتحانی «تعلیم و تربیت اسلامی» است که ۵۰٪ سوالات آن (۲۰ سوال تست کنکوری) مستقیماً از کتاب‌های دین و زندگی پایه دهم، یازدهم و دوازدهم طرح می‌شود. بنابراین برای متقاضیان فرهنگیان، خواندن تست‌های دینی الزامی است!",
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurface,
                            lineHeight = 22.sp
                        )
                    }
                }

                Button(
                    onClick = {
                        showDetailsDialog = false
                        onOpenDiniLessons()
                    },
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.tertiary,
                        contentColor = MaterialTheme.colorScheme.onTertiary
                    ),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Text(
                        text = "فهمیدم! ورود به درسنامه‌های دین و زندگی ➔",
                        style = MaterialTheme.typography.titleSmall,
                        fontWeight = FontWeight.Bold
                    )
                }

                Spacer(modifier = Modifier.height(20.dp))
            }
        }
    }

    Surface(
        shape = RoundedCornerShape(18.dp),
        color = MaterialTheme.colorScheme.tertiaryContainer.copy(alpha = 0.28f),
        border = BorderStroke(1.2.dp, MaterialTheme.colorScheme.tertiary.copy(alpha = 0.45f)),
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(18.dp))
            .clickable { onOpenDiniLessons() }
            .testTag("din_o_zendegi_hero_card")
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(14.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.weight(1f)
                ) {
                    Surface(
                        shape = CircleShape,
                        color = MaterialTheme.colorScheme.tertiaryContainer,
                        modifier = Modifier.size(42.dp)
                    ) {
                        Box(contentAlignment = Alignment.Center) {
                            Icon(
                                painter = painterResource(R.drawable.ic_leaf_dini),
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.onTertiaryContainer,
                                modifier = Modifier.size(22.dp)
                            )
                        }
                    }
                    Spacer(modifier = Modifier.width(12.dp))
                    Column(verticalArrangement = Arrangement.spacedBy(3.dp)) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(
                                text = "دین و زندگی ۱، ۲ و ۳ جامع انسانی",
                                style = MaterialTheme.typography.titleSmall,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.onSurface
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Surface(
                                shape = RoundedCornerShape(6.dp),
                                color = MaterialTheme.colorScheme.tertiaryContainer
                            ) {
                                Text(
                                    text = "ضریب ۱۴.۲۳ نهایی + دفترچه ۳ فرهنگیان",
                                    style = MaterialTheme.typography.labelSmall,
                                    fontWeight = FontWeight.Bold,
                                    color = MaterialTheme.colorScheme.onTertiaryContainer,
                                    modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                                )
                            }
                        }
                        Text(
                            text = "دروس عمومی از کنکور سراسری حذف شده‌اند؛ اما دینی بالاترین ضریب نهایی (۱۴.۲۳) و ۵۰٪ سوالات دفترچه ۳ فرهنگیان است!",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                OutlinedButton(
                    onClick = { showDetailsDialog = true },
                    border = BorderStroke(1.dp, MaterialTheme.colorScheme.tertiary),
                    contentPadding = PaddingValues(horizontal = 10.dp, vertical = 4.dp),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Text(
                        text = "جزئیات مصوبه سنجش ℹ️",
                        style = MaterialTheme.typography.labelMedium,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.tertiary
                    )
                }

                Button(
                    onClick = onOpenDiniLessons,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.tertiary,
                        contentColor = MaterialTheme.colorScheme.onTertiary
                    ),
                    contentPadding = PaddingValues(horizontal = 14.dp, vertical = 6.dp),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Text(
                        text = "ورود به دروس دینی ➔",
                        style = MaterialTheme.typography.labelMedium,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    }
}

@Composable
fun OfficialSourceLinksCard() {
    Surface(
        shape = RoundedCornerShape(16.dp),
        color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.35f),
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.4f)),
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier.padding(14.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = Icons.Default.Info,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.size(20.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "🌐 مراجع و لینک‌های مستقیم استعلام صحت اطلاعات:",
                    style = MaterialTheme.typography.titleSmall,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary
                )
            }

            val links = listOf(
                "سازمان سنجش آموزش کشور (دفترچه‌ها و اخبار کنکور)" to "https://www.sanjesh.org",
                "پنجره خدمات آموزش و پرورش (ثبت‌نام نهایی و ترمیم معدل)" to "https://my.medu.ir",
                "پایگاه کشوری چاپ و توزیع کتاب‌های درسی (PDF کتب رسمی)" to "http://chap.sch.ir",
                "شورای عالی انقلاب فرهنگی (مصوبات کنکور و سوابق تحصیلی)" to "https://sccr.ir",
                "دانشگاه فرهنگیان (اخبار پذیرش دانشجو-معلم)" to "https://cfu.ac.ir"
            )

            links.forEach { (title, url) ->
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 3.dp)
                ) {
                    Text(
                        text = "• $title:",
                        style = MaterialTheme.typography.bodySmall,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    Text(
                        text = url,
                        style = MaterialTheme.typography.labelMedium,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary
                    )
                }
            }
        }
    }
}

// ==========================================
// DEDICATED SUBJECT HUB SUB-VIEWS
// ==========================================

@Composable
/**
 * سربرگ یک بخش از صفحهٔ کتاب (عمودی): تیتر + رنگ موضوع.
 */
@Composable
private fun BookPageSectionHeader(
    title: String,
    accentColor: Color
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(width = 4.dp, height = 22.dp)
                .clip(RoundedCornerShape(2.dp))
                .background(accentColor)
        )
        Spacer(modifier = Modifier.width(10.dp))
        Text(
            text = title,
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold,
            color = accentColor,
            maxLines = 1,
            softWrap = false
        )
    }
}

fun SubjectTestBankView(
    subjectId: String,
    subjectTitle: String,
    accentColor: Color,
    lessons: List<ZeroLesson>
) {
    val responsive = LocalResponsiveDimens.current

    val quizItems = remember(subjectId, lessons) {
        lessons.map { lesson ->
            Pair(lesson.title, lesson.miniCheck)
        }
    }

    if (quizItems.isEmpty()) {
        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .padding(responsive.horizontalPadding),
            color = accentColor.copy(alpha = 0.08f),
            shape = RoundedCornerShape(12.dp)
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "📝 سوالات و تست‌های اختصاصی $subjectTitle",
                    style = MaterialTheme.typography.titleSmall,
                    fontWeight = FontWeight.Bold,
                    color = accentColor
                )
                Spacer(modifier = Modifier.height(6.dp))
                Text(
                    text = "تست‌های خرداد و کنکور این درس به ترتیب در انتهای گام‌های درسنامه قرار دارند. می‌توانید از روی صفحهٔ درس هر گام را مطالعه و تست مربوطه را حل کنید.",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    textAlign = TextAlign.Center
                )
            }
        }
    } else {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 6.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
                Surface(
                    color = accentColor.copy(alpha = 0.08f),
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Row(
                        modifier = Modifier.padding(12.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Default.Quiz,
                            contentDescription = null,
                            tint = accentColor,
                            modifier = Modifier.size(24.dp)
                        )
                        Spacer(modifier = Modifier.width(10.dp))
                        Column {
                            Text(
                                text = "📝 بانک تست و خودآزمایی $subjectTitle (${BidiFormatter.toPersianDigits(quizItems.size.toString())} تست)",
                                style = MaterialTheme.typography.titleSmall,
                                fontWeight = FontWeight.Bold,
                                color = accentColor
                            )
                            Text(
                                text = "برای پاسخگویی، روی گزینه مورد نظر کلیک کنید تا پاسخ تشریحی و تحلیل گزینه صحیح نمایش داده شود.",
                                style = MaterialTheme.typography.labelSmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    }
                }

            quizItems.forEachIndexed { index, (lessonTitle, q) ->
                var selectedOption by remember("quiz_${subjectId}_$index") { mutableStateOf<Int?>(null) }
                var showExplanation by remember("quiz_${subjectId}_$index") { mutableStateOf(false) }

                Surface(
                    shape = RoundedCornerShape(14.dp),
                    color = MaterialTheme.colorScheme.surface,
                    border = BorderStroke(0.7.dp, MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.45f)),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(
                        modifier = Modifier.padding(12.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.weight(1f)) {
                                Surface(
                                    color = accentColor.copy(alpha = 0.14f),
                                    shape = CircleShape,
                                    modifier = Modifier.size(26.dp)
                                ) {
                                    Box(contentAlignment = Alignment.Center) {
                                        Text(
                                            text = BidiFormatter.toPersianDigits((index + 1).toString()),
                                            style = MaterialTheme.typography.labelSmall,
                                            fontWeight = FontWeight.Bold,
                                            color = accentColor
                                        )
                                    }
                                }
                                Spacer(modifier = Modifier.width(8.dp))
                                Text(
                                    text = BidiFormatter.formatMixedPersianText(lessonTitle),
                                    style = MaterialTheme.typography.labelMedium,
                                    fontWeight = FontWeight.Bold,
                                    color = accentColor
                                )
                            }
                        }

                        Text(
                            text = BidiFormatter.formatMixedPersianText(q.question),
                            style = MaterialTheme.typography.bodyMedium,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onSurface
                        )

                        q.options.forEachIndexed { optIndex, optionText ->
                            val isSelected = selectedOption == optIndex
                            val isCorrect = optIndex == q.correctIndex
                            val optionBg = when {
                                isSelected && isCorrect -> Color(0xFF16A34A).copy(alpha = 0.15f)
                                isSelected && !isCorrect -> Color(0xFFDC2626).copy(alpha = 0.15f)
                                else -> MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.35f)
                            }
                            val optionBorder = when {
                                isSelected && isCorrect -> Color(0xFF16A34A)
                                isSelected && !isCorrect -> Color(0xFFDC2626)
                                else -> MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.3f)
                            }

                            Surface(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clip(RoundedCornerShape(8.dp))
                                    .clickable {
                                        selectedOption = optIndex
                                        showExplanation = true
                                    },
                                shape = RoundedCornerShape(8.dp),
                                color = optionBg,
                                border = BorderStroke(0.8.dp, optionBorder)
                            ) {
                                Row(
                                    modifier = Modifier.padding(horizontal = 10.dp, vertical = 8.dp),
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.SpaceBetween
                                ) {
                                    Text(
                                        text = BidiFormatter.formatMixedPersianText("${BidiFormatter.toPersianDigits((optIndex + 1).toString())}) $optionText"),
                                        style = MaterialTheme.typography.bodySmall,
                                        color = MaterialTheme.colorScheme.onSurface,
                                        modifier = Modifier.weight(1f)
                                    )
                                    if (isSelected) {
                                        Icon(
                                            imageVector = if (isCorrect) Icons.Default.CheckCircle else Icons.Default.Close,
                                            contentDescription = null,
                                            tint = if (isCorrect) Color(0xFF16A34A) else Color(0xFFDC2626),
                                            modifier = Modifier.size(18.dp)
                                        )
                                    }
                                }
                            }
                        }

                        if (showExplanation) {
                            Surface(
                                color = accentColor.copy(alpha = 0.08f),
                                shape = RoundedCornerShape(8.dp),
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Column(
                                    modifier = Modifier.padding(10.dp),
                                    verticalArrangement = Arrangement.spacedBy(4.dp)
                                ) {
                                    Text(
                                        text = "💡 پاسخ تشریحی:",
                                        style = MaterialTheme.typography.labelMedium,
                                        fontWeight = FontWeight.Bold,
                                        color = accentColor
                                    )
                                    Text(
                                        text = BidiFormatter.formatMixedPersianText(q.explanation),
                                        style = MaterialTheme.typography.bodySmall,
                                        color = MaterialTheme.colorScheme.onSurface
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

@Composable
fun SubjectStudyMethodView(
    subjectId: String,
    subjectTitle: String,
    accentColor: Color,
    lessons: List<ZeroLesson>
) {
    val responsive = LocalResponsiveDimens.current

    val methodInfo = remember(subjectId) {
        FiveSubjectsMethodRepository.methods.find {
            it.id.contains(subjectId, ignoreCase = true) ||
                (subjectId == "arooz" && it.id.contains("fonoon")) ||
                (subjectId == "farhangian_tarbiat" && it.id.contains("dini")) ||
                (subjectId == "farhangian_hoosh" && it.id.contains("math"))
        }
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
            com.example.ui.components.TopRankersAndSocialTricksHub(
                initialSubjectId = subjectId
            )

        if (methodInfo != null) {
                Surface(
                    shape = RoundedCornerShape(16.dp),
                    color = accentColor.copy(alpha = 0.08f),
                    border = BorderStroke(1.dp, accentColor.copy(alpha = 0.3f)),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(
                        modifier = Modifier.padding(14.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                imageVector = methodInfo.iconVector,
                                contentDescription = null,
                                tint = accentColor,
                                modifier = Modifier.size(24.dp)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = methodInfo.methodTitle,
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.Bold,
                                color = accentColor
                            )
                        }

                        Text(
                            text = methodInfo.quickSummary,
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurface
                        )

                        HorizontalDivider(
                            color = accentColor.copy(alpha = 0.2f),
                            thickness = 0.7.dp
                        )

                        Text(
                            text = "📌 گام‌های اجرای فوق‌سریع این متد در کنکور:",
                            style = MaterialTheme.typography.titleSmall,
                            fontWeight = FontWeight.Bold,
                            color = accentColor
                        )

                        methodInfo.stepByStepGuide.forEach { (stepTitle, stepDesc) ->
                            Surface(
                                color = MaterialTheme.colorScheme.surface,
                                shape = RoundedCornerShape(10.dp),
                                border = BorderStroke(0.6.dp, accentColor.copy(alpha = 0.2f)),
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Column(
                                    modifier = Modifier.padding(10.dp),
                                    verticalArrangement = Arrangement.spacedBy(4.dp)
                                ) {
                                    Text(
                                        text = "📍 $stepTitle",
                                        style = MaterialTheme.typography.labelMedium,
                                        fontWeight = FontWeight.Bold,
                                        color = accentColor
                                    )
                                    Text(
                                        text = stepDesc,
                                        style = MaterialTheme.typography.bodySmall,
                                        color = MaterialTheme.colorScheme.onSurfaceVariant
                                    )
                                }
                            }
                        }

                        Surface(
                            color = accentColor.copy(alpha = 0.15f),
                            shape = RoundedCornerShape(10.dp),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Row(
                                modifier = Modifier.padding(10.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Star,
                                    contentDescription = null,
                                    tint = accentColor,
                                    modifier = Modifier.size(18.dp)
                                )
                                Spacer(modifier = Modifier.width(8.dp))
                                Text(
                                    text = "🏆 قانون طلایی: ${methodInfo.goldenRuleText}",
                                    style = MaterialTheme.typography.labelMedium,
                                    fontWeight = FontWeight.Bold,
                                    color = accentColor
                                )
                            }
                        }
                    }
                }
        }

        val repLesson = lessons.firstOrNull()
        if (repLesson != null) {
                SlowLearnerSuperMethodCard(
                    lesson = repLesson,
                    accentColor = accentColor
                )
        }
    }
}

@Composable
fun SubjectFlashcardsView(
    subjectId: String,
    subjectTitle: String,
    accentColor: Color
) {
    val responsive = LocalResponsiveDimens.current

    val flashcards = remember(subjectId) {
        FlashcardAndQuizBank.flashcards.filter {
            it.subjectId == subjectId ||
                (subjectId == "arooz" && it.subjectId == "fonoon") ||
                (subjectId == "farhangian_tarbiat" && it.subjectId == "farhangian") ||
                (subjectId == "farhangian_hoosh" && it.subjectId == "math")
        }.ifEmpty { FlashcardAndQuizBank.flashcards.take(6) }
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
            Surface(
                color = accentColor.copy(alpha = 0.08f),
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier.padding(12.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Default.Lightbulb,
                        contentDescription = null,
                        tint = accentColor,
                        modifier = Modifier.size(24.dp)
                    )
                    Spacer(modifier = Modifier.width(10.dp))
                    Column {
                        Text(
                            text = "📇 فلش‌کارت‌ها و نکات طلایی $subjectTitle (${BidiFormatter.toPersianDigits(flashcards.size.toString())} کارت)",
                            style = MaterialTheme.typography.titleSmall,
                            fontWeight = FontWeight.Bold,
                            color = accentColor
                        )
                        Text(
                            text = "روی هر کارت کلیک کنید تا پاسخ ساده و رمز حافظه نمایش داده شود.",
                            style = MaterialTheme.typography.labelSmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
            }

        flashcards.forEach { card ->
            var isFlipped by remember(card.id) { mutableStateOf(false) }

            Surface(
                shape = RoundedCornerShape(14.dp),
                color = MaterialTheme.colorScheme.surface,
                border = BorderStroke(0.8.dp, accentColor.copy(alpha = 0.35f)),
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(14.dp))
                    .clickable { isFlipped = !isFlipped }
            ) {
                Column(
                    modifier = Modifier.padding(14.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Surface(
                            color = accentColor.copy(alpha = 0.12f),
                            shape = RoundedCornerShape(6.dp)
                        ) {
                            Text(
                                text = BidiFormatter.formatMixedPersianText(card.categoryTag),
                                style = MaterialTheme.typography.labelSmall,
                                fontWeight = FontWeight.Bold,
                                color = accentColor,
                                modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp)
                            )
                        }
                        Text(
                            text = if (isFlipped) "نمایش روی کارت ↺" else "نمایش پاسخ و رمز 👁️",
                            style = MaterialTheme.typography.labelSmall,
                            color = accentColor,
                            fontWeight = FontWeight.Bold
                        )
                    }

                    Text(
                        text = BidiFormatter.formatMixedPersianText(card.frontQuestion),
                        style = MaterialTheme.typography.titleSmall,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurface
                    )

                    if (isFlipped) {
                        HorizontalDivider(color = accentColor.copy(alpha = 0.2f), thickness = 0.7.dp)

                        Text(
                            text = BidiFormatter.formatMixedPersianText(card.backAnswer),
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )

                        if (card.memoryHook.isNotBlank()) {
                            Surface(
                                color = accentColor.copy(alpha = 0.12f),
                                shape = RoundedCornerShape(8.dp),
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Text(
                                    text = "🎯 رمز یادگیری: ${BidiFormatter.formatMixedPersianText(card.memoryHook)}",
                                    style = MaterialTheme.typography.labelSmall,
                                    fontWeight = FontWeight.Bold,
                                    color = accentColor,
                                    modifier = Modifier.padding(8.dp)
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
fun SubjectBudgetingView(
    subject: SubjectMeta,
    lessonsCount: Int
) {
    val responsive = LocalResponsiveDimens.current

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(
            start = responsive.horizontalPadding,
            top = 6.dp,
            end = responsive.horizontalPadding,
            bottom = 88.dp
        ),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        item {
            Surface(
                shape = RoundedCornerShape(16.dp),
                color = subject.color.copy(alpha = 0.08f),
                border = BorderStroke(1.dp, subject.color.copy(alpha = 0.3f)),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier.padding(14.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text(
                        text = "📊 بودجه‌بندی و ضرایب رسمی ${subject.title}",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = subject.color
                    )

                    Text(
                        text = "• ضریب کنکور سراسری / فرهنگیان: ${subject.konkoorCoefficient}",
                        style = MaterialTheme.typography.bodySmall,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurface
                    )

                    Text(
                        text = "• هدف‌گذاری برای دانش‌آموز زیر صفر: ${subject.targetForZeroStudent}",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurface
                    )

                    Text(
                        text = "• توصیه راهبردی: ${subject.zeroReassurance}",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )

                    Text(
                        text = "• تعداد کل درسنامه‌ها: ${BidiFormatter.toPersianDigits(lessonsCount.toString())} درس جامع پله به پله",
                        style = MaterialTheme.typography.labelMedium,
                        fontWeight = FontWeight.Bold,
                        color = subject.color
                    )
                }
            }
        }
    }
}

@Composable
fun SubjectNahaeeExamsView(
    subjectId: String,
    subjectTitle: String,
    accentColor: Color
) {
    val responsive = LocalResponsiveDimens.current

    val mappedSubjectId = remember(subjectId) {
        when (subjectId) {
            "arooz" -> "fonoon"
            "farhangian_tarbiat" -> "dini"
            "farhangian_hoosh" -> "math"
            else -> subjectId
        }
    }

    val fullBooklets = remember(mappedSubjectId) {
        NahaeeExamsAndTarmimArchive.fullExamBooklets.filter { it.subjectId == mappedSubjectId }
    }

    val nightSummaries = remember(mappedSubjectId) {
        NahaeeExamsAndTarmimArchive.nightBeforeSummaries.filter { it.subjectId == mappedSubjectId }
    }

    val baremItem = remember(mappedSubjectId) {
        NahaeeExamsAndTarmimArchive.baremBandiList.find { it.subjectId == mappedSubjectId }
    }

    val descriptiveQuestions = remember(mappedSubjectId) {
        NahaeeExamsAndTarmimArchive.descriptiveNahaeeQuestions.filter {
            it.subjectId == mappedSubjectId
        }
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        // ۱. کارت هدر ویژه امتحانات نهایی و ترمیم معدل درس
            Surface(
                color = MaterialTheme.colorScheme.surfaceContainerHigh,
                shape = RoundedCornerShape(24.dp),
                border = BorderStroke(1.dp, accentColor.copy(alpha = 0.45f)),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Surface(
                            shape = RoundedCornerShape(50),
                            color = accentColor.copy(alpha = 0.15f)
                        ) {
                            Text(
                                text = "🏛️ تاثیر ۶۰٪ قطعی سوابق تحصیلی",
                                style = MaterialTheme.typography.labelSmall,
                                fontWeight = FontWeight.Black,
                                color = accentColor,
                                modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp)
                            )
                        }
                        if (baremItem != null) {
                            Surface(
                                shape = RoundedCornerShape(50),
                                color = MaterialTheme.colorScheme.tertiaryContainer
                            ) {
                                Text(
                                    text = "ضریب: ${baremItem.coefficient}",
                                    style = MaterialTheme.typography.labelSmall,
                                    fontWeight = FontWeight.Black,
                                    color = MaterialTheme.colorScheme.onTertiaryContainer,
                                    modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp)
                                )
                            }
                        }
                    }

                    Text(
                        text = "پرونده کامل امتحان نهایی $subjectTitle (دفترچه ۲۰ نمره‌ای + بسته شب امتحان + سوالات تشریحی)",
                        style = MaterialTheme.typography.titleSmall,
                        fontWeight = FontWeight.Black,
                        color = MaterialTheme.colorScheme.onSurface
                    )

                    Text(
                        text = "شامل برگه کامل ۲۰ نمره‌ای از سوال ۱ تا آخر، کلید ریز ۰.۲۵ نمره‌ای مصححین آموزش و پرورش، بارم‌بندی رسمی و نکات ۲۰ قطعی شب امتحان.",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }

        // ۲. بسته جمع‌بندی ۲۰ قطعی شب امتحان این درس
        nightSummaries.forEach { pack ->
            Surface(
                shape = RoundedCornerShape(24.dp),
                color = MaterialTheme.colorScheme.surface,
                border = BorderStroke(1.dp, accentColor.copy(alpha = 0.4f)),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier.padding(14.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text(
                        text = "⚡ ${pack.subjectTitle}",
                        style = MaterialTheme.typography.titleSmall,
                        fontWeight = FontWeight.Black,
                        color = accentColor
                    )
                    pack.guaranteedQuestionTypes.forEach { bullet ->
                        Surface(
                            shape = RoundedCornerShape(12.dp),
                            color = MaterialTheme.colorScheme.surfaceContainerLow,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            BiDiText(
                                text = bullet,
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.onSurface,
                                modifier = Modifier.padding(10.dp)
                            )
                        }
                    }
                    Surface(
                        shape = RoundedCornerShape(12.dp),
                        color = accentColor.copy(alpha = 0.12f),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        BiDiText(
                            text = "🏆 فرمول طلایی مصحح برگه نهایی: ${pack.goldenFormulaOrKeywords}",
                            style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold),
                            color = accentColor,
                            modifier = Modifier.padding(10.dp)
                        )
                    }
                }
            }
        }

        // ۳. دفترچه کامل ۲۰ نمره‌ای از سوال ۱ تا آخر برای این درس
        fullBooklets.forEach { booklet ->
            Surface(
                shape = RoundedCornerShape(24.dp),
                color = MaterialTheme.colorScheme.surface,
                border = BorderStroke(1.2.dp, accentColor.copy(alpha = 0.5f)),
                modifier = Modifier.fillMaxWidth()
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
                        Text(
                            text = "📄 ${booklet.subjectTitle}",
                            style = MaterialTheme.typography.titleSmall,
                            fontWeight = FontWeight.Black,
                            color = accentColor,
                            modifier = Modifier.weight(1f)
                        )
                        Surface(
                            shape = RoundedCornerShape(50),
                            color = accentColor.copy(alpha = 0.14f)
                        ) {
                            Text(
                                text = "۲۰ نمره کامل",
                                style = MaterialTheme.typography.labelSmall,
                                fontWeight = FontWeight.Black,
                                color = accentColor,
                                modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp)
                            )
                        }
                    }

                    booklet.sheetRows.forEach { rowItem ->
                        Surface(
                            shape = RoundedCornerShape(16.dp),
                            color = MaterialTheme.colorScheme.surfaceContainerLow,
                            border = BorderStroke(0.7.dp, MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.4f)),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Column(
                                modifier = Modifier.padding(12.dp),
                                verticalArrangement = Arrangement.spacedBy(6.dp)
                            ) {
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Text(
                                        text = "سوال ${BidiFormatter.toPersianDigits(rowItem.rowNumber.toString())}: ${rowItem.sectionTitle}",
                                        style = MaterialTheme.typography.labelMedium,
                                        fontWeight = FontWeight.Black,
                                        color = accentColor,
                                        modifier = Modifier.weight(1f)
                                    )
                                    Text(
                                        text = rowItem.scoreBaram,
                                        style = MaterialTheme.typography.labelSmall,
                                        fontWeight = FontWeight.Black,
                                        color = accentColor
                                    )
                                }
                                BiDiText(
                                    text = rowItem.questionPrompt,
                                    style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.SemiBold),
                                    color = MaterialTheme.colorScheme.onSurface
                                )
                                HorizontalDivider(color = MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.35f))
                                Text(
                                    text = "✅ کلید رسمی تصحیح مصحح:",
                                    style = MaterialTheme.typography.labelSmall,
                                    fontWeight = FontWeight.Black,
                                    color = MaterialTheme.colorScheme.tertiary
                                )
                                BiDiText(
                                    text = rowItem.officialAnswerKey,
                                    style = MaterialTheme.typography.bodySmall,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                            }
                        }
                    }
                }
            }
        }

        // ۴. سوالات تشریحی پرتکرار خرداد، شهریور و دی این درس
        descriptiveQuestions.forEach { q ->
            Surface(
                shape = RoundedCornerShape(22.dp),
                color = MaterialTheme.colorScheme.surface,
                border = BorderStroke(0.8.dp, MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.45f)),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier.padding(14.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Surface(
                            color = accentColor.copy(alpha = 0.14f),
                            shape = RoundedCornerShape(50)
                        ) {
                            Text(
                                text = "${q.sessionYear} • بارم: ${q.scorePoints}",
                                style = MaterialTheme.typography.labelSmall,
                                fontWeight = FontWeight.Black,
                                color = accentColor,
                                modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp)
                            )
                        }
                    }

                    BiDiText(
                        text = q.questionText,
                        style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold),
                        color = MaterialTheme.colorScheme.onSurface
                    )

                    Surface(
                        color = accentColor.copy(alpha = 0.07f),
                        shape = RoundedCornerShape(14.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Column(
                            modifier = Modifier.padding(12.dp),
                            verticalArrangement = Arrangement.spacedBy(6.dp)
                        ) {
                            Text(
                                text = "🔑 کلید تصحیح رسمی برگه نهایی:",
                                style = MaterialTheme.typography.labelMedium,
                                fontWeight = FontWeight.Black,
                                color = accentColor
                            )
                            BiDiText(
                                text = q.officialCorrectionKey,
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.onSurface
                            )
                            if (q.commonStudentTraps.isNotBlank()) {
                                Text(
                                    text = "⚠️ دام مصححین: ${BidiFormatter.formatMixedPersianText(q.commonStudentTraps)}",
                                    style = MaterialTheme.typography.labelSmall,
                                    fontWeight = FontWeight.Bold,
                                    color = Color(0xFFDC2626)
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

