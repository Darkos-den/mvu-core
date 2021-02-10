package com.darkos.mvu.model

data class StateCmdData<T: MVUState>(
    val state: T,
    val effect: Effect
)

infix fun <T : MVUState> T.andEffect(cmd: Effect) =
    StateCmdData(state = this, effect = cmd)