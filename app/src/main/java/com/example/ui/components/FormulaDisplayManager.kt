package com.example.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDirection
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

/**
 * مدیریت اختصاصی فرمول‌های ریاضی و اقتصادی در لایه UI و جهت متن
 * این کلاس مسئولیت اعمال اجباری جهت چپ‌به‌راست (LTR)، استانداردسازی علامت‌های ریاضی
 * و ایجاد حاشیه امن (Padding & Margin) بین فرمول‌ها و متون فارسی را بر عهده دارد.
 */
object FormulaDisplayManager {
    private const val LRI = "\u2066" // Left-to-Right Isolate
    private const val PDI = "\u2069" // Pop Directional Isolate
    private const val LRM = "\u200E" // Left-to-Right Mark
    private const val MATH_MINUS = '−' // U+2212 Unicode Math Minus

    /**
     * استانداردسازی عبارت فرمول (ریاضی/اقتصادی) با ایجاد فواصل مناسب اطراف عملگرها
     */
    fun formatFormulaString(rawFormula: String): String {
        if (rawFormula.isBlank()) return rawFormula

        var result = rawFormula
            .replace("===>", " ⟹ ")
            .replace("--->", " ⟹ ")
            .replace("-->", " ⟹ ")
            .replace("->", " ⟹ ")
            .replace("*", " × ")
            .replace("/", " ÷ ")

        // Replace hyphens used as minus signs with true mathematical minus
        result = result.replace(Regex("(\\d|[a-zA-Z\\)\\s])-(\\d|[a-zA-Z\\(\\s])"), "$1 $MATH_MINUS $2")

        // Ensure spacing around math operators (+, -, =, ×, ÷, ⟹)
        result = result
            .replace("+", " + ")
            .replace(MATH_MINUS.toString(), " $MATH_MINUS ")
            .replace("=", " = ")
            .replace("×", " × ")
            .replace("÷", " ÷ ")
            .replace(Regex("\\s+"), " ")
            .trim()

        // Clean up spaces inside parentheses or adjacent to unary minus e.g. "( − x )" -> "(−x)"
        result = result
            .replace("( $MATH_MINUS ", "($MATH_MINUS")
            .replace("( + ", "(+")
            .replace(" $MATH_MINUS )", "$MATH_MINUS)")
            .replace(" = $MATH_MINUS ", " = $MATH_MINUS")

        return result
    }

    /**
     * ایزوله‌سازی LTR کامل با استفاده از Unicode Isolates
     * جهت قرارگیری در دل متون فارسی بدون اختلال در برعکس شدن پرانتزها و منهای منفی.
     */
    fun isolateLtrFormula(rawFormula: String): String {
        val formatted = formatFormulaString(rawFormula)
        return " $LRI$LRM$formatted$LRM$PDI "
    }

    /**
     * تشخیص اینکه آیا متن ورودی یک فرمول ریاضی یا اقتصادی است
     */
    fun isFormula(text: String): Boolean {
        val mathSymbols = setOf('+', MATH_MINUS, '-', '=', '×', '÷', '*', '/', '^', '√', '∫', '∑', 'Δ', 'π', '≤', '≥', '≠', '∈', '⟹')
        if (text.any { it in mathSymbols }) return true
        if (text.contains("ax²") || text.contains("x²") || text.contains("y²") || text.contains("GDP") || text.contains("TR") || text.contains("TC")) return true
        return false
    }
}

/**
 * کامپوننت اختصاصی جهت نمایش فرمول‌های ریاضی و اقتصادی در جهت اجباری LTR
 * همراه با فاصله امن (Padding)، فونت منواسپیس خوانا و حاشیه ملایم تا فرمول به متن فارسی نچسبد.
 */
@Composable
fun IsolatedFormulaChip(
    formula: String,
    modifier: Modifier = Modifier,
    accentColor: Color = MaterialTheme.colorScheme.primary,
    textStyle: TextStyle = MaterialTheme.typography.bodyMedium,
    isInline: Boolean = true
) {
    SvgVectorMathFormulaDiagram(
        formula = formula,
        modifier = modifier
            .padding(horizontal = if (isInline) 4.dp else 0.dp, vertical = 2.dp)
            .testTag("isolated_formula_chip"),
        accentColor = accentColor
    )
}

/**
 * کارت اختصاصی نمایش فرمول ریاضی/اقتصادی همراه با برچسب فارسی و فرمول جداگانه LTR
 */
@Composable
fun MathAndEconomicsFormulaCard(
    labelFa: String,
    formula: String,
    modifier: Modifier = Modifier,
    accentColor: Color = MaterialTheme.colorScheme.primary
) {
    Surface(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        shape = RoundedCornerShape(10.dp),
        color = MaterialTheme.colorScheme.surface,
        border = androidx.compose.foundation.BorderStroke(1.dp, accentColor.copy(alpha = 0.3f))
    ) {
        Column(
            modifier = Modifier.padding(10.dp)
        ) {
            if (labelFa.isNotBlank()) {
                BiDiText(
                    text = labelFa,
                    style = MaterialTheme.typography.labelSmall,
                    fontWeight = FontWeight.Bold,
                    color = accentColor,
                    modifier = Modifier.padding(bottom = 6.dp)
                )
            }

            IsolatedFormulaChip(
                formula = formula,
                accentColor = accentColor,
                isInline = false,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}
