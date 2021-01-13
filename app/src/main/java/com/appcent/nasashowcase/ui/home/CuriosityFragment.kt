package com.appcent.nasashowcase.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.appcent.nasashowcase.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CuriosityFragment : Fragment() {

    private lateinit var curiosityViewModel: CuriosityViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        curiosityViewModel =
            ViewModelProvider(this).get(CuriosityViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_home, container, false)
        val textView: TextView = root.findViewById(R.id.text_home)
        curiosityViewModel.text.observe(viewLifecycleOwner, Observer {
            textView.text = it
        })
        return root
    }
}