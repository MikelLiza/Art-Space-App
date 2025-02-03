package com.example.artspaceapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.gestures.detectHorizontalDragGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.artspaceapp.ui.theme.ArtSpaceAppTheme
import kotlin.math.absoluteValue

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ArtSpaceAppTheme {
                ArtSpaceApp()
            }
        }
    }
}

@Composable
fun ArtSpaceApp(modifier: Modifier = Modifier) {
    var currentArtworkIndex by remember { mutableStateOf(0) }
    var dragAmount by remember { mutableStateOf(0f) }
    val artworks = listOf(
        Artwork(R.drawable._09118, "Joker", "We live in A society"),
        Artwork(R.drawable._9434ea4f3cf83feef354139f4b1eca1, "Waltah", "waltah white"),
        Artwork(R.drawable.artorias, "Knight Artorias", "The abysswalker"),
        Artwork(R.drawable.leonsandwich, "Leon", "Good job leon, you did it"),
        Artwork(R.drawable.lucas, "Lucas", "")
    )
    val currentArtwork = artworks[currentArtworkIndex]

    Scaffold(
        modifier = modifier
            .fillMaxSize()
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Box(
                modifier = Modifier
                    .padding(16.dp)
                    .weight(1f)
                    .pointerInput(Unit) {
                        detectHorizontalDragGestures(
                            onDragEnd = {
                                if (dragAmount.absoluteValue > 50) {
                                    if (dragAmount > 0) {
                                        currentArtworkIndex =
                                            (currentArtworkIndex - 1).takeIf { it >= 0 }
                                                ?: (artworks.size - 1)
                                    } else {
                                        currentArtworkIndex = (currentArtworkIndex + 1) % artworks.size
                                    }
                                }
                                dragAmount = 0f
                            }
                        ) { change, drag ->
                            dragAmount += drag
                        }
                    }
            ) {
                Image(
                    painter = painterResource(id = currentArtwork.imageRes),
                    contentDescription = stringResource(R.string.artwork_content_description),
                    modifier = Modifier.align(Alignment.Center)
                )
            }
            Spacer(modifier = Modifier.height(16.dp))
            Column(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = currentArtwork.title,
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.Bold,
                    fontSize = 24.sp
                )
                Text(
                    text = currentArtwork.artist,
                    style = MaterialTheme.typography.bodyMedium,
                    fontSize = 16.sp
                )
            }
            Spacer(modifier = Modifier.height(16.dp))
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalArrangement = Arrangement.SpaceAround
            ) {
                Button(
                    onClick = {
                        currentArtworkIndex =
                            (currentArtworkIndex - 1).takeIf { it >= 0 } ?: (artworks.size - 1)
                    },
                    modifier = Modifier.size(150.dp, 50.dp)
                ) {
                    Text(stringResource(R.string.previous_button))
                }
                Button(
                    onClick = {
                        currentArtworkIndex = (currentArtworkIndex + 1) % artworks.size
                    },
                    modifier = Modifier.size(150.dp, 50.dp)
                ) {
                    Text(stringResource(R.string.next_button))
                }
            }
        }
    }
}

data class Artwork(val imageRes: Int, val title: String, val artist: String)

@Preview(showBackground = true)
@Composable
fun ArtSpaceAppPreview() {
    ArtSpaceAppTheme {
        ArtSpaceApp()
    }
}