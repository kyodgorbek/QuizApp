package com.example.quizapp.util

//------------------
// There could be more ways to create quiz for demonstration I am using this class to
// initialize a quiz with question
class QuizFactory {

    //----------
    fun getQuiz(): Quiz {
        val quiz = Quiz()
        val round1 = quiz.createRound()
        addRound1Questions(round1)
        return quiz
    }

    //----------
    private fun addRound1Questions(round: Round) {
        round.addQuestion(
            Question("What is 2+2 ?")
                .addOption(Option("1", false))
                .addOption(Option("2", false))
                .addOption(Option("3", false))
                .addOption(Option("4", true))
        )
        round.addQuestion(
            Question("What is 3+3 ?")
                .addOption(Option("5", false))
                .addOption(Option("6", true))
                .addOption(Option("7", false))
                .addOption(Option("4", false))
        )

        round.addQuestion(
            Question("What is 2-2 ?")
                .addOption(Option("1", false))
                .addOption(Option("4", false))
                .addOption(Option("0", true))
                .addOption(Option("2", false))
        )

        round.addQuestion(
            Question("What is 5+2 ?")
                .addOption(Option("8", false))
                .addOption(Option("9", false))
                .addOption(Option("6", false))
                .addOption(Option("7", true))
        )

        round.addQuestion(
            Question("What is 4x2 ?")
                .addOption(Option("9", false))
                .addOption(Option("10", false))
                .addOption(Option("8", true))
                .addOption(Option("6", false))
        )


        round.addQuestion(
            Question("What is 3+4 ?")
                .addOption(Option("9", false))
                .addOption(Option("6", false))
                .addOption(Option("8", false))
                .addOption(Option("7", true))
        )
        round.addQuestion(
            Question("What is 9-5 ?")
                .addOption(Option("3", false))
                .addOption(Option("4", true))
                .addOption(Option("2", false))
                .addOption(Option("1", false))
        )

        round.addQuestion(
            Question("What is 10-8 ?")
                .addOption(Option("5", false))
                .addOption(Option("1", false))
                .addOption(Option("2", true))
                .addOption(Option("3", false))
        )

        round.addQuestion(
            Question("What is 7+5 ?")
                .addOption(Option("15", false))
                .addOption(Option("13", false))
                .addOption(Option("11", false))
                .addOption(Option("12", true))
        )

        round.addQuestion(
            Question("What is 5x3 ?")
                .addOption(Option("16", false))
                .addOption(Option("14", false))
                .addOption(Option("15", true))
                .addOption(Option("12", false))
        )
        round.shuffleQuestions()
    }
}