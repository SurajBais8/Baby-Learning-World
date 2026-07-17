package com.example.ui.screens

import androidx.compose.animation.*
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.ProgressDatabase

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(
    viewModel: MainViewModel,
    lang: String,
    onBack: () -> Unit
) {
    val isHindi = lang == "hi"
    val userProgress by viewModel.userProgress.collectAsState()

    // Temporary values for toggles since we don't have separate boolean settings,
    // we can manage state inside SettingsScreen or add it to VM.
    // For simple, solid UX, we can save them as mutableStateOf and simulate,
    // or toggle VM darkTheme / language!
    var isMusicOn by remember { mutableStateOf(true) }
    var isSoundOn by remember { mutableStateOf(true) }
    var isVoiceOn by remember { mutableStateOf(true) }

    // State for Reset progress modal & Parental lock
    var showParentGate by remember { mutableStateOf(false) }
    var actionToPerform by remember { mutableStateOf<String?>(null) } // "reset"
    var firstTerm by remember { mutableStateOf((3..8).random()) }
    var secondTerm by remember { mutableStateOf((2..6).random()) }
    var inputAnswer by remember { mutableStateOf("") }
    var gateError by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = if (isHindi) "ऐप सेटिंग्स ⚙️" else "App Settings ⚙️",
                        fontWeight = FontWeight.Bold,
                        fontSize = 22.sp
                    )
                },
                navigationIcon = {
                    IconButton(
                        onClick = onBack,
                        modifier = Modifier.testTag("btn_settings_back")
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                }
            )
        }
    ) { innerPadding ->
        PlaygroundBackground {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .padding(20.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Top
            ) {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(24.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.9f))
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(20.dp),
                        verticalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        Text(
                            text = if (isHindi) "पसंद और सेटिंग्स" else "Preferences",
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Black,
                            color = MaterialTheme.colorScheme.primary
                        )

                        // Music Switch
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = if (isHindi) "पार्श्व संगीत (Music)" else "Background Music",
                                fontWeight = FontWeight.Bold,
                                fontSize = 16.sp
                            )
                            Switch(
                                checked = isMusicOn,
                                onCheckedChange = {
                                    isMusicOn = it
                                    viewModel.speak(
                                        if (it) "Music turned on!" else "Music turned off!",
                                        if (it) "संगीत चालू किया गया!" else "संगीत बंद किया गया!"
                                    )
                                },
                                modifier = Modifier.testTag("switch_music")
                            )
                        }

                        // Sound Switch
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = if (isHindi) "क्लिक आवाज (Sounds)" else "Click Sounds",
                                fontWeight = FontWeight.Bold,
                                fontSize = 16.sp
                            )
                            Switch(
                                checked = isSoundOn,
                                onCheckedChange = {
                                    isSoundOn = it
                                    viewModel.speak(
                                        if (it) "Sounds enabled!" else "Sounds disabled!",
                                        if (it) "ध्वनि प्रभाव सक्षम!" else "ध्वनि प्रभाव अक्षम!"
                                    )
                                },
                                modifier = Modifier.testTag("switch_sounds")
                            )
                        }

                        // Voice Switch
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = if (isHindi) "आवाज गाइड (Voice Guides)" else "Pronunciation Guides",
                                fontWeight = FontWeight.Bold,
                                fontSize = 16.sp
                            )
                            Switch(
                                checked = isVoiceOn,
                                onCheckedChange = {
                                    isVoiceOn = it
                                    if (it) {
                                        viewModel.speak("Voice guides active!", "आवाज गाइड सक्रिय!")
                                    }
                                },
                                modifier = Modifier.testTag("switch_voice")
                            )
                        }

                        Divider()

                        // Language Selection List
                        Column(
                            modifier = Modifier.fillMaxWidth(),
                            verticalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Text(
                                text = when (userProgress.lang) {
                                    "hi" -> "ऐप भाषा (Language)"
                                    "es" -> "Idioma de la aplicación"
                                    "fr" -> "Langue de l'application"
                                    "ar" -> "لغة التطبيق"
                                    else -> "App Language"
                                },
                                fontWeight = FontWeight.Bold,
                                fontSize = 16.sp
                            )
                            
                            val languages = listOf(
                                Triple("en", "English 🇬🇧", "English"),
                                Triple("hi", "हिंदी 🇮🇳", "Hindi"),
                                Triple("es", "Español 🇪🇸", "Spanish"),
                                Triple("fr", "Français 🇫🇷", "French"),
                                Triple("ar", "العربية 🇸🇦", "Arabic")
                            )
                            
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 4.dp),
                                horizontalArrangement = Arrangement.spacedBy(6.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                languages.forEach { (code, label, _) ->
                                    val isSelected = userProgress.lang == code
                                    Box(
                                        modifier = Modifier
                                            .weight(1f)
                                            .clip(RoundedCornerShape(12.dp))
                                            .background(
                                                if (isSelected) MaterialTheme.colorScheme.primary
                                                else MaterialTheme.colorScheme.surfaceVariant
                                            )
                                            .clickable {
                                                viewModel.setLanguage(code)
                                            }
                                            .padding(vertical = 6.dp)
                                            .testTag("lang_select_$code"),
                                        contentAlignment = Alignment.Center
                                    ) {
                                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                            Text(
                                                text = label.split(" ").last(), // Flag
                                                fontSize = 20.sp
                                            )
                                            Spacer(modifier = Modifier.height(2.dp))
                                            Text(
                                                text = label.split(" ").first(), // Label
                                                fontSize = 9.sp,
                                                fontWeight = FontWeight.Bold,
                                                color = if (isSelected) MaterialTheme.colorScheme.onPrimary
                                                else MaterialTheme.colorScheme.onSurfaceVariant
                                            )
                                        }
                                    }
                                }
                            }
                        }

                        // Dark Mode Toggle
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = if (isHindi) "डार्क मोड (Dark Mode)" else "Dark Mode",
                                fontWeight = FontWeight.Bold,
                                fontSize = 16.sp
                            )
                            Switch(
                                checked = userProgress.isDarkMode,
                                onCheckedChange = { viewModel.toggleDarkMode() },
                                modifier = Modifier.testTag("switch_dark_mode")
                            )
                        }

                        Divider()

                        // Reset Progress Button (Parental Gate Protected)
                        Button(
                            onClick = {
                                actionToPerform = "reset"
                                showParentGate = true
                                firstTerm = (3..8).random()
                                secondTerm = (2..6).random()
                                inputAnswer = ""
                                gateError = false
                            },
                            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFD32F2F)),
                            shape = RoundedCornerShape(16.dp),
                            modifier = Modifier
                                .fillMaxWidth()
                                .testTag("btn_reset_progress")
                        ) {
                            Icon(imageVector = Icons.Default.Lock, contentDescription = "Lock", modifier = Modifier.size(16.dp))
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(
                                text = if (isHindi) "प्रगति रीसेट करें (Reset)" else "Reset All Progress",
                                fontWeight = FontWeight.Bold,
                                color = Color.White
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Info card
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(20.dp),
                    colors = CardDefaults.cardColors(containerColor = Color(0xFFE8F5E9))
                ) {
                    Row(
                        modifier = Modifier
                            .padding(16.dp)
                            .fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(imageVector = Icons.Default.Info, contentDescription = "Info", tint = Color(0xFF2E7D32))
                        Spacer(modifier = Modifier.width(10.dp))
                        Text(
                            text = if (isHindi) "यह ऐप ऑफ़लाइन काम करता है। बच्चे का डेटा केवल आपके डिवाइस पर सुरक्षित रूप से संग्रहीत किया जाता है।" else "Offline Mode Active. Your child's learning scores and history are stored locally and 100% privately.",
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF2E7D32)
                        )
                    }
                }
            }

            // Parental Lock Gate Modal for sensitive operations
            if (showParentGate) {
                AlertDialog(
                    onDismissRequest = { showParentGate = false },
                    title = {
                        Text(
                            text = if (isHindi) "सुरक्षा द्वार (Parent Gate) 👨‍👩‍👧" else "Parental Authorization Gate 👨‍👩‍👧",
                            fontWeight = FontWeight.Black,
                            fontSize = 18.sp
                        )
                    },
                    text = {
                        Column {
                            Text(
                                text = if (isHindi) "आगे बढ़ने से पहले कृपया इस प्रश्न का उत्तर दें:" else "To perform this action, please solve this math question:",
                                fontSize = 14.sp,
                                modifier = Modifier.padding(bottom = 12.dp)
                            )

                            // Math equation
                            Text(
                                text = "$firstTerm + $secondTerm = ?",
                                fontSize = 28.sp,
                                fontWeight = FontWeight.Black,
                                textAlign = TextAlign.Center,
                                modifier = Modifier.fillMaxWidth()
                            )

                            OutlinedTextField(
                                value = inputAnswer,
                                onValueChange = { inputAnswer = it },
                                label = { Text(if (isHindi) "जवाब" else "Your Answer") },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(top = 12.dp)
                                    .testTag("input_parental_reset_answer")
                            )

                            if (gateError) {
                                Text(
                                    text = if (isHindi) "गलत उत्तर! कृपया फिर से कोशिश करें।" else "Incorrect answer! Please try again.",
                                    color = Color.Red,
                                    fontSize = 12.sp,
                                    modifier = Modifier.padding(top = 4.dp)
                                )
                            }
                        }
                    },
                    confirmButton = {
                        Button(
                            onClick = {
                                val expected = firstTerm + secondTerm
                                if (inputAnswer.trim() == "$expected") {
                                    showParentGate = false
                                    gateError = false
                                    if (actionToPerform == "reset") {
                                        // Reset Room Progress database locally
                                        viewModel.addStars(-viewModel.userProgress.value.totalStars) // reset stars to 0
                                        viewModel.speak(
                                            "Progress successfully reset! Let's start the adventure again!",
                                            "प्रगति सफलतापूर्वक रीसेट हो गई! चलिए फिर से शुरुआत करते हैं!"
                                        )
                                    }
                                } else {
                                    gateError = true
                                    firstTerm = (3..8).random()
                                    secondTerm = (2..6).random()
                                }
                            }
                        ) {
                            Text(text = if (isHindi) "पुष्टि करें" else "Confirm")
                        }
                    },
                    dismissButton = {
                        TextButton(
                            onClick = { showParentGate = false }
                        ) {
                            Text(text = if (isHindi) "रद्द करें" else "Cancel")
                        }
                    }
                )
            }
        }
    }
}
