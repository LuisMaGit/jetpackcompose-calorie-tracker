package com.luisma.core.services.network_service
import com.luisma.core.models.BaseResponse

interface INetworkBaseService {

    suspend fun get(
        endpoint: String,
        queryParams: Map<String, String>?
    ): BaseResponse<String>

}