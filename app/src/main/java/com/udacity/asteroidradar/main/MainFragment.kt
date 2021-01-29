package com.udacity.asteroidradar.main

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.udacity.asteroidradar.R
import com.udacity.asteroidradar.databinding.FragmentMainBinding

class MainFragment : Fragment() {


    private val viewModel: MainViewModel by lazy {
        val activity = requireNotNull(this.activity) {
            "You can only access the viewModel after onViewCreated()"
        }
        ViewModelProvider(
            this,
            MainViewModelFactory(activity.application)
        ).get(MainViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


        val binding = FragmentMainBinding.inflate(inflater)
        binding.lifecycleOwner = this

        binding.viewModel = viewModel
        val adapter =
            MainRecyclerAdapter(AsteroidClickListener {
                viewModel.onSelectAsteroid(it)
            })
        binding.asteroidRecycler.adapter = adapter


        viewModel.asteroids.observe(viewLifecycleOwner, Observer {
            it?.let {
                adapter.submitList(it)
            }
        })

        viewModel.selectedAsteroid.observe(
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
        when (item.itemId) {
            R.id.menu_week_asteroid -> {
                Toast.makeText(activity, "menu_week_asteroid", Toast.LENGTH_SHORT).show()
                viewModel.getWeeklyAsteroid()
            }
            R.id.menu_today_asteroid -> {
                Toast.makeText(activity, "menu_today_asteroid", Toast.LENGTH_SHORT).show()

                viewModel.getTodayAsteroid()
            }
            R.id.menu_save_asteroid -> {
                Toast.makeText(activity, "menu_save_asteroid", Toast.LENGTH_SHORT).show()

                viewModel.getSavedAsteroid()
            }
        }
        return true
    }
}
