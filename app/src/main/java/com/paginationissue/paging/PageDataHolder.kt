package com.paginationissue.paging


/**
 * A simple list wrapper. The list should represent paginated data items.
 *
 * @param <E> type of the list item
</E> */
interface PageDataHolder<E> {

    /**
     * Returns the data list this data holder wraps.
     */
    val pageData: List<E>

}