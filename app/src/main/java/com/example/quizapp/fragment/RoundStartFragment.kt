package com.example.quizapp.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import com.example.quizapp.R
import com.example.quizapp.util.Round

//------------------
class RoundStartFragment : Fragment() {

    var onStart: () -> Unit = {}
    var round: Round? = null

    //----------
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val v = inflater.inflate(R.layout.quiz_round, container, false)
        v.findViewById<Button>(R.id.start).setOnClickListener { onStart.invoke() }
        return v
    }
}