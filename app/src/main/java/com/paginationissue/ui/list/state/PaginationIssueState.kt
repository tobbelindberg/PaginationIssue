package com.paginationissue.ui.list.state

import com.paginationissue.ui.state.State

data class PaginationIssueState(
    var loadingFirstPage: Boolean,
    var loadingRefresh: Boolean,
    var loadingNextPage: Boolean,
    var list: List<String>?
) : State {

    companion object {

        fun initialState(): PaginationIssueState {
            return PaginationIssueState(false, false, false, null)
        }
    }
}