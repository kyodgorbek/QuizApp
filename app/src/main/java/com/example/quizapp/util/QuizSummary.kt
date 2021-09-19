package com.example.quizapp.util

//------------------
class QuizSummary {

    var totalTime: Long = 0
    var totalQuestions = 0
    var totalCorrect = 0

    //----------
    constructor(quiz: Quiz) {
        totalTime = quiz.getTimeConsumed() / 1000
        quiz.rounds.forEach { round ->
            run {
                round.questions.forEach { question ->
                    run {
                        totalQuestions++
                        if (question.isAnsweredCorrect())
                            totalCorrect++
                    }
                }
            }
        }
    }
}