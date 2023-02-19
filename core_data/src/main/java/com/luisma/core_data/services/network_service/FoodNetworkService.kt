package com.luisma.core_data.services.network_service

import FoodDto
import com.luisma.core.models.BaseResponse
import com.luisma.core.models.food.Food
import com.luisma.core.services.JsonSerializationService
import com.luisma.core.services.network_service.FoodEndpoints
import com.luisma.core.services.network_service.IFoodNetworkService
import com.luisma.core.services.network_service.INetworkBaseService

class FoodNetworkService(
    private val client: INetworkBaseService,
    private val jsonSerializationService: JsonSerializationService
) : IFoodNetworkService {

    override suspend fun searchFood(
        search: String,
        page: Int,
        pageSize: Int
    ): BaseResponse<Food> {
        val response = client.get(
            FoodEndpoints.searchFood,
            queryParams = mapOf(
                "search_terms" to search,
                "page" to page.toString(),
                "page_size" to pageSize.toString(),
            )
        )

        return when (response) {
            is BaseResponse.Success -> {
                val dto = FoodDto.fromJson(
                    response.data,
                    jsonSerializationService
                )
                BaseResponse.Success(
                    dto.toCore()
                )
            }
            is BaseResponse.Failure -> {
                BaseResponse.Failure(
                    message = response.message
                )
            }
        }
    }
}