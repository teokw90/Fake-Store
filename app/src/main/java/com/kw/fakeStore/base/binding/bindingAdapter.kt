package com.kw.fakeStore.base.binding

import android.view.View
import androidx.databinding.BindingAdapter
import com.android.volley.toolbox.NetworkImageView
import com.kw.fakeStore.utils.ImageRequester

@BindingAdapter("imageForUrl")
fun NetworkImageView.setImageFromUrl(url: String) {
    ImageRequester.setImageFromUrl(this, url)
}

@BindingAdapter("visibilityBasedOnBoolean")
fun View.setVisibilityBasedOnBoolean(visibleNeeded: Boolean) {
    visibility = when(visibleNeeded) {
        true -> View.VISIBLE
        false -> View.GONE
    }
}