package com.washfi.todoapp.addnotes.bottomsheet

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.washfi.todoapp.R
import com.washfi.todoapp.addnotes.OnOptionClickListener

class FileSelectorFragment : BottomSheetDialogFragment() {
    companion object {
        const val TAG = "FileSelectorFragment"
        fun newInstance() = FileSelectorFragment()
    }

    private lateinit var textViewCamera: TextView
    private lateinit var textViewGallery: TextView
    private lateinit var onOptionClick: OnOptionClickListener

    override fun onAttach(context: Context) {
        super.onAttach(context)
        onOptionClick = context as OnOptionClickListener
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.bottom_sheet_selector, container, false)
        bindViews(view)
        setupClickListener()
        return view
    }

    private fun setupClickListener() {
        textViewCamera.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                onOptionClick.onCameraClick()
                dismiss()
            }

        })

        textViewGallery.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                onOptionClick.onGalleryClick()
                dismiss()
            }

        })
    }

    private fun bindViews(view: View) {
        textViewCamera = view.findViewById(R.id.textViewCamera)
        textViewGallery = view.findViewById(R.id.textViewGallery)
    }
}