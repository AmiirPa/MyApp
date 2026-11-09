package com.example.data.curriculum

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBalance
import androidx.compose.material.icons.filled.AltRoute
import androidx.compose.material.icons.filled.Assignment
import androidx.compose.material.icons.filled.AutoStories
import androidx.compose.material.icons.filled.Badge
import androidx.compose.material.icons.filled.BarChart
import androidx.compose.material.icons.filled.Bolt
import androidx.compose.material.icons.filled.Book
import androidx.compose.material.icons.filled.Calculate
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.EditNote
import androidx.compose.material.icons.filled.EmojiEvents
import androidx.compose.material.icons.filled.EmojiObjects
import androidx.compose.material.icons.filled.Explore
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FormatQuote
import androidx.compose.material.icons.filled.Functions
import androidx.compose.material.icons.filled.GraphicEq
import androidx.compose.material.icons.filled.Groups
import androidx.compose.material.icons.filled.HistoryEdu
import androidx.compose.material.icons.filled.Language
import androidx.compose.material.icons.filled.Lightbulb
import androidx.compose.material.icons.filled.MenuBook
import androidx.compose.material.icons.filled.MusicNote
import androidx.compose.material.icons.filled.Palette
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Psychology
import androidx.compose.material.icons.filled.Public
import androidx.compose.material.icons.filled.QueryStats
import androidx.compose.material.icons.filled.Quiz
import androidx.compose.material.icons.filled.School
import androidx.compose.material.icons.filled.Science
import androidx.compose.material.icons.filled.ShowChart
import androidx.compose.material.icons.filled.Spa
import androidx.compose.material.icons.filled.Spellcheck
import androidx.compose.material.icons.filled.TableChart
import androidx.compose.material.icons.filled.Timeline
import androidx.compose.material.icons.filled.Timer
import androidx.compose.material.icons.filled.Translate
import androidx.compose.material.icons.filled.TrendingUp
import androidx.compose.material.icons.filled.WarningAmber
import androidx.compose.ui.graphics.vector.ImageVector

/**
 * لوگوی هر درس: یک آیکون Material یکتا برای هر درس.
 *
 * قرارداد (ثابت — نمایش درس روی آن حساب می‌کند):
 * [iconForLesson] همیشه یک [ImageVector] برمی‌گرداند و هرگز null نیست؛
 * اول نگاشت صریح بر اساس id درس را نگاه می‌کند و اگر درس در آن نبود،
 * از fallback قطعی بر اساس subjectId استفاده می‌کند، پس **هر** درس
 * (حتی درس‌هایی که اینجا نامشان نیامده) یک لوگو دارد.
 *
 * نگاشت صریح: 818 درس هسته (انسانی ۱۰ تا ۱۲ + فرهنگیان + ریاضی)؛
 * بقیهٔ درس‌ها (پایه دبستان و متوسطه اول و تمرین‌ها) با fallback موضوعی
 * پوشش داده می‌شوند. همهٔ آیکون‌ها از آیکون‌های Material موجود در پروژه‌اند
 * (قبلاً در کد پروژه import شده‌اند) و با SubjectDesignSystem هماهنگ‌اند:
 * هر موضوع همان آیکونی را می‌گیرد که SubjectDesignSystem برای آن موضوع دارد،
 * مگر درس‌هایی که موضوع فرعی‌شان آیکون دقیق‌تری می‌خواهد (مثلاً آمار در ریاضی).
 */
object LessonLogos1406 {

    /** تعداد درس‌هایی که نگاشت صریح دارند. */
    const val EXPLICIT_LESSON_COUNT: Int = 818

    fun iconForLesson(lessonId: String, subjectId: String): ImageVector {
        when (lessonId) {
            // عربی
            "ar-gap-majhul-madi" -> return Icons.Default.Translate
            "ar-gap-majhul-mudare" -> return Icons.Default.Translate
            "ar-gap-naeb-fael" -> return Icons.Default.Translate
            "ar-tip-01" -> return Icons.Default.Translate
            "ar-tip-02" -> return Icons.Default.Translate
            "ar-tip-03" -> return Icons.Default.Translate
            "ar-tip-04" -> return Icons.Default.Translate
            "ar-tip-05" -> return Icons.Default.Translate
            "ar-tip-06" -> return Icons.Default.Translate
            "ar-zero-01" -> return Icons.Default.Book
            "ar-zero-02" -> return Icons.Default.Book
            "ar-zero-03" -> return Icons.Default.Book
            "ar-zero-04" -> return Icons.Default.Book
            "ar-zero-05" -> return Icons.Default.Book
            "ar-zero-06" -> return Icons.Default.Book
            "ar12-01-lesson1-la-nafy-jins" -> return Icons.Default.Translate
            "ar12-02-lesson2-hal" -> return Icons.Default.Translate
            "ar12-03-lesson3-istithna-tamyiz" -> return Icons.Default.Translate
            "ar12-04-lesson4-5-monada-mafoul-ialal" -> return Icons.Default.Translate
            "ar1406-01-word-types-sigha" -> return Icons.Default.Translate
            "ar1406-02-past-present-tenses" -> return Icons.Default.Translate
            "ar1406-03-mujarrad-mazeed-1" -> return Icons.Default.Translate
            "ar1406-04-mazeed-2" -> return Icons.Default.Translate
            "ar1406-05-lesson5-nominal-sentence" -> return Icons.Default.Translate
            "ar1406-06-lesson5-verbal-sentence" -> return Icons.Default.Translate
            "arabic_0000_absolute_zero_7th_to_12th_khodemoni" -> return Icons.Default.Book
            "arabic_000_bismillah_mastery" -> return Icons.Default.Book
            "arabic_00_sigha_hand_code" -> return Icons.Default.Translate
            "arabic_01_word_types_pronouns" -> return Icons.Default.Translate
            "arabic_01b_numbers_time_nouns" -> return Icons.Default.Timer
            "arabic_02_feal_bab_majhool" -> return Icons.Default.Translate
            "arabic_02_verbs_abwab" -> return Icons.Default.Translate
            "arabic_02b_derived_nouns_qaleb" -> return Icons.Default.Translate
            "arabic_03_grade12_rules" -> return Icons.Default.Translate
            "arabic_03b_grade11_shart_naqeseh_vasf" -> return Icons.Default.Translate
            "arabic_04_grade12_istisna_hasr_hal" -> return Icons.Default.Translate
            "arabic_04_mnemonics_final20" -> return Icons.Default.Translate
            "arabic_05_konkoor_translation_keys" -> return Icons.Default.Translate
            "arabic_06_konkoor_100_tables_and_traps" -> return Icons.Default.Translate
            "arabic_11_ch1_tafzeel_makan_zaman" -> return Icons.Default.Translate
            "arabic_11_ch2_shart_and_naqeseh" -> return Icons.Default.Translate
            "arabic_11_ch3_moqarabeh_vasfiyeh_huruf" -> return Icons.Default.Translate
            "arabic_step01_alphabet_diacritics_nouns" -> return Icons.Default.Translate
            "arabic_step03_complete_14_sigha_tables" -> return Icons.Default.Translate
            "arabic_step06_all_8_abwab_master_table" -> return Icons.Default.Translate
            "arabic_step11_mansoobat_estisna_monada_table" -> return Icons.Default.Translate
            "arabic_ultra_fast_techniques" -> return Icons.Default.Bolt
            "arabic_visual_persian_mastery" -> return Icons.Default.Translate
            "found1406-ar-01-alphabet-reading" -> return Icons.Default.Translate
            "found1406-ar-02-sigha-what-is" -> return Icons.Default.Translate
            "found1406-ar-03-pronouns" -> return Icons.Default.Translate
            "found1406-ar-04-demonstratives" -> return Icons.Default.Translate
            "found1406-ar-05-gender-number" -> return Icons.Default.Timer
            "found1406-ar-06-numbers" -> return Icons.Default.Timer
            "found1406-ar-07-past-tense" -> return Icons.Default.Translate
            "found1406-ar-08-present-tense" -> return Icons.Default.Translate
            "found1406-ar-09-imperative" -> return Icons.Default.Translate
            "found1406-ar-10-nominal-sentence" -> return Icons.Default.Translate
            "found1406-ar-11-verbal-sentence" -> return Icons.Default.Translate
            "found1406-ar-12-translation" -> return Icons.Default.Translate
            "found1406-ar-13-reading-text" -> return Icons.Default.Translate
            "gap-ar-01" -> return Icons.Default.Translate
            "mid7-arabic-01-alphabet" -> return Icons.Default.Translate
            "mid7-arabic-02-greetings" -> return Icons.Default.Translate
            "mid7-arabic-03-language" -> return Icons.Default.Translate
            "mid7-arabic-04-gender" -> return Icons.Default.Translate
            "mid7-arabic-05-dual" -> return Icons.Default.Translate
            "mid7-arabic-06-plural-demonstrative" -> return Icons.Default.Translate
            "mid7-arabic-07-hal-hamza" -> return Icons.Default.Translate
            "mid7-arabic-08-man" -> return Icons.Default.Translate
            "mid7-arabic-09-ma" -> return Icons.Default.Translate
            "mid7-arabic-10-ayna" -> return Icons.Default.Translate
            "mid7-arabic-11-kam-numbers" -> return Icons.Default.Timer
            "mid7-arabic-12-daily-conversation" -> return Icons.Default.Translate
            "mid7-arabic-13-past-first-second-singular" -> return Icons.Default.Translate
            "mid7-arabic-14-mother-son-third-singular" -> return Icons.Default.Translate
            "mid7-arabic-15-first-person-plural-phone" -> return Icons.Default.Translate
            "mid7-arabic-16-second-person-plural-village" -> return Icons.Default.Translate
            "mid7-arabic-17-arbaeen-story-third-plural" -> return Icons.Default.Translate
            "mid8-arabic-01-review-7" -> return Icons.Default.Translate
            "mid8-arabic-02-importance-arabic" -> return Icons.Default.Translate
            "mid8-arabic-03-future-job" -> return Icons.Default.Timer
            "mid8-arabic-04-new-experience" -> return Icons.Default.Translate
            "mid8-arabic-05-friendship" -> return Icons.Default.Translate
            "mid8-arabic-06-travel" -> return Icons.Default.Translate
            "mid8-arabic-07-earth-wide" -> return Icons.Default.Translate
            "mid8-arabic-08-self-reliance" -> return Icons.Default.Translate
            "mid8-arabic-09-science-trip" -> return Icons.Default.Translate
            "mid8-arabic-10-wisdoms" -> return Icons.Default.Translate
            "mid9-arabic-01-review" -> return Icons.Default.Translate
            "mid9-arabic-02-safe-crossing" -> return Icons.Default.Translate
            "mid9-arabic-03-friendship-bridge" -> return Icons.Default.Translate
            "mid9-arabic-04-patience-key" -> return Icons.Default.Translate
            "mid9-arabic-05-hope" -> return Icons.Default.Translate
            "mid9-arabic-06-life-change" -> return Icons.Default.Translate
            "mid9-arabic-07-fruit-of-effort" -> return Icons.Default.Translate
            "mid9-arabic-08-taxi-dialogue" -> return Icons.Default.Translate
            "mid9-arabic-09-health-texts" -> return Icons.Default.Translate
            "mid9-arabic-10-soleimani-letter" -> return Icons.Default.Translate
            "site-sub-02" -> return Icons.Default.Translate
            // عروض
            "arooz-quick-01" -> return Icons.Default.MusicNote
            "arooz-quick-02" -> return Icons.Default.MusicNote
            "arooz-quick-03" -> return Icons.Default.MusicNote
            "arooz-quick-04" -> return Icons.Default.MusicNote
            "arooz-quick-05" -> return Icons.Default.MusicNote
            "arooz-quick-06" -> return Icons.Default.MusicNote
            "arooz12-01-dowri" -> return Icons.Default.MusicNote
            "arooz12-02-meters12" -> return Icons.Default.MusicNote
            "arooz12-03-licenses" -> return Icons.Default.MusicNote
            "arooz12-04-ghafieh-advanced" -> return Icons.Default.MusicNote
            "arooz_00_bahr_barcode" -> return Icons.Default.MusicNote
            "arooz_00_clapping_zero" -> return Icons.Default.MusicNote
            "arooz_01_syllables_zero" -> return Icons.Default.MusicNote
            "arooz_02_cutting_u_dash" -> return Icons.Default.MusicNote
            "arooz_03_samaei_rhythms" -> return Icons.Default.MusicNote
            "arooz_03b_poetic_licenses" -> return Icons.Default.MusicNote
            "arooz_04_ghafieh_rules" -> return Icons.Default.MusicNote
            "arooz_04_mnemonics_mastery" -> return Icons.Default.MusicNote
            "arooz_05_all_16_konkoor_meters" -> return Icons.Default.MusicNote
            "arooz_06_konkoor_100_speed_scansion" -> return Icons.Default.MusicNote
            "arooz_10_ch1_syllables_and_script" -> return Icons.Default.MusicNote
            "arooz_10_ch2_ghafieh_tabsareha" -> return Icons.Default.MusicNote
            "arooz_11_ch2_nahamsan_meters" -> return Icons.Default.MusicNote
            "arooz_11_lesson3_vocal_bases" -> return Icons.Default.MusicNote
            "arooz_11_lesson6_hamsan_meters" -> return Icons.Default.MusicNote
            "arooz_fonoon_ultra_fast_techniques" -> return Icons.Default.Bolt
            "arooz_master_complete_100" -> return Icons.Default.MusicNote
            "aroozm1406-01-rhythm-clap" -> return Icons.Default.MusicNote
            "aroozm1406-02-taghtee" -> return Icons.Default.MusicNote
            "aroozm1406-03-poetry-hook" -> return Icons.Default.MusicNote
            "arozero-01-heja-clap" -> return Icons.Default.MusicNote
            "arozero-02-taghtee" -> return Icons.Default.MusicNote
            "arozero-03-rokn-rhythm" -> return Icons.Default.MusicNote
            "arozero-04-hazaj" -> return Icons.Default.MusicNote
            "arozero-05-rajaz" -> return Icons.Default.MusicNote
            "arozero-06-ramal" -> return Icons.Default.MusicNote
            "arozero-07-mozare" -> return Icons.Default.MusicNote
            "arozero-08-mojtass" -> return Icons.Default.MusicNote
            "arozero-09-motaqareb" -> return Icons.Default.MusicNote
            "arozero-10-kamel" -> return Icons.Default.MusicNote
            "arozero-11-monsareh" -> return Icons.Default.MusicNote
            "arozero-12-dowri" -> return Icons.Default.MusicNote
            "arozero-13-licenses" -> return Icons.Default.MusicNote
            "arozero-14-ghafieh" -> return Icons.Default.MusicNote
            "arozero-15-ghafieh-defects" -> return Icons.Default.MusicNote
            // دین و زندگی
            "dini1-01-hadaf-zendegi" -> return Icons.Default.Spa
            "dini1-02-qodrat-parvaz" -> return Icons.Default.Spa
            "dini1-03-be-sooye-javedanegi" -> return Icons.Default.Spa
            "dini1-04-ayandeye-rowshan" -> return Icons.Default.Spa
            "dini1-05-manzelgahe-baad" -> return Icons.Default.Spa
            "dini1-06-vagheye-bozorg" -> return Icons.Default.Spa
            "dini1-07-farjam-kar" -> return Icons.Default.Spa
            "dini1-08-ahang-safar" -> return Icons.Default.Spa
            "dini1-09-etemad-bar-oo" -> return Icons.Default.Spa
            "dini1-10-yari-az-do-yar-aziz" -> return Icons.Default.Spa
            "dini1-11-fazilat-arastegi" -> return Icons.Default.Spa
            "dini1-12-payam-pooshidegi" -> return Icons.Default.Spa
            "dini2-01-hedayat-elahi" -> return Icons.Default.Spa
            "dini2-02-tadavom-hedayat" -> return Icons.Default.Spa
            "dini2-03-akharin-payambar" -> return Icons.Default.Spa
            "dini2-04-mojeze-javidan" -> return Icons.Default.MenuBook
            "dini2-05-masouliyat-payambar" -> return Icons.Default.Spa
            "dini2-06-pishvaye-osve" -> return Icons.Default.Spa
            "dini2-07-emamat-tadavom-resalat" -> return Icons.Default.Spa
            "dini2-08-jan-va-janeshin-payambar" -> return Icons.Default.Spa
            "dini2-09-vaziyat-mosalamanan-pas-az-rehlat" -> return Icons.Default.Spa
            "dini2-10-ehyaye-arzeshhaye-rastin" -> return Icons.Default.Spa
            "dini2-11-jahad-dar-rastaye-velayat-zaheri" -> return Icons.Default.Spa
            "dini2-12-asr-gheybat-emam-zaman" -> return Icons.Default.Spa
            "dini2-13-dar-entezar-zohour" -> return Icons.Default.Spa
            "dini2-14-marjaeyat-va-velayat-faqih" -> return Icons.Default.Spa
            "dini2-15-rahbari-va-mardom" -> return Icons.Default.Spa
            "dini2-16-ezzat-nafs" -> return Icons.Default.Spa
            "dini2-17-zaminehaye-peyvand" -> return Icons.Default.Spa
            "dini2-18-peyvand-moqaddas" -> return Icons.Default.Spa
            "dini3-01-hasti-bakhsh" -> return Icons.Default.Spa
            "dini3-01-qaza-qadar" -> return Icons.Default.Spa
            "dini3-02-sonnat-elahi" -> return Icons.Default.Spa
            "dini3-02-yegane-bi-hamta" -> return Icons.Default.Spa
            "dini3-03-towbe" -> return Icons.Default.Spa
            "dini3-03-towhid-va-sabk-zendegi" -> return Icons.Default.Spa
            "dini3-04-donyaye-emruz" -> return Icons.Default.Spa
            "dini3-04-faghat-baraye-to" -> return Icons.Default.Spa
            "dini3-05-qodrat-parvaz" -> return Icons.Default.Spa
            "dini3-06-sonnathaye-khodavand-dar-zendegi" -> return Icons.Default.Spa
            "dini3-07-dar-gero-kar-khish" -> return Icons.Default.Spa
            "dini3-08-bazgasht" -> return Icons.Default.Spa
            "dini3-09-zendegi-dar-donyaye-emruz-va-amal-be-ahkam-elahi" -> return Icons.Default.Spa
            "dini3-10-payehaye-ostovar" -> return Icons.Default.Spa
            "dini3-11-asr-shokoufai" -> return Icons.Default.Spa
            "dini3-12-negahi-be-tamadon-jadid" -> return Icons.Default.Spa
            "dini3-13-masouliyat-bozorg-ma" -> return Icons.Default.Spa
            "dini_01_grade10_1403_complete" -> return Icons.Default.Spa
            "dini_01_tawheed_degrees" -> return Icons.Default.Spa
            "dini_02_grade11_1404_complete" -> return Icons.Default.MenuBook
            "dini_03_grade12_1405_complete" -> return Icons.Default.Spa
            "dini_04_divine_laws_sunan_barzakh" -> return Icons.Default.Spa
            "dini_10_ch1_purpose_creation_theology" -> return Icons.Default.Spa
            "dini_10_ch2_death_barzakh_hereafter" -> return Icons.Default.Spa
            "dini_10_ch3_friendship_ethics_society" -> return Icons.Default.Spa
            "dini_11_ch1_divine_guidance_quran_prophet" -> return Icons.Default.MenuBook
            "dini_11_ch2_imamate_ghadir_velayat" -> return Icons.Default.Spa
            "dini_11_ch3_imams_life_ghaybat_velayat" -> return Icons.Default.Spa
            "site-sub-08" -> return Icons.Default.Spa
            // اقتصاد
            "eco-new-01-kasbokar-karafarini" -> return Icons.Default.AccountBalance
            "eco-new-02-entekhab-noe-kasbokar" -> return Icons.Default.AccountBalance
            "eco-new-03-osul-entekhab-dorost" -> return Icons.Default.AccountBalance
            "eco-new-04-marz-emkanat-tolid" -> return Icons.Default.TrendingUp
            "eco-new-05-bazar" -> return Icons.Default.ShowChart
            "eco-new-06-naghsh-dolat" -> return Icons.Default.AccountBalance
            "eco-new-07-tejarat-beynolmelal" -> return Icons.Default.AccountBalance
            "eco-new-08-rokud-bikari-faqr" -> return Icons.Default.TrendingUp
            "eco-new-09-tavarom" -> return Icons.Default.TrendingUp
            "eco-new-10-moqavemsazi" -> return Icons.Default.AccountBalance
            "eco-new-11-roshd-pishraft" -> return Icons.Default.TrendingUp
            "eco-new-12-budjebandi" -> return Icons.Default.AccountBalance
            "eco-new-13-tasmimgiri-makharej" -> return Icons.Default.AccountBalance
            "eco-new-14-pasandaz-sarmayegozari" -> return Icons.Default.AccountBalance
            "eco-quick-01" -> return Icons.Default.TrendingUp
            "eco-quick-02" -> return Icons.Default.TrendingUp
            "eco-quick-03" -> return Icons.Default.TrendingUp
            "eco-quick-04" -> return Icons.Default.ShowChart
            "eco-quick-05" -> return Icons.Default.AccountBalance
            "eco-quick-06" -> return Icons.Default.TrendingUp
            "eco1406-01-what-is-economics" -> return Icons.Default.AccountBalance
            "eco1406-02-opportunity-cost-ppf-deciles" -> return Icons.Default.TrendingUp
            "eco1406-03-money-functions-types" -> return Icons.Default.AccountBalance
            "eco1406-04-banking-inflation-unemployment" -> return Icons.Default.TrendingUp
            "econ_01_grade10_1403_complete" -> return Icons.Default.ShowChart
            "econ_02_math_problems_shortcuts_100" -> return Icons.Default.TrendingUp
            "econ_03_gdp_inflation_unemployment_budget" -> return Icons.Default.TrendingUp
            "econ_04_market_equilibrium_curves" -> return Icons.Default.ShowChart
            "econ_05_tax_gini_distribution" -> return Icons.Default.AccountBalance
            "econ_10_ch2_market_supply_demand" -> return Icons.Default.ShowChart
            "econ_10_ch4_trade_exchange_resilience" -> return Icons.Default.AccountBalance
            "econ_full_math_mastery" -> return Icons.Default.TrendingUp
            "economics_speed_shortcuts_master" -> return Icons.Default.AccountBalance
            "found1406-soc-11-economy-basics" -> return Icons.Default.TrendingUp
            "gdp1406-01-what-is-gdp" -> return Icons.Default.TrendingUp
            "gdp1406-02-gdp-calculation-nominal-real" -> return Icons.Default.TrendingUp
            "site-sub-07" -> return Icons.Default.AccountBalance
            // زبان انگلیسی
            "eng-zero-01" -> return Icons.Default.Book
            "eng-zero-02" -> return Icons.Default.Book
            "eng-zero-03" -> return Icons.Default.Book
            "eng-zero-04" -> return Icons.Default.Book
            "eng-zero-05" -> return Icons.Default.Book
            "eng-zero-06" -> return Icons.Default.Book
            "eng1406-01-vision3-appreciation" -> return Icons.Default.Language
            "eng1406-02-vision3-look-it-up" -> return Icons.Default.Spellcheck
            "eng1406-03-vision3-renewable-energy" -> return Icons.Default.Language
            "eng1406-04-vision1-traveling" -> return Icons.Default.Language
            "english_000_bismillah_mastery" -> return Icons.Default.Book
            "english_000_zero_dictionary_grammar_words" -> return Icons.Default.Book
            "english_00_seventh_grade_train" -> return Icons.Default.Language
            "english_01_sentence_zero" -> return Icons.Default.Book
            "english_01b_nouns_comparatives_adjectives" -> return Icons.Default.Language
            "english_01c_gerund_infinitive_conjunctions" -> return Icons.Default.Language
            "english_02_grade12_grammar" -> return Icons.Default.EditNote
            "english_03_grade12_reading_cloze_vocab" -> return Icons.Default.MenuBook
            "english_04_tag_questions_condition_passive" -> return Icons.Default.Language
            "english_05_konkoor_100_master_tables" -> return Icons.Default.EditNote
            "english_06_full_vocab_7_to_12_konkoor" -> return Icons.Default.Spellcheck
            "english_10_vision1_lesson1_saving_nature" -> return Icons.Default.Language
            "english_10_vision1_lesson2_wonders_creation" -> return Icons.Default.Language
            "english_10_vision1_lesson3_value_knowledge" -> return Icons.Default.Language
            "english_11_vision2_lesson1_healthy_lifestyle" -> return Icons.Default.Language
            "english_11_vision2_lesson2_gerund_infinitive" -> return Icons.Default.Language
            "english_11_vision2_lesson3_culture_and_art" -> return Icons.Default.Language
            "english_full_nahaee_konkoor" -> return Icons.Default.EditNote
            "english_full_persian_mastery" -> return Icons.Default.Language
            "english_step01_pronouns_tobe_sentence_table" -> return Icons.Default.Language
            "english_step03_all_tenses_step_by_step_table" -> return Icons.Default.EditNote
            "english_step11_passive_conditionals_relative_tags" -> return Icons.Default.Language
            "found1406-en-01-alphabet-sounds" -> return Icons.Default.Language
            "found1406-en-02-first-words" -> return Icons.Default.Language
            "found1406-en-03-vocab-1" -> return Icons.Default.Spellcheck
            "found1406-en-04-vocab-2" -> return Icons.Default.Spellcheck
            "found1406-en-05-tobe" -> return Icons.Default.Language
            "found1406-en-06-have" -> return Icons.Default.Language
            "found1406-en-07-vocab-3" -> return Icons.Default.Spellcheck
            "found1406-en-08-vocab-4" -> return Icons.Default.Spellcheck
            "found1406-en-09-vocab-5" -> return Icons.Default.Spellcheck
            "found1406-en-10-present-simple" -> return Icons.Default.Language
            "found1406-en-11-vocab-6" -> return Icons.Default.Spellcheck
            "found1406-en-12-vocab-7" -> return Icons.Default.Spellcheck
            "found1406-en-13-past-simple" -> return Icons.Default.Language
            "found1406-en-14-reading" -> return Icons.Default.MenuBook
            "found1406-en-15-intonation" -> return Icons.Default.Language
            "found1406-en-16-vocab-8" -> return Icons.Default.Spellcheck
            "found1406-en-17-future-compare" -> return Icons.Default.Language
            "mid7-english-01-my-name" -> return Icons.Default.Language
            "mid7-english-02-my-classmates" -> return Icons.Default.Language
            "mid7-english-03-my-age" -> return Icons.Default.Language
            "mid7-english-04-my-family" -> return Icons.Default.Language
            "mid7-english-05-appearance" -> return Icons.Default.Language
            "mid7-english-06-house" -> return Icons.Default.Language
            "mid7-english-07-address" -> return Icons.Default.Language
            "mid7-english-08-favorite-food" -> return Icons.Default.Language
            "mid8-english-01-nationality" -> return Icons.Default.Language
            "mid8-english-02-my-week" -> return Icons.Default.Language
            "mid8-english-03-my-abilities" -> return Icons.Default.Language
            "mid8-english-04-my-health" -> return Icons.Default.Language
            "mid8-english-05-my-city" -> return Icons.Default.Language
            "mid8-english-06-my-village" -> return Icons.Default.Language
            "mid8-english-07-my-hobbies" -> return Icons.Default.Language
            "mid9-english-01-personality" -> return Icons.Default.Language
            "mid9-english-02-travel" -> return Icons.Default.Language
            "mid9-english-03-festivals" -> return Icons.Default.Language
            "mid9-english-04-services" -> return Icons.Default.Language
            "mid9-english-05-media" -> return Icons.Default.Language
            "mid9-english-06-health" -> return Icons.Default.Language
            // فرهنگیان
            "far_01_roadmap_1406" -> return Icons.Default.School
            "far_02_gharaati_talim" -> return Icons.Default.AutoStories
            "far_03_teacher_intelligence" -> return Icons.Default.AutoStories
            "far_03a_verbal_logical_math_intelligence_full" -> return Icons.Default.School
            "far_04_interview_humanities" -> return Icons.Default.Quiz
            "far_05_dini_farhangian_ensani" -> return Icons.Default.School
            "far_06_comprehensive_interview_scenarios" -> return Icons.Default.Quiz
            "far_07_konkoor_full_package_humanities_tables" -> return Icons.Default.Psychology
            "far_08_dini_complete_10_11_12_1406" -> return Icons.Default.School
            "far_08_qaraati_teacher_methods_complete" -> return Icons.Default.AutoStories
            "far_09_farsi_grammar_1_to_12_1406" -> return Icons.Default.School
            "far_09_teacher_aptitude_reasoning" -> return Icons.Default.AutoStories
            "far_10_remaining_humanities_books_10_11_12" -> return Icons.Default.School
            "farh-src-01-roadmap-exam-1405" -> return Icons.Default.School
            "farh-src-02-qaraati-ch1-value" -> return Icons.Default.AutoStories
            "farh-src-03-qaraati-ch2-attributes" -> return Icons.Default.AutoStories
            "farh-src-04-qaraati-ch3-duties" -> return Icons.Default.AutoStories
            "farh-src-05-dini-correct-range" -> return Icons.Default.School
            "farh-src-06-housh-quantitative" -> return Icons.Default.School
            "farh-src-07-housh-classification" -> return Icons.Default.Groups
            "farh-src-08-kanoon-writing" -> return Icons.Default.School
            "farh1406-01-sanad-tahavol" -> return Icons.Default.School
            "farh1406-02-talim-tarbiyat-eslami" -> return Icons.Default.AutoStories
            "farhangian_04_mnemonics_final20" -> return Icons.Default.Quiz
            "persian_internet_konkoor_gems" -> return Icons.Default.School
            // فارسی
            "far1-01-cheshmeh" -> return Icons.Default.MenuBook
            "far1-02-az-amookhtan-nang-madar" -> return Icons.Default.MenuBook
            "far1-03-pasdari-haghighat" -> return Icons.Default.MenuBook
            "far1-05-bidad-zaleman" -> return Icons.Default.MenuBook
            "far1-06-mehr-vafa" -> return Icons.Default.MenuBook
            "far1-07-jamal-kamal" -> return Icons.Default.MenuBook
            "far1-08-safar-basreh" -> return Icons.Default.MenuBook
            "far1-09-kelas-naghashi" -> return Icons.Default.MenuBook
            "far1-10-daryadelan-safshekan" -> return Icons.Default.MenuBook
            "far1-11-khak-azadegan" -> return Icons.Default.MenuBook
            "far1-12-rostam-ashkbous" -> return Icons.Default.MenuBook
            "far1-13-gordafarid" -> return Icons.Default.MenuBook
            "far1-14-tooti-baghal" -> return Icons.Default.MenuBook
            "far1-16-khosrow" -> return Icons.Default.MenuBook
            "far1-17-sepideh-dam" -> return Icons.Default.MenuBook
            "far1-18-azamat-negah" -> return Icons.Default.MenuBook
            "far1406-01-shokr-nemat" -> return Icons.Default.MenuBook
            "far1406-02-mast-hoshiyar" -> return Icons.Default.MenuBook
            "far1406-03-azadi" -> return Icons.Default.MenuBook
            "far1406-05-damavandiyeh" -> return Icons.Default.MenuBook
            "far1406-06-naynameh" -> return Icons.Default.MenuBook
            "far1406-07-haghighate-eshgh" -> return Icons.Default.MenuBook
            "far2-01-niki" -> return Icons.Default.MenuBook
            "far2-02-ghazi-bast" -> return Icons.Default.MenuBook
            "far2-03-dar-amvaj-sand" -> return Icons.Default.MenuBook
            "far2-05-aghazgari-tanha" -> return Icons.Default.MenuBook
            "far2-06-parvardeh-eshgh" -> return Icons.Default.MenuBook
            "far2-07-baran-mohabbat" -> return Icons.Default.MenuBook
            "far2-08-dar-kooy-asheghan" -> return Icons.Default.MenuBook
            "far2-09-zowq-latif" -> return Icons.Default.MenuBook
            "far2-10-bang-jaras" -> return Icons.Default.MenuBook
            "far2-11-yaran-ashegh" -> return Icons.Default.MenuBook
            "far2-12-kaveh-dadkhah" -> return Icons.Default.MenuBook
            "far2-14-hamleh-heydari" -> return Icons.Default.MenuBook
            "far2-15-kabootar-towqdar" -> return Icons.Default.MenuBook
            "far2-16-ghesseh-eynakam" -> return Icons.Default.MenuBook
            "far2-17-khamooshi-darya" -> return Icons.Default.MenuBook
            "far2-18-khan-adl" -> return Icons.Default.MenuBook
            "far3-08-az-pariz-ta-paris" -> return Icons.Default.MenuBook
            "far3-09-kavir" -> return Icons.Default.MenuBook
            "far3-10-fasl-shokufayi" -> return Icons.Default.MenuBook
            "far3-11-an-shab-aziz" -> return Icons.Default.MenuBook
            "far3-12-gozar-siyavash-az-atash" -> return Icons.Default.MenuBook
            "far3-13-khan-hashtom" -> return Icons.Default.MenuBook
            "far3-14-si-morgh-va-simorgh" -> return Icons.Default.MenuBook
            "far3-16-kabab-ghaz" -> return Icons.Default.MenuBook
            "far3-17-khande-to" -> return Icons.Default.MenuBook
            "far3-18-eshgh-javdani" -> return Icons.Default.MenuBook
            "farsi_01_syntax_roles_groups" -> return Icons.Default.FormatQuote
            "farsi_02_vocab_spelling_3_realms" -> return Icons.Default.Spellcheck
            "farsi_02_word_morphology" -> return Icons.Default.Spellcheck
            "farsi_04_advanced_syntax_pronoun_shift" -> return Icons.Default.FormatQuote
            "farsi_10_ch1_grammar_vocab_realms" -> return Icons.Default.Spellcheck
            "farsi_10_ch2_literary_intellectual_realms" -> return Icons.Default.Spellcheck
            "farsi_11_ch1_grammar_vocab_realms" -> return Icons.Default.FormatQuote
            "farsi_12_ch1_syntax_noun_phrase_realms" -> return Icons.Default.FormatQuote
            "farsi_12_ch2_final_exam_vocab_spelling_authors" -> return Icons.Default.Spellcheck
            "found1406-farsi-01-alphabet-reading" -> return Icons.Default.MenuBook
            "found1406-farsi-02-sentence-grammar" -> return Icons.Default.MenuBook
            "found1406-farsi-03-reading-signs" -> return Icons.Default.MenuBook
            "found1406-farsi-04-spelling-1" -> return Icons.Default.Spellcheck
            "found1406-farsi-05-spelling-2" -> return Icons.Default.Spellcheck
            "found1406-farsi-06-adjective-ezafe" -> return Icons.Default.MenuBook
            "found1406-farsi-07-pronoun-adverb" -> return Icons.Default.MenuBook
            "found1406-farsi-08-sentence-types" -> return Icons.Default.MenuBook
            "found1406-farsi-09-comprehension-1" -> return Icons.Default.MenuBook
            "found1406-farsi-10-comprehension-2" -> return Icons.Default.MenuBook
            "found1406-farsi-11-descriptive-essay" -> return Icons.Default.MenuBook
            "mid7-farsi-01-zang-afarinesh" -> return Icons.Default.MenuBook
            "mid7-farsi-02-cheshme-marefat" -> return Icons.Default.MenuBook
            "mid7-farsi-03-nasl-ayande-saz" -> return Icons.Default.MenuBook
            "mid7-farsi-04-bahar-mi-resad" -> return Icons.Default.MenuBook
            "mid7-farsi-05-dars-azad" -> return Icons.Default.MenuBook
            "mid7-farsi-06-ghalb-kochak" -> return Icons.Default.MenuBook
            "mid7-farsi-07-elm-zendegani" -> return Icons.Default.MenuBook
            "mid7-farsi-08-zendegi-lahzeha" -> return Icons.Default.MenuBook
            "mid7-farsi-09-nasihat-emam" -> return Icons.Default.MenuBook
            "mid7-farsi-10-kelas-adabiyat" -> return Icons.Default.MenuBook
            "mid7-farsi-11-ahd-peyman" -> return Icons.Default.MenuBook
            "mid7-farsi-12-khadamat-moteghabel" -> return Icons.Default.MenuBook
            "mid7-farsi-13-osveh-nikou" -> return Icons.Default.MenuBook
            "mid7-farsi-14-imam-khomeini" -> return Icons.Default.MenuBook
            "mid7-farsi-15-dars-azad" -> return Icons.Default.MenuBook
            "mid7-farsi-16-adam-ahani-shaparak" -> return Icons.Default.MenuBook
            "mid7-farsi-17-ma-mitavanim" -> return Icons.Default.MenuBook
            "mid8-farsi-01-pish-az-inha" -> return Icons.Default.MenuBook
            "mid8-farsi-02-khub-jahan-ra-bebin" -> return Icons.Default.MenuBook
            "mid8-farsi-03-armaghan-iran" -> return Icons.Default.MenuBook
            "mid8-farsi-04-safar-shekoftan" -> return Icons.Default.MenuBook
            "mid8-farsi-05-dars-azad" -> return Icons.Default.MenuBook
            "mid8-farsi-06-rah-nikbakhti" -> return Icons.Default.MenuBook
            "mid8-farsi-07-adab-nikan" -> return Icons.Default.MenuBook
            "mid8-farsi-08-azadegi" -> return Icons.Default.MenuBook
            "mid8-farsi-09-nojavan-bahush" -> return Icons.Default.MenuBook
            "mid8-farsi-10-qalam-sehramiz" -> return Icons.Default.MenuBook
            "mid8-farsi-11-parchamdaran" -> return Icons.Default.MenuBook
            "mid8-farsi-12-shir-hagh" -> return Icons.Default.MenuBook
            "mid8-farsi-13-adabiat-enghelab" -> return Icons.Default.MenuBook
            "mid8-farsi-14-yad-hossein" -> return Icons.Default.MenuBook
            "mid8-farsi-15-dars-azad" -> return Icons.Default.MenuBook
            "mid8-farsi-16-parande-azadi" -> return Icons.Default.MenuBook
            "mid8-farsi-17-rah-khoshbakhti" -> return Icons.Default.MenuBook
            "mid9-farsi-01-afarinesh" -> return Icons.Default.MenuBook
            "mid9-farsi-02-ajayeb" -> return Icons.Default.MenuBook
            "mid9-farsi-03-ayeneh" -> return Icons.Default.MenuBook
            "mid9-farsi-04-hamneshin" -> return Icons.Default.MenuBook
            "mid9-farsi-05-dars-azad" -> return Icons.Default.MenuBook
            "mid9-farsi-06-adab" -> return Icons.Default.MenuBook
            "mid9-farsi-07-partow-omid" -> return Icons.Default.MenuBook
            "mid9-farsi-08-mam-mihan" -> return Icons.Default.MenuBook
            "mid9-farsi-09-raz-movaffaghiat" -> return Icons.Default.MenuBook
            "mid9-farsi-10-arash" -> return Icons.Default.MenuBook
            "mid9-farsi-11-zan-parsa" -> return Icons.Default.MenuBook
            "mid9-farsi-12-payamavar-rahmat" -> return Icons.Default.MenuBook
            "mid9-farsi-13-ashna-ghariban-milad-gol" -> return Icons.Default.MenuBook
            "mid9-farsi-14-peyda-penhan" -> return Icons.Default.MenuBook
            "mid9-farsi-15-dars-azad-adabiyat-bumi-2" -> return Icons.Default.MenuBook
            "mid9-farsi-16-arezoo" -> return Icons.Default.MenuBook
            "mid9-farsi-17-shazde-koochooloo" -> return Icons.Default.MenuBook
            "site-sub-04" -> return Icons.Default.AutoStories
            // علوم و فنون
            "fon12-01-laf-nashr" -> return Icons.Default.AutoStories
            "fon12-02-oslub-moadele-hosn-talil" -> return Icons.Default.AutoStories
            "fon12-03-iham-tanasob" -> return Icons.Default.AutoStories
            "fon12-04-eghragh-talmih" -> return Icons.Default.AutoStories
            "fon3-01-tarikh-12-13-bazgasht-bidari" -> return Icons.Default.HistoryEdu
            "fon3-02-music-bases" -> return Icons.Default.MusicNote
            "fon3-03-moraat-tazmin" -> return Icons.Default.AutoStories
            "fon3-04-sabkshenasi-12-13" -> return Icons.Default.HistoryEdu
            "fon3-05-licenses-verbal" -> return Icons.Default.AutoStories
            "fon3-06-tazad-motanaghez" -> return Icons.Default.AutoStories
            "fon3-07-tarikh-14-moaser-enghelab" -> return Icons.Default.HistoryEdu
            "fon3-08-licenses-metric" -> return Icons.Default.AutoStories
            "fon3-10-sabkshenasi-moaser-enghelab" -> return Icons.Default.HistoryEdu
            "fon3-11-nimai-meter" -> return Icons.Default.AutoStories
            "fon3-12-hesamizi" -> return Icons.Default.AutoStories
            "fonoon_00_zero_to_100_all_24_arrayeh_step_by_step" -> return Icons.Default.AutoStories
            "fonoon_01_bayani_figures" -> return Icons.Default.AutoStories
            "fonoon_01_four_pillars" -> return Icons.Default.AutoStories
            "fonoon_01b_verbal_figures_saj" -> return Icons.Default.AutoStories
            "fonoon_02_iham_paradox_badie" -> return Icons.Default.AutoStories
            "fonoon_02b_spiritual_figures_talmih" -> return Icons.Default.AutoStories
            "fonoon_03_styles_history" -> return Icons.Default.HistoryEdu
            "fonoon_04_grade12_lafo_nashr_esloob_hasan" -> return Icons.Default.AutoStories
            "fonoon_04_mnemonics_final20" -> return Icons.Default.HistoryEdu
            "fonoon_05_styles_history_comprehensive" -> return Icons.Default.HistoryEdu
            "fonoon_06_advanced_konkoor_arrayeh_traps" -> return Icons.Default.AutoStories
            "fonoon_07_complete_literary_history_10_11_12" -> return Icons.Default.HistoryEdu
            "fonoon_10_ch1_history_khorasani" -> return Icons.Default.HistoryEdu
            "fonoon_10_ch2_badi_tarsee_movazeneh" -> return Icons.Default.AutoStories
            "fonoon_10_ch3_tashbih_majaz_10" -> return Icons.Default.AutoStories
            "fonoon_10_lesson10_sade5_6" -> return Icons.Default.HistoryEdu
            "fonoon_11_ch1_history_eraqi" -> return Icons.Default.HistoryEdu
            "fonoon_11_ch2_tashbih_types" -> return Icons.Default.AutoStories
            "fonoon_11_ch3_estearah_majaz_kenayeh" -> return Icons.Default.AutoStories
            "fonoon_12_ch1_history_hindi_bazgasht" -> return Icons.Default.HistoryEdu
            "fonoon_master_arrayeh_micro_notes" -> return Icons.Default.AutoStories
            // جغرافیا
            "found1406-soc-01-maps" -> return Icons.Default.Explore
            "found1406-soc-02-earth-motions" -> return Icons.Default.Public
            "found1406-soc-03-climate" -> return Icons.Default.Science
            "found1406-soc-04-population" -> return Icons.Default.Public
            "geo1-01-joghrafia-elmi-baraye-zendegi-behtar" -> return Icons.Default.Public
            "geo1-02-ravesh-motalee-va-pazhuhesh" -> return Icons.Default.Public
            "geo1-03-mogheiyat-joghrafiayi-iran" -> return Icons.Default.Public
            "geo1-04-nahamvarihaye-iran" -> return Icons.Default.Public
            "geo1-05-ab-va-havaye-iran" -> return Icons.Default.Public
            "geo1-06-manabe-ab-iran" -> return Icons.Default.Science
            "geo1-07-vizhegihaye-jamiyat-iran" -> return Icons.Default.Public
            "geo1-08-taqsimat-keshvari-iran" -> return Icons.Default.Explore
            "geo1-09-sokonatgahhaye-iran" -> return Icons.Default.Public
            "geo1-10-tavanhaye-eqtesadi-iran" -> return Icons.Default.TrendingUp
            "geo1406-01-settlements" -> return Icons.Default.Public
            "geo1406-02-city-rural-management" -> return Icons.Default.Public
            "geo1406-03-transport" -> return Icons.Default.TrendingUp
            "geo1406-04-natural-hazards" -> return Icons.Default.Public
            "geo2-01-mana-va-mafhum-nahiye" -> return Icons.Default.Explore
            "geo2-02-ensan-va-nahiye" -> return Icons.Default.Public
            "geo2-03-navahi-ab-va-havayi" -> return Icons.Default.Public
            "geo2-04-nahamvariha-va-ashkal-zamin" -> return Icons.Default.Explore
            "geo2-05-navahi-zisti" -> return Icons.Default.Public
            "geo2-06-navahi-farhangi" -> return Icons.Default.Public
            "geo2-07-navahi-eqtesadi-keshavarzi-va-sanati" -> return Icons.Default.TrendingUp
            "geo2-08-navahi-eqtesadi-tejarat-va-eqtesad-jahani" -> return Icons.Default.TrendingUp
            "geo2-09-mana-va-mafhum-nahiye-siyasi" -> return Icons.Default.Public
            "geo2-10-keshvar-yek-nahiye-siyasi" -> return Icons.Default.Public
            "geo2-11-geopolitik" -> return Icons.Default.Public
            "geo2-case2-islamic-world" -> return Icons.Default.Public
            "geo3-04-transport-management" -> return Icons.Default.TrendingUp
            "geo3-06-crisis-management" -> return Icons.Default.Public
            "geo3-skills-geo-techniques" -> return Icons.Default.Explore
            "geo_00_zero_maps_coordinates" -> return Icons.Default.Explore
            "geo_01_grade10_1403" -> return Icons.Default.Public
            "geo_02_grade11_1404" -> return Icons.Default.Public
            "geo_03_grade12_1405" -> return Icons.Default.Public
            "geo_04_iran_climate_rivers_gis" -> return Icons.Default.Science
            "geo_10_ch1_location_topography_climate_rivers" -> return Icons.Default.Science
            "geo_10_ch2_iran_population_settlements_economy" -> return Icons.Default.TrendingUp
            "geo_10_ch3_economy_transport_borders" -> return Icons.Default.TrendingUp
            "geo_11_ch1_regions_biomes_culture" -> return Icons.Default.Science
            "geo_11_ch2_economic_political_regions_seas" -> return Icons.Default.TrendingUp
            "geo_11_ch3_case_studies_sw_asia_europe" -> return Icons.Default.Public
            "geo_12_ch1_applied_geography_hazards" -> return Icons.Default.Public
            "geo_12_ch2_climate_spatial_planning_gis" -> return Icons.Default.Science
            "site-sub-10" -> return Icons.Default.Explore
            // تاریخ
            "found1406-soc-05-timeline" -> return Icons.Default.HistoryEdu
            "found1406-soc-06-ancient-iran" -> return Icons.Default.HistoryEdu
            "found1406-soc-07-islam-iran" -> return Icons.Default.HistoryEdu
            "found1406-soc-08-modern-iran" -> return Icons.Default.HistoryEdu
            "hist1-01" -> return Icons.Default.HistoryEdu
            "hist1-02" -> return Icons.Default.HistoryEdu
            "hist1-03" -> return Icons.Default.HistoryEdu
            "hist1-04" -> return Icons.Default.HistoryEdu
            "hist1-05" -> return Icons.Default.HistoryEdu
            "hist1-06" -> return Icons.Default.HistoryEdu
            "hist1-07" -> return Icons.Default.HistoryEdu
            "hist1-08" -> return Icons.Default.HistoryEdu
            "hist1-09" -> return Icons.Default.HistoryEdu
            "hist1-10" -> return Icons.Default.HistoryEdu
            "hist1-11" -> return Icons.Default.HistoryEdu
            "hist1-12" -> return Icons.Default.HistoryEdu
            "hist1-13" -> return Icons.Default.HistoryEdu
            "hist1-14" -> return Icons.Default.HistoryEdu
            "hist1-15" -> return Icons.Default.HistoryEdu
            "hist1-16" -> return Icons.Default.HistoryEdu
            "hist2-01" -> return Icons.Default.HistoryEdu
            "hist2-02" -> return Icons.Default.HistoryEdu
            "hist2-03" -> return Icons.Default.HistoryEdu
            "hist2-04" -> return Icons.Default.HistoryEdu
            "hist2-05" -> return Icons.Default.HistoryEdu
            "hist2-06" -> return Icons.Default.HistoryEdu
            "hist2-07" -> return Icons.Default.HistoryEdu
            "hist2-08" -> return Icons.Default.HistoryEdu
            "hist2-09" -> return Icons.Default.HistoryEdu
            "hist2-10" -> return Icons.Default.HistoryEdu
            "hist2-11" -> return Icons.Default.HistoryEdu
            "hist2-12" -> return Icons.Default.HistoryEdu
            "hist2-13" -> return Icons.Default.HistoryEdu
            "hist2-14" -> return Icons.Default.HistoryEdu
            "hist2-15" -> return Icons.Default.HistoryEdu
            "hist2-16" -> return Icons.Default.HistoryEdu
            "hist3-01" -> return Icons.Default.HistoryEdu
            "hist3-02" -> return Icons.Default.HistoryEdu
            "hist3-03" -> return Icons.Default.HistoryEdu
            "hist3-04" -> return Icons.Default.HistoryEdu
            "hist3-05" -> return Icons.Default.HistoryEdu
            "hist3-06" -> return Icons.Default.HistoryEdu
            "hist3-07" -> return Icons.Default.HistoryEdu
            "hist3-08" -> return Icons.Default.HistoryEdu
            "hist3-09" -> return Icons.Default.HistoryEdu
            "hist3-10" -> return Icons.Default.HistoryEdu
            "hist3-11" -> return Icons.Default.HistoryEdu
            "hist3-12" -> return Icons.Default.HistoryEdu
            "hist_00_elementary_to_9th_zero" -> return Icons.Default.HistoryEdu
            "hist_01_grade10_1403" -> return Icons.Default.HistoryEdu
            "hist_02_grade11_1404" -> return Icons.Default.HistoryEdu
            "hist_03_grade12_1405" -> return Icons.Default.HistoryEdu
            "hist_04_treaties_kings_konkoor_tables" -> return Icons.Default.HistoryEdu
            "hist_05_safavid_constitutional_oil" -> return Icons.Default.HistoryEdu
            "hist_10_ch1_ancient_world_mesopotamia_egypt" -> return Icons.Default.HistoryEdu
            "hist_10_ch2_ancient_iran_medes_achaemenids_parthians_sasanians" -> return Icons.Default.HistoryEdu
            "hist_11_ch1_islam_prophet_caliphates" -> return Icons.Default.HistoryEdu
            "hist_11_ch2_iran_islamic_dynasties_tahirid_to_timurid" -> return Icons.Default.HistoryEdu
            "hist_12_ch1_afshar_zand_qajar_constitution" -> return Icons.Default.HistoryEdu
            "hist_12_ch2_pahlavi_oil_nationalization_revolution" -> return Icons.Default.HistoryEdu
            "site-sub-06" -> return Icons.Default.HistoryEdu
            // منطق
            "found1406-logic-01-think" -> return Icons.Default.AltRoute
            "found1406-logic-02-word-meaning" -> return Icons.Default.AltRoute
            "found1406-logic-03-concept" -> return Icons.Default.AltRoute
            "found1406-logic-04-definition" -> return Icons.Default.MenuBook
            "found1406-logic-05-proposition" -> return Icons.Default.AltRoute
            "found1406-logic-06-reasoning" -> return Icons.Default.AltRoute
            "found1406-logic-07-syllogism" -> return Icons.Default.AltRoute
            "found1406-logic-08-induction-analogy" -> return Icons.Default.AltRoute
            "found1406-logic-09-fallacies" -> return Icons.Default.WarningAmber
            "found1406-logic-10-workshop" -> return Icons.Default.AltRoute
            "logic-tip-01" -> return Icons.Default.AltRoute
            "logic1406-01-definitions-deep" -> return Icons.Default.MenuBook
            "logic1406-02-propositions-square-conversion" -> return Icons.Default.AltRoute
            "logic1406-03-syllogism-reasoning" -> return Icons.Default.AltRoute
            "logic1406-04-fallacies-deep" -> return Icons.Default.WarningAmber
            "logic1406-05-esteghra-tamsil" -> return Icons.Default.AltRoute
            "logic_10_lesson1_2_concepts_terms" -> return Icons.Default.AltRoute
            "logic_10_lesson3_4_relations_five_universals" -> return Icons.Default.AltRoute
            "logic_10_lesson5_definitions" -> return Icons.Default.MenuBook
            "logic_phil_complete_mastery" -> return Icons.Default.AltRoute
            "logicm1406-01-philosophy-compare" -> return Icons.Default.AltRoute
            "phil_00_khodemoni_logic_10_complete" -> return Icons.Default.AltRoute
            "phil_01_logic_concepts_five_universals" -> return Icons.Default.MenuBook
            "phil_02_logic_propositions_square" -> return Icons.Default.AltRoute
            "phil_03_logic_syllogisms_fallacies" -> return Icons.Default.WarningAmber
            "philo_03_logic_syllogism_conditional" -> return Icons.Default.AltRoute
            // ریاضی و آمار
            "found1406-math-01-add-subtract" -> return Icons.Default.Calculate
            "found1406-math-02-multiplication-fractions" -> return Icons.Default.Calculate
            "found1406-math-03-division" -> return Icons.Default.Calculate
            "found1406-math-04-negative-numbers" -> return Icons.Default.Calculate
            "found1406-math-05-fractions-1" -> return Icons.Default.Calculate
            "found1406-math-06-fractions-2" -> return Icons.Default.Calculate
            "found1406-math-07-fractions-3" -> return Icons.Default.Calculate
            "found1406-math-08-decimals-1" -> return Icons.Default.Calculate
            "found1406-math-09-decimals-2" -> return Icons.Default.Calculate
            "found1406-math-10-ratio-proportion" -> return Icons.Default.Calculate
            "found1406-math-11-percent-1" -> return Icons.Default.Calculate
            "found1406-math-12-percent-2" -> return Icons.Default.Calculate
            "found1406-math-13-powers-roots" -> return Icons.Default.GraphicEq
            "found1406-math-14-algebra-1" -> return Icons.Default.Calculate
            "found1406-math-15-equations-1" -> return Icons.Default.GraphicEq
            "found1406-math-16-geometry-1" -> return Icons.Default.Calculate
            "found1406-math-17-geometry-2" -> return Icons.Default.Calculate
            "found1406-math-18-geometry-3" -> return Icons.Default.Calculate
            "found1406-math-19-coordinates" -> return Icons.Default.Calculate
            "found1406-math-20-rational-irrational" -> return Icons.Default.Calculate
            "found1406-math-21-statistics-1" -> return Icons.Default.BarChart
            "found1406-math-22-statistics-2" -> return Icons.Default.BarChart
            "found1406-math-23-probability-1" -> return Icons.Default.QueryStats
            "found1406-math-24-sets" -> return Icons.Default.QueryStats
            "math_000_bismillah_mastery" -> return Icons.Default.Calculate
            "math_00_elementary_school" -> return Icons.Default.Calculate
            "math_01_signs_fractions" -> return Icons.Default.Calculate
            "math_01b_powers_radicals_identities" -> return Icons.Default.GraphicEq
            "math_02_equations" -> return Icons.Default.GraphicEq
            "math_02b_sets_venn_intervals" -> return Icons.Default.TableChart
            "math_03_functions" -> return Icons.Default.BarChart
            "math_03_radicals_powers" -> return Icons.Default.GraphicEq
            "math_04_functions_domain" -> return Icons.Default.Functions
            "math_05_sequences_arithmetic" -> return Icons.Default.Timeline
            "math_05c_grade11_functions_indices" -> return Icons.Default.BarChart
            "math_05d_sequences_arithmetic_deep" -> return Icons.Default.BarChart
            "math_05e_sequences_geometric_deep" -> return Icons.Default.BarChart
            "math_06_statistics_dispersion" -> return Icons.Default.BarChart
            "math_07_combinatorics_probability" -> return Icons.Default.QueryStats
            "math_07_grade12_regression_time_series" -> return Icons.Default.BarChart
            "math_08_logic_propositions" -> return Icons.Default.QueryStats
            "math_09_statistics_percentiles" -> return Icons.Default.BarChart
            "math_10_ch4_data_display" -> return Icons.Default.BarChart
            "math_10_conditional_probability" -> return Icons.Default.QueryStats
            "math_11_grade11_functions_exponential" -> return Icons.Default.Functions
            "math_12_konkoor_80_percent_shortcuts" -> return Icons.Default.Bolt
            "math_ultra_fast_techniques" -> return Icons.Default.Bolt
            "mid7-math-01-problem-strategies" -> return Icons.Default.Calculate
            "mid7-math-02-integers" -> return Icons.Default.Calculate
            "mid7-math-03-algebra-equation" -> return Icons.Default.GraphicEq
            "mid7-math-04-geometry-reasoning" -> return Icons.Default.Calculate
            "mid7-math-05-divisors-primes" -> return Icons.Default.Calculate
            "mid7-math-06-surface-volume" -> return Icons.Default.Calculate
            "mid7-math-07-power-root" -> return Icons.Default.GraphicEq
            "mid7-math-08-vector-coordinate" -> return Icons.Default.Calculate
            "mid7-math-09-stats-probability" -> return Icons.Default.BarChart
            "mid8-math-01-integers-rationals" -> return Icons.Default.Calculate
            "mid8-math-02-prime-numbers" -> return Icons.Default.Calculate
            "mid8-math-03-polygons" -> return Icons.Default.Calculate
            "mid8-math-04-algebra-equation" -> return Icons.Default.GraphicEq
            "mid8-math-05-vector-coordinate" -> return Icons.Default.Calculate
            "mid8-math-06-triangle" -> return Icons.Default.Calculate
            "mid8-math-07-power-root" -> return Icons.Default.GraphicEq
            "mid8-math-08-stats-probability" -> return Icons.Default.BarChart
            "mid8-math-09-circle" -> return Icons.Default.Calculate
            "mid9-math-01-sets" -> return Icons.Default.TableChart
            "mid9-math-02-real-numbers" -> return Icons.Default.Calculate
            "mid9-math-03-reasoning-proof-geometry" -> return Icons.Default.Calculate
            "mid9-math-04-powers-roots" -> return Icons.Default.GraphicEq
            "mid9-math-05-algebraic-expressions" -> return Icons.Default.Calculate
            "mid9-math-06-linear-equations" -> return Icons.Default.GraphicEq
            "mid9-math-07-rational-expressions" -> return Icons.Default.Calculate
            "mid9-math-08-volume-area" -> return Icons.Default.Calculate
            "site-sub-01" -> return Icons.Default.Calculate
            // فلسفه
            "phil-tip-01" -> return Icons.Default.Psychology
            "phil1-03-falsafe-va-zendegi" -> return Icons.Default.Psychology
            "phil1-05-zendegi-bar-asas-andishe" -> return Icons.Default.Psychology
            "phil1-09-chisti-ensan-1" -> return Icons.Default.Psychology
            "phil1-10-chisti-ensan-2" -> return Icons.Default.Psychology
            "phil1-11-ensan-mowjud-akhlaqgara" -> return Icons.Default.Psychology
            "phil2-01-hasti-va-chisti" -> return Icons.Default.Psychology
            "phil2-02-jahan-momkenat" -> return Icons.Default.Psychology
            "phil2-03-jahan-elli-malooli" -> return Icons.Default.Psychology
            "phil2-04-kodam-tasvir" -> return Icons.Default.Psychology
            "phil2-05-khoda-dar-falsafe-1" -> return Icons.Default.Psychology
            "phil2-06-khoda-dar-falsafe-2" -> return Icons.Default.Psychology
            "phil2-07-aghl-1" -> return Icons.Default.Psychology
            "phil2-07-details-aghl-dore-jadid" -> return Icons.Default.Psychology
            "phil2-08-aghl-2" -> return Icons.Default.Psychology
            "phil2-08-stages-roshd-aghl" -> return Icons.Default.Psychology
            "phil2-09-aghaz-falsafeh" -> return Icons.Default.Psychology
            "phil2-10-dowreh-miyani" -> return Icons.Default.Psychology
            "phil2-11-dowran-motakhkher" -> return Icons.Default.Psychology
            "phil2-12-hekmat-moaser" -> return Icons.Default.Psychology
            "phil_04_grade11_history_epistemology" -> return Icons.Default.Psychology
            "phil_05_grade12_existence_quiddity" -> return Icons.Default.Psychology
            "phil_06_grade12_causality_motion_schools" -> return Icons.Default.Psychology
            "phil_07_grade12_mulla_sadra_and_theology" -> return Icons.Default.Psychology
            "phil_08_konkoor_100_master_tables" -> return Icons.Default.AltRoute
            "phil_09_khodemoni_philosophy_11_12_complete" -> return Icons.Default.Psychology
            "phil_11_ch1_what_is_philosophy_greeks" -> return Icons.Default.Psychology
            "phil_11_ch2_epistemology_knowledge" -> return Icons.Default.Psychology
            "phil_11_ch3_islamic_philosophy_ethics" -> return Icons.Default.Psychology
            "philo_04_grade11_history_epistemology" -> return Icons.Default.Psychology
            "philo_07_mnemonics_final20" -> return Icons.Default.AltRoute
            "site-sub-03" -> return Icons.Default.AltRoute
            // روان‌شناسی
            "psy1406-01-learning" -> return Icons.Default.Psychology
            "psy1406-02-emotion-needs" -> return Icons.Default.Psychology
            "psy1406-03-personality" -> return Icons.Default.Psychology
            "psy1406-04-mental-health" -> return Icons.Default.Psychology
            "psych-tip-01" -> return Icons.Default.Psychology
            "psych1406-07-negaresh" -> return Icons.Default.Psychology
            "psych1406-08-sabk-zendegi-salem" -> return Icons.Default.Psychology
            "psych1406-erikson-stages" -> return Icons.Default.Psychology
            "psych_00_zero_khodemoni" -> return Icons.Default.Psychology
            "psych_01_grade11_1404_complete" -> return Icons.Default.Lightbulb
            "psych_02_sensation_memory_forgetting" -> return Icons.Default.Lightbulb
            "psych_03_perception_attention_gestalt" -> return Icons.Default.EmojiObjects
            "psych_04_memory_forgetting_strategies" -> return Icons.Default.Lightbulb
            "psych_11_ch1_schools_and_development" -> return Icons.Default.Psychology
            "psych_11_ch3_thinking_problem_solving" -> return Icons.Default.Psychology
            "psych_11_ch4_stress_mental_health" -> return Icons.Default.Psychology
            "site-sub-09" -> return Icons.Default.Psychology
            // جامعه‌شناسی
            "found1406-soc-09-rights-law" -> return Icons.Default.Groups
            "found1406-soc-10-culture-identity" -> return Icons.Default.Public
            "site-sub-05" -> return Icons.Default.Groups
            "soc-gap-soc1-06-weber-iron-cage" -> return Icons.Default.Groups
            "soc-gap-soc1-11-cultural-transformation-chain" -> return Icons.Default.Public
            "soc-gap-soc1-15-aristotle-six-systems" -> return Icons.Default.Groups
            "soc-gap-soc1-thinkers-chapter-openings" -> return Icons.Default.Groups
            "soc-gap-soc2-06-jean-calvin" -> return Icons.Default.Public
            "soc-gap-soc2-10-cuban-crisis-iran-famine" -> return Icons.Default.Groups
            "soc-gap-soc2-14-social-revolution-jihad-treatises" -> return Icons.Default.Groups
            "soc-gap-soc2-15-cultural-geography-palestine" -> return Icons.Default.Public
            "soc-gap-soc3-06-personality-persuasive-power" -> return Icons.Default.Groups
            "soc-tip-01" -> return Icons.Default.Public
            "soc-tip-02" -> return Icons.Default.Groups
            "soc-tip-03" -> return Icons.Default.Groups
            "soc1-01" -> return Icons.Default.Groups
            "soc1-02" -> return Icons.Default.Groups
            "soc1-03" -> return Icons.Default.Groups
            "soc1-04" -> return Icons.Default.Groups
            "soc1-05" -> return Icons.Default.Groups
            "soc1-06" -> return Icons.Default.Groups
            "soc1-07" -> return Icons.Default.Groups
            "soc1-08" -> return Icons.Default.Badge
            "soc1-09" -> return Icons.Default.Badge
            "soc1-10" -> return Icons.Default.Badge
            "soc1-11" -> return Icons.Default.Badge
            "soc1-12" -> return Icons.Default.Badge
            "soc1-13" -> return Icons.Default.Badge
            "soc1-14" -> return Icons.Default.Badge
            "soc1-15" -> return Icons.Default.Badge
            "soc1-16" -> return Icons.Default.Badge
            "soc2-01" -> return Icons.Default.Public
            "soc2-02" -> return Icons.Default.Public
            "soc2-03" -> return Icons.Default.Public
            "soc2-04" -> return Icons.Default.Public
            "soc2-05" -> return Icons.Default.Public
            "soc2-06" -> return Icons.Default.Public
            "soc2-07" -> return Icons.Default.Groups
            "soc2-08" -> return Icons.Default.Groups
            "soc2-09" -> return Icons.Default.Groups
            "soc2-10" -> return Icons.Default.Groups
            "soc2-11" -> return Icons.Default.Groups
            "soc2-12" -> return Icons.Default.Groups
            "soc2-13" -> return Icons.Default.Groups
            "soc2-14" -> return Icons.Default.Groups
            "soc2-15" -> return Icons.Default.Groups
            "soc3-01" -> return Icons.Default.Groups
            "soc3-02" -> return Icons.Default.Groups
            "soc3-03" -> return Icons.Default.Groups
            "soc3-04" -> return Icons.Default.Groups
            "soc3-05" -> return Icons.Default.Groups
            "soc3-06" -> return Icons.Default.Groups
            "soc3-07" -> return Icons.Default.Groups
            "soc3-08" -> return Icons.Default.Badge
            "soc3-09" -> return Icons.Default.Groups
            "soc3-10" -> return Icons.Default.Groups
            "soc_000_khodemoni_zero_to_100" -> return Icons.Default.Groups
            "soc_00_elementary_to_9th_zero" -> return Icons.Default.Groups
            "soc_01_grade10_1403_complete" -> return Icons.Default.Public
            "soc_01b_grade10_identity_mobility_control" -> return Icons.Default.Badge
            "soc_02_grade11_1404_complete" -> return Icons.Default.Public
            "soc_02b_grade11_west_islam_modernity" -> return Icons.Default.Groups
            "soc_03_grade12_1405_complete" -> return Icons.Default.Groups
            "soc_10_ch1_human_social_action" -> return Icons.Default.Groups
            "soc_10_ch2_culture_layers_values" -> return Icons.Default.Public
            "soc_11_ch1_cultural_world_globalization" -> return Icons.Default.Public
            "soc_12_ch1_three_approaches_social_sciences" -> return Icons.Default.Groups
            "soc_12_ch2_social_order_family_policy" -> return Icons.Default.Groups
            "soc_master_10_11_12_full_encyclopedia" -> return Icons.Default.Public
        }
        return when (subjectId.lowercase().trim()) {
            "arabic" -> Icons.Default.Translate
            "arooz" -> Icons.Default.MusicNote
            "defa" -> Icons.Default.EmojiEvents
            "dini" -> Icons.Default.Spa
            "economics" -> Icons.Default.AccountBalance
            "english" -> Icons.Default.Language
            "farhangian" -> Icons.Default.School
            "farsi" -> Icons.Default.MenuBook
            "fonoon" -> Icons.Default.AutoStories
            "general_humanities" -> Icons.Default.Favorite
            "geography" -> Icons.Default.Public
            "hedyeh" -> Icons.Default.Spa
            "history" -> Icons.Default.HistoryEdu
            "honar" -> Icons.Default.Palette
            "karfan" -> Icons.Default.Assignment
            "logic" -> Icons.Default.AltRoute
            "math" -> Icons.Default.Calculate
            "media_environment" -> Icons.Default.PlayArrow
            "negaresh" -> Icons.Default.EditNote
            "payam" -> Icons.Default.Favorite
            "philosophy" -> Icons.Default.Psychology
            "psychology" -> Icons.Default.Psychology
            "quran" -> Icons.Default.MenuBook
            "science" -> Icons.Default.Science
            "social_identity" -> Icons.Default.Badge
            "social_studies" -> Icons.Default.Groups
            "sociology" -> Icons.Default.Groups
            "study_methods" -> Icons.Default.CalendarMonth
            "tafakkor" -> Icons.Default.EmojiObjects
            else -> Icons.Default.MenuBook
        }
    }
}
