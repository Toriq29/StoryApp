package com.dicoding.picodiploma.loginwithanimation

import com.dicoding.picodiploma.loginwithanimation.data.remote.response.StoryItem

object DataDummy {

    fun generateDummyStoryResponse(): List<StoryItem> {
        val items: MutableList<StoryItem> = arrayListOf()
        for (i in 0..100) {
            val story = StoryItem(
                name = "name + $i",
                description = "desc + $i"
            )
            items.add(story)
        }
        return items
    }
}