package com.example.news.models

import com.google.gson.annotations.SerializedName

data class Covid(
    @SerializedName("countries_stat")
    val countries : List<Countries>,
    @SerializedName("world_total")
    val worldTotal: WordTotal
)
data class WordTotal(
    @SerializedName("total_cases")
    var cases : String,
    @SerializedName("total_deaths")
    var deaths: String,
    @SerializedName("total_recovered")
    var totalRecovered: String,
    @SerializedName("new_cases")
    var newCases: String
)

data class Countries(
    @SerializedName("country_name")
    var countryName: String,
    var cases : String,
    var deaths: String,
    @SerializedName("total_recovered")
    var totalRecovered: String,
    @SerializedName("new_cases")
    var newCases: String

)