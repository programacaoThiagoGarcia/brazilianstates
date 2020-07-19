package com.programacaothiagogarcia.brazilstate.network

import com.programacaothiagogarcia.brazilstate.model.State
import retrofit2.Response
import retrofit2.http.GET

interface Connect {
    @GET("estados")
    suspend fun getStates() : Response<List<State>>
}