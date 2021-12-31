package com.br.guilherme.events.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.br.guilherme.core.CoroutineContextProvider
import com.br.guilherme.core.base.DataResult
import com.br.guilherme.domain.events.usecase.LoadAllEventsUseCase
import com.br.guilherme.events.model.EventItem
import com.br.guilherme.events.model.toView
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext


@HiltViewModel
class EventsViewModel @Inject constructor(private val eventsUseCase: LoadAllEventsUseCase, contextProvider: CoroutineContextProvider) :
    ViewModel() {
    val data: LiveData<ScreenState<List<EventItem>>>
        get() = _state
    private val _state =
        MutableLiveData<ScreenState<List<EventItem>>>()

    private val ioContext: CoroutineContext = (contextProvider.io)

    fun loadEvents() {

        viewModelScope.launch(ioContext) {
            _state.postValue(ScreenState.Loading)

            eventsUseCase.invoke(Unit).catch { _state.postValue(ScreenState.Error(Exception(it))) }
                .collect {

                    when (it) {
                        is DataResult.OnSuccess -> _state.postValue(
                            ScreenState.Success(it.response.map { i -> i.toView() })
                        )
                        is DataResult.OnFailed -> _state.postValue(ScreenState.Error(it.throwable))
                        else -> {
                            _state.postValue(ScreenState.Error(Exception("Error não tratado")))
                        }
                    }
                }
        }
    }
}