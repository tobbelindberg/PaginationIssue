package com.paginationissue.ui.list

import com.paginationissue.paging.PageDataHolder
import com.paginationissue.paging.PageNumberTokenStrategy
import com.paginationissue.paging.Pager
import com.paginationissue.repo.ListRepo
import com.paginationissue.ui.list.state.*
import com.paginationissue.ui.state.PartialState
import com.paginationissue.ui.state.StateReducer
import com.paginationissue.util.Optional
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.PublishSubject

class PaginationIssueInteractor {


    private val mRefresh = PublishSubject.create<Optional<Unit>>()
    private val pager: Pager<PartialState<PaginationIssueState>, Int>

    private val repo = ListRepo()

    init {
        pager = Pager.create(
            INITIAL_PAGE + 1,
            PageNumberTokenStrategy<PartialState<PaginationIssueState>, String> { page ->

                (page as? PageDataHolder<String>)?.let {
                    it.pageData
                }
            },
            ::nextPageObservable
        )
    }

    fun stateObservable(): Observable<PaginationIssueState> {
        return Observable.merge(
            listOf(
                firstPage(),
                nextPage(),
                refresh()
            )
        )
            .scan(PaginationIssueState.initialState(), StateReducer::reduce)
    }

    fun onNextPage() {
        pager.next()
    }

    fun onRefresh() {
        mRefresh.onNext(Optional.empty())
    }

    private fun firstPage(): Observable<PartialState<PaginationIssueState>> {
        return repo.getPage(INITIAL_PAGE).firstOrError().toObservable()
            .subscribeOn(Schedulers.io())
            .map<PartialState<PaginationIssueState>> { FirstPageLoaded(it) }
            .startWith(FirstPageLoading())
    }

    private fun refresh(): Observable<PartialState<PaginationIssueState>> {
        return mRefresh.flatMap { _ ->
            repo.getPage(INITIAL_PAGE).firstOrError().toObservable()
                .subscribeOn(Schedulers.io())
                .doOnNext { pager.resetTo(INITIAL_PAGE + 1) }
                .map<PartialState<PaginationIssueState>> { RefreshLoaded(it) }
                .startWith(RefreshLoading())
        }
    }

    private fun nextPage(): Observable<PartialState<PaginationIssueState>> {
        return pager.observable
    }

    private fun nextPageObservable(page: Int): Observable<PartialState<PaginationIssueState>> {
        return repo.getPage(page).firstOrError().toObservable()
            .subscribeOn(Schedulers.io())
            .map<PartialState<PaginationIssueState>> { NextPageLoaded(it) }
            .startWith { NextPageLoading() }
    }

    companion object {
        private const val INITIAL_PAGE: Int = 1
    }
}