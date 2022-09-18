package com.example.mytoproject.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import com.example.mytoproject.R
import com.example.mytoproject.databinding.FragmentHomeBinding


class HomeFragment : Fragment() {

    private lateinit var binding : FragmentHomeBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_home, container, false)
        // Inflate the layout for this fragment

        binding.homeTab.setOnClickListener{
            it.findNavController().navigate(R.id.action_homeFragment_self)
        }
        binding.CalculatorTab.setOnClickListener {
            it.findNavController().navigate(R.id.action_homeFragment_to_calculatingFragment)
        }
        binding.boardTab.setOnClickListener {

            it.findNavController().navigate(R.id.action_homeFragment_to_boardFragment)
        }
        binding.notificationTab.setOnClickListener{
            it.findNavController().navigate(R.id.action_homeFragment_to_optionFragment)
        }
        binding.timeTableTab.setOnClickListener {
            it.findNavController().navigate(R.id.action_homeFragment_to_timeTableFragment)
        }

        return binding.root
    }

}

