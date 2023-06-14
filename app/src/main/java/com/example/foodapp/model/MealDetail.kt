package com.example.foodapp.model

import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

data class MealDetail (
    @SerializedName("meals" ) var meals : List<MealsDetail>? = null
)

@Entity("saved_meal_details")
data class MealsDetail (
    @SerializedName("idMeal"                      ) val IDmeal                      : String? = null,
    @SerializedName("strMeal"                     ) val strMeal                     : String? = null,
    @SerializedName("strDrinkAlternate"           ) val strDrinkAlternate           : String? = null,
    @SerializedName("strCategory"                 ) val strCategory                 : String? = null,
    @SerializedName("strArea"                     ) val strArea                     : String? = null,
    @SerializedName("strInstructions"             ) val strInstructions             : String? = null,
    @SerializedName("strMealThumb"                ) val strMealThumb                : String? = null,
    @SerializedName("strTags"                     ) val strTags                     : String? = null,
    @SerializedName("strYoutube"                  ) var strYoutube                  : String? = null,

    @SerializedName("strIngredient1"              ) val ingredient1                 : String? = null,
    @SerializedName("strIngredient2"              ) val ingredient2                 : String? = null,
    @SerializedName("strIngredient3"              ) val ingredient3                 : String? = null,
    @SerializedName("strIngredient4"              ) val ingredient4                 : String? = null,
    @SerializedName("strIngredient5"              ) val ingredient5                 : String? = null,
    @SerializedName("strIngredient6"              ) val ingredient6                 : String? = null,
    @SerializedName("strIngredient7"              ) val ingredient7                 : String? = null,
    @SerializedName("strIngredient8"              ) val ingredient8                 : String? = null,
    @SerializedName("strIngredient9"              ) val ingredient9                 : String? = null,
    @SerializedName("strIngredient10"             ) val ingredient10                : String? = null,
    @SerializedName("strIngredient11"             ) val ingredient11                : String? = null,
    @SerializedName("strIngredient12"             ) val ingredient12                : String? = null,
    @SerializedName("strIngredient13"             ) val ingredient13                : String? = null,
    @SerializedName("strIngredient14"             ) val ingredient14                : String? = null,
    @SerializedName("strIngredient15"             ) val ingredient15                : String? = null,
    @SerializedName("strIngredient16"             ) val ingredient16                : String? = null,
    @SerializedName("strIngredient17"             ) val ingredient17                : String? = null,
    @SerializedName("strIngredient18"             ) val ingredient18                : String? = null,
    @SerializedName("strIngredient19"             ) val ingredient19                : String? = null,
    @SerializedName("strIngredient20"             ) val ingredient20                : String? = null,

    @SerializedName("strMeasure1"                 ) val measure1                    : String? = null,
    @SerializedName("strMeasure2"                 ) val measure2                    : String? = null,
    @SerializedName("strMeasure3"                 ) val measure3                    : String? = null,
    @SerializedName("strMeasure4"                 ) val measure4                    : String? = null,
    @SerializedName("strMeasure5"                 ) val measure5                    : String? = null,
    @SerializedName("strMeasure6"                 ) val measure6                    : String? = null,
    @SerializedName("strMeasure7"                 ) val measure7                    : String? = null,
    @SerializedName("strMeasure8"                 ) val measure8                    : String? = null,
    @SerializedName("strMeasure9"                 ) val measure9                    : String? = null,
    @SerializedName("strMeasure10"                ) val measure10                   : String? = null,
    @SerializedName("strMeasure11"                ) val measure11                   : String? = null,
    @SerializedName("strMeasure12"                ) val measure12                   : String? = null,
    @SerializedName("strMeasure13"                ) val measure13                   : String? = null,
    @SerializedName("strMeasure14"                ) val measure14                   : String? = null,
    @SerializedName("strMeasure15"                ) val measure15                   : String? = null,
    @SerializedName("strMeasure16"                ) val measure16                   : String? = null,
    @SerializedName("strMeasure17"                ) val measure17                   : String? = null,
    @SerializedName("strMeasure18"                ) val measure18                   : String? = null,
    @SerializedName("strMeasure19"                ) val measure19                   : String? = null,
    @SerializedName("strMeasure20"                ) val measure20                   : String? = null,

    @SerializedName("strSource"                   ) val strSource                   : String? = null,
    @SerializedName("strImageSource"              ) val strImageSource              : String? = null,
    @SerializedName("strCreativeCommonsConfirmed" ) val strCreativeCommonsConfirmed : String? = null,
    @SerializedName("dateModified"                ) val dateModified                : String? = null,

    @PrimaryKey(autoGenerate = true)
    val primaryKey: Int = 0,
)

data class IngredientAndMeasures(
    val ingredents : String?,
    val measures : String?,
)




