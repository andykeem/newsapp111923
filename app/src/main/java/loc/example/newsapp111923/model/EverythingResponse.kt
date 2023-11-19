package loc.example.newsapp111923.model

data class EverythingResponse(
    val status: String,
    val totalResults: Int,
    val articles: List<Article>
)
