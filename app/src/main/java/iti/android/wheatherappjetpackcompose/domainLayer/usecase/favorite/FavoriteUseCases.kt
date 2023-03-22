package iti.android.wheatherappjetpackcompose.domainLayer.usecase.favorite

data class FavoriteUseCases(
    val deleteFavorite: DeleteFavoriteUseCase,
    val insertFavorite: InsertFavoriteUseCase,
    val getFavoritesUseCase: GetFavoritesUseCase,
    val getSettingsUseCase: GetSettingsUseCase,

    )
