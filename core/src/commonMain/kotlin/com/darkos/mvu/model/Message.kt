package com.darkos.mvu.model

abstract class Message

object Idle: Message()

object ComponentInitialized : Message()

data class RestoreState<T : MVUState>(
    val state: T
) : Message()