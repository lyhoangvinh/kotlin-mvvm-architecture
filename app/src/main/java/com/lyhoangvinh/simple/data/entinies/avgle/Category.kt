package com.lyhoangvinh.simple.data.entinies.avgle

import android.os.Parcel
import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

@Entity(tableName = "CATEGORY")
data class Category(

    @PrimaryKey(autoGenerate = true)
    var id: Long,

    @SerializedName("CHID")
    @Expose
    var CHID: String? = "",

    @SerializedName("name")
    @Expose
    var name: String? = "",

    @SerializedName("slug")
    @Expose
    var slug: String? = "",

    @SerializedName("total_videos")
    @Expose
    var totalVideos: Int? = 0,

    @SerializedName("category_url")
    @Expose
    var categoryUrl: String? = "",

    @SerializedName("cover_url")
    @Expose
    var coverUrl: String? = ""
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readLong(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readValue(Int::class.java.classLoader) as? Int,
        parcel.readString(),
        parcel.readString()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeLong(id)
        parcel.writeString(CHID)
        parcel.writeString(name)
        parcel.writeString(slug)
        parcel.writeValue(totalVideos)
        parcel.writeString(categoryUrl)
        parcel.writeString(coverUrl)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Category> {
        override fun createFromParcel(parcel: Parcel): Category {
            return Category(parcel)
        }

        override fun newArray(size: Int): Array<Category?> {
            return arrayOfNulls(size)
        }
    }
}