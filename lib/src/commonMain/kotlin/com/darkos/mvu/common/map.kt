package com.darkos.mvu.common

import com.darkos.mvu.model.MVUState
import com.darkos.mvu.model.StateCmdData

fun <T : MVUState, U : MVUState> StateCmdData<T>.map(mapper: (T) -> U): StateCmdData<U> {
    return StateCmdData(
        state = mapper(state),
        effect = effect
    )
}