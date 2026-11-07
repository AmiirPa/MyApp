package com.example.ui.ai

import android.content.Context
import android.content.Intent
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

/**
 * AI features disabled per user request.
 * Functions and components are maintained as safe no-op stubs to avoid breaking imports.
 */
fun configureAppCheck(context: Context, intent: Intent?) {
    // Disabled per user instruction
}

@Composable
fun GeminiSmartTutorCard(
    subjectTitle: String,
    topicTitle: String,
    gradeLevel: String,
    modifier: Modifier = Modifier,
    onFormulaGenerated: (String) -> Unit = {}
) {
    // Hidden per user request: AI API removed in favor of 100% complete built-in curated pedagogical lessons
}
