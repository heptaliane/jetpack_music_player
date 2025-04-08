package com.example.jetpackmusicplayer

import LoopMode
import MusicListScreen
import MusicPlayerScreen
import android.Manifest
import android.content.pm.PackageManager
import android.media.MediaPlayer
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.core.content.ContextCompat
import com.example.jetpackmusicplayer.data.MusicMetadata
import getAllMusicFiles
import kotlinx.coroutines.delay
import retrieveMusicMetadata
import scanMusicDirectory
import java.io.File

class MainActivity : ComponentActivity() {
    private val requestPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted: Boolean ->
            if (isGranted) {
                loadMusicMetadata()
            } else {
                Toast.makeText(this, "Please allow read audio access", Toast.LENGTH_SHORT).show()
            }
        }
    private var player: MediaPlayer? = null
    private var currentMusicMetadata: MusicMetadata? = null
    private var musicData: List<MusicMetadata> = emptyList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        checkRequestPermission()

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

            MusicListScreen(
                musicDataList = musicData,
                onClick = { data ->
                    startMusic(data)
                }
            )
        }
    }

    private fun checkRequestPermission() {
        val permission = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            Manifest.permission.READ_MEDIA_AUDIO
        } else {
            Manifest.permission.READ_EXTERNAL_STORAGE
        }

        when {
            ContextCompat.checkSelfPermission(
                this,
                permission
            ) == PackageManager.PERMISSION_GRANTED -> {
                loadMusicMetadata()
            }

            shouldShowRequestPermissionRationale(permission) -> {
                Toast.makeText(this, "Please allow read audio access", Toast.LENGTH_SHORT).show()
                requestPermissionLauncher.launch(permission)
            }

            else -> {
                requestPermissionLauncher.launch(permission)
            }
        }
    }

    private fun startMusic(music: MusicMetadata) {
        stopMusic()
        val musicFile = File(music.filepath)
        if (musicFile.exists()) {
            player = MediaPlayer().apply {
                setDataSource(musicFile.absolutePath)
                prepare()
                start()
            }
            currentMusicMetadata = retrieveMusicMetadata(this, musicFile)
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

    private fun loadMusicMetadata() {
        scanMusicDirectory(this)
        val files = getAllMusicFiles(this)
        musicData = files.map {
            retrieveMusicMetadata(this, it)
        }
    }
}