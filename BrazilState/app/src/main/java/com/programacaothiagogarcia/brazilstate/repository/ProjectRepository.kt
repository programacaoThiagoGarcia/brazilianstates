package com.programacaothiagogarcia.brazilstate.repository


import com.programacaothiagogarcia.brazilstate.model.State
import com.programacaothiagogarcia.brazilstate.network.Connect
import com.programacaothiagogarcia.brazilstate.network.ConnectManager
import com.programacaothiagogarcia.brazilstate.network.Resource
import kotlinx.coroutines.*


class ProjectRepository {
    private val mConnect: Connect = ConnectManager.connectService()

    /*
    * ViewModel faz a chamada para obter a lista de estados
    * passando um atributo como filtro, sendo ele null ou uma região.
    * Quando ele vem nulo ele devolve a lista completa de estados.
    * */
    suspend fun getStatesForFilter(filter: String?): Resource<List<State>>? {
        val data = sortedData()
        return when (filter) {
            null -> data.resp
            else -> {
                Resource.Success(data.filterList?.getOrElse(filter) { emptyList() })
            }
        }
    }

    suspend fun getFilter(): List<String>? = sortedData().filter

    /*
    * Métodos privados para conexão com o serviço e filtro da lista
    * */

    private var inProgressSort: Deferred<SortedData>? = null
    private suspend fun sortedData(): SortedData = withContext(Dispatchers.Main) {
        inProgressSort?.await() ?: doSortedData()
    }

    private suspend fun doSortedData(): SortedData {
        val result = coroutineScope {
            val deferred = async { SortedData.from(getStates()) }
            inProgressSort = deferred
            deferred.await()
        }
        return result
    }

    /*
    * Método que faz a chamada para o serviço e vai trazer a lista
    * Transforma no objeto Resource que pode ter Success ou Erro
    * */
    private suspend fun getStates(): Resource<List<State>> {
        return withContext(Dispatchers.IO) {
            val response = async<Resource<List<State>>> {
                val resp = mConnect.getStates()
                if (resp.isSuccessful) {
                    Resource.Success(resp.body())
                } else {
                    Resource.Error("ERRO")
                }
            }
            response.await()
        }
    }

    private class SortedData private constructor(
        val filter: List<String>?,
        val resp: Resource<List<State>>,
        val filterList: Map<String, List<State>>?
    ) {
        companion object {
            suspend fun from(response: Resource<List<State>>): SortedData {
                return withContext(Dispatchers.Default) {
                    when (response) {
                        is Resource.Success -> {
                            val filter: List<String> =
                                response.data?.map { it.regiao.nome }!!.distinctBy { it }
                            val regionPlace: Map<String, List<State>> =
                                response.data.groupBy { it.regiao.nome }
                            SortedData(filter, response, regionPlace)
                        }
                        is Resource.Error -> {
                            val filter: List<String>? = null
                            SortedData(filter, response, null)
                        }
                    }

                }
            }

        }
    }


}