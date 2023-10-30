package com.rangga.storyapp.data.response

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ListStoryDataResponse(
	val id : String,
	val name : String,
	val description : String,
	val photoUrl : String
) : Parcelable