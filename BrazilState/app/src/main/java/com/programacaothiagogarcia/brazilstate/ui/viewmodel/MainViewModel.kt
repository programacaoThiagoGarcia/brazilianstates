package com.programacaothiagogarcia.brazilstate.ui.viewmodel


import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.programacaothiagogarcia.brazilstate.model.State
import com.programacaothiagogarcia.brazilstate.network.Resource
import com.programacaothiagogarcia.brazilstate.repository.ProjectRepository
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {
    private var currentJob: Job? = null
    private var repository: ProjectRepository = ProjectRepository()
    private var filter = FilterHolder()


    private val mLoading: MutableLiveData<Boolean> by lazy {
        MutableLiveData<Boolean>()
    }
    val loading: LiveData<Boolean>?
        get() = mLoading

    private val mStatesList: MutableLiveData<List<State>> by lazy {
        MutableLiveData<List<State>>()
    }
    val statesList: LiveData<List<State>>?
        get() = mStatesList

    private val mFilterList: MutableLiveData<List<String>> by lazy {
        MutableLiveData<List<String>>()
    }
    val filterList: LiveData<List<String>>?
        get() = mFilterList

    init {
        getStatesFromRepository()
    }

    private fun getStatesFromRepository() {
        currentJob?.cancel()
        currentJob = viewModelScope.launch {
            mLoading.postValue(true)
            val resp = repository.getStatesForFilter(filter.currentValue)
            if (filter.currentValue.isNullOrEmpty()) {
                mFilterList.postValue(repository.getFilter())
            }
            when (resp) {
                is Resource.Success -> {
                    Log.d("DEBUG:", "")
                    mLoading.postValue(false)
                    mStatesList.postValue(resp.data)
                }
                is Resource.Error -> {
                    mLoading.postValue(false)
                    Log.d("DEBUG:", "")
                }
            }

        }

    }

    fun onFilterChanged(filter: String, isChecked: Boolean) {
        this.filter.update(filter, isChecked)
        getStatesFromRepository()

    }

    private class FilterHolder {
        var currentValue: String? = null
            private set

        fun update(changetFilter: String, isChecked: Boolean): Boolean {
            return if (isChecked) {
                currentValue = changetFilter
                true
            } else {
                currentValue = null
                false
            }
        }
    }
}