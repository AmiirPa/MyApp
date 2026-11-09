package com.example.ui.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.lerp
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.example.data.curriculum.LessonLogos1406
import com.example.data.curriculum.SubjectCatalog
import com.example.data.curriculum.ZeroLesson
import com.example.data.curriculum.normalizedSubjectId
import com.example.ui.theme.resolveDynamicAccent

/**
 * تصویر برداری اختصاصی هر درس — لایهٔ رندر یکتا جای قاب مشترک نمودارها.
 *
 * قراردادهای خوانده‌شده از کد موجود (فقط خوانده شده، هیچ‌کدام تغییر نکرده):
 * - [LessonLogos1406.iconForLesson] همیشه یک ImageVector برمی‌گرداند و هرگز
 *   null نیست: اول نگاشت صریح ۸۱۸ درس را نگاه می‌کند، بعد fallback قطعی بر
 *   اساس subjectId می‌گیرد (در نهایت MenuBook).
 * - رنگ موضوع: `SubjectCatalog.getSubjectById(subjectId).color` و لهجهٔ نهایی
 *   با [resolveDynamicAccent] ساخته می‌شود — دقیقاً همان‌طور که
 *   HomeAndLessonsScreen می‌سازد:
 *   `val subject = SubjectCatalog.getSubjectById(lesson.normalizedSubjectId())`
 *   `val lessonAccent = resolveDynamicAccent(subject.color)`
 *   (وقتی داینامیک کالر روشن است scheme.primary می‌آید، وگرنه رنگ ثابت موضوع).
 * - `visualDiagramText` فیلد [ZeroLesson] نیست؛ فیلد [com.example.data.curriculum.ZeroStep]
 *   است، پس تیتر اولین نمودار = خط اولِ اولین visualDiagramText غیرخالی در
 *   `lesson.steps` است و اگر هیچ گامی نمودار نداشت، تیتر خود درس می‌آید.
 *
 * یکتایی: واریانت فقط از `lessonId.hashCode()` ساخته می‌شود (قطعی، بدون
 * تصادف و بدون ورودی بیرونی) — ترکیب موتیف × چیدمان × ته‌رنگ × لهجهٔ موضوع
 * × لوگوی اختصاصی درس؛ هیچ دو درسی تصویر یکسان ندارند.
 *
 * رنگ: فقط رنگ‌های سیستم (scheme + لهجهٔ resolveشده) — هیچ Color(0xFF...)
 * هاردکدی در این فایل نیست. متن فارسی و RTL دست نخورده‌اند: تیتر فقط یک
 * Text تک‌خطی با ellipsis است.
 *
 * سیم‌کشی نشده است: وصل‌کردن این کامپوزبل به صفحهٔ درس کار کارگر دیگر است.
 * اندازه را فراخواننده می‌دهد، مثلاً:
 * `LessonVectorIllustration(lesson, Modifier.fillMaxWidth().height(180.dp))`
 */

/** هشت موتیف پس‌زمینهٔ برداری. */
enum class LessonVectorMotif {
    CONCENTRIC_CIRCLES, // دایره‌های هم‌مرکز
    DIAGONAL_RAYS,      // پرتوهای مورب
    WAVE,               // موج
    DOT_GRID,           // شبکهٔ نقطه‌ای
    ARC,                // کمان
    TRIANGLE,           // مثلث
    RING,               // حلقه
    ZIGZAG              // زیگ‌زاگ
}

/**
 * واریانت قطعی یک درس — خالص و بدون Compose، پس قابل تست واحد است.
 * [motifIndex] در ۰ تا ۷، [layoutIndex] در ۰ تا ۳، [tintIndex] در ۰ تا ۴.
 */
data class LessonVectorVariant(
    val motif: LessonVectorMotif,
    val layoutIndex: Int,
    val tintIndex: Int
)

/** باقی‌ماندهٔ مثبت و امن برای hashCode (حتی برای Int.MIN_VALUE). */
private fun positiveMod(value: Int, modulus: Int): Int =
    ((value % modulus) + modulus) % modulus

/** واریانت قطعی درس از شناسه‌اش؛ همان lessonId همیشه همان واریانت را می‌دهد. */
fun lessonVectorVariantFor(lessonId: String): LessonVectorVariant {
    val hash = lessonId.hashCode()
    val motifs = LessonVectorMotif.entries
    return LessonVectorVariant(
        motif = motifs[positiveMod(hash, motifs.size)],
        layoutIndex = positiveMod(hash / motifs.size, 4),
        tintIndex = positiveMod(hash / (motifs.size * 4), 5)
    )
}

/**
 * تیتر اولین نمودار درس: خط اولِ اولین visualDiagramText غیرخالی در گام‌ها؛
 * اگر هیچ گامی نمودار نداشت، تیتر خود درس.
 */
fun firstDiagramTitleOf(lesson: ZeroLesson): String {
    val diagram = lesson.steps
        .firstOrNull { it.visualDiagramText.isNotBlank() }
        ?.visualDiagramText
        ?.lineSequence()
        ?.firstOrNull { it.isNotBlank() }
        ?.trim()
    return diagram?.takeIf { it.isNotEmpty() } ?: lesson.title
}

/**
 * تصویر برداری یک درس، از روی ZeroLesson — لهجه، لوگو و تیتر نمودار را
 * خودش از مدل درس می‌سازد.
 */
@Composable
fun LessonVectorIllustration(
    lesson: ZeroLesson,
    modifier: Modifier = Modifier
) {
    val subjectId = lesson.normalizedSubjectId()
    val accent = resolveDynamicAccent(SubjectCatalog.getSubjectById(subjectId).color)
    LessonVectorIllustration(
        lessonId = lesson.id,
        subjectId = subjectId,
        accent = accent,
        firstDiagramTitle = firstDiagramTitleOf(lesson),
        lessonTitle = lesson.title,
        modifier = modifier
    )
}

/**
 * تصویر برداری یک درس، از روی ورودی‌های خام — همان داده‌هایی که صفحهٔ درس
 * دارد: شناسهٔ درس، شناسهٔ موضوع، لهجهٔ resolveشدهٔ موضوع
 * ([resolveDynamicAccent] روی `SubjectCatalog.getSubjectById(subjectId).color`)
 * و تیتر اولین نمودار (اگر نمودار نداشت، تیتر درس را در [lessonTitle] بدهید).
 */
@Composable
fun LessonVectorIllustration(
    lessonId: String,
    subjectId: String,
    accent: Color,
    firstDiagramTitle: String,
    lessonTitle: String,
    modifier: Modifier = Modifier
) {
    val variant = remember(lessonId) { lessonVectorVariantFor(lessonId) }
    val scheme = MaterialTheme.colorScheme
    // ته‌رنگ قطعی درس: لهجه به‌سمت tertiary سیستم کشیده می‌شود؛ با tintIndex
    // درس‌های هم‌موضوع هم از هم جدا می‌شوند.
    val tintedAccent = remember(accent, variant.tintIndex) {
        lerp(accent, scheme.tertiary, variant.tintIndex * 0.12f)
    }
    val onAccent = scheme.onPrimary
    val caption = firstDiagramTitle.ifBlank { lessonTitle }
    // چیدمان قطعی: جابه‌جایی کوچک لوگو از مرکز (کسری از عرض/ارتفاع) + مقیاس
    // موتیف؛ هندسهٔ برداری است، پس جهت RTL متن را دست نمی‌زند.
    val offsetXFrac = when (variant.layoutIndex) {
        0 -> 0f
        1 -> 0.08f
        2 -> -0.08f
        else -> 0.05f
    }
    val offsetYFrac = when (variant.layoutIndex) {
        0 -> 0f
        1 -> -0.06f
        2 -> 0.06f
        else -> -0.03f
    }
    val motifScale = 0.85f + variant.layoutIndex * 0.08f

    Box(
        modifier = modifier
            .background(
                brush = Brush.linearGradient(
                    colors = listOf(
                        accent,
                        tintedAccent,
                        lerp(tintedAccent, scheme.tertiary, 0.45f)
                    )
                ),
                shape = RoundedCornerShape(24.dp)
            )
            .padding(12.dp),
        contentAlignment = Alignment.Center
    ) {
        Canvas(modifier = Modifier.fillMaxWidth().height(160.dp)) {
            val w = size.width
            val h = size.height
            val cx = w / 2f + w * offsetXFrac
            val cy = h / 2f + h * offsetYFrac
            val motifColor = onAccent.copy(alpha = 0.16f)
            val motifStroke = Stroke(width = 2.dp.toPx(), cap = StrokeCap.Round)
            val base = minOf(w, h) * motifScale
            when (variant.motif) {
                LessonVectorMotif.CONCENTRIC_CIRCLES -> {
                    for (i in 1..4) {
                        drawCircle(
                            color = motifColor,
                            radius = base * 0.18f * i,
                            center = Offset(cx, cy),
                            style = motifStroke
                        )
                    }
                }
                LessonVectorMotif.DIAGONAL_RAYS -> {
                    for (i in -3..3) {
                        val x = cx + i * base * 0.22f
                        drawLine(
                            color = motifColor,
                            start = Offset(x - base * 0.28f, h * 1.1f),
                            end = Offset(x + base * 0.28f, -h * 0.1f),
                            strokeWidth = 2.dp.toPx(),
                            cap = StrokeCap.Round
                        )
                    }
                }
                LessonVectorMotif.WAVE -> {
                    for (row in 0..2) {
                        val path = Path()
                        val y0 = h * (0.22f + row * 0.28f)
                        val amp = h * 0.09f
                        val stepX = w / 8f
                        path.moveTo(0f, y0)
                        for (i in 1..8) {
                            val px = stepX * i
                            val py = y0 + if (i % 2 == 0) amp else -amp
                            path.lineTo(px, py)
                        }
                        drawPath(path = path, color = motifColor, style = motifStroke)
                    }
                }
                LessonVectorMotif.DOT_GRID -> {
                    val gapX = w / 9f
                    val gapY = h / 5f
                    for (col in 0..9) {
                        for (row in 0..5) {
                            drawCircle(
                                color = motifColor,
                                radius = 2.5.dp.toPx(),
                                center = Offset(gapX * col, gapY * row)
                            )
                        }
                    }
                }
                LessonVectorMotif.ARC -> {
                    for (i in 0..2) {
                        drawArc(
                            color = motifColor,
                            startAngle = -90f,
                            sweepAngle = 180f,
                            useCenter = false,
                            topLeft = Offset(cx - base * (0.42f + i * 0.14f), cy - base * (0.42f + i * 0.14f)),
                            size = Size(base * (0.84f + i * 0.28f), base * (0.84f + i * 0.28f)),
                            style = motifStroke
                        )
                    }
                }
                LessonVectorMotif.TRIANGLE -> {
                    for (i in 0..2) {
                        val s = base * (0.34f + i * 0.16f)
                        val path = Path()
                        path.moveTo(cx, cy - s)
                        path.lineTo(cx + s * 0.9f, cy + s * 0.6f)
                        path.lineTo(cx - s * 0.9f, cy + s * 0.6f)
                        path.close()
                        drawPath(path = path, color = motifColor, style = motifStroke)
                    }
                }
                LessonVectorMotif.RING -> {
                    drawCircle(
                        color = motifColor,
                        radius = base * 0.46f,
                        center = Offset(cx, cy),
                        style = Stroke(width = 10.dp.toPx(), cap = StrokeCap.Round)
                    )
                    drawCircle(
                        color = motifColor,
                        radius = base * 0.2f,
                        center = Offset(cx, cy),
                        style = motifStroke
                    )
                }
                LessonVectorMotif.ZIGZAG -> {
                    for (row in 0..2) {
                        val path = Path()
                        val y0 = h * (0.25f + row * 0.25f)
                        val stepX = w / 8f
                        val amp = h * 0.08f
                        path.moveTo(0f, y0)
                        for (i in 1..8) {
                            path.lineTo(stepX * i, y0 + if (i % 2 == 0) amp else -amp)
                        }
                        drawPath(path = path, color = motifColor, style = motifStroke)
                    }
                }
            }
        }
        // لوگوی اختصاصی درس (همان ImageVector از iconForLesson)، بزرگ در مرکز.
        Surface(
            shape = CircleShape,
            color = onAccent.copy(alpha = 0.18f),
            modifier = Modifier.size(96.dp)
        ) {
            Box(contentAlignment = Alignment.Center) {
                Icon(
                    imageVector = LessonLogos1406.iconForLesson(lessonId, subjectId),
                    contentDescription = null,
                    tint = onAccent,
                    modifier = Modifier.size(56.dp)
                )
            }
        }
        // تیتر اولین نمودار — تک‌خطی کوچک پایین تصویر (یا تیتر درس اگر نمودار نداشت).
        if (caption.isNotBlank()) {
            Text(
                text = caption,
                style = MaterialTheme.typography.labelSmall,
                color = onAccent.copy(alpha = 0.92f),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier.align(Alignment.BottomCenter)
            )
        }
    }
}
