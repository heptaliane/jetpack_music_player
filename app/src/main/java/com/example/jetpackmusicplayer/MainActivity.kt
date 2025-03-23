package com.example.jetpackmusicplayer

import MusicPlayer
import MusicPlayerScreen
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import java.io.File

class MainActivity : ComponentActivity() {
    private var player: MusicPlayer? = null
    private var musicFilePath: String = "example.mp3"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        player = MusicPlayer()
        setContent {
            val isPlaying by remember { derivedStateOf { player?.isPlaying() == true } }
            MusicPlayerScreen(
                isPlaying = isPlaying,
                onResume = { resumeMusic() },
                onPause = { pauseMusic() }
            )
        }
    }

    private fun playMusic() {
        val file = File(musicFilePath)
        player?.play(file)
    }

    private fun stopMusic() {
        player?.stop()
    }

    private fun pauseMusic() {
        player?.pause()
    }

    private fun resumeMusic() {
        player?.resume()
    }
}