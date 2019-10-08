package com.paginationissue.paging

/**
 * Token strategy that uses page numbers as pagination tokens.
 * @param <T> type of the token
 * @param <E> type of the list item of the paginated data
 *
 * Create new [PageNumberTokenStrategy] with pageDataFunction for extracting the paginated
 * data list from the data.
 *
</E></T> */
class PageNumberTokenStrategy<T, E>(private val pageDataFunction: (T) -> List<E>?) :
    TokenStrategy<Int, T> {


    override fun generateNextPageToken(currentPageToken: Int,
                                              previousPage: T,
                                              currentPage: T): Int? {
        var nextPageToken = currentPageToken

        val previousPageData = pageDataFunction(previousPage)
        val pageData = pageDataFunction(currentPage)

        return pageData?.let {
            if(previousPageData == null || PagingUtil.canPaginateToNextPage(previousPageData, pageData)){
                ++nextPageToken
            } else {
                null
            }
        } ?: nextPageToken
    }

}
