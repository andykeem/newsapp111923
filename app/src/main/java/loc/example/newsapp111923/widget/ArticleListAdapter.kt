package loc.example.newsapp111923.widget

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import loc.example.newsapp111923.databinding.ListItemArticleBinding
import loc.example.newsapp111923.model.Article

class ArticleListAdapter :
    ListAdapter<Article, ArticleViewHolder>(object : DiffUtil.ItemCallback<Article>() {
        override fun areItemsTheSame(oldItem: Article, newItem: Article): Boolean {
            return oldItem.author == newItem.author && oldItem.title == newItem.title
        }

        override fun areContentsTheSame(oldItem: Article, newItem: Article): Boolean {
            return oldItem == newItem
        }
    }) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticleViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return ListItemArticleBinding.inflate(inflater, parent, false).run {
            ArticleViewHolder(this)
        }
    }

    override fun onBindViewHolder(holder: ArticleViewHolder, position: Int) {
        holder.onBind(getItem(position))
    }
}
