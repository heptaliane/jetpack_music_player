package com.example.jetpackmusicplayer

import MusicPlayerScreen
import android.media.MediaPlayer
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import kotlinx.coroutines.delay
import java.io.File

class MainActivity : ComponentActivity() {
    private var player: MediaPlayer? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        startMusic()

        setContent {
            val isPlaying by remember { derivedStateOf { player?.isPlaying == true } }
            val currentPosition = remember { mutableIntStateOf(0) }
            val duration = remember { mutableIntStateOf(100) }

            LaunchedEffect(isPlaying) {
                while (isPlaying) {
                    currentPosition.intValue = player?.currentPosition ?: 0
                    duration.intValue = player?.duration ?: 100
                    delay(1000)
                }
            }

            MusicPlayerScreen(
                isPlaying = isPlaying,
                duration = duration.intValue,
                currentPosition = currentPosition.intValue,
                songTitle = "title",
                artistName = "artist",
                albumName = "album",
                onResume = { resumeMusic() },
                onPause = { pauseMusic() },
                onSeek = { position ->
                    player?.seekTo(position)
                    currentPosition.intValue = position
                }
            )
        }
    }

    private fun startMusic() {
        stopMusic()
        val musicFile = File(getExternalFilesDir(null), "sample.mp3")
        if (musicFile.exists()) {
            player = MediaPlayer().apply {
                setDataSource(musicFile.absolutePath)
                prepare()
                start()
            }
        }
    }

    private fun stopMusic() {
        player?.release()
        player = null
    }

    private fun resumeMusic() {
        player?.start()
    }

    private fun pauseMusic() {
        player?.pause()
    }
}