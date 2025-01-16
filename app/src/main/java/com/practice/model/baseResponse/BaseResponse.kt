package com.practice.model.baseResponse

data class BaseResponse<T>(
    val body: T?,
    val status: Int?,
    val message: String?
)
