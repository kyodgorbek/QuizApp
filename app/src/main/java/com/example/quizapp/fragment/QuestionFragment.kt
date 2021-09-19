package com.example.quizapp.fragment

import android.os.Bundle
import android.text.format.DateUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.quizapp.R
import com.example.quizapp.util.Option
import com.example.quizapp.util.Question

//------------------
class QuestionFragment : Fragment() {

    var onNext: (Option?) -> Unit = {}

    private lateinit var question: Question

    private lateinit var qText: TextView
    private lateinit var totalTime: TextView
    private lateinit var questTime: TextView
    private lateinit var option1: RadioButton
    private lateinit var option2: RadioButton
    private lateinit var option3: RadioButton
    private lateinit var option4: RadioButton
    private lateinit var group: RadioGroup

    private lateinit var next: Button

    //----------
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val v = inflater.inflate(R.layout.frag_question, container, false)
        v.findViewById<Button>(R.id.next).setOnClickListener {
            when (group.checkedRadioButtonId) {
                R.id.option1 -> onNext.invoke(question.options[0])
                R.id.option2 -> onNext.invoke(question.options[1])
                R.id.option3 -> onNext.invoke(question.options[2])
                R.id.option4 -> onNext.invoke(question.options[3])
                else -> onNext.invoke(null)
            }
        }
        qText = v.findViewById(R.id.question)
        next = v.findViewById(R.id.next)
        option1 = v.findViewById(R.id.option1)
        option2 = v.findViewById(R.id.option2)
        option3 = v.findViewById(R.id.option3)
        option4 = v.findViewById(R.id.option4)
        group = v.findViewById(R.id.radio)
        totalTime = v.findViewById(R.id.t_time)
        questTime = v.findViewById(R.id.q_time)
        setQuestion(question)
        return v
    }

    //----------
    fun setQuestion(question: Question) {
        this.question = question
        if (this::qText.isInitialized) {
            qText.text = question.text
            group.clearCheck()
            option1.text = question.options[0].text
            option2.text = question.options[1].text
            option3.text = question.options[2].text
            option4.text = question.options[3].text
            enableOptions()
        }
    }

    //----------
    fun setQuestionTime(seconds: Int) {
        questTime.text = "00:".plus(seconds.toString().padStart(2, '0'))
    }

    //----------
    fun setTotalTime(seconds: Int) {
        totalTime.text = DateUtils.formatElapsedTime(seconds.toLong())
    }

    //----------
    fun disableOptions() {
        option1.isEnabled = false
        option2.isEnabled = false
        option3.isEnabled = false
        option4.isEnabled = false
    }

    //----------
    private fun enableOptions() {
        option1.isEnabled = true
        option2.isEnabled = true
        option3.isEnabled = true
        option4.isEnabled = true
        option3.visibility = View.VISIBLE
        option4.visibility = View.VISIBLE
    }

    //----------
    fun updateLifeLineOptions(question: Question) {
        this.question = question
        option1.text = question.options[0].text
        option2.text = question.options[1].text
        option3.visibility = View.INVISIBLE
        option4.visibility = View.INVISIBLE
    }
}