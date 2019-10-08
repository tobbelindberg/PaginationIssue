package com.paginationissue.paging;

import androidx.annotation.Nullable;

import java.util.Collection;
import java.util.List;

public class PagingUtil {

    /**
     * Returns true if it is possible to paginate to the next page.
     */
    public static boolean canPaginateToNextPage(List previousPage, List page) {
        if (isEmpty(page))
            return false;

        if (previousPage == null && isNotEmpty(page))
            return true;

        return !PagingUtil.isSamePageData(previousPage, page);
    }

    /**
     * Returns true if the data is page-wise equal.
     *
     * <p>Does not traverse the entire data to check equality, but only critical points.</p>
     */
    public static boolean isSamePageData(List data1, List data2) {
        int size = data1.size();
        if (size != data2.size()) {
            return false;
        } else if (size == 0) {
            return true;
        } else {
            //check both ends to support ascending and descending ordering
            return data1.get(0).equals(data2.get(0))
                    && data1.get(size - 1).equals(data2.get(size - 1));
        }
    }

    /**
     * Returns true if collection is either null or empty, otherwise false
     */
    public static boolean isEmpty(@Nullable Collection collection) {
        return collection == null || collection.isEmpty();
    }

    /**
     * Returns false if collection is either null or empty, otherwise true
     */
    public static boolean isNotEmpty(@Nullable Collection collection) {
        return !isEmpty(collection);
    }

}
