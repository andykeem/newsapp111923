package loc.example.newsapp111923.service

import loc.example.newsapp111923.model.EverythingResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsService {
    //    https://newsapi.org/v2/everything?q=tesla&from=2023-10-19&sortBy=publishedAt&apiKey=987aa97c056746e6856b61f06abfc951
    @GET(value = "everything")
    suspend fun fetchArticles(
        @Query("q") q: String = "tesla",
        @Query("from") from: String = "2023-10-19",
        @Query("sortBy") sortBy: String = "published",
        @Query("apiKey") apiKey: String = "987aa97c056746e6856b61f06abfc951"
    ): EverythingResponse
}