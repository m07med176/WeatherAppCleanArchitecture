package iti.android.wheatherappjetpackcompose.dataLayer.repository

import android.content.Context
import iti.android.wheatherappjetpackcompose.dataLayer.source.local.ILocalDataSource
import iti.android.wheatherappjetpackcompose.dataLayer.source.remote.IRemoteDataSource

interface RepositoryInterface : ILocalDataSource, IRemoteDataSource {
    val context: Context
}