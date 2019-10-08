package com.paginationissue.ui.state;


public class StateReducer {

    public static <T extends State, U extends PartialState<T>> T reduce(T previousState,
            U partialState) {
        return partialState.reduceState(previousState);
    }

}
