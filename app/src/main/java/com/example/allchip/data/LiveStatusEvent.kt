package com.example.allchip.data


class LiveStatusEvent<out T>(val status: Status, val data: T?, val message: String?) {


    companion object {
        const val UNKNOW_ERROR = "unknow"
        fun <T> success(data: T?): LiveStatusEvent<T> {
            return LiveStatusEvent(Status.SUCCESS, data, null)
        }

        fun <T> error(msg: String? = UNKNOW_ERROR, data: T? = null): LiveStatusEvent<T> {
            return LiveStatusEvent(Status.ERROR, data, msg)
        }


        fun <T> loading(data: T? = null): LiveStatusEvent<T> {
            return LiveStatusEvent(Status.LOADING, data, null)
        }
    }

    fun isSucces(): Boolean {
        return status == Status.SUCCESS
    }

    fun isError(): Boolean {
        return status == Status.ERROR
    }


    fun isLoading():Boolean {
        return status == Status.LOADING
    }

    fun <T> isSucces(block: () -> T) {
        if (status == Status.SUCCESS) {
            block.invoke()
        }
    }

    fun <T> isError(block: () -> T) {
        if (status == Status.ERROR) {
            block.invoke()
        }
    }

    fun <T> isLoading(block: () -> T) {
        if (status == Status.LOADING) {
            block.invoke()
        }
    }



    enum class Status {
        SUCCESS,
        ERROR,
        LOADING
    }
}



