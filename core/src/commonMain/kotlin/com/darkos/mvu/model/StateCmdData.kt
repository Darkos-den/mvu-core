package com.darkos.mvu.model

data class StateCmdData<T: MVUState>(
    val state: T,
    val effect: Effect
)