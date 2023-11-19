package loc.example.newsapp111923.repo

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import loc.example.newsapp111923.model.Article
import loc.example.newsapp111923.service.NewsService
import javax.inject.Inject

interface NewsRepository {
    fun fetchArticles(): Flow<List<Article>>
}

class NewsRepositoryImpl @Inject constructor(
    private val service: NewsService
) : NewsRepository {
    override fun fetchArticles(): Flow<List<Article>> {
        return flow {
            val articles = service.fetchArticles()
                .articles
            emit(articles)
        }.flowOn(Dispatchers.IO)
    }
}