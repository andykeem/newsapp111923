package loc.example.newsapp111923.widget

import androidx.recyclerview.widget.RecyclerView.ViewHolder
import loc.example.newsapp111923.BR
import loc.example.newsapp111923.databinding.ListItemArticleBinding
import loc.example.newsapp111923.model.Article

class ArticleViewHolder(private val bind: ListItemArticleBinding) : ViewHolder(bind.root) {
    fun onBind(item: Article) {
        bind.setVariable(BR.model, item)
    }
}
