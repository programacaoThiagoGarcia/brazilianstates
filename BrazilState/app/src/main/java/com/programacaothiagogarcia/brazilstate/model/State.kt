package com.programacaothiagogarcia.brazilstate.model


import com.google.gson.annotations.SerializedName

data class State(
    @SerializedName("id")
    val id: Int? = null,
    @SerializedName("nome")
    val nome: String? = null,
    @SerializedName("regiao")
    val regiao: Regiao,
    @SerializedName("sigla")
    val sigla: String? = null
)