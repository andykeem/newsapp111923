package loc.example.newsapp111923.di

import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import loc.example.newsapp111923.repo.NewsRepository
import loc.example.newsapp111923.repo.NewsRepositoryImpl
import loc.example.newsapp111923.service.NewsService
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@InstallIn(value = [SingletonComponent::class])
@Module
abstract class AppModule {
    companion object {
        //        https://newsapi.org/v2/everything?q=tesla&from=2023-10-19&sortBy=publishedAt&apiKey=987aa97c056746e6856b61f06abfc951
        private const val BASE_URL = "https://newsapi.org/v2/"

        @Provides
        fun provideLoggingInterceptor(): Interceptor =
            HttpLoggingInterceptor()
                .setLevel(HttpLoggingInterceptor.Level.BODY)

        @Provides
        fun provideOkHttpClient(interceptor: Interceptor) =
            OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .build()

        @Provides
        fun provideNewsService(client: OkHttpClient) =
            Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build()
                .create(NewsService::class.java)
    }

    @Singleton
    @Binds
    abstract fun bindNewsRepository(impl: NewsRepositoryImpl): NewsRepository
}