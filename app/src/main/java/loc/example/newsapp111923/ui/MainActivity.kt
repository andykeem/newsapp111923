package loc.example.newsapp111923.ui

import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.doOnTextChanged
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.DividerItemDecoration
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import loc.example.newsapp111923.databinding.ActivityMainBinding
import loc.example.newsapp111923.ext.hide
import loc.example.newsapp111923.ext.show
import loc.example.newsapp111923.model.UiState
import loc.example.newsapp111923.viewmodel.NewsViewModel
import loc.example.newsapp111923.widget.ArticleListAdapter

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val viewModel by viewModels<NewsViewModel>()
    private val listAdapter by lazy { ArticleListAdapter() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_main)
        binding = ActivityMainBinding.inflate(layoutInflater).also {
            setContentView(it.root)
        }
        binding.listView.apply {
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
                viewModel.uiState.collect {
                    Log.d(TAG, "onCreate - articles: $it")
                    when (it) {
                        is UiState.Loading -> binding.progressBar.show()
                        is UiState.Success -> {
                            binding.progressBar.hide()
                            listAdapter.submitList(it.data)
                        }

                        is UiState.Failure -> {
                            binding.progressBar.hide()
                            Log.e(TAG, "Failed fetch articles: ${it.e.message}", it.e)
                        }
                    }
                }
            }
        }
        binding.search.doOnTextChanged { text, start, before, count ->
            viewModel.onTextChange(text, start, before, count)
        }
    }
}