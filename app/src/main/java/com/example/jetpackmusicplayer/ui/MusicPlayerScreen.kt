import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun MusicPlayerScreen(
    isPlaying: Boolean,
    currentPosition: Int,
    duration: Int,
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
        Slider(
            value = currentPosition.toFloat(),
            onValueChange = { onSeek(it.toInt()) },
            valueRange = 0f..duration.toFloat(),
            modifier = Modifier.fillMaxWidth(),
        )
        if (isPlaying) {
            Button(onClick = { onPause() }, modifier = Modifier.fillMaxWidth()) { Text("Pause") }
        } else {
            Button(onClick = { onResume() }, modifier = Modifier.fillMaxWidth()) { Text("Resume") }
        }
    }
}