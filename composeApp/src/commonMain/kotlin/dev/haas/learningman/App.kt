package dev.haas.learningman

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import dev.icerock.moko.mvvm.compose.getViewModel
import dev.icerock.moko.mvvm.compose.viewModelFactory
import dev.icerock.moko.mvvm.viewmodel.ViewModel
import io.kamel.image.KamelImage
import io.kamel.image.asyncPainterResource
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
@Preview
fun App() {
    MaterialTheme {
        val viewModel = getViewModel(
            Unit,
            viewModelFactory { ImageViewModel() }
        )
        ImagesPage(viewModel)
    }
}

@Composable
fun ImagesPage(viewModel: ImageViewModel) {
    val uiState by viewModel.uiState.collectAsState()
    println("${uiState.images}")
    AnimatedVisibility(visible = uiState.images.isNotEmpty()) {
        LazyVerticalGrid(
            columns = GridCells.Fixed(3),
            horizontalArrangement = Arrangement.spacedBy(5.dp),
            verticalArrangement = Arrangement.spacedBy(5.dp),
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 5.dp),
            contentPadding = PaddingValues(5.dp)
        ) {
            items(uiState.images) { image ->
                ImageCell(image)
            }
        }
    }
}

@Composable
fun ImageCell(image: Imagemodel) {
    KamelImage(
        resource = { asyncPainterResource("https://sebi.io/demo-image-api/${image.path}") },
        contentDescription = "${image.category} by ${image.author}",
        modifier = Modifier
            .fillMaxWidth()
            .aspectRatio(1f),
        contentScale = ContentScale.Crop
    )
}
