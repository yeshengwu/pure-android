package com.example.pureandroid.livedata

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer

class TestLiveDataFragment : Fragment() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val liveData: LiveData<String> = MutableLiveData<String>()
        liveData.observe(this, object : Observer<String> {
            override fun onChanged(t: String?) {

            }
        })

        liveData.observe(viewLifecycleOwner, object : Observer<String> {
            override fun onChanged(t: String?) {

            }
        })

        liveData.observe(viewLifecycleOwner, Observer<String> {
            
        })

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


        return super.onCreateView(inflater, container, savedInstanceState)
    }

}