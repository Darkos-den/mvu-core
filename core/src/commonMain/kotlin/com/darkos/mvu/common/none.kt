package com.darkos.mvu.common

import com.darkos.mvu.model.MVUState
import com.darkos.mvu.model.None
import com.darkos.mvu.model.StateCmdData

fun <T: MVUState> T.none() = StateCmdData(this, None)