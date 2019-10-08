package com.paginationissue.paging;


import java.io.Serializable;

public final class TokenProvider<T extends Serializable> {

    private T token;

    TokenProvider(TokenProviderRestoreState<T> tokenProviderRestoreState) {
        token = tokenProviderRestoreState.token();
    }

    TokenProvider(T token) {
        this.token = token;
    }

    void setToken(T token) {
        this.token = token;
    }

    boolean hasNext() {
        return token != null;
    }

    T nextPageToken() {
        return token;
    }

    TokenProviderRestoreState<T> restoredState() {
        return TokenProviderRestoreState.create(token);
    }
}
