package com.tattoodo.app.bindings

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.paginationissue.paging.PaginationScrollListener

@BindingAdapter("onScrollListener")
fun RecyclerView.setOnScrollListener(oldScrollListener: PaginationScrollListener?,
                                     newScrollListener: PaginationScrollListener?) {
    oldScrollListener?.also { oldScrollListener ->
        removeOnScrollListener(oldScrollListener)
    }

    newScrollListener?.also { newScrollListener ->
        addOnScrollListener(newScrollListener)
    }

}