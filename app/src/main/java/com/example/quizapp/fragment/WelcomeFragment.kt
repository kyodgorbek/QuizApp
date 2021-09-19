package com.example.quizapp.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import com.example.quizapp.R

//------------------
class WelcomeFragment : Fragment() {

    var onStart: () -> Unit = {}

    //----------
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val v = inflater.inflate(R.layout.welcome, container, false)
        v.findViewById<Button>(R.id.start).setOnClickListener { onStart.invoke() }
        return v
    }
}