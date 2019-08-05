package com.lyhoangvinh.simple.data.entities

import org.json.JSONObject
import retrofit2.HttpException
import java.io.IOException

class ErrorEntity {
    private var message = ""
    private var httpCode = 0

    constructor()
    constructor(message: String, httpCode: Int) {
        this.message = message
        this.httpCode = httpCode
    }

    fun setHttpCode(httpCode: Int) {
        this.httpCode = httpCode
    }

    fun setMessage(message: String) {
        this.message = message
    }

    fun getMessage() = message

    companion object {
        val HTTP_ERROR_CODE_UNAUTHORIZED = 401
        val OOPS = "Oops! please try again"
        val NETWORK_UNAVAILABLE = "Network problem!"
        val ERROR_UNAUTHORIZED = "Error! Please re-login!"

        fun getError(throwable: Throwable?): ErrorEntity {
            val e = ErrorEntity()
            if (throwable is HttpException) {
                val httpException = throwable as HttpException?
                if (httpException!!.code() == HTTP_ERROR_CODE_UNAUTHORIZED) {
                    e.setHttpCode(HTTP_ERROR_CODE_UNAUTHORIZED)
                    e.setMessage(ERROR_UNAUTHORIZED)
                } else {
                    // get the body fail
                    val baseResponse = httpException.response()
                    e.setHttpCode(baseResponse.code())
                    if (!baseResponse.isSuccessful && baseResponse.errorBody() != null) {
                        try {
                            val jObjError = JSONObject(baseResponse.errorBody()!!.string())
                            e.setMessage(jObjError.getString("message"))
                        } catch (eXX: Exception) {
                            eXX.printStackTrace()
                            e.setMessage("Error")
                        }

                    }
                }
            } else if (throwable is IOException) {
                e.setMessage(NETWORK_UNAVAILABLE)
            } else {
                e.setMessage(OOPS)
            }
            return e
        }

        fun getError(reason: String?): ErrorEntity {
            return if (reason != null) {
                ErrorEntity(reason, 0)
            } else {
                ErrorEntity(OOPS, 0)
            }
        }

        fun errorOops(): ErrorEntity = ErrorEntity(OOPS, 0)
    }
}


