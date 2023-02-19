package com.luisma.core.services.network_service

import com.luisma.core.models.food.Food
import com.luisma.core.models.BaseResponse

interface IFoodNetworkService {
    suspend fun searchFood(
        search: String,
        page: Int,
        pageSize: Int
    ): BaseResponse<Food>
}