import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun MusicPlayerScreen(
    isPlaying: Boolean,
    onPause: () -> Unit,
    onResume: () -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center
    ) {
        if (isPlaying) {
            Button(onClick = { onPause() }, modifier = Modifier.fillMaxWidth()) { Text("Pause") }
        } else {
            Button(onClick = { onResume() }, modifier = Modifier.fillMaxWidth()) { Text("Resume") }
        }
    }
}