package com.example.jetpackmusicplayer

import MusicPlayerScreen
import android.media.MediaPlayer
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember

class MainActivity : ComponentActivity() {
    private var player: MediaPlayer? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            val isPlaying by remember { derivedStateOf { player?.isPlaying == true } }
            val currentPosition by remember { mutableIntStateOf(0) }
            val duration by remember { mutableIntStateOf(100) }

            MusicPlayerScreen(
                isPlaying = isPlaying,
                duration = duration,
                currentPosition = currentPosition,
                onResume = { resumeMusic() },
                onPause = { pauseMusic() },
                onSeek = { position ->
                    player?.seekTo(position)
                }
            )
        }
    }

    private fun resumeMusic() {
        player?.start()
    }

    private fun pauseMusic() {
        player?.pause()
    }
}