package com.lyhoangvinh.simple.data.entities.comic

import android.os.Parcel
import android.os.Parcelable
import androidx.room.Entity
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

@Entity
data class ImageAll(
    @SerializedName("medium_url")
    @Expose
    var medium_url: String? = "",

    @SerializedName("thumb_url")
    @Expose
    var thumbUrl: String? = "",

    @SerializedName("super_url")
    @Expose
    var superUrl: String? = ""
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readString()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(medium_url)
        parcel.writeString(thumbUrl)
        parcel.writeString(superUrl)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<ImageAll> {
        override fun createFromParcel(parcel: Parcel): ImageAll {
            return ImageAll(parcel)
        }

        override fun newArray(size: Int): Array<ImageAll?> {
            return arrayOfNulls(size)
        }
    }
}