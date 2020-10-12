package app.fdj.fdjtest.entity

import com.google.gson.annotations.SerializedName

data class Player(
    @SerializedName("idPlayer") val id: Int,
    @SerializedName("strPlayer") val name: String,
    @SerializedName("dateBorn") val bornAt: String,
    @SerializedName("strPosition") val position: String,
    @SerializedName("strThumb") val thumb: String
)