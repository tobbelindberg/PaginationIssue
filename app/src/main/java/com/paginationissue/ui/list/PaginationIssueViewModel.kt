package com.paginationissue.ui.list

import android.util.Log
import androidx.databinding.ObservableBoolean
import androidx.lifecycle.ViewModel
import androidx.recyclerview.widget.DiffUtil
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.paginationissue.BR
import com.paginationissue.R
import com.paginationissue.ui.list.state.PaginationIssueState
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import me.tatarka.bindingcollectionadapter2.ItemBinding
import me.tatarka.bindingcollectionadapter2.collections.DiffObservableList

class PaginationIssueViewModel : ViewModel() {
    private val subscriptions = CompositeDisposable()
    private val interactor = PaginationIssueInteractor()

    val loadingFirstPageVisible = ObservableBoolean(true)

    val refreshVisible = ObservableBoolean(false)

    val loadingNextPageVisible = ObservableBoolean(false)

    val onSwipeToRefresh = SwipeRefreshLayout.OnRefreshListener {
        interactor.onRefresh()
    }

    val items =
        DiffObservableList<TextItemViewModel>(object : DiffUtil.ItemCallback<TextItemViewModel>() {
            override fun areItemsTheSame(
                oldItem: TextItemViewModel,
                newItem: TextItemViewModel
            ): Boolean {
                return oldItem.hashCode() == newItem.hashCode()
            }

            override fun areContentsTheSame(
                oldItem: TextItemViewModel,
                newItem: TextItemViewModel
            ): Boolean {
                return oldItem.text == newItem.text
            }
        })

    val itemBinding = ItemBinding.of<TextItemViewModel>(BR.viewModel, R.layout.item_text)

    fun initStateObservable() {
        interactor.stateObservable()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy(onNext = ::render, onError = { e ->
                Log.e(PaginationIssueViewModel::class.java.simpleName, "Error", e)
            })
            .addTo(subscriptions)
    }


    private fun render(state: PaginationIssueState) {

        state.list?.also {
            val newList = state.list?.map { text ->
                TextItemViewModel(text)
            } ?: listOf()
            items.update(newList)
        }

        loadingFirstPageVisible.set(state.loadingFirstPage)
        loadingNextPageVisible.set(state.loadingNextPage)


        refreshVisible.set(state.loadingRefresh)
    }

    internal fun onLoadMore() {
        interactor.onNextPage()
    }

    override fun onCleared() {
        super.onCleared()
        subscriptions.dispose()
    }
}
