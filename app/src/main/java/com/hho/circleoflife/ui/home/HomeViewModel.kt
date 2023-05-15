package com.hho.circleoflife.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.hho.circleoflife.models.request.CircleRequestModel
import com.hho.circleoflife.models.response.CircleResponseModel
import com.hho.circleoflife.repository.CircleRepository
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

class HomeViewModel(
    private val repository: CircleRepository
) : ViewModel() {

    private val _newData = MutableLiveData<CircleResponseModel>()
    val newData:LiveData<CircleResponseModel> get() = _newData

    fun getCircle(circleRequestData: CircleRequestModel) = viewModelScope.launch {
        repository.getCircle(circleRequestData)
            .onEach {
                _newData.value = it
            }
            .catch {
                _newData.value = CircleResponseModel(
                    message = "${it.message}"
                )
            }
            .launchIn(viewModelScope)
    }

    @Suppress("UNCHECKED_CAST")
    class Factory(
        private val repository: CircleRepository
    ): ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return HomeViewModel(repository) as T
        }
    }

}