package com.dev_yogesh.montra.utils.viewState

import com.dev_yogesh.montra.model.Transaction


sealed class DetailState {
    object Loading : DetailState()
    object Empty : DetailState()
    data class Success(val transaction: Transaction) : DetailState()
    data class Error(val exception: Throwable) : DetailState()
}
