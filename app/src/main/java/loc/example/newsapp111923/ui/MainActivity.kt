package loc.example.newsapp111923.ui

import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.DividerItemDecoration
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import loc.example.newsapp111923.databinding.ActivityMainBinding
import loc.example.newsapp111923.viewmodel.NewsViewModel
import loc.example.newsapp111923.widget.ArticleListAdapter

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var bind: ActivityMainBinding
    private val model by viewModels<NewsViewModel>()
    private val listAdapter by lazy { ArticleListAdapter() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_main)
        bind = ActivityMainBinding.inflate(layoutInflater).also {
            setContentView(it.root)
        }
        bind.listView.apply {
            adapter = listAdapter
            addItemDecoration(
                DividerItemDecoration(
                    this@MainActivity,
                    DividerItemDecoration.VERTICAL
                )
            )
        }
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                model.articles.collect {
                    Log.d(TAG, "onCreate - articles: $it")
                    listAdapter.submitList(it)
                }
            }
        }
    }
}