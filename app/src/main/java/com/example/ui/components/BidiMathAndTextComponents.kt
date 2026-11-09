package com.example.ui.components

import java.util.Collections
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.graphics.luminance
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontSynthesis
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.sp
import androidx.compose.ui.text.style.TextDirection
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.curriculum.FlashcardItem
import com.example.data.curriculum.MiniCheckQuestion
import com.example.data.curriculum.QuizQuestionItem
import com.example.data.curriculum.WorkedExample
import com.example.data.curriculum.ZeroLesson
import com.example.data.curriculum.ZeroStep
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.Calculate
import androidx.compose.material3.Icon
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.drawscope.Stroke

/**
 * Universal BiDi & Persian/Math/English Typography Formatter with O(1) LRU Memoization
 * and live Persian (`۱۲۳`) / English (`123`) digit switching.
 */
object BidiFormatter {
    private const val LRI = "\u2066" // Left-to-Right Isolate
    private const val PDI = "\u2069" // Pop Directional Isolate
    private const val LRO = "\u202D" // Left-to-Right Override (forces strong L on digits, parens & operators)
    private const val PDF = "\u202C" // Pop Directional Formatting
    private const val LRM = "\u200E" // Left-to-Right Mark
    private const val RLM = "\u200F" // Right-to-Left Mark
    private const val MATH_MINUS = '−' // U+2212 true mathematical minus

    private val persianDigits = charArrayOf('۰', '۱', '۲', '۳', '۴', '۵', '۶', '۷', '۸', '۹')
    private val memoCache: MutableMap<String, String> = Collections.synchronizedMap(
        object : LinkedHashMap<String, String>(4096, 0.75f, true) {
            override fun removeEldestEntry(eldest: MutableMap.MutableEntry<String, String>?): Boolean = size > 4096
        }
    )

    var usePersianDigits by mutableStateOf(true)
        private set

    fun setDigitMode(persian: Boolean) {
        if (usePersianDigits != persian) {
            usePersianDigits = persian
            memoCache.clear()
        }
    }

    fun toPersianDigits(input: String, forcePersian: Boolean = false): String {
        if (!forcePersian && !usePersianDigits) {
            return toEnglishDigits(input)
        }
        val sb = StringBuilder(input.length)
        for (ch in input) {
            when (ch) {
                in '0'..'9' -> sb.append(persianDigits[ch - '0'])
                in '٠'..'٩' -> sb.append(persianDigits[ch - '٠'])
                else -> sb.append(ch)
            }
        }
        return sb.toString()
    }

    fun toEnglishDigits(input: String): String {
        val sb = StringBuilder(input.length)
        for (ch in input) {
            when (ch) {
                in '۰'..'۹' -> sb.append(('0'.code + (ch - '۰')).toChar())
                in '٠'..'٩' -> sb.append(('0'.code + (ch - '٠')).toChar())
                else -> sb.append(ch)
            }
        }
        return sb.toString()
    }

    fun applyPreferredDigits(input: String, persian: Boolean = usePersianDigits): String {
        return if (persian) toPersianDigits(input) else toEnglishDigits(input)
    }

    /**
     * Splits chained math equations (separated by `|` or `⟹`) onto individual clean lines
     * and separates equations from long Persian prefixes ending with `:` so formulas never
     * stick together or break mid-equation across lines.
     */
    private fun splitChainedFormulasIntoCleanLines(input: String): String {
        return input.lines().joinToString("\n") { rawLine ->
            val line = balanceUnclosedParenthesesInLine(rawLine)
            val trimmed = line.trim()
            // Preserve ASCII diagram boxes and pure text tables
            if (trimmed.isEmpty() ||
                trimmed.startsWith("┌") ||
                trimmed.startsWith("│") ||
                trimmed.startsWith("├") ||
                trimmed.startsWith("└")
            ) {
                line
            } else {
                val hasMathEquation = line.contains('=') && line.any { it in "+−-×÷" } &&
                    line.any { it in '0'..'9' || it in '۰'..'۹' }
                if (!hasMathEquation) {
                    line
                } else {
                    processMathEquationLineSeparation(line)
                }
            }
        }
    }

    private fun processMathEquationLineSeparation(line: String): String {
        var working = line
        // 1. If there is a Persian label before ':' followed by a math equation (e.g. "فرمول دو برابر کن به علاوه یک: ۹۵ × ۲ = ۱۹۰")
        val lastColonIdx = working.lastIndexOf(':')
        var prefixHeader = ""
        var mathBody = working
        if (lastColonIdx in 3 until (working.length - 4)) {
            val beforeColon = working.substring(0, lastColonIdx + 1).trim()
            val afterColon = working.substring(lastColonIdx + 1).trim()
            val beforeHasPersian = beforeColon.any { isPersianOrArabicLetterOrPunct(it) }
            val afterHasMathEq = afterColon.contains('=') && afterColon.any { it in "+−-×÷" }
            if (beforeHasPersian && afterHasMathEq) {
                prefixHeader = beforeColon
                mathBody = afterColon
            }
        }

        // 2. If mathBody has chained steps separated by '|' or '⟹', split them into distinct lines
        val hasPipeSteps = mathBody.contains('|') && mathBody.split('|').count { part ->
            part.contains('=') || part.any { it in "+−-×÷" }
        } >= 2

        val steps = mutableListOf<String>()
        if (hasPipeSteps) {
            val pipeParts = mathBody.split('|').map { it.trim() }.filter { it.isNotEmpty() }
            for (part in pipeParts) {
                if (part.contains('⟹') && part.split('⟹').all { sub -> sub.any { it in '0'..'9' || it in '۰'..'۹' } }) {
                    val arrowParts = part.split('⟹').map { it.trim() }.filter { it.isNotEmpty() }
                    arrowParts.forEachIndexed { idx, ap ->
                        if (idx == 0) steps.add(ap) else steps.add("نتیجه ⟵ $ap")
                    }
                } else {
                    steps.add(part)
                }
            }
        } else if (mathBody.contains('⟹') && mathBody.count { it == '=' } >= 2) {
            val arrowParts = mathBody.split('⟹').map { it.trim() }.filter { it.isNotEmpty() }
            arrowParts.forEachIndexed { idx, ap ->
                if (idx == 0) steps.add(ap) else steps.add("نتیجه ⟵ $ap")
            }
        }

        return when {
            steps.size >= 2 -> {
                val formattedSteps = steps.mapIndexed { idx, s ->
                    val stepNum = persianDigits[(idx + 1).coerceAtMost(9)]
                    "گام $stepNum: $s"
                }.joinToString("\n")
                if (prefixHeader.isNotEmpty()) {
                    "$prefixHeader\n$formattedSteps"
                } else {
                    formattedSteps
                }
            }
            prefixHeader.isNotEmpty() && prefixHeader.length >= 14 -> {
                "$prefixHeader\n$mathBody"
            }
            else -> working
        }
    }

    /**
     * Automatically balances any unclosed '(' inside Persian guillemets «...» or at the end of a line
     * so truncated parentheses never invert following bracket pairs.
     */
    private fun balanceUnclosedParenthesesInLine(line: String): String {
        if (!line.contains('(') && !line.contains(')')) return line
        val sb = StringBuilder(line.length + 4)
        var openCount = 0
        for (i in line.indices) {
            val c = line[i]
            when (c) {
                '(' -> {
                    openCount++
                    sb.append(c)
                }
                ')' -> {
                    if (openCount > 0) {
                        openCount--
                        sb.append(c)
                    } else {
                        sb.append(c)
                    }
                }
                '»' -> {
                    while (openCount > 0) {
                        sb.append(')')
                        openCount--
                    }
                    sb.append(c)
                }
                else -> sb.append(c)
            }
        }
        while (openCount > 0) {
            sb.append(')')
            openCount--
        }
        return sb.toString()
    }

    /**
     * Formats any Persian/mixed string with O(1) LruCache lookup:
     * 1. Strips any stray HTML `<u>` / `</u>` tags.
     * 2. Converts en-dash `–` or hyphen `-` used as a math minus into `−` (`U+2212`).
     * 3. Converts digits to Persian (`۰-۹`) or English (`0-9`) according to user preference.
     * 4. Separates multi-step equations onto distinct lines and wraps every LTR/math island
     *    in `\u2066\u202D...\u202C\u2069` while keeping Persian/mixed parentheses properly paired in RTL.
     */
    private val prophetHonorificRegex = Regex(
        "(پیامبر\\s*اکرم|پیامبر\\s*اسلام|رسول\\s*خدا|رسول\\s*اکرم|حضرت\\s*محمد|پیامبر\\s*گرامی)(?!\\s*(\\(|ص|صل))"
    )
    private val imamHonorificRegex = Regex(
        "(امام\\s*علی|حضرت\\s*علی|امیرالمؤمنین|امیرالمومنین|امام\\s*حسن\\s*مجتبی|امام\\s*حسن\\s*عسکری|امام\\s*حسن|امام\\s*حسین|سیدالشهدا|حضرت\\s*ابوالفضل|امام\\s*سجاد|امام\\s*زین\\u200Cالعابدین|امام\\s*باقر|امام\\s*محمد\\s*باقر|امام\\s*صادق|امام\\s*جعفر\\s*صادق|امام\\s*کاظم|امام\\s*موسی\\s*کاظم|امام\\s*رضا|امام\\s*جواد|امام\\s*هادی|امام\\s*عسکری|ائمه\\s*اطهار|ائمه\\s*معصومین)(?!\\s*(\\(|ع|علیه))"
    )
    private val fatimaHonorificRegex = Regex(
        "(حضرت\\s*فاطمه\\s*زهرا|حضرت\\s*فاطمه|حضرت\\s*زهرا|فاطمه\\s*زهرا)(?!\\s*(\\(|س|ع|سلام))"
    )
    private val mahdiHonorificRegex = Regex(
        "(امام\\s*زمان|حضرت\\s*مهدی|امام\\s*عصر|ولی\\s*عصر|حجة\\s*بن\\s*الحسن)(?!\\s*(\\(|عج|ع))"
    )

    fun ensureSacredHonorifics(input: String): String {
        if (input.length < 6) return input
        if (!input.contains("پیامبر") && !input.contains("رسول") && !input.contains("حضرت") &&
            !input.contains("امام") && !input.contains("امیرالم") && !input.contains("سیدالشهدا") &&
            !input.contains("فاطمه") && !input.contains("ائمه") && !input.contains("ولی عصر")
        ) {
            return input
        }
        var out = prophetHonorificRegex.replace(input, "$1 (ص)")
        out = fatimaHonorificRegex.replace(out, "$1 (س)")
        out = mahdiHonorificRegex.replace(out, "$1 (عج)")
        out = imamHonorificRegex.replace(out, "$1 (ع)")
        return out
    }

    fun formatMixedPersianText(raw: String, persianDigitsMode: Boolean = usePersianDigits): String {
        if (raw.isEmpty()) return raw
        val cacheKey = "${if (persianDigitsMode) 'P' else 'E'}:$raw"
        memoCache[cacheKey]?.let { return it }

        val withoutHtmlTags = ensureSacredHonorifics(
            raw
                .replace("<u>", "")
                .replace("</u>", "")
                .replace("<U>", "")
                .replace("</U>", "")
        )
        val arabicProcessed = ArabicTextProcessor.processArabicText(withoutHtmlTags)
        val cleaned = convertCaretsToSuperscripts(arabicProcessed)
            .replace("===>", " ⟹ ")
            .replace("--->", " ⟹ ")
            .replace("-->", " ⟹ ")
            .replace("->", " ⟹ ")

        val separated = splitChainedFormulasIntoCleanLines(cleaned)

        val result = separated.lines().joinToString("\n") { line ->
            formatSingleMixedLine(line, persianDigitsMode)
        }
        memoCache[cacheKey] = result
        return result
    }

    private fun formatSingleMixedLine(line: String, persianDigitsMode: Boolean): String {
        if (line.isBlank()) return line
        if (isPredominantlyLtrFormulaOrEnglish(line)) {
            return formatLtrFormula(line, persianDigitsMode)
        }
        val normalized = normalizeDigitsAndMinusInMixedLine(line, persianDigitsMode)
        return wrapMathAndSignedIslands(normalized)
    }

    private fun isProsodyLine(line: String): Boolean {
        return line.contains("U") && (line.contains("–") || line.contains("—") || line.contains("- U") || line.contains("U -"))
    }

    /**
     * Finds the matching closing bracket (`)`, `]`, `}`) for an opening bracket at `openIdx`.
     */
    private fun findMatchingCloseBracket(line: String, openIdx: Int): Int {
        val openCh = line[openIdx]
        val closeCh = when (openCh) {
            '(' -> ')'
            '[' -> ']'
            '{' -> '}'
            else -> return -1
        }
        var depth = 0
        for (k in openIdx until line.length) {
            when (line[k]) {
                openCh -> depth++
                closeCh -> {
                    depth--
                    if (depth == 0) return k
                }
            }
        }
        return -1
    }

    /**
     * Returns the index of the matching closing bracket ONLY if the bracketed group at `openIdx`
     * contains zero Persian/Arabic characters and at least one Latin/Math character (e.g. `(verb)`, `(Infinitive)`, `(x - 2)`).
     * Returns -1 if the bracketed group contains any Persian/Arabic text (e.g. `(وِرب)`, `(اسم مصدر / Gerund)`).
     */
    private fun isPureLtrBracketGroup(line: String, openIdx: Int): Int {
        if (openIdx >= line.length || line[openIdx] !in "([{") return -1
        val closeIdx = findMatchingCloseBracket(line, openIdx)
        if (closeIdx == -1 || closeIdx <= openIdx + 1) return -1
        val inside = line.substring(openIdx + 1, closeIdx)
        val hasPersianOrArabic = inside.any { isPersianOrArabicLetterOrPunct(it) }
        val hasMathOrLatin = inside.any { isCoreLtrChar(it) || isMathOrLtrConnector(it) }
        return if (!hasPersianOrArabic && hasMathOrLatin) closeIdx else -1
    }

    /**
     * Wraps an LTR math or English island in LRI + LRO + ... + PDF + PDI so that:
     * - Parentheses in `(verb)`, `(Infinitive)`, `(x + 1)` NEVER invert to `)verb(`.
     * - Persian digits `۰-۹` and math operators `×`, `+`, `−`, `=` in `۵ × ۲ + ۱ = ۱۱` NEVER reverse to `۱۱ = ۱ + ۲ × ۵`.
     * - Spaces around math operators in compact equations use non-breaking spaces `\u00A0` so equations never split mid-operation.
     */
    private fun wrapLtrIslandWithStrongOverride(island: String): String {
        if (island.isEmpty()) return ""
        if (island.any { isPersianOrArabicLetterOrPunct(it) }) {
            return island
        }
        val isMathExpr = island.any { it in "=×÷+−" } && island.any { it in '0'..'9' || it in '۰'..'۹' }
        val lockedIsland = if (isMathExpr && island.length <= 48) {
            island
                .replace(" × ", "\u00A0×\u00A0")
                .replace(" ÷ ", "\u00A0÷\u00A0")
                .replace(" + ", "\u00A0+\u00A0")
                .replace(" − ", "\u00A0−\u00A0")
                .replace(" = ", "\u00A0=\u00A0")
        } else {
            island
        }
        return "$LRI$LRO$lockedIsland$PDF$PDI"
    }

    /**
     * Checks if a digit sequence starting at `idx` is the beginning of a mathematical expression
     * (e.g. `2x + 3 = 7`, `۲x² − ۵x = ۰`, `۳ × ۴ = ۱۲`, `۲³ × ۲⁴ = ۲⁷`) rather than a plain prose number.
     */
    private fun isStartOfDigitLedMathIsland(line: String, idx: Int): Boolean {
        val c = line[idx]
        if (c !in '0'..'9' && c !in '۰'..'۹') return false
        var p = idx
        while (p < line.length && (line[p] in '0'..'9' || line[p] in '۰'..'۹')) p++
        if (p < line.length && (line[p] == '.' || line[p] == ')' || line[p] == '-')) {
            val afterPunct = skipSpaces(line, p + 1)
            if (afterPunct >= line.length || isPersianOrArabicLetterOrPunct(line[afterPunct])) {
                return false
            }
        }
        if (p < line.length) {
            val nextCh = line[p]
            if (nextCh in 'a'..'z' || nextCh in 'A'..'Z' || nextCh in "²³⁴⁵⁶⁷⁸⁹⁰ⁿᵐᵏ⁺⁻√Δπ!") {
                return true
            }
            val afterSpace = skipSpaces(line, p)
            if (afterSpace < line.length && line[afterSpace] in "+−=×÷<>≤≥≠≈") {
                return true
            }
        }
        return false
    }

    /**
     * Advances `end` across a contiguous LTR/Math island starting from `initialEnd`,
     * ONLY consuming bracket groups if they are 100% pure LTR (`isPureLtrBracketGroup != -1`)
     * and NEVER consuming standalone `(` or `)` that belong to the surrounding Persian/Arabic sentence.
     */
    private fun scanLtrIslandEnd(line: String, initialEnd: Int): Int {
        val n = line.length
        var end = initialEnd
        while (end < n) {
            val ch = line[end]
            if (isPersianOrArabicLetterOrPunct(ch)) break
            if (ch in "([{") {
                val closeB = isPureLtrBracketGroup(line, end)
                if (closeB != -1) {
                    end = closeB + 1
                    continue
                } else {
                    break
                }
            }
            if (ch in ")]}") {
                // Standalone closing bracket belongs to outer RTL group — never swallow it!
                break
            }
            if (isCoreLtrChar(ch) || isMathOrLtrConnector(ch) || isPunctInMath(ch) || ch in "ℕ𝕎ℤℚℝσ̄⁺⁻⁼⁽⁾") {
                end++
            } else if (ch == ' ') {
                val nextIdx = skipSpaces(line, end)
                if (nextIdx < n && !isPersianOrArabicLetterOrPunct(line[nextIdx])) {
                    val nextCh = line[nextIdx]
                    if (nextCh in "([{") {
                        val closeB = isPureLtrBracketGroup(line, nextIdx)
                        if (closeB != -1) {
                            end = closeB + 1
                            continue
                        } else {
                            break
                        }
                    } else if (isCoreLtrChar(nextCh) || isMathOrLtrConnector(nextCh) || nextCh in "ℕ𝕎ℤℚℝ") {
                        end = nextIdx
                    } else {
                        break
                    }
                } else {
                    break
                }
            } else {
                break
            }
        }
        return end
    }

    private fun wrapMathAndSignedIslands(line: String): String {
        if (isProsodyLine(line)) {
            return line
        }
        val hasExplicitMathOrLatinOrParens = line.any {
            it in "=×÷^√Δπ()" || it in 'a'..'z' || it in 'A'..'Z' || it == MATH_MINUS
        }
        if (!hasExplicitMathOrLatinOrParens) {
            return line
        }
        val n = line.length
        val sb = StringBuilder(n + 48)
        // Track closing brackets of Persian/mixed parenthesized groups so they also get RLM anchors
        val rtlClosingBrackets = BooleanArray(n)
        var i = 0
        while (i < n) {
            val c = line[i]

            if (rtlClosingBrackets[i]) {
                sb.append(RLM).append(c).append(RLM)
                i++
                continue
            }

            // Case 1: Signed number or signed math expression (−۵, −2x + 3 = 0)
            if ((c == MATH_MINUS || c == '+') && i + 1 < n &&
                (line[i + 1] in '۰'..'۹' || line[i + 1] in '0'..'9' || line[i + 1] in 'a'..'z' || line[i + 1] in 'A'..'Z')
            ) {
                val prev = if (i > 0) line[i - 1] else ' '
                if (prev == ' ' || prev in "([{،:؛=" || i == 0) {
                    val end = scanLtrIslandEnd(line, i + 1)
                    var trimmedEnd = end
                    while (trimmedEnd > i && (line[trimmedEnd - 1] == ' ' || line[trimmedEnd - 1] == ':' || line[trimmedEnd - 1] == '.')) {
                        trimmedEnd--
                    }
                    val island = line.substring(i, trimmedEnd)
                    sb.append(wrapLtrIslandWithStrongOverride(island))
                    if (trimmedEnd < end) sb.append(line, trimmedEnd, end)
                    i = end
                    continue
                }
            }

            // Case 1B: Opening Bracket '(', '[', '{'
            if (c == '(' || c == '[' || c == '{') {
                val closeIdx = isPureLtrBracketGroup(line, i)
                if (closeIdx != -1) {
                    // Pure LTR/Math bracket group like (verb), (Infinitive), (Simple Present), (x - 2), (۱ + ۲)² = ۹
                    val end = scanLtrIslandEnd(line, closeIdx + 1)
                    var trimmedEnd = end
                    while (trimmedEnd > i && (line[trimmedEnd - 1] == ' ' || line[trimmedEnd - 1] == ':')) {
                        trimmedEnd--
                    }
                    val fullIsland = line.substring(i, trimmedEnd)
                    sb.append(wrapLtrIslandWithStrongOverride(fullIsland))
                    if (trimmedEnd < end) sb.append(line, trimmedEnd, end)
                    i = end
                    continue
                } else {
                    // Persian or Mixed Persian+English bracket group like (وِرب), (اسم مصدر / Gerund), (Verb، Subject و...)
                    // Anchor both opening and closing brackets in RTL with RLM so they ALWAYS pair & mirror properly in RTL!
                    val matchClose = findMatchingCloseBracket(line, i)
                    if (matchClose != -1) {
                        rtlClosingBrackets[matchClose] = true
                        sb.append(RLM).append(c).append(RLM)
                        i++
                        continue
                    }
                }
            }

            // Case 1C: Numeric fractions like ۱/۲, 3/4, −۳/۴
            if ((c in '0'..'9' || c in '۰'..'۹') && (i == 0 || line[i - 1] in " ([\n،؛:")) {
                var slashIdx = -1
                var end = i + 1
                while (end < n && (line[end] in '0'..'9' || line[end] in '۰'..'۹' || line[end] == '/')) {
                    if (line[end] == '/') {
                        if (slashIdx != -1) break
                        slashIdx = end
                    }
                    end++
                }
                if (slashIdx != -1 && slashIdx > i && slashIdx < end - 1) {
                    val fraction = line.substring(i, end)
                    sb.append(wrapLtrIslandWithStrongOverride(fraction))
                    i = end
                    continue
                }
            }

            // Case 2: Math/English island starting with a Latin letter, math symbol, or digit-led equation (e.g. 2x + 3 = 7, ۵ × ۲ + ۱ = ۱۱)
            if (c in 'a'..'z' || c in 'A'..'Z' || c in "ℕ𝕎ℤℚℝ√Δπσx̄" || isStartOfDigitLedMathIsland(line, i)) {
                val start = i
                val end = scanLtrIslandEnd(line, i + 1)
                var trimmedEnd = end
                while (trimmedEnd > start && (line[trimmedEnd - 1] == ' ' || line[trimmedEnd - 1] == ':' || line[trimmedEnd - 1] == '.')) {
                    trimmedEnd--
                }
                val island = line.substring(start, trimmedEnd)
                if (island.isNotEmpty()) {
                    sb.append(wrapLtrIslandWithStrongOverride(island))
                }
                if (trimmedEnd < end) {
                    sb.append(line, trimmedEnd, end)
                }
                i = end
                continue
            }

            sb.append(c)
            i++
        }
        return sb.toString()
    }

    private fun isDashChar(c: Char): Boolean {
        return c == '-' || c == '–' || c == '−' || c == '—'
    }

    private fun normalizeDigitsAndMinusInMixedLine(input: String, persianDigitsMode: Boolean): String {
        val chars = input.toCharArray()
        val out = StringBuilder(chars.size)
        for (i in chars.indices) {
            val c = chars[i]
            if (isDashChar(c)) {
                val nextNonSpace = chars.drop(i + 1).firstOrNull { !it.isWhitespace() }
                val prevNonSpace = if (i > 0) chars.take(i).lastOrNull { !it.isWhitespace() } else null

                val isProsodyLongSign = (nextNonSpace == 'U' || prevNonSpace == 'U') ||
                    (prevNonSpace == '(' && nextNonSpace == ')' && input.contains("هجا"))

                val isBullet = (i == 0 || prevNonSpace == null) &&
                    (nextNonSpace != null && isPersianOrArabicLetterOrPunct(nextNonSpace))

                val isMathMinus = !isProsodyLongSign && !isBullet && (
                    nextNonSpace != null && (nextNonSpace in '0'..'9' || nextNonSpace in '۰'..'۹' || isCoreLtrChar(nextNonSpace))
                )

                when {
                    isBullet -> out.append('•')
                    isMathMinus -> out.append(MATH_MINUS)
                    else -> out.append(c)
                }
            } else if (c in '0'..'9' || c in '۰'..'۹' || c in '٠'..'٩') {
                val digitValue = when (c) {
                    in '0'..'9' -> c - '0'
                    in '۰'..'۹' -> c - '۰'
                    else -> c - '٠'
                }
                if (!persianDigitsMode) {
                    out.append(('0'.code + digitValue).toChar())
                } else {
                    out.append(persianDigits[digitValue])
                }
            } else {
                out.append(c)
            }
        }
        return out.toString()
    }

    fun isPersianOrArabicLetterOrPunct(c: Char): Boolean {
        if (c in '۰'..'۹' || c in '٠'..'٩') return false
        return c in '\u0600'..'\u06FF' ||
               c in '\u0750'..'\u077F' ||
               c in '\u08A0'..'\u08FF' ||
               c in '\uFB50'..'\uFDFF' ||
               c in '\uFE70'..'\uFEFF' ||
               c == '\u200C' || c == '\u200D' ||
               c == '«' || c == '»' || c == '•' || c == '،' || c == '؛' || c == '؟'
    }

    private fun isLatinOrSubscript(c: Char): Boolean {
        return c in 'a'..'z' || c in 'A'..'Z' || c in "₀₁₂₃₄₅₆₇₈₉ₘₙₖ"
    }

    private fun isCoreLtrChar(c: Char): Boolean {
        return c in 'a'..'z' ||
            c in 'A'..'Z' ||
            c in '0'..'9' ||
            c in '۰'..'۹' ||
            c in "²³⁴⁵⁶⁷⁸⁹⁰ⁿᵐᵏ₀₁₂₃₄₅₆₇₈₉ₘₙ√Δπ"
    }

    private fun isMathOrLtrConnector(c: Char): Boolean {
        return c in "+−=×÷*/^<>≤≥≠≈≡∪∩⊆⊂∈∉⇔!%"
    }

    private fun isPunctInMath(c: Char): Boolean {
        return c in ",.;:'\""
    }

    private fun skipSpaces(s: String, from: Int): Int {
        var i = from
        while (i < s.length && s[i] == ' ') i++
        return i
    }

    private fun toSuperscriptChar(c: Char): Char? = when (c) {
        '0', '۰', '٠' -> '⁰'
        '1', '۱', '١' -> '¹'
        '2', '۲', '٢' -> '²'
        '3', '۳', '٣' -> '³'
        '4', '۴', '٤' -> '⁴'
        '5', '۵', '٥' -> '⁵'
        '6', '۶', '٦' -> '⁶'
        '7', '۷', '٧' -> '⁷'
        '8', '۸', '٨' -> '⁸'
        '9', '۹', '٩' -> '⁹'
        '+' -> '⁺'
        '-', '−', '–' -> '⁻'
        '=' -> '⁼'
        '(' -> '⁽'
        ')' -> '⁾'
        'n' -> 'ⁿ'
        'm' -> 'ᵐ'
        'k' -> 'ᵏ'
        'x' -> 'ˣ'
        'y' -> 'ʸ'
        else -> null
    }

    private fun toSubscriptChar(c: Char): Char? = when (c) {
        '0', '۰', '٠' -> '₀'
        '1', '۱', '١' -> '₁'
        '2', '۲', '٢' -> '₂'
        '3', '۳', '٣' -> '₃'
        '4', '۴', '٤' -> '₄'
        '5', '۵', '٥' -> '₅'
        '6', '۶', '٦' -> '₆'
        '7', '۷', '٧' -> '₇'
        '8', '۸', '٨' -> '₈'
        '9', '۹', '٩' -> '₉'
        '+' -> '₊'
        '-', '−', '–' -> '₋'
        'n' -> 'ₙ'
        'm' -> 'ₘ'
        'k' -> 'ₖ'
        'i' -> 'ᵢ'
        'j' -> 'ⱼ'
        'x' -> 'ₓ'
        else -> null
    }

    /**
     * Converts raw caret exponents (`a^2`, `a^(m+n)`, `q^(n-1)`) and raw subscripts (`a_n`, `a_1`, `S_n`, `a_20`)
     * into real Unicode superscripts (`a²`, `aᵐ⁺ⁿ`, `qⁿ⁻¹`) and subscripts (`aₙ`, `a₁`, `Sₙ`, `a₂₀`).
     */
    fun convertCaretsToSuperscripts(input: String): String {
        if (!input.contains('^') && !input.contains('_')) return input
        val sb = StringBuilder(input.length)
        var i = 0
        while (i < input.length) {
            if (input[i] == '^' && i + 1 < input.length) {
                if (input[i + 1] == '(') {
                    val closeIdx = input.indexOf(')', i + 2)
                    if (closeIdx != -1 && closeIdx - (i + 2) <= 12) {
                        val inside = input.substring(i + 2, closeIdx)
                        val mapped = inside.map { ch ->
                            if (ch == ' ') null else toSuperscriptChar(ch)
                        }
                        if (mapped.none { it == null } && inside.isNotBlank()) {
                            for (ch in inside) {
                                if (ch != ' ') sb.append(toSuperscriptChar(ch)!!)
                            }
                            i = closeIdx + 1
                            continue
                        }
                    }
                } else {
                    var j = i + 1
                    val superChars = StringBuilder()
                    while (j < input.length) {
                        val sc = toSuperscriptChar(input[j])
                        if (sc != null && input[j] !in "()+-−–=") {
                            superChars.append(sc)
                            j++
                        } else {
                            break
                        }
                    }
                    if (superChars.isNotEmpty()) {
                        sb.append(superChars)
                        i = j
                        continue
                    }
                }
            } else if (input[i] == '_' && i > 0 && i + 1 < input.length &&
                (input[i - 1] in 'a'..'z' || input[i - 1] in 'A'..'Z')
            ) {
                var j = i + 1
                val subChars = StringBuilder()
                while (j < input.length) {
                    val sc = toSubscriptChar(input[j])
                    if (sc != null && input[j] !in "+-−–") {
                        subChars.append(sc)
                        j++
                    } else {
                        break
                    }
                }
                // Only convert if it's a math subscript (not a snake_case identifier like math_01)
                val nextCharAfterSub = if (j < input.length) input[j] else ' '
                if (subChars.isNotEmpty() && nextCharAfterSub != '_' && nextCharAfterSub !in 'a'..'z' && nextCharAfterSub !in 'A'..'Z') {
                    sb.append(subChars)
                    i = j
                    continue
                }
            }
            sb.append(input[i])
            i++
        }
        return sb.toString()
    }

    fun formatLtrFormula(raw: String, persianDigitsMode: Boolean = usePersianDigits): String {
        val withSuperscripts = convertCaretsToSuperscripts(raw)
        val base = withSuperscripts
            .replace(LRI, "")
            .replace(PDI, "")
            .replace(LRM, "")
            .replace("===>", " ⟹ ")
            .replace("--->", " ⟹ ")
            .replace("-->", " ⟹ ")
            .replace("->", " ⟹ ")
            .replace('-', MATH_MINUS)
            .replace('–', MATH_MINUS)
            .trim()
        // Check if formula uses Latin variables (x, y, a, b, c, n, P, A, B, S, etc.)
        val hasLatinVariables = base.any { it in 'a'..'z' || it in 'A'..'Z' }
        return if (hasLatinVariables || !persianDigitsMode) {
            toEnglishDigits(base)
        } else {
            toPersianDigits(base)
        }
    }

    fun isPredominantlyLtrFormulaOrEnglish(line: String): Boolean {
        val trimmed = line
            .replace(LRI, "")
            .replace(PDI, "")
            .replace(LRM, "")
            .trim()
        if (trimmed.isEmpty()) return false
        if (trimmed.contains("U") && trimmed.contains("–") && !trimmed.contains("=")) return false

        val persianCount = trimmed.count { isPersianOrArabicLetterOrPunct(it) }
        val latinOrMathCount = trimmed.count {
            it in 'a'..'z' || it in 'A'..'Z' || it in '0'..'9' || it in '۰'..'۹' ||
                it in "=+−-–×÷*/^√∪∩⊆⟹⇔()[]{}<>≤≥≠!Δ²³⁴⁵⁶⁷⁸⁹⁰ⁿᵐᵏ₀₁₂₃₄₅₆₇₈₉"
        }
        return persianCount == 0 && latinOrMathCount > 0
    }

    fun formatZeroLesson(lesson: ZeroLesson, persianDigitsMode: Boolean = usePersianDigits): ZeroLesson = lesson.copy(
        levelBadge = formatMixedPersianText( convertCaretsToSuperscripts(lesson.levelBadge), persianDigitsMode),
        title = formatMixedPersianText(convertCaretsToSuperscripts(lesson.title), persianDigitsMode),
        subtitle = formatMixedPersianText(convertCaretsToSuperscripts(lesson.subtitle), persianDigitsMode),
        whyStudentsStruggle = formatMixedPersianText(convertCaretsToSuperscripts(lesson.whyStudentsStruggle), persianDigitsMode),
        steps = lesson.steps.map { step ->
            ZeroStep(
                stepNumber = step.stepNumber,
                title = formatMixedPersianText(convertCaretsToSuperscripts(step.title), persianDigitsMode),
                simpleExplanation = formatMixedPersianText(convertCaretsToSuperscripts(step.simpleExplanation), persianDigitsMode),
                visualDiagramText = formatMixedPersianText(convertCaretsToSuperscripts(step.visualDiagramText), persianDigitsMode),
                memoryTrick = formatMixedPersianText(convertCaretsToSuperscripts(step.memoryTrick), persianDigitsMode)
            )
        },
        goldenFormulas = lesson.goldenFormulas.map { formatMixedPersianText(convertCaretsToSuperscripts(it), persianDigitsMode) },
        workedExamples = lesson.workedExamples.map { ex ->
            WorkedExample(
                question = formatMixedPersianText(convertCaretsToSuperscripts(ex.question), persianDigitsMode),
                stepByStepSolution = formatMixedPersianText(convertCaretsToSuperscripts(ex.stepByStepSolution), persianDigitsMode),
                commonTrapAlert = formatMixedPersianText(convertCaretsToSuperscripts(ex.commonTrapAlert), persianDigitsMode)
            )
        },
        miniCheck = MiniCheckQuestion(
            question = formatMixedPersianText(convertCaretsToSuperscripts(lesson.miniCheck.question), persianDigitsMode),
            options = lesson.miniCheck.options.map { formatMixedPersianText(convertCaretsToSuperscripts(it), persianDigitsMode) },
            correctIndex = lesson.miniCheck.correctIndex,
            explanation = formatMixedPersianText(convertCaretsToSuperscripts(lesson.miniCheck.explanation), persianDigitsMode)
        )
    )

    fun formatFlashcard(card: FlashcardItem, persianDigitsMode: Boolean = usePersianDigits): FlashcardItem = card.copy(
        categoryTag = formatMixedPersianText(convertCaretsToSuperscripts(card.categoryTag), persianDigitsMode),
        frontQuestion = formatMixedPersianText(convertCaretsToSuperscripts(card.frontQuestion), persianDigitsMode),
        backAnswer = formatMixedPersianText(convertCaretsToSuperscripts(card.backAnswer), persianDigitsMode),
        memoryHook = formatMixedPersianText(convertCaretsToSuperscripts(card.memoryHook), persianDigitsMode)
    )

    fun formatQuizQuestion(q: QuizQuestionItem, persianDigitsMode: Boolean = usePersianDigits): QuizQuestionItem = q.copy(
        difficultyTag = formatMixedPersianText(convertCaretsToSuperscripts(q.difficultyTag), persianDigitsMode),
        questionText = formatMixedPersianText(convertCaretsToSuperscripts(q.questionText), persianDigitsMode),
        options = q.options.map { formatMixedPersianText(convertCaretsToSuperscripts(it), persianDigitsMode) },
        zeroLevelSolution = formatMixedPersianText(convertCaretsToSuperscripts(q.zeroLevelSolution), persianDigitsMode),
        shortcutTechnique = formatMixedPersianText(convertCaretsToSuperscripts(q.shortcutTechnique), persianDigitsMode)
    )
}

private fun isArabicDiacriticMark(ch: Char): Boolean {
    return ch in '\u0610'..'\u061A' ||
           ch in '\u064B'..'\u065F' ||
           ch == '\u0670' ||
           ch in '\u06D6'..'\u06ED'
}

private val IMPORTANT_EXAM_KEYWORDS = listOf(
    "نکته طلایی",
    "نکته مهم",
    "نکته کنکوری",
    "تله کنکوری",
    "تله تستی",
    "تله طراح",
    "فرمول طلایی",
    "قانون طلایی",
    "قاعده طلایی",
    "رد گزینه",
    "رمز حافظه",
    "تکنیک فاینمن",
    "بازیابی فعال",
    "مرور فاصله‌دار",
    "نقشه ذهنی",
    "امتحان نهایی",
    "کنکور فرهنگیان",
    "حواست باشد",
    "توجه مهم",
    "جمع‌بندی سریع",
    "نتیجه مهم",
    "گام اول",
    "گام دوم",
    "گام سوم",
    "گام چهارم",
    "پاسخ تشریحی",
    "روش تستی",
    "نکته‌سنجی طراح",
    "نکته‌سنجی",
    "دام طراح",
    "دام تستی",
    "شاه‌کلید",
    "حواست باشه",
    "کلیدواژه",
    "تعریف",
    "فرمول",
    "نتیجه",
    "شرط لازم",
    "تفاوت",
    "شباهت",
    "علت",
    "معلول"
)

private val annotatedStringCache: MutableMap<String, AnnotatedString> = Collections.synchronizedMap(
    object : LinkedHashMap<String, AnnotatedString>(2048, 0.75f, true) {
        override fun removeEldestEntry(eldest: MutableMap.MutableEntry<String, AnnotatedString>?): Boolean = size > 2048
    }
)

private fun buildRichHighlightedPersianText(
    rawFormatted: String,
    isBoldMode: Boolean,
    enableSmartHighlights: Boolean,
    isUnderlineMode: Boolean,
    isHighlighterMode: Boolean,
    isColoredDiacritics: Boolean,
    diacriticsPalette: com.example.ui.theme.DiacriticsColorPalette,
    highlighterPalette: com.example.ui.theme.HighlighterColorPalette,
    underlinePalette: com.example.ui.theme.UnderlineColorPalette,
    isDarkTheme: Boolean,
    primaryEmphasisColor: Color,
    secondaryEmphasisColor: Color,
    highlightBgColor: Color,
    highlightIntensity: Int = 1,
    fontWeightLevel: Int = 0
): AnnotatedString {
    val effectiveHighlightActive = isHighlighterMode && highlightIntensity > 0
    val cacheKey = "$rawFormatted|$isBoldMode|$fontWeightLevel|$enableSmartHighlights|$isUnderlineMode|$effectiveHighlightActive|$highlightIntensity|$isColoredDiacritics|${diacriticsPalette.name}|${highlighterPalette.name}|${underlinePalette.name}|$isDarkTheme|${primaryEmphasisColor.toArgb()}|${secondaryEmphasisColor.toArgb()}"
    annotatedStringCache[cacheKey]?.let { return it }

    // 1. Parse explicit **bold** and __underline__ markers if present
    val explicitBoldRanges = mutableListOf<IntRange>()
    val explicitUnderlineRanges = mutableListOf<IntRange>()

    // First strip **bold**
    val afterBold: String
    if (rawFormatted.contains("**")) {
        val sb = StringBuilder(rawFormatted.length)
        var i = 0
        while (i < rawFormatted.length) {
            if (i + 1 < rawFormatted.length && rawFormatted[i] == '*' && rawFormatted[i + 1] == '*') {
                val closeIdx = rawFormatted.indexOf("**", i + 2)
                if (closeIdx != -1 && closeIdx > i + 2) {
                    val startInClean = sb.length
                    sb.append(rawFormatted, i + 2, closeIdx)
                    val endInClean = sb.length
                    explicitBoldRanges.add(startInClean until endInClean)
                    i = closeIdx + 2
                    continue
                }
            }
            sb.append(rawFormatted[i])
            i++
        }
        afterBold = sb.toString()
    } else {
        afterBold = rawFormatted
    }

    // Next strip __underline__
    val strippedText: String
    if (afterBold.contains("__")) {
        val sb = StringBuilder(afterBold.length)
        var i = 0
        while (i < afterBold.length) {
            if (i + 1 < afterBold.length && afterBold[i] == '_' && afterBold[i + 1] == '_') {
                val closeIdx = afterBold.indexOf("__", i + 2)
                if (closeIdx != -1 && closeIdx > i + 2) {
                    val startInClean = sb.length
                    sb.append(afterBold, i + 2, closeIdx)
                    val endInClean = sb.length
                    explicitUnderlineRanges.add(startInClean until endInClean)
                    i = closeIdx + 2
                    continue
                }
            }
            sb.append(afterBold[i])
            i++
        }
        strippedText = sb.toString()
    } else {
        strippedText = afterBold
    }

    // Resolve user-chosen Highlighter background color and high-contrast foreground text color
    val markerPrimaryBg = if (effectiveHighlightActive) {
        highlighterPalette.resolveBackground(isDarkTheme, highlightIntensity)
    } else {
        Color.Transparent
    }
    val underlineAccentColor = underlinePalette.resolveColor(isDarkTheme)
    val effectiveUnderlineOrKeyColor = if (isUnderlineMode) underlineAccentColor else primaryEmphasisColor
    val textOnMarker = if (effectiveHighlightActive) {
        highlighterPalette.resolveTextColor(isDarkTheme)
    } else if (isUnderlineMode) {
        underlineAccentColor
    } else {
        effectiveUnderlineOrKeyColor
    }

    val baseWeight = when {
        fontWeightLevel >= 2 || isBoldMode -> FontWeight.Bold
        fontWeightLevel == 1 -> FontWeight.Medium
        else -> null
    }

    val result = buildAnnotatedString {
        append(strippedText)

        // Helper to ensure no SpanStyle ever starts or ends on an Arabic diacritic mark
        fun safeSpanAdd(style: SpanStyle, rawStart: Int, rawEnd: Int) {
            var s = rawStart.coerceIn(0, strippedText.length)
            var e = rawEnd.coerceIn(0, strippedText.length)
            while (s > 0 && isArabicDiacriticMark(strippedText[s])) {
                s--
            }
            while (e < strippedText.length && isArabicDiacriticMark(strippedText[e])) {
                e++
            }
            if (s < e) {
                addStyle(style = style, start = s, end = e)
            }
        }

        // 0. If global Medium/Bold weight level is enabled, apply base span weight without changing font size
        if (baseWeight != null && strippedText.isNotEmpty()) {
            addStyle(
                style = SpanStyle(
                    fontWeight = baseWeight
                ),
                start = 0,
                end = strippedText.length
            )
        }

        // 1. Apply explicit **...** spans (Important Lesson Sections — NEVER apply background when highlighter is OFF)
        for (range in explicitBoldRanges) {
            safeSpanAdd(
                style = SpanStyle(
                    fontWeight = FontWeight.Bold,
                    color = if (effectiveHighlightActive) textOnMarker else effectiveUnderlineOrKeyColor,
                    background = if (effectiveHighlightActive) markerPrimaryBg else Color.Transparent,
                    textDecoration = if (isUnderlineMode) TextDecoration.Underline else TextDecoration.None
                ),
                rawStart = range.first,
                rawEnd = range.last + 1
            )
        }

        // 1.5 Apply explicit __...__ underline spans
        for (range in explicitUnderlineRanges) {
            safeSpanAdd(
                style = SpanStyle(
                    fontWeight = FontWeight.Bold,
                    color = if (effectiveHighlightActive) textOnMarker else underlineAccentColor,
                    textDecoration = TextDecoration.Underline,
                    background = if (effectiveHighlightActive) markerPrimaryBg else Color.Transparent
                ),
                rawStart = range.first,
                rawEnd = range.last + 1
            )
        }

        if (enableSmartHighlights && strippedText.length >= 6) {
            // 2. Highlight lead-in titles before ':' on each line (e.g., "• تعریف استعاره:", "۱. اصل اول:")
            var lineStart = 0
            while (lineStart < strippedText.length) {
                val nextNewline = strippedText.indexOf('\n', lineStart)
                val lineEnd = if (nextNewline == -1) strippedText.length else nextNewline
                val colonIdx = strippedText.indexOf(':', lineStart)
                if (colonIdx in (lineStart + 2) until lineEnd) {
                    val prefixLen = colonIdx - lineStart
                    if (prefixLen <= 52) {
                        val prefixSub = strippedText.substring(lineStart, colonIdx)
                        // Ensure it's a Persian/educational lead-in, not a time like 12:30 or URL
                        if (prefixSub.any { BidiFormatter.isPersianOrArabicLetterOrPunct(it) }) {
                            if (colonIdx == lineEnd - 1) {
                                // سرفصل بخش (دو نقطه آخر خط، مثل «📖 آموزش کامل...:»):
                                // فقط بولد؛ خط زیر آبی و رنگ آبی ندارد تا شبیه لینک نشود.
                                safeSpanAdd(
                                    style = SpanStyle(
                                        fontWeight = FontWeight.Bold,
                                        textDecoration = TextDecoration.None
                                    ),
                                    rawStart = lineStart,
                                    rawEnd = colonIdx + 1
                                )
                            } else {
                                safeSpanAdd(
                                    style = SpanStyle(
                                        fontWeight = FontWeight.Bold,
                                        color = effectiveUnderlineOrKeyColor,
                                        textDecoration = if (isUnderlineMode) TextDecoration.Underline else TextDecoration.None
                                    ),
                                    rawStart = lineStart,
                                    rawEnd = colonIdx + 1
                                )
                            }
                        }
                    }
                }
                lineStart = lineEnd + 1
            }

            // 3. Bold & Accent key terms inside Persian guillemets «...» (max 64 chars so full sentences aren't highlighted)
            var searchIdx = 0
            while (searchIdx < strippedText.length) {
                val openG = strippedText.indexOf('«', searchIdx)
                if (openG == -1) break
                val closeG = strippedText.indexOf('»', openG + 1)
                if (closeG == -1) break
                if (closeG - openG in 2..64) {
                    val inside = strippedText.substring(openG + 1, closeG)
                    val isArabicWithDiacritics = inside.any { isArabicDiacriticMark(it) } ||
                        inside.contains("الله") || inside.contains("اللَّه") || inside.contains("اللَّه")
                    val spanDecoration = if (isUnderlineMode && !isArabicWithDiacritics) TextDecoration.Underline else TextDecoration.None
                    val spanBg = if (effectiveHighlightActive && !isArabicWithDiacritics) markerPrimaryBg else Color.Transparent
                    val spanColor = if (isArabicWithDiacritics) {
                        if (isDarkTheme) Color(0xFF34D399) else Color(0xFF047857)
                    } else if (effectiveHighlightActive) {
                        textOnMarker
                    } else if (isUnderlineMode) {
                        underlineAccentColor
                    } else {
                        secondaryEmphasisColor
                    }

                    safeSpanAdd(
                        style = SpanStyle(
                            fontWeight = FontWeight.Bold,
                            color = spanColor,
                            textDecoration = spanDecoration,
                            background = spanBg
                        ),
                        rawStart = openG,
                        rawEnd = closeG + 1
                    )
                }
                searchIdx = closeG + 1
            }
        }

        // 4. Color Arabic diacritics when enabled
        if (isColoredDiacritics) {
            for (idx in strippedText.indices) {
                val ch = strippedText[idx]
                if (isArabicDiacriticMark(ch)) {
                    addStyle(
                        style = SpanStyle(
                            color = diacriticsPalette.colorForDiacritic(ch, isDarkTheme)
                        ),
                        start = idx,
                        end = idx + 1
                    )
                }
            }
        }
    }
    annotatedStringCache[cacheKey] = result
    return result
}

@Composable
fun BiDiText(
    text: String,
    modifier: Modifier = Modifier,
    color: Color = Color.Unspecified,
    style: TextStyle = MaterialTheme.typography.bodyMedium,
    fontWeight: FontWeight? = null,
    textAlign: TextAlign? = null,
    maxLines: Int = Int.MAX_VALUE,
    isUnderlineEnabled: Boolean? = null,
    isHighlighterEnabled: Boolean? = null
) {
    val themeController = com.example.ui.theme.LocalAppThemeController.current
    val isBoldMode = themeController.isBoldTextEnabled
    val isUnderlineMode = isUnderlineEnabled ?: themeController.isUnderlineKeyPointsEnabled
    val isHighlighterMode = isHighlighterEnabled ?: themeController.isHighlighterEnabled
    val revision = themeController.fontRevision

    val activeFontFamily = remember(
        themeController.customFontFamily,
        themeController.selectedFontPreset,
        isBoldMode,
        revision
    ) {
        themeController.customFontFamily ?: themeController.selectedFontPreset.toFontFamily(forceBold = isBoldMode)
    }

    BiDiProseBlock(
        rawText = text,
        modifier = modifier,
        color = color,
        style = style,
        fontWeight = fontWeight,
        textAlign = textAlign,
        maxLines = maxLines,
        isUnderlineMode = isUnderlineMode,
        isHighlighterMode = isHighlighterMode,
        activeFontFamily = activeFontFamily
    )
}

@Composable
private fun BiDiProseBlock(
    rawText: String,
    modifier: Modifier = Modifier,
    color: Color = Color.Unspecified,
    style: TextStyle = MaterialTheme.typography.bodyMedium,
    fontWeight: FontWeight? = null,
    textAlign: TextAlign? = null,
    maxLines: Int = Int.MAX_VALUE,
    isUnderlineMode: Boolean = false,
    isHighlighterMode: Boolean = false,
    activeFontFamily: FontFamily = FontFamily.Default
) {
    val themeController = com.example.ui.theme.LocalAppThemeController.current
    val persianDigitsMode = themeController.usePersianDigits
    val isBoldMode = themeController.isBoldTextEnabled
    val fontWeightLevel = themeController.fontWeightLevel
    val highlightIntensity = themeController.highlightIntensity
    val revision = themeController.fontRevision
    val isColoredDiacritics = themeController.isColoredDiacriticsEnabled
    val diacriticsPalette = themeController.selectedDiacriticsPalette
    val highlighterPalette = themeController.selectedHighlighterPalette
    val underlinePalette = themeController.selectedUnderlinePalette
    val alignmentPreset = themeController.textAlignmentPreset
    val colorScheme = MaterialTheme.colorScheme
    val isDarkTheme = colorScheme.background.luminance() < 0.5f

    val formatted = remember(rawText, persianDigitsMode, revision) {
        BidiFormatter.formatMixedPersianText(BidiFormatter.convertCaretsToSuperscripts(rawText), persianDigitsMode)
    }

    val containsArabicDiacritic = remember(formatted) {
        formatted.any { isArabicDiacriticMark(it) }
    }

    // Keep font size 100% uniform and standard across all texts (never inflate individual lines with diacritics)
    val effectiveFontSize = style.fontSize

    val effectiveHighlightActive = isHighlighterMode && highlightIntensity > 0
    val hasExplicitMarkdown = formatted.contains("**") || formatted.contains("__")
    val hasGuillemets = formatted.contains("«") && formatted.contains("»")
    val hasColonOrKeyword = formatted.contains(":") || formatted.contains("⟹") || formatted.contains("⟵")
    val hasArabicDiacritics = isColoredDiacritics && containsArabicDiacritic
    val enableSmartHighlights = hasExplicitMarkdown || effectiveHighlightActive || isUnderlineMode || hasGuillemets || hasColonOrKeyword

    val primaryEmphasisColor = if (color != Color.Unspecified && color != colorScheme.onSurface && color != colorScheme.onSurfaceVariant) {
        color
    } else {
        underlinePalette.resolveColor(isDarkTheme)
    }
    val secondaryEmphasisColor = if (color != Color.Unspecified && color != colorScheme.onSurface && color != colorScheme.onSurfaceVariant) {
        color
    } else {
        if (isDarkTheme) Color(0xFFFBBF24) else Color(0xFF9A3412)
    }
    val highlightBgColor = if (effectiveHighlightActive) {
        highlighterPalette.resolveBackground(isDarkTheme, highlightIntensity)
    } else {
        Color.Transparent
    }

    val effectiveFontWeight = when {
        fontWeightLevel >= 2 || isBoldMode -> FontWeight.Bold
        fontWeightLevel == 1 -> FontWeight.Medium
        else -> fontWeight ?: style.fontWeight ?: FontWeight.Normal
    }

    val boldStrokeShadow = style.shadow

    val isPredominantlyLtr = remember(formatted) {
        BidiFormatter.isPredominantlyLtrFormulaOrEnglish(formatted)
    }
    val effectiveTextDirection = if (isPredominantlyLtr) TextDirection.Ltr else TextDirection.Rtl
    val effectiveTextAlign = when {
        textAlign != null -> textAlign
        alignmentPreset == com.example.ui.theme.TextAlignmentPreset.RIGHT -> TextAlign.Right
        alignmentPreset == com.example.ui.theme.TextAlignmentPreset.CENTER -> TextAlign.Center
        alignmentPreset == com.example.ui.theme.TextAlignmentPreset.LEFT -> TextAlign.Left
        isPredominantlyLtr -> TextAlign.Left
        else -> TextAlign.Right
    }
    val effectiveLayoutDir = if (isPredominantlyLtr) LayoutDirection.Ltr else LayoutDirection.Rtl

    val verticalSpacingDp = themeController.textVerticalSpacingDp.dp
    val spacedModifier = if (themeController.textVerticalSpacingDp > 0.1f) {
        modifier.padding(vertical = verticalSpacingDp)
    } else {
        modifier
    }

    val needsAnnotatedString = enableSmartHighlights || hasArabicDiacritics
    if (needsAnnotatedString) {
        val annotated = remember(
            formatted,
            isBoldMode,
            fontWeightLevel,
            enableSmartHighlights,
            isUnderlineMode,
            effectiveHighlightActive,
            highlightIntensity,
            hasArabicDiacritics,
            diacriticsPalette,
            highlighterPalette,
            underlinePalette,
            isDarkTheme,
            primaryEmphasisColor,
            secondaryEmphasisColor,
            highlightBgColor,
            revision
        ) {
            buildRichHighlightedPersianText(
                rawFormatted = formatted,
                isBoldMode = isBoldMode,
                enableSmartHighlights = enableSmartHighlights,
                isUnderlineMode = isUnderlineMode,
                isHighlighterMode = effectiveHighlightActive,
                isColoredDiacritics = hasArabicDiacritics,
                diacriticsPalette = diacriticsPalette,
                highlighterPalette = highlighterPalette,
                underlinePalette = underlinePalette,
                isDarkTheme = isDarkTheme,
                primaryEmphasisColor = primaryEmphasisColor,
                secondaryEmphasisColor = secondaryEmphasisColor,
                highlightBgColor = highlightBgColor,
                highlightIntensity = highlightIntensity,
                fontWeightLevel = fontWeightLevel
            )
        }

        CompositionLocalProvider(LocalLayoutDirection provides effectiveLayoutDir) {
            Text(
                text = annotated,
                modifier = spacedModifier,
                color = color,
                style = style.copy(
                    fontFamily = activeFontFamily,
                    fontSize = effectiveFontSize,
                    fontWeight = effectiveFontWeight,
                    fontSynthesis = if (isBoldMode) FontSynthesis.Weight else FontSynthesis.None,
                    shadow = boldStrokeShadow,
                    textDirection = effectiveTextDirection,
                    letterSpacing = androidx.compose.ui.unit.TextUnit.Unspecified,
                    fontFeatureSettings = "liga 1, rlig 1"
                ),
                fontWeight = effectiveFontWeight,
                textAlign = effectiveTextAlign,
                maxLines = maxLines,
                softWrap = maxLines > 1,
                overflow = TextOverflow.Ellipsis
            )
        }
    } else {
        CompositionLocalProvider(LocalLayoutDirection provides effectiveLayoutDir) {
            Text(
                text = formatted,
                modifier = spacedModifier,
                color = color,
                style = style.copy(
                    fontFamily = activeFontFamily,
                    fontSize = effectiveFontSize,
                    fontWeight = effectiveFontWeight,
                    fontSynthesis = if (isBoldMode) FontSynthesis.All else FontSynthesis.None,
                    shadow = boldStrokeShadow,
                    textDirection = effectiveTextDirection,
                    letterSpacing = androidx.compose.ui.unit.TextUnit.Unspecified,
                    fontFeatureSettings = "liga 1, rlig 1"
                ),
                fontWeight = effectiveFontWeight,
                textAlign = effectiveTextAlign,
                maxLines = maxLines,
                softWrap = maxLines > 1,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}

/**
 * Checks whether a single line is a mathematical equation or numeric calculation
 * that should be rendered with [SvgVectorMathFormulaDiagram] instead of plain text.
 */
internal fun isRenderableMathEquationLine(rawLine: String): Boolean {
    val clean = rawLine
        .replace("\u2066", "")
        .replace("\u2069", "")
        .replace("\u202D", "")
        .replace("\u202C", "")
        .replace("\u200E", "")
        .replace("\u200F", "")
        .replace("\u00A0", " ")
        .trim()
    if (clean.length < 3) return false
    // Exclude linguistic / Arabic / grammar lines
    if (clean.any { isArabicDiacriticMark(it) } ||
        clean.contains("فاعل") || clean.contains("مفعول") || clean.contains("مبتدا") ||
        clean.contains("مضاف") || clean.contains("مجرور") || clean.contains("ضمه") ||
        clean.contains("فتحه") || clean.contains("کسره") || clean.contains("تنوین") ||
        clean.contains("Verb") || clean.contains("Subject") || clean.contains("Object") ||
        clean.contains("Gerund") || clean.contains("Infinitive") || clean.contains("Tense") ||
        clean.contains("استعاره") || clean.contains("تشبیه") || clean.contains("مجاز") ||
        clean.contains("کنایه") || clean.contains("واج‌آرایی") || clean.contains("قافیه") ||
        clean.contains("ردیف") || clean.contains("عروض") || clean.contains("هجا")
    ) {
        return false
    }

    val hasMathOp = clean.contains("×") || clean.contains("÷") || clean.contains("−") ||
        clean.contains("±") || clean.contains("√") || clean.contains("Δ") ||
        clean.contains("²") || clean.contains("³") || clean.contains("ⁿ") ||
        clean.contains("₁") || clean.contains("₂") ||
        Regex("[0-9۰-۹a-zA-Z]\\s*[+\\-*/=]\\s*[0-9۰-۹a-zA-Z(√]").containsMatchIn(clean)

    if (!hasMathOp) return false

    val hasEqualsOrArrow = clean.contains("=") || clean.contains("⟹") || clean.contains("≤") || clean.contains("≥")
    val digitOrVarCount = clean.count { it.isDigit() || it in '۰'..'۹' || it in "xyznabdkrΔ√²³₁₂" }
    return (hasEqualsOrArrow && digitOrVarCount >= 2) ||
        (clean.count { it in "×÷±√²³" } >= 1 && digitOrVarCount >= 2)
}

private fun splitTextIntoProseAndSvgMathSegments(rawText: String): List<Pair<String, Boolean>> {
    if (rawText.isBlank()) return emptyList()
    val preSplit = BidiFormatter.convertCaretsToSuperscripts(rawText)
        .replace("===>", " ⟹ ")
        .replace("--->", " ⟹ ")
        .replace("-->", " ⟹ ")
        .replace("->", " ⟹ ")

    // Expand chained equations separated by | into separate lines first
    val expandedLines = mutableListOf<String>()
    for (line in preSplit.lines()) {
        if (line.contains("|") && isRenderableMathEquationLine(line)) {
            val parts = line.split("|").map { it.trim() }.filter { it.isNotEmpty() }
            if (parts.size >= 2) {
                parts.forEachIndexed { idx, part ->
                    // If the first part has a prefix like "پاسخ تشریحی: الگو: ۵ × ۲ + ۱ = ۱۱", separate the Persian header
                    if (idx == 0 && part.count { it == ':' } >= 1) {
                        val lastColon = part.lastIndexOf(':')
                        val headerPart = part.substring(0, lastColon + 1).trim()
                        val eqPart = part.substring(lastColon + 1).trim()
                        if (headerPart.isNotEmpty() && isRenderableMathEquationLine(eqPart)) {
                            expandedLines.add(headerPart)
                            expandedLines.add("گام ۱: $eqPart")
                        } else {
                            expandedLines.add(part)
                        }
                    } else if (isRenderableMathEquationLine(part) && !part.startsWith("گام")) {
                        expandedLines.add("گام ${idx + 1}: $part")
                    } else {
                        expandedLines.add(part)
                    }
                }
                continue
            }
        }
        expandedLines.add(line)
    }

    val segments = mutableListOf<Pair<String, Boolean>>()
    val currentProse = StringBuilder()

    fun flushProse() {
        if (currentProse.isNotEmpty()) {
            segments.add(currentProse.toString().trimEnd() to false)
            currentProse.clear()
        }
    }

    for (line in expandedLines) {
        val trimmed = line.trim()
        if (isRenderableMathEquationLine(trimmed)) {
            flushProse()
            segments.add(trimmed to true)
        } else {
            if (currentProse.isNotEmpty()) currentProse.append('\n')
            currentProse.append(line)
        }
    }
    flushProse()
    return segments
}

@Composable
fun LtrFormulaBadge(
    formula: String,
    modifier: Modifier = Modifier,
    accentColor: Color = MaterialTheme.colorScheme.primary
) {
    SvgVectorMathFormulaDiagram(
        formula = formula,
        modifier = modifier,
        accentColor = accentColor
    )
}

private data class ParsedFractionFormula(
    val lhsPrefix: String,
    val numerator: String,
    val denominator: String,
    val rhsSuffix: String,
    val isLtrMath: Boolean
)

private fun parseFractionFormula(rawFormula: String): ParsedFractionFormula? {
    val cleaned = rawFormula
        .replace("\u2066", "")
        .replace("\u2069", "")
        .replace("\u202D", "")
        .replace("\u202C", "")
        .replace("\u200E", "")
        .replace("\u00A0", " ")
        .trim()
    if (cleaned.count { it == '/' } != 1 || cleaned.contains("http") || cleaned.contains("www")) return null

    if (cleaned.contains("علامت") || cleaned.contains("فاعل") || cleaned.contains("مفعول") ||
        cleaned.contains("مبتدا") || cleaned.contains("مضاف") || cleaned.contains("مجرور") ||
        cleaned.contains("اسم") || cleaned.contains("فعل") || cleaned.contains("حرف") ||
        cleaned.contains("ضمه") || cleaned.contains("فتحه") || cleaned.contains("کسره") ||
        cleaned.contains("تنوین") || cleaned.contains("گام") || cleaned.contains("گزینه") ||
        cleaned.contains("سوال") || cleaned.contains("نهایی") || cleaned.contains("کنکور") ||
        cleaned.contains("کتاب") || cleaned.contains("کلیدواژه") || cleaned.contains("پاسخ") ||
        cleaned.contains("تعریف") || cleaned.contains("حکم") || cleaned.contains("مصداق") ||
        cleaned.any { ArabicTextProcessor.isArabicDiacritic(it) }
    ) {
        return null
    }

    val hasEquals = cleaned.contains("=")
    val hasExplicitMathKeywords = cleaned.contains("مجموع") || cleaned.contains("تعداد") ||
        cleaned.contains("تقسیم") || cleaned.contains("نرخ") || cleaned.contains("جمعیت") ||
        cleaned.contains("احتمال") || cleaned.contains("ضریب") || cleaned.contains("بازه") ||
        cleaned.contains("سود") || cleaned.contains("سرمایه") || cleaned.contains("شاخص") ||
        cleaned.contains("میانگین") || cleaned.contains("واریانس") || cleaned.contains("انحراف") ||
        cleaned.any { it in "Δ√±×÷²³ⁿ₁₂₀" || it in 'a'..'z' || it in 'A'..'Z' }
    if (!hasEquals && !hasExplicitMathKeywords) return null

    val lhsPrefix: String
    val exprPart: String
    if (hasEquals && cleaned.indexOf('=') < cleaned.indexOf('/')) {
        lhsPrefix = cleaned.substringBefore('=').trim()
        exprPart = cleaned.substringAfter('=').trim()
    } else {
        lhsPrefix = ""
        exprPart = cleaned
    }

    val slashIdx = exprPart.indexOf('/')
    if (slashIdx <= 0 || slashIdx >= exprPart.length - 1) return null

    val rawNum = exprPart.substring(0, slashIdx).trim()
    var rawDen = exprPart.substring(slashIdx + 1).trim()
    var rhsSuffix = ""

    val multIdx = rawDen.indexOf('×')
    if (multIdx > 0 && !rawDen.substring(0, multIdx).contains('(')) {
        rhsSuffix = rawDen.substring(multIdx).trim()
        rawDen = rawDen.substring(0, multIdx).trim()
    }

    val numClean = rawNum.removeSurrounding("(", ")").trim()
    val denClean = rawDen.removeSurrounding("(", ")").trim()
    if (numClean.isEmpty() || denClean.isEmpty() || numClean.length > 55 || denClean.length > 55) return null

    val isLtr = BidiFormatter.isPredominantlyLtrFormulaOrEnglish(cleaned)
    return ParsedFractionFormula(
        lhsPrefix = lhsPrefix,
        numerator = numClean,
        denominator = denClean,
        rhsSuffix = rhsSuffix,
        isLtrMath = isLtr
    )
}

// ============================================================================
// UNIVERSAL SVG / VECTOR GRAPHICAL MATH FORMULA ENGINE
// Renders all mathematical formulas, numeric operations, fractions, roots,
// and powers as high-precision Canvas Vector Diagrams (never plain text).
// ============================================================================

private sealed class VectorMathToken {
    data class OperatorNode(val op: String) : VectorMathToken()
    data class TermCapsule(
        val baseText: String,
        val superscript: String = "",
        val subscript: String = "",
        val hasSquareRoot: Boolean = false,
        val isNumeric: Boolean = false,
        val isFinalResult: Boolean = false
    ) : VectorMathToken()
    data class StackedFractionNode(
        val numerator: String,
        val denominator: String
    ) : VectorMathToken()
}

private data class ParsedMathFormulaStep(
    val persianLabel: String,
    val tokens: List<VectorMathToken>
)

private fun parseFormulaIntoVectorSteps(
    rawFormula: String,
    usePersianDigits: Boolean
): List<ParsedMathFormulaStep> {
    val cleanRaw = BidiFormatter.convertCaretsToSuperscripts(rawFormula)
        .replace("\u2066", "")
        .replace("\u2069", "")
        .replace("\u202D", "")
        .replace("\u202C", "")
        .replace("\u200E", "")
        .replace("\u200F", "")
        .replace("\u00A0", " ")
        .replace("===>", " ⟹ ")
        .replace("--->", " ⟹ ")
        .replace("-->", " ⟹ ")
        .replace("->", " ⟹ ")
        .replace("!", "")
        .trim()

    if (cleanRaw.isEmpty()) return emptyList()

    // Split multi-step formulas separated by | or newlines
    val rawLines = cleanRaw
        .split(Regex("\\n|\\|"))
        .map { it.trim() }
        .filter { it.isNotEmpty() }

    val resultSteps = mutableListOf<ParsedMathFormulaStep>()

    for (line in rawLines) {
        // Extract optional Persian label before ':'
        var persianHeader = ""
        var mathBody = line
        if (line.contains(":")) {
            val lastColonIdx = line.lastIndexOf(':')
            val beforeColon = line.substring(0, lastColonIdx).trim()
            val afterColon = line.substring(lastColonIdx + 1).trim()
            if (beforeColon.any { BidiFormatter.isPersianOrArabicLetterOrPunct(it) } && afterColon.isNotEmpty()) {
                persianHeader = beforeColon
                mathBody = afterColon
            }
        }

        // If mathBody has '⟹' chaining multiple equations, split them into sequential steps
        val arrowParts = if (mathBody.contains("⟹") && mathBody.length > 26) {
            mathBody.split("⟹").map { it.trim() }.filter { it.isNotEmpty() }
        } else {
            listOf(mathBody)
        }

        arrowParts.forEachIndexed { idx, part ->
            val stepLabel = when {
                idx == 0 && persianHeader.isNotEmpty() -> persianHeader
                idx > 0 -> "نتیجه گام"
                else -> ""
            }
            val tokens = tokenizeSingleMathExpression(part, usePersianDigits)
            if (tokens.isNotEmpty()) {
                resultSteps.add(ParsedMathFormulaStep(persianLabel = stepLabel, tokens = tokens))
            }
        }
    }

    return resultSteps
}

private fun tokenizeSingleMathExpression(
    expr: String,
    usePersianDigits: Boolean
): List<VectorMathToken> {
    // Check if the whole expression is a fraction formula first
    val frac = parseFractionFormula(expr)
    if (frac != null) {
        val list = mutableListOf<VectorMathToken>()
        if (frac.lhsPrefix.isNotEmpty()) {
            list.add( parseSingleTermCapsule(frac.lhsPrefix, usePersianDigits, isResult = false) )
            list.add( VectorMathToken.OperatorNode("=") )
        }
        list.add(
            VectorMathToken.StackedFractionNode(
                numerator = BidiFormatter.applyPreferredDigits(frac.numerator, usePersianDigits),
                denominator = BidiFormatter.applyPreferredDigits(frac.denominator, usePersianDigits)
            )
        )
        if (frac.rhsSuffix.isNotEmpty()) {
            list.addAll(tokenizeFlatMathString(frac.rhsSuffix, usePersianDigits))
        }
        return list
    }
    return tokenizeFlatMathString(expr, usePersianDigits)
}

private fun tokenizeFlatMathString(
    expr: String,
    usePersianDigits: Boolean
): List<VectorMathToken> {
    val normalized = expr
        .replace("*", " × ")
        .replace("−", " − ")
        .replace("+", " + ")
        .replace("×", " × ")
        .replace("÷", " ÷ ")
        .replace("=", " = ")
        .replace("≠", " ≠ ")
        .replace("≈", " ≈ ")
        .replace("≤", " ≤ ")
        .replace("≥", " ≥ ")
        .replace("±", " ± ")
        .replace("⟹", " ⟹ ")
        .replace(Regex("([0-9۰-۹a-zA-Z)\\]])\\s*-\\s*([0-9۰-۹a-zA-Z(√])"), "$1 − $2")
        .replace(Regex("\\s+"), " ")
        .trim()

    if (normalized.isEmpty()) return emptyList()

    // Tokenize while keeping parenthesized groups like (n − 1) together
    val rawChunks = mutableListOf<String>()
    val sb = StringBuilder()
    var parenDepth = 0
    for (ch in normalized) {
        when {
            ch == '(' || ch == '[' -> {
                parenDepth++
                sb.append(ch)
            }
            ch == ')' || ch == ']' -> {
                if (parenDepth > 0) parenDepth--
                sb.append(ch)
            }
            ch == ' ' && parenDepth == 0 -> {
                if (sb.isNotEmpty()) {
                    rawChunks.add(sb.toString())
                    sb.clear()
                }
            }
            else -> sb.append(ch)
        }
    }
    if (sb.isNotEmpty()) rawChunks.add(sb.toString())

    val operatorSet = setOf("+", "−", "-", "×", "÷", "=", "≠", "≈", "≤", "≥", "±", "⟹")
    val lastEqualsIndex = rawChunks.indexOfLast { it == "=" || it == "⟹" }

    return rawChunks.mapIndexed { index, chunk ->
        if (chunk in operatorSet) {
            VectorMathToken.OperatorNode(if (chunk == "-") "−" else chunk)
        } else if (chunk.contains("/") && chunk.length in 3..25 && !chunk.startsWith("/")) {
            val num = chunk.substringBefore("/").removeSurrounding("(", ")").trim()
            val den = chunk.substringAfter("/").removeSurrounding("(", ")").trim()
            if (num.isNotEmpty() && den.isNotEmpty()) {
                VectorMathToken.StackedFractionNode(
                    numerator = BidiFormatter.applyPreferredDigits(num, usePersianDigits),
                    denominator = BidiFormatter.applyPreferredDigits(den, usePersianDigits)
                )
            } else {
                parseSingleTermCapsule(chunk, usePersianDigits, isResult = lastEqualsIndex != -1 && index > lastEqualsIndex)
            }
        } else {
            val isFinalAnswer = lastEqualsIndex != -1 && index > lastEqualsIndex && index == rawChunks.lastIndex
            parseSingleTermCapsule(chunk, usePersianDigits, isResult = isFinalAnswer)
        }
    }
}

private fun parseSingleTermCapsule(
    rawToken: String,
    usePersianDigits: Boolean,
    isResult: Boolean
): VectorMathToken.TermCapsule {
    var token = rawToken.trim()
    val hasSqrt = token.startsWith("√")
    if (hasSqrt) {
        token = token.removePrefix("√").removeSurrounding("(", ")").trim()
    }

    val superscriptChars = "⁰¹²³⁴⁵⁶⁷⁸⁹⁺⁻⁼⁽⁾ⁿ"
    val subscriptChars = "₀₁₂₃₄₅₆₇₈₉₊₋₌₍₎ₙₘₖ"

    val baseBuilder = StringBuilder()
    val supBuilder = StringBuilder()
    val subBuilder = StringBuilder()

    for (ch in token) {
        when (ch) {
            in superscriptChars -> supBuilder.append(
                when (ch) {
                    '⁰' -> '0'; '¹' -> '1'; '²' -> '2'; '³' -> '3'; '⁴' -> '4'
                    '⁵' -> '5'; '⁶' -> '6'; '⁷' -> '7'; '⁸' -> '8'; '⁹' -> '9'
                    '⁺' -> '+'; '⁻' -> '−'; 'ⁿ' -> 'n'; else -> ch
                }
            )
            in subscriptChars -> subBuilder.append(
                when (ch) {
                    '₀' -> '0'; '₁' -> '1'; '₂' -> '2'; '₃' -> '3'; '₄' -> '4'
                    '₅' -> '5'; '₆' -> '6'; '₇' -> '7'; '₈' -> '8'; '₉' -> '9'
                    'ₙ' -> 'n'; 'ₘ' -> 'm'; 'ₖ' -> 'k'; else -> ch
                }
            )
            else -> baseBuilder.append(ch)
        }
    }

    val baseFormatted = BidiFormatter.applyPreferredDigits(
        baseBuilder.toString().ifEmpty { token },
        usePersianDigits
    )
    val supFormatted = BidiFormatter.applyPreferredDigits(supBuilder.toString(), usePersianDigits)
    val subFormatted = BidiFormatter.applyPreferredDigits(subBuilder.toString(), usePersianDigits)
    val isPureNumber = baseFormatted.all { it.isDigit() || it in '۰'..'۹' || it in ".,٫−+%" } &&
        baseFormatted.any { it.isDigit() || it in '۰'..'۹' }

    return VectorMathToken.TermCapsule(
        baseText = baseFormatted,
        superscript = supFormatted,
        subscript = subFormatted,
        hasSquareRoot = hasSqrt,
        isNumeric = isPureNumber,
        isFinalResult = isResult
    )
}

/**
 * Pure Canvas SVG Vector Icon for mathematical operators (+, −, ×, ÷, =, ⟹, ±, ≤, ≥, ≠).
 * Drawn with geometric vector paths and lines—never plain font glyphs!
 */
@Composable
fun SvgMathOperatorVectorIcon(
    operator: String,
    accentColor: Color,
    modifier: Modifier = Modifier
) {
    val isEqualsOrImplies = operator == "=" || operator == "⟹" || operator == "≠" || operator == "≈"
    val badgeBg = if (isEqualsOrImplies) {
        accentColor.copy(alpha = 0.18f)
    } else {
        accentColor.copy(alpha = 0.10f)
    }
    val strokeColor = if (isEqualsOrImplies) accentColor else MaterialTheme.colorScheme.onSurface

    Box(
        modifier = modifier
            .size(if (operator == "⟹") 30.dp else 24.dp),
        contentAlignment = Alignment.Center
    ) {
        Canvas(modifier = Modifier.fillMaxSize()) {
            val w = size.width
            val h = size.height
            val cx = w / 2f
            val cy = h / 2f
            val r = minOf(w, h) * 0.24f
            val strokeW = (minOf(w, h) * 0.105f).coerceAtLeast(2.2f)

            // Vector squircle badge background + hairline vector frame
            drawRoundRect(
                color = badgeBg,
                cornerRadius = CornerRadius(w * 0.30f, h * 0.30f)
            )
            drawRoundRect(
                color = accentColor.copy(alpha = 0.35f),
                cornerRadius = CornerRadius(w * 0.30f, h * 0.30f),
                style = Stroke(width = 1.2f)
            )

            when (operator) {
                "+" -> {
                    drawLine(
                        color = strokeColor,
                        start = Offset(cx - r, cy),
                        end = Offset(cx + r, cy),
                        strokeWidth = strokeW,
                        cap = StrokeCap.Round
                    )
                    drawLine(
                        color = strokeColor,
                        start = Offset(cx, cy - r),
                        end = Offset(cx, cy + r),
                        strokeWidth = strokeW,
                        cap = StrokeCap.Round
                    )
                }
                "−", "-" -> {
                    drawLine(
                        color = strokeColor,
                        start = Offset(cx - r, cy),
                        end = Offset(cx + r, cy),
                        strokeWidth = strokeW,
                        cap = StrokeCap.Round
                    )
                }
                "×", "*" -> {
                    val d = r * 0.82f
                    drawLine(
                        color = strokeColor,
                        start = Offset(cx - d, cy - d),
                        end = Offset(cx + d, cy + d),
                        strokeWidth = strokeW,
                        cap = StrokeCap.Round
                    )
                    drawLine(
                        color = strokeColor,
                        start = Offset(cx - d, cy + d),
                        end = Offset(cx + d, cy - d),
                        strokeWidth = strokeW,
                        cap = StrokeCap.Round
                    )
                }
                "÷", "/" -> {
                    drawLine(
                        color = strokeColor,
                        start = Offset(cx - r, cy),
                        end = Offset(cx + r, cy),
                        strokeWidth = strokeW,
                        cap = StrokeCap.Round
                    )
                    drawCircle(
                        color = strokeColor,
                        radius = strokeW * 0.62f,
                        center = Offset(cx, cy - r * 0.72f)
                    )
                    drawCircle(
                        color = strokeColor,
                        radius = strokeW * 0.62f,
                        center = Offset(cx, cy + r * 0.72f)
                    )
                }
                "=" -> {
                    val gap = r * 0.45f
                    drawLine(
                        color = strokeColor,
                        start = Offset(cx - r, cy - gap),
                        end = Offset(cx + r, cy - gap),
                        strokeWidth = strokeW,
                        cap = StrokeCap.Round
                    )
                    drawLine(
                        color = strokeColor,
                        start = Offset(cx - r, cy + gap),
                        end = Offset(cx + r, cy + gap),
                        strokeWidth = strokeW,
                        cap = StrokeCap.Round
                    )
                }
                "≠" -> {
                    val gap = r * 0.45f
                    drawLine(
                        color = strokeColor,
                        start = Offset(cx - r, cy - gap),
                        end = Offset(cx + r, cy - gap),
                        strokeWidth = strokeW,
                        cap = StrokeCap.Round
                    )
                    drawLine(
                        color = strokeColor,
                        start = Offset(cx - r, cy + gap),
                        end = Offset(cx + r, cy + gap),
                        strokeWidth = strokeW,
                        cap = StrokeCap.Round
                    )
                    drawLine(
                        color = strokeColor,
                        start = Offset(cx - r * 0.6f, cy + r * 0.85f),
                        end = Offset(cx + r * 0.6f, cy - r * 0.85f),
                        strokeWidth = strokeW * 0.85f,
                        cap = StrokeCap.Round
                    )
                }
                "±" -> {
                    val topCy = cy - r * 0.25f
                    val pr = r * 0.75f
                    drawLine(
                        color = strokeColor,
                        start = Offset(cx - pr, topCy),
                        end = Offset(cx + pr, topCy),
                        strokeWidth = strokeW * 0.85f,
                        cap = StrokeCap.Round
                    )
                    drawLine(
                        color = strokeColor,
                        start = Offset(cx, topCy - pr),
                        end = Offset(cx, topCy + pr),
                        strokeWidth = strokeW * 0.85f,
                        cap = StrokeCap.Round
                    )
                    drawLine(
                        color = strokeColor,
                        start = Offset(cx - pr, cy + r * 0.85f),
                        end = Offset(cx + pr, cy + r * 0.85f),
                        strokeWidth = strokeW * 0.85f,
                        cap = StrokeCap.Round
                    )
                }
                "⟹" -> {
                    val gap = r * 0.36f
                    val startX = cx - r * 1.15f
                    val endX = cx + r * 0.85f
                    val tipX = cx + r * 1.25f
                    drawLine(
                        color = strokeColor,
                        start = Offset(startX, cy - gap),
                        end = Offset(endX, cy - gap),
                        strokeWidth = strokeW * 0.82f,
                        cap = StrokeCap.Round
                    )
                    drawLine(
                        color = strokeColor,
                        start = Offset(startX, cy + gap),
                        end = Offset(endX, cy + gap),
                        strokeWidth = strokeW * 0.82f,
                        cap = StrokeCap.Round
                    )
                    val headPath = Path().apply {
                        moveTo(endX - r * 0.25f, cy - r * 0.85f)
                        lineTo(tipX, cy)
                        lineTo(endX - r * 0.25f, cy + r * 0.85f)
                    }
                    drawPath(
                        path = headPath,
                        color = strokeColor,
                        style = Stroke(width = strokeW * 0.9f, cap = StrokeCap.Round, join = StrokeJoin.Round)
                    )
                }
                "≤", "≥" -> {
                    val isLess = operator == "≤"
                    val brPath = Path().apply {
                        if (isLess) {
                            moveTo(cx + r * 0.7f, cy - r * 0.75f)
                            lineTo(cx - r * 0.7f, cy - r * 0.1f)
                            lineTo(cx + r * 0.7f, cy + r * 0.45f)
                        } else {
                            moveTo(cx - r * 0.7f, cy - r * 0.75f)
                            lineTo(cx + r * 0.7f, cy - r * 0.1f)
                            lineTo(cx - r * 0.7f, cy + r * 0.45f)
                        }
                    }
                    drawPath(
                        path = brPath,
                        color = strokeColor,
                        style = Stroke(width = strokeW * 0.85f, cap = StrokeCap.Round, join = StrokeJoin.Round)
                    )
                    drawLine(
                        color = strokeColor,
                        start = Offset(cx - r * 0.7f, cy + r * 0.85f),
                        end = Offset(cx + r * 0.7f, cy + r * 0.85f),
                        strokeWidth = strokeW * 0.85f,
                        cap = StrokeCap.Round
                    )
                }
                else -> {
                    drawCircle(
                        color = strokeColor,
                        radius = strokeW * 0.8f,
                        center = Offset(cx, cy)
                    )
                }
            }
        }
    }
}

/**
 * Geometric SVG Vector Capsule for Numbers (`۵`, `۲`, `۱۹۱`) and Algebraic Terms (`aₙ`, `x²`, `√Δ`).
 * Draws a custom Canvas vector frame, bottom vector accent bar for numeric values,
 * radical `√` vector overline path, and 2D raised/lowered superscript/subscript badges.
 */
@Composable
private fun SvgMathTermVectorCapsule(
    term: VectorMathToken.TermCapsule,
    accentColor: Color,
    fontFamily: FontFamily
) {
    val isDark = MaterialTheme.colorScheme.background.luminance() < 0.5f
    val resultEmerald = if (isDark) Color(0xFF34D399) else Color(0xFF059669)
    val capsuleTint = when {
        term.isFinalResult -> resultEmerald
        term.isNumeric -> accentColor
        else -> MaterialTheme.colorScheme.secondary
    }
    val surfaceBg = MaterialTheme.colorScheme.surface

    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier.padding(vertical = 1.dp)
    ) {
        // Custom SVG Vector Capsule Frame + Radical Overbar + Numeric Base Vector Line
        Canvas(modifier = Modifier.matchParentSize()) {
            val w = size.width
            val h = size.height
            val cr = CornerRadius(18f, 18f)

            drawRoundRect(
                color = surfaceBg,
                cornerRadius = cr
            )
            drawRoundRect(
                color = capsuleTint.copy(alpha = if (term.isFinalResult) 0.18f else 0.09f),
                cornerRadius = cr
            )
            drawRoundRect(
                color = capsuleTint.copy(alpha = if (term.isFinalResult) 0.75f else 0.38f),
                cornerRadius = cr,
                style = Stroke(width = if (term.isFinalResult) 3.0f else 1.8f)
            )

            // Bottom geometric vector indicator bar for numbers & final results
            if (term.isNumeric || term.isFinalResult) {
                val barMargin = w * 0.22f
                drawLine(
                    color = capsuleTint.copy(alpha = if (term.isFinalResult) 0.9f else 0.55f),
                    start = Offset(barMargin, h - 3.2f),
                    end = Offset(w - barMargin, h - 3.2f),
                    strokeWidth = if (term.isFinalResult) 3.2f else 2.2f,
                    cap = StrokeCap.Round
                )
            }

            // True SVG Vector Radical (√) Path with Overbar when hasSquareRoot == true
            if (term.hasSquareRoot) {
                val sqrtPath = Path().apply {
                    moveTo(6f, h * 0.55f)
                    lineTo(10f, h * 0.50f)
                    lineTo(14.5f, h * 0.78f)
                    lineTo(20f, 5.5f)
                    lineTo(w - 6f, 5.5f)
                }
                drawPath(
                    path = sqrtPath,
                    color = capsuleTint,
                    style = Stroke(width = 2.6f, cap = StrokeCap.Round, join = StrokeJoin.Round)
                )
            }
        }

        Row(
            modifier = Modifier.padding(
                start = if (term.hasSquareRoot) 13.dp else 9.dp,
                end = 9.dp,
                top = if (term.hasSquareRoot) 5.dp else 4.dp,
                bottom = 5.dp
            ),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "\u2066\u202D${term.baseText}\u202C\u2069",
                style = MaterialTheme.typography.bodyMedium.copy(
                    fontFamily = fontFamily,
                    textDirection = TextDirection.Ltr,
                    fontSize = if (term.isFinalResult) 15.sp else 14.sp
                ),
                fontWeight = FontWeight.Black,
                color = if (term.isFinalResult) resultEmerald else MaterialTheme.colorScheme.onSurface
            )

            if (term.superscript.isNotEmpty() || term.subscript.isNotEmpty()) {
                Spacer(modifier = Modifier.width(2.dp))
                Column(
                    horizontalAlignment = Alignment.Start,
                    verticalArrangement = Arrangement.Center
                ) {
                    if (term.superscript.isNotEmpty()) {
                        Surface(
                            color = capsuleTint.copy(alpha = 0.18f),
                            shape = RoundedCornerShape(4.dp)
                        ) {
                            Text(
                                text = term.superscript,
                                style = MaterialTheme.typography.labelSmall.copy(
                                    fontFamily = fontFamily,
                                    fontSize = 9.5.sp,
                                    lineHeight = 10.sp,
                                    textDirection = TextDirection.Ltr
                                ),
                                fontWeight = FontWeight.Black,
                                color = capsuleTint,
                                modifier = Modifier.padding(horizontal = 3.dp, vertical = 0.5.dp)
                            )
                        }
                    }
                    if (term.subscript.isNotEmpty()) {
                        Surface(
                            color = MaterialTheme.colorScheme.surfaceVariant,
                            shape = RoundedCornerShape(4.dp)
                        ) {
                            Text(
                                text = term.subscript,
                                style = MaterialTheme.typography.labelSmall.copy(
                                    fontFamily = fontFamily,
                                    fontSize = 9.5.sp,
                                    lineHeight = 10.sp,
                                    textDirection = TextDirection.Ltr
                                ),
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.onSurfaceVariant,
                                modifier = Modifier.padding(horizontal = 3.dp, vertical = 0.5.dp)
                            )
                        }
                    }
                }
            }
        }
    }
}

/**
 * 2D Stacked Vector Fraction Node with a Canvas-drawn SVG Fraction Bar and Diamond End-Caps.
 */
@Composable
private fun SvgStackedFractionVectorNode(
    fraction: VectorMathToken.StackedFractionNode,
    accentColor: Color,
    fontFamily: FontFamily
) {
    Surface(
        color = MaterialTheme.colorScheme.surface,
        shape = RoundedCornerShape(12.dp),
        border = BorderStroke(1.dp, accentColor.copy(alpha = 0.38f))
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .width(IntrinsicSize.Max)
                .padding(horizontal = 10.dp, vertical = 5.dp)
        ) {
            Text(
                text = "\u2066\u202D${fraction.numerator}\u202C\u2069",
                style = MaterialTheme.typography.bodySmall.copy(
                    fontFamily = fontFamily,
                    textDirection = TextDirection.Ltr
                ),
                fontWeight = FontWeight.Black,
                color = MaterialTheme.colorScheme.onSurface,
                textAlign = TextAlign.Center
            )

            // Pure SVG Vector Fraction Bar with Circle Caps
            Canvas(
                modifier = Modifier
                    .fillMaxWidth()
                    .widthIn(min = 48.dp)
                    .height(7.dp)
            ) {
                val cy = size.height / 2f
                drawLine(
                    color = accentColor,
                    start = Offset(3f, cy),
                    end = Offset(size.width - 3f, cy),
                    strokeWidth = 2.8f,
                    cap = StrokeCap.Round
                )
                drawCircle(
                    color = accentColor,
                    radius = 2.6f,
                    center = Offset(3f, cy)
                )
                drawCircle(
                    color = accentColor,
                    radius = 2.6f,
                    center = Offset(size.width - 3f, cy)
                )
            }

            Text(
                text = "\u2066\u202D${fraction.denominator}\u202C\u2069",
                style = MaterialTheme.typography.bodySmall.copy(
                    fontFamily = fontFamily,
                    textDirection = TextDirection.Ltr
                ),
                fontWeight = FontWeight.Black,
                color = MaterialTheme.colorScheme.onSurface,
                textAlign = TextAlign.Center
            )
        }
    }
}

/**
 * Primary SVG / Vector Mathematical Formula Diagram Component.
 * Draws a blueprint vector grid canvas with corner vector brackets and renders
 * every step and token as structured SVG Vector Capsules and Vector Operator Icons.
 */
fun containsMathFormulaExpression(text: String): Boolean {
    val trimmed = text.trim()
    if (trimmed.isEmpty()) return false
    val hasMathSymbol = trimmed.any { it in setOf('=', '+', '×', '÷', '−', '√', '^', '≤', '≥', '≠') }
    val isPureNumberOrFraction = trimmed.all {
        it.isDigit() || it in '۰'..'۹' || it in setOf('/', '.', '-', '+', '%', '٪', ' ', '(', ')')
    } && trimmed.any { it.isDigit() || it in '۰'..'۹' }
    return hasMathSymbol || isPureNumberOrFraction
}

@Composable
fun SvgVectorMathFormulaDiagram(
    formula: String,
    modifier: Modifier = Modifier,
    accentColor: Color = MaterialTheme.colorScheme.primary
) {
    if (formula.isBlank()) return
    val themeController = com.example.ui.theme.LocalAppThemeController.current
    val isBoldMode = themeController.isBoldTextEnabled
    val activeFontFamily = themeController.customFontFamily
        ?: themeController.selectedFontPreset.toFontFamily(forceBold = isBoldMode)
    val isPureFormula = remember(formula) {
        BidiFormatter.isPredominantlyLtrFormulaOrEnglish(formula)
    }

    Surface(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(14.dp),
        color = accentColor.copy(alpha = 0.08f),
        border = BorderStroke(0.9.dp, accentColor.copy(alpha = 0.30f))
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 12.dp, vertical = 8.dp),
            contentAlignment = if (isPureFormula) Alignment.Center else Alignment.CenterStart
        ) {
            BiDiProseBlock(
                rawText = formula,
                modifier = Modifier.fillMaxWidth(),
                color = MaterialTheme.colorScheme.onSurface,
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.Bold,
                textAlign = if (isPureFormula) TextAlign.Center else null,
                isUnderlineMode = themeController.isUnderlineKeyPointsEnabled,
                isHighlighterMode = themeController.isHighlighterEnabled,
                activeFontFamily = activeFontFamily
            )
        }
    }
}

/**
 * Renders mathematical and rule formulas using the SVG Vector Math Engine (`SvgVectorMathFormulaDiagram`).
 */
@Composable
fun VisualMathFormulaCard(
    formula: String,
    modifier: Modifier = Modifier,
    accentColor: Color = MaterialTheme.colorScheme.primary
) {
    SvgVectorMathFormulaDiagram(
        formula = formula,
        modifier = modifier,
        accentColor = accentColor
    )
}

private sealed class DiagramBlockSegment {
    data class TableSegment(val rows: List<List<String>>) : DiagramBlockSegment()
    data class LineSegment(val line: String) : DiagramBlockSegment()
}

@Composable
fun EducationalStructuredTable(
    rows: List<List<String>>,
    accentColor: Color,
    modifier: Modifier = Modifier
) {
    if (rows.isEmpty()) return
    val colCount = remember(rows) { rows.maxOfOrNull { it.size } ?: 0 }
    if (colCount == 0) return

    val hasHeader = rows.size >= 2
    val headerCells = if (hasHeader) rows.first() else emptyList()
    val dataRows = if (hasHeader) rows.drop(1) else rows

    val themeController = com.example.ui.theme.LocalAppThemeController.current
    // Allow student to switch between full RTL Table View and Mobile Comparison Cards when table has 3+ columns
    var showAsCards by remember(themeController.tableDisplayPreference) {
        mutableStateOf(themeController.tableDisplayPreference == "CARDS" && colCount >= 3 && hasHeader)
    }

    val columnWidths = remember(rows, colCount) {
        (0 until colCount).map { colIdx ->
            val maxLen = rows.maxOfOrNull { row -> row.getOrNull(colIdx)?.length ?: 0 } ?: 10
            when {
                colIdx == 0 -> (maxLen * 6.2f + 28f).coerceIn(124f, 168f).dp
                else -> (maxLen * 5.8f + 28f).coerceIn(132f, 210f).dp
            }
        }
    }

    val outlineColor = MaterialTheme.colorScheme.outlineVariant
    val headerBg = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.55f)
    val headerTextColor = MaterialTheme.colorScheme.onPrimaryContainer
    val evenRowBg = MaterialTheme.colorScheme.surface
    val oddRowBg = MaterialTheme.colorScheme.surfaceContainerLowest

    CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl) {
        Column(
            modifier = modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            if (colCount >= 3 && hasHeader && dataRows.isNotEmpty()) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = if (showAsCards) "نمای کارت‌های مقایسه‌ای" else "جدول مقایسه‌ای (قابل اسکرول افقی)",
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        fontWeight = FontWeight.Medium
                    )
                    Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                        Surface(
                            onClick = { showAsCards = false },
                            shape = RoundedCornerShape(8.dp),
                            color = if (!showAsCards) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.surface,
                            border = BorderStroke(1.dp, if (!showAsCards) MaterialTheme.colorScheme.primary else outlineColor)
                        ) {
                            Text(
                                text = "جدول",
                                style = MaterialTheme.typography.labelSmall,
                                fontWeight = FontWeight.Bold,
                                color = if (!showAsCards) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.onSurfaceVariant,
                                modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp)
                            )
                        }
                        Surface(
                            onClick = { showAsCards = true },
                            shape = RoundedCornerShape(8.dp),
                            color = if (showAsCards) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.surface,
                            border = BorderStroke(1.dp, if (showAsCards) MaterialTheme.colorScheme.primary else outlineColor)
                        ) {
                            Text(
                                text = "کارت مقایسه‌ای",
                                style = MaterialTheme.typography.labelSmall,
                                fontWeight = FontWeight.Bold,
                                color = if (showAsCards) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.onSurfaceVariant,
                                modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp)
                            )
                        }
                    }
                }
            }

            if (showAsCards && hasHeader) {
                Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                    dataRows.forEach { row ->
                        val rowTitle = row.firstOrNull().orEmpty()
                        Surface(
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(12.dp),
                            color = MaterialTheme.colorScheme.surface,
                            border = BorderStroke(1.dp, outlineColor)
                        ) {
                            Column(
                                modifier = Modifier.padding(12.dp),
                                verticalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                Surface(
                                    color = headerBg,
                                    shape = RoundedCornerShape(8.dp),
                                    modifier = Modifier.fillMaxWidth()
                                ) {
                                    BiDiText(
                                        text = "${headerCells.firstOrNull().orEmpty()}: $rowTitle",
                                        style = MaterialTheme.typography.titleSmall,
                                        fontWeight = FontWeight.Bold,
                                        color = headerTextColor,
                                        modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp)
                                    )
                                }
                                for (colIdx in 1 until colCount) {
                                    val colTitle = headerCells.getOrNull(colIdx).orEmpty()
                                    val cellVal = row.getOrNull(colIdx).orEmpty()
                                    if (cellVal.isNotEmpty()) {
                                        Row(
                                            modifier = Modifier.fillMaxWidth(),
                                            horizontalArrangement = Arrangement.spacedBy(6.dp),
                                            verticalAlignment = Alignment.Top
                                        ) {
                                            Text(
                                                text = "• $colTitle:",
                                                style = MaterialTheme.typography.bodySmall,
                                                fontWeight = FontWeight.Bold,
                                                color = accentColor
                                            )
                                            BiDiText(
                                                text = cellVal,
                                                style = MaterialTheme.typography.bodySmall,
                                                color = MaterialTheme.colorScheme.onSurface,
                                                modifier = Modifier.weight(1f)
                                            )
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            } else {
                val useHorizontalScroll = colCount >= 3
                val sharedScrollState = rememberScrollState()
                val tableContainerModifier = if (useHorizontalScroll) {
                    Modifier
                        .fillMaxWidth()
                        .horizontalScroll(sharedScrollState)
                } else {
                    Modifier.fillMaxWidth()
                }

                Box(modifier = tableContainerModifier) {
                    Column(
                        modifier = Modifier
                            .let { if (!useHorizontalScroll) it.fillMaxWidth() else it }
                            .clip(RoundedCornerShape(12.dp))
                            .border(1.dp, outlineColor, RoundedCornerShape(12.dp))
                    ) {
                        rows.forEachIndexed { rowIdx, rowCells ->
                            val isHeaderRow = hasHeader && rowIdx == 0
                            val bg = when {
                                isHeaderRow -> headerBg
                                rowIdx % 2 == 1 -> evenRowBg
                                else -> oddRowBg
                            }

                            Row(
                                modifier = Modifier
                                    .let { if (!useHorizontalScroll) it.fillMaxWidth() else it }
                                    .background(bg)
                                    .height(IntrinsicSize.Min),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                for (colIdx in 0 until colCount) {
                                    val cellText = rowCells.getOrNull(colIdx).orEmpty()
                                    val isFirstCol = colIdx == 0
                                    val cellModifier = if (useHorizontalScroll) {
                                        Modifier
                                            .width(columnWidths[colIdx])
                                            .fillMaxHeight()
                                            .padding(horizontal = 12.dp, vertical = 10.dp)
                                    } else {
                                        Modifier
                                            .weight(if (isFirstCol) 0.42f else 0.58f)
                                            .fillMaxHeight()
                                            .padding(horizontal = 12.dp, vertical = 10.dp)
                                    }

                                    Box(
                                        modifier = cellModifier,
                                        contentAlignment = if (isHeaderRow) Alignment.Center else Alignment.CenterStart
                                    ) {
                                        BiDiText(
                                            text = cellText,
                                            style = if (isHeaderRow) MaterialTheme.typography.labelLarge else MaterialTheme.typography.bodySmall,
                                            fontWeight = when {
                                                isHeaderRow -> FontWeight.Bold
                                                isFirstCol -> FontWeight.SemiBold
                                                else -> FontWeight.Normal
                                            },
                                            color = when {
                                                isHeaderRow -> headerTextColor
                                                isFirstCol -> accentColor
                                                else -> MaterialTheme.colorScheme.onSurface
                                            },
                                            textAlign = if (isHeaderRow) TextAlign.Center else TextAlign.Right,
                                            maxLines = 8,
                                            modifier = Modifier.fillMaxWidth()
                                        )
                                    }

                                    if (colIdx < colCount - 1) {
                                        Box(
                                            modifier = Modifier
                                                .fillMaxHeight()
                                                .width(1.dp)
                                                .background(outlineColor)
                                        )
                                    }
                                }
                            }

                            if (rowIdx < rows.lastIndex) {
                                Box(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(1.dp)
                                        .background(outlineColor)
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
fun SmartDiagramAndFormulaBlock(
    rawText: String,
    accentColor: Color,
    modifier: Modifier = Modifier
) {
    val segments = remember(rawText) {
        val withoutBoxChars = rawText
            .replace("┌", "")
            .replace("┐", "")
            .replace("└", "")
            .replace("┘", "")
            .replace("├", "")
            .replace("┤", "")
            .replace("┬", "")
            .replace("┴", "")
            .replace("┼", "")
            .replace("═", "")
            .replace("║", "")
            .replace("╒", "")
            .replace("╕", "")
            .replace("╘", "")
            .replace("╛", "")
        val cleanedLines = BidiFormatter.convertCaretsToSuperscripts(withoutBoxChars)
            .replace("===>", " ⟹ ")
            .replace("--->", " ⟹ ")
            .replace("-->", " ⟹ ")
            .replace("->", " ⟹ ")
            .lines()
            .map { rawLine ->
                rawLine.trim()
                    .removePrefix("│")
                    .removeSuffix("│")
                    .removePrefix("|")
                    .removeSuffix("|")
                    .replace("│", "|")
                    .replace("──", "")
                    .replace("───", "")
                    .trim()
                    .removePrefix("•")
                    .removePrefix("-")
                    .trim()
            }
            .filter { line ->
                val stripped = line.replace("─", "").replace("_", "").replace("-", "").replace("|", "").trim()
                stripped.isNotEmpty()
            }

        val builtSegments = mutableListOf<DiagramBlockSegment>()
        val pendingTableRows = mutableListOf<List<String>>()

        fun flushTable() {
            if (pendingTableRows.isNotEmpty()) {
                builtSegments.add(DiagramBlockSegment.TableSegment(pendingTableRows.toList()))
                pendingTableRows.clear()
            }
        }

        for (line in cleanedLines) {
            val isMathLine = isRenderableMathEquationLine(line)
            val isTableLine = line.contains("|") && !line.contains("|k|") && !line.contains("P(A|B)") && !isMathLine
            if (isTableLine) {
                val cells = line.split("|").map { it.trim() }.filter { it.isNotEmpty() }
                if (cells.size >= 2) {
                    pendingTableRows.add(cells)
                } else {
                    flushTable()
                    builtSegments.add(DiagramBlockSegment.LineSegment(line.replace("|", "").trim()))
                }
            } else {
                flushTable()
                builtSegments.add(DiagramBlockSegment.LineSegment(line))
            }
        }
        flushTable()
        builtSegments
    }

    Column(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .background(
                Brush.linearGradient(
                    colors = listOf(
                        accentColor.copy(alpha = 0.14f),
                        accentColor.copy(alpha = 0.06f),
                        MaterialTheme.colorScheme.surfaceContainerLow
                    )
                )
            )
            .border(
                width = 1.dp,
                color = accentColor.copy(alpha = 0.35f),
                shape = RoundedCornerShape(16.dp)
            )
            .padding(horizontal = 12.dp, vertical = 12.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Box(
                modifier = Modifier
                    .size(28.dp)
                    .clip(CircleShape)
                    .background(accentColor.copy(alpha = 0.16f)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.AutoAwesome,
                    contentDescription = null,
                    tint = accentColor,
                    modifier = Modifier.size(16.dp)
                )
            }
            BiDiText(
                text = "نمودار",
                style = MaterialTheme.typography.titleSmall,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface
            )
        }
        segments.forEach { segment ->
            when (segment) {
                is DiagramBlockSegment.TableSegment -> {
                    EducationalStructuredTable(
                        rows = segment.rows,
                        accentColor = accentColor
                    )
                }

                is DiagramBlockSegment.LineSegment -> {
                    val line = segment.line
                    val isPureLtr = BidiFormatter.isPredominantlyLtrFormulaOrEnglish(line)
                    val hasColonFormula = line.contains(":") && hasLtrAfterColon(line)
                    val isFractionLine = parseFractionFormula(line) != null
                    val isMathLine = isRenderableMathEquationLine(line)
                    val isAlgorithmHeader = line.contains("الگوریتم") ||
                        line.contains("فرمول ۳ مرحله‌ای") ||
                        line.contains("تکنیک ۳ مرحله‌ای") ||
                        line.startsWith("📌") ||
                        line.contains("جدول ")
                    val isStepLine = line.startsWith("گام ") || line.startsWith("مرحله ")

                    when {
                        isAlgorithmHeader -> {
                            Surface(
                                color = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.55f),
                                shape = RoundedCornerShape(10.dp),
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Row(
                                    modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp),
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.Calculate,
                                        contentDescription = null,
                                        tint = accentColor,
                                        modifier = Modifier.size(18.dp)
                                    )
                                    BiDiText(
                                        text = line.removePrefix("📌").trim(),
                                        style = MaterialTheme.typography.titleSmall,
                                        fontWeight = FontWeight.Bold,
                                        color = MaterialTheme.colorScheme.onPrimaryContainer,
                                        modifier = Modifier.weight(1f)
                                    )
                                }
                            }
                        }

                        isMathLine || isPureLtr || isFractionLine || hasColonFormula -> {
                            SvgVectorMathFormulaDiagram(
                                formula = line,
                                accentColor = accentColor
                            )
                        }

                        isStepLine -> {
                            val parts = line.split(":", limit = 2)
                            val stepBadge = parts[0].trim()
                            val stepDesc = parts.getOrNull(1)?.trim() ?: ""

                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clip(RoundedCornerShape(12.dp))
                                    .background(MaterialTheme.colorScheme.surface)
                                    .border(1.dp, MaterialTheme.colorScheme.outlineVariant, RoundedCornerShape(12.dp))
                                    .padding(horizontal = 12.dp, vertical = 10.dp),
                                verticalAlignment = Alignment.Top,
                                horizontalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                Surface(
                                    color = MaterialTheme.colorScheme.primaryContainer,
                                    shape = RoundedCornerShape(50)
                                ) {
                                    Text(
                                        text = BidiFormatter.applyPreferredDigits(stepBadge),
                                        style = MaterialTheme.typography.labelSmall,
                                        fontWeight = FontWeight.Bold,
                                        color = MaterialTheme.colorScheme.onPrimaryContainer,
                                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp)
                                    )
                                }

                                BiDiText(
                                    text = stepDesc,
                                    style = MaterialTheme.typography.bodyMedium,
                                    fontWeight = FontWeight.Medium,
                                    color = MaterialTheme.colorScheme.onSurface,
                                    modifier = Modifier.weight(1f)
                                )
                            }
                        }

                        else -> {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clip(RoundedCornerShape(12.dp))
                                    .background(MaterialTheme.colorScheme.surface)
                                    .border(0.8.dp, MaterialTheme.colorScheme.outlineVariant, RoundedCornerShape(12.dp))
                                    .padding(horizontal = 12.dp, vertical = 10.dp),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                Box(
                                    modifier = Modifier
                                        .width(3.5.dp)
                                        .height(16.dp)
                                        .clip(RoundedCornerShape(2.dp))
                                        .background(accentColor)
                                )
                                BiDiText(
                                    text = line,
                                    style = MaterialTheme.typography.bodyMedium,
                                    fontWeight = FontWeight.Medium,
                                    color = MaterialTheme.colorScheme.onSurface,
                                    modifier = Modifier.weight(1f)
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

private fun hasLtrAfterColon(line: String): Boolean {
    val after = line.substringAfter(":", "").trim()
    if (after.isEmpty()) return false
    return BidiFormatter.isPredominantlyLtrFormulaOrEnglish(after) || parseFractionFormula(after) != null || isRenderableMathEquationLine(after)
}





