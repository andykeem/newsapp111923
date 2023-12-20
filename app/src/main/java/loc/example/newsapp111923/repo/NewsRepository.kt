package loc.example.newsapp111923.repo

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.datetime.Clock
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import loc.example.newsapp111923.model.Article
import loc.example.newsapp111923.service.NewsService
import javax.inject.Inject

interface NewsRepository {
    fun fetchArticles(): Flow<Result<List<Article>>>
    fun searchArticles(term: String): Flow<Result<List<Article>>>
}

class NewsRepositoryImpl @Inject constructor(
    private val service: NewsService
) : NewsRepository {
    override fun fetchArticles(): Flow<Result<List<Article>>> {
        return flow {
            val result = try {
                val articles = service.topHeadlines(country = "us")
                    .articles
                Result.success(value = articles)
            } catch (e: Exception) {
                Result.failure(exception = e)
            }
            emit(result)
        }.flowOn(Dispatchers.IO)
    }

    override fun searchArticles(term: String): Flow<Result<List<Article>>> {
        val now = Clock.System.now().toLocalDateTime(TimeZone.UTC)
//        val from = "${now.year}-${now.monthNumber}-${now.dayOfMonth.dec()}"
        return flow {
            val result = try {
                val resp = service.everything(q = term) // , from = from)
                Result.success(value = resp.articles)
            } catch (e: Exception) {
                Result.failure(exception = e)
            }
            emit(result)
        }.flowOn(Dispatchers.IO)
    }
}