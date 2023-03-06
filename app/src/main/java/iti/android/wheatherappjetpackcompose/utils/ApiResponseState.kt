package iti.android.wheatherappjetpackcompose.utils

sealed class ApiResponseState<T> {
    class OnSuccess<T>(var data: T) : ApiResponseState<T>()
    class OnError<T>(var message: String) : ApiResponseState<T>()
    class OnLoading<T> : ApiResponseState<T>()
}
