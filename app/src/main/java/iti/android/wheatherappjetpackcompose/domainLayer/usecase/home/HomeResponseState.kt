package iti.android.wheatherappjetpackcompose.domainLayer.usecase.home

sealed class HomeResponseState<T> {
    class OnNoLocationDetected<T> : HomeResponseState<T>()
    class OnSuccess<T>(var data: T) : HomeResponseState<T>()
    class OnError<T>(var message: String) : HomeResponseState<T>()
    class OnLoading<T> : HomeResponseState<T>()
    class OnNothingData<T> : HomeResponseState<T>()
}
