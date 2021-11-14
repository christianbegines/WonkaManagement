package es.christianbegines.wonkamanagement.models

import com.google.gson.annotations.SerializedName

data class OompaLoompa (
    @SerializedName("first_name") var firstName : String,
    @SerializedName("last_name") var lastName : String,
    @SerializedName("favorite") var favorite : Favorite,
    @SerializedName("gender") var gender : String,
    @SerializedName("image") var image : String,
    @SerializedName("profession") var profession : String,
    @SerializedName("email") var email : String,
    @SerializedName("age") var age : Int,
    @SerializedName("country") var country : String,
    @SerializedName("height") var height : Int,
    @SerializedName("id") var id : Int,
    @SerializedName("quote") var quote : String,
    @SerializedName("description") var description : String

)