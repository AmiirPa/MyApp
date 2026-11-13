package com.example.ui.calm

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.dp
import kotlin.math.PI
import kotlin.math.sin

/**
 * تصویرسازی برداری متحرک بالای صفحهٔ امروز: کتاب باز، مسیر قله و ستاره‌های چشمک‌زن.
 * همه‌چیز با Canvas کشیده می‌شود (بدون فایل تصویری) تا سبک و تیز بماند.
 */
@Composable
fun CalmHeroIllustration(modifier: Modifier = Modifier) {
    val primary = MaterialTheme.colorScheme.primary
    val tertiary = MaterialTheme.colorScheme.tertiary
    val surface = MaterialTheme.colorScheme.surfaceVariant
    val t = rememberInfiniteTransition(label = "hero")
    val phase by t.animateFloat(
        initialValue = 0f, targetValue = 1f,
        animationSpec = infiniteRepeatable(tween(6000, easing = LinearEasing), RepeatMode.Restart),
        label = "phase"
    )
    val draw by t.animateFloat(
        initialValue = 0f, targetValue = 1f,
        animationSpec = infiniteRepeatable(tween(4200), RepeatMode.Reverse),
        label = "draw"
    )

    Canvas(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .height(150.dp)
            .clip(RoundedCornerShape(24.dp))
    ) {
        val w = size.width
        val h = size.height
        drawRect(Brush.verticalGradient(listOf(surface, primary.copy(alpha = 0.18f))))

        // تپه‌های موجی در پس‌زمینه
        for (layer in 0..1) {
            val p = Path()
            val base = h * (0.72f + layer * 0.1f)
            p.moveTo(0f, h)
            p.lineTo(0f, base)
            var x = 0f
            while (x <= w) {
                val y = base + sin((x / w * 2 * PI + phase * 2 * PI + layer).toFloat()) * h * 0.05f
                p.lineTo(x, y); x += 8f
            }
            p.lineTo(w, h); p.close()
            drawPath(p, (if (layer == 0) primary else tertiary).copy(alpha = 0.22f + layer * 0.12f))
        }

        // مسیر قله (کشیده‌شدن تدریجی)
        val trail = Path().apply {
            moveTo(w * 0.08f, h * 0.82f)
            cubicTo(w * 0.3f, h * 0.9f, w * 0.35f, h * 0.45f, w * 0.55f, h * 0.5f)
            cubicTo(w * 0.7f, h * 0.55f, w * 0.75f, h * 0.2f, w * 0.88f, h * 0.18f)
        }
        drawPath(
            trail, primary.copy(alpha = 0.9f),
            style = Stroke(width = 4f, pathEffect = androidx.compose.ui.graphics.PathEffect.dashPathEffect(
                floatArrayOf(18f, 12f), phase = -phase * 120f))
        )
        // پرچم مقصد
        val fx = w * 0.88f; val fy = h * 0.18f
        drawLine(primary, Offset(fx, fy), Offset(fx, fy - h * 0.16f), strokeWidth = 4f)
        val flag = Path().apply {
            moveTo(fx, fy - h * 0.16f)
            lineTo(fx + w * 0.06f * (0.8f + 0.2f * draw), fy - h * 0.12f)
            lineTo(fx, fy - h * 0.08f); close()
        }
        drawPath(flag, tertiary)

        // کتاب باز با صفحهٔ ورق‌خورنده
        val bx = w * 0.18f; val by = h * 0.42f; val bw = w * 0.13f; val bh = h * 0.28f
        val left = Path().apply {
            moveTo(bx, by); quadraticBezierTo(bx - bw * 0.5f, by - bh * 0.1f, bx - bw, by)
            lineTo(bx - bw, by + bh); quadraticBezierTo(bx - bw * 0.5f, by + bh * 0.9f, bx, by + bh); close()
        }
        val right = Path().apply {
            moveTo(bx, by); quadraticBezierTo(bx + bw * 0.5f, by - bh * 0.1f, bx + bw, by)
            lineTo(bx + bw, by + bh); quadraticBezierTo(bx + bw * 0.5f, by + bh * 0.9f, bx, by + bh); close()
        }
        drawPath(left, Color.White.copy(alpha = 0.92f))
        drawPath(right, Color.White.copy(alpha = 0.85f))
        drawPath(left, primary, style = Stroke(3f))
        drawPath(right, primary, style = Stroke(3f))
        val turn = bx + bw * (1f - 2f * draw)
        drawLine(tertiary, Offset(bx, by), Offset(turn, by + bh * 0.05f), strokeWidth = 3f)
        for (i in 1..3) {
            val ly = by + bh * i / 4.2f
            drawLine(primary.copy(alpha = 0.35f), Offset(bx - bw * 0.8f, ly), Offset(bx - bw * 0.15f, ly), 2f)
            drawLine(primary.copy(alpha = 0.35f), Offset(bx + bw * 0.15f, ly), Offset(bx + bw * 0.8f, ly), 2f)
        }

        // ستاره‌های چشمک‌زن با فاز متفاوت
        val stars = listOf(0.42f to 0.18f, 0.62f to 0.12f, 0.33f to 0.3f, 0.72f to 0.36f, 0.52f to 0.26f)
        stars.forEachIndexed { i, (sx, sy) ->
            val a = 0.35f + 0.65f * ((sin((phase * 2 * PI + i * 1.3).toFloat()) + 1f) / 2f)
            val c = Offset(w * sx, h * sy); val r = 5f + i
            drawLine(tertiary.copy(alpha = a), c - Offset(r, 0f), c + Offset(r, 0f), 2.5f)
            drawLine(tertiary.copy(alpha = a), c - Offset(0f, r), c + Offset(0f, r), 2.5f)
            drawCircle(Color.White.copy(alpha = a), radius = 2f, center = c)
        }
        drawRect(Color.Transparent, size = Size(w, h))
    }
}
