package com.hho.circleoflife.ui.auth

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.hho.circleoflife.models.response.UserInfoResponseModel
import com.hho.circleoflife.repository.Repository
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch


class AuthViewModel(
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
            return AuthViewModel(repository) as T
        }
    }

}