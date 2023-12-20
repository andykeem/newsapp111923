package loc.example.newsapp111923.model

import android.content.Intent
import android.view.View
import loc.example.newsapp111923.ui.ArticleDetailActivity
import loc.example.newsapp111923.ui.ArticleDetailActivity.Companion.ARTICLE_URL

data class Article(
    val author: String,
    val title: String,
    val description: String,
    val url: String,
    val urlToImage: String,
    val publishedAt: String,
    val content: String
) {
    fun onClick(view: View) {
        with(Intent(view.context, ArticleDetailActivity::class.java)) {
            putExtra(ARTICLE_URL, url)
            view.context.startActivity(this)
        }
    }
}