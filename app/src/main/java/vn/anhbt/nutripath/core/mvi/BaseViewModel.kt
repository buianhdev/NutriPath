package vn.anhbt.nutripath.core.mvi

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

abstract class BaseViewModel<S: UIState, I: MviIntent, E: MviEvent>(
    initialState: S
): ViewModel() {

    private val _state = MutableStateFlow<S>(initialState)
    val state = _state.asStateFlow()

    private val _event = Channel<E>(Channel.BUFFERED)
    val event = _event.receiveAsFlow()

    val currentState: S = _state.value

    fun updateState(reducer: S.() -> S) {
        _state.update { currentState ->
            currentState.reducer()
        }
    }

    fun setState(newState: S) {
        _state.value = newState
    }

    fun dispatchIntent(intent: I) {
        onIntent(intent)
    }

    protected abstract fun onIntent(intent: I)

    protected fun sendEvent(event: E) {
        viewModelScope.launch {
            _event.send(event)
        }
    }

}