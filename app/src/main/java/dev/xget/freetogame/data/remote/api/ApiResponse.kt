package dev.xget.freetogame.data.remote.api

sealed class ApiResponse<T> (val data : T? = null,val message : String? =null) {
    class Success<T>(data: T?) : ApiResponse<T>(data = data)
    class Error<T>(message: String?, e: Exception? = null) : ApiResponse<T>(message = message)

    class Loading<T> : ApiResponse<T>()
}