package loc.example.newsapp111923.model

import androidx.annotation.StringRes
import loc.example.newsapp111923.R

sealed class UiState<out T> {
    data class Failure(val e: Throwable) : UiState<Nothing>()
    data class Loading(@StringRes val id: Int = R.string.loading) : UiState<Nothing>()
    data class Success<T>(val data: T) : UiState<T>()
}