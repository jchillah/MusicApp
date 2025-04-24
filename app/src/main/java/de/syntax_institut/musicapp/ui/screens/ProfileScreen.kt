package de.syntax_institut.musicapp.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import de.syntax_institut.musicapp.ui.theme.DeepBlack
import de.syntax_institut.musicapp.ui.theme.Gold
import de.syntax_institut.musicapp.ui.theme.LightGreen
import de.syntax_institut.musicapp.ui.theme.MusicAppTheme

@Composable
fun ProfileScreen(
    isDarkTheme: Boolean,
    onToggleTheme: () -> Unit,
    onBackClick: () -> Unit
) {
    val accentColor = if (isDarkTheme) Gold else LightGreen
    val backgroundColor = DeepBlack


    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp)
            .background(backgroundColor),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Button(
            onClick = onBackClick,
            colors = ButtonDefaults.buttonColors(
                containerColor = accentColor,
                contentColor = Color.Black
            )
        ) {
            Text("Zurück")
        }

        Spacer(modifier = Modifier.height(24.dp))

        AsyncImage(
            model = "https://via.placeholder.com/150",
            contentDescription = "Profilbild",
            modifier = Modifier
                .size(120.dp)
                .clip(CircleShape),
            contentScale = ContentScale.Crop
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            "Max Mustermann",
            style = MaterialTheme.typography.headlineMedium,
            color = accentColor
        )
        Text(
            "max@beispiel.de",
            style = MaterialTheme.typography.bodyMedium,
            color = accentColor
        )

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = onBackClick,
            colors = ButtonDefaults.buttonColors(
                containerColor = accentColor,
                contentColor = Color.Black
            )
        ) {
            Text("Abmelden", style = MaterialTheme.typography.labelLarge)
        }

        Spacer(modifier = Modifier.height(16.dp))

        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                "Dark Mode",
                style = MaterialTheme.typography.bodyMedium,
                color = accentColor
            )
            Switch(
                checked = isDarkTheme,
                onCheckedChange = { onToggleTheme() },
                colors = SwitchDefaults.colors(
                    checkedThumbColor = accentColor,
                    uncheckedThumbColor = accentColor,
                    checkedTrackColor = accentColor.copy(alpha = 0.5f),
                    uncheckedTrackColor = accentColor.copy(alpha = 0.5f)
                )
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ProfileScreenPreview() {
    MusicAppTheme(darkTheme = false) {
        ProfileScreen(
            isDarkTheme = false,
            onToggleTheme = {},
            onBackClick = {}
        )
    }
}
