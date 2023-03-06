package iti.android.wheatherappjetpackcompose.domainLayer.usecase.home

data class HomeUseCases(
    val deleteHomeUseCase: DeleteHomeUseCase,
    val getHomeUseCase: GetHomeUseCase,
    val getWeatherDetailsUseCase: GetWeatherDetailsUseCase,
    val insertHomeUseCase: InsertHomeUseCase,
)