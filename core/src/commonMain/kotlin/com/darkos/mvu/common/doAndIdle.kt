package com.darkos.mvu.common

import com.darkos.mvu.model.Idle

fun doAndIdle(block: ()->Unit) = block().let { Idle }