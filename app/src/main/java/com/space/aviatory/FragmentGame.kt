package com.space.aviatory

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.Navigation
import com.space.aviatory.databinding.FragmentGameBinding


class FragmentGame : Fragment() {

    private lateinit var binding: FragmentGameBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentGameBinding.inflate(inflater,container,false)
        binding.game.setEndListener(object : GameView.Companion.EndListener{
            override fun end() {
                Log.d("TAG","LIST")
               requireActivity().runOnUiThread {
                   Toast.makeText(requireContext(),"Game Over",Toast.LENGTH_LONG).show()
                   var set = requireContext().getSharedPreferences("prefs", Context.MODE_PRIVATE).getStringSet("records",HashSet<String>())!!.map { it.toInt() }.toSet()
                   var set1 = HashSet<Int>()
                   set1.addAll(set)
                   if(!set1.contains(binding.game.score)) set1.add(binding.game.score)
                   requireActivity().getSharedPreferences("prefs",Context.MODE_PRIVATE).edit()
                       .putStringSet("records",set1.map { it.toString() }.toSet()).apply()
                   val navController = Navigation.findNavController(requireActivity(),R.id.fragmentContainerView)
                   navController.popBackStack()
               }
            }

        })
        binding.imageView5.setOnClickListener {
            val navController = Navigation.findNavController(requireActivity(),R.id.fragmentContainerView)
            navController.popBackStack()
        }

        return binding.root
    }


}