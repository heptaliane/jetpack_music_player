package com.example.jetpackmusicplayer

import LoopMode
import MusicMetadataRetriever
import MusicPlayerScreen
import android.media.MediaPlayer
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import com.example.jetpackmusicplayer.data.MusicMetadata
import kotlinx.coroutines.delay
import java.io.File

class MainActivity : ComponentActivity() {
    private var player: MediaPlayer? = null
    private val retriever = MusicMetadataRetriever()
    private var currentMusicMetadata: MusicMetadata? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        startMusic()

        setContent {
            val isPlaying = remember { mutableStateOf(false) }
            val currentPosition = remember { mutableIntStateOf(0) }
            val duration = remember { mutableIntStateOf(100) }
            val loopMode = remember { mutableStateOf(LoopMode.NONE) }

            LaunchedEffect("position") {
                while (true) {
                    if (isPlaying.value) {
                        currentPosition.intValue = player?.currentPosition ?: 0
                        duration.intValue = player?.duration ?: 0
                    }
                    delay(500)
                }
            }

            LaunchedEffect("isPlaying") {
                while (true) {
                    player?.let {
                        if (isPlaying.value != it.isPlaying) {
                            isPlaying.value = it.isPlaying
                        }
                    }
                    delay(50)
                }
            }

            MusicPlayerScreen(
                isPlaying = isPlaying.value,
                duration = duration.intValue,
                currentPosition = currentPosition.intValue,
                songTitle = currentMusicMetadata?.title ?: currentMusicMetadata?.filename ?: "",
                artistName = currentMusicMetadata?.artist,
                albumName = currentMusicMetadata?.album,
                loopMode = loopMode.value,
                onResume = { resumeMusic() },
                onPause = { pauseMusic() },
                onSeek = { position ->
                    player?.seekTo(position)
                    player?.start()
                    currentPosition.intValue = position
                },
                onLoopModeChange = {
                    loopMode.value = when (loopMode.value) {
                        LoopMode.NONE -> LoopMode.ONE
                        LoopMode.ONE -> LoopMode.ALL
                        LoopMode.ALL -> LoopMode.NONE
                    }
                    player?.isLooping = loopMode.value == LoopMode.ONE
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
            currentMusicMetadata = retriever.getMetadata(musicFile)
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