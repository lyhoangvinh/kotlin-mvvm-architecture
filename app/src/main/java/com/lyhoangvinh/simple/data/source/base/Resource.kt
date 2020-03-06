package com.lyhoangvinh.simple.data.source.base

import com.lyhoangvinh.simple.data.entities.State
import com.lyhoangvinh.simple.data.entities.Status
import com.lyhoangvinh.simple.data.entities.Status.*

/**
 * A generic class that holds a value with its loading status.
 * @param <T>
</T> */

data class Resource<T>(val status: Status, val data: T?, val message: String?) {
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
            return Resource(Status.SUCCESS, data, null)
        }

        fun <T> error(message: String, data: T?): Resource<T> {
            return Resource(Status.ERROR, data, message)
        }

        fun <T> loading(): Resource<T> {
            return Resource(Status.LOADING, null, null)
        }
    }
}
