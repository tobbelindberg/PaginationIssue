package com.paginationissue.ui.list.state

import com.paginationissue.paging.PageDataHolder
import com.paginationissue.ui.state.PartialState

class NextPageLoaded(override val pageData: List<String>) : PartialState<PaginationIssueState>,
    PageDataHolder<String> {

    override fun reduceState(previousState: PaginationIssueState): PaginationIssueState {
        return previousState.copy(list = pageData, loadingNextPage = false)
    }
}