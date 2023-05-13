package com.hho.circleoflife.viewmodels

import androidx.lifecycle.*
import com.hho.circleoflife.models.UserInfoResponseModel
import com.hho.circleoflife.repository.Repository
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

class MainViewModel(
    private val repository: Repository
): ViewModel() {

    private val _newData = MutableLiveData<UserInfoResponseModel>()
    val newData:LiveData<UserInfoResponseModel> get() = _newData

    fun getUserInfo() = viewModelScope.launch {
        repository.getUserInfo()
            .onEach {
                _newData.value = it
            }
            .catch {
                _newData.value = UserInfoResponseModel(
                    message = "${it.message}"
                )
            }
            .launchIn(viewModelScope)
    }

    @Suppress("UNCHECKED_CAST")
    class Factory(
        private val repository: Repository
    ): ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return MainViewModel(repository) as T
        }
    }

}