package com.smparkworld.bigwalktest.data

import com.google.gson.annotations.SerializedName
import java.text.SimpleDateFormat

data class Campaign(

    @SerializedName("id")
    var id: Int,

    @SerializedName("name")
    var name: String,

    @SerializedName("campaignPromoter")
    var promoter: Promoter,

    @SerializedName("organizations")
    var organizations: List<Organization>,

    @SerializedName("ratio")
    var ratio: Int,

    @SerializedName("endDate")
    var endDate: String,

    @SerializedName("smallListThumbnailImagePath")
    var imagePath: String,

    @SerializedName("my")
    var my: My
) {
    fun isClose(): Boolean =
        SimpleDateFormat("yyyy-MM-dd H:m:s").parse(endDate.substring(0, 9) + " " + endDate.substring(11))
            .time < System.currentTimeMillis()
}

data class Promoter (

    @SerializedName("id")
    var id: Int,

    @SerializedName("name")
    var name: String
)

data class Organization(

    @SerializedName("id")
    var id: Int
)

data class My (

    @SerializedName("donatedSteps")
    var donatedSteps: Int,

    @SerializedName("ranking")
    var ranking: Int,

    @SerializedName("lastDonatedDateTime")
    var lastDonatedDateTime: String,

    @SerializedName("story")
    var story: Boolean
)

enum class CampaignType {
    ALL, OPEN, GROUP
}