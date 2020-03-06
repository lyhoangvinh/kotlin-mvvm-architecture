package com.lyhoangvinh.simple.data.entities

data class State(var status: Status, var message: String?) {
    companion object {
        fun loading() = State(Status.LOADING, null)
        fun success() = State(Status.SUCCESS, null)
        fun error(message: String? = null) = State(Status.ERROR, message)
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) {
            return true
        }
        if (other == null || javaClass != other.javaClass) {
            return false
        }

        val state = other as State?

        if (status != state?.status) {
            return false
        }

        return if (message != null) message == state.message else state.message == null
    }

    override fun hashCode(): Int {
        val result = status.hashCode()
        return 31 * result + if (message != null) message!!.hashCode() else 0
    }

    override fun toString(): String {
        return "status: $status, message: $message"
    }
}
