package com.paginationissue.paging

import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.PublishSubject
import io.reactivex.subjects.Subject
import java.io.Serializable

/**
 * The Pager aims to simplify accessing paginated data sources. The pager is operated by a data
 * source observable and a paging function for page tokens.
 *
 * @param <I> type of the data that the pager requests
 * @param <P> type of the paging token
</P></I> */
class Pager<I, P : Serializable> private constructor(private val tokenProvider: TokenProvider<P>,
                                                     private val tokenStrategy: TokenStrategy<P, I>,
                                                     obtainFunction: (P) -> Observable<I>) {

    /**
     * Returns the data observable of the pager that clients are to subscribe to. The paginated data
     * from the obtain function provided via constructor is delivered here.
     */

    private val pages: Subject<P> = PublishSubject.create<P>().toSerialized()
    val observable: Observable<I> = pages.hide()
        .observeOn(Schedulers.io())
        .filter { !inFlight }
        .doOnNext { inFlight = true }
        .flatMap(obtainFunction)
        .doOnNext(::onNextPage)


    private var previousPage: I? = null
    private var inFlight: Boolean = false

    /**
     * Returns the last delivered token. This can be used to save the current state of the pager.
     *
     * Note that the last delivered token is `null` when the Pager has reached the end.
     *
     * See also [.resetTo]
     */
    val pageToken: P?
        get() = tokenProvider.nextPageToken()


    /**
     * Returns the state used to restore this pager's token provider.
     */
    val restoreState: TokenProviderRestoreState<P>
        get() = tokenProvider.restoredState()

    /**
     * Requests next page from the pager. The next page token is delivered to the paging function
     * provided in the constructor.
     *
     *
     * A great time to invoke this function is when the list of data has reached its end and next
     * page of data is required.
     */
    fun next() {
        if (pages.hasObservers() && tokenProvider.hasNext()) {
            val nextToken = tokenProvider.nextPageToken()
            if (!inFlight) {
                pages.onNext(nextToken)
            }
        }
    }

    /**
     * Resets the pager to the given token.
     *
     *
     * Use to restart the pager from the previous state it was left in, or from the beginning
     *
     * @param token the next token delivered to the paging function
     */
    fun resetTo(token: P?) {
        if (token !== tokenProvider.nextPageToken()) {
            tokenProvider.setToken(token)
            previousPage = null
        }
        inFlight = false
    }

    private fun onNextPage(page: I) {
        val newToken =
            tokenStrategy.generateNextPageToken(tokenProvider.nextPageToken(), previousPage, page)
        if (newToken !== tokenProvider.nextPageToken()) {
            tokenProvider.setToken(newToken)
            inFlight = false
            previousPage = page
        }
    }

    companion object {

        /**
         * Create new Pager with a static first page.
         *
         * @param firstPage      the first page token that the pager should use for paging. Note that
         * the very first page of data is usually not requested via the pager, so
         * the initial token should represent the second page of data.
         * @param tokenStrategy  the token strategy that is used for generating next page tokens
         * @param obtainFunction function that is to request data based on the paging token provided
         * by the pager
         * @param <T>            type of the pager data
         * @param <O>            type of the paging token
        </O></T> */
        fun <T, O : Serializable> create(firstPage: O, tokenStrategy: TokenStrategy<O, T>,
                                         obtainFunction: (O) -> Observable<T>): Pager<T, O> {
            return Pager(TokenProvider(firstPage), tokenStrategy, obtainFunction)
        }

        /**
         * Create new Pager with a page token provider.
         *
         * @param tokenProvider  the token provider that the pager should use for paging. Note that the
         * very first page of data is usually not requested via the pager, so the
         * initial token should represent the second page of data.
         * @param tokenStrategy  the token strategy that is used for generating next page tokens
         * @param obtainFunction function that is to request data based on the paging token provided
         * by the pager
         * @param <T>            type of the pager data
         * @param <O>            type of the paging token
        </O></T> */
        fun <T, O : Serializable> create(tokenProvider: TokenProvider<O>,
                                         tokenStrategy: TokenStrategy<O, T>,
                                         obtainFunction: (O) -> Observable<T>): Pager<T, O> {
            return Pager(tokenProvider, tokenStrategy, obtainFunction)
        }
    }

}