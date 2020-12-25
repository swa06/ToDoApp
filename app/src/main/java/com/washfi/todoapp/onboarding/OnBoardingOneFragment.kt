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

class OnBoardingOneFragment : Fragment() {
    lateinit var buttonNext: Button
    lateinit var onNextClick: OnNextClick

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_on_boarding_one, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bindViews(view)
    }

    private fun bindViews(view: View) {
        buttonNext = view.findViewById(R.id.buttonNext)
        addClickListeners()
    }

    private fun addClickListeners() {
        buttonNext.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                onNextClick.onClick()
            }
        })
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        onNextClick = context as OnNextClick
    }

    interface OnNextClick {
        fun onClick()
    }
}