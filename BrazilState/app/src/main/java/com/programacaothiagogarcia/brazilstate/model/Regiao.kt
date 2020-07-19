package com.programacaothiagogarcia.brazilstate.model


import com.google.gson.annotations.SerializedName

data class Regiao(
    @SerializedName("id")
    val id: Int? = null,
    @SerializedName("nome")
    val nome: String,
    @SerializedName("sigla")
    val sigla: String? = null
)