package com.paginationissue.ui.list.state

import com.paginationissue.ui.state.PartialState

class RefreshLoading: PartialState<PaginationIssueState> {
    override fun reduceState(previousState: PaginationIssueState): PaginationIssueState {
        return previousState.copy(loadingRefresh = true)
    }

}