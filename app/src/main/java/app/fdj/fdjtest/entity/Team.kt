package app.fdj.fdjtest.entity

import com.google.gson.annotations.SerializedName

data class Team(
    @SerializedName("idTeam") val id: Int,
    @SerializedName("strTeamBadge") val badge: String
)