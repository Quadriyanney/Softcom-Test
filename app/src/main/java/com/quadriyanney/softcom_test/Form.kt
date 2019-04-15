package com.quadriyanney.softcom_test

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

data class Form (
    val id: String,
    val name: String,
    val pages: List<Page>
)

@Parcelize
data class Page (
    val label: String,
    val sections: List<Section>
) : Parcelable

@Parcelize
data class Section (
    val label: String,
    val elements: List<Element>
) : Parcelable

@Parcelize
data class Element (
    val type: String,
    val file: String?,
    val label: String?,
    val isMandatory: Boolean?,
    @SerializedName("unique_id")
    val uniqueId: String,
    val keyboard: String?,
    val formattedNumeric: String?,
    val mode: String,
    val rules: List<Rule>?
) : Parcelable

@Parcelize
data class Rule (
    val condition: String,
    val value: String,
    val action: String,
    val otherwise: String,
    val targets: List<String>
) : Parcelable
