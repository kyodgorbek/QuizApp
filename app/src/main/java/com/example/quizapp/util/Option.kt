package com.example.quizapp.util

//------------------
class Option(val text: String, val isCorrect: Boolean) {

    //----------
    override fun equals(other: Any?): Boolean {
        return text == (other as Option).text
    }

    //----------
    override fun hashCode(): Int {
        return text.hashCode()
    }
}