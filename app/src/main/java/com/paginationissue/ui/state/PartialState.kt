package com.paginationissue.ui.state

interface PartialState<T : State> {

    fun reduceState(previousState: T): T

}
