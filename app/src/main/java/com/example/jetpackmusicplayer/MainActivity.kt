package com.example.jetpackmusicplayer

import MusicPlayer
import MusicPlayerScreen
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import java.io.File

class MainActivity : ComponentActivity() {
    private var player: MusicPlayer? = null
    private var musicFilePath: String = "example.mp3"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        player = MusicPlayer()
        setContent {
            MusicPlayerScreen(
                onPlay = { playMusic() },
                onStop = { stopMusic() }
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
}