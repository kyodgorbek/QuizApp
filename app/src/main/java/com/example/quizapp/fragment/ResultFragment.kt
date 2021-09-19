package com.example.quizapp.fragment

import android.os.Bundle
import android.text.format.DateUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.quizapp.R
import com.example.quizapp.util.QuizSummary

//------------------
class ResultFragment : Fragment() {

    lateinit var summary: QuizSummary

    //----------
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val v = inflater.inflate(R.layout.result, container, false)
        if (this::summary.isInitialized) {
            v.findViewById<TextView>(R.id.t_time).text =
                DateUtils.formatElapsedTime(summary.totalTime)
            v.findViewById<TextView>(R.id.c_ans).text =
                summary.totalCorrect.toString().plus("/").plus(summary.totalQuestions)
        }
        return v
    }
}