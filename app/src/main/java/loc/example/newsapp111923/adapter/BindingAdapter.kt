package loc.example.newsapp111923.adapter

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import coil.load
import loc.example.newsapp111923.R

object BindingAdapter {

    @JvmStatic
    @BindingAdapter("imgUrl")
    fun loadImg(view: ImageView?, url: String?) {
        if (url.isNullOrBlank()) return
        view?.load(url) {
            crossfade(true)
            placeholder(R.drawable.ic_place_holder)
//            transformations(CircleCropTransformation())
        }
    }
}