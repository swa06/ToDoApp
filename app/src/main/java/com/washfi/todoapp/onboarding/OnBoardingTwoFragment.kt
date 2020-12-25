package com.washfi.todoapp.onboarding

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import com.washfi.todoapp.R

class OnBoardingTwoFragment : Fragment() {
    lateinit var buttonDone: Button
    lateinit var buttonBack: Button
    lateinit var onOptionClick: OnOptionClick

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_on_boarding_two, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bindViews(view)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        onOptionClick = context as OnOptionClick
    }

    private fun bindViews(view: View) {
        buttonBack = view.findViewById(R.id.buttonBack)
        buttonDone = view.findViewById(R.id.buttonDone)
        addClickListeners()
    }

    private fun addClickListeners() {
        buttonBack.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                onOptionClick.onOptionBack()
            }
        })

        buttonDone.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                onOptionClick.onOptionDone()
            }
        })
    }

    interface OnOptionClick {
        fun onOptionBack()
        fun onOptionDone()
    }
}