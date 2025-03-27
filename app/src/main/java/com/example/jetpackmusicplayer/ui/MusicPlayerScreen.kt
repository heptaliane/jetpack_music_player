import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@SuppressLint("DefaultLocale")
fun formatTime(ms: Int): String {
    val seconds = ms / 1000
    return String.format("%02d:%02d", seconds / 60, seconds % 60)
}

@Composable
fun MusicPlayerScreen(
    isPlaying: Boolean,
    currentPosition: Int,
    duration: Int,
    songTitle: String,
    albumName: String?,
    artistName: String?,
    onPause: () -> Unit,
    onResume: () -> Unit,
    onSeek: (Int) -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center
    ) {
        Text(songTitle, style = MaterialTheme.typography.titleLarge)
        albumName?.let {
            Text(it, style = MaterialTheme.typography.labelLarge)
        }
        artistName?.let {
            Text(it, style = MaterialTheme.typography.labelLarge)
        }
        Spacer(modifier = Modifier.height(16.dp))
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(formatTime(currentPosition), modifier = Modifier.padding(end = 8.dp))
            Slider(
                value = currentPosition.toFloat(),
                onValueChange = { onSeek(it.toInt()) },
                valueRange = 0f..duration.toFloat(),
                modifier = Modifier.weight(1f),
            )
            Text(formatTime(duration), modifier = Modifier.padding(start = 8.dp))
        }
        if (isPlaying) {
            Button(onClick = { onPause() }, modifier = Modifier.fillMaxWidth()) { Text("Pause") }
        } else {
            Button(onClick = { onResume() }, modifier = Modifier.fillMaxWidth()) { Text("Resume") }
        }
    }
}