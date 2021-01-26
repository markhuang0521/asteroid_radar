package com.udacity.asteroidradar.main

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.squareup.picasso.Picasso
import com.udacity.asteroidradar.R
import com.udacity.asteroidradar.databinding.FragmentMainBinding
import com.udacity.asteroidradar.domain.Asteroid

class MainFragment : Fragment() {


    private val viewModel: MainViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


        val binding = FragmentMainBinding.inflate(inflater)
        binding.lifecycleOwner = this

        binding.viewModel = viewModel
        binding.asteroidRecycler.adapter =
            MainRecyclerAdapter(AsteroidClickListener {
                viewModel.onSelectAsteroid(it)
            })


        viewModel.curAsteroid.observe(
            viewLifecycleOwner, Observer
            {
                it?.let {
                    findNavController().navigate(MainFragmentDirections.actionShowDetail(it))
                    viewModel.unSelectAsteroid()

                }

            })

        // my naive way to perform swipe refresh, please suggest a better way to do this in kotlin. thanks
        viewModel.refresh.observe(viewLifecycleOwner, Observer
        {
            if (it) {
                viewModel.refresh()
                viewModel.onReady()
            }
        })


        setHasOptionsMenu(true)

        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.main_overflow_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return true
    }
}
