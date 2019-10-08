package com.paginationissue.util

import java.util.*

class Optional<T>(private val value: T?) {

    private constructor() : this(null)

    fun get(): T {
        if (value == null) {
            throw NoSuchElementException("No value present")
        }
        return value
    }

    fun isEmpty(): Boolean {
        return value != null
    }

    companion object {
        fun <T> empty(): Optional<T> {
            return Optional()
        }
    }
}