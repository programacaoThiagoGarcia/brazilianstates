package com.programacaothiagogarcia.brazilstate.ui.viewmodel


import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.programacaothiagogarcia.brazilstate.network.Resource
import com.programacaothiagogarcia.brazilstate.repository.ProjectRepository
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {
    private var currentJob : Job? = null
    private var repository : ProjectRepository = ProjectRepository()
    init {
        getStatesFromRepository()
    }

    private fun getStatesFromRepository(){
        currentJob?.cancel()
        currentJob = viewModelScope.launch {

            val resp = repository.getStatesForFilter("Norte")
            val filter = repository.getFilter()
            when(resp){
                is Resource.Success ->{
                    Log.d("DEBUG:","")
                }
                is Resource.Error -> {
                    Log.d("DEBUG:","")
                }
            }
        }


    }
}