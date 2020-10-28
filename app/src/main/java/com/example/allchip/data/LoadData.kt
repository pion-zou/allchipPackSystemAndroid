package com.example.allchip.data

import androidx.annotation.IntDef


@Target(AnnotationTarget.FIELD, AnnotationTarget.VALUE_PARAMETER)
@Retention(AnnotationRetention.SOURCE)
annotation class LoadStatus {

    companion object {
        const val INIT: Int = 1101
        const val REFRESH: Int = 1102
        const val LOADER_MORE: Int = 1103
    }
}


data class LoadData<T>(@LoadStatus val status: Int, val pageIndex: Int = 1, val data: T? = null) {
    companion object {
        fun <T> success(@LoadStatus status: Int, data: T?): LoadData<T> {
            return LoadData(status = status, data = data)
        }

        fun <T> success(@LoadStatus status: Int, pageIndex: Int, data: T?): LoadData<T> {
            return LoadData(status, pageIndex, data)
        }

        fun <T> error(@LoadStatus status: Int): LoadData<T> {
            return LoadData(status)
        }

    }

}


