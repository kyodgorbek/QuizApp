package com.example.quizapp.util

//------------------
class Question(val text: String) {

    var answeringTime = 15 // seconds
    var options: ArrayList<Option> = ArrayList()
    var displayedAt: Long = -1 // time at which question displayed
    var answeredAt: Long = -1 // time at which it exited and next question was shown
    var selectedOption: Option? = null

    //----------
    fun addOption(option: Option): Question {
        options.add(option)
        return this
    }

    //----------
    // set time at which question was displayed
    fun setDisplayed() {
        displayedAt = System.currentTimeMillis()
    }

    //----------
    // set time at which question was answered
    fun setAnswered(option: Option) {
        answeredAt = System.currentTimeMillis()
        selectedOption = option
    }

    //----------
    fun isAnswered(): Boolean {
        return selectedOption != null
    }

    //----------
    fun isAnsweredCorrect(): Boolean {
        options.forEach { option ->
            if (selectedOption != null && option.isCorrect && option == selectedOption) return true
        }
        return false
    }

    //----------
    fun eliminateTwoOptions() {
        var o2 = ArrayList<Option>()
        options.forEach { option ->
            run {
                if (option.isCorrect) {
                    o2.add(option)
                    return@forEach
                }
            }
        }
        o2.add(options[0])
        options = o2
        options.shuffle()
    }
}