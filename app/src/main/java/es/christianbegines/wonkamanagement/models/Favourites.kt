package es.christianbegines.wonkamanagement.models

import com.google.gson.annotations.SerializedName

data class Favorite(
    @SerializedName("color") var color: String,
    @SerializedName("food") var food: String,
    @SerializedName("random_string") var randomString: String,
    @SerializedName("song") var song: String

)