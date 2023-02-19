import com.luisma.core.models.food.Food
import com.luisma.core.models.food.NutrimentsFood
import com.luisma.core.models.food.ProductsFood
import com.luisma.core.services.JsonSerializationService
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class FoodDto(
    @SerialName("count")
    val count: Int?,
    @SerialName("page")
    val page: Int?,
    @SerialName("page_count")
    val pageCount: Int?,
    @SerialName("page_size")
    val pageSize: Int?,
    @SerialName("products")
    val products: List<ProductsFoodDto>?
) {
    companion object {
        fun fromJson(
            data: String,
            jsonSerializationService: JsonSerializationService
        ): FoodDto {
            return jsonSerializationService.decodeFromString(string = data)
        }
    }

    fun toCore(): Food {
        val base = Food()
        return Food(
            count = count ?: base.count,
            page = page ?: base.page,
            pageCount = pageCount ?: base.pageCount,
            pageSize = pageSize ?: base.pageSize,
            products = products?.map {
                it.toCore()
            } ?: base.products,
        )
    }
}

@Serializable
data class ProductsFoodDto(
    @SerialName("product_name")
    val name: String? = null,
    @SerialName("image_front_thumb_url")
    val imageUrl: String? = null,
    @SerialName("nutriments")
    val nutriments: NutrimentsFoodDto? = null,
) {
    fun toCore(): ProductsFood {
        val base = ProductsFood()
        return ProductsFood(
            name = name ?: base.name,
            imageUrl = imageUrl ?: base.imageUrl,
            nutriments = nutriments?.toCore() ?: base.nutriments,
        )
    }
}

@Serializable
data class NutrimentsFoodDto(
    @SerialName("carbohydrates_100g")
    val carbohydrates100g: Float? = null,
    @SerialName("energy-kcal_100g")
    val energyKcal100g: Float? = null,
    @SerialName("fat_100g")
    val fat100g: Float? = null,
    @SerialName("proteins_100g")
    val proteins100g: Float? = null,
) {
    fun toCore(): NutrimentsFood {
        val base = NutrimentsFood()
        return NutrimentsFood(
            carbohydrates100g = carbohydrates100g?.toInt() ?: base.carbohydrates100g,
            energyKcal100g = energyKcal100g?.toInt() ?: base.energyKcal100g,
            fat100g = fat100g?.toInt() ?: base.fat100g,
            proteins100g = proteins100g?.toInt() ?: base.proteins100g,
        )
    }
}