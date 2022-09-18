package com.example.mytoproject.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import com.example.mytoproject.R
import com.example.mytoproject.databinding.FragmentOptionBinding


class OptionFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private lateinit var binding : FragmentOptionBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_option, container, false)
        // Inflate the layout for this fragment

        binding.homeTab.setOnClickListener{
            it.findNavController().navigate(R.id.action_optionFragment_to_homeFragment)
        }
        binding.CalculatorTab.setOnClickListener {
            it.findNavController().navigate(R.id.action_optionFragment_to_calculatingFragment)
        }
        binding.boardTab.setOnClickListener {

            it.findNavController().navigate(R.id.action_optionFragment_to_boardFragment)
        }
        binding.notificationTab.setOnClickListener{
            it.findNavController().navigate(R.id.action_optionFragment_self)
        }
        binding.timeTableTab.setOnClickListener {
            it.findNavController().navigate(R.id.action_optionFragment_to_timeTableFragment)
        }

        return binding.root


    }


}