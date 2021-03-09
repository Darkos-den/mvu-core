package com.darkos.mvu.model

data class StateCmdData<T: MVUState>(
    val state: T,
    val effect: Effect
)

infix fun <T : MVUState> T.andEffect(cmd: Effect) =
    StateCmdData(state = this, effect = cmd)

infix fun <T : MVUState> StateCmdData<T>.replaceEffect(cmd: Effect): StateCmdData<T> {
    return StateCmdData(
        state = this.state,
        effect = cmd
    )
}

infix fun <T : MVUState> StateCmdData<out MVUState>.replaceState(state: T): StateCmdData<T> {
    return StateCmdData(
        state = state,
        effect = this.effect
    )
}