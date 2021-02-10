package com.darkos.mvu

import com.darkos.mvu.model.Effect
import com.darkos.mvu.model.Message
import com.darkos.mvu.model.flow.FlowEffect
import kotlinx.coroutines.flow.Flow

interface EffectHandler {
    suspend fun call(effect: Effect): Message
    suspend fun<T> callAsFlow(effect: T) : Flow<Message> where
            T: Effect,
            T: FlowEffect
}

interface NotFlowableEffectHandler: EffectHandler {

    override suspend fun <T> callAsFlow(effect: T): Flow<Message> where T : Effect, T : FlowEffect {
        throw UnsupportedOperationException()
    }
}