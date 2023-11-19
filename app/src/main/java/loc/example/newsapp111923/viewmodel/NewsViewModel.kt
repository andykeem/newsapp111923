package loc.example.newsapp111923.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import loc.example.newsapp111923.model.Article
import loc.example.newsapp111923.repo.NewsRepository
import javax.inject.Inject

@HiltViewModel
class NewsViewModel @Inject constructor(
    private val newsRepo: NewsRepository
) : ViewModel() {
    private val _articles = MutableStateFlow(emptyList<Article>())
    val articles: StateFlow<List<Article>> = _articles.asStateFlow()

    init {
        viewModelScope.launch {
            newsRepo.fetchArticles().collect {
                _articles.value = it
            }
        }
    }
}