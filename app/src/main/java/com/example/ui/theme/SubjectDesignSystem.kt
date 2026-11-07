package com.example.ui.theme

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBalance
import androidx.compose.material.icons.filled.AltRoute
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.AutoStories
import androidx.compose.material.icons.filled.Badge
import androidx.compose.material.icons.filled.Calculate
import androidx.compose.material.icons.filled.EditNote
import androidx.compose.material.icons.filled.EmojiObjects
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Groups
import androidx.compose.material.icons.filled.HistoryEdu
import androidx.compose.material.icons.filled.Language
import androidx.compose.material.icons.filled.MenuBook
import androidx.compose.material.icons.filled.Psychology
import androidx.compose.material.icons.filled.Public
import androidx.compose.material.icons.filled.School
import androidx.compose.material.icons.filled.Spa
import androidx.compose.material.icons.filled.Translate
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector

/**
 * Material 3 Expressive Subject Design Tokens.
 * Provides distinct visual personality, iconography, and colors for all 18 curriculum areas.
 */
@Immutable
data class SubjectDesignToken(
    val id: String,
    val icon: ImageVector,
    val accentColor: Color,
    val containerColor: Color,
    val onContainerColor: Color,
    val gradientBrush: Brush,
    val shortBadge: String
)

object SubjectDesignSystem {

    fun getToken(subjectId: String, isDark: Boolean = false): SubjectDesignToken {
        val cleanId = subjectId.lowercase().trim()
        return when {
            cleanId.contains("math") -> SubjectDesignToken(
                id = "math",
                icon = Icons.Default.Calculate,
                accentColor = if (isDark) Color(0xFF818CF8) else Color(0xFF4338CA),
                containerColor = if (isDark) Color(0xFF1E1B4B) else Color(0xFFEEF2FF),
                onContainerColor = if (isDark) Color(0xFFE0E7FF) else Color(0xFF312E81),
                gradientBrush = if (isDark) {
                    Brush.linearGradient(listOf(Color(0xFF312E81), Color(0xFF1E1B4B)))
                } else {
                    Brush.linearGradient(listOf(Color(0xFFEEF2FF), Color(0xFFE0E7FF)))
                },
                shortBadge = "ریاضی و آمار"
            )

            cleanId.contains("fonoon") || cleanId.contains("adabi") -> SubjectDesignToken(
                id = "fonoon",
                icon = Icons.Default.AutoStories,
                accentColor = if (isDark) Color(0xFFFB7185) else Color(0xFF9F1239),
                containerColor = if (isDark) Color(0xFF4C0519) else Color(0xFFFFE4E6),
                onContainerColor = if (isDark) Color(0xFFFFE4E6) else Color(0xFF4C0519),
                gradientBrush = if (isDark) {
                    Brush.linearGradient(listOf(Color(0xFF4C0519), Color(0xFF1F030B)))
                } else {
                    Brush.linearGradient(listOf(Color(0xFFFFF1F2), Color(0xFFFFE4E6)))
                },
                shortBadge = "علوم و فنون"
            )

            cleanId.contains("arabic") -> SubjectDesignToken(
                id = "arabic",
                icon = Icons.Default.Translate,
                accentColor = if (isDark) Color(0xFF2DD4BF) else Color(0xFF0F766E),
                containerColor = if (isDark) Color(0xFF115E59) else Color(0xFFCCFBF1),
                onContainerColor = if (isDark) Color(0xFFCCFBF1) else Color(0xFF042F2E),
                gradientBrush = if (isDark) {
                    Brush.linearGradient(listOf(Color(0xFF0F766E), Color(0xFF042F2E)))
                } else {
                    Brush.linearGradient(listOf(Color(0xFFE6FFFA), Color(0xFFCCFBF1)))
                },
                shortBadge = "عربی تخصصی"
            )

            cleanId.contains("philosophy") -> SubjectDesignToken(
                id = "philosophy",
                icon = Icons.Default.Psychology,
                accentColor = if (isDark) Color(0xFFFBBF24) else Color(0xFFB45309),
                containerColor = if (isDark) Color(0xFF451A03) else Color(0xFFFEF3C7),
                onContainerColor = if (isDark) Color(0xFFFEF3C7) else Color(0xFF451A03),
                gradientBrush = if (isDark) {
                    Brush.linearGradient(listOf(Color(0xFF78350F), Color(0xFF451A03)))
                } else {
                    Brush.linearGradient(listOf(Color(0xFFFFFBEB), Color(0xFFFEF3C7)))
                },
                shortBadge = "فلسفه"
            )

            cleanId.contains("logic") -> SubjectDesignToken(
                id = "logic",
                icon = Icons.Default.AltRoute,
                accentColor = if (isDark) Color(0xFFA78BFA) else Color(0xFF6366F1),
                containerColor = if (isDark) Color(0xFF2E1065) else Color(0xFFEDE9FE),
                onContainerColor = if (isDark) Color(0xFFEDE9FE) else Color(0xFF2E1065),
                gradientBrush = if (isDark) {
                    Brush.linearGradient(listOf(Color(0xFF3B0764), Color(0xFF1E1035)))
                } else {
                    Brush.linearGradient(listOf(Color(0xFFF5F3FF), Color(0xFFEDE9FE)))
                },
                shortBadge = "منطق"
            )

            cleanId.contains("psychology") -> SubjectDesignToken(
                id = "psychology",
                icon = Icons.Default.EmojiObjects,
                accentColor = if (isDark) Color(0xFFC084FC) else Color(0xFF7E22CE),
                containerColor = if (isDark) Color(0xFF3B0764) else Color(0xFFF3E8FF),
                onContainerColor = if (isDark) Color(0xFFF3E8FF) else Color(0xFF3B0764),
                gradientBrush = if (isDark) {
                    Brush.linearGradient(listOf(Color(0xFF4A044E), Color(0xFF2A002E)))
                } else {
                    Brush.linearGradient(listOf(Color(0xFFFAF5FF), Color(0xFFF3E8FF)))
                },
                shortBadge = "روان‌شناسی"
            )

            cleanId.contains("economics") -> SubjectDesignToken(
                id = "economics",
                icon = Icons.Default.AccountBalance,
                accentColor = if (isDark) Color(0xFF38BDF8) else Color(0xFF0369A1),
                containerColor = if (isDark) Color(0xFF0C4A6E) else Color(0xFFE0F2FE),
                onContainerColor = if (isDark) Color(0xFFE0F2FE) else Color(0xFF082F49),
                gradientBrush = if (isDark) {
                    Brush.linearGradient(listOf(Color(0xFF075985), Color(0xFF082F49)))
                } else {
                    Brush.linearGradient(listOf(Color(0xFFF0F9FF), Color(0xFFE0F2FE)))
                },
                shortBadge = "اقتصاد"
            )

            cleanId.contains("farhangian") -> SubjectDesignToken(
                id = "farhangian",
                icon = Icons.Default.School,
                accentColor = if (isDark) Color(0xFF34D399) else Color(0xFF047857),
                containerColor = if (isDark) Color(0xFF064E3B) else Color(0xFFD1FAE5),
                onContainerColor = if (isDark) Color(0xFFD1FAE5) else Color(0xFF022C22),
                gradientBrush = if (isDark) {
                    Brush.linearGradient(listOf(Color(0xFF065F46), Color(0xFF022C22)))
                } else {
                    Brush.linearGradient(listOf(Color(0xFFECFDF5), Color(0xFFD1FAE5)))
                },
                shortBadge = "فرهنگیان"
            )

            cleanId.contains("dini") -> SubjectDesignToken(
                id = "dini",
                icon = Icons.Default.Spa,
                accentColor = if (isDark) Color(0xFF4ADE80) else Color(0xFF15803D),
                containerColor = if (isDark) Color(0xFF052E16) else Color(0xFFDCFCE7),
                onContainerColor = if (isDark) Color(0xFFDCFCE7) else Color(0xFF052E16),
                gradientBrush = if (isDark) {
                    Brush.linearGradient(listOf(Color(0xFF166534), Color(0xFF052E16)))
                } else {
                    Brush.linearGradient(listOf(Color(0xFFF0FDF4), Color(0xFFDCFCE7)))
                },
                shortBadge = "دین و زندگی"
            )

            cleanId.contains("sociology") -> SubjectDesignToken(
                id = "sociology",
                icon = Icons.Default.Groups,
                accentColor = if (isDark) Color(0xFF2DD4BF) else Color(0xFF0D9488),
                containerColor = if (isDark) Color(0xFF134E4A) else Color(0xFFCCFBF1),
                onContainerColor = if (isDark) Color(0xFFCCFBF1) else Color(0xFF042F2E),
                gradientBrush = if (isDark) {
                    Brush.linearGradient(listOf(Color(0xFF115E59), Color(0xFF042F2E)))
                } else {
                    Brush.linearGradient(listOf(Color(0xFFF0FDFA), Color(0xFFCCFBF1)))
                },
                shortBadge = "جامعه‌شناسی"
            )

            cleanId.contains("history") -> SubjectDesignToken(
                id = "history",
                icon = Icons.Default.HistoryEdu,
                accentColor = if (isDark) Color(0xFFFB923C) else Color(0xFFC2410C),
                containerColor = if (isDark) Color(0xFF431407) else Color(0xFFFFEDD5),
                onContainerColor = if (isDark) Color(0xFFFFEDD5) else Color(0xFF431407),
                gradientBrush = if (isDark) {
                    Brush.linearGradient(listOf(Color(0xFF7C2D12), Color(0xFF431407)))
                } else {
                    Brush.linearGradient(listOf(Color(0xFFFFF7ED), Color(0xFFFFEDD5)))
                },
                shortBadge = "تاریخ"
            )

            cleanId.contains("geography") -> SubjectDesignToken(
                id = "geography",
                icon = Icons.Default.Public,
                accentColor = if (isDark) Color(0xFFF97316) else Color(0xFFEA580C),
                containerColor = if (isDark) Color(0xFF431407) else Color(0xFFFFEDD5),
                onContainerColor = if (isDark) Color(0xFFFFEDD5) else Color(0xFF431407),
                gradientBrush = if (isDark) {
                    Brush.linearGradient(listOf(Color(0xFF9A3412), Color(0xFF431407)))
                } else {
                    Brush.linearGradient(listOf(Color(0xFFFFF7ED), Color(0xFFFFEDD5)))
                },
                shortBadge = "جغرافیا"
            )

            cleanId.contains("farsi") -> SubjectDesignToken(
                id = "farsi",
                icon = Icons.Default.MenuBook,
                accentColor = if (isDark) Color(0xFFF472B6) else Color(0xFFBE185D),
                containerColor = if (isDark) Color(0xFF500724) else Color(0xFFFCE7F3),
                onContainerColor = if (isDark) Color(0xFFFCE7F3) else Color(0xFF500724),
                gradientBrush = if (isDark) {
                    Brush.linearGradient(listOf(Color(0xFF831843), Color(0xFF500724)))
                } else {
                    Brush.linearGradient(listOf(Color(0xFFFDF2F8), Color(0xFFFCE7F3)))
                },
                shortBadge = "ادبیات فارسی"
            )

            cleanId.contains("negaresh") -> SubjectDesignToken(
                id = "negaresh",
                icon = Icons.Default.EditNote,
                accentColor = if (isDark) Color(0xFFF472B6) else Color(0xFFDB2777),
                containerColor = if (isDark) Color(0xFF700B33) else Color(0xFFFCE7F3),
                onContainerColor = if (isDark) Color(0xFFFCE7F3) else Color(0xFF500724),
                gradientBrush = if (isDark) {
                    Brush.linearGradient(listOf(Color(0xFF9D174D), Color(0xFF500724)))
                } else {
                    Brush.linearGradient(listOf(Color(0xFFFDF2F8), Color(0xFFFCE7F3)))
                },
                shortBadge = "نگارش"
            )

            cleanId.contains("english") -> SubjectDesignToken(
                id = "english",
                icon = Icons.Default.Language,
                accentColor = if (isDark) Color(0xFF60A5FA) else Color(0xFF2563EB),
                containerColor = if (isDark) Color(0xFF1E3A8A) else Color(0xFFDBEAFE),
                onContainerColor = if (isDark) Color(0xFFDBEAFE) else Color(0xFF1E3A8A),
                gradientBrush = if (isDark) {
                    Brush.linearGradient(listOf(Color(0xFF1D4ED8), Color(0xFF1E3A8A)))
                } else {
                    Brush.linearGradient(listOf(Color(0xFFEFF6FF), Color(0xFFDBEAFE)))
                },
                shortBadge = "زبان انگلیسی"
            )

            cleanId.contains("identity") || cleanId.contains("social") -> SubjectDesignToken(
                id = "social_identity",
                icon = Icons.Default.Badge,
                accentColor = if (isDark) Color(0xFF818CF8) else Color(0xFF4F46E5),
                containerColor = if (isDark) Color(0xFF1E1B4B) else Color(0xFFEEF2FF),
                onContainerColor = if (isDark) Color(0xFFEEF2FF) else Color(0xFF1E1B4B),
                gradientBrush = if (isDark) {
                    Brush.linearGradient(listOf(Color(0xFF3730A3), Color(0xFF1E1B4B)))
                } else {
                    Brush.linearGradient(listOf(Color(0xFFEEF2FF), Color(0xFFE0E7FF)))
                },
                shortBadge = "هویت اجتماعی"
            )

            cleanId.contains("health") || cleanId.contains("general") -> SubjectDesignToken(
                id = "general_humanities",
                icon = Icons.Default.Favorite,
                accentColor = if (isDark) Color(0xFF38BDF8) else Color(0xFF0284C7),
                containerColor = if (isDark) Color(0xFF082F49) else Color(0xFFE0F2FE),
                onContainerColor = if (isDark) Color(0xFFE0F2FE) else Color(0xFF082F49),
                gradientBrush = if (isDark) {
                    Brush.linearGradient(listOf(Color(0xFF0369A1), Color(0xFF082F49)))
                } else {
                    Brush.linearGradient(listOf(Color(0xFFF0F9FF), Color(0xFFE0F2FE)))
                },
                shortBadge = "سلامت و بهداشت"
            )

            else -> SubjectDesignToken(
                id = "default",
                icon = Icons.Default.MenuBook,
                accentColor = if (isDark) Color(0xFF82D5C8) else Color(0xFF006A60),
                containerColor = if (isDark) Color(0xFF005048) else Color(0xFF9EF2E4),
                onContainerColor = if (isDark) Color(0xFF9EF2E4) else Color(0xFF00201C),
                gradientBrush = if (isDark) {
                    Brush.linearGradient(listOf(Color(0xFF005048), Color(0xFF00201C)))
                } else {
                    Brush.linearGradient(listOf(Color(0xFFF0FDF4), Color(0xFFDCFCE7)))
                },
                shortBadge = "درس عمومی"
            )
        }
    }
}
