package com.example.foodapp.hilt

import android.content.Context
import androidx.room.Room
import com.example.foodapp.database.DataBase
import com.example.foodapp.repository.ApiRepo
import com.example.foodapp.repository.ApiRepository
import com.example.foodapp.repository.DbRepo
import com.example.foodapp.repository.DbRepository
import com.example.foodapp.service.FoodApi
import com.example.foodapp.util.Constants.BASE_URL
import com.example.foodapp.util.base.NetworkHelper
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object HiltModule {

    @Provides
    @Singleton
    fun provideRetrofit() : FoodApi = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(FoodApi::class.java)

    @Provides
    @Singleton
    fun provideApiRepo(api: FoodApi, networkHelper: NetworkHelper ) : ApiRepository = ApiRepo(api, networkHelper)

    @Provides
    @Singleton
    fun provideDbRepo(dao : com.example.foodapp.database.Dao) : DbRepository = DbRepo(dao)

    @Provides
    @Singleton
    fun provideDataBase(@ApplicationContext context : Context) =
        Room.databaseBuilder(context, DataBase::class.java, "FoodApp").build()

    @Provides
    @Singleton
    fun provideDao(dataBase: DataBase) = dataBase.getDao()

    @Provides
    @Singleton
    fun provideNetworkHelper(@ApplicationContext context: Context) = NetworkHelper(context)

}