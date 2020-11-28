package com.darkos.mvu.model.flow

import com.darkos.mvu.model.Message

abstract class FlowMessage(val isFinal: Boolean) : Message()