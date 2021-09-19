package com.example.quizapp.util

//------------------
class Round(val id: Int) {

    val questions = ArrayList<Question>()
    private var currentQuestIndex = -1

    //----------
    fun shuffleQuestions() {
        questions.shuffle()
    }

    //----------
    fun addQuestion(question: Question): Round {
        questions.add(question)
        return this
    }

    //----------
    fun hasNextQuestion(): Boolean {
        return currentQuestIndex <= questions.size - 2
    }

    //----------
    fun getNextQuestion(): Question? {
        return if (hasNextQuestion())
            questions[++currentQuestIndex]
        else
            null
    }
}