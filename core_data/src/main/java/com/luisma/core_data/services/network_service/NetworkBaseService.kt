package com.luisma.core_data.services.network_service

import com.luisma.core.models.BaseResponse
import com.luisma.core.services.network_service.INetworkBaseService
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.http.*

class NetworkBaseService(
    private val client: HttpClient
) : INetworkBaseService {


    override suspend fun get(
        endpoint: String,
        queryParams: Map<String, String>?
    ): BaseResponse<String> {
        try {
            val response = client.get(endpoint) {
                url {
                    queryParams?.forEach { (key, value) ->
                        parameters.append(key, value)
                    }
                }
            }

            if (response.status == HttpStatusCode.OK) {
                return BaseResponse.Success(response.body())
            }
            return BaseResponse.Failure()
        } catch (e: Exception) {
            return BaseResponse.Failure(
                message = e.toString()
            )
        }
    }
}