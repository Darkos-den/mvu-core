package com.darkos.mvu

import com.darkos.mvu.model.MVUState
import com.darkos.mvu.model.Message
import com.darkos.mvu.model.StateCmdData

interface Reducer<T : MVUState> {
    fun update(state: T, message: Message): StateCmdData<T>
}