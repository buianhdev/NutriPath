package vn.anhbt.nutripath.presentation.onboarding.firstopen

import vn.anhbt.nutripath.core.mvi.BaseViewModel

// MVI ViewModel for the onboarding welcome screen.
// NOTE: skeleton only — implement the intent handling / business logic yourself.
class FirstOpenViewModel : BaseViewModel<FirstOpenState, FirstOpenIntent, FirstOpenEvent>(
    initialState = FirstOpenState(),
) {
    override fun onIntent(intent: FirstOpenIntent) {
        // TODO: handle intents
    }
}
