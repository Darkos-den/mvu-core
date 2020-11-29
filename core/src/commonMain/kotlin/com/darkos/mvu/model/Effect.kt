package com.darkos.mvu.model

abstract class Effect

interface ScopedEffect {
    val scope: Any
}

object None : Effect()