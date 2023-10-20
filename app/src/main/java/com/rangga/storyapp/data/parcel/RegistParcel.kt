package com.rangga.storyapp.data.parcel

import kotlinx.parcelize.Parcelize
import android.os.Parcelable

@Parcelize
data class RegistParcel(
    val name: String,
    val email: String,
    val password: String
) : Parcelable