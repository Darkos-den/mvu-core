package com.darkos.mvu

import com.darkos.mvu.model.MVUState

interface Component<T : MVUState> {
    fun render(state: T)
}
