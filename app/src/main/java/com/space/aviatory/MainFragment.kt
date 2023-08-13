package com.space.aviatory

import android.graphics.drawable.Drawable
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import com.space.aviatory.databinding.FragmentMainBinding
import java.io.IOException

class MainFragment : Fragment() {

    private lateinit var binding: FragmentMainBinding


     override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
         binding = FragmentMainBinding.inflate(inflater,container,false)
         try {
             // get input stream
             val ims = requireContext().assets.open("bg.png")
             // load image as Drawable
             val d = Drawable.createFromStream(ims, null)
             // set image to ImageView
             binding.imageView.setImageDrawable(d)
             ims.close()
         } catch (ex: IOException) {
             ex.printStackTrace()
         }
         try {
             // get input stream
             val ims = requireContext().assets.open("logo.png")
             // load image as Drawable
             val d = Drawable.createFromStream(ims, null)
             // set image to ImageView
             binding.imageView2.setImageDrawable(d)
             ims.close()
         } catch (ex: IOException) {
             ex.printStackTrace()
         }
        binding.button.setOnClickListener {
            val navController = Navigation.findNavController(requireActivity(),R.id.fragmentContainerView)
            navController.navigate(R.id.fragmentGame)
        }
         binding.button2.setOnClickListener {
             val navController = Navigation.findNavController(requireActivity(),R.id.fragmentContainerView)
             navController.navigate(R.id.scoreFragment)
         }
        return binding.root
    }


}