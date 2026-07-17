package com.example.ui.screens

import android.media.AudioFormat
import android.media.AudioManager
import android.media.AudioTrack
import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.MusicNote
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.LearningData
import kotlin.concurrent.thread

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InstrumentsScreen(
    viewModel: MainViewModel,
    lang: String,
    onBack: () -> Unit
) {
    val isHindi = lang == "hi"
    val instruments = LearningData.instrumentList
    var activeIndex by remember { mutableStateOf<Int?>(0) }

    // Fun music scale jump animation
    val infiniteTransition = rememberInfiniteTransition(label = "music_note")
    val noteScale by infiniteTransition.animateFloat(
        initialValue = 1f,
        targetValue = 1.15f,
        animationSpec = infiniteRepeatable(
            animation = tween(600, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "scale"
    )

    // Synthesizes a pure sine wave tone at a specific frequency
    fun playSynthesizedTone(frequencyHz: Int) {
        thread {
            val sampleRate = 44100
            val durationSeconds = 0.4
            val numSamples = (sampleRate * durationSeconds).toInt()
            val sample = DoubleArray(numSamples)
            val generatedSnd = ByteArray(2 * numSamples)
            
            // Build sine wave audio samples
            for (i in 0 until numSamples) {
                sample[i] = Math.sin(2 * Math.PI * i / (sampleRate / frequencyHz))
            }
            
            // Convert to 16-bit PCM sound array
            var idx = 0
            for (dVal in sample) {
                val valShort = (dVal * 32767).toInt().toShort()
                generatedSnd[idx++] = (valShort.toInt() and 0x00ff).toByte()
                generatedSnd[idx++] = ((valShort.toInt() and 0xff00) ushr 8).toByte()
            }
            
            try {
                val audioTrack = AudioTrack(
                    AudioManager.STREAM_MUSIC,
                    sampleRate,
                    AudioFormat.CHANNEL_OUT_MONO,
                    AudioFormat.ENCODING_PCM_16BIT,
                    generatedSnd.size,
                    AudioTrack.MODE_STATIC
                )
                audioTrack.write(generatedSnd, 0, generatedSnd.size)
                audioTrack.play()
                
                // Allow sound playback, then release resources
                Thread.sleep((durationSeconds * 1000).toLong() + 50)
                audioTrack.stop()
                audioTrack.release()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = if (isHindi) "संगीत वाद्ययंत्र 🥁" else "Musical Instruments 🥁",
                        fontWeight = FontWeight.Bold,
                        fontSize = 22.sp
                    )
                },
                navigationIcon = {
                    IconButton(
                        onClick = onBack,
                        modifier = Modifier.testTag("btn_instruments_back")
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
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
                .padding(innerPadding)
                .padding(horizontal = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            
            // Large selected instrument layout
            if (activeIndex != null) {
                val instrument = instruments[activeIndex!!]
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 12.dp)
                        .testTag("instrument_detail_card"),
                    shape = RoundedCornerShape(28.dp),
                    colors = CardDefaults.cardColors(containerColor = Color(0xFFE1F5FE)),
                    elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(20.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        // Dancing instrument emoji
                        Text(
                            text = instrument.emoji,
                            fontSize = 85.sp,
                            modifier = Modifier
                                .scale(noteScale)
                                .clickable {
                                    viewModel.speak(instrument.nameEn, instrument.nameHi)
                                    playSynthesizedTone(instrument.soundToneHz)
                                    viewModel.addStars(1)
                                }
                                .padding(12.dp)
                        )

                        Text(
                            text = if (isHindi) instrument.nameHi else instrument.nameEn,
                            fontSize = 28.sp,
                            fontWeight = FontWeight.Black,
                            color = Color(0xFF01579B)
                        )

                        Spacer(modifier = Modifier.height(12.dp))

                        Button(
                            onClick = {
                                viewModel.speak(instrument.nameEn, instrument.nameHi)
                                playSynthesizedTone(instrument.soundToneHz)
                                viewModel.addStars(1)
                                viewModel.spawnBalloons(3)
                            },
                            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF0288D1)),
                            shape = RoundedCornerShape(16.dp),
                            modifier = Modifier.testTag("btn_speak_instrument")
                        ) {
                            Icon(imageVector = Icons.Default.MusicNote, contentDescription = "Play Sound", tint = Color.White)
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(
                                text = if (isHindi) "धुनी सुनें" else "Play Music Sound",
                                fontSize = 15.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                }
            }

            Text(
                text = if (isHindi) "बजाने के लिए वाद्ययंत्र चुनें! 👇" else "Tap an instrument to play sound! 👇",
                fontSize = 15.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.secondary,
                modifier = Modifier.padding(vertical = 8.dp)
            )

            // Grid listing of musical instruments
            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp),
                modifier = Modifier
                    .fillMaxSize()
                    .weight(1f)
            ) {
                itemsIndexed(instruments) { index, instrument ->
                    val isSelected = activeIndex == index
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(95.dp)
                            .clip(RoundedCornerShape(20.dp))
                            .background(
                                if (isSelected) {
                                    Brush.verticalGradient(listOf(Color(0xFF81D4FA), Color(0xFF29B6F6)))
                                } else {
                                    Brush.verticalGradient(listOf(Color.White, Color(0xFFE1F5FE)))
                                }
                            )
                            .clickable {
                                activeIndex = index
                                viewModel.speak(instrument.nameEn, instrument.nameHi)
                                playSynthesizedTone(instrument.soundToneHz)
                            }
                            .testTag("instrument_card_$index"),
                        contentAlignment = Alignment.Center
                    ) {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Text(text = instrument.emoji, fontSize = 32.sp)
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = if (isHindi) instrument.nameHi else instrument.nameEn,
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Bold,
                                color = if (isSelected) Color.White else Color(0xFF0277BD)
                            )
                        }
                    }
                }
            }
        }
    }
}
