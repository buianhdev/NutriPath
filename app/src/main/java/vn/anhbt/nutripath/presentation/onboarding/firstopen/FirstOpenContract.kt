package vn.anhbt.nutripath.presentation.onboarding.firstopen

import vn.anhbt.nutripath.core.mvi.MviEvent
import vn.anhbt.nutripath.core.mvi.MviIntent
import vn.anhbt.nutripath.core.mvi.UIState

// MVI contract for the onboarding welcome screen.
// NOTE: skeleton only — fill in the state fields, intents, and events when implementing the VM.

// TODO: define the welcome screen UI state.
class FirstOpenState : UIState

// TODO: define user intents (e.g. ContinueClicked).
sealed interface FirstOpenIntent : MviIntent

// TODO: define one-shot events (e.g. NavigateToPlanning).
sealed interface FirstOpenEvent : MviEvent
