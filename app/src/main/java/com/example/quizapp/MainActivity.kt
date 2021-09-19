package com.example.quizapp

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.example.quizapp.fragment.QuestionFragment
import com.example.quizapp.fragment.ResultFragment
import com.example.quizapp.fragment.RoundStartFragment
import com.example.quizapp.fragment.WelcomeFragment
import com.example.quizapp.util.*

//------------------
class MainActivity : AppCompatActivity(), Quiz.QuizListener {

    private val LifeLine_50_50 = 1
    private val LifeLine_10s = 2

    private lateinit var quiz: Quiz
    private var qFrag: QuestionFragment? = null

    //----------
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        quiz = QuizFactory().getQuiz()
        quiz.setListener(this)
        addWelcomeFragment()
    }

    //----------
    private fun addWelcomeFragment() {
        val wf = WelcomeFragment()
        wf.onStart = { quiz.start() }
        supportFragmentManager.beginTransaction().replace(R.id.root, wf).commit()
    }

    //----------
    private fun showStartRound(round: Round) {
        val rf = RoundStartFragment()
        rf.round = round
        rf.onStart = { quiz.startRound() }
        supportFragmentManager.beginTransaction().replace(R.id.root, rf).commit()
    }

    //----------
    private fun setQuestion(quiz: Quiz, question: Question) {
        if (qFrag == null) {
            qFrag = QuestionFragment()
            supportFragmentManager.beginTransaction().replace(R.id.root, qFrag!!).commit()
        }
        qFrag!!.onNext = { option ->
            if (option != null)
                question.setAnswered(option)
            quiz.nextQuestion()
        }
        qFrag!!.setQuestion(question)
    }

    //----------
    private fun showResult(summary: QuizSummary) {
        var rf = ResultFragment()
        rf.summary = summary
        supportFragmentManager.beginTransaction().replace(R.id.root, rf).commit()
    }

    //----------
    override fun onQuizStart(quiz: Quiz) {
        val round = quiz.getNextRound()
        if (round != null)
            showStartRound(round)
    }

    //----------
    override fun onQuizTimerTick(quiz: Quiz, seconds: Int) {
        qFrag?.setTotalTime(seconds)
    }

    //----------
    override fun onRoundStart(quiz: Quiz, round: Round) {
        invalidateOptionsMenu()
    }

    //----------
    override fun onRoundEnd(quiz: Quiz, round: Round) {
        val round = quiz.getNextRound()
        if (round == null)
            quiz.end()
    }

    //----------
    override fun onNextQuestion(quiz: Quiz, round: Round, question: Question) {
        setQuestion(quiz, question)
    }

    //----------
    override fun onQuestion5050LifeLine(quiz: Quiz, question: Question) {
        qFrag?.updateLifeLineOptions(question)
        invalidateOptionsMenu()
    }

    //----------
    override fun onQuestion10sLifeLine(quiz: Quiz, question: Question) {
        invalidateOptionsMenu()
    }

    //----------
    override fun onQuestionTimerStart(quiz: Quiz, round: Round, question: Question) {

    }

    //----------
    override fun onQuestionTimerTick(quiz: Quiz, round: Round, question: Question, seconds: Int) {
        qFrag?.setQuestionTime(seconds)
    }

    //----------
    override fun onQuestionTimerEnd(quiz: Quiz, round: Round, question: Question) {
        qFrag?.disableOptions()
    }

    //----------
    override fun onQuizEnd(quiz: Quiz) {
        var summary = QuizSummary(quiz)
        showResult(summary)
    }

    //----------
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menu?.clear()
        if (quiz.getCurrentRound() != null) {
            if (!quiz.lifeLine5050Consumed)
                menu?.add(0, LifeLine_50_50, 0, getString(R.string.life_5050))
            if (!quiz.lifeLine10sConsumed)
                menu?.add(0, LifeLine_10s, 0, getString(R.string.life_10s))
        }
        return true
    }

    //----------
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            LifeLine_50_50 -> quiz.take5050LifeLine()
            LifeLine_10s -> quiz.takeLifeLine10s()
        }
        return true
    }
}