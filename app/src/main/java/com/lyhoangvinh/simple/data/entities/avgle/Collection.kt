package com.lyhoangvinh.simple.data.entities.avgle

import android.os.Parcel
import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

@Entity(tableName = "COLLECTION")
data class Collection(

    @PrimaryKey(autoGenerate = true)
    var idAuto: Long,

    @SerializedName("id")
    @Expose
    var idCLS: String? = "",

    @SerializedName("title")
    @Expose
    var title: String? = "",

    @SerializedName("keyword")
    @Expose
    var keyword: String? = "",

    @SerializedName("cover_url")
    @Expose
    var coverUrl: String? = "",
    @SerializedName("total_views")
    @Expose
    var totalViews: Int? = 0,
    @SerializedName("video_count")
    @Expose
    var videoCount: Int? = 0,
    @SerializedName("collection_url")
    @Expose
    var collectionUrl: String? = "",

    var type: Int? = null
) :Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readLong(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readValue(Int::class.java.classLoader) as? Int,
        parcel.readValue(Int::class.java.classLoader) as? Int,
        parcel.readString(),
        parcel.readValue(Int::class.java.classLoader) as? Int
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeLong(idAuto)
        parcel.writeString(idCLS)
        parcel.writeString(title)
        parcel.writeString(keyword)
        parcel.writeString(coverUrl)
        parcel.writeValue(totalViews)
        parcel.writeValue(videoCount)
        parcel.writeString(collectionUrl)
        parcel.writeValue(type)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Collection> {
        override fun createFromParcel(parcel: Parcel): Collection {
            return Collection(parcel)
        }

        override fun newArray(size: Int): Array<Collection?> {
            return arrayOfNulls(size)
        }
    }
}