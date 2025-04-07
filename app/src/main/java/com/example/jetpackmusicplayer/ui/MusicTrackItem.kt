import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.unit.dp
import androidx.core.graphics.createBitmap
import com.example.jetpackmusicplayer.data.MusicMetadata

@Composable
fun MusicTrackItem(
    data: MusicMetadata,
    onClick: (MusicMetadata) -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .clickable { onClick(data) }
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        val defaultArtwork = createBitmap(100, 100)
        val artwork = data.artwork ?: defaultArtwork
        val title = data.title ?: data.filename

        Image(
            bitmap = artwork.asImageBitmap(),
            contentDescription = "Album Artwork",
            modifier = Modifier.size(100.dp)
        )
        Spacer(modifier = Modifier.width(16.dp))
        Column {
            Text(text = title, style = MaterialTheme.typography.titleLarge)
            Text(text = "Artist: ${data.artist}")
            Text(text = "Album: ${data.album}")
            Text(text = "Duration: ${data.duration}")
        }
    }
}