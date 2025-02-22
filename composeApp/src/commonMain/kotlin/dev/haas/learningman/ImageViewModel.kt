package dev.haas.learningman

import dev.icerock.moko.mvvm.viewmodel.ViewModel
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.get
import io.ktor.client.statement.bodyAsText
import io.ktor.serialization.kotlinx.json.json
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.serialization.json.Json

data class ImageUiState(val images: List<Imagemodel> = emptyList())
class ImageViewModel: ViewModel() {
    private val _uiState = MutableStateFlow<ImageUiState>(ImageUiState())
    public val uiState = _uiState.asStateFlow()
    private val httpClient= HttpClient{
        install(ContentNegotiation) {
            json()
        }
    }

    init{
        updateImages()
    }

    override fun onCleared() {
        httpClient.close()
    }
    fun updateImages(){
        viewModelScope.launch{
            val images=getImages()
            _uiState.update{
                it.copy(images = images)
            }
            println("Updated images: $images")
        }
    }
    suspend fun getImages(): List<Imagemodel> {
        return try {
            val responseBody = httpClient.get("https://sebi.io/demo-image-api/pictures.json").bodyAsText()
            // Deserialize the JSON response manually using the Json object
            val images = Json.decodeFromString<List<Imagemodel>>(responseBody)
            images
        } catch (e: Exception) {
            e.printStackTrace() // Log the error
            emptyList() // Return an empty list in case of an error
        }
    }

}