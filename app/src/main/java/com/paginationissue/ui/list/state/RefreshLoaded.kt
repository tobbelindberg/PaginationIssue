package com.paginationissue.ui.list.state

import com.paginationissue.ui.state.PartialState

class RefreshLoaded(val newList: List<String>) : PartialState<PaginationIssueState> {

    override fun reduceState(previousState: PaginationIssueState): PaginationIssueState {
        return previousState.copy(list = newList, loadingRefresh = false)
    }
}