package com.paginationissue.repo

import io.reactivex.Observable

class ListRepo {

    val listMap = mapOf<Int, List<String>>(1 to mutableListOf("1", "2", "3", "4", "5", "6", "7", "8", "9", "10"),
        2 to mutableListOf("11", "12", "13", "14", "15", "16", "17", "18", "19", "20"),
        3 to mutableListOf("21", "22", "23", "24", "25", "26", "27", "28", "29", "30"))

    fun getPage(page: Int): Observable<List<String>>{
        return Observable.defer {
            Thread.sleep(1500L) // Working hard
            val list = mutableListOf<String>()
            for(i in 1..page){
                listMap[page]?.also { pageList ->
                    list.addAll(pageList)
                }
            }

            Observable.just(list)
        }
    }
}