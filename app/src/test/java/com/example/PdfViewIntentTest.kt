package com.example

import android.content.Intent
import android.net.Uri
import com.example.ui.components.buildExternalPdfViewIntent
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

/**
 * Regression test for the official-PDF fallback path
 * (NativeTextbookPdfGenerator.startOfficialMinistryPdfDownload): when the
 * DownloadManager enqueue fails, the PDF URL is opened with an ACTION_VIEW
 * intent — which must carry FLAG_ACTIVITY_NEW_TASK, because the callers pass a
 * plain Context that is not guaranteed to be an Activity.
 */
@RunWith(RobolectricTestRunner::class)
class PdfViewIntentTest {

    @Test
    fun pdfViewIntent_isActionViewWithThePdfUrl() {
        val url = "https://chap.sch.ir/book/123"
        val intent = buildExternalPdfViewIntent(url)
        assertEquals(Intent.ACTION_VIEW, intent.action)
        assertEquals(Uri.parse(url), intent.data)
    }

    @Test
    fun pdfViewIntent_carriesFlagActivityNewTask() {
        val intent = buildExternalPdfViewIntent("https://chap.sch.ir/book/123")
        assertTrue(
            "FLAG_ACTIVITY_NEW_TASK must be set so startActivity() works from a non-Activity Context",
            (intent.flags and Intent.FLAG_ACTIVITY_NEW_TASK) != 0
        )
    }
}
