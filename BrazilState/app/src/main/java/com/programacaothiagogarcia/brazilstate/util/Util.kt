package com.programacaothiagogarcia.brazilstate.util

import android.view.View
import android.widget.ImageView
import androidx.databinding.BindingAdapter


@BindingAdapter("android:set_image")
fun setImage(imageView: ImageView, name: String) {
    val image = "com.programacaothiagogarcia.brazilstate:mipmap/$name".toLowerCase()
    val draw = imageView.context.resources.getIdentifier(image, "mipmap", null)
    imageView.setImageResource(draw)
}

@BindingAdapter("android:show_widget")
fun hiddenWidget(view: View, show: Boolean) {
    view.visibility = if (show) {
        View.VISIBLE
    } else {
        View.GONE
    }
}