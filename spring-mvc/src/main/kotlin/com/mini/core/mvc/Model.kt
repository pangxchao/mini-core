@file:JvmName("ModelKt")
@file:Suppress("unused")

package com.mini.core.mvc

import com.mini.core.mvc.model.JsonModel
import com.mini.core.mvc.model.PageModel
import com.mini.core.mvc.model.StreamModel


fun page(init: PageModel.() -> PageModel): PageModel {
    return PageModel().apply {
        init(this)
    }
}

fun json(init: JsonModel.() -> JsonModel): JsonModel {
    return JsonModel().apply {
        init(this)
    }
}

fun stream(init: StreamModel.() -> StreamModel): StreamModel {
    return StreamModel().apply {
        init(this)
    }
}
