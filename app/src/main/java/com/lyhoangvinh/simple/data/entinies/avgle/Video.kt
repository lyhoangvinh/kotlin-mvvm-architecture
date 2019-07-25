package com.lyhoangvinh.simple.data.entinies.avgle

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

@Entity(tableName = "VIDEO")
data class Video(

    @PrimaryKey(autoGenerate = true)
    var idAuto: Long,

    @SerializedName("vid")
    @Expose
    var vid: String? = "",

    @SerializedName("uid")
    @Expose
    var uid: String? = "",

    @SerializedName("title")
    @Expose
    var title: String? = "",

    @SerializedName("keyword")
    @Expose
    var keyword: String? = "",

    @SerializedName("channel")
    @Expose
    var channel: String? = "",

    @SerializedName("duration")
    @Expose
    var duration: Double? = 0.0,

    @SerializedName("framerate")
    @Expose
    var framerate: Double? = 0.0,

    @SerializedName("hd")
    @Expose
    var hd: Boolean? = false,

    @SerializedName("addtime")
    @Expose
    var addTime: Long? = 0,

    @SerializedName("viewnumber")
    @Expose
    var viewNumber: Long? = 0,

    @SerializedName("likes")
    @Expose
    var likes: Long? = 0,

    @SerializedName("dislikes")
    @Expose
    var dislikes: Long? = 0,

    @SerializedName("video_url")
    @Expose
    var videoUrl: String? = "",

    @SerializedName("embedded_url")
    @Expose
    var embeddedUrl: String? = "",

    @SerializedName("preview_url")
    @Expose
    var previewUrl: String? = "",

    @SerializedName("preview_video_url")
    @Expose
    var previewVideoUrl: String? = "",

    var type: Int? = null
)