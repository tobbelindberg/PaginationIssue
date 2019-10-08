package com.paginationissue.ui.list.state

import com.paginationissue.ui.state.PartialState

class NextPageLoading: PartialState<PaginationIssueState> {
    override fun reduceState(previousState: PaginationIssueState): PaginationIssueState {
        return previousState.copy(loadingNextPage = true)
    }

}