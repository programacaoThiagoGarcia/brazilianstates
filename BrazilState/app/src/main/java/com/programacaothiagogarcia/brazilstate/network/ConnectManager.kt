package com.programacaothiagogarcia.brazilstate.network


import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

private const val BASE_URL = "https://servicodados.ibge.gov.br/api/v1/localidades/"
object ConnectManager {
    private val retrofit  : Retrofit by lazy {
        Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build() }


        fun connectService() = retrofit.create(Connect::class.java)
    }

