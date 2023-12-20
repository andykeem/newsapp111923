package loc.example.newsapp111923.service

import loc.example.newsapp111923.api.response.EverythingResponse
import loc.example.newsapp111923.api.response.TopHeadlinesResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsService {
    //    GET https://newsapi.org/v2/everything?q=tesla&from=2023-10-19&sortBy=publishedAt&apiKey=123456789
    @GET(value = "everything")
    suspend fun everything(
        @Query("q") q: String = "tesla",
//        @Query("from") from: String = "2023-10-19",
//        @Query("sortBy") sortBy: String = "published"
    ): EverythingResponse

    //    GET https://newsapi.org/v2/top-headlines?country=us&apiKey=123456789
    @GET(value = "top-headlines")
    suspend fun topHeadlines(
        @Query("country") country: String = "us"
    ): TopHeadlinesResponse
}