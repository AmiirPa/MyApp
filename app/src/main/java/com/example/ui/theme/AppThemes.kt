package com.example.ui.theme

/**
 * ═══════════════════════════════════════════════════════════════════════════
 *  سه تم گرافیکی — «باغ شب»، «شفق»، «کاشی»
 *
 *  هر تم یک صحنه‌ی تمام‌صفحه است که با Canvas کشیده می‌شود (بدون تصویر
 *  بیت‌مپی، پس حجم اضافه نمی‌کند و در هر اندازه‌ای تیز می‌ماند)، به‌همراه
 *  یک بسته رنگ که همه‌ی متن‌ها و پنل‌ها از آن می‌آیند.
 *
 *  ── نصب (سه قدم) ────────────────────────────────────────────────────────
 *
 *  ۱) این فایل را بگذار در:
 *       app/src/main/java/com/example/ui/theme/AppThemes.kt
 *
 *  ۲) در MainActivity، جایی که محتوای برنامه رندر می‌شود، داخل Box یک
 *     لایه‌ی پس‌زمینه و یک تأمین‌کننده‌ی تم اضافه کن:
 *
 *       var variant by remember { mutableStateOf(AppThemeVariant.GARDEN) }
 *       ProvideAppTheme(variant) {
 *           Box(Modifier.fillMaxSize()) {
 *               ThemeBackground(variant)      // صحنه‌ی تمام‌صفحه
 *               YourExistingContent()         // همان محتوای فعلی، دست‌نخورده
 *           }
 *       }
 *
 *  ۳) هرجا خواستی انتخاب تم را نشان بدهی، همین را صدا بزن:
 *
 *       ThemeSwitcher(selected = variant, onSelect = { variant = it })
 *
 *  ── استفاده در متن‌ها ────────────────────────────────────────────────────
 *
 *       val t = LocalAppTheme.current
 *       Text("سلام", color = t.ink)
 *       Box(Modifier.glassPanel(t)) { ... }     // پنل شیشه‌ای
 *
 *  هیچ تمی تم فعلی برنامه را عوض نمی‌کند؛ فقط رنگ‌ها را در اختیار می‌گذارد.
 *  اگر چیزی خراب شد، فقط دو خط فراخوانی را بردار.
 * ═══════════════════════════════════════════════════════════════════════════
 */

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlin.math.cos
import kotlin.math.sin

// ═══════════════════════════════════════════════════════════════════════════
//  ۱) مدل تم
// ═══════════════════════════════════════════════════════════════════════════

enum class AppThemeVariant(val label: String) {
    GARDEN("باغ شب"),
    AURORA("شفق"),
    TILE("کاشی")
}

/**
 * رنگ‌های یک تم. همه‌ی رنگ‌ها از اینجا می‌آیند تا هیچ‌جا رنگ ثابت نماند.
 */
data class AppThemeColors(
    val bgTop: Color,
    val bgMid: Color,
    val bgBottom: Color,
    val ink: Color,
    val inkSoft: Color,
    val inkFaint: Color,
    val accent: Color,
    val accentSoft: Color,
    val line: Color,
    val panel: Color,
    val panelBorder: Color,
    val danger: Color,
    val onAccent: Color
)

val GardenNight = AppThemeColors(
    bgTop = Color(0xFF0B4B45),
    bgMid = Color(0xFF083A36),
    bgBottom = Color(0xFF04211F),
    ink = Color(0xFFF4EFE2),
    inkSoft = Color(0xFFC8D8D2),
    inkFaint = Color(0xFF8FA8A2),
    accent = Color(0xFFE8C274),
    accentSoft = Color(0xFF7FD0C4),
    line = Color(0x47E8C274),
    panel = Color(0x8C062624),
    panelBorder = Color(0x47E8C274),
    danger = Color(0xFFFFC9BD),
    onAccent = Color(0xFF10201D)
)

val Aurora = AppThemeColors(
    bgTop = Color(0xFF2B1055),
    bgMid = Color(0xFF6F2A8F),
    bgBottom = Color(0xFF1A0A2E),
    ink = Color(0xFFF7F2FF),
    inkSoft = Color(0xFFCFC2E6),
    inkFaint = Color(0xFFA394BF),
    accent = Color(0xFFFFD6EC),
    accentSoft = Color(0xFF7EF0FF),
    line = Color(0x33FFFFFF),
    panel = Color(0x1AFFFFFF),
    panelBorder = Color(0x33FFFFFF),
    danger = Color(0xFFFFC0C0),
    onAccent = Color(0xFF2B1055)
)

val TileBlue = AppThemeColors(
    bgTop = Color(0xFF0D2F4A),
    bgMid = Color(0xFF092237),
    bgBottom = Color(0xFF061726),
    ink = Color(0xFFEEF6FC),
    inkSoft = Color(0xFFB9CDE0),
    inkFaint = Color(0xFF8AA3B8),
    accent = Color(0xFFE8C274),
    accentSoft = Color(0xFF5FB8D8),
    line = Color(0x4D5FB8D8),
    panel = Color(0x9E061726),
    panelBorder = Color(0x4D5FB8D8),
    danger = Color(0xFFFFC9BD),
    onAccent = Color(0xFF061726)
)

fun colorsFor(variant: AppThemeVariant): AppThemeColors = when (variant) {
    AppThemeVariant.GARDEN -> GardenNight
    AppThemeVariant.AURORA -> Aurora
    AppThemeVariant.TILE -> TileBlue
}

val LocalAppTheme = staticCompositionLocalOf { GardenNight }

@Composable
fun ProvideAppTheme(
    variant: AppThemeVariant,
    content: @Composable () -> Unit
) {
    CompositionLocalProvider(LocalAppTheme provides colorsFor(variant), content = content)
}

// ═══════════════════════════════════════════════════════════════════════════
//  ۲) صحنه‌ی پس‌زمینه — تمام‌صفحه
// ═══════════════════════════════════════════════════════════════════════════

@Composable
fun ThemeBackground(
    variant: AppThemeVariant,
    modifier: Modifier = Modifier
) {
    val t = colorsFor(variant)
    Canvas(modifier.fillMaxSize()) {
        // پایه: گرادیان عمودی
        drawRect(
            brush = Brush.linearGradient(
                colors = listOf(t.bgTop, t.bgMid, t.bgBottom),
                start = Offset(size.width * 0.2f, 0f),
                end = Offset(size.width * 0.8f, size.height)
            )
        )
        when (variant) {
            AppThemeVariant.GARDEN -> drawGarden(t)
            AppThemeVariant.AURORA -> drawAurora(t)
            AppThemeVariant.TILE -> drawTile(t)
        }
    }
}

/** باغ شب: گره‌چینی طلایی + طاق + هاله‌ی نور */
private fun DrawScope.drawGarden(t: AppThemeColors) {
    // هاله‌ی گرم بالا-راست
    drawCircle(
        brush = Brush.radialGradient(
            colors = listOf(t.accent.copy(alpha = 0.45f), Color.Transparent),
            center = Offset(size.width * 0.82f, size.height * 0.10f),
            radius = size.width * 0.55f
        ),
        radius = size.width * 0.55f,
        center = Offset(size.width * 0.82f, size.height * 0.10f)
    )

    // شبکه‌ی ستاره‌های هشت‌پر
    val cell = size.width / 7f
    val stroke = Stroke(width = 1.dp.toPx())
    var row = 0
    var y = cell / 2f
    while (y < size.height) {
        var x = cell / 2f + if (row % 2 == 0) 0f else cell / 2f
        while (x < size.width + cell) {
            drawEightStar(
                center = Offset(x, y),
                radius = cell * 0.30f,
                color = t.accent.copy(alpha = 0.18f),
                style = stroke
            )
            drawCircle(
                color = t.accentSoft.copy(alpha = 0.12f),
                radius = cell * 0.055f,
                center = Offset(x, y)
            )
            x += cell
        }
        y += cell
        row++
    }

    // طاق (قوس) بالای صفحه
    val archW = size.width * 0.46f
    val archH = size.height * 0.30f
    val archLeft = size.width * 0.27f
    val archTop = size.height * 0.06f
    val arch = Path().apply {
        moveTo(archLeft, archTop + archH)
        lineTo(archLeft, archTop + archW * 0.5f)
        cubicTo(
            archLeft, archTop,
            archLeft + archW, archTop,
            archLeft + archW, archTop + archW * 0.5f
        )
        lineTo(archLeft + archW, archTop + archH)
    }
    drawPath(arch, color = t.accent.copy(alpha = 0.40f), style = Stroke(width = 2.dp.toPx()))

    val inner = Path().apply {
        val inset = archW * 0.10f
        moveTo(archLeft + inset, archTop + archH)
        lineTo(archLeft + inset, archTop + archW * 0.5f)
        cubicTo(
            archLeft + inset, archTop + inset,
            archLeft + archW - inset, archTop + inset,
            archLeft + archW - inset, archTop + archW * 0.5f
        )
        lineTo(archLeft + archW - inset, archTop + archH)
    }
    drawPath(inner, color = t.accent.copy(alpha = 0.18f), style = Stroke(width = 1.dp.toPx()))

    // ستاره‌ی مرکزی طاق
    drawEightStar(
        center = Offset(archLeft + archW / 2f, archTop + archW * 0.52f),
        radius = archW * 0.13f,
        color = t.accent.copy(alpha = 0.85f),
        style = null
    )

    // ذرات نور
    val dots = listOf(
        0.10f to 0.46f, 0.88f to 0.40f, 0.72f to 0.62f,
        0.20f to 0.72f, 0.55f to 0.86f, 0.92f to 0.78f
    )
    dots.forEachIndexed { i, (fx, fy) ->
        drawCircle(
            color = t.accent.copy(alpha = if (i % 2 == 0) 0.55f else 0.30f),
            radius = (if (i % 3 == 0) 2.2f else 1.4f) * density,
            center = Offset(size.width * fx, size.height * fy)
        )
    }
}

/** شفق: سه هاله‌ی رنگی + قوس‌های نوری */
private fun DrawScope.drawAurora(t: AppThemeColors) {
    fun glow(cx: Float, cy: Float, r: Float, color: Color, alpha: Float) {
        val c = Offset(size.width * cx, size.height * cy)
        drawCircle(
            brush = Brush.radialGradient(
                colors = listOf(color.copy(alpha = alpha), Color.Transparent),
                center = c, radius = r
            ),
            radius = r, center = c
        )
    }
    glow(0.22f, 0.10f, size.width * 0.75f, t.accentSoft, 0.45f)
    glow(0.86f, 0.46f, size.width * 0.85f, Color(0xFFFF9EC7), 0.40f)
    glow(0.50f, 0.94f, size.width * 0.90f, Color(0xFFFFE17E), 0.32f)

    // قوس‌های نوری
    val stroke = Stroke(width = 1.dp.toPx())
    for (i in 0..2) {
        val yBase = size.height * (0.26f + i * 0.03f)
        val amp = size.height * 0.035f
        val p = Path()
        var x = -size.width * 0.1f
        p.moveTo(x, yBase)
        while (x < size.width * 1.1f) {
            val nx = x + size.width * 0.2f
            p.quadraticBezierTo(
                x + size.width * 0.1f, yBase - amp,
                nx, yBase
            )
            x = nx
        }
        drawPath(p, color = Color.White.copy(alpha = 0.16f), style = stroke)
    }

    // ستاره‌ها
    val stars = listOf(
        0.16f to 0.06f, 0.34f to 0.04f, 0.72f to 0.12f, 0.88f to 0.05f,
        0.50f to 0.18f, 0.08f to 0.30f, 0.94f to 0.26f
    )
    stars.forEachIndexed { i, (fx, fy) ->
        drawCircle(
            color = Color.White.copy(alpha = if (i % 2 == 0) 0.85f else 0.45f),
            radius = (if (i % 3 == 0) 2f else 1.3f) * density,
            center = Offset(size.width * fx, size.height * fy)
        )
    }
}

/** کاشی: گره‌چینی متراکم + پرده‌ی تیره‌ی عمودی */
private fun DrawScope.drawTile(t: AppThemeColors) {
    val cell = size.width / 8f
    val stroke = Stroke(width = 1.dp.toPx())
    var row = 0
    var y = cell / 2f
    while (y < size.height) {
        var x = cell / 2f + if (row % 2 == 0) 0f else cell / 2f
        while (x < size.width + cell) {
            drawEightStar(
                center = Offset(x, y),
                radius = cell * 0.34f,
                color = t.accentSoft.copy(alpha = 0.35f),
                style = stroke
            )
            drawCircle(
                color = t.accent.copy(alpha = 0.28f),
                radius = cell * 0.05f,
                center = Offset(x, y),
                style = Stroke(width = 1.dp.toPx())
            )
            x += cell
        }
        y += cell
        row++
    }

    // پرده‌ی تیره تا متن خوانا بماند
    drawRect(
        brush = Brush.verticalGradient(
            colors = listOf(
                Color(0x26081726),
                Color(0x8C081726),
                Color(0xEB061726)
            )
        )
    )
}

/** ستاره‌ی هشت‌پر — پایه‌ی گره‌چینی */
private fun DrawScope.drawEightStar(
    center: Offset,
    radius: Float,
    color: Color,
    style: Stroke?
) {
    val p = Path()
    val points = 8
    for (i in 0 until points * 2) {
        val r = if (i % 2 == 0) radius else radius * 0.52f
        val a = Math.PI * i / points - Math.PI / 2
        val x = center.x + (r * cos(a)).toFloat()
        val y = center.y + (r * sin(a)).toFloat()
        if (i == 0) p.moveTo(x, y) else p.lineTo(x, y)
    }
    p.close()
    if (style == null) drawPath(p, color = color) else drawPath(p, color = color, style = style)
}

// ═══════════════════════════════════════════════════════════════════════════
//  ۳) پنل شیشه‌ای و سواچ تم
// ═══════════════════════════════════════════════════════════════════════════

/** پنلی که محتوا روی گرافیک می‌نشیند و خوانا می‌ماند. */
fun Modifier.glassPanel(t: AppThemeColors, radius: Int = 18): Modifier =
    this
        .clip(RoundedCornerShape(radius.dp))
        .background(t.panel)
        .border(1.dp, t.panelBorder, RoundedCornerShape(radius.dp))

@Composable
fun ThemeSwitcher(
    selected: AppThemeVariant,
    onSelect: (AppThemeVariant) -> Unit,
    modifier: Modifier = Modifier
) {
    val t = LocalAppTheme.current
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        AppThemeVariant.values().forEach { v ->
            val active = v == selected
            val c = colorsFor(v)
            Row(
                modifier = Modifier
                    .weight(1f)
                    .clip(RoundedCornerShape(14.dp))
                    .background(if (active) t.accent.copy(alpha = 0.22f) else t.panel)
                    .border(
                        width = if (active) 1.5.dp else 1.dp,
                        color = if (active) t.accent else t.panelBorder,
                        shape = RoundedCornerShape(14.dp)
                    )
                    .clickable { onSelect(v) }
                    .padding(vertical = 10.dp, horizontal = 8.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Box(
                    Modifier
                        .size(12.dp)
                        .clip(CircleShape)
                        .background(
                            Brush.linearGradient(listOf(c.bgTop, c.bgMid, c.accent))
                        )
                )
                Text(
                    text = v.label,
                    color = if (active) t.accent else t.inkSoft,
                    fontSize = 11.5.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(start = 6.dp)
                )
            }
        }
    }
}
