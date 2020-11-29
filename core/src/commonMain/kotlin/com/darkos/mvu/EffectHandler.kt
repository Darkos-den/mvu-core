package com.darkos.mvu

import com.darkos.mvu.model.Effect
import com.darkos.mvu.model.Message
import com.darkos.mvu.model.flow.FlowEffect
import com.darkos.mvu.model.flow.FlowMessage
import kotlinx.coroutines.flow.Flow

interface EffectHandler {
    suspend fun call(effect: Effect): Message
    suspend fun call(effect: FlowEffect): Flow<FlowMessage>
}