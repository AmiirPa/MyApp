package com.example.ui.design

/**
 * ═══════════════════════════════════════════════════════════════════════════
 *  لایه‌ی طراحی — مینیمال، تمیز، راست‌به‌چپ
 *
 *  این فایل یک «سیستم» است، نه یک صفحه. هیچ چیزی را در برنامه‌ی فعلی عوض
 *  نمی‌کند و به ناوبری وصل نیست؛ فقط ابزار می‌دهد.
 *
 *  ── چطور استفاده کنم؟ ─────────────────────────────────────────────────
 *
 *  یک بار، بالای محتوای هر صفحه، توکن‌ها را تأمین کن:
 *
 *      AppDesignScope {
 *          LessonsScreen(...)
 *      }
 *
 *  بعد از کامپوننت‌ها استفاده کن:
 *
 *      SectionLabel("درس‌ها", trailing = "۲۵۴۸")
 *      LessonRow(index = 1, title = lesson.title, meta = "۲۵ دقیقه · ۴ گام",
 *                progress = 0.5f, done = false) { open(lesson) }
 *      ContentCard { Text("…") }
 *      FormulaBlock(listOf("a² + b² = c²"))
 *      QuizOption(index = 1, text = "گزینه", state = OptionState.Correct) {}
 *      EmptyState("درسی ثبت نشده")
 *
 *  ── قواعد سبک ─────────────────────────────────────────────────────────
 *  • مینیمال: بدون سایه‌ی سنگین، بدون گرادیان تزئینی، بدون ایموجی.
 *  • جدایی با خط مویی (hairline) و تغییر روشنایی ۲ تا ۴ درصد.
 *  • فقط سه شعاع (۱۲/۱۶/۲۴)، پنج فاصله (۴/۸/۱۲/۱۶/۲۴)، سه اندازه آیکن.
 *  • همه‌ی متن‌ها maxLines و overflow دارند تا در عرض کم نشکنند.
 *  • فرمول‌ها با LocalLayoutDirection = Ltr جدا می‌شوند تا اعداد برنگردند.
 *  • رنگ درس‌ها در هر دو تم کنتراست ۴٫۵:۱ یا بیشتر دارند.
 *
 *  ── چه چیزی را عوض نمی‌کند ────────────────────────────────────────────
 *  تم فعلی برنامه (MyApplicationTheme) دست‌نخورده می‌ماند. این لایه فقط
 *  روی MaterialTheme سوار می‌شود و از آن رنگ می‌گیرد.
 * ═══════════════════════════════════════════════════════════════════════════
 */

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.luminance
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDirection
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlin.math.roundToInt

// ───────────────────────────────────────────────────────────────────────────
// ۱) فاصله، شعاع، حرکت
// ───────────────────────────────────────────────────────────────────────────

object Space {
    val xs: Dp = 4.dp
    val s: Dp = 8.dp
    val m: Dp = 12.dp
    val l: Dp = 16.dp
    val xl: Dp = 24.dp
    val xxl: Dp = 32.dp
}

object Radius {
    val s: Dp = 12.dp
    val m: Dp = 16.dp
    val l: Dp = 24.dp
}

object Motion {
    const val QUICK = 160
    const val NORMAL = 260
    const val SLOW = 420
}

// ───────────────────────────────────────────────────────────────────────────
// ۲) رنگ‌های درس‌ها — دو نسخه، هر دو با کنتراست کافی
// ───────────────────────────────────────────────────────────────────────────

data class SubjectTint(val light: Color, val dark: Color) {
    fun resolve(isDark: Boolean): Color = if (isDark) dark else light
}

object SubjectPalette {
    val math = SubjectTint(Color(0xFF1F5FA8), Color(0xFF7FB3E8))
    val arabic = SubjectTint(Color(0xFF17706A), Color(0xFF6FD3C8))
    val farsi = SubjectTint(Color(0xFFA24B1E), Color(0xFFF0A97C))
    val fonoon = SubjectTint(Color(0xFF7A4A16), Color(0xFFE0AE72))
    val dini = SubjectTint(Color(0xFF2F6B45), Color(0xFF86D3A5))
    val english = SubjectTint(Color(0xFF2E5AA8), Color(0xFF8FB6F0))
    val sociology = SubjectTint(Color(0xFF3B6B5A), Color(0xFF93CBB8))
    val psychology = SubjectTint(Color(0xFF8A3B62), Color(0xFFE79FC0))
    val history = SubjectTint(Color(0xFF6B4A2A), Color(0xFFD8B58C))
    val geography = SubjectTint(Color(0xFF1F6B7A), Color(0xFF7FD0DC))
    val philosophy = SubjectTint(Color(0xFF5B3F7A), Color(0xFFC0A6E0))
    val logic = SubjectTint(Color(0xFF6B5A1F), Color(0xFFDCC97F))
    val economics = SubjectTint(Color(0xFF8A5A1F), Color(0xFFE8BC7C))
    val farhangian = SubjectTint(Color(0xFF3A4A7A), Color(0xFFA9B8E8))
    val health = SubjectTint(Color(0xFF2F6B6B), Color(0xFF8FD8D8))
    val negaresh = SubjectTint(Color(0xFF7A4A4A), Color(0xFFE0A8A8))
    val honar = SubjectTint(Color(0xFF6B5A4A), Color(0xFFD0BFA8))
    val fallback = SubjectTint(Color(0xFF4A5568), Color(0xFFA9B6C6))

    fun of(subjectId: String): SubjectTint = when (subjectId) {
        "math", "riyazi" -> math
        "arabic", "arabi" -> arabic
        "farsi", "adabiat" -> farsi
        "fonoon", "arooz", "negaresh" -> fonoon
        "dini", "quran", "hedyeh", "payam" -> dini
        "english", "zaban" -> english
        "sociology", "social_studies", "ejtemaei", "social_identity" -> sociology
        "psychology", "ravanshenasi" -> psychology
        "history" -> history
        "geography" -> geography
        "philosophy", "falsafeh" -> philosophy
        "logic" -> logic
        "economics" -> economics
        "farhangian" -> farhangian
        "health" -> health
        "honar" -> honar
        else -> fallback
    }
}

// ───────────────────────────────────────────────────────────────────────────
// ۳) توکن‌ها
// ───────────────────────────────────────────────────────────────────────────

data class AppTokens(
    val isDark: Boolean,
    val surface: Color,
    val surfaceAlt: Color,
    val hairline: Color,
    val ink: Color,
    val inkSoft: Color,
    val inkFaint: Color,
    val accent: Color,
    val accentSoft: Color,
    val positive: Color,
    val warning: Color
)

private val LocalTokens = staticCompositionLocalOf<AppTokens> {
    error("AppDesignScope را بالای محتوا بگذار")
}

/** رنگ تاکیدی را از تم بگیر، ولی روشنایی سطوح را خودمان کنترل می‌کنیم. */
private fun buildTokens(scheme: ColorScheme): AppTokens {
    val isDark = scheme.surface.luminance() < 0.5f
    return if (isDark) {
        AppTokens(
            isDark = true,
            surface = Color(0xFF111A24),
            surfaceAlt = Color(0xFF16212D),
            hairline = Color(0xFF22303F),
            ink = Color(0xFFE9EFF7),
            inkSoft = Color(0xFFA7B4C4),
            inkFaint = Color(0xFF6E7C8B),
            accent = scheme.primary,
            accentSoft = scheme.primary.copy(alpha = 0.16f),
            positive = Color(0xFF6FBF94),
            warning = Color(0xFFE08A63)
        )
    } else {
        AppTokens(
            isDark = false,
            surface = Color(0xFFFFFDF9),
            surfaceAlt = Color(0xFFF5F1E8),
            hairline = Color(0xFFE4DBCB),
            ink = Color(0xFF141A22),
            inkSoft = Color(0xFF4C5563),
            inkFaint = Color(0xFF8B93A1),
            accent = scheme.primary,
            accentSoft = scheme.primary.copy(alpha = 0.12f),
            positive = Color(0xFF2F8F63),
            warning = Color(0xFFB04A3D)
        )
    }
}

@Composable
fun AppDesignScope(content: @Composable () -> Unit) {
    val scheme = MaterialTheme.colorScheme
    val tokens = remember(scheme.surface, scheme.primary, scheme.secondary) {
        buildTokens(scheme)
    }
    CompositionLocalProvider(LocalTokens provides tokens) { content() }
}

/** برای استفاده در کامپوننت‌های خودت. */
val appTokens: AppTokens
    @Composable get() = LocalTokens.current

// ───────────────────────────────────────────────────────────────────────────
// ۴) تایپوگرافی — یک جا، بدون sp دستی
// ───────────────────────────────────────────────────────────────────────────

object Type {
    @Composable fun display() = MaterialTheme.typography.headlineMedium.copy(
        fontWeight = FontWeight.ExtraBold, letterSpacing = (-0.6).sp, lineHeight = 40.sp
    )
    @Composable fun title() = MaterialTheme.typography.titleLarge.copy(
        fontWeight = FontWeight.Bold, lineHeight = 30.sp
    )
    @Composable fun section() = MaterialTheme.typography.titleMedium.copy(
        fontWeight = FontWeight.Bold, lineHeight = 26.sp
    )
    @Composable fun body() = MaterialTheme.typography.bodyLarge.copy(lineHeight = 30.sp)
    @Composable fun small() = MaterialTheme.typography.bodyMedium.copy(lineHeight = 24.sp)
    @Composable fun caption() = MaterialTheme.typography.labelMedium.copy(
        fontWeight = FontWeight.Medium, lineHeight = 18.sp
    )
    @Composable fun mono() = MaterialTheme.typography.bodyMedium.copy(
        fontWeight = FontWeight.SemiBold, textDirection = TextDirection.Ltr
    )
}

// ───────────────────────────────────────────────────────────────────────────
// ۵) جداکننده و برچسب بخش
// ───────────────────────────────────────────────────────────────────────────

@Composable
fun Hairline(modifier: Modifier = Modifier) {
    val t = appTokens
    Box(modifier.fillMaxWidth().height(1.dp).background(t.hairline))
}

@Composable
fun SectionLabel(
    text: String,
    modifier: Modifier = Modifier,
    trailing: String? = null
) {
    val t = appTokens
    Row(
        modifier = modifier.fillMaxWidth().padding(top = Space.xl, bottom = Space.s),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(Modifier.size(6.dp).clip(CircleShape).background(t.accent))
        Spacer(Modifier.width(Space.s))
        Text(
            text = text,
            style = Type.caption(),
            color = t.inkSoft,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier.weight(1f, fill = false)
        )
        if (trailing != null) {
            Spacer(Modifier.width(Space.s))
            Spacer(Modifier.weight(1f))
            Text(
                text = trailing,
                style = Type.caption(),
                color = t.inkFaint,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}

// ───────────────────────────────────────────────────────────────────────────
// ۶) کارت — تخت، با خط مویی، بدون سایه
// ───────────────────────────────────────────────────────────────────────────

@Composable
fun ContentCard(
    modifier: Modifier = Modifier,
    accent: Color? = null,
    content: @Composable () -> Unit
) {
    val t = appTokens
    val shape = RoundedCornerShape(Radius.m)
    Row(
        modifier = modifier
            .fillMaxWidth()
            .clip(shape)
            .background(t.surface)
            .border(1.dp, t.hairline, shape)
    ) {
        if (accent != null) {
            Box(Modifier.width(3.dp).heightIn(min = 48.dp).background(accent))
        }
        Box(Modifier.padding(Space.l).fillMaxWidth()) { content() }
    }
}

// ───────────────────────────────────────────────────────────────────────────
// ۷) ردیف درس — شماره، عنوان، متادیتا، نوار پیشرفت
// ───────────────────────────────────────────────────────────────────────────

@Composable
fun LessonRow(
    index: Int,
    title: String,
    meta: String,
    modifier: Modifier = Modifier,
    progress: Float = 0f,
    done: Boolean = false,
    tint: Color? = null,
    onClick: (() -> Unit)? = null
) {
    val t = appTokens
    val shape = RoundedCornerShape(Radius.m)
    val accent = tint ?: t.accent
    val animated by animateFloatAsState(
        targetValue = progress.coerceIn(0f, 1f),
        animationSpec = tween(Motion.NORMAL),
        label = "lesson-progress"
    )
    val numberBg by animateColorAsState(
        targetValue = if (done) t.positive else t.surfaceAlt,
        animationSpec = tween(Motion.QUICK),
        label = "lesson-number"
    )

    Row(
        modifier = modifier
            .fillMaxWidth()
            .clip(shape)
            .background(t.surface)
            .border(1.dp, if (done) t.positive.copy(alpha = 0.35f) else t.hairline, shape)
            .then(
                if (onClick != null) Modifier.clickable(role = Role.Button) { onClick() } else Modifier
            )
            .padding(horizontal = Space.l, vertical = Space.m),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(30.dp)
                .clip(RoundedCornerShape(Radius.s))
                .background(numberBg),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = toPersianDigits(index),
                style = Type.caption(),
                color = if (done) Color.White else t.inkSoft,
                maxLines = 1
            )
        }

        Spacer(Modifier.width(Space.m))

        Column(Modifier.weight(1f)) {
            Text(
                text = title,
                style = Type.small().copy(fontWeight = FontWeight.SemiBold),
                color = t.ink,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )
            Spacer(Modifier.height(Space.xs))
            Text(
                text = meta,
                style = Type.caption(),
                color = t.inkFaint,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            if (animated > 0.01f) {
                Spacer(Modifier.height(Space.s))
                ProgressLine(progress = animated, color = accent)
            }
        }

        if (onClick != null) {
            Spacer(Modifier.width(Space.s))
            Text(
                text = "‹",
                style = Type.small(),
                color = t.inkFaint,
                maxLines = 1
            )
        }
    }
}

// ───────────────────────────────────────────────────────────────────────────
// ۸) نوار پیشرفت — نازک، بدون گوشه‌ی اضافه
// ───────────────────────────────────────────────────────────────────────────

@Composable
fun ProgressLine(
    progress: Float,
    modifier: Modifier = Modifier,
    color: Color? = null,
    height: Dp = 4.dp
) {
    val t = appTokens
    val p = progress.coerceIn(0f, 1f)
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(height)
            .clip(CircleShape)
            .background(t.hairline)
    ) {
        Box(
            Modifier
                .fillMaxWidth(p)
                .height(height)
                .clip(CircleShape)
                .background(color ?: t.accent)
        )
    }
}

// ───────────────────────────────────────────────────────────────────────────
// ۹) فرمول — جدا شده از متن فارسی تا اعداد برنگردند
// ───────────────────────────────────────────────────────────────────────────

@Composable
fun FormulaBlock(
    formulas: List<String>,
    modifier: Modifier = Modifier,
    title: String = "فرمول‌های طلایی"
) {
    if (formulas.isEmpty()) return
    val t = appTokens
    Column(modifier.fillMaxWidth()) {
        Text(
            text = title,
            style = Type.caption(),
            color = t.inkFaint,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
        Spacer(Modifier.height(Space.s))
        formulas.forEachIndexed { i, f ->
            CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Ltr) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = Space.xs)
                        .clip(RoundedCornerShape(Radius.s))
                        .background(t.surfaceAlt)
                        .padding(horizontal = Space.m, vertical = Space.s),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = toPersianDigits(i + 1),
                        style = Type.caption(),
                        color = t.accent,
                        maxLines = 1
                    )
                    Spacer(Modifier.width(Space.s))
                    Text(
                        text = f,
                        style = Type.mono(),
                        color = t.ink,
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis,
                        modifier = Modifier.weight(1f)
                    )
                }
            }
            Spacer(Modifier.height(Space.xs))
        }
    }
}

// ───────────────────────────────────────────────────────────────────────────
// ۱۰) گزینه‌ی تست
// ───────────────────────────────────────────────────────────────────────────

enum class OptionState { Idle, Selected, Correct, Wrong }

@Composable
fun QuizOption(
    index: Int,
    text: String,
    state: OptionState,
    modifier: Modifier = Modifier,
    onClick: (() -> Unit)? = null
) {
    val t = appTokens
    val shape = RoundedCornerShape(Radius.s)
    val borderColor = when (state) {
        OptionState.Idle -> t.hairline
        OptionState.Selected -> t.accent
        OptionState.Correct -> t.positive
        OptionState.Wrong -> t.warning
    }
    val fill = when (state) {
        OptionState.Idle -> t.surface
        OptionState.Selected -> t.accentSoft
        OptionState.Correct -> t.positive.copy(alpha = 0.12f)
        OptionState.Wrong -> t.warning.copy(alpha = 0.12f)
    }
    val animatedBorder by animateColorAsState(borderColor, tween(Motion.QUICK), label = "opt-border")
    val animatedFill by animateColorAsState(fill, tween(Motion.QUICK), label = "opt-fill")

    Row(
        modifier = modifier
            .fillMaxWidth()
            .clip(shape)
            .background(animatedFill)
            .border(1.5.dp, animatedBorder, shape)
            .then(
                if (onClick != null) Modifier.clickable(role = Role.RadioButton) { onClick() } else Modifier
            )
            .padding(horizontal = Space.m, vertical = Space.m),
        verticalAlignment = Alignment.Top
    ) {
        Box(
            modifier = Modifier
                .size(22.dp)
                .clip(RoundedCornerShape(Radius.s - 4.dp))
                .background(animatedBorder.copy(alpha = 0.16f)),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = toPersianDigits(index),
                style = Type.caption(),
                color = animatedBorder,
                maxLines = 1
            )
        }
        Spacer(Modifier.width(Space.m))
        Text(
            text = text,
            style = Type.small(),
            color = t.ink,
            maxLines = 4,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier.weight(1f)
        )
    }
}

// ───────────────────────────────────────────────────────────────────────────
// ۱۱) برچسب کوچک
// ───────────────────────────────────────────────────────────────────────────

@Composable
fun Pill(
    text: String,
    modifier: Modifier = Modifier,
    tint: Color? = null
) {
    val t = appTokens
    val c = tint ?: t.accent
    Box(
        modifier = modifier
            .clip(CircleShape)
            .background(c.copy(alpha = 0.12f))
            .border(1.dp, c.copy(alpha = 0.35f), CircleShape)
            .padding(horizontal = Space.m, vertical = Space.xs)
    ) {
        Text(
            text = text,
            style = Type.caption(),
            color = c,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
    }
}

// ───────────────────────────────────────────────────────────────────────────
// ۱۲) حالت خالی — یک جمله، بدون تصویر تزئینی
// ───────────────────────────────────────────────────────────────────────────

@Composable
fun EmptyState(
    message: String,
    modifier: Modifier = Modifier,
    hint: String? = null
) {
    val t = appTokens
    Column(
        modifier = modifier
            .fillMaxWidth()
            .widthIn(max = 420.dp)
            .padding(vertical = Space.xxl, horizontal = Space.l),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = message,
            style = Type.small(),
            color = t.inkSoft,
            textAlign = TextAlign.Center,
            maxLines = 3,
            overflow = TextOverflow.Ellipsis
        )
        if (hint != null) {
            Spacer(Modifier.height(Space.s))
            Text(
                text = hint,
                style = Type.caption(),
                color = t.inkFaint,
                textAlign = TextAlign.Center,
                maxLines = 3,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}

// ───────────────────────────────────────────────────────────────────────────
// ۱۳) متن ترکیبی فارسی و فرمول
// ───────────────────────────────────────────────────────────────────────────

/**
 * متن فارسی که داخلش عبارت ریاضی دارد. عبارت‌های لاتین جدا و چپ‌به‌راست
 * نمایش داده می‌شوند تا «۸ − ۳ = ۵» برنگردد.
 */
@Composable
fun MixedText(
    text: String,
    modifier: Modifier = Modifier,
    emphasizeNumbers: Boolean = true
) {
    val t = appTokens
    val annotated: AnnotatedString = remember(text, emphasizeNumbers) {
        buildAnnotatedString {
            var i = 0
            val buffer = StringBuilder()
            fun flush() {
                if (buffer.isNotEmpty()) {
                    append(buffer.toString())
                    buffer.clear()
                }
            }
            while (i < text.length) {
                val c = text[i]
                val isFormulaChar = c.isDigit() ||
                    c in "+-−×÷=/^²³<>()" ||
                    (c in 'a'..'z') || (c in 'A'..'Z')
                if (isFormulaChar) {
                    flush()
                    val start = i
                    while (i < text.length) {
                        val d = text[i]
                        if (d.isDigit() || d in "+-−×÷=/^²³<>()" || d in 'a'..'z' || d in 'A'..'Z') i++
                        else break
                    }
                    val chunk = text.substring(start, i)
                    if (emphasizeNumbers && chunk.length > 1) {
                        withStyle(SpanStyle(fontWeight = FontWeight.SemiBold)) { append(chunk) }
                    } else {
                        append(chunk)
                    }
                } else {
                    buffer.append(c)
                    i++
                }
            }
            flush()
        }
    }
    Text(
        text = annotated,
        style = Type.body(),
        color = t.ink,
        maxLines = 12,
        overflow = TextOverflow.Ellipsis,
        modifier = modifier.fillMaxWidth()
    )
}

// ───────────────────────────────────────────────────────────────────────────
// ۱۴) عدد فارسی
// ───────────────────────────────────────────────────────────────────────────

private const val FA_DIGITS = "۰۱۲۳۴۵۶۷۸۹"

fun toPersianDigits(value: Int): String = toPersianDigits(value.toString())

fun toPersianDigits(value: String): String = buildString(value.length) {
    for (ch in value) {
        append(if (ch in '0'..'9') FA_DIGITS[ch - '0'] else ch)
    }
}

fun Float.toPercentFa(): String = "${(this * 100).roundToInt().let(::toPersianDigits)}٪"

// ───────────────────────────────────────────────────────────────────────────
// ۱۵) ابزار دسترس‌پذیری برای عناصر گرافیکی
// ───────────────────────────────────────────────────────────────────────────

fun Modifier.describedAs(description: String): Modifier =
    this.semantics { contentDescription = description }

/** رنگ متن روی یک پس‌زمینه‌ی دلخواه؛ اگر تیره بود سفید، وگرنه مشکی. */
fun onColorFor(background: Color): Color =
    if (background.luminance() > 0.55f) Color(0xFF11181F) else Color.White

/** برای تست بصری: یک لیست نمونه که همه‌ی کامپوننت‌ها را نشان می‌دهد. */
@Composable
fun AppDesignShowcase(modifier: Modifier = Modifier) {
    AppDesignScope {
        Column(modifier.fillMaxWidth().padding(Space.l)) {
            SectionLabel("درس‌ها", trailing = "۲۴ درس")
            LessonRow(
                index = 1,
                title = "ریاضی پایه ۱: جمع و تفریق",
                meta = "۲۵ دقیقه · ۴ گام",
                progress = 0.75f,
                done = false,
                tint = SubjectPalette.math.resolve(appTokens.isDark)
            )
            Spacer(Modifier.height(Space.s))
            LessonRow(
                index = 2,
                title = "فارسی پایه ۲: جمله، اسم و فعل",
                meta = "۲۰ دقیقه · ۴ گام",
                progress = 1f,
                done = true,
                tint = SubjectPalette.farsi.resolve(appTokens.isDark)
            )
            Spacer(Modifier.height(Space.l))
            ContentCard(accent = appTokens.accent) {
                Column {
                    Text("گام ۱ — تعریف", style = Type.section(), color = appTokens.ink)
                    Spacer(Modifier.height(Space.s))
                    MixedText("در معادله ۸ − ۳ = ۵، عدد ۵ حاصل تفریق است.")
                }
            }
            Spacer(Modifier.height(Space.l))
            FormulaBlock(listOf("a² + b² = c²", "Δ = b² − 4ac"))
            Spacer(Modifier.height(Space.l))
            QuizOption(1, "همیشه درست است", OptionState.Correct)
            Spacer(Modifier.height(Space.s))
            QuizOption(2, "فقط وقتی x مثبت باشد", OptionState.Wrong)
            Spacer(Modifier.height(Space.l))
            Row(horizontalArrangement = Arrangement.spacedBy(Space.s)) {
                Pill("کنکور ۱۴۰۶")
                Pill("نهایی", tint = appTokens.positive)
            }
        }
    }
}
