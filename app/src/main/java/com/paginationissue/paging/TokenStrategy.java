package com.paginationissue.paging;

import androidx.annotation.Nullable;

/**
 * Token strategy that can be used for generating pagination tokens.
 *
 * @param <T> type of the pagination token
 * @param <P> type of the data that flows through {@link Pager}
 */
public interface TokenStrategy<T, P> {

    /**
     * Implementations should return the next page token or {@code null} to indicate that the last
     * page has been reached and the pager should stop paging. The current page and the previous
     * page are also delivered to the function. These can be used to calculate the next page token
     * and to compare the pages for determining if the end of data has been reached.
     *
     * @param currentPageToken the currently active token that corresponds to data in currentPage
     * @param previousPage     the page returned by the previous token
     * @param currentPage      the page returned by requesting it with currentPageToken
     * @return token for requesting the next page
     */
    @Nullable
    T generateNextPageToken(T currentPageToken, P previousPage, P currentPage);

}
