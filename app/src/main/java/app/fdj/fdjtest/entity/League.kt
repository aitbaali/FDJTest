package app.fdj.fdjtest.entity

import android.os.Parcel
import android.os.Parcelable
import com.arlib.floatingsearchview.suggestions.model.SearchSuggestion
import com.google.gson.annotations.SerializedName

data class League(
    @SerializedName("idLeague") val id: Int,
    @SerializedName("strLeague") val name: String
) : SearchSuggestion {

    constructor(parcel: Parcel) : this(parcel.readInt(), parcel.readString())

    override fun writeToParcel(dest: Parcel?, flags: Int) {
        dest?.writeInt(id)
        dest?.writeString(name)
    }

    override fun describeContents(): Int = 0

    override fun getBody(): String = name

    companion object CREATOR : Parcelable.Creator<League> {
        override fun createFromParcel(parcel: Parcel): League = League(parcel)

        override fun newArray(size: Int): Array<League?> = arrayOfNulls(size)
    }
}