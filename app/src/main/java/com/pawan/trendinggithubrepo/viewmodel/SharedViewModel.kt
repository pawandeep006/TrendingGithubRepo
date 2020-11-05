package com.pawan.trendinggithubrepo.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.pawan.trendinggithubrepo.source.NetworkRepo
import com.pawan.trendinggithubrepo.source.network.model.Item
import com.pawan.trendinggithubrepo.utils.log
import javax.inject.Inject

class SharedViewModel @Inject constructor(private val networkRepo: NetworkRepo) : ViewModel() {

    var selectedItem: Item? = null
    lateinit var repoListLiveData: LiveData<List<Item>>
    val errorLiveData: MutableLiveData<String> = MutableLiveData()

    init {
        fetchData()
    }

    private fun fetchData() {
        repoListLiveData = liveData {
            try {
                emit(networkRepo.fetchRepoWithQuery("android", "stars"))
            } catch (e: Exception) {
                e.message?.log()
                errorLiveData.value = e.message
            }
        }
    }
}