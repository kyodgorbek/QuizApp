package com.example.quizapp.util

import android.os.Handler
import android.os.Looper
import java.util.*
import kotlin.collections.ArrayList

//------------------
class Quiz {

    val rounds = ArrayList<Round>()
    private var currentRound = -1
    private var currentQuestion: Question? = null
    private val handler = Handler(Looper.getMainLooper())

    private var listener: QuizListener? = null

    private var startTime: Long = -1 // quiz start time
    private var endTime: Long = -1 // quiz end

    private lateinit var quizTimer: Timer // timer for complete quiz
    var lifeLine5050Consumed = false // true when he takes 50 50 life line
    var lifeLine10sConsumed = false // true when he takes 10s life line

    //----------
    interface QuizListener {
        fun onQuizStart(quiz: Quiz)
        fun onQuizTimerTick(quiz: Quiz, seconds: Int)
        fun onRoundStart(quiz: Quiz, round: Round)
        fun onRoundEnd(quiz: Quiz, round: Round)
        fun onNextQuestion(quiz: Quiz, round: Round, question: Question)
        fun onQuestion5050LifeLine(quiz: Quiz, question: Question)
        fun onQuestion10sLifeLine(quiz: Quiz, question: Question)
        fun onQuestionTimerStart(quiz: Quiz, round: Round, question: Question)
        fun onQuestionTimerTick(quiz: Quiz, round: Round, question: Question, seconds: Int)
        fun onQuestionTimerEnd(quiz: Quiz, round: Round, question: Question)
        fun onQuizEnd(quiz: Quiz)
    }

    //----------
    fun createRound(): Round {
        val round = Round(rounds.size)
        rounds.add(round)
        return round
    }

    //----------
    fun take5050LifeLine() {
        currentQuestion?.eliminateTwoOptions()
        listener?.onQuestion5050LifeLine(this, currentQuestion!!)
        lifeLine5050Consumed = true
    }

    //----------
    fun takeLifeLine10s() {
        if (currentQuestion != null) {
            currentQuestion!!.answeringTime += 10
            lifeLine10sConsumed = true
            listener?.onQuestion10sLifeLine(this, currentQuestion!!)
        }
    }

    //----------
    fun setListener(listener: QuizListener) {
        this.listener = listener
    }

    //----------
    private fun hasNextRound(): Boolean {
        return currentRound <= rounds.size - 2
    }

    //----------
    fun start() {
        if (rounds.isNotEmpty()) {
            startTime = System.currentTimeMillis()
            listener?.onQuizStart(this)
        }
    }

    //----------
    private fun startQuizTimer() {
        quizTimer = Timer()
        var seconds = 0
        quizTimer.scheduleAtFixedRate(object : TimerTask() {
            override fun run() {
                handler.post { listener?.onQuizTimerTick(this@Quiz, seconds++) }
            }
        }, 0, 1000)
    }

    //----------
    fun end() {
        endTime = System.currentTimeMillis()
        listener?.onQuizEnd(this)
    }

    //----------
    fun getTimeConsumed(): Long {
        return endTime - startTime
    }

    //----------
    fun startRound() {
        currentRound++
        nextQuestion()
        if (currentRound == 0) //== start quiz timer at first round
            startQuizTimer()
        listener?.onRoundStart(this, rounds[currentRound])
    }

    //----------
    fun nextQuestion() {
        val round = rounds[currentRound]
        if (round.hasNextQuestion()) {
            currentQuestion = round.getNextQuestion()!!
            listener?.onNextQuestion(this, round, currentQuestion!!)
            listener?.onQuestionTimerStart(this, round, currentQuestion!!)
            currentQuestion?.setDisplayed()
            startQuestionTimer(round, currentQuestion!!)
        } else {
            listener?.onRoundEnd(this, round)
        }
    }

    //----------
    fun getNextRound(): Round? {
        return if (hasNextRound())
            rounds[currentRound + 1]
        else
            null
    }

    //----------
    fun getCurrentRound(): Round? {
        return if (currentRound >= 0) rounds[currentRound] else null
    }

    //----------
    private fun startQuestionTimer(round: Round, question: Question) {
        object : Thread() {
            override fun run() {
                var sec = 0
                do {
                    try {
                        if (question == currentQuestion) {
                            handler.post {
                                listener?.onQuestionTimerTick(this@Quiz, round, question, sec++)
                            }
                            sleep(1000)
                        }
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                } while (question.displayedAt + (question.answeringTime * 1000) > System.currentTimeMillis() - 1000)
                // Reducing 1 sec to make it run till 15 else it runs from 0..14
                if (question == currentQuestion)
                    listener?.onQuestionTimerEnd(this@Quiz, round, question)
            }
        }.start()
    }
}