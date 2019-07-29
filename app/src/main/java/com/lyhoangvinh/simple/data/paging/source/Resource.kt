package com.lyhoangvinh.simple.data.paging.source

import com.lyhoangvinh.simple.data.paging.source.Status.*

/**
 * A generic class that holds a value with its loading status.
 * @param <T>
</T> */

class Resource<T>(status: Status, val data: T?, message: String?) {

      val state: State = State(status, message)

    override fun equals(other: Any?): Boolean {
        if (this === other) {
            return true
        }
        if (other == null || javaClass != other.javaClass) {
            return false
        }

        val resource: Resource<*>? = other as Resource<*>?

        return if (resource?.state == this.state && data != null) data == resource.data else resource?.data == null
    }

    override fun hashCode(): Int {
        var result = state.hashCode()
        result = 31 * result + (data?.hashCode() ?: 0)
        return result
    }

    override fun toString(): String {
        return "Resource{" +
                "status=" + state.status +
                ", message='" + state.message + '\''.toString() +
                ", data=" + data +
                '}'.toString()
    }

    companion object {

        fun <T> success(data: T?): Resource<T> {
            return Resource(SUCCESS, data, null)
        }

        fun <T> error(msg: String, data: T?): Resource<T> {
            return Resource(ERROR, data, msg)
        }

        fun <T> loading(data: T?): Resource<T> {
            return Resource(LOADING, data, null)
        }
    }
}
