package br.com.felipe.santana.continuadayoshi

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Agent(
    val id: String,
    val name: String,
    val avatar: String,
    val access_level: Int,
    val access_color: String,
    val email: String,
    val password: String,
    val access_code: String,
    val created_at: String,
    val location:String
) : Parcelable