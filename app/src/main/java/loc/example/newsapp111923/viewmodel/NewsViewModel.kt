package loc.example.newsapp111923.viewmodel

import android.content.ContentValues.TAG
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import loc.example.newsapp111923.model.Article
import loc.example.newsapp111923.model.UiState
import loc.example.newsapp111923.repo.NewsRepository
import javax.inject.Inject
import kotlin.time.Duration.Companion.seconds

@OptIn(FlowPreview::class, ExperimentalCoroutinesApi::class)
@HiltViewModel
class NewsViewModel @Inject constructor(
    private val newsRepo: NewsRepository
) : ViewModel() {
    private val _uiState =
        MutableStateFlow<UiState<List<Article>>>(UiState.Loading())
    val uiState: StateFlow<UiState<List<Article>>> = _uiState.asStateFlow()
    private val searchTerm = MutableStateFlow<String>("")

    init {
        viewModelScope.launch {
            _uiState.value = UiState.Loading()
            newsRepo.fetchArticles().collect {
                it.onSuccess {
                    _uiState.value = UiState.Success(data = it)
                }
                it.onFailure {
                    _uiState.value = UiState.Failure(e = it)
                }
            }
        }

        viewModelScope.launch {
            searchTerm.debounce(1.seconds.inWholeMilliseconds)
                .filter { it.isNotBlank() }
                .onStart { UiState.Loading() }
                .flatMapLatest {
                    Log.d(TAG, "term: $it")
                    newsRepo.searchArticles(it)
                }.collect { result ->
                    result.onFailure {
                        UiState.Failure(e = it)
                    }
                    result.onSuccess { articles ->
                        _uiState.update {
                            UiState.Success(articles)
                        }
                    }
                }
        }
    }

    fun onTextChange(text: CharSequence?, start: Int, before: Int, count: Int) {
        Log.d(TAG, "onTextChange - text: $text")
        searchTerm.tryEmit(text.toString())
    }
}

