package com.paginationissue.ui.list.state

import com.paginationissue.ui.state.PartialState

class FirstPageLoading: PartialState<PaginationIssueState> {
    override fun reduceState(previousState: PaginationIssueState): PaginationIssueState {
        return previousState.copy(loadingFirstPage = true)
    }
}