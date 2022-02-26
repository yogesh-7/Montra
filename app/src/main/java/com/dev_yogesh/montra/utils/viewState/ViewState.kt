package com.dev_yogesh.montra.utils.viewState

import com.dev_yogesh.montra.model.Transaction


sealed class ViewState {
    object Loading : ViewState()
    object Empty : ViewState()
    data class Success(val transaction: List<Transaction>) : ViewState()
    data class Error(val exception: Throwable) : ViewState()
}
