package com.br.guilherme.checkin.ui

import android.util.Patterns
import androidx.core.util.PatternsCompat
import androidx.lifecycle.*
import com.br.guilherme.core.base.DataResult
import com.br.guilherme.domain.checkin.MakeCheckInUseCase
import com.br.guilherme.domain.model.CheckInModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class CheckInViewModel @Inject constructor(private val makeCheckInUseCase: MakeCheckInUseCase) :
    ViewModel() {

    val email = MutableLiveData("")
    val name = MutableLiveData("")

    val valid = MediatorLiveData<Boolean>()

    val state: LiveData<ScreenState>
        get() = _state
    private val _state =
        MutableLiveData<ScreenState>()



    init {
        valid.addSource(email) {
            valid.value  = isEmailValid(it) && name.value?.let { it1 -> isNameValid(it1) } ?: false
        }

        valid.addSource(name) {
            valid.value = isNameValid(it) && email.value?.let { it1 -> isEmailValid(it1) } ?: false
        }
    }


    fun makeCheckIn(eventId : String) {

        val register = CheckInModel(eventId, name.value!!, email.value!!)


        viewModelScope.launch {
            _state.postValue(ScreenState.Loading)
            makeCheckInUseCase.invoke(register).catch {
                _state.postValue(ScreenState.Error(it))
            }.collect {
                when(it){
                    is DataResult.OnSuccess -> _state.postValue(ScreenState.Success)
                    is DataResult.OnFailed -> _state.postValue(ScreenState.Error(it.throwable))
                }
            }
        }
    }

    private fun isEmailValid(emailAddress: String): Boolean {
        return PatternsCompat.EMAIL_ADDRESS.matcher(emailAddress).matches()
    }

    private fun isNameValid(name: String): Boolean {
        return name.isNotEmpty()
    }

}