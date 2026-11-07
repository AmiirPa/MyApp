package com.example.ui.screens

import android.os.Build
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Logout
import androidx.compose.material.icons.filled.Brightness4
import androidx.compose.material.icons.filled.BrightnessHigh
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.CloudDone
import androidx.compose.material.icons.filled.CloudSync
import androidx.compose.material.icons.filled.ExpandLess
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.material.icons.filled.FontDownload
import androidx.compose.material.icons.filled.FormatSize
import androidx.compose.material.icons.filled.MenuBook
import androidx.compose.material.icons.filled.Palette
import androidx.compose.material.icons.filled.RestartAlt
import androidx.compose.material.icons.filled.SettingsBrightness
import androidx.compose.material.icons.filled.UploadFile
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Slider
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.sp
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.credentials.CredentialManager
import androidx.compose.foundation.BorderStroke
import com.example.R
import com.example.data.firebase.onGoogleSignInClicked
import com.example.data.firebase.signOut
import com.example.ui.components.BiDiText
import com.example.ui.components.BidiFormatter
import com.example.ui.theme.AppColorPalette
import com.example.ui.theme.AppThemeMode
import androidx.compose.foundation.isSystemInDarkTheme
import com.example.ui.theme.BuiltInFontPreset
import com.example.ui.theme.DiacriticsColorPalette
import com.example.ui.theme.HighlighterColorPalette
import com.example.ui.theme.LocalAppThemeController
import com.example.ui.theme.TextAlignmentPreset
import com.example.ui.theme.UnderlineColorPalette
import java.io.File
import java.io.FileInputStream

@Composable
fun ExpressiveDynamicAppLogo(
    modifier: Modifier = Modifier,
    size: Dp = 64.dp
) {
    Surface(
        modifier = modifier.size(size),
        shape = RoundedCornerShape(percent = 28),
        color = MaterialTheme.colorScheme.primaryContainer,
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant)
    ) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = Icons.Default.MenuBook,
                contentDescription = "لوگوی صفر تا فرهنگیان",
                tint = MaterialTheme.colorScheme.onPrimaryContainer,
                modifier = Modifier.size(size * 0.52f)
            )
        }
    }
}

@OptIn(ExperimentalLayoutApi::class, ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(
    currentUserId: String? = null,
    userDisplayName: String? = null,
    onShowMessage: (String) -> Unit
) {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    val credentialManager = remember(context) { CredentialManager.create(context) }
    val themeController = LocalAppThemeController.current
    var isSigningIn by remember { mutableStateOf(false) }
    var showResetDialog by remember { mutableStateOf(false) }

    if (showResetDialog) {
        AlertDialog(
            onDismissRequest = { showResetDialog = false },
            icon = {
                Icon(
                    imageVector = Icons.Default.RestartAlt,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.error,
                    modifier = Modifier.size(28.dp)
                )
            },
            title = {
                Text(
                    text = "بازنشانی کلی به تنظیمات اولیه",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )
            },
            text = {
                Text(
                    text = "آیا اطمینان دارید که می‌خواهید تمامی تنظیمات برنامه (شامل حالت نمایش، فونت فعال، رنگ‌بندی، سایز متن، فاصله خطوط، اعراب و نمایش اعداد) به حالت اولیه کارخانه بازگردند؟",
                    style = MaterialTheme.typography.bodyMedium
                )
            },
            confirmButton = {
                Button(
                    onClick = {
                        showResetDialog = false
                        themeController.resetAllAppearanceToStandard()
                        onShowMessage("کلیه تنظیمات برنامه به حالت اولیه کارخانه بازنشانی شدند.")
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.error)
                ) {
                    Text("بله، بازنشانی شود")
                }
            },
            dismissButton = {
                OutlinedButton(
                    onClick = { showResetDialog = false }
                ) {
                    Text("انصراف")
                }
            }
        )
    }

    val fontPickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.OpenDocument()
    ) { uri ->
        if (uri != null) {
            themeController.importCustomFontFromUri(uri)
                .onSuccess { fileName ->
                    onShowMessage("فونت «$fileName» فعال شد.")
                }
                .onFailure {
                    onShowMessage("فایل فونت معتبر (.ttf یا .otf) انتخاب کنید.")
                }
        }
    }

    var showAdvancedSettings by remember { mutableStateOf(false) }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .testTag("settings_screen"),
        contentPadding = PaddingValues(start = 20.dp, top = 12.dp, end = 20.dp, bottom = 92.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        // ۱. کارت حالت نمایش و رنگ پویا (اصلی‌ترین تنظیمات ظاهری)
        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                border = BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant),
                elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.Default.SettingsBrightness,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.primary,
                            modifier = Modifier.size(20.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "حالت نمایش و رنگ‌بندی",
                            style = MaterialTheme.typography.titleSmall,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                    }

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        AppThemeMode.entries.forEach { mode ->
                            val selected = themeController.themeMode == mode
                            val modeIcon = when (mode) {
                                AppThemeMode.LIGHT -> Icons.Default.BrightnessHigh
                                AppThemeMode.DARK -> Icons.Default.Brightness4
                                AppThemeMode.SYSTEM -> Icons.Default.SettingsBrightness
                            }
                            Surface(
                                modifier = Modifier
                                    .weight(1f)
                                    .clip(RoundedCornerShape(12.dp))
                                    .clickable {
                                        themeController.updateThemeMode(mode)
                                    }
                                    .testTag("theme_mode_${mode.name.lowercase()}"),
                                shape = RoundedCornerShape(12.dp),
                                color = if (selected) {
                                    MaterialTheme.colorScheme.primary
                                } else {
                                    MaterialTheme.colorScheme.background
                                },
                                border = BorderStroke(
                                    1.dp,
                                    if (selected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.outlineVariant
                                )
                            ) {
                                Column(
                                    modifier = Modifier.padding(vertical = 10.dp, horizontal = 6.dp),
                                    horizontalAlignment = Alignment.CenterHorizontally,
                                    verticalArrangement = Arrangement.spacedBy(4.dp)
                                ) {
                                    Icon(
                                        imageVector = modeIcon,
                                        contentDescription = null,
                                        tint = if (selected) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.onSurfaceVariant,
                                        modifier = Modifier.size(18.dp)
                                    )
                                    Text(
                                        text = mode.shortTitle,
                                        style = MaterialTheme.typography.labelMedium,
                                        fontWeight = if (selected) FontWeight.Bold else FontWeight.Medium,
                                        color = if (selected) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.onSurface,
                                        textAlign = TextAlign.Center
                                    )
                                }
                            }
                        }
                    }

                    val systemDark = isSystemInDarkTheme()
                    val currentIsDark = when (themeController.themeMode) {
                        AppThemeMode.DARK -> true
                        AppThemeMode.LIGHT -> false
                        AppThemeMode.SYSTEM -> systemDark
                    }
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(12.dp))
                            .background(MaterialTheme.colorScheme.background)
                            .border(1.dp, MaterialTheme.colorScheme.outlineVariant, RoundedCornerShape(12.dp))
                            .clickable {
                                val newMode = if (currentIsDark) AppThemeMode.LIGHT else AppThemeMode.DARK
                                themeController.updateThemeMode(newMode)
                            }
                            .padding(horizontal = 12.dp, vertical = 10.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                text = "حالت روز با کنتراست بالا",
                                style = MaterialTheme.typography.bodyMedium,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.onSurface
                            )
                            Text(
                                text = if (currentIsDark) "حالت تاریک فعال است" else "حالت روشن فعال است",
                                style = MaterialTheme.typography.labelSmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                        Switch(
                            checked = !currentIsDark,
                            onCheckedChange = { isLight ->
                                val newMode = if (isLight) AppThemeMode.LIGHT else AppThemeMode.DARK
                                themeController.updateThemeMode(newMode)
                            },
                            modifier = Modifier.testTag("high_contrast_day_mode_switch")
                        )
                    }

                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clip(RoundedCornerShape(12.dp))
                                .background(MaterialTheme.colorScheme.background)
                                .border(1.dp, MaterialTheme.colorScheme.outlineVariant, RoundedCornerShape(12.dp))
                                .padding(horizontal = 12.dp, vertical = 8.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column(modifier = Modifier.weight(1f)) {
                                Text(
                                    text = "رنگ پویا (Material You)",
                                    style = MaterialTheme.typography.bodyMedium,
                                    fontWeight = FontWeight.Bold,
                                    color = MaterialTheme.colorScheme.onSurface
                                )
                                Text(
                                    text = "هماهنگی رنگ برنامه با تصویر زمینه دستگاه",
                                    style = MaterialTheme.typography.labelSmall,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                            }
                            Switch(
                                checked = themeController.useDynamicColor,
                                onCheckedChange = { themeController.updateDynamicColor(it) },
                                modifier = Modifier.testTag("dynamic_color_switch")
                            )
                        }
                    }

                    HorizontalDivider(color = MaterialTheme.colorScheme.outlineVariant)

                    // انتخاب زبان سراسری برنامه (فارسی RTL / انگلیسی LTR)
                    Text(
                        text = themeController.tr("زبان و جهت چیدمان برنامه:", "App Language & Layout Direction:"),
                        style = MaterialTheme.typography.labelMedium,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        listOf(
                            Triple("fa", "فارسی (راست‌به‌چپ)", "lang_mode_fa"),
                            Triple("en", "English (Left-to-Right)", "lang_mode_en")
                        ).forEach { (langCode, langTitle, tag) ->
                            val isLangSelected = themeController.appLanguage == langCode
                            Surface(
                                onClick = { themeController.updateAppLanguage(langCode) },
                                shape = RoundedCornerShape(10.dp),
                                color = if (isLangSelected) MaterialTheme.colorScheme.primaryContainer else MaterialTheme.colorScheme.background,
                                border = BorderStroke(
                                    1.dp,
                                    if (isLangSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.outlineVariant
                                ),
                                modifier = Modifier
                                    .weight(1f)
                                    .testTag(tag)
                            ) {
                                Text(
                                    text = langTitle,
                                    style = MaterialTheme.typography.labelMedium,
                                    fontWeight = if (isLangSelected) FontWeight.Bold else FontWeight.Medium,
                                    color = if (isLangSelected) MaterialTheme.colorScheme.onPrimaryContainer else MaterialTheme.colorScheme.onSurface,
                                    textAlign = TextAlign.Center,
                                    modifier = Modifier.padding(vertical = 9.dp, horizontal = 8.dp)
                                )
                            }
                        }
                    }

                    HorizontalDivider(color = MaterialTheme.colorScheme.outlineVariant)

                    // ۵ پالت رنگی منتخب با کنتراست استاندارد + پیش‌نمایش زنده کامپوننت‌ها
                    var showAllPalettes by remember { mutableStateOf(false) }
                    val displayedPalettes = if (showAllPalettes) AppColorPalette.entries else AppColorPalette.featuredPalettes

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                imageVector = Icons.Default.Palette,
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.primary,
                                modifier = Modifier.size(18.dp)
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(
                                text = "پالت‌های رنگی منتخب (استاندارد کنتراست)",
                                style = MaterialTheme.typography.bodySmall,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.onSurface
                            )
                        }
                        Text(
                            text = if (showAllPalettes) "نمایش ۵ پالت منتخب" else "مشاهده همه (${BidiFormatter.applyPreferredDigits(AppColorPalette.entries.size.toString())})",
                            style = MaterialTheme.typography.labelSmall,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.primary,
                            modifier = Modifier
                                .clip(RoundedCornerShape(6.dp))
                                .clickable { showAllPalettes = !showAllPalettes }
                                .padding(horizontal = 6.dp, vertical = 4.dp)
                        )
                    }

                    FlowRow(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(6.dp),
                        verticalArrangement = Arrangement.spacedBy(6.dp),
                        maxItemsInEachRow = 2
                    ) {
                        displayedPalettes.forEach { palette ->
                            val isSelected = !themeController.useDynamicColor &&
                                themeController.selectedColorPalette == palette
                            Surface(
                                modifier = Modifier
                                    .weight(1f)
                                    .clip(RoundedCornerShape(10.dp))
                                    .clickable {
                                        themeController.selectColorPalette(palette)
                                    }
                                    .testTag("featured_palette_${palette.name.lowercase()}"),
                                shape = RoundedCornerShape(10.dp),
                                color = if (isSelected) MaterialTheme.colorScheme.primaryContainer else MaterialTheme.colorScheme.background,
                                border = BorderStroke(
                                    1.dp,
                                    if (isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.outlineVariant
                                )
                            ) {
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(horizontal = 9.dp, vertical = 8.dp),
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                                ) {
                                    Box(
                                        modifier = Modifier
                                            .size(18.dp)
                                            .clip(CircleShape)
                                            .background(palette.previewPrimary)
                                    )
                                    Column(modifier = Modifier.weight(1f)) {
                                        Text(
                                            text = palette.persianTitle,
                                            style = MaterialTheme.typography.labelMedium,
                                            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
                                            color = if (isSelected) MaterialTheme.colorScheme.onPrimaryContainer else MaterialTheme.colorScheme.onSurface,
                                            maxLines = 1
                                        )
                                        Text(
                                            text = palette.persianSubtitle,
                                            style = MaterialTheme.typography.labelSmall,
                                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                                            maxLines = 1,
                                            overflow = TextOverflow.Ellipsis
                                        )
                                    }
                                    if (isSelected) {
                                        Icon(
                                            imageVector = Icons.Default.CheckCircle,
                                            contentDescription = null,
                                            tint = MaterialTheme.colorScheme.primary,
                                            modifier = Modifier.size(16.dp)
                                        )
                                    }
                                }
                            }
                        }
                    }

                    // پیش‌نمایش زنده کامپوننت‌های واقعی (کارت، تیتر، متن، دکمه، جدول و هایلایت)
                    Surface(
                        modifier = Modifier
                            .fillMaxWidth()
                            .testTag("theme_live_component_preview"),
                        shape = RoundedCornerShape(12.dp),
                        color = MaterialTheme.colorScheme.background,
                        border = BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant)
                    ) {
                        Column(
                            modifier = Modifier.padding(12.dp),
                            verticalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    text = "پیش‌نمایش زنده ظاهر و خوانایی",
                                    style = MaterialTheme.typography.titleSmall,
                                    fontWeight = FontWeight.Bold,
                                    color = MaterialTheme.colorScheme.primary
                                )
                                Surface(
                                    shape = RoundedCornerShape(8.dp),
                                    color = MaterialTheme.colorScheme.primary
                                ) {
                                    Text(
                                        text = "دکمه نمونه",
                                        style = MaterialTheme.typography.labelSmall,
                                        fontWeight = FontWeight.Bold,
                                        color = MaterialTheme.colorScheme.onPrimary,
                                        modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp)
                                    )
                                }
                            }
                            BiDiText(
                                text = "نکته کلیدی: در گزاره شرطی «p ⟹ q» تنها حالت نادرست **مقدم درست و تالی نادرست** است.",
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.onSurface
                            )
                            Surface(
                                shape = RoundedCornerShape(8.dp),
                                color = MaterialTheme.colorScheme.surface,
                                border = BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant),
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(horizontal = 10.dp, vertical = 6.dp),
                                    horizontalArrangement = Arrangement.SpaceBetween
                                ) {
                                    Text(
                                        text = BidiFormatter.applyPreferredDigits("ردیف ۱: جدول نمونه"),
                                        style = MaterialTheme.typography.labelSmall,
                                        fontWeight = FontWeight.Bold,
                                        color = MaterialTheme.colorScheme.primary
                                    )
                                    Text(
                                        text = BidiFormatter.applyPreferredDigits("کنتراست ۱۰۰٪ خوانا"),
                                        style = MaterialTheme.typography.labelSmall,
                                        color = MaterialTheme.colorScheme.onSurfaceVariant
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }

        // ۲. کارت فونت فارسی، اندازه نوشته و نمایش اعداد (اصلی‌ترین تنظیمات مطالعه)
        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                border = BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant),
                elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    var showFontPopup by remember { mutableStateOf(false) }

                    if (showFontPopup) {
                        ModalBottomSheet(
                            onDismissRequest = { showFontPopup = false }
                        ) {
                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(16.dp),
                                verticalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                Text(
                                    text = "انتخاب فونت برنامه (${BuiltInFontPreset.entries.size} فونت)",
                                    style = MaterialTheme.typography.titleMedium,
                                    fontWeight = FontWeight.Bold,
                                    color = MaterialTheme.colorScheme.primary
                                )
                                HorizontalDivider(color = MaterialTheme.colorScheme.outlineVariant)
                                LazyColumn(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(420.dp),
                                    verticalArrangement = Arrangement.spacedBy(6.dp)
                                ) {
                                    items(BuiltInFontPreset.entries.toList()) { preset ->
                                        val isSelected = themeController.customFontName == null &&
                                            themeController.selectedFontPreset == preset
                                        val presetFontFamily = remember(preset, themeController.isBoldTextEnabled) {
                                            preset.toFontFamily(forceBold = themeController.isBoldTextEnabled)
                                        }
                                        Surface(
                                            onClick = {
                                                themeController.selectBuiltInFontPreset(preset)
                                                showFontPopup = false
                                                onShowMessage("فونت «${preset.persianTitle}» اعمال شد.")
                                            },
                                            shape = RoundedCornerShape(10.dp),
                                            color = if (isSelected) MaterialTheme.colorScheme.primaryContainer else MaterialTheme.colorScheme.surface,
                                            border = BorderStroke(
                                                1.dp,
                                                if (isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.outlineVariant
                                            ),
                                            modifier = Modifier.fillMaxWidth()
                                        ) {
                                            Row(
                                                modifier = Modifier.padding(12.dp),
                                                verticalAlignment = Alignment.CenterVertically,
                                                horizontalArrangement = Arrangement.SpaceBetween
                                            ) {
                                                Column(modifier = Modifier.weight(1f)) {
                                                    Text(
                                                        text = preset.persianTitle,
                                                        style = MaterialTheme.typography.bodyMedium.copy(fontFamily = presetFontFamily),
                                                        fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
                                                        color = if (isSelected) MaterialTheme.colorScheme.onPrimaryContainer else MaterialTheme.colorScheme.onSurface
                                                    )
                                                    Text(
                                                        text = preset.persianSubtitle,
                                                        style = MaterialTheme.typography.labelSmall.copy(fontFamily = presetFontFamily),
                                                        color = MaterialTheme.colorScheme.onSurfaceVariant
                                                    )
                                                }
                                                if (isSelected) {
                                                    Icon(
                                                        imageVector = Icons.Default.CheckCircle,
                                                        contentDescription = null,
                                                        tint = MaterialTheme.colorScheme.primary,
                                                        modifier = Modifier.size(20.dp)
                                                    )
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }

                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.Default.FontDownload,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.primary,
                            modifier = Modifier.size(20.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        BiDiText(
                            text = "فونت و اندازه متن مطالعه",
                            style = MaterialTheme.typography.titleSmall,
                            fontWeight = FontWeight.Bold
                        )
                    }

                    Button(
                        onClick = { showFontPopup = true },
                        shape = RoundedCornerShape(12.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .testTag("open_font_popup_btn")
                    ) {
                        Icon(Icons.Default.FontDownload, contentDescription = null, modifier = Modifier.size(18.dp))
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "انتخاب فونت (${themeController.selectedFontPreset.persianTitle})",
                            style = MaterialTheme.typography.labelLarge
                        )
                    }

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        OutlinedButton(
                            onClick = {
                                fontPickerLauncher.launch(
                                    arrayOf("font/*", "application/x-font-ttf", "application/x-font-otf", "*/*")
                                )
                            },
                            shape = RoundedCornerShape(10.dp),
                            modifier = Modifier
                                .weight(1f)
                                .testTag("import_custom_font_button")
                        ) {
                            Icon(Icons.Default.UploadFile, contentDescription = null, modifier = Modifier.size(16.dp))
                            Spacer(modifier = Modifier.width(6.dp))
                            Text("بارگذاری فونت دلخواه (TTF)", style = MaterialTheme.typography.labelMedium)
                        }

                        if (themeController.customFontName != null) {
                            OutlinedButton(
                                onClick = {
                                    themeController.resetToDefaultFont()
                                },
                                shape = RoundedCornerShape(10.dp),
                                modifier = Modifier.testTag("reset_font_button")
                            ) {
                                Icon(Icons.Default.RestartAlt, contentDescription = null, modifier = Modifier.size(16.dp))
                                Spacer(modifier = Modifier.width(4.dp))
                                Text("پیش‌فرض", style = MaterialTheme.typography.labelMedium)
                            }
                        }
                    }

                    HorizontalDivider(color = MaterialTheme.colorScheme.outlineVariant)

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Default.FormatSize,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.primary,
                            modifier = Modifier.size(18.dp)
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = BidiFormatter.formatMixedPersianText(
                                "اندازه متن: ${(themeController.fontScale * 100).toInt()}٪"
                            ),
                            style = MaterialTheme.typography.bodySmall,
                            fontWeight = FontWeight.Bold
                        )
                    }

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        listOf(
                            0.85f to "۸۵٪ ظریف",
                            0.95f to "۹۵٪ متعادل",
                            1.00f to "۱۰۰٪ استاندارد",
                            1.12f to "۱۱۲٪ درشت"
                        ).forEach { (scaleVal, label) ->
                            val isSelected = kotlin.math.abs(themeController.fontScale - scaleVal) < 0.03f
                            Surface(
                                onClick = { themeController.updateFontScale(scaleVal) },
                                shape = RoundedCornerShape(8.dp),
                                color = if (isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.background,
                                border = BorderStroke(
                                    1.dp,
                                    if (isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.outlineVariant
                                ),
                                modifier = Modifier.weight(1f)
                            ) {
                                Text(
                                    text = BidiFormatter.applyPreferredDigits(label),
                                    style = MaterialTheme.typography.labelSmall,
                                    fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
                                    color = if (isSelected) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.onSurface,
                                    textAlign = TextAlign.Center,
                                    modifier = Modifier.padding(vertical = 7.dp, horizontal = 2.dp)
                                )
                            }
                        }
                    }

                    Slider(
                        value = themeController.fontScale,
                        onValueChange = { themeController.updateFontScale(it) },
                        valueRange = 0.80f..1.35f,
                        modifier = Modifier.testTag("font_scale_slider")
                    )

                    HorizontalDivider(color = MaterialTheme.colorScheme.outlineVariant)

                    // کنترل مستقل وزن و ضخامت فونت (بدون تغییر اندازه فونت)
                    Text(
                        text = "وزن و ضخامت نوشته (مستقل از اندازه متن):",
                        style = MaterialTheme.typography.bodySmall,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        listOf(
                            Triple(0, "معمولی (Regular)", "font_weight_regular"),
                            Triple(1, "نیمه‌پررنگ (Medium)", "font_weight_medium"),
                            Triple(2, "پررنگ (Bold)", "font_weight_bold")
                        ).forEach { (level, label, tag) ->
                            val isWeightSelected = themeController.fontWeightLevel == level
                            Surface(
                                onClick = { themeController.updateFontWeightLevel(level) },
                                shape = RoundedCornerShape(8.dp),
                                color = if (isWeightSelected) MaterialTheme.colorScheme.primaryContainer else MaterialTheme.colorScheme.background,
                                border = BorderStroke(
                                    1.dp,
                                    if (isWeightSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.outlineVariant
                                ),
                                modifier = Modifier
                                    .weight(1f)
                                    .testTag(tag)
                            ) {
                                Text(
                                    text = label,
                                    style = MaterialTheme.typography.labelSmall,
                                    fontWeight = when (level) {
                                        2 -> FontWeight.Bold
                                        1 -> FontWeight.Medium
                                        else -> FontWeight.Normal
                                    },
                                    color = if (isWeightSelected) MaterialTheme.colorScheme.onPrimaryContainer else MaterialTheme.colorScheme.onSurface,
                                    textAlign = TextAlign.Center,
                                    modifier = Modifier.padding(vertical = 8.dp, horizontal = 4.dp)
                                )
                            }
                        }
                    }

                    HorizontalDivider(color = MaterialTheme.colorScheme.outlineVariant)

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        listOf(
                            Triple(true, "اعداد فارسی (۱۲۳۴)", "digits_mode_persian"),
                            Triple(false, "اعداد انگلیسی (1234)", "digits_mode_english")
                        ).forEach { (isPersian, label, tag) ->
                            val selected = themeController.usePersianDigits == isPersian
                            Surface(
                                modifier = Modifier
                                    .weight(1f)
                                    .clip(RoundedCornerShape(10.dp))
                                    .clickable {
                                        themeController.updatePersianDigits(isPersian)
                                    }
                                    .testTag(tag),
                                shape = RoundedCornerShape(10.dp),
                                color = if (selected) {
                                    MaterialTheme.colorScheme.primaryContainer
                                } else {
                                    MaterialTheme.colorScheme.background
                                },
                                border = BorderStroke(
                                    1.dp,
                                    if (selected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.outlineVariant
                                )
                            ) {
                                Box(
                                    modifier = Modifier.padding(vertical = 9.dp, horizontal = 8.dp),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Text(
                                        text = label,
                                        style = MaterialTheme.typography.labelMedium,
                                        fontWeight = if (selected) FontWeight.Bold else FontWeight.Medium,
                                        color = if (selected) MaterialTheme.colorScheme.onPrimaryContainer else MaterialTheme.colorScheme.onSurface,
                                        textAlign = TextAlign.Center,
                                        maxLines = 1
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }

        // ۲.۵ کارت تنظیمات یادگیری از منفی صفر، نمایش جدول، صدا و حافظه آفلاین (Part 13)
        item {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("learning_personalization_card"),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                border = BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant),
                elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Text(
                        text = "تنظیمات یادگیری، جدول‌ها، صدا و مطالعه آفلاین",
                        style = MaterialTheme.typography.titleSmall,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurface
                    )

                    // ۱. انتخاب سطح شروع
                    Text(
                        text = "سطح شروع آموزش در تمام دروس:",
                        style = MaterialTheme.typography.labelMedium,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        listOf(
                            "MINUS_ZERO" to "منفی صفر",
                            "BEGINNER" to "مقدماتی",
                            "INTERMEDIATE" to "متوسط",
                            "ADVANCED" to "پیشرفته"
                        ).forEach { (key, label) ->
                            val sel = themeController.learningStartLevel == key
                            Surface(
                                onClick = { themeController.updateLearningStartLevel(key) },
                                shape = RoundedCornerShape(8.dp),
                                color = if (sel) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.background,
                                border = BorderStroke(
                                    1.dp,
                                    if (sel) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.outlineVariant
                                ),
                                modifier = Modifier.weight(1f)
                            ) {
                                Text(
                                    text = label,
                                    style = MaterialTheme.typography.labelSmall,
                                    fontWeight = if (sel) FontWeight.Bold else FontWeight.Medium,
                                    color = if (sel) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.onSurface,
                                    textAlign = TextAlign.Center,
                                    modifier = Modifier.padding(vertical = 8.dp, horizontal = 2.dp)
                                )
                            }
                        }
                    }

                    // ۲. میزان جزئیات توضیح درس‌نامه
                    Text(
                        text = "میزان جزئیات توضیحات درس‌نامه:",
                        style = MaterialTheme.typography.labelMedium,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        listOf(
                            "BRIEF" to "کوتاه و خلاصه",
                            "NORMAL" to "معمولی",
                            "COMPLETE" to "کامل و تشریحی"
                        ).forEach { (key, label) ->
                            val sel = themeController.explanationDetailLevel == key
                            Surface(
                                onClick = { themeController.updateExplanationDetailLevel(key) },
                                shape = RoundedCornerShape(8.dp),
                                color = if (sel) MaterialTheme.colorScheme.primaryContainer else MaterialTheme.colorScheme.background,
                                border = BorderStroke(
                                    1.dp,
                                    if (sel) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.outlineVariant
                                ),
                                modifier = Modifier.weight(1f)
                            ) {
                                Text(
                                    text = label,
                                    style = MaterialTheme.typography.labelSmall,
                                    fontWeight = if (sel) FontWeight.Bold else FontWeight.Medium,
                                    color = if (sel) MaterialTheme.colorScheme.onPrimaryContainer else MaterialTheme.colorScheme.onSurface,
                                    textAlign = TextAlign.Center,
                                    modifier = Modifier.padding(vertical = 8.dp, horizontal = 4.dp)
                                )
                            }
                        }
                    }

                    // ۳. حالت پیش‌فرض نمایش جدول‌های چندستونی
                    Text(
                        text = "حالت پیش‌فرض نمایش جدول‌های آموزشی:",
                        style = MaterialTheme.typography.labelMedium,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        listOf(
                            "AUTO" to "خودکار",
                            "TABLE_SCROLL" to "جدول افقی",
                            "CARDS" to "کارت مقایسه‌ای"
                        ).forEach { (key, label) ->
                            val sel = themeController.tableDisplayPreference == key
                            Surface(
                                onClick = { themeController.updateTableDisplayPreference(key) },
                                shape = RoundedCornerShape(8.dp),
                                color = if (sel) MaterialTheme.colorScheme.primaryContainer else MaterialTheme.colorScheme.background,
                                border = BorderStroke(
                                    1.dp,
                                    if (sel) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.outlineVariant
                                ),
                                modifier = Modifier.weight(1f)
                            ) {
                                Text(
                                    text = label,
                                    style = MaterialTheme.typography.labelSmall,
                                    fontWeight = if (sel) FontWeight.Bold else FontWeight.Medium,
                                    color = if (sel) MaterialTheme.colorScheme.onPrimaryContainer else MaterialTheme.colorScheme.onSurface,
                                    textAlign = TextAlign.Center,
                                    modifier = Modifier.padding(vertical = 8.dp, horizontal = 4.dp)
                                )
                            }
                        }
                    }

                    // ۴. سوئیچ‌های کاربردی یادگیری و دسترس‌پذیری
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "نمایش پیش‌نیازها در ابتدای هر درس",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurface,
                            modifier = Modifier.weight(1f)
                        )
                        Switch(
                            checked = themeController.showPrerequisitesBeforeLesson,
                            onCheckedChange = { themeController.updateShowPrerequisitesBeforeLesson(it) }
                        )
                    }

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "فعال‌سازی رمزگذاری‌ها و تکنیک‌های حفظ (Mnemonic)",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurface,
                            modifier = Modifier.weight(1f)
                        )
                        Switch(
                            checked = themeController.showMnemonicsAndMemoryTricks,
                            onCheckedChange = { themeController.updateShowMnemonicsAndMemoryTricks(it) }
                        )
                    }

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "کاهش حرکت و انیمیشن (تکز و دسترس‌پذیری)",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurface,
                            modifier = Modifier.weight(1f)
                        )
                        Switch(
                            checked = themeController.reduceMotionEnabled,
                            onCheckedChange = { themeController.updateReduceMotion(it) }
                        )
                    }

                    // ۵. هدف مطالعه روزانه و پاک‌سازی امن حافظه موقت آفلاین
                    HorizontalDivider(color = MaterialTheme.colorScheme.outlineVariant)
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                text = "بسته کامل آفلاین و مدیریت حافظه",
                                style = MaterialTheme.typography.bodySmall,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.onSurface
                            )
                            Text(
                                text = "تمام درس‌نامه‌ها، جداول و نمودارها ۱۰۰٪ آفلاین روی گوشی فعال‌اند.",
                                style = MaterialTheme.typography.labelSmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                        OutlinedButton(
                            onClick = {
                                val msg = themeController.clearTemporaryCache()
                                onShowMessage(msg)
                            },
                            shape = RoundedCornerShape(10.dp)
                        ) {
                            Text("پاک‌سازی کش", style = MaterialTheme.typography.labelSmall)
                        }
                    }
                }
            }
        }

        // ۳. پشتیبان‌گیری ابری و حساب کاربری
        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                border = BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant),
                elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = if (currentUserId != null) Icons.Default.CloudDone else Icons.Default.CloudSync,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.primary
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "حساب کاربری و پشتیبان‌گیری ابری",
                            style = MaterialTheme.typography.titleSmall,
                            fontWeight = FontWeight.Bold
                        )
                    }

                    if (currentUserId == null) {
                        Button(
                            onClick = {
                                isSigningIn = true
                                onGoogleSignInClicked(
                                    context = context,
                                    credentialManager = credentialManager,
                                    onAuthSuccess = {
                                        isSigningIn = false
                                        onShowMessage("حساب گوگل متصل شد.")
                                    },
                                    onAuthError = { errMsg ->
                                        isSigningIn = false
                                        onShowMessage(errMsg)
                                    },
                                    scope = scope,
                                    onAuthCancelled = { isSigningIn = false }
                                )
                            },
                            enabled = !isSigningIn,
                            shape = RoundedCornerShape(12.dp),
                            modifier = Modifier
                                .fillMaxWidth()
                                .testTag("optional_google_sign_in_button")
                        ) {
                            if (isSigningIn) {
                                CircularProgressIndicator(
                                    modifier = Modifier.size(18.dp),
                                    strokeWidth = 2.dp,
                                    color = MaterialTheme.colorScheme.onPrimary
                                )
                                Spacer(modifier = Modifier.width(8.dp))
                            }
                            Text("اتصال به حساب گوگل")
                        }
                    } else {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "متصل: ${userDisplayName ?: "کاربر گوگل"}",
                                style = MaterialTheme.typography.bodyMedium,
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier.weight(1f)
                            )
                            OutlinedButton(
                                onClick = {
                                    signOut(
                                        context = context,
                                        credentialManager = credentialManager,
                                        onSignOutComplete = {
                                            onShowMessage("از حساب خارج شدید.")
                                        },
                                        scope = scope
                                    )
                                },
                                modifier = Modifier.testTag("optional_sign_out_button")
                            ) {
                                Icon(Icons.AutoMirrored.Filled.Logout, contentDescription = null, modifier = Modifier.size(18.dp))
                                Spacer(modifier = Modifier.width(6.dp))
                                Text("خروج")
                            }
                        }
                    }
                }
            }
        }

        // ۴. دکمه باز و بسته کردن تنظیمات پیشرفته (جلوگیری از شلوغی صفحه تنظیمات)
        item {
            Surface(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(16.dp))
                    .clickable { showAdvancedSettings = !showAdvancedSettings }
                    .testTag("toggle_advanced_settings_btn"),
                shape = RoundedCornerShape(16.dp),
                color = MaterialTheme.colorScheme.surface,
                border = BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 14.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = "تنظیمات پیشرفته مطالعه، رنگ‌بندی و متون عربی",
                            style = MaterialTheme.typography.titleSmall,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                        Text(
                            text = "پالت‌های رنگی، فاصله خطوط، هایلایت، اعراب عربی و خروجی APK",
                            style = MaterialTheme.typography.labelSmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                    Icon(
                        imageVector = if (showAdvancedSettings) Icons.Default.ExpandLess else Icons.Default.ExpandMore,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.primary
                    )
                }
            }
        }

        if (showAdvancedSettings) {
            // ۴.۱ پالت رنگ برنامه
            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                    border = BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant),
                    elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                imageVector = Icons.Default.Palette,
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.primary,
                                modifier = Modifier.size(18.dp)
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(
                                text = "پالت رنگ اختصاصی برنامه",
                                style = MaterialTheme.typography.titleSmall,
                                fontWeight = FontWeight.Bold
                            )
                        }

                        FlowRow(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(6.dp),
                            verticalArrangement = Arrangement.spacedBy(6.dp),
                            maxItemsInEachRow = 2
                        ) {
                            AppColorPalette.entries.forEach { palette ->
                                val isSelected = !themeController.useDynamicColor &&
                                    themeController.selectedColorPalette == palette
                                Surface(
                                    modifier = Modifier
                                        .weight(1f)
                                        .clip(RoundedCornerShape(10.dp))
                                        .clickable {
                                            themeController.selectColorPalette(palette)
                                        }
                                        .testTag("color_palette_${palette.name.lowercase()}"),
                                    shape = RoundedCornerShape(10.dp),
                                    color = if (isSelected) {
                                        MaterialTheme.colorScheme.primaryContainer
                                    } else {
                                        MaterialTheme.colorScheme.background
                                    },
                                    border = BorderStroke(
                                        1.dp,
                                        if (isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.outlineVariant
                                    )
                                ) {
                                    Row(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(horizontal = 9.dp, vertical = 8.dp),
                                        verticalAlignment = Alignment.CenterVertically,
                                        horizontalArrangement = Arrangement.spacedBy(6.dp)
                                    ) {
                                        Box(
                                            modifier = Modifier
                                                .size(18.dp)
                                                .clip(CircleShape)
                                                .background(palette.previewPrimary)
                                        )
                                        Text(
                                            text = palette.persianTitle,
                                            style = MaterialTheme.typography.labelMedium,
                                            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
                                            modifier = Modifier.weight(1f)
                                        )
                                        if (isSelected) {
                                            Icon(
                                                imageVector = Icons.Default.CheckCircle,
                                                contentDescription = null,
                                                tint = MaterialTheme.colorScheme.primary,
                                                modifier = Modifier.size(16.dp)
                                            )
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }

            // ۴.۲ فاصله خطوط، ضخامت متن و تراز پاراگراف‌ها
            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                    border = BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant),
                    elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        Text(
                            text = BidiFormatter.formatMixedPersianText(
                                "فاصله عمودی پاراگراف‌ها: ${themeController.textVerticalSpacingDp.toInt()} dp"
                            ),
                            style = MaterialTheme.typography.bodySmall,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.primary
                        )

                        Slider(
                            value = themeController.textVerticalSpacingDp,
                            onValueChange = { themeController.updateTextVerticalSpacing(it) },
                            valueRange = 0f..14f,
                            modifier = Modifier.testTag("vertical_spacing_slider")
                        )

                        Text(
                            text = BidiFormatter.formatMixedPersianText(
                                "فاصله بین خطوط متن: ${(themeController.lineSpacingScale * 100).toInt()}٪"
                            ),
                            style = MaterialTheme.typography.bodySmall,
                            fontWeight = FontWeight.Bold
                        )

                        Slider(
                            value = themeController.lineSpacingScale,
                            onValueChange = { themeController.updateLineSpacingScale(it) },
                            valueRange = 0.85f..1.50f,
                            modifier = Modifier.testTag("line_spacing_slider")
                        )

                        HorizontalDivider(color = MaterialTheme.colorScheme.outlineVariant)

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column(modifier = Modifier.weight(1f)) {
                                Text(
                                    text = "حالت نوشته پررنگ (Bold)",
                                    style = MaterialTheme.typography.bodySmall,
                                    fontWeight = FontWeight.Bold
                                )
                                Text(
                                    text = "نمایش کل متون با وزن ضخیم‌تر",
                                    style = MaterialTheme.typography.labelSmall,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                            }
                            Switch(
                                checked = themeController.isBoldTextEnabled,
                                onCheckedChange = { themeController.updateBoldText(it) },
                                modifier = Modifier.testTag("bold_text_switch")
                            )
                        }

                        HorizontalDivider(color = MaterialTheme.colorScheme.outlineVariant)

                        Text(
                            text = "جهت و تراز متون:",
                            style = MaterialTheme.typography.bodySmall,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.primary
                        )

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(6.dp)
                        ) {
                            TextAlignmentPreset.entries.forEach { preset ->
                                val isAlignSelected = themeController.textAlignmentPreset == preset
                                Surface(
                                    onClick = {
                                        themeController.selectTextAlignmentPreset(preset)
                                        onShowMessage("تراز متون روی «${preset.title}» تنظیم شد.")
                                    },
                                    shape = RoundedCornerShape(8.dp),
                                    color = if (isAlignSelected) MaterialTheme.colorScheme.primaryContainer else MaterialTheme.colorScheme.background,
                                    border = BorderStroke(
                                        1.dp,
                                        if (isAlignSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.outlineVariant
                                    ),
                                    modifier = Modifier
                                        .weight(1f)
                                        .testTag("text_align_${preset.id}")
                                ) {
                                    Text(
                                        text = preset.title,
                                        style = MaterialTheme.typography.labelSmall,
                                        fontWeight = if (isAlignSelected) FontWeight.Bold else FontWeight.Medium,
                                        color = if (isAlignSelected) MaterialTheme.colorScheme.onPrimaryContainer else MaterialTheme.colorScheme.onSurface,
                                        textAlign = TextAlign.Center,
                                        maxLines = 1,
                                        softWrap = false,
                                        modifier = Modifier.padding(vertical = 7.dp, horizontal = 2.dp)
                                    )
                                }
                            }
                        }
                    }
                }
            }

            // ۴.۳ رنگ هایلایت و خط زیر نکات مهم دروس
            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                    border = BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant),
                    elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        Text(
                            text = "شدت و رنگ هایلایت و خط زیر نکات کلیدی دروس",
                            style = MaterialTheme.typography.titleSmall,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.primary
                        )

                        // انتخاب ۳ سطحی شدت هایلایت (خاموش / ملایم / متوسط)
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(6.dp)
                        ) {
                            listOf(
                                Triple(0, "هایلایت: خاموش", "highlight_intensity_0"),
                                Triple(1, "ملایم (پیشنهادی)", "highlight_intensity_1"),
                                Triple(2, "متوسط (پررنگ‌تر)", "highlight_intensity_2")
                            ).forEach { (lvl, title, tag) ->
                                val isSel = (if (themeController.isHighlighterEnabled) themeController.highlightIntensity else 0) == lvl
                                Surface(
                                    onClick = { themeController.updateHighlightIntensity(lvl) },
                                    shape = RoundedCornerShape(10.dp),
                                    color = if (isSel) MaterialTheme.colorScheme.primaryContainer else MaterialTheme.colorScheme.background,
                                    border = BorderStroke(1.dp, if (isSel) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.outlineVariant),
                                    modifier = Modifier
                                        .weight(1f)
                                        .testTag(tag)
                                ) {
                                    Text(
                                        text = title,
                                        style = MaterialTheme.typography.labelSmall,
                                        fontWeight = if (isSel) FontWeight.Bold else FontWeight.Medium,
                                        color = if (isSel) MaterialTheme.colorScheme.onPrimaryContainer else MaterialTheme.colorScheme.onSurface,
                                        textAlign = TextAlign.Center,
                                        modifier = Modifier.padding(horizontal = 6.dp, vertical = 8.dp)
                                    )
                                }
                            }
                        }

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Surface(
                                onClick = {
                                    themeController.updateHighlighter(!themeController.isHighlighterEnabled)
                                },
                                shape = RoundedCornerShape(10.dp),
                                color = if (themeController.isHighlighterEnabled) MaterialTheme.colorScheme.primaryContainer else MaterialTheme.colorScheme.background,
                                border = BorderStroke(1.dp, if (themeController.isHighlighterEnabled) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.outlineVariant),
                                modifier = Modifier.weight(1f)
                            ) {
                                Text(
                                    text = if (themeController.isHighlighterEnabled) "هایلایت: روشن" else "هایلایت: خاموش",
                                    style = MaterialTheme.typography.labelSmall,
                                    fontWeight = FontWeight.Bold,
                                    textAlign = TextAlign.Center,
                                    modifier = Modifier.padding(horizontal = 10.dp, vertical = 8.dp)
                                )
                            }

                            Surface(
                                onClick = {
                                    themeController.updateUnderlineKeyPoints(!themeController.isUnderlineKeyPointsEnabled)
                                },
                                shape = RoundedCornerShape(10.dp),
                                color = if (themeController.isUnderlineKeyPointsEnabled) MaterialTheme.colorScheme.primaryContainer else MaterialTheme.colorScheme.background,
                                border = BorderStroke(1.dp, if (themeController.isUnderlineKeyPointsEnabled) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.outlineVariant),
                                modifier = Modifier.weight(1f)
                            ) {
                                Text(
                                    text = if (themeController.isUnderlineKeyPointsEnabled) "خط زیر نکات: روشن" else "خط زیر نکات: خاموش",
                                    style = MaterialTheme.typography.labelSmall,
                                    fontWeight = FontWeight.Bold,
                                    textAlign = TextAlign.Center,
                                    modifier = Modifier.padding(horizontal = 10.dp, vertical = 8.dp)
                                )
                            }
                        }

                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .horizontalScroll(rememberScrollState()),
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            HighlighterColorPalette.entries.forEach { hlPalette ->
                                val isSelected = themeController.selectedHighlighterPalette == hlPalette
                                Surface(
                                    onClick = {
                                        themeController.selectHighlighterPalette(hlPalette)
                                        if (!themeController.isHighlighterEnabled) {
                                            themeController.updateHighlighter(true)
                                        }
                                        onShowMessage("رنگ هایلایت «${hlPalette.title}» فعال شد.")
                                    },
                                    shape = RoundedCornerShape(10.dp),
                                    color = if (isSelected) MaterialTheme.colorScheme.primaryContainer else MaterialTheme.colorScheme.background,
                                    border = BorderStroke(
                                        width = 1.dp,
                                        color = if (isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.outlineVariant
                                    ),
                                    modifier = Modifier.testTag("highlighter_color_${hlPalette.id}")
                                ) {
                                    Row(
                                        modifier = Modifier.padding(horizontal = 10.dp, vertical = 7.dp),
                                        verticalAlignment = Alignment.CenterVertically,
                                        horizontalArrangement = Arrangement.spacedBy(6.dp)
                                    ) {
                                        Box(
                                            modifier = Modifier
                                                .size(14.dp)
                                                .clip(CircleShape)
                                                .background(hlPalette.previewSwatch)
                                        )
                                        Text(
                                            text = hlPalette.title,
                                            style = MaterialTheme.typography.labelSmall,
                                            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
                                            maxLines = 1
                                        )
                                    }
                                }
                            }
                        }

                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .horizontalScroll(rememberScrollState()),
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            UnderlineColorPalette.entries.forEach { ulPalette ->
                                val isSelected = themeController.selectedUnderlinePalette == ulPalette
                                Surface(
                                    onClick = {
                                        themeController.selectUnderlinePalette(ulPalette)
                                        if (!themeController.isUnderlineKeyPointsEnabled) {
                                            themeController.updateUnderlineKeyPoints(true)
                                        }
                                        onShowMessage("رنگ خط زیر نکات «${ulPalette.title}» فعال شد.")
                                    },
                                    shape = RoundedCornerShape(10.dp),
                                    color = if (isSelected) MaterialTheme.colorScheme.primaryContainer else MaterialTheme.colorScheme.background,
                                    border = BorderStroke(
                                        width = 1.dp,
                                        color = if (isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.outlineVariant
                                    ),
                                    modifier = Modifier.testTag("underline_color_${ulPalette.id}")
                                ) {
                                    Row(
                                        modifier = Modifier.padding(horizontal = 10.dp, vertical = 7.dp),
                                        verticalAlignment = Alignment.CenterVertically,
                                        horizontalArrangement = Arrangement.spacedBy(6.dp)
                                    ) {
                                        Box(
                                            modifier = Modifier
                                                .size(14.dp)
                                                .clip(CircleShape)
                                                .background(ulPalette.lightColor)
                                        )
                                        Text(
                                            text = ulPalette.title,
                                            style = MaterialTheme.typography.labelSmall,
                                            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
                                            maxLines = 1
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            }

            // ۴.۴ رنگی کردن اعراب عربی و قلم عربی
            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                    border = BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant),
                    elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Column(modifier = Modifier.weight(1f)) {
                                Text(
                                    text = "رنگی کردن اعراب عربی (ـَ ـِ ـُ)",
                                    style = MaterialTheme.typography.titleSmall,
                                    fontWeight = FontWeight.Bold
                                )
                                Text(
                                    text = "تشخیص فاعل، مفعول و مجرور با رنگ مجزا",
                                    style = MaterialTheme.typography.labelSmall,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                            }
                            Switch(
                                checked = themeController.isColoredDiacriticsEnabled,
                                onCheckedChange = {
                                    themeController.updateColoredDiacritics(it)
                                },
                                modifier = Modifier.testTag("colored_diacritics_switch")
                            )
                        }

                        FlowRow(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(8.dp),
                            verticalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            DiacriticsColorPalette.entries.forEach { palette ->
                                val selected = themeController.selectedDiacriticsPalette == palette
                                Surface(
                                    modifier = Modifier
                                        .clip(RoundedCornerShape(10.dp))
                                        .clickable {
                                            themeController.selectDiacriticsPalette(palette)
                                            if (!themeController.isColoredDiacriticsEnabled) {
                                                themeController.updateColoredDiacritics(true)
                                            }
                                        }
                                        .testTag("diacritic_palette_${palette.id}"),
                                    shape = RoundedCornerShape(10.dp),
                                    color = if (selected) MaterialTheme.colorScheme.primaryContainer else MaterialTheme.colorScheme.background,
                                    border = BorderStroke(
                                        width = 1.dp,
                                        color = if (selected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.outlineVariant
                                    )
                                ) {
                                    Row(
                                        modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp),
                                        verticalAlignment = Alignment.CenterVertically,
                                        horizontalArrangement = Arrangement.spacedBy(6.dp)
                                    ) {
                                        Box(
                                            modifier = Modifier
                                                .size(12.dp)
                                                .clip(CircleShape)
                                                .background(palette.previewColor)
                                        )
                                        Text(
                                            text = palette.title,
                                            style = MaterialTheme.typography.labelSmall,
                                            fontWeight = if (selected) FontWeight.Bold else FontWeight.Medium
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            }

            // ۴.۵ ذخیره مستقیم فایل نصبی APK و عیب‌یاب
            item {
                val apkExportLauncher = rememberLauncherForActivityResult(
                    contract = ActivityResultContracts.CreateDocument("application/vnd.android.package-archive")
                ) { targetUri ->
                    if (targetUri != null) {
                        runCatching {
                            val sourceApk = File(context.applicationInfo.sourceDir)
                            FileInputStream(sourceApk).use { input ->
                                context.contentResolver.openOutputStream(targetUri)?.use { output ->
                                    input.copyTo(output)
                                }
                            }
                        }.onSuccess {
                            onShowMessage("فایل نصبی با نام رسمی «Sefr_Ta_Farhangian_v3.6.apk» ذخیره شد.")
                        }.onFailure {
                            onShowMessage("خطا در ذخیره فایل نصبی: ${it.localizedMessage ?: "مجدداً تلاش کنید"}")
                        }
                    }
                }

                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                    border = BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant),
                    elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp, vertical = 12.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                text = "ذخیره فایل نصبی APK در دستگاه",
                                style = MaterialTheme.typography.titleSmall,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.onSurface
                            )
                            Text(
                                text = "خروجی با نام استاندارد Sefr_Ta_Farhangian_v3.6.apk",
                                style = MaterialTheme.typography.labelSmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                        Spacer(modifier = Modifier.width(8.dp))
                        Button(
                            onClick = {
                                apkExportLauncher.launch("Sefr_Ta_Farhangian_v3.6.apk")
                            },
                            shape = RoundedCornerShape(10.dp),
                            modifier = Modifier.testTag("export_clean_apk_btn")
                        ) {
                            Text("ذخیره APK", style = MaterialTheme.typography.labelMedium)
                        }
                    }
                }
            }

            item {
                CompactBugLoggerCard(onShowMessage = onShowMessage)
            }
        }

        // ۵. کارت بازنشانی کلی به تنظیمات اولیه
        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                border = BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant),
                elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 12.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        modifier = Modifier.weight(1f)
                    ) {
                        Icon(
                            imageVector = Icons.Default.RestartAlt,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.error,
                            modifier = Modifier.size(20.dp)
                        )
                        Column {
                            Text(
                                text = "بازنشانی به تنظیمات پیش‌فرض",
                                style = MaterialTheme.typography.titleSmall,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.onSurface
                            )
                            Text(
                                text = "بازگرداندن فونت، رنگ و اندازه متن به حالت استاندارد",
                                style = MaterialTheme.typography.labelSmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    }
                    OutlinedButton(
                        onClick = { showResetDialog = true },
                        shape = RoundedCornerShape(10.dp),
                        modifier = Modifier.testTag("reset_all_settings_top_btn")
                    ) {
                        Text("بازنشانی", style = MaterialTheme.typography.labelMedium, color = MaterialTheme.colorScheme.error)
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun CompactBugLoggerCard(
    onShowMessage: (String) -> Unit
) {
    val context = LocalContext.current
    val themeController = LocalAppThemeController.current
    var selectedCategory by remember { mutableStateOf("فونت/عربی") }
    var bugText by remember { mutableStateOf("") }
    var showDiagnosticsDetail by remember { mutableStateOf(false) }

    val categories = listOf("فونت/عربی", "نمایش/رنگ", "محتوای درس", "تقویم/برنامه", "سایر")

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .testTag("compact_bug_logger_card"),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        border = BorderStroke(0.8.dp, MaterialTheme.colorScheme.primary.copy(alpha = 0.35f)),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
    ) {
        Column(
            modifier = Modifier.padding(12.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = "🐞 لاگ باگ کوچک و عیب‌یاب گوشی (نسخه نصبی APK)",
                        style = MaterialTheme.typography.titleSmall,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary
                    )
                    Text(
                        text = "ثبت سریع مشکل یا ریست تنظیمات کش‌شده روی گوشی واقعی",
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
                OutlinedButton(
                    onClick = {
                        themeController.resetAllAppearanceToStandard()
                        onShowMessage("تنظیمات فونت، فاصله و رنگ به حالت استاندارد کارخانه بازنشانی شد.")
                    },
                    shape = RoundedCornerShape(8.dp),
                    modifier = Modifier.testTag("reset_appearance_standard_btn")
                ) {
                    Icon(Icons.Default.RestartAlt, contentDescription = null, modifier = Modifier.size(14.dp))
                    Spacer(modifier = Modifier.width(4.dp))
                    Text("ریست استاندارد", style = MaterialTheme.typography.labelSmall)
                }
            }

            FlowRow(
                horizontalArrangement = Arrangement.spacedBy(6.dp),
                verticalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                categories.forEach { cat ->
                    val isSelected = selectedCategory == cat
                    Surface(
                        onClick = { selectedCategory = cat },
                        shape = RoundedCornerShape(8.dp),
                        color = if (isSelected) MaterialTheme.colorScheme.primaryContainer else MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.45f)
                    ) {
                        Text(
                            text = cat,
                            style = MaterialTheme.typography.labelSmall,
                            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                        )
                    }
                }
            }

            androidx.compose.material3.OutlinedTextField(
                value = bugText,
                onValueChange = { bugText = it },
                placeholder = {
                    Text(
                        "توضیح کوتاه باگ روی گوشی (مثلاً: در درس ۲ عربی خط سوم...)",
                        style = MaterialTheme.typography.bodySmall
                    )
                },
                singleLine = true,
                shape = RoundedCornerShape(10.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("bug_logger_input")
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                Button(
                    onClick = {
                        if (bugText.isNotBlank()) {
                            themeController.addBugLog(selectedCategory, bugText)
                            bugText = ""
                            onShowMessage("باگ در لاگ داخلی دستگاه ثبت شد.")
                        } else {
                            onShowMessage("لطفاً توضیح کوتاهی درباره مشکل بنویسید.")
                        }
                    },
                    shape = RoundedCornerShape(9.dp),
                    modifier = Modifier
                        .weight(1f)
                        .testTag("submit_bug_log_btn")
                ) {
                    Text("ثبت لاگ باگ (${BidiFormatter.toPersianDigits(themeController.bugLogs.size.toString())})", style = MaterialTheme.typography.labelMedium)
                }

                OutlinedButton(
                    onClick = {
                        val report = themeController.buildDeviceDiagnosticsSummary()
                        val clipboard = context.getSystemService(android.content.Context.CLIPBOARD_SERVICE) as? android.content.ClipboardManager
                        clipboard?.setPrimaryClip(android.content.ClipData.newPlainText("BugReport", report))
                        showDiagnosticsDetail = !showDiagnosticsDetail
                        onShowMessage("گزارش کامل فنی گوشی و لاگ باگ‌ها در کلیپ‌بورد کپی شد!")
                    },
                    shape = RoundedCornerShape(9.dp),
                    modifier = Modifier.testTag("copy_bug_diagnostics_btn")
                ) {
                    Text("📋 کپی و مشاهده لاگ", style = MaterialTheme.typography.labelMedium)
                }
            }

            if (showDiagnosticsDetail || themeController.bugLogs.isNotEmpty()) {
                Surface(
                    color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.4f),
                    shape = RoundedCornerShape(10.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(
                        modifier = Modifier.padding(9.dp),
                        verticalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        Text(
                            text = BidiFormatter.formatMixedPersianText(
                                "📱 گوشی: ${Build.MANUFACTURER} ${Build.MODEL} • Android ${Build.VERSION.RELEASE} • فونت: ${themeController.selectedFontPreset.persianTitle}"
                            ),
                            style = MaterialTheme.typography.labelSmall,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.primary
                        )
                        themeController.bugLogs.take(5).forEach { entry ->
                            Text(
                                text = BidiFormatter.formatMixedPersianText(
                                    "• [${entry.category}] ${entry.description} (${entry.timestamp})"
                                ),
                                style = MaterialTheme.typography.labelSmall,
                                color = MaterialTheme.colorScheme.onSurface
                            )
                        }
                        if (themeController.bugLogs.isNotEmpty()) {
                            androidx.compose.material3.TextButton(
                                onClick = {
                                    themeController.clearBugLogs()
                                    onShowMessage("لاگ باگ‌ها پاک شد.")
                                }
                            ) {
                                Text("پاک کردن لیست لاگ‌ها", style = MaterialTheme.typography.labelSmall)
                            }
                        }
                    }
                }
            }
        }
    }
}

