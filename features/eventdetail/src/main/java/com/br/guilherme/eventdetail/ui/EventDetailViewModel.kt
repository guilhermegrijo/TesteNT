package com.br.guilherme.eventdetail.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.br.guilherme.core.base.DataResult
import com.br.guilherme.domain.eventDetail.usecase.LoadEventUseCase
import com.br.guilherme.eventdetail.model.EventItem
import com.br.guilherme.eventdetail.model.toView
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EventDetailViewModel @Inject constructor(
    private val loadEventUseCase: LoadEventUseCase,
) : ViewModel() {
    val state: LiveData<ScreenState<EventItem>>
        get() = _state
    private val _state =
        MutableLiveData<ScreenState<EventItem>>()


    internal fun loadEvent(id: String) {


        viewModelScope.launch {
            _state.postValue(ScreenState.Loading)
            loadEventUseCase.invoke(id).catch {_state.postValue(ScreenState.Error(it)) }.collect {
                when (it) {
                    is DataResult.OnSuccess -> _state.postValue(ScreenState.Success(it.response.toView()))
                    is DataResult.OnFailed -> _state.postValue(ScreenState.Error(it.throwable))
                    else -> _state.postValue(ScreenState.Error(Exception()))
                }
            }
        }


    }
}