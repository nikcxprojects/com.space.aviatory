package com.space.aviatory

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import com.space.aviatory.databinding.FragmentScoreBinding

class ScoreFragment : Fragment() {


    private lateinit var binding: FragmentScoreBinding
    private lateinit var adapter: ScoreAdapter


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentScoreBinding.inflate(inflater,container,false)
        adapter = ScoreAdapter(requireContext())
        binding.list.adapter = adapter
        binding.imageView8.setOnClickListener {
            val navController = Navigation.findNavController(requireActivity(),R.id.fragmentContainerView)
            navController.popBackStack()
        }
        return binding.root
    }


}