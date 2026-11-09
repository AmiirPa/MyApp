package com.example.ui.components

import com.example.data.curriculum.subcategoryFolder
import android.app.DownloadManager
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color as AndroidColor
import android.graphics.Paint
import android.graphics.RectF
import android.graphics.Typeface
import android.graphics.pdf.PdfDocument
import android.graphics.pdf.PdfRenderer
import android.net.Uri
import android.os.Environment
import android.os.ParcelFileDescriptor
import android.text.Layout
import android.text.StaticLayout
import android.text.TextDirectionHeuristics
import android.text.TextPaint
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Canvas as ComposeCanvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.rememberTransformableState
import androidx.compose.foundation.gestures.transformable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
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
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Download
import androidx.compose.material.icons.filled.MenuBook
import androidx.compose.material.icons.filled.PictureAsPdf
import androidx.compose.material.icons.filled.SaveAlt
import androidx.compose.material.icons.filled.ZoomIn
import androidx.compose.material.icons.filled.ZoomOut
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.core.content.res.ResourcesCompat
import com.example.R
import com.example.data.curriculum.AdvancedMnemonicEngine
import com.example.data.curriculum.ZeroLesson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.File
import java.io.FileOutputStream

data class OfficialTextbookPdf(
    val bookId: String,
    val subjectId: String,
    val gradeLevel: Int, // 1: پیش‌نیاز، 2: دهم، 3: یازدهم، 4: دوازدهم
    val ministryCode: String,
    val title: String,
    val gradeBadge: String,
    val editionYear: String,
    val officialChapPdfUrl: String,
    val officialChapters: List<String>
)

object TextbookPdfCatalog {

    val allBooks: List<OfficialTextbookPdf> = listOf(
        // ======================== ۱. علوم و فنون ادبی ۱، ۲ و ۳ ========================
        OfficialTextbookPdf(
            bookId = "pdf_fonoon_10",
            subjectId = "fonoon",
            gradeLevel = 2,
            ministryCode = "110217",
            title = "علوم و فنون ادبی ۱ — پایه دهم انسانی",
            gradeBadge = "پایه دهم",
            editionYear = "چاپ ۱۴۰۳ آموزش و پرورش",
            officialChapPdfUrl = "http://chap.sch.ir/sites/default/files/lbooks/1403-1404/110217.pdf",
            officialChapters = listOf(
                "فصل اول — درس ۱: تاریخ ادبیات (تحول زبان فارسی تا قرن پنجم)",
                "فصل اول — درس ۲: زیبایی‌شناسی (وزن و هماهنگی واژه‌ها، واج‌آرایی، سجع)",
                "فصل اول — درس ۳: موسیقی شعر (تقطیع هجایی و خط عروضی)",
                "فصل دوم — درس ۴: تاریخ ادبیات (سبک خراسانی در قرن چهارم و پنجم)",
                "فصل دوم — درس ۵: زیبایی‌شناسی (موازنه و ترصیع)",
                "فصل دوم — درس ۶: موسیقی شعر (قافیه، ردیف و تبصره‌های قافیه)",
                "فصل سوم — درس ۷: تاریخ ادبیات (قرن ششم و سبک بینابینی)",
                "فصل سوم — درس ۸: زیبایی‌شناسی (جناس تام و جناس ناقص)",
                "فصل سوم — درس ۹: موسیقی شعر (پایه‌های آوایی و ارکان عروضی)",
                "فصل چهارم — درس ۱۰: سبک‌شناسی و تحلیل متن قرن ۴، ۵ و ۶",
                "فصل چهارم — درس ۱۱: زیبایی‌شناسی (مبانی تشبیه، مجاز و کنایه)",
                "فصل چهارم — درس ۱۲: موسیقی شعر (تقطیع با ارکان عروضی)"
            )
        ),
        OfficialTextbookPdf(
            bookId = "pdf_fonoon_11",
            subjectId = "fonoon",
            gradeLevel = 3,
            ministryCode = "111217",
            title = "علوم و فنون ادبی ۲ — پایه یازدهم انسانی",
            gradeBadge = "پایه یازدهم",
            editionYear = "چاپ ۱۴۰۴ آموزش و پرورش",
            officialChapPdfUrl = "http://chap.sch.ir/sites/default/files/lbooks/1403-1404/111217.pdf",
            officialChapters = listOf(
                "فصل اول — درس ۱: تاریخ ادبیات قرن هفتم و هشتم (حمله مغول و سبک عراقی)",
                "فصل اول — درس ۲: زیبایی‌شناسی (تشبیه مفرد و تشبیه مرکب)",
                "فصل اول — درس ۳: موسیقی شعر (اوزان همسان — بحر هزج و رمل)",
                "فصل دوم — درس ۴: ادامه تاریخ ادبیات قرن ۷ و ۸ (مولوی، سعدی، حافظ)",
                "فصل دوم — درس ۵: زیبایی‌شناسی (تشبیه بلیغ، مضمر و تفضیل)",
                "فصل دوم — درس ۶: موسیقی شعر (اوزان همسان — بحر رجز و متقارب)",
                "فصل سوم — درس ۷: تاریخ ادبیات قرن نهم (تیموریان و جامی)",
                "فصل سوم — درس ۸: زیبایی‌شناسی (استعاره مصرحه و مکنیه / تشخیص)",
                "فصل سوم — درس ۹: موسیقی شعر (اوزان همسان دولختی — منسرح و مضارع)",
                "فصل چهارم — درس ۱۰: سبک‌شناسی قرن ۷، ۸ و ۹ (مختصات سبک عراقی)",
                "فصل چهارم — درس ۱۱: زیبایی‌شناسی (مجاز و علاقه‌های آن، کنایه)",
                "فصل چهارم — درس ۱۲: موسیقی شعر (مرور و تحلیل اوزان یازدهم)"
            )
        ),
        OfficialTextbookPdf(
            bookId = "pdf_fonoon_12",
            subjectId = "fonoon",
            gradeLevel = 4,
            ministryCode = "112217",
            title = "علوم و فنون ادبی ۳ — پایه دوازدهم انسانی",
            gradeBadge = "پایه دوازدهم",
            editionYear = "چاپ ۱۴۰۵ آموزش و پرورش",
            officialChapPdfUrl = "http://chap.sch.ir/sites/default/files/lbooks/1403-1404/112217.pdf",
            officialChapters = listOf(
                "فصل اول — درس ۱: تاریخ ادبیات قرن دهم و یازدهم (سبک هندی و صفویه)",
                "فصل اول — درس ۲: زیبایی‌شناسی (مراعات نظیر، تلمیح و تضمین)",
                "فصل اول — درس ۳: موسیقی شعر (اوزان دوری و ناهمسان)",
                "فصل دوم — درس ۴: تاریخ ادبیات قرن ۱۲ و ۱۳ (دوره بازگشت ادبی)",
                "فصل دوم — درس ۵: زیبایی‌شناسی (ایهام، ایهام تناسب و اغراق)",
                "فصل دوم — درس ۶: موسیقی شعر (اختیارات شاعری زبانی و وزنی)",
                "فصل سوم — درس ۷: تاریخ ادبیات دوره مشروطه و معاصر",
                "فصل سوم — درس ۸: زیبایی‌شناسی (تضاد، متناقض‌نما/پارادوکس و حس‌آمیزی)",
                "فصل سوم — درس ۹: موسیقی شعر (کاربرد اختیارات شاعری در تقطیع)",
                "فصل چهارم — درس ۱۰: ادبیات انقلاب اسلامی و سبک‌شناسی معاصر",
                "فصل چهارم — درس ۱۱: زیبایی‌شناسی (لف و نشر، اسلوب معادله و حسن تعلیل)",
                "فصل چهارم — درس ۱۲: موسیقی شعر نیمایی (شعر نو) و جمع‌بندی ۱۶ وزن"
            )
        ),

        // ======================== ۲. عروض و قافیه ۱، ۲ و ۳ ========================
        OfficialTextbookPdf(
            bookId = "pdf_arooz_10",
            subjectId = "arooz",
            gradeLevel = 2,
            ministryCode = "110217-A",
            title = "عروض و قافیه ۱ (درس ۳، ۶، ۹ و ۱۲ علوم و فنون دهم)",
            gradeBadge = "پایه دهم",
            editionYear = "چاپ ۱۴۰۳ آموزش و پرورش",
            officialChapPdfUrl = "http://chap.sch.ir/sites/default/files/lbooks/1403-1404/110217.pdf",
            officialChapters = listOf(
                "درس ۳ فنون دهم: هجا، خط عروضی، حذف همزه و کمیت هجاها (U و —)",
                "درس ۶ فنون دهم: قافیه، ردیف، حروف و حرکات قافیه و تبصره‌های ۱ و ۲",
                "درس ۹ فنون دهم: پایه‌های آوایی و دسته‌بندی ارکان عروضی",
                "درس ۱۲ فنون دهم: تقطیع هجایی و رکنی ابیات کلاسیک"
            )
        ),
        OfficialTextbookPdf(
            bookId = "pdf_arooz_11",
            subjectId = "arooz",
            gradeLevel = 3,
            ministryCode = "111217-A",
            title = "عروض و قافیه ۲ (درس ۳، ۶، ۹ و ۱۲ علوم و فنون یازدهم)",
            gradeBadge = "پایه یازدهم",
            editionYear = "چاپ ۱۴۰۴ آموزش و پرورش",
            officialChapPdfUrl = "http://chap.sch.ir/sites/default/files/lbooks/1403-1404/111217.pdf",
            officialChapters = listOf(
                "درس ۳ فنون یازدهم: اوزان همسان تک‌رکنی (مفاعیلن — بحر هزج، فاعلاتن — بحر رمل)",
                "درس ۶ فنون یازدهم: ادامه اوزان همسان (مستفعلن — بحر رجز، فعولن — بحر متقارب)",
                "درس ۹ فنون یازدهم: اوزان همسان دولختی و متناوب",
                "درس ۱۲ فنون یازدهم: تمرین جامع تشخیص وزن و بحر"
            )
        ),
        OfficialTextbookPdf(
            bookId = "pdf_arooz_12",
            subjectId = "arooz",
            gradeLevel = 4,
            ministryCode = "112217-A",
            title = "عروض و قافیه ۳ (درس ۳، ۶، ۹ و ۱۲ علوم و فنون دوازدهم)",
            gradeBadge = "پایه دوازدهم",
            editionYear = "چاپ ۱۴۰۵ آموزش و پرورش",
            officialChapPdfUrl = "http://chap.sch.ir/sites/default/files/lbooks/1403-1404/112217.pdf",
            officialChapters = listOf(
                "درس ۳ فنون دوازدهم: اوزان دوری و ناهمسان (مفتعلن فاعلن، مفعول فاعلات...)",
                "درس ۶ فنون دوازدهم: اختیارات شاعری زبانی (حذف همزه، تغییر کمیت مصوت‌ها، تسکین)",
                "درس ۹ فنون دوازدهم: اختیارات شاعری وزنی (ابدال، قلب، اضافه کردن هجا در پایان)",
                "درس ۱۲ فنون دوازدهم: وزن شعر نو (نیمایی) و جدول کامل ۱۶ وزن کنکور"
            )
        ),

        // ======================== ۳. ریاضی و آمار ۱، ۲ و ۳ ========================
        OfficialTextbookPdf(
            bookId = "pdf_math_base",
            subjectId = "math",
            gradeLevel = 1,
            ministryCode = "100100",
            title = "کتابچه پیش‌نیاز ریاضی انسانی (پایه اول ابتدایی تا نهم)",
            gradeBadge = "پیش‌نیاز",
            editionYear = "ویژه شروع از صفر",
            officialChapPdfUrl = "http://chap.sch.ir/sites/default/files/lbooks/1403-1404/110216.pdf",
            officialChapters = listOf(
                "بخش ۱: جدول ضرب سریع، بخش‌پذیری و اولویت عملیات ریاضی",
                "بخش ۲: اعداد علامت‌دار (مثبت و منفی) و عملیات چهارگانه کسرها",
                "بخش ۳: مجموعه اعداد (N, W, Z, Q, R)، بازه‌ها و نمودار ون",
                "بخش ۴: قوانین توان، رادیکال و اتحادهای جبری (مربع دو جمله و مزدوج)"
            )
        ),
        OfficialTextbookPdf(
            bookId = "pdf_math_10",
            subjectId = "math",
            gradeLevel = 2,
            ministryCode = "110216",
            title = "ریاضی و آمار ۱ — پایه دهم انسانی",
            gradeBadge = "پایه دهم",
            editionYear = "چاپ ۱۴۰۳ آموزش و پرورش",
            officialChapPdfUrl = "http://chap.sch.ir/sites/default/files/lbooks/1403-1404/110216.pdf",
            officialChapters = listOf(
                "فصل اول: معادله درجه دوم (معادله، روش تجزیه، دلتا، جمع ضرایب، سهمی و تعیین علامت)",
                "فصل دوم: تابع (مفهوم تابع، زوج مرتب، دامنه توابع کسری و رادیکالی، تابع خطی)",
                "فصل سوم: آمار توصیفی (میانگین، میانه، چارک‌ها، دامنه میان‌چارکی، واریانس، انحراف معیار و CV)",
                "فصل چهارم: نمایش داده‌ها (مقیاس‌های اندازه‌گیری اسمی/ترتیبی/فاصله‌ای/نسبی و نمودارهای آماری)"
            )
        ),
        OfficialTextbookPdf(
            bookId = "pdf_math_11",
            subjectId = "math",
            gradeLevel = 3,
            ministryCode = "111216",
            title = "ریاضی و آمار ۲ — پایه یازدهم انسانی",
            gradeBadge = "پایه یازدهم",
            editionYear = "چاپ ۱۴۰۴ آموزش و پرورش",
            officialChapPdfUrl = "http://chap.sch.ir/sites/default/files/lbooks/1403-1404/111216.pdf",
            officialChapters = listOf(
                "فصل اول: آشنایی با منطق و استدلال ریاضی (گزاره، نقیض، عطف، فصل، شرطی، دوشرطی و هم‌ارزی)",
                "فصل دوم: تابع (توابع ثابت، همانی، چندضابطه‌ای، پلکانی، جزء صحیح و تابع نمایی رشد و زوال)",
                "فصل سوم: آمار (شاخص‌های آماری، شاخص بهای کالا CPI، خط فقر، سری‌های زمانی و خط برازش)"
            )
        ),
        OfficialTextbookPdf(
            bookId = "pdf_math_12",
            subjectId = "math",
            gradeLevel = 4,
            ministryCode = "112216",
            title = "ریاضی و آمار ۳ — پایه دوازدهم انسانی",
            gradeBadge = "پایه دوازدهم",
            editionYear = "چاپ ۱۴۰۵ آموزش و پرورش",
            officialChapPdfUrl = "http://chap.sch.ir/sites/default/files/lbooks/1403-1404/112216.pdf",
            officialChapters = listOf(
                "فصل اول: آمار و احتمال (شمارش، اصل ضرب، فاکتوریل، جایگشت، ترکیب، احتمال شرطی و توزیع نرمال)",
                "فصل دوم: الگوهای خطی (دنباله حسابی، جمله عمومی، واسطه حسابی و مجموع جملات حسابی)",
                "فصل سوم: الگوهای غیرخطی (دنباله هندسی، ریشه nام، توان گویا و رشد و زوال مرکب)"
            )
        ),

        // ======================== ۴. عربی، زبان قرآن ۱، ۲ و ۳ ========================
        OfficialTextbookPdf(
            bookId = "pdf_arabic_base",
            subjectId = "arabic",
            gradeLevel = 1,
            ministryCode = "100214",
            title = "کتابچه پیش‌نیاز عربی (پایه هفتم، هشتم و نهم)",
            gradeBadge = "پیش‌نیاز",
            editionYear = "ویژه شروع از صفر",
            officialChapPdfUrl = "http://chap.sch.ir/sites/default/files/lbooks/1403-1404/110214.pdf",
            officialChapters = listOf(
                "درس ۰: آموزش حرکات، ضمایر، اقسام کلمه (اسم، فعل، حرف) و اعراب",
                "درس ۱: جدول کامل ۱۴ صیغه فعل ماضی، مضارع، مستقبل، امر و نهی"
            )
        ),
        OfficialTextbookPdf(
            bookId = "pdf_arabic_10",
            subjectId = "arabic",
            gradeLevel = 2,
            ministryCode = "110214",
            title = "عربی، زبان قرآن ۱ — پایه دهم انسانی",
            gradeBadge = "پایه دهم",
            editionYear = "چاپ ۱۴۰۳ آموزش و پرورش",
            officialChapPdfUrl = "http://chap.sch.ir/sites/default/files/lbooks/1403-1404/110214.pdf",
            officialChapters = listOf(
                "درس ۱ و ۲: مرور پایه، اعداد اصلی و ترتیبی (۱ تا ۱۰۰) و ساعت‌خوانی",
                "درس ۳ و ۴: افعال ثلاثی مزید (باب إفعال، تفعیل، مفاعله، تفعّل، تفاعل، افتعال، انفعال، استفعال)",
                "درس ۵ و ۶: فعل مجهول (ماضی و مضارع مجهول) و جمله اسمیه و فعلیه",
                "درس ۷ و ۸: اعراب فرعی (مثنی و جمع مذکر سالم)، معرفه و نکره و ترجمه صفت"
            )
        ),
        OfficialTextbookPdf(
            bookId = "pdf_arabic_11",
            subjectId = "arabic",
            gradeLevel = 3,
            ministryCode = "111214",
            title = "عربی، زبان قرآن ۲ — پایه یازدهم انسانی",
            gradeBadge = "پایه یازدهم",
            editionYear = "چاپ ۱۴۰۴ آموزش و پرورش",
            officialChapPdfUrl = "http://chap.sch.ir/sites/default/files/lbooks/1403-1404/111214.pdf",
            officialChapters = listOf(
                "درس ۱ و ۲: اسم تفضیل (أفعَل / فُعلی)، اسم مکان و اسم مبالغه",
                "درس ۳ و ۴: اسلوب شرط (إنْ، إذا، مَنْ، ما، کلّما) و ترجمه دقیق جملات شرطی",
                "درس ۵ تا ۷: افعال ناقصه (کان، صار، لیس، أصبح)، حروف مشبهة بالفعل (إنّ، أنّ...) و ترجمه انْ مصدریه"
            )
        ),
        OfficialTextbookPdf(
            bookId = "pdf_arabic_12",
            subjectId = "arabic",
            gradeLevel = 4,
            ministryCode = "112214",
            title = "عربی، زبان قرآن ۳ — پایه دوازدهم انسانی",
            gradeBadge = "پایه دوازدهم",
            editionYear = "چاپ ۱۴۰۵ آموزش و پرورش",
            officialChapPdfUrl = "http://chap.sch.ir/sites/default/files/lbooks/1403-1404/112214.pdf",
            officialChapters = listOf(
                "درس ۱: حروف مشبهة بالفعل، لای نفی جنس (لا رَیبَ) و مفعول مطلق (تأکیدی و نوعی)",
                "درس ۲: حال (قید حالت — مفرد و جمله حالیه)",
                "درس ۳: منادا (یا أیّها، منادای مضاف و مفرد علم) و اسلوب استثنا (إلّا) و حصر (إنّما)",
                "درس ۴: تمییز (ملفوظ و ملحوظ) و جمع‌بندی کل قواعد عربی کنکور و نهایی"
            )
        ),

        // ======================== ۵. زبان انگلیسی ۱، ۲ و ۳ (Vision 1-3) ========================
        OfficialTextbookPdf(
            bookId = "pdf_english_base",
            subjectId = "english",
            gradeLevel = 1,
            ministryCode = "100210",
            title = "کتابچه پیش‌نیاز زبان انگلیسی (Prospect 1, 2, 3 — هفتم تا نهم)",
            gradeBadge = "پیش‌نیاز",
            editionYear = "ویژه شروع از صفر",
            officialChapPdfUrl = "http://chap.sch.ir/sites/default/files/lbooks/1403-1404/110210.pdf",
            officialChapters = listOf(
                "بخش ۱: لغت‌نامه اصطلاحات گرامری (Subject, Verb, Object, Adjective, Adverb)",
                "بخش ۲: ضمایر فاعلی، مفعولی، ملکی و انعکاسی",
                "بخش ۳: جدول کامل زمان‌های اصلی (حال ساده، حال استمراری، گذشته ساده، گذشته استمراری)"
            )
        ),
        OfficialTextbookPdf(
            bookId = "pdf_english_10",
            subjectId = "english",
            gradeLevel = 2,
            ministryCode = "110210",
            title = "زبان انگلیسی ۱ (Vision 1) — پایه دهم",
            gradeBadge = "پایه دهم",
            editionYear = "چاپ ۱۴۰۳ آموزش و پرورش",
            officialChapPdfUrl = "http://chap.sch.ir/sites/default/files/lbooks/1403-1404/110210.pdf",
            officialChapters = listOf(
                "Lesson 1 (Saving Nature): زمان آینده با will و be going to + اسم‌های خاص و عام",
                "Lesson 2 (Wonders of Creation): صفات پایه، صفت برتر (Comparative -er/more) و عالی (Superlative -est/most)",
                "Lesson 3 (The Value of Knowledge): گذشته استمراری (was/were + ing) و ضمایر انعکاسی (myself, himself...)",
                "Lesson 4 (Traveling the World): افعال کمکی وجهی (can, may, must, should) و حروف اضافه زمان و مکان"
            )
        ),
        OfficialTextbookPdf(
            bookId = "pdf_english_11",
            subjectId = "english",
            gradeLevel = 3,
            ministryCode = "111210",
            title = "زبان انگلیسی ۲ (Vision 2) — پایه یازدهم",
            gradeBadge = "پایه یازدهم",
            editionYear = "چاپ ۱۴۰۴ آموزش و پرورش",
            officialChapPdfUrl = "http://chap.sch.ir/sites/default/files/lbooks/1403-1404/111210.pdf",
            officialChapters = listOf(
                "Lesson 1 (Understanding People): اسامی قابل شمارش و غیرقابل شمارش + کمیت‌نماها (many, much, a lot of) و ماضی نقلی (Present Perfect)",
                "Lesson 2 (A Healthy Lifestyle): اسم مصدر (Gerund: verb + ing) و مصدر با to (Infinitive)",
                "Lesson 3 (Art and Culture): جملات شرطی نوع اول (If + Present Simple, will + Verb)"
            )
        ),
        OfficialTextbookPdf(
            bookId = "pdf_english_12",
            subjectId = "english",
            gradeLevel = 4,
            ministryCode = "112210",
            title = "زبان انگلیسی ۳ (Vision 3) — پایه دوازدهم",
            gradeBadge = "پایه دوازدهم",
            editionYear = "چاپ ۱۴۰۵ آموزش و پرورش",
            officialChapPdfUrl = "http://chap.sch.ir/sites/default/files/lbooks/1403-1404/112210.pdf",
            officialChapters = listOf(
                "Lesson 1 (Sense of Appreciation): جملات مجهول (Passive Voice) و دنباله‌های سوالی (Tag Questions)",
                "Lesson 2 (Look it Up!): ضمایر موصولی (who, whom, which, that) و جملات مرکب (and, but, or, so)",
                "Lesson 3 (Renewable Energy): زمان گذشته کامل / ماضی بعید (Past Perfect: had + P.P.) و جملات شرطی نوع دوم"
            )
        ),

        // ======================== ۶. فلسفه ۱ و ۲ و منطق دهم ========================
        OfficialTextbookPdf(
            bookId = "pdf_logic_10",
            subjectId = "logic",
            gradeLevel = 2,
            ministryCode = "110220",
            title = "منطق — پایه دهم انسانی",
            gradeBadge = "پایه دهم",
            editionYear = "چاپ ۱۴۰۳ آموزش و پرورش",
            officialChapPdfUrl = "http://chap.sch.ir/sites/default/files/lbooks/1403-1404/110220.pdf",
            officialChapters = listOf(
                "درس ۱ و ۲: منطق ترازوی اندیشه، تصور و تصدیق، دلالت و اقسام لفظ",
                "درس ۳ و ۴: مفهوم و مصداق، کلی و جزئی، نسب چهارگانه و کلیات خمس (نوع، جنس، فصل، عرض خاص و عام)",
                "درس ۵: تعریف (حد تام، حد ناقص، رسم تام، رسم ناقص) و شرایط تعریف صحیح",
                "درس ۶ و ۷: قضیه حملیه، محصورات اربعه (الف، ب، ج، د)، مربع تقابل و عکس مستوی",
                "درس ۸ و ۹: قیاس اقترانی (اشکال چهارگانه)، قضیه شرطی و قیاس استثنایی",
                "درس ۱۰: مغالطات (تعمیم شتاب‌زده، ایهام، توسل به شخص، پهلوان‌پنبه، دور و مصادره به مطلوب)"
            )
        ),
        OfficialTextbookPdf(
            bookId = "pdf_philosophy_11",
            subjectId = "philosophy",
            gradeLevel = 3,
            ministryCode = "111220",
            title = "فلسفه ۱ — پایه یازدهم انسانی",
            gradeBadge = "پایه یازدهم",
            editionYear = "چاپ ۱۴۰۴ آموزش و پرورش",
            officialChapPdfUrl = "http://chap.sch.ir/sites/default/files/lbooks/1403-1404/111220.pdf",
            officialChapters = listOf(
                "درس ۱ تا ۳: چیستی فلسفه، ریشه و ویژگی‌های تفکر فلسفی، فیلسوفان طبیعت‌گرا (تالس، هراکلیتوس، پارمنیدس)",
                "درس ۴ تا ۶: سوفیست‌ها (پروتاگوراس و گرگیاس)، سقراط، افلاطون (عالم مُثُل) و ارسطو",
                "درس ۷ تا ۱۰: معرفت‌شناسی، نقد شکاکیت، عقل‌گرایی (دکارت)، تجربه‌گرایی (لاک و هیوم) و کانت",
                "درس ۱۱ و ۱۲: فلسفه در جهان اسلام (کندی، فارابی، ابن‌سینا، سهروردی، ملاصدرا) و انسان‌شناسی"
            )
        ),
        OfficialTextbookPdf(
            bookId = "pdf_philosophy_12",
            subjectId = "philosophy",
            gradeLevel = 4,
            ministryCode = "112220",
            title = "فلسفه ۲ — پایه دوازدهم انسانی",
            gradeBadge = "پایه دوازدهم",
            editionYear = "چاپ ۱۴۰۵ آموزش و پرورش",
            officialChapPdfUrl = "http://chap.sch.ir/sites/default/files/lbooks/1403-1404/112220.pdf",
            officialChapters = listOf(
                "درس ۱ تا ۳: هستی و چیستی (وجود و ماهیت)، بداهت وجود، اصالت وجود و مراتب تشکیکی هستی",
                "درس ۴ تا ۶: اصل علیت، سنخیت، وجوب و امکان، قوه و فعل و حرکت جوهری ملاصدرا",
                "درس ۷ تا ۹: مکاتب فلسفی اسلامی (مشاء، اشراق، عرفان و حکمت متعالیه) و اثبات واجب‌الوجود",
                "درس ۱۰ و ۱۱: عقل در فلسفه اسلامی و حکمت عملی"
            )
        ),

        // ======================== ۷. جامعه‌شناسی ۱، ۲ و ۳ ========================
        OfficialTextbookPdf(
            bookId = "pdf_sociology_10",
            subjectId = "sociology",
            gradeLevel = 2,
            ministryCode = "110219",
            title = "جامعه‌شناسی ۱ — پایه دهم انسانی",
            gradeBadge = "پایه دهم",
            editionYear = "چاپ ۱۴۰۳ آموزش و پرورش",
            officialChapPdfUrl = "http://chap.sch.ir/sites/default/files/lbooks/1403-1404/110219.pdf",
            officialChapters = listOf(
                "درس ۱ تا ۴: کنش انسانی، کنش اجتماعی، پدیده‌های اجتماعی، ارزش و هنجار",
                "درس ۵ تا ۹: جهان اجتماعی، فرهنگ، حق و باطلِ فرهنگی و ارزیابی فرهنگ‌ها",
                "درس ۱۰ تا ۱۴: هویت فردی و اجتماعی، جامعه‌پذیری، تحرک اجتماعی، کج‌روی و کنترل اجتماعی"
            )
        ),
        OfficialTextbookPdf(
            bookId = "pdf_sociology_11",
            subjectId = "sociology",
            gradeLevel = 3,
            ministryCode = "111219",
            title = "جامعه‌شناسی ۲ — پایه یازدهم انسانی",
            gradeBadge = "پایه یازدهم",
            editionYear = "چاپ ۱۴۰۴ آموزش و پرورش",
            officialChapPdfUrl = "http://chap.sch.ir/sites/default/files/lbooks/1403-1404/111219.pdf",
            officialChapters = listOf(
                "درس ۱ تا ۵: فرهنگ جهانی، خاستگاه فرهنگ غرب (رنسانس، رفورم دینی، روشنگری)",
                "درس ۶ تا ۱۰: ویژگی‌های فرهنگ مدرن غرب (اومانیسم، سکولاریسم، علم‌گرایی، سرمایه‌داری و استعمار)",
                "درس ۱۱ تا ۱۴: رویارویی جهان اسلام با غرب، بیداری اسلامی و انقلاب اسلامی ایران"
            )
        ),
        OfficialTextbookPdf(
            bookId = "pdf_sociology_12",
            subjectId = "sociology",
            gradeLevel = 4,
            ministryCode = "112219",
            title = "جامعه‌شناسی ۳ — پایه دوازدهم انسانی",
            gradeBadge = "پایه دوازدهم",
            editionYear = "چاپ ۱۴۰۵ آموزش و پرورش",
            officialChapPdfUrl = "http://chap.sch.ir/sites/default/files/lbooks/1403-1404/112219.pdf",
            officialChapters = listOf(
                "درس ۱ تا ۵: شکل‌گیری علوم اجتماعی و مقایسه ۳ رویکرد (تبیینی/پوزیتیویستی، تفسیری و انتقادی)",
                "درس ۶ و ۷: نظم اجتماعی و آسیب‌های اجتماعی از دیدگاه سه رویکرد و دیدگاه اسلامی",
                "درس ۸ تا ۱۰: خانواده، سبک زندگی و سیاست‌گذاری فرهنگی و اجتماعی"
            )
        ),

        // ======================== ۸. روان‌شناسی یازدهم و اقتصاد دهم ========================
        OfficialTextbookPdf(
            bookId = "pdf_psychology_11",
            subjectId = "psychology",
            gradeLevel = 3,
            ministryCode = "111224",
            title = "روان‌شناسی — پایه یازدهم انسانی",
            gradeBadge = "پایه یازدهم",
            editionYear = "چاپ ۱۴۰۴ آموزش و پرورش",
            officialChapPdfUrl = "http://chap.sch.ir/sites/default/files/lbooks/1403-1404/111224.pdf",
            officialChapters = listOf(
                "فصل ۱ و ۲: تعریف علمی روان‌شناسی، روش‌های پژوهش و روان‌شناسی رشد (مراحل پیاژه و اریکسون)",
                "فصل ۳ و ۴: احساس، توجه، ادراک و حافظه (حسی، کوتاه‌مدت، بلندمدت و علل فراموشی)",
                "فصل ۵ و ۶: تفکر، حل مسئله، تصمیم‌گیری و خطاهای شناختی",
                "فصل ۷ و ۸: انگیزش، هیجان، روان‌شناسی سلامت، استرس و سبک‌های مقابله"
            )
        ),
        OfficialTextbookPdf(
            bookId = "pdf_economics_10",
            subjectId = "economics",
            gradeLevel = 2,
            ministryCode = "110221",
            title = "اقتصاد — پایه دهم انسانی",
            gradeBadge = "پایه دهم",
            editionYear = "چاپ ۱۴۰۳ آموزش و پرورش",
            officialChapPdfUrl = "http://chap.sch.ir/sites/default/files/lbooks/1403-1404/110221.pdf",
            officialChapters = listOf(
                "بخش ۱ (فصل ۱): کسب‌وکار، هزینه فرصت، سود حسابداری و سود اقتصادی و نقطه سربه‌سر",
                "بخش ۲ (فصل ۲): بازار، قوانین عرضه و تقاضا، قیمت تعادلی، مازاد و کمبود",
                "بخش ۳ (فصل ۳): پول، بانکداری بدون ربا (عقود مبادله‌ای و مشارکتی)، تورم و نقدینگی",
                "بخش ۴ (فصل ۴ و ۵): تولید ملی (GDP)، بیکاری، بودجه دولت، مالیات، تجارت بین‌الملل و اقتصاد مقاومتی"
            )
        ),

        // ======================== ۹. تاریخ ۱، ۲ و ۳ ========================
        OfficialTextbookPdf(
            bookId = "pdf_history_10",
            subjectId = "history",
            gradeLevel = 2,
            ministryCode = "110218",
            title = "تاریخ ۱ (ایران و جهان باستان) — پایه دهم انسانی",
            gradeBadge = "پایه دهم",
            editionYear = "چاپ ۱۴۰۳ آموزش و پرورش",
            officialChapPdfUrl = "http://chap.sch.ir/sites/default/files/lbooks/1403-1404/110218.pdf",
            officialChapters = listOf(
                "بخش اول (درس ۱ تا ۶): تاریخ‌نگاری، تمدن‌های بین‌النهرین، مصر، یونان و روم باستان",
                "بخش دوم (درس ۷ تا ۱۶): آریایی‌ها، مادها، هخامنشیان، سلوکیان، اشکانیان و ساسانیان"
            )
        ),
        OfficialTextbookPdf(
            bookId = "pdf_history_11",
            subjectId = "history",
            gradeLevel = 3,
            ministryCode = "111218",
            title = "تاریخ ۲ (اسلام و ایران تا صفویه) — پایه یازدهم انسانی",
            gradeBadge = "پایه یازدهم",
            editionYear = "چاپ ۱۴۰۴ آموزش و پرورش",
            officialChapPdfUrl = "http://chap.sch.ir/sites/default/files/lbooks/1403-1404/111218.pdf",
            officialChapters = listOf(
                "بخش اول (درس ۱ تا ۷): ظهور اسلام، حکومت نبوی در مدینه، خلفا، امویان و عباسیان",
                "بخش دوم (درس ۸ تا ۱۶): طاهریان، صفاریان، سامانیان، آل‌بویه، غزنویان، سلجوقیان، ایلخانان و صفویه"
            )
        ),
        OfficialTextbookPdf(
            bookId = "pdf_history_12",
            subjectId = "history",
            gradeLevel = 4,
            ministryCode = "112218",
            title = "تاریخ ۳ (ایران و جهان معاصر) — پایه دوازدهم انسانی",
            gradeBadge = "پایه دوازدهم",
            editionYear = "چاپ ۱۴۰۵ آموزش و پرورش",
            officialChapPdfUrl = "http://chap.sch.ir/sites/default/files/lbooks/1403-1404/112218.pdf",
            officialChapters = listOf(
                "بخش اول (درس ۱ تا ۶): افشاریه، زندیه، قاجار، جنگ‌های ایران و روس و انقلاب مشروطه",
                "بخش دوم (درس ۷ تا ۱۲): جنگ‌های جهانی، عصر پهلوی، ملی شدن نفت، انقلاب اسلامی و دفاع مقدس"
            )
        ),

        // ======================== ۱۰. جغرافیا ۱، ۲ و ۳ ========================
        OfficialTextbookPdf(
            bookId = "pdf_geography_10",
            subjectId = "geography",
            gradeLevel = 2,
            ministryCode = "110213",
            title = "جغرافیای ایران (جغرافیا ۱) — پایه دهم",
            gradeBadge = "پایه دهم",
            editionYear = "چاپ ۱۴۰۳ آموزش و پرورش",
            officialChapPdfUrl = "http://chap.sch.ir/sites/default/files/lbooks/1403-1404/110213.pdf",
            officialChapters = listOf(
                "درس ۱ تا ۵: موقعیت جغرافیایی ایران، ناهمواری‌ها، آب‌وهوا (۴ توده هوا) و ۶ حوضه آبریز",
                "درس ۶ تا ۱۰: جمعیت، مهاجرت، سکونتگاه‌های شهری و روستایی، اقتصاد و تقسیمات کشوری"
            )
        ),
        OfficialTextbookPdf(
            bookId = "pdf_geography_11",
            subjectId = "geography",
            gradeLevel = 3,
            ministryCode = "111223",
            title = "جغرافیا ۲ (جغرافیای ناحیه‌ای) — پایه یازدهم انسانی",
            gradeBadge = "پایه یازدهم",
            editionYear = "چاپ ۱۴۰۴ آموزش و پرورش",
            officialChapPdfUrl = "http://chap.sch.ir/sites/default/files/lbooks/1403-1404/111223.pdf",
            officialChapters = listOf(
                "درس ۱ تا ۶: مفهوم ناحیه، نواحی طبیعی، زیست‌بوم‌های جهان (توندرا، تایگا، ساوان...) و نواحی فرهنگی",
                "درس ۷ تا ۱۲: نواحی اقتصادی، سیاسی، ژئوپلیتیک، شکل کشورها و حقوق بین‌الملل دریاها"
            )
        ),
        OfficialTextbookPdf(
            bookId = "pdf_geography_12",
            subjectId = "geography",
            gradeLevel = 4,
            ministryCode = "112223",
            title = "جغرافیا ۳ (جغرافیای کاربردی) — پایه دوازدهم انسانی",
            gradeBadge = "پایه دوازدهم",
            editionYear = "چاپ ۱۴۰۵ آموزش و پرورش",
            officialChapPdfUrl = "http://chap.sch.ir/sites/default/files/lbooks/1403-1404/112223.pdf",
            officialChapters = listOf(
                "درس ۱ تا ۳: جغرافیای کاربردی، شهرهای جدید، مخاطرات طبیعی و چرخه مدیریت بحران",
                "درس ۴ تا ۶: آب‌وهواشناسی کاربردی، آمایش سرزمین، حمل‌ونقل و فناوری‌های نوین (RS, GPS, GIS)"
            )
        ),

        // ======================== ۱۱. دین و زندگی ۱، ۲ و ۳ ========================
        OfficialTextbookPdf(
            bookId = "pdf_dini_10",
            subjectId = "dini",
            gradeLevel = 2,
            ministryCode = "110215",
            title = "دین و زندگی ۱ — پایه دهم انسانی",
            gradeBadge = "پایه دهم",
            editionYear = "چاپ ۱۴۰۳ آموزش و پرورش",
            officialChapPdfUrl = "http://chap.sch.ir/sites/default/files/lbooks/1403-1404/110215.pdf",
            officialChapters = listOf(
                "درس ۱ تا ۶: هدف زندگی، پر پرواز (سرمایه‌های انسان)، پنجره‌ای به روشنایی و فطرت",
                "درس ۷ تا ۱۲: واقعه بزرگ (مرگ و توفّی)، عالم برزخ، قیامت، تجسم عمل، دوستی و مسئولیت"
            )
        ),
        OfficialTextbookPdf(
            bookId = "pdf_dini_11",
            subjectId = "dini",
            gradeLevel = 3,
            ministryCode = "111215",
            title = "دین و زندگی ۲ — پایه یازدهم انسانی",
            gradeBadge = "پایه یازدهم",
            editionYear = "چاپ ۱۴۰۴ آموزش و پرورش",
            officialChapPdfUrl = "http://chap.sch.ir/sites/default/files/lbooks/1403-1404/111215.pdf",
            officialChapters = listOf(
                "درس ۱ تا ۷: هدایت الهی، معجزه، قرآن معجزه جاویدان، ختم نبوت و عصمت پیامبران",
                "درس ۸ تا ۱۴: تداوم هدایت (امامت)، واقعه غدیر، نهضت عاشورا، عصر غیبت و ولایت فقیه"
            )
        ),
        OfficialTextbookPdf(
            bookId = "pdf_dini_12",
            subjectId = "dini",
            gradeLevel = 4,
            ministryCode = "112215",
            title = "دین و زندگی ۳ — پایه دوازدهم انسانی",
            gradeBadge = "پایه دوازدهم",
            editionYear = "چاپ ۱۴۰۵ آموزش و پرورش",
            officialChapPdfUrl = "http://chap.sch.ir/sites/default/files/lbooks/1403-1404/112215.pdf",
            officialChapters = listOf(
                "درس ۱ تا ۵: هستی‌بخش، مراتب توحید (ذاتی، صفاتی، افعالی، عملی) و اخلاص",
                "درس ۶ تا ۱۰: سنت‌های الهی، قضا و قدر، اختیار انسان، توبه، پایه استوار (خانواده) و تمدن اسلامی"
            )
        ),

        // ======================== ۱۲. فارسی و نگارش ۱، ۲ و ۳ ========================
        OfficialTextbookPdf(
            bookId = "pdf_farsi_10",
            subjectId = "farsi",
            gradeLevel = 2,
            ministryCode = "110202",
            title = "فارسی ۱ و نگارش ۱ — پایه دهم",
            gradeBadge = "پایه دهم",
            editionYear = "چاپ ۱۴۰۳ آموزش و پرورش",
            officialChapPdfUrl = "http://chap.sch.ir/sites/default/files/lbooks/1403-1404/110202.pdf",
            officialChapters = listOf(
                "قلمرو زبانی فارسی ۱: انواع فعل (اسنادی، گذرا به مفعول/متمم)، گروه اسمی (وابسته‌های پیشین و پسین)",
                "قلمرو ادبی و فکری فارسی ۱: واژگان، املا (هم‌آواها)، تاریخ ادبیات و درک مطلب دروس ۱ تا ۱۸",
                "نگارش ۱: توصیف، جان‌بخشی، مثل‌نویسی و بازگردانی شعر به نثر"
            )
        ),
        OfficialTextbookPdf(
            bookId = "pdf_farsi_11",
            subjectId = "farsi",
            gradeLevel = 3,
            ministryCode = "111202",
            title = "فارسی ۲ و نگارش ۲ — پایه یازدهم",
            gradeBadge = "پایه یازدهم",
            editionYear = "چاپ ۱۴۰۴ آموزش و پرورش",
            officialChapPdfUrl = "http://chap.sch.ir/sites/default/files/lbooks/1403-1404/111202.pdf",
            officialChapters = listOf(
                "قلمرو زبانی فارسی ۲: ساختمان واژه (ساده، مشتق، مرکب، مشتق-مرکب) و نقش‌های تبعی (معطوف، بدل، تکرار)",
                "قلمرو ادبی و فکری فارسی ۲: واژگان، املا و تاریخ ادبیات دروس ۱ تا ۱۸",
                "نگارش ۲: مقایسه‌نویسی، استدلال‌نویسی و تحلیل ضرب‌المثل"
            )
        ),
        OfficialTextbookPdf(
            bookId = "pdf_farsi_12",
            subjectId = "farsi",
            gradeLevel = 4,
            ministryCode = "112202",
            title = "فارسی ۳ و نگارش ۳ — پایه دوازدهم (ویژه نهایی)",
            gradeBadge = "پایه دوازدهم",
            editionYear = "چاپ ۱۴۰۵ آموزش و پرورش",
            officialChapPdfUrl = "http://chap.sch.ir/sites/default/files/lbooks/1403-1404/112202.pdf",
            officialChapters = listOf(
                "قلمرو زبانی فارسی ۳: شمارش تکواژ و واژه، وابسته‌های وابسته و حذف به قرینه لفظی و معنایی",
                "قلمرو ادبی و فکری فارسی ۳: لغات، املا، آثار و درک مطلب دروس ۱ تا ۱۸ ویژه امتحان نهایی",
                "نگارش ۳: قالب‌های آماده نمره ۲۰ در انشای تحلیلی، حکایت‌نویسی و درک متن نهایی"
            )
        ),

        // ======================== ۱۳. منابع اختصاصی فرهنگیان و دروس عمومی ========================
        OfficialTextbookPdf(
            bookId = "pdf_farhangian_gharaati",
            subjectId = "farhangian",
            gradeLevel = 3,
            ministryCode = "FAR-1406",
            title = "مهارت‌های معلمی استاد قرائتی + سند تحول بنیادین",
            gradeBadge = "دفترچه فرهنگیان",
            editionYear = "منبع رسمی آزمون اختصاصی فرهنگیان ۱۴۰۶",
            officialChapPdfUrl = "http://chap.sch.ir/sites/default/files/lbooks/1403-1404/112215.pdf",
            officialChapters = listOf(
                "فصل ۱: جایگاه و عظمت معلم، انگیزه‌های الهی و اخلاص در تدریس",
                "فصل ۲: ویژگی‌ها، بایسته‌ها و آفات اخلاقی و علمی معلم",
                "فصل ۳: شیوه‌ها و مهارت‌های ارتباطی، تشویق و تنبیه و قصه‌گویی در کلاس",
                "پیوست: ساحت‌های شش‌گانه تربیتی در سند تحول بنیادین آموزش و پرورش"
            )
        ),
        OfficialTextbookPdf(
            bookId = "pdf_farhangian_motahari",
            subjectId = "farhangian",
            gradeLevel = 3,
            ministryCode = "FAR-MOT",
            title = "تعلیم و تربیت در اسلام — استاد شهید مرتضی مطهری",
            gradeBadge = "منبع رسمی فرهنگیان",
            editionYear = "منبع رسمی مصوب سازمان سنجش برای دانشگاه فرهنگیان",
            officialChapPdfUrl = "http://chap.sch.ir/sites/default/files/lbooks/1403-1404/112216.pdf",
            officialChapters = listOf(
                "بخش اول: پرورش عقل و تفکر مستقل در نظام تربیتی اسلام",
                "بخش دوم: عوامل مؤثر در تربیت (عادت، تقوا، عبادت، کار و معاشرت)",
                "بخش سوم: کرامت نفس و تفاوت عزت‌نفس مؤمن با تکبر و خودبزرگ‌بینی",
                "بخش چهارم: پیوند علم و ایمان در فرآیند یاددهی-یادگیری دانش‌آموزان"
            )
        ),
        OfficialTextbookPdf(
            bookId = "pdf_farhangian_intelligence",
            subjectId = "farhangian",
            gradeLevel = 4,
            ministryCode = "FAR-INT",
            title = "هوش و استعداد معلمی + مصاحبه و کانون ارزیابی فرهنگیان",
            gradeBadge = "هوش و مصاحبه",
            editionYear = "ویژه کنکور و مصاحبه فرهنگیان ۱۴۰۶",
            officialChapPdfUrl = "http://chap.sch.ir/sites/default/files/lbooks/1403-1404/110220.pdf",
            officialChapters = listOf(
                "بخش ۱: هوش کلامی، درک مطلب، استنباط منطقی و تحلیل استدلال",
                "بخش ۲: هوش ریاضی، الگوهای عددی و حل مسئله ویژه داوطلبان انسانی",
                "بخش ۳: هوش فضایی-تجسمی و تحلیل سناریوهای حل اختلاف در کلاس درس",
                "بخش ۴: سناریوهای واقعی کانون ارزیابی، مدیریت کلاس و سوالات مصاحبه گزینش"
            )
        ),
        OfficialTextbookPdf(
            bookId = "pdf_farhangian_tamaddon",
            subjectId = "farhangian",
            gradeLevel = 2,
            ministryCode = "FAR-CIV",
            title = "فرهنگ و تمدن اسلام و ایران ویژه کنکور دانشگاه فرهنگیان",
            gradeBadge = "فرهنگ و تمدن",
            editionYear = "گزیده منابع رسمی اعلامی سازمان سنجش آموزش کشور",
            officialChapPdfUrl = "http://chap.sch.ir/sites/default/files/lbooks/1403-1404/110218.pdf",
            officialChapters = listOf(
                "فصل ۱: پیدایش تمدن اسلامی و شکوفایی مراکز علمی، نظامیه‌ها و رصدخانه‌ها",
                "فصل ۲: سهم دانشمندان ایرانی و مسلمان در ریاضیات، نجوم، طب و فلسفه",
                "فصل ۳: تاریخ تحول آموزش و پرورش ایران از مکتب‌خانه‌ها تا دارالفنون و دانشگاه"
            )
        ),
        OfficialTextbookPdf(
            bookId = "pdf_farhangian_ayat_ahkam",
            subjectId = "farhangian",
            gradeLevel = 2,
            ministryCode = "FAR-REL",
            title = "آیات، روایات و احکام گزینش و مصاحبه تخصصی فرهنگیان",
            gradeBadge = "آیات و احکام",
            editionYear = "جمع‌آوری جامع پرسش‌های پرتکرار آزمون کتبی و مصاحبه گزینش",
            officialChapPdfUrl = "http://chap.sch.ir/sites/default/files/lbooks/1403-1404/110219.pdf",
            officialChapters = listOf(
                "بخش ۱: پیام آیات دهم و یازدهم مرتبط با هدایت، امامت، اخلاص و تفکر",
                "بخش ۲: احکام مبتلابه و پرتکرار گزینش (نماز، روزه، ولایت فقیه، خمس، غسل)",
                "بخش ۳: مسائل روز، قانون اساسی، تاریخ اسلام و سند تحول بنیادین"
            )
        ),
        OfficialTextbookPdf(
            bookId = "pdf_general_10_11_12",
            subjectId = "general_humanities",
            gradeLevel = 4,
            ministryCode = "112226",
            title = "سلامت و بهداشت ۱۲، هویت اجتماعی ۱۲، محیط زیست ۱۱ و سواد رسانه‌ای ۱۰",
            gradeBadge = "دروس نهایی",
            editionYear = "چاپ ۱۴۰۳ تا ۱۴۰۵ آموزش و پرورش",
            officialChapPdfUrl = "http://chap.sch.ir/sites/default/files/lbooks/1403-1404/112226.pdf",
            officialChapters = listOf(
                "کتاب ۱: سلامت و بهداشت دوازدهم (فرمول BMI، بیماری‌های واگیر و غیرواگیر، تغذیه سالم)",
                "کتاب ۲: هویت اجتماعی دوازدهم و مدیریت خانواده و سبک زندگی دوازدهم",
                "کتاب ۳: انسان و محیط زیست یازدهم (آب مجازی، ردپای محیط‌زیستی، انرژی‌های پاک)",
                "کتاب ۴: تفکر و سواد رسانه‌ای دهم (فنون اقناع، کلیشه‌سازی و رژیم مصرف رسانه‌ای)"
            )
        )
    )

    fun getBookForSubjectAndGrade(subjectId: String, gradeLevel: Int): OfficialTextbookPdf {
        return allBooks.firstOrNull { it.subjectId == subjectId && it.gradeLevel == gradeLevel }
            ?: allBooks.firstOrNull { it.subjectId == subjectId }
            ?: allBooks.first()
    }

    fun getBooksForSubject(subjectId: String): List<OfficialTextbookPdf> {
        return allBooks.filter { it.subjectId == subjectId }.sortedBy { it.gradeLevel }
    }
}

/**
 * Builds the external-viewer intent for an official ministry PDF.
 *
 * FLAG_ACTIVITY_NEW_TASK is required: the callers hand
 * [NativeTextbookPdfGenerator.startOfficialMinistryPdfDownload] a plain
 * [Context] (LocalContext.current), which is not guaranteed to be an Activity
 * context — and startActivity() from a non-Activity context throws
 * ("Calling startActivity() from outside of an Activity context requires the
 * FLAG_ACTIVITY_NEW_TASK flag"). The flag is harmless when the context already
 * is an Activity, so it is set unconditionally.
 */
fun buildExternalPdfViewIntent(pdfUrl: String): Intent =
    Intent(Intent.ACTION_VIEW, Uri.parse(pdfUrl)).apply {
        addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
    }

/**
 * تولیدکننده فایل PDF چندصفحه‌ای با فونت رسمی وزیرمتن، کادربندی استاندارد دولایه، راست‌چین دقیق و گرافیک کتاب درسی
 */
object NativeTextbookPdfGenerator {

    fun generateOrGetPdfFile(
        context: Context,
        book: OfficialTextbookPdf,
        lessons: List<ZeroLesson>
    ): File {
        val dir = File(context.filesDir, "textbooks_pdf")
        if (!dir.exists()) dir.mkdirs()
        val outFile = File(dir, "${book.bookId}_v5.pdf")
        if (outFile.exists() && outFile.length() > 2048L) {
            return outFile
        }

        val pageWidth = 595 // استاندارد A4
        val pageHeight = 842
        val outerMargin = 20f
        val innerMargin = 26f
        val contentMargin = 38f
        val contentWidth = (pageWidth - contentMargin * 2).toInt()

        val vazirRegular = runCatching {
            ResourcesCompat.getFont(context, R.font.vazirmatn)
        }.getOrNull() ?: Typeface.DEFAULT

        val vazirBold = runCatching {
            ResourcesCompat.getFont(context, R.font.vazirmatn_bold)
        }.getOrNull() ?: Typeface.create(vazirRegular, Typeface.BOLD)

        val pdfDocument = PdfDocument()

        val titlePaint = TextPaint(Paint.ANTI_ALIAS_FLAG).apply {
            color = AndroidColor.rgb(17, 62, 122)
            textSize = 14.5f
            typeface = vazirBold
        }
        val subtitlePaint = TextPaint(Paint.ANTI_ALIAS_FLAG).apply {
            color = AndroidColor.rgb(30, 58, 138)
            textSize = 11.2f
            typeface = vazirBold
        }
        val bodyPaint = TextPaint(Paint.ANTI_ALIAS_FLAG).apply {
            color = AndroidColor.rgb(24, 27, 35)
            textSize = 10.2f
            typeface = vazirRegular
        }
        val formulaPaint = TextPaint(Paint.ANTI_ALIAS_FLAG).apply {
            color = AndroidColor.rgb(6, 95, 70)
            textSize = 10.5f
            typeface = vazirBold
        }
        val footerPaint = TextPaint(Paint.ANTI_ALIAS_FLAG).apply {
            color = AndroidColor.rgb(100, 116, 139)
            textSize = 9f
            typeface = vazirBold
        }
        val outerFramePaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
            color = AndroidColor.rgb(37, 99, 235)
            style = Paint.Style.STROKE
            strokeWidth = 2.2f
        }
        val innerFramePaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
            color = AndroidColor.rgb(147, 197, 253)
            style = Paint.Style.STROKE
            strokeWidth = 1f
        }
        val cornerOrnamentPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
            color = AndroidColor.rgb(29, 78, 216)
            style = Paint.Style.FILL
        }
        val boxBgPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
            color = AndroidColor.rgb(239, 246, 255)
            style = Paint.Style.FILL
        }
        val boxBorderPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
            color = AndroidColor.rgb(59, 130, 246)
            style = Paint.Style.STROKE
            strokeWidth = 1.2f
        }
        val formulaBoxBgPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
            color = AndroidColor.rgb(236, 253, 245)
            style = Paint.Style.FILL
        }
        val formulaBoxBorderPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
            color = AndroidColor.rgb(16, 185, 129)
            style = Paint.Style.STROKE
            strokeWidth = 1.2f
        }
        val headerBgPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
            color = AndroidColor.rgb(23, 37, 84)
            style = Paint.Style.FILL
        }
        val headerAccentPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
            color = AndroidColor.rgb(59, 130, 246)
            style = Paint.Style.FILL
        }
        val whiteTitlePaint = TextPaint(Paint.ANTI_ALIAS_FLAG).apply {
            color = AndroidColor.WHITE
            textSize = 13.5f
            typeface = vazirBold
        }

        var pageNumber = 1

        fun buildRtlLayout(
            text: String,
            paint: TextPaint,
            width: Int = contentWidth,
            alignment: Layout.Alignment = Layout.Alignment.ALIGN_NORMAL
        ): StaticLayout {
            val cleanText = BidiFormatter.formatMixedPersianText(
                BidiFormatter.convertCaretsToSuperscripts(text)
            )
            return StaticLayout.Builder
                .obtain(cleanText, 0, cleanText.length, paint, width.coerceAtLeast(100))
                .setTextDirection(TextDirectionHeuristics.RTL)
                .setAlignment(alignment)
                .setLineSpacing(3.5f, 1.22f)
                .setIncludePad(false)
                .build()
        }

        fun drawFramedPageChrome(canvas: Canvas, pageNum: Int) {
            canvas.drawColor(AndroidColor.rgb(252, 253, 255))
            // کادر بیرونی ضخیم استاندارد کتاب درسی
            canvas.drawRoundRect(
                RectF(outerMargin, outerMargin, pageWidth - outerMargin, pageHeight - outerMargin),
                14f,
                14f,
                outerFramePaint
            )
            // کادر درونی ظریف استاندارد کتاب درسی
            canvas.drawRoundRect(
                RectF(innerMargin, innerMargin, pageWidth - innerMargin, pageHeight - innerMargin),
                10f,
                10f,
                innerFramePaint
            )
            // گل‌میخ‌های گرافیکی چهار گوشه کادر
            val corners = listOf(
                outerMargin to outerMargin,
                (pageWidth - outerMargin) to outerMargin,
                outerMargin to (pageHeight - outerMargin),
                (pageWidth - outerMargin) to (pageHeight - outerMargin)
            )
            corners.forEach { (cx, cy) ->
                canvas.drawCircle(cx, cy, 4.5f, cornerOrnamentPaint)
            }
            // نوار شماره صفحه و کد کتاب در پایین کادر
            val footerLayout = buildRtlLayout(
                "صفحه $pageNum • ${book.title} (کد رسمی: ${book.ministryCode})",
                footerPaint,
                contentWidth,
                Layout.Alignment.ALIGN_CENTER
            )
            canvas.save()
            canvas.translate(contentMargin, pageHeight - innerMargin - 18f)
            footerLayout.draw(canvas)
            canvas.restore()
        }

        fun drawFramedBox(
            canvas: Canvas,
            text: String,
            paint: TextPaint,
            topY: Float,
            bgPaint: Paint? = null,
            borderPaint: Paint? = null,
            headerRibbonTitle: String? = null,
            ribbonColorRgb: Int = AndroidColor.rgb(37, 99, 235)
        ): Float {
            val innerW = if (bgPaint != null || borderPaint != null) contentWidth - 24 else contentWidth
            val layout = buildRtlLayout(text, paint, innerW)
            val ribbonH = if (headerRibbonTitle != null) 22f else 0f
            val boxH = ribbonH + layout.height + if (bgPaint != null || borderPaint != null) 18f else 6f
            val rect = RectF(contentMargin, topY, pageWidth - contentMargin, topY + boxH)
            if (bgPaint != null) {
                canvas.drawRoundRect(rect, 10f, 10f, bgPaint)
            }
            if (headerRibbonTitle != null) {
                val rPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
                    color = ribbonColorRgb
                    style = Paint.Style.FILL
                }
                canvas.drawRoundRect(
                    RectF(contentMargin, topY, pageWidth - contentMargin, topY + ribbonH),
                    10f,
                    10f,
                    rPaint
                )
                val rTextPaint = TextPaint(Paint.ANTI_ALIAS_FLAG).apply {
                    color = AndroidColor.WHITE
                    textSize = 10f
                    typeface = vazirBold
                }
                val rLayout = buildRtlLayout(headerRibbonTitle, rTextPaint, innerW)
                canvas.save()
                canvas.translate(contentMargin + 12f, topY + 3f)
                rLayout.draw(canvas)
                canvas.restore()
            }
            if (borderPaint != null) {
                canvas.drawRoundRect(rect, 10f, 10f, borderPaint)
            }
            canvas.save()
            val offsetX = if (bgPaint != null || borderPaint != null) contentMargin + 12f else contentMargin
            val offsetY = topY + ribbonH + if (bgPaint != null || borderPaint != null) 9f else 2f
            canvas.translate(offsetX, offsetY)
            layout.draw(canvas)
            canvas.restore()
            return topY + boxH + 8f
        }

        fun drawVectorInfographicDiagramOnPdf(
            canvas: Canvas,
            topY: Float,
            diagramTitle: String,
            node1: String,
            node2: String,
            node3: String
        ): Float {
            val boxH = 74f
            val fullRect = RectF(contentMargin, topY, pageWidth - contentMargin, topY + boxH)
            val cardBg = Paint(Paint.ANTI_ALIAS_FLAG).apply {
                color = AndroidColor.rgb(248, 250, 252)
                style = Paint.Style.FILL
            }
            val cardBorder = Paint(Paint.ANTI_ALIAS_FLAG).apply {
                color = AndroidColor.rgb(99, 102, 241)
                style = Paint.Style.STROKE
                strokeWidth = 1.5f
            }
            canvas.drawRoundRect(fullRect, 10f, 10f, cardBg)
            canvas.drawRoundRect(fullRect, 10f, 10f, cardBorder)

            val diagHeaderLayout = buildRtlLayout(
                "📐 نمودار و ساختار گرافیکی فصل: $diagramTitle",
                subtitlePaint,
                contentWidth - 20,
                Layout.Alignment.ALIGN_CENTER
            )
            canvas.save()
            canvas.translate(contentMargin + 10f, topY + 5f)
            diagHeaderLayout.draw(canvas)
            canvas.restore()

            val nodeTop = topY + 28f
            val nodeBottom = topY + boxH - 8f
            val gap = 14f
            val singleW = (contentWidth - 24f - gap * 2f) / 3f
            val nodeColors = listOf(
                AndroidColor.rgb(37, 99, 235),
                AndroidColor.rgb(124, 58, 237),
                AndroidColor.rgb(5, 150, 105)
            )
            val nodes = listOf(node3, node2, node1) // RTL order right-to-left
            nodes.forEachIndexed { i, label ->
                val left = contentMargin + 12f + i * (singleW + gap)
                val right = left + singleW
                val nFill = Paint(Paint.ANTI_ALIAS_FLAG).apply {
                    color = nodeColors[i]
                    style = Paint.Style.FILL
                    alpha = 28
                }
                val nStroke = Paint(Paint.ANTI_ALIAS_FLAG).apply {
                    color = nodeColors[i]
                    style = Paint.Style.STROKE
                    strokeWidth = 1.6f
                }
                canvas.drawRoundRect(RectF(left, nodeTop, right, nodeBottom), 8f, 8f, nFill)
                canvas.drawRoundRect(RectF(left, nodeTop, right, nodeBottom), 8f, 8f, nStroke)

                val nPaint = TextPaint(Paint.ANTI_ALIAS_FLAG).apply {
                    color = nodeColors[i]
                    textSize = 8.8f
                    typeface = vazirBold
                }
                val nLayout = buildRtlLayout(label, nPaint, (singleW - 10f).toInt(), Layout.Alignment.ALIGN_CENTER)
                canvas.save()
                canvas.translate(left + 5f, nodeTop + 6f)
                nLayout.draw(canvas)
                canvas.restore()
            }
            return topY + boxH + 8f
        }

        // ==================== صفحه ۱: جلد گرافیکی و فهرست کادربندی‌شده فصول ====================
        run {
            val currentNum = pageNumber++
            val pageInfo = PdfDocument.PageInfo.Builder(pageWidth, pageHeight, currentNum).create()
            val page = pdfDocument.startPage(pageInfo)
            val canvas = page.canvas
            drawFramedPageChrome(canvas, currentNum)

            // کادر سرصفحه گرافیکی جلد
            val headerRect = RectF(contentMargin, 36f, pageWidth - contentMargin, 130f)
            canvas.drawRoundRect(headerRect, 14f, 14f, headerBgPaint)
            canvas.drawRoundRect(
                RectF(contentMargin + 6f, 42f, pageWidth - contentMargin - 6f, 52f),
                6f,
                6f,
                headerAccentPaint
            )

            val headerLayout = buildRtlLayout(
                "کتاب درسی و درسنامه جامع: ${book.title}\n${book.editionYear} • کد رسمی وزارت آموزش و پرورش: ${book.ministryCode}",
                whiteTitlePaint,
                contentWidth - 28,
                Layout.Alignment.ALIGN_CENTER
            )
            canvas.save()
            canvas.translate(contentMargin + 14f, 60f)
            headerLayout.draw(canvas)
            canvas.restore()

            var y = 142f
            y = drawVectorInfographicDiagramOnPdf(
                canvas = canvas,
                topY = y,
                diagramTitle = book.title,
                node1 = "۱. آموزش خط‌به‌خط کتاب",
                node2 = "۲. نمودار و فرمول ۵ ثانیه‌ای",
                node3 = "۳. حل تست و نمونه سوال نهایی"
            )

            y = drawFramedBox(
                canvas = canvas,
                text = "در این کتاب تمام فصول رسمی آموزش و پرورش با کادربندی استاندارد، فونت وزیرمتن و نمودارهای درختی چیدمان شده‌اند:",
                paint = bodyPaint,
                topY = y,
                bgPaint = boxBgPaint,
                borderPaint = boxBorderPaint,
                headerRibbonTitle = "📑 فهرست رسمی فصول و درس‌های کتاب (${book.officialChapters.size} فصل)",
                ribbonColorRgb = AndroidColor.rgb(29, 78, 216)
            )

            book.officialChapters.forEachIndexed { idx, ch ->
                if (y < pageHeight - 95f) {
                    y = drawFramedBox(
                        canvas,
                        "فصل / بخش ${idx + 1}: $ch",
                        bodyPaint,
                        y,
                        formulaBoxBgPaint,
                        formulaBoxBorderPaint
                    )
                }
            }

            if (y < pageHeight - 90f) {
                y = drawFramedBox(
                    canvas,
                    "✨ در صفحات بعدی: آموزش گام‌به‌گام، کادرهای گرافیکی فرمول و جدول، تکنیک‌های ابداعی ۵ ثانیه‌ای و نمونه سوالات حل‌شده هر فصل با فونت وزیرمتن آورده شده است.",
                    subtitlePaint,
                    y + 4f,
                    boxBgPaint,
                    boxBorderPaint
                )
            }

            pdfDocument.finishPage(page)
        }

        // ==================== صفحات ۲ به بعد: درسنامه‌های کادربندی‌شده با نمودار گرافیکی ====================
        lessons.forEachIndexed { index, lesson ->
            var currentNum = pageNumber++
            var pageInfo = PdfDocument.PageInfo.Builder(pageWidth, pageHeight, currentNum).create()
            var page = pdfDocument.startPage(pageInfo)
            var canvas = page.canvas
            drawFramedPageChrome(canvas, currentNum)

            var y = 38f

            fun ensureSpace(neededHeight: Float) {
                if (y + neededHeight > pageHeight - 52f) {
                    pdfDocument.finishPage(page)
                    currentNum = pageNumber++
                    pageInfo = PdfDocument.PageInfo.Builder(pageWidth, pageHeight, currentNum).create()
                    page = pdfDocument.startPage(pageInfo)
                    canvas = page.canvas
                    drawFramedPageChrome(canvas, currentNum)
                    y = 38f
                }
            }

            // سرصفحه کادربندی‌شده درس با نوار رنگی بالا
            y = drawFramedBox(
                canvas = canvas,
                text = lesson.subtitle,
                paint = subtitlePaint,
                topY = y,
                bgPaint = boxBgPaint,
                borderPaint = boxBorderPaint,
                headerRibbonTitle = "📘 درس ${index + 1}: ${lesson.title}",
                ribbonColorRgb = AndroidColor.rgb(30, 58, 138)
            )

            // نمودار گرافیکی ۳ مرحله‌ای بالای هر درس در PDF
            val s1 = lesson.steps.getOrNull(0)?.title ?: "مفهوم پایه"
            val s2 = lesson.steps.getOrNull(1)?.title ?: "فرمول و رابطه"
            val s3 = lesson.steps.getOrNull(2)?.title ?: "تکنیک تست کنکور"
            ensureSpace(85f)
            y = drawVectorInfographicDiagramOnPdf(
                canvas = canvas,
                topY = y,
                diagramTitle = lesson.title,
                node1 = "۱. $s1",
                node2 = "۲. $s2",
                node3 = "۳. $s3"
            )

            if (lesson.whyStudentsStruggle.isNotBlank()) {
                ensureSpace(65f)
                y = drawFramedBox(
                    canvas,
                    "🎯 نکته کلیدی و هدف درس: ${lesson.whyStudentsStruggle}",
                    subtitlePaint,
                    y
                )
            }

            lesson.steps.forEach { step ->
                ensureSpace(125f)
                y = drawFramedBox(
                    canvas = canvas,
                    text = step.simpleExplanation,
                    paint = bodyPaint,
                    topY = y,
                    bgPaint = boxBgPaint,
                    borderPaint = boxBorderPaint,
                    headerRibbonTitle = "بخش ${step.stepNumber}: ${step.title}",
                    ribbonColorRgb = AndroidColor.rgb(37, 99, 235)
                )
                if (step.visualDiagramText.isNotBlank()) {
                    ensureSpace(90f)
                    y = drawFramedBox(
                        canvas = canvas,
                        text = step.visualDiagramText,
                        paint = formulaPaint,
                        topY = y,
                        bgPaint = formulaBoxBgPaint,
                        borderPaint = formulaBoxBorderPaint,
                        headerRibbonTitle = "📊 کادر گرافیکی فرمول، جدول و نمودار",
                        ribbonColorRgb = AndroidColor.rgb(5, 150, 105)
                    )
                }
                if (step.memoryTrick.isNotBlank()) {
                    ensureSpace(50f)
                    y = drawFramedBox(
                        canvas,
                        "💡 رمز و تکنیک سریع: ${step.memoryTrick}",
                        subtitlePaint,
                        y
                    )
                }
            }

            val advMethod = AdvancedMnemonicEngine.getMethodForLesson(lesson)
            ensureSpace(130f)
            y = drawFramedBox(
                canvas,
                "⚡ ${advMethod.methodTitle}\n• کد رمز حافظه: ${advMethod.mnemonicCode}\n• الگوریتم ۵ ثانیه‌ای تست: ${advMethod.fiveSecondAlgorithm}\n• مثال سریع: ${advMethod.exampleShortcut}",
                formulaPaint,
                y,
                formulaBoxBgPaint,
                formulaBoxBorderPaint
            )

            if (lesson.goldenFormulas.isNotEmpty()) {
                ensureSpace(90f)
                val joinedFormulas = "🏆 کادر جمع‌بندی قواعد و فرمول‌های طلایی:\n" +
                    lesson.goldenFormulas.joinToString("\n") { "• $it" }
                y = drawFramedBox(
                    canvas,
                    joinedFormulas,
                    formulaPaint,
                    y,
                    boxBgPaint,
                    boxBorderPaint
                )
            }

            if (lesson.workedExamples.isNotEmpty()) {
                lesson.workedExamples.forEachIndexed { exIdx, ex ->
                    ensureSpace(100f)
                    y = drawFramedBox(
                        canvas,
                        "✏️ نمونه سوال امتحانی و کنکوری ${exIdx + 1}: ${ex.question}\n✅ پاسخ تشریحی: ${ex.stepByStepSolution}\n⚠️ دام تستی: ${ex.commonTrapAlert}",
                        bodyPaint,
                        y,
                        boxBgPaint,
                        boxBorderPaint
                    )
                }
            }

            pdfDocument.finishPage(page)
        }

        FileOutputStream(outFile).use { fos ->
            pdfDocument.writeTo(fos)
        }
        pdfDocument.close()
        return outFile
    }

    fun startOfficialMinistryPdfDownload(context: Context, book: OfficialTextbookPdf) {
        try {
            val request = DownloadManager.Request(Uri.parse(book.officialChapPdfUrl))
                .setTitle("دانلود PDF رسمی: ${book.title}")
                .setDescription("پایگاه کتاب‌های درسی آموزش و پرورش — کد کتاب ${book.ministryCode}")
                .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
                .setDestinationInExternalPublicDir(
                    Environment.DIRECTORY_DOWNLOADS,
                    "Ketab_${book.ministryCode}.pdf"
                )
                .setAllowedOverMetered(true)
                .setAllowedOverRoaming(true)

            val dm = context.getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager
            dm.enqueue(request)
            Toast.makeText(
                context,
                "دانلود PDF رسمی کتاب (کد ${book.ministryCode}) در پوشه Downloads آغاز شد.",
                Toast.LENGTH_LONG
            ).show()
        } catch (_: Exception) {
            try {
                context.startActivity(buildExternalPdfViewIntent(book.officialChapPdfUrl))
            } catch (_: Exception) {
                Toast.makeText(
                    context,
                    "نسخه کامل کتاب درسی همین الان در داخل برنامه باز است.",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }
}

/**
 * پنجره تمام‌صفحه مطالعه گرافیکی، کادربندی‌شده و ورق‌زن (اسکرول چپ و راست) کتاب درسی و PDF
 * طراحی‌شده بر اساس Pixel UI و Material 3 Expressive اندروید ۱۷ با فونت وزیرمتن و شکل‌های گرافیکی
 */
@Composable
fun InAppTextbookPdfViewerDialog(
    book: OfficialTextbookPdf,
    lessons: List<ZeroLesson>,
    accentColor: Color,
    onDismiss: () -> Unit,
    onSelectLessonFromToc: (String) -> Unit = {}
) {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()

    // تعداد کل صفحات کتاب گرافیکی: صفحه ۰ (جلد و فهرست رسمی) + ۱ صفحه کادربندی‌شده کامل برای هر درس
    val totalInteractivePages = remember(lessons.size) { 1 + lessons.size }
    val pagerState = rememberPagerState(
        initialPage = 0,
        pageCount = { totalInteractivePages }
    )

    // حالت نمایش: false = نمای کتاب گرافیکی کادربندی‌شده با فونت وزیرمتن و شکل‌ها (پیش‌فرض) | true = پیش‌نمایش چاپی PDF
    var isPrintBitmapMode by remember { mutableStateOf(false) }
    var pdfFile by remember(book.bookId) { mutableStateOf<File?>(null) }
    var pdfPageCount by remember(book.bookId) { mutableIntStateOf(1) }
    var currentPdfBitmapPage by remember(book.bookId) { mutableIntStateOf(0) }
    var renderedBitmap by remember { mutableStateOf<Bitmap?>(null) }
    var isGeneratingPdf by remember(book.bookId) { mutableStateOf(false) }
    var zoomScale by remember { mutableFloatStateOf(1f) }

    val savePdfLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.CreateDocument("application/pdf")
    ) { uri: Uri? ->
        if (uri != null) {
            scope.launch(Dispatchers.IO) {
                try {
                    val file = pdfFile ?: NativeTextbookPdfGenerator.generateOrGetPdfFile(context, book, lessons).also {
                        pdfFile = it
                    }
                    context.contentResolver.openOutputStream(uri)?.use { out ->
                        file.inputStream().use { input ->
                            input.copyTo(out)
                        }
                    }
                    withContext(Dispatchers.Main) {
                        Toast.makeText(
                            context,
                            "فایل PDF کادربندی‌شده «${book.title}» با موفقیت ذخیره شد.",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                } catch (_: Exception) {
                }
            }
        }
    }

    LaunchedEffect(book.bookId, lessons.size, isPrintBitmapMode) {
        if (isPrintBitmapMode && pdfFile == null) {
            isGeneratingPdf = true
            withContext(Dispatchers.IO) {
                val generated = NativeTextbookPdfGenerator.generateOrGetPdfFile(context, book, lessons)
                pdfFile = generated
                try {
                    ParcelFileDescriptor.open(generated, ParcelFileDescriptor.MODE_READ_ONLY)?.use { pfd ->
                        PdfRenderer(pfd).use { renderer ->
                            pdfPageCount = renderer.pageCount.coerceAtLeast(1)
                        }
                    }
                } catch (_: Exception) {
                    pdfPageCount = 1
                }
            }
            isGeneratingPdf = false
        }
    }

    LaunchedEffect(pdfFile, currentPdfBitmapPage, isPrintBitmapMode) {
        if (!isPrintBitmapMode) return@LaunchedEffect
        val file = pdfFile ?: return@LaunchedEffect
        if (!file.exists()) return@LaunchedEffect
        withContext(Dispatchers.IO) {
            try {
                ParcelFileDescriptor.open(file, ParcelFileDescriptor.MODE_READ_ONLY)?.use { pfd ->
                    PdfRenderer(pfd).use { renderer ->
                        if (currentPdfBitmapPage in 0 until renderer.pageCount) {
                            renderer.openPage(currentPdfBitmapPage).use { page ->
                                val bmpWidth = page.width * 2
                                val bmpHeight = page.height * 2
                                val bmp = Bitmap.createBitmap(bmpWidth, bmpHeight, Bitmap.Config.ARGB_8888)
                                val canvas = Canvas(bmp)
                                canvas.drawColor(AndroidColor.WHITE)
                                page.render(bmp, null, null, PdfRenderer.Page.RENDER_MODE_FOR_DISPLAY)
                                renderedBitmap = bmp
                            }
                        }
                    }
                }
            } catch (_: Exception) {
            }
        }
    }

    DisposableEffect(Unit) {
        onDispose {
            renderedBitmap = null
        }
    }

    Dialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(usePlatformDefaultWidth = false)
    ) {
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .testTag("in_app_pdf_viewer_dialog"),
            color = MaterialTheme.colorScheme.background
        ) {
            Column(modifier = Modifier.fillMaxSize()) {
                // ==================== ۱. هدر Pixel UI Expressive ====================
                Surface(
                    color = accentColor,
                    tonalElevation = 6.dp,
                    shape = RoundedCornerShape(bottomStart = 28.dp, bottomEnd = 28.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 14.dp, vertical = 10.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
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
                                    onClick = onDismiss,
                                    shape = CircleShape,
                                    color = Color.White.copy(alpha = 0.2f),
                                    modifier = Modifier.size(36.dp)
                                ) {
                                    Box(contentAlignment = Alignment.Center) {
                                        Icon(
                                            imageVector = Icons.Default.Close,
                                            contentDescription = "بستن کتاب",
                                            tint = Color.White,
                                            modifier = Modifier.size(20.dp)
                                        )
                                    }
                                }
                                Spacer(modifier = Modifier.width(10.dp))
                                Column {
                                    Text(
                                        text = BidiFormatter.formatMixedPersianText("📕 ${book.title}"),
                                        style = MaterialTheme.typography.titleSmall,
                                        fontWeight = FontWeight.Bold,
                                        color = Color.White,
                                        maxLines = 2,
                                        overflow = TextOverflow.Ellipsis
                                    )
                                    Text(
                                        text = BidiFormatter.formatMixedPersianText("${book.editionYear} • کد رسمی: ${book.ministryCode}"),
                                        style = MaterialTheme.typography.labelSmall,
                                        color = Color.White.copy(alpha = 0.92f),
                                        maxLines = 1,
                                        overflow = TextOverflow.Ellipsis
                                    )
                                }
                            }

                            // دکمه سوییچ بین نمای کتاب گرافیکی و پیش‌نمایش PDF چاپی
                            Surface(
                                onClick = { isPrintBitmapMode = !isPrintBitmapMode },
                                color = Color.White,
                                shape = RoundedCornerShape(50)
                            ) {
                                Text(
                                    text = if (isPrintBitmapMode) "🎨 نمای گرافیکی کتاب" else "🖨️ نمای چاپی A4",
                                    style = MaterialTheme.typography.labelSmall,
                                    fontWeight = FontWeight.ExtraBold,
                                    color = accentColor,
                                    modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp)
                                )
                            }
                        }

                        // ردیف دکمه‌های ذخیره و دانلود PDF و پرش به فهرست
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(6.dp)
                        ) {
                            Button(
                                onClick = {
                                    savePdfLauncher.launch("${book.title.replace(" ", "_")}.pdf")
                                },
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = Color.White,
                                    contentColor = accentColor
                                ),
                                shape = RoundedCornerShape(50),
                                contentPadding = PaddingValues(horizontal = 10.dp, vertical = 6.dp),
                                modifier = Modifier.weight(1f)
                            ) {
                                Icon(
                                    imageVector = Icons.Default.SaveAlt,
                                    contentDescription = null,
                                    modifier = Modifier.size(15.dp)
                                )
                                Spacer(modifier = Modifier.width(4.dp))
                                Text(
                                    text = "ذخیره فایل PDF",
                                    style = MaterialTheme.typography.labelSmall,
                                    fontWeight = FontWeight.Bold,
                                    maxLines = 1,
                                    overflow = TextOverflow.Ellipsis
                                )
                            }

                            Button(
                                onClick = {
                                    NativeTextbookPdfGenerator.startOfficialMinistryPdfDownload(context, book)
                                },
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = Color.Black.copy(alpha = 0.25f),
                                    contentColor = Color.White
                                ),
                                shape = RoundedCornerShape(50),
                                contentPadding = PaddingValues(horizontal = 10.dp, vertical = 6.dp),
                                modifier = Modifier.weight(1f)
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Download,
                                    contentDescription = null,
                                    modifier = Modifier.size(15.dp)
                                )
                                Spacer(modifier = Modifier.width(4.dp))
                                Text(
                                    text = BidiFormatter.formatMixedPersianText("دانلود چاپ (${book.ministryCode})"),
                                    style = MaterialTheme.typography.labelSmall,
                                    fontWeight = FontWeight.Bold,
                                    maxLines = 1,
                                    overflow = TextOverflow.Ellipsis
                                )
                            }

                            if (!isPrintBitmapMode && pagerState.currentPage != 0) {
                                Button(
                                    onClick = {
                                        scope.launch { pagerState.animateScrollToPage(0) }
                                    },
                                    colors = ButtonDefaults.buttonColors(
                                        containerColor = Color.White.copy(alpha = 0.22f),
                                        contentColor = Color.White
                                    ),
                                    shape = RoundedCornerShape(50),
                                    contentPadding = PaddingValues(horizontal = 10.dp, vertical = 6.dp)
                                ) {
                                    Text(
                                        text = "📑 فهرست",
                                        style = MaterialTheme.typography.labelSmall,
                                        fontWeight = FontWeight.Bold
                                    )
                                }
                            }
                        }
                    }
                }

                // ==================== ۲. بدنه اصلی کتاب درسی (گرافیکی ورق‌زن یا چاپی) ====================
                if (!isPrintBitmapMode) {
                    // نمای گرافیکی کادربندی‌شده با فونت وزیرمتن، شکل‌های تعاملی و اسکرول چپ و راست (HorizontalPager)
                    HorizontalPager(
                        state = pagerState,
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxWidth()
                    ) { pageIdx ->
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(horizontal = 10.dp, vertical = 8.dp)
                        ) {
                            // کادر استاندارد دو لایه کتاب درسی (Double Framed Textbook Page)
                            Card(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .border(
                                        width = 2.5.dp,
                                        color = accentColor.copy(alpha = 0.85f),
                                        shape = RoundedCornerShape(26.dp)
                                    )
                                    .padding(4.dp)
                                    .border(
                                        width = 1.dp,
                                        color = accentColor.copy(alpha = 0.35f),
                                        shape = RoundedCornerShape(22.dp)
                                    ),
                                shape = RoundedCornerShape(22.dp),
                                colors = CardDefaults.cardColors(
                                    containerColor = MaterialTheme.colorScheme.surface
                                ),
                                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
                            ) {
                                if (pageIdx == 0) {
                                    // صفحه اول: جلد گرافیکی کادربندی‌شده + فهرست رسمی فصول + فهرست درسنامه‌ها
                                    LazyColumn(
                                        modifier = Modifier.fillMaxSize(),
                                        contentPadding = PaddingValues(16.dp),
                                        verticalArrangement = Arrangement.spacedBy(12.dp)
                                    ) {
                                        item {
                                            FramedTextbookGraphicHeaderBanner(
                                                book = book,
                                                pageLabel = "صفحه ۱ • جلد، نقشه مفهومی و فهرست رسمی کتاب درسی",
                                                accentColor = accentColor
                                            )
                                        }

                                        // اینفوگرافیک و نقشه مفهومی گرافیکی کتاب در صفحه اول PDF
                                        item {
                                            Surface(
                                                color = MaterialTheme.colorScheme.surfaceContainerLow,
                                                shape = RoundedCornerShape(18.dp),
                                                border = BorderStroke(1.5.dp, accentColor.copy(alpha = 0.45f)),
                                                modifier = Modifier.fillMaxWidth()
                                            ) {
                                                Column(
                                                    modifier = Modifier.padding(12.dp),
                                                    verticalArrangement = Arrangement.spacedBy(10.dp)
                                                ) {
                                                    Surface(
                                                        color = accentColor,
                                                        shape = RoundedCornerShape(10.dp),
                                                        modifier = Modifier.fillMaxWidth()
                                                    ) {
                                                        Text(
                                                            text = BidiFormatter.formatMixedPersianText("🎨 نقشه گرافیکی و ساختار استاندارد این کتاب درسی"),
                                                            style = MaterialTheme.typography.labelLarge,
                                                            fontWeight = FontWeight.ExtraBold,
                                                            color = Color.White,
                                                            modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp)
                                                        )
                                                    }
                                                    Row(
                                                        modifier = Modifier.fillMaxWidth(),
                                                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                                                    ) {
                                                        val pillars = listOf(
                                                            Triple("۱. درسنامه کادربندی", "${lessons.size} فصل کامل", Color(0xFF2563EB)),
                                                            Triple("۲. نمودار و جدول", "گرافیک برداری", Color(0xFF7C3AED)),
                                                            Triple("۳. فرمول و تست", "تکنیک ۵ ثانیه‌ای", Color(0xFF059669))
                                                        )
                                                        pillars.forEach { (pTitle, pSub, pCol) ->
                                                            Surface(
                                                                color = pCol.copy(alpha = 0.12f),
                                                                shape = RoundedCornerShape(14.dp),
                                                                border = BorderStroke(1.5.dp, pCol.copy(alpha = 0.45f)),
                                                                modifier = Modifier.weight(1f)
                                                            ) {
                                                                Column(
                                                                    modifier = Modifier.padding(8.dp),
                                                                    horizontalAlignment = Alignment.CenterHorizontally,
                                                                    verticalArrangement = Arrangement.spacedBy(3.dp)
                                                                ) {
                                                                    Text(
                                                                        text = BidiFormatter.formatMixedPersianText(pTitle),
                                                                        style = MaterialTheme.typography.labelSmall,
                                                                        fontWeight = FontWeight.ExtraBold,
                                                                        color = pCol,
                                                                        textAlign = TextAlign.Center
                                                                    )
                                                                    Text(
                                                                        text = BidiFormatter.formatMixedPersianText(pSub),
                                                                        style = MaterialTheme.typography.labelSmall,
                                                                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                                                                        textAlign = TextAlign.Center
                                                                    )
                                                                }
                                                            }
                                                        }
                                                    }
                                                    Text(
                                                        text = BidiFormatter.formatMixedPersianText(
                                                            "👈 برای ورق زدن صفحات کتاب، صفحه را به چپ یا راست بکشید یا روی هر فصل کلیک کنید."
                                                        ),
                                                        style = MaterialTheme.typography.bodySmall,
                                                        fontWeight = FontWeight.Bold,
                                                        color = accentColor
                                                    )
                                                }
                                            }
                                        }

                                        item {
                                            Text(
                                                text = BidiFormatter.formatMixedPersianText("📐 سرفصل‌های رسمی وزارت آموزش و پرورش (${book.title}):"),
                                                style = MaterialTheme.typography.titleSmall,
                                                fontWeight = FontWeight.ExtraBold,
                                                color = accentColor
                                            )
                                        }

                                        itemsIndexed(book.officialChapters) { idx, chapterTitle ->
                                            Surface(
                                                color = MaterialTheme.colorScheme.surfaceContainerLow,
                                                shape = RoundedCornerShape(16.dp),
                                                border = BorderStroke(1.dp, accentColor.copy(alpha = 0.25f)),
                                                modifier = Modifier.fillMaxWidth()
                                            ) {
                                                Row(
                                                    modifier = Modifier.padding(12.dp),
                                                    verticalAlignment = Alignment.CenterVertically
                                                ) {
                                                    Surface(
                                                        color = accentColor,
                                                        shape = CircleShape,
                                                        modifier = Modifier.size(26.dp)
                                                    ) {
                                                        Box(contentAlignment = Alignment.Center) {
                                                            Text(
                                                                text = BidiFormatter.formatMixedPersianText("${idx + 1}"),
                                                                color = Color.White,
                                                                style = MaterialTheme.typography.labelSmall,
                                                                fontWeight = FontWeight.Bold
                                                            )
                                                        }
                                                    }
                                                    Spacer(modifier = Modifier.width(10.dp))
                                                    Text(
                                                        text = BidiFormatter.formatMixedPersianText(chapterTitle),
                                                        style = MaterialTheme.typography.bodyMedium,
                                                        fontWeight = FontWeight.SemiBold,
                                                        color = MaterialTheme.colorScheme.onSurface
                                                    )
                                                }
                                            }
                                        }

                                        if (lessons.isNotEmpty()) {
                                            item {
                                                Spacer(modifier = Modifier.height(4.dp))
                                                Text(
                                                    text = BidiFormatter.formatMixedPersianText("📚 صفحات درس‌به‌درس با گرافیک، جدول و فرمول (کلیک برای باز شدن صفحه):"),
                                                    style = MaterialTheme.typography.titleSmall,
                                                    fontWeight = FontWeight.ExtraBold,
                                                    color = accentColor
                                                )
                                            }

                                            itemsIndexed(lessons) { idx, lesson ->
                                                Surface(
                                                    onClick = {
                                                        scope.launch {
                                                            pagerState.animateScrollToPage(idx + 1)
                                                        }
                                                    },
                                                    color = MaterialTheme.colorScheme.surfaceContainerHigh,
                                                    shape = RoundedCornerShape(18.dp),
                                                    border = BorderStroke(1.5.dp, accentColor.copy(alpha = 0.4f)),
                                                    modifier = Modifier.fillMaxWidth()
                                                ) {
                                                    Row(
                                                        modifier = Modifier
                                                            .fillMaxWidth()
                                                            .padding(14.dp),
                                                        verticalAlignment = Alignment.CenterVertically,
                                                        horizontalArrangement = Arrangement.SpaceBetween
                                                    ) {
                                                        Column(modifier = Modifier.weight(1f)) {
                                                            Text(
                                                                text = BidiFormatter.formatMixedPersianText("صفحه ${idx + 2} • ${lesson.title}"),
                                                                style = MaterialTheme.typography.bodyMedium,
                                                                fontWeight = FontWeight.Bold,
                                                                color = MaterialTheme.colorScheme.onSurface,
                                                                maxLines = 2,
                                                                overflow = TextOverflow.Ellipsis
                                                            )
                                                            Text(
                                                                text = BidiFormatter.formatMixedPersianText(lesson.subtitle),
                                                                style = MaterialTheme.typography.labelSmall,
                                                                color = MaterialTheme.colorScheme.onSurfaceVariant,
                                                                maxLines = 2,
                                                                overflow = TextOverflow.Ellipsis
                                                            )
                                                        }
                                                        Surface(
                                                            color = accentColor,
                                                            shape = RoundedCornerShape(50)
                                                        ) {
                                                            Text(
                                                                text = BidiFormatter.formatMixedPersianText("صفحه ${idx + 2} ⬅"),
                                                                style = MaterialTheme.typography.labelSmall,
                                                                fontWeight = FontWeight.Bold,
                                                                color = Color.White,
                                                                modifier = Modifier.padding(horizontal = 10.dp, vertical = 5.dp)
                                                            )
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    }
                                } else {
                                    // صفحات درس‌ها (کادربندی‌شده + گرافیک + جدول + فرمول + تکنیک ۵ ثانیه‌ای)
                                    val lesson = lessons.getOrNull(pageIdx - 1)
                                    if (lesson != null) {
                                        val advMethod = remember(lesson.id) {
                                            AdvancedMnemonicEngine.getMethodForLesson(lesson)
                                        }
                                        LazyColumn(
                                            modifier = Modifier.fillMaxSize(),
                                            contentPadding = PaddingValues(16.dp),
                                            verticalArrangement = Arrangement.spacedBy(14.dp)
                                        ) {
                                            // ۱. بنر گرافیکی بالای صفحه کتاب
                                            item {
                                                FramedTextbookGraphicHeaderBanner(
                                                    book = book,
                                                    pageLabel = "صفحه ${pageIdx + 1} از $totalInteractivePages • درس ${pageIdx}: ${lesson.title}",
                                                    accentColor = accentColor
                                                )
                                            }

                                            // ۲. کادر هدف و اهمیت درس در کتاب درسی
                                            item {
                                                Surface(
                                                    color = accentColor.copy(alpha = 0.08f),
                                                    shape = RoundedCornerShape(18.dp),
                                                    border = BorderStroke(1.5.dp, accentColor.copy(alpha = 0.4f)),
                                                    modifier = Modifier.fillMaxWidth()
                                                ) {
                                                    Column(
                                                        modifier = Modifier.padding(14.dp),
                                                        verticalArrangement = Arrangement.spacedBy(6.dp)
                                                    ) {
                                                        Row(
                                                            modifier = Modifier.fillMaxWidth(),
                                                            horizontalArrangement = Arrangement.SpaceBetween,
                                                            verticalAlignment = Alignment.CenterVertically
                                                        ) {
                                                            Text(
                                                                text = BidiFormatter.formatMixedPersianText("🎯 چکیده و هدف این فصل از کتاب:"),
                                                                style = MaterialTheme.typography.titleSmall,
                                                                fontWeight = FontWeight.ExtraBold,
                                                                color = accentColor,
                                                                maxLines = 1,
                                                                overflow = TextOverflow.Ellipsis,
                                                                modifier = Modifier.weight(1f)
                                                            )
                                                            OutlinedButton(
                                                                onClick = {
                                                                    onDismiss()
                                                                    onSelectLessonFromToc(lesson.id)
                                                                },
                                                                shape = RoundedCornerShape(50),
                                                                contentPadding = PaddingValues(horizontal = 10.dp, vertical = 4.dp)
                                                            ) {
                                                                Text(
                                                                    text = "ورود به کارگاه تست درس",
                                                                    style = MaterialTheme.typography.labelSmall,
                                                                    fontWeight = FontWeight.Bold,
                                                                    color = accentColor,
                                                                    maxLines = 1,
                                                                    softWrap = false
                                                                )
                                                            }
                                                        }
                                                        BiDiText(
                                                            text = lesson.whyStudentsStruggle,
                                                            style = MaterialTheme.typography.bodyMedium,
                                                            color = MaterialTheme.colorScheme.onSurface
                                                        )
                                                    }
                                                }
                                            }

                                            // ۳. شکل و اینفوگرافیک تعاملی اختصاصی این درس در داخل صفحه PDF
                                            item {
                                                LessonInteractiveVisualCard(
                                                    lesson = lesson,
                                                    subjectColor = accentColor
                                                )
                                            }

                                            // ۴. گام‌های کادربندی‌شده درس با نمودار و فرمول استاندارد
                                            itemsIndexed(lesson.steps) { _, step ->
                                                Surface(
                                                    color = MaterialTheme.colorScheme.surfaceContainerLow,
                                                    shape = RoundedCornerShape(20.dp),
                                                    border = BorderStroke(1.5.dp, accentColor.copy(alpha = 0.35f)),
                                                    modifier = Modifier.fillMaxWidth()
                                                ) {
                                                    Column(
                                                        modifier = Modifier.padding(14.dp),
                                                        verticalArrangement = Arrangement.spacedBy(10.dp)
                                                    ) {
                                                        // سرخط کادر گام
                                                        Row(
                                                            verticalAlignment = Alignment.CenterVertically,
                                                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                                                        ) {
                                                            Surface(
                                                                color = accentColor,
                                                                shape = RoundedCornerShape(10.dp)
                                                            ) {
                                                                Text(
                                                                    text = BidiFormatter.formatMixedPersianText("بخش ${step.stepNumber}"),
                                                                    style = MaterialTheme.typography.labelMedium,
                                                                    fontWeight = FontWeight.ExtraBold,
                                                                    color = Color.White,
                                                                    modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp)
                                                                )
                                                            }
                                                            Text(
                                                                text = BidiFormatter.formatMixedPersianText(step.title),
                                                                style = MaterialTheme.typography.titleSmall,
                                                                fontWeight = FontWeight.ExtraBold,
                                                                color = MaterialTheme.colorScheme.onSurface
                                                            )
                                                        }

                                                        HorizontalDivider(color = accentColor.copy(alpha = 0.2f))

                                                        BiDiText(
                                                            text = step.simpleExplanation,
                                                            style = MaterialTheme.typography.bodyMedium,
                                                            color = MaterialTheme.colorScheme.onSurface
                                                        )

                                                        if (step.visualDiagramText.isNotBlank()) {
                                                            SmartDiagramAndFormulaBlock(
                                                                rawText = step.visualDiagramText,
                                                                accentColor = accentColor
                                                            )
                                                        }

                                                        if (step.memoryTrick.isNotBlank()) {
                                                            Surface(
                                                                color = MaterialTheme.colorScheme.secondaryContainer.copy(alpha = 0.55f),
                                                                shape = RoundedCornerShape(14.dp),
                                                                border = BorderStroke(1.dp, MaterialTheme.colorScheme.secondary.copy(alpha = 0.4f)),
                                                                modifier = Modifier.fillMaxWidth()
                                                            ) {
                                                                BiDiText(
                                                                    text = "💡 رمز طلایی کتاب: ${step.memoryTrick}",
                                                                    style = MaterialTheme.typography.bodySmall,
                                                                    fontWeight = FontWeight.Bold,
                                                                    color = MaterialTheme.colorScheme.onSecondaryContainer,
                                                                    modifier = Modifier.padding(10.dp)
                                                                )
                                                            }
                                                        }
                                                    }
                                                }
                                            }

                                            // ۵. کادر متد ابداعی و میان‌بر ۵ ثانیه‌ای
                                            item {
                                                Surface(
                                                    color = MaterialTheme.colorScheme.tertiaryContainer.copy(alpha = 0.45f),
                                                    shape = RoundedCornerShape(20.dp),
                                                    border = BorderStroke(2.dp, MaterialTheme.colorScheme.tertiary.copy(alpha = 0.6f)),
                                                    modifier = Modifier.fillMaxWidth()
                                                ) {
                                                    Column(
                                                        modifier = Modifier.padding(14.dp),
                                                        verticalArrangement = Arrangement.spacedBy(8.dp)
                                                    ) {
                                                        Text(
                                                            text = BidiFormatter.formatMixedPersianText("⚡ ${advMethod.methodTitle}"),
                                                            style = MaterialTheme.typography.titleSmall,
                                                            fontWeight = FontWeight.ExtraBold,
                                                            color = MaterialTheme.colorScheme.tertiary
                                                        )
                                                        BiDiText(
                                                            text = "🔑 کد رمز حافظه:\n${advMethod.mnemonicCode}",
                                                            style = MaterialTheme.typography.bodySmall,
                                                            fontWeight = FontWeight.Bold,
                                                            color = MaterialTheme.colorScheme.onSurface
                                                        )
                                                        BiDiText(
                                                            text = "🚀 الگوریتم ۵ ثانیه‌ای حل تست:\n${advMethod.fiveSecondAlgorithm}",
                                                            style = MaterialTheme.typography.bodySmall,
                                                            color = MaterialTheme.colorScheme.onSurface
                                                        )
                                                    }
                                                }
                                            }

                                            // ۶. کادر فرمول‌ها و قواعد طلایی
                                            if (lesson.goldenFormulas.isNotEmpty()) {
                                                item {
                                                    Surface(
                                                        color = accentColor.copy(alpha = 0.1f),
                                                        shape = RoundedCornerShape(20.dp),
                                                        border = BorderStroke(1.5.dp, accentColor),
                                                        modifier = Modifier.fillMaxWidth()
                                                    ) {
                                                        Column(
                                                            modifier = Modifier.padding(14.dp),
                                                            verticalArrangement = Arrangement.spacedBy(8.dp)
                                                        ) {
                                                            Text(
                                                                text = "🏆 کادر جمع‌بندی فرمول‌ها و قواعد طلایی فصل:",
                                                                style = MaterialTheme.typography.titleSmall,
                                                                fontWeight = FontWeight.ExtraBold,
                                                                color = accentColor
                                                            )
                                                            lesson.goldenFormulas.forEachIndexed { fIdx, gf ->
                                                                SmartDiagramAndFormulaBlock(
                                                                    rawText = "${fIdx + 1}) $gf",
                                                                    accentColor = accentColor
                                                                )
                                                            }
                                                        }
                                                    }
                                                }
                                            }

                                            // ۷. کادر نمونه سوالات حل‌شده امتحانی و کنکوری
                                            if (lesson.workedExamples.isNotEmpty()) {
                                                item {
                                                    Text(
                                                        text = "✏️ کادر نمونه سوالات امتحانی و کنکوری حل‌شده:",
                                                        style = MaterialTheme.typography.titleSmall,
                                                        fontWeight = FontWeight.ExtraBold,
                                                        color = accentColor
                                                    )
                                                }
                                                itemsIndexed(lesson.workedExamples) { exIdx, ex ->
                                                    Surface(
                                                        color = MaterialTheme.colorScheme.surfaceContainerHigh,
                                                        shape = RoundedCornerShape(18.dp),
                                                        border = BorderStroke(1.dp, accentColor.copy(alpha = 0.4f)),
                                                        modifier = Modifier.fillMaxWidth()
                                                    ) {
                                                        Column(
                                                            modifier = Modifier.padding(14.dp),
                                                            verticalArrangement = Arrangement.spacedBy(8.dp)
                                                        ) {
                                                            BiDiText(
                                                                text = "سوال ${exIdx + 1}: ${ex.question}",
                                                                style = MaterialTheme.typography.bodyMedium,
                                                                fontWeight = FontWeight.ExtraBold,
                                                                color = MaterialTheme.colorScheme.onSurface
                                                            )
                                                            HorizontalDivider(color = accentColor.copy(alpha = 0.2f))
                                                            BiDiText(
                                                                text = "✅ پاسخ گام‌به‌گام:\n${ex.stepByStepSolution}",
                                                                style = MaterialTheme.typography.bodyMedium,
                                                                color = MaterialTheme.colorScheme.onSurface
                                                            )
                                                            Surface(
                                                                color = MaterialTheme.colorScheme.errorContainer.copy(alpha = 0.45f),
                                                                shape = RoundedCornerShape(12.dp),
                                                                modifier = Modifier.fillMaxWidth()
                                                            ) {
                                                                BiDiText(
                                                                    text = "⚠️ هشدار تله طراح: ${ex.commonTrapAlert}",
                                                                    style = MaterialTheme.typography.bodySmall,
                                                                    fontWeight = FontWeight.Bold,
                                                                    color = MaterialTheme.colorScheme.onErrorContainer,
                                                                    modifier = Modifier.padding(10.dp)
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
                    }

                    // ==================== ۳. نوار پایین Pixel UI برای ورق زدن صفحات و اسکرول افقی صفحات ====================
                    Surface(
                        tonalElevation = 8.dp,
                        color = MaterialTheme.colorScheme.surfaceContainerHigh,
                        shape = RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 12.dp, vertical = 8.dp),
                            verticalArrangement = Arrangement.spacedBy(6.dp)
                        ) {
                            // نوار اسکرول افقی چپ و راست برای پرش سریع بین صفحات کتاب
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .horizontalScroll(rememberScrollState()),
                                horizontalArrangement = Arrangement.spacedBy(6.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                for (idx in 0 until totalInteractivePages) {
                                    val isCurrent = pagerState.currentPage == idx
                                    Surface(
                                        onClick = {
                                            scope.launch { pagerState.animateScrollToPage(idx) }
                                        },
                                        shape = RoundedCornerShape(50),
                                        color = if (isCurrent) accentColor else MaterialTheme.colorScheme.surface,
                                        border = BorderStroke(1.dp, accentColor.copy(alpha = 0.4f))
                                    ) {
                                        Text(
                                            text = if (idx == 0) {
                                                "📑 جلد و فهرست"
                                            } else {
                                                BidiFormatter.formatMixedPersianText("صفحه ${idx + 1}")
                                            },
                                            style = MaterialTheme.typography.labelSmall,
                                            fontWeight = if (isCurrent) FontWeight.ExtraBold else FontWeight.Medium,
                                            color = if (isCurrent) Color.White else MaterialTheme.colorScheme.onSurface,
                                            modifier = Modifier.padding(horizontal = 12.dp, vertical = 5.dp)
                                        )
                                    }
                                }
                            }

                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Button(
                                    onClick = {
                                        if (pagerState.currentPage > 0) {
                                            scope.launch {
                                                pagerState.animateScrollToPage(pagerState.currentPage - 1)
                                            }
                                        }
                                    },
                                    enabled = pagerState.currentPage > 0,
                                    colors = ButtonDefaults.buttonColors(containerColor = accentColor),
                                    shape = RoundedCornerShape(50),
                                    contentPadding = PaddingValues(horizontal = 14.dp, vertical = 6.dp)
                                ) {
                                    Icon(
                                        imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                                        contentDescription = "صفحه قبل",
                                        modifier = Modifier.size(16.dp)
                                    )
                                    Spacer(modifier = Modifier.width(4.dp))
                                    Text("صفحه قبل", style = MaterialTheme.typography.labelSmall, fontWeight = FontWeight.Bold)
                                }

                                Text(
                                    text = BidiFormatter.formatMixedPersianText(
                                        "صفحه ${pagerState.currentPage + 1} از $totalInteractivePages (اسکرول چپ و راست)"
                                    ),
                                    style = MaterialTheme.typography.labelMedium,
                                    fontWeight = FontWeight.ExtraBold,
                                    color = MaterialTheme.colorScheme.onSurface
                                )

                                Button(
                                    onClick = {
                                        if (pagerState.currentPage < totalInteractivePages - 1) {
                                            scope.launch {
                                                pagerState.animateScrollToPage(pagerState.currentPage + 1)
                                            }
                                        }
                                    },
                                    enabled = pagerState.currentPage < totalInteractivePages - 1,
                                    colors = ButtonDefaults.buttonColors(containerColor = accentColor),
                                    shape = RoundedCornerShape(50),
                                    contentPadding = PaddingValues(horizontal = 14.dp, vertical = 6.dp)
                                ) {
                                    Text("صفحه بعد", style = MaterialTheme.typography.labelSmall, fontWeight = FontWeight.Bold)
                                    Spacer(modifier = Modifier.width(4.dp))
                                    Icon(
                                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                                        contentDescription = "صفحه بعد",
                                        modifier = Modifier.size(16.dp)
                                    )
                                }
                            }
                        }
                    }
                } else {
                    // نمای چاپی A4 (PdfRenderer با فونت وزیرمتن و کادر دو لایه)
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxWidth()
                            .background(MaterialTheme.colorScheme.surfaceContainerLowest),
                        contentAlignment = Alignment.Center
                    ) {
                        if (isGeneratingPdf) {
                            Column(
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.spacedBy(12.dp)
                            ) {
                                CircularProgressIndicator(color = accentColor)
                                Text(
                                    text = BidiFormatter.formatMixedPersianText("در حال صفحه‌آرایی و کادربندی PDF با فونت وزیرمتن..."),
                                    style = MaterialTheme.typography.bodyMedium,
                                    fontWeight = FontWeight.Bold,
                                    color = MaterialTheme.colorScheme.onSurface
                                )
                            }
                        } else {
                            val bmp = renderedBitmap
                            if (bmp != null) {
                                val transformState = rememberTransformableState { zoomChange, _, _ ->
                                    zoomScale = (zoomScale * zoomChange).coerceIn(1f, 2.5f)
                                }
                                Column(
                                    modifier = Modifier
                                        .fillMaxSize()
                                        .verticalScroll(rememberScrollState())
                                        .horizontalScroll(rememberScrollState())
                                        .padding(12.dp),
                                    horizontalAlignment = Alignment.CenterHorizontally
                                ) {
                                    Card(
                                        shape = RoundedCornerShape(16.dp),
                                        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
                                        colors = CardDefaults.cardColors(containerColor = Color.White),
                                        border = BorderStroke(2.dp, accentColor)
                                    ) {
                                        Image(
                                            bitmap = bmp.asImageBitmap(),
                                            contentDescription = "صفحه چاپی ${currentPdfBitmapPage + 1} کتاب ${book.title}",
                                            contentScale = ContentScale.FillWidth,
                                            modifier = Modifier
                                                .width((360 * zoomScale).dp)
                                                .transformable(state = transformState)
                                        )
                                    }
                                }
                            }
                        }
                    }

                    Surface(
                        tonalElevation = 6.dp,
                        color = MaterialTheme.colorScheme.surfaceContainerHigh,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 12.dp, vertical = 8.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Button(
                                onClick = {
                                    if (currentPdfBitmapPage > 0) currentPdfBitmapPage--
                                },
                                enabled = currentPdfBitmapPage > 0,
                                colors = ButtonDefaults.buttonColors(containerColor = accentColor),
                                shape = RoundedCornerShape(50),
                                contentPadding = PaddingValues(horizontal = 12.dp, vertical = 6.dp)
                            ) {
                                Icon(
                                    imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                                    contentDescription = "صفحه قبل",
                                    modifier = Modifier.size(16.dp)
                                )
                                Spacer(modifier = Modifier.width(4.dp))
                                Text("صفحه قبل", style = MaterialTheme.typography.labelSmall)
                            }

                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(4.dp)
                            ) {
                                IconButton(
                                    onClick = { zoomScale = (zoomScale - 0.25f).coerceAtLeast(1f) },
                                    modifier = Modifier.size(32.dp)
                                ) {
                                    Icon(Icons.Default.ZoomOut, contentDescription = "کوچک‌نمایی")
                                }
                                Text(
                                    text = BidiFormatter.formatMixedPersianText("برگه چاپی ${currentPdfBitmapPage + 1} از ${pdfPageCount.coerceAtLeast(1)}"),
                                    style = MaterialTheme.typography.labelLarge,
                                    fontWeight = FontWeight.Bold,
                                    color = MaterialTheme.colorScheme.onSurface
                                )
                                IconButton(
                                    onClick = { zoomScale = (zoomScale + 0.25f).coerceAtMost(2.25f) },
                                    modifier = Modifier.size(32.dp)
                                ) {
                                    Icon(Icons.Default.ZoomIn, contentDescription = "بزرگ‌نمایی")
                                }
                            }

                            Button(
                                onClick = {
                                    if (currentPdfBitmapPage < pdfPageCount - 1) currentPdfBitmapPage++
                                },
                                enabled = currentPdfBitmapPage < pdfPageCount - 1,
                                colors = ButtonDefaults.buttonColors(containerColor = accentColor),
                                shape = RoundedCornerShape(50),
                                contentPadding = PaddingValues(horizontal = 12.dp, vertical = 6.dp)
                            ) {
                                Text("صفحه بعد", style = MaterialTheme.typography.labelSmall)
                                Spacer(modifier = Modifier.width(4.dp))
                                Icon(
                                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                                    contentDescription = "صفحه بعد",
                                    modifier = Modifier.size(16.dp)
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

/**
 * بنر گرافیکی برداری (Canvas) بالای هر صفحه از کتاب درسی در داخل نمایشگر PDF
 */
@Composable
private fun FramedTextbookGraphicHeaderBanner(
    book: OfficialTextbookPdf,
    pageLabel: String,
    accentColor: Color
) {
    Surface(
        shape = RoundedCornerShape(20.dp),
        color = accentColor.copy(alpha = 0.12f),
        border = BorderStroke(1.5.dp, accentColor.copy(alpha = 0.45f)),
        modifier = Modifier.fillMaxWidth()
    ) {
        Box(modifier = Modifier.fillMaxWidth()) {
            // پس‌زمینه گرافیکی با دوایر و خطوط هندسی کتاب درسی
            ComposeCanvas(
                modifier = Modifier
                    .matchParentSize()
                    .clip(RoundedCornerShape(20.dp))
            ) {
                drawCircle(
                    color = accentColor.copy(alpha = 0.12f),
                    radius = size.minDimension * 0.65f,
                    center = Offset(size.width * 0.12f, size.height * 0.25f)
                )
                drawCircle(
                    color = accentColor.copy(alpha = 0.08f),
                    radius = size.minDimension * 0.45f,
                    center = Offset(size.width * 0.88f, size.height * 0.8f)
                )
                drawRoundRect(
                    color = accentColor.copy(alpha = 0.22f),
                    topLeft = Offset(8.dp.toPx(), 8.dp.toPx()),
                    size = Size(size.width - 16.dp.toPx(), size.height - 16.dp.toPx()),
                    cornerRadius = CornerRadius(14.dp.toPx(), 14.dp.toPx()),
                    style = Stroke(width = 1.dp.toPx())
                )
            }

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Surface(
                        color = accentColor,
                        shape = RoundedCornerShape(50)
                    ) {
                        Text(
                            text = BidiFormatter.formatMixedPersianText(book.gradeBadge),
                            style = MaterialTheme.typography.labelSmall,
                            fontWeight = FontWeight.ExtraBold,
                            color = Color.White,
                            modifier = Modifier.padding(horizontal = 10.dp, vertical = 3.dp)
                        )
                    }
                    Surface(
                        color = MaterialTheme.colorScheme.surface,
                        shape = RoundedCornerShape(50),
                        border = BorderStroke(1.dp, accentColor.copy(alpha = 0.4f))
                    ) {
                        Text(
                            text = BidiFormatter.formatMixedPersianText("کد کتاب: ${book.ministryCode}"),
                            style = MaterialTheme.typography.labelSmall,
                            fontWeight = FontWeight.Bold,
                            color = accentColor,
                            modifier = Modifier.padding(horizontal = 10.dp, vertical = 3.dp)
                        )
                    }
                }

                Text(
                    text = BidiFormatter.formatMixedPersianText(book.title),
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface
                )

                Text(
                    text = BidiFormatter.formatMixedPersianText(pageLabel),
                    style = MaterialTheme.typography.labelMedium,
                    fontWeight = FontWeight.SemiBold,
                    color = accentColor
                )
            }
        }
    }
}

/**
 * کتابخانه مدرن و فهرست جامع کتاب‌های درسی و منابع رسمی کنکور انسانی و فرهنگیان
 * با سلسله‌مراتب تایپوگرافی استاندارد، جست‌وجو، فیلتر پایه و بدون بریدگی عنوان‌های طولانی.
 */
@Composable
fun LibraryAndResourcesCatalogView(
    allLessons: List<ZeroLesson>,
    onOpenLesson: (String) -> Unit = {},
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val themeController = com.example.ui.theme.LocalAppThemeController.current
    val allBooks = remember { TextbookPdfCatalog.allBooks }
    var searchQuery by remember { mutableStateOf("") }
    var selectedGradeFilter by remember { mutableIntStateOf(0) } // 0=All, 2=10th, 3=11th, 4=12th, 5=Farhangian, 1=Base
    var expandedBookId by remember { mutableStateOf<String?>(null) }
    var activeBookForViewer by remember { mutableStateOf<OfficialTextbookPdf?>(null) }

    activeBookForViewer?.let { book ->
        val bookLessons = remember(book.bookId, allLessons) {
            allLessons.filter {
                it.subjectId == book.subjectId &&
                    (it.subcategoryFolder().first == book.gradeLevel || book.subjectId == "farhangian")
            }.ifEmpty {
                allLessons.filter { it.subjectId == book.subjectId }
            }
        }
        InAppTextbookPdfViewerDialog(
            book = book,
            lessons = bookLessons,
            accentColor = MaterialTheme.colorScheme.primary,
            onDismiss = { activeBookForViewer = null },
            onSelectLessonFromToc = { lessonId ->
                activeBookForViewer = null
                onOpenLesson(lessonId)
            }
        )
    }

    val filteredBooks = remember(allBooks, searchQuery, selectedGradeFilter) {
        allBooks.filter { book ->
            val matchesFilter = when (selectedGradeFilter) {
                1 -> book.gradeLevel == 1
                2 -> book.gradeLevel == 2 && book.subjectId != "farhangian"
                3 -> book.gradeLevel == 3 && book.subjectId != "farhangian"
                4 -> book.gradeLevel == 4 && book.subjectId != "farhangian"
                5 -> book.subjectId == "farhangian"
                else -> true
            }
            val q = searchQuery.trim()
            val matchesSearch = q.isEmpty() ||
                book.title.contains(q, ignoreCase = true) ||
                book.ministryCode.contains(q, ignoreCase = true) ||
                book.editionYear.contains(q, ignoreCase = true) ||
                book.officialChapters.any { it.contains(q, ignoreCase = true) }
            matchesFilter && matchesSearch
        }
    }

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .testTag("library_resources_catalog_view"),
        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 12.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        item(key = "library_header") {
            Surface(
                shape = RoundedCornerShape(16.dp),
                color = MaterialTheme.colorScheme.surface,
                border = BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier.padding(14.dp),
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                text = themeController.tr(
                                    "کتابخانه و منابع رسمی کنکور و فرهنگیان",
                                    "Official Textbooks & Resources Library"
                                ),
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.onSurface
                            )
                            Text(
                                text = BidiFormatter.applyPreferredDigits(
                                    themeController.tr(
                                        "${filteredBooks.size} کتاب و منبع رسمی با سرفصل مصوب، کد چاپ و مطالعه آفلاین",
                                        "${filteredBooks.size} official textbooks with ministry code & offline reader"
                                    )
                                ),
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                        Surface(
                            color = MaterialTheme.colorScheme.primaryContainer,
                            shape = RoundedCornerShape(10.dp)
                        ) {
                            Text(
                                text = BidiFormatter.applyPreferredDigits("${allBooks.size} جلد"),
                                style = MaterialTheme.typography.labelMedium,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.onPrimaryContainer,
                                modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp)
                            )
                        }
                    }

                    androidx.compose.material3.OutlinedTextField(
                        value = searchQuery,
                        onValueChange = { searchQuery = it },
                        placeholder = {
                            Text(
                                text = themeController.tr(
                                    "جست‌وجوی نام کتاب، کد چاپ یا سرفصل...",
                                    "Search book title, ministry code, or chapter..."
                                ),
                                style = MaterialTheme.typography.bodySmall
                            )
                        },
                        singleLine = true,
                        shape = RoundedCornerShape(12.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .testTag("library_search_input")
                    )

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .horizontalScroll(rememberScrollState()),
                        horizontalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        val filters = listOf(
                            0 to themeController.tr("همه منابع", "All"),
                            2 to themeController.tr("پایه دهم", "Grade 10"),
                            3 to themeController.tr("پایه یازدهم", "Grade 11"),
                            4 to themeController.tr("پایه دوازدهم", "Grade 12"),
                            5 to themeController.tr("اختصاصی فرهنگیان", "Farhangian"),
                            1 to themeController.tr("پیش‌نیاز پایه", "Prerequisites")
                        )
                        filters.forEach { (code, label) ->
                            val isSelected = selectedGradeFilter == code
                            Surface(
                                onClick = { selectedGradeFilter = code },
                                shape = RoundedCornerShape(10.dp),
                                color = if (isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.surfaceContainerLow,
                                border = BorderStroke(
                                    1.dp,
                                    if (isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.outlineVariant
                                )
                            ) {
                                Text(
                                    text = label,
                                    style = MaterialTheme.typography.labelMedium,
                                    fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
                                    color = if (isSelected) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.onSurface,
                                    modifier = Modifier.padding(horizontal = 12.dp, vertical = 7.dp)
                                )
                            }
                        }
                    }
                }
            }
        }

        if (filteredBooks.isEmpty()) {
            item(key = "library_empty_state") {
                Surface(
                    shape = RoundedCornerShape(14.dp),
                    color = MaterialTheme.colorScheme.surface,
                    border = BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(
                        modifier = Modifier.padding(24.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        Text(
                            text = themeController.tr(
                                "کتابی با این عبارت یافت نشد.",
                                "No matching textbook found."
                            ),
                            style = MaterialTheme.typography.titleSmall,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                        Text(
                            text = themeController.tr(
                                "فیلتر پایه را تغییر دهید یا عبارت کوتاه‌تری جست‌وجو کنید.",
                                "Try changing the grade filter or search term."
                            ),
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
            }
        } else {
            itemsIndexed(filteredBooks, key = { _, b -> b.bookId }) { _, book ->
                val isExpanded = expandedBookId == book.bookId
                Surface(
                    shape = RoundedCornerShape(14.dp),
                    color = MaterialTheme.colorScheme.surface,
                    border = BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant),
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag("library_book_card_${book.bookId}")
                ) {
                    Column(
                        modifier = Modifier.padding(14.dp),
                        verticalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        Row(
                            verticalAlignment = Alignment.Top,
                            horizontalArrangement = Arrangement.spacedBy(10.dp),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Surface(
                                color = MaterialTheme.colorScheme.primaryContainer,
                                shape = RoundedCornerShape(10.dp),
                                modifier = Modifier.size(42.dp)
                            ) {
                                Box(contentAlignment = Alignment.Center) {
                                    Icon(
                                        imageVector = Icons.Default.MenuBook,
                                        contentDescription = null,
                                        tint = MaterialTheme.colorScheme.onPrimaryContainer,
                                        modifier = Modifier.size(22.dp)
                                    )
                                }
                            }
                            Column(
                                modifier = Modifier.weight(1f),
                                verticalArrangement = Arrangement.spacedBy(4.dp)
                            ) {
                                Text(
                                    text = BidiFormatter.applyPreferredDigits(book.title),
                                    style = MaterialTheme.typography.titleSmall,
                                    fontWeight = FontWeight.Bold,
                                    color = MaterialTheme.colorScheme.onSurface,
                                    maxLines = 2,
                                    overflow = TextOverflow.Ellipsis
                                )
                                Row(
                                    horizontalArrangement = Arrangement.spacedBy(6.dp),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Surface(
                                        color = MaterialTheme.colorScheme.secondaryContainer,
                                        shape = RoundedCornerShape(6.dp)
                                    ) {
                                        Text(
                                            text = BidiFormatter.applyPreferredDigits(book.gradeBadge),
                                            style = MaterialTheme.typography.labelSmall,
                                            fontWeight = FontWeight.SemiBold,
                                            color = MaterialTheme.colorScheme.onSecondaryContainer,
                                            modifier = Modifier.padding(horizontal = 7.dp, vertical = 2.dp)
                                        )
                                    }
                                    Text(
                                        text = BidiFormatter.applyPreferredDigits("${book.editionYear} • کد: ${book.ministryCode}"),
                                        style = MaterialTheme.typography.labelSmall,
                                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                                        maxLines = 1,
                                        overflow = TextOverflow.Ellipsis
                                    )
                                }
                            }
                        }

                        if (isExpanded) {
                            Surface(
                                color = MaterialTheme.colorScheme.surfaceContainerLow,
                                shape = RoundedCornerShape(10.dp),
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Column(
                                    modifier = Modifier.padding(10.dp),
                                    verticalArrangement = Arrangement.spacedBy(4.dp)
                                ) {
                                    Text(
                                        text = BidiFormatter.applyPreferredDigits("فهرست فصول رسمی (${book.officialChapters.size} بخش):"),
                                        style = MaterialTheme.typography.labelMedium,
                                        fontWeight = FontWeight.Bold,
                                        color = MaterialTheme.colorScheme.primary
                                    )
                                    book.officialChapters.forEach { ch ->
                                        Text(
                                            text = BidiFormatter.applyPreferredDigits("• $ch"),
                                            style = MaterialTheme.typography.bodySmall,
                                            color = MaterialTheme.colorScheme.onSurface
                                        )
                                    }
                                }
                            }
                        }

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(8.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Button(
                                onClick = { activeBookForViewer = book },
                                shape = RoundedCornerShape(10.dp),
                                contentPadding = PaddingValues(horizontal = 12.dp, vertical = 8.dp),
                                modifier = Modifier.weight(1f)
                            ) {
                                Icon(
                                    imageVector = Icons.Default.MenuBook,
                                    contentDescription = null,
                                    modifier = Modifier.size(16.dp)
                                )
                                Spacer(modifier = Modifier.width(6.dp))
                                Text(
                                    text = themeController.tr("مطالعه در برنامه", "Read in App"),
                                    style = MaterialTheme.typography.labelMedium,
                                    fontWeight = FontWeight.Bold,
                                    maxLines = 1
                                )
                            }

                            OutlinedButton(
                                onClick = {
                                    NativeTextbookPdfGenerator.startOfficialMinistryPdfDownload(context, book)
                                },
                                shape = RoundedCornerShape(10.dp),
                                contentPadding = PaddingValues(horizontal = 10.dp, vertical = 8.dp)
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Download,
                                    contentDescription = null,
                                    modifier = Modifier.size(16.dp)
                                )
                                Spacer(modifier = Modifier.width(4.dp))
                                Text(
                                    text = themeController.tr("دانلود PDF", "PDF"),
                                    style = MaterialTheme.typography.labelMedium,
                                    maxLines = 1
                                )
                            }

                            OutlinedButton(
                                onClick = {
                                    expandedBookId = if (isExpanded) null else book.bookId
                                },
                                shape = RoundedCornerShape(10.dp),
                                contentPadding = PaddingValues(horizontal = 10.dp, vertical = 8.dp)
                            ) {
                                Text(
                                    text = if (isExpanded) {
                                        themeController.tr("بستن فصول", "Hide")
                                    } else {
                                        BidiFormatter.applyPreferredDigits(
                                            themeController.tr("${book.officialChapters.size} فصل", "${book.officialChapters.size} Ch")
                                        )
                                    },
                                    style = MaterialTheme.typography.labelSmall,
                                    maxLines = 1
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

