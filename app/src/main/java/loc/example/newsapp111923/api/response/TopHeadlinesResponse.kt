package loc.example.newsapp111923.api.response

import loc.example.newsapp111923.model.Article

data class TopHeadlinesResponse(
    val status: String,
    val totalResults: Int,
    val articles: List<Article>
)
