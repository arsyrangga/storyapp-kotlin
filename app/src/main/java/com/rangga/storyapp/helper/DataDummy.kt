package com.rangga.storyapp.helper

import com.rangga.storyapp.data.parcel.ListStoryParcel

object DataDummy {
    fun generateDummyStoryEntity(): List<ListStoryParcel> {
        val newsList = ArrayList<ListStoryParcel>()
        for (i in 0..10) {
            val story = ListStoryParcel(
                "$i",
                "Dummy name",
                "dummy desc",
                "https://www.dicoding.com/",
                "2002-09-11",
                -20.3,
                -213.3
            )
            newsList.add(story)
        }
        return newsList
    }
    fun generateDummyEmptyStoryEntity(): List<ListStoryParcel> {
        val newsList = ArrayList<ListStoryParcel>()
        return newsList
    }
}