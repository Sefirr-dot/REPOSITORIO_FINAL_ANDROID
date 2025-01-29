package com.example.tabsfragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.geolocalizacion.DetailActivity
import com.example.geolocalizacion.R
import com.example.geolocalizacion.databinding.ActivityMainBinding
import com.example.geolocalizacion.databinding.FragmentFirstBinding
import com.example.geolocalizacion.databinding.FragmentFourthBinding

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [FirstFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class FourthFragment : Fragment() {


    private var _binding: FragmentFourthBinding? = null
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentFourthBinding.inflate(inflater, container, false)
        val view = binding.root



        binding.imgvMuseo.setOnClickListener {

            //38.88861466967061, -3.7119762931206415
            val latitude = 38.88861466967061
            val longitude = -3.7119762931206415
            val video = R.raw.corral

            val intent = Intent(requireContext(), DetailActivity::class.java).apply {
                putExtra("PLACE_NAME", "Corral de Comedias de Almagro")
                putExtra("LATITUDE", latitude)
                putExtra("LONGITUDE", longitude)

                putExtra("video", video)
            }
            startActivity(intent)
        }

        return view
    }




}