package es.christianbegines.wonkamanagement.helpers

import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy


fun ImageView.loadUrl(url: String) {
    Glide.with(this)
        .load(url)
        .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
        .circleCrop()
        .into(this)
}
