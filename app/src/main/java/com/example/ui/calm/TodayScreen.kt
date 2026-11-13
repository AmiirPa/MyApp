package com.example.ui.calm

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.data.curriculum.SubjectCatalog
import com.example.data.curriculum.normalizedSubjectId
import com.example.ui.KonkoorUiState
import com.example.ui.KonkoorViewModel

/**
 * امروز: فقط سه چیز — درس بعدیِ مسیر از صفر، مرورهای امروز، و یک حلقهٔ کوچک پیشرفت.
 */
@Composable
fun TodayScreen(viewModel: KonkoorViewModel, uiState: KonkoorUiState, nav: CalmNavigator) {
    val prefs = LocalCalmPrefs.current
    val reviewQueue by viewModel.adaptiveReviewQueue.collectAsStateWithLifecycle()
    val lessonById = remember(uiState.allLessons) { uiState.allLessons.associateBy { it.id } }
    val path = remember(prefs.startStage, uiState.allLessons, uiState.lessonProgressMap) {
        buildPathState(prefs.startStage, uiState.allLessons, lessonById, uiState.lessonProgressMap)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(bottom = 24.dp)
    ) {
        ScreenTitle(
            title = "امروز",
            subtitle = "قدم‌به‌قدم، از صفر تا صد",
            trailing = {
                ProgressRing(
                    fraction = path.fraction,
                    size = 54.dp,
                    label = faDigits((path.fraction * 100).toInt()) + "٪"
                )
            }
        )

        Spacer(Modifier.height(8.dp))
        CalmHeroIllustration()
        Spacer(Modifier.height(12.dp))

        // ── کارت بزرگ درس بعدی ──
        val next = path.nextLesson
        if (uiState.allLessons.isEmpty()) {
            CalmCard(modifier = Modifier.padding(horizontal = ScreenPadding)) {
                Text("در حال آماده کردن درس‌ها…", style = MaterialTheme.typography.titleMedium)
            }
        } else if (next == null) {
            CalmCard(modifier = Modifier.padding(horizontal = ScreenPadding), tonal = true) {
                Text("آفرین! همهٔ درس‌های این مسیر تمام شد.", style = MaterialTheme.typography.titleLarge, color = MaterialTheme.colorScheme.onPrimaryContainer)
                Spacer(Modifier.height(6.dp))
                Text("حالا وقت مرور و تست‌زنی است.", style = MaterialTheme.typography.bodyLarge, color = MaterialTheme.colorScheme.onPrimaryContainer)
                Spacer(Modifier.height(16.dp))
                PrimaryAction("برو به تمرین") { nav.push(Route.PickSubject(forQuiz = true)) }
            }
        } else {
            val subject = SubjectCatalog.getSubjectById(next.normalizedSubjectId())
            val started = path.doneLessons > 0
            CalmCard(
                modifier = Modifier.padding(horizontal = ScreenPadding),
                tonal = true,
                onClick = { nav.openLesson(next.id) },
                contentPadding = PaddingValues(22.dp)
            ) {
                Text(
                    if (started) "ادامهٔ مسیر" else "از همین‌جا شروع کن",
                    style = MaterialTheme.typography.labelLarge,
                    color = MaterialTheme.colorScheme.primary
                )
                Spacer(Modifier.height(10.dp))
                Text(
                    next.title,
                    style = MaterialTheme.typography.headlineSmall,
                    color = MaterialTheme.colorScheme.onPrimaryContainer,
                    maxLines = 3,
                    overflow = TextOverflow.Ellipsis
                )
                Spacer(Modifier.height(6.dp))
                val stepLine = path.nextStep?.let { "${it.stageLabel} · ${it.step.title}" } ?: subject.shortTitle
                Text(
                    stepLine,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.75f),
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
                Spacer(Modifier.height(20.dp))
                Row(verticalAlignment = Alignment.CenterVertically) {
                    PrimaryAction(if (started) "ادامه بده" else "شروع کن") { nav.openLesson(next.id) }
                    Spacer(Modifier.width(14.dp))
                    Text(
                        "حدود ${faDigits(next.estimatedMinutes.coerceAtLeast(5))} دقیقه",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.7f)
                    )
                }
            }
            Spacer(Modifier.height(10.dp))
            Text(
                "${faDigits(path.doneLessons)} از ${faDigits(path.totalLessons)} درس مسیر",
                modifier = Modifier.padding(horizontal = ScreenPadding + 4.dp),
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }

        // ── مرور امروز ──
        SectionLabel("مرور امروز")
        if (reviewQueue.isEmpty()) {
            QuietNote("امروز چیزی برای مرور نداری. هر درسی که تمام کنی، مرورش به‌موقع همین‌جا می‌آید.")
        } else {
            CalmCard(
                modifier = Modifier.padding(horizontal = ScreenPadding),
                onClick = { nav.push(Route.Review) }
            ) {
                Text(
                    "${faDigits(reviewQueue.size)} مورد برای مرور",
                    style = MaterialTheme.typography.titleMedium
                )
                Spacer(Modifier.height(8.dp))
                Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                    reviewQueue.take(3).forEach { card ->
                        val title = lessonById[card.topicId]?.title
                            ?: SubjectCatalog.getSubjectById(card.subjectId).shortTitle
                        Text(
                            "• $title",
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                    }
                }
                Spacer(Modifier.height(14.dp))
                Row(modifier = Modifier.fillMaxWidth()) {
                    SecondaryAction("شروع مرور") { nav.push(Route.Review) }
                }
            }
        }
    }
}
