package com.darkos.mvu.model

data class StateMessageData<T : MVUState>(
    val state: T,
    val message: Message
)